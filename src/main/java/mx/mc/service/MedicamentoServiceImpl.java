/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.MedicamentoMapper;
import mx.mc.model.Estructura;
import mx.mc.model.IntipharmItem;
import mx.mc.model.Medicamento;
import mx.mc.model.MedicamentoOff_Extended;
import mx.mc.model.Medicamento_Extended;
import mx.mc.model.NutricionParenteralDetalleExtended;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.VistaMedicamento;
import mx.mc.util.Comunes;
import org.primefaces.model.SortOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author bbautista
 */
@Service
public class MedicamentoServiceImpl extends GenericCrudServiceImpl<Medicamento, String> implements MedicamentoService {

    @Autowired
    private MedicamentoMapper medicamentoMapper;

    @Autowired
    public MedicamentoServiceImpl(GenericCrudMapper<Medicamento, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public List<VistaMedicamento> obtenerBusqueda(String cadena, int tipo) throws Exception {
        List<VistaMedicamento> medicamentoList = new ArrayList<>();
        try {
            medicamentoList = medicamentoMapper.obtenerBusqueda(cadena, tipo);
        } catch (Exception ex) {
            throw new Exception("Error al obtener la busqueda" + ex.getMessage());
        }
        return medicamentoList;
    }

    @Override
    public Medicamento obtenerPorIdMedicamento(String idMedicamento) throws Exception {
        Medicamento medicamentoSelect = new Medicamento();
        try {
            medicamentoSelect = medicamentoMapper.obtenerPorIdMedicamento(idMedicamento);
        } catch (Exception ex) {
            throw new Exception("Error al obtener Medicamento" + ex.getMessage());
        }
        return medicamentoSelect;
    }

    @Override
    public Medicamento obtenerImagen(String idMedicamento) throws Exception {
        Medicamento mdto = new Medicamento();
        try {
            mdto = medicamentoMapper.obtenerImagen(idMedicamento);
        } catch (Exception ex) {
            throw new Exception("Error al obtener Imagen Medicamento" + ex.getMessage());
        }
        return mdto;
    }

    @Override
    public List<Medicamento_Extended> obtenerInsumoDisponible(String cadenaBusqueda, String idEstructura, Integer noDiasCaducidad) throws Exception {
        try {
            return medicamentoMapper.obtenerInsumoDisponible(cadenaBusqueda, idEstructura, noDiasCaducidad);
        } catch (Exception ex) {
            throw new Exception("Error al obtener Insumo Disponible " + ex.getMessage());
        }
    }
    
    @Override
    public List<Medicamento_Extended> obtenerInsumoyLoteDisponible(String cadenaBusqueda, List<Estructura> estructuraList, Integer noDiasCaducidad) throws Exception {
        try {
            return medicamentoMapper.obtenerInsumoyLoteDisponible(cadenaBusqueda, estructuraList, noDiasCaducidad);
        } catch (Exception ex) {
            throw new Exception("Error al obtenerInsumoyLoteDisponible " + ex.getMessage());
        }
    }
    
    @Override
    public Medicamento_Extended obtenerInsumoDisponiblePorIdInsumo(List<Estructura> estructuraList, Integer noDiasCaducidad, String idMedicamento) throws Exception {
        try {
            return medicamentoMapper.obtenerInsumoDisponiblePorIdInsumo(estructuraList, noDiasCaducidad, idMedicamento);
        } catch (Exception ex) {
            throw new Exception("Error al obtenerInsumoDisponiblePorIdInsumo " + ex.getMessage());
        }
    }
    
    @Override
    public List<Medicamento_Extended> obtenerInsumoDisponiblePrescExt(String cadenaBusqueda, List<Estructura> estructuraList, Integer noDiasCaducidad) throws Exception {
        try {
            return medicamentoMapper.obtenerInsumoDisponiblePrescExt(cadenaBusqueda, estructuraList, noDiasCaducidad);
        } catch (Exception ex) {
            throw new Exception("Error al obtenerInsumoDisponiblePrescExt " + ex.getMessage());
        }
    }

    @Override
    public Medicamento obtenerMedicamento(String idMedicamento) throws Exception {
        try {
            return medicamentoMapper.obtenerMedicamento(idMedicamento);
        } catch (Exception ex) {
            throw new Exception("Error al obtener Insumo. " + ex.getMessage());
        }
    }
    
    @Override
    public List<Medicamento> obtenerPorNombre(String nombre) throws Exception{
        try{
            return medicamentoMapper.obtenerPorNombre(nombre);
        }catch(Exception ex){
            throw new Exception("Error al obtener Insumos " + ex.getMessage());
        }        
    }
    @Override
    public List<Medicamento> obtenerXNombreTipo(String cadena, int tipo,String idEstructura) throws Exception {
        List<Medicamento> medicamentoList = new ArrayList<>();
        try {
            medicamentoList = medicamentoMapper.obtenerXNombreTipo(cadena, tipo,idEstructura);
        } catch (Exception ex) {
            throw new Exception("Error al obtener la busqueda" + ex.getMessage());
        }
        return medicamentoList;
    }
    @Override
    public List<Medicamento> obtenerTiposInsumoPorAlmacen(String idEstructura) throws Exception {
        try{
            return medicamentoMapper.tiposInsumoPorAlmacen(idEstructura);
        }catch(Exception ex){
            throw new Exception("Error al obtener los tipos de servicio" + ex.getMessage());
        }
    }

    @Override
    public List<Medicamento_Extended> obtenerMedicamentosPorPrescripcion(
            String idPaciente , int numRegistros , List<Integer> listEstatusMinistracion,
            Integer ministraPrevDays, Integer ministraLaterHours) throws Exception {
        
        try{
            return medicamentoMapper.obtenerMedicamentosPorPrescripcion(
                    idPaciente , numRegistros ,listEstatusMinistracion, ministraPrevDays, ministraLaterHours);
        }catch(Exception ex){
            throw new Exception("Error al obtener los tipos de servicio" + ex.getMessage());
        }
    }
    
    @Override
    public List<Medicamento_Extended> obtenerDetallePrescripcionSolucion(String folioPrescripcion) throws Exception {
        
        try{
            return medicamentoMapper.obtenerDetallePrescripcionSolucion(folioPrescripcion);
        }catch(Exception ex){
            throw new Exception("Error al obtener los tipos de servicio" + ex.getMessage());
        }
    }
    
    @Override
    public List<MedicamentoOff_Extended> obtenerMedicamentosPorPrescripcionList(
            List<String> idPaciente , int numRegistros , List<Integer> listEstatusMinistracion,
            Integer ministraPrevDays, Integer ministraLaterHours) throws Exception {
        
        try{
            return medicamentoMapper.obtenerMedicamentosPorPrescripcionList(
                    idPaciente , numRegistros ,listEstatusMinistracion, ministraPrevDays, ministraLaterHours);
        }catch(Exception ex){
            throw new Exception("Error al obtener los tipos de servicio" + ex.getMessage());
        }
    }

    @Override
    public List<Medicamento> obtenerInsumos(String cadenaBusqueda) throws Exception {
        try {
            return medicamentoMapper.obtenerInsumos(cadenaBusqueda);
        } catch (Exception ex) {
            throw new Exception("Error al obtener Insumos " + ex.getMessage());
        }
    }

    @Override
    public Medicamento obtenerMedicamento(Medicamento medicamento) throws Exception {
        try {
            return medicamentoMapper.obtenerPorMedicamento(medicamento);
        } catch (Exception ex) {
            throw new Exception("Error al obtener Insumo. " + ex.getMessage());
        }
    }

    @Override
    public List<Medicamento> obtenerMedicamentoByName(String valor) throws Exception {
        try {
            return medicamentoMapper.obtenerMedicamentoByName(valor);
        } catch (Exception e) {
         throw  new Exception("Error al obtener el Medicamento:" + e.getMessage());
        }
    }

    @Override
    public List<Medicamento> obtenerAutoCompNombreClave(String nombre) throws Exception {
        try {
            return medicamentoMapper.obtenerAutoCompNombreClave(nombre);
        } catch (Exception e) {
            throw new Exception("Error al obtener la Clave y Nombre del Medicamento" + e.getMessage());
        }
    }

    @Override
    public Medicamento obtenerMedicaByClave(String claveInstitucional) throws Exception {
        try {
            return medicamentoMapper.obtenerMedicaByClave(claveInstitucional);
        } catch (Exception e) {
            throw  new  Exception("Error al Obtener el Medicamento " + e.getMessage());
        }
        
    }
    
    @Override
    public Medicamento_Extended obtenerMedicamentoPresentacion(String claveInstitucional) throws Exception {
        try {
            return medicamentoMapper.obtenerMedicamentoPresentacion(claveInstitucional);
        } catch (Exception e) {
            throw  new  Exception("Error al Obtener el Medicamento " + e.getMessage());
        }
        
    }

    @Override
    public Medicamento obtenerMedicaLocalidad(String idMedicamento) throws Exception {
         Medicamento medicamentoSelect = new Medicamento();
        try {
            medicamentoSelect = medicamentoMapper.obtenerMedicaLocalidad(idMedicamento);
        } catch (Exception ex) {
            throw new Exception("Error al obtener Medicamento" + ex.getMessage());
        }
        return medicamentoSelect;
    }

    @Override
    public boolean actualizarLocalidadAVG(Medicamento medicamento) throws Exception {
       try {
           return medicamentoMapper.actualizarLocalidadAVG(medicamento);
       } catch(Exception ex) {
           throw new Exception("Error al actualizar localidad de  Medicamento" + ex.getMessage());
       }
    }
    
    @Override
    public List<Medicamento> obtenerMedicamentosControlados(List<Integer> listaSubCategorias) throws Exception {
        try {
            return medicamentoMapper.obtenerMedicamentosControlados(listaSubCategorias);
        } catch (Exception e) {
            throw new Exception("Error al obtener la Clave y Nombre del Medicamento" + e.getMessage());
        }
    }

    @Override
    public List<Medicamento_Extended> obtenerListaMedicamentos(String claveInstitucional, String tipo) throws Exception {
        try {
            return medicamentoMapper.obtenerListaMedicamentos(claveInstitucional, tipo);
        } catch (Exception e) {
            throw new Exception("Error al obtener Medicamentos para WebService" + e.getMessage());
        }
    }

    @Override
    public List<Medicamento_Extended> obtenerListInventario(String claveInstitucional, String claveAlmacen) throws Exception {
        try {
            return medicamentoMapper.obtenerListInventario(claveInstitucional,claveAlmacen);
        } catch (Exception e) {
            throw new Exception("Error al obtener inventario por WebService" + e.getMessage());
        }
    }    

    @Override
    public List<Medicamento_Extended> obtenerListInventarioSubAlmacen(String claveInstitucional, String claveAlmacen) throws Exception {
        try {
            return medicamentoMapper.obtenerListInventarioSubAlmacen(claveInstitucional,claveAlmacen);
        } catch (Exception e) {
            throw new Exception("Error al obtener inventario por WebService" + e.getMessage());
        }
    }    
    
    
    //  Métodos de Intipharm
    
    @Override
    public List<IntipharmItem> obtenerSelectCabiDrgList() throws Exception {
        try {
            return medicamentoMapper.obtenerSelectCabiDrgList();
        } catch (Exception e) {
            throw new Exception("Error al obtener los registros " + e.getMessage());
        }
        
    }

    @Override
    public String validarExistencia(String claveInstitucional) throws Exception {
       try {
           return medicamentoMapper.validarExistencia(claveInstitucional);
       } catch (Exception ex) {
           throw new Exception("Error al momento de buscar medicamento por clave   " + ex.getMessage());
       }
    }

    @Override
    public List<VistaMedicamento> obtenerBusquedaMedicamento(ParamBusquedaReporte paramBusquedaReporte, int tipoInsumo, int startingAt, 
            int maxPerPage, String sortField, SortOrder sortOrder) throws Exception {
        try {
            String order = sortOrder==SortOrder.ASCENDING ? "asc": sortOrder==SortOrder.DESCENDING ? "desc" : null;            
            return  medicamentoMapper.obtenerBusquedaMedicamento(paramBusquedaReporte, tipoInsumo, startingAt, maxPerPage, sortField, order);
        } catch (Exception e) {
           throw new Exception("Error al momento de buscar Medicamento  " + e.getMessage()); 
        }
    }

    @Override
    public Long obtenerTotalBusquedaMedicamento(ParamBusquedaReporte paramBusquedaReporte, int tipoInsumo) throws Exception {
        try {
            return medicamentoMapper.obtenerTotalBusquedaMedicamento(paramBusquedaReporte, tipoInsumo);
        } catch (Exception e) {
           throw new Exception("Error al momento de obtenerTotalBusquedaMedicamento " + e.getMessage());  
        }
    }
    
    @Override
    public List<Medicamento_Extended> searchMedicamentoAutoComplete(String cadena, String idEstructura, boolean activaAutoCompleteInsumos) throws Exception{ 
        try {
                List<Medicamento_Extended> listaMedicamentos = medicamentoMapper
                        .searchMedicamentoAutoComplete(cadena, idEstructura, activaAutoCompleteInsumos);
                if (listaMedicamentos == null) {
                    listaMedicamentos = new ArrayList<>();
                }
                /* TODO: APL-Revisar porqué se setea el idMedicamento a un nuevo UUID */
                /* else {
                    for(int i = 0 ; i < listaMedicamentos.size(); i++){
                        Medicamento_Extended med = listaMedicamentos.get(i);
                        med.setIdMedicamento(Comunes.getUUID());
                    }
                }*/
                return listaMedicamentos;
        } catch (Exception ex) {
            throw new Exception("Error al momento de obtener medicamentos del inventario " + ex.getMessage());
        }
        
    }
        
    @Override
    public List<Medicamento_Extended> searchMedicamentoAutoCompleteMultipleAlm(String cadena, List<String> idEstructuraList, boolean activaAutoCompleteInsumos) throws Exception{ 
        try {
                List<Medicamento_Extended> listaMedicamentos = medicamentoMapper
                        .searchMedicamentoAutoCompleteMultipleAlm(cadena, idEstructuraList, activaAutoCompleteInsumos);
                if (listaMedicamentos == null) {
                    listaMedicamentos = new ArrayList<>();
                }
                return listaMedicamentos;
        } catch (Exception ex) {
            throw new Exception("Error al momento de obtener medicamentos del inventario " + ex.getMessage());
        }
        
    }

    @Override
    public List<Medicamento_Extended> searchMedicamentoRecepcionAutoComplete(String cadena, String idEstructura, boolean activaAutoCompleteInsumos) throws Exception{ 
        try {
                List<Medicamento_Extended> listaMedicamentos = medicamentoMapper
                        .searchMedicamentoRecepcionAutoComplete(cadena, idEstructura, activaAutoCompleteInsumos);
                if (listaMedicamentos == null) {
                    listaMedicamentos = new ArrayList<>();
                }else{
                    for(int i = 0 ; i < listaMedicamentos.size(); i++){
                        Medicamento_Extended med = listaMedicamentos.get(i);
                        med.setIdMedicamento(Comunes.getUUID());
                    }
                }
                return listaMedicamentos;
        } catch (Exception ex) {
            throw new Exception("Error al momento de obtener medicamentos del inventario " + ex.getMessage());
        }
        
    }
    @Override
    public List<Medicamento> obtenerTipoMedicamento(String idMedicamento) throws Exception {
        try {
            return medicamentoMapper.obtenerTipoMedicamento(idMedicamento);
        } catch (Exception ex) {
            throw new Exception("Error al obtener Tipo Insumo " + ex);
        }

    }
    
    @Override
    public List<Medicamento> obtenerInsumoLikeClave(String clave) throws Exception {
        try {
            return medicamentoMapper.obtenerInsumoLikeClave(clave);
        } catch (Exception ex) {
            throw new Exception("Error al obtener Tipo Insumo " + ex);
        }

    }
    @Override
    public List<MedicamentoOff_Extended> obtenerMedicamentosPorPrescripcionSync(List<String> idsEstructura, List<Integer> listaEstatusPresc,
            List<Integer> listEstatusMinistracion, Integer ministraPrevDays, Integer ministraLaterHours) throws Exception {
        
        try {
            return medicamentoMapper.obtenerMedicamentosPorPrescripcionSync(idsEstructura, listaEstatusPresc, listEstatusMinistracion, ministraPrevDays, ministraLaterHours);
        } catch(Exception ex) {
            throw new Exception("Error al obtener los tipos de servicio" + ex.getMessage());
        }
    }

    @Override
    public Medicamento_Extended obtenerMedicamentoByClaveSiam(String claveInstitucional) throws Exception {
        try {
            return medicamentoMapper.obtenerMedicamentoByClaveSiam(claveInstitucional);
        } catch (Exception e) {
            throw new Exception("Error al obtener obtenerMedicamentoByClaveSiam() " + e.getMessage());
        }
    }

    @Override
    public boolean updateEstatusInsumo(Medicamento medicamento) throws Exception {
        try {
            return medicamentoMapper.updateEstatusInsumo(medicamento);
        } catch (Exception e) {
            throw new Exception("Error al actualizar updateEstatusInsumo() " + e.getMessage());
        }
    }
    
    @Override
    public List<Medicamento> obtenerXNombreTipoCatalog(String cadena, int tipo) throws Exception {
        List<Medicamento> medicamentoList = new ArrayList<>();
        try {
            medicamentoList = medicamentoMapper.obtenerXNombreTipoCatalog(cadena, tipo);
        } catch (Exception ex) {
            throw new Exception("Error al obtener la busqueda" + ex.getMessage());
        }
        return medicamentoList;
    }

    @Override
    public List<VistaMedicamento> buscarSalMedicamento(String cadena) throws Exception {
        List<VistaMedicamento> listaMedicamentos =  new ArrayList<>();
        try {
            listaMedicamentos = medicamentoMapper.buscarSalMedicamento(cadena);
        } catch(Exception ex) {
            throw new Exception("Error en autocomplete para buscar la sal de un medicamento" + ex.getMessage());
        }
        return listaMedicamentos;
    }

    @Override
    public VistaMedicamento buscarMedicamentoPorId(String idMedicamento) throws Exception {        
        try {
            return medicamentoMapper.buscarMedicamentoPorId(idMedicamento);
        } catch(Exception ex) {
            throw new Exception("Error en autocomplete para buscar la sal de un medicamento" + ex.getMessage());
        }
        
    }

    @Override
    public List<Medicamento> obtenerTodosMedicamentosActivos() throws Exception {
        try {
            return medicamentoMapper.obtenerTodosMedicamentosActivos();
        } catch(Exception ex) {
            throw new Exception("Error al obtener lista de medicamentos activos:  " + ex.getMessage());
        }
    }

    @Override
    public List<NutricionParenteralDetalleExtended> obtenerMedicamentosParentales() throws Exception {
        try {
            return medicamentoMapper.obtenerMedicamentosParentales();
        } catch(Exception ex) {
            throw new Exception("Error al obtener lista de medicamentos parentales:  " + ex.getMessage());
        }
    }
    
    @Override
    public Medicamento obtenerMedicamentoCaloria(String idMedicamento) throws Exception {
        try {
            return medicamentoMapper.obtenerMedicamentoCaloria(idMedicamento);
        } catch(Exception ex) {
            throw new Exception("Error al obtener obtenerMedicamentoCaloria:  " + ex.getMessage());
        }
    }
    
    @Override
    public List<Medicamento> obtenerCaloriasMedicamentos() throws Exception {
        try {
            return medicamentoMapper.obtenerCaloriasMedicamentos();
        } catch(Exception ex) {
            throw new Exception("Error al obtener obtenerCaloriasMedicamentos:  " + ex.getMessage());
        }
    }
    
    
    @Override
    public List<Medicamento_Extended> obtenerInsumoLista(ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage, String sortField,SortOrder sortOrder) throws Exception {
        try {
            return medicamentoMapper.obtenerInsumoLista(paramBusquedaReporte, startingAt, maxPerPage, sortField, sortField);
        } catch (Exception e) {
         throw  new Exception("Error al obtener la lista de insumos:" + e.getMessage());
        }
    }
    
    @Override
    public Long obtenerInsumoListaNoTotal (ParamBusquedaReporte paramBusquedaReporte) throws Exception {
        try {
            return medicamentoMapper.obtenerInsumoListaNoTotal(paramBusquedaReporte);
        } catch (Exception e) {
         throw  new Exception("Error al obtener Numero de registros totales de la lista de insumos:" + e.getMessage());
        }
    }
    
    @Override
    public Medicamento_Extended obtenerMedicamentoPorIdInsumo(String idMedicamento) throws Exception {
        try {
            return medicamentoMapper.obtenerMedicamentoPorIdInsumo(idMedicamento);
        } catch (Exception ex) {
            throw new Exception("Error al obtener Insumo por idMedicamento. " + ex.getMessage());
        }
    }
    
    @Override
    public Medicamento_Extended obtenerMedicamentoByIdMedicamento(String idMedicamento) throws Exception {
        Medicamento_Extended medicamentoSelect = new Medicamento_Extended();
        try {
            medicamentoSelect = medicamentoMapper.obtenerMedicamentoByIdMedicamento(idMedicamento);
        } catch (Exception ex) {
            throw new Exception("Error al obtener Medicamento" + ex.getMessage());
        }
        return medicamentoSelect;
    }

    @Override
    public List<Medicamento_Extended> obtenerAutoCompleteMedicamento(String cadena, String idEstructuraServicio, List<String> listaTipoSurtimiento) throws Exception {
        try {
            return medicamentoMapper.obtenerAutoCompleteMedicamento(cadena, idEstructuraServicio, listaTipoSurtimiento);
        } catch (Exception e) {
            throw new Exception("Error al obtener la Clave y Nombre de los Medicamentos " + e.getMessage());
        }
    }
    
    @Override
    public List<Medicamento> obtenerMedicamentoAdicionarParaPrepararAsc() throws Exception {
        try {
            return medicamentoMapper.obtenerMedicamentoAdicionarParaPrepararAsc();
        } catch (Exception e) {
            throw new Exception("Error al obtener medicamentos por prioridad a preparar " + e.getMessage());
        }
    }
    
    @Override
    public List<Medicamento_Extended> obtenerMedicamentoParaSRAM(String cadena, String idEstructura, boolean activaAutoCompleteInsumos) throws Exception{ 
        try {
                List<Medicamento_Extended> listaMedicamentos = medicamentoMapper
                        .obtenerMedicamentoParaSRAM(cadena, idEstructura, activaAutoCompleteInsumos);
                if (listaMedicamentos == null) {
                    listaMedicamentos = new ArrayList<>();
                }
                /* TODO: APL-Revisar porqué se setea el idMedicamento a un nuevo UUID */
                /* else {
                    for(int i = 0 ; i < listaMedicamentos.size(); i++){
                        Medicamento_Extended med = listaMedicamentos.get(i);
                        med.setIdMedicamento(Comunes.getUUID());
                    }
                }*/
                return listaMedicamentos;
        } catch (Exception ex) {
            throw new Exception("Error al momento de obtener medicamentos del inventario " + ex.getMessage());
        }
        
    }
    
    @Override
    public List<Medicamento_Extended> obtenerMedicamentoRemanente(
            String cadena, List<String> idEstructuraList
            , boolean activaAutoCompleteInsumos
            , Integer remanetesSoluciones) throws Exception{ 
        try {
                List<Medicamento_Extended> listaMedicamentos = medicamentoMapper
                        .obtenerMedicamentoRemanente(cadena, idEstructuraList, activaAutoCompleteInsumos, remanetesSoluciones);
                if (listaMedicamentos == null) {
                    listaMedicamentos = new ArrayList<>();
                }
                /* TODO: APL-Revisar porqué se setea el idMedicamento a un nuevo UUID */
                /* else {
                    for(int i = 0 ; i < listaMedicamentos.size(); i++){
                        Medicamento_Extended med = listaMedicamentos.get(i);
                        med.setIdMedicamento(Comunes.getUUID());
                    }
                }*/
                return listaMedicamentos;
        } catch (Exception ex) {
            throw new Exception("Error al momento de obtener medicamentos del inventario " + ex.getMessage());
        }
        
    }

    @Override
    public List<Medicamento> obtenerPorClaveInstitucional(List<String> listaClaveInstitucional ) throws Exception {
        try {
            return medicamentoMapper.obtenerPorClaveInstitucional(listaClaveInstitucional);
        } catch (Exception ex) {
            throw new Exception("Error al obtener Insumo por clave istitucional " + ex.getMessage());
        }
    }
    

    @Override
    public List<Medicamento_Extended> searchMedicamentoAutoCompleteMultipleAlmConsolida(String cadena, List<String> idEstructuraList, boolean activaAutoCompleteInsumos) throws Exception{ 
        try {
                List<Medicamento_Extended> listaMedicamentos = medicamentoMapper
                        .searchMedicamentoAutoCompleteMultipleAlmConsolida(cadena, idEstructuraList, activaAutoCompleteInsumos);
                if (listaMedicamentos == null) {
                    listaMedicamentos = new ArrayList<>();
                }
                return listaMedicamentos;
        } catch (Exception ex) {
            throw new Exception("Error al momento de obtener medicamentos del inventario " + ex.getMessage());
        }
        
    }

    
    @Override
    public Medicamento_Extended obtenerMedicamentoExtended (String claveInstitucional) throws Exception {
        try {
            return medicamentoMapper.obtenerMedicamentoExtended(claveInstitucional);
        } catch (Exception ex) {
            throw new Exception("Error al obtener Insumo. " + ex.getMessage());
        }
    }
    
    @Override
    public List<Medicamento_Extended> searchMedicamentAutoComplete(String cadena, Integer tipo) throws Exception {
        try {
            return medicamentoMapper.searchMedicamentAutoComplete(cadena, tipo);
        }catch (Exception ex) {
            throw new Exception("Error al obtener Insumo. " + ex.getMessage());
        }
    }
}
