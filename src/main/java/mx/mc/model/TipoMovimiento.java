package mx.mc.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author gcruz
 *
 */
public class TipoMovimiento implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer idTipoMovimiento;
    private String descripcion;
    private String tipoMovimiento;
    private Integer activo;

    public TipoMovimiento() {

    }

    public TipoMovimiento(Integer idTipoMovimiento) {
        this.idTipoMovimiento = idTipoMovimiento;
    }

    public TipoMovimiento(Integer idTipoMovimiento, String descripcion, String tipoMovimiento, Integer activo) {
        this.idTipoMovimiento = idTipoMovimiento;
        this.descripcion = descripcion;
        this.tipoMovimiento = tipoMovimiento;
        this.activo = activo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.idTipoMovimiento);
        hash = 89 * hash + Objects.hashCode(this.descripcion);
        hash = 89 * hash + Objects.hashCode(this.tipoMovimiento);
        hash = 89 * hash + Objects.hashCode(this.activo);
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
        final TipoMovimiento other = (TipoMovimiento) obj;
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        if (!Objects.equals(this.tipoMovimiento, other.tipoMovimiento)) {
            return false;
        }
        if (!Objects.equals(this.idTipoMovimiento, other.idTipoMovimiento)) {
            return false;
        }
        return Objects.equals(this.activo, other.activo);
    }

    @Override
    public String toString() {
        return "TipoMovimiento [idTipoMovimiento=" + idTipoMovimiento + ", descripcion=" + descripcion
                + ", tipoMovimiento=" + tipoMovimiento + ", activo=" + activo + "]";
    }

    public Integer getIdTipoMovimiento() {
        return idTipoMovimiento;
    }

    public void setIdTipoMovimiento(Integer idTipoMovimiento) {
        this.idTipoMovimiento = idTipoMovimiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }

}
