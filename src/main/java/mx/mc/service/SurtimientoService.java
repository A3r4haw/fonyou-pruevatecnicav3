package mx.mc.service;

import java.util.Date;
import java.util.List;
import mx.mc.model.DevolucionMinistracionDetalleExtended;
import mx.mc.model.DiagnosticoPaciente;
import mx.mc.model.Estructura;
import mx.mc.model.Inventario;
import mx.mc.model.InventarioExtended;
import mx.mc.model.MovimientoInventario;
import mx.mc.model.Paciente;
import mx.mc.model.PacienteDomicilio;
import mx.mc.model.PacienteServicio;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.Prescripcion;
import mx.mc.model.PrescripcionInsumo;
import mx.mc.model.PrescripcionInsumo_Extended;
import mx.mc.model.Prescripcion_Extended;
import mx.mc.model.Solucion;
import mx.mc.model.Surtimiento;
import mx.mc.model.SurtimientoEnviado;
import mx.mc.model.SurtimientoEnviado_Extend;
import mx.mc.model.SurtimientoInsumo;
import mx.mc.model.SurtimientoInsumo_Extend;
import mx.mc.model.SurtimientoMinistrado;
import mx.mc.model.Surtimiento_Extend;
import mx.mc.model.Usuario;
import mx.mc.model.Visita;
import mx.mc.model.VistaRecepcionMedicamento;
import org.primefaces.model.SortOrder;

/**
 *
 * @author hramirez
 */
public interface SurtimientoService extends GenericCrudService<Surtimiento, String> {

    List<Surtimiento_Extend> obtenerPorFechaEstructuraPacienteCamaPrescripcion(
            Date fechaProgramada, String cadenaBusqueda, List<String> tipoPrescripcionList, List<Integer> listEstatusPaciente, List<Integer> listEstatusPrescripcion, List<Integer> listEstatusSurtimiento, List<Estructura> listServiciosQueSurte) throws Exception;        

    List<Surtimiento_Extend> obtenerPorFechaEstructuraPacienteCamaPrescripcionLazy(
            Date fechaProgramada, ParamBusquedaReporte paramBusquedaReporte,
            int startingAt,
            int maxPerPage, List<String> tipoPrescripcionList, List<Integer> listEstatusPaciente, List<Integer> listEstatusPrescripcion, List<Integer> listEstatusSurtimiento, List<Estructura> listServiciosQueSurte) throws Exception;
    
    List<Surtimiento_Extend> obtenerPorFechaEstructuraPacienteCamaPrescripcionOrderLazy(
            Date fechaProgramada, ParamBusquedaReporte paramBusquedaReporte,
            int startingAt,
            int maxPerPage, List<String> tipoPrescripcionList, List<Integer> listEstatusPaciente, List<Integer> listEstatusPrescripcion,
            List<Integer> listEstatusSurtimiento, List<Estructura> listServiciosQueSurte,String sortField,SortOrder sortOrder) throws Exception;
    
    List<Surtimiento_Extend> obtenerPorFechaEstructuraPacienteCamaPrescripcionSolucionOrderLazy(
            Date fechaProgramada, ParamBusquedaReporte paramBusquedaReporte,
            int startingAt,
            int maxPerPage, List<String> tipoPrescripcionList, List<Integer> listEstatusPaciente, List<Integer> listEstatusPrescripcion,
            List<Integer> listEstatusSurtimiento, List<Estructura> listServiciosQueSurte,String sortField,SortOrder sortOrder) throws Exception;

    Long obtenerTotalPorFechaEstructuraPacienteCamaPrescripcionLazy(
            Date fechaProgramada,
            ParamBusquedaReporte paramBusquedaReporte, List<String> tipoPrescripcionList, List<Integer> listEstatusPaciente, List<Integer> listEstatusPrescripcion, List<Integer> listEstatusSurtimiento, List<Estructura> listServiciosQueSurte) throws Exception;
    
    Long obtenerTotalPorFechaEstructuraPacienteCamaPrescripcionSolucionLazy(
            Date fechaProgramada,
            ParamBusquedaReporte paramBusquedaReporte, List<String> tipoPrescripcionList, List<Integer> listEstatusPaciente, List<Integer> listEstatusPrescripcion, List<Integer> listEstatusSurtimiento, List<Estructura> listServiciosQueSurte) throws Exception;
    
