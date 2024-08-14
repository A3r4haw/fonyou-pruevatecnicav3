/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.ArrayList;
import java.util.List;
import mx.mc.dto.MedicamentoDTO;
import mx.mc.dto.ParamBusquedaAlertaDTO;
import mx.mc.dto.ParamBusquedaProtocoloDTO;
import mx.mc.dto.RespuestaAlertasDTO;
import mx.mc.dto.RespuestaValidacionSolucionDTO;
import mx.mc.dto.ValidacionNoCumplidaDTO;
import mx.mc.enums.CatalogoGeneral_Enum;
import mx.mc.enums.EstatusPrescripcion_Enum;
import mx.mc.enums.EstatusSurtimiento_Enum;
import mx.mc.init.Constantes;
import mx.mc.mapper.ConfigMapper;
import mx.mc.mapper.DiagnosticoMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.HipersensibilidadMapper;
import mx.mc.mapper.IndTerapeuticaMapper;
import mx.mc.mapper.InteraccionMapper;
import mx.mc.mapper.MedicamentoMapper;
import mx.mc.mapper.PacienteMapper;
import mx.mc.mapper.PrescripcionInsumoMapper;
import mx.mc.mapper.PrescripcionMapper;
import mx.mc.mapper.ReaccionMapper;
import mx.mc.model.AlertaFarmacovigilancia;
import mx.mc.model.Config;
import mx.mc.model.Diagnostico;
import mx.mc.model.HipersensibilidadExtended;
import mx.mc.model.IndTerapeuticaExtended;
import mx.mc.model.InteraccionExtended;
import mx.mc.model.Medicamento;
import mx.mc.model.Paciente;
import mx.mc.model.Prescripcion;
import mx.mc.model.PrescripcionInsumo_Extended;
import mx.mc.model.ProtocoloExtended;
import mx.mc.model.ReaccionExtend;
import mx.mc.model.SurtimientoInsumo_Extend;
import mx.mc.util.Comunes;
import mx.mc.util.FechaUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author gcruz
 */
