package mx.mc.enums;

/**
 *
 * @author hramirez
 */
public enum EstatusMinistracion_Enum {

    PENDIENTE(1),
    MINISTRADO(2),
    NO_MINISTRADO(3),
    CANCELADO(4),
    ;

    private final Integer value;

    private EstatusMinistracion_Enum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

}
