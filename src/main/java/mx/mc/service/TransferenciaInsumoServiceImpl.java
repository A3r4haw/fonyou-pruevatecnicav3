/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.TransferenciaInsumoMapper;
import org.springframework.stereotype.Service;
import mx.mc.model.TransferenciaInsumo;
import org.springframework.beans.factory.annotation.Autowired;
/**
 *
 * @author bbautista
 */
@Service
public class TransferenciaInsumoServiceImpl extends GenericCrudServiceImpl<TransferenciaInsumo,String> implements TransferenciaInsumoService {

    @Autowired
    private TransferenciaInsumoMapper transferenciaInsumoMapper;
    
    @Autowired
    public TransferenciaInsumoServiceImpl(GenericCrudMapper<TransferenciaInsumo, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public List<TransferenciaInsumo> obtenerDetalle(String idTransferencia )  throws Exception {
        try{
            return transferenciaInsumoMapper.detalleTransfer(idTransferencia);
            
        }catch(Exception ex){
            throw new Exception("Error al obtener la Busqueda. " + ex.getMessage());
        }
    }   
    
}
