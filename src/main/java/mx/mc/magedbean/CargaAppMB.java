/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.magedbean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import mx.mc.lazy.AplicationMovilLazy;
import mx.mc.init.Constantes;
import mx.mc.model.AplicationMovil;
import mx.mc.service.AppMovilService;
import net.dongliu.apk.parser.ApkFile;
import net.dongliu.apk.parser.bean.ApkMeta;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
 
/**
 *
 * @author unava
 */
@Controller
@RequestScoped
@Scope(value = "view")
public class CargaAppMB  implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(MedicamentoMB.class);

    private List<AplicationMovil> appMovilList = new ArrayList<>();
    private String pathDefinitionTemp;
    private boolean estatus;           
    private String error;
    private boolean activo;
    private boolean noYes;
    private String idVersion;
    private final Map<String, Serializable> filterValues = new HashMap<>();
    @Autowired
    private transient AppMovilService appMovilService;
    private AplicationMovilLazy aplicationMovilLazy;
    
    @PostConstruct
    public void init() {
        error = "Ya se ha cargado esta versión de la aplicación !";
    } 
    
    public void list(){
       try {
            appMovilList = appMovilService.obtenerLista(null, null);
            aplicationMovilLazy = new AplicationMovilLazy(appMovilList);
            if (appMovilList.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aún no hay ninguna aplicación en la nube", ""));
            }
            LOGGER.debug("Resultados: {}", appMovilList.size());
        } catch (Exception e1) {
            LOGGER.error("Error en list", e1);
        }
   }
   
   public void confirm(String idVersion){
        this.idVersion = idVersion;
        estatus = true;
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, estatus);
   }
   
   public void yes(){
       estatus = true;
       try {
           filterValues.toString();
           boolean active = appMovilService.updateLista(idVersion);
           if (active) {
                appMovilList = appMovilService.obtenerLista(null, null);
                aplicationMovilLazy = new AplicationMovilLazy(appMovilList);
                LOGGER.debug("Resultados: {}", appMovilList.size());
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cambio de versión con exito"));
                estatus = false;
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("hubo un error"));
                estatus = false;
            } 
        } catch (Exception e1) {
            LOGGER.error("Error en yes", e1);
        }
       PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, estatus);
   }
  
   public void no(){
       try {
            appMovilList = appMovilService.obtenerLista(null, null);
            aplicationMovilLazy = new AplicationMovilLazy(appMovilList);
            estatus = false;
        } catch (Exception e1) {
            LOGGER.error("Error en no", e1);
            estatus = false;
        }
       FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("No se realizó ninguna configuración"));
       PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, estatus);
   }
    
    private String createFile(byte[] bites, String name, String version, Long versionCode)throws Exception {
        if (bites != null) {
            name = name.replaceAll(" ", "_");
            version = version.replaceAll(" ", "_");
            String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/") + Constantes.PATH_APP ;
            File f = new File(path);
            f.mkdirs();
            String pathDefinition = path + name + "_" + version + "_vc" + versionCode.toString();
            File fileE = new File(pathDefinition);
            if (!fileE.exists()) {
                try (FileOutputStream fos = new FileOutputStream(pathDefinition)) {
                    fos.write(bites);
                    fos.flush();
                }
                return pathDefinition;
            } else {
                return "null";
            }
        }
        return "";
    }
    
    private String createFileTemp(byte[] bites, String nombre) throws IOException {
        if (bites != null) {
            nombre = nombre.replaceAll(" ", "_");
            String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/") + Constantes.PATH_APP_TMP;
            File f = new File(path);
            f.mkdirs();
            pathDefinitionTemp = path + nombre;
            try (FileOutputStream fos = new FileOutputStream(pathDefinitionTemp)) {
                fos.write(bites);
                fos.flush();
            }
            return pathDefinitionTemp;
        }
        return "";
    }
    
    private boolean fileExistsBD(String apkFile, Long versionCode) {
        try {
            appMovilList = appMovilService.obtenerLista(apkFile, versionCode);
        } catch(Exception ex) {
            LOGGER.error("ERROR al obtener la lista: {}", ex.getMessage());
        }
        if (appMovilList != null && !appMovilList.isEmpty()) {
            File fileE = new File(pathDefinitionTemp);
            if (!fileE.exists()){fileE.delete();}
            return true;
        } else {
            return false;
        }
   }
    
    public void apkFileUpload(FileUploadEvent event) {                    
        try {
            AplicationMovil appMovil;
            ApkMeta apkMeta;
            UploadedFile upfile = event.getFile();
            String appTemp = createFileTemp(upfile.getContents(), upfile.getFileName());
            try (ApkFile apkFileTemp = new ApkFile(appTemp)) {
                apkMeta = apkFileTemp.getApkMeta();
            }
            String app = createFile(upfile.getContents(), apkMeta.getLabel(), apkMeta.getVersionName(), apkMeta.getVersionCode());
            if (!app.equals("null")) {
                if (!fileExistsBD(apkMeta.getVersionName(), apkMeta.getVersionCode())) {
                    appMovil =  new AplicationMovil(apkMeta.getLabel(),apkMeta.getVersionCode().intValue(), apkMeta.getVersionName(), apkMeta.getPackageName(), false);
                    boolean process = appMovilService.storeApp(appMovil);
                    if (process) {
                        appMovilList = appMovilService.obtenerLista(null, null);
                        aplicationMovilLazy = new AplicationMovilLazy(appMovilList);
                    }
                    File fileE = new File(pathDefinitionTemp);
                    if (fileE.exists()){fileE.delete();}
                } else {
                    File fileE = new File(pathDefinitionTemp);
                    if (fileE.exists()) {fileE.delete();}
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR al cargar la aplicación: "+ error +"", ""));
                }
            } else {
                File fileE = new File(pathDefinitionTemp);
                if(fileE.exists()){fileE.delete();}
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR al cargar la aplicación: "+ error +"", ""));
            }
        }catch(Exception ex){
            File fileE = new File(pathDefinitionTemp);
            if(fileE.exists()){fileE.delete();}
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR al cargar la aplicación: "+ ex.getMessage()+"", ""));
                LOGGER.error("ERROR al cargar la aplicación: {}", ex.getMessage());
        }
    }  
    
    public AplicationMovilLazy getAplicationMovilLazy() {
        return aplicationMovilLazy;
    }

    public void setAplicationMovilLazy(AplicationMovilLazy aplicationMovilLazy) {
        this.aplicationMovilLazy = aplicationMovilLazy;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public boolean isNoYes() {
        return noYes;
    }

    public void setNoYes(boolean noYes) {
        this.noYes = noYes;
    }

    public boolean isEstatus() {
        return estatus;
    }

    public void setEstatus(boolean estatus) {
        this.estatus = estatus;
    }
}
