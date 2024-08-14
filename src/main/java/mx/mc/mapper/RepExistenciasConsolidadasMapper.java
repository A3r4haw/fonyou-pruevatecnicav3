package mx.mc.mapper;

import java.util.List;
import mx.mc.model.Medicamento_Extended;
import org.apache.ibatis.annotations.Param;




/**
 * 
 * @author olozada
 *
 */
public interface RepExistenciasConsolidadasMapper { 
	
    List<Medicamento_Extended> obtenerReporteExist_Consolidadas(
            @Param("medicamento_Extended") Medicamento_Extended medicamento_Extended,
            @Param("startingAt") int startingAt, @Param("maxPerPage") int maxPerPage);
    
    Long obtenerTotalReporteExist_Consolidadas(@Param("medicamento_Extended") Medicamento_Extended medicamento_Extended);
    
    List<Medicamento_Extended> obtenerReporteEntregaExist_Consolidadas(
            @Param("medicamento_Extended") Medicamento_Extended medicamento_Extended,
            @Param("startingAt") int startingAt, @Param("maxPerPage") int maxPerPage);
    
    Long obtenerTotalReporteEntregaExist_Consolidadas(@Param("medicamento_Extended") Medicamento_Extended medicamento_Extended);
    

}
