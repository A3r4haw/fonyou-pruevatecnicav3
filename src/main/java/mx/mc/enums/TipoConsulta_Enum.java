package mx.mc.enums;

/**
 *
 * @author hramirez
 */
public enum TipoConsulta_Enum {

    CONSULTA_INTERNA("I"),
    CONSULTA_EXTERNA("E"),
    URGENCIAS("U"),
    HOSPITALIZACION("H"),
    ;

    private final String value;

    private TipoConsulta_Enum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
