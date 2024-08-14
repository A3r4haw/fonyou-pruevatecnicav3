package mx.mc.model;

import java.io.Serializable;

/**
 * @author AORTIZ
 */
public class Pais implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idPais;
    private String nombrePais;
    private char a2;
    private char a3;
    private int n3;
    private int estatus;

    public Pais() {
        //No code needed in constructor
    }

    public int getIdPais() {
        return idPais;
    }

    public void setIdPais(int idPais) {
        this.idPais = idPais;
    }

    public String getNombrePais() {
        return nombrePais;
    }

    public void setNombrePais(String nombrePais) {
        this.nombrePais = nombrePais;
    }

    public char getA2() {
        return a2;
    }

    public void setA2(char a2) {
        this.a2 = a2;
    }

    public char getA3() {
        return a3;
    }

    public void setA3(char a3) {
        this.a3 = a3;
    }

    public int getN3() {
        return n3;
    }

    public void setN3(int n3) {
        this.n3 = n3;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }
    
}
