/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.ws.common.vo;

import java.util.List;
import mx.mc.model.Medicamento_Extended;

/**
 *
 * @author gcruz
 */
public class Response {
    private String codigo;
    private String mensaje;
    private boolean status;
    
    private List<Medicamento_Extended> lista;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Medicamento_Extended> getLista() {
        return lista;
    }

    public void setLista(List<Medicamento_Extended> lista) {
        this.lista = lista;
    }  
}
