package mx.mc.enums;

/**
 *
 * @author hramirez
 */
public enum CodTipoMovimiento_Enum {

    ENTRADA(1, 1),
    SALIDA(2, -1),
    NA(3, 0),;

    private final Integer value;
    private final Integer sufijo;

    private CodTipoMovimiento_Enum(Integer value, Integer sufijo) {
        this.value = value;
        this.sufijo = sufijo;
    }

    public Integer getValue() {
        return value;
    }

    public Integer getSufijo() {
        return sufijo;
    }

}
