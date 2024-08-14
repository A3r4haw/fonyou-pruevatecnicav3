package mx.mc.service; 

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.mc.mapper.ReporteMovimientosMapper;
import mx.mc.model.Consumo;
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
import org.primefaces.model.SortOrder;

/**
 *
 * @author gcruz
 *
 */
@Service
public class ReporteMovimientosServiceImpl implements ReporteMovimientosService {

    @Autowired
    private ReporteMovimientosMapper reporteMovimientosMapper;

    public ReporteMovimientosServiceImpl() {
        //No code needed in constructor
    }

    @Override
    public List<DataResultReport> consultarMovimiento(ParamBusquedaReporte paramBusquedaReporte, int startingAt,
            int maxPerPage) throws Exception {
        try {
            return reporteMovimientosMapper.consultarMovimiento(paramBusquedaReporte, startingAt, maxPerPage);
        } catch (Exception ex) {
            throw new Exception("Error al obtener consulta de reporte movimientos generales" + ex.getMessage());
        }
    }

    @Override
    public Long obtenerTotalRegistros(ParamBusquedaReporte paramBusquedaReporte) throws Exception {
        try {
            return reporteMovimientosMapper.obtenerTotalRegistros(paramBusquedaReporte);
        } catch (Exception ex) {
            throw new Exception("Error al obtener total de registros de reporte movimientos generales" + ex.getMessage());
        }
    }

    @Override
    public List<ReportInventarioExistencias> consultarInventarioExistencias(ParamBusquedaReporte paramBusquedaReporte,
            int startingAt, int maxPerPage,String sortField, SortOrder sortOrder ) throws Exception {
        try {
            String order = sortOrder==SortOrder.ASCENDING ? "asc": sortOrder==SortOrder.DESCENDING ? "desc" : null;            
            return reporteMovimientosMapper.consultarInventarioExistencias(paramBusquedaReporte, startingAt, maxPerPage,sortField,order);
        } catch (Exception ex) {
            throw new Exception("Error al obtener consulta de reporte inventario y existencias" + ex.getMessage());
        }
    }
    
    @Override
    public List<ReportInventarioExistencias> consultarInventarioExistenciasGlobal(ParamBusquedaReporte paramBusquedaReporte) throws Exception {
        try {
            return reporteMovimientosMapper.consultarInventarioExistenciasGlobal(paramBusquedaReporte);
        } catch (Exception ex) {
            throw new Exception("Error al obtener consulta de reporte inventario y existencias" + ex.getMessage());
        }
    }

    @Override
    public Long getTotalRegistrosInventarioExistencias(ParamBusquedaReporte paramBusquedaReporte) throws Exception {
        try {
            return reporteMovimientosMapper.getTotalRegistrosInventarioExistencias(paramBusquedaReporte);
        } catch (Exception ex) {
            throw new Exception("Error al obtener total de registros de reporte inventario y existencias" + ex.getMessage());
        }
    }

    /*Servicio de ConsultarEmisoinVales*/
    @Override
    public List<DataResultVales> consultarEmisionVales(ParamBusquedaReporte paramBusquedaReporte, int startingAt,
            int maxPerPage) throws Exception {
        try {
            return reporteMovimientosMapper.consultarEmisionVales(paramBusquedaReporte, startingAt, maxPerPage);
        } catch (Exception ex) {
            throw new Exception("Error al obtener consulta de Emision de Vales " + ex.getMessage());
        }
    }

    @Override
    public Long obtenerTotalRegistrosVales(ParamBusquedaReporte paramBusquedaReporte) throws Exception {
        try {
            return reporteMovimientosMapper.obtenerTotalRegistrosVales(paramBusquedaReporte);
        } catch (Exception ex) {
            throw new Exception("Error al obtener total de registros de reporte de vales" + ex.getMessage());
        }
    }

    /*Servicio de ConsultarEmisionRecetas*/
    @Override
    public List<DataResultVales> consultarEmisionRecetas(ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage) throws Exception {
        try {
            return reporteMovimientosMapper.consultarEmisionRecetas(paramBusquedaReporte, startingAt, maxPerPage);
        } catch (Exception ex) {
            throw new Exception("Error al obtener consulta de Reporte de Recetas " + ex.getMessage());
        }
    }

    @Override
    public Long obtenerTotalRegistrosRecetas(ParamBusquedaReporte paramBusquedaReporte) throws Exception {
        try {
            return reporteMovimientosMapper.obtenerTotalRegistrosRecetas(paramBusquedaReporte);
        } catch (Exception ex) {
            throw new Exception("Error al obtener el total de registros de Reporte Recetas" + ex.getMessage());
        }
    }

