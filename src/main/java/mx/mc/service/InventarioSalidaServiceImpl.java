/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.init.Constantes;
import mx.mc.model.InventarioSalida;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.InventarioMapper;
import mx.mc.mapper.InventarioSalidaMapper;
import mx.mc.model.InventarioExtended;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author bbautista
 */
@Service
public class InventarioSalidaServiceImpl extends GenericCrudServiceImpl<InventarioSalida, String> implements InventarioSalidaService {
    
    @Autowired
    private InventarioSalidaMapper inventarioSalidaMapper;

    @Autowired
    private InventarioMapper invMapper;

    @Autowired
    public InventarioSalidaServiceImpl(GenericCrudMapper<InventarioSalida, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public InventarioSalida obtenerDetalleInsumo(String idInsumo, String lote, String idEstructura, Integer tipoAlmacen) throws Exception {
        try {
            return inventarioSalidaMapper.obtenerDetalleInsumoSalida(idInsumo, lote, idEstructura, tipoAlmacen);
        } catch (Exception ex) {
            throw new Exception("Error al obtenerDetalleInsumo. " + ex.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    // todo no debe haber objeto de Rebasto EnviadoExted, debe ser un objeto de inventario
    public boolean saveAjuste(List<InventarioExtended> listDescont, List<InventarioSalida> listaAjusteInventario)
            throws Exception {
        boolean resp = Constantes.INACTIVO;
        try {

            if (!listDescont.isEmpty()) {
                if (invMapper.actualizarAjusteInvent(listDescont) > 0) {
                    resp = Constantes.ACTIVO;
                } else {
                    throw new Exception("Ocurrio un error al actualizar el Inventario");
                }
            }
            if (!listaAjusteInventario.isEmpty()) {
                resp = inventarioSalidaMapper.insertarLista(listaAjusteInventario);
                if (!resp) {
                    throw new Exception("Ocurrio un error al insertar en Inventario Salida");
                }
            }
            //Anexar el insertMovimientoInventario
        } catch (Exception ex) {
            throw new Exception("Error al saveAjuste. " + ex.getMessage());
        }
        return resp;
    }
    @Override
    public InventarioSalida obtenerDetalleInsumo(String idInventario, Integer tipoAlmacen) throws Exception {
        try {
            return inventarioSalidaMapper.obtenerDetalleInsumoSalida2(idInventario,tipoAlmacen);
        } catch (Exception ex) {
            throw new Exception("Error al obtenerDetalleInsumo. " + ex.getMessage());
        }
    }
}
