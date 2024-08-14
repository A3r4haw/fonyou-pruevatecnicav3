package mx.mc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import mx.mc.enums.EstatusPrescripcion_Enum;
import mx.mc.enums.EstatusSurtimiento_Enum;
import mx.mc.enums.TipoDocumento_Enum;
import mx.mc.init.Constantes;
import mx.mc.mapper.DevolucionMinistracionMapper;
import mx.mc.mapper.FoliosMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.InventarioMapper;
import mx.mc.mapper.InventarioSalidaMapper;
import mx.mc.mapper.MovimientoInventarioMapper;
import mx.mc.mapper.PrescripcionInsumoMapper;
import mx.mc.mapper.PrescripcionMapper;
import mx.mc.mapper.ReabastoEnviadoMapper;
import mx.mc.mapper.ReabastoInsumoMapper;
import mx.mc.mapper.ReabastoMapper;
import mx.mc.mapper.SurtimientoEnviadoMapper;
import mx.mc.mapper.SurtimientoInsumoMapper;
import mx.mc.mapper.SurtimientoMapper;
import mx.mc.mapper.UsuarioMapper;
import mx.mc.model.DevolucionDetalleExtended;
import mx.mc.model.DevolucionMinistracion;
import mx.mc.model.Folios;
import mx.mc.model.Inventario;
import mx.mc.model.InventarioExtended;
import mx.mc.model.InventarioSalida;
import mx.mc.model.MovimientoInventario;
import mx.mc.model.Prescripcion;
import mx.mc.model.PrescripcionInsumo;
import mx.mc.model.Reabasto;
import mx.mc.model.ReabastoEnviado;
import mx.mc.model.ReabastoEnviadoExtended;
import mx.mc.model.ReabastoInsumo;
import mx.mc.model.Surtimiento;
import mx.mc.model.SurtimientoEnviado;
import mx.mc.model.SurtimientoInsumo;
import mx.mc.model.TipoBloqueo;
import mx.mc.model.Usuario;
import mx.mc.util.Comunes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import mx.mc.mapper.TipoBloqueoMapper;
import mx.mc.model.ClaveProveedorBarras;

/**
 *
 * @author bbautista
 */
@Service
public class InventarioServiceImpl extends GenericCrudServiceImpl<Inventario, String> implements InventarioService {
    
    @Autowired
    private InventarioMapper inventarioMapper;

    @Autowired
    private MovimientoInventarioMapper movimientoInventarioMapper;

    @Autowired
    private SurtimientoEnviadoMapper surtimientoEnviadoMapper;

    @Autowired
    private SurtimientoMapper surtimientoMapper;

    @Autowired
    private SurtimientoInsumoMapper surtimientoInsumoMapper;

    @Autowired
    private PrescripcionMapper prescripcionMapper;

    @Autowired
    private PrescripcionInsumoMapper prescripcionInsumoMapper;

    @Autowired
    private InventarioSalidaMapper inventarioSalidaMapper;

    @Autowired
    private DevolucionMinistracionMapper devolucionMinistracionMapper;

    @Autowired
    private FoliosMapper foliosMapper;
    
    @Autowired
    private TipoBloqueoMapper tipoBloqueoMapper;
    
    @Autowired
    private UsuarioMapper usuarioMapper;
    
    @Autowired
    private ReabastoMapper reabastoMapper;
    
    @Autowired
    private ReabastoInsumoMapper reabastoInsumoMapper;
    
    @Autowired
    private ReabastoEnviadoMapper reabastoEnviadoMapper;

