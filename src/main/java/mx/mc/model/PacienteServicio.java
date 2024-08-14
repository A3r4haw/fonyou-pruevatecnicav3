package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author hramirez
 */
public class PacienteServicio implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idPacienteServicio;
    private String idVisita;
    private String idEstructura;
    private Date fechaAsignacionInicio;
    private String idUsuarioAsignacionInicio;
    private Date fechaAsignacionFin;
    private String idUsuarioAsignacionFin;
    private Integer idMotivoPacienteMovimiento;
    private String idMedico;
    private String justificacion;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;

    public PacienteServicio() {
    }

    public PacienteServicio(String idVisita) {
        this.idVisita = idVisita;
    }

    public PacienteServicio(String idVisita, String idEstructura) {
        this.idVisita = idVisita;
        this.idEstructura = idEstructura;
    }

    public String getIdPacienteServicio() {
        return idPacienteServicio;
    }

    public void setIdPacienteServicio(String idPacienteServicio) {
        this.idPacienteServicio = idPacienteServicio;
    }

    public String getIdVisita() {
        return idVisita;
    }

    public void setIdVisita(String idVisita) {
        this.idVisita = idVisita;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public Date getFechaAsignacionInicio() {
        return fechaAsignacionInicio;
    }

    public void setFechaAsignacionInicio(Date fechaAsignacionInicio) {
        this.fechaAsignacionInicio = fechaAsignacionInicio;
    }

    public String getIdUsuarioAsignacionInicio() {
        return idUsuarioAsignacionInicio;
    }

    public void setIdUsuarioAsignacionInicio(String idUsuarioAsignacionInicio) {
        this.idUsuarioAsignacionInicio = idUsuarioAsignacionInicio;
    }

    public Date getFechaAsignacionFin() {
        return fechaAsignacionFin;
    }

    public void setFechaAsignacionFin(Date fechaAsignacionFin) {
        this.fechaAsignacionFin = fechaAsignacionFin;
    }

    public String getIdUsuarioAsignacionFin() {
        return idUsuarioAsignacionFin;
    }

    public void setIdUsuarioAsignacionFin(String idUsuarioAsignacionFin) {
        this.idUsuarioAsignacionFin = idUsuarioAsignacionFin;
    }

    public Integer getIdMotivoPacienteMovimiento() {
        return idMotivoPacienteMovimiento;
    }

    public void setIdMotivoPacienteMovimiento(Integer idMotivoPacienteMovimiento) {
        this.idMotivoPacienteMovimiento = idMotivoPacienteMovimiento;
    }

    public String getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(String idMedico) {
        this.idMedico = idMedico;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
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

    @Override
    public String toString() {
        return "PacienteServicio{" + "idPacienteServicio=" + idPacienteServicio + ", idVisita=" + idVisita + ", idEstructura=" + idEstructura + ", fechaAsignacionInicio=" + fechaAsignacionInicio + ", idUsuarioAsignacionInicio=" + idUsuarioAsignacionInicio + ", fechaAsignacionFin=" + fechaAsignacionFin + ", idUsuarioAsignacionFin=" + idUsuarioAsignacionFin + ", idMotivoPacienteMovimiento=" + idMotivoPacienteMovimiento + ", idMedico=" + idMedico + ", justificacion=" + justificacion + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.idPacienteServicio);
        hash = 43 * hash + Objects.hashCode(this.idVisita);
        hash = 43 * hash + Objects.hashCode(this.idEstructura);
        hash = 43 * hash + Objects.hashCode(this.fechaAsignacionInicio);
        hash = 43 * hash + Objects.hashCode(this.idUsuarioAsignacionInicio);
        hash = 43 * hash + Objects.hashCode(this.fechaAsignacionFin);
        hash = 43 * hash + Objects.hashCode(this.idUsuarioAsignacionFin);
        hash = 43 * hash + Objects.hashCode(this.idMotivoPacienteMovimiento);
        hash = 43 * hash + Objects.hashCode(this.idMedico);
        hash = 43 * hash + Objects.hashCode(this.justificacion);
        hash = 43 * hash + Objects.hashCode(this.insertFecha);
        hash = 43 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 43 * hash + Objects.hashCode(this.updateFecha);
        hash = 43 * hash + Objects.hashCode(this.updateIdUsuario);
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
        final PacienteServicio other = (PacienteServicio) obj;
        if (!Objects.equals(this.idPacienteServicio, other.idPacienteServicio)) {
            return false;
        }
        if (!Objects.equals(this.idVisita, other.idVisita)) {
            return false;
        }
        if (!Objects.equals(this.idEstructura, other.idEstructura)) {
            return false;
        }
        if (!Objects.equals(this.idUsuarioAsignacionInicio, other.idUsuarioAsignacionInicio)) {
            return false;
        }
        if (!Objects.equals(this.idUsuarioAsignacionFin, other.idUsuarioAsignacionFin)) {
            return false;
        }
        if (!Objects.equals(this.idMedico, other.idMedico)) {
            return false;
        }
        if (!Objects.equals(this.justificacion, other.justificacion)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.fechaAsignacionInicio, other.fechaAsignacionInicio)) {
            return false;
        }
        if (!Objects.equals(this.fechaAsignacionFin, other.fechaAsignacionFin)) {
            return false;
        }
        if (!Objects.equals(this.idMotivoPacienteMovimiento, other.idMotivoPacienteMovimiento)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        return Objects.equals(this.updateFecha, other.updateFecha);
    }
}
