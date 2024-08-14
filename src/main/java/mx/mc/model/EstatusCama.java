package mx.mc.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * 
 * @author gcruz
 *
 */
public class EstatusCama implements Serializable{

    private static final long serialVersionUID = 1L;

    private int idEstatusCama;
    private String nombreEstatusCama;

    public EstatusCama() {

    }

    public EstatusCama(int idEstatusCama, String nombreEstatusCama) {
        this.idEstatusCama = idEstatusCama;
        this.nombreEstatusCama = nombreEstatusCama;
    }

    public int getIdEstatusCama() {
        return idEstatusCama;
    }

    public void setIdEstatusCama(int idEstatusCama) {
        this.idEstatusCama = idEstatusCama;
    }

    public String getNombreEstatusCama() {
        return nombreEstatusCama;
    }

    public void setNombreEstatusCama(String nombreEstatusCama) {
        this.nombreEstatusCama = nombreEstatusCama;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.idEstatusCama);
        hash = 89 * hash + Objects.hashCode(this.nombreEstatusCama);
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
        EstatusCama other = (EstatusCama) obj;
        if (idEstatusCama != other.idEstatusCama) {
            return false;
        }
        if (nombreEstatusCama == null) {
            if (other.nombreEstatusCama != null) {
                return false;
            }
        } else if (!nombreEstatusCama.equals(other.nombreEstatusCama)) {
            return false;
        }
        return true;
    }

	 
}
