package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Cervanets
 */
public class NotaDispenColectDetalle implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idNotaDispenColectDetalle;
    private String idNotaDispenColect;
    private String idSurtimiento;
    private String idSolucion;
    private Integer numeroMezcla;
    private String comentarios;
    private Integer idContenedor;
    private Integer idEstatusDispenColect;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;

    public NotaDispenColectDetalle() {
    }

    public NotaDispenColectDetalle(String idNotaDispenColectDetalle) {
        this.idNotaDispenColectDetalle = idNotaDispenColectDetalle;
    }

    public NotaDispenColectDetalle(String idNotaDispenColectDetalle, String idNotaDispenColect, String idSurtimiento, String idSolucion, Integer numeroMezcla, String comentarios, Integer idContenedor, Integer idEstatusDispenColect, Date insertFecha, String insertIdUsuario, Date updateFecha, String updateIdUsuario) {
        this.idNotaDispenColectDetalle = idNotaDispenColectDetalle;
        this.idNotaDispenColect = idNotaDispenColect;
        this.idSurtimiento = idSurtimiento;
        this.idSolucion = idSolucion;
        this.numeroMezcla = numeroMezcla;
        this.comentarios = comentarios;
        this.idContenedor = idContenedor;
        this.idEstatusDispenColect = idEstatusDispenColect;
        this.insertFecha = insertFecha;
        this.insertIdUsuario = insertIdUsuario;
        this.updateFecha = updateFecha;
        this.updateIdUsuario = updateIdUsuario;
    }

    public String getIdNotaDispenColectDetalle() {
        return idNotaDispenColectDetalle;
    }

    public void setIdNotaDispenColectDetalle(String idNotaDispenColectDetalle) {
        this.idNotaDispenColectDetalle = idNotaDispenColectDetalle;
    }

    public String getIdNotaDispenColect() {
        return idNotaDispenColect;
    }

    public void setIdNotaDispenColect(String idNotaDispenColect) {
        this.idNotaDispenColect = idNotaDispenColect;
    }

    public String getIdSurtimiento() {
        return idSurtimiento;
    }

    public void setIdSurtimiento(String idSurtimiento) {
        this.idSurtimiento = idSurtimiento;
    }

    public String getIdSolucion() {
        return idSolucion;
    }

    public void setIdSolucion(String idSolucion) {
        this.idSolucion = idSolucion;
    }

    public Integer getNumeroMezcla() {
        return numeroMezcla;
    }

    public void setNumeroMezcla(Integer numeroMezcla) {
        this.numeroMezcla = numeroMezcla;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Integer getIdContenedor() {
        return idContenedor;
    }

    public void setIdContenedor(Integer idContenedor) {
        this.idContenedor = idContenedor;
    }

    public Integer getIdEstatusDispenColect() {
        return idEstatusDispenColect;
    }

    public void setIdEstatusDispenColect(Integer idEstatusDispenColect) {
        this.idEstatusDispenColect = idEstatusDispenColect;
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
        return "NotaDispenColectDetalle{" + "idNotaDispenColectDetalle=" + idNotaDispenColectDetalle + ", idNotaDispenColect=" + idNotaDispenColect + ", idSurtimiento=" + idSurtimiento + ", idSolucion=" + idSolucion + ", numeroMezcla=" + numeroMezcla + ", comentarios=" + comentarios + ", idContenedor=" + idContenedor + ", idEstatusDispenColect=" + idEstatusDispenColect + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.idNotaDispenColectDetalle);
        hash = 29 * hash + Objects.hashCode(this.idNotaDispenColect);
        hash = 29 * hash + Objects.hashCode(this.idSurtimiento);
        hash = 29 * hash + Objects.hashCode(this.idSolucion);
        hash = 29 * hash + Objects.hashCode(this.numeroMezcla);
        hash = 29 * hash + Objects.hashCode(this.comentarios);
        hash = 29 * hash + Objects.hashCode(this.idContenedor);
        hash = 29 * hash + Objects.hashCode(this.idEstatusDispenColect);
        hash = 29 * hash + Objects.hashCode(this.insertFecha);
        hash = 29 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 29 * hash + Objects.hashCode(this.updateFecha);
        hash = 29 * hash + Objects.hashCode(this.updateIdUsuario);
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
        final NotaDispenColectDetalle other = (NotaDispenColectDetalle) obj;
        if (!Objects.equals(this.idNotaDispenColectDetalle, other.idNotaDispenColectDetalle)) {
            return false;
        }
        if (!Objects.equals(this.idNotaDispenColect, other.idNotaDispenColect)) {
            return false;
        }
        if (!Objects.equals(this.idSurtimiento, other.idSurtimiento)) {
            return false;
        }
        if (!Objects.equals(this.idSolucion, other.idSolucion)) {
            return false;
        }
        if (!Objects.equals(this.comentarios, other.comentarios)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.numeroMezcla, other.numeroMezcla)) {
            return false;
        }
        if (!Objects.equals(this.idContenedor, other.idContenedor)) {
            return false;
        }
        if (!Objects.equals(this.idEstatusDispenColect, other.idEstatusDispenColect)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        return Objects.equals(this.updateFecha, other.updateFecha);
    }
    
    

}
