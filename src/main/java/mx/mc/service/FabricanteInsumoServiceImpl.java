package mx.mc.service;

import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.FabricanteInsumo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * @author hramirez
 */
@Service
public class FabricanteInsumoServiceImpl extends GenericCrudServiceImpl<FabricanteInsumo, String> implements FabricanteInsumoService {
    
    @Autowired
    public FabricanteInsumoServiceImpl(GenericCrudMapper<FabricanteInsumo, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
    
}
