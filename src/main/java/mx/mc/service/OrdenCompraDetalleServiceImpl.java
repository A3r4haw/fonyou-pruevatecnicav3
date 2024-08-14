/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.OrdenCompraDetalleMapper;
import mx.mc.model.OrdenCompraDetalle;
import mx.mc.model.OrdenCompraDetalleExtended;
import mx.mc.model.OrdenCompra_Extended;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author gcruz
 */

@Service
public class OrdenCompraDetalleServiceImpl extends GenericCrudServiceImpl<OrdenCompraDetalle, String> implements OrdenCompraDetalleService {
    
    @Autowired
    private OrdenCompraDetalleMapper ordenCompraDetalleMapper;
    
    @Autowired
    public OrdenCompraDetalleServiceImpl(GenericCrudMapper<OrdenCompraDetalle, String> genericCrudMapper) {
        super(genericCrudMapper);
    }    

    @Override
    public List<OrdenCompraDetalleExtended> obtenerListaDetalleOrden(String idOrdenCompra) throws Exception {
        try {
            return ordenCompraDetalleMapper.obtenerListaDetalleOrden(idOrdenCompra);
        } catch(Exception ex) {
            throw new Exception("Error al obtener el detalle de la orden de compra " + ex.getMessage());
        }
    }

}
