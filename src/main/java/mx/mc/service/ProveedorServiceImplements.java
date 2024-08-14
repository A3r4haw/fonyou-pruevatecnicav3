package mx.mc.service;

import java.util.ArrayList;
import java.util.List;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.ProveedorMapper;
import mx.mc.model.Proveedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author AORTIZ
 */
@Service
public class ProveedorServiceImplements extends GenericCrudServiceImpl<Proveedor, String> implements ProveedorService {
    
    @Autowired
    private ProveedorMapper proveedorMapper;

    public ProveedorServiceImplements() {
        //No code needed in constructor
    }

    @Autowired
    public ProveedorServiceImplements(GenericCrudMapper<Proveedor, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public int insertarProveedor(Proveedor proveedor) throws Exception {
        try {
            return proveedorMapper.insertarProveedor(proveedor);
        } catch (Exception ex) {
            throw new Exception("Error al Insertar el Proveedor. " + ex.getMessage());
        }
    }

    @Override
    public List<Proveedor> obtenerListaProveedores() throws Exception {
        try {
            List<Proveedor> listaProvedores = proveedorMapper.obtenerProveedores();
            if (listaProvedores == null) {
                listaProvedores = new ArrayList<>();
            }
            return listaProvedores;
        } catch (Exception ex) {
            throw new Exception("Error al obtener la lista. " + ex.getMessage());
        }
    }

    @Override
    public int eliminarProveedor(String idProveedor) throws Exception {
        try {
            return proveedorMapper.eliminarProveedor(idProveedor);
        } catch (Exception ex) {
            throw new Exception("Error al obtener la lista. " + ex.getMessage());
        }
    }

    @Override
    public int editarProveedor(Proveedor proveedor) throws Exception {
        try {
            return proveedorMapper.editarProveedor(proveedor);
        } catch (Exception ex) {
            throw new Exception("Error al obtener la lista. " + ex.getMessage());
        }
    }

    @Override
    public Proveedor obtenerProvedor(String idProveedor) throws Exception {
        try {
            return proveedorMapper.obtenerProvedor(idProveedor);
        } catch (Exception ex) {
            throw new Exception("Ocurrio un error al obtener al proveedor    " + ex.getMessage());
        }
    }

    @Override
    public Proveedor obtenerProveedorByName(String nombreProveedor) throws Exception {
        try {
            return proveedorMapper.obtenerProveedorByName(nombreProveedor);
        } catch (Exception ex) {
            throw new Exception("Ocurrio un error al obtener el nombre del proveedor: " + ex.getMessage());
        }
    }

}
