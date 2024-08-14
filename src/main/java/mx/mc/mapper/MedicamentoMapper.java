/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.math.BigDecimal;
import java.util.List;
import mx.mc.model.Estructura;
import mx.mc.model.IntipharmItem;
import mx.mc.model.Medicamento;
import mx.mc.model.MedicamentoOff_Extended;
import mx.mc.model.Medicamento_Extended;
import mx.mc.model.NutricionParenteralDetalleExtended;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.VistaMedicamento;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author bbautista
 */
public interface MedicamentoMapper extends GenericCrudMapper<Medicamento,String> {
    
    List<Medicamento> obtenerMedicamentoByName(@Param ("valor") String valor);
    
    List<VistaMedicamento> obtenerBusqueda(@Param("cadena") String cadena,@Param("tipo") int tipo); 
    
    List<VistaMedicamento> obtenerBusquedaMedicamento(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte,
            @Param("tipoInsumo") int tipoInsumo,@Param("startingAt") int startingAt, @Param("maxPerPage") int maxPerPage
            ,@Param("sortField") String sortField,@Param("sortOrder") String sortOrder); 
    
    Long obtenerTotalBusquedaMedicamento(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte,@Param("tipoInsumo")int tipoInsumo);
    
    Medicamento obtenerPorIdMedicamento(@Param("idMedicamento") String idMedicamento);
    Medicamento obtenerImagen(@Param("idMedicamento") String idMedicamento);
    
    List<Medicamento_Extended> obtenerInsumoDisponible(@Param("cadenaBusqueda") String cadenaBusqueda, @Param("idEstructura") String idEstructura, @Param("noDiasCaducidad") Integer noDiasCaducidad );
    
    List<Medicamento_Extended> obtenerInsumoyLoteDisponible(@Param("cadenaBusqueda") String cadenaBusqueda, @Param("estructuraList") List<Estructura> estructuraList, @Param("noDiasCaducidad") Integer noDiasCaducidad );
    
    Medicamento_Extended obtenerInsumoDisponiblePorIdInsumo(@Param("estructuraList") List<Estructura> estructuraList, @Param("noDiasCaducidad") Integer noDiasCaducidad, @Param("idMedicamento") String idMedicamento );
    
    List<Medicamento_Extended> obtenerInsumoDisponiblePrescExt(@Param("cadenaBusqueda") String cadenaBusqueda, @Param("estructuraList") List<Estructura> estructuraList, @Param("noDiasCaducidad") Integer noDiasCaducidad );
    
    List<Medicamento> obtenerPorNombre(@Param("nombreCorto") String nombre);
    
    List<Medicamento> obtenerAutoCompNombreClave(@Param("nombreCorto") String nombre);
    
    Medicamento obtenerMedicamento(@Param("idMedicamento") String idMedicamento);
    List<Medicamento> obtenerXNombreTipo(@Param("cadena") String cadena,@Param("tipo") int tipo,@Param("idEstructura") String idEstructura); 
    List<Medicamento> tiposInsumoPorAlmacen(@Param("idEstructura") String idEstructura);
    
    List<Medicamento_Extended> obtenerMedicamentosPorPrescripcion(
            @Param("idPaciente") String idPaciente , 
            @Param("numRegistros") int numRegistros , 
            @Param("listEstatusMinistracion") List<Integer> listEstatusMinistracion,
            @Param("ministraPrevDays") Integer ministraPrevDays, 
            @Param("ministraLaterHours") Integer ministraLaterHours);
    
    List<Medicamento_Extended> obtenerDetallePrescripcionSolucion(
            @Param("folioPrescripcion") String folioPrescripcion );
    
    List<MedicamentoOff_Extended> obtenerMedicamentosPorPrescripcionList(
            @Param("idPaciente") List<String> idPaciente , 
            @Param("numRegistros") int numRegistros , 
            @Param("listEstatusMinistracion") List<Integer> listEstatusMinistracion,
            @Param("ministraPrevDays") Integer ministraPrevDays, 
            @Param("ministraLaterHours") Integer ministraLaterHours);
    
    List<Medicamento> obtenerInsumos(@Param("cadenaBusqueda") String cadenaBusqueda);
    
    Medicamento obtenerPorMedicamento(Medicamento medicamento);
    
    Medicamento obtenerMedicaByClave(@Param("claveInstitucional") String claveInstitucional);   
    
    Medicamento_Extended obtenerMedicamentoPresentacion(@Param("claveInstitucional") String claveInstitucional);   
    
    Medicamento obtenerMedicaLocalidad(@Param("idMedicamento") String idMedicamento);
    
    boolean actualizarLocalidadAVG(Medicamento medicamento);
    
    List<Medicamento> obtenerMedicamentosControlados(@Param("listaSubCategorias") List<Integer> listaSubCategorias);
    
    List<Medicamento_Extended> obtenerListaMedicamentos(@Param("claveInstitucional") String claveInstitucional, @Param("tipo") String tipo);
    
    List<Medicamento_Extended> obtenerListInventario(@Param("claveInstitucional") String claveInstitucional, @Param("claveAlmacen") String claveAlmacen); 
 
