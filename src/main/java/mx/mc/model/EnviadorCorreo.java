package mx.mc.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Cervanets
 */
public class EnviadorCorreo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String servidor;
    private String puerto;
    private String nombreUsuario;
    private String contrasena;
    private String protocolo;
    private String smptAuth;
    private String startTls;
    private String depura;

    public EnviadorCorreo() {
    }

    public EnviadorCorreo(String servidor, String puerto, String nombreUsuario, String contrasena, String protocolo, String smptAuth, String startTls, String depura) {
        this.servidor = servidor;
        this.puerto = puerto;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.protocolo = protocolo;
        this.smptAuth = smptAuth;
        this.startTls = startTls;
        this.depura = depura;
    }

    public String getServidor() {
        return servidor;
    }

    public void setServidor(String servidor) {
        this.servidor = servidor;
    }

    public String getPuerto() {
        return puerto;
    }

    public void setPuerto(String puerto) {
        this.puerto = puerto;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

    public String getSmptAuth() {
        return smptAuth;
    }

    public void setSmptAuth(String smptAuth) {
        this.smptAuth = smptAuth;
    }

    public String getStartTls() {
        return startTls;
    }

    public void setStartTls(String startTls) {
        this.startTls = startTls;
    }

    public String getDepura() {
        return depura;
    }

    public void setDepura(String depura) {
        this.depura = depura;
    }

    @Override
    public String toString() {
        return "EnviadorCorreo{" + "servidor=" + servidor + ", puerto=" + puerto + ", nombreUsuario=" + nombreUsuario + ", contrasena=" + contrasena + ", protocolo=" + protocolo + ", smptAuth=" + smptAuth + ", startTls=" + startTls + ", depura=" + depura + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.servidor);
        hash = 89 * hash + Objects.hashCode(this.puerto);
        hash = 89 * hash + Objects.hashCode(this.nombreUsuario);
        hash = 89 * hash + Objects.hashCode(this.contrasena);
        hash = 89 * hash + Objects.hashCode(this.protocolo);
        hash = 89 * hash + Objects.hashCode(this.smptAuth);
        hash = 89 * hash + Objects.hashCode(this.startTls);
        hash = 89 * hash + Objects.hashCode(this.depura);
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
        final EnviadorCorreo other = (EnviadorCorreo) obj;
        if (!Objects.equals(this.servidor, other.servidor)) {
            return false;
        }
        if (!Objects.equals(this.puerto, other.puerto)) {
            return false;
        }
        if (!Objects.equals(this.nombreUsuario, other.nombreUsuario)) {
            return false;
        }
        if (!Objects.equals(this.contrasena, other.contrasena)) {
            return false;
        }
        if (!Objects.equals(this.protocolo, other.protocolo)) {
            return false;
        }
        if (!Objects.equals(this.smptAuth, other.smptAuth)) {
            return false;
        }
        if (!Objects.equals(this.startTls, other.startTls)) {
            return false;
        }
        return Objects.equals(this.depura, other.depura);
    }

}
