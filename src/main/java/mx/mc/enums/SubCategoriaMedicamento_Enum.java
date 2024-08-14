package mx.mc.enums;

/**
 *
 * @author aortiz
 */
public enum SubCategoriaMedicamento_Enum {

    CONTROLADA_G1(25),
    CONTROLADA_G2(24),
    CONTROLADA_G3(23),
    ;

    private final Integer value;

    private SubCategoriaMedicamento_Enum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

}
