/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import mx.mc.enums.EstatusOrdenesCompra_Enum;
import mx.mc.enums.EstatusReabasto_Enum;
import mx.mc.enums.TipoDocumento_Enum;
import mx.mc.init.Constantes;
import mx.mc.mapper.AlmacenControlMapper;
import mx.mc.mapper.DetalleInsumoSiamMapper;
import mx.mc.mapper.EntidadHospitalariaMapper;
import mx.mc.mapper.EstructuraMapper;
import mx.mc.mapper.FolioAlternativoFolioMusMapper;
import mx.mc.mapper.FoliosMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.InventarioMapper;
import mx.mc.mapper.MovimientoInventarioMapper;
import mx.mc.mapper.OrdenCompraDetalleMapper;
import mx.mc.mapper.OrdenCompraMapper;
import mx.mc.mapper.ReabastoEnviadoMapper;
import mx.mc.mapper.ReabastoInsumoMapper;
import mx.mc.mapper.ReabastoMapper;
import mx.mc.mapper.UsuarioMapper;
import mx.mc.model.AlmacenControl;
import mx.mc.model.DetalleInsumoSiam;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.Estructura;
import mx.mc.model.FolioAlternativoFolioMus;
import mx.mc.model.Folios;
import mx.mc.model.Inventario;
import mx.mc.model.MovimientoInventario;
import mx.mc.model.OrdenCompra;
import mx.mc.model.Reabasto;
import mx.mc.model.ReabastoEnviado;
import mx.mc.model.ReabastoEnviadoExtended;
import mx.mc.model.ReabastoExtended;
import mx.mc.model.ReabastoInsumo;
import mx.mc.model.ReabastoInsumoExtended;
import mx.mc.model.Usuario;
import mx.mc.util.Comunes;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author bbautista
 */
@Service
public class ReabastoServiceImpl extends GenericCrudServiceImpl<Reabasto, String> implements ReabastoService {

    @Autowired
    private ReabastoMapper reabastoMapper;

    @Autowired
    private ReabastoInsumoMapper reabastoInsumoMapper;

    @Autowired
    private InventarioMapper inventarioMapper;

    @Autowired
    private FoliosMapper foliosMapper;

    @Autowired
    private MovimientoInventarioMapper movimientoInventarioMapper;

    @Autowired
    private ReabastoEnviadoMapper reabastoEnviadoMapper;

    @Autowired
    private AlmacenControlMapper almacenControlMapper;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private EstructuraMapper estructuraMapper;

    @Autowired
    private EntidadHospitalariaMapper entidadHospitalariaMapper;

    @Autowired
    private FolioAlternativoFolioMusMapper folioAlternativoFolioMusMapper;

    @Autowired
    private DetalleInsumoSiamMapper detalleInsumoSiamMapper;

    @Autowired
    private OrdenCompraMapper ordenCompraMapper;

