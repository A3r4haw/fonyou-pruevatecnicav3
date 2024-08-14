package mx.mc.service;

import com.ibm.icu.util.Calendar;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import mx.mc.enums.EstatusDevolucion_Enum;
import mx.mc.enums.EstatusGabinete_Enum;
import mx.mc.enums.EstatusMinistracion_Enum;
import mx.mc.enums.EstatusPrescripcion_Enum;
import mx.mc.enums.EstatusSolucion_Enum;
import mx.mc.enums.EstatusSurtimiento_Enum;
import mx.mc.enums.TipoDocumento_Enum;
import mx.mc.init.Constantes;
import mx.mc.mapper.DevolucionMinistracionDetalleMapper;
import mx.mc.mapper.DevolucionMinistracionMapper;
import mx.mc.mapper.DiagnosticoMapper;
import mx.mc.mapper.FoliosMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.InventarioMapper;
import mx.mc.mapper.MedicamentoMapper;
import mx.mc.mapper.MovimientoInventarioMapper;
import mx.mc.mapper.NutricionParenteralMapper;
import mx.mc.mapper.PacienteDomicilioMapper;
import mx.mc.mapper.PacienteMapper;
import mx.mc.mapper.PacienteServicioMapper;
import mx.mc.mapper.PrescripcionInsumoMapper;
import mx.mc.mapper.PrescripcionMapper;
import mx.mc.mapper.SolucionMapper;
import mx.mc.mapper.SurtimientoEnviadoMapper;
import mx.mc.mapper.SurtimientoInsumoMapper;
import mx.mc.mapper.SurtimientoMapper;
import mx.mc.mapper.SurtimientoMinistradoMapper;
import mx.mc.mapper.UsuarioMapper;
import mx.mc.mapper.VisitaMapper;
import mx.mc.model.DevolucionMinistracion;
import mx.mc.model.DevolucionMinistracionDetalle;
import mx.mc.model.DevolucionMinistracionDetalleExtended;
import mx.mc.model.DiagnosticoPaciente;
import mx.mc.model.Estructura;
import mx.mc.model.Folios;
import mx.mc.model.Inventario;
import mx.mc.model.InventarioExtended;
import mx.mc.model.Medicamento;
import mx.mc.model.MovimientoInventario;
import mx.mc.model.NutricionParenteral;
import mx.mc.model.Paciente;
import mx.mc.model.PacienteDomicilio;
import mx.mc.model.PacienteServicio;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.Prescripcion;
import mx.mc.model.PrescripcionInsumo;
import mx.mc.model.PrescripcionInsumo_Extended;
import mx.mc.model.Prescripcion_Extended;
import mx.mc.model.Solucion;
import mx.mc.model.Surtimiento;
import mx.mc.model.SurtimientoEnviado;
import mx.mc.model.SurtimientoEnviado_Extend;
import mx.mc.model.SurtimientoInsumo;
import mx.mc.model.SurtimientoInsumo_Extend;
import mx.mc.model.SurtimientoMinistrado;
import mx.mc.model.SurtimientoMinistrado_Extend;
import mx.mc.model.Surtimiento_Extend;
import mx.mc.model.Usuario;
import mx.mc.model.Visita;
import mx.mc.model.VistaRecepcionMedicamento;
import mx.mc.util.Comunes;
import mx.mc.ws.suministro.enums.EstatusSolicitud_Enum;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/*
 * @author hramirez
 */
@Service
public class SurtimientoServiceImpl extends GenericCrudServiceImpl<Surtimiento, String> implements SurtimientoService {

    @Autowired
    private SurtimientoMapper surtimientoMapper;
    @Autowired
    private SurtimientoInsumoMapper surtimientoInsumoMapper;
    @Autowired
    private SurtimientoEnviadoMapper surtimientoEnviadoMapper;
    @Autowired
    private SurtimientoMinistradoMapper surtimientoMinistradoMapper;
    @Autowired
    private PrescripcionMapper prescripcionMapper;
    @Autowired
    private PrescripcionInsumoMapper prescripcionInsumoMapper;
    @Autowired
    private DevolucionMinistracionMapper devolucionMapper;
    private DevolucionMinistracion devMinistracion;
    @Autowired
    private DevolucionMinistracionDetalleMapper devolucionDetalleMapper;
    @Autowired
    private InventarioMapper inventarioMapper;
    @Autowired
    private MovimientoInventarioMapper movimientoInventarioMapper;
    @Autowired
    private MedicamentoMapper medicamentoMapper;
    @Autowired
    private NutricionParenteralMapper nutricionPareteralMapper;

    private DevolucionMinistracionDetalle devMinDetalle;
    private List<DevolucionMinistracionDetalle> devMinDestalleList;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private FoliosMapper foliosMapper;
    @Autowired
    PacienteMapper pacienteMapper;
    @Autowired
    PacienteDomicilioMapper pacienteDomicilioMapper;
    @Autowired
    VisitaMapper visitaMapper;
    @Autowired
    PacienteServicioMapper pacienteServicioMapper;
    @Autowired
    SolucionMapper solucionMapper;
    
    @Autowired
    private DiagnosticoMapper diagnosticoMapper;

    private String idUsuario;
    private boolean devo;
    private List<SurtimientoMinistrado_Extend> minisList;

