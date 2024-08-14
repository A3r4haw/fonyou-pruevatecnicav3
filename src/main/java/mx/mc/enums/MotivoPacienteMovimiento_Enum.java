package mx.mc.enums;

/**
 *
 * @author aortiz
 */
public enum MotivoPacienteMovimiento_Enum {

    ADMISION(1),
    TRANS_SERV(2),
    ADM_POR_TRANS(3),
    ALTA(4),
    DEFUNCION(5),
    ALTA_VOLUNT(6),
    TRANS_HOSPITAL(7),
    ;

    private final Integer value;

    private MotivoPacienteMovimiento_Enum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

}
