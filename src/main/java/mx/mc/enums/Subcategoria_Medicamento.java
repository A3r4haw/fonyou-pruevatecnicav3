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
public enum Subcategoria_Medicamento {
    ANALGESIA(1),
    ANESTESIA(2),
    CARDIOLOGIA(3),
    DERMATOLOGIA(4),
    ENDO_METABOLISMO(5),
    EIP(6),
    EI(7),
    GASTROENTEROLOGIA(8),
    GINECOOBSTETRICIA(9),
    HEMATOLOGIA(10),
    INTOXICACIONES(11),
    NEFROLOGIAUROLOGIA(12),
    NEUMOLOGIA(13),
    NEUROLOGIA(14),
    OFTALMOLOGIA(15),
    ONCOLOGIA(16),
    OTORRINOLARINGOLOGIA(17),
    PLANIFICACIONFAMILIAR(18),
    PSIQUIATRIA(19),
    RT(20),
    SOLUCIONESPLASMA(21),
    VACUNAS(22),
    CONTROLADO_GRUPO_I(23),
    CONTROLADO_GRUPO_II(24),
    CONTROLADO_GRUPO_III(25),
    NO_DEFINIDO(26)
    ;
    
    private final int value;

    private Subcategoria_Medicamento(int value) {
        this.value = value;
    }        

    public int getValue() {
        return value;
    }
        
}
