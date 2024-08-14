package mx.mc.mapper;

import java.util.Date;
import java.util.List;
import mx.mc.model.ClaveProveedorBarras;
import mx.mc.model.ReabastoEnviadoExtended;
import mx.mc.model.DevolucionDetalleExtended;
import mx.mc.model.Inventario;
import mx.mc.model.InventarioExtended;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author bbautista
 */
public interface InventarioMapper extends GenericCrudMapper<Inventario, String> {

    public int actualizarInventario(
            @Param("listaDetalle") List<ReabastoEnviadoExtended> listaDetalle);

    public int actualizarAjusteInvent(
            @Param("listaDetalle") List<InventarioExtended> listaDetalle);

    public boolean actualizarInventarioSurtidos(
            @Param("listaDetalle") List<Inventario> listaDetalle);

    public boolean actualizarInventarioRecetaManual(
            @Param("listaDetalle") List<InventarioExtended> listaDetalle);

    Inventario obtenerExistencia(@Param("idInsumo") String idInsumo, @Param("idEstructura") String idEstructura);

    public Inventario obtenerInventariosPorInsumoEstructuraYLote(
            @Param("idInsumo") String idInsumo,
            @Param("idEstructura") String idEstructura,
            @Param("lote") String Lote);

    public Inventario obtenerInventarioPorClveInstEstructuraYLote(Inventario inventario);

    public String obtenerIdiventarioPorInsumoEstructuraYLote(
            @Param("idInsumo") String idInsumo,
            @Param("idEstructura") String idEstructura,
            @Param("lote") String Lote);

    public Inventario obtenerIdiventarioPorInsumoEstructura(
            @Param("idInsumo") String idInsumo,
            @Param("idEstructura") String idEstructura);

    public Inventario obtenerIdiventarioPorInsumo(@Param("idInsumo") String idInsumo);

    public boolean insertListInventarios(@Param("listaInventarios") List<Inventario> listaInventarios);

    public boolean actualizarListInventarios(
            @Param("listaInventarios") List<Inventario> listaInventarios);

    public List<InventarioExtended> obtenerListaInactivosByIdInsumos(
            @Param("listIdInsumos") List<String> listIdInsumos);

    public DevolucionDetalleExtended getInventarioForDevolucion(@Param("reabastoEnviado") ReabastoEnviadoExtended reabastoEnviado);

    public boolean actualizaInvByInvInsumoEstr(@Param("listaInventarios") List<Inventario> listaInventarios);

    public boolean actualizaInvByInvInsumoEstrRefill(@Param("listaInventarios") List<Inventario> listaInventarios);

    public boolean actualizaInvByInv(@Param("listaInventarios") List<InventarioExtended> listaInventarios);

    public boolean restarInventarioMasivo(@Param("listaInventarios") List<Inventario> insumos);

    public boolean actualizarInventarioGlobal(Inventario inventario);

    public boolean revertirInventarioList(
            @Param("listaInventarios") List<Inventario> listaInventarios);

    public Inventario obtenerInventariosPorInsumoEstructuraLoteYCantidadXCaja(@Param("idInsumo") String idInsumo,
            @Param("idEstructura") String idEstructura, @Param("lote") String Lote, @Param("cantidadXCaja") Integer cantidadXCaja,
            @Param("claveProveedor") String claveProveedor);

    public Inventario obtenerInventariosPorInsumoEstructuraLoteYCantidadXCajaYFechaCad(@Param("idInsumo") String idInsumo,
            @Param("idEstructura") String idEstructura, @Param("lote") String Lote, @Param("cantidadXCaja") Integer cantidadXCaja,
            @Param("claveProveedor") String claveProveedor, @Param("fechaCaducidad") Date fechaCaducidad);

    public List<Inventario> obtenerInventariosPorInsumoEstructuraCantidadXCajaYProveedor(@Param("idInsumo") String idInsumo,
            @Param("idEstructura") String idEstructura, @Param("cantidadXCaja") Integer cantidadXCaja,
            @Param("claveProveedor") String claveProveedor, @Param("lote") String lote);

    public boolean insertarManual(Inventario inventario);

    public boolean actualizarAjusteInventario(
            @Param("listInventarioActualizar") List<InventarioExtended> listaInventarios);

    public boolean insertarNuevoInventario(Inventario inventarioSelect);

    public Inventario obtenerInventariosPorInsumoLoteFechaCadIdInsumoClProv(
            @Param("lote") String lote, @Param("idInsumo") String idInsumo, @Param("idEstructura") String idEstructura, @Param("fechaCaducidad") Date fechaCaducidad);

    public boolean actualizarInventarioPresentCom(@Param("idInventario") String idInventario, @Param("presentacionComercial") int presentacionComercial, @Param("cantidadXCaja") int cantidadXCaja);

