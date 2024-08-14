/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.io.Serializable;

/**
 *
 * @author mcalderon
 */
public class PermisoUsuario implements Serializable{

    private static final long serialVersionUID = 1L;
    private boolean puedeCrear;
    private boolean puedeVer;
    private boolean puedeEditar;
    private boolean puedeEliminar;
    private boolean puedeProcesar;
    private boolean puedeAutorizar;
    
    public boolean isPuedeCrear() {
        return puedeCrear;
    }

    public void setPuedeCrear(boolean puedeCrear) {
        this.puedeCrear = puedeCrear;
    }

    public boolean isPuedeVer() {
        return puedeVer;
    }

    public void setPuedeVer(boolean puedeVer) {
        this.puedeVer = puedeVer;
    }

    public boolean isPuedeEditar() {
        return puedeEditar;
    }

    public void setPuedeEditar(boolean puedeEditar) {
        this.puedeEditar = puedeEditar;
    }

    public boolean isPuedeEliminar() {
        return puedeEliminar;
    }

    public void setPuedeEliminar(boolean puedeEliminar) {
        this.puedeEliminar = puedeEliminar;
    }

    public boolean isPuedeProcesar() {
        return puedeProcesar;
    }

    public void setPuedeProcesar(boolean puedeProcesar) {
        this.puedeProcesar = puedeProcesar;
    }

    public boolean isPuedeAutorizar() {
        return puedeAutorizar;
    }

    public void setPuedeAutorizar(boolean puedeAutorizar) {
        this.puedeAutorizar = puedeAutorizar;
    }
    
    
}
