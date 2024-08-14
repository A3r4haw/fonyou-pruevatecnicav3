package mx.mc.service;

import java.util.Date;
import java.util.List;
import mx.mc.model.SurtimientoInsumo;
import mx.mc.model.SurtimientoInsumo_Extend;

/**
 *
 * @author hramirez
 */
public interface RecetaColectivaService extends GenericCrudService<SurtimientoInsumo, String> {

    public List<SurtimientoInsumo_Extend> obtenerSurtimientosProgramados(Date fechaProgramada 
            , String idSurtimiento 
            , String idPrescripcion 
            , List<Integer> listEstatusSurtimientoInsumo 
            , List<Integer> listEstatusSurtimiento
            , String idEstructura
            , boolean surtimientoMixto) throws Exception;
    
     public List<SurtimientoInsumo_Extend> obtenerSurtimientoInsumoExtendedByIdPrescripcion(
             String idPrescripcion) throws Exception;
}
