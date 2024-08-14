package mx.mc.magedbean;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Response;
import mx.mc.dto.MedicamentoDTO;
import mx.mc.dto.ParamBusquedaAlertaDTO;
import mx.mc.dto.RespuestaAlertasDTO;
import mx.mc.dto.ValidacionNoCumplidaDTO;
import mx.mc.enums.CatalogoGeneral_Enum;
import mx.mc.lazy.PrescripcionesLazy;
import mx.mc.enums.EstatusDiagnostico_Enum;
import mx.mc.enums.EstatusGabinete_Enum;
import mx.mc.enums.EstatusPaciente_Enum;
import mx.mc.enums.EstatusPrescripcion_Enum;
import mx.mc.enums.EstatusSurtimiento_Enum;
import mx.mc.enums.TipoAlmacen_Enum;
import mx.mc.enums.TipoAreaEstructura_Enum;
import mx.mc.enums.TipoConsulta_Enum;
import mx.mc.enums.TipoPrescripcion_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.init.CustomWebSecurityConfigurerAdapter;
import mx.mc.model.AlertaFarmacovigilancia;
import mx.mc.model.AlmacenControl_Extended;
import mx.mc.model.AlmacenInsumoComprometido;
import mx.mc.model.Cama;
import mx.mc.model.CamaExtended;
import mx.mc.model.Diagnostico;
import mx.mc.model.DiagnosticoPaciente;
import mx.mc.model.Estructura;
import mx.mc.model.EstructuraAlmacenServicio;
import mx.mc.model.Medicamento;
import mx.mc.model.Medicamento_Extended;
import mx.mc.model.PacienteServicio;
import mx.mc.model.Paciente_Extended;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.Prescripcion;
import mx.mc.model.PrescripcionInsumo;
import mx.mc.model.PrescripcionInsumo_Extended;
import mx.mc.model.Prescripcion_Extended;
import mx.mc.model.Surtimiento;
import mx.mc.model.SurtimientoInsumo;
import mx.mc.model.TransaccionPermisos;
import mx.mc.model.Usuario;
import mx.mc.model.Visita;
import mx.mc.service.AlmacenControlService;
import mx.mc.service.DiagnosticoService;
import mx.mc.service.EstructuraAlmacenServicioService;
import mx.mc.service.EstructuraService;
import mx.mc.service.MedicamentoService;
import mx.mc.service.PacienteService;
import mx.mc.service.UsuarioService;
import mx.mc.service.CamaService;
import mx.mc.service.PacienteServicioService;
import mx.mc.service.PacienteUbicacionService;
import mx.mc.service.PrescripcionService;
import mx.mc.service.ReportesService;
import mx.mc.service.VisitaService;
import mx.mc.util.Comunes;
import mx.mc.util.FechaUtil;
import mx.mc.util.Mensaje;
import mx.mc.ws.farmacovigilancia.service.ReaccionesAdversas;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author hramirez
 */
