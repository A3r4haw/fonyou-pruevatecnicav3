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
public enum TipoIngrediente_Enum {
     VEHICULO(1),
     PRINCIPIO_ACTIVO(2),
     FARMACO(3),
     DILIUYENTE(4)
    ;

    private final Integer value;

    private TipoIngrediente_Enum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
