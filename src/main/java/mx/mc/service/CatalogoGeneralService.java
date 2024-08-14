package mx.mc.service;

import java.util.List;
import mx.mc.model.CatalogoGeneral;

/**
 * @author AORTIZ
 */
public interface CatalogoGeneralService extends GenericCrudService<CatalogoGeneral, String> {
    public List<CatalogoGeneral> obtenerCatalogosPorGrupo(int idGrupo) throws Exception;
    public List<CatalogoGeneral> obtenerTiposInsumos(int idGrupo,String idEstructura) throws Exception;
    public String obtenerPorIdCatalogoGrl(int idCatalogo,int idGrupo) throws Exception;
}
