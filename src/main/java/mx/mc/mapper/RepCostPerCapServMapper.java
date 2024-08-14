package mx.mc.mapper;

import java.util.Date;
import java.util.List;
import mx.mc.model.DataResultReport;
import org.apache.ibatis.annotations.Param;

/**
 * @author AORTIZ
 */
public interface RepCostPerCapServMapper extends GenericCrudMapper<DataResultReport, String>{
   
    List<DataResultReport> obtenerDatosRepCostPerCapServ(
            @Param("idEstructura") String idEstructura , 
            @Param("fechaInicio") Date fechaInicio , 
            @Param("fechaFin") Date fechaFin);
    
}
