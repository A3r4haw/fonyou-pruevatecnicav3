/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.Date;
import java.util.List;
import mx.mc.model.DispensacionMaterialInsumoExtended;

/**
 *
 * @author apalacios
 */
public interface DispensacionMaterialInsumoService {
    public List<DispensacionMaterialInsumoExtended> obtenerInsumosDispensacion(String cadenaBusqueda, String idEstructura, Integer tipo) throws Exception;
    public DispensacionMaterialInsumoExtended obtenerInsumoPorQR(String claveInstitucional, String lote, Date fechaCaducidad, String idEstructura, Integer tipo) throws Exception;
    public List<DispensacionMaterialInsumoExtended> obtenerInsumosPorIdDispensacion(String idDispensacion) throws Exception;
}
