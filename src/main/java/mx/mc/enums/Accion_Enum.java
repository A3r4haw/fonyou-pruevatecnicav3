package mx.mc.enums;

/**
 *
 * @author hramirez
 */
public enum Accion_Enum {

    CREAR("CREAR"),
    VER("VER"),
    EDITAR("EDITAR"),
    ELIMINAR("ELIMINAR"),
    AUTORIZAR("AUTORIZAR"),
    PROCESAR("PROCESAR"),;

    private final String value;

    private Accion_Enum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
