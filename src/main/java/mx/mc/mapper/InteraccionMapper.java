/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.List;
import mx.mc.dto.MedicamentoDTO;
import mx.mc.model.Interaccion;
import mx.mc.model.InteraccionExtended;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author gcruz
 */
public interface InteraccionMapper extends GenericCrudMapper<Interaccion, String> {
    
    List<InteraccionExtended> obtenerInteracciones(@Param("cadena")String cadenaBusqueda, @Param("tipoInteraccion")int tipoInteraccion, 
                                                            @Param("startingAt") int startingAt, @Param("maxPerPage") int maxPerPage
                                                            , @Param("sortField") String sortField,@Param("sortOrder") String sortOrder);
    
    Long obtenerTotalInteracciones(@Param("cadena")String cadenaBusqueda, @Param("tipoInteraccion")int tipoInteraccion);
    
    Boolean insertarInteraccion(InteraccionExtended interaccion);
    
    Integer obtenerSiguienteIdInteraccion();
    
    InteraccionExtended buscarInteraccion(InteraccionExtended interaccionExtended);
    
    Boolean eliminarInteraccionPorId(@Param("idInteraccion") Integer idInteraccion);
    
    List<InteraccionExtended> obtenerAlertaInteraccion(@Param("pacienteNumero") String pacienteNumero);
    
    InteraccionExtended obtenerInteraccionById(@Param("idInteraccion") Integer idInteraccion);
    
    Boolean actualizarInteraccion(InteraccionExtended interaccion);
    
    List<InteraccionExtended> obtenerInteraccionesClavesMedicamento(@Param("listaMedicamentos") List<MedicamentoDTO> listaMedicamentos);
    
    List<MedicamentoDTO> obtenerListaMedicamentosPrescrpByPacienteNumero(@Param("pacienteNumero") String pacienteNumero, @Param("tipoInsumo")Integer tipoInsumo);
}
