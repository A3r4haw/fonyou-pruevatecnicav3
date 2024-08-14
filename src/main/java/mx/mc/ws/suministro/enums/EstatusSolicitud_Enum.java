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
public enum EstatusSolicitud_Enum {
    PROCESANDO("PROCESANDO","N"),
    ENVIADA("Enviada","EV"),
    PROCESADA("Procesada por Farmacia","PR"),
    REABASTO_CANCELADO("Cancelada por el usuario", "CR"),
    REABASTO_CANCELADO_VIGENCIA("Cancelada por Vigencia", "CX"),
    ;
    
    private final String descripcion;
    private final String clave;

    private EstatusSolicitud_Enum(String descripcion, String clave) {
        this.descripcion = descripcion;
        this.clave = clave;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getClave() {
        return clave;
    }
    
    
}


