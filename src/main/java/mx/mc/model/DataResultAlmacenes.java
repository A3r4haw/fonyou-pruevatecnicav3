/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.util.Date;

/**
 *
 * @author mcalderon
 */
public class DataResultAlmacenes extends DataResultReport {

    private static final long serialVersionUID = 1L;

    /*      los heredamos de DataResultReport
     folio
     claveMedicamento
     nombreMedicamento
     lote   
     */
    private String descripcionMovimiento;
    private String descripcionMotivo;
    private int insumoSolictado;
    private Date fechaSolicitada;
    private String idUsuarioSolicita;
    private Integer cantidadEnviado;
    private Date fechaSurtida;
    private String idUsuarioSurte;
    private Integer cantidadRecibida;
    private Date fechaRecibida;
    private String idUsuarioRecibe;
    private Integer cantidadIngresada;
    private Date fechaIngresada;
    private String idUsuarioIngresa;
    private String estructura;

    private String estructuraOrigen;
    private String estructuraDestino;
    private String estructuraInventario;

    public String getDescripcionMovimiento() {
        return descripcionMovimiento;
    }

    public void setDescripcionMovimiento(String descripcionMovimiento) {
        this.descripcionMovimiento = descripcionMovimiento;
    }

    public String getDescripcionMotivo() {
        return descripcionMotivo;
    }

    public void setDescripcionMotivo(String descripcionMotivo) {
        this.descripcionMotivo = descripcionMotivo;
    }

    public int getInsumoSolictado() {
        return insumoSolictado;
    }

    public void setInsumoSolictado(int insumoSolictado) {
        this.insumoSolictado = insumoSolictado;
    }

    public Date getFechaSolicitada() {
        return fechaSolicitada;
    }

    public void setFechaSolicitada(Date fechaSolicitada) {
        this.fechaSolicitada = fechaSolicitada;
    }

    public String getIdUsuarioSolicita() {
        return idUsuarioSolicita;
    }

    public void setIdUsuarioSolicita(String idUsuarioSolicita) {
        this.idUsuarioSolicita = idUsuarioSolicita;
    }

    public Integer getCantidadEnviado() {
        return cantidadEnviado;
    }

    public void setCantidadEnviado(Integer cantidadEnviado) {
        this.cantidadEnviado = cantidadEnviado;
    }

    public Date getFechaSurtida() {
        return fechaSurtida;
    }

    public void setFechaSurtida(Date fechaSurtida) {
        this.fechaSurtida = fechaSurtida;
    }

    public String getIdUsuarioSurte() {
        return idUsuarioSurte;
    }

    public void setIdUsuarioSurte(String idUsuarioSurte) {
        this.idUsuarioSurte = idUsuarioSurte;
    }

    @Override
    public Integer getCantidadRecibida() {
        return cantidadRecibida;
    }

    @Override
    public void setCantidadRecibida(Integer cantidadRecibida) {
        this.cantidadRecibida = cantidadRecibida;
    }

    public Date getFechaRecibida() {
        return fechaRecibida;
    }

    public void setFechaRecibida(Date fechaRecibida) {
        this.fechaRecibida = fechaRecibida;
    }

    public String getIdUsuarioRecibe() {
        return idUsuarioRecibe;
    }

    public void setIdUsuarioRecibe(String idUsuarioRecibe) {
        this.idUsuarioRecibe = idUsuarioRecibe;
    }

    public Integer getCantidadIngresada() {
        return cantidadIngresada;
    }

    public void setCantidadIngresada(Integer cantidadIngresada) {
        this.cantidadIngresada = cantidadIngresada;
    }

    public Date getFechaIngresada() {
        return fechaIngresada;
    }

    public void setFechaIngresada(Date fechaIngresada) {
        this.fechaIngresada = fechaIngresada;
    }

    public String getIdUsuarioIngresa() {
        return idUsuarioIngresa;
    }

    public void setIdUsuarioIngresa(String idUsuarioIngresa) {
        this.idUsuarioIngresa = idUsuarioIngresa;
    }

    public String getEstructura() {
        return estructura;
    }

    public void setEstructura(String estructura) {
        this.estructura = estructura;
    }

    public String getEstructuraOrigen() {
        return estructuraOrigen;
    }

    public void setEstructuraOrigen(String estructuraOrigen) {
        this.estructuraOrigen = estructuraOrigen;
    }

    public String getEstructuraDestino() {
        return estructuraDestino;
    }

    public void setEstructuraDestino(String estructuraDestino) {
        this.estructuraDestino = estructuraDestino;
    }

    public String getEstructuraInventario() {
        return estructuraInventario;
    }

    public void setEstructuraInventario(String estructuraInventario) {
        this.estructuraInventario = estructuraInventario;
    }

}
