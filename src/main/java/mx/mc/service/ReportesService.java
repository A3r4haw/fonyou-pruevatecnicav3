package mx.mc.service;

import java.util.List;
import mx.mc.model.CamaExtended;
import mx.mc.model.DevolucionMezclaExtended;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.Estructura;
import mx.mc.model.Etiqueta;
import mx.mc.model.EtiquetaInsumo;
import mx.mc.model.FichaPrevencionExtended;
import mx.mc.model.FolioAlternativoFolioMus;
import mx.mc.model.Medicamento_Extended;
import mx.mc.model.NutricionParenteralExtended;
import mx.mc.model.Paciente_Extended;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.ParamLibMedControlados;
import mx.mc.model.Prescripcion;
import mx.mc.model.Prescripcion_Extended;
import mx.mc.model.Reabasto;
import mx.mc.model.ReabastoExtended;
import mx.mc.model.Reaccion;
import mx.mc.model.RepSurtimientoPresc;
import mx.mc.model.Solucion;
import mx.mc.model.SolucionExtended;
import mx.mc.model.Surtimiento_Extend;
import mx.mc.model.NotaDispenColect_Extended;
import mx.mc.model.TemplateEtiqueta;
import mx.mc.model.Turno;
import mx.mc.model.Usuario;
import org.primefaces.model.StreamedContent;
/**
 *
 * @author hramirez
 */
public interface ReportesService {

    public StreamedContent generaReportePorRequerimientoNo(
            Integer requerimientoNo, String nombreAlmacen
            , String requerimientoFecha, String requerimientoFolio
            , String isGabinete
    ) throws Exception;
    
    
    public StreamedContent generaReporteDevolucion(
           Integer requerimientoNo , String nombreAlmacen , String requerimientoFecha
            , String requerimientoFolio , String isGabinete
    ) throws Exception;
    
    public byte[] reporteSolicitudReabasto(Reabasto reabasto, int idTipoAlmacen, String nombreUsuario, String claveUsuario) throws Exception;
    
    public byte[] reporteSolicitudReabasto(Reabasto reabasto, List<FolioAlternativoFolioMus> listFolioAlternativos, int idTipoAlmacen, String nombreUsuario, String claveUsuario) throws Exception;
    
    public byte[] reporteSolicitudColectivo(Reabasto reabasto, List<Reabasto> colectivos, int idTipoAlmacen, String nombreUsuario, String claveUsuario) throws Exception;
    
    public byte[] imprimePrescripcion(Paciente_Extended pa,CamaExtended ce,Usuario u,Paciente_Extended pe,Prescripcion_Extended p) throws Exception;
    
    public boolean imprimeSurtimiento(Surtimiento_Extend s, String pathTmp, String url) throws Exception;
    
    public byte[] imprimirOrdenRecibir(ReabastoExtended reabastoSelect, EntidadHospitalaria entidad) throws Exception;
    
    public byte[] imprimirOrdenIngresar(ReabastoExtended p, EntidadHospitalaria entidad) throws Exception;
    
    public byte[] imprimirOrdenSurtir(ReabastoExtended reabastoSelect, EntidadHospitalaria entidad, String titulo) throws Exception;
    
    public byte[] imprimePulcera(Paciente_Extended p) throws Exception;
    
    public boolean imprimirEtiqueta(Etiqueta etiqueta) throws Exception;
    
    public boolean imprimirEtiquetaItem(EtiquetaInsumo etiqueta) throws Exception;
    
    public boolean imprimirEtiquetaCM(TemplateEtiqueta te, Integer cant, String ip) throws Exception;
    
    //Existencias
    public byte[] imprimeReporteExistencias(ParamBusquedaReporte p, EntidadHospitalaria entidad, boolean hospChiconc) throws Exception;
    
    public byte[] imprimeReporteExistenciasMostarndoColumnas(ParamBusquedaReporte p, EntidadHospitalaria entidad, boolean mostrarOrigenInsumos,
            boolean mostrarClaveProveedor, boolean mostrarUnidosis, boolean mostrarCosteLote) throws Exception;       
    
