package mx.mc.model;

/**
 *
 * @author Cervanets
 */
public class FichaPrevencionExtended extends FichaPrevencion {

    private static final long serialVersionUID = 1L;

    private String nombreUsuarioRegistra;
    private String nombreUsuarioActualiza;
    private String nombreEstructura;
    private String estatusPrevencion;
    private String nombreArea;
    private String nombreTurno;
    private String nombreUsuarioRealizaLimpieza;
    private String nombreUsuarioSupervisa;
    private String nombreUsuarioAprueba;

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

    public String getNombreEstructura() {
        return nombreEstructura;
    }

    public void setNombreEstructura(String nombreEstructura) {
        this.nombreEstructura = nombreEstructura;
    }

    public String getEstatusPrevencion() {
        return estatusPrevencion;
    }

    public void setEstatusPrevencion(String estatusPrevencion) {
        this.estatusPrevencion = estatusPrevencion;
    }

    public String getNombreArea() {
        return nombreArea;
    }

    public void setNombreArea(String nombreArea) {
        this.nombreArea = nombreArea;
    }

    public String getNombreTurno() {
        return nombreTurno;
    }

    public void setNombreTurno(String nombreTurno) {
        this.nombreTurno = nombreTurno;
    }

    public String getNombreUsuarioRealizaLimpieza() {
        return nombreUsuarioRealizaLimpieza;
    }

    public void setNombreUsuarioRealizaLimpieza(String nombreUsuarioRealizaLimpieza) {
        this.nombreUsuarioRealizaLimpieza = nombreUsuarioRealizaLimpieza;
    }

    public String getNombreUsuarioSupervisa() {
        return nombreUsuarioSupervisa;
    }

    public void setNombreUsuarioSupervisa(String nombreUsuarioSupervisa) {
        this.nombreUsuarioSupervisa = nombreUsuarioSupervisa;
    }

    public String getNombreUsuarioAprueba() {
        return nombreUsuarioAprueba;
    }

    public void setNombreUsuarioAprueba(String nombreUsuarioAprueba) {
        this.nombreUsuarioAprueba = nombreUsuarioAprueba;
    }

}
