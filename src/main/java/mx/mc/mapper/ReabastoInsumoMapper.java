/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.Date;
import java.util.List;
import mx.mc.model.ReabastoInsumo;
import mx.mc.model.ReabastoInsumoExtended;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author bbautista
 */
public interface ReabastoInsumoMapper extends GenericCrudMapper<ReabastoInsumo, String> {

    public List<ReabastoInsumoExtended> obtenerReabastoInsumoExtends(
            @Param("idReabasto") String idReabasto, @Param("listEstatusReabasto") List<Integer> listEstatusReabasto);

    public List<ReabastoInsumoExtended> obtenerReabastoInsumoExtendsSurt(
            @Param("idReabasto") String idReabasto,
            @Param("idEstructura") String idEstructura,
            @Param("listEstatusReabasto") List<Integer> listEstatusReabasto);

    public boolean actualizarListaReabastoInsumo(
            List<ReabastoInsumoExtended> listaReabastoInsumo);

    public boolean eliminarListaReabastoInsumo(
            List<ReabastoInsumo> listaReabastoInsumo);

    public int obtenerCantidadMedicamento(@Param("idMedicamento") String idMedicamento);

    public List<ReabastoInsumo> obtenerListaNormal(
            @Param("idEstructura") String idEstructura,
            @Param("idEstructuraPadre") String idEstructuraPadre,
            @Param("tipoAlmacen") Integer idTipoAlmacen,
            @Param("tipoInsumo") Integer tipoInsumo);

    public ReabastoInsumo obtenerMaxMinReorCantActual(
            @Param("idEstructura") String idEstructura,
            @Param("idMedicamento") String idMedicamento);

    public List<ReabastoInsumoExtended> obtenerListaMaxMinReorCantActual(
            @Param("idEstructura") String idEstructura,
            @Param("cadena") String cadena);

    public boolean guardarReabastoInsumo(List<ReabastoInsumo> rInsumos);

    public boolean actualizarReabastoInsumo(List<ReabastoInsumo> rInsumos);

    public List<ReabastoInsumo> obtenerReabastosInsumos(@Param("idReabasto") String idReabasto, @Param("tipoAlmacen") Integer idTipoAlmacen);

    public boolean eliminarPorIdReabasto(@Param("idReabasto") String idReabasto);

    public boolean actualizarPorIdReabasto(ReabastoInsumo reabastoInsumo);

    public List<ReabastoInsumoExtended> obtenerReabastoInsumoProveedorFarmacia(
            @Param("idReabasto") String idReabasto,
            @Param("listEstatusReabasto") List<Integer> listEstatusReabasto);

    public List<ReabastoInsumoExtended> obtenerValorReabastoInsumoProveedorFarmacia(
            @Param("idReabasto") String idReabasto,
            @Param("listEstatusReabasto") List<Integer> listEstatusReabasto,
            @Param("cadena") String cadena,
            @Param("activa_autoCompleteInsumos") boolean activa_autoCompleteInsumos);

    public boolean insertarListReabastoInsumos(@Param("listReabastoInsumo") List<ReabastoInsumo> listReabastoInsumo) throws Exception;

    List<ReabastoInsumoExtended> detalleTransfer(@Param("idReabasto") String idReabasto);

    public List<ReabastoInsumoExtended> obtenerListaNormalTransfer(
            @Param("idOrigen") String idOrigen,
            @Param("idDestino") String idDestino,
            @Param("tipoAlmacen") Integer idTipoAlmacen,
            @Param("tipoInsumo") Integer tipoInsumo);

    public ReabastoInsumoExtended getReabastoInsumoByFolioClave(@Param("folio") String folio, @Param("claveInstitucional") String claveInstitucional,
            @Param("lote") String lote, @Param("fechaCaducidad") Date fechaCaducidad, @Param("claveEstructura") String claveEstructura);

    public ReabastoInsumoExtended getReabastoInsumoByFolioClaveInv(@Param("claveInstitucional") String claveInstitucional,
            @Param("lote") String lote, @Param("fechaCaducidad") Date fechaCaducidad, @Param("claveEstructura") String claveEstructura);

    public List<ReabastoInsumoExtended> getListReabastoInsumoByFolioClaveInv(@Param("claveInstitucional") String claveInstitucional,
            @Param("lote") String lote, @Param("fechaCaducidad") Date fechaCaducidad, @Param("claveEstructura") String claveEstructura);

    public List<ReabastoInsumoExtended> obtenerDetalleReabasto(@Param("idReabasto") String idReabasto, @Param("folioAlternativo") String folioAlternativo);

    public ReabastoInsumo obtenerReabastoInsumo(@Param("idReabasto") String idReabasto, @Param("idInsumo") String idInsumo) throws Exception;

    public List<ReabastoInsumo> obtenerInsumosdeReabastoporIdReabasto(@Param("idReabasto") String idReabasto, @Param("idEstructura") String idEstructura, @Param("tipoAlmacen") Integer idTipoAlmacen);

    public List<ReabastoInsumo> obtenerInsumosEnviadosReabastoByEstructura(@Param("idEstructura") String idEstructura, @Param("fechaSolicitud") Date fechaSolicitud);

    public List<ReabastoInsumo> obtenerInsumosEnviadosPrescripByEstructura(@Param("idEstructura") String idEstructura, @Param("fechaSolicitud") Date fechaSolicitud);

    public List<ReabastoInsumo> obtenerListaGlobalCEDIME();

    public List<ReabastoInsumo> obtenerListaInsumoByEstructura(@Param("idEstructura") String idEstructura, @Param("idInsumo") String idInsumo, @Param("solicitud") String solicitud);

    public List<ReabastoInsumoExtended> detalleInsumoByFolioAlternativo(@Param("folioAlternativo") String folio);

    public Integer obtenerTamañoDetalle(@Param("idReabasto") String idReabasto);

    public ReabastoInsumo obtenerReabastoInsumoForSIAM(@Param("idReabasto") String idReabasto, @Param("idInsumo") String idInsumo, @Param("idAlmacen") String idAlmacen) throws Exception;

    public List<ReabastoInsumoExtended> obtenerReabastoInsumoPorIdEstructuraYporIdInsumo(@Param("idEstructura") String idEstructura, @Param("idInsumo") String idInsumo);

}
