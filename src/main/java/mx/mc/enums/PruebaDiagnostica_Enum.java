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
public enum PruebaDiagnostica_Enum {
    
    EVALUACIONMEDICA("Evaluación médica", 1),
    ANALISISSANGRE("Análisis de sangre", 2),                    
    PRUEBACUTANEA("Pruebas cutánea", 3),
    PRUEBALGE("Pruebas LGE", 4);
    
    private final String nombrePrueba;
    private final Integer valor;
    
    private PruebaDiagnostica_Enum(String nombrePrueba, Integer valor) {
        this.nombrePrueba = nombrePrueba;
        this.valor = valor;
    }

    public String getNombrePrueba() {
        return nombrePrueba;
    }

    public Integer getValor() {
        return valor;
    }
    
}
