/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.issste.ws.model;

import java.io.Serializable;
import java.util.List;
import javax.json.bind.annotation.JsonbProperty;

/**
 *
 * @author Admin
 */
public class ResponseColectivoSIAM  implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @JsonbProperty("nuevo_folio")
    private String nuevoFolio;
    
    @JsonbProperty("generado")
    private Integer generado;
    
    @JsonbProperty("mensaje")
    private String mensaje;  
    
    @JsonbProperty("detallesInsumos")
    private List<InsumoSIAM> insumos;

    public ResponseColectivoSIAM() {
    }    
        
    public ResponseColectivoSIAM(String nuevoFolio, Integer generado) {
        this.nuevoFolio = nuevoFolio;
        this.generado = generado;
    }   
    
    public String getNuevoFolio() {
        return nuevoFolio;
    }

    public void setNuevoFolio(String nuevoFolio) {
        this.nuevoFolio = nuevoFolio;
    }

    public Integer getGenerado() {
        return generado;
    }

    public void setGenerado(Integer generado) {
        this.generado = generado;
    }   

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<InsumoSIAM> getInsumos() {
        return insumos;
    }

    public void setInsumos(List<InsumoSIAM> insumos) {
        this.insumos = insumos;
    }
    
    
}
