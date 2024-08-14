/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.Date;
import java.util.List;
import mx.mc.model.ClaveProveedorBarras;
import mx.mc.model.ClaveProveedorBarras_Extend;
import mx.mc.model.Inventario;

/**
 *
 * @author gcruz
 */
public interface ClaveProveedorBarrasService extends GenericCrudService<ClaveProveedorBarras, String> {

    public ClaveProveedorBarras_Extend obtenerMedicamentoByClaveSAP(String claveProveedor) throws Exception;

    public ClaveProveedorBarras obtenerExistenciaByProveedorBarras(String claveProveedor, String codigoBarras) throws Exception;

    public ClaveProveedorBarras_Extend obtenerClave(String codigoBarras) throws Exception;
    
    public ClaveProveedorBarras_Extend obtenerByClave(String idReabastoEnviado)throws Exception;

    public List<ClaveProveedorBarras_Extend> obtenerListaClavesCodigoBarras(String codigoBarras, String idEstructura, String idUsuario) throws Exception;

    public List<ClaveProveedorBarras_Extend> obtenerListaClavesCodigoBarrasExt(String codigoBarras, String idEstructura, String idUsuario) throws Exception;

    public List<ClaveProveedorBarras_Extend> obtenerListaClavesCodigoQrExt(String claveInstitucional, String lote, Date fechaCaducidad, String idEstructura, Integer cantidadXcaja) throws Exception;

    public List<ClaveProveedorBarras_Extend> obtenerListaClavesCodigoQr(String claveInstitucional, String lote, Date fechaCaducidad, String idEstructura, String idUsuario) throws Exception;

    //metodo 
    public List<ClaveProveedorBarras_Extend> obtenerListaByQR(String claveInstitucional, String lote, Date fechaCaducidad, String idEstructura, String idUsuario,boolean buscaCantCero) throws Exception;

    public List<ClaveProveedorBarras_Extend> obtenerListaClavesByBarras(String codigoBarras, String idEstructura, String idUsuario,boolean buscaCantCero) throws Exception;

    public List<ClaveProveedorBarras_Extend> obtenerListaByName(String codigoBarras, String idEstructura, String idUsuario,boolean buscaCantCero) throws Exception;

    //Transferencia
    public List<ClaveProveedorBarras_Extend> obtenerListaClavesCodigoQrTransfer(String claveInstitucional, String lote, Date fechaCaducidad, String idEstructuraOrigen, String idEstructuraDestino) throws Exception;

    public List<ClaveProveedorBarras_Extend> obtenerListaClavesCodigoBarrasTransfer(String codigoBarras, String idEstructuraOrigen, String idEstructuraDestino) throws Exception;

    public List<ClaveProveedorBarras_Extend> obtenerListaClavesDescripcionTransfer(String descripcion, String idEstructuraOrigen, String idEstructuraDestino) throws Exception;
    
    public List<ClaveProveedorBarras_Extend> obtenerListaClavesCodigoBarrasExtChiconcuac(
            String codigoBarras, String idEstructura, String idUsuario , List<Integer> listaSubCategorias, boolean rManual) throws Exception;
    
    public List<ClaveProveedorBarras_Extend> obtenerListaByNameClave(String folio, String cadena) throws Exception;
    
    public List<ClaveProveedorBarras_Extend> obtenerListaByNameClaveSkuQr(String folio, String cadena,boolean activaAutoCompleteInsumos) throws Exception;
        
    public List<ClaveProveedorBarras_Extend> obtenerByFolio(String folio)throws Exception;
    
    public boolean insertarCodigoProveedor(ClaveProveedorBarras inserCodBarr, Inventario invenupd) throws Exception;
    
    public boolean eliminarCodigoProveedorCodigoBarra(ClaveProveedorBarras codBarr) throws Exception;
    
}
