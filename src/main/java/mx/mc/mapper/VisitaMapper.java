package mx.mc.mapper;

import mx.mc.model.Visita;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author hramirez
 */
public interface VisitaMapper extends GenericCrudMapper<Visita, String> {
    
    public Visita obtenerVisitaAbierta(Visita visita);
    
    public Visita obtenerVisitaPorNumero(@Param("numVisita") String numeroVisita);
    
    public Visita obtenerVisitaDelDia(@Param("claveDerechoHabiencia") String ClaveDerechohabiencia);
}
