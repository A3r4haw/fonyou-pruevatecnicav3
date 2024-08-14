/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.issste.ws.model;

import java.io.Serializable;
import javax.json.bind.annotation.JsonbProperty;

/**
 *
 * @author Admin
 */
public class ColectivoSurtidoDetalle implements Serializable{

    private static final long serialVersionUID = 1L;
    @JsonbProperty("Folio")
    private String folio;
    @JsonbProperty("FolioExterno")
    private String folioExterno;

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }    

    public String getFolioExterno() {
        return folioExterno;
    }

    public void setFolioExterno(String folioExterno) {
        this.folioExterno = folioExterno;
    }

    
    
    
}
