package mx.mc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import mx.mc.model.ReabastoEnviado;
import mx.mc.model.ReabastoEnviadoExtended;
import mx.mc.model.ReabastoInsumo;

/**
 *
 * @author AORTIZ
 */
public interface ReabastoEnviadoMapper extends GenericCrudMapper<ReabastoEnviado, String> {
    public int insertarDetalleInsumos(@Param("listaDetalle") List<ReabastoEnviado> listaDetalle);
    
	List<ReabastoEnviadoExtended> obtenerListaReabastoEnviado(@Param("idReabastoInsumo") String idReabastoInsumo,
                @Param("listEstatusReabasto") List<Integer> listEstatusReabasto);
        
        List<ReabastoEnviado> obtenerReabastoEnviadoByListaReabastoInsumo(List<ReabastoInsumo> listReabastoEnviado);
                
	boolean actualizarListaReabastoEnviado(List<ReabastoEnviado> listReabastoEnviado);
        
        boolean actualizarListaReabastoEnviadoByIdReabastoInsumo(List<ReabastoEnviado> listReabastoEnviado);
        
        boolean eliminarListaReabastoEnviado(@Param("idReabasto") String idReabasto);
        
        boolean insertListReabatoEnviado(@Param("listReabastoEnviado") List<ReabastoEnviado> listReabastoEnviado);
        
        ReabastoEnviadoExtended obtenerInventarioPorClveInstEstructuraYLote(ReabastoEnviadoExtended reabastoEnviadoExtended);
        
        List<ReabastoEnviadoExtended> obtenerListaReabastoEnviadoInv(@Param("idReabastoInsumo") String idReabastoInsumo,
                @Param("idEstatusReabasto") int idEstatusReabasto, @Param("idEstructura") String idEstructura);
        
        boolean eliminarListReabastoEnviado(List<ReabastoEnviado> listReabastoEnviado);
        
        List<ReabastoEnviadoExtended> obtenerReabastoEnviadoPorIdEstructuraIdInsumoIdInventario (
                @Param("idEstructura") String idEstructura , @Param("idInsumo") String idInsumo, @Param("idInventario") String idInventario );
       
}
