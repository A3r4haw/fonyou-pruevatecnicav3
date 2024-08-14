package mx.mc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import mx.mc.init.Constantes;
import mx.mc.mapper.AlmacenControlMapper;
import mx.mc.mapper.FolioAlternativoFolioMusMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.InventarioMapper;
import mx.mc.mapper.MovimientoInventarioMapper;
import mx.mc.mapper.ReabastoEnviadoMapper;
import mx.mc.mapper.ReabastoInsumoMapper;
import mx.mc.mapper.ReabastoMapper;
import mx.mc.model.AlmacenControl;
import mx.mc.model.FolioAlternativoFolioMus;
import mx.mc.model.Inventario;
import mx.mc.model.MovimientoInventario;
import mx.mc.model.Reabasto;
import mx.mc.model.ReabastoEnviado;
import mx.mc.model.ReabastoInsumo;
import mx.mc.model.ReabastoInsumoExtended;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author bbautista
 */
@Service
public class ReabastoInsumoServiceImpl extends GenericCrudServiceImpl<ReabastoInsumo, String> implements ReabastoInsumoService {

    @Autowired
    private ReabastoInsumoMapper reabastoInsumoMapper;

    @Autowired
    private ReabastoMapper reabastoMapper;

    @Autowired
    private ReabastoEnviadoMapper reabastoEnviadoMapper;

    @Autowired
    private InventarioMapper inventarioMapper;

    @Autowired
    private MovimientoInventarioMapper movimientoInventarioMapper;
    
    @Autowired
    private FolioAlternativoFolioMusMapper folioAlternativoFolioMusMapper;
    
    @Autowired
    private AlmacenControlMapper almacenControlMapper;

