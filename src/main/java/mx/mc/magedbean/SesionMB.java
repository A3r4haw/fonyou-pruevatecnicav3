package mx.mc.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import mx.mc.enums.TipoPerfilUsuario_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.Config;
import mx.mc.model.TransaccionPermisos;
import mx.mc.model.Usuario;
import mx.mc.service.ConfigService;
import mx.mc.service.TransaccionService;
import mx.mc.service.UsuarioService;
import mx.mc.util.Comunes;
import mx.mc.util.Mensaje;
import org.primefaces.PrimeFaces;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

/**
 *
 * @author hramirez
 */
@Controller
@Scope(value = "session")
public class SesionMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(SesionMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    private Date fechaActual;
    private String nombreUsuario;
    private String reportName;
    private byte[] reportStream;

    @Autowired
    private transient UsuarioService usuarioService;

    @Autowired
    private transient ConfigService configService;

    @Named(value = "#{usuarioEnSesion}")
    private Usuario usuarioSelected;

    @Autowired
    private transient TransaccionService transaccionService;

    private MenuModel menuModel;
    private List<TransaccionPermisos> permisosList;
    private List<Config> configList;

    private boolean prescripcionMayor24Horas;
    private String horaCorteSurtimiento;
    private Integer noDiasCaducidad;
    private Integer permitePrescribeSinExistencias;
    private boolean administrador;
    private boolean jefeArea;
    private Integer noDiasResurtimiento;
    private boolean pacienteConCita;
    private Integer horasCancelacion;
    private String sesObtenerDatos;

    private boolean conexionDimesa;
    private String dimesaUsuario;
    private String dimesaClave;
    private String dimesaUrl;
    private String dimesaFarmaciaHospital;
    private String dimesaFarmaciaClinica;
    private boolean surtimientoMixto;
    private boolean surtimientoSinPistoleo;
    private boolean ingresoSinPistoleo;
    private boolean recibeSinPistoleo;
    private boolean reabastoAutomaticoManual;
    private Integer surtimientoProveedorAutomatico;

    private Integer parametroSemaforo1;
    private Integer parametroSemaforo2;
    private boolean hospChiconcuac;
    private boolean hospToluca;
    private boolean hospProdemex;
    private boolean devolucionSinPistoleo;
    private boolean cargaLayoutConIngresoPrellenado;
    private int cantidadCero;
    private boolean capsulaDisponible;
    private boolean cantidadRazonada;
    private boolean dotacionDiaria;
    private boolean dotacionDiariaCuracion;
    private boolean surtimientoSabado;
    private boolean surtimientoDomingo;
    private Integer numDuracion;

    private boolean permiteAjusteInventarioGlobal;
    private boolean parametrosemaforo;
    private boolean separarInsumos;
    private boolean permiteCancelarSurtimiento;
    private boolean habilitaEditaryCancelarSurtimientoManual;
    private boolean permiteSurtimientoManualHospitalizacion;
    private boolean funcionesOrdenReabasto;
    private boolean activaCamposRepEmisionRecetas;
    private boolean activaCamposReporteMovimientosGenerales;
    private boolean activaCamposReporteAcumulados;
    private boolean activaCancelarSurtIntColectivo;
    private boolean activaColectivoServicios;
    private boolean activaTransformacionClaves;
    private boolean activaAutocompleteQrClave;
    private boolean activaCampoReabastoCantidadXLote;
    private boolean activaCampoReabastoCantidadXClave;

    private boolean utilizarCantidadComprometida;
    private boolean gestionInsumosAutocomplete;
    private boolean registrarOrigenInsumos;
    private boolean mostrarOrigenDeInsumo;
    private boolean mostrarCostePorLote;
    private boolean mostrarClaveProveedor;
    private boolean mostrarUnidosis;
    private int sessionTimeout;
    private boolean muestraCantidadPorCaja;
    private Integer hrsPrevReceta;
    private Integer hrsPostReceta;
    private boolean habilitaVales;
    private Integer activaNumMaxDiasResurtible;

    private boolean codificacionGS1;
    private boolean mostrarFolioAlternativo;
    private boolean funWsSiamActivo;
    private String conSiamGeneraColectivo;
    private boolean funSolicitudXServicio;
    private String con_siam_consultaFolioReceta;
    private String funSiamConsultaDetalle;
    private boolean funValidarFarmacoVigilancia;
    private boolean funAsigAlmacenServicioMultiple;
    private String conSiamConsultaInsumoEnCT;
    private Integer numDiasCiclo;
    private Integer numEtiquetasImpresaMezcla;
    private Integer passwordNumCaracter;
    private Integer protocoloVacioDefault;
    private Integer numMinutosEntregaMezcla;
    private Integer numDiasMaxPrescMezcla;
    private Integer recepcionManualPrellenada;

    private Double numMaxCompatibilidad;
    private Double factorMolMonovaPotasio;
    private Double factorMolDivalZinc;
    private Double factorMolesDivalEstabilidad;
    private Double numMaxEstabilidad;
    private Double numMaxOsmolaridad;
    private Integer numMinEditaCancela;
    private Integer tipoInsumoValidaTratDuplicado;
    private Integer noHrsPrevValidaTratDuplicado;
    private Integer noHrsPostValidaTratDuplicado;
    private String correoDestGralCenMez;
    private Integer usoRemanentes;
    private Integer usoRemanentesSoluciones;
    private String nptDefaultViaAdministracion;
    private String nptDefaultEnvaseContenedor;
    private boolean listaDiagnostico;
    private boolean capturaLoteCaducidadManual;
    private Integer noFarmacosPermitidos;
    private Integer noDiluyentesPermitidos;
    private boolean moduloCentralMezclas;
    private boolean agruparXPrescripcionAutorizar;
    private boolean validaExistencias1erToma;
    private boolean validaExistencias1erDia;
    private boolean validaExistenciasTomasTotales;
    private boolean validaExistenciasRestrictiva;
    private boolean enviaCorreoalValidarMezcla;
    
    /**
     * Obtiene el usuario logueado y carga todos sus datos y permisos
     */
    @PostConstruct
    public void init() {
        LOGGER.debug("mx.mc.magedbean.SesionMB.init()");
        sesObtenerDatos = "ses.obtener.datos";
        limpia();
        obtenerUsuarioEnSesion();
        obtenerDatosUsuario();
        obtenerPerfilUsuario();
        obtenerDatosSistema();
        obtenerParametros();
        obtenerPermisosUsuario();
    }

    /**
     * limpia las variables
     */
    private void limpia() {
        LOGGER.debug("mx.mc.magedbean.SesionMB.limpia()");
        fechaActual = new java.util.Date();
        nombreUsuario = null;
        usuarioSelected = new Usuario();
        permisosList = new ArrayList<>();
        usuarioSelected.setPermisosList(permisosList);
        configList = new ArrayList<>();
        menuModel = new DefaultMenuModel();
        this.administrador = false;
        this.jefeArea = false;
    }

    /**
     * Obtiene datos del usuario Autenticado
     */
    private void obtenerUsuarioEnSesion() {
        LOGGER.debug("mx.mc.magedbean.SesionMB.obtenerUsuarioEnSesion()");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            nombreUsuario = authentication.getName();
            LOGGER.debug("Usuario en Sesión: {}, Con Permisos en Transacciones: ", nombreUsuario);
        }
    }

    /**
     * Obtiene Información completa de Usuario En Sesión done está activo el
     * usuario y no está bloqueado
     */
    private void obtenerDatosUsuario() {
        LOGGER.debug("mx.mc.magedbean.SesionMB.loadDatosUsuario()");
        boolean status = Constantes.INACTIVO;
        try {
            if (nombreUsuario != null
                    && !nombreUsuario.isEmpty()) {
                Usuario usuario = new Usuario();
                usuario.setNombreUsuario(nombreUsuario);
                usuario.setActivo(Constantes.ACTIVO);
                usuario.setUsuarioBloqueado(Constantes.INACTIVO);
                usuarioSelected = usuarioService.obtener(usuario);
            }
            status = Constantes.ACTIVO;
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString(sesObtenerDatos), ex);
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(sesObtenerDatos), null);
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    /**
     * Obtiene los módulos Activos y permitidos al Usuario, Transacciones
     * activas del usuario, y acciones por Transaccion Agrega los menus y
     * submenus genericos
     */
    private void obtenerPermisosUsuario() {
        LOGGER.debug("mx.mc.magedbean.SesionMB.obtenerPermisosUsuario()");
        boolean status = Constantes.INACTIVO;
        try {
            if (usuarioSelected == null || usuarioSelected.getIdUsuario() == null || usuarioSelected.getIdUsuario().isEmpty()) {
                LOGGER.error(RESOURCES.getString(sesObtenerDatos));
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(sesObtenerDatos), null);

            } else {
                permisosList = transaccionService.obtenerPermisosPorIdUsuario(usuarioSelected.getIdUsuario());
                if (permisosList != null && !permisosList.isEmpty()) {
                    usuarioSelected.setPermisosList(permisosList);
                    permisosList = new ArrayList<>();
                    TransaccionPermisos tp;
                    menuModel = new DefaultMenuModel();
                    DefaultSubMenu subMenuPrevio = null;
                    DefaultSubMenu subMenu = null;
                    DefaultMenuItem menuItem;
                    TransaccionPermisos transaccionActual;
                    String appContext = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
                    ListIterator<TransaccionPermisos> ite = usuarioSelected.getPermisosList().listIterator();
                    while (ite.hasNext()) {
                        transaccionActual = ite.next();
                        if (transaccionActual.isModuloActivo() && transaccionActual.isRolActivo() && transaccionActual.isTransaccionActivo()) {
                            tp = new TransaccionPermisos();
                            tp.setNombre(transaccionActual.getModulo());
                            tp.setTransaccionActivo(transaccionActual.isModuloActivo());
                            tp.setDescripcion(Constantes.TXT_ESPACIO);
                            tp.setUrl(Constantes.URL_GATO);
                            tp.setModuloActivo(Constantes.ACTIVO);

                            //Valida que no haya sido creado y Crea Submenu
                            if (!permisosList.contains(tp)) {
                                subMenu = new DefaultSubMenu(" " + transaccionActual.getModulo());
                                subMenu.setIcon(transaccionActual.getIconModulo());
                                permisosList.add(tp);
                            }

                            tp = new TransaccionPermisos();
                            tp.setNombre(transaccionActual.getNombre());
                            tp.setTransaccionActivo(transaccionActual.isTransaccionActivo());
                            tp.setDescripcion(transaccionActual.getDescripcion());
                            tp.setUrl(transaccionActual.getUrl().toLowerCase());
                            tp.setCodigo(transaccionActual.getCodigo());

                            //Valida que no haya sido creado y Crea Elemento de Submenu
                            if (subMenu != null) {
                                if (!permisosList.contains(tp)) {
                                    menuItem = new DefaultMenuItem(" " + transaccionActual.getNombre());
                                    menuItem.setUrl(appContext + transaccionActual.getUrl());
                                    menuItem.setIcon(transaccionActual.getIconTransaccion());
                                    subMenu.addElement(menuItem);
                                    permisosList.add(tp);
                                }

                                if (subMenuPrevio != null && !subMenu.getLabel().equals(subMenuPrevio.getLabel())) {
                                    menuModel.addElement(subMenuPrevio);
                                }
                            }
                            subMenuPrevio = subMenu;
                        }
                    }
                    if (subMenu != null) {
                        menuModel.addElement(subMenu);
                    }
//                    Agrega el Menu default de sessión
                    subMenu = new DefaultSubMenu(" Sesión ");
                    subMenu.setIcon("fa fa-user");

                    menuItem = new DefaultMenuItem(" Mi Cuenta");
                    menuItem.setUrl(appContext + "/secure/cuenta.xhtml");
                    menuItem.setIcon("fa fa-th-list");
                    subMenu.addElement(menuItem);

                    menuItem = new DefaultMenuItem(" Cerrar Sesión");
                    menuItem.setUrl(appContext + "/perform_logout");
                    menuItem.setIcon("fa fa-sign-out");
                    subMenu.addElement(menuItem);
                    menuModel.addElement(subMenu);
                }
                status = Constantes.ACTIVO;
            }
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString(sesObtenerDatos), ex);
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(sesObtenerDatos), null);
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    /**
     * Obtiene la lista de Parámetros del sistema
     */
    private void obtenerDatosSistema() {
        LOGGER.debug("mx.mc.magedbean.SesionMB.obtenerDatosSistema()");
        Config c = new Config();
        c.setActiva(Constantes.ACTIVO);
        try {
            configList = configService.obtenerLista(c);
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString("sys.config.err"), ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sys.config.err"), null);
        }
    }

    /**
     * Obtiene la configuración de cada parámetro
     */
    private void obtenerParametros() {
        LOGGER.trace("mx.mc.magedbean.SesionMB.obtenerParametros()");
        prescripcionMayor24Horas = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_PRESCRIPCION24HRS) == Constantes.ESTATUS_ACTIVO;
        horaCorteSurtimiento = Comunes.obtenValorConfiguracion(configList, Constantes.PARAM_SYSTEM_HORACORTESURTIMIENTO);
        noDiasCaducidad = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_DIASPARACADUCIDAD);
        permitePrescribeSinExistencias = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_PRESCRIBE_SIN_EXISTENCIAS);
        pacienteConCita = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_PACIENTECONCITA) == Constantes.ESTATUS_ACTIVO;
        noDiasResurtimiento = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_DIASRESURTIMIENTO);
        horasCancelacion = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_HORASCANCELACION);
        conexionDimesa = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_FUNC_DIMESA) == Constantes.ESTATUS_ACTIVO;
        dimesaUsuario = Comunes.obtenValorConfiguracion(configList, Constantes.PARAM_SYSTEM_DIMESA_WS_USUARIO);
        dimesaClave = Comunes.obtenValorConfiguracion(configList, Constantes.PARAM_SYSTEM_DIMESA_WS_CONTRASENIA);
        dimesaUrl = Comunes.obtenValorConfiguracion(configList, Constantes.PARAM_SYSTEM_DIMESA_WS_URL);
        dimesaFarmaciaHospital = Comunes.obtenValorConfiguracion(configList, Constantes.PARAM_SYSTEM_DIMESA_WS_FARMACIA_HOSPITAL);
        dimesaFarmaciaClinica = Comunes.obtenValorConfiguracion(configList, Constantes.PARAM_SYSTEM_DIMESA_WS_FARMACIA_CLINICA);
        surtimientoMixto = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_SURTIMIENTO_MIXTO) == Constantes.ESTATUS_ACTIVO;
        surtimientoSinPistoleo = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_SURTIMIENTO_SIN_PISTOLEO) == Constantes.ESTATUS_ACTIVO;
        recibeSinPistoleo = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_RECIBIR_SIN_PISTOLEO) == Constantes.ESTATUS_ACTIVO;
        ingresoSinPistoleo = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_INGRESO_SIN_PISTOLEO) == Constantes.ESTATUS_ACTIVO;
        reabastoAutomaticoManual = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_REABASTO_AUTOMATICO_MANUAL) == Constantes.ESTATUS_ACTIVO;
        surtimientoProveedorAutomatico = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SURTIMIENTO_PROVEEDOR_AUTOMATICO);
        parametroSemaforo1 = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_PARAMETRO1_SEMAFORIZACION);
        parametroSemaforo2 = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_PARAMETRO2_SEMAFORIZACION);
        hospChiconcuac = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSEM_HOSP_CHICONCUAC) == Constantes.ESTATUS_ACTIVO;
        hospToluca = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSEM_HOSP_TOLUCA) == Constantes.ESTATUS_ACTIVO;
        hospProdemex = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSEM_HOSP_PRODEMEX) == Constantes.ESTATUS_ACTIVO;
        devolucionSinPistoleo = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSYTEM_DEVOLUCION_SIN_PISTOLEO) == Constantes.ESTATUS_ACTIVO;
        cargaLayoutConIngresoPrellenado = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSYTEM_RECEPCION_LAYOUT_CON_INGRESO_PRELLENADO) == Constantes.ESTATUS_ACTIVO;
        cantidadCero = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSYTEM_CANTIDAD_CERO);
        capsulaDisponible = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_SURTIMIENTO_CAPSULA) == Constantes.ESTATUS_ACTIVO;
        cantidadRazonada = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSEM_CANTIDAD_RAZONADA) == Constantes.ESTATUS_ACTIVO;
        permiteAjusteInventarioGlobal = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSEM_PERMITE_AJUSTE_INEVTARIO_GLOBAL) == Constantes.ESTATUS_ACTIVO;
        parametrosemaforo = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_PERMITE_VISUALIZAR_SEMAFORO) == Constantes.ESTATUS_ACTIVO;
        permiteCancelarSurtimiento = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_PERMITE_CANCELAR_SURTIMIENTO) == Constantes.ESTATUS_ACTIVO;
        habilitaEditaryCancelarSurtimientoManual = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_ACTIVA_CANCELAR_EDITAR_SURTMANUAL) == Constantes.ESTATUS_ACTIVO;
        permiteSurtimientoManualHospitalizacion = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_PERMITE_SURTIMIENTO_MANUAL_HOSPITALIZACION) == Constantes.ESTATUS_ACTIVO;
        funcionesOrdenReabasto = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_ACTIVA_ELIMINAR_CODIGOMEDICAMENTO) == Constantes.ESTATUS_ACTIVO;
        activaCamposRepEmisionRecetas = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_ACTIVA_CAMPOS_REP_CONSULTA_RECETAS) == Constantes.ESTATUS_ACTIVO;
        activaCamposReporteMovimientosGenerales = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_ACTIVA_CAMPOS_REP_MOVIMIENTOSGENERALES) == Constantes.ESTATUS_ACTIVO;
        activaCamposReporteAcumulados = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_ACTIVA_CAMPOS_REP_ACUMULADOS) == Constantes.ESTATUS_ACTIVO;
        activaCancelarSurtIntColectivo = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_ACTIVA_CAMPOS_CANCEL_SURT_COLECTIVO) == Constantes.ESTATUS_ACTIVO;
        separarInsumos = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_SEPARAR_INSUMOS) == Constantes.ESTATUS_ACTIVO;
        dotacionDiaria = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_DOTACION_DIARIA) == Constantes.ESTATUS_ACTIVO;
        dotacionDiariaCuracion = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSEM_DOTACION_DIARIA_CURACION) == Constantes.ESTATUS_ACTIVO;
        activaColectivoServicios = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_COLECTIVO_SERVICIOS) == Constantes.ESTATUS_ACTIVO;
        activaTransformacionClaves = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_TRANSFORMACION_CLAVES) == Constantes.ESTATUS_ACTIVO;
        activaAutocompleteQrClave = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_AUTOCOMPLETE_BY_QR_CLAVE_BARRAS) == Constantes.ESTATUS_ACTIVO;
        surtimientoSabado = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_SURTIMIENTO_SABADO) == Constantes.ESTATUS_ACTIVO;
        surtimientoDomingo = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_SURTIMIENTO_DOMINGO) == Constantes.ESTATUS_ACTIVO;
        numDuracion = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_SURTIMIENTO_DURACION);
        activaCampoReabastoCantidadXClave = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_ACTIVA_CAMPO_REABASTO_CLAVE) == Constantes.ESTATUS_ACTIVO;
        activaCampoReabastoCantidadXLote = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_ACTIVA_CAMPO_REABASTO_LOTE) == Constantes.ESTATUS_ACTIVO;
        utilizarCantidadComprometida = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_USAR_CANTIDAD_COMPROMETIDA) == Constantes.ESTATUS_ACTIVO;
        gestionInsumosAutocomplete = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_GESTION_INSUMOS_POR_AUTOCOMPLETE) == Constantes.ESTATUS_ACTIVO;
        registrarOrigenInsumos = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_REGISTRAR_ORIGEN_INSUMOS) == Constantes.ESTATUS_ACTIVO;
        sessionTimeout = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_SESSION_TIMEOUT);
        muestraCantidadPorCaja = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_CANTIDAD_CAJA) == Constantes.ESTATUS_ACTIVO;
        mostrarOrigenDeInsumo = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_ACTIVA_MOSTRAR_ORIGEN_INSUMOS) == Constantes.ESTATUS_ACTIVO;
        mostrarCostePorLote = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_ACTIVA_MOSTRAR_COSTE_POR_LOTE) == Constantes.ESTATUS_ACTIVO;
        mostrarClaveProveedor = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_ACTIVA_MOSTRAR_CLAVE_PROVEEDOR) == Constantes.ESTATUS_ACTIVO;
        mostrarUnidosis = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_ACTIVA_MOSTRAR_UNIDOSIS) == Constantes.ESTATUS_ACTIVO;
        codificacionGS1 = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_CODIGOS_GS1) == Constantes.ESTATUS_ACTIVO;
        hrsPrevReceta = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_NUM_HRS_ANTERIORES_RECETA);
        hrsPostReceta = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_NUM_HRS_POSTERIORES_RECETA);
        habilitaVales = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_ACTIVA_HABILITA_VALES) == Constantes.ESTATUS_ACTIVO;
        mostrarFolioAlternativo = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_MOSTRAR_FOLIO_ALTERNATIVO) == Constantes.ESTATUS_ACTIVO;
        activaNumMaxDiasResurtible = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_ACTIVA_NUMERO_MAXIMO_DIAS_RESURTIBLE);
        funWsSiamActivo = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_WS_SIAM) == Constantes.ESTATUS_ACTIVO;
        conSiamGeneraColectivo = Comunes.obtenValorConfiguracion(configList, Constantes.PARAM_SYSTEM_URL_GENERACOLECTIVO_SIAM);
        funSolicitudXServicio = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_SOLICITUD_INSUMO_X_SERVICIO) == Constantes.ESTATUS_ACTIVO;
        con_siam_consultaFolioReceta = Comunes.obtenValorConfiguracion(configList, Constantes.PARAM_SYSTEM_URL_CONSULTAFOLIORECETA);
        funSiamConsultaDetalle = Comunes.obtenValorConfiguracion(configList, Constantes.PARAM_SYSTEM_URL_CONSULTADETALLE_SIAM);
        funValidarFarmacoVigilancia = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_FUNVALIDARFARMACOVIGILANCIA) == Constantes.ESTATUS_ACTIVO;
        funAsigAlmacenServicioMultiple = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_ALMACEN_SERVICIO_MULTIPLE) == Constantes.ESTATUS_ACTIVO;
        conSiamConsultaInsumoEnCT = Comunes.obtenValorConfiguracion(configList, Constantes.PARAM_SYSTEM_URL_CONSULTAINSUMOENCT_SIAM);
        numDiasCiclo = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_NUM_DIAS_CICLO);
        numEtiquetasImpresaMezcla = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_NUM_ETIQUETA_IMPRIME_MEZCLA);
        passwordNumCaracter = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_PASS_NUM_CARACTER);
        protocoloVacioDefault = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_PROTOCOLO_VACIO_PORDEFAULT);
        numMinutosEntregaMezcla = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_NUM_MINUTOS_ENTREGA_MEZCLA);
        numDiasMaxPrescMezcla = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_NUM_DIAS_MAX_PRESCR_MEZCLA);
        recepcionManualPrellenada = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_RECEPCION_MANUAL_PRELLENADA);

        numMaxCompatibilidad = Comunes.obtenValorConfiguracionDec(configList, Constantes.PARAM_SYSTEM_NUM_MAX_COMPATIBILIDAD);
        factorMolMonovaPotasio = Comunes.obtenValorConfiguracionDec(configList, Constantes.PARAM_SYSTEM_FACTOR_MOL_MONOVA_POTASIO);
        factorMolDivalZinc = Comunes.obtenValorConfiguracionDec(configList, Constantes.PARAM_SYSTEM_FACTOR_MOL_DIVAL_ZINC);
        factorMolesDivalEstabilidad = Comunes.obtenValorConfiguracionDec(configList, Constantes.PARAM_SYSTEM_FACTOR_MOL_DIVAL_ESTABILIDAD);
        numMaxEstabilidad = Comunes.obtenValorConfiguracionDec(configList, Constantes.PARAM_SYSTEM_NUM_MAX_ESTABILIDAD);
        numMaxOsmolaridad = Comunes.obtenValorConfiguracionDec(configList, Constantes.PARAM_SYSTEM_NUM_MAX_OSMOLARIDAD);
        numMinEditaCancela = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_NUM_MIN_EDITACANCELA);
        tipoInsumoValidaTratDuplicado = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_FUNC_TIPOINSUMO_PRESCRIPCION);
        noHrsPrevValidaTratDuplicado = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_FUNC_NUMHRSPREV_PRESCRIPCION);
        noHrsPostValidaTratDuplicado = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_FUNC_NUMHRSPOST_PRESCRIPCION);
        correoDestGralCenMez = Comunes.obtenValorConfiguracion(configList, Constantes.PARAM_SYSTEM_CORREO_DEST_GRAL_CEN_MEZ);
        usoRemanentes = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_USO_REMANENTES);
        usoRemanentesSoluciones = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_USO_REMANENTES_SOLUCIONES);

        nptDefaultViaAdministracion = Comunes.obtenValorConfiguracion(configList, Constantes.PARAM_SYSTEM_NPT_VIAADMON_DEFAULT);
        nptDefaultEnvaseContenedor = Comunes.obtenValorConfiguracion(configList, Constantes.PARAM_SYSTEM_NPT_ENVCONT_DEFAULT);
        listaDiagnostico = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_LISTA_DIAGNOSTICO) == Constantes.ESTATUS_ACTIVO;
        capturaLoteCaducidadManual = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_CAPTURA_LOTE_CADUCIDAD) == Constantes.ESTATUS_ACTIVO;
        noFarmacosPermitidos = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_NUMERO_FARMACOS_PERMITIDOS);
        noDiluyentesPermitidos = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_NUMERO_DILUYENTES_PERMITIDOS);
        moduloCentralMezclas = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_FUNC_MODULO_CENTRAL_MEZCLAS) == Constantes.ESTATUS_ACTIVO;
        agruparXPrescripcionAutorizar = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_AGRUPAR_MEZCLAS_POR_PRESCRIPCION_AUTORIZAR) == Constantes.ESTATUS_ACTIVO;
        validaExistencias1erToma = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_VALIDAR_EXISTENCIAS_1ER_TOMA) == Constantes.ESTATUS_ACTIVO;
        validaExistencias1erDia = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_VALIDAR_EXISTENCIAS_1ER_DIA) == Constantes.ESTATUS_ACTIVO;
        validaExistenciasTomasTotales = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_VALIDAR_EXISTENCIAS_TOMAS_TOTALES) == Constantes.ESTATUS_ACTIVO;
        validaExistenciasRestrictiva = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_VALIDAR_EXISTENCIAS_RESTRICTIVA) == Constantes.ESTATUS_ACTIVO;
        enviaCorreoalValidarMezcla = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_ENVIA_CORREO_AL_VALIDAR_MEZCLA) == Constantes.ESTATUS_ACTIVO;
        
    }

    /**
     * Obtiene el perfil del usuario
     */
    public void obtenerPerfilUsuario() {
        LOGGER.debug("mx.mc.magedbean.SesionMB.obtenerPerfilUsuario()");
        try {
            this.administrador = (Objects.equals(usuarioSelected.getAdministrador(), TipoPerfilUsuario_Enum.ADMIN.getIdTipoPerfil()));
            this.jefeArea = (Objects.equals(usuarioSelected.getIdTipoPerfil(), TipoPerfilUsuario_Enum.JEFE_AREA.getIdTipoPerfil()));
        } catch (Exception ex) {
            LOGGER.error("Error al obtener el perfil de usuario: {}", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(sesObtenerDatos), null);
        }
    }

    public void freeBytesFromSession() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        if (sesion != null) {
            sesion.setReportStream(null);
            sesion.setReportName(null);
        }
    }

    //<editor-fold  defaultstate="collapsed" desc="Getter and Setters...">
    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Usuario getUsuarioSelected() {
        return usuarioSelected;
    }

    public void setUsuarioSelected(Usuario usuarioSelected) {
        this.usuarioSelected = usuarioSelected;
    }

    public List<TransaccionPermisos> getPermisosList() {
        return permisosList;
    }

    public void setPermisosList(List<TransaccionPermisos> permisosList) {
        this.permisosList = permisosList;
    }

    public MenuModel getMenuModel() {
        return menuModel;
    }

    public void setMenuModel(MenuModel menuModel) {
        this.menuModel = menuModel;
    }

    public List<Config> getConfigList() {
        return configList;
    }

    public void setConfigList(List<Config> configList) {
        this.configList = configList;
    }

    public boolean isPrescripcionMayor24Horas() {
        return prescripcionMayor24Horas;
    }

    public void setPrescripcionMayor24Horas(boolean prescripcionMayor24Horas) {
        this.prescripcionMayor24Horas = prescripcionMayor24Horas;
    }

    public String getHoraCorteSurtimiento() {
        return horaCorteSurtimiento;
    }

    public void setHoraCorteSurtimiento(String horaCorteSurtimiento) {
        this.horaCorteSurtimiento = horaCorteSurtimiento;
    }

    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }

    public boolean isJefeArea() {
        return jefeArea;
    }

    public void setJefeArea(boolean jefeArea) {
        this.jefeArea = jefeArea;
    }

    public Integer getNoDiasCaducidad() {
        return noDiasCaducidad;
    }

    public void setNoDiasCaducidad(Integer noDiasCaducidad) {
        this.noDiasCaducidad = noDiasCaducidad;
    }

    public Integer getPermitePrescribeSinExistencias() {
        return permitePrescribeSinExistencias;
    }

    public void setPermitePrescribeSinExistencias(Integer permitePrescribeSinExistencias) {
        this.permitePrescribeSinExistencias = permitePrescribeSinExistencias;
    }

    private int number;

    public int getNumber() {
        return number;
    }

    public void increment() {
        number++;
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

    public Integer getHorasCancelacion() {
        return horasCancelacion;
    }

    public void setHorasCancelacion(Integer horasCancelacion) {
        this.horasCancelacion = horasCancelacion;
    }

    public String getDimesaUsuario() {
        return dimesaUsuario;
    }

    public void setDimesaUsuario(String dimesaUsuario) {
        this.dimesaUsuario = dimesaUsuario;
    }

    public String getDimesaClave() {
        return dimesaClave;
    }

    public void setDimesaClave(String dimesaClave) {
        this.dimesaClave = dimesaClave;
    }

    public String getDimesaUrl() {
        return dimesaUrl;
    }

    public void setDimesaUrl(String dimesaUrl) {
        this.dimesaUrl = dimesaUrl;
    }

    public String getDimesaFarmaciaHospital() {
        return dimesaFarmaciaHospital;
    }

    public void setDimesaFarmaciaHospital(String dimesaFarmaciaHospital) {
        this.dimesaFarmaciaHospital = dimesaFarmaciaHospital;
    }

    public String getDimesaFarmaciaClinica() {
        return dimesaFarmaciaClinica;
    }

    public void setDimesaFarmaciaClinica(String dimesaFarmaciaClinica) {
        this.dimesaFarmaciaClinica = dimesaFarmaciaClinica;
    }

    public boolean isSurtimientoMixto() {
        return surtimientoMixto;
    }

    public void setSurtimientoMixto(boolean surtimientoMixto) {
        this.surtimientoMixto = surtimientoMixto;
    }

    public boolean isSurtimientoSinPistoleo() {
        return surtimientoSinPistoleo;
    }

    public void setSurtimientoSinPistoleo(boolean surtimientoSinPistoleo) {
        this.surtimientoSinPistoleo = surtimientoSinPistoleo;
    }

    public boolean isIngresoSinPistoleo() {
        return ingresoSinPistoleo;
    }

    public void setIngresoSinPistoleo(boolean ingresoSinPistoleo) {
        this.ingresoSinPistoleo = ingresoSinPistoleo;
    }

    public boolean isRecibeSinPistoleo() {
        return recibeSinPistoleo;
    }

    public void setRecibeSinPistoleo(boolean recibeSinPistoleo) {
        this.recibeSinPistoleo = recibeSinPistoleo;
    }

    public boolean isReabastoAutomaticoManual() {
        return reabastoAutomaticoManual;
    }

    public void setReabastoAutomaticoManual(boolean reabastoAutomaticoManual) {
        this.reabastoAutomaticoManual = reabastoAutomaticoManual;
    }

    public Integer getSurtimientoProveedorAutomatico() {
        return surtimientoProveedorAutomatico;
    }

    public void setSurtimientoProveedorAutomatico(Integer surtimientoProveedorAutomatico) {
        this.surtimientoProveedorAutomatico = surtimientoProveedorAutomatico;
    }

    public Integer getParametroSemaforo1() {
        return parametroSemaforo1;
    }

    public void setParametroSemaforo1(Integer parametroSemaforo1) {
        this.parametroSemaforo1 = parametroSemaforo1;
    }

    public Integer getParametroSemaforo2() {
        return parametroSemaforo2;
    }

    public void setParametroSemaforo2(Integer parametroSemaforo2) {
        this.parametroSemaforo2 = parametroSemaforo2;
    }

    public boolean isHospChiconcuac() {
        return hospChiconcuac;
    }

    public void setHospChiconcuac(boolean hospChiconcuac) {
        this.hospChiconcuac = hospChiconcuac;
    }

    public boolean isHospToluca() {
        return hospToluca;
    }

    public void setHospToluca(boolean hospToluca) {
        this.hospToluca = hospToluca;
    }

    public boolean isHospProdemex() {
        return hospProdemex;
    }

    public void setHospProdemex(boolean hospProdemex) {
        this.hospProdemex = hospProdemex;
    }

    public boolean isDevolucionSinPistoleo() {
        return devolucionSinPistoleo;
    }

    public void setDevolucionSinPistoleo(boolean devolucionSinPistoleo) {
        this.devolucionSinPistoleo = devolucionSinPistoleo;
    }

    public boolean isCargaLayoutConIngresoPrellenado() {
        return cargaLayoutConIngresoPrellenado;
    }

    public void setCargaLayoutConIngresoPrellenado(boolean cargaLayoutConIngresoPrellenado) {
        this.cargaLayoutConIngresoPrellenado = cargaLayoutConIngresoPrellenado;
    }

    public int getCantidadCero() {
        return cantidadCero;
    }

    public void setCantidadCero(int cantidadCero) {
        this.cantidadCero = cantidadCero;
    }

    public boolean isCapsulaDisponible() {
        return capsulaDisponible;
    }

    public void setCapsulaDisponible(boolean capsulaDisponible) {
        this.capsulaDisponible = capsulaDisponible;
    }

    public boolean isPermiteAjusteInventarioGlobal() {
        return permiteAjusteInventarioGlobal;
    }

    public void setPermiteAjusteInventarioGlobal(boolean permiteAjusteInventarioGlobal) {
        this.permiteAjusteInventarioGlobal = permiteAjusteInventarioGlobal;
    }

    public boolean isParametrosemaforo() {
        return parametrosemaforo;
    }

    public void setParametrosemaforo(boolean parametrosemaforo) {
        this.parametrosemaforo = parametrosemaforo;
    }

    public boolean isSepararInsumos() {
        return separarInsumos;
    }

    public void setSepararInsumos(boolean separarInsumos) {
        this.separarInsumos = separarInsumos;
    }

    public boolean isCantidadRazonada() {
        return cantidadRazonada;
    }

    public void setCantidadRazonada(boolean cantidadRazonada) {
        this.cantidadRazonada = cantidadRazonada;
    }

    public boolean isDotacionDiaria() {
        return dotacionDiaria;
    }

    public void setDotacionDiaria(boolean dotacionDiaria) {
        this.dotacionDiaria = dotacionDiaria;
    }

    public boolean isDotacionDiariaCuracion() {
        return dotacionDiariaCuracion;
    }

    public void setDotacionDiariaCuracion(boolean dotacionDiariaCuracion) {
        this.dotacionDiariaCuracion = dotacionDiariaCuracion;
    }

    public boolean isPermiteCancelarSurtimiento() {
        return permiteCancelarSurtimiento;
    }

    public void setPermiteCancelarSurtimiento(boolean permiteCancelarSurtimiento) {
        this.permiteCancelarSurtimiento = permiteCancelarSurtimiento;
    }

    public boolean isHabilitaEditaryCancelarSurtimientoManual() {
        return habilitaEditaryCancelarSurtimientoManual;
    }

    public void setHabilitaEditaryCancelarSurtimientoManual(boolean habilitaEditaryCancelarSurtimientoManual) {
        this.habilitaEditaryCancelarSurtimientoManual = habilitaEditaryCancelarSurtimientoManual;
    }

    public boolean isFuncionesOrdenReabasto() {
        return funcionesOrdenReabasto;
    }

    public void setFuncionesOrdenReabasto(boolean funcionesOrdenReabasto) {
        this.funcionesOrdenReabasto = funcionesOrdenReabasto;
    }

    public boolean isActivaCamposRepEmisionRecetas() {
        return activaCamposRepEmisionRecetas;
    }

    public void setActivaCamposRepEmisionRecetas(boolean activaCamposRepEmisionRecetas) {
        this.activaCamposRepEmisionRecetas = activaCamposRepEmisionRecetas;
    }

    public boolean isActivaCamposReporteMovimientosGenerales() {
        return activaCamposReporteMovimientosGenerales;
    }

    public void setActivaCamposReporteMovimientosGenerales(boolean activaCamposReporteMovimientosGenerales) {
        this.activaCamposReporteMovimientosGenerales = activaCamposReporteMovimientosGenerales;
    }

    public boolean isActivaCamposReporteAcumulados() {
        return activaCamposReporteAcumulados;
    }

    public void setActivaCamposReporteAcumulados(boolean activaCamposReporteAcumulados) {
        this.activaCamposReporteAcumulados = activaCamposReporteAcumulados;
    }

    public boolean isActivaCancelarSurtIntColectivo() {
        return activaCancelarSurtIntColectivo;
    }

    public void setActivaCancelarSurtIntColectivo(boolean activaCancelarSurtIntColectivo) {
        this.activaCancelarSurtIntColectivo = activaCancelarSurtIntColectivo;
    }

    public boolean isActivaColectivoServicios() {
        return activaColectivoServicios;
    }

    public void setActivaColectivoServicios(boolean activaColectivoServicios) {
        this.activaColectivoServicios = activaColectivoServicios;
    }

    public boolean isActivaTransformacionClaves() {
        return activaTransformacionClaves;
    }

    public void setActivaTransformacionClaves(boolean activaTransformacionClaves) {
        this.activaTransformacionClaves = activaTransformacionClaves;
    }

    public boolean isActivaAutocompleteQrClave() {
        return activaAutocompleteQrClave;
    }

    public void setActivaAutocompleteQrClave(boolean activaAutocompleteQrClave) {
        this.activaAutocompleteQrClave = activaAutocompleteQrClave;
    }

    public boolean isConexionDimesa() {
        return conexionDimesa;
    }

    public boolean isSurtimientoSabado() {
        return surtimientoSabado;
    }

    public void setSurtimientoSabado(boolean surtimientoSabado) {
        this.surtimientoSabado = surtimientoSabado;
    }

    public boolean isSurtimientoDomingo() {
        return surtimientoDomingo;
    }

    public void setSurtimientoDomingo(boolean surtimientoDomingo) {
        this.surtimientoDomingo = surtimientoDomingo;
    }

    public Integer getNumDuracion() {
        return numDuracion;
    }

    public void setNumDuracion(Integer numDuracion) {
        this.numDuracion = numDuracion;
    }

    public boolean isActivaCampoReabastoCantidadXLote() {
        return activaCampoReabastoCantidadXLote;
    }

    public void setActivaCampoReabastoCantidadXLote(boolean activaCampoReabastoCantidadXLote) {
        this.activaCampoReabastoCantidadXLote = activaCampoReabastoCantidadXLote;
    }

    public boolean isActivaCampoReabastoCantidadXClave() {
        return activaCampoReabastoCantidadXClave;
    }

    public void setActivaCampoReabastoCantidadXClave(boolean activaCampoReabastoCantidadXClave) {
        this.activaCampoReabastoCantidadXClave = activaCampoReabastoCantidadXClave;
    }

    public boolean isUtilizarCantidadComprometida() {
        return utilizarCantidadComprometida;
    }

    public void setUtilizarCantidadComprometida(boolean utilizarCantidadComprometida) {
        this.utilizarCantidadComprometida = utilizarCantidadComprometida;
    }

    public boolean isGestionInsumosAutocomplete() {
        return gestionInsumosAutocomplete;
    }

    public void setGestionInsumosAutocomplete(boolean gestionInsumosAutocomplete) {
        this.gestionInsumosAutocomplete = gestionInsumosAutocomplete;
    }

    public boolean isRegistrarOrigenInsumos() {
        return registrarOrigenInsumos;
    }

    public void setRegistrarOrigenInsumos(boolean registrarOrigenInsumos) {
        this.registrarOrigenInsumos = registrarOrigenInsumos;
    }

    public boolean isMostrarOrigenDeInsumo() {
        return mostrarOrigenDeInsumo;
    }

    public void setMostrarOrigenDeInsumo(boolean mostrarOrigenDeInsumo) {
        this.mostrarOrigenDeInsumo = mostrarOrigenDeInsumo;
    }

    public boolean isMostrarCostePorLote() {
        return mostrarCostePorLote;
    }

    public void setMostrarCostePorLote(boolean mostrarCostePorLote) {
        this.mostrarCostePorLote = mostrarCostePorLote;
    }

    public boolean isMostrarClaveProveedor() {
        return mostrarClaveProveedor;
    }

    public void setMostrarClaveProveedor(boolean mostrarClaveProveedor) {
        this.mostrarClaveProveedor = mostrarClaveProveedor;
    }

    public boolean isMostrarUnidosis() {
        return mostrarUnidosis;
    }

    public void setMostrarUnidosis(boolean mostrarUnidosis) {
        this.mostrarUnidosis = mostrarUnidosis;
    }

    public int getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(int sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public boolean isPermiteSurtimientoManualHospitalizacion() {
        return permiteSurtimientoManualHospitalizacion;
    }

    public void setPermiteSurtimientoManualHospitalizacion(boolean permiteSurtimientoManualHospitalizacion) {
        this.permiteSurtimientoManualHospitalizacion = permiteSurtimientoManualHospitalizacion;
    }

    public boolean isCodificacionGS1() {
        return codificacionGS1;
    }

    public boolean isMuestraCantidadPorCaja() {
        return muestraCantidadPorCaja;
    }

    public void setMuestraCantidadPorCaja(boolean muestraCantidadPorCaja) {
        this.muestraCantidadPorCaja = muestraCantidadPorCaja;
    }

    public Integer getHrsPrevReceta() {
        return hrsPrevReceta;
    }

    public void setHrsPrevReceta(Integer hrsPrevReceta) {
        this.hrsPrevReceta = hrsPrevReceta;
    }

    public Integer getHrsPostReceta() {
        return hrsPostReceta;
    }

    public void setHrsPostReceta(Integer hrsPostReceta) {
        this.hrsPostReceta = hrsPostReceta;
    }

    public boolean isHabilitaVales() {
        return habilitaVales;
    }

    public void setHabilitaVales(boolean habilitaVales) {
        this.habilitaVales = habilitaVales;
    }

    public Integer getActivaNumMaxDiasResurtible() {
        return activaNumMaxDiasResurtible;
    }

    public void setActivaNumMaxDiasResurtible(Integer activaNumMaxDiasResurtible) {
        this.activaNumMaxDiasResurtible = activaNumMaxDiasResurtible;
    }

    public boolean isMostrarFolioAlternativo() {
        return mostrarFolioAlternativo;
    }

    public void setMostrarFolioAlternativo(boolean mostrarFolioAlternativo) {
        this.mostrarFolioAlternativo = mostrarFolioAlternativo;
    }

    public boolean isFunWsSiamActivo() {
        return funWsSiamActivo;
    }

    public void setFunWsSiamActivo(boolean funWsSiamActivo) {
        this.funWsSiamActivo = funWsSiamActivo;
    }

    public String getConSiamGeneraColectivo() {
        return conSiamGeneraColectivo;
    }

    public void setConSiamGeneraColectivo(String conSiamGeneraColectivo) {
        this.conSiamGeneraColectivo = conSiamGeneraColectivo;
    }

    public boolean isFunSolicitudXServicio() {
        return funSolicitudXServicio;
    }

    public void setFunSolicitudXServicio(boolean funSolicitudXServicio) {
        this.funSolicitudXServicio = funSolicitudXServicio;
    }

    public String getCon_siam_consultaFolioReceta() {
        return con_siam_consultaFolioReceta;
    }

    public void setCon_siam_consultaFolioReceta(String con_siam_consultaFolioReceta) {
        this.con_siam_consultaFolioReceta = con_siam_consultaFolioReceta;
    }

    public String getFunSiamConsultaDetalle() {
        return funSiamConsultaDetalle;
    }

    public void setFunSiamConsultaDetalle(String funSiamConsultaDetalle) {
        this.funSiamConsultaDetalle = funSiamConsultaDetalle;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public byte[] getReportStream() {
        return reportStream;
    }

    public void setReportStream(byte[] reportStream) {
        this.reportStream = reportStream;
    }

    public boolean isFunValidarFarmacoVigilancia() {
        return funValidarFarmacoVigilancia;
    }

    public void setFunValidarFarmacoVigilancia(boolean funValidarFarmacoVigilancia) {
        this.funValidarFarmacoVigilancia = funValidarFarmacoVigilancia;
    }

    public boolean isFunAsigAlmacenServicioMultiple() {
        return funAsigAlmacenServicioMultiple;
    }

    public void setFunAsigAlmacenServicioMultiple(boolean funAsigAlmacenServicioMultiple) {
        this.funAsigAlmacenServicioMultiple = funAsigAlmacenServicioMultiple;
    }

    public String getConSiamConsultaInsumoEnCT() {
        return conSiamConsultaInsumoEnCT;
    }

    public void setConSiamConsultaInsumoEnCT(String conSiamConsultaInsumoEnCT) {
        this.conSiamConsultaInsumoEnCT = conSiamConsultaInsumoEnCT;
    }

    public Integer getNumDiasCiclo() {
        return numDiasCiclo;
    }

    public void setNumDiasCiclo(Integer numDiasCiclo) {
        this.numDiasCiclo = numDiasCiclo;
    }

    public Integer getNumEtiquetasImpresaMezcla() {
        return numEtiquetasImpresaMezcla;
    }

    public void setNumEtiquetasImpresaMezcla(Integer numEtiquetasImpresaMezcla) {
        this.numEtiquetasImpresaMezcla = numEtiquetasImpresaMezcla;
    }

    public Integer getPasswordNumCaracter() {
        return passwordNumCaracter;
    }

    public void setPasswordNumCaracter(Integer passwordNumCaracter) {
        this.passwordNumCaracter = passwordNumCaracter;
    }

    public Integer getProtocoloVacioDefault() {
        return protocoloVacioDefault;
    }

    public void setProtocoloVacioDefault(Integer protocoloVacioDefault) {
        this.protocoloVacioDefault = protocoloVacioDefault;
    }

    public Integer getNumMinutosEntregaMezcla() {
        return numMinutosEntregaMezcla;
    }

    public void setNumMinutosEntregaMezcla(Integer numMinutosEntregaMezcla) {
        this.numMinutosEntregaMezcla = numMinutosEntregaMezcla;
    }

    public Integer getNumDiasMaxPrescMezcla() {
        return numDiasMaxPrescMezcla;
    }

    public void setNumDiasMaxPrescMezcla(Integer numDiasMaxPrescMezcla) {
        this.numDiasMaxPrescMezcla = numDiasMaxPrescMezcla;
    }

    public Integer getRecepcionManualPrellenada() {
        return recepcionManualPrellenada;
    }

    public void setRecepcionManualPrellenada(Integer recepcionManualPrellenada) {
        this.recepcionManualPrellenada = recepcionManualPrellenada;
    }

    public Double getNumMaxCompatibilidad() {
        return numMaxCompatibilidad;
    }

    public void setNumMaxCompatibilidad(Double numMaxCompatibilidad) {
        this.numMaxCompatibilidad = numMaxCompatibilidad;
    }

    public Double getFactorMolMonovaPotasio() {
        return factorMolMonovaPotasio;
    }

    public void setFactorMolMonovaPotasio(Double factorMolMonovaPotasio) {
        this.factorMolMonovaPotasio = factorMolMonovaPotasio;
    }

    public Double getFactorMolDivalZinc() {
        return factorMolDivalZinc;
    }

    public void setFactorMolDivalZinc(Double factorMolDivalZinc) {
        this.factorMolDivalZinc = factorMolDivalZinc;
    }

    public Double getFactorMolesDivalEstabilidad() {
        return factorMolesDivalEstabilidad;
    }

    public void setFactorMolesDivalEstabilidad(Double factorMolesDivalEstabilidad) {
        this.factorMolesDivalEstabilidad = factorMolesDivalEstabilidad;
    }

    public Double getNumMaxEstabilidad() {
        return numMaxEstabilidad;
    }

    public void setNumMaxEstabilidad(Double numMaxEstabilidad) {
        this.numMaxEstabilidad = numMaxEstabilidad;
    }

    public Double getNumMaxOsmolaridad() {
        return numMaxOsmolaridad;
    }

    public void setNumMaxOsmolaridad(Double numMaxOsmolaridad) {
        this.numMaxOsmolaridad = numMaxOsmolaridad;
    }

    public Integer getNumMinEditaCancela() {
        return numMinEditaCancela;
    }

    public void setNumMinEditaCancela(Integer numMinEditaCancela) {
        this.numMinEditaCancela = numMinEditaCancela;
    }

    public Integer getTipoInsumoValidaTratDuplicado() {
        return tipoInsumoValidaTratDuplicado;
    }

    public void setTipoInsumoValidaTratDuplicado(Integer tipoInsumoValidaTratDuplicado) {
        this.tipoInsumoValidaTratDuplicado = tipoInsumoValidaTratDuplicado;
    }

    public Integer getNoHrsPrevValidaTratDuplicado() {
        return noHrsPrevValidaTratDuplicado;
    }

    public void setNoHrsPrevValidaTratDuplicado(Integer noHrsPrevValidaTratDuplicado) {
        this.noHrsPrevValidaTratDuplicado = noHrsPrevValidaTratDuplicado;
    }

    public Integer getNoHrsPostValidaTratDuplicado() {
        return noHrsPostValidaTratDuplicado;
    }

    public void setNoHrsPostValidaTratDuplicado(Integer noHrsPostValidaTratDuplicado) {
        this.noHrsPostValidaTratDuplicado = noHrsPostValidaTratDuplicado;
    }

    public String getCorreoDestGralCenMez() {
        return correoDestGralCenMez;
    }

    public void setCorreoDestGralCenMez(String correoDestGralCenMez) {
        this.correoDestGralCenMez = correoDestGralCenMez;
    }

    public Integer getUsoRemanentes() {
        return usoRemanentes;
    }

    public void setUsoRemanentes(Integer usoRemanentes) {
        this.usoRemanentes = usoRemanentes;
    }

    public Integer getUsoRemanentesSoluciones() {
        return usoRemanentesSoluciones;
    }

    public void setUsoRemanentesSoluciones(Integer usoRemanentesSoluciones) {
        this.usoRemanentesSoluciones = usoRemanentesSoluciones;
    }

    public String getNptDefaultViaAdministracion() {
        return nptDefaultViaAdministracion;
    }

    public void setNptDefaultViaAdministracion(String nptDefaultViaAdministracion) {
        this.nptDefaultViaAdministracion = nptDefaultViaAdministracion;
    }

    public String getNptDefaultEnvaseContenedor() {
        return nptDefaultEnvaseContenedor;
    }

    public void setNptDefaultEnvaseContenedor(String nptDefaultEnvaseContenedor) {
        this.nptDefaultEnvaseContenedor = nptDefaultEnvaseContenedor;
    }

    public boolean isListaDiagnostico() {
        return listaDiagnostico;
    }

    public void setListaDiagnostico(boolean listaDiagnostico) {
        this.listaDiagnostico = listaDiagnostico;
    }

    public boolean isCapturaLoteCaducidadManual() {
        return capturaLoteCaducidadManual;
    }

    public void setCapturaLoteCaducidadManual(boolean capturaLoteCaducidadManual) {
        this.capturaLoteCaducidadManual = capturaLoteCaducidadManual;
    }

    public Integer getNoFarmacosPermitidos() {
        return noFarmacosPermitidos;
    }

    public void setNoFarmacosPermitidos(Integer noFarmacosPermitidos) {
        this.noFarmacosPermitidos = noFarmacosPermitidos;
    }

    public Integer getNoDiluyentesPermitidos() {
        return noDiluyentesPermitidos;
    }

    public void setNoDiluyentesPermitidos(Integer noDiluyentesPermitidos) {
        this.noDiluyentesPermitidos = noDiluyentesPermitidos;
    }
    
    public boolean isModuloCentralMezclas() {
        return moduloCentralMezclas;
    }

    public void setModuloCentralMezclas(boolean moduloCentralMezclas) {
        this.moduloCentralMezclas = moduloCentralMezclas;
    }

    public boolean isAgruparXPrescripcionAutorizar() {
        return agruparXPrescripcionAutorizar;
    }

    public void setAgruparXPrescripcionAutorizar(boolean agruparXPrescripcionAutorizar) {
        this.agruparXPrescripcionAutorizar = agruparXPrescripcionAutorizar;
    }

    public boolean isValidaExistencias1erToma() {
        return validaExistencias1erToma;
    }

    public void setValidaExistencias1erToma(boolean validaExistencias1erToma) {
        this.validaExistencias1erToma = validaExistencias1erToma;
    }

    public boolean isValidaExistencias1erDia() {
        return validaExistencias1erDia;
    }

    public void setValidaExistencias1erDia(boolean validaExistencias1erDia) {
        this.validaExistencias1erDia = validaExistencias1erDia;
    }

    public boolean isValidaExistenciasTomasTotales() {
        return validaExistenciasTomasTotales;
    }

    public void setValidaExistenciasTomasTotales(boolean validaExistenciasTomasTotales) {
        this.validaExistenciasTomasTotales = validaExistenciasTomasTotales;
    }
    
    public boolean isValidaExistenciasRestrictiva() {
        return validaExistenciasRestrictiva;
    }

    public void setValidaExistenciasRestrictiva(boolean validaExistenciasRestrictiva) {
        this.validaExistenciasRestrictiva = validaExistenciasRestrictiva;
    }
    
    public boolean isEnviaCorreoalValidarMezcla() {
        return enviaCorreoalValidarMezcla;
    }

    public void setEnviaCorreoalValidarMezcla(boolean enviaCorreoalValidarMezcla) {
        this.enviaCorreoalValidarMezcla = enviaCorreoalValidarMezcla;
    }

    //</editor-fold>

    
}
