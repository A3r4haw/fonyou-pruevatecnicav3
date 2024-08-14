/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.ViaAdministracion;
import org.primefaces.model.SortOrder;
/**
 *
 * @author bbautista
 */
public interface ViaAdministracionService extends GenericCrudService<ViaAdministracion,String> {
      public List<ViaAdministracion> obtenerTodo () throws Exception;  
      public ViaAdministracion obtenerPorId(Integer idViaAdministracion) throws Exception;
      public Integer obtenerSiguienteId() throws Exception;
      
      public String validaNombreExistenteVia(String nombreViaAdministracion) throws Exception;
      
      public List<ViaAdministracion> obtenerBusquedaViaAdministraciones(ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage
    ,String sortField,SortOrder sortOrder) throws Exception;
      
      public Long obtenerTotalBusquedaViaAdministraciones(ParamBusquedaReporte paramBusquedaReporte) throws Exception;
}
