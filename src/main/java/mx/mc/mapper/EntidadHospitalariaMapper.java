/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.List;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.EntidadHospitalariaExtended;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author mcalderon
 */
public interface EntidadHospitalariaMapper extends GenericCrudMapper<EntidadHospitalaria, String>{
    
    EntidadHospitalaria obtenerEntidadById(@Param("idEntidadHospitalaria") String idEntidadHospitalaria);
    
    public boolean insertEntidadHospTable(EntidadHospitalaria entidadhosp);
    
    List<EntidadHospitalaria> obtenerBusquedaEntidad(@Param("cadena") String cadena); 
    
    public boolean eliminarEntidad(EntidadHospitalaria mS);
    
    public boolean actualizarEstatusEntidad(EntidadHospitalaria mS);
    
    EntidadHospitalariaExtended obtenerEntidadHospital();    
    
    EntidadHospitalaria obtenerEntidadHospByIdUsuaurio(String idUsuario);
    
    List<EntidadHospitalaria> obtenerEntidadesdHospitalActivas();
    
    List<EntidadHospitalaria> obtenerEntidadesActiasNoEsteIdEntidad(@Param("idEntidadHospitalaria") String idEntidadHospitalaria);
    
    boolean actualizarEntidadHospitalaria(EntidadHospitalaria entidadhosp);
}
