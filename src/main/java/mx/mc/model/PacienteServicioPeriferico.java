package mx.mc.model;

import java.util.Date;

/**
 *
 * @author mcalderon
 */
public class PacienteServicioPeriferico extends PacienteServicio{
    static final long serialVersionUID = 1L;
    private String idPacienteServicioPeriferico;
    private String idPaciente;    
    private Date fechaIngreso;
    private String idUsuarioIngresa;
    private String notasIngreso;
    private Date fechaEgreso;
    private String idUsuarioEgreso;
    private String notasEgreso;
    private String idMedicoAutoriza;
    private int idMotivoPacienteMovimientoSalida;    

    public PacienteServicioPeriferico() {
        //No code needed in constructor
    }

    public String getIdPacienteServicioPeriferico() {
        return idPacienteServicioPeriferico;
    }

    public void setIdPacienteServicioPeriferico(String idPacienteServicioPeriferico) {
        this.idPacienteServicioPeriferico = idPacienteServicioPeriferico;
    }

    public String getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(String idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getIdUsuarioIngresa() {
        return idUsuarioIngresa;
    }

    public void setIdUsuarioIngresa(String idUsuarioIngresa) {
        this.idUsuarioIngresa = idUsuarioIngresa;
    }

    public String getNotasIngreso() {
        return notasIngreso;
    }

    public void setNotasIngreso(String notasIngreso) {
        this.notasIngreso = notasIngreso;
    }    

    public Date getFechaEgreso() {
        return fechaEgreso;
    }

    public void setFechaEgreso(Date fechaEgreso) {
        this.fechaEgreso = fechaEgreso;
    }

    public String getIdUsuarioEgreso() {
        return idUsuarioEgreso;
    }

    public void setIdUsuarioEgreso(String idUsuarioEgreso) {
        this.idUsuarioEgreso = idUsuarioEgreso;
    }

    public String getNotasEgreso() {
        return notasEgreso;
    }

    public void setNotasEgreso(String notasEgreso) {
        this.notasEgreso = notasEgreso;
    }

    public String getIdMedicoAutoriza() {
        return idMedicoAutoriza;
    }

    public void setIdMedicoAutoriza(String idMedicoAutoriza) {
        this.idMedicoAutoriza = idMedicoAutoriza;
    }

    public int getIdMotivoPacienteMovimientoSalida() {
        return idMotivoPacienteMovimientoSalida;
    }

    public void setIdMotivoPacienteMovimientoSalida(int idMotivoPacienteMovimientoSalida) {
        this.idMotivoPacienteMovimientoSalida = idMotivoPacienteMovimientoSalida;
    }  
    
}