    public boolean imprimeExcelExistencias(ParamBusquedaReporte p,EntidadHospitalaria entidad)throws Exception;
    
    public boolean imprimeExcelExistenciasMostarndoColumnas(ParamBusquedaReporte p,EntidadHospitalaria entidad, boolean mostrarOrigenInsumos,
            boolean mostrarClaveProveedor, boolean mostrarUnidosis, boolean mostrarCosteLote)throws Exception;
    
    //Movimients Generales
    public byte[] imprimeReporteMovGenerales(ParamBusquedaReporte p, EntidadHospitalaria entidad, boolean mostrarOrigenInsumos) throws Exception;
    
    public byte[] imprimeReporteLibroControlados(ParamLibMedControlados p, EntidadHospitalaria entidad) throws Exception;
    
    public boolean  imprimeExcelGeneral(ParamBusquedaReporte p,EntidadHospitalaria entidad)throws Exception;
    
    public boolean  imprimeExcelGenerales(ParamBusquedaReporte p,EntidadHospitalaria entidad, boolean mostrarOrigenInsumos)throws Exception;
    
    //Vales
    public byte[] imprimeReporteEmisionVales(ParamBusquedaReporte p, EntidadHospitalaria entidad) throws Exception;
    
    public boolean  imprimeExcelVales(ParamBusquedaReporte p, EntidadHospitalaria entidad)throws Exception;
    
    //Recetas
    public byte[] imprimeReporteRecetas(ParamBusquedaReporte p, EntidadHospitalaria entidad) throws Exception;
    
    public boolean  imprimeExcelRecetas(ParamBusquedaReporte p,EntidadHospitalaria entidad)throws Exception;
    
    //Cancelaciones
    public byte[] imprimeReporteCancelaciones(ParamBusquedaReporte p, EntidadHospitalaria entidad) throws Exception;
    
    public boolean imprimeExcelCancelaciones(ParamBusquedaReporte p)throws Exception;
    
    //Almacenes
    public byte[] imprimeReporteAlmacenes(ParamBusquedaReporte p, EntidadHospitalaria entidad) throws Exception;   
    
    public boolean generaExcelAlmacen(ParamBusquedaReporte p, EntidadHospitalaria entidad)throws Exception;   
        
    public boolean imprimeSurtimientoPrescExt(
            Surtimiento_Extend surtimientoExtendedSelected,
            String nombreUsuario, String pathTmp, String url) throws Exception;
    
    public byte[] imprimeSurtimientoPrescInt(
            RepSurtimientoPresc repSurtimientoPresc,
            String estatusSurtimiento,
            Integer cantIsumos) throws Exception;
    
     public boolean imprimeSurtPresManualHosp(
            RepSurtimientoPresc repSurtimientoPresc,
            String pathTmp, 
            String url,
            String estatusSurtimiento,
            Integer cantIsumos) throws Exception;
    
    public byte[] imprimeSurtimientoPrescManual(
            Surtimiento_Extend surtimientoExtendedSelected,
            String nombreUsuario) throws Exception;
    
    public byte[] imprimeSurtimientoPrescManualChiconcuac(
            Prescripcion prescripcion,
            EntidadHospitalaria entidadHospitalaria,
            Surtimiento_Extend surtimientoExtendedSelected,
            String nombreUsuario) throws Exception;
    
    public byte[] imprimeSurtimientoPrescColectivaChiconcuac(Prescripcion prescripcion,
            EntidadHospitalaria entidadHospitalaria,
            Surtimiento_Extend surtimientoExtendedSelected,
            String nombreUsuario) throws Exception; 
    
    public byte[] imprimeReporteColectivaChiconcuac(Prescripcion prescripcion,
            EntidadHospitalaria entidadHospitalaria,
            Surtimiento_Extend surtimientoExtendedSelected,
            String nombreUsuario) throws Exception; 
    
    public boolean imprimeSurtimientoVales(Surtimiento_Extend surtimientoExtendedSelected,
            String nombreUsuario, String pathTmp, String url) throws Exception;
    
    
    //reporteAcumulados Chiconcuac
    
    public byte[] imprimeReporteAcumulados(ParamBusquedaReporte p, EntidadHospitalaria entidad) throws Exception;
        
