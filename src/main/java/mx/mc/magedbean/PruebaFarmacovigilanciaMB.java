/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.magedbean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import mx.mc.dto.MedicamentoDTO;
import mx.mc.dto.ParamBusquedaAlertaDTO;
import mx.mc.dto.ParamBusquedaProtocoloDTO;
import mx.mc.dto.RespuestaAlertasDTO;
import mx.mc.dto.RespuestaValidacionSolucionDTO;
import mx.mc.dto.ValidacionNoCumplidaDTO;
import mx.mc.dto.ValidacionSolucionMezclaDetalleDTO;
import mx.mc.model.AlertaFarmacovigilancia;
import mx.mc.model.ProtocoloExtended;
import mx.mc.service.AlertaFarmacovigilanciaService;
import mx.mc.service.CentralMezclaSolucionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author gcruz
 */
@Controller
@Scope(value = "view")
public class PruebaFarmacovigilanciaMB implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(PruebaFarmacovigilanciaMB.class);
    
    private RespuestaAlertasDTO respuestaAlertasDto;
    private List<AlertaFarmacovigilancia> listaAlertaFarmacovigilancia;
    private List<ValidacionNoCumplidaDTO> ValidacionNoCumplidas;
    
    private RespuestaValidacionSolucionDTO validacionSolucion;
    private List<ProtocoloExtended> listaProtocolos;
    private List<ValidacionSolucionMezclaDetalleDTO> listaDetalleProtocolos;
    
    @Autowired
    private transient AlertaFarmacovigilanciaService alertaFarmacovigilanciaService;
    
    @Autowired
    private transient CentralMezclaSolucionService centralMezclaSolucionService;
    
    public void buscarAlertas() {
        ParamBusquedaAlertaDTO paramBusquedaAlerta = new ParamBusquedaAlertaDTO();
        try {
            //Se llena el objeto que se enviara a metodo
            paramBusquedaAlerta.setNumeroPaciente("TAPA-005");
            paramBusquedaAlerta.setNumeroMedico("8975433");
            paramBusquedaAlerta.setNumeroVisita("1100987");
            paramBusquedaAlerta.setFolioPrescripcion("1100989");
            
            List<String> listaDiagnosticos = new ArrayList<>();
            listaDiagnosticos.add("A499");
            listaDiagnosticos.add("R520");
            listaDiagnosticos.add("M546");
            listaDiagnosticos.add("K6111");
            paramBusquedaAlerta.setListaDiagnosticos(listaDiagnosticos);
            
            List<MedicamentoDTO> listaMedicamentos = new ArrayList<>();
            MedicamentoDTO unMedicamento = new MedicamentoDTO();
            unMedicamento.setClaveMedicamento("010.000.0104.01.01");
            unMedicamento.setDosis(new BigDecimal("100"));
            unMedicamento.setDuracion(1);
            unMedicamento.setFrecuencia(4);
            listaMedicamentos.add(unMedicamento);
            
            unMedicamento.setClaveMedicamento("010.000.0108.00.00");
            unMedicamento.setDosis(new BigDecimal("500"));
            unMedicamento.setDuracion(1);
            unMedicamento.setFrecuencia(6);
            listaMedicamentos.add(unMedicamento);
            
            unMedicamento.setClaveMedicamento("040.000.0202.00");
            unMedicamento.setDosis(new BigDecimal("10"));
            unMedicamento.setDuracion(1);
            unMedicamento.setFrecuencia(8);
            listaMedicamentos.add(unMedicamento);
            
            unMedicamento.setClaveMedicamento("040.000.2106.00");
            unMedicamento.setDosis(new BigDecimal("100"));
            unMedicamento.setDuracion(1);
            unMedicamento.setFrecuencia(6);
            listaMedicamentos.add(unMedicamento);
            
            unMedicamento.setClaveMedicamento("010.000.2174.00.00");
            unMedicamento.setDosis(new BigDecimal("3"));
            unMedicamento.setDuracion(1);
            unMedicamento.setFrecuencia(4);
            listaMedicamentos.add(unMedicamento);
            
            unMedicamento.setClaveMedicamento("010000212800");
            unMedicamento.setDosis(new BigDecimal("500"));
            unMedicamento.setDuracion(1);
            unMedicamento.setFrecuencia(8);
            listaMedicamentos.add(unMedicamento);
            
            paramBusquedaAlerta.setListaMedicametos(listaMedicamentos);
            
            respuestaAlertasDto = alertaFarmacovigilanciaService.buscarAlertasFarmacovigilancia(paramBusquedaAlerta);
            if(respuestaAlertasDto.getListaAlertaFarmacovigilancia() != null) {
                if(!respuestaAlertasDto.getListaAlertaFarmacovigilancia().isEmpty()) {
                    listaAlertaFarmacovigilancia = respuestaAlertasDto.getListaAlertaFarmacovigilancia();
                }    
            }
            if(respuestaAlertasDto.getValidacionNoCumplidas() != null) {
                if(!respuestaAlertasDto.getValidacionNoCumplidas().isEmpty()) {
                    ValidacionNoCumplidas = respuestaAlertasDto.getValidacionNoCumplidas();
                }
            }
            
            ParamBusquedaProtocoloDTO paramBusquedaProtocoloDTO = new ParamBusquedaProtocoloDTO();
            paramBusquedaProtocoloDTO.setNumeroPaciente("PM-00052");
            validacionSolucion = centralMezclaSolucionService.buscarProtocosMezclaSolucion(paramBusquedaProtocoloDTO);
            if(validacionSolucion.getListaCentralMezclasSolucion() != null) {
                if(!validacionSolucion.getListaCentralMezclasSolucion().isEmpty()) {
                    listaProtocolos = validacionSolucion.getListaCentralMezclasSolucion();                   
                }
            }
            
        } catch(Exception ex){
            LOGGER.error("Error al buscar las alertas   " + ex.getMessage());
        }
    }
    
    public void obtenerDetalle(ProtocoloExtended protocoloSelected) {
        try {
            
            if(protocoloSelected != null) {
                if(protocoloSelected.getListaDetalleValidacionSolucion() != null) {
                    if(!protocoloSelected.getListaDetalleValidacionSolucion().isEmpty()) {
                        listaDetalleProtocolos = protocoloSelected.getListaDetalleValidacionSolucion();
                    }
                }
            }
        } catch(Exception ex) {
            LOGGER.error("Error al buscar el detalle del registro de protocolo  " + ex.getMessage());
        }
    }

    public RespuestaAlertasDTO getRespuestaAlertasDto() {
        return respuestaAlertasDto;
    }

    public void setRespuestaAlertasDto(RespuestaAlertasDTO respuestaAlertasDto) {
        this.respuestaAlertasDto = respuestaAlertasDto;
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

    public AlertaFarmacovigilanciaService getAlertaFarmacovigilanciaService() {
        return alertaFarmacovigilanciaService;
    }

    public void setAlertaFarmacovigilanciaService(AlertaFarmacovigilanciaService alertaFarmacovigilanciaService) {
        this.alertaFarmacovigilanciaService = alertaFarmacovigilanciaService;
    }

    public RespuestaValidacionSolucionDTO getValidacionSolucion() {
        return validacionSolucion;
    }

    public void setValidacionSolucion(RespuestaValidacionSolucionDTO validacionSolucion) {
        this.validacionSolucion = validacionSolucion;
    }

    public List<ProtocoloExtended> getListaProtocolos() {
        return listaProtocolos;
    }

    public void setListaProtocolos(List<ProtocoloExtended> listaProtocolos) {
        this.listaProtocolos = listaProtocolos;
    }

    public List<ValidacionSolucionMezclaDetalleDTO> getListaDetalleProtocolos() {
        return listaDetalleProtocolos;
    }

    public void setListaDetalleProtocolos(List<ValidacionSolucionMezclaDetalleDTO> listaDetalleProtocolos) {
        this.listaDetalleProtocolos = listaDetalleProtocolos;
    }    
    
}
