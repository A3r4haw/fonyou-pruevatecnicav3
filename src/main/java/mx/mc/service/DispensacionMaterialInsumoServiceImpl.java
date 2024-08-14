/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.Date;
import java.util.List;
import mx.mc.mapper.DispensacionMaterialInsumoMapper;
import mx.mc.model.DispensacionMaterialInsumoExtended;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author apalacios
 */
@Service
public class DispensacionMaterialInsumoServiceImpl implements DispensacionMaterialInsumoService {
    
    @Autowired
    private DispensacionMaterialInsumoMapper dispensacionMaterialInsumoMapper;

    @Override
    public List<DispensacionMaterialInsumoExtended> obtenerInsumosDispensacion(String cadenaBusqueda, String idEstructura, Integer tipo) throws Exception {
        try {
            return dispensacionMaterialInsumoMapper.obtenerInsumosDispensacion(cadenaBusqueda, idEstructura, tipo);
        } catch (Exception e) {
            throw new Exception("Error al obtener Insumos para Dispensacion " + e.getMessage());
        }
    }
    
    @Override
    public DispensacionMaterialInsumoExtended obtenerInsumoPorQR(String claveInstitucional, String lote, Date fechaCaducidad, String idEstructura, Integer tipo) throws Exception {
        try {
            return dispensacionMaterialInsumoMapper.obtenerInsumoPorQR(claveInstitucional, lote, fechaCaducidad, idEstructura, tipo);
        } catch (Exception e) {
            throw new Exception("Error al obtener Insumo por QR " + e.getMessage());
        }
    }
    
    @Override
    public List<DispensacionMaterialInsumoExtended> obtenerInsumosPorIdDispensacion(String idDispensacion) throws Exception {
        try {
            return dispensacionMaterialInsumoMapper.obtenerInsumosPorIdDispensacion(idDispensacion);
        } catch (Exception e) {
            throw new Exception("Error al obtener Insumos por idDispensacion " + e.getMessage());
        }
    }
}
