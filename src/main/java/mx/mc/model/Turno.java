package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author aortiz
 */
public class Turno implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idTurno;
    private String nombre;
    private Integer lun;
    private Integer mar;
    private Integer mie;
    private Integer jue;
    private Integer vie;
    private Integer sab;
    private Integer dom;
    private Date horaInicio;
    private Date horaFin;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsurio;

    public Turno() {
    }
    
    public Turno(Integer idTurno) {
        this.idTurno = idTurno;
    }
    
    public Integer getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(Integer idTurno) {
        this.idTurno = idTurno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getLun() {
        return lun;
    }

    public void setLun(Integer lun) {
        this.lun = lun;
    }

    public Integer getMar() {
        return mar;
    }

    public void setMar(Integer mar) {
        this.mar = mar;
    }

    public Integer getMie() {
        return mie;
    }

    public void setMie(Integer mie) {
        this.mie = mie;
    }

    public Integer getJue() {
        return jue;
    }

    public void setJue(Integer jue) {
        this.jue = jue;
    }

    public Integer getVie() {
        return vie;
    }

    public void setVie(Integer vie) {
        this.vie = vie;
    }

    public Integer getSab() {
        return sab;
    }

    public void setSab(Integer sab) {
        this.sab = sab;
    }

    public Integer getDom() {
        return dom;
    }

    public void setDom(Integer dom) {
        this.dom = dom;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Date horaFin) {
        this.horaFin = horaFin;
    }

    public Date getInsertFecha() {
        return insertFecha;
    }

    public void setInsertFecha(Date insertFecha) {
        this.insertFecha = insertFecha;
    }

    public String getInsertIdUsuario() {
        return insertIdUsuario;
    }

    public void setInsertIdUsuario(String insertIdUsuario) {
        this.insertIdUsuario = insertIdUsuario;
    }

    public Date getUpdateFecha() {
        return updateFecha;
    }

    public void setUpdateFecha(Date updateFecha) {
        this.updateFecha = updateFecha;
    }

    public String getUpdateIdUsurio() {
        return updateIdUsurio;
    }

    public void setUpdateIdUsurio(String updateIdUsurio) {
        this.updateIdUsurio = updateIdUsurio;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.idTurno);
        hash = 97 * hash + Objects.hashCode(this.nombre);
        hash = 97 * hash + Objects.hashCode(this.lun);
        hash = 97 * hash + Objects.hashCode(this.mar);
        hash = 97 * hash + Objects.hashCode(this.mie);
        hash = 97 * hash + Objects.hashCode(this.jue);
        hash = 97 * hash + Objects.hashCode(this.vie);
        hash = 97 * hash + Objects.hashCode(this.sab);
        hash = 97 * hash + Objects.hashCode(this.dom);
        hash = 97 * hash + Objects.hashCode(this.horaInicio);
        hash = 97 * hash + Objects.hashCode(this.horaFin);
        hash = 97 * hash + Objects.hashCode(this.insertFecha);
        hash = 97 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 97 * hash + Objects.hashCode(this.updateFecha);
        hash = 97 * hash + Objects.hashCode(this.updateIdUsurio);
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
        final Turno other = (Turno) obj;
        if (!Objects.equals(this.idTurno, other.idTurno)) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsurio, other.updateIdUsurio)) {
            return false;
        }
        if (!Objects.equals(this.lun, other.lun)) {
            return false;
        }
        if (!Objects.equals(this.mar, other.mar)) {
            return false;
        }
        if (!Objects.equals(this.mie, other.mie)) {
            return false;
        }
        if (!Objects.equals(this.jue, other.jue)) {
            return false;
        }
        if (!Objects.equals(this.vie, other.vie)) {
            return false;
        }
        if (!Objects.equals(this.sab, other.sab)) {
            return false;
        }
        if (!Objects.equals(this.dom, other.dom)) {
            return false;
        }
        if (!Objects.equals(this.horaInicio, other.horaInicio)) {
            return false;
        }
        if (!Objects.equals(this.horaFin, other.horaFin)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        return Objects.equals(this.updateFecha, other.updateFecha);
    }
}
