/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.model.ClaveMedicamentoSku;
import mx.mc.model.ClaveMedicamentoSku_Extend;

/**
 *
 * @author bbautista
 */
public interface ClaveMedicamentoSkuService extends GenericCrudService<ClaveMedicamentoSku, String> {

    public ClaveMedicamentoSku obtenerClave(String sku) throws Exception;
    
    public Integer obtenerCantidadPorClaveMedicamento(String idMedicamento) throws Exception;

    public List<ClaveMedicamentoSku> obtenerListaClave(String sku) throws Exception;
    
    public List<ClaveMedicamentoSku_Extend> obtenerListaClavesSku (String sku, String idEstructura,String idUsuario) throws Exception;

}
