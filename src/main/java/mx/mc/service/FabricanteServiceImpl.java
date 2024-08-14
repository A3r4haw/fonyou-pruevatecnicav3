package mx.mc.service;

import java.util.List;
import mx.mc.mapper.FabricanteMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.Fabricante;
import org.primefaces.model.SortOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * @author hramirez
 */
@Service
public class FabricanteServiceImpl extends GenericCrudServiceImpl<Fabricante, String> implements FabricanteService {
    
    @Autowired
    private FabricanteMapper fabricanteMapper;
    
    @Autowired
    public FabricanteServiceImpl(GenericCrudMapper<Fabricante, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
    
    @Override
    public List<Fabricante> obtenerListaPorIdInsumo(String idInsumo) throws Exception {
        try{
            return fabricanteMapper.obtenerListaPorIdInsumo(idInsumo);
        }catch(Exception ex){
            throw new Exception("Error al obtener los datos del fabricante: " + ex.getMessage());
        }
    }
    
    @Override
    public Integer obtenerSiguienteId() throws Exception {
        try {
            return fabricanteMapper.obtenerSiguienteId();
        } catch(Exception ex) {
            throw new Exception("Ocurrio un error al obtener el siguiente id de Fabricante" + ex.getMessage());
        }
    }

    @Override
    public List<Fabricante> obtenerListaFabricantes(String cadenaBusqueda, int startingAt, int maxPerPage, String sortField, SortOrder sortOrder) throws Exception {
        try {
            return fabricanteMapper.obtenerListaFabricantes(cadenaBusqueda, startingAt, maxPerPage, sortField, sortOrder);
        } catch(Exception ex) {
            throw new Exception("  " + ex.getMessage());
        }
    }

    @Override
    public Long obtenerTotalFabricantes(String cadenaBusqueda) throws Exception {
        try {
            return fabricanteMapper.obtenerTotalFabricantes(cadenaBusqueda);
        } catch(Exception ex) {
            throw new Exception("  " + ex.getMessage());
        }
    }
    
}
