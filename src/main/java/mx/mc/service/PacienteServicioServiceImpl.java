package mx.mc.service;

import java.util.Date;
import mx.mc.mapper.CamaMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.PacienteMapper;
import mx.mc.mapper.PacienteServicioMapper;
import mx.mc.mapper.PacienteUbicacionMapper;
import mx.mc.model.Paciente;
import mx.mc.model.PacienteServicio;
import mx.mc.model.PacienteUbicacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/*
 * @author hramirez
 */
@Service
public class PacienteServicioServiceImpl extends GenericCrudServiceImpl<PacienteServicio, String> implements PacienteServicioService {
    
    @Autowired
    private PacienteServicioMapper pacienteServicioMapper;
    
    @Autowired
    private PacienteMapper pacienteMapper;
    
    @Autowired
    private CamaMapper camaMapper;
    
    @Autowired
    private PacienteUbicacionMapper pacienteUbicacionMapper;

    @Autowired
    public PacienteServicioServiceImpl(GenericCrudMapper<PacienteServicio, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public PacienteServicio obtenerPacienteServicioAbierto(PacienteServicio pacienteServicio) throws Exception {
        PacienteServicio pacienteServicioAbierto = null;
        try {
            pacienteServicioAbierto = pacienteServicioMapper.
                    obtenerPacienteServicioAbierto(pacienteServicio);
        } catch (Exception ex) {
            throw new Exception("Error al obtener el registro" + ex.getMessage());
        }
        return pacienteServicioAbierto;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean cerrarServicioYasigarOtro(PacienteServicio pacienteServicio, 
            Paciente paciente , PacienteServicio nuevoServicio , 
            Integer idEstatusCama , PacienteUbicacion pacienteUbicacion) throws Exception {
        
        try {
            pacienteServicioMapper.actualizar(pacienteServicio);
            pacienteServicioMapper.insertar(nuevoServicio);
            pacienteMapper.actualizar(paciente);
            camaMapper.actualizarCamaLiberada(
            pacienteServicio.getIdPacienteServicio(), idEstatusCama);
            pacienteUbicacionMapper.actualizarPacienteUbicacion(pacienteUbicacion);
            
        } catch (Exception ex) {
            throw new Exception("Error en el metodo cerrarServicio" + ex.getMessage());
        }
        return true;
    }
 
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean registrarPacienteServicio(PacienteServicio pacienteServicio,
            Paciente paciente) throws Exception {
        boolean resp = false;
        try {
            resp = pacienteServicioMapper.insertar(pacienteServicio);

            if (resp) {
                resp = pacienteMapper.actualizar(paciente);
            }
        } catch (Exception ex) {
            throw new Exception("Error al registrar Paciente Servicio " + ex.getMessage());
        }
        return resp;
    }
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean cerrarAsignacionesporIdVisita(PacienteServicio pacienteServicio) throws Exception {
        boolean resp = false;
        try {
            resp = pacienteServicioMapper.cerrarAsignacionesporIdVisita(pacienteServicio);
        } catch (Exception ex) {
            throw new Exception("Error al cerrarAsignacionesporIdVisita " + ex.getMessage());
        }
        return resp;
    }
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean cierraAsignacionesAbiertas(
            Date fechaAsignacionFin
            , Integer idMotivoPacienteMovimiento
            , String idUsuarioAsignacionFin
            , Date updateFecha
            , String updateIdUsuario
            , String idPaciente) throws Exception {
        boolean resp = false;
        try {
            resp = pacienteServicioMapper.cierraAsignacionesAbiertas(fechaAsignacionFin, idMotivoPacienteMovimiento, idUsuarioAsignacionFin, updateFecha, updateIdUsuario, idPaciente);
        } catch (Exception ex) {
            throw new Exception("Error al cerrar Asignaciones de servicio abiertas " + ex.getMessage());
        }
        return resp;
    }
    
}
