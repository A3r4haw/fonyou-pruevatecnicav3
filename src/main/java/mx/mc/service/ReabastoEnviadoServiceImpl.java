package mx.mc.service;

import java.util.ArrayList;
import java.util.List;

import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.ReabastoEnviadoMapper;
import mx.mc.mapper.ReabastoInsumoMapper;
import mx.mc.model.ReabastoEnviado;
import mx.mc.model.ReabastoEnviadoExtended;
import mx.mc.model.ReabastoInsumo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author AORTIZ
 */
@Service
public class ReabastoEnviadoServiceImpl extends GenericCrudServiceImpl<ReabastoEnviado, String> implements ReabastoEnviadoService {

    @Autowired
    private ReabastoEnviadoMapper reabastoEnviadoMapper;
    
    @Autowired
    private ReabastoInsumoMapper reabastoInsumoMapper;

    public ReabastoEnviadoServiceImpl() {
        //No code needed in constructor
    }
    
    @Autowired
    public ReabastoEnviadoServiceImpl(GenericCrudMapper<ReabastoEnviado, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public List<ReabastoEnviadoExtended> obtenerListaReabastoEnviado(String idReabastoInsumo, List<Integer> listEstatusReabasto) throws Exception {
            List<ReabastoEnviadoExtended> listaReabastoEnviado = new ArrayList<>();
            try {
                    listaReabastoEnviado = reabastoEnviadoMapper.obtenerListaReabastoEnviado(idReabastoInsumo, listEstatusReabasto);
            } catch(Exception ex) {
                    throw new Exception("Error al obtener la lista reabasto enviado "+ex.getMessage());
            }
            return listaReabastoEnviado;
    }
	
    @Override
    public boolean actualizarListaReabastoEnviado(List<ReabastoEnviado> listReabastoEnviado) throws Exception {
            try {
                    return reabastoEnviadoMapper.actualizarListaReabastoEnviado(listReabastoEnviado);
            } catch( Exception ex) {
                    throw new Exception("Ocurrio un error al momento de actualizar la lista reabastoEnviado  " + ex.getMessage());
            }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean eliminarReabastoEnviado(
            ReabastoEnviado reabastoEnviado,ReabastoInsumo reabastoInsumo) throws Exception {
        try {
                boolean resp = reabastoEnviadoMapper.eliminar(reabastoEnviado);
                if (resp) {
                   resp = reabastoInsumoMapper.actualizar(reabastoInsumo);
                }
                return resp;
            } catch( Exception ex) {
                throw new Exception("Ocurrio un error al momento de actualizar la lista reabastoEnviado  " + ex.getMessage());
            }
    }

    @Override
    public ReabastoEnviadoExtended obtenerInventarioPorClveInstEstructuraYLote(ReabastoEnviadoExtended reabastoEnviadoExtended) throws Exception {
        try {           
            return reabastoEnviadoMapper.obtenerInventarioPorClveInstEstructuraYLote(reabastoEnviadoExtended);
        } catch (Exception ex) {
            throw new Exception("Ocurrio un error al momento de actualizar la lista reabastoEnviado  " + ex.getMessage());
        }
    }
    
    @Override
    public List<ReabastoEnviadoExtended> obtenerListaReabastoEnviadoInv(String idReabastoInsumo, int idEstatusReabasto, String idEstructura) throws Exception {
            List<ReabastoEnviadoExtended> listaReabastoEnviado = new ArrayList<>();
            try {
                    listaReabastoEnviado = reabastoEnviadoMapper.obtenerListaReabastoEnviadoInv(idReabastoInsumo, idEstatusReabasto, idEstructura);
            } catch(Exception ex) {
                    throw new Exception("Error al obtener la lista reabasto enviado "+ex.getMessage());
            }
            return listaReabastoEnviado;
    }
    
    @Override
    public List<ReabastoEnviado> obtenerReabastoEnviadoByListaReabastoInsumo(
            List<ReabastoInsumo> listaReabastoInsumo) throws Exception {
        try {           
            return reabastoEnviadoMapper.obtenerReabastoEnviadoByListaReabastoInsumo(listaReabastoInsumo);
        } catch (Exception ex) {
            throw new Exception("Ocurrio un error al momento de actualizar la lista reabastoEnviado  " + ex.getMessage());
        }
    }
    
    @Override
    public List<ReabastoEnviadoExtended> obtenerReabastoEnviadoPorIdEstructuraIdInsumoIdInventario(String idEstructura , String idInsumo, String idInventario ) throws Exception {
        try {           
            return reabastoEnviadoMapper.obtenerReabastoEnviadoPorIdEstructuraIdInsumoIdInventario(idEstructura, idInsumo, idInventario);
        } catch (Exception ex) {
            throw new Exception("Ocurrio un error al momento de obtener la lista reabastoEnviado por idEstructura , idInsumo y idInventario " + ex.getMessage());
        }
    }
    
}
