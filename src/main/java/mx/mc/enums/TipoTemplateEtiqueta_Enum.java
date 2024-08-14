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
public enum TipoTemplateEtiqueta_Enum {
        
    E("E","Etiqueta"),
    P("P","Pulsera Brazalete")
    ;

    private final String value;
    private final String sufijo;

    private TipoTemplateEtiqueta_Enum(String value, String sufijo) {
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
