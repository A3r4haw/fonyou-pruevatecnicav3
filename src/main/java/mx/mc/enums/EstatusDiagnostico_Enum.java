package mx.mc.enums;

/**
 *
 * @author hramirez
 */
public enum EstatusDiagnostico_Enum {

    DIAGNOSTICADO(1),
    TRATADO(2),;

    private final Integer value;

    private EstatusDiagnostico_Enum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

}
