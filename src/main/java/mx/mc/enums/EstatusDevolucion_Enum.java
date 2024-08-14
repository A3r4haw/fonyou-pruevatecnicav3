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
public enum EstatusDevolucion_Enum {
    REGISTRADA(1),
    GUARDADA(2),    
    ENTRANSITO(3),    
    RECIBIDA(4),
    INGRESADA(5),
    CANCELADA(6),    
    ;

   private final Integer value;

    private EstatusDevolucion_Enum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
   
   
}
