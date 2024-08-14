package mx.mc.mapper;

import java.util.List;
import mx.mc.model.Sepomex;
import org.apache.ibatis.annotations.Param;

/**
 * @author AORTIZ
 */
public interface SepomexMapper extends GenericCrudMapper<Sepomex, String> {

    public List<Sepomex> obtenerEstados() throws Exception;

    public List<Sepomex> obtenerMunicipios(@Param("idEstado") String idEstado) throws Exception;

    public List<Sepomex> obtenerColonias(@Param("idEstado") String idEstado, 
            @Param("idMunicipio") String idMunicipio) throws Exception;
    
    public Sepomex obtenerCodPost(@Param("idEstado") String idEstado,
            @Param("idMunicipio") String idMunicipio, 
            @Param("idColonia") String idColonia) throws Exception;
    
    public List<Sepomex> obtenerEstadoMunYColByCodPost(
            @Param("codigoPostal") String codigoPostal) throws Exception;
}
