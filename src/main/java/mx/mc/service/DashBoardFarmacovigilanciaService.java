/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.model.DashboardResult;

/**
 *
 * @author gcruz
 */
public interface DashBoardFarmacovigilanciaService {
    
    public List<DashboardResult> getTopFarmacosMayorReaccion() throws Exception;
    
    public List<DashboardResult> getTopTiposHipersensibilidad() throws Exception;
    
    List<DashboardResult> getTopFarmacosRAM() throws Exception;
    
    List<DashboardResult>  getTopPacientesReaccion() throws Exception;
    
    List<DashboardResult> getTopUsuariosReaccion() throws Exception;
    
    List<DashboardResult> getTopPacientesReaccionByIdPaciente(String cadena) throws Exception;
            
}
