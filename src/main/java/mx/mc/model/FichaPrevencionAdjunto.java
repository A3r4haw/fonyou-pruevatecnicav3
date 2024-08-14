package mx.mc.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Cervanets
 */
public class FichaPrevencionAdjunto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idFichaPrevencion;
    private Integer idAdjunto;

    public FichaPrevencionAdjunto() {
    }

    public FichaPrevencionAdjunto(String idFichaPrevencion, Integer idAdjunto) {
        this.idFichaPrevencion = idFichaPrevencion;
        this.idAdjunto = idAdjunto;
    }

    public String getIdFichaPrevencion() {
        return idFichaPrevencion;
    }

    public void setIdFichaPrevencion(String idFichaPrevencion) {
        this.idFichaPrevencion = idFichaPrevencion;
    }

    public Integer getIdAdjunto() {
        return idAdjunto;
    }

    public void setIdAdjunto(Integer idAdjunto) {
        this.idAdjunto = idAdjunto;
    }

    @Override
    public String toString() {
        return "FichaPrevencionAdjunto{" + "idFichaPrevencion=" + idFichaPrevencion + ", idAdjunto=" + idAdjunto + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.idFichaPrevencion);
        hash = 59 * hash + Objects.hashCode(this.idAdjunto);
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
        final FichaPrevencionAdjunto other = (FichaPrevencionAdjunto) obj;
        if (!Objects.equals(this.idFichaPrevencion, other.idFichaPrevencion)) {
            return false;
        }
        return Objects.equals(this.idAdjunto, other.idAdjunto);
    }

}
