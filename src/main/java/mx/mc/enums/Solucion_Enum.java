package mx.mc.enums;

/**
 *
 * @author Cervanets
 */
public enum Solucion_Enum {

    ONCOLOGIA("ONC"),
    NUTRIONALPARENTERAL("NPT"),
    ANTIBIOTICOS("ANT"),
    ;

    private final String value;

    private Solucion_Enum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}

