package mx.mc.service;

import java.util.ArrayList;
import java.util.List;
import mx.mc.enums.TipoDocumento_Enum;
import mx.mc.mapper.FoliosMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.OrdenCompraDetalleMapper;
import mx.mc.mapper.OrdenCompraMapper;
import mx.mc.mapper.ReabastoInsumoMapper;
import mx.mc.mapper.ReabastoMapper;
import mx.mc.model.Folios;
import mx.mc.model.OrdenCompra;
import mx.mc.model.OrdenCompraDetalle;
import mx.mc.model.OrdenCompraDetalleExtended;
import mx.mc.model.OrdenCompra_Extended;
import mx.mc.model.Reabasto;
import mx.mc.model.ReabastoInsumo;
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
public class OrdenCompraServiceImplements extends GenericCrudServiceImpl<OrdenCompra, String> implements OrdenCompraService{
    
    @Autowired
    private OrdenCompraMapper ordenCompraMapper;
    
    @Autowired 
    OrdenCompraDetalleMapper ordenCompraDetalleMapper;
    
    @Autowired
    FoliosMapper foliosMapper;
    
    @Autowired
    ReabastoMapper reabastoMapper;
    
    @Autowired
    ReabastoInsumoMapper reabastoInsumoMapper;
    
    @Autowired
    public OrdenCompraServiceImplements(GenericCrudMapper<OrdenCompra, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
    
    @Override
    public List<OrdenCompra_Extended> obtenerUltimosRegistrosOrdenCompra(int numeroRegistros) throws Exception {
        try {
                return ordenCompraMapper
                        .obtenerUltimosRegistrosOrdenCompra(numeroRegistros);
        } catch (Exception ex) {
            throw new Exception("Error obtener. " + ex.getMessage());
        }
    }

    @Override
    public List<OrdenCompra_Extended> obtenerRegistrosPorCriterioDeBusqueda(String criterioBusqueda, int numRegistros) throws Exception {
        try {
                List<OrdenCompra_Extended> listaOrdenes = ordenCompraMapper
                        .obtenerRegistrosPorCriterioDeBusqueda(criterioBusqueda,numRegistros);
                if (listaOrdenes == null) {
                    listaOrdenes = new ArrayList<>();
                }
                return listaOrdenes;
        } catch (Exception ex) {
            throw new Exception("Error obtener. " + ex.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean registrarOrdenCompra(OrdenCompra_Extended ordenCompra, List<OrdenCompraDetalleExtended> listOrdenDetalle,
                   Folios folioOrdenCompra, Reabasto reabasto, List<ReabastoInsumo> listReabastoInsumo, Folios folioReabasto,
                   boolean registrarReabasto) throws Exception  {
        try {
            boolean resp = true;
            
            if (registrarReabasto) {
                Integer tipoDoc = TipoDocumento_Enum.ORDEN_REABASTO.getValue();
                Folios folio = foliosMapper.obtenerPrefixPorDocument(tipoDoc);
                reabasto.setFolio(Comunes.generaFolio(folio));
                folio.setSecuencia(Comunes.separaFolio(reabasto.getFolio()));
                folio.setUpdateFecha(new java.util.Date());
                folio.setUpdateIdUsuario(reabasto.getInsertIdUsuario());
                
                resp = foliosMapper.actualizaFolios(folio);     //                    resp = foliosMapper.actualizaFolios(folioReabasto);
                
                if (!resp) {
                    throw new Exception("Error al Actualizar folio del reabasto asociado a la orden de compra.");

                } else {
                    resp = reabastoMapper.insertar(reabasto);
                    if (!resp) {
                        throw new Exception("Error al registrar el Reabasto relacionado a la orden de compra.");

                    } else {
                        if (!listReabastoInsumo.isEmpty()) {
                            resp = reabastoInsumoMapper.insertarListReabastoInsumos(listReabastoInsumo);
                            if (!resp) {
                                throw new Exception("Error al registrar el Detalle del Reabasto relacionado a la orden de compra.");

                            }
                        } else {
                            throw new Exception("Error al registrar el Detalle del Reabasto vacío relacionado a la orden de compra.");
                        }
                    }
                }
            }
            
            ordenCompra.setIdReabasto(reabasto.getIdReabasto());
            Integer tipoDoc = TipoDocumento_Enum.ORDEN_COMPRA.getValue();
            Folios folio = foliosMapper.obtenerPrefixPorDocument(tipoDoc);
            ordenCompra.setFolioOrdenCompra(Comunes.generaFolio(folio));
            folio.setSecuencia(Comunes.separaFolio(ordenCompra.getFolioOrdenCompra()));
            
            resp = foliosMapper.actualizaFolios(folio); //                        resp = foliosMapper.actualizaFolios(folioOrdenCompra);
            
            if(!resp) {
                throw new Exception("Error al Actualizar folio de la orden de compra.");
                
            } else {
                resp = ordenCompraMapper.insertar(ordenCompra);
                
                if(!resp) {
                    throw new Exception("Error al registrar la orden de compra.");

                } else {
                    resp = ordenCompraDetalleMapper.insertarListOrdenDetalleCompra(listOrdenDetalle);
                    
                    if(!resp) {
                        throw new Exception("Error al registrar el detalle de la orden de compra.");
                    }
                }
            }
                        
            return resp;
            
        } catch(Exception ex) {
            throw new Exception("Error al registrar orden de compra. " + ex.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean registrarReabastoOrdenCompra(OrdenCompra_Extended ordenCompra, List<OrdenCompraDetalleExtended> nuevaListaDetalle,
            Reabasto reabasto, List<ReabastoInsumo> listReabastoInsumo, Folios folioReabasto) throws Exception {

        try {
            boolean resp;
            ordenCompra.setIdReabasto(reabasto.getIdReabasto());

            Integer tipoDoc = TipoDocumento_Enum.ORDEN_REABASTO.getValue();
            Folios folio = foliosMapper.obtenerPrefixPorDocument(tipoDoc);
            reabasto.setFolio(Comunes.generaFolio(folio));
            folio.setSecuencia(Comunes.separaFolio(reabasto.getFolio()));
            folio.setUpdateFecha(new java.util.Date());
            folio.setUpdateIdUsuario(reabasto.getInsertIdUsuario());

            resp = foliosMapper.actualizaFolios(folio);     //                    resp = foliosMapper.actualizaFolios(folioReabasto);
            if (!resp) {
                throw new Exception("Error al Actualizar folio del reabasto asociado a la orden de compra.");

            } else {
                resp = reabastoMapper.insertar(reabasto);
                if (!resp) {
                    throw new Exception("Error al registrar el Reabasto relacionado a la orden de compra.");

                } else {
                    if (listReabastoInsumo.isEmpty()) {
                        throw new Exception("Error al registrar el Detalle del Reabasto vacío relacionado a la orden de compra.");

                    } else {
                        resp = reabastoInsumoMapper.insertarListReabastoInsumos(listReabastoInsumo);
                        if (!resp) {
                            throw new Exception("Error al registrar el Detalle del Reabasto relacionado a la orden de compra.");

                        } else {
                            ordenCompraMapper.actualizarOrdenCompra(ordenCompra);
                            if (!resp) {
                                throw new Exception("Error al registrar la orden de compra.");

                            } else {
                                if (nuevaListaDetalle.isEmpty()) {
                                    throw new Exception("Error al registrar el detalle vació de la orden de compra.");

                                } else {
                                    OrdenCompraDetalle oc;
                                    for (OrdenCompraDetalle item : nuevaListaDetalle) {
                                        OrdenCompraDetalle temp = new OrdenCompraDetalle();
                                        temp.setIdOrdenCompra(item.getIdOrdenCompra());
                                        temp.setIdInsumo(item.getIdInsumo());
                                        oc = ordenCompraDetalleMapper.obtener(temp);
                                        if (oc == null) {
                                            resp = ordenCompraDetalleMapper.insertar(item);
                                            if (!resp) {
                                                throw new Exception("Error al registrar el detalle de la orden de compra.");

                                            }

                                        } else {
                                            //resp = ordenCompraDetalleMapper.insertarListOrdenDetalleCompra(nuevaListaDetalle);
                                            resp = ordenCompraDetalleMapper.actualizar(item);
                                            if (!resp) {
                                                throw new Exception("Error al actualizar el detalle de la orden de compra.");

                                            }

                                        }
                                    }

                                    if (!resp) {
                                        throw new Exception("Error al registrar el detalle de la orden de compra.");

                                    }
                                }
                            }
                        }
                    }
                }
            }

            return resp;
        } catch (Exception ex) {
            throw new Exception("Error al registrar el reabasto de la orden de compra " + ex.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public OrdenCompra_Extended obtenerOrdenCompra(String idOrdenCompra) throws Exception {
        try {
            return ordenCompraMapper.obtenerOrdenCompra(idOrdenCompra);
        } catch(Exception ex) {
            throw new Exception("Error al obtener la orden de compra " + ex.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean actualizarOrdenCompra(OrdenCompra_Extended ordenCompra, List<OrdenCompraDetalleExtended> listOrdenDetalle) throws Exception {
        try {
            boolean resp = ordenCompraMapper.actualizarOrdenCompra(ordenCompra);
            if(!listOrdenDetalle.isEmpty()) {
                resp = ordenCompraDetalleMapper.insertarListOrdenDetalleCompra(listOrdenDetalle);
            }
            return resp;
        } catch(Exception ex) {
            throw new Exception("Error al actualizar la orden de compra " + ex.getMessage());
        }
    }

    @Override
    public List<OrdenCompra_Extended> obtenerOrdenesCompraLazzy(String cadena, String idEstructura, int startingAt, int maxPerPage, String sortField, SortOrder sortOrder) throws Exception {
          List<OrdenCompra_Extended> ordenCompraList = new ArrayList<>();
        try {
            String order = sortOrder == SortOrder.ASCENDING ? "asc" : sortOrder == SortOrder.DESCENDING ? "desc" : null;
            ordenCompraList = ordenCompraMapper.obtenerOrdenesCompraLazzy(cadena, idEstructura, startingAt, maxPerPage, sortField, order);
        } catch (Exception ex) {
            throw new Exception("Error al obtener la busqueda" + ex.getMessage());
        }
        return ordenCompraList;
    }

    @Override
    public Long obtenerBusquedaTotalLazy(String cadena, String idEstructura) throws Exception {
         try {
            return ordenCompraMapper.obtenerBusquedaTotalLazy(cadena, idEstructura);
        } catch (Exception ex) {
            throw new Exception("Error al obtener la busqueda" + ex.getMessage());
        }
    }
    
}
