package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 
 * @author gcruz
 *
 */
public class MensajeHL7 implements Serializable {
    private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String idMensaje;
	private String servicio;
	private String cama;
	private Date fechaCreacion;
	private String mensaje;
	
	public MensajeHL7() {
		
	}
	
	public MensajeHL7(Integer id, String idMensaje, String servicio, String cama, Date fechaCreacion, String mensaje) {
		super();
		this.id = id;
		this.idMensaje = idMensaje;
		this.servicio = servicio;
		this.cama = cama;
		this.fechaCreacion = fechaCreacion;
		this.mensaje = mensaje;
	}
	
	@Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.id);
        hash = 89 * hash + Objects.hashCode(this.idMensaje);
        hash = 89 * hash + Objects.hashCode(this.servicio);
        hash = 89 * hash + Objects.hashCode(this.cama);
        hash = 89 * hash + Objects.hashCode(this.fechaCreacion);
        hash = 89 * hash + Objects.hashCode(this.mensaje);
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
        final MensajeHL7 other = (MensajeHL7) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.idMensaje, other.idMensaje)) {
            return false;
        }
        if (!Objects.equals(this.servicio, other.servicio)) {
            return false;
        }
        if (!Objects.equals(this.cama, other.cama)) {
            return false;
        }
        if (!Objects.equals(this.fechaCreacion, other.fechaCreacion)) {
            return false;
        }
        return Objects.equals(this.mensaje, other.mensaje);
    }

	@Override
	public String toString() {
		return "MensajeHL7 [id=" + id + ", idMensaje=" + idMensaje + ", servicio=" + servicio + ", cama=" + cama
				+ ", fechaCreacion=" + fechaCreacion + ", mensaje=" + mensaje + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIdMensaje() {
		return idMensaje;
	}

	public void setIdMensaje(String idMensaje) {
		this.idMensaje = idMensaje;
	}

	public String getServicio() {
		return servicio;
	}

	public void setServicio(String servicio) {
		this.servicio = servicio;
	}

	public String getCama() {
		return cama;
	}

	public void setCama(String cama) {
		this.cama = cama;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}	
	
}
