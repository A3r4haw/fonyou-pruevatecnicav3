package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author hramirez
 */
public class Notificacion implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idNotificacion;
    private String idTransaccion;
    private Date fecha;
    private Integer idTipoNotificacion;
    private String resumen;
    private String descripcion;

    public Notificacion() {
    }

    public Notificacion(String idNotificacion, String idTransaccion, Date fecha, Integer idTipoNotificacion, String resumen, String descripcion) {
        this.idNotificacion = idNotificacion;
        this.idTransaccion = idTransaccion;
        this.fecha = fecha;
        this.idTipoNotificacion = idTipoNotificacion;
        this.resumen = resumen;
        this.descripcion = descripcion;
    }

    public String getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(String idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    public String getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(String idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getIdTipoNotificacion() {
        return idTipoNotificacion;
    }

    public void setIdTipoNotificacion(Integer idTipoNotificacion) {
        this.idTipoNotificacion = idTipoNotificacion;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Notificacion{" + "idNotificacion=" + idNotificacion + ", idTransaccion=" + idTransaccion + ", fecha=" + fecha + ", idTipoNotificacion=" + idTipoNotificacion + ", resumen=" + resumen + ", descripcion=" + descripcion + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.idNotificacion);
        hash = 67 * hash + Objects.hashCode(this.idTransaccion);
        hash = 67 * hash + Objects.hashCode(this.fecha);
        hash = 67 * hash + Objects.hashCode(this.idTipoNotificacion);
        hash = 67 * hash + Objects.hashCode(this.resumen);
        hash = 67 * hash + Objects.hashCode(this.descripcion);
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
        final Notificacion other = (Notificacion) obj;
        if (!Objects.equals(this.idNotificacion, other.idNotificacion)) {
            return false;
        }
        if (!Objects.equals(this.idTransaccion, other.idTransaccion)) {
            return false;
        }
        if (!Objects.equals(this.resumen, other.resumen)) {
            return false;
        }
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        if (!Objects.equals(this.fecha, other.fecha)) {
            return false;
        }
        return Objects.equals(this.idTipoNotificacion, other.idTipoNotificacion);
    }
}
