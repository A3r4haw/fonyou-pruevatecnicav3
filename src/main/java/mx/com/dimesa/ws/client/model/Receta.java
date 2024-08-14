
package mx.com.dimesa.ws.client.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para Receta complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="Receta">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Cama" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DescripcionServicio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Estatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Fecha" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="FechaReferencia" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="Folio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FolioPago" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Materiales" type="{http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites}ArrayOfRecetaMaterial" minOccurs="0"/>
 *         &lt;element name="Medico" type="{http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites}Medico" minOccurs="0"/>
 *         &lt;element name="Paciente" type="{http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites}Paciente" minOccurs="0"/>
 *         &lt;element name="Piso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Servicio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Tipo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Receta", propOrder = {
    "cama",
    "descripcionServicio",
    "estatus",
    "fecha",
    "fechaReferencia",
    "folio",
    "folioPago",
    "materiales",
    "medico",
    "paciente",
    "piso",
    "servicio",
    "tipo"
})
public class Receta {

    @XmlElementRef(name = "Cama", namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", type = JAXBElement.class, required = false)
    protected JAXBElement<String> cama;
    @XmlElementRef(name = "DescripcionServicio", namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", type = JAXBElement.class, required = false)
    protected JAXBElement<String> descripcionServicio;
    @XmlElementRef(name = "Estatus", namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", type = JAXBElement.class, required = false)
    protected JAXBElement<String> estatus;
    @XmlElement(name = "Fecha")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fecha;
    @XmlElementRef(name = "FechaReferencia", namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", type = JAXBElement.class, required = false)
    protected JAXBElement<XMLGregorianCalendar> fechaReferencia;
    @XmlElementRef(name = "Folio", namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", type = JAXBElement.class, required = false)
    protected JAXBElement<String> folio;
    @XmlElementRef(name = "FolioPago", namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", type = JAXBElement.class, required = false)
    protected JAXBElement<String> folioPago;
    @XmlElementRef(name = "Materiales", namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfRecetaMaterial> materiales;
    @XmlElementRef(name = "Medico", namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", type = JAXBElement.class, required = false)
    protected JAXBElement<Medico> medico;
    @XmlElementRef(name = "Paciente", namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", type = JAXBElement.class, required = false)
    protected JAXBElement<Paciente> paciente;
    @XmlElementRef(name = "Piso", namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", type = JAXBElement.class, required = false)
    protected JAXBElement<String> piso;
    @XmlElementRef(name = "Servicio", namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", type = JAXBElement.class, required = false)
    protected JAXBElement<String> servicio;
    @XmlElementRef(name = "Tipo", namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", type = JAXBElement.class, required = false)
    protected JAXBElement<String> tipo;

    /**
     * Obtiene el valor de la propiedad cama.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCama() {
        return cama;
    }

    /**
     * Define el valor de la propiedad cama.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCama(JAXBElement<String> value) {
        this.cama = value;
    }

    /**
     * Obtiene el valor de la propiedad descripcionServicio.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDescripcionServicio() {
        return descripcionServicio;
    }

    /**
     * Define el valor de la propiedad descripcionServicio.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDescripcionServicio(JAXBElement<String> value) {
        this.descripcionServicio = value;
    }

    /**
     * Obtiene el valor de la propiedad estatus.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getEstatus() {
        return estatus;
    }

    /**
     * Define el valor de la propiedad estatus.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setEstatus(JAXBElement<String> value) {
        this.estatus = value;
    }

    /**
     * Obtiene el valor de la propiedad fecha.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFecha() {
        return fecha;
    }

    /**
     * Define el valor de la propiedad fecha.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFecha(XMLGregorianCalendar value) {
        this.fecha = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaReferencia.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getFechaReferencia() {
        return fechaReferencia;
    }

    /**
     * Define el valor de la propiedad fechaReferencia.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setFechaReferencia(JAXBElement<XMLGregorianCalendar> value) {
        this.fechaReferencia = value;
    }

    /**
     * Obtiene el valor de la propiedad folio.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getFolio() {
        return folio;
    }

    /**
     * Define el valor de la propiedad folio.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setFolio(JAXBElement<String> value) {
        this.folio = value;
    }

    /**
     * Obtiene el valor de la propiedad folioPago.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getFolioPago() {
        return folioPago;
    }

    /**
     * Define el valor de la propiedad folioPago.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setFolioPago(JAXBElement<String> value) {
        this.folioPago = value;
    }

    /**
     * Obtiene el valor de la propiedad materiales.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfRecetaMaterial }{@code >}
     *     
     */
    public JAXBElement<ArrayOfRecetaMaterial> getMateriales() {
        return materiales;
    }

    /**
     * Define el valor de la propiedad materiales.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfRecetaMaterial }{@code >}
     *     
     */
    public void setMateriales(JAXBElement<ArrayOfRecetaMaterial> value) {
        this.materiales = value;
    }

    /**
     * Obtiene el valor de la propiedad medico.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Medico }{@code >}
     *     
     */
    public JAXBElement<Medico> getMedico() {
        return medico;
    }

    /**
     * Define el valor de la propiedad medico.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Medico }{@code >}
     *     
     */
    public void setMedico(JAXBElement<Medico> value) {
        this.medico = value;
    }

    /**
     * Obtiene el valor de la propiedad paciente.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Paciente }{@code >}
     *     
     */
    public JAXBElement<Paciente> getPaciente() {
        return paciente;
    }

    /**
     * Define el valor de la propiedad paciente.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Paciente }{@code >}
     *     
     */
    public void setPaciente(JAXBElement<Paciente> value) {
        this.paciente = value;
    }

    /**
     * Obtiene el valor de la propiedad piso.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPiso() {
        return piso;
    }

    /**
     * Define el valor de la propiedad piso.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPiso(JAXBElement<String> value) {
        this.piso = value;
    }

    /**
     * Obtiene el valor de la propiedad servicio.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getServicio() {
        return servicio;
    }

    /**
     * Define el valor de la propiedad servicio.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setServicio(JAXBElement<String> value) {
        this.servicio = value;
    }

    /**
     * Obtiene el valor de la propiedad tipo.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTipo() {
        return tipo;
    }

    /**
     * Define el valor de la propiedad tipo.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTipo(JAXBElement<String> value) {
        this.tipo = value;
    }

}
