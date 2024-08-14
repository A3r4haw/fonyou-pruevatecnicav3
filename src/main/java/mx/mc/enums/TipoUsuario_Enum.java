package mx.mc.enums;

/**
 *
 * @author aortiz
 */
public enum TipoUsuario_Enum {

    MEDICO(1),
    ENFERMERA(2),
    ADMINISTRATIVO(3),
    ;

    private final Integer value;

    private TipoUsuario_Enum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

}
