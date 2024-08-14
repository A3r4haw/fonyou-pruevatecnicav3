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
public enum EstatusReaccionAbversa_Enum {
        REGISTRADO(1),
	NOTIFICADA(2),
	CONFIRMADA(3)
	;
	
	private final Integer value;

    private EstatusReaccionAbversa_Enum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
