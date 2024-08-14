package mx.mc.mapper;

import java.util.List;
import mx.mc.model.Pais;

/**
 * @author AORTIZ
 */
public interface PaisMapper extends GenericCrudMapper<Pais, String> {
    public List<Pais> obtenerLista() throws Exception;
}
