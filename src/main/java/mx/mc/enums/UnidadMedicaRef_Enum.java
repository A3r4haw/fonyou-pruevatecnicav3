package mx.mc.enums;

/**
 *
 * @author aortiz
 */
public enum UnidadMedicaRef_Enum {

    IMSS(4),
    ISSSTE(5),
    SEDENA(6),
    SEGURO_POPULAR(51),
    ;

    private final Integer value;

    private UnidadMedicaRef_Enum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

}
