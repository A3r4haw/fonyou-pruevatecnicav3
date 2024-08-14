package mx.mc.enums;

/**
 * @author hramireza
 */
public enum GrupoCatalogoGeneral_Enum {

    TIPO_DE_PACIENTE(1),
    UNIDAD_MEDICA(2),
    ESTADO_CIVIL(3),
    ESCOLARIDAD(4),
    OCUPACION(5),
    GPO_ETNICO(6),
    RELIGION(7),
    GRUPO_SANGUINEO(8),
    NVEL_SOC_ECONOM(9),
    TIPO_VIVIENDA(10),
    TIPO_PARENTESCO(11),
    TIPO_INSUMOS(12);

    private final int value;

    private GrupoCatalogoGeneral_Enum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
