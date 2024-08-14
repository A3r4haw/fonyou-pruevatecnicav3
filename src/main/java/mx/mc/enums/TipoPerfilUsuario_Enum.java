/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.enums;

/**
 *
 * @author gcruz
 */
public enum TipoPerfilUsuario_Enum {
    ADMIN(1,"Administrador"),
    JEFE_AREA(2,"Jefe de Area")
    ;
    
    private final Integer idTipoPerfil;
    private final String nombrePerfil;
    
    private TipoPerfilUsuario_Enum(Integer idTipoPerfil, String nombrePerfil) {
        this.idTipoPerfil = idTipoPerfil;
        this.nombrePerfil = nombrePerfil;
    }

    public Integer getIdTipoPerfil() {
        return idTipoPerfil;
    }

    public String getNombrePerfil() {
        return nombrePerfil;
    }
    
    
}
