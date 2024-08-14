package mx.mc.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Cervanets
 */
public class NotaDispenColectDetalle_Extended extends NotaDispenColectDetalle implements Serializable {

    private static final long serialVersionUID = 1L;

    private String tipoSolucion;
    private String nombreContenedor;
    private Date fechaProgramada;
    private String folio;
    private String nombrePaciente;
    private String nombreEstructura;
    private String cama;
    private String nombreMedico;
    private String nombreUsuarioRecibe;
    private String tipoPrescripcion;
    private Date fechaParaEntregar;

    public String getTipoSolucion() {
        return tipoSolucion;
    }

    public void setTipoSolucion(String tipoSolucion) {
        this.tipoSolucion = tipoSolucion;
    }

    public String getNombreContenedor() {
        return nombreContenedor;
    }

    public void setNombreContenedor(String nombreContenedor) {
        this.nombreContenedor = nombreContenedor;
    }

    public Date getFechaProgramada() {
        return fechaProgramada;
    }

    public void setFechaProgramada(Date fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getNombreEstructura() {
        return nombreEstructura;
    }

    public void setNombreEstructura(String nombreEstructura) {
        this.nombreEstructura = nombreEstructura;
    }

    public String getCama() {
        return cama;
    }

    public void setCama(String cama) {
        this.cama = cama;
    }

    public String getNombreMedico() {
        return nombreMedico;
    }

    public void setNombreMedico(String nombreMedico) {
        this.nombreMedico = nombreMedico;
    }

    public String getNombreUsuarioRecibe() {
        return nombreUsuarioRecibe;
    }

    public void setNombreUsuarioRecibe(String nombreUsuarioRecibe) {
        this.nombreUsuarioRecibe = nombreUsuarioRecibe;
    }

    public String getTipoPrescripcion() {
        return tipoPrescripcion;
    }

    public void setTipoPrescripcion(String tipoPrescripcion) {
        this.tipoPrescripcion = tipoPrescripcion;
    }

    public Date getFechaParaEntregar() {
        return fechaParaEntregar;
    }

    public void setFechaParaEntregar(Date fechaParaEntregar) {
        this.fechaParaEntregar = fechaParaEntregar;
    }

}
