package mx.mc.service;

import java.util.List;
import mx.mc.model.Folios;
import mx.mc.model.OrdenCompra;
import mx.mc.model.OrdenCompraDetalleExtended;
import mx.mc.model.OrdenCompra_Extended;
import mx.mc.model.Reabasto;
import mx.mc.model.ReabastoInsumo;
import org.primefaces.model.SortOrder;

/**
 * @author AORTIZ
 */
public interface OrdenCompraService extends GenericCrudService<OrdenCompra, String>{

    public List<OrdenCompra_Extended> obtenerUltimosRegistrosOrdenCompra(
            int numeroRegistros) throws Exception;
    
    public List<OrdenCompra_Extended> obtenerRegistrosPorCriterioDeBusqueda(
            String criterioBusqueda, int numRegistros) throws Exception;
    
    public boolean registrarOrdenCompra(OrdenCompra_Extended ordenCompra, List<OrdenCompraDetalleExtended> listOrdenDetalle,
            Folios folioOrdenCompra, Reabasto reabasto, List<ReabastoInsumo> listReabastoInsumo, Folios folioReabasto,
            boolean registrarReabasto) throws Exception;        
    
    public boolean registrarReabastoOrdenCompra(OrdenCompra_Extended ordenCompra, List<OrdenCompraDetalleExtended> nuevaListaDetalle,
            Reabasto reabasto, List<ReabastoInsumo> listReabastoInsumo, Folios folioReabasto) throws Exception;
    
    public OrdenCompra_Extended obtenerOrdenCompra(String idOrdenCompra) throws Exception;
    
    boolean actualizarOrdenCompra(OrdenCompra_Extended ordenCompra, List<OrdenCompraDetalleExtended> listOrdenDetalle) throws Exception;
    
    public List<OrdenCompra_Extended> obtenerOrdenesCompraLazzy(String cadena, String idEstructura, int startingAt, int maxPerPage, String sortField, SortOrder sortOrder) throws Exception;
    
    public Long obtenerBusquedaTotalLazy(String cadena, String idEstructura) throws Exception;
}
