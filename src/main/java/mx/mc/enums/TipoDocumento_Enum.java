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
public enum TipoDocumento_Enum {

    ORDEN_REABASTO(1),
    DEVOLUCION_DE_SURTIMIENTO(2),
    TRANSFERENCIA(3),
    PRESCRIPCION(4),
    SURTIMIENTO_DE_PRESCRIPCION(5),
    REABASTO(6),
    DEVOLUVIONES_DE_REABASTO(7),
    RECETA(8),
    AJUSTES(9),
    ORDEN_COMPRA(10),
    DEVOLUCION_MINISTRACION(11),
    REENVIO_MEDICAMENTO(12),
    IMSS_SAIF_SOLICITUD(13),
    DISPENSACION_MATERIAL(14),
    PACIENTE_MANUAL(15),
    USUARIO_MANUAL(16),
    REPORTECONTROLDIARIO(17),
    INGRESODEVOLUCIONREABASTO(18),    
    DEVOLUCION_MANUAL(19),
    ORDEN_MANUAL_SOLUCIONES(20),
    REACCIONADVERSA(21),
    REACCIONHIPERSENSIBILIDAD(22),
    ORDENPREPARACIONMEZCLA(23),
    FICHAPREVENCIONCONTAMINACION(24),
    DEVOLUCION_MEZCLAS(25),
    RETIRO_MEZCLAS(26),
    NOTA_DISPEN_COLECT(27)
    ;
    
    private final Integer value;

    private TipoDocumento_Enum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    
}
