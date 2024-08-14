package mx.mc.service;

import mx.mc.mapper.PacienteMapper;

import mx.mc.mapper.PacienteServicioPerifericoMapper;

import mx.mc.model.PacienteServicioPeriferico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/*
 * @author mcalderon
 */
@Service
public class PacienteServicioPerifericoServiceImpl extends GenericCrudServiceImpl<PacienteServicioPeriferico, String> implements PacienteServicioPerifericoService {
    
    @Autowired
    private PacienteServicioPerifericoMapper pacienteServicioPerifericoMapper;

    @Autowired
    private PacienteMapper pacienteMapper;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean insertarPacienteServicioPeriferico(PacienteServicioPeriferico pacienteServicioPeriferico) throws Exception {
        boolean resp = false;
        try {
            resp = pacienteServicioPerifericoMapper.insertar(pacienteServicioPeriferico);
            if (!resp) {
                throw new Exception("Error al registrar ServicioPeriferico. ");
            } else {
                resp = pacienteMapper.actualizarIdEstructuraPeriferico(pacienteServicioPeriferico);
                if (!resp) {
                    throw new Exception("Error al actualizar el idEstrcuturaPeriferico. ");
                }
            }
        } catch (Exception e) {
            throw new Exception("Error al InsertarPacienteServicioPeriferico" + e.getMessage());
        }
        return true;
    }    

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean removerServicioPeriferico(PacienteServicioPeriferico removerServicioPeriferico) throws Exception {
        boolean resp = false;
        try {
            resp = pacienteMapper.actualizarIdEstructuraPeriferico(removerServicioPeriferico);
            if(!resp){
                throw new  Exception("Error al actualizar y remover el idEstructuraPeriferico.");
            }else{
                resp = pacienteServicioPerifericoMapper.actualizar(removerServicioPeriferico);
                if(!resp){
                    throw new  Exception("Error al actualizar el PacienteServicioPeriférico");
                }
            }
        } catch (Exception e) {
            throw new Exception("Error al remover el ServicioPeriférico." + e.getMessage());
        }
        return resp;
    }


    @Override
    public PacienteServicioPeriferico obtenerDatos(PacienteServicioPeriferico pacienteServicioPeriferico) throws Exception {
        PacienteServicioPeriferico paci = null;        
        try {
            paci = pacienteServicioPerifericoMapper.obtenerDatos(pacienteServicioPeriferico);
        } catch (Exception e) {
            throw new Exception("Error al InsertarPacienteServicioPeriferico" + e.getMessage());
        }
        return paci;
    }
}
