package mx.mc.enums;

/**
 *
 * @author hramirez
 */
public enum EstatusDimesaRegistroConsumo_Enum {

    PENDIENTE(0),
    PROCESANDO(1),
    PROCESADO(2),
    ERROR(3),
    REPROCESAR(4),
    ;

    private final Integer value;

    private EstatusDimesaRegistroConsumo_Enum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

}
