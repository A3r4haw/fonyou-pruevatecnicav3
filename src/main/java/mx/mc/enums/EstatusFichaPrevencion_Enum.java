package mx.mc.enums;

/**
 *
 * @author Cervanets
 */
public enum EstatusFichaPrevencion_Enum {
    REGISTRADO(1),
    INCOMPLETO(2),
    REVISADO(3),    
    NO_CONFORME(4),    
    APROBADO(5),    
        CANCELADO(6),    
    ;

    private final Integer value;

    private EstatusFichaPrevencion_Enum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
