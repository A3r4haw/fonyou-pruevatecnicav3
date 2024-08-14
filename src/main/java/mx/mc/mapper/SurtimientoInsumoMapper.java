package mx.mc.mapper;

import java.util.Date;
import java.util.List;
import mx.mc.model.SurtimientoEnviado_Extend;
import mx.mc.model.SurtimientoInsumo;
import mx.mc.model.SurtimientoInsumo_Extend;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author hramirez
 */
public interface SurtimientoInsumoMapper extends GenericCrudMapper<SurtimientoInsumo, String> {

    boolean registraSurtimientoInsumoList (List<SurtimientoInsumo> surtimientoList);
    
    boolean registraSurtimientoInsumoExtendedList (List<SurtimientoInsumo_Extend> surtimientoList);
    
    boolean eliminarPorIdPrescripcion (@Param("idPrescripcion") String idPrescripcion);
    
    boolean cancelar (@Param("idPrescripcion") String idPrescripcion
            , @Param("idUsuario") String idUsuario
            , @Param("fecha") Date fecha
            , @Param("idEstatusSurtimiento") Integer idEstatusSurtimiento);
    
    boolean cancelarSurtimientoInsumos (@Param("idSurtimiento") String idSurtimiento
            , @Param("idUsuario") String idUsuario
            , @Param("fecha") Date fecha
            , @Param("idEstatusSurtimiento") Integer idEstatusSurtimiento);
    
    List<SurtimientoInsumo_Extend> obtenerSurtimientosProgramados(
            @Param("fechaProgramada") Date fechaProgramada 
            , @Param("idSurtimiento") String idSurtimiento 
            , @Param("idPrescripcion") String idPrescripcion 
            , @Param("listEstatusSurtimientoInsumo") List<Integer> listEstatusSurtimientoInsumo 
            , @Param("listEstatusSurtimiento") List<Integer> listEstatusSurtimiento 
            , @Param("idEstructura") String idEstructura 
            , @Param("surtimientoMixto") boolean surtimientoMixto 
    );
    
    List<SurtimientoInsumo_Extend> obtenerSurtimientosProgramadosExt(
            @Param("fechaProgramada") Date fechaProgramada 
            , @Param("idSurtimiento") String idSurtimiento 
            , @Param("idPrescripcion") String idPrescripcion 
            , @Param("listEstatusSurtimientoInsumo") List<Integer> listEstatusSurtimientoInsumo 
            , @Param("listEstatusSurtimiento") List<Integer> listEstatusSurtimiento 
            , @Param("idEstructura") String idEstructura 
            , @Param("surtimientoMixto") boolean surtimientoMixto 
    );
    
    List<SurtimientoInsumo_Extend> obtenerSurtimientoInsumoExtended(String idPrescripcion);
    
    boolean actualizarSurtimientoInsumoList (List<SurtimientoInsumo> surtimientoList);
    boolean actualizarSurtimientoInsumoListTraking (List<SurtimientoInsumo> surtimientoList);
    boolean actualizarSurtimientoInsumoListVales (List<SurtimientoInsumo> surtimientoList);
    boolean actualizarEstatusSurtimientoInsumoList (List<SurtimientoInsumo> surtimientoList);
    boolean revertirSusrtimiento0(SurtimientoInsumo insumo);
    boolean revertirSusrtimiento2(SurtimientoInsumo insumo);
    boolean clonarSusrtimientoPorIdSurtimiento(@Param("idSurtimiento") String idSurtimiento,@Param("newFolio") String newFolio);
    List<SurtimientoInsumo_Extend> obtenerByIdSurtimientoRecepMedi(@Param("idSurtimiento") String idSurtimiento);
    
    boolean actualizarSurInsPorFolioEstatusIntipharm(@Param("folio") String folio, @Param("estatus") int estatus,String idUsuario,Date updatefecha);        
    
    public SurtimientoInsumo_Extend getSurtimientoByTracking(@Param("folioSurtimiento") String folioSurtimiento, 
            @Param("numeroPaciente") String numeroPaciente,
            @Param("claveInstitucional") String claveInstitucional, @Param("lote") String lote,@Param("fechaCaducidad") Date fechaCaducidad);
    
