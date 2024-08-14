package mx.mc.service;

import java.util.List;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.NotaDispenColectDetalleMapper;
import mx.mc.model.NotaDispenColectDetalle;
import mx.mc.model.NotaDispenColectDetalle_Extended;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Cervanets
 */
@Service
public class NotaDispenColectDetalleServiceImpl extends GenericCrudServiceImpl<NotaDispenColectDetalle, String> implements NotaDispenColectDetalleService {

    @Autowired
    NotaDispenColectDetalleMapper notaDispenColectDetalleMapper;

    public NotaDispenColectDetalleServiceImpl(GenericCrudMapper<NotaDispenColectDetalle, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
    
    @Override
    public List<NotaDispenColectDetalle_Extended> obtenerListaMezclas(NotaDispenColectDetalle_Extended ndce) throws Exception {
        try {
            return notaDispenColectDetalleMapper.obtenerListaMezclas(ndce);
        } catch (Exception e){
            throw new Exception("Error obtener lista de mezclas de Nota de dispensacion colectiva. " + e.getMessage());
        }
    }
    
    
}
