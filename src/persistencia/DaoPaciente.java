/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import dominio.Paciente;

/**
 *
 * @author ecomobile
 */
public interface DaoPaciente {
 	String Consultar_Pacientes = "SELECT * FROM pacientes";
	String Consultar_Paciente = "SELECT * FROM pacientes "
                + "WHERE cedula = #{cedula}";
	String Crear_Paciente   = "INSERT INTO pacientes "
                + "(cedula,nombrePaciente,apellidoPaciente,email,login,password)"
                + "VALUES (#{cedula}, #{nombrePaciente}, "
                + "#{apellidoPaciente}, #{email},#{login},#{password})";
	String Actualizar_Paciente   = "    UPDATE pacientes SET cedula = #{cedula}, "
                + "nombrePaciente = #{nombrePaciente}, apellidoPaciente = #{apellidoPaciente},"
                + "email = #{email}, login = #{login}, password = #{password}"
                + " WHERE cedula = #{cedula}";
	String Eliminar_Paciente   = "DELETE FROM pacientes WHERE cedula = #{cedula}";

	@Select(Consultar_Pacientes)
	public List<Paciente> obtenerTodosPacientes() throws Exception;

	@Select(Consultar_Paciente)
	public Paciente obtenerPacientePorId(int id) throws Exception;

	@Insert(Crear_Paciente)
	public int crearPaciente (Paciente paciente) throws Exception;

	@Update(Actualizar_Paciente)
	public int actualizarPaciente(Paciente paciente) throws Exception; 

	@Delete(Eliminar_Paciente)
	public int eliminarPaciente (Paciente paciente) throws Exception;  

}

