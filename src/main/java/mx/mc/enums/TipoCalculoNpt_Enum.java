package mx.mc.enums;

/**
 *
 * @author hramirez
 */
public enum TipoCalculoNpt_Enum {

    COMPATIBILIDAD("10"),                 // compatibilidad
    ESTABILIDAD_ELECT_MONOVAL_P1("1"),    // estabilidad elect monovalentes Parte1
    ESTABILIDAD_ELECT_DIVAL_P1("2"),      // estabilidad elect divalentes Parte1
    ESTABILIDAD_ELECT_MONOVAL_P2("3"),    // estabilidad elect monovalentes parte2
    ESTABILIDAD_ELECT_DIVAL_P2("4");      // estabilidad elect divalentes Parte2

    private final String value;

    private TipoCalculoNpt_Enum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
