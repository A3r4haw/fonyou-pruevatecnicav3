package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 
 * @author gcruz
 *
 */
public class Devolucion implements Serializable {
	
	private static final long serialVersionUID = 1L;
	 
	private String idDevolucion;
	private String folio;
	private String idAlmacenOrigen;
	private String idAlmacenDestino;
	private Date fechaDevolucion;
	private String idUsrDevolucion;
	private Integer idEstatusDevolucion;	
	
	public Devolucion() {
		
	}

	public Devolucion(String idDevolucion, String folio, String idAlmacenOrigen, String idAlmacenDestino,
			Date fechaDevolucion, String idUsrDevolucion, Integer idEstatusDevolucion) {
		super();
		this.idDevolucion = idDevolucion;
		this.folio = folio;
		this.idAlmacenOrigen = idAlmacenOrigen;
		this.idAlmacenDestino = idAlmacenDestino;
		this.fechaDevolucion = fechaDevolucion;
		this.idUsrDevolucion = idUsrDevolucion;
		this.idEstatusDevolucion = idEstatusDevolucion;
	}

	public String getIdDevolucion() {
		return idDevolucion;
	}

	public void setIdDevolucion(String idDevolucion) {
		this.idDevolucion = idDevolucion;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getIdAlmacenOrigen() {
		return idAlmacenOrigen;
	}

	public void setIdAlmacenOrigen(String idAlmacenOrigen) {
		this.idAlmacenOrigen = idAlmacenOrigen;
	}

	public String getIdAlmacenDestino() {
		return idAlmacenDestino;
	}

	public void setIdAlmacenDestino(String idAlmacenDestino) {
		this.idAlmacenDestino = idAlmacenDestino;
	}

	public Date getFechaDevolucion() {
		return fechaDevolucion;
	}

	public void setFechaDevolucion(Date fechaDevolucion) {
		this.fechaDevolucion = fechaDevolucion;
	}

	public String getIdUsrDevolucion() {
		return idUsrDevolucion;
	}

	public void setIdUsrDevolucion(String idUsrDevolucion) {
		this.idUsrDevolucion = idUsrDevolucion;
	}

	public Integer getIdEstatusDevolucion() {
		return idEstatusDevolucion;
	}

	public void setIdEstatusDevolucion(Integer idEstatusDevolucion) {
		this.idEstatusDevolucion = idEstatusDevolucion;
	}	

	@Override
	public int hashCode() {
		int hash = 7;
	    hash = 79 * hash + Objects.hashCode(this.fechaDevolucion);
	    hash = 79 * hash + Objects.hashCode(this.folio);
	    hash = 79 * hash + Objects.hashCode(this.idAlmacenDestino);
	    hash = 79 * hash + Objects.hashCode(this.idAlmacenOrigen);
	    hash = 79 * hash + Objects.hashCode(this.idDevolucion);
	    hash = 79 * hash + Objects.hashCode(this.idEstatusDevolucion);
	    hash = 79 * hash + Objects.hashCode(this.idUsrDevolucion);
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
        Devolucion other = (Devolucion) obj;
        if (fechaDevolucion == null) {
            if (other.fechaDevolucion != null) {
                return false;
            }
        } else if (!fechaDevolucion.equals(other.fechaDevolucion)) {
            return false;
        }
        if (folio == null) {
            if (other.folio != null) {
                return false;
            }
        } else if (!folio.equals(other.folio)) {
            return false;
        }
        if (idAlmacenDestino == null) {
            if (other.idAlmacenDestino != null) {
                return false;
            }
        } else if (!idAlmacenDestino.equals(other.idAlmacenDestino)) {
            return false;
        }
        if (idAlmacenOrigen == null) {
            if (other.idAlmacenOrigen != null) {
                return false;
            }
        } else if (!idAlmacenOrigen.equals(other.idAlmacenOrigen)) {
            return false;
        }
        if (idDevolucion == null) {
            if (other.idDevolucion != null) {
                return false;
            }
        } else if (!idDevolucion.equals(other.idDevolucion)) {
            return false;
        }
        if (idEstatusDevolucion == null) {
            if (other.idEstatusDevolucion != null) {
                return false;
            }
        } else if (!idEstatusDevolucion.equals(other.idEstatusDevolucion)) {
            return false;
        }
        if (idUsrDevolucion == null) {
            if (other.idUsrDevolucion != null) {
                return false;
            }
        } else if (!idUsrDevolucion.equals(other.idUsrDevolucion)) {
            return false;
        }
        return true;
    }
}
