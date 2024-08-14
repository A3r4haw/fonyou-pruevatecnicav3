package mx.mc.service;

import java.util.List;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.DimesaRecetaMaterialMapper;
import mx.mc.model.DimesaRecetaMaterial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * @author hramirez
 */
@Service
public class DimesaRecetaMaterialServiceImpl extends GenericCrudServiceImpl<DimesaRecetaMaterial, String> implements DimesaRecetaMaterialService {
    
    @Autowired
    private DimesaRecetaMaterialMapper dimesaRecetaMaterialMapper;

    @Autowired
    public DimesaRecetaMaterialServiceImpl(GenericCrudMapper<DimesaRecetaMaterial, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public List<DimesaRecetaMaterial> obtenerListaVale(DimesaRecetaMaterial dimesaRecetaMaterial) throws Exception {
        try{
            return dimesaRecetaMaterialMapper.obtenerListaVale(dimesaRecetaMaterial);
        }catch(Exception ex){
            throw new Exception("Error al obtener la lista de devoluciones de la estructura" + ex.getMessage());
        }
    }
}
