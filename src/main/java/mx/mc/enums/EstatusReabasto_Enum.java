package mx.mc.enums;

/**
 *
 * @author hramirez
 */
public enum EstatusReabasto_Enum {
	REGISTRADA(1),
	SOLICITADA(2),
	FARMACIA(3),
	ENTRANSITO(4),
	RECIBIDAPARCIAL(5),
	RECIBIDA(6),
	CANCELADA(7),
	INGRESADA(8),
        NO_SURTIDO(9),
        CANCELADA_VIGENCIA(10),
        SURTIDAPARCIAL(11)
	;
	
	private final Integer value;

    private EstatusReabasto_Enum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

}
