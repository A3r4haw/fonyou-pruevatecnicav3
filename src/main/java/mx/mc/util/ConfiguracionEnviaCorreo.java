//package mx.mc.util;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Properties;
//import java.util.ResourceBundle;
//import javax.annotation.PostConstruct;
//import mx.mc.init.Constantes;
//import mx.mc.magedbean.RecuperarContrasenaMB;
//import mx.mc.model.Config;
//import mx.mc.model.EnviadorCorreo;
//import mx.mc.service.ConfigService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//
///**
// *
// * @author Cervanets
// */
//@Configuration
//public class ConfiguracionEnviaCorreo implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//    private final Logger LOGGER = LoggerFactory.getLogger(ConfiguracionEnviaCorreo.class);
//    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
//
//    @Autowired
//    private transient ConfigService configService;
//    
//    
//    
//    /**
//     * Postconsrtuctor que gestiona lo necesario
//     */
//    @PostConstruct
//    public void init() {
//        LOGGER.trace("mx.mc.magedbean.ConfiguracionEnviaCorreo.init()");
//    }
//    
//    /**
//     * Obtiene los parámetros del sistema
//     */
//    private List<Config> obtenerDatosSistema() {
//        LOGGER.debug("mx.mc.init.ConfiguracionEnviaCorreo.obtenerDatosSistema()");
//        List<Config> configList = new ArrayList<>();
//        Config c = new Config();
//        c.setActiva(Constantes.ACTIVO);
//        try {
//            configList = configService.obtenerLista(c);
//        } catch (Exception ex) {
//            LOGGER.error(RESOURCES.getString("sys.config.err"), ex);
//        }
//        return configList;
//    }
//    
//    
//    /**
//     * Método para obtener los parámetros de la cuenta para el enviador de correo
//     * @param configList
//     * @return 
//     */
//    private EnviadorCorreo obtenerCuentaAutenticacion(List<Config> configList){
//        LOGGER.trace("mx.mc.util.ConfiguracionEnviaCorreo.obtenerCuentaAutenticacion()");
//        EnviadorCorreo ec = null;
//        try {
//            
//            String servidor = Comunes.obtenValorConfiguracion(configList, Constantes.PARAM_SYSTEM_CORREO_SERVIDOR);
//            String puerto = Comunes.obtenValorConfiguracion(configList, Constantes.PARAM_SYSTEM_CORREO_PUERTO);
//            String nombreUsuario = Comunes.obtenValorConfiguracion(configList, Constantes.PARAM_SYSTEM_CORREO_NOMBREUSUARIO);
//            String contrasena = Comunes.obtenValorConfiguracion(configList, Constantes.PARAM_SYSTEM_CORREO_CONTRASENA);
//            String protocolo = Comunes.obtenValorConfiguracion(configList, Constantes.PARAM_SYSTEM_CORREO_PROTOCOLO);
//            String smtpAuth = Comunes.obtenValorConfiguracion(configList, Constantes.PARAM_SYSTEM_CORREO_SMTPAUTH);
//            String startTls = Comunes.obtenValorConfiguracion(configList, Constantes.PARAM_SYSTEM_CORREO_STARTTLS);
//            String depura = Comunes.obtenValorConfiguracion(configList, Constantes.PARAM_SYSTEM_CORREO_DEPURA);
//            
//            ec = new EnviadorCorreo();
//            ec.setServidor((servidor != null) ? servidor : "smtp.gmail.com");
//            ec.setPuerto((puerto != null) ? puerto : "587");
//            ec.setNombreUsuario((nombreUsuario!= null) ? nombreUsuario : "planempresafe@gmail.com");
//            ec.setContrasena((contrasena != null) ? contrasena : "Tauro015.X5K");
//            ec.setProtocolo((protocolo != null) ? protocolo : "smtp");
//            ec.setSmptAuth((smtpAuth != null) ? smtpAuth : "true");
//            ec.setStartTls((startTls != null) ? startTls : "true");
//            ec.setDepura((depura != null) ? depura : "true");
//            
//        } catch (Exception ex){
//            LOGGER.error(RESOURCES.getString("sys.config.err"), ex);
//        }
//        return ec;
//    }
//
//    /**
//     * Método para Obtener el MailSender
//     * @return 
//     */
//    @Bean
//    public JavaMailSender getJavaMailSender() {
//        LOGGER.trace("mx.mc.util.ConfiguracionEnviaCorreo.getJavaMailSender()");
//        
//        List<Config> configList = obtenerDatosSistema();
//        EnviadorCorreo ec = obtenerCuentaAutenticacion(configList);
//        
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost(ec.getServidor());
//        mailSender.setPort(Integer.valueOf(ec.getPuerto()));
//
//        mailSender.setUsername(ec.getNombreUsuario());
//        mailSender.setPassword(ec.getContrasena());
//
//        Properties props = mailSender.getJavaMailProperties();
//        props.put("mail.transport.protocol", ec.getProtocolo());
//        props.put("mail.smtp.auth", ec.getSmptAuth());
//        props.put("mail.smtp.starttls.enable", ec.getStartTls());
//        props.put("mail.debug", ec.getDepura());
//
//        return mailSender;
//    }
//
//    /**
//     * Método para Obtener el MailSender
//     * @return 
//     */
////    @Bean
////    public SimpleMailMessage emailTemplate() {
////        SimpleMailMessage message = new SimpleMailMessage();
////        message.setTo("somebody@gmail.com");
////        message.setFrom("admin@gmail.com");
////        message.setText("FATAL - Application crash. Save your job !!");
////        return message;
////    }
//    
//    
//
//}
