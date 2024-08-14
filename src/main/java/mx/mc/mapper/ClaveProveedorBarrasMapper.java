/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.Date;
import java.util.List;
import mx.mc.model.ClaveProveedorBarras;
import mx.mc.model.ClaveProveedorBarras_Extend;
import mx.mc.model.Inventario;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author gcruz
 */
public interface ClaveProveedorBarrasMapper extends GenericCrudMapper<ClaveProveedorBarras, String> {
    
     public ClaveProveedorBarras obtenerExistenciaByProveedorBarras(@Param("claveProveedor")String claveProveedor, @Param("codigoBarras") String codigoBarras) throws Exception;
     
     boolean insertarListClaveProvedorBarras(@Param("listClaveProveedorBarras") List<ClaveProveedorBarras> listClaveProveedorBarras) throws Exception;
     
     public ClaveProveedorBarras_Extend obtenerMedicamentoByClaveSAP(@Param("claveProveedor")String claveProveedor) throws Exception;
     
     boolean updateListClaveProveedorBarras(@Param("listClaveProveedorBarrasExiste") List<ClaveProveedorBarras> listClaveProveedorBarrasExiste) throws Exception;
     
     ClaveProveedorBarras_Extend obtenerClave(@Param("codigoBarras") String codigoBarras);
     
     ClaveProveedorBarras_Extend obtenerByClave(@Param("idReabastoEnviado") String idReabastoEnviado);
     
     public List<ClaveProveedorBarras_Extend> obtenerListaClavesCodigoBarras(@Param("codigoBarras") String codigoBarras, @Param("idEstructura") String idEstructura,@Param("idUsuario") String idUsuario);
     
     public List<ClaveProveedorBarras_Extend> obtenerListaClavesCodigoBarrasExt(@Param("codigoBarras") String codigoBarras, @Param("idEstructura") String idEstructura,@Param("idUsuario") String idUsuario);
     
     public List<ClaveProveedorBarras_Extend> obtenerListaClavesCodigoBarrasExtChiconcuac(
             @Param("codigoBarras") String codigoBarras, 
             @Param("idEstructura") String idEstructura,
             @Param("idUsuario") String idUsuario,
             @Param("listaSubCategoria") List<Integer>listaSubCategoria,
             @Param("Rmanual") boolean Rmanual
     );
     
     public List<ClaveProveedorBarras_Extend> obtenerListaClavesCodigoQrExt(
             @Param("claveInstitucional") String claveInstitucional
             , @Param("lote") String lote
             , @Param("fechaCaducidad") Date fechaCaducidad
             , @Param("idEstructura") String idEstructura
             , @Param("cantidadXcaja") Integer cantidadXcaja);
     
     public List<ClaveProveedorBarras_Extend> obtenerListaClavesCodigoQr(
             @Param("claveInstitucional") String claveInstitucional
             , @Param("lote") String lote
             , @Param("fechaCaducidad") Date fechaCaducidad
             , @Param("idEstructura") String idEstructura,@Param("idUsuario") String idUsuario);
     
     //metodo prueba
     public List<ClaveProveedorBarras_Extend> obtenerListaByName(@Param("codigoBarras") String codigoBarras, @Param("idEstructura") 
             String idEstructura,@Param("idUsuario") String idUsuario,@Param("buscaCantCero")boolean buscaCantCero); 
 
     public List<ClaveProveedorBarras_Extend> obtenerListaClavesByBarras(@Param("codigoBarras") String codigoBarras,
             @Param("idEstructura") String idEstructura,
             @Param("idUsuario") String idUsuario,@Param("buscaCantCero")boolean buscaCantCero); 
      
     public List<ClaveProveedorBarras_Extend> obtenerListaByQR(
             @Param("claveInstitucional") String claveInstitucional
             , @Param("lote") String lote
             , @Param("fechaCaducidad") Date fechaCaducidad
             , @Param("idEstructura") String idEstructura,@Param("idUsuario") String idUsuario
             ,@Param("buscaCantCero")boolean buscaCantCero);
     
     //Transferencia
     public List<ClaveProveedorBarras_Extend> obtenerListaClavesCodigoQrTransfer(
             @Param("claveInstitucional") String claveInstitucional
             , @Param("lote") String lote
             , @Param("fechaCaducidad") Date fechaCaducidad
             , @Param("idEstructuraOrigen") String idEstructuraOrigen
             , @Param("idEstructuraDestino") String idEstructuraDestino);
     
     public List<ClaveProveedorBarras_Extend> obtenerListaClavesCodigoBarrasTransfer(
             @Param("codigoBarras") String codigoBarras
             , @Param("idEstructuraOrigen") String idEstructuraOrigen
             ,@Param("idEstructuraDestino") String idEstructuraDestino);
     
     public List<ClaveProveedorBarras_Extend> obtenerListaClavesDescripcionTransfer(
             @Param("descripcion") String descripcion
             , @Param("idEstructuraOrigen") String idEstructuraOrigen
             , @Param("idEstructuraDestino") String idEstructuraDestino);
     
     public List<ClaveProveedorBarras_Extend> obtenerByFolio(@Param("folio")String folio);
     
     public List<ClaveProveedorBarras_Extend> obtenerListaByNameClave(@Param("folio")String folio,@Param("cadena")String cadena); 
     
     public List<ClaveProveedorBarras_Extend> obtenerListaByNameClaveSkuQr(@Param("folio")String folio,@Param("cadena")String cadena,
             @Param("activa_autoCompleteInsumos")boolean activa_autoCompleteInsumos); 
     
     public boolean insertarCodigoProveedor(ClaveProveedorBarras inserCodBarr );
     
     public boolean eliminarCodigoProveedorCodigoBarra(ClaveProveedorBarras codBarr );
    
}
