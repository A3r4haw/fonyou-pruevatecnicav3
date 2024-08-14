/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.List;
import mx.mc.model.OrdenCompraDetalle;
import mx.mc.model.OrdenCompraDetalleExtended;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author gcruz
 */
public interface OrdenCompraDetalleMapper extends GenericCrudMapper<OrdenCompraDetalle, String> {
    
    public boolean insertarListOrdenDetalleCompra(@Param("listOrdenDetalle") List<OrdenCompraDetalleExtended> listOrdenDetalle) throws Exception;
    
    public List<OrdenCompraDetalleExtended> obtenerListaDetalleOrden( @Param("idOrdenCompra") String idOrdenCompra)throws Exception;
    
}
