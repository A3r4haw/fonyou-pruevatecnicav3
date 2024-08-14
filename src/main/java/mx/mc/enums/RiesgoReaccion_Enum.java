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
public enum RiesgoReaccion_Enum {
    
    ALTO("Alto"),
    MEDIO("Medio"),
    BAJO("Bajo");
    
    private final String value;
    
    private RiesgoReaccion_Enum(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }

}
