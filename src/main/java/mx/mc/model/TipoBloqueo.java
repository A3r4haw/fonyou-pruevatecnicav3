package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author olozada
 */
public class TipoBloqueo implements Serializable {
    private static final long serialVersionUID = 1L;
    
  private Integer idTipoBloqueo;
  private String tipoBloqueo;
  private String comentarios;
  private Integer activo;
  private Date insertFecha;
  private String insertIdUsuario;
  private Date updateFecha;
  private String updateIdUsuario;
  private String fecha;
  private String claveInstitucional;
  private String lote;

   
  public TipoBloqueo() {

  } 
  
  public TipoBloqueo(Integer idTipoBloqueo) {
        this.idTipoBloqueo = idTipoBloqueo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.idTipoBloqueo);
        hash = 89 * hash + Objects.hashCode(this.tipoBloqueo);
        hash = 89 * hash + Objects.hashCode(this.comentarios);
        hash = 89 * hash + Objects.hashCode(this.activo);
        hash = 89 * hash + Objects.hashCode(this.insertFecha);
        hash = 89 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 89 * hash + Objects.hashCode(this.updateFecha);
        hash = 89 * hash + Objects.hashCode(this.updateIdUsuario);
        hash = 89 * hash + Objects.hashCode(this.fecha);
        hash = 89 * hash + Objects.hashCode(this.claveInstitucional);
        hash = 89 * hash + Objects.hashCode(this.lote);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TipoBloqueo other = (TipoBloqueo) obj;
        if (!Objects.equals(this.idTipoBloqueo, other.idTipoBloqueo)) {
            return false;
        }
        if (!Objects.equals(this.tipoBloqueo, other.tipoBloqueo)) {
            return false;
        }
        if (!Objects.equals(this.comentarios, other.comentarios)) {
            return false;
        }
        if (!Objects.equals(this.activo, other.activo)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateFecha, other.updateFecha)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.fecha, other.fecha)) {
            return false;
        }
        if (!Objects.equals(this.claveInstitucional, other.claveInstitucional)) {
            return false;
        }
        return Objects.equals(this.lote, other.lote);
    }

    @Override
    public String toString() {
        return "TipoBloqueo{" + "idTipoBloqueo=" + idTipoBloqueo + ", tipoBloqueo=" + tipoBloqueo + ", comentarios=" + comentarios + ", activo=" + activo + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + ", fecha=" + fecha + ", claveInstitucional=" + claveInstitucional + ", lote=" + lote + '}';
    }

    public Integer getIdTipoBloqueo() {
        return idTipoBloqueo;
    }

    public void setIdTipoBloqueo(Integer idTipoBloqueo) {
        this.idTipoBloqueo = idTipoBloqueo;
    }

    public String getTipoBloqueo() {
        return tipoBloqueo;
    }

    public void setTipoBloqueo(String TipoBloqueo) {
        this.tipoBloqueo = TipoBloqueo;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getClaveInstitucional() {
        return claveInstitucional;
    }

    public void setClaveInstitucional(String claveInstitucional) {
        this.claveInstitucional = claveInstitucional;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }
  
    
}
