
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
 *         &lt;element name="CreateRecetaIssemymResult" type="{http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites}RecetaSAFWSResultado" minOccurs="0"/>
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
    "createRecetaIssemymResult"
})
@XmlRootElement(name = "CreateRecetaIssemymResponse")
public class CreateRecetaIssemymResponse {

    @XmlElementRef(name = "CreateRecetaIssemymResult", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<RecetaSAFWSResultado> createRecetaIssemymResult;

    /**
     * Obtiene el valor de la propiedad createRecetaIssemymResult.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link RecetaSAFWSResultado }{@code >}
     *     
     */
    public JAXBElement<RecetaSAFWSResultado> getCreateRecetaIssemymResult() {
        return createRecetaIssemymResult;
    }

    /**
     * Define el valor de la propiedad createRecetaIssemymResult.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link RecetaSAFWSResultado }{@code >}
     *     
     */
    public void setCreateRecetaIssemymResult(JAXBElement<RecetaSAFWSResultado> value) {
        this.createRecetaIssemymResult = value;
    }

}
