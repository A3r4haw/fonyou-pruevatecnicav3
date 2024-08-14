/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.util.List;

/**
 *
 * @author bbautista
 */
public class AlmacenControl_Extended extends AlmacenControl {

    private static final long serialVersionUID = 1L;
    private String motivo;
    private boolean list;
    private boolean process;
    private int total;
    private List<Medicamento> insumosList;

    @Override
    public String toString() {
        return "AlmacenControl_Extended{" + "motivo=" + motivo + ", list=" + list + ", process=" + process + ", insumosList=" + insumosList + '}';
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public boolean isList() {
        return list;
    }

    public void setList(boolean list) {
        this.list = list;
    }

    public boolean isProcess() {
        return process;
    }

    public void setProcess(boolean process) {
        this.process = process;
    }

    public List<Medicamento> getInsumosList() {
        return insumosList;
    }

    public void setInsumosList(List<Medicamento> insumosList) {
        this.insumosList = insumosList;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    
}
