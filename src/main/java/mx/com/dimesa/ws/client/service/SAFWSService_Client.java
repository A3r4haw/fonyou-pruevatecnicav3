package mx.com.dimesa.ws.client.service;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.bind.JAXBElement;
import mx.com.dimesa.ws.client.model.ArrayOfReceta;
import mx.com.dimesa.ws.client.model.ArrayOfRecetaMaterial;
import mx.com.dimesa.ws.client.model.Medico;
import mx.com.dimesa.ws.client.model.Paciente;
import mx.com.dimesa.ws.client.model.Receta;
import mx.com.dimesa.ws.client.model.RecetaSAFWSResultado;
import mx.com.dimesa.ws.client.model.Usuario;
import mx.com.dimesa.ws.client.model.ObjectFactory;
import java.text.SimpleDateFormat;
import mx.com.dimesa.ws.client.model.ArrayOfTMAT;
import mx.com.dimesa.ws.client.model.RecetaMaterial;
import mx.mc.model.DimesaRecetaMaterial;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import mx.mc.model.DimesaUsuario;
import mx.mc.model.DimesaReceta;

public class SAFWSService_Client {

    private static final Logger LOGGER = LoggerFactory.getLogger(SAFWSService_Client.class);

    private static final QName SERVICE_NAME = new QName("http://tempuri.org/", "RecetaSAFWSService");

    private final URL wsdlURL;
    private final RecetaSAFWSService ss;
    @Autowired
    private final IRecetaSAFWSService port;        

    public SAFWSService_Client() {
        LOGGER.trace("mx.com.dimesa.ws.client.service.SAFWSService_Client.<init>()");
        wsdlURL = RecetaSAFWSService.WSDL_LOCATION;
        LOGGER.debug("URL: {}" , wsdlURL );
        ss = new RecetaSAFWSService(wsdlURL, SERVICE_NAME);
        port = ss.getBasicHttpBindingIRecetaSAFWSService();
    }

    public void getReceta() throws Exception {
        LOGGER.trace("Invoking getReceta...");

        Usuario usuario = new Usuario();
        JAXBElement<String> contrasenia = null;
        usuario.setContrasenia(contrasenia);
        JAXBElement<String> farmacia = null;
        usuario.setFarmacia(farmacia);
        JAXBElement<String> nombre = null;
        usuario.setNombre(nombre);

        ArrayOfReceta getRecetaRecetas = new ArrayOfReceta();
        List<Receta> recetaList = new ArrayList<>();
        Receta receta = new Receta();
        JAXBElement<String> cama = null;
        receta.setCama(cama);
        JAXBElement<String> descripcionServicio = null;
        receta.setDescripcionServicio(descripcionServicio);
        JAXBElement<String> recetaEstatus = null;
        receta.setEstatus(recetaEstatus);
        receta.setFecha(javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar("2018-02-17T19:50:22.855-06:00"));
        JAXBElement<javax.xml.datatype.XMLGregorianCalendar> recetaFechaReferencia = null;
        receta.setFechaReferencia(recetaFechaReferencia);
        JAXBElement<String> recetaFolio = null;
        receta.setFolio(recetaFolio);
        JAXBElement<String> recetaFolioPago = null;
        receta.setFolioPago(recetaFolioPago);
        JAXBElement<ArrayOfRecetaMaterial> recetaMateriales = null;
        receta.setMateriales(recetaMateriales);
        JAXBElement<Medico> recetaMedico = null;
        receta.setMedico(recetaMedico);
        JAXBElement<Paciente> recetaPaciente = null;
        receta.setPaciente(recetaPaciente);
        JAXBElement<String> recetaPiso = null;
        receta.setPiso(recetaPiso);
        JAXBElement<String> recetaServicio = null;
        receta.setServicio(recetaServicio);
        JAXBElement<String> recetaTipo = null;
        receta.setTipo(recetaTipo);
        recetaList.add(receta);
        getRecetaRecetas.getReceta().addAll(recetaList);
        try {
            ArrayOfReceta output = port.getReceta(usuario, getRecetaRecetas);
            LOGGER.info("getReceta.result={0}" , output);

        } catch (IRecetaSAFWSServiceGetRecetaRecetaSAFWSExceptionFaultFaultMessage e) {
            LOGGER.error("Expected exception: IRecetaSAFWSService_GetReceta_RecetaSAFWSExceptionFault_FaultMessage has occurred.");
            LOGGER.error(e.toString());
        }
    }

