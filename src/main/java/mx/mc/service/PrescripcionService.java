package mx.mc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import mx.mc.model.AlmacenInsumoComprometido;
import mx.mc.model.DiagnosticoPaciente;
import mx.mc.model.IntipharmPrescription;
import mx.mc.model.IntipharmPrescriptionMedication;
import mx.mc.model.InventarioExtended;
import mx.mc.model.MovimientoInventario;
import mx.mc.model.Paciente;
import mx.mc.model.ParamLibMedControlados;
import mx.mc.model.Prescripcion;
import mx.mc.model.PrescripcionInsumo;
import mx.mc.model.PrescripcionInsumo_Extended;
import mx.mc.model.Prescripcion_Extended;
import mx.mc.model.ReporteLibroControlados;
import mx.mc.model.Solucion;
import mx.mc.model.SolucionExtended;
import mx.mc.model.Surtimiento;
import mx.mc.model.SurtimientoEnviado;
import mx.mc.model.SurtimientoInsumo;
import mx.mc.model.SurtimientoInsumo_Extend;
import mx.mc.model.Surtimiento_Extend;

/**
 *
 * @author hramirez
 */
public interface PrescripcionService extends GenericCrudService<Prescripcion, String> {

    List<Prescripcion_Extended> obtenerPrescripcionesPorIdPaciente(
            String idPaciente, String idPrescripcion, Date fechaPrescripcion, String tipoConsulta, Integer recurrente, String cadenaBusqueda
    ) throws Exception;
    
    List<Prescripcion_Extended> obtenerRecetasManuales(
            List<String> listaTipoPrescripcion, 
            List<Integer> listaEstatusPrescripcion,
            String cadenaBusqueda
    ) throws Exception;
    
    boolean registrarPrescripcion (Prescripcion p
            , List<DiagnosticoPaciente> diagnoticoList
            , List<PrescripcionInsumo> insumoList
            , List<Surtimiento> surtimientoList
            , List<AlmacenInsumoComprometido> comprometidoLis) throws Exception;
    
    public boolean registrarPrescripcionManual(Prescripcion p, Surtimiento s ,
            List<DiagnosticoPaciente> diagnoticoList, 
            List<PrescripcionInsumo> insumoList, 
            List<SurtimientoInsumo_Extend> surtimientoList ,
            List<InventarioExtended> listaInventario ,
            List<PrescripcionInsumo_Extended> listaPrescripcionInsumoExtended ,
            List<MovimientoInventario> listaMovInvent) throws Exception; 
    
    boolean editarPrescripcion (Prescripcion p
            , List<DiagnosticoPaciente> diagnoticoList
            , List<PrescripcionInsumo> insumoList
            , List<Surtimiento> surtimientoList
            , List<AlmacenInsumoComprometido> comprometidoLis) throws Exception;
    
    public List<PrescripcionInsumo_Extended> obtenerInsumosPorIdPrescripcion (String idPrescripcion, Integer tipoInsumo) throws Exception;
    
    boolean cancelarPrescripcion (String idPrescripcion, String idUsuario, Date fecha, Integer idEstatusPrescripcion, Integer idEstatusGabinete, Integer idEstatusSurtimiento) throws Exception;
    
    public boolean cancelarPrescripcionChiconcuac(Prescripcion prescripcion, 
            String idUsuario, Date fecha, Integer idEstatusPrescripcion, 
            Integer idEstatusGabinete, Integer idEstatusSurtimiento) throws Exception;
    
    public List<ReporteLibroControlados> obtenerReporteMedicamentosControlados(
            ParamLibMedControlados parametrosBusqueda) throws Exception;
    
    public Prescripcion verificarFolioCancelar(String folio) throws Exception;
    
    public ArrayList<IntipharmPrescription> selectCabiDrgOrdList () throws Exception;
    
    public List<IntipharmPrescriptionMedication> selectCabiDrgOrdListMedication(String folio) throws Exception;
    
    public boolean actualizarByfolioSurt(String folio) throws Exception;
    
    public String obtenerFolioPrescBySurt(String folio) throws Exception;
    
    public List<Prescripcion_Extended> obtenerByFolioPrescripcionForList(String folio, Paciente pacienteObj, String filter) throws Exception;
    
    public Prescripcion_Extended obtenerPrescripcionByFolio(String prescripcionFolio) throws Exception;
    
    public PrescripcionInsumo_Extended obtenerPrescripcionInsumoByIdPrescripcion(String idPrescripcion) throws Exception;

    public boolean registrarSurtirOrdenSolucion(boolean existePrescripcion, Prescripcion prescripcion, List<PrescripcionInsumo> insumoList, Surtimiento surtimiento, List<SurtimientoInsumo_Extend> surtimientoInsumoList, List<SurtimientoEnviado> surtimientoEnviadoList, List<InventarioExtended> inventarioList, List<MovimientoInventario> movimientoInventarioList) throws Exception;
    
    public boolean actualizarInsumo(PrescripcionInsumo_Extended item) throws Exception;
    
    Prescripcion_Extended obtenerByFolioPrescripcion(String prescripcionFolio) throws Exception;
 
    public PrescripcionInsumo_Extended obtenerUltimaPrescripcionInsumoByIdPrescripcion(String idPrescripcion) throws Exception;
        
    public List<Prescripcion_Extended> obtenerRecetasSurtidas(String cadenaBusqueda) throws Exception;
    
    public PrescripcionInsumo_Extended obtenerPrimerPrescripcionInsumoByIdPrescripcion(String idPrescripcion) throws Exception;
    
    public boolean registrarOrdenSolucion(boolean existePrescripcion, Prescripcion prescripcion, List<PrescripcionInsumo> insumoList, Surtimiento_Extend surtimiento, List<SurtimientoInsumo_Extend> surtimientoInsumoList, List<DiagnosticoPaciente> listaDiagnosticoPaciente, boolean autorizar) throws Exception;
    
    public boolean modificarOrdenSolucion(
            Prescripcion prescripcion ,
             List<PrescripcionInsumo> piListInsertar ,
            List<PrescripcionInsumo> piListActualizar ,
            List<PrescripcionInsumo> piListEliminar ,
            Surtimiento_Extend surtimiento ,
             List<SurtimientoInsumo> siListInsertar ,
            List<SurtimientoInsumo> siListActualizar ,
            List<SurtimientoInsumo> siListEliminar ,
            List<DiagnosticoPaciente> listaDiagnosticoPaciente ,
            Solucion s) throws Exception;
    
    public boolean autorizarOrdenSolucion(Prescripcion prescripcion, List<PrescripcionInsumo> piListInsertar, List<PrescripcionInsumo> piListActualizar, Surtimiento_Extend surtimiento, List<SurtimientoInsumo> siListInsertar, List<SurtimientoInsumo> siListActualizar, Solucion solucion, boolean agrupaMezclaPrescAutorizar) throws Exception;
    
    public boolean registrarOrdenesSoluciones(List<Prescripcion> listaPrescripciones, List<PrescripcionInsumo> insumoList, List<Surtimiento> listaSurtimientosExt, List<SurtimientoInsumo_Extend> surtimientoInsumoList, List<SolucionExtended> listaSolucionesExt,List<DiagnosticoPaciente> listaDiagnosticoPaciente) throws Exception;
}