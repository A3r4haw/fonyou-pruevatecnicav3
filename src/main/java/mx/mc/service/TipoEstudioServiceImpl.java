package mx.mc.service;

import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.TipoEstudio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipoEstudioServiceImpl extends GenericCrudServiceImpl<TipoEstudio, String> implements TipoEstudioService{

    @Autowired
    public TipoEstudioServiceImpl(GenericCrudMapper<TipoEstudio, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
}
