package mx.mc.enums;

/**
 *
 * @author hramirez
 */
public enum TipoPrescripcion_Enum {

    NORMAL("N"),
    URGENTE("U"),
    MANUAL("M"),
    DOSIS_UNICA("D"),
    ;

    private final String value;

    private TipoPrescripcion_Enum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