    @Autowired
    public InventarioServiceImpl(GenericCrudMapper<Inventario, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
    

    @Override
    public int actualizarInventario(
            List<ReabastoEnviadoExtended> listaDetalle) throws Exception {
        int resp = 0;
        try {
            resp = inventarioMapper.actualizarInventario(listaDetalle);
            return resp;
        } catch (Exception ex) {
            throw new Exception("Error actualizarInventario. " + ex.getMessage());
        }
    }

    @Override
    public List<Inventario> obtenerLotesInventario(String cadenaBusqueda) throws Exception {
        try {
            return inventarioMapper.obtenerLotesInventario(cadenaBusqueda);
        } catch (Exception ex) {
            throw new Exception("Error al obtener Lotes " + ex.getMessage());
        }
    }

    @Override
    public Inventario obtenerInventariosPorInsumoEstructuraYLote(
            String idInsumo, String idEstructura, String lote) throws Exception {
        Inventario inventario = new Inventario();
        try {
            inventario = inventarioMapper.obtenerInventariosPorInsumoEstructuraYLote(idInsumo, idEstructura, lote);
            return inventario;
        } catch (Exception ex) {
            throw new Exception("Error obtenerInventariosPorInsumoEstructuraYLote. " + ex.getMessage());
        }
    }

    @Override
    public String obtenerIdiventarioPorInsumoEstructuraYLote(String idInsumo,
            String idEstructura, String lote) throws Exception {
        try {
            return inventarioMapper.
                    obtenerIdiventarioPorInsumoEstructuraYLote(idInsumo, idEstructura, lote);
        } catch (Exception ex) {
            throw new Exception("Error obtenerIdiventarioPorInsumoEstructuraYLote. " + ex.getMessage());
        }
    }

    @Override
    public Inventario obtenerIdiventarioPorInsumoEstructura(String idInsumo,
            String idEstructura) throws Exception {
        try {
            return inventarioMapper.obtenerIdiventarioPorInsumoEstructura(idInsumo, idEstructura);
        } catch (Exception ex) {
            throw new Exception("Error obtenerIdiventarioPorInsumoEstructura. " + ex.getMessage());
        }
    }

    @Override
    public Inventario obtenerIdiventarioPorInsumo(String idInsumo) throws Exception {
        try {
            return inventarioMapper.
                    obtenerIdiventarioPorInsumo(idInsumo);
        } catch (Exception ex) {
            throw new Exception("Error obtenerIdiventarioPorInsumo. " + ex.getMessage());
        }
    }

    @Override
    public Inventario obtenerExistencia(String idInsumo, String idEstructura) throws Exception {
        try {
            return inventarioMapper.obtenerExistencia(idInsumo, idEstructura);
        } catch (Exception ex) {
            throw new Exception("Error al obtener Insumo Disponible " + ex);
        }
    }

    @Override
    public List<Inventario> obtenerLotesPorMedicamento(String idInsumo) throws Exception {
        try {
            return inventarioMapper.obtenerLotesPorMedicamento(idInsumo);
        } catch (Exception ex) {
            throw new Exception("Error al obtener Insumo Disponible " + ex);
        }
    }

    @Override
    public boolean insertListInventarios(List<Inventario> listaInventarios) throws Exception {
        try {
            return inventarioMapper.insertListInventarios(listaInventarios);
        } catch (Exception e) {
            throw new Exception("Error al insertar la lista de inventarios. " + e.getMessage());
        }
    }

    @Override
    public boolean actualizarListInventarios(List<Inventario> listaInventarios) throws Exception {
        boolean resp = false;
        try {
            resp = inventarioMapper.actualizarListInventarios(listaInventarios);
            return resp;
        } catch (Exception ex) {
            throw new Exception("Error actualizar lista de Inventarios. " + ex.getMessage());
        }
    }

    @Override
    public List<InventarioExtended> obtenerListaInactivosByIdInsumos(List<String> listIdInsumos) throws Exception {
        try {
            return inventarioMapper.obtenerListaInactivosByIdInsumos(listIdInsumos);
        } catch (Exception ex) {
            throw new Exception("Error al obtener lista de Inventarios Inactivos. " + ex.getMessage());
        }
    }

    @Override
    public DevolucionDetalleExtended getInventarioForDevolucion(ReabastoEnviadoExtended reabastoEnviado) throws Exception {
        try {
            return inventarioMapper.getInventarioForDevolucion(reabastoEnviado);
        } catch (Exception ex) {
            throw new Exception("Error al obtener el inventario a devolver!!   " + ex.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean revertirInventario(Surtimiento surtimientoSelected,
            List<SurtimientoEnviado> surtimientoEnviadoList,
            Usuario currentUser) throws Exception {
        List<Inventario> listaInventario = new ArrayList<>();
        List<MovimientoInventario> listaMovInventario = new ArrayList<>();
        boolean result = Constantes.INACTIVO;
        try {
            //recorrer La lista para obtener el inventario a actualizar
            for (SurtimientoEnviado item : surtimientoEnviadoList) {
                Inventario invent = new Inventario();
                invent.setIdInventario(item.getIdInventarioSurtido());
                invent.setCantidadActual(item.getCantidadEnviado());
                MovimientoInventario movi = new MovimientoInventario();
                movi.setIdMovimientoInventario(Comunes.getUUID());
                movi.setIdTipoMotivo(23);
                movi.setFecha(new Date());
                movi.setIdUsuarioMovimiento(currentUser.getIdUsuario());
                movi.setIdEstrutcuraOrigen(currentUser.getIdEstructura());
                movi.setIdEstrutcuraDestino(surtimientoSelected.getIdEstructuraAlmacen());
                movi.setIdInventario(item.getIdInventarioSurtido());
                movi.setCantidad(item.getCantidadEnviado());
                movi.setFolioDocumento(surtimientoSelected.getFolio());

                listaInventario.add(invent);
                listaMovInventario.add(movi);
            }
            //Revertir Inventario  
            result = inventarioMapper.revertirInventarioListCancelacion(listaInventario);
            //Registrar Movimiento Inventario
            if (result) {
                result = movimientoInventarioMapper.insertarMovimientosInventarios(listaMovInventario);
                if (result) {
                    switch (surtimientoSelected.getProcesado()) {
                        case 0:
                            result = surtimientoEnviadoMapper.revertirSurtimientoEnviado0(surtimientoEnviadoList);
                            break;
                        case 1:
                        case 2:
                            result = surtimientoEnviadoMapper.revertirSurtimientoEnviado2(surtimientoEnviadoList, EstatusSurtimiento_Enum.CANCELADO.getValue(), currentUser.getIdUsuario());
                            break;
                        default:
                    }

                    if (result) {
                        SurtimientoInsumo insumoS = new SurtimientoInsumo();
                        insumoS.setFechaCancela(new Date());
                        insumoS.setIdUsuarioCancela(currentUser.getIdUsuario());
                        insumoS.setUpdateFecha(new Date());
                        insumoS.setUpdateIdUsuario(currentUser.getIdUsuario());
                        insumoS.setIdSurtimiento(surtimientoSelected.getIdSurtimiento());

                        switch (surtimientoSelected.getProcesado()) {
                            case 0:
                                insumoS.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                                result = surtimientoInsumoMapper.revertirSusrtimiento0(insumoS);
                                break;
                            case 1:
                            case 2:
                                insumoS.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.CANCELADO.getValue());
                                result = surtimientoInsumoMapper.revertirSusrtimiento2(insumoS);
                                break;
                            default:
                        }

                        if (result) {
                            surtimientoSelected.setUpdateFecha(new Date());
                            surtimientoSelected.setUpdateIdUsuario(currentUser.getIdUsuario());
                            if (surtimientoSelected.getProcesado() == 0) {
                                surtimientoSelected.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                            } else {
                                surtimientoSelected.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.CANCELADO.getValue());
                            }
                            result = surtimientoMapper.actualizar(surtimientoSelected);
                            if (result) {
                                PrescripcionInsumo insumoP = new PrescripcionInsumo();
                                insumoP.setUpdateFecha(new Date());
                                insumoP.setUpdateIdUsuario(currentUser.getIdUsuario());
                                insumoP.setIdEstatusPrescripcion(EstatusPrescripcion_Enum.PROGRAMADA.getValue());
                                insumoP.setIdPrescripcion(surtimientoSelected.getIdPrescripcion());
                                result = prescripcionInsumoMapper.cambiarEstatusPrescripcion(insumoP);
                                if (result) {
                                    Prescripcion pres = new Prescripcion();
                                    pres.setUpdateFecha(new Date());
                                    pres.setUpdateIdUsuario(currentUser.getIdUsuario());
                                    pres.setIdEstatusPrescripcion(EstatusPrescripcion_Enum.PROGRAMADA.getValue());
                                    pres.setIdPrescripcion(surtimientoSelected.getIdPrescripcion());
                                    result = prescripcionMapper.cambiarEstatusPrescripcion(pres);
                                    if (!result) {
                                        throw new Exception("error al cambiarEstatusPrescripcion");
                                    }
                                } else {
                                    throw new Exception("Error al cambiarEstatusPrescripcion");
                                }
                            } else {
                                throw new Exception("No se pudo Actualizar estatus Surtimiento");
                            }
                        } else {
                            throw new Exception("No se pudo Revertir SurtimientoInsumo");
                        }
                    } else {
                        throw new Exception("No se pudo Revertir SurtimientoEnviado");
                    }
                } else {
                    throw new Exception("No se pudo Revertir el Inventatrio");
                }
            } else {
                throw new Exception("No se pudo Revertir el Inventatrio");
            }

        } catch (Exception ex) {
            throw new Exception("Ocurrio una Excecion al Revertir el Inventario::" + ex.getMessage());
        }
        return true;
    }

    @Override
    public Inventario obtenerInventariosPorInsumoEstructuraLoteYCantidadXCaja(
            String idInsumo, String idEstructura, String lote, Integer cantidadXCaja, String claveProveedor) throws Exception {
        try {
            return inventarioMapper.obtenerInventariosPorInsumoEstructuraLoteYCantidadXCaja(idInsumo, idEstructura, lote, cantidadXCaja, claveProveedor);
        } catch (Exception ex) {
            throw new Exception("Error obtenerInventariosPorInsumoEstructuraLoteYCantidadXCaja. " + ex.getMessage());
        }
    }

    @Override
    public Inventario obtenerInventariosPorInsumoEstructuraLoteYCantidadXCajaYFechaCad(
            String idInsumo, String idEstructura, String lote,
            Integer cantidadXCaja, String claveProveedor, Date fechaCaducidad) throws Exception {
        try {
            return inventarioMapper.obtenerInventariosPorInsumoEstructuraLoteYCantidadXCajaYFechaCad(
                    idInsumo, idEstructura, lote, cantidadXCaja, claveProveedor, fechaCaducidad);
        } catch (Exception ex) {
            throw new Exception("Error obtenerInventariosPorInsumoEstructuraLoteYCantidadXCaja. " + ex.getMessage());
        }
    }

    @Override
    public List<Inventario> obtenerInventariosPorInsumoEstructuraCantidadXCajaYProveedor(
            String idInsumo, String idEstructura, Integer cantidadXCaja, String claveProveedor, String lote) throws Exception {
        
        try {
           return inventarioMapper.obtenerInventariosPorInsumoEstructuraCantidadXCajaYProveedor(idInsumo, idEstructura, cantidadXCaja, claveProveedor, lote);
            
        } catch (Exception ex) {
            throw new Exception("Error obtenerInventariosPorInsumoEstructuraCantidadXCajaYProveedor. " + ex.getMessage());
        }
    }

    @Override
    public Inventario obtenerInventarioPorClveInstEstructuraYLote(Inventario inventario) throws Exception {
        try {
            return inventarioMapper.obtenerInventarioPorClveInstEstructuraYLote(inventario);
        } catch (Exception ex) {
            throw new Exception("Error obtenerInventarioPorClveInstEstructuraYLote. " + ex.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean insertarManual(Inventario inventarioSelect) throws Exception {
        boolean res = false;
        try {
            res = inventarioMapper.insertarManual(inventarioSelect);
        } catch (Exception ex) {
            throw new Exception("Error obtenerInventarioPorClveInstEstructuraYLote. " + ex.getMessage());
        }
        return res;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean recivirMedicamentosDevolucion(
            List<Inventario> listaInventario,
            List<InventarioSalida> listaInvSalida,
            Surtimiento surtimiento,
            List<MovimientoInventario> listaMovimientosInventario,
            DevolucionMinistracion devMinistracion) throws Exception {

        boolean resp = false;
        try {
            if (!listaInventario.isEmpty()) {
                resp = inventarioMapper.revertirInventarioList(listaInventario);
                if (!resp) {
                    throw new Exception("No se pudo recivir el medicamento");
                }
            }
            if(surtimiento != null){
                Folios folioSurtimiento = foliosMapper.obtenerPrefixPorDocument(TipoDocumento_Enum.SURTIMIENTO_DE_PRESCRIPCION.getValue());
                surtimiento.setFolio(Comunes.generaFolio(folioSurtimiento));
                folioSurtimiento.setSecuencia(Comunes.separaFolio(surtimiento.getFolio()));
                resp = foliosMapper.actualizaFolios(folioSurtimiento);
                if (!resp) {
                    throw new Exception("Error al registrar Folio de Surtimiento. ");
                }
                resp = surtimientoMapper.insertar(surtimiento);
                if (!resp) {
                    throw new Exception("No se pudo insertar el medicamento");
                }
                if(!surtimiento.getDetalle().isEmpty()){
                    resp = surtimientoInsumoMapper.resurteSurtimientoInsumoList(surtimiento.getDetalle());
                    if (!resp) {
                        throw new Exception("No se pudo resurteSurtimientoInsumoList");
                    }
                }
            }
            if (!listaInvSalida.isEmpty()) {
                resp = inventarioSalidaMapper.insertarLista(listaInvSalida);
                if (!resp) {
                    throw new Exception("No se pudo insertarLista");
                }
            }
           
            resp = movimientoInventarioMapper.insertarMovimientosInventarios(listaMovimientosInventario);
            if (!resp) {
                throw new Exception("No se pudo insertarMovimientosInventarios");
            }
            resp = devolucionMinistracionMapper.actualizar(devMinistracion);
            if (!resp) {
                throw new Exception("No se pudo actualizar el medicamento");
            }

        } catch (Exception e) {
            throw new Exception("Error recivirMedicamentosDevolucion. " + e.getMessage());
        }
        return resp;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean actualizarInventarioGlobal(
            Inventario inventario, MovimientoInventario movInventario) throws Exception {
        boolean res = false;
        try {
            res = inventarioMapper.actualizarInventarioGlobal(inventario);
            if (!res) {
                throw new Exception("Error al actualizar el inventario. ");
            } else {
                int tipoDoc = TipoDocumento_Enum.AJUSTES.getValue();
                Folios folio = foliosMapper.obtenerPrefixPorDocument(tipoDoc);
                movInventario.setFolioDocumento(Comunes.generaFolio(folio));
                folio.setSecuencia(Comunes.separaFolio(movInventario.getFolioDocumento()));
                res = foliosMapper.actualizaFolios(folio);
                if (!res) {
                    throw new Exception("Error al registrar Folio de Surtimiento. ");
                }
                movimientoInventarioMapper.insertar(movInventario);
            }
        } catch (Exception ex) {
            throw new Exception("Error actualizarInventarioGlobal. " + ex.getMessage());
        }
        return res;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean actualizarEntradaInventario(List<InventarioExtended> listInventarioActualizar,
            List<MovimientoInventario> listaMovimientos) throws Exception {
        boolean res = false;
        try {
            int tipoDoc = TipoDocumento_Enum.AJUSTES.getValue();
            Folios folio = foliosMapper.obtenerPrefixPorDocument(tipoDoc);
            for (MovimientoInventario item : listaMovimientos) {
                item.setFolioDocumento(Comunes.generaFolio(folio));
            }
            folio.setSecuencia(Comunes.separaFolio(listaMovimientos.get(0).getFolioDocumento()));
            res = foliosMapper.actualizaFolios(folio);
            if (!res) {
                throw new Exception("Error al registrar Folio de Movimiento Inventario. ");
            } else {
                res = inventarioMapper.actualizarAjusteInventario(listInventarioActualizar);
                if (!res) {
                    throw new Exception("Error al actualizar el Inventario");
                } else {
                    res = movimientoInventarioMapper.insertarMovimientosInventarios(listaMovimientos);
                    if (!res) {
                        throw new Exception("Error al Ingresar los Movimientos de Inventario");
                    }
                }
            }

        } catch (Exception e) {
            throw new Exception("Error al actualizar los Datos " + e.getMessage());
        }
        return res;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean insertaNuevoInventario(Inventario inventarioSelect, MovimientoInventario movimientoInventario) throws Exception {
        boolean res = false;
        try {
            int tipoDoc = TipoDocumento_Enum.AJUSTES.getValue();
            Folios folio = foliosMapper.obtenerPrefixPorDocument(tipoDoc);
            movimientoInventario.setFolioDocumento(Comunes.generaFolio(folio));
            folio.setSecuencia(Comunes.separaFolio(movimientoInventario.getFolioDocumento()));
            res = foliosMapper.actualizaFolios(folio);
            if (!res) {
                throw new Exception("Error al registrar Folio de Movimiento Inventario. ");
            } else {
                res = inventarioMapper.insertarNuevoInventario(inventarioSelect);
                if (!res) {
                    throw new Exception("Error al registrar el Inventario ");
                } else {
                    res = movimientoInventarioMapper.insertar(movimientoInventario);
                    if (!res) {
                        throw new Exception("Error al insertar el movimientoInventario ");
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("Error al Ingresar el nuevo Inventario" + e.getMessage());
        }
        return res;
    }

    @Override
    public Inventario obtenerInventariosPorInsumoLoteFechaCadIdInsumoClProv(String lote, String idInsumo, String idEstructura, Date fechaCaducidad) throws Exception {
        try {
            return inventarioMapper.obtenerInventariosPorInsumoLoteFechaCadIdInsumoClProv(lote, idInsumo, idEstructura, fechaCaducidad);
        } catch (Exception e) {
            throw new Exception("Error al Obtener el Inventario Solicitado" + e.getMessage());
        }
    }

    @Override
    public boolean actualizarInventarioPresentCom(String idInventario, int presentacionComercial, int cantidadXCaja) throws Exception {
        boolean res = false;
        try {
            res = inventarioMapper.actualizarInventarioPresentCom(idInventario, presentacionComercial, cantidadXCaja);
            return res;
        } catch (Exception e) {
            throw new Exception("Error al actualizar la presentacion Comercial " + e.getMessage());
        }
    }

    @Override
    public Inventario validarExistInventGlob(String idInsumo, String idEstructura, String lote, Integer cantidadXCaja, String claveProveedor, int presentacionComercial) throws Exception {
        try {
            return inventarioMapper.validarExistInventGlob(idInsumo, idEstructura, lote, cantidadXCaja, claveProveedor, presentacionComercial);
        } catch (Exception e) {
            throw new Exception("Error al verificar Existencia " + e.getMessage());
        }
    }

    @Override
    public List<Inventario> obtenerExistenciasPorIdEstructuraIdInsumo(String idEstructura, String idInsumo) throws Exception {
        try {
            return inventarioMapper.obtenerExistenciasPorIdEstructuraIdInsumo(idEstructura, idInsumo);
        } catch (Exception ex) {
            throw new Exception("Error obtenerExistenciasPorIdEstructuraIdInsumo. " + ex.getMessage());
        }
    }

    @Override
    public List<InventarioExtended> obtenerListaporLotes(String claveInstitucional) throws Exception {
        try {
            return inventarioMapper.obtenerListaporLotes(claveInstitucional);
        } catch (Exception ex) {
            throw new Exception("Error al obtenerListaporLotes" + ex);
        }

    }

    @Override
    public List<Inventario> listaLotesPorClaveInst(String claveInstitucional , String estructura) throws Exception {
        try {
            return inventarioMapper.listaLotesPorClaveInst(claveInstitucional , estructura);
        } catch (Exception ex) {
            throw new Exception("Error al listaLotesPorClaveInst" + ex);
        }

    }
    
    @Override
    public List<Inventario> listaMedicamentoEstructura(String claveInstitucional) throws Exception {
        try {
            return inventarioMapper.listaMedicamentoEstructura(claveInstitucional);
        } catch (Exception ex) {
            throw new Exception("Error al listaMedicamentoEstructura" + ex);
        }

    }
        
    @Override
    public List<Inventario> listaFechasCaducidad1(String claveInstitucional, String lote, String estructura) throws Exception {
        try {
            return inventarioMapper.listaFechasCaducidad1(claveInstitucional, lote, estructura);
        } catch (Exception ex) {
            throw new Exception("Error al listaFechasCaducidad1" + ex);
        }

    }
    

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean actualizarInventarioCantidadActual(Inventario inventario, Inventario inventarioInactivar, MovimientoInventario movimientoInventario) throws Exception {
        boolean res = false;
        try {
            res = inventarioMapper.actualizarInventarioCantidadActual(inventario);
            if (!res) {
                throw new Exception("Error al alctualizar la cantidadActual del Inventario ");
            } else {
                int tipoDoc = TipoDocumento_Enum.AJUSTES.getValue();
                Folios folio = foliosMapper.obtenerPrefixPorDocument(tipoDoc);
                movimientoInventario.setFolioDocumento(Comunes.generaFolio(folio));
                folio.setSecuencia(Comunes.separaFolio(movimientoInventario.getFolioDocumento()));
                res = foliosMapper.actualizaFolios(folio);
                if (!res) {
                    throw new Exception("Error al actualizar actualizaFolios" + folio);
                } else {
                    res = movimientoInventarioMapper.insertar(movimientoInventario);
                    if (!res) {
                        throw new Exception("Error al insertar el movimiento Inventario ");
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("Error al actuaizar la cantidadActual del InventarioInactivar " + e.getMessage());
        }
        return res;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean actualizarInventarioGlobalLote(Inventario inventario, MovimientoInventario movimientoInventario) throws Exception {
        boolean res = false;
        try {
            res = inventarioMapper.actualizarInventarioGlobalLote(inventario);
            if (!res) {
                throw new Exception("Error al actualizar el lote del inventario. ");
            } else {
                int tipoDoc = TipoDocumento_Enum.AJUSTES.getValue();
                Folios folio = foliosMapper.obtenerPrefixPorDocument(tipoDoc);
                movimientoInventario.setFolioDocumento(Comunes.generaFolio(folio));
                folio.setSecuencia(Comunes.separaFolio(movimientoInventario.getFolioDocumento()));
                res = foliosMapper.actualizaFolios(folio);
                if (!res) {
                    throw new Exception("Error al actualizaFolios del ajuste movimientoInventario: " + folio);
                } else {
                    res = movimientoInventarioMapper.insertar(movimientoInventario);
                    if (!res) {
                        throw new Exception("Exception al registrar el movimientoInventario ");
                    }
                }
            }
        } catch (Exception ex) {
            throw new Exception("Error actualizarInventarioGlobalLote. " + ex.getMessage());
        }
        return res;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean actualizarInventarioGlobalCaducidad(Inventario inventario, List<MovimientoInventario> movimientoInventario) throws Exception {
        boolean res = false;
        try {
            res = inventarioMapper.actualizarInventarioGlobalCaducidad(inventario);
            if (!res) {
                throw new Exception("Error al actualizar la caducidad del inventario. ");
            } else {
                for(MovimientoInventario movimiento : movimientoInventario) {
                int tipoDoc = TipoDocumento_Enum.AJUSTES.getValue();
                Folios folio = foliosMapper.obtenerPrefixPorDocument(tipoDoc);
                    movimiento.setFolioDocumento(Comunes.generaFolio(folio));
                    folio.setSecuencia(Comunes.separaFolio(movimiento.getFolioDocumento()));
                res = foliosMapper.actualizaFolios(folio);
                if (!res) {
                    throw new Exception("Error al actualizaFolios del ajuste movimiento Inventario: " + folio);
                    } 
                }                
                if (!res) {
                    throw new Exception("Error al actualizar un folios del ajuste movimiento Inventario: ");
                } else {
                    res = movimientoInventarioMapper.insertarMovimientosInventarios(movimientoInventario);
                    if (!res) {
                        throw new Exception("Error al registrar el movimiento Inventario ");
                    }
                }
            }
        } catch (Exception ex) {
            throw new Exception("Error actualizarInventarioGlobalCaducidad. " + ex.getMessage());
        }
        return res;
    }

    @Override
    public InventarioExtended getInventarioTracking(String claveInstitucional, String lote, String idEstructura, Date fechaCaducidad,int presentacionComercial,boolean banderaInventario) throws Exception {
        try {
            return inventarioMapper.getInventarioTracking(claveInstitucional, lote,idEstructura,fechaCaducidad,presentacionComercial,banderaInventario);
        } catch (Exception e) {
            throw  new Exception("Error al buscar el Inventario" + e.getMessage());                    
        }
    }

    @Override
    public Inventario getInventarioByStock(String lote, String claveInstitucional, String claveEstructura, Date fechaCaducidad, int cantidadXCaja) throws Exception {
        try {
            return inventarioMapper.getInventarioByStock(lote, claveInstitucional, claveEstructura, fechaCaducidad, cantidadXCaja);
        } catch (Exception e) {
            throw new Exception("Error al Obtener el Inventario por Stock" + e.getMessage());
        }
    }
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean actualizarDatosForStocks(Inventario inventario, MovimientoInventario movimientoInventario, Usuario usuario, Reabasto reabasto, ReabastoInsumo reabastoInsumo, ReabastoEnviado reabastoEnviado, boolean existeUsuario) throws Exception {
        boolean res = false;
        try {
            if (!existeUsuario) {
                res = usuarioMapper.insertar(usuario);
                if (!res) {
                    throw new Exception("Error al registrar el Usuario");
                }
            }
            res = inventarioMapper.actualizarInventarioCantidadActual(inventario);
            if (!res) {
                throw new Exception("Error al Actualizar el Inventario");
            } else {
                res = movimientoInventarioMapper.insertar(movimientoInventario);
                if (!res) {
                    throw new Exception("Error al insertar  el movimiento Inventario.");
                } else {
                    res = reabastoMapper.insertar(reabasto);
                    if (!res) {
                        throw new Exception("Error al Registrar el Reabasto");
                    } else {
                        res = reabastoInsumoMapper.insertar(reabastoInsumo);
                        if (!res) {
                            throw new Exception("Error al Registrar el ReabastoInsumo");
                        } else {
                            res = reabastoEnviadoMapper.insertar(reabastoEnviado);
                            if (!res) {
                                throw new Exception("Error al Registrar el ReabastoEnviado");
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("Error al Actualizar el Stock de Inventario " + e.getMessage());
        }
        return res;
    }    
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean actualizarDatosForStocksList(//String idEstructura, 
            List<Inventario> inventarioList, List<Inventario> idInventariosConCeros,
            List<MovimientoInventario> movimientoInventarioList, boolean actualizaInventarioExistente) throws Exception {
        boolean res = false;
        try {
            if (actualizaInventarioExistente) {
                res = inventarioMapper.actualizarInventariosCantidadActualStock(inventarioList);
                if (!res) {
                    throw new Exception("Error al actualizar Los Inventarios en los Sistemas");
                }
            }
            if(!idInventariosConCeros.isEmpty()){
                res = inventarioMapper.actualizarInventarioForRestockList(//idEstructura,
                    idInventariosConCeros);
                if(!res){
                    throw new Exception("Error al actualizar La Existencia de Inventarios a 0");
                }
            }            
            if (!movimientoInventarioList.isEmpty()) {
                res = movimientoInventarioMapper.insertarMovimientosInventarios(movimientoInventarioList);
                if (!res) {
                    throw new Exception("Error al Insertar la lista de movimientoInventarios");
                }
            }                     
        } catch (Exception e) {
            throw new Exception("Error al Actualizar el StockList de Inventarios " + e.getMessage());
        }
        return res;
    }
    
    

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean desactivarInsumoInventario(Inventario invt, TipoBloqueo tipoBloqueo) throws Exception {
        boolean resp = false;

        try {
            resp = tipoBloqueoMapper.insertar(tipoBloqueo);
            if (resp) {
                resp = inventarioMapper.actualizar(invt);

                if (!resp) 
                    throw new Exception("Error al Registrar Bloqueo . ");
                } else 
                    throw new Exception("Error al Registrar Update Inventario. ");
                
        } catch (Exception e) {
            throw new Exception("Error al desactivar " + e);
        }
        return resp;
    }
    
    
 @Override
    public Inventario obtenerIdInsumoByClave(String claveInstitucional, String lote, Date fecha, String estructura) throws Exception {
        try {
            return inventarioMapper.obtenerIdInsumoByClave(claveInstitucional, lote , fecha, estructura);
        } catch (Exception ex) {
            throw new Exception("Error al obtener Insumo. " + ex.getMessage());
        }
    }
    
     @Override
    public Inventario obtenerIdInsumoporClaveyLote(String claveInstitucional) throws Exception {
        try {
            return inventarioMapper.obtenerIdInsumoporClaveyLote(claveInstitucional);
        } catch (Exception ex) {
            throw new Exception("Error al obtener Insumo. " + ex.getMessage());
        }
    }
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean actualizarInventarioBloqueos(Inventario invt, TipoBloqueo tipoBloqueo, MovimientoInventario movimientoInventario) throws Exception {
         boolean resp = false;
         try {
            resp = tipoBloqueoMapper.insertar(tipoBloqueo);
            if (!resp) {
                throw new Exception("Error al Registrar Bloqueo . ");
            } else {
                resp = inventarioMapper.actualizarInventarioBloqueos(invt);
                if (!resp) {
                    throw new Exception("Error al Registrar Update Inventario. ");
                } else {
                    int tipoDoc = TipoDocumento_Enum.AJUSTES.getValue();
                    Folios folio = foliosMapper.obtenerPrefixPorDocument(tipoDoc);
                    movimientoInventario.setFolioDocumento(Comunes.generaFolio(folio));
                    folio.setSecuencia(Comunes.separaFolio(movimientoInventario.getFolioDocumento()));
                    resp = foliosMapper.actualizaFolios(folio);
                    if (!resp) {
                        throw new Exception("Error al actualizar el folio del ajuste movimiento Inventario:" + folio);
                    } else {
                        resp = movimientoInventarioMapper.insertar(movimientoInventario);
                        if (!resp) {
                            throw new Exception("Error al registrar el movimiento Inventario.");
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("Error al desactivar " + e);
        }
        return resp;
    }

    @Override
    public List<Inventario> getListByIdInventario(List<String> idInventariosConCeros
    ) throws Exception {                
        try {            
           return inventarioMapper.getListByIdInventario(idInventariosConCeros
           );
        } catch (Exception ex) {
            throw new Exception("Error getListByIdInventario. " + ex.getMessage());
        }
    }
    
    @Override
    public InventarioExtended getInsumoByFolioClaveInvRefill(String claveInstitucional, String lote, Date fechaCaducidad, String codigoArea) throws Exception {
        try {
            return inventarioMapper.getInsumoByFolioClaveInvRefill(claveInstitucional, lote, fechaCaducidad, codigoArea);
        } catch (Exception e) {
            throw new Exception("Error al obtener el Inventario " + e.getMessage());
        }
    }
    
    @Override
    public List<ClaveProveedorBarras> obtienelistaCodigoBarras(String claveInstitucional) throws Exception {
        List<ClaveProveedorBarras> listaCodProvedor = new ArrayList<>();
        try {
          listaCodProvedor = inventarioMapper.obtienelistaCodigoBarras(claveInstitucional);
        } catch(Exception ex) {
            throw new Exception("Error al obtener la lista de impresoras " + ex.getMessage());
        }
        return listaCodProvedor;
    }
    
    @Override
    public List<ClaveProveedorBarras> obtienelistaProveedor(String claveInstitucional) throws Exception {
        List<ClaveProveedorBarras> listaCodProvedor = new ArrayList<>();
        try {
            listaCodProvedor = inventarioMapper.obtienelistaProveedor(claveInstitucional);
        } catch (Exception ex) {
            throw new Exception("Error al obtener la lista de impresoras " + ex.getMessage());
        }
        return listaCodProvedor;
    }

    @Override
    public List<ClaveProveedorBarras> obtienelistaCodigoBarrasByclaveInstAndClaveProv(String claveInstitucional , String claveProveedor) throws Exception {
        List<ClaveProveedorBarras> listaCodProvedor = new ArrayList<>();
        try {
            listaCodProvedor = inventarioMapper.obtienelistaCodigoBarrasByclaveInstAndClaveProv(claveInstitucional , claveProveedor);
        } catch (Exception ex) {
            throw new Exception("Error al obtener la lista de impresoras " + ex.getMessage());
        }
        return listaCodProvedor;
    }
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean updateClavProveedor(Inventario actualizar) throws Exception {
        try {
            return inventarioMapper.updateClavProveedor(actualizar);
        } catch (Exception ex) {
            throw new Exception("Error al eliminar Codigo Proveedor" + ex.getMessage());
        }
    }
    
    @Override
    public List<InventarioExtended> obtenerInventarioPorIdEstructurayporIdInsumo(String idEstructura, String idInsumo) throws Exception {
        try {
            return inventarioMapper.obtenerInventarioPorIdEstructurayporIdInsumo(idEstructura, idInsumo);
        } catch (Exception ex) {
            throw new Exception("Error al obtener el inventario por cada almacen y por cada insumo: " + ex.getMessage());
        }
    }
    
    @Override
    public Inventario obtenerLoteSugerido(List<String> idEstructuraList , String idInsumo) throws Exception {
        try {
            return inventarioMapper.obtenerLoteSugerido(idEstructuraList, idInsumo);
        } catch (Exception ex) {
            throw new Exception("Error al obtener el lote sugerido " + ex.getMessage());
        }
    }
    
    @Override
    public List<InventarioExtended> obtenerInventarioPorIdEstructurasIdInsumo(List<String> idEstructuraList, String idInsumo) throws Exception {
        try {
            return inventarioMapper.obtenerInventarioPorIdEstructurasIdInsumo(idEstructuraList, idInsumo);
        } catch (Exception ex) {
            throw new Exception("Error al obtener el inventario por cada almacen y por cada insumo: " + ex.getMessage());
        }
    }
    
}
    

