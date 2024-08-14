package mx.mc.service; 

import java.util.List;
import mx.mc.model.Consumo;

import mx.mc.model.DataResultReport;
import mx.mc.model.DataResultVales;
import mx.mc.model.DataResultAlmacenes;
import mx.mc.model.DataResultConsolidado;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.Mezcla;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.ParamLibMedControlados;
import mx.mc.model.ReportInventarioExistencias;
import mx.mc.model.ReporteConcentracionArticulos;
import mx.mc.model.ReporteEstatusInsumo;
import mx.mc.model.ReporteSurtidoServicio;
import mx.mc.model.ReporteLibroControlados;
import mx.mc.model.Solucion;
import mx.mc.model.Solucion_Extended;
import mx.mc.model.SurtimientoMinistrado_Extend;
import org.primefaces.model.SortOrder;

/**
 * 
 * @author gcruz
 *
 */
public interface ReporteMovimientosService {
	
	public List<DataResultReport> consultarMovimiento(ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage) throws Exception;
	
	public Long obtenerTotalRegistros(ParamBusquedaReporte paramBusquedaReporte) throws Exception;
	
	public List<ReportInventarioExistencias> consultarInventarioExistencias(ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage,String sortField,SortOrder sortOrder) throws Exception;
        
        public List<ReportInventarioExistencias> consultarInventarioExistenciasGlobal(ParamBusquedaReporte paramBusquedaReporte) throws Exception;
	
	public Long getTotalRegistrosInventarioExistencias(ParamBusquedaReporte paramBusquedaReporte) throws Exception;
	
        //Vales
        public List<DataResultVales> consultarEmisionVales(ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage) throws Exception;
        
        public Long obtenerTotalRegistrosVales(ParamBusquedaReporte paramBusquedaReporte) throws Exception;
        
        //Recetas
        public List<DataResultVales> consultarEmisionRecetas(ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage) throws Exception;
        
        public Long obtenerTotalRegistrosRecetas(ParamBusquedaReporte paramBusquedaReporte) throws Exception;
        
        public List<DataResultVales> consultarRecetasColectivas(ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage) throws  Exception;
        
        public Long obtenerTotalReceColectivas(ParamBusquedaReporte paramBusquedaReporte) throws  Exception;
        
        //Cancelaciones
        public List<DataResultVales> consultarCancelaciones(ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage) throws Exception;
	
        public Long obtenerTotalCancelaciones(ParamBusquedaReporte paramBusquedaReporte) throws Exception;
        
        //Alamacenes 
        public List<DataResultAlmacenes> consultarAlmacenes(ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage) throws Exception;
	
        public Long obtenerTotalAlmacenes(ParamBusquedaReporte paramBusquedaReporte) throws Exception;
            
        //Medicamentos Inventario
        public List<ReportInventarioExistencias> listaInsumosInventario(String idEstructura,String cadena) throws Exception;
        
        
        //                      Acumulados chiconcuac
        public List<DataResultReport> consultarAcumulados(ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage) throws Exception;
        
        public Long obtenerTotalAcumulados(ParamBusquedaReporte paramBusquedaReporte) throws Exception;
        
        public List<DataResultReport>  consultarAcumuladosMedico(ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage) throws Exception;
        
        public Long obtenerTotalAcumuladosMedico(ParamBusquedaReporte paramBusquedaReporte) throws Exception;
        
        public List<DataResultReport>  consultarAcumuladosPaciente(ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage) throws Exception;
        
        public Long obtenerTotalAcumuladosPaciente(ParamBusquedaReporte paramBusquedaReporte) throws Exception;
        
        public List<DataResultReport>  consultarAcumuladosColectivo(ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage) throws Exception;
        
        public Long obtenerTotalAcumuladosColectivo(ParamBusquedaReporte paramBusquedaReporte) throws Exception;
        
        //      ReporteMinistrados
        
        public List<SurtimientoMinistrado_Extend> consultarMinistraciones(ParamBusquedaReporte paramBusquedaReporte,List<String> estructuraList, int startingAt, int maxPerPage) throws Exception;
	
	public Long obtenerTotalRegistrosMinistraciones(ParamBusquedaReporte paramBusquedaReporte,List<String> estructuraList) throws Exception;
        
        //Reporte Estatus Insumos
        public List<ReporteEstatusInsumo> consultarEstatusInsumos(ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage) throws Exception;     
        
        public List<ReporteConcentracionArticulos> consultarEstatusInsumosConce(ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage) throws Exception; 
	
        public List<ReporteSurtidoServicio> consultarSurtidoServicio(ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage) throws Exception; 
        
        public Long consultarSurtidoServicioTotal(ParamBusquedaReporte paramBusquedaReporte) throws Exception;
        
	public Long obtenerTotalEstatusInsumos(ParamBusquedaReporte paramBusquedaReporte) throws Exception;
        
        public Long obtenerTotalEstatusInsumosConce(ParamBusquedaReporte paramBusquedaReporte) throws Exception;
        
        public List<ReporteLibroControlados> obtenerReporteMedicamentosRefri5000(
            ParamLibMedControlados paramLibMedControlados, int startingAt, int maxPerPage) throws Exception;
        
        public Long obtenerTotalReporteMedicamentosRefri5000(ParamLibMedControlados paramLibMedControlados) throws Exception;
        
        public List<ReporteLibroControlados> obtenerReporteMedicamentosControlDiario(
            ParamLibMedControlados paramLibMedControlados, int startingAt, int maxPerPage) throws Exception;
        
        public Long obtenerTotalReporteMedicamentosControlDiario(ParamLibMedControlados paramLibMedControlados) throws Exception;
        
        public List<DataResultConsolidado> obtenerSurtPrescripcionConsolidado(
            ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage) throws Exception;
        
        public Long obtenerTotalSurtPrescripcionConsolidado(ParamBusquedaReporte paramBusquedaReporte) throws Exception;
        
        public Long obtenerTotalConsumos(ParamBusquedaReporte paramBusquedaReporte) throws Exception;
        public List<Consumo> obtenerConsumos(ParamBusquedaReporte paramBusquedaReporte) throws Exception;
        
        
        public Long obtenerTotalMezclas(ParamBusquedaReporte paramBusquedaReporte) throws Exception;
        public List<Mezcla> obtenerMezclas(ParamBusquedaReporte paramBusquedaReporte) throws Exception;
        public Long obtenerTotalMezclasEstatusSol(ParamBusquedaReporte paramBusquedaReporte) throws Exception;
        public List<Mezcla> obtenerMezclasByEstatusSol(ParamBusquedaReporte paramBusquedaReporte) throws Exception;
        
}
