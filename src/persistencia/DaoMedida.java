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
import dominio.MedidaArchivo;

/**
 *
 * @author ecomobile
 */
public interface DaoMedida {
 	String Consultar_Medidas = "SELECT * FROM medidas";
	String Consultar_Medida = "SELECT * FROM Medidas "
                + "WHERE idMedida = #{idMedida}";
	String Crear_Medida    = "INSERT INTO medidas (idMedida,nombreMedida,"
                + "feto,resultadoNumerico,resultadoSemanas,unidad,"
                + "tipo,fk_idEstudio,fk_idPaciente)"
                + "VALUES (null, #{nombreMedida}, #{feto},"
                + " #{valorNumericoResultado},#{valorStringResultado},"
                + "#{unidad},#{tipo},#{idEstudio},#{idPaciente})";
	String Actualizar_Medida   = " UPDATE medidas SET idMedida = #{idMedida}, "
                + "nombreMedida = #{nombreMedida}, feto = #{feto},"
                + "resultadoNumerico = #{valorNumericoResultado},"
                + "resultadoSemanas = #{valorStringResultado},"
                + "unidad = #{unidad}, tipo = #{tipo}, fk_idEstudio = #{idEstudio},"
                + "fk_idPaciente = #{idPaciente},"
                + " WHERE idMedida = #{idMedida}";
	String Eliminar_Medida   = "DELETE FROM medidas WHERE idMedida=#{idMedida}";

	@Select(Consultar_Medidas)
	public List<MedidaArchivo> obtenerTodasMedidas() throws Exception;

	@Select(Consultar_Medida)
	public MedidaArchivo obtenerMedidaPorId(int id) throws Exception;

	@Insert(Crear_Medida)
	public int crearMedida (MedidaArchivo medida) throws Exception;

	@Update(Actualizar_Medida)
	public int actualizarMedida(MedidaArchivo medida) throws Exception;

	@Delete(Eliminar_Medida)
	public int eliminarMedida (MedidaArchivo medida) throws Exception;

}

