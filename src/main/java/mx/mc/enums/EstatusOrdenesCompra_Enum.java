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
public enum EstatusOrdenesCompra_Enum {
    
    REGISTRADA("Registrada", 1),
    ENTREGADA("Entregada", 2),
    TRANSITO("En Transito", 3),
    CANCELADA("Cancelada", 4);
    
    
    
    private final String nombreEstatus;
    private final Integer idEstatusOrdenCompra;
    
    private EstatusOrdenesCompra_Enum(String nombreEstatus, Integer idEstatusOrdenCompra) {
        this.nombreEstatus = nombreEstatus;
        this.idEstatusOrdenCompra = idEstatusOrdenCompra;
    }
    
    public String getNombreEstatus() {
        return this.nombreEstatus;
    }
    
    public Integer getIdEstatusOrdenCompra() {
        return this.idEstatusOrdenCompra;
    }
}
