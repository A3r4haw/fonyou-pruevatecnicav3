/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import mx.mc.dto.MedicamentoDTO;
import mx.mc.dto.ParamBusquedaProtocoloDTO;
import mx.mc.dto.RespuestaValidacionSolucionDTO;
import mx.mc.dto.SurtimientoValidarDTO;
import mx.mc.dto.ValidacionSolucionMezclaDetalleDTO;
import mx.mc.enums.EstatusSurtimiento_Enum;
import mx.mc.magedbean.SesionMB;
import mx.mc.mapper.DiagnosticoMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.MedicamentoMapper;
import mx.mc.mapper.PacienteMapper;
import mx.mc.mapper.PrescripcionInsumoMapper;
import mx.mc.mapper.PrescripcionMapper;
import mx.mc.mapper.ProtocoloDetalleDiaMapper;
import mx.mc.mapper.ProtocoloMapper;
import mx.mc.mapper.SurtimientoMinistradoMapper;
import mx.mc.model.Diagnostico;
import mx.mc.model.Medicamento;
import mx.mc.model.Paciente;
import mx.mc.model.Prescripcion;
import mx.mc.model.PrescripcionInsumo_Extended;
import mx.mc.model.ProtocoloDetalleDia;
import mx.mc.model.ProtocoloExtended;
import mx.mc.util.FechaUtil;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author gcruz
 */
@Service
public class CentralMezclaSolucionServiceImpl extends GenericCrudServiceImpl<ProtocoloExtended, String> implements CentralMezclaSolucionService {
    
    @Autowired
    private PacienteMapper pacienteMapper;
    
    @Autowired
    private ProtocoloMapper protocoloMapper;
    
    @Autowired
    private PrescripcionInsumoMapper prescripcionInsumoMapper;
    
    @Autowired 
    private DiagnosticoMapper diagnosticoMapper;
    
    @Autowired
    private MedicamentoMapper medicamentoMapper;
    
    @Autowired
    private SurtimientoMinistradoMapper surtimientoMinistradoMapper;
    
    @Autowired
    private ProtocoloDetalleDiaMapper protocoloDetalleDiaMapper;
    
    @Autowired
    private PrescripcionMapper prescripcionMapper;
    
