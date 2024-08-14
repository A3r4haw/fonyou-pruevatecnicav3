
package mx.com.dimesa.ws.client.service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import mx.com.dimesa.ws.client.model.RecetaSAFWSResultado;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CreateRecetaResult" type="{http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites}RecetaSAFWSResultado" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "createRecetaResult"
})
@XmlRootElement(name = "CreateRecetaResponse")
public class CreateRecetaResponse {

    @XmlElementRef(name = "CreateRecetaResult", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<RecetaSAFWSResultado> createRecetaResult;

    /**
     * Obtiene el valor de la propiedad createRecetaResult.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link RecetaSAFWSResultado }{@code >}
     *     
     */
    public JAXBElement<RecetaSAFWSResultado> getCreateRecetaResult() {
        return createRecetaResult;
    }

    /**
     * Define el valor de la propiedad createRecetaResult.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link RecetaSAFWSResultado }{@code >}
     *     
     */
    public void setCreateRecetaResult(JAXBElement<RecetaSAFWSResultado> value) {
        this.createRecetaResult = value;
    }

}
