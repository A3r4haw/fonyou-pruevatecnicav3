
package mx.com.dimesa.ws.client.service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import mx.com.dimesa.ws.client.model.ArrayOfReceta;
import mx.com.dimesa.ws.client.model.ArrayOfTMAT;
import mx.com.dimesa.ws.client.model.Receta;
import mx.com.dimesa.ws.client.model.RecetaSAFWSResultado;
import mx.com.dimesa.ws.client.model.Usuario;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.tempuri package. 
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

    private final static QName _CancelRecetaResponseCancelRecetaResult_QNAME = new QName("http://tempuri.org/", "CancelRecetaResult");
    private final static QName _EntregaResponseEntregaResult_QNAME = new QName("http://tempuri.org/", "EntregaResult");
    private final static QName _CreateRecetaIssemymReceta_QNAME = new QName("http://tempuri.org/", "receta");
    private final static QName _CreateRecetaIssemymUsuario_QNAME = new QName("http://tempuri.org/", "usuario");
    private final static QName _InventarioClaveSAP_QNAME = new QName("http://tempuri.org/", "ClaveSAP");
    private final static QName _EntregaCodigo_QNAME = new QName("http://tempuri.org/", "codigo");
    private final static QName _CreateColectivoResponseCreateColectivoResult_QNAME = new QName("http://tempuri.org/", "CreateColectivoResult");
    private final static QName _CreateRecetaResponseCreateRecetaResult_QNAME = new QName("http://tempuri.org/", "CreateRecetaResult");
    private final static QName _CreateRecetaMaestraResponseCreateRecetaMaestraResult_QNAME = new QName("http://tempuri.org/", "CreateRecetaMaestraResult");
    private final static QName _CreateRecetaIssemymResponseCreateRecetaIssemymResult_QNAME = new QName("http://tempuri.org/", "CreateRecetaIssemymResult");
    private final static QName _GetRecetaRecetas_QNAME = new QName("http://tempuri.org/", "recetas");
    private final static QName _CreateColectivoIssemymResponseCreateColectivoIssemymResult_QNAME = new QName("http://tempuri.org/", "CreateColectivoIssemymResult");
    private final static QName _GetRecetaResponseGetRecetaResult_QNAME = new QName("http://tempuri.org/", "GetRecetaResult");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.tempuri
     * 
     */
    public ObjectFactory() {
        //No code needed in constructor
    }

    /**
     * Create an instance of {@link CreateRecetaResponse }
     * 
     */
    public CreateRecetaResponse createCreateRecetaResponse() {
        return new CreateRecetaResponse();
    }

    /**
     * Create an instance of {@link InventarioResponse }
     * 
     */
    public InventarioResponse createInventarioResponse() {
        return new InventarioResponse();
    }

    /**
     * Create an instance of {@link Entrega }
     * 
     */
    public Entrega createEntrega() {
        return new Entrega();
    }

    /**
     * Create an instance of {@link CreateRecetaMaestraResponse }
     * 
     */
    public CreateRecetaMaestraResponse createCreateRecetaMaestraResponse() {
        return new CreateRecetaMaestraResponse();
    }

    /**
     * Create an instance of {@link EntregaResponse }
     * 
     */
    public EntregaResponse createEntregaResponse() {
        return new EntregaResponse();
    }

    /**
     * Create an instance of {@link CancelRecetaResponse }
     * 
     */
    public CancelRecetaResponse createCancelRecetaResponse() {
        return new CancelRecetaResponse();
    }

    /**
     * Create an instance of {@link CreateColectivo }
     * 
     */
    public CreateColectivo createCreateColectivo() {
        return new CreateColectivo();
    }

    /**
     * Create an instance of {@link Inventario }
     * 
     */
    public Inventario createInventario() {
        return new Inventario();
    }

    /**
     * Create an instance of {@link CreateRecetaIssemymResponse }
     * 
     */
    public CreateRecetaIssemymResponse createCreateRecetaIssemymResponse() {
        return new CreateRecetaIssemymResponse();
    }

    /**
     * Create an instance of {@link CancelReceta }
     * 
     */
    public CancelReceta createCancelReceta() {
        return new CancelReceta();
    }

    /**
     * Create an instance of {@link CreateColectivoIssemymResponse }
     * 
     */
    public CreateColectivoIssemymResponse createCreateColectivoIssemymResponse() {
        return new CreateColectivoIssemymResponse();
    }

    /**
     * Create an instance of {@link CreateColectivoIssemym }
     * 
     */
    public CreateColectivoIssemym createCreateColectivoIssemym() {
        return new CreateColectivoIssemym();
    }

    /**
     * Create an instance of {@link GetReceta }
     * 
     */
    public GetReceta createGetReceta() {
        return new GetReceta();
    }

    /**
     * Create an instance of {@link CreateReceta }
     * 
     */
    public CreateReceta createCreateReceta() {
        return new CreateReceta();
    }

    /**
     * Create an instance of {@link CreateColectivoResponse }
     * 
     */
    public CreateColectivoResponse createCreateColectivoResponse() {
        return new CreateColectivoResponse();
    }

    /**
     * Create an instance of {@link GetRecetaResponse }
     * 
     */
    public GetRecetaResponse createGetRecetaResponse() {
        return new GetRecetaResponse();
    }

    /**
     * Create an instance of {@link CreateRecetaIssemym }
     * 
     */
    public CreateRecetaIssemym createCreateRecetaIssemym() {
        return new CreateRecetaIssemym();
    }

    /**
     * Create an instance of {@link CreateRecetaMaestra }
     * 
     */
    public CreateRecetaMaestra createCreateRecetaMaestra() {
        return new CreateRecetaMaestra();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RecetaSAFWSResultado }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "CancelRecetaResult", scope = CancelRecetaResponse.class)
    public JAXBElement<RecetaSAFWSResultado> createCancelRecetaResponseCancelRecetaResult(RecetaSAFWSResultado value) {
        return new JAXBElement<>(_CancelRecetaResponseCancelRecetaResult_QNAME, RecetaSAFWSResultado.class, CancelRecetaResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfTMAT }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "EntregaResult", scope = EntregaResponse.class)
    public JAXBElement<ArrayOfTMAT> createEntregaResponseEntregaResult(ArrayOfTMAT value) {
        return new JAXBElement<>(_EntregaResponseEntregaResult_QNAME, ArrayOfTMAT.class, EntregaResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Receta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "receta", scope = CreateRecetaIssemym.class)
    public JAXBElement<Receta> createCreateRecetaIssemymReceta(Receta value) {
        return new JAXBElement<>(_CreateRecetaIssemymReceta_QNAME, Receta.class, CreateRecetaIssemym.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Usuario }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "usuario", scope = CreateRecetaIssemym.class)
    public JAXBElement<Usuario> createCreateRecetaIssemymUsuario(Usuario value) {
        return new JAXBElement<>(_CreateRecetaIssemymUsuario_QNAME, Usuario.class, CreateRecetaIssemym.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Usuario }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "usuario", scope = Inventario.class)
    public JAXBElement<Usuario> createInventarioUsuario(Usuario value) {
        return new JAXBElement<>(_CreateRecetaIssemymUsuario_QNAME, Usuario.class, Inventario.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "ClaveSAP", scope = Inventario.class)
    public JAXBElement<String> createInventarioClaveSAP(String value) {
        return new JAXBElement<>(_InventarioClaveSAP_QNAME, String.class, Inventario.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "codigo", scope = Entrega.class)
    public JAXBElement<String> createEntregaCodigo(String value) {
        return new JAXBElement<>(_EntregaCodigo_QNAME, String.class, Entrega.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Usuario }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "usuario", scope = Entrega.class)
    public JAXBElement<Usuario> createEntregaUsuario(Usuario value) {
        return new JAXBElement<>(_CreateRecetaIssemymUsuario_QNAME, Usuario.class, Entrega.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Receta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "receta", scope = CreateRecetaMaestra.class)
    public JAXBElement<Receta> createCreateRecetaMaestraReceta(Receta value) {
        return new JAXBElement<>(_CreateRecetaIssemymReceta_QNAME, Receta.class, CreateRecetaMaestra.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Usuario }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "usuario", scope = CreateRecetaMaestra.class)
    public JAXBElement<Usuario> createCreateRecetaMaestraUsuario(Usuario value) {
        return new JAXBElement<>(_CreateRecetaIssemymUsuario_QNAME, Usuario.class, CreateRecetaMaestra.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Receta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "receta", scope = CreateColectivo.class)
    public JAXBElement<Receta> createCreateColectivoReceta(Receta value) {
        return new JAXBElement<>(_CreateRecetaIssemymReceta_QNAME, Receta.class, CreateColectivo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Usuario }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "usuario", scope = CreateColectivo.class)
    public JAXBElement<Usuario> createCreateColectivoUsuario(Usuario value) {
        return new JAXBElement<>(_CreateRecetaIssemymUsuario_QNAME, Usuario.class, CreateColectivo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RecetaSAFWSResultado }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "CreateColectivoResult", scope = CreateColectivoResponse.class)
    public JAXBElement<RecetaSAFWSResultado> createCreateColectivoResponseCreateColectivoResult(RecetaSAFWSResultado value) {
        return new JAXBElement<>(_CreateColectivoResponseCreateColectivoResult_QNAME, RecetaSAFWSResultado.class, CreateColectivoResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RecetaSAFWSResultado }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "CreateRecetaResult", scope = CreateRecetaResponse.class)
    public JAXBElement<RecetaSAFWSResultado> createCreateRecetaResponseCreateRecetaResult(RecetaSAFWSResultado value) {
        return new JAXBElement<>(_CreateRecetaResponseCreateRecetaResult_QNAME, RecetaSAFWSResultado.class, CreateRecetaResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Receta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "receta", scope = CancelReceta.class)
    public JAXBElement<Receta> createCancelRecetaReceta(Receta value) {
        return new JAXBElement<>(_CreateRecetaIssemymReceta_QNAME, Receta.class, CancelReceta.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Usuario }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "usuario", scope = CancelReceta.class)
    public JAXBElement<Usuario> createCancelRecetaUsuario(Usuario value) {
        return new JAXBElement<>(_CreateRecetaIssemymUsuario_QNAME, Usuario.class, CancelReceta.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Receta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "receta", scope = CreateReceta.class)
    public JAXBElement<Receta> createCreateRecetaReceta(Receta value) {
        return new JAXBElement<>(_CreateRecetaIssemymReceta_QNAME, Receta.class, CreateReceta.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Usuario }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "usuario", scope = CreateReceta.class)
    public JAXBElement<Usuario> createCreateRecetaUsuario(Usuario value) {
        return new JAXBElement<>(_CreateRecetaIssemymUsuario_QNAME, Usuario.class, CreateReceta.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Receta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "receta", scope = CreateColectivoIssemym.class)
    public JAXBElement<Receta> createCreateColectivoIssemymReceta(Receta value) {
        return new JAXBElement<>(_CreateRecetaIssemymReceta_QNAME, Receta.class, CreateColectivoIssemym.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Usuario }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "usuario", scope = CreateColectivoIssemym.class)
    public JAXBElement<Usuario> createCreateColectivoIssemymUsuario(Usuario value) {
        return new JAXBElement<>(_CreateRecetaIssemymUsuario_QNAME, Usuario.class, CreateColectivoIssemym.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RecetaSAFWSResultado }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "CreateRecetaMaestraResult", scope = CreateRecetaMaestraResponse.class)
    public JAXBElement<RecetaSAFWSResultado> createCreateRecetaMaestraResponseCreateRecetaMaestraResult(RecetaSAFWSResultado value) {
        return new JAXBElement<>(_CreateRecetaMaestraResponseCreateRecetaMaestraResult_QNAME, RecetaSAFWSResultado.class, CreateRecetaMaestraResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RecetaSAFWSResultado }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "CreateRecetaIssemymResult", scope = CreateRecetaIssemymResponse.class)
    public JAXBElement<RecetaSAFWSResultado> createCreateRecetaIssemymResponseCreateRecetaIssemymResult(RecetaSAFWSResultado value) {
        return new JAXBElement<>(_CreateRecetaIssemymResponseCreateRecetaIssemymResult_QNAME, RecetaSAFWSResultado.class, CreateRecetaIssemymResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfReceta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "recetas", scope = GetReceta.class)
    public JAXBElement<ArrayOfReceta> createGetRecetaRecetas(ArrayOfReceta value) {
        return new JAXBElement<>(_GetRecetaRecetas_QNAME, ArrayOfReceta.class, GetReceta.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Usuario }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "usuario", scope = GetReceta.class)
    public JAXBElement<Usuario> createGetRecetaUsuario(Usuario value) {
        return new JAXBElement<>(_CreateRecetaIssemymUsuario_QNAME, Usuario.class, GetReceta.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RecetaSAFWSResultado }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "CreateColectivoIssemymResult", scope = CreateColectivoIssemymResponse.class)
    public JAXBElement<RecetaSAFWSResultado> createCreateColectivoIssemymResponseCreateColectivoIssemymResult(RecetaSAFWSResultado value) {
        return new JAXBElement<>(_CreateColectivoIssemymResponseCreateColectivoIssemymResult_QNAME, RecetaSAFWSResultado.class, CreateColectivoIssemymResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfReceta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "GetRecetaResult", scope = GetRecetaResponse.class)
    public JAXBElement<ArrayOfReceta> createGetRecetaResponseGetRecetaResult(ArrayOfReceta value) {
        return new JAXBElement<>(_GetRecetaResponseGetRecetaResult_QNAME, ArrayOfReceta.class, GetRecetaResponse.class, value);
    }

}