@Controller
@Scope(value = "view")
public class PrescripcionMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(PrescripcionMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private boolean editable;
    private boolean huboError;
    private String cadenaBusqueda;
    private boolean elementoSeleccionado;
    private boolean muestraConfirmacion;
    private PermisoUsuario permiso;
    private Date fechaActual;
    private String nombreUsuario;    
    private String prcLista;
    private String errRegistroIncorrecto;
    private String errAccion;
    private String prcDiaMinimo;
    private String prcNoPuedeCancelar;
    private String prcRegistroErr;

    private Usuario usuarioSelected;
    private CamaExtended camaselected;

    @Autowired
    private transient PrescripcionService prescripcionService;
    private Prescripcion prescripcionSelected;
    private List<Prescripcion> prescripcionList;
    private List<Prescripcion> prescripcionListSelected;
    private List<Prescripcion_Extended> prescripcionExtendedList;
    private Prescripcion_Extended prescripcionExtendedSelected;

    private List<TransaccionPermisos> permisosList;
    private Paciente_Extended pacienteExtendSelected;
    private Paciente_Extended pacientes;
    private List<Paciente_Extended> pacienteExtendList;
    private List<Paciente_Extended> pacienteExtendListSelected;
    private List<Estructura> listaEstructuras;

    @Autowired
    private transient PacienteService pacienteService;
    @Autowired
    private transient UsuarioService usuarioService;
    @Autowired
    private transient CamaService camaService;
    @Autowired
    private transient PacienteServicioService pacienteServicioService;
    @Autowired
    private transient PacienteUbicacionService pacienteUbicacionService;
    @Autowired
    private transient VisitaService visitaService;

    private List<Medicamento> insumoList;
    private Medicamento insumoSelected;
    private Medicamento_Extended insumoExtendedSelected;

    private List<PrescripcionInsumo_Extended> prescripcionInsumoExtendedList;
    private PrescripcionInsumo_Extended prescripcionInsumoExtendedSelected;
    private PrescripcionInsumo_Extended prescripcionInsumo;

    private PrescripcionesLazy prescripcionesLazy;
    private ParamBusquedaReporte paramBusquedaReporte;

    @Autowired
    private transient MedicamentoService medicamentoService;

    private String diagnosticoCadenaBusqueda;
    @Autowired
    private transient DiagnosticoService diagnosticoService;
    private List<Diagnostico> diagnosticoList;
    private Diagnostico diagnosticoSelected;
    private Diagnostico diagnosticoDeterminado;

    private String notaMedica;
    private String firmaMedico;
    private boolean firmaValida;
    private String tipoPrescripcion;

    private List<Estructura> almacenDispensadorList;
    private String idAlmacenDispensador;
    
    @Autowired
    private transient AlmacenControlService  ctrlService;
    @Autowired
    private transient EstructuraAlmacenServicioService estructuraAlmacenServicioService;
    @Autowired
    private transient EstructuraService estructuraService;

    @Autowired
    private transient ReportesService reportesService;

    @Autowired
    private transient ServletContext servletContext;

    /**
     * Parametros de configuración a nivel Sistema
     */
    private boolean prescripcionMayor24Horas;
    private String horaCorteSurtimiento;
    private Integer noDiasCaducidad;
    private Integer permitePrescribeSinExistencias;

    private boolean funValidarFarmacoVigilancia;
    private RespuestaAlertasDTO alertasDTO;       
    
    /**
     * Consulta los permisos del usuario y obtiene lospacientes registrados
     */
    @PostConstruct
    public void init() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.init()");        
        prcLista = "prc.lista";
        errRegistroIncorrecto = "err.registro.incorrecto";
        errAccion = "err.accion";
        prcDiaMinimo = "prc.dia.minimo";
        prcNoPuedeCancelar = "prc.nopuedecancelar";
        prcRegistroErr = "prc.registro.err";
        limpia();
        
        //consultaPermisosUsuario();
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");

        usuarioSelected = sesion.getUsuarioSelected();
        prescripcionMayor24Horas = sesion.isPrescripcionMayor24Horas();
        horaCorteSurtimiento = sesion.getHoraCorteSurtimiento();
        noDiasCaducidad = sesion.getNoDiasCaducidad();
        permitePrescribeSinExistencias = sesion.getPermitePrescribeSinExistencias();
        permiso = Comunes.obtenerPermisos(Transaccion_Enum.PRESCRIPCION.getSufijo());
        obtenerAlmacenDispensador();
        obtenerEstructuras();
        paramBusquedaReporte = new ParamBusquedaReporte();
        obtenerPacientes();     
        funValidarFarmacoVigilancia = sesion.isFunValidarFarmacoVigilancia();
    }

    /**
     * Limpia las variables
     */
    private void limpia() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.limpia()");
        muestraConfirmacion = Constantes.INACTIVO;

        elementoSeleccionado = Constantes.INACTIVO;

        huboError = Constantes.INACTIVO;
        cadenaBusqueda = "";

        fechaActual = new java.util.Date();
        nombreUsuario = null;

        usuarioSelected = new Usuario();
        permisosList = new ArrayList<>();
        usuarioSelected.setPermisosList(permisosList);

        firmaValida = Constantes.INACTIVO;
        firmaMedico = null;

        tipoPrescripcion = "";
        horaCorteSurtimiento = Constantes.HORA_CORTE_SURTIMIENTO;

    }

    /**
     * Obtiene el almacen Dispensador del servicio desde donde el médico está
     * generando prescripción
     */
    private void obtenerAlmacenDispensador() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.obtenerAlmacenDispensador()");
        almacenDispensadorList = new ArrayList<>();
        String prcObtenerAlmacen = "prc.obtener.almacen";

        List<EstructuraAlmacenServicio> estructuraAlmacenSurteServicio;
        Estructura estructuraAlmacen;
        if (usuarioSelected == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcObtenerAlmacen), null);

        } else if (usuarioSelected.getIdEstructura() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcObtenerAlmacen), null);

        } else if (usuarioSelected.getIdEstructura().trim().isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcObtenerAlmacen), null);

        } else {
            try {
                boolean buscar = true;
                String idEstructura = usuarioSelected.getIdEstructura();
                Estructura e;
                while (buscar) {

                    estructuraAlmacenSurteServicio = estructuraAlmacenServicioService.obtenerLista(new EstructuraAlmacenServicio(null, idEstructura));
                    if (!estructuraAlmacenSurteServicio.isEmpty()) {
                        for(EstructuraAlmacenServicio almacen : estructuraAlmacenSurteServicio){
                            estructuraAlmacen = estructuraService.obtener(new Estructura(almacen.getIdEstructuraAlmacen()));
                            if (estructuraAlmacen.getActiva().equals(Constantes.ES_ACTIVO)
                                    && Objects.equals(estructuraAlmacen.getIdTipoAreaEstructura(), TipoAreaEstructura_Enum.ALMACEN.getValue())
                                    && Objects.equals(estructuraAlmacen.getIdTipoAlmacen(), TipoAlmacen_Enum.ALMACEN.getValue())
                                    || Objects.equals(estructuraAlmacen.getIdTipoAlmacen(), TipoAlmacen_Enum.SUBALMACEN.getValue())) {
                                almacenDispensadorList.add(estructuraAlmacen);
                                buscar = false;
                            }
                        }
                    }
                    e = estructuraService.obtener(new Estructura(idEstructura));
                    idEstructura = e.getIdEstructuraPadre();

                    if (idEstructura == null) {
                        buscar = false;
                    }
                }

            } catch (Exception ex) {
                LOGGER.error(RESOURCES.getString(prcObtenerAlmacen), ex);
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcObtenerAlmacen), null);
            }
        }
        if (almacenDispensadorList.isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcObtenerAlmacen), null);
        }

    }

    public void obtenerEstructuras() {
        listaEstructuras = new ArrayList<>();
        try {
            List<String> idsEstructura = estructuraService.obtenerIdsEstructuraJerarquica(usuarioSelected.getIdEstructura(), false);
            for (String item : idsEstructura) {
                listaEstructuras.add(estructuraService.obtenerEstructura(item));
            }
            listaEstructuras.add(estructuraService.obtenerEstructura(usuarioSelected.getIdEstructura()));

        } catch (Exception e) {
            LOGGER.error("Error al obtener las Estructuras: {}", e.getMessage());
        }
    }

    /**
     * Obtiene la lista de pacientes registrados
     */
    public void obtenerPacientes() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.obtenerPacientes()");

        boolean status = Constantes.INACTIVO;
        pacienteExtendList = new ArrayList<>();

        if (!permiso.isPuedeVer()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.transaccion"), null);
            status = Constantes.ACTIVO;

        } else {
            try {
                if (cadenaBusqueda != null
                        && cadenaBusqueda.trim().isEmpty()) {
                    cadenaBusqueda = "";
                }

// todo : Filtrar Pacientes por idEstructura del Médico
// regla: listar pacientes asignados al servicio al que está asignado el usuario
                String idUnidadMedica = null;
                if (usuarioSelected.getIdEstructura() != null
                        && !usuarioSelected.getIdEstructura().trim().isEmpty()) {
                    idUnidadMedica = usuarioSelected.getIdEstructura();
                }
// regla listar pacientes en estatus: Asignado a servicio y asignado a cama
                List<Integer> listEstatusPaciente = new ArrayList<>();
                listEstatusPaciente.add(EstatusPaciente_Enum.ASIGNADO_A_SERVICIO.getValue());
                listEstatusPaciente.add(EstatusPaciente_Enum.ASIGNADO_A_CAMA.getValue());
                paramBusquedaReporte.setNuevaBusqueda(true);
                paramBusquedaReporte.setCadenaBusqueda(cadenaBusqueda);

                prescripcionesLazy = new PrescripcionesLazy(pacienteService, paramBusquedaReporte, listEstatusPaciente, listaEstructuras, idUnidadMedica);

                LOGGER.debug("Resultados: {}", prescripcionesLazy.getTotalReg());

                status = Constantes.ACTIVO;

            } catch (Exception ex) {
                LOGGER.error(RESOURCES.getString("prc.pac.lista"), ex);
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.pac.lista"), null);
            }
        }

        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public void onRowUnselectPaciente() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.onRowUnselectPaciente()");
        pacienteExtendSelected = null;
        elementoSeleccionado = Constantes.INACTIVO;
    }

    public void onRowSelectPaciente(SelectEvent event) {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.onRowSelectPaciente()");
        pacienteExtendSelected = (Paciente_Extended) event.getObject();
        if (pacienteExtendSelected != null) {
            elementoSeleccionado = true;
        }
    }

    public void getRowKey() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.onRowSelectPaciente()");
    }

    public void onRowSelectPrescripcion(SelectEvent event) {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.onRowSelectPrescripcion()");
        prescripcionExtendedSelected = (Prescripcion_Extended) event.getObject();
        if (prescripcionExtendedSelected != null) {
            elementoSeleccionado = true;
        }
    }

    public void obtieneDatosDoctor() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.obtieneDatosDoctor()");
        try {
            usuarioSelected = usuarioService.obtenerUsuarioPorId(prescripcionExtendedSelected.getIdMedico());
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString("prc.obtener.paciente"), ex);
        }
    }

    public void obtenerCamaNombreEstructura() {
        try {
            camaselected = camaService.obtenerCamaNombreEstructura(pacienteExtendSelected.getIdPaciente());
        } catch (Exception e) {
            LOGGER.error("Error al obtener el nombre de la Cama y servicio");
        }
    }

    public void onRowUnselectPrescripcion() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.onRowUnselectPrescripcion()");
        prescripcionExtendedSelected = null;
        elementoSeleccionado = Constantes.INACTIVO;
    }

    public void onRowSelectDiagnostico(SelectEvent event) {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.onRowSelectDiagnostico()");
        diagnosticoDeterminado = (Diagnostico) event.getObject();
        if (diagnosticoDeterminado != null) {
            elementoSeleccionado = true;
        }
    }

    public void onRowUnselectDiagnostico() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.onRowUnselectDiagnostico()");
        diagnosticoDeterminado = null;
        elementoSeleccionado = Constantes.INACTIVO;
    }

    public void onRowSelectInsumo(SelectEvent event) {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.onRowSelectInsumo()");
        insumoSelected = (Medicamento) event.getObject();
        if (insumoSelected != null) {
            elementoSeleccionado = true;
        }
    }

    public void onRowUnselectInsumo() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.onRowUnselectInsumo()");
        insumoSelected = null;
        elementoSeleccionado = Constantes.INACTIVO;
    }

    public void creaRegistro() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.creaRegistro()");
        prescripcionSelected = new Prescripcion();
    }

    public void editaRegistro() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.editaRegistro()");

    }

    /**
     * Al seleccionar editar un Paciente lista las prescripciones por paciente
     */
    public void verEditarPaciente() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.verEditarPaciente()");
        prescripcionExtendedList = new ArrayList<>();

        if (!permiso.isPuedeVer()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.transaccion"), null);

        } else if (pacienteExtendSelected != null
                && pacienteExtendSelected.getIdPaciente() != null) {

            obtieneDatosPaciente();

            try {
                String idPaciente = pacienteExtendSelected.getIdPaciente();
                String idPrescripcion = null;
                Date fechaPrescripcion = null;
                String tipoConsulta = null;
                Integer recurrente = null;
                String cadena = null;
                prescripcionExtendedList.addAll(prescripcionService.obtenerPrescripcionesPorIdPaciente(idPaciente, idPrescripcion, fechaPrescripcion, tipoConsulta, recurrente, cadena));
            } catch (Exception ex) {
                LOGGER.error(RESOURCES.getString(prcLista), ex);
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcLista), null);
            }
        }

    }

    /**
     * Consulta datos del paciente seleccionado para ver, editar o crear nuevas
     * prescripciones
     */
    private void obtieneDatosPaciente() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.obtieneDatosPaciente()");
        Paciente_Extended p = pacienteExtendSelected;
        try {
            pacienteExtendSelected = pacienteService.obtenerPacienteCompletoPorId(p.getIdPaciente());
            Cama camaPaciente = camaService.obtenerCamaNombreEstructura(p.getIdPaciente());
            if (camaPaciente != null) {
                pacienteExtendSelected.setNombreCama(camaPaciente.getNombreCama());
            }
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString("prc.obtener.paciente"), ex);
        }
    }

    /**
     * Al seleccionar editar un Paciente lista las prescripciones por paciente
     *
     * @param idPaciente
     */
    public void verEditarPacienteCard(String idPaciente) {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.verEditarPacienteCard()");
        prescripcionExtendedList = new ArrayList<>();

        if (!permiso.isPuedeVer()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.transaccion"), null);

        } else if (idPaciente != null) {
            try {
                String idPrescripcion = null;
                Date fechaPrescripcion = null;
                String tipoConsulta = null;
                Integer recurrente = null;
                String cadena = null;
                prescripcionExtendedList.addAll(prescripcionService.obtenerPrescripcionesPorIdPaciente(idPaciente, idPrescripcion, fechaPrescripcion, tipoConsulta, recurrente, cadena));
            } catch (Exception ex) {
                LOGGER.error(RESOURCES.getString(prcLista), ex);
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcLista), null);
            }
        }

    }

    public void verEditarPrescripcionPorId(String idPrescripcion) {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.verEditarPrescripcionPorId()");
        prescripcionExtendedSelected = new Prescripcion_Extended();
        prescripcionExtendedSelected.setIdPrescripcion(idPrescripcion);
        verEditarPrescripcion();
    }

    /**
     * Al seleccionar y abrir una prescripción obtiene los datos
     */
    public void verEditarPrescripcion() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.verEditarPrescripcion()");
        diagnosticoList = new ArrayList<>();
        diagnosticoSelected = new Diagnostico();
        diagnosticoDeterminado = new Diagnostico();

        prescripcionInsumoExtendedList = new ArrayList<>();
        prescripcionInsumoExtendedSelected = new PrescripcionInsumo_Extended();
        prescripcionInsumo = new PrescripcionInsumo_Extended();
        insumoSelected = new Medicamento();
        prescripcionSelected = new Prescripcion();

        notaMedica = "";
        firmaMedico = "";
        tipoPrescripcion = TipoPrescripcion_Enum.NORMAL.getValue();

        editable = Constantes.INACTIVO;

        if (!permiso.isPuedeVer()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.transaccion"), null);

        } else if (prescripcionExtendedSelected == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);

        } else if (prescripcionExtendedSelected.getIdPrescripcion() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);

        } else {

            try {
                Prescripcion p = new Prescripcion();
                p.setIdPrescripcion(prescripcionExtendedSelected.getIdPrescripcion());

                prescripcionSelected = prescripcionService.obtener(p);

                if (prescripcionSelected == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.obtener"), null);

                } else {
                    if (prescripcionSelected.getTipoPrescripcion() != null) {
                        tipoPrescripcion = prescripcionSelected.getTipoPrescripcion();
                    }

                    if (prescripcionExtendedSelected.getIdEstatusPrescripcion() != null
                            && prescripcionExtendedSelected.getIdEstatusPrescripcion().equals(EstatusPrescripcion_Enum.REGRISTRADA.getValue())) {
                        editable = true;
                    }

                    notaMedica = prescripcionSelected.getComentarios();

                    if (prescripcionSelected.getIdPrescripcion() != null) {
                        String idPaciente = pacienteExtendSelected.getIdPaciente();
                        String idVisita = null;

                        diagnosticoList = diagnosticoService.obtenerPorIdPacienteIdVisitaIdPrescripcion(idPaciente, idVisita, prescripcionSelected.getIdPrescripcion());
                        Integer tipoInsumo = CatalogoGeneral_Enum.MEDICAMENTO.getValue();
                        prescripcionInsumoExtendedList = prescripcionService.obtenerInsumosPorIdPrescripcion(prescripcionSelected.getIdPrescripcion(), tipoInsumo);

                    }
                }
                diagnosticoSelected = new Diagnostico();
                insumoSelected = new Medicamento();
                prescripcionInsumoExtendedSelected = new PrescripcionInsumo_Extended();

            } catch (Exception ex) {
                LOGGER.error(RESOURCES.getString("prc.consulta"), ex);
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.consulta"), null);
            }

            if (prescripcionSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.obtener.prescripcion"), null);

            }
        }
    }

    public void validaIactivaRegistro() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.validaIactivaRegistro()");

        if (!permiso.isPuedeEditar()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errAccion), null);

        } else if (prescripcionSelected == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);
        }

    }

    public void inactivaRegistro() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.inactivaRegistro()");
        String errActualizar = "err.actualizar";
        boolean estatus = Constantes.INACTIVO;
        try {
            Prescripcion r = new Prescripcion();
            r.setIdPrescripcion(prescripcionSelected.getIdPrescripcion());
            r.setUpdateFecha(new java.util.Date());
            r.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
            estatus = prescripcionService.actualizar(r);
            if (estatus) {
                Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("ok.actualizar") + prescripcionSelected.getFolio(), null);
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errActualizar) + prescripcionSelected.getFolio(), null);
            }
        } catch (Exception ex) {
            LOGGER.error("{} {} {}", RESOURCES.getString(errActualizar), prescripcionSelected.getFolio(), ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errActualizar) + prescripcionSelected.getFolio(), null);

        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, estatus);

    }

    /**
     * Valida Eliminar una prescripción
     */
    public void validaEliminaPrescripcion() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.validaEliminaPrescripcion()");

        if (!permiso.isPuedeEliminar()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errAccion), null);

        } else if (prescripcionSelected == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);

        } else {
            // todo:    validar el estatus de la prescripción a eliminar
            eliminaPrescripcion();
        }

    }

    /**
     * Elimina la prescripción seleccionada, confirmada y validada
     */
    public void eliminaPrescripcion() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.eliminaPrescripcion()");
        String errEliminar = "err.eliminar";
        boolean estatus = Constantes.INACTIVO;
        try {
            Prescripcion r = new Prescripcion();
            r.setIdPrescripcion(prescripcionSelected.getIdPrescripcion());
            if (estatus) {
                List<Prescripcion> prescripcionesRegistradosList = new ArrayList<>();
                prescripcionesRegistradosList.addAll(prescripcionList);
                ListIterator<Prescripcion> iter = prescripcionesRegistradosList.listIterator();
                while (iter.hasNext()) {
                    if (iter.next().getIdPrescripcion().equals(prescripcionSelected.getIdPrescripcion())) {
                        iter.remove();
                    }
                }
                prescripcionList = new ArrayList<>();
                prescripcionList.addAll(prescripcionesRegistradosList);
                prescripcionSelected = new Prescripcion();

                Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("ok.eliminar") + prescripcionSelected.getFolio(), null);
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errEliminar) + prescripcionSelected.getFolio(), null);
            }
        } catch (Exception ex) {
            LOGGER.error("{} {} {}", RESOURCES.getString(errEliminar), prescripcionSelected.getFolio(), ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errEliminar) + prescripcionSelected.getFolio(), null);

        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, estatus);

    }

    /**
     *
     * @param event
     */
    public void defineNotas(AjaxBehaviorEvent event) {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.defineNotas()");
        try {
            notaMedica = (String) ((UIOutput) event.getSource()).getValue();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    /**
     * Inicializa variables antes de grear una prescripción
     */
    private boolean creaPrescripcion() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.creaPrescripcion()");
        String prcPacienteEstatusIncorrecto = "prc.paciente.estatus.incorrecto";
        boolean res = Constantes.INACTIVO;
        if (!permiso.isPuedeCrear() && !permiso.isPuedeEditar()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errAccion), null);

        } else {
            editable = Constantes.ACTIVO;
            prescripcionSelected = new Prescripcion();
            diagnosticoList = new ArrayList<>();
            diagnosticoSelected = new Diagnostico();

            prescripcionInsumoExtendedList = new ArrayList<>();
            prescripcionInsumo = new PrescripcionInsumo_Extended();
            prescripcionInsumo.setComentarios("");
            insumoSelected = new Medicamento();
            notaMedica = "";

// todo:    Validación por idEstructura
// todo:    Validación ingreso hospitalario
// todo:    Validación asignación a servicio
// todo:    Validación asignación a cama
// todo:    Validación de estatus de paciente
// regla: Solamente puede realizar prescripciones a paciente con estatus asignado a servicio o asignado a cama
            obtieneDatosPaciente();

            if (pacienteExtendSelected != null) {
                try {
                    String idPaciente = pacienteExtendSelected.getIdPaciente();
                    Visita visitaAbierta = visitaService.obtenerVisitaAbierta(new Visita(idPaciente));
                    if (visitaAbierta != null
                            && visitaAbierta.getIdVisita() != null) {
                        String idVisita = visitaAbierta.getIdVisita();
                        String idPrescripcion = null;
                        diagnosticoList.addAll(diagnosticoService.obtenerPorIdPacienteIdVisitaIdPrescripcion(idPaciente, idVisita, idPrescripcion));
                    }
                } catch (Exception ex) {
                    LOGGER.error("Error al Obtener diagnosticos previos de la visita: {}", ex.getMessage());
                }
                if (pacienteExtendSelected.getIdEstatusPaciente() <= 0) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcPacienteEstatusIncorrecto), null);

                } else if (!pacienteExtendSelected.getIdEstatusPaciente().equals(EstatusPaciente_Enum.ASIGNADO_A_CAMA.getValue())
                        && !pacienteExtendSelected.getIdEstatusPaciente().equals(EstatusPaciente_Enum.ASIGNADO_A_SERVICIO.getValue())) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcPacienteEstatusIncorrecto), null);

                } else {
                    res = Constantes.ACTIVO;
                }
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcPacienteEstatusIncorrecto), null);
            }
        }
        return res;
    }

    public void creaPrescripcionNormal() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.creaPrescripcionNormal()");
        boolean res = creaPrescripcion();
        if (res) {
            prescripcionSelected.setTipoPrescripcion(TipoPrescripcion_Enum.NORMAL.getValue());
            tipoPrescripcion = TipoPrescripcion_Enum.NORMAL.name();
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, res);
    }

    public void creaPrescripcionUrgente() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.creaPrescripcionUrgente()");
        boolean res = creaPrescripcion();
        if (res) {
            tipoPrescripcion = TipoPrescripcion_Enum.URGENTE.name();
            prescripcionSelected.setTipoPrescripcion(TipoPrescripcion_Enum.URGENTE.getValue());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, res);
    }

    public void creaPrescripcionDosisUnica() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.creaPrescripcionDosisUnica()");
        boolean res = creaPrescripcion();
        if (res) {
            tipoPrescripcion = TipoPrescripcion_Enum.DOSIS_UNICA.name();
            prescripcionSelected.setTipoPrescripcion(TipoPrescripcion_Enum.DOSIS_UNICA.getValue());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, res);
    }

    /**
     * Consulta Diagnosticos por cadena de texto mayor a 3 caracteres y retorna
     * las coincidencias
     *
     * @param cadena
     * @return
     */
    public List<Diagnostico> autocompleteDiagnostico(String cadena) {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.autocompleteDiagnostico()");
        List<Diagnostico> diagList = new ArrayList<>();

        try {
            diagList.addAll(diagnosticoService.obtenerListaAutoComplete(cadena));

        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString("prc.dia.lista"), ex);
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.dia.lista"), null);
        }
        return diagList;
    }

    public void setDiagnosticoSelected(SelectEvent e) {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.setDiagnosticoSelected()");
        diagnosticoSelected = (Diagnostico) e.getObject();
    }

    public void setDiagnosticoUnSelected() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.setDiagnosticoUnSelected()");
        diagnosticoSelected = new Diagnostico();
    }

    /**
     * Valida la agregación de un diagnostico
     */
    public void validaDiagnostico() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.validaDiagnostico()");
        boolean status = Constantes.INACTIVO;

        if (!permiso.isPuedeCrear() && !permiso.isPuedeEditar()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errAccion), null);

        } else {

            try {
                if (diagnosticoDeterminado == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);

                } else if (diagnosticoDeterminado.getIdDiagnostico() == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcDiaMinimo), null);

                } else {
                    boolean diagnosticoRegistrado = Constantes.INACTIVO;
                    for (Diagnostico item : diagnosticoList) {
                        if (item.getIdDiagnostico().equals(diagnosticoDeterminado.getIdDiagnostico())) {
                            diagnosticoRegistrado = true;
                            break;
                        }
                    }

                    if (diagnosticoRegistrado) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.dia.duplicado"), null);

                    } else {
                        boolean res = agregaDiagnostico();
                        if (res) {
                            mostrarConfirmacion();
                        }
                    }
                }

            } catch (Exception ex) {
                LOGGER.error(RESOURCES.getString("prc.dia.err"), ex);
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.dia.err"), null);
            }
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);

    }

    /**
     * Agrega un diagnostico a una prescripción
     *
     * @return
     */
    private boolean agregaDiagnostico() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.agregaDiagnostico()");

        boolean res;
        Diagnostico d = new Diagnostico();
        d.setIdDiagnostico(diagnosticoDeterminado.getIdDiagnostico());
        d.setActivo(Constantes.ACTIVO);
        try {
            diagnosticoDeterminado = diagnosticoService.obtener(d);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener el Diagnóstico: {}", ex.getMessage());
        }
        res = diagnosticoList.add(diagnosticoDeterminado);

        diagnosticoDeterminado = new Diagnostico();
        return res;
    }

    /**
     * Muestra el tab de confirmación
     */
    private void mostrarConfirmacion() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.mostrarConfirmacion()");
