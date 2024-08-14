package mx.mc.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import mx.mc.enums.EstatusCama_Enum;
import mx.mc.enums.EstatusGabinete_Enum;
import mx.mc.enums.EstatusPaciente_Enum;
import mx.mc.enums.MotivoPacienteMovimiento_Enum;
import mx.mc.enums.TipoUsuario_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.Cama;
import mx.mc.model.Diagnostico;
import mx.mc.model.Estructura;
import mx.mc.model.Etiqueta;
import mx.mc.model.Impresora;
import mx.mc.model.MotivoPacienteMovimiento;
import mx.mc.model.Paciente;
import mx.mc.model.PacienteResponsable;
import mx.mc.model.PacienteServicio;
import mx.mc.model.PacienteServicioPeriferico;
import mx.mc.model.PacienteUbicacion;
import mx.mc.model.Paciente_Extended;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.Usuario;
import mx.mc.model.Visita;
import mx.mc.service.CamaService;
import mx.mc.service.DiagnosticoService;
import mx.mc.service.EstructuraService;
import mx.mc.service.ImpresoraService;
import mx.mc.service.MotivoPacienteMovimientoService;
import mx.mc.service.PacienteService;
import mx.mc.service.PacienteServicioPerifericoService;
import mx.mc.service.PacienteServicioService;
import mx.mc.service.PacienteUbicacionService;
import mx.mc.service.ReportesService;
import mx.mc.service.UsuarioService;
import mx.mc.service.VisitaService;
import mx.mc.util.Comunes;
import mx.mc.util.FechaUtil;
import mx.mc.util.Mensaje;
import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * @author AORTIZ
 */
