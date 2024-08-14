package mx.mc.enums;

/**
 *
 * @author hramirez
 */
public enum TipoCalorias_Enum {

    PROTEICAS(1),
    NO_PROTEICAS(2);

    private final Integer value;

    private TipoCalorias_Enum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

}
