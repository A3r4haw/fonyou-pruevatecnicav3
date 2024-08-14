package mx.mc.enums;

/**
 *
 * @author mcalderon
 */
public enum EstatusCita_Enum {

    PROGRAMADA(1),
    CANCELADA(2),
    CERRADA(3),    
    ;

    private final Integer value;

    private EstatusCita_Enum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

}
