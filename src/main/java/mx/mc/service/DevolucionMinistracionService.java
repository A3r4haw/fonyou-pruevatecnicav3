/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.model.DevolucionMinistracion;
import mx.mc.model.DevolucionMinistracionExtended;
import mx.mc.model.DevolucionMinistracionDetalle;
import mx.mc.model.DevolucionMinistracionDetalleExtended;
import mx.mc.model.Paciente;
import org.primefaces.model.SortOrder;

/**
 *
 * @author bbautista
 */
public interface DevolucionMinistracionService extends GenericCrudService<DevolucionMinistracion,String> {
    public boolean insertDevolucion(DevolucionMinistracion devolucion, List<DevolucionMinistracionDetalle> devolucionList) throws Exception;
    public List<DevolucionMinistracion> obtenerListaDevolucion() throws Exception;
    public List<DevolucionMinistracionExtended> obtenerListaDevolucionExtended(
            DevolucionMinistracionExtended devolucionMinistracionExtended) throws Exception;
    public List<DevolucionMinistracion> obtenerBusquedaDevolucion(String cadena) throws Exception;
    public boolean actualizaDevolucion(DevolucionMinistracion devolucion, List<DevolucionMinistracionDetalle> devolucionList)throws Exception;
    
    public List<DevolucionMinistracionExtended> obtenerListaDevMedMinistracion(String idEstructura, String cadenaBusqueda, int startingAt, int maxPerPage, List<String> tipo) throws Exception;
    public boolean insertDevolucionExt(DevolucionMinistracionExtended devolucion, List<DevolucionMinistracionDetalleExtended> devolucionList) throws Exception;    
    public boolean actualizaDevolucionExt(DevolucionMinistracionExtended devolucionExt, List<DevolucionMinistracionDetalleExtended> devolucionExtList)throws Exception;
    public DevolucionMinistracionExtended obtenerByFolioSurtimientoForDev(String folioSurtimiento, Paciente pacienteDev, String filter) throws Exception;
    public List<DevolucionMinistracionExtended> obtenerByFolioSurtimientoForDevList(String folioSurtimiento, Paciente pacienteDev, String filter) throws Exception;
    
    public List<DevolucionMinistracionExtended> obtenerBusquedaDevolucionLazy(String cadena, String almacen, int startingAt, int maxPerPage, String sortField, SortOrder sortOrder) throws Exception;
    public Long  obtenerBusquedaDevolucionTotalLazy(String cadena, String almacen) throws Exception;
}
