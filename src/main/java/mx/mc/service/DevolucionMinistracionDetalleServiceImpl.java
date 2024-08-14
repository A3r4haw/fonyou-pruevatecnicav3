/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.ArrayList;
import java.util.List;
import mx.mc.mapper.DevolucionMinistracionDetalleMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.SurtimientoEnviadoMapper;
import mx.mc.model.DevolucionMinistracionDetalle;
import mx.mc.model.DevolucionMinistracionDetalleExtended;
import mx.mc.model.SurtimientoEnviado_Extend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author bbautista
 */
@Service
public class DevolucionMinistracionDetalleServiceImpl 
        extends GenericCrudServiceImpl<DevolucionMinistracionDetalle,String> 
        implements DevolucionMinistracionDetalleService {

    @Autowired
    private DevolucionMinistracionDetalleMapper devolucionMinistracionDetalleMapper;
    
    @Autowired
    private SurtimientoEnviadoMapper surtimientoEnviadoMapper;
    
    @Autowired
    public DevolucionMinistracionDetalleServiceImpl(GenericCrudMapper<DevolucionMinistracionDetalle, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public List<DevolucionMinistracionDetalle> obtenerListaDetalle(String idDevolucionMinistracion) throws Exception {
        try{
            return devolucionMinistracionDetalleMapper.obtenerListaByIdDevolucionMinistracion(idDevolucionMinistracion);
        }catch(Exception ex){
            throw new Exception("Error al obtener la lista detalle"+ex.getMessage()); //To change body of generated methods, choose Tools | Templates.
        }
    }
    
    @Override
    public List<DevolucionMinistracionDetalleExtended> obtenerListaDetalleExtended(
            DevolucionMinistracionDetalleExtended parametros, String idSurtimiento) throws Exception {
        try{
            List<DevolucionMinistracionDetalleExtended> devDetalleExtList;
            devDetalleExtList = devolucionMinistracionDetalleMapper.obtenerListaByIdDevolucionMinistracionExtended(parametros);
             List<SurtimientoEnviado_Extend> detail = surtimientoEnviadoMapper.obtenerDetalleInsumoDevMediByIdSurtimientoIdDevolucion(idSurtimiento,parametros.getIdDevolucionMinistracion());
            for(DevolucionMinistracionDetalleExtended dev : devDetalleExtList){
                List<SurtimientoEnviado_Extend> devDetalleExtListAux = new ArrayList<>();
                for (SurtimientoEnviado_Extend item : detail) {
                    if(dev.getIdSurtimientoInsumo().equals(item.getIdSurtimientoInsumo())){
                        devDetalleExtListAux.add(item);
                    }
                }
                dev.setSurtimientoEnviadoExtList(devDetalleExtListAux);
            }
            return devDetalleExtList;
        }catch(Exception ex){
            throw new Exception("Error al obtener la lista detalle"+ex.getMessage()); //To change body of generated methods, choose Tools | Templates.
        }
    }

    
    
    @Override
    public List<DevolucionMinistracionDetalleExtended> devolucionMiniDetallePorIdPrescripcion(String idPrescripcion) throws Exception {
        List<DevolucionMinistracionDetalleExtended> devolucionMiniDetalleExt= new ArrayList<>();
        try {
             devolucionMiniDetalleExt= devolucionMinistracionDetalleMapper.devolucionMiniDetalleExtend(idPrescripcion);
        } catch (Exception ex) {
            throw new Exception("Error al Obtener detalle Surtimiento Devolucion " + ex.getMessage());
        }
        return devolucionMiniDetalleExt;
    }

    @Override
    public List<DevolucionMinistracionDetalleExtended> obtenerListaDetalleExt(String idDevolucionMinistracion) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
