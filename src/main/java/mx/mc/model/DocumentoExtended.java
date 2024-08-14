package mx.mc.model;

/**
 *
 * @author hramirez
 */
public class DocumentoExtended extends Documento {

    private static final long serialVersionUID = 1L;

    private String nombreTipoArchivo;
    private String nombreEstructura;
    private String nombreUsuarioRegistra;
    private String nombreUsuarioActualiza;
    private String nombreUsuarioAutoriza;

    public String getNombreTipoArchivo() {
        return nombreTipoArchivo;
    }

    public void setNombreTipoArchivo(String nombreTipoArchivo) {
        this.nombreTipoArchivo = nombreTipoArchivo;
    }

    public String getNombreEstructura() {
        return nombreEstructura;
    }

    public void setNombreEstructura(String nombreEstructura) {
        this.nombreEstructura = nombreEstructura;
    }

    public String getNombreUsuarioRegistra() {
        return nombreUsuarioRegistra;
    }

    public void setNombreUsuarioRegistra(String nombreUsuarioRegistra) {
        this.nombreUsuarioRegistra = nombreUsuarioRegistra;
    }

    public String getNombreUsuarioActualiza() {
        return nombreUsuarioActualiza;
    }

    public void setNombreUsuarioActualiza(String nombreUsuarioActualiza) {
        this.nombreUsuarioActualiza = nombreUsuarioActualiza;
    }

    public String getNombreUsuarioAutoriza() {
        return nombreUsuarioAutoriza;
    }

    public void setNombreUsuarioAutoriza(String nombreUsuarioAutoriza) {
        this.nombreUsuarioAutoriza = nombreUsuarioAutoriza;
    }

}
