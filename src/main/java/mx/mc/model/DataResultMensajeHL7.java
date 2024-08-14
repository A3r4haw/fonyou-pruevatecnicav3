package mx.mc.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author gcruz
 *
 */
public class DataResultMensajeHL7 implements Serializable {
	private static final long serialVersionUID = 1L;
        
	private String idPaciente;
	private String servicio;
	private String cama;
	private String tipoMensaje;
	private String idMensaje;
	private String origen;
	private String destino;
	private String estatus;
	private Date fechaRegistro;
	private String ingresoMensaje;
        private String visita;
        private String tipoMedicacion;
        private String idMedicacion;
        private String claveInstitucional;
        private String nombreCorto;
        private String frecuencia;
        private String duracion;
        private String concentracion;
        private String multidosis;
        private String aditivo;
        private String nombrePaciente;
	
	public String getCama() {
		return cama;
	}
	
	public void setCama(String cama) {
		this.cama = cama;
	}

	public String getIdPaciente() {
		return idPaciente;
	}

	public void setIdPaciente(String idPaciente) {
		this.idPaciente = idPaciente;
	}

	public String getServicio() {
		return servicio;
	}

	public void setServicio(String servicio) {
		this.servicio = servicio;
	}

	public String getTipoMensaje() {
		return tipoMensaje;
	}

	public void setTipoMensaje(String tipoMensaje) {
		this.tipoMensaje = tipoMensaje;
	}

	public String getIdMensaje() {
		return idMensaje;
	}

	public void setIdMensaje(String idMensaje) {
		this.idMensaje = idMensaje;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getIngresoMensaje() {
		return ingresoMensaje;
	}

	public void setIngresoMensaje(String ingresoMensaje) {
		this.ingresoMensaje = ingresoMensaje;
	}

    public String getVisita() {
        return visita;
    }

    public void setVisita(String visita) {
        this.visita = visita;
    }

    public String getTipoMedicacion() {
        return tipoMedicacion;
    }

    public void setTipoMedicacion(String tipoMedicacion) {
        this.tipoMedicacion = tipoMedicacion;
    }

    public String getIdMedicacion() {
        return idMedicacion;
    }

    public void setIdMedicacion(String idMedicacion) {
        this.idMedicacion = idMedicacion;
    }

    public String getClaveInstitucional() {
        return claveInstitucional;
    }

    public void setClaveInstitucional(String claveInstitucional) {
        this.claveInstitucional = claveInstitucional;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getConcentracion() {
        return concentracion;
    }

    public void setConcentracion(String concentracion) {
        this.concentracion = concentracion;
    }

    public String getMultidosis() {
        return multidosis;
    }

    public void setMultidosis(String multidosis) {
        this.multidosis = multidosis;
    }

    public String getAditivo() {
        return aditivo;
    }

    public void setAditivo(String aditivo) {
        this.aditivo = aditivo;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }
	
}
