/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import mx.gob.issste.ws.ColectivoSiam;
import mx.gob.issste.ws.model.DerechoHabienteSiam;
import mx.gob.issste.ws.model.InsumoRecetaSiam;
import mx.gob.issste.ws.model.MedicoSiam;
import mx.mc.service.GenericCrudServiceImpl;
import org.springframework.stereotype.Service;
import mx.gob.issste.ws.model.RecetaSiam;
import mx.mc.enums.EstatusGabinete_Enum;
import mx.mc.enums.EstatusPrescripcion_Enum;
import mx.mc.enums.TipoDocumento_Enum;
import mx.mc.init.Constantes;
import mx.mc.mapper.EstructuraMapper;
import mx.mc.mapper.FoliosMapper;
import mx.mc.mapper.MedicamentoMapper;
import mx.mc.mapper.PacienteMapper;
import mx.mc.mapper.PacienteServicioMapper;
import mx.mc.mapper.PrescripcionInsumoMapper;
import mx.mc.mapper.PrescripcionMapper;
import mx.mc.mapper.SurtimientoInsumoMapper;
import mx.mc.mapper.SurtimientoMapper;
import mx.mc.mapper.UsuarioMapper;
import mx.mc.mapper.VisitaMapper;
import mx.mc.model.Estructura;
import mx.mc.model.Folios;
import mx.mc.model.Medicamento;
import mx.mc.model.Paciente;
import mx.mc.model.PacienteServicio;
import mx.mc.model.Prescripcion;
import mx.mc.model.PrescripcionInsumo;
import mx.mc.model.Prescripcion_Extended;
import mx.mc.model.Surtimiento;
import mx.mc.model.SurtimientoInsumo;
import mx.mc.model.Usuario;
import mx.mc.model.Visita;
import mx.mc.util.Comunes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Admin
 */
