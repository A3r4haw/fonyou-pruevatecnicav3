/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.dto;

import java.util.Objects;

/**
 *
 * @author gcruz
 */
public class ValidacionNoCumplidaDTO {
    
    private String codigo;
    private String descripcion;
    private String mandatoria;

    public ValidacionNoCumplidaDTO() {
    }

    public ValidacionNoCumplidaDTO(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.codigo);
        hash = 89 * hash + Objects.hashCode(this.descripcion);
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
        final ValidacionNoCumplidaDTO other = (ValidacionNoCumplidaDTO) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ValidacionNoCumplidaDto{" + "codigo=" + codigo + ", descripcion=" + descripcion + '}';
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMandatoria() {
        return mandatoria;
    }

    public void setMandatoria(String mandatoria) {
        this.mandatoria = mandatoria;
    }
    
    
}
