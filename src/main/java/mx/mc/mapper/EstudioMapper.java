package mx.mc.mapper;

import java.util.List;
import mx.mc.model.Estudio;
import mx.mc.model.ParamBusquedaReporte;
import org.apache.ibatis.annotations.Param;

public interface EstudioMapper extends GenericCrudMapper<Estudio, String> {
    List<Estudio> obtenerBusquedaEstudio(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte,
            @Param("tipoEstudio") int tipoEstudio, @Param("startingAt") int startingAt, @Param("maxPerPage") int maxPerPage
            ,@Param("sortField") String sortField, @Param("sortOrder") String sortOrder); 
    
    Long obtenerTotalBusquedaEstudio(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte, @Param("tipoEstudio") int tipoEstudio);
    
    Estudio obtenerPorIdEstudio(@Param("idEstudio") String idEstudio);
    
    Estudio obtenerPorClaveEstudio(@Param("claveEstudio") String claveEstudio);
}

