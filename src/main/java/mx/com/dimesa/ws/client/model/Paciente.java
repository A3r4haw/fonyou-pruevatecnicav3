
package mx.com.dimesa.ws.client.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para Paciente complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="Paciente">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ApellidoMaterno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ApellidoPaterno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DescripcionPadecimiento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DescripcionPrograma" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Direccion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FechaNacimiento" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="Nombre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NumeroAfiliacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Padecimiento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Programa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Sexo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Telefono" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Paciente", propOrder = {
    "apellidoMaterno",
    "apellidoPaterno",
    "descripcionPadecimiento",
    "descripcionPrograma",
    "direccion",
    "fechaNacimiento",
    "nombre",
    "numeroAfiliacion",
    "padecimiento",
    "programa",
    "sexo",
    "telefono"
})
public class Paciente {

    @XmlElementRef(name = "ApellidoMaterno", namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", type = JAXBElement.class, required = false)
    protected JAXBElement<String> apellidoMaterno;
    @XmlElementRef(name = "ApellidoPaterno", namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", type = JAXBElement.class, required = false)
    protected JAXBElement<String> apellidoPaterno;
    @XmlElementRef(name = "DescripcionPadecimiento", namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", type = JAXBElement.class, required = false)
    protected JAXBElement<String> descripcionPadecimiento;
    @XmlElementRef(name = "DescripcionPrograma", namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", type = JAXBElement.class, required = false)
    protected JAXBElement<String> descripcionPrograma;
    @XmlElementRef(name = "Direccion", namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", type = JAXBElement.class, required = false)
    protected JAXBElement<String> direccion;
    @XmlElementRef(name = "FechaNacimiento", namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", type = JAXBElement.class, required = false)
    protected JAXBElement<XMLGregorianCalendar> fechaNacimiento;
    @XmlElementRef(name = "Nombre", namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nombre;
    @XmlElementRef(name = "NumeroAfiliacion", namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", type = JAXBElement.class, required = false)
    protected JAXBElement<String> numeroAfiliacion;
    @XmlElementRef(name = "Padecimiento", namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", type = JAXBElement.class, required = false)
    protected JAXBElement<String> padecimiento;
    @XmlElementRef(name = "Programa", namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", type = JAXBElement.class, required = false)
    protected JAXBElement<String> programa;
    @XmlElementRef(name = "Sexo", namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", type = JAXBElement.class, required = false)
    protected JAXBElement<String> sexo;
    @XmlElementRef(name = "Telefono", namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", type = JAXBElement.class, required = false)
    protected JAXBElement<String> telefono;

    /**
     * Obtiene el valor de la propiedad apellidoMaterno.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getApellidoMaterno() {
        return apellidoMaterno;
    }

    /**
     * Define el valor de la propiedad apellidoMaterno.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setApellidoMaterno(JAXBElement<String> value) {
        this.apellidoMaterno = value;
    }

    /**
     * Obtiene el valor de la propiedad apellidoPaterno.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getApellidoPaterno() {
        return apellidoPaterno;
    }

    /**
     * Define el valor de la propiedad apellidoPaterno.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setApellidoPaterno(JAXBElement<String> value) {
        this.apellidoPaterno = value;
    }

    /**
     * Obtiene el valor de la propiedad descripcionPadecimiento.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDescripcionPadecimiento() {
        return descripcionPadecimiento;
    }

    /**
     * Define el valor de la propiedad descripcionPadecimiento.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDescripcionPadecimiento(JAXBElement<String> value) {
        this.descripcionPadecimiento = value;
    }

    /**
     * Obtiene el valor de la propiedad descripcionPrograma.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDescripcionPrograma() {
        return descripcionPrograma;
    }

    /**
     * Define el valor de la propiedad descripcionPrograma.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDescripcionPrograma(JAXBElement<String> value) {
        this.descripcionPrograma = value;
    }

    /**
     * Obtiene el valor de la propiedad direccion.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDireccion() {
        return direccion;
    }

    /**
     * Define el valor de la propiedad direccion.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDireccion(JAXBElement<String> value) {
        this.direccion = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaNacimiento.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Define el valor de la propiedad fechaNacimiento.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setFechaNacimiento(JAXBElement<XMLGregorianCalendar> value) {
        this.fechaNacimiento = value;
    }

    /**
     * Obtiene el valor de la propiedad nombre.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNombre() {
        return nombre;
    }

    /**
     * Define el valor de la propiedad nombre.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNombre(JAXBElement<String> value) {
        this.nombre = value;
    }

    /**
     * Obtiene el valor de la propiedad numeroAfiliacion.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNumeroAfiliacion() {
        return numeroAfiliacion;
    }

    /**
     * Define el valor de la propiedad numeroAfiliacion.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNumeroAfiliacion(JAXBElement<String> value) {
        this.numeroAfiliacion = value;
    }

    /**
     * Obtiene el valor de la propiedad padecimiento.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPadecimiento() {
        return padecimiento;
    }

    /**
     * Define el valor de la propiedad padecimiento.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPadecimiento(JAXBElement<String> value) {
        this.padecimiento = value;
    }

    /**
     * Obtiene el valor de la propiedad programa.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPrograma() {
        return programa;
    }

    /**
     * Define el valor de la propiedad programa.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPrograma(JAXBElement<String> value) {
        this.programa = value;
    }

    /**
     * Obtiene el valor de la propiedad sexo.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSexo() {
        return sexo;
    }

    /**
     * Define el valor de la propiedad sexo.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSexo(JAXBElement<String> value) {
        this.sexo = value;
    }

    /**
     * Obtiene el valor de la propiedad telefono.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTelefono() {
        return telefono;
    }

    /**
     * Define el valor de la propiedad telefono.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTelefono(JAXBElement<String> value) {
        this.telefono = value;
    }

}
