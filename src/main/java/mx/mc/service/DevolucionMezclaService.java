/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.model.DevolucionMezcla;
import mx.mc.model.DevolucionMezclaDetalle;
import mx.mc.model.DevolucionMezclaExtended;
import mx.mc.model.SurtimientoInsumo_Extend;
import org.primefaces.model.SortOrder;

/**
 *
 * @author bbautista
 */
public interface DevolucionMezclaService extends GenericCrudService<DevolucionMezcla, String> {

    public boolean insertarDevolucionMezcla(DevolucionMezcla devolucion, List<DevolucionMezclaDetalle> detalleMezclaList, Integer tipoDocumento) throws Exception;

    public List<SurtimientoInsumo_Extend> detalleDevolucionMezcla(String idDevolucionMezcla) throws Exception;

    public DevolucionMezclaExtended obtenerDevolucionMezclaById(String idDevolucionMezcla) throws Exception;

    public DevolucionMezclaExtended obtenerSolucionByIdSolucion(String idSolucion) throws Exception;

    public List<DevolucionMezclaExtended> obtenerBusquedaLazy(String cadena, String almacen, Integer idEstatusSolucion, int startingAt, int maxPerPage, String sortField, SortOrder sortOrder) throws Exception;
    
    public Long obtenerBusquedaTotalLazy(String cadena, String almacen) throws Exception;
    
}