    public Inventario validarExistInventGlob(@Param("idInsumo") String idInsumo, @Param("idEstructura") String idEstructura,
            @Param("lote") String Lote, @Param("cantidadXCaja") Integer cantidadXCaja,
            @Param("claveProveedor") String claveProveedor, @Param("presentacionComercial") int presentacionComercial);

    List<Inventario> obtenerLotesPorMedicamento(@Param("idInsumo") String idInsumo);

    public int updateInventario(@Param("listaDetalle") List<ReabastoEnviadoExtended> listaDetalle);

    public List<Inventario> obtenerExistenciasPorIdEstructuraIdInsumo(@Param("idEstructura") String idEstructura, @Param("idInsumo") String idInsumo);

    List<Inventario> obtenerLotesInventario(@Param("cadenaBusqueda") String cadenaBusqueda);

    List<InventarioExtended> obtenerListaporLotes(@Param("claveInstitucional") String claveInstitucional);

    List<Inventario> listaLotesPorClaveInst(@Param("claveInstitucional") String claveInstitucional, @Param("estructura") String estructura);

    List<Inventario> listaFechasCaducidad1(@Param("claveInstitucional") String claveInstitucional, @Param("lote") String lote, @Param("estructura") String estructura);

    public Inventario obtenerIdInsumoporClaveyLote(@Param("claveInstitucional") String claveInstitucional);
    
    public Inventario obtenerIdInsumoByClave(@Param("claveInstitucional") String claveInstitucional, @Param("lote") String lote, @Param("fecha") Date fecha, @Param("estructura") String estructura);

    public boolean actualizarInventarioCantidadActual(Inventario inventario);

    public boolean actualizarInventarioForRestockList(//@Param("idEstructura") String idEstructura,
            @Param("idInventariosConCeros") List<Inventario> idInventariosConCeros);

    public boolean actualizarInventariosCantidadActualStock(@Param("inventarioList") List<Inventario> inventarioList);

    public boolean actualizarInventarioCantidadInactivar(Inventario inventarioInactivar);

    public boolean actualizarInventarioGlobalLote(Inventario inventario);

    public boolean actualizarInventarioGlobalCaducidad(Inventario inventario);

    public boolean revertirInventarioListCancelacion(
            @Param("listaInventarios") List<Inventario> listaInventarios);

    public InventarioExtended getInventarioTracking(@Param("claveInstitucional") String claveInstitucional, @Param("lote") String lote,
             @Param("idEstructura") String idEstructura, @Param("fechaCaducidad") Date fechaCaducidad, @Param("presentacionComercial") int presentacionComercial, @Param("banderaInventario") boolean banderaInventario);

    public Inventario getInventarioByStock(@Param("lote") String lote, @Param("claveInstitucional") String claveInstitucional,
            @Param("claveEstructura") String claveEstructura, @Param("fechaCaducidad") Date fechaCaducidad, @Param("cantidadXCaja") int cantidadXCaja);

    public boolean actualizaInvporClaveinstitucional(Inventario inventario);


    public boolean actualizarInventarioBloqueos(Inventario inventario);

    public List<Inventario> getListByIdInventario(@Param("idInventarioList") List<String> idInventarioList
    );

    public InventarioExtended getInsumoByFolioClaveInvRefill(@Param("claveInstitucional") String claveInstitucional,
            @Param("lote") String lote, @Param("fechaCaducidad") Date fechaCaducidad, @Param("claveEstructura") String claveEstructura);

    List<Inventario> listaMedicamentoEstructura(@Param("claveInstitucional") String claveInstitucional);

    List<ClaveProveedorBarras> obtienelistaCodigoBarras(@Param("claveInstitucional") String claveInstitucional);
    
    List<ClaveProveedorBarras> obtienelistaProveedor(@Param("claveInstitucional") String claveInstitucional);
    
    List<ClaveProveedorBarras> obtienelistaCodigoBarrasByclaveInstAndClaveProv(@Param("claveInstitucional") String claveInstitucional , @Param("claveProveedor") String claveProveedor);

    public boolean updateClavProveedor(Inventario actualizar);
    
    List<InventarioExtended> obtenerInventarioPorIdEstructurayporIdInsumo(@Param("idEstructura") String idEstructura , @Param("idInsumo")  String idInsumo);
    
    Inventario obtenerLoteSugerido(@Param("idEstructuraList") List<String> idEstructuraList , @Param("idInsumo")  String idInsumo);
    
    List<InventarioExtended> obtenerInventarioPorIdEstructurasIdInsumo(@Param("idEstructuraList") List<String> idEstructuraList , @Param("idInsumo")  String idInsumo);

}
