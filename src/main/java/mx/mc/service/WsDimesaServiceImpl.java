package mx.mc.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import java.util.ArrayList;
import java.util.List;
import mx.com.dimesa.ws.client.model.ArrayOfReceta;
import mx.com.dimesa.ws.client.model.ArrayOfRecetaMaterial;
import mx.com.dimesa.ws.client.model.Paciente;
import mx.com.dimesa.ws.client.model.Receta;
import mx.com.dimesa.ws.client.model.RecetaMaterial;
import mx.com.dimesa.ws.client.model.RecetaSAFWSResultado;
import mx.com.dimesa.ws.client.service.IRecetaSAFWSService;
import mx.com.dimesa.ws.client.model.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.microsoft.schemas._2003._10.serialization.ObjectFactory;
import javax.xml.datatype.DatatypeConfigurationException;
import mx.com.dimesa.ws.client.service.IRecetaSAFWSServiceCancelRecetaRecetaSAFWSExceptionFaultFaultMessage;
import mx.com.dimesa.ws.client.service.IRecetaSAFWSServiceCreateColectivoRecetaSAFWSExceptionFaultFaultMessage;
import mx.com.dimesa.ws.client.service.IRecetaSAFWSServiceGetRecetaRecetaSAFWSExceptionFaultFaultMessage;

/**
 *
 * @author hramirez
 */
//@Service
public class WsDimesaServiceImpl //implements WsDimesaService 
{
    private static final Logger LOGGER = LoggerFactory.getLogger(WsDimesaServiceImpl.class);

