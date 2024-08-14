package mx.mc.enums;

/**
 *
 * @author hramirez
 */
public enum EstatusRecepcion_Enum {

    RECIBIDO(1),
    NO_RECIBIDO(2),
    ;

    private final Integer value;

    private EstatusRecepcion_Enum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

}