//        muestraConfirmacion = Constantes.INACTIVO;
//        if (diagnosticoList != null) {
//            if (!diagnosticoList.isEmpty()) {
//                if (prescripcionInsumoExtendedList != null) {
//                    if (!prescripcionInsumoExtendedList.isEmpty()) {
//                        muestraConfirmacion = true;
//                        // todo:    No funciona mostrar el tab de confirmación de una prescripción
//                    }
//                }
//            }
//        }
    }

    public void onItemSelectInsumo(SelectEvent event) {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.onItemSelectInsumo()");        
        insumoSelected = (Medicamento) event.getObject();
    }

    /**
     * Consulta de insumos por cadena de texto mayor a 3 caracteres y retorna
     * las coincidencias
     *
     * @param query
     * @return
     */
    public List<Medicamento_Extended> autocompleteInsumo(String query) {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.autocompleteInsumo()");
        List<Medicamento_Extended> insumosList = new ArrayList<>();
        try {
            insumosList.addAll(medicamentoService.obtenerInsumoyLoteDisponible(query, almacenDispensadorList, noDiasCaducidad));

        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString("prc.ins.lista"), ex);
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.ins.lista"), null);
        }
        return insumosList;
    }

    /**
     * Valida el medicamento que se agregará a una prescripción
     */
    public void validaMedicamento() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.validaMedicamento()");

        if (prescripcionSelected == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.nula"), null);

        } else if (prescripcionInsumo == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.nula"), null);

        } else if (insumoExtendedSelected == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.ins.nulo"), null);

        } else if (insumoExtendedSelected.getIdMedicamento() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.ins.nulo"), null);

        } else if (prescripcionInsumo.getDosis() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.ins.dosis"), null);

        } else if (prescripcionInsumo.getDosis().compareTo(BigDecimal.ZERO) <= 0) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.ins.dosis"), null);

        } else if (prescripcionInsumo.getFrecuencia() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.ins.frec"), null);

        } else if (prescripcionInsumo.getFrecuencia() <= 0) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.ins.frec"), null);

        } else if (prescripcionInsumo.getFechaInicio() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.ins.fechainicio"), null);

        } else {

            prescripcionInsumo.setIndicaciones(prescripcionInsumo.getIndicaciones() == null ? "" : prescripcionInsumo.getIndicaciones());

// regla: valida si se permites prescripciones por mas de 24 horas
            Integer duracionP = Constantes.PRESCRIPCION_DURACION;
            boolean valido = Constantes.INACTIVO;
            if (prescripcionMayor24Horas) {
                if (prescripcionInsumo.getDuracion() == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.ins.duracion"), null);

                } else if (prescripcionInsumo.getDuracion() <= 0) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.ins.duracion"), null);

                } else {
                    duracionP = prescripcionInsumo.getDuracion();
                    valido = Constantes.ACTIVO;
                }
            } else {
                valido = Constantes.ACTIVO;

            }

            if (valido) {
// todo:    Valida la existencia de Medicamento
// regla: prescribe solo medicamentos con existencia suficiente
                Integer cocienteEntero = 1;
                if(!insumoExtendedSelected.isIndivisible()) {
                    BigDecimal dosis = prescripcionInsumo.getDosis().setScale(0, RoundingMode.CEILING);
                    BigDecimal residuo = dosis.remainder(insumoExtendedSelected.getConcentracion());
                    BigDecimal cociente = dosis.divide(insumoExtendedSelected.getConcentracion(), 2, RoundingMode.HALF_UP);
                    cocienteEntero = cociente.intValue();
                    if(residuo.compareTo(BigDecimal.ZERO) == 1) {
                        cocienteEntero = cocienteEntero + 1;
                    }
                }
                
                Integer cantidad = ((24 / prescripcionInsumo.getFrecuencia()) * cocienteEntero) * duracionP;
                //prescripcionInsumo.setDosis((insumoExtendedSelected.getConcentracion().multiply(new BigDecimal(cocienteEntero)).setScale(0)));
                Medicamento_Extended insumo = null;
                try {
                    insumo = medicamentoService
                            .obtenerInsumoDisponiblePorIdInsumo(almacenDispensadorList, noDiasCaducidad, insumoExtendedSelected.getIdMedicamento());
                } catch (Exception exc) {
                    LOGGER.error(exc.getMessage());
                }
                if (insumo == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.insumo.insuficiente"), null);

                } else if (cantidad > (insumo.getCantidadActual() - insumo.getCantidadComprometida())) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.insumo.insuficiente"), null);

                } else {
                    prescripcionInsumo.setDuracion(duracionP);
                    prescripcionInsumo.setFechaInicio(prescripcionInsumo.getFechaInicio());

                    boolean insumoRegistrado = Constantes.INACTIVO;
                    for (PrescripcionInsumo_Extended item : prescripcionInsumoExtendedList) {
                        if (item.getIdInsumo().equals(insumoSelected.getIdMedicamento())) {
                            insumoRegistrado = true;
                            break;
                        }
                    }

                    if (insumoRegistrado) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.ins.duplicado") + insumoSelected.getClaveInstitucional(), null);

                    } else {
                        boolean res = agregaMedicamento();
                        if (res) {
                            mostrarConfirmacion();
                        }
                    }
                }
            }
        }
    }

    /**
     * Agrega un medicamento a una prescripción validado
     *
     * @return
     */
    private boolean agregaMedicamento() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.agregaMedicamento()");

        Medicamento m = null;
        try {
            m = medicamentoService.obtenerMedicamento(insumoExtendedSelected.getIdMedicamento());
        } catch (Exception e) {
            LOGGER.error(RESOURCES.getString("prc.ins.elegido"), e);
        }
        if (m == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.ins.elegido"), null);

        } else {
            prescripcionInsumo.setClaveInstitucional(m.getClaveInstitucional());
            prescripcionInsumo.setNombreCorto(m.getNombreCorto());
            prescripcionInsumo.setNombreLargo(m.getNombreLargo());
            prescripcionInsumo.setIdInsumo(m.getIdMedicamento());

// regla: Calculo de piezas antes de 24 Horas
            BigDecimal dosis = prescripcionInsumo.getDosis().setScale(0, RoundingMode.CEILING);
            Integer cant = (24 / prescripcionInsumo.getFrecuencia());
            Integer cocienteEntero = 1;
            if(!insumoExtendedSelected.isIndivisible()) {
                BigDecimal residuo = dosis.remainder(insumoExtendedSelected.getConcentracion());
                BigDecimal cociente = dosis.divide(insumoExtendedSelected.getConcentracion(), 2, RoundingMode.HALF_UP);
                cocienteEntero = cociente.intValue();
                if(residuo.compareTo(BigDecimal.ZERO) == 1) {
                    cocienteEntero = cocienteEntero + 1;
                }
                prescripcionInsumo.setCantidad24Horas(cant * cocienteEntero);
            } else {
                prescripcionInsumo.setCantidad24Horas(cocienteEntero);
            }
            prescripcionInsumoExtendedList.add(prescripcionInsumo);

//            Limpia valores
            insumoExtendedSelected = new Medicamento_Extended();
            prescripcionInsumo = new PrescripcionInsumo_Extended();
            return true;
        }
        return Constantes.INACTIVO;
    }

    /**
     * valida la eliminación de un diagnóstico agregado a una prescripción
     *
     * @param idDiagnostico
     */
    public void validaEliminaDiagnostico(String idDiagnostico) {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.validaEliminaDiagnostico()");
        if (!permiso.isPuedeCrear() || !permiso.isPuedeEditar()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.transaccion"), null);

        } else if (idDiagnostico == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);

        } else if (diagnosticoList == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);

        } else if (diagnosticoList.isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);

        } else {
            eliminaDiagnostico(idDiagnostico);
            mostrarConfirmacion();
        }
    }

    /**
     * Elimina un diagnostico de la prescripción
     *
     * @param idDiagnostico
     * @return
     */
    private boolean eliminaDiagnostico(String idDiagnostico) {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.eliminaDiagnostico()");
        List<Diagnostico> insumosRegistradosList = new ArrayList<>();
        insumosRegistradosList.addAll(diagnosticoList);
        ListIterator<Diagnostico> iter = insumosRegistradosList.listIterator();
        while (iter.hasNext()) {
            if (iter.next().getIdDiagnostico().equals(idDiagnostico)) {
                iter.remove();
            }
        }
        diagnosticoList = new ArrayList<>();
        diagnosticoList.addAll(insumosRegistradosList);
        diagnosticoDeterminado = new Diagnostico();
        diagnosticoSelected = new Diagnostico();
        return true;
    }

    /**
     * Valida la eliminación de un medicamento de una prescripción
     *
     * @param idMedicamento
     */
    public void validaEliminaInsumo(String idMedicamento) {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.validaEliminaInsumo()");
        if (!permiso.isPuedeCrear() || !permiso.isPuedeEditar()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.transaccion"), null);

        } else if (idMedicamento == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);

        } else if (prescripcionInsumoExtendedList == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);

        } else if (prescripcionInsumoExtendedList.isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);

        } else {
            eliminaMedicamento(idMedicamento);
            mostrarConfirmacion();
        }
    }

    /**
     * Elimina un medicamento de una prescripción
     *
     * @param idDiagnostico
     * @return
     */
    private boolean eliminaMedicamento(String idInsumo) {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.eliminaMedicamento()");
        List<PrescripcionInsumo_Extended> insumosRegistradosList = new ArrayList<>();
        insumosRegistradosList.addAll(prescripcionInsumoExtendedList);
        ListIterator<PrescripcionInsumo_Extended> iter = insumosRegistradosList.listIterator();
        while (iter.hasNext()) {
            if (iter.next().getIdInsumo().equals(idInsumo)) {
                iter.remove();
            }
        }
        prescripcionInsumoExtendedList = new ArrayList<>();
        prescripcionInsumoExtendedList.addAll(insumosRegistradosList);
        prescripcionInsumoExtendedSelected = new PrescripcionInsumo_Extended();
        return true;
    }

    /**
     * Valida la Cancelación de una prescripción metodo por idPrescripción
     */
    public void validaCancelarPrescripcionPorId(String idPrescripcion) {
        LOGGER.error("mx.mc.magedbean.PrescripcionMB.validaCancelarPrescripcionPorId()");
        if (idPrescripcion == null || idPrescripcion.trim().isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcNoPuedeCancelar), null);

        } else {
            prescripcionExtendedSelected = new Prescripcion_Extended();
            prescripcionExtendedSelected.setIdPrescripcion(idPrescripcion);
            validaCancelarPrescripcion();
        }
    }

    /**
     * Valida estatus y objetos necesarios para cancelar una prescripción
     */
    public void validaCancelarPrescripcion() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.validaCancelarPrescripcion()");

        boolean status = Constantes.INACTIVO;
        if (!permiso.isPuedeEditar()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.transaccion"), null);

        } else if (prescripcionExtendedSelected == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcNoPuedeCancelar), null);

        } else if (prescripcionExtendedSelected.getIdPrescripcion() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcNoPuedeCancelar), null);

        } else {
            try {
                Prescripcion p = new Prescripcion();
                p.setIdPrescripcion(prescripcionExtendedSelected.getIdPrescripcion());

                prescripcionSelected = prescripcionService.obtener(p);

                if (prescripcionSelected == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcNoPuedeCancelar), null);

                } else if (prescripcionSelected.getIdEstatusPrescripcion().equals(EstatusPrescripcion_Enum.FINALIZADA.getValue())
                        || prescripcionSelected.getIdEstatusPrescripcion().equals(EstatusPrescripcion_Enum.CANCELADA.getValue())) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcNoPuedeCancelar), null);

                } else {
                    status = cancelarPrescripcion();
                    if (status) {
                        for (Prescripcion_Extended item : prescripcionExtendedList) {
                            if (item.getIdPrescripcion().equals(prescripcionSelected.getIdPrescripcion())) {
                                item.setIdEstatusPrescripcion(EstatusPrescripcion_Enum.CANCELADA.getValue());
                                item.setEstatusPrescripcion(EstatusPrescripcion_Enum.CANCELADA.toString());
                            }
                        }
                    }
                }

            } catch (Exception ex) {
                LOGGER.error(RESOURCES.getString(prcNoPuedeCancelar), ex);
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcNoPuedeCancelar), null);
            }
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);

    }

    /**
     * Cancela una prescripción así como todos los surtimientos dependientes de
     * esta prescripción y todos medicamentos de la prescripción
     *
     * @return
     */
    private boolean cancelarPrescripcion() {
        LOGGER.error("mx.mc.magedbean.PrescripcionMB.cancelarPrescripcion()");
        boolean res = Constantes.INACTIVO;
        try {
            res = prescripcionService.cancelarPrescripcion(prescripcionSelected.getIdPrescripcion(), usuarioSelected.getIdUsuario(), new java.util.Date(), EstatusPrescripcion_Enum.CANCELADA.getValue(), EstatusGabinete_Enum.CANCELADA.getValue(), EstatusSurtimiento_Enum.CANCELADO.getValue());

        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString(prcNoPuedeCancelar), ex);
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcNoPuedeCancelar), null);
        }
        return res;
    }

    /**
     * Valida la existencia de una cadea de texto como firma
     */
    public void validaPrescripcion() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.validaFirma()");

        boolean status = Constantes.INACTIVO;

        if (!permiso.isPuedeCrear() || !permiso.isPuedeEditar()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.transaccion"), null);

        } else if (notaMedica == null || notaMedica.trim().isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.nota"), null);

        } else if (diagnosticoList == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcDiaMinimo), null);

        } else if (diagnosticoList.isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcDiaMinimo), null);

        } else if (prescripcionInsumoExtendedList == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.ins.minimo"), null);

        } else if (prescripcionInsumoExtendedList.isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.ins.minimo"), null);

        } else {            
            status = Constantes.ACTIVO;
            validarFarmacovigilancia();
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }
    
    public void validarFarmacovigilancia() {
        boolean existenAlertas = false;
        try {
            if(funValidarFarmacoVigilancia) {
                ParamBusquedaAlertaDTO  alertaDTO= new ParamBusquedaAlertaDTO();
                //El folio no se manda debido a que todavia no se registra en Mus
                //alertaDTO.setFolioPrescripcion(prescripcionSelected.getFolio());
                alertaDTO.setNumeroPaciente(pacienteExtendSelected.getPacienteNumero());
                //alertaDTO.setNumeroVisita(surtimientoExtendedSelected.get|);
                alertaDTO.setNumeroMedico(usuarioSelected.getCedProfesional());

                List<String> listaDiagnosticos= new ArrayList<>();
                for(Diagnostico unDiagnostico: diagnosticoList){
                    listaDiagnosticos.add(unDiagnostico.getClave());
                }
                alertaDTO.setListaDiagnosticos(listaDiagnosticos);

                List<MedicamentoDTO> listaMedicametosDTO= new ArrayList<>();
                
                for (PrescripcionInsumo_Extended item : prescripcionInsumoExtendedList) {
                    MedicamentoDTO medicamentoDTO = new MedicamentoDTO();
                    medicamentoDTO.setClaveMedicamento(item.getClaveInstitucional());
                    medicamentoDTO.setDosis(item.getDosis());
                    medicamentoDTO.setDuracion(item.getDuracion());
                    medicamentoDTO.setFrecuencia(item.getFrecuencia());
                    
                    listaMedicametosDTO.add(medicamentoDTO);
                }
                
                alertaDTO.setListaMedicametos(listaMedicametosDTO);

                ObjectMapper Obj = new ObjectMapper();
                String json = Obj.writeValueAsString(alertaDTO);
                ReaccionesAdversas cs = new ReaccionesAdversas(servletContext);
                Response respMus = cs.validaFarmacoVigilancia(json);

                if(respMus!=null){
                    if(respMus.getStatus()==200){
                        alertasDTO = parseResponseDTO(respMus.getEntity().toString());
                        if(alertasDTO.getCodigo().equals("06")) {
                            existenAlertas=true;
                        } else {
                            existenAlertas=false;
                        }
                    }
                }
            } else {
                existenAlertas=false;
            }
        } catch(Exception ex) {
            LOGGER.error("Error al momento de validar la existencia de alertas de farmacovigilancia:    " + ex.getMessage());
        }        
        PrimeFaces.current().ajax().addCallbackParam(Constantes.EXISTE_ALERTA, existenAlertas);
        
    }
    
    private RespuestaAlertasDTO parseResponseDTO(String request){
        RespuestaAlertasDTO dto= new RespuestaAlertasDTO();
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode params = mapper.readTree(request);
            
            dto.setCodigo(params.hasNonNull("codigo") ? params.get("codigo").asText() : null);
            dto.setDescripcion(params.hasNonNull("descripcion") ? params.get("descripcion").asText() : null);
            
            List<AlertaFarmacovigilancia> listaAlerta = new ArrayList<>();
            if (params.hasNonNull("listaAlertaFarmacovigilancia")) {
                for (Iterator<JsonNode> iter = params.get("listaAlertaFarmacovigilancia").elements(); iter.hasNext();) {
                    JsonNode node = iter.next();
                    AlertaFarmacovigilancia item = new AlertaFarmacovigilancia();
                    item.setNumero(node.hasNonNull("numero") ? node.get("numero").asInt():null);
                    item.setFactor1(node.hasNonNull("factor1") ? node.get("factor1").asText() : null);
                    item.setFactor2(node.hasNonNull("factor2") ? node.get("factor2").asText() : null);
                    item.setRiesgo(node.hasNonNull("riesgo") ? node.get("riesgo").asText() : null);
                    item.setTipo(node.hasNonNull("tipo") ? node.get("tipo").asText() : null);
                    item.setOrigen(node.hasNonNull("origen") ? node.get("origen").asText() : null);
                    item.setClasificacion(node.hasNonNull("clasificacion") ? node.get("clasificacion").asText() : null);
                    item.setMotivo(node.hasNonNull("motivo") ? node.get("motivo").asText() : null); 
                    if(node.get("riesgo").asText().equals("Alto")) {
                        item.setColorRiesgo("#FDCAE1");
                    } else {
                        if(node.get("riesgo").asText().equals("Medio")) {
                            item.setColorRiesgo("#F4FAB4");
                        }else {
                            if(node.get("riesgo").asText().equals("Bajo")) {
                                item.setColorRiesgo("#D8F8E1");
                            } else {
                                item.setColorRiesgo("");
                            }
                        }
                    }

                    listaAlerta.add(item);                    
                }
            }
            dto.setListaAlertaFarmacovigilancia(listaAlerta);
            
            List<ValidacionNoCumplidaDTO> listaValidacion = new ArrayList<>();
            if(params.hasNonNull("ValidacionNoCumplidas")){
                for (Iterator<JsonNode> iter = params.get("ValidacionNoCumplidas").elements(); iter.hasNext();) {
                    JsonNode node = iter.next();
                    ValidacionNoCumplidaDTO item = new ValidacionNoCumplidaDTO();
                    item.setCodigo(node.hasNonNull("codigo") ? node.get("codigo").asText():null);
                    item.setDescripcion(node.hasNonNull("descripcion") ? node.get("descripcion").asText():null);
                    
                    listaValidacion.add(item);
                }
            }
            dto.setValidacionNoCumplidas(listaValidacion);
            
        }catch(IOException ex){
            LOGGER.error("Ocurrio un error al parsear el request:",ex);
        }
        return dto;
    }

    public void rechazarValidacionPrescripcion(){
        LOGGER.trace("mx.mc.magedbean.prescripcionMB.rechazarValidacionPrescripcion()");    
        boolean status = Constantes.INACTIVO;
        try{
            
        }catch(Exception ex){
            LOGGER.error("ocurrio un error en: rechazarValidacionPrescripcion    ",ex);
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }
    
    /**
     * Guarda prescripción despues de validada
     */
    public void guardarPrescripcion() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.guardarPrescripcion()");
        boolean status;
        if (!CustomWebSecurityConfigurerAdapter.customPasswordEncoder().matches(firmaMedico, usuarioSelected.getClaveAcceso())) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.firma.incorrecta"), null);
            return;
        }
        firmaValida = true;
        prescripcionSelected.setIdEstatusPrescripcion(EstatusPrescripcion_Enum.PROGRAMADA.getValue());
        Integer idEstatusGabinete = EstatusGabinete_Enum.OK.getValue();
        if (usuarioSelected.getIdEstructura() != null) {
            try {
                Estructura e = estructuraService.obtenerEstructura(usuarioSelected.getIdEstructura());
                if (e != null
                        && e.getEnvioHL7()) {
                    idEstatusGabinete = EstatusGabinete_Enum.PENDIENTE.getValue();
                }
            } catch (Exception ex) {
                LOGGER.error("Error al buscar si es gabinete o no: {}", ex.getMessage());
            }
        }
        prescripcionSelected.setIdEstatusGabinete(idEstatusGabinete);
        if (prescripcionSelected.getIdPrescripcion() == null) {
            status = registraPrescripcion();
        } else {
            status = editaPrescripcion();
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    /**
     * Registra la prescripción con diagnósticos y medicamentos, y notas Médicas
     */
    private boolean registraPrescripcion() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.registraPrescripcion()");
        String prcVisita = "prc.visita";
        String prcServicio = "prc.servicio";
        boolean res = Constantes.INACTIVO;
        if (!permiso.isPuedeCrear()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errAccion), null);

        } else {

            Visita v = null;
            try {
                v = visitaService.obtenerVisitaAbierta(new Visita(pacienteExtendSelected.getIdPaciente()));
            } catch (Exception ex) {
                LOGGER.error(RESOURCES.getString(prcVisita), ex);
            }
// regla: paciente debe tener visita abierta
            if (v == null || v.getIdVisita() == null || v.getIdVisita().isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcVisita), null);
                return Constantes.INACTIVO;
            }

            PacienteServicio ps = null;
            try {
                ps = pacienteServicioService.obtener(new PacienteServicio(v.getIdVisita()));
            } catch (Exception ex) {
                LOGGER.error(RESOURCES.getString(prcServicio), ex);
            }