    public RecetaSAFWSResultado cancelReceta(DimesaUsuario du,DimesaReceta dr) throws Exception {
        LOGGER.trace("Invoking cancelReceta...");
        RecetaSAFWSResultado output= null;
        ObjectFactory factory= new ObjectFactory();
        
        //Datos del usuario de Conexión
        Usuario usuario = factory.createUsuario();
        JAXBElement<java.lang.String> contrasenia= factory.createUsuarioContrasenia(du.getContrasenia());
        JAXBElement<java.lang.String> farmacia = factory.createUsuarioFarmacia(du.getFarmacia());
        JAXBElement<java.lang.String> nombre = factory.createUsuarioNombre(du.getNombre());
        usuario.setContrasenia(contrasenia);
        usuario.setFarmacia(farmacia);
        usuario.setNombre(nombre);
        
                
        Receta receta = new Receta();
        SimpleDateFormat formato= new SimpleDateFormat();
        formato.applyPattern("yyyy-MM-dd'T'HH:mm:ss");
        String fechaString = formato.format(dr.getFecha());        
        receta.setFecha(javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar(fechaString));
        
        String fecReferencia = formato.format(dr.getFechaReferencia());
        JAXBElement<javax.xml.datatype.XMLGregorianCalendar> fechaReferencia = factory.createRecetaFechaReferencia(
                javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar(fecReferencia));
        receta.setFechaReferencia(fechaReferencia);
        
        JAXBElement<String> folio = factory.createRecetaFolio(dr.getFolio());
        receta.setFolio(folio);
        JAXBElement<String> folioPago = factory.createRecetaFolioPago(dr.getFolioPago());
        receta.setFolioPago(folioPago);
        
        ArrayOfRecetaMaterial unArrayOfReceta = factory.createArrayOfRecetaMaterial();
        for(DimesaRecetaMaterial item:dr.getMateriales()) {
            RecetaMaterial recMaterial = factory.createRecetaMaterial();
            JAXBElement<java.lang.String> claveInterna = factory.createRecetaMaterialClaveInterna(item.getClaveInterna());        
            recMaterial.setClaveInterna(claveInterna);
            JAXBElement<java.lang.String> claveSap = factory.createRecetaMaterialClaveSAP(item.getClaveSap());
            recMaterial.setClaveSAP(claveSap);
            recMaterial.setPosicion(item.getPosision());
            recMaterial.setCantidadEntregada(item.getCantidadEntregada() != null ? item.getCantidadEntregada() : BigDecimal.ZERO);
            recMaterial.setCantidadSolicitada(item.getCantidadSolicitada() != null ? item.getCantidadSolicitada(): BigDecimal.ZERO);
            unArrayOfReceta.getRecetaMaterial().add(recMaterial);                 
        }
        
        JAXBElement<ArrayOfRecetaMaterial> materiales = factory.createRecetaMateriales(unArrayOfReceta);
        receta.setMateriales(materiales);
        
        //Datos del Médico
        String nombreCompletoMedico = dr.getMedicoNombre();
        String[] nombreMedico = nombreCompletoMedico.split(" ");
        String nombreMed = "";
        String apMedicoPaterno = "";
        String apMedicoMaterno = "";
        
        if(nombreMedico.length == 0) {
            apMedicoPaterno = "TRIAGE";
            nombreMed = "TRIAGE";
            
        } else if(nombreMedico.length == 1) {
            apMedicoPaterno = nombreMedico[0];
            nombreMed = nombreMedico[0];
            
        } else if(nombreMedico.length == 2) {
            apMedicoPaterno = nombreMedico[nombreMedico.length - 1];
            nombreMed = nombreMedico[nombreMedico.length - 2];
            
        } else if(nombreMedico.length > 2) {
            apMedicoPaterno = nombreMedico[nombreMedico.length - 2];
            apMedicoMaterno = nombreMedico[nombreMedico.length - 1];
            
            for(int i = 0; i < nombreMedico.length - 2; i ++) {
                nombreMed = nombreMed.concat(nombreMedico[i]+ " ");
            }  
        }       
        String cedulaMedico = "CEMED12547";
        if(dr.getMedicoCedula() != null) {
            cedulaMedico = dr.getMedicoCedula();
        }
        JAXBElement<java.lang.String> apellidoMaterno = factory.createMedicoApellidoMaterno(apMedicoMaterno);
        JAXBElement<java.lang.String> apellidoPaterno = factory.createMedicoApellidoPaterno(apMedicoPaterno);        
        JAXBElement<java.lang.String> medicoNombre = factory.createMedicoNombre(nombreMed);
        JAXBElement<java.lang.String> cedula = factory.createMedicoCedula(cedulaMedico);
        Medico unMedico = factory.createMedico();
        unMedico.setApellidoMaterno(apellidoMaterno);
        unMedico.setApellidoPaterno(apellidoPaterno);
        unMedico.setCedula(cedula);
        unMedico.setNombre(medicoNombre);        
        JAXBElement<Medico> medicoJax = factory.createMedico(unMedico);
        receta.setMedico(medicoJax);
        
        //Datos del Paciente
        Paciente unPaciente = new Paciente();
        JAXBElement<java.lang.String> apPaternoPaciente = factory.createPacienteApellidoPaterno(dr.getApellidoPaterno());
        JAXBElement<java.lang.String> apMaternoPaciente = factory.createPacienteApellidoMaterno(dr.getApellidoMaterno());
        JAXBElement<java.lang.String> nombrePaciente = factory.createPacienteNombre(dr.getNombre());
        JAXBElement<java.lang.String> sexo = factory.createPacienteSexo(dr.getSexo());
        String fechaNacimiento = formato.format(dr.getFechaNacimiento());        
        JAXBElement<javax.xml.datatype.XMLGregorianCalendar> fechaPacienteNacimiento = factory.createPacienteFechaNacimiento(javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar(fechaNacimiento));
        JAXBElement<java.lang.String> padecimiento = factory.createPacientePadecimiento(dr.getPadecimiento());
        JAXBElement<java.lang.String> programa = factory.createPacientePrograma(dr.getPrograma());
        JAXBElement<java.lang.String> numeroAfiliacion = factory.createPacienteNumeroAfiliacion(dr.getNumeroAfiliacion());
        JAXBElement<Paciente> paciente = factory.createPaciente(unPaciente);
        unPaciente.setApellidoPaterno(apPaternoPaciente);
        unPaciente.setApellidoMaterno(apMaternoPaciente);
        unPaciente.setNombre(nombrePaciente);
        unPaciente.setSexo(sexo);
        unPaciente.setFechaNacimiento(fechaPacienteNacimiento);
        unPaciente.setPadecimiento(padecimiento);
        unPaciente.setPrograma(programa);
        unPaciente.setNumeroAfiliacion(numeroAfiliacion);
        receta.setPaciente(paciente);

        try {
            output = port.cancelReceta(usuario, receta);
            if (output != null) {
                LOGGER.info("cancelReceta.result={0}", output.toString());
            }

        } catch (IRecetaSAFWSServiceCancelRecetaRecetaSAFWSExceptionFaultFaultMessage e) {
            LOGGER.error("Expected exception: IRecetaSAFWSService_CancelReceta_RecetaSAFWSExceptionFault_FaultMessage has occurred.");
           if (e != null) {
                LOGGER.error(e.toString());
                if (e.getMessage()!= null) {

                    if (e.getMessage().contains("Receta ya existe, no es posible crear otra con el mismo folio")) {
                        output = new RecetaSAFWSResultado();
                        output.setCodigo(4);    // Receta ya Existe
                        JAXBElement<String> mensaje = factory.createRecetaFolio("Receta ya existe, no es posible crear otra con el mismo folio.");
                        output.setMensaje(mensaje);
                        
                    } else if (e.getMessage().contains("Usuario no válido"))  {
                        output = new RecetaSAFWSResultado();
                        output.setCodigo(1);    // Usuario no válido
                        JAXBElement<String> mensaje = factory.createRecetaFolio("Usuario no válido.");
                        output.setMensaje(mensaje);
                        
                    } else if (e.getMessage().contains("Acceso a farmacia no válido"))  {
                        output = new RecetaSAFWSResultado();
                        output.setCodigo(2);    // Acceso a farmacia no válido
                        JAXBElement<String> mensaje = factory.createRecetaFolio("Acceso a farmacia no válido.");
                        output.setMensaje(mensaje);
                        
                    } else if (e.getMessage().contains("Las posiciones de los materiales no son correctas"))  {
                        output = new RecetaSAFWSResultado();
                        output.setCodigo(6);    // Las posiciones de los materiales no son correctas
                        JAXBElement<String> mensaje = factory.createRecetaFolio("Las posiciones de los materiales no son correctas.");
                        output.setMensaje(mensaje);
                        
                    } else if (e.getMessage().contains("Material no encontrado"))  {
                        output = new RecetaSAFWSResultado();
                        output.setCodigo(7);    // Material no encontrado
                        JAXBElement<String> mensaje = factory.createRecetaFolio("Material no encontrado.");
                        output.setMensaje(mensaje);
                    }
                }
            }
        }
        return output;
    }

