package mx.mc.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * 
 * @author gcruz
 *
 */
public class EstructuraAlmacenServicio implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idAlmacenServicio;
    private String idEstructuraAlmacen;
    private String idEstructuraServicio;
    private Integer prioridadSurtir;

    public EstructuraAlmacenServicio(String idEstructuraAlmacen, String idEstructuraServicio) {
        this.idEstructuraAlmacen = idEstructuraAlmacen;
        this.idEstructuraServicio = idEstructuraServicio;
    }

    public EstructuraAlmacenServicio(String idAlmacenServicio, String idEstructuraAlmacen, String idEstructuraServicio) {
        this.idAlmacenServicio = idAlmacenServicio;
        this.idEstructuraAlmacen = idEstructuraAlmacen;
        this.idEstructuraServicio = idEstructuraServicio;
    }        
    
    public EstructuraAlmacenServicio() {

    }

    public String getIdAlmacenServicio() {
        return idAlmacenServicio;
    }

    public void setIdAlmacenServicio(String idAlmacenServicio) {
        this.idAlmacenServicio = idAlmacenServicio;
    }

    public String getIdEstructuraAlmacen() {
        return idEstructuraAlmacen;
    }

    public void setIdEstructuraAlmacen(String idEstructuraAlmacen) {
        this.idEstructuraAlmacen = idEstructuraAlmacen;
    }

    public String getIdEstructuraServicio() {
        return idEstructuraServicio;
    }

    public void setIdEstructuraServicio(String idEstructuraServicio) {
        this.idEstructuraServicio = idEstructuraServicio;
    }

    public Integer getPrioridadSurtir() {
        return prioridadSurtir;
    }

    public void setPrioridadSurtir(Integer prioridadSurtir) {
        this.prioridadSurtir = prioridadSurtir;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.idAlmacenServicio);
        hash = 97 * hash + Objects.hashCode(this.idEstructuraAlmacen);
        hash = 97 * hash + Objects.hashCode(this.idEstructuraServicio);
        hash = 97 * hash + Objects.hashCode(this.prioridadSurtir);
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
        final EstructuraAlmacenServicio other = (EstructuraAlmacenServicio) obj;
        if (!Objects.equals(this.idAlmacenServicio, other.idAlmacenServicio)) {
            return false;
        }
        if (!Objects.equals(this.idEstructuraAlmacen, other.idEstructuraAlmacen)) {
            return false;
        }
        if (!Objects.equals(this.idEstructuraServicio, other.idEstructuraServicio)) {
            return false;
        }
        if (!Objects.equals(this.prioridadSurtir, other.prioridadSurtir)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EstructuraAlmacenServicio{" + "idAlmacenServicio=" + idAlmacenServicio + ", idEstructuraAlmacen=" + idEstructuraAlmacen + ", idEstructuraServicio=" + idEstructuraServicio + ", prioridadSurtir=" + prioridadSurtir + '}';
    }

    
       
}
