/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

/**
 *
 * @author Khaterine Castellano
 */
public class Paciente {

    private int cedula;
    private String nombrePaciente;
    private String apellidoPaciente;
    private String email;
    private String login;
    private String password;
    
    public Paciente(int cedula,String nombre,String apellido,
            String email,String login,String password) {
        this.cedula = cedula;
        this.nombrePaciente = nombre;
        this.apellidoPaciente = apellido;
        this.email = email;
        this.login = login;
        this.password = password;
    }
    
    public Paciente() {
    }


    public String getApellidoPaciente() {
        return apellidoPaciente;
    }

    public void setApellidoPaciente(String apellidoPaciente) {
        this.apellidoPaciente = apellidoPaciente;
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Pacient{" + "cedula=" + cedula + ", nombre=" + 
                nombrePaciente + ", apellido=" + apellidoPaciente
                + ", email=" + email +  ", login=" + login 
                + ", password=" + password + '}';
    }
    
    
}
