/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.util.List;
import mx.mc.dto.ValidacionSolucionMezclaDetalleDTO;

/**
 *
 * @author gcruz
 */
public class ProtocoloExtended extends Protocolo {

    private static final long serialVersionUID = 1L;
    
    private String idMedicamento;
    private String claveProtocolo;
    private String diagnostico;
    private String claveMedicamento;
    private String nombreMedicamento;
    private Integer idProtocoloDetalle;
    private String dosis;
    private String frecuencia;
    private String periodo;
    private Integer ciclos;
    private String estabilidad;
    private String area;
    private String peso;
    private boolean valido;
    private Integer receso;
    List<ValidacionSolucionMezclaDetalleDTO> listaDetalleValidacionSolucion;

    @Override
    public String toString() {
        return "ProtocoloExtended{" + "idMedicamento=" + idMedicamento + ", claveProtocolo=" + claveProtocolo + ", diagnostico=" + diagnostico + ", claveMedicamento=" + claveMedicamento + ", nombreMedicamento=" + nombreMedicamento + ", idProtocoloDetalle=" + idProtocoloDetalle + ", dosis=" + dosis + ", frecuencia=" + frecuencia + ", periodo=" + periodo + ", ciclos=" + ciclos + ", estabilidad=" + estabilidad + ", area=" + area + ", peso=" + peso + ", valido=" + valido + ", receso=" + receso + ", listaDetalleValidacionSolucion=" + listaDetalleValidacionSolucion + '}';
    }

    
    
    public String getIdMedicamento() {
        return idMedicamento;
    }
    
    public void setIdMedicamento(String idMedicamento) {
        this.idMedicamento = idMedicamento;
    }
    
    public String getClaveProtocolo() {
        return claveProtocolo;
    }

    public void setClaveProtocolo(String claveProtocolo) {
        this.claveProtocolo = claveProtocolo;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getClaveMedicamento() {
        return claveMedicamento;
    }

    public void setClaveMedicamento(String claveMedicamento) {
        this.claveMedicamento = claveMedicamento;
    }

    public String getNombreMedicamento() {
        return nombreMedicamento;
    }

    public void setNombreMedicamento(String nombreMedicamento) {
        this.nombreMedicamento = nombreMedicamento;
    }

    public Integer getIdProtocoloDetalle() {
        return idProtocoloDetalle;
    }

    public void setIdProtocoloDetalle(Integer idProtocoloDetalle) {
        this.idProtocoloDetalle = idProtocoloDetalle;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Integer getCiclos() {
        return ciclos;
    }

    public void setCiclos(Integer ciclos) {
        this.ciclos = ciclos;
    }

    public String getEstabilidad() {
        return estabilidad;
    }

    public void setEstabilidad(String estabilidad) {
        this.estabilidad = estabilidad;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public boolean isValido() {
        return valido;
    }

    public void setValido(boolean valido) {
        this.valido = valido;
    }

    public Integer getReceso() {
        return receso;
    }

    public void setReceso(Integer receso) {
        this.receso = receso;
    }

    public List<ValidacionSolucionMezclaDetalleDTO> getListaDetalleValidacionSolucion() {
        return listaDetalleValidacionSolucion;
    }

    public void setListaDetalleValidacionSolucion(List<ValidacionSolucionMezclaDetalleDTO> listaDetalleValidacionSolucion) {
        this.listaDetalleValidacionSolucion = listaDetalleValidacionSolucion;
    }
           
}
