package mx.mc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author hramirez
 */
public class PrescripcionInsumo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idPrescripcionInsumo;
    private String idPrescripcion;
    private String idInsumo;
    
    private String indicaciones;

    private Date fechaInicio;
    private BigDecimal dosis;
    private Integer frecuencia;
    private Integer duracion;
    private String comentarios;
    private Integer idEstatusPrescripcion;
    
    private Integer idEstatusMirth;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;
    private Integer envioJV;
    private Integer idTipoIngrediente;
    private Double velocidad;
    private Double ritmo;
    private Integer perfusionContinua;
    private Integer idUnidadConcentracion;
    

    public PrescripcionInsumo() {
    }

    public PrescripcionInsumo(String idPrescripcion, Integer idEstatusPrescripcion, Date updateFecha, String updateIdUsuario) {
        this.idPrescripcion = idPrescripcion;
        this.idEstatusPrescripcion = idEstatusPrescripcion;
        this.updateFecha = updateFecha;
        this.updateIdUsuario = updateIdUsuario;
    }

    public String getIdPrescripcionInsumo() {
        return idPrescripcionInsumo;
    }

    public void setIdPrescripcionInsumo(String idPrescripcionInsumo) {
        this.idPrescripcionInsumo = idPrescripcionInsumo;
    }

    public String getIdPrescripcion() {
        return idPrescripcion;
    }

    public void setIdPrescripcion(String idPrescripcion) {
        this.idPrescripcion = idPrescripcion;
    }

    public String getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(String idInsumo) {
        this.idInsumo = idInsumo;
    }

    public String getIndicaciones() {
        return indicaciones;
    }

    public void setIndicaciones(String indicaciones) {
        this.indicaciones = indicaciones;
    }
    
    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
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

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Integer getIdEstatusPrescripcion() {
        return idEstatusPrescripcion;
    }

    public void setIdEstatusPrescripcion(Integer idEstatusPrescripcion) {
        this.idEstatusPrescripcion = idEstatusPrescripcion;
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

    public Integer getIdEstatusMirth() {
        return idEstatusMirth;
    }

    public void setIdEstatusMirth(Integer idEstatusMirth) {
        this.idEstatusMirth = idEstatusMirth;
    }

    public Integer getEnvioJV() {
        return envioJV;
    }

    public void setEnvioJV(Integer envioJV) {
        this.envioJV = envioJV;
    }

    public Integer getIdTipoIngrediente() {
        return idTipoIngrediente;
    }

    public void setIdTipoIngrediente(Integer idTipoIngrediente) {
        this.idTipoIngrediente = idTipoIngrediente;
    }

    public Double getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(Double velocidad) {
        this.velocidad = velocidad;
    }

    public Double getRitmo() {
        return ritmo;
    }

    public void setRitmo(Double ritmo) {
        this.ritmo = ritmo;
    }

    public Integer getPerfusionContinua() {
        return perfusionContinua;
    }

    public void setPerfusionContinua(Integer perfusionContinua) {
        this.perfusionContinua = perfusionContinua;
    }

    public Integer getIdUnidadConcentracion() {
        return idUnidadConcentracion;
    }

    public void setIdUnidadConcentracion(Integer idUnidadConcentracion) {
        this.idUnidadConcentracion = idUnidadConcentracion;
    }    

    @Override
    public String toString() {
        return "PrescripcionInsumo{" + "idPrescripcionInsumo=" + idPrescripcionInsumo + ", idPrescripcion=" + idPrescripcion + ", idInsumo=" + idInsumo + ", indicaciones=" + indicaciones + ", fechaInicio=" + fechaInicio + ", dosis=" + dosis + ", frecuencia=" + frecuencia + ", duracion=" + duracion + ", comentarios=" + comentarios + ", idEstatusPrescripcion=" + idEstatusPrescripcion + ", idEstatusMirth=" + idEstatusMirth + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + ", envioJV=" + envioJV + ", idTipoIngrediente=" + idTipoIngrediente + ", velocidad=" + velocidad + ", ritmo=" + ritmo + ", perfusionContinua=" + perfusionContinua + ", idUnidadConcentracion=" + idUnidadConcentracion + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.idPrescripcionInsumo);
        hash = 53 * hash + Objects.hashCode(this.idPrescripcion);
        hash = 53 * hash + Objects.hashCode(this.idInsumo);
        hash = 53 * hash + Objects.hashCode(this.indicaciones);
        hash = 53 * hash + Objects.hashCode(this.fechaInicio);
        hash = 53 * hash + Objects.hashCode(this.dosis);
        hash = 53 * hash + Objects.hashCode(this.frecuencia);
        hash = 53 * hash + Objects.hashCode(this.duracion);
        hash = 53 * hash + Objects.hashCode(this.comentarios);
        hash = 53 * hash + Objects.hashCode(this.idEstatusPrescripcion);
        hash = 53 * hash + Objects.hashCode(this.idEstatusMirth);
        hash = 53 * hash + Objects.hashCode(this.insertFecha);
        hash = 53 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 53 * hash + Objects.hashCode(this.updateFecha);
        hash = 53 * hash + Objects.hashCode(this.updateIdUsuario);
        hash = 53 * hash + Objects.hashCode(this.envioJV);
        hash = 53 * hash + Objects.hashCode(this.idTipoIngrediente);
        hash = 53 * hash + (int) (Double.doubleToLongBits(this.velocidad) ^ (Double.doubleToLongBits(this.velocidad) >>> 32));
        hash = 53 * hash + (int) (Double.doubleToLongBits(this.ritmo) ^ (Double.doubleToLongBits(this.ritmo) >>> 32));
        hash = 53 * hash + Objects.hashCode(this.perfusionContinua);
        hash = 53 * hash + Objects.hashCode(this.idUnidadConcentracion);
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
        final PrescripcionInsumo other = (PrescripcionInsumo) obj;
        if (Double.doubleToLongBits(this.velocidad) != Double.doubleToLongBits(other.velocidad)) {
            return false;
        }
        if (Double.doubleToLongBits(this.ritmo) != Double.doubleToLongBits(other.ritmo)) {
            return false;
        }
        if (!Objects.equals(this.idPrescripcionInsumo, other.idPrescripcionInsumo)) {
            return false;
        }
        if (!Objects.equals(this.idPrescripcion, other.idPrescripcion)) {
            return false;
        }
        if (!Objects.equals(this.idInsumo, other.idInsumo)) {
            return false;
        }
        if (!Objects.equals(this.indicaciones, other.indicaciones)) {
            return false;
        }
        if (!Objects.equals(this.comentarios, other.comentarios)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.fechaInicio, other.fechaInicio)) {
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
        if (!Objects.equals(this.idEstatusPrescripcion, other.idEstatusPrescripcion)) {
            return false;
        }
        if (!Objects.equals(this.idEstatusMirth, other.idEstatusMirth)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        if (!Objects.equals(this.updateFecha, other.updateFecha)) {
            return false;
        }
        if (!Objects.equals(this.envioJV, other.envioJV)) {
            return false;
        }
        if (!Objects.equals(this.idTipoIngrediente, other.idTipoIngrediente)) {
            return false;
        }
        if (!Objects.equals(this.perfusionContinua, other.perfusionContinua)) {
            return false;
        }
        if (!Objects.equals(this.idUnidadConcentracion, other.idUnidadConcentracion)) {
            return false;
        }
        return true;
    }
    
}
