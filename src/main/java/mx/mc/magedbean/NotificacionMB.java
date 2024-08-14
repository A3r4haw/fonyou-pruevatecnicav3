package mx.mc.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import mx.mc.init.Constantes;
import mx.mc.model.Notificacion;
import mx.mc.model.Usuario;
import mx.mc.service.NotificacionService;
import mx.mc.util.Mensaje;
import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author hramirez
 */
@Controller
@Scope(value = "session")
public class NotificacionMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificacionMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    private Date fechaActual;
    private String nombreUsuario;

    @Autowired
    private transient NotificacionService notificacionService;

    private List<Notificacion> listNotificacion;
    private Usuario usuarioSelected;
    private int number = 0;
    private String marquesina = "";

    /**
     * Obtiene el usuario logueado y carga todos sus datos y permisos
     */
    @PostConstruct
    public void init() {
        LOGGER.debug("mx.mc.magedbean.NotificacionMB.init()");
        limpia();
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        //TODO: Planear la forma de categorizar los diferentes tipos de mensajes y notificaciones. Temporalmente:
        //      Administradores y Jefes de Área:    idTipoNotificacion = 1
        //      El resto de los usuarios:           idTipoNotificacion = 2
        obtenerNotificaciones(sesion.isAdministrador() || sesion.isJefeArea() ? 1 : 2);
    }

    /**
     * limpia ñas variables
     */
    private void limpia() {
        LOGGER.debug("mx.mc.magedbean.NotificacionMB.limpia()");
        fechaActual = new java.util.Date();
        nombreUsuario = null;
        usuarioSelected = new Usuario();
        marquesina = "";
        listNotificacion = new ArrayList<>();
    }

    public void armaMarquesina() {
        marquesina = "";
        for (Notificacion n : listNotificacion) {
            marquesina += "&bull; " + n.getDescripcion() + "&emsp;";
        }
    }

    public void obtenerNotificaciones(Integer idTipoNotificacion) {
        LOGGER.debug("mx.mc.magedbean.NotificacionMB.obtenerNotificaciones()");
        boolean status = Constantes.INACTIVO;

        try {
            Notificacion noti = new Notificacion();
            if (idTipoNotificacion.equals(2)) // 2-Mostrar notificación a todos los usuarios
            {
                noti.setIdTipoNotificacion(idTipoNotificacion);
            }
            listNotificacion = notificacionService.obtenerLista(noti);
            if (listNotificacion != null && !listNotificacion.isEmpty()) {
                number = listNotificacion.size();
                if (number > 0) {
                    status = Constantes.ACTIVO;
                }
                armaMarquesina();
            }
        } catch (Exception ex) {
            LOGGER.error("Error al Buscar Notificaciones: {}", ex.getMessage());
            Mensaje.showMessage("error", RESOURCES.getString("notificacion.err.buscarNotifica"), null);
        }
        PrimeFaces.current().ajax().addCallbackParam("suena", status);
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Usuario getUsuarioSelected() {
        return usuarioSelected;
    }

    public void setUsuarioSelected(Usuario usuarioSelected) {
        this.usuarioSelected = usuarioSelected;
    }

    public int getNumber() {
        return number;
    }

    public void increment() {
        number++;
    }

    public List<Notificacion> getListNotificacion() {
        return listNotificacion;
    }

    public void setListNotificacion(List<Notificacion> listNotificacion) {
        this.listNotificacion = listNotificacion;
    }

    public String getMarquesina() {
        return marquesina;
    }

    public void setMarquesina(String marquesina) {
        this.marquesina = marquesina;
    }
}
