package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author hramirez
 */
public class DiagnosticoPaciente implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idDiagnosticoPaciente;
    private String idPrescripcion;
    private Date fechaDiagnostico;
    private String idUsuarioDiagnostico;
    private String idDiagnostico;
    private Date fechaFinDiagnostico;
    private String idUsuarioDiagnosticoTratado;
    private Integer idEstatusDiagnostico;

    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;

    public DiagnosticoPaciente() {
        //No code needed in constructor
    }

    public String getIdDiagnosticoPaciente() {
        return idDiagnosticoPaciente;
    }

    public void setIdDiagnosticoPaciente(String idDiagnosticoPaciente) {
        this.idDiagnosticoPaciente = idDiagnosticoPaciente;
    }

    public String getIdPrescripcion() {
        return idPrescripcion;
    }

    public void setIdPrescripcion(String idPrescripcion) {
        this.idPrescripcion = idPrescripcion;
    }

    public Date getFechaDiagnostico() {
        return fechaDiagnostico;
    }

    public void setFechaDiagnostico(Date fechaDiagnostico) {
        this.fechaDiagnostico = fechaDiagnostico;
    }

    public String getIdUsuarioDiagnostico() {
        return idUsuarioDiagnostico;
    }

    public void setIdUsuarioDiagnostico(String idUsuarioDiagnostico) {
        this.idUsuarioDiagnostico = idUsuarioDiagnostico;
    }

    public String getIdDiagnostico() {
        return idDiagnostico;
    }

    public void setIdDiagnostico(String idDiagnostico) {
        this.idDiagnostico = idDiagnostico;
    }

    public Date getFechaFinDiagnostico() {
        return fechaFinDiagnostico;
    }

    public void setFechaFinDiagnostico(Date fechaFinDiagnostico) {
        this.fechaFinDiagnostico = fechaFinDiagnostico;
    }

    public String getIdUsuarioDiagnosticoTratado() {
        return idUsuarioDiagnosticoTratado;
    }

    public void setIdUsuarioDiagnosticoTratado(String idUsuarioDiagnosticoTratado) {
        this.idUsuarioDiagnosticoTratado = idUsuarioDiagnosticoTratado;
    }

    public Integer getIdEstatusDiagnostico() {
        return idEstatusDiagnostico;
    }

    public void setIdEstatusDiagnostico(Integer idEstatusDiagnostico) {
        this.idEstatusDiagnostico = idEstatusDiagnostico;
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
        return "DiagnosticoPaciente{" + "idDiagnosticoPaciente=" + idDiagnosticoPaciente + ", idPrescripcion=" + idPrescripcion + ", fechaDiagnostico=" + fechaDiagnostico + ", idUsuarioDiagnostico=" + idUsuarioDiagnostico + ", idDiagnostico=" + idDiagnostico + ", fechaFinDiagnostico=" + fechaFinDiagnostico + ", idUsuarioDiagnosticoTratado=" + idUsuarioDiagnosticoTratado + ", idEstatusDiagnostico=" + idEstatusDiagnostico + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.idDiagnosticoPaciente);
        hash = 53 * hash + Objects.hashCode(this.idPrescripcion);
        hash = 53 * hash + Objects.hashCode(this.fechaDiagnostico);
        hash = 53 * hash + Objects.hashCode(this.idUsuarioDiagnostico);
        hash = 53 * hash + Objects.hashCode(this.idDiagnostico);
        hash = 53 * hash + Objects.hashCode(this.fechaFinDiagnostico);
        hash = 53 * hash + Objects.hashCode(this.idUsuarioDiagnosticoTratado);
        hash = 53 * hash + Objects.hashCode(this.idEstatusDiagnostico);
        hash = 53 * hash + Objects.hashCode(this.insertFecha);
        hash = 53 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 53 * hash + Objects.hashCode(this.updateFecha);
        hash = 53 * hash + Objects.hashCode(this.updateIdUsuario);
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
        final DiagnosticoPaciente other = (DiagnosticoPaciente) obj;
        if (!Objects.equals(this.idDiagnosticoPaciente, other.idDiagnosticoPaciente)) {
            return false;
        }
        if (!Objects.equals(this.idPrescripcion, other.idPrescripcion)) {
            return false;
        }
        if (!Objects.equals(this.idUsuarioDiagnostico, other.idUsuarioDiagnostico)) {
            return false;
        }
        if (!Objects.equals(this.idDiagnostico, other.idDiagnostico)) {
            return false;
        }
        if (!Objects.equals(this.idUsuarioDiagnosticoTratado, other.idUsuarioDiagnosticoTratado)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.fechaDiagnostico, other.fechaDiagnostico)) {
            return false;
        }
        if (!Objects.equals(this.fechaFinDiagnostico, other.fechaFinDiagnostico)) {
            return false;
        }
        if (!Objects.equals(this.idEstatusDiagnostico, other.idEstatusDiagnostico)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        return Objects.equals(this.updateFecha, other.updateFecha);
    }

}
