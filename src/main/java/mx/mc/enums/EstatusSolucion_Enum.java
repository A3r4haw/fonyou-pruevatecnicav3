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
public enum EstatusSolucion_Enum {
    INVALIDA(0),
    //    PREPARADA(1),
    //    ERROR_AL_PREPARAR(2),
    //    MEZCLA_CORRECTA (3),
    //    MEZCLA_INCORRECTA (4),
    //    ACONDICIONADO (5),
    //    ENTREGADO (6),
    //    NO_ENTREGADO (7),
    //    MINISTRADO (8),
    //    NO_MINISTRADO (9),
    //    DEVOLUCION (10), 
    //    VALIDADA (11),
    //    REGISTRADA (12),
    //    RETIRADO(13),
    //    CANCELADO(14),
    //    ERROR_EN_PRESCRIPCION(15),
    //    ERROR_EN_ORDEN_DE_PREPARACION(16)
    PRESCRIPCION_REGISTRADA(1),
    PRESCRIPCION_SOLICITADA(2),
    ORDEN_CREADA(3),
    PRESCRIPCION_RECHAZADA(4),
    PRESCRIPCION_VALIDADA(5),
    OP_RECHAZADA(6),
    OP_VALIDADA(7),
    MEZCLA_RECHAZADA(8),
    MEZCLA_ERROR_AL_PREPARAR(9),
    MEZCLA_PREPARADA(10),
    INSPECCION_NO_CONFORME(11),
    INSPECCION_CONFORME(12),
    ACONDICIONAMIENTO_NO_CONFORME(13),
    ACONDICIONAMIENTO_CONFORME(14),
    MEZCLA_EN_DISTRIBUCIÓN(15),
    MEZCLA_ENTREGADA(16),
    MEZCLA_NO_ENTREGADA(17),
    MEZCLA_NO_ACEPTADA(18),
    MEZCLA_CADUCADA(19),
    CANCELADA(20),
    RETIRADA(21),
    DEVUELTA(22),
    RECIBIDA(23),
    MINISTRADA(24),
    NO_MINISTRADA(25),
    POR_AUTORIZAR(26);

    private EstatusSolucion_Enum(Integer value) {
        this.value = value;
    }

    private final Integer value;

    public Integer getValue() {
        return value;
    }

    public static EstatusSolucion_Enum getStatusFromId(int status) {
        switch (status) {
            default:                return INVALIDA;
            case 1:                return PRESCRIPCION_REGISTRADA;
            case 2:                return PRESCRIPCION_SOLICITADA;
            case 3:                return ORDEN_CREADA;
            case 4:                return PRESCRIPCION_RECHAZADA;
            case 5:                return PRESCRIPCION_VALIDADA;
            case 6:                return OP_RECHAZADA;
            case 7:                return OP_VALIDADA;
            case 8:                return MEZCLA_RECHAZADA;
            case 9:                return MEZCLA_ERROR_AL_PREPARAR;
            case 10:                return MEZCLA_PREPARADA;
            case 11:                return INSPECCION_NO_CONFORME;
            case 12:                return INSPECCION_CONFORME;
            case 13:                return ACONDICIONAMIENTO_NO_CONFORME;
            case 14:                return ACONDICIONAMIENTO_CONFORME;
            case 15:                return MEZCLA_EN_DISTRIBUCIÓN;
            case 16:                return MEZCLA_ENTREGADA;
            case 17:                return MEZCLA_NO_ENTREGADA;
            case 18:                return MEZCLA_NO_ACEPTADA;
            case 19:                return MEZCLA_CADUCADA;
            case 20:                return CANCELADA;
            case 21:                return RETIRADA;
            case 22:                return DEVUELTA;
            case 23:                return RECIBIDA;
            case 24:                return MINISTRADA;
            case 25:                return NO_MINISTRADA;
            case 26:                return POR_AUTORIZAR;
        }
    }

}
