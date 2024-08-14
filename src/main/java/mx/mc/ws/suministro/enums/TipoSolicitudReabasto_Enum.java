/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.ws.suministro.enums;

/**
 *
 * @author gcruz
 */
public enum TipoSolicitudReabasto_Enum {
    SERVICIO("SRV"),
    SOLICITUDORDINARIA("ORD"),
    SOLICITUDEXTRAORDINARIA("EXT")
    ;
    
    private final String clave;
    
    private TipoSolicitudReabasto_Enum(String clave) {
        this.clave = clave;
    }

    public String getClave() {
        return clave;
    }
    
}
