/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.model.MedicamentoConcomitante;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.Reaccion;
import mx.mc.model.ReaccionExtend;
import org.primefaces.model.SortOrder;

/**
 *
 * @author bbautista
 */
public interface ReaccionService extends GenericCrudService<Reaccion, String>{
    
    List<ReaccionExtend> obtenerReaccionesListLazy(ParamBusquedaReporte paramBusquedaReporte, Integer estatusReaccionAdv,
            int startingAt, int maxPerPage, String sortField,SortOrder sortOrder) throws Exception;
    
    Long obtenerTotalReaccionesListLazy(ParamBusquedaReporte paramBusquedaReporte, Integer estatusReaccionAdv) throws Exception;
    
    boolean insertarReaccion(Reaccion reaccion,List<MedicamentoConcomitante> insumosList) throws Exception;
    
    boolean actualizarReaccion(Reaccion reaccion,List<MedicamentoConcomitante> insumosList) throws Exception;
    
    List<ReaccionExtend> obtenerReaccionesByIdPacienteIdInsumos(String idPaciente, List<String> listaInsumos) throws Exception;
    
}