    public void createColectivoIssemym() throws java.lang.Exception {
        LOGGER.trace("Invoking createColectivoIssemym...");

        ObjectFactory factory = new ObjectFactory();

        Usuario usuario = factory.createUsuario();
        //JAXBElement<java.lang.String> contrasenia = factory.createUsuarioContrasenia("ISSEMYM.2018");  //QA calidad
        JAXBElement<java.lang.String> contrasenia = factory.createUsuarioContrasenia("ISSEMYM.PRD"); //Producccion
        usuario.setContrasenia(contrasenia);
        //JAXBElement<java.lang.String> farmacia = factory.createUsuarioFarmacia("577974"); //QA Calidad
        JAXBElement<java.lang.String> farmacia = factory.createUsuarioFarmacia("796467");
        usuario.setFarmacia(farmacia);
        //JAXBElement<java.lang.String> nombre = factory.createUsuarioNombre("INTISSEMyMQA"); //QA calidad
        JAXBElement<java.lang.String> nombre = factory.createUsuarioNombre("INTISSEMYM");
        usuario.setNombre(nombre);
        
        Receta receta = new Receta();
        receta.setFecha(javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar("2018-02-17T19:50:22.857-06:00"));
        JAXBElement<javax.xml.datatype.XMLGregorianCalendar> fechaReferencia = factory.createRecetaFechaReferencia(javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar("2018-02-17T19:50:22.857-06:00"));
        receta.setFechaReferencia(fechaReferencia);
        JAXBElement<java.lang.String> folio = factory.createRecetaFolio("FISi4");
        receta.setFolio(folio);
        JAXBElement<java.lang.String> folioPago = factory.createRecetaFolioPago("PISo14");
        receta.setFolioPago(folioPago);

        ArrayOfRecetaMaterial unArrayOfReceta = factory.createArrayOfRecetaMaterial();
        
        RecetaMaterial recMaterial = factory.createRecetaMaterial();
        
        JAXBElement<java.lang.String> claveInterna = factory.createRecetaMaterialClaveInterna("25311NZ00104");
        recMaterial.setClaveInterna(claveInterna);
        JAXBElement<java.lang.String> claveSap = factory.createRecetaMaterialClaveSAP("5505943");
        recMaterial.setClaveSAP(claveSap);
        Integer posicion = 1;
        recMaterial.setPosicion(posicion);
        
        unArrayOfReceta.getRecetaMaterial().add(recMaterial);                 
        
        JAXBElement<ArrayOfRecetaMaterial> arrayOfReceta = factory.createRecetaMateriales(unArrayOfReceta);
        receta.setMateriales(arrayOfReceta);

        
        JAXBElement<java.lang.String> apellidoMaterno = factory.createMedicoApellidoMaterno("Perez");
        JAXBElement<java.lang.String> apellidoPaterno = factory.createMedicoApellidoPaterno("Gomez");
        JAXBElement<java.lang.String> cedula = factory.createMedicoCedula("MED123456");
        JAXBElement<java.lang.String> nombreMedico = factory.createMedicoNombre("Joel");
        Medico unMedico = factory.createMedico();
        unMedico.setApellidoMaterno(apellidoMaterno);
        unMedico.setApellidoPaterno(apellidoPaterno);
        unMedico.setCedula(cedula);
        unMedico.setNombre(nombreMedico);
        JAXBElement<Medico> medicoJax = factory.createMedico(unMedico);
        receta.setMedico(medicoJax);

        try {
            mx.com.dimesa.ws.client.model.RecetaSAFWSResultado createColectivoIssemymReturn = port.createColectivoIssemym(usuario, receta);
            LOGGER.trace("createColectivoIssemym.result={}", createColectivoIssemymReturn.getMensaje().getValue());
        } catch (IRecetaSAFWSServiceCreateColectivoIssemymRecetaSAFWSExceptionFaultFaultMessage e) {    
            LOGGER.error("Expected exception: IRecetaSAFWSService_CreateColectivoIssemym_RecetaSAFWSExceptionFault_FaultMessage has occurred.");
            LOGGER.error("Error en web service: {}", e.toString());
        }
    }

