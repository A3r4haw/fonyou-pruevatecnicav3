package mx.mc.model;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Cervanets
 */
public class NotaDispenColect {

    private String idNotaDispenColect;
    private String folio;
    private String idEstructura;
    private String idTipoSolucion;
    private Integer idTurno;
    private String comentarios;
    private Date fechaEntrega;
    private String idUsuarioEntrega;
    private Date fechaDispensa;
    private String idUsuarioDispensa;
    private Date fechaDistribuye;
    private String idUsuarioDistribuye;
    private Date fechaRecibe;
    private String idUsuarioRecibe;
    private String notas;
    private Integer idEstatusDispenColect;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;
    private String userRecibe;

    public NotaDispenColect() {
    }

    public NotaDispenColect(String idNotaDispenColect) {
        this.idNotaDispenColect = idNotaDispenColect;
    }

    public NotaDispenColect(String idNotaDispenColect, String folio, String idEstructura, String idTipoSolucion, Integer idTurno, String comentarios, Date fechaEntrega, String idUsuarioEntrega, Date fechaDispensa, String idUsuarioDispensa, Date fechaDistribuye, String idUsuarioDistribuye, Date fechaRecibe, String idUsuarioRecibe, String notas, Integer idEstatusDispenColect, Date insertFecha, String insertIdUsuario, Date updateFecha, String updateIdUsuario, String userRecibe) {
        this.idNotaDispenColect = idNotaDispenColect;
        this.folio = folio;
        this.idEstructura = idEstructura;
        this.idTipoSolucion = idTipoSolucion;
        this.idTurno = idTurno;
        this.comentarios = comentarios;
        this.fechaEntrega = fechaEntrega;
        this.idUsuarioEntrega = idUsuarioEntrega;
        this.fechaDispensa = fechaDispensa;
        this.idUsuarioDispensa = idUsuarioDispensa;
        this.fechaDistribuye = fechaDistribuye;
        this.idUsuarioDistribuye = idUsuarioDistribuye;
        this.fechaRecibe = fechaRecibe;
        this.idUsuarioRecibe = idUsuarioRecibe;
        this.notas = notas;
        this.idEstatusDispenColect = idEstatusDispenColect;
        this.insertFecha = insertFecha;
        this.insertIdUsuario = insertIdUsuario;
        this.updateFecha = updateFecha;
        this.updateIdUsuario = updateIdUsuario;
        this.userRecibe = userRecibe;
    }

    @Override
    public String toString() {
        return "NotaDispenColect{" + "idNotaDispenColect=" + idNotaDispenColect + ", folio=" + folio + ", idEstructura=" + idEstructura + ", idTipoSolucion=" + idTipoSolucion + ", idTurno=" + idTurno + ", comentarios=" + comentarios + ", fechaEntrega=" + fechaEntrega + ", idUsuarioEntrega=" + idUsuarioEntrega + ", fechaDispensa=" + fechaDispensa + ", idUsuarioDispensa=" + idUsuarioDispensa + ", fechaDistribuye=" + fechaDistribuye + ", idUsuarioDistribuye=" + idUsuarioDistribuye + ", fechaRecibe=" + fechaRecibe + ", idUsuarioRecibe=" + idUsuarioRecibe + ", notas=" + notas + ", idEstatusDispenColect=" + idEstatusDispenColect + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + ", userRecibe=" + userRecibe + '}';
    }

    public String getIdNotaDispenColect() {
        return idNotaDispenColect;
    }

