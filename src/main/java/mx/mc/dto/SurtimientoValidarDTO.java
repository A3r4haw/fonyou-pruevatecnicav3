/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.dto;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author gcruz
 */
public class SurtimientoValidarDTO {
    
    private String idSurtimiento;
    private Integer idEstatusSurtimiento;
    private Date fechaSurtimiento;
    private String folioSurtimiento;
    private String idSurtimientoInsumo;
    private Date fechaSurtimientoInsumo;
    private String idSurtimientoEnviado;
    private String idMinistrado;
    private Date fechaProgMinistrado;
    private Date fechaMinistrado;
    
    public SurtimientoValidarDTO() {
        
    }

    public SurtimientoValidarDTO(String idSurtimiento, Integer idEstatusSurtimiento, Date fechaSurtimiento, String folioSurtimiento, String idSurtimientoInsumo, Date fechaSurtimientoInsumo, String idSurtimientoEnviado, String idMinistrado, Date fechaProgMinistrado, Date fechaMinistrado) {
        this.idSurtimiento = idSurtimiento;
        this.idEstatusSurtimiento = idEstatusSurtimiento;
        this.fechaSurtimiento = fechaSurtimiento;
        this.folioSurtimiento = folioSurtimiento;
        this.idSurtimientoInsumo = idSurtimientoInsumo;
        this.fechaSurtimientoInsumo = fechaSurtimientoInsumo;
        this.idSurtimientoEnviado = idSurtimientoEnviado;
        this.idMinistrado = idMinistrado;
        this.fechaProgMinistrado = fechaProgMinistrado;
        this.fechaMinistrado = fechaMinistrado;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.idSurtimiento);
        hash = 17 * hash + Objects.hashCode(this.idEstatusSurtimiento);
        hash = 17 * hash + Objects.hashCode(this.fechaSurtimiento);
        hash = 17 * hash + Objects.hashCode(this.folioSurtimiento);
        hash = 17 * hash + Objects.hashCode(this.idSurtimientoInsumo);
        hash = 17 * hash + Objects.hashCode(this.fechaSurtimientoInsumo);
        hash = 17 * hash + Objects.hashCode(this.idSurtimientoEnviado);
        hash = 17 * hash + Objects.hashCode(this.idMinistrado);
        hash = 17 * hash + Objects.hashCode(this.fechaProgMinistrado);
        hash = 17 * hash + Objects.hashCode(this.fechaMinistrado);
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
        final SurtimientoValidarDTO other = (SurtimientoValidarDTO) obj;
        if (!Objects.equals(this.idSurtimiento, other.idSurtimiento)) {
            return false;
        }
        if (!Objects.equals(this.idEstatusSurtimiento, other.idEstatusSurtimiento)) {
            return false;
        }
        if (!Objects.equals(this.folioSurtimiento, other.folioSurtimiento)) {
            return false;
        }
        if (!Objects.equals(this.idSurtimientoInsumo, other.idSurtimientoInsumo)) {
            return false;
        }
        if (!Objects.equals(this.idSurtimientoEnviado, other.idSurtimientoEnviado)) {
            return false;
        }
        if (!Objects.equals(this.idMinistrado, other.idMinistrado)) {
            return false;
        }
        if (!Objects.equals(this.fechaSurtimiento, other.fechaSurtimiento)) {
            return false;
        }
        if (!Objects.equals(this.fechaSurtimientoInsumo, other.fechaSurtimientoInsumo)) {
            return false;
        }
        if (!Objects.equals(this.fechaProgMinistrado, other.fechaProgMinistrado)) {
            return false;
        }
        if (!Objects.equals(this.fechaMinistrado, other.fechaMinistrado)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SurtimientoValidarDTO{" + "idSurtimiento=" + idSurtimiento + ", idEstatusSurtimiento=" + idEstatusSurtimiento + ", fechaSurtimiento=" + fechaSurtimiento + ", folioSurtimiento=" + folioSurtimiento + ", idSurtimientoInsumo=" + idSurtimientoInsumo + ", fechaSurtimientoInsumo=" + fechaSurtimientoInsumo + ", idSurtimientoEnviado=" + idSurtimientoEnviado + ", idMinistrado=" + idMinistrado + ", fechaProgMinistrado=" + fechaProgMinistrado + ", fechaMinistrado=" + fechaMinistrado + '}';
    }

    public String getIdSurtimiento() {
        return idSurtimiento;
    }

    public void setIdSurtimiento(String idSurtimiento) {
        this.idSurtimiento = idSurtimiento;
    }

    public Integer getIdEstatusSurtimiento() {
        return idEstatusSurtimiento;
    }

    public void setIdEstatusSurtimiento(Integer idEstatusSurtimiento) {
        this.idEstatusSurtimiento = idEstatusSurtimiento;
    }

    public Date getFechaSurtimiento() {
        return fechaSurtimiento;
    }

    public void setFechaSurtimiento(Date fechaSurtimiento) {
        this.fechaSurtimiento = fechaSurtimiento;
    }

    public String getFolioSurtimiento() {
        return folioSurtimiento;
    }

    public void setFolioSurtimiento(String folioSurtimiento) {
        this.folioSurtimiento = folioSurtimiento;
    }

    public String getIdSurtimientoInsumo() {
        return idSurtimientoInsumo;
    }

    public void setIdSurtimientoInsumo(String idSurtimientoInsumo) {
        this.idSurtimientoInsumo = idSurtimientoInsumo;
    }

    public Date getFechaSurtimientoInsumo() {
        return fechaSurtimientoInsumo;
    }

    public void setFechaSurtimientoInsumo(Date fechaSurtimientoInsumo) {
        this.fechaSurtimientoInsumo = fechaSurtimientoInsumo;
    }

    public String getIdSurtimientoEnviado() {
        return idSurtimientoEnviado;
    }

    public void setIdSurtimientoEnviado(String idSurtimientoEnviado) {
        this.idSurtimientoEnviado = idSurtimientoEnviado;
    }

    public String getIdMinistrado() {
        return idMinistrado;
    }

    public void setIdMinistrado(String idMinistrado) {
        this.idMinistrado = idMinistrado;
    }

    public Date getFechaProgMinistrado() {
        return fechaProgMinistrado;
    }

    public void setFechaProgMinistrado(Date fechaProgMinistrado) {
        this.fechaProgMinistrado = fechaProgMinistrado;
    }

    public Date getFechaMinistrado() {
        return fechaMinistrado;
    }

    public void setFechaMinistrado(Date fechaMinistrado) {
        this.fechaMinistrado = fechaMinistrado;
    }
    
    
}
