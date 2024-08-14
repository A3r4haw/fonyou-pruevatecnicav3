package mx.mc.mapper;

import mx.mc.model.Cita;
import org.apache.ibatis.annotations.Param;

/**
 * @author gcruz
 */
public interface CitaMapper extends GenericCrudMapper<Cita, String> {
    public Cita obtenerById(@Param("idCita") String idCita);
    public boolean updateCita(Cita cita);
    public boolean cancelCitaById(@Param("idCita") String idCita);
    public Cita existeCita(Cita cita);    
    public boolean actualizarEstatusCitas(Cita cita);



}
