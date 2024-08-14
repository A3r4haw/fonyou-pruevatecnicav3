
package mx.com.dimesa.ws.client.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="InventarioResult" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
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
    "inventarioResult"
})
@XmlRootElement(name = "InventarioResponse")
public class InventarioResponse {

    @XmlElement(name = "InventarioResult")
    protected Long inventarioResult;

    /**
     * Obtiene el valor de la propiedad inventarioResult.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getInventarioResult() {
        return inventarioResult;
    }

    /**
     * Define el valor de la propiedad inventarioResult.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setInventarioResult(Long value) {
        this.inventarioResult = value;
    }

}