    @Autowired
    public ReabastoServiceImpl(GenericCrudMapper<Reabasto, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public List<ReabastoExtended> obtenerReabastoExtends(Reabasto reabasto, List<Integer> listEstatusReabasto, Integer idTipoAlmacen) throws Exception {
        try {
            return reabastoMapper.obtenerReabastoExtends(reabasto, listEstatusReabasto, idTipoAlmacen);
        } catch (Exception e) {
            throw new Exception("Error obtenerReabastoExtends. " + e.getMessage());
        }
    }

    @Override
    public List<ReabastoExtended> obtenerRegistrosPorCriterioDeBusqueda(
            String textoBusqueda, int numRegistros, List<Integer> listEstatusReabasto,
            String idEstructuraPadre, String idEstructura, Integer idTipoAlmacen) throws Exception {
        try {
            return reabastoMapper.
                    obtenerRegistrosPorCriterioDeBusqueda(
                            textoBusqueda, numRegistros,
                            listEstatusReabasto, idEstructuraPadre, idEstructura, idTipoAlmacen);
        } catch (Exception e) {
            throw new Exception("Error obtenerReabastoExtends. " + e.getMessage());
        }
    }

    @Override
    public List<ReabastoExtended> obtenerRegistrosPorCriterioDeBusquedas(
            String criterioBusqueda, int numRegistros, List<Integer> listEstatusReabasto,
            String idEstructuraPadre, String idEstructura, Integer idTipoAlmacen) throws Exception {
        try {
            return reabastoMapper.
                    obtenerRegistrosPorCriterioDeBusquedas(
                            criterioBusqueda, numRegistros,
                            listEstatusReabasto, idEstructuraPadre, idEstructura, idTipoAlmacen);
        } catch (Exception e) {
            throw new Exception("Error obtenerReabastoExtends. " + e.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean surtirOrdenReabasto(
            Reabasto reabasto, List<ReabastoInsumoExtended> listaReabastoInsumo) throws Exception {
        try {
            boolean resp = reabastoInsumoMapper.
                    actualizarListaReabastoInsumo(listaReabastoInsumo);
            if (resp) {
                resp = reabastoMapper.actualizar(reabasto);
            }

            if (resp) {
                for (ReabastoInsumoExtended item : listaReabastoInsumo) {
                    if (item.getListaDetalleReabIns() != null
                            && !item.getListaDetalleReabIns().isEmpty()) {
                        inventarioMapper.actualizarInventario(
                                item.getListaDetalleReabIns());
                    }
                    //Actualizamos la tabla Almacen control, para indicar que la orden ya fue surtiuda 
                    //TODO Optimizar actualizacion en una lista
                    Reabasto bto = reabastoMapper.obtenerReabastoPorID(reabasto.getIdReabasto());
                    AlmacenControl ctrl = almacenControlMapper.obtenerIdPuntosControl(bto.getIdEstructura(), item.getIdInsumo());
                    ctrl.setSolicitud(Constantes.ESTATUS_INACTIVO);
                    almacenControlMapper.ctrlSurtido(ctrl);

                }
                for (ReabastoInsumoExtended item : listaReabastoInsumo) {
                    List<MovimientoInventario> listaMovInv = new ArrayList<>();
                    if (item.getListaDetalleReabIns() != null
                            && !item.getListaDetalleReabIns().isEmpty()) {
                        for (ReabastoEnviadoExtended itemj : item.getListaDetalleReabIns()) {
                            MovimientoInventario movimientoInv = new MovimientoInventario();
                            movimientoInv.setIdMovimientoInventario(Comunes.getUUID());
                            movimientoInv.setIdTipoMotivo(15);
                            movimientoInv.setFecha(new Date());
                            movimientoInv.setIdUsuarioMovimiento(reabasto.getIdUsuarioSolicitud());
                            movimientoInv.setIdEstrutcuraOrigen(reabasto.getIdEstructuraPadre());
                            movimientoInv.setIdEstrutcuraDestino(reabasto.getIdEstructura());
                            movimientoInv.setIdInventario(itemj.getIdInventarioSurtido());
                            movimientoInv.setCantidad(itemj.getCantidadEnviado());
                            movimientoInv.setFolioDocumento(reabasto.getFolio());
                            listaMovInv.add(movimientoInv);
                        }
                        movimientoInventarioMapper.insertarMovimientosInventarios(listaMovInv);
                    }
                }
            }

            reabastoEnviadoMapper.eliminarListaReabastoEnviado(reabasto.getIdReabasto());
            for (ReabastoInsumoExtended item : listaReabastoInsumo) {
                List<ReabastoEnviado> listaReabastoEnviado = new ArrayList<>();
                if (item.getListaDetalleReabIns() != null
                        && !item.getListaDetalleReabIns().isEmpty()) {
                    for (ReabastoEnviadoExtended itemj : item.getListaDetalleReabIns()) {
                        ReabastoEnviado reabastoEnviado = new ReabastoEnviado();
                        reabastoEnviado.setIdReabastoEnviado(Comunes.getUUID());
                        reabastoEnviado.setIdReabastoInsumo(item.getIdReabastoInsumo());
                        reabastoEnviado.setIdInsumo(itemj.getIdMedicamento());
                        reabastoEnviado.setIdEstructura(itemj.getIdEstructura());
                        if (itemj.getCantidadCaja() != null) {
                            reabastoEnviado.setCantidadXCaja(itemj.getCantidadCaja());
                        } else {
                            reabastoEnviado.setCantidadXCaja(1);
                        }
                        reabastoEnviado.setFechaCad(itemj.getFechaCaducidad());
                        reabastoEnviado.setLoteEnv(itemj.getLote());
                        reabastoEnviado.setIdInventarioSurtido(itemj.getIdInventarioSurtido());
                        reabastoEnviado.setCantidadEnviado(itemj.getCantidadEnviado());
                        reabastoEnviado.setIdEstatusReabasto(Constantes.ESTATUS_REABASTO_ENTRANS);

                        reabastoEnviado.setOsmolaridad(itemj.getOsmolaridad());
                        reabastoEnviado.setDensidad(itemj.getDensidad());
                        reabastoEnviado.setCalorias(itemj.getCalorias());
                        reabastoEnviado.setNoHorasEstabilidad(itemj.getNoHorasEstabilidad());
                        reabastoEnviado.setIdFabricante(itemj.getIdFabricante());
                        reabastoEnviado.setIdProveedor(itemj.getIdProveedor());
                        reabastoEnviado.setClaveOriginalSurtida(itemj.getClaveProveedor());

                        reabastoEnviado.setInsertFecha(new Date());
                        reabastoEnviado.setInsertIdUsuario(item.getInsertIdUsuario());
                        listaReabastoEnviado.add(reabastoEnviado);
                    }
                } else {
                    ReabastoEnviado reabastoEnviado = new ReabastoEnviado();
                    reabastoEnviado.setIdReabastoEnviado(Comunes.getUUID());
                    reabastoEnviado.setIdReabastoInsumo(item.getIdReabastoInsumo());
                    reabastoEnviado.setIdInsumo(item.getIdInsumo());
                    reabastoEnviado.setIdEstructura(reabasto.getIdEstructura());
                    reabastoEnviado.setCantidadXCaja(item.getFactorTransformacion());
                    reabastoEnviado.setCantidadEnviado(item.getCantidadSurtida());
                    reabastoEnviado.setIdEstatusReabasto(Constantes.ESTATUS_REABASTO_ENTRANS);
                    reabastoEnviado.setInsertFecha(new Date());
                    reabastoEnviado.setInsertIdUsuario(item.getInsertIdUsuario());
                    listaReabastoEnviado.add(reabastoEnviado);
                }
                reabastoEnviadoMapper.insertarDetalleInsumos(listaReabastoEnviado);
            }
            return resp;
        } catch (Exception e) {
            throw new Exception("Error actualizarCabezeraReabasto. " + e.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean surtirOrdenReabastoProveedorFarmacia(
            Reabasto reabasto, List<ReabastoInsumoExtended> listaReabastoInsumo,
            String idUsuario) throws Exception {
        try {
            boolean resp = reabastoInsumoMapper.
                    actualizarListaReabastoInsumo(listaReabastoInsumo);
            if (resp) {
                resp = reabastoMapper.actualizar(reabasto);
            }

            reabastoEnviadoMapper.eliminarListaReabastoEnviado(reabasto.getIdReabasto());
            for (ReabastoInsumoExtended item : listaReabastoInsumo) {
                if (item.getListaDetalleReabIns() != null
                        && !item.getListaDetalleReabIns().isEmpty()) {
                    List<ReabastoEnviado> listaReabastoEnviado = new ArrayList<>();
                    for (ReabastoEnviadoExtended itemj : item.getListaDetalleReabIns()) {
                        ReabastoEnviado reabastoEnviado = new ReabastoEnviado();
                        reabastoEnviado.setIdReabastoEnviado(Comunes.getUUID());
                        reabastoEnviado.setIdReabastoInsumo(item.getIdReabastoInsumo());
                        reabastoEnviado.setCantidadEnviado(itemj.getCantidadEnviado());
                        reabastoEnviado.setIdEstatusReabasto(Constantes.ESTATUS_REABASTO_ENTRANS);
                        reabastoEnviado.setIdInsumo(item.getIdInsumo());
                        reabastoEnviado.setIdEstructura(itemj.getIdEstructura());
                        reabastoEnviado.setLoteEnv(itemj.getLote());
                        reabastoEnviado.setFechaCad(itemj.getFechaCaducidad());
                        reabastoEnviado.setClaveProveedor(itemj.getClaveProveedor());
                        reabastoEnviado.setCantidadXCaja(item.getCantidadXCaja());
                        reabastoEnviado.setInsertFecha(new Date());
                        reabastoEnviado.setInsertIdUsuario(idUsuario);

                        reabastoEnviado.setOsmolaridad(itemj.getOsmolaridad());
                        reabastoEnviado.setDensidad(itemj.getDensidad());
                        reabastoEnviado.setCalorias(itemj.getCalorias());
                        reabastoEnviado.setNoHorasEstabilidad(itemj.getNoHorasEstabilidad());
                        reabastoEnviado.setIdFabricante(itemj.getIdFabricante());
                        reabastoEnviado.setIdProveedor(itemj.getIdProveedor());
                        reabastoEnviado.setClaveOriginalSurtida(itemj.getClaveProveedor());

                        listaReabastoEnviado.add(reabastoEnviado);
                    }
                    reabastoEnviadoMapper.insertarDetalleInsumos(listaReabastoEnviado);
                }
                //Actualizamos la tabla Almacen control, para indicar que la orden ya fue surtiuda 
                Reabasto bto = reabastoMapper.obtenerReabastoPorID(reabasto.getIdReabasto());
                AlmacenControl ctrl = almacenControlMapper.obtenerIdPuntosControl(bto.getIdEstructura(), item.getIdInsumo());
                ctrl.setSolicitud(Constantes.ESTATUS_INACTIVO);
                almacenControlMapper.ctrlSurtido(ctrl);
            }
            return resp;
        } catch (Exception e) {
            throw new Exception("Error actualizarCabezeraReabasto. " + e.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean guardarOrdenReabasto(
            Reabasto reabasto,
            List<ReabastoInsumoExtended> listaReabastoInsumo) throws Exception {
        try {
            boolean resp = reabastoInsumoMapper.
                    actualizarListaReabastoInsumo(listaReabastoInsumo);
            if (resp) {
                reabastoEnviadoMapper.eliminarListaReabastoEnviado(reabasto.getIdReabasto());
                for (ReabastoInsumoExtended item : listaReabastoInsumo) {
                    List<ReabastoEnviado> listaReabastoEnviado = new ArrayList<>();
                    if (item.getListaDetalleReabIns() != null) {
                        for (ReabastoEnviadoExtended itemj : item.getListaDetalleReabIns()) {
                            ReabastoEnviado reabastoEnviado = new ReabastoEnviado();
                            reabastoEnviado.setIdReabastoEnviado(Comunes.getUUID());
                            reabastoEnviado.setIdReabastoInsumo(itemj.getIdReabastoInsumo());
                            reabastoEnviado.setIdInsumo(itemj.getIdMedicamento());
                            reabastoEnviado.setIdEstructura(itemj.getIdEstructura());
                            reabastoEnviado.setFechaCad(itemj.getFechaCaducidad());
                            reabastoEnviado.setLoteEnv(itemj.getLote());
                            reabastoEnviado.setIdInventarioSurtido(itemj.getIdInventarioSurtido());
                            reabastoEnviado.setCantidadEnviado(itemj.getCantidadEnviado());
                            reabastoEnviado.setIdEstatusReabasto(Constantes.ESTATUS_REABASTO_ENTRANS);
                            reabastoEnviado.setInsertFecha(new Date());
                            reabastoEnviado.setInsertIdUsuario(item.getInsertIdUsuario());
                            listaReabastoEnviado.add(reabastoEnviado);
                        }
                        if (!item.getListaDetalleReabIns().isEmpty()) {
                            reabastoEnviadoMapper.insertarDetalleInsumos(listaReabastoEnviado);
                        }
                    }
                }
            }
            return resp;
        } catch (Exception e) {
            throw new Exception("Error actualizarCabezeraReabasto. " + e.getMessage());
        }
    }

    @Override
    public List<Reabasto> obtenerBusqueda(String cadena, String almacen) throws Exception {
        List<Reabasto> reabastoList = new ArrayList<>();
        try {
            reabastoList = reabastoMapper.obtenerBusqueda(cadena, almacen);
        } catch (Exception ex) {
            throw new Exception("Error al obtener la busqueda" + ex.getMessage());
        }
        return reabastoList;
    }

    @Override
    public Reabasto obtenerReabastoPorID(String cadena) throws Exception {
        Reabasto reabasto = new Reabasto();
        try {
            reabasto = reabastoMapper.obtenerReabastoPorID(cadena);
        } catch (Exception ex) {
            throw new Exception("Error al obtener la busqueda" + ex.getMessage());
        }
        return reabasto;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean insertarSolicitud(Reabasto reabasto, List<ReabastoInsumo> insumos, Integer idTipoAlmacen, boolean xServicio) throws Exception {
        boolean res = false;
        List<AlmacenControl> insumosPC = new ArrayList<>();
        try {
            //Consultar y generar Folio
            int tipoDoc;
            if (idTipoAlmacen.equals(Constantes.ALMACEN_FARMACIA)) {
                tipoDoc = TipoDocumento_Enum.ORDEN_COMPRA.getValue();
            } else {
                tipoDoc = TipoDocumento_Enum.ORDEN_REABASTO.getValue();
            }

            Folios folio = foliosMapper.obtenerPrefixPorDocument(tipoDoc);
            reabasto.setFolio(Comunes.generaFolio(folio));
            //Agregamos los datos para actualizar el Folio
            folio.setSecuencia(Comunes.separaFolio(reabasto.getFolio()));
            folio.setUpdateFecha(new Date());
            folio.setUpdateIdUsuario(reabasto.getInsertIdUsuario());
            res = foliosMapper.actualizaFolios(folio);
            if (res) {
                res = reabastoMapper.insertar(reabasto);
                if (res) {
                    res = reabastoInsumoMapper.guardarReabastoInsumo(insumos);
                    if (res) {
                        if (reabasto.getIdTipoOrden().equals(Constantes.TIPO_ORDEN_NORMAL)) {
                            for (ReabastoInsumo ri : insumos) {
                                if (ri.getIdAlmacenPuntosControl() != null) {
                                    AlmacenControl ac = new AlmacenControl();
                                    ac.setIdAlmacenPuntosControl(ri.getIdAlmacenPuntosControl());
                                    ac.setSolicitud(1);
                                    ac.setUpdateFecha(new Date());
                                    ac.setUpdateIdUsuario(ri.getInsertIdUsuario());
                                    insumosPC.add(ac);
                                }
                            }
                            if (!insumosPC.isEmpty() && !xServicio) {
                                res = almacenControlMapper.actualizarMasivo(insumosPC);
                                if (!res) {
                                    throw new Exception("Error al Actualizar los insumos en Puntos de Control");
                                }
                            }
                        }
                    } else {
                        throw new Exception("Error al Insertar los insumos de la solicitud ");
                    }
                } else {
                    throw new Exception("Error al Insertar el reabasto de la solicitud ");
                }
            } else {
                throw new Exception("Error al actualizar el Folio");
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return res;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean actualizarSolicitud(Reabasto reabasto, List<ReabastoInsumo> insumosUpdate) throws Exception {
        boolean res = false;
        List<AlmacenControl> insumosPC = new ArrayList<>();
        try {
            res = reabastoMapper.actualizar(reabasto);
            if (res) {
                if (!insumosUpdate.isEmpty()) {
                    for (ReabastoInsumo ri : insumosUpdate) {
                        AlmacenControl ac = new AlmacenControl();
                        ac.setIdAlmacenPuntosControl(ri.getIdAlmacenPuntosControl());
                        ac.setSolicitud(1);
                        ac.setUpdateFecha(new Date());
                        ac.setUpdateIdUsuario(ri.getInsertIdUsuario());
                        insumosPC.add(ac);
                    }

                    reabastoInsumoMapper.guardarReabastoInsumo(insumosUpdate);

                    reabastoInsumoMapper.actualizarReabastoInsumo(insumosUpdate);
                }
                res = almacenControlMapper.actualizarMasivo(insumosPC);
                if (!res) {
                    throw new Exception("Error al cancelar Reabasto");
                }
            } else {
                throw new Exception("Error al cancelar Reabasto Insumos");
            }

        } catch (Exception ex) {
            throw new Exception("Error al cancelar la Solicitud" + ex.getMessage());
        }
        return res;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean cancelarSolicitud(Reabasto reab, List<ReabastoInsumo> reabIns, Integer idTipoAlmacen) throws Exception {
        boolean res = false;
        List<AlmacenControl> insumosPC = new ArrayList<>();
        try {

            for (ReabastoInsumo ri : reabIns) {
                AlmacenControl ac = new AlmacenControl();
                ac.setIdAlmacenPuntosControl(ri.getIdAlmacenPuntosControl());
                ac.setSolicitud(0);
                ac.setUpdateFecha(new Date());
                ac.setUpdateIdUsuario(ri.getInsertIdUsuario());
                ri.setIdEstatusReabasto(EstatusReabasto_Enum.CANCELADA.getValue());
                insumosPC.add(ac);
            }
            res = almacenControlMapper.actualizarMasivo(insumosPC);
            if (!res) {
                throw new Exception("Error al actualizar puntos de control actualizados. ");
            } else {
                res = reabastoInsumoMapper.actualizarReabastoInsumo(reabIns);
                if (!res) {
                    throw new Exception("Error al cancelar ReabastoInusmo. ");
                } else {
                    res = reabastoMapper.actualizar(reab);
                    if (!res) {
                        throw new Exception("Error al cancelar Reabasto. ");
                    } else {
                        OrdenCompra oc = new OrdenCompra();
                        oc.setIdReabasto(reab.getIdReabasto());
                        oc = ordenCompraMapper.obtener(oc);
                        if (!res) {
                            throw new Exception("Error al consultar Orden Reabasto asociada. ");
                        } else {
                            if (oc != null) {
                                oc.setIdEstatusOrdenCompra(EstatusOrdenesCompra_Enum.CANCELADA.getIdEstatusOrdenCompra());
                                oc.setUpdateFecha(new java.util.Date());
                                oc.setUpdateIdUsuario(reab.getUpdateIdUsuario());
                                res = ordenCompraMapper.actualizar(oc);
                                if (!res) {
                                    throw new Exception("Error al cancelar Orden Reabasto asociada. ");
                                }
                            }
                        }

                    }
                }
            }
        } catch (Exception ex) {
            throw new Exception("Error al cancelar la Solicitud" + ex.getMessage());
        }
        return res;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean eliminarPorId(String idReabasto, String folio, AlmacenControl almacenControl) throws Exception {
        boolean res = false;
        try {
            res = reabastoInsumoMapper.obtenerTamañoDetalle(idReabasto) > 0 ? reabastoInsumoMapper.eliminarPorIdReabasto(idReabasto) : true;
            if (res) {
                List<FolioAlternativoFolioMus> alternativoList = folioAlternativoFolioMusMapper.obtenerFoliosAlternativos(folio);
                alternativoList.forEach(f -> {
                    detalleInsumoSiamMapper.eliminarDetalle(f.getIdFolioAlternativo());
                });

                res = alternativoList.size() > 0 ? folioAlternativoFolioMusMapper.eliminarPorFolio(folio) : true;
                if (res) {
                    res = reabastoMapper.eliminarPorId(idReabasto);
                    if (res) {
                        res = almacenControlMapper.updateSolicitudByIdAlmacen(almacenControl);
                        if (!res) {
                            throw new Exception("Error al Eliminar el Reabasto");
                        }
                    } else {
                        throw new Exception("Error al Eliminar el Reabasto");
                    }
                } else {
                    throw new Exception("Error al Eliminar el Folio AlternativoMus");
                }
            } else {
                throw new Exception("Error al Eliminar el Insumo");
            }

        } catch (Exception ex) {
            throw new Exception("Error al eliminar por ID" + ex.getMessage());
        }
        return res;
    }

    @Override
    public List<ReabastoExtended> obtenerReabastoExtendsAdmin(Reabasto reabasto, List<Integer> listEstatusReabasto, Integer idTipoAlmacen) throws Exception {
        try {
            return reabastoMapper.obtenerReabastoExtendsAdmin(reabasto, listEstatusReabasto, idTipoAlmacen);
        } catch (Exception e) {
            throw new Exception("Error obtenerReabastoExtends. " + e.getMessage());
        }
    }

    @Override
    public Reabasto getReabastoByFolio(String folio) throws Exception {
        try {
            return reabastoMapper.getReabastoByFolio(folio);
        } catch (Exception e) {
            throw new Exception("Error obtener el reabasto por folio . " + e.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean eliminarOrdenReabasto(Reabasto reabasto, List<ReabastoInsumo> insumosInsert) throws Exception {
        boolean res = false;
        try {
            res = reabastoInsumoMapper.eliminarListaReabastoInsumo(insumosInsert);
            if (res) {
                res = reabastoMapper.eliminarPorId(reabasto.getIdReabasto());
            } else {
                throw new Exception("Error al cancelar Reabasto Insumos");
            }
        } catch (Exception ex) {
            throw new Exception("Error al cancelar la Solicitud" + ex.getMessage());
        }
        return res;
    }

    @Override
    public List<Reabasto> obtenerTransferencias(String cadena, String idEstructura) throws Exception {
        try {
            return reabastoMapper.obtenerTransferencias(cadena, idEstructura);
        } catch (Exception ex) {
            throw new Exception("Error al obtener la Busqueda. " + ex.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean insertarSolicitudTransfer(Reabasto reabasto, List<ReabastoInsumoExtended> insumosExt) throws Exception {
        boolean res = true;
        List<Inventario> insuInvList = new ArrayList<>();
        List<ReabastoInsumo> reabInsuList = new ArrayList<>();
        List<ReabastoEnviado> insuEnviList = new ArrayList<>();
        List<MovimientoInventario> moviInvList = new ArrayList<>();

        try {
            //Consultamos y generamos el folio
            Folios folio = foliosMapper.obtenerPrefixPorDocument(TipoDocumento_Enum.TRANSFERENCIA.getValue());
            reabasto.setFolio(Comunes.generaFolio(folio));

            //actualizar puntos de control cuando sea normal
            for (ReabastoInsumoExtended reaInsuExt : insumosExt) {
                ReabastoInsumo reabInsumo = new ReabastoInsumo();
                //Datos para insertar el ReabastoInsumo
                reabInsumo.setIdReabastoInsumo(Comunes.getUUID());
                reabInsumo.setIdReabasto(reabasto.getIdReabasto());
                reabInsumo.setIdInsumo(reaInsuExt.getIdInsumo());
                reabInsumo.setCantidadSolicitada(reaInsuExt.getCantidadSolicitada());
                reabInsumo.setCantidadComprometida(0);
                reabInsumo.setCantidadSurtida(reaInsuExt.getCantidadSurtida());
                reabInsumo.setCantidadRecibida(0);
                reabInsumo.setIdEstatusReabasto(reaInsuExt.getIdEstatusReabasto());
                reabInsumo.setInsertFecha(new Date());
                reabInsumo.setInsertIdUsuario(reabasto.getInsertIdUsuario());

                for (ReabastoEnviadoExtended reaEnvExt : reaInsuExt.getListaDetalleReabIns()) {
                    Inventario inventario = new Inventario();
                    ReabastoEnviado reabEnviado = new ReabastoEnviado();
                    MovimientoInventario movimiento = new MovimientoInventario();

                    //Datos para llenar el reabastoEnviado                    
                    reabEnviado.setIdReabastoEnviado(reaEnvExt.getIdReabastoEnviado());
                    reabEnviado.setIdReabastoInsumo(reabInsumo.getIdReabastoInsumo());
                    reabEnviado.setIdInventarioSurtido(reaEnvExt.getIdInventarioSurtido());
                    reabEnviado.setCantidadEnviado(reaEnvExt.getCantidadEnviado());
                    reabEnviado.setCantidadRecibida(0);
                    reabEnviado.setCantidadIngresada(0);
                    reabEnviado.setIdEstatusReabasto(reaEnvExt.getIdEstatusReabasto());
                    reabEnviado.setIdInsumo(reaEnvExt.getIdInsumo());
                    reabEnviado.setIdEstructura(reaEnvExt.getIdEstructura());
                    reabEnviado.setLoteEnv(reaEnvExt.getLoteEnv());
                    reabEnviado.setFechaCad(reaEnvExt.getFechaCad());
                    reabEnviado.setCantidadXCaja(reaEnvExt.getCantidadXCaja());
                    reabEnviado.setPresentacionComercial(reaEnvExt.getPresentacionComercial());
                    reabEnviado.setInsertFecha(new Date());
                    reabEnviado.setInsertIdUsuario(reabasto.getInsertIdUsuario());
                    //Datos para actualizar el inventario origen
                    inventario.setIdInventario(reaEnvExt.getIdInventarioSurtido());
                    inventario.setCantidadActual(reaEnvExt.getCantidadEnviado());
                    inventario.setIdEstructura(reabasto.getIdProveedor());
                    inventario.setLote(reaEnvExt.getLoteEnv());
                    inventario.setUpdateFecha(new Date());
                    inventario.setUpdateIdUsuario(reabasto.getInsertIdUsuario());
                    //Datos par insertar el movimiento inventario
                    movimiento.setIdMovimientoInventario(Comunes.getUUID());
                    movimiento.setIdTipoMotivo(21);
                    movimiento.setFecha(new Date());
                    movimiento.setIdUsuarioMovimiento(reabasto.getInsertIdUsuario());
                    movimiento.setIdEstrutcuraOrigen(reabasto.getIdProveedor());
                    movimiento.setIdEstrutcuraDestino(reabasto.getIdEstructura());
                    movimiento.setIdInventario(reaEnvExt.getIdInventarioSurtido());
                    movimiento.setCantidad(reaEnvExt.getCantidadEnviado());
                    movimiento.setFolioDocumento(reabasto.getFolio());

                    insuEnviList.add(reabEnviado);
                    insuInvList.add(inventario);
                    moviInvList.add(movimiento);
                }

                reabInsuList.add(reabInsumo);
            }
            //Agregamos los datos para actualizar el Folio                                
            folio.setSecuencia(Comunes.separaFolio(reabasto.getFolio()));
            folio.setUpdateFecha(new Date());
            folio.setUpdateIdUsuario(reabasto.getInsertIdUsuario());
            res = foliosMapper.actualizaFolios(folio);
            if (res) {
                res = reabastoMapper.insertar(reabasto);
                if (res) {
                    res = reabastoInsumoMapper.insertarListReabastoInsumos(reabInsuList);
                    if (res) {
                        res = reabastoEnviadoMapper.insertListReabatoEnviado(insuEnviList);
                        if (res) {
                            res = inventarioMapper.restarInventarioMasivo(insuInvList);
                            if (res) {
                                res = movimientoInventarioMapper.insertarMovimientosInventarios(moviInvList);
                                if (!res) {
                                    throw new Exception("Error al insertar los movimientos inventario");
                                }
                            } else {
                                throw new Exception("Error al actualizar el inventario");
                            }
                        } else {
                            throw new Exception("Error al Insertar los insumos en Reabasto Enviado");
                        }
                    } else {
                        throw new Exception("Error al Insertar los insumos de la solicitud");
                    }
                } else {
                    throw new Exception("Error al Insertar el reabasto de la solicitud ");
                }
            } else {
                throw new Exception("Error al actualizar el Folio");
            }
        } catch (Exception ex) {

            throw new Exception("Error al Insertar la solicitud" + ex.getMessage());
        }
        return res;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean guardarReabastoColectivo(Reabasto reabasto, List<ReabastoInsumoExtended> insumos, boolean editarOrden,
            List<ReabastoInsumoExtended> insumosEliminar) throws Exception {
        boolean res = true;
        List<ReabastoInsumo> reabInsuList = new ArrayList<>();
        List<ReabastoEnviado> insuEnviList = new ArrayList<>();
        try {
            for (ReabastoInsumoExtended reaInsuExt : insumos) {
                ReabastoInsumo reabInsumo = new ReabastoInsumo();
                //Datos para insertar el ReabastoInsumo
                reabInsumo.setIdReabastoInsumo(reaInsuExt.getIdReabastoInsumo());
                reabInsumo.setIdReabasto(reaInsuExt.getIdReabasto());
                reabInsumo.setIdInsumo(reaInsuExt.getIdInsumo());
                reabInsumo.setCantidadSolicitada(reaInsuExt.getCantidadSolicitada());
                reabInsumo.setCantidadComprometida(reaInsuExt.getCantidadComprometida());
                reabInsumo.setCantidadSurtida(reaInsuExt.getCantidadSurtida());
                reabInsumo.setCantidadRecibida(0);
                reabInsumo.setIdEstatusReabasto(reaInsuExt.getIdEstatusReabasto());
                reabInsumo.setInsertFecha(new Date());
                reabInsumo.setInsertIdUsuario(reabasto.getInsertIdUsuario());
                reabInsumo.setObservaciones(reaInsuExt.getObservaciones());
                reabInsumo.setCantidadActual(reaInsuExt.getCantidadActual());
                for (ReabastoEnviadoExtended reaEnvExt : reaInsuExt.getListaDetalleReabIns()) {
                    ReabastoEnviado reabEnviado = new ReabastoEnviado();
                    //Datos para llenar el reabastoEnviado                    
                    reabEnviado.setIdReabastoEnviado(reaEnvExt.getIdReabastoEnviado());
                    reabEnviado.setIdReabastoInsumo(reabInsumo.getIdReabastoInsumo());
                    reabEnviado.setIdInventarioSurtido(reaEnvExt.getIdInventarioSurtido());
                    reabEnviado.setCantidadEnviado(reaEnvExt.getCantidadEnviado());
                    reabEnviado.setCantidadRecibida(0);
                    reabEnviado.setCantidadIngresada(0);
                    reabEnviado.setIdEstatusReabasto(reaEnvExt.getIdEstatusReabasto());
                    reabEnviado.setIdInsumo(reaEnvExt.getIdInsumo());
                    reabEnviado.setIdEstructura(reaEnvExt.getIdEstructura());
                    reabEnviado.setLoteEnv(reaEnvExt.getLoteEnv());
                    reabEnviado.setFechaCad(reaEnvExt.getFechaCad());
                    reabEnviado.setCantidadXCaja(reaEnvExt.getCantidadXCaja());
                    reabEnviado.setPresentacionComercial(reaEnvExt.getPresentacionComercial());
                    reabEnviado.setInsertFecha(new Date());
                    reabEnviado.setInsertIdUsuario(reabasto.getInsertIdUsuario());
                    insuEnviList.add(reabEnviado);
                }
                reabInsuList.add(reabInsumo);
            }
            if (!editarOrden) {
                res = reabastoMapper.insertar(reabasto);
            } else {
                res = reabastoMapper.actualizar(reabasto);
                //Se elimina los inumos que ya el usuario los elimino previamente
                if (!insumosEliminar.isEmpty()) {
                    // Se itera la lista para crear listas a eliminar de reabastoInsumo y reabastoEnviado
                    List<ReabastoInsumo> listInsumoEliminar = new ArrayList<>();
                    List<ReabastoEnviado> listEnviadoEliminar = new ArrayList<>();
                    for (ReabastoInsumoExtended insumoEliminar : insumosEliminar) {
                        ReabastoInsumo insumo = reabastoInsumoMapper.obtener(new ReabastoInsumo(insumoEliminar.getIdReabastoInsumo()));
                        if (insumo != null) {
                            listInsumoEliminar.add(new ReabastoInsumo(insumoEliminar.getIdReabastoInsumo()));
                            for (ReabastoEnviadoExtended insumoEnviado : insumoEliminar.getListaDetalleReabIns()) {
                                listEnviadoEliminar.add(new ReabastoEnviado(insumoEnviado.getIdReabastoEnviado()));
                            }
                        }
                    }
                    if (!listEnviadoEliminar.isEmpty()) {
                        res = reabastoEnviadoMapper.eliminarListReabastoEnviado(listEnviadoEliminar);
                        if (res) {
                            if (res) {
                                res = reabastoInsumoMapper.eliminarListaReabastoInsumo(listInsumoEliminar);
                            } else {
                                throw new Exception("Error al eliminar los insumos de reabasto colectivo:   ");
                            }
                        } else {
                            throw new Exception("Error al eliminar los insumos enviados de reabasto colectivo:   ");
                        }
                    }

                }
            }
            if (res) {
                if (!reabInsuList.isEmpty()) {
                    res = reabastoInsumoMapper.insertarListReabastoInsumos(reabInsuList);
                    if (res) {
                        res = reabastoEnviadoMapper.insertListReabatoEnviado(insuEnviList);
                        if (!res) {
                            throw new Exception("Error al Insertar los insumos en Reabasto Enviado:      ");
                        }
                    } else {
                        throw new Exception("Error al Insertar los insumos de reabasto colectivo:   ");
                    }
                }
            } else {
                throw new Exception("Error al Insertar el reabasto de reabasto colectivo:    ");
            }
            return res;
        } catch (Exception ex) {
            throw new Exception("Error al guardar el Reabasto colectivo:   " + ex.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean surtirOrdenReabastoColectivo(
            Reabasto reabasto, List<ReabastoInsumoExtended> listaReabastoInsumo) throws Exception {
        try {
            boolean resp = reabastoInsumoMapper.
                    actualizarListaReabastoInsumo(listaReabastoInsumo);
            if (resp) {
                resp = reabastoMapper.actualizar(reabasto);
            }

            if (resp) {
                for (ReabastoInsumoExtended item : listaReabastoInsumo) {
                    if (item.getListaDetalleReabIns() != null
                            && !item.getListaDetalleReabIns().isEmpty()) {
                        inventarioMapper.updateInventario(
                                item.getListaDetalleReabIns());
                    }
                    //Actualizamos la tabla Almacen control, para indicar que la orden ya fue surtiuda 
                    //TODO Optimizar actualizacion en una lista
                    /*NO es necesario para este reabasto
                    Reabasto bto = reabastoMapper.obtenerReabastoPorID(reabasto.getIdReabasto());
                    AlmacenControl ctrl = almacenControlMapper.obtenerIdPuntosControl(bto.getIdEstructura(), item.getIdInsumo());
                    ctrl.setSolicitud(Constantes.ESTATUS_INACTIVO);
                    almacenControlMapper.ctrlSurtido(ctrl);*/

                }
                for (ReabastoInsumoExtended item : listaReabastoInsumo) {
                    List<MovimientoInventario> listaMovInv = new ArrayList<>();
                    if (item.getListaDetalleReabIns() != null
                            && !item.getListaDetalleReabIns().isEmpty()) {
                        for (ReabastoEnviadoExtended itemj : item.getListaDetalleReabIns()) {
                            MovimientoInventario movimientoInv = new MovimientoInventario();
                            movimientoInv.setIdMovimientoInventario(Comunes.getUUID());
                            //todo Revisar el tipo de movimiento
                            movimientoInv.setIdTipoMotivo(30);
                            movimientoInv.setFecha(new Date());
                            movimientoInv.setIdUsuarioMovimiento(reabasto.getIdUsuarioSolicitud());
                            movimientoInv.setIdEstrutcuraOrigen(reabasto.getIdEstructuraPadre());
                            movimientoInv.setIdEstrutcuraDestino(reabasto.getIdEstructura());
                            movimientoInv.setIdInventario(itemj.getIdInventarioSurtido());
                            movimientoInv.setCantidad(itemj.getCantidadEnviado());
                            movimientoInv.setFolioDocumento(reabasto.getFolio());
                            listaMovInv.add(movimientoInv);
                        }
                        movimientoInventarioMapper.insertarMovimientosInventarios(listaMovInv);
                    }
                }
            }

            reabastoEnviadoMapper.eliminarListaReabastoEnviado(reabasto.getIdReabasto());
            for (ReabastoInsumoExtended item : listaReabastoInsumo) {
                List<ReabastoEnviado> listaReabastoEnviado = new ArrayList<>();
                if (item.getListaDetalleReabIns() != null
                        && !item.getListaDetalleReabIns().isEmpty()) {
                    for (ReabastoEnviadoExtended itemj : item.getListaDetalleReabIns()) {
                        ReabastoEnviado reabastoEnviado = new ReabastoEnviado();
                        reabastoEnviado.setIdReabastoEnviado(Comunes.getUUID());
                        reabastoEnviado.setIdReabastoInsumo(item.getIdReabastoInsumo());
                        reabastoEnviado.setIdInsumo(itemj.getIdMedicamento());
                        reabastoEnviado.setIdEstructura(itemj.getIdEstructura());
                        reabastoEnviado.setCantidadXCaja(itemj.getCantidadXCaja());
                        reabastoEnviado.setFechaCad(itemj.getFechaCaducidad());
                        reabastoEnviado.setLoteEnv(itemj.getLote());
                        reabastoEnviado.setIdInventarioSurtido(itemj.getIdInventarioSurtido());
                        reabastoEnviado.setCantidadEnviado(itemj.getCantidadEnviado());
                        reabastoEnviado.setIdEstatusReabasto(Constantes.ESTATUS_REABASTO_ENTRANS);
                        reabastoEnviado.setInsertFecha(new Date());
                        reabastoEnviado.setInsertIdUsuario(item.getInsertIdUsuario());
                        listaReabastoEnviado.add(reabastoEnviado);
                    }
                } else {
                    ReabastoEnviado reabastoEnviado = new ReabastoEnviado();
                    reabastoEnviado.setIdReabastoEnviado(Comunes.getUUID());
                    reabastoEnviado.setIdReabastoInsumo(item.getIdReabastoInsumo());
                    reabastoEnviado.setIdInsumo(item.getIdInsumo());
                    reabastoEnviado.setIdEstructura(reabasto.getIdEstructura());
                    reabastoEnviado.setCantidadXCaja(item.getFactorTransformacion());
                    reabastoEnviado.setCantidadEnviado(item.getCantidadSurtida());
                    reabastoEnviado.setIdEstatusReabasto(Constantes.ESTATUS_REABASTO_ENTRANS);
                    reabastoEnviado.setInsertFecha(new Date());
                    reabastoEnviado.setInsertIdUsuario(item.getInsertIdUsuario());
                    listaReabastoEnviado.add(reabastoEnviado);
                }
                reabastoEnviadoMapper.insertarDetalleInsumos(listaReabastoEnviado);
            }
            return resp;
        } catch (Exception e) {
            throw new Exception("Error actualizarCabezeraReabasto. " + e.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean cancelarOrdenReabastoColectivo(Reabasto reabasto,
            ReabastoInsumo reabastoInsumo,
            List<ReabastoEnviado> listaReabastoEnviado,
            List<Inventario> listaInventarios,
            List<MovimientoInventario> listaMovimientos) throws Exception {
        boolean resp = false;
        try {
            resp = this.reabastoMapper.actualizar(reabasto);
            if (resp) {
                resp = this.reabastoInsumoMapper.actualizarPorIdReabasto(reabastoInsumo);
                if (resp) {
                    resp = this.reabastoEnviadoMapper.actualizarListaReabastoEnviadoByIdReabastoInsumo(listaReabastoEnviado);
                    if (resp) {
                        resp = this.inventarioMapper.revertirInventarioList(listaInventarios);
                        if (resp) {
                            this.movimientoInventarioMapper.insertarMovimientosInventarios(listaMovimientos);
                        }
                    } else {
                        throw new Exception("Error al actualizar el reabastoEnviado. ");
                    }
                } else {
                    throw new Exception("Error al actualizar el reabastoEnviado. ");
                }
            } else {
                throw new Exception("Error al actualizar el reabasto. ");
            }
            return resp;
        } catch (Exception e) {
            throw new Exception("Error actualizarCabezeraReabasto. " + e.getMessage());
        }
    }

    @Override
    public List<Reabasto> obtenerBusquedaLazy(String cadena, String almacen, int startingAt, int maxPerPage, String sortField, SortOrder sortOrder) throws Exception {
        List<Reabasto> reabastoList = new ArrayList<>();
        try {
            String order = sortOrder == SortOrder.ASCENDING ? "asc" : sortOrder == SortOrder.DESCENDING ? "desc" : null;
            reabastoList = reabastoMapper.obtenerBusquedaLazy(cadena, almacen, startingAt, maxPerPage, sortField, order);
        } catch (Exception ex) {
            throw new Exception("Error al obtener la busqueda" + ex.getMessage());
        }
        return reabastoList;
    }

    @Override
    public Long obtenerBusquedaTotalLazy(String cadena, String almacen) throws Exception {
        try {
            return reabastoMapper.obtenerBusquedaTotalLazy(cadena, almacen);
        } catch (Exception ex) {
            throw new Exception("Error al obtener la busqueda" + ex.getMessage());
        }
    }

    @Override
    public List<ReabastoExtended> obtenerBusquedaRecepcionLazy(String cadena, String almacen, String almacenPadre, int tipoAlmacen, int startingAt, int maxPerPage,
            String sortField, SortOrder sortOrder) throws Exception {
        List<ReabastoExtended> reabastoList = new ArrayList<>();
        try {
            String order = sortOrder == SortOrder.ASCENDING ? "asc" : sortOrder == SortOrder.DESCENDING ? "desc" : null;
            reabastoList = reabastoMapper.obtenerBusquedaRecepcionLazy(cadena, almacen, almacenPadre, tipoAlmacen, startingAt, maxPerPage, sortField, order);
        } catch (Exception ex) {
            throw new Exception("Error al obtener la busqueda" + ex.getMessage());
        }
        return reabastoList;
    }

    @Override
    public Long obtenerBusquedaRecepcionTotalLazy(String cadena, String almacen, String almacenPadre, int tipoAlmacen) throws Exception {
        try {
            return reabastoMapper.obtenerBusquedaRecepcionTotalLazy(cadena, almacen, almacenPadre, tipoAlmacen);
        } catch (Exception ex) {
            throw new Exception("Error al obtener la busqueda" + ex.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean insertForRefill1(boolean existUsuario, Usuario user, Reabasto reabasto,
            List<ReabastoInsumoExtended> reabastoExtendList,
            List<ReabastoEnviado> reabastoEnviadoList,
            List<Inventario> inventarioList,
            List<MovimientoInventario> movInventarioList, List<Inventario> lisInventarioInsert) throws Exception {
        boolean resp = false;
        try {
            resp = reabastoMapper.actualizar(reabasto);
            if (!resp) {
                throw new Exception("Error al actualizar el Reabasto ");
            } else {
                resp = reabastoInsumoMapper.actualizarListaReabastoInsumo(reabastoExtendList);
                if (!resp) {
                    throw new Exception("Error al actualizar el ReabastoInsumoList ");
                } else {
                    resp = reabastoEnviadoMapper.insertListReabatoEnviado(reabastoEnviadoList);
                    if (!resp) {
                        throw new Exception("Error al actualizar el ReabastoEnviadoList ");
                    } else {
                        if (lisInventarioInsert != null && !lisInventarioInsert.isEmpty()) {
                            resp = inventarioMapper.insertListInventarios(lisInventarioInsert);
                            if (!resp) {
                                throw new Exception("Error al actualizar el ReabastoInsumoList ");
                            }
                        }
                        if (inventarioList != null) {
                            resp = inventarioMapper.actualizaInvByInvInsumoEstr(inventarioList);
                            if (!resp) {
                                throw new Exception("Error al actualizar la lista de Inventarios ");
                            }
                        }
                        if (!existUsuario) {
                            resp = usuarioMapper.insertar(user);
                            if (!resp) {
                                throw new Exception("Error al insertar el usuario ");
                            } else {
                                resp = movimientoInventarioMapper.insertarMovimientosInventarios(movInventarioList);
                                if (!resp) {
                                    throw new Exception("Error al insertar el usuario ");
                                }
                            }
                        } else {
                            resp = movimientoInventarioMapper.insertarMovimientosInventarios(movInventarioList);
                            if (!resp) {
                                throw new Exception("Error al insertar el usuario ");
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("Error en InsertForRefill " + e.getMessage());
        }
        return resp;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean insertForRefillInv(boolean existUsuario, Usuario user, List<Inventario> inventarioList,
            List<MovimientoInventario> movInventarioList, List<Inventario> lisInventarioInsert) throws Exception {
        boolean resp = false;
        try {
            if (lisInventarioInsert != null && !lisInventarioInsert.isEmpty()) {
                resp = inventarioMapper.insertListInventarios(lisInventarioInsert);
                if (!resp) {
                    throw new Exception("Error al actualizar lisInventarioInsert ");
                }
            }
            if (inventarioList != null && !inventarioList.isEmpty()) {
                resp = inventarioMapper.actualizaInvByInvInsumoEstrRefill(inventarioList);
                if (!resp) {
                    throw new Exception("Error al actualizar la lista inventarioList ");
                }
            }
            if (!existUsuario) {
                resp = usuarioMapper.insertar(user);
                if (!resp) {
                    throw new Exception("Error al insertar el usuario ");
                } else {
                    if (!movInventarioList.isEmpty()) {
                        resp = movimientoInventarioMapper.insertarMovimientosInventarios(movInventarioList);
                        if (!resp) {
                            throw new Exception("Error al insertar el movimientoInventario ");
                        }
                    }
                }
            } else {
                if (!movInventarioList.isEmpty()) {
                    resp = movimientoInventarioMapper.insertarMovimientosInventarios(movInventarioList);
                    if (!resp) {
                        throw new Exception("Error al insertar el usuario ");
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("Error en InsertForRefill " + e.getMessage());
        }
        return resp;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean surtirReabastoProveedorAutomatico(Reabasto reabasto, List<ReabastoInsumoExtended> listaReabastoInsumo, List<ReabastoInsumo> listaReabastoInsumoAgreg, List<ReabastoEnviado> listaReabastoEnviado) throws Exception {
        boolean resp = false;
        try {
            resp = reabastoMapper.actualizar(reabasto);
            if (!resp) {
                throw new Exception("Error al actualizar el Reabasto ");
            } else {
                if (listaReabastoInsumoAgreg != null && !listaReabastoInsumoAgreg.isEmpty()) {
                    resp = reabastoInsumoMapper.insertarListReabastoInsumos(listaReabastoInsumoAgreg);
                    if (!resp) {
                        throw new Exception("Error al insertar lista de reabastoInsumo");
                    }
                }
                resp = reabastoInsumoMapper.actualizarListaReabastoInsumo(listaReabastoInsumo);

                if (!resp) {
                    throw new Exception("Error al actualizar el ReabastoInsumoList ");
                } else {
                    resp = reabastoEnviadoMapper.insertListReabatoEnviado(listaReabastoEnviado);
                    if (!resp) {
                        throw new Exception("Error al insertar lista de ReabastoEnviadoList ");
                    } else {
                        List<AlmacenControl> listaAlmacenControl = new ArrayList<>();
                        for (ReabastoInsumoExtended item : listaReabastoInsumo) {
                            AlmacenControl ctrl = almacenControlMapper.obtenerIdPuntosControl(reabasto.getIdEstructura(), item.getIdInsumo());
                            if (ctrl == null) {
                                ctrl = almacenControlMapper.obtenerIdPuntosControlServicio(reabasto.getIdEstructura(), item.getIdInsumo());
                            }

                            if (ctrl != null) {
                                ctrl.setSolicitud(Constantes.ESTATUS_INACTIVO);
                                listaAlmacenControl.add(ctrl);
                            }
                        }
                        resp = almacenControlMapper.actualizaListaAlmacenCtrl(listaAlmacenControl);
                        if (!resp) {
                            throw new Exception("Ocurrio un error al momento de actualizar los puntos de control.   ");
                        }
                    }
                }
            }
        } catch (Exception ex) {
            throw new Exception("Error al surtir la orden de Recepción Manual  " + ex.getMessage());
        }
        return resp;
    }

    @Override
    public List<ReabastoExtended> obtenerReabastoByFolio(String folio) throws Exception {
        try {
            return reabastoMapper.obtenerReabastoByFolio(folio);
        } catch (Exception e) {
            throw new Exception("Error obtener el reabasto por folio . " + e.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean insertarSolicitud(List<FolioAlternativoFolioMus> listFolioAlternativos, Reabasto reabasto, List<ReabastoInsumo> insumos, Integer idTipoAlmacen, boolean separaInsumos) throws Exception {
        boolean res = false;
        List<AlmacenControl> insumosPC = new ArrayList<>();
        try {
            //Consultar y generar Folio
            int tipoDoc;
            if (idTipoAlmacen.equals(Constantes.ALMACEN_FARMACIA)) {
                tipoDoc = TipoDocumento_Enum.ORDEN_COMPRA.getValue();
            } else {
                tipoDoc = TipoDocumento_Enum.ORDEN_REABASTO.getValue();
            }
            Folios folio = foliosMapper.obtenerPrefixPorDocument(tipoDoc);
            reabasto.setFolio(Comunes.generaFolio(folio));
            //Agregamos los datos para actualizar el Folio
            folio.setSecuencia(Comunes.separaFolio(reabasto.getFolio()));
            folio.setUpdateFecha(new Date());
            folio.setUpdateIdUsuario(reabasto.getInsertIdUsuario());
            res = foliosMapper.actualizaFolios(folio);
            if (res) {
                res = reabastoMapper.insertar(reabasto);
                if (res) {
                    if (reabasto.getIdTipoOrden().equals(Constantes.TIPO_ORDEN_NORMAL)) {
                        for (ReabastoInsumo ri : insumos) {
                            if (ri.getIdAlmacenPuntosControl() != null) {
                                AlmacenControl ac = new AlmacenControl();
                                ac.setIdAlmacenPuntosControl(ri.getIdAlmacenPuntosControl());
                                ac.setSolicitud(1);
                                ac.setUpdateFecha(new Date());
                                ac.setUpdateIdUsuario(ri.getInsertIdUsuario());
                                insumosPC.add(ac);
                            }
                        }
                        if (!insumosPC.isEmpty()) {
                            res = almacenControlMapper.actualizarMasivo(insumosPC);
                            if (!res) {
                                throw new Exception("Error al Actualizar los insumos en Puntos de Control");
                            }
                        }
                    }
                    if (res) {
                        for (FolioAlternativoFolioMus folioAlternativo : listFolioAlternativos) {
                            int tipoDocumento = TipoDocumento_Enum.IMSS_SAIF_SOLICITUD.getValue();
                            res = generaFolioSAIF(tipoDocumento, folioAlternativo);
                            if (res) {
                                folioAlternativo.setFolioMUS(reabasto.getFolio());
                                Estructura estruct = new Estructura();
                                estruct.setIdEstructura(reabasto.getIdEstructura());
                                estruct = estructuraMapper.obtenerEstructura(reabasto.getIdEstructura());
                                if (estruct.getIdEntidadHospitalaria() == null) {
                                    estruct.setIdEntidadHospitalaria("");
                                }
                                EntidadHospitalaria enti = entidadHospitalariaMapper.obtenerEntidadById(estruct.getIdEntidadHospitalaria());
                                if (enti != null) {
                                    String folioAlt = enti.getCodigoUmf() + "A4" + folioAlternativo.getFolioAlternativo(); //Solo falta la clave de identidad
                                    folioAlternativo.setFolioAlternativo(folioAlt);
                                }
                            } else {
                                throw new Exception("Error al actualizar el Folio Alternativo");
                            }

                            if (res) {
                                res = folioAlternativoFolioMusMapper.insertar(folioAlternativo);
                                if (!res) {
                                    throw new Exception("Error al insertar el FolioAlternativoFolioMus");
                                }
                            } else {
                                throw new Exception("Error al actualizar el Folio");
                            }
                        }
                        if (res) {
                            res = reabastoInsumoMapper.guardarReabastoInsumo(insumos);
                            if (!res) {
                                throw new Exception("Error al Insertar los folios alternativos");
                            }
                        }
                    } else {
                        throw new Exception("Error al Insertar los insumos de la solicitud ");
                    }
                } else {
                    throw new Exception("Error al Insertar el reabasto de la solicitud ");
                }
            } else {
                throw new Exception("Error al actualizar el Folio");
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return res;
    }

    private boolean actualizarPuntosControl(boolean res, Integer tipoOrden, List<AlmacenControl> insumosPC,
            List<ReabastoInsumo> insumosGpo) throws Exception {
        if (res) {
            if (tipoOrden.equals(Constantes.TIPO_ORDEN_NORMAL)) {
                for (ReabastoInsumo ri : insumosGpo) {
                    if (ri.getIdAlmacenPuntosControl() != null) {
                        AlmacenControl ac = new AlmacenControl();
                        ac.setIdAlmacenPuntosControl(ri.getIdAlmacenPuntosControl());
                        ac.setSolicitud(1);
                        ac.setUpdateFecha(new Date());
                        ac.setUpdateIdUsuario(ri.getInsertIdUsuario());
                        insumosPC.add(ac);
                    }
                }
                if (!insumosPC.isEmpty()) {
                    res = almacenControlMapper.actualizarMasivo(insumosPC);
                    if (!res) {
                        throw new Exception("Error al Actualizar los insumos en Puntos de Control");
                    }
                }
            }
        } else {
            throw new Exception("Error al Insertar los insumos de la solicitud ");
        }
        return res;
    }

    private boolean generaFolioSAIF(int tipoDocumento, FolioAlternativoFolioMus folioAlternativo) {
        boolean res;
        Folios folio = foliosMapper.obtenerPrefixPorDocument(tipoDocumento);
        folioAlternativo.setFolioAlternativo(Comunes.generaFolio(folio));
        //Agregamos los datos para actualizar el Folio
        folio.setSecuencia(Comunes.separaFolio(folioAlternativo.getFolioAlternativo()));
        folio.setUpdateFecha(new Date());
        folio.setUpdateIdUsuario(folioAlternativo.getInsertIdUsuario());
        res = foliosMapper.actualizaFolios(folio);
        return res;
    }

    @Override
    public Long obtenerBusquedaTotalSurtimientoLazy(String cadena, Reabasto reabasto, List<Integer> listEstatusReabasto, Integer idTipoAlmacen) throws Exception {
        try {
            return reabastoMapper.obtenerBusquedaTotalSurtimientoLazy(cadena, reabasto, listEstatusReabasto, idTipoAlmacen);
        } catch (Exception e) {
            throw new Exception("Error obtenerBusquedaTotalSurtimientoLazy. " + e.getMessage());
        }
    }

    @Override
    public List<ReabastoExtended> obtenerBusquedaSurtimientoLazy(String cadena, Reabasto reabasto, List<Integer> listEstatusReabasto, Integer idTipoAlmacen,
            int startingAt, int maxPerPage, String sortField, SortOrder sortOrder) throws Exception {
        try {
            String order = sortOrder == SortOrder.ASCENDING ? "asc" : sortOrder == SortOrder.DESCENDING ? "desc" : null;
            return reabastoMapper.obtenerBusquedaSurtimientoLazy(cadena, reabasto, listEstatusReabasto, idTipoAlmacen,
                    startingAt, maxPerPage, sortField, order);
        } catch (Exception e) {
            throw new Exception("Error obtenerBusquedaSurtimientoLazy. " + e.getMessage());
        }
    }

    @Override
    public ReabastoExtended obtenerReabastoExtendedPorID(String idReabasto) throws Exception {
        ReabastoExtended reabasto = new ReabastoExtended();
        try {
            reabasto = reabastoMapper.obtenerReabastoExtendedPorID(idReabasto);
        } catch (Exception ex) {
            throw new Exception("Error al obtener la busqueda de ReabastoExtended" + ex.getMessage());
        }
        return reabasto;
    }

    @Override
    public Long obtenerBusquedaIngresoTotalLazy(String cadena, String almacen, String almacenPadre, int tipoAlmacen, List<Integer> listEstatusReabasto) throws Exception {
        try {
            return reabastoMapper.obtenerBusquedaIngresoTotalLazy(cadena, almacen, almacenPadre, tipoAlmacen, listEstatusReabasto);
        } catch (Exception ex) {
            throw new Exception("Error al obtener la busqueda total ingreso ordenes" + ex.getMessage());
        }
    }

    @Override
    public List<ReabastoExtended> obtenerBusquedaIngresoLazy(String cadena, String almacen, String almacenPadre, int tipoAlmacen, List<Integer> listEstatusReabasto,
            int startingAt, int maxPerPage, String sortField, SortOrder sortOrder) throws Exception {
        List<ReabastoExtended> reabastoList = new ArrayList<>();
        try {
            String order = sortOrder == SortOrder.ASCENDING ? "asc" : sortOrder == SortOrder.DESCENDING ? "desc" : null;
            reabastoList = reabastoMapper.obtenerBusquedaIngresoLazy(cadena, almacen, almacenPadre, tipoAlmacen, listEstatusReabasto, startingAt, maxPerPage, sortField, order);
        } catch (Exception ex) {
            throw new Exception("Error al obtener la busqueda ingreso ordenes" + ex.getMessage());
        }
        return reabastoList;
    }

    @Override
    public ReabastoExtended obtenerReabastoByFolioAlternativo(String folioAlternativo) throws Exception {
        try {
            return reabastoMapper.obtenerReabastoByFolioAlternativo(folioAlternativo);
        } catch (Exception e) {
            throw new Exception("Error obtener el reabasto por folio . " + e.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean surtirReabastoRecepcionColectivo(Reabasto reabasto, List<ReabastoInsumoExtended> listaReabastoInsumo, List<ReabastoEnviado> listaReabEnviadoInsert, List<ReabastoEnviado> listaReabEnviadoUpdate, List<DetalleInsumoSiam> listaDetalleInsumoSiam, List<Inventario> inventarioCedimeUpdate, List<Inventario> inventarioCedimeInsert) throws Exception {
        boolean resp = false;
        try {
            resp = reabastoMapper.actualizar(reabasto);
            if (!resp) {
                throw new Exception("Error al actualizar el Reabasto ");
            } else {
                resp = reabastoInsumoMapper.actualizarListaReabastoInsumo(listaReabastoInsumo);
                if (!resp) {
                    throw new Exception("Error al actualizar el ReabastoInsumoList ");
                } else {
                    if (listaReabEnviadoInsert != null && !listaReabEnviadoInsert.isEmpty()) {
                        resp = reabastoEnviadoMapper.insertListReabatoEnviado(listaReabEnviadoInsert);
                    }
                    if (!resp) {
                        throw new Exception("Error al insertar lista de listaReabEnviadoInsert ");
                    } else {
                        if (listaReabEnviadoUpdate != null && !listaReabEnviadoUpdate.isEmpty()) {
                            resp = reabastoEnviadoMapper.actualizarListaReabastoEnviado(listaReabEnviadoUpdate);
                        }
                        if (!resp) {
                            throw new Exception("Error al actualizar lista de listaReabEnviadoUpdate ");
                        } else {
                            resp = detalleInsumoSiamMapper.actualizarLista(listaDetalleInsumoSiam);
                            if (!resp) {
                                throw new Exception("Error al actualizar el ReabastoInsumoList ");
                            } else {
                                if (inventarioCedimeUpdate != null && !inventarioCedimeUpdate.isEmpty()) {
                                    resp = inventarioMapper.actualizaInvByInvInsumoEstr(inventarioCedimeUpdate);
                                }
                                if (!resp) {
                                    throw new Exception("Error al actualizar inventarioCedime ");
                                } else {
                                    if (inventarioCedimeInsert != null && !inventarioCedimeInsert.isEmpty()) {
                                        resp = inventarioMapper.insertListInventarios(inventarioCedimeInsert);
                                    }
                                    if (!resp) {
                                        throw new Exception("Error al insertar inventarioCedime ");
                                    } else {
                                        List<AlmacenControl> listaAlmacenControl = new ArrayList<>();
                                        for (DetalleInsumoSiam item : listaDetalleInsumoSiam) {
                                            AlmacenControl ctrl = almacenControlMapper.obtenerIdPuntosControl(item.getIdEstructura(), item.getIdInsumo());
                                            ctrl.setSolicitud(Constantes.ESTATUS_INACTIVO);
                                            listaAlmacenControl.add(ctrl);
                                        }
                                        resp = almacenControlMapper.actualizaListaAlmacenCtrl(listaAlmacenControl);
                                        if (!resp) {
                                            throw new Exception("Ocurrio un error al momento de actualizar los puntos de control.   ");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            throw new Exception("Error al surtir la orden de Recepción Manual  " + ex.getMessage());
        }
        return resp;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean cancelarReabastoyReabastoInsumo(Reabasto reab, List<ReabastoInsumoExtended> reabIns) throws Exception {
        boolean res = false;
        List<AlmacenControl> insumosPC = new ArrayList<>();
        try {
            for (ReabastoInsumo ri : reabIns) {
                AlmacenControl ac = almacenControlMapper.obtenerIdPuntosControl(reab.getIdEstructura(), ri.getIdInsumo());
                ac.setSolicitud(Constantes.ESTATUS_INACTIVO);
                ac.setUpdateFecha(new Date());
                ac.setUpdateIdUsuario(ri.getInsertIdUsuario());
                ri.setIdEstatusReabasto(EstatusReabasto_Enum.CANCELADA.getValue());
                insumosPC.add(ac);
            }
            res = almacenControlMapper.actualizarMasivo(insumosPC);
            if (!res) {
                throw new Exception("Error al actualizar puntos de controls");
            } else {
                res = reabastoInsumoMapper.actualizarListaReabastoInsumo(reabIns);
                if (res) {
                    res = reabastoMapper.actualizar(reab);
                    if (!res) {
                        throw new Exception("Error al cancelar Reabasto");
                    }  else {
                        OrdenCompra oc = new OrdenCompra();
                        oc.setIdReabasto(reab.getIdReabasto());
                        oc = ordenCompraMapper.obtener(oc);
                        if (!res) {
                            throw new Exception("Error al consultar Orden de compra asociada al Reabasto. ");
                        } else {
                            if (oc != null) {
                                oc.setIdEstatusOrdenCompra(EstatusOrdenesCompra_Enum.CANCELADA.getIdEstatusOrdenCompra());
                                oc.setUpdateFecha(new java.util.Date());
                                oc.setUpdateIdUsuario(reab.getUpdateIdUsuario());
                                res = ordenCompraMapper.actualizar(oc);
                                if (!res) {
                                    throw new Exception("Error al cancelar Orden Reabasto asociada. ");
                    }
                            }
                        }
                        
                    }
                } else {
                    throw new Exception("Error al cancelar Reabasto Insumos");
                }
            }

        } catch (Exception ex) {
            throw new Exception("Error al cancelar la Solicitud" + ex.getMessage());
        }
        return res;
    }
}
