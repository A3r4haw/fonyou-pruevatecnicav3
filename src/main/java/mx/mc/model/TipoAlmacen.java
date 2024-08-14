package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 
 * @author gcruz
 *
 */
public class TipoAlmacen implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idTipoAlmacen;
    private String nombreTipoAlmacen;
    private String area;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;
    
    public TipoAlmacen() {
    	
    }
    
	public TipoAlmacen(int idTipoAlmacen, String nombreTipoAlmacen, String area, Date insertFecha, String insertIdUsuario,
			Date updateFecha, String updateIdUsuario) {
		this.idTipoAlmacen = idTipoAlmacen;
		this.nombreTipoAlmacen = nombreTipoAlmacen;
                this.area=area;
		this.insertFecha = insertFecha;
		this.insertIdUsuario = insertIdUsuario;
		this.updateFecha = updateFecha;
		this.updateIdUsuario = updateIdUsuario;
	}

    @Override
    public String toString() {
        return "TipoAlmacen{" + "idTipoAlmacen=" + idTipoAlmacen + ", nombreTipoAlmacen=" + nombreTipoAlmacen +", area=" + area + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + '}';
    }

	public int getIdTipoAlmacen() {
		return idTipoAlmacen;
	}

	public void setIdTipoAlmacen(int idTipoAlmacen) {
		this.idTipoAlmacen = idTipoAlmacen;
	}

	public String getNombreTipoAlmacen() {
		return nombreTipoAlmacen;
	}

	public void setNombreTipoAlmacen(String nombreTipoAlmacen) {
		this.nombreTipoAlmacen = nombreTipoAlmacen;
	}

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + this.idTipoAlmacen;
        hash = 53 * hash + Objects.hashCode(this.nombreTipoAlmacen);
        hash = 53 * hash + Objects.hashCode(this.area);
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
        final TipoAlmacen other = (TipoAlmacen) obj;
        if (this.idTipoAlmacen != other.idTipoAlmacen) {
            return false;
        }
        if (!Objects.equals(this.nombreTipoAlmacen, other.nombreTipoAlmacen)) {
            return false;
        }
        if (!Objects.equals(this.area, other.area)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        return Objects.equals(this.updateFecha, other.updateFecha);
    }
}
