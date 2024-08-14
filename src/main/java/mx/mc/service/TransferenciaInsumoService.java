/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;
import mx.mc.model.TransferenciaInsumo;
import java.util.List;
/**
 *
 * @author bbautista
 */
public interface TransferenciaInsumoService extends GenericCrudService<TransferenciaInsumo,String> {
    public List<TransferenciaInsumo> obtenerDetalle(String idTransferencia) throws Exception; 
}
