package mx.mc.service;

import java.util.Date;
import mx.mc.mapper.CamaMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.PacienteMapper;
import mx.mc.mapper.PacienteUbicacionMapper;
import mx.mc.model.Cama;
import mx.mc.model.Paciente;
import mx.mc.model.PacienteUbicacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/*
 * @author hramirez
 */
@Service
public class PacienteUbicacionServiceImpl extends GenericCrudServiceImpl<PacienteUbicacion, String> implements PacienteUbicacionService {
    
    @Autowired
    private PacienteUbicacionMapper pacienteUbicacionMapper;
    
    @Autowired
    private CamaMapper camaMapper;
    
    @Autowired
    private PacienteMapper pacienteMapper;

    @Autowired
    public PacienteUbicacionServiceImpl(GenericCrudMapper<PacienteUbicacion, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Transactional (propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean asignarCamaPaciente(
            PacienteUbicacion pacienteUbicacion, Cama cama , Paciente paciente) throws Exception {
        boolean resp;
        try {
            resp = pacienteUbicacionMapper.insertar(pacienteUbicacion);
            if (resp) {
                camaMapper.actualizar(cama);
            }
            if (resp) {
                pacienteMapper.actualizar(paciente);
            }
        }catch (Exception ex) {
            throw new Exception("Error al asignar la cama." + ex.getMessage());
        }
        return resp;
    }
    
    @Transactional (propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean liberarCamaPaciente(PacienteUbicacion pacienteUbicacion , 
            Paciente paciente , Integer idEstatusCama) throws Exception {
        boolean resp;
        try {
            resp = camaMapper.actualizarCamaLiberada(pacienteUbicacion.getIdPacienteServicio(), 
                        idEstatusCama);
            if (resp) {
                pacienteUbicacionMapper.actualizarPacienteUbicacion(pacienteUbicacion);
            }
            if (resp) {
                pacienteMapper.actualizar(paciente);
            }
        }catch (Exception ex) {
            throw new Exception("Error al asignar la cama." + ex.getMessage());
        }
        return resp;
    }
    
    @Override
    public PacienteUbicacion obtenerCamaAsignada(PacienteUbicacion pu) throws Exception {
        try {
            return pacienteUbicacionMapper.obtenerCamaAsignada(pu);
        }catch (Exception ex) {
            throw new Exception("Error al buscar la cama asignada ultima." + ex.getMessage());
        }
    }
    
    @Override
    public boolean actualizarPacienteUbicacion(PacienteUbicacion pacienteUbicacion) throws Exception {
        try {
            return pacienteUbicacionMapper.actualizarPacienteUbicacion(pacienteUbicacion);
        }catch (Exception ex) {
            throw new Exception("Error al actualizarPacienteUbicacion de la cama asignada. " + ex.getMessage());
        }
    }
    
    @Override
    public boolean cierraAsigCamaAbiertas(Date fecha, String idUsuario, String idPaciente) throws Exception {
        try {
            return pacienteUbicacionMapper.cierraAsigCamaAbiertas(fecha, idUsuario, idPaciente);
        }catch (Exception ex) {
            throw new Exception("Error al cerrar las camasa asignadas con visita abierta. " + ex.getMessage());
        }
    }

}
