package mx.mc.enums;

/**
 *
 * @author hramirez
 */
public enum TipoAlmacen_Enum {

    NO_APLICA(1),
    FARMACIA(2),
    ALMACEN(3),
    SUBALMACEN(4),
    CENTROMEZCLA(5),;

    private final Integer value;

    private TipoAlmacen_Enum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

}
