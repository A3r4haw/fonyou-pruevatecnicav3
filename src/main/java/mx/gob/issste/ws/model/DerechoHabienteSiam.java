/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.issste.ws.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class DerechoHabienteSiam  implements Serializable{

    private static final long serialVersionUID = 1L;
    private Integer id;
    @JsonProperty("num_issste")
    private Integer numIssste;    
    private String rfc;
    @JsonProperty("apa_tra")
    private String apaTra;
    @JsonProperty("apa_tra")
    private String amaTra;
    @JsonProperty("nom_tra")
    private String nomTra;
    @JsonProperty("curp_tra")
    private String curpTra;
    @JsonProperty("apellido_paterno")
    private String apellidoPaterno;
    @JsonProperty("apellido_materno")
    private String apellidoMaterno;
    private String nombre;
    private String curp; 
    @JsonProperty("parentesco_cve")
    private Integer parentescoCve;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumIssste() {
        return numIssste;
    }

    public void setNumIssste(Integer numIssste) {
        this.numIssste = numIssste;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getApaTra() {
        return apaTra;
    }

    public void setApaTra(String apaTra) {
        this.apaTra = apaTra;
    }

    public String getAmaTra() {
        return amaTra;
    }

    public void setAmaTra(String amaTra) {
        this.amaTra = amaTra;
    }

    public String getNomTra() {
        return nomTra;
    }

    public void setNomTra(String nomTra) {
        this.nomTra = nomTra;
    }

    public String getCurpTra() {
        return curpTra;
    }

    public void setCurpTra(String curpTra) {
        this.curpTra = curpTra;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public Integer getParentescoCve() {
        return parentescoCve;
    }

    public void setParentescoCve(Integer parentescoCve) {
        this.parentescoCve = parentescoCve;
    }

    @Override
    public String toString() {
        return "DerechoHabienteSiam{" + "id=" + id + ", numIssste=" + numIssste + ", rfc=" + rfc + ", apaTra=" + apaTra + ", amaTra=" + amaTra + ", nomTra=" + nomTra + ", curpTra=" + curpTra + ", apellidoPaterno=" + apellidoPaterno + ", apellidoMaterno=" + apellidoMaterno + ", nombre=" + nombre + ", curp=" + curp + ", parentescoCve=" + parentescoCve + '}';
    }
    
    
    
}
