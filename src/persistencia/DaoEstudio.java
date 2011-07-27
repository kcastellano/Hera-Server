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
import dominio.Estudio;

/**
 *
 * @author ecomobile
 */
public interface DaoEstudio {
 	String Consultar_Estudios = "SELECT * FROM estudios";
	String Consultar_Estudio = "SELECT * FROM estudios "
                + "WHERE idEstudio = #{idEstudio}";
	String Crear_Estudio    = "    INSERT INTO estudios "
                + "(idEstudio,fechaEstudio,trimestre,fk_idPaciente)"
                + "VALUES (null, #{fechaEstudio}, #{trimestre}, #{idPaciente})";
	String Actualizar_Estudio   = "    UPDATE estudios SET idEstudio = #{idEstudio}, "
                + "fechaEstudio = #{fechaEstudio}, trimestre = #{trimestre},"
                + "fk_idPaciente = #{idPaciente},"
                + " WHERE idEstudio = #{idEstudio}";
	String Eliminar_Estudio   = "DELETE FROM estudios WHERE idEstudio=#{idEstudio}";

	@Select(Consultar_Estudios)
	public List<Estudio> obtenerTodosEstudios() throws Exception;

	@Select(Consultar_Estudio)
	public Estudio obtenerEstudioPorId(int id) throws Exception;

	@Insert(Crear_Estudio)
	public int crearEstudio (Estudio estudio) throws Exception;

	@Update(Actualizar_Estudio)
	public int actualizarEstudio(Estudio estudio) throws Exception; 

	@Delete(Eliminar_Estudio)
	public int eliminarEstudio (Estudio estudio) throws Exception;  

}

