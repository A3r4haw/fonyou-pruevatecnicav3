package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 
 * @author gcruz
 *
 */
public class TipoCama implements Serializable {
	
    private static final long serialVersionUID = 1L;

    private int idTipoCama;
    private String nombreTipoCama;
    private int estatus;
    private String insertIdUsuario;
    private Date insertFecha;
    private String updateIdUsuario;
    private Date updateFecha;

    public TipoCama() {

    }

    public TipoCama(int idTipoCama, String nombreTipoCama, int estatus, String insertIdUsuario, Date insertFecha,
            String updateIdUsuario, Date updateFecha) {
        this.idTipoCama = idTipoCama;
        this.nombreTipoCama = nombreTipoCama;
        this.estatus = estatus;
        this.insertIdUsuario = insertIdUsuario;
        this.insertFecha = insertFecha;
        this.updateIdUsuario = updateIdUsuario;
        this.updateFecha = updateFecha;
    }

    public int getIdTipoCama() {
        return idTipoCama;
    }

    public void setIdTipoCama(int idTipoCama) {
        this.idTipoCama = idTipoCama;
    }

    public String getNombreTipoCama() {
        return nombreTipoCama;
    }

    public void setNombreTipoCama(String nombreTipoCama) {
        this.nombreTipoCama = nombreTipoCama;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.idTipoCama);
        hash = 89 * hash + Objects.hashCode(this.nombreTipoCama);
        hash = 89 * hash + Objects.hashCode(this.estatus);
        hash = 89 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 89 * hash + Objects.hashCode(this.insertFecha);
        hash = 89 * hash + Objects.hashCode(this.updateIdUsuario);
        hash = 89 * hash + Objects.hashCode(this.updateFecha);
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
        TipoCama other = (TipoCama) obj;
        if (estatus != other.estatus) {
            return false;
        }
        if (idTipoCama != other.idTipoCama) {
            return false;
        }
        if (insertFecha == null) {
            if (other.insertFecha != null) {
                return false;
            }
        } else if (!insertFecha.equals(other.insertFecha)) {
            return false;
        }
        if (insertIdUsuario == null) {
            if (other.insertIdUsuario != null) {
                return false;
            }
        } else if (!insertIdUsuario.equals(other.insertIdUsuario)) {
            return false;
        }
        if (nombreTipoCama == null) {
            if (other.nombreTipoCama != null) {
                return false;
            }
        } else if (!nombreTipoCama.equals(other.nombreTipoCama)) {
            return false;
        }
        if (updateFecha == null) {
            if (other.updateFecha != null) {
                return false;
            }
        } else if (!updateFecha.equals(other.updateFecha)) {
            return false;
        }
        if (updateIdUsuario == null) {
            if (other.updateIdUsuario != null) {
                return false;
            }
        } else if (!updateIdUsuario.equals(other.updateIdUsuario)) {
            return false;
        }
        return true;
    }

}