// regla: paciente debe estar asignado a un servicio
            if (ps == null || ps.getIdPacienteServicio() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcServicio), null);
                return Constantes.INACTIVO;
            }
            try {

                prescripcionSelected.setIdEstructura(usuarioSelected.getIdEstructura());
                prescripcionSelected.setIdPacienteServicio(ps.getIdPacienteServicio());
                // todo:    asignar nomenclatura del folio
//                Folios f = folioService.obtenerPrefixPorDocument(TipoDocumento_Enum.PRESCRIPCION.getValue());
//                String folio = Comunes.generaFolio(f);
                prescripcionSelected.setIdPrescripcion(Comunes.getUUID());
//                prescripcionSelected.setIdPrescripcion(folio);
                prescripcionSelected.setFolio(Comunes.getUUID().substring(1, 8));
                prescripcionSelected.setFechaPrescripcion(new java.util.Date());
                prescripcionSelected.setFechaFirma(new java.util.Date());
                prescripcionSelected.setTipoConsulta(TipoConsulta_Enum.CONSULTA_INTERNA.getValue());
                prescripcionSelected.setIdMedico(usuarioSelected.getIdUsuario());
                prescripcionSelected.setRecurrente(Constantes.INACTIVO);
                prescripcionSelected.setComentarios(notaMedica);
                prescripcionSelected.setIdEstatusGabinete(EstatusGabinete_Enum.PENDIENTE.getValue());
                prescripcionSelected.setInsertFecha(new java.util.Date());
                prescripcionSelected.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                // todo agregar la unidad jerarquica a la que pertenece la prescripcion
//                prescripcionSelected.setIdEstructura(usuarioSelected.getIdEstructura());

                List<DiagnosticoPaciente> diagnosticoPacienteList = new ArrayList<>();
                DiagnosticoPaciente diagnosticoPaciente;
                for (Diagnostico ite : diagnosticoList) {
                    diagnosticoPaciente = new DiagnosticoPaciente();
                    diagnosticoPaciente.setIdDiagnosticoPaciente(Comunes.getUUID());
                    diagnosticoPaciente.setIdPrescripcion(prescripcionSelected.getIdPrescripcion());
                    diagnosticoPaciente.setFechaDiagnostico(new java.util.Date());
                    diagnosticoPaciente.setIdUsuarioDiagnostico(usuarioSelected.getIdUsuario());
                    diagnosticoPaciente.setIdDiagnostico(ite.getIdDiagnostico());
                    diagnosticoPaciente.setFechaFinDiagnostico(null);
                    diagnosticoPaciente.setIdUsuarioDiagnosticoTratado(null);
                    diagnosticoPaciente.setInsertFecha(new java.util.Date());
                    diagnosticoPaciente.setIdEstatusDiagnostico(EstatusDiagnostico_Enum.DIAGNOSTICADO.getValue());                    
                    diagnosticoPaciente.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                    diagnosticoPaciente.setUpdateIdUsuario(null);
                    diagnosticoPaciente.setUpdateFecha(null);                    
                    diagnosticoPacienteList.add(diagnosticoPaciente);
                }

                List<PrescripcionInsumo> prescInsumoList = new ArrayList<>();                
                List<String> itemList = new ArrayList<>();
                PrescripcionInsumo prescInsumo;
                for (PrescripcionInsumo_Extended item : prescripcionInsumoExtendedList) {
                    prescInsumo = new PrescripcionInsumo();
                    prescInsumo.setIdPrescripcionInsumo(Comunes.getUUID());
                    prescInsumo.setIdPrescripcion(prescripcionSelected.getIdPrescripcion());
                    prescInsumo.setIdInsumo(item.getIdInsumo());
                    prescInsumo.setFechaInicio(item.getFechaInicio());
                    prescInsumo.setDosis(item.getDosis());
                    prescInsumo.setFrecuencia(item.getFrecuencia());
                    prescInsumo.setDuracion(item.getDuracion());
                    prescInsumo.setComentarios(null);
                    prescInsumo.setIdEstatusPrescripcion(prescripcionSelected.getIdEstatusPrescripcion());
                    prescInsumo.setIndicaciones(item.getIndicaciones());
                    prescInsumo.setIdEstatusMirth(prescripcionSelected.getIdEstatusGabinete());
                    prescInsumo.setInsertFecha(new java.util.Date());
                    prescInsumo.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                    prescInsumo.setUpdateFecha(null);
                    prescInsumo.setUpdateIdUsuario(null);
                    prescInsumo.setIdTipoIngrediente(0);
//                    prescInsumo.setVelocidad(0);
//                    prescInsumo.setRitmo(0);
                    prescInsumo.setPerfusionContinua(0);
                    prescInsumo.setIdUnidadConcentracion(0);
                                        
                    prescInsumoList.add(prescInsumo);
                    itemList.add(item.getIdInsumo());
                }

                // Buscar el almacen que surtirá la prescripción
                idAlmacenDispensador = almacenDispensadorList.size()>1 ? BuscarAlmacenDispensador(itemList) : almacenDispensadorList.get(0).getIdEstructura(); 
                
                List<Surtimiento> surtimientoList = calculaSurtimientos(prescInsumoList);
                List<AlmacenInsumoComprometido> comprometidoLis = calculaComprometidos(prescInsumoList);

                res = prescripcionService.registrarPrescripcion(prescripcionSelected, diagnosticoPacienteList, prescInsumoList, surtimientoList, comprometidoLis);

            } catch (Exception ex) {
                LOGGER.error(RESOURCES.getString(prcRegistroErr), ex);
                prescripcionSelected.setIdPrescripcion(null);
            }
        }
        if (!res) {
            LOGGER.error(RESOURCES.getString(prcRegistroErr));
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcRegistroErr), null);

        } else {
            verEditarPaciente();
            Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("prc.registro.ok"), null);

        }
        return res;
    }

    private String BuscarAlmacenDispensador(List<String> itemList){
        String idAlmacen="";
        try {
            List<AlmacenControl_Extended> ctrlList =  ctrlService.obtenerTotalInsumoAlmacen(almacenDispensadorList, itemList);
            for(AlmacenControl_Extended item : ctrlList){
                if(item.getTotal()==itemList.size()){
                    idAlmacen=item.getIdAlmacen();
                    break;
                }
            }
            if(idAlmacen.isEmpty()){
                Estructura almacen = almacenDispensadorList.stream().filter(p->p.getIdTipoAlmacen().equals(TipoAlmacen_Enum.ALMACEN.getValue())).findAny().orElse(null);
                idAlmacen=almacen.getIdEstructura();
            }
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString(prcRegistroErr), ex);
        }
        return idAlmacen;
    }
    
    private List<Surtimiento> calculaSurtimientos(List<PrescripcionInsumo> prescInsumoList) {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.calculaSurtimientos()");
        List<Surtimiento> surtimientoList = new ArrayList<>();

        Collections.sort(prescInsumoList, (Object o1, Object o2) -> {
            PrescripcionInsumo p1 = (PrescripcionInsumo) o1;
            PrescripcionInsumo p2 = (PrescripcionInsumo) o2;
            return p1.getDuracion().compareTo(p2.getDuracion());
        });
        PrescripcionInsumo pi = Collections.max(prescInsumoList, (Object o1, Object o2) -> {
            PrescripcionInsumo p1 = (PrescripcionInsumo) o1;
            PrescripcionInsumo p2 = (PrescripcionInsumo) o2;
            return p1.getDuracion().compareTo(p2.getDuracion());
        });

        Integer numeroSurtimientos = 0;
        Medicamento medic = null;
        try {
            medic = medicamentoService.obtenerPorIdMedicamento(pi.getIdInsumo());
        } catch(Exception ex) {
            LOGGER.error("error al buscar el medicamento por id PrescripcionMB linea 1671: " + ex.getMessage());
        }
        if(medic.isIndivisible()) {
            numeroSurtimientos = 1;
        } else {
            numeroSurtimientos = pi.getDuracion();
        }
        

        Integer cont = 0;
        Surtimiento s;
        for (Integer i = 0; i < numeroSurtimientos; i++) {
            s = new Surtimiento();
            s.setIdSurtimiento(Comunes.getUUID());
            s.setIdEstructuraAlmacen(idAlmacenDispensador);
            s.setIdPrescripcion(prescripcionSelected.getIdPrescripcion());
            s.setFechaProgramada(FechaUtil.sumarRestarDiasFecha(new java.util.Date(), cont));
            s.setFolio(Comunes.getUUID().substring(1, 8));
            s.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
            s.setIdEstatusMirth(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
            s.setInsertFecha(new java.util.Date());
            s.setInsertIdUsuario(usuarioSelected.getIdUsuario());
            s.setDetalle(new ArrayList<>());
            surtimientoList.add(s);
            cont++;
        }

        Collections.sort(surtimientoList, (Object o1, Object o2) -> {
            Surtimiento s1 = (Surtimiento) o1;
            Surtimiento s2 = (Surtimiento) o2;
            return s1.getFechaProgramada().compareTo(s2.getFechaProgramada());
        });

        cont = 0;
        SurtimientoInsumo si;
        List<SurtimientoInsumo> siList;
        Integer noDias;
        Integer cantidadSolicitada;

// regla: se calculan los surtimientos
        for (PrescripcionInsumo item : prescInsumoList) {
// regla: si se usan prescripciones mayores a 24 horas
            if (prescripcionMayor24Horas) {
                noDias = item.getDuracion();
            } else {
                noDias = Constantes.PRESCRIPCION_DURACION;
            }

            if (noDias > 0) {
                for (Surtimiento item2 : surtimientoList) {                    
                    if (cont >= noDias) {
                        break;
                    } else {
                        Medicamento medicamento = null;
// regla: se calcula el total solicitado por 24 horas
                        BigDecimal dosis = item.getDosis().setScale(0, RoundingMode.CEILING);
                        try {
                            medicamento = medicamentoService.obtenerPorIdMedicamento(item.getIdInsumo());
                        } catch(Exception ex) {
                            LOGGER.error("Error al buscar el medicaemnto por id  " +ex.getMessage() );
                        }
                        Integer cocienteEntero = 0;
                        if(medicamento.isIndivisible()) {
                            cantidadSolicitada = 1;
                            cont = noDias;
                            cocienteEntero = 1;
                        } else {
                            BigDecimal residuo = dosis.remainder(medicamento.getConcentracion());
                            BigDecimal cociente = dosis.divide(medicamento.getConcentracion(), 2, RoundingMode.HALF_UP);
                            cocienteEntero = cociente.intValue();
                            if(residuo.compareTo(BigDecimal.ZERO) == 1) {
                                cocienteEntero = cocienteEntero + 1;
                            }
                            cantidadSolicitada = (24 / item.getFrecuencia() * cocienteEntero);
                        }                        
                        /* Se comentan lineas de codigo debido a que por el momento no se utiliza la fecha hora corte, se generaran las prescripciones por 24hrs
// regla: se calcula el numero de horas restantes para el siguiente corte de surtimiento a partir de la hora de inicio tratamiento
                        Integer numeroHorasRestantes = FechaUtil.horasFaltantesParaCorte(horaCorteSurtimiento, item.getFechaInicio());
                        if (cont == 0) {
// regla: siempre se registra un surtimiento inmediato con al menos 1 dosis                  
                            if (numeroHorasRestantes < item.getFrecuencia()) {
                                cantidadSolicitada = cocienteEntero; // dosis.intValue();
                            } else {
                                cantidadSolicitada = cocienteEntero + ((numeroHorasRestantes / item.getFrecuencia())) * cocienteEntero;  // dosis.intValue() + ((numeroHorasRestantes / item.getFrecuencia()) * dosis.intValue());
                            }
                        } else {
// regla: se calculan las cantidades faltantes de surtir sin incluir todo el último día para completar solo 24 horas
                            int calculo = ((24 - numeroHorasRestantes) / item.getFrecuencia());
                            if (calculo == 0) {
                                calculo = 1;
                            }
                            cantidadSolicitada = calculo * cocienteEntero; // dosis.intValue();
                        }                         
*/                    
                        Estructura estructura = null;
                        try {
                            estructura = estructuraService.obtenerEstructuraHl7(usuarioSelected.getIdEstructura());
                        } catch(Exception ex) {
                            LOGGER.error("Error al buscar la estructura");
                        }
                      
                        if(estructura != null) {
                            Integer numeroSurtInsumos = cantidadSolicitada / cocienteEntero;
                            Date fechaProgramada = null;
                            for(int numMedicacion = 1; numMedicacion <= numeroSurtInsumos; numMedicacion++) {                                
                                if(numMedicacion == 1) {
                                    fechaProgramada = item2.getFechaProgramada();
                                } else {
                                    fechaProgramada = FechaUtil.sumarRestarHorasFecha(fechaProgramada, item.getFrecuencia());
                                }
                                siList = new ArrayList<>();
                                siList.addAll(item2.getDetalle());
                                si = new SurtimientoInsumo();
                                si.setIdSurtimientoInsumo(Comunes.getUUID());
                                si.setIdSurtimiento(item2.getIdSurtimiento());
                                si.setIdPrescripcionInsumo(item.getIdPrescripcionInsumo());
                                si.setFechaProgramada(fechaProgramada);
                                si.setCantidadSolicitada(cocienteEntero);
                                si.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                                si.setIdEstatusMirth(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                                si.setNumeroMedicacion(numMedicacion);
                                si.setInsertFecha(new java.util.Date());
                                si.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                                siList.add(si);
                                item2.setDetalle(siList);
                            }
                        } else {
                            siList = new ArrayList<>();
                            siList.addAll(item2.getDetalle());
                            si = new SurtimientoInsumo();
                            si.setIdSurtimientoInsumo(Comunes.getUUID());
                            si.setIdSurtimiento(item2.getIdSurtimiento());
                            si.setIdPrescripcionInsumo(item.getIdPrescripcionInsumo());
                            si.setFechaProgramada(item2.getFechaProgramada());
                            si.setCantidadSolicitada(cantidadSolicitada);
                            si.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                            si.setIdEstatusMirth(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                            si.setInsertFecha(new java.util.Date());
                            si.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                            siList.add(si);
                            item2.setDetalle(siList);
                        }                        
                    }
                    cont++;
                }
                cont = 0;
            }
        }

        return surtimientoList;
    }

    private List<AlmacenInsumoComprometido> calculaComprometidos(List<PrescripcionInsumo> prescInsumoList) {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.calculaComprometidos()");
        List<AlmacenInsumoComprometido> comprometidoLis = new ArrayList<>();
        try {
            AlmacenInsumoComprometido aic;
            Integer cantidadComprometida = 0;
            for (PrescripcionInsumo item : prescInsumoList) {
                BigDecimal dosis = item.getDosis().setScale(0, RoundingMode.CEILING);
                cantidadComprometida = ((24 / item.getFrecuencia()) * dosis.intValue()) * item.getDuracion();
                aic = new AlmacenInsumoComprometido();
                aic.setIdEstructura(idAlmacenDispensador);
                aic.setIdInsumo(item.getIdInsumo());
                aic.setCantidadComprometida(cantidadComprometida);
                comprometidoLis.add(aic);
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        return comprometidoLis;
    }

    private boolean editaPrescripcion() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.editaPrescripcion()");
        boolean res = Constantes.INACTIVO;
        if (!permiso.isPuedeEditar()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errAccion), null);

        } else {

            try {
                // todo:    asignar nomenclatura del folio
//                prescripcionSelected.setFolio(Comunes.getUUID().substring(1, 8));
                prescripcionSelected.setFechaFirma(new java.util.Date());
                prescripcionSelected.setIdMedico(usuarioSelected.getIdUsuario());
                prescripcionSelected.setComentarios(notaMedica);
                prescripcionSelected.setUpdateFecha(new java.util.Date());
                prescripcionSelected.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
                // todo agregar la unidad jerarquica a la que pertenece la prescripcion
//                prescripcionSelected.setIdEstructura(usuarioSelected.getIdEstructura());

                List<DiagnosticoPaciente> diagnosticoPacienteList = new ArrayList<>();
                DiagnosticoPaciente diagnosticoPaciente;
                for (Diagnostico item : diagnosticoList) {
                    diagnosticoPaciente = new DiagnosticoPaciente();
                    diagnosticoPaciente.setIdDiagnosticoPaciente(Comunes.getUUID());
                    diagnosticoPaciente.setIdPrescripcion(prescripcionSelected.getIdPrescripcion());
                    diagnosticoPaciente.setFechaDiagnostico(new java.util.Date());
                    diagnosticoPaciente.setIdUsuarioDiagnostico(usuarioSelected.getIdUsuario());
                    diagnosticoPaciente.setIdDiagnostico(item.getIdDiagnostico());
                    diagnosticoPaciente.setFechaFinDiagnostico(null);
                    diagnosticoPaciente.setIdUsuarioDiagnosticoTratado(null);
                    diagnosticoPaciente.setIdEstatusDiagnostico(EstatusDiagnostico_Enum.DIAGNOSTICADO.getValue());
                    diagnosticoPaciente.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                    diagnosticoPaciente.setInsertFecha(new java.util.Date());                    
                    diagnosticoPaciente.setUpdateFecha(null);
                    diagnosticoPaciente.setUpdateIdUsuario(null);
                    diagnosticoPacienteList.add(diagnosticoPaciente);
                }

                List<PrescripcionInsumo> prescInsumoList = new ArrayList<>();
                List<String> itemList = new ArrayList<>();
                PrescripcionInsumo prescInsumo;
                for (PrescripcionInsumo_Extended item : prescripcionInsumoExtendedList) {
                    prescInsumo = new PrescripcionInsumo();
                    prescInsumo.setIdPrescripcionInsumo(Comunes.getUUID());
                    prescInsumo.setIdPrescripcion(prescripcionSelected.getIdPrescripcion());
                    prescInsumo.setIdInsumo(item.getIdInsumo());
                    prescInsumo.setFechaInicio(item.getFechaInicio());
                    prescInsumo.setDosis(item.getDosis());
                    prescInsumo.setFrecuencia(item.getFrecuencia());
                    prescInsumo.setDuracion(item.getDuracion());
                    prescInsumo.setComentarios(null);
                    prescInsumo.setIdEstatusPrescripcion(prescripcionSelected.getIdEstatusPrescripcion());
                    prescInsumo.setIdEstatusMirth(prescripcionSelected.getIdEstatusGabinete());
                    prescInsumo.setIndicaciones(item.getIndicaciones());
                    prescInsumo.setInsertFecha(new java.util.Date());
                    prescInsumo.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                    prescInsumo.setUpdateFecha(null);
                    prescInsumo.setUpdateIdUsuario(null);
                    
                    prescInsumoList.add(prescInsumo);
                    itemList.add(item.getIdInsumo());
                }
                
                // Buscar el almacen que surtirá la prescripción
                idAlmacenDispensador = almacenDispensadorList.size()>1 ? BuscarAlmacenDispensador(itemList) : almacenDispensadorList.get(0).getIdEstructura(); 
                
                List<Surtimiento> surtimientoList = calculaSurtimientos(prescInsumoList);
                List<AlmacenInsumoComprometido> comprometidoLis = calculaComprometidos(prescInsumoList);

                res = prescripcionService.editarPrescripcion(prescripcionSelected, diagnosticoPacienteList, prescInsumoList, surtimientoList, comprometidoLis);

            } catch (Exception ex) {
                LOGGER.error(RESOURCES.getString(prcRegistroErr), ex);
            }
        }
        if (!res) {
            LOGGER.error(RESOURCES.getString(prcRegistroErr));
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcRegistroErr), null);

        } else {
            verEditarPaciente();
            Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("prc.registro.ok"), null);

        }
        return res;
    }

    public void imprimirPorId(String idPrescripcion) throws Exception {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.imprimir()");
        prescripcionExtendedSelected = (Prescripcion_Extended) prescripcionService.obtener(new Prescripcion(idPrescripcion));
        imprimir();
    }

    public void imprimir() throws Exception {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.imprimir()");
        verEditarPrescripcion();
        obtenerCamaNombreEstructura();
        boolean status = Constantes.INACTIVO;
        try {

            obtieneDatosDoctor();
            pacientes = new Paciente_Extended();
            pacientes = pacienteService.obtenerNombreEstructurabyIdpaciente(pacienteExtendSelected.getIdPaciente());

            byte[] buffer = reportesService.imprimePrescripcion(pacientes, camaselected, usuarioSelected, pacienteExtendSelected, prescripcionExtendedSelected);
            if (buffer != null) {
                status = Constantes.ACTIVO;
                SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "sesionMB");
                sesion.setReportStream(buffer);
                sesion.setReportName(String.format("prescripcion_%s.pdf", prescripcionExtendedSelected.getFolio()));                
            }
        } catch (Exception e) {
            LOGGER.error("Error al generar la Impresión: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    
    
    public Integer getSizePrescripcionList() {
        if (prescripcionExtendedList != null) {
            return prescripcionExtendedList.size();
        } else {
            return 0;
        }
    }

    public Integer getSizePacienteList() {
        if (pacienteExtendList != null) {
            return pacienteExtendList.size();
        } else {
            return 0;
        }
    }
    
    public String getCadenaBusqueda() {
        return cadenaBusqueda;
    }

    public void setCadenaBusqueda(String cadenaBusqueda) {
        this.cadenaBusqueda = cadenaBusqueda;
    }

    public boolean isHuboError() {
        return huboError;
    }

    public void setHuboError(boolean huboError) {
        this.huboError = huboError;
    }


    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }
    public Usuario getUsuarioSelected() {
        return usuarioSelected;
    }

    public void setUsuarioSelected(Usuario usuarioSelected) {
        this.usuarioSelected = usuarioSelected;
    }
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

  public Prescripcion getPrescripcionSelected() {
        return prescripcionSelected;
    }

    public void setPrescripcionSelected(Prescripcion prescripcionSelected) {
        this.prescripcionSelected = prescripcionSelected;
    }

    public CamaExtended getCamaselected() {
        return camaselected;
    }

    public void setCamaselected(CamaExtended camaselected) {
        this.camaselected = camaselected;
    }

  

    public List<Prescripcion> getPrescripcionListSelected() {
        return prescripcionListSelected;
    }

    public void setPrescripcionListSelected(List<Prescripcion> prescripcionListSelected) {
        this.prescripcionListSelected = prescripcionListSelected;
    }

    public List<Prescripcion> getPrescripcionList() {
        return prescripcionList;
    }

    public void setPrescripcionList(List<Prescripcion> prescripcionList) {
        this.prescripcionList = prescripcionList;
    }


    public List<TransaccionPermisos> getPermisosList() {
        return permisosList;
    }

    public void setPermisosList(List<TransaccionPermisos> permisosList) {
        this.permisosList = permisosList;
    }
    public boolean isElementoSeleccionado() {
        return elementoSeleccionado;
    }

    public void setElementoSeleccionado(boolean elementoSeleccionado) {
        this.elementoSeleccionado = elementoSeleccionado;
    }


    public Paciente_Extended getPacientes() {
        return pacientes;
    }

    public void setPacientes(Paciente_Extended pacientes) {
        this.pacientes = pacientes;
    }
    public Paciente_Extended getPacienteExtendSelected() {
        return pacienteExtendSelected;
    }

    public void setPacienteExtendSelected(Paciente_Extended pacienteExtendSelected) {
        this.pacienteExtendSelected = pacienteExtendSelected;
    }


    public List<Paciente_Extended> getPacienteExtendList() {
        return pacienteExtendList;
    }

    public void setPacienteExtendList(List<Paciente_Extended> pacienteExtendList) {
        this.pacienteExtendList = pacienteExtendList;
    }

    public List<Paciente_Extended> getPacienteExtendListSelected() {
        return pacienteExtendListSelected;
    }

    public void setPacienteExtendListSelected(List<Paciente_Extended> pacienteExtendListSelected) {
        this.pacienteExtendListSelected = pacienteExtendListSelected;
    }

    public Prescripcion_Extended getPrescripcionExtendedSelected() {
        return prescripcionExtendedSelected;
    }

    public void setPrescripcionExtendedSelected(Prescripcion_Extended prescripcionExtendedSelected) {
        this.prescripcionExtendedSelected = prescripcionExtendedSelected;
    }

    public List<Prescripcion_Extended> getPrescripcionExtendedList() {
        return prescripcionExtendedList;
    }

    public void setPrescripcionExtendedList(List<Prescripcion_Extended> prescripcionExtendedList) {
        this.prescripcionExtendedList = prescripcionExtendedList;
    }

    public List<Diagnostico> getDiagnosticoList() {
        return diagnosticoList;
    }

    public void setDiagnosticoList(List<Diagnostico> diagnosticoList) {
        this.diagnosticoList = diagnosticoList;
    }

    public Diagnostico getDiagnosticoSelected() {
        return diagnosticoSelected;
    }

    public void setDiagnosticoSelected(Diagnostico diagnosticoSelected) {
        this.diagnosticoSelected = diagnosticoSelected;
    }

    public Integer getSizeDiagnosticoList() {
        if (diagnosticoList != null) {
            return diagnosticoList.size();
        } else {
            return 0;
        }
    }

    public List<Medicamento> getInsumoList() {
        return insumoList;
    }

    public void setInsumoList(List<Medicamento> insumoList) {
        this.insumoList = insumoList;
    }

    public Medicamento getInsumoSelected() {
        return insumoSelected;
    }

    public void setInsumoSelected(Medicamento insumoSelected) {
        this.insumoSelected = insumoSelected;
    }

    public Integer getSizeInsumoList() {
        if (insumoList != null) {
            return insumoList.size();
        } else {
            return 0;
        }
    }

    public List<PrescripcionInsumo_Extended> getPrescripcionInsumoExtendedList() {
        return prescripcionInsumoExtendedList;
    }

    public void setPrescripcionInsumoExtendedList(List<PrescripcionInsumo_Extended> prescripcionInsumoExtendedList) {
        this.prescripcionInsumoExtendedList = prescripcionInsumoExtendedList;
    }

    public PrescripcionInsumo_Extended getPrescripcionInsumoExtendedSelected() {
        return prescripcionInsumoExtendedSelected;
    }

    public void setPrescripcionInsumoExtendedSelected(PrescripcionInsumo_Extended prescripcionInsumoExtendedSelected) {
        this.prescripcionInsumoExtendedSelected = prescripcionInsumoExtendedSelected;
    }

    public Integer getSizePrescripcionInsumoExtendedList() {
        if (prescripcionInsumoExtendedList != null) {
            return prescripcionInsumoExtendedList.size();
        } else {
            return 0;
        }
    }

    public String getDiagnosticoCadenaBusqueda() {
        return diagnosticoCadenaBusqueda;
    }

    public void setDiagnosticoCadenaBusqueda(String diagnosticoCadenaBusqueda) {
        this.diagnosticoCadenaBusqueda = diagnosticoCadenaBusqueda;
    }

    public PrescripcionInsumo_Extended getPrescripcionInsumo() {
        return prescripcionInsumo;
    }

    public void setPrescripcionInsumo(PrescripcionInsumo_Extended prescripcionInsumo) {
        this.prescripcionInsumo = prescripcionInsumo;
    }

    public Diagnostico getDiagnosticoDeterminado() {
        return diagnosticoDeterminado;
    }

    public void setDiagnosticoDeterminado(Diagnostico diagnosticoDeterminado) {
        this.diagnosticoDeterminado = diagnosticoDeterminado;
    }

    public boolean isMuestraConfirmacion() {
        return muestraConfirmacion;
    }

    public void setMuestraConfirmacion(boolean muestraConfirmacion) {
        this.muestraConfirmacion = muestraConfirmacion;
    }

    public String getNotaMedica() {
        return notaMedica;
    }

    public void setNotaMedica(String notaMedica) {
        this.notaMedica = notaMedica;
    }

    public String getFirmaMedico() {
        return firmaMedico;
    }

    public void setFirmaMedico(String firmaMedico) {
        this.firmaMedico = firmaMedico;
    }

    public boolean isFirmaValida() {
        return firmaValida;
    }

    public void setFirmaValida(boolean firmaValida) {
        this.firmaValida = firmaValida;
    }

    public String getTipoPrescripcion() {
        return tipoPrescripcion;
    }

    public void setTipoPrescripcion(String tipoPrescripcion) {
        this.tipoPrescripcion = tipoPrescripcion;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isPrescripcionMayor24Horas() {
        return prescripcionMayor24Horas;
    }

    public void setPrescripcionMayor24Horas(boolean prescripcionMayor24Horas) {
        this.prescripcionMayor24Horas = prescripcionMayor24Horas;
    }

    public Medicamento_Extended getInsumoExtendedSelected() {
        return insumoExtendedSelected;
    }

    public void setInsumoExtendedSelected(Medicamento_Extended insumoExtendedSelected) {
        this.insumoExtendedSelected = insumoExtendedSelected;
    }

    public List<Estructura> getListaEstructuras() {
        return listaEstructuras;
    }

    public void setListaEstructuras(List<Estructura> listaEstructuras) {
        this.listaEstructuras = listaEstructuras;
    }

    public PrescripcionesLazy getPrescripcionesLazy() {
        return prescripcionesLazy;
    }

    public void setPrescripcionesLazy(PrescripcionesLazy prescripcionesLazy) {
        this.prescripcionesLazy = prescripcionesLazy;
    }

    public ParamBusquedaReporte getParamBusquedaReporte() {
        return paramBusquedaReporte;
    }

    public void setParamBusquedaReporte(ParamBusquedaReporte paramBusquedaReporte) {
        this.paramBusquedaReporte = paramBusquedaReporte;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

    public RespuestaAlertasDTO getAlertasDTO() {
        return alertasDTO;
    }

    public void setAlertasDTO(RespuestaAlertasDTO alertasDTO) {
        this.alertasDTO = alertasDTO;
    }

}
