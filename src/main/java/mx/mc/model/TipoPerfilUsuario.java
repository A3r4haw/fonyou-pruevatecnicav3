/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author gcruz
 */
public class TipoPerfilUsuario implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer idTipoPerfilUsuario;
    private String nombrePerfil;
    
    public TipoPerfilUsuario() {
        
    }
    
    public TipoPerfilUsuario(Integer idTipoPerfilUsuario,String nombrePerfil) {
        this.idTipoPerfilUsuario = idTipoPerfilUsuario;
        this.nombrePerfil = nombrePerfil; 
    }

    @Override
    public String toString() {
        return "TipoPerfilUsuario{" + "idTipoPerfilUsuario=" + idTipoPerfilUsuario + ", nombrePerfil=" + nombrePerfil + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.idTipoPerfilUsuario);
        hash = 59 * hash + Objects.hashCode(this.nombrePerfil);
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
        final TipoPerfilUsuario other = (TipoPerfilUsuario) obj;
        if (!Objects.equals(this.nombrePerfil, other.nombrePerfil)) {
            return false;
        }
        return Objects.equals(this.idTipoPerfilUsuario, other.idTipoPerfilUsuario);
    }
    
    public Integer getIdTipoPerfilUsuario() {
        return idTipoPerfilUsuario;
    }

    public void setIdTipoPerfilUsuario(Integer idTipoPerfilUsuario) {
        this.idTipoPerfilUsuario = idTipoPerfilUsuario;
    }

    public String getNombrePerfil() {
        return nombrePerfil;
    }

    public void setNombrePerfil(String nombrePerfil) {
        this.nombrePerfil = nombrePerfil;
    }
    
}
