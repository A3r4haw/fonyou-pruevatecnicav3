package mx.mc.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Cervanets
 */
public class NotaDispenColect_Extended extends NotaDispenColect implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nombreUsuarioEntrega;
    private String nombreAreaDispensa;
    private String nombreUsuarioDispensa;
    private String nombreAreaDistribuye;
    private String nombreUsuarioDistribuye;
    private String nombreEstructura;
    private String nombreUsuarioRecibe;
    private String estatusNotaDispencolect;
    private String nombreTurno;
    private String tipoSolucion;
    private Integer numeroMezclas;
    private Date fechaEntregaInicio;
    private Date fechaEntregaFin;
    private String cadena;

    public String getNombreUsuarioDistribuye() {
        return nombreUsuarioDistribuye;
    }

    public void setNombreUsuarioDistribuye(String nombreUsuarioDistribuye) {
        this.nombreUsuarioDistribuye = nombreUsuarioDistribuye;
    }

    public String getNombreAreaDistribuye() {
        return nombreAreaDistribuye;
    }

    public void setNombreAreaDistribuye(String nombreAreaDistribuye) {
        this.nombreAreaDistribuye = nombreAreaDistribuye;
    }

    public String getEstatusNotaDispencolect() {
        return estatusNotaDispencolect;
    }

    public void setEstatusNotaDispencolect(String estatusNotaDispencolect) {
        this.estatusNotaDispencolect = estatusNotaDispencolect;
    }

    public String getNombreTurno() {
        return nombreTurno;
    }

    public void setNombreTurno(String nombreTurno) {
        this.nombreTurno = nombreTurno;
    }

    public String getNombreAreaDispensa() {
        return nombreAreaDispensa;
    }

    public void setNombreAreaDispensa(String nombreAreaDispensa) {
        this.nombreAreaDispensa = nombreAreaDispensa;
    }

    public Integer getNumeroMezclas() {
        return numeroMezclas;
    }

    public void setNumeroMezclas(Integer numeroMezclas) {
        this.numeroMezclas = numeroMezclas;
    }

    public String getNombreUsuarioEntrega() {
        return nombreUsuarioEntrega;
    }

    public void setNombreUsuarioEntrega(String nombreUsuarioEntrega) {
        this.nombreUsuarioEntrega = nombreUsuarioEntrega;
    }

    public String getNombreUsuarioDispensa() {
        return nombreUsuarioDispensa;
    }

    public void setNombreUsuarioDispensa(String nombreUsuarioDispensa) {
        this.nombreUsuarioDispensa = nombreUsuarioDispensa;
    }

    public String getNombreUsuarioRecibe() {
        return nombreUsuarioRecibe;
    }

    public void setNombreUsuarioRecibe(String nombreUsuarioRecibe) {
        this.nombreUsuarioRecibe = nombreUsuarioRecibe;
    }

    public String getTipoSolucion() {
        return tipoSolucion;
    }

    public void setTipoSolucion(String tipoSolucion) {
        this.tipoSolucion = tipoSolucion;
    }

    public String getNombreEstructura() {
        return nombreEstructura;
    }

    public void setNombreEstructura(String nombreEstructura) {
        this.nombreEstructura = nombreEstructura;
    }

    public Date getFechaEntregaInicio() {
        return fechaEntregaInicio;
    }

    public void setFechaEntregaInicio(Date fechaEntregaInicio) {
        this.fechaEntregaInicio = fechaEntregaInicio;
    }

    public Date getFechaEntregaFin() {
        return fechaEntregaFin;
    }

    public void setFechaEntregaFin(Date fechaEntregaFin) {
        this.fechaEntregaFin = fechaEntregaFin;
    }

    public String getCadena() {
        return cadena;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }

}