    List<Surtimiento_Extend> obtenerPorFechaEstructuraPacientePrescripcionExt(
            Date fechaProgramada, String cadenaBusqueda, List<String> tipoPrescripcionList,Integer numHorPrevReceta,Integer numHorPostReceta,            
            List<Integer> listEstatusPrescripcion, List<Integer> listEstatusSurtimiento, List<String> listServiciosQueSurte,
            int startingAt,int maxPerPage,String sortField,SortOrder sortOrder) throws Exception;
    
    Long obtenerTotalPorFechaEstructuraPacientePrescripcionExt(
            Date fechaProgramada, String cadenaBusqueda, List<String> tipoPrescripcionList,Integer numHorPrevReceta,Integer numHorPostReceta,            
            List<Integer> listEstatusPrescripcion, List<Integer> listEstatusSurtimiento, List<String> listServiciosQueSurte)throws Exception;

    List<Surtimiento_Extend> obtenerPorIdInsumoIdPrescripcion(String idSurtimiento, String idPrescripcion) throws Exception;

    boolean registrarSurtimiento(
            Surtimiento_Extend surtimientoExtendedSelected, List<SurtimientoInsumo> surtimientoInsumoList, List<SurtimientoEnviado> surtimientoEnviadoList) throws Exception;

    public boolean registrarSurtimientoParcial(Surtimiento surtimiento, List<SurtimientoInsumo_Extend> surtimientoInsumoList,
            Prescripcion prescripcion, List<Inventario> listaInventarios, List<MovimientoInventario> listaMovInv)
            throws Exception;

    boolean surtirPrescripcion(
            Surtimiento surtimiento, List<SurtimientoInsumo> surtimientoInsumoList, List<SurtimientoEnviado> surtimientoEnviadoList, List<Inventario> inventarioList, List<MovimientoInventario> movimientoInventarioList) throws Exception;

    public boolean registrarSurtimientoPrescExt(Surtimiento_Extend surtimientoExtendedSelected, List<SurtimientoInsumo> surtimientoInsumoList, List<SurtimientoEnviado> surtimientoEnviadoList, List<Inventario> listIinventario, List<MovimientoInventario> listaMovInventario)
            throws Exception;
    
    public boolean registrarServSurtimientoPrescExt(Paciente paciente,PacienteDomicilio pacienteDomicilio,Visita visita, 
            PacienteServicio pacienteServicio,Prescripcion prescripcion,List<PrescripcionInsumo> prescripcionInsumoList,
            Surtimiento_Extend surtimientoExtendedSelected, List<SurtimientoInsumo> surtimientoInsumoList, List<SurtimientoEnviado> 
                    surtimientoEnviadoList, List<Inventario> listIinventario, List<MovimientoInventario> listaMovInventario,boolean  existePaciente)
            throws Exception;

    public boolean registrarSurtimientoVales(Surtimiento_Extend surtimientoExtendedSelected, List<SurtimientoInsumo> surtimientoInsumoList, List<SurtimientoEnviado> surtimientoEnviadoList)
            throws Exception;

    List<SurtimientoEnviado> obtenerListaSurtimientoEnviadoPorIdSurtimiento(String idSurtimiento) throws Exception;

    List<SurtimientoEnviado_Extend> obtenerDetalleEnviadoPorIdSurtimientoInsumo(String idSurtimientoInsumo) throws Exception;

    int actualizarListaCantidadRecbida(List<SurtimientoInsumo_Extend> insumos, VistaRecepcionMedicamento vistaMed, String idUsuario) throws Exception;
    
    int actualizarListaCantidadRecbidaOff(List<SurtimientoInsumo_Extend> insumos, VistaRecepcionMedicamento vistaMed, String idUsuario) throws Exception;

    List<SurtimientoEnviado> obtenerDetalleDevolucionPorIdPrescripcion(String idPrescripcion) throws Exception;

    List<SurtimientoEnviado_Extend> obtenerDetalleDevolucionPorIdPrescripcionExtend(String idPrescripcion) throws Exception;

