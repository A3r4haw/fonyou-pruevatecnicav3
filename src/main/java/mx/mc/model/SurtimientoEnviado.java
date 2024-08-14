package mx.mc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author hramirez
 */
public class SurtimientoEnviado implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idSurtimientoEnviado;
    private String idSurtimientoInsumo;
    private String idInventarioSurtido;
    private Integer cantidadEnviado;
    private Integer cantidadRecibido;
    private Integer idEstatusSurtimiento;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;

    private boolean existeSobrante;
    private Integer idTipoJustificante;
    private BigDecimal cantidadExcedente;

    //Estos no van en la BD
    private String idInsumo;
    private String clave;
    private String medicamento;
    private String lote;
    private Date caducidad;
    private Date fechaInicio;
    private String ministracion;
    private Integer orden;
    private Integer cantidad;

    private Integer factorTransformacion;
    private Integer presentacionComercial;

    private Integer usoDeRemanente;

    public SurtimientoEnviado() {
        //No code needed in constructor
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.idSurtimientoEnviado);
        hash = 61 * hash + Objects.hashCode(this.idSurtimientoInsumo);
        hash = 61 * hash + Objects.hashCode(this.idInventarioSurtido);
        hash = 61 * hash + Objects.hashCode(this.cantidadEnviado);
        hash = 61 * hash + Objects.hashCode(this.cantidadRecibido);
        hash = 61 * hash + Objects.hashCode(this.idEstatusSurtimiento);
        hash = 61 * hash + Objects.hashCode(this.insertFecha);
        hash = 61 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 61 * hash + Objects.hashCode(this.updateFecha);
        hash = 61 * hash + Objects.hashCode(this.updateIdUsuario);
        hash = 61 * hash + Objects.hashCode(this.idInsumo);
        hash = 61 * hash + Objects.hashCode(this.clave);
        hash = 61 * hash + Objects.hashCode(this.medicamento);
        hash = 61 * hash + Objects.hashCode(this.lote);
        hash = 61 * hash + Objects.hashCode(this.caducidad);
        hash = 61 * hash + Objects.hashCode(this.fechaInicio);
        hash = 61 * hash + Objects.hashCode(this.ministracion);
        hash = 61 * hash + Objects.hashCode(this.orden);
        hash = 61 * hash + Objects.hashCode(this.cantidad);
        hash = 61 * hash + Objects.hashCode(this.presentacionComercial);
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
        final SurtimientoEnviado other = (SurtimientoEnviado) obj;
        if (!Objects.equals(this.idSurtimientoEnviado, other.idSurtimientoEnviado)) {
            return false;
        }
        if (!Objects.equals(this.idSurtimientoInsumo, other.idSurtimientoInsumo)) {
            return false;
        }
        if (!Objects.equals(this.idInventarioSurtido, other.idInventarioSurtido)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.idInsumo, other.idInsumo)) {
            return false;
        }
        if (!Objects.equals(this.clave, other.clave)) {
            return false;
        }
        if (!Objects.equals(this.medicamento, other.medicamento)) {
            return false;
        }
        if (!Objects.equals(this.lote, other.lote)) {
            return false;
        }
        if (!Objects.equals(this.ministracion, other.ministracion)) {
            return false;
        }
        if (!Objects.equals(this.cantidadEnviado, other.cantidadEnviado)) {
            return false;
        }
        if (!Objects.equals(this.cantidadRecibido, other.cantidadRecibido)) {
            return false;
        }
        if (!Objects.equals(this.idEstatusSurtimiento, other.idEstatusSurtimiento)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        if (!Objects.equals(this.updateFecha, other.updateFecha)) {
            return false;
        }
        if (!Objects.equals(this.caducidad, other.caducidad)) {
            return false;
        }
        if (!Objects.equals(this.fechaInicio, other.fechaInicio)) {
            return false;
        }
        if (!Objects.equals(this.orden, other.orden)) {
            return false;
        }
        if (!Objects.equals(this.cantidad, other.cantidad)) {
            return false;
        }
        return Objects.equals(this.presentacionComercial, other.presentacionComercial);
    }

    @Override
    public String toString() {
        return "SurtimientoEnviado{" + "idSurtimientoEnviado=" + idSurtimientoEnviado + ", idSurtimientoInsumo=" + idSurtimientoInsumo + ", idInventarioSurtido=" + idInventarioSurtido + ", cantidadEnviado=" + cantidadEnviado + ", cantidadRecibido=" + cantidadRecibido + ", idEstatusSurtimiento=" + idEstatusSurtimiento + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + ", idInsumo=" + idInsumo + ", clave=" + clave + ", medicamento=" + medicamento + ", lote=" + lote + ", caducidad=" + caducidad + ", fechaInicio=" + fechaInicio + ", ministracion=" + ministracion + ", orden=" + orden + ", cantidad=" + cantidad + ", presentacionComercial=" + presentacionComercial + '}';
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(String idInsumo) {
        this.idInsumo = idInsumo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public Date getCaducidad() {
        return caducidad;
    }

    public void setCaducidad(Date caducidad) {
        this.caducidad = caducidad;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getMinistracion() {
        return ministracion;
    }

    public void setMinistracion(String ministracion) {
        this.ministracion = ministracion;
    }

    public Integer getCantidadRecibido() {
        return cantidadRecibido;
    }

    public void setCantidadRecibido(Integer cantidadRecibido) {
        this.cantidadRecibido = cantidadRecibido;
    }

    public String getIdSurtimientoEnviado() {
        return idSurtimientoEnviado;
    }

    public void setIdSurtimientoEnviado(String idSurtimientoEnviado) {
        this.idSurtimientoEnviado = idSurtimientoEnviado;
    }

    public String getIdSurtimientoInsumo() {
        return idSurtimientoInsumo;
    }

    public void setIdSurtimientoInsumo(String idSurtimientoInsumo) {
        this.idSurtimientoInsumo = idSurtimientoInsumo;
    }

    public String getIdInventarioSurtido() {
        return idInventarioSurtido;
    }

    public void setIdInventarioSurtido(String idInventarioSurtido) {
        this.idInventarioSurtido = idInventarioSurtido;
    }

    public Integer getCantidadEnviado() {
        return cantidadEnviado;
    }

    public void setCantidadEnviado(Integer cantidadEnviado) {
        this.cantidadEnviado = cantidadEnviado;
    }

    public Integer getIdEstatusSurtimiento() {
        return idEstatusSurtimiento;
    }

    public void setIdEstatusSurtimiento(Integer idEstatusSurtimiento) {
        this.idEstatusSurtimiento = idEstatusSurtimiento;
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

    public Integer getFactorTransformacion() {
        return factorTransformacion;
    }

    public void setFactorTransformacion(Integer factorTransformacion) {
        this.factorTransformacion = factorTransformacion;
    }

    public Integer getPresentacionComercial() {
        return presentacionComercial;
    }

    public void setPresentacionComercial(Integer presentacionComercial) {
        this.presentacionComercial = presentacionComercial;
    }

    public boolean isExisteSobrante() {
        return existeSobrante;
    }

    public void setExisteSobrante(boolean existeSobrante) {
        this.existeSobrante = existeSobrante;
    }

    public Integer getIdTipoJustificante() {
        return idTipoJustificante;
    }

    public void setIdTipoJustificante(Integer idTipoJustificante) {
        this.idTipoJustificante = idTipoJustificante;
    }

    public BigDecimal getCantidadExcedente() {
        return cantidadExcedente;
    }

    public void setCantidadExcedente(BigDecimal cantidadExcedente) {
        this.cantidadExcedente = cantidadExcedente;
    }

    public Integer getUsoDeRemanente() {
        return usoDeRemanente;
    }

    public void setUsoDeRemanente(Integer usoDeRemanente) {
        this.usoDeRemanente = usoDeRemanente;
    }

}
