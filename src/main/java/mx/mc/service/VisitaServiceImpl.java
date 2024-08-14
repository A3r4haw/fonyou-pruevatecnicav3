package mx.mc.service;

import mx.mc.mapper.CamaMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.PacienteMapper;
import mx.mc.mapper.PacienteServicioMapper;
import mx.mc.mapper.PacienteUbicacionMapper;
import mx.mc.mapper.VisitaMapper;
import mx.mc.model.Paciente;
import mx.mc.model.PacienteServicio;
import mx.mc.model.PacienteUbicacion;
import mx.mc.model.Visita;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/*
 * @author hramirez
 */
@Service
public class VisitaServiceImpl extends GenericCrudServiceImpl<Visita, String> implements VisitaService {
    
    @Autowired
    private VisitaMapper visitaMapper;
    
    @Autowired
    private PacienteServicioMapper pacienteServicioMapper;
    
    @Autowired
    private PacienteMapper pacienteMapper;
    
    @Autowired
    private CamaMapper camaMapper;
    
    @Autowired
    private PacienteUbicacionMapper pacienteUbicacionMapper;

    @Autowired
    public VisitaServiceImpl(GenericCrudMapper<Visita, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean registrarVisitaPaciente(Visita visita , 
            PacienteServicio pacienteServicio , Paciente paciente) throws Exception {
        boolean resp = false;
        try {
            resp = visitaMapper.insertar(visita);
            if (resp) {
                resp = pacienteServicioMapper.insertar(pacienteServicio);
            }
            if (resp) {
                resp = pacienteMapper.actualizar(paciente);
            }
        } catch (Exception ex) {
            throw new Exception("Error al obtener la busqueda" + ex.getMessage());
        }
        return resp;
    }

    @Override
    public Visita obtenerVisitaAbierta(Visita visita) throws Exception {
        Visita visitaAbierta = null;
        try {
            visitaAbierta = visitaMapper.obtenerVisitaAbierta(visita);
        } catch (Exception ex) {
            throw new Exception("Error al obtener la visita" + ex.getMessage());
        }
        return visitaAbierta;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean cerrarVisitaYServicio(
            Visita visita, PacienteServicio pacienteServicio  , 
            Paciente paciente , Integer idEstatusCama , PacienteUbicacion pacienteUbicacion) throws Exception {
        boolean resp = true;
        try {
            pacienteServicioMapper.actualizar(pacienteServicio);
            pacienteMapper.actualizar(paciente);
            camaMapper.actualizarCamaLiberada(
                    pacienteServicio.getIdPacienteServicio(), idEstatusCama);
            pacienteUbicacionMapper.actualizarPacienteUbicacion(pacienteUbicacion);
            
        } catch (Exception ex) {
            throw new Exception("Error al obtener la busqueda" + ex.getMessage());
        }
        return resp;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean insertarVisitaYServicioGenericos(Visita v, PacienteServicio ps) throws Exception {
        boolean resp;
        try {
            resp = visitaMapper.insertar(v);
            if (!resp){
                throw new Exception("Error al registrar visita. " );
            } else {
                resp = pacienteServicioMapper.insertar(ps);
                if (!resp){
                    throw new Exception("Error al registrar servicio. " );
                }
            }
        } catch (Exception ex) {
            throw new Exception("Error al registrar Visita y Servicio. " + ex.getMessage());
         }
        return resp;
    }
    
    public Visita obtenerVisitaPorNumero(String numeroVisita) throws Exception {
        try {
            return visitaMapper.obtenerVisitaPorNumero(numeroVisita);
        } catch(Exception ex) {
            throw new Exception("Error al obtenerVisitaPorNumero. " + ex.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override    
    public boolean insertarVisitaServicioUbicacinoGenericos(Visita v, PacienteServicio ps, PacienteUbicacion pu) throws Exception{
        boolean resp;
        try {
            resp = visitaMapper.insertar(v);
            if (!resp){
                throw new Exception("Error al registrar visita. " );
            } else {
                resp = pacienteServicioMapper.insertar(ps);
                if (!resp){
                    throw new Exception("Error al registrar servicio. " );
                } else {
                    resp = pacienteUbicacionMapper.insertar(pu);
                    if (!resp){
                        throw new Exception("Error al registrar ubicacion. " );
                    }
                }
            }
        } catch (Exception ex) {
            throw new Exception("Error al registrar Visita y Servicio. " + ex.getMessage());
         }
        return resp;
    }

}
