
package mx.com.dimesa.ws.client.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import mx.com.dimesa.ws.client.arrays.ArrayOfstring;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.datacontract.schemas._2004._07.recetasafwsappcore package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private static final QName _Paciente_QNAME = new QName("http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", "Paciente");
    private static final QName _ArrayOfReceta_QNAME = new QName("http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", "ArrayOfReceta");
    private static final QName _RecetaSAFWSException_QNAME = new QName("http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Exception", "RecetaSAFWSException");
    private static final QName _TMAT_QNAME = new QName("http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", "T_MAT");
    private static final QName _RecetaSAFWSResultado_QNAME = new QName("http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", "RecetaSAFWSResultado");
    private static final QName _Usuario_QNAME = new QName("http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", "Usuario");
    private static final QName _Receta_QNAME = new QName("http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", "Receta");
    private static final QName _Medico_QNAME = new QName("http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", "Medico");
    private static final QName _ArrayOfTMAT_QNAME = new QName("http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", "ArrayOfT_MAT");
    private static final QName _ArrayOfRecetaMaterial_QNAME = new QName("http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", "ArrayOfRecetaMaterial");
    private static final QName _RecetaMaterial_QNAME = new QName("http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", "RecetaMaterial");
    private static final QName _RecetaSAFWSResultadoMensaje_QNAME = new QName("http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", "Mensaje");
    private static final QName _RecetaMaterialDosis_QNAME = new QName("http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", "Dosis");
    private static final QName _RecetaMaterialClave_QNAME = new QName("http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", "Clave");
    private static final QName _RecetaMaterialClaveInterna_QNAME = new QName("http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", "ClaveInterna");
    private static final QName _RecetaMaterialClaveSAP_QNAME = new QName("http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", "ClaveSAP");
    private static final QName _RecetaMaterialTratamiento_QNAME = new QName("http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", "Tratamiento");
    private static final QName _RecetaMaterialDescripcionSAP_QNAME = new QName("http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", "DescripcionSAP");
    private static final QName _RecetaSAFWSExceptionCodigo_QNAME = new QName("http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Exception", "Codigo");
    private static final QName _RecetaSAFWSExceptionMensaje_QNAME = new QName("http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Exception", "Mensaje");
    private static final QName _TMATFECHACADUCIDAD_QNAME = new QName("http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", "FECHA_CADUCIDAD");
    private static final QName _TMATLOTE_QNAME = new QName("http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", "LOTE");
    private static final QName _TMATCodigoBarras_QNAME = new QName("http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", "CodigoBarras");
    private static final QName _TMATCANTIDAD_QNAME = new QName("http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", "CANTIDAD");
    private static final QName _TMATMATERIAL_QNAME = new QName("http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", "MATERIAL");
    private static final QName _PacienteFechaNacimiento_QNAME = new QName("http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", "FechaNacimiento");
    private static final QName _PacienteNombre_QNAME = new QName("http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", "Nombre");
    private static final QName _PacienteApellidoMaterno_QNAME = new QName("http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", "ApellidoMaterno");
    private static final QName _PacientePrograma_QNAME = new QName("http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", "Programa");
    private static final QName _PacienteDescripcionPadecimiento_QNAME = new QName("http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", "DescripcionPadecimiento");
    private static final QName _PacienteApellidoPaterno_QNAME = new QName("http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", "ApellidoPaterno");
    private static final QName _PacienteNumeroAfiliacion_QNAME = new QName("http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", "NumeroAfiliacion");
    private static final QName _PacienteTelefono_QNAME = new QName("http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", "Telefono");
    private static final QName _PacienteDireccion_QNAME = new QName("http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", "Direccion");
    private static final QName _PacienteDescripcionPrograma_QNAME = new QName("http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", "DescripcionPrograma");
    private static final QName _PacientePadecimiento_QNAME = new QName("http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", "Padecimiento");
    private static final QName _PacienteSexo_QNAME = new QName("http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", "Sexo");
    private static final QName _RecetaPiso_QNAME = new QName("http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", "Piso");
    private static final QName _RecetaFechaReferencia_QNAME = new QName("http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", "FechaReferencia");
    private static final QName _RecetaTipo_QNAME = new QName("http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", "Tipo");
    private static final QName _RecetaDescripcionServicio_QNAME = new QName("http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", "DescripcionServicio");
    private static final QName _RecetaMateriales_QNAME = new QName("http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", "Materiales");
    private static final QName _RecetaFolio_QNAME = new QName("http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", "Folio");
    private static final QName _RecetaServicio_QNAME = new QName("http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", "Servicio");
    private static final QName _RecetaFolioPago_QNAME = new QName("http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", "FolioPago");
    private static final QName _RecetaEstatus_QNAME = new QName("http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", "Estatus");
    private static final QName _RecetaCama_QNAME = new QName("http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", "Cama");
    private static final QName _UsuarioContrasenia_QNAME = new QName("http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", "Contrasenia");
    private static final QName _UsuarioFarmacia_QNAME = new QName("http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", "Farmacia");
    private static final QName _MedicoCedula_QNAME = new QName("http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", "Cedula");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.datacontract.schemas._2004._07.recetasafwsappcore
     * 
     */
    public ObjectFactory() {
        //No code needed in constructor
    }

    /**
     * Create an instance of {@link RecetaSAFWSException }
     * 
     */
    public RecetaSAFWSException createRecetaSAFWSException() {
        return new RecetaSAFWSException();
    }

    /**
     * Create an instance of {@link Paciente }
     * 
     */
    public Paciente createPaciente() {
        return new Paciente();
    }

    /**
     * Create an instance of {@link Usuario }
     * 
     */
    public Usuario createUsuario() {
        return new Usuario();
    }

    /**
     * Create an instance of {@link RecetaMaterial }
     * 
     */
    public RecetaMaterial createRecetaMaterial() {
        return new RecetaMaterial();
    }

    /**
     * Create an instance of {@link ArrayOfReceta }
     * 
     */
    public ArrayOfReceta createArrayOfReceta() {
        return new ArrayOfReceta();
    }

    /**
     * Create an instance of {@link Receta }
     * 
     */
    public Receta createReceta() {
        return new Receta();
    }

    /**
     * Create an instance of {@link TMAT }
     * 
     */
    public TMAT createTMAT() {
        return new TMAT();
    }

    /**
     * Create an instance of {@link Medico }
     * 
     */
    public Medico createMedico() {
        return new Medico();
    }

    /**
     * Create an instance of {@link ArrayOfTMAT }
     * 
     */
    public ArrayOfTMAT createArrayOfTMAT() {
        return new ArrayOfTMAT();
    }

    /**
     * Create an instance of {@link ArrayOfRecetaMaterial }
     * 
     */
    public ArrayOfRecetaMaterial createArrayOfRecetaMaterial() {
        return new ArrayOfRecetaMaterial();
    }

    /**
     * Create an instance of {@link RecetaSAFWSResultado }
     * 
     */
    public RecetaSAFWSResultado createRecetaSAFWSResultado() {
        return new RecetaSAFWSResultado();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Paciente }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "Paciente")
    public JAXBElement<Paciente> createPaciente(Paciente value) {
        return new JAXBElement<>(_Paciente_QNAME, Paciente.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfReceta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "ArrayOfReceta")
    public JAXBElement<ArrayOfReceta> createArrayOfReceta(ArrayOfReceta value) {
        return new JAXBElement<>(_ArrayOfReceta_QNAME, ArrayOfReceta.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RecetaSAFWSException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Exception", name = "RecetaSAFWSException")
    public JAXBElement<RecetaSAFWSException> createRecetaSAFWSException(RecetaSAFWSException value) {
        return new JAXBElement<>(_RecetaSAFWSException_QNAME, RecetaSAFWSException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TMAT }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "T_MAT")
    public JAXBElement<TMAT> createTMAT(TMAT value) {
        return new JAXBElement<>(_TMAT_QNAME, TMAT.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RecetaSAFWSResultado }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "RecetaSAFWSResultado")
    public JAXBElement<RecetaSAFWSResultado> createRecetaSAFWSResultado(RecetaSAFWSResultado value) {
        return new JAXBElement<>(_RecetaSAFWSResultado_QNAME, RecetaSAFWSResultado.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Usuario }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "Usuario")
    public JAXBElement<Usuario> createUsuario(Usuario value) {
        return new JAXBElement<>(_Usuario_QNAME, Usuario.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Receta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "Receta")
    public JAXBElement<Receta> createReceta(Receta value) {
        return new JAXBElement<>(_Receta_QNAME, Receta.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Medico }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "Medico")
    public JAXBElement<Medico> createMedico(Medico value) {
        return new JAXBElement<>(_Medico_QNAME, Medico.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfTMAT }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "ArrayOfT_MAT")
    public JAXBElement<ArrayOfTMAT> createArrayOfTMAT(ArrayOfTMAT value) {
        return new JAXBElement<>(_ArrayOfTMAT_QNAME, ArrayOfTMAT.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfRecetaMaterial }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "ArrayOfRecetaMaterial")
    public JAXBElement<ArrayOfRecetaMaterial> createArrayOfRecetaMaterial(ArrayOfRecetaMaterial value) {
        return new JAXBElement<>(_ArrayOfRecetaMaterial_QNAME, ArrayOfRecetaMaterial.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RecetaMaterial }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "RecetaMaterial")
    public JAXBElement<RecetaMaterial> createRecetaMaterial(RecetaMaterial value) {
        return new JAXBElement<>(_RecetaMaterial_QNAME, RecetaMaterial.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "Mensaje", scope = RecetaSAFWSResultado.class)
    public JAXBElement<String> createRecetaSAFWSResultadoMensaje(String value) {
        return new JAXBElement<>(_RecetaSAFWSResultadoMensaje_QNAME, String.class, RecetaSAFWSResultado.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "Dosis", scope = RecetaMaterial.class)
    public JAXBElement<String> createRecetaMaterialDosis(String value) {
        return new JAXBElement<>(_RecetaMaterialDosis_QNAME, String.class, RecetaMaterial.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "Clave", scope = RecetaMaterial.class)
    public JAXBElement<String> createRecetaMaterialClave(String value) {
        return new JAXBElement<>(_RecetaMaterialClave_QNAME, String.class, RecetaMaterial.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "ClaveInterna", scope = RecetaMaterial.class)
    public JAXBElement<String> createRecetaMaterialClaveInterna(String value) {
        return new JAXBElement<>(_RecetaMaterialClaveInterna_QNAME, String.class, RecetaMaterial.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "ClaveSAP", scope = RecetaMaterial.class)
    public JAXBElement<String> createRecetaMaterialClaveSAP(String value) {
        return new JAXBElement<>(_RecetaMaterialClaveSAP_QNAME, String.class, RecetaMaterial.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "Tratamiento", scope = RecetaMaterial.class)
    public JAXBElement<String> createRecetaMaterialTratamiento(String value) {
        return new JAXBElement<>(_RecetaMaterialTratamiento_QNAME, String.class, RecetaMaterial.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "DescripcionSAP", scope = RecetaMaterial.class)
    public JAXBElement<String> createRecetaMaterialDescripcionSAP(String value) {
        return new JAXBElement<>(_RecetaMaterialDescripcionSAP_QNAME, String.class, RecetaMaterial.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Exception", name = "Codigo", scope = RecetaSAFWSException.class)
    public JAXBElement<String> createRecetaSAFWSExceptionCodigo(String value) {
        return new JAXBElement<>(_RecetaSAFWSExceptionCodigo_QNAME, String.class, RecetaSAFWSException.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Exception", name = "Mensaje", scope = RecetaSAFWSException.class)
    public JAXBElement<String> createRecetaSAFWSExceptionMensaje(String value) {
        return new JAXBElement<>(_RecetaSAFWSExceptionMensaje_QNAME, String.class, RecetaSAFWSException.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "FECHA_CADUCIDAD", scope = TMAT.class)
    public JAXBElement<String> createTMATFECHACADUCIDAD(String value) {
        return new JAXBElement<>(_TMATFECHACADUCIDAD_QNAME, String.class, TMAT.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "LOTE", scope = TMAT.class)
    public JAXBElement<String> createTMATLOTE(String value) {
        return new JAXBElement<>(_TMATLOTE_QNAME, String.class, TMAT.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfstring }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "CodigoBarras", scope = TMAT.class)
    public JAXBElement<ArrayOfstring> createTMATCodigoBarras(ArrayOfstring value) {
        return new JAXBElement<>(_TMATCodigoBarras_QNAME, ArrayOfstring.class, TMAT.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "CANTIDAD", scope = TMAT.class)
    public JAXBElement<String> createTMATCANTIDAD(String value) {
        return new JAXBElement<>(_TMATCANTIDAD_QNAME, String.class, TMAT.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "MATERIAL", scope = TMAT.class)
    public JAXBElement<String> createTMATMATERIAL(String value) {
        return new JAXBElement<>(_TMATMATERIAL_QNAME, String.class, TMAT.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "FechaNacimiento", scope = Paciente.class)
    public JAXBElement<XMLGregorianCalendar> createPacienteFechaNacimiento(XMLGregorianCalendar value) {
        return new JAXBElement<>(_PacienteFechaNacimiento_QNAME, XMLGregorianCalendar.class, Paciente.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "Nombre", scope = Paciente.class)
    public JAXBElement<String> createPacienteNombre(String value) {
        return new JAXBElement<>(_PacienteNombre_QNAME, String.class, Paciente.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "ApellidoMaterno", scope = Paciente.class)
    public JAXBElement<String> createPacienteApellidoMaterno(String value) {
        return new JAXBElement<>(_PacienteApellidoMaterno_QNAME, String.class, Paciente.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "Programa", scope = Paciente.class)
    public JAXBElement<String> createPacientePrograma(String value) {
        return new JAXBElement<>(_PacientePrograma_QNAME, String.class, Paciente.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "DescripcionPadecimiento", scope = Paciente.class)
    public JAXBElement<String> createPacienteDescripcionPadecimiento(String value) {
        return new JAXBElement<>(_PacienteDescripcionPadecimiento_QNAME, String.class, Paciente.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "ApellidoPaterno", scope = Paciente.class)
    public JAXBElement<String> createPacienteApellidoPaterno(String value) {
        return new JAXBElement<>(_PacienteApellidoPaterno_QNAME, String.class, Paciente.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "NumeroAfiliacion", scope = Paciente.class)
    public JAXBElement<String> createPacienteNumeroAfiliacion(String value) {
        return new JAXBElement<>(_PacienteNumeroAfiliacion_QNAME, String.class, Paciente.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "Telefono", scope = Paciente.class)
    public JAXBElement<String> createPacienteTelefono(String value) {
        return new JAXBElement<>(_PacienteTelefono_QNAME, String.class, Paciente.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "Direccion", scope = Paciente.class)
    public JAXBElement<String> createPacienteDireccion(String value) {
        return new JAXBElement<>(_PacienteDireccion_QNAME, String.class, Paciente.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "DescripcionPrograma", scope = Paciente.class)
    public JAXBElement<String> createPacienteDescripcionPrograma(String value) {
        return new JAXBElement<>(_PacienteDescripcionPrograma_QNAME, String.class, Paciente.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "Padecimiento", scope = Paciente.class)
    public JAXBElement<String> createPacientePadecimiento(String value) {
        return new JAXBElement<>(_PacientePadecimiento_QNAME, String.class, Paciente.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "Sexo", scope = Paciente.class)
    public JAXBElement<String> createPacienteSexo(String value) {
        return new JAXBElement<>(_PacienteSexo_QNAME, String.class, Paciente.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Paciente }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "Paciente", scope = Receta.class)
    public JAXBElement<Paciente> createRecetaPaciente(Paciente value) {
        return new JAXBElement<>(_Paciente_QNAME, Paciente.class, Receta.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "Piso", scope = Receta.class)
    public JAXBElement<String> createRecetaPiso(String value) {
        return new JAXBElement<>(_RecetaPiso_QNAME, String.class, Receta.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "FechaReferencia", scope = Receta.class)
    public JAXBElement<XMLGregorianCalendar> createRecetaFechaReferencia(XMLGregorianCalendar value) {
        return new JAXBElement<>(_RecetaFechaReferencia_QNAME, XMLGregorianCalendar.class, Receta.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "Tipo", scope = Receta.class)
    public JAXBElement<String> createRecetaTipo(String value) {
        return new JAXBElement<>(_RecetaTipo_QNAME, String.class, Receta.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "DescripcionServicio", scope = Receta.class)
    public JAXBElement<String> createRecetaDescripcionServicio(String value) {
        return new JAXBElement<>(_RecetaDescripcionServicio_QNAME, String.class, Receta.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfRecetaMaterial }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "Materiales", scope = Receta.class)
    public JAXBElement<ArrayOfRecetaMaterial> createRecetaMateriales(ArrayOfRecetaMaterial value) {
        return new JAXBElement<>(_RecetaMateriales_QNAME, ArrayOfRecetaMaterial.class, Receta.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "Folio", scope = Receta.class)
    public JAXBElement<String> createRecetaFolio(String value) {
        return new JAXBElement<>(_RecetaFolio_QNAME, String.class, Receta.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "Servicio", scope = Receta.class)
    public JAXBElement<String> createRecetaServicio(String value) {
        return new JAXBElement<>(_RecetaServicio_QNAME, String.class, Receta.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "FolioPago", scope = Receta.class)
    public JAXBElement<String> createRecetaFolioPago(String value) {
        return new JAXBElement<>(_RecetaFolioPago_QNAME, String.class, Receta.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Medico }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "Medico", scope = Receta.class)
    public JAXBElement<Medico> createRecetaMedico(Medico value) {
        return new JAXBElement<>(_Medico_QNAME, Medico.class, Receta.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "Estatus", scope = Receta.class)
    public JAXBElement<String> createRecetaEstatus(String value) {
        return new JAXBElement<>(_RecetaEstatus_QNAME, String.class, Receta.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "Cama", scope = Receta.class)
    public JAXBElement<String> createRecetaCama(String value) {
        return new JAXBElement<>(_RecetaCama_QNAME, String.class, Receta.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "Contrasenia", scope = Usuario.class)
    public JAXBElement<String> createUsuarioContrasenia(String value) {
        return new JAXBElement<>(_UsuarioContrasenia_QNAME, String.class, Usuario.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "Nombre", scope = Usuario.class)
    public JAXBElement<String> createUsuarioNombre(String value) {
        return new JAXBElement<>(_PacienteNombre_QNAME, String.class, Usuario.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "Farmacia", scope = Usuario.class)
    public JAXBElement<String> createUsuarioFarmacia(String value) {
        return new JAXBElement<>(_UsuarioFarmacia_QNAME, String.class, Usuario.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "Nombre", scope = Medico.class)
    public JAXBElement<String> createMedicoNombre(String value) {
        return new JAXBElement<>(_PacienteNombre_QNAME, String.class, Medico.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "ApellidoMaterno", scope = Medico.class)
    public JAXBElement<String> createMedicoApellidoMaterno(String value) {
        return new JAXBElement<>(_PacienteApellidoMaterno_QNAME, String.class, Medico.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "ApellidoPaterno", scope = Medico.class)
    public JAXBElement<String> createMedicoApellidoPaterno(String value) {
        return new JAXBElement<>(_PacienteApellidoPaterno_QNAME, String.class, Medico.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", name = "Cedula", scope = Medico.class)
    public JAXBElement<String> createMedicoCedula(String value) {
        return new JAXBElement<>(_MedicoCedula_QNAME, String.class, Medico.class, value);
    }

}