    public void createReceta() throws java.lang.Exception {
        LOGGER.trace("Invoking createReceta...");

        Usuario usuario = new Usuario();
        JAXBElement<String> contrasenia = null;
        usuario.setContrasenia(contrasenia);
        JAXBElement<String> farmacia = null;
        usuario.setFarmacia(farmacia);
        JAXBElement<String> nombre = null;
        usuario.setNombre(nombre);
        Receta receta = new Receta();
        JAXBElement<String> recetaCama = null;
        receta.setCama(recetaCama);
        JAXBElement<String> recetaDescripcionServicio = null;
        receta.setDescripcionServicio(recetaDescripcionServicio);
        JAXBElement<String> recetaEstatus = null;
        receta.setEstatus(recetaEstatus);
        receta.setFecha(javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar("2018-02-17T19:50:22.859-06:00"));
        JAXBElement<javax.xml.datatype.XMLGregorianCalendar> recetaFechaReferencia = null;
        receta.setFechaReferencia(recetaFechaReferencia);
        JAXBElement<String> recetaFolio = null;
        receta.setFolio(recetaFolio);
        JAXBElement<String> recetaFolioPago = null;
        receta.setFolioPago(recetaFolioPago);
        JAXBElement<ArrayOfRecetaMaterial> recetaMateriales = null;
        receta.setMateriales(recetaMateriales);
        JAXBElement<Medico> recetaMedico = null;
        receta.setMedico(recetaMedico);
        JAXBElement<Paciente> recetaPaciente = null;
        receta.setPaciente(recetaPaciente);
        JAXBElement<String> recetaPiso = null;
        receta.setPiso(recetaPiso);
        JAXBElement<String> recetaServicio = null;
        receta.setServicio(recetaServicio);
        JAXBElement<String> recetaTipo = null;
        receta.setTipo(recetaTipo);
        try {
            RecetaSAFWSResultado output = port.createReceta(usuario, receta);
            LOGGER.info("createReceta.result={0}" , output);

        } catch (IRecetaSAFWSServiceCreateRecetaRecetaSAFWSExceptionFaultFaultMessage e) {
            LOGGER.error("Expected exception: IRecetaSAFWSService_CreateReceta_RecetaSAFWSExceptionFault_FaultMessage has occurred.");
            LOGGER.error(e.toString());
        }
    }

