/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.model.AlmacenControl;
import mx.mc.model.DetalleInsumoSiam;
import mx.mc.model.FolioAlternativoFolioMus;
import mx.mc.model.Inventario;
import mx.mc.model.MovimientoInventario;
import mx.mc.model.Reabasto;
import mx.mc.model.ReabastoEnviado;
import mx.mc.model.ReabastoExtended;
import mx.mc.model.ReabastoInsumo;
import mx.mc.model.ReabastoInsumoExtended;
import mx.mc.model.Usuario;
import org.primefaces.model.SortOrder;

/**
 *
 * @author bbautista
 */
public interface ReabastoService extends GenericCrudService<Reabasto,String> {
    public List<ReabastoExtended> obtenerReabastoExtends(Reabasto reabasto, 
    		List<Integer> listEstatusReabasto
            , Integer idTipoAlmacen) throws Exception;
    
    public boolean surtirOrdenReabasto(Reabasto reabasto, 
            List<ReabastoInsumoExtended> listaReabastoInsumo) throws Exception;
    
    public boolean surtirOrdenReabastoProveedorFarmacia(
            Reabasto reabasto, List<ReabastoInsumoExtended> listaReabastoInsumo , 
            String idUsuario) throws Exception;
        
    public List<ReabastoExtended> obtenerRegistrosPorCriterioDeBusqueda(
            String textoBusqueda, int numRegistros, List<Integer> listEstatusReabasto
            , String idEstructuraPadre, String idEstructura, Integer idTipoAlmacen) throws Exception;
        
    public List<ReabastoExtended> obtenerRegistrosPorCriterioDeBusquedas(
            String criterioBusqueda, int numRegistros, List<Integer> listEstatusReabasto
            , String idEstructuraPadre, String idEstructura, Integer idTipoAlmacen) throws Exception;
    
    public boolean guardarOrdenReabasto(Reabasto reabasto, 
            List<ReabastoInsumoExtended> listaReabastoInsumo) throws Exception;
    
    public List<Reabasto> obtenerBusqueda(String cadena, String almacen) throws Exception;
    
    public Reabasto obtenerReabastoPorID(String cadena) throws Exception;
    public boolean eliminarPorId(String idReabasto,String folio , AlmacenControl almacenControl) throws Exception;
    public boolean cancelarSolicitud(Reabasto soli, List<ReabastoInsumo> insumos,Integer idTipoAlmacen) throws Exception;
    public boolean insertarSolicitud(Reabasto reabasto, List<ReabastoInsumo> insumos,Integer idTipoAlmacen,boolean colectivoXServicio) throws Exception;
    public boolean actualizarSolicitud(Reabasto reabasto, List<ReabastoInsumo> insumosUpdate) throws Exception;
    
    public List<ReabastoExtended> obtenerReabastoExtendsAdmin(Reabasto reabasto, 
    		List<Integer> listEstatusReabasto
            , Integer idTipoAlmacen) throws Exception;
    
    public Reabasto getReabastoByFolio(String folio) throws Exception;
    
    public boolean eliminarOrdenReabasto(
            Reabasto reabasto, List<ReabastoInsumo> insumosInsert) throws Exception;
    public List<Reabasto> obtenerTransferencias(String cadena,String idEstructura) throws Exception;
    public boolean insertarSolicitudTransfer(Reabasto reabasto, List<ReabastoInsumoExtended> insumos) throws Exception;

    public boolean guardarReabastoColectivo(Reabasto reabasto, List<ReabastoInsumoExtended> insumos, boolean editarOrden, List<ReabastoInsumoExtended> insumosEliminar)throws Exception;
    
    public boolean surtirOrdenReabastoColectivo(Reabasto reabasto, List<ReabastoInsumoExtended> listaReabastoInsumo) throws Exception;
    
