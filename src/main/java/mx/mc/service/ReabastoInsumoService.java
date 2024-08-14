/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.Date;
import java.util.List;
import mx.mc.model.FolioAlternativoFolioMus;

import mx.mc.model.Inventario;
import mx.mc.model.MovimientoInventario;
import mx.mc.model.Reabasto;
import mx.mc.model.ReabastoEnviado;
import mx.mc.model.ReabastoInsumo;
import mx.mc.model.ReabastoInsumoExtended;

/**
 *
 * @author bbautista
 */
public interface ReabastoInsumoService extends GenericCrudService<ReabastoInsumo, String> {

    public List<ReabastoInsumoExtended> obtenerReabastoInsumoExtends(
            String idReabasto, List<Integer> listEstatusReabasto) throws Exception;

    public List<ReabastoInsumoExtended> obtenerReabastoInsumoExtendsSurt(
            String idReabasto, String idEstructura, List<Integer> listEstatusReabasto) throws Exception;

    public List<ReabastoInsumoExtended> obtenerReabastoInsumoProveedorFarmacia(
            String idReabasto, String idEstructura, List<Integer> listEstatusReabasto) throws Exception;
    
    public List<ReabastoInsumoExtended> obtenerValorReabastoInsumoProveedorFarmacia(
            String idReabasto, String idEstructura, List<Integer> listEstatusReabasto,String cadena, boolean activaAutoCompleteInsumos) throws Exception;

    public int obtenerCantidadMedicamento(String idMedicamento) throws Exception;

    public boolean actualizarListaReabastoInsumo(
            List<ReabastoInsumoExtended> listaReabastoInsumo) throws Exception;

    public boolean actulizaRecibirOrdenReabasto(Reabasto unReabasto, List<ReabastoInsumoExtended> listInsumoRecibir,
            List<ReabastoEnviado> listReabastoEnviado) throws Exception;

    public boolean actulizaIngresoOrdenReabasto(Reabasto unReabasto, List<ReabastoInsumoExtended> listInsumoRecibir,
            List<ReabastoEnviado> listReabastoEnviado, List<Inventario> listInventarioInsert,
            List<Inventario> listInventarioUpdate, List<MovimientoInventario> listMovInventario) throws Exception;

    public List<ReabastoInsumo> obtenerListaNormal(String idEstructura, String idEstructuraPadre, Integer tipoAlmacen, Integer tipoInsumo) throws Exception;

    public boolean guardarReabastoInsumo(List<ReabastoInsumo> rInsumos) throws Exception;

    public boolean actualizarReabastoInsumo(List<ReabastoInsumo> rInsumos) throws Exception;

    public List<ReabastoInsumo> obtenerReabastosInsumos(String idReabasto, Integer tipoAlmacen) throws Exception;

    public boolean eliminarPorIdReabasto(String idReabasto) throws Exception;

    public List<ReabastoInsumoExtended> obtenerDetalle(String idReabasto) throws Exception;

    public List<ReabastoInsumoExtended> obtenerListaNormalTransfer(String idOrigen, String idDestino, Integer tipoAlmacen, Integer tipoInsumo) throws Exception;

    public ReabastoInsumo obtenerMaxMinReorCantActual(String idEStructura, String idMedicamento) throws Exception;
    
    public List<ReabastoInsumoExtended> obtenerListaMaxMinReorCantActual(String idEStructura, String claveInstitucional) throws Exception;

    public ReabastoInsumoExtended getReabastoInsumoByFolioClave(String folio, String claveInstitucional, String lote, Date fechaCaducidad, String codigoArea) throws Exception;
    
    public ReabastoInsumoExtended getReabastoInsumoByFolioClaveInv(String claveInstitucional, String lote, Date fechaCaducidad, String codigoArea) throws Exception;
    
    public List<ReabastoInsumoExtended> getListReabastoInsumoByFolioClaveInv(String claveInstitucional, String lote, Date fechaCaducidad, String codigoArea) throws Exception;
    
    public List<ReabastoInsumoExtended> obtenerDetalleReabasto(String idReabasto, String folioAlternativo) throws Exception;

    public ReabastoInsumo obtenerReabastoInsumo(String idReabasto, String idInsumo) throws Exception;

    public boolean actulizaOrdenReabastoWS(Reabasto unReabasto, List<ReabastoInsumoExtended> listInsumoRecibir,
            List<ReabastoEnviado> listReabastoEnviado, List<ReabastoInsumo> listReabastoInsumo, FolioAlternativoFolioMus folioAlterMus) throws Exception;

    public List<ReabastoInsumo> obtenerInsumosdeReabastoporIdReabasto(String idReabasto, String idEStructura, Integer tipoAlmacen) throws Exception;

    public List<ReabastoInsumo> obtenerInsumosEnviadosReabastoByEstructura(String idEstructura,Date fechaSolicitud) throws Exception;
    
    public List<ReabastoInsumo> obtenerInsumosEnviadosPrescripByEstructura(String idEstructura,Date fechaSolicitud) throws Exception;
    
    public List<ReabastoInsumo> obtenerListaGlobalCEDIME() throws Exception;
    
    public List<ReabastoInsumo> obtenerListaInsumoByEstructura(String idEstructura,String idInsumo,String solicitud) throws Exception;
    
    public List<ReabastoInsumoExtended> detalleInsumoByFolioAlternativo(String folio)throws Exception;
    
    public ReabastoInsumo obtenerReabastoInsumoForSIAM(String idReabasto,String idInsumo,String idAlmancen)throws Exception;
    
    public List<ReabastoInsumoExtended> obtenerReabastoInsumoPorIdEstructuraYporIdInsumo(String idEstructura, String idInsumo) throws Exception;
    
}
