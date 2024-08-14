package mx.mc.enums;

/**
 *
 * @author hramirez
 */
public enum TipoJustificacion_Enum {

    COMPLETA(0),
    ERROR(1),
    INSUFICIENTE(2),
    ;

    private final Integer value;

    private TipoJustificacion_Enum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

}