    public SurtimientoInsumo_Extend getSurtimientoByTrackingByFolio(@Param("folioSurtimiento") String folioSurtimiento, 
            @Param("numeroPaciente") String numeroPaciente,@Param("claveInstitucional") String claveInstitucional,@Param("numeroMedicacion") int numeroMedicacion);
    
    public SurtimientoInsumo_Extend getSurtimientoByTrackingByPresInsumo(@Param("folioSurtimiento") String folioSurtimiento, 
            @Param("claveInstitucional") String claveInstitucional,@Param("numeroMedicacion") int numeroMedicacion);        
    
    boolean clonarSurtimientoCanceladoPorIdSurtimiento(@Param("idSurtimiento") String idSurtimiento,@Param("newFolio") String newFolio);
            
    List<SurtimientoInsumo_Extend> obtenerSurtInsumosByIdSurtimiento(@Param("idSurtimiento") String idSurtimiento);
    
    List<SurtimientoEnviado_Extend> obtenerListaSurtimientoEnviadoPorIdSurtimientoDevolucion(@Param("idSurtimiento") String idSurtimiento);
    
    List<SurtimientoInsumo> obtenerListaSurtiminetoInsumo(@Param("listInsumo")  List<String> listInsumo);
    
    boolean resurteSurtimientoInsumoList(List<SurtimientoInsumo> surtimientoEnviadoList);
    
    SurtimientoInsumo_Extend obtenerSurtInsumosByIdSurtimientoInsumo(@Param("idSurtimientoInsumo") String idSurtimientoInsumo);
    
    SurtimientoInsumo_Extend obtenerSurtimientoInsumoByIdSurtimiento(@Param("idSurtimiento") String idSurtimiento);
    
    boolean updateAsignSurtimientoInsumo(SurtimientoInsumo_Extend surtObj);
  
    List<SurtimientoInsumo_Extend> obtenerSurtimInsumoByClaveAgrupada(@Param("claveAgrupada") String claveAgrupada);
    
    List<SurtimientoInsumo_Extend> obtenerSurtimientoInsumosByIdSurtimiento(@Param("idSurtimiento") String idSurtimiento, @Param("mayorCero") boolean mayorCero);
    
    boolean actualizarLista(@Param("insumos")List<SurtimientoInsumo> insumos);
    
    List<SurtimientoInsumo> obtenerListSurtimientosInsumosByIdSurtimiento(@Param("idSurtimiento") String idSurtimiento);
    
    List<SurtimientoInsumo_Extend> obtenerDetalleReceta(@Param("idPrescripcion") String idPrescripcion);
    
    List<SurtimientoInsumo_Extend> obtenerListaByIdSurtimiento(@Param("idSurtimiento") String idSurtimiento);
    
    boolean eliminaPorIdSurtimiento(@Param("idSurtimiento") String idSurtimiento);
    
    List<SurtimientoInsumo_Extend> obtenerMedicamentosPrescritosPorPaciente(
            @Param("idPaciente") String idPaciente 
            , @Param("idInsumoList") List<String> idInsumoList 
            , @Param("tipoInsumo") Integer tipoInsumo 
            , @Param("noHrsPrev") Integer noHrsPrev 
            , @Param("noHrsPost") Integer noHrsPost 
            , @Param("listEstatusPrescripcion") List<Integer> listEstatusPrescripcion 
            , @Param("listEstatusSurtimientoInsumo") List<Integer> listEstatusSurtimientoInsumo 
    );
    
    
    List<SurtimientoInsumo_Extend> obtenerSurtimientosProgramadosLoteSug(
            @Param("fechaProgramada") Date fechaProgramada 
            , @Param("idSurtimiento") String idSurtimiento 
            , @Param("idPrescripcion") String idPrescripcion 
            , @Param("listEstatusSurtimientoInsumo") List<Integer> listEstatusSurtimientoInsumo 
            , @Param("listEstatusSurtimiento") List<Integer> listEstatusSurtimiento 
            , @Param("idEstructuraList") List<String> idEstructuraList 
            , @Param("surtimientoMixto") boolean surtimientoMixto 
    );
    
    List<SurtimientoInsumo_Extend> obtenerDetalleSolucion(@Param("idSolucion") String idSolucion);
    
    List<SurtimientoInsumo> obtenerSurtimientosInsumos(@Param("idPrescripcion") String idPrescripcion);
    
}
