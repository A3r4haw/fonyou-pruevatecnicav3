package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author hramirez
 */
public class NotificacionLeida implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idNotificacionLeida;
    private String idNotificacion;
    private Date fecha;
    private String idUsuario;

    public NotificacionLeida() {
    }

    public NotificacionLeida(String idNotificacionLeida, String idNotificacion, Date fecha, String idUsuario) {
        this.idNotificacionLeida = idNotificacionLeida;
        this.idNotificacion = idNotificacion;
        this.fecha = fecha;
        this.idUsuario = idUsuario;
    }

    public String getIdNotificacionLeida() {
        return idNotificacionLeida;
    }

    public void setIdNotificacionLeida(String idNotificacionLeida) {
        this.idNotificacionLeida = idNotificacionLeida;
    }

    public String getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(String idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public String toString() {
        return "NotificacionLeida{" + "idNotificacionLeida=" + idNotificacionLeida + ", idNotificacion=" + idNotificacion + ", fecha=" + fecha + ", idUsuario=" + idUsuario + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + Objects.hashCode(this.idNotificacionLeida);
        hash = 43 * hash + Objects.hashCode(this.idNotificacion);
        hash = 43 * hash + Objects.hashCode(this.fecha);
        hash = 43 * hash + Objects.hashCode(this.idUsuario);
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
        final NotificacionLeida other = (NotificacionLeida) obj;
        if (!Objects.equals(this.idNotificacionLeida, other.idNotificacionLeida)) {
            return false;
        }
        if (!Objects.equals(this.idNotificacion, other.idNotificacion)) {
            return false;
        }
        if (!Objects.equals(this.idUsuario, other.idUsuario)) {
            return false;
        }
        return Objects.equals(this.fecha, other.fecha);
    }
}
