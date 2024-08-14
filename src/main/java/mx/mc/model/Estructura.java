package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author hramirez
 */
public class Estructura implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idEstructura;
    private String idEstructuraPadre;
    private String nombre;
    private String descripcion;
    private Integer activa;
    private Integer idTipoAreaEstructura;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;
    private Integer idTipoAlmacen;
    private Integer idTipoArea;
    private boolean envioHL7;
    private String idEntidadHospitalaria;
    private String clavePresupuestal;
    private boolean mostrarPC;

    private String nombrePadre;
    private String tipoAreaEstructura;
    private String tipoAlmacen;
    private String estatus;
    private String motivo;
    private String claveEstructura;
    private String idAlmacenPeriferico;
    private String ubicacion;
    
    public Estructura() {
    }

    public Estructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public Estructura(String idEstructura, String idEstructuraPadre, String nombre, String descripcion, Integer activa, Integer idTipoAreaEstructura, Date insertFecha, String insertIdUsuario, Date updateFecha, String updateIdUsuario, Integer idTipoAlmacen, Integer idTipoArea, boolean envioHL7, String idEntidadHospitalaria, String clavePresupuestal, boolean mostrarPC, String nombrePadre, String tipoAreaEstructura, String tipoAlmacen, String estatus, String motivo, String claveEstructura, String idAlmacenPeriferico, String ubicacion) {
        this.idEstructura = idEstructura;
        this.idEstructuraPadre = idEstructuraPadre;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.activa = activa;
        this.idTipoAreaEstructura = idTipoAreaEstructura;
        this.insertFecha = insertFecha;
        this.insertIdUsuario = insertIdUsuario;
        this.updateFecha = updateFecha;
        this.updateIdUsuario = updateIdUsuario;
        this.idTipoAlmacen = idTipoAlmacen;
        this.idTipoArea = idTipoArea;
        this.envioHL7 = envioHL7;
        this.idEntidadHospitalaria = idEntidadHospitalaria;
        this.clavePresupuestal = clavePresupuestal;
        this.mostrarPC = mostrarPC;
        this.nombrePadre = nombrePadre;
        this.tipoAreaEstructura = tipoAreaEstructura;
        this.tipoAlmacen = tipoAlmacen;
        this.estatus = estatus;
        this.motivo = motivo;
        this.claveEstructura = claveEstructura;
        this.idAlmacenPeriferico = idAlmacenPeriferico;
        this.ubicacion = ubicacion;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.idEstructura);
        hash = 53 * hash + Objects.hashCode(this.idEstructuraPadre);
        hash = 53 * hash + Objects.hashCode(this.nombre);
        hash = 53 * hash + Objects.hashCode(this.descripcion);
        hash = 53 * hash + Objects.hashCode(this.activa);
        hash = 53 * hash + Objects.hashCode(this.idTipoAreaEstructura);
        hash = 53 * hash + Objects.hashCode(this.insertFecha);
        hash = 53 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 53 * hash + Objects.hashCode(this.updateFecha);
        hash = 53 * hash + Objects.hashCode(this.updateIdUsuario);
        hash = 53 * hash + Objects.hashCode(this.idTipoAlmacen);
        hash = 53 * hash + Objects.hashCode(this.idTipoArea);
        hash = 53 * hash + (this.envioHL7 ? 1 : 0);
        hash = 53 * hash + Objects.hashCode(this.idEntidadHospitalaria);
        hash = 53 * hash + Objects.hashCode(this.clavePresupuestal);
        hash = 53 * hash + (this.mostrarPC ? 1 : 0);
        hash = 53 * hash + Objects.hashCode(this.nombrePadre);
        hash = 53 * hash + Objects.hashCode(this.tipoAreaEstructura);
        hash = 53 * hash + Objects.hashCode(this.tipoAlmacen);
        hash = 53 * hash + Objects.hashCode(this.estatus);
        hash = 53 * hash + Objects.hashCode(this.motivo);
        hash = 53 * hash + Objects.hashCode(this.claveEstructura);
        hash = 53 * hash + Objects.hashCode(this.idAlmacenPeriferico);
        hash = 53 * hash + Objects.hashCode(this.ubicacion);
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
        final Estructura other = (Estructura) obj;
        if (this.envioHL7 != other.envioHL7) {
            return false;
        }
        if (this.mostrarPC != other.mostrarPC) {
            return false;
        }
        if (!Objects.equals(this.idEstructura, other.idEstructura)) {
            return false;
        }
        if (!Objects.equals(this.idEstructuraPadre, other.idEstructuraPadre)) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.idEntidadHospitalaria, other.idEntidadHospitalaria)) {
            return false;
        }
        if (!Objects.equals(this.clavePresupuestal, other.clavePresupuestal)) {
            return false;
        }
        if (!Objects.equals(this.nombrePadre, other.nombrePadre)) {
            return false;
        }
        if (!Objects.equals(this.tipoAreaEstructura, other.tipoAreaEstructura)) {
            return false;
        }
        if (!Objects.equals(this.tipoAlmacen, other.tipoAlmacen)) {
            return false;
        }
        if (!Objects.equals(this.estatus, other.estatus)) {
            return false;
        }
        if (!Objects.equals(this.motivo, other.motivo)) {
            return false;
        }
        if (!Objects.equals(this.claveEstructura, other.claveEstructura)) {
            return false;
        }
        if (!Objects.equals(this.idAlmacenPeriferico, other.idAlmacenPeriferico)) {
            return false;
        }
        if (!Objects.equals(this.ubicacion, other.ubicacion)) {
            return false;
        }
        if (!Objects.equals(this.activa, other.activa)) {
            return false;
        }
        if (!Objects.equals(this.idTipoAreaEstructura, other.idTipoAreaEstructura)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        if (!Objects.equals(this.updateFecha, other.updateFecha)) {
            return false;
        }
        if (!Objects.equals(this.idTipoAlmacen, other.idTipoAlmacen)) {
            return false;
        }
        if (!Objects.equals(this.idTipoArea, other.idTipoArea)) {
            return false;
        }
        return true;
    }
   
    @Override
    public String toString() {
        return "Estructura{" + "idEstructura=" + idEstructura + ", idEstructuraPadre=" + idEstructuraPadre + ", nombre=" + nombre + ", descripcion=" + descripcion + ", activa=" + activa + ", idTipoAreaEstructura=" + idTipoAreaEstructura + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + ", idTipoAlmacen=" + idTipoAlmacen + ", idTipoArea=" + idTipoArea + ", envioHL7=" + envioHL7 + ", idEntidadHospitalaria=" + idEntidadHospitalaria + ", clavePresupuestal=" + clavePresupuestal + ", mostrarPC=" + mostrarPC + ", nombrePadre=" + nombrePadre + ", tipoAreaEstructura=" + tipoAreaEstructura + ", tipoAlmacen=" + tipoAlmacen + ", estatus=" + estatus + ", motivo=" + motivo + ", claveEstructura=" + claveEstructura + ", idAlmacenPeriferico=" + idAlmacenPeriferico + ", ubicacion=" + ubicacion + '}';
    }
      
    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }    

    public String getTipoAreaEstructura() {
        return tipoAreaEstructura;
    }

    public void setTipoAreaEstructura(String tipoAreaEstructura) {
        this.tipoAreaEstructura = tipoAreaEstructura;
    }

    public String getTipoAlmacen() {
        return tipoAlmacen;
    }

    public void setTipoAlmacen(String tipoAlmacen) {
        this.tipoAlmacen = tipoAlmacen;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }    
    
    public String getNombrePadre() {
        return nombrePadre;
    }

    public void setNombrePadre(String nombrePadre) {
        this.nombrePadre = nombrePadre;
    }
       
    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public String getIdEstructuraPadre() {
        return idEstructuraPadre;
    }

    public void setIdEstructuraPadre(String idEstructuraPadre) {
        this.idEstructuraPadre = idEstructuraPadre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean getEnvioHL7() {
        return envioHL7;
    }

    public void setEnvioHL7(boolean envioHL7) {
        this.envioHL7 = envioHL7;
    }
    
    public Integer getActiva() {
        return activa;
    }

    public void setActiva(Integer activa) {
        this.activa = activa;
    }

    public Integer getIdTipoAreaEstructura() {
		return idTipoAreaEstructura;
	}

    public void setIdTipoAreaEstructura(Integer idTipoAreaEstructura) {
            this.idTipoAreaEstructura = idTipoAreaEstructura;
    }

    public Date getInsertFecha() {
        return insertFecha;
    }

    public void setInsertFecha(Date insertFecha) {
        this.insertFecha = insertFecha;
    }

    public String getInsertIdUsuario() {
        return insertIdUsuario;
    }

    public void setInsertIdUsuario(String insertIdUsuario) {
        this.insertIdUsuario = insertIdUsuario;
    }

    public Date getUpdateFecha() {
        return updateFecha;
    }

    public void setUpdateFecha(Date updateFecha) {
        this.updateFecha = updateFecha;
    }

    public String getUpdateIdUsuario() {
        return updateIdUsuario;
    }

    public void setUpdateIdUsuario(String updateIdUsuario) {
        this.updateIdUsuario = updateIdUsuario;
    }
    
    public Integer getIdTipoAlmacen() {
            return idTipoAlmacen;
    }

    public void setIdTipoAlmacen(Integer idTipoAlmacen) {
            this.idTipoAlmacen = idTipoAlmacen;
    }

    public Integer getIdTipoArea() {
            return idTipoArea;
    }

    public void setIdTipoArea(Integer idTipoArea) {
            this.idTipoArea = idTipoArea;
    } 

    public String getIdEntidadHospitalaria() {
        return idEntidadHospitalaria;
    }

    public void setIdEntidadHospitalaria(String idEntidadHospitalaria) {
        this.idEntidadHospitalaria = idEntidadHospitalaria;
    }

    public String getClaveEstructura() {
        return claveEstructura;
    }

    public void setClaveEstructura(String claveEstructura) {
        this.claveEstructura = claveEstructura;
    }

    public String getClavePresupuestal() {
        return clavePresupuestal;
    }

    public void setClavePresupuestal(String clavePresupuestal) {
        this.clavePresupuestal = clavePresupuestal;
    }

    public String getIdAlmacenPeriferico() {
        return idAlmacenPeriferico;
    }

    public void setIdAlmacenPeriferico(String idAlmacenPeriferico) {
        this.idAlmacenPeriferico = idAlmacenPeriferico;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public boolean isMostrarPC() {
        return mostrarPC;
    }

    public void setMostrarPC(boolean mostrarPC) {
        this.mostrarPC = mostrarPC;
    }
    
}
