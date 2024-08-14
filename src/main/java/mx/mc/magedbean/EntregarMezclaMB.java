package mx.mc.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import mx.mc.enums.EstatusPaciente_Enum;
import mx.mc.enums.EstatusPrescripcion_Enum;
import mx.mc.enums.EstatusSolucion_Enum;
import mx.mc.enums.EstatusSurtimiento_Enum;
import mx.mc.enums.TipoAreaEstructura_Enum;
import mx.mc.enums.TipoImpresora_Enum;
import mx.mc.enums.TipoTemplateEtiqueta_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.lazy.DispensacionSolucionLazy;
import mx.mc.model.CodigoInsumo;
import mx.mc.model.Diagnostico;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.EnvaseContenedor;
import mx.mc.model.Estructura;
import mx.mc.model.EtiquetaInsumo;
import mx.mc.model.Impresora;
import mx.mc.model.MotivosRechazo;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.Prescripcion;
import mx.mc.model.Protocolo;
import mx.mc.model.Solucion;
import mx.mc.model.Surtimiento;
import mx.mc.model.SurtimientoInsumo_Extend;
import mx.mc.model.Surtimiento_Extend;
import mx.mc.model.TemplateEtiqueta;
import mx.mc.model.TipoSolucion;
import mx.mc.model.Usuario;
import mx.mc.model.ViaAdministracion;
import mx.mc.model.Visita;
import mx.mc.service.DiagnosticoService;
import mx.mc.service.EntidadHospitalariaService;
import mx.mc.service.EnvaseContenedorService;
import mx.mc.service.EstructuraService;
import mx.mc.service.ImpresoraService;
import mx.mc.service.MotivosRechazoService;
import mx.mc.service.PrescripcionService;
import mx.mc.service.ProtocoloService;
import mx.mc.service.ReportesService;
import mx.mc.service.SolucionService;
import mx.mc.service.SurtimientoInsumoService;
import mx.mc.service.SurtimientoService;
import mx.mc.service.TemplateEtiquetaService;
import mx.mc.service.TipoSolucionService;
import mx.mc.service.UsuarioService;
import mx.mc.service.ViaAdministracionService;
import mx.mc.service.VisitaService;
import mx.mc.util.CodigoBarras;
import mx.mc.util.Comunes;
import mx.mc.util.FechaUtil;
import mx.mc.util.Mensaje;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
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
public class EntregarMezclaMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(EntregarMezclaMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    private boolean elementoSeleccionado;
    private String cadenaBusqueda;
    private SesionMB sesion;
    private PermisoUsuario permiso;
    private Usuario usuarioSelected;
    private boolean editable;
    private String nombreCompleto;
    private String surSinAlmacen;
    private Integer estatusSolucion;
    private transient List<String> tipoPrescripcionSelectedList;
    private transient List<Integer> listEstatusSurtimiento;
    private ParamBusquedaReporte paramBusquedaReporte;
    private boolean solucion;
    private String paramEstatus;
    private DispensacionSolucionLazy dispensacionSolucionLazy;
    private String comentarios;
    private String mezcla_correcta;
    private String acondicionada;
    private String entregada;
    private String no_acondicionada;
    private String errRegistroIncorrecto;
    private String surIncorrecto;
    private String diagnosticos;
    private Date estabilidad;
    private Integer cantPrint;
    private transient List<Impresora> listaImpresoras;
    private Surtimiento surtim;
    private String folioSolucion;
    private String loteSolucion;
    private Date caducidadSolucion;
    private String tipoArchivoImprimir;
    private String docEtiqueta;
    private String docReporte;
    private boolean imprimeEtiqueta;
    private boolean imprimeReporte;
    private String idPrintSelect;
    private Boolean activeBtnPrint;
    private Integer numTemp;
    private transient List<TemplateEtiqueta> templateList;
    private String template;

    @Autowired
    private transient EstructuraService estructuraService;
    private transient List<Estructura> listServiciosQueSurte;

    @Autowired
    private transient SurtimientoService surtimientoService;
    private Surtimiento_Extend surtimientoExtendedSelected;
    private transient List<Surtimiento_Extend> surtimientoExtendedListSelected;
    private transient List<Surtimiento_Extend> surtimientoExtendedList;

    @Autowired
    private transient SurtimientoInsumoService surtimientoInsumoService;
    private SurtimientoInsumo_Extend surtimientoInsumoExtendedSelected;
    private transient List<SurtimientoInsumo_Extend> surtimientoInsumoExtendedList;

    @Autowired
    private transient SolucionService solucionService;
    private Solucion solucionSelect;

    @Autowired
    private transient MotivosRechazoService motivosRechazoService;
    private transient List<MotivosRechazo> listaMotivosRechazo;
    private Integer idMotivoRechazo;

    @Autowired
    private transient ImpresoraService impresoraService;

    @Autowired
    private transient TemplateEtiquetaService templateService;

    @Autowired
    private transient ReportesService reportesService;

    @Autowired
    private transient PrescripcionService prescripcionService;

    @Autowired
    private transient UsuarioService usuarioService;
    private transient List<Usuario> listUsuarios;
    private Usuario usrselected;

    private transient List<Estructura> listaEstructurasRecibir;
    private String idSerivicioRecive;

    @Autowired
    private transient EntidadHospitalariaService entidadHospitalariaService;

    @Autowired
    private transient DiagnosticoService diagnosticoService;

    @Autowired
    private transient EnvaseContenedorService envaseService;

    @Autowired
    private transient ViaAdministracionService viaAdministracionService;

    @Autowired
    private transient TipoSolucionService tiposolucionService;

    @Autowired
    private transient ProtocoloService protocoloService;

    @Autowired
    private transient VisitaService visitaService;

    private Prescripcion prescripcionSelected;
    private Date fechaActual;

    private transient List<Integer> estatusSolucionLista;

    @PostConstruct
    public void init() {
        LOGGER.trace("mx.mc.magedbean.EntregarMezclaMB.init()");
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        permiso = Comunes.obtenerPermisos(Transaccion_Enum.ENTREGASOLUCION.getSufijo());
        editable = false;
        usuarioSelected = sesion.getUsuarioSelected();
        nombreCompleto = (usuarioSelected.getNombre() + " " + usuarioSelected.getApellidoPaterno() + " " + usuarioSelected.getApellidoMaterno()).toUpperCase();
        paramEstatus = "estatus";
        surSinAlmacen = "sur.sin.almacen";
        errRegistroIncorrecto = "err.registro.incorrecto";
        surIncorrecto = "sur.incorrecto";
        listaMotivosRechazo = new ArrayList<>();

        listEstatusSurtimiento = new ArrayList<>();
        listEstatusSurtimiento.add(EstatusSurtimiento_Enum.SURTIDO.getValue());

        solucion = Constantes.ACTIVO;
        this.estatusSolucionLista = new ArrayList<>();
        this.estatusSolucionLista.add(EstatusSolucion_Enum.ACONDICIONAMIENTO_CONFORME.getValue());

        paramBusquedaReporte = new ParamBusquedaReporte();
        estatusSolucion = EstatusSolucion_Enum.ACONDICIONAMIENTO_CONFORME.getValue();
        mezcla_correcta = Constantes.MEZCLA_CORRECTA;
        acondicionada = Constantes.ACONDICIONADA;
        entregada = Constantes.ENTREGADA;
        no_acondicionada = Constantes.NO_ACONDICIONADA;
        idMotivoRechazo = Constantes.ESTATUS_INACTIVO;
        comentarios = "";
        diagnosticos = "";
        estabilidad = new Date();
        surtim = new Surtimiento();
        folioSolucion = "";
        loteSolucion = "";
        caducidadSolucion = new Date();
        tipoArchivoImprimir = "";
        docEtiqueta = TipoImpresora_Enum.ETIQUETA.getValue();
        docEtiqueta = TipoImpresora_Enum.ETIQUETA.getValue();
        imprimeReporte = true;
        docReporte = TipoImpresora_Enum.NORMAL.getValue();
        numTemp = 0;
        templateList = new ArrayList<>();
        template = StringUtils.EMPTY;
        surtimientoExtendedListSelected = new ArrayList<>();
        obtenerMotivosRechazoActivos();
        obtieneServiciosQuePuedeSurtir();
        obtenerSurtimientos();
        obtenerEstructurasRecibir();
        obtenerTemplatesEtiquetas();
    }

    private void obtenerMotivosRechazoActivos() {
        LOGGER.trace("mx.mc.magedbean.AcondicionarSolucionesMB.obtenerMotivosRechazoActivos()");
        try {
            listaMotivosRechazo = motivosRechazoService.obtenerMotivosRechazoActivos();
        } catch (Exception ex) {
            LOGGER.error("Error al buscar los motivos de Rechazo  " + ex.getMessage());
        }
    }

    /**
     * Obtiene los Servicios que puede surtir el usuario, dependiendo de la
     * estructura jerarquica a la que esté asignada el usuario en sesion
     * Adicionalmente El area a la que el usuario este asignada, debe ser de
     * tipo almacen y que tenga una asignación de servicio hospitalario queF
     * puede surtir
     */
    private void obtieneServiciosQuePuedeSurtir() {
        LOGGER.trace("mx.mc.magedbean.AcondicionarSolucionesMB.obtieneServiciosQuePuedeSurtir()");
        listServiciosQueSurte = new ArrayList<>();
        permiso.setPuedeVer(false);

        Estructura estSol = null;
        try {
            estSol = estructuraService.obtener(new Estructura(usuarioSelected.getIdEstructura()));

        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }

        if (estSol == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surSinAlmacen), null);

        } else if (estSol.getIdTipoAreaEstructura() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surSinAlmacen), null);

        } else if (!Objects.equals(estSol.getIdTipoAreaEstructura(), TipoAreaEstructura_Enum.ALMACEN.getValue())) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.almacen.incorrectado"), null);

        } else {
            permiso.setPuedeVer(true);
            try {
                if (usuarioSelected.getIdEstructura() != null) {
                    List<Estructura> estructuraServicio = estructuraService.obtenerServicioQueSurtePorIdEstructura(usuarioSelected.getIdEstructura());
                    if (estructuraServicio.isEmpty()) {
                        estructuraServicio.add(estSol);
                    }
                    for (Estructura servicio : estructuraServicio) {
                        listServiciosQueSurte.add(servicio);
                        List<String> idsEstructura = estructuraService.obtenerIdsEstructuraJerarquica(servicio.getIdEstructura(), true);
                        for (String item : idsEstructura) {
                            listServiciosQueSurte.add(estructuraService.obtenerEstructura(item));
                        }
                    }
                }
            } catch (Exception excp) {
                LOGGER.error("Error al obtener Servicios que puede surtir el usuario: {}", excp.getMessage());
            }
        }

    }

    public void obtenerSurtimientos() {
        LOGGER.trace("mx.mc.magedbean.AcondicionarSolucionesMB.obtenerSoluciones()");

        boolean status = Constantes.INACTIVO;
        boolean modal = Constantes.INACTIVO;
        surtimientoExtendedList = new ArrayList<>();

        if (!permiso.isPuedeVer()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.transaccion"), null);
            status = Constantes.ACTIVO;

        } else if (usuarioSelected == null || usuarioSelected.getIdEstructura() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surSinAlmacen), null);
            status = Constantes.ACTIVO;

        } else if (listServiciosQueSurte.isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.almacen.incorrectado"), null);

        } else {
            try {

                if (cadenaBusqueda != null && cadenaBusqueda.trim().isEmpty()) {
                    cadenaBusqueda = null;
                }

// regla: Listar prescripciones donde el paciente tiene estatus: Asignado a Servicio o Asignación a Cama
                List<Integer> listEstatusPacienteSol = new ArrayList<>();
                listEstatusPacienteSol.add(EstatusPaciente_Enum.ASIGNADO_A_SERVICIO.getValue());
                listEstatusPacienteSol.add(EstatusPaciente_Enum.ASIGNADO_A_CAMA.getValue());
//TODO: Se elimina la regla de paciente asignado a servicio y cama por reglas de HJM
                listEstatusPacienteSol = null;

// regla: Listar prescripciones solo con estatus de PROGRAMADA
                List<Integer> listEstatusPrescripcion = new ArrayList<>();
                listEstatusPrescripcion.add(EstatusPrescripcion_Enum.PROGRAMADA.getValue());
                listEstatusPrescripcion.add(EstatusPrescripcion_Enum.FINALIZADA.getValue());
                listEstatusPrescripcion.add(EstatusPrescripcion_Enum.CANCELADA.getValue());

// regla: filtro de tipo de prescripción: Normal, Dósis Única, Normal o todas
                if (tipoPrescripcionSelectedList != null && tipoPrescripcionSelectedList.isEmpty()) {
                    tipoPrescripcionSelectedList = null;
                }

// regla: listar prescripciones con fecha igual o menor a la fecha actual, nunca prescripciones futuras
                Date fechaProgramada = new java.util.Date();

                paramBusquedaReporte.setNuevaBusqueda(true);
                paramBusquedaReporte.setCadenaBusqueda(cadenaBusqueda);

                int tipoProceso = Constantes.SIN_TIPO_PROCESO;
                dispensacionSolucionLazy = new DispensacionSolucionLazy(
                        surtimientoService,
                        paramBusquedaReporte,
                        fechaProgramada,
                        tipoPrescripcionSelectedList,
                        listEstatusPacienteSol,
                        listEstatusPrescripcion,
                        listEstatusSurtimiento,
                        listServiciosQueSurte,
                        solucion,
                        estatusSolucionLista
                        , null, tipoProceso, false
                );

                LOGGER.debug("Resultados: {}", dispensacionSolucionLazy.getTotalReg());

                status = Constantes.ACTIVO;

            } catch (Exception ex) {
                LOGGER.error(RESOURCES.getString("prc.pac.lista"), ex);
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.pac.lista"), null);
            }
        }
        PrimeFaces.current().ajax().addCallbackParam("modal", modal);
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    public void onRowSelectSurtimiento(SelectEvent event) {
        LOGGER.trace("mx.mc.magedbean.AcondicionarSolucionesMB.onRowSelectSurtimiento()");
        surtimientoInsumoExtendedSelected = (SurtimientoInsumo_Extend) event.getObject();
        if (surtimientoInsumoExtendedSelected != null) {
            elementoSeleccionado = true;
        }
    }

    public void onRowUnselectSurtimiento() {
        LOGGER.trace("mx.mc.magedbean.AcondicionarSolucionesMB.onRowUnselectSurtimiento()");
        surtimientoInsumoExtendedSelected = null;
        elementoSeleccionado = false;
    }

    public void onTabChange(TabChangeEvent evt) {
// regla: Listar prescripciones solo con estatus de surtimiento programado
        this.estatusSolucionLista = new ArrayList<>();

        String valorStatus = evt.getTab().getId();
        switch (valorStatus) {
            case Constantes.ACONDICIONADA:
                estatusSolucion = EstatusSolucion_Enum.ACONDICIONAMIENTO_CONFORME.getValue();
                break;
            case Constantes.ENTREGADA:
                estatusSolucion = EstatusSolucion_Enum.MEZCLA_ENTREGADA.getValue();
                break;

            case Constantes.NO_ACONDICIONADA:
                estatusSolucion = EstatusSolucion_Enum.MEZCLA_NO_ENTREGADA.getValue();
                break;
        }
        this.estatusSolucionLista.add(estatusSolucion);
        obtenerSurtimientos();
    }

    /**
     * Muestra el Detalle de cada surtimiento de cada prescripción
     */
    public void verSolucion() {
        LOGGER.trace("mx.mc.magedbean.AcondicionarSolucionesMB.verSolucion()");
        boolean status = Constantes.INACTIVO;
        boolean modal = Constantes.INACTIVO;
        comentarios = "";
        idMotivoRechazo = Constantes.ES_INACTIVO;
        if (!permiso.isPuedeVer()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.transaccion"), null);

        } else if (usuarioSelected == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surSinAlmacen), null);

        } else if (surtimientoExtendedSelected == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);

        } else if (surtimientoExtendedSelected.getIdPrescripcion() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);

        } else if (surtimientoExtendedSelected.getIdSurtimiento() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);

        } else if (surtimientoExtendedSelected.getIdEstatusSurtimiento() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);

        } else {
            try {

                String idSurtimiento = surtimientoExtendedSelected.getIdSurtimiento();

                surtimientoInsumoExtendedList = new ArrayList<>();
                boolean mayorCero = true;
                surtimientoInsumoExtendedList.addAll(surtimientoInsumoService.obtenerSurtimientoInsumosByIdSurtimiento(idSurtimiento, mayorCero));

                obtenerPrescripcion();
                obtenerSolucion();
                obtenerIdentificacionMezcla();
                calculaCaducidadMezcla();
                evaluaEdicion();

                String idSolucion = null;
                solucionSelect = solucionService.obtenerSolucion(idSolucion, idSurtimiento);
                if (solucionSelect != null) {
                    if (solucionSelect.getProteccionLuz() == null) {
                        solucionSelect.setProteccionLuz(0);
                    }
                    if (solucionSelect.getProteccionTemp() == null) {
                        solucionSelect.setProteccionTemp(0);
                    }
                }

                if (solucionSelect.getIdEstatusSolucion() == EstatusSolucion_Enum.MEZCLA_ENTREGADA.getValue()) {
                    usrselected = usuarioService.obtenerUsuarioByIdUsuario(solucionSelect.getIdUsuarioRecibe());
                    idSerivicioRecive = solucionSelect.getIdServicioRecibe();
                    comentarios = solucionSelect.getComentarioRecibe();
                } else {
                    usrselected = null;
                    idSerivicioRecive = "";
                    comentarios = "";
                }

                status = Constantes.ACTIVO;
            } catch (Exception ex) {
                LOGGER.error(RESOURCES.getString(surIncorrecto), ex);
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surIncorrecto), null);
            }
        }
        if (estatusSolucion == EstatusSolucion_Enum.MEZCLA_ENTREGADA.getValue()
                || estatusSolucion == EstatusSolucion_Enum.MEZCLA_NO_ENTREGADA.getValue()) {
            modal = Constantes.ACTIVO;
        } else {
            modal = Constantes.INACTIVO;
        }
        PrimeFaces.current().ajax().addCallbackParam("modal", modal);
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    private void evaluaEdicion() {
        LOGGER.trace("mx.mc.magedbean.PreparacionSolucionMB.evaluaEdicion()");
        if (this.solucionSelect != null) {
            if (this.solucionSelect.getIdEstatusSolucion() != null) {
                if (permiso.isPuedeAutorizar()
                        || ((permiso.isPuedeCrear() || permiso.isPuedeEditar())
                        && this.solucionSelect.getIdEstatusSolucion() == EstatusSolucion_Enum.ACONDICIONAMIENTO_CONFORME.getValue())) {
                    this.editable = true;
                }
            }
        }
    }

    private String claveMezcla;
    private String loteMezcla;
    private Date caducidadMezcla;
    private Integer estabilidadMezcla;

    private void obtenerIdentificacionMezcla() {
        LOGGER.trace("mx.mc.magedbean.PreparacionSolucionMB.obtenerIdentificacionMezcla()");
        if (this.surtimientoExtendedSelected != null) {
            if (this.surtimientoExtendedSelected.getClaveAgrupada() != null) {
                CodigoInsumo cin = CodigoBarras.parsearCodigoDeBarras(this.surtimientoExtendedSelected.getClaveAgrupada());
                if (cin != null) {
                    this.claveMezcla = cin.getClave();
                    this.loteMezcla = cin.getLote();
                    this.caducidadMezcla = new java.util.Date(); // cin.getFecha();
                }
            }
        }
    }

    private void calculaCaducidadMezcla() {
        LOGGER.trace("mx.mc.magedbean.PreparacionSolucionMB.calculaCaducidadMezcla()");
        if (this.estabilidadMezcla > 0) {
            if (solucionSelect.getFechaPrepara() != null) {
                caducidadMezcla = FechaUtil.sumarRestarHorasFecha(solucionSelect.getFechaPrepara(), estabilidadMezcla);
            } else {
                this.caducidadMezcla = FechaUtil.sumarRestarHorasFecha(new java.util.Date(), estabilidadMezcla);
            }
        }
    }

    
    private void obtenerPrescripcion() {
        LOGGER.trace("mx.mc.magedbean.PreparacionSolucionMB.obtenerPrescripcion()");
        prescripcionSelected = new Prescripcion();
        try {
            if (surtimientoExtendedSelected != null) {
                prescripcionSelected = prescripcionService.obtener(new Prescripcion(surtimientoExtendedSelected.getIdPrescripcion()));
            }
        } catch (Exception e) {
            LOGGER.error("Error al obtenerPrescripcion {} ", e.getMessage());
        }
    }

    private List<String> obtenerDiagnosticos() {
        LOGGER.error("mx.mc.magedbean.PreparacionSolucionMB.obtenerDiagnosticos()");
        List<String> diagnosticoLista = new ArrayList<>();
        try {
            if (surtimientoExtendedSelected != null) {
                if (surtimientoExtendedSelected.getIdPaciente() != null) {
                    Visita v = visitaService.obtener(new Visita(surtimientoExtendedSelected.getIdPaciente()));
                    if (v != null) {
                        List<Diagnostico> listDiagnostico = diagnosticoService.obtenerPorIdPacienteIdVisitaIdPrescripcion(
                                surtimientoExtendedSelected.getIdPaciente(),
                                v.getIdVisita(),
                                prescripcionSelected.getIdPrescripcion());
                        if (listDiagnostico != null) {
                            for (Diagnostico d : listDiagnostico) {
                                diagnosticoLista.add(d.getClave() + " - " + d.getNombre());
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al obtenerDiagnosticos {} ", e.getMessage());
        }
        return diagnosticoLista;
    }

    private void obtenerSolucion() {
        LOGGER.trace("mx.mc.magedbean.PreparacionSolucionMB.obtenerSolucion()");
        Solucion s = new Solucion();
        try {
            if (surtimientoExtendedSelected != null) {
                s.setIdSurtimiento(surtimientoExtendedSelected.getIdSurtimiento());
                solucionSelect = solucionService.obtener(s);
                if (solucionSelect != null) {

                    if (solucionSelect.getIdEstatusSolucion() != null) {
                        this.surtimientoExtendedSelected.setEstatusSurtimiento(EstatusSolucion_Enum.getStatusFromId(solucionSelect.getIdEstatusSolucion()).name());
                    }
                    surtimientoExtendedSelected.setPesoPaciente(solucionSelect.getPesoPaciente());
                    surtimientoExtendedSelected.setTallaPaciente(solucionSelect.getTallaPaciente());
                    surtimientoExtendedSelected.setAreaCorporal(solucionSelect.getAreaCorporal());
                    surtimientoExtendedSelected.setObservaciones(solucionSelect.getObservaciones());
                    surtimientoExtendedSelected.setInstruccionPreparacion(solucionSelect.getInstruccionesPreparacion());
                    if (solucionSelect.getVolumen() != null) {
                        surtimientoExtendedSelected.setVolumenTotal(solucionSelect.getVolumen());
                    }
                    if(solucionSelect.getMinutosInfusion() > 0) {                       
                        surtimientoExtendedSelected.setMinutosInfusion(solucionSelect.getMinutosInfusion());
                    }
                    if (prescripcionSelected != null) {
                        TipoSolucion ts = obtenerTipoSolucion(prescripcionSelected.getIdTipoSolucion());
                        if (ts != null) {
                            surtimientoExtendedSelected.setTipoSolucion(ts.getClave() + " - " + ts.getDescripcion());
                        }

                        ViaAdministracion vad = obtenerViaAdministracion(prescripcionSelected.getIdViaAdministracion());
                        if (vad != null) {
                            surtimientoExtendedSelected.setNombreViaAdministracion(vad.getNombreViaAdministracion());
                        }

                        Protocolo pr = obtenerProtocolo(prescripcionSelected.getIdProtocolo());
                        if (pr != null) {
                            surtimientoExtendedSelected.setNombreProtoclo(pr.getClaveProtocolo() + " - " + pr.getDescripcionProtocolo());
                        }
                        Diagnostico di = obtenerDiagnostico(prescripcionSelected.getIdDiagnostico());
                        if (di != null) {
                            surtimientoExtendedSelected.setNombreDiagnostico(di.getClave() + " - " + di.getNombre());
                        }
                    }
                    EnvaseContenedor ec = obtenerContenedor(solucionSelect.getIdEnvaseContenedor());
                    if (ec != null) {
                        surtimientoExtendedSelected.setNombreEnvaseContenedor(ec.getDescripcion());
                    }
                    claveMezcla = solucionSelect.getClaveMezcla();
                    loteMezcla = solucionSelect.getLoteMezcla();
                    caducidadMezcla = solucionSelect.getCaducidadMezcla();
                    estabilidadMezcla = solucionSelect.getEstabilidadMezcla();
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al obtenerSolucion {} ", e.getMessage());
        }
    }

    private TipoSolucion obtenerTipoSolucion(String idTiposolucion) {
        try {
            return tiposolucionService.obtener(new TipoSolucion(idTiposolucion));
        } catch (Exception e) {
            LOGGER.error("Error al obtenerTipoSolucion {} ", e.getMessage());
        }
        return null;
    }

    private ViaAdministracion obtenerViaAdministracion(Integer idViaAdministracion) {
        try {
            return viaAdministracionService.obtenerPorId(idViaAdministracion);
        } catch (Exception e) {
            LOGGER.error("Error al obtenerViaAdministracion {} ", e.getMessage());
        }
        return null;
    }

    private EnvaseContenedor obtenerContenedor(Integer idEnvaseContenedor) {
        try {
            return envaseService.obtenerXidEnvase(idEnvaseContenedor);
        } catch (Exception e) {
            LOGGER.error("Error al obtenerContenedor {} ", e.getMessage());
        }
        return null;
    }

    private Protocolo obtenerProtocolo(Integer idProtocolo) {
        try {
            return protocoloService.obtener(new Protocolo(idProtocolo));
        } catch (Exception e) {
            LOGGER.error("Error al obtenerProtocolo {} ", e.getMessage());
        }
        return null;
    }

    private Diagnostico obtenerDiagnostico(String idDiagnostico) {
        try {
            return diagnosticoService.obtenerDiagnosticoPorIdDiag(idDiagnostico);
        } catch (Exception e) {
            LOGGER.error("Error al obtenerDiagnostico {} ", e.getMessage());
        }
        return null;
    }

    public void acondicionada() {
        LOGGER.trace("mx.mc.magedbean.AcondicionarSolucionesMB.acondicionada()");
        boolean status = Constantes.INACTIVO;
        try {
            //solucionSelect = solucionService.obtenerXidSurtimiento(surtimientoExtendedSelected.getIdSurtimiento());
            if (this.usuarioSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El usuario es inválido.", null);

            } else if (!permiso.isPuedeCrear() && !permiso.isPuedeEditar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No tiene permiso para realizar esta acción.", null);

            } else if (solucionSelect == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "La solución de la prescripción de mezcla es inválida.", null);

            } else {
                solucionSelect.setFechaAcondicionamiento(new Date());
                solucionSelect.setIdUsuarioAcondiciona(usuarioSelected.getIdUsuario());
                solucionSelect.setIdEstatusSolucion(EstatusSolucion_Enum.ACONDICIONAMIENTO_CONFORME.getValue());
                solucionSelect.setUpdateFecha(new Date());
                solucionSelect.setUpdateIdUsuario(usuarioSelected.getIdUsuario());

                status = solucionService.actualizar(solucionSelect);
                if (!status) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al reagistrar el acondicionamiento de la mezcla. ", null);

                } else {
                    comentarios = "";
                    idMotivoRechazo = Constantes.ES_INACTIVO;
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, "El acondicionamiento de la mezcla se registró correctatamente. ", null);
                }

            }

        } catch (Exception ex) {
            LOGGER.error("Error al confirmar la mezcla acondicionada:  " + ex.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    public void noAcondicionada() {
        LOGGER.trace("mx.mc.magedbean.AcondicionarSolucionesMB.noAcondicionada()");
        boolean status = Constantes.INACTIVO;
        try {
            //solucionSelect = solucionService.obtenerXidSurtimiento(surtimientoExtendedSelected.getIdSurtimiento());
            if (this.usuarioSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El usuario es inválido.", null);

            } else if (!permiso.isPuedeCrear() && !permiso.isPuedeEditar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No tiene permiso para realizar esta acción.", null);

            } else if (solucionSelect == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "La solución de la prescripción de mezcla es inválida.", null);

            } else {
                solucionSelect.setFechaAcondicionamiento(new Date());
                solucionSelect.setIdUsuarioAcondiciona(usuarioSelected.getIdUsuario());
                solucionSelect.setComentarioAcondicionada(comentarios);
                solucionSelect.setIdEstatusSolucion(EstatusSolucion_Enum.MEZCLA_NO_ENTREGADA.getValue());
                solucionSelect.setUpdateFecha(new Date());
                solucionSelect.setUpdateIdUsuario(usuarioSelected.getIdUsuario());

                status = solucionService.actualizar(solucionSelect);
                if (!status) {
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, "Error al registrar la mezcla no acondicionada. ", null);

                } else {
                    comentarios = "";
                    idMotivoRechazo = Constantes.ES_INACTIVO;
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, "La mezcla no acondicionada se registró correctamente. ", null);
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al dejar no acondicionada la mecla:   " + ex.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    public void entregarMezcla() {
        boolean status = Constantes.INACTIVO;
        try {
            if (solucionSelect != null) {
                solucionSelect.setFechaEntrega(new Date());
                solucionSelect.setIdUsuarioRecibe(usuarioSelected.getIdUsuario());
                solucionSelect.setIdServicioRecibe(idSerivicioRecive);
                solucionSelect.setIdEstatusSolucion(EstatusSolucion_Enum.MEZCLA_ENTREGADA.getValue());
//                solucionSelect.setComentarioRecibe(comentarios);
                solucionSelect.setUpdateFecha(new Date());
                solucionSelect.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
                solucionSelect.setFechaEntrega(new Date());

                status = solucionService.actualizar(solucionSelect);
            }
        } catch (Exception ex) {
            LOGGER.error("Error al entregar la mezcla:   " + ex.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
        comentarios = "";
        usrselected = null;
        idSerivicioRecive = "";
        Mensaje.showMessage(Constantes.MENSAJE_INFO, "La Solución se entregó con éxtio", null);
    }

    /**
     * Preimpresión de etiqueta
     *
     * @param item
     */
    public void previewPrint(Surtimiento_Extend item) {
        LOGGER.trace("mx.mc.magedbean.DispensacionSolucionMB.previewPrint()");
        try {
            if (this.usuarioSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Usuario inválido. ", null);

            } else if (!this.permiso.isPuedeVer()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No tiene permisos de esta acción. ", null);

            } else if (item == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Surtimiento de prescripción de mezcla inválida. ", null);

            } else if (item.getIdSurtimiento() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Surtimiento de prescripción de mezcla inválida. ", null);

            } else {
                this.cantPrint = 1;
                this.surtimientoExtendedSelected = item;
                this.listaImpresoras = impresoraService.obtenerListaImpresoraByUsuario(usuarioSelected.getIdUsuario());
                if (listaImpresoras != null) {
                    this.listaImpresoras = listaImpresoras.stream().filter(i -> i.getTipo().equals(TipoImpresora_Enum.ETIQUETA.getValue())).collect(Collectors.toList());
                }
                String idSolucion = null;
                String idSurtimiento = item.getIdSurtimiento();
                this.solucionSelect = solucionService.obtenerSolucion(idSolucion, idSurtimiento);
                if (this.solucionSelect != null) {
                    this.folioSolucion = this.solucionSelect.getClaveMezcla();
                    this.loteSolucion = this.solucionSelect.getLoteMezcla();
                    this.caducidadSolucion = this.solucionSelect.getCaducidadMezcla();
                }
            }

        } catch (Exception ex) {
            LOGGER.error("Error al obtener datos de previsualización de impresión: {}", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al obtener datos de previsualización de impresión. ", null);
        }
    }

    public void changePrint() {
        try {
            activeBtnPrint = Constantes.INACTIVO;
            if (idPrintSelect != null && !idPrintSelect.equals("") && cantPrint > 0) {
                activeBtnPrint = Constantes.ACTIVO;
            }
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al cambiar la impresora: {}", ex.getMessage());
        }
    }

    public void changeDoctoImpresora() {
        try {
            if (tipoArchivoImprimir != null && !tipoArchivoImprimir.equals("")) {
                if (tipoArchivoImprimir.equals(TipoImpresora_Enum.NORMAL.getValue())) {
                    imprimeReporte = false;
                    imprimeEtiqueta = true;
                } else if (tipoArchivoImprimir.equals(TipoImpresora_Enum.ETIQUETA.getValue())) {
                    imprimeEtiqueta = false;
                    imprimeReporte = true;
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error: {}", ex.getMessage());
        }
    }

    /**
     * Obtiene la lista de templates de impresión de etiquetas
     */
    private void obtenerTemplatesEtiquetas() {
        try {
            TemplateEtiqueta o = new TemplateEtiqueta();
            o.setActiva(Constantes.ACTIVOS);
            o.setTipo(TipoTemplateEtiqueta_Enum.E.getValue());
            templateList = templateService.obtenerLista(o);
            numTemp = templateList.size();
            // TODO: 12Mar2020 Permitir la selección de templates
            if (numTemp == 1) {
                template = templateList.get(0).getContenido();
            }
        } catch (Exception ex) {
            LOGGER.error(Constantes.MENSAJE_ERROR, "Error al obtener los templates :: " + ex.getMessage());
        }
    }

    /**
     * Imprime la etiqueta con la ClaveAgrupada despues de surtirse la solucion.
     *
     * @throws Exception
     */
    public void imprimirEtiquetaItem() throws Exception {
        LOGGER.trace("mx.mc.magedbean.EntregarMezclaMB.imprimirEtiquetaItem()");
        boolean status = false;

        try {
            if (surtimientoExtendedSelected != null) {

                Impresora print = impresoraService.obtenerPorIdImpresora(idPrintSelect);
                if (print != null && !"".equals(template)) {
                    EtiquetaInsumo etiqueta = new EtiquetaInsumo();
                    etiqueta.setCaducidad(caducidadSolucion);
                    etiqueta.setOrigen("Origen");
                    etiqueta.setLaboratorio("");
                    etiqueta.setFotosencible("");
                    etiqueta.setTextoInstitucional("");
                    etiqueta.setDescripcion("Nombre Solución");

                    etiqueta.setLote(loteSolucion);
                    etiqueta.setClave(folioSolucion);
                    etiqueta.setTemplate(template);
                    etiqueta.setCantiad(cantPrint);
                    etiqueta.setIpPrint(print.getIpImpresora());
                    etiqueta.setCodigoQR(CodigoBarras.generaCodigoDeBarras(folioSolucion, loteSolucion, caducidadSolucion, null));

                    status = reportesService.imprimirEtiquetaItem(etiqueta);
                    if (status) {
                        Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("paciente.info.impCorrectamente"), null);
                    } else {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("paciente.err.genimpresion"), null);
                    }
                }
            }
        } catch (Exception e) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("paciente.err.conect"), null);
            LOGGER.error("Error en imprimirEtiquetaItem: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    public void cancelImprimirEtiqueta() {
        try {
            idPrintSelect = null;
            cantPrint = 1;
            activeBtnPrint = Constantes.INACTIVO;
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al cancelar la impresión: {}", ex.getMessage());
        }
    }

    public List<Usuario> autocompleteUsuario(String query) {
        LOGGER.trace("mx.mc.magedbean.ReporteMovimientosGralesMB.autocompleteUsuario()");
        List<Usuario> listUsuario = new ArrayList<>();
        try {
            listUsuario.addAll(usuarioService.obtenerUsuarios(query));
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener los usuarios: {}", ex.getMessage());
        }
        return listUsuario;
    }

    private void obtenerEstructurasRecibir() {
        LOGGER.trace("mx.mc.magedbean.AcondicionarSolucionesMB.obtenerEstructurasRecibir()");
        try {
            listaEstructurasRecibir = estructuraService.obtenerEstructuraSnHl7();
        } catch (Exception ex) {
            LOGGER.error("Error al buscar los motivos de Rechazo  " + ex.getMessage());
        }
    }

    public void imprimirReporte() {
        LOGGER.trace("mx.mc.magedbean.AcondicionarSolucionesMB.imprimirReporte()");
        boolean status = false;
        byte[] buffer = null;
        try {

            Estructura estruct = estructuraService.obtenerEstructura(surtimientoExtendedSelected.getIdEstructura());
            if (estruct.getIdEntidadHospitalaria() == null) {
                estruct.setIdEntidadHospitalaria("");
            }

            EntidadHospitalaria enti = entidadHospitalariaService.obtenerEntidadById(estruct.getIdEntidadHospitalaria());
            if (enti == null) {
                enti = new EntidadHospitalaria();
                enti.setDomicilio("");
                enti.setNombre("");
            }
            diagnosticos = "";
            String alergias = "";
            if (surtimientoExtendedSelected == null) {
                surtimientoExtendedSelected = (Surtimiento_Extend) dispensacionSolucionLazy.getRowKey(surtimientoExtendedSelected);
            }

            List<Diagnostico> list = diagnosticoService.obtenerPorIdPacienteIdVisitaIdPrescripcion(surtimientoExtendedSelected.getIdPaciente(), surtimientoExtendedSelected.getIdVisita(), surtimientoExtendedSelected.getIdPrescripcion());
            list.forEach(a -> {
                if (a.getDescripcion().length() > 2) {
                    diagnosticos += a.getDescripcion();
                }
            });

            EnvaseContenedor contenedor = new EnvaseContenedor();
            String idSolucion = null;
            String idSurtimiento = surtimientoExtendedSelected.getIdSurtimiento();
            Solucion solucion = solucionService.obtenerSolucion(idSolucion, idSurtimiento);
            if (solucion != null) {
                contenedor = envaseService.obtenerXidEnvase(solucion.getIdEnvaseContenedor());
            }

            Prescripcion prescripcion = prescripcionService.obtener(new Prescripcion(surtimientoExtendedSelected.getIdPrescripcion()));

            buffer = reportesService.reporteValidacionSoluciones(
                    surtimientoExtendedSelected,
                    solucion,
                    enti,
                    usuarioSelected.getNombre() + ' ' + usuarioSelected.getApellidoPaterno() + ' ' + usuarioSelected.getApellidoMaterno(),
                    alergias,
                    diagnosticos,
                    contenedor.getDescripcion(),
                    prescripcion
            );

            if (buffer != null) {
                SesionMB sesionReporte = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "sesionMB");
                sesionReporte.setReportStream(buffer);
                sesionReporte.setReportName(String.format("OrdenPreparacion_%s.pdf", surtimientoExtendedSelected.getFolio()));
                status = Constantes.ACTIVO;
            }
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener datos del usuario: {}", ex.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    public boolean isElementoSeleccionado() {
        return elementoSeleccionado;
    }

    public void setElementoSeleccionado(boolean elementoSeleccionado) {
        this.elementoSeleccionado = elementoSeleccionado;
    }

    public String getCadenaBusqueda() {
        return cadenaBusqueda;
    }

    public void setCadenaBusqueda(String cadenaBusqueda) {
        this.cadenaBusqueda = cadenaBusqueda;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

    public Usuario getUsuarioSelected() {
        return usuarioSelected;
    }

    public void setUsuarioSelected(Usuario usuarioSelected) {
        this.usuarioSelected = usuarioSelected;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getSurSinAlmacen() {
        return surSinAlmacen;
    }

    public void setSurSinAlmacen(String surSinAlmacen) {
        this.surSinAlmacen = surSinAlmacen;
    }

    public Integer getEstatusSolucion() {
        return estatusSolucion;
    }

    public void setEstatusSolucion(Integer estatusSolucion) {
        this.estatusSolucion = estatusSolucion;
    }

    public List<String> getTipoPrescripcionSelectedList() {
        return tipoPrescripcionSelectedList;
    }

    public void setTipoPrescripcionSelectedList(List<String> tipoPrescripcionSelectedList) {
        this.tipoPrescripcionSelectedList = tipoPrescripcionSelectedList;
    }

    public List<Integer> getListEstatusSurtimiento() {
        return listEstatusSurtimiento;
    }

    public void setListEstatusSurtimiento(List<Integer> listEstatusSurtimiento) {
        this.listEstatusSurtimiento = listEstatusSurtimiento;
    }

    public ParamBusquedaReporte getParamBusquedaReporte() {
        return paramBusquedaReporte;
    }

    public void setParamBusquedaReporte(ParamBusquedaReporte paramBusquedaReporte) {
        this.paramBusquedaReporte = paramBusquedaReporte;
    }

    public boolean isSolucion() {
        return solucion;
    }

    public void setSolucion(boolean solucion) {
        this.solucion = solucion;
    }

    public String getParamEstatus() {
        return paramEstatus;
    }

    public void setParamEstatus(String paramEstatus) {
        this.paramEstatus = paramEstatus;
    }

    public DispensacionSolucionLazy getDispensacionSolucionLazy() {
        return dispensacionSolucionLazy;
    }

    public void setDispensacionSolucionLazy(DispensacionSolucionLazy dispensacionSolucionLazy) {
        this.dispensacionSolucionLazy = dispensacionSolucionLazy;
    }

    public Integer getSizeSurtimientoInsumoExtendedList() {
        if (surtimientoInsumoExtendedList != null) {
            return surtimientoInsumoExtendedList.size();
        } else {
            return 0;
        }
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getMezcla_correcta() {
        return mezcla_correcta;
    }

    public void setMezcla_correcta(String mezcla_correcta) {
        this.mezcla_correcta = mezcla_correcta;
    }

    public String getAcondicionada() {
        return acondicionada;
    }

    public void setAcondicionada(String acondicionada) {
        this.acondicionada = acondicionada;
    }

    public String getEntregada() {
        return entregada;
    }

    public void setEntregada(String entregada) {
        this.entregada = entregada;
    }

    public String getNo_acondicionada() {
        return no_acondicionada;
    }

    public void setNo_acondicionada(String no_acondicionada) {
        this.no_acondicionada = no_acondicionada;
    }

    public String getErrRegistroIncorrecto() {
        return errRegistroIncorrecto;
    }

    public void setErrRegistroIncorrecto(String errRegistroIncorrecto) {
        this.errRegistroIncorrecto = errRegistroIncorrecto;
    }

    public String getSurIncorrecto() {
        return surIncorrecto;
    }

    public void setSurIncorrecto(String surIncorrecto) {
        this.surIncorrecto = surIncorrecto;
    }

    public String getDiagnosticos() {
        return diagnosticos;
    }

    public void setDiagnosticos(String diagnosticos) {
        this.diagnosticos = diagnosticos;
    }

    public List<Estructura> getListServiciosQueSurte() {
        return listServiciosQueSurte;
    }

    public void setListServiciosQueSurte(List<Estructura> listServiciosQueSurte) {
        this.listServiciosQueSurte = listServiciosQueSurte;
    }

    public Surtimiento_Extend getSurtimientoExtendedSelected() {
        return surtimientoExtendedSelected;
    }

    public void setSurtimientoExtendedSelected(Surtimiento_Extend surtimientoExtendedSelected) {
        this.surtimientoExtendedSelected = surtimientoExtendedSelected;
    }

    public List<Surtimiento_Extend> getSurtimientoExtendedList() {
        return surtimientoExtendedList;
    }

    public void setSurtimientoExtendedList(List<Surtimiento_Extend> surtimientoExtendedList) {
        this.surtimientoExtendedList = surtimientoExtendedList;
    }

    public SurtimientoInsumo_Extend getSurtimientoInsumoExtendedSelected() {
        return surtimientoInsumoExtendedSelected;
    }

    public void setSurtimientoInsumoExtendedSelected(SurtimientoInsumo_Extend surtimientoInsumoExtendedSelected) {
        this.surtimientoInsumoExtendedSelected = surtimientoInsumoExtendedSelected;
    }

    public List<SurtimientoInsumo_Extend> getSurtimientoInsumoExtendedList() {
        return surtimientoInsumoExtendedList;
    }

    public void setSurtimientoInsumoExtendedList(List<SurtimientoInsumo_Extend> surtimientoInsumoExtendedList) {
        this.surtimientoInsumoExtendedList = surtimientoInsumoExtendedList;
    }

    public Solucion getSolucionSelect() {
        return solucionSelect;
    }

    public void setSolucionSelect(Solucion solucionSelect) {
        this.solucionSelect = solucionSelect;
    }

    public List<MotivosRechazo> getListaMotivosRechazo() {
        return listaMotivosRechazo;
    }

    public void setListaMotivosRechazo(List<MotivosRechazo> listaMotivosRechazo) {
        this.listaMotivosRechazo = listaMotivosRechazo;
    }

    public Integer getIdMotivoRechazo() {
        return idMotivoRechazo;
    }

    public void setIdMotivoRechazo(Integer idMotivoRechazo) {
        this.idMotivoRechazo = idMotivoRechazo;
    }

    public Date getEstabilidad() {
        return estabilidad;
    }

    public void setEstabilidad(Date estabilidad) {
        this.estabilidad = estabilidad;
    }

    public Integer getCantPrint() {
        return cantPrint;
    }

    public void setCantPrint(Integer cantPrint) {
        this.cantPrint = cantPrint;
    }

    public List<Impresora> getListaImpresoras() {
        return listaImpresoras;
    }

    public void setListaImpresoras(List<Impresora> listaImpresoras) {
        this.listaImpresoras = listaImpresoras;
    }

    public Surtimiento getSurtim() {
        return surtim;
    }

    public void setSurtim(Surtimiento surtim) {
        this.surtim = surtim;
    }

    public String getFolioSolucion() {
        return folioSolucion;
    }

    public void setFolioSolucion(String folioSolucion) {
        this.folioSolucion = folioSolucion;
    }

    public String getLoteSolucion() {
        return loteSolucion;
    }

    public void setLoteSolucion(String loteSolucion) {
        this.loteSolucion = loteSolucion;
    }

    public Date getCaducidadSolucion() {
        return caducidadSolucion;
    }

    public void setCaducidadSolucion(Date caducidadSolucion) {
        this.caducidadSolucion = caducidadSolucion;
    }

    public String getTipoArchivoImprimir() {
        return tipoArchivoImprimir;
    }

    public void setTipoArchivoImprimir(String tipoArchivoImprimir) {
        this.tipoArchivoImprimir = tipoArchivoImprimir;
    }

    public String getDocEtiqueta() {
        return docEtiqueta;
    }

    public void setDocEtiqueta(String docEtiqueta) {
        this.docEtiqueta = docEtiqueta;
    }

    public boolean isImprimeEtiqueta() {
        return imprimeEtiqueta;
    }

    public void setImprimeEtiqueta(boolean imprimeEtiqueta) {
        this.imprimeEtiqueta = imprimeEtiqueta;
    }

    public String getIdPrintSelect() {
        return idPrintSelect;
    }

    public void setIdPrintSelect(String idPrintSelect) {
        this.idPrintSelect = idPrintSelect;
    }

    public Boolean getActiveBtnPrint() {
        return activeBtnPrint;
    }

    public void setActiveBtnPrint(Boolean activeBtnPrint) {
        this.activeBtnPrint = activeBtnPrint;
    }

    public Integer getNumTemp() {
        return numTemp;
    }

    public void setNumTemp(Integer numTemp) {
        this.numTemp = numTemp;
    }

    public List<TemplateEtiqueta> getTemplateList() {
        return templateList;
    }

    public void setTemplateList(List<TemplateEtiqueta> templateList) {
        this.templateList = templateList;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public List<Usuario> getListUsuarios() {
        return listUsuarios;
    }

    public void setListUsuarios(List<Usuario> listUsuarios) {
        this.listUsuarios = listUsuarios;
    }

    public Usuario getUsrselected() {
        return usrselected;
    }

    public void setUsrselected(Usuario usrselected) {
        this.usrselected = usrselected;
    }

    public List<Estructura> getListaEstructurasRecibir() {
        return listaEstructurasRecibir;
    }

    public void setListaEstructurasRecibir(List<Estructura> listaEstructurasRecibir) {
        this.listaEstructurasRecibir = listaEstructurasRecibir;
    }

    public String getIdSerivicioRecive() {
        return idSerivicioRecive;
    }

    public void setIdSerivicioRecive(String idSerivicioRecive) {
        this.idSerivicioRecive = idSerivicioRecive;
    }

    public String getDocReporte() {
        return docReporte;
    }

    public void setDocReporte(String docReporte) {
        this.docReporte = docReporte;
    }

    public boolean isImprimeReporte() {
        return imprimeReporte;
    }

    public void setImprimeReporte(boolean imprimeReporte) {
        this.imprimeReporte = imprimeReporte;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public String getClaveMezcla() {
        return claveMezcla;
    }

    public void setClaveMezcla(String claveMezcla) {
        this.claveMezcla = claveMezcla;
    }

    public String getLoteMezcla() {
        return loteMezcla;
    }

    public void setLoteMezcla(String loteMezcla) {
        this.loteMezcla = loteMezcla;
    }

    public Date getCaducidadMezcla() {
        return caducidadMezcla;
    }

    public void setCaducidadMezcla(Date caducidadMezcla) {
        this.caducidadMezcla = caducidadMezcla;
    }

    public Integer getEstabilidadMezcla() {
        return estabilidadMezcla;
    }

    public void setEstabilidadMezcla(Integer estabilidadMezcla) {
        this.estabilidadMezcla = estabilidadMezcla;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public List<Surtimiento_Extend> getSurtimientoExtendedListSelected() {
        return surtimientoExtendedListSelected;
    }

    public void setSurtimientoExtendedListSelected(List<Surtimiento_Extend> surtimientoExtendedListSelected) {
        this.surtimientoExtendedListSelected = surtimientoExtendedListSelected;
    }

    /**
     * Previsualiza la lista a dispensar de forma colectiva para confirmacion
     */
    public void previsualizaLista() {
        LOGGER.trace("mx.mc.magedbean.EntregarMezclaMB.previsualizaLista()");
        for (Surtimiento_Extend item : surtimientoExtendedListSelected) {
            LOGGER.info(item.getFolio());
        }
    }

}
