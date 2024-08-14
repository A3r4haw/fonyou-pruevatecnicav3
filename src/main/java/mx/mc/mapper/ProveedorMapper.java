package mx.mc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import mx.mc.model.Proveedor;

/**
 * @author AORTIZ
 */
public interface ProveedorMapper extends GenericCrudMapper<Proveedor, String>{
    
    int insertarProveedor(Proveedor proveedor);
    
    int eliminarProveedor(@Param("idProveedor") String idProveedor);
    
    int editarProveedor(Proveedor proveedor);

    List<Proveedor> obtenerProveedores();
    
    Proveedor obtenerProvedor(@Param("idProveedor") String idProveedor);
    
    Proveedor obtenerProveedorByName(@Param("nombreProveedor") String nombreProveedor);
}
