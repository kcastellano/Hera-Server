/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import dominio.MedidaUsuario;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import jess.*;

/**
 *
 * @author katecastellano
 */
public class Diagnostico {

    private int id = 1;
    private Rete engine = new Rete(this);
    private Iterator result = null;

    private void checkInitialized() {

        String rulesFile = "/Users/katecastellano/Desktop/HeraDataServer/src/persistencia/rules.clp";
        try {

            engine.executeCommand("(batch \"" + rulesFile + "\")");
            engine.reset();
        } catch (Exception je) {
            System.out.println("Error:" + je);
        }
    }

    public String processDiagnostic(String forma, String ubicacion, String bordes, String diametro) {
        String diagnose = null;
        checkInitialized();
        try {
            ArrayList<MedidaUsuario> listaMedidas = new ArrayList<MedidaUsuario>();
            listaMedidas = getMedidas(ubicacion, bordes, forma, diametro);

            engine.reset();
            for (int i = 0; i < listaMedidas.size(); ++i) {
                MedidaUsuario med = new MedidaUsuario();
                med = listaMedidas.get(i);
                Value idMedida = new Value(med.getIdMedida(), RU.INTEGER);
                Value nombre = new Value(med.getNombreMedida(), RU.ATOM);
                Value valor = new Value(med.getValorMedida(), RU.ATOM);
                Value tipo = new Value(med.getTipoMedida(), RU.ATOM);
                Fact medida = new Fact("medida", engine);
                medida.setSlotValue("idMedida", idMedida);
                medida.setSlotValue("nombre", nombre);
                medida.setSlotValue("tipo", tipo);
                medida.setSlotValue("valor", valor);
                engine.assertFact(medida);
            }
            engine.run();
            int val = listaMedidas.get(0).getIdMedida();
            result = engine.runQuery("diagnostico", new ValueVector().add(val));
            if (result.hasNext()) {
                String resultado = null;
                String edadGestacional = null;
                Token token = (Token) result.next();
                Fact fact1 = token.fact(1);
                Fact fact2 = token.fact(2);
                resultado = fact2.getSlotValue("resultado").stringValue(null);
                edadGestacional = fact2.getSlotValue("edadGestacional").stringValue(null);
                
                if (!edadGestacional.equalsIgnoreCase("0")) {
                    double GA = truncate(Double.valueOf(edadGestacional));
                    diagnose = "La edad gestacional es: " + GA
                            + " y el diagnostico es: " + resultado;
                } else {
                    diagnose = "El diagnostico es: " + resultado;
                }
            }


        } catch (JessException ex) {
            Logger.getLogger(Diagnostico.class.getName()).log(Level.SEVERE, null, ex);
        }

        return diagnose;
    }

    private MedidaUsuario setMedidas(String nombre, String tipo, String valor) {

        MedidaUsuario medida = new MedidaUsuario();
        medida.setIdMedida(id);
        medida.setNombreMedida(nombre);
        medida.setTipoMedida(tipo);
        medida.setValorMedida(valor);

        return medida;

    }

    private ArrayList<MedidaUsuario> getMedidas(String ubicacion, String bordes, String forma, String diametro) {
        ArrayList<MedidaUsuario> listaMedidas = new ArrayList<MedidaUsuario>();
        MedidaUsuario medida1 = setMedidas("forma", "saco-gestacional", forma);
        id++;
        MedidaUsuario medida2 = setMedidas("ubicacion", "saco-gestacional", ubicacion);
        id++;
        MedidaUsuario medida3 = setMedidas("bordes", "saco-gestacional", bordes);
        id++;
        MedidaUsuario medida4 = setMedidas("diametro", "saco-gestacional", diametro);
        listaMedidas.add(medida1);
        listaMedidas.add(medida2);
        listaMedidas.add(medida3);
        listaMedidas.add(medida4);
        return listaMedidas;
    }

    private static double truncate(double x) {
        DecimalFormat df = new DecimalFormat("0.#");
        String d = df.format(x);
        d = d.replaceAll(",", ".");
        Double dbl = new Double(d);
        return dbl.doubleValue();
    }
}
