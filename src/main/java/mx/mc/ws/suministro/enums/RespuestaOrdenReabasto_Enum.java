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
public enum RespuestaOrdenReabasto_Enum {
    ERROR_CONSULTA("00","Error al buscar el folio de solicitud."),
    REABASTO_ENCONTRADO("01","Detalle de Medicamentos Encontrado."),
    FOLIO_NULO("02","El Folio de Solicitud No puede ser nulo o vacío."),
    FOLIO_NO_EXISTE("03","El Folio de Solicitud no existe."),
    SIN_MEDICAMENTOS("04","La Solicitud de Reabasto debe tener al menos un Medicamento surtido."),
    REABASTO_REGISTRADO("05","Registro de Surtimiento exitoso."),
    ERROR_REGISTRO("06","Error al registrar el Surtimiento de la Ordenes de Reabasto."),
    ERROR_MEDICAMENTO("07","Un medicamento dentro de la lista no existe."),
    FOLIO_VACIO("08","El folio no puede ser Vacio."),
    ERROR_CONVERSION("09","La cantidad surtida no corresponde a la cantidad exacta a convertir"),
    ENVIADA("Enviada","EV"),
    PROCESADA("Procesada por Farmacia","PR"),
    REABASTO_CANCELADO("CR", "Cancelada por el usuario"),
    REABASTO_CANCELADO_VIGENCIA("CX", "Cancelada por Vigencia")
    ;
    
    private final String codigoRespuesta;
    private final String descripcion;
    
    private RespuestaOrdenReabasto_Enum(String codigoRespuesta, String descripcion) {
        this.codigoRespuesta = codigoRespuesta;
        this.descripcion = descripcion;
    }

    public String getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public String getDescripcion() {
        return descripcion;
    }
    
    
}
