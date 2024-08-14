package mx.mc.service;

import mx.mc.model.PacienteDomicilio;

/**
 * @author AORTIZ
 */
public interface PacienteDomicilioService extends GenericCrudService<PacienteDomicilio, String>{
    public PacienteDomicilio obtenerPacienteDomicilioByIdPaciente(String idPaciente) throws Exception;
}
