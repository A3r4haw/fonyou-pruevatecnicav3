/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.init.Constantes;
import mx.mc.mapper.AlmacenControlMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.AlmacenControl;
import mx.mc.model.AlmacenControl_Extended;
import mx.mc.model.Estructura;
import mx.mc.model.Medicamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author bbautista
 */
@Service
public class AlmacenControlServiceImpl extends GenericCrudServiceImpl<AlmacenControl,String> implements AlmacenControlService {
    
    @Autowired
    public AlmacenControlMapper almacenCtrlMapper;

    @Autowired
    public AlmacenControlServiceImpl(GenericCrudMapper<AlmacenControl, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public boolean actualizaEstatusGabinete(String idMedicamento,Integer estatusGabinete) throws Exception {
        boolean res=Constantes.INACTIVO;
        try{
            res = almacenCtrlMapper.actualizaEstatusGabinete(idMedicamento,estatusGabinete); 
        }catch(Exception ex){
            throw new Exception("Error al actualizar el estatus Gabinete" + ex.getMessage());
        }
        return res;
    }

    @Override
    public AlmacenControl obtenerIdPuntosControl(String idAlmacen, String idMedicamento) throws Exception {        
        try{
            return almacenCtrlMapper.obtenerIdPuntosControl(idAlmacen,idMedicamento); 
        }catch(Exception ex){
            throw new Exception("Error al actualizar el estatus Gabinete" + ex.getMessage());
        }        
    }
    
    @Override
    public boolean actualizarMasivo(List<AlmacenControl> insumos) throws Exception{
        try{
            return almacenCtrlMapper.actualizarMasivo(insumos);
        }catch(Exception ex){
            throw new Exception("Error al actualizar de forma masiva" + ex.getMessage());
        }
    }
    
    @Override
    public List<AlmacenControl_Extended> obtenerTotalInsumoAlmacen(List<Estructura> estructuraList,List<String> insumoList) throws Exception{
        try {
            return almacenCtrlMapper.obtenerTotalInsumoAlmacen(estructuraList,insumoList);
        } catch (Exception ex) {
            throw new Exception("Error al obtener la lista" + ex.getMessage());
        }
    }
}
