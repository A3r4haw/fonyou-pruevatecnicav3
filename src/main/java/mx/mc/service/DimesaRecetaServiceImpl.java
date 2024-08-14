package mx.mc.service;

import java.util.List;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.DimesaRecetaMapper;
import mx.mc.model.DimesaReceta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * @author hramirez
 */
@Service
public class DimesaRecetaServiceImpl extends GenericCrudServiceImpl<DimesaReceta, String> implements DimesaRecetaService {
    
    @Autowired
    private DimesaRecetaMapper dimesaRecetaMapper;

    @Autowired
    public DimesaRecetaServiceImpl(GenericCrudMapper<DimesaReceta, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    /**
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<DimesaReceta> obtenerListaCancelados() throws Exception {
        try{
            return dimesaRecetaMapper.obtenerListaCancelados();
        }catch(Exception ex){
            throw new Exception("Error al obtener la lista de devoluciones de la estructura" + ex.getMessage());
        }
    }
    
    @Override
    public List<DimesaReceta> obtenerListaVales(DimesaReceta dimesaReceta) throws Exception {
        try{
            return dimesaRecetaMapper.obtenerListaVales(dimesaReceta);
        }catch(Exception ex){
            throw new Exception("Error al obtener la lista de devoluciones de la estructura" + ex.getMessage());
        }
    }

}
