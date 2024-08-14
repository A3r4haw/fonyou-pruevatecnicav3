package mx.mc.enums;

/**
 *
 * @author hramirez
 */
public enum NivelLog_Enum {

    ALL("ALL", ""),
    TRACE("TRACE", ""),
    DEBUG("DEBUG", ""),
    INFO("INFO", ""),
    WARNING("WARNING", ""),
    ERROR("ERROR", ""),
    FATAL("FATAL", ""),
    ;

    private final String value;
    private final String sufijo;

    private NivelLog_Enum(String value, String sufijo) {
        this.value = value;
        this.sufijo = sufijo;
    }

    public String getValue() {
        return value;
    }

    public String getSufijo() {
        return sufijo;
    }

}
