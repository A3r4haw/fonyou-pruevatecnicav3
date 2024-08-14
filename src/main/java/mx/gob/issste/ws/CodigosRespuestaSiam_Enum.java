/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.issste.ws;

public enum CodigosRespuestaSiam_Enum {
    EXITOSO(0),
    FOLIO_INEXISTENTE(1),
    SOLICITUD_PROCESADA(2),
    SOLICITUD_CANCELADA(3),
    SOLICITUD_CANCELADA_VIGENCIA(4),
    SOLICITUD_NO_SURTIDA(5),
    ESTATUS_INVALIDO(6),
    CANTIDAD_INVALIDA(7),
    INSUMO_INEXISTENTE(8),
    ERROR_RECIBIR(9)
    ;

    private final Integer value;

    private CodigosRespuestaSiam_Enum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
