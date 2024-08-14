/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.util;

import java.util.Date;
import mx.mc.init.Constantes;
import mx.mc.model.Paciente;
import mx.mc.model.PacienteDomicilio;

/**
 *
 * @author mcalderon
 */
public class PacienteDomicilioUtil {
    
        
    public static PacienteDomicilio crearPacienteDomicilioGenerico(Paciente paciente) {
        PacienteDomicilio pacienteDomicilio = new PacienteDomicilio();
        pacienteDomicilio.setIdPaciente(paciente.getIdPaciente());
        pacienteDomicilio.setIdPacienteDomicilio(Comunes.getUUID());
        pacienteDomicilio.setCalle(Constantes.NO_DEFINIDO);
        pacienteDomicilio.setNumeroExterior(Constantes.NO_DEFINIDO);
        pacienteDomicilio.setTelefonoCasa(Constantes.NO_DEFINIDO);
        pacienteDomicilio.setIdPais(Constantes.ID_PAIS_MEXICO);
        pacienteDomicilio.setInsertFecha(new Date());
        pacienteDomicilio.setInsertIdUsuario(paciente.getInsertIdUsuario());
        return pacienteDomicilio;
        
//  De igual manera, solo puedo agregar datos Genericos        
        /*
        idEstado
        idMunicipio
        idColonia
        codigoPostal
        numeroInterior
        telefonoOficina
        extension
        telefonoCelular
        correoElectronico
        cuentaFacebook
        updateFecha
        updateIdUsuario
        */
        
    }
    
    
}
