package mx.mc.service;

import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.Pais;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author AORTIZ
 */
@Service
public class PaisServiceImplements extends GenericCrudServiceImpl<Pais, String> implements PaisService {

    @Autowired
    public PaisServiceImplements(GenericCrudMapper<Pais, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
}