    List<Medicamento_Extended> obtenerListFechaCaducidad(@Param("lote") String lote);
    List<Medicamento_Extended> obtenerListInventarioSubAlmacen(@Param("claveInstitucional") String claveInstitucional, @Param("claveAlmacen") String claveAlmacen);
    
    List<IntipharmItem> obtenerSelectCabiDrgList();
    
    String validarExistencia(@Param("claveInstitucional") String claveInstitucional);
    
    List<Medicamento_Extended> searchMedicamentoAutoComplete(@Param("cadena") String cadena, @Param("idEstructura") String idEstructura, @Param("activa_autoCompleteInsumos") boolean activa_autoCompleteInsumos);
    
    List<Medicamento_Extended> searchMedicamentoAutoCompleteMultipleAlm(@Param("cadena") String cadena, @Param("idEstructuraList") List<String> idEstructuraList, @Param("activa_autoCompleteInsumos") boolean activa_autoCompleteInsumos);
    
    List<Medicamento_Extended> searchMedicamentoRecepcionAutoComplete(@Param("cadena") String cadena, @Param("idEstructura") String idEstructura, @Param("activa_autoCompleteInsumos") boolean activa_autoCompleteInsumos);
    
    List<Medicamento> obtenerTipoMedicamento(@Param("idMedicamento") String idMedicamento);
    
    List<Medicamento> obtenerInsumoLikeClave(@Param ("valor") String valor);
    
    List<MedicamentoOff_Extended> obtenerMedicamentosPorPrescripcionSync(
            @Param("idsEstructura") List<String> idsEstructura,
            @Param("listaEstatusPresc") List<Integer> listaEstatusPresc,
            @Param("listEstatusMinistracion") List<Integer> listEstatusMinistracion,
            @Param("ministraPrevDays") Integer ministraPrevDays, 
            @Param("ministraLaterHours") Integer ministraLaterHours);
    
    Medicamento_Extended obtenerMedicamentoByClaveSiam(@Param("claveInstitucional") String claveInstitucional);
    
    boolean updateEstatusInsumo(Medicamento medicamento);
    
    List<Medicamento> obtenerXNombreTipoCatalog(@Param("cadena") String cadena,@Param("tipo") int tipo); 
    
    List<VistaMedicamento> buscarSalMedicamento(@Param("cadena") String cadena);
    
    VistaMedicamento buscarMedicamentoPorId(@Param("idMedicamento") String idMedicamento);
     
    List<Medicamento> obtenerTodosMedicamentosActivos();
    
    List<NutricionParenteralDetalleExtended> obtenerMedicamentosParentales();
    
    Medicamento obtenerMedicamentoCaloria(String idMedicamento);
    
    List<Medicamento> obtenerCaloriasMedicamentos();
        
    List<Medicamento_Extended> obtenerInsumoLista(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte , @Param("startingAt") int startingAt
            , @Param("maxPerPage") int maxPerPage , @Param("sortField") String sortField , @Param("sortOrder") String sortOrder); 
    
    Long obtenerInsumoListaNoTotal(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte);
    
    Medicamento_Extended obtenerMedicamentoPorIdInsumo(@Param("idMedicamento") String idMedicamento);
    
    Medicamento_Extended obtenerMedicamentoByIdMedicamento(@Param("idMedicamento") String idMedicamento); 
    
    List<Medicamento_Extended> obtenerAutoCompleteMedicamento(
            @Param("cadena") String cadena
            , @Param("idEstructuraServicio") String idEstructuraServicio
            , @Param("listaTipoSurtimiento") List<String> listaTipoSurtimiento
    );
    
    List<Medicamento> obtenerMedicamentoAdicionarParaPrepararAsc();
    
    List<Medicamento_Extended> obtenerMedicamentoParaSRAM(@Param("cadena") String cadena, @Param("idEstructura") String idEstructura, @Param("activa_autoCompleteInsumos") boolean activa_autoCompleteInsumos);
    
    List<Medicamento_Extended> obtenerMedicamentoRemanente(
            @Param("cadena") String cadena, @Param("idEstructuraList") List<String> idEstructuraList
            , @Param("activa_autoCompleteInsumos") boolean activa_autoCompleteInsumos
            , @Param("remanetesSoluciones") Integer remanetesSoluciones );
    
    List<Medicamento> obtenerPorClaveInstitucional(@Param("listaClaveInstitucional") List<String> listaClaveInstitucional);
    
    List<Medicamento_Extended> searchMedicamentoAutoCompleteMultipleAlmConsolida(@Param("cadena") String cadena, @Param("idEstructuraList") List<String> idEstructuraList, @Param("activa_autoCompleteInsumos") boolean activa_autoCompleteInsumos);
    
    Medicamento_Extended obtenerMedicamentoExtended (@Param("claveInstitucional") String claveInstitucional);
    
    List<Medicamento_Extended> searchMedicamentAutoComplete(@Param("cadena") String cadena, @Param("tipo") Integer tipo);
    
}

