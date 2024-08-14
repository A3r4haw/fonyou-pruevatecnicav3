package mx.mc.service;

import java.util.Date;
import java.util.List;
import mx.mc.model.ClaveProveedorBarras;
import mx.mc.model.DevolucionDetalleExtended;
import mx.mc.model.DevolucionMinistracion;
import mx.mc.model.Inventario;
import mx.mc.model.InventarioExtended;
import mx.mc.model.InventarioSalida;
import mx.mc.model.MovimientoInventario;
import mx.mc.model.Reabasto;
import mx.mc.model.ReabastoEnviado;
import mx.mc.model.ReabastoEnviadoExtended;
import mx.mc.model.ReabastoInsumo;
import mx.mc.model.Surtimiento;
import mx.mc.model.SurtimientoEnviado;
import mx.mc.model.TipoBloqueo;
import mx.mc.model.Usuario;

/**
 *
 * @author bbautista
 */
public interface InventarioService extends GenericCrudService<Inventario, String> {

    public int actualizarInventario(List<ReabastoEnviadoExtended> listaDetalle) throws Exception;

    public Inventario obtenerInventariosPorInsumoEstructuraYLote(
            String idInsumo, String idEstructura, String lote) throws Exception;

    public Inventario obtenerIdiventarioPorInsumo(String idInsumo) throws Exception;

    public Inventario obtenerInventarioPorClveInstEstructuraYLote(Inventario inventario) throws Exception;

    public String obtenerIdiventarioPorInsumoEstructuraYLote(String idInsumo,
            String idEstructura, String lote) throws Exception;

    public Inventario obtenerIdiventarioPorInsumoEstructura(String idInsumo,
            String idEstructura) throws Exception;

    public Inventario obtenerExistencia(String idInsumo, String idEstructura) throws Exception;

    public boolean insertListInventarios(List<Inventario> listaInventarios) throws Exception;

    public boolean actualizarListInventarios(List<Inventario> listaInventarios) throws Exception;

    public List<InventarioExtended> obtenerListaInactivosByIdInsumos(List<String> listIdInsumos) throws Exception;

    public DevolucionDetalleExtended getInventarioForDevolucion(ReabastoEnviadoExtended reabastoEnviado) throws Exception;

    boolean revertirInventario(Surtimiento surtimientoSelected,
            List<SurtimientoEnviado> surtimientoEnviadoList,
            Usuario currentUser) throws Exception;

    public Inventario obtenerInventariosPorInsumoEstructuraLoteYCantidadXCaja(
            String idInsumo, String idEstructura, String lote, Integer cantidadXCaja, String claveProveedor) throws Exception;

    public Inventario obtenerInventariosPorInsumoEstructuraLoteYCantidadXCajaYFechaCad(
            String idInsumo, String idEstructura, String lote,
            Integer cantidadXCaja, String claveProveedor, Date fechaCaducidad) throws Exception;

    public List<Inventario> obtenerInventariosPorInsumoEstructuraCantidadXCajaYProveedor(
            String idInsumo, String idEstructura, Integer cantidadXCaja, String claveProveedor, String lote) throws Exception;

    public boolean insertarManual(Inventario inventarioSelect) throws Exception;

    public boolean recivirMedicamentosDevolucion(
            List<Inventario> listaInventario,
            List<InventarioSalida> listaInvSalida,
            Surtimiento surtimiento,
            List<MovimientoInventario> listaMovimientosInventario,
            DevolucionMinistracion devMinistracion) throws Exception;

    public boolean actualizarInventarioGlobal(
            Inventario inventario, MovimientoInventario movInventario) throws Exception;

    public boolean actualizarEntradaInventario(
            List<InventarioExtended> listInventarioActualizar,
            List<MovimientoInventario> listaMovimientos) throws Exception;

    public boolean insertaNuevoInventario(Inventario inventarioSelect, MovimientoInventario movimientoInventario) throws Exception;

    public Inventario obtenerInventariosPorInsumoLoteFechaCadIdInsumoClProv(String lote, String idInsumo, String idEstructura, Date fechaCaducidad) throws Exception;

