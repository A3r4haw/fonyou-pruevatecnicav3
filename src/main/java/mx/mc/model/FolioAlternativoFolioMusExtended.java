/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.io.Serializable;
import mx.gob.issste.ws.model.ResponseDetalleSIAM;

/**
 *
 * @author Admin
 */
public class FolioAlternativoFolioMusExtended extends FolioAlternativoFolioMus implements Serializable{

    private static final long serialVersionUID = 1L;
    
    private String servicio;
    private String idEstructura;
    private ResponseDetalleSIAM response;
    private boolean recibir;

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public ResponseDetalleSIAM getResponse() {
        return response;
    }

    public void setResponse(ResponseDetalleSIAM response) {
        this.response = response;
    }

    public boolean isRecibir() {
        return recibir;
    }

    public void setRecibir(boolean recibir) {
        this.recibir = recibir;
    }
    
    
    
}
