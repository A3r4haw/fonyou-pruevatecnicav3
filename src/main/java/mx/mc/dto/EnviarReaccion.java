/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.dto;

import mx.mc.model.Reaccion;

/**
 *
 * @author bbautista
 */
public class EnviarReaccion {
    String token;
    Reaccion reaccion;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Reaccion getReaccion() {
        return reaccion;
    }

    public void setReaccion(Reaccion reaccion) {
        this.reaccion = reaccion;
    }
    
    
}
