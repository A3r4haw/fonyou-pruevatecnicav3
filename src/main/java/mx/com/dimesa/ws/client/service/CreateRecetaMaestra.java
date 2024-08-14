
package mx.com.dimesa.ws.client.service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import mx.com.dimesa.ws.client.model.Receta;
import mx.com.dimesa.ws.client.model.Usuario;


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
 *         &lt;element name="usuario" type="{http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites}Usuario" minOccurs="0"/>
 *         &lt;element name="receta" type="{http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites}Receta" minOccurs="0"/>
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
    "usuario",
    "receta"
})
@XmlRootElement(name = "CreateRecetaMaestra")
public class CreateRecetaMaestra {

    @XmlElementRef(name = "usuario", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<Usuario> usuario;
    @XmlElementRef(name = "receta", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<Receta> receta;

    /**
     * Obtiene el valor de la propiedad usuario.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Usuario }{@code >}
     *     
     */
    public JAXBElement<Usuario> getUsuario() {
        return usuario;
    }

    /**
     * Define el valor de la propiedad usuario.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Usuario }{@code >}
     *     
     */
    public void setUsuario(JAXBElement<Usuario> value) {
        this.usuario = value;
    }

    /**
     * Obtiene el valor de la propiedad receta.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Receta }{@code >}
     *     
     */
    public JAXBElement<Receta> getReceta() {
        return receta;
    }

    /**
     * Define el valor de la propiedad receta.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Receta }{@code >}
     *     
     */
    public void setReceta(JAXBElement<Receta> value) {
        this.receta = value;
    }

}