@Service
public class AlertaFarmacovigilanciaServiceImpl extends GenericCrudServiceImpl<AlertaFarmacovigilancia, String> 
                    implements AlertaFarmacovigilanciaService {
    
    @Autowired
    private PacienteMapper pacienteMapper;
    
    /*@Autowired
    private VisitaMapper visitaMapper;*/
    
    @Autowired
    private PrescripcionMapper prescripcionMapper;
    
    @Autowired
    private MedicamentoMapper medicamentoMapper;
    
    @Autowired
    private InteraccionMapper interaccionMapper;
    
    @Autowired
    private DiagnosticoMapper diagnosticoMapper;
    
    @Autowired
    private IndTerapeuticaMapper indTerapeuticaMapper;
    
    @Autowired
    private ReaccionMapper reaccionMapper;
    
    @Autowired
    private ConfigMapper configService;
    
    @Autowired
    private PrescripcionInsumoMapper prescripcionInsumoMapper;
    
    @Autowired
    private HipersensibilidadMapper hipersensibilidadMapper;
    
    @Autowired
    private CentralMezclaSolucionService centralMezclaSolucionService;
    
    @Autowired
    private SurtimientoInsumoService surtimientoInsService;
    
    @Autowired
    public AlertaFarmacovigilanciaServiceImpl(GenericCrudMapper<AlertaFarmacovigilancia, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public RespuestaAlertasDTO buscarAlertasFarmacovigilancia(ParamBusquedaAlertaDTO paramBusquedaAlerta) throws Exception {
        RespuestaAlertasDTO respuestaAlertaDto = new RespuestaAlertasDTO();
        RespuestaValidacionSolucionDTO validacionSolucion = new RespuestaValidacionSolucionDTO();
        Integer numeroAlerta = 1;
        try {
            List<ValidacionNoCumplidaDTO> ValidacionNoCumplidas = new ArrayList<>();
            ValidacionNoCumplidaDTO validacionNoCumplida = null;
            List<String> listaMedicamentos = new ArrayList<>();            
            List<AlertaFarmacovigilancia> listaAlertaFarmacovigilancia = new ArrayList<>();
            List<Medicamento> listaParamMedicamentos = new ArrayList<>();
            List<MedicamentoDTO> listaClavesMedicamento = new ArrayList<>();
            //Se valida la existencia de los medicamentos que se envian
            for(MedicamentoDTO medicamentoDTO: paramBusquedaAlerta.getListaMedicametos()) {
                Medicamento medicamento = medicamentoMapper.obtenerMedicaByClave(medicamentoDTO.getClaveMedicamento());
                if(medicamento != null) {
                    listaMedicamentos.add(medicamento.getIdMedicamento());
                    listaParamMedicamentos.add(medicamento);
                    
                } else {
                    validacionNoCumplida = new ValidacionNoCumplidaDTO();
                    validacionNoCumplida.setCodigo("03");
                    validacionNoCumplida.setDescripcion("La clave de medicamento no existe :" + medicamentoDTO.getClaveMedicamento());
                    ValidacionNoCumplidas.add(validacionNoCumplida);
                }
            }
            
            //Se valida la existencia del paciente
            Paciente paciente = pacienteMapper.obtenerPacienteByNumeroPaciente(paramBusquedaAlerta.getNumeroPaciente());
            if(paciente != null) {
                /*Visita visita = visitaMapper.obtenerVisitaPorNumero(paramBusquedaAlerta.getNumeroVisita());
                if(visita != null) {
                    
                } else {
                    respuestaAlertaDto.setCodigo("02");
                    respuestaAlertaDto.setDescripcion("El número de visita no existe");
                }*/
// TODO: corregir la consulta de arámetros
                List<InteraccionExtended> listaInteraccionesEncontrada = new ArrayList<>();
                //Se obtiene las interacciones por numero de paciente primero se buscan los medicamentos que tiene en sus prescripciones
                Integer tipoInsumo = CatalogoGeneral_Enum.MEDICAMENTO.getValue();
                
                List<Config> configList = obtenerDatosSistema();
//                            String tempValue1 = Comunes.obtenValorConfiguracion(configList, Constantes.PARAM_SYSTEM_FUNC_TIPOINSUMO_PRESCRIPCION);
//                            tempValue1 = (!tempValue1.isEmpty()) ? tempValue1 : "0";
//                            Integer tipoInsumo = Integer.parseInt(tempValue1);

                String tempValue2 = Comunes.obtenValorConfiguracion(configList, Constantes.PARAM_SYSTEM_FUNC_NUMHRSPREV_PRESCRIPCION);
                tempValue2 = (!tempValue2.isEmpty()) ? tempValue2 : "0";
                Integer numHrsPrev = Integer.parseInt(tempValue2);

                String tempValue3 = Comunes.obtenValorConfiguracion(configList, Constantes.PARAM_SYSTEM_FUNC_NUMHRSPOST_PRESCRIPCION);
                tempValue3 = (!tempValue3.isEmpty()) ? tempValue3 : "0";
                Integer numHrsPost = Integer.parseInt(tempValue3);

                List<Integer> listEstatusPrescripcion = new ArrayList<>();
                listEstatusPrescripcion.add(EstatusPrescripcion_Enum.REGRISTRADA.getValue());
                listEstatusPrescripcion.add(EstatusPrescripcion_Enum.PROGRAMADA.getValue());
                listEstatusPrescripcion.add(EstatusPrescripcion_Enum.PROCESANDO.getValue());
                listEstatusPrescripcion.add(EstatusPrescripcion_Enum.SURTIDA_PARCIAL.getValue());
                listEstatusPrescripcion.add(EstatusPrescripcion_Enum.FINALIZADA.getValue());
                List<Integer> listEstatusSurtimientoInsumo = new ArrayList<>();
                listEstatusSurtimientoInsumo.add(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                listEstatusSurtimientoInsumo.add(EstatusSurtimiento_Enum.COMPLETADO.getValue());
                listEstatusSurtimientoInsumo.add(EstatusSurtimiento_Enum.EN_TRANSITO.getValue());
                listEstatusSurtimientoInsumo.add(EstatusSurtimiento_Enum.RECIBIDO.getValue());
                listEstatusSurtimientoInsumo.add(EstatusSurtimiento_Enum.SURTIDO.getValue());
                listEstatusSurtimientoInsumo.add(EstatusSurtimiento_Enum.SUTIDO_PARCIAL.getValue());

                List<String> surtIdInsumoLista = new ArrayList<>();
                for (Medicamento med : listaParamMedicamentos) {
                    surtIdInsumoLista.add(med.getIdMedicamento());
                }
                
                            
                if (paciente.getIdPaciente() != null && !surtIdInsumoLista.isEmpty()) {
                    List<SurtimientoInsumo_Extend> surInsumoLista;
                    surInsumoLista = surtimientoInsService.obtenerMedicamentosPrescritosPorPaciente(
                            paciente.getIdPaciente(),
                            surtIdInsumoLista,
                            tipoInsumo,
                            numHrsPrev,
                            numHrsPost,
                            listEstatusPrescripcion,
                            listEstatusSurtimientoInsumo);
                    if (surInsumoLista != null && !surInsumoLista.isEmpty()) {

                        StringBuilder sb;
                        Integer cont = 1;
                        SurtimientoInsumo_Extend siAnt = new SurtimientoInsumo_Extend();
                        for (SurtimientoInsumo_Extend o : surInsumoLista) {
                            if (cont>1){
                                if (!siAnt.getIdPrescripcion().equalsIgnoreCase(o.getIdPrescripcion())) {
                                    sb = new StringBuilder();
                                    sb.append("Folio Pres: ").append(o.getFolioPrescripcion());
                                    sb.append(" Insumo: ").append(o.getClaveInstitucional());
                                    sb.append(" Fecha: ").append(FechaUtil.formatoFecha(o.getFechaProgramada(), "dd/MM/yyyy HH:mm"));
                                    sb.append(" Médico: ").append(o.getNombreMedico());
                                    sb.append(" Posología: ").append(o.getDosis()).append(StringUtils.SPACE).append(o.getUnidadConcentracion());
                                    sb.append("|").append(o.getFrecuencia());
                                    sb.append("|").append(o.getDuracion());
                                    
                                    validacionNoCumplida = new ValidacionNoCumplidaDTO();
                                    validacionNoCumplida.setCodigo("00");
                                    validacionNoCumplida.setDescripcion("Posible duplicidad: " + sb.toString());
                                    if (ValidacionNoCumplidas.contains(validacionNoCumplida)) {
                                        ValidacionNoCumplidas.add(validacionNoCumplida);
                                    }
                                    
                                }
                            }
                            siAnt = new SurtimientoInsumo_Extend();
                            siAnt.setIdPrescripcion(o.getIdPrescripcion());
                            cont++;
                        }
                    }
        
                }
                
                
                listaClavesMedicamento = interaccionMapper.obtenerListaMedicamentosPrescrpByPacienteNumero(paramBusquedaAlerta.getNumeroPaciente(), tipoInsumo);
                //Se agregan medicamentos que estan llegando en el parametro de entrada
                if(!listaClavesMedicamento.isEmpty()) {
                    if(!paramBusquedaAlerta.getListaMedicametos().isEmpty()) {
                        for(MedicamentoDTO medicamentoDTO: paramBusquedaAlerta.getListaMedicametos()) {
                            listaClavesMedicamento.add(medicamentoDTO);
                                                        
                        }
                    }
                    //Se buscan las interacciones que se tienen en la lista de medicamentos
                    List<InteraccionExtended> listaInteraccion = interaccionMapper.obtenerInteraccionesClavesMedicamento(listaClavesMedicamento);
                    //List<InteraccionExtended> listaInteraccion = interaccionMapper.obtenerAlertaInteraccion(paramBusquedaAlerta.getNumeroPaciente());
                    
                    // Se compara la lista que se envia contra la lista de interacciones medicamentosas
                    for(Medicamento unMedicamento: listaParamMedicamentos) {
                        for(InteraccionExtended interaccionEncontrada : listaInteraccion){
                            if(unMedicamento.getSustanciaActiva().equals(interaccionEncontrada.getIdSustanciaUno()) || 
                                    unMedicamento.getSustanciaActiva().equals(interaccionEncontrada.getIdSustanciaDos())) {
                                if(listaInteraccionesEncontrada.contains(interaccionEncontrada) == false) {
                                    listaInteraccionesEncontrada.add(interaccionEncontrada);
                                }
                            }
                        }
                    }
                    if(!listaInteraccionesEncontrada.isEmpty()) {
                        //Agrega a lista a devolver interacciones de paciente
                        for(InteraccionExtended interaccion : listaInteraccionesEncontrada) {
                            
                            AlertaFarmacovigilancia alertaInteraccion = new AlertaFarmacovigilancia();
                            alertaInteraccion.setFactor1(interaccion.getMedicamento());
                            alertaInteraccion.setFactor2(interaccion.getMedicamentoInteraccion());
                            alertaInteraccion.setTipo(interaccion.getTipoInteraccion());
                            alertaInteraccion.setOrigen(interaccion.getNombreEmisor());
                            alertaInteraccion.setClasificacion("Interacción Medicamentosa");
                            /*Faltan estos dos valores    */                    
                            alertaInteraccion.setRiesgo(interaccion.getRiesgo());
                            alertaInteraccion.setNumero(numeroAlerta);
                            listaAlertaFarmacovigilancia.add(alertaInteraccion);
                            numeroAlerta+= 1;
                        }                        
                    }
                } else {
                    List<InteraccionExtended> listaInteraccion = interaccionMapper.obtenerInteraccionesClavesMedicamento(paramBusquedaAlerta.getListaMedicametos());
                    for (InteraccionExtended interaccion : listaInteraccion) {
                        AlertaFarmacovigilancia alertaInteraccion = new AlertaFarmacovigilancia();
                        alertaInteraccion.setFactor1(interaccion.getMedicamento());
                        alertaInteraccion.setFactor2(interaccion.getMedicamentoInteraccion());
                        alertaInteraccion.setTipo(interaccion.getTipoInteraccion());
                        alertaInteraccion.setOrigen(interaccion.getNombreEmisor());
                        alertaInteraccion.setClasificacion("Interacción Medicamentosa");
                        /*Faltan estos dos valores    */
                        alertaInteraccion.setRiesgo(interaccion.getRiesgo());
                        alertaInteraccion.setNumero(numeroAlerta);
                        listaAlertaFarmacovigilancia.add(alertaInteraccion);
                        numeroAlerta += 1;
                    }
                }                
                
                //Se busca lista para RAM con idMedicamento y el idPaciente
                if(!listaMedicamentos.isEmpty()) {
                    List<ReaccionExtend> listaReacciones = reaccionMapper.obtenerReaccionesByIdPacienteIdInsumos(paciente.getIdPaciente(), listaMedicamentos);
                    if(listaReacciones != null && !listaReacciones.isEmpty()) {
                        for(ReaccionExtend unaReaccion : listaReacciones) {
                            AlertaFarmacovigilancia alertaInteraccion = new AlertaFarmacovigilancia();
                            alertaInteraccion.setFactor1(unaReaccion.getNombrePaciente());
                            alertaInteraccion.setFactor2(unaReaccion.getMedicamento());
                            alertaInteraccion.setTipo(unaReaccion.getTipo());
                            alertaInteraccion.setOrigen("");// Por el momento queda de esta manera
                            alertaInteraccion.setClasificacion("RAM");
                            alertaInteraccion.setRiesgo(unaReaccion.getRiesgo()); //falta este valor
                            alertaInteraccion.setNumero(numeroAlerta);
                            listaAlertaFarmacovigilancia.add(alertaInteraccion);
                            numeroAlerta+= 1;
                        }
                    }
                }                
                               
                //Se busca lista para reacciones de hipersensibilidad por paciente y medicamentos para saber si existe alguno que se evia para esta reacción
                //Primero se valida  si los medicamentos se envian para buscar con respecto a estos medicamentos
                if(!paramBusquedaAlerta.getListaMedicametos().isEmpty()) {
                    List<HipersensibilidadExtended> listaHipersensibilidad  = hipersensibilidadMapper.obtenerListaReacHiperPorIdPaciente(paciente.getIdPaciente(), paramBusquedaAlerta.getListaMedicametos());
                    if(listaHipersensibilidad != null && !listaHipersensibilidad.isEmpty()) {
                        for(HipersensibilidadExtended unaHipersensibilidad : listaHipersensibilidad) {
                            AlertaFarmacovigilancia alertaHipersensibilidad = new AlertaFarmacovigilancia();
                            alertaHipersensibilidad.setFactor1(unaHipersensibilidad.getNombrePaciente());
                            alertaHipersensibilidad.setFactor2(unaHipersensibilidad.getNombreComercial());
                            alertaHipersensibilidad.setTipo(unaHipersensibilidad.tipoAlergia);
                            alertaHipersensibilidad.setOrigen("");
                            alertaHipersensibilidad.setClasificacion("Reacción de Hipersensibilidad");
                            alertaHipersensibilidad.setRiesgo(unaHipersensibilidad.getRiesgo()); //falta este valor
                            alertaHipersensibilidad.setNumero(numeroAlerta);
                            listaAlertaFarmacovigilancia.add(alertaHipersensibilidad);
                            numeroAlerta+= 1;
                        }
                    } 
                }                
            } else {
                
                //Se valida la lista de medicamentos que envian
                if (!paramBusquedaAlerta.getListaMedicametos().isEmpty()) {
                    if (paramBusquedaAlerta.getListaMedicametos().size() > 1) {
                        List<InteraccionExtended> listaInteracciones = interaccionMapper.obtenerInteraccionesClavesMedicamento(paramBusquedaAlerta.getListaMedicametos());
                        if (!listaInteracciones.isEmpty()) {
                            for (InteraccionExtended interaccion : listaInteracciones) {
                                AlertaFarmacovigilancia alertaInteraccion = new AlertaFarmacovigilancia();
                                alertaInteraccion.setFactor1(interaccion.getMedicamento());
                                alertaInteraccion.setFactor2(interaccion.getMedicamentoInteraccion());
                                alertaInteraccion.setTipo(interaccion.getTipoInteraccion());
                                alertaInteraccion.setOrigen(interaccion.getNombreEmisor());
                                alertaInteraccion.setClasificacion("Interacción Medicamentosa");
                                /*Faltan estos dos valores    */
                                alertaInteraccion.setRiesgo(interaccion.getRiesgo());
                                alertaInteraccion.setNumero(numeroAlerta);
                                listaAlertaFarmacovigilancia.add(alertaInteraccion);
                                numeroAlerta += 1;
                            }
                        }
                    }
                }
                
                validacionNoCumplida = new ValidacionNoCumplidaDTO();
                validacionNoCumplida.setCodigo("01");
                validacionNoCumplida.setDescripcion("El número de paciente no existe");
                ValidacionNoCumplidas.add(validacionNoCumplida);                
            }                                               
                                   
            //Se valida el folio de prescripcion
            Prescripcion prescripcion = prescripcionMapper.obtenerByFolioPrescripcion(paramBusquedaAlerta.getFolioPrescripcion());
            if(prescripcion != null) {
                Integer tipoInsumo = CatalogoGeneral_Enum.MEDICAMENTO.getValue();
                List<PrescripcionInsumo_Extended> listaPrescripcionInsumo = prescripcionInsumoMapper.obtenerInsumosPorIdPrescripcion(prescripcion.getIdPrescripcion(), tipoInsumo);
                for(PrescripcionInsumo_Extended prescripcionInsumo : listaPrescripcionInsumo) {
                    //listaMedicamentos.add(prescripcionInsumo.getIdInsumo());
                     for(String idDiagnostico : paramBusquedaAlerta.getListaDiagnosticos()) {
                        Diagnostico unDiagnostico = diagnosticoMapper.obtenerDiagnosticoPorIdDiag(idDiagnostico);
                        if(unDiagnostico != null) {
                            List<IndTerapeuticaExtended> indicacionLista = indTerapeuticaMapper.buscarDiagnosYMedicamento(prescripcionInsumo.getClaveInstitucional(), unDiagnostico.getClave());
                            String motivo = "";
                            if(indicacionLista != null) {
                                for (IndTerapeuticaExtended indicacion : indicacionLista){
                                    if(prescripcionInsumo.getDosis().compareTo(indicacion.getDosisMax()) == 1) {
                                        motivo = motivo + " La dosis mínima " + indicacion.getDosisMin() + " dosis máxima " +indicacion.getDosisMax();
                                    }    
                                    if(prescripcionInsumo.getFrecuencia() > indicacion.getFrecuenciaSuperior()) {
                                        motivo = motivo + " La frecuencia máxima: " + indicacion.getFrecuenciaSuperior();
                                    }
                                    if(indicacion.getDuracionMinima() != null) {
                                        if(prescripcionInsumo.getDuracion() > indicacion.getDuracionMinima()){                                    
                                            motivo = motivo + " La duración mínima: " + indicacion.getDuracionMinima();                                        
                                        }
                                    }

                                    if (!motivo.isEmpty()) { 
                                        AlertaFarmacovigilancia alertaInteraccion = new AlertaFarmacovigilancia();
                                        alertaInteraccion.setFactor1(indicacion.getNombreMedicamento());
                                        alertaInteraccion.setFactor2(indicacion.getNombreDiagnostico());
                                        alertaInteraccion.setTipo(indicacion.getTipoPaciente());   
                                        alertaInteraccion.setOrigen(indicacion.getOrigen());
                                        alertaInteraccion.setClasificacion("Indicación Terapéutica");
                                        alertaInteraccion.setRiesgo(indicacion.getRiesgo());
                                        alertaInteraccion.setMotivo(motivo);
                                        alertaInteraccion.setNumero(numeroAlerta);
                                        listaAlertaFarmacovigilancia.add(alertaInteraccion);
                                        numeroAlerta+= 1;
                                    }
                                }
                            } 
                        } else {
                            validacionNoCumplida = new ValidacionNoCumplidaDTO();
                            validacionNoCumplida.setCodigo("05");
                            validacionNoCumplida.setDescripcion("El diagnóstico no existe con clave:  " + unDiagnostico.getClave());
                            ValidacionNoCumplidas.add(validacionNoCumplida);
                        }
                     }    
                } 
            } else {
                
                //Se valida cada diagnostico con cada medicamento por si existe una indicacion terapeutica
                for(String claveDiagnostico : paramBusquedaAlerta.getListaDiagnosticos()) {
                    Diagnostico unDiagnostico = diagnosticoMapper.obtenerDiagnosticoPorClave(claveDiagnostico);
                    if(unDiagnostico != null) {
                        for(MedicamentoDTO medicamentoDTO : paramBusquedaAlerta.getListaMedicametos()) {
                            List<IndTerapeuticaExtended> indicacionLista = indTerapeuticaMapper.buscarDiagnosYMedicamento(medicamentoDTO.getClaveMedicamento(), claveDiagnostico);
//                                       IndTerapeuticaExtended indicacion = indTerapeuticaMapper.buscarDiagnosYMedicamento(medicamentoDTO.getClaveMedicamento(), claveDiagnostico);
                            String motivo = "";
                            if(indicacionLista != null) {
                                for (IndTerapeuticaExtended indicacion : indicacionLista){
                                    if(medicamentoDTO.getDosis().compareTo(indicacion.getDosisMax()) == 1) {
                                        motivo = motivo + " La dósis minima " + indicacion.getDosisMin() + " dósis maxima " +indicacion.getDosisMax();
                                    }

                                    if(medicamentoDTO.getFrecuencia() > indicacion.getFrecuenciaSuperior()) {
                                        motivo = motivo + " La frecuencia máxima: " + indicacion.getFrecuenciaSuperior();
                                    }
                                    if(indicacion.getDuracionMinima() != null) {
                                        if(medicamentoDTO.getDuracion() > indicacion.getDuracionMinima()){
                                            motivo = motivo + " La duración mínima: " + indicacion.getDuracionMinima();                                       
                                        }
                                    }
                                    if(motivo != "") { 
                                        AlertaFarmacovigilancia alertaInteraccion = new AlertaFarmacovigilancia();
                                        alertaInteraccion.setFactor1(indicacion.getNombreMedicamento());
                                        alertaInteraccion.setFactor2(indicacion.getNombreDiagnostico());
                                        alertaInteraccion.setTipo(indicacion.getTipoPaciente());   
                                        alertaInteraccion.setOrigen(indicacion.getOrigen());
                                        alertaInteraccion.setClasificacion("Indicación Terapéutica");
                                        alertaInteraccion.setRiesgo(indicacion.getRiesgo());
                                        alertaInteraccion.setMotivo(motivo);
                                        alertaInteraccion.setNumero(numeroAlerta);
                                        listaAlertaFarmacovigilancia.add(alertaInteraccion);
                                        numeroAlerta+= 1;
                                    }
                                }
                            
//                            String motivo = "";
//                            if(indicacion != null) {
                            }                            
                        }
                    } else {
                        validacionNoCumplida = new ValidacionNoCumplidaDTO();
                        validacionNoCumplida.setCodigo("05");
                        validacionNoCumplida.setDescripcion("El diagnóstico no existe con clave:  " + claveDiagnostico);
                        ValidacionNoCumplidas.add(validacionNoCumplida);
                    }

                }
                
                validacionNoCumplida = new ValidacionNoCumplidaDTO();
                validacionNoCumplida.setCodigo("02");
                validacionNoCumplida.setDescripcion("El folio de prescripción no existe");
                ValidacionNoCumplidas.add(validacionNoCumplida);
            }                                               
            
            
            if(!listaAlertaFarmacovigilancia.isEmpty()) {
                respuestaAlertaDto.setListaAlertaFarmacovigilancia(listaAlertaFarmacovigilancia);
                respuestaAlertaDto.setCodigo("06");
                respuestaAlertaDto.setDescripcion("Se encontró información con datos proporcionados");
            } else {
                respuestaAlertaDto.setCodigo("04");
                respuestaAlertaDto.setDescripcion("No se encontró información con datos proporcionados");
            }
            respuestaAlertaDto.setValidacionNoCumplidas(ValidacionNoCumplidas);
            
            if(paramBusquedaAlerta.isEsSolucionOncologica()) {
                ParamBusquedaProtocoloDTO paramBusquedaProtocoloDTO = new ParamBusquedaProtocoloDTO();
                paramBusquedaProtocoloDTO.setIdPrescripcion(prescripcion.getIdPrescripcion());
                if(paramBusquedaAlerta.getNumeroPaciente() != null) {
                    paramBusquedaProtocoloDTO.setNumeroPaciente(paramBusquedaAlerta.getNumeroPaciente());
                }
                paramBusquedaProtocoloDTO.setFolioSurtimiento(paramBusquedaAlerta.getFolioSurtimiento() != null ? paramBusquedaAlerta.getFolioSurtimiento() : "");
                validacionSolucion = centralMezclaSolucionService.buscarProtocosMezclaSolucion(paramBusquedaProtocoloDTO);
                if(validacionSolucion.getListaCentralMezclasSolucion() != null) {
                    if(!validacionSolucion.getListaCentralMezclasSolucion().isEmpty()) {
                         respuestaAlertaDto.setListaProtocolos(validacionSolucion.getListaCentralMezclasSolucion());                   
                    }
                }
            }
            
        } catch(Exception ex) {
            throw new Exception("Error al buscar las alertas de farmacovigilancia:  " + ex.getMessage());
        }
        return respuestaAlertaDto;
    }
    
    private List<Config> obtenerDatosSistema() {
        List<Config> configList = null;
        Config c = new Config();
        c.setActiva(Constantes.ACTIVO);
        try {
            configList = configService.obtenerLista(c);
        } catch (Exception ex) {
            
        }
        return configList;
    }
    
}
