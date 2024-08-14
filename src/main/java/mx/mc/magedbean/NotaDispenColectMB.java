package mx.mc.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import mx.mc.enums.EstatusNotaDispenColect_Enum;
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
import mx.mc.model.Cama;
import mx.mc.model.CodigoInsumo;
import mx.mc.model.Diagnostico;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.EnvaseContenedor;
import mx.mc.model.Estructura;
import mx.mc.model.EtiquetaInsumo;
import mx.mc.model.HorarioEntrega;
import mx.mc.model.Impresora;
import mx.mc.model.MotivosRechazo;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.Prescripcion;
import mx.mc.model.Protocolo;
import mx.mc.model.Solucion;
import mx.mc.model.NotaDispenColectDetalle_Extended;
import mx.mc.model.NotaDispenColect_Extended;
import mx.mc.model.Paciente;
import mx.mc.model.PacienteServicio;
import mx.mc.model.PacienteUbicacion;
import mx.mc.model.Surtimiento;
import mx.mc.model.SurtimientoInsumo_Extend;
import mx.mc.model.Surtimiento_Extend;
import mx.mc.model.TemplateEtiqueta;
import mx.mc.model.TipoSolucion;
import mx.mc.model.Turno;
import mx.mc.model.Usuario;
import mx.mc.model.ViaAdministracion;
import mx.mc.model.Visita;
import mx.mc.service.CamaService;
import mx.mc.service.DiagnosticoService;
import mx.mc.service.EntidadHospitalariaService;
import mx.mc.service.EnvaseContenedorService;
import mx.mc.service.EstructuraService;
import mx.mc.service.HorarioEntregaService;
import mx.mc.service.ImpresoraService;
import mx.mc.service.MotivosRechazoService;
import mx.mc.service.NotaDispenColectDetalleService;
import mx.mc.service.NotaDispenColectService;
import mx.mc.service.PacienteService;
import mx.mc.service.PacienteServicioService;
import mx.mc.service.PacienteUbicacionService;
import mx.mc.service.PrescripcionService;
import mx.mc.service.ProtocoloService;
import mx.mc.service.ReportesService;
import mx.mc.service.SolucionService;
import mx.mc.service.SurtimientoInsumoService;
import mx.mc.service.SurtimientoService;
import mx.mc.service.TemplateEtiquetaService;
import mx.mc.service.TipoSolucionService;
import mx.mc.service.TurnoService;
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
public class NotaDispenColectMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(NotaDispenColectMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    private boolean elementoSeleccionado;
    private String cadenaBusqueda;
    private String cadenaBusquedaNotas;
    private String cadenaBusquedaNotasE;
    private String cadenaBusquedaNotasN;
    private SesionMB sesion;
    private PermisoUsuario permiso;
    private Usuario usuarioSelected;
    private boolean editable;
    private String nombreCompleto;
    private String surSinAlmacen;
    private Integer estatusSolucion;
    private Integer estatusNotaDispensacion;
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

    private String mezclaAcondicionada;
    private String mezclaNoAcondicionada;
    private String mezclaEnTransito;
    private String mezclaEntregada;
    private String mezclaNoEntregada;

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

    private Prescripcion prescripcionSelected;
    private Date fechaActual;

    private transient List<Integer> estatusSolucionLista;

    private String idEstructura;
    private String idTipoSolucion;
    private Date fechaParaEntregar;
    private Date fechaParaEntregarFin;

    private String idEstructuraNotas;
    private String idTipoSolucionNotas;
    private Date fechaParaEntregarNotas;
    private Date fechaParaEntregarFinNotas;

    private String idEstructuraNotasE;
    private String idTipoSolucionNotasE;
    private Date fechaParaEntregarNotasE;
    private Date fechaParaEntregarFinNotasE;

    private String idEstructuraNotasN;
    private String idTipoSolucionNotasN;
    private Date fechaParaEntregarNotasN;
    private Date fechaParaEntregarFinNotasN;
    
    private transient List<HorarioEntrega> horarioEntregaLista;
    @Autowired
    private transient HorarioEntregaService horarioEntregaService;
    private transient List<TipoSolucion> tipoSolucionList;

    private transient List<NotaDispenColectDetalle_Extended> notaDispenColectDetalleListaSelected;
    private transient List<NotaDispenColect_Extended> notaDispenColectLista;
    private NotaDispenColect_Extended notaDispenColectSelected;
    private NotaDispenColectDetalle_Extended notaDispenColectDetalleSelected;

    @Autowired
    private transient NotaDispenColectService notaDispenColectService;
    @Autowired
    private transient NotaDispenColectDetalleService notaDispenColectDetalleService;

    private Usuario usuarioEntrega;
    private Usuario usuarioDispensa;
    private Usuario usuarioDistribuye;
    private Usuario usuarioRecibe;
    private String userRecibe;
    private boolean administrador;
    private String tabSeleccionado;

    @Autowired
    private transient PacienteServicioService pacienteServicioService;
    @Autowired
    private transient VisitaService visitaService;
    @Autowired
    private transient PacienteService pacienteService;
    @Autowired
    private transient PacienteUbicacionService pacienteUbicacionService;
    @Autowired
    private transient CamaService camaService;

    @PostConstruct
    public void init() {
        LOGGER.trace("mx.mc.magedbean.NotaDispenColectMB.init()");
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        
        tabSeleccionado = Constantes.ACONDICIONADA;
        
        sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        administrador = sesion.isAdministrador();

        permiso = Comunes.obtenerPermisos(Transaccion_Enum.DISPENSACIONCOLECTIVAMEZCLAS.getSufijo());
        editable = false;
        usuarioSelected = sesion.getUsuarioSelected();

        StringBuilder nombreUsuario = new StringBuilder();
        nombreUsuario.append(this.usuarioSelected.getNombre());
        nombreUsuario.append(StringUtils.SPACE);
        nombreUsuario.append(this.usuarioSelected.getApellidoMaterno());
        nombreUsuario.append(StringUtils.SPACE);
        nombreUsuario.append(this.usuarioSelected.getApellidoPaterno());

        nombreCompleto = (nombreUsuario).toString().toUpperCase();
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

        mezclaAcondicionada = Constantes.ACONDICIONADA;
        mezclaNoAcondicionada = Constantes.NO_ACONDICIONADA;
        mezclaEnTransito = Constantes.EN_TRANSITO;
        mezclaEntregada = Constantes.ENTREGADA;
        mezclaNoEntregada = Constantes.NO_ENTREGADA;
        estatusNotaDispensacion = EstatusNotaDispenColect_Enum.EN_TRANSITO.getValue();

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

        this.notaDispenColectSelected = new NotaDispenColect_Extended();
        this.notaDispenColectSelected.setIdTurno(0);

        fechaActual = new java.util.Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaActual);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        fechaParaEntregar = cal.getTime();
        fechaParaEntregarNotas = cal.getTime();
        fechaParaEntregarNotasE = cal.getTime();
        fechaParaEntregarNotasN = cal.getTime();

        cal = Calendar.getInstance();
        cal.setTime(fechaActual);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        fechaParaEntregarFin = cal.getTime();
        fechaParaEntregarFinNotas = cal.getTime();
        fechaParaEntregarFinNotasE = cal.getTime();
        fechaParaEntregarFinNotasN = cal.getTime();

        obtenerTiposSolucion();
        obtenerMotivosRechazoActivos();
        obtieneServiciosQuePuedeSurtir();
        obtenerEstructurasRecibir();
        obtenerTemplatesEtiquetas();
        obtenerTurnos();
        obtenerContenedores();
        obtenerNotasDispenColect();
        
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

        } else {
            permiso.setPuedeVer(true);
            try {
                if (usuarioSelected.getIdEstructura() != null) {
                    List<Estructura> estructuraServicio = estructuraService.obtenerServicioQueSurtePorIdEstructura(usuarioSelected.getIdEstructura());
                    if (estructuraServicio.isEmpty()) {
                        estructuraServicio.add(estSol);
                    }

                    List<Integer> tipoAreaEstructuraLista = new ArrayList<>();
                    tipoAreaEstructuraLista.add(TipoAreaEstructura_Enum.CONSULTAEXTERNA.getValue());
                    tipoAreaEstructuraLista.add(TipoAreaEstructura_Enum.CONSULTAINTERNA.getValue());
                    tipoAreaEstructuraLista.add(TipoAreaEstructura_Enum.ESPECIALIDAD.getValue());
                    tipoAreaEstructuraLista.add(TipoAreaEstructura_Enum.AREA.getValue());
                    tipoAreaEstructuraLista.add(TipoAreaEstructura_Enum.SERVICIO.getValue());
                    tipoAreaEstructuraLista.add(TipoAreaEstructura_Enum.ALA.getValue());
                    tipoAreaEstructuraLista.add(TipoAreaEstructura_Enum.PABELLO.getValue());
                    listServiciosQueSurte.addAll(estructuraService.getEstructuraByLisTipoAreaEstructura(tipoAreaEstructuraLista));
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

        if (!administrador && idEstructura == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Seleccione servicio. ", null);

        } else if (!administrador && idTipoSolucion == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Seleccione tipo de solución. ", null);

        } else if (!administrador && fechaParaEntregar == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Seleccione fecha programada Inicio. ", null);

        } else if (!administrador && fechaParaEntregarFin == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Seleccione fecha Programada Final. ", null);

        } else {

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
                    listEstatusPrescripcion.add(EstatusPrescripcion_Enum.SURTIDA_PARCIAL.getValue());

// regla: filtro de tipo de prescripción: Normal, Dósis Única, Normal o todas
                    if (tipoPrescripcionSelectedList != null && tipoPrescripcionSelectedList.isEmpty()) {
                        tipoPrescripcionSelectedList = null;
                    }

// regla: listar prescripciones con fecha igual o menor a la fecha actual, nunca prescripciones futuras
                    Date fechaProgramada = new java.util.Date();

                    paramBusquedaReporte.setNuevaBusqueda(true);
                    paramBusquedaReporte.setFechaInicio(fechaParaEntregar);
                    paramBusquedaReporte.setFechaFin(fechaParaEntregarFin);
                    paramBusquedaReporte.setIdEstructura(idEstructura);
                    paramBusquedaReporte.setCadenaBusqueda(cadenaBusqueda);
                    int tipoProceso = 3;
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
                            estatusSolucionLista,
                            idTipoSolucion,
                            tipoProceso,
                            idEstructura);
                    LOGGER.debug("Resultados: {}", dispensacionSolucionLazy.getTotalReg());

                    status = Constantes.ACTIVO;

                } catch (Exception ex) {
                    LOGGER.error(RESOURCES.getString("prc.pac.lista"), ex);
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.pac.lista"), null);
                }
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
        this.estatusSolucionLista = new ArrayList<>();

        String valorStatus = evt.getTab().getId();
        tabSeleccionado = valorStatus;
        switch (valorStatus) {
            case Constantes.ACONDICIONADA:
                estatusSolucion = EstatusSolucion_Enum.ACONDICIONAMIENTO_CONFORME.getValue();
                obtenerSurtimientos();
                break;
            case Constantes.EN_TRANSITO:
                estatusNotaDispensacion = EstatusNotaDispenColect_Enum.EN_TRANSITO.getValue();
                buscaNotasEnTransito();
//                obtenerNotasDispenColect();
//                estatusSolucion = EstatusSolucion_Enum.MEZCLA_EN_DISTRIBUCIÓN.getValue();
                break;
            case Constantes.ENTREGADA:
                estatusNotaDispensacion = EstatusNotaDispenColect_Enum.ENTREGADA.getValue();
                buscaNotasEntregadas();
//                obtenerNotasDispenColect();
//                estatusSolucion = EstatusSolucion_Enum.MEZCLA_ENTREGADA.getValue();
                break;
            case Constantes.NO_ENTREGADA:
                estatusNotaDispensacion = EstatusNotaDispenColect_Enum.NO_ENTREGADA.getValue();
                buscaNotasNoEntregadas();
//                obtenerNotasDispenColect();
//                estatusSolucion = EstatusSolucion_Enum.MEZCLA_NO_ENTREGADA.getValue();
                break;
//            case Constantes.NO_ACONDICIONADA:
////                estatusSolucion = EstatusSolucion_Enum.ACONDICIONAMIENTO_NO_CONFORME.getValue();
//                break;
        }

        this.estatusSolucionLista.add(estatusSolucion);
    }

    /**
     * Muestra el Detalle de cada surtimiento de cada prescripción
     *
     * @param event
     */
    public void verSolucion(SelectEvent event) {
        LOGGER.trace("mx.mc.magedbean.AcondicionarSolucionesMB.verSolucion()");
        boolean status = Constantes.INACTIVO;
        boolean modal = Constantes.INACTIVO;
        comentarios = "";
        idMotivoRechazo = Constantes.ES_INACTIVO;
        if (event != null) {
            try {
                surtimientoExtendedSelected = (Surtimiento_Extend) event.getObject();
            } catch (Exception e) {
                LOGGER.error("Error al obtener solución por idSurtimiento {} ", e.getMessage());
            }
        } else {
            surtimientoExtendedSelected = null;
        }

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

                if (Objects.equals(solucionSelect.getIdEstatusSolucion(), EstatusSolucion_Enum.MEZCLA_ENTREGADA.getValue())) {
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
        if (Objects.equals(estatusSolucion, EstatusSolucion_Enum.MEZCLA_ENTREGADA.getValue())
                || Objects.equals(estatusSolucion, EstatusSolucion_Enum.MEZCLA_NO_ENTREGADA.getValue())) {
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
                        && Objects.equals(this.solucionSelect.getIdEstatusSolucion(), EstatusSolucion_Enum.ACONDICIONAMIENTO_CONFORME.getValue()))) {
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
                    if (solucionSelect.getMinutosInfusion() > 0) {
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
                solucionSelect.setIdEstatusSolucion(EstatusSolucion_Enum.ACONDICIONAMIENTO_NO_CONFORME.getValue());
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
        List<Usuario> listaUsuarios = new ArrayList<>();
        try {
            //Se cambia consulta para solo mostrar usuarios tipo quimico para entrega de solucion en Disp. Colectiva
            listaUsuarios.addAll(usuarioService.obtenerUsuarioQuimicos(query));
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener los usuarios: {}", ex.getMessage());
        }
        return listaUsuarios;
    }

    public void seleccionarUsuario(SelectEvent evt) {
        LOGGER.trace("mx.mc.magedbean.NotaDispenColectMB.seleccionarUsuario()");
        try {
            this.usuarioEntrega = (Usuario) evt.getObject();
        } catch (Exception e) {
            LOGGER.error("Error al seleccionar usuario :: {} ", e.getMessage());
        }
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
            Solucion sol = solucionService.obtenerSolucion(idSolucion, idSurtimiento);
//            Solucion sol = solucionService.obtenerXidSurtimiento(surtimientoExtendedSelected.getIdSurtimiento());
            if (sol != null) {
                contenedor = envaseService.obtenerXidEnvase(sol.getIdEnvaseContenedor());
            }

            Prescripcion prescripcion = prescripcionService.obtener(new Prescripcion(surtimientoExtendedSelected.getIdPrescripcion()));
            buffer = reportesService.reporteValidacionSoluciones(
                    surtimientoExtendedSelected,
                    sol,
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

    private Integer obtenerTurno(Date hora) {
        LOGGER.trace("mx.mc.magedbean.NotaDispenColectMB.obtenerTurno()");
        Integer res = 0;
        try {
            Turno t = turnoService.obtenerPorHora(hora);
            if (t != null) {
                res = t.getIdTurno();
            }
        } catch (Exception e) {
            LOGGER.error("Error al obtener el turno con base en el horario de enntrega :: {} ", e.getMessage());
        }
        return res;
    }

    /**
     * Previsualiza la lista a dispensar de forma colectiva para confirmacion
     */
    public void previsualizaLista() {
        LOGGER.trace("mx.mc.magedbean.EntregarMezclaMB.previsualizaLista()");
        boolean status = Constantes.INACTIVO;

        this.notaDispenColectSelected = new NotaDispenColect_Extended();
        this.notaDispenColectSelected.setIdNotaDispenColect(Comunes.getUUID());
        this.notaDispenColectSelected.setFolio("0");
        this.notaDispenColectSelected.setIdEstructura(idEstructura);
        try {
            Estructura estruct = estructuraService.obtenerEstructura(idEstructura);
            if (estruct != null) {
                this.notaDispenColectSelected.setNombreEstructura(estruct.getNombre());
            }
        } catch (Exception ex) {
            LOGGER.error("Error al obtener la estructura del servicio seleccionado :: {} ", ex.getMessage());
        }

        this.notaDispenColectSelected.setIdTurno(obtenerTurno(fechaActual));

        this.notaDispenColectSelected.setIdTipoSolucion(idTipoSolucion);
        this.notaDispenColectSelected.setFechaEntrega(new java.util.Date());
        this.notaDispenColectSelected.setIdEstatusDispenColect(EstatusNotaDispenColect_Enum.POR_ENTREGAR.getValue());
        this.notaDispenColectSelected.setEstatusNotaDispencolect(EstatusNotaDispenColect_Enum.POR_ENTREGAR.name().replace("_", " "));

        if (this.usuarioSelected == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Usuario inválido", null);

        } else if (this.usuarioSelected.getIdUsuario() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Usuario inválido", null);

        } else if (!this.permiso.isPuedeCrear() && !this.permiso.isPuedeEditar()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No tiene permisos para esta acción", null);

        } else if (surtimientoExtendedListSelected == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Debe Seleccionar al menos una mezcla", null);

        } else if (surtimientoExtendedListSelected.isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Debe Seleccionar al menos una mezcla", null);

        } else {
            this.notaDispenColectDetalleListaSelected = new ArrayList<>();
            NotaDispenColectDetalle_Extended ndcd;
            Integer count = 0;
            for (Surtimiento_Extend item : surtimientoExtendedListSelected) {
                count++;
                ndcd = new NotaDispenColectDetalle_Extended();
                ndcd.setIdNotaDispenColectDetalle(Comunes.getUUID());
                ndcd.setIdNotaDispenColect(this.notaDispenColectSelected.getIdNotaDispenColect());
                ndcd.setIdSurtimiento(item.getIdSurtimiento());
                ndcd.setNumeroMezcla(count);
                ndcd.setIdContenedor(item.getIdContenedor());
                ndcd.setNombreContenedor(obtenerNombreContenedor(item.getIdContenedor()));
                ndcd.setTipoSolucion(item.getTipoSolucion());
                ndcd.setFechaProgramada(item.getFechaProgramada());
                ndcd.setFolio(item.getFolio());
                ndcd.setNombrePaciente(item.getNombrePaciente());
                ndcd.setNombreEstructura(item.getNombreEstructura());
                ndcd.setCama(item.getCama());
                ndcd.setNombreMedico(item.getNombreMedico());
                ndcd.setFechaParaEntregar(item.getFechaParaEntregar());
                ndcd.setTipoPrescripcion(item.getTipoPrescripcion());
                ndcd.setComentarios(item.getCama());
                this.notaDispenColectDetalleListaSelected.add(ndcd);
            }
            status = true;
        }
        PrimeFaces.current().ajax().addCallbackParam("estatus", status);
    }

    private transient List<EnvaseContenedor> contenedoresLista;

    private void obtenerContenedores() {
        LOGGER.trace("mx.mc.magedbean.NotaDispenColectMB.obtenerContenedores()");
        try {
            this.contenedoresLista = envaseService.obtenerLista(new EnvaseContenedor());
        } catch (Exception ex) {
            LOGGER.error("Error al obtener lista de contenedores. {} ", ex.getMessage());
        }
    }

    private String obtenerNombreContenedor(Integer idContenedor) {
        LOGGER.trace("mx.mc.magedbean.NotaDispenColectMB.obtenerNombreContenedor()");
        String res = null;
        for (EnvaseContenedor item : this.contenedoresLista) {
            if (Objects.equals(item.getIdEnvaseContenedor(), idContenedor)) {
                res = item.getDescripcion();
            }
        }
        return res;
    }

    private String validarDatosDeNotaDispColec() {
        LOGGER.trace("mx.mc.magedbean.NotaDispenColectMB.validarDatosDeNotaDispColec()");
        String msg = null;

        if (this.usuarioSelected == null) {
            msg = "Usuario inválido. ";

        } else if (this.usuarioSelected.getIdUsuario() == null) {
            msg = "Usuario inválido. ";

        } else if (this.notaDispenColectSelected == null) {
            msg = "Dispensación colectiva inválida. ";

        } else if (this.notaDispenColectSelected.getFechaEntrega() == null) {
            msg = "Fecha de entrega inválida. ";

        } else if (this.usuarioEntrega == null) {
            msg = "Usuario que entrega inválido. ";

        } else if (this.usuarioEntrega.getIdUsuario() == null) {
            msg = "Usuario que entrega inválido. ";

        } else if (this.notaDispenColectSelected.getIdEstructura() == null) {
            msg = "Servicio de entrtega inválido. ";

        } else if (this.notaDispenColectSelected.getIdTurno() == null) {
            msg = "Turno de entrega inválido. ";

        } else if (this.notaDispenColectSelected.getIdTurno() == 0) {
            msg = "Turno de enntrega inválido. ";

        } else if (this.notaDispenColectDetalleListaSelected == null) {
            msg = "La Dispensación colectiva debe tener al menos una mezcla. ";

        } else if (this.notaDispenColectDetalleListaSelected.isEmpty()) {
            msg = "La Dispensación colectiva debe tener al menos una mezcla. ";
        }
        return msg;
    }

    /**
     * Prevalida la nota de dispensación colectiva que se guardará
     */
    public void validaNotaDispenColec() {
        LOGGER.trace("mx.mc.magedbean.NotaDispenColectMB.validaNotaDispenColec()");
        boolean status = false;

        if (notaDispenColectSelected.getFechaEntrega().before(fechaActual)) {
            Mensaje.showMessage(Constantes.MENSAJE_WARN, "Verifique fecha hora de entrega, es previa al día actual. ", null);
        }
        String msg = validarDatosDeNotaDispColec();
        if (msg != null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, msg, null);

        } else if (!this.permiso.isPuedeCrear()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No tiene permisos para esta acción. ", null);

        } else {
            status = true;
        }
        PrimeFaces.current().ajax().addCallbackParam("estatus", status);

    }

    /**
     * Registra una nueva nota de dispensación colectiva
     */
    public void registrarNotaDispenColectiva() {
        LOGGER.trace("mx.mc.magedbean.NotaDispenColectMB.registrarNotaDispenColectiva()");
        boolean status = false;

        String msg = validarDatosDeNotaDispColec();
        if (msg != null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, msg, null);

        } else if (!this.permiso.isPuedeCrear()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No tiene permisos para esta acción. ", null);

        } else {
            try {
                if (this.notaDispenColectSelected.getFolio().equals("0")) {
                    this.notaDispenColectSelected.setIdEstatusDispenColect(EstatusNotaDispenColect_Enum.EN_TRANSITO.getValue());
                    if (this.usuarioEntrega != null) {
                        this.notaDispenColectSelected.setIdUsuarioEntrega(this.usuarioEntrega.getIdUsuario());
                    }
                    this.notaDispenColectSelected.setFechaDispensa(new java.util.Date());
                    this.notaDispenColectSelected.setIdUsuarioDispensa(this.usuarioSelected.getIdUsuario());

                    this.notaDispenColectSelected.setInsertFecha(new java.util.Date());
                    this.notaDispenColectSelected.setInsertIdUsuario(this.usuarioSelected.getIdUsuario());

                    for (NotaDispenColectDetalle_Extended item : this.notaDispenColectDetalleListaSelected) {
                        item.setInsertFecha(new java.util.Date());
                        item.setInsertIdUsuario(this.usuarioSelected.getIdUsuario());
                        item.setIdEstatusDispenColect(EstatusNotaDispenColect_Enum.EN_TRANSITO.getValue());
                    }
                    status = this.notaDispenColectService.registrarNotaDispenColect(notaDispenColectSelected, notaDispenColectDetalleListaSelected);

                } else {

                    this.notaDispenColectSelected.setFechaDispensa(new java.util.Date());
                    this.notaDispenColectSelected.setIdUsuarioDispensa(this.usuarioSelected.getIdUsuario());

                    if (this.usuarioEntrega != null) {
                        this.notaDispenColectSelected.setIdUsuarioEntrega(this.usuarioEntrega.getIdUsuario());
                    }

                    if (this.usuarioDistribuye != null) {
                        this.notaDispenColectSelected.setIdUsuarioDistribuye(this.usuarioDistribuye.getIdUsuario());
                        this.notaDispenColectSelected.setFechaDistribuye(new java.util.Date());
                    }
                    /* Se cambia por texto libre
                    if (this.usuarioRecibe != null) {
                        this.notaDispenColectSelected.setIdUsuarioRecibe(this.usuarioRecibe.getIdUsuario());
                        this.notaDispenColectSelected.setFechaRecibe(new java.util.Date());
                    }*/
                    if (userRecibe != null && userRecibe != "") {
                        this.notaDispenColectSelected.setUserRecibe(this.userRecibe);
                        this.notaDispenColectSelected.setFechaRecibe(new java.util.Date());
                    }
                    this.notaDispenColectSelected.setUpdateFecha(new java.util.Date());
                    this.notaDispenColectSelected.setUpdateIdUsuario(this.usuarioSelected.getIdUsuario());

                    for (NotaDispenColectDetalle_Extended item : this.notaDispenColectDetalleListaSelected) {
                        item.setUpdateFecha(new java.util.Date());
                        item.setUpdateIdUsuario(this.usuarioSelected.getIdUsuario());
                    }

                    status = this.notaDispenColectService.actualizarNotaDispenColect(notaDispenColectSelected, notaDispenColectDetalleListaSelected);
                }

                if (!status) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al guardar Nota de Dispensación Colectiva.", null);

                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, "Nota de Dispensación Colectiva guardada correctamente.", null);
                    obtenerSurtimientos();
                    obtenerNotasDispenColect();
                    imprimeListaDistribucion();
                }
            } catch (Exception ex) {
                LOGGER.error("Error al guardar la lista de distribución de mezclas {} ", ex.getMessage());
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al guardar Nota de Dispensación Colectiva.", null);
            }
        }
        PrimeFaces.current().ajax().addCallbackParam("estatus", status);
    }

    public void cancelarNotaDispenColectiva() {
        LOGGER.trace("mx.mc.magedbean.NotaDispenColectMB.cancelarNotaDispenColectiva()");
        boolean status = false;

        String msg = validarDatosDeNotaDispColec();
        if (msg != null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, msg, null);

        } else if (!this.permiso.isPuedeEliminar()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No tiene permisos para esta acción. ", null);

        } else {
            try {
                this.notaDispenColectSelected.setIdEstatusDispenColect(EstatusNotaDispenColect_Enum.CANCELADA.getValue());

                this.notaDispenColectSelected.setUpdateFecha(new java.util.Date());
                this.notaDispenColectSelected.setUpdateIdUsuario(this.usuarioSelected.getIdUsuario());

                for (NotaDispenColectDetalle_Extended item : this.notaDispenColectDetalleListaSelected) {
                    item.setUpdateFecha(new java.util.Date());
                    item.setUpdateIdUsuario(this.usuarioSelected.getIdUsuario());
                    item.setIdEstatusDispenColect(EstatusNotaDispenColect_Enum.CANCELADA.getValue());
                }
                status = this.notaDispenColectService.cancelarNotaDispenColect(notaDispenColectSelected, notaDispenColectDetalleListaSelected);

                if (!status) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al cancelar Nota de Dispensación Colectiva.", null);

                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, "Nota de Dispensación Colectiva cancelada correctamente.", null);
                    obtenerSurtimientos();
                    obtenerNotasDispenColect();
                }
            } catch (Exception ex) {
                LOGGER.error("Error al cancelar la lista de distribución de mezclas {} ", ex.getMessage());
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al cancelar Nota de Dispensación Colectiva.", null);
            }
        }
        PrimeFaces.current().ajax().addCallbackParam("estatus", status);
    }

    /**
     * Confirma el registro de la enntrega de una nota de dispensación colectiva
     */
    public void confirmarEntregaNotaDispenColectiva() {
        LOGGER.trace("mx.mc.magedbean.NotaDispenColectMB.confirmarEntregaNotaDispenColectiva()");
        boolean status = false;

        String msg = validarDatosDeNotaDispColec();
        if (msg != null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, msg, null);

        } else if (!this.permiso.isPuedeCrear() || !this.permiso.isPuedeEditar()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No tiene permisos para esta acción. ", null);

        } else {
            try {
                this.notaDispenColectSelected.setIdEstatusDispenColect(EstatusNotaDispenColect_Enum.ENTREGADA.getValue());
                this.notaDispenColectSelected.setUpdateFecha(new java.util.Date());
                this.notaDispenColectSelected.setUpdateIdUsuario(this.usuarioSelected.getIdUsuario());
                this.notaDispenColectSelected.setFechaEntrega(new java.util.Date());

                if (usuarioDistribuye != null) {
                    this.notaDispenColectSelected.setIdUsuarioDistribuye(usuarioDistribuye.getIdUsuario());
                    this.notaDispenColectSelected.setFechaDistribuye(new java.util.Date());
                }
                /*Se cambia por texto libre
                if (usuarioRecibe != null){
                    this.notaDispenColectSelected.setIdUsuarioRecibe(usuarioRecibe.getIdUsuario());
                    this.notaDispenColectSelected.setFechaRecibe(new java.util.Date());
                }*/
                if (userRecibe != null && userRecibe != "") {
                    this.notaDispenColectSelected.setUserRecibe(this.userRecibe);
                    this.notaDispenColectSelected.setFechaRecibe(new java.util.Date());
                }

                for (NotaDispenColectDetalle_Extended item : this.notaDispenColectDetalleListaSelected) {
                    item.setUpdateFecha(new java.util.Date());
                    item.setUpdateIdUsuario(this.usuarioSelected.getIdUsuario());
                    item.setIdEstatusDispenColect(EstatusNotaDispenColect_Enum.ENTREGADA.getValue());
                }
                status = this.notaDispenColectService.actualizarNotaDispenColect(notaDispenColectSelected, notaDispenColectDetalleListaSelected);
                if (!status) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al guardar Nota de Dispensación Colectiva.", null);

                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, "Nota de Dispensación Colectiva guardada correctamente.", null);
                    obtenerSurtimientos();
                    obtenerNotasDispenColect();
                    imprimeListaDistribucion();
                }
            } catch (Exception ex) {
                LOGGER.error("Error al guardar la lista de distribución de mezclas {} ", ex.getMessage());
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al guardar Nota de Dispensación Colectiva.", null);
            }
        }
        PrimeFaces.current().ajax().addCallbackParam("estatus", status);
    }

    public void cierraListaDistribucion() {
        LOGGER.trace("mx.mc.magedbean.NotaDispenColectMB.cierraListaDistribucion()");
        this.notaDispenColectSelected = new NotaDispenColect_Extended();
        this.notaDispenColectDetalleListaSelected = new ArrayList<>();
    }

    /**
     * Reimprimir nota de dispensacion colectiva
     *
     * @param ndce
     */
    public void imprimeNotaDispenColectiva(NotaDispenColect_Extended ndce) {
        LOGGER.trace("mx.mc.magedbean.NotaDispenColectMB.imprimeNotaDispenColectiva()");
        notaDispenColectSelected = ndce;
        imprimeNotaDispenColectiva();
    }

    /**
     * Reimprimir nota de dispensacion colectiva
     */
    public void imprimeNotaDispenColectiva() {
        LOGGER.trace("mx.mc.magedbean.NotaDispenColectMB.imprimeNotaDispenColectiva()");
        boolean status = false;

        if (this.usuarioSelected == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Usuario inválido", null);

        } else if (this.usuarioSelected.getIdUsuario() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Usuario inválido", null);

        } else if (!this.permiso.isPuedeVer()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No tiene permisos para esta acción", null);

        } else if (notaDispenColectSelected == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Nota de Dispensación Colectiva Inválida", null);

        } else if (notaDispenColectSelected.getIdNotaDispenColect() == null
                || notaDispenColectSelected.getIdNotaDispenColect().trim().isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Nota de Dispensación Colectiva Inválida", null);

        } else if (notaDispenColectSelected == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Nota inválida. ", null);

        } else if (!this.permiso.isPuedeEditar()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No tiene permisos para esta acción. ", null);

        } else {
            try {
                if (notaDispenColectSelected.getIdUsuarioEntrega() != null) {
                    usuarioEntrega = usuarioService.obtenerUsuarioByIdUsuario(notaDispenColectSelected.getIdUsuarioEntrega());
                }
                if (notaDispenColectSelected.getIdUsuarioDispensa() != null) {
                    usuarioDispensa = usuarioService.obtenerUsuarioByIdUsuario(notaDispenColectSelected.getIdUsuarioDispensa());
                }
                if (notaDispenColectSelected.getIdUsuarioDistribuye() != null) {
                    usuarioDistribuye = usuarioService.obtenerUsuarioByIdUsuario(notaDispenColectSelected.getIdUsuarioDistribuye());
                }
                /* Se cambia por texto libre
                if (notaDispenColectSelected.getIdUsuarioRecibe() != null) {
                    usuarioRecibe = usuarioService.obtenerUsuarioByIdUsuario(notaDispenColectSelected.getIdUsuarioRecibe());
                }*/
                if (notaDispenColectSelected.getUserRecibe() != null) {
                    userRecibe = notaDispenColectSelected.getUserRecibe();
                }

                NotaDispenColectDetalle_Extended ndce = new NotaDispenColectDetalle_Extended();
                ndce.setIdNotaDispenColect(notaDispenColectSelected.getIdNotaDispenColect());
                ndce.setNombreEstructura(notaDispenColectSelected.getNombreEstructura());
                notaDispenColectDetalleListaSelected = notaDispenColectDetalleService.obtenerListaMezclas(ndce);
                if (!notaDispenColectDetalleListaSelected.isEmpty()) {
                    notaDispenColectSelected.setNumeroMezclas(notaDispenColectDetalleListaSelected.size());
                    for (NotaDispenColectDetalle_Extended nota : notaDispenColectDetalleListaSelected) {
                        String idSolucion = nota.getIdSolucion();
                        String idSurtimiento = null;
                        Solucion sol = solucionService.obtenerSolucion(idSolucion, idSurtimiento);
                        if (sol != null) {
                            Surtimiento sur = surtimientoService.obtenerByIdSurtimiento(nota.getIdSurtimiento());
                            if (sur != null) {
                                Prescripcion pre = prescripcionService.obtener(new Prescripcion(sur.getIdPrescripcion()));
                                if (pre != null) {
                                    nota.setNombreEstructura(estructuraService.obtenerEstructura(pre.getIdEstructura()).getNombre());
                                    nota.setTipoPrescripcion(pre.getTipoPrescripcion());
                                    Usuario medico = usuarioService.obtenerUsuarioByIdUsuario(pre.getIdMedico());
                                    StringBuilder nombreMedico = new StringBuilder();
                                    if (medico != null) {
                                        nombreMedico.append(medico.getNombre());
                                        nombreMedico.append(StringUtils.SPACE);
                                        nombreMedico.append(medico.getApellidoPaterno());
                                        nombreMedico.append(StringUtils.SPACE);
                                        nombreMedico.append(medico.getApellidoMaterno());
                                        nota.setNombreMedico(nombreMedico.toString());
                                    }
                                    PacienteUbicacion put = new PacienteUbicacion();
                                    put.setIdPacienteUbicacion(pre.getIdPacienteUbicacion());
                                    PacienteUbicacion pu = pacienteUbicacionService.obtener(put);
                                    if (pu != null) {
                                        Cama c = camaService.obtenerCama(pu.getIdCama());
                                        if (c != null) {
                                            nota.setCama(c.getNombreCama());
                                        }
                                    }
                                    PacienteServicio pst = new PacienteServicio();
                                    pst.setIdPacienteServicio(pre.getIdPacienteServicio());
                                    PacienteServicio ps = pacienteServicioService.obtener(pst);
                                    if (ps != null) {
                                        Visita o = new Visita();
                                        o.setIdVisita(ps.getIdVisita());
                                        Visita vis = visitaService.obtener(o);
                                        if (vis != null) {
                                            Paciente pact = new Paciente();
                                            pact.setIdPaciente(vis.getIdPaciente());
                                            Paciente pac = pacienteService.obtener(pact);
                                            StringBuilder nombrePaciente = new StringBuilder();
                                            if (pac != null) {
                                                nombrePaciente.append(pac.getNombreCompleto());
                                                nombrePaciente.append(StringUtils.SPACE);
                                                nombrePaciente.append(pac.getApellidoPaterno());
                                                nombrePaciente.append(StringUtils.SPACE);
                                                nombrePaciente.append(pac.getApellidoMaterno());
                                                nota.setNombrePaciente(nombrePaciente.toString());
                                            }
                                        }
                                    }
                                    nota.setTipoSolucion(obtenerTipoSolucion(pre.getIdTipoSolucion()).getDescripcion());
                                }
                                nota.setFolio(sur.getFolio());
                            }
                            nota.setNombreContenedor(" ");
                            nota.setIdContenedor(sol.getIdEnvaseContenedor());
                            if (sol.getIdEnvaseContenedor() != null && sol.getIdEnvaseContenedor() > 0) {
                                EnvaseContenedor enCon = obtenerContenedor(sol.getIdEnvaseContenedor());
                                if (enCon != null) {
                                    nota.setNombreContenedor(enCon.getDescripcion());
                                }
                            }
                            nota.setFechaParaEntregar(sol.getFechaParaEntregar());
                        }
                    }
                }
                imprimeListaDistribucion();
                status = true;
            } catch (Exception e) {
                LOGGER.error("Error al obtener nota de dispensacion colectiva :: {} ", e.getMessage());
            }
        }
        PrimeFaces.current().ajax().addCallbackParam("estatus", status);
    }

    public void imprimeListaDistribucion() {
        LOGGER.trace("mx.mc.magedbean.NotaDispenColectMB.imprimeListaDistribucion()");

        String msg = validarDatosDeNotaDispColec();
        if (msg != null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, msg, null);

        } else if (!this.permiso.isPuedeVer()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No tiene permisos para esta acción. ", null);

        } else {
            boolean status = false;
            byte[] buffer = null;
            try {
                EntidadHospitalaria enti = new EntidadHospitalaria();
                enti.setNombre("");
                enti.setDomicilio("");

                if (this.usuarioSelected.getIdEstructura() != null) {
                    Estructura estruct = estructuraService.obtenerEstructura(this.usuarioSelected.getIdEstructura());

                    if (estruct != null) {
                        this.notaDispenColectSelected.setNombreAreaDistribuye(estruct.getNombre());
                        if (estruct.getIdEntidadHospitalaria() != null) {
                            enti = entidadHospitalariaService.obtenerEntidadById(estruct.getIdEntidadHospitalaria());
                            if (enti == null) {
                                enti = new EntidadHospitalaria();
                                enti.setNombre("");
                                enti.setDomicilio("");
                            }
                        }
                    }
                }

                if (this.notaDispenColectSelected.getIdTurno() != null) {
                    Turno o = this.turnoService.obtener(new Turno(this.notaDispenColectSelected.getIdTurno()));
                    if (o != null) {
                        this.notaDispenColectSelected.setNombreTurno(o.getNombre());
                    }
                }

                if (this.notaDispenColectSelected.getIdUsuarioEntrega() != null) {
                    this.notaDispenColectSelected.setNombreUsuarioEntrega(obtenerUsuario(this.notaDispenColectSelected.getIdUsuarioEntrega()));
                }

                if (this.notaDispenColectSelected.getIdUsuarioDispensa() != null) {
                    this.notaDispenColectSelected.setNombreUsuarioDispensa(obtenerUsuario(this.notaDispenColectSelected.getIdUsuarioDispensa()));
                }

                if (this.notaDispenColectSelected.getIdUsuarioDistribuye() != null) {
                    this.notaDispenColectSelected.setNombreUsuarioDistribuye(obtenerUsuario(this.notaDispenColectSelected.getIdUsuarioDistribuye()));
                }
                /* se cambia por texto libre
                if (this.notaDispenColectSelected.getIdUsuarioRecibe() != null) {
                    this.notaDispenColectSelected.setNombreUsuarioRecibe(obtenerUsuario(this.notaDispenColectSelected.getIdUsuarioRecibe()));
                }*/
                if (this.notaDispenColectSelected.getUserRecibe() != null) {
                    this.notaDispenColectSelected.setNombreUsuarioRecibe(this.notaDispenColectSelected.getUserRecibe());
                }
                
                if(this.notaDispenColectSelected.getNombreEstructura() == null) {
                    Estructura servicio = estructuraService.obtenerEstructura(notaDispenColectSelected.getIdEstructura());
                    if(servicio != null) {
                        this.notaDispenColectSelected.setNombreEstructura(servicio.getNombre());
                    }
                }
                buffer = reportesService.imprimeListaDistribucion(enti, this.notaDispenColectSelected);

                if (buffer != null) {
                    SesionMB sesionReporte = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "sesionMB");
                    sesionReporte.setReportStream(buffer);
                    sesionReporte.setReportName(String.format("NotaDispColec_%s.pdf", this.notaDispenColectSelected.getFolio()));
                    status = Constantes.ACTIVO;
                }
            } catch (Exception ex) {
                LOGGER.error("Error al obtener datos de la impresión de la nota :: {}", ex.getMessage());
            }
            PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);

        }
    }

    private String obtenerUsuario(String idUsuario) {
        String res = "";
        try {
            Usuario u = usuarioService.obtenerUsuarioByIdUsuario(idUsuario);
            if (u != null) {
                StringBuilder sb = new StringBuilder();
                if (u.getNombre() != null) {
                    sb.append(u.getNombre());
                    sb.append(StringUtils.SPACE);
                }
                if (u.getApellidoPaterno() != null) {
                    sb.append(u.getApellidoPaterno());
                    sb.append(StringUtils.SPACE);
                }
                if (u.getApellidoMaterno() != null) {
                    sb.append(u.getApellidoMaterno());
                    sb.append(StringUtils.SPACE);
                }
                res = sb.toString();
            }
        } catch (Exception e) {
            LOGGER.error("Error al obtener datos del usuario :: {}", e.getMessage());
        }
        return res;
    }

    private transient List<Turno> turnoLista;

    public List<Turno> getTurnoLista() {
        return turnoLista;
    }

    public void setTurnoLista(List<Turno> turnoLista) {
        this.turnoLista = turnoLista;
    }

    @Autowired
    private transient TurnoService turnoService;

    private void obtenerTurnos() {
        LOGGER.trace("mx.mc.magedbean.NotaDispenColectMB.obtenerTurnos()");
        this.turnoLista = new ArrayList<>();
        try {
            this.turnoLista.addAll(turnoService.obtenerLista(new Turno()));
        } catch (Exception ex) {
            LOGGER.error("Errir al obtener turnos {} ", ex.getMessage());
        }
    }

    /**
     * Obtiene la lista de horarios de entrega
     *
     * @param idTipoSolucionSelected
     */
    public void obtenerListaHorarios(String idTipoSolucionSelected) {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.obtenerListaHorarios()");
        this.horarioEntregaLista = new ArrayList<>();
        try {
            HorarioEntrega he = new HorarioEntrega();
            he.setIdTipoSolucion(idTipoSolucion);
            he.setActiva(1);
            this.horarioEntregaLista.addAll(this.horarioEntregaService.obtenerLista(he));
        } catch (Exception e) {
            LOGGER.error("Error al obtener lista de horarios de entrega :: {} ", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al obtener lista de horarios de entrega. ", null);
        }
    }

    public void obtenerTiposSolucion() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.obtenerTiposSolucion()");
        List<String> listaClaves = new ArrayList<>();
        listaClaves.add("ANT");
        listaClaves.add("ONC");
        listaClaves.add("NPT");
        tipoSolucionList = new ArrayList<>();
        try {
            tipoSolucionList.addAll(this.tiposolucionService.obtenerByListaClaves(listaClaves));
        } catch (Exception ex) {
            LOGGER.error("Error al obtener la lista de tipos de soluciones :: {} ", ex.getMessage());
        }
    }

    public void buscaNotasEnTransito() {
        NotaDispenColect_Extended ndce = new NotaDispenColect_Extended();
        ndce.setIdEstructura(idEstructuraNotas);
        ndce.setFechaEntrega(fechaParaEntregarNotas);
        ndce.setFechaEntregaFin(fechaParaEntregarFinNotas);
        ndce.setIdTipoSolucion(idTipoSolucionNotas);
        ndce.setCadena(cadenaBusquedaNotas);
        ndce.setIdEstatusDispenColect(estatusNotaDispensacion);
        obtenerNotasDispenColect(ndce);
    }

    public void buscaNotasEntregadas() {
        NotaDispenColect_Extended ndce = new NotaDispenColect_Extended();
        ndce.setIdEstructura(idEstructuraNotasE);
        ndce.setFechaEntrega(fechaParaEntregarNotasE);
        ndce.setFechaEntregaFin(fechaParaEntregarFinNotasE);
        ndce.setIdTipoSolucion(idTipoSolucionNotasE);
        ndce.setCadena(cadenaBusquedaNotasE);
        ndce.setIdEstatusDispenColect(estatusNotaDispensacion);
        obtenerNotasDispenColect(ndce);
    }

    public void buscaNotasNoEntregadas() {
        NotaDispenColect_Extended ndce = new NotaDispenColect_Extended();
        ndce.setIdEstructura(idEstructuraNotasN);
        ndce.setFechaEntrega(fechaParaEntregarNotasN);
        ndce.setFechaEntregaFin(fechaParaEntregarFinNotasN);
        ndce.setIdTipoSolucion(idTipoSolucionNotasN);
        ndce.setCadena(cadenaBusquedaNotasN);
        ndce.setIdEstatusDispenColect(estatusNotaDispensacion);
        obtenerNotasDispenColect(ndce);
    }
    
    private void obtenerNotasDispenColect(NotaDispenColect_Extended ndce) {
        LOGGER.trace("mx.mc.magedbean.NotaDispenColectMB.obtenerNotasDispenColect()");
        try {
            this.notaDispenColectLista = new ArrayList<>();
            this.notaDispenColectLista.addAll(notaDispenColectService.obtenerListaNotas(ndce));
        } catch (Exception e) {
            LOGGER.error("Error al buscar las noras de dispensación colectiva :: {} ", e.getMessage());
        }
    }
    
    public void obtenerNotasDispenColect() {
        LOGGER.trace("mx.mc.magedbean.NotaDispenColectMB.obtenerNotasDispenColect()");
        try {
            this.notaDispenColectLista = new ArrayList<>();
            NotaDispenColect_Extended ndce = new NotaDispenColect_Extended();
            ndce.setIdEstructura(idEstructuraNotas);
            ndce.setFechaEntrega(fechaParaEntregarNotas);
            ndce.setFechaEntregaFin(fechaParaEntregarFinNotas);
            ndce.setIdTipoSolucion(idTipoSolucionNotas);
            ndce.setCadena(cadenaBusquedaNotas);
            ndce.setIdEstatusDispenColect(estatusNotaDispensacion);
            this.notaDispenColectLista.addAll(notaDispenColectService.obtenerListaNotas(ndce));
        } catch (Exception e) {
            LOGGER.error("Error al buscar las noras de dispensación colectiva :: {} ", e.getMessage());
        }
    }

    /**
     * Permite visualizar una nota de dispensación almacenada
     */
    public void verNota() {
        LOGGER.trace("mx.mc.magedbean.NotaDispenColectMB.verNota()");
        boolean status = false;
//        String msg = validarDatosDeNotaDispColec();
//        
//        if (msg != null) {
//            Mensaje.showMessage(Constantes.MENSAJE_ERROR, msg, null);
//
//        } else 
        if (notaDispenColectSelected == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Nota inválida. ", null);

        } else if (!this.permiso.isPuedeEditar()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No tiene permisos para esta acción. ", null);

        } else {
            try {
//                if ()
//                notaDispenColectSelected.setIdTurno(1);
                if (notaDispenColectSelected.getIdUsuarioEntrega() != null) {
                    usuarioEntrega = usuarioService.obtenerUsuarioByIdUsuario(notaDispenColectSelected.getIdUsuarioEntrega());
                }
                if (notaDispenColectSelected.getIdUsuarioDispensa() != null) {
                    usuarioDispensa = usuarioService.obtenerUsuarioByIdUsuario(notaDispenColectSelected.getIdUsuarioDispensa());
                }
                if (notaDispenColectSelected.getIdUsuarioDistribuye() != null) {
                    usuarioDistribuye = usuarioService.obtenerUsuarioByIdUsuario(notaDispenColectSelected.getIdUsuarioDistribuye());
                }
                /* Se cambia por texto libre
                if (notaDispenColectSelected.getIdUsuarioRecibe() != null) {
                    usuarioRecibe = usuarioService.obtenerUsuarioByIdUsuario(notaDispenColectSelected.getIdUsuarioRecibe());
                }*/
                if (notaDispenColectSelected.getUserRecibe() != null) {
                    userRecibe = notaDispenColectSelected.getUserRecibe();
                }

                NotaDispenColectDetalle_Extended ndce = new NotaDispenColectDetalle_Extended();
                ndce.setIdNotaDispenColect(notaDispenColectSelected.getIdNotaDispenColect());
                notaDispenColectDetalleListaSelected = notaDispenColectDetalleService.obtenerListaMezclas(ndce);
                if (!notaDispenColectDetalleListaSelected.isEmpty()) {
                    notaDispenColectSelected.setNumeroMezclas(notaDispenColectDetalleListaSelected.size());
                    for (NotaDispenColectDetalle_Extended nota : notaDispenColectDetalleListaSelected) {
                        String idSolucion = nota.getIdSolucion();
                        String idSurtimiento = null;
                        Solucion sol = solucionService.obtenerSolucion(idSolucion, idSurtimiento);
                        if (sol != null) {
                            Surtimiento sur = surtimientoService.obtenerByIdSurtimiento(nota.getIdSurtimiento());
                            if (sur != null) {
                                Prescripcion pre = prescripcionService.obtener(new Prescripcion(sur.getIdPrescripcion()));
                                if (pre != null) {
                                    nota.setNombreEstructura(estructuraService.obtenerEstructura(pre.getIdEstructura()).getNombre());
                                    nota.setTipoPrescripcion(pre.getTipoPrescripcion());
                                    Usuario medico = usuarioService.obtenerUsuarioByIdUsuario(pre.getIdMedico());
                                    StringBuilder nombreMedico = new StringBuilder();
                                    if (medico != null) {
                                        nombreMedico.append(medico.getNombre());
                                        nombreMedico.append(StringUtils.SPACE);
                                        nombreMedico.append(medico.getApellidoPaterno());
                                        nombreMedico.append(StringUtils.SPACE);
                                        nombreMedico.append(medico.getApellidoMaterno());
                                        nota.setNombreMedico(nombreMedico.toString());
                                    }
                                    PacienteUbicacion put = new PacienteUbicacion();
                                    put.setIdPacienteUbicacion(pre.getIdPacienteUbicacion());
                                    PacienteUbicacion pu = pacienteUbicacionService.obtener(put);
                                    if (pu != null) {
                                        Cama c = camaService.obtenerCama(pu.getIdCama());
                                        if (c != null) {
                                            nota.setCama(c.getNombreCama());
                                        }
                                    }
                                    PacienteServicio pst = new PacienteServicio();
                                    pst.setIdPacienteServicio(pre.getIdPacienteServicio());
                                    PacienteServicio ps = pacienteServicioService.obtener(pst);
                                    if (ps != null) {
                                        Visita o = new Visita();
                                        o.setIdVisita(ps.getIdVisita());
                                        Visita vis = visitaService.obtener(o);
                                        if (vis != null) {
                                            Paciente pact = new Paciente();
                                            pact.setIdPaciente(vis.getIdPaciente());
                                            Paciente pac = pacienteService.obtener(pact);
                                            StringBuilder nombrePaciente = new StringBuilder();
                                            if (pac != null) {
                                                nombrePaciente.append(pac.getNombreCompleto());
                                                nombrePaciente.append(StringUtils.SPACE);
                                                nombrePaciente.append(pac.getApellidoPaterno());
                                                nombrePaciente.append(StringUtils.SPACE);
                                                nombrePaciente.append(pac.getApellidoMaterno());
                                                nota.setNombrePaciente(nombrePaciente.toString());
                                            }
                                        }
                                    }
                                    nota.setTipoSolucion(obtenerTipoSolucion(pre.getIdTipoSolucion()).getDescripcion());
                                }
                                nota.setFolio(sur.getFolio());
                            }
                            nota.setIdContenedor(sol.getIdEnvaseContenedor());
                            nota.setNombreContenedor(obtenerContenedor(sol.getIdEnvaseContenedor()).getDescripcion());
                            nota.setFechaParaEntregar(sol.getFechaParaEntregar());
                        }
                    }
                    status = true;
                }
            } catch (Exception e) {
                LOGGER.error("Error al obtener mezclas de notas de dispensacio colectiva. :: {} ", e.getMessage());
            }
        }
        PrimeFaces.current().ajax().addCallbackParam("estatus", status);
    }

    public void eliminarMezclaDeLista(NotaDispenColectDetalle_Extended nota) {
        LOGGER.trace("mx.mc.magedbean.NotaDispenColectMB.eliminarMezclaDeLista()");
        try {
            if (this.notaDispenColectDetalleListaSelected != null) {
                if (!this.notaDispenColectDetalleListaSelected.isEmpty()) {
                    List<NotaDispenColectDetalle_Extended> notas = new ArrayList<>();
                    notas.addAll(notaDispenColectDetalleListaSelected);
                    for (int i = 0; i < notas.size(); i++) {
//                    for (NotaDispenColectDetalle_Extended item : listaNotas){
                        if (notas.get(i).getIdNotaDispenColect().equals(nota.getIdNotaDispenColect())) {
                            notas.remove(i);
                            break;
                        }
                    }
                    notaDispenColectDetalleListaSelected = new ArrayList<>();
                    notaDispenColectDetalleListaSelected.addAll(notas);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al eliminar mezcla de nota de dispensacio colectiva :: {} ", e.getMessage());
        }
    }

    public String getMezclaAcondicionada() {
        return mezclaAcondicionada;
    }

    public void setMezclaAcondicionada(String mezclaAcondicionada) {
        this.mezclaAcondicionada = mezclaAcondicionada;
    }

    public String getMezclaNoAcondicionada() {
        return mezclaNoAcondicionada;
    }

    public void setMezclaNoAcondicionada(String mezclaNoAcondicionada) {
        this.mezclaNoAcondicionada = mezclaNoAcondicionada;
    }

    public String getMezclaEnTransito() {
        return mezclaEnTransito;
    }

    public void setMezclaEnTransito(String mezclaEnTransito) {
        this.mezclaEnTransito = mezclaEnTransito;
    }

    public String getMezclaEntregada() {
        return mezclaEntregada;
    }

    public void setMezclaEntregada(String mezclaEntregada) {
        this.mezclaEntregada = mezclaEntregada;
    }

    public String getMezclaNoEntregada() {
        return mezclaNoEntregada;
    }

    public void setMezclaNoEntregada(String mezclaNoEntregada) {
        this.mezclaNoEntregada = mezclaNoEntregada;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public String getIdTipoSolucion() {
        return idTipoSolucion;
    }

    public void setIdTipoSolucion(String idTipoSolucion) {
        this.idTipoSolucion = idTipoSolucion;
    }

    public Date getFechaParaEntregar() {
        return fechaParaEntregar;
    }

    public void setFechaParaEntregar(Date fechaParaEntregar) {
        this.fechaParaEntregar = fechaParaEntregar;
    }

    public List<HorarioEntrega> getHorarioEntregaLista() {
        return horarioEntregaLista;
    }

    public void setHorarioEntregaLista(List<HorarioEntrega> horarioEntregaLista) {
        this.horarioEntregaLista = horarioEntregaLista;
    }

    public List<TipoSolucion> getTipoSolucionList() {
        return tipoSolucionList;
    }

    public void setTipoSolucionList(List<TipoSolucion> tipoSolucionList) {
        this.tipoSolucionList = tipoSolucionList;
    }

    public NotaDispenColectService getNotaDispenColectService() {
        return notaDispenColectService;
    }

    public void setNotaDispenColectService(NotaDispenColectService notaDispenColectService) {
        this.notaDispenColectService = notaDispenColectService;
    }

    public List<EnvaseContenedor> getContenedoresLista() {
        return contenedoresLista;
    }

    public void setContenedoresLista(List<EnvaseContenedor> contenedoresLista) {
        this.contenedoresLista = contenedoresLista;
    }

    public Usuario getUsuarioEntrega() {
        return usuarioEntrega;
    }

    public void setUsuarioEntrega(Usuario usuarioEntrega) {
        this.usuarioEntrega = usuarioEntrega;
    }

    public Usuario getUsuarioDispensa() {
        return usuarioDispensa;
    }

    public void setUsuarioDispensa(Usuario usuarioDispensa) {
        this.usuarioDispensa = usuarioDispensa;
    }

    public Usuario getUsuarioDistribuye() {
        return usuarioDistribuye;
    }

    public void setUsuarioDistribuye(Usuario usuarioDistribuye) {
        this.usuarioDistribuye = usuarioDistribuye;
    }

    public NotaDispenColectDetalle_Extended getNotaDispenColectDetalleSelected() {
        return notaDispenColectDetalleSelected;
    }

    public void setNotaDispenColectDetalleSelected(NotaDispenColectDetalle_Extended notaDispenColectDetalleSelected) {
        this.notaDispenColectDetalleSelected = notaDispenColectDetalleSelected;
    }

    public NotaDispenColect_Extended getNotaDispenColectSelected() {
        return notaDispenColectSelected;
    }

    public void setNotaDispenColectSelected(NotaDispenColect_Extended notaDispenColectSelected) {
        this.notaDispenColectSelected = notaDispenColectSelected;
    }

    public List<NotaDispenColectDetalle_Extended> getNotaDispenColectDetalleLista() {
        return notaDispenColectDetalleListaSelected;
    }

    public void setNotaDispenColectDetalleLista(List<NotaDispenColectDetalle_Extended> notaDispenColectDetalleListaSelected) {
        this.notaDispenColectDetalleListaSelected = notaDispenColectDetalleListaSelected;
    }

    public Usuario getUsuarioRecibe() {
        return usuarioRecibe;
    }

    public void setUsuarioRecibe(Usuario usuarioRecibe) {
        this.usuarioRecibe = usuarioRecibe;
    }

    public List<NotaDispenColectDetalle_Extended> getNotaDispenColectDetalleListaSelected() {
        return notaDispenColectDetalleListaSelected;
    }

    public void setNotaDispenColectDetalleListaSelected(List<NotaDispenColectDetalle_Extended> notaDispenColectDetalleListaSelected) {
        this.notaDispenColectDetalleListaSelected = notaDispenColectDetalleListaSelected;
    }

    public List<NotaDispenColect_Extended> getNotaDispenColectLista() {
        return notaDispenColectLista;
    }

    public void setNotaDispenColectLista(List<NotaDispenColect_Extended> notaDispenColectLista) {
        this.notaDispenColectLista = notaDispenColectLista;
    }

    public Date getFechaParaEntregarFin() {
        return fechaParaEntregarFin;
    }

    public void setFechaParaEntregarFin(Date fechaParaEntregarFin) {
        this.fechaParaEntregarFin = fechaParaEntregarFin;
    }

    public String getUserRecibe() {
        return userRecibe;
    }

    public void setUserRecibe(String userRecive) {
        this.userRecibe = userRecive;
    }

    public void estableceFechaEntrega(AjaxBehaviorEvent event) {
        try {
            if (notaDispenColectSelected != null && event != null) {
                notaDispenColectSelected.setFechaEntrega((Date) ((UIOutput) event.getSource()).getValue());
            }
        } catch (Exception e) {
            LOGGER.error("Error al registrar modificar fecha de entrega de nota de dispensacion colectiva :: {} ", e.getMessage());
        }
    }

    public Date getFechaParaEntregarNotas() {
        return fechaParaEntregarNotas;
    }

    public void setFechaParaEntregarNotas(Date fechaParaEntregarNotas) {
        this.fechaParaEntregarNotas = fechaParaEntregarNotas;
    }

    public Date getFechaParaEntregarFinNotas() {
        return fechaParaEntregarFinNotas;
    }

    public void setFechaParaEntregarFinNotas(Date fechaParaEntregarFinNotas) {
        this.fechaParaEntregarFinNotas = fechaParaEntregarFinNotas;
    }

    public Date getFechaParaEntregarNotasE() {
        return fechaParaEntregarNotasE;
    }

    public void setFechaParaEntregarNotasE(Date fechaParaEntregarNotasE) {
        this.fechaParaEntregarNotasE = fechaParaEntregarNotasE;
    }

    public Date getFechaParaEntregarFinNotasE() {
        return fechaParaEntregarFinNotasE;
    }

    public void setFechaParaEntregarFinNotasE(Date fechaParaEntregarFinNotasE) {
        this.fechaParaEntregarFinNotasE = fechaParaEntregarFinNotasE;
    }

    public Date getFechaParaEntregarNotasN() {
        return fechaParaEntregarNotasN;
    }

    public void setFechaParaEntregarNotasN(Date fechaParaEntregarNotasN) {
        this.fechaParaEntregarNotasN = fechaParaEntregarNotasN;
    }

    public Date getFechaParaEntregarFinNotasN() {
        return fechaParaEntregarFinNotasN;
    }

    public void setFechaParaEntregarFinNotasN(Date fechaParaEntregarFinNotasN) {
        this.fechaParaEntregarFinNotasN = fechaParaEntregarFinNotasN;
    }
    
    public String getIdEstructuraNotas() {
        return idEstructuraNotas;
    }

    public void setIdEstructuraNotas(String idEstructuraNotas) {
        this.idEstructuraNotas = idEstructuraNotas;
    }

    public String getIdTipoSolucionNotas() {
        return idTipoSolucionNotas;
    }

    public void setIdTipoSolucionNotas(String idTipoSolucionNotas) {
        this.idTipoSolucionNotas = idTipoSolucionNotas;
    }

    public String getIdEstructuraNotasE() {
        return idEstructuraNotasE;
    }

    public void setIdEstructuraNotasE(String idEstructuraNotasE) {
        this.idEstructuraNotasE = idEstructuraNotasE;
    }

    public String getIdTipoSolucionNotasE() {
        return idTipoSolucionNotasE;
    }

    public void setIdTipoSolucionNotasE(String idTipoSolucionNotasE) {
        this.idTipoSolucionNotasE = idTipoSolucionNotasE;
    }

    public String getIdEstructuraNotasN() {
        return idEstructuraNotasN;
    }

    public void setIdEstructuraNotasN(String idEstructuraNotasN) {
        this.idEstructuraNotasN = idEstructuraNotasN;
    }

    public String getIdTipoSolucionNotasN() {
        return idTipoSolucionNotasN;
    }

    public void setIdTipoSolucionNotasN(String idTipoSolucionNotasN) {
        this.idTipoSolucionNotasN = idTipoSolucionNotasN;
    }
    public String getCadenaBusquedaNotas() {
        return cadenaBusquedaNotas;
    }

    public void setCadenaBusquedaNotas(String cadenaBusquedaNotas) {
        this.cadenaBusquedaNotas = cadenaBusquedaNotas;
    }

    public String getCadenaBusquedaNotasE() {
        return cadenaBusquedaNotasE;
    }

    public void setCadenaBusquedaNotasE(String cadenaBusquedaNotasE) {
        this.cadenaBusquedaNotasE = cadenaBusquedaNotasE;
    }

    public String getCadenaBusquedaNotasN() {
        return cadenaBusquedaNotasN;
    }

    public void setCadenaBusquedaNotasN(String cadenaBusquedaNotasN) {
        this.cadenaBusquedaNotasN = cadenaBusquedaNotasN;
    }
    
}
