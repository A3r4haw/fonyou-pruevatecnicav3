/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import mx.mc.mapper.DevolucionMezclaDetalleMapper;
import mx.mc.mapper.DevolucionMezclaMapper;
import mx.mc.mapper.FoliosMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.SolucionMapper;
import mx.mc.model.DevolucionMezcla;
import mx.mc.model.DevolucionMezclaDetalle;
import mx.mc.model.DevolucionMezclaExtended;
import mx.mc.model.Folios;
import mx.mc.model.Solucion;
import mx.mc.model.SurtimientoInsumo_Extend;
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
public class DevolucionMezclaServiceImpl extends GenericCrudServiceImpl<DevolucionMezcla,String> implements DevolucionMezclaService{

    @Autowired
    private DevolucionMezclaMapper devolucionMezclaMapper; 
    
    @Autowired
    private DevolucionMezclaDetalleMapper devolucionMezclaDetalleMapper; 

    @Autowired
    private FoliosMapper foliosMapper;
    
    @Autowired
    private SolucionMapper solucionMapper;
    
    
    @Autowired
    public DevolucionMezclaServiceImpl(GenericCrudMapper<DevolucionMezcla, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)  
    @Override
    public boolean insertarDevolucionMezcla(DevolucionMezcla devolucion, List<DevolucionMezclaDetalle> detalleMezclaList,Integer tipoDocumento) throws Exception {
        boolean res=true;
        try {
             //Obtener el Folio por tipo de documento
            Folios folios = foliosMapper.obtenerPrefixPorDocument(tipoDocumento);
            devolucion.setFolio(Comunes.generaFolio(folios));
            //Actualizar el folio
            folios.setSecuencia(Comunes.separaFolio(devolucion.getFolio()));
            folios.setUpdateFecha(new Date());
            folios.setUpdateIdUsuario(devolucion.getIdUsuario());

            res = foliosMapper.actualizaFolios(folios);
            if(res){
                res = devolucionMezclaMapper.insertar(devolucion);
                if(res){
                    res = devolucionMezclaDetalleMapper.insertListDetalleMezcla(detalleMezclaList);
                    if(res){
                        Solucion solucion = new Solucion();
                        solucion.setIdSolucion(devolucion.getIdSolucion());
                        solucion.setIdEstatusSolucion(devolucion.getIdEstatusSolucion());
                        solucion.setUpdateFecha(new Date());
                        solucion.setUpdateIdUsuario(devolucion.getInsertIdUsuario());
                        res = solucionMapper.actualizar(solucion);
                    }
                }else
                    throw new Exception("Ocurrio un error al momento de Actualizar el retiro en insertarDevolucionMezcla");
            }else
                throw new Exception("Ocurrio un error al momento de Actualizar el folio en insertarDevolucionMezcla");
        } catch (Exception e) {
            throw new Exception("Ocurrio un error al momento de insertarDevolucionMezcla:: " + e.getMessage());
        }
        return res;
    }
    
    @Override
    public List<SurtimientoInsumo_Extend>detalleDevolucionMezcla(String idDevolucionMezcla) throws Exception{
        try {
            return devolucionMezclaDetalleMapper.detalleDevolucionMezcla(idDevolucionMezcla);
        } catch (Exception ex) {
            throw new Exception("Error al obtener la lista de soluciones: "+ex.getMessage());
        }
    }
    
    @Override
    public DevolucionMezclaExtended obtenerDevolucionMezclaById(String idDevolucionMezcla) throws Exception{
        try {
            return devolucionMezclaMapper.obtenerDevolucionMezclaById(idDevolucionMezcla);            
        } catch (Exception ex) {
            throw new Exception("Error al obtener la lista de soluciones: "+ex.getMessage());
        }
    }
    
    @Override
    public DevolucionMezclaExtended obtenerSolucionByIdSolucion(String idSolucion) throws Exception {
        try {
            return devolucionMezclaMapper.obtenerSolucionByIdSolucion(idSolucion);
        } catch (Exception ex) {
            throw new Exception("Ocurrio un error al obtener la solucion: " + ex.getMessage());

        }
    }

    @Override
    public List<DevolucionMezclaExtended> obtenerBusquedaLazy(String cadena, String almacen, Integer idEstatusSolucion, int startingAt, int maxPerPage, String sortField, SortOrder sortOrder) throws Exception {
        List<DevolucionMezclaExtended> devolucionList = new ArrayList<>();
        try {
            String order = sortOrder == SortOrder.ASCENDING ? "asc" : sortOrder == SortOrder.DESCENDING ? "desc" : null;
            devolucionList = devolucionMezclaMapper.obtenerBusquedaLazy(cadena, almacen, idEstatusSolucion, startingAt, maxPerPage, sortField, order);
        } catch (Exception ex) {
            throw new Exception("Error al obtener la busqueda" + ex.getMessage());
        }
        return devolucionList;
    }
    
    @Override
    public Long obtenerBusquedaTotalLazy(String cadena, String almacen) throws Exception {
        try {
            return devolucionMezclaMapper.obtenerBusquedaTotalLazy(cadena, almacen);
        } catch (Exception ex) {
            throw new Exception("Error al obtener la busqueda" + ex.getMessage());
        }
    }
            
}
