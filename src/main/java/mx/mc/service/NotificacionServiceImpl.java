package mx.mc.service;

import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.Notificacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * @author hramirez
 */
@Service
public class NotificacionServiceImpl extends GenericCrudServiceImpl<Notificacion, String> implements NotificacionService {
    
    @Autowired
    public NotificacionServiceImpl(GenericCrudMapper<Notificacion, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
}
