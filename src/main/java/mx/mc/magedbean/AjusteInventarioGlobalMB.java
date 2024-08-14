package mx.mc.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import mx.mc.enums.TipoMotivo_Enum;
import mx.mc.enums.TipoTemplateEtiqueta_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.ClaveProveedorBarras;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.Estructura;
import mx.mc.model.EtiquetaInsumo;
import mx.mc.model.Fabricante;
import mx.mc.model.Impresora;
import mx.mc.model.Inventario;
import mx.mc.model.Medicamento;
import mx.mc.model.MovimientoInventario;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.ReportInventarioExistencias;
import mx.mc.model.TemplateEtiqueta;
import mx.mc.model.TipoBloqueo;
import mx.mc.model.TipoBloqueoMotivos;
import mx.mc.model.Usuario;
import mx.mc.service.ClaveProveedorBarrasService;
import mx.mc.service.EntidadHospitalariaService;
import mx.mc.service.EstructuraService;
import mx.mc.service.FabricanteService;
import mx.mc.service.ImpresoraService;
import mx.mc.service.InventarioService;
import mx.mc.service.MedicamentoService;
import mx.mc.service.ReporteMovimientosService;
import mx.mc.service.ReportesService;
import mx.mc.service.TemplateEtiquetaService;
import mx.mc.service.TipoBloqueoMotivosService;
import mx.mc.util.CodigoBarras;
import mx.mc.util.Comunes;
import mx.mc.util.FechaUtil;
import mx.mc.util.Mensaje;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author aortiz
 *
 */
