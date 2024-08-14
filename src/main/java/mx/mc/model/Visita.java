package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author hramirez
 */
public class Visita implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idVisita;
    private String idPaciente;
    private Date fechaIngreso;
    private String idUsuarioIngresa;
    private Date fechaEgreso;
    private String idUsuarioCierra;
    private Integer idMotivoPacienteMovimiento;
    private String motivoConsulta;
    private String tipoVisita;
    private String numVisita;   
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;
    
    public Visita() {
    }

    public Visita(String idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getIdVisita() {
        return idVisita;
    }

    public void setIdVisita(String idVisita) {
        this.idVisita = idVisita;
    }

    public String getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(String idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getIdUsuarioIngresa() {
        return idUsuarioIngresa;
    }

    public void setIdUsuarioIngresa(String idUsuarioIngresa) {
        this.idUsuarioIngresa = idUsuarioIngresa;
    }

    public Date getFechaEgreso() {
        return fechaEgreso;
    }

    public void setFechaEgreso(Date fechaEgreso) {
        this.fechaEgreso = fechaEgreso;
    }

    public String getIdUsuarioCierra() {
        return idUsuarioCierra;
    }

    public void setIdUsuarioCierra(String idUsuarioCierra) {
        this.idUsuarioCierra = idUsuarioCierra;
    }

    public Integer getIdMotivoPacienteMovimiento() {
        return idMotivoPacienteMovimiento;
    }

    public void setIdMotivoPacienteMovimiento(Integer idMotivoPacienteMovimiento) {
        this.idMotivoPacienteMovimiento = idMotivoPacienteMovimiento;
    }

    public String getMotivoConsulta() {
        return motivoConsulta;
    }

    public void setMotivoConsulta(String motivoConsulta) {
        this.motivoConsulta = motivoConsulta;
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

    public String getTipoVisita() {
        return tipoVisita;
    }

    public void setTipoVisita(String tipoVisita) {
        this.tipoVisita = tipoVisita;
    }

    public String getNumVisita() {
        return numVisita;
    }

    public void setNumVisita(String numVisita) {
        this.numVisita = numVisita;
    }

    @Override
    public String toString() {
        return "Visita{" + "idVisita=" + idVisita + ", idPaciente=" + idPaciente + ", fechaIngreso=" + fechaIngreso + ", idUsuarioIngresa=" + idUsuarioIngresa + ", fechaEgreso=" + fechaEgreso + ", idUsuarioCierra=" + idUsuarioCierra + ", idMotivoPacienteMovimiento=" + idMotivoPacienteMovimiento + ", motivoConsulta=" + motivoConsulta + ", tipoVisita=" + tipoVisita + ", numVisita=" + numVisita + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.idVisita);
        hash = 97 * hash + Objects.hashCode(this.idPaciente);
        hash = 97 * hash + Objects.hashCode(this.fechaIngreso);
        hash = 97 * hash + Objects.hashCode(this.idUsuarioIngresa);
        hash = 97 * hash + Objects.hashCode(this.fechaEgreso);
        hash = 97 * hash + Objects.hashCode(this.idUsuarioCierra);
        hash = 97 * hash + Objects.hashCode(this.idMotivoPacienteMovimiento);
        hash = 97 * hash + Objects.hashCode(this.motivoConsulta);
        hash = 97 * hash + Objects.hashCode(this.tipoVisita);
        hash = 97 * hash + Objects.hashCode(this.numVisita);
        hash = 97 * hash + Objects.hashCode(this.insertFecha);
        hash = 97 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 97 * hash + Objects.hashCode(this.updateFecha);
        hash = 97 * hash + Objects.hashCode(this.updateIdUsuario);
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
        final Visita other = (Visita) obj;
        if (!Objects.equals(this.idVisita, other.idVisita)) {
            return false;
        }
        if (!Objects.equals(this.idPaciente, other.idPaciente)) {
            return false;
        }
        if (!Objects.equals(this.idUsuarioIngresa, other.idUsuarioIngresa)) {
            return false;
        }
        if (!Objects.equals(this.idUsuarioCierra, other.idUsuarioCierra)) {
            return false;
        }
        if (!Objects.equals(this.motivoConsulta, other.motivoConsulta)) {
            return false;
        }
        if (!Objects.equals(this.tipoVisita, other.tipoVisita)) {
            return false;
        }
        if (!Objects.equals(this.numVisita, other.numVisita)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.fechaIngreso, other.fechaIngreso)) {
            return false;
        }
        if (!Objects.equals(this.fechaEgreso, other.fechaEgreso)) {
            return false;
        }
        if (!Objects.equals(this.idMotivoPacienteMovimiento, other.idMotivoPacienteMovimiento)) {
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
    
}
