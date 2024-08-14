/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.mc.mapper.AppMovilMapper;
import mx.mc.model.AplicationMovil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ulai
 */
@Service
public class AppMovilServiceImpl implements AppMovilService {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(AppMovilServiceImpl.class);
    
    @Autowired
    private AppMovilMapper appMapper; 
    private boolean appVal = false;
    
    @Override
    public boolean storeApp(AplicationMovil app) throws Exception {
        try {
            return appMapper.storeApp(app);
        } catch (Exception e) {
            throw new Exception("Error al guardar la aplicación" + e.getMessage());
        }
    }
    
    @Override
    public boolean removeApp(String id) throws Exception {
        try {
            appVal = appMapper.removeApp(id);
        } catch (Exception e) {
            throw new Exception("Error al remover la aplicación" + e.getMessage());
        }

        return appVal;
    }
    
    @Override
    public List<AplicationMovil> obtenerLista(String versionName, Long versionCode){
        List<AplicationMovil>  appList = new ArrayList<>();
        try {
            appList = appMapper.obtenerLista(versionName, versionCode);
        } catch (Exception e) {
                Logger.getLogger(AppMovilServiceImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return appList;
    }
    
    @Override
    public boolean updateLista(String id) throws Exception {
        
        try {
            Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    appVal = appMapper.updateListaAux();
                }
            });
            thread1.start();
            thread1.join();
            Thread thread2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    appVal = appMapper.updateLista(id);
                }
            });
            thread2.start();
            thread2.join();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            Thread.currentThread().interrupt();
            throw new Exception("Error al remover la aplicación" + e.getMessage());
        }
        return appVal;
    }

    @Override
    public AplicationMovil obtenerAPK(){
        AplicationMovil appList = new AplicationMovil();
        try {
            appList = appMapper.obtenerAPK();
        } catch (Exception e) {
                Logger.getLogger(AppMovilServiceImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return appList;
    }
}
