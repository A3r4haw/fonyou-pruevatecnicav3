/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.List;
import mx.mc.dto.MedicamentoDTO;
import mx.mc.model.Hipersensibilidad;
import mx.mc.model.HipersensibilidadExtended;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author gcruz
 */
public interface HipersensibilidadMapper extends GenericCrudMapper<Hipersensibilidad, String> {
    
    List<HipersensibilidadExtended> obtenerReaccionesHipersensibilidad(@Param("cadena")String cadenaBusqueda, @Param("startingAt") int startingAt, 
                @Param("maxPerPage") int maxPerPage, @Param("sortField") String sortField, @Param("sortOrder") String sortOrder);
    
    Long obtenerTotalReacionesHipersensibilidad(@Param("cadena") String cadenaBusqueda);
    
    boolean insertarReaccionHipersensibilidad(HipersensibilidadExtended hipersensibilidadExtended);
    
    HipersensibilidadExtended obtenerHipersensibilidadPorIdHiper(@Param("idHipersensibilidad") String idHipersensibilidad);
    
    boolean actualizarReaccionHipersensibilidad(HipersensibilidadExtended hipersensibilidadExtended);
    
    List<HipersensibilidadExtended> obtenerListaReacHiperPorIdPaciente(@Param("idPaciente") String idPaciente, @Param("listaMedicamentos") List<MedicamentoDTO> listaMedicamentos);
    
    boolean eliminarHipersensibilidad(@Param("idHipersensibilidad") String idHipersensibilidad);
    
}
