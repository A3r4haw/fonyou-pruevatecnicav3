package mx.mc.service;

import java.util.Date;
import java.util.List;
import mx.mc.dto.SurtimientoValidarDTO;
import mx.mc.model.MovimientoInventario;
import mx.mc.model.SurtimientoMinistrado;
import mx.mc.model.SurtimientoMinistrado_Extend;

/**
 *
 * @author aortiz
 */
public interface SurtimientoMinistradoService extends GenericCrudService<SurtimientoMinistrado, String> {
    
     public boolean actualizarSurtiminetoMinistrado(
            List<SurtimientoMinistrado> listSurtiminetoMinistrado ,
            List<MovimientoInventario> listaMovInv) throws Exception;
     
     List<SurtimientoMinistrado> obtenerSurtimientosMinistrados(List<String> listaSurtimientoInsumos) throws Exception;
     
     boolean actualizarSurtMinistradoConfirmado(List<SurtimientoMinistrado> listMinistracion) throws Exception;     
     
     List<SurtimientoMinistrado_Extend> obtenerSurtimientoMinistradoLazzy(String idEstructura, String idUsuario, int startingAt, int maxPerPage) throws Exception;
     
     Long obtenerTotalSurtimientoMinistradoLazzy(String idEstructura, String idUsuario) throws Exception;

     public List<SurtimientoMinistrado_Extend> obtenerListSurtimientoMinistradoSolucion(String claveAgrupada) throws Exception;
     
     public List<SurtimientoMinistrado_Extend> obtenerMinistracionesPaciente(String idPaciente, Date fechaInicio, Date fechaFin) throws Exception;
     
     public List<SurtimientoMinistrado_Extend> obtenerUltimasMinistracionesPac(String idPaciente) throws Exception;
     
    public List<SurtimientoValidarDTO> obtenerSurtimientosAValidar(String idPrescripcionInsumo) throws Exception;
    
    public List<SurtimientoValidarDTO> obtenerSurtimientosAValidarOrdenadoASC(String idPrescripcionInsumo) throws Exception;
    
    public SurtimientoMinistrado_Extend obtenerSurtimientoMinistradoByIdSurtimiento(String idSurtimiento) throws Exception;
    
}