    List<SurtimientoEnviado> insumosDePrescripcion(String folio, String idSurtimientoEnviado) throws Exception;

    List<SurtimientoEnviado_Extend> insumosDePrescripcionExtend(String folio, String idSurtimientoEnviado) throws Exception;

    List<Surtimiento> obtenerSurtimientoConsumoExterna() throws Exception;

    boolean actualizarPorFolio(String folio, Integer procesado) throws Exception;

    boolean actualizarPorFolioVale(String folio, Integer procesado) throws Exception;

    Surtimiento obtenerPorFolio(String folio) throws Exception;

    boolean clonarSurtimiento(String idSurtimiento, String newFolio) throws Exception;

    List<Surtimiento_Extend> obtenerPorFechaEstructuraPacienteCamaPrescripcionCancelacion(
            Date fechaProgramada, String cadenaBusqueda, List<String> tipoPrescripcionList, List<Integer> listEstatusPaciente, List<Integer> listEstatusPrescripcion, List<Integer> listEstatusSurtimiento, List<Estructura> listServiciosQueSurte) throws Exception;

    List<SurtimientoInsumo_Extend> detalleSurtimientoInsumoExtRecepMedi(String idSurtimiento) throws Exception;
     
    public List<SurtimientoEnviado_Extend> obtenerByIdSurtimientoEnviadoDevMedi(String idSurtimiento) throws Exception;
             
    List<DevolucionMinistracionDetalleExtended> obtenerDevolucionDetalleExtPorIdSurtimiento(String idSurtimiento) throws Exception;

    public List<Surtimiento_Extend> obtenerPorFechaEstructuraPacienteCamaPrescripcionCancelacionInterna(
            Date fechaProgramada, String cadenaBusqueda, List<String> tipoPrescripcionList,
            List<Integer> listEstatusPaciente, List<Integer> listEstatusPrescripcion,
            List<Integer> listEstatusSurtimiento, List<Estructura> listServiciosQueSurte) throws Exception;

    public boolean actualizaSurtimientoRecibidoPorGabinetes(String folio, int idEstatusSurtimiento, String idUsuario, Date updateFecha, boolean existeUsuario,
            Usuario usuario,int numeroMedicacion,boolean actSurtInsumo) throws Exception;

    public boolean actualizarRegistrosTracking(boolean existUsuario, Usuario usuario, Surtimiento surtimiento, List<SurtimientoInsumo> surtimientoInsumoList,
            List<SurtimientoEnviado> surtimientoEnviadoList, List<InventarioExtended> inventarioList, List<MovimientoInventario> movimientoInventarioList,List<SurtimientoMinistrado> surtMinistradoList) throws Exception;

    boolean clonarSurtimientoCancelado(String idSurtimiento, String newFolio) throws Exception;

    public boolean actualizarFolio(String idSurtimiento, String newFolio) throws Exception;

    public boolean actualizarTipoCancelacion(String idSurtimiento, int idTipoCancelacion) throws Exception;
    
    int obtenerSurtPrescripcion(String folio)throws Exception;
    
    int obtenerTotalSurtimiento(String folio) throws Exception;
    
    List<SurtimientoInsumo_Extend> obtenerDetalleSurtimiento(String idSurtimiento) throws Exception;
    
    List<SurtimientoEnviado_Extend> obtenerListaSurtimientoEnviadoPorIdSurtimientoDevolucion(String idSurtimiento) throws Exception;
    
    public boolean ligaridCapsulaconSurtimiento (Surtimiento surtircapsula)throws Exception;
    
    public Surtimiento_Extend obtenerSurtimientoExtendedPorIdSurtimiento(String idSurtimiento, String idPrescripcion, String folio, String idEstatusSurtimiento) throws Exception;
    
    SurtimientoEnviado_Extend obtenerByIdSurtimientoEnviado(String idSurtimientoEnviado) throws Exception;
    
    Surtimiento_Extend obtenerByIdSurtimiento(String idSurtimiento) throws Exception;
    
    Surtimiento_Extend obtenerByIdPrescripcion(String idPrescripcion) throws Exception;
    
    List<String> surtimientoEnviadoByPrecripcion(String idPrescripcion) throws Exception;
    
