package mx.mc.mapper;

import java.util.List;
import mx.mc.model.FichaPrevencion;
import mx.mc.model.FichaPrevencionExtended;

/**
 *
 * @author Cervanets
 */
public interface FichaPrevencionMapper extends GenericCrudMapper<FichaPrevencion, String> {
    
    public List<FichaPrevencionExtended> obtenerListaFichas(FichaPrevencionExtended fpe);
    
    public FichaPrevencionExtended obtenerFicha(FichaPrevencionExtended fpe);
    
}
