package mx.mc.service;

import java.util.List;

import mx.mc.model.ReabastoEnviado;
import mx.mc.model.ReabastoEnviadoExtended;
import mx.mc.model.ReabastoInsumo;

/**
 *
 * @author AORTIZ
 */
public interface ReabastoEnviadoService extends GenericCrudService<ReabastoEnviado, String> {

    List<ReabastoEnviadoExtended> obtenerListaReabastoEnviado(String idReabastoInsumo, List<Integer> listEstatusReabasto) throws Exception;

    boolean actualizarListaReabastoEnviado(List<ReabastoEnviado> listReabastoEnviado) throws Exception;

    boolean eliminarReabastoEnviado(
            ReabastoEnviado reabastoEnviado, ReabastoInsumo reabastoInsumo) throws Exception;

    ReabastoEnviadoExtended obtenerInventarioPorClveInstEstructuraYLote(ReabastoEnviadoExtended reabastoEnviadoExtended) throws Exception;

    List<ReabastoEnviadoExtended> obtenerListaReabastoEnviadoInv(String idReabastoInsumo, int idEstatusReabasto, String idEstructura) throws Exception;

    public List<ReabastoEnviado> obtenerReabastoEnviadoByListaReabastoInsumo(
            List<ReabastoInsumo> listaReabastoInsumo) throws Exception;

    public List<ReabastoEnviadoExtended> obtenerReabastoEnviadoPorIdEstructuraIdInsumoIdInventario(String idEstructura , String idInsumo, String idInventario ) throws Exception;

}
