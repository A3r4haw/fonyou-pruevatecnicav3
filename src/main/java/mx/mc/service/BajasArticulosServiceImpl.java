/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.mapper.BajasArticulosMapper;
import mx.mc.model.BajasArticulos;
import mx.mc.model.ParamBusquedaReporte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author olozada
 */


@Service
public class BajasArticulosServiceImpl implements BajasArticulosService {

    @Autowired
    private BajasArticulosMapper bajasArticulosMapper;
  
    public BajasArticulosServiceImpl(){
        //No code needed in constructor
    }
  
  @Override
    public List<BajasArticulos> consultarBajasArticulos(ParamBusquedaReporte paramBusquedaReporte, int startingAt,
            int maxPerPage) throws Exception {
        try {
            return bajasArticulosMapper.consultarBajasArticulos(paramBusquedaReporte, startingAt, maxPerPage);
        } catch (Exception ex) {
            throw new Exception("Error al obtener consulta de reporte Bajas Articulos" + ex.getMessage());
        }
    }

    @Override
    public Long obtenerTotalRegistros(ParamBusquedaReporte paramBusquedaReporte) throws Exception {
        try {
            return bajasArticulosMapper.obtenerTotalRegistros(paramBusquedaReporte);
        } catch (Exception ex) {
            throw new Exception("Error al obtener total de registros de reporte Baja Articulos" + ex.getMessage());
        }
    } 
    }
    

