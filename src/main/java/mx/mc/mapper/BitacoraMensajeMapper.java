package mx.mc.mapper;

import mx.mc.model.BitacoraMensaje;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author hramirez
 */
public interface BitacoraMensajeMapper extends GenericCrudMapper<BitacoraMensaje, String> {

public void updateMensajeBitacora(
            @Param("idBitacoraMensaje")String idBitacoraMensaje,@Param("codigoRespuesta")String codigoRespuesta);    

public BitacoraMensaje validaExistenciaBitacora(@Param("idMensaje") String idMensaje);

}
