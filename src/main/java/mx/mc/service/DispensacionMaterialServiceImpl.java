/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.enums.TipoDocumento_Enum;
import mx.mc.mapper.DispensacionMaterialInsumoMapper;
import mx.mc.mapper.DispensacionMaterialMapper;
import mx.mc.mapper.FoliosMapper;
import mx.mc.mapper.InventarioMapper;
import mx.mc.mapper.MovimientoInventarioMapper;
import mx.mc.model.DispensacionMaterial;
import mx.mc.model.DispensacionMaterialExtended;
import mx.mc.model.DispensacionMaterialInsumo;
import mx.mc.model.Folios;
import mx.mc.model.Inventario;
import mx.mc.model.MovimientoInventario;
import mx.mc.model.ParamBusquedaReporte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author apalacios
 */
@Service
public class DispensacionMaterialServiceImpl implements DispensacionMaterialService {
    
    @Autowired
    private DispensacionMaterialMapper dispensacionMaterialMapper;
    
    @Autowired
    private DispensacionMaterialInsumoMapper dispensacionMaterialInsumoMapper;    
    
    @Autowired
    private FoliosMapper foliosMapper;
    
    @Autowired
    private InventarioMapper inventarioMapper;
    
    @Autowired
    private MovimientoInventarioMapper movimientoInventarioMapper;
    
    @Override
    public List<DispensacionMaterialExtended> obtenerDispensacionesLazzy(String idEstructura, String idUsuario, String cadenaBusqueda, int startingAt, 
            int maxPerPage, String sortBy, String sortOrder) throws Exception {
        try {
            return dispensacionMaterialMapper.obtenerDispensacionesLazzy(idEstructura, idUsuario, cadenaBusqueda, startingAt, maxPerPage, sortBy, sortOrder);
        } catch(Exception ex) {
            throw new Exception("Error en el metodo :: obtenerDispensacionesLazzy  " + ex.getMessage());
        }
    }
    
    @Override
    public List<DispensacionMaterialExtended> obtenerDispensacionesReporte(ParamBusquedaReporte params) throws Exception {
        try {
            return dispensacionMaterialMapper.obtenerDispensacionesReporte(params);
        } catch(Exception ex) {
            throw new Exception("Error en el metodo :: obtenerReporteDispensacionMaterialesCuración  " + ex.getMessage());
        }
    }
    
    @Override
    public Long obtenerTotalDispensacionesLazzy(String idEstructura, String idUsuario, String cadenaBusqueda) throws Exception {
        try {
            return dispensacionMaterialMapper.obtenerTotalDispensacionesLazzy(idEstructura, idUsuario, cadenaBusqueda);
        } catch (Exception ex) {
            throw new Exception("Error en el metodo :: obtenerTotalDispensacionesLazzy    " + ex.getMessage());
        }
    }
    
    @Override
    public Folios obtenerFolioDispensacion() throws Exception {
        Folios folio = null;
        try {
            int tipoDoc = TipoDocumento_Enum.DISPENSACION_MATERIAL.getValue();
            folio = foliosMapper.obtenerPrefixPorDocument(tipoDoc);
        } catch (Exception ex) {
            throw new Exception("Error al obtener folio de Dispensación " + ex.getMessage());
        }
        return folio;
    }
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean registrarDispensacionMaterial(Folios folio, DispensacionMaterial dispensacionMaterial, List<DispensacionMaterialInsumo> listaDispensacionInsumo,
            List<Inventario> inventarioList, List<MovimientoInventario> movimientoInventarioList) throws Exception {
        boolean res = false;
        try {
            res = foliosMapper.actualizaFolios(folio);
            if (!res) {
                throw new Exception("Error al registrar Folio de Dispensación. ");
            }
             else {
                res = dispensacionMaterialMapper.insertar(dispensacionMaterial);
                if (!res) {
                    throw new Exception("Error al registrar Dispensación. ");
                } else {
                    res = dispensacionMaterialInsumoMapper.insertarDispensacionInsumoList(listaDispensacionInsumo);
                    if (!res) {
                        throw new Exception("Error al registrar los Insumos de la Dispensación. ");
                    }
                    
                    else {
                        res = inventarioMapper.actualizarInventarioSurtidos(inventarioList);
                        if (!res) {
                            throw new Exception("Error al actualizar Inventarios por Dispensación de Material de Curación. ");
                        } else {
                            res = movimientoInventarioMapper.insertarMovimientosInventarios(movimientoInventarioList);
                            if (!res) {
                                throw new Exception("Error al registrar movimientos de Inventarios por Dispensación de Material de Curación. ");
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            throw new Exception("Error registrar Dispensación. " + ex.getMessage());
        }
        return res;
    }    
}
