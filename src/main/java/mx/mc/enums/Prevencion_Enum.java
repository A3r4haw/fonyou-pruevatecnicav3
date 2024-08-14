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
public enum Prevencion_Enum {
    NOTOMARFARMACO("No tomar fármaco", 1),
    FILTROSPARTICULA("Filtros de partículas de alta eficiencia", 2),
    NOCOMERALIMENTO("No comer un alimento en particular", 3),
    MUDARSEUNAZONA("Mudarse a una zona en la que no exista el alérgeno", 4),
    ELIMINARELEMENTOS("Eliminar elementos que acumulen polvo", 5),
    CUBRIRCOLCHONESALMOHADAS("Cubrir los colchones y las almohadas", 6),
    UTILIZARALMOHADAS("Utilizar almohadas de fibra sintética", 7),
    LAVARROPA("Lavar frecuentemente ropa de cama en agua caliente", 8),
    LIMPIARCASA("Limpiar la casa a menudo, incluso quitar el pc", 9),
    APARATOSAIREACONDISIONADO("Usar aparatos de aire acondicionado y deshumidificador", 10),
    APLICARVAPORCALIENTE("Aplicar vapor caliente en el hogar", 11),
    EXTERMINARCUCARACHAS("Exterminar las cucarachas", 12);
    
    private final String nombrePrevencion;
    private final Integer valor;
    
    private Prevencion_Enum(String nombrePrevencion, Integer valor) {
        this.nombrePrevencion = nombrePrevencion;
        this.valor = valor;
    }

    public String getNombrePrevencion() {
        return nombrePrevencion;
    }

    public Integer getValor() {
        return valor;
    }
}
