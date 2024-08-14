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
 * @author gcruz
 */
public class MedControlSurYReab implements Serializable {

    private static final long serialVersionUID = 1L;
    private Date insertFecha; 
    private String idInventario;
    private String nombreCorto;
    private String idSurtimientoInsumo; 
    private String idReabastoInsumo; 
    private String lote;
    private Date fechaCaducidad;
    private Date fechaAno;
    private int idTipoOrigen;
    private String procedencia;
    private String nombreMedico;
    private String cedula; 
    private String numFactura;
    private String numReceta; 
    private Integer cantidadAdquirida;
    private Integer cantidadVendida;
    private String observaciones;
    private String firma; 
    private Integer idSubcategoria;
    private String idEstructura;
    private String idMedicamento;

    public MedControlSurYReab() {
        //No code needed in constructor
    }

    public Date getInsertFecha() {
        return insertFecha;
    }

    public void setInsertFecha(Date insertFecha) {
        this.insertFecha = insertFecha;
    }

    public String getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(String idInventario) {
        this.idInventario = idInventario;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    public String getIdSurtimientoInsumo() {
        return idSurtimientoInsumo;
    }

    public void setIdSurtimientoInsumo(String idSurtimientoInsumo) {
        this.idSurtimientoInsumo = idSurtimientoInsumo;
    }

    public String getIdReabastoInsumo() {
        return idReabastoInsumo;
    }

    public void setIdReabastoInsumo(String idReabastoInsumo) {
        this.idReabastoInsumo = idReabastoInsumo;
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

    public Date getFechaAno() {
        return fechaAno;
    }

    public void setFechaAno(Date fechaAno) {
        this.fechaAno = fechaAno;
    }

    public int getIdTipoOrigen() {
        return idTipoOrigen;
    }

    public void setIdTipoOrigen(int idTipoOrigen) {
        this.idTipoOrigen = idTipoOrigen;
    }

    public String getProcedencia() {
        return procedencia;
    }

    public void setProcedencia(String procedencia) {
        this.procedencia = procedencia;
    }

    public String getNombreMedico() {
        return nombreMedico;
    }

    public void setNombreMedico(String nombreMedico) {
        this.nombreMedico = nombreMedico;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNumFactura() {
        return numFactura;
    }

    public void setNumFactura(String numFactura) {
        this.numFactura = numFactura;
    }

    public String getNumReceta() {
        return numReceta;
    }

    public void setNumReceta(String numReceta) {
        this.numReceta = numReceta;
    }

    public Integer getCantidadAdquirida() {
        return cantidadAdquirida;
    }

    public void setCantidadAdquirida(Integer cantidadAdquirida) {
        this.cantidadAdquirida = cantidadAdquirida;
    }

    public Integer getCantidadVendida() {
        return cantidadVendida;
    }

    public void setCantidadVendida(Integer cantidadVendida) {
        this.cantidadVendida = cantidadVendida;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public Integer getIdSubcategoria() {
        return idSubcategoria;
    }

    public void setIdSubcategoria(Integer idSubcategoria) {
        this.idSubcategoria = idSubcategoria;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public String getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(String idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    @Override
    public String toString() {
        return "MedControlSurYReab{" + "insertFecha=" + insertFecha + ", idInventario=" + idInventario + ", nombreCorto=" + nombreCorto + ", idSurtimientoInsumo=" + idSurtimientoInsumo + ", idReabastoInsumo=" + idReabastoInsumo + ", lote=" + lote + ", fechaCaducidad=" + fechaCaducidad + ", fechaAno=" + fechaAno + ", idTipoOrigen=" + idTipoOrigen + ", procedencia=" + procedencia + ", nombreMedico=" + nombreMedico + ", cedula=" + cedula + ", numFactura=" + numFactura + ", numReceta=" + numReceta + ", cantidadAdquirida=" + cantidadAdquirida + ", cantidadVendida=" + cantidadVendida + ", observaciones=" + observaciones + ", firma=" + firma + ", idSubcategoria=" + idSubcategoria + ", idEstructura=" + idEstructura + ", idMedicamento=" + idMedicamento + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.insertFecha);
        hash = 19 * hash + Objects.hashCode(this.idInventario);
        hash = 19 * hash + Objects.hashCode(this.nombreCorto);
        hash = 19 * hash + Objects.hashCode(this.idSurtimientoInsumo);
        hash = 19 * hash + Objects.hashCode(this.idReabastoInsumo);
        hash = 19 * hash + Objects.hashCode(this.lote);
        hash = 19 * hash + Objects.hashCode(this.fechaCaducidad);
        hash = 19 * hash + Objects.hashCode(this.fechaAno);
        hash = 19 * hash + this.idTipoOrigen;
        hash = 19 * hash + Objects.hashCode(this.procedencia);
        hash = 19 * hash + Objects.hashCode(this.nombreMedico);
        hash = 19 * hash + Objects.hashCode(this.cedula);
        hash = 19 * hash + Objects.hashCode(this.numFactura);
        hash = 19 * hash + Objects.hashCode(this.numReceta);
        hash = 19 * hash + Objects.hashCode(this.cantidadAdquirida);
        hash = 19 * hash + Objects.hashCode(this.cantidadVendida);
        hash = 19 * hash + Objects.hashCode(this.observaciones);
        hash = 19 * hash + Objects.hashCode(this.firma);
        hash = 19 * hash + Objects.hashCode(this.idSubcategoria);
        hash = 19 * hash + Objects.hashCode(this.idEstructura);
        hash = 19 * hash + Objects.hashCode(this.idMedicamento);
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
        final MedControlSurYReab other = (MedControlSurYReab) obj;
        if (this.idTipoOrigen != other.idTipoOrigen) {
            return false;
        }
        if (!Objects.equals(this.idInventario, other.idInventario)) {
            return false;
        }
        if (!Objects.equals(this.nombreCorto, other.nombreCorto)) {
            return false;
        }
        if (!Objects.equals(this.idSurtimientoInsumo, other.idSurtimientoInsumo)) {
            return false;
        }
        if (!Objects.equals(this.idReabastoInsumo, other.idReabastoInsumo)) {
            return false;
        }
        if (!Objects.equals(this.lote, other.lote)) {
            return false;
        }
        if (!Objects.equals(this.procedencia, other.procedencia)) {
            return false;
        }
        if (!Objects.equals(this.nombreMedico, other.nombreMedico)) {
            return false;
        }
        if (!Objects.equals(this.cedula, other.cedula)) {
            return false;
        }
        if (!Objects.equals(this.numFactura, other.numFactura)) {
            return false;
        }
        if (!Objects.equals(this.numReceta, other.numReceta)) {
            return false;
        }
        if (!Objects.equals(this.observaciones, other.observaciones)) {
            return false;
        }
        if (!Objects.equals(this.firma, other.firma)) {
            return false;
        }
        if (!Objects.equals(this.idEstructura, other.idEstructura)) {
            return false;
        }
        if (!Objects.equals(this.idMedicamento, other.idMedicamento)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        if (!Objects.equals(this.fechaCaducidad, other.fechaCaducidad)) {
            return false;
        }
        if (!Objects.equals(this.fechaAno, other.fechaAno)) {
            return false;
        }
        if (!Objects.equals(this.cantidadAdquirida, other.cantidadAdquirida)) {
            return false;
        }
        if (!Objects.equals(this.cantidadVendida, other.cantidadVendida)) {
            return false;
        }
        return Objects.equals(this.idSubcategoria, other.idSubcategoria);
    }
}
