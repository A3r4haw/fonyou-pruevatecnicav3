package mx.mc.magedbean;

import java.io.File;
import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import mx.mc.enums.EstatusPrescripcion_Enum;
import mx.mc.enums.EstatusSurtimiento_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.CodigoInsumo;
import mx.mc.model.Estructura;
import mx.mc.model.Inventario;
import mx.mc.model.Medicamento;
import mx.mc.model.Surtimiento;
import mx.mc.model.SurtimientoEnviado;
import mx.mc.model.SurtimientoEnviado_Extend;
import mx.mc.model.SurtimientoInsumo;
import mx.mc.model.SurtimientoInsumo_Extend;
import mx.mc.model.Surtimiento_Extend;
import mx.mc.model.TipoJustificacion;
import mx.mc.model.TransaccionPermisos;
import mx.mc.model.Usuario;
import mx.mc.service.EstructuraService;
import mx.mc.service.InventarioService;
import mx.mc.service.MedicamentoService;
import mx.mc.service.ReportesService;
import mx.mc.service.SurtimientoInsumoService;
import mx.mc.service.SurtimientoService;
import mx.mc.service.TipoJustificacionService;
import mx.mc.util.Comunes;
import mx.mc.util.FechaUtil;
import mx.mc.util.Mensaje;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.FileOutputStream;
import com.lowagie.text.pdf.PdfCopyFields;
import com.lowagie.text.pdf.PdfReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import mx.mc.lazy.SurtPrescripcionExtLazy;
import mx.mc.enums.TipoConsulta_Enum;
import mx.mc.init.CustomWebSecurityConfigurerAdapter;
import mx.mc.model.CantidadRazonada;
import mx.mc.model.CantidadRazonadaExtended;
import mx.mc.model.ClaveProveedorBarras_Extend;
import mx.mc.model.InventarioExtended;
import mx.mc.model.MovimientoInventario;
import mx.mc.model.Paciente;
import mx.mc.model.PermisoUsuario;
import mx.mc.service.CantidadRazonadaService;
import mx.mc.service.ClaveProveedorBarrasService;
import mx.mc.service.PacienteService;
import mx.mc.service.TransaccionService;
import mx.mc.service.UsuarioService;
import mx.mc.util.CodigoBarras;
import org.primefaces.PrimeFaces;
import org.primefaces.event.TabChangeEvent;

/**
 *
 * @author hramirez
 */
