/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.seidor.ulises.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import mx.mc.model.ReabastoExtended;

/**
 *
 * @author apalacios
 */
public class ImpAlbEntradas implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String TIPO_MOVIMIENTO_CLIENTE = "EPCLI";
    private long id;
    private String albaran;
    private String documento;
    private String tipoMovimientoCliente;
    private String proveedor;
    private String observaciones;
    private String tratado;
    private String error;
    private Date fechaTratado;
    private long version;
    private Integer prioridad;
    private int urgencia;
    private Integer orden;
    private String centro;
    private String tipoFlujo;
    private String tipoAlmacen;
    private String fechaRecepcion;
    private String codProveedor;
    private Date fechaCreacion;
    private String clasificaciones;
    private String valoresClasificaciones;
    private String matricula;
    private String zonaVisualizacion;
    private String checkListDescarga;
    private String codCliente;
    private String cliente;
    private String documentoOrigen;
    private String condicionAgrupa;
    private String propietario;
    private String pedidoCliente;

    public ImpAlbEntradas(ReabastoExtended reabasto, String tipoAlmacen) {
        this.albaran = reabasto.getFolio();
        this.documento = null;
        this.tipoMovimientoCliente = TIPO_MOVIMIENTO_CLIENTE;
        this.proveedor = reabasto.getNombreProveedor();
        this.observaciones = null;
        this.tratado = "N";
        this.error = null;
        this.fechaTratado = null;
        this.version = 1;
        this.prioridad = 0;
        this.urgencia = 0;
        this.orden = 0;
        this.centro = null;
        this.tipoFlujo = null;
        this.tipoAlmacen = tipoAlmacen;
        this.fechaRecepcion = null;
        this.codProveedor = null;
        this.fechaCreacion = new Date();
        this.clasificaciones = null;
        this.valoresClasificaciones = null;
        this.matricula = null;
        this.zonaVisualizacion = null;
        this.checkListDescarga = null;
        this.codCliente = null;
        this.cliente = null;
        this.documentoOrigen = null;
        this.condicionAgrupa = null;
        this.propietario = null;
        this.pedidoCliente = null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAlbaran() {
        return albaran;
    }

    public void setAlbaran(String albaran) {
        this.albaran = albaran;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getTipoMovimientoCliente() {
        return tipoMovimientoCliente;
    }

    public void setTipoMovimientoCliente(String tipoMovimientoCliente) {
        this.tipoMovimientoCliente = tipoMovimientoCliente;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getTratado() {
        return tratado;
    }

    public void setTratado(String tratado) {
        this.tratado = tratado;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Date getFechaTratado() {
        return fechaTratado;
    }

    public void setFechaTratado(Date fechaTratado) {
        this.fechaTratado = fechaTratado;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public Integer getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Integer prioridad) {
        this.prioridad = prioridad;
    }

    public int getUrgencia() {
        return urgencia;
    }

    public void setUrgencia(int urgencia) {
        this.urgencia = urgencia;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public String getCentro() {
        return centro;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }

    public String getTipoFlujo() {
        return tipoFlujo;
    }

    public void setTipoFlujo(String tipoFlujo) {
        this.tipoFlujo = tipoFlujo;
    }

    public String getTipoAlmacen() {
        return tipoAlmacen;
    }

    public void setTipoAlmacen(String tipoAlmacen) {
        this.tipoAlmacen = tipoAlmacen;
    }

    public String getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(String fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public String getCodProveedor() {
        return codProveedor;
    }

    public void setCodProveedor(String codProveedor) {
        this.codProveedor = codProveedor;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getClasificaciones() {
        return clasificaciones;
    }

    public void setClasificaciones(String clasificaciones) {
        this.clasificaciones = clasificaciones;
    }

    public String getValoresClasificaciones() {
        return valoresClasificaciones;
    }

    public void setValoresClasificaciones(String valoresClasificaciones) {
        this.valoresClasificaciones = valoresClasificaciones;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getZonaVisualizacion() {
        return zonaVisualizacion;
    }

    public void setZonaVisualizacion(String zonaVisualizacion) {
        this.zonaVisualizacion = zonaVisualizacion;
    }

    public String getCheckListDescarga() {
        return checkListDescarga;
    }

    public void setCheckListDescarga(String checkListDescarga) {
        this.checkListDescarga = checkListDescarga;
    }

    public String getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(String codCliente) {
        this.codCliente = codCliente;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getDocumentoOrigen() {
        return documentoOrigen;
    }

    public void setDocumentoOrigen(String documentoOrigen) {
        this.documentoOrigen = documentoOrigen;
    }

    public String getCondicionAgrupa() {
        return condicionAgrupa;
    }

    public void setCondicionAgrupa(String condicionAgrupa) {
        this.condicionAgrupa = condicionAgrupa;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public String getPedidoCliente() {
        return pedidoCliente;
    }

    public void setPedidoCliente(String pedidoCliente) {
        this.pedidoCliente = pedidoCliente;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 43 * hash + Objects.hashCode(this.albaran);
        hash = 43 * hash + Objects.hashCode(this.documento);
        hash = 43 * hash + Objects.hashCode(this.tipoMovimientoCliente);
        hash = 43 * hash + Objects.hashCode(this.proveedor);
        hash = 43 * hash + Objects.hashCode(this.observaciones);
        hash = 43 * hash + Objects.hashCode(this.tratado);
        hash = 43 * hash + Objects.hashCode(this.error);
        hash = 43 * hash + Objects.hashCode(this.fechaTratado);
        hash = 43 * hash + (int) (this.version ^ (this.version >>> 32));
        hash = 43 * hash + Objects.hashCode(this.prioridad);
        hash = 43 * hash + this.urgencia;
        hash = 43 * hash + Objects.hashCode(this.orden);
        hash = 43 * hash + Objects.hashCode(this.centro);
        hash = 43 * hash + Objects.hashCode(this.tipoFlujo);
        hash = 43 * hash + Objects.hashCode(this.tipoAlmacen);
        hash = 43 * hash + Objects.hashCode(this.fechaRecepcion);
        hash = 43 * hash + Objects.hashCode(this.codProveedor);
        hash = 43 * hash + Objects.hashCode(this.fechaCreacion);
        hash = 43 * hash + Objects.hashCode(this.clasificaciones);
        hash = 43 * hash + Objects.hashCode(this.valoresClasificaciones);
        hash = 43 * hash + Objects.hashCode(this.matricula);
        hash = 43 * hash + Objects.hashCode(this.zonaVisualizacion);
        hash = 43 * hash + Objects.hashCode(this.checkListDescarga);
        hash = 43 * hash + Objects.hashCode(this.codCliente);
        hash = 43 * hash + Objects.hashCode(this.cliente);
        hash = 43 * hash + Objects.hashCode(this.documentoOrigen);
        hash = 43 * hash + Objects.hashCode(this.condicionAgrupa);
        hash = 43 * hash + Objects.hashCode(this.propietario);
        hash = 43 * hash + Objects.hashCode(this.pedidoCliente);
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
        final ImpAlbEntradas other = (ImpAlbEntradas) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.version != other.version) {
            return false;
        }
        if (this.urgencia != other.urgencia) {
            return false;
        }
        if (!Objects.equals(this.albaran, other.albaran)) {
            return false;
        }
        if (!Objects.equals(this.documento, other.documento)) {
            return false;
        }
        if (!Objects.equals(this.tipoMovimientoCliente, other.tipoMovimientoCliente)) {
            return false;
        }
        if (!Objects.equals(this.proveedor, other.proveedor)) {
            return false;
        }
        if (!Objects.equals(this.observaciones, other.observaciones)) {
            return false;
        }
        if (!Objects.equals(this.tratado, other.tratado)) {
            return false;
        }
        if (!Objects.equals(this.error, other.error)) {
            return false;
        }
        if (!Objects.equals(this.centro, other.centro)) {
            return false;
        }
        if (!Objects.equals(this.tipoFlujo, other.tipoFlujo)) {
            return false;
        }
        if (!Objects.equals(this.tipoAlmacen, other.tipoAlmacen)) {
            return false;
        }
        if (!Objects.equals(this.fechaRecepcion, other.fechaRecepcion)) {
            return false;
        }
        if (!Objects.equals(this.codProveedor, other.codProveedor)) {
            return false;
        }
        if (!Objects.equals(this.clasificaciones, other.clasificaciones)) {
            return false;
        }
        if (!Objects.equals(this.valoresClasificaciones, other.valoresClasificaciones)) {
            return false;
        }
        if (!Objects.equals(this.matricula, other.matricula)) {
            return false;
        }
        if (!Objects.equals(this.zonaVisualizacion, other.zonaVisualizacion)) {
            return false;
        }
        if (!Objects.equals(this.checkListDescarga, other.checkListDescarga)) {
            return false;
        }
        if (!Objects.equals(this.codCliente, other.codCliente)) {
            return false;
        }
        if (!Objects.equals(this.cliente, other.cliente)) {
            return false;
        }
        if (!Objects.equals(this.documentoOrigen, other.documentoOrigen)) {
            return false;
        }
        if (!Objects.equals(this.condicionAgrupa, other.condicionAgrupa)) {
            return false;
        }
        if (!Objects.equals(this.propietario, other.propietario)) {
            return false;
        }
        if (!Objects.equals(this.pedidoCliente, other.pedidoCliente)) {
            return false;
        }
        if (!Objects.equals(this.fechaTratado, other.fechaTratado)) {
            return false;
        }
        if (!Objects.equals(this.prioridad, other.prioridad)) {
            return false;
        }
        if (!Objects.equals(this.orden, other.orden)) {
            return false;
        }
        return Objects.equals(this.fechaCreacion, other.fechaCreacion);
    }
    
    
}
