/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.ArrayList;
import java.util.List;
import mx.mc.enums.TipoDocumento_Enum;
import mx.mc.mapper.DiagnosticoMapper;
import mx.mc.mapper.FoliosMapper;
import mx.mc.mapper.NutricionParenteralDetalleMapper;
import mx.mc.mapper.NutricionParenteralMapper;
import mx.mc.mapper.PacienteServicioMapper;
import mx.mc.mapper.PrescripcionInsumoMapper;
import mx.mc.mapper.PrescripcionMapper;
import mx.mc.mapper.SolucionMapper;
import mx.mc.mapper.SurtimientoEnviadoMapper;
import mx.mc.mapper.SurtimientoInsumoMapper;
import mx.mc.mapper.SurtimientoMapper;
import mx.mc.mapper.VisitaMapper;
import mx.mc.model.DiagnosticoPaciente;
import mx.mc.model.Folios;
import mx.mc.model.NutricionParenteral;
import mx.mc.model.NutricionParenteralDetalleExtended;
import mx.mc.model.NutricionParenteralExtended;
import mx.mc.model.PacienteServicio;
import mx.mc.model.Prescripcion;
import mx.mc.model.PrescripcionInsumo;
import mx.mc.model.Solucion;
import mx.mc.model.Surtimiento;
import mx.mc.model.SurtimientoEnviado;
import mx.mc.model.SurtimientoInsumo;
import mx.mc.model.Visita;
import mx.mc.util.Comunes;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author gcruz
 */
@Service
public class NutricionParenteralServiceImpl extends GenericCrudServiceImpl<NutricionParenteral,String> implements  NutricionParenteralService {

    @Autowired
    private NutricionParenteralMapper nutricionParenteralMapper;
    
    @Autowired
    private NutricionParenteralDetalleMapper nutricionParenteralDetalleMapper;
    
    @Autowired
    private FoliosMapper foliosMapper;
    
    @Autowired
    private PrescripcionMapper prescripcionMapper;
    
    @Autowired
    private PrescripcionInsumoMapper prescripcionInsumoMapper;
    
    @Autowired
    private SurtimientoMapper surtimientoMapper;
    
    @Autowired
    private SurtimientoInsumoMapper surtimientoInsumoMapper;
    
    @Autowired
    private SurtimientoEnviadoMapper surtimientoEnviadoMapper;
    
    @Autowired
    private SolucionMapper solucionMapper;
    
    @Autowired 
    private PacienteServicioMapper pacienteServicioMapper;
    
    @Autowired
    private VisitaMapper visitaMapper;
    
    @Autowired
    private DiagnosticoMapper diagnosticoMapper; 
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean guardarOrdenPreparacionMezcla(NutricionParenteralExtended nutricionParenteral, Folios folioOrden) throws Exception {
        boolean resp = true;
        try {
            if (nutricionParenteral.getFolio().trim().equals("0")) {
                int tipoDoc = TipoDocumento_Enum.PRESCRIPCION.getValue();
                Folios folio = foliosMapper.obtenerPrefixPorDocument(tipoDoc);
                String folioDoc = Comunes.generaFolio(folio);

                folio.setSecuencia(Comunes.separaFolio(folioDoc));
                resp = foliosMapper.actualizaFolios(folio);
                nutricionParenteral.setFolio(folioDoc);
            }
            if (!resp) {
                throw new Exception("Error al actualizar el folio de la orden de Preparación. ");
            } else {
                resp = nutricionParenteralMapper.insertar(nutricionParenteral);
                if (resp) {
                    if (!nutricionParenteral.getListaMezclaMedicamentos().isEmpty()) {
                        resp = nutricionParenteralDetalleMapper.guardarListaNutricionParenteralDetalle(nutricionParenteral.getListaMezclaMedicamentos());
                    }
                    if (resp) {
                        resp = foliosMapper.actualizaFolios(folioOrden);
                    } else {
                        throw new Exception("Error al actualizar el folio de la orden de Preparación. ");
                    }
                } else {
                    throw new Exception("Error al insertar nutrición parenteral. ");
                }
            }
        } catch (Exception ex) {
            throw new Exception("Error al guardarOrdenPreparacionMezcla  " + ex.getMessage());
        }
        return resp;
    }
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean actualizarOrdenPreparacionMezcla(List<NutricionParenteralDetalleExtended> listaActualizar, List<NutricionParenteralDetalleExtended> listaBorrar, 
                                                    NutricionParenteralExtended nutricionParenteral) throws Exception {
        boolean resp = false;
        try {
            resp = nutricionParenteralMapper.actualizar(nutricionParenteral);
            if(resp) {
                if(!listaActualizar.isEmpty())
                    resp = nutricionParenteralDetalleMapper.actualizarListaNutricionParenteralDetalle(listaActualizar);
                if(resp) {
                    if(!nutricionParenteral.getListaMezclaMedicamentos().isEmpty())
                        resp = nutricionParenteralDetalleMapper.guardarListaNutricionParenteralDetalle(nutricionParenteral.getListaMezclaMedicamentos());
                        if(resp) {
                            if(!listaBorrar.isEmpty())
                                resp =   nutricionParenteralDetalleMapper.borrarListaNutricionParanDetalle(listaBorrar);
                        } else {
                            throw new Exception("Error al guardar nutrición parenteral detalle. ");
                        }                       
                } else {
                    throw new Exception("Error al actualizar nutrición parenteral detalle. ");
                }
            } else {
                throw new Exception("Error al actualizar nutrición parenteral. ");
            }
        } catch(Exception ex) {
            throw new Exception("Error al actualizarOrdenPreparacionMezcla  " + ex.getMessage());
        }
        return resp;
    }

