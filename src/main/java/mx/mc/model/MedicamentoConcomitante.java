/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author bbautista
 */
public class MedicamentoConcomitante {
    private String idConcomitante;
    private String idReaccion;
    private String idInsumo;
    private String clave;
    private Double dosis;
  @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date inicioTratamiento;
  @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date finTratamiento;
    private String idDiagnostico;
    private String motivoPrescripcion;
  @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date insertFecha;
    private String insertIdUsuario;
  @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date updateFecha;
    private String updateIdUsuario;

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.idConcomitante);
        hash = 41 * hash + Objects.hashCode(this.idReaccion);
        hash = 41 * hash + Objects.hashCode(this.idInsumo);
        hash = 41 * hash + Objects.hashCode(this.clave);
        hash = 41 * hash + Objects.hashCode(this.dosis);
        hash = 41 * hash + Objects.hashCode(this.inicioTratamiento);
        hash = 41 * hash + Objects.hashCode(this.finTratamiento);
        hash = 41 * hash + Objects.hashCode(this.idDiagnostico);
        hash = 41 * hash + Objects.hashCode(this.motivoPrescripcion);
        hash = 41 * hash + Objects.hashCode(this.insertFecha);
        hash = 41 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 41 * hash + Objects.hashCode(this.updateFecha);
        hash = 41 * hash + Objects.hashCode(this.updateIdUsuario);
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
        final MedicamentoConcomitante other = (MedicamentoConcomitante) obj;
        if (!Objects.equals(this.idConcomitante, other.idConcomitante)) {
            return false;
        }
        if (!Objects.equals(this.idReaccion, other.idReaccion)) {
            return false;
        }
        if (!Objects.equals(this.idInsumo, other.idInsumo)) {
            return false;
        }
        if (!Objects.equals(this.clave, other.clave)) {
            return false;
        }
        if (!Objects.equals(this.idDiagnostico, other.idDiagnostico)) {
            return false;
        }
        if (!Objects.equals(this.motivoPrescripcion, other.motivoPrescripcion)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.dosis, other.dosis)) {
            return false;
        }
        if (!Objects.equals(this.inicioTratamiento, other.inicioTratamiento)) {
            return false;
        }
        if (!Objects.equals(this.finTratamiento, other.finTratamiento)) {
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

    @Override
    public String toString() {
        return "MedicamentoConcomitante{" + "idConcomitante=" + idConcomitante + ", idReaccion=" + idReaccion + ", idInsumo=" + idInsumo + ", clave=" + clave + ", dosis=" + dosis + ", inicioTratamiento=" + inicioTratamiento + ", finTratamiento=" + finTratamiento + ", idDiagnostico=" + idDiagnostico + ", motivoPrescripcion=" + motivoPrescripcion + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + '}';
    }

    public String getIdConcomitante() {
        return idConcomitante;
    }

    public void setIdConcomitante(String idConcomitante) {
        this.idConcomitante = idConcomitante;
    }

    public String getIdReaccion() {
        return idReaccion;
    }

    public void setIdReaccion(String idReaccion) {
        this.idReaccion = idReaccion;
    }

    public String getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(String idInsumo) {
        this.idInsumo = idInsumo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Double getDosis() {
        return dosis;
    }

    public void setDosis(Double dosis) {
        this.dosis = dosis;
    }

    public Date getInicioTratamiento() {
        return inicioTratamiento;
    }

    public void setInicioTratamiento(Date inicioTratamiento) {
        this.inicioTratamiento = inicioTratamiento;
    }

    public Date getFinTratamiento() {
        return finTratamiento;
    }

    public void setFinTratamiento(Date finTratamiento) {
        this.finTratamiento = finTratamiento;
    }

    public String getIdDiagnostico() {
        return idDiagnostico;
    }

    public void setIdDiagnostico(String idDiagnostico) {
        this.idDiagnostico = idDiagnostico;
    }

    public String getMotivoPrescripcion() {
        return motivoPrescripcion;
    }

    public void setMotivoPrescripcion(String motivoPrescripcion) {
        this.motivoPrescripcion = motivoPrescripcion;
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
