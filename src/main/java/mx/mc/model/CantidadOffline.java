/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.io.Serializable;

/**
 *
 * @author Ulai
 */
public class CantidadOffline implements Serializable {
    private static final long serialVersionUID = 1L;
    
        private String idSurtimientoInsumo;
        private Integer cantidadSurtida;
        private Integer cantidadRecibido;

        public String getIdSurtimientoInsumo() {
            return idSurtimientoInsumo;
        }

        public void setIdSurtimientoInsumo(String idSurtimientoInsumo) {
            this.idSurtimientoInsumo = idSurtimientoInsumo;
        }

        public Integer getCantidadSurtida() {
            return cantidadSurtida;
        }

        public void setCantidadSurtida(Integer cantidadSurtida) {
            this.cantidadSurtida = cantidadSurtida;
        }

    public Integer getCantidadRecibido() {
        return cantidadRecibido;
    }

    public void setCantidadRecibido(Integer cantidadRecibido) {
        this.cantidadRecibido = cantidadRecibido;
    }
        
}
