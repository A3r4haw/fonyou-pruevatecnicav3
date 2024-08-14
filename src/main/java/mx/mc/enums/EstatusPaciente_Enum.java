package mx.mc.enums;

/**
 *
 * @author hramirez
 */
public enum EstatusPaciente_Enum {

    REGISTRADO(1),
    EN_VISITA(2),
    ASIGNADO_A_SERVICIO(3),
    ASIGNADO_A_CAMA(4),
    EGRESO(5),
    ;

    private final Integer value;

    private EstatusPaciente_Enum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

}