    public void inventario() throws java.lang.Exception {
        LOGGER.trace("Invoking inventario...");
        ObjectFactory factory = new ObjectFactory();

        Usuario usuario = factory.createUsuario();
        JAXBElement<String> contrasenia = factory.createUsuarioContrasenia("ISSEMYM.2018");
        usuario.setContrasenia(contrasenia);
        JAXBElement<String> farmacia = factory.createUsuarioFarmacia("577974");
        usuario.setFarmacia(farmacia);
        JAXBElement<String> nombre = factory.createUsuarioNombre("INTISSEMyMQA");
        usuario.setNombre(nombre);
        String inventarioClaveSAP = "_inventario_claveSAP-1621665657";
        try {
            java.lang.Long output = port.inventario(usuario, inventarioClaveSAP);
            LOGGER.info("inventario.result={}", output);

        } catch (IRecetaSAFWSServiceInventarioRecetaSAFWSExceptionFaultFaultMessage e) {
            LOGGER.error("Expected exception: IRecetaSAFWSService_Inventario_RecetaSAFWSExceptionFault_FaultMessage has occurred.");
            LOGGER.error(e.toString());
        }
    }

    public void createRecetaMaestra() throws java.lang.Exception {
        LOGGER.trace("Invoking createRecetaMaestra...");

        Usuario usuario = new Usuario();
        JAXBElement<String> contrasenia = null;
        usuario.setContrasenia(contrasenia);
        JAXBElement<String> farmacia = null;
        usuario.setFarmacia(farmacia);
        JAXBElement<String> nombre = null;
        usuario.setNombre(nombre);
        Receta receta = new Receta();
        JAXBElement<String> recetaCama = null;
        receta.setCama(recetaCama);
        JAXBElement<String> recetaDescripcionServicio = null;
        receta.setDescripcionServicio(recetaDescripcionServicio);
        JAXBElement<String> recetaEstatus = null;
        receta.setEstatus(recetaEstatus);
        receta.setFecha(javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar("2018-02-17T19:50:22.861-06:00"));
        JAXBElement<javax.xml.datatype.XMLGregorianCalendar> recetaFechaReferencia = null;
        receta.setFechaReferencia(recetaFechaReferencia);
        JAXBElement<String> recetaFolio = null;
        receta.setFolio(recetaFolio);
        JAXBElement<String> recetaFolioPago = null;
        receta.setFolioPago(recetaFolioPago);
        JAXBElement<ArrayOfRecetaMaterial> recetaMateriales = null;
        receta.setMateriales(recetaMateriales);
        JAXBElement<Medico> recetaMedico = null;
        receta.setMedico(recetaMedico);
        JAXBElement<Paciente> recetaPaciente = null;
        receta.setPaciente(recetaPaciente);
        JAXBElement<String> recetaPiso = null;
        receta.setPiso(recetaPiso);
        JAXBElement<String> recetaServicio = null;
        receta.setServicio(recetaServicio);
        JAXBElement<String> recetaTipo = null;
        receta.setTipo(recetaTipo);
        try {
            RecetaSAFWSResultado output = port.createRecetaMaestra(usuario, receta);
            LOGGER.info("createRecetaMaestra.result={0}" , output);

        } catch (IRecetaSAFWSServiceCreateRecetaMaestraRecetaSAFWSExceptionFaultFaultMessage e) {
            LOGGER.error("Expected exception: IRecetaSAFWSService_CreateRecetaMaestra_RecetaSAFWSExceptionFault_FaultMessage has occurred.");
            LOGGER.error(e.toString());
        }
    }

