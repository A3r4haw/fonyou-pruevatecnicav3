/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author aortiz
 */
public class DevolucionMinistracionDetalleExtended extends DevolucionMinistracionDetalle implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String claveInstitucional;
    private String nombreCorto;
    private String lote;
    private Date fechaCaducidad;
    private int cantidadDevuelta;
    private int cantidadRecibida;    
    private Integer cantidadMaxima;
    private String idInventario;
    private String idSurtimientoInsumo;
    private List<SurtimientoEnviado_Extend> surtimientoEnviadoExtList;
    private int tipoDevolucion;
    private String comentario;
    
    
    public String getClaveInstitucional() {
        return claveInstitucional;
    }

    public void setClaveInstitucional(String claveInstitucional) {
        this.claveInstitucional = claveInstitucional;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public int getCantidadDevuelta() {
        return cantidadDevuelta;
    }

    public void setCantidadDevuelta(int cantidadDevuelta) {
        this.cantidadDevuelta = cantidadDevuelta;
    }

    public int getCantidadRecibida() {
        return cantidadRecibida;
    }

    public void setCantidadRecibida(int cantidadRecibida) {
        this.cantidadRecibida = cantidadRecibida;
    }

    @Override
    public String getIdInventario() {
        return idInventario;
    }

    @Override
    public void setIdInventario(String idInventario) {
        this.idInventario = idInventario;
    }

    public String getIdSurtimientoInsumo() {
        return idSurtimientoInsumo;
    }

    public void setIdSurtimientoInsumo(String idSurtimientoInsumo) {
        this.idSurtimientoInsumo = idSurtimientoInsumo;
    }

    public List<SurtimientoEnviado_Extend> getSurtimientoEnviadoExtList() {
        return surtimientoEnviadoExtList;
    }

    public void setSurtimientoEnviadoExtList(List<SurtimientoEnviado_Extend> surtimientoEnviadoExtList) {
        this.surtimientoEnviadoExtList = surtimientoEnviadoExtList;
    }

    public Integer getCantidadMaxima() {
        return cantidadMaxima;
    }

    public void setCantidadMaxima(Integer cantidadMaxima) {
        this.cantidadMaxima = cantidadMaxima;
    }

    public int getTipoDevolucion() {
        return tipoDevolucion;
    }

    public void setTipoDevolucion(int tipoDevolucion) {
        this.tipoDevolucion = tipoDevolucion;
    }
    
    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
