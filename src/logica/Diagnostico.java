/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import dominio.MedidaUsuario;
import dominio.NombreMediciones;
import dominio.Resultado;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    public ArrayList<String> nombresMediciones = null;

    private void checkInitialized() {

        String rulesFile = "/Users/katecastellano/Universidad/Tesis/Proyectos/Java/HeraDataServer/src/persistencia/rules.clp";
        try {

            engine.executeCommand("(batch \"" + rulesFile + "\")");
            engine.reset();
        } catch (Exception je) {
            System.out.println("Error:" + je);
        }
    }

    public ArrayList<String> getNombresMediciones() {
        ArrayList<String> nombreMediciones = new ArrayList<String>();

        for (NombreMediciones n : NombreMediciones.values()) {
            String nombre = n.name();
            nombreMediciones.add(nombre);
        }
        return nombreMediciones;
    }

    public Resultado processDiagnostic(ArrayList<String> lista) {
        String diagnose = null;
        checkInitialized();
        Resultado res = new Resultado();
        try {
            ArrayList<MedidaUsuario> listaMedidas = new ArrayList<MedidaUsuario>();
            listaMedidas = getMedidas(lista);

            engine.reset();
            for (int i = 0; i < listaMedidas.size(); ++i) {
                MedidaUsuario med = new MedidaUsuario();
                med = listaMedidas.get(i);
                Value idMedida = new Value(med.getIdMedida(), RU.INTEGER);
                Value nombre = new Value(med.getNombreMedida(), RU.ATOM);
                Value valor = new Value(med.getValorMedida(), RU.ATOM);
                Fact medida = new Fact("medida", engine);
                medida.setSlotValue("idMedida", idMedida);
                medida.setSlotValue("nombre", nombre);
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
                            + "\n" + "El diagnostico es: " + resultado;
                    res.setResultado(diagnose);
                    res.setEdadGestacional(GA);

                } else {
                    diagnose = "El diagnostico es: " + resultado;
                    res.setResultado(diagnose);
                    res.setEdadGestacional(0);
                }
            }


        } catch (JessException ex) {
            Logger.getLogger(Diagnostico.class.getName()).log(Level.SEVERE, null, ex);
        }

        return res;
    }

    public ArrayList<String> fechaProbableDeParto(double GA) {

        int semana = (int) GA;
        int dias = (int) (GA - (int) GA);
        Calendar cal = Calendar.getInstance();
        ArrayList<String> fechas = new ArrayList<String>();

        cal.add(Calendar.WEEK_OF_YEAR, -semana);
        cal.add(Calendar.DAY_OF_YEAR, -dias);

        String FUR = "Fecha estimada ultima regla: " + cal.get(Calendar.DATE) + "-"
                + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.YEAR);

        cal.add(Calendar.WEEK_OF_YEAR, 1);
        cal.add(Calendar.WEEK_OF_MONTH, -13);
        int dia = cal.get(Calendar.DATE);
        int mes = (cal.get(Calendar.MONTH) + 1);
        int anio = cal.get(Calendar.YEAR) + 1;

        String FPP = "Fecha probable de parto: " + dia + "-"
                + mes + "-" + anio;

        fechas.add(FUR);
        fechas.add(FPP);

        System.out.println(FPP);
        System.out.println(FUR);
        return fechas;

    }

    private ArrayList<MedidaUsuario> getMedidas(ArrayList<String> lista) {
        ArrayList<MedidaUsuario> listaMedidas = new ArrayList<MedidaUsuario>();
        ArrayList<String> nombreMediciones = new ArrayList<String>();
        nombreMediciones = getNombresMediciones();
        for (int i = 0; i < lista.size(); i++) {
            int idM = id;
            MedidaUsuario medida = new MedidaUsuario();
            medida.setIdMedida(idM);
            medida.setNombreMedida(nombreMediciones.get(i));
            medida.setValorMedida(lista.get(i));
            id++;
            listaMedidas.add(medida);
            System.out.println(medida.getIdMedida());
            System.out.println(medida.getNombreMedida());
            System.out.println(medida.getValorMedida());
        }

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
