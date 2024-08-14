package mx.mc.enums;

/**
 *
 * @author hramirez
 */
public enum TipoAreaEstructura_Enum {

    HOSPITAL(1),
    ALMACEN(2),
    CONSULTAINTERNA(3),
    CONSULTAEXTERNA(4),
    ESPECIALIDAD(5),
    AREA(6),
    SERVICIO(7),
    ALA(8),
    PABELLO(9),
    ;

    private final Integer value;

    private TipoAreaEstructura_Enum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

}
