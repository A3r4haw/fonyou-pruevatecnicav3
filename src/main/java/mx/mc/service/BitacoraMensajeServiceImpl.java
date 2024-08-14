package mx.mc.service;

import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.BitacoraMensajeMapper;
import mx.mc.model.BitacoraMensaje;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * @author hramirez
 */
@Service
public class BitacoraMensajeServiceImpl extends GenericCrudServiceImpl<BitacoraMensaje, String> implements BitacoraMensajeService {
    
    @Autowired
    private BitacoraMensajeMapper bitacoraMensajeMapper;

    @Autowired
    public BitacoraMensajeServiceImpl(GenericCrudMapper<BitacoraMensaje, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public void updateMensajeBitacora(String idBitacoraMensaje, String codigoRespuesta) throws Exception {
        try {
            bitacoraMensajeMapper.updateMensajeBitacora(idBitacoraMensaje, codigoRespuesta);
        } catch (Exception e) {
            throw new Exception("Error al actualizar la BitacoraMensaje " + e.getMessage());
        }
    }

    @Override
    public BitacoraMensaje validaExistenciaBitacora(String idMensage) throws Exception {        
        try {
            return bitacoraMensajeMapper.validaExistenciaBitacora(idMensage);            
        } catch (Exception e) {
            throw new Exception("Error al Consultar validaExistenciaBitacora " + e.getMessage());
        }        
    }

}
