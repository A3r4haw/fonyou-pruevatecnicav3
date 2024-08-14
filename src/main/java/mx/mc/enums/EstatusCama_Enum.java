package mx.mc.enums;

/**
 *
 * @author aortiz
 */
public enum EstatusCama_Enum {

    DISPONIBLE(1),
    MANTENIMIENTO(2),
    DESHABILITADA(3),
    OCUPADA(4),
    ;

    private final Integer value;

    private EstatusCama_Enum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

}
