package mx.mc.enums;

/**
 *
 * @author apalacios
 */
public enum EstatusCensoInsumo_Enum {

    REGISTRADO(1),
    ACEPTADO(2),
    RECHAZADO(3)
    ;

    private final Integer value;

    private EstatusCensoInsumo_Enum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
