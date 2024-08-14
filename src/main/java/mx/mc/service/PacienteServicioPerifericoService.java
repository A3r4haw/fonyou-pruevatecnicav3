package mx.mc.service;

import mx.mc.model.PacienteServicioPeriferico;


/**
 *
 * @author mcalderon
 */
public interface PacienteServicioPerifericoService extends GenericCrudService<PacienteServicioPeriferico, String> {
    
    public boolean insertarPacienteServicioPeriferico(
            PacienteServicioPeriferico pacienteServicioPeriferico            
    )throws Exception;    
    
    public boolean removerServicioPeriferico(PacienteServicioPeriferico removerServicioPeriferico) throws Exception;

    public PacienteServicioPeriferico obtenerDatos(PacienteServicioPeriferico pacienteServicioPeriferico)throws Exception;
    
    
}
