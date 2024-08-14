/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author bbautista
 */
public class CantidadRazonadaExtended extends CantidadRazonada{
    
    private static final long serialVersionUID = 1L;
    
    private Date ultimoSurtimiento;
    private Integer diasRestantes;    
    private String tipoConsulta;
    private Integer totalSurtDia;
    private Integer totalSurtMes;
    private Date fechaInicio;
    private Date siguienteSurt;
    private String insumo;
    private String motivo;
    private boolean process;
    private boolean list;
    private List<Medicamento> insumosList;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.ultimoSurtimiento);
        hash = 79 * hash + Objects.hashCode(this.diasRestantes);
        hash = 79 * hash + Objects.hashCode(this.tipoConsulta);
        hash = 79 * hash + Objects.hashCode(this.totalSurtDia);
        hash = 79 * hash + Objects.hashCode(this.totalSurtMes);
        hash = 79 * hash + Objects.hashCode(this.fechaInicio);
        hash = 79 * hash + Objects.hashCode(this.siguienteSurt);
        hash = 79 * hash + Objects.hashCode(this.insumo);
        hash = 79 * hash + Objects.hashCode(this.motivo);
        hash = 79 * hash + (this.process ? 1 : 0);
        hash = 79 * hash + (this.list ? 1 : 0);
        hash = 79 * hash + Objects.hashCode(this.insumosList);
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
        final CantidadRazonadaExtended other = (CantidadRazonadaExtended) obj;
        if (this.process != other.process) {
            return false;
        }
        if (this.list != other.list) {
            return false;
        }
        if (!Objects.equals(this.tipoConsulta, other.tipoConsulta)) {
            return false;
        }
        if (!Objects.equals(this.insumo, other.insumo)) {
            return false;
        }
        if (!Objects.equals(this.motivo, other.motivo)) {
            return false;
        }
        if (!Objects.equals(this.ultimoSurtimiento, other.ultimoSurtimiento)) {
            return false;
        }
        if (!Objects.equals(this.diasRestantes, other.diasRestantes)) {
            return false;
        }
        if (!Objects.equals(this.totalSurtDia, other.totalSurtDia)) {
            return false;
        }
        if (!Objects.equals(this.totalSurtMes, other.totalSurtMes)) {
            return false;
        }
        if (!Objects.equals(this.fechaInicio, other.fechaInicio)) {
            return false;
        }
        if (!Objects.equals(this.siguienteSurt, other.siguienteSurt)) {
            return false;
        }
        if (!Objects.equals(this.insumosList, other.insumosList)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "CantidadRazonadaExtended{" + "ultimoSurtimiento=" + ultimoSurtimiento + ", diasRestantes=" + diasRestantes + ", tipoConsulta=" + tipoConsulta + ", totalSurtDia=" + totalSurtDia + ", totalSurtMes=" + totalSurtMes + ", fechaInicio=" + fechaInicio + ", siguienteSurt=" + siguienteSurt + ", insumo=" + insumo + ", motivo=" + motivo + ", process=" + process + ", list=" + list + ", insumosList=" + insumosList +'}';
    }    
    
    public Date getUltimoSurtimiento() {
        return ultimoSurtimiento;
    }

    public void setUltimoSurtimiento(Date ultimoSurtimiento) {
        this.ultimoSurtimiento = ultimoSurtimiento;
    }

    public Integer getDiasRestantes() {
        return diasRestantes;
    }

    public void setDiasRestantes(Integer diasRestantes) {
        this.diasRestantes = diasRestantes;
    }

    public String getTipoConsulta() {
        return tipoConsulta;
    }

    public void setTipoConsulta(String tipoConsulta) {
        this.tipoConsulta = tipoConsulta;
    }

    public Integer getTotalSurtDia() {
        return totalSurtDia;
    }

    public void setTotalSurtDia(Integer totalSurtDia) {
        this.totalSurtDia = totalSurtDia;
    }

    public Integer getTotalSurtMes() {
        return totalSurtMes;
    }

    public void setTotalSurtMes(Integer totalSurtMes) {
        this.totalSurtMes = totalSurtMes;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getSiguienteSurt() {
        return siguienteSurt;
    }

    public void setSiguienteSurt(Date siguienteSurt) {
        this.siguienteSurt = siguienteSurt;
    }

    public String getInsumo() {
        return insumo;
    }

    public void setInsumo(String insumo) {
        this.insumo = insumo;
    }        

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public boolean isProcess() {
        return process;
    }

    public void setProcess(boolean process) {
        this.process = process;
    }

    public boolean isList() {
        return list;
    }

    public void setList(boolean list) {
        this.list = list;
    }

    public List<Medicamento> getInsumosList() {
        return insumosList;
    }

    public void setInsumosList(List<Medicamento> insumosList) {
        this.insumosList = insumosList;
    }
    
}