    @Override
    public List<DataResultVales> consultarRecetasColectivas(ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage) throws Exception {
        try {
            return reporteMovimientosMapper.consultarRecetasColectivas(paramBusquedaReporte, startingAt, maxPerPage);
        } catch (Exception e) {
            throw new Exception("Error al obtener consulta de Reporte de Recetas Colectivas " + e.getMessage());
        }
    }

    @Override
    public Long obtenerTotalReceColectivas(ParamBusquedaReporte paramBusquedaReporte) throws Exception {
        try {
            return reporteMovimientosMapper.obtenerTotalReceColectivas(paramBusquedaReporte);
        } catch (Exception e) {
            throw  new Exception("Error al obtener el total de registros de Colectivas " + e.getMessage());
        }
    }
    
    //Servicio de Cancelaciones
    @Override
    public List<DataResultVales> consultarCancelaciones(ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage) throws Exception {
        try {
            return reporteMovimientosMapper.consultarCancelaciones(paramBusquedaReporte, startingAt, maxPerPage);
        } catch (Exception ex) {
            throw new Exception("Error al Obtener el Reporte de Cancelaciones " + ex.getMessage());
        }
    }

    @Override
    public Long obtenerTotalCancelaciones(ParamBusquedaReporte paramBusquedaReporte) throws Exception {
        try {
            return reporteMovimientosMapper.obtenerTotalCancelaciones(paramBusquedaReporte);
        } catch (Exception ex) {
            throw new Exception("Error al Obtener el Total de Registros de Cancelaciones " + ex.getMessage());
        }
    }

    @Override
    public List<DataResultAlmacenes> consultarAlmacenes(ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage) throws Exception {
        try {
            return reporteMovimientosMapper.rptConsultarAlmacenes(paramBusquedaReporte, startingAt, maxPerPage);
        } catch (Exception ex) {
            throw new Exception("Error  al obtener el Reporte de Almacenes " + ex.getMessage());
        }
    }

    @Override
    public Long obtenerTotalAlmacenes(ParamBusquedaReporte paramBusquedaReporte) throws Exception {
        try {
            return reporteMovimientosMapper.rptObtenerTotalAlmacenes(paramBusquedaReporte);            
        } catch (Exception ex) {
            throw new Exception("Error al obtener el total de Datos de Reporte de Almacenes " + ex.getMessage());
        }
    }

    @Override
    public List<ReportInventarioExistencias> listaInsumosInventario(String idEstructura, String cadena) throws Exception {
        try {
            return reporteMovimientosMapper.listInsumosInventarioAlmacen(idEstructura,cadena);            
        } catch (Exception ex) {
            throw new Exception("Error al obtener lista de Insumos por Almacen " + ex.getMessage());
        }
    }    

    @Override
    public List<DataResultReport> consultarAcumulados(ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage) throws Exception {
        try {
            return reporteMovimientosMapper.consultarAcumulados(paramBusquedaReporte, startingAt, maxPerPage);
        } catch (Exception e) {
            throw new Exception("Error al obtener los Acumulados por Clave" + e.getMessage());
        }
    }

    @Override
    public Long obtenerTotalAcumulados(ParamBusquedaReporte paramBusquedaReporte) throws Exception {
        try {
            return reporteMovimientosMapper.obtenerTotalAcumulados(paramBusquedaReporte);
        } catch (Exception e) {
            throw  new Exception("Error al obtene el total de Acumulados por Clave" + e.getMessage());
        }
    }

    @Override
    public List<DataResultReport> consultarAcumuladosMedico(ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage) throws Exception {
        try {
            return reporteMovimientosMapper.consultarAcumuladosMedico(paramBusquedaReporte, startingAt, maxPerPage);
        } catch (Exception e) {
            throw new Exception("Error al obtener los Acumulados por Médico" + e.getMessage());
        }
    }

    @Override
    public Long obtenerTotalAcumuladosMedico(ParamBusquedaReporte paramBusquedaReporte) throws Exception {
        try {
            return reporteMovimientosMapper.obtenerTotalAcumuladosMedico(paramBusquedaReporte);
        } catch (Exception e) {
            throw  new Exception("Error al obtene el total de Acumulados por Médico" + e.getMessage());
        }
    }

    @Override
    public List<DataResultReport> consultarAcumuladosPaciente(ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage) throws Exception {
        try {
            return reporteMovimientosMapper.consultarAcumuladosPaciente(paramBusquedaReporte, startingAt, maxPerPage);
        } catch (Exception e) {
            throw new Exception("Error al obtener los Acumulados por Paciente" + e.getMessage());
        }
    }

