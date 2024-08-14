
package mx.com.dimesa.ws.client.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;
import mx.com.dimesa.ws.client.arrays.ArrayOfstring;


/**
 * <p>Clase Java para T_MAT complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="T_MAT">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CANTIDAD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CodigoBarras" type="{http://schemas.microsoft.com/2003/10/Serialization/Arrays}ArrayOfstring" minOccurs="0"/>
 *         &lt;element name="FECHA_CADUCIDAD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LOTE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MATERIAL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "T_MAT", propOrder = {
    "cantidad",
    "codigoBarras",
    "fechacaducidad",
    "lote",
    "material"
})
public class TMAT {

    @XmlElementRef(name = "CANTIDAD", namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", type = JAXBElement.class, required = false)
    protected JAXBElement<String> cantidad;
    @XmlElementRef(name = "CodigoBarras", namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfstring> codigoBarras;
    @XmlElementRef(name = "FECHA_CADUCIDAD", namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", type = JAXBElement.class, required = false)
    protected JAXBElement<String> fechacaducidad;
    @XmlElementRef(name = "LOTE", namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", type = JAXBElement.class, required = false)
    protected JAXBElement<String> lote;
    @XmlElementRef(name = "MATERIAL", namespace = "http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites", type = JAXBElement.class, required = false)
    protected JAXBElement<String> material;

    /**
     * Obtiene el valor de la propiedad cantidad.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCANTIDAD() {
        return cantidad;
    }

    /**
     * Define el valor de la propiedad cantidad.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCANTIDAD(JAXBElement<String> value) {
        this.cantidad = value;
    }

    /**
     * Obtiene el valor de la propiedad codigoBarras.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfstring }{@code >}
     *     
     */
    public JAXBElement<ArrayOfstring> getCodigoBarras() {
        return codigoBarras;
    }

    /**
     * Define el valor de la propiedad codigoBarras.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfstring }{@code >}
     *     
     */
    public void setCodigoBarras(JAXBElement<ArrayOfstring> value) {
        this.codigoBarras = value;
    }

    /**
     * Obtiene el valor de la propiedad fechacaducidad.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getFECHACADUCIDAD() {
        return fechacaducidad;
    }

    /**
     * Define el valor de la propiedad fechacaducidad.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setFECHACADUCIDAD(JAXBElement<String> value) {
        this.fechacaducidad = value;
    }

    /**
     * Obtiene el valor de la propiedad lote.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getLOTE() {
        return lote;
    }

    /**
     * Define el valor de la propiedad lote.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setLOTE(JAXBElement<String> value) {
        this.lote = value;
    }

    /**
     * Obtiene el valor de la propiedad material.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getMATERIAL() {
        return material;
    }

    /**
     * Define el valor de la propiedad material.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setMATERIAL(JAXBElement<String> value) {
        this.material = value;
    }

}
