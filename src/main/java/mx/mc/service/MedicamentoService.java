/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.math.BigDecimal;
import java.util.List;
import mx.mc.model.Estructura;
import mx.mc.model.IntipharmItem;
import mx.mc.model.Medicamento;
import mx.mc.model.MedicamentoOff_Extended;
import mx.mc.model.Medicamento_Extended;
import mx.mc.model.NutricionParenteralDetalleExtended;
import mx.mc.model.VistaMedicamento;
import mx.mc.model.ParamBusquedaReporte;
import org.primefaces.model.SortOrder;
/**
 *
 * @author bbautista
 */
public interface MedicamentoService extends GenericCrudService<Medicamento,String> {
    
    public List<Medicamento> obtenerMedicamentoByName(String valor) throws Exception;
    
    public List<VistaMedicamento> obtenerBusqueda(String cadena, int tipo) throws Exception;
    
    public List<VistaMedicamento> obtenerBusquedaMedicamento(ParamBusquedaReporte paramBusquedaReporte,int tipoInsumo, int startingAt, int maxPerPage
    ,String sortField,SortOrder sortOrder) throws Exception;        
    
    public Long obtenerTotalBusquedaMedicamento(ParamBusquedaReporte paramBusquedaReporte,int tipoInsumo) throws Exception;
    
    public Medicamento obtenerPorIdMedicamento(String idMedicamento) throws Exception;
    public Medicamento obtenerImagen(String idMedicamento) throws Exception;
    
    public List<Medicamento_Extended> obtenerInsumoDisponible(String cadenaBusqueda, String idEstructura, Integer noDiasCaducidad) throws Exception;
    
    public List<Medicamento_Extended> obtenerInsumoyLoteDisponible(String cadenaBusqueda, List<Estructura> estructuraList, Integer noDiasCaducidad) throws Exception;
    
    public Medicamento_Extended obtenerInsumoDisponiblePorIdInsumo(List<Estructura> estructuraList, Integer noDiasCaducidad, String idMedicamento) throws Exception;
    
    public List<Medicamento_Extended> obtenerInsumoDisponiblePrescExt(String cadenaBusqueda, List<Estructura> estructuraList, Integer noDiasCaducidad) throws Exception;
    
    public List<Medicamento> obtenerPorNombre(String nombre) throws Exception;
    
    public List<Medicamento> obtenerAutoCompNombreClave(String nombre) throws Exception;
            
    public Medicamento obtenerMedicamento(String idMedicamento) throws Exception;
    public List<Medicamento> obtenerXNombreTipo(String cadena, int tipo,String idEstructura) throws Exception;
    public List<Medicamento> obtenerTiposInsumoPorAlmacen(String idEstructura) throws Exception;
    
    public List<Medicamento_Extended> obtenerMedicamentosPorPrescripcion(
            String idPaciente , int numRegistros , List<Integer> listEstatusSurtimiento, Integer ministraPrevDays, Integer ministraLaterHours) throws Exception;
    
    public List<Medicamento_Extended> obtenerDetallePrescripcionSolucion(String folioPrescripcion) throws Exception;
    
    public List<MedicamentoOff_Extended> obtenerMedicamentosPorPrescripcionList(
            List<String> idPaciente , int numRegistros , List<Integer> listEstatusSurtimiento, Integer ministraPrevDays, Integer ministraLaterHours) throws Exception;
    
    public List<Medicamento> obtenerInsumos(String cadenaBusqueda) throws Exception;
    
    public Medicamento obtenerMedicamento(Medicamento medicamento) throws Exception;
    
    public Medicamento obtenerMedicaByClave(String claveInstitucional) throws Exception;
    
    public Medicamento_Extended obtenerMedicamentoPresentacion(String claveInstitucional) throws Exception;
    
    public Medicamento obtenerMedicaLocalidad(String idMedicamento) throws Exception;
    
    boolean actualizarLocalidadAVG(Medicamento medicamento)throws Exception;
    
    public List<Medicamento> obtenerMedicamentosControlados(List<Integer> listaSubCategorias) throws Exception;
    
    public List<Medicamento_Extended> obtenerListaMedicamentos(String claveMedicamento, String tipo) throws Exception;
    
