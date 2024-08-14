package mx.mc.mapper;

import java.util.Date;
import mx.mc.model.Turno;
import org.apache.ibatis.annotations.Param;

/**
 * @author aortiz
 */
public interface TurnoMapper extends GenericCrudMapper<Turno, String> {

    public Turno obtenerPorHora(@Param("hora") Date hora);

}
