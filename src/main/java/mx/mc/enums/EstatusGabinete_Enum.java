package mx.mc.enums;

/**
 *
 * @author hramirez
 */
public enum EstatusGabinete_Enum {

    PENDIENTE(1),
    ACTUALIZAR(2),
    INACTIVAR(3),
    ACTIVAR(4),
    PROCESANDO(5),
    OK(6),
    ERROR(7),
    MERGE(8),
    CANCELADA(9),
    ;

    private final Integer value;

    private EstatusGabinete_Enum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

}