    public List<Medicamento_Extended> obtenerListInventario(String claveInstitucional, String claveAlmacen) throws Exception;
    
    public List<Medicamento_Extended> obtenerListInventarioSubAlmacen(String claveInstitucional, String claveAlmacen) throws Exception;
    
    /*Métodos Intipharm */
    public List<IntipharmItem> obtenerSelectCabiDrgList() throws Exception;
    
    public String validarExistencia(String claveInstitucional) throws Exception;
    
    public List<Medicamento_Extended> searchMedicamentoAutoComplete(String cadena, String idEstructura, boolean activaAutoCompleteInsumos) throws Exception;
    
    public List<Medicamento_Extended> searchMedicamentoAutoCompleteMultipleAlm(String cadena, List<String> idEstructuraList, boolean activaAutoCompleteInsumos) throws Exception;
    
    public List<Medicamento_Extended> searchMedicamentoRecepcionAutoComplete(String cadena, String idEstructura, boolean activaAutoCompleteInsumos) throws Exception;
    
    public List<Medicamento> obtenerTipoMedicamento(String idMedicamento) throws Exception;

    public List<Medicamento> obtenerInsumoLikeClave(String clave) throws Exception;
    
    public List<MedicamentoOff_Extended> obtenerMedicamentosPorPrescripcionSync(List<String> idsEstructura, List<Integer> listaEstatusPresc, List<Integer> listEstatusMinistracion, Integer ministraPrevDays, Integer ministraLaterHours) throws Exception;
    
    public Medicamento_Extended obtenerMedicamentoByClaveSiam(String claveInstitucional) throws Exception;
    
    public boolean updateEstatusInsumo(Medicamento medicamento) throws Exception;
    
    public List<Medicamento> obtenerXNombreTipoCatalog(String cadena, int tipo) throws Exception;
    
    public List<VistaMedicamento> buscarSalMedicamento(String cadena) throws Exception;
    
    public VistaMedicamento buscarMedicamentoPorId(String idMedicamento) throws Exception;
    
    public List<Medicamento> obtenerTodosMedicamentosActivos() throws Exception;
    
    public List<NutricionParenteralDetalleExtended> obtenerMedicamentosParentales() throws Exception;
    
    public Medicamento obtenerMedicamentoCaloria(String idMedicamento) throws Exception;
    
    public List<Medicamento> obtenerCaloriasMedicamentos() throws Exception;
    
    public List<Medicamento_Extended> obtenerInsumoLista(ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage
    ,String sortField,SortOrder sortOrder) throws Exception;
    
    public Long obtenerInsumoListaNoTotal (ParamBusquedaReporte paramBusquedaReporte) throws Exception;
    
    public Medicamento_Extended obtenerMedicamentoPorIdInsumo(String idMedicamento) throws Exception;
    
    public Medicamento_Extended obtenerMedicamentoByIdMedicamento(String idMedicamento) throws Exception;
    
    List<Medicamento_Extended> obtenerAutoCompleteMedicamento(String cadena, String idEstructuraServicio, List<String> listaTipoSurtimiento) throws Exception;
    
    public List<Medicamento> obtenerMedicamentoAdicionarParaPrepararAsc() throws Exception;
    
    public List<Medicamento_Extended> obtenerMedicamentoParaSRAM(String cadena, String idEstructura, boolean activaAutoCompleteInsumos) throws Exception;
    
    public List<Medicamento_Extended> obtenerMedicamentoRemanente(
            String cadena, List<String> idEstructuraList
            , boolean activaAutoCompleteInsumos
            , Integer remanetesSoluciones) throws Exception;
    
    public List<Medicamento> obtenerPorClaveInstitucional(List<String> listaClaveInstitucional ) throws Exception;
    
    public List<Medicamento_Extended> searchMedicamentoAutoCompleteMultipleAlmConsolida(String cadena, List<String> idEstructuraList, boolean activaAutoCompleteInsumos) throws Exception;
    
    public Medicamento_Extended obtenerMedicamentoExtended (String claveInstitucional) throws Exception;
    
    public List<Medicamento_Extended> searchMedicamentAutoComplete(String cadena, Integer tipo) throws Exception;
    
}

