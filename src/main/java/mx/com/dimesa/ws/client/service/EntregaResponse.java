
package mx.com.dimesa.ws.client.service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import mx.com.dimesa.ws.client.model.ArrayOfTMAT;


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
 *         &lt;element name="EntregaResult" type="{http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites}ArrayOfT_MAT" minOccurs="0"/>
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
    "entregaResult"
})
@XmlRootElement(name = "EntregaResponse")
public class EntregaResponse {

    @XmlElementRef(name = "EntregaResult", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfTMAT> entregaResult;

    /**
     * Obtiene el valor de la propiedad entregaResult.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfTMAT }{@code >}
     *     
     */
    public JAXBElement<ArrayOfTMAT> getEntregaResult() {
        return entregaResult;
    }

    /**
     * Define el valor de la propiedad entregaResult.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfTMAT }{@code >}
     *     
     */
    public void setEntregaResult(JAXBElement<ArrayOfTMAT> value) {
        this.entregaResult = value;
    }

}
