package logica;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import dominio.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.*;

/**
 *
 * @author Khaterine Castellano
 */
public class ConexionSocket extends Thread {

    /** The hang-around time */
    final static int MINUTES = 1;
    /** The port number */
    public static int PORT1 = 1234;
    public static int PORT2 = 2345;
    /** The server socket. */
    ServerSocket ss;

    private Paciente patient = new Paciente();
    private String diagnose = null;
    private ArrayList<String> lista;

    /** Constructor -- just creates the ServerSocket */
    public ConexionSocket() throws IOException {
        ss = new ServerSocket(PORT1);
    }

    public boolean receiveFile(Socket clientSocket) throws IOException {
        DataInputStream input = new DataInputStream(clientSocket.getInputStream());
        String filename = patient.getNombrePaciente() + patient.getApellidoPaciente() + ".zip";
        FileOutputStream file = new FileOutputStream(filename);
        DataOutputStream output = new DataOutputStream(file);
        int bytesRead = 0;
        int currentBytes = 0;
        int length = 0;
        length = input.readInt();
        byte[] mybytearray = new byte[length];
        BufferedOutputStream bos = new BufferedOutputStream(file);
        long start = System.currentTimeMillis();
        bytesRead = input.read(mybytearray, 0, length);
        currentBytes = bytesRead;

        do {
            bytesRead =
                    input.read(mybytearray, currentBytes, (length - currentBytes));
            if (bytesRead >= 0) {
                currentBytes += bytesRead;
            }
        } while (bytesRead > 0);

        bos.write(mybytearray, 0, currentBytes);
        bos.flush();
        extractZipFile(filename);
        return true;
    }

    public void extractZipFile(String zipFile) throws ZipException, IOException {

        System.out.println("Extracting");
        try {
            int BUFFER = 2048;
            File file = new File(zipFile);


            ZipFile zip = new ZipFile(file);
            String newPath = zipFile.substring(0, zipFile.length() - 4);

            new File(newPath).mkdir();
            Enumeration zipFileEntries = zip.entries();

            // Process each entry
            while (zipFileEntries.hasMoreElements()) {
                // grab a zip file entry
                ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();

                String currentEntry = entry.getName();

                File destFile = new File(newPath, currentEntry);
                destFile = new File(newPath, destFile.getName());
                File destinationParent = destFile.getParentFile();

                // create the parent directory structure if needed
                destinationParent.mkdirs();
                if (!entry.isDirectory()) {
                    BufferedInputStream is = new BufferedInputStream(zip.getInputStream(entry));
                    int currentByte;
                    // establish buffer for writing file
                    byte data[] = new byte[BUFFER];

                    // write the current file to disk
                    FileOutputStream fos = new FileOutputStream(destFile);
                    BufferedOutputStream dest = new BufferedOutputStream(fos,
                            BUFFER);

                    // read and write until last byte is encountered
                    while ((currentByte = is.read(data, 0, BUFFER)) != -1) {
                        dest.write(data, 0, currentByte);
                    }
                    dest.flush();
                    dest.close();
                    is.close();
                }

            }
        } catch (IOException e) {
            System.err.println("Error" + e);
        }
    }

    public void receiveMessage(Socket clientSocket) throws IOException {
        DataInputStream input = new DataInputStream(clientSocket.getInputStream());
        int length = 0;
        length = input.readInt();
        byte[] array = new byte[length];
        System.out.println("The size is:" + length);
        for (int i = 0; i < length; i++) {
            byte ch = input.readByte();
            array[i] = ch;
        }
        String line = new String(array);
        System.out.println("The client says:" + line);
    }

    public Paciente receivePatientInformation(Socket clientSocket) throws IOException {
        DataInputStream input = new DataInputStream(clientSocket.getInputStream());
        String nombre = input.readUTF();
        String apellido = input.readUTF();
        String email = input.readUTF();
        String login = input.readUTF();
        String password = input.readUTF();

        patient.setNombrePaciente(nombre);
        patient.setApellidoPaciente(apellido);
        patient.setEmail(email);
        patient.setLogin(login);
        patient.setPassword(password);

        return patient;
    }

    public void getMeasurements(Socket clientSocket) throws IOException {
        DataInputStream input = new DataInputStream(clientSocket.getInputStream());
        int cantidad = input.readInt();
        System.out.println(cantidad);
        lista = new ArrayList<String>();
        for (int i = 0; i < cantidad; i++) {
            String valor = input.readUTF();
            lista.add(valor);
        }
        
        Diagnostico diagnostico = new Diagnostico();
       diagnose= diagnostico.processDiagnostic(lista.get(0).toString(), lista.get(1).toString(),
                lista.get(2).toString(), lista.get(3).toString());
       sendDiagnose(clientSocket);
    }

    public void sendDiagnose(Socket clientSocket) {
        DataOutputStream output = null;
        try {
            output = new DataOutputStream(clientSocket.getOutputStream());
            System.out.println(diagnose);
    //        System.out.println(diagnose.length());
      //      output.writeInt(diagnose.length());
            output.writeUTF(diagnose);
        } catch (IOException ex) {
            System.out.println("No se pudo enviar el diagnostico, error: "+ ex);
        }

    }

    public void sendMessage(Socket clientSocket, String message) throws IOException {
        DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
        String response = "Hello im the Server";
        output.writeInt(response.length());
        output.writeUTF(response);


    }

    public Socket connectToClient() {
        Socket clientSocket = null;
        try {
            clientSocket = ss.accept();
            System.out.println("Connection Made");

        } catch (IOException e) {
            System.out.println("Accept Failed");
            System.exit(1);
        }
        return clientSocket;
    }

    public void run() {
        boolean conn = false;
        while (conn == false) {
            try {
                System.out.println("SocketServer waiting for connection");
                // Socket cliente = new Socket("192.168.1.122",2345);

                Socket cliente = connectToClient();
                getMeasurements(cliente);
              //  cliente = connectToClient();
                

//                Paciente paciente = new Paciente();
//                paciente = receivePatientInformation(cliente);
//                receiveFile(cliente);
//
//                ConexionSybase connection = new ConexionSybase();
//                conn = connection.connectionServer("dbeng", paciente);
//
//                if (conn == true) {
//                    System.out.println("Se conecto a Sybase");
//
//                } else {
//                    System.out.println("Se ha producido un error al intentar conectarse a Sybase.");
//                }
            } catch (Exception e) {
                System.err.println("Error" + e);
            }
        }
    }

    public int swabInt(int v) {
        return (v >>> 24) | (v << 24)
                | ((v << 8) & 0x00FF0000) | ((v >> 8) & 0x0000FF00);
    }
}
