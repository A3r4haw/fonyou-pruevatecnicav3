package mx.mc.enums;

/**
 *
 * @author apalacios
 */
public enum TipoImpresora_Enum {
    NORMAL("N"),
    ETIQUETA("E"),
    PULSERA("P"),
    TICKET("T"),
    ONC("ONC"),
    ANT("ANT"),
    NPT("NPT"),
    UNI("UNI"),
    ;

    private final String value;

    private TipoImpresora_Enum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
