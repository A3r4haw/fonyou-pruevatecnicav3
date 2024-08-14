package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author olozada
 */
public class EnvioNeumatico implements Serializable {
    private static final long serialVersionUID = 1L;
    private int idenvioNeumatico ;
    private String idCapsula;
    private String folioSurtimiento;
    private String folioPrescripcion;
    private String usuario;
    private Date fechaHoraSalida;
    private String estacionSalida;
    private Date fechaHoraLlegada;
    private String estacionLlegada;
    private String nombreCap;

    public EnvioNeumatico() {
    }

    public EnvioNeumatico(int idenvioNeumatico) {
        this.idenvioNeumatico = idenvioNeumatico;
    }

    @Override
    public String toString() {
        return "EnvioNeumatico{" + "idenvioNeumatico=" + idenvioNeumatico + ", idCapsula=" + idCapsula + ", folioSurtimiento=" + folioSurtimiento + ", folioPrescripcion=" + folioPrescripcion + ", usuario=" + usuario + ", fechaHoraSalida=" + fechaHoraSalida + ", estacionSalida=" + estacionSalida + ", fechaHoraLlegada=" + fechaHoraLlegada + ", estacionLlegada=" + estacionLlegada + ", nombreCap=" + nombreCap + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + this.idenvioNeumatico;
        hash = 53 * hash + Objects.hashCode(this.idCapsula);
        hash = 53 * hash + Objects.hashCode(this.folioSurtimiento);
        hash = 53 * hash + Objects.hashCode(this.folioPrescripcion);
        hash = 53 * hash + Objects.hashCode(this.usuario);
        hash = 53 * hash + Objects.hashCode(this.fechaHoraSalida);
        hash = 53 * hash + Objects.hashCode(this.estacionSalida);
        hash = 53 * hash + Objects.hashCode(this.fechaHoraLlegada);
        hash = 53 * hash + Objects.hashCode(this.estacionLlegada);
        hash = 53 * hash + Objects.hashCode(this.nombreCap);
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
        final EnvioNeumatico other = (EnvioNeumatico) obj;
        if (this.idenvioNeumatico != other.idenvioNeumatico) {
            return false;
        }
        if (!Objects.equals(this.idCapsula, other.idCapsula)) {
            return false;
        }
        if (!Objects.equals(this.folioSurtimiento, other.folioSurtimiento)) {
            return false;
        }
        if (!Objects.equals(this.folioPrescripcion, other.folioPrescripcion)) {
            return false;
        }
        if (!Objects.equals(this.usuario, other.usuario)) {
            return false;
        }
        if (!Objects.equals(this.fechaHoraSalida, other.fechaHoraSalida)) {
            return false;
        }
        if (!Objects.equals(this.estacionSalida, other.estacionSalida)) {
            return false;
        }
        if (!Objects.equals(this.fechaHoraLlegada, other.fechaHoraLlegada)) {
            return false;
        }
        if (!Objects.equals(this.estacionLlegada, other.estacionLlegada)) {
            return false;
        }
        return Objects.equals(this.nombreCap, other.nombreCap);
    }

    
    public int getIdenvioNeumatico() {
        return idenvioNeumatico;
    }

    public void setIdenvioNeumatico(int idenvioNeumatico) {
        this.idenvioNeumatico = idenvioNeumatico;
    }

    public String getIdCapsula() {
        return idCapsula;
    }

    public void setIdCapsula(String idCapsula) {
        this.idCapsula = idCapsula;
    }

    public String getFolioSurtimiento() {
        return folioSurtimiento;
    }

    public void setFolioSurtimiento(String folioSurtimiento) {
        this.folioSurtimiento = folioSurtimiento;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Date getFechaHoraSalida() {
        return fechaHoraSalida;
    }

    public void setFechaHoraSalida(Date fechaHoraSalida) {
        this.fechaHoraSalida = fechaHoraSalida;
    }

    public String getEstacionSalida() {
        return estacionSalida;
    }

    public void setEstacionSalida(String estacionSalida) {
        this.estacionSalida = estacionSalida;
    }

    public Date getFechaHoraLlegada() {
        return fechaHoraLlegada;
    }

    public void setFechaHoraLlegada(Date fechaHoraLlegada) {
        this.fechaHoraLlegada = fechaHoraLlegada;
    }

    public String getEstacionLlegada() {
        return estacionLlegada;
    }

    public void setEstacionLlegada(String estacionLlegada) {
        this.estacionLlegada = estacionLlegada;
    }

    public String getFolioPrescripcion() {
        return folioPrescripcion;
    }

    public void setFolioPrescripcion(String folioPrescripcion) {
        this.folioPrescripcion = folioPrescripcion;
    }

    public String getNombreCap() {
        return nombreCap;
    }

    public void setNombreCap(String nombreCap) {
        this.nombreCap = nombreCap;
    }

    
   
}
