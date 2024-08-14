package mx.mc.enums;

/**
 *
 * @author aortiz
 */
public enum PrefijoPrescripcion_Enum {

    NORMAL("E"),
    CONTROLADA("F"),
    ;

    private final String value;

    private PrefijoPrescripcion_Enum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
