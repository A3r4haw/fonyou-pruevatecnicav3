package mx.mc.enums;


/**
 *
 * @author Cervanets
 */
public enum TipoSurtimiento_Enum {
    MATERIAL("MAT"),
    MEDICAMENTO("MED"),
    OCOLOGICO("ONC"),
    ANTIBIOTICO("ANT"),
    NUTRICIONPARENTERAL("NPT");
    private final String value;

    private TipoSurtimiento_Enum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
