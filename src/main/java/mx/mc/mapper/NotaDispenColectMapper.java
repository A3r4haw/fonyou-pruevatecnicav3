package mx.mc.mapper;

import java.util.List;
import mx.mc.model.NotaDispenColect;
import mx.mc.model.NotaDispenColect_Extended;


/**
 *
 * @author Cervanets
 */
public interface NotaDispenColectMapper extends GenericCrudMapper<NotaDispenColect, String> {
    
    public List<NotaDispenColect_Extended>obtenerListaNotas(NotaDispenColect_Extended ndc);
    
}
