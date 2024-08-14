/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.enums;

/**
 *
 * @author bbautista
 */
public enum CodigosRespuestaFarmacovigilancia_Enum {
    EXITOSO(0),
    ERROR_RECIBIR(1),
    DATOS_INCOMPLETOS(2),
    ERROR_EN_INSUMOS(3)
    ;
    
    private final Integer value;

    private CodigosRespuestaFarmacovigilancia_Enum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
