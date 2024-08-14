/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.mc.magedbean;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.lazy.FabricanteLazy;
import mx.mc.model.Fabricante;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.Usuario;
import mx.mc.service.FabricanteService;
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
 * @author gcruz
 */
@Controller
@Scope(value = "view")
public class FabricanteMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(FabricanteMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    private boolean status;
    private String cadenaBusqueda;
    private boolean message;
    private SesionMB sesion;
    private Usuario usuarioSelected;        
    
    @Autowired
    private transient FabricanteService fabricanteService;
    private FabricanteLazy fabricanteLazy;
    private Fabricante fabricanteSelect;
    private PermisoUsuario permiso;
    
     @PostConstruct
    public void init() {
        initialize();
        permiso = Comunes.obtenerPermisos(Transaccion_Enum.FABRICANTE.getSufijo());
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        usuarioSelected = sesion.getUsuarioSelected();
        if (permiso.isPuedeVer()){
            buscarFabricantes();
        }
    }

    private void initialize() {
        cadenaBusqueda = "";
        fabricanteSelect = new Fabricante();
        //listaFabricantes = new ArrayList<>();
        
        message = Constantes.ACTIVO;
        status = Constantes.INACTIVO;
    }
    
    public void buscarFabricantes() {
        LOGGER.trace("mx.mc.magedbean.FabricanteMB.buscarFabricantes()");
        try {
            if (!this.permiso.isPuedeVer()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.accion"), "");
                return;
            }
            if (cadenaBusqueda != null
                    && cadenaBusqueda.trim().isEmpty()) {
                cadenaBusqueda = null;
            }
            fabricanteLazy = new FabricanteLazy(fabricanteService, cadenaBusqueda);
            
            cadenaBusqueda = null;
        } catch(Exception ex) {
            LOGGER.error("Error al buscar fabricantes" + ex.getMessage());
        }
    }
    public void nuevoFabricante() {
        fabricanteSelect = new Fabricante();
    }
    
    public void guardarFabricante() {
        LOGGER.trace("mx.mc.magedbean.FabricanteMB.guardarFabricante()");
        status = false;
        try {
            if(fabricanteSelect.getIdFabricante() != null) {
                fabricanteSelect.setUpdateFecha(new java.util.Date());
                fabricanteSelect.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
                status = fabricanteService.actualizar(fabricanteSelect);
                if(status)
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, "El fabricante se actualizo correctamente.  ", null);
            } else {
                Integer idFabricante = fabricanteService.obtenerSiguienteId();
                fabricanteSelect.setIdFabricante(idFabricante);
                fabricanteSelect.setInsertFecha(new java.util.Date());
                fabricanteSelect.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                fabricanteSelect.setIdEstatus(Constantes.ACTIVOS);
                status = fabricanteService.insertar(fabricanteSelect);
                if(status)
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, "El fabricante se guardo correctamente.  ", null);
            }            
            
        } catch(Exception ex) {
            LOGGER.error("Error al obtener el idFabricante siguiente" + ex.getMessage());
        }
        
        PrimeFaces.current().ajax().addCallbackParam("estatus", status);
        buscarFabricantes();
    }
    
    public void changeStatus(Integer idFabricante, Integer idEstatus) {
        LOGGER.trace("mx.mc.magedbean.FabricanteMB.guardarFabricante()");
        status = false;
        String estatus = "";
        try  {
            Fabricante unFabricante = new Fabricante();
            if (permiso.isPuedeEditar()) {
                unFabricante.setIdFabricante(idFabricante);
                unFabricante.setUpdateFecha(new java.util.Date());
                unFabricante.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
                if (idEstatus > 0) {
                    unFabricante.setIdEstatus(0);
                    estatus = "inactivo";
                } else {
                    unFabricante.setIdEstatus(1);
                    estatus = "activo";
                }
            }
            status = fabricanteService.actualizar(unFabricante);
            if(status)
                Mensaje.showMessage(Constantes.MENSAJE_INFO, "El fabricante se " + estatus, null);
        } catch(Exception ex) {
            LOGGER.error("Error al activar o inactivar el fabricante  " + ex.getMessage());
        }
    }
    
    public void obtenerFabricante(Integer idFabricante) {
        try {
            Fabricante unFabricante = new Fabricante();
            unFabricante.setIdFabricante(idFabricante);
            fabricanteSelect = fabricanteService.obtener(unFabricante);
        } catch(Exception ex) {
            LOGGER.error("Error al intentar obtener el fabricante " +ex.getMessage());
        }
        
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getCadenaBusqueda() {
        return cadenaBusqueda;
    }

    public void setCadenaBusqueda(String cadenaBusqueda) {
        this.cadenaBusqueda = cadenaBusqueda;
    }

    public boolean isMessage() {
        return message;
    }

    public void setMessage(boolean message) {
        this.message = message;
    }

    public FabricanteLazy getFabricanteLazy() {
        return fabricanteLazy;
    }

    public void setFabricanteLazy(FabricanteLazy fabricanteLazy) {
        this.fabricanteLazy = fabricanteLazy;
    }

    public Fabricante getFabricanteSelect() {
        return fabricanteSelect;
    }

    public void setFabricanteSelect(Fabricante fabricanteSelect) {
        this.fabricanteSelect = fabricanteSelect;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }
    
    
}
