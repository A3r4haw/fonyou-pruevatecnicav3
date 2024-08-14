/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.dto;

import java.util.List;
import mx.mc.model.AlertaFarmacovigilancia;
import mx.mc.model.ProtocoloExtended;

/**
 *
 * @author gcruz
 */
public class RespuestaAlertasDTO {
    private String codigo;
    private String descripcion;
    private List<AlertaFarmacovigilancia> listaAlertaFarmacovigilancia;
    private List<ValidacionNoCumplidaDTO> ValidacionNoCumplidas;
    private List<ProtocoloExtended> listaProtocolos;

    public RespuestaAlertasDTO() {
        
    }
    
    public RespuestaAlertasDTO(String codigo, String descripcion, List<AlertaFarmacovigilancia> listaAlertaFarmacovigilancia, List<ProtocoloExtended> listaProtocolos) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.listaAlertaFarmacovigilancia = listaAlertaFarmacovigilancia;
        this.listaProtocolos = listaProtocolos;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<AlertaFarmacovigilancia> getListaAlertaFarmacovigilancia() {
        return listaAlertaFarmacovigilancia;
    }

    public void setListaAlertaFarmacovigilancia(List<AlertaFarmacovigilancia> listaAlertaFarmacovigilancia) {
        this.listaAlertaFarmacovigilancia = listaAlertaFarmacovigilancia;
    }

    public List<ValidacionNoCumplidaDTO> getValidacionNoCumplidas() {
        return ValidacionNoCumplidas;
    }

    public void setValidacionNoCumplidas(List<ValidacionNoCumplidaDTO> ValidacionNoCumplidas) {
        this.ValidacionNoCumplidas = ValidacionNoCumplidas;
    }

    public List<ProtocoloExtended> getListaProtocolos() {
        return listaProtocolos;
    }

    public void setListaProtocolos(List<ProtocoloExtended> listaProtocolos) {
        this.listaProtocolos = listaProtocolos;
    }
        
}
