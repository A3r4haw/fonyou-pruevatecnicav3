package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author AORTIZ
 */
public class OrdenCompra implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String idOrdenCompra;
    private String idReabasto;
    private String folioOrdenCompra;
    private String idProveedor;
    private int idEstatusOrdenCompra;
    private Date fecha;
    private String idEstructura;
    private Integer idTipoOrigen;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;


    public OrdenCompra() {
        //No code needed in constructor
    }
    
    public String getIdOrdenCompra() {
        return idOrdenCompra;
    }

    public void setIdOrdenCompra(String idOrdenCompra) {
        this.idOrdenCompra = idOrdenCompra;
    }

    public String getIdReabasto() {
        return idReabasto;
    }

    public void setIdReabasto(String idReabasto) {
        this.idReabasto = idReabasto;
    }

    public String getFolioOrdenCompra() {
        return folioOrdenCompra;
    }

    public void setFolioOrdenCompra(String folioOrdenCompra) {
        this.folioOrdenCompra = folioOrdenCompra;
    }

    public String getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(String idProveedor) {
        this.idProveedor = idProveedor;
    }

    public int getIdEstatusOrdenCompra() {
        return idEstatusOrdenCompra;
    }

    public void setIdEstatusOrdenCompra(int idEstatusOrdenCompra) {
        this.idEstatusOrdenCompra = idEstatusOrdenCompra;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public Integer getIdTipoOrigen() {
        return idTipoOrigen;
    }

    public void setIdTipoOrigen(Integer idTipoOrigen) {
        this.idTipoOrigen = idTipoOrigen;
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
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.idOrdenCompra);
        hash = 59 * hash + Objects.hashCode(this.idReabasto);
        hash = 59 * hash + Objects.hashCode(this.folioOrdenCompra);
        hash = 59 * hash + Objects.hashCode(this.idProveedor);
        hash = 59 * hash + this.idEstatusOrdenCompra;
        hash = 59 * hash + Objects.hashCode(this.fecha);
        hash = 59 * hash + Objects.hashCode(this.idEstructura);
        hash = 59 * hash + this.idTipoOrigen;
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
        final OrdenCompra other = (OrdenCompra) obj;
        if (this.idEstatusOrdenCompra != other.idEstatusOrdenCompra) {
            return false;
        }
        if (!Objects.equals(this.idOrdenCompra, other.idOrdenCompra)) {
            return false;
        }
        if (!Objects.equals(this.idReabasto, other.idReabasto)) {
            return false;
        }
        if (!Objects.equals(this.folioOrdenCompra, other.folioOrdenCompra)) {
            return false;
        }
        if (!Objects.equals(this.idProveedor, other.idProveedor)) {
            return false;
        }
        if (!Objects.equals(this.idEstructura, other.idEstructura)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.fecha, other.fecha)) {
            return false;
        }
        if (!Objects.equals(this.idTipoOrigen, other.idTipoOrigen)) {
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
}
