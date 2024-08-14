
package mx.com.dimesa.ws.client.model;

import java.math.BigDecimal;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para RecetaMaterial complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="RecetaMaterial">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CantidadEntregada" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="CantidadSolicitada" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="Clave" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ClaveInterna" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ClaveSAP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DescripcionSAP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Dosis" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Posicion" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Tratamiento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RecetaMaterial", propOrder = {
    "cantidadEntregada",
    "cantidadSolicitada",
    "clave",
    "claveInterna",
    "claveSAP",
    "descripcionSAP",
    "dosis",
    "posicion",
    "tratamiento"
})
public class RecetaMaterial {

    @XmlElement(name = "CantidadEntregada")
    protected BigDecimal cantidadEntregada;
    @XmlElement(name = "CantidadSolicitada")
    protected BigDecimal cantidadSolicitada;
    @XmlElementRef(name = "Clave", namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", type = JAXBElement.class, required = false)
    protected JAXBElement<String> clave;
    @XmlElementRef(name = "ClaveInterna", namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", type = JAXBElement.class, required = false)
    protected JAXBElement<String> claveInterna;
    @XmlElementRef(name = "ClaveSAP", namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", type = JAXBElement.class, required = false)
    protected JAXBElement<String> claveSAP;
    @XmlElementRef(name = "DescripcionSAP", namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", type = JAXBElement.class, required = false)
    protected JAXBElement<String> descripcionSAP;
    @XmlElementRef(name = "Dosis", namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dosis;
    @XmlElement(name = "Posicion")
    protected Integer posicion;
    @XmlElementRef(name = "Tratamiento", namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", type = JAXBElement.class, required = false)
    protected JAXBElement<String> tratamiento;

    /**
     * Obtiene el valor de la propiedad cantidadEntregada.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCantidadEntregada() {
        return cantidadEntregada;
    }

    /**
     * Define el valor de la propiedad cantidadEntregada.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCantidadEntregada(BigDecimal value) {
        this.cantidadEntregada = value;
    }

    /**
     * Obtiene el valor de la propiedad cantidadSolicitada.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    /**
     * Define el valor de la propiedad cantidadSolicitada.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCantidadSolicitada(BigDecimal value) {
        this.cantidadSolicitada = value;
    }

    /**
     * Obtiene el valor de la propiedad clave.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getClave() {
        return clave;
    }

    /**
     * Define el valor de la propiedad clave.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setClave(JAXBElement<String> value) {
        this.clave = value;
    }

    /**
     * Obtiene el valor de la propiedad claveInterna.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getClaveInterna() {
        return claveInterna;
    }

    /**
     * Define el valor de la propiedad claveInterna.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setClaveInterna(JAXBElement<String> value) {
        this.claveInterna = value;
    }

    /**
     * Obtiene el valor de la propiedad claveSAP.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getClaveSAP() {
        return claveSAP;
    }

    /**
     * Define el valor de la propiedad claveSAP.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setClaveSAP(JAXBElement<String> value) {
        this.claveSAP = value;
    }

    /**
     * Obtiene el valor de la propiedad descripcionSAP.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDescripcionSAP() {
        return descripcionSAP;
    }

    /**
     * Define el valor de la propiedad descripcionSAP.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDescripcionSAP(JAXBElement<String> value) {
        this.descripcionSAP = value;
    }

    /**
     * Obtiene el valor de la propiedad dosis.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDosis() {
        return dosis;
    }

    /**
     * Define el valor de la propiedad dosis.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDosis(JAXBElement<String> value) {
        this.dosis = value;
    }

    /**
     * Obtiene el valor de la propiedad posicion.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPosicion() {
        return posicion;
    }

    /**
     * Define el valor de la propiedad posicion.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPosicion(Integer value) {
        this.posicion = value;
    }

    /**
     * Obtiene el valor de la propiedad tratamiento.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTratamiento() {
        return tratamiento;
    }

    /**
     * Define el valor de la propiedad tratamiento.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTratamiento(JAXBElement<String> value) {
        this.tratamiento = value;
    }

}
