package mx.mc.model;

import java.io.Serializable;
import java.util.Objects;
/**
 * 
 * @author gcruz
 *
 */
public class DevolucionDetalle implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String idDevolucionDetalle;
	private String idDevolucion;
	private String idInsumo;
	private String idInventario;
	private Integer idMotivoDevolucion;
	private Integer cantidad;
	private Integer cantidadIngresar;
	private String comentario;
	
	public DevolucionDetalle() {
            //No code needed in constructor
	}

	public String getIdDevolucionDetalle() {
		return idDevolucionDetalle;
	}

	public void setIdDevolucionDetalle(String idDevolucionDetalle) {
		this.idDevolucionDetalle = idDevolucionDetalle;
	}

	public String getIdDevolucion() {
		return idDevolucion;
	}

	public void setIdDevolucion(String idDevolucion) {
		this.idDevolucion = idDevolucion;
	}

	public String getIdInsumo() {
		return idInsumo;
	}

	public void setIdInsumo(String idInsumo) {
		this.idInsumo = idInsumo;
	}

	public String getIdInventario() {
		return idInventario;
	}

	public void setIdInventario(String idInventario) {
		this.idInventario = idInventario;
	}

	public Integer getIdMotivoDevolucion() {
		return idMotivoDevolucion;
	}

	public void setIdMotivoDevolucion(Integer idMotivoDevolucion) {
		this.idMotivoDevolucion = idMotivoDevolucion;
	}
	
	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Integer getCantidadIngresar() {
		return cantidadIngresar;
	}

	public void setCantidadIngresar(Integer cantidadIngresar) {
		this.cantidadIngresar = cantidadIngresar;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	@Override
	public int hashCode() {
		int hash = 7;
	    hash = 79 * hash + Objects.hashCode(this.cantidad);
	    hash = 79 * hash + Objects.hashCode(this.comentario);
	    hash = 79 * hash + Objects.hashCode(this.idDevolucion);
	    hash = 79 * hash + Objects.hashCode(this.idDevolucionDetalle);
	    hash = 79 * hash + Objects.hashCode(this.idInsumo);
	    hash = 79 * hash + Objects.hashCode(this.idInventario);
	    hash = 79 * hash + Objects.hashCode(this.idMotivoDevolucion);
	    hash = 79 * hash + Objects.hashCode(this.cantidad);
	    hash = 79 * hash + Objects.hashCode(this.comentario);
	    hash = 79 * hash + Objects.hashCode(this.cantidadIngresar);
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
        DevolucionDetalle other = (DevolucionDetalle) obj;
        if (cantidad == null) {
            if (other.cantidad != null) {
                return false;
            }
        } else if (!cantidad.equals(other.cantidad)) {
            return false;
        }
        if (cantidadIngresar == null) {
            if (other.cantidadIngresar != null) {
                return false;
            }
        } else if (!cantidadIngresar.equals(other.cantidadIngresar)) {
            return false;
        }
        if (comentario == null) {
            if (other.comentario != null) {
                return false;
            }
        } else if (!comentario.equals(other.comentario)) {
            return false;
        }
        if (idDevolucion == null) {
            if (other.idDevolucion != null) {
                return false;
            }
        } else if (!idDevolucion.equals(other.idDevolucion)) {
            return false;
        }
        if (idDevolucionDetalle == null) {
            if (other.idDevolucionDetalle != null) {
                return false;
            }
        } else if (!idDevolucionDetalle.equals(other.idDevolucionDetalle)) {
            return false;
        }
        if (idInsumo == null) {
            if (other.idInsumo != null) {
                return false;
            }
        } else if (!idInsumo.equals(other.idInsumo)) {
            return false;
        }
        if (idInventario == null) {
            if (other.idInventario != null) {
                return false;
            }
        } else if (!idInventario.equals(other.idInventario)) {
            return false;
        }
        if (idMotivoDevolucion == null) {
            if (other.idMotivoDevolucion != null) {
                return false;
            }
        } else if (!idMotivoDevolucion.equals(other.idMotivoDevolucion)) {
            return false;
        }
        return true;
    }

}
