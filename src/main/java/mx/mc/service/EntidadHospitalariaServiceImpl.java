package mx.mc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.EntidadHospitalariaMapper;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.EntidadHospitalariaExtended;

/**
 *
 * @author mcalderon
 */
@Service
public class EntidadHospitalariaServiceImpl extends GenericCrudServiceImpl<EntidadHospitalaria, String> implements EntidadHospitalariaService{
    
    @Autowired
    public EntidadHospitalariaServiceImpl(GenericCrudMapper<EntidadHospitalaria, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
    
    @Autowired
    private EntidadHospitalariaMapper entidadHospitalariaMapper;

    @Override
    public EntidadHospitalaria obtenerEntidadById(String idEntidadHospitalaria) throws Exception {
        try {
            return entidadHospitalariaMapper.obtenerEntidadById(idEntidadHospitalaria);
        } catch (Exception e) {
            throw new Exception("Error al Obtener la EntidadHospitalaria" + e.getMessage());
        }
    }

    @Override
    public boolean insertEntidadHospTable(EntidadHospitalaria entidadhosp) throws Exception {
         boolean resp = false;         
        try {
            resp = entidadHospitalariaMapper.insertEntidadHospTable(entidadhosp);        
        } catch (Exception e) {
            throw new Exception("Error al registar Entidad. " + e);
        }
        return resp;
    }

    @Override
    public List<EntidadHospitalaria> obtenerBusquedaEntidad(String cadena) throws Exception {
        List<EntidadHospitalaria> entidadHospList = new ArrayList<>();
        try {
            entidadHospList = entidadHospitalariaMapper.obtenerBusquedaEntidad(cadena);
        } catch (Exception ex) {
            throw new Exception("Error al obtener la busqueda" + ex.getMessage());
        }
        return entidadHospList;
    }
    
    @Override
    public boolean eliminarEntidad(EntidadHospitalaria mS) throws Exception {
        boolean res = false;
        try {
            res = entidadHospitalariaMapper.eliminarEntidad(mS);
        } catch (Exception ex) {
            throw new Exception("Error al eliminar la Entidad" + ex.getMessage());
        }
        return res;
    }
    
    @Override
    public boolean actualizarEstatusEntidad(EntidadHospitalaria mS) throws Exception {
        boolean resp = false;
        try {
            resp = entidadHospitalariaMapper.actualizarEstatusEntidad(mS);
        } catch (Exception e) {
            throw new Exception("Error al actualizar EstatusEntidad. " + e);
        }
        return resp;
    }

    @Override
    public EntidadHospitalariaExtended obtenerEntidadHospital() throws Exception {
         try {
            return entidadHospitalariaMapper.obtenerEntidadHospital();
        } catch (Exception e) {
            throw new Exception("Error al Obtener la EntidadHospitalaria de Hospital" + e.getMessage());
        }
    }

    @Override
    public EntidadHospitalaria obtenerEntidadHospByIdUsuaurio(String idUsuario) throws Exception {
        try {
            return entidadHospitalariaMapper.obtenerEntidadHospByIdUsuaurio(idUsuario);
        } catch (Exception e) {
            throw new Exception("Error al obtenerEntidadHospByIdUsuaurio " + e.getMessage());
        }
    }
    
    @Override
    public List<EntidadHospitalaria> obtenerEntidadesdHospitalActivas() throws Exception {
        try {
            return entidadHospitalariaMapper.obtenerEntidadesdHospitalActivas();
        } catch(Exception ex) {
            throw new Exception("Ocurrio un error al buscar las entidades activas  " + ex.getMessage());
        }
    }
    
    @Override
    public List<EntidadHospitalaria> obtenerEntidadesActiasNoEsteIdEntidad(String idEntidadHospitalaria) throws Exception {
        try {
            return entidadHospitalariaMapper.obtenerEntidadesActiasNoEsteIdEntidad(idEntidadHospitalaria);
        } catch(Exception ex) {
            throw new Exception("Error al obtene entidades activas menos la que se va actualizar  " + ex.getMessage());
        }
    }
    
    @Override
    public boolean actualizarEntidadHospitalaria(EntidadHospitalaria entidadHospitalaria) throws Exception {
        try {
             return entidadHospitalariaMapper.actualizarEntidadHospitalaria(entidadHospitalaria);
        } catch(Exception ex) {
            throw new Exception("Error al momento de actualizar la entidad hospitalaria " + ex.getMessage());
        }
    }
}
    

