package mx.mc.enums;

/**
 *
 * @author hramirez
 */
public enum MotivoCancelacionRechazoSolucion_Enum {
    ALTA_PAC(1, "Alta de paciente"),
    CAMBIO_TRATAMIENTO(2, "Cambio de Tratamiento"),
    DATOS_INCOMPLETOS(3, "Datos incompletos"),
    ALTA_DIAGNOSTICO(4, "Diagnóstico de alta"),
    EGRESO_PAC(5, "Egreso de paciente"),
    ERROR_PREPARACION(6, "Error en preparación"),
    ERROR_PRESCRIPCION(7, "Error en prescripción"),
    MEZCLA_INESTABLE(8, "Mezcla inestable"),
    SOSP_EVENNT_ADVERSO(9, "Sospecha de evento Adverso"),
    REACC_HIPERSENSIBILIDAD(10, "Reacción de hipersensibilidad"),
    SOSP_RAM(11, "Sospecha de RAM"),
    MEDIC_CONTAMINADO(12, "Medicamento contaminado"),
    MEDIC_CADUCO(13, "Medicamento caduco"),
    COND_PREP_INCORRECTA(14, "Condiciones de preparación incorrectas"),
    COND_CONS_INCORRECTA(15, "Condiciones de conservación incorrectas"),
    OTRO(16, "Otro"),
    CANT_INSUFICIENTES(17, "Cantidades Insuficientes");

    private final Integer value;
    private final String descripcion;

    private MotivoCancelacionRechazoSolucion_Enum(Integer value, String descripcion) {
        this.value = value;
        this.descripcion = descripcion;
    }

    public Integer getValue() {
        return value;
    }

    public String getDescripcion() {
        return descripcion;
    }

}
