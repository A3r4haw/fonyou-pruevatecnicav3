/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.List;
import mx.mc.model.AplicationMovil;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author Ulai
 */
public interface AppMovilMapper {
    
    boolean storeApp( AplicationMovil app );
    
    boolean removeApp(@Param("id") String id);
    
    List<AplicationMovil> obtenerLista(@Param("versionName") String versionName, @Param("versionCode") Long versionCode);
    
     boolean updateLista(@Param("id") String id);
     
     boolean updateListaAux();
     
     AplicationMovil obtenerAPK();
}