    @Override
    public Long obtenerTotalAcumuladosPaciente(ParamBusquedaReporte paramBusquedaReporte) throws Exception {
        try {
            return reporteMovimientosMapper.obtenerTotalAcumuladosPaciente(paramBusquedaReporte);
        } catch (Exception e) {
            throw  new Exception("Error al obtene el total de Acumulados por Paciente" + e.getMessage());
        }
    }

    @Override
    public List<DataResultReport> consultarAcumuladosColectivo(ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage) throws Exception {
        try {
            return reporteMovimientosMapper.consultarAcumuladosColectivo(paramBusquedaReporte, startingAt, maxPerPage);
        } catch (Exception e) {
            throw new Exception("Error al obtener los Acumulados por Colectivos" + e.getMessage());
        }
    }

    @Override
    public Long obtenerTotalAcumuladosColectivo(ParamBusquedaReporte paramBusquedaReporte) throws Exception {
        try {
            return reporteMovimientosMapper.obtenerTotalAcumuladosColectivo(paramBusquedaReporte);
        } catch (Exception e) {
            throw  new Exception("Error al obtene el total de Acumulados por Colectivos" + e.getMessage());
        }
    }

    @Override
    public List<SurtimientoMinistrado_Extend> consultarMinistraciones(ParamBusquedaReporte paramBusquedaReporte,List<String> estructuraList, int startingAt, int maxPerPage) throws Exception {
        try {
            return reporteMovimientosMapper.consultarMinistraciones(paramBusquedaReporte, estructuraList, startingAt, maxPerPage);
        } catch (Exception e) {
            throw new Exception("Error al obtener las Ministraciones" + e.getMessage());
        }
    }

    @Override
    public Long obtenerTotalRegistrosMinistraciones(ParamBusquedaReporte paramBusquedaReporte,List<String> estructuraList) throws Exception {
        try {
            return reporteMovimientosMapper.obtenerTotalRegistrosMinistraciones(paramBusquedaReporte, estructuraList);
        } catch (Exception e) {
            throw new Exception("Error al obtener el Total de Ministraciones" + e.getMessage());
        }
    }

    @Override
    public List<ReporteEstatusInsumo> consultarEstatusInsumos(ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage) throws Exception {
        try {
            return reporteMovimientosMapper.consultarEstatusInsumos(paramBusquedaReporte, startingAt, maxPerPage);
        } catch (Exception e) {
            throw new Exception("Error al consultarEstatusInsumos "+ e.getMessage());
        }
    }
    
    @Override
    public List<ReporteConcentracionArticulos> consultarEstatusInsumosConce(ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage) throws Exception {
        try {
            return reporteMovimientosMapper.consultarEstatusInsumosConce(paramBusquedaReporte, startingAt, maxPerPage);
        } catch (Exception e) {
            throw new Exception("Error al consultarEstatusInsumosConce "+ e.getMessage());
        }
    }
    
    @Override
    public List<ReporteSurtidoServicio> consultarSurtidoServicio(ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage) throws Exception {
        try {
            return reporteMovimientosMapper.consultarSurtidoServicio(paramBusquedaReporte, startingAt, maxPerPage);
        } catch (Exception e) {
            throw new Exception("Error al consultarSurtidoServicio "+ e.getMessage());
        }
    }
    
    @Override
    public Long obtenerTotalEstatusInsumos(ParamBusquedaReporte paramBusquedaReporte) throws Exception {
        try {
            return reporteMovimientosMapper.obtenerTotalEstatusInsumos(paramBusquedaReporte);
        } catch (Exception e) {
            throw new Exception("Error al obtenerTotalEstatusInsumos "+ e.getMessage());
        }
    }
    
    @Override
    public Long consultarSurtidoServicioTotal(ParamBusquedaReporte paramBusquedaReporte) throws Exception {
        try {
            return reporteMovimientosMapper.obtenerTotalSurtidoServicio(paramBusquedaReporte);
        } catch (Exception e) {
            throw new Exception("Error al consultarSurtidoServicioTotal "+ e.getMessage());
        }
    }
    
    @Override
    public Long obtenerTotalEstatusInsumosConce(ParamBusquedaReporte paramBusquedaReporte) throws Exception {
        try {
            return reporteMovimientosMapper.obtenerTotalEstatusInsumosConce(paramBusquedaReporte);
        } catch (Exception e) {
            throw new Exception("Error al obtenerTotalEstatusInsumosConce "+ e.getMessage());
        }
    }

    @Override
    public List<ReporteLibroControlados> obtenerReporteMedicamentosRefri5000(ParamLibMedControlados paramLibMedControlados, int startingAt, int maxPerPage) throws Exception {
        try {
            return reporteMovimientosMapper.obtenerReporteMedicamentosRefri5000(paramLibMedControlados, startingAt, maxPerPage);
        } catch (Exception e) {
            throw new Exception("Error al obtener Medicamentos por Refrigeración/5000 "+ e.getMessage());
        }
    }