    public void setIdNotaDispenColect(String idNotaDispenColect) {
        this.idNotaDispenColect = idNotaDispenColect;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public String getIdTipoSolucion() {
        return idTipoSolucion;
    }

    public void setIdTipoSolucion(String idTipoSolucion) {
        this.idTipoSolucion = idTipoSolucion;
    }

    public Integer getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(Integer idTurno) {
        this.idTurno = idTurno;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getIdUsuarioEntrega() {
        return idUsuarioEntrega;
    }

    public void setIdUsuarioEntrega(String idUsuarioEntrega) {
        this.idUsuarioEntrega = idUsuarioEntrega;
    }

    public Date getFechaDispensa() {
        return fechaDispensa;
    }

    public void setFechaDispensa(Date fechaDispensa) {
        this.fechaDispensa = fechaDispensa;
    }

    public String getIdUsuarioDispensa() {
        return idUsuarioDispensa;
    }

    public void setIdUsuarioDispensa(String idUsuarioDispensa) {
        this.idUsuarioDispensa = idUsuarioDispensa;
    }

    public Date getFechaDistribuye() {
        return fechaDistribuye;
    }

    public void setFechaDistribuye(Date fechaDistribuye) {
        this.fechaDistribuye = fechaDistribuye;
    }

    public String getIdUsuarioDistribuye() {
        return idUsuarioDistribuye;
    }

    public void setIdUsuarioDistribuye(String idUsuarioDistribuye) {
        this.idUsuarioDistribuye = idUsuarioDistribuye;
    }

    public Date getFechaRecibe() {
        return fechaRecibe;
    }

    public void setFechaRecibe(Date fechaRecibe) {
        this.fechaRecibe = fechaRecibe;
    }

    public String getIdUsuarioRecibe() {
        return idUsuarioRecibe;
    }

    public void setIdUsuarioRecibe(String idUsuarioRecibe) {
        this.idUsuarioRecibe = idUsuarioRecibe;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public Integer getIdEstatusDispenColect() {
        return idEstatusDispenColect;
    }

    public void setIdEstatusDispenColect(Integer idEstatusDispenColect) {
        this.idEstatusDispenColect = idEstatusDispenColect;
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

    public String getUpdateIdUsuario() {
        return updateIdUsuario;
    }

    public void setUpdateIdUsuario(String updateIdUsuario) {
        this.updateIdUsuario = updateIdUsuario;
    }

    public String getUserRecibe() {
        return userRecibe;
    }

    public void setUserRecibe(String userRecibe) {
        this.userRecibe = userRecibe;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.idNotaDispenColect);
        hash = 37 * hash + Objects.hashCode(this.folio);
        hash = 37 * hash + Objects.hashCode(this.idEstructura);
        hash = 37 * hash + Objects.hashCode(this.idTipoSolucion);
        hash = 37 * hash + Objects.hashCode(this.idTurno);
        hash = 37 * hash + Objects.hashCode(this.comentarios);
        hash = 37 * hash + Objects.hashCode(this.fechaEntrega);
        hash = 37 * hash + Objects.hashCode(this.idUsuarioEntrega);
        hash = 37 * hash + Objects.hashCode(this.fechaDispensa);
        hash = 37 * hash + Objects.hashCode(this.idUsuarioDispensa);
        hash = 37 * hash + Objects.hashCode(this.fechaDistribuye);
        hash = 37 * hash + Objects.hashCode(this.idUsuarioDistribuye);
        hash = 37 * hash + Objects.hashCode(this.fechaRecibe);
        hash = 37 * hash + Objects.hashCode(this.idUsuarioRecibe);
        hash = 37 * hash + Objects.hashCode(this.notas);
        hash = 37 * hash + Objects.hashCode(this.idEstatusDispenColect);
        hash = 37 * hash + Objects.hashCode(this.insertFecha);
        hash = 37 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 37 * hash + Objects.hashCode(this.updateFecha);
        hash = 37 * hash + Objects.hashCode(this.updateIdUsuario);
        hash = 37 * hash + Objects.hashCode(this.userRecibe);
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
        final NotaDispenColect other = (NotaDispenColect) obj;
        if (!Objects.equals(this.idNotaDispenColect, other.idNotaDispenColect)) {
            return false;
        }
        if (!Objects.equals(this.folio, other.folio)) {
            return false;
        }
        if (!Objects.equals(this.idEstructura, other.idEstructura)) {
            return false;
        }
        if (!Objects.equals(this.idTipoSolucion, other.idTipoSolucion)) {
            return false;
        }
        if (!Objects.equals(this.comentarios, other.comentarios)) {
            return false;
        }
        if (!Objects.equals(this.idUsuarioEntrega, other.idUsuarioEntrega)) {
            return false;
        }
        if (!Objects.equals(this.idUsuarioDispensa, other.idUsuarioDispensa)) {
            return false;
        }
        if (!Objects.equals(this.idUsuarioDistribuye, other.idUsuarioDistribuye)) {
            return false;
        }
        if (!Objects.equals(this.idUsuarioRecibe, other.idUsuarioRecibe)) {
            return false;
        }
        if (!Objects.equals(this.notas, other.notas)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.idTurno, other.idTurno)) {
            return false;
        }
        if (!Objects.equals(this.fechaEntrega, other.fechaEntrega)) {
            return false;
        }
        if (!Objects.equals(this.fechaDispensa, other.fechaDispensa)) {
            return false;
        }
        if (!Objects.equals(this.fechaDistribuye, other.fechaDistribuye)) {
            return false;
        }
        if (!Objects.equals(this.fechaRecibe, other.fechaRecibe)) {
            return false;
        }
        if (!Objects.equals(this.idEstatusDispenColect, other.idEstatusDispenColect)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        if (!Objects.equals(this.updateFecha, other.updateFecha)) {
            return false;
        }
        return Objects.equals(this.userRecibe, other.userRecibe);
    }

}
