package mx.mc.enums;

/**
 *
 * @author Cervanets
 */
public enum EstatusNotaDispenColect_Enum {
    POR_ENTREGAR(1),
    CANCELADA(2),
    EN_TRANSITO(3),
    NO_ENTREGADA(4),
    NO_RECIBIDA(5),
    ENTREGA_PARCIAL(6),
    ENTREGADA(7),
    ;

    private final int value;

    private EstatusNotaDispenColect_Enum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
