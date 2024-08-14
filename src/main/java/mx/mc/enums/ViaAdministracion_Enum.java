package mx.mc.enums;

/**
 *
 * @author cervanets
 */
public enum ViaAdministracion_Enum {

    INTRAVENOSA(22),
    ORAL(34),
    INTRAMUSCULAR(13),;

    private final Integer value;

    private ViaAdministracion_Enum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

}
