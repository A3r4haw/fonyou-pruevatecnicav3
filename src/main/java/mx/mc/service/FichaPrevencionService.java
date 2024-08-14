package mx.mc.service;

import java.util.List;
import mx.mc.model.AdjuntoExtended;
import mx.mc.model.FichaPrevencion;
import mx.mc.model.FichaPrevencionExtended;

/**
 *
 * @author Cervanets
 */

public interface FichaPrevencionService  extends GenericCrudService<FichaPrevencion, String> {
    
    public List<FichaPrevencionExtended> obtenerListaFichas(FichaPrevencionExtended fpe) throws Exception;
    
    public boolean insertaFichaConAdjuntos(FichaPrevencion fpe, List<AdjuntoExtended> adjuntosLista ) throws Exception;
    
    public FichaPrevencionExtended obtenerFicha(FichaPrevencionExtended fpe)  throws Exception;
    
    public boolean actualizaFichaConAdjuntos(FichaPrevencion fpe, List<AdjuntoExtended> adjuntosLista ) throws Exception;
    
}
