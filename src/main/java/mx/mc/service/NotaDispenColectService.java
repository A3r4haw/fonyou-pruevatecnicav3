package mx.mc.service;

import java.util.List;
import mx.mc.model.NotaDispenColect;
import mx.mc.model.NotaDispenColectDetalle_Extended;
import mx.mc.model.NotaDispenColect_Extended;

/**
 *
 * @author Cervanets
 */
public interface NotaDispenColectService extends GenericCrudService<NotaDispenColect, String> {

    boolean registrarNotaDispenColect(NotaDispenColect_Extended ndc, List<NotaDispenColectDetalle_Extended> ndcl) throws Exception;
    
    boolean actualizarNotaDispenColect(NotaDispenColect_Extended ndc, List<NotaDispenColectDetalle_Extended> ndcl) throws Exception;
    
    public List<NotaDispenColect_Extended>obtenerListaNotas(NotaDispenColect_Extended ndc) throws Exception;
    
    boolean cancelarNotaDispenColect(NotaDispenColect_Extended ndc, List<NotaDispenColectDetalle_Extended> ndcl) throws Exception;
    
}
