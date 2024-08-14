package mx.mc.magedbean;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.event.AjaxBehaviorEvent;
import mx.mc.enums.PlantillaCorreo_Enum;
import mx.mc.init.Constantes;
import mx.mc.init.CustomWebSecurityConfigurerAdapter;
import mx.mc.model.AdjuntoExtended;
import mx.mc.model.Config;
import mx.mc.model.PlantillaCorreo;
import mx.mc.model.Usuario;
import mx.mc.service.ConfigService;
import mx.mc.service.PlantillaCorreoService;
import mx.mc.service.UsuarioService;
import mx.mc.util.Comunes;
import mx.mc.util.EnviaCorreoUtil;
import mx.mc.util.FechaUtil;
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
 * @author Cervanets
 */
@Scope("view")
@Controller
public class RecuperarContrasenaMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private final Logger LOGGER = LoggerFactory.getLogger(RecuperarContrasenaMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    @Autowired
    private transient UsuarioService usuarioService;

    @Autowired
    private transient PlantillaCorreoService plantillaCorreoService;

    @Autowired
    protected transient EnviaCorreoUtil enviaCorreoUtil;

    private transient List<Config> configList;

    @Autowired
    private transient ConfigService configService;

    private String correo;
    private String nombreUsuario;
    private Usuario usuario;
    private String contrasena1;
    private String contrasena2;
    private boolean mostrarDatos;

    /**
     * Postconsrtuctor que gestiona lo necesario
     */
    @PostConstruct
    public void init() {
        LOGGER.trace("mx.mc.magedbean.RecuperarContrasenaMB.init()");
        inicializaVariables();
        this.mostrarDatos = Constantes.ACTIVO;
    }

    private void inicializaVariables() {
        this.correo = StringUtils.EMPTY;
        this.nombreUsuario = StringUtils.EMPTY;
        this.usuario = new Usuario();
    }

    /**
     * Obtiene los parámetros del sistema
     */
    private void obtenerDatosSistema() {
        LOGGER.debug("mx.mc.init.RecuperarContrasenaMB.obtenerDatosSistema()");
        Config c = new Config();
        c.setActiva(Constantes.ACTIVO);
        try {
            configList = configService.obtenerLista(c);
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString("sys.config.err"), ex);
        }
    }

    /**
     * Obtiene el valor para que la contraseña expire
     *
     * @param value
     * @return
     */
    private Integer obtenerNoDiasContrasenaExpire(String value) {
        Integer noDiasContrasenaExpire = 365;
        if (StringUtils.isNumeric(value)) {
            try {
                noDiasContrasenaExpire = Integer.parseInt(value);
            } catch (NumberFormatException ex) {
                LOGGER.error(RESOURCES.getString("sys.config.err"), ex);
            }
        }
        return noDiasContrasenaExpire;
    }

    /**
     * Método que permite validar los datos de cambio de contraseña
     */
    public void validarCambioContrasena() {
        LOGGER.trace("mx.mc.magedbean.RecuperarContrasenaMB.validarCambioContrasena()");
        boolean estatus = Constantes.INACTIVO;

        try {

            if (this.nombreUsuario == null || this.nombreUsuario.trim().isEmpty()) {
                String cambio_contrasena_err_nombreUsuario = "Usuario es requerido ";
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, cambio_contrasena_err_nombreUsuario, null);

            } else if (this.correo == null || this.correo.trim().isEmpty()) {
                String cambio_contrasena_err_correo = "Cuenta de correo requerido ";
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, cambio_contrasena_err_correo, null);

            } else {

                this.usuario = usuarioService.getUserByUserName(nombreUsuario);
                if (this.usuario == null) {
                    String cambio_contrasena_err_usuarioNoEncontrado = "El usuario no fué encontrado ";
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, cambio_contrasena_err_usuarioNoEncontrado, null);

                } else if (!this.usuario.getCorreoElectronico().equals(this.correo)) {
                    String cambio_contrasena_err_correoNoEncontrado = "El correo no coincide con el registrado ";
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, cambio_contrasena_err_correoNoEncontrado, null);

                } else {
                    estatus = Constantes.ACTIVO;
                }
            }
        } catch (Exception e) {
            String cambio_contrasena_err_consultaDatos = "Error al consultar datos para cambio de contraseña ";
            LOGGER.error(cambio_contrasena_err_consultaDatos, e);
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, cambio_contrasena_err_consultaDatos, null);
        }
        PrimeFaces.current().ajax().addCallbackParam("estatus", estatus);
    }

    public void validaContrasena1(AjaxBehaviorEvent event) {
        LOGGER.trace("mx.mc.magedbean.RecuperarContrasenaMB.validaContrasena1()");
        try {
            String x = ((HtmlSelectOneMenu)event.getSource()).getClientId();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    public void validaContrasena2(AjaxBehaviorEvent event) {
        LOGGER.trace("mx.mc.magedbean.RecuperarContrasenaMB.validaContrasena2()");
        try {
            String x = ((HtmlSelectOneMenu)event.getSource()).getClientId();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
    }
    
    /**
     * Método para actualizar la contraseña
     */
    public void cambiarContrasena() {
        LOGGER.trace("mx.mc.magedbean.RecuperarContrasenaMB.cambiarContrasena()");

        boolean estatus = Constantes.INACTIVO;

        if (this.contrasena2 == null || this.contrasena2.trim().isEmpty()) {
            String cambio_contrasena_err_contrasenaNueva = "Contraseña y confirmación requerida";
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, cambio_contrasena_err_contrasenaNueva, null);
        } else {
//
//        } else if (this.contrasena1 == null || this.contrasena1.trim().isEmpty()) {
//            String cambio_contrasena_err_confirmacionContrasena = "Confirmacion de contraeña reuerida ";
//            Mensaje.showMessage(Constantes.MENSAJE_ERROR, cambio_contrasena_err_confirmacionContrasena, null);
//
//        } else if (!this.contrasena1.trim().toUpperCase().equals(this.contrasena2.trim().toUpperCase())) {
//            String cambio_contrasena_err_confirmacionContrasena = "Confirmacion de contraeña reuerida ";
//            Mensaje.showMessage(Constantes.MENSAJE_ERROR, cambio_contrasena_err_confirmacionContrasena, null);
//
//        } else {
            obtenerDatosSistema();
            String diasContrasenaExpire = Comunes.obtenValorConfiguracion(configList, Constantes.PARAM_SYSTEM_NODIASCONTRASENAEXPIRE);
            Integer noDiasContrasenaExpire = obtenerNoDiasContrasenaExpire(diasContrasenaExpire);

            Usuario u = new Usuario();
            u.setIdUsuario(usuario.getIdUsuario());
            u.setUpdateFecha(new java.util.Date());
            u.setUpdateIdUsuario(usuario.getIdUsuario());
            u.setActivo(usuario.isActivo());
            u.setUsuarioBloqueado(usuario.isUsuarioBloqueado());
            u.setFechaVigencia(FechaUtil.sumarRestarDiasFecha(new java.util.Date(), noDiasContrasenaExpire));
            u.setNumErrorAcceso(0);

            try {

                u.setClaveAcceso(CustomWebSecurityConfigurerAdapter.argon2Encode(this.contrasena2));
                estatus = usuarioService.actualizar(u);
                this.mostrarDatos = Constantes.INACTIVO;

                if (!estatus) {
                    String cambio_contrasena_err = "Error al cambiar la contraseña";
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, cambio_contrasena_err, null);

                } else {
                    String cambio_contrasena_ok = "Éxito al cambiar la contraseña";
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, cambio_contrasena_ok, null);
                    boolean res = enviarCorreo();
                    if (!res) {
                        String cambio_contrasena_err_actualizarDatos = "Error al enviar correo de confirmación. ";
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, cambio_contrasena_err_actualizarDatos, null);

                    }
                }
            } catch (Exception e) {
                String cambio_contrasena_err_actualizarDatos = "Error al actualizar la contraseña de usuario ";
                LOGGER.error(cambio_contrasena_err_actualizarDatos, e);
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, cambio_contrasena_err_actualizarDatos, null);
            }
        }

        PrimeFaces.current().ajax().addCallbackParam("estatus", estatus);
    }

    /**
     * Prepara la información para generar el correo y enviarlo
     */
    private boolean enviarCorreo() {
        LOGGER.trace("mx.mc.magedbean.RecuperarContrasenaMB.generaCorreo()");

        boolean res = Constantes.INACTIVO;
        try {

            String correoUsuario = null;
            if (this.usuario.getCorreoElectronico() == null || this.usuario.getCorreoElectronico().trim().isEmpty()) {
                correoUsuario = this.usuario.getCorreoElectronico();
            }

            String remitenteCorreo = Comunes.obtenValorConfiguracion(configList, Constantes.PARAM_SYSTEM_REMITENTE_CORREO);
            String[] destinatarios = new String[]{this.usuario.getCorreoElectronico()};
            String[] copiara = null;
            String asunto = "Cambio de contraseña ";

            String remitenteNombre = Comunes.obtenValorConfiguracion(configList, Constantes.PARAM_SYSTEM_REMITENTE_NOMBRE);
            String urlportal = Comunes.obtenValorConfiguracion(configList, Constantes.PARAM_SYSTEM_URL_PORATL);

            Object[] parametros = {usuario.getNombreUsuario(), remitenteCorreo, urlportal};

            List<AdjuntoExtended> adjuntosLista = null;

            PlantillaCorreo pc = new PlantillaCorreo();
            pc.setClave(PlantillaCorreo_Enum.CAMBIO_CONTRASENA.getClave());
            PlantillaCorreo plantilla = plantillaCorreoService.obtener(pc);

            if (plantilla == null) {
                String cambio_contrasena_err_enviaCorreo_plantilla = "Plantilla de correo no encontrada ";
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, cambio_contrasena_err_enviaCorreo_plantilla, null);
            } else {
                res = enviaCorreoUtil.enviarCorreoConPlantillaYAdjuntos(remitenteCorreo, destinatarios, copiara,
                        asunto, parametros, plantilla, adjuntosLista);

            }

        } catch (Exception e) {
            String cambio_contrasena_err_enviaCorreo = "Error al enviar correo notificación ";
            LOGGER.error(cambio_contrasena_err_enviaCorreo, e);
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, cambio_contrasena_err_enviaCorreo, null);
        }

        return res;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena1() {
        return contrasena1;
    }

    public void setContrasena1(String contrasena1) {
        this.contrasena1 = contrasena1;
    }

    public String getContrasena2() {
        return contrasena2;
    }

    public void setContrasena2(String contrasena2) {
        this.contrasena2 = contrasena2;
    }

    public boolean isMostrarDatos() {
        return mostrarDatos;
    }

    public void setMostrarDatos(boolean mostrarDatos) {
        this.mostrarDatos = mostrarDatos;
    }

}
