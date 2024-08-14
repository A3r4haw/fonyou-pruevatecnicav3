/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.model.AplicationMovil;

/**
 *
 * @author Ulai
 */
public interface AppMovilService {
    
    boolean storeApp(AplicationMovil app) throws Exception;
    
    boolean removeApp(String idApp) throws Exception;
    
    List<AplicationMovil> obtenerLista(String versionName, Long versionCode) throws Exception;
    
    boolean updateLista(String id) throws Exception;
    
    AplicationMovil obtenerAPK() throws Exception;
}
