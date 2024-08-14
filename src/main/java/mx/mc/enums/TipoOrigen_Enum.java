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
public enum TipoOrigen_Enum {
    ADMINISTRACION(1),
    COMPRA_DIRECTA(2);

    private final Integer value;

    private TipoOrigen_Enum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

}
