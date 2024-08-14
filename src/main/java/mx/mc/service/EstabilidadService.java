package mx.mc.service;

import java.util.List;
import mx.mc.model.Estabilidad;
import mx.mc.model.Estabilidad_Extended;
import mx.mc.model.SurtimientoInsumo_Extend;
import org.primefaces.model.SortOrder;

/**
 *
 * @author cervanets
 */
public interface EstabilidadService extends GenericCrudService<Estabilidad, String> {
        
    public List<Estabilidad_Extended> buscarEstabilidad(
            String idInsumo
            , String claveDiluyente
            , Integer idViaAdministracion
            , Integer idFabricante
            , Integer idContenedor
    ) throws Exception;
    
    public List<Estabilidad_Extended> obtenerListaEstabilidades(String cadenaBusqueda, int startingAt, int maxPerPage, String sortField, SortOrder sortOrder) throws Exception;
    
    public Long obtenerTotalEstabilidades(String cadenaBusqueda) throws Exception;
    
    public Estabilidad_Extended obtenerEstabilidad(
            String idInsumo
            , String claveDiluyente
            , Integer idViaAdministracion
            , Integer idFabricante
    ) throws Exception;
    
    public Estabilidad_Extended obtenerEstabilidadPorId(String idEstabilidad) throws Exception;
    
    
}
