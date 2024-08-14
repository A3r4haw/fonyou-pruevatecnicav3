/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.magedbean;

import mx.mc.model.TemplateEtiqueta;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.Usuario;
import mx.mc.service.TemplateEtiquetaService;
import mx.mc.util.Comunes;
import mx.mc.util.Mensaje;
import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author bbautista
 */
@Controller
@Scope(value = "view")
public class TemplateEtiquetaMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateEtiquetaMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private boolean activeBtnSave;
    private String busquedaSolicitud;
    private Integer numeroRegistros;
    private Usuario currentSesionUser;
    private PermisoUsuario permiso;
    private TemplateEtiqueta templateEtiqueta;
    private List<TemplateEtiqueta> templatesList;    
    
    @Autowired
    private transient TemplateEtiquetaService templateService;

    @PostConstruct
    public void init() {
        initialize();
        permiso = Comunes.obtenerPermisos(Transaccion_Enum.TEMPLATE.getSufijo());
        buscarTemplate();
    }

    private void initialize() {
        templateEtiqueta = new TemplateEtiqueta();
        templatesList = new ArrayList<>();
        busquedaSolicitud = "";
        numeroRegistros = 0;
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");

        currentSesionUser = sesion.getUsuarioSelected();
    }        

    public void buscarTemplate() {
        LOGGER.trace("Buscando Impresoras..");
        try {
            templatesList = templateService.obtenerTemplateName(busquedaSolicitud);
            numeroRegistros = templatesList.size();
        } catch (Exception e) {
            LOGGER.error("Error al buscar Templates: {}", e.getMessage());
        }
    }

    public void crearTemplate() {
        templateEtiqueta = new TemplateEtiqueta();
        activeBtnSave = Constantes.INACTIVO;
    }

    public void obtenerTemplate(String idTemp) {
        try {
            templateEtiqueta = new TemplateEtiqueta();
            templateEtiqueta = templateService.obtenerById(idTemp);

        } catch (Exception ex) {
            LOGGER.error("Error al obtener el template: {}", ex.getMessage());
        }
    }

    public void saveTemplate() {
        boolean status = Constantes.INACTIVO;
        try {
            if (templateEtiqueta != null) {
                
                if(templateEtiqueta.getIdTemplate()==null){                                
                    templateEtiqueta.setIdTemplate(Comunes.getUUID());
                    templateEtiqueta.setInsertFecha(new Date());
                    templateEtiqueta.setInsertIdUsuario(currentSesionUser.getIdUsuario());

                    if (templateService.insertar(templateEtiqueta)) {
                        status = Constantes.ACTIVO;
                    } else {
                        status = Constantes.INACTIVO;
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("template.error.guardar"), null);
                    }
                }else{                    
                    templateEtiqueta.setUpdateFecha(new Date());
                    templateEtiqueta.setUpdateIdUsuario(currentSesionUser.getIdUsuario());
                    
                    if(templateService.actualizar(templateEtiqueta))
                        status = Constantes.ACTIVO;
                    else{
                        status = Constantes.INACTIVO;
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("template.error.guardar"), null);
                    }
                }
                
                if(status){
                    templatesList = templateService.obtenerListaAll();
                    numeroRegistros = templatesList.size();
                    Mensaje.showMessage("Info", RESOURCES.getString("template.info.guardado"), null);
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al guardar el Template: {}", ex.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public void eliminarTemplate(String idTemp){
        try{
            if(templateService.delete(idTemp)){
                templatesList = templateService.obtenerListaAll();
                numeroRegistros = templatesList.size();
                Mensaje.showMessage("Info", RESOURCES.getString("template.info.eliminado"), null);
            }else{             
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("template.error.eliminado"), null);
            }
                
        }catch(Exception ex){
            LOGGER.error("Error al eliminar el template: {}", ex.getMessage());
        }
    }
    
    public void reviewSave() {
        if (templateEtiqueta != null) {
            if (templateEtiqueta.getNombre() == null) {
                activeBtnSave = Constantes.INACTIVO;
            } else if (templateEtiqueta.getTipo() == null) {
                activeBtnSave = Constantes.INACTIVO;
            } else if (templateEtiqueta.getContenido() == null) {
                activeBtnSave = Constantes.INACTIVO;
            } else {
                activeBtnSave = Constantes.ACTIVO;
            }
        } else {
            activeBtnSave = Constantes.INACTIVO;
        }
    }

    public boolean isActiveBtnSave() {
        return activeBtnSave;
    }

    public void setActiveBtnSave(boolean activeBtnSave) {
        this.activeBtnSave = activeBtnSave;
    }

    public String getBusquedaSolicitud() {
        return busquedaSolicitud;
    }

    public void setBusquedaSolicitud(String busquedaSolicitud) {
        this.busquedaSolicitud = busquedaSolicitud;
    }

    public Integer getNumeroRegistros() {
        return numeroRegistros;
    }

    public void setNumeroRegistros(Integer numeroRegistros) {
        this.numeroRegistros = numeroRegistros;
    }

    public TemplateEtiqueta getTemplateEtiqueta() {
        return templateEtiqueta;
    }

    public void setTemplateEtiqueta(TemplateEtiqueta templateEtiqueta) {
        this.templateEtiqueta = templateEtiqueta;
    }

    public List<TemplateEtiqueta> getTemplatesList() {
        return templatesList;
    }

    public void setTemplatesList(List<TemplateEtiqueta> templatesList) {
        this.templatesList = templatesList;
    }

    public Usuario getCurrentSesionUser() {
        return currentSesionUser;
    }

    public void setCurrentSesionUser(Usuario currentSesionUser) {
        this.currentSesionUser = currentSesionUser;
    }

    public TemplateEtiquetaService getTemplateService() {
        return templateService;
    }

    public void setTemplateService(TemplateEtiquetaService templateService) {
        this.templateService = templateService;
    }    

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }
    
}
