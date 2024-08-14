/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.model.InventarioExtended;
import mx.mc.model.InventarioSalida;

/**
 *
 * @author bbautista
 */
public interface InventarioSalidaService extends GenericCrudService<InventarioSalida, String>{
    public InventarioSalida obtenerDetalleInsumo(String idInsumo,String lote,String idEstructura,Integer tipoAlmacen) throws Exception;
    public InventarioSalida obtenerDetalleInsumo(String idInventario,Integer tipoAlmacen) throws Exception;    
    public boolean saveAjuste(List<InventarioExtended> listDescont, List<InventarioSalida> listSalida) throws Exception;
}
