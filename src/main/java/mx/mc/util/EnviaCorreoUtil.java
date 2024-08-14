package mx.mc.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import mx.mc.init.Constantes;
import mx.mc.model.AdjuntoExtended;
import mx.mc.model.PlantillaCorreo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 *
 * @author Cervanets
 */
@Component
public class EnviaCorreoUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(EnviaCorreoUtil.class);
    
    @Autowired
    private transient JavaMailSender mailSender;

    @Autowired
    private transient SimpleMailMessage simpleMailMessage;
    
    
    

    public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
        this.simpleMailMessage = simpleMailMessage;
    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    
    

    /**
     * Envia email indicando los valores del correo. El correo se envia en
     * segundo plano. Se intenta entregar 5 veces
     *
     * @param to
     * @param asunto
     * @param msg
     */
    public void enviarCorreoPlano(String para, String asunto, String msg) {
        final SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(simpleMailMessage.getFrom());
        message.setTo(para);
        message.setSubject(asunto);
        message.setText(msg);

        new Thread() {
            @Override
            public void run() {
                enviarCorreoSimple(message);
            }
        }.start();
    }

    private void enviarCorreoSimple(final SimpleMailMessage message) {
        LOGGER.trace("mx.mc.util.EnviaCorreoUtil.enviarCorreoSimple()");
        int noIntentos = 0;
        int tiempoEspera = 15000;
        boolean envioExitoso = false;
        do {
            try {
                LOGGER.debug("Se intenta enviar el correo.");
                mailSender.send(message);
                envioExitoso = true;
                LOGGER.debug("Correo enviado.");
            } catch (Exception e) {
                LOGGER.error("Error al enviar el correo {} ", e);
                noIntentos++;
            }

            if (!envioExitoso) {
                LOGGER.debug("No se pudo enviar en el intento {} , se tiempo de espera para siguente intento {} ", noIntentos, tiempoEspera);
                try {
                    Thread.sleep(tiempoEspera);
                } catch (Exception e) {
                    LOGGER.debug("Error al esperar para reenvió {} ", e);
                }
            }
        } while (!envioExitoso && noIntentos < 5);
    }

    /**
     * Método para enviar un correo con formato Html
     *
     * @param to
     * @param asunto
     * @param msg
     */
    public void enviarCorreoHtml(String para, String asunto, String msg) {
        try {
            final MimeMessage message = mailSender.createMimeMessage();
            message.setSubject(asunto);
            MimeMessageHelper helper;
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(simpleMailMessage.getFrom());
            helper.setTo(para);
            helper.setText(msg, true);
            new Thread() {
                @Override
                public void run() {
                    enviarCorreo(message);
                }
            }.start();
        } catch (MessagingException ex) {
            throw new MailParseException(ex);
        }
    }

    /**
     * Método para enviar correo
     *
     * @param message
     */
    private boolean enviarCorreo(final MimeMessage message) {
        LOGGER.trace("mx.mc.util.EnviaCorreoUtil.enviarCorreo()");
        
        int noIntentos = 0;
        int tiempoEspera = 15000;
        boolean envioExitoso = Constantes.INACTIVO;
        do {
            try {
                LOGGER.debug("Se intenta enviar el correo.");
                mailSender.send(message);
                envioExitoso = true;
                LOGGER.debug("Correo enviado.");
            } catch (Exception e) {
               LOGGER.error("Error al enviar el correo {} ", e);
                noIntentos++;
            }
            if (!envioExitoso) {
                LOGGER.debug("No se pudo enviar en el intento {} , tiempo de espera para siguente intento {} ", noIntentos, tiempoEspera);
                try {
                    Thread.sleep(tiempoEspera);
                } catch (Exception e) {
                    LOGGER.debug("Error al esperar para reenvió {} ", e);
                }
            }
        } while (!envioExitoso && noIntentos < 2);
        return envioExitoso;
    }

    /**
     * Método para enviar un correo mediante una plantilla y con Adjuntos
     *
     * @param remitente
     * @param destinatarios
     * @param copiaPara
     * @param asunto
     * @param parametros
     * @param plantilla
     * @param adjuntosLista
     * @return 
     * @throws Exception
     */
    public boolean enviarCorreoConPlantillaYAdjuntos(String remitente, String[] destinatarios, String[] copiaPara, String asunto,
            Object[] parametros, PlantillaCorreo plantilla, List<AdjuntoExtended> adjuntosLista) throws Exception {
        LOGGER.trace("mx.mc.util.EnviaCorreoUtil.enviarCorreoConPlantillaYAdjuntos()");

        boolean res = Constantes.INACTIVO;
        
        if (plantilla != null) {
            String msg = MessageFormat.format(plantilla.getContenido(), Arrays.toString(parametros));
            final MimeMessage messageMime = mailSender.createMimeMessage();
            messageMime.setContent(destinatarios, msg);
            messageMime.setSubject(asunto, "UTF-8");
            MimeMessageHelper helper = new MimeMessageHelper(messageMime, true, "UTF-8");

            if (remitente != null) {
                helper.setFrom(remitente);
            } else {
                helper.setFrom(simpleMailMessage.getFrom());
            }
            if (destinatarios != null) {
                helper.setTo(destinatarios);
            }

            if (copiaPara != null) {
                helper.setBcc(copiaPara);
            }

            helper.setText(msg, true);

            if (adjuntosLista != null){
                try {
                    for (AdjuntoExtended ae : adjuntosLista) {
                        InputStreamSource is = new ByteArrayResource(ae.getAdjunto());
                        helper.addAttachment(ae.getNombreAdjunto(), is);
                    }
                } catch (MessagingException e) {
                    throw new MailParseException("Error al adjnutar archivos al correo. {} ", e);
                }
            }

//            new Thread() {
//                @Override
//                public void run() {
                    res = enviarCorreo(messageMime);
//                    res = Constantes.INACTIVO;
//                }
//            }.start();
        } else {
            throw new Exception("Error al enviar el mensaje mediante una plantilla. ");
        }
        return res;
    }
    
}
