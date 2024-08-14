package mx.mc.service;

import java.util.List;
import mx.mc.mapper.EstudioMapper;

import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.Estudio;
import mx.mc.model.ParamBusquedaReporte;
import org.primefaces.model.SortOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstudioServiceImpl extends GenericCrudServiceImpl<Estudio, String> implements EstudioService {

    @Autowired
    private EstudioMapper estudioMapper;

    @Autowired
    public EstudioServiceImpl(GenericCrudMapper<Estudio, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public List<Estudio> obtenerBusquedaEstudio(ParamBusquedaReporte paramBusquedaReporte, int tipoEstudio, int startingAt, int maxPerPage, String sortField, SortOrder sortOrder) throws Exception {
        try {
            String order = sortOrder==SortOrder.ASCENDING ? "asc": sortOrder==SortOrder.DESCENDING ? "desc" : null;            
            return estudioMapper.obtenerBusquedaEstudio(paramBusquedaReporte, tipoEstudio, startingAt, maxPerPage, sortField, order);
        } catch (Exception e) {
            throw new Exception("Error al momento de buscar Estudios " + e.getMessage()); 
        }
    }

    @Override
    public Long obtenerTotalBusquedaEstudio(ParamBusquedaReporte paramBusquedaReporte, int tipoEstudio) throws Exception {
        try {
            return estudioMapper.obtenerTotalBusquedaEstudio(paramBusquedaReporte, tipoEstudio);
        } catch (Exception e) {
            throw new Exception("Error al momento de obtener total de Estudios " + e.getMessage());  
        }
    }
    
    @Override
    public Estudio obtenerPorIdEstudio(String idEstudio) throws Exception {
        Estudio estudio = new Estudio();
        try {
            estudio = estudioMapper.obtenerPorIdEstudio(idEstudio);
        } catch (Exception ex) {
            throw new Exception("Error al obtener Estudio por Id" + ex.getMessage());
        }
        return estudio;
    }
    
    @Override
    public Estudio obtenerPorClaveEstudio(String claveEstudio) throws Exception {
        Estudio estudio = new Estudio();
        try {
            estudio = estudioMapper.obtenerPorClaveEstudio(claveEstudio);
        } catch (Exception ex) {
            throw new Exception("Error al obtener Estudio por Clave" + ex.getMessage());
        }
        return estudio;
    }
}
