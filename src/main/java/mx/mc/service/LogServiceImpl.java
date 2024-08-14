package mx.mc.service;

import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.Log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * @author hramirez
 */
@Service
public class LogServiceImpl extends GenericCrudServiceImpl<Log, String> implements LogService {

    @Autowired
    public LogServiceImpl(GenericCrudMapper<Log, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
}
