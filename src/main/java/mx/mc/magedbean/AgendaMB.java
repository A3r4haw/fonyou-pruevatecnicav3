package mx.mc.magedbean;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import mx.mc.enums.EstatusCita_Enum;
import mx.mc.enums.TipoAreaEstructura_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.Cita;
import mx.mc.model.Estructura;
import mx.mc.model.Paciente;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.Turno;
import mx.mc.model.Usuario;
import mx.mc.model.Usuario_Extended;
import mx.mc.service.CitaService;
import mx.mc.service.EstructuraService;
import mx.mc.service.PacienteService;
import mx.mc.service.TurnoService;
import mx.mc.service.UsuarioService;
import mx.mc.util.Comunes;
import mx.mc.util.FechaUtil;
import mx.mc.util.Mensaje;
import org.apache.commons.lang3.time.DateUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * @author aortiz
 */
@Controller
@Scope(value = "view")
public class AgendaMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(AgendaMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);    

    private DefaultScheduleModel eventModel;
    private Usuario usuarioSession;
    private Integer idTurno;
    private List<Turno> listaTurnos;
    private String horaInicio;
    private String horaFin;
    private Date fechaInicio;
    private Date fechaFin;
    private Date fechaActual;
    private List<Cita> listaCitas;
    private String vistaAgenda;
    private Date diaInicial;
    private String tituloCita;
    private List<Estructura> listaConsultorios;
    private String idConsultorio;
    private List<Usuario_Extended> listaMedicos;
    private String idMedico;
    private PermisoUsuario permiso;
    private List<Paciente> listaPacientes;
    private Paciente idPaciente;
    private String comentarios;
    private boolean disabled;
    private boolean cancel;
    private boolean clean;
    private boolean save;
    private boolean closeCita;
    private String errPermisos;

    @Autowired
    private transient TurnoService turnoService;

    @Autowired
    private transient CitaService citaService;
    private Cita citaSelect;

    @Autowired
    private transient EstructuraService estructuraService;

    @Autowired
    private transient UsuarioService usuarioService;

    @Autowired
    private transient PacienteService pacienteService;

    /**
     * Metodo que se inicializa al invocar la pantalla de agenda
     */
    @PostConstruct
    public void init() {
        try {
            initialize();                
            permiso = Comunes.obtenerPermisos(Transaccion_Enum.AGENDA.getSufijo());            
            
            this.horaInicio = "";
            this.horaFin = "";
            this.vistaAgenda = "month";
            errPermisos="estr.err.permisos";

            this.listaTurnos = new ArrayList<>();
            this.usuarioSession = Comunes.obtenerUsuarioSesion();
            this.eventModel = new DefaultScheduleModel();
            this.listaTurnos = turnoService.obtenerLista(new Turno());
            List<Integer> listaTipoArea = new ArrayList<>();
            listaTipoArea.add(TipoAreaEstructura_Enum.CONSULTAEXTERNA.getValue());
            this.listaConsultorios = estructuraService.
                    obtenerEstructurasPorTipo(listaTipoArea);
        } catch (Exception e) {
            LOGGER.error("Error en el metodo init :: {}", e.getMessage());
        }

    }

    /**
     * Metodo que inicializa los valores de la pantalla
     */
    public void initialize() {
        this.tituloCita = "";
        this.fechaInicio = new Date();
        this.fechaFin = new Date();
        this.diaInicial = new Date();
        this.fechaActual = new Date();
        this.idPaciente = null;
        this.citaSelect = null;
        this.comentarios = "";
    }

    public void seleccionarTurno() {
        try {
            Turno turno = null;
            for (Turno item : this.listaTurnos) {
                if (item.getIdTurno().intValue() == this.idTurno) {
                    turno = item;
                    break;
                }
            }
            if (turno != null) {
                DateFormat dateFormat = new SimpleDateFormat("HH:mm");
                this.horaInicio = dateFormat.format(turno.getHoraInicio());
                this.horaFin = dateFormat.format(turno.getHoraFin());
                this.listaMedicos = usuarioService.
                        obtenerDoctoresPorTurnoYConsultorio(this.idTurno, this.idConsultorio);
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo seleccionarTurno :: {}", e.getMessage());
        }
    }

    public void seleccionarFecha(SelectEvent selectEvent) {
        try {
            boolean mostrar = Constantes.INACTIVO;
            clean = Constantes.INACTIVO;
            cancel = Constantes.ACTIVO;
            save = Constantes.INACTIVO;
            Date fechaSelecionada = (Date) selectEvent.getObject();
            Date fechaActual = new Date();
            if (this.idConsultorio == null || this.idConsultorio.isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("agenda.err.selectConsultorio"), null);
                return;
            }
            if (this.idTurno == null || this.idTurno == 0) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("agenda.err.selectTurno"), null);
                return;
            }
            if (this.idMedico == null || this.idMedico.isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("agenda.err.selectMedico"), null);
                return;
            }
            if (fechaSelecionada.before(fechaActual) && !DateUtils.isSameDay(fechaSelecionada, fechaActual)) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("agenda.err.fechaCitaAnterior"), null);
                return;
            }
            //Se muestra el dialog para crear una nueva cita
            if (this.vistaAgenda.equalsIgnoreCase(Constantes.VISTA_DIA)) {
                if (!this.permiso.isPuedeCrear()) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errPermisos), null);
                    return;
                }
                Cita cita = new Cita();
                cita.setFechaInicio(fechaSelecionada);
                cita.setIdEstructura(idConsultorio);
                cita.setIdMedico(idMedico);

                if (existeCita(cita) == null) {
                    clean = Constantes.ACTIVO;
                    cancel = Constantes.INACTIVO;
                    save = Constantes.ACTIVO;
                    disabled = Constantes.INACTIVO;
                    initialize();
                    mostrar = Constantes.ACTIVO;
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("agenda.err.mismaHoraCita"), null);
                    return;
                }
            }
            if (this.horaInicio == null || this.horaInicio.isEmpty()
                    || this.horaFin == null || this.horaFin.isEmpty()) {
                this.vistaAgenda = Constantes.VISTA_MES;
            } else {
                this.vistaAgenda = Constantes.VISTA_DIA;
            }
            this.diaInicial = fechaSelecionada;
            if (fechaSelecionada.after(fechaActual)) {
                this.fechaInicio = fechaSelecionada;
                this.fechaFin = new Date(this.fechaInicio.getTime() + (3600 * 1000));
            } else {
                this.fechaInicio = fechaActual;
                this.fechaFin = new Date(this.fechaInicio.getTime() + (3600 * 1000));
            }

            PrimeFaces.current().ajax().addCallbackParam("mostrar", mostrar);
        } catch (Exception e) {
            LOGGER.error("Error en el metodo seleccionarFecha :: {}", e.getMessage());
        }

    }

    public void selectItem(SelectEvent selectEvent) {
        try {
            boolean mostrar = Constantes.ACTIVO;
            disabled = Constantes.ACTIVO;
            clean = Constantes.INACTIVO;
            save = Constantes.INACTIVO;
            cancel = Constantes.INACTIVO;

            DefaultScheduleEvent item = (DefaultScheduleEvent) selectEvent.getObject();
            citaSelect = citaService.obtenerById(item.getDescription());//itegem.getDescription guarda el Id de la cita            
            this.idPaciente = pacienteService.obtenerPacienteByIdPaciente(citaSelect.getIdPaciente());
            this.fechaInicio = citaSelect.getFechaInicio();
            this.fechaFin = citaSelect.getFechaFin();
            this.tituloCita = citaSelect.getTitulo();
            this.comentarios = citaSelect.getComentarios();

            if (citaSelect.getEstatus().equals(EstatusCita_Enum.CERRADA.getValue())) {
                closeCita = true;
            } else {
                closeCita = false;
            }

            if (FechaUtil.isFechaMayorIgualQue(citaSelect.getFechaInicio(), fechaActual)) {
                disabled = Constantes.INACTIVO;
                cancel = Constantes.ACTIVO;
                save = Constantes.ACTIVO;
            }

            PrimeFaces.current().ajax().addCallbackParam("mostrar", mostrar);

        } catch (Exception e) {
            LOGGER.error("Error en el metodo selectItem :: {}", e.getMessage());
        }
    }

    public void moveItem(ScheduleEntryMoveEvent selectEvent) {
        try {
            DefaultScheduleEvent item = (DefaultScheduleEvent) selectEvent.getScheduleEvent();
            Cita cita = new Cita();
            Cita citaRollBack = citaService.obtenerById(item.getDescription());

            if (FechaUtil.isFechaMayorIgualQue(item.getStartDate(), fechaActual)) {
                cita.setIdCita(item.getDescription());
                cita.setFechaInicio(item.getStartDate());
                cita.setFechaFin(item.getEndDate());
                cita.setIdEstructura(idConsultorio);
                cita.setIdMedico(idMedico);

                Cita ncita = existeCita(cita);
                if (ncita == null) {
                    if (citaService.actualizar(cita)) {
                        Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("agenda.info.citaMovida"), null);
                    } else {
                        citaRollBack(citaRollBack);
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("agenda.err.noMovioCita"), null);
                    }
                } else {
                    citaRollBack(citaRollBack);
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("agenda.err.existeCita"), null);
                }
            } else {
                citaRollBack(citaRollBack);
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("agenda.err.existFechaAnterior"), null);
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo moveItem :: {}", e.getMessage());
        }
    }

    public void cancelarCita() {
        boolean resp = Constantes.INACTIVO;
        try {
            if (!this.permiso.isPuedeEliminar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errPermisos), null);
                return;
            }

            if (citaService.cancelCita(citaSelect.getIdCita())) {//Comentarios tiene el ID de la cita
                resp = Constantes.ACTIVO;
                Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("agenda.info.canceloCita"), null);
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("agenda.err.noCanceloCita"), null);
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo cancelarCita :: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam("cancel", resp);
    }

    public void cargarEventosCalendario() {
        try {
            if (!this.permiso.isPuedeVer()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errPermisos), null);
                return;
            }
            Cita cita = new Cita();
            cita.setIdEstructura(idConsultorio);
            cita.setIdTurno(idTurno);
            cita.setIdMedico(idMedico);

            this.listaCitas = citaService.obtenerLista(cita);

            for (Cita _item : this.listaCitas) {
                DefaultScheduleEvent item = new DefaultScheduleEvent();
                item.setStartDate(_item.getFechaInicio());
                item.setEndDate(_item.getFechaFin());
                item.setTitle(_item.getTitulo());
                item.setDescription(_item.getIdCita());//En este campo e guardara el ID de la Cita (No se muestra en el calendario)

                if (this.permiso.isPuedeEditar()) {
                    item.setEditable(FechaUtil.isFechaMayorIgualQue(_item.getFechaInicio(), fechaActual));
                } else {
                    item.setEditable(Constantes.INACTIVO);
                }

                this.eventModel.addEvent(item);
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo cargarEventosCalendario :: {}", e.getMessage());
        }
    }

    private Cita existeCita(Cita cita) {
        Cita ncita = new Cita();
        try {
            ncita = citaService.existeCita(cita);
        } catch (Exception e) {
            LOGGER.error("Error en el metodo existeCita :: {}", e.getMessage());
        }
        return ncita;
    }

    private void citaRollBack(Cita cita) {
        DefaultScheduleEvent rollback = new DefaultScheduleEvent();
        rollback.setStartDate(cita.getFechaInicio());
        rollback.setEndDate(cita.getFechaFin());
        rollback.setDescription(cita.getIdCita());
        rollback.setTitle(cita.getTitulo());
        eventModel.getEvents().stream().filter(prdct -> prdct.getDescription().equals(cita.getIdCita())).forEach(cnsmr -> 
            rollback.setId(cnsmr.getId())
        );
        eventModel.updateEvent(rollback);
    }

    /**
     *
     * Metodo utilizado para guardar las citas en la base de datos
     */
    public void guardarCita() {
        try {
            boolean save = Constantes.INACTIVO;
            Cita cita = new Cita();
            cita.setIdEstructura(this.idConsultorio);
            cita.setIdAgenda("");
            cita.setIdPaciente(this.idPaciente.getIdPaciente());
            cita.setIdMedico(this.idMedico);
            cita.setIdTurno(this.idTurno);
            cita.setTitulo(this.tituloCita);
            cita.setFechaInicio(this.fechaInicio);
            cita.setFechaFin(this.fechaFin);
            cita.setComentarios(this.comentarios);
            cita.setEstatus(1);

            if (citaSelect == null) {//Insertar   
                if (!this.permiso.isPuedeCrear()) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errPermisos), null);
                    return;
                }
                cita.setIdCita(Comunes.getUUID());
                cita.setInsertFecha(new Date());
                cita.setInsertIdUsuario(this.usuarioSession.getIdUsuario());

                if (citaService.insertar(cita)) {
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("agenda.info.crearCita"), null);
                    initialize();
                    save = Constantes.ACTIVO;
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("agenda.err.crearCita"), null);
                }

            } else {//Actualizar
                if (!this.permiso.isPuedeEditar()) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errPermisos), null);
                    return;
                }
                cita.setIdCita(citaSelect.getIdCita());
                cita.setUpdateFecha(new Date());
                cita.setUpdateIdUsuario(this.usuarioSession.getIdUsuario());
                if (citaService.actualizar(cita)) {
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("agenda.info.updateCita"), null);
                    initialize();
                    save = Constantes.ACTIVO;
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("agenda.err.updateCita"), null);
                }
            }
            PrimeFaces.current().ajax().addCallbackParam("save", save);
        } catch (Exception e) {
            LOGGER.error("Error en el metodo guardarCita :: {}", e.getMessage());
        }
    }

    public void actualizarEstatusCita() throws Exception {
        try {
            boolean save = Constantes.INACTIVO;
            Cita cita = new Cita();
            cita.setIdCita(citaSelect.getIdCita());
            cita.setEstatus(EstatusCita_Enum.CERRADA.getValue());

            if (citaService.actualizarEstatusCitas(cita)) {
                Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("agenda.info.closeCita"), null);                                                   
                save = Constantes.ACTIVO;             
            }
            PrimeFaces.current().ajax().addCallbackParam("save", save);
        } catch (Exception e) {
            LOGGER.error("Error en el metodo actualizarEstatusCita. {}", e.getMessage());
        }
    }

    public List<Paciente> autocompletePaciente(String valor) {
        try {
            Paciente paciente = new Paciente();
            paciente.setNombreCompleto(valor);
            paciente.setIdEstructura(this.idConsultorio);
            this.listaPacientes = pacienteService.obtenerLista(paciente);
        } catch (Exception e) {
            LOGGER.error("Error en el metodo autoCompletarCodigoPostal :: {}", e.getMessage());
        }
        return this.listaPacientes;
    }

    public boolean isSave() {
        return save;
    }

    public void setSave(boolean save) {
        this.save = save;
    }

    public boolean isClean() {
        return clean;
    }

    public void setClean(boolean clean) {
        this.clean = clean;
    }

    public boolean isCloseCita() {
        return closeCita;
    }

    public void setCloseCita(boolean closeCita) {
        this.closeCita = closeCita;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    public TurnoService getTurnoService() {
        return turnoService;
    }

    public void setTurnoService(TurnoService turnoService) {
        this.turnoService = turnoService;
    }

    public CitaService getCitaService() {
        return citaService;
    }

    public void setCitaService(CitaService citaService) {
        this.citaService = citaService;
    }

    public Cita getCitaSelect() {
        return citaSelect;
    }

    public void setCitaSelect(Cita citaSelect) {
        this.citaSelect = citaSelect;
    }

    public EstructuraService getEstructuraService() {
        return estructuraService;
    }

    public void setEstructuraService(EstructuraService estructuraService) {
        this.estructuraService = estructuraService;
    }

    public UsuarioService getUsuarioService() {
        return usuarioService;
    }

    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public PacienteService getPacienteService() {
        return pacienteService;
    }

    public void setPacienteService(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public void setEventModel(DefaultScheduleModel eventModel) {
        this.eventModel = eventModel;
    }

    public Integer getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(Integer idTurno) {
        this.idTurno = idTurno;
    }

    public List<Turno> getListaTurnos() {
        return listaTurnos;
    }

    public void setListaTurnos(List<Turno> listaTurnos) {
        this.listaTurnos = listaTurnos;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public List<Cita> getListaCitas() {
        return listaCitas;
    }

    public void setListaCitas(List<Cita> listaCitas) {
        this.listaCitas = listaCitas;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getVistaAgenda() {
        return vistaAgenda;
    }

    public void setVistaAgenda(String vistaAgenda) {
        this.vistaAgenda = vistaAgenda;
    }

    public Date getDiaInicial() {
        return diaInicial;
    }

    public void setDiaInicial(Date diaInicial) {
        this.diaInicial = diaInicial;
    }

    public String getTituloCita() {
        return tituloCita;
    }

    public void setTituloCita(String tituloCita) {
        this.tituloCita = tituloCita;
    }

    public List<Estructura> getListaConsultorios() {
        return listaConsultorios;
    }

    public void setListaConsultorios(List<Estructura> listaConsultorios) {
        this.listaConsultorios = listaConsultorios;
    }

    public String getIdConsultorio() {
        return idConsultorio;
    }

    public void setIdConsultorio(String idConsultorio) {
        this.idConsultorio = idConsultorio;
    }

    public List<Usuario_Extended> getListaMedicos() {
        return listaMedicos;
    }

    public void setListaMedicos(List<Usuario_Extended> listaMedicos) {
        this.listaMedicos = listaMedicos;
    }

    public String getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(String idMedico) {
        this.idMedico = idMedico;
    }

    public List<Paciente> getListaPacientes() {
        return listaPacientes;
    }

    public void setListaPacientes(List<Paciente> listaPacientes) {
        this.listaPacientes = listaPacientes;
    }

    public Paciente getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Paciente idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Usuario getUsuarioSession() {
        return usuarioSession;
    }

    public void setUsuarioSession(Usuario usuarioSession) {
        this.usuarioSession = usuarioSession;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }
    
}
