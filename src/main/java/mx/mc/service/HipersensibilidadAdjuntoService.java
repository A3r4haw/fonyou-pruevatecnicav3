/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.model.HipersensibilidadAdjunto;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author gcruz
 */
public interface HipersensibilidadAdjuntoService extends GenericCrudService<HipersensibilidadAdjunto, String> {
    
    boolean insertarListaHiperAdjuntos(@Param("listaHiperAdjuntos") List<HipersensibilidadAdjunto> listaHiperAdjuntos) throws Exception;
    
}
