package mx.mc.enums;

/**
 *
 * @author hramirez
 */
public enum TipoMovimiento_Enum {

    ENTRADA("E"),
    SALIDA("S"),
    TRANSFERENCIA("T"),
    MODIFICACION("M");

    private final String value;

    private TipoMovimiento_Enum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