    SurtimientoEnviado_Extend obtenerSurtimientoEnviadoByIdSurtimientoInsumo(String idSurtimientoInsumo) throws Exception;
    
    public boolean updateAsignPrescripcion(List<Prescripcion_Extended> prescripcionList, List<PrescripcionInsumo_Extended> prescripcionInsumoList, List<Surtimiento_Extend > surtimientoList, List<SurtimientoInsumo_Extend> surtimientoInsumoList, SurtimientoEnviado_Extend surtimientoEnviadoDispensacionDirecta) throws Exception;
    
    public List<Surtimiento_Extend> obtenerSurtimientoConEstatus(List<String> listaSurtimiento,  List<Integer> listaEstatus) throws Exception;
    
    public String obtenerAlmacenPadreOfSurtimiento(String idEstructura) throws Exception;
    
    public boolean surtimientoParcial(Surtimiento surtimiento, List<SurtimientoInsumo> surtimientoInsumo) throws Exception;    
    
    public boolean actualizarClaveSolucionSurtimietoByFolio(String folio, String claveAgrupada) throws Exception;    
    
    public Surtimiento_Extend obtenerByFechaAndPrescripcion(String idPrescripcion, Date fechaProgramada) throws Exception;
    
    public Surtimiento_Extend obtenerDetallePrescripcionSolucion(String folio) throws Exception;
    
    public boolean actualizarInsumosValidacion(Solucion solucion, Surtimiento surt, List<SurtimientoInsumo> item,List<SurtimientoEnviado> enviadoList, List<DiagnosticoPaciente> diagnoticoList, boolean solicionRegistrada) throws Exception;
    
    public boolean surtirSolucion(            
            Surtimiento surtimiento,
            List<SurtimientoInsumo> surtimientoInsumoList,
            List<SurtimientoEnviado> surtimientoEnviadoList,
            List<Inventario> inventarioList,
            List<MovimientoInventario> movimientoInventarioList,
            Solucion solucion) throws Exception;

    List<Surtimiento_Extend> obtenerPorFechaEstructuraPacientePrescripcionSolucionLazy(
            Date fechaProgramada, Date fechaProgramadaFin, ParamBusquedaReporte paramBusquedaReporte,
            int startingAt, int maxPerPage
            , List<String> tipoPrescripcionList, List<Integer> listEstatusPaciente
            , List<Integer> listEstatusPrescripcion, List<Estructura> listServiciosQueSurte
            , List<Integer> estatusSurtimientoLista, List<Integer> estatusSolucionLista
            , String idTipoSolucion, int tipoProceso, String sortField, SortOrder sortOrder
            , String tipoPrescripcion, String estatusSolucion, String nombreEstructura, String tipoSolucion, String nombreMedico
            , String folio, String fechaProgramada2, String nombrePaciente, String cama, String folioPrescripcion, boolean agruparParaAutorizar
    ) throws Exception;
    
    Long obtenerTotalPorFechaEstructuraPacientePrescripcionSolucionLazy(
            Date fechaProgramada, ParamBusquedaReporte paramBusquedaReporte
            , List<String> tipoPrescripcionList, List<Integer> listEstatusPaciente
            , List<Integer> listEstatusPrescripcion, List<Estructura> listServiciosQueSurte
            , List<Integer> estatusSurtimientoLista, List<Integer> estatusSolucionLista
            , String idTipoSolucion, int tipoProceso
            , String tipoPrescripcion, String estatusSolucion, String nombreEstructura, String tipoSolucion, String nombreMedico
            , String folio, String fechaProgramada2, String nombrePaciente, String cama, String folioPrescripcion, boolean agruparParaAutorizar) throws Exception;

    public Surtimiento_Extend obtenerUltimoByIdPrescripcion(String idPrescripcion) throws Exception;
    
    public Surtimiento_Extend obtenerSurtimientoByFolioSurtimientoOrFolioPrescripcion(String folioSurtimiento, String folioPrescripcion) throws Exception;    
    
    public String registrarSurtimiento(Surtimiento surtimiento, List<SurtimientoInsumo> item) throws Exception;
    
