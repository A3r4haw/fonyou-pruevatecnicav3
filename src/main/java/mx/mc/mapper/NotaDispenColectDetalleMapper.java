package mx.mc.mapper;

import java.util.List;
import mx.mc.model.NotaDispenColectDetalle;
import mx.mc.model.NotaDispenColectDetalle_Extended;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author Cervanets
 */
public interface NotaDispenColectDetalleMapper extends GenericCrudMapper<NotaDispenColectDetalle, String> {

    boolean registrarLista(List<NotaDispenColectDetalle_Extended> ndcdLista);
    
    boolean actualizarLista(List<NotaDispenColectDetalle_Extended> ndcdLista);
    
    List<NotaDispenColectDetalle_Extended> obtenerListaMezclas(NotaDispenColectDetalle_Extended ndce) throws Exception;
    
    boolean eliminarLista(@Param("idNotaDispenColect") String idNotaDispenColect);
    
}
