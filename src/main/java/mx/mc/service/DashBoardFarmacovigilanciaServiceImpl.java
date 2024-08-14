/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.mapper.DashBoardFarmacovigilanciaMapper;
import mx.mc.model.DashboardResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author gcruz
 */
@Service
public class DashBoardFarmacovigilanciaServiceImpl implements DashBoardFarmacovigilanciaService {
    
    @Autowired
    private DashBoardFarmacovigilanciaMapper dashBoardFarmacovigilanciaMapper;

    @Override
    public List<DashboardResult> getTopFarmacosMayorReaccion() throws Exception {
        try {
            return dashBoardFarmacovigilanciaMapper.getTopFarmacosMayorReaccion();
        } catch(Exception ex) {
            throw new Exception("Error al obtener top de farmacos con mayor reacción" + ex.getMessage());
        }
    }

    @Override
    public List<DashboardResult> getTopTiposHipersensibilidad() throws Exception {
        try {
            return dashBoardFarmacovigilanciaMapper.getTopTiposHipersensibilidad();
        } catch(Exception ex) {
            throw new Exception("Error al obtener top tipos hipersensibilidad    " + ex.getMessage());
        }
    }
    
     @Override
    public List<DashboardResult> getTopFarmacosRAM() throws Exception {
        try {
            return dashBoardFarmacovigilanciaMapper.getTopFarmacosRAM();
        } catch(Exception ex) {
            throw new Exception("Error al obtener top farmacos RAM    " + ex.getMessage());
        }
    }
    
     @Override
    public List<DashboardResult> getTopPacientesReaccion() throws Exception {
        try {
            return dashBoardFarmacovigilanciaMapper.getTopPacientesReaccion();
        } catch(Exception ex) {
            throw new Exception("Error al obtener top pacientes reacción    " + ex.getMessage());
        }
    }

    @Override
    public List<DashboardResult> getTopPacientesReaccionByIdPaciente(String cadena) throws Exception {
        try {
            return dashBoardFarmacovigilanciaMapper.getTopPacientesReaccionByIdPaciente(cadena);
        } catch(Exception ex) {
            throw new Exception("Error al obtener pacientes por numero o nombre paciente " + ex.getMessage());            
        }
    }

    @Override
    public List<DashboardResult> getTopUsuariosReaccion() throws Exception {
        try{
            return dashBoardFarmacovigilanciaMapper.getTopUsuariosReaccion();
        } catch(Exception ex) {
            throw new Exception("Error al obtener los usuarios con registros de mas reacciones:  " + ex.getMessage());
        }
    }
    
}
