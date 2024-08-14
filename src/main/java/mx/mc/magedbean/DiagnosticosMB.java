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
import mx.mc.lazy.DiagnosticosLazy;
import mx.mc.model.Diagnostico;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.Usuario;
import mx.mc.service.DiagnosticoService;
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
public class DiagnosticosMB implements Serializable {
    
    @Autowired
    private transient DiagnosticoService diagnosticoService;
    
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(DiagnosticosMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private PermisoUsuario permiso;
    private boolean btnNew;
    private String cadenaBusqueda;
    private ParamBusquedaReporte paramBusquedaReporte;
    private DiagnosticosLazy diagnosticosLazy;
    private boolean message;
    private Usuario usuarioSelect;
    private Diagnostico diagnosticoSelect;
    private boolean statusProcess;
    private boolean status;
    private boolean actualizar;
    private String claveAnterior;
    
    @PostConstruct
    public void init() {
        LOGGER.trace("Diagnosticos");
        
        initialize();        
        permiso = Comunes.obtenerPermisos(Transaccion_Enum.DIAGNOSTICO.getSufijo());        
        if (permiso.isPuedeCrear()) {
            btnNew = Constantes.INACTIVO;
        }
        if(permiso.isPuedeVer()) {
            buscarDiagnostico();
        }
    }
    
    private void initialize() {
        this.setCadenaBusqueda("");
        
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        usuarioSelect = sesion.getUsuarioSelected();
        btnNew = Constantes.INACTIVO;
        paramBusquedaReporte = new ParamBusquedaReporte();
        diagnosticoSelect = new Diagnostico();
        status = Constantes.INACTIVO;
        actualizar = Constantes.INACTIVO;
    }

    public void buscarDiagnostico() {
        LOGGER.trace("Buscando conincidencias de diagnostico: {}", cadenaBusqueda);
        try {
            paramBusquedaReporte.setNuevaBusqueda(true);
            paramBusquedaReporte.setCadenaBusqueda(cadenaBusqueda);
            if (paramBusquedaReporte.getCadenaBusqueda().equals("")) {
                paramBusquedaReporte.setCadenaBusqueda(null);
            }

            diagnosticosLazy = new DiagnosticosLazy(diagnosticoService, paramBusquedaReporte);

            LOGGER.debug("Resultados: {}", diagnosticosLazy.getTotalReg());

        } catch (Exception e) {
            LOGGER.error("Error al buscar Diagnosticos: {}", e.getMessage());
        }
    }
    
    public void crearDiagnostico() {
        try {
            if (permiso.isPuedeCrear()) {
                diagnosticoSelect = new Diagnostico();
                status = Constantes.INACTIVO;
                actualizar = Constantes.INACTIVO;
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_WARN,RESOURCES.getString("diagnostico.error.sinPermisoCrear"), null);
            }
            
        } catch(Exception ex) {
            LOGGER.error("Ocurrio un error al abrir modal para crear diagnóstico:  " + ex.getMessage());
        }
    }
    
