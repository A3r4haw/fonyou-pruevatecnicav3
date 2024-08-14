/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.util.Date;

/**
 *
 * @author mcalderoniado
 */
public class DataResultVales extends DataResultReport {

    private static final long serialVersionUID = 1L;

    private Date fechaFirma;
    private String folioSurtimiento;
    private Date fechaProgramada;
    private Integer procesado;
    private Integer insumoEnviado;
    private Integer insumoSolictado;
    private Integer insumoCantEnviada;
    private int cantidadVale;
    private String folioVale;

    private Date fechaEnvio;
    private String mensaje;
    private int codigo;
    
    
    //recetas
     //paciente, estatus
    private String medico;
    private String cobertura;
    private Date fechaPrescripcion;
    private String origen;
    private Date fechaSurtCanc;
    
    public Date getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(Date fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getCodigo() {
        return codigo;
    }

    //
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    
    
    public Date getFechaFirma() {
        return fechaFirma;
    }

    public void setFechaFirma(Date fechaFirma) {
        this.fechaFirma = fechaFirma;
    }

    public Date getFechaProgramada() {
        return fechaProgramada;
    }

    public void setFechaProgramada(Date fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
    }

    public Integer getProcesado() {
        return procesado;
    }

    public void setProcesado(Integer procesado) {
        this.procesado = procesado;
    }

    public String getFolioSurtimiento() {
        return folioSurtimiento;
    }

    public void setFolioSurtimiento(String folioSurtimiento) {
        this.folioSurtimiento = folioSurtimiento;
    }

    public Integer getInsumoEnviado() {
        return insumoEnviado;
    }

    public void setInsumoEnviado(Integer insumoEnviado) {
        this.insumoEnviado = insumoEnviado;
    }

    public Integer getInsumoSolictado() {
        return insumoSolictado;
    }

    public void setInsumoSolictado(Integer insumodSolictado) {
        this.insumoSolictado = insumodSolictado;
    }

    public Integer getInsumoCantEnviada() {
        return insumoCantEnviada;
    }

    public void setInsumoCantEnviada(Integer insumoCantEnviada) {
        this.insumoCantEnviada = insumoCantEnviada;
    }
    
    
    public int getCantidadVale() {
        return cantidadVale;
    }

    public void setCantidadVale(int cantidadVale) {
        this.cantidadVale = cantidadVale;
    }

    public String getFolioVale() {
        return folioVale;
    }

    public void setFolioVale(String folioVale) {
        this.folioVale = folioVale;
    }

    @Override
    public String getMedico() {
        return medico;
    }

    @Override
    public void setMedico(String medico) {
        this.medico = medico;
    }

    public String getCobertura() {
        return cobertura;
    }

    public void setCobertura(String cobertura) {
        this.cobertura = cobertura;
    }

    public Date getFechaPrescripcion() {
        return fechaPrescripcion;
    }

    public void setFechaPrescripcion(Date fechaPrescripcion) {
        this.fechaPrescripcion = fechaPrescripcion;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public Date getFechaSurtCanc() {
        return fechaSurtCanc;
    }

    public void setFechaSurtCanc(Date fechaSurtCanc) {
        this.fechaSurtCanc = fechaSurtCanc;
    }
    

}
