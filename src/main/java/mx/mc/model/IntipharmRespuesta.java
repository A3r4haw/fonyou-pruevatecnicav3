/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.io.Serializable;

/**
 *
 * @author mcalderon
 */
public class IntipharmRespuesta implements Serializable {

    private static final long serialVersionUID = 1L;

    private String folio;
    private String mensaje;
    private boolean error;

    public String getFolioSurtimiento() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

}