    public RecetaSAFWSResultado createRecetaIssemym(mx.mc.model.DimesaUsuario du, mx.mc.model.DimesaReceta dr) throws java.lang.Exception {
        LOGGER.trace("Invoking createRecetaIssemym...");
        RecetaSAFWSResultado output = null;
        ObjectFactory factory = new ObjectFactory();

        Usuario usuario = factory.createUsuario();
        JAXBElement<java.lang.String> contrasenia = factory.createUsuarioContrasenia(du.getContrasenia());
        usuario.setContrasenia(contrasenia);
        JAXBElement<java.lang.String> farmacia = factory.createUsuarioFarmacia(du.getFarmacia());
        usuario.setFarmacia(farmacia);
        JAXBElement<java.lang.String> nombre = factory.createUsuarioNombre(du.getNombre());
        usuario.setNombre(nombre);        
        
        Receta receta = new Receta();
        SimpleDateFormat formato = new SimpleDateFormat();
        formato.applyPattern("yyyy-MM-dd'T'HH:mm:ss");
        String fechaString = formato.format(dr.getFecha());
        receta.setFecha(javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar(fechaString));
        
        String fecReferencia = formato.format(dr.getFechaReferencia());        
        JAXBElement<javax.xml.datatype.XMLGregorianCalendar> fechaReferencia = factory.createRecetaFechaReferencia(javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar(fecReferencia));
        receta.setFechaReferencia(fechaReferencia);        
        JAXBElement<java.lang.String> folio = factory.createRecetaFolio(dr.getFolio());
        receta.setFolio(folio);        
        JAXBElement<java.lang.String> folioPago = factory.createRecetaFolioPago(dr.getFolioPago());
        receta.setFolioPago(folioPago);

        ArrayOfRecetaMaterial unArrayOfReceta = factory.createArrayOfRecetaMaterial();
         //todo quitar comentario para informacion de medicamentos que se consultaron en BD
        for(DimesaRecetaMaterial unaRecetaMaterial:dr.getMateriales()) {
            RecetaMaterial recMaterial = factory.createRecetaMaterial();
            JAXBElement<java.lang.String> claveInterna = factory.createRecetaMaterialClaveInterna(unaRecetaMaterial.getClaveInterna());        
            recMaterial.setClaveInterna(claveInterna);
            JAXBElement<java.lang.String> claveSap = factory.createRecetaMaterialClaveSAP(unaRecetaMaterial.getClaveSap());
            recMaterial.setClaveSAP(claveSap);
            recMaterial.setPosicion(unaRecetaMaterial.getPosision());
            recMaterial.setCantidadEntregada(unaRecetaMaterial.getCantidadEntregada() != null ? unaRecetaMaterial.getCantidadEntregada() : BigDecimal.ZERO);
            recMaterial.setCantidadSolicitada(unaRecetaMaterial.getCantidadSolicitada() != null ? unaRecetaMaterial.getCantidadSolicitada(): BigDecimal.ZERO);
            
            unArrayOfReceta.getRecetaMaterial().add(recMaterial);                 
        }        
        JAXBElement<ArrayOfRecetaMaterial> arrayOfReceta = factory.createRecetaMateriales(unArrayOfReceta);
        receta.setMateriales(arrayOfReceta);
        
        String nombreCompletoMedico = dr.getMedicoNombre();
        String[] nombreMedico = nombreCompletoMedico.split(" ");
        String nombreMed = "";
        String apMedicoPaterno = "";
        String apMedicoMaterno = "";
        
        if(nombreMedico.length == 0) {
            apMedicoPaterno = "TRIAGE";
            nombreMed = "TRIAGE";
            
        } else if(nombreMedico.length == 1) {
            apMedicoPaterno = nombreMedico[0];
            nombreMed = nombreMedico[0];
            
        } else if(nombreMedico.length == 2) {
            apMedicoPaterno = nombreMedico[nombreMedico.length - 1];
            nombreMed = nombreMedico[nombreMedico.length - 2];
            
        } else if(nombreMedico.length > 2) {
            apMedicoPaterno = nombreMedico[nombreMedico.length - 2];
            apMedicoMaterno = nombreMedico[nombreMedico.length - 1];
            
            for(int i = 0; i < nombreMedico.length - 2; i ++) {
                nombreMed = nombreMed.concat(nombreMedico[i]+ " ");

            }  
        }       
        String cedulaMedico = "CEMED12547";
        if(dr.getMedicoCedula() != null) {
            cedulaMedico = dr.getMedicoCedula();
        }
        JAXBElement<java.lang.String> apellidoMaterno = factory.createMedicoApellidoMaterno(apMedicoMaterno);
        JAXBElement<java.lang.String> apellidoPaterno = factory.createMedicoApellidoPaterno(apMedicoPaterno);        
        JAXBElement<java.lang.String> medicoNombre = factory.createMedicoNombre(nombreMed);
        JAXBElement<java.lang.String> cedula = factory.createMedicoCedula(cedulaMedico);
        Medico unMedico = factory.createMedico();
        unMedico.setApellidoMaterno(apellidoMaterno);
        unMedico.setApellidoPaterno(apellidoPaterno);
        unMedico.setCedula(cedula);
        unMedico.setNombre(medicoNombre);
        JAXBElement<Medico> medicoJax = factory.createMedico(unMedico);
        receta.setMedico(medicoJax);

        Paciente unPaciente = new Paciente();
        JAXBElement<java.lang.String> apPaternoPaciente = factory.createPacienteApellidoPaterno(dr.getApellidoPaterno());
        unPaciente.setApellidoPaterno(apPaternoPaciente);
        JAXBElement<java.lang.String> apMaternoPaciente = factory.createPacienteApellidoMaterno(dr.getApellidoMaterno());
        unPaciente.setApellidoMaterno(apMaternoPaciente);
        JAXBElement<java.lang.String> nombrePaciente = factory.createPacienteNombre(dr.getNombre());
        unPaciente.setNombre(nombrePaciente);
        JAXBElement<java.lang.String> sexo = factory.createPacienteSexo(dr.getSexo());
        unPaciente.setSexo(sexo);
        String fechaNacimiento = formato.format(dr.getFechaNacimiento());        
        JAXBElement<javax.xml.datatype.XMLGregorianCalendar> fechaPacienteNacimiento = factory.createPacienteFechaNacimiento(javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar(fechaNacimiento));
        unPaciente.setFechaNacimiento(fechaPacienteNacimiento);
        JAXBElement<java.lang.String> padecimiento = factory.createPacientePadecimiento(dr.getPadecimiento());
        unPaciente.setPadecimiento(padecimiento);
        JAXBElement<java.lang.String> programa = factory.createPacientePrograma(dr.getPrograma());
        unPaciente.setPrograma(programa);
        JAXBElement<java.lang.String> numeroAfiliacion = factory.createPacienteNumeroAfiliacion(dr.getNumeroAfiliacion());
        unPaciente.setNumeroAfiliacion(numeroAfiliacion);
        JAXBElement<Paciente> paciente = factory.createPaciente(unPaciente);
        receta.setPaciente(paciente);
        
        try {
            output = port.createRecetaIssemym(usuario, receta);
            if(output != null){
                LOGGER.info("createRecetaIssemym.result={0}" , output.toString());
            }            
        } catch (IRecetaSAFWSServiceCreateRecetaIssemymRecetaSAFWSExceptionFaultFaultMessage e) {
            LOGGER.error("Expected exception: IRecetaSAFWSService_CreateRecetaIssemym_RecetaSAFWSExceptionFault_FaultMessage has occurred.");

            if (e != null) {
                LOGGER.error(e.toString());
                if (e.getMessage()!= null) {
// TODO:    catch receta ya existe.
                    if (e.getMessage().contains("Receta ya existe, no es posible crear otra con el mismo folio")) {
                        output = new RecetaSAFWSResultado();
                        output.setCodigo(4);    // Receta ya Existe
                        JAXBElement<String> mensaje = factory.createRecetaFolio("Receta ya existe, no es posible crear otra con el mismo folio.");
                        output.setMensaje(mensaje);
                        
                    } else if (e.getMessage().contains("Usuario no válido"))  {
                        output = new RecetaSAFWSResultado();
                        output.setCodigo(1);    // Usuario no válido
                        JAXBElement<String> mensaje = factory.createRecetaFolio("Usuario no válido.");
                        output.setMensaje(mensaje);
                        
                    } else if (e.getMessage().contains("Acceso a farmacia no válido"))  {
                        output = new RecetaSAFWSResultado();
                        output.setCodigo(2);    // Acceso a farmacia no válido
                        JAXBElement<String> mensaje = factory.createRecetaFolio("Acceso a farmacia no válido.");
                        output.setMensaje(mensaje);
                        
                    } else if (e.getMessage().contains("Las posiciones de los materiales no son correctas"))  {
                        output = new RecetaSAFWSResultado();
                        output.setCodigo(6);    // Las posiciones de los materiales no son correctas
                        JAXBElement<String> mensaje = factory.createRecetaFolio("Las posiciones de los materiales no son correctas.");
                        output.setMensaje(mensaje);
                        
                    } else if (e.getMessage().contains("Material no encontrado"))  {
                        output = new RecetaSAFWSResultado();
                        output.setCodigo(7);    // Material no encontrado
                        JAXBElement<String> mensaje = factory.createRecetaFolio("Material no encontrado.");
                        output.setMensaje(mensaje);                
                    }
                }
            }
        }
        
        return output;
    }

