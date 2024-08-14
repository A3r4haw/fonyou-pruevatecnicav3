package mx.mc.mapper;

import java.util.List;
import mx.mc.model.Consumo;

import org.apache.ibatis.annotations.Param;

import mx.mc.model.DataResultReport;
import mx.mc.model.DataResultVales;
import mx.mc.model.DataResultAlmacenes;
import mx.mc.model.DataResultConsolidado;
import mx.mc.model.Mezcla;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.ParamLibMedControlados;
import mx.mc.model.ReportInventarioExistencias;
import mx.mc.model.ReporteConcentracionArticulos;
import mx.mc.model.ReporteEstatusInsumo;
import mx.mc.model.ReporteSurtidoServicio;
import mx.mc.model.ReporteLibroControlados;
import mx.mc.model.SurtimientoMinistrado_Extend;
/**
 * 
 * @author gcruz
 *
 */
public interface ReporteMovimientosMapper { 
	
	List<DataResultReport> consultarMovimiento(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte,
			@Param("startingAt") int startingAt, @Param("maxPerPage") int maxPerPage);
	
	Long obtenerTotalRegistros(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte);
	
	List<ReportInventarioExistencias> consultarInventarioExistencias(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte,
			@Param("startingAt") int startingAt, @Param("maxPerPage") int maxPerPage,@Param("sortField") String sortField,@Param("sortOrder") String sortOrder);
        
        List<ReportInventarioExistencias> consultarInventarioExistenciasGlobal(
                @Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte);
	
	Long getTotalRegistrosInventarioExistencias(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte);
        
        /**Metodo para ReporteMovimiento.xhtm*/     
        
        List<DataResultVales> consultarEmisionVales(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte,
			@Param("startingAt") int startingAt, @Param("maxPerPage") int maxPerPage);
	        
        Long obtenerTotalRegistrosVales(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte);
        
        
        /*Movimientos de Recetas*/          
        List<DataResultVales> consultarEmisionRecetas(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte,
                        @Param("startingAt") int startingAt, @Param("maxPerPage") int maxPerPage);
        
        Long obtenerTotalRegistrosRecetas(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte);
        
        List<DataResultVales> consultarRecetasColectivas(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte,
                        @Param("startingAt") int startingAt, @Param("maxPerPage") int maxPerPage);
        
        Long obtenerTotalReceColectivas(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte);
        
        
        /*Movimientos de cancelaciones*/
        List<DataResultVales> consultarCancelaciones(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte,
                        @Param("startingAt") int startingAt, @Param("maxPerPage") int maxPerPage);
                
        Long obtenerTotalCancelaciones(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte);
        
        //Movimientos de Almacenes
        List<DataResultAlmacenes> rptConsultarAlmacenes(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte,
                @Param("startingAt") int startingAt, @Param("maxPerPage") int maxPerPage);
        
        List<DataResultAlmacenes> consultarAlmacenes(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte,
                @Param("startingAt") int startingAt, @Param("maxPerPage") int maxPerPage);
        
        Long rptObtenerTotalAlmacenes(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte);
        
        Long obtenerTotalAlmacenes(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte);
        
        List<ReportInventarioExistencias> listInsumosInventarioAlmacen(@Param("idEstructura") String idEstructura,@Param("cadena") String cadena);
        
                     //reporteAcumulados chiconcuac
        
        List<DataResultReport> consultarAcumulados(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte,
			@Param("startingAt") int startingAt, @Param("maxPerPage") int maxPerPage);
	
	Long obtenerTotalAcumulados(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte);
        
        List<DataResultReport> consultarAcumuladosMedico(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte,
			@Param("startingAt") int startingAt, @Param("maxPerPage") int maxPerPage);
	
	Long obtenerTotalAcumuladosMedico(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte);
        
        List<DataResultReport> consultarAcumuladosPaciente(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte,
			@Param("startingAt") int startingAt, @Param("maxPerPage") int maxPerPage);
	
	Long obtenerTotalAcumuladosPaciente(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte);
        
        List<DataResultReport> consultarAcumuladosColectivo(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte,
			@Param("startingAt") int startingAt, @Param("maxPerPage") int maxPerPage);
	
	Long obtenerTotalAcumuladosColectivo(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte);
        
        
        //reporte de Ministraciones
        
    List<SurtimientoMinistrado_Extend> consultarMinistraciones(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte,
            @Param("estructuraList") List<String> estructuraList,            
            @Param("startingAt") int startingAt, @Param("maxPerPage") int maxPerPage);

    Long obtenerTotalRegistrosMinistraciones(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte
            ,@Param("estructuraList") List<String> estructuraList);
    
    // Reporte Estatus Ministraciones
    
    List<ReporteEstatusInsumo> consultarEstatusInsumos(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte,
            @Param("startingAt") int startingAt, @Param("maxPerPage") int maxPerPage);    
    
    List<ReporteConcentracionArticulos> consultarEstatusInsumosConce(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte,
            @Param("startingAt") int startingAt, @Param("maxPerPage") int maxPerPage);
    
    List<ReporteSurtidoServicio> consultarSurtidoServicio(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte,
            @Param("startingAt") int startingAt, @Param("maxPerPage") int maxPerPage);  
    
    Long obtenerTotalEstatusInsumos(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte);

    Long obtenerTotalSurtidoServicio(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte);
    
    Long obtenerTotalEstatusInsumosConce(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte);
 
    List<ReporteLibroControlados> obtenerReporteMedicamentosRefri5000(
            @Param("paramLibMedControlados") ParamLibMedControlados paramLibMedControlados,
            @Param("startingAt") int startingAt, @Param("maxPerPage") int maxPerPage);
    
    Long obtenerTotalReporteMedicamentosRefri5000(@Param("paramLibMedControlados") ParamLibMedControlados paramLibMedControlados);
    
    List<ReporteLibroControlados> obtenerReporteMedicamentosControlDiario(
            @Param("paramLibMedControlados") ParamLibMedControlados paramLibMedControlados,
            @Param("startingAt") int startingAt, @Param("maxPerPage") int maxPerPage);
    
    Long obtenerTotalReporteMedicamentosControlDiario(@Param("paramLibMedControlados") ParamLibMedControlados paramLibMedControlados);
    
    List<DataResultConsolidado> obtenerSurtPrescripcionConsolidado(
            @Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte,
            @Param("startingAt") int startingAt, @Param("maxPerPage") int maxPerPage);
    
    Long obtenerTotalSurtPrescripcionConsolidado(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte);
    
    Long obtenerTotalConsumos(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte);

    List<Consumo> obtenerConsumos(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte);
    
    
    Long obtenerTotalMezclas(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte);
    List<Mezcla> obtenerMezclas(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte);
    Long obtenerTotalMezclasEstatusSol(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte);
    List<Mezcla> obtenerMezclasByEstatusSol(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte);
    
}
