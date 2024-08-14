package mx.mc.service;

import java.util.ArrayList;
import java.util.List;
import mx.mc.enums.TipoDocumento_Enum;
import mx.mc.mapper.FoliosMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.PacienteMapper;
import mx.mc.mapper.PacienteServicioMapper;
import mx.mc.mapper.PacienteUbicacionMapper;
import mx.mc.mapper.TurnoMedicoMapper;
import mx.mc.mapper.VisitaMapper;
import mx.mc.model.Estructura;
import mx.mc.model.Folios;
import mx.mc.model.Paciente;
import mx.mc.model.PacienteDomicilio;
import mx.mc.model.PacienteResponsable;
import mx.mc.model.PacienteServicio;
import mx.mc.model.PacienteUbicacion;
import mx.mc.model.Paciente_Extended;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.TurnoMedico;
import mx.mc.model.Visita;
import mx.mc.util.Comunes;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author AORTIZ
 */
@Service
public class PacienteServiceImplements extends GenericCrudServiceImpl<Paciente, String> implements PacienteService{
    
    @Autowired
    private PacienteMapper pacienteMapper;
    
    @Autowired
    private TurnoMedicoMapper turnoMedicoMapper;
    
    @Autowired
    private FoliosMapper foliosMapper;
    
    @Autowired
    private VisitaMapper visitaMapper;
    
    @Autowired
    private PacienteServicioMapper pacienteServicioMapper;
    
    @Autowired
    private PacienteUbicacionMapper pacienteUbicacionMapper;
    
