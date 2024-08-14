package mx.mc.service;

import java.util.List;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.SepomexMapper;
import mx.mc.model.Sepomex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author AORTIZ
 */
@Service
public class SepomexServiceImplements extends GenericCrudServiceImpl<Sepomex, String> implements SepomexService {
    
    @Autowired
    private SepomexMapper sepomexMapper;

    @Autowired
    public SepomexServiceImplements(GenericCrudMapper<Sepomex, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public List<Sepomex> obtenerEstados() throws Exception {
        try {
            return sepomexMapper
                        .obtenerEstados();
        } catch (Exception ex) {
            throw new Exception("Error obtenerEstados. " + ex.getMessage());
        }
    }

    @Override
    public List<Sepomex> obtenerMunicipios(String idEstado) throws Exception {
        try {
            return sepomexMapper
                        .obtenerMunicipios(idEstado);
        } catch (Exception ex) {
            throw new Exception("Error obtenerMunicipios. " + ex.getMessage());
        }
    }

    @Override
    public List<Sepomex> obtenerColonias(String idEstado, String idMunicipio) throws Exception {
        try {
            return sepomexMapper
                        .obtenerColonias(idEstado, idMunicipio);
        } catch (Exception ex) {
            throw new Exception("Error obtenerColonias. " + ex.getMessage());
        }
    }

    @Override
    public Sepomex obtenerCodPost(String idEstado, String idMunicipio, String idColonia) 
            throws Exception {
        try {
            return sepomexMapper
                        .obtenerCodPost(idEstado, idMunicipio, idColonia);
        } catch (Exception ex) {
            throw new Exception("Error obtenerCodPost. " + ex.getMessage());
        }
    }
    
    @Override
    public  List<Sepomex> obtenerEstadoMunYColByCodPost(String codigoPostal)
            throws Exception {
        try {
            return sepomexMapper
                        .obtenerEstadoMunYColByCodPost(codigoPostal);
        } catch (Exception ex) {
            throw new Exception("Error obtenerEstadoMunYColByCodPost. " + ex.getMessage());
        }
    }

}
