/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.ClaveMedicamentoSku;
import mx.mc.mapper.ClaveMedicamentoSkuMapper;
import mx.mc.model.ClaveMedicamentoSku_Extend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author bbautista
 */
@Service
public class ClaveMedicamentoSkuServiceImpl extends GenericCrudServiceImpl<ClaveMedicamentoSku, String> implements ClaveMedicamentoSkuService {
    
    @Autowired
    private ClaveMedicamentoSkuMapper skuMapper;

    @Autowired
    public ClaveMedicamentoSkuServiceImpl(GenericCrudMapper<ClaveMedicamentoSku, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public ClaveMedicamentoSku obtenerClave(String sku) throws Exception {
        try {
            return skuMapper.obtenerClave(sku);
        } catch (Exception ex) {
            throw new Exception("Error al obtener la clave ISSEMYM: " + ex.getMessage());
        }
    }

    @Override
    public List<ClaveMedicamentoSku> obtenerListaClave(String sku) throws Exception {
        try {
            return skuMapper.obtenerListaClave(sku);
        } catch (Exception ex) {
            throw new Exception("Error al obtener la lista de claves ISSEMYM: " + ex.getMessage());
        }
    }

    @Override
    public Integer obtenerCantidadPorClaveMedicamento(String idMedicamento) throws Exception {
        try{
            return skuMapper.obtenerCantidadPorClaveMedicamento(idMedicamento);
        }catch(Exception ex){
            throw new Exception("Error en el metodo obtenerCantidadPorClaveMedicamento: "+ex.getMessage());
        }
    }

    @Override
    public List<ClaveMedicamentoSku_Extend> obtenerListaClavesSku (String sku, String idEstructura, String idUsuario) throws Exception {
        try{
            return skuMapper.obtenerListaClavesSku(sku, idEstructura,idUsuario);
        }catch(Exception ex){
            throw new Exception("Error en el metodo obtenerListaClavesSku: " + ex.getMessage());
        }
    }
    
}
