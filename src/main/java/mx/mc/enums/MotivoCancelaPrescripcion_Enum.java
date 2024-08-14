package mx.mc.enums;

/**
 *
 * @author Cervanets
 */
public enum MotivoCancelaPrescripcion_Enum {

        ALTA(1),
        CAMBIO_TRATAMIENTO(2),
        DIAGNOSTICO_DE_ALTA(3),
        EGRESO(4),
        ERROR_PRESCRIPCION(5),
        SOSPECHA_EVENTO_ADVERSO(6),
        ALTA_VOLUNTARIA(7),
        TRANSFERENCIA_SERVICIO(8),
        TRANSFERENCIA_HOSPITALARIA(9);

        private final Integer value;

        private MotivoCancelaPrescripcion_Enum(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

    }
