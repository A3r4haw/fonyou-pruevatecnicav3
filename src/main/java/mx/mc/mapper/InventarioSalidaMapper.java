/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.List;
import mx.mc.model.InventarioSalida;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author bbautista
 */
public interface InventarioSalidaMapper extends GenericCrudMapper<InventarioSalida,String> {
    InventarioSalida obtenerDetalleInsumoSalida(
            @Param("clave") String idInsumo, 
            @Param("idLote") String lote, 
            @Param("idEstructura") String idEstructura,
            @Param("tipoAlmacen") Integer tipoAlmacen);
    
    boolean insertarLista(@Param("listaAjusteInventario") List<InventarioSalida> listaAjusteInventario);
    InventarioSalida obtenerDetalleInsumoSalida2(
            @Param("idInventario") String idInventario,
            @Param("tipoAlmacen") Integer tipoAlmacen);
}
