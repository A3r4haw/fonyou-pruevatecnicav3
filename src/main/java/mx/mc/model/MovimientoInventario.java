package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author AORTIZ
 */
public class MovimientoInventario implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String idMovimientoInventario;
    private int idTipoMotivo;
    private Date fecha;
    private String idUsuarioMovimiento;
    private String idEstrutcuraOrigen;
    private String idEstrutcuraDestino;
    private String idInventario;
    private int cantidad;
    private String folioDocumento;
    private Date fechaCaducidad;

    public MovimientoInventario() {
    //No code needed in constructor
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.idMovimientoInventario);
        hash = 59 * hash + this.idTipoMotivo;
        hash = 59 * hash + Objects.hashCode(this.fecha);
        hash = 59 * hash + Objects.hashCode(this.idUsuarioMovimiento);
        hash = 59 * hash + Objects.hashCode(this.idEstrutcuraOrigen);
        hash = 59 * hash + Objects.hashCode(this.idEstrutcuraDestino);
        hash = 59 * hash + Objects.hashCode(this.idInventario);
        hash = 59 * hash + this.cantidad;
        hash = 59 * hash + Objects.hashCode(this.folioDocumento);
        hash = 59 * hash + Objects.hashCode(this.fechaCaducidad);
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
        final MovimientoInventario other = (MovimientoInventario) obj;
        if (this.idTipoMotivo != other.idTipoMotivo) {
            return false;
        }
        if (this.cantidad != other.cantidad) {
            return false;
        }
        if (!Objects.equals(this.idMovimientoInventario, other.idMovimientoInventario)) {
            return false;
        }
        if (!Objects.equals(this.idUsuarioMovimiento, other.idUsuarioMovimiento)) {
            return false;
        }
        if (!Objects.equals(this.idEstrutcuraOrigen, other.idEstrutcuraOrigen)) {
            return false;
        }
        if (!Objects.equals(this.idEstrutcuraDestino, other.idEstrutcuraDestino)) {
            return false;
        }
        if (!Objects.equals(this.idInventario, other.idInventario)) {
            return false;
        }
        if (!Objects.equals(this.folioDocumento, other.folioDocumento)) {
            return false;
        }
        if (!Objects.equals(this.fecha, other.fecha)) {
            return false;
    }
        return Objects.equals(this.fechaCaducidad, other.fechaCaducidad);
    }

    

    @Override
    public String toString() {
        return "MovimientoInventario{" + "idMovimientoInventario=" + idMovimientoInventario + ", idTipoMotivo=" + idTipoMotivo + ", fecha=" + fecha + ", idUsuarioMovimiento=" + idUsuarioMovimiento + ", idEstrutcuraOrigen=" + idEstrutcuraOrigen + ", idEstrutcuraDestino=" + idEstrutcuraDestino + ", idInventario=" + idInventario + ", cantidad=" + cantidad + ", folioDocumento=" + folioDocumento + ", fechaCaducidad=" + fechaCaducidad +'}';
    }

    
    public String getIdMovimientoInventario() {
        return idMovimientoInventario;
    }

    public void setIdMovimientoInventario(String idMovimientoInventario) {
        this.idMovimientoInventario = idMovimientoInventario;
    }

    public int getIdTipoMotivo() {
        return idTipoMotivo;
    }

    public void setIdTipoMotivo(int idTipoMotivo) {
        this.idTipoMotivo = idTipoMotivo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getIdUsuarioMovimiento() {
        return idUsuarioMovimiento;
    }

    public void setIdUsuarioMovimiento(String idUsuarioMovimiento) {
        this.idUsuarioMovimiento = idUsuarioMovimiento;
    }

    public String getIdEstrutcuraOrigen() {
        return idEstrutcuraOrigen;
    }

    public void setIdEstrutcuraOrigen(String idEstrutcuraOrigen) {
        this.idEstrutcuraOrigen = idEstrutcuraOrigen;
    }

    public String getIdEstrutcuraDestino() {
        return idEstrutcuraDestino;
    }

    public void setIdEstrutcuraDestino(String idEstrutcuraDestino) {
        this.idEstrutcuraDestino = idEstrutcuraDestino;
    }

    public String getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(String idInventario) {
        this.idInventario = idInventario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getFolioDocumento() {
        return folioDocumento;
    }

    public void setFolioDocumento(String folioDocumento) {
        this.folioDocumento = folioDocumento;
    }
    
    public Date getFechaCaducidad() {
        return fechaCaducidad;
}

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }
    
}