     public boolean cancelarOrdenReabastoColectivo(Reabasto reabasto, ReabastoInsumo reabastoInsumo ,
            List<ReabastoEnviado> listaReabastoEnviado ,
            List<Inventario> listaInventarios ,
            List<MovimientoInventario> listaMovimientos
     ) throws Exception;
     //Solicitud
    public List<Reabasto> obtenerBusquedaLazy(String cadena, String almacen, int startingAt, int maxPerPage, String sortField, SortOrder sortOrder) throws Exception;
    public Long obtenerBusquedaTotalLazy(String cadena, String almacen) throws Exception;
    //Recepcion Reabasto
    public List<ReabastoExtended> obtenerBusquedaRecepcionLazy(String cadena, String almacen, String almacenPadre,int tipoAlmacen, int startingAt, int maxPerPage, String sortField, SortOrder sortOrder) throws Exception;
    public Long obtenerBusquedaRecepcionTotalLazy(String cadena, String almacen, String almacenPadre,int tipoAlmacen) throws Exception;
    
    public boolean insertForRefill1(boolean existUsuario,Usuario user,Reabasto reabasto, List<ReabastoInsumoExtended> reabastoExtendList, 
            List<ReabastoEnviado> reabastoEnviadoList,
            List<Inventario> inventarioList, List<MovimientoInventario> movInventarioList,List<Inventario>lisInventarioInsert)throws Exception;
    
    public boolean insertForRefillInv(boolean existUsuario,Usuario user,List<Inventario> inventarioList,
            List<MovimientoInventario> movInventarioList,List<Inventario>lisInventarioInsert)throws Exception;
    
    public boolean surtirReabastoProveedorAutomatico(Reabasto reabasto, List<ReabastoInsumoExtended> listaReabastoInsumo,List<ReabastoInsumo> listaReabastoInsumoAgreg, List<ReabastoEnviado> listaReabastoEnviado) throws Exception;

    public List<ReabastoExtended> obtenerReabastoByFolio(String folio) throws Exception;
    
    public boolean insertarSolicitud(List<FolioAlternativoFolioMus> listFolioAlternativos, Reabasto reabasto, List<ReabastoInsumo> insumos, Integer idTipoAlmacen, boolean separaInsumos) throws Exception;       
    
    public Long obtenerBusquedaTotalSurtimientoLazy(String cadena, Reabasto reabasto, List<Integer> listEstatusReabasto, Integer idTipoAlmacen) throws Exception;
    
    public List<ReabastoExtended> obtenerBusquedaSurtimientoLazy(String cadena, Reabasto reabasto, List<Integer> listEstatusReabasto, Integer idTipoAlmacen,
                                    int startingAt, int maxPerPage, String sortField, SortOrder sortOrder) throws Exception;
    
    public ReabastoExtended obtenerReabastoExtendedPorID(String idReabasto) throws Exception;
    
    public Long obtenerBusquedaIngresoTotalLazy(String cadena, String almacen, String almacenPadre,int tipoAlmacen, List<Integer> listEstatusReabasto) throws Exception;
    
    public List<ReabastoExtended> obtenerBusquedaIngresoLazy(String cadena, String almacen, String almacenPadre,int tipoAlmacen, List<Integer> listEstatusReabasto, int startingAt, int maxPerPage, String sortField, SortOrder sortOrder) throws Exception;
    
    public ReabastoExtended obtenerReabastoByFolioAlternativo(String folioAlternativo) throws Exception;
        
    public boolean surtirReabastoRecepcionColectivo(Reabasto reabasto, List<ReabastoInsumoExtended> listaReabastoInsumo, List<ReabastoEnviado> listaReabEnviadoInsert, List<ReabastoEnviado> listaReabEnviadoUpdate, List<DetalleInsumoSiam> listaDetalleInsumoSiam, List<Inventario> inventarioCedimeUpdate, List<Inventario> inventarioCedimeInsert) throws Exception;
    
    public boolean cancelarReabastoyReabastoInsumo(Reabasto soli, List<ReabastoInsumoExtended> insumos) throws Exception;
    
}
