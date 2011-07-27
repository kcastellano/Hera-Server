/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heradataserver;


import logica.ConexionSocket;
import java.io.*;

/**
 *
 * @author Khaterine Castellano
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] argv) throws IOException, Exception {
        new ConexionSocket().start();
    }
}
