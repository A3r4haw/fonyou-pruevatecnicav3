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
public class InventarioSalida implements Serializable {
    
    private static final long serialVersionUID = 1L; 
    private String idInventarioSalida;
    private String idInventario;
    private String idEstructura;
    private Integer cantidad;
    private Double costoUnidosis;
    private Integer idTipoMotivo;
    private String notas;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;
    
    private String idMedicamento;
    private String clave;
    private String nombreCorto;
    private String presentacion;
    private Integer existencia;
    private String lote;
    private Date fechaCaducidad;
    private String tipoMotivo;
    private Integer activo;
    private String tipoMovimiento;

    public InventarioSalida() {
        //No code needed in constructor
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.idInventarioSalida);
        hash = 97 * hash + Objects.hashCode(this.idInventario);
        hash = 97 * hash + Objects.hashCode(this.idEstructura);
        hash = 97 * hash + Objects.hashCode(this.cantidad);
        hash = 97 * hash + Objects.hashCode(this.costoUnidosis);
        hash = 97 * hash + Objects.hashCode(this.idTipoMotivo);
        hash = 97 * hash + Objects.hashCode(this.notas);
        hash = 97 * hash + Objects.hashCode(this.insertFecha);
        hash = 97 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 97 * hash + Objects.hashCode(this.updateFecha);
        hash = 97 * hash + Objects.hashCode(this.updateIdUsuario);
        hash = 97 * hash + Objects.hashCode(this.idMedicamento);
        hash = 97 * hash + Objects.hashCode(this.clave);
        hash = 97 * hash + Objects.hashCode(this.nombreCorto);
        hash = 97 * hash + Objects.hashCode(this.presentacion);
        hash = 97 * hash + Objects.hashCode(this.existencia);
        hash = 97 * hash + Objects.hashCode(this.lote);
        hash = 97 * hash + Objects.hashCode(this.fechaCaducidad);
        hash = 97 * hash + Objects.hashCode(this.tipoMotivo);
        hash = 97 * hash + Objects.hashCode(this.activo);
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
        final InventarioSalida other = (InventarioSalida) obj;
        if (!Objects.equals(this.idInventarioSalida, other.idInventarioSalida)) {
            return false;
        }
        if (!Objects.equals(this.idInventario, other.idInventario)) {
            return false;
        }
        if (!Objects.equals(this.idEstructura, other.idEstructura)) {
            return false;
        }
        if (!Objects.equals(this.notas, other.notas)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.idMedicamento, other.idMedicamento)) {
            return false;
        }
        if (!Objects.equals(this.clave, other.clave)) {
            return false;
        }
        if (!Objects.equals(this.nombreCorto, other.nombreCorto)) {
            return false;
        }
        if (!Objects.equals(this.presentacion, other.presentacion)) {
            return false;
        }
        if (!Objects.equals(this.lote, other.lote)) {
            return false;
        }
        if (!Objects.equals(this.tipoMotivo, other.tipoMotivo)) {
            return false;
        }
        if (!Objects.equals(this.cantidad, other.cantidad)) {
            return false;
        }
        if (!Objects.equals(this.costoUnidosis, other.costoUnidosis)) {
            return false;
        }
        if (!Objects.equals(this.idTipoMotivo, other.idTipoMotivo)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        if (!Objects.equals(this.updateFecha, other.updateFecha)) {
            return false;
        }
        if (!Objects.equals(this.existencia, other.existencia)) {
            return false;
        }
        if (!Objects.equals(this.fechaCaducidad, other.fechaCaducidad)) {
            return false;
        }
        return Objects.equals(this.activo, other.activo);
    }

    @Override
    public String toString() {
        return "InventarioSalida{" + "idInventarioSalida=" + idInventarioSalida + ", idInventario=" + idInventario + ", idEstructura=" + idEstructura + ", cantidad=" + cantidad + ", costoUnidosis=" + costoUnidosis + ", idTipoMotivo=" + idTipoMotivo + ", notas=" + notas + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + ", idMedicamento=" + idMedicamento + ", clave=" + clave + ", nombreCorto=" + nombreCorto + ", presentacion=" + presentacion + ", existencia=" + existencia + ", lote=" + lote + ", fechaCaducidad=" + fechaCaducidad + ", tipoMotivo=" + tipoMotivo + ", activo=" + activo + '}';
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }

    
    public String getTipoMotivo() {
        return tipoMotivo;
    }

    public void setTipoMotivo(String tipoMotivo) {
        this.tipoMotivo = tipoMotivo;
    }

   
    public String getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(String idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

  
    public String getIdInventarioSalida() {
        return idInventarioSalida;
    }

    public void setIdInventarioSalida(String idInventarioSalida) {
        this.idInventarioSalida = idInventarioSalida;
    }

    public String getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(String idInventario) {
        this.idInventario = idInventario;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getCostoUnidosis() {
        return costoUnidosis;
    }

    public void setCostoUnidosis(Double costoUnidosis) {
        this.costoUnidosis = costoUnidosis;
    }

    public Integer getIdTipoMotivo() {
        return idTipoMotivo;
    }

    public void setIdTipoMotivo(Integer idTipoMotivo) {
        this.idTipoMotivo = idTipoMotivo;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
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

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public Integer getExistencia() {
        return existencia;
    }

    public void setExistencia(Integer existencia) {
        this.existencia = existencia;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }    

}
