package mx.mc.service;

import java.util.List;
import mx.mc.mapper.CatalogoGeneralMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.CatalogoGeneral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author AORTIZ
 */
@Service
public class CatalogoGeneralServiceImplements extends GenericCrudServiceImpl<CatalogoGeneral, String> implements CatalogoGeneralService {
    
    @Autowired
    private CatalogoGeneralMapper catalogoGeneralMapper;
    
    @Autowired
    public CatalogoGeneralServiceImplements(GenericCrudMapper<CatalogoGeneral, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
    
    @Override
    public List<CatalogoGeneral> obtenerCatalogosPorGrupo(int idGrupo) throws Exception {
       try {
            return catalogoGeneralMapper.obtenerCatalogosPorGrupo(idGrupo);
        } catch (Exception ex) {
            throw new Exception("Error obtener. " + ex.getMessage());
        }
       
    }

    @Override
    public List<CatalogoGeneral> obtenerTiposInsumos(int idGrupo, String idEstructura) throws Exception {
        try{
            return catalogoGeneralMapper.obtenerTiposInsumos(idGrupo,idEstructura);
        }catch(Exception ex){
            throw new Exception("Error tipos de Insumos " + ex.getMessage());
        }
    }

    @Override
    public String obtenerPorIdCatalogoGrl(int idCatalogo,int idGrupo) throws Exception {
        String name="";
        try{
            CatalogoGeneral catalogo = new CatalogoGeneral();
            catalogo.setIdCatalogoGeneral(idCatalogo);
            catalogo.setIdGrupo(idGrupo);
            catalogo =catalogoGeneralMapper.obtener(catalogo);
            
            if(catalogo!= null)
                name=catalogo.getNombreCatalogo();
                        
        }catch(Exception ex){
            throw new Exception("Error tipos de Insumos " + ex.getMessage());
        }
        return name;
    }
}
