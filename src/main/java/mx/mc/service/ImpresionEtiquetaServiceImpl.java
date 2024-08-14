package mx.mc.service;

import java.util.List;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.ImpresionEtiquetaMapper;
import mx.mc.model.ImpresionEtiqueta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Cervanets
 */
@Service
public class ImpresionEtiquetaServiceImpl  extends GenericCrudServiceImpl<ImpresionEtiqueta, String> implements ImpresionEtiquetaService {
    
    @Autowired
    private ImpresionEtiquetaMapper impresionEtiquetaMapper;
            
    
    @Autowired
    public ImpresionEtiquetaServiceImpl(GenericCrudMapper<ImpresionEtiqueta, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public List<ImpresionEtiqueta> obtenerImpresiones(ImpresionEtiqueta o) throws Exception {
        try {
            return impresionEtiquetaMapper.obtenerImpresiones(o);
        } catch (Exception e){
            throw new Exception("Error al obtener Impresion de Etiquetas. " + e.getMessage());
        }
    }
    
}

