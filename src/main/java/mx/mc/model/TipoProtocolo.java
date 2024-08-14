package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Cervanets
 */
public class TipoProtocolo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idTipoProtocolo;
    private String nombreTipoProtocolo;
    private Integer idEstatus;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;

    public TipoProtocolo() {
    }

    public TipoProtocolo(Integer idTipoProtocolo) {
        this.idTipoProtocolo = idTipoProtocolo;
    }

    public Integer getIdTipoProtocolo() {
        return idTipoProtocolo;
    }

    public void setIdTipoProtocolo(Integer idTipoProtocolo) {
        this.idTipoProtocolo = idTipoProtocolo;
    }

    public String getNombreTipoProtocolo() {
        return nombreTipoProtocolo;
    }

    public void setNombreTipoProtocolo(String nombreTipoProtocolo) {
        this.nombreTipoProtocolo = nombreTipoProtocolo;
    }

    public Integer getIdEstatus() {
        return idEstatus;
    }

    public void setIdEstatus(Integer idEstatus) {
        this.idEstatus = idEstatus;
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
    public String toString() {
        return "TipoProtocolo{" + "idTipoProtocolo=" + idTipoProtocolo + ", nombreTipoProtocolo=" + nombreTipoProtocolo + ", idEstatus=" + idEstatus + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.idTipoProtocolo);
        hash = 59 * hash + Objects.hashCode(this.nombreTipoProtocolo);
        hash = 59 * hash + Objects.hashCode(this.idEstatus);
        hash = 59 * hash + Objects.hashCode(this.insertFecha);
        hash = 59 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 59 * hash + Objects.hashCode(this.updateFecha);
        hash = 59 * hash + Objects.hashCode(this.updateIdUsuario);
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
        final TipoProtocolo other = (TipoProtocolo) obj;
        if (!Objects.equals(this.nombreTipoProtocolo, other.nombreTipoProtocolo)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.idTipoProtocolo, other.idTipoProtocolo)) {
            return false;
        }
        if (!Objects.equals(this.idEstatus, other.idEstatus)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        return Objects.equals(this.updateFecha, other.updateFecha);
    }
    
    
}
