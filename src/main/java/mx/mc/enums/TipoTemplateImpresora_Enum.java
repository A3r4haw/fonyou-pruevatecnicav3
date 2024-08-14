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
public enum TipoTemplateImpresora_Enum {
        
    E("E",""),
    P("P",""),
    O("O","")
    ;

    private final String value;
    private final String sufijo;

    private TipoTemplateImpresora_Enum(String value, String sufijo) {
        this.value = value;
        this.sufijo = sufijo;
    }

    public String getValue() {
        return value;
    }

    public String getSufijo() {
        return sufijo;
    }
    
}
