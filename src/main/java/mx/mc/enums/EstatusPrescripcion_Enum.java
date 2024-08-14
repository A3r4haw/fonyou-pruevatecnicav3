package mx.mc.enums;

/**
 *
 * @author hramirez
 */
public enum EstatusPrescripcion_Enum {

    REGRISTRADA(1),
    PROGRAMADA(2),
    FINALIZADA(3),
    CANCELADA(4),
    PROCESANDO(5),
    SURTIDA_PARCIAL(6),
    ;

    private final Integer value;

    private EstatusPrescripcion_Enum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static EstatusPrescripcion_Enum getStatusFromId(int status) {
        switch (status) {
            default:
                return null;
            case 0:
                return null;
            case 1:
                return REGRISTRADA;
            case 2:
                return PROGRAMADA;
            case 3:
                return FINALIZADA;
            case 4:
                return CANCELADA;
            case 5:
                return PROCESANDO;
            case 6:
                return SURTIDA_PARCIAL;
        }
    }
    
}