@Controller
@Scope(value = "view")
public class PacienteEgresoIngresoMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(PacienteEgresoIngresoMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    private String textoBusqueda;
    private Paciente_Extended pacienteSelect;
    private List<Estructura> listaEstructuras;
    private List<MotivoPacienteMovimiento> listaMotivos;
    private List<Usuario> listaUsuarios;
    private String idMedico;
    private Integer idMotivoMovPaciente;
    private String idEstructura;
    private String justificacion;
    private String motivoConsulta;
    private String ubicacionPaciente;
    private Cama cama;
    private List<Diagnostico> listaDiagnosticos;
    private List<Paciente_Extended> listaPacientes;
    private Paciente_Extended pacienteDetalle;
    private PacienteServicioPeriferico obtenerPeriferico;
    private PacienteServicioPeriferico removerServicioPeriferico;
    private Integer tabIndex;
    private PacienteResponsable pacienteResponsable;
    private Usuario usuarioSelect;
    private Usuario usuarioSession;
    private boolean renderComboServicios;
    private List<Impresora> listImpresora;
    private String idImpresora;
    private String errPermisos;
    private String errorValidacion;
    private PermisoUsuario permiso;

    @Autowired
    private transient EstructuraService estructuraService;

    @Autowired
    private transient MotivoPacienteMovimientoService motivoPacienteMovimientoService;

    @Autowired
    private transient UsuarioService usuarioService;

    @Autowired
    private transient VisitaService visitaService;

    @Autowired
    private transient PacienteServicioService pacienteServicioService;

    @Autowired
    private transient CamaService camaService;

    @Autowired
    private transient PacienteUbicacionService pacienteUbicacionService;

    @Autowired
    private transient DiagnosticoService diagnosticoService;

    @Autowired
    private transient PacienteService pacienteService;

    @Autowired
    private transient PacienteServicioPerifericoService pacienteServicioPerifericoService;

    @Autowired
    private transient ReportesService reportesService;

    @Autowired
    private transient ImpresoraService impresoraService;

    /**
     * Metodo que se ejecuta despues de cargar la pantalla de pacientes
     */
    @PostConstruct
    public void init() {
        try {
            errPermisos = "estr.err.permisos";
            errorValidacion = "errorValidacion";
            initialize();
            this.listaPacientes = this.pacienteService.obtenerUltimosPacientesPacienteExtended(
                    Constantes.REGISTROS_PARA_MOSTRAR);
            this.usuarioSession = Comunes.obtenerUsuarioSesion();
            permiso = Comunes.obtenerPermisos(Transaccion_Enum.PACIENTES_EGR_ING.getSufijo());
            this.listImpresora = this.impresoraService.obtenerListaImpresoraByUsuario(usuarioSession.getIdUsuario());
        } catch (Exception e) {
            LOGGER.error("Error en el metodo init :: {}", e.getMessage());
        }
    }

    /**
     * Metodo que inicializa todos los atributos de la clase
     */
    public void initialize() {
        this.textoBusqueda = "";
        this.idImpresora = null;
        this.pacienteSelect = new Paciente_Extended();
        this.listaEstructuras = new ArrayList<>();
        this.listaMotivos = new ArrayList<>();
        this.listaUsuarios = new ArrayList<>();
        this.cama = new Cama();
        this.idMotivoMovPaciente = 0;
        this.idMedico = "";
        this.idEstructura = "";
        this.usuarioSelect = new Usuario();
        this.obtenerPeriferico = new PacienteServicioPeriferico();
        this.removerServicioPeriferico = new PacienteServicioPeriferico();
        this.justificacion = "";
        this.motivoConsulta = "";
        this.ubicacionPaciente = "";
        this.listaDiagnosticos = new ArrayList<>();
        this.listaPacientes = new ArrayList<>();
        this.pacienteDetalle = new Paciente_Extended();
        this.tabIndex = 0;
        this.renderComboServicios = false;
        this.listImpresora = new ArrayList<>();
    }

    /**
     * Metodo que carga el modal para dar ingreso al paciente al servicio
     *
     * @param pacienteSelect
     */
    public void cargarModalIngreso(Paciente_Extended pacienteSelect) {
        try {
            limpiarCampos();
            this.pacienteSelect = pacienteSelect;
            this.listaEstructuras = obtenerListaEstructuras();
            this.listaMotivos = obtenerListaMotivos(Constantes.TIPO_MOTIVO_INGRESO);
        } catch (Exception e) {
            LOGGER.error("Error en el metodo cargarModalIngreso :: {}", e.getMessage());
        }
    }

    /**
     * Metodo que carga el modal para dar egreso al paciente
     *
     * @param pacienteSelect
     */
    public void cargarModalEgreso(Paciente_Extended pacienteSelect) {
        try {
            limpiarCampos();
            this.pacienteSelect = pacienteSelect;
            this.listaMotivos = obtenerListaMotivos(Constantes.TIPO_MOTIVO_EGRESO);
            this.listaUsuarios = obtenerListaMedicos(pacienteSelect);
            this.ubicacionPaciente = obtenerUbicacionPaciente(pacienteSelect.getIdEstructura());
            this.cama = obtenerNumeroCama(pacienteSelect.getIdPaciente());
            this.listaDiagnosticos = obtenerDiagnostico(pacienteSelect.getIdPaciente());
        } catch (Exception e) {
            LOGGER.error("Error en el metodo cargarModalEgreso :: {}", e.getMessage());
        }
    }

    private List<Diagnostico> obtenerDiagnostico(String idPaciente) {
        List<Diagnostico> listaDiagnostico = new ArrayList<>();
        try {
            listaDiagnostico = diagnosticoService.obtenerDiagnosticoByIdPaciente(idPaciente);
        } catch (Exception e) {
            LOGGER.error("Error en el metodo obtenerDiagnostico :: {}", e.getMessage());
        }
        return listaDiagnostico;
    }

    private Cama obtenerNumeroCama(String idPaciente) {
        Cama numeroCama = null;
        try {
            Visita visitaAbierta = obtenerVisitaAbierta(idPaciente);
            PacienteServicio pacienteServicioAbierto = obtenerServicioAbierto(visitaAbierta);
            PacienteUbicacion pacienteUbicacionParam = new PacienteUbicacion();
            pacienteUbicacionParam.setIdPacienteServicio(
                    pacienteServicioAbierto.getIdPacienteServicio());
            PacienteUbicacion pacienteUbicacion = pacienteUbicacionService.
                    obtener(pacienteUbicacionParam);
            numeroCama = camaService.obtenerCama(pacienteUbicacion.getIdCama());
        } catch (Exception e) {
            LOGGER.error("Error en el metodo obtenerNumeroCama :: {}", e.getMessage());
        }
        return numeroCama;
    }

    private String obtenerUbicacionPaciente(String idEstructura) {
        String ubicacion = "";
        Estructura estructura = null;
        try {
            estructura = estructuraService.obtener(new Estructura(idEstructura));
            if (estructura.getIdEstructuraPadre() != null) {
                ubicacion = obtenerUbicacionPaciente(estructura.getIdEstructuraPadre()) + " > " + estructura.getNombre();
            } else {
                ubicacion = estructura.getNombre();
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo obtenerUbicacionPaciente :: {}", e.getMessage());
        }
        return ubicacion;
    }

    /**
     * Metodo que obtine una lista de estructuras por tipo
     *
     * @return List<Estructura>
     */
    private List<Estructura> obtenerListaEstructuras() {
        List<Estructura> listEstructuras = null;
        try {
            List<Integer> listaTipos = new ArrayList<>();
            listaTipos.add(3);
            listaTipos.add(4);
            listEstructuras = estructuraService.obtenerEstructurasPorTipo(listaTipos);
        } catch (Exception e) {
            LOGGER.error("Error en el metodo obtenerListaEstructuras :: {}", e.getMessage());
        }
        return listEstructuras;
    }

    private List<MotivoPacienteMovimiento> obtenerListaMotivos(String tipoMotivo) {
        List<MotivoPacienteMovimiento> listMotivos = null;
        try {
            MotivoPacienteMovimiento motivoPacienteMovimiento = new MotivoPacienteMovimiento();
            motivoPacienteMovimiento.setTipoMovimiento(tipoMotivo);
            motivoPacienteMovimiento.setActivo(Constantes.ES_ACTIVO);
            listMotivos = motivoPacienteMovimientoService.obtenerLista(motivoPacienteMovimiento);
        } catch (Exception e) {
            LOGGER.error("Error en el metodo obtenerListaMotivos :: {}", e.getMessage());
        }
        return listMotivos;
    }

    private List<Usuario> obtenerListaMedicos(Paciente_Extended pacienteSelect) {
        List<Usuario> listUsuarios = null;
        try {
            Usuario usuario = new Usuario();
            usuario.setActivo(true);
            usuario.setIdTipoUsuario(TipoUsuario_Enum.MEDICO.getValue());
            usuario.setIdEstructura(pacienteSelect.getIdEstructura());
            listUsuarios = usuarioService.obtenerLista(usuario);
        } catch (Exception e) {
            LOGGER.error("Error en el metodo obtenerListaMotivos :: {}", e.getMessage());
        }
        return listUsuarios;
    }

    /**
     * Metodo utilizado para ingresar al paciente a un servicio
     */
    public void ingresarPaciente() {
        try {
            if (!this.permiso.isPuedeProcesar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errPermisos), null);
                return;
            }
            if (validarCamposIngresoPaciente()) {
                PrimeFaces.current().ajax().addCallbackParam(errorValidacion, true);
                return;
            }
            Visita visita = setDatosVisita();
            PacienteServicio pacienteServicio = setDatosPacienteServicio(visita);
            Paciente paciente = setDatosPaciente();

            boolean resp = visitaService.registrarVisitaPaciente(
                    visita, pacienteServicio, paciente);
            if (resp) {
                Mensaje.showMessage("Info", RESOURCES.getString("pacienteEgrIng.ok.ingreso"), null);
                this.listaPacientes = this.pacienteService.obtenerUltimosPacientesPacienteExtended(
                        Constantes.REGISTROS_PARA_MOSTRAR);
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo ingresarPaciente :: {}", e.getMessage());
        }
    }

    public void ingresarPacientePeriferico() {
        try {
            if (!this.permiso.isPuedeAutorizar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errPermisos), null);
                return;
            }
            if (validarCamposIngresoPaciente()) {
                PrimeFaces.current().ajax().addCallbackParam(errorValidacion, true);
                return;
            }
            PacienteServicioPeriferico pacienteServicioPeriferico = setDatosPacienteServicioPeriferico();

            boolean resp = pacienteServicioPerifericoService.insertarPacienteServicioPeriferico(pacienteServicioPeriferico);
            if (resp) {
                this.motivoConsulta = "";
                Mensaje.showMessage("Info", RESOURCES.getString("pacienteEgrIng.ok.ingreso"), null);
                this.listaPacientes = this.pacienteService.obtenerUltimosPacientesPacienteExtended(
                        Constantes.REGISTROS_PARA_MOSTRAR);
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo ingresarPacientePeriferico :: {}", e.getMessage());
        }
    }

    private Visita setDatosVisita() {
        Visita visita = new Visita();
        visita.setIdVisita(Comunes.getUUID());
        visita.setIdPaciente(this.pacienteSelect.getIdPaciente());
        visita.setFechaIngreso(new Date());
        visita.setIdUsuarioIngresa(this.usuarioSession.getIdUsuario());
        visita.setIdMotivoPacienteMovimiento(
                MotivoPacienteMovimiento_Enum.ADMISION.getValue());
        visita.setMotivoConsulta(this.motivoConsulta);
        visita.setInsertFecha(new Date());
        visita.setInsertIdUsuario(this.usuarioSession.getIdUsuario());
        return visita;
    }

    private Paciente setDatosPaciente() {
        Paciente paciente = new Paciente();
        paciente.setIdPaciente(this.pacienteSelect.getIdPaciente());
        paciente.setIdEstatusPaciente(
                EstatusPaciente_Enum.ASIGNADO_A_SERVICIO.getValue());
        paciente.setEstatusGabinete(EstatusGabinete_Enum.PENDIENTE.getValue());
        paciente.setIdEstructura(this.idEstructura);
        paciente.setUpdateFecha(new Date());
        paciente.setUpdateIdUsuario(this.usuarioSession.getIdUsuario());
        return paciente;
    }

    private PacienteServicio setDatosPacienteServicio(Visita visita) {
        PacienteServicio pacienteServicio = new PacienteServicio();
        pacienteServicio.setIdPacienteServicio(Comunes.getUUID());
        pacienteServicio.setIdVisita(visita.getIdVisita());
        pacienteServicio.setIdEstructura(this.idEstructura);
        pacienteServicio.setFechaAsignacionInicio(new Date());
        pacienteServicio.setIdUsuarioAsignacionInicio(
                this.usuarioSession.getIdUsuario());
        pacienteServicio.setIdMotivoPacienteMovimiento(
                MotivoPacienteMovimiento_Enum.ADMISION.getValue());
        pacienteServicio.setInsertFecha(new Date());
        pacienteServicio.setInsertIdUsuario(this.usuarioSession.getIdUsuario());
        return pacienteServicio;
    }

    private PacienteServicioPeriferico setDatosPacienteServicioPeriferico() {
        PacienteServicioPeriferico pacienteServicioPeriferico = new PacienteServicioPeriferico();

        if (pacienteSelect.getIdEstructura() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No puede Asignarle un Periferico, si no cuenta con un Servicio", null);
        } else {
            pacienteServicioPeriferico.setIdPacienteServicioPeriferico(Comunes.getUUID());
            pacienteServicioPeriferico.setIdPaciente(this.pacienteSelect.getIdPaciente());
            pacienteServicioPeriferico.setIdEstructura(this.idEstructura);
            pacienteServicioPeriferico.setFechaIngreso(new java.util.Date());
            pacienteServicioPeriferico.setIdUsuarioIngresa(this.usuarioSession.getIdUsuario());
            //TODO: verificar el por que de admision en el setIdMotivoPacienteMovimiento es == otivoPacienteMovimiento_Enum.ADMISION.getValue();
            pacienteServicioPeriferico.setIdMotivoPacienteMovimiento(idMotivoMovPaciente);

            //TODO: Verificar que el medico que this.medico  y no UsuarioSession.getIdUsuario();
            pacienteServicioPeriferico.setIdMedicoAutoriza(this.idMedico);
            pacienteServicioPeriferico.setNotasIngreso(motivoConsulta);
            pacienteServicioPeriferico.setInsertFecha(new java.util.Date());
            pacienteServicioPeriferico.setInsertIdUsuario(this.usuarioSession.getIdUsuario());

        }
        return pacienteServicioPeriferico;
    }

    /**
     * Metodo utilizado para Egresar al paciente a un servicio
     */
    public void egresarPaciente() {
        try {
            if (!this.permiso.isPuedeProcesar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errPermisos), "");
                return;
            }
            if (validarCamposEgresoPaciente()) {
                PrimeFaces.current().ajax().addCallbackParam(errorValidacion, true);
                return;
            }

            boolean resp;
            if (this.idMotivoMovPaciente.intValue()
                    == MotivoPacienteMovimiento_Enum.TRANS_SERV.getValue()) {
                resp = cerrarServicioYasignarOtro();
            } else {
                resp = cerrarVisitaYServicio();
            }
            if (resp) {
                Mensaje.showMessage("Info", RESOURCES.getString("pacienteEgrIng.ok.egreso"), null);
                this.listaPacientes = this.pacienteService.obtenerUltimosPacientesPacienteExtended(
                        Constantes.REGISTROS_PARA_MOSTRAR);
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("surtimiento.error.operacion"), null);
            }

        } catch (Exception e) {
            LOGGER.error("Error en el metodo egresarPaciente :: {}", e.getMessage());
        }
    }

    public void egresarPacienteServicioPeriferico() {
        try {
            if (!this.permiso.isPuedeAutorizar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errPermisos), null);
                return;
            }
            if (validarCamposEgresoPacientePeriferico()) {
                PrimeFaces.current().ajax().addCallbackParam(errorValidacion, true);
                return;
            }
            boolean resp;

            resp = removerPacienteServicioPeriferico();

            if (resp) {
                Mensaje.showMessage("Info", RESOURCES.getString("pacienteEgrIng.err.periferico"), null);
                this.listaPacientes = this.pacienteService.obtenerUltimosPacientesPacienteExtended(
                        Constantes.REGISTROS_PARA_MOSTRAR);
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("surtimiento.error.operacion"), null);
            }

        } catch (Exception e) {
            LOGGER.error("Error en el metodo egresarPacienteServicioPeriferico :: {}", e.getMessage());
        }
    }

    private boolean cerrarVisitaYServicio() {
        boolean resp = false;
        try {
            Visita visitaAbierta = obtenerVisitaAbierta(this.pacienteSelect.getIdPaciente());
            PacienteServicio pacienteServicioAbierto = obtenerServicioAbierto(visitaAbierta);

            pacienteServicioAbierto.setFechaAsignacionFin(new Date());
            pacienteServicioAbierto.setIdUsuarioAsignacionFin(this.usuarioSession.getIdUsuario());
            pacienteServicioAbierto.setIdMotivoPacienteMovimiento(this.idMotivoMovPaciente);
            pacienteServicioAbierto.setIdMedico(this.idMedico);
            pacienteServicioAbierto.setJustificacion(this.justificacion);
            pacienteServicioAbierto.setUpdateIdUsuario(this.usuarioSession.getIdUsuario());
            pacienteServicioAbierto.setUpdateFecha(new Date());

            Paciente paciente = new Paciente();
            paciente.setIdPaciente(this.pacienteSelect.getIdPaciente());
            paciente.setIdEstructura(Constantes.TXT_VACIO);
            paciente.setUpdateFecha(new Date());
            paciente.setUpdateIdUsuario(this.usuarioSession.getIdUsuario());
            paciente.setIdEstatusPaciente(EstatusPaciente_Enum.EGRESO.getValue());
            paciente.setEstatusGabinete(EstatusGabinete_Enum.INACTIVAR.getValue());

            visitaAbierta.setIdUsuarioCierra(this.usuarioSession.getIdUsuario());
            visitaAbierta.setFechaEgreso(new Date());
            visitaAbierta.setIdMotivoPacienteMovimiento(this.idMotivoMovPaciente);
            visitaAbierta.setUpdateIdUsuario(this.usuarioSession.getIdUsuario());
            visitaAbierta.setUpdateFecha(new Date());

            PacienteUbicacion pacienteUbicacion = new PacienteUbicacion();
            pacienteUbicacion.setIdPacienteServicio(pacienteServicioAbierto.getIdPacienteServicio());
            pacienteUbicacion.setIdUsuarioUbicaFin(this.usuarioSession.getIdUsuario());
            pacienteUbicacion.setFechaUbicaFin(new Date());
            pacienteUbicacion.setUpdateIdUsuario(this.usuarioSession.getIdUsuario());
            pacienteUbicacion.setUpdateFecha(new Date());

            resp = visitaService.cerrarVisitaYServicio(
                    visitaAbierta, pacienteServicioAbierto, paciente,
                    EstatusCama_Enum.DISPONIBLE.getValue(), pacienteUbicacion);

        } catch (Exception e) {
            LOGGER.error("Error en el metodo cerrarVisitayServicio :: {}", e.getMessage());
        }
        return resp;
    }

    private boolean cerrarServicioYasignarOtro() {
        boolean resp = false;
        try {
            Visita visitaAbierta = obtenerVisitaAbierta(this.pacienteSelect.getIdPaciente());
            PacienteServicio pacienteServicioAbierto = obtenerServicioAbierto(visitaAbierta);
            PacienteServicio nuevoServicio = setDatosPacienteServicio(visitaAbierta);

            pacienteServicioAbierto.setFechaAsignacionFin(new Date());
            pacienteServicioAbierto.setIdUsuarioAsignacionFin(this.usuarioSession.getIdUsuario());
            pacienteServicioAbierto.setIdMotivoPacienteMovimiento(this.idMotivoMovPaciente);
            pacienteServicioAbierto.setIdMedico(this.idMedico);
            pacienteServicioAbierto.setJustificacion(this.justificacion);
            pacienteServicioAbierto.setUpdateIdUsuario(this.usuarioSession.getIdUsuario());
            pacienteServicioAbierto.setUpdateFecha(new Date());

            Paciente paciente = new Paciente();
            paciente.setIdPaciente(this.pacienteSelect.getIdPaciente());
            paciente.setIdEstructura(this.idEstructura);
            paciente.setUpdateFecha(new Date());
            paciente.setUpdateIdUsuario(this.usuarioSession.getIdUsuario());
            paciente.setIdEstatusPaciente(EstatusPaciente_Enum.ASIGNADO_A_SERVICIO.getValue());
            paciente.setEstatusGabinete(EstatusGabinete_Enum.INACTIVAR.getValue());

            PacienteUbicacion pacienteUbicacion = new PacienteUbicacion();
            pacienteUbicacion.setIdPacienteServicio(pacienteServicioAbierto.getIdPacienteServicio());
            pacienteUbicacion.setIdUsuarioUbicaFin(this.usuarioSession.getIdUsuario());
            pacienteUbicacion.setFechaUbicaFin(new Date());
            pacienteUbicacion.setUpdateIdUsuario(this.usuarioSession.getIdUsuario());
            pacienteUbicacion.setUpdateFecha(new Date());

            resp = pacienteServicioService.cerrarServicioYasigarOtro(
                    pacienteServicioAbierto, paciente, nuevoServicio,
                    EstatusCama_Enum.DISPONIBLE.getValue(), pacienteUbicacion);
        } catch (Exception e) {
            LOGGER.error("Error en el metodo cerrarServicio :: {}", e.getMessage());
        }
        return resp;
    }

    private boolean removerPacienteServicioPeriferico() {
        boolean resp = false;

        try {

            removerServicioPeriferico.setIdMedicoAutoriza(idMedico);
            //Ya no se necesita la estructura ya que se va a actualizar a null para el paciente en su campo IdEstructuraPeriferico
            removerServicioPeriferico.setIdEstructura(pacienteSelect.getIdEstructuraPeriferico());

            removerServicioPeriferico.setIdPaciente(pacienteSelect.getIdPaciente());

            obtenerPeriferico = pacienteServicioPerifericoService.obtenerDatos(removerServicioPeriferico);

            obtenerPeriferico.setUpdateFecha(new java.util.Date());
            obtenerPeriferico.setUpdateIdUsuario(usuarioSession.getIdUsuario());
            obtenerPeriferico.setIdMotivoPacienteMovimientoSalida(idMotivoMovPaciente);
            obtenerPeriferico.setIdMedicoAutoriza(idMedico);
            obtenerPeriferico.setIdEstructura(null);
            obtenerPeriferico.setNotasEgreso(justificacion);
            obtenerPeriferico.setFechaEgreso(new java.util.Date());
            obtenerPeriferico.setIdUsuarioEgreso(usuarioSession.getIdUsuario());
            obtenerPeriferico.setUpdateIdUsuario(usuarioSession.getIdUsuario());
            obtenerPeriferico.setUpdateFecha(new java.util.Date());

            resp = pacienteServicioPerifericoService.removerServicioPeriferico(obtenerPeriferico);
        } catch (Exception e) {
            LOGGER.error("Error al Remover el ServicioPeriferico: {}", e.getMessage());
        }
        return resp;
    }

    private boolean validarCamposEgresoPaciente() {
        boolean resp = false;
        try {
            if (this.idMotivoMovPaciente == 0) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("pacienteEgrIng.err.motivoSalida"), null);
                return true;
            }

            if (this.idMotivoMovPaciente.intValue() == MotivoPacienteMovimiento_Enum.TRANS_SERV.getValue()
                    && this.idEstructura.equals("0")) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("pacienteEgrIng.err.servicio"), null);
                return true;
            }
