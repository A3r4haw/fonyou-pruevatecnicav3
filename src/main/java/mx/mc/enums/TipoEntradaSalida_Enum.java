package mx.mc.enums;

/**
 *
 * @author mcalderon
 */
public enum TipoEntradaSalida_Enum {

    ENTRADA("E"),
    SALIDA("S"),;

    private final String value;

    private TipoEntradaSalida_Enum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
