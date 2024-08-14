/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;


import java.util.List;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.EntidadHospitalariaExtended;


/**
 *
 * @author mcalderon
 */
public interface EntidadHospitalariaService extends GenericCrudService<EntidadHospitalaria, String>{

    public EntidadHospitalaria obtenerEntidadById(String idEntidadHospitalaria) throws Exception;
    
    public boolean insertEntidadHospTable (EntidadHospitalaria entidadhosp)throws Exception;
    
    public List<EntidadHospitalaria> obtenerBusquedaEntidad(String cadena) throws Exception;
    
    public boolean eliminarEntidad(EntidadHospitalaria mS) throws Exception;
    
    public boolean actualizarEstatusEntidad(EntidadHospitalaria mS)throws Exception;
    
    public EntidadHospitalariaExtended obtenerEntidadHospital() throws Exception;        
        
    public EntidadHospitalaria obtenerEntidadHospByIdUsuaurio(String idUsuario) throws Exception;
    
    public List<EntidadHospitalaria> obtenerEntidadesdHospitalActivas() throws Exception;
    
    public List<EntidadHospitalaria> obtenerEntidadesActiasNoEsteIdEntidad(String idEntidadHospitalaria) throws Exception;
    
    public boolean actualizarEntidadHospitalaria(EntidadHospitalaria entidadHospitalaria) throws Exception;
   
}
