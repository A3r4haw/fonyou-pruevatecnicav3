package mx.mc.enums;

/**
 *
 * @author hramirez
 */
public enum EstatusSurtimiento_Enum {
    
    PROGRAMADO(1),
    SURTIDO(2),
    EN_TRANSITO(3),
    RECIBIDO(4),
    CANCELADO(5),
    SUSPENDIDO(6),
    SUTIDO_PARCIAL(7),
    COMPLETADO(8),
    NO_SURTIDO(9),
    RECHAZADA(11),
//    POR_VALIDAR(10),
//    VALIDADO(12),
//    PREPARADA(13),
//    NO_PREPARADA(14),
    ;

    private final Integer value;

    private EstatusSurtimiento_Enum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static EstatusSurtimiento_Enum getStatusFromId(int status) {
        switch (status) {
            default: return null; 
            case 1: return PROGRAMADO;
            case 2: return SURTIDO;
            case 3: return EN_TRANSITO;
            case 4: return RECIBIDO;
            case 5: return CANCELADO;
            case 6: return SUSPENDIDO;
            case 7: return SUTIDO_PARCIAL;
            case 8: return COMPLETADO;
            case 9: return NO_SURTIDO;
//            case 10: return POR_VALIDAR;
            case 11: return RECHAZADA;
//            case 12: return VALIDADO;
//            case 13: return PREPARADA;
//            case 14: return NO_PREPARADA;
        }
    }
}
