package mx.mc.model;

import java.io.Serializable;

/**
 * @author AORTIZ
 */
public class Sepomex  implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private int idCp;
    private String dCodigo;
    private String dAsenta;
    private String dTipoAsenta;
    private String dMnpio;
    private String dEstado;
    private String dCiudad;
    private String dCp;
    private String cEstado;
    private String cOficina;
    private String cCp;
    private String cTipoAsenta;
    private String cMnpio;
    private String idAsentaCpcons;
    private String dZona;
    private String cCveCiudad;

    public Sepomex() {
        //No code needed in constructor
    }

    public int getIdCp() {
        return idCp;
    }

    public void setIdCp(int idCp) {
        this.idCp = idCp;
    }

    public String getdCodigo() {
        return dCodigo;
    }

    public void setdCodigo(String dCodigo) {
        this.dCodigo = dCodigo;
    }

    public String getdAsenta() {
        return dAsenta;
    }

    public void setdAsenta(String dAsenta) {
        this.dAsenta = dAsenta;
    }

    public String getdTipoAsenta() {
        return dTipoAsenta;
    }

    public void setdTipoAsenta(String dTipoAsenta) {
        this.dTipoAsenta = dTipoAsenta;
    }

    public String getdMnpio() {
        return dMnpio;
    }

    public void setdMnpio(String dMnpio) {
        this.dMnpio = dMnpio;
    }

    public String getdEstado() {
        return dEstado;
    }

    public void setdEstado(String dEstado) {
        this.dEstado = dEstado;
    }

    public String getdCiudad() {
        return dCiudad;
    }

    public void setdCiudad(String dCiudad) {
        this.dCiudad = dCiudad;
    }

    public String getdCp() {
        return dCp;
    }

    public void setdCp(String dCp) {
        this.dCp = dCp;
    }

    public String getcEstado() {
        return cEstado;
    }

    public void setcEstado(String cEstado) {
        this.cEstado = cEstado;
    }

    public String getcOficina() {
        return cOficina;
    }

    public void setcOficina(String cOficina) {
        this.cOficina = cOficina;
    }

    public String getcCp() {
        return cCp;
    }

    public void setcCp(String cCp) {
        this.cCp = cCp;
    }

    public String getcTipoAsenta() {
        return cTipoAsenta;
    }

    public void setcTipoAsenta(String cTipoAsenta) {
        this.cTipoAsenta = cTipoAsenta;
    }

    public String getcMnpio() {
        return cMnpio;
    }

    public void setcMnpio(String cMnpio) {
        this.cMnpio = cMnpio;
    }

    public String getIdAsentaCpcons() {
        return idAsentaCpcons;
    }

    public void setIdAsentaCpcons(String idAsentaCpcons) {
        this.idAsentaCpcons = idAsentaCpcons;
    }

    public String getdZona() {
        return dZona;
    }

    public void setdZona(String dZona) {
        this.dZona = dZona;
    }

    public String getcCveCiudad() {
        return cCveCiudad;
    }

    public void setcCveCiudad(String cCveCiudad) {
        this.cCveCiudad = cCveCiudad;
    }
   
}