    @Autowired
    public PacienteServiceImplements(GenericCrudMapper<Paciente, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
    
    @Override
    public List<Paciente> obtenerUltimosPacientes(int numeroRegistros) throws Exception {
       try {
                List<Paciente> listaPacientes = pacienteMapper.
                        obtenerUltimosRegistros(numeroRegistros);
                if (listaPacientes == null) {
                    listaPacientes = new ArrayList<>();
                }
                return listaPacientes;
        } catch (Exception ex) {
            throw new Exception("Error obtener. " + ex.getMessage());
        }
       
    }
    
    @Transactional (propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean insertarPaciente(Paciente paciente,PacienteDomicilio pacienteDom, 
            PacienteResponsable pacienteResp , List<TurnoMedico> listaTurnos) throws Exception {
        
        boolean res = true;
        try {
            String numeroPaciente;
            if (paciente.getPacienteNumero() == null || !paciente.getPacienteNumero().trim().isEmpty()) {
                // Consultar y generar Folio
                int tipoDoc = TipoDocumento_Enum.PACIENTE_MANUAL.getValue();
                Folios folio = foliosMapper.obtenerPrefixPorDocument(tipoDoc);
                numeroPaciente = Comunes.generaFolio(folio);
                folio.setSecuencia(Comunes.separaFolio(numeroPaciente));
                res = foliosMapper.actualizaFolios(folio);
                paciente.setPacienteNumero(numeroPaciente);
            }
            if (!res) {
                throw new Exception("Error al registrar Folio de Paciente. ");
            } else {
                res = (pacienteMapper.insertarPaciente(paciente) > 0);
                if (!res) {
                    throw new Exception("Error al registrar Paciente. ");
                    
                } else {
                    res = (pacienteMapper.insertarPacienteDomicilio(pacienteDom) > 0);
                    if (!res){
                        throw new Exception("Error al registrar domicilio de Paciente. ");
                    } else {
                        if (pacienteResp.getNombreCompleto() == null) {
                            return true;
                            
                        } else {
                            res = (pacienteMapper.insertarPacienteResponsable(pacienteResp) > 0);
                            if (!res) {
                                throw new Exception("Error al registrar responsable de Paciente. ");
                            } else {
                                if (!listaTurnos.isEmpty()) {
                                    res = turnoMedicoMapper.insertarListaTurnos(listaTurnos);
                                    if (!res){
                                        throw new Exception("Error al registrar turnos de Paciente. ");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }catch (Exception ex) {
            throw new Exception("Error al registrar Manualmente al Paciente. " + ex.getMessage());
        }
        return res;
    }

    @Transactional (propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean actualizarPaciente(Paciente paciente, PacienteDomicilio pacienteDom, 
            PacienteResponsable pacienteResp, List<TurnoMedico> listaTurnos) throws Exception {
         try {
            pacienteMapper.actualizarPaciente(paciente);
            pacienteMapper.actualizarPacienteDomicilio(pacienteDom);
            if (pacienteResp.getNombreCompleto() != null) {
                if (pacienteResp.getIdPacienteResponsable() == null) {
                    pacienteResp.setIdPacienteResponsable(Comunes.getUUID());
                    pacienteResp.setInsertFecha(pacienteResp.getUpdateFecha());
                    pacienteResp.setInsertIdUsuario(pacienteResp.getUpdateIdUsuario());
                    pacienteMapper.
                            insertarPacienteResponsable(pacienteResp);
                } else {
                    pacienteMapper.
                            actualizarPacienteResponsable(pacienteResp);
                }
            }
            if (!listaTurnos.isEmpty()) {
                turnoMedicoMapper.eliminarByIdUsuario(paciente.getIdPaciente());
                turnoMedicoMapper.insertarListaTurnos(listaTurnos);
            }
            return true;             
        }catch (Exception ex) {
            throw new Exception("Error obtener. " + ex.getMessage());
        }
    }

    @Override
    public Paciente obtenerPacienteByIdPaciente(String idPaciente) throws Exception {
        try {
                return pacienteMapper.
                        obtenerPacienteByIdPaciente(idPaciente);
        } catch (Exception ex) {
            throw new Exception("Error obtener. " + ex.getMessage());
        }
    }

    @Override
    public List<Paciente_Extended> obtenerRegistrosPorCriterioDeBusqueda(String criterioBusqueda, int numRegistros) throws Exception {
        try {
                List<Paciente_Extended> listaPacientes = pacienteMapper
                        .obtenerRegistrosPorCriterioDeBusqueda(criterioBusqueda,numRegistros);
                if (listaPacientes == null) {
                    listaPacientes = new ArrayList<>();
                }
                return listaPacientes;
        } catch (Exception ex) {
            throw new Exception("Error obtener. " + ex.getMessage());
        }
    }
    public List<Paciente> obtenerRegistrosPorCriterioDeBusqueda2(String criterioBusqueda, int numRegistros) throws Exception {
        try {
                List<Paciente> listaPacientes = pacienteMapper
                        .obtenerRegistrosPorCriterioDeBusqueda2(criterioBusqueda,numRegistros);
                if (listaPacientes == null) {
                    listaPacientes = new ArrayList<>();
                }
                return listaPacientes;
        } catch (Exception ex) {
            throw new Exception("Error obtener. " + ex.getMessage());
        }
    }
        
    @Override
    public List<Paciente_Extended> obtenerPacientesPorIdUnidad(
            String idUnidadMedica
            , String idUnidadMedicaPeriferico
            , String cadenaBusqueda
            , int numRegistros
            ,List<Integer> listEstatusPaciente
    ) throws Exception {
       try {
                List<Paciente_Extended> listaPacientes = 
                pacienteMapper.obtenerPacientesPorIdUnidad(
                        idUnidadMedica, idUnidadMedicaPeriferico, cadenaBusqueda, numRegistros,listEstatusPaciente);
                if (listaPacientes == null) {
                    listaPacientes = new ArrayList<>();
                }
                return  listaPacientes;
        } catch (Exception ex) {
            throw new Exception("Error al obtener Pacientes en Prescripción. " + ex.getMessage());
        }
       
    }
    
    @Override
    public List<Paciente_Extended> obtenerPacientePorIdUnidadAct(List<Estructura> listaEstructuras, String idUnidadMedicaPeriferico, String cadenaBusqueda, int numRegistros, List<Integer> listEstatusPaciente) throws Exception {
        try {
            List<Paciente_Extended> listaPacientes
                    = pacienteMapper.obtenerPacientePorIdUnidadAct(
                            listaEstructuras, idUnidadMedicaPeriferico, cadenaBusqueda, numRegistros, listEstatusPaciente);
            if (listaPacientes == null) {
                listaPacientes = new ArrayList<>();
            }
            return listaPacientes;
        } catch (Exception e) {
            throw new Exception("Error al obtener los pacientes en Prescripción. " + e.getMessage());
        }
    }
    
    
    @Override
    public List<Paciente_Extended> obtenerPacientePorIdUnidadActLazy(ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage, List<Estructura> listaEstructuras, String idUnidadMedicaPeriferico, List<Integer> listEstatusPaciente) throws Exception {
        try {
            List<Paciente_Extended> listaPacientes
                    = pacienteMapper.obtenerPacientePorIdUnidadActLazy(paramBusquedaReporte, startingAt, maxPerPage, listaEstructuras, idUnidadMedicaPeriferico, listEstatusPaciente);
            if (listaPacientes == null) {
                listaPacientes = new ArrayList<>();
            }
            return listaPacientes;
        } catch (Exception e) {
            throw new Exception("Error al obtener los pacientes en Prescripción");
        }
    }   
    
    @Override
    public Long obtenerTotalPacientePorIdUnidadActLazy(ParamBusquedaReporte paramBusquedaReporte, List<Estructura> listaEstructuras, String idUnidadMedicaPeriferico, List<Integer> listEstatusPaciente) throws Exception {
        try {
            return pacienteMapper.obtenerTotalPacientePorIdUnidadActLazy(paramBusquedaReporte, listaEstructuras, idUnidadMedicaPeriferico, listEstatusPaciente);
        } catch (Exception e) {
            throw new Exception("Error al Obtener el Total de Registros de Prescripciones");
        }
    }
    
    
    @Override
    public List<Paciente_Extended> obtenerPacientesPorIdUnidadConsExt(
            String idUnidadMedica
            , String idUnidadMedicaPeriferico
            , String cadenaBusqueda
            , int numRegistros
            ,List<Integer> listEstatusPaciente
            ,List<TurnoMedico> listaIdTurno
    ) throws Exception {
       try {
                List<Paciente_Extended> listaPacientes = 
                pacienteMapper.obtenerPacientesPorIdUnidadConsExt(
                        idUnidadMedica, idUnidadMedicaPeriferico, cadenaBusqueda, numRegistros,listEstatusPaciente,listaIdTurno);
                if (listaPacientes == null) {
                    listaPacientes = new ArrayList<>();
                }
                return  listaPacientes;
        } catch (Exception ex) {
            throw new Exception("Error al obtener Pacientes en Prescripción. " + ex.getMessage());
        }
       
    }

    @Override
    public List<Paciente_Extended> obtenerUltimosPacientesPacienteExtended(int numeroRegistros) throws Exception {
        try {
                return pacienteMapper
                        .obtenerUltimosRegistrosPacienteExtended(numeroRegistros);
        } catch (Exception ex) {
            throw new Exception("Error obtener. " + ex.getMessage());
        }
    }
     
    
    @Override
    public Paciente_Extended obtenerPacienteCompletoPorId(String idPaciente) throws Exception {
        try {
            return pacienteMapper.obtenerPacienteCompletoPorId(idPaciente);
        } catch (Exception ex) {
            throw new Exception("Error obtener. " + ex.getMessage());
        }
    }

    @Override
    public List<Paciente_Extended> obtenerPacientesYCamas(String idEstructura , 
            Integer numRegistros, List<Estructura> listaidEstructura) throws Exception {
        try {
            return pacienteMapper.obtenerPacientesYCamas(idEstructura, numRegistros, listaidEstructura);
        } catch (Exception ex) {
            throw new Exception("Error obtener. " + ex.getMessage());
        }
    }

    @Override
    public List<Paciente_Extended> obtenerPacientesYCamasPorCriterioBusqueda(
            String idEstructura , Integer numRegistros , 
            String criterioBusqueda) throws Exception {
        try {
            return pacienteMapper.obtenerPacientesYCamasPorCriterioBusqueda(
                    idEstructura, numRegistros, criterioBusqueda);
        } catch (Exception ex) {
            throw new Exception("Error obtener. " + ex.getMessage());
        }
    }

    @Override
    public List<Paciente_Extended> obtenerPacietesConPrescripcion(int numRegistros , 
            List<String> idsEstructura, String textoBusqueda , 
            List<Integer> listaEstatusPresc) throws Exception {
        try {
            return pacienteMapper.obtenerPacietesConPrescripcion(numRegistros , 
                    idsEstructura, textoBusqueda , listaEstatusPresc);
        } catch (Exception ex) {
            throw new Exception("Error obtener. " + ex.getMessage());
        }
    }
    
    @Override
    public List<Paciente> obtenerPacietesConPrescripcionList(int numRegistros , 
            List<String> idsEstructura, String textoBusqueda , 
            List<Integer> listaEstatusPresc) throws Exception {
        try {
            return pacienteMapper.obtenerPacietesConPrescripcionList(numRegistros , 
                    idsEstructura, textoBusqueda , listaEstatusPresc);
        } catch (Exception ex) {
            throw new Exception("Error obtener. " + ex.getMessage());
        }
    }

    @Override
    public Paciente_Extended obtenerNombreEstructurabyIdpaciente(String idPaciente) throws Exception {
        try {
            return pacienteMapper.obtenerNombreEstructurabyIdpaciente(idPaciente);
        } catch (Exception e) {
            throw new Exception("Error al botener el Nombre de la Estructura" + e.getMessage());
        }
    }    

    @Override
    public List<Paciente_Extended> obtenerRegistrosPorCriterioDeBusquedaLazy(ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage, String sortField, SortOrder sortOrder) throws Exception {
        try {
            String order = sortOrder==SortOrder.ASCENDING ? "asc": sortOrder==SortOrder.DESCENDING ? "desc" : null;            
            return pacienteMapper.obtenerRegistrosPorCriterioDeBusquedaLazy(paramBusquedaReporte, startingAt, maxPerPage, sortField, order);
        } catch (Exception e) {
            throw  new Exception("Error al obtener los Registros de Pacientes. " + e.getMessage());
        }
    }

    @Override
    public Long obtenerTotalRegistrosPorCriterioDeBusquedaLazy(ParamBusquedaReporte paramBusquedaReporte) throws Exception {
        try {
            return pacienteMapper.obtenerTotalRegistrosPorCriterioDeBusquedaLazy(paramBusquedaReporte);
        } catch (Exception e) {
            throw  new Exception("Error al obtener los Registros de Pacientes. " + e.getMessage());
        }
    }    

    @Override
    public List<Paciente> obtenerPacientes(String cadenaBusqueda) throws Exception {
        try {
            return pacienteMapper.obtenerPacientes(cadenaBusqueda);
        } catch (Exception e) {
            throw new Exception("Error al obtener los Pacientes " + e.getMessage());
        }
    }
    
    @Override
    public Integer obtenerNumeroMaximoPaciente() throws Exception {
        Integer numeroPaciente = 0;
        try {
            numeroPaciente = pacienteMapper.obtenerNumeroMaximoPaciente();
        } catch(Exception e) {
            throw new Exception("Error al obtener el numero de paciente " + e.getMessage());
        }
        return numeroPaciente;
    }
    
    @Override
    public List<Paciente_Extended> obtenerRegistrosPorCriterioDeBusquedaYEstructura(String criterioBusqueda, String idEstructura, int numRegistros) throws Exception {
        try {
                List<Paciente_Extended> listaPacientes = pacienteMapper
                        .obtenerRegistrosPorCriterioDeBusquedaYEstructura(criterioBusqueda, idEstructura, numRegistros);
                if (listaPacientes == null) {
                    listaPacientes = new ArrayList<>();
                }
                return listaPacientes;
        } catch (Exception ex) {
            throw new Exception("Error obtener. " + ex.getMessage());
        }
    }

    @Override
    public Paciente obtenerPacienteByNumeroPaciente(String numeroPaciente) throws Exception {
        try {
            return pacienteMapper.obtenerPacienteByNumeroPaciente(numeroPaciente);
        } catch (Exception e) {
            throw new Exception("Error al obtener el paciente " + e.getMessage());
        }
    }

    @Override
    public List<Paciente_Extended> searchPacienteAutoComplete(String cadena) throws Exception{
        List<Paciente_Extended> listaPacientes;
        try {
            listaPacientes = pacienteMapper.obtenerRegistrosPorCriterioDeBusqueda(cadena, 0);
            for (Paciente_Extended paciente : listaPacientes) {
                String nomTemp = paciente.getNombreCompleto() + " " + paciente.getApellidoPaterno() + " " + paciente.getApellidoMaterno();
                paciente.setNombrePaciente(nomTemp.trim());
            }
        } catch (Exception ex) {
            listaPacientes = new ArrayList<>();
            throw new Exception("Error al momento de obtener pacientes en autocomplete " + ex.getMessage());
        }
        return listaPacientes;
    }
    
    @Override
    public List<Paciente> obtenerDerechohabientes(String claveDerechohabiencia, String idPaciente) throws Exception {
        try {
            List<Paciente> listaPacientes = pacienteMapper.obtenerDerechohabientes(claveDerechohabiencia, idPaciente);
            if (listaPacientes == null) {
                listaPacientes = new ArrayList<>();
            }
            return listaPacientes;
        } catch (Exception ex) {
            throw new Exception("Error obtenerDerechohabientes. " + ex.getMessage());
        }
    }

    @Override
    public Paciente obtenerPacienteByRfcCvDehCurp(String rfc, int claveDerechoHabiencia, String curp) throws Exception {
        try {
            return pacienteMapper.obtenerPacienteByRfcCvDehCurp(rfc, claveDerechoHabiencia, curp);
        } catch (Exception e) {
            throw new Exception("Error en obtenerPacienteByNumeroPacienteRfcCvDeh() " + e.getMessage());
        }
    }

    @Override
    public Paciente_Extended obtenerPacienteByIdPrescripcion(String idPrescripcion) throws Exception {
        try {
            return pacienteMapper.obtenerPacienteByIdPrescripcion(idPrescripcion);
        } catch(Exception ex) {
            throw new Exception("Ocurrio un error al momento de btener información de paciente por idPrescripcion:  " + ex.getMessage());
        }
    }
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean insertarPacienteVisitaServicioUbicacion(
            Paciente paciente,
            PacienteDomicilio pacienteDom,
            PacienteResponsable pacienteResp,
            List<TurnoMedico> listaTurnos,
            Visita v,
            PacienteServicio ps,
            PacienteUbicacion pu
    ) throws Exception {

        boolean res = true;
        try {
            String numeroPaciente;
            if (paciente.getPacienteNumero() == null || paciente.getPacienteNumero().trim().isEmpty()) {
                // Consultar y generar Folio
                int tipoDoc = TipoDocumento_Enum.PACIENTE_MANUAL.getValue();
                Folios folio = foliosMapper.obtenerPrefixPorDocument(tipoDoc);
                numeroPaciente = Comunes.generaFolio(folio);
                folio.setSecuencia(Comunes.separaFolio(numeroPaciente));
                res = foliosMapper.actualizaFolios(folio);
                paciente.setPacienteNumero(numeroPaciente);
            }
            if (!res) {
                throw new Exception("Error al registrar Folio de Paciente. ");
            } else {
                res = (pacienteMapper.insertarPaciente(paciente) > 0);
                if (!res) {
                    throw new Exception("Error al registrar Paciente. ");

                } else {
                    res = (pacienteMapper.insertarPacienteDomicilio(pacienteDom) > 0);
                    if (!res) {
                        throw new Exception("Error al registrar domicilio de Paciente. ");

                    } else {
// TODO: 28dic22 considerar el registro de paciente responsable
//                        if (pacienteResp != null) {
//                            res = (pacienteMapper.insertarPacienteResponsable(pacienteResp) > 0);
//                            if (!res) {
//                                throw new Exception("Error al registrar responsable de Paciente. ");
//
//                            }
//                        }

                        if (!listaTurnos.isEmpty()) {
                            res = turnoMedicoMapper.insertarListaTurnos(listaTurnos);
                            if (!res) {
                                throw new Exception("Error al registrar turnos de Paciente. ");
                            }
                        }

                        Visita vTmp = new Visita();
                        vTmp.setIdPaciente(v.getIdPaciente());
                        vTmp = visitaMapper.obtenerVisitaAbierta(vTmp);
                        if (vTmp == null) {
                            res = visitaMapper.insertar(v);
                        } else {
                            v = vTmp;
                        }

                        if (!res) {
                            throw new Exception("Error al registrar visita de paciente. ");

                        } else {
                            PacienteServicio psTemp = new PacienteServicio();
                            psTemp.setIdVisita(v.getIdVisita());
                            psTemp.setIdEstructura(ps.getIdEstructura());
                            psTemp = pacienteServicioMapper.obtenerPacienteServicioAbierto(psTemp);
                            if (psTemp == null) {
                                res = pacienteServicioMapper.insertar(ps);
                            } else {
                                ps = psTemp;
                            }

                            if (!res) {
                                throw new Exception("Error al registrar asignación paciente a servicio. ");

                            } else {

                                PacienteUbicacion puTmp = new PacienteUbicacion();
                                puTmp.setIdPacienteServicio(ps.getIdPacienteServicio());
                                puTmp.setIdCama(pu.getIdCama());
                                puTmp = pacienteUbicacionMapper.obtenerCamaAsignada(puTmp);
                                if (puTmp == null) {
                                    res = pacienteUbicacionMapper.insertar(pu);
                                } else {
                                    pu = puTmp;
                                }

                                if (!res) {
                                    throw new Exception("Error al registrar asignación pcaiente a cama. ");
                                }
                            }
                        }

                    }
                }
            }
        } catch (Exception ex) {
            throw new Exception("Error al registrar Manualmente al Paciente. " + ex.getMessage());
        }
        return res;
    }

    @Transactional (propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean insertarPacienteVisitaServicio(Paciente paciente,PacienteDomicilio pacienteDom, 
            PacienteResponsable pacienteResp , List<TurnoMedico> listaTurnos,
            Visita v, PacienteServicio ps) throws Exception {
        
        boolean res = true;
        try {
            String numeroPaciente;
            if (paciente.getPacienteNumero() == null || !paciente.getPacienteNumero().trim().isEmpty()) {
                // Consultar y generar Folio
                int tipoDoc = TipoDocumento_Enum.PACIENTE_MANUAL.getValue();
                Folios folio = foliosMapper.obtenerPrefixPorDocument(tipoDoc);
                numeroPaciente = Comunes.generaFolio(folio);
                folio.setSecuencia(Comunes.separaFolio(numeroPaciente));
                res = foliosMapper.actualizaFolios(folio);
                paciente.setPacienteNumero(numeroPaciente);
            }
            if (!res) {
                throw new Exception("Error al registrar Folio de Paciente. ");
            } else {
                res = (pacienteMapper.insertarPaciente(paciente) > 0);
                if (!res) {
                    throw new Exception("Error al registrar Paciente. ");
                    
                } else {
                    res = (pacienteMapper.insertarPacienteDomicilio(pacienteDom) > 0);
                    if (!res){
                        throw new Exception("Error al registrar domicilio de Paciente. ");
                    } else {
                        if (pacienteResp.getNombreCompleto() == null) {
                            return true;
                            
                        } else {
                            res = (pacienteMapper.insertarPacienteResponsable(pacienteResp) > 0);
                            if (!res) {
                                throw new Exception("Error al registrar responsable de Paciente. ");
                            } else {
                                if (!listaTurnos.isEmpty()) {
                                    res = turnoMedicoMapper.insertarListaTurnos(listaTurnos);
                                    if (!res){
                                        throw new Exception("Error al registrar turnos de Paciente. ");
                                    } else {
                                        res = visitaMapper.insertar(v);
                                        if (!res){
                                            throw new Exception("Error al registrar visita. " );
                                        } else {
                                            res = pacienteServicioMapper.insertar(ps);
                                            if (!res) {
                                                throw new Exception("Error al registrar servicio. " );
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }catch (Exception ex) {
            throw new Exception("Error al registrar Manualmente al Paciente. " + ex.getMessage());
        }
        return res;
    }
    
}