    public Surtimiento_Extend obtenerSurtimientoExtendedByIdSurtimiento(String idSurtimiento) throws Exception;
        
    Long obtenerTotalOrdenesSolucionLazy(
            Date fechaProgramada,
            ParamBusquedaReporte paramBusquedaReporte, List<String> tipoPrescripcionList, List<Integer> listEstatusPaciente, List<Integer> listEstatusPrescripcion, List<Integer> listEstatusSurtimiento, List<Estructura> listServiciosQueSurte) throws Exception;
    
    List<Surtimiento_Extend> obtenerOrdenesSolucionLazy(
            Date fechaProgramada, ParamBusquedaReporte paramBusquedaReporte,
            int startingAt,
            int maxPerPage, List<String> tipoPrescripcionList, List<Integer> listEstatusPaciente, List<Integer> listEstatusPrescripcion,
            List<Integer> listEstatusSurtimiento, List<Estructura> listServiciosQueSurte,String sortField,SortOrder sortOrder) throws Exception;
    
    public Surtimiento_Extend obtenerSolucionPorIdSurtimiento(String idSurtimiento) throws Exception;
    
    public boolean cancelarPrescripcionSurtimientoSolucion(String idPrescripcion, Solucion s) throws Exception;

    List<Surtimiento_Extend> obtenerPorFechaEstructuraPacientePrescripcionFechaEntregSolucionLazy(
            Date fechaProgramada, ParamBusquedaReporte paramBusquedaReporte,
            int startingAt, int maxPerPage
            , List<String> tipoPrescripcionList, List<Integer> listEstatusPaciente
            , List<Integer> listEstatusPrescripcion, List<Estructura> listServiciosQueSurte
            , List<Integer> estatusSurtimientoLista, List<Integer> estatusSolucionLista
            , String sortField, SortOrder sortOrder
            , String idEstructura, String idTipoSolucion, Date fechaParaEntregar, Integer idHorarioParaEntregar
    ) throws Exception;

    Long obtenerTotalPorFechaEstructuraPacientePrescripcionFechaEntregSolucionLazy(
            Date fechaProgramada, ParamBusquedaReporte paramBusquedaReporte
            , List<String> tipoPrescripcionList, List<Integer> listEstatusPaciente
            , List<Integer> listEstatusPrescripcion, List<Estructura> listServiciosQueSurte
            , List<Integer> estatusSurtimientoLista, List<Integer> estatusSolucionLista
            , String idEstructura, String idTipoSolucion, Date fechaParaEntregar, Integer idHorarioParaEntregar
    ) throws Exception;
    
    public boolean cancelarPrescripcionNptSurtimientoSolucion(String idSurtimiento, Solucion s) throws Exception;

    public List<Surtimiento_Extend> obtenerSolucionesPorIdEstructuraTipoMezclaFechas(
            ParamBusquedaReporte paramBusquedaReporte
            , List<String> listTipoPrescripcion
            , List<Integer> listEstatusPaciente
            , List<Integer> listEstatusPrescripcion
            , List<Estructura> listServiciosQueSurte
            , List<Integer> estatusSurtimientoLista
            , List<Integer> estatusSolucionLista
            , String idTipoSolucion
            , String tipoPrescripcion
            , String tipoSolucion
            , String fechaProgramada
            , String folio
            , String folioPrescripcion
            , String nombrePaciente
            , String nombreEstructura
            , String cama
            , String nombreMedico
            , String estatusSolucion
    ) throws Exception;
    
    public Long obtenerTotalSolucionesPorIdEstructuraTipoMezclaFechas(
            ParamBusquedaReporte paramBusquedaReporte
            , List<String> listTipoPrescripcion
            , List<Integer> listEstatusPaciente
            , List<Integer> listEstatusPrescripcion
            , List<Estructura> listServiciosQueSurte
            , List<Integer> estatusSurtimientoLista
            , List<Integer> estatusSolucionLista
            , String idTipoSolucion
            , String tipoPrescripcion
            , String tipoSolucion
            , String fechaProgramada
            , String folio
            , String folioPrescripcion
            , String nombrePaciente
            , String nombreEstructura
            , String cama
            , String nombreMedico
            , String estatusSolucion
    ) throws Exception;

    
}
