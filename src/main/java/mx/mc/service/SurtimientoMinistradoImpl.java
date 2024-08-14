package mx.mc.service;

import java.util.Date;
import java.util.List;
import mx.mc.dto.SurtimientoValidarDTO;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.MovimientoInventarioMapper;
import mx.mc.mapper.SurtimientoMinistradoMapper;
import mx.mc.model.MovimientoInventario;
import mx.mc.model.SurtimientoMinistrado;
import mx.mc.model.SurtimientoMinistrado_Extend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * @author aortiz
 */
@Service
public class SurtimientoMinistradoImpl extends GenericCrudServiceImpl<SurtimientoMinistrado, String> implements SurtimientoMinistradoService {
    
    @Autowired
    private SurtimientoMinistradoMapper surtimientoMinistradoMapper;
    
    @Autowired
    private MovimientoInventarioMapper movimientoInventarioMapper;

    @Autowired
    public SurtimientoMinistradoImpl(GenericCrudMapper<SurtimientoMinistrado, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public boolean actualizarSurtiminetoMinistrado(
            List<SurtimientoMinistrado> listSurtiminetoMinistrado ,
            List<MovimientoInventario> listaMovInv) throws Exception{
        boolean resp = false;
        try {
            resp  = surtimientoMinistradoMapper.actualizarSurtimientoMinistrado(listSurtiminetoMinistrado);
            if (!resp) {
                throw new Exception("Error en el metodo :: actualizarSurtimientoMinistrado"); 
            }
            if (listaMovInv != null && !listaMovInv.isEmpty()) {
                resp =  movimientoInventarioMapper.insertarMovimientosInventarios(listaMovInv);
                if (!resp) {
                    throw new Exception("Error en el metodo :: insertarMovimientosInventarios"); 
                }
            }
        } catch (Exception e) {
            throw new Exception("Error en el metodo :: actualizarSurtiminetoMinistrado()" + e.getMessage());
        }
        return resp;
    }

    @Override
    public List<SurtimientoMinistrado> obtenerSurtimientosMinistrados(List<String> listaSurtimientoInsumos) throws Exception{
        try {
            return surtimientoMinistradoMapper.obtenerSurtimientosMinistrados(listaSurtimientoInsumos);
       } catch(Exception ex) {
           throw new Exception("Error en el metodo :: obtenerSurtimientosMinistrados  " + ex.getMessage());
       }
    }
    
    @Override
    public boolean actualizarSurtMinistradoConfirmado(List<SurtimientoMinistrado> listMinistracion) throws Exception {
        try {
            return surtimientoMinistradoMapper.actualizarSurtMinistradoConfirmado(listMinistracion);
        } catch(Exception ex) {
            throw new Exception("Error en el metodo :: actualizarSurtMinistradoConfirmado  " + ex.getMessage());
        }
    }
    
    @Override
    public List<SurtimientoMinistrado_Extend> obtenerSurtimientoMinistradoLazzy(String idEstructura, String idUsuario, int startingAt, int maxPerPage) throws Exception {
        try {
            return surtimientoMinistradoMapper.obtenerSurtimientoMinistradoLazzy(idEstructura, idUsuario, startingAt, maxPerPage);
        } catch(Exception ex) {
            throw new Exception("Error en el metodo :: obtenerSurtimientoMinistrado  " + ex.getMessage());
        }
    }
    
    @Override
    public Long obtenerTotalSurtimientoMinistradoLazzy(String idEstructura, String idUsuario) throws Exception {
        try {
            return surtimientoMinistradoMapper.obtenerTotalSurtimientoMinistradoLazzy(idEstructura, idUsuario);
        } catch (Exception ex) {
            throw new Exception("Error en el metodo :: obtenerTotalSurtimientoMinistradoLazzy    " + ex.getMessage());
        }
    }

    @Override
    public List<SurtimientoMinistrado_Extend> obtenerListSurtimientoMinistradoSolucion(String claveAgrupada) throws Exception {
        try {
            return surtimientoMinistradoMapper.obtenerListSurtimientoMinistradoSolucion(claveAgrupada);
        } catch (Exception ex) {
            throw new Exception("Error en el metodo :: obtenerListSurtimientoMinistradoSolucion " + ex.getMessage());
        }
    }
    
    @Override
    public List<SurtimientoMinistrado_Extend> obtenerMinistracionesPaciente(String idPaciente, Date fechaInicio, Date fechaFin) throws Exception {
        try {
            return surtimientoMinistradoMapper.obtenerMinistracionesPaciente(idPaciente,fechaInicio,fechaFin);
        } catch (Exception ex) {
            throw new Exception("Error en el metodo :: obtenerMinistracionesPaciente " + ex.getMessage());
        }
    }
    
    @Override
    public List<SurtimientoMinistrado_Extend> obtenerUltimasMinistracionesPac(String idPaciente) throws Exception {
        try {
            return surtimientoMinistradoMapper.obtenerUltimasMinistracionesPac(idPaciente);
        } catch (Exception ex) {
            throw new Exception("Error en el metodo :: obtenerMinistracionesPaciente " + ex.getMessage());
        }
    }

    @Override
    public List<SurtimientoValidarDTO> obtenerSurtimientosAValidar(String idPrescripcionInsumo) throws Exception {
        try {
            return surtimientoMinistradoMapper.obtenerSurtimientosAValidar(idPrescripcionInsumo);
        } catch(Exception ex) {
            throw new Exception("Error al buscar obtenerSurtimientosAValidar   " + ex.getMessage());
        }
    }
    
    @Override
    public List<SurtimientoValidarDTO> obtenerSurtimientosAValidarOrdenadoASC(String idPrescripcionInsumo) throws Exception {
        try {
            return surtimientoMinistradoMapper.obtenerSurtimientosAValidarOrdenadoASC(idPrescripcionInsumo);
        } catch(Exception ex) {
            throw new Exception("Error al buscar obtenerSurtimientosAValidar   " + ex.getMessage());
        }
    }

    @Override
    public SurtimientoMinistrado_Extend obtenerSurtimientoMinistradoByIdSurtimiento(String idSurtimiento) throws Exception {
        try {
            return surtimientoMinistradoMapper.obtenerSurtimientoMinistradoByIdSurtimiento(idSurtimiento);
        } catch(Exception ex) {
            throw new Exception("Error al buscar obtenerSurtimientoMinistradoByIdSurtimiento   " + ex.getMessage());
        }
    }
}
