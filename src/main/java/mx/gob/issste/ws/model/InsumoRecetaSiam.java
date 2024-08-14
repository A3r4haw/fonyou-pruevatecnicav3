/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.issste.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.json.bind.annotation.JsonbProperty;
/**
 *
 * @author mcalderon
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InsumoRecetaSiam implements Serializable{

    private static final long serialVersionUID = 1L;
    
    @JsonbProperty("clave_insumo")  //: "string",
    private String claveInsumo;
    
    @JsonbProperty("esAntibiotico")  //: 0,
    private boolean esAntibiotico;
    
    @JsonbProperty("precio")  //: 0,
    private double precio;
    
    @JsonbProperty("cantidad")  //: 0,
    private Integer cantidad;
    
    @JsonbProperty("negado")  //: 0,	
    private Integer negado;
    
    @JsonbProperty("tipo_insumo")  //: 0,
    private String tipoInsumo;        

    public String getClaveInsumo() {
        return claveInsumo;
    }

    public void setClaveInsumo(String claveInsumo) {
        this.claveInsumo = claveInsumo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getNegado() {
        return negado;
    }

    public void setNegado(Integer negado) {
        this.negado = negado;
    }

    public String getTipoInsumo() {
        return tipoInsumo;
    }

    public void setTipoInsumo(String tipoInsumo) {
        this.tipoInsumo = tipoInsumo;
    }

    public boolean isEsAntibiotico() {
        return esAntibiotico;
    }

    public void setEsAntibiotico(boolean esAntibiotico) {
        this.esAntibiotico = esAntibiotico;
    }

    @Override
    public String toString() {
        return "InsumoRecetaSiam{" + "claveInsumo=" + claveInsumo + ", esAntibiotico=" + esAntibiotico + ", precio=" + precio + ", cantidad=" + cantidad + ", negado=" + negado + ", tipoInsumo=" + tipoInsumo + '}';
    }
  
}
