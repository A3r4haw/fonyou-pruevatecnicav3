package mx.mc.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UsuarioExtended extends User {

    private static final long serialVersionUID = 1;

    private Usuario usuario;

    private HashMap<String, List<String>> permisosPorModulos;
    private List<String> listaModulos;
    private HashMap<String, List<String>> accionesPorTransaccion;

    public UsuarioExtended(
            String username
            , String password
            , boolean enabled
            , boolean accountNonExpired
            , boolean credentialsNonExpired
            , boolean accountNonLocked
            , Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired,
                accountNonLocked, authorities);
        
        this.usuario = usuario;
        this.permisosPorModulos = permisosPorModulos;
        this.listaModulos = listaModulos;
        this.accionesPorTransaccion = accionesPorTransaccion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public HashMap<String, List<String>> getPermisosPorModulos() {
        return permisosPorModulos;
    }

    public void setPermisosPorModulos(HashMap<String, List<String>> permisosPorModulos) {
        this.permisosPorModulos = permisosPorModulos;
    }

    public List<String> getListaModulos() {
        return listaModulos;
    }

    public void setListaModulos(List<String> listaModulos) {
        this.listaModulos = listaModulos;
    }

    public HashMap<String, List<String>> getAccionesPorTransaccion() {
        return accionesPorTransaccion;
    }

    public void setAccionesPorTransaccion(HashMap<String, List<String>> accionesPorTransaccion) {
        this.accionesPorTransaccion = accionesPorTransaccion;
    }

    @Override
    public String toString() {
        return "UsuarioExtended{" + "usuario=" + usuario + ", permisosPorModulos=" + permisosPorModulos + ", listaModulos=" + listaModulos + ", accionesPorTransaccion=" + accionesPorTransaccion + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.usuario);
        hash = 47 * hash + Objects.hashCode(this.permisosPorModulos);
        hash = 47 * hash + Objects.hashCode(this.listaModulos);
        hash = 47 * hash + Objects.hashCode(this.accionesPorTransaccion);
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
        final UsuarioExtended other = (UsuarioExtended) obj;
        if (!Objects.equals(this.usuario, other.usuario)) {
            return false;
        }
        if (!Objects.equals(this.permisosPorModulos, other.permisosPorModulos)) {
            return false;
        }
        if (!Objects.equals(this.listaModulos, other.listaModulos)) {
            return false;
        }
        return Objects.equals(this.accionesPorTransaccion, other.accionesPorTransaccion);
    }
}
