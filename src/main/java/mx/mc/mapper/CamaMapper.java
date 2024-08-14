package mx.mc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import mx.mc.model.Cama;
import mx.mc.model.CamaExtended;
import org.primefaces.model.SortOrder;

/**
 * 
 * @author gcruz
 *
 */
public interface CamaMapper extends GenericCrudMapper<Cama, String> {
	
	List<CamaExtended> obtenerCamasByServicio(@Param("idEstructura") String idEstructura);
        
	Cama obtenerCama(@Param("idCama") String idCama);
        
        List<CamaExtended> obtenerCamaByEstructuraAndEstatus(
                @Param("idEstructura") String idEstructura , 
        @Param("listEstatusCama") List<Integer> listEstatusCama);
        
        boolean actualizarCamaLiberada(
                @Param("idPacienteServicio") String idPacienteServicio , 
                @Param("idEstatusCama") Integer idEstatusCama);
        Cama obtenerCamaPorNombre(Cama cama);
        
        CamaExtended obtenerCamaNombreEstructura(@Param("idPaciente") String idPaciente);
        
        Cama obterCamaPorNombreYEstructura(@Param("nombreCama") String nombreCama, 
                @Param("idEstructura") String idEstructura);
        
        List<CamaExtended> obtenerServicioCamas(@Param("idEntidadHosp")  String idEntidadHosp
                ,@Param("startingAt") int startingAt
                ,@Param("maxPerPage")  int maxPerPage
                ,@Param("valueServ")  String servicio
                ,@Param("valueRoom")  String cama
                ,@Param("sortField")  String sortField
                ,@Param("sortOrder")  String sortOrder);
        
        Long obtenerTotalServicioCamas(@Param("idEntidadHosp") String idEntidadHosp,@Param("valueServ") String servicio,@Param("valueRoom") String cama);
}
