package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Cervanets
 */
public class HorarioEntrega implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idHorarioEntrega;
    private String nombre;
    private String descripcion;
    private Integer entregaProgramada;
    private Integer entregaMismoDia;
    private Integer diaEntrega;
    private Date horaMinimaSolicitud;
    private Date horaMaximaSolicitud;
    private Date horaEntrega;
    private Integer lunes;
    private Integer martes;
    private Integer miercoles;
    private Integer jueves;
    private Integer viernes;
    private Integer sabado;
    private Integer domingo;
    private String idTipoSolucion;
    private Date horaMaximaCancelacion;
    private Integer solicitudUrgente;
    private Integer activa;
    private Date insertFecha;
    private String insertIdUsuario;
    private Integer updateFecha;
    private Date updateIdUsuario;

    public HorarioEntrega() {
    }

    public HorarioEntrega(Integer idHorarioEntrega) {
        this.idHorarioEntrega = idHorarioEntrega;
    }

    public Integer getIdHorarioEntrega() {
        return idHorarioEntrega;
    }

    public void setIdHorarioEntrega(Integer idHorarioEntrega) {
        this.idHorarioEntrega = idHorarioEntrega;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getEntregaProgramada() {
        return entregaProgramada;
    }

    public void setEntregaProgramada(Integer entregaProgramada) {
        this.entregaProgramada = entregaProgramada;
    }

    public Integer getEntregaMismoDia() {
        return entregaMismoDia;
    }

    public void setEntregaMismoDia(Integer entregaMismoDia) {
        this.entregaMismoDia = entregaMismoDia;
    }

    public Integer getDiaEntrega() {
        return diaEntrega;
    }

    public void setDiaEntrega(Integer diaEntrega) {
        this.diaEntrega = diaEntrega;
    }

    public Date getHoraMinimaSolicitud() {
        return horaMinimaSolicitud;
    }

    public void setHoraMinimaSolicitud(Date horaMinimaSolicitud) {
        this.horaMinimaSolicitud = horaMinimaSolicitud;
    }

    public Date getHoraMaximaSolicitud() {
        return horaMaximaSolicitud;
    }

    public void setHoraMaximaSolicitud(Date horaMaximaSolicitud) {
        this.horaMaximaSolicitud = horaMaximaSolicitud;
    }

    public Date getHoraEntrega() {
        return horaEntrega;
    }

    public void setHoraEntrega(Date horaEntrega) {
        this.horaEntrega = horaEntrega;
    }

    public Integer getLunes() {
        return lunes;
    }

    public void setLunes(Integer lunes) {
        this.lunes = lunes;
    }

    public Integer getMartes() {
        return martes;
    }

    public void setMartes(Integer martes) {
        this.martes = martes;
    }

    public Integer getMiercoles() {
        return miercoles;
    }

    public void setMiercoles(Integer miercoles) {
        this.miercoles = miercoles;
    }

    public Integer getJueves() {
        return jueves;
    }

    public void setJueves(Integer jueves) {
        this.jueves = jueves;
    }

    public Integer getViernes() {
        return viernes;
    }

    public void setViernes(Integer viernes) {
        this.viernes = viernes;
    }

    public Integer getSabado() {
        return sabado;
    }

    public void setSabado(Integer sabado) {
        this.sabado = sabado;
    }

    public Integer getDomingo() {
        return domingo;
    }

    public void setDomingo(Integer domingo) {
        this.domingo = domingo;
    }

    public String getIdTipoSolucion() {
        return idTipoSolucion;
    }

    public void setIdTipoSolucion(String idTipoSolucion) {
        this.idTipoSolucion = idTipoSolucion;
    }
    
    public Date getHoraMaximaCancelacion() {
        return horaMaximaCancelacion;
    }

    public void setHoraMaximaCancelacion(Date horaMaximaCancelacion) {
        this.horaMaximaCancelacion = horaMaximaCancelacion;
    }

    public Integer getSolicitudUrgente() {
        return solicitudUrgente;
    }

    public void setSolicitudUrgente(Integer solicitudUrgente) {
        this.solicitudUrgente = solicitudUrgente;
    }

    public Integer getActiva() {
        return activa;
    }

    public void setActiva(Integer activa) {
        this.activa = activa;
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

    public Integer getUpdateFecha() {
        return updateFecha;
    }

    public void setUpdateFecha(Integer updateFecha) {
        this.updateFecha = updateFecha;
    }

    public Date getUpdateIdUsuario() {
        return updateIdUsuario;
    }

    public void setUpdateIdUsuario(Date updateIdUsuario) {
        this.updateIdUsuario = updateIdUsuario;
    }

    @Override
    public String toString() {
        return "HorarioEntrega{" + "idHorarioEntrega=" + idHorarioEntrega + ", nombre=" + nombre + ", descripcion=" + descripcion + ", entregaProgramada=" + entregaProgramada + ", entregaMismoDia=" + entregaMismoDia + ", diaEntrega=" + diaEntrega + ", horaMinimaSolicitud=" + horaMinimaSolicitud + ", horaMaximaSolicitud=" + horaMaximaSolicitud + ", horaEntrega=" + horaEntrega + ", lunes=" + lunes + ", martes=" + martes + ", miercoles=" + miercoles + ", jueves=" + jueves + ", viernes=" + viernes + ", sabado=" + sabado + ", domingo=" + domingo + ", idTipoSolucion=" + idTipoSolucion + ", horaMaximaCancelacion=" + horaMaximaCancelacion + ", solicitudUrgente=" + solicitudUrgente + ", activa=" + activa + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.idHorarioEntrega);
        hash = 97 * hash + Objects.hashCode(this.nombre);
        hash = 97 * hash + Objects.hashCode(this.descripcion);
        hash = 97 * hash + Objects.hashCode(this.entregaProgramada);
        hash = 97 * hash + Objects.hashCode(this.entregaMismoDia);
        hash = 97 * hash + Objects.hashCode(this.diaEntrega);
        hash = 97 * hash + Objects.hashCode(this.horaMinimaSolicitud);
        hash = 97 * hash + Objects.hashCode(this.horaMaximaSolicitud);
        hash = 97 * hash + Objects.hashCode(this.horaEntrega);
        hash = 97 * hash + Objects.hashCode(this.lunes);
        hash = 97 * hash + Objects.hashCode(this.martes);
        hash = 97 * hash + Objects.hashCode(this.miercoles);
        hash = 97 * hash + Objects.hashCode(this.jueves);
        hash = 97 * hash + Objects.hashCode(this.viernes);
        hash = 97 * hash + Objects.hashCode(this.sabado);
        hash = 97 * hash + Objects.hashCode(this.domingo);
        hash = 97 * hash + Objects.hashCode(this.idTipoSolucion);
        hash = 97 * hash + Objects.hashCode(this.horaMaximaCancelacion);
        hash = 97 * hash + Objects.hashCode(this.solicitudUrgente);
        hash = 97 * hash + Objects.hashCode(this.activa);
        hash = 97 * hash + Objects.hashCode(this.insertFecha);
        hash = 97 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 97 * hash + Objects.hashCode(this.updateFecha);
        hash = 97 * hash + Objects.hashCode(this.updateIdUsuario);
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
        final HorarioEntrega other = (HorarioEntrega) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.idHorarioEntrega, other.idHorarioEntrega)) {
            return false;
        }
        if (!Objects.equals(this.entregaProgramada, other.entregaProgramada)) {
            return false;
        }
        if (!Objects.equals(this.entregaMismoDia, other.entregaMismoDia)) {
            return false;
        }
        if (!Objects.equals(this.diaEntrega, other.diaEntrega)) {
            return false;
        }
        if (!Objects.equals(this.horaMinimaSolicitud, other.horaMinimaSolicitud)) {
            return false;
        }
        if (!Objects.equals(this.horaMaximaSolicitud, other.horaMaximaSolicitud)) {
            return false;
        }
        if (!Objects.equals(this.horaEntrega, other.horaEntrega)) {
            return false;
        }
        if (!Objects.equals(this.lunes, other.lunes)) {
            return false;
        }
        if (!Objects.equals(this.martes, other.martes)) {
            return false;
        }
        if (!Objects.equals(this.miercoles, other.miercoles)) {
            return false;
        }
        if (!Objects.equals(this.jueves, other.jueves)) {
            return false;
        }
        if (!Objects.equals(this.viernes, other.viernes)) {
            return false;
        }
        if (!Objects.equals(this.sabado, other.sabado)) {
            return false;
        }
        if (!Objects.equals(this.domingo, other.domingo)) {
            return false;
        }
        if (!Objects.equals(this.idTipoSolucion, other.idTipoSolucion)) {
            return false;
        }
        if (!Objects.equals(this.horaMaximaCancelacion, other.horaMaximaCancelacion)) {
            return false;
        }
        if (!Objects.equals(this.solicitudUrgente, other.solicitudUrgente)) {
            return false;
        }
        if (!Objects.equals(this.activa, other.activa)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        if (!Objects.equals(this.updateFecha, other.updateFecha)) {
            return false;
        }
        return Objects.equals(this.updateIdUsuario, other.updateIdUsuario);
    }

}
