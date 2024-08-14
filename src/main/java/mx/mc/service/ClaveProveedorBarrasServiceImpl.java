/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import mx.mc.mapper.ClaveProveedorBarrasMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.InventarioMapper;
import mx.mc.model.ClaveProveedorBarras;
import mx.mc.model.ClaveProveedorBarras_Extend;
import mx.mc.model.Inventario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author gcruz
 */
@Service
public class ClaveProveedorBarrasServiceImpl extends GenericCrudServiceImpl<ClaveProveedorBarras, String> implements ClaveProveedorBarrasService {
    
    @Autowired
    private ClaveProveedorBarrasMapper claveProveedorBarrasMapper;
    
    @Autowired
    private InventarioMapper inventarioMapper;
    
    @Autowired
    public ClaveProveedorBarrasServiceImpl(GenericCrudMapper<ClaveProveedorBarras, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
    
    @Override
    public ClaveProveedorBarras obtenerExistenciaByProveedorBarras(String claveProveedor, String codigoBarras) throws Exception {
        try {
          return claveProveedorBarrasMapper.obtenerExistenciaByProveedorBarras(claveProveedor, codigoBarras);
        } catch(Exception ex) {
            throw new Exception("Ocurrio un error al momento de buscar la existencia en claveProveedorBarras   " + ex.getMessage());
        }        
    }

    @Override
    public ClaveProveedorBarras_Extend obtenerMedicamentoByClaveSAP(String claveProveedor) throws Exception {
        try {
            return claveProveedorBarrasMapper.obtenerMedicamentoByClaveSAP(claveProveedor);
        }catch(Exception ex){
                throw new Exception("Error al obtener informacion de medicamento: " + ex.getMessage());
        }
    }

    @Override
    public ClaveProveedorBarras_Extend obtenerClave(String codigoBarras) throws Exception {
         try {
            return claveProveedorBarrasMapper.obtenerClave(codigoBarras);
        } catch (Exception ex) {
            throw new Exception("Error al obtener la clave ISSEMYM: " + ex.getMessage());
        }
    }
    
    @Override
    public ClaveProveedorBarras_Extend obtenerByClave(String idReabastoEnviado) throws Exception {
        try {
            return claveProveedorBarrasMapper.obtenerByClave(idReabastoEnviado);
        } catch (Exception e) {
            throw new Exception("Error al obtener la Clave " + e.getMessage());
        }
    }

    @Override
    public List<ClaveProveedorBarras_Extend> obtenerListaClavesCodigoBarras(String codigoBarras, String idEstructura, String idUsuario) throws Exception {
        List<ClaveProveedorBarras_Extend> list= new ArrayList<>();
        try{
            list= claveProveedorBarrasMapper.obtenerListaClavesCodigoBarras(codigoBarras, idEstructura,idUsuario);
        }catch(Exception ex){
            throw new Exception("Error en el metodo obtenerListaClavesCodigoBarras: " + ex.getMessage());
        }
        return list;
    }
    
    @Override
    public List<ClaveProveedorBarras_Extend> obtenerListaClavesCodigoBarrasExt(String codigoBarras, String idEstructura, String idUsuario) throws Exception {
        List<ClaveProveedorBarras_Extend> list= new ArrayList<>();
        try{
            list= claveProveedorBarrasMapper.obtenerListaClavesCodigoBarrasExt(codigoBarras, idEstructura,idUsuario);
        }catch(Exception ex){
            throw new Exception("Error en el metodo obtenerListaClavesCodigoBarrasExt: " + ex.getMessage());
        }
        return list;
    }
    
    @Override
    public List<ClaveProveedorBarras_Extend> obtenerListaClavesCodigoBarrasExtChiconcuac(
            String codigoBarras, String idEstructura, String idUsuario , List<Integer> listaSubCategorias,boolean rManual) throws Exception {
        List<ClaveProveedorBarras_Extend> list= new ArrayList<>();
        try{
            list= claveProveedorBarrasMapper.obtenerListaClavesCodigoBarrasExtChiconcuac(
                    codigoBarras, idEstructura,idUsuario,listaSubCategorias,rManual);
        }catch(Exception ex){
            throw new Exception("Error en el metodo obtenerListaClavesCodigoBarrasExtChiconcuac: " + ex.getMessage());
        }
        return list;
    }
    
    @Override
    public List<ClaveProveedorBarras_Extend> obtenerListaClavesCodigoQrExt(String claveInstitucional, String lote, Date fechaCaducidad, String idEstructura, Integer cantidadXcaja) throws Exception {
        List<ClaveProveedorBarras_Extend> list= new ArrayList<>();
        try{
            list= claveProveedorBarrasMapper.obtenerListaClavesCodigoQrExt(claveInstitucional, lote, fechaCaducidad, idEstructura, cantidadXcaja);
        }catch(Exception ex){
            throw new Exception("Error en el metodo obtenerListaClavesCodigoQrExt: " + ex.getMessage());
        }
        return list;
    }
    
    @Override
    public List<ClaveProveedorBarras_Extend> obtenerListaClavesCodigoQr (String claveInstitucional, String lote , Date fechaCaducidad, String idEstructura,String idUsuario) throws Exception {
        try{
            return claveProveedorBarrasMapper.obtenerListaClavesCodigoQr(claveInstitucional, lote, fechaCaducidad , idEstructura,idUsuario);
        }catch(Exception ex){
            throw new Exception("Error en el metodo obtenerListaClavesCodigoQr: " + ex.getMessage());
        }
    }

    @Override
    public List<ClaveProveedorBarras_Extend> obtenerListaByName(String codigoBarras, String idEstructura, String idUsuario,boolean buscaCantCero) throws Exception {
        try {
            return claveProveedorBarrasMapper.obtenerListaByName(codigoBarras, idEstructura, idUsuario,buscaCantCero);
        } catch (Exception ex) {
            throw new Exception("Error en el metodo obtenerListaPrueba: " + ex.getMessage());
        }
    }

    @Override
    public List<ClaveProveedorBarras_Extend> obtenerListaByQR(String claveInstitucional, String lote, Date fechaCaducidad, String idEstructura, String idUsuario,boolean buscaCantCero) throws Exception {
        try {
            return claveProveedorBarrasMapper.obtenerListaByQR(claveInstitucional, lote, fechaCaducidad, idEstructura, idUsuario,buscaCantCero);            
        } catch (Exception ex) {
            throw new Exception("Error en el metodo obtenerListaByQR: "+ ex.getMessage());
        }
    }

    @Override
    public List<ClaveProveedorBarras_Extend> obtenerListaClavesByBarras(String codigoBarras, String idEstructura, String idUsuario,boolean buscaCantCero) throws Exception {
        try {
            return claveProveedorBarrasMapper.obtenerListaClavesByBarras(codigoBarras, idEstructura, idUsuario, buscaCantCero);
        } catch (Exception ex) {
            throw  new Exception("Error en el metodo obtenerListaClavesByBarras: "+ex.getMessage());
        }
    }
    
//Transferencia    
@Override
    public List<ClaveProveedorBarras_Extend> obtenerListaClavesCodigoQrTransfer (String claveInstitucional, String lote , Date fechaCaducidad, String idEstructuraOrigen,String idEstructuraDestino) throws Exception {
        try{
            return claveProveedorBarrasMapper.obtenerListaClavesCodigoQrTransfer(claveInstitucional, lote, fechaCaducidad , idEstructuraOrigen,idEstructuraDestino);
        }catch(Exception ex){
            throw new Exception("Error en el metodo obtenerListaClavesCodigoQrTransfer: " + ex.getMessage());
        }
    }
     @Override
    public List<ClaveProveedorBarras_Extend> obtenerListaClavesCodigoBarrasTransfer(String codigoBarras, String idEstructuraOrigen,String idEstructuraDestino) throws Exception {
        List<ClaveProveedorBarras_Extend> list= new ArrayList<>();
        try{
            list= claveProveedorBarrasMapper.obtenerListaClavesCodigoBarrasTransfer(codigoBarras, idEstructuraOrigen,idEstructuraDestino);
        }catch(Exception ex){
            throw new Exception("Error en el metodo obtenerListaClavesCodigoBarrasTransfer: " + ex.getMessage());
        }
        return list;
    }
    @Override
    public List<ClaveProveedorBarras_Extend> obtenerListaClavesDescripcionTransfer(String descripcion, String idEstructuraOrigen,String idEstructuraDestino) throws Exception {
        List<ClaveProveedorBarras_Extend> list= new ArrayList<>();
        try{
            list= claveProveedorBarrasMapper.obtenerListaClavesDescripcionTransfer(descripcion, idEstructuraOrigen,idEstructuraDestino);
        }catch(Exception ex){
            throw new Exception("Error en el metodo obtenerListaClavesDescripcionTransfer: " + ex.getMessage());
        }
        return list;
    }

    @Override
    public List<ClaveProveedorBarras_Extend> obtenerByFolio(String folio) throws Exception {
        try {
            return claveProveedorBarrasMapper.obtenerByFolio(folio);
        } catch (Exception e) {
            throw  new Exception("Error al consultar por Folio!!!");
        }
    }

    @Override
    public List<ClaveProveedorBarras_Extend> obtenerListaByNameClave(String folio, String cadena) throws Exception {
        try {
            return claveProveedorBarrasMapper.obtenerListaByNameClave(folio, cadena);
        } catch (Exception e) {
            throw new Exception("Error al Obtener los registros " + e.getMessage());
        }
    }

    @Override
    public List<ClaveProveedorBarras_Extend> obtenerListaByNameClaveSkuQr(String folio, String cadena,boolean activaAutoCompleteInsumos) throws Exception {
        try {
            return claveProveedorBarrasMapper.obtenerListaByNameClaveSkuQr(folio, cadena,activaAutoCompleteInsumos);
        } catch (Exception e) {
            throw new Exception("Error al Obtener los registros " + e.getMessage());
        }
    }
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean insertarCodigoProveedor(ClaveProveedorBarras inserCodBarr, Inventario invenupd) throws Exception {
        boolean res = false;
        try {
        res = claveProveedorBarrasMapper.insertarCodigoProveedor(inserCodBarr);
            if (!res) 
                throw new Exception("Error al insertar CodigoBarras. ");
            else { 
                res = inventarioMapper.updateClavProveedor(invenupd);
                if (!res) {
                    throw new Exception("Error al actualizar el codigo de barras. ");
                }                    
            }
        } catch (Exception ex) {
            throw new Exception("Error al insertar y actualizar datos Codigo Proveedor" + ex.getMessage());
        }
         return res;
    }
    
    @Override
    public boolean eliminarCodigoProveedorCodigoBarra(ClaveProveedorBarras codBarr) throws Exception {
        try {
            return claveProveedorBarrasMapper.eliminarCodigoProveedorCodigoBarra(codBarr);
        } catch (Exception ex) {
            throw new Exception("Error al eliminar Codigo Proveedor" + ex.getMessage());
        }
    }
    
}
