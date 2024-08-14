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
public class ResponseDetalleSIAM implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @JsonbProperty("respuestaCodigo")
    private Integer respuestaCodigo;
    
    @JsonbProperty("respuestaMensaje")
    private String respuestaMensaje;

    @JsonbProperty("datosExtra")
    private List<InsumoSIAM> datosExtra;

    public ResponseDetalleSIAM() {
    }

    public ResponseDetalleSIAM(Integer respuestaCodigo, String respuestaMensaje, List<InsumoSIAM> datosExtra) {
        this.respuestaCodigo = respuestaCodigo;
        this.respuestaMensaje = respuestaMensaje;
        this.datosExtra = datosExtra;
    }

    public Integer getRespuestaCodigo() {
        return respuestaCodigo;
    }

    public void setRespuestaCodigo(Integer respuestaCodigo) {
        this.respuestaCodigo = respuestaCodigo;
    }

    public String getRespuestaMensaje() {
        return respuestaMensaje;
    }

    public void setRespuestaMensaje(String respuestaMensaje) {
        this.respuestaMensaje = respuestaMensaje;
    }

    public List<InsumoSIAM> getDatosExtra() {
        return datosExtra;
    }

    public void setDatosExtra(List<InsumoSIAM> datosExtra) {
        this.datosExtra = datosExtra;
    }

    
    
    
}
