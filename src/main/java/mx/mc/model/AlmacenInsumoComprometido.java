package mx.mc.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author hramirez
 */
public class AlmacenInsumoComprometido implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idEstructura;
    private String idInsumo;
    private String idPrescripcion;
    private Integer cantidadComprometida;

    public AlmacenInsumoComprometido() {
    }

    public AlmacenInsumoComprometido(String idEstructura, String idInsumo, String idPrescripcion, Integer cantidadComprometida) {
        this.idEstructura = idEstructura;
        this.idInsumo = idInsumo;
        this.idPrescripcion = idPrescripcion;
        this.cantidadComprometida = cantidadComprometida;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public String getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(String idInsumo) {
        this.idInsumo = idInsumo;
    }

    public String getIdPrescripcion() {
        return idPrescripcion;
    }

    public void setIdPrescripcion(String idPrescripcion) {
        this.idPrescripcion = idPrescripcion;
    }

    public Integer getCantidadComprometida() {
        return cantidadComprometida;
    }

    public void setCantidadComprometida(Integer cantidadComprometida) {
        this.cantidadComprometida = cantidadComprometida;
    }

    @Override
    public String toString() {
        return "AlmacenInsumoComprometido{" + "idEstructura=" + idEstructura + ", idInsumo=" + idInsumo + ", cantidadComprometida=" + cantidadComprometida + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.idEstructura);
        hash = 53 * hash + Objects.hashCode(this.idInsumo);
        hash = 53 * hash + Objects.hashCode(this.cantidadComprometida);
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
        final AlmacenInsumoComprometido other = (AlmacenInsumoComprometido) obj;
        if (!Objects.equals(this.idEstructura, other.idEstructura)) {
            return false;
        }
        if (!Objects.equals(this.idInsumo, other.idInsumo)) {
            return false;
        }
        return Objects.equals(this.cantidadComprometida, other.cantidadComprometida);
    }

}
