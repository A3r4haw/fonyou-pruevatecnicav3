package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author hramirez
 */
public class Ministracion implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idMinistracion;
    private String idSurtimientoInsumo;
    private Integer fechaProgramada;
    private Integer fechaMinistracion;
    private String idUsuarioMinistracion;
    private Integer cantidadMinistracion;
    private Integer idEstatusPrescripcion;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;

    public Ministracion() {
        //No code needed in constructor
    }
  
    public String getIdMinistracion() {
        return idMinistracion;
    }

    public void setIdMinistracion(String idMinistracion) {
        this.idMinistracion = idMinistracion;
    }

    public String getIdSurtimientoInsumo() {
        return idSurtimientoInsumo;
    }

    public void setIdSurtimientoInsumo(String idSurtimientoInsumo) {
        this.idSurtimientoInsumo = idSurtimientoInsumo;
    }

    public Integer getFechaProgramada() {
        return fechaProgramada;
    }

    public void setFechaProgramada(Integer fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
    }

    public Integer getFechaMinistracion() {
        return fechaMinistracion;
    }

    public void setFechaMinistracion(Integer fechaMinistracion) {
        this.fechaMinistracion = fechaMinistracion;
    }

    public String getIdUsuarioMinistracion() {
        return idUsuarioMinistracion;
    }

    public void setIdUsuarioMinistracion(String idUsuarioMinistracion) {
        this.idUsuarioMinistracion = idUsuarioMinistracion;
    }

    public Integer getCantidadMinistracion() {
        return cantidadMinistracion;
    }

    public void setCantidadMinistracion(Integer cantidadMinistracion) {
        this.cantidadMinistracion = cantidadMinistracion;
    }

    public Integer getIdEstatusPrescripcion() {
        return idEstatusPrescripcion;
    }

    public void setIdEstatusPrescripcion(Integer idEstatusPrescripcion) {
        this.idEstatusPrescripcion = idEstatusPrescripcion;
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
        return "Ministracion{" + "idMinistracion=" + idMinistracion + ", idSurtimientoInsumo=" + idSurtimientoInsumo + ", fechaProgramada=" + fechaProgramada + ", fechaMinistracion=" + fechaMinistracion + ", idUsuarioMinistracion=" + idUsuarioMinistracion + ", cantidadMinistracion=" + cantidadMinistracion + ", idEstatusPrescripcion=" + idEstatusPrescripcion + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.idMinistracion);
        hash = 79 * hash + Objects.hashCode(this.idSurtimientoInsumo);
        hash = 79 * hash + Objects.hashCode(this.fechaProgramada);
        hash = 79 * hash + Objects.hashCode(this.fechaMinistracion);
        hash = 79 * hash + Objects.hashCode(this.idUsuarioMinistracion);
        hash = 79 * hash + Objects.hashCode(this.cantidadMinistracion);
        hash = 79 * hash + Objects.hashCode(this.idEstatusPrescripcion);
        hash = 79 * hash + Objects.hashCode(this.insertFecha);
        hash = 79 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 79 * hash + Objects.hashCode(this.updateFecha);
        hash = 79 * hash + Objects.hashCode(this.updateIdUsuario);
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
        final Ministracion other = (Ministracion) obj;
        if (!Objects.equals(this.idMinistracion, other.idMinistracion)) {
            return false;
        }
        if (!Objects.equals(this.idSurtimientoInsumo, other.idSurtimientoInsumo)) {
            return false;
        }
        if (!Objects.equals(this.idUsuarioMinistracion, other.idUsuarioMinistracion)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.fechaProgramada, other.fechaProgramada)) {
            return false;
        }
        if (!Objects.equals(this.fechaMinistracion, other.fechaMinistracion)) {
            return false;
        }
        if (!Objects.equals(this.cantidadMinistracion, other.cantidadMinistracion)) {
            return false;
        }
        if (!Objects.equals(this.idEstatusPrescripcion, other.idEstatusPrescripcion)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        return Objects.equals(this.updateFecha, other.updateFecha);
    }

}
