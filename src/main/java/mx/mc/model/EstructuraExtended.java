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
public class EstructuraExtended extends Estructura{
    private static final long serialVersionUID = 1L;
    
    private String nombrePadre;
    private String nodo;
    private String idNode;
    private String idNodeParent;
    private boolean selected;
    private List<TipoSurtimiento> tipoSurtimientoList;

    public String getNombrePadre() {
        return nombrePadre;
    }

    public void setNombrePadre(String nombrePadre) {
        this.nombrePadre = nombrePadre;
    }

    public String getNodo() {
        return nodo;
    }

    public void setNodo(String nodo) {
        this.nodo = nodo;
    }
        
    public String getIdNode() {
        return idNode;
    }

    public void setIdNode(String idNode) {
        this.idNode = idNode;
    }

    public String getIdNodeParent() {
        return idNodeParent;
    }

    public void setIdNodeParent(String idNodeParent) {
        this.idNodeParent = idNodeParent;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public List<TipoSurtimiento> getTipoSurtimientoList() {
        return tipoSurtimientoList;
    }

    public void setTipoSurtimientoList(List<TipoSurtimiento> tipoSurtimientoList) {
        this.tipoSurtimientoList = tipoSurtimientoList;
    }
    
    
    
}
