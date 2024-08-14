package mx.mc.service;

import java.util.List;
import mx.mc.mapper.ConfigMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.Config;
import mx.mc.model.ParamBusquedaReporte;
import org.primefaces.model.SortOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * @author hramirez
 */
@Service
public class ConfigServiceImpl extends GenericCrudServiceImpl<Config, String> implements ConfigService {

    @Autowired
    private ConfigMapper configMapper;
    
    @Autowired
    public ConfigServiceImpl(GenericCrudMapper<Config, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
    
    @Override
    public List<Config> obtenerConfigOrdenadoPorCadena(Config params, int startingAt, int maxPerPage,
             String sortField, SortOrder sortOrder) throws Exception {
        try {
            String order = sortOrder == SortOrder.ASCENDING ? "asc" : "desc" ;
            return configMapper.obtenerConfigOrdenadoPorCadena(params, startingAt, maxPerPage, sortField, order);
        } catch (Exception e) {
            throw new Exception("Error al obtener los Registros. " + e.getMessage());
}
    }

    @Override
    public Long obtenerTotalConfigOrdenadoPorCadena(Config params) throws Exception {
        try {
            return configMapper.obtenerTotalConfigOrdenadoPorCadena(params);
        } catch (Exception e) {
            throw new Exception("Error al obtener el total de registros de Usuarios." + e.getMessage());
        }
    }
    
    @Override
    public Config obtenerByNombre(String nombre) throws Exception {
        try {
            return configMapper.obtenerByNombre(nombre);
        } catch (Exception e) {
            throw new Exception("Error al obtener el total de registros de Usuarios." + e.getMessage());
        }
    }
}
