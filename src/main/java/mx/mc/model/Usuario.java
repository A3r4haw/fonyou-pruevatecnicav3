package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author hramirez
 */
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idUsuario;
    private String nombreUsuario;
    private String claveAcceso;
    private String correoElectronico;

    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;

    private boolean activo;
    private boolean usuarioBloqueado;
    private boolean actualizarClave;
    
    private Date fechaVigencia;
    private Date ultimoIngreso;
    
    private Integer idTipoUsuario;
    private String cedProfesional;
    private String cedEspecialidad;

    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;

    private String idEstructura;
    private String pathEstructura;
    private Integer administrador;
    private Integer idTipoPerfil; 
    private String matriculaPersonal;

    private List<TransaccionPermisos> permisosList;
    private Boolean prescribeControlados;
    
    private Integer numErrorAcceso;

    private String idTipoSolucion;


    public Usuario() {
        //No code needed in constructor
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.idUsuario);
        hash = 89 * hash + Objects.hashCode(this.nombreUsuario);
        hash = 89 * hash + Objects.hashCode(this.claveAcceso);
        hash = 89 * hash + Objects.hashCode(this.correoElectronico);
        hash = 89 * hash + Objects.hashCode(this.nombre);
        hash = 89 * hash + Objects.hashCode(this.apellidoPaterno);
        hash = 89 * hash + Objects.hashCode(this.apellidoMaterno);
        hash = 89 * hash + (this.activo ? 1 : 0);
        hash = 89 * hash + (this.usuarioBloqueado ? 1 : 0);
        hash = 89 * hash + (this.actualizarClave ? 1 : 0);
        hash = 89 * hash + Objects.hashCode(this.fechaVigencia);
        hash = 89 * hash + Objects.hashCode(this.ultimoIngreso);
        hash = 89 * hash + Objects.hashCode(this.idTipoUsuario);
        hash = 89 * hash + Objects.hashCode(this.cedProfesional);
        hash = 89 * hash + Objects.hashCode(this.cedEspecialidad);
        hash = 89 * hash + Objects.hashCode(this.insertFecha);
        hash = 89 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 89 * hash + Objects.hashCode(this.updateFecha);
        hash = 89 * hash + Objects.hashCode(this.updateIdUsuario);
        hash = 89 * hash + Objects.hashCode(this.idEstructura);
        hash = 89 * hash + Objects.hashCode(this.pathEstructura);
        hash = 89 * hash + Objects.hashCode(this.permisosList);
        hash = 89 * hash + Objects.hashCode(this.administrador);
        hash = 89 * hash + Objects.hashCode(this.idTipoPerfil);
        hash = 89 * hash + Objects.hashCode(this.matriculaPersonal);
        hash = 89 * hash + Objects.hashCode(this.numErrorAcceso);
        hash = 89 * hash + Objects.hashCode(this.idTipoSolucion);
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
        final Usuario other = (Usuario) obj;
        if (this.activo != other.activo) {
            return false;
        }
        if (this.usuarioBloqueado != other.usuarioBloqueado) {
            return false;
        }
        if (this.actualizarClave != other.actualizarClave) {
            return false;
        }
        if (!Objects.equals(this.idUsuario, other.idUsuario)) {
            return false;
        }
        if (!Objects.equals(this.nombreUsuario, other.nombreUsuario)) {
            return false;
        }
        if (!Objects.equals(this.claveAcceso, other.claveAcceso)) {
            return false;
        }
        if (!Objects.equals(this.correoElectronico, other.correoElectronico)) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.apellidoPaterno, other.apellidoPaterno)) {
            return false;
        }
        if (!Objects.equals(this.apellidoMaterno, other.apellidoMaterno)) {
            return false;
        }
        if (!Objects.equals(this.cedProfesional, other.cedProfesional)) {
            return false;
        }
        if (!Objects.equals(this.cedEspecialidad, other.cedEspecialidad)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.idEstructura, other.idEstructura)) {
            return false;
        }
        if (!Objects.equals(this.pathEstructura, other.pathEstructura)) {
            return false;
        }
        if (!Objects.equals(this.fechaVigencia, other.fechaVigencia)) {
            return false;
        }
        if (!Objects.equals(this.ultimoIngreso, other.ultimoIngreso)) {
            return false;
        }
        if (!Objects.equals(this.idTipoUsuario, other.idTipoUsuario)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        if (!Objects.equals(this.updateFecha, other.updateFecha)) {
            return false;
        }
        if (!Objects.equals(this.permisosList, other.permisosList)) {
            return false;
        }
        if (!Objects.equals(this.administrador, other.administrador)) {
            return false;
        }
        if (!Objects.equals(this.idTipoPerfil, other.idTipoPerfil)) {
            return false;
        }
        if (!Objects.equals(this.numErrorAcceso, other.numErrorAcceso)) {
            return false;
        }
        if (!Objects.equals(this.idTipoSolucion, other.idTipoSolucion)) {
            return false;
        }
        return Objects.equals(this.matriculaPersonal, other.matriculaPersonal);
    }

    @Override
    public String toString() {
        return "Usuario{" + "idUsuario=" + idUsuario + ", nombreUsuario=" + nombreUsuario + ", claveAcceso=" + claveAcceso + ", correoElectronico=" + correoElectronico + ", nombre=" + nombre + ", apellidoPaterno=" + apellidoPaterno + ", apellidoMaterno=" + apellidoMaterno + ", activo=" + activo + ", usuarioBloqueado=" + usuarioBloqueado + ", actualizarClave=" + actualizarClave + ", fechaVigencia=" + fechaVigencia + ", ultimoIngreso=" + ultimoIngreso + ", idTipoUsuario=" + idTipoUsuario + ", cedProfesional=" + cedProfesional + ", cedEspecialidad=" + cedEspecialidad + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + ", idEstructura=" + idEstructura + ", pathEstructura=" + pathEstructura + ", permisosList=" + permisosList + ", administrador=" + administrador + ", idTipoPerfil=" + idTipoPerfil + ",matriculaPersonal =" + matriculaPersonal + ", numErrorAcceso = " + numErrorAcceso + ", idTipoSolucion= " + idTipoSolucion + '}';
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getClaveAcceso() {
        return claveAcceso;
    }

    public void setClaveAcceso(String claveAcceso) {
        this.claveAcceso = claveAcceso;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public boolean isUsuarioBloqueado() {
        return usuarioBloqueado;
    }

    public void setUsuarioBloqueado(boolean usuarioBloqueado) {
        this.usuarioBloqueado = usuarioBloqueado;
    }

    public boolean isActualizarClave() {
        return actualizarClave;
    }

    public void setActualizarClave(boolean actualizarClave) {
        this.actualizarClave = actualizarClave;
    }

    public Date getFechaVigencia() {
        return fechaVigencia;
    }

    public void setFechaVigencia(Date fechaVigencia) {
        this.fechaVigencia = fechaVigencia;
    }

    public Date getUltimoIngreso() {
        return ultimoIngreso;
    }

    public void setUltimoIngreso(Date ultimoIngreso) {
        this.ultimoIngreso = ultimoIngreso;
    }

    public Integer getIdTipoUsuario() {
        return idTipoUsuario;
    }

    public void setIdTipoUsuario(Integer idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
    }

    public String getCedProfesional() {
        return cedProfesional;
    }

    public void setCedProfesional(String cedProfesional) {
        this.cedProfesional = cedProfesional;
    }

    public String getCedEspecialidad() {
        return cedEspecialidad;
    }

    public void setCedEspecialidad(String cedEspecialidad) {
        this.cedEspecialidad = cedEspecialidad;
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

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public String getPathEstructura() {
        return pathEstructura;
    }

    public void setPathEstructura(String pathEstructura) {
        this.pathEstructura = pathEstructura;
    }

    public List<TransaccionPermisos> getPermisosList() {
        return permisosList;
    }

    public void setPermisosList(List<TransaccionPermisos> permisosList) {
        this.permisosList = permisosList;
    }

    public Boolean getPrescribeControlados() {
        return prescribeControlados;
    }

    public void setPrescribeControlados(Boolean prescribeControlados) {
        this.prescribeControlados = prescribeControlados;
    }

    public Integer getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Integer administrador) {
        this.administrador = administrador;
    }

    public Integer getIdTipoPerfil() {
        return idTipoPerfil;
    }

    public void setIdTipoPerfil(Integer idTipoPerfil) {
        this.idTipoPerfil = idTipoPerfil;
    }

    public String getMatriculaPersonal() {
        return matriculaPersonal;
    }

    public void setMatriculaPersonal(String matriculaPersonal) {
        this.matriculaPersonal = matriculaPersonal;
    }

    public Integer getNumErrorAcceso() {
        return numErrorAcceso;
    }

    public void setNumErrorAcceso(Integer numErrorAcceso) {
        this.numErrorAcceso = numErrorAcceso;
    }
    
     public String getIdTipoSolucion() {
        return idTipoSolucion;
    }
    
    public void setIdTipoSolucion(String idTipoSolucion) {
        this.idTipoSolucion = idTipoSolucion;
    }
    
}
