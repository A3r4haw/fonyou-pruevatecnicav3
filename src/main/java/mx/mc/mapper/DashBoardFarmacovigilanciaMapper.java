/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.List;
import mx.mc.model.DashboardResult;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author gcruz
 */
public interface DashBoardFarmacovigilanciaMapper {
    
    List<DashboardResult> getTopFarmacosMayorReaccion();
    
    List<DashboardResult> getTopTiposHipersensibilidad();
    
    List<DashboardResult> getTopFarmacosRAM();
    
    List<DashboardResult>  getTopPacientesReaccion();
    
    List<DashboardResult> getTopUsuariosReaccion();
    
    List<DashboardResult> getTopPacientesReaccionByIdPaciente(@Param("cadena") String cadena);
    
}
