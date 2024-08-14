package mx.mc.enums;

/**
 *
 * @author apalacios
 */
public enum EstatusDispensacionMaterial_Enum {

    REGISTRADA(1),
    CANCELADA(2)
    ;

    private final Integer value;

    private EstatusDispensacionMaterial_Enum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

}
