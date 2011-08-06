/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dominio;

/**
 *
 * @author katecastellano
 */
public class Resultado {

    private String resultado;
    private double edadGestacional;

    public Resultado() {
    }

    public Resultado(String resultado, double edadGestacional) {
        this.resultado = resultado;
        this.edadGestacional = edadGestacional;
    }

    public double getEdadGestacional() {
        return edadGestacional;
    }

    public void setEdadGestacional(double edadGestacional) {
        this.edadGestacional = edadGestacional;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }



}
