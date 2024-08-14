/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author gcruz
 */
public class Interaccion implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer idInteraccion;
    private Date fecha;
    private Integer idSustanciaUno;
    private Integer idSustanciaDos;
    private Integer idTipoInteraccion;
    private String notas;
    private Integer idEmisor;
    private String riesgo;
    private String insertIdUsuario;
    private Date insertFecha;
    private String updateIdUsuario;
    private Date updateFecha;
    
    public Interaccion() {
        
    }

    public Interaccion(Integer idInteraccion, Date fecha, Integer idSustanciaUno, Integer idSustanciaDos, Integer idTipoInteraccion, String notas, Integer idEmisor, String riesgo, String insertIdUsuario, Date insertFecha, String updateIdUsuario, Date updateFecha) {
        this.idInteraccion = idInteraccion;
        this.fecha = fecha;
        this.idSustanciaUno = idSustanciaUno;
        this.idSustanciaDos = idSustanciaDos;
        this.idTipoInteraccion = idTipoInteraccion;
        this.notas = notas;
        this.idEmisor = idEmisor;
        this.riesgo = riesgo;
        this.insertIdUsuario = insertIdUsuario;
        this.insertFecha = insertFecha;
        this.updateIdUsuario = updateIdUsuario;
        this.updateFecha = updateFecha;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.idInteraccion);
        hash = 43 * hash + Objects.hashCode(this.fecha);
        hash = 43 * hash + Objects.hashCode(this.idSustanciaUno);
        hash = 43 * hash + Objects.hashCode(this.idSustanciaDos);
        hash = 43 * hash + Objects.hashCode(this.idTipoInteraccion);
        hash = 43 * hash + Objects.hashCode(this.notas);
        hash = 43 * hash + Objects.hashCode(this.idEmisor);
        hash = 43 * hash + Objects.hashCode(this.riesgo);
        hash = 43 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 43 * hash + Objects.hashCode(this.insertFecha);
        hash = 43 * hash + Objects.hashCode(this.updateIdUsuario);
        hash = 43 * hash + Objects.hashCode(this.updateFecha);
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
        final Interaccion other = (Interaccion) obj;
        if (!Objects.equals(this.notas, other.notas)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.idInteraccion, other.idInteraccion)) {
            return false;
        }
        if (!Objects.equals(this.fecha, other.fecha)) {
            return false;
        }
        if (!Objects.equals(this.idSustanciaUno, other.idSustanciaUno)) {
            return false;
        }
        if (!Objects.equals(this.idSustanciaDos, other.idSustanciaDos)) {
            return false;
        }
        if (!Objects.equals(this.idTipoInteraccion, other.idTipoInteraccion)) {
            return false;
        }
        if (!Objects.equals(this.idEmisor, other.idEmisor)) {
            return false;
        }
        if (!Objects.equals(this.riesgo, other.riesgo)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        if (!Objects.equals(this.updateFecha, other.updateFecha)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Interaccion{" + "idInteraccion=" + idInteraccion + ", fecha=" + fecha + ", idSustanciaUno=" + idSustanciaUno + ", idSustanciaDos=" + idSustanciaDos + ", idTipoInteraccion=" + idTipoInteraccion + ", notas=" + notas + ", idEmisor=" + idEmisor + ", riesgo=" + riesgo + ", insertIdUsuario=" + insertIdUsuario + ", insertFecha=" + insertFecha + ", updateIdUsuario=" + updateIdUsuario + ", updateFecha=" + updateFecha + '}';
    }

    public Integer getIdInteraccion() {
        return idInteraccion;
    }

    public void setIdInteraccion(Integer idInteraccion) {
        this.idInteraccion = idInteraccion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getIdSustanciaUno() {
        return idSustanciaUno;
    }

    public void setIdSustanciaUno(Integer idSustanciaUno) {
        this.idSustanciaUno = idSustanciaUno;
    }

    public Integer getIdSustanciaDos() {
        return idSustanciaDos;
    }

    public void setIdSustanciaDos(Integer idSustanciaDos) {
        this.idSustanciaDos = idSustanciaDos;
    }   

    public Integer getIdTipoInteraccion() {
        return idTipoInteraccion;
    }

    public void setIdTipoInteraccion(Integer idTipoInteraccion) {
        this.idTipoInteraccion = idTipoInteraccion;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public Integer getIdEmisor() {
        return idEmisor;
    }

    public void setIdEmisor(Integer idEmisor) {
        this.idEmisor = idEmisor;
    }

    public String getRiesgo() {
        return riesgo;
    }

    public void setRiesgo(String riesgo) {
        this.riesgo = riesgo;
    }

    public String getInsertIdUsuario() {
        return insertIdUsuario;
    }

    public void setInsertIdUsuario(String insertIdUsuario) {
        this.insertIdUsuario = insertIdUsuario;
    }

    public Date getInsertFecha() {
        return insertFecha;
    }

    public void setInsertFecha(Date insertFecha) {
        this.insertFecha = insertFecha;
    }

    public String getUpdateIdUsuario() {
        return updateIdUsuario;
    }

    public void setUpdateIdUsuario(String updateIdUsuario) {
        this.updateIdUsuario = updateIdUsuario;
    }

    public Date getUpdateFecha() {
        return updateFecha;
    }

    public void setUpdateFecha(Date updateFecha) {
        this.updateFecha = updateFecha;
    }
    
    
    
}
