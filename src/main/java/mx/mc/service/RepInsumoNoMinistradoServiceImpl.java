/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.RepInsumoNoMinistradoMapper;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.RepInsumoNoMinistrado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author olozada
 */
@Service
public class RepInsumoNoMinistradoServiceImpl extends GenericCrudServiceImpl<RepInsumoNoMinistrado , String> implements RepInsumoNoMinistradoService{

    @Autowired
    private RepInsumoNoMinistradoMapper repInsumoNoMinistradoMapper;
    
    @Autowired
    public RepInsumoNoMinistradoServiceImpl(GenericCrudMapper<RepInsumoNoMinistrado, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public List<RepInsumoNoMinistrado> obtenerListaInsumo(String cadenaBusqueda) throws Exception {
        try {
            return repInsumoNoMinistradoMapper.obtenerListaInsumo(cadenaBusqueda);
        } catch (Exception ex) {
            throw new Exception("Ocurrio un error al obtener lista de Insumos   " + ex.getMessage());
        }
    }

    @Override
    public List<RepInsumoNoMinistrado> consultaInsumoNoMinistrados(ParamBusquedaReporte paramBusquedaReporte, int startingAt, 
            int maxPerPage) throws Exception {
        try {
            return repInsumoNoMinistradoMapper.consultaInsumoNoMinistrados(paramBusquedaReporte, startingAt, maxPerPage);
        } catch (Exception ex) {
            throw new Exception("Error al obtener consulta de reporte " + ex.getMessage());
        }
    }
    
    @Override
    public Long obtenerTotalInsumoNoMinistrado(ParamBusquedaReporte paramBusquedaReporte) throws Exception {
        try {
            return repInsumoNoMinistradoMapper.obtenerTotalInsumoNoMinistrado(paramBusquedaReporte);
        } catch (Exception ex) {
            throw new Exception("Error al obtener total de registros " + ex.getMessage());
        }
    }
}
