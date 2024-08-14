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
public enum RespuestasInfoMedicamento_Enum {
    SI(1),
    NO(2),
    NO_SE_SABE(3);
    
    private final Integer value;

    private RespuestasInfoMedicamento_Enum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
        
}
