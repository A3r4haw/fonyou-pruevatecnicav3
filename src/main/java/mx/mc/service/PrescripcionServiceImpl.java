package mx.mc.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.mc.enums.EstatusSolucion_Enum;
import mx.mc.enums.TipoDocumento_Enum;
import mx.mc.enums.TipoMotivo_Enum;
import mx.mc.mapper.DevolucionMinistracionMapper;
import mx.mc.mapper.DiagnosticoMapper;
import mx.mc.mapper.DiagnosticoPacienteMapper;
import mx.mc.mapper.FoliosMapper;

import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.InventarioMapper;
import mx.mc.mapper.MedControlSurYReabMapper;
import mx.mc.mapper.MovimientoInventarioMapper;
import mx.mc.mapper.PrescripcionInsumoMapper;
import mx.mc.mapper.PrescripcionMapper;
import mx.mc.mapper.SolucionMapper;
import mx.mc.mapper.SurtimientoEnviadoMapper;
import mx.mc.mapper.SurtimientoInsumoMapper;
import mx.mc.mapper.SurtimientoMapper;
import mx.mc.model.AlmacenInsumoComprometido;
import mx.mc.model.DiagnosticoPaciente;
import mx.mc.model.Folios;
import mx.mc.model.IntipharmPrescription;
import mx.mc.model.IntipharmPrescriptionMedication;
import mx.mc.model.Inventario;
import mx.mc.model.InventarioExtended;
import mx.mc.model.MovimientoInventario;
import mx.mc.model.Paciente;
import mx.mc.model.ParamLibMedControlados;
import mx.mc.model.PrescripcionInsumo;
import mx.mc.model.Prescripcion;
import mx.mc.model.PrescripcionInsumo_Extended;
import mx.mc.model.Prescripcion_Extended;
import mx.mc.model.ReporteLibroControlados;
import mx.mc.model.Solucion;
import mx.mc.model.SolucionExtended;
import mx.mc.model.Surtimiento;
import mx.mc.model.SurtimientoEnviado;
import mx.mc.model.SurtimientoInsumo;
import mx.mc.model.SurtimientoInsumo_Extend;
import mx.mc.model.Surtimiento_Extend;
import mx.mc.util.Comunes;
import mx.mc.util.FechaUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/*
 * @author hramirez
 */
@Service
public class PrescripcionServiceImpl extends GenericCrudServiceImpl<Prescripcion, String> implements PrescripcionService {

    @Autowired
    private PrescripcionMapper prescripcionMapper;
    @Autowired
    private DevolucionMinistracionMapper devolucionMinistracionMapper;
    @Autowired
    private PrescripcionInsumoMapper prescripcionInsumoMapper;
    @Autowired
    private DiagnosticoMapper diagnosticoMapper;
    @Autowired
    private DiagnosticoPacienteMapper diagnosticoPacienteMapper;
    @Autowired
    private SurtimientoMapper surtimientoMapper;
    @Autowired
    private SurtimientoInsumoMapper surtimientoInsumoMapper;
    @Autowired
    private SurtimientoEnviadoMapper surtimientoEnviadoMapper;
    @Autowired
    private InventarioMapper inventarioMapper;
    @Autowired
    private MovimientoInventarioMapper movimientoInventarioMapper;
    @Autowired
    private FoliosMapper foliosMapper;
    @Autowired
    private MedControlSurYReabMapper medControlSurYReabMapperMapper;
    @Autowired
    private SolucionMapper solucionMapper;

