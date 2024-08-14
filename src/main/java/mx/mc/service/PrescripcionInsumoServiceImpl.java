/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.PrescripcionInsumoMapper;
import mx.mc.model.PrescripcionInsumo;
import mx.mc.model.PrescripcionInsumo_Extended;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author gcruz
 */
@Service
public class PrescripcionInsumoServiceImpl extends GenericCrudServiceImpl<PrescripcionInsumo, String> implements PrescripcionInsumoService {

    @Autowired
    private PrescripcionInsumoMapper prescripcionInsumoMapper;
    
    @Autowired
    public PrescripcionInsumoServiceImpl(GenericCrudMapper<PrescripcionInsumo, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
    
    @Override
    public List<PrescripcionInsumo> obtenerPrescripcionInsumosPorIdPrescripcion(String idPrescripcion) throws Exception {
        try {
            return prescripcionInsumoMapper.obtenerPrescripcionInsumosPorIdPrescripcion(idPrescripcion);
        } catch(Exception ex) {
            throw  new Exception("Error al obtener la lista de prescripcionInsumos con idPrescripcion : " + idPrescripcion + "   " + ex.getMessage());
        }
        
    }
    
    @Override
    public List<PrescripcionInsumo_Extended> obtenerPrescripcionesByIdPaciente(String idPaciente, String folioSurtimiento) throws Exception {
        try {
            return prescripcionInsumoMapper.obtenerPrescripcionesByIdPaciente(idPaciente, folioSurtimiento);
        } catch(Exception ex) {
            throw new Exception("Error al buscar la prescripcion insumos de paciente " + ex.getMessage());
        }
    }
    
    @Override
    public boolean registraMedicamentoList (List<PrescripcionInsumo> insumoList) throws Exception{
        try {
            return prescripcionInsumoMapper.registraMedicamentoList (insumoList);
        } catch (Exception ex) {
            throw  new Exception("Error al registrar la lista de prescripcionInsumos:" + ex.getMessage());
        }
    }
}