@Controller
@Scope(value = "view")
public class SurtPrescripcionExtMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(SurtPrescripcionExtMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    private boolean editable;

    private boolean huboError;
    private boolean vales;
    private String cadenaBusqueda;
    private boolean elementoSeleccionado;
    private Pattern regexNumber;
    private String surSinAlmacen;    
    private String errRegistroIncorrecto;
    private String surIncorrecto;
    private String surClaveIncorrecta;
    private PermisoUsuario permiso;    
    private Usuario usuarioSelected;
    private Integer numHorPrevReceta;
    private Date fechaActual;
    private Integer numHorPostReceta;
    private boolean habilitaVales;
    private boolean valCantidadRazonada;
    private Integer maxNumDiasResurtible;
    private Integer cantResurt;
    private Integer numDiasResurtimiento;
    private boolean procederSurtimiento;
    private Integer numResurtimiento;    
    private String programada;
    private String surtida;
    private String cancelada;
    private List<Integer> listEstatusPrescripcion;
    private List<Integer> listEstatusSurtimiento;
    private Paciente paciente;
    private boolean habilitaValidacionVale;    
    private ClaveProveedorBarras_Extend skuSap;
    private List<ClaveProveedorBarras_Extend> skuSapList;

    @Autowired
    private transient EstructuraService estructuraService;
    private List<String> listServiciosQueSurte;

    @Autowired
    private transient SurtimientoService surtimientoService;
    @Autowired
    private transient SurtimientoInsumoService surtimientoInsumoService;
    @Autowired
    private transient PacienteService pacienteService;

    private Surtimiento_Extend surtimientoExtendedSelected;
    private List<Surtimiento_Extend> surtimientoExtendedList;

    private SurtimientoInsumo_Extend surtimientoInsumoExtendedSelected;
    private List<SurtimientoInsumo_Extend> surtimientoInsumoExtendedList;

    private List<TransaccionPermisos> permisosList;
    private String tipoPrescripcion;
    private List<String> tipoPrescripcionSelectedList;
    private SurtPrescripcionExtLazy surtPrescripcionExtLazy;
    private int cajasSurtidas;
    private int cantidadTotalSurtir;

    private String codigoBarras;
    private boolean eliminaCodigoBarras;

    private List<TipoJustificacion> justificacionList;
    @Autowired
    private transient TipoJustificacionService tipoJustificacionService;

    private Integer xcantidad;
    private Integer noDiasCaducidad;
    private boolean surtimientoMixto;
    private List<InventarioExtended> inventarioBloqueadoList;
    private CantidadRazonada cantidadRazonada;
    private CantidadRazonadaExtended cantidadRazonadaExt;

    @Autowired
    private transient CantidadRazonadaService cantidadRazonadaService;

    @Autowired
    private transient InventarioService inventarioService;

    @Autowired
    private transient MedicamentoService medicamentoService;

    @Autowired
    private transient UsuarioService usuarioService;

    @Autowired
    private transient TransaccionService transaccionService;

    @Autowired
    private transient ReportesService reportesService;

    @Autowired
    private transient ClaveProveedorBarrasService claveProveedorBarrasService;

    private String archivo;
    private String archivoVale;
    private String rutaPdfs;
    private String pathTmp;
    private String pathTmp2;
    private File dirTmp;
    private String urlf;
    private static PdfReader reader2;    
    private String userResponsable;
    private String passResponsable;
    private String nombreCompleto;
    private String msjAlert;
    private boolean msjMdlSurtimiento;
    private boolean authorization;    
    private boolean exist;
    private String idResponsable;
    private boolean authorizado;
    private String codigoBarrasAutorizte;    
    private Integer cantidadTotalSurtirByVale;
    private SurtimientoInsumo_Extend surtimientoInsumoItem;
    private Integer auxValorVale;
    private Integer xcantidadAutorizte;

    /**
     * Consulta los permisos del usuario y obtiene lospacientes registrados
     */
    @PostConstruct
    public void init() {
        LOGGER.debug("mx.mc.magedbean.SurtPrescripcionExtMB.init()");
        surSinAlmacen = "sur.sin.almacen";        
        errRegistroIncorrecto = "err.registro.incorrecto";
        surIncorrecto = "sur.incorrecto";
        surClaveIncorrecta = "sur.claveincorrecta";
        cajasSurtidas = 0;
        cantidadTotalSurtir = 0;
        auxValorVale = 0;        
        procederSurtimiento = true;
        cantResurt = 0;
        surtimientoInsumoItem = new SurtimientoInsumo_Extend();
        cantidadTotalSurtirByVale = 0;
        habilitaValidacionVale = false;
        regexNumber = Constantes.regexNumber;
        programada = Constantes.PROGRAMADA;        
        listEstatusPrescripcion = new ArrayList<>();
        listEstatusSurtimiento = new ArrayList<>();
        habilitaVales = false;
        surtida = Constantes.SURTIDA;
        cancelada = Constantes.CANCELADA;
        listEstatusPrescripcion.add(EstatusPrescripcion_Enum.SURTIDA_PARCIAL.getValue());
        listEstatusSurtimiento.add(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
        listEstatusPrescripcion.add(EstatusPrescripcion_Enum.PROGRAMADA.getValue());
        listEstatusSurtimiento.add(EstatusSurtimiento_Enum.SUTIDO_PARCIAL.getValue());        
        limpia();
        permiso = Comunes.obtenerPermisos(Transaccion_Enum.SURTPRESCEXT.getSufijo());
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");

        surtimientoMixto = sesion.isSurtimientoMixto();
        numHorPrevReceta = sesion.getHrsPrevReceta();
        numHorPostReceta = sesion.getHrsPostReceta();
        usuarioSelected = sesion.getUsuarioSelected();
        noDiasCaducidad = sesion.getNoDiasCaducidad();
        habilitaVales = sesion.isHabilitaVales();
        valCantidadRazonada = sesion.isCantidadRazonada();
        numDiasResurtimiento = sesion.getNoDiasResurtimiento();
        maxNumDiasResurtible = sesion.getActivaNumMaxDiasResurtible();        
        nombreCompleto = (usuarioSelected.getNombre() + " " + usuarioSelected.getApellidoPaterno() + " " + usuarioSelected.getApellidoMaterno()).toUpperCase();
        obtieneServiciosQuePuedeSurtir();
        obtenerSurtimientos();
        obtenerJustificacion();       
        dirTmp = new File(Comunes.getPath());
    }

    /**
     * Obtiene los Servicios que puede surtir el usuario, dependiendo de la
     * estructura jerarquica a la que esté asignada el usuario en sesion
     * Adicionalmente El area a la que el usuario este asignada, debe ser de
     * tipo almacen y que tenga una asignación de servicio hospitalario que
     * puede surtir
     */
    private void obtieneServiciosQuePuedeSurtir() {
        LOGGER.debug("mx.mc.magedbean.SurtPrescripcionExtMB.obtieneServiciosQuePuedeSurtir()");
        listServiciosQueSurte = new ArrayList<>();
        permiso.setPuedeVer(false);

        Estructura estru = null;
        try {
            estru = estructuraService.obtener(new Estructura(usuarioSelected.getIdEstructura()));

        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }

        if (estru == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surSinAlmacen), null);

        } else if (estru.getIdTipoAreaEstructura() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surSinAlmacen), null);

        } else {
            permiso.setPuedeVer(true);
            try {
                if (usuarioSelected.getIdEstructura() != null) {
                    List<Estructura> estructServicio = estructuraService.obtenerServicioQueSurtePorIdEstructura(estru.getIdEstructura());
                    for(Estructura servicio : estructServicio) {                        
                        listServiciosQueSurte.addAll(estructuraService.obtenerIdsEstructuraJerarquica(servicio.getIdEstructura(), true));
                        listServiciosQueSurte.add(servicio.getIdEstructura());
                    }
                }
            } catch (Exception ex) {
                LOGGER.error("Error al obtener Servicios que puede surtir el usuario: {}", ex.getMessage());
            }
        }

    }

    /**
     * Limpia las variables
     */
    private void limpia() {
        LOGGER.debug("mx.mc.magedbean.SurtPrescripcionExtMB.limpia()");        
        xcantidad = 1;
        elementoSeleccionado = false;
        huboError = false;
        eliminaCodigoBarras = false;
        cadenaBusqueda = null;
        codigoBarras = null;
        fechaActual = new java.util.Date();
        usuarioSelected = new Usuario();
        permisosList = new ArrayList<>();
        skuSapList = new ArrayList<>();        
        usuarioSelected.setPermisosList(permisosList);          
        surtimientoExtendedSelected = new Surtimiento_Extend();               
        skuSap = new ClaveProveedorBarras_Extend();   
        tipoPrescripcion = "";      
    }

    public void onTabChange(TabChangeEvent evnt) {
        String valorStatus = evnt.getTab().getId();
        listEstatusSurtimiento = new ArrayList<>();
        listEstatusPrescripcion = new ArrayList<>();
        if (valorStatus.equalsIgnoreCase(Constantes.PROGRAMADA)) {
            listEstatusSurtimiento.add(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
            listEstatusPrescripcion.add(EstatusPrescripcion_Enum.PROGRAMADA.getValue());
            listEstatusSurtimiento.add(EstatusSurtimiento_Enum.SUTIDO_PARCIAL.getValue());
            listEstatusPrescripcion.add(EstatusPrescripcion_Enum.SURTIDA_PARCIAL.getValue());

        } else if (valorStatus.equalsIgnoreCase(Constantes.SURTIDA)) {
            listEstatusSurtimiento.add(EstatusSurtimiento_Enum.SURTIDO.getValue());
            listEstatusPrescripcion = null;
        } else if (valorStatus.equalsIgnoreCase(Constantes.CANCELADA)) {
            listEstatusSurtimiento.add(EstatusSurtimiento_Enum.CANCELADO.getValue());
            listEstatusPrescripcion.add(EstatusPrescripcion_Enum.CANCELADA.getValue());
        }

        obtenerSurtimientos();
    }

    /**
     * Obtiene la lista de pacientes registrados
     */
    public void obtenerSurtimientos() {
        LOGGER.debug("mx.mc.magedbean.SurtPrescripcionExtMB.obtenerSurtimientos()");
        boolean status = Constantes.INACTIVO;
        surtimientoExtendedList = new ArrayList<>();

        if (!permiso.isPuedeVer()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.transaccion"), null);
            status = Constantes.ACTIVO;

        } else if (usuarioSelected == null || usuarioSelected.getIdEstructura() == null || usuarioSelected.getIdEstructura().isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surSinAlmacen), null);
            status = Constantes.ACTIVO;

        } else {
            try {
                if (cadenaBusqueda != null && cadenaBusqueda.trim().isEmpty()) {
                    cadenaBusqueda = null;
                }
                if (tipoPrescripcionSelectedList != null && tipoPrescripcionSelectedList.isEmpty()) {
                    tipoPrescripcionSelectedList = null;

                }

                if (listServiciosQueSurte.isEmpty()) {
                    this.listServiciosQueSurte = null;
                }
// regla: listar prescripciones con fecha igual o menor a la fecha actual, nunca prescripciones futuras
                Date fechaProgramada = new java.util.Date();

                // TODO:    agregar reglas de Negocio
                // 1.- El surtimiento solo funciona en Almacenes (CEDIME, CEDIMAT, Farmacia Externa) y subalmacenes (Gabinetes o sin sistema)
                // 2.- Solo surten los insumos que tienen activos
                // 3.- CEDIME y CEDIMAT solo surten a Consulta Interna

                surtPrescripcionExtLazy = new SurtPrescripcionExtLazy(surtimientoService, fechaProgramada, cadenaBusqueda, tipoPrescripcionSelectedList,
                        numHorPrevReceta, numHorPostReceta, listEstatusPrescripcion, listEstatusSurtimiento, listServiciosQueSurte);

                LOGGER.debug("Resultados: {}", surtPrescripcionExtLazy.getTotalReg());

                status = Constantes.ACTIVO;
            } catch (Exception ex) {
                LOGGER.error(RESOURCES.getString("prc.pac.lista"), ex);
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.pac.lista"), null);
            }
        }
        cadenaBusqueda = "";
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    /**
     * Obtener listado de Justificación
     */
    private void obtenerJustificacion() {
        LOGGER.debug("mx.mc.magedbean.SurtPrescripcionExtMB.obtenerJustificacion()");
        justificacionList = new ArrayList<>();
        try {
            boolean activo = Constantes.ACTIVO;
            List<Integer> listTipoJustificacion = null;
            justificacionList.addAll(tipoJustificacionService.obtenerActivosPorListId(activo, listTipoJustificacion));
        } catch (Exception ex) {
            LOGGER.error("Error en obtenerJustificacion: {}", ex.getMessage());
        }
    }

    public void onRowSelectSurtimiento(SelectEvent evnt) {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.onRowSelectSurtimiento()");
        surtimientoExtendedSelected = (Surtimiento_Extend) evnt.getObject();
        if (surtimientoExtendedSelected != null) {
            elementoSeleccionado = true;
        }
    }

    public void onRowUnselectSurtimiento() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.onRowUnselectSurtimiento()");
        surtimientoExtendedSelected = null;
        elementoSeleccionado = false;
    }

    /**
     * Muestra el Detalle de cada surtimiento de cada prescripción
     */
    public void verSurtimiento() {
        LOGGER.debug("mx.mc.magedbean.SurtPrescripcionExtMB.verSurtimiento()");
        numResurtimiento = 0;
        boolean status = Constantes.INACTIVO;
        boolean modal = Constantes.INACTIVO;
        if (!permiso.isPuedeVer()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.transaccion"), null);
        } else if (usuarioSelected == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surSinAlmacen), null);
        } else if (surtimientoExtendedSelected == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);
        } else if (surtimientoExtendedSelected.getIdPrescripcion() == null || surtimientoExtendedSelected.getIdPrescripcion().isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);
        } else if (surtimientoExtendedSelected.getIdSurtimiento() == null || surtimientoExtendedSelected.getIdSurtimiento().isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);
        } else if (surtimientoExtendedSelected.getIdEstatusSurtimiento() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);
        } else {
            try {
                if (surtimientoExtendedSelected.getResurtimiento() != null && surtimientoExtendedSelected.getResurtimiento() > 0) {
                    Surtimiento_Extend surtExt = surtimientoExtendedSelected;
                    procederSurtimiento = validarResurtimiento(surtExt);
                }
                Date fechaProgramada = new java.util.Date();
                String idSurtimiento = surtimientoExtendedSelected.getIdSurtimiento();
                String idPrescripcion = surtimientoExtendedSelected.getIdPrescripcion();
                List<Integer> listStatusSurtimient = new ArrayList<>();
                //Este valor es cuando querramos mostrarlos en tabs
                listStatusSurtimient.add(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                listStatusSurtimient.add(EstatusSurtimiento_Enum.SUTIDO_PARCIAL.getValue());
                List<Integer> listEstatusSurtimientoInsumo = new ArrayList<>();
                listEstatusSurtimientoInsumo.add(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                listEstatusSurtimientoInsumo.add(EstatusSurtimiento_Enum.SUTIDO_PARCIAL.getValue());
                String idEstructura = usuarioSelected.getIdEstructura();

                surtimientoInsumoExtendedList = new ArrayList<>();
                surtimientoInsumoExtendedList
                        .addAll(
                                surtimientoInsumoService
                                        .obtenerSurtimientosProgramadosExt(fechaProgramada, idSurtimiento, idPrescripcion, listEstatusSurtimientoInsumo, listStatusSurtimient, idEstructura, surtimientoMixto)
                        );
//                      TODO: buscar forma de optimizar sin ciclo
                surtimientoInsumoExtendedList.forEach(item -> {
                    double noCajas = 0;
                    Integer factorTransfomacion = Integer.valueOf(item.getFactorTransformacion());
                    double cantSolicitada = item.getCantidadSolicitada();
                    noCajas = cantSolicitada / factorTransfomacion;
                    if (cantSolicitada % factorTransfomacion > 0) {
                        noCajas += 1;
                    }
                    noCajas = Math.floor(noCajas);
                    item.setCajasSolicitadas(BigDecimal.valueOf(noCajas).movePointLeft(0));
                    item.setCantidadVale(0);
                    int totalVale = (item.getTotalVale() * factorTransfomacion);
                    item.setCantidadEnviada(item.getTotalEnviado() + totalVale);
                });
                if (!surtimientoInsumoExtendedList.isEmpty()) {
                    buscaMedicmanetosBloqueados();
                }

                status = Constantes.ACTIVO;
            } catch (Exception ex) {
                LOGGER.error(RESOURCES.getString(surIncorrecto), ex);
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surIncorrecto), null);
            }
        }

        PrimeFaces.current().ajax().addCallbackParam("modal", modal);
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    /**
     * Muestra el Detalle de cada surtimiento de cada prescripción
     */
    public void verSurtimientoEscaneado() {
        LOGGER.debug("mx.mc.magedbean.SurtPrescripcionExtMB.verSurtimiento()");

        boolean modal = Constantes.ACTIVO;

        try {
            List<Integer> listStatusPrescripcion = new ArrayList<>();
            listStatusPrescripcion.add(EstatusPrescripcion_Enum.PROGRAMADA.getValue());
            List<Integer> listStatusSurtimient = new ArrayList<>();
            listStatusSurtimient.add(EstatusSurtimiento_Enum.PROGRAMADO.getValue());

            Date fechaProgramada = new java.util.Date();
            surtPrescripcionExtLazy = new SurtPrescripcionExtLazy(surtimientoService, fechaProgramada, cadenaBusqueda, tipoPrescripcionSelectedList,
                    numHorPrevReceta, numHorPostReceta, listStatusPrescripcion, listStatusSurtimient, listServiciosQueSurte);

            surtimientoExtendedSelected = null;
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString(surIncorrecto), ex);
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surIncorrecto), null);
        }

        if (!permiso.isPuedeVer()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prescripcionExt.err.noregistro"), null);

        } else if (usuarioSelected == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surSinAlmacen), null);

        } else if (usuarioSelected.getIdEstructura() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surSinAlmacen), null);
        } else if (surtimientoExtendedSelected == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);
            modal = Constantes.INACTIVO;
        } else if (surtimientoExtendedSelected.getIdPrescripcion() == null || surtimientoExtendedSelected.getIdPrescripcion().isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);

        } else if (surtimientoExtendedSelected.getIdSurtimiento() == null || surtimientoExtendedSelected.getIdSurtimiento().isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);

        } else if (surtimientoExtendedSelected.getIdEstatusSurtimiento() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);

        } else {

            try {
                Date fechaProgramada = new java.util.Date();
                String idSurtimiento = surtimientoExtendedSelected.getIdSurtimiento();
                String idPrescripcion = surtimientoExtendedSelected.getIdPrescripcion();
                List<Integer> listStatusSurtimient = new ArrayList<>();
                listStatusSurtimient.add(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                List<Integer> listEstatusSurtimientoInsumo = new ArrayList<>();
                listEstatusSurtimientoInsumo.add(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                String idEstructura = usuarioSelected.getIdEstructura();

                surtimientoInsumoExtendedList = new ArrayList<>();
                surtimientoInsumoExtendedList
                        .addAll(
                                surtimientoInsumoService
                                        .obtenerSurtimientosProgramados(fechaProgramada, idSurtimiento, idPrescripcion, listEstatusSurtimientoInsumo, listStatusSurtimient, idEstructura, surtimientoMixto)
                        );
// TODO: optimizar cantidadcajas sin ciclo
//                for (SurtimientoInsumo_Extend item : surtimientoInsumoExtendedList) {
//                    Integer noCajas = 0;
//                    Integer factorTransfomacion = Integer.valueOf(item.getFactorTransformacion());
//                    Integer cantSolicitada = item.getCantidadSolicitada();
//                    noCajas = cantSolicitada / factorTransfomacion;
//                    if (cantSolicitada % factorTransfomacion > 0) {
//                        noCajas += 1;
//                    }
//                    item.setCajasSolicitadas(noCajas);
//                }
                if (!surtimientoInsumoExtendedList.isEmpty()) {
                    buscaMedicmanetosBloqueados();
                }
            } catch (Exception ex) {
                LOGGER.error(RESOURCES.getString(surIncorrecto), ex);
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surIncorrecto), null);
            }
        }

        PrimeFaces.current().ajax().addCallbackParam("modal", modal);
    }

    /**
     * Busca si algun medicamento de los generados en la prescripción tiene
     * algún Lote Bloqueado
     */
    private void buscaMedicmanetosBloqueados() {
        LOGGER.trace("mx.mc.magedbean.SurtPrescripcionExtMB.buscaMedicmanetosBloqueados()");
        try {
            List<String> idInsumoLis = new ArrayList<>();
            for (SurtimientoInsumo_Extend item : surtimientoInsumoExtendedList) {
                idInsumoLis.add(item.getIdInsumo());
            }
            inventarioBloqueadoList = inventarioService.obtenerListaInactivosByIdInsumos(idInsumoLis);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }

    }

    /**
     * Lee el codigo de barras de un medicamento y confirma la cantidad escaneda
     * para enviarse en el surtimento de prescripción
     *
     * @param ev
     */
    public void validaLecturaPorCodigo(SelectEvent ev) {
        skuSap = (ClaveProveedorBarras_Extend) ev.getObject();
        Medicamento medicamento;
        Inventario inv;
        LOGGER.debug("mx.mc.magedbean.SurtPrescripcionExtMB.validaLecturaPorCodigo()");
        boolean status = true;
        authorization = false;
        try {
            String idInventario = skuSap.getIdInventario();
            inv = inventarioService.obtener(new Inventario(idInventario));
            if (inv != null) {
                medicamento = medicamentoService.obtenerMedicamento(inv.getIdInsumo());
                if (medicamento != null && inv.getIdInsumo() != null) {
                    codigoBarras = CodigoBarras.generaCodigoDeBarras(medicamento.getClaveInstitucional(), inv.getLote(), inv.getFechaCaducidad(), inv.getCantidadXCaja());
                }
            }

            if (codigoBarras == null) {
                return;

            } else if (codigoBarras.trim().isEmpty()) {
                return;

            } else if (surtimientoExtendedSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surIncorrecto), null);

            } else if (surtimientoInsumoExtendedList == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surIncorrecto), null);

            } else if (surtimientoInsumoExtendedList.isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surIncorrecto), null);

            } else if (eliminaCodigoBarras) {
                status = eliminarLotePorCodigo();
            } else {
                status = agregarLotePorCodigo();
            }
            codigoBarras = "";
            xcantidad = 1;
            eliminaCodigoBarras = false;
        } catch (Exception exc) {
            LOGGER.error("Error en el metodo surtirInsumos :: {}", exc.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    private boolean validarCantRazonadaVales(SurtimientoInsumo_Extend items, int cantidadTotalSurtirByVale) throws Exception {
        boolean resp = Constantes.INACTIVO;
        habilitaValidacionVale = true;
        cantidadRazonada = cantidadRazonadaService.cantidadRazonadaInsumo(items.getClaveInstitucional());
        // Se verifica que el parametro de cantidad razonada este activo
        if (cantidadRazonada != null && !authorization && valCantidadRazonada) {
            Paciente pat = new Paciente();
            pat.setPacienteNumero(surtimientoExtendedSelected.getPacienteNumero());
            paciente = pacienteService.obtener(pat);
            int diasRestantes = 0;
            String ultimoSurtimient = "";

            cantidadRazonadaExt = cantidadRazonadaService.cantidadRazonadaInsumoPacienteExt(paciente.getIdPaciente(), items.getIdInsumo());
            if (cantidadRazonadaExt != null) {
                diasRestantes = cantidadRazonadaExt.getDiasRestantes();
                // En esta linea de código estamos indicando el nuevo formato que queremos para nuestra fecha.
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
                // Aqui usamos la instancia formatter para darle el formato a la fecha. Es importante ver que el resultado es un string.
                ultimoSurtimient = formatter.format(cantidadRazonadaExt.getUltimoSurtimiento());

            }

            //Consulta Externa
            if (surtimientoExtendedSelected.getTipoConsulta().equals(TipoConsulta_Enum.CONSULTA_EXTERNA.getValue())) {
                cajasSurtidas = cantidadTotalSurtirByVale; //totalMes   ya se pbtiene en cantidadTotalSurtirByVale.
                if (cajasSurtidas > cantidadRazonada.getCantidadPresentacionComercial()) {
                    msjMdlSurtimiento = false;
                    msjAlert = "El siguiente Medicamento debe ser Autorizado para surtir la receta<br/><b>"
                            + items.getClaveInstitucional() + "</b> El Medicamento solo puede surtirse cada  <b>" + cantidadRazonada.getPeriodoPresentacionComercial() + "</b> días, faltan  <b>" + diasRestantes + "</b>, ultimo surtimiento: <b>" + ultimoSurtimient + "</b>";

                    xcantidadAutorizte = items.getTotalVale();
                    codigoBarras = "";
                    return false;
                } else {
                    authorization = true;
                }
            }
        } else {
            authorization = true;
        }
        if (authorization) {
            resp = authorization;
        }

        return resp;

    }

    /**
     * Agrega un escaneo como subdetalle del resurtimiento
     *
     * @return
     */
    private boolean agregarLotePorCodigo() throws Exception {
        LOGGER.trace("mx.mc.magedbean.SurtPrescripcionExtMB.agregarLotePorCodigo()");
        boolean resp = Constantes.INACTIVO;
        boolean encontrado = Constantes.INACTIVO;
        habilitaValidacionVale = false;
        //Respaldamos el valor de codigoBarras, por si se requiere Autorizacioon
        codigoBarrasAutorizte = codigoBarras;

        CodigoInsumo ci = parsearCodigoDeBarrasConCantidad(codigoBarras);
        if (ci == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.parser"), null);

        } else if (FechaUtil.sumarRestarDiasFecha(new java.util.Date(), noDiasCaducidad).after(ci.getFecha())) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.caducidadvencida"), null);

        } else {

            List<SurtimientoEnviado_Extend> surtimientoEnviadoExtendList;
            SurtimientoEnviado_Extend surtimientoEnviadoExt;
            Integer cantEscaneada = 0;
            Integer cantEnviada;
            surtimientoExtendedSelected.setIdEstructuraAlmacen(usuarioSelected.getIdEstructura());
            for (SurtimientoInsumo_Extend surtInExt : surtimientoInsumoExtendedList) {
// regla: solo escanea medicamentos si la clave escaneada existe en el detalle solicitado
                if (surtInExt.getClaveInstitucional().contains(ci.getClave())) {
                    encontrado = Constantes.ACTIVO;
// regla: solo escanea medicamentos si no esta bloqueado a nivel catálogo
                    if (!surtInExt.isMedicamentoActivo()) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.clavebloqueada"), null);

// regla: factor multiplicador debe ser 1 o mayor
                    } else if (xcantidad != null && xcantidad < 1) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.cantidadincorrecta"), null);

                    } else {
                        boolean bloqueado = true;
                        if (inventarioBloqueadoList != null) {
                            List<Inventario> invList = inventarioBloqueadoList.stream()
                                    .filter(p -> p.getIdInsumo().equals(surtInExt.getIdInsumo()) && p.getLote().equals(ci.getClave()))
                                    .collect(Collectors.toList());
                            bloqueado = (invList == null || invList.isEmpty()) ? Constantes.INACTIVO : Constantes.ACTIVO;
                        }

                        xcantidad = (xcantidad != null) ? xcantidad : 1;
                        cantEscaneada = ci.getCantidad() * xcantidad;
                        cantEnviada = (surtInExt.getCantidadEnviada() != null) ? surtInExt.getCantidadEnviada() : 0;
                        cantEnviada = cantEnviada + cantEscaneada;
                        int cajasSolicitadas = surtInExt.getCajasSolicitadas().intValue();
                        int factorTransform = Integer.valueOf(surtInExt.getFactorTransformacion());
                        cajasSurtidas = (cantEnviada / factorTransform);

                        if (habilitaVales) {
                            cantidadTotalSurtir = cajasSurtidas + surtInExt.getCantidadVale();
                        }

// regla: solo escanea medicamentos si no esta bloqueado a nivel Lote en el inventario
                        if (bloqueado) {
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.lotebloqueado"), null);

// regla: no puede surtir mas medicamento que el solicitado
                        } else if (cantEnviada > surtInExt.getCantidadSolicitada() && Constantes.PARAMETRO_SURTIR_SOLO_SOLICITADO_MAX) {
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.exedido"), null);

                        } else if (cajasSurtidas > cajasSolicitadas) {
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.cajas.exedida"), null);

// regla nueva: Si la cantidadtotal es mayor a lo solicitado, referente a la suma con los vales y lo surtido.
                        } else if (cantidadTotalSurtir > cajasSolicitadas) {
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.cajas.exedida"), null);
                        } else {
                            surtimientoEnviadoExtendList = new ArrayList<>();
                            if (surtInExt.getSurtimientoEnviadoExtendList() != null) {
                                surtimientoEnviadoExtendList.addAll(surtInExt.getSurtimientoEnviadoExtendList());
                            }
                            Inventario inventarioPorSurtir = null;
                            String claveProveedor = null;
                            try {
                                inventarioPorSurtir = inventarioService
                                        .obtenerInventariosPorInsumoEstructuraLoteYCantidadXCaja(
                                                surtInExt.getIdInsumo(),
                                                 surtimientoExtendedSelected.getIdEstructuraAlmacen(),
                                                 ci.getLote(),
                                                 ci.getCantidad(),
                                                 claveProveedor);
                            } catch (Exception ex) {
                                LOGGER.error(RESOURCES.getString("sur.loteincorrecto"), ex);
                            }
                            if (inventarioPorSurtir == null) {
                                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.loteincorrecto"), null);

                            } else if (inventarioPorSurtir.getActivo() == 0) {
                                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.lotebloqueado"), null);
                                codigoBarras = "";
                            } else if (inventarioPorSurtir.getCantidadActual() < cantEscaneada) {
                                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.cantidadinsuficiente"), null);
                                codigoBarras = "";
                            } else {
// regla: cantidadRazonada 
                                resp = Constantes.INACTIVO;
                                cantidadRazonada = cantidadRazonadaService.cantidadRazonadaInsumo(ci.getClave());
                                // Se verifica que el parametro de cantidad razonada este activo
                                if (cantidadRazonada != null && !authorization && valCantidadRazonada) {
                                    Paciente patient = new Paciente();
                                    patient.setPacienteNumero(surtimientoExtendedSelected.getPacienteNumero());
                                    paciente = pacienteService.obtener(patient);
                                    int totalDia = 0;
                                    int totalMes = 0;
                                    int diasRestantes = 0;
                                    String ultimoSurtimiento = "";

                                    cantidadRazonadaExt = cantidadRazonadaService.cantidadRazonadaInsumoPacienteExt(paciente.getIdPaciente(), surtInExt.getIdInsumo());
                                    if (cantidadRazonadaExt != null && cantidadRazonadaExt.getTotalSurtMes() != null) {
                                        totalDia = cantidadRazonadaExt.getTotalSurtDia();
                                        totalMes = cantidadRazonadaExt.getTotalSurtMes();
                                        diasRestantes = cantidadRazonadaExt.getDiasRestantes();
                                        // En esta linea de código estamos indicando el nuevo formato que queremos para nuestra fecha.
                                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
                                        // Aqui usamos la instancia formatter para darle el formato a la fecha. Es importante ver que el resultado es un string.
                                        ultimoSurtimiento = formatter.format(cantidadRazonadaExt.getUltimoSurtimiento());
                                    }

                                    //Consulta Interna
                                    if (surtimientoExtendedSelected.getTipoConsulta().equals(TipoConsulta_Enum.CONSULTA_INTERNA.getValue())) {
                                        cantEnviada = cantEnviada + totalDia;
                                        if (cantEnviada > cantidadRazonada.getCantidadDosisUnitaria()) {
                                            exist = false;
                                            msjMdlSurtimiento = false;
                                            msjAlert = "El Medicamento siguiente  debe ser Autorizado para surtir la receta <br/><b>"
                                                    + ci.getClave() + "</b> La cantidad del Medicamento debe ser menor o igual a <b>" + cantidadRazonada.getCantidadDosisUnitaria() + "</b>, se solicita <b>" + cantEnviada + "</b>";

                                            xcantidadAutorizte = cantEscaneada;
                                            codigoBarras = "";
                                            return false;
                                        } else {
                                            authorization = true;
                                        }
                                        //Consulta Externa
                                    } else if (surtimientoExtendedSelected.getTipoConsulta().equals(TipoConsulta_Enum.CONSULTA_EXTERNA.getValue())) {
                                        cajasSurtidas = (cantEnviada / factorTransform) + (totalMes / factorTransform);
                                        if (cajasSurtidas > cantidadRazonada.getCantidadPresentacionComercial()) {
                                            exist = false;
                                            msjMdlSurtimiento = false;
                                            msjAlert = "El Medicamento siguiente  debe ser Autorizado para surtir la receta <br/><b> "
                                                    + ci.getClave() + "</b> El Medicamento solo puede surtirse cada  <b>" + cantidadRazonada.getPeriodoPresentacionComercial() + "</b> días, faltan  <b>" + diasRestantes + "</b>, ultimo surtimiento: <b>" + ultimoSurtimiento + "</b>";

                                            xcantidadAutorizte = (cantEscaneada / factorTransform);
                                            codigoBarras = "";
                                            return false;
                                        } else {
                                            authorization = true;
                                        }
                                    }

                                } else {
                                    authorization = true;
                                }

                                if (authorization) {

                                    if (surtimientoEnviadoExtendList.isEmpty()) {
                                        surtimientoEnviadoExt = new SurtimientoEnviado_Extend();
                                        surtimientoEnviadoExt.setIdSurtimientoEnviado(Comunes.getUUID());
                                        surtimientoEnviadoExt.setIdSurtimientoInsumo(surtInExt.getIdSurtimientoInsumo());
                                        surtimientoEnviadoExt.setLote(ci.getLote());
                                        surtimientoEnviadoExt.setCaducidad(ci.getFecha());
                                        surtimientoEnviadoExt.setCantidadEnviado(cantEscaneada);
                                        surtimientoEnviadoExt.setIdInventarioSurtido(inventarioPorSurtir.getIdInventario());
                                        surtimientoEnviadoExt.setFactorTransformacion(inventarioPorSurtir.getCantidadXCaja());
                                        surtimientoEnviadoExt.setClaveProveedor(inventarioPorSurtir.getClaveProveedor());
                                        surtimientoEnviadoExtendList.add(surtimientoEnviadoExt);

                                    } else {
                                        boolean bandera = false;
                                        for (SurtimientoEnviado_Extend surtEnv : surtimientoEnviadoExtendList) {
                                            bandera = false;
                                            // regla: si se pistolea mas de un medicmento y con el mismo lote se agrupan por lotes sumarizando las cantidades
                                            if (surtEnv.getLote().equals(ci.getLote()) && surtEnv.getCaducidad().equals(ci.getFecha())) {
                                                surtEnv.setCantidadEnviado(surtEnv.getCantidadEnviado() + cantEscaneada);
                                                break;

                                            } else {
                                                bandera = true;
                                            }
                                        }
                                        if (bandera) {
                                            // regla: si es el único Lote pistoleado solo muestra una linea en subdetalle
                                           SurtimientoEnviado_Extend surtEnviadoExt = new SurtimientoEnviado_Extend();
                                            surtEnviadoExt.setIdSurtimientoEnviado(Comunes.getUUID());
                                            surtEnviadoExt.setIdSurtimientoInsumo(surtInExt.getIdSurtimientoInsumo());
                                            surtEnviadoExt.setLote(ci.getLote());
                                            surtEnviadoExt.setCaducidad(ci.getFecha());
                                            surtEnviadoExt.setCantidadEnviado(cantEscaneada);
                                            surtEnviadoExt.setIdInventarioSurtido(inventarioPorSurtir.getIdInventario());
                                            surtEnviadoExt.setFactorTransformacion(inventarioPorSurtir.getCantidadXCaja());
                                            surtEnviadoExt.setClaveProveedor(inventarioPorSurtir.getClaveProveedor());
                                            surtimientoEnviadoExtendList.add(surtEnviadoExt);
                                        }
                                    }
                                    if (valCantidadRazonada && authorizado) {
                                        surtInExt.setIdUsuarioAutCanRazn(idResponsable);
                                    }

                                    surtInExt.setCantidadEnviada(cantEnviada);
                                    surtInExt.setSurtimientoEnviadoExtendList(surtimientoEnviadoExtendList);
                                    resp = Constantes.ACTIVO;
                                    xcantidad = 1;
                                }
                            }
                        }
                    }
                }
            }
        }
        if (!encontrado) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surClaveIncorrecta), null);
        }
        return resp;
    }

    /**
     * elimina un escaneo como subdetalle del resurtimiento
     *
     * @return
     */
    private boolean eliminarLotePorCodigo() {
        LOGGER.trace("mx.mc.magedbean.SurtPrescripcionExtMB.eliminarLotePorCodigo()");
        boolean res = Constantes.INACTIVO;
        boolean encontrado = Constantes.INACTIVO;

        CodigoInsumo ci = parsearCodigoDeBarrasConCantidad(codigoBarras);
        if (ci == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.parser"), null);

        } else if (FechaUtil.sumarRestarDiasFecha(new java.util.Date(), noDiasCaducidad).after(ci.getFecha())) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.caducidadvencida"), null);

        } else {

            List<SurtimientoEnviado_Extend> surtimientoEnviadoExtendList;
            Integer cantidadEscaneada = 0;
            Integer cantidadEnviada = 0;
            for (SurtimientoInsumo_Extend item : surtimientoInsumoExtendedList) {

// regla: puede escanear medicamentos mientras la clave escaneada exista en el detalle solicitado
                if (item.getClaveInstitucional().contains(ci.getClave())) {
                    encontrado = Constantes.ACTIVO;
// regla: factor multiplicador debe ser 1 o mayor
                    if (xcantidad != null && xcantidad < 1) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.cantidadincorrecta"), null);

                    } else {
                        cantidadEscaneada = ci.getCantidad() * xcantidad;
                        cantidadEnviada = (item.getCantidadEnviada() != null) ? item.getCantidadEnviada() : 0;
                        cantidadEnviada = cantidadEnviada - cantidadEscaneada;
                        cantidadEnviada = (cantidadEnviada < 0) ? 0 : cantidadEnviada;

                        surtimientoEnviadoExtendList = new ArrayList<>();
                        if (item.getSurtimientoEnviadoExtendList() == null) {
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.lotesinescanear"), null);

                        } else {
                            surtimientoEnviadoExtendList.addAll(item.getSurtimientoEnviadoExtendList());

// regla: el lote aliminar del surtimiento ya debió ser escaneado para eliminaro
                            if (surtimientoEnviadoExtendList.isEmpty()) {
                                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.lotesinescanear"), null);

                            } else {
// regla: si el lote escaneado ya ha sido agregado se descuentan las cantidades
                                Integer cantidResultante = 0;
                                for (SurtimientoEnviado_Extend lote : surtimientoEnviadoExtendList) {
                                    if (lote.getLote().equals(ci.getLote()) && lote.getCaducidad().equals(ci.getFecha())) {
                                        cantidResultante = lote.getCantidadEnviado() - cantidadEscaneada;
                                        if (cantidResultante < 1) {
                                            surtimientoEnviadoExtendList.remove(lote);
                                        } else {
                                            lote.setCantidadEnviado(cantidResultante);
                                        }
                                        break;
                                    }
                                }
                                item.setCantidadEnviada(cantidadEnviada);
                                item.setSurtimientoEnviadoExtendList(surtimientoEnviadoExtendList);
                                res = Constantes.ACTIVO;
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (!encontrado) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surClaveIncorrecta), null);
        }
        return res;
    }

    public Surtimiento_Extend obtenerFechaAnterior(Surtimiento_Extend surtExt) throws Exception {
        Date date;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(surtExt.getFechaProgramada());
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - numDiasResurtimiento);
        date = calendar.getTime();

        surtExt = surtimientoService.obtenerByFechaAndPrescripcion(surtExt.getIdPrescripcion(), date);

        return surtExt;
    }

    public boolean validarResurtimiento(Surtimiento_Extend surtExtend) throws Exception {
        boolean respuesta = true;
        do {
            surtExtend = obtenerFechaAnterior(surtExtend);
            if (surtExtend == null) {
                if (numResurtimiento == 0) {
                    numResurtimiento = 1;
                } else {
                    numResurtimiento += 1;
                }
            } else {
                if (numResurtimiento == 0) {
                    numResurtimiento++;
                } else {
                    numResurtimiento += 1;
                }
            }
        } while (surtExtend != null);
        cantResurt = (maxNumDiasResurtible / numDiasResurtimiento);
        if (numResurtimiento > cantResurt) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No puedes surtir mas surtimientos  que los resurtimientos", null);
            respuesta = false;
        }
        return respuesta;
    }

    public void validaSurtimiento() {
        LOGGER.trace("mx.mc.magedbean.SurtPrescripcionExtMB.validaSurtimiento()");
        limpiaVariables();
        boolean status = Constantes.INACTIVO;
        try {
            int estatusSurtimiento = 0;

            if (surtimientoExtendedSelected.getResurtimiento() != null && surtimientoExtendedSelected.getResurtimiento() > 0) {
                Surtimiento_Extend surtExt = surtimientoExtendedSelected;
                procederSurtimiento = validarResurtimiento(surtExt);
            }

            if (procederSurtimiento) {
                for (SurtimientoInsumo_Extend itemSurtInsuExt : surtimientoInsumoExtendedList) {
                    if (itemSurtInsuExt.getCantidadEnviada() == null) {
                        itemSurtInsuExt.setCantidadEnviada(0);
                    }
                    int factorTran = Integer.valueOf(itemSurtInsuExt.getFactorTransformacion());
                    int cajSurtidas = (itemSurtInsuExt.getCantidadEnviada() / factorTran);;
                    int cantTotalSurtir = cajSurtidas + itemSurtInsuExt.getCantidadVale();

                    if (itemSurtInsuExt.getCajasSolicitadas().intValue() == cantTotalSurtir) {
                        estatusSurtimiento = EstatusSurtimiento_Enum.SURTIDO.getValue();
                    } else if (itemSurtInsuExt.getCajasSolicitadas().intValue() > cantTotalSurtir) {
                        estatusSurtimiento = EstatusSurtimiento_Enum.SUTIDO_PARCIAL.getValue();
                    }
                }

                if (surtimientoInsumoExtendedList == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surClaveIncorrecta), null);

                } else if (surtimientoInsumoExtendedList.isEmpty()) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surClaveIncorrecta), null);

                } else {

                    Integer cantidadEnviada = 0;

                    surtimientoExtendedSelected.setIdEstatusSurtimiento(estatusSurtimiento);
                    surtimientoExtendedSelected.setIdEstructuraAlmacen(usuarioSelected.getIdEstructura());
                    Inventario inventarioActual;
                    boolean desabasto = false;
                    StringBuilder sb = new StringBuilder();

                    List<SurtimientoInsumo> surtimientoInsumoList = new ArrayList<>();
                    SurtimientoEnviado suEnv;
                    List<SurtimientoEnviado> surtimientoEnviadoList = new ArrayList<>();
                    List<SurtimientoInsumo> surtimientoInsumoListVale = new ArrayList<>();
                    List<Inventario> listIinventario = new ArrayList<>();
                    List<MovimientoInventario> listaMovInventario = new ArrayList<>();
                    int vale = 0;

                    for (SurtimientoInsumo_Extend surtimientoInsumo : surtimientoInsumoExtendedList) {
                        cantidadEnviada = 0;

                        int cantidTotalVale = Integer.valueOf(surtimientoInsumo.getFactorTransformacion()) * surtimientoInsumo.getTotalVale();
                        surtimientoInsumo.setCantidadEnviada((surtimientoInsumo.getCantidadEnviada() - surtimientoInsumo.getTotalEnviado()) - cantidTotalVale);
                        int valorTotalEnviado = surtimientoInsumo.getCantidadEnviada() + surtimientoInsumo.getTotalEnviado();
                        surtimientoInsumo.setTotalEnviado(valorTotalEnviado);

                        int valorTotalVale = surtimientoInsumo.getCantidadVale() + surtimientoInsumo.getTotalVale();
                        surtimientoInsumo.setTotalVale(valorTotalVale);

                        surtimientoInsumoList.add((SurtimientoInsumo) surtimientoInsumo);

                        if (surtimientoInsumo.getCantidadVale() != null && surtimientoInsumo.getCantidadVale() > 0) {
                            surtimientoInsumoListVale.add(surtimientoInsumo);
                            vale++;
                        }

                        if (surtimientoInsumo.getSurtimientoEnviadoExtendList() != null) {
// regla: si el surtimiento enviado tiene al menos 1 medicamento, se puede enviar la orden
                            if (!surtimientoInsumo.getSurtimientoEnviadoExtendList().isEmpty()) {

                                for (SurtimientoEnviado_Extend surtEnviado : surtimientoInsumo.getSurtimientoEnviadoExtendList()) {

                                    if (surtEnviado.getIdSurtimientoInsumo().equalsIgnoreCase(surtimientoInsumo.getIdSurtimientoInsumo())) {
                                        cantidadEnviada = cantidadEnviada + surtEnviado.getCantidadEnviado();

// TODO: valida cantidad surtida contra existencias
                                        String claveProveedor = null;
                                        inventarioActual = inventarioService.obtenerInventariosPorInsumoEstructuraLoteYCantidadXCaja(
                                                surtimientoInsumo.getIdInsumo(),
                                                usuarioSelected.getIdEstructura(),
                                                surtEnviado.getLote(),
                                                surtEnviado.getFactorTransformacion(),
                                                claveProveedor);

                                        if (inventarioActual == null || inventarioActual.getCantidadActual() == null
                                                || inventarioActual.getCantidadActual() < surtEnviado.getCantidadEnviado()) {
                                            if (sb.length() > 0) {
                                                sb.append("\n");
                                            }
                                            sb.append(" * Clave: ");
                                            sb.append(surtimientoInsumo.getClaveInstitucional());
                                            sb.append("  Requerida: ");
                                            sb.append(surtEnviado.getCantidadEnviado());
                                            sb.append("  Existente: ");
                                            sb.append((inventarioActual != null) ? inventarioActual.getCantidadActual() : 0);
                                            desabasto = true;
                                            this.vales = false;
                                            break;
                                        }

                                        Inventario inventario = new Inventario();
                                        inventario.setCantidadActual(surtEnviado.getCantidadEnviado());
                                        inventario.setIdInventario(inventarioActual.getIdInventario());
                                        listIinventario.add(inventario);

                                        MovimientoInventario movimientoInv = new MovimientoInventario();
                                        movimientoInv.setIdMovimientoInventario(Comunes.getUUID());
                                        movimientoInv.setIdTipoMotivo(20);
                                        movimientoInv.setFecha(new Date());
                                        movimientoInv.setIdUsuarioMovimiento(usuarioSelected.getIdUsuario());
                                        movimientoInv.setIdEstrutcuraOrigen(usuarioSelected.getIdEstructura());
                                        movimientoInv.setIdEstrutcuraDestino(surtimientoExtendedSelected.getIdEstructura());
                                        movimientoInv.setIdInventario(surtEnviado.getIdInventarioSurtido());
                                        movimientoInv.setCantidad(surtEnviado.getCantidadEnviado());
                                        movimientoInv.setFolioDocumento(surtimientoExtendedSelected.getFolioPrescripcion());
                                        listaMovInventario.add(movimientoInv);

                                    }

// todo: setear el id del Inventario
                                    surtEnviado.setIdEstatusSurtimiento(estatusSurtimiento);
                                    surtEnviado.setInsertFecha(new java.util.Date());
                                    surtEnviado.setInsertIdUsuario(usuarioSelected.getIdUsuario());

                                    surtimientoEnviadoList.add((SurtimientoEnviado) surtEnviado);
                                }

                                surtimientoInsumo.setIdEstatusSurtimiento(estatusSurtimiento);
                                surtimientoInsumo.setCantidadEnviada(cantidadEnviada);
                                surtimientoInsumo.setIdUsuarioEnviada(usuarioSelected.getIdUsuario());
                                surtimientoInsumo.setFechaEnviada(new java.util.Date());

                                surtimientoExtendedSelected.setIdEstatusSurtimiento(estatusSurtimiento);

                            } else {

                                cantidadEnviada = 0;
                                suEnv = new SurtimientoEnviado();
                                suEnv.setCantidadEnviado(cantidadEnviada);
                                suEnv.setIdEstatusSurtimiento(estatusSurtimiento);
                                suEnv.setIdSurtimientoEnviado(Comunes.getUUID());
                                suEnv.setIdSurtimientoInsumo(surtimientoInsumo.getIdSurtimientoInsumo());
                                suEnv.setInsertFecha(new java.util.Date());
                                suEnv.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                                suEnv.setIdInsumo(surtimientoInsumo.getIdInsumo());

// OJOOOOOOOOO
                                Inventario inventarioFaltante = inventarioService.obtenerIdiventarioPorInsumoEstructura(
                                        surtimientoInsumo.getIdInsumo(),
                                        usuarioSelected.getIdEstructura());
                                suEnv.setIdInventarioSurtido(inventarioFaltante.getIdInventario());
                                suEnv.setLote(inventarioFaltante.getLote());
                                suEnv.setFactorTransformacion(inventarioFaltante.getCantidadXCaja());

                                surtimientoEnviadoList.add(suEnv);

                            }

                        } else {
                            if (Objects.equals(surtimientoInsumo.getIdEstatusSurtimiento(), estatusSurtimiento)) {
                                surtimientoExtendedSelected.setIdEstatusSurtimiento(estatusSurtimiento);
                            }
                        }
                    }
                    if (desabasto) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Existencias Insuficientes: \n" + sb.toString(), null);

                    } else {

                        boolean res;
                        boolean res2;

                        surtimientoExtendedSelected.setIdEstructuraAlmacen(usuarioSelected.getIdEstructura());
                        surtimientoExtendedSelected.setUpdateFecha(new java.util.Date());
                        surtimientoExtendedSelected.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
                        surtimientoExtendedSelected.setIdEstatusSurtimiento(estatusSurtimiento);

                        res = surtimientoService.registrarSurtimientoPrescExt(
                                surtimientoExtendedSelected,
                                surtimientoInsumoList,
                                surtimientoEnviadoList,
                                listIinventario,
                                listaMovInventario);

                        pathTmp = "";
                        pathTmp2 = "";
                        if (res) {
                            imprimirTcket(surtimientoExtendedSelected, usuarioSelected.getNombreUsuario());
                            if (vale > 0) {
                                res2 = surtimientoService
                                        .registrarSurtimientoVales(surtimientoExtendedSelected, surtimientoInsumoListVale, surtimientoEnviadoList);

                                vales = Constantes.ACTIVO;
                                if (res2) {
                                    imprimirTcketVale(surtimientoExtendedSelected, usuarioSelected.getNombreUsuario());
                                }
                            }

                            if (archivo.length() > 0) {
                                String ticket = (System.getProperty("os.name").toLowerCase().contains("windows")) ? "\\" : "/";
                                rutaPdfs = dirTmp.getPath() + ticket + surtimientoExtendedSelected.getFolio() + ".pdf";
                                PdfReader reader1 = new PdfReader(pathTmp);
                                if (pathTmp2.length() > 0) {
                                    reader2 = new PdfReader(pathTmp2);
                                }
                                PdfCopyFields copia = new PdfCopyFields(new FileOutputStream(rutaPdfs));
                                copia.addDocument(reader1);

                                if (pathTmp2.length() > 0) {
                                    copia.addDocument(reader2);
                                }

                                String jsText1 = "this.print({bUI: true, bSilent: true, bShrinkToFit: true});";

                                copia.addJavaScript(jsText1);

                                copia.close();
                                archivo = urlf + "/resources/tmp/" + surtimientoExtendedSelected.getFolio() + ".pdf";
                            }
                            status = true;
                        }
                        init();

                        if (status) {
                            Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("sur.exitoso"), null);

// TODO: remover elemento de la lista de forma eficiente
                            surtimientoExtendedList.stream().filter(prdct -> prdct.getFolio().equals(surtimientoExtendedSelected.getFolio())).forEach(cnsmr -> 
                                surtimientoExtendedList.remove(cnsmr)
                            );

                        }
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error en el metodo surtirInsumos :: {}", ex.getMessage());
        }
        cadenaBusqueda = "";
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public void imprimirPorId(String idSurtimiento) throws Exception {
        LOGGER.debug("mx.mc.magedbean.SurtPrescripcionExtMB.imprimirPorId()");
        surtimientoExtendedSelected = (Surtimiento_Extend) surtimientoService.obtener(new Surtimiento(idSurtimiento));
        imprimir();
    }
        
    private void limpiaVariables() {
        archivo = "";
        pathTmp = "";
        pathTmp2 = "";
        rutaPdfs = "";
    }

    public void imprimir() throws Exception {
        LOGGER.debug("mx.mc.magedbean.SurtPrescripcionExtMB.imprimir()");
        boolean status = Constantes.INACTIVO;
        try {
            String pathTmpo = dirTmp.getPath() + "\\surtimiento.pdf";
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

            boolean resp = reportesService.imprimeSurtimiento(surtimientoExtendedSelected, pathTmpo, url);
            if (resp) {
                status = Constantes.ACTIVO;
                archivo = url + "/resources/tmp/surtimiento.pdf";
            }
        } catch (Exception exc) {
            LOGGER.error("Error al generar la imprimirPorId: {}", exc.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public void actualizaCantidadVale(SurtimientoInsumo_Extend item) {
        surtimientoInsumoExtendedList.stream().filter(insumo -> (insumo.getIdInsumo().equals(item.getIdInsumo()))).forEachOrdered(insumo -> {
            boolean valStatus = true;
            authorization = false;
            int cantSurt = item.getCantidadEnviada() / Integer.valueOf(item.getFactorTransformacion());//Aqui ya va incluido el valor de totalVale
            cantidadTotalSurtirByVale = item.getCantidadVale() + cantSurt;
            if (item.getCajasSolicitadas().intValue() < cantidadTotalSurtirByVale) {
                item.setCantidadVale(0);
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.cajasvales.exedida"), null);
            } else {
                try {
                    surtimientoInsumoItem = item;
                    auxValorVale = surtimientoInsumoItem.getCantidadVale();
                    surtimientoInsumoItem.setCantidadVale(0);
                    valStatus = validarCantRazonadaVales(item, cantidadTotalSurtirByVale);
                    if (valStatus) {
                        surtimientoInsumoItem.setCantidadVale(auxValorVale);
                    }
                } catch (Exception ex) {
                    LOGGER.error("Error al actualizar cantidad Vales: {}", ex.getMessage());
                }
            }
            PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, valStatus);
        });
    }

    public void imprimirTcket(Surtimiento_Extend surtimientoExtendedSelected, String nombreUsuario) throws Exception {
        LOGGER.debug("mx.mc.magedbean.SurtPrescripcionExtMB.imprimirTcket()");
        boolean status = Constantes.INACTIVO;
        try {
            //Se cambio las dos // por la diagonal 
            String rutaTicket = (System.getProperty("os.name").toLowerCase().contains("windows")) ? "\\ticket" : "/ticket";
            pathTmp = dirTmp.getPath() + rutaTicket + surtimientoExtendedSelected.getFolio() + ".pdf";
            FacesContext cntxt = FacesContext.getCurrentInstance();
            ExternalContext ext = cntxt.getExternalContext();
            URI uri = new URI(ext.getRequestScheme(),
                     null,
                     ext.getRequestServerName(),
                     ext.getRequestServerPort(),
                     ext.getRequestContextPath(),
                     null,
                     null);
            String ligaUrl = uri.toASCIIString();
            urlf = ligaUrl;
            boolean res = reportesService.imprimeSurtimientoPrescExt(
                    surtimientoExtendedSelected,
                    nombreUsuario, pathTmp, ligaUrl);

            if (res) {
                status = Constantes.ACTIVO;
                archivo = ligaUrl + "/resources/tmp/ticket" + surtimientoExtendedSelected.getFolio() + ".pdf";
            }
        } catch (Exception e) {
            LOGGER.error("Error al generar la imprimirTcket: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);

    }

    public void imprimirTcketVale(Surtimiento_Extend surtimientoExtendedSelected, String nombreUsuario) throws Exception {
        LOGGER.debug("mx.mc.magedbean.SurtPrescripcionExtMB.imprimirTcketVale()");
        boolean status = Constantes.INACTIVO;
        try {
            //Se cambio las dos // por la diagonal 
            String rutaTicket = (System.getProperty("os.name").toLowerCase().contains("windows")) ? "\\vale" : "/vale";
            pathTmp2 = dirTmp.getPath() + rutaTicket + surtimientoExtendedSelected.getFolio() + ".pdf";
            FacesContext cntxt = FacesContext.getCurrentInstance();
            ExternalContext ext = cntxt.getExternalContext();
            URI uriVal = new URI(ext.getRequestScheme(),
                     null,
                     ext.getRequestServerName(),
                     ext.getRequestServerPort(),
                     ext.getRequestContextPath(),
                     null,
                     null);
            String url = uriVal.toASCIIString();
            urlf = url;
            reportesService.imprimeSurtimientoVales(
                    surtimientoExtendedSelected,
                    nombreUsuario, pathTmp2, url);
        } catch (Exception e) {
            LOGGER.error("Error al generar la imprimirTcketVale: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam("vales", status);
    }

    public CodigoInsumo parsearCodigoDeBarrasConCantidad(String codigo) {
        LOGGER.trace("mx.mc.magedbean.SurtPrescripcionExtMB.parsearCodigoDeBarrasConCantidad()");
        try {
            CodigoInsumo cin = CodigoBarras.parsearCodigoDeBarras(codigo);
            if (cin != null) {
                return cin;
            } else {
                ClaveProveedorBarras_Extend claveProveedorBarras = claveProveedorBarrasService.obtenerClave(codigo);
                if (claveProveedorBarras != null) {
                    cin = new CodigoInsumo();
                    cin.setClave(claveProveedorBarras.getClaveInstitucional());
                    cin.setLote(claveProveedorBarras.getClaveProveedor());
                    cin.setCantidad(claveProveedorBarras.getCantidadXCaja());
                    cin.setFecha(Mensaje.generaCaducidadSKU(claveProveedorBarras.getCodigoBarras()));
                    return cin;
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prescripcionExt.err.valorInvalido"), null);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al Parsear Código de Barras: {}", e.getMessage());
        }
        return null;
    }


    public List<ClaveProveedorBarras_Extend> autoComplete(String cadena) {
        LOGGER.trace("mx.mc.magedbean.SurtPrescripcionExtMB.autoComplete()");
        skuSapList = new ArrayList<>();
        try {
            String idEstructura = usuarioSelected.getIdEstructura();
            CodigoInsumo codIns = CodigoBarras.parsearCodigoDeBarras(cadena);
            if (codIns != null) {
                String claveInstitucional = codIns.getClave();
                String lote = codIns.getLote();
                Date fechaCaducidad = codIns.getFecha();
                Integer cantidadXcaja = null;
                if (codIns.getCantidad() != null) {
                    cantidadXcaja = codIns.getCantidad();
                }
                skuSapList = claveProveedorBarrasService
                        .obtenerListaClavesCodigoQrExt(claveInstitucional, lote, fechaCaducidad, idEstructura, cantidadXcaja);
            } else {
                skuSapList = claveProveedorBarrasService.obtenerListaClavesCodigoBarrasExt(cadena, idEstructura, usuarioSelected.getIdUsuario());
            }
            medicamentoRecetado();
        } catch (Exception ex) {
            LOGGER.error("Error al obtener obtenerListaClavesSku : {}", ex.getMessage());
        }
        return skuSapList;
    }

    /**
     * Valida si un medicamento escaneado esta dentro de la receta
     */
    private void medicamentoRecetado() {
        LOGGER.trace("mx.mc.magedbean.SurtPrescripcionExtMB.medicamentoRecetado()");
        List<ClaveProveedorBarras_Extend> cpbTmp = new ArrayList<>();
        cpbTmp.addAll(skuSapList);
        skuSapList = new ArrayList<>();
        if (!cpbTmp.isEmpty()) {
            try {
                for (SurtimientoInsumo_Extend item : surtimientoInsumoExtendedList) {
                    for (ClaveProveedorBarras_Extend item2 : cpbTmp) {
                        if (item.getClaveInstitucional().equals(item2.getClaveInstitucional())) {
                            skuSapList.add(item2);
                        }
                    }
                }
            } catch (Exception exc) {
                LOGGER.error("Error al obtener obtenerListaClavesSku : {}", exc.getMessage());
            }
        }
    }

    /**
     *
     * @param ev
     */
    public void handleSelect(SelectEvent ev) {
        LOGGER.info("mx.mc.magedbean.SurtPrescripcionExtMB.handleSelect()");
        Inventario invent = null;
        try {
            skuSap = (ClaveProveedorBarras_Extend) ev.getObject();
            String idInventario = skuSap.getIdInventario();

            invent = inventarioService.obtener(new Inventario(idInventario));

        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }

        if (invent != null && invent.getIdInsumo() != null) {
            try {
                Medicamento m = medicamentoService.obtenerMedicamento(invent.getIdInsumo());
                codigoBarras = CodigoBarras.generaCodigoDeBarras(m.getClaveInstitucional(), invent.getLote(), invent.getFechaCaducidad(), invent.getCantidadXCaja());
                skuSap = new ClaveProveedorBarras_Extend();
            } catch (Exception ex) {
                LOGGER.error(ex.getMessage());
            }
        }
    }

    public void authorization() {
        boolean estatus = false;
        try {

            Usuario usuario = new Usuario();
            usuario.setNombreUsuario(userResponsable);
            usuario.setActivo(Constantes.ACTIVO);
            usuario.setUsuarioBloqueado(Constantes.INACTIVO);
            msjMdlSurtimiento = false;

            Usuario user = usuarioService.obtener(usuario);
            if (user != null) {
                if (CustomWebSecurityConfigurerAdapter.customPasswordEncoder().matches(passResponsable, user.getClaveAcceso())) {
                    List<TransaccionPermisos> permisosAutorizaLis = transaccionService.permisosUsuarioTransaccion(user.getIdUsuario(), Transaccion_Enum.SURTPRESCEXT.getSufijo());
                    if (permisosAutorizaLis != null) {
                        permisosAutorizaLis.stream().forEach(item -> {
                            if (item.getAccion().equals("AUTORIZAR")) {
                                exist = true;
                            }
                        });

                        if (exist) {
                            authorizado = true;
                            authorization = true;                            
                            idResponsable = user.getIdUsuario();
                            codigoBarras = codigoBarrasAutorizte;
                            xcantidad = xcantidadAutorizte;
                            if (habilitaValidacionVale) {
                                estatus = validarCantRazonadaVales(surtimientoInsumoItem, cantidadTotalSurtirByVale);
                                if (estatus) {
                                    surtimientoInsumoItem.setCantidadVale(auxValorVale);
                                    surtimientoInsumoItem.setIdUsuarioAutCanRazn(idResponsable);
                                }
                            } else {
                                estatus = agregarLotePorCodigo();
                            }
                            if (estatus) {
                                if (permiso.isPuedeAutorizar()) {
                                    userResponsable = usuarioSelected.getNombreUsuario();
                                    passResponsable = usuarioSelected.getClaveAcceso();
                                } else {
                                    userResponsable = "";
                                    passResponsable = "";
                                }
                                estatus = Constantes.ACTIVO;                                
                                authorizado = false;
                                xcantidad = 1;
                                eliminaCodigoBarras = false;
                                codigoBarras = "";
                                idResponsable = "";
                                msjMdlSurtimiento = true;
                            }
                        } else {
                            passResponsable = "";
                            userResponsable = "";                            
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El usuario no tiene permisos para Autorizar", null);
                        }
                    } else {
                        passResponsable = "";
                        userResponsable = "";                        
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El usuario no tiene permisos para está transacción", null);
                    }
                }
            } else {
                passResponsable = "";
                userResponsable = "";                
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El nombre de usuariono es valido", null);
            }
        } catch (Exception exc) {
            LOGGER.error("Ocurrio una excepcion: ", exc);
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, exc.getMessage(), null);
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, estatus);
    }

    public String getRutaPdfs() {
        return rutaPdfs;
    }

    public void setRutaPdfs(String rutaPdfs) {
        this.rutaPdfs = rutaPdfs;
    }

    public void cancelAuthorization() {
        authorization = false;
        msjMdlSurtimiento = true;
    }

    public void handleUnSelect() {
        skuSap = new ClaveProveedorBarras_Extend();
    }

    public String getPathTmp2() {
        return pathTmp2;
    }

    public void setPathTmp2(String pathTmp2) {
        this.pathTmp2 = pathTmp2;
    }

    public String getPathTmp() {
        return pathTmp;
    }

    public void setPathTmp(String pathTmp) {
        this.pathTmp = pathTmp;
    }

    public String getUrlf() {
        return urlf;
    }

    public void setUrlf(String urlf) {
        this.urlf = urlf;
    }

    public String getProgramada() {
        return programada;
    }

    public void setProgramada(String programada) {
        this.programada = programada;
    }

    public SurtPrescripcionExtLazy getSurtPrescripcionExtLazy() {
        return surtPrescripcionExtLazy;
    }

    public void setSurtPrescripcionExtLazy(SurtPrescripcionExtLazy surtPrescripcionExtLazy) {
        this.surtPrescripcionExtLazy = surtPrescripcionExtLazy;
    }

    public String getSurtida() {
        return surtida;
    }

    public void setSurtida(String surtida) {
        this.surtida = surtida;
    }

    public boolean isHabilitaVales() {
        return habilitaVales;
    }

    public void setHabilitaVales(boolean habilitaVales) {
        this.habilitaVales = habilitaVales;
    }

    public String getCancelada() {
        return cancelada;
    }

    public void setCancelada(String cancelada) {
        this.cancelada = cancelada;
    }

    public String getMsjAlert() {
        return msjAlert;
    }

    public void setMsjAlert(String msjAlert) {
        this.msjAlert = msjAlert;
    }

    public String getPassResponsable() {
        return passResponsable;
    }

    public void setPassResponsable(String passResponsable) {
        this.passResponsable = passResponsable;
    }

    public String getUserResponsable() {
        return userResponsable;
    }

    public void setUserResponsable(String userResponsable) {
        this.userResponsable = userResponsable;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public boolean isAuthorization() {
        return authorization;
    }

    public void setAuthorization(boolean authorization) {
        this.authorization = authorization;
    }

    public boolean isMsjMdlSurtimiento() {
        return msjMdlSurtimiento;
    }

    public void setMsjMdlSurtimiento(boolean msjMdlSurtimiento) {
        this.msjMdlSurtimiento = msjMdlSurtimiento;
    }

    public boolean isProcederSurtimiento() {
        return procederSurtimiento;
    }

    public void setProcederSurtimiento(boolean procederSurtimiento) {
        this.procederSurtimiento = procederSurtimiento;
    }

    public Integer getCantResurt() {
        return cantResurt;
    }

    public void setCantResurt(Integer cantResurt) {
        this.cantResurt = cantResurt;
    }

    public Integer getNumResurtimiento() {
        return numResurtimiento;
    }

    public void setNumResurtimiento(Integer numResurtimiento) {
        this.numResurtimiento = numResurtimiento;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

    public String getArchivoVale() {
        return archivoVale;
    }

    public void setArchivoVale(String archivoVale) {
        this.archivoVale = archivoVale;
    }

    public boolean isVales() {
        return vales;
    }

    public void setVales(boolean vales) {
        this.vales = vales;
    }

    public boolean isHuboError() {
        return huboError;
    }

    public void setHuboError(boolean huboError) {
        this.huboError = huboError;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public String getCadenaBusqueda() {
        return cadenaBusqueda;
    }

    public void setCadenaBusqueda(String cadenaBusqueda) {
        this.cadenaBusqueda = cadenaBusqueda;
    }

    public Usuario getUsuarioSelected() {
        return usuarioSelected;
    }

    public void setUsuarioSelected(Usuario usuarioSelected) {
        this.usuarioSelected = usuarioSelected;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public Integer getSizeSurtimientoExtendList() {
        if (surtimientoExtendedList != null) {
            return surtimientoExtendedList.size();
        } else {
            return 0;
        }
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

    public String getTipoPrescripcion() {
        return tipoPrescripcion;
    }

    public void setTipoPrescripcion(String tipoPrescripcion) {
        this.tipoPrescripcion = tipoPrescripcion;
    }

      public List<String> getTipoPrescripcionSelectedList() {
        return tipoPrescripcionSelectedList;
    }

    public void setTipoPrescripcionSelectedList(List<String> tipoPrescripcionSelectedList) {
        this.tipoPrescripcionSelectedList = tipoPrescripcionSelectedList;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarra) {
        this.codigoBarras = codigoBarra;
    }

    public List<Surtimiento_Extend> getSurtimientoExtendedList() {
        return surtimientoExtendedList;
    }

    public void setSurtimientoExtendedList(List<Surtimiento_Extend> surtimientoExtendedList) {
        this.surtimientoExtendedList = surtimientoExtendedList;
    }

    public Surtimiento_Extend getSurtimientoExtendedSelected() {
        return surtimientoExtendedSelected;
    }

    public void setSurtimientoExtendedSelected(Surtimiento_Extend surtimientoExtendedSelected) {
        this.surtimientoExtendedSelected = surtimientoExtendedSelected;
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

    public void onRowSelectInsumo(SelectEvent event) {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.onRowSelectInsumo()");
        surtimientoInsumoExtendedSelected = (SurtimientoInsumo_Extend) event.getObject();
        if (surtimientoInsumoExtendedSelected != null) {
            elementoSeleccionado = true;
        }
    }

    public void onRowUnselectInsumo() {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.onRowUnselectInsumo()");
        surtimientoInsumoExtendedSelected = null;
        elementoSeleccionado = false;
    }

    public Integer getSizeSurtimientoInsumoExtendedList() {
        if (surtimientoInsumoExtendedList != null) {
            return surtimientoInsumoExtendedList.size();
        } else {
            return 0;
        }
    }

    public List<TipoJustificacion> getJustificacionList() {
        return justificacionList;
    }

    public void setJustificacionList(List<TipoJustificacion> justificacionList) {
        this.justificacionList = justificacionList;
    }

    public Integer getXcantidad() {
        return xcantidad;
    }

    public void setXcantidad(Integer xcantidad) {
        this.xcantidad = xcantidad;
    }

    public boolean isEliminaCodigoBarras() {
        return eliminaCodigoBarras;
    }

    public void setEliminaCodigoBarras(boolean eliminaCodigoBarras) {
        this.eliminaCodigoBarras = eliminaCodigoBarras;
    }

    public ClaveProveedorBarras_Extend getSkuSap() {
        return skuSap;
    }

    public void setSkuSap(ClaveProveedorBarras_Extend skuSap) {
        this.skuSap = skuSap;
    }

    public Pattern getRegexNumber() {
        return regexNumber;
    }

    public void setRegexNumber(Pattern regexNumber) {
        this.regexNumber = regexNumber;
    }

    public List<ClaveProveedorBarras_Extend> getSkuSapList() {
        return skuSapList;
    }

    public void setSkuSapList(List<ClaveProveedorBarras_Extend> skuSapList) {
        this.skuSapList = skuSapList;
    }
    
}
