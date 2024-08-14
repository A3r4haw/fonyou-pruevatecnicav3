package mx.mc.service;

import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.NotificacionLeida;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * @author hramirez
 */
@Service
public class NotificacionLeidaServiceImpl extends GenericCrudServiceImpl<NotificacionLeida, String> implements NotificacionLeidaService {

    @Autowired
    public NotificacionLeidaServiceImpl(GenericCrudMapper<NotificacionLeida, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
}
