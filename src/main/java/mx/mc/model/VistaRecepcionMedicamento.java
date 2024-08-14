/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author bbautista
 */
public class VistaRecepcionMedicamento implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String idSurtimiento;
    private String idPrescripcion;
    private String folioSurtimiento;
    private String fechaProgramada;
    private Integer idEstatusSurtimiento;
    private String estatusPrescripcion;
    private String estatusSurtimiento;
    private String nombrePaciente;
    private String pacienteNumero;
    private String claveDerechohabiencia;
    private String nombreEstructura;
    private String nombreCama;
    private String nombreMedico;
    private String folioPrescripcion;
    private String tipoPrescripcion;
    private String idSurtimientoEnviado;
    private String idSurtimientoInsumo;
    private String fechaPrescripcion;
    private String idEstructuraAlmacen;
    private String idEstructuraAlmacenPadre;
    private String nombreAlmacen;
    private Integer idTipoAlmacen;
    private Integer idTipoAreaEstructura;       
    private Integer idEstatusPaciente;
    
    public VistaRecepcionMedicamento() {
        //No code needed in constructor
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.idSurtimiento);
        hash = 47 * hash + Objects.hashCode(this.idPrescripcion);
        hash = 47 * hash + Objects.hashCode(this.folioSurtimiento);
        hash = 47 * hash + Objects.hashCode(this.fechaProgramada);
        hash = 47 * hash + Objects.hashCode(this.idEstatusSurtimiento);
        hash = 47 * hash + Objects.hashCode(this.estatusPrescripcion);
        hash = 47 * hash + Objects.hashCode(this.estatusSurtimiento);
        hash = 47 * hash + Objects.hashCode(this.nombrePaciente);
        hash = 47 * hash + Objects.hashCode(this.pacienteNumero);
        hash = 47 * hash + Objects.hashCode(this.claveDerechohabiencia);
        hash = 47 * hash + Objects.hashCode(this.nombreEstructura);
        hash = 47 * hash + Objects.hashCode(this.nombreCama);
        hash = 47 * hash + Objects.hashCode(this.nombreMedico);
        hash = 47 * hash + Objects.hashCode(this.folioPrescripcion);
        hash = 47 * hash + Objects.hashCode(this.tipoPrescripcion);
        hash = 47 * hash + Objects.hashCode(this.idSurtimientoEnviado);
        hash = 47 * hash + Objects.hashCode(this.idSurtimientoInsumo);
        hash = 47 * hash + Objects.hashCode(this.fechaPrescripcion);
        hash = 47 * hash + Objects.hashCode(this.idEstructuraAlmacen);
        hash = 47 * hash + Objects.hashCode(this.idEstructuraAlmacenPadre);
        hash = 47 * hash + Objects.hashCode(this.nombreAlmacen);
        hash = 47 * hash + Objects.hashCode(this.idTipoAlmacen);
        hash = 47 * hash + Objects.hashCode(this.idTipoAreaEstructura);
        hash = 47 * hash + Objects.hashCode(this.idEstatusPaciente);
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
        final VistaRecepcionMedicamento other = (VistaRecepcionMedicamento) obj;
        if (!Objects.equals(this.idSurtimiento, other.idSurtimiento)) {
            return false;
        }
        if (!Objects.equals(this.idPrescripcion, other.idPrescripcion)) {
            return false;
        }
        if (!Objects.equals(this.folioSurtimiento, other.folioSurtimiento)) {
            return false;
        }
        if (!Objects.equals(this.fechaProgramada, other.fechaProgramada)) {
            return false;
        }
        if (!Objects.equals(this.estatusPrescripcion, other.estatusPrescripcion)) {
            return false;
        }
        if (!Objects.equals(this.estatusSurtimiento, other.estatusSurtimiento)) {
            return false;
        }
        if (!Objects.equals(this.nombrePaciente, other.nombrePaciente)) {
            return false;
        }
        if (!Objects.equals(this.pacienteNumero, other.pacienteNumero)) {
            return false;
        }
        if (!Objects.equals(this.claveDerechohabiencia, other.claveDerechohabiencia)) {
            return false;
        }
        if (!Objects.equals(this.nombreEstructura, other.nombreEstructura)) {
            return false;
        }
        if (!Objects.equals(this.nombreCama, other.nombreCama)) {
            return false;
        }
        if (!Objects.equals(this.nombreMedico, other.nombreMedico)) {
            return false;
        }
        if (!Objects.equals(this.folioPrescripcion, other.folioPrescripcion)) {
            return false;
        }
        if (!Objects.equals(this.tipoPrescripcion, other.tipoPrescripcion)) {
            return false;
        }
        if (!Objects.equals(this.idSurtimientoEnviado, other.idSurtimientoEnviado)) {
            return false;
        }
        if (!Objects.equals(this.idSurtimientoInsumo, other.idSurtimientoInsumo)) {
            return false;
        }
        if (!Objects.equals(this.fechaPrescripcion, other.fechaPrescripcion)) {
            return false;
        }
        if (!Objects.equals(this.idEstructuraAlmacen, other.idEstructuraAlmacen)) {
            return false;
        }
        if (!Objects.equals(this.idEstructuraAlmacenPadre, other.idEstructuraAlmacenPadre)) {
            return false;
        }
        if (!Objects.equals(this.nombreAlmacen, other.nombreAlmacen)) {
            return false;
        }
        if (!Objects.equals(this.idEstatusSurtimiento, other.idEstatusSurtimiento)) {
            return false;
        }
        if (!Objects.equals(this.idTipoAlmacen, other.idTipoAlmacen)) {
            return false;
        }
        if (!Objects.equals(this.idTipoAreaEstructura, other.idTipoAreaEstructura)) {
            return false;
        }
        return Objects.equals(this.idEstatusPaciente, other.idEstatusPaciente);
    }

    @Override
    public String toString() {
        return "VistaRecepcionMedicamento{" + "idSurtimiento=" + idSurtimiento + ", idPrescripcion=" + idPrescripcion + ", folioSurtimiento=" + folioSurtimiento + ", fechaProgramada=" + fechaProgramada + ", idEstatusSurtimiento=" + idEstatusSurtimiento + ", estatusPrescripcion=" + estatusPrescripcion + ", estatusSurtimiento=" + estatusSurtimiento + ", nombrePaciente=" + nombrePaciente + ", pacienteNumero=" + pacienteNumero + ", claveDerechohabiencia=" + claveDerechohabiencia + ", nombreEstructura=" + nombreEstructura + ", nombreCama=" + nombreCama + ", nombreMedico=" + nombreMedico + ", folioPrescripcion=" + folioPrescripcion + ", tipoPrescripcion=" + tipoPrescripcion + ", idSurtimientoEnviado=" + idSurtimientoEnviado + ", idSurtimientoInsumo=" + idSurtimientoInsumo + ", fechaPrescripcion=" + fechaPrescripcion + ", idEstructuraAlmacen=" + idEstructuraAlmacen + ", idEstructuraAlmacenPadre=" + idEstructuraAlmacenPadre + ", nombreAlmacen=" + nombreAlmacen + ", idTipoAlmacen=" + idTipoAlmacen + ", idTipoAreaEstructura=" + idTipoAreaEstructura + ", idEstatusPaciente=" + idEstatusPaciente + '}';
    }

    public Integer getIdEstatusPaciente() {
        return idEstatusPaciente;
    }

    public void setIdEstatusPaciente(Integer idEstatusPaciente) {
        this.idEstatusPaciente = idEstatusPaciente;
    }

   

    public Integer getIdTipoAlmacen() {
        return idTipoAlmacen;
    }

    public void setIdTipoAlmacen(Integer idTipoAlmacen) {
        this.idTipoAlmacen = idTipoAlmacen;
    }

    public Integer getIdTipoAreaEstructura() {
        return idTipoAreaEstructura;
    }

    public void setIdTipoAreaEstructura(Integer idTipoAreaEstructura) {
        this.idTipoAreaEstructura = idTipoAreaEstructura;
    }

    
    public String getIdEstructuraAlmacen() {
        return idEstructuraAlmacen;
    }

    public void setIdEstructuraAlmacen(String idEstructuraAlmacen) {
        this.idEstructuraAlmacen = idEstructuraAlmacen;
    }

    public String getIdEstructuraAlmacenPadre() {
        return idEstructuraAlmacenPadre;
    }

    public void setIdEstructuraAlmacenPadre(String idEstructuraAlmacenPadre) {
        this.idEstructuraAlmacenPadre = idEstructuraAlmacenPadre;
    }

    public String getNombreAlmacen() {
        return nombreAlmacen;
    }

    public void setNombreAlmacen(String nombreAlmacen) {
        this.nombreAlmacen = nombreAlmacen;
    }

   
    public String getIdSurtimientoEnviado() {
        return idSurtimientoEnviado;
    }

    public void setIdSurtimientoEnviado(String idSurtimientoEnviado) {
        this.idSurtimientoEnviado = idSurtimientoEnviado;
    }

    public String getIdSurtimientoInsumo() {
        return idSurtimientoInsumo;
    }

    public void setIdSurtimientoInsumo(String idSurtimientoInsumo) {
        this.idSurtimientoInsumo = idSurtimientoInsumo;
    }

    public String getFechaPrescripcion() {
        return fechaPrescripcion;
    }

    public void setFechaPrescripcion(String fechaPrescripcion) {
        this.fechaPrescripcion = fechaPrescripcion;
    }

    
    public String getIdSurtimiento() {
        return idSurtimiento;
    }

    public void setIdSurtimiento(String idSurtimiento) {
        this.idSurtimiento = idSurtimiento;
    }

    public String getIdPrescripcion() {
        return idPrescripcion;
    }

    public void setIdPrescripcion(String idPrescripcion) {
        this.idPrescripcion = idPrescripcion;
    }

    public String getFolioSurtimiento() {
        return folioSurtimiento;
    }

    public void setFolioSurtimiento(String folioSurtimiento) {
        this.folioSurtimiento = folioSurtimiento;
    }

    public String getFechaProgramada() {
        return fechaProgramada;
    }

    public void setFechaProgramada(String fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
    }

    public Integer getIdEstatusSurtimiento() {
        return idEstatusSurtimiento;
    }

    public void setIdEstatusSurtimiento(Integer idEstatusSurtimiento) {
        this.idEstatusSurtimiento = idEstatusSurtimiento;
    }

    public String getEstatusPrescripcion() {
        return estatusPrescripcion;
    }

    public void setEstatusPrescripcion(String estatusPrescripcion) {
        this.estatusPrescripcion = estatusPrescripcion;
    }

    public String getEstatusSurtimiento() {
        return estatusSurtimiento;
    }

    public void setEstatusSurtimiento(String estatusSurtimiento) {
        this.estatusSurtimiento = estatusSurtimiento;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getPacienteNumero() {
        return pacienteNumero;
    }

    public void setPacienteNumero(String pacienteNumero) {
        this.pacienteNumero = pacienteNumero;
    }

    public String getClaveDerechohabiencia() {
        return claveDerechohabiencia;
    }

    public void setClaveDerechohabiencia(String claveDerechohabiencia) {
        this.claveDerechohabiencia = claveDerechohabiencia;
    }

    public String getNombreEstructura() {
        return nombreEstructura;
    }

    public void setNombreEstructura(String nombreEstructura) {
        this.nombreEstructura = nombreEstructura;
    }

    public String getNombreCama() {
        return nombreCama;
    }

    public void setNombreCama(String nombreCama) {
        this.nombreCama = nombreCama;
    }

    public String getNombreMedico() {
        return nombreMedico;
    }

    public void setNombreMedico(String nombreMedico) {
        this.nombreMedico = nombreMedico;
    }

    public String getFolioPrescripcion() {
        return folioPrescripcion;
    }

    public void setFolioPrescripcion(String folioPrescripcion) {
        this.folioPrescripcion = folioPrescripcion;
    }

    public String getTipoPrescripcion() {
        return tipoPrescripcion;
    }

    public void setTipoPrescripcion(String tipoPrescripcion) {
        this.tipoPrescripcion = tipoPrescripcion;
    }
    
    
    
}