@Service
public class RecetaSiamServiceImpl extends GenericCrudServiceImpl<RecetaSiam, String> implements RecetaSiamService{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(RecetaSiamServiceImpl.class);
    @Autowired
    private UsuarioMapper usuarioMapper;
    @Autowired
    private PacienteMapper pacienteMapper;    
    @Autowired
    private VisitaMapper visitaMapper;    
    @Autowired
    private EstructuraMapper estructuraMapper;    
    @Autowired
    private PacienteServicioMapper pacienteServicioMapper;    
    @Autowired
    private PrescripcionMapper prescripcionMapper;    
    @Autowired
    private PrescripcionInsumoMapper prescripcionInsumoMapper;    
    @Autowired
    private SurtimientoMapper surtimientoMapper;
    @Autowired
    private SurtimientoInsumoMapper surtimientoInsumoMapper;    
    @Autowired
    private MedicamentoMapper medicamentoMapper;
    @Autowired
    private FoliosMapper foliosMapper;
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public String RegistrarRecetaSiam(RecetaSiam recetaSiam,Usuario usuarioSel) throws Exception {
        
        String folioMus="";
        boolean res = false;
        try {
            Date fechaSurtimiento = parseDate(recetaSiam.getFechaSurtimiento());
            // Buscar Estrucutura
            Estructura estructura = estructuraMapper.getEstructuraMedicoSIAM(recetaSiam.getMedico().getEspecialidad());
            if(estructura==null)
                estructura = estructuraMapper.getEstructuraMedicoSIAM("Consulta Externa");
            //Validar la existencia del paciente
            Paciente paciente = ObtenerPaciente(recetaSiam.getDerechoHabiente(),usuarioSel.getIdUsuario());
            
            // Validar Medico    
            Usuario medico = ObtenerMedico(recetaSiam.getMedico(),usuarioSel.getIdUsuario()); 
            
            // crear la visita            
            Visita visita = ObtenerVisita(recetaSiam.getDerechoHabiente().getNumIssste().toString()
                    ,recetaSiam.getId().toString(),paciente.getIdPaciente(),usuarioSel.getIdUsuario());

            String idPacienteServicio =  ObtenerIdPacienteServicio(visita.getIdVisita(),estructura.getIdEstructura(),medico.getIdUsuario(),usuarioSel.getIdUsuario());
            
            if(estructura != null){                            
            // crear Prescripcion
                Prescripcion prescripcion = new Prescripcion();
                prescripcion.setIdPrescripcion(Comunes.getUUID());
                prescripcion.setIdEstructura(estructura.getIdEstructura());
                prescripcion.setIdPacienteServicio(idPacienteServicio);
                prescripcion.setFolio(recetaSiam.getFolio());   
                prescripcion.setFechaPrescripcion(fechaSurtimiento);
                prescripcion.setTipoPrescripcion("N");
                prescripcion.setTipoConsulta("E");
                prescripcion.setIdMedico(medico.getIdUsuario());
                prescripcion.setRecurrente(Constantes.INACTIVO);
                prescripcion.setIdEstatusPrescripcion(EstatusPrescripcion_Enum.FINALIZADA.getValue());
                prescripcion.setIdEstatusGabinete(EstatusGabinete_Enum.OK.getValue());
                prescripcion.setInsertFecha(new Date());
                prescripcion.setInsertIdUsuario(usuarioSel.getIdUsuario());
                                
                List<PrescripcionInsumo> insumosList = new ArrayList<>();
                for(InsumoRecetaSiam item : recetaSiam.getInsumoRecetaList()){
                    Medicamento medi= medicamentoMapper.obtenerMedicaByClave(item.getClaveInsumo());
                    if(medi != null){
                        PrescripcionInsumo insumo = new PrescripcionInsumo();
                        insumo.setIdPrescripcionInsumo(Comunes.getUUID());
                        insumo.setIdPrescripcion(prescripcion.getIdPrescripcion());
                        insumo.setIdInsumo(medi.getIdMedicamento());
                        insumo.setFechaInicio(new Date());
                        insumo.setDosis(medi.getConcentracion());
                        insumo.setFrecuencia(item.getCantidad());
                        insumo.setDuracion(1);
                        insumo.setIdEstatusPrescripcion(EstatusPrescripcion_Enum.PROCESANDO.getValue());
                        insumo.setInsertFecha(new Date());
                        insumo.setInsertIdUsuario(usuarioSel.getIdUsuario());
                        
                        insumosList.add(insumo);
                    }                
                }
                if(!insumosList.isEmpty()){
                    res= RegistrarPrescripcion(prescripcion, insumosList);                
                    if(res){                
                        // Obtener id Almacen
                        Estructura almacen = estructuraMapper.obtenerAlmacenQueSurtePorIdEstructura(estructura.getIdEstructura());
                        if(almacen == null)
                            almacen = estructuraMapper.obtenerAlmacenQueSurtePorIdEstructura(estructura.getIdEstructuraPadre());

                        // Crear surtimiento
                        Surtimiento surtimiento = new Surtimiento();
                        surtimiento.setIdSurtimiento(Comunes.getUUID());
                        surtimiento.setIdEstructuraAlmacen(almacen.getIdEstructura());
                        surtimiento.setIdPrescripcion(prescripcion.getIdPrescripcion());
                        surtimiento.setFechaProgramada(fechaSurtimiento);
                        surtimiento.setFolio("S"+recetaSiam.getFolio());
                        surtimiento.setIdEstatusSurtimiento(2);
                        surtimiento.setIdEstatusMirth(2);
                        surtimiento.setInsertFecha(new Date());
                        surtimiento.setInsertIdUsuario(usuarioSel.getIdUsuario());

                        List<SurtimientoInsumo> surInsumoList = new ArrayList<>();
                        for(PrescripcionInsumo item : insumosList){
                            SurtimientoInsumo surInsumo = new SurtimientoInsumo();
                            surInsumo.setIdSurtimientoInsumo(Comunes.getUUID());
                            surInsumo.setIdSurtimiento(surtimiento.getIdSurtimiento());
                            surInsumo.setIdPrescripcionInsumo(item.getIdPrescripcionInsumo());
                            surInsumo.setFechaProgramada(fechaSurtimiento);
                            surInsumo.setCantidadSolicitada(item.getFrecuencia());
                            surInsumo.setIdEstatusSurtimiento(2);
                            surInsumo.setIdEstatusMirth(2);                    
                            surInsumo.setInsertFecha(new Date());
                            surInsumo.setInsertIdUsuario(usuarioSel.getIdUsuario());

                            surInsumoList.add(surInsumo);
                        }
                        if(!surInsumoList.isEmpty())
                            folioMus = RegistrarSurtimiento(surtimiento,surInsumoList);
                    }else
                        throw new Exception("No se puede crear la receta");
                }else
                    throw new Exception("No se puede crear la receta");
            }else
                throw new Exception("No se puede crear la receta");
        } catch (Exception e) {
            throw new Exception("Error al registrar la receta.");
        }
        
        return folioMus;
                
    }
    public Date parseDate(String strDate) throws Exception {        
        try{
            List<ThreadLocal<SimpleDateFormat>> threadLocals = new  ArrayList<ThreadLocal<SimpleDateFormat>>();
            String[] formatts = {
                "dd-MM-yyyy'T'HH:mm:ss.SSS'Z'","dd/MM/yyyy'T'HH:mm:ss.SSS'Z'",
                "yyyy-MM-dd'T'HH:mm:ss.SSS","yyyy/MM/dd'T'HH:mm:ss.SSS",
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'","yyyy/MM/dd'T'HH:mm:ss.SSS'Z'",                
                "dd-MMM-yyyy'T'HH:mm:ss.SSS'Z'","dd/MMM/yyyy'T'HH:mm:ss.SSS'Z'",                
                "dd-MMM-yyyy HH:mm:ss","dd/MMM/yyyy HH:mm:ss",
                "dd-MM-yyyy HH:mm","dd/MM/yyyy HH:mm",
                "dd-MM-yyyy","dd/MM/yyyy",
                "dd-MMM-yyyy","dd/MMM/yyyy",
                "yyyy-MM-dd","yyyy/MM/dd",
                "yyyy-MMM-dd","yyyy/MMM/dd",
                "d MMMM, yyyy","MM dd, yyyy","E, MMM dd yyyy","E, MMM dd yyyy HH:mm:ss"};
            for (final String format : formatts) {
                ThreadLocal<SimpleDateFormat> dateFormatTL = new ThreadLocal<SimpleDateFormat>() {
                    protected SimpleDateFormat initialValue() {
                        SimpleDateFormat sdf = new SimpleDateFormat(format);
                        sdf.setLenient(false);
                        return sdf;
                    }
                };
                threadLocals.add(dateFormatTL);
            } 
            
            for (ThreadLocal<SimpleDateFormat> tl : threadLocals) {
                SimpleDateFormat sdf = tl.get();
                try {
                    return sdf.parse(strDate);
                } catch (Exception e) {
                    // Ignore and try next date parser
                }
            }
        }catch(Exception ex){
            LOGGER.error("Ocurrio una exception",ex);
        }
        
        return new Date();
    }
    
    
    private Paciente ObtenerPaciente(DerechoHabienteSiam derechoHabiente, String idUsuario ){
        Paciente paciente = pacienteMapper.obtenerPacienteByNumeroPaciente(derechoHabiente.getNumIssste().toString());
        if(paciente == null){
            paciente = new Paciente();
            paciente.setIdPaciente(Comunes.getUUID());
            paciente.setNombreCompleto(derechoHabiente.getNombre());
            paciente.setApellidoPaterno(derechoHabiente.getApellidoPaterno());
            paciente.setApellidoMaterno(derechoHabiente.getApellidoMaterno());
            paciente.setSexo('U');
            paciente.setFechaNacimiento(new Date());
            paciente.setIdTipoPaciente(1);
            paciente.setIdUnidadMedica(1);
            paciente.setClaveDerechohabiencia(derechoHabiente.getNumIssste().toString());
            paciente.setIdEstatusPaciente(1);
            paciente.setPacienteNumero(derechoHabiente.getNumIssste().toString());
            paciente.setIdEstadoCivil(1);
            paciente.setIdEscolaridad(1);
            paciente.setIdGrupoEtnico(1);
            paciente.setIdGrupoSanguineo(1);
            paciente.setIdReligion(1);
            paciente.setIdNivelSocioEconomico(1);
            paciente.setIdTipoVivienda(1);
            paciente.setIdOcupacion(1);                
            paciente.setInsertFecha(new Date());
            paciente.setInsertIdUsuario(idUsuario);

            pacienteMapper.insertar(paciente);
        }
            
        return paciente;
    }    
    private Usuario ObtenerMedico(MedicoSiam medico, String idUsuario) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Usuario user=null;
        try{
        user = usuarioMapper.obtenerUsuarioByMatriculaPersonal(medico.getClave().toString());
        if(user == null){
            user = new Usuario();
            user.setIdUsuario(Comunes.getUUID());
            user.setNombreUsuario(medico.getClave());
            user.setClaveAcceso(user.getIdUsuario());
            user.setCorreoElectronico(medico.getClave()+"@email.com");
            user.setNombre(medico.getNombre());
            user.setActivo(Constantes.ACTIVO);
            user.setUsuarioBloqueado(Constantes.INACTIVO);
            Date vigencia = sdf.parse("2040-12-31T23:59:59.986Z");
            user.setFechaVigencia(vigencia);
            user.setCedProfesional(medico.getClave());
            user.setMatriculaPersonal(medico.getClave());
            user.setInsertFecha(new Date());
            user.setInsertIdUsuario(idUsuario);

            usuarioMapper.insertar(user);
        }
        }catch(Exception ex){
            throw new Exception("Error al obtener el medico.");
        }
        return user;
    }
    private Visita ObtenerVisita(String numIssste,String numVisita, String idPaciente,String idUsuario){
        Visita visita = visitaMapper.obtenerVisitaDelDia(numIssste);
        if(visita==null){
            visita= new Visita();
            visita.setIdVisita(Comunes.getUUID());
            visita.setIdPaciente(idPaciente);
            visita.setFechaIngreso(new Date());
            visita.setIdUsuarioIngresa(idUsuario);
            visita.setIdMotivoPacienteMovimiento(1);
            visita.setFechaEgreso(new Date());
            visita.setIdUsuarioCierra(idUsuario);
            visita.setMotivoConsulta("Consulta Externa");
            visita.setTipoVisita("E");
            visita.setNumVisita(numVisita);
            visita.setInsertFecha(new Date());
            visita.setInsertIdUsuario(idUsuario);

            visitaMapper.insertar(visita);
        }
        return visita;
    }
    private String ObtenerIdPacienteServicio(String idVisita,String idEstructura,String idMedico, String idUsuario){        
        // crear Paciente Servicio
        PacienteServicio servicio = pacienteServicioMapper.obtenerPacienteServicioDia(idVisita);
        
        if(servicio==null){        
            servicio = new PacienteServicio();
            servicio.setIdPacienteServicio(Comunes.getUUID());
            servicio.setIdVisita(idVisita);
            servicio.setIdEstructura(idEstructura);
            servicio.setFechaAsignacionInicio(new Date());
            servicio.setIdUsuarioAsignacionInicio(idUsuario);
            servicio.setFechaAsignacionFin(new Date());
            servicio.setIdUsuarioAsignacionFin(idUsuario);
            servicio.setIdMotivoPacienteMovimiento(1);
            servicio.setIdMedico(idMedico);
            servicio.setInsertFecha(new Date());
            servicio.setInsertIdUsuario(idUsuario);

            pacienteServicioMapper.insertar(servicio);
        }        
        
        return servicio.getIdPacienteServicio();
    }
    private boolean RegistrarPrescripcion(Prescripcion prescripcion,List<PrescripcionInsumo> insumosList) throws Exception{
        boolean resp=false;
        try{            
            resp = prescripcionMapper.insertar(prescripcion);
            if(resp){                
                prescripcionInsumoMapper.registraMedicamentoList(insumosList);
            }
        }catch(Exception ex){
            throw new Exception("Error al insertar los SurtimientoInsumos.");
        }
        return resp;
    }        
    private String RegistrarSurtimiento(Surtimiento surtimiento,List<SurtimientoInsumo> surInsumoList) throws Exception{
        String folioMus="";
        boolean res= false;        
        Folios folios = foliosMapper.obtenerPrefixPorDocument(TipoDocumento_Enum.RECETA.getValue());
        surtimiento.setFolio(Comunes.generaFolio(folios));
        folios.setSecuencia(Comunes.separaFolio(surtimiento.getFolio()));            
        res = foliosMapper.actualizaFolios(folios);
        if(res){
            res = surtimientoMapper.insertar(surtimiento);
            if(res){
                res = surtimientoInsumoMapper.registraSurtimientoInsumoList(surInsumoList);
                if(!res)
                    throw new Exception("Error al insertar los SurtimientoInsumos.");
            }else
                throw new Exception("Error al insertar el Surtimiento.");
        }else
            throw new Exception("Error al generar el folio.");
        folioMus = surtimiento.getFolio();
        
        return folioMus;
    }
}
