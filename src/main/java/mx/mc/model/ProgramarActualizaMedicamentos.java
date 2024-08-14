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
public class ProgramarActualizaMedicamentos implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private String idProgramarActualizaMedicamentos;
    private Date fechaProgramada;
    private String nota;
    private Integer estatus;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;
    
    public ProgramarActualizaMedicamentos() {
        //No code needed in constructor
    }

    @Override
    public String toString() {
        return "programarActualizaMedicamentos{" + "idProgramarActualizaMedicamentos=" + idProgramarActualizaMedicamentos + ", fechaProgramada=" + fechaProgramada + ", nota=" + nota + ", estatus=" + estatus + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.idProgramarActualizaMedicamentos);
        hash = 67 * hash + Objects.hashCode(this.fechaProgramada);
        hash = 67 * hash + Objects.hashCode(this.nota);
        hash = 67 * hash + this.estatus;
        hash = 67 * hash + Objects.hashCode(this.insertFecha);
        hash = 67 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 67 * hash + Objects.hashCode(this.updateFecha);
        hash = 67 * hash + Objects.hashCode(this.updateIdUsuario);
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
        final ProgramarActualizaMedicamentos other = (ProgramarActualizaMedicamentos) obj;
        if (this.estatus != other.estatus) {
            return false;
        }
        if (!Objects.equals(this.idProgramarActualizaMedicamentos, other.idProgramarActualizaMedicamentos)) {
            return false;
        }
        if (!Objects.equals(this.nota, other.nota)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.fechaProgramada, other.fechaProgramada)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        if (!Objects.equals(this.updateFecha, other.updateFecha)) {
            return false;
        }
        return true;
    }

    public String getIdProgramarActualizaMedicamentos() {
        return idProgramarActualizaMedicamentos;
    }

    public void setIdProgramarActualizaMedicamentos(String idProgramarActualizaMedicamentos) {
        this.idProgramarActualizaMedicamentos = idProgramarActualizaMedicamentos;
    }

    public Date getFechaProgramada() {
        return fechaProgramada;
    }

    public void setFechaProgramada(Date fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
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
     
    
}
