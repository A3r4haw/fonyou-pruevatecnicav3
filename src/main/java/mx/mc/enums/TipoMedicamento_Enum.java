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
public enum TipoMedicamento_Enum {
    
    REFRIGERACION("Refrigeración", 1),
    CLAVES5000("Claves 5000", 2),
    CLAVES4000("Claves 4000", 3),
    CONTROLADO("Controlado", 4);

    private final String nombre;
    private final Integer sufijo;
    
    private TipoMedicamento_Enum(String nombre, Integer sufijo) {
        this.nombre = nombre;
        this.sufijo = sufijo;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getSufijo() {
        return sufijo;
    }
}
