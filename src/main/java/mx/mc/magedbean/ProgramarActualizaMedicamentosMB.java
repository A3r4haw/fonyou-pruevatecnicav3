/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import mx.mc.enums.EstatusActualizaMedicamentos_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.ProgramarActualizaMedicamentos;
import mx.mc.model.Usuario;
import mx.mc.service.ProgramarAtualMedicamentosService;
import mx.mc.util.Comunes;
import mx.mc.util.Mensaje;
import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author gcruz
 */
@Controller
@Scope(value = "view")
public class ProgramarActualizaMedicamentosMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(DiaFestivoMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);   

    private Date fechaProgramada;
    private String nota;
    private Usuario usuarioSession;
    private PermisoUsuario permiso;
    private ProgramarActualizaMedicamentos programarActuaMedicamentos;
    private List<ProgramarActualizaMedicamentos> listaFechasProgramadas;
    
    @Autowired
    private transient ProgramarAtualMedicamentosService progActuaMedService;
    
     @PostConstruct
    public void init() {
        try {
            usuarioSession = Comunes.obtenerUsuarioSesion();
            initialize();
            permiso = Comunes.obtenerPermisos(Transaccion_Enum.PROGRAMARACTUALMEDTOS.getSufijo());
            obtenerFechasProgramadas();
        } catch (Exception e) {
            LOGGER.error("Error en el metodo init de  ProgramarActualizaMedicamentosMB: {}", e.getMessage());
        }
    }
    
    /**
     * Metodo que inicializa todos los atributos de la clase
     */
    public void initialize() {
        
        fechaProgramada = new java.util.Date();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(java.util.Calendar.MILLISECOND, 0);
        fechaProgramada = cal.getTime();
        nota = "";
        programarActuaMedicamentos = new ProgramarActualizaMedicamentos();
        listaFechasProgramadas = new ArrayList<>();
    }
    
    public void obtenerFechasProgramadas() throws Exception{
        
        try {
            listaFechasProgramadas = progActuaMedService.obtenerFechasProgramadas();
        } catch(Exception ex) {
             throw new Exception("Error al obtener fechas programadas automáticas: " + ex.getMessage());
        }
    }    
    
    public void agregarProgramacionAutomatica() throws Exception {
        boolean programarActualizacion = false;
        try {
            programarActuaMedicamentos.setIdProgramarActualizaMedicamentos(Comunes.getUUID());
            programarActuaMedicamentos.setFechaProgramada(fechaProgramada);
            programarActuaMedicamentos.setNota(nota);
            programarActuaMedicamentos.setEstatus(EstatusActualizaMedicamentos_Enum.PROGRAMADA.getValue());
            programarActuaMedicamentos.setInsertFecha(new Date());
            programarActuaMedicamentos.setInsertIdUsuario(usuarioSession.getIdUsuario());
            
            programarActualizacion = progActuaMedService.insertar(programarActuaMedicamentos);
            
            if(programarActualizacion) {
                fechaProgramada = new Date();
                nota = "";
                obtenerFechasProgramadas();
                Mensaje.showMessage("info", RESOURCES.getString("progAutomaMedica.info.guardar"), null);
            }
        } catch (Exception ex) {
                throw new Exception("Error al programar la fecha automática: " + ex.getMessage());
        }
    }

    public void cancelarFechaProgramada(String idProgramarActualizaMedicamentos) throws Exception {
        LOGGER.debug("mx.mc.magedbean.ProgramarActualizaMedicamentosMB.cancelarFechaProgramada()");
        try {
            boolean resp = progActuaMedService.cancelarFechaProgramada(idProgramarActualizaMedicamentos);
            if (resp) {
                obtenerFechasProgramadas();
                boolean status = Constantes.ACTIVO;
                Mensaje.showMessage("info", RESOURCES.getString("progAutomaMedica.info.cancelar"), null);
                PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
            }
        } catch(Exception ex) {
            throw new Exception("Error al cancelar la fecha programada automática para medicamentos:  " + ex.getMessage());
        }
    }  
    
    public Date getFechaProgramada() {
        return fechaProgramada;
    }

    public void setFechaProgramada(Date fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public List<ProgramarActualizaMedicamentos> getListaFechasProgramadas() {
        return listaFechasProgramadas;
    }

    public void setListaFechasProgramadas(List<ProgramarActualizaMedicamentos> listaFechasProgramadas) {
        this.listaFechasProgramadas = listaFechasProgramadas;
    }    

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }        
    
}
