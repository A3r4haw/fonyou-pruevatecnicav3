package mx.mc.magedbean;

import java.io.Serializable;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import mx.mc.commons.SolucionUtils;
import mx.mc.enums.CatalogoGeneral_Enum;
import mx.mc.enums.EstatusPrescripcion_Enum;
import mx.mc.enums.EstatusSolucion_Enum;
import mx.mc.enums.EstatusSurtimiento_Enum;
import mx.mc.enums.MotivoCancelacionRechazoSolucion_Enum;
import mx.mc.enums.TipoAreaEstructura_Enum;
import mx.mc.enums.TipoImpresora_Enum;
import mx.mc.enums.TipoPerfilUsuario_Enum;
import mx.mc.enums.TipoTemplateEtiqueta_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.lazy.DispensacionSolucionLazy;
import mx.mc.model.CodigoInsumo;
import mx.mc.model.Diagnostico;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.EnvaseContenedor;
import mx.mc.model.Estabilidad_Extended;
import mx.mc.model.Estructura;
import mx.mc.model.EtiquetaInsumo;
import mx.mc.model.HorarioEntrega;
import mx.mc.model.ImpresionEtiqueta;
import mx.mc.model.Impresora;
import mx.mc.model.MotivosRechazo;
import mx.mc.model.Paciente;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.Prescripcion;
import mx.mc.model.PrescripcionInsumo;
import mx.mc.model.Protocolo;
import mx.mc.model.RepSurtimientoPresc;
import mx.mc.model.Solucion;
import mx.mc.model.Surtimiento;
import mx.mc.model.SurtimientoEnviado_Extend;
import mx.mc.model.SurtimientoInsumo;
import mx.mc.model.SurtimientoInsumo_Extend;
import mx.mc.model.Surtimiento_Extend;
import mx.mc.model.TemplateEtiqueta;
import mx.mc.model.TipoSolucion;
import mx.mc.model.Usuario;
import mx.mc.model.ViaAdministracion;
import mx.mc.service.CamaService;
import mx.mc.service.CatalogoGeneralService;
import mx.mc.service.ConfigService;
import mx.mc.service.DiagnosticoService;
import mx.mc.service.EntidadHospitalariaService;
import mx.mc.service.EnvaseContenedorService;
import mx.mc.service.EquipoService;
import mx.mc.service.EstabilidadService;
import mx.mc.service.EstructuraService;
import mx.mc.service.FrecuenciaService;
import mx.mc.service.HipersensibilidadService;
import mx.mc.service.HorarioEntregaService;
import mx.mc.service.ImpresionEtiquetaService;
import mx.mc.service.ImpresoraService;
import mx.mc.service.InventarioService;
import mx.mc.service.MedicamentoService;
import mx.mc.service.MotivosRechazoService;
import mx.mc.service.PacienteService;
import mx.mc.service.PacienteServicioService;
import mx.mc.service.PacienteUbicacionService;
import mx.mc.service.PlantillaCorreoService;
import mx.mc.service.PrescripcionInsumoService;
import mx.mc.service.PrescripcionService;
import mx.mc.service.ProtocoloService;
import mx.mc.service.ReaccionService;
import mx.mc.service.ReportesService;
import mx.mc.service.SolucionService;
import mx.mc.service.SurtimientoEnviadoService;
import mx.mc.service.SurtimientoInsumoService;
import mx.mc.service.SurtimientoService;
import mx.mc.service.TemplateEtiquetaService;
import mx.mc.service.TipoJustificacionService;
import mx.mc.service.TipoSolucionService;
import mx.mc.service.TipoUsuarioService;
import mx.mc.service.TurnoService;
import mx.mc.service.UsuarioService;
import mx.mc.service.ViaAdministracionService;
import mx.mc.service.VisitaService;
import mx.mc.util.Comunes;
import mx.mc.util.EnviaCorreoUtil;
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
 * @author gcruz
 */
@Controller
@Scope(value = "view")
public class InspeccionarMezclaSolucionMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(InspeccionarMezclaSolucionMB.class);
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
    private boolean esSolucion;
    private String paramEstatus;
    private DispensacionSolucionLazy dispensacionSolucionLazy;