@Controller
@Scope(value = "view")
public class AjusteInventarioGlobalMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(AjusteInventarioGlobalMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    private int sem1;
    private int sem2;
    private int cantidadCero;
    private Boolean buscaCantidadCero;
    private String itemPirnt;
    private Boolean activeBtnPrint;
    private String idPrintSelect;
    private Integer cantPrint;
    private Integer numTemp;

    private ParamBusquedaReporte paramBusquedaReporte;
    private List<Estructura> listAlmacenesSubAlm;
    private List<Estructura> listaAuxiliar;
    private String idEstructura;
    private Usuario usuarioSession;

    private List<ReportInventarioExistencias> listReportInventarioExistencias;
    private ReportInventarioExistencias registroSelect;
    private boolean permiteAjusteInventarioGlobal;
    private boolean parametrosemaforo;

    private String template;
    private List<TemplateEtiqueta> templateList;

    private Impresora impresoraSelect;
    private List<Impresora> listaImpresoras;

    private String lote;
    private String claveInstitucional;
    private String fechacaducidad;
    private String idMedicamento;
    private Medicamento claveMedicamentos;
    private boolean desabilitado;
    private Date fecha;
    private String comentarios;
    private boolean edit;
    private String bloqueo;
    private int activo;
    private Usuario usuarioSelect;
    private List<Medicamento> listaMedicamento;
    private List<TipoBloqueoMotivos> listTipoBloqueoMotivos;
    private List<Inventario> listaLotes;
    private List<Inventario> listaFechas;
    private List<Inventario> listaEstructura;
    private List<String> tipoBloqueoSelects;
    private String tipoBloqueo;
    private String tipobloq;
    private int idTipoBloqueoMotivos;
    private Integer idTipoBloqueo;
    private Inventario idInsumo;
    private Date fechaid;
    private int statusInsum;
    private List<Inventario> listidinventarioSelect;
    private String estructura;
    private String codigoBarras;
    private String newCodBarInsert;
    private List<ClaveProveedorBarras> listaCodigoBarras;
    private List<ClaveProveedorBarras> listaProveedores;
    private List<ClaveProveedorBarras> listaCodigoBarrasProveedor;
    private boolean status;
    private String claveProveedor;
    private String claveProveedorCmb;
    private boolean isAdmin;
    private boolean isJefeArea;
    private boolean utilizarCantidadComprometida;
    private boolean gestionInsumosAutocomplete;
    private boolean registrarOrigenInsumos;
    private PermisoUsuario permiso;

    @Autowired
    private transient TemplateEtiquetaService templateService;

    @Autowired
    private transient ImpresoraService impresoraService;

    @Autowired
    private transient EstructuraService estructuraService;

    @Autowired
    private transient MedicamentoService medicamentoService;

    @Autowired
    private transient ReporteMovimientosService reporteMovimientosService;

    @Autowired
    private transient ReportesService reportesService;

    @Autowired
    private transient EntidadHospitalariaService entidadHospitalariaService;

    @Autowired
    private transient InventarioService inventarioService;

    @Autowired
    private transient TipoBloqueoMotivosService tipoBloqueoMotivosService;

    @Autowired
    private transient ClaveProveedorBarrasService claveProveedorBarrasService;

    @Autowired
    private transient FabricanteService fabricanteService;

    private transient List<Fabricante> listaFabricante;
    private Fabricante fabricante;
    private Integer idFabricante;
    private Inventario inventarioSelect;

    /**
     * Metodo que se ejecuta despues de cargar la pantalla de pacientes
     */
    @PostConstruct
    public void init() {
        LOGGER.trace("mx.mc.magedbean.AjusteInventarioGlobalMB.init()");
        try {
            initialize();
            obtienePerfil();
            permiso = Comunes.obtenerPermisos(Transaccion_Enum.AJUSTE_INVGLOB.getSufijo());
            obtieneAlmacenes();
            obtenerTemplatesEtiquetas();
            obtienelistaTipoBloqueo();

        } catch (Exception e) {
            LOGGER.error("Error en el metodo init :: {}", e.getMessage());
        }
    }

    /**
     * Metodo que inicializa todos los atributos de la clase
     */
    private void initialize() {
        try {
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");

            sem1 = sesion.getParametroSemaforo1();
            sem2 = sesion.getParametroSemaforo2();
            permiteAjusteInventarioGlobal = sesion.isPermiteAjusteInventarioGlobal();
            parametrosemaforo = sesion.isParametrosemaforo();
            cantidadCero = 0; //sesion.getCantidadCero();
            buscaCantidadCero = false;

            this.usuarioSession = sesion.getUsuarioSelected();

            this.utilizarCantidadComprometida = sesion.isUtilizarCantidadComprometida();
            this.gestionInsumosAutocomplete = sesion.isGestionInsumosAutocomplete();
            this.registrarOrigenInsumos = sesion.isRegistrarOrigenInsumos();

            this.paramBusquedaReporte = new ParamBusquedaReporte();
            this.listAlmacenesSubAlm = new ArrayList<>();
            this.idEstructura = null;
            templateList = new ArrayList<>();
            numTemp = 0;
            idPrintSelect = null;
            activeBtnPrint = Constantes.INACTIVO;

            this.lote = StringUtils.EMPTY;
            this.fechacaducidad = null;
            this.medi = null;
            this.tipobloq = StringUtils.EMPTY;
            this.tipoBloqueo = StringUtils.EMPTY;
            this.listaMedicamento = new ArrayList<>();
            this.listaLotes = new ArrayList<>();
            this.listaFechas = new ArrayList<>();
            this.claveInstitucional = StringUtils.EMPTY;
            this.comentarios = StringUtils.EMPTY;;

            template = StringUtils.EMPTY;

            this.listaFabricante = obtenerListaFabricante();

        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener la lista de almacenes y subalmacenes: {}", ex.getMessage());
        }

    }

    private List<Fabricante> obtenerListaFabricante() {
        List<Fabricante> listaFabricante = new ArrayList<>();
        try {
            Fabricante f = new Fabricante();
            f.setIdEstatus(Constantes.ACTIVOS);
            listaFabricante = fabricanteService.obtenerLista(f);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener lista de Fabricantes; {}", ex.getMessage());
        }
        return listaFabricante;
    }

    /**
     * Obtiene el perfil del usuario en sesión
     */
    private void obtienePerfil() {
        LOGGER.trace("mx.mc.magedbean.AjusteInventarioGlobalMB.obtienePerfil()");
        if (usuarioSession != null) {
            isAdmin = Comunes.isAdministrador();
            isJefeArea = Comunes.isJefeArea();
        }
    }

    /**
     * Historial: 26dic2019 hramirez -> Se cambia la validación de si es admini
     * o jefe de área en función del campo usuarios.administrador
     *
     */
    private void obtieneAlmacenes() {
        LOGGER.trace("mx.mc.magedbean.SolicitudReabastoMB.obtieneAlmacen()");
        try {
            if (isAdmin) {
// Si es administrador puede listar todos los almacenes activos
                this.listAlmacenesSubAlm.addAll(estructuraService.obtenerAlmacenesActivos());
                idEstructura = null;

            } else if (isJefeArea) {
                if (this.usuarioSession.getIdEstructura() != null && !this.usuarioSession.getIdEstructura().isEmpty()) {
                    // ñista todos los servicios que surten de manera diorecta mi almacen
                    List<Estructura> almacenesQueSurtenServicioDeUsuarioSesion = estructuraService.obtenerAlmacenesQueSurtenServicio(this.usuarioSession.getIdEstructura());
                    if (almacenesQueSurtenServicioDeUsuarioSesion.isEmpty()) {
                        // Si es Jefe de área lista su almaén asignado mas sus almacenes dependientes                
                        List<String> listaIdAlmaceneshijos = estructuraService.obtenerIdsEstructuraJerarquica(this.usuarioSession.getIdEstructura(), true);
                        listaIdAlmaceneshijos.add(this.usuarioSession.getIdEstructura());
                        this.listAlmacenesSubAlm.addAll(estructuraService.obtenerEstructurasActivosPorId(listaIdAlmaceneshijos));
                        idEstructura = usuarioSession.getIdEstructura();
                    } else {
                        this.listAlmacenesSubAlm.addAll(almacenesQueSurtenServicioDeUsuarioSesion);
                    }
                }

            } else {
// Si es usuario normal operativo sólo lista su propio almacen
                this.listAlmacenesSubAlm.add(estructuraService.obtener(new Estructura(usuarioSession.getIdEstructura())));
                idEstructura = usuarioSession.getIdEstructura();
            }
        } catch (Exception ex) {
            LOGGER.error("Error al obtener Estructura: {}", ex.getMessage());
        }
    }

    /**
     * Obtiene la lista de templates de impresión de etiquetas
     */
    private void obtenerTemplatesEtiquetas() {
        try {
            templateList = templateService.obtenerListaTipo(TipoTemplateEtiqueta_Enum.E.getValue());
            numTemp = templateList.size();
// TODO: 12Mar2020 Permitir la selección de templates
            if (numTemp == 1) {
                template = templateList.get(0).getContenido();
            }
        } catch (Exception ex) {
            LOGGER.error("Error al obtener los templates :: {}", ex.getMessage());
        }
    }

    /**
     * Metodo para realizar la consulta desde la vista
     *
     */
    public void consultar() {
        LOGGER.trace("mx.mc.magedbean.AjusteInventarioGlobalMB.consultar()");
        DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("mainForm:tablaInvent");
        dataTable.resetValue();
        try {
            this.paramBusquedaReporte.setCantidadCero(cantidadCero);
            if (this.idEstructura != null) {
                this.paramBusquedaReporte.setIdEstructura(this.idEstructura);
            } else {
                if (!this.isAdmin) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("ajustInven.err.seleccioneEstructura"), null);
                    return;
                }
            }

            this.listReportInventarioExistencias = reporteMovimientosService.consultarInventarioExistenciasGlobal(paramBusquedaReporte);
            LOGGER.debug("Resultados: {}", this.listReportInventarioExistencias.size());
        } catch (Exception e1) {
            LOGGER.error("Error al consultar registros para ajuste inventario global: {}", e1.getMessage());
        }
    }

    /**
     * Modifica el manejo de un insusmo de dosis unitaria a Presentación
     * comercial
     *
     * @param idInventario
     * @param idInsumo
     * @param idEstructura
     * @param cantidadXCaja
     * @param lote
     * @param claveProveedor
     * @param presentacionComercial
     * @param cantidadActual
     * @param factorTransformacion
     */
    public void alterarUnidosis(String idInventario, String idInsumo, String idEstructura, int cantidadXCaja, String lote, String claveProveedor, int presentacionComercial, int cantidadActual, int factorTransformacion) {
        LOGGER.trace("mx.mc.magedbean.AjusteInventarioGlobalMB.alterarUnidosis()");
        boolean estatus = Constantes.INACTIVO;
        try {
            if (permiso.isPuedeProcesar()) {
                int nuevaPresentacionComercial = (presentacionComercial != 1) ? 1 : 0;
                int nuevaCantidadXCaja = (presentacionComercial != 1) ? factorTransformacion : 1;

                Inventario in = inventarioService.validarExistInventGlob(idInsumo, idEstructura, lote, cantidadXCaja, claveProveedor, nuevaPresentacionComercial);
                if (in == null) {
                    estatus = inventarioService.actualizarInventarioPresentCom(idInventario, nuevaPresentacionComercial, nuevaCantidadXCaja);
                    if (estatus) {
                        Mensaje.showMessage(Constantes.MENSAJE_INFO, "Registro Actualizado Correctamente", null);
                        consultar();
                    } else {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al Actualizar los Datos", null);
                    }

                } else {
                    int cantidadInactivar = 0;

                    in.setCantidadActual(in.getCantidadActual() + cantidadActual);
                    in.setUpdateFecha(new java.util.Date());
                    in.setUpdateIdUsuario(usuarioSession.getIdUsuario());

                    //Creación del segundo objeto de inventario para inactivar el inventario 
                    Inventario inventarioInactivar = new Inventario();
                    inventarioInactivar.setIdInventario(idInventario);
                    inventarioInactivar.setCantidadActual(cantidadInactivar);
                    inventarioInactivar.setUpdateFecha(new java.util.Date());
                    inventarioInactivar.setUpdateIdUsuario(usuarioSession.getIdUsuario());

                    MovimientoInventario movInventario = new MovimientoInventario();
                    movInventario.setIdMovimientoInventario(Comunes.getUUID());
                    movInventario.setFecha(new java.util.Date());
                    movInventario.setIdUsuarioMovimiento(this.usuarioSession.getIdUsuario());
                    movInventario.setIdTipoMotivo(TipoMotivo_Enum.MODIFICACION_CANTIDAD_ACTUAL.getMotivoValue());
                    movInventario.setIdInventario(idInventario);
                    movInventario.setCantidad(cantidadInactivar);
                    movInventario.setIdEstrutcuraOrigen(in.getIdEstructura());
                    movInventario.setIdEstrutcuraDestino(in.getIdEstructura());

                    estatus = inventarioService.actualizarInventarioCantidadActual(in, inventarioInactivar, movInventario);
                    if (estatus) {
                        Mensaje.showMessage(Constantes.MENSAJE_INFO, "Registro Actualizado Correctamente", null);
                        consultar();
                    } else {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al Actualizar los Datos", null);
                    }
                }
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estr.err.permisos"), null);
                return;
            }
        } catch (Exception e) {
            LOGGER.error("Error al modificar la presentación del insumos DU y PC: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, estatus);
    }

    /**
     * Preimpresión de etiqueta
     *
     * @param item
     */
    public void previewPrint(ReportInventarioExistencias item) {
        LOGGER.trace("mx.mc.magedbean.AjusteInventarioGlobalMB.previewPrint()");
        try {
            cantPrint = 1;
            registroSelect = item;
            itemPirnt = item.getNombreMedicamento();
            listaImpresoras = impresoraService.obtenerListaImpresoraByUsuario(usuarioSession.getIdUsuario());

        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener datos del usuario: {}", ex.getMessage());
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

    public void cancelImprimirEtiquetaInvGlobal() {
        try {
            idPrintSelect = null;
            cantPrint = 1;
            activeBtnPrint = Constantes.INACTIVO;
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al cancelar la impresión InvGlobal: {}", ex.getMessage());
        }
    }

    public void imprimirEtiquetaItem() throws Exception {
        boolean estatus = false;
        try {
            if (registroSelect != null) {
                Impresora print = impresoraService.obtenerPorIdImpresora(idPrintSelect);
                if (print != null && !"".equals(template)) {
                    TemplateEtiqueta o = templateService.obtenerById(template);
                    if (o != null && o.getContenido() != null) {
                        EtiquetaInsumo etiqueta = new EtiquetaInsumo();
                        etiqueta.setCaducidad(registroSelect.getFechaCaducidad());
                        etiqueta.setOrigen(registroSelect.getNombreOrigen());
                        etiqueta.setLaboratorio("");
                        etiqueta.setFotosencible("");
                        etiqueta.setTextoInstitucional("");
                        etiqueta.setDescripcion(registroSelect.getNombreMedicamento());
                        etiqueta.setLote(registroSelect.getLote());
                        etiqueta.setClave(registroSelect.getClaveMedicamento());
                        etiqueta.setTemplate(o.getContenido());

                        etiqueta.setConcentracion(registroSelect.getConcentracion());
                        etiqueta.setCodigoQR(CodigoBarras.generaCodigoDeBarras(registroSelect.getClaveMedicamento(), registroSelect.getLote(), registroSelect.getFechaCaducidad(), null));

                        etiqueta.setCantiad(cantPrint);
                        etiqueta.setIpPrint(print.getIpImpresora());

                        estatus = reportesService.imprimirEtiquetaItem(etiqueta);
                        if (estatus) {
                            Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("paciente.info.impCorrectamente"), null);
                        } else {
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("paciente.err.genimpresion"), null);
                        }
                    }
                }
            }
        } catch (Exception e) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("paciente.err.conect"), null);
            LOGGER.error("Error al generar la Impresión: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, estatus);
    }
    
    public void obtieneIdInventario(ReportInventarioExistencias item) {
        LOGGER.trace("mx.mc.magedbean.AjusteInventarioGlobalMB.obtieneIdInventario()");
        boolean res = false;
        try {
            registroSelect = item;
            String idInventario = item.getIdInventario();
            if (idInventario == null){
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Identificador de Inventario inválido.", null);
            } else {
                inventarioSelect = inventarioService.obtener(new Inventario(idInventario));
                if (inventarioSelect!= null) {
                    idFabricante = inventarioSelect.getIdFabricante();
                    res = true;
                }
            }
        } catch (Exception ex) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al obtener el Fabricante.", null);
            LOGGER.error("Error al consultar Codigo Proveedor: {}", ex.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam("estatus", res);
    }
    

    public void actualizarFabricante(){
        LOGGER.trace("mx.mc.magedbean.AjusteInventarioGlobalMB.actualizarFabricante()");
        boolean estatus = false;
        try {
            if (!permiso.isPuedeAutorizar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No tiene permisos para esta acción.", null);
            
            } else if (inventarioSelect == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Identificador de Inventario inválido.", null);
            
            } else if (inventarioSelect.getIdInventario() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Identificador de Inventario inválido.", null);
                
            } else {
                inventarioSelect.setIdFabricante(idFabricante);
                inventarioSelect.setUpdateFecha(new java.util.Date());
                inventarioSelect.setUpdateIdUsuario(usuarioSession.getIdUsuario());
                estatus = inventarioService.actualizarPorId(inventarioSelect);
                if (estatus) {
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, "Fabricando guardado correctamente.", null);
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al guardar el Fabricante.", null);
                }
            }

        } catch (Exception e) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al guardar el Fabricante.", null);
            LOGGER.error("Error al generar la Impresión: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, estatus);
    }

    /**
     * Consulta de insumos por cadena de texto mayor a 3 caracteres y retorna
     * las coincidencias
     *
     * @param query
     * @return
     */
    public List<Medicamento> autocompleteInsumo(String query) {
        LOGGER.trace("mx.mc.magedbean.AjusteInventarioGlobalMB.autocompleteInsumo()");
        List<Medicamento> insumosList = new ArrayList<>();
        try {
            insumosList.addAll(medicamentoService.obtenerInsumos(query));

        } catch (Exception ex) {
            LOGGER.error("Ocurrió un error al consultar el medicamento: {}", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("ajustInven.err.obtMedicamAutocompl"), null);
        }
        return insumosList;
    }

    /**
     * Exporta a PDF la lista de insumos
     *
     * @param actionEvent
     */
    public void imprimeReporteExistencias() {
        LOGGER.trace("mx.mc.magedbean.AjusteInventarioGlobalMB.imprimeReporteExistencias()");
        boolean estatus = false;
        byte[] buffer = null;
        try {

            this.paramBusquedaReporte.setCantidadCero(cantidadCero);
            if (this.idEstructura != null) {
                this.paramBusquedaReporte.setIdEstructura(this.idEstructura);
            } else {
                if (!this.isAdmin) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("ajustInven.err.seleccioneEstructura"), null);
                    return;
                }
            }

            Estructura e = estructuraService.obtenerEstructura(this.idEstructura);
            this.paramBusquedaReporte.setNombreEstructura(e.getNombre());

            EntidadHospitalaria ent = new EntidadHospitalaria();
            ent.setIdEntidadHospitalaria(e.getIdEntidadHospitalaria());
            EntidadHospitalaria entidad = entidadHospitalariaService.obtener(ent);

            buffer = reportesService.imprimeReporteExistencias(this.paramBusquedaReporte, entidad, permiteAjusteInventarioGlobal);
            if (buffer != null) {
                estatus = true;
                SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "sesionMB");
                sesion.setReportStream(buffer);
                sesion.setReportName(String.format("existenciasInventario_%s.pdf", FechaUtil.formatoFecha(new Date(), "yyyyMMdd")));
            }
        } catch (Exception e) {
            LOGGER.error("Error al generar la Impresión PDF: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, estatus);
    }

    /**
     * Exporta a PDF la lista de insumos
     *
     */
    public void generaExcelExistencia() {
        LOGGER.trace("mx.mc.magedbean.AjusteInventarioGlobalMB.generaExcelExistencia()");
        boolean estatus = Constantes.INACTIVO;
        try {
            this.paramBusquedaReporte.setCantidadCero(cantidadCero);
            if (this.idEstructura != null) {
                this.paramBusquedaReporte.setIdEstructura(this.idEstructura);
            } else {
                if (!this.isAdmin) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("ajustInven.err.seleccioneEstructura"), null);
                    return;
                }
            }

            Estructura etr = estructuraService.obtenerEstructura(this.idEstructura);
            this.paramBusquedaReporte.setNombreEstructura(etr.getNombre());

            EntidadHospitalaria eh = new EntidadHospitalaria();
            eh.setIdEntidadHospitalaria(etr.getIdEntidadHospitalaria());
            EntidadHospitalaria entidad = entidadHospitalariaService.obtener(eh);

            estatus = reportesService.imprimeExcelExistencias(paramBusquedaReporte, entidad);

        } catch (Exception e) {
            LOGGER.error("Error al generar Excel: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, estatus);
    }

    /**
     * Actualiza en base de datos los cambios registrados en las celdas lote,
     * caducidad cantidad actual
     *
     * @param event
     */
    public void onCellEdit(CellEditEvent event) {
        LOGGER.trace("mx.mc.magedbean.AjusteInventarioGlobalMB.onCellEdit()");
        boolean estatus = false;
        String cadena = "";
        Integer entero = 0;
        Date fechaa = new java.util.Date();
        String idInventario = event.getRowKey();

        try {
// actualiza Lote
            if (event.getOldValue().getClass() == cadena.getClass()) {
                String oldValue = (String) event.getOldValue();
                String newValue = (String) event.getNewValue();
                if (!newValue.equals("") && !newValue.equals(oldValue)) {
                    Inventario inventario = inventarioService.obtener(new Inventario(idInventario));
                    inventario.setLote(newValue);
                    inventario.setUpdateIdUsuario(usuarioSession.getIdUsuario());
                    inventario.setUpdateFecha(new java.util.Date());

                    MovimientoInventario movInventario = new MovimientoInventario();
                    movInventario.setIdMovimientoInventario(Comunes.getUUID());
                    movInventario.setFecha(inventario.getUpdateFecha());
                    movInventario.setIdUsuarioMovimiento(this.usuarioSession.getIdUsuario());
                    movInventario.setIdTipoMotivo(TipoMotivo_Enum.MODIFICACION_LOTE.getMotivoValue());
                    movInventario.setIdInventario(idInventario);
                    movInventario.setCantidad(0);
                    movInventario.setIdEstrutcuraOrigen(inventario.getIdEstructura());
                    movInventario.setIdEstrutcuraDestino(inventario.getIdEstructura());

                    estatus = inventarioService.actualizarInventarioGlobalLote(inventario, movInventario);
                    if (estatus) {
                        consultar();
                        Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("ajustInven.info.actualizaLote"), null);
                    } else {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("ajustInven.err.actualizaLote"), null);
                    }
                }
// actualiza cantidad actual
            } else if (event.getOldValue().getClass() == entero.getClass()) {
                Integer oldValue = (Integer) event.getOldValue();
                Integer newValue = (Integer) event.getNewValue();
                if (newValue != null && !newValue.equals(oldValue) && newValue >= 0) {
                    Inventario inventario = inventarioService.obtener(new Inventario(idInventario));
                    inventario.setCantidadActual(newValue);
                    inventario.setUpdateFecha(new Date());
                    inventario.setEnviarAVG(Constantes.ESTATUS_ACTIVO);
                    inventario.setUpdateIdUsuario(this.usuarioSession.getIdUsuario());

                    MovimientoInventario movInventario = new MovimientoInventario();
                    movInventario.setIdMovimientoInventario(Comunes.getUUID());
                    if (oldValue > newValue) {
                        movInventario.setIdTipoMotivo(TipoMotivo_Enum.SAL_AJUSTE_INVENTARIO.getMotivoValue());
                        movInventario.setCantidad(oldValue - newValue);
                    } else {
                        movInventario.setIdTipoMotivo(TipoMotivo_Enum.ENT_AJUSTEINVENTARIO.getMotivoValue());
                        movInventario.setCantidad(newValue - oldValue);
                    }
                    movInventario.setFecha(new Date());
                    movInventario.setIdUsuarioMovimiento(this.usuarioSession.getIdUsuario());
                    movInventario.setIdEstrutcuraOrigen(inventario.getIdEstructura());
                    movInventario.setIdEstrutcuraDestino(inventario.getIdEstructura());
                    movInventario.setIdInventario(idInventario);

                    estatus = inventarioService.actualizarInventarioGlobal(inventario, movInventario);
                    if (estatus) {
                        consultar();
                        Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("ajustInven.info.actualizaCantidad"), null);
                    } else {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("ajustInven.err.actualizaCantidad"), null);
                    }
                }
// actualiza caducidad
            } else if (event.getOldValue().getClass() == fechaa.getClass()) {
                Date oldValue = (Date) event.getOldValue();
                Date newValue = (Date) event.getNewValue();

                if (!newValue.equals(oldValue)) {
                    Inventario inventario = inventarioService.obtener(new Inventario(idInventario));
                    inventario.setFechaCaducidad(newValue);
                    inventario.setUpdateIdUsuario(usuarioSession.getIdUsuario());
                    inventario.setUpdateFecha(new java.util.Date());

                    List<MovimientoInventario> listaInventario = new ArrayList<>();
                    MovimientoInventario movInventarioAnterior = new MovimientoInventario();
                    movInventarioAnterior.setIdMovimientoInventario(Comunes.getUUID());
                    movInventarioAnterior.setFecha(inventario.getUpdateFecha());
                    movInventarioAnterior.setIdUsuarioMovimiento(this.usuarioSession.getIdUsuario());
                    movInventarioAnterior.setIdTipoMotivo(TipoMotivo_Enum.MODIFICACION_CADUCIDAD.getMotivoValue());
                    movInventarioAnterior.setIdEstrutcuraOrigen(inventario.getIdEstructura());
                    movInventarioAnterior.setIdEstrutcuraDestino(inventario.getIdEstructura());
                    movInventarioAnterior.setIdInventario(idInventario);
                    movInventarioAnterior.setFechaCaducidad(oldValue);
                    listaInventario.add(movInventarioAnterior);

                    MovimientoInventario movInventario = new MovimientoInventario();
                    movInventario.setIdMovimientoInventario(Comunes.getUUID());
                    movInventario.setFecha(inventario.getUpdateFecha());
                    movInventario.setIdUsuarioMovimiento(this.usuarioSession.getIdUsuario());
                    movInventario.setIdTipoMotivo(TipoMotivo_Enum.MODIFICACION_CADUCIDAD_SALI.getMotivoValue());
                    movInventario.setIdEstrutcuraOrigen(inventario.getIdEstructura());
                    movInventario.setIdEstrutcuraDestino(inventario.getIdEstructura());
                    movInventario.setIdInventario(idInventario);
                    movInventario.setFechaCaducidad(newValue);
                    listaInventario.add(movInventario);
                    estatus = inventarioService.actualizarInventarioGlobalCaducidad(inventario, listaInventario);
                    if (estatus) {
                        consultar();
                        Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("ajustInven.info.actualizaCaducidad"), null);
                    } else {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("ajustInven.err.actualizaCaducidad"), null);
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error(" Error al actualizar registro de inventario: {}", ex.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, estatus);
    }

    public List<Estructura> getListAlmacenesSubAlm() {
        return listAlmacenesSubAlm;
    }

    public Usuario getUsuarioSession() {
        return usuarioSession;
    }

    public void setUsuarioSession(Usuario usuarioSession) {
        this.usuarioSession = usuarioSession;
    }

    public List<ReportInventarioExistencias> getListReportInventarioExistencias() {
        return listReportInventarioExistencias;
    }

    public void setListReportInventarioExistencias(List<ReportInventarioExistencias> listReportInventarioExistencias) {
        this.listReportInventarioExistencias = listReportInventarioExistencias;
    }

    public ReportInventarioExistencias getRegistroSelect() {
        return registroSelect;
    }

    public void setRegistroSelect(ReportInventarioExistencias registroSelect) {
        this.registroSelect = registroSelect;
    }

    public int getSem1() {
        return sem1;
    }

    public void setSem1(int sem1) {
        this.sem1 = sem1;
    }

    public int getSem2() {
        return sem2;
    }

    public void setSem2(int sem2) {
        this.sem2 = sem2;
    }

    public void limpiarDialogo() {
        try {
            if (!this.permiso.isPuedeEditar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.transaccion"), null);
            } else {
                this.medi = null;
                this.listaMedicamento = new ArrayList<>();
                this.listaLotes = new ArrayList<>();
                this.listaFechas = new ArrayList<>();
                this.claveInstitucional = StringUtils.EMPTY;
                this.lote = StringUtils.EMPTY;
                this.fechacaducidad = null;
                this.comentarios = StringUtils.EMPTY;;
                this.tipobloq = StringUtils.EMPTY;
                this.tipoBloqueo = StringUtils.EMPTY;
                this.activo = 0;
                this.estructura = StringUtils.EMPTY;
                this.claveProveedor = StringUtils.EMPTY;
                this.newCodBarInsert = StringUtils.EMPTY;
                this.claveProveedorCmb = StringUtils.EMPTY;

            }
        } catch (Exception e) {
            LOGGER.error("Error al limpiar claves por inactivar. {}", e.getMessage());
        }
    }

    /**
     * AutoComplete para Lista de Medicamentos
     *
     * @param query
     * @return
     */
    public List<Medicamento> autoCompletarCodigoMedicamento(String query) {
        try {
            this.listaMedicamento = medicamentoService.obtenerInsumos(query);
        } catch (Exception e) {
            LOGGER.error("Error en el metodo autoCompletarCodigoMedicamento :: {}", e.getMessage());
        }
        return this.listaMedicamento;
    }
    /**
     *
     * Obtiene lista de Lotes en base a clavemedicamento seleccionado.
     *
     * @param event
     */
    private Medicamento medi;
    boolean loteActivo;

    public void onItemSelect(SelectEvent event) {
        LOGGER.trace("AjusteInventarioGlobalMB.onItemSelect");
        try {
            this.medi = (Medicamento) event.getObject();
            if (this.medi.getClaveInstitucional() != null) {
                this.listaEstructura = inventarioService.listaMedicamentoEstructura(this.medi.getClaveInstitucional());
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo onItemSelect :: {}", e.getMessage());
        }
    }

    public void onSelectEstructura() {
        LOGGER.trace("AjusteInventarioGlobalMB.SelectEstructura");
        try {
            if (this.medi.getClaveInstitucional() != null) {
                this.listaLotes = inventarioService.listaLotesPorClaveInst(this.medi.getClaveInstitucional(), estructura);
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo onItemSelect :: {}", e.getMessage());
        }
    }

    /**
     * Obtine Lista de Fechas de Caducidad en Base a Claver y Lote Selecionado
     */
    public void onItemSelectLote() {
        LOGGER.trace("InsumoActivoInactivoMB.onItemSelectLote");
        try {
            if (medi != null && medi.getClaveInstitucional() != null && !medi.getClaveInstitucional().trim().isEmpty() && this.lote != null && !this.lote.trim().isEmpty()) {
                this.listaFechas = inventarioService.listaFechasCaducidad1(medi.getClaveInstitucional(), this.lote, estructura);
                statusInsum = this.listaFechas.get(0).getActivo();
                activo = this.listaFechas.get(0).getActivo();
            } else {
                this.listaFechas = new ArrayList<>();
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo onItemSelectLote : {}", e.getMessage());
        }
    }

    /**
     * Obtiene Lista de Datos de lote y caducidad Selecionado
     */
    public void onSelectFechaCaduciadad() {
        LOGGER.trace("InsumoActivoInactivoMB.onItemSelectFecha");
        try {
            this.activo = listaFechas.get(0).getActivo();
            this.statusInsum = listaFechas.get(0).getActivo();
            fecha = listaFechas.get(0).getFechaCaducidad();
        } catch (Exception e) {
            LOGGER.error("Error en el metodo onItemSelectCaducidad: {}", e.getMessage());
        }
    }

    /**
     * Lista de Tipos de Bloqueo.
     */
    private void obtienelistaTipoBloqueo() {
        try {
            listTipoBloqueoMotivos = tipoBloqueoMotivosService.obtieneListaByIdTipoBloqueo(Constantes.ACTIVOS);
        } catch (Exception ex) {
            LOGGER.info("Error al buscar la lista de Bloqueo: {}", ex.getMessage());
        }
    }

    /**
     * Metodo para Activar y Desactivar Insumo
     */
    public void desactivarInsumo() {
        try {

            if (activo > 0) {
                this.activo = Constantes.ES_INACTIVO;
            } else {
                this.activo = Constantes.ES_ACTIVO;
            }
        } catch (Exception ex) {
            LOGGER.error("Error al actualizar status Medicamento: {}", ex.getMessage());
        }
    }

    /**
     * Metodo para Guardar Cambios de insumos activos e inactivos
     */
    public void activarInactivarInsumos() {
        LOGGER.trace("mx.mc.magedbean.AjusteInventarioGlobalMB.activarInactivarInsumos()");
        boolean estatus = Constantes.INACTIVO;
        try {
            //validaciones 
            if (this.medi == null || this.medi.getClaveInstitucional() == null
                    || this.medi.getClaveInstitucional().trim().isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("blok.ins.err.clave"), null);
            } else if (this.estructura == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("blok.ins.err.estructura"), null);
            } else if (this.statusInsum == this.activo) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("blok.ins.err.activo"), null);
            } else if (this.activo == 0 && tipobloq == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("blok.ins.err.tipobloq"), null);
            } else if (this.activo == 1 && tipobloq == null && lote == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("blok.ins.err.lote"), null);
            } else if (this.comentarios == null || this.comentarios.trim().isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("blok.ins.err.comentario"), null);
            } else {
// TODO : 12Mar2020  corregir la busqueda, si se inactiva lote la busqueda en el inventario requiere mas datos
                Inventario inventarioSelected = inventarioService.obtenerIdInsumoByClave(this.medi.getClaveInstitucional(), this.lote, this.fecha, estructura);
                Inventario invt = new Inventario();
                invt.setActivo(activo);
                invt.setUpdateFecha(new Date());
                invt.setUpdateIdUsuario(this.usuarioSession.getIdUsuario());
                invt.setIdInsumo(inventarioSelected.getIdInsumo());
                if (lote != null) {
                    invt.setLote(lote);
                }
                if (fechacaducidad != null) {
                    invt.setFechaCaducidad(fecha);
                }
                //objeto Tipo Bloqueo
                TipoBloqueo tipobloqueo = new TipoBloqueo();
                tipobloqueo.setIdTipoBloqueo(getIdTipoBloqueo());
                tipobloqueo.setClaveInstitucional(this.medi.getClaveInstitucional());
                tipobloqueo.setLote(lote);
                tipobloqueo.setTipoBloqueo(tipobloq);
                tipobloqueo.setComentarios(comentarios);
                tipobloqueo.setActivo(activo);
                tipobloqueo.setInsertFecha(new Date());
                tipobloqueo.setInsertIdUsuario(this.usuarioSession.getIdUsuario());
                tipobloqueo.setUpdateFecha(new java.util.Date());
                tipobloqueo.setUpdateIdUsuario(usuarioSession.getIdUsuario());

                MovimientoInventario movInventario = new MovimientoInventario();
                movInventario.setIdMovimientoInventario(Comunes.getUUID());
                movInventario.setFecha(new java.util.Date());
                movInventario.setIdUsuarioMovimiento(this.usuarioSession.getIdUsuario());
                movInventario.setIdTipoMotivo(TipoMotivo_Enum.AJUSTE_POR_CONTINGENCIA.getMotivoValue());
                movInventario.setIdInventario(inventarioSelected.getIdInventario());
                movInventario.setCantidad(inventarioSelected.getCantidadActual());
                movInventario.setIdEstrutcuraOrigen(inventarioSelected.getIdEstructura());
                movInventario.setIdEstrutcuraDestino(inventarioSelected.getIdEstructura());

                estatus = inventarioService.actualizarInventarioBloqueos(invt, tipobloqueo, movInventario);
                if (!estatus) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("blok.ins.err.bloqueo"), null);
                } else {
                    Mensaje.showMessage("info", RESOURCES.getString("blok.ins.ok.bloqueo"), null);
                    consultar();
                }

            }

        } catch (Exception e) {
            LOGGER.error("Error en el metodo guardarCambios : {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, estatus);
    }

    public void obtienelistaCodigoBarras(ReportInventarioExistencias item) {
        LOGGER.trace("mx.mc.magedbean.AjusteInventarioGlobalMB.obtienelistaCodigoBarras()");
        try {
            registroSelect = item;
            listaCodigoBarras = inventarioService.obtienelistaCodigoBarras(item.getClaveMedicamento());
            obtienelistaProveedor(item);
            limpiarDialogo();
        } catch (Exception ex) {
            LOGGER.error("Error al consultar Codigo Proveedor: {}", ex.getMessage());
        }
    }

    public void obtienelistaProveedor(ReportInventarioExistencias item) {
        LOGGER.trace("mx.mc.magedbean.AjusteInventarioGlobalMB.obtienelistadeProveedores()");
        try {
            registroSelect = item;
            listaProveedores = inventarioService.obtienelistaProveedor(item.getClaveMedicamento());
        } catch (Exception ex) {
            LOGGER.error("Error al consultar Codigo Proveedor: {}", ex.getMessage());
        }
    }

    public void obtienelistaCodigoBarrasByclaveInstAndClaveProv() {
        LOGGER.trace("mx.mc.magedbean.AjusteInventarioGlobalMB.obtienelistadeProveedores()");
        try {
            listaCodigoBarrasProveedor = inventarioService.obtienelistaCodigoBarrasByclaveInstAndClaveProv(registroSelect.getClaveMedicamento(), claveProveedorCmb);
        } catch (Exception ex) {
            LOGGER.error("Error al consultar Codigo Proveedor: {}", ex.getMessage());
        }
    }

    public void guardarCambiosEnProvedorCodigoBarras() {
        LOGGER.trace("mx.mc.magedbean.AjusteInventarioGlobalMB.guardarCambiosEnProvedorCodigoBarras()");
        status = Constantes.INACTIVO;
        try {
            Inventario actualizar = new Inventario();
            actualizar.setIdInventario(registroSelect.getIdInventario());
            actualizar.setClaveProveedor(claveProveedorCmb);

            status = inventarioService.updateClavProveedor(actualizar);

        } catch (Exception ex) {
            LOGGER.error("Error al consultar Codigo Proveedor: {}", ex.getMessage());
        }
    }

    public void eliminarCodigoProveedor(String codigoBarras) {
        LOGGER.trace("mx.mc.magedbean.AjusteInventarioGlobalMB.eliminarCodigoProveedor()");
        status = Constantes.INACTIVO;
        try {
            ClaveProveedorBarras codBarr = new ClaveProveedorBarras();
            codBarr.setClaveInstitucional(registroSelect.getClaveMedicamento());
            codBarr.setCodigoBarras(codigoBarras);
            status = claveProveedorBarrasService.eliminarCodigoProveedorCodigoBarra(codBarr);
            if (status) {
                Mensaje.showMessage("Info", RESOURCES.getString("codigoBarrasProveedor.info.eliminar"), null);
                obtienelistaCodigoBarras(registroSelect);
            } else {
                Mensaje.showMessage("Error", RESOURCES.getString("blok.sup.err.codigoBarrasProveedor"), null);
            }
        } catch (Exception ex) {
            LOGGER.error("Error al Eliminar Codigo Proveedor Barras: {}", ex.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public void insertarCodigoProveedor() {
        LOGGER.trace("mx.mc.magedbean.AjusteInventarioGlobalMB.eliminarCodigoProveedor()");
        status = Constantes.INACTIVO;
        try {
            if (claveProveedor == null) {
                Mensaje.showMessage("Error", "Clave de insumo de proveedor requerida.", null);

            } else if (newCodBarInsert == null || this.newCodBarInsert.trim().isEmpty()) {
                Mensaje.showMessage("Error", "Código de barras requerido. ", null);

            } else if (claveProveedorBarrasService.obtenerExistenciaByProveedorBarras(claveProveedor, codigoBarras) != null) {
                Mensaje.showMessage("Error", "Ya existe un registro con estos datos. ", null);

            } else {
                ClaveProveedorBarras inserCodBarr = new ClaveProveedorBarras();
                inserCodBarr.setClaveProveedor(claveProveedor);
                inserCodBarr.setCodigoBarras(newCodBarInsert);
                inserCodBarr.setClaveInstitucional(registroSelect.getClaveMedicamento());
                inserCodBarr.setInsertUsuario(this.usuarioSession.getIdUsuario());
                inserCodBarr.setInsertFecha(new Date());
                inserCodBarr.setCantidadXCaja(registroSelect.getCantidadXCaja());

                Inventario invenupd = new Inventario();
                invenupd.setIdInventario(registroSelect.getIdInventario());
                invenupd.setClaveProveedor(claveProveedor);
                invenupd.setUpdateIdUsuario(this.usuarioSession.getIdUsuario());
                invenupd.setUpdateFecha(new Date());

                status = claveProveedorBarrasService.insertarCodigoProveedor(inserCodBarr, invenupd);
                if (status) {
                    Mensaje.showMessage("Info", RESOURCES.getString("codigoBarrasProveedor.info.insertar"), null);
                    obtienelistaCodigoBarras(registroSelect);
                    limpiarDialogo();
                } else {
                    Mensaje.showMessage("Error", RESOURCES.getString("blok.ins.err.bloqueo"), null);
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al Insertar Codigo Proveedor Barras: {}", ex.getMessage());
        }
    }

    public void cambiarBusquedaCantidadCero() {
        if (buscaCantidadCero) {
            cantidadCero = 1;
        } else {
            cantidadCero = 0;
        }
    }

    public ParamBusquedaReporte getParamBusquedaReporte() {
        return paramBusquedaReporte;
    }

    public void setParamBusquedaReporte(ParamBusquedaReporte paramBusquedaReporte) {
        this.paramBusquedaReporte = paramBusquedaReporte;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public String getItemPirnt() {
        return itemPirnt;
    }

    public void setItemPirnt(String itemPirnt) {
        this.itemPirnt = itemPirnt;
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

    public Integer getCantPrint() {
        return cantPrint;
    }

    public void setCantPrint(Integer cantPrint) {
        this.cantPrint = cantPrint;
    }

    public int getCantidadCero() {
        return cantidadCero;
    }

    public void setCantidadCero(int cantidadCero) {
        this.cantidadCero = cantidadCero;
    }

    public Boolean getBuscaCantidadCero() {
        return buscaCantidadCero;
    }

    public void setBuscaCantidadCero(Boolean buscaCantidadCero) {
        this.buscaCantidadCero = buscaCantidadCero;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public List<TemplateEtiqueta> getTemplateList() {
        return templateList;
    }

    public void setTemplateList(List<TemplateEtiqueta> templateList) {
        this.templateList = templateList;
    }

    public Integer getNumTemp() {
        return numTemp;
    }

    public void setNumTemp(Integer numTemp) {
        this.numTemp = numTemp;
    }

    public List<Estructura> getListaAuxiliar() {
        return listaAuxiliar;
    }

    public void setListaAuxiliar(List<Estructura> listaAuxiliar) {
        this.listaAuxiliar = listaAuxiliar;
    }

    public List<Medicamento> getListaMedicamento() {
        return listaMedicamento;
    }

    public void setListaMedicamento(List<Medicamento> listaMedicamento) {
        this.listaMedicamento = listaMedicamento;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(String idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public Medicamento getClaveMedicamentos() {
        return claveMedicamentos;
    }

    public void setClaveMedicamentos(Medicamento claveMedicamentos) {
        this.claveMedicamentos = claveMedicamentos;
    }

    public String getClaveInstitucional() {
        return claveInstitucional;
    }

    public void setClaveInstitucional(String claveInstitucional) {
        this.claveInstitucional = claveInstitucional;
    }

    public String getFechacaducidad() {
        return fechacaducidad;
    }

    public void setFechacaducidad(String fechacaducidad) {
        this.fechacaducidad = fechacaducidad;
    }

    public boolean isDesabilitado() {
        return desabilitado;
    }

    public void setDesabilitado(boolean desabilitado) {
        this.desabilitado = desabilitado;
    }

    public List<Inventario> getListaLotes() {
        return listaLotes;
    }

    public void setListaLotes(List<Inventario> listaLotes) {
        this.listaLotes = listaLotes;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public String getBloqueo() {
        return bloqueo;
    }

    public void setBloqueo(String bloqueo) {
        this.bloqueo = bloqueo;
    }

    public List<Inventario> getListaFechas() {
        return listaFechas;
    }

    public void setListaFechas(List<Inventario> listaFechas) {
        this.listaFechas = listaFechas;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public Usuario getUsuarioSelect() {
        return usuarioSelect;
    }

    public void setUsuarioSelect(Usuario usuarioSelect) {
        this.usuarioSelect = usuarioSelect;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getIdTipoBloqueoMotivos() {
        return idTipoBloqueoMotivos;
    }

    public void setIdTipoBloqueoMotivos(int idTipoBloqueoMotivos) {
        this.idTipoBloqueoMotivos = idTipoBloqueoMotivos;
    }

    public List<TipoBloqueoMotivos> getListTipoBloqueoMotivos() {
        return listTipoBloqueoMotivos;
    }

    public void setListTipoBloqueoMotivos(List<TipoBloqueoMotivos> listTipoBloqueoMotivos) {
        this.listTipoBloqueoMotivos = listTipoBloqueoMotivos;
    }

    public List<String> getTipoBloqueoSelects() {
        return tipoBloqueoSelects;
    }

    public void setTipoBloqueoSelects(List<String> tipoBloqueoSelects) {
        this.tipoBloqueoSelects = tipoBloqueoSelects;
    }

    public String getTipoBloqueo() {
        return tipoBloqueo;
    }

    public void setTipoBloqueo(String tipoBloqueo) {
        this.tipoBloqueo = tipoBloqueo;
    }

    public String getTipobloq() {
        return tipobloq;
    }

    public void setTipobloq(String tipobloq) {
        this.tipobloq = tipobloq;
    }

    public Integer getIdTipoBloqueo() {
        return idTipoBloqueo;
    }

    public void setIdTipoBloqueo(Integer idTipoBloqueo) {
        this.idTipoBloqueo = idTipoBloqueo;
    }

    public Date getFechaid() {
        return fechaid;
    }

    public void setFechaid(Date fechaid) {
        this.fechaid = fechaid;
    }

    public int getStatus() {
        return statusInsum;
    }

    public void setStatus(int statusInsum) {
        this.statusInsum = statusInsum;
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

    public List<Inventario> getListidinventarioSelect() {
        return listidinventarioSelect;
    }

    public void setListidinventarioSelect(List<Inventario> listidinventarioSelect) {
        this.listidinventarioSelect = listidinventarioSelect;
    }

    public List<Inventario> getListaEstructura() {
        return listaEstructura;
    }

    public void setListaEstructura(List<Inventario> listaEstructura) {
        this.listaEstructura = listaEstructura;
    }

    public String getEstructura() {
        return estructura;
    }

    public void setEstructura(String estructura) {
        this.estructura = estructura;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public List<ClaveProveedorBarras> getListaCodigoBarras() {
        return listaCodigoBarras;
    }

    public void setListaCodigoBarras(List<ClaveProveedorBarras> listaCodigoBarras) {
        this.listaCodigoBarras = listaCodigoBarras;
    }

    public String getNewCodBarInsert() {
        return newCodBarInsert;
    }

    public void setNewCodBarInsert(String newCodBarInsert) {
        this.newCodBarInsert = newCodBarInsert;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ClaveProveedorBarrasService getClaveProveedorBarrasService() {
        return claveProveedorBarrasService;
    }

    public void setClaveProveedorBarrasService(ClaveProveedorBarrasService claveProveedorBarrasService) {
        this.claveProveedorBarrasService = claveProveedorBarrasService;
    }

    public String getClaveProveedor() {
        return claveProveedor;
    }

    public void setClaveProveedor(String claveProveedor) {
        this.claveProveedor = claveProveedor;
    }

    public List<ClaveProveedorBarras> getListaProveedores() {
        return listaProveedores;
    }

    public void setListaProveedores(List<ClaveProveedorBarras> listaProveedores) {
        this.listaProveedores = listaProveedores;
    }

    public List<ClaveProveedorBarras> getListaCodigoBarrasProveedor() {
        return listaCodigoBarrasProveedor;
    }

    public void setListaCodigoBarrasProveedor(List<ClaveProveedorBarras> listaCodigoBarrasProveedor) {
        this.listaCodigoBarrasProveedor = listaCodigoBarrasProveedor;
    }

    public String getClaveProveedorCmb() {
        return claveProveedorCmb;
    }

    public void setClaveProveedorCmb(String claveProveedorCmb) {
        this.claveProveedorCmb = claveProveedorCmb;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean isIsJefeArea() {
        return isJefeArea;
    }

    public void setIsJefeArea(boolean isJefeArea) {
        this.isJefeArea = isJefeArea;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

    public List<Fabricante> getListaFabricante() {
        return listaFabricante;
    }

    public void setListaFabricante(List<Fabricante> listaFabricante) {
        this.listaFabricante = listaFabricante;
    }

    public Integer getIdFabricante() {
        return idFabricante;
    }

    public void setIdFabricante(Integer idFabricante) {
        this.idFabricante = idFabricante;
    }

}
