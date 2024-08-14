/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author bbautista
 */
public class ReabastoXServicio implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private Reabasto reabasto;
    private List<ReabastoInsumo> insumos;

    public Reabasto getReabasto() {
        return reabasto;
    }

    public void setReabasto(Reabasto reabasto) {
        this.reabasto = reabasto;
    }

    public List<ReabastoInsumo> getInsumos() {
        return insumos;
    }

    public void setInsumos(List<ReabastoInsumo> insumos) {
        this.insumos = insumos;
    }

}
