package mx.mc.service;

import java.util.List;
import mx.mc.model.NotaDispenColectDetalle;
import mx.mc.model.NotaDispenColectDetalle_Extended;

/**
 *
 * @author Cervanets
 */
public interface NotaDispenColectDetalleService  extends GenericCrudService<NotaDispenColectDetalle, String> {

    public List<NotaDispenColectDetalle_Extended> obtenerListaMezclas(NotaDispenColectDetalle_Extended ndce) throws Exception;
}
