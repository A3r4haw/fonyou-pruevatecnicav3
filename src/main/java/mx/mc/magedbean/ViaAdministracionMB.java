/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.mc.magedbean;

import java.io.Serializable;
import java.util.Date;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.lazy.ViaAdministracionLazy;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.Usuario;
import mx.mc.model.ViaAdministracion;
import mx.mc.service.ViaAdministracionService;
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
public class ViaAdministracionMB implements Serializable {
    
    @Autowired
    private transient ViaAdministracionService viaAdministracionService;
    
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(ViaAdministracionMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private PermisoUsuario permiso;
    private boolean btnNew;
    private String cadenaBusqueda;
    private ParamBusquedaReporte paramBusquedaReporte;
    private ViaAdministracionLazy viaAdministracionLazy;
    private boolean message;
    private Usuario usuarioSelect;
    private ViaAdministracion viaAdministracionSelect;
    private boolean statusProcess;
    private boolean status;
    private boolean actualizar;
    private String nombreViaAnterior;
    
    @PostConstruct
    public void init() {
        LOGGER.trace("Vía Administración");
        
        initialize();        
        permiso = Comunes.obtenerPermisos(Transaccion_Enum.VIAADMINISTRACION.getSufijo());        
        if (permiso.isPuedeCrear()) {
            btnNew = Constantes.INACTIVO;
        }
        if(permiso.isPuedeVer()) {
            buscarViaAdministracion();
        }
    }
    
    private void initialize() {
        this.setCadenaBusqueda("");
    
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        usuarioSelect = sesion.getUsuarioSelected();
        btnNew = Constantes.INACTIVO;
        paramBusquedaReporte = new ParamBusquedaReporte();
        viaAdministracionSelect = new ViaAdministracion();
        status = Constantes.INACTIVO;
        actualizar = Constantes.INACTIVO;
    }

    public void buscarViaAdministracion() {
        LOGGER.trace("Buscando conincidencias de vía administración: {}", cadenaBusqueda);
        try {
            paramBusquedaReporte.setNuevaBusqueda(true);
            paramBusquedaReporte.setCadenaBusqueda(cadenaBusqueda);
            if (paramBusquedaReporte.getCadenaBusqueda().equals("")) {
                paramBusquedaReporte.setCadenaBusqueda(null);
            }

            viaAdministracionLazy = new ViaAdministracionLazy(viaAdministracionService, paramBusquedaReporte);

            LOGGER.debug("Resultados: {}", viaAdministracionLazy.getTotalReg());

        } catch (Exception e) {
            LOGGER.error("Error al buscar Vía Administraciones: {}", e.getMessage());
        }
    }
    
    public void crearViaAdministracion() {
        try {
            if (permiso.isPuedeCrear()) {
                viaAdministracionSelect = new ViaAdministracion();
                status = Constantes.INACTIVO;
                actualizar = Constantes.INACTIVO;
                Integer idViaAdministracion = viaAdministracionService.obtenerSiguiente();
                viaAdministracionSelect.setIdViaAdministracion(idViaAdministracion);
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString("viaAdmon.error.sinPermisoCrear"), null);
            }
            
        } catch(Exception ex) {
            LOGGER.error("Ocurrio un error al abrir modal para crear una vía de administración:  " + ex.getMessage());
        }
    }
    
