package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author AORTIZ
 */
public class CatalogoGeneral implements Serializable {
    private static final long serialVersionUID = 1L;

    private int idCatalogoGeneral;
    private String nombreCatalogo;
    private int estatus;
    private int idGrupo;
    private String insertIdUsuario;
    private Date insertFecha;
    private String updateIdUsuario;
    private Date updateFecha;
    private String clave;
    private int orden;
    private String condicion;

    public CatalogoGeneral() {
    }

    @Override
    public String toString() {
        return "CatalogoGeneral{" + "idCatalogoGeneral=" + idCatalogoGeneral + ", nombreCatalogo=" + nombreCatalogo + ", estatus=" + estatus + ", idGrupo=" + idGrupo + ", insertIdUsuario=" + insertIdUsuario + ", insertFecha=" + insertFecha + ", updateIdUsuario=" + updateIdUsuario + ", updateFecha=" + updateFecha + ", clave=" + clave + ", orden=" + orden + ", condicion=" + condicion + '}';
    }
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.idCatalogoGeneral;
        hash = 89 * hash + Objects.hashCode(this.nombreCatalogo);
        hash = 89 * hash + this.estatus;
        hash = 89 * hash + this.idGrupo;
        hash = 89 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 89 * hash + Objects.hashCode(this.insertFecha);
        hash = 89 * hash + Objects.hashCode(this.updateIdUsuario);
        hash = 89 * hash + Objects.hashCode(this.updateFecha);
        hash = 89 * hash + Objects.hashCode(this.clave);
        hash = 89 * hash + this.orden;
        hash = 89 * hash + Objects.hashCode(this.condicion);
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
        final CatalogoGeneral other = (CatalogoGeneral) obj;
        if (this.idCatalogoGeneral != other.idCatalogoGeneral) {
            return false;
        }
        if (this.estatus != other.estatus) {
            return false;
        }
        if (this.idGrupo != other.idGrupo) {
            return false;
        }
        if (this.orden != other.orden) {
            return false;
        }
        if (!Objects.equals(this.nombreCatalogo, other.nombreCatalogo)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.clave, other.clave)) {
            return false;
        }
        if (!Objects.equals(this.condicion, other.condicion)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        if (!Objects.equals(this.updateFecha, other.updateFecha)) {
            return false;
        }
        return true;
    }

    public int getIdCatalogoGeneral() {
        return idCatalogoGeneral;
    }

    public void setIdCatalogoGeneral(int idCatalogoGeneral) {
        this.idCatalogoGeneral = idCatalogoGeneral;
    }

    public String getNombreCatalogo() {
        return nombreCatalogo;
    }

    public void setNombreCatalogo(String nombreCatalogo) {
        this.nombreCatalogo = nombreCatalogo;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getInsertIdUsuario() {
        return insertIdUsuario;
    }

    public void setInsertIdUsuario(String insertIdUsuario) {
        this.insertIdUsuario = insertIdUsuario;
    }

    public Date getInsertFecha() {
        return insertFecha;
    }

    public void setInsertFecha(Date insertFecha) {
        this.insertFecha = insertFecha;
    }

    public String getUpdateIdUsuario() {
        return updateIdUsuario;
    }

    public void setUpdateIdUsuario(String updateIdUsuario) {
        this.updateIdUsuario = updateIdUsuario;
    }

    public Date getUpdateFecha() {
        return updateFecha;
    }

    public void setUpdateFecha(Date updateFecha) {
        this.updateFecha = updateFecha;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

   
}
