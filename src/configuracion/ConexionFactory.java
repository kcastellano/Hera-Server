/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package configuracion;

/**
 *
 * @author ecomobile
 */
import java.io.Reader;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


public class ConexionFactory {


    public static SqlSessionFactory getSession() {
                
    SqlSessionFactory sqlMap = null; 
    Reader read;
        try {
            read = Resources.getResourceAsReader("configuracion/configuration.xml");
            sqlMap = new SqlSessionFactoryBuilder().build(read);
        } catch (Exception e) {
            e.printStackTrace();
        }
     return sqlMap;
    }
}