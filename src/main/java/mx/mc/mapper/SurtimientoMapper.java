package mx.mc.mapper;

import java.util.Date;
import java.util.List;
import mx.mc.model.Estructura;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.Surtimiento;
import mx.mc.model.Surtimiento_Extend;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author hramirez
 */
public interface SurtimientoMapper extends GenericCrudMapper<Surtimiento, String> {
    
    boolean eliminarPorIdPrescripcion (@Param("idPrescripcion") String idPrescripcion);
            
    boolean cancelar (
            @Param("idPrescripcion") String idPrescripcion
            , @Param("idUsuario") String idUsuario
            , @Param("fecha") Date fecha
            , @Param("idEstatusSurtimiento") Integer idEstatusSurtimiento);
    
    boolean cancelarSurtimiento (
            @Param("idSurtimiento") String idSurtimiento
            , @Param("idUsuario") String idUsuario
            , @Param("fecha") Date fecha
            , @Param("idEstatusSurtimiento") Integer idEstatusSurtimiento);
    
    boolean cancelarSurtimientoChiconcuac (
            @Param("idPrescripcion") String idPrescripcion
            , @Param("idUsuario") String idUsuario
            , @Param("fecha") Date fecha
            , @Param("idEstatusSurtimiento") Integer idEstatusSurtimiento);
    
    List<Surtimiento_Extend> obtenerPorFechaEstructuraPacienteCamaPrescripcion(
            @Param("fechaProgramada") Date fechaProgramada
            , @Param("cadenaBusqueda") String cadenaBusqueda
            , @Param("listTipoPrescripcion") List<String> listTipoPrescripcion
            , @Param("listEstatusPaciente") List<Integer> listEstatusPaciente
            , @Param("listEstatusPrescripcion") List<Integer> listEstatusPrescripcion
            , @Param("listEstatusSurtimiento") List<Integer> listEstatusSurtimiento
            , @Param("listServiciosQueSurte") List<Estructura> listServiciosQueSurte
    );
    
    List<Surtimiento_Extend> obtenerPorFechaEstructuraPacienteCamaPrescripcionLazy(
            @Param("fechaProgramada") Date fechaProgramada
            ,@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte
            ,@Param("startingAt") int startingAt 
            ,@Param("maxPerPage") int maxPerPage
            , @Param("listTipoPrescripcion") List<String> listTipoPrescripcion
            , @Param("listEstatusPaciente") List<Integer> listEstatusPaciente
            , @Param("listEstatusPrescripcion") List<Integer> listEstatusPrescripcion
            , @Param("listEstatusSurtimiento") List<Integer> listEstatusSurtimiento
            , @Param("listServiciosQueSurte") List<Estructura> listServiciosQueSurte
    );
    
    List<Surtimiento_Extend> obtenerPorFechaEstructuraPacienteCamaPrescripcionOrderLazy(
            @Param("fechaProgramada") Date fechaProgramada
            ,@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte
            ,@Param("startingAt") int startingAt 
            ,@Param("maxPerPage") int maxPerPage
            , @Param("listTipoPrescripcion") List<String> listTipoPrescripcion
            , @Param("listEstatusPaciente") List<Integer> listEstatusPaciente
            , @Param("listEstatusPrescripcion") List<Integer> listEstatusPrescripcion
            , @Param("listEstatusSurtimiento") List<Integer> listEstatusSurtimiento
            , @Param("listServiciosQueSurte") List<Estructura> listServiciosQueSurte
            ,@Param("sortField") String sortField,@Param("sortOrder") String sortOrder
    );
    
    Long obtenerTotalPorFechaEstructuraPacienteCamaPrescripcionLazy(
            @Param("fechaProgramada") Date fechaProgramada
            ,@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte
            , @Param("listTipoPrescripcion") List<String> listTipoPrescripcion
            , @Param("listEstatusPaciente") List<Integer> listEstatusPaciente
            , @Param("listEstatusPrescripcion") List<Integer> listEstatusPrescripcion
            , @Param("listEstatusSurtimiento") List<Integer> listEstatusSurtimiento
            , @Param("listServiciosQueSurte") List<Estructura> listServiciosQueSurte
    );    
    
    List<Surtimiento_Extend> obtenerPorFechaEstructuraPacienteCamaPrescripcionSolucionOrderLazy(
            @Param("fechaProgramada") Date fechaProgramada
            ,@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte
            ,@Param("startingAt") int startingAt 
            ,@Param("maxPerPage") int maxPerPage
            , @Param("listTipoPrescripcion") List<String> listTipoPrescripcion
            , @Param("listEstatusPaciente") List<Integer> listEstatusPaciente
            , @Param("listEstatusPrescripcion") List<Integer> listEstatusPrescripcion
            , @Param("listEstatusSurtimiento") List<Integer> listEstatusSurtimiento
            , @Param("listServiciosQueSurte") List<Estructura> listServiciosQueSurte
            ,@Param("sortField") String sortField,@Param("sortOrder") String sortOrder
    );
    
    Long obtenerTotalPorFechaEstructuraPacienteCamaPrescripcionSolucionLazy(
            @Param("fechaProgramada") Date fechaProgramada
            ,@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte
            , @Param("listTipoPrescripcion") List<String> listTipoPrescripcion
            , @Param("listEstatusPaciente") List<Integer> listEstatusPaciente
            , @Param("listEstatusPrescripcion") List<Integer> listEstatusPrescripcion
            , @Param("listEstatusSurtimiento") List<Integer> listEstatusSurtimiento
            , @Param("listServiciosQueSurte") List<Estructura> listServiciosQueSurte
    );    
    
    List<Surtimiento_Extend> obtenerPorFechaEstructuraPacientePrescripcionExt(
            @Param("fechaProgramada") Date fechaProgramada
            , @Param("cadenaBusqueda") String cadenaBusqueda
            , @Param("listTipoPrescripcion") List<String> listTipoPrescripcion
            , @Param("numHorPrevReceta") Integer numHorPrevReceta
            , @Param("numHorPostReceta") Integer numHorPostReceta
            , @Param("listEstatusPrescripcion") List<Integer> listEstatusPrescripcion
            , @Param("listEstatusSurtimiento") List<Integer> listEstatusSurtimiento
            , @Param("listServiciosQueSurte") List<String> listServiciosQueSurte
            , @Param("startingAt") int startingAt ,@Param("maxPerPage") int maxPerPage
            , @Param("sortField") String sortField,@Param("sortOrder") String sortOrder
    );
    
    public Long obtenerTotalPorFechaEstructuraPacientePrescripcionExt(
            @Param("fechaProgramada") Date fechaProgramada
            , @Param("cadenaBusqueda") String cadenaBusqueda
            , @Param("listTipoPrescripcion") List<String> listTipoPrescripcion
            , @Param("numHorPrevReceta") Integer numHorPrevReceta
            , @Param("numHorPostReceta") Integer numHorPostReceta
            , @Param("listEstatusPrescripcion") List<Integer> listEstatusPrescripcion
            , @Param("listEstatusSurtimiento") List<Integer> listEstatusSurtimiento
            , @Param("listServiciosQueSurte") List<String> listServiciosQueSurte            
    );
    
    List<Surtimiento_Extend> obtenerPorIdInsumoIdPrescripcion(@Param("idSurtimiento") String idSurtimiento, @Param("idPrescripcion") String idPrescripcion);

    Integer obtenerNumeroSurtimientosProgramados (@Param("idPrescripcion") String idPrescripcion, @Param("idEstatusSurtimiento") Integer idEstatusSurtimiento);
    
    List<Surtimiento> obtenerSurtimientoConsumoExterna ();
    
    boolean actualizarPorFolio(@Param("folio") String folio, @Param("procesado") Integer procesado);
    
    boolean actualizarPorFolioVale(@Param("folio") String folio, @Param("procesado") Integer procesado);
    
    Surtimiento obtenerPorFolio(@Param("folio") String folio);
    boolean clonarSurtimientoPorId(@Param("idSurtimiento") String idSurtimiento,@Param("newFolio") String newFolio);
    List<Surtimiento_Extend> obtenerPorFechaEstructuraPacienteCamaPrescripcionCancelacion(
            @Param("fechaProgramada") Date fechaProgramada
            , @Param("cadenaBusqueda") String cadenaBusqueda
            , @Param("listTipoPrescripcion") List<String> listTipoPrescripcion
            , @Param("listEstatusPaciente") List<Integer> listEstatusPaciente
            , @Param("listEstatusPrescripcion") List<Integer> listEstatusPrescripcion
            , @Param("listEstatusSurtimiento") List<Integer> listEstatusSurtimiento
            , @Param("listServiciosQueSurte") List<Estructura> listServiciosQueSurte
    );
    List<Surtimiento_Extend> obtenerPorFechaEstructuraPacienteCamaPrescripcionCancelacionInterna(
            @Param("fechaProgramada") Date fechaProgramada
            , @Param("cadenaBusqueda") String cadenaBusqueda
            , @Param("listTipoPrescripcion") List<String> listTipoPrescripcion
            , @Param("listEstatusPaciente") List<Integer> listEstatusPaciente
            , @Param("listEstatusPrescripcion") List<Integer> listEstatusPrescripcion
            , @Param("listEstatusSurtimiento") List<Integer> listEstatusSurtimiento
            , @Param("listServiciosQueSurte") List<Estructura> listServiciosQueSurte
    );
    
    boolean actualizarPorFolioEstatusIntipharm(@Param("folio") String folio, @Param("idEstatusSurtimiento") int idEstatusSurtimiento,
            @Param("updateIdUsuario") String updateIdUsuario,@Param("updateFecha") Date updateFecha,
            @Param("numeroMedicacion") int numeroMedicacion,@Param("actualizar") boolean  actualizar);

    boolean clonarSurtimientoCanceladoPorId(@Param("idSurtimiento") String idSurtimiento,@Param("newFolio") String newFolio);
    boolean actualizarFolio(@Param("idSurtimiento") String idSurtimiento, @Param("newFolio") String newFolio);
    boolean actualizarTipoCancelacion(@Param("idSurtimiento") String idSurtimiento, @Param("idTipoCancelacion") int idTipoCancelacion);
    
    int obtenerSurtPrescripcion(@Param("folio") String folio);
    
    int obtenerTotalSurtimiento(@Param("folio") String folio);
    
    public boolean ligaridCapsulaconSurtimiento(Surtimiento surtircapsulal);
    
    Surtimiento_Extend obtenerSurtimientoExtendedPorIdSurtimiento(
             @Param("idSurtimiento") String idSurtimiento,
             @Param("idPrescripcion") String idPrescripcion,
             @Param("folio") String folio,
             @Param("idEstatusSurtimiento") String idEstatusSurtimiento
    );
    
    Surtimiento_Extend obtenerByIdSurtimiento(@Param("idSurtimiento") String idSurtimiento);
    
    
    Surtimiento_Extend obtenerByIdPrescripcion(@Param("idPrescripcion") String idPrescripcion);
    
    boolean updateAsignSurtimiento(@Param("idEstatusSurtimiento") Integer idEstatusSurtimiento, @Param("updateFecha") Date updateFecha, @Param("updateIdUsuario") String updateIdUsuario, @Param("idEstatusMirth") Integer idEstatusMirth, @Param("idSurtimiento") String idSurtimiento);
    
    List<Surtimiento_Extend> obtenerSurtimientoConEstatus(@Param("listaSurtimiento") List<String> listaSurtimiento, @Param("listaEstatus") List<Integer> listaEstatus);
        
    boolean actualizarClaveSolucionSurtimietoByFolio (@Param("folio") String folio, @Param("claveAgrupada") String claveAgrupada);
    
    Surtimiento_Extend obtenerByFechaAndPrescripcion(@Param("idPrescripcion") String idPrescripcion, @Param("fechaProgramada") Date fechaProgramada);
    
    Surtimiento_Extend obtenerDetallePrescripcionSolucion(@Param("folioPrescripcion") String folioPrescripcion); 
    
    List<Surtimiento_Extend> obtenerPorFechaEstructuraPacientePrescripcionSolucionLazy(
            @Param("fechaProgramada") Date fechaProgramada
            , @Param("fechaProgramadaFin") Date fechaProgramadaFin
            ,@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte
            ,@Param("startingAt") int startingAt 
            ,@Param("maxPerPage") int maxPerPage
            , @Param("listTipoPrescripcion") List<String> listTipoPrescripcion
            , @Param("listEstatusPaciente") List<Integer> listEstatusPaciente
            , @Param("listEstatusPrescripcion") List<Integer> listEstatusPrescripcion
            , @Param("listServiciosQueSurte") List<Estructura> listServiciosQueSurte
            , @Param("estatusSurtimientoLista") List<Integer> estatusSurtimientoLista
            , @Param("estatusSolucionLista") List<Integer> estatusSolucionLista
            , @Param("idTipoSolucion") String idTipoSolucion
            , @Param("tipoProceso") int tipoProceso
            , @Param("sortField") String sortField,@Param("sortOrder") String sortOrder
            , @Param("tipoPrescripcion") String tipoPrescripcion
            , @Param("estatusSolucion")String estatusSolucion
            , @Param("nombreEstructura") String nombreEstructura
            , @Param("tipoSolucion")String tipoSolucion
            , @Param("nombreMedico")String nombreMedico
            , @Param("folio")String folio
            , @Param("fechaProgramada2")String fechaProgramada2
            , @Param("nombrePaciente")String nombrePaciente
            , @Param("cama")String cama
            , @Param("folioPrescripcion")String folioPrescripcion
            , @Param("agruparParaAutorizar") boolean agruparParaAutorizar
    );
    
    Long obtenerTotalPorFechaEstructuraPacientePrescripcionSolucionLazy(
            @Param("fechaProgramada") Date fechaProgramada
            ,@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte
            , @Param("listTipoPrescripcion") List<String> listTipoPrescripcion
            , @Param("listEstatusPaciente") List<Integer> listEstatusPaciente
            , @Param("listEstatusPrescripcion") List<Integer> listEstatusPrescripcion
            , @Param("listServiciosQueSurte") List<Estructura> listServiciosQueSurte
            , @Param("estatusSurtimientoLista") List<Integer> estatusSurtimientoLista
            , @Param("estatusSolucionLista") List<Integer> estatusSolucionLista
            , @Param("idTipoSolucion") String idTipoSolucion
            , @Param("tipoProceso") int tipoProceso
            , @Param("tipoPrescripcion")String tipoPrescripcion
            , @Param("estatusSolucion")String estatusSolucion
            , @Param("nombreEstructura") String nombreEstructura
            , @Param("tipoSolucion")String tipoSolucion
            , @Param("nombreMedico")String nombreMedico
            , @Param("folio")String folio
            , @Param("fechaProgramada2")String fechaProgramada2
            , @Param("nombrePaciente")String nombrePaciente
            , @Param("cama")String cama
            , @Param("folioPrescripcion")String folioPrescripcion
            , @Param("agruparParaAutorizar") boolean agruparParaAutorizar
    );    
    
    Surtimiento_Extend obtenerUltimoByIdPrescripcion(@Param("idPrescripcion") String idPrescripcion);
    
    Surtimiento_Extend obtenerSurtimientoByFolioSurtimientoOrFolioPrescripcion(@Param("folioSurtimiento") String folioSurtimiento, @Param("folioPrescripcion") String folioPrescripcion);

    Long obtenerTotalOrdenesSolucionLazy(
            @Param("fechaProgramada") Date fechaProgramada
            ,@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte
            , @Param("listTipoPrescripcion") List<String> listTipoPrescripcion
            , @Param("listEstatusPaciente") List<Integer> listEstatusPaciente
            , @Param("listEstatusPrescripcion") List<Integer> listEstatusPrescripcion
            , @Param("listEstatusSurtimiento") List<Integer> listEstatusSurtimiento
            , @Param("listServiciosQueSurte") List<Estructura> listServiciosQueSurte
    );
    
    Surtimiento_Extend obtenerSurtimientoExtendedByIdSurtimiento(@Param("idSurtimiento") String idSurtimiento);
            
    List<Surtimiento_Extend> obtenerOrdenesSolucionLazy(
            @Param("fechaProgramada") Date fechaProgramada
            ,@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte
            ,@Param("startingAt") int startingAt 
            ,@Param("maxPerPage") int maxPerPage
            , @Param("listTipoPrescripcion") List<String> listTipoPrescripcion
            , @Param("listEstatusPaciente") List<Integer> listEstatusPaciente
            , @Param("listEstatusPrescripcion") List<Integer> listEstatusPrescripcion
            , @Param("listEstatusSurtimiento") List<Integer> listEstatusSurtimiento
            , @Param("listServiciosQueSurte") List<Estructura> listServiciosQueSurte
            ,@Param("sortField") String sortField,@Param("sortOrder") String sortOrder
    );
    
    Surtimiento_Extend obtenerSolucionPorIdSurtimiento(@Param("idSurtimiento") String idSurtimiento);

    List<Surtimiento_Extend> obtenerPorFechaEstructuraPacientePrescripcionFechaEntregSolucionLazy(
            @Param("fechaProgramada") Date fechaProgramada
            ,@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte
            ,@Param("startingAt") int startingAt 
            ,@Param("maxPerPage") int maxPerPage
            , @Param("listTipoPrescripcion") List<String> listTipoPrescripcion
            , @Param("listEstatusPaciente") List<Integer> listEstatusPaciente
            , @Param("listEstatusPrescripcion") List<Integer> listEstatusPrescripcion
            , @Param("listServiciosQueSurte") List<Estructura> listServiciosQueSurte
            , @Param("estatusSurtimientoLista") List<Integer> estatusSurtimientoLista
            , @Param("estatusSolucionLista") List<Integer> estatusSolucionLista
            , @Param("sortField") String sortField,@Param("sortOrder") String sortOrder
            , @Param("idEstructura") String idEstructura
            , @Param("idTipoSolucion") String idTipoSolucion
            , @Param("fechaParaEntregar") Date fechaParaEntregar
            , @Param("idHorarioParaEntregar") Integer idHorarioParaEntregar
    );

    Long obtenerTotalPorFechaEstructuraPacientePrescripcionFechaEntregSolucionLazy(
            @Param("fechaProgramada") Date fechaProgramada
            ,@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte
            , @Param("listTipoPrescripcion") List<String> listTipoPrescripcion
            , @Param("listEstatusPaciente") List<Integer> listEstatusPaciente
            , @Param("listEstatusPrescripcion") List<Integer> listEstatusPrescripcion
            , @Param("listServiciosQueSurte") List<Estructura> listServiciosQueSurte
            , @Param("estatusSurtimientoLista") List<Integer> estatusSurtimientoLista
            , @Param("estatusSolucionLista") List<Integer> estatusSolucionLista
            , @Param("idEstructura") String idEstructura
            , @Param("idTipoSolucion") String idTipoSolucion
            , @Param("fechaParaEntregar") Date fechaParaEntregar
            , @Param("idHorarioParaEntregar") Integer idHorarioParaEntregar
    );


    List<Surtimiento_Extend> obtenerSolucionesPorIdEstructuraTipoMezclaFechas(
            @Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte
            , @Param("listTipoPrescripcion") List<String> listTipoPrescripcion
            , @Param("listEstatusPaciente") List<Integer> listEstatusPaciente
            , @Param("listEstatusPrescripcion") List<Integer> listEstatusPrescripcion
            , @Param("listServiciosQueSurte") List<Estructura> listServiciosQueSurte
            , @Param("estatusSurtimientoLista") List<Integer> estatusSurtimientoLista
            , @Param("estatusSolucionLista") List<Integer> estatusSolucionLista
            , @Param("idTipoSolucion") String idTipoSolucion
            , @Param("tipoPrescripcion") String tipoPrescripcion
            , @Param("tipoSolucion")String tipoSolucion
            , @Param("fechaProgramada")String fechaProgramada
            , @Param("folio")String folio
            , @Param("folioPrescripcion")String folioPrescripcion
            , @Param("nombrePaciente")String nombrePaciente
            , @Param("nombreEstructura") String nombreEstructura
            , @Param("cama")String cama
            , @Param("nombreMedico")String nombreMedico
            , @Param("estatusSolucion")String estatusSolucion
    );
    
    Long obtenerTotalSolucionesPorIdEstructuraTipoMezclaFechas(
            @Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte
            , @Param("listTipoPrescripcion") List<String> listTipoPrescripcion
            , @Param("listEstatusPaciente") List<Integer> listEstatusPaciente
            , @Param("listEstatusPrescripcion") List<Integer> listEstatusPrescripcion
            , @Param("listServiciosQueSurte") List<Estructura> listServiciosQueSurte
            , @Param("estatusSurtimientoLista") List<Integer> estatusSurtimientoLista
            , @Param("estatusSolucionLista") List<Integer> estatusSolucionLista
            , @Param("idTipoSolucion") String idTipoSolucion
            , @Param("tipoPrescripcion") String tipoPrescripcion
            , @Param("tipoSolucion")String tipoSolucion
            , @Param("fechaProgramada")String fechaProgramada
            , @Param("folio")String folio
            , @Param("folioPrescripcion")String folioPrescripcion
            , @Param("nombrePaciente")String nombrePaciente
            , @Param("nombreEstructura") String nombreEstructura
            , @Param("cama")String cama
            , @Param("nombreMedico")String nombreMedico
            , @Param("estatusSolucion")String estatusSolucion
    );
    
    List<Surtimiento> obtenerSurtimientosByIdPrescripcion(@Param("idPrescripcion") String idPrescripcion); 
    
}
