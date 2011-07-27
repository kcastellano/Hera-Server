/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dominio;

import java.util.ArrayList;

/**
 *
 * @author katecastellano
 */
public class MedidaUsuario {

     private int idMedida;
    private String nombreMedida;
    private String tipoMedida;
    private String valorMedida;
    private ArrayList<MedidaUsuario> lista;

    public MedidaUsuario() {
    }

    public MedidaUsuario(int idMedida, String nombreMedida, String tipoMedida, String valorMedida) {
        this.idMedida = idMedida;
        this.nombreMedida = nombreMedida;
        this.tipoMedida = tipoMedida;
        this.valorMedida = valorMedida;
    }

    public int getIdMedida() {
        return idMedida;
    }

    public void setIdMedida(int idMedida) {
        this.idMedida = idMedida;
    }

    public ArrayList<MedidaUsuario> getLista() {
        return lista;
    }

    public void setLista(ArrayList<MedidaUsuario> lista) {
        this.lista = lista;
    }

    public String getNombreMedida() {
        return nombreMedida;
    }

    public void setNombreMedida(String nombreMedida) {
        this.nombreMedida = nombreMedida;
    }

    public String getTipoMedida() {
        return tipoMedida;
    }

    public void setTipoMedida(String tipoMedida) {
        this.tipoMedida = tipoMedida;
    }

    public String getValorMedida() {
        return valorMedida;
    }

    public void setValorMedida(String valorMedida) {
        this.valorMedida = valorMedida;
    }
}
