
package mx.com.dimesa.ws.client.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para ArrayOfReceta complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfReceta">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Receta" type="{http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites}Receta" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfReceta", propOrder = {
    "receta"
})
public class ArrayOfReceta {

    @XmlElement(name = "Receta", nillable = true)
    protected List<Receta> receta;

    /**
     * Gets the value of the receta property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the receta property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReceta().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Receta }
     * 
     * 
     */
    public List<Receta> getReceta() {
        if (receta == null) {
            receta = new ArrayList<>();
        }
        return this.receta;
    }

}
