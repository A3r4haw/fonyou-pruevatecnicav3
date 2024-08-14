/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.util;

import java.util.Date;
import mx.mc.enums.MotivoPacienteMovimiento_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.Paciente;
import mx.mc.model.Visita;

/**
 *
 * @author mcalderon
 */
public class VisitaUtil {
    
    public static Visita crearVisita(Paciente patient) {
        Visita visitaGenerica = new Visita();
        visitaGenerica.setIdVisita(Comunes.getUUID());
        visitaGenerica.setIdPaciente(patient.getIdPaciente());        
        visitaGenerica.setFechaIngreso(new Date());
        visitaGenerica.setIdUsuarioIngresa(patient.getInsertIdUsuario());        
        visitaGenerica.setIdMotivoPacienteMovimiento(MotivoPacienteMovimiento_Enum.ADMISION.getValue());        
        visitaGenerica.setMotivoConsulta(Constantes.TXT_VACIO);
        visitaGenerica.setInsertFecha(new Date());
        visitaGenerica.setInsertIdUsuario(patient.getInsertIdUsuario());
        return visitaGenerica;
        
        //Igual que las demas
        /*fechaEgreso
        idUsuarioCierra
        tipoVisita
        numVisita
        updateFecha
        updateIdUsuario
         */
    }
    
}
