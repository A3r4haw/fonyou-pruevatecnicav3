/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.magedbean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.regex.Matcher;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import mx.mc.enums.Transaccion_Enum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import mx.mc.init.Constantes;
import mx.mc.init.CustomWebSecurityConfigurerAdapter;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.Usuario;
import mx.mc.model.Usuario_Extended;
import mx.mc.service.UsuarioService;
import mx.mc.util.Comunes;
import mx.mc.util.Mensaje;

/**
 *
 * @author bbautista
 */
@Controller
@Scope(value = "view")
public class CuentaMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(CuentaMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    private boolean activo;    
    private Date date = new Date();
    private SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss");
    private String nombre;    
    private String usuario; 
    private String correo;    
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String fechaVigencia;
    private String cedulaProf;
    private String cedulaEsp;
    private String contrasena;
    private String confirmContrasena;
    private boolean  habilitaCampos;
    private String estructura;
    private boolean correctPass;
    private PermisoUsuario permiso;

    @Autowired
    private transient UsuarioService usuarioService;
    private Usuario usuarioSelect;
    private Usuario_Extended usuarioExtendedSelect;
    private Usuario currentSesionUser;        

    @PostConstruct
    public void init(){
        passwordNumCaracter = 4;
        initialize();
        permiso = Comunes.obtenerPermisos(Transaccion_Enum.USUARIOS.getSufijo());
        habilitaCampos = true;        
    }

    /*
        Methods Private
     */
    private void initialize(){
        try {
            usuarioSelect = new Usuario();                                   
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
            currentSesionUser = sesion.getUsuarioSelected();
            obtenerUsuario();
            correctPass = false;                       
            passwordNumCaracter = sesion.getPasswordNumCaracter();
            
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(CuentaMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void habilitarCampos() {
        habilitaCampos = false;
    }
    

    private Integer passwordNumCaracter;

    public Integer getPasswordNumCaracter() {
        return passwordNumCaracter;
    }

    public void setPasswordNumCaracter(Integer passwordNumCaracter) {
        this.passwordNumCaracter = passwordNumCaracter;
    }
    
    public  void validaDatosCuentaUser() throws Exception{
        boolean cont = Constantes.ACTIVO;

        Matcher mat;

        if (!usuarioExtendedSelect.getCorreoElectronico().equals("")) {
            mat = Constantes.regexMail.matcher(usuarioExtendedSelect.getCorreoElectronico());
            if (!mat.find()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.correoinvalido"), null);
                cont = Constantes.INACTIVO;
            }
        } else {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.correobligatorio"), null);
            cont = Constantes.INACTIVO;
        }
        if (!usuarioExtendedSelect.getNombre().equals("")) {
            mat = Constantes.regexNomAp.matcher(usuarioExtendedSelect.getNombre());
            if (!mat.find()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.nombreinvalido"), null);
                cont = Constantes.INACTIVO;
            }
        } else {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.nombreobligatorio"), null);
            cont = Constantes.INACTIVO;
        }

        if (!usuarioExtendedSelect.getApellidoPaterno().equals("")) {
            mat = Constantes.regexNomAp.matcher(usuarioExtendedSelect.getApellidoPaterno());
            if (!mat.find()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.apaternoInvalido"), null);
                cont = Constantes.INACTIVO;
            }
        } else {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.apaternObligatorio"), null);
            cont = Constantes.INACTIVO;
        }

        if (!usuarioExtendedSelect.getApellidoMaterno().equals("")) {
            mat = Constantes.regexNomAp.matcher(usuarioExtendedSelect.getApellidoMaterno());
            if (!mat.find()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.amaternoInvalido"), null);
                cont = Constantes.INACTIVO;
            }
        }
//        else {
//            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.amaternoInvalido"), null);
//            cont = Constantes.INACTIVO;
//        }
        
        if (!contrasena.equals("")) {

            if (contrasena.length() < passwordNumCaracter) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.claveInvalida") + " " + passwordNumCaracter, null);
                cont = Constantes.INACTIVO;
            } else {
                if (contrasena.equals(confirmContrasena)) {
                    usuarioExtendedSelect.setClaveAcceso(contrasena);
                    correctPass = true;
                } else {                    
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Las contraseñas son Diferentes ", null);
                    cont = Constantes.INACTIVO;
                }
            }
        }
        
        if(cont){
            actualizarCuentaUsuario(usuarioExtendedSelect);
        }
        
    }
    
    public void actualizarCuentaUsuario(Usuario_Extended usuarioExtendedSelect) throws Exception{
        Usuario user = new Usuario();
        user.setIdUsuario(usuarioExtendedSelect.getIdUsuario());
        user.setNombre(usuarioExtendedSelect.getNombre());
        user.setApellidoPaterno(usuarioExtendedSelect.getApellidoPaterno());
        user.setApellidoMaterno(usuarioExtendedSelect.getApellidoMaterno());
        user.setCorreoElectronico(usuarioExtendedSelect.getCorreoElectronico());
        user.setUpdateFecha(new java.util.Date());
        user.setUpdateIdUsuario(usuarioExtendedSelect.getIdUsuario());        
        if (correctPass) {
            user.setClaveAcceso(usuarioExtendedSelect.getClaveAcceso());
        }

        user.setClaveAcceso(CustomWebSecurityConfigurerAdapter.argon2Encode(user.getClaveAcceso()));
        boolean actuCuenta = usuarioService.actualizarCuentaUsuario(user);
        if(actuCuenta){
            Mensaje.showMessage(Constantes.MENSAJE_INFO, "Se actualizó Correctamente la Cuenta", null);
        } 
    }

    public Usuario_Extended obtenerUsuario() throws Exception {
        try {
            usuarioExtendedSelect = usuarioService.obtenerCuentaUsuarioPorId(currentSesionUser.getIdUsuario());
        } catch (Exception ex) {
            LOGGER.error("Error al obtener el Usuario: {}", ex.getMessage());
        }
        return usuarioExtendedSelect;
    }



    /*
    Section Getters & Setters
     */   

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public SimpleDateFormat getSdf() {
        return sdf;
    }

    public void setSdf(SimpleDateFormat sdf) {
        this.sdf = sdf;
    }
  
    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public UsuarioService getUsuarioService() {
        return usuarioService;
    }

    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public Usuario getUsuarioSelect() {
        return usuarioSelect;
    }

    public void setUsuarioSelect(Usuario usuarioSelect) {
        this.usuarioSelect = usuarioSelect;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getFechaVigencia() {
        return fechaVigencia;
    }

    public void setFechaVigencia(String fechaVigencia) {
        this.fechaVigencia = fechaVigencia;
    }

    public String getCedulaProf() {
        return cedulaProf;
    }

    public void setCedulaProf(String cedulaProf) {
        this.cedulaProf = cedulaProf;
    }

    public String getCedulaEsp() {
        return cedulaEsp;
    }

    public void setCedulaEsp(String cedulaEsp) {
        this.cedulaEsp = cedulaEsp;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getConfirmContrasena() {
        return confirmContrasena;
    }

    public void setConfirmContrasena(String confirmContrasena) {
        this.confirmContrasena = confirmContrasena;
    }

    public boolean isHabilitaCampos() {
        return habilitaCampos;
    }

    public void setHabilitaCampos(boolean habilitaCampos) {
        this.habilitaCampos = habilitaCampos;
    }

    public String getEstructura() {
        return estructura;
    }

    public void setEstructura(String estructura) {
        this.estructura = estructura;
    }

    public Usuario_Extended getUsuarioExtendedSelect() {
        return usuarioExtendedSelect;
    }

    public void setUsuarioExtendedSelect(Usuario_Extended usuarioExtendedSelect) {
        this.usuarioExtendedSelect = usuarioExtendedSelect;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }   
}