/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import configuracion.ConexionFactory;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import persistencia.DaoEstudio;
import dominio.Estudio;

/**
 *
 * @author ecomobile
 */
public class ControladorEstudio {

    public List<Estudio> obtenerTodosEstudios() throws Exception {
        SqlSession sesion = ConexionFactory.getSession().openSession();
        DaoEstudio estudioDao = sesion.getMapper(DaoEstudio.class);
        List<Estudio> estudios = estudioDao.obtenerTodosEstudios();
        sesion.close();
        return estudios;
    }

    public Estudio obtenerEstudioPorId(int id) throws Exception {
        SqlSession sesion = ConexionFactory.getSession().openSession();
        DaoEstudio estudioDao = sesion.getMapper(DaoEstudio.class);
        Estudio estudio = estudioDao.obtenerEstudioPorId(id);
        sesion.close();
        return estudio;
    }

    public Estudio crearEstudio(Estudio estudio) throws Exception {
        SqlSession sesion = ConexionFactory.getSession().openSession();
        DaoEstudio estudioDao = sesion.getMapper(DaoEstudio.class);
        estudioDao.crearEstudio(estudio);
        sesion.commit();
        sesion.close();
        return estudio;
    }

    public Estudio actualizarEstudio(Estudio estudio) throws Exception {
        SqlSession sesion = ConexionFactory.getSession().openSession();
        DaoEstudio estudioDao = sesion.getMapper(DaoEstudio.class);
        estudioDao.actualizarEstudio(estudio);
        sesion.commit();
        sesion.close();
        return estudio;
    }

    public int eliminarEstudio(Estudio estudio) throws Exception {
        SqlSession sesion = ConexionFactory.getSession().openSession();
        DaoEstudio estudioDao = sesion.getMapper(DaoEstudio.class);
        int resultado = estudioDao.eliminarEstudio(estudio);
        sesion.commit();
        sesion.close();
        return resultado;
    }
}
