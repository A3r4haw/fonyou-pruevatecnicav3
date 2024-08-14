package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Cervanets
 */
public class SignoVital implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idSigno;
    private Date fecha;
    private String idPaciente;
    private String idPacienteServicio;
    private String idPrescripcion;
    private Double temperaturaCorporal;
    private Double frecuenciaRespiratoria;
    private Double frecuenciaCardiaca;
    private Double presionDistolica;
    private Double presionSistolica;
    private Double presionIntracraneal;
    private Double saturacionOxigeno;
    private Double saturacionVenosaOxigeno;
    private Double glucosa;
    private Double estres;
    private Double dolor;
    private Double signosPiel;
    private Double estadofuncional;

    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;

    public SignoVital() {
    }

    public SignoVital(String idSigno) {
        this.idSigno = idSigno;
    }

    public String getIdSigno() {
        return idSigno;
    }

    public void setIdSigno(String idSigno) {
        this.idSigno = idSigno;
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

    public Double getTemperaturaCorporal() {
        return temperaturaCorporal;
    }

    public void setTemperaturaCorporal(Double temperaturaCorporal) {
        this.temperaturaCorporal = temperaturaCorporal;
    }

    public Double getFrecuenciaRespiratoria() {
        return frecuenciaRespiratoria;
    }

    public void setFrecuenciaRespiratoria(Double frecuenciaRespiratoria) {
        this.frecuenciaRespiratoria = frecuenciaRespiratoria;
    }

    public Double getFrecuenciaCardiaca() {
        return frecuenciaCardiaca;
    }

    public void setFrecuenciaCardiaca(Double frecuenciaCardiaca) {
        this.frecuenciaCardiaca = frecuenciaCardiaca;
    }

    public Double getPresionDistolica() {
        return presionDistolica;
    }

    public void setPresionDistolica(Double presionDistolica) {
        this.presionDistolica = presionDistolica;
    }

    public Double getPresionSistolica() {
        return presionSistolica;
    }

    public void setPresionSistolica(Double presionSistolica) {
        this.presionSistolica = presionSistolica;
    }

    public Double getPresionIntracraneal() {
        return presionIntracraneal;
    }

    public void setPresionIntracraneal(Double presionIntracraneal) {
        this.presionIntracraneal = presionIntracraneal;
    }

    public Double getSaturacionOxigeno() {
        return saturacionOxigeno;
    }

    public void setSaturacionOxigeno(Double saturacionOxigeno) {
        this.saturacionOxigeno = saturacionOxigeno;
    }

    public Double getSaturacionVenosaOxigeno() {
        return saturacionVenosaOxigeno;
    }

    public void setSaturacionVenosaOxigeno(Double saturacionVenosaOxigeno) {
        this.saturacionVenosaOxigeno = saturacionVenosaOxigeno;
    }

    public Double getGlucosa() {
        return glucosa;
    }

    public void setGlucosa(Double glucosa) {
        this.glucosa = glucosa;
    }

    public Double getEstres() {
        return estres;
    }

    public void setEstres(Double estres) {
        this.estres = estres;
    }

    public Double getDolor() {
        return dolor;
    }

    public void setDolor(Double dolor) {
        this.dolor = dolor;
    }

    public Double getSignosPiel() {
        return signosPiel;
    }

    public void setSignosPiel(Double signosPiel) {
        this.signosPiel = signosPiel;
    }

    public Double getEstadofuncional() {
        return estadofuncional;
    }

    public void setEstadofuncional(Double estadofuncional) {
        this.estadofuncional = estadofuncional;
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
        return "SignoVital{" + "idSigno=" + idSigno + ", fecha=" + fecha + ", idPaciente=" + idPaciente + ", idPacienteServicio=" + idPacienteServicio + ", idPrescripcion=" + idPrescripcion + ", temperaturaCorporal=" + temperaturaCorporal + ", frecuenciaRespiratoria=" + frecuenciaRespiratoria + ", frecuenciaCardiaca=" + frecuenciaCardiaca + ", presionDistolica=" + presionDistolica + ", presionSistolica=" + presionSistolica + ", presionIntracraneal=" + presionIntracraneal + ", saturacionOxigeno=" + saturacionOxigeno + ", saturacionVenosaOxigeno=" + saturacionVenosaOxigeno + ", glucosa=" + glucosa + ", estres=" + estres + ", dolor=" + dolor + ", signosPiel=" + signosPiel + ", estadofuncional=" + estadofuncional + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.idSigno);
        hash = 41 * hash + Objects.hashCode(this.fecha);
        hash = 41 * hash + Objects.hashCode(this.idPaciente);
        hash = 41 * hash + Objects.hashCode(this.idPacienteServicio);
        hash = 41 * hash + Objects.hashCode(this.idPrescripcion);
        hash = 41 * hash + Objects.hashCode(this.temperaturaCorporal);
        hash = 41 * hash + Objects.hashCode(this.frecuenciaRespiratoria);
        hash = 41 * hash + Objects.hashCode(this.frecuenciaCardiaca);
        hash = 41 * hash + Objects.hashCode(this.presionDistolica);
        hash = 41 * hash + Objects.hashCode(this.presionSistolica);
        hash = 41 * hash + Objects.hashCode(this.presionIntracraneal);
        hash = 41 * hash + Objects.hashCode(this.saturacionOxigeno);
        hash = 41 * hash + Objects.hashCode(this.saturacionVenosaOxigeno);
        hash = 41 * hash + Objects.hashCode(this.glucosa);
        hash = 41 * hash + Objects.hashCode(this.estres);
        hash = 41 * hash + Objects.hashCode(this.dolor);
        hash = 41 * hash + Objects.hashCode(this.signosPiel);
        hash = 41 * hash + Objects.hashCode(this.estadofuncional);
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
        final SignoVital other = (SignoVital) obj;
        if (!Objects.equals(this.idSigno, other.idSigno)) {
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
        if (!Objects.equals(this.temperaturaCorporal, other.temperaturaCorporal)) {
            return false;
        }
        if (!Objects.equals(this.frecuenciaRespiratoria, other.frecuenciaRespiratoria)) {
            return false;
        }
        if (!Objects.equals(this.frecuenciaCardiaca, other.frecuenciaCardiaca)) {
            return false;
        }
        if (!Objects.equals(this.presionDistolica, other.presionDistolica)) {
            return false;
        }
        if (!Objects.equals(this.presionSistolica, other.presionSistolica)) {
            return false;
        }
        if (!Objects.equals(this.presionIntracraneal, other.presionIntracraneal)) {
            return false;
        }
        if (!Objects.equals(this.saturacionOxigeno, other.saturacionOxigeno)) {
            return false;
        }
        if (!Objects.equals(this.saturacionVenosaOxigeno, other.saturacionVenosaOxigeno)) {
            return false;
        }
        if (!Objects.equals(this.glucosa, other.glucosa)) {
            return false;
        }
        if (!Objects.equals(this.estres, other.estres)) {
            return false;
        }
        if (!Objects.equals(this.dolor, other.dolor)) {
            return false;
        }
        if (!Objects.equals(this.signosPiel, other.signosPiel)) {
            return false;
        }
        if (!Objects.equals(this.estadofuncional, other.estadofuncional)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        return Objects.equals(this.updateFecha, other.updateFecha);
    }

}