    //Imprime Ministraciones    
    public byte[] imprimeReporteMinistracion(ParamBusquedaReporte p, EntidadHospitalaria entidad,List<String> estructuraList) throws Exception;
    
    public byte[] imprimeReporteMaterialesCuracion(ParamBusquedaReporte params, EntidadHospitalaria entidad) throws Exception;
    
    public boolean imprimeExcelMaterialesCuracion(ParamBusquedaReporte params, EntidadHospitalaria entidad) throws Exception;
    
    public boolean  imprimeExcelMinistracion(ParamBusquedaReporte p,EntidadHospitalaria entidad,List<String> estructuraList)throws Exception;
    
    public byte[] imprimeReporteTerapeutico(ParamBusquedaReporte p, EntidadHospitalaria entidad) throws Exception;
   
    //Imprime estatusInsumos    
     public byte[] imprimeReporteEstatusInsumos(ParamBusquedaReporte p, EntidadHospitalaria entidad,Estructura estructura) throws Exception;
     
     public byte[] imprimeReporteEstatusInsumosConce(ParamBusquedaReporte p, EntidadHospitalaria entidadHospitalaria,Estructura est) throws Exception;
     
     public byte[] imprimeSurtidosServicio(ParamBusquedaReporte p) throws Exception;
     
     public boolean generaExcelSurtidosServicio(ParamBusquedaReporte p) throws Exception;
     
     public boolean generaExcelBajaArticulos(ParamBusquedaReporte p, EntidadHospitalaria entidad, Estructura estructura) throws Exception;
     
     public boolean  generaExcelEstatusInsumosConce(ParamBusquedaReporte p,EntidadHospitalaria entidadHospitalaria,Estructura est)throws Exception;
     
     public boolean  generaExcelEstatusInsumos(ParamBusquedaReporte p,EntidadHospitalaria entidad,Estructura estructura)throws Exception;
     
     public byte[] imprimeReporteControlCaducidad(ParamBusquedaReporte p, EntidadHospitalaria entidad) throws Exception;
    
    public boolean generaExcelControlCaducidad(ParamBusquedaReporte p, EntidadHospitalaria entidad)throws Exception;
    
     public byte[] imprimeReporteRefri5000(ParamLibMedControlados p) throws Exception;
     
     public byte[] imprimeReporteCamas(ParamBusquedaReporte paramReporte, List<String>listaEstructuras,EntidadHospitalaria entidad) throws Exception;
         
    public byte[] imprimeReporteEnvioNeumatico(ParamBusquedaReporte p, EntidadHospitalaria entidad) throws Exception;
    
    public byte[] imprimeReporteBajasInsumos(ParamBusquedaReporte p, EntidadHospitalaria entidad, Estructura estructura) throws Exception;
    
    public byte[] imprimirOrdenSurtirProveedorFarmacia(ReabastoExtended reabastoSelect) throws Exception;
    
    public byte[] imprimeReporteInsumoNoMiistrado(ParamBusquedaReporte p, EntidadHospitalaria entidad) throws Exception;
    
    public boolean generaExcelInsumoNoMinistrado(ParamBusquedaReporte p, String pathTmp,String url,EntidadHospitalaria entidad)throws Exception;

    public byte[] imprimeReporteControlDiario(ParamLibMedControlados p) throws Exception;
    
    public byte[] SurtPrescripcionConsolidado(ParamBusquedaReporte p, EntidadHospitalaria entidad, String datosUsuario,String matrPers) throws Exception;
    
    public boolean generaExcelPrescConsolidado(ParamBusquedaReporte p, EntidadHospitalaria entidad,String datosUsuario,String matrPers)throws Exception;
    
    public byte[] imprimeReporteExist_Consolidadas(Medicamento_Extended p, EntidadHospitalaria entidad,String nombreUsuario) throws Exception;
    
    public boolean imprimeExcel_ReporteExist_Consolidadas(Medicamento_Extended p, String pathTmp,String url,EntidadHospitalaria entidad)throws Exception;
    
    public byte[] reporteTranferencia(Reabasto reabasto,EntidadHospitalaria entidad, String nombreUsuario) throws Exception;

