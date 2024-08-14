package mx.mc.magedbean;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import mx.mc.init.Constantes;
import mx.mc.util.FechaUtil;
import org.slf4j.LoggerFactory;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Cervanets
 */
@Controller
@Scope(value = "request")
public class ValidadorFecha implements Validator , Serializable {

    private static final long serialVersionUID = 1L;
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(RecepcionManualMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private static final ResourceBundle RESOURCESMESSAGE = ResourceBundle.getBundle(Constantes.GLOBAL_PARAM);

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        LOGGER.trace("mx.mc.magedbean.ValidadorFecha.validate()");
        String message;
        Date fechaActual = new java.util.Date();
        Date fechaMaxima = new java.util.Date();
        try {
            fechaMaxima = FechaUtil.formatoFecha("yyyy-MM-dd", Constantes.CADUCIDAD_GENERICA);
        } catch (Exception ex) {
            LOGGER.error("Error al parsear fecha generica: {} " , ex.getMessage());
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validacion Error", ex.getMessage());
            throw new ValidatorException(facesMessage);
        }
        if (value == null) {
            message = "Fecha requerida";
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validacion Error", message));
//            return;
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validacion Error", message);
            throw new ValidatorException(Arrays.asList(facesMessage));
            
//        } else if (!FechaUtil.isFormatoFechaValida((Date) value)) {
//            message = "Formato de Fecha incorrecto";
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validacion Error", message));
//            return;
            
//        } else if (!FechaUtil.isFechaValida(fechaActual, fechaMaxima , (Date) value )) {
//            message = "Debe introducir una fecha correcta mayor a la fecha actual";
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validacion Error", message));
//            return;
            
        } else if (!esMayoQueHoy((Date) value)) {
            message = "Debe introducir una fecha correcta mayor a la fecha actual";
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validacion Error", message));
//            return;
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validacion Error", message);
            throw new ValidatorException(Arrays.asList(facesMessage));
            
        }
    }

    private boolean esMayoQueHoy(Date value) {
        LocalDate fechaAcomparar = this.convertirFecha(value);
        boolean res =fechaAcomparar.compareTo(LocalDate.now()) > 0;
        return res;
    }

    private LocalDate convertirFecha(Date dateToConvert) {
        return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

}
