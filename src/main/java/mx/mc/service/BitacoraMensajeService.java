package mx.mc.service;

import mx.mc.model.BitacoraMensaje;

/**
 *
 * @author mcalderon
 */
public interface BitacoraMensajeService extends GenericCrudService<BitacoraMensaje, String> {

    public void updateMensajeBitacora(String idBitacora,String codigoRespuesta)throws Exception;
    
    public BitacoraMensaje validaExistenciaBitacora(String idMensage) throws Exception;
    
}