    @Override
    public List<NutricionParenteralExtended> obtenerBusquedaLazy(String cadena, String idEstructura, int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, List<Integer> estatusSolucionLista
            , String tipoPrescripcion, String estatusSolucion, String nombreEstructura, String tipoSolucion, String nombreMedico
            , String folio, String fechaProgramada2, String nombrePaciente, String cama, String folioPrescripcion) throws Exception {
        List<NutricionParenteralExtended> listaOrdenesParanterales = new ArrayList<>();
        try {
            String order = sortOrder == SortOrder.ASCENDING ? "asc" : sortOrder == SortOrder.DESCENDING ? "desc" : null;
            listaOrdenesParanterales = nutricionParenteralMapper.obtenerBusquedaLazy(cadena, idEstructura, startingAt, maxPerPage, sortField, order, estatusSolucionLista
                    , tipoPrescripcion, estatusSolucion, nombreEstructura, tipoSolucion, nombreMedico
                    , folio, fechaProgramada2, nombrePaciente, cama, folioPrescripcion);
        } catch(Exception ex) {
            throw new Exception("Error al buscar ordenes de preparación  " + ex.getMessage());
        }
        return listaOrdenesParanterales;
    }

    @Override
    public Long obtenerBusquedaTotalLazy(String cadena, String idEstructura, List<Integer> estatusSolucionLista
            , String tipoPrescripcion, String estatusSolucion, String nombreEstructura, String tipoSolucion, String nombreMedico
            , String folio, String fechaProgramada2, String nombrePaciente, String cama, String folioPrescripcion
    ) throws Exception {
        try {
            return nutricionParenteralMapper.obtenerBusquedaTotalLazy(cadena, idEstructura, estatusSolucionLista
                    , tipoPrescripcion, estatusSolucion, nombreEstructura, tipoSolucion, nombreMedico
                    , folio, fechaProgramada2, nombrePaciente, cama, folioPrescripcion);
        } catch(Exception ex) {
            throw new Exception("Error al obtenerTotal de busqueda lazy orden de preparacion" + ex.getMessage());
        }
    }

