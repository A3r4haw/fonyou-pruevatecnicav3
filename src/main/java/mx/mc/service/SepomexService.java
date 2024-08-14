package mx.mc.service;

import java.util.List;
import mx.mc.model.Sepomex;

/**
 * @author AORTIZ
 */
public interface SepomexService extends GenericCrudService<Sepomex, String> {

    public List<Sepomex> obtenerEstados() throws Exception;

    public List<Sepomex> obtenerMunicipios(String idEstado) throws Exception;

    public List<Sepomex> obtenerColonias(String idEstado, 
            String idMunicipio) throws Exception;
    
    public Sepomex obtenerCodPost(String idEstado, 
            String idMunicipio, String idColonia) throws Exception;
    
    public List<Sepomex> obtenerEstadoMunYColByCodPost(
            String codigoPostal) throws Exception;
}
