/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author bbautista
 */
public class EtiquetaInsumo implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String hospital;
    private String clave;
    private String descripcion;
    private String lote;
    private Date caducidad;
    private String fotosencible;
    private String origen;
    private String laboratorio;
    private String textoInstitucional;
    private String template;
    
    private Integer cantiad;
    private String ipPrint;
    
    private BigDecimal concentracion;
    private String codigoQR;
    
    public EtiquetaInsumo() {
    //No code needed in constructor
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.hospital);
        hash = 97 * hash + Objects.hashCode(this.clave);
        hash = 97 * hash + Objects.hashCode(this.descripcion);
        hash = 97 * hash + Objects.hashCode(this.lote);
        hash = 97 * hash + Objects.hashCode(this.caducidad);
        hash = 97 * hash + Objects.hashCode(this.fotosencible);
        hash = 97 * hash + Objects.hashCode(this.origen);
        hash = 97 * hash + Objects.hashCode(this.laboratorio);
        hash = 97 * hash + Objects.hashCode(this.textoInstitucional);
        hash = 97 * hash + Objects.hashCode(this.template);
        hash = 97 * hash + Objects.hashCode(this.cantiad);
        hash = 97 * hash + Objects.hashCode(this.ipPrint);
        hash = 97 * hash + Objects.hashCode(this.concentracion);
        hash = 97 * hash + Objects.hashCode(this.codigoQR);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EtiquetaInsumo other = (EtiquetaInsumo) obj;
        if (!Objects.equals(this.hospital, other.hospital)) {
            return false;
        }
        if (!Objects.equals(this.clave, other.clave)) {
            return false;
        }
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        if (!Objects.equals(this.lote, other.lote)) {
            return false;
        }
        if (!Objects.equals(this.fotosencible, other.fotosencible)) {
            return false;
        }
        if (!Objects.equals(this.origen, other.origen)) {
            return false;
        }
        if (!Objects.equals(this.laboratorio, other.laboratorio)) {
            return false;
        }
        if (!Objects.equals(this.textoInstitucional, other.textoInstitucional)) {
            return false;
        }
        if (!Objects.equals(this.template, other.template)) {
            return false;
        }
        if (!Objects.equals(this.ipPrint, other.ipPrint)) {
            return false;
        }
        if (!Objects.equals(this.caducidad, other.caducidad)) {
            return false;
        }
        return Objects.equals(this.cantiad, other.cantiad);
    }

    @Override
    public String toString() {
        return "EtiquetaInsumo{" + "hospital=" + hospital + ", clave=" + clave + ", descripcion=" + descripcion + ", lote=" + lote + ", caducidad=" + caducidad + ", fotosencible=" + fotosencible + ", origen=" + origen + ", laboratorio=" + laboratorio + ", textoInstitucional=" + textoInstitucional + ", template=" + template + ", cantiad=" + cantiad + ", ipPrint=" + ipPrint + '}';
    }



    public String getIpPrint() {
        return ipPrint;
    }

    public void setIpPrint(String ipPrint) {
        this.ipPrint = ipPrint;
    }
    
    
    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public Date getCaducidad() {
        return caducidad;
    }

    public void setCaducidad(Date caducidad) {
        this.caducidad = caducidad;
    }

    public String getFotosencible() {
        return fotosencible;
    }

    public void setFotosencible(String fotosencible) {
        this.fotosencible = fotosencible;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    public String getTextoInstitucional() {
        return textoInstitucional;
    }

    public void setTextoInstitucional(String textoInstitucional) {
        this.textoInstitucional = textoInstitucional;
    }

    public Integer getCantiad() {
        return cantiad;
    }

    public void setCantiad(Integer cantiad) {
        this.cantiad = cantiad;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public BigDecimal getConcentracion() {
        return concentracion;
    }

    public void setConcentracion(BigDecimal concentracion) {
        this.concentracion = concentracion;
    }

    public String getCodigoQR() {
        return codigoQR;
    }

    public void setCodigoQR(String codigoQR) {
        this.codigoQR = codigoQR;
    }

}
