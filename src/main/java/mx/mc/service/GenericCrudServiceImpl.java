package mx.mc.service;

import java.util.Date;
import java.util.List;
import mx.mc.mapper.GenericCrudMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author hramirez
 */
@Service
public abstract class GenericCrudServiceImpl<O, K> implements GenericCrudService<O, K> {

    private GenericCrudMapper<O, K> genericCrudMapper;

    public GenericCrudServiceImpl() {
    }

    public GenericCrudServiceImpl(GenericCrudMapper<O, K> genericCrudMapper) {
        this.genericCrudMapper = genericCrudMapper;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public O obtener(O o) throws Exception {
        try {
            return genericCrudMapper.obtener(o);
        } catch (Exception ex) {
            throw new Exception("Error obtener. " + ex.getMessage());
        }
    }

    @Override
    public List<O> obtenerLista(O o) throws Exception {
        try {
            return genericCrudMapper.obtenerLista(o);
        } catch (Exception ex) {
            throw new Exception("Error obtener lista. " + ex.getMessage());
        }
    }

    @Override
    public boolean insertar(O o) throws Exception {
        try {
            return genericCrudMapper.insertar(o);
        }catch(DataIntegrityViolationException e) {
            String aux = e.getCause().toString().replace("java.sql.SQLIntegrityConstraintViolationException: Duplicate entry", "Registro duplicado");
            aux=aux.replace("for key", "en el campo");
            aux=aux.replace("Fx_idx", "");            
            throw new Exception(aux);
        } catch (Exception ex) {
            throw new Exception("Error Registrar. " + ex.getMessage());
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean actualizar(O o) throws Exception {
        try {
            return genericCrudMapper.actualizar(o);
        }catch (DataIntegrityViolationException e) {
            String aux = e.getCause().toString().replace("java.sql.SQLIntegrityConstraintViolationException: Duplicate entry", "Registro duplicado");
            aux=aux.replace("for key", "en el campo");
            aux=aux.replace("Fx_idx", "");            
            throw new Exception(aux);
        } 
        catch (Exception ex) {
            throw new Exception("Error Actualizar. " + ex.getMessage());
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean eliminar(O o) throws Exception {
        try {
            return genericCrudMapper.eliminar(o);
        } catch (Exception ex) {
            throw new Exception("Error eliminar. " + ex.getMessage());
        }
    }

    @Override
    public List<O> obtenerListaTotal(O o) throws Exception {
        try {
            return genericCrudMapper.obtenerListaTotal(o);
        } catch (Exception ex) {
            throw new Exception("Error obtener public List<O> obtenerListaTotal. " + ex.getMessage());
        }
    }

    @Override
    public boolean insertarLista(List<O> o) throws Exception {
        try {
            return genericCrudMapper.insertarLista(o);
        } catch (Exception ex) {
            throw new Exception("Error insertarLista List<O>. " + ex.getMessage());
        }
    }

    @Override
    public int obtenerSiguiente() throws Exception {
        try {
            return genericCrudMapper.obtenerSiguiente();
        } catch (Exception ex) {
            throw new Exception("Error obtenerSiguiente. " + ex.getMessage());
        }
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean actualizarPorId(O o) throws Exception {
        try {
            return genericCrudMapper.actualizarPorId(o);
            
        } catch (DataIntegrityViolationException e) {
            String aux = e.getCause().toString().replace("java.sql.SQLIntegrityConstraintViolationException: Duplicate entry", "Registro duplicado");
            aux=aux.replace("for key", "en el campo");
            aux=aux.replace("Fx_idx", "");            
            throw new Exception(aux);
        } catch (Exception ex) {
            throw new Exception("Error Actualizar. " + ex.getMessage());
        }
    }

    
}
