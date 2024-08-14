package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Cervanets
 */
public class MedidaAntropometrica implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idMedidaAntropometrica;
    private Date fecha;
    private String idPaciente;
    private String idPacienteServicio;
    private String idPrescripcion;
    private Double peso;
    private Double estatura;
    private Double superficieCorporal;
    private Double cintura;

    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;

    public MedidaAntropometrica() {
    }

    public MedidaAntropometrica(String idMedidaAntropometrica) {
        this.idMedidaAntropometrica = idMedidaAntropometrica;
    }

    public String getIdMedidaAntropometrica() {
        return idMedidaAntropometrica;
    }

    public void setIdMedidaAntropometrica(String idMedidaAntropometrica) {
        this.idMedidaAntropometrica = idMedidaAntropometrica;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(String idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getIdPacienteServicio() {
        return idPacienteServicio;
    }

    public void setIdPacienteServicio(String idPacienteServicio) {
        this.idPacienteServicio = idPacienteServicio;
    }

    public String getIdPrescripcion() {
        return idPrescripcion;
    }

    public void setIdPrescripcion(String idPrescripcion) {
        this.idPrescripcion = idPrescripcion;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getEstatura() {
        return estatura;
    }

    public void setEstatura(Double estatura) {
        this.estatura = estatura;
    }

    public Double getSuperficieCorporal() {
        return superficieCorporal;
    }

    public void setSuperficieCorporal(Double superficieCorporal) {
        this.superficieCorporal = superficieCorporal;
    }

    public Double getCintura() {
        return cintura;
    }

    public void setCintura(Double cintura) {
        this.cintura = cintura;
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
        return "MedidaAntropometrica{" + "idMedidaAntropometrica=" + idMedidaAntropometrica + ", fecha=" + fecha + ", idPaciente=" + idPaciente + ", idPacienteServicio=" + idPacienteServicio + ", idPrescripcion=" + idPrescripcion + ", peso=" + peso + ", estatura=" + estatura + ", superficieCorporal=" + superficieCorporal + ", cintura=" + cintura + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.idMedidaAntropometrica);
        hash = 53 * hash + Objects.hashCode(this.fecha);
        hash = 53 * hash + Objects.hashCode(this.idPaciente);
        hash = 53 * hash + Objects.hashCode(this.idPacienteServicio);
        hash = 53 * hash + Objects.hashCode(this.idPrescripcion);
        hash = 53 * hash + Objects.hashCode(this.peso);
        hash = 53 * hash + Objects.hashCode(this.estatura);
        hash = 53 * hash + Objects.hashCode(this.superficieCorporal);
        hash = 53 * hash + Objects.hashCode(this.cintura);
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
        final MedidaAntropometrica other = (MedidaAntropometrica) obj;
        if (!Objects.equals(this.idMedidaAntropometrica, other.idMedidaAntropometrica)) {
            return false;
        }
        if (!Objects.equals(this.idPaciente, other.idPaciente)) {
            return false;
        }
        if (!Objects.equals(this.idPacienteServicio, other.idPacienteServicio)) {
            return false;
        }
        if (!Objects.equals(this.idPrescripcion, other.idPrescripcion)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.fecha, other.fecha)) {
            return false;
        }
        if (!Objects.equals(this.peso, other.peso)) {
            return false;
        }
        if (!Objects.equals(this.estatura, other.estatura)) {
            return false;
        }
        if (!Objects.equals(this.superficieCorporal, other.superficieCorporal)) {
            return false;
        }
        if (!Objects.equals(this.cintura, other.cintura)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        return Objects.equals(this.updateFecha, other.updateFecha);
    }

}
