package mx.mc.enums;

/**
 *
 * @author hramirez
 */
public enum Frecuencia_Enum {

    OD(24),
    BID(12),
    ONCE(0),
//    OO(O),
    QID(4),
    TID(3),
    _6xD(6),
    C1(1),
    C2(2),;

    private final Integer value;

    private Frecuencia_Enum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

}
