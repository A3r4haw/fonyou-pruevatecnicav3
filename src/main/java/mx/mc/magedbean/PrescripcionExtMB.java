package mx.mc.magedbean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import mx.mc.enums.CatalogoGeneral_Enum;
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
import mx.mc.model.AlmacenControl_Extended;
import mx.mc.model.AlmacenInsumoComprometido;
import mx.mc.model.Diagnostico;
import mx.mc.model.DiagnosticoPaciente;
import mx.mc.model.Estructura;
import mx.mc.model.EstructuraAlmacenServicio;
import mx.mc.model.Medicamento;
import mx.mc.model.Medicamento_Extended;
import mx.mc.model.PacienteServicio;
import mx.mc.model.Paciente_Extended;
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
import mx.mc.service.ClaveMedicamentoSkuService;
import mx.mc.service.DiagnosticoService;
import mx.mc.service.EstructuraAlmacenServicioService;
import mx.mc.service.EstructuraService;
import mx.mc.service.MedicamentoService;
import mx.mc.service.PacienteService;
import mx.mc.service.PacienteServicioService;
import mx.mc.service.PacienteUbicacionService;
import mx.mc.service.PrescripcionService;
import mx.mc.service.VisitaService;
import mx.mc.util.Comunes;
import mx.mc.util.FechaUtil;
import mx.mc.util.Mensaje;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author aortiz
 */