    public void guardarViaAdministracion() {
        status = Constantes.INACTIVO;
        boolean res;
        try {
            if (permiso.isPuedeCrear()) {
                String valida = validarDatosForm();
                if(valida.isEmpty()) {
                    ViaAdministracion viaAdmon = new ViaAdministracion();
                    viaAdmon.setNombreViaAdministracion( viaAdministracionSelect.getNombreViaAdministracion());
                    ViaAdministracion existeVia = viaAdministracionService.obtener(viaAdmon);
                    //String clave = viaAdministracionService.validaNombreExistenteVia(viaAdministracionSelect.getNombreViaAdministracion());
                    if(existeVia == null) {                        
                        viaAdministracionSelect.setInsertUsuarioId(usuarioSelect.getIdUsuario());
                        viaAdministracionSelect.setInsertFecha(new Date());

                        res = viaAdministracionService.insertar(viaAdministracionSelect);

                        if(res) {
                            Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("viaAdmon.exito.guardar"), null);
                            status = Constantes.ACTIVO;
                        }
                    } else {
                        Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("viaAdmon.error.existeNombreVia"), null);
                    } 
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, valida, null);
                }                               
            } else {
                LOGGER.error("No tiene permisos el usuario para crear vías de administración");
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("viaAdmon.error.sinPermiso"), null);
            }
            PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
        } catch(Exception ex) {
            LOGGER.error("Ocurrio un error al intentar guardar la vía de administración:  " + ex.getMessage());
        }
    }
    
    public String validarDatosForm() {
        String valida = "";
               
        if(viaAdministracionSelect.getNombreViaAdministracion().isEmpty() || viaAdministracionSelect.getNombreViaAdministracion()== null) {
            valida = RESOURCES.getString("viaAdmon.error.nombreObligatorio");
        }
        
        return valida;
    }
    
    public void obtenerViaAdministracion(Integer idViaAdministracion) {
        try {
            if (permiso.isPuedeVer() && idViaAdministracion != null ) {
                this.setViaAdministracionSelect(viaAdministracionService.obtenerPorId(idViaAdministracion));
                if(viaAdministracionSelect != null)
                    nombreViaAnterior = viaAdministracionSelect.getNombreViaAdministracion();
                actualizar = Constantes.ACTIVO;
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString("viaAdmon.error.sinPermiso"), null);
            }
        } catch(Exception ex) {
            LOGGER.error("Ocurrio un error al obtener la vía de administración:  " + ex.getMessage());            
        }
    }
    
    public void cambiarEstatusViaAdmon(Integer idViaAdmon, boolean status) {
        try {
            if (permiso.isPuedeEditar()) {
                ViaAdministracion unaViaAdmon = new ViaAdministracion();
                unaViaAdmon.setIdViaAdministracion(idViaAdmon);
                unaViaAdmon.setUpdateUsuarioId(usuarioSelect.getIdUsuario());
                unaViaAdmon.setUpdateFecha(new Date());
                if (status) {
                    unaViaAdmon.setActiva(Constantes.INACTIVO);
                } else {
                    unaViaAdmon.setActiva(Constantes.ACTIVO);
                }
                boolean res = viaAdministracionService.actualizar(unaViaAdmon);
                if(res){
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("viaAdmon.exito.cambioEstatus"), null);
                }
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString("viaAdmon.error.sinPermiso"), null);
            }
        } catch (Exception ex) {
            LOGGER.error("Error al cambiar el estatus de la vía de administración: {}", ex.getMessage());
        }
    }
    
    public void actualizarViaAdministracion() {
        status = Constantes.INACTIVO;
        boolean res;
        try {
            if(permiso.isPuedeEditar()) {
                String existeNombre = null;
                //Se valida que el nombre de la via de admon no sea el mismo para validar si existe
                if(!nombreViaAnterior.equalsIgnoreCase(viaAdministracionSelect.getNombreViaAdministracion()))
                    existeNombre = viaAdministracionService.validaNombreExistenteVia(viaAdministracionSelect.getNombreViaAdministracion());
                
                if(existeNombre == null) {
                    viaAdministracionSelect.setUpdateFecha(new Date());
                    viaAdministracionSelect.setUpdateUsuarioId(usuarioSelect.getIdUsuario());
                    res = viaAdministracionService.actualizar(viaAdministracionSelect);

                    if(res) {
                        Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("viaAdmon.exito.actualizar"), null);
                        status = Constantes.ACTIVO;
                    }
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("viaAdmon.error.existeNombreVia"), null);
                }                               
            } else {
                LOGGER.error("No tiene permisos el usuario para editar una vía de administración");
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("viaAdmon.error.sinPermiso"), null);
            }
            PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
        } catch (Exception ex) {
            LOGGER.error("Error al actualizar la vía de administración: {}", ex.getMessage());
        }
    }

    public ViaAdministracionService getViaAdministracionService() {
        return viaAdministracionService;
    }

    public void setViaAdministracionService(ViaAdministracionService viaAdministracionService) {
        this.viaAdministracionService = viaAdministracionService;
    }
    
    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

    public boolean isBtnNew() {
        return btnNew;
    }

    public void setBtnNew(boolean btnNew) {
        this.btnNew = btnNew;
    }

    public String getCadenaBusqueda() {
        return cadenaBusqueda;
    }

    public void setCadenaBusqueda(String cadenaBusqueda) {
        this.cadenaBusqueda = cadenaBusqueda;
    }

    public boolean isStatusProcess() {
        return statusProcess;
    }

    public void setStatusProcess(boolean statusProcess) {
        this.statusProcess = statusProcess;
    }

    public ViaAdministracionLazy getViaAdministracionLazy() {
        return viaAdministracionLazy;
    }

    public void setViaAdministracionLazy(ViaAdministracionLazy viaAdministracionLazy) {
        this.viaAdministracionLazy = viaAdministracionLazy;
    }

    public boolean isMessage() {
        return message;
    }

    public void setMessage(boolean message) {
        this.message = message;
    }

    public ViaAdministracion getViaAdministracionSelect() {
        return viaAdministracionSelect;
    }

    public void setViaAdministracionSelect(ViaAdministracion viaAdministracionSelect) {
        this.viaAdministracionSelect = viaAdministracionSelect;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isActualizar() {
        return actualizar;
    }

    public void setActualizar(boolean actualizar) {
        this.actualizar = actualizar;
    }
        
}
