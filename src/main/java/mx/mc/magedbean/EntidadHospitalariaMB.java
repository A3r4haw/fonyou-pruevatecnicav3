package mx.mc.magedbean;

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
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.Estructura;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.Usuario;
import mx.mc.service.EntidadHospitalariaService;
import mx.mc.service.EstructuraService;
import mx.mc.util.Comunes;
import mx.mc.util.Mensaje;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author olozada
 */
@Controller
@Scope(value = "view")
public class EntidadHospitalariaMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(EntidadHospitalariaMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);    

    private boolean administrador;
    private Usuario usuarioSession;
    private ParamBusquedaReporte paramBusquedaReporte;

    private EntidadHospitalaria entidadSelected;
    private int numeroRegistros;
    private String cadenaBusqueda;
    private boolean btnNew;
    private boolean message;
    private String valorestatus;
    private boolean status;
    private PermisoUsuario permiso;
    private boolean editarEntidad;
    @Autowired
    private transient EstructuraService estructuraService;

    @Autowired
    private transient EntidadHospitalariaService entidadHospitalariaService;
    private List<EntidadHospitalaria> entidadHospList;
    private List<EntidadHospitalaria> entidadHospList2;

    @PostConstruct
    public void init() {

        this.setAdministrador(Comunes.isAdministrador());
        this.setUsuarioSession(Comunes.obtenerUsuarioSesion());
        initialize();
        permiso = Comunes.obtenerPermisos(Transaccion_Enum.ENTIDADHOSPITALARIA.getSufijo());
        buscarEntidad();
    }

    public void initialize() {
        this.setCadenaBusqueda("");
        this.paramBusquedaReporte = new ParamBusquedaReporte();
        entidadHospList = new ArrayList<>();
        entidadSelected = new EntidadHospitalaria();
        editarEntidad = false;

    }

    /**
     * Obtiene Sesion de Usuario
     *
     * @return
     */
    public Usuario obtenerUsuarioSesion() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        return sesion.getUsuarioSelected();
    }

    public void eliminarEntidad(String idEntidad, String domicilio) {
        status = Constantes.INACTIVO;
        try {

            EntidadHospitalaria mS = new EntidadHospitalaria();
            mS.setIdEntidadHospitalaria(idEntidad);
            mS.setDomicilio(domicilio);
            List<Estructura> listaEstructura = estructuraService.obtenerEstructurasPorIdEntidad(idEntidad);
            if(!listaEstructura.isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("entidadHospitalaria.err.eliminaEntidad"), null);
            } else {
                status = entidadHospitalariaService.eliminarEntidad(mS);

                if (status) {
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("ententidadHopsitalaria.info.eliminar"), null);
                    limpiarDialogo();
                    buscarEntidad();
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("ententidadHopsitalaria.err.eliminar"), null);
                }
            }
            
        } catch (Exception ex) {
            LOGGER.error("Error al actualizar status Entidada: {}", ex.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    /**
     * Metodo para buscar la entidad que se selecciona para poder editar la entidad
     * @param idEntidad 
     */
    public void obtenerEntidad(String idEntidad) {
        LOGGER.trace("Buscar la entidad con idEntidad:  " , idEntidad);
        try{
            entidadSelected = entidadHospitalariaService.obtenerEntidadById(idEntidad);
            editarEntidad = true;
        } catch(Exception ex) {
            LOGGER.error("Error al buscar entidad por idEntidad:  " + ex.getMessage());
        }
    }
    
    public void buscarEntidad() {
        LOGGER.trace("Buscando conincidencias de: {}", cadenaBusqueda);
        try {
            entidadHospList = entidadHospitalariaService.obtenerBusquedaEntidad(cadenaBusqueda);
            numeroRegistros = entidadHospList.size();
            editarEntidad = false;
            } catch (Exception e) {
            LOGGER.error("Error al buscar Entidad: {}", e.getMessage());
        }
    }

    public boolean validaEntidadActiva() {
        boolean activa = Constantes.INACTIVO;
        try {
            List<EntidadHospitalaria> listaEntidades = entidadHospitalariaService.obtenerEntidadesdHospitalActivas();
            if(listaEntidades.isEmpty()) {
                activa = Constantes.ACTIVO;
            }
        } catch(Exception ex) {
            LOGGER.error("Ocurrio un error al buscar entidades activas " + ex.getMessage());
        }
        
        return activa;
    }
    
    public void statusEntidad(String idEntidad, boolean estatus) {
        try {
            if (permiso.isPuedeEditar()) {
                EntidadHospitalaria mS = new EntidadHospitalaria();
                mS.setIdEntidadHospitalaria(idEntidad);
                mS.setUpdateFecha(new java.util.Date());
                mS.setUpdateIdUsuario(usuarioSession.getIdUsuario());
                if (!estatus) {                    
                    if(validaEntidadActiva()) {
                        mS.setEstatus(Constantes.ACTIVO);
                        estructuraService.actualizarEntidadTodas(idEntidad, usuarioSession.getIdUsuario(), new java.util.Date());
                    } else {
                        Mensaje.showMessage("Warn", RESOURCES.getString("entidadHospitalaria.err.activar"), null);
                        return;
                    }                  
                } else {                    
                    mS.setEstatus(Constantes.INACTIVO);
                }
                entidadHospitalariaService.actualizarEstatusEntidad(mS);
                Mensaje.showMessage(Constantes.MENSAJE_INFO, "Se actualizo la entidad correctamente", null);
                for (EntidadHospitalaria view : entidadHospList) {
                    if (view.getIdEntidadHospitalaria().equals(idEntidad)) {
                        view.setEstatus(mS.getEstatus());
                        break;
                    }
                }
            } else {
                Mensaje.showMessage("Warn", RESOURCES.getString("medicamento.warn.sinPermTransac"), null);
            }
        } catch (Exception ex) {
            LOGGER.error("Error al actualizar status Entidada: {}", ex.getMessage());
        }

    }

    public void ingresarEntidadHosp() {
        LOGGER.debug("mx.mc.magedbean.EntidadHospitalariaMB.ingresarEntidadHosp()");
        status = Constantes.INACTIVO;
        try {

            if(editarEntidad) {
                if (permiso.isPuedeEditar()) {
                    entidadSelected.setUpdateFecha(new java.util.Date());
                    entidadSelected.setUpdateIdUsuario(usuarioSession.getIdUsuario());
                    List<EntidadHospitalaria> listEntidadesActivas = new ArrayList<>();
                    if(entidadSelected.getEstatus()) {
                        listEntidadesActivas = entidadHospitalariaService.
                                            obtenerEntidadesActiasNoEsteIdEntidad(entidadSelected.getIdEntidadHospitalaria());
                    }                    
                    if(listEntidadesActivas.isEmpty()) {
                        boolean resp = entidadHospitalariaService.actualizarEntidadHospitalaria(entidadSelected);
                        if(resp) {
                            status = Constantes.ACTIVO;
                            Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("entidadHospitalaria.info.actualizar"), null);
                            buscarEntidad();
                        }
                    } else {
                        Mensaje.showMessage("Warn", RESOURCES.getString("entidadHospitalaria.err.activar"), null);
                        return;
                    }                    
                    
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_WARN,RESOURCES.getString("entidadHospitalaria.err.permEditar"), null);
                    status = Constantes.INACTIVO;
                }
            } else {
                if(permiso.isPuedeCrear()) {
                    if (entidadSelected.getNombre() == null || entidadSelected.getNombre().trim().isEmpty()) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("entidadHopsitalaria.req.nombre"), null);
                    } else if (entidadSelected.getDomicilio() == null || entidadSelected.getDomicilio().trim().isEmpty()) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("entidadHopsitalaria.req.domicilio"), null);
                    } else {
                        entidadSelected.setIdEntidadHospitalaria(Comunes.getUUID());
                        entidadSelected.setInsertFecha(new Date());
                        entidadSelected.setInsertIdUsuario(this.usuarioSession.getIdUsuario());
                        
                        if(entidadSelected.getEstatus()) {
                            if(!validaEntidadActiva()) {
                                Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString("entidadHospitalaria.err.activar"), null);
                                return;                                 
                            }                                                     
                        }
                        status = entidadHospitalariaService.insertEntidadHospTable(entidadSelected);
                        if (status) {
                            Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("entidadHopsitalaria.info.crear"), null);
                            buscarEntidad();                                                        
                        } else {
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("entidadHopsitalaria.err.crear"), null);
                        }                       
                    }
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_WARN,RESOURCES.getString("entidadHospitalaria.err.permEditar"), null);
                    status = Constantes.INACTIVO;
                }                
            }
            
        } catch (Exception ex) {
            LOGGER.info("Error al insertar datos en Tabla EntidadHosp: {}", ex.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);

    }

    public void limpiarDialogo() {
        try {
            entidadSelected = new EntidadHospitalaria();
            editarEntidad = false;
            this.cadenaBusqueda = StringUtils.EMPTY;

        } catch (Exception e) {
            LOGGER.error("Error al limpiar tabla Entidad Hospitalaria: {}", e.getMessage());
        }
    }

    public EstructuraService getEstructuraService() {
        return estructuraService;
    }

    public void setEstructuraService(EstructuraService estructuraService) {
        this.estructuraService = estructuraService;
    }

    public EntidadHospitalariaService getEntidadHospitalariaService() {
        return entidadHospitalariaService;
    }

    public void setEntidadHospitalariaService(EntidadHospitalariaService entidadHospitalariaService) {
        this.entidadHospitalariaService = entidadHospitalariaService;
    }

    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }

    public Usuario getUsuarioSession() {
        return usuarioSession;
    }

    public void setUsuarioSession(Usuario usuarioSession) {
        this.usuarioSession = usuarioSession;
    }

    public ParamBusquedaReporte getParamBusquedaReporte() {
        return paramBusquedaReporte;
    }

    public void setParamBusquedaReporte(ParamBusquedaReporte paramBusquedaReporte) {
        this.paramBusquedaReporte = paramBusquedaReporte;
    }

    public EntidadHospitalaria getEntidadSelected() {
        return entidadSelected;
    }

    public void setEntidadSelected(EntidadHospitalaria entidadSelected) {
        this.entidadSelected = entidadSelected;
    }  

    public List<EntidadHospitalaria> getEntidadHospList() {
        return entidadHospList;
    }

    public void setEntidadHospList(List<EntidadHospitalaria> entidadHospList) {
        this.entidadHospList = entidadHospList;
    }

    public int getNumeroRegistros() {
        return numeroRegistros;
    }

    public void setNumeroRegistros(int numeroRegistros) {
        this.numeroRegistros = numeroRegistros;
    }

    public List<EntidadHospitalaria> getEntidadHospList2() {
        return entidadHospList2;
    }

    public void setEntidadHospList2(List<EntidadHospitalaria> entidadHospList2) {
        this.entidadHospList2 = entidadHospList2;
    }

    public String getCadenaBusqueda() {
        return cadenaBusqueda;
    }

    public void setCadenaBusqueda(String cadenaBusqueda) {
        this.cadenaBusqueda = cadenaBusqueda;
    }

    public boolean isBtnNew() {
        return btnNew;
    }

    public void setBtnNew(boolean btnNew) {
        this.btnNew = btnNew;
    }

    public boolean isMessage() {
        return message;
    }

    public void setMessage(boolean message) {
        this.message = message;
    }

    public String getValorestatus() {
        return valorestatus;
    }

    public void setValorestatus(String valorestatus) {
        this.valorestatus = valorestatus;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

}
