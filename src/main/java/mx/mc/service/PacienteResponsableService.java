package mx.mc.service;

import mx.mc.model.PacienteResponsable;

/**
 * @author AORTIZ
 */
public interface PacienteResponsableService extends GenericCrudService<PacienteResponsable, String>{
    public PacienteResponsable obtenerPacienteResponsableByIdPaciente(String idPaciente) throws Exception;
}