    public boolean actualizarInventarioPresentCom(String idInventario, int presentacionComercial, int cantidadXCaja) throws Exception;

    public Inventario validarExistInventGlob(
            String idInsumo, String idEstructura, String lote, Integer cantidadXCaja, String claveProveedor, int presentacionComercial) throws Exception;

    public List<Inventario> obtenerLotesPorMedicamento(String idInsumo) throws Exception;

    public List<Inventario> obtenerExistenciasPorIdEstructuraIdInsumo(String idEstructura, String idInsumo) throws Exception;

    public List<Inventario> obtenerLotesInventario(String cadenaBusqueda) throws Exception;

    public List<InventarioExtended> obtenerListaporLotes(String claveInstitucional) throws Exception;

    public List<Inventario> listaLotesPorClaveInst(String claveInstitucional, String estructura) throws Exception;

    public List<Inventario> listaFechasCaducidad1(String claveInstitucional, String lote, String estructura) throws Exception;

    public boolean desactivarInsumoInventario(Inventario invt, TipoBloqueo tipoBloqueo) throws Exception;
    
    public Inventario obtenerIdInsumoporClaveyLote(String claveInstitucional) throws Exception;

    public Inventario obtenerIdInsumoByClave(String claveInstitucional, String lote, Date fecha, String estructura) throws Exception;

    public boolean actualizarInventarioGlobalLote(Inventario inventario, MovimientoInventario movimientoInventario) throws Exception;

    public boolean actualizarInventarioGlobalCaducidad(Inventario inventario, List<MovimientoInventario> movimientoInventario) throws Exception;

    public InventarioExtended getInventarioTracking(String claveInstitucional, String lote, String idEstructura, Date fechaCaducidad, int presentacionComercial, boolean banderaInventario) throws Exception;

    public Inventario getInventarioByStock(String lote, String claveInstitucional, String claveEstructura, Date fechaCaducidad, int cantidadXCaja) throws Exception;

    public boolean actualizarDatosForStocks(Inventario inventario, MovimientoInventario movimientoInventario, Usuario usuario,
            Reabasto reabasto, ReabastoInsumo reabastoInsumo, ReabastoEnviado reabastoEnviado, boolean existeUsuario) throws Exception;

    public boolean actualizarDatosForStocksList(//String idEstructura,
            List<Inventario> inventarioList, List<Inventario> idInventariosConCeros, List<MovimientoInventario> movimientoInventarioList, boolean actualizaInventarioExistente) throws Exception;

    public boolean actualizarInventarioCantidadActual(Inventario inventario, Inventario inventarioInactivar, MovimientoInventario movimientoInventario) throws Exception;

    public boolean actualizarInventarioBloqueos(Inventario invt, TipoBloqueo tipoBloqueo, MovimientoInventario movimientoInventario) throws Exception;

    public List<Inventario> getListByIdInventario(List<String> idInventarioList) throws Exception;

    public List<Inventario> listaMedicamentoEstructura(String claveInstitucional) throws Exception;

    public InventarioExtended getInsumoByFolioClaveInvRefill(String claveInstitucional, String lote, Date fechaCaducidad, String codigoArea) throws Exception;

    public List<ClaveProveedorBarras> obtienelistaCodigoBarras(String claveInstitucional) throws Exception;
    
    public List<ClaveProveedorBarras> obtienelistaProveedor(String claveInstitucional) throws Exception;

    public List<ClaveProveedorBarras> obtienelistaCodigoBarrasByclaveInstAndClaveProv(String claveInstitucional , String claveProveedor ) throws Exception;
    
    public boolean updateClavProveedor(Inventario actualizar) throws Exception;
    
    public List<InventarioExtended> obtenerInventarioPorIdEstructurayporIdInsumo(String idEstructura, String idInsumo) throws Exception;
    
    public Inventario obtenerLoteSugerido(List<String> idEstructuraList , String idInsumo) throws Exception ;
    
    public List<InventarioExtended> obtenerInventarioPorIdEstructurasIdInsumo(List<String> idEstructuraList, String idInsumo) throws Exception;
    
}