    public void createColectivoIssemym(mx.mc.model.DimesaUsuario du, mx.mc.model.DimesaReceta dr) throws java.lang.Exception {
        LOGGER.trace("Invoking createColectivoIssemym...");
       
        ObjectFactory factory = new ObjectFactory();

        Usuario usuario = factory.createUsuario();
        JAXBElement<java.lang.String> contrasenia = factory.createUsuarioContrasenia(du.getContrasenia());
        usuario.setContrasenia(contrasenia);
        JAXBElement<java.lang.String> farmacia = factory.createUsuarioFarmacia(du.getFarmacia());
        usuario.setFarmacia(farmacia);
        JAXBElement<java.lang.String> nombre = factory.createUsuarioNombre(du.getNombre());
        usuario.setNombre(nombre);        
        
        Receta receta = new Receta();
        SimpleDateFormat formato = new SimpleDateFormat();
        formato.applyPattern("YYYY-MM-ddTHH:mm:ss");
        String fechaString = formato.format(dr.getFecha());
        receta.setFecha(javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar(fechaString));
        
        String fecReferencia = formato.format(dr.getFechaReferencia());        
        JAXBElement<javax.xml.datatype.XMLGregorianCalendar> fechaReferencia = factory.createRecetaFechaReferencia(javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar(fecReferencia));
        receta.setFechaReferencia(fechaReferencia);        
        JAXBElement<java.lang.String> folio = factory.createRecetaFolio(dr.getFolio());
        receta.setFolio(folio);        
        JAXBElement<java.lang.String> folioPago = factory.createRecetaFolioPago(dr.getFolioPago());
        receta.setFolioPago(folioPago);

        ArrayOfRecetaMaterial unArrayOfReceta = factory.createArrayOfRecetaMaterial();
        
        for(DimesaRecetaMaterial unaRecetaMaterial:dr.getMateriales()) {
            RecetaMaterial recMaterial = factory.createRecetaMaterial();
            JAXBElement<java.lang.String> claveInterna = factory.createRecetaMaterialClaveInterna(unaRecetaMaterial.getClaveInterna());        
            recMaterial.setClaveInterna(claveInterna);
            JAXBElement<java.lang.String> claveSap = factory.createRecetaMaterialClaveSAP(unaRecetaMaterial.getClaveSap());
            recMaterial.setClaveSAP(claveSap);
            recMaterial.setPosicion(unaRecetaMaterial.getPosision());
            recMaterial.setCantidadEntregada(unaRecetaMaterial.getCantidadEntregada() != null ? unaRecetaMaterial.getCantidadEntregada() : BigDecimal.ZERO);
            recMaterial.setCantidadSolicitada(unaRecetaMaterial.getCantidadSolicitada() != null ? unaRecetaMaterial.getCantidadSolicitada(): BigDecimal.ZERO);
            unArrayOfReceta.getRecetaMaterial().add(recMaterial);                 
        }
        JAXBElement<ArrayOfRecetaMaterial> arrayOfReceta = factory.createRecetaMateriales(unArrayOfReceta);
        receta.setMateriales(arrayOfReceta);
        
        JAXBElement<java.lang.String> apellidoMaterno = factory.createMedicoApellidoMaterno("Perez");
        JAXBElement<java.lang.String> apellidoPaterno = factory.createMedicoApellidoPaterno("Gomez");
        JAXBElement<java.lang.String> cedula = factory.createMedicoCedula("MED123456");
        JAXBElement<java.lang.String> nombreMedico = factory.createMedicoNombre("Joel");
        Medico unMedico = factory.createMedico();
        unMedico.setApellidoMaterno(apellidoMaterno);
        unMedico.setApellidoPaterno(apellidoPaterno);
        unMedico.setCedula(cedula);
        unMedico.setNombre(nombreMedico);
        JAXBElement<Medico> medicoJax = factory.createMedico(unMedico);
        receta.setMedico(medicoJax);

        try {
            RecetaSAFWSResultado output = port.createColectivoIssemym(usuario, receta);
            LOGGER.info("createRecetaIssemym.result={0}" , output);

        } catch (IRecetaSAFWSServiceCreateColectivoIssemymRecetaSAFWSExceptionFaultFaultMessage e) {
            LOGGER.error("Expected exception: IRecetaSAFWSServiceCreateColectivoIssemymRecetaSAFWSExceptionFaultFaultMessage has occurred.");
            LOGGER.error(e.toString());
        }
    }
    
