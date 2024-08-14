package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author hramirez
 */
public class Config implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idConfig;
    private String nombre;
    private String valor;
    private String descripcion;
    private boolean activa;
    private Integer idTipoParam;
    private Date insertFecha;
    private String insertidUsuario;
    private Date updateFecha;
    private String updateIdUsuario;

    public Config() {
        //No code needed in constructor
    }

    public Integer getIdConfig() {
        return idConfig;
    }

    public void setIdConfig(Integer idConfig) {
        this.idConfig = idConfig;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public Date getInsertFecha() {
        return insertFecha;
    }

    public void setInsertFecha(Date insertFecha) {
        this.insertFecha = insertFecha;
    }

    public String getInsertidUsuario() {
        return insertidUsuario;
    }

    public void setInsertidUsuario(String insertidUsuario) {
        this.insertidUsuario = insertidUsuario;
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

    public Integer getIdTipoParam() {
        return idTipoParam;
    }

    public void setIdTipoParam(Integer idTipoParam) {
        this.idTipoParam = idTipoParam;
    }

    @Override
    public String toString() {
        return "Config{" + "idConfig=" + idConfig + ", nombre=" + nombre + ", valor=" + valor + ", descripcion=" + descripcion + ", activa=" + activa + ", idTipoParam=" + idTipoParam + ", insertFecha=" + insertFecha + ", insertidUsuario=" + insertidUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + Objects.hashCode(this.idConfig);
        hash = 13 * hash + Objects.hashCode(this.nombre);
        hash = 13 * hash + Objects.hashCode(this.valor);
        hash = 13 * hash + Objects.hashCode(this.descripcion);
        hash = 13 * hash + (this.activa ? 1 : 0);
        hash = 13 * hash + Objects.hashCode(this.idTipoParam);
        hash = 13 * hash + Objects.hashCode(this.insertFecha);
        hash = 13 * hash + Objects.hashCode(this.insertidUsuario);
        hash = 13 * hash + Objects.hashCode(this.updateFecha);
        hash = 13 * hash + Objects.hashCode(this.updateIdUsuario);
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
        final Config other = (Config) obj;
        if (this.activa != other.activa) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.valor, other.valor)) {
            return false;
        }
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        if (!Objects.equals(this.insertidUsuario, other.insertidUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.idConfig, other.idConfig)) {
            return false;
        }
        if (!Objects.equals(this.idTipoParam, other.idTipoParam)) {
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
}