    @Autowired
    public ReabastoInsumoServiceImpl(GenericCrudMapper<ReabastoInsumo, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public List<ReabastoInsumoExtended> obtenerReabastoInsumoExtends(String idReabasto, List<Integer> listEstatusReabasto) throws Exception {
        try {
            return reabastoInsumoMapper.
                    obtenerReabastoInsumoExtends(idReabasto, listEstatusReabasto);
        } catch (Exception e) {
            throw new Exception("Error obtenerReabastoExtends. " + e.getMessage());
        }
    }

    @Override
    public List<ReabastoInsumoExtended> obtenerReabastoInsumoExtendsSurt(
            String idReabasto, String idEstructura, List<Integer> listEstatusReabasto) throws Exception {
        try {
            return reabastoInsumoMapper.
                    obtenerReabastoInsumoExtendsSurt(idReabasto, idEstructura, listEstatusReabasto);
        } catch (Exception e) {
            throw new Exception("Error obtenerReabastoExtends. " + e.getMessage());
        }
    }

    @Override
    public List<ReabastoInsumoExtended> obtenerReabastoInsumoProveedorFarmacia(
            String idReabasto, String idEstructura, List<Integer> listEstatusReabasto) throws Exception {
        try {
            return reabastoInsumoMapper.
                    obtenerReabastoInsumoProveedorFarmacia(idReabasto, listEstatusReabasto);
        } catch (Exception e) {
            throw new Exception("Error obtenerReabastoExtends. " + e.getMessage());
        }
    }
    
    @Override
    public List<ReabastoInsumoExtended> obtenerValorReabastoInsumoProveedorFarmacia(String idReabasto, String idEstructura, 
                            List<Integer> listEstatusReabasto, String cadena, boolean activaAutoCompleteInsumos) throws Exception {
        try {
            return reabastoInsumoMapper.
                    obtenerValorReabastoInsumoProveedorFarmacia(idReabasto, listEstatusReabasto,cadena, activaAutoCompleteInsumos);
        } catch (Exception e) {
            throw new Exception("Error obtenerReabastoExtends. " + e.getMessage());
        }
    }
    
    @Override
    public int obtenerCantidadMedicamento(String idMedicamento) throws Exception {
        try {
            return reabastoInsumoMapper.obtenerCantidadMedicamento(idMedicamento);
        } catch (Exception e) {
            throw new Exception("Error obtenerCantidadMedicamento. " + e.getMessage());
        }
    }

    @Override
    public boolean actualizarListaReabastoInsumo(List<ReabastoInsumoExtended> listaReabastoInsumo) throws Exception {
        try {
            return reabastoInsumoMapper.actualizarListaReabastoInsumo(listaReabastoInsumo);
        } catch (Exception ex) {
            throw new Exception("Error al actualizar la lista de insumos  " + ex.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean actulizaRecibirOrdenReabasto(Reabasto unReabasto, List<ReabastoInsumoExtended> listInsumoRecibir,
            List<ReabastoEnviado> listReabastoEnviado) throws Exception {
        boolean res = false;
        try {
            res = reabastoEnviadoMapper.actualizarListaReabastoEnviado(listReabastoEnviado);
            if (!res) {
                throw new Exception("Ocurrio un error al actualizar lista de reabastoEnviado");
            } else {
                res = reabastoInsumoMapper.actualizarListaReabastoInsumo(listInsumoRecibir);
                if (!res) {
                    throw new Exception("Ocurrio un error al actualizar lista de insumoRecibir");
                } else {
                    res = reabastoMapper.actualizar(unReabasto);
                }
            }
        } catch (Exception ex) {
            throw new Exception("Ocurrio un error al momento de recibir la orden Reabasto " + ex.getMessage());
        }
        return res;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean actulizaIngresoOrdenReabasto(Reabasto unReabasto, List<ReabastoInsumoExtended> listInsumoRecibir,
            List<ReabastoEnviado> listReabastoEnviado, List<Inventario> listInventarioInsert,
            List<Inventario> listInventarioUpdate, List<MovimientoInventario> listMovInventario) throws Exception {
        boolean res = false;
        try {
            res = reabastoEnviadoMapper.actualizarListaReabastoEnviado(listReabastoEnviado);
            if (!res) {
                throw new Exception("Ocurrio un error al actualizar lista de reabastoEnviado");
            } else {
                res = reabastoInsumoMapper.actualizarListaReabastoInsumo(listInsumoRecibir);
                if (!res) {
                    throw new Exception("Ocurrio un error al actualizar lista de insumoRecibir");
                } else {
                    res = reabastoMapper.actualizar(unReabasto);
                    if (!res) {
                        throw new Exception("Ocurrio un error al insertar actualizar reabasto");
                    } else {
                        if (!listInventarioInsert.isEmpty()) {
                            res = inventarioMapper.insertListInventarios(listInventarioInsert);
                            if (!res) {
                                throw new Exception("Ocurrio un error al insertar lista de inventalistInventarioInsertrios");
                            }
                        }
                        if (!listInventarioUpdate.isEmpty()) {
                            res = inventarioMapper.actualizarListInventarios(listInventarioUpdate);
                            if (!res) {
                                throw new Exception("Ocurrio un error al actualizar lista de inventarios");
                            }
                        }
                        if (!listMovInventario.isEmpty()) {
                            res = movimientoInventarioMapper.insertarMovimientosInventarios(listMovInventario);
                            if (!res) {
                                throw new Exception("Ocurrio un error al insertar lista de movimientos");
                            }
                        }

                    }
                }
            }
        } catch (Exception ex) {
            throw new Exception("Ocurrio un error al momento de recibir la orden Reabasto " + ex.getMessage());
        }
        return res;
    }

    @Override
    public List<ReabastoInsumo> obtenerListaNormal(String idEStructura, String idEstructuraPadre, Integer tipoAlmacen, Integer tipoInsumo) throws Exception {
        try {
            return reabastoInsumoMapper.obtenerListaNormal(idEStructura, idEstructuraPadre, tipoAlmacen, tipoInsumo);
        } catch (Exception ex) {
            throw new Exception("Error al obtener los Insumos: " + ex);
        }
    }

    @Override
    public ReabastoInsumo obtenerMaxMinReorCantActual(String idEStructura, String idMedicamento) throws Exception {
        try {
            return reabastoInsumoMapper.obtenerMaxMinReorCantActual(idEStructura, idMedicamento);
        } catch (Exception ex) {
            throw new Exception("Error al obtener los Insumos: " + ex);
        }
    }    
    
    @Override
    public List<ReabastoInsumoExtended> obtenerListaMaxMinReorCantActual(String idEStructura, String cadena) throws Exception {
        try {
            return reabastoInsumoMapper.obtenerListaMaxMinReorCantActual(idEStructura, cadena);
        } catch (Exception e) {
            throw new Exception("Error al obtener la lista de Insumos " + e.getMessage());
        }
    }
    
    @Override
    public boolean guardarReabastoInsumo(List<ReabastoInsumo> rInsumo) throws Exception {
        boolean res = false;
        try {
            res = reabastoInsumoMapper.guardarReabastoInsumo(rInsumo);
        } catch (Exception ex) {
            throw new Exception("Error registrar ReabastoInsumo masivo " + ex.getMessage());
        }
        return res;
    }

    @Override
    public boolean actualizarReabastoInsumo(List<ReabastoInsumo> rInsumo) throws Exception {
        boolean res = false;
        try {
            res = reabastoInsumoMapper.actualizarReabastoInsumo(rInsumo);
        } catch (Exception ex) {
            throw new Exception("Error Actualizar ReabastoInsumo masivo " + ex.getMessage());
        }
        return res;
    }

    @Override
    public List<ReabastoInsumo> obtenerReabastosInsumos(String idReabasto, Integer tipoAlmacen) throws Exception {
        try {
            return reabastoInsumoMapper.
                    obtenerReabastosInsumos(idReabasto, tipoAlmacen);
        } catch (Exception e) {
            throw new Exception("Error obtenerReabastoExtends. " + e.getMessage());
        }
    }

    @Override
    public boolean eliminarPorIdReabasto(String idReabasto) throws Exception {
        try {
            return reabastoInsumoMapper.eliminarPorIdReabasto(idReabasto);
        } catch (Exception ex) {
            throw new Exception("Error al Eliminar por idReabasto. " + ex.getMessage());
        }
    }

    @Override
    public List<ReabastoInsumoExtended> obtenerDetalle(String idReabasto) throws Exception {
        try {
            return reabastoInsumoMapper.detalleTransfer(idReabasto);

        } catch (Exception ex) {
            throw new Exception("Error al obtener la Busqueda. " + ex.getMessage());
        }
    }

    @Override
    public List<ReabastoInsumoExtended> obtenerListaNormalTransfer(String idOrigen, String idDestino, Integer tipoAlmacen, Integer tipoInsumo) throws Exception {
        try {
            return reabastoInsumoMapper.obtenerListaNormalTransfer(idOrigen, idDestino, tipoAlmacen, tipoInsumo);
        } catch (Exception ex) {
            throw new Exception("Error al obtener los Insumos: " + ex);
        }
    }

    @Override
    public ReabastoInsumoExtended getReabastoInsumoByFolioClave(String folio, String claveInstitucional, String lote, Date fechaCaducidad, String codigoArea) throws Exception {
        try {
            return reabastoInsumoMapper.getReabastoInsumoByFolioClave(folio, claveInstitucional, lote, fechaCaducidad, codigoArea);
        } catch (Exception e) {
            throw new Exception("Error al obtener el ReabastoInsumo" + e.getMessage());
        }
    }
    
    @Override
    public ReabastoInsumoExtended getReabastoInsumoByFolioClaveInv(String claveInstitucional, String lote, Date fechaCaducidad, String codigoArea) throws Exception {
        try {
            return reabastoInsumoMapper.getReabastoInsumoByFolioClaveInv(claveInstitucional, lote, fechaCaducidad, codigoArea);
        } catch (Exception e) {
            throw new Exception("Error al obtener el ReabastoInsumo" + e.getMessage());
        }
    }
    
    @Override
    public List<ReabastoInsumoExtended> getListReabastoInsumoByFolioClaveInv(String claveInstitucional, String lote, Date fechaCaducidad, String codigoArea) throws Exception {
        try {
            return reabastoInsumoMapper.getListReabastoInsumoByFolioClaveInv(claveInstitucional, lote, fechaCaducidad, codigoArea);
        } catch (Exception e) {
            throw new Exception("Error al obtener el ReabastoInsumo" + e.getMessage());
        }
    }

    @Override
    public List<ReabastoInsumoExtended> obtenerDetalleReabasto(String idReabasto, String folioAlternativo) throws Exception {
        try {
            return reabastoInsumoMapper.obtenerDetalleReabasto(idReabasto, folioAlternativo);

        } catch (Exception ex) {
            throw new Exception("Error al obtener la obtenerDetalleReabasto. " + ex.getMessage());
        }
    }

    @Override
    public ReabastoInsumo obtenerReabastoInsumo(String idReabasto, String idInsumo) throws Exception {
        try {
            return reabastoInsumoMapper.obtenerReabastoInsumo(idReabasto, idInsumo);
        } catch (Exception ex) {
            throw new Exception("Error al obtener reabastoInsumo.   " + ex.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean actulizaOrdenReabastoWS(Reabasto unReabasto, List<ReabastoInsumoExtended> listInsumoRecibir,
            List<ReabastoEnviado> listReabastoEnviado, List<ReabastoInsumo> listReabastoInsumo, FolioAlternativoFolioMus folioAlterMus) throws Exception {
        boolean res = false;
        try {
            res = reabastoEnviadoMapper.insertListReabatoEnviado(listReabastoEnviado);
            if (!res) {
                throw new Exception("Ocurrio un error al insertar la lista de reabasto enviado.  ");
            } else {
                res = reabastoInsumoMapper.actualizarListaReabastoInsumo(listInsumoRecibir);
                if (!res) {
                    throw new Exception("Ocurrio un error al actualizar los reabastos insumos.  ");
                } else {
                    res = reabastoMapper.actualizar(unReabasto);
                    if (!res) {
                        throw new Exception("Ocurrio un error al actualizar el reabasto.  ");
                    } else {
                        res = folioAlternativoFolioMusMapper.actualizar(folioAlterMus);
                        if(!res) {
                            throw new Exception("Ocurrio un error al momento de actualizar el folioAlternativoMus.   ");
                        } else {
                            List<AlmacenControl> listaAlmacenControl = new ArrayList<>();
                            for(ReabastoInsumoExtended item : listInsumoRecibir ){
                                AlmacenControl ctrl = almacenControlMapper.obtenerIdPuntosControl(unReabasto.getIdEstructura(), item.getIdInsumo());
                                ctrl.setSolicitud(Constantes.ESTATUS_INACTIVO);
                                listaAlmacenControl.add(ctrl);
                            }
                            res = almacenControlMapper.actualizaListaAlmacenCtrl(listaAlmacenControl);
                            if(!res) {
                                throw new Exception("Ocurrio un error al momento de actualizar los puntos de control.   ");
                            } else {
                                 if (!listReabastoInsumo.isEmpty()) {
                                    res = reabastoInsumoMapper.insertarListReabastoInsumos(listReabastoInsumo);
                                    if(!res) {
                                        throw new Exception("Ocurrio un error al momento de insertar la lista de insumos.   ");                                                      
                                    }                                
                                } 
                            }
                        }                                              
                    }
                }
            }
        } catch (Exception ex) {
            throw new Exception("Ocurrio un error en metodo de actulizaOrdenReabastoWS.  ");
        }
        return res;
    }

    @Override
    public List<ReabastoInsumo> obtenerInsumosdeReabastoporIdReabasto(String idReabasto, String idEStructura, Integer tipoAlmacen) throws Exception {
        try {
            return reabastoInsumoMapper.obtenerInsumosdeReabastoporIdReabasto(idReabasto,idEStructura, tipoAlmacen);
        } catch (Exception e) {
            throw new Exception("Error obtenerReabastoExtends. " + e.getMessage());
        }
    }   

    @Override
    public List<ReabastoInsumo> obtenerInsumosEnviadosReabastoByEstructura(String idEstructura,Date fechaSolicitud) throws Exception {
        try{
            return reabastoInsumoMapper.obtenerInsumosEnviadosReabastoByEstructura(idEstructura,fechaSolicitud);
        }catch(Exception ex){
            throw  new Exception("Ocurrio un error al obtener insumos enviados.",ex);
        }
    }
    
    @Override
    public List<ReabastoInsumo> obtenerInsumosEnviadosPrescripByEstructura(String idEstructura,Date fechaSolicitud) throws Exception {
        try{
            return reabastoInsumoMapper.obtenerInsumosEnviadosPrescripByEstructura(idEstructura,fechaSolicitud);
        }catch(Exception ex){
            throw  new Exception("Ocurrio un error al obtener insumos enviados.",ex);
        }
    }
    
    @Override
    public List<ReabastoInsumo> obtenerListaGlobalCEDIME()throws Exception{
        try{
            return reabastoInsumoMapper.obtenerListaGlobalCEDIME();
        }catch(Exception ex){
            throw  new Exception("Ocurrio un error al obtener lista global CEDIME.",ex);
        }
    }
    
    @Override
    public List<ReabastoInsumo> obtenerListaInsumoByEstructura(String idEstructura,String idInsumo,String solicitud) throws Exception{
        try{
            return reabastoInsumoMapper.obtenerListaInsumoByEstructura(idEstructura,idInsumo,solicitud);
        }catch(Exception ex){
            throw  new Exception("Ocurrio un error al obtener insumos enviados.",ex);
        }
    }
    
    @Override
    public List<ReabastoInsumoExtended> detalleInsumoByFolioAlternativo(String folio)throws Exception{
        try {
            return  reabastoInsumoMapper.detalleInsumoByFolioAlternativo(folio);
        } catch (Exception ex) {
            throw  new Exception("Ocurrio un error al obtener detalle de insumos.",ex);
        }
    }
    
    @Override
    public ReabastoInsumo obtenerReabastoInsumoForSIAM(String idReabasto,String idInsumo,String idAlmancen)throws Exception{
        try {
            return reabastoInsumoMapper.obtenerReabastoInsumoForSIAM(idReabasto, idInsumo, idAlmancen);
        } catch (Exception ex) {
            throw  new Exception("Ocurrio un error al obtener detalle de insumos para SIAM.",ex);
        }
    }
    
    @Override
    public List<ReabastoInsumoExtended> obtenerReabastoInsumoPorIdEstructuraYporIdInsumo(String idEstructura, String idInsumo) throws Exception{
        try {
            return reabastoInsumoMapper.obtenerReabastoInsumoPorIdEstructuraYporIdInsumo(idEstructura, idInsumo);
        } catch (Exception ex) {
            throw  new Exception("Ocurrio un error al obtener Reabasto Insumos por IdEstructura y por IdAlmacen.",ex);
        }
    }
    
}
