/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.Date;
import java.util.List;
import mx.mc.enums.TipoDocumento_Enum;
import mx.mc.mapper.FoliosMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.MedicamentoConcomitanteMapper;
import mx.mc.mapper.ReaccionMapper;
import mx.mc.model.Folios;
import mx.mc.model.MedicamentoConcomitante;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.Reaccion;
import mx.mc.model.ReaccionExtend;
import mx.mc.util.Comunes;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author bbautista
 */
@Service
public class ReaccionServiceImpl extends GenericCrudServiceImpl<Reaccion, String> implements ReaccionService {    

    @Autowired
    ReaccionMapper reaccionMapper;
    
    
    @Autowired
    FoliosMapper folioMapper;
    
    @Autowired
    MedicamentoConcomitanteMapper concomitanteMapper;
    
    @Autowired
    public ReaccionServiceImpl(GenericCrudMapper<Reaccion, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
        
    
    @Override
    public List<ReaccionExtend> obtenerReaccionesListLazy(ParamBusquedaReporte paramBusquedaReporte, Integer estatusReaccionAdv,
            int startingAt, int maxPerPage, String sortField,SortOrder sortOrder) throws Exception {
        try {
            String order = sortOrder==SortOrder.ASCENDING ? "asc": sortOrder==SortOrder.DESCENDING ? "desc" : null;
            return reaccionMapper.obtenerReaccionesListLazy(paramBusquedaReporte, estatusReaccionAdv, startingAt, maxPerPage, sortField, order);
        } catch (Exception e) {
            throw new Exception("Errot al listar Surtimentos de Prescripciones y Soluciones" + e.getMessage());
        }
    }
    
    @Override
    public Long obtenerTotalReaccionesListLazy(ParamBusquedaReporte paramBusquedaReporte, Integer  estatusReaccionAdv) throws Exception {
        try {
            return reaccionMapper.obtenerTotalReaccionesListLazy(paramBusquedaReporte, estatusReaccionAdv);
        } catch (Exception e) {
            throw new Exception("Error al Obtener el total de Surtimientos de Prescripciones. " + e.getMessage());
        }
    }
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override    
    public boolean insertarReaccion(Reaccion reaccion,List<MedicamentoConcomitante> insumosList)throws Exception{
        boolean resultado=false;
        try{
            //Obtener el Folio por tipo de documento
            Folios folios = folioMapper.obtenerPrefixPorDocument(TipoDocumento_Enum.REACCIONADVERSA.getValue());
            reaccion.setFolio(Comunes.generaFolio(folios));
            //Actualizar el folio
            folios.setSecuencia(Comunes.separaFolio(reaccion.getFolio()));
            folios.setUpdateFecha(new Date());
            folios.setUpdateIdUsuario(reaccion.getInsertIdUsuario());

            boolean res = folioMapper.actualizaFolios(folios);
            if(res){                
                res = reaccionMapper.insertar(reaccion);
                
                if(res && insumosList.size()>0)
                    concomitanteMapper.insertarListaInsumos(insumosList);            
            }
            
        }catch(Exception ex){
            throw new Exception("Error al insertar la reacción: "+ex);
        }
        return resultado;
    }
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override    
    public boolean actualizarReaccion(Reaccion reaccion,List<MedicamentoConcomitante> insumosList)throws Exception{
        boolean resultado=false;
        try{
            resultado = reaccionMapper.actualizar(reaccion);
            if(resultado) {
                concomitanteMapper.eliminarInsumos(reaccion.getIdReaccion());
                if(insumosList.size()>0){
                    concomitanteMapper.insertarListaInsumos(insumosList);
                }  
            }
        }catch(Exception ex){
            throw new Exception("Error al insertar la reacción: "+ex);
        }
        return resultado;
    }    

    @Override
    public List<ReaccionExtend> obtenerReaccionesByIdPacienteIdInsumos(String idPaciente, List<String> listaInsumos) throws Exception{
        try {
            return reaccionMapper.obtenerReaccionesByIdPacienteIdInsumos(idPaciente, listaInsumos);
        } catch(Exception ex) {
            throw new Exception("Error al obtener reacciones RAM paciente e insumos: " + ex.getMessage());
        }
    }
    
}
