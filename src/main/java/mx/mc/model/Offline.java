/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.json.bind.annotation.JsonbProperty;

/**
 *
 * @author Ulai
 */
public class Offline implements Serializable {
        private static final long serialVersionUID = 1L;
        private Collection<SurtimientoInsumo_Extend> surtimientoInsumoExtendedList;
        private boolean eliminaCodigoBarras;
        @JsonbProperty("codigoBarras")
        private List<String> codigoBarras;
        private Surtimiento_Extend surtimientoExtend;
        private List<CantidadOffline> cantidad;
        private List<CantidadOffline_Extended> codigo;
        private VistaRecepcionMedicamento vistaRecepcionMedicamento;
        
    public Collection<SurtimientoInsumo_Extend> getSurtimientoInsumoExtendedList() {
        return surtimientoInsumoExtendedList;
    }

    public void setSurtimientoInsumoExtendedList(Collection<SurtimientoInsumo_Extend> surtimientoInsumoExtendedList) {
        this.surtimientoInsumoExtendedList = surtimientoInsumoExtendedList;
    }

    public boolean isEliminaCodigoBarras() {
        return eliminaCodigoBarras;
    }

    public void setEliminaCodigoBarras(boolean eliminaCodigoBarras) {
        this.eliminaCodigoBarras = eliminaCodigoBarras;
    }

    public List<String> getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(List<String> codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public Surtimiento_Extend getSurtimientoExtend() {
        return surtimientoExtend;
    }

    public void setSurtimientoExtend(Surtimiento_Extend surtimientoExtend) {
        this.surtimientoExtend = surtimientoExtend;
    }

    public List<CantidadOffline> getCantidad() {
        return cantidad;
    }

    public void setCantidad(List<CantidadOffline> cantidad) {
        this.cantidad = cantidad;
    }

    public VistaRecepcionMedicamento getVistaRecepcionMedicamento() {
        return vistaRecepcionMedicamento;
    }

    public void setVistaRecepcionMedicamento(VistaRecepcionMedicamento vistaRecepcionMedicamento) {
        this.vistaRecepcionMedicamento = vistaRecepcionMedicamento;
    }

    public List<CantidadOffline_Extended> getCodigo() {
        return codigo;
    }

    public void setCodigo(List<CantidadOffline_Extended> codigo) {
        this.codigo = codigo;
    }
    
    
    
   
}
