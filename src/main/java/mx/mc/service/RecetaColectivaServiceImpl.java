package mx.mc.service;

import java.util.Date;
import java.util.List;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.SurtimientoInsumoMapper;
import mx.mc.model.SurtimientoInsumo;
import mx.mc.model.SurtimientoInsumo_Extend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * @author hramirez
 */
@Service
public class RecetaColectivaServiceImpl extends GenericCrudServiceImpl<SurtimientoInsumo, String> implements RecetaColectivaService {
    
    @Autowired
    private SurtimientoInsumoMapper surtimientoInsumoMapper;

    @Autowired
    public RecetaColectivaServiceImpl(GenericCrudMapper<SurtimientoInsumo, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public List<SurtimientoInsumo_Extend> obtenerSurtimientosProgramados(Date fechaProgramada 
            , String idSurtimiento 
            , String idPrescripcion 
            , List<Integer> listEstatusSurtimientoInsumo 
            , List<Integer> listEstatusSurtimiento
            ,  String idEstructura
            , boolean surtimientoMixto) throws Exception {
        try {
            return surtimientoInsumoMapper.obtenerSurtimientosProgramados(fechaProgramada, idSurtimiento, idPrescripcion,listEstatusSurtimientoInsumo, listEstatusSurtimiento,  idEstructura, surtimientoMixto);
        } catch (Exception ex) {
            throw new Exception("Error obtener Insumos del Surtimiento de Prescripción Seleccionado. " + ex.getMessage());
        }
    }
    
    @Override
    public List<SurtimientoInsumo_Extend> obtenerSurtimientoInsumoExtendedByIdPrescripcion(String idPrescripcion) throws Exception {
        try {
            return surtimientoInsumoMapper.obtenerSurtimientoInsumoExtended(idPrescripcion);
        } catch (Exception ex) {
            throw new Exception("Error obtener Insumos del Surtimiento de Prescripción Seleccionado. " + ex.getMessage());
        }
    }

}