//    private String comentariosCorrecta;
    private String comentariosIncorrecta;
    private String preparada;
    private String mezcla_correcta;
    private String mezcla_incorrecta;
    private String errRegistroIncorrecto;
    private String surIncorrecto;
    private String diagnosticos;

    @Autowired
    private transient EstructuraService estructuraService;
    private transient List<Estructura> listServiciosQueSurte;

    @Autowired
    private transient SurtimientoService surtimientoService;
    private Surtimiento_Extend surtimientoExtendedSelected;
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
    private transient PrescripcionService prescripcionService;

    @Autowired
    private transient PrescripcionInsumoService prescripcionInsumoService;

    @Autowired
    private transient ReportesService reportesService;

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

    @Autowired
    private transient CamaService camService;
    @Autowired
    private transient FrecuenciaService frecuenciaService;
    @Autowired
    private transient TurnoService turnoService;
    @Autowired
    private transient PacienteServicioService pacienteServicioService;

    @Autowired
    private transient PacienteUbicacionService pacienteUbicacionService;

    @Autowired
    private transient CatalogoGeneralService catalogoGeneralService;
    @Autowired
    private transient TipoUsuarioService tipoUsuarioService;
    @Autowired
    private transient UsuarioService usuarioService;
    @Autowired
    private transient EnviaCorreoUtil enviaCorreoUtil;
    @Autowired
    private transient PlantillaCorreoService plantillaCorreoService;
    @Autowired
    private transient ConfigService configService;
    @Autowired
    private transient TipoJustificacionService tipoJustificacionService;
    @Autowired
    private transient MedicamentoService medicamentoService;
    @Autowired
    private transient PacienteService pacienteService;
    @Autowired
    private transient HorarioEntregaService horarioEntregaService;
    @Autowired
    private transient HipersensibilidadService hipersensibilidadService;
    @Autowired
    private transient ReaccionService reaccionService;
    @Autowired
    private transient EquipoService equipoService;
    @Autowired
    private transient ImpresoraService impresoraService;
    @Autowired
    private transient EstabilidadService estabilidadService;
    @Autowired
    private transient InventarioService inventarioService;
    @Autowired
    private transient SurtimientoEnviadoService enviadoService;
    @Autowired
    private transient ImpresionEtiquetaService impresionEtiquetaService;

    private Prescripcion prescripcionSelected;
    private Date fechaActual;

    private transient List<Integer> estatusSolucionLista;

    private SolucionUtils solUtils;

    private Date fechaParaEntregar;
    private Integer idHorarioParaEntregar;
    private transient List<HorarioEntrega> horarioEntregaLista;
    private HorarioEntrega horarioEntrega;
    private Solucion solucion;
    
    private Integer numeroImpresiones;
    private String notas;

    @PostConstruct
    public void init() {
        LOGGER.trace("mx.mc.magedbean.InspeccionarMezclaSolucionMB.init()");

        inicializa();

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        this.sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        this.permiso = Comunes.obtenerPermisos(Transaccion_Enum.INSPECCSOLUCION.getSufijo());
        this.usuarioSelected = this.sesion.getUsuarioSelected();
        this.nombreCompleto = (this.usuarioSelected.getNombre() + " " + this.usuarioSelected.getApellidoPaterno() + " " + this.usuarioSelected.getApellidoMaterno()).toUpperCase();

        this.cantPrintMultiplicador = this.sesion.getNumEtiquetasImpresaMezcla();

        this.listEstatusSurtimiento = new ArrayList<>();
        this.listEstatusSurtimiento.add(EstatusSurtimiento_Enum.SURTIDO.getValue());

        this.estatusSolucionLista = new ArrayList<>();
        this.estatusSolucionLista.add(EstatusSolucion_Enum.MEZCLA_PREPARADA.getValue());

        this.solUtils = new SolucionUtils(estructuraService, surtimientoService, templateService, impresoraService, motivosRechazoService, prescripcionService, surtimientoInsumoService, solucionService, reportesService, entidadHospitalariaService, diagnosticoService, envaseService, viaAdministracionService, tiposolucionService, protocoloService, visitaService, camService, frecuenciaService, catalogoGeneralService, turnoService, tipoUsuarioService, pacienteServicioService, pacienteUbicacionService, enviaCorreoUtil, plantillaCorreoService, configService, tipoJustificacionService, medicamentoService, usuarioService, pacienteService, hipersensibilidadService, reaccionService, equipoService, estabilidadService, inventarioService);
        try {
            obtenerImpresoras();
            obtenerTemplates();
            obtenerMotivosRechazo();
            obtenerServiciosQueSurte();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        obtenerSurtimientos();
        this.solucion = new Solucion();
    }

    private void inicializa() {
        LOGGER.trace("mx.mc.magedbean.InspeccionarMezclaSolucionMB.inicializa()");
        this.esSolucion = Constantes.ACTIVO;
        this.fechaActual = new java.util.Date();
        this.editable = false;
        this.paramEstatus = "estatus";
        this.surSinAlmacen = "sur.sin.almacen";
        this.errRegistroIncorrecto = "err.registro.incorrecto";
        this.surIncorrecto = "sur.incorrecto";

        paramBusquedaReporte = new ParamBusquedaReporte();

        preparada = Constantes.PREPARADA;
        mezcla_correcta = Constantes.MEZCLA_CORRECTA;
        mezcla_incorrecta = Constantes.MEZCLA_INCORRECTA;
        idMotivoRechazo = null;
        comentariosIncorrecta = "";
        diagnosticos = "";

        docEtiqueta = TipoImpresora_Enum.ETIQUETA.getValue();
        docReporte = TipoImpresora_Enum.NORMAL.getValue();
        mostrarImpresionEtiqueta = false;
        tipoArchivoImprimir = "";
        numTemp = 0;
    }

    private void obtenerImpresoras() {
        this.listaImpresoras = new ArrayList<>();
        this.listaImpresoras.addAll(this.solUtils.obtenerListaImpresoras(
                this.usuarioSelected, null, TipoImpresora_Enum.ETIQUETA.getValue()));
    }

    private void obtenerTemplates() {
        this.templateList = new ArrayList<>();
        String tipoTemplate = TipoTemplateEtiqueta_Enum.E.getValue();
        this.templateList.addAll(this.solUtils.obtenerListaTemplates(tipoTemplate));
    }

    private void obtenerMotivosRechazo() {
        this.listaMotivosRechazo = new ArrayList<>();
        this.listaMotivosRechazo.addAll(this.solUtils.obtenerMotivosRechazo());
    }

    private void obtenerServiciosQueSurte() {
        listServiciosQueSurte = new ArrayList<>();
        listServiciosQueSurte = obtieneServiciosQuePuedeSurtir(usuarioSelected);
//        this.listServiciosQueSurte.addAll(this.solUtils.obtieneServiciosQuePuedeSurtir(
//                this.usuarioSelected.getIdEstructura()));
    }
    
    /**
     * Obtiene los Servicios que puede surtir el usuario, dependiendo de la
     * idEstructura de la unidad jerarquica por la que consulta el usuario en
     * sesion Adicionalmente El area a la que el usuario este asignada, debe ser
     * de tipo almacen y que tenga una asignación de servicio hospitalario que
     * puede surtir
     *
     * @param u usuario en sesión que listará surtimientos
     * @return
     */
    public List<Estructura> obtieneServiciosQuePuedeSurtir(Usuario u) {
        LOGGER.trace("mx.mc.magedbean.DispensacionSolucionMB.obtieneServiciosQuePuedeSurtir()");
        List<Estructura> estLista = new ArrayList<>();

        if (u == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Usuario inválido.", null);

        } else if (u.getIdEstructura() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Área de usuario inválida.", null);

        } else {
            if (Objects.equals(u.getIdTipoPerfil(), TipoPerfilUsuario_Enum.ADMIN.getIdTipoPerfil())
                    || Objects.equals(u.getIdTipoPerfil(), TipoPerfilUsuario_Enum.JEFE_AREA.getIdTipoPerfil())) {

                try {
                    List<Integer> tipoEstructuraLista = new ArrayList<>();
                    tipoEstructuraLista.add(TipoAreaEstructura_Enum.AREA.getValue());
                    tipoEstructuraLista.add(TipoAreaEstructura_Enum.CONSULTAEXTERNA.getValue());
                    tipoEstructuraLista.add(TipoAreaEstructura_Enum.CONSULTAINTERNA.getValue());
                    tipoEstructuraLista.add(TipoAreaEstructura_Enum.ESPECIALIDAD.getValue());
                    tipoEstructuraLista.add(TipoAreaEstructura_Enum.PABELLO.getValue());
                    tipoEstructuraLista.add(TipoAreaEstructura_Enum.SERVICIO.getValue());

                    List<Estructura> estructuraServicio = estructuraService.obtenerEstructurasPorTipo(tipoEstructuraLista);
                    for (Estructura servicio : estructuraServicio) {
                        estLista.add(servicio);
                        List<String> idsEstructura = estructuraService.obtenerIdsEstructuraJerarquica(servicio.getIdEstructura(), true);
                        for (String item : idsEstructura) {
                            estLista.add(estructuraService.obtenerEstructura(item));
                        }
                    }
                } catch (Exception ex) {
                    LOGGER.error(ex.getMessage());
                }

            } else {
                try {
                    List<Estructura> estructuraServicio = estructuraService.obtenerServicioQueSurtePorIdEstructura(u.getIdEstructura());
                    for (Estructura servicio : estructuraServicio) {
                        estLista.add(servicio);
                        List<String> idsEstructura = estructuraService.obtenerIdsEstructuraJerarquica(servicio.getIdEstructura(), true);
                        for (String item : idsEstructura) {
                            estLista.add(estructuraService.obtenerEstructura(item));
                        }
                    }
                } catch (Exception excp) {
                    LOGGER.error("Error al obtener Servicios que puede surtir el usuario: {}", excp.getMessage());
                }
            }
        }
        return estLista;
    }
    

    /**
     * Método que busca las surtimientos de prescripción de soluciones o mezclas
     * dependiendo de los diferentes parámetros de búsqeuda
     *
     */
    public void obtenerSurtimientos() {
        LOGGER.trace("mx.mc.magedbean.InspeccionarMezclaSolucionMB.obtenerSoluciones()");

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
                List<Integer> listEstatusPacienteSol = null;
//                List<Integer> listEstatusPacienteSol = new ArrayList<>();
//                listEstatusPacienteSol.add(EstatusPaciente_Enum.ASIGNADO_A_SERVICIO.getValue());
//                listEstatusPacienteSol.add(EstatusPaciente_Enum.ASIGNADO_A_CAMA.getValue());
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

                int tipoProceso = Constantes.TIPO_PROCESO_QUIMICO;
                
                dispensacionSolucionLazy = new DispensacionSolucionLazy(
                        surtimientoService,
                        paramBusquedaReporte,
                        fechaProgramada,
                        tipoPrescripcionSelectedList,
                        listEstatusPacienteSol,
                        listEstatusPrescripcion,
                        listEstatusSurtimiento,
                        listServiciosQueSurte,
                        esSolucion,
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
        LOGGER.trace("mx.mc.magedbean.InspeccionarMezclaSolucionMB.onRowSelectSurtimiento()");
        surtimientoInsumoExtendedSelected = (SurtimientoInsumo_Extend) event.getObject();
        if (surtimientoInsumoExtendedSelected != null) {
            elementoSeleccionado = true;
        }
    }

    public void onRowUnselectSurtimiento() {
        LOGGER.trace("mx.mc.magedbean.InspeccionarMezclaSolucionMB.onRowUnselectSurtimiento()");
        surtimientoInsumoExtendedSelected = null;
        elementoSeleccionado = false;
    }

    public void onTabChange(TabChangeEvent evt) {
        LOGGER.trace("mx.mc.magedbean.InspeccionarMezclaSolucionMB.onTabChange()");
// regla: Listar prescripciones solo con estatus de surtimiento surtido
        this.estatusSolucionLista = new ArrayList<>();
        mostrarImpresionEtiqueta = false;
        String valorStatus = evt.getTab().getId();
        switch (valorStatus) {
            case Constantes.PREPARADA:
                estatusSolucion = EstatusSolucion_Enum.MEZCLA_PREPARADA.getValue();
                break;
            case Constantes.MEZCLA_CORRECTA:
                this.estatusSolucion = EstatusSolucion_Enum.INSPECCION_CONFORME.getValue();
                mostrarImpresionEtiqueta = true;
                break;
            case Constantes.MEZCLA_INCORRECTA:
                this.estatusSolucion = EstatusSolucion_Enum.INSPECCION_NO_CONFORME.getValue();
                break;
        }
        this.estatusSolucionLista.add(estatusSolucion);
        obtenerSurtimientos();
    }

    /**
     * Muestra el Detalle de cada surtimiento de cada prescripción
     */
    public void verSolucion() {
        LOGGER.trace("mx.mc.magedbean.InspeccionarMezclaSolucionMB.verSolucion()");
        boolean status = Constantes.INACTIVO;
        boolean modal = Constantes.INACTIVO;
        comentariosIncorrecta = "";
        idMotivoRechazo = null;
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
               
                surtimientoInsumoExtendedList = solUtils.eliminarRegistrosDuplicados(surtimientoInsumoExtendedList);
                obtenerPrescripcion();
                obtenerSolucion();
                obtenerIdentificacionMezcla();
                calculaCaducidadMezcla();

//                obtenerListaHorarios(prescripcionSelected.getIdTipoSolucion());
//                if (surtimientoExtendedSelected.getFechaParaEntregar() != null) {
//                    String horaEnt = FechaUtil.formatoCadena(surtimientoExtendedSelected.getFechaParaEntregar(), "HH:mm:ss");
//                    Date horaParaEntrega = FechaUtil.formatoFecha("HH:mm:ss", horaEnt);
//
//                    HorarioEntrega o = new HorarioEntrega();
//                    o.setHoraEntrega(horaParaEntrega);
//                    o.setActiva(-1);
//                    o.setIdTipoSolucion(prescripcionSelected.getIdTipoSolucion());
//                    HorarioEntrega he = this.horarioEntregaService.obtener(o);
//                    if (he == null) {
//                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Horario de entrega inválido. ", null);
//                    } else {
//                        surtimientoExtendedSelected.setIdHorarioParaEntregar(he.getIdHorarioEntrega());
//                    }
//                }
                evaluaEdicion();

                status = Constantes.ACTIVO;
            } catch (Exception ex) {
                LOGGER.error(RESOURCES.getString(surIncorrecto), ex);
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surIncorrecto), null);
            }
        }
        PrimeFaces.current().ajax().addCallbackParam("modal", modal);
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    private void evaluaEdicion() {
        LOGGER.trace("mx.mc.magedbean.PreparacionSolucionMB.evaluaEdicion()");
        this.editable = this.solUtils.evaluaEdicion(solucionSelect, permiso, EstatusSolucion_Enum.MEZCLA_PREPARADA.getValue());
    }

    private String claveMezcla;
    private String loteMezcla;
    private Date caducidadMezcla;
    private Integer estabilidadMezcla;

    private void obtenerIdentificacionMezcla() {
        LOGGER.trace("mx.mc.magedbean.PreparacionSolucionMB.obtenerIdentificacionMezcla()");

        CodigoInsumo cin = this.solUtils.obtenerIdentificacionMezcla(surtimientoExtendedSelected);
        if (cin != null) {
            this.claveMezcla = cin.getClave();
            this.loteMezcla = cin.getLote();
// TODO: REvisar el tema de la fecha
            this.caducidadMezcla = new java.util.Date(); // cin.getFecha();
        }

    }

    private void calculaCaducidadMezcla() {
        LOGGER.trace("mx.mc.magedbean.PreparacionSolucionMB.calculaCaducidadMezcla()");
        this.caducidadMezcla = this.solUtils.calculaCaducidadMezcla(estabilidadMezcla, solucionSelect);
    }

    
    private void obtenerPrescripcion() {
        LOGGER.trace("mx.mc.magedbean.PreparacionSolucionMB.obtenerPrescripcion()");
        this.prescripcionSelected = this.solUtils.obtenerPrescripcion(surtimientoExtendedSelected);
    }

    private List<String> obtenerDiagnosticos() {
        LOGGER.error("mx.mc.magedbean.PreparacionSolucionMB.obtenerDiagnosticos()");
        List<String> diagnosticoLista = this.solUtils.obtenerDiagnosticos(this.surtimientoExtendedSelected);
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

                    surtimientoExtendedSelected.setFechaParaEntregar(solucionSelect.getFechaParaEntregar());
                    surtimientoExtendedSelected.setProteccionLuz(solucionSelect.getProteccionLuz() == 1);
                    surtimientoExtendedSelected.setTempAmbiente(solucionSelect.getProteccionTemp() == 1);
                    surtimientoExtendedSelected.setTempRefrigeracion(solucionSelect.getProteccionTempRefrig() == 1);
                    surtimientoExtendedSelected.setNoAgitar(solucionSelect.getNoAgitar() == 1);
                    surtimientoExtendedSelected.setIndicaciones(solucionSelect.getIndicaciones());
                    if (solucionSelect.getProteccionLuz() != null && solucionSelect.getProteccionLuz()==1){
                        cantPrint = cantPrintMultiplicador * 2;
                    }

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

                        if (prescripcionSelected.getIdProtocolo() != null) {
                            Protocolo pr = obtenerProtocolo(prescripcionSelected.getIdProtocolo());
                            if (pr != null) {
                                surtimientoExtendedSelected.setNombreProtoclo(pr.getClaveProtocolo() + " - " + pr.getDescripcionProtocolo());
                            }
                        }

                        if (prescripcionSelected.getIdDiagnostico() != null) {
                            Diagnostico di = obtenerDiagnostico(prescripcionSelected.getIdDiagnostico());
                            if (di != null) {
                                surtimientoExtendedSelected.setNombreDiagnostico(di.getClave() + " - " + di.getNombre());
                            }
                        }
                    }
                    if (solucionSelect.getIdEnvaseContenedor() != null) {
                        EnvaseContenedor ec = obtenerContenedor(solucionSelect.getIdEnvaseContenedor());
                        if (ec != null) {
                            surtimientoExtendedSelected.setNombreEnvaseContenedor(ec.getDescripcion());
                        }
                    }
                    claveMezcla = solucionSelect.getClaveMezcla();
                    loteMezcla = solucionSelect.getLoteMezcla();
                    caducidadMezcla = solucionSelect.getCaducidadMezcla();
                    estabilidadMezcla = solucionSelect.getEstabilidadMezcla();
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al obtenerSolucion :: {} ", e.getMessage());
        }
    }

    private TipoSolucion obtenerTipoSolucion(String idTiposolucion) {
        return this.solUtils.obtenerTipoSolucion(idTiposolucion);
    }

    private ViaAdministracion obtenerViaAdministracion(Integer idViaAdministracion) {
        return this.solUtils.obtenerViaAdministracion(idViaAdministracion);
    }

    private EnvaseContenedor obtenerContenedor(Integer idEnvaseContenedor) {
        return this.solUtils.obtenerContenedor(idEnvaseContenedor);
    }

    private Protocolo obtenerProtocolo(Integer idProtocolo) {
        return this.solUtils.obtenerProtocolo(idProtocolo);
    }

    private Diagnostico obtenerDiagnostico(String idDiagnostico) {
        return this.solUtils.obtenerDiagnostico(idDiagnostico);
    }

    public void mezclaCorrecta() {
        boolean status = Constantes.INACTIVO;
        try {
            if (usuarioSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Usuario inválido. ", null);

            } else if (!permiso.isPuedeCrear() || !permiso.isPuedeEditar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No tiene permiso de esta acción. ", null);

            } else if (solucionSelect == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "La mezcla es inválida. ", null);

            } else if (solucionSelect.getApariencia() == null || !solucionSelect.getApariencia().trim().equals("Conforme")) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El campo Apariencia es requerido. ", null);
                
            } else if (solucionSelect.getIntegridadFisica() == null || !solucionSelect.getIntegridadFisica().trim().equals("Conforme")) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El campo Integridad física es requerido. ", null);
                
            } else {
                solucionSelect.setFechaInspeccion(new Date());
                solucionSelect.setIdUsuarioInspeccion(usuarioSelected.getIdUsuario());
                solucionSelect.setIdEstatusSolucion(EstatusSolucion_Enum.INSPECCION_CONFORME.getValue());
                solucionSelect.setUpdateFecha(new Date());
                solucionSelect.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
                solucionSelect.setLoteMezcla(loteMezcla);
                solucionSelect.setCaducidadMezcla(caducidadMezcla);
                solucionSelect.setClaveMezcla(claveMezcla);
                solucionSelect.setEstabilidadMezcla(estabilidadMezcla);

                status = solucionService.actualizar(solucionSelect);
                if (!status) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al confirmar inspección coforme de mezcla. ", null);

                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, "Registro correcto de inspección coforme de mezcla. ", null);

                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al confirmar la inspección conforme de la mezcla :: {}  ", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al confirmar inspección coforme de mezcla. ", null);
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    /**
     * registra la inpección no conforme de una mezcla elaborada
     */
    public void mezclaIncorrecta() {
        LOGGER.trace("mx.mc.magedbean.InspeccionarMezclaSolucionMB.mezclaIncorrecta()");
        boolean status = Constantes.INACTIVO;
        try {
            //solucionSelect = solucionService.obtenerXidSurtimiento(surtimientoExtendedSelected.getIdSurtimiento());
            if (usuarioSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Sesión de usuario inválida. ", null);

            } else if (!permiso.isPuedeEliminar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No tiene permisos para esta acción. ", null);

            } else if (prescripcionSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "La prescripción de la solución es inválida. ", null);

            } else if (surtimientoExtendedSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El surtimiento de la prescripción de la solución es inválida. ", null);

            } else if (solucionSelect == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "La solución es inválida. ", null);

            } else if (idMotivoRechazo == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Seleccione un motivo de rechazo.", null);

            } else {
                solucionSelect.setIdEstatusSolucion(EstatusSolucion_Enum.INSPECCION_NO_CONFORME.getValue());
                solucionSelect.setUpdateFecha(new Date());
                solucionSelect.setUpdateIdUsuario(usuarioSelected.getIdUsuario());

                solucionSelect.setFechaInspeccion(new Date());
                solucionSelect.setIdUsuarioInspeccion(usuarioSelected.getIdUsuario());
                solucionSelect.setComentarioInspeccion(comentariosIncorrecta);
                solucionSelect.setIdMotivoRechazo(idMotivoRechazo);

                Prescripcion nuevaPrescripcion = prescripcionService
                        .obtenerByFolioPrescripcion(surtimientoExtendedSelected.getFolioPrescripcion());
                nuevaPrescripcion.setIdEstatusPrescripcion(EstatusPrescripcion_Enum.PROGRAMADA.getValue());
                nuevaPrescripcion.setUpdateFecha(new Date());
                nuevaPrescripcion.setUpdateIdUsuario(usuarioSelected.getIdUsuario());

                List<PrescripcionInsumo> listaPrescripcionInsumo = prescripcionInsumoService
                        .obtenerPrescripcionInsumosPorIdPrescripcion(nuevaPrescripcion.getIdPrescripcion());
                for (PrescripcionInsumo prescripcionInsumoNuevo : listaPrescripcionInsumo) {
                    prescripcionInsumoNuevo.setIdEstatusPrescripcion(EstatusPrescripcion_Enum.PROGRAMADA.getValue());
                    prescripcionInsumoNuevo.setUpdateFecha(new Date());
                    prescripcionInsumoNuevo.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
                }

                Surtimiento surtimientoNuevo = surtimientoService
                        .obtenerByIdSurtimiento(surtimientoExtendedSelected.getIdSurtimiento());
                String idSurtimientoAnterior = surtimientoNuevo.getIdSurtimiento();
                String idSurtimientoNuevo = Comunes.getUUID();
                surtimientoNuevo.setIdSurtimiento(idSurtimientoNuevo);
                surtimientoNuevo.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.SURTIDO.getValue());

                String folioSurtimiento = surtimientoNuevo.getFolio();
                Integer siguienteNumero = Integer.parseInt(folioSurtimiento.substring(folioSurtimiento.length() - 2)) + 1;
                String folioSurtiSiguiente = siguienteNumero < 10 ? nuevaPrescripcion.getFolio() + "0" + String.valueOf(siguienteNumero)
                        : nuevaPrescripcion.getFolio() + String.valueOf(siguienteNumero);
                surtimientoNuevo.setFolio(folioSurtiSiguiente);
                surtimientoNuevo.setInsertFecha(new Date());
                surtimientoNuevo.setInsertIdUsuario(usuarioSelected.getIdUsuario());

                List<SurtimientoInsumo> listaSurtimientoInsumosNuevos = surtimientoInsumoService
                        .obtenerListSurtimientosInsumosByIdSurtimiento(idSurtimientoAnterior);

                for (SurtimientoInsumo surtimientoInsumoNuevo : listaSurtimientoInsumosNuevos) {
                    surtimientoInsumoNuevo.setIdSurtimientoInsumo(Comunes.getUUID());
                    surtimientoInsumoNuevo.setIdSurtimiento(idSurtimientoNuevo);
                    surtimientoInsumoNuevo.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.SURTIDO.getValue());
                    surtimientoInsumoNuevo.setFechaEnviada(null);
                    surtimientoInsumoNuevo.setIdUsuarioEnviada(null);
                    surtimientoInsumoNuevo.setCantidadEnviada(null);
                    surtimientoInsumoNuevo.setInsertFecha(new Date());
                    surtimientoInsumoNuevo.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                }

                status = solucionService.mezclaIncorrecta(solucionSelect, nuevaPrescripcion, listaPrescripcionInsumo, surtimientoNuevo, listaSurtimientoInsumosNuevos);

                if (!status) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al guardar el registro de inspección no conforme.", null);

                } else {
                    comentariosIncorrecta = "";
                    idMotivoRechazo = null;
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, "El registro de inspección no conforme ha sido guardado.", null);
                }
            }

        } catch (Exception ex) {
            LOGGER.error("Error al registrar la inspecció de la mecla no conforme. {} ", ex.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    public void previewPrint(Surtimiento_Extend item) {
        LOGGER.trace("mx.mc.magedbean.InspeccionarMezclaSolucionMB.previewPrint()");
        boolean status = false;
        byte[] buffer = null;
        try {

            Estructura estruct = estructuraService.obtenerEstructura(item.getIdEstructura());
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
                surtimientoExtendedSelected = (Surtimiento_Extend) dispensacionSolucionLazy.getRowKey(item);
            }

            List<Diagnostico> list = diagnosticoService.obtenerPorIdPacienteIdVisitaIdPrescripcion(surtimientoExtendedSelected.getIdPaciente(), surtimientoExtendedSelected.getIdVisita(), surtimientoExtendedSelected.getIdPrescripcion());
            list.forEach(a -> {
                if (a.getNombre().length() > 2) {
                    diagnosticos += "| " + a.getClave() + " - " + a.getNombre();
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
                    item,
                    solucion,
                    enti,
                    usuarioSelected.getNombre() + ' ' + usuarioSelected.getApellidoPaterno() + ' ' + usuarioSelected.getApellidoMaterno(),
                    alergias,
                    diagnosticos,
                    contenedor.getDescripcion(),
                    prescripcion
            );

            if (buffer != null) {
                sesion.setReportStream(buffer);
                sesion.setReportName(String.format("OrdenPreparacion_%s.pdf", item.getFolio()));
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

    public ParamBusquedaReporte getParamBusquedaReporte() {
        return paramBusquedaReporte;
    }

    public void setParamBusquedaReporte(ParamBusquedaReporte paramBusquedaReporte) {
        this.paramBusquedaReporte = paramBusquedaReporte;
    }

    public boolean isEsSolucion() {
        return esSolucion;
    }

    public void setEsSolucion(boolean esSolucion) {
        this.esSolucion = esSolucion;
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

    public String getComentariosIncorrecta() {
        return comentariosIncorrecta;
    }

    public void setComentariosIncorrecta(String comentariosIncorrecta) {
        this.comentariosIncorrecta = comentariosIncorrecta;
    }

    public String getPreparada() {
        return preparada;
    }

    public void setPreparada(String preparada) {
        this.preparada = preparada;
    }

    public String getMezcla_correcta() {
        return mezcla_correcta;
    }

    public void setMezcla_correcta(String mezcla_correcta) {
        this.mezcla_correcta = mezcla_correcta;
    }

    public String getMezcla_incorrecta() {
        return mezcla_incorrecta;
    }

    public void setMezcla_incorrecta(String mezcla_incorrecta) {
        this.mezcla_incorrecta = mezcla_incorrecta;
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

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    private boolean mostrarImpresionEtiqueta;
    private String tipoArchivoImprimir;
    private boolean imprimeReporte;
    private boolean imprimeEtiqueta;
    private String docReporte;
    private String docEtiqueta;
    private String surCaducidadvencida;
    private String surLoteIncorrecto;
    private String surInvalido;

    private transient List<TemplateEtiqueta> templateList;
    private transient List<TemplateEtiqueta> templateListPorSolucion;
    private String template;
    private Integer numTemp;
    private String itemPirnt;
    private Boolean activeBtnPrint;
    private String idPrintSelect;
    private Integer cantPrint;
    private Integer cantPrintMultiplicador;

    private Impresora impresoraSelect;
    private transient List<Impresora> listaImpresoras;
    private transient List<Impresora> listaImpresorasPorSolucion;

    @Autowired
    private transient TemplateEtiquetaService templateService;

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

    public void activaImpresion() {
        LOGGER.trace("mx.mc.magedbean.InspeccionarMezclaSolucionMB.activaImpresion()");
        this.imprimeEtiqueta = Constantes.INACTIVO;
        if (this.docEtiqueta != null && this.idPrintSelect != null && this.template != null && this.cantPrint > 0) {
            this.imprimeEtiqueta = Constantes.ACTIVO;
        }
    }

    public void cancelImprimirEtiqueta() {
        this.idPrintSelect = null;
        this.docEtiqueta = null;
        this.template = null;
        this.imprimeEtiqueta = Constantes.INACTIVO;
    }

    public void imprimirSurtimiento() {
        LOGGER.trace("mx.mc.magedbean.DispensacionSolucionMB.imprimirSurtimiento()");
        boolean status = Constantes.INACTIVO;
        try {
            RepSurtimientoPresc repSurtimientoPresc = obtenerDatosReporte();
            EstatusSurtimiento_Enum statusSurtimiento = EstatusSurtimiento_Enum.getStatusFromId(surtimientoExtendedSelected.getIdEstatusSurtimiento());

            byte[] buffer = reportesService.imprimeSurtimientoPrescInt(
                    repSurtimientoPresc, statusSurtimiento.name(),
                    surtimientoExtendedSelected.getDetalle() != null ? surtimientoExtendedSelected.getDetalle().size() : 0);
            if (buffer != null) {
                status = Constantes.ACTIVO;
                sesion.setReportStream(buffer);
                sesion.setReportName(String.format("DispInsumos%s.pdf", surtimientoExtendedSelected.getFolio()));
                obtenerSurtimientos();
            }
        } catch (Exception e) {
            LOGGER.error("Error en imprimirSurtimiento: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    private RepSurtimientoPresc obtenerDatosReporte() {
        RepSurtimientoPresc repSurtimientoPresc = new RepSurtimientoPresc();
        repSurtimientoPresc.setUnidadHospitalaria("");
        repSurtimientoPresc.setClasificacionPresupuestal("");
        try {
            Estructura estr = estructuraService.obtenerEstructura(surtimientoExtendedSelected.getIdEstructura());
            repSurtimientoPresc.setClasificacionPresupuestal(estr.getClavePresupuestal() == null ? "" : estr.getClavePresupuestal());
            EntidadHospitalaria entidad = entidadHospitalariaService.obtenerEntidadById(estr.getIdEntidadHospitalaria());
            if (entidad != null) {
                repSurtimientoPresc.setUnidadHospitalaria(entidad.getNombre());
            }
            repSurtimientoPresc.setPiso(estr.getUbicacion());

            Surtimiento surt = surtimientoService.obtenerPorFolio(surtimientoExtendedSelected.getFolio());
            repSurtimientoPresc.setFechaSolicitado(surt.getFechaProgramada());

            Estructura valorServicio = estructuraService.obtenerEstructura(usuarioSelected.getIdEstructura());
            if (valorServicio != null) {
                repSurtimientoPresc.setNombreCopia(valorServicio.getNombre());
            }

            SurtimientoInsumo surIns = new SurtimientoInsumo();
            surIns.setIdSurtimiento(surt.getIdSurtimiento());
            List<SurtimientoInsumo> lsi = surtimientoInsumoService.obtenerLista(surIns);
            if (lsi != null && !lsi.isEmpty()) {
                surIns = lsi.get(0);
                repSurtimientoPresc.setFechaAtendido(surIns.getFechaEnviada());
            }
        } catch (Exception exc) {
            LOGGER.error(exc.getMessage());
        }

        repSurtimientoPresc.setFolioPrescripcion(surtimientoExtendedSelected.getFolioPrescripcion());
        repSurtimientoPresc.setFolioSurtimiento(surtimientoExtendedSelected.getFolio());
        repSurtimientoPresc.setFechaActual(new Date());
        repSurtimientoPresc.setNombrePaciente(surtimientoExtendedSelected.getNombrePaciente());
        repSurtimientoPresc.setClavePaciente(surtimientoExtendedSelected.getClaveDerechohabiencia());
        repSurtimientoPresc.setServicio(surtimientoExtendedSelected.getNombreEstructura());
        repSurtimientoPresc.setCama(surtimientoExtendedSelected.getCama());
        repSurtimientoPresc.setTurno(surtimientoExtendedSelected.getTurno());
        repSurtimientoPresc.setIdEstatusSurtimiento(surtimientoExtendedSelected.getIdEstatusSurtimiento());
        return repSurtimientoPresc;
    }

    public void preImpresionEtiqueta(Surtimiento_Extend item) {
        LOGGER.trace("mx.mc.magedbean.InspeccionarMezclaSolucionMB.preparaImpresionEtiqueta()");
        boolean status = false;

        if (usuarioSelected == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Usuario inválido. ", null);

        } else if (!permiso.isPuedeCrear() || !permiso.isPuedeEditar()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No tiene permisos para esta acción. ", null);
            
        } else if (item == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Impresión de mezcla inválida. ", null);

        } else if (item.getIdSurtimiento() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Impresión de mezcla inválida. ", null);

        } else {
            TipoSolucion ts = null;
            this.prescripcionSelected = null;
            try {
                Prescripcion p = new Prescripcion();
                p.setIdPrescripcion(item.getIdPrescripcion());
                this.prescripcionSelected = prescripcionService.obtener(p);
                if (prescripcionSelected != null) {
                    ts = tiposolucionService.obtener(new TipoSolucion(prescripcionSelected.getIdTipoSolucion()));
                }
            } catch (Exception e) {
                LOGGER.error("Error al obtener Solución para imprimir {} ", e.getMessage());
            }

            this.surtimientoExtendedSelected = null;
            try {
                this.surtimientoExtendedSelected = surtimientoService.obtenerSurtimientoExtendedByIdSurtimiento(item.getIdSurtimiento());
            } catch (Exception e) {
                LOGGER.error("Error al obtener mezcla para imprimir ");
            }

            this.solucionSelect = null;
            try {
                Solucion s = new Solucion();
                s.setIdSurtimiento(this.surtimientoExtendedSelected.getIdSurtimiento());
                this.solucionSelect = solucionService.obtener(s);
                if (solucionSelect.getProteccionLuz() != null && solucionSelect.getProteccionLuz() == 1) {
                    cantPrint = cantPrintMultiplicador * 2;
                } else {
                    cantPrint = cantPrintMultiplicador;
                }
//                this.solucionSelect = solucionService.obtenerDatosSolucionByIdSurtimiento(this.surtimientoExtendedSelected.getIdSurtimiento());
            } catch (Exception e) {
                LOGGER.error("Error al obtener Solución para imprimir {} ", e.getMessage());
            }

            if (this.prescripcionSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Impresión de mezcla inválida. ", null);

            } else if (this.surtimientoExtendedSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Impresión de mezcla inválida. ", null);

            } else if (this.surtimientoExtendedSelected.getIdSurtimiento() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Impresión de mezcla inválida. ", null);

            } else if (this.solucionSelect == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No se pueden ontener datos de la mezcla para imprimir. ", null);

            } else {

                numeroImpresiones = buscarImpresiones(surtimientoExtendedSelected.getIdSurtimiento());
                notas = "";

                if (numeroImpresiones > 0 && !permiso.isPuedeAutorizar()) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Se detecta una impresión previa, requiere permiso de reimpresion. ", null);
                } else {

                    this.claveMezcla = solucionSelect.getClaveMezcla();
                    this.loteMezcla = solucionSelect.getLoteMezcla();
                    this.caducidadMezcla = solucionSelect.getCaducidadMezcla();

                    if (this.listaImpresoras == null || this.listaImpresoras.isEmpty()) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No se encontraron impresoras disponibles. ", null);

                    } else if (this.templateList == null || this.templateList.isEmpty()) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No se encontraron plantillas de impresión. ", null);
                    } else {
                        listaImpresorasPorSolucion = new ArrayList<>();
                        listaImpresorasPorSolucion.addAll(listaImpresoras);
                        numTemp = templateList.size();
                        templateListPorSolucion = new ArrayList<>();
                        templateListPorSolucion.addAll(templateList);

                        if (!permiso.isPuedeAutorizar()) {
                            listaImpresorasPorSolucion = new ArrayList<>();
                        }

                        for (Impresora imp : listaImpresoras) {
                            if (imp.getIdTipoSolucion().trim().equalsIgnoreCase(ts.getIdTipoSolucion())) {
                                idPrintSelect = imp.getIdImpresora();
                                if (!permiso.isPuedeAutorizar()) {
                                    listaImpresorasPorSolucion.add(imp);
                                }
                                break;
                            }
                        }

                        if (!permiso.isPuedeAutorizar()) {
                            templateListPorSolucion = new ArrayList<>();
                        }
                        for (TemplateEtiqueta temp : templateList) {
                            if (temp.getIdtipoSolucion().trim().equalsIgnoreCase(ts.getIdTipoSolucion())) {
                                template = temp.getIdTemplate();
                                if (!permiso.isPuedeAutorizar()) {
                                    templateListPorSolucion.add(temp);
                                }
                                break;
                            }
                        }
                        activaImpresion();
                        status = true;
                    }
                }
            }
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    /**
     * Imprime la etiqueta con la ClaveAgrupada despues de surtirse la solucion.
     *
     */
    public void imprimirEtiquetaItem() {
        LOGGER.trace("mx.mc.magedbean.InspeccionarMezclaSolucionMB.imprimirEtiquetaItem()");
        boolean status = false;

        if (this.prescripcionSelected == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No se pueden ontener datos de la prescripción de mezcla para imprimir. ", null);

        } else if (surtimientoExtendedSelected == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Surtimiento asociado a la mezcla inválido. ", null);

        } else if (surtimientoExtendedSelected.getIdSurtimiento() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Surtimiento asociado a la mezcla inválido. ", null);

        } else if (surtimientoExtendedSelected.getIdPrescripcion() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Prescripcion asociadaa a la mezcla inválida. ", null);

        } else if (idPrintSelect == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Impresora seleccionada inválida. ", null);

        } else if (template == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Plantilla de etiqueta seleccionada inválida. ", null);

        } else {

            Impresora print = null;
            EtiquetaInsumo etiqueta = null;
            try {
                print = impresoraService.obtenerPorIdImpresora(idPrintSelect);
            } catch (Exception e) {
                LOGGER.error("Error al obtener datos de la impresora seleccionada {} ", e.getMessage());
            }

            TemplateEtiqueta templaEtiqueta = null;
            try {
                templaEtiqueta = templateService.obtenerById(template);
            } catch (Exception e) {
                LOGGER.error("Error al obtener datos de la platilla de impresion seleccionada {} ", e.getMessage());
            }

            ViaAdministracion va  = null;
            if (solucionSelect.getIdViaAdministracion() != null) {
                try {
                    va  = viaAdministracionService.obtenerPorId(solucionSelect.getIdViaAdministracion());
                } catch (Exception e) {
                    LOGGER.error("Error al obtener la vía de administración para la impresion seleccionada {} ", e.getMessage());
                }
            }

            String nombreArea = "";
            String nombreHospital = "";
            String direccionHospital = "";

            try {
                Estructura e = estructuraService.obtenerEstructura(this.surtimientoExtendedSelected.getIdEstructuraAlmacen());
                if (e.getNombre() != null) {
                    nombreArea = e.getNombre();
                    if (e.getIdEntidadHospitalaria() != null) {
                        EntidadHospitalaria eh = entidadHospitalariaService.obtenerEntidadById(e.getIdEntidadHospitalaria());
                        if (eh != null) {
                            nombreHospital = eh.getNombre();
                            direccionHospital = eh.getDomicilio();
                        }
                    }
                }
            } catch (Exception e) {
                LOGGER.error("Error la buscar la entidad hospitalaria {} ", e.getMessage());
            }

            if (this.solucionSelect == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No se pueden ontener datos de la mezcla para imprimir. ", null);

            } else if (print == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No se pueden obetener datos de la impresora seleccionada. ", null);

            } else if (print.getIdImpresora() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No se pueden obetener datos de la impresora seleccionada. ", null);

            } else if (templaEtiqueta == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No se pueden obetener datos de la plantilla de impresión seleccionada. ", null);

            } else if (templaEtiqueta.getContenido() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No se pueden obetener datos de la plantilla de impresión seleccionada. ", null);

            } else {
                String cont = templaEtiqueta.getContenido();

                cont = cont.replace("#TITULO1#", (!nombreHospital.equals(StringUtils.EMPTY)) ? eliminaCarEspe(nombreHospital) : "");
                cont = cont.replace("#TITULO2#", (!nombreArea.equals(StringUtils.EMPTY)) ? eliminaCarEspe(nombreArea) : "");

                cont = cont.replace("#FOL_OPREP#", (surtimientoExtendedSelected.getFolio() != null) ? surtimientoExtendedSelected.getFolio() : "");
                cont = cont.replace("#FOL_PRES#", (prescripcionSelected.getFolio() != null) ? prescripcionSelected.getFolio() : "");
                cont = cont.replace("#FECHA#", (surtimientoExtendedSelected.getFechaProgramada() != null) ? FechaUtil.formatoFecha(surtimientoExtendedSelected.getFechaProgramada(), "dd/MM/yyyy HH:mm") : "");
                
                
                
                String codQr = eliminaCarEspe(surtimientoExtendedSelected.getClaveAgrupada());
                cont = cont.replace("#COD_QR#", (codQr != null && !codQr.trim().isEmpty()) ? codQr : "0");
                String numEt = "1";
                cont = cont.replace("#NUM_ETQ#", numEt);
                
                cont = cont.replace("#NOM_COM_PAC#", (surtimientoExtendedSelected.getNombrePaciente() != null) ? eliminaCarEspe(surtimientoExtendedSelected.getNombrePaciente()) : "");
                cont = cont.replace("#CLA_PAC#", (surtimientoExtendedSelected.getPacienteNumero() != null) ? surtimientoExtendedSelected.getPacienteNumero() : "");
//                cont = cont.replace("#FECHA_NAC#", (surtimientoExtendedSelected.getEdadPaciente() > 0) ? String.valueOf(surtimientoExtendedSelected.getEdadPaciente()) : "");
                cont = cont.replace("#FECHA_NAC#", (surtimientoExtendedSelected.getFechaNacimiento() != null) ? FechaUtil.formatoCadena(surtimientoExtendedSelected.getFechaNacimiento(), "yyyy-MM-dd") : "");
                cont = cont.replace("#PESO#", (surtimientoExtendedSelected.getPesoPaciente() > 0) ? String.valueOf(surtimientoExtendedSelected.getPesoPaciente()) : "");
                cont = cont.replace("#NUM_PAC#", (surtimientoExtendedSelected.getPacienteNumero() != null) ? surtimientoExtendedSelected.getPacienteNumero() : "");

                cont = cont.replace("#NOM_CAM#", (surtimientoExtendedSelected.getCama() != null) ? eliminaCarEspe(surtimientoExtendedSelected.getCama()) : "");
                cont = cont.replace("#NOM_SER#", (surtimientoExtendedSelected.getNombreEstructura() != null) ? eliminaCarEspe(surtimientoExtendedSelected.getNombreEstructura()) : "");
                cont = cont.replace("#NOM_MED#", (surtimientoExtendedSelected.getNombreMedico() != null) ? eliminaCarEspe(surtimientoExtendedSelected.getNombreMedico()) : "");
                cont = cont.replace("#CED_MED#", (surtimientoExtendedSelected.getCedProfesional() != null) ? surtimientoExtendedSelected.getCedProfesional() : "");

                cont = cont.replace("#FEC_PREP#", (solucionSelect.getFechaPrepara() != null) ? FechaUtil.formatoFecha(solucionSelect.getFechaPrepara(), "dd/MM/yyyy HH:mm") : "");
                
                cont = cont.replace("#VEL_INF#", (solucionSelect.getVelocidad() != null && solucionSelect.getVelocidad() > 0) ? String.valueOf(solucionSelect.getVelocidad()) : "");
                cont = cont.replace("#TIE_INF#", (solucionSelect.getMinutosInfusion() != null && solucionSelect.getMinutosInfusion() > 0) ? String.valueOf(solucionSelect.getMinutosInfusion()) : "");
                cont = cont.replace("#VOL_TOT#", (solucionSelect.getVolumen() != null) ? String.valueOf(solucionSelect.getVolumen()) : "");
                cont = cont.replace("#TIP_SOL#", (surtimientoExtendedSelected.getTipoSolucion() != null) ? eliminaCarEspe(surtimientoExtendedSelected.getTipoSolucion()) : "");
                cont = cont.replace("#TIP_INF#", (surtimientoExtendedSelected.getTipoSolucion() != null) ? eliminaCarEspe(surtimientoExtendedSelected.getTipoSolucion()) : "");
                cont = cont.replace("#VIA_ADM#", (va  != null) ? eliminaCarEspe(va.getNombreViaAdministracion()) : "");
                cont = cont.replace("#PES_TOT#", (solucionSelect.getPesoTotal() !=null && solucionSelect.getPesoTotal() > 0) ? String.valueOf(solucionSelect.getPesoTotal()) : "");
                cont = cont.replace("#CAL_TOT#", (solucionSelect.getPesoTotal() !=null && solucionSelect.getCaloriasTotales() > 0) ? String.valueOf(solucionSelect.getCaloriasTotales()) : "");
                cont = cont.replace("#OSM_TOT#", (solucionSelect.getPesoTotal() !=null && solucionSelect.getOmolairdadTotal() > 0) ? String.valueOf(solucionSelect.getOmolairdadTotal()) : "");
// TODO: Agregar el overfill
                cont = cont.replace("#OVERFILL#", (solucionSelect.getSobrellenado() !=null && solucionSelect.getSobrellenado()> 0) ? String.valueOf(solucionSelect.getSobrellenado()) : "");
                cont = cont.replace("#CON_MINISTRACION#", "");
                
                cont = cont.replace("#LOTE_MEZ#", (solucionSelect.getLoteMezcla()!= null) ? solucionSelect.getLoteMezcla(): "");
                
                try {
                    surtimientoInsumoExtendedList = new ArrayList<>();
                    boolean mayorCero = true;
                    surtimientoInsumoExtendedList.addAll(surtimientoInsumoService.obtenerSurtimientoInsumosByIdSurtimiento(surtimientoExtendedSelected.getIdSurtimiento(), mayorCero));

                    for (SurtimientoInsumo_Extend item : surtimientoInsumoExtendedList) {
                        List<SurtimientoEnviado_Extend> seList = enviadoService.detalleSurtimientoEnviadoXIdSurtimientoInsumo(item.getIdSurtimientoInsumo(), mayorCero);
                        item.setSurtimientoEnviadoExtendList(seList);
                    }
                    Estabilidad_Extended est = solUtils.obtenerEstabilidad(surtimientoInsumoExtendedList, va);
                    cont = cont.replace("#TIE_RF#", (est != null && est.getLimHrsUsoRedFria() != null && est.getLimHrsUsoRedFria() > 0) ? est.getLimHrsUsoRedFria().toString() : "");
                    cont = cont.replace("#TIE_RS#", (est != null && est.getLimHrsUsoRedSeca() != null) ? est.getLimHrsUsoRedSeca().toString() : "");
//                    cont = cont.replace("#FEC_MAX_MIN#", (solucionSelect.getCaducidadMezcla() != null) ? FechaUtil.formatoFecha(solucionSelect.getCaducidadMezcla(), "dd/MM/yyyy HH:mm") : "");
                    cont = cont.replace("#FEC_MAX_MIN#", (est != null && est.getLimHrsUsoRedFria() != null && est.getLimHrsUsoRedFria() > 0 && solucionSelect.getFechaPrepara() != null) ? FechaUtil.formatoFecha( FechaUtil.sumarRestarHorasFecha(solucionSelect.getFechaPrepara(), est.getLimHrsUsoRedFria() ), "dd/MM/yyyy HH:mm") : "");
                    cont = cont.replace("#FEC_MAX_MIN_RS#", (est != null && est.getLimHrsUsoRedSeca()!= null && solucionSelect.getFechaPrepara() != null) ? FechaUtil.formatoFecha( FechaUtil.sumarRestarHorasFecha(solucionSelect.getFechaPrepara(), est.getLimHrsUsoRedSeca() ), "dd/MM/yyyy HH:mm") : "");
                
                } catch (Exception exc ){
                    LOGGER.error("Error al buscar las máximas fechas de uso en red fria y seca de la estabilidad :: {} ", exc.getMessage());
                }
                cont = cont.replace("#COMENTARIOS#", (solucionSelect.getObservaciones() != null) ? eliminaCarEspe(solucionSelect.getObservaciones()) : "");
                
                StringBuilder condConserv = new StringBuilder();
                if (solucionSelect.getProteccionLuz() != null && solucionSelect.getProteccionLuz()>0) {
                    condConserv.append("Protoger de la luz. | ");
                }
                if (solucionSelect.getProteccionTempRefrig()!= null && solucionSelect.getProteccionTempRefrig()>0) {
                    condConserv.append("Mant. temp. Refrig. | ");
                }
                if (solucionSelect.getProteccionTemp()!= null && solucionSelect.getProteccionTemp()>0) {
                    condConserv.append("Mant. temp. ambiente. | ");
                }

                String condConservacion = condConserv.toString();
                
                cont = cont.replace("#CON_CONSERVACION#", (condConservacion != null) ? eliminaCarEspe(condConservacion) : "");

                String usuarioPrepara = "";
                if (solucionSelect.getIdUsuarioPrepara() != null) {
                    try {
                        Usuario u = usuarioService.obtenerUsuarioByIdUsuario(solucionSelect.getIdUsuarioPrepara());
                        if (u != null) {
                            usuarioPrepara = eliminaCarEspe(u.getNombre() + " " + u.getApellidoPaterno() + " " + u.getApellidoMaterno());
                        }
                    } catch (Exception e) {
                        LOGGER.error("Error al buscar el envase para la etiqueta {} ", e.getMessage());
                    }
                }

                cont = cont.replace("#NOMBRE_PREPARADOR#", usuarioPrepara);

                String nombreContenedor = "";
                if (solucionSelect.getIdEnvaseContenedor() != null) {
                    try {
                        EnvaseContenedor e = envaseService.obtenerXidEnvase(solucionSelect.getIdEnvaseContenedor());
                        if (e != null) {
                            nombreContenedor = eliminaCarEspe(e.getDescripcion());
                        }
                    } catch (Exception e) {
                        LOGGER.error("Error al buscar el envase para la etiqueta {} ", e.getMessage());
                    }
                }

                cont = cont.replace("#NOMBRE_CONTENEDOR#", nombreContenedor);

                try {
                    boolean mayorCero = true;
                    this.surtimientoInsumoExtendedList = surtimientoInsumoService.obtenerSurtimientoInsumosByIdSurtimiento(this.surtimientoExtendedSelected.getIdSurtimiento(), mayorCero);
                } catch (Exception e) {
                    LOGGER.error("Error al obtener los medicamentos de la mezcla. ");
                }
                List<String> listaIdSurtimientoInsumo = new ArrayList<>();
                boolean agrega = false;
                for (int i = 0; i < 25; i++) {
                    String num = String.format("%02d", i + 1);
                    if (this.surtimientoInsumoExtendedList != null && this.surtimientoInsumoExtendedList.size() >= i + 1) {
                        if (this.surtimientoInsumoExtendedList.get(i) != null &&
                                this.surtimientoInsumoExtendedList.get(i).getTipoInsumo() == CatalogoGeneral_Enum.MEDICAMENTO.getValue() ){
                            if(listaIdSurtimientoInsumo.isEmpty()) {
                                listaIdSurtimientoInsumo.add(this.surtimientoInsumoExtendedList.get(i).getIdSurtimientoInsumo());
                                agrega = true;
                            } else {
                                for(String idSurtimientoInsumo : listaIdSurtimientoInsumo) {
                                    if(idSurtimientoInsumo.equals(this.surtimientoInsumoExtendedList.get(i).getIdSurtimientoInsumo())) {
                                        agrega = false;
                                        break;
                                    } else {
                                       agrega = true; 
                                    }
                                }
                                listaIdSurtimientoInsumo.add(this.surtimientoInsumoExtendedList.get(i).getIdSurtimientoInsumo());
                            }                             
                            if(agrega) {
                                cont = cont.replace("#NOMBRE_MEDICAMENTO_" + num + "#",
                                        (this.surtimientoInsumoExtendedList.get(i).getNombreCorto() != null)
                                       ? eliminaCarEspe(this.surtimientoInsumoExtendedList.get(i).getNombreCorto())
                                       : "");
                               String cantUmedida = (this.surtimientoInsumoExtendedList.get(i).getDosis() != null)
                                       ? this.surtimientoInsumoExtendedList.get(i).getDosis().toString() : "";
                               cantUmedida = (this.surtimientoInsumoExtendedList.get(i).getUnidadConcentracion() != null)
                                       ? cantUmedida + " " + this.surtimientoInsumoExtendedList.get(i).getUnidadConcentracion()
                                       : cantUmedida + "";
                               cont = cont.replace("#VOL_" + num + "#", cantUmedida);
                               String dosis = (this.surtimientoInsumoExtendedList.get(i).getDosis() != null)
                                       ? this.surtimientoInsumoExtendedList.get(i).getDosis().toString() : "";
                               dosis = (this.surtimientoInsumoExtendedList.get(i).getDosis()!= null)
                                       ? dosis + " mL"
                                       : dosis + "";
                               cont = cont.replace("#DOSIS_" + num + "#", dosis);

                               String datoComp = (this.surtimientoInsumoExtendedList.get(i).getNombreFabricante() != null)
                                       ? "|FAB:" + this.surtimientoInsumoExtendedList.get(i).getNombreFabricante() : "|FAB: ";

                               datoComp = (this.surtimientoInsumoExtendedList.get(i).getLote() != null)
                                       ? datoComp + " |LOT:" + this.surtimientoInsumoExtendedList.get(i).getLote() : "|LOT: ";

                               cont = cont.replace("#DATO_INSUMO_" + num + "#", datoComp);
                               agrega = false;
                            }

                        } else {
                           cont = cont.replace("#NOMBRE_MEDICAMENTO_" + num + "#", "");
                           cont = cont.replace("#VOL_" + num + "#", "");
                           cont = cont.replace("#DOSIS_" + num + "#", "");
                           cont = cont.replace("#VOL_" + num + "#", "");
                           cont = cont.replace("#DATO_INSUMO_" + num + "#", "");
                        }
                    }                        
                }
                
                cont = cont.replace("#TIE_RF#", "");
                cont = cont.replace("#TIE_RS#", "");
                cont = cont.replace("#FEC_MAX_MIN#", "");
                cont = cont.replace("#FEC_MAX_MIN_RS#", "");
                
                cont = cont.replace("#NOMBRE_MEDICAMENTO_01#", "");
                cont = cont.replace("#VOL_01#", "");
                
                cont = cont.replace("#NOMBRE_MEDICAMENTO_02#", "");
                cont = cont.replace("#VOL_02#", "");
                
                cont = cont.replace("#NOMBRE_MEDICAMENTO_03#", "");
                cont = cont.replace("#VOL_03#", "");
                
                cont = cont.replace("#NOMBRE_MEDICAMENTO_04#", "");
                cont = cont.replace("#VOL_04#", "");
                
                cont = cont.replace("#DOM_HOSP#", eliminaCarEspe(direccionHospital));

                /*NOMBRE_MEDICAMENTO_02  CANT_UMEDIDA_01
FECHA_PREP      FOL_OPREP       FECHA_PREP
NOM_COM_PAC     CLA_PAC         EDAD        PESO                NUM_PAC
NOM_CAM         NOM_SER         NOM_MED     CED_MED
FEC_MAX_MIN     VEL_INF         TIP_INF     CAL_TOT             PES_TOT       VOL_TOT       OSM_TOT
COMENTARIOS     CON_MINISTRACION            CON_CONSERVACION    NUM_ETQ                        */
                templaEtiqueta.setContenido(cont);
                Integer band = 1;
                if (band == 1) {
                    try {
                        status = reportesService.imprimirEtiquetaCM(templaEtiqueta, cantPrint, print.getIpImpresora());
                    } catch (Exception e) {
                        LOGGER.error("Error al generar la impresión {} ", e.getMessage());
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, e.getMessage(), null);
                    }
                }
                if (status) {
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("paciente.info.impCorrectamente"), null);
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No se pudo generar la impresión.", null);
                }
                Integer idMotivoReimpresion = 0;
                    String notas = "";
                    String idUsuario = usuarioSelected.getIdUsuario();
                    registrarImpresion(
                            surtimientoExtendedSelected.getIdSurtimiento()
                            , surtimientoExtendedSelected.getFolio()
                            , cont, idMotivoReimpresion, notas, idUsuario
                            , cantPrint, status);
            }
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    private String eliminaCarEspe(String texto) {
        texto = Normalizer.normalize(texto, Normalizer.Form.NFD);
        texto = texto.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return texto;
    }

    public String getTipoArchivoImprimir() {
        return tipoArchivoImprimir;
    }

    public void setTipoArchivoImprimir(String tipoArchivoImprimir) {
        this.tipoArchivoImprimir = tipoArchivoImprimir;
    }

    public boolean isImprimeReporte() {
        return imprimeReporte;
    }

    public void setImprimeReporte(boolean imprimeReporte) {
        this.imprimeReporte = imprimeReporte;
    }

    public boolean isImprimeEtiqueta() {
        return imprimeEtiqueta;
    }

    public void setImprimeEtiqueta(boolean imprimeEtiqueta) {
        this.imprimeEtiqueta = imprimeEtiqueta;
    }

    public String getDocReporte() {
        return docReporte;
    }

    public void setDocReporte(String docReporte) {
        this.docReporte = docReporte;
    }

    public String getDocEtiqueta() {
        return docEtiqueta;
    }

    public void setDocEtiqueta(String docEtiqueta) {
        this.docEtiqueta = docEtiqueta;
    }

    public List<TemplateEtiqueta> getTemplateList() {
        return templateList;
    }

    public void setTemplateList(List<TemplateEtiqueta> templateList) {
        this.templateList = templateList;
    }

    public String getItemPirnt() {
        return itemPirnt;
    }

    public void setItemPirnt(String itemPirnt) {
        this.itemPirnt = itemPirnt;
    }

    public Boolean getActiveBtnPrint() {
        return activeBtnPrint;
    }

    public void setActiveBtnPrint(Boolean activeBtnPrint) {
        this.activeBtnPrint = activeBtnPrint;
    }

    public boolean isMostrarImpresionEtiqueta() {
        return mostrarImpresionEtiqueta;
    }

    public void setMostrarImpresionEtiqueta(boolean mostrarImpresionEtiqueta) {
        this.mostrarImpresionEtiqueta = mostrarImpresionEtiqueta;
    }

    public String getSurCaducidadvencida() {
        return surCaducidadvencida;
    }

    public void setSurCaducidadvencida(String surCaducidadvencida) {
        this.surCaducidadvencida = surCaducidadvencida;
    }

    public String getSurLoteIncorrecto() {
        return surLoteIncorrecto;
    }

    public void setSurLoteIncorrecto(String surLoteIncorrecto) {
        this.surLoteIncorrecto = surLoteIncorrecto;
    }

    public String getSurInvalido() {
        return surInvalido;
    }

    public void setSurInvalido(String surInvalido) {
        this.surInvalido = surInvalido;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Impresora getImpresoraSelect() {
        return impresoraSelect;
    }

    public void setImpresoraSelect(Impresora impresoraSelect) {
        this.impresoraSelect = impresoraSelect;
    }

    public List<Impresora> getListaImpresoras() {
        return listaImpresoras;
    }

    public void setListaImpresoras(List<Impresora> listaImpresoras) {
        this.listaImpresoras = listaImpresoras;
    }

    public Integer getNumTemp() {
        return numTemp;
    }

    public void setNumTemp(Integer numTemp) {
        this.numTemp = numTemp;
    }

    public String getIdPrintSelect() {
        return idPrintSelect;
    }

    public void setIdPrintSelect(String idPrintSelect) {
        this.idPrintSelect = idPrintSelect;
    }

    public Integer getCantPrint() {
        return cantPrint;
    }

    public void setCantPrint(Integer cantPrint) {
        this.cantPrint = cantPrint;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public Date getFechaParaEntregar() {
        return fechaParaEntregar;
    }

    public void setFechaParaEntregar(Date fechaParaEntregar) {
        this.fechaParaEntregar = fechaParaEntregar;
    }

    public Integer getIdHorarioParaEntregar() {
        return idHorarioParaEntregar;
    }

    public void setIdHorarioParaEntregar(Integer idHorarioParaEntregar) {
        this.idHorarioParaEntregar = idHorarioParaEntregar;
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
            he.setIdTipoSolucion(idTipoSolucionSelected);
            he.setActiva(1);
            this.horarioEntregaLista.addAll(this.horarioEntregaService.obtenerLista(he));
        } catch (Exception e) {
            LOGGER.error("Error al obtener lista de horarios de entrega :: {} ", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al obtener lista de horarios de entrega. ", null);
        }
    }

    public List<Impresora> getListaImpresorasPorSolucion() {
        return listaImpresorasPorSolucion;
    }

    public void setListaImpresorasPorSolucion(List<Impresora> listaImpresorasPorSolucion) {
        this.listaImpresorasPorSolucion = listaImpresorasPorSolucion;
    }

    public List<TemplateEtiqueta> getTemplateListPorSolucion() {
        return templateListPorSolucion;
    }

    public void setTemplateListPorSolucion(List<TemplateEtiqueta> templateListPorSolucion) {
        this.templateListPorSolucion = templateListPorSolucion;
    }
    
    /**
     * Realiza las acciones de validación y para cancelación de orden de preparación
     */
    public void validaCancelar() {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.validaCancelar()");
        boolean status = false;
        this.solucion = new Solucion();
        this.otro = false;

        if (this.usuarioSelected == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Usuario inválido.", null);

        } else if (this.usuarioSelected.getIdUsuario() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Usuario inválido.", null);

        } else if (!permiso.isPuedeAutorizar()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No tiene permisos de esta acción.", null);

        } else if (surtimientoExtendedSelected == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Surtimiento de prescripción inválido.", null);

        } else if (surtimientoExtendedSelected.getIdSurtimiento() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Surtimiento de prescripción inválido.", null);

        } else if (surtimientoExtendedSelected.getIdPrescripcion() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Prescripción inválida.", null);

        } else {
            String idSurtimiento = surtimientoExtendedSelected.getIdSurtimiento();
            String idSolucion = null;
            this.solucion = this.solUtils.obtenerSolucion(idSolucion, idSurtimiento);

            if (this.solucion == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Solución inválida.", null);

            } else if (!Objects.equals(solucion.getIdEstatusSolucion(), EstatusSolucion_Enum.MEZCLA_PREPARADA.getValue())) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El estatus no permite cancelación.", null);

            } else {
                status = true;
            }
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }
    
    private boolean otro;

    public void changeMotivo() {
        if (solucion != null) {
            if (Objects.equals(solucion.getIdMotivoCancelacion(), MotivoCancelacionRechazoSolucion_Enum.OTRO.getValue())) {
                otro = Constantes.ACTIVO;
            } else {
                otro = Constantes.INACTIVO;
            }
            solucion.setMotivoCancela("");
        }
    }

    public boolean isOtro() {
        return otro;
    }

    public void setOtro(boolean otro) {
        this.otro = otro;
    }
        
    /**
     * Permite cancelar una mezcla
     */
    public void cancelarSolucion() {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.cancelarSolucion()");
        boolean status = Constantes.INACTIVO;
        try {
            if (this.usuarioSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Usuario inválido.", null);

            } else if (!this.permiso.isPuedeAutorizar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No tiene permiso para realizar esta acción.", null);

            } else if (surtimientoExtendedSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Surtimiento de Mezcla inválido.", null);

            } else if (surtimientoExtendedSelected.getIdSurtimiento() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Surtimiento de Mezcla inválido.", null);

            } else if (!Objects.equals(surtimientoExtendedSelected.getIdEstatusSolucion(), EstatusSolucion_Enum.MEZCLA_PREPARADA.getValue())) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Estatus de Mezcla inválido.", null);

            } else  if(solucion == null){
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Mezcla inválida.", null);

            } else  if(solucion.getIdMotivoCancelacion() == null){
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Registre motivo de cancelación.", null);

            } else  if(Objects.equals(solucion.getIdMotivoCancelacion(), MotivoCancelacionRechazoSolucion_Enum.OTRO.getValue())
                    && solucion.getMotivoCancela().trim().isEmpty()){
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Registre cuál motivo de cancelación.", null);

            } else  if(solucion.getComentariosCancelacion() == null || solucion.getComentariosCancelacion().trim().isEmpty()){
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Registre comentarios de cancelación.", null);
            
            } else {
                Surtimiento surtimiento = new Surtimiento();
                surtimiento.setIdSurtimiento(surtimientoExtendedSelected.getIdSurtimiento());
                
                if (Objects.equals(surtimientoExtendedSelected.getIdEstatusSurtimiento(), EstatusSurtimiento_Enum.PROGRAMADO.getValue())) {
                    
                    surtimiento.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.CANCELADO.getValue());
                    
                    for (SurtimientoInsumo_Extend item : surtimientoInsumoExtendedList){
                        item.setUpdateFecha(new java.util.Date());
                        item.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
                        item.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.CANCELADO.getValue());
                        item.setFechaCancela(new java.util.Date());
                        item.setIdUsuarioCancela(usuarioSelected.getIdUsuario());
                        
                    }
                } else {
                    surtimiento.setIdEstatusSurtimiento(surtimientoExtendedSelected.getIdEstatusSurtimiento());
                }
                
                surtimiento.setUpdateFecha(new Date());
                surtimiento.setUpdateIdUsuario(usuarioSelected.getIdUsuario());

                Solucion o = solucionService.obtenerSolucion(null, this.surtimientoExtendedSelected.getIdSurtimiento());
                if (o == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Mezcla inválida.", null);
                    
                    
                } else {
                    Solucion s = new Solucion();
                    s.setIdSolucion(o.getIdSolucion());
                    s.setUpdateFecha(new java.util.Date());
                    s.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
                    
                    s.setIdEstatusSolucion(EstatusSolucion_Enum.CANCELADA.getValue());
                    
                    s.setIdUsuarioValOrdenPrep(usuarioSelected.getIdUsuario());
                    s.setIdUsuarioCancela(usuarioSelected.getIdUsuario());
                    s.setIdMotivoCancelacion(solucion.getIdMotivoCancelacion());
                    s.setComentariosCancelacion(solucion.getComentariosCancelacion());
                    
                    status = solucionService.rechazarMezcla(s, surtimiento, surtimientoInsumoExtendedList, false);
                }

                if (!status) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("reacciones.error.guardar"), null);

                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, "Orden de Preparación Cancelada.", null);

                    StringBuilder nombrePaciente = new StringBuilder();
                    String pacienteNumero = "";
                    if (this.surtimientoExtendedSelected != null) {
                        Paciente pac = pacienteService.obtenerPacienteByIdPaciente(this.surtimientoExtendedSelected.getIdPaciente());
                        if (pac != null) {
                            nombrePaciente.append(pac.getNombreCompleto());
                            nombrePaciente.append(StringUtils.SPACE);
                            nombrePaciente.append(pac.getApellidoPaterno());
                            nombrePaciente.append(StringUtils.SPACE);
                            nombrePaciente.append(pac.getApellidoMaterno());
                            pacienteNumero = pac.getPacienteNumero();
                        }
                    }
                    this.surtimientoExtendedSelected.setNombrePaciente(nombrePaciente.toString());
                    this.surtimientoExtendedSelected.setPacienteNumero(pacienteNumero);
                    Prescripcion p = this.solUtils.obtenerPrescripcion(surtimientoExtendedSelected);
                    Usuario u = null;
                    if (p != null) {
                        String IdUsuario = p.getIdMedico();
                        u = this.solUtils.obtenerUsuarioPorId(IdUsuario);
                        if (u != null) {
                            String asunto = "Orden de Preparación de mezcla " + surtimientoExtendedSelected.getFolio() + " Cancelada. ";
                            String msj = solucion.getComentariosCancelacion();
                            this.solUtils.enviarCorreo(u, this.usuarioSelected, asunto, msj, surtimientoExtendedSelected);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("ocurrio un error en: rechazarValidacionSolucion", ex);
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    public Solucion getSolucion() {
        return solucion;
    }

    public void setSolucion(Solucion solucion) {
        this.solucion = solucion;
    }
    

    /**
     * Realiza las acciones de validación y para rechazo de orden de preparación
     */
    public void validaNoConforme() {
        LOGGER.trace("mx.mc.magedbean.InspeccionarMezclaSolucionMB.validaNoConforme()");
        boolean status = false;
        this.solucion = new Solucion();
        this.otro = false;
        evaluaEdicion();
        if (this.usuarioSelected == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Usuario inválido.", null);

        } else if (this.usuarioSelected.getIdUsuario() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Usuario inválido.", null);

        } else if (!permiso.isPuedeAutorizar()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No tiene permisos de esta acción.", null);

        } else if (surtimientoExtendedSelected == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Surtimiento de prescripción inválido.", null);

        } else if (surtimientoExtendedSelected.getIdSurtimiento() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Surtimiento de prescripción inválido.", null);

        } else if (surtimientoExtendedSelected.getIdPrescripcion() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Prescripción inválida.", null);

        } else {
            String idSurtimiento = surtimientoExtendedSelected.getIdSurtimiento();
            String idSolucion = null;
            this.solucion = this.solUtils.obtenerSolucion(idSolucion, idSurtimiento);

            if (this.solucion == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Solución inválida.", null);

            } else if (!Objects.equals(solucion.getIdEstatusSolucion(), EstatusSolucion_Enum.MEZCLA_PREPARADA.getValue())) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El estatus no permite rechazo.", null);

            } else {
                status = true;
            }
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    private Integer buscarImpresiones(String idSurtimiento) {
        LOGGER.trace("mx.mc.magedbean.AcondicionarSolucionesMB.buscarImpresiones()");
        Integer numeroImpresiones = 1;
        try {
            ImpresionEtiqueta ie = new ImpresionEtiqueta();
            ie.setIdSurtimiento(idSurtimiento);
            List<ImpresionEtiqueta> itLista = impresionEtiquetaService.obtenerImpresiones(ie);
            if (itLista != null) {
                numeroImpresiones = itLista.size();
            }
        } catch (Exception e) {
            LOGGER.trace("Error al buscar bitácora de impresión :: {} ", e.getMessage());
        }
        return numeroImpresiones;
    }

    private void registrarImpresion(
            String idSurtimiento, String folio, String contenido
            , Integer setIdMotivoReimpresion, String notas, String idUsuario
            , Integer cantidadImpresiones, boolean impresionExitosa) {
        LOGGER.trace("mx.mc.magedbean.AcondicionarSolucionesMB.registrarImpresion()");
        try {
            ImpresionEtiqueta ie = new ImpresionEtiqueta();
            
            ie.setIdSurtimiento(idSurtimiento);
            ie.setFolio(folio);
            
            List<ImpresionEtiqueta> itLista = impresionEtiquetaService.obtenerImpresiones(ie);
            Integer numeroImpresiones = 1;
            boolean reimpresion = false;
            if (itLista != null) {
                numeroImpresiones = itLista.size() + 1;
                if (numeroImpresiones > 0) {
                    reimpresion = true;
                }
            }
            
            ie.setIdImpresionEtiqueta(Comunes.getUUID());
            ie.setContenido(contenido);
            ie.setImpresionExitosa(impresionExitosa);
            
            ie.setNumeroImpresiones(cantidadImpresiones);
            ie.setReimpresion(reimpresion);
            ie.setIdMotivoReimpresion(setIdMotivoReimpresion);
            ie.setNotas(notas);
            ie.setInsertFecha(new Date());
            ie.setInsertIdUsuario(idUsuario);
            impresionEtiquetaService.insertar(ie);
        } catch (Exception e) {
            LOGGER.trace("Error al registrar bitácora de impresión :: {} ", e.getMessage());
        }
    }
    
    public Integer getNumeroImpresiones() {
        return numeroImpresiones;
    }

    public void setNumeroImpresiones(Integer numeroImpresiones) {
        this.numeroImpresiones = numeroImpresiones;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }
            
}
