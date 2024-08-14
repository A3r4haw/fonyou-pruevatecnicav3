package mx.mc.service;

import java.util.List;

import mx.mc.model.Proveedor;

/**
 * @author AORTIZ
 */
public interface ProveedorService extends GenericCrudService<Proveedor, String>{
    
    int insertarProveedor(Proveedor proveedor) throws Exception;

    int eliminarProveedor(String idProveedor) throws Exception;
    
    int editarProveedor(Proveedor proveedor) throws Exception;
    
    List<Proveedor> obtenerListaProveedores() throws Exception;
    
    Proveedor obtenerProvedor(String idProveedor) throws Exception;
    
    Proveedor obtenerProveedorByName(String nombreProveedor)throws Exception;
}