    @Override
    public NutricionParenteralExtended obtenerNutricionParenteralById(String idNutricionParenteral) throws Exception {
        try {
            return nutricionParenteralMapper.obtenerNutricionParenteralById(idNutricionParenteral);
        } catch(Exception ex) {
            throw new Exception("Error al buscar la nutricionParenteral   " + ex.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean generarSolucionNutricionParenteral(boolean actualiza, Prescripcion prescripcion, List<PrescripcionInsumo> listaPrescripcionInsumos, Surtimiento surtimiento, List<SurtimientoInsumo> listaSurtimientoInsumos, 
            List<SurtimientoEnviado> listaSurtimientoEnviado, Solucion solucion, Folios folioSurtimiento, NutricionParenteral nutricionParenteral, List<DiagnosticoPaciente> listDiagnosticosPaciente) throws Exception {
        boolean resp = false;
        try {
            if(actualiza) {
                resp = prescripcionMapper.actualizar(prescripcion);
            } else {
                resp = prescripcionMapper.insertar(prescripcion);
            }            
            if(resp) {
                if(!actualiza)
                    resp = prescripcionInsumoMapper.registraMedicamentoList(listaPrescripcionInsumos);
                if(resp) {
                    int tipoDoc = TipoDocumento_Enum.SURTIMIENTO_DE_PRESCRIPCION.getValue();
                    Folios folio = foliosMapper.obtenerPrefixPorDocument(tipoDoc);
                    String folioDoc = Comunes.generaFolio(folio);

                    folio.setSecuencia(Comunes.separaFolio(folioDoc));
                    resp = foliosMapper.actualizaFolios(folio);
                    surtimiento.setFolio(folioDoc);
                    
                    resp = surtimientoMapper.insertar(surtimiento);
                    if(resp) {
                        resp = surtimientoInsumoMapper.registraSurtimientoInsumoList(listaSurtimientoInsumos);
                        if(resp) {
                            if (listaSurtimientoEnviado != null && !listaSurtimientoEnviado.isEmpty()) {
                                resp = surtimientoEnviadoMapper.registraSurtimientoEnviadoList(listaSurtimientoEnviado);
                            }
                            
                            if(resp) {
                                resp = solucionMapper.insertar(solucion);
                                if(resp) {
                                    resp = foliosMapper.actualizaFolios(folioSurtimiento);
                                    if(resp) {
                                        resp = nutricionParenteralMapper.actualizar(nutricionParenteral);
                                        if(resp) {
                                            if(!listDiagnosticosPaciente.isEmpty()) {
                                                resp = diagnosticoMapper.eliminaDiagnosticoList(prescripcion.getIdPrescripcion());                                                
                                                resp = diagnosticoMapper.registraDiagnosticoList(listDiagnosticosPaciente);
                                                if(!resp) 
                                                    throw new Exception("Error al eliminar e insertar los diagnosticos de paciente");                                                
                                            }
                                        } else {
                                            throw new Exception("Error al actualizar la nutricionParenteral");
                                        }                                                                                                                            
                                    } else {
                                        throw new Exception("Error al actualizar el folio de surtimiento");
                                    }                                   
                                } else {
                                    throw new Exception("Error al insertar la solución");
                                }                                
                            } else {
                                throw new Exception("Error al insertar el surtiiento enviados");
                            }
                        } else {
                            throw new Exception("Error al insertar el surtiiento insumos");
                        }
                    } else {
                        throw new Exception("Error al insertar el surtiiento");
                    }
                } else {
                    throw new Exception("Error al insertar la prescripción insumos");
                }
            } else {
                throw new Exception("Error al insertar la prescripción");
            }
        } catch(Exception ex) {
            throw new Exception("Error al generar la solucion de nutrición parenteral" + ex.getMessage());
        }
        return resp;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean cerrarServicioYAsignarNuevoServicio(PacienteServicio servicioCerrar, PacienteServicio asignarServicio) throws Exception {
        boolean resp = false;
        try {
            resp = pacienteServicioMapper.actualizar(servicioCerrar);
            if(resp) {
                resp = pacienteServicioMapper.insertar(asignarServicio);
                if(!resp)
                    throw new Exception("Error al intentar registrar el servicio del paciente");
            } else {
                throw new Exception("Error al intentar cerrar el servicio del paciente");
            }
        } catch(Exception ex) {
            throw new Exception("Error al cambiar de servicio a paciente " + ex.getMessage());
        }
        return resp;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean generarNuevaVisitaYPacienteServicio(Visita visita, PacienteServicio pacienteServicio) throws Exception {
        boolean resp = false;
        try {
            resp = visitaMapper.insertar(visita);
            if(resp) {
                resp = pacienteServicioMapper.insertar(pacienteServicio);
                if(!resp)
                    throw new Exception("Error al intentar registrar pacienteServicio");
            } else {
                throw new Exception("Error al intentar registrar la visita  ");
            }
        } catch(Exception ex) {
            throw new Exception("Error al generarNuevaVisitaYPacienteServicio   " + ex.getMessage());
        }
        return resp;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean registrarPrecripcionSurtimientoSolucionNPT(
            Prescripcion prescripcion,
             List<PrescripcionInsumo> listaPrescripcionInsumos,
             Surtimiento surtimiento,
             List<SurtimientoInsumo> listaSurtimientoInsumos,
             List<SurtimientoEnviado> listaSurtimientoEnviado,
             Solucion solucion,
             NutricionParenteralExtended nutricionParenteral,
             List<DiagnosticoPaciente> listDiagnosticosPaciente
    ) throws Exception {
        boolean resp = false;
        try {
            Folios folio = foliosMapper.obtenerPrefixPorDocument(TipoDocumento_Enum.PRESCRIPCION.getValue());
            String folioPrescripcion = Comunes.generaFolio(folio);
            folio.setSecuencia(Comunes.separaFolio(folioPrescripcion));
            resp = foliosMapper.actualizaFolios(folio);
            
            if (!resp) {
                throw new Exception("Error al generar Folio de la Prescripción.");
            } else {
                
                prescripcion.setFolio(folioPrescripcion);
                resp = prescripcionMapper.insertar(prescripcion);
                
                if (!resp) {
                    throw new Exception("Error al guardar la Prescripción.");
                } else {
                
                    resp = prescripcionInsumoMapper.registraMedicamentoList(listaPrescripcionInsumos);
                
                    if (!resp) {
                        throw new Exception("Error al registrar la lista de Prescripción Insumo.");
                    } else {

                        folio = foliosMapper.obtenerPrefixPorDocument(TipoDocumento_Enum.ORDEN_MANUAL_SOLUCIONES.getValue());
                        String folioSurtimiento = Comunes.generaFolio(folio);
                        folio.setSecuencia(Comunes.separaFolio(folioSurtimiento));
                        resp = foliosMapper.actualizaFolios(folio);

                        if (!resp) {
                            throw new Exception("Error al generar Folio del Surtimiento.");
                        } else {                            
                    
                            surtimiento.setFolio(folioSurtimiento);
                            resp = surtimientoMapper.insertar(surtimiento);
                            
                            if (!resp) {
                                throw new Exception("Error al registrar el Surtimiento.");
                            } else {
                            
                                resp = surtimientoInsumoMapper.registraSurtimientoInsumoList(listaSurtimientoInsumos);
                                
                                if (!resp) {
                                    throw new Exception("Error al registrar la lista de insumos del Surtimiento.");
                                } else {
                                
                                    resp = solucionMapper.insertar(solucion);
                                    
                                    if (!resp) {
                                        throw new Exception("Error al registrar la lista de insumos enviados del Surtimiento.");
                                    } else {
                                    
                                        nutricionParenteral.setFolio(prescripcion.getFolio());
                                        nutricionParenteral.setFolioPreparacion(surtimiento.getFolio());
                                        resp = nutricionParenteralMapper.insertar(nutricionParenteral);
                                        
                                        if (!resp) {
                                            throw new Exception("Error al insertar nutrición parenteral. ");
                                        } else {
                                            
                                            if (!nutricionParenteral.getListaMezclaMedicamentos().isEmpty()) {
                                            
                                                resp = nutricionParenteralDetalleMapper.guardarListaNutricionParenteralDetalle(nutricionParenteral.getListaMezclaMedicamentos());
                                                
                                                if (!resp) {
                                                    throw new Exception("Error al insertar lista de insumos de prescripcion nutrición parenteral. ");
                                                }
                                            }
                                            
                                            if (!listDiagnosticosPaciente.isEmpty()) {
                                            
                                                resp = diagnosticoMapper.registraDiagnosticoList(listDiagnosticosPaciente);
                                                
                                                if (!resp) {
                                                    throw new Exception("Error al insertar los diagnosticos de paciente.");
                                                }
                                            }
// TODO: hasta que se valide, se registra
//                                            if (listaSurtimientoEnviado != null) {
//                                                if (!listaSurtimientoEnviado.isEmpty()) {
//                                                    resp = surtimientoEnviadoMapper.registraSurtimientoEnviadoList(listaSurtimientoEnviado);
//                                                    if (!resp) {
//                                                        throw new Exception("Error al registrar la lista de insumos enviados del Surtimiento.");
//                                                    }
//                                                }
//                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            throw new Exception("Error al generar la solucion de nutrición parenteral" + ex.getMessage());
        }
        return resp;
    }

    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean actualizarPrecripcionSurtimientoSolucionNPT(
            Prescripcion prescripcion,
            List<PrescripcionInsumo> listaPrescripcionInsumos,
            Surtimiento surtimiento,
            List<SurtimientoInsumo> listaSurtimientoInsumos,
            List<SurtimientoEnviado> listaSurtimientoEnviado,
            Solucion solucion,
            NutricionParenteralExtended nutricionParenteral,
            List<DiagnosticoPaciente> listDiagnosticosPaciente
    ) throws Exception {
        boolean resp = false;
        try {
            resp = prescripcionMapper.actualizar(prescripcion);
            if (!resp) {
                throw new Exception("Error al guardar la Prescripción.");
            } else {
                PrescripcionInsumo pit;
                for (PrescripcionInsumo pi : listaPrescripcionInsumos) {
                    pit = new PrescripcionInsumo();
                    pit.setIdPrescripcionInsumo(pi.getIdPrescripcionInsumo());
                    PrescripcionInsumo o = prescripcionInsumoMapper.obtener(pit);
                    if (o == null) {
                        resp = prescripcionInsumoMapper.insertar(pi);
                    } else {
                        resp = prescripcionInsumoMapper.actualizar(pi);
                    }
                    if (!resp) {
                        throw new Exception("Error al guardar la lista de Prescripción Insumo.");
                    }
                }
                resp = surtimientoMapper.actualizar(surtimiento);
                if (!resp) {
                    throw new Exception("Error al guardar el Surtimiento.");
                } else {
                    SurtimientoInsumo sit;
                    for (SurtimientoInsumo si : listaSurtimientoInsumos) {
                        sit = new SurtimientoInsumo();
                        sit.setIdSurtimientoInsumo(si.getIdSurtimientoInsumo());
                        SurtimientoInsumo o = surtimientoInsumoMapper.obtener(sit);
                        if (o == null) {
                            resp = surtimientoInsumoMapper.insertar(si);
                        } else {
                            resp = surtimientoInsumoMapper.actualizar(si);
                        }
                        if (!resp) {
                            throw new Exception("Error al guardar la lista de insumos del Surtimiento.");
                        }
                    }
                    if (listaSurtimientoEnviado != null && !listaSurtimientoEnviado.isEmpty()) {
                        SurtimientoEnviado senv;
                        for (SurtimientoEnviado se : listaSurtimientoEnviado) {
                            senv = new SurtimientoEnviado();
                            senv.setIdSurtimientoEnviado(se.getIdSurtimientoEnviado());
                            SurtimientoEnviado o = surtimientoEnviadoMapper.obtener(senv);
                            if (o == null) {
                                resp = surtimientoEnviadoMapper.insertar(se);
                            } else {
                                resp = surtimientoEnviadoMapper.actualizar(se);
                            }
                            if (!resp) {
                                throw new Exception("Error al guardar la lista de insumos por enviar del Surtimiento.");
                            }
                        }
                    }
//                    resp = surtimientoInsumoMapper.actualizarEstatusSurtimientoInsumoList(listaSurtimientoInsumos);
//                    if (!resp) {
//                        throw new Exception("Error al guardar la lista de insumos del Surtimiento.");
//                    } else {
//                        resp = surtimientoEnviadoMapper.actualizaSurtimientoEnviadoList(listaSurtimientoEnviado);
//                        if (!resp) {
//                            throw new Exception("Error al guardar la lista de insumos enviados del Surtimiento.");
//                        }
                    resp = solucionMapper.actualizar(solucion);
                    if (!resp) {
                        throw new Exception("Error al guardar la lista de insumos enviados del Surtimiento.");
                    } else {
                        nutricionParenteral.setFolio(prescripcion.getFolio());
                        nutricionParenteral.setFolioPreparacion(surtimiento.getFolio());
                        resp = nutricionParenteralMapper.actualizar(nutricionParenteral);

                        if (!resp) {
                            throw new Exception("Error al insertar nutrición parenteral. ");
                        } else {

                            if (!nutricionParenteral.getListaMezclaMedicamentos().isEmpty()) {
                                resp = nutricionParenteralDetalleMapper.actualizarListaNutricionParenteralDetalle(nutricionParenteral.getListaMezclaMedicamentos());

                                if (!resp) {
                                    throw new Exception("Error al insertar lista de insumos de prescripcion nutrición parenteral. ");
                                }
                            }

                            if (!listDiagnosticosPaciente.isEmpty()) {
                                resp = diagnosticoMapper.eliminaDiagnosticoList(listDiagnosticosPaciente.get(0).getIdPrescripcion());
                                if (!resp) {
                                    throw new Exception("Error al actualizar los diagnosticos de paciente.");
                                } else {
                                    resp = diagnosticoMapper.registraDiagnosticoList(listDiagnosticosPaciente);   
                                    if (!resp) {
                                        throw new Exception("Error al insertar los diagnosticos de paciente.");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            throw new Exception("Error al generar la solucion de nutrición parenteral" + ex.getMessage());
        }
        return resp;
    }

    
}
