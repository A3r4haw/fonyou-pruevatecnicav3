/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author unava
 */
public class SurtimientoEnviadoOff_Extend extends SurtimientoEnviado implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private boolean offline;
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

    private String idEstructura;
    private Integer activo;
    private String claveProveedor;
    private boolean conforme;
    private Integer cantidadDevolver;


    public boolean isOffline() {
        return offline;
    }

    public void setOffline(boolean offline) {
        this.offline = offline;
    }

    @Override
    public String getIdSurtimientoEnviado() {
        return idSurtimientoEnviado;
    }

    @Override
    public void setIdSurtimientoEnviado(String idSurtimientoEnviado) {
        this.idSurtimientoEnviado = idSurtimientoEnviado;
    }

    @Override
    public String getIdSurtimientoInsumo() {
        return idSurtimientoInsumo;
    }

    @Override
    public void setIdSurtimientoInsumo(String idSurtimientoInsumo) {
        this.idSurtimientoInsumo = idSurtimientoInsumo;
    }

    @Override
    public String getIdInventarioSurtido() {
        return idInventarioSurtido;
    }

    @Override
    public void setIdInventarioSurtido(String idInventarioSurtido) {
        this.idInventarioSurtido = idInventarioSurtido;
    }

    @Override
    public Integer getCantidadEnviado() {
        return cantidadEnviado;
    }

    @Override
    public void setCantidadEnviado(Integer cantidadEnviado) {
        this.cantidadEnviado = cantidadEnviado;
    }

    @Override
    public Integer getCantidadRecibido() {
        return cantidadRecibido;
    }

    @Override
    public void setCantidadRecibido(Integer cantidadRecibido) {
        this.cantidadRecibido = cantidadRecibido;
    }

    @Override
    public Integer getIdEstatusSurtimiento() {
        return idEstatusSurtimiento;
    }

    @Override
    public void setIdEstatusSurtimiento(Integer idEstatusSurtimiento) {
        this.idEstatusSurtimiento = idEstatusSurtimiento;
    }

    @Override
    public Date getInsertFecha() {
        return insertFecha;
    }

    @Override
    public void setInsertFecha(Date insertFecha) {
        this.insertFecha = insertFecha;
    }

    @Override
    public String getInsertIdUsuario() {
        return insertIdUsuario;
    }

    @Override
    public void setInsertIdUsuario(String insertIdUsuario) {
        this.insertIdUsuario = insertIdUsuario;
    }

    @Override
    public Date getUpdateFecha() {
        return updateFecha;
    }

    @Override
    public void setUpdateFecha(Date updateFecha) {
        this.updateFecha = updateFecha;
    }

    @Override
    public String getUpdateIdUsuario() {
        return updateIdUsuario;
    }

    @Override
    public void setUpdateIdUsuario(String updateIdUsuario) {
        this.updateIdUsuario = updateIdUsuario;
    }

    @Override
    public String getIdInsumo() {
        return idInsumo;
    }

    @Override
    public void setIdInsumo(String idInsumo) {
        this.idInsumo = idInsumo;
    }

    @Override
    public String getClave() {
        return clave;
    }

    @Override
    public void setClave(String clave) {
        this.clave = clave;
    }

    @Override
    public String getMedicamento() {
        return medicamento;
    }

    @Override
    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    @Override
    public String getLote() {
        return lote;
    }

    @Override
    public void setLote(String lote) {
        this.lote = lote;
    }

    @Override
    public Date getCaducidad() {
        return caducidad;
    }

    @Override
    public void setCaducidad(Date caducidad) {
        this.caducidad = caducidad;
    }

    @Override
    public Date getFechaInicio() {
        return fechaInicio;
    }

    @Override
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    @Override
    public String getMinistracion() {
        return ministracion;
    }

    @Override
    public void setMinistracion(String ministracion) {
        this.ministracion = ministracion;
    }

    @Override
    public Integer getOrden() {
        return orden;
    }

    @Override
    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    @Override
    public Integer getCantidad() {
        return cantidad;
    }

    @Override
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public Integer getFactorTransformacion() {
        return factorTransformacion;
    }

    @Override
    public void setFactorTransformacion(Integer factorTransformacion) {
        this.factorTransformacion = factorTransformacion;
    }

    @Override
    public Integer getPresentacionComercial() {
        return presentacionComercial;
    }

    @Override
    public void setPresentacionComercial(Integer presentacionComercial) {
        this.presentacionComercial = presentacionComercial;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }

    public String getClaveProveedor() {
        return claveProveedor;
    }

    public void setClaveProveedor(String claveProveedor) {
        this.claveProveedor = claveProveedor;
    }

    public boolean isConforme() {
        return conforme;
    }

    public void setConforme(boolean conforme) {
        this.conforme = conforme;
    }

    public Integer getCantidadDevolver() {
        return cantidadDevolver;
    }

    public void setCantidadDevolver(Integer cantidadDevolver) {
        this.cantidadDevolver = cantidadDevolver;
    }

}
