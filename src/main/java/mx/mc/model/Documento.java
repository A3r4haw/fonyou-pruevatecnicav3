package mx.mc.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author hramirez
 */
public class Documento implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idDocumento;
    private String nombre;
    private String descripcion;
    private Integer idTipoArchivo;
    private String nombreArchivo;
    private String extension;
    private byte[] archivo;
    private Date fecha;
    private boolean autorizado;
    private boolean activo;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;
    private String idEstructura;
    private String idUsuarioAutoriza;
    private Date fechaAutorizado;

    public Documento() {
    }

    public Documento(String idDocumento) {
        this.idDocumento = idDocumento;
    }

    public Documento(String idDocumento, String nombre, String descripcion, Integer idTipoArchivo, String nombreArchivo, String extension, byte[] archivo, Date fecha, boolean autorizado, boolean activo, Date insertFecha, String insertIdUsuario, Date updateFecha, String updateIdUsuario, String idEstructura, String idUsuarioAutoriza, Date fechaAutorizado) {
        this.idDocumento = idDocumento;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idTipoArchivo = idTipoArchivo;
        this.nombreArchivo = nombreArchivo;
        this.extension = extension;
        this.archivo = archivo;
        this.fecha = fecha;
        this.autorizado = autorizado;
        this.activo = activo;
        this.insertFecha = insertFecha;
        this.insertIdUsuario = insertIdUsuario;
        this.updateFecha = updateFecha;
        this.updateIdUsuario = updateIdUsuario;
        this.idEstructura = idEstructura;
        this.idUsuarioAutoriza = idUsuarioAutoriza;
        this.fechaAutorizado = fechaAutorizado;
    }

    public String getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(String idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getIdTipoArchivo() {
        return idTipoArchivo;
    }

    public void setIdTipoArchivo(Integer idTipoArchivo) {
        this.idTipoArchivo = idTipoArchivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public boolean isAutorizado() {
        return autorizado;
    }

    public void setAutorizado(boolean autorizado) {
        this.autorizado = autorizado;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
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

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public String getIdUsuarioAutoriza() {
        return idUsuarioAutoriza;
    }

    public void setIdUsuarioAutoriza(String idUsuarioAutoriza) {
        this.idUsuarioAutoriza = idUsuarioAutoriza;
    }

    public Date getFechaAutorizado() {
        return fechaAutorizado;
    }

    public void setFechaAutorizado(Date fechaAutorizado) {
        this.fechaAutorizado = fechaAutorizado;
    }

    @Override
    public String toString() {
        return "Documento{" + "idDocumento=" + idDocumento + ", nombre=" + nombre + ", descripcion=" + descripcion + ", idTipoArchivo=" + idTipoArchivo + ", nombreArchivo=" + nombreArchivo + ", extension=" + extension + ", archivo=" + archivo + ", fecha=" + fecha + ", autorizado=" + autorizado + ", activo=" + activo + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + ", idEstructura=" + idEstructura + ", idUsuarioAutoriza=" + idUsuarioAutoriza + ", fechaAutorizado=" + fechaAutorizado + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.idDocumento);
        hash = 29 * hash + Objects.hashCode(this.nombre);
        hash = 29 * hash + Objects.hashCode(this.descripcion);
        hash = 29 * hash + Objects.hashCode(this.idTipoArchivo);
        hash = 29 * hash + Objects.hashCode(this.nombreArchivo);
        hash = 29 * hash + Objects.hashCode(this.extension);
        hash = 29 * hash + Arrays.hashCode(this.archivo);
        hash = 29 * hash + Objects.hashCode(this.fecha);
        hash = 29 * hash + (this.autorizado ? 1 : 0);
        hash = 29 * hash + (this.activo ? 1 : 0);
        hash = 29 * hash + Objects.hashCode(this.insertFecha);
        hash = 29 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 29 * hash + Objects.hashCode(this.updateFecha);
        hash = 29 * hash + Objects.hashCode(this.updateIdUsuario);
        hash = 29 * hash + Objects.hashCode(this.idEstructura);
        hash = 29 * hash + Objects.hashCode(this.idUsuarioAutoriza);
        hash = 29 * hash + Objects.hashCode(this.fechaAutorizado);
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
        final Documento other = (Documento) obj;
        if (this.autorizado != other.autorizado) {
            return false;
        }
        if (this.activo != other.activo) {
            return false;
        }
        if (!Objects.equals(this.idDocumento, other.idDocumento)) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        if (!Objects.equals(this.nombreArchivo, other.nombreArchivo)) {
            return false;
        }
        if (!Objects.equals(this.extension, other.extension)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.idEstructura, other.idEstructura)) {
            return false;
        }
        if (!Objects.equals(this.idUsuarioAutoriza, other.idUsuarioAutoriza)) {
            return false;
        }
        if (!Objects.equals(this.idTipoArchivo, other.idTipoArchivo)) {
            return false;
        }
        if (!Arrays.equals(this.archivo, other.archivo)) {
            return false;
        }
        if (!Objects.equals(this.fecha, other.fecha)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        if (!Objects.equals(this.updateFecha, other.updateFecha)) {
            return false;
        }
        return Objects.equals(this.fechaAutorizado, other.fechaAutorizado);
    }

}
