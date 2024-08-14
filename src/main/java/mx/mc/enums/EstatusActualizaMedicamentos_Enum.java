/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.enums;

/**
 *
 * @author gcruz
 */
public enum EstatusActualizaMedicamentos_Enum {
    
    PROGRAMADA(1),
    PROCESANDO(2),
    FINALIZADA(3),
    CANCELADO(4),
    ;

    private final Integer value;

    private EstatusActualizaMedicamentos_Enum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
