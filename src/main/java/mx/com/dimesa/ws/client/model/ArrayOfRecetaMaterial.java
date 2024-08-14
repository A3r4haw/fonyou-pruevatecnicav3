
package mx.com.dimesa.ws.client.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para ArrayOfRecetaMaterial complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRecetaMaterial">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RecetaMaterial" type="{http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites}RecetaMaterial" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRecetaMaterial", propOrder = {
    "recetaMaterial"
})
public class ArrayOfRecetaMaterial {

    @XmlElement(name = "RecetaMaterial", nillable = true)
    protected List<RecetaMaterial> recetaMaterial;

    /**
     * Gets the value of the recetaMaterial property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the recetaMaterial property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRecetaMaterial().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RecetaMaterial }
     * 
     * 
     */
    public List<RecetaMaterial> getRecetaMaterial() {
        if (recetaMaterial == null) {
            recetaMaterial = new ArrayList<>();
        }
        return this.recetaMaterial;
    }

}
