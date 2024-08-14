/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.dto;

import java.util.List;
import mx.mc.model.ProtocoloExtended;

/**
 *
 * @author gcruz
 */
public class RespuestaValidacionSolucionDTO {
    
    private String codigo;
    private String descripcion;
    private List<ProtocoloExtended> listaCentralMezclasSolucion;

    public RespuestaValidacionSolucionDTO() {
    }        

    public RespuestaValidacionSolucionDTO(String codigo, String descripcion, List<ProtocoloExtended> listaCentralMezclasSolucion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.listaCentralMezclasSolucion = listaCentralMezclasSolucion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<ProtocoloExtended> getListaCentralMezclasSolucion() {
        return listaCentralMezclasSolucion;
    }

    public void setListaCentralMezclasSolucion(List<ProtocoloExtended> listaCentralMezclasSolucion) {
        this.listaCentralMezclasSolucion = listaCentralMezclasSolucion;
    }
    
    
}
