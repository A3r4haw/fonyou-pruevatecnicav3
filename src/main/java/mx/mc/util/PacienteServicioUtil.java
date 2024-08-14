/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.util;

import java.util.Date;
import mx.mc.enums.MotivoPacienteMovimiento_Enum;
import mx.mc.model.Paciente;
import mx.mc.model.PacienteServicio;
import mx.mc.model.Visita;

/**
 *
 * @author mcalderon
 */
public class PacienteServicioUtil {
    
    // Método con Objetos necesarios a nivel BD.
    public static PacienteServicio crearPacienteServicio( Paciente patient, Visita visita){        
        PacienteServicio pacienteServicioGenerico = new PacienteServicio();
        
        pacienteServicioGenerico.setIdPacienteServicio(Comunes.getUUID());
        pacienteServicioGenerico.setIdVisita(visita.getIdVisita());                
        pacienteServicioGenerico.setIdEstructura(patient.getIdEstructura());
        pacienteServicioGenerico.setFechaAsignacionInicio(new Date());
        pacienteServicioGenerico.setIdUsuarioAsignacionInicio(patient.getInsertIdUsuario());
        pacienteServicioGenerico.setIdMotivoPacienteMovimiento(MotivoPacienteMovimiento_Enum.ADMISION.getValue());
        pacienteServicioGenerico.setInsertFecha(new Date());
        pacienteServicioGenerico.setInsertIdUsuario(patient.getInsertIdUsuario());
        return pacienteServicioGenerico;
        
//      Estos datos como tal pueden variar por ello aun no se han agregado
/*      idUsuarioAsignacionInicio
        fechaAsignacionFin
        idUsuarioAsignacionFin
        idMedico
        justificacion
        updateFecha
        updateIdUsuario
        numTransfer
*/
    }
    
}
