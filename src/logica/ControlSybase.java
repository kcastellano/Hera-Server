package logica;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;
import java.sql.*;
import java.util.Properties;
import dominio.*;
import java.text.SimpleDateFormat;
import java.util.Vector;

/**
 *
 * @author Khaterine Castellano
 */
public class ControlSybase {

    private Paciente paciente = new Paciente();
    private Estudio estudio = new Estudio();
    private Vector<MedidaArchivo> medidas = new Vector<MedidaArchivo>();
    private ControladorPaciente controladorPaciente = new ControladorPaciente();
    private ControladorEstudio controladorEstudio = new ControladorEstudio();
    private ControladorMedida controladorMedida = new ControladorMedida();

    public boolean sybaseConnection(Paciente pacient) {

        boolean connected = false;
        Logger log = Logger.getLogger("Probando ejemplo de Sybase.");
        String driver, url, dbFile, odbcDriver, host;
        Connection conn;
        host = "localhost";
        Properties _props = new Properties();
        _props.put("user", "dba");
        _props.put("password", "sql");


        try {
            Class.forName("com.sybase.jdbc2.jdbc.SybDriver").newInstance();

            StringBuffer temp = new StringBuffer();
            url = "jdbc:sybase:Tds:localhost:2638";

            conn = DriverManager.getConnection(url, _props);
            Statement stmt = conn.createStatement();
            String query = " SELECT * FROM DBCREATORS.Z_Patient";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                paciente.setCedula(Integer.valueOf(rs.getString(2)));
                paciente.setApellidoPaciente(rs.getString(3).toLowerCase());
                paciente.setNombrePaciente((rs.getString(4)).toLowerCase());
                paciente.setEmail(pacient.getEmail());
                paciente.setLogin(pacient.getLogin());
                paciente.setPassword(pacient.getPassword());
            }



            query = " SELECT * FROM DBCREATORS.Z_Study";
            rs = stmt.executeQuery(query);
            while (rs.next()) {

                estudio.setId(Integer.valueOf(rs.getString(2)));
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
                java.util.Date parsedDate = dateFormat.parse(rs.getString(3));
                java.sql.Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
                estudio.setFechaEstudio(timestamp);
                estudio.setIdPaciente(paciente.getCedula());
            }

            query = " SELECT * FROM DBCREATORS.Z_Parameter";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                MedidaArchivo medida = new MedidaArchivo();
                medida.setTipo(rs.getString(7));
                medida.setFeto(rs.getString(8).substring(6));
                medida.setValorNumericoResultado(Float.valueOf(rs.getString(10)));
                medida.setValorStringResultado(rs.getString(11));
                medida.setUnidad(rs.getString(12));
                medida.setNombreMedida(rs.getString(13));
                medida.setIdEstudio(estudio.getId());
                medida.setIdPaciente(paciente.getCedula());
                medidas.add(medida);
            }

            rs.close();
            stmt.close();
            conn.close();
            String tipo = medidas.elementAt(0).getFeto();
            insertarRegistros(paciente, estudio, medidas);

            // *****************************************************************
            // Finalmente, se liberan los recursos y se cierra la conexión:
            // *****************************************************************

            connected = true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }


        return connected;
    }

    public void insertarRegistros(Paciente paciente, Estudio estudio,
            Vector<MedidaArchivo> medidas) throws Exception {
        MedidaArchivo medida = new MedidaArchivo();
        controladorPaciente.crearPaciente(paciente);
        controladorEstudio.crearEstudio(estudio);

        for (int i = 0; i < medidas.size(); i++) {
            medida = medidas.elementAt(i);
            controladorMedida.crearMedida(medida);
        }
    }
}