//            if (this.idMedico.equals("0")) {
//                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("pacienteEgrIng.err.medico"), null);
//                return true;
//            }

        } catch (Exception e) {
            LOGGER.error("Error en el metodo validarCamposEgresoPaciente :: {}", e.getMessage());
        }
        return resp;
    }

    private boolean validarCamposEgresoPacientePeriferico() {
        boolean resp = false;
        try {
            if (this.idMotivoMovPaciente == 0) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("pacienteEgrIng.err.motivoSalida"), null);
                return true;
            }

            if (this.idMotivoMovPaciente.intValue() == MotivoPacienteMovimiento_Enum.TRANS_SERV.getValue()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("pacienteEgrIng.err.motPeriferico"), null);

            }
            if (this.idMedico.equals("0")) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("pacienteEgrIng.err.medico"), null);
                return true;
            }

        } catch (Exception e) {
            LOGGER.error("Error en el metodo validarCamposEgresoPacientePeriferico :: {}", e.getMessage());
        }
        return resp;
    }

    private boolean validarCamposIngresoPaciente() {
        boolean resp = false;
        try {
            if (this.idEstructura.equals("0")) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("pacienteEgrIng.err.servicio"), null);
                return true;
            }
            if (this.idMotivoMovPaciente == 0) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("pacienteEgrIng.err.motivoConsulta"), null);
                return true;
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo validarCamposIngresoPaciente :: {}", e.getMessage());
        }
        return resp;
    }

    private PacienteServicio obtenerServicioAbierto(Visita visitaAbierta) throws Exception {
        PacienteServicio pacienteServicio = new PacienteServicio();
        pacienteServicio.setIdVisita(visitaAbierta.getIdVisita());
        return pacienteServicioService.
                obtenerPacienteServicioAbierto(pacienteServicio);
    }

    private Visita obtenerVisitaAbierta(String idPaciente) throws Exception {
        Visita visita = new Visita();
        visita.setIdPaciente(idPaciente);
        return visitaService.obtenerVisitaAbierta(visita);
    }

    /**
     * Metodo utilizado para buscar pacientes de acuerdo a un paramentro de
     * busqueda
     */
    public void buscarRegistros() {
        try {
            if (!this.permiso.isPuedeVer()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errPermisos), null);
                return;
            }
            this.listaPacientes = pacienteService
                    .obtenerRegistrosPorCriterioDeBusqueda(this.textoBusqueda,
                            Constantes.REGISTROS_PARA_MOSTRAR);
        } catch (Exception e) {
            LOGGER.error("Error en el metodo buscarRegistros :: {}", e.getMessage());
        }
    }

    /**
     * Metodo utilizado para obener los datos del detalle
     */
    public void mostrarDetallePacienteSelect() {
        try {
            this.pacienteDetalle = pacienteService.obtenerPacienteCompletoPorId(
                    this.pacienteSelect.getIdPaciente());
        } catch (Exception e) {
            LOGGER.error("Error en el metodo mostrarDetallePacienteSelect :: {}", e.getMessage());
        }
    }

    /**
     * Metodo utilizado para obener los datos del detalle al pulsar el boton en
     * el registro
     */
    public void mostrarDetallePacienteBotonRegistro(String idPaciente) {
        try {
            this.pacienteDetalle = pacienteService.obtenerPacienteCompletoPorId(
                    idPaciente);
        } catch (Exception e) {
            LOGGER.error("Error en el metodo mostrarDetallePaciente :: {}", e.getMessage());
        }
    }

    public void imprimir(Paciente_Extended pacienteSelect) throws Exception {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.imprimir()");
        boolean status = Constantes.INACTIVO;
        try {

            byte[] buffer = reportesService.imprimePulcera(pacienteSelect);
            if (buffer != null) {
                status = Constantes.ACTIVO;
                SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "sesionMB");
                sesion.setReportStream(buffer);
                sesion.setReportName(String.format("prescripcion_%s.pdf", pacienteSelect.getPacienteNumero()));
            }
        } catch (Exception e) {
            LOGGER.error("Error al generar la Impresión: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public void cargarPaciente(Paciente_Extended pacienteSelect) {
        try {
            limpiarCampos();
            this.pacienteSelect = pacienteSelect;
        } catch (Exception e) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("paciente.err.conect"), null);
            LOGGER.error("Error al generar en cargarPaciente: {}", e.getMessage());
        }
    }

    public void imprimirEtiqueta() throws Exception {
        try {
            if (this.idImpresora != null) {
                Etiqueta etiqueta = new Etiqueta();
                etiqueta.setNombrePaciente(
                        pacienteSelect.getNombreCompleto() + " " + pacienteSelect.getApellidoPaterno() + " " + pacienteSelect.getApellidoMaterno());
                etiqueta.setNumeroPaciente(pacienteSelect.getPacienteNumero());
                etiqueta.setClavePaciente(pacienteSelect.getClaveDerechohabiencia());
                etiqueta.setTitulo("MUS - Hospital Motion");
                Impresora unaImpresora = impresoraService.obtenerPorIdImpresora(this.idImpresora);
                etiqueta.setIpImpresora(unaImpresora.getIpImpresora());
                etiqueta.setFechaHora(FechaUtil.formatoCadena(new Date(), "dd/MM/yyyy HH:mm:ss"));
                boolean resp = reportesService.imprimirEtiqueta(etiqueta);
                if (resp) {
                    Mensaje.showMessage("Info", RESOURCES.getString("paciente.info.impCorrectamente"), null);
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("paciente.err.genimpresion"), null);
                }
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("paciente.err.selctImpre"), null);
            }
            limpiarCampos();
        } catch (Exception e) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("paciente.err.conect"), null);
            LOGGER.error("Error al generar al imprimirEtiqueta: {}", e.getMessage());
        }
    }

    public void cargarComboServicios() {
        try {
            this.renderComboServicios = false;
            if (this.idMotivoMovPaciente == MotivoPacienteMovimiento_Enum.TRANS_SERV.getValue().intValue()) {
                this.listaEstructuras = obtenerListaEstructuras();
                this.renderComboServicios = true;
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo cargarComboServicios :: {}", e.getMessage());
        }
    }

    public void cargarComboServiciosPerif() {
        try {
            this.renderComboServicios = false;
            if (this.idMotivoMovPaciente == MotivoPacienteMovimiento_Enum.TRANS_SERV.getValue().intValue()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No existe esta funcionalidad en Servicio Periférico, Seleccione Otro", null);
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo cargarComboServicios :: {}", e.getMessage());
        }
    }

    private void limpiarCampos() {
        this.justificacion = "";
        this.idEstructura = "";
        this.idMedico = "";
        this.idMotivoMovPaciente = 0;
        this.renderComboServicios = false;
        this.idImpresora = null;
    }

    public String getTextoBusqueda() {
        return textoBusqueda;
    }

    public void setTextoBusqueda(String textoBusqueda) {
        this.textoBusqueda = textoBusqueda;
    }

    public Usuario getUsuarioSelect() {
        return usuarioSelect;
    }

    public void setUsuarioSelect(Usuario usuarioSelect) {
        this.usuarioSelect = usuarioSelect;
    }

    public Paciente_Extended getPacienteSelect() {
        return pacienteSelect;
    }

    public void setPacienteSelect(Paciente_Extended pacienteSelect) {
        this.pacienteSelect = pacienteSelect;
    }

    public List<Estructura> getListaEstructuras() {
        return listaEstructuras;
    }

    public void setListaEstructuras(List<Estructura> listaEstructuras) {
        this.listaEstructuras = listaEstructuras;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public List<MotivoPacienteMovimiento> getListaMotivos() {
        return listaMotivos;
    }

    public void setListaMotivos(List<MotivoPacienteMovimiento> listaMotivos) {
        this.listaMotivos = listaMotivos;
    }

    public Integer getIdMotivoMovPaciente() {
        return idMotivoMovPaciente;
    }

    public void setIdMotivoMovPaciente(Integer idMotivoMovPaciente) {
        this.idMotivoMovPaciente = idMotivoMovPaciente;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public Usuario getUsuarioSession() {
        return usuarioSession;
    }

    public void setUsuarioSession(Usuario usuarioSession) {
        this.usuarioSession = usuarioSession;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

    public String getMotivoConsulta() {
        return motivoConsulta;
    }

    public void setMotivoConsulta(String motivoConsulta) {
        this.motivoConsulta = motivoConsulta;
    }

    public String getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(String idMedico) {
        this.idMedico = idMedico;
    }

    public String getUbicacionPaciente() {
        return ubicacionPaciente;
    }

    public void setUbicacionPaciente(String ubicacionPaciente) {
        this.ubicacionPaciente = ubicacionPaciente;
    }

    public Cama getCama() {
        return cama;
    }

    public void setCama(Cama cama) {
        this.cama = cama;
    }

    public List<Diagnostico> getListaDiagnosticos() {
        return listaDiagnosticos;
    }

    public void setListaDiagnosticos(List<Diagnostico> listaDiagnosticos) {
        this.listaDiagnosticos = listaDiagnosticos;
    }

    public List<Paciente_Extended> getListaPacientes() {
        return listaPacientes;
    }

    public void setListaPacientes(List<Paciente_Extended> listaPacientes) {
        this.listaPacientes = listaPacientes;
    }

    public Paciente_Extended getPacienteDetalle() {
        return pacienteDetalle;
    }

    public void setPacienteDetalle(Paciente_Extended pacienteDetalle) {
        this.pacienteDetalle = pacienteDetalle;
    }

    public Integer getTabIndex() {
        return tabIndex;
    }

    public void setTabIndex(Integer tabIndex) {
        this.tabIndex = tabIndex;
    }

    public PacienteResponsable getPacienteResponsable() {
        return pacienteResponsable;
    }

    public void setPacienteResponsable(PacienteResponsable pacienteResponsable) {
        this.pacienteResponsable = pacienteResponsable;
    }

    public boolean isRenderComboServicios() {
        return renderComboServicios;
    }

    public void setRenderComboServicios(boolean renderComboServicios) {
        this.renderComboServicios = renderComboServicios;
    }

    public PacienteServicioPeriferico getObtenerPeriferico() {
        return obtenerPeriferico;
    }

    public void setObtenerPeriferico(PacienteServicioPeriferico obtenerPeriferico) {
        this.obtenerPeriferico = obtenerPeriferico;
    }

    public PacienteServicioPeriferico getRemoverServicioPeriferico() {
        return removerServicioPeriferico;
    }

    public void setRemoverServicioPeriferico(PacienteServicioPeriferico removerServicioPeriferico) {
        this.removerServicioPeriferico = removerServicioPeriferico;
    }

    public List<Impresora> getListImpresora() {
        return listImpresora;
    }

    public void setListImpresora(List<Impresora> listImpresora) {
        this.listImpresora = listImpresora;
    }

    public String getIdImpresora() {
        return idImpresora;
    }

    public void setIdImpresora(String idImpresora) {
        this.idImpresora = idImpresora;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }
}
