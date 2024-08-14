/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.ws.movil.util;

import mx.mc.enums.Accion_Enum;
import mx.mc.model.PermisoUsuario;

/**
 *
 * @author apalacios
 */
public class Comunes {
    
    public static PermisoUsuario obtenPermiso(String accion, PermisoUsuario permisosUsuario) {
        switch (accion) {
            case "CREAR":
                permisosUsuario.setPuedeCrear(accion.equals(Accion_Enum.CREAR.getValue()));
                break;
            case "VER":
                permisosUsuario.setPuedeVer(accion.equals(Accion_Enum.VER.getValue()));
                break;
            case "EDITAR":
                permisosUsuario.setPuedeEditar(accion.equals(Accion_Enum.EDITAR.getValue()));
                break;
            case "ELIMINAR":
                permisosUsuario.setPuedeEliminar(accion.equals(Accion_Enum.ELIMINAR.getValue()));
                break;
            case "PROCESAR":
                permisosUsuario.setPuedeProcesar(accion.equals(Accion_Enum.PROCESAR.getValue()));
                break;
            case "AUTORIZAR":
                permisosUsuario.setPuedeAutorizar(accion.equals(Accion_Enum.AUTORIZAR.getValue()));
                break;
            default:
        }
        return permisosUsuario;
    }
}
