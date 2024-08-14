package mx.mc.service;

import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.PlantillaCorreoMapper;
import mx.mc.model.PlantillaCorreo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Cervanets
 */
@Service
public class PlantillaCorreoServiceImpl extends GenericCrudServiceImpl<PlantillaCorreo, String> implements PlantillaCorreoService {

    @Autowired
    PlantillaCorreoMapper adjuntoMapper;

    @Autowired
    public PlantillaCorreoServiceImpl(GenericCrudMapper<PlantillaCorreo, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

}
