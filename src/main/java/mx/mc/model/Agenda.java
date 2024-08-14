package mx.mc.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author aortiz
 */
public class Agenda implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idAgenda;
    private String idEstructura;
    private String nombre;
    private Integer estatus;
    private Date inicioOperacion;
    private Date finOperacion;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private Date updateIdUsuario;

    public Agenda() {
        //No code needed in constructor
    }

    public String getIdAgenda() {
        return idAgenda;
    }

    public void setIdAgenda(String idAgenda) {
        this.idAgenda = idAgenda;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public Date getInicioOperacion() {
        return inicioOperacion;
    }

    public void setInicioOperacion(Date inicioOperacion) {
        this.inicioOperacion = inicioOperacion;
    }

    public Date getFinOperacion() {
        return finOperacion;
    }

    public void setFinOperacion(Date finOperacion) {
        this.finOperacion = finOperacion;
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

    public Date getUpdateIdUsuario() {
        return updateIdUsuario;
    }

    public void setUpdateIdUsuario(Date updateIdUsuario) {
        this.updateIdUsuario = updateIdUsuario;
    }

}
