package mx.mc.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author aortiz
 */
public class TurnoMedico implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idTurnoMedico;
    private Integer idTurno;
    private String idMedico;

    public TurnoMedico() {
    }

    public TurnoMedico(String idTurnoMedico, Integer idTurno, String idMedico) {
        this.idTurnoMedico = idTurnoMedico;
        this.idTurno = idTurno;
        this.idMedico = idMedico;
    }
    
    public String getIdTurnoMedico() {
        return idTurnoMedico;
    }

    public void setIdTurnoMedico(String idTurnoMedico) {
        this.idTurnoMedico = idTurnoMedico;
    }

    public Integer getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(Integer idTurno) {
        this.idTurno = idTurno;
    }

    public String getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(String idMedico) {
        this.idMedico = idMedico;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.idTurnoMedico);
        hash = 79 * hash + Objects.hashCode(this.idTurno);
        hash = 79 * hash + Objects.hashCode(this.idMedico);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TurnoMedico other = (TurnoMedico) obj;
        if (!Objects.equals(this.idTurnoMedico, other.idTurnoMedico)) {
            return false;
        }
        if (!Objects.equals(this.idMedico, other.idMedico)) {
            return false;
        }
        return Objects.equals(this.idTurno, other.idTurno);
    }
}
