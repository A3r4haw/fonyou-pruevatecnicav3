
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package mx.com.dimesa.ws.client.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class was generated by Apache CXF 2.5.2
 * 2018-03-01T11:38:42.365-06:00
 * Generated source version: 2.5.2
 * 
 */

@javax.jws.WebService(
                      serviceName = "RecetaSAFWSService",
                      portName = "BasicHttpBinding_IRecetaSAFWSService",
                      targetNamespace = "http://tempuri.org/",
                      wsdlLocation = "http://svcsafb2b.dimesa.com.mx/RecetaSAFWSService.svc?wsdl",
                      endpointInterface = "org.tempuri.IRecetaSAFWSService")
                      
public class IRecetaSAFWSServiceImpl implements IRecetaSAFWSService {
    private static final Logger LOGGER = LoggerFactory.getLogger(IRecetaSAFWSServiceImpl.class);

    /* (non-Javadoc)
     * @see org.tempuri.IRecetaSAFWSService#entrega(org.datacontract.schemas._2004._07.recetasafwsappcore.Usuario  usuario ,)java.lang.String  codigo )*
     */
    public mx.com.dimesa.ws.client.model.ArrayOfTMAT entrega(mx.com.dimesa.ws.client.model.Usuario usuario,java.lang.String codigo) throws IRecetaSAFWSServiceEntregaRecetaSAFWSExceptionFaultFaultMessage    { 
        LOGGER.info("Executing operation entrega");
        try {
            mx.com.dimesa.ws.client.model.ArrayOfTMAT returnn = new mx.com.dimesa.ws.client.model.ArrayOfTMAT();
            java.util.List<mx.com.dimesa.ws.client.model.TMAT> returnTMAT = new java.util.ArrayList<>();
            mx.com.dimesa.ws.client.model.TMAT returnTMATVal1 = new mx.com.dimesa.ws.client.model.TMAT();
            javax.xml.bind.JAXBElement<java.lang.String> returnTMATVal1CANTIDAD = null;
            returnTMATVal1.setCANTIDAD(returnTMATVal1CANTIDAD);
            javax.xml.bind.JAXBElement<mx.com.dimesa.ws.client.arrays.ArrayOfstring> returnTMATVal1CodigoBarras = null;
            returnTMATVal1.setCodigoBarras(returnTMATVal1CodigoBarras);
            javax.xml.bind.JAXBElement<java.lang.String> returnTMATVal1FECHACADUCIDAD = null;
            returnTMATVal1.setFECHACADUCIDAD(returnTMATVal1FECHACADUCIDAD);
            javax.xml.bind.JAXBElement<java.lang.String> returnTMATVal1LOTE = null;
            returnTMATVal1.setLOTE(returnTMATVal1LOTE);
            javax.xml.bind.JAXBElement<java.lang.String> returnTMATVal1MATERIAL = null;
            returnTMATVal1.setMATERIAL(returnTMATVal1MATERIAL);
            returnTMAT.add(returnTMATVal1);
            returnn.getTMAT().addAll(returnTMAT);
            return returnn;
        } catch (java.lang.Exception ex) {
            LOGGER.error(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see org.tempuri.IRecetaSAFWSService#cancelReceta(org.datacontract.schemas._2004._07.recetasafwsappcore.Usuario  usuario ,)org.datacontract.schemas._2004._07.recetasafwsappcore.Receta  receta )*
     */
    public mx.com.dimesa.ws.client.model.RecetaSAFWSResultado cancelReceta(mx.com.dimesa.ws.client.model.Usuario usuario,mx.com.dimesa.ws.client.model.Receta receta) throws IRecetaSAFWSServiceCancelRecetaRecetaSAFWSExceptionFaultFaultMessage    { 
        LOGGER.info("Executing operation cancelReceta");
        try {
            mx.com.dimesa.ws.client.model.RecetaSAFWSResultado returnn = new mx.com.dimesa.ws.client.model.RecetaSAFWSResultado();
            returnn.setCodigo(Integer.valueOf(-1866141850));
            javax.xml.bind.JAXBElement<java.lang.String> returnMensaje = null;
            returnn.setMensaje(returnMensaje);
            return returnn;
        } catch (java.lang.Exception ex) {
            LOGGER.error(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see org.tempuri.IRecetaSAFWSService#getReceta(org.datacontract.schemas._2004._07.recetasafwsappcore.Usuario  usuario ,)org.datacontract.schemas._2004._07.recetasafwsappcore.ArrayOfReceta  recetas )*
     */
    public mx.com.dimesa.ws.client.model.ArrayOfReceta getReceta(mx.com.dimesa.ws.client.model.Usuario usuario,mx.com.dimesa.ws.client.model.ArrayOfReceta recetas) throws IRecetaSAFWSServiceGetRecetaRecetaSAFWSExceptionFaultFaultMessage    { 
        LOGGER.info("Executing operation getReceta");
        try {
            mx.com.dimesa.ws.client.model.ArrayOfReceta returnn = new mx.com.dimesa.ws.client.model.ArrayOfReceta();
            java.util.List<mx.com.dimesa.ws.client.model.Receta> returnReceta = new java.util.ArrayList<>();
            mx.com.dimesa.ws.client.model.Receta returnRecetaVal1 = new mx.com.dimesa.ws.client.model.Receta();
            javax.xml.bind.JAXBElement<java.lang.String> returnRecetaVal1Cama = null;
            returnRecetaVal1.setCama(returnRecetaVal1Cama);
            javax.xml.bind.JAXBElement<java.lang.String> returnRecetaVal1DescripcionServicio = null;
            returnRecetaVal1.setDescripcionServicio(returnRecetaVal1DescripcionServicio);
            javax.xml.bind.JAXBElement<java.lang.String> returnRecetaVal1Estatus = null;
            returnRecetaVal1.setEstatus(returnRecetaVal1Estatus);
            returnRecetaVal1.setFecha(javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar("2018-03-01T11:38:42.371-06:00"));
            javax.xml.bind.JAXBElement<javax.xml.datatype.XMLGregorianCalendar> returnRecetaVal1FechaReferencia = null;
            returnRecetaVal1.setFechaReferencia(returnRecetaVal1FechaReferencia);
            javax.xml.bind.JAXBElement<java.lang.String> returnRecetaVal1Folio = null;
            returnRecetaVal1.setFolio(returnRecetaVal1Folio);
            javax.xml.bind.JAXBElement<java.lang.String> returnRecetaVal1FolioPago = null;
            returnRecetaVal1.setFolioPago(returnRecetaVal1FolioPago);
            javax.xml.bind.JAXBElement<mx.com.dimesa.ws.client.model.ArrayOfRecetaMaterial> returnRecetaVal1Materiales = null;
            returnRecetaVal1.setMateriales(returnRecetaVal1Materiales);
            javax.xml.bind.JAXBElement<mx.com.dimesa.ws.client.model.Medico> returnRecetaVal1Medico = null;
            returnRecetaVal1.setMedico(returnRecetaVal1Medico);
            javax.xml.bind.JAXBElement<mx.com.dimesa.ws.client.model.Paciente> returnRecetaVal1Paciente = null;
            returnRecetaVal1.setPaciente(returnRecetaVal1Paciente);
            javax.xml.bind.JAXBElement<java.lang.String> returnRecetaVal1Piso = null;
            returnRecetaVal1.setPiso(returnRecetaVal1Piso);
            javax.xml.bind.JAXBElement<java.lang.String> returnRecetaVal1Servicio = null;
            returnRecetaVal1.setServicio(returnRecetaVal1Servicio);
            javax.xml.bind.JAXBElement<java.lang.String> returnRecetaVal1Tipo = null;
            returnRecetaVal1.setTipo(returnRecetaVal1Tipo);
            returnReceta.add(returnRecetaVal1);
            returnn.getReceta().addAll(returnReceta);
            return returnn;
        } catch (java.lang.Exception ex) {
            LOGGER.error(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see org.tempuri.IRecetaSAFWSService#createColectivoIssemym(org.datacontract.schemas._2004._07.recetasafwsappcore.Usuario  usuario ,)org.datacontract.schemas._2004._07.recetasafwsappcore.Receta  receta )*
     */
    public mx.com.dimesa.ws.client.model.RecetaSAFWSResultado createColectivoIssemym(mx.com.dimesa.ws.client.model.Usuario usuario,mx.com.dimesa.ws.client.model.Receta receta) throws IRecetaSAFWSServiceCreateColectivoIssemymRecetaSAFWSExceptionFaultFaultMessage    { 
        LOGGER.info("Executing operation createColectivoIssemym");
        try {
            mx.com.dimesa.ws.client.model.RecetaSAFWSResultado returnn = new mx.com.dimesa.ws.client.model.RecetaSAFWSResultado();
            returnn.setCodigo(Integer.valueOf(-345748464));
            javax.xml.bind.JAXBElement<java.lang.String> returnMensaje = null;
            returnn.setMensaje(returnMensaje);
            return returnn;
        } catch (java.lang.Exception ex) {
            LOGGER.error(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see org.tempuri.IRecetaSAFWSService#createColectivo(org.datacontract.schemas._2004._07.recetasafwsappcore.Usuario  usuario ,)org.datacontract.schemas._2004._07.recetasafwsappcore.Receta  receta )*
     */
    public mx.com.dimesa.ws.client.model.RecetaSAFWSResultado createColectivo(mx.com.dimesa.ws.client.model.Usuario usuario,mx.com.dimesa.ws.client.model.Receta receta) throws IRecetaSAFWSServiceCreateColectivoRecetaSAFWSExceptionFaultFaultMessage    { 
        LOGGER.info("Executing operation createColectivo");
        try {
            mx.com.dimesa.ws.client.model.RecetaSAFWSResultado returnn = new mx.com.dimesa.ws.client.model.RecetaSAFWSResultado();
            returnn.setCodigo(Integer.valueOf(-301146757));
            javax.xml.bind.JAXBElement<java.lang.String> returnMensaje = null;
            returnn.setMensaje(returnMensaje);
            return returnn;
        } catch (java.lang.Exception ex) {
            LOGGER.error(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see org.tempuri.IRecetaSAFWSService#createReceta(org.datacontract.schemas._2004._07.recetasafwsappcore.Usuario  usuario ,)org.datacontract.schemas._2004._07.recetasafwsappcore.Receta  receta )*
     */
    public mx.com.dimesa.ws.client.model.RecetaSAFWSResultado createReceta(mx.com.dimesa.ws.client.model.Usuario usuario,mx.com.dimesa.ws.client.model.Receta receta) throws IRecetaSAFWSServiceCreateRecetaRecetaSAFWSExceptionFaultFaultMessage    { 
        LOGGER.info("Executing operation createReceta");
        try {
            mx.com.dimesa.ws.client.model.RecetaSAFWSResultado returnn = new mx.com.dimesa.ws.client.model.RecetaSAFWSResultado();
            returnn.setCodigo(Integer.valueOf(-497820231));
            javax.xml.bind.JAXBElement<java.lang.String> returnMensaje = null;
            returnn.setMensaje(returnMensaje);
            return returnn;
        } catch (java.lang.Exception ex) {
            LOGGER.error(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see org.tempuri.IRecetaSAFWSService#inventario(org.datacontract.schemas._2004._07.recetasafwsappcore.Usuario  usuario ,)java.lang.String  claveSAP )*
     */
    public java.lang.Long inventario(mx.com.dimesa.ws.client.model.Usuario usuario,java.lang.String claveSAP) throws IRecetaSAFWSServiceInventarioRecetaSAFWSExceptionFaultFaultMessage    { 
        LOGGER.info("Executing operation inventario");
        try {
            return -4957358345536750635l;
        } catch (java.lang.Exception ex) {
            LOGGER.error(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see org.tempuri.IRecetaSAFWSService#createRecetaMaestra(org.datacontract.schemas._2004._07.recetasafwsappcore.Usuario  usuario ,)org.datacontract.schemas._2004._07.recetasafwsappcore.Receta  receta )*
     */
    public mx.com.dimesa.ws.client.model.RecetaSAFWSResultado createRecetaMaestra(mx.com.dimesa.ws.client.model.Usuario usuario,mx.com.dimesa.ws.client.model.Receta receta) throws IRecetaSAFWSServiceCreateRecetaMaestraRecetaSAFWSExceptionFaultFaultMessage    { 
        LOGGER.info("Executing operation createRecetaMaestra");
        try {
            mx.com.dimesa.ws.client.model.RecetaSAFWSResultado returnn = new mx.com.dimesa.ws.client.model.RecetaSAFWSResultado();
            returnn.setCodigo(Integer.valueOf(-1496122531));
            javax.xml.bind.JAXBElement<java.lang.String> returnMensaje = null;
            returnn.setMensaje(returnMensaje);
            return returnn;
        } catch (java.lang.Exception ex) {
            LOGGER.error(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see org.tempuri.IRecetaSAFWSService#createRecetaIssemym(org.datacontract.schemas._2004._07.recetasafwsappcore.Usuario  usuario ,)org.datacontract.schemas._2004._07.recetasafwsappcore.Receta  receta )*
     */
    public mx.com.dimesa.ws.client.model.RecetaSAFWSResultado createRecetaIssemym(mx.com.dimesa.ws.client.model.Usuario usuario,mx.com.dimesa.ws.client.model.Receta receta) throws IRecetaSAFWSServiceCreateRecetaIssemymRecetaSAFWSExceptionFaultFaultMessage    { 
        LOGGER.info("Executing operation createRecetaIssemym");
        try {
            mx.com.dimesa.ws.client.model.RecetaSAFWSResultado returnn = new mx.com.dimesa.ws.client.model.RecetaSAFWSResultado();
            returnn.setCodigo(Integer.valueOf(-1298276757));
            javax.xml.bind.JAXBElement<java.lang.String> returnMensaje = null;
            returnn.setMensaje(returnMensaje);
            return returnn;
        } catch (java.lang.Exception ex) {
            LOGGER.error(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

}
