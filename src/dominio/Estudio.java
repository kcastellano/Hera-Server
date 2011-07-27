/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import java.sql.*;

/**
 *
 * @author Khaterine Castellano
 */
public class Estudio {

    private int id;
    private Timestamp fechaEstudio;
    private int trimestre;
    private int idPaciente;

    public Estudio() {
    }

    public Estudio(int id, Timestamp fechaEstudio, int trimestre, int idPaciente) {
        this.id = id;
        this.fechaEstudio = fechaEstudio;
        this.trimestre = trimestre;
        this.idPaciente = idPaciente;
    }

    public Timestamp getFechaEstudio() {
        return fechaEstudio;
    }

    public void setFechaEstudio(Timestamp fechaEstudio) {
        this.fechaEstudio = fechaEstudio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public int getTrimestre() {
        return trimestre;
    }

    public void setTrimestre(int trimestre) {
        this.trimestre = trimestre;
    }

    @Override
    public String toString() {
        return "Estudio{" + "id=" + id + ", fecha=" + fechaEstudio + 
                ", trimestre=" + trimestre + ", idPaciente=" + idPaciente + '}';
    }
    
    
    
}
