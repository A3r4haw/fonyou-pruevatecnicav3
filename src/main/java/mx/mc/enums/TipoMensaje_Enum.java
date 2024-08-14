package mx.mc.enums;

/**
 *
 * @author mcalderon
 */
public enum TipoMensaje_Enum {

    INTIPHARM("INT"),
    HIS("HIS"),;

    private final String value;

    private TipoMensaje_Enum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
