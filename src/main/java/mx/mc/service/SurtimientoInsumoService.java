package mx.mc.service;

import java.util.Date;
import java.util.List;
import mx.mc.model.PrescripcionInsumo;
import mx.mc.model.SurtimientoEnviado;
import mx.mc.model.SurtimientoInsumo;
import mx.mc.model.SurtimientoInsumo_Extend;

/**
 *
 * @author hramirez
 */
public interface SurtimientoInsumoService extends GenericCrudService<SurtimientoInsumo, String> {

    public List<SurtimientoInsumo_Extend> obtenerSurtimientosProgramados(Date fechaProgramada 
            , String idSurtimiento 
            , String idPrescripcion 
            , List<Integer> listEstatusSurtimientoInsumo 
            , List<Integer> listEstatusSurtimiento
            , String idEstructura
            , boolean surtimientoMixto) throws Exception;
    
    public List<SurtimientoInsumo_Extend> obtenerSurtimientosProgramadosExt(Date fechaProgramada 
            , String idSurtimiento 
            , String idPrescripcion 
            , List<Integer> listEstatusSurtimientoInsumo 
            , List<Integer> listEstatusSurtimiento
            , String idEstructura
            , boolean surtimientoMixto) throws Exception;
    
    boolean clonarSurtimientoInsumo(String idSurtimiento, String newFolio) throws Exception;       
    
     public List<SurtimientoInsumo_Extend> obtenerSurtimientoInsumoExtendedByIdPrescripcion(
             String idPrescripcion) throws Exception;
     
     public SurtimientoInsumo_Extend getSurtimientoByTracking(String folio,String numeroPaciente,
             String claveInstitucional,String lote,Date fechaCaducidad)throws Exception;
     
     public SurtimientoInsumo_Extend getSurtimientoByTrackingByFolio(String folio,String numeroPaciente,String claveInstitucional,int numeroMedicacion)throws Exception;
     
     public SurtimientoInsumo_Extend getSurtimientoByTrackingByPresInsumo(String folio,String claveInstitucional,int numeroPrescripcion)throws Exception;
     
     boolean clonarSurtimientoInsumoCancelado(String idSurtimiento, String newFolio) throws Exception;
     
     public List<SurtimientoInsumo> obtenerListaSurtiminetoInsumo(List<String> listaInsumo)throws Exception;
     
     public SurtimientoInsumo_Extend obtenerSurtInsumosByIdSurtimientoInsumo(String idSurtimientoInsumo)throws Exception;
     
     public SurtimientoInsumo_Extend obtenerSurtimientoInsumoByIdSurtimiento(String idSurtimiento) throws Exception;
     
     public List<SurtimientoInsumo_Extend> obtenerSurtimInsumoByClaveAgrupada(String claveAgrupada) throws Exception;
     
     public List<SurtimientoInsumo_Extend> obtenerSurtimientoInsumosByIdSurtimiento(String idSurtimiento, boolean mayorCero) throws Exception;
     
     public boolean actualizarSurtimientoInsumoList(List<SurtimientoInsumo> insumos) throws Exception;
     
     public boolean insertarInsumo(PrescripcionInsumo item, SurtimientoInsumo insumo) throws Exception;
     
     public boolean eliminarInsumo(PrescripcionInsumo item, SurtimientoInsumo insumo) throws Exception;
     
     public List<SurtimientoEnviado> obtenerSurtimientoEnviados(String idSurtimiento) throws Exception;
     
     List<SurtimientoInsumo> obtenerListSurtimientosInsumosByIdSurtimiento(String idSurtimiento) throws Exception;
          
    public List<SurtimientoInsumo_Extend> obtenerDetalleReceta(String idPrescripcion) throws Exception;
    
    public List<SurtimientoInsumo_Extend> obtenerListaByIdSurtimiento(String idSurtimiento) throws Exception;
    
    public boolean eliminaPorIdSurtimiento(String idSurtimiento) throws Exception;
    
    public List<SurtimientoInsumo_Extend> obtenerMedicamentosPrescritosPorPaciente(
            String idPaciente 
            , List<String> idInsumoList 
            , Integer tipoInsumo 
            , Integer noHrsPrev 
            , Integer noHrsPost 
            , List<Integer> listEstatusPrescripcion 
            , List<Integer> listEstatusSurtimientoInsumo 
    ) throws Exception;
    
    public List<SurtimientoInsumo_Extend> obtenerSurtimientosProgramadosLoteSug(
            Date fechaProgramada 
            , String idSurtimiento 
            , String idPrescripcion 
            , List<Integer> listEstatusSurtimientoInsumo
            , List<Integer> listEstatusSurtimiento
            , List<String> idEstructuraList 
            , boolean surtimientoMixto) throws Exception;
    
    public List<SurtimientoInsumo_Extend> obtenerDetalleSolucion(String idSolucion) throws Exception;
    
}
