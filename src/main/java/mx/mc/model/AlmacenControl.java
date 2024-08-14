/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author bbautista
 */
public class AlmacenControl implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String idAlmacenPuntosControl;
    private String idAlmacen;
    private String idMedicamento;
    private Integer minimo;
    private Integer reorden;
    private Integer maximo;
    private Integer dotacion;
    private boolean activo;
    private Integer solicitud;
    private Integer estatusGabinete;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;

    private String insumo;
    private String almacen;
    private String nombreLargo;
    private String claveInstitucional;
    private Integer presentacionComercial;

    public AlmacenControl() {
        //No code needed in constructor
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.idAlmacenPuntosControl);
        hash = 89 * hash + Objects.hashCode(this.idAlmacen);
        hash = 89 * hash + Objects.hashCode(this.idMedicamento);
        hash = 89 * hash + Objects.hashCode(this.minimo);
        hash = 89 * hash + Objects.hashCode(this.reorden);
        hash = 89 * hash + Objects.hashCode(this.maximo);
        hash = 89 * hash + Objects.hashCode(this.dotacion);
        hash = 89 * hash + (this.activo ? 1 : 0);
        hash = 89 * hash + Objects.hashCode(this.solicitud);
        hash = 89 * hash + Objects.hashCode(this.estatusGabinete);
        hash = 89 * hash + Objects.hashCode(this.insertFecha);
        hash = 89 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 89 * hash + Objects.hashCode(this.updateFecha);
        hash = 89 * hash + Objects.hashCode(this.updateIdUsuario);
        hash = 89 * hash + Objects.hashCode(this.insumo);
        hash = 89 * hash + Objects.hashCode(this.almacen);
        hash = 89 * hash + Objects.hashCode(this.presentacionComercial);
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
        final AlmacenControl other = (AlmacenControl) obj;
        if (this.activo != other.activo) {
            return false;
        }
        if (!Objects.equals(this.idAlmacenPuntosControl, other.idAlmacenPuntosControl)) {
            return false;
        }
        if (!Objects.equals(this.idAlmacen, other.idAlmacen)) {
            return false;
        }
        if (!Objects.equals(this.idMedicamento, other.idMedicamento)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.insumo, other.insumo)) {
            return false;
        }
        if (!Objects.equals(this.almacen, other.almacen)) {
            return false;
        }
        if (!Objects.equals(this.minimo, other.minimo)) {
            return false;
        }
        if (!Objects.equals(this.reorden, other.reorden)) {
            return false;
        }
        if (!Objects.equals(this.maximo, other.maximo)) {
            return false;
        }
        if (!Objects.equals(this.dotacion, other.dotacion)) {
            return false;
        }
        if (!Objects.equals(this.solicitud, other.solicitud)) {
            return false;
        }
        if (!Objects.equals(this.estatusGabinete, other.estatusGabinete)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        if (!Objects.equals(this.updateFecha, other.updateFecha)) {
            return false;
        }
        return Objects.equals(this.presentacionComercial, other.presentacionComercial);
    }

    @Override
    public String toString() {
        return "AlmacenControl{" + "idAlmacenPuntosControl=" + idAlmacenPuntosControl + ", idAlmacen=" + idAlmacen + ", idMedicamento=" + idMedicamento + ", minimo=" + minimo + ", reorden=" + reorden + ", maximo=" + maximo+ ", dotacion=" + dotacion + ", activo=" + activo + ", solicitud=" + solicitud + ", estatusGabinete=" + estatusGabinete + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + ", insumo=" + insumo + ", almacen=" + almacen + ", presentacionComercial="+ presentacionComercial + '}';
    }

    public Integer getEstatusGabinete() {
        return estatusGabinete;
    }

    public void setEstatusGabinete(Integer estatusGabinete) {
        this.estatusGabinete = estatusGabinete;
    }

    public Integer getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Integer solicitud) {
        this.solicitud = solicitud;
    }

    public String getIdAlmacenPuntosControl() {
        return idAlmacenPuntosControl;
    }

    public void setIdAlmacenPuntosControl(String idAlmacenPuntosControl) {
        this.idAlmacenPuntosControl = idAlmacenPuntosControl;
    }

    public String getIdAlmacen() {
        return idAlmacen;
    }

    public void setIdAlmacen(String idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    public String getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(String idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public Integer getMinimo() {
        return minimo;
    }

    public void setMinimo(Integer minimo) {
        this.minimo = minimo;
    }

    public Integer getReorden() {
        return reorden;
    }

    public void setReorden(Integer reorden) {
        this.reorden = reorden;
    }

    public Integer getDotacion() {
        return dotacion;
    }

    public void setDotacion(Integer dotacion) {
        this.dotacion = dotacion;
    }
        
    public Integer getMaximo() {
        return maximo;
    }

    public void setMaximo(Integer maximo) {
        this.maximo = maximo;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
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

    public String getInsumo() {
        return insumo;
    }

    public void setInsumo(String insumo) {
        this.insumo = insumo;
    }

    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
    }

    public String getNombreLargo() {
        return nombreLargo;
    }

    public void setNombreLargo(String nombreLargo) {
        this.nombreLargo = nombreLargo;
    }

    public String getClaveInstitucional() {
        return claveInstitucional;
    }

    public void setClaveInstitucional(String claveInstitucional) {
        this.claveInstitucional = claveInstitucional;
    }

    public Integer getPresentacionComercial() {
        return presentacionComercial;
    }

    public void setPresentacionComercial(Integer presentacionComercial) {
        this.presentacionComercial = presentacionComercial;
    }

}