    public void guardarDiagnostico() {
        status = Constantes.INACTIVO;
        boolean res;
        try {
            if (permiso.isPuedeCrear()) {
                String valida = validarDatosForm();
                if(valida.isEmpty()) {
                    String clave = diagnosticoService.validaClaveExistente(diagnosticoSelect.getClave());
                    if(clave == null) {
                        diagnosticoSelect.setIdDiagnostico(Comunes.getUUID());
                        diagnosticoSelect.setInsertIdUsuario(usuarioSelect.getIdUsuario());
                        diagnosticoSelect.setInsertFecha(new Date());

                        res = diagnosticoService.insertar(diagnosticoSelect);

                        if(res) {
                            Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("diagnostico.exito.guardar"), null);
                            status = Constantes.ACTIVO;
                        }
                    } else {
                        Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("diagnostico.error.existeClave"), null);
                    } 
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, valida, null);
                }                               
            } else {
                LOGGER.error("No tiene permisos el usuario para crear diagnósticos");
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("diagnostico.error.sinPermiso"), null);
            }
            PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
        } catch(Exception ex) {
            LOGGER.error("Ocurrio un error al intentar guardar el diagnostico:  " + ex.getMessage());
        }
    }
    
    public String validarDatosForm() {
        String valida = "";
        
        if(diagnosticoSelect.getClave().isEmpty() || diagnosticoSelect.getClave() == null) {
            valida = RESOURCES.getString("diagnostico.error.claveObligatoria");
        }
        if(diagnosticoSelect.getNombre().isEmpty() || diagnosticoSelect.getNombre() == null) {
            valida = valida +" "+ RESOURCES.getString("diagnostico.error.nombreObligatorio");
        }
        
        return valida;
    }
    
    public void obtenerDiagnostico(String idDiagnostico, boolean statusDiag) {
        try {
            claveAnterior = "";
            if (permiso.isPuedeVer() && !idDiagnostico.isEmpty()) {
                this.setDiagnosticoSelect(diagnosticoService.obtenerDiagnosticoPorIdDiag(idDiagnostico));
                if(diagnosticoSelect != null)
                    claveAnterior = diagnosticoSelect.getClave();
                actualizar = Constantes.ACTIVO;
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString("diagnostico.error.sinPermiso"), null);
            }
        } catch(Exception ex) {
            LOGGER.error("Ocurrio un error al obtener el diagnostico:  " + ex.getMessage());            
        }
    }
    
    public void cambiarEstatusDiagnostico(String idDiagnostico, boolean status) {
        try {
            if (permiso.isPuedeEditar()) {
                Diagnostico unDiagnostico = new Diagnostico();
                unDiagnostico.setIdDiagnostico(idDiagnostico);
                unDiagnostico.setUpdateIdUsuario(usuarioSelect.getIdUsuario());
                unDiagnostico.setUpdateFecha(new Date());
                if (status) {
                    unDiagnostico.setActivo(Constantes.INACTIVO);
                } else {
                    unDiagnostico.setActivo(Constantes.ACTIVO);
                }
                boolean res = diagnosticoService.actualizar(unDiagnostico);
                if(res){
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("diagnostico.exito.cambioEstatus"), null);
                }
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString("diagnostico.error.sinPermiso"), null);
            }
        } catch (Exception ex) {
            LOGGER.error("Error al cambiar el estatus del diagnóstico: {}", ex.getMessage());
        }
    }
    
    public void actualizarDiagnostico() {
        status = Constantes.INACTIVO;
        boolean res;
        try {
            if(permiso.isPuedeEditar()) {
                String clave = null;
                //Se valida que al momento de modificar la clave si es la misma no valida
                if(!claveAnterior.equalsIgnoreCase(diagnosticoSelect.getClave())) 
                    clave = diagnosticoService.validaClaveExistente(diagnosticoSelect.getClave());                
                diagnosticoSelect.setUpdateFecha(new Date());
                diagnosticoSelect.setUpdateIdUsuario(usuarioSelect.getIdUsuario());
                if(clave == null) {                    
                    res = diagnosticoService.actualizar(diagnosticoSelect);
                    
                    if(res) {
                        Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("diagnostico.exito.actualizar"), null);
                        status = Constantes.ACTIVO;
                    }
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("diagnostico.error.existeClave"), null);
                }
            } else {
                LOGGER.error("No tiene permisos el usuario para crear diagnósticos");
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("diagnostico.error.sinPermiso"), null);
            }
            PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
        } catch (Exception ex) {
            LOGGER.error("Error al actualizar el diagnóstico: {}", ex.getMessage());
        }
    }
    
    public DiagnosticoService getDiagnosticoService() {
        return diagnosticoService;
    }

    public void setDiagnosticoService(DiagnosticoService diagnosticoService) {
        this.diagnosticoService = diagnosticoService;
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

    public DiagnosticosLazy getDiagnosticosLazy() {
        return diagnosticosLazy;
    }

    public void setDiagnosticosLazy(DiagnosticosLazy diagnosticosLazy) {
        this.diagnosticosLazy = diagnosticosLazy;
    }

    public boolean isMessage() {
        return message;
    }

    public void setMessage(boolean message) {
        this.message = message;
    }

    public Diagnostico getDiagnosticoSelect() {
        return diagnosticoSelect;
    }

    public void setDiagnosticoSelect(Diagnostico diagnosticoSelect) {
        this.diagnosticoSelect = diagnosticoSelect;
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
