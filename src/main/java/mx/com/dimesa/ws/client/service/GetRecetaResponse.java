
package mx.com.dimesa.ws.client.service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import mx.com.dimesa.ws.client.model.ArrayOfReceta;


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
 *         &lt;element name="GetRecetaResult" type="{http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites}ArrayOfReceta" minOccurs="0"/>
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
    "getRecetaResult"
})
@XmlRootElement(name = "GetRecetaResponse")
public class GetRecetaResponse {

    @XmlElementRef(name = "GetRecetaResult", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfReceta> getRecetaResult;

    /**
     * Obtiene el valor de la propiedad getRecetaResult.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfReceta }{@code >}
     *     
     */
    public JAXBElement<ArrayOfReceta> getGetRecetaResult() {
        return getRecetaResult;
    }

    /**
     * Define el valor de la propiedad getRecetaResult.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfReceta }{@code >}
     *     
     */
    public void setGetRecetaResult(JAXBElement<ArrayOfReceta> value) {
        this.getRecetaResult = value;
    }

}