    @Override
    public Long obtenerTotalReporteMedicamentosRefri5000(ParamLibMedControlados paramLibMedControlados) throws Exception {
        try {
            return reporteMovimientosMapper.obtenerTotalReporteMedicamentosRefri5000(paramLibMedControlados);
        } catch (Exception e) {
            throw new Exception("Error al obtener el total de Medicamentos por Refrigeración/5000 "+ e.getMessage());
        }
    }

    @Override
    public List<ReporteLibroControlados> obtenerReporteMedicamentosControlDiario(ParamLibMedControlados paramLibMedControlados, int startingAt, int maxPerPage) throws Exception {
        try {
            return reporteMovimientosMapper.obtenerReporteMedicamentosControlDiario(paramLibMedControlados, startingAt, maxPerPage);
        } catch (Exception e) {
            throw new Exception("Error al obtener Medicamentos"+ e.getMessage());
        }
    }

    @Override
    public Long obtenerTotalReporteMedicamentosControlDiario(ParamLibMedControlados paramLibMedControlados) throws Exception {
        try {
            return reporteMovimientosMapper.obtenerTotalReporteMedicamentosControlDiario(paramLibMedControlados);
        } catch (Exception e) {
            throw new Exception("Error al obtener el total de Medicamentos"+ e.getMessage());
        }
    } 

    @Override
    public List<DataResultConsolidado> obtenerSurtPrescripcionConsolidado(ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage) throws Exception {
        try {
            return reporteMovimientosMapper.obtenerSurtPrescripcionConsolidado(paramBusquedaReporte, startingAt, maxPerPage);
        } catch (Exception e) {
            throw new Exception("Error al obtenerSurtPrescripcionConsolidado "+ e.getMessage());
        }
    }

    @Override
    public Long obtenerTotalSurtPrescripcionConsolidado(ParamBusquedaReporte paramBusquedaReporte) throws Exception {
        try {
            return reporteMovimientosMapper.obtenerTotalSurtPrescripcionConsolidado(paramBusquedaReporte);
        } catch (Exception e) {
            throw new Exception("Error al obtenerTotalSurtPrescripcionConsolidado "+ e.getMessage());
        }
    }
    
    @Override
    public Long obtenerTotalConsumos(ParamBusquedaReporte paramBusquedaReporte) throws Exception {
        try {
            return reporteMovimientosMapper.obtenerTotalConsumos(paramBusquedaReporte);
        } catch (Exception e) {
            throw new Exception("Error al obtenerTotalConsumos :: "+ e.getMessage());
        }
    }
    
    @Override
    public List<Consumo> obtenerConsumos(ParamBusquedaReporte paramBusquedaReporte) throws Exception {
        try {
            return reporteMovimientosMapper.obtenerConsumos(paramBusquedaReporte);
        } catch (Exception e) {
            throw new Exception("Error al obtenerConsumos :: "+ e.getMessage());
        }
    }
    
    
    @Override
    public Long obtenerTotalMezclas(ParamBusquedaReporte paramBusquedaReporte) throws Exception {
        try {
            return reporteMovimientosMapper.obtenerTotalMezclas(paramBusquedaReporte);
        } catch (Exception e) {
            throw new Exception("Error al obtenerTotalMezclas :: "+ e.getMessage());
        }
    }
    
    @Override
    public List<Mezcla> obtenerMezclas(ParamBusquedaReporte paramBusquedaReporte) throws Exception {
        try {
            return reporteMovimientosMapper.obtenerMezclas(paramBusquedaReporte);
        } catch (Exception e) {
            throw new Exception("Error al obtenerMezclas :: "+ e.getMessage());
        }
    }
    
     @Override
    public Long obtenerTotalMezclasEstatusSol(ParamBusquedaReporte paramBusquedaReporte) throws Exception {
        try {
            return reporteMovimientosMapper.obtenerTotalMezclasEstatusSol(paramBusquedaReporte);
        } catch (Exception e) {
            throw new Exception("Error al obtenerTotalMezclasSolicitadas :: "+ e.getMessage());
        }
    }
    
    @Override
    public List<Mezcla> obtenerMezclasByEstatusSol(ParamBusquedaReporte paramBusquedaReporte) throws Exception {
        try {
            return reporteMovimientosMapper.obtenerMezclasByEstatusSol(paramBusquedaReporte);
        } catch (Exception e) {
            throw new Exception("Error al obtenerMezclasSolicitadas :: "+ e.getMessage());
        }
    }

}
