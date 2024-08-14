package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 
 * @author gcruz
 *
 */
public class Cama implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String idCama;
    private String nombreCama;
    private String ubicacion;
    private Integer idTipoCama;
    private Integer idEstatusCama;
    private String idEstructura;
    private String insertIdUsuario;
    private Date insertFecha;
    private String updateIdUsuario;
    private Date updateFecha;
    
    //Estos no van en la Base de Datos
    private String area;
    private String servicio;
    private String tipoCama;
    private String estatus;  
    private String razon;
    
    public Cama() {
    	//No code needed in constructor
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.idCama);
        hash = 67 * hash + Objects.hashCode(this.nombreCama);
        hash = 67 * hash + Objects.hashCode(this.ubicacion);
        hash = 67 * hash + Objects.hashCode(this.idTipoCama);
        hash = 67 * hash + Objects.hashCode(this.idEstatusCama);
        hash = 67 * hash + Objects.hashCode(this.idEstructura);
        hash = 67 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 67 * hash + Objects.hashCode(this.insertFecha);
        hash = 67 * hash + Objects.hashCode(this.updateIdUsuario);
        hash = 67 * hash + Objects.hashCode(this.updateFecha);
        hash = 67 * hash + Objects.hashCode(this.area);
        hash = 67 * hash + Objects.hashCode(this.servicio);
        hash = 67 * hash + Objects.hashCode(this.tipoCama);
        hash = 67 * hash + Objects.hashCode(this.estatus);
        hash = 67 * hash + Objects.hashCode(this.razon);
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
        final Cama other = (Cama) obj;
        if (!Objects.equals(this.idCama, other.idCama)) {
            return false;
        }
        if (!Objects.equals(this.nombreCama, other.nombreCama)) {
            return false;
        }
        if (!Objects.equals(this.ubicacion, other.ubicacion)) {
            return false;
        }
        if (!Objects.equals(this.idEstructura, other.idEstructura)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.area, other.area)) {
            return false;
        }
        if (!Objects.equals(this.servicio, other.servicio)) {
            return false;
        }
        if (!Objects.equals(this.tipoCama, other.tipoCama)) {
            return false;
        }
        if (!Objects.equals(this.estatus, other.estatus)) {
            return false;
        }
        if (!Objects.equals(this.razon, other.razon)) {
            return false;
        }
        if (!Objects.equals(this.idTipoCama, other.idTipoCama)) {
            return false;
        }
        if (!Objects.equals(this.idEstatusCama, other.idEstatusCama)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        return Objects.equals(this.updateFecha, other.updateFecha);
    }

    @Override
    public String toString() {
        return "Cama{" + "idCama=" + idCama + ", nombreCama=" + nombreCama + ", ubicacion=" + ubicacion + ", idTipoCama=" + idTipoCama + ", idEstatusCama=" + idEstatusCama + ", idEstructura=" + idEstructura + ", insertIdUsuario=" + insertIdUsuario + ", insertFecha=" + insertFecha + ", updateIdUsuario=" + updateIdUsuario + ", updateFecha=" + updateFecha + ", area=" + area + ", servicio=" + servicio + ", tipoCama=" + tipoCama + ", estatus=" + estatus + ", razon=" + razon + '}';
    }

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    
    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getTipoCama() {
        return tipoCama;
    }

    public void setTipoCama(String tipoCama) {
        this.tipoCama = tipoCama;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

   
    public String getIdCama() {
        return idCama;
    }

    public void setIdCama(String idCama) {
        this.idCama = idCama;
    }

    public String getNombreCama() {
        return nombreCama;
    }

    public void setNombreCama(String nombreCama) {
        this.nombreCama = nombreCama;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Integer getIdTipoCama() {
        return idTipoCama;
    }

    public void setIdTipoCama(Integer idTipoCama) {
        this.idTipoCama = idTipoCama;
    }

    public Integer getIdEstatusCama() {
        return idEstatusCama;
    }

    public void setIdEstatusCama(Integer idEstatusCama) {
        this.idEstatusCama = idEstatusCama;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public String getInsertIdUsuario() {
        return insertIdUsuario;
    }

    public void setInsertIdUsuario(String insertIdUsuario) {
        this.insertIdUsuario = insertIdUsuario;
    }

    public Date getInsertFecha() {
        return insertFecha;
    }

    public void setInsertFecha(Date insertFecha) {
        this.insertFecha = insertFecha;
    }

    public String getUpdateIdUsuario() {
        return updateIdUsuario;
    }

    public void setUpdateIdUsuario(String updateIdUsuario) {
        this.updateIdUsuario = updateIdUsuario;
    }

    public Date getUpdateFecha() {
        return updateFecha;
    }

    public void setUpdateFecha(Date updateFecha) {
        this.updateFecha = updateFecha;
    }

   
}
