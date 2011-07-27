/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import java.io.Serializable;

/**
 *
 * @author Khaterine Castellano
 */
public class MedidaArchivo implements Serializable {
    
    private int id;
    private String nombreMedida;
    private String feto;
    private float valorNumericoResultado;
    private String valorStringResultado;
    private String unidad;
    private String tipo;
    private int idPaciente;
    private int idEstudio;

    public MedidaArchivo() {
    }

    public MedidaArchivo(int id, String nombreMedida, String feto, float valorNumericoResultado, String valorStringResultado, String unidad, String tipo, int idPaciente, int idEstudio) {
        this.id = id;
        this.nombreMedida = nombreMedida;
        this.feto = feto;
        this.valorNumericoResultado = valorNumericoResultado;
        this.valorStringResultado = valorStringResultado;
        this.unidad = unidad;
        this.tipo = tipo;
        this.idPaciente = idPaciente;
        this.idEstudio = idEstudio;
    }

    public String getFeto() {
        return feto;
    }

    public void setFeto(String feto) {
        this.feto = feto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdEstudio() {
        return idEstudio;
    }

    public void setIdEstudio(int idEstudio) {
        this.idEstudio = idEstudio;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getNombreMedida() {
        return nombreMedida;
    }

    public void setNombreMedida(String nombreMedida) {
        this.nombreMedida = nombreMedida;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public float getValorNumericoResultado() {
        return valorNumericoResultado;
    }

    public void setValorNumericoResultado(float valorNumericoResultado) {
        this.valorNumericoResultado = valorNumericoResultado;
    }

    public String getValorStringResultado() {
        return valorStringResultado;
    }

    public void setValorStringResultado(String valorStringResultado) {
        this.valorStringResultado = valorStringResultado;
    }

    @Override
    public String toString() {
        return "Medida{" + "id=" + id + ", nombre=" + nombreMedida + 
                ", feto=" + feto + ", valorNumericoResultado=" +
                valorNumericoResultado + ", valorStringResultado=" 
                + valorStringResultado + ", unidad=" + unidad + 
                ", tipo=" + tipo + ", idPaciente=" + idPaciente +
                ", idEstudio=" + idEstudio + '}';
    }
    
   
}
