
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
 *         &lt;element name="CreateColectivoIssemymResult" type="{http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites}RecetaSAFWSResultado" minOccurs="0"/>
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
    "createColectivoIssemymResult"
})
@XmlRootElement(name = "CreateColectivoIssemymResponse")
public class CreateColectivoIssemymResponse {

    @XmlElementRef(name = "CreateColectivoIssemymResult", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<RecetaSAFWSResultado> createColectivoIssemymResult;

    /**
     * Obtiene el valor de la propiedad createColectivoIssemymResult.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link RecetaSAFWSResultado }{@code >}
     *     
     */
    public JAXBElement<RecetaSAFWSResultado> getCreateColectivoIssemymResult() {
        return createColectivoIssemymResult;
    }

    /**
     * Define el valor de la propiedad createColectivoIssemymResult.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link RecetaSAFWSResultado }{@code >}
     *     
     */
    public void setCreateColectivoIssemymResult(JAXBElement<RecetaSAFWSResultado> value) {
        this.createColectivoIssemymResult = value;
    }

}