    public boolean generaReporteReaccion(Reaccion reaccion,Integer opc, String pathTmp, String url) throws Exception;
    
    public byte[] reporteValidacionSoluciones(
            Surtimiento_Extend surtimiento,Solucion solucion, EntidadHospitalaria entidad, String nombreUsuario
            , String alergias, String diagnosticos, String envase, Prescripcion p) throws Exception;
    
    public byte[] imprimirReporteHistoricoSoluciones(String idPrescripcion, String idSurtimiento, List<String> listaDiag,
                    Paciente_Extended paciente, SolucionExtended solucion, EntidadHospitalaria entidad, String folioPrescripcion,
                    String folioSurtimiento, List<String> listaIdMedicamentos, List<String> listaClaveMedicamentos) throws Exception;
 
    public boolean imprimirExcelReporteHistoricoSoluciones(String idPrescripcion, String idSurtimiento, List<String> listaDiag,
                    Paciente_Extended paciente, SolucionExtended solucion, EntidadHospitalaria entidad, String folioPrescripcion,
                    String folioSurtimiento, List<String> listaIdMedicamentos, List<String> listaClaveMedicamentos) throws Exception;
    
    public byte[] reporteNutricionPareteral(Surtimiento_Extend surtimiento,Solucion solucion, EntidadHospitalaria entidad, 
                    String nombreUsuario,String alergias,String diagnosticos,String envase, NutricionParenteralExtended nutricionParenteral) throws Exception;
    
    
    public byte[] reporteFichaPrevencion(FichaPrevencionExtended fpe, EntidadHospitalaria entidad) throws Exception;
    
    public byte[] reporteRetiroMezcla(DevolucionMezclaExtended mezcla, EntidadHospitalaria entidad,String usuarioCreate) throws Exception;
    
    public byte[] reporteDevolucionMezcla(DevolucionMezclaExtended mezcla, EntidadHospitalaria entidad,String usuarioCreate) throws Exception;
    
    public byte[] imprimeReporteAccionesUsuario(ParamBusquedaReporte paramReporte, EntidadHospitalaria entidad) throws Exception;
    
    public boolean imprimeExcelBitacoraAcciones(ParamBusquedaReporte paramBusquedaReporte, EntidadHospitalaria entidad);

    public byte[] imprimeListaDistribucion(EntidadHospitalaria entidad, NotaDispenColect_Extended solucionDistribuye) throws Exception;
    
    public byte[] generConsumosPdf(ParamBusquedaReporte p, EntidadHospitalaria entidad) throws Exception;
        
    public boolean generaConsumosExcel(ParamBusquedaReporte p, EntidadHospitalaria entidad) throws Exception;
    
    public byte[] imprimePrescripcionMezcla(
            Surtimiento_Extend surtimiento,Solucion solucion, EntidadHospitalaria entidad, String nombreUsuario
            , String alergias, String diagnosticos, String envase, Prescripcion p) throws Exception;
    
    public byte[] imprimePrescripcionMezclaNPT(
            EntidadHospitalaria entidad, NutricionParenteralExtended nutricionParenteral
            , Surtimiento_Extend surtimiento, Solucion solucion, Prescripcion p
            , String alergias, String diagnosticos, Usuario usuario
            ) throws Exception;
    
    public byte[] generaMezclasPdf(ParamBusquedaReporte p, EntidadHospitalaria entidad) throws Exception;
        
    public boolean generaMezclasExcel(ParamBusquedaReporte p, EntidadHospitalaria entidad) throws Exception;


    public byte[] imprimeReporteEntregaExistConsolidadas(Medicamento_Extended p, EntidadHospitalaria entidad, String nombreUsuario, String nombreEstructura , String idEstructura , String nombreTurno, Integer idTurno) throws Exception;
    
    public boolean imprimeExcel_ReporteEntregaExist_Consolidadas(Medicamento_Extended p, String pathTmp,String url,EntidadHospitalaria entidad, String nombreUsuario, String nombreEstructura , String idEstructura , String nombreTurno, Integer idTurno) throws Exception;
    
    
}
