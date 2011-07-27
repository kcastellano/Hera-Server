/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import configuracion.ConexionFactory;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import persistencia.DaoMedida;
import dominio.MedidaArchivo;

/**
 *
 * @author ecomobile
 */
public class ControladorMedida {

    public List<MedidaArchivo> obtenerTodasMedidas() throws Exception {
        SqlSession sesion = ConexionFactory.getSession().openSession();
        DaoMedida medidaDao = sesion.getMapper(DaoMedida.class);
        List<MedidaArchivo> medidas = medidaDao.obtenerTodasMedidas();
        sesion.close();
        return medidas;
    }

    public MedidaArchivo obtenerMedidaPorId(int id) throws Exception {
        SqlSession sesion = ConexionFactory.getSession().openSession();
        DaoMedida medidaDao = sesion.getMapper(DaoMedida.class);
        MedidaArchivo medida = medidaDao.obtenerMedidaPorId(id);
        sesion.close();
        return medida;
    }

    public MedidaArchivo crearMedida(MedidaArchivo medida) throws Exception {
        SqlSession sesion = ConexionFactory.getSession().openSession();
        DaoMedida medidaDao = sesion.getMapper(DaoMedida.class);
        medidaDao.crearMedida(medida);
        sesion.commit();
        sesion.close();
        return medida;
    }

    public MedidaArchivo actualizarMedida(MedidaArchivo medida) throws Exception {
        SqlSession sesion = ConexionFactory.getSession().openSession();
        DaoMedida medidaDao = sesion.getMapper(DaoMedida.class);
        medidaDao.actualizarMedida(medida);
        sesion.commit();
        sesion.close();
        return medida;
    }

    public int eliminarMedida(MedidaArchivo medida) throws Exception {
        SqlSession sesion = ConexionFactory.getSession().openSession();
        DaoMedida medidaDao = sesion.getMapper(DaoMedida.class);
        int resultado = medidaDao.eliminarMedida(medida);
        sesion.commit();
        sesion.close();
        return resultado;
    }
}
