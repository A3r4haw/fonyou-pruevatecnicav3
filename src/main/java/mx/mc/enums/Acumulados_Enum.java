/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.enums;

/**
 *
 * @author mcalderon
 */
public enum Acumulados_Enum {
     
    ACUMULADO_CLAVE(1),
    ACUMULADO_MEDICO(2),
    ACUMULADO_PACIENTE(3),
    ACUMULADO_COLECTIVO(4)
    ;

    private final int value;

    private Acumulados_Enum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