    public ArrayOfTMAT entrega(mx.mc.model.DimesaUsuario du, String folioSolicitud) throws java.lang.Exception {
        LOGGER.trace("Invoking CreateColectivoIssemym...");

         ObjectFactory factory = new ObjectFactory();

        Usuario usuario = factory.createUsuario();
        JAXBElement<java.lang.String> contrasenia = factory.createUsuarioContrasenia(du.getContrasenia());
        usuario.setContrasenia(contrasenia);
        JAXBElement<java.lang.String> farmacia = factory.createUsuarioFarmacia(du.getFarmacia());
        usuario.setFarmacia(farmacia);
        JAXBElement<java.lang.String> nombre = factory.createUsuarioNombre(du.getNombre());
        usuario.setNombre(nombre);      
        mx.com.dimesa.ws.client.model.ArrayOfTMAT output = null;
        try {
            output = port.entrega(usuario, folioSolicitud);
            LOGGER.info("createRecetaIssemym.result={0}" , output);
        } catch (IRecetaSAFWSServiceEntregaRecetaSAFWSExceptionFaultFaultMessage e) {
            LOGGER.error("Expected exception: IRecetaSAFWSServiceCreateColectivoIssemymRecetaSAFWSExceptionFaultFaultMessage has occurred.");
            LOGGER.error(e.toString());
            throw new Exception( e.getMessage().replaceAll("mx.com.dimesa.ws.client.service.IRecetaSAFWSServiceEntregaRecetaSAFWSExceptionFaultFaultMessage", "Error") );
        }
        return output;
    }    
}