    @Autowired
    public PrescripcionServiceImpl(GenericCrudMapper<Prescripcion, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public List<Prescripcion_Extended> obtenerPrescripcionesPorIdPaciente(
            String idPaciente, String idPrescripcion, Date fechaPrescripcion, String tipoConsulta, Integer recurrente, String cadenaBusqueda) throws Exception {
        try {
            return prescripcionMapper.obtenerPrescripcionesPorIdPaciente(idPaciente, idPrescripcion, fechaPrescripcion, tipoConsulta, recurrente, cadenaBusqueda);
        } catch (Exception ex) {
            throw new Exception("Error listar Prescripciones de Paciente. " + ex.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean registrarPrescripcion(Prescripcion p, List<DiagnosticoPaciente> diagnoticoList, List<PrescripcionInsumo> insumoList, List<Surtimiento> surtimientoList, List<AlmacenInsumoComprometido> comprometidoLis) throws Exception {
        boolean res = false;
        try {

//            Consultar y generar Folio
            int tipoDoc = TipoDocumento_Enum.PRESCRIPCION.getValue();
            Folios folio = foliosMapper.obtenerPrefixPorDocument(tipoDoc);

            p.setFolio(Comunes.generaFolio(folio));

            folio.setSecuencia(Comunes.separaFolio(p.getFolio()));
            res = foliosMapper.actualizaFolios(folio);
            if (!res) {
                throw new Exception("Error al registrar Folio de Prescripción. ");

            } else {
                res = prescripcionMapper.insertar(p);
                if (!res) {
                    throw new Exception("Error al registrar Prescripción. ");

                } else {
                    res = diagnosticoMapper.registraDiagnosticoList(diagnoticoList);
                    if (!res) {
                        throw new Exception("Error al registrar Diagnósticos de Prescripción. ");

                    } else {
                        res = prescripcionInsumoMapper.registraMedicamentoList(insumoList);
                        if (!res) {
                            throw new Exception("Error al registrar Medicamentos de Prescripción. ");

                        } else {
                            for (Surtimiento item : surtimientoList) {
                                item.setIdPrescripcion(p.getIdPrescripcion());

                                tipoDoc = TipoDocumento_Enum.SURTIMIENTO_DE_PRESCRIPCION.getValue();
                                folio = foliosMapper.obtenerPrefixPorDocument(tipoDoc);

                                item.setFolio(Comunes.generaFolio(folio));

                                folio.setSecuencia(Comunes.separaFolio(item.getFolio()));
                                res = foliosMapper.actualizaFolios(folio);

                                if (!res) {
                                    throw new Exception("Error al registrar Folio de Surtimiento. ");

                                } else {
                                    res = surtimientoMapper.insertar(item);
                                    if (!res) {
                                        throw new Exception("Error al registrar Surtimientos de Prescripción. ");

                                    } else {
                                        res = surtimientoInsumoMapper.registraSurtimientoInsumoList(item.getDetalle());

                                        if (!res) {
                                            throw new Exception("Error al registrar Medicamentos de Surtimiento de Prescripción. ");

                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            throw new Exception("Error registrar Prescripción. " + ex.getMessage());
        }
        return res;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean registrarPrescripcionManual(
            Prescripcion p, Surtimiento s, List<DiagnosticoPaciente> diagnoticoList,
            List<PrescripcionInsumo> insumoList,
            List<SurtimientoInsumo_Extend> surtimientoList,
            List<InventarioExtended> listaInventario,
            List<PrescripcionInsumo_Extended> listaPrescripcionInsumoExtended,
            List<MovimientoInventario> listaMovInventario) throws Exception {
        boolean res = false;
        try {
            res = prescripcionMapper.insertar(p);
            if (!res) {
                throw new Exception("Error al registrar Prescripción. ");

            } else {
                res = diagnosticoMapper.registraDiagnosticoList(diagnoticoList);
                if (!res) {
                    throw new Exception("Error al registrar Diagnósticos de Prescripción. ");

                } else {
                    res = prescripcionInsumoMapper.registraMedicamentoList(insumoList);
                    if (!res) {
                        throw new Exception("Error al registrar Medicamentos de Prescripción. ");

                    } else {

                        int tipoDoc = TipoDocumento_Enum.SURTIMIENTO_DE_PRESCRIPCION.getValue();
                        Folios folio = foliosMapper.obtenerPrefixPorDocument(tipoDoc);
                        String folioSurtimiento = Comunes.generaFolio(folio);
                        s.setFolio(folioSurtimiento);
                        folio.setSecuencia(Comunes.separaFolio(folioSurtimiento));
                        res = foliosMapper.actualizaFolios(folio);

                        if (!res) {
                            throw new Exception("Error al registrar Folio de Surtimiento. ");

                        } else {
                            s.setFolio(folioSurtimiento);
                            res = surtimientoMapper.insertar(s);
                            if (!res) {
                                throw new Exception("Error al registrar Surtimientos de Prescripción. ");
                            } else {
                                res = surtimientoInsumoMapper.registraSurtimientoInsumoExtendedList(surtimientoList);
                                if (!res) {
                                    throw new Exception("Error al registrar la lista de Surtimiento Insumo. ");
                                } else {
                                    for (PrescripcionInsumo_Extended item : listaPrescripcionInsumoExtended) {
                                        res = surtimientoEnviadoMapper.registraSurtimientoEnviadoExtendedList(
                                                item.getSurtimientoEnviadoExtendedList());
                                    }
                                    if (!res) {
                                        throw new Exception("Error al registrar la lista de Surtimiento Insumo. ");
                                    } else {
                                        res = inventarioMapper.actualizarInventarioRecetaManual(listaInventario);
                                        if (!res) {
                                            throw new Exception("Error al registrar la lista de Inventarios. ");
                                        } else {
                                            for (MovimientoInventario mi : listaMovInventario) {
                                                mi.setFolioDocumento(folioSurtimiento);
                                            }
                                            res = movimientoInventarioMapper.insertarMovimientosInventarios(listaMovInventario);
                                            if (!res) {
                                                throw new Exception("Error al registrar la lista de Movimientos Inventario.");
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
            throw new Exception("Error registrar Prescripción. " + ex.getMessage());
        }
        return res;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean editarPrescripcion(Prescripcion p, List<DiagnosticoPaciente> diagnoticoList, List<PrescripcionInsumo> insumoList, List<Surtimiento> surtimientoList, List<AlmacenInsumoComprometido> comprometidoLis) throws Exception {
        boolean res = false;
        try {
            res = prescripcionMapper.actualizar(p);
            if (!res) {
                throw new Exception("Error al actualizar Prescripción. ");

            } else {
                diagnosticoMapper.eliminaDiagnosticoList(p.getIdPrescripcion());
                res = diagnosticoMapper.registraDiagnosticoList(diagnoticoList);
                if (!res) {
                    throw new Exception("Error al actualizar Diagnosticos de Prescripción. ");

                } else {

                    prescripcionInsumoMapper.eliminaMedicamentoList(p.getIdPrescripcion());
                    res = prescripcionInsumoMapper.registraMedicamentoList(insumoList);

                    if (!res) {
                        throw new Exception("Error al actualizar Medicamentos de Prescripción. ");

                    } else {

                        surtimientoInsumoMapper.eliminarPorIdPrescripcion(p.getIdPrescripcion());
                        surtimientoMapper.eliminarPorIdPrescripcion(p.getIdPrescripcion());

                        for (Surtimiento item : surtimientoList) {
                            item.setIdPrescripcion(p.getIdPrescripcion());
                            res = surtimientoMapper.insertar(item);
                            if (!res) {
                                throw new Exception("Error al actualizar Surtimiento de Prescripción. ");
                            } else {
                                res = surtimientoInsumoMapper.registraSurtimientoInsumoList(item.getDetalle());
                                if (!res) {
                                    throw new Exception("Error al actualizar Medicamento de Surtimiento de Prescripción. ");

                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            throw new Exception("Error registrar Prescripción. " + ex.getMessage());
        }
        return res;
    }

    @Override
    public List<PrescripcionInsumo_Extended> obtenerInsumosPorIdPrescripcion(String idPrescripcion, Integer tipoInsumo) throws Exception {
        try {
            return prescripcionInsumoMapper.obtenerInsumosPorIdPrescripcion(idPrescripcion, tipoInsumo);
        } catch (Exception ex) {
            throw new Exception("Error al obtener Insumos de Prescripción. " + ex.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean cancelarPrescripcion(String idPrescripcion, String idUsuario, Date fecha, Integer idEstatusPrescripcion, Integer idEstatusGabinete, Integer idEstatusSurtimiento) throws Exception {
        boolean res = false;
        try {

            res = surtimientoInsumoMapper.cancelar(idPrescripcion, idUsuario, fecha, idEstatusSurtimiento);

            if (!res) {
                throw new Exception("Error al cancelar medicamentos de surtimientos de la Prescripción ");

            } else {

                res = surtimientoMapper.cancelar(idPrescripcion, idUsuario, fecha, idEstatusSurtimiento);

                if (!res) {
                    throw new Exception("Error al cancelar surtimientos de la Prescripción. ");

                } else {
                    res = prescripcionMapper.cancelar(idPrescripcion, idUsuario, fecha, idEstatusPrescripcion, idEstatusGabinete);
                    if (!res) {
                        throw new Exception("Error al cancelar la Prescripción. ");
                    }
                }
            }

        } catch (Exception ex) {
            throw new Exception("Error al cancelar la Prescripción. " + ex.getMessage());
        }
        return res;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean cancelarPrescripcionChiconcuac(Prescripcion prescripcion, String idUsuario, Date fecha, Integer idEstatusPrescripcion, Integer idEstatusGabinete, Integer idEstatusSurtimiento) throws Exception {
        boolean res = false;
        try {

            res = surtimientoInsumoMapper.cancelar(prescripcion.getIdPrescripcion(), idUsuario, fecha, idEstatusSurtimiento);

            if (!res) {
                throw new Exception("Error al cancelar medicamentos de surtimientos de la Prescripción ");

            } else {
                res = surtimientoMapper.cancelarSurtimientoChiconcuac(prescripcion.getIdPrescripcion(), idUsuario, fecha, idEstatusSurtimiento);

                if (!res) {
                    throw new Exception("Error al cancelar surtimientos de la Prescripción. ");

                } else {
                    res = prescripcionMapper.cancelarChiconcuac(prescripcion.getIdPrescripcion(),
                            idUsuario, fecha, idEstatusPrescripcion, idEstatusGabinete, prescripcion.getFolio());
                    if (!res) {
                        throw new Exception("Error al cancelar la Prescripción. ");
                    } else {
                        List<SurtimientoEnviado> listaSurtEnv = surtimientoEnviadoMapper.obtenerByIdPrescripcion(prescripcion.getIdPrescripcion());
                        List<Inventario> listaInv = new ArrayList<>();
                        List<MovimientoInventario> listaMovInv = new ArrayList<>();
                        for (SurtimientoEnviado item : listaSurtEnv) {
                            Inventario inventario = new Inventario();
                            inventario.setIdInventario(item.getIdInventarioSurtido());
                            inventario.setCantidadActual(item.getCantidadEnviado());
                            listaInv.add(inventario);

                            MovimientoInventario movimientoInve = new MovimientoInventario();
                            movimientoInve.setIdMovimientoInventario(Comunes.getUUID());
                            movimientoInve.setIdTipoMotivo(TipoMotivo_Enum.DEV_CANCEL_RECETA.getMotivoValue());
                            movimientoInve.setFecha(new Date());
                            movimientoInve.setIdUsuarioMovimiento(idUsuario);
                            movimientoInve.setIdEstrutcuraOrigen(prescripcion.getIdEstructura());
                            movimientoInve.setIdEstrutcuraDestino(prescripcion.getIdEstructura());
                            movimientoInve.setIdInventario(inventario.getIdInventario());
                            movimientoInve.setCantidad(inventario.getCantidadActual());
                            movimientoInve.setFolioDocumento(prescripcion.getFolio());
                            listaMovInv.add(movimientoInve);
                        }
                        res = inventarioMapper.revertirInventarioList(listaInv);
                        if (res) {
                            res = movimientoInventarioMapper.insertarMovimientosInventarios(listaMovInv);
                            if (!res) {
                                throw new Exception("Error al cancelar la Prescripción. ");
                            }
                        } else {
                            throw new Exception("Error al cancelar la Prescripción. ");
                        }

                    }
                }
            }

        } catch (Exception ex) {
            throw new Exception("Error al cancelar la Prescripción. " + ex.getMessage());
        }
        return res;
    }

    @Override
    public List<Prescripcion_Extended> obtenerRecetasManuales(
            List<String> listaTipoPrescripcion,
            List<Integer> listaEstatusPrescripcion,
            String cadenaBusqueda) throws Exception {
        try {
            return prescripcionMapper.obtenerRecetasManuales(
                    listaTipoPrescripcion, listaEstatusPrescripcion, cadenaBusqueda);
        } catch (Exception ex) {
            throw new Exception("Error al obtener Insumos de Prescripción. " + ex.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public List<ReporteLibroControlados> obtenerReporteMedicamentosControlados(
            ParamLibMedControlados parametrosBusqueda) throws Exception {
        List<ReporteLibroControlados> listaMedControlados = new ArrayList<>();
        try {
            listaMedControlados = this.medControlSurYReabMapperMapper.
                    obtenerReporteMedicamentosControlados(parametrosBusqueda);
        } catch (Exception ex) {
            throw new Exception("Error al cancelar la Prescripción. " + ex.getMessage());
        }
        return listaMedControlados;
    }

    @Override
    public Prescripcion verificarFolioCancelar(String folio) throws Exception {
        try {
            return prescripcionMapper.verificarFolioCancelar(folio);
        } catch (Exception e) {
            throw new Exception("Error al verificar el Fólio" + e.getMessage());
        }
    }

    @Override
    public ArrayList<IntipharmPrescription> selectCabiDrgOrdList() throws Exception {
        try {
            return prescripcionMapper.selectCabiDrgOrdList();
        } catch (Exception e) {
            throw new Exception("Error al obtener los registros de IntipharmPrescription " + e.getMessage());
        }
    }

    @Override
    public List<IntipharmPrescriptionMedication> selectCabiDrgOrdListMedication(String folio) throws Exception {
        try {
            return prescripcionMapper.selectCabiDrgOrdListMedication(folio);
        } catch (Exception e) {
            throw new Exception("Error al obtener los registros de IntipharmPrescriptionMedication " + e.getMessage());
        }
    }

    @Override
    public boolean actualizarByfolioSurt(String folio) throws Exception {
        try {
            return prescripcionMapper.actualizarByfolioSurt(folio);
        } catch (Exception e) {
            throw new Exception("Error al actualizar el folio de la Prescripción" + e.getMessage());
        }
    }

    @Override
    public String obtenerFolioPrescBySurt(String folio) throws Exception {
        String folioP = "";
        try {
            folioP = prescripcionMapper.obtenerFolioPrescBySurt(folio);
        } catch (Exception e) {
            throw new Exception("Error al consultar el folio de la Prescripción " + e.getMessage());
        }
        return folioP;
    }

    @Override
    public List<Prescripcion_Extended> obtenerByFolioPrescripcionForList(String folio, Paciente pacienteObj, String filter) throws Exception {
        List<Prescripcion_Extended> prescList = new ArrayList<>();

        String idAlmacen = "";
        if (filter.equalsIgnoreCase("folio")) {
            try {
                idAlmacen = devolucionMinistracionMapper.obteneridEstructuraAlmacenOfSurtimiento(folio);
                String idEstructura = devolucionMinistracionMapper.obtenerAlmacenPadreOfSurtimiento(idAlmacen);
                prescList = prescripcionMapper.obtenerByFolioPrescripcionForList(folio, idEstructura, filter);
            } catch (Exception ex) {
                Logger.getLogger(DevolucionMinistracionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                throw new Exception("No se pudo obtener las prescripción");
            }
        } else if (filter.equalsIgnoreCase("paciente")) {
            try {
                String idEstructura = devolucionMinistracionMapper.obtenerAlmacenPadreOfSurtimiento(pacienteObj.getIdEstructura());
                prescList = prescripcionMapper.obtenerByFolioPrescripcionForList(pacienteObj.getNombreCompleto(), idEstructura, filter);
            } catch (Exception ex) {
                Logger.getLogger(DevolucionMinistracionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                throw new Exception("No se pudo obtener las prescripción");
            }
        }

        return prescList;
    }

    @Override
    public Prescripcion_Extended obtenerPrescripcionByFolio(String prescripcionFolio) throws Exception {
        try {
            return prescripcionMapper.obtenerPrescripcionByFolio(prescripcionFolio);
        } catch (Exception e) {
            throw new Exception("Error al obtener los registros de IntipharmPrescriptionMedication " + e.getMessage());
        }
    }

    @Override
    public PrescripcionInsumo_Extended obtenerPrescripcionInsumoByIdPrescripcion(String idPrescripcion) throws Exception {
        try {
            return prescripcionInsumoMapper.obtenerPrescripcionInsumoByIdPrescripcion(idPrescripcion);
        } catch (Exception e) {
            throw new Exception("Error al obtener los registros de IntipharmPrescriptionMedication " + e.getMessage());
        }
    }

    @Override
    public boolean actualizarInsumo(PrescripcionInsumo_Extended item) throws Exception {
        try {
            return prescripcionInsumoMapper.actualizarInsumo(item);
        } catch (Exception e) {
            throw new Exception("Error al obtener los registros de IntipharmPrescriptionMedication " + e.getMessage());
        }
    }

    @Override
    public Prescripcion_Extended obtenerByFolioPrescripcion(String prescripcionFolio) throws Exception {
        try {
            return prescripcionMapper.obtenerByFolioPrescripcion(prescripcionFolio);
        } catch (Exception ex) {
            throw new Exception("Error al obtener la prescripcion con folio : " + prescripcionFolio + "   " + ex.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean registrarSurtirOrdenSolucion(
            boolean existePrescripcion,
            Prescripcion prescripcion,
            List<PrescripcionInsumo> insumoList,
            Surtimiento surtimiento,
            List<SurtimientoInsumo_Extend> surtimientoList,
            List<SurtimientoEnviado> surtimientoEnviadoList,
            List<InventarioExtended> listaInventario,
            List<MovimientoInventario> listaMovInventario) throws Exception {
        boolean res = false;
        try {
            if (existePrescripcion) {
                res = prescripcionMapper.actualizar(prescripcion);
            } else {
                res = prescripcionMapper.insertar(prescripcion);
            }
            if (!res) {
                throw new Exception("Error al registrar Prescripción. ");
            } else {
                res = prescripcionInsumoMapper.registraMedicamentoList(insumoList);
                if (!res) {
                    throw new Exception("Error al registrar Medicamentos de Prescripción. ");
                } else {
                    int tipoDoc = TipoDocumento_Enum.ORDEN_MANUAL_SOLUCIONES.getValue();
                    Folios folio = foliosMapper.obtenerPrefixPorDocument(tipoDoc);
                    String folioSurtimiento = Comunes.generaFolio(folio);
                    folio.setSecuencia(Comunes.separaFolio(folioSurtimiento));
                    res = foliosMapper.actualizaFolios(folio);
                    if (!res) {
                        throw new Exception("Error al registrar Folio de Surtimiento. ");
                    } else {
                        surtimiento.setFolio(folioSurtimiento);
                        res = surtimientoMapper.insertar(surtimiento);
                        if (!res) {
                            throw new Exception("Error al registrar Surtimientos de Prescripción. ");
                        } else {
                            res = surtimientoInsumoMapper.registraSurtimientoInsumoExtendedList(surtimientoList);
                            if (!res) {
                                throw new Exception("Error al registrar la lista de Surtimiento Insumo. ");
                            } else {
                                res = surtimientoEnviadoMapper.registraSurtimientoEnviadoList(surtimientoEnviadoList);
                                if (!res) {
                                    throw new Exception("Error al registrar la lista de Surtimiento Insumo. ");
                                } else {
                                    res = inventarioMapper.actualizarInventarioRecetaManual(listaInventario);
                                    if (!res) {
                                        throw new Exception("Error al registrar la lista de Inventarios. ");
                                    } else {
                                        for (MovimientoInventario mi : listaMovInventario) {
                                            mi.setFolioDocumento(folioSurtimiento);
                                        }
                                        res = movimientoInventarioMapper.insertarMovimientosInventarios(listaMovInventario);
                                        if (!res) {
                                            throw new Exception("Error al registrar la lista de Movimientos Inventario.");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            throw new Exception("Error en registrarSurtirOrdenSolucion. " + ex.getMessage());
        }
        return res;
    }

    @Override
    public PrescripcionInsumo_Extended obtenerUltimaPrescripcionInsumoByIdPrescripcion(String idPrescripcion) throws Exception {
        try {
            return prescripcionInsumoMapper.obtenerPrescripcionInsumoByIdPrescripcion(idPrescripcion);
        } catch (Exception e) {
            throw new Exception("Error al obtener los registros de IntipharmPrescriptionMedication " + e.getMessage());
        }
    }

    @Override
    public List<Prescripcion_Extended> obtenerRecetasSurtidas(String cadenaBusqueda) throws Exception {
        try {
            return prescripcionMapper.obtenerRecetasSurtidas(cadenaBusqueda);
        } catch (Exception e) {
            throw new Exception("Error al obtener las recetas surtidas " + e.getMessage());
        }
    }

    @Override
    public PrescripcionInsumo_Extended obtenerPrimerPrescripcionInsumoByIdPrescripcion(String idPrescripcion) throws Exception {
        try {
            return prescripcionInsumoMapper.obtenerPrimerPrescripcionInsumoByIdPrescripcion(idPrescripcion);
        } catch (Exception e) {
            throw new Exception("Error al obtener los registros de IntipharmPrescriptionMedication " + e.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean registrarOrdenSolucion(
            boolean existePrescripcion,
            Prescripcion prescripcion,
            List<PrescripcionInsumo> insumoList,
            Surtimiento_Extend surtimiento,
            List<SurtimientoInsumo_Extend> surtimientoList,
            List<DiagnosticoPaciente> listaDiagnosticoPaciente, boolean autorizar) throws Exception {
        boolean res = false;
        try {
            if (existePrescripcion) {
                res = prescripcionMapper.actualizar(prescripcion);
            } else {
                if (prescripcion.getFolio() == null) {
                    int tipoDoc = TipoDocumento_Enum.PRESCRIPCION.getValue();
                    Folios folio = foliosMapper.obtenerPrefixPorDocument(tipoDoc);
                    String folioNuevo = Comunes.generaFolio(folio);
                    folio.setSecuencia(Comunes.separaFolio(folioNuevo));
                    res = foliosMapper.actualizaFolios(folio);
                    if (!res) {
                        throw new Exception("Error al registrar Folio de Surtimiento. ");
                    } else {
                        prescripcion.setFolio(folioNuevo);
                    }
                }

                res = prescripcionMapper.insertar(prescripcion);
            }
            if (!res) {
                throw new Exception("Error al registrar Prescripción. ");
            } else {
                res = prescripcionInsumoMapper.registraMedicamentoList(insumoList);
                if (!res) {
                    throw new Exception("Error al registrar Medicamentos de Prescripción. ");
                } else {
                    if (!listaDiagnosticoPaciente.isEmpty()) {
                        for (DiagnosticoPaciente dp : listaDiagnosticoPaciente) {
                            if (diagnosticoPacienteMapper.obtenerPorPrescripcionDiagnostico(dp.getIdPrescripcion(), dp.getIdDiagnostico()) == null) {
                                res = diagnosticoPacienteMapper.insertar(dp);
                                if (!res) {
                                    throw new Exception("Error al registrar los diagnosticos de la prescripcion de soluciones. ");
                                }
                            }
                        }
                    }
                    Integer numDias = 1;
                    Integer numTomas = 1;
                    Integer numHoras = insumoList.get(0).getFrecuencia();
                    Integer numMezclas = 1;
                    if(numHoras >= 13) {
                        numDias = insumoList.get(0).getDuracion();
                        numMezclas = (numDias * 24) / numHoras + 1;
                    } else {
                        if (numHoras > 0){
                            numDias = insumoList.get(0).getDuracion();
                            numTomas = (24 / numHoras);
                            numMezclas = numDias * numTomas;
                        }
                    }
                    for (int i = 0; i < numMezclas; i++) {
                        if (i > 0) {
                            Date fechaProg = sumarHoras(surtimiento.getFechaProgramada(), numHoras);
                            surtimiento.setIdSurtimiento(Comunes.getUUID());
                            surtimiento.setFechaProgramada(fechaProg);
                            for (SurtimientoInsumo_Extend si : surtimientoList) {
                                si.setIdSurtimientoInsumo(Comunes.getUUID());
                                si.setIdSurtimiento(surtimiento.getIdSurtimiento());
                                si.setFechaProgramada(surtimiento.getFechaProgramada());
                            }
                        }
                        int tipoDoc = TipoDocumento_Enum.ORDEN_MANUAL_SOLUCIONES.getValue();
                        Folios folio = foliosMapper.obtenerPrefixPorDocument(tipoDoc);
                        String folioSurtimiento = Comunes.generaFolio(folio);
                        folio.setSecuencia(Comunes.separaFolio(folioSurtimiento));
                        res = foliosMapper.actualizaFolios(folio);
                        if (!res) {
                            throw new Exception("Error al registrar Folio de Surtimiento. ");
                        } else {
                            surtimiento.setFolio(folioSurtimiento);
                            res = surtimientoMapper.insertar(surtimiento);

                            if (!res) {
                                throw new Exception("Error al registrar Surtimientos de Prescripción. ");
                            } else {
                                res = surtimientoInsumoMapper.registraSurtimientoInsumoExtendedList(surtimientoList);
                                if (!res) {
                                    throw new Exception("Error al registrar la lista de Surtimiento Insumo. ");
                                } else {

                                    Solucion s = new Solucion();
                                    s.setIdSolucion(Comunes.getUUID());
                                    s.setIdSurtimiento(surtimiento.getIdSurtimiento());
                                if(autorizar) {
                                    s.setIdEstatusSolucion(EstatusSolucion_Enum.POR_AUTORIZAR.getValue());
                                } else {
                                    s.setIdEstatusSolucion(EstatusSolucion_Enum.PRESCRIPCION_SOLICITADA.getValue());
                                }                                
                                    s.setIdEnvaseContenedor(surtimiento.getIdContenedor());
                                    s.setObservaciones(surtimiento.getObservaciones());
                                    s.setVolumenFinal((surtimiento.getVolumenTotal() != null) ? surtimiento.getVolumenTotal().toString() : "0");
                                    s.setVolumen((surtimiento.getVolumenTotal() != null) ? BigDecimal.valueOf(surtimiento.getVolumenTotal().doubleValue()) : BigDecimal.ZERO);
                                    s.setInstruccionesPreparacion(surtimiento.getInstruccionPreparacion());
                                    s.setProteccionLuz((surtimiento.isProteccionLuz()) ? 1 : 0);
                                    s.setProteccionTemp((surtimiento.isTempAmbiente()) ? 1 : 0);
                                    s.setProteccionTempRefrig((surtimiento.isTempRefrigeracion()) ? 1 : 0);
                                    s.setNoAgitar((surtimiento.isNoAgitar()) ? 1 : 0);
                                    s.setEdadPaciente(surtimiento.getEdadPaciente());
                                    s.setPesoPaciente(surtimiento.getPesoPaciente());
                                    s.setTallaPaciente(surtimiento.getTallaPaciente());
                                    s.setAreaCorporal(surtimiento.getAreaCorporal());
                                    s.setIdViaAdministracion(prescripcion.getIdViaAdministracion());
                                    s.setPerfusionContinua((surtimiento.isPerfusionContinua()) ? 1 : 0);
                                    s.setVelocidad(surtimiento.getVelocidad());
                                    s.setRitmo(surtimiento.getRitmo());
                                    s.setIdProtocolo(prescripcion.getIdProtocolo());
                                    s.setIdDiagnostico(prescripcion.getIdDiagnostico());
//todo: 28dic2022 obtener la unidad de concentracion 
                                    s.setUnidadConcentracion(surtimiento.getUnidadConcentracion());
                                    s.setInsertFecha(new java.util.Date());
                                    s.setInsertIdUsuario(surtimiento.getInsertIdUsuario());
                                    s.setMinutosInfusion(surtimiento.getMinutosInfusion());
                                    res = solucionMapper.insertar(s);

                                    if (!res) {
                                        throw new Exception("Error al registrar la orden de solucion. ");
                                    }
                                }
                            }
                        }

                    }
                }
            }
        } catch (Exception ex) {
            throw new Exception("Error en registrarOrdenSolucion. " + ex.getMessage());
        }
        return res;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean modificarOrdenSolucion(
            Prescripcion prescripcion ,
             List<PrescripcionInsumo> piListInsertar ,
            List<PrescripcionInsumo> piListActualizar ,
            List<PrescripcionInsumo> piListEliminar ,
            Surtimiento_Extend surtimiento ,
             List<SurtimientoInsumo> siListInsertar ,
            List<SurtimientoInsumo> siListActualizar ,
            List<SurtimientoInsumo> siListEliminar ,
            List<DiagnosticoPaciente> listaDiagnosticoPaciente ,
            Solucion s) throws Exception {
        boolean res = false;
        
        try {
            
            res = prescripcionMapper.actualizar(prescripcion);
            if (!res) {
                throw new Exception("Error al actualizar la Prescripción. ");

            } else {

                res = surtimientoMapper.actualizar(surtimiento);
                if (!res) {
                    throw new Exception("Error al actualizar Surtimientos de Prescripción. ");

                } else {
                    
                    if (piListEliminar != null && !piListEliminar.isEmpty()) {
                        for (PrescripcionInsumo pie : piListEliminar) {
                            
                            Surtimiento suTmp = new Surtimiento();
                            suTmp.setIdPrescripcion(pie.getIdPrescripcion());
                            List<Surtimiento> listSurt = surtimientoMapper.obtenerLista(suTmp);
                            if (listSurt != null && !listSurt.isEmpty()) {
                                for (Surtimiento sur : listSurt){
                                    SurtimientoInsumo siTmp = new SurtimientoInsumo();
                                    siTmp.setIdSurtimientoInsumo(sur.getIdSurtimiento());
                                    
                                    List<SurtimientoInsumo> listSurIns;
                                    listSurIns = surtimientoInsumoMapper.obtenerListSurtimientosInsumosByIdSurtimiento(sur.getIdSurtimiento());
                                    if (listSurIns != null && !listSurIns.isEmpty()) {
                                        for (SurtimientoInsumo surIns : listSurIns) {
                                            if (pie.getIdPrescripcionInsumo().equals(surIns.getIdPrescripcionInsumo())) {
                                                surtimientoEnviadoMapper.eliminarPorIdSurtimientoInsumo(surIns.getIdSurtimientoInsumo());
                                                surtimientoInsumoMapper.eliminar(surIns);
                                            }
                                        }
                                    }
                                }
                            }
                       }
                    }
                    
                    if (piListEliminar != null && !piListEliminar.isEmpty()) {
                        for (PrescripcionInsumo pie : piListEliminar) {
                            res = prescripcionInsumoMapper.eliminar(pie);
                            if (!res) {
                                throw new Exception("Error al eliminar Prescripción insumo. ");
                            }
                        }
                    }
                    
                    Date fechaInicio = prescripcion.getFechaPrescripcion();
                    if (piListActualizar != null && !piListActualizar.isEmpty()) {
                        res = prescripcionInsumoMapper.actualizarListaPrescripcionInsumo(piListActualizar);
                        if (!res) {
                            throw new Exception("Error al actualizar Prescripción insumo. ");
                        }
                        fechaInicio = piListActualizar.get(0).getFechaInicio();
                    }
                    
                    
                    if (siListActualizar != null && !siListActualizar.isEmpty()) {
                        surtimientoInsumoMapper.actualizarSurtimientoInsumoList(siListActualizar);
                        if (!res) {
                            throw new Exception("Error al actualizar Prescripción insumo. ");
                        }
                    }

                    if (piListInsertar != null && !piListInsertar.isEmpty()) {

                        for (PrescripcionInsumo item : piListInsertar) {
                            item.setFechaInicio(fechaInicio);
                        }
                        prescripcionInsumoMapper.registraMedicamentoList(piListInsertar);

                        if (!res) {
                            throw new Exception("Error al guardar Prescripción insumo. ");
                        } else {
                            if (siListInsertar != null && !siListInsertar.isEmpty()) {
                                Surtimiento suTmp = new Surtimiento();
                                suTmp.setIdPrescripcion(prescripcion.getIdPrescripcion());
                                List<Surtimiento> listSurt = surtimientoMapper.obtenerLista(suTmp);

                                if (listSurt != null && !listSurt.isEmpty()) {
                                    List<SurtimientoInsumo> siLisNuevos = new ArrayList<>();
                                    
                                    for (SurtimientoInsumo sii : siListInsertar) {
                                        for (Surtimiento sur : listSurt) {
                                            
                                            SurtimientoInsumo siTmp = new SurtimientoInsumo();
                                            if (sii.getIdSurtimiento().equals(sur.getIdSurtimiento())) {
                                                siTmp.setIdSurtimientoInsumo(sii.getIdSurtimientoInsumo());
                                            } else {
                                                siTmp.setIdSurtimientoInsumo(Comunes.getUUID());
                                            }
                                            siTmp.setIdSurtimiento(sur.getIdSurtimiento());
                                            siTmp.setIdPrescripcionInsumo(sii.getIdPrescripcionInsumo());
                                            siTmp.setFechaProgramada(sur.getFechaProgramada());
                                            siTmp.setCantidadSolicitada(sii.getCantidadSolicitada());
                                            siTmp.setCantidadEnviada(sii.getCantidadEnviada());
                                            siTmp.setIdEstatusSurtimiento(sii.getIdEstatusSurtimiento());
                                            siTmp.setIdEstatusMirth(sii.getIdEstatusMirth());
                                            siTmp.setInsertFecha(sii.getInsertFecha());
                                            siTmp.setInsertIdUsuario(sii.getInsertIdUsuario());
                                            siLisNuevos.add(siTmp);
                                        }
                                    }
                                    if (siLisNuevos != null && !siLisNuevos.isEmpty()) {
                                        surtimientoInsumoMapper.registraSurtimientoInsumoList(siLisNuevos);
                                    }
//                                surtimientoInsumoMapper.registraSurtimientoInsumoList(siListInsertar);
//                                if (!res) {
//                                    throw new Exception("Error al guardar Prescripción insumo. ");
//                                }
                                }
                            }
                        }
                    }
                    if (surtimiento!= null) {
                        String descripcionMezcla = solucionMapper.obtenerDescripcionMezcla(surtimiento.getIdSurtimiento());
                        s.setDescripcion(descripcionMezcla);
                    }
                    
                    res = solucionMapper.actualizar(s);
                    if (!res) {
                        throw new Exception("Error al actualizar la solucion. ");

                    } else {
                        if (!listaDiagnosticoPaciente.isEmpty()) {
                            for (DiagnosticoPaciente dp : listaDiagnosticoPaciente) {
                                if (diagnosticoPacienteMapper.obtenerPorPrescripcionDiagnostico(dp.getIdPrescripcion(), dp.getIdDiagnostico()) == null) {
                                    res = diagnosticoPacienteMapper.insertar(dp);
                                    if (!res) {
                                        throw new Exception("Error al registrar los diagnosticos de la prescripcion de soluciones. ");
                                        
                                    }
                                }
                            }
                        }
                    }
                    
                }
            }
        } catch (Exception ex) {
            throw new Exception("Error en modificarOrdenSolucion. " + ex.getMessage());
        }
        return res;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean autorizarOrdenSolucion(Prescripcion prescripcion, List<PrescripcionInsumo> piListInsertar, List<PrescripcionInsumo> piListActualizar, Surtimiento_Extend surtimiento, List<SurtimientoInsumo> siListInsertar, List<SurtimientoInsumo> siListActualizar, Solucion s, boolean agrupaMezclaPrescAutorizar) throws Exception {
        boolean res = false;
        try {
            res = prescripcionMapper.actualizar(prescripcion);
             if (!res) {
                throw new Exception("Error al registrar Prescripción. ");
            } else {
                if (piListInsertar != null) {
                    if (!piListInsertar.isEmpty()) {
                        prescripcionInsumoMapper.registraMedicamentoList(piListInsertar);
                        if (!res) {
                            throw new Exception("Error al registrar Prescripción insumo. ");
                        }
                    }
                }
                if (piListActualizar != null) {
                    if (!piListActualizar.isEmpty()) {
                        prescripcionInsumoMapper.actualizarListaPrescripcionInsumo(piListActualizar);
                        if (!res) {
                            throw new Exception("Error al actualizar Prescripción insumo. ");
                        }
                    }
                }
                if(agrupaMezclaPrescAutorizar) {
                    List<SurtimientoInsumo> listaNewSurtInsumo = new ArrayList<>();
                    List<Surtimiento> listaSurtimientos = surtimientoMapper.obtenerSurtimientosByIdPrescripcion(prescripcion.getIdPrescripcion());                    
                    for(Surtimiento surt : listaSurtimientos) {
                        surt.setUpdateFecha(surtimiento.getUpdateFecha());
                        surt.setUpdateIdUsuario(surtimiento.getUpdateIdUsuario());
                        surt.setIdEstatusSurtimiento(surtimiento.getIdEstatusSurtimiento());
                        if (siListInsertar != null) {
                            if (!siListInsertar.isEmpty()) {
                                for(SurtimientoInsumo surtInsumo : siListInsertar) {
                                    SurtimientoInsumo newSurtInsumo = new SurtimientoInsumo();
                                    newSurtInsumo = surtInsumo;
                                    newSurtInsumo.setIdSurtimiento(surt.getIdSurtimiento());
                                    listaNewSurtInsumo.add(newSurtInsumo);
                                }
                            }
                        }    
                    }                    
                    List<SurtimientoInsumo> listaSurtimientoInsumosAll = new ArrayList<>();
                    if (siListActualizar != null) {
                        if (!siListActualizar.isEmpty()) {
                            listaSurtimientoInsumosAll = surtimientoInsumoMapper.obtenerSurtimientosInsumos(prescripcion.getIdPrescripcion());  
                            for(SurtimientoInsumo surtInsu : listaSurtimientoInsumosAll) {
                                for(SurtimientoInsumo surInsumAct : siListActualizar) {
                                    if(surtInsu.getIdPrescripcionInsumo().equals(surInsumAct.getIdPrescripcionInsumo())) {
                                        surtInsu.setUpdateFecha(surInsumAct.getUpdateFecha());
                                        surtInsu.setUpdateIdUsuario(surInsumAct.getUpdateIdUsuario());
                                        surtInsu.setIdEstatusSurtimiento(surInsumAct.getIdEstatusSurtimiento());
                                    }
                                }
                            }
                        }
                    }        
                    if(!listaNewSurtInsumo.isEmpty()) {
                        res = surtimientoInsumoMapper.registraSurtimientoInsumoList(listaNewSurtInsumo);
                        if (!res) {
                            throw new Exception("Error al registrar Prescripción insumo. ");
                        }
                    }
                    if(!listaSurtimientoInsumosAll.isEmpty()) {
                        res = surtimientoInsumoMapper.actualizarSurtimientoInsumoList(listaSurtimientoInsumosAll);
                        if (!res) {
                            throw new Exception("Error al actualizar surtimientos insumos. ");
                        }
                        
                    }
                    List<Solucion> listaSoluciones = solucionMapper.obtenerSolucionesByIdPrescripcion(prescripcion.getIdPrescripcion());
                    for(Solucion sol : listaSoluciones) {
                        sol.setIdUsuarioAutoriza(s.getIdUsuarioAutoriza());
                        sol.setFechaAutoriza(s.getFechaAutoriza());
                        sol.setComentarioAutoriza(s.getComentarioAutoriza() != null ? s.getComentarioAutoriza() : "");
                        sol.setIdEstatusSolucion(s.getIdEstatusSolucion());
                        sol.setUpdateFecha(s.getUpdateFecha());
                        sol.setUpdateIdUsuario(s.getUpdateIdUsuario());
                        
                    }
                    if(listaSoluciones != null) {
                        if(!listaSoluciones.isEmpty()) {
                            res = solucionMapper.actualizarListaSoluciones(listaSoluciones);
                            if (!res) {
                                throw new Exception("Error al actualizar las soluciones por Prescripción. ");
                            }
                        }                        
                    }
                } else {
                    res = surtimientoMapper.actualizar(surtimiento);
                     if (!res) {
                        throw new Exception("Error al registrar Surtimiento de Prescripción. ");
                    } else {
                        if (siListInsertar != null) {
                            if (!siListInsertar.isEmpty()) {
                                res = surtimientoInsumoMapper.registraSurtimientoInsumoList(siListInsertar);
                                if (!res) {
                                    throw new Exception("Error al registrar surtimientos insumos. ");
                                }
                            }
                        }
                        if (siListActualizar != null) {
                            if (!siListActualizar.isEmpty()) {
                                res = surtimientoInsumoMapper.actualizarSurtimientoInsumoList(siListActualizar);
                                if (!res) {
                                    throw new Exception("Error al actualizr surtimientos insumos.  ");
                                }
                            }
                        }
                        res = solucionMapper.actualizar(s);

                        if (!res) {
                            throw new Exception("Error al actualizar la orden de solucion. ");
                        } 
                    }
                }       
            }    
        } catch (Exception ex) {
            throw new Exception("Error en autorizarOrdenSolucion. " + ex.getMessage());
        }
        return res;
    }
    
    private Date sumarHoras(Date fecha, Integer numHoras) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        if (fecha != null && numHoras > 0) {
            cal.add(Calendar.HOUR, numHoras);
        }
        return cal.getTime();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean registrarOrdenesSoluciones(List<Prescripcion> listaPrescripciones, List<PrescripcionInsumo> insumoList,
            List<Surtimiento> listaSurtimientosExt, List<SurtimientoInsumo_Extend> surtimientoInsumoList,
            List<SolucionExtended> listaSolucionesExt, List<DiagnosticoPaciente> listaDiagnosticoPaciente) throws Exception {
        boolean res = false;
        try {
            for (Prescripcion prescripcion : listaPrescripciones) {
                int tipoDoc = TipoDocumento_Enum.PRESCRIPCION.getValue();
                Folios folio = foliosMapper.obtenerPrefixPorDocument(tipoDoc);
                String folioNuevo = Comunes.generaFolio(folio);
                folio.setSecuencia(Comunes.separaFolio(folioNuevo));
                res = foliosMapper.actualizaFolios(folio);
                if (!res) {
                    throw new Exception("Error al registrar Folio de Surtimiento. ");
                } else {
                    prescripcion.setFolio(folioNuevo);
                }
            }
            res = prescripcionMapper.insertarLista(listaPrescripciones);

            if (!res) {
                throw new Exception("Error al registrar Prescripciones. ");
            } else {
                res = prescripcionInsumoMapper.registraMedicamentoList(insumoList);
                if (!res) {
                    throw new Exception("Error al registrar Medicamentos de Prescripción. ");
                } else {
                    for (Surtimiento surtimiento : listaSurtimientosExt) {
                        int tipoDoc = TipoDocumento_Enum.ORDEN_MANUAL_SOLUCIONES.getValue();
                        Folios folio = foliosMapper.obtenerPrefixPorDocument(tipoDoc);
                        String folioSurtimiento = Comunes.generaFolio(folio);
                        folio.setSecuencia(Comunes.separaFolio(folioSurtimiento));
                        res = foliosMapper.actualizaFolios(folio);
                        if (!res) {
                            throw new Exception("Error al registrar Folio de Surtimiento. ");
                        } else {
                            surtimiento.setFolio(folioSurtimiento);
                        }
                    }
                    res = surtimientoMapper.insertarLista(listaSurtimientosExt);
                    if (!res) {
                        throw new Exception("Error al registrar Surtimientos de Prescripción. ");
                    } else {
                        res = surtimientoInsumoMapper.registraSurtimientoInsumoExtendedList(surtimientoInsumoList);
                        if (!res) {
                            throw new Exception("Error al registrar la lista de Surtimiento Insumo. ");
                        } else {
                            List<Solucion> ListaSoluciones = new ArrayList<>();
                            ListaSoluciones.addAll(listaSolucionesExt);
                            res = solucionMapper.insertarLista(ListaSoluciones);
                            if (!res) {
                                throw new Exception("Error al registrar la orden de solucion. ");
                            } else {
                                if (!listaDiagnosticoPaciente.isEmpty()) {
                                    for (DiagnosticoPaciente dp : listaDiagnosticoPaciente) {
                                        if (diagnosticoPacienteMapper.obtenerPorPrescripcionDiagnostico(dp.getIdPrescripcion(), dp.getIdDiagnostico()) == null) {
                                            res = diagnosticoPacienteMapper.insertar(dp);
                                            if (!res) {
                                                throw new Exception("Error al registrar los diagnosticos de la prescripcion de soluciones. ");
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
            throw new Exception("Error en registrarOrdenSolucion. " + ex.getMessage());
        }
        return res;
    }
}
