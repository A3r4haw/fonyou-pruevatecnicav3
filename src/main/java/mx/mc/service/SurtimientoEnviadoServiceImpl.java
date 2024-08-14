/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.SurtimientoEnviadoMapper;
import mx.mc.model.SurtimientoEnviado;
import mx.mc.model.SurtimientoEnviado_Extend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author bbautista
 */
@Service
public class SurtimientoEnviadoServiceImpl extends GenericCrudServiceImpl<SurtimientoEnviado, String> implements SurtimientoEnviadoService{

    @Autowired
    SurtimientoEnviadoMapper enviadoMapper; 
    
    public SurtimientoEnviadoServiceImpl(GenericCrudMapper<SurtimientoEnviado, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
    
    @Override
    public List<SurtimientoEnviado_Extend> detalleSurtimientoEnviadoXIdSurtimientoInsumo(String idSurtimientoInsumo, boolean mayorCero) throws Exception{
        try {
            return enviadoMapper.detalleSurtimientoEnviadoXIdSurtimientoInsumo(idSurtimientoInsumo, mayorCero);
        } catch (Exception ex) {
            throw new Exception("Error al obtener los surtimientos enviados. " + ex.getMessage());
        }
    }
    
    @Override
    public List<SurtimientoEnviado_Extend> obtenerSurtimientoEnviadoPorIdEstructuraIdInsumoIdInventario(String idEstructura , String idInsumo, String idInventario ) throws Exception{
        try {
            return enviadoMapper.obtenerSurtimientoEnviadoPorIdEstructuraIdInsumoIdInventario(idEstructura, idInsumo, idInventario);
        } catch (Exception ex) {
            throw new Exception("Error al obtener los surtimientos enviados. " + ex.getMessage());
        }
    }
}
