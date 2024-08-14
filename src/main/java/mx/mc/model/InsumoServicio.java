/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

/**
 *
 * @author Admin
 */
public class InsumoServicio {
    private String idEstructura;
    private String idInsumo;
    private String servicio;
    private String clave;
    private String insumo;
    private Integer minimo;
    private Integer reorden;
    private Integer maximo;
    private Integer dotacion;
    private Integer cantidadActual;
    private Integer cantidadSolicitada;

    public InsumoServicio() {
    }

    public InsumoServicio(String idEstructura, String idInsumo, String servicio, String clave, String insumo,Integer minimo, Integer reorden, Integer maximo, Integer dotacion, Integer cantidadActual, Integer cantidadSolicitada) {
        this.idEstructura = idEstructura;
        this.idInsumo = idInsumo;
        this.servicio = servicio;
        this.clave = clave;
        this.insumo = insumo;
        this.minimo = minimo;
        this.reorden = reorden;
        this.maximo = maximo;
        this.dotacion = dotacion;
        this.cantidadActual = cantidadActual;
        this.cantidadSolicitada = cantidadSolicitada;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public String getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(String idInsumo) {
        this.idInsumo = idInsumo;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getInsumo() {
        return insumo;
    }

    public void setInsumo(String insumo) {
        this.insumo = insumo;
    }

    public Integer getMinimo() {
        return minimo;
    }

    public void setMinimo(Integer minimo) {
        this.minimo = minimo;
    }

    public Integer getReorden() {
        return reorden;
    }

    public void setReorden(Integer reorden) {
        this.reorden = reorden;
    }

    public Integer getMaximo() {
        return maximo;
    }

    public void setMaximo(Integer maximo) {
        this.maximo = maximo;
    }

    public Integer getDotacion() {
        return dotacion;
    }

    public void setDotacion(Integer dotacion) {
        this.dotacion = dotacion;
    }

    public Integer getCantidadActual() {
        return cantidadActual;
    }

    public void setCantidadActual(Integer cantidadActual) {
        this.cantidadActual = cantidadActual;
    }

    public Integer getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    public void setCantidadSolicitada(Integer cantidadSolicitada) {
        this.cantidadSolicitada = cantidadSolicitada;
    }
    
    
    
}
