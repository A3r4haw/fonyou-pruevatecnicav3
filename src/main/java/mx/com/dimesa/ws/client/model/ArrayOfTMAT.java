
package mx.com.dimesa.ws.client.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para ArrayOfT_MAT complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfT_MAT">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="T_MAT" type="{http://schemas.datacontract.org/2004/07/RecetaSAFWSAppCore.Composites}T_MAT" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfT_MAT", propOrder = {
    "tmat"
})
public class ArrayOfTMAT {

    @XmlElement(name = "T_MAT", nillable = true)
    protected List<TMAT> tmat;

    /**
     * Gets the value of the tmat property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tmat property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTMAT().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TMAT }
     * 
     * 
     */
    public List<TMAT> getTMAT() {
        if (tmat == null) {
            tmat = new ArrayList<>();
        }
        return this.tmat;
    }

}
