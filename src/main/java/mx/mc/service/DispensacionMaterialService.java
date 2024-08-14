/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.model.DispensacionMaterial;
import mx.mc.model.DispensacionMaterialExtended;
import mx.mc.model.DispensacionMaterialInsumo;
import mx.mc.model.Folios;
import mx.mc.model.Inventario;
import mx.mc.model.MovimientoInventario;
import mx.mc.model.ParamBusquedaReporte;

/**
 *
 * @author apalacios
 */
public interface DispensacionMaterialService {
    public List<DispensacionMaterialExtended> obtenerDispensacionesLazzy(String idEstructura, String idUsuario, String cadenaBusqueda, int startingAt, int maxPerPage, String sortBy, String sortOrder) throws Exception;
    public List<DispensacionMaterialExtended> obtenerDispensacionesReporte(ParamBusquedaReporte params) throws Exception;
    public Long obtenerTotalDispensacionesLazzy(String idEstructura, String idUsuario, String cadenaBusqueda) throws Exception;
    public Folios obtenerFolioDispensacion() throws Exception;
    public boolean registrarDispensacionMaterial(Folios folio, DispensacionMaterial dispensacion, List<DispensacionMaterialInsumo> listaDispensacionInsumo, List<Inventario> inventarioList, List<MovimientoInventario> movimientoInventarioList) throws Exception;
}
