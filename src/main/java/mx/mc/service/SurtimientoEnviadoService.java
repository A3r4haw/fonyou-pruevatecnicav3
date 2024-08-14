/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.model.SurtimientoEnviado;
import mx.mc.model.SurtimientoEnviado_Extend;

/**
 *
 * @author bbautista
 */
public interface SurtimientoEnviadoService extends GenericCrudService<SurtimientoEnviado, String>{
    
    public List<SurtimientoEnviado_Extend> detalleSurtimientoEnviadoXIdSurtimientoInsumo(String idSurtimientoInsumo, boolean mayorCero) throws Exception;
    
    public List<SurtimientoEnviado_Extend> obtenerSurtimientoEnviadoPorIdEstructuraIdInsumoIdInventario(String idEstructura , String idInsumo, String idInventario ) throws Exception;
    
}