    @Autowired
    private IRecetaSAFWSService recetaSAFWS;

    
    public void ejecuta(){
        Receta r = new Receta();
        RecetaMaterial rm = new RecetaMaterial();
        try {
            registrarReceta(r, rm, "", "", "", "");
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        
    }
    
//    @Override
    public RecetaSAFWSResultado registrarReceta(Receta receta, RecetaMaterial recetaMaterial, String url, String proc, String user, String pass) throws Exception {
        LOGGER.trace("Invocando WS... ");
        RecetaSAFWSResultado output = new RecetaSAFWSResultado();

        try {
            Usuario usuario;
            usuario = new Usuario();
            ObjectFactory factory = new ObjectFactory();
            JAXBElement<String> nombre = factory.createString("INTISSEMyMQA");
            JAXBElement<String> contrasenia = factory.createString("ISSEMYM.2018");
            JAXBElement<String> farmacia = factory.createString("577974");
            
            usuario.setContrasenia(contrasenia);
            usuario.setFarmacia(farmacia);
            usuario.setNombre(nombre);
            
            Receta recet;
            recet = new Receta(); 
            Date date = new Date(); 
            XMLGregorianCalendar xmlDate = null; 
            GregorianCalendar gc = new GregorianCalendar(); 
            gc.setTime(date); 
            xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
            
// setter Receta receta, RecetaMaterial recetaMaterial 
            JAXBElement<XMLGregorianCalendar> xmlGergFecha = factory.createDateTime(xmlDate); 
            JAXBElement<String> folio = factory.createString("F14"); 
            recet.setFecha(xmlDate); 
            recet.setFechaReferencia(xmlGergFecha); 
            recet.setFolio(folio);
            JAXBElement<String> claveInterna = factory.createString("25311NA05721");
            JAXBElement<String> dosis = factory.createString("1");
            JAXBElement<String> tratamiento = factory.createString("1");
            RecetaMaterial recMaterial = new RecetaMaterial();
            
            ArrayOfRecetaMaterial arrayOfReceta = new ArrayOfRecetaMaterial(); 
            recMaterial.setCantidadEntregada(new BigDecimal(0)); 
            recMaterial.setCantidadSolicitada(new BigDecimal(1)); 
            recMaterial.setClaveInterna(claveInterna); 
            recMaterial.setDosis(dosis); 
            recMaterial.setPosicion(2); 
            recMaterial.setTratamiento(tratamiento); 

            arrayOfReceta.getRecetaMaterial().add(recMaterial);
            JAXBElement<ArrayOfRecetaMaterial> listMateriales;
            listMateriales = new JAXBElement<>(new QName(ArrayOfRecetaMaterial.class.getSimpleName()), ArrayOfRecetaMaterial.class,null);
            listMateriales.setValue(arrayOfReceta);
            recet.setMateriales(listMateriales);

            Paciente pac = new Paciente(); 
            pac.setFechaNacimiento(xmlGergFecha); 
            
            JAXBElement<Paciente> paciente = new JAXBElement<>(new QName(Paciente.class.getSimpleName()), Paciente.class, null);
            paciente.setValue(pac);
            recet.setPaciente(paciente); 
            JAXBElement<String> piso = factory.createString("1"); 
            JAXBElement<String> servicio = factory.createString("SERV"); 
            JAXBElement<String> tipo = factory.createString("1"); 
            recet.setPiso(piso); 
            recet.setServicio(servicio); 
            recet.setTipo(tipo); 
            
            output = recetaSAFWS.createColectivo(usuario, recet);
            if (output != null) {
                LOGGER.info("Anticipo Registrado...");
            }
        } catch (Exception ex) {
            LOGGER.error("Error al registrar anticipo:", ex);
        }
        LOGGER.info("FIN EJECUCION WS -> ");
        return output;
    }

//    @Override
    public RecetaSAFWSResultado registrarColectivo(Receta receta, RecetaMaterial recetaMaterial, String url, String proc, String user, String pass) throws Exception {
        LOGGER.trace("Invocando WS... ");
        RecetaSAFWSResultado output = new RecetaSAFWSResultado();
        
        try {
            Usuario usuario;
            usuario = new Usuario();
            
            // setter
            //String url, String proc, String user, String pass
            ObjectFactory factory = new ObjectFactory();
            JAXBElement<String> nombre = factory.createString("INTISSEMyMQA");
            JAXBElement<String> contrasenia = factory.createString("ISSEMYM.2018");
            JAXBElement<String> farmacia = factory.createString("577974");
            usuario.setNombre(nombre);
            usuario.setContrasenia(contrasenia);
            usuario.setFarmacia(farmacia);
            
            Receta recetaNueva = new Receta();
            Date date = new Date();
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(date);
            XMLGregorianCalendar xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
			// setter
            //Receta receta, RecetaMaterial recetaMaterial
            JAXBElement<XMLGregorianCalendar> xmlGergFecha = factory.createDateTime(xmlDate);
            JAXBElement<String> folio = factory.createString("F14");
            
            recetaNueva.setFecha(xmlDate);
            recetaNueva.setFechaReferencia(xmlGergFecha);
            recetaNueva.setFolio(folio);
            JAXBElement<String> claveInterna = factory.createString("25311NA05721");
            JAXBElement<String> dosis = factory.createString("1");
            JAXBElement<String> tratamiento = factory.createString("1");
            RecetaMaterial recMaterial = new RecetaMaterial();
            JAXBElement<ArrayOfRecetaMaterial> listMateriales = new JAXBElement<>(new QName(ArrayOfRecetaMaterial.class.getSimpleName()), ArrayOfRecetaMaterial.class,null);
            ArrayOfRecetaMaterial arrayOfReceta = new ArrayOfRecetaMaterial();
            recMaterial.setCantidadEntregada(new BigDecimal(0));
            recMaterial.setCantidadSolicitada(new BigDecimal(1));
            recMaterial.setClaveInterna(claveInterna);
            recMaterial.setDosis(dosis);
            recMaterial.setPosicion(2);
            recMaterial.setTratamiento(tratamiento);
            arrayOfReceta.getRecetaMaterial().add(recMaterial);
            
            listMateriales.setValue(arrayOfReceta);
			recetaNueva.setMateriales(listMateriales);
			
			JAXBElement<Paciente> paciente = new JAXBElement<>(new QName(Paciente.class.getSimpleName()), Paciente.class, null);
			Paciente pac = new Paciente();
			pac.setFechaNacimiento(xmlGergFecha);
			paciente.setValue(pac);						
			recetaNueva.setPaciente(paciente);
			
			JAXBElement<String> piso = factory.createString("1");
			JAXBElement<String> servicio = factory.createString("SERV");
			JAXBElement<String> tipo = factory.createString("1");
			recetaNueva.setPiso(piso);
			recetaNueva.setServicio(servicio);
			recetaNueva.setTipo(tipo);
                        
            output = recetaSAFWS.createColectivo(usuario, recetaNueva);
            if (output != null) {
                LOGGER.info("Anticipo Registrado...");
            }

        } catch (DatatypeConfigurationException | IRecetaSAFWSServiceCreateColectivoRecetaSAFWSExceptionFaultFaultMessage ex) {
            LOGGER.error("Error al registrar anticipo:", ex);
        }
        LOGGER.info("FIN EJECUCION WS -> ");
        return output;
    }

//    @Override
    public RecetaSAFWSResultado cancelarReceta(Receta receta, RecetaMaterial recetaMaterial, String url, String proc, String user, String pass) throws Exception {
        LOGGER.trace("Invocando WS... ");
        RecetaSAFWSResultado output = new RecetaSAFWSResultado();

        try {
            Usuario usuario;
            usuario = new Usuario();
            // setter
            //String url, String proc, String user, String pass

            Receta recet = new Receta();
            // setter
            //Receta receta, RecetaMaterial recetaMaterial

            output = recetaSAFWS.cancelReceta(usuario, recet);
            if (output != null) {
                LOGGER.info("Anticipo Registrado...");
            }

        } catch (IRecetaSAFWSServiceCancelRecetaRecetaSAFWSExceptionFaultFaultMessage ex) {
            LOGGER.error("Error al registrar anticipo:", ex);
        }
        LOGGER.info("FIN EJECUCION WS -> ");
        return output;
    }

//    @Override
    public List<RecetaSAFWSResultado> consultarColectivo(Receta receta, RecetaMaterial recetaMaterial, String url, String proc, String user, String pass) throws Exception {
        LOGGER.trace("Invocando WS... ");
        List<RecetaSAFWSResultado> output = (List<RecetaSAFWSResultado>) new RecetaSAFWSResultado();

        try {
            Usuario usuario;
            usuario = new Usuario();
            // setter
            //String url, String proc, String user, String pass

            List<Receta> recetaList;
            recetaList = new ArrayList<>();

            Receta recet = new Receta();
            recetaList.add(recet);
            // setter
            //Receta receta, RecetaMaterial recetaMaterial

            output = (List<RecetaSAFWSResultado>) recetaSAFWS.getReceta(usuario, (ArrayOfReceta) recetaList);
            if (output != null) {
                LOGGER.info("Anticipo Registrado...");
            }

        } catch (IRecetaSAFWSServiceGetRecetaRecetaSAFWSExceptionFaultFaultMessage ex) {
            LOGGER.error("Error al registrar anticipo:", ex);
        }
        LOGGER.info("FIN EJECUCION WS -> ");
        return output;
    }
}
