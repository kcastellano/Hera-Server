/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import configuracion.ConexionFactory;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import persistencia.DaoPaciente;
import dominio.Paciente;

/**
 *
 * @author ecomobile
 */
public class ControladorPaciente {

    public List<Paciente> obtenerTodosPacientes() throws Exception {
        SqlSession sesion = ConexionFactory.getSession().openSession();
        DaoPaciente PacienteDao = sesion.getMapper(DaoPaciente.class);
        List<Paciente> Pacientes = PacienteDao.obtenerTodosPacientes();
        sesion.close();
        return Pacientes;
    }

    public Paciente obtenerPacientePorId(int id) throws Exception {
        SqlSession sesion = ConexionFactory.getSession().openSession();
        DaoPaciente PacienteDao = sesion.getMapper(DaoPaciente.class);
        Paciente Paciente = PacienteDao.obtenerPacientePorId(id);
        sesion.close();
        return Paciente;
    }

    public Paciente crearPaciente(Paciente paciente) throws Exception {
        SqlSession sesion = ConexionFactory.getSession().openSession();
        DaoPaciente PacienteDao = sesion.getMapper(DaoPaciente.class);
        PacienteDao.crearPaciente(paciente);
        sesion.commit();
        sesion.close();
        return paciente;
    }

    public Paciente actualizarPaciente(Paciente paciente) throws Exception {
        SqlSession sesion = ConexionFactory.getSession().openSession();
        DaoPaciente PacienteDao = sesion.getMapper(DaoPaciente.class);
        PacienteDao.actualizarPaciente(paciente);
        sesion.commit();
        sesion.close();
        return paciente;
    }

    public int eliminarPaciente(Paciente paciente) throws Exception {
        SqlSession sesion = ConexionFactory.getSession().openSession();
        DaoPaciente PacienteDao = sesion.getMapper(DaoPaciente.class);
        int resultado = PacienteDao.eliminarPaciente(paciente);
        sesion.commit();
        sesion.close();
        return resultado;
    }
}
