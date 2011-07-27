/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import dominio.Paciente;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;

/**
 *
 * @author Khaterine Castellano
 */
public class ConexionSybase {

    static class StreamGobbler extends Thread {

        InputStream is;
        String type;

        StreamGobbler(InputStream is, String type) {
            this.is = is;
            this.type = type;
        }

        public void run() {
            try {
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line = null;
                while ((line = br.readLine()) != null) {
                    System.out.println(type + ">" + line);
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    public ConexionSybase() {
    }

    
    public boolean connectionServer(String command, Paciente paciente) {
        boolean connected = false;
        try {
            String osName = System.getProperty("os.name");
            String[] cmd = new String[2];
            cmd[0] = "."; // should exist on all POSIX systems
            cmd[1] = command;
            String filename = paciente.getNombrePaciente()+paciente.getApellidoPaciente();
            writeScript(filename);
            Process process;
            process = Runtime.getRuntime().exec("chmod 777 /home/ecomobile/Server/"+filename);

            Runtime rt = Runtime.getRuntime();
            System.out.println("Executing " + cmd[0] + " " + cmd[1]);
            Process proc = rt.exec(new String[]{"bash", "-c", "sh script.sh"});

            StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), "ERROR");
            StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), "OUTPUT");

            errorGobbler.start();
            outputGobbler.start();

            Thread.sleep(2000L);
            ControlSybase db = new ControlSybase();
            connected = db.sybaseConnection(paciente);
            System.out.println(connected);
            endConnection();


        } catch (Throwable t) {
            t.printStackTrace();
        }
        return connected;
    }

    public void writeScript(String nameFile) {

        String scriptContent = "#!/bin/sh \n # Script de inicio de la base de datos \n"
                + "# de Sybase, extraida del aparato para ecosonogramas Logiq P5 \n"
                + ". /opt/sybase/SYBSsa9/bin/asa_config.sh; \n"
                + "dbeng9 /home/ecomobile/Server/"+nameFile+"/Master.db<< EOF  \n"
                +"y \n"
                + "EOF";

        try {
            Writer output = new BufferedWriter(new FileWriter("/home/ecomobile/Server/script.sh"));
            output.write(scriptContent);
            output.close();
            Runtime.getRuntime().exec("chmod u+x /home/ecomobile/Server/script.sh");
            System.out.println("El archivo fue creado");
        } catch (IOException ex) {
            System.out.println(ex);
            System.out.println("El archivo no pudo ser creado");
        }

    }

    public void endConnection() throws IOException {
        Process process;
        process = Runtime.getRuntime().exec(new String[]{"bash", "-c", "sh script2.sh"});
        File file = new File("/home/ecomobile/Server/script.sh");
        file.delete();
        StreamGobbler errorGobbler = new StreamGobbler(process.getErrorStream(), "ERROR");

        // any output?
        StreamGobbler outputGobbler = new StreamGobbler(process.getInputStream(), "OUTPUT");
        errorGobbler.start();
        outputGobbler.start();
    }
}