    @Autowired
    public SurtimientoServiceImpl(GenericCrudMapper<Surtimiento, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public List<Surtimiento_Extend> obtenerPorFechaEstructuraPacienteCamaPrescripcion(
            Date fechaProgramada,
            String cadenaBusqueda,
            List<String> tipoPrescripcionList,
            List<Integer> listEstatusPaciente,
            List<Integer> listEstatusPrescripcion,
            List<Integer> listEstatusSurtimiento,
            List<Estructura> listServiciosQueSurte) throws Exception {
        try {
            return surtimientoMapper.obtenerPorFechaEstructuraPacienteCamaPrescripcion(fechaProgramada, cadenaBusqueda, tipoPrescripcionList, listEstatusPaciente, listEstatusPrescripcion, listEstatusSurtimiento, listServiciosQueSurte);
        } catch (Exception ex) {
            throw new Exception("Error obtenerPorFechaEstructuraPacienteCamaPrescripcion. " + ex.getMessage());
        }
    }

    @Override
    public List<Surtimiento_Extend> obtenerPorFechaEstructuraPacienteCamaPrescripcionLazy(Date fechaProgramada, ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage, List<String> tipoPrescripcionList, List<Integer> listEstatusPaciente, List<Integer> listEstatusPrescripcion, List<Integer> listEstatusSurtimiento, List<Estructura> listServiciosQueSurte) throws Exception {
        try {
            return surtimientoMapper.obtenerPorFechaEstructuraPacienteCamaPrescripcionLazy(fechaProgramada, paramBusquedaReporte, startingAt, maxPerPage, tipoPrescripcionList, listEstatusPaciente, listEstatusPrescripcion, listEstatusSurtimiento, listServiciosQueSurte);
        } catch (Exception e) {
            throw new Exception("Errot al listar Surtimentos de Prescripciones" + e.getMessage());
        }
    }

    @Override
    public List<Surtimiento_Extend> obtenerPorFechaEstructuraPacienteCamaPrescripcionOrderLazy(Date fechaProgramada, ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage, List<String> tipoPrescripcionList, List<Integer> listEstatusPaciente, List<Integer> listEstatusPrescripcion, List<Integer> listEstatusSurtimiento, List<Estructura> listServiciosQueSurte, String sortField, SortOrder sortOrder) throws Exception {
        try {
            String order = sortOrder == SortOrder.ASCENDING ? "asc" : sortOrder == SortOrder.DESCENDING ? "desc" : null;
            return surtimientoMapper.obtenerPorFechaEstructuraPacienteCamaPrescripcionOrderLazy(fechaProgramada, paramBusquedaReporte, startingAt, maxPerPage, tipoPrescripcionList, listEstatusPaciente, listEstatusPrescripcion, listEstatusSurtimiento, listServiciosQueSurte, sortField, order);
        } catch (Exception e) {
            throw new Exception("Errot al listar Surtimentos de Prescripciones" + e.getMessage());
        }
    }

    @Override
    public List<Surtimiento_Extend> obtenerPorFechaEstructuraPacienteCamaPrescripcionSolucionOrderLazy(Date fechaProgramada, ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage, List<String> tipoPrescripcionList, List<Integer> listEstatusPaciente, List<Integer> listEstatusPrescripcion, List<Integer> listEstatusSurtimiento, List<Estructura> listServiciosQueSurte, String sortField, SortOrder sortOrder) throws Exception {
        try {
            String order = sortOrder == SortOrder.ASCENDING ? "asc" : sortOrder == SortOrder.DESCENDING ? "desc" : null;
            return surtimientoMapper.obtenerPorFechaEstructuraPacienteCamaPrescripcionSolucionOrderLazy(fechaProgramada, paramBusquedaReporte, startingAt, maxPerPage, tipoPrescripcionList, listEstatusPaciente, listEstatusPrescripcion, listEstatusSurtimiento, listServiciosQueSurte, sortField, order);
        } catch (Exception e) {
            throw new Exception("Errot al listar Surtimentos de Prescripciones y Soluciones" + e.getMessage());
        }
    }

    @Override
    public Long obtenerTotalPorFechaEstructuraPacienteCamaPrescripcionLazy(Date fechaProgramada, ParamBusquedaReporte paramBusquedaReporte, List<String> tipoPrescripcionList, List<Integer> listEstatusPaciente, List<Integer> listEstatusPrescripcion, List<Integer> listEstatusSurtimiento, List<Estructura> listServiciosQueSurte) throws Exception {
        try {
            return surtimientoMapper.obtenerTotalPorFechaEstructuraPacienteCamaPrescripcionLazy(fechaProgramada, paramBusquedaReporte, tipoPrescripcionList, listEstatusPaciente, listEstatusPrescripcion, listEstatusSurtimiento, listServiciosQueSurte);
        } catch (Exception e) {
            throw new Exception("Error al Obtener el total de Surtimientos de Prescripciones. " + e.getMessage());
        }
    }

    @Override
    public Long obtenerTotalPorFechaEstructuraPacienteCamaPrescripcionSolucionLazy(Date fechaProgramada, ParamBusquedaReporte paramBusquedaReporte, List<String> tipoPrescripcionList, List<Integer> listEstatusPaciente, List<Integer> listEstatusPrescripcion, List<Integer> listEstatusSurtimiento, List<Estructura> listServiciosQueSurte) throws Exception {
        try {
            return surtimientoMapper.obtenerTotalPorFechaEstructuraPacienteCamaPrescripcionSolucionLazy(fechaProgramada, paramBusquedaReporte, tipoPrescripcionList, listEstatusPaciente, listEstatusPrescripcion, listEstatusSurtimiento, listServiciosQueSurte);
        } catch (Exception e) {
            throw new Exception("Error al Obtener el total de Surtimientos de Prescripciones y Soluciones. " + e.getMessage());
        }
    }

    @Override
    public List<Surtimiento_Extend> obtenerPorFechaEstructuraPacientePrescripcionExt(
            Date fechaProgramada, String cadenaBusqueda, List<String> tipoPrescripcionList,
            Integer numHorPrevReceta, Integer numHorPostReceta,
            List<Integer> listEstatusPrescripcion, List<Integer> listEstatusSurtimiento,
            List<String> listServiciosQueSurte, int startingAt, int maxPerPage, String sortField, SortOrder sortOrder) throws Exception {
        try {
            String order = sortOrder == SortOrder.ASCENDING ? "asc" : sortOrder == SortOrder.DESCENDING ? "desc" : null;
            return surtimientoMapper.obtenerPorFechaEstructuraPacientePrescripcionExt(fechaProgramada, cadenaBusqueda, tipoPrescripcionList,
                    numHorPrevReceta, numHorPostReceta, listEstatusPrescripcion, listEstatusSurtimiento,
                    listServiciosQueSurte, startingAt, maxPerPage, sortField, order);
        } catch (Exception ex) {
            throw new Exception("Error listar Surtimientos de Prescripcines Externas. " + ex.getMessage());
        }
    }

    @Override
    public Long obtenerTotalPorFechaEstructuraPacientePrescripcionExt(
            Date fechaProgramada, String cadenaBusqueda, List<String> tipoPrescripcionList,
            Integer numHorPrevReceta, Integer numHorPostReceta,
            List<Integer> listEstatusPrescripcion, List<Integer> listEstatusSurtimiento,
            List<String> listServiciosQueSurte) throws Exception {
        try {
            return surtimientoMapper.obtenerTotalPorFechaEstructuraPacientePrescripcionExt(fechaProgramada, cadenaBusqueda, tipoPrescripcionList,
                    numHorPrevReceta, numHorPostReceta, listEstatusPrescripcion, listEstatusSurtimiento, listServiciosQueSurte);
        } catch (Exception ex) {
            throw new Exception("Error listar Surtimientos de Prescripcines Externas. " + ex.getMessage());
        }
    }

    @Override
    public List<Surtimiento_Extend> obtenerPorIdInsumoIdPrescripcion(String idSurtimiento, String idPrescripcion) throws Exception {
        try {
            return surtimientoMapper.obtenerPorIdInsumoIdPrescripcion(idSurtimiento, idPrescripcion);
        } catch (Exception ex) {
            throw new Exception("Error obtener Surtimiento de Prescripción Seleccionado. " + ex.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean registrarSurtimiento(Surtimiento_Extend surtimientoExtendedSelected,
            List<SurtimientoInsumo> surtimientoInsumoList,
            List<SurtimientoEnviado> surtimientoEnviadoList)
            throws Exception {
        boolean res = false;
        try {
            res = surtimientoMapper.actualizar(surtimientoExtendedSelected);
            if (!res) {
                throw new Exception("Error al actualizar Prescripción. ");

            } else {
                res = surtimientoInsumoMapper.actualizarSurtimientoInsumoList(surtimientoInsumoList);
                if (!res) {
                    throw new Exception("Error al actualizarSurtimientoInsumoList. ");
                } else {
                    res = surtimientoEnviadoMapper.registraSurtimientoEnviadoList(surtimientoEnviadoList);
                    if (!res) {
                        throw new Exception("Error al registraSurtimientoEnviadoList. ");
                    } else {
                        Integer numeroSurtimientosProgramados = surtimientoMapper
                                .obtenerNumeroSurtimientosProgramados(surtimientoExtendedSelected.getIdPrescripcion(),
                                        EstatusSurtimiento_Enum.PROGRAMADO.getValue());

                        if (numeroSurtimientosProgramados == 0) {
                            Prescripcion p = new Prescripcion(surtimientoExtendedSelected.getIdPrescripcion(),
                                    EstatusPrescripcion_Enum.FINALIZADA.getValue(),
                                    surtimientoExtendedSelected.getUpdateFecha(),
                                    surtimientoExtendedSelected.getUpdateIdUsuario());
                            res = prescripcionMapper.cambiarEstatusPrescripcion(p);
                            if (!res) {
                                throw new Exception("Error al cambiarEstatusPrescripcion. ");
                            } else {
                                PrescripcionInsumo pi = new PrescripcionInsumo(surtimientoExtendedSelected.getIdPrescripcion(),
                                        EstatusPrescripcion_Enum.FINALIZADA.getValue(),
                                        surtimientoExtendedSelected.getUpdateFecha(),
                                        surtimientoExtendedSelected.getUpdateIdUsuario());
                                res = prescripcionInsumoMapper.cambiarEstatusPrescripcion(pi);
                                if (!res) {
                                    throw new Exception("Error al cambiarEstatusPrescripcion. ");
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            throw new Exception("Error al registrarSurtimiento " + ex.getMessage());
        }
        return res;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean registrarSurtimientoParcial(Surtimiento surtimiento,
            List<SurtimientoInsumo_Extend> surtimientoInsumoList,
            Prescripcion prescripcion, List<Inventario> listaInventarios,
            List<MovimientoInventario> listaMovInv)
            throws Exception {
        boolean res = false;
        try {
            int tipoDoc = TipoDocumento_Enum.SURTIMIENTO_DE_PRESCRIPCION.getValue();
            Folios folio = foliosMapper.obtenerPrefixPorDocument(tipoDoc);
            surtimiento.setFolio(Comunes.generaFolio(folio));
            folio.setSecuencia(Comunes.separaFolio(surtimiento.getFolio()));
            res = foliosMapper.actualizaFolios(folio);
            if (res) {
                res = surtimientoMapper.insertar(surtimiento);
            } else {
                throw new Exception("Error al insertar Surtimiento. ");
            }

            if (!res) {
                throw new Exception("Error al actualizaFolios. ");
            } else {
                res = surtimientoInsumoMapper.registraSurtimientoInsumoExtendedList(surtimientoInsumoList);
                if (!res) {
                    throw new Exception("Error al registraSurtimientoInsumoExtendedList. ");
                } else {
                    for (SurtimientoInsumo_Extend item : surtimientoInsumoList) {
                        res = surtimientoEnviadoMapper.registraSurtimientoEnviadoExtendedList(item.getSurtimientoEnviadoExtendList());
                        if (!res) {
                            throw new Exception("Error al registraSurtimientoEnviadoExtendedList. ");
                        }
                    }
                    res = prescripcionMapper.cambiarEstatusPrescripcion(prescripcion);
                    if (!res) {
                        throw new Exception("Error al cambiarEstatusPrescripcion. ");
                    } else {
                        res = inventarioMapper.actualizarInventarioSurtidos(listaInventarios);
                        if (!res) {
                            throw new Exception("Error al actualizarInventarioSurtidos. ");
                        } else {
                            res = movimientoInventarioMapper.insertarMovimientosInventarios(listaMovInv);
                            if (!res) {
                                throw new Exception("Error al insertarMovimientosInventarios. ");
                            }
                        }
                    }
                }
            }

        } catch (Exception ex) {
            throw new Exception("Error al registrarSurtimientoParcial " + ex.getMessage());
        }
        return res;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean surtirPrescripcion(
            Surtimiento surtimiento,
            List<SurtimientoInsumo> surtimientoInsumoList,
            List<SurtimientoEnviado> surtimientoEnviadoList,
            List<Inventario> inventarioList,
            List<MovimientoInventario> movimientoInventarioList) throws Exception {
        boolean res = false;
        try {
            res = surtimientoMapper.actualizar(surtimiento);

            if (!res) {
                throw new Exception("Error al surtis Prescripción.");

            } else {
                res = surtimientoInsumoMapper.actualizarSurtimientoInsumoList(surtimientoInsumoList);
                if (!res) {
                    throw new Exception("Error al actualizarSurtimientoInsumoList. ");
                } else {
                    res = surtimientoEnviadoMapper.registraSurtimientoEnviadoList(surtimientoEnviadoList);
                    if (!res) {
                        throw new Exception("Error al registraSurtimientoEnviadoList. ");
                    } else {
                        res = inventarioMapper.actualizarInventarioSurtidos(inventarioList);
                        if (!res) {
                            throw new Exception("Error al actualizar Inventarios por Surtimiento de Prescripción. ");
                        } else {
                            res = movimientoInventarioMapper.insertarMovimientosInventarios(movimientoInventarioList);
                            if (!res) {
                                throw new Exception("Error al registrar movimientos de Inventarios por Surtimiento de Prescripción. ");
                            } else {
                                Integer numeroSurtimientosProgramados = surtimientoMapper
                                        .obtenerNumeroSurtimientosProgramados(surtimiento.getIdPrescripcion(),
                                                EstatusSurtimiento_Enum.PROGRAMADO.getValue());

                                if (numeroSurtimientosProgramados == 0) {
                                    Prescripcion p = new Prescripcion(surtimiento.getIdPrescripcion(),
                                            EstatusPrescripcion_Enum.FINALIZADA.getValue(),
                                            surtimiento.getUpdateFecha(),
                                            surtimiento.getUpdateIdUsuario());
                                    res = prescripcionMapper.cambiarEstatusPrescripcion(p);
                                    if (!res) {
                                        throw new Exception("Error al cambiarEstatusPrescripcion. ");
                                    } else {
                                        PrescripcionInsumo pi = new PrescripcionInsumo(surtimiento.getIdPrescripcion(),
                                                EstatusPrescripcion_Enum.FINALIZADA.getValue(),
                                                surtimiento.getUpdateFecha(),
                                                surtimiento.getUpdateIdUsuario());
                                        res = prescripcionInsumoMapper.cambiarEstatusPrescripcion(pi);
                                        if (!res) {
                                            throw new Exception("Error al cambiarEstatusPrescripcion. ");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            throw new Exception("Error al surtirPrescripcion " + ex.getMessage());
        }
        return res;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean registrarSurtimientoPrescExt(Surtimiento_Extend surtimientoExtendedSelected,
            List<SurtimientoInsumo> surtimientoInsumoList,
            List<SurtimientoEnviado> surtimientoEnviadoList,
            List<Inventario> listIinventario,
            List<MovimientoInventario> listaMovInventario)
            throws Exception {
        boolean res = false;
        try {
            res = surtimientoMapper.actualizar(surtimientoExtendedSelected);
            if (!res) {
                throw new Exception("Error al actualizar Surtimiento. ");
            } else {

                res = surtimientoInsumoMapper.actualizarSurtimientoInsumoList(surtimientoInsumoList);
                if (!res) {
                    throw new Exception("Error al actualizarSurtimientoInsumoList. ");
                } else {
                    if (listIinventario != null && !listIinventario.isEmpty()) {
                        res = inventarioMapper.actualizarInventarioSurtidos(listIinventario);
                    } else {
                        res = true;
                    }
                    if (!res) {
                        throw new Exception("Error al actualizar Inventarios Surtidos. ");
                    } else {
                        if (listaMovInventario != null && !listaMovInventario.isEmpty()) {
                            res = movimientoInventarioMapper.insertarMovimientosInventarios(listaMovInventario);
                        } else {
                            res = true;
                        }
                        if (!res) {
                            throw new Exception("Error al registrar Movimientos de Surtimiento. ");
                        } else {
                            if (surtimientoEnviadoList != null && !surtimientoEnviadoList.isEmpty()) {
                                res = surtimientoEnviadoMapper.registraSurtimientoEnviadoList(surtimientoEnviadoList);
                            } else {
                                res = true;
                            }
                            if (!res) {
                                throw new Exception("Error al registrar Surtimiento de Prescripción. ");
                            } else {
                                Integer numeroSurtimientosProgramados = surtimientoMapper
                                        .obtenerNumeroSurtimientosProgramados(surtimientoExtendedSelected.getIdPrescripcion(),
                                                EstatusSurtimiento_Enum.PROGRAMADO.getValue());

                                if (numeroSurtimientosProgramados == 0) {
                                    Prescripcion p = new Prescripcion(surtimientoExtendedSelected.getIdPrescripcion(),
                                            EstatusPrescripcion_Enum.FINALIZADA.getValue(),
                                            surtimientoExtendedSelected.getUpdateFecha(),
                                            surtimientoExtendedSelected.getUpdateIdUsuario());
                                    res = prescripcionMapper.cambiarEstatusPrescripcion(p);
                                    if (!res) {
                                        throw new Exception("Error al registrar Prescripción surtida. ");
                                    } else {
                                        PrescripcionInsumo pi = new PrescripcionInsumo(surtimientoExtendedSelected.getIdPrescripcion(),
                                                EstatusPrescripcion_Enum.FINALIZADA.getValue(),
                                                surtimientoExtendedSelected.getUpdateFecha(),
                                                surtimientoExtendedSelected.getUpdateIdUsuario());
                                        res = prescripcionInsumoMapper.cambiarEstatusPrescripcion(pi);
                                        if (!res) {
                                            throw new Exception("Error al registrar Prescripción Insumo Surtida. ");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            throw new Exception("Error al registrarSurtimientoPrescExt " + ex.getMessage());
        }
        return res;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean registrarServSurtimientoPrescExt(Paciente paciente, PacienteDomicilio pacienteDomicilio, Visita visita,
            PacienteServicio pacienteServicio, Prescripcion prescripcion, List<PrescripcionInsumo> prescripcionInsumoList,
            Surtimiento_Extend surtimientoExtendedSelected, List<SurtimientoInsumo> surtimientoInsumoList,
            List<SurtimientoEnviado> surtimientoEnviadoList,
            List<Inventario> listIinventario, List<MovimientoInventario> listaMovInventario, boolean existePaciente) throws Exception {
        boolean res = false;
        try {
            if (!existePaciente) {
                if (paciente != null) {
                    //            Consultar y generar Folio
                    int tipoDoc = TipoDocumento_Enum.PACIENTE_MANUAL.getValue();
                    Folios folio = foliosMapper.obtenerPrefixPorDocument(tipoDoc);

                    paciente.setPacienteNumero(Comunes.generaFolio(folio));

                    folio.setSecuencia(Comunes.separaFolio(paciente.getPacienteNumero()));
                    res = foliosMapper.actualizaFolios(folio);

                    if (!res) {
                        throw new Exception("Error al registrar el pacienteNumero ");
                    } else {
                        res = pacienteMapper.insertar(paciente);
                        if (!res) {
                            throw new Exception("Error al Ingresar al Paciente");
                        } else {
                            if (pacienteDomicilio != null) {
                                res = (pacienteMapper.insertarPacienteDomicilio(pacienteDomicilio) > 0);
                                if (!res) {
                                    throw new Exception("Error al Ingresar al PacienteDomicilio");
                                }
                            }
                            if (visita != null) {
                                res = visitaMapper.insertar(visita);
                                if (!res) {
                                    throw new Exception("Error al Ingresar la Visita");
                                } else {
                                    if (pacienteServicio != null) {
                                        res = pacienteServicioMapper.insertar(pacienteServicio);
                                        if (!res) {
                                            throw new Exception("Error al Ingresar el Paciente Servicio");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (prescripcion != null) {
                res = prescripcionMapper.insertar(prescripcion);
                if (!res) {
                    throw new Exception("Error al insertar la Prescripción.");
                } else {
                    res = prescripcionInsumoMapper.registraMedicamentoList(prescripcionInsumoList);
                    if (!res) {
                        throw new Exception("Error al insertar la PrescripcinesInsumos.");
                    }
                }
            }

            res = surtimientoMapper.insertar(surtimientoExtendedSelected);  // registro
            if (!res) {
                throw new Exception("Error ingresar el Surtimiento de Prescripción. ");
            } else {
                res = surtimientoInsumoMapper.registraSurtimientoInsumoList(surtimientoInsumoList);  // registro
                if (!res) {
                    throw new Exception("Error al ingresar SurtimientosInsumos. ");
                } else {
                    if (listIinventario != null && !listIinventario.isEmpty()) {
                        res = inventarioMapper.actualizarInventarioSurtidos(listIinventario);
                    } else {
                        res = true;
                    }
                    if (!res) {
                        throw new Exception("Error al actualizar Inventarios Surtidos. ");
                    } else {
                        if (listaMovInventario != null && !listaMovInventario.isEmpty()) {
                            res = movimientoInventarioMapper.insertarMovimientosInventarios(listaMovInventario);
                        } else {
                            res = true;
                        }
                        if (!res) {
                            throw new Exception("Error al registrar Movimientos de Surtimiento. ");
                        } else {
                            if (surtimientoEnviadoList != null && !surtimientoEnviadoList.isEmpty()) {
                                res = surtimientoEnviadoMapper.registraSurtimientoEnviadoList(surtimientoEnviadoList);
                            } else {
                                res = true;
                            }
                            if (!res) {
                                throw new Exception("Error al registrar Surtimiento de Prescripción. ");
                            } else {
                                Integer numeroSurtimientosProgramados = surtimientoMapper
                                        .obtenerNumeroSurtimientosProgramados(surtimientoExtendedSelected.getIdPrescripcion(),
                                                EstatusSurtimiento_Enum.PROGRAMADO.getValue());

                                if (numeroSurtimientosProgramados == 0) {
                                    Prescripcion p = new Prescripcion(surtimientoExtendedSelected.getIdPrescripcion(),
                                            EstatusPrescripcion_Enum.FINALIZADA.getValue(),
                                            surtimientoExtendedSelected.getUpdateFecha(),
                                            surtimientoExtendedSelected.getUpdateIdUsuario());
                                    res = prescripcionMapper.cambiarEstatusPrescripcion(p);
                                    if (!res) {
                                        throw new Exception("Error al registrar Prescripción surtida. ");
                                    } else {
                                        PrescripcionInsumo pi = new PrescripcionInsumo(surtimientoExtendedSelected.getIdPrescripcion(),
                                                EstatusPrescripcion_Enum.FINALIZADA.getValue(),
                                                surtimientoExtendedSelected.getUpdateFecha(),
                                                surtimientoExtendedSelected.getUpdateIdUsuario());
                                        res = prescripcionInsumoMapper.cambiarEstatusPrescripcion(pi);
                                        if (!res) {
                                            throw new Exception("Error al registrar Prescripción Insumo Surtida. ");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("Error al actualizar Surtimiento de Prescripción. " + e.getMessage());
        }
        return res;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean registrarSurtimientoVales(Surtimiento_Extend surtimientoExtendedSelected,
            List<SurtimientoInsumo> surtimientoInsumoList,
            List<SurtimientoEnviado> surtimientoEnviadoList)
            throws Exception {
        boolean res = false;
        try {
            res = surtimientoMapper.actualizar(surtimientoExtendedSelected);
            if (!res) {
                throw new Exception("Error al actualizar . ");

            } else {
                int i = 1;
                String folio;
                for (SurtimientoInsumo insumo : surtimientoInsumoList) {
                    if (i < 10) {
                        folio = surtimientoExtendedSelected.getFolio() + "V0" + i;
                    } else {
                        folio = surtimientoExtendedSelected.getFolio() + "V" + i;
                    }

                    insumo.setFolioVale(folio);
                    i++;
                }

                res = surtimientoInsumoMapper.actualizarSurtimientoInsumoList(surtimientoInsumoList);
                if (!res) {
                    throw new Exception("Error al actualizarSurtimientoInsumoList. ");
                } else {/*
                    List<ReabastoEnviadoExtended> listaInventario = new ArrayList<>();
                    List<MovimientoInventario> listaMovInventario = new ArrayList<>();
                    for (SurtimientoInsumo surtInsumo : surtimientoInsumoList) {
                        for (SurtimientoEnviado surtEnviado : surtimientoEnviadoList) {
                            if (surtEnviado.getIdSurtimientoInsumo().equalsIgnoreCase(surtInsumo.getIdSurtimientoInsumo())) {
                                
// TODO: se divide el numero de piezas por caja
                                Integer cantEnviadad = surtEnviado.getCantidadEnviado() / surtEnviado.getFactorTransformacion();
                                surtEnviado.setCantidadEnviado(cantEnviadad);
                                
                                ReabastoEnviadoExtended inventario = new ReabastoEnviadoExtended();
                                
                                inventario.setCantidadEnviado( cantEnviadad );
                                inventario.setIdMedicamento(surtEnviado.getIdInsumo());
                                inventario.setLote(surtEnviado.getLote());
                                inventario.setIdEstructura(surtimientoExtendedSelected.getIdEstructuraAlmacen());
                                listaInventario.add(inventario);
                                        
                                MovimientoInventario movimientoInv = new MovimientoInventario();
                                Inventario inventarioMov = inventarioMapper.
                                        obtenerInventariosPorInsumoEstructuraYLote(
                                                inventario.getIdMedicamento(), 
                                                inventario.getIdEstructura(), 
                                                inventario.getLote());
                                
                                movimientoInv.setIdMovimientoInventario(Comunes.getUUID());
                                movimientoInv.setIdTipoMotivo(20);
                                movimientoInv.setFecha(new Date());
                                movimientoInv.setIdUsuarioMovimiento(surtimientoExtendedSelected.getUpdateIdUsuario());
                                movimientoInv.setIdEstrutcuraOrigen(inventario.getIdEstructura());
                                movimientoInv.setIdEstrutcuraDestino(surtimientoExtendedSelected.getIdEstructura());
                                movimientoInv.setIdInventario(inventarioMov.getIdInventario());
                                movimientoInv.setCantidad( cantEnviadad );
                                movimientoInv.setFolioDocumento(surtimientoExtendedSelected.getFolioPrescripcion());
                                
                                listaMovInventario.add(movimientoInv);
                                
// TODO:
                                surtEnviado.setIdInventarioSurtido(inventarioMov.getIdInventario());
                            }
                        }
                    }
                    
                    inventarioMapper.actualizarInventario(listaInventario);
                    
                    movimientoInventarioMapper.insertarMovimientosInventarios(listaMovInventario);
                    
                    res = surtimientoEnviadoMapper.registraSurtimientoEnviadoList(surtimientoEnviadoList);
                    if (!res) {
                        throw new Exception("Error al registraSurtimientoEnviadoList. ");
                    }else {
                        Integer numeroSurtimientosProgramados = surtimientoMapper
                                .obtenerNumeroSurtimientosProgramados(surtimientoExtendedSelected.getIdPrescripcion()
                                        , EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                        
                        if (numeroSurtimientosProgramados == 0) {
                            Prescripcion p = new Prescripcion(surtimientoExtendedSelected.getIdPrescripcion()
                                    , EstatusPrescripcion_Enum.FINALIZADA.getValue()
                                    , surtimientoExtendedSelected.getUpdateFecha()
                                    , surtimientoExtendedSelected.getUpdateIdUsuario() );
                            res = prescripcionMapper.cambiarEstatusPrescripcion(p);
                            if (!res) {
                                throw new Exception("Error al cambiarEstatusPrescripcion. ");
                            } else {
                                PrescripcionInsumo pi = new PrescripcionInsumo(surtimientoExtendedSelected.getIdPrescripcion()
                                    , EstatusPrescripcion_Enum.FINALIZADA.getValue()
                                    , surtimientoExtendedSelected.getUpdateFecha()
                                    , surtimientoExtendedSelected.getUpdateIdUsuario());
                                res = prescripcionInsumoMapper.cambiarEstatusPrescripcion(pi);
                                if (!res) {
                                    throw new Exception("Error al cambiarEstatusPrescripcion. ");
                                }
                            }
                        }
                    }*/
                }
            }
        } catch (Exception ex) {
            throw new Exception("Error al registrarSurtimientoVales. " + ex.getMessage());
        }
        return res;
    }

    @Override
    public List<SurtimientoEnviado> obtenerListaSurtimientoEnviadoPorIdSurtimiento(String idSurtimiento) throws Exception {
        try {
            return surtimientoEnviadoMapper.obtenerListaSurtimientoEnviadoPorIdSurtimiento(idSurtimiento);
        } catch (Exception ex) {
            throw new Exception("Error al Obtener detalle Surtimiento Enviado");
        }
    }

    @Override
    public List<SurtimientoEnviado_Extend> obtenerDetalleEnviadoPorIdSurtimientoInsumo(String idSurtimientoInsumo) throws Exception {
        try {
            return surtimientoEnviadoMapper.detalleInsumoSurtimientoEnviadoPorIdSurtimientoInsumo(idSurtimientoInsumo);
        } catch (Exception ex) {
            throw new Exception("Error al Obtener detalle Surtimiento Enviado por idSurtimientoInsumo");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public int actualizarListaCantidadRecbida(List<SurtimientoInsumo_Extend> insumoExtList, VistaRecepcionMedicamento vistaMed, String usuario) throws Exception {
        boolean resul;
        devo = Constantes.INACTIVO;
        int response = 0;
        List<SurtimientoInsumo> insumoList = new ArrayList<>();
        List<SurtimientoEnviado> surInsumoEnvList = new ArrayList<>();
        minisList = new ArrayList<>();

        devMinDestalleList = new ArrayList<>();
        idUsuario = usuario;
        try {
            insumoExtList.forEach(insumoExt -> {
                insumoExt.setFechaRecepcion(new Date());
                insumoExt.setIdUsuarioRecepcion(idUsuario);
                insumoExt.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.RECIBIDO.getValue());
                insumoExt.setUpdateFecha(new Date());
                insumoExt.setUpdateIdUsuario(idUsuario);
                if (insumoExt.getSurtimientoEnviadoExtendList() != null) {
                    insumoExt.getSurtimientoEnviadoExtendList().forEach(insumoEnv -> {
                        insumoEnv.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.RECIBIDO.getValue());
                        insumoEnv.setUpdateFecha(new Date());
                        insumoEnv.setUpdateIdUsuario(idUsuario);
                        if (insumoEnv.getCantidadEnviado() > insumoEnv.getCantidadRecibido()) {
                            devo = Constantes.ACTIVO;
                            devMinDetalle = new DevolucionMinistracionDetalle();
                            devMinDetalle.setIdDevolucionDetalle(Comunes.getUUID());
                            devMinDetalle.setIdInsumo(insumoEnv.getIdInsumo());
                            devMinDetalle.setCantidad((insumoEnv.getCantidadEnviado() - insumoEnv.getCantidadRecibido()));
                            devMinDetalle.setIdEstatusDevolucion(EstatusDevolucion_Enum.REGISTRADA.getValue());
                            devMinDetalle.setInsertFecha(new Date());
                            devMinDetalle.setInsertIdUsuario(idUsuario);
                            devMinDestalleList.add(devMinDetalle);
                        }

                        surInsumoEnvList.add(insumoEnv);
                        minisList = desglosaInsumosMinistracion(insumoExt.getIdPrescripcionInsumo(), insumoEnv, minisList);
                    });
                }
                insumoList.add(insumoExt);
            });

            //Actualizat Estatus SurtimientoEnviado
            resul = surtimientoEnviadoMapper.actualizaCantidadRecibidaList(surInsumoEnvList);
            if (resul) {
                response++;
                resul = surtimientoInsumoMapper.actualizarEstatusSurtimientoInsumoList(insumoList);
                if (resul) {
                    response++;
                    //Actualizatr Estatus Surtimiento
                    Surtimiento sur = new Surtimiento();
                    sur.setIdSurtimiento(vistaMed.getIdSurtimiento());
                    sur.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.RECIBIDO.getValue());

                    resul = surtimientoMapper.actualizar(sur);
                    if (resul) {
                        response++;
                        List<? extends SurtimientoMinistrado> baseSurtList = minisList;
                        if (baseSurtList instanceof List) {
                            resul = surtimientoMinistradoMapper.insertarSurtimientoMinistradoList((List<SurtimientoMinistrado>) baseSurtList);
                        } else {
                            resul = Constantes.INACTIVO;
                        }
                        if (resul) {
                            response++;
                        } else {
                            throw new Exception("Error al insertar en Surtimiento Ministrado.");
                        }
//                        insertar la orden de devolucion si es que hay diferencia entre la cantidad recibida con la enviada
                        if (devo) {
                            Folios folio = foliosMapper.obtenerPrefixPorDocument(TipoDocumento_Enum.DEVOLUCION_MINISTRACION.getValue());
                            devMinistracion = new DevolucionMinistracion();
                            devMinistracion.setIdDevolucionMinistracion(Comunes.getUUID());
                            devMinistracion.setIdSurtimiento(vistaMed.getIdSurtimiento());
                            devMinistracion.setFolio(Comunes.generaFolio(folio));
                            devMinistracion.setIdAlmacenOrigen(vistaMed.getIdEstructuraAlmacen());
                            devMinistracion.setIdAlmacenDestino(vistaMed.getIdEstructuraAlmacenPadre());
                            devMinistracion.setIdEstatusDevolucion(EstatusDevolucion_Enum.REGISTRADA.getValue());
                            devMinistracion.setFechaDevolucion(new Date());
                            devMinistracion.setInsertFecha(new Date());
                            devMinistracion.setInsertIdUsuario(idUsuario);

                            devMinDestalleList.forEach(detalle
                                    -> detalle.setIdDevolucionMinistracion(devMinistracion.getIdDevolucionMinistracion())
                            );

                            resul = devolucionMapper.insertar(devMinistracion);
                            if (resul) {
                                response++;
                                resul = devolucionDetalleMapper.insertarLista(devMinDestalleList);
                                if (resul) {
                                    folio.setSecuencia(Comunes.separaFolio(devMinistracion.getFolio()));
                                    resul = foliosMapper.actualizaFolios(folio);
                                    if (!resul) {
                                        throw new Exception("Error al actualizar el Folio de Devolucion. ");
                                    }
                                    response++;
                                } else {
                                    throw new Exception("Error al insertar detalle Devolución. ");
                                }
                            } else {
                                throw new Exception("Error al insertar cabecera Devolución. ");
                            }
                        }
                    } else {
                        throw new Exception("Error al actualizar Surtimiento. ");
                    }
                } else {
                    throw new Exception("Error al actualizar SurtimientoInsumo de Prescripción. ");
                }
            } else {
                throw new Exception("Error al actualizar SurtimientoEnviado. ");
            }
        } catch (Exception ex) {
            throw new Exception("Error al Actualizar detalle Surtimiento Enviado :: " + ex.getMessage());
        }
        return response;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public int actualizarListaCantidadRecbidaOff(List<SurtimientoInsumo_Extend> insumoExtList, VistaRecepcionMedicamento vistaMed, String usuario) throws Exception {
        boolean resul;
        devo = Constantes.INACTIVO;
        int response = 0;
        List<SurtimientoInsumo> insumoList = new ArrayList<>();
        List<SurtimientoEnviado> surInsumoEnvList = new ArrayList<>();
        minisList = new ArrayList<>();

        devMinDestalleList = new ArrayList<>();
        idUsuario = usuario;
        try {
            insumoExtList.forEach(insumoExt -> {
                insumoExt.setFechaRecepcion(new Date());
                insumoExt.setIdUsuarioRecepcion(idUsuario);
                insumoExt.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.RECIBIDO.getValue());
                insumoExt.setUpdateFecha(new Date());
                insumoExt.setUpdateIdUsuario(idUsuario);
                if (insumoExt.getSurtimientoEnviadoExtendList() != null) {
                    insumoExt.getSurtimientoEnviadoExtendList().forEach(insumoEnv -> {
                        insumoEnv.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.RECIBIDO.getValue());
                        insumoEnv.setUpdateFecha(new Date());
                        insumoEnv.setUpdateIdUsuario(idUsuario);
                        if (insumoEnv.getCantidadEnviado() > insumoEnv.getCantidadRecibido()) {
                            devo = Constantes.ACTIVO;
                            devMinDetalle = new DevolucionMinistracionDetalle();
                            devMinDetalle.setIdDevolucionDetalle(Comunes.getUUID());
                            devMinDetalle.setIdInsumo(insumoEnv.getIdInsumo());
                            devMinDetalle.setCantidad((insumoEnv.getCantidadEnviado() - insumoEnv.getCantidadRecibido()));
                            devMinDetalle.setIdEstatusDevolucion(EstatusDevolucion_Enum.REGISTRADA.getValue());
                            devMinDetalle.setInsertFecha(new Date());
                            devMinDetalle.setInsertIdUsuario(idUsuario);

                            devMinDestalleList.add(devMinDetalle);
                        }

                        surInsumoEnvList.add(insumoEnv);
                        minisList = desglosaInsumosMinistracion(insumoExt.getIdPrescripcionInsumo(), insumoEnv, minisList);
                    });
                }
                insumoList.add(insumoExt);
            });

            //Actualizat Estatus SurtimientoEnviado
            resul = surtimientoEnviadoMapper.actualizaCantidadRecibidaList(surInsumoEnvList);
            if (resul) {
                response++;
                resul = surtimientoInsumoMapper.actualizarEstatusSurtimientoInsumoList(insumoList);
                if (resul) {
                    response++;
                    //Actualizatr Estatus Surtimiento
                    Surtimiento sur = new Surtimiento();
                    sur.setIdSurtimiento(vistaMed.getIdSurtimiento());
                    sur.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.RECIBIDO.getValue());

                    resul = surtimientoMapper.actualizar(sur);
                    if (resul) {
                        response++;
                        List<? extends SurtimientoMinistrado> baseSurtList = minisList;
                        if (baseSurtList instanceof List) {
                            resul = surtimientoMinistradoMapper.insertarSurtimientoMinistradoList((List<SurtimientoMinistrado>) baseSurtList);
                        } else {
                            resul = Constantes.INACTIVO;
                        }
                        if (resul) {
                            response++;
                        } else {
                            throw new Exception("Error al insertar en Surtimiento Ministrado.");
                        }
//                        insertar la orden de devolucion si es que hay diferencia entre la cantidad recibida con la enviada
                        if (devo) {
                            Folios folio = foliosMapper.obtenerPrefixPorDocument(TipoDocumento_Enum.DEVOLUCION_MINISTRACION.getValue());
                            devMinistracion = new DevolucionMinistracion();
                            devMinistracion.setIdDevolucionMinistracion(Comunes.getUUID());
                            devMinistracion.setIdSurtimiento(vistaMed.getIdSurtimiento());
                            devMinistracion.setFolio(Comunes.generaFolio(folio));
                            devMinistracion.setIdAlmacenOrigen(vistaMed.getIdEstructuraAlmacen());
                            devMinistracion.setIdAlmacenDestino(vistaMed.getIdEstructuraAlmacenPadre());
                            devMinistracion.setIdEstatusDevolucion(EstatusDevolucion_Enum.REGISTRADA.getValue());
                            devMinistracion.setFechaDevolucion(new Date());
                            devMinistracion.setInsertFecha(new Date());
                            devMinistracion.setInsertIdUsuario(idUsuario);
                            devMinDestalleList.forEach(detalle
                                    -> detalle.setIdDevolucionMinistracion(devMinistracion.getIdDevolucionMinistracion())
                            );

                            resul = devolucionMapper.insertar(devMinistracion);
                            if (resul) {
                                response++;
                                resul = devolucionDetalleMapper.insertarLista(devMinDestalleList);
                                if (resul) {
                                    folio.setSecuencia(Comunes.separaFolio(devMinistracion.getFolio()));
                                    resul = foliosMapper.actualizaFolios(folio);
                                    if (!resul) {
                                        throw new Exception("Error al actualizar el Folio de Devolucion. ");
                                    }
                                    response++;
                                } else {
                                    throw new Exception("Error al insertar detalle Devolución. ");
                                }
                            } else {
                                throw new Exception("Error al insertar cabecera Devolución. ");
                            }
                        }
                    } else {
                        throw new Exception("Error al actualizar Surtimiento de Prescripción.");
                    }
                } else {
                    throw new Exception("Error al actualizar SurtimientoInsumo de Prescripción. ");
                }
            } else {
                throw new Exception("Error al actualizar SurtimientoEnviado. ");
            }
        } catch (Exception ex) {
            throw new Exception("Error al Actualizar detalle Surtimiento Enviado :: " + ex.getMessage());
        }
        return response;
    }

    private SurtimientoMinistrado_Extend medicamentoProcesado(String claveInstitucional, List<SurtimientoMinistrado_Extend> lista) {
        if (lista == null || lista.isEmpty()) {
            return null;
        }
        List<SurtimientoMinistrado_Extend> listSurtMin = lista.stream().filter(s -> s.getClaveInstitucional().equals(claveInstitucional)).collect(Collectors.toList());
        if (listSurtMin.isEmpty()) {
            return null;
        }
        listSurtMin.sort((s1, s2) -> s2.getFechaProgramado().compareTo(s1.getFechaProgramado()));
        return listSurtMin.get(0);
    }

    private List<SurtimientoMinistrado_Extend> desglosaInsumosMinistracion(String idPrescripcionInsumo, SurtimientoEnviado enviado, List<SurtimientoMinistrado_Extend> lista) {

        PrescripcionInsumo insumo = new PrescripcionInsumo();
        insumo.setIdPrescripcionInsumo(idPrescripcionInsumo);
        insumo = prescripcionInsumoMapper.obtener(insumo);
        Medicamento med = medicamentoMapper.obtenerPorIdMedicamento(insumo.getIdInsumo());

        int frecuencia = insumo.getFrecuencia() != null ? insumo.getFrecuencia() : 0;
        Date fechaInicio = insumo.getFechaInicio();
        Calendar calendar = Calendar.getInstance();
        SurtimientoMinistrado_Extend old = medicamentoProcesado(med.getClaveInstitucional(), lista);
        if (old != null) {
            fechaInicio = old.getFechaProgramado();
            calendar.setTime(fechaInicio);
            calendar.add(Calendar.HOUR, frecuencia);
            fechaInicio = calendar.getTime();
        }

        BigDecimal dosis = insumo.getDosis() != null ? insumo.getDosis() : BigDecimal.ZERO;
        if (med.isIndivisible()) {
            dosis = new BigDecimal(enviado.getCantidadEnviado());
        } else {
            BigDecimal concentracion = med.getConcentracion() != null ? med.getConcentracion() : BigDecimal.ZERO;
            if (concentracion.compareTo(BigDecimal.ZERO) > 0) {
                if (dosis.remainder(concentracion).compareTo(BigDecimal.ZERO) == 0) {
                    dosis = dosis.divide(concentracion);
                } else {
                    dosis = dosis.divide(concentracion, RoundingMode.CEILING);
                    dosis = dosis.setScale(0, RoundingMode.CEILING);
                }
            }
        }

        int tomas = 0;
        int cantidad = 0;
        int res = 0;

        tomas = 24 / frecuencia;
        cantidad = enviado.getCantidadRecibido();

        for (int i = 1; i <= tomas; i++) {
            // Se modifica la regla para que pueda enviar dosis incompletas
            BigDecimal bdCantidad = new BigDecimal(cantidad);
            if (bdCantidad.compareTo(dosis) > 0) {
                res = cantidad - dosis.intValue();
                cantidad = res;
            } else if (cantidad > 0) {
                dosis = bdCantidad;
                cantidad = 0;
            } else {
                dosis = BigDecimal.ZERO;
                i = tomas + 1;
            }

            if (dosis.compareTo(BigDecimal.ZERO) > 0) {
                SurtimientoMinistrado_Extend minis = new SurtimientoMinistrado_Extend();

                minis.setClaveInstitucional(med.getClaveInstitucional());
                minis.setIdMinistrado(Comunes.getUUID());
                minis.setIdSurtimientoEnviado(enviado.getIdSurtimientoEnviado());
                minis.setFechaProgramado(fechaInicio);
                minis.setIdEstatusSurtimiento(EstatusMinistracion_Enum.PENDIENTE.getValue());
                minis.setCantidad(dosis.intValue());
                minis.setDosis(insumo.getDosis());
                minis.setOrden(i);
                minis.setInsertFecha(enviado.getUpdateFecha());
                minis.setInsertIdUsuario(enviado.getUpdateIdUsuario());

                lista.add(minis);
                calendar.setTime(fechaInicio);
                calendar.add(Calendar.HOUR, frecuencia);
                fechaInicio = calendar.getTime();
            }
        }
        return lista;
    }

    @Override
    public List<SurtimientoEnviado> obtenerDetalleDevolucionPorIdPrescripcion(String idPrescripcion) throws Exception {
        try {
            return surtimientoEnviadoMapper.detalleInsumoSurtimientoDevolucion(idPrescripcion);
        } catch (Exception ex) {
            throw new Exception("Error al obtenerDetalleDevolucionPorIdPrescripcion");
        }
    }

    @Override
    public List<SurtimientoEnviado_Extend> obtenerDetalleDevolucionPorIdPrescripcionExtend(String idPrescripcion) throws Exception {
        try {
            return surtimientoEnviadoMapper.detalleInsumoSurtimientoDevolucionExtend(idPrescripcion);
        } catch (Exception ex) {
            throw new Exception("Error al obtenerDetalleDevolucionPorIdPrescripcionExtend");
        }
    }

    @Override
    public List<SurtimientoEnviado> insumosDePrescripcion(String folio, String idSurtimientoEnviado) throws Exception {
        try {
            return surtimientoEnviadoMapper.insumosEnMinistracion(folio, idSurtimientoEnviado);
        } catch (Exception ex) {
            throw new Exception("Error al Obtener insumos de la prescripcion");
        }
    }

    @Override
    public List<SurtimientoEnviado_Extend> insumosDePrescripcionExtend(String folio, String idSurtimientoEnviado) throws Exception {
        try {
            return surtimientoEnviadoMapper.insumosEnMinistracionExtend(folio, idSurtimientoEnviado);
        } catch (Exception ex) {
            throw new Exception("Error al Obtener insumos de la prescripcion");
        }
    }

    @Override
    public List<Surtimiento> obtenerSurtimientoConsumoExterna() throws Exception {
        try {
            return surtimientoMapper.obtenerSurtimientoConsumoExterna();
        } catch (Exception ex) {
            throw new Exception("Error al Obtener insumos de la receta Externa surtida");
        }
    }

    @Override
    public boolean actualizarPorFolio(String folio, Integer procesado) throws Exception {
        try {
            return surtimientoMapper.actualizarPorFolio(folio, procesado);
        } catch (Exception ex) {
            throw new Exception("Error al Actualizar surtimiento Procesado.");
        }
    }

    @Override
    public boolean actualizarPorFolioVale(String folio, Integer procesado) throws Exception {
        try {
            return surtimientoMapper.actualizarPorFolioVale(folio, procesado);
        } catch (Exception ex) {
            throw new Exception("Error al Actualizar surtimiento Procesado.");
        }
    }

    @Override
    public Surtimiento obtenerPorFolio(String folio) throws Exception {
        try {
            return surtimientoMapper.obtenerPorFolio(folio);
        } catch (Exception ex) {
            throw new Exception("Error al obtener Procesado");
        }
    }

    @Override
    public boolean clonarSurtimiento(String idSurtimiento, String newFolio) throws Exception {
        try {
            return surtimientoMapper.clonarSurtimientoPorId(idSurtimiento, newFolio);
        } catch (Exception ex) {
            throw new Exception("Error al clonar Surtimiento");
        }
    }

    @Override
    public List<Surtimiento_Extend> obtenerPorFechaEstructuraPacienteCamaPrescripcionCancelacion(Date fechaProgramada, String cadenaBusqueda, List<String> tipoPrescripcionList, List<Integer> listEstatusPaciente, List<Integer> listEstatusPrescripcion, List<Integer> listEstatusSurtimiento, List<Estructura> listServiciosQueSurte) throws Exception {
        try {
            return surtimientoMapper.obtenerPorFechaEstructuraPacienteCamaPrescripcionCancelacion(fechaProgramada, cadenaBusqueda, tipoPrescripcionList, listEstatusPaciente, listEstatusPrescripcion, listEstatusSurtimiento, listServiciosQueSurte);
        } catch (Exception ex) {
            throw new Exception("Error obtenerPorFechaEstructuraPacienteCamaPrescripcionCancelacion. " + ex.getMessage());
        }
    }

    @Override
    public List<Surtimiento_Extend> obtenerPorFechaEstructuraPacienteCamaPrescripcionCancelacionInterna(
            Date fechaProgramada, String cadenaBusqueda, List<String> tipoPrescripcionList,
            List<Integer> listEstatusPaciente, List<Integer> listEstatusPrescripcion,
            List<Integer> listEstatusSurtimiento, List<Estructura> listServiciosQueSurte) throws Exception {
        try {
            return surtimientoMapper.obtenerPorFechaEstructuraPacienteCamaPrescripcionCancelacionInterna(
                    fechaProgramada, cadenaBusqueda, tipoPrescripcionList, listEstatusPaciente,
                    listEstatusPrescripcion, listEstatusSurtimiento, listServiciosQueSurte);
        } catch (Exception ex) {
            throw new Exception("Error obtenerPorFechaEstructuraPacienteCamaPrescripcionCancelacionInterna. " + ex.getMessage());
        }
    }

    @Override
    public List<SurtimientoInsumo_Extend> detalleSurtimientoInsumoExtRecepMedi(String idSurtimiento) throws Exception {
        List<SurtimientoInsumo_Extend> insumosList = new ArrayList<>();
        try {

            insumosList = surtimientoInsumoMapper.obtenerByIdSurtimientoRecepMedi(idSurtimiento);
            if (idSurtimiento.equals("OFFLINE")) {
                List<SurtimientoEnviado_Extend> detail = surtimientoEnviadoMapper.obtenerByIdSurtimientoInsumoRecepMedi(idSurtimiento);
                for (SurtimientoInsumo_Extend itemInsumo : insumosList) {
                    for (SurtimientoEnviado_Extend itemInsumoEnviado : detail) {
                        if (itemInsumo.getIdSurtimientoInsumo().equals(itemInsumoEnviado.getIdSurtimientoInsumo())) {
                            itemInsumo.setSurtimientoEnviadoExtendList(Arrays.asList(itemInsumoEnviado));
                        }
                    }
                }
            } else {
                for (SurtimientoInsumo_Extend item : insumosList) {
                    List<SurtimientoEnviado_Extend> detail = surtimientoEnviadoMapper.obtenerByIdSurtimientoInsumoRecepMedi(item.getIdSurtimientoInsumo());
                    item.setSurtimientoEnviadoExtendList(detail);
                }
            }

        } catch (Exception ex) {
            throw new Exception("Error detalleSurtimientoInsumoExtRecepMedi. " + ex.getMessage());
        }
        return insumosList;
    }

    @Override
    public List<DevolucionMinistracionDetalleExtended> obtenerDevolucionDetalleExtPorIdSurtimiento(String idSurtimiento) throws Exception {
        try {
            List<DevolucionMinistracionDetalleExtended> devDetalleExtList;

            devDetalleExtList = devolucionDetalleMapper.obtenerDevolucionDetalleExtPorIdSurtimiento(idSurtimiento);
            List<SurtimientoEnviado_Extend> detail = surtimientoEnviadoMapper.obtenerByIdSurtimientoInsumoDevMedi(idSurtimiento);
            for (DevolucionMinistracionDetalleExtended dev : devDetalleExtList) {
                List<SurtimientoEnviado_Extend> devDetalleExtListAux = new ArrayList<>();
                for (SurtimientoEnviado_Extend item : detail) {
                    if (dev.getIdSurtimientoInsumo().equals(item.getIdSurtimientoInsumo())) {
                        devDetalleExtListAux.add(item);
                    }
                }
                dev.setSurtimientoEnviadoExtList(devDetalleExtListAux);
            }
            return devDetalleExtList;
        } catch (Exception ex) {
            throw new Exception("Error al obtenerDevolucionDetalleExtPorIdSurtimiento");
        }
    }

    @Override
    public List<SurtimientoEnviado_Extend> obtenerByIdSurtimientoEnviadoDevMedi(String idSurtimiento) throws Exception {
        try {
            List<SurtimientoEnviado_Extend> devDetalleExtList;
            devDetalleExtList = surtimientoEnviadoMapper.obtenerByIdSurtimientoEnviadoDevMedi(idSurtimiento);
            return devDetalleExtList;
        } catch (Exception ex) {
            throw new Exception("Error al obtenerByIdSurtimientoEnviadoDevMedi");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean actualizaSurtimientoRecibidoPorGabinetes(String folio, int idEstatusSurtimiento, String updateIdUsuario, Date updateFecha, boolean existeUsuario,
            Usuario usuario, int numeroMedicacion, boolean actSurtInsumo) throws Exception {
        boolean res = false;
        try {
            if (existeUsuario) {
                res = surtimientoMapper.actualizarPorFolioEstatusIntipharm(folio, idEstatusSurtimiento, updateIdUsuario, updateFecha, numeroMedicacion, actSurtInsumo);
            } else {
                res = usuarioMapper.insertar(usuario);
                if (!res) {
                    throw new Exception("Error al insertar el Usuario ");
                } else {
                    res = surtimientoMapper.actualizarPorFolioEstatusIntipharm(folio, idEstatusSurtimiento, updateIdUsuario, updateFecha, numeroMedicacion, actSurtInsumo);
                    if (!res) {
                        throw new Exception("Error al actualizar el estatusSurtimiento ");
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("Error al actualizar los Surtimientos  " + e.getMessage());
        }
        return res;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean actualizarRegistrosTracking(boolean existUsuario, Usuario usuario, Surtimiento surtimiento, List<SurtimientoInsumo> surtimientoInsumoList, List<SurtimientoEnviado> surtimientoEnviadoList, List<InventarioExtended> inventarioList, List<MovimientoInventario> movimientoInventarioList, List<SurtimientoMinistrado> surtMinistradoList) throws Exception {
        boolean resp = false;
        try {
            resp = surtimientoMapper.actualizar(surtimiento);
            if (!resp) {
                throw new Exception("Error al actualizar el surtimiento. ");
            } else {
                resp = surtimientoInsumoMapper.actualizarSurtimientoInsumoList(surtimientoInsumoList);
                if (!resp) {
                    throw new Exception("Error al actualizar la lista surtimientoInsumo ");
                } else {
                    resp = surtimientoEnviadoMapper.registraSurtimientoEnviadoList(surtimientoEnviadoList);
                    if (!resp) {
                        throw new Exception("Error al actualizar la lista surtimientoEnviado ");
                    } else {
                        resp = surtimientoMinistradoMapper.insertListSurtimientosMinistrados(surtMinistradoList);
                        if (!resp) {
                            throw new Exception("Error al Insertar la Lista surtimientos Ministrados");
                        } else {
                            resp = inventarioMapper.actualizaInvByInv(inventarioList);
                            if (!resp) {
                                throw new Exception("Error al actualizar la lista Inventario ");
                            } else {
                                if (!existUsuario) {
                                    resp = usuarioMapper.insertar(usuario);
                                    if (!resp) {
                                        throw new Exception("Error al insertar el Usuario ");
                                    } else {
                                        resp = movimientoInventarioMapper.insertarMovimientosInventarios(movimientoInventarioList);
                                        if (!resp) {
                                            throw new Exception("Error al actualizar la lista MovimientoInventario ");
                                        }
                                    }
                                } else {
                                    resp = movimientoInventarioMapper.insertarMovimientosInventarios(movimientoInventarioList);
                                    if (!resp) {
                                        throw new Exception("Error al actualizar la lista MovimientoInventario ");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("Error al actualizarRegistrosTracking " + e.getMessage());
        }
        return resp;
    }

    @Override
    public boolean clonarSurtimientoCancelado(String idSurtimiento, String newFolio) throws Exception {
        try {
            return surtimientoMapper.clonarSurtimientoCanceladoPorId(idSurtimiento, newFolio);
        } catch (Exception ex) {
            throw new Exception("Error al ClonarSurtimientoCancelado");
        }
    }

    @Override
    public boolean actualizarFolio(String idSurtimiento, String newFolio) throws Exception {
        try {
            return surtimientoMapper.actualizarFolio(idSurtimiento, newFolio);
        } catch (Exception ex) {
            throw new Exception("Error al Actualizar el folio del surtimiento.");
        }
    }

    @Override
    public boolean actualizarTipoCancelacion(String idSurtimiento, int idTipoCancelacion) throws Exception {
        try {
            return surtimientoMapper.actualizarTipoCancelacion(idSurtimiento, idTipoCancelacion);
        } catch (Exception ex) {
            throw new Exception("Error al Actualizar el tipo de Cancelacion del surtimiento.");
        }
    }

    @Override
    public int obtenerSurtPrescripcion(String folio) throws Exception {
        try {
            return surtimientoMapper.obtenerSurtPrescripcion(folio);
        } catch (Exception e) {
            throw new Exception("Error al obtener el total obtenerDatoPrescripcion." + e.getMessage());
        }
    }

    @Override
    public int obtenerTotalSurtimiento(String folio) throws Exception {
        try {
            return surtimientoMapper.obtenerTotalSurtimiento(folio);
        } catch (Exception e) {
            throw new Exception("Error al obtener el total obtenerTotal surtimientosRestantes." + e.getMessage());
        }
    }

    @Override
    public List<SurtimientoInsumo_Extend> obtenerDetalleSurtimiento(String idSurtimiento) throws Exception {
        List<SurtimientoInsumo_Extend> insumosList = new ArrayList<>();
        try {
            insumosList = surtimientoInsumoMapper.obtenerSurtInsumosByIdSurtimiento(idSurtimiento);

        } catch (Exception ex) {
            throw new Exception("Error obtenerDetalleSurtimiento. " + ex.getMessage());
        }
        return insumosList;
    }

    @Override
    public List<SurtimientoEnviado_Extend> obtenerListaSurtimientoEnviadoPorIdSurtimientoDevolucion(String idSurtimiento) throws Exception {
        List<SurtimientoEnviado_Extend> insumosList = new ArrayList<>();
        try {
            insumosList = surtimientoInsumoMapper.obtenerListaSurtimientoEnviadoPorIdSurtimientoDevolucion(idSurtimiento);

        } catch (Exception ex) {
            throw new Exception("Error listar los insumos. " + ex.getMessage());
        }
        return insumosList;
    }

    @Override
    public boolean ligaridCapsulaconSurtimiento(Surtimiento surtircapsula) throws Exception {
        boolean resp = false;
        try {
            resp = surtimientoMapper.ligaridCapsulaconSurtimiento(surtircapsula);
        } catch (Exception e) {
            throw new Exception("Error al registar Capsula " + e);
        }
        return resp;
    }

    @Override
    public Surtimiento_Extend obtenerSurtimientoExtendedPorIdSurtimiento(String idSurtimiento, String idPrescripcion, String folio, String idEstatusSurtimiento) throws Exception {
        Surtimiento_Extend surteExt = null;
        try {
            surteExt = surtimientoMapper.obtenerSurtimientoExtendedPorIdSurtimiento(idSurtimiento, idPrescripcion, folio, idEstatusSurtimiento);
        } catch (Exception ex) {
            throw new Exception("Error al obtenerSurtimientoExtendedPorIdSurtimiento");
        }
        return surteExt;
    }

    @Override
    public SurtimientoEnviado_Extend obtenerByIdSurtimientoEnviado(String idSurtimientoEnviado) throws Exception {
        try {
            return surtimientoEnviadoMapper.obtenerByIdSurtimientoEnviado(idSurtimientoEnviado);
        } catch (Exception ex) {
            throw new Exception("Error al obtenerByIdSurtimientoEnviado");
        }
    }

    @Override
    public Surtimiento_Extend obtenerByIdSurtimiento(String idSurtimiento) throws Exception {
        try {
            return surtimientoMapper.obtenerByIdSurtimiento(idSurtimiento);
        } catch (Exception ex) {
            throw new Exception("Error al obtenerByIdSurtimiento");
        }
    }

    @Override
    public Surtimiento_Extend obtenerByIdPrescripcion(String idPrescripcion) throws Exception {
        try {
            return surtimientoMapper.obtenerByIdPrescripcion(idPrescripcion);
        } catch (Exception ex) {
            throw new Exception("Error al obtenerByIdPrescripcion");
        }
    }

    @Override
    public List<String> surtimientoEnviadoByPrecripcion(String idPrescripcion) throws Exception {
        try {
            return surtimientoEnviadoMapper.surtimientoEnviadoByPrecripcion(idPrescripcion);
        } catch (Exception ex) {
            throw new Exception("Error al surtimientoEnviadoByPrecripcion");
        }
    }

    @Override
    public SurtimientoEnviado_Extend obtenerSurtimientoEnviadoByIdSurtimientoInsumo(String idSurtimientoInsumo) throws Exception {
        try {
            return surtimientoEnviadoMapper.obtenerSurtimientoEnviadoByIdSurtimientoInsumo(idSurtimientoInsumo);
        } catch (Exception ex) {
            throw new Exception("Error al obtenerSurtimientoEnviadoByIdSurtimientoInsumo");
        }

    }

    @Override
    public List<Surtimiento_Extend> obtenerSurtimientoConEstatus(List<String> listaSurtimiento, List<Integer> listaEstatus) throws Exception {
        try {
            return surtimientoMapper.obtenerSurtimientoConEstatus(listaSurtimiento, listaEstatus);
        } catch (Exception ex) {
            throw new Exception("Error al obtener los surtimientos");
        }

    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean updateAsignPrescripcion(List<Prescripcion_Extended> prescripcionList, List<PrescripcionInsumo_Extended> prescripcionInsumoList, List<Surtimiento_Extend> surtimientoList, List<SurtimientoInsumo_Extend> surtimientoInsumoList, SurtimientoEnviado_Extend surtimientoEnviadoDispensacionDirecta) throws Exception {

        boolean check = false;
        try {
            for (int i = 0; i < prescripcionList.size(); i++) {
                Prescripcion_Extended p = prescripcionList.get(i);
                check = prescripcionMapper.updateAsignPrescripcion(p.getIdEstatusPrescripcion(), p.getComentarios(), p.getUpdateFecha(), p.getUpdateIdUsuario(), p.getIdPrescripcion());
            }
            if (check) {
                for (int i = 0; i < prescripcionInsumoList.size(); i++) {
                    PrescripcionInsumo_Extended p = prescripcionInsumoList.get(i);
                    check = prescripcionInsumoMapper.updateAsignPrescripcionInsumo(p.getIdEstatusPrescripcion(), p.getUpdateFecha(), p.getUpdateIdUsuario(), p.getIdPrescripcionInsumo());
                }
            }
            if (check) {
                for (int i = 0; i < surtimientoList.size(); i++) {
                    Surtimiento_Extend p = surtimientoList.get(i);
                    check = surtimientoMapper.updateAsignSurtimiento(p.getIdEstatusSurtimiento(), p.getUpdateFecha(), p.getUpdateIdUsuario(), p.getIdEstatusMirth(), p.getIdSurtimiento());
                }
            }

            if (check) {
                for (int i = 0; i < surtimientoInsumoList.size(); i++) {
                    SurtimientoInsumo_Extend p = surtimientoInsumoList.get(i);
                    check = surtimientoInsumoMapper.updateAsignSurtimientoInsumo(p);
                }
            }

            if (check) {
                check = surtimientoEnviadoMapper.updateAsignPrescripcionEnviado(surtimientoEnviadoDispensacionDirecta.getIdSurtimientoInsumo(), surtimientoEnviadoDispensacionDirecta.getUpdateFecha(), surtimientoEnviadoDispensacionDirecta.getUpdateIdUsuario(), surtimientoEnviadoDispensacionDirecta.getIdSurtimientoEnviado());

            }
        } catch (Exception ex) {
            throw new Exception("Error al al asignar la prescripción");
        }

        return check;
    }

    @Override
    public String obtenerAlmacenPadreOfSurtimiento(String idEstructura) throws Exception {

        try {
            return devolucionMapper.obtenerAlmacenPadreOfSurtimiento(idEstructura);
        } catch (Exception ex) {
            throw new Exception("Error al ObtenerAlmacenPadreOfSurtimiento");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean surtimientoParcial(Surtimiento surtimiento, List<SurtimientoInsumo> surtimientoInsumo) throws Exception {
        boolean res = false;
        res = surtimientoMapper.insertar(surtimiento);
        if (!res) {
            throw new Exception("Error al registrar Surtimiento. ");
        } else {
            for (SurtimientoInsumo objectInsumo : surtimientoInsumo) {
                res = surtimientoInsumoMapper.insertar(objectInsumo);
                if (!res) {
                    throw new Exception("Error al registrar Surtimiento Insumo. ");
                }
            }
        }
        return res;
    }

    @Override
    public boolean actualizarClaveSolucionSurtimietoByFolio(String folio, String claveAgrupada) throws Exception {
        boolean resp = false;
        try {
            resp = surtimientoMapper.actualizarClaveSolucionSurtimietoByFolio(folio, claveAgrupada);
        } catch (Exception ex) {
            throw new Exception("Error al actualizar la claveSolución");
        }
        return resp;
    }

    @Override
    public Surtimiento_Extend obtenerByFechaAndPrescripcion(String idPrescripcion, Date fechaProgramada) throws Exception {
        try {
            return surtimientoMapper.obtenerByFechaAndPrescripcion(idPrescripcion, fechaProgramada);
        } catch (Exception e) {
            throw new Exception("Error al obtenerByFechaAndPrescripcion");
        }
    }

    @Override
    public Surtimiento_Extend obtenerDetallePrescripcionSolucion(String folio) throws Exception {
        try {
            return surtimientoMapper.obtenerDetallePrescripcionSolucion(folio);
        } catch (Exception ex) {
            throw new Exception("Error al obtener el detalle de la prescripcion de solucion" + ex);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean actualizarInsumosValidacion(Solucion solucion,Surtimiento surt, List<SurtimientoInsumo> items, List<SurtimientoEnviado> enviadoList, List<DiagnosticoPaciente> diagnoticoList, boolean solicionRegistrada) throws Exception {
        boolean rest = false;
        try {
            if (!solicionRegistrada) {
                rest = solucionMapper.insertar(solucion);
            } else {
                rest = solucionMapper.actualizar(solucion);
            }
            if (!rest) {
                throw new Exception("Error al insertar la solucion.");
            }else{
                rest = surtimientoMapper.actualizar(surt);
                if (!rest) {
                    throw new Exception("Error al actualizar el surtimiento.");
                }else{
                    rest = surtimientoInsumoMapper.actualizarSurtimientoInsumoList(items);
                    if (!rest) {
                        throw new Exception("Error al actualizar los surtimientosInsumos.");
                    }else{
                        for (SurtimientoEnviado item:enviadoList){
                            SurtimientoEnviado o = new SurtimientoEnviado();
                            o.setIdSurtimientoEnviado(item.getIdSurtimientoEnviado());
                            SurtimientoEnviado se = surtimientoEnviadoMapper.obtener(o);
                            if (se == null){
                                item.setInsertFecha(new java.util.Date());
                                item.setInsertIdUsuario(solucion.getUpdateIdUsuario());
                                rest = surtimientoEnviadoMapper.insertar(item);
                            } else {
                                rest = surtimientoEnviadoMapper.actualizar(item);
                            }
                        }
//                        rest = surtimientoEnviadoMapper.registraSurtimientoEnviadoList(enviadoList);
                        if (!rest) {
                            throw new Exception("Error al registrar los surtimientosEnvados.");
                        } else { 
                            if(!diagnoticoList.isEmpty()) {
                                rest = diagnosticoMapper.eliminaDiagnosticoList(surt.getIdPrescripcion());
                                if(!rest) {
                                    throw new Exception("Error al eliminar diagnsticos de paciente.");
                                } else {
                                    rest = diagnosticoMapper.registraDiagnosticoList(diagnoticoList);
                                    if(!rest) {
                                        throw new Exception("Error al registrar diagnsticos de paciente.");
                                    }
                                }
                            }                            
                        }
                    }
                }
            }
        } catch (Exception ex) {
            throw new Exception("Error al actualizarlos insumos de la solucion :: " + ex);
        }
        return rest;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean surtirSolucion(            
            Surtimiento surtimiento,
            List<SurtimientoInsumo> surtimientoInsumoList,
            List<SurtimientoEnviado> surtimientoEnviadoList,
            List<Inventario> inventarioList,
            List<MovimientoInventario> movimientoInventarioList,
            Solucion solucion) throws Exception {
        boolean res = false;
        try {
            res = surtimientoMapper.actualizar(surtimiento);
            if (!res) {
                throw new Exception("Error al actualizar el surtimiento.");
            } else {
                res = surtimientoInsumoMapper.actualizarSurtimientoInsumoList(surtimientoInsumoList);
                if (!res) {
                    throw new Exception("Error al actualizarSurtimientoInsumoList. ");
                } else {
                    if (surtimientoEnviadoList != null && !surtimientoEnviadoList.isEmpty()) {
                        for (SurtimientoEnviado se : surtimientoEnviadoList ) {
                            SurtimientoEnviado_Extend suTmp = surtimientoEnviadoMapper.obtenerByIdSurtimientoEnviado(se.getIdSurtimientoEnviado());
                            if (suTmp != null) {
                                res = surtimientoEnviadoMapper.actualizar(se);
                            } else {
                                res = surtimientoEnviadoMapper.insertar(se);
                            }
                        }
                    }
//                    res = surtimientoEnviadoMapper.actualizaSurtimientoEnviadoList(surtimientoEnviadoList);
                    if (!res) {
                        throw new Exception("Error al registraSurtimientoEnviadoList. ");
                    } else {
                        res = inventarioMapper.actualizarInventarioSurtidos(inventarioList);
                        if (!res) {
                            throw new Exception("Error al actualizar Inventarios por Surtimiento de Prescripción. ");
                        } else {
                            res = movimientoInventarioMapper.insertarMovimientosInventarios(movimientoInventarioList);
                            if (!res) {
                                throw new Exception("Error al registrar movimientos de Inventarios por Surtimiento de Prescripción. ");
                            } else {
                                Integer numeroSurtimientosProgramados = surtimientoMapper
                                        .obtenerNumeroSurtimientosProgramados(surtimiento.getIdPrescripcion(),
                                                EstatusSurtimiento_Enum.PROGRAMADO.getValue());

                                if (numeroSurtimientosProgramados == 0) {
                                    Prescripcion p = new Prescripcion(surtimiento.getIdPrescripcion(),
                                            EstatusPrescripcion_Enum.FINALIZADA.getValue(),
                                            surtimiento.getUpdateFecha(),
                                            surtimiento.getUpdateIdUsuario());
                                    res = prescripcionMapper.cambiarEstatusPrescripcion(p);
                                    if (!res) {
                                        throw new Exception("Error al cambiarEstatusPrescripcion. ");
                                    } else {
                                        PrescripcionInsumo pi = new PrescripcionInsumo(surtimiento.getIdPrescripcion(),
                                                EstatusPrescripcion_Enum.FINALIZADA.getValue(),
                                                surtimiento.getUpdateFecha(),
                                                surtimiento.getUpdateIdUsuario());
                                        res = prescripcionInsumoMapper.cambiarEstatusPrescripcion(pi);
                                        if (!res) {
                                            throw new Exception("Error al cambiarEstatusPrescripcion. ");
                                        } else {
                                            res = solucionMapper.actualizar(solucion);
                                            if (!res) {
                                                throw new Exception("Error al actualizar solucion. ");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }                
            }
        } catch (Exception ex) {
            throw new Exception("Error al surtirPrescripcion " + ex.getMessage());
        }
        return res;
    }
    
    @Override
    public List<Surtimiento_Extend> obtenerPorFechaEstructuraPacientePrescripcionSolucionLazy(
            Date fechaProgramada, Date fechaProgramadaFin, ParamBusquedaReporte paramBusquedaReporte
            , int startingAt, int maxPerPage
            , List<String> tipoPrescripcionList, List<Integer> listEstatusPaciente
            , List<Integer> listEstatusPrescripcion, List<Estructura> listServiciosQueSurte
            , List<Integer> estatusSurtimientoLista, List<Integer> estatusSolucionLista
            , String idTipoSolucion, int tipoProceso
            , String sortField, SortOrder sortOrder
            , String tipoPrescripcion, String estatusSolucion, String nombreEstructura, String tipoSolucion, String nombreMedico
            , String folio, String fechaProgramada2, String nombrePaciente, String cama, String folioPrescripcion, boolean agruparParaAutorizar
    ) throws Exception {
        try {
            String order = sortOrder == SortOrder.ASCENDING ? "asc" : sortOrder == SortOrder.DESCENDING ? "desc" : null;
            return surtimientoMapper.obtenerPorFechaEstructuraPacientePrescripcionSolucionLazy(
                    fechaProgramada, fechaProgramadaFin , paramBusquedaReporte, startingAt, maxPerPage
                    , tipoPrescripcionList, listEstatusPaciente, listEstatusPrescripcion
                    , listServiciosQueSurte, estatusSurtimientoLista, estatusSolucionLista
                    , idTipoSolucion, tipoProceso, sortField, order
                    , tipoPrescripcion, estatusSolucion, nombreEstructura, tipoSolucion, nombreMedico
                    , folio, fechaProgramada2, nombrePaciente, cama, folioPrescripcion, agruparParaAutorizar
            );
        } catch (Exception e) {
            throw new Exception("Error al listar Surtimentos de Prescripciones y Soluciones. " + e.getMessage());
        }
    }

    @Override
    public Long obtenerTotalPorFechaEstructuraPacientePrescripcionSolucionLazy(
            Date fechaProgramada, ParamBusquedaReporte paramBusquedaReporte
            , List<String> tipoPrescripcionList, List<Integer> listEstatusPaciente
            , List<Integer> listEstatusPrescripcion, List<Estructura> listServiciosQueSurte
            , List<Integer> estatusSurtimientoLista, List<Integer> estatusSolucionLista
            , String idTipoSolucion, int tipoProceso
            , String tipoPrescripcion, String estatusSolucion, String nombreEstructura, String tipoSolucion, String nombreMedico
            , String folio, String fechaProgramada2, String nombrePaciente, String cama, String folioPrescripcion, boolean agruparParaAutorizar
    ) throws Exception {
        try {
            return surtimientoMapper.obtenerTotalPorFechaEstructuraPacientePrescripcionSolucionLazy(
                    fechaProgramada, paramBusquedaReporte, tipoPrescripcionList, listEstatusPaciente
                    , listEstatusPrescripcion, listServiciosQueSurte, estatusSurtimientoLista
                    , estatusSolucionLista, idTipoSolucion, tipoProceso
                    , tipoPrescripcion, estatusSolucion, nombreEstructura, tipoSolucion, nombreMedico
                    , folio, fechaProgramada2, nombrePaciente, cama, folioPrescripcion, agruparParaAutorizar
            );
        } catch (Exception e) {
            throw new Exception("Error al Obtener el total de Surtimientos de Prescripciones y Soluciones. " + e.getMessage());
        }
    }
    
    @Override
    public Surtimiento_Extend obtenerUltimoByIdPrescripcion(String idPrescripcion) throws Exception{
        try {
            return  surtimientoMapper.obtenerUltimoByIdPrescripcion(idPrescripcion);
        } catch (Exception ex) {
            throw new Exception("Error al obtenerUltimoByIdPrescripcion");
        }
    }

    @Override
    public Surtimiento_Extend obtenerSurtimientoByFolioSurtimientoOrFolioPrescripcion(String folioSurtimiento, String folioPrescripcion) throws Exception {
        try {
            return surtimientoMapper.obtenerSurtimientoByFolioSurtimientoOrFolioPrescripcion(folioSurtimiento, folioPrescripcion);
            
        } catch (Exception ex) {
            throw new Exception("Error idPrescripcion y idSurtimiento por obtenerSurtimientoByFolio:   " + ex.getMessage());
        }
    }
    
    @Override
    public Long obtenerTotalOrdenesSolucionLazy(Date fechaProgramada, ParamBusquedaReporte paramBusquedaReporte, List<String> tipoPrescripcionList, List<Integer> listEstatusPaciente, List<Integer> listEstatusPrescripcion, List<Integer> listEstatusSurtimiento, List<Estructura> listServiciosQueSurte) throws Exception {
        try {
            return surtimientoMapper.obtenerTotalOrdenesSolucionLazy(fechaProgramada, paramBusquedaReporte, tipoPrescripcionList, listEstatusPaciente, listEstatusPrescripcion, listEstatusSurtimiento, listServiciosQueSurte);
        } catch (Exception e) {
            throw new Exception("Error al Obtener el total de Surtimientos de Prescripciones y Soluciones. " + e.getMessage());
        }
    }
    
    @Override
    public List<Surtimiento_Extend> obtenerOrdenesSolucionLazy(Date fechaProgramada, ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage, List<String> tipoPrescripcionList, List<Integer> listEstatusPaciente, List<Integer> listEstatusPrescripcion, List<Integer> listEstatusSurtimiento, List<Estructura> listServiciosQueSurte, String sortField, SortOrder sortOrder) throws Exception {
        try {
            String order = sortOrder == SortOrder.ASCENDING ? "asc" : sortOrder == SortOrder.DESCENDING ? "desc" : null;
            return surtimientoMapper.obtenerOrdenesSolucionLazy(fechaProgramada, paramBusquedaReporte, startingAt, maxPerPage, tipoPrescripcionList, listEstatusPaciente, listEstatusPrescripcion, listEstatusSurtimiento, listServiciosQueSurte, sortField, order);
        } catch (Exception e) {
            throw new Exception("Errot al listar Ordenes de Solucion" + e.getMessage());
        }
    }
    

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public String registrarSurtimiento(Surtimiento surtimiento, List<SurtimientoInsumo> items) throws Exception {
        boolean res = false;
        String folio="";
        try {
            Folios folios = foliosMapper.obtenerPrefixPorDocument(TipoDocumento_Enum.RECETA.getValue());
            surtimiento.setFolio(Comunes.generaFolio(folios));
            folios.setSecuencia(Comunes.separaFolio(surtimiento.getFolio()));            
            res = foliosMapper.actualizaFolios(folios);
            if(res){
                res = surtimientoMapper.insertar(surtimiento);
                if(res){
                    res = surtimientoInsumoMapper.registraSurtimientoInsumoList(items);
                    if(!res)
                        throw new Exception("Error al insertar los SurtimientoInsumos.");
                }else
                    throw new Exception("Error al insertar el Surtimiento.");
            }else
                throw new Exception("Error al generar el folio.");
            folio = surtimiento.getFolio();
        } catch (Exception ex) {
           throw new Exception("Error al registrar el surtimiento y los insumos: "+ex.getMessage()); 
        }
        return folio;
    }
    
    @Override
    public Surtimiento_Extend obtenerSurtimientoExtendedByIdSurtimiento(String idSurtimiento) throws Exception {
        try {
            return surtimientoMapper.obtenerSurtimientoExtendedByIdSurtimiento(idSurtimiento);
        } catch(Exception ex) {
            throw new Exception("Error al obtenerSurtimientoExtendedByIdSurtimiento : "+ex.getMessage()); 
        }
    }
    
    @Override
    public Surtimiento_Extend obtenerSolucionPorIdSurtimiento(String idSurtimiento) throws Exception {
        try {
            return surtimientoMapper.obtenerSolucionPorIdSurtimiento(idSurtimiento);
        } catch (Exception e) {
            throw new Exception("Errot al listar Soluciones por idSurtimiento" + e.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean cancelarPrescripcionSurtimientoSolucion(String idPrescripcion, Solucion s) throws Exception {
        boolean res = false;
        try {
            res = solucionMapper.actualizar(s);
            if (!res){
                throw new Exception("Error al cancelar solución. "); 
            } else {
                String idUsuarioCancela = s.getIdUsuarioCancela();
                Date fecha = s.getUpdateFecha();
                Integer idEstatusSurtimiento = EstatusSurtimiento_Enum.CANCELADO.getValue();
                List<SurtimientoInsumo_Extend> siList = surtimientoInsumoMapper.obtenerByIdSurtimientoRecepMedi(s.getIdSurtimiento());
                if (siList !=null && !siList.isEmpty()) {
                    res = surtimientoInsumoMapper.cancelarSurtimientoInsumos(s.getIdSurtimiento(), idUsuarioCancela, fecha, idEstatusSurtimiento);
                    if (!res) {
                        throw new Exception("Error al cancelar insumos del surtimiento. ");
                    }
                }
                Surtimiento_Extend surt = surtimientoMapper.obtenerByIdSurtimiento(s.getIdSurtimiento());
                if (surt != null){
                    res = surtimientoMapper.cancelarSurtimiento(s.getIdSurtimiento(), idUsuarioCancela, fecha, idEstatusSurtimiento);
                    if (!res) {
                        throw new Exception("Error al cancelar el surtimiento. ");
                    }
                }
// TODO: 09ago2023 HRC - Agregar un campo para que el usuario decida se cancela la mezcla o todas las mezclas de la prescripción
//                        Integer idEstatusPrescripcion = EstatusPrescripcion_Enum.CANCELADA.getValue();
//                        Integer idEstatusGabinete = 0;
//                        res = prescripcionInsumoMapper.cancelarPorIdPrescripcion(idPrescripcion, idEstatusPrescripcion, idUsuarioCancela, fecha);
//                        if (!res){
//                            throw new Exception("Error al cancelar la prescripción. "); 
//                        } else {
//                            res = prescripcionMapper.cancelar(idPrescripcion, idUsuarioCancela, fecha, idEstatusPrescripcion, idEstatusGabinete);
//                            if (!res){
//                                throw new Exception("Error al cancelar la prescripción. "); 
//                            }
//                        }
            }
        } catch (Exception ex) {
            throw new Exception("Error al cancelarPrescripcionSurtimientoSolucion " + ex.getMessage());
        }
        return res;
    }
    
    @Override
    public List<Surtimiento_Extend> obtenerPorFechaEstructuraPacientePrescripcionFechaEntregSolucionLazy(
            Date fechaProgramada, ParamBusquedaReporte paramBusquedaReporte
            , int startingAt, int maxPerPage
            , List<String> tipoPrescripcionList, List<Integer> listEstatusPaciente
            , List<Integer> listEstatusPrescripcion, List<Estructura> listServiciosQueSurte
            , List<Integer> estatusSurtimientoLista, List<Integer> estatusSolucionLista
            , String sortField, SortOrder sortOrder
            , String idEstructura, String idTipoSolucion, Date fechaParaEntregar, Integer idHorarioParaEntregar) throws Exception {
        try {
            String order = sortOrder == SortOrder.ASCENDING ? "asc" : sortOrder == SortOrder.DESCENDING ? "desc" : null;
            return surtimientoMapper.obtenerPorFechaEstructuraPacientePrescripcionFechaEntregSolucionLazy(
                    fechaProgramada, paramBusquedaReporte, startingAt, maxPerPage, tipoPrescripcionList, listEstatusPaciente, listEstatusPrescripcion, listServiciosQueSurte, estatusSurtimientoLista, estatusSolucionLista, sortField, order, idEstructura, idTipoSolucion, fechaParaEntregar, idHorarioParaEntregar);
        } catch (Exception e) {
            throw new Exception("Error al listar Surtimentos de Prescripciones y Soluciones. " + e.getMessage());
        }
    }

    @Override
    public Long obtenerTotalPorFechaEstructuraPacientePrescripcionFechaEntregSolucionLazy(Date fechaProgramada, ParamBusquedaReporte paramBusquedaReporte, List<String> tipoPrescripcionList, List<Integer> listEstatusPaciente, List<Integer> listEstatusPrescripcion, List<Estructura> listServiciosQueSurte, List<Integer> estatusSurtimientoLista, List<Integer> estatusSolucionLista
    , String idEstructura, String idTipoSolucion, Date fechaParaEntregar, Integer idHorarioParaEntregar) throws Exception {
        try {
            return surtimientoMapper.obtenerTotalPorFechaEstructuraPacientePrescripcionFechaEntregSolucionLazy(fechaProgramada, paramBusquedaReporte, tipoPrescripcionList, listEstatusPaciente, listEstatusPrescripcion, listServiciosQueSurte, estatusSurtimientoLista, estatusSolucionLista, idEstructura, idTipoSolucion, fechaParaEntregar, idHorarioParaEntregar);
        } catch (Exception e) {
            throw new Exception("Error al Obtener el total de Surtimientos de Prescripciones y Soluciones. " + e.getMessage());
        }
    }
    
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean cancelarPrescripcionNptSurtimientoSolucion(String idSurtimiento, Solucion s) throws Exception {
        boolean res = false;
        try {
            res = solucionMapper.actualizar(s);
            if (!res){
                throw new Exception("Error al cancelar solución. "); 
            } else {
                String idUsuarioCancela = s.getIdUsuarioCancela();
                Date fecha = s.getUpdateFecha();
                
                Integer idEstatusSurtimiento = EstatusSurtimiento_Enum.CANCELADO.getValue();
                
                List<SurtimientoInsumo_Extend> siList = surtimientoInsumoMapper.obtenerByIdSurtimientoRecepMedi(s.getIdSurtimiento());
                
                if (siList !=null && !siList.isEmpty()) {
                    res = surtimientoInsumoMapper.cancelarSurtimientoInsumos(s.getIdSurtimiento(), idUsuarioCancela, fecha, idEstatusSurtimiento);
                    if (!res) {
                        throw new Exception("Error al cancelar insumos del surtimiento. ");
                    }
                }
                
                Surtimiento_Extend surt = surtimientoMapper.obtenerByIdSurtimiento(s.getIdSurtimiento());
                if (surt != null){
                    res = surtimientoMapper.cancelarSurtimiento(s.getIdSurtimiento(), idUsuarioCancela, fecha, idEstatusSurtimiento);
                    if (!res) {
                        throw new Exception("Error al cancelar el surtimiento. ");
                    } else {
                        res = prescripcionMapper.cancelar(
                                surt.getIdPrescripcion()
                                , idUsuarioCancela
                                , new java.util.Date()
                                , EstatusPrescripcion_Enum.CANCELADA.getValue()
                                , EstatusGabinete_Enum.CANCELADA.getValue());
                        if (!res) {
                            throw new Exception("Error al cancelar la prescripcion. ");
                        } else {
                            NutricionParenteral o = new NutricionParenteral();
                            o.setIdSurtimiento(idSurtimiento);
                            
                            NutricionParenteral np = nutricionPareteralMapper.obtener(o);
                            np.setIdEstatusSolucion(EstatusSolucion_Enum.CANCELADA.getValue());
                            np.setUpdateFecha(new java.util.Date());
                            np.setUpdateIdUsuario(idUsuarioCancela);
                            res = nutricionPareteralMapper.actualizar(np);
                            if (!res) {
                                throw new Exception("Error al cancelar la prescripcion. ");
                            }
                        }                        
                    }
                }
            }
        } catch (Exception ex) {
            throw new Exception("Error al cancelarPrescripcionSurtimientoSolucion " + ex.getMessage());
        }
        return res;
    }


    
    @Override
    public List<Surtimiento_Extend> obtenerSolucionesPorIdEstructuraTipoMezclaFechas(
            ParamBusquedaReporte paramBusquedaReporte
            , List<String> listTipoPrescripcion
            , List<Integer> listEstatusPaciente
            , List<Integer> listEstatusPrescripcion
            , List<Estructura> listServiciosQueSurte
            , List<Integer> estatusSurtimientoLista
            , List<Integer> estatusSolucionLista
            , String idTipoSolucion
            , String tipoPrescripcion
            , String tipoSolucion
            , String fechaProgramada
            , String folio
            , String folioPrescripcion
            , String nombrePaciente
            , String nombreEstructura
            , String cama
            , String nombreMedico
            , String estatusSolucion
    ) throws Exception {
        try {
            return surtimientoMapper.obtenerSolucionesPorIdEstructuraTipoMezclaFechas(
                    paramBusquedaReporte
                    , listTipoPrescripcion
                    , listEstatusPaciente
                    , listEstatusPrescripcion
                    , listServiciosQueSurte
                    , estatusSurtimientoLista
                    , estatusSolucionLista
                    , idTipoSolucion
                    , tipoPrescripcion
                    , tipoSolucion
                    , fechaProgramada
                    , folio
                    , folioPrescripcion
                    , nombrePaciente
                    , nombreEstructura
                    , cama
                    , nombreMedico
                    , estatusSolucion
            );
        } catch (Exception e) {
            throw new Exception("Error al listar Surtimentos de Prescripciones y Soluciones. " + e.getMessage());
        }
    }

    @Override
    public Long obtenerTotalSolucionesPorIdEstructuraTipoMezclaFechas(
            ParamBusquedaReporte paramBusquedaReporte
            , List<String> listTipoPrescripcion
            , List<Integer> listEstatusPaciente
            , List<Integer> listEstatusPrescripcion
            , List<Estructura> listServiciosQueSurte
            , List<Integer> estatusSurtimientoLista
            , List<Integer> estatusSolucionLista
            , String idTipoSolucion
            , String tipoPrescripcion
            , String tipoSolucion
            , String fechaProgramada
            , String folio
            , String folioPrescripcion
            , String nombrePaciente
            , String nombreEstructura
            , String cama
            , String nombreMedico
            , String estatusSolucion
    ) throws Exception {
        try {
            return surtimientoMapper.obtenerTotalSolucionesPorIdEstructuraTipoMezclaFechas(
                    paramBusquedaReporte
                    , listTipoPrescripcion
                    , listEstatusPaciente
                    , listEstatusPrescripcion
                    , listServiciosQueSurte
                    , estatusSurtimientoLista
                    , estatusSolucionLista
                    , idTipoSolucion
                    , tipoPrescripcion
                    , tipoSolucion
                    , fechaProgramada
                    , folio
                    , folioPrescripcion
                    , nombrePaciente
                    , nombreEstructura
                    , cama
                    , nombreMedico
                    , estatusSolucion
            );
        } catch (Exception e) {
            throw new Exception("Error al Obtener el total de Surtimientos de Prescripciones y Soluciones. " + e.getMessage());
        }
    }
    

    
}