    @Autowired
    public CentralMezclaSolucionServiceImpl(GenericCrudMapper<ProtocoloExtended, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public RespuestaValidacionSolucionDTO buscarProtocosMezclaSolucion(ParamBusquedaProtocoloDTO paramBusquedaProtocoloDTO) throws Exception {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        Integer numeroDiasCiclo = sesion.getNumDiasCiclo();
        RespuestaValidacionSolucionDTO respuestaValidacionSolucionDTO = new RespuestaValidacionSolucionDTO();
        List<ProtocoloExtended> listaProtocolos = new ArrayList<>();
        List<PrescripcionInsumo_Extended> listaPrescripcionInsumos = new ArrayList<>();
        List<Diagnostico> listaDiagnosticosEncontrados = new ArrayList<>();        
        List<ProtocoloExtended> listaProtocolosValidados = new ArrayList<>();
        Prescripcion prescripcion = new Prescripcion();        
        
        try {
            //Se valida la existencia del paciente
            Paciente paciente = pacienteMapper.obtenerPacienteByNumeroPaciente(paramBusquedaProtocoloDTO.getNumeroPaciente());
            if(paciente != null) {
                listaPrescripcionInsumos = prescripcionInsumoMapper.obtenerPrescripcionesByIdPaciente(paciente.getIdPaciente(), paramBusquedaProtocoloDTO.getFolioSurtimiento());
                listaDiagnosticosEncontrados = diagnosticoMapper.obtenerPorIdPacienteIdVisitaIdPrescripcion(paciente.getIdPaciente(), null, null);
                prescripcion = prescripcionMapper.obtener1erPrescripcionByIdPacienteAndIdPrescripcion(paciente.getIdPaciente(), paramBusquedaProtocoloDTO.getIdPrescripcion());
            } else {
                //Se valida la lista de medicamentos que envian
                if (!paramBusquedaProtocoloDTO.getListaMedicametos().isEmpty()) {
                    for(MedicamentoDTO medicamentoDTO: paramBusquedaProtocoloDTO.getListaMedicametos()) {
                        Medicamento medicamento = medicamentoMapper.obtenerMedicaByClave(medicamentoDTO.getClaveMedicamento());
                        if(medicamento != null) {
                            PrescripcionInsumo_Extended prescripcionInsumo = new PrescripcionInsumo_Extended();
                            prescripcionInsumo.setIdInsumo(medicamento.getIdMedicamento());
                            prescripcionInsumo.setClaveInstitucional(medicamento.getClaveInstitucional());
                            prescripcionInsumo.setNombreCorto(medicamento.getNombreCorto());
                            prescripcionInsumo.setNombreLargo(medicamento.getNombreLargo());
                            prescripcionInsumo.setConcentracion(medicamento.getConcentracion().toString());
                            prescripcionInsumo.setDosis(medicamentoDTO.getDosis());
                            prescripcionInsumo.setFrecuencia(medicamentoDTO.getFrecuencia());
                            prescripcionInsumo.setDuracion(medicamentoDTO.getDuracion());
                            listaPrescripcionInsumos.add(prescripcionInsumo);
                        }
                    }    
                }
            }
            /*if(!listaDiagnosticosEncontrados.isEmpty()) {
                List<String> listaDiagnosticos = new ArrayList<>();
                for(Diagnostico unDiagnostico : listaDiagnosticosEncontrados) {
                   // paramBusquedaProtocoloDTO.getListaDiagnosticos().add(unDiagnostico.getClave());
                   listaDiagnosticos.add(unDiagnostico.getClave());
                } 
                paramBusquedaProtocoloDTO.setListaDiagnosticos(listaDiagnosticos);
            }
            if(!paramBusquedaProtocoloDTO.getListaDiagnosticos().isEmpty()) {
                listaProtocolos = protocoloMapper.buscarProtocolosDiagnostico(paramBusquedaProtocoloDTO.getListaDiagnosticos());
            }*/
            listaProtocolos = protocoloMapper.buscarProtocolosByIdDiagnosticoAndIdProtocolo(prescripcion.getIdDiagnostico(), prescripcion.getIdProtocolo());
            if(!listaPrescripcionInsumos.isEmpty() && !listaProtocolos.isEmpty()) {
                for(ProtocoloExtended unProtocolo :listaProtocolos) {
                    for(PrescripcionInsumo_Extended unaPrescripcionInsumo : listaPrescripcionInsumos) {
                        boolean validar = true;
                        if(unProtocolo.getClaveMedicamento().equals(unaPrescripcionInsumo.getClaveInstitucional())) {
                            List<ValidacionSolucionMezclaDetalleDTO> listaDetalleValidacionSolucion = new ArrayList<>();
                            String[] frecuencia = unProtocolo.getFrecuencia().split(" ");
                            if(unaPrescripcionInsumo.getFrecuencia() != Integer.parseInt(frecuencia[0])) {
                                //Se genera un detalle de que no tiene la misma frecuencia
                                ValidacionSolucionMezclaDetalleDTO validacionSolucionDetalleDTO = new ValidacionSolucionMezclaDetalleDTO();
                                validacionSolucionDetalleDTO.setNombre("Frecuencia");
                                validacionSolucionDetalleDTO.setIndicada(frecuencia[0]);
                                validacionSolucionDetalleDTO.setPrescrita(unaPrescripcionInsumo.getFrecuencia().toString());
                                validacionSolucionDetalleDTO.setCausa("La frecuencia no es correcta");
                                listaDetalleValidacionSolucion.add(validacionSolucionDetalleDTO);
                                validar = false;
                            }
                            String[] dosis = unProtocolo.getDosis().split(" ");
                            if(unaPrescripcionInsumo.getDosis().compareTo(new BigDecimal(dosis[0])) != 0) {
                                //Se genera un detalle de que no tiene la misma dosis
                                ValidacionSolucionMezclaDetalleDTO validacionSolucionDetalleDTO = new ValidacionSolucionMezclaDetalleDTO();
                                validacionSolucionDetalleDTO.setNombre("Dosis");
                                validacionSolucionDetalleDTO.setIndicada(dosis[0]);
                                validacionSolucionDetalleDTO.setPrescrita(unaPrescripcionInsumo.getDosis().toString());
                                validacionSolucionDetalleDTO.setCausa("La dosis no es correcta");
                                listaDetalleValidacionSolucion.add(validacionSolucionDetalleDTO);
                                validar = false;
                            }
                            ProtocoloExtended protocoloResp = new ProtocoloExtended();
                            String[] valorPeriodo = unProtocolo.getPeriodo().split(" ");
                            Integer periodosProgramados = Integer.parseInt(valorPeriodo[0]);
                            //if(unaPrescripcionInsumo.getDuracion() == periodosProgramados) {
                                List<SurtimientoValidarDTO> listaSurtimientosValidar = surtimientoMinistradoMapper.obtenerSurtimientosAValidar(unaPrescripcionInsumo.getIdPrescripcionInsumo());
                                Date fechaValidar = null;
                                Integer estatusSurt = listaSurtimientosValidar.get(0).getIdEstatusSurtimiento();

                                //Integer total = Integer.parseInt(valorPeriodo[0]) * unProtocolo.getCiclos();
                                //todo cambiar esto para buscar el total de surtimientos y encontrar cuantos ciclos programados lleva
                                Integer totalSurtimientos = 0;
                                if(paciente != null) { //TODO MEJORAR LA CONSULTA Y MANDAR DIRECTAMENTE EL IDPRESCRIPCION
                                    totalSurtimientos = prescripcionInsumoMapper.totalSurtimientosByIdPacienteAndIdInsumo(paciente.getIdPaciente(), unaPrescripcionInsumo.getIdInsumo(), 
                                                                    unaPrescripcionInsumo.getIdPrescripcionInsumo());//listaSurtimientosValidar.size();
                                }                            

                                Integer ciclosProgramados = totalSurtimientos / periodosProgramados;

                                List<SurtimientoValidarDTO> listaSurtimientosValidarOrdenado = surtimientoMinistradoMapper.obtenerSurtimientosAValidarOrdenadoASC(unaPrescripcionInsumo.getIdPrescripcionInsumo());
                                Integer posUltimoSurtimiento = listaSurtimientosValidarOrdenado.size() - 1;
                                Integer numDias = 0;
                                Date fechaInicio = prescripcion.getFechaPrescripcion();
                                Date fechaFin = null;
                                String nombreFecha = "";
                                SurtimientoValidarDTO surtimientoValidar = null;
                                for(SurtimientoValidarDTO surtDTO : listaSurtimientosValidarOrdenado) {
                                    if(Objects.equals(surtDTO.getIdEstatusSurtimiento(), EstatusSurtimiento_Enum.PROGRAMADO.getValue())) {
                                        surtimientoValidar = surtDTO;
                                    }
                                }
                            if(surtimientoValidar != null) {    
                                if(surtimientoValidar.getFechaMinistrado() != null) {
                                    nombreFecha = "MINISTRADA";
                                    fechaValidar = surtimientoValidar.getFechaMinistrado();
                                    //fechaInicio = listaSurtimientosValidarOrdenado.get(0).getFechaMinistrado();
                                    fechaFin = listaSurtimientosValidarOrdenado.get(posUltimoSurtimiento).getFechaMinistrado();
                                } else {
                                    if(surtimientoValidar.getFechaProgMinistrado() != null) {
                                        nombreFecha = "PROGMINISTRADA";
                                        fechaValidar = surtimientoValidar.getFechaProgMinistrado();
                                        //fechaInicio = listaSurtimientosValidarOrdenado.get(0).getFechaProgMinistrado();
                                        fechaFin = listaSurtimientosValidarOrdenado.get(posUltimoSurtimiento).getFechaProgMinistrado();
                                    } else {
                                        if(surtimientoValidar.getFechaSurtimientoInsumo() != null) {
                                            nombreFecha = "SURTIMIENTO";
                                            fechaValidar = surtimientoValidar.getFechaSurtimientoInsumo();
                                            //fechaInicio = listaSurtimientosValidarOrdenado.get(0).getFechaSurtimientoInsumo();
                                            fechaFin = listaSurtimientosValidarOrdenado.get(posUltimoSurtimiento).getFechaSurtimientoInsumo();
                                        }
                                    }
                                }
                                //Obtener la comparativa de dias entre dos fechas mas el mismo
                                numDias = Days.daysBetween(new DateTime(fechaInicio), new DateTime(fechaFin)).getDays() + 1;
                                Integer contadorPeriodo = 0;
                                boolean fechaEncontrada = false;
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                Date fechaComparada = null; 
                                for(SurtimientoValidarDTO unSurtimiento : listaSurtimientosValidarOrdenado) {
                                    if(nombreFecha.equalsIgnoreCase("MINISTRADA")) {
                                        if(sdf.format(unSurtimiento.getFechaMinistrado()).equals(sdf.format(fechaValidar))) {
                                            // se genera correctamente
                                            contadorPeriodo+=1;
                                            fechaEncontrada = true;
                                            fechaComparada = unSurtimiento.getFechaMinistrado();
                                            break;
                                        }
                                    } else {
                                        if(nombreFecha.equalsIgnoreCase("PROGMINISTRADA")) {
                                            if(sdf.format(unSurtimiento.getFechaProgMinistrado()).equals(sdf.format(fechaValidar))) {
                                                // se genera correctamente
                                                contadorPeriodo+=1;
                                                fechaEncontrada = true;
                                                fechaComparada = unSurtimiento.getFechaProgMinistrado();
                                                break;
                                            }
                                        } else {
                                            if(sdf.format(unSurtimiento.getFechaSurtimiento()).equals(sdf.format(fechaValidar))) {
                                                // se genera correctamente
                                                contadorPeriodo+=1;
                                                fechaEncontrada = true;
                                                fechaComparada = unSurtimiento.getFechaSurtimiento();
                                                break;
                                            }
                                        }
                                    }
                                    contadorPeriodo+=1;                                                                        
                                }
                                
                                if(contadorPeriodo > Integer.parseInt(unProtocolo.getPeriodo())) {
                                    contadorPeriodo = contadorPeriodo - (Integer.parseInt(unProtocolo.getPeriodo()) * ciclosProgramados);
                                }
                                
                                if(fechaEncontrada) {
                                    List<ProtocoloDetalleDia> listaDetalle = protocoloDetalleDiaMapper.obtenerListDetalleDiaByIdProtocoloDetalle(unProtocolo.getIdProtocoloDetalle());
                                    String diaFecha = "";
                                    Date fechaInicioCiclo = fechaInicio;
                                    Date fechaSumada = null;
                                    if(!listaDetalle.isEmpty() && ciclosProgramados == 0) {
                                        //Se realiza la iteración de la lista detallepor día
                                        for(ProtocoloDetalleDia unDetalleDia : listaDetalle) {
                                            //Se suman los dias a la fecha inicio para saber si es correcta la fecha, se soloca un menos uno por que comienza en el mismo día
                                            fechaSumada = FechaUtil.sumarRestarDiasFecha(fechaInicioCiclo, unDetalleDia.getNumeroDia()- 1);
                                            //Se valida la fecha sumada con respecto a la fecha comparada para saber si corresponde al día
                                            if(sdf.format(fechaSumada).equals(sdf.format(fechaComparada))) {
                                                diaFecha = "Fecha de Aplicación correcta";
                                                break;
                                            } else {
                                                //Se valida si la fecha sumada es mayor que la programada para romper el ciclo ya que no es una fecha de aplicación
                                                if(FechaUtil.isFechaMayorQue(fechaSumada, fechaComparada)) break;
                                            }

                                        }
                                    } else {
                                        //Esto es para repetirlo dependiendo de los ciclos que lleve
                                        for(int i = 0; i< ciclosProgramados; i++) {
                                            if(!listaDetalle.isEmpty()) {
                                                //Se realiza la iteración de la lista detallepor día
                                                for(ProtocoloDetalleDia unDetalleDia : listaDetalle) {
                                                    //Se suman los dias a la fecha inicio para saber si es correcta la fecha, se soloca un menos uno por que comienza en el mismo día
                                                    fechaSumada = FechaUtil.sumarRestarDiasFecha(fechaInicioCiclo, unDetalleDia.getNumeroDia()- 1);
                                                    //Se valida la fecha sumada con respecto a la fecha comparada para saber si corresponde al día
                                                    if(sdf.format(fechaSumada).equals(sdf.format(fechaComparada))) {
                                                        diaFecha = "Fecha de Aplicación correcta";
                                                        break;
                                                    } else {
                                                        //Se valida si la fecha sumada es mayor que la programada para romper el ciclo ya que no es una fecha de aplicación
                                                        if(FechaUtil.isFechaMayorQue(fechaSumada, fechaComparada)) break;
                                                    }

                                                }                                                                               
                                            }
                                            //Si es igual a null es por que no tiene ningun detalle día, se le suman los días a la fecha inicio del ciclo que termino
                                            if(fechaSumada == null) {
                                                fechaSumada = FechaUtil.sumarRestarDiasFecha(fechaInicio,periodosProgramados);
                                            }                                                                                                                
                                        }
                                    }
                                    
                                    Integer diasDebenTranscurrir = 0;
                                    if (ciclosProgramados == 0) {
                                        diasDebenTranscurrir = numeroDiasCiclo;
                                    } else {
                                        diasDebenTranscurrir = ciclosProgramados * numeroDiasCiclo;
                                    }                                 
                                    Date fechaCicloConcluido = FechaUtil.sumarRestarDiasFecha(fechaInicio, diasDebenTranscurrir);
                                    //Date fechaCicloConcluido =  null;
                                    //Se valida que el total de surtimientos sea mayor que el periodo programado, indica que ya termino un ciclo
                                    if (totalSurtimientos > periodosProgramados) {
                                        if (FechaUtil.isFechaMayorQue(fechaCicloConcluido, fechaComparada)) {
                                            ValidacionSolucionMezclaDetalleDTO validacionSolucionDetalleDTO = new ValidacionSolucionMezclaDetalleDTO();
                                            validacionSolucionDetalleDTO.setNombre("Fecha Ciclo No Cumplido");
                                            validacionSolucionDetalleDTO.setIndicada(sdf.format(fechaCicloConcluido));
                                            validacionSolucionDetalleDTO.setPrescrita(sdf.format(fechaComparada));   
                                            validacionSolucionDetalleDTO.setCausa("No se han cumplido los " + diasDebenTranscurrir + " días de termino de ciclo");
                                            listaDetalleValidacionSolucion.add(validacionSolucionDetalleDTO);
                                            validar = false;
                                        }
                                        //Se valida si contiene un receso para determinar si ya termino un ciclo y sumar los días de receso
                                        if (unProtocolo.getReceso() != null && unProtocolo.getReceso() > 0) {//TODO REVISAR SI HACE FALTA MULTIPLICAR EL RECESO POR EL NUMERO DE CICLOS QUE LLEVA
                                            
                                            fechaInicioCiclo = FechaUtil.sumarRestarDiasFecha(fechaInicio, (unProtocolo.getReceso() + diasDebenTranscurrir));
                                            //Se valida si la fecha sumada por el receso de protocolo es mayor, quiere decir que no dejo pasar el receso
                                            if (FechaUtil.isFechaMayorQue(fechaInicioCiclo, fechaComparada)) {
                                                // //Se genera un detalle donde la fecha no corresponde por día
                                                ValidacionSolucionMezclaDetalleDTO validacionSolucionDetalleDTO = new ValidacionSolucionMezclaDetalleDTO();
                                                validacionSolucionDetalleDTO.setNombre("Fecha Receso");
                                                validacionSolucionDetalleDTO.setIndicada(sdf.format(fechaInicioCiclo));
                                                validacionSolucionDetalleDTO.setPrescrita(sdf.format(fechaComparada));
                                                validacionSolucionDetalleDTO.setCausa("No se dejaron pasar los " + unProtocolo.getReceso() + " días de receso");
                                                listaDetalleValidacionSolucion.add(validacionSolucionDetalleDTO);
                                                validar = false;
                                            }
                                        } 
                                    }                                

                                    //Se valida si encuentra detalle por dia y si es correcta la fecha en todos los ciclos
                                    if(diaFecha.isEmpty() && !listaDetalle.isEmpty()) {
                                        // //Se genera un detalle donde la fecha no corresponde por día
                                        ValidacionSolucionMezclaDetalleDTO validacionSolucionDetalleDTO = new ValidacionSolucionMezclaDetalleDTO();
                                        validacionSolucionDetalleDTO.setNombre("Fecha Aplicación");
                                        validacionSolucionDetalleDTO.setIndicada(sdf.format(fechaSumada));
                                        validacionSolucionDetalleDTO.setPrescrita(sdf.format(fechaComparada));
                                        validacionSolucionDetalleDTO.setCausa("La fecha no corresponde al día de aplicación del protocolo");
                                        listaDetalleValidacionSolucion.add(validacionSolucionDetalleDTO);
                                        validar = false;
                                    }    

                                    //Se genera objeto correcto de protocolo                                
                                    protocoloResp.setClaveProtocolo(unProtocolo.getClaveProtocolo());
                                    protocoloResp.setDiagnostico(unProtocolo.getDiagnostico());
                                    protocoloResp.setClaveMedicamento(unaPrescripcionInsumo.getClaveInstitucional());
                                    protocoloResp.setNombreMedicamento(unaPrescripcionInsumo.getNombreCorto());
                                    protocoloResp.setDosis(unProtocolo.getDosis());
                                    protocoloResp.setFrecuencia(unProtocolo.getFrecuencia());
                                    protocoloResp.setCiclos(unProtocolo.getCiclos());
                                    protocoloResp.setEstabilidad(unProtocolo.getEstabilidad());
                                    protocoloResp.setArea(unProtocolo.getArea());
                                    protocoloResp.setPeso(unProtocolo.getPeso());
                                    
                                    if(unaPrescripcionInsumo.getDuracion() <= periodosProgramados) {
                                        ValidacionSolucionMezclaDetalleDTO validacionSolucionDetalleDTO = new ValidacionSolucionMezclaDetalleDTO();
                                        validacionSolucionDetalleDTO.setNombre("Periodo");
                                        validacionSolucionDetalleDTO.setIndicada(unProtocolo.getPeriodo());
                                        validacionSolucionDetalleDTO.setPrescrita(contadorPeriodo.toString());
                                        validacionSolucionDetalleDTO.setCausa("Es el periodo en el que se encuentra ");
                                        listaDetalleValidacionSolucion.add(validacionSolucionDetalleDTO);
                                    } else {
                                        ValidacionSolucionMezclaDetalleDTO validacionSolucionDetalleDTO = new ValidacionSolucionMezclaDetalleDTO();
                                        validacionSolucionDetalleDTO.setNombre("Periodo");
                                        validacionSolucionDetalleDTO.setIndicada(valorPeriodo[0]);
                                        validacionSolucionDetalleDTO.setPrescrita(unaPrescripcionInsumo.getDuracion().toString());
                                        validacionSolucionDetalleDTO.setCausa("El periodo no es correcto");
                                        listaDetalleValidacionSolucion.add(validacionSolucionDetalleDTO);
                                        validar = false;
                                    }
                                    

                                    ValidacionSolucionMezclaDetalleDTO valSolucionDetalleDTO = new ValidacionSolucionMezclaDetalleDTO();
                                    valSolucionDetalleDTO.setNombre("Ciclo");
                                    valSolucionDetalleDTO.setIndicada(unProtocolo.getCiclos().toString());
                                    valSolucionDetalleDTO.setPrescrita(ciclosProgramados.toString());
                                    if (ciclosProgramados <= unProtocolo.getCiclos()) {
                                        valSolucionDetalleDTO.setCausa("Los ciclos que tiene hasta el momento son correctos");                                        
                                    } else {
                                        valSolucionDetalleDTO.setCausa("El numero de ciclos no es el correcto ");
                                    }

                                    listaDetalleValidacionSolucion.add(valSolucionDetalleDTO);

                                } else {
                                    // //Se genera un detalle de que no se encuentra la fecha
                                    ValidacionSolucionMezclaDetalleDTO validacionSolucionDetalleDTO = new ValidacionSolucionMezclaDetalleDTO();
                                    validacionSolucionDetalleDTO.setNombre("Fecha");
                                    validacionSolucionDetalleDTO.setIndicada(sdf.format(fechaComparada));
                                    validacionSolucionDetalleDTO.setPrescrita(sdf.format(fechaValidar));
                                    validacionSolucionDetalleDTO.setCausa("La fecha no se encuentra dentro del protocolo");
                                    listaDetalleValidacionSolucion.add(validacionSolucionDetalleDTO);
                                    validar = false;
                                }
                            /*} else {
                                
                            }*/
                                                        
                                protocoloResp.setValido(validar);                            
                                protocoloResp.setListaDetalleValidacionSolucion(listaDetalleValidacionSolucion);
                                listaProtocolosValidados.add(protocoloResp);
                            }    
                        } else {
                            //TODO pendiente para mostrar los medicamentos que no estan en el protocolo
                        }
                    }
                }
            }
            
            respuestaValidacionSolucionDTO.setListaCentralMezclasSolucion(listaProtocolosValidados);
            
        } catch(Exception ex) {
            throw new Exception("Error al buscar protocolos de mezcla de solución  " + ex.getMessage());
        }
        return respuestaValidacionSolucionDTO;
    }
    
}
