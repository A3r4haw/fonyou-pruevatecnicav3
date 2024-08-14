/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author gcruz
 */
public class MedicamentoDTO {
    
    public String claveMedicamento;
    private BigDecimal dosis;
    private Integer frecuencia;
    private Integer duracion;

    public MedicamentoDTO() {
        
    }

    public MedicamentoDTO(String claveMedicamento, BigDecimal dosis, Integer frecuencia, Integer duracion) {
        this.claveMedicamento = claveMedicamento;
        this.dosis = dosis;
        this.frecuencia = frecuencia;
        this.duracion = duracion;
    }

    @Override
    public String toString() {
        return "MedicamentoDTO{" + "claveMedicamento=" + claveMedicamento + ", dosis=" + dosis + ", frecuencia=" + frecuencia + ", duracion=" + duracion + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + Objects.hashCode(this.claveMedicamento);
        hash = 61 * hash + Objects.hashCode(this.dosis);
        hash = 61 * hash + Objects.hashCode(this.frecuencia);
        hash = 61 * hash + Objects.hashCode(this.duracion);
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
        final MedicamentoDTO other = (MedicamentoDTO) obj;
        if (!Objects.equals(this.claveMedicamento, other.claveMedicamento)) {
            return false;
        }
        if (!Objects.equals(this.dosis, other.dosis)) {
            return false;
        }
        if (!Objects.equals(this.frecuencia, other.frecuencia)) {
            return false;
        }
        if (!Objects.equals(this.duracion, other.duracion)) {
            return false;
        }
        return true;
    }

    public String getClaveMedicamento() {
        return claveMedicamento;
    }

    public void setClaveMedicamento(String claveMedicamento) {
        this.claveMedicamento = claveMedicamento;
    }

    public BigDecimal getDosis() {
        return dosis;
    }

    public void setDosis(BigDecimal dosis) {
        this.dosis = dosis;
    }

    public Integer getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(Integer frecuencia) {
        this.frecuencia = frecuencia;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }        
    
}
