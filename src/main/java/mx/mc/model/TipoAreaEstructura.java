package mx.mc.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * 
 * @author gcruz
 *
 */
public class TipoAreaEstructura implements Serializable {

	private static final long serialVersionUID = 1L;

	private int idTipoAreaEstructura;
	private String nombreArea;
	private String area;
        
	public TipoAreaEstructura() {
		
	}

	public TipoAreaEstructura(int idTipoAreaEstructura, String nombreArea,String area) {
		super();
		this.idTipoAreaEstructura = idTipoAreaEstructura;
		this.nombreArea = nombreArea;
                this.area=area;
	}

	public int getIdTipoAreaEstructura() {
		return idTipoAreaEstructura;
	}

	public void setIdTipoAreaEstructura(int idTipoAreaEstructura) {
		this.idTipoAreaEstructura = idTipoAreaEstructura;
	}

	public String getNombreArea() {
		return nombreArea;
	}

	public void setNombreArea(String nombreArea) {
		this.nombreArea = nombreArea;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

	@Override
	public String toString() {
		return "TipoAreaEstructura {idTipoAreaEstructura=" + idTipoAreaEstructura + ", nombreArea=" + nombreArea + ", area=" + area + "}";
	}

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.idTipoAreaEstructura;
        hash = 29 * hash + Objects.hashCode(this.nombreArea);
        hash = 29 * hash + Objects.hashCode(this.area);
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
        final TipoAreaEstructura other = (TipoAreaEstructura) obj;
        if (this.idTipoAreaEstructura != other.idTipoAreaEstructura) {
            return false;
        }
        if (!Objects.equals(this.nombreArea, other.nombreArea)) {
            return false;
        }
        return Objects.equals(this.area, other.area);
    }
}