@Controller
@Scope(value = "view")
public class PrescripcionExtMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(PrescripcionExtMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private boolean editable;

    private boolean huboError;
    private String cadenaBusqueda;
    private boolean elementoSeleccionado;
    private boolean muestraConfirmacion;
    private PermisoUsuario permiso;
    private Date fechaActual;
    private String nombreUsuario;

    private Usuario usuarioSelected;
    
    private Prescripcion prescripcionSelected;
    private List<Prescripcion> prescripcionList;
    private List<Prescripcion> prescripcionListSelected;
    private List<Prescripcion_Extended> prescripcionExtendedList;
    private Prescripcion_Extended prescripcionExtendedSelected;

    private List<TransaccionPermisos> permisosList;
    private Paciente_Extended pacienteExtendSelected;
    private List<Paciente_Extended> pacienteExtendList;
    private List<Paciente_Extended> pacienteExtendListSelected;
    
    @Autowired
    private transient PacienteService pacienteService;
    @Autowired
    private transient PacienteServicioService pacienteServicioService;
    @Autowired
    private transient DiagnosticoService diagnosticoService;
    @Autowired
    private transient PacienteUbicacionService pacienteUbicacionService;
    @Autowired
    private transient PrescripcionService prescripcionService;
    @Autowired
    private transient VisitaService visitaService;
    @Autowired
    private transient MedicamentoService medicamentoService;        
    
    private List<Medicamento> insumoList;
    private Medicamento insumoSelected;
    private Medicamento_Extended insumoExtendedSelected;
    private String diagnosticoCadenaBusqueda;
    private List<PrescripcionInsumo_Extended> prescripcionInsumoExtendedList;
    private PrescripcionInsumo_Extended prescripcionInsumoExtendedSelected;
    private PrescripcionInsumo_Extended prescripcionInsumo;


    private List<Diagnostico> diagnosticoList;
    private Diagnostico diagnosticoSelected;
    private Diagnostico diagnosticoDeterminado;
    private boolean resurtimientoDisabled;
    private Integer resurtimiento;
    private String notaMedica;
    private String firmaMedico;
    private boolean firmaValida;
    private String tipoPrescripcion;    
    private boolean checkResurtimiento;
    private String prcObtenerAlmacen;
    private String errTransaccion;
    private String errAccion;    
    private String prcLista;
    private String errRegistroIncorrecto;
    private String errActualizar;
    private String errEliminar;
    private String prcDiaMinimo;
    private String prcNopuedeCancelar;
    private String prcRegistroErr;

    private List<Estructura> almacenDispensadorList;
    private String idAlmacenDispensador;
    
    @Autowired
    private transient AlmacenControlService  ctrlService;
    @Autowired
    private transient EstructuraAlmacenServicioService estructuraAlmacenServicioService;
    @Autowired
    private transient EstructuraService estructuraService;

    @Autowired
    private transient ClaveMedicamentoSkuService claveMedicamentoSkuService;

    private String archivo;

    /**
     * Parametros de configuración a nivel Sistema
     */
    private boolean prescripcionMayor24Horas;
    private Integer noDiasCaducidad;
    private Usuario usuarioSession;
    private Integer noDiasResurtimiento;
    private boolean pacienteConCita;

    /**
     * Consulta los permisos del usuario y obtiene lospacientes registrados
     */
    @PostConstruct
    public void init() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.init()");
        prcObtenerAlmacen = "prc.obtener.almacen";
        errTransaccion = "err.transaccion";        
        prcLista = "prc.lista";
        errRegistroIncorrecto = "err.registro.incorrecto";
        errAccion = "err.accion";
        errActualizar = "err.actualizar";
        errEliminar = "err.eliminar";
        prcDiaMinimo = "prc.dia.minimo";
        prcNopuedeCancelar = "prc.nopuedecancelar";
        prcRegistroErr = "prc.registro.err";
        limpia();
        this.usuarioSession = Comunes.obtenerUsuarioSesion();
        //consultaPermisosUsuario();        
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        usuarioSelected = sesion.getUsuarioSelected();
        prescripcionMayor24Horas = sesion.isPrescripcionMayor24Horas();
        noDiasCaducidad = sesion.getNoDiasCaducidad();
        this.pacienteConCita = sesion.isPacienteConCita();
        this.noDiasResurtimiento = sesion.getNoDiasResurtimiento();       
        permiso = Comunes.obtenerPermisos(Transaccion_Enum.PRESCEXT.getSufijo());
        obtenerAlmacenDispensador();
        obtenerPacientes();
    }

    /**
     * Limpia las variables
     */
    private void limpia() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.limpia()");        
        muestraConfirmacion = Constantes.INACTIVO;

        elementoSeleccionado = Constantes.INACTIVO;

        huboError = Constantes.INACTIVO;
        cadenaBusqueda = null;

        fechaActual = new java.util.Date();
        nombreUsuario = null;

        usuarioSelected = new Usuario();
        permisosList = new ArrayList<>();
        usuarioSelected.setPermisosList(permisosList);

        firmaValida = Constantes.INACTIVO;
        firmaMedico = null;

        tipoPrescripcion = "";
        this.resurtimientoDisabled = true;

    }

    /**
     * Obtiene el almacen Dispensador del servicio desde donde el médico está
     * generando prescripción
     */
    private void obtenerAlmacenDispensador() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.obtenerAlmacenDispensador()");
        almacenDispensadorList = new ArrayList<>();

        List<EstructuraAlmacenServicio> eas;
        Estructura e;
        if (usuarioSelected == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcObtenerAlmacen), null);

        } else if (usuarioSelected.getIdEstructura() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcObtenerAlmacen), null);

        } else if (usuarioSelected.getIdEstructura().trim().isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcObtenerAlmacen), null);

        } else {
            String idEstructuraServicio = usuarioSelected.getIdEstructura();
            try {
                eas = estructuraAlmacenServicioService.obtenerLista(new EstructuraAlmacenServicio(null, idEstructuraServicio));
                if (!eas.isEmpty()) {
                    for(EstructuraAlmacenServicio almacen : eas){
                        e = estructuraService.obtener(new Estructura(almacen.getIdEstructuraAlmacen()));
                        if (e.getActiva().equals(Constantes.ES_ACTIVO)
                                && Objects.equals(e.getIdTipoAreaEstructura(), TipoAreaEstructura_Enum.ALMACEN.getValue())
                                && Objects.equals(e.getIdTipoAlmacen(), TipoAlmacen_Enum.ALMACEN.getValue())
                                || Objects.equals(e.getIdTipoAlmacen(), TipoAlmacen_Enum.SUBALMACEN.getValue())) {
                            almacenDispensadorList.add(e);
                        }
                    }
                }
                if (almacenDispensadorList.isEmpty()) {
                    e = estructuraService.obtenerEstructura(idEstructuraServicio);
                    if (e != null) {
                        boolean encontrado = false;
                        Integer cont = 0;
                        String idEstructuraTemp = e.getIdEstructuraPadre();
                        while (!encontrado && idEstructuraTemp != null) {
                            eas = estructuraAlmacenServicioService.obtenerLista(new EstructuraAlmacenServicio(null, idEstructuraTemp));
                            if(eas.isEmpty()) {
                                for(EstructuraAlmacenServicio almacen : eas){
                                    e = estructuraService.obtener(new Estructura(almacen.getIdEstructuraAlmacen()));
                                    if (e.getActiva().equals(Constantes.ES_ACTIVO)) {
                                        if (Objects.equals(e.getIdTipoAreaEstructura(), TipoAreaEstructura_Enum.ALMACEN.getValue())
                                                && Objects.equals(e.getIdTipoAlmacen(), TipoAlmacen_Enum.ALMACEN.getValue()) || Objects.equals(e.getIdTipoAlmacen(), TipoAlmacen_Enum.SUBALMACEN.getValue())) {
                                            almacenDispensadorList.add(e);
                                            encontrado = true;
                                        }
                                    } else {
                                        idEstructuraTemp = e.getIdEstructuraPadre();
                                    }
                                }
                            }
                            if (cont == 10) {
                                encontrado = true;
                            }
                            cont++;
                        }
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

    /**
     * Obtiene la lista de pacientes registrados
     */
    public void obtenerPacientes() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.obtenerPacientes()");

        boolean estatus = Constantes.INACTIVO;
        pacienteExtendList = new ArrayList<>();

        if (!permiso.isPuedeVer()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errTransaccion), null);
            estatus = Constantes.ACTIVO;

        } else {
            try {
                if (cadenaBusqueda != null
                        && cadenaBusqueda.trim().isEmpty()) {
                    cadenaBusqueda = null;
                }

                String idUnidadMedica = null;
                if (usuarioSelected.getIdEstructura() != null
                        && !usuarioSelected.getIdEstructura().trim().isEmpty()) {
                    idUnidadMedica = usuarioSelected.getIdEstructura();
                }

                List<Integer> listEstatusPaciente = new ArrayList<>();
                listEstatusPaciente.add(EstatusPaciente_Enum.ASIGNADO_A_SERVICIO.getValue());
                pacienteExtendList.addAll(pacienteService.obtenerPacientesPorIdUnidadConsExt(idUnidadMedica, idUnidadMedica, cadenaBusqueda, Constantes.REGISTROS_PARA_MOSTRAR, listEstatusPaciente, new ArrayList<>()));

                if (prescripcionList == null) {
                    prescripcionList = new ArrayList<>();
                }
                estatus = Constantes.ACTIVO;

            } catch (Exception exn) {
                LOGGER.error(RESOURCES.getString("prc.pac.lista"), exn);
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.pac.lista"), null);
            }
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, estatus);
    }


    public void onRowSelectPaciente(SelectEvent event) {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.onRowSelectPaciente()");
        pacienteExtendSelected = (Paciente_Extended) event.getObject();
        if (pacienteExtendSelected != null) {
            elementoSeleccionado = true;
        }
    }

    public void onRowSelectPrescripcion(SelectEvent event) {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.onRowSelectPrescripcion()");
        prescripcionExtendedSelected = (Prescripcion_Extended) event.getObject();
        if (prescripcionExtendedSelected != null) {
            elementoSeleccionado = true;
        }
    }
    
    public void onRowUnselectPaciente() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.onRowUnselectPaciente()");
        pacienteExtendSelected = null;
        elementoSeleccionado = Constantes.INACTIVO;
    }

    public void onRowUnselectPrescripcion() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.onRowUnselectPrescripcion()");
        prescripcionExtendedSelected = null;
        elementoSeleccionado = Constantes.INACTIVO;
    }

    public void onRowUnselectDiagnostico() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.onRowUnselectDiagnostico()");
        diagnosticoDeterminado = null;
        elementoSeleccionado = Constantes.INACTIVO;
    }
    
    public void onRowSelectDiagnostico(SelectEvent event) {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.onRowSelectDiagnostico()");
        diagnosticoDeterminado = (Diagnostico) event.getObject();
        if (diagnosticoDeterminado != null) {
            elementoSeleccionado = true;
        }
    }
    

    public void onRowSelectInsumo(SelectEvent event) {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.onRowSelectInsumo()");
        insumoSelected = (Medicamento) event.getObject();
        if (insumoSelected != null) {
            elementoSeleccionado = true;
        }
    }

    public void editaRegistro() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.editaRegistro()");

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


    /**
     * Al seleccionar editar un Paciente lista las prescripciones por paciente
     */
    public void verEditarPaciente() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionExtMB.verEditarPaciente()");
        prescripcionExtendedList = new ArrayList<>();
        
        if (!permiso.isPuedeVer()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errTransaccion), null);
        } else if (pacienteExtendSelected != null && pacienteExtendSelected.getIdPaciente() != null) {

            obtieneDatosPaciente();

            try {
                String idPaciente = pacienteExtendSelected.getIdPaciente();
                String idPrescripcion = null;
                String tipoConsulta = null;
                Integer recurrente = null;
                Date fechaPrescripcion = null;                
                String cadena = null;
                prescripcionExtendedList.addAll(prescripcionService.obtenerPrescripcionesPorIdPaciente(idPaciente, idPrescripcion, fechaPrescripcion, tipoConsulta, recurrente, cadena));
            } catch (Exception ex) {
                LOGGER.error(RESOURCES.getString(prcLista), ex);
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcLista), null);
            }
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
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errTransaccion), null);

        } else if (idPaciente != null) {
            try {
                String idPrescripcion = null;
                Date fechaPrescripcion = null;
                String tipConsulta = null;
                Integer recurrente = null;
                String cadena = null;
                prescripcionExtendedList.addAll(prescripcionService.obtenerPrescripcionesPorIdPaciente(idPaciente, idPrescripcion, fechaPrescripcion, tipConsulta, recurrente, cadena));
            } catch (Exception exc) {
                LOGGER.error(RESOURCES.getString(prcLista), exc);
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
        Paciente_Extended pEx = pacienteExtendSelected;
        try {
            pacienteExtendSelected = pacienteService.obtenerPacienteCompletoPorId(pEx.getIdPaciente());
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString("prc.obtener.paciente"), ex);
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

        prescripcionInsumoExtendedList = new ArrayList<>();
        prescripcionInsumoExtendedSelected = new PrescripcionInsumo_Extended();
        prescripcionInsumo = new PrescripcionInsumo_Extended();
        diagnosticoList = new ArrayList<>();
        diagnosticoSelected = new Diagnostico();
        diagnosticoDeterminado = new Diagnostico();        
        editable = Constantes.INACTIVO;
        prescripcionSelected = new Prescripcion();
        notaMedica = "";
        firmaMedico = "";
        tipoPrescripcion = TipoPrescripcion_Enum.NORMAL.getValue();
        insumoSelected = new Medicamento();

        if (!permiso.isPuedeVer()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errTransaccion), null);

        } else if (prescripcionExtendedSelected == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);

        } else if (prescripcionExtendedSelected.getIdPrescripcion() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);

        } else {

            try {
                Prescripcion pres = new Prescripcion();
                pres.setIdPrescripcion(prescripcionExtendedSelected.getIdPrescripcion());

                prescripcionSelected = prescripcionService.obtener(pres);

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
                        String idPatient = pacienteExtendSelected.getIdPaciente();
                        String idVisita = null;

                        diagnosticoList = diagnosticoService.obtenerPorIdPacienteIdVisitaIdPrescripcion(idPatient, idVisita, prescripcionSelected.getIdPrescripcion());
                        Integer tipoInsumo = CatalogoGeneral_Enum.MEDICAMENTO.getValue();
                        prescripcionInsumoExtendedList = prescripcionService.obtenerInsumosPorIdPrescripcion(prescripcionSelected.getIdPrescripcion(), tipoInsumo);

                    }
                }
                diagnosticoSelected = new Diagnostico();
                insumoSelected = new Medicamento();
                prescripcionInsumoExtendedSelected = new PrescripcionInsumo_Extended();
            } catch (Exception exc) {
                LOGGER.error(RESOURCES.getString("prc.consulta"), exc);
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.consulta"), null);
            }
            if (prescripcionSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.obtener.prescripcion"), null);

            }
        }
    }

    public void inactivaRegistro() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.inactivaRegistro()");

        boolean estatus = Constantes.INACTIVO;
        try {
            Prescripcion r = new Prescripcion();
            r.setIdPrescripcion(prescripcionSelected.getIdPrescripcion());
            r.setUpdateFecha(new java.util.Date());
            r.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
            estatus = prescripcionService.actualizar(r);
            if (estatus) {
                Mensaje.showMessage("Info", RESOURCES.getString("ok.actualizar") + prescripcionSelected.getFolio(), null);
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errActualizar) + prescripcionSelected.getFolio(), null);
            }
        } catch (Exception exc) {
            LOGGER.error("{} {} {}", RESOURCES.getString(errActualizar), prescripcionSelected.getFolio(), exc.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errActualizar) + prescripcionSelected.getFolio(), null);

        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, estatus);

    }
    
    
    public void validaIactivaRegistro() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.validaIactivaRegistro()");
        if (!permiso.isPuedeEditar()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errAccion), null);
        } else if (prescripcionSelected == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);
        }

    }


    /**
     * Elimina la prescripción seleccionada, confirmada y validada
     */
    public void eliminaPrescripcion() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.eliminaPrescripcion()");

        boolean estatus = Constantes.INACTIVO;
        try {
            Prescripcion r = new Prescripcion();
            r.setIdPrescripcion(prescripcionSelected.getIdPrescripcion());
            if (estatus) {
                List<Prescripcion> prescripcionesRegistradosLis = new ArrayList<>();
                prescripcionesRegistradosLis.addAll(prescripcionList);
                ListIterator<Prescripcion> iter = prescripcionesRegistradosLis.listIterator();
                while (iter.hasNext()) {
                    if (iter.next().getIdPrescripcion().equals(prescripcionSelected.getIdPrescripcion())) {
                        iter.remove();
                    }
                }
                prescripcionList = new ArrayList<>();
                prescripcionList.addAll(prescripcionesRegistradosLis);
                prescripcionSelected = new Prescripcion();

                Mensaje.showMessage("Info", RESOURCES.getString("ok.eliminar") + prescripcionSelected.getFolio(), null);
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errEliminar) + prescripcionSelected.getFolio(), null);
            }
        } catch (Exception exc) {
            LOGGER.error("{} {} {}", RESOURCES.getString(errEliminar), prescripcionSelected.getFolio(), exc.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errEliminar) + prescripcionSelected.getFolio(), null);

        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, estatus);

    }

    
        /**
     * Valida Eliminar una prescripción
     */
    public void validaEliminaPrescripcion() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionExtMB.validaEliminaPrescripcion()");
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
     * Inicializa variables antes de grear una prescripción
     */
    private boolean creaPrescripcion() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.creaPrescripcion()");
        String estatusIncorrecto = "prc.paciente.estatus.incorrecto";
        boolean res = Constantes.INACTIVO;
        if (!permiso.isPuedeCrear() && !permiso.isPuedeEditar()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errAccion), null);

        } else {
            prescripcionSelected = new Prescripcion();
            editable = Constantes.ACTIVO;            
            insumoSelected = new Medicamento();
            diagnosticoList = new ArrayList<>();
            diagnosticoSelected = new Diagnostico();
            prescripcionInsumoExtendedList = new ArrayList<>();
            prescripcionInsumo = new PrescripcionInsumo_Extended();  
            prescripcionInsumo.setComentarios("");          
            
            notaMedica = "";

// todo:    Validación por idEstructura
// todo:    Validación ingreso hospitalario
// todo:    Validación asignación a cama
// todo:    Validación asignación a servicio
// todo:    Validación de estatus de paciente
// regla: Solamente puede realizar prescripciones a paciente con estatus asignado a servicio o asignado a cama
            obtieneDatosPaciente();

            if (pacienteExtendSelected != null) {
                try {
                    String idPaciente = pacienteExtendSelected.getIdPaciente();
                    Visita visitAbierta = visitaService.obtenerVisitaAbierta(new Visita(idPaciente));
                    if (visitAbierta != null
                            && visitAbierta.getIdVisita() != null) {
                        String idVisita = visitAbierta.getIdVisita();
                        String idPrescripcion = null;
                        diagnosticoList.addAll(diagnosticoService.obtenerPorIdPacienteIdVisitaIdPrescripcion(idPaciente, idVisita, idPrescripcion));
                    }
                } catch (Exception ex) {
                    LOGGER.error("Error al Obtener diagnosticos previos de la visita: {}", ex.getMessage());
                }
                if (pacienteExtendSelected.getIdEstatusPaciente() <= 0) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(estatusIncorrecto), null);

                } else if (!pacienteExtendSelected.getIdEstatusPaciente().equals(EstatusPaciente_Enum.ASIGNADO_A_CAMA.getValue())
                        && !pacienteExtendSelected.getIdEstatusPaciente().equals(EstatusPaciente_Enum.ASIGNADO_A_SERVICIO.getValue())) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(estatusIncorrecto), null);

                } else {
                    res = Constantes.ACTIVO;
                }
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(estatusIncorrecto), null);
            }
        }
        return res;
    }

    public void creaPrescripcionNormal() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.creaPrescripcionNormal()");
        boolean resp = creaPrescripcion();
        this.diagnosticoList = new ArrayList<>();
        if (resp) {
            prescripcionSelected.setTipoPrescripcion(TipoPrescripcion_Enum.NORMAL.getValue());
            tipoPrescripcion = TipoPrescripcion_Enum.NORMAL.name();
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, resp);
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

    
    public void creaPrescripcionUrgente() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.creaPrescripcionUrgente()");
        boolean resp = creaPrescripcion();
        if (resp) {
            tipoPrescripcion = TipoPrescripcion_Enum.URGENTE.name();
            prescripcionSelected.setTipoPrescripcion(TipoPrescripcion_Enum.URGENTE.getValue());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, resp);
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
        List<Diagnostico> diagnList = new ArrayList<>();

        try {
            diagnList.addAll(diagnosticoService.obtenerListaAutoComplete(cadena));

        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString("prc.dia.lista"), ex);
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.dia.lista"), null);
        }
        return diagnList;
    }

    public void setDiagnosticoUnSelected() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.setDiagnosticoUnSelected()");
        diagnosticoSelected = new Diagnostico();
    }

    
    public void setDiagnosticoSelected(SelectEvent e) {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.setDiagnosticoSelected()");
        diagnosticoSelected = (Diagnostico) e.getObject();
    }

    /**
     * Valida la agregación de un diagnostico
     */
    public void validaDiagnostico() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.validaDiagnostico()");
        boolean estatus = Constantes.INACTIVO;

        if (!permiso.isPuedeCrear() && !permiso.isPuedeEditar()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errAccion), null);

        } else {

            try {
                if (diagnosticoDeterminado == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);

                } else if (diagnosticoDeterminado.getIdDiagnostico() == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcDiaMinimo), null);

                } else if (notaMedica == null || notaMedica.trim().isEmpty()) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.nota"), null);

                } else {
                    boolean diagnosticoRegistrado = Constantes.INACTIVO;
                    for (Diagnostico ite : diagnosticoList) {
                        if (ite.getIdDiagnostico().equals(diagnosticoDeterminado.getIdDiagnostico())) {
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

            } catch (Exception exc) {
                LOGGER.error(RESOURCES.getString("prc.dia.err"), exc);
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.dia.err"), null);
            }
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, estatus);

    }

    /**
     * Agrega un diagnostico a una prescripción
     *
     * @return
     */
    private boolean agregaDiagnostico() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.agregaDiagnostico()");
        boolean res;
        Diagnostico di = new Diagnostico();
        di.setIdDiagnostico(diagnosticoDeterminado.getIdDiagnostico());
        di.setActivo(Constantes.ACTIVO);
        try {
            diagnosticoDeterminado = diagnosticoService.obtener(di);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener el Diagnóstico: {}", ex.getMessage());
        }
        res = diagnosticoList.add(diagnosticoDeterminado);

        diagnosticoDeterminado = new Diagnostico();
        return res;
    }

    
    public void onItemSelectInsumo(SelectEvent event) {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.onItemSelectInsumo()");        
        insumoSelected = (Medicamento) event.getObject();
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


    /**
     * Consulta de insumos por cadena de texto mayor a 3 caracteres y retorna
     * las coincidencias
     *
     * @param query
     * @return
     */
    public List<Medicamento_Extended> autocompleteInsumo(String query) {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.autocompleteInsumo()");
        List<Medicamento_Extended> listaInsumo = new ArrayList<>();
        try {
            listaInsumo.addAll(medicamentoService.obtenerInsumoDisponiblePrescExt(query, almacenDispensadorList, noDiasCaducidad));

        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString("prc.ins.lista"), ex);
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.ins.lista"), null);
        }
        return listaInsumo;
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

        } else if (insumoExtendedSelected.getIdMedicamento() == null || insumoExtendedSelected.getIdMedicamento().isEmpty()) {
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

// regla: valida si se permites prescripciones por mas de 24 horas
            Integer duracion = Constantes.PRESCRIPCION_DURACION;
            boolean valido = Constantes.INACTIVO;
            if (prescripcionMayor24Horas) {
                if (prescripcionInsumo.getDuracion() == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.ins.duracion"), null);

                } else if (prescripcionInsumo.getDuracion() <= 0) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.ins.duracion"), null);

                } else {
                    duracion = prescripcionInsumo.getDuracion();
                    valido = Constantes.ACTIVO;
                }
            } else {
                valido = Constantes.ACTIVO;

            }

            if (valido) {
// todo:    Valida la existencia de Medicamento
// regla: prescribe solo medicamentos con existencia suficiente
                BigDecimal dosis = prescripcionInsumo.getDosis().setScale(0, RoundingMode.CEILING);
                Integer cantidad = ((24 / prescripcionInsumo.getFrecuencia()) * dosis.intValue()) * duracion;
                if (cantidad > (insumoExtendedSelected.getCantidadActual() - insumoExtendedSelected.getCantidadComprometida())) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.insumo.insuficiente"), null);

                } else {
                    prescripcionInsumo.setDuracion(duracion);
                    prescripcionInsumo.setFechaInicio(prescripcionInsumo.getFechaInicio());

                    boolean insumRegistrado = Constantes.INACTIVO;
                    for (PrescripcionInsumo_Extended item : prescripcionInsumoExtendedList) {
                        if (item.getIdInsumo().equals(insumoSelected.getIdMedicamento())) {
                            insumRegistrado = true;
                            break;
                        }
                    }

                    if (insumRegistrado) {
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

        Medicamento med = null;
        try {
            med = medicamentoService.obtenerMedicamento(insumoExtendedSelected.getIdMedicamento());
        } catch (Exception e) {
            LOGGER.error(RESOURCES.getString("prc.ins.elegido"), e);
        }
        if (med == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.ins.elegido"), null);

        } else {
            prescripcionInsumo.setClaveInstitucional(med.getClaveInstitucional());
            prescripcionInsumo.setNombreCorto(med.getNombreCorto());
            prescripcionInsumo.setNombreLargo(med.getNombreLargo());
            prescripcionInsumo.setIdInsumo(med.getIdMedicamento());
// regla: Calculo de piezas antes de 24 Horas
            BigDecimal dosis = prescripcionInsumo.getDosis().setScale(0, RoundingMode.CEILING);
            Integer cant = (24 / prescripcionInsumo.getFrecuencia());
            prescripcionInsumo.setCantidad24Horas(cant * dosis.intValue());
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
     * @param idDiagnostic
     */
    public void validaEliminaDiagnostico(String idDiagnostic) {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.validaEliminaDiagnostico()");
        if (!permiso.isPuedeCrear() || !permiso.isPuedeEditar()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errTransaccion), null);

        } else if (idDiagnostic == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);

        } else if (diagnosticoList == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);

        } else if (diagnosticoList.isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);

        } else {
            eliminaDiagnostico(idDiagnostic);
            mostrarConfirmacion();
        }
    }

    /**
     * Elimina un diagnostico de la prescripción
     *
     * @param idDiagnostic
     * @return
     */
    private boolean eliminaDiagnostico(String idDiagnostic) {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.eliminaDiagnostico()");
        List<Diagnostico> insumosRegistradosList = new ArrayList<>();
        insumosRegistradosList.addAll(diagnosticoList);
        ListIterator<Diagnostico> iter = insumosRegistradosList.listIterator();
        while (iter.hasNext()) {
            if (iter.next().getIdDiagnostico().equals(idDiagnostic)) {
                iter.remove();
            }
        }
        diagnosticoDeterminado = new Diagnostico();
        diagnosticoSelected = new Diagnostico();
        diagnosticoList = new ArrayList<>();
        diagnosticoList.addAll(insumosRegistradosList);        
        return true;
    }

    /**
     * Valida la eliminación de un medicamento de una prescripción
     *
     * @param idMedicament
     */
    public void validaEliminaInsumo(String idMedicament) {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.validaEliminaInsumo()");
        if (!permiso.isPuedeCrear() || !permiso.isPuedeEditar()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errTransaccion), null);

        } else if (idMedicament == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);

        } else if (prescripcionInsumoExtendedList == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);

        } else if (prescripcionInsumoExtendedList.isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);

        } else {
            eliminaMedicamento(idMedicament);
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
     * @param idPrescripcio
     */
    public void validaCancelarPrescripcionPorId(String idPrescripcio) {
        LOGGER.error("mx.mc.magedbean.PrescripcionMB.validaCancelarPrescripcionPorId()");
        if (idPrescripcio == null || idPrescripcio.trim().isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcNopuedeCancelar), null);

        } else {
            prescripcionExtendedSelected = new Prescripcion_Extended();
            prescripcionExtendedSelected.setIdPrescripcion(idPrescripcio);
            validaCancelarPrescripcion();
        }
    }

    /**
     * Valida estatus y objetos necesarios para cancelar una prescripción
     */
    public void validaCancelarPrescripcion() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.validaCancelarPrescripcion()");

        boolean estatus = Constantes.INACTIVO;
        if (!permiso.isPuedeEditar()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errTransaccion), null);

        } else if (prescripcionExtendedSelected == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcNopuedeCancelar), null);

        } else if (prescripcionExtendedSelected.getIdPrescripcion() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcNopuedeCancelar), null);

        } else {
            try {
                Prescripcion pr = new Prescripcion();
                pr.setIdPrescripcion(prescripcionExtendedSelected.getIdPrescripcion());

                prescripcionSelected = prescripcionService.obtener(pr);

                if (prescripcionSelected == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcNopuedeCancelar), null);

                } else if (prescripcionSelected.getIdEstatusPrescripcion().equals(EstatusPrescripcion_Enum.FINALIZADA.getValue())
                        || prescripcionSelected.getIdEstatusPrescripcion().equals(EstatusPrescripcion_Enum.CANCELADA.getValue())) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcNopuedeCancelar), null);

                } else {
                    estatus = cancelarPrescripcion();
                    if (estatus) {
                        for (Prescripcion_Extended item : prescripcionExtendedList) {
                            if (item.getIdPrescripcion().equals(prescripcionSelected.getIdPrescripcion())) {
                                item.setIdEstatusPrescripcion(EstatusPrescripcion_Enum.CANCELADA.getValue());
                                item.setEstatusPrescripcion(EstatusPrescripcion_Enum.CANCELADA.toString());
                            }
                        }
                    }
                }

            } catch (Exception ex) {
                LOGGER.error(RESOURCES.getString(prcNopuedeCancelar), ex);
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcNopuedeCancelar), null);
            }
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, estatus);

    }

    /**
     * Cancela una prescripción así como todos los surtimientos dependientes de
     * esta prescripción y todos medicamentos de la prescripción
     *
     * @return
     */
    private boolean cancelarPrescripcion() {
        LOGGER.error("mx.mc.magedbean.PrescripcionMB.cancelarPrescripcion()");
        boolean resp = Constantes.INACTIVO;
        try {
            resp = prescripcionService.cancelarPrescripcion(prescripcionSelected.getIdPrescripcion(), usuarioSelected.getIdUsuario(), new java.util.Date(), EstatusPrescripcion_Enum.CANCELADA.getValue(), EstatusGabinete_Enum.CANCELADA.getValue(), EstatusSurtimiento_Enum.CANCELADO.getValue());

        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString(prcNopuedeCancelar), ex);
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcNopuedeCancelar), null);
        }
        return resp;
    }

    /**
     * Valida la existencia de una cadea de texto como firma
     */
    public void validaPrescripcion() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.validaPrescripcion()");

        boolean estatus = Constantes.INACTIVO;

        if (!permiso.isPuedeCrear() || !permiso.isPuedeEditar()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errTransaccion), null);

        } else if (diagnosticoList == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcDiaMinimo), null);

        } else if (diagnosticoList.isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcDiaMinimo), null);

        } else if (prescripcionInsumoExtendedList == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.ins.minimo"), null);

        } else if (prescripcionInsumoExtendedList.isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.ins.minimo"), null);

        } else if (notaMedica == null || notaMedica.trim().isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.nota"), null);

        } else {
            estatus = Constantes.ACTIVO;
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, estatus);
    }

    /**
     * Guarda prescripción despues de validada
     */
    public void guardarPrescripcion() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.guardarPrescripcion()");
        boolean estatus;
        if (!CustomWebSecurityConfigurerAdapter.customPasswordEncoder().matches(firmaMedico, usuarioSelected.getClaveAcceso())) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.firma.incorrecta"), null);

        } else {
            firmaValida = true;

        }
        if (firmaValida) {
            prescripcionSelected.setIdEstatusPrescripcion(EstatusPrescripcion_Enum.PROGRAMADA.getValue());
            prescripcionSelected.setIdEstatusGabinete(noDiasCaducidad);
        } else {
            prescripcionSelected.setIdEstatusPrescripcion(EstatusPrescripcion_Enum.REGRISTRADA.getValue());
        }
        if (prescripcionSelected.getIdPrescripcion() == null) {
            estatus = registraPrescripcion();
        } else {
            estatus = editaPrescripcion();
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, estatus);
    }

    /**
     * Registra la prescripción con diagnósticos y medicamentos, y notas Médicas
     */
    private boolean registraPrescripcion() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.registraPrescripcion()");
        String prcVisita = "prc.visita";
        String prcServicio = "prc.servicio";
        boolean resp = Constantes.INACTIVO;
        if (!permiso.isPuedeCrear()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errAccion), null);

        } else {

            Visita vi = null;
            try {
                vi = visitaService.obtener(new Visita(pacienteExtendSelected.getIdPaciente()));
            } catch (Exception ex) {
                LOGGER.error(RESOURCES.getString(prcVisita), ex);
            }
            // regla: paciente debe tener visita abierta
            if (vi == null || vi.getIdVisita() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcVisita), null);
                return Constantes.INACTIVO;
            }

            PacienteServicio pas = null;
            try {
                pas = pacienteServicioService.obtener(new PacienteServicio(vi.getIdVisita()));
            } catch (Exception ex) {
                LOGGER.error(RESOURCES.getString(prcServicio), ex);
            }
            // regla: paciente debe estar asignado a un servicio
            if (pas == null || pas.getIdPacienteServicio() == null || pas.getIdPacienteServicio().isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcServicio), null);
                return Constantes.INACTIVO;
            }

            try {
                prescripcionSelected.setIdPrescripcion(Comunes.getUUID());
                prescripcionSelected.setIdEstructura(usuarioSelected.getIdEstructura());
                prescripcionSelected.setIdPacienteServicio(pas.getIdPacienteServicio());
                // todo:    asignar nomenclatura del folio
                prescripcionSelected.setFolio(Comunes.getUUID().substring(1, 8));
                prescripcionSelected.setFechaPrescripcion(new java.util.Date());
                prescripcionSelected.setFechaFirma(new java.util.Date());
                prescripcionSelected.setTipoConsulta(TipoConsulta_Enum.CONSULTA_EXTERNA.getValue());
                prescripcionSelected.setIdMedico(usuarioSelected.getIdUsuario());
                prescripcionSelected.setRecurrente(this.checkResurtimiento);
                prescripcionSelected.setComentarios(notaMedica);
                prescripcionSelected.setIdEstatusGabinete(EstatusGabinete_Enum.PENDIENTE.getValue());
                prescripcionSelected.setInsertFecha(new java.util.Date());
                prescripcionSelected.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                prescripcionSelected.setResurtimiento(this.resurtimiento);
                // todo agregar la unidad jerarquica a la que pertenece la prescripcion
//                prescripcionSelected.setIdEstructura(usuarioSelected.getIdEstructura());

                List<DiagnosticoPaciente> diagnosticoPacienteLis = new ArrayList<>();
                DiagnosticoPaciente diagnosticoPatient;
                for (Diagnostico item : diagnosticoList) {
                    diagnosticoPatient = new DiagnosticoPaciente();
                    diagnosticoPatient.setIdDiagnosticoPaciente(Comunes.getUUID());
                    diagnosticoPatient.setIdPrescripcion(prescripcionSelected.getIdPrescripcion());
                    diagnosticoPatient.setFechaDiagnostico(new java.util.Date());
                    diagnosticoPatient.setIdUsuarioDiagnostico(usuarioSelected.getIdUsuario());
                    diagnosticoPatient.setIdDiagnostico(item.getIdDiagnostico());
                    diagnosticoPatient.setFechaFinDiagnostico(null);
                    diagnosticoPatient.setIdUsuarioDiagnosticoTratado(null);
                    diagnosticoPatient.setIdEstatusDiagnostico(EstatusDiagnostico_Enum.DIAGNOSTICADO.getValue());
                    diagnosticoPatient.setInsertFecha(new java.util.Date());
                    diagnosticoPatient.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                    diagnosticoPatient.setUpdateFecha(null);
                    diagnosticoPatient.setUpdateIdUsuario(null);
                    diagnosticoPacienteLis.add(diagnosticoPatient);
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

                resp = prescripcionService.registrarPrescripcion(prescripcionSelected, diagnosticoPacienteLis, prescInsumoList, surtimientoList, null);

            } catch (Exception ex) {
                LOGGER.error(RESOURCES.getString(prcRegistroErr), ex);
                prescripcionSelected.setIdPrescripcion(null);
            }
        }
        if (!resp) {
            LOGGER.error(RESOURCES.getString(prcRegistroErr));
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcRegistroErr), null);

        } else {
            verEditarPaciente();
            Mensaje.showMessage("Info", RESOURCES.getString("prc.registro.ok"), null);

        }
        return resp;
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
            if(idAlmacen.isEmpty())
            {
                Estructura almacen = almacenDispensadorList.stream().filter(p->p.getIdTipoAlmacen().equals(TipoAlmacen_Enum.ALMACEN.getValue())).findAny().orElse(null);
                idAlmacen=almacen.getIdEstructura();
            }
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString(prcRegistroErr), ex);
        }
        return idAlmacen;
    }
    
    private List<Surtimiento> calculaSurtimientos(List<PrescripcionInsumo> prescInsumoList) throws Exception {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.calculaSurtimientos()");
        List<Surtimiento> surtimientoList = new ArrayList<>();
        //TODO modificar para farmacia externa
//        Collections.sort(prescInsumoList, (Object o1, Object o2) -> {
//            PrescripcionInsumo p1 = (PrescripcionInsumo) o1;
//            PrescripcionInsumo p2 = (PrescripcionInsumo) o2;
//            return p1.getDuracion().compareTo(p2.getDuracion());
//        });
//        PrescripcionInsumo pi = Collections.max(prescInsumoList, (Object o1, Object o2) -> {
//            PrescripcionInsumo p1 = (PrescripcionInsumo) o1;
//            PrescripcionInsumo p2 = (PrescripcionInsumo) o2;
//            return p1.getDuracion().compareTo(p2.getDuracion());
//        });
//
//        Integer numeroSurtimientos = pi.getDuracion();

        Integer cont = 0;
        Surtimiento surt;
        surt = new Surtimiento();
        surt.setIdSurtimiento(Comunes.getUUID());
        surt.setIdEstructuraAlmacen(idAlmacenDispensador);
        surt.setIdPrescripcion(prescripcionSelected.getIdPrescripcion());
        surt.setFechaProgramada(FechaUtil.sumarRestarDiasFecha(new java.util.Date(), cont));
        surt.setFolio(Comunes.getUUID().substring(1, 8));
        surt.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
        surt.setInsertFecha(new java.util.Date());
        surt.setInsertIdUsuario(usuarioSelected.getIdUsuario());
        surt.setDetalle(new ArrayList<>());
        surtimientoList.add(surt);

        cont = 0;
        SurtimientoInsumo si;
        List<SurtimientoInsumo> siList = new ArrayList<>();
        Integer noDias = 1;
        Integer cantidadSolicitadaPorDia;
//TODO para farmacia externa calcular el numero de tomas totales por dosis
// regla: se calculan los surtimientos
        for (PrescripcionInsumo item : prescInsumoList) {
            if (noDias > 0) {
                Surtimiento item2 = surtimientoList.get(0);
                //TODO Cambiar por parametros en base de datos
                BigDecimal dosis = item.getDosis().setScale(0, RoundingMode.CEILING);
                if (Constantes.PARAMETRO_RECEPCION_MIXTA == 0) {
                    cantidadSolicitadaPorDia = (24 / item.getFrecuencia()) * dosis.intValue() * item.getDuracion();

                    Integer cantidadComercial = claveMedicamentoSkuService.obtenerCantidadPorClaveMedicamento(item.getIdInsumo());
                    if (cantidadComercial == null || cantidadComercial < 1) {
                        cantidadComercial = 1;
                    }
                    Integer result;
                    result = cantidadSolicitadaPorDia / cantidadComercial;
                    if (cantidadSolicitadaPorDia % cantidadComercial > 0) {
                        result += 1;
                    }
                    Integer cantidadFinal = result * cantidadComercial;
                    cantidadSolicitadaPorDia = cantidadFinal;

                } else {
                    cantidadSolicitadaPorDia = (24 / item.getFrecuencia()) * dosis.intValue() * item.getDuracion();
                }
                si = new SurtimientoInsumo();
                si.setIdSurtimientoInsumo(Comunes.getUUID());
                si.setIdSurtimiento(item2.getIdSurtimiento());
                si.setIdPrescripcionInsumo(item.getIdPrescripcionInsumo());
                si.setFechaProgramada(item2.getFechaProgramada());
                si.setCantidadSolicitada(cantidadSolicitadaPorDia);
                si.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                si.setInsertFecha(new java.util.Date());
                si.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                siList.add(si);
                item2.setDetalle(siList);
            }
            cont++;
        }
        return surtimientoList;
    }

    private List<AlmacenInsumoComprometido> calculaComprometidos(List<PrescripcionInsumo> prescInsumoList) {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.calculaComprometidos()");
        List<AlmacenInsumoComprometido> comprometidoLis = new ArrayList<>();
        try {
            AlmacenInsumoComprometido aico;
            Integer cantidadComprometida = 0;
            for (PrescripcionInsumo item : prescInsumoList) {
                BigDecimal dosis = item.getDosis().setScale(0, RoundingMode.CEILING);
                cantidadComprometida = ((24 / item.getFrecuencia()) * dosis.intValue()) * item.getDuracion();
                aico = new AlmacenInsumoComprometido();
                aico.setIdEstructura(idAlmacenDispensador);
                aico.setIdInsumo(item.getIdInsumo());
                aico.setCantidadComprometida(cantidadComprometida);
                comprometidoLis.add(aico);
            }
        } catch (Exception excp) {
            LOGGER.error(excp.getMessage());
        }
        return comprometidoLis;
    }

    private boolean editaPrescripcion() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.editaPrescripcion()");
        boolean result = Constantes.INACTIVO;
        if (!permiso.isPuedeEditar()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errAccion), null);
        } else {
            try {
                // todo:    asignar nomenclatura del folio
//                prescripcionSelected.setFolio(Comunes.getUUID().substring(1, 8));
                prescripcionSelected.setFechaFirma(new java.util.Date());
                prescripcionSelected.setIdMedico(usuarioSelected.getIdUsuario());
                prescripcionSelected.setRecurrente(this.checkResurtimiento);
                prescripcionSelected.setResurtimiento(this.resurtimiento);
                prescripcionSelected.setComentarios(notaMedica);
                prescripcionSelected.setUpdateFecha(new java.util.Date());
                prescripcionSelected.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
                // todo agregar la unidad jerarquica a la que pertenece la prescripcion
//                prescripcionSelected.setIdEstructura(usuarioSelected.getIdEstructura());

                List<DiagnosticoPaciente> diagnosticoPacienteList = new ArrayList<>();
                DiagnosticoPaciente pacienteDiagnostico;
                for (Diagnostico item : diagnosticoList) {
                    pacienteDiagnostico = new DiagnosticoPaciente();
                    pacienteDiagnostico.setIdDiagnosticoPaciente(Comunes.getUUID());
                    pacienteDiagnostico.setIdPrescripcion(prescripcionSelected.getIdPrescripcion());
                    pacienteDiagnostico.setFechaDiagnostico(new java.util.Date());
                    pacienteDiagnostico.setIdUsuarioDiagnostico(usuarioSelected.getIdUsuario());
                    pacienteDiagnostico.setIdDiagnostico(item.getIdDiagnostico());
                    pacienteDiagnostico.setFechaFinDiagnostico(null);
                    pacienteDiagnostico.setIdUsuarioDiagnosticoTratado(null);
                    pacienteDiagnostico.setIdEstatusDiagnostico(EstatusDiagnostico_Enum.DIAGNOSTICADO.getValue());
                    pacienteDiagnostico.setInsertFecha(new java.util.Date());
                    pacienteDiagnostico.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                    pacienteDiagnostico.setUpdateFecha(null);
                    pacienteDiagnostico.setUpdateIdUsuario(null);
                    diagnosticoPacienteList.add(pacienteDiagnostico);
                }

                List<PrescripcionInsumo> prescInsumoList = new ArrayList<>();              
                List<String> itemList = new ArrayList<>();
                PrescripcionInsumo prescrInsumo;
                for (PrescripcionInsumo_Extended item : prescripcionInsumoExtendedList) {
                    prescrInsumo = new PrescripcionInsumo();
                    prescrInsumo.setIdPrescripcionInsumo(Comunes.getUUID());
                    prescrInsumo.setIdPrescripcion(prescripcionSelected.getIdPrescripcion());
                    prescrInsumo.setIdInsumo(item.getIdInsumo());
                    prescrInsumo.setFechaInicio(item.getFechaInicio());
                    prescrInsumo.setDosis(item.getDosis());
                    prescrInsumo.setFrecuencia(item.getFrecuencia());
                    prescrInsumo.setDuracion(item.getDuracion());
                    prescrInsumo.setComentarios(null);
                    prescrInsumo.setIdEstatusPrescripcion(prescripcionSelected.getIdEstatusPrescripcion());
                    prescrInsumo.setIndicaciones(item.getIndicaciones());
                    prescrInsumo.setInsertFecha(new java.util.Date());
                    prescrInsumo.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                    prescrInsumo.setUpdateFecha(null);
                    prescrInsumo.setUpdateIdUsuario(null);
                    prescInsumoList.add(prescrInsumo);
                    itemList.add(item.getIdInsumo());
                }

                // Buscar el almacen que surtirá la prescripción
                idAlmacenDispensador = almacenDispensadorList.size()>1 ? BuscarAlmacenDispensador(itemList) : almacenDispensadorList.get(0).getIdEstructura(); 
                
                List<Surtimiento> surtimientoList = calculaSurtimientos(prescInsumoList);
                List<AlmacenInsumoComprometido> comprometidoLis = calculaComprometidos(prescInsumoList);

                result = prescripcionService.editarPrescripcion(prescripcionSelected, diagnosticoPacienteList, prescInsumoList, surtimientoList, comprometidoLis);

            } catch (Exception exc) {
                LOGGER.error(RESOURCES.getString(prcRegistroErr), exc);

            }
        }
        if (!result) {
            LOGGER.error(RESOURCES.getString(prcRegistroErr));
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcRegistroErr), null);

        } else {
            verEditarPaciente();
            Mensaje.showMessage("Info", RESOURCES.getString("prc.registro.ok"), null);

        }
        return result;
    }

    public void imprimirPorId(String idPrescripcion) throws Exception {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.imprimir()");
        prescripcionExtendedSelected = (Prescripcion_Extended) prescripcionService.obtener(new Prescripcion(idPrescripcion));
        imprimir();
    }

    public void imprimir() throws Exception {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.imprimir()");
        boolean status = Constantes.INACTIVO;
        try {
            FacesContext ctxt = FacesContext.getCurrentInstance();
            ExternalContext ext = ctxt.getExternalContext();
            URI uri = new URI(ext.getRequestScheme(),
                    null,
                    ext.getRequestServerName(),
                    ext.getRequestServerPort(),
                    ext.getRequestContextPath(),
                    null,
                    null);
            String url = uri.toASCIIString();

                status = Constantes.ACTIVO;
                archivo = url + "/resources/tmp/prescripcion.pdf";
        } catch (URISyntaxException e) {
            LOGGER.error("Error al generar la Impresión: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public void habilitarResurtimiento() {
        try {
            this.resurtimiento = 1;
            this.editable = true;
            this.prescripcionInsumo.setDuracion(null);
            this.resurtimientoDisabled = true;
            if (this.checkResurtimiento) {
                this.resurtimientoDisabled = false;
                this.prescripcionInsumo.setDuracion(this.noDiasResurtimiento);
                this.editable = false;
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo :: habilitarResurtimiento: {}", e.getMessage());
        }
    }



    public void setHuboError(boolean huboError) {
        this.huboError = huboError;
    }
    
    public boolean isHuboError() {
        return huboError;
    }

    
    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }
    
    public String getArchivo() {
        return archivo;
    }

    public String getCadenaBusqueda() {
        return cadenaBusqueda;
    }

    public void setCadenaBusqueda(String cadenaBusqueda) {
        this.cadenaBusqueda = cadenaBusqueda;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
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

    public List<Prescripcion> getPrescripcionList() {
        return prescripcionList;
    }

    public void setPrescripcionList(List<Prescripcion> prescripcionList) {
        this.prescripcionList = prescripcionList;
    }

    public Prescripcion getPrescripcionSelected() {
        return prescripcionSelected;
    }

    public void setPrescripcionSelected(Prescripcion prescripcionSelected) {
        this.prescripcionSelected = prescripcionSelected;
    }

    
    public Integer getSizePacienteList() {
        if (pacienteExtendList != null) {
            return pacienteExtendList.size();
        } else {
            return 0;
        }
    }
    
    public Integer getSizePrescripcionList() {
        if (prescripcionExtendedList != null) {
            return prescripcionExtendedList.size();
        } else {
            return 0;
        }
    }


    public boolean isElementoSeleccionado() {
        return elementoSeleccionado;
    }

    public void setElementoSeleccionado(boolean elementoSeleccionado) {
        this.elementoSeleccionado = elementoSeleccionado;
    }

    public List<Prescripcion> getPrescripcionListSelected() {
        return prescripcionListSelected;
    }

    public void setPrescripcionListSelected(List<Prescripcion> prescripcionListSelected) {
        this.prescripcionListSelected = prescripcionListSelected;
    }

    public List<TransaccionPermisos> getPermisosList() {
        return permisosList;
    }

    public void setPermisosList(List<TransaccionPermisos> permisosList) {
        this.permisosList = permisosList;
    }

    public List<Paciente_Extended> getPacienteExtendList() {
        return pacienteExtendList;
    }

    public void setPacienteExtendList(List<Paciente_Extended> pacienteExtendList) {
        this.pacienteExtendList = pacienteExtendList;
    }

    public Paciente_Extended getPacienteExtendSelected() {
        return pacienteExtendSelected;
    }

    public void setPacienteExtendSelected(Paciente_Extended pacienteExtendSelected) {
        this.pacienteExtendSelected = pacienteExtendSelected;
    }

    public List<Paciente_Extended> getPacienteExtendListSelected() {
        return pacienteExtendListSelected;
    }

    public void setPacienteExtendListSelected(List<Paciente_Extended> pacienteExtendListSelected) {
        this.pacienteExtendListSelected = pacienteExtendListSelected;
    }

    public List<Prescripcion_Extended> getPrescripcionExtendedList() {
        return prescripcionExtendedList;
    }

    public void setPrescripcionExtendedList(List<Prescripcion_Extended> prescripcionExtendedList) {
        this.prescripcionExtendedList = prescripcionExtendedList;
    }

    public Prescripcion_Extended getPrescripcionExtendedSelected() {
        return prescripcionExtendedSelected;
    }

    public void setPrescripcionExtendedSelected(Prescripcion_Extended prescripcionExtendedSelected) {
        this.prescripcionExtendedSelected = prescripcionExtendedSelected;
    }

    public List<Diagnostico> getDiagnosticoList() {
        return diagnosticoList;
    }

    public void setDiagnosticoList(List<Diagnostico> diagnosticoList) {
        this.diagnosticoList = diagnosticoList;
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

    public Diagnostico getDiagnosticoSelected() {
        return diagnosticoSelected;
    }

    public void setDiagnosticoSelected(Diagnostico diagnosticoSelected) {
        this.diagnosticoSelected = diagnosticoSelected;
    }

    public void setInsumoList(List<Medicamento> insumoList) {
        this.insumoList = insumoList;
    }

    public void setInsumoSelected(Medicamento insumoSelected) {
        this.insumoSelected = insumoSelected;
    }

    public Medicamento getInsumoSelected() {
        return insumoSelected;
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

    public Diagnostico getDiagnosticoDeterminado() {
        return diagnosticoDeterminado;
    }

    public void setDiagnosticoDeterminado(Diagnostico diagnosticoDeterminado) {
        this.diagnosticoDeterminado = diagnosticoDeterminado;
    }

    public PrescripcionInsumo_Extended getPrescripcionInsumo() {
        return prescripcionInsumo;
    }

    public void setPrescripcionInsumo(PrescripcionInsumo_Extended prescripcionInsumo) {
        this.prescripcionInsumo = prescripcionInsumo;
    }

    public boolean isMuestraConfirmacion() {
        return muestraConfirmacion;
    }

    public void setMuestraConfirmacion(boolean muestraConfirmacion) {
        this.muestraConfirmacion = muestraConfirmacion;
    }

    public String getFirmaMedico() {
        return firmaMedico;
    }

    public void setFirmaMedico(String firmaMedico) {
        this.firmaMedico = firmaMedico;
    }

    public String getNotaMedica() {
        return notaMedica;
    }

    public void setNotaMedica(String notaMedica) {
        this.notaMedica = notaMedica;
    }

    public boolean isFirmaValida() {
        return firmaValida;
    }

    public void setFirmaValida(boolean firmaValida) {
        this.firmaValida = firmaValida;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public String getTipoPrescripcion() {
        return tipoPrescripcion;
    }

    public void setTipoPrescripcion(String tipoPrescripcion) {
        this.tipoPrescripcion = tipoPrescripcion;
    }

    public boolean isPrescripcionMayor24Horas() {
        return prescripcionMayor24Horas;
    }

    public void setPrescripcionMayor24Horas(boolean prescripcionMayor24Horas) {
        this.prescripcionMayor24Horas = prescripcionMayor24Horas;
    }

    public Usuario getUsuarioSession() {
        return usuarioSession;
    }

    public void setUsuarioSession(Usuario usuarioSession) {
        this.usuarioSession = usuarioSession;
    }

    public Medicamento_Extended getInsumoExtendedSelected() {
        return insumoExtendedSelected;
    }

    public void setInsumoExtendedSelected(Medicamento_Extended insumoExtendedSelected) {
        this.insumoExtendedSelected = insumoExtendedSelected;
    }

    public boolean isResurtimientoDisabled() {
        return resurtimientoDisabled;
    }

    public void setResurtimientoDisabled(boolean resurtimientoDisabled) {
        this.resurtimientoDisabled = resurtimientoDisabled;
    }

    public Integer getResurtimiento() {
        return resurtimiento;
    }

    public void setResurtimiento(Integer resurtimiento) {
        this.resurtimiento = resurtimiento;
    }

    public boolean isCheckResurtimiento() {
        return checkResurtimiento;
    }

    public void setCheckResurtimiento(boolean checkResurtimiento) {
        this.checkResurtimiento = checkResurtimiento;
    }

    public Integer getNoDiasResurtimiento() {
        return noDiasResurtimiento;
    }

    public void setNoDiasResurtimiento(Integer noDiasResurtimiento) {
        this.noDiasResurtimiento = noDiasResurtimiento;
    }

    public boolean isPacienteConCita() {
        return pacienteConCita;
    }

    public void setPacienteConCita(boolean pacienteConCita) {
        this.pacienteConCita = pacienteConCita;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

}
