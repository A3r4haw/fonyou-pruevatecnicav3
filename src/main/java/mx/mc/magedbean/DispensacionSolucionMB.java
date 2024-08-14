package mx.mc.magedbean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import mx.mc.enums.EstatusGabinete_Enum;
import mx.mc.lazy.DispensacionSolucionLazy;
import mx.mc.enums.EstatusPaciente_Enum;
import mx.mc.enums.EstatusPrescripcion_Enum;
import mx.mc.enums.EstatusSolucion_Enum;
import mx.mc.enums.EstatusSurtimiento_Enum;
import mx.mc.enums.TipoAreaEstructura_Enum;
import mx.mc.enums.TipoConsulta_Enum;
import mx.mc.enums.TipoImpresora_Enum;
import mx.mc.enums.TipoJustificacion_Enum;
import mx.mc.enums.TipoMotivo_Enum;
import mx.mc.enums.TipoPerfilUsuario_Enum;
import mx.mc.enums.TipoTemplateEtiqueta_Enum;
import mx.mc.enums.TipoUsuario_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.CamaExtended;
import mx.mc.model.CantidadRazonada;
import mx.mc.model.CantidadRazonadaExtended;
import mx.mc.model.Capsula;
import mx.mc.model.CodigoInsumo;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.EnvioNeumatico;
import mx.mc.model.Estructura;
import mx.mc.model.EtiquetaInsumo;
import mx.mc.model.Impresora;
import mx.mc.model.Inventario;
import mx.mc.model.InventarioExtended;
import mx.mc.model.Medicamento_Extended;
import mx.mc.model.MovimientoInventario;
import mx.mc.model.Paciente;
import mx.mc.model.PacienteServicio;
import mx.mc.model.Paciente_Extended;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.Prescripcion;
import mx.mc.model.PrescripcionInsumo;
import mx.mc.model.PrescripcionInsumo_Extended;
import mx.mc.model.RepSurtimientoPresc;
import mx.mc.model.Solucion;
import mx.mc.model.Surtimiento;
import mx.mc.model.SurtimientoEnviado;
import mx.mc.model.SurtimientoEnviado_Extend;
import mx.mc.model.SurtimientoInsumo;
import mx.mc.model.SurtimientoInsumo_Extend;
import mx.mc.model.Surtimiento_Extend;
import mx.mc.model.TemplateEtiqueta;
import mx.mc.model.TipoJustificacion;
import mx.mc.model.TransaccionPermisos;
import mx.mc.model.Usuario;
import mx.mc.model.ViaAdministracion;
import mx.mc.model.Visita;
import mx.mc.service.CamaService;
import mx.mc.service.CantidadRazonadaService;
import mx.mc.service.CapsulaService;
import mx.mc.service.EntidadHospitalariaService;
import mx.mc.service.EnvioNeumaticoService;
import mx.mc.service.EstructuraService;
import mx.mc.service.ImpresoraService;
import mx.mc.service.InventarioService;
import mx.mc.service.MedicamentoService;
import mx.mc.service.PacienteService;
import mx.mc.service.PacienteServicioService;
import mx.mc.service.PrescripcionService;
import mx.mc.service.ReportesService;
import mx.mc.service.SolucionService;
import mx.mc.service.SurtimientoEnviadoService;
import mx.mc.service.SurtimientoInsumoService;
import mx.mc.service.SurtimientoService;
import mx.mc.service.TemplateEtiquetaService;
import mx.mc.service.TipoJustificacionService;
import mx.mc.service.TransaccionService;
import mx.mc.service.UsuarioService;
import mx.mc.service.ViaAdministracionService;
import mx.mc.service.VisitaService;
import mx.mc.util.CodigoBarras;
import mx.mc.util.Comunes;
import mx.mc.util.FechaUtil;
import mx.mc.util.GenerateAlphaNumericString;
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
public class DispensacionSolucionMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(DispensacionSolucionMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private boolean editable;

    private boolean huboError;
    private String cadenaBusqueda;
    private boolean elementoSeleccionado;
    private Date fechaActual;
    private Usuario usuarioSelected;
    private String userResponsable;
    private String passResponsable;
    private String idResponsable;
    private String nombreCompleto;
    private boolean authorization;
    private boolean authorizado;
    private boolean exist;
    private boolean msjMdlSurtimiento;
    private String msjAlert;

    private DispensacionSolucionLazy dispensacionSolucionLazy;
    private ParamBusquedaReporte paramBusquedaReporte;
    private boolean activaAutoCompleteInsumos;
    private String surSinAlmacen;

    @Autowired
    private transient EstructuraService estructuraService;
    private transient List<Estructura> listServiciosQueSurte;
    private Medicamento_Extended medicamento;
    private transient List<Medicamento_Extended> medicamentoList;

    @Autowired
    private transient SurtimientoService surtimientoService;
    @Autowired
    private transient SurtimientoInsumoService surtimientoInsumoService;
    @Autowired
    private transient SurtimientoEnviadoService surtimientoEnviadoService;

    private Surtimiento_Extend surtimientoExtendedSelected;
    private transient List<Surtimiento_Extend> surtimientoExtendedList;

    private SurtimientoInsumo_Extend surtimientoInsumoExtendedSelected;
    private transient List<SurtimientoInsumo_Extend> surtimientoInsumoExtendedList;

    private transient List<TransaccionPermisos> permisosList;
    private String tipoPrescripcion;
    private transient List<String> tipoPrescripcionSelectedList;

    private transient List<TemplateEtiqueta> templateList;
    private String template;
    private Integer numTemp;
    private String itemPirnt;
    private Boolean activeBtnPrint;
    private String idPrintSelect;
    private Integer cantPrint;

    private Impresora impresoraSelect;
    private transient List<Impresora> listaImpresoras;

    private String codigoBarras;
    private boolean eliminaCodigoBarras;
    private String codigoBarrasAutorizte;
    private Integer xcantidadAutorizte;

    private Integer estatusSurtimiento;
    private String validada;
    private String programada;
    private String surtida;
    private String cancelada;
    private String folioSolucion;
    private String loteSolucion;
    private Date caducidadSolucion;
    private transient List<Integer> listEstatusSurtimiento;
    private Surtimiento surtim;
    private boolean mostrarImpresionEtiqueta;
    private String tipoArchivoImprimir;
    private boolean imprimeReporte;
    private boolean imprimeEtiqueta;
    private String docReporte;
    private String docEtiqueta;
    private String paramEstatus;
    private String errRegistroIncorrecto;
    private String surIncorrecto;
    private String surCaducidadvencida;
    private String surLoteIncorrecto;
    private String surInvalido;
    private PermisoUsuario permiso;
    private Usuario medico;
    private Paciente_Extended pacienteExtended;
    private transient List<CamaExtended> listaCamas;
    private transient List<ViaAdministracion> viaAdministracionList;
    private ViaAdministracion viaAdministracion;
    private String idTipoSolucion;
    private Usuario quimico;
    private Prescripcion prescripcionSelected;
    private transient List<PrescripcionInsumo> listaPrescripcionInsumo;
    private boolean perfusionContinua;
    private boolean existePrescripcion;
    private transient List<SurtimientoEnviado> surtimientoEnviadoList;

    @Autowired
    private transient CantidadRazonadaService cantidadRazonadaService;

    @Autowired
    private transient PacienteService pacienteService;

    private transient List<TipoJustificacion> justificacionList;
    @Autowired
    private transient TipoJustificacionService tipoJustificacionService;

    private Integer xcantidad;
    private Integer noDiasCaducidad;
    @Autowired
    private transient InventarioService inventarioService;

    @Autowired
    private transient ReportesService reportesService;

    @Autowired
    private transient EntidadHospitalariaService entidadHospitalariaService;

    @Autowired
    private transient CapsulaService capsulaService;

    @Autowired
    private transient EnvioNeumaticoService envioNeumaticoService;

    @Autowired
    private transient MedicamentoService medicamentoService;

    @Autowired
    private transient UsuarioService usuarioService;
    @Autowired
    private transient TransaccionService transaccionService;

    @Autowired
    private transient TemplateEtiquetaService templateService;

    @Autowired
    private transient ImpresoraService impresoraService;

    @Autowired
    private transient CamaService camaService;
    
    @Autowired
    private transient ViaAdministracionService viaAdministracionService;
    
    @Autowired
    private transient PrescripcionService prescripcionService;
    
    @Autowired
    private transient VisitaService visitaService;
    
    @Autowired
    private transient PacienteServicioService pacienteServicioService;
    
    private SesionMB sesion;
    
    
    private boolean esSolucion;
    private transient List<Integer> estatusSolucionLista;
    private boolean sanitizacionCofirmada;
    private boolean usoRemanente;
    private Integer remanetesSoluciones;
    private boolean permitirRemanentes;
    
    @Autowired
    private transient SolucionService solucionService;
    
    /**
     * Consulta los permisos del usuario y obtiene lospacientes registrados
     */
    @PostConstruct
    public void init() {
        LOGGER.debug("mx.mc.magedbean.DispensacionSolucionMB.init()");
        surSinAlmacen = "sur.sin.almacen";
        paramEstatus = "estatus";
        errRegistroIncorrecto = "err.registro.incorrecto";
        surIncorrecto = "sur.incorrecto";
        surCaducidadvencida = "sur.caducidadvencida";
        surLoteIncorrecto = "sur.loteincorrecto";
        surInvalido = "sur.invalido";
        
        listEstatusSurtimiento = new ArrayList<>();
        estatusSurtimiento = EstatusSurtimiento_Enum.PROGRAMADO.getValue();
        listEstatusSurtimiento.add(estatusSurtimiento);
        
        this.esSolucion= true;
        this.estatusSolucionLista = new ArrayList<>();
        this.estatusSolucionLista.add(EstatusSolucion_Enum.OP_VALIDADA.getValue());
        
        
        docEtiqueta = TipoImpresora_Enum.ETIQUETA.getValue();
        docReporte = TipoImpresora_Enum.NORMAL.getValue();
        mostrarImpresionEtiqueta = false;
        tipoArchivoImprimir = "";
        validada = Constantes.VALIDADA;
        programada = Constantes.PROGRAMADA;
        surtida = Constantes.SURTIDA;
        cancelada = Constantes.CANCELADA;
        limpia();
        templateList = new ArrayList<>();
        template = StringUtils.EMPTY;
        numTemp = 0;
        
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        capsulaDisponible = sesion.isCapsulaDisponible();
        this.permitirRemanentes = (sesion.getUsoRemanentes() == 1);
        this.remanetesSoluciones = sesion.getUsoRemanentesSoluciones();
        this.usoRemanente = this.permitirRemanentes;
        
        obtenerTemplatesEtiquetas();
        
        paramBusquedaReporte = new ParamBusquedaReporte();
        permiso = Comunes.obtenerPermisos(Transaccion_Enum.DISPENSAPRESCSOLUCION.getSufijo());
        usuarioSelected = sesion.getUsuarioSelected();
        noDiasCaducidad = sesion.getNoDiasCaducidad();
        userResponsable = usuarioSelected.getNombreUsuario();
        passResponsable = usuarioSelected.getClaveAcceso();
        nombreCompleto = (usuarioSelected.getNombre() + " " + usuarioSelected.getApellidoPaterno() + " " + usuarioSelected.getApellidoMaterno()).toUpperCase();
        
        listServiciosQueSurte = obtieneServiciosQuePuedeSurtir(usuarioSelected);
        
        obtenerSurtimientos();
        obtenerJustificacion();
        folioSolucion = "";
        loteSolucion = "";
        caducidadSolucion = new Date();
        surtim = new Surtimiento();
        imprimeReporte = true;
        activaAutoCompleteInsumos = sesion.isActivaAutocompleteQrClave();
        obtenerCamas();
        cargaViaAdministracion();
        viaAdministracion = new ViaAdministracion();
        loteSolucion = Constantes.LOTE_GENERICO;
        existePrescripcion = false;
        sanitizacionCofirmada = false;
        
    }
    
    public void obtenerCamas() {
        LOGGER.debug("mx.mc.magedbean.DispensacionSolucionMB.obtenerCamas()");
        try {
            listaCamas = camaService.obtenerCamasByServicio(surtimientoExtendedSelected.getIdEstructura());
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
    }
    
    private void cargaViaAdministracion() {
        LOGGER.trace("cargaViaAdministración");
        try {
            viaAdministracionList = viaAdministracionService.obtenerTodo();
        } catch (Exception ex) {
            LOGGER.error("Error al obtener datos: {}", ex.getMessage());
        }
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
     * Obtiene los Servicios que puede surtir el usuario, dependiendo de la
     * estructura jerarquica a la que esté asignada el usuario en sesion
     * Adicionalmente El area a la que el usuario este asignada, debe ser de
     * tipo almacen y que tenga una asignación de servicio hospitalario queF
     * puede surtir
     */
//    private void obtieneServiciosQuePuedeSurtir() {
//        LOGGER.debug("mx.mc.magedbean.DispensacionSolucionMB.obtieneServiciosQuePuedeSurtir()");
//        listServiciosQueSurte = new ArrayList<>();
//        permiso.setPuedeVer(false);
//
//        Estructura estSol = null;
//        try {
//            estSol = estructuraService.obtener(new Estructura(usuarioSelected.getIdEstructura()));
//
//        } catch (Exception ex) {
//            LOGGER.error(ex.getMessage());
//        }
//
//        if (estSol == null) {
//            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surSinAlmacen), null);
//
//        } else if (estSol.getIdTipoAreaEstructura() == null) {
//            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surSinAlmacen), null);
//
//        } else if (!Objects.equals(estSol.getIdTipoAreaEstructura(), TipoAreaEstructura_Enum.ALMACEN.getValue())) {
//            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.almacen.incorrectado"), null);
//
//        } else {
//            permiso.setPuedeVer(true);
//            try {
//                if (usuarioSelected.getIdEstructura() != null) {
//                    List<Estructura> estructuraServicio = estructuraService.obtenerServicioQueSurtePorIdEstructura(usuarioSelected.getIdEstructura());
//                    for(Estructura servicio : estructuraServicio){
//                        listServiciosQueSurte.add(servicio);
//                        List<String> idsEstructura = estructuraService.obtenerIdsEstructuraJerarquica(servicio.getIdEstructura(), true);
//                        for (String item : idsEstructura) {
//                            listServiciosQueSurte.add(estructuraService.obtenerEstructura(item));
//                        }
//                    }
//                }
//            } catch (Exception excp) {
//                LOGGER.error("Error al obtener Servicios que puede surtir el usuario: {}", excp.getMessage());
//            }
//        }
//
//    }

    /**
     * Limpia las variables
     */
    private void limpia() {
        LOGGER.debug("mx.mc.magedbean.DispensacionSolucionMB.limpia()");
        elementoSeleccionado = false;
        huboError = false;
        cadenaBusqueda = null;
        fechaActual = new java.util.Date();
        tipoPrescripcionSelectedList = new ArrayList<>();
        usuarioSelected = new Usuario();
        permisosList = new ArrayList<>();
        usuarioSelected.setPermisosList(permisosList);
        surtimientoExtendedSelected = new Surtimiento_Extend();
        codigoBarras = null;
        eliminaCodigoBarras = false;
        xcantidad = 1;
        nombreCompleto = "";
        userResponsable = "";
        tipoPrescripcion = "";
        passResponsable = "";
        idResponsable = "";
        msjMdlSurtimiento = true;
        exist = false;
        msjAlert = "";
    }

    public List<Medicamento_Extended> autoCompleteMedicamentos(String cadena) {
        List<Medicamento_Extended> listaMedicamentos = new ArrayList<>();
        try {
            listaMedicamentos = medicamentoService.searchMedicamentoAutoComplete(cadena.trim(), usuarioSelected.getIdEstructura(), activaAutoCompleteInsumos);

        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al consultar el medicamento");
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("medicamento.err.autocomplete"), null);
        }
        return listaMedicamentos;
    }

    /**
     * Método que busca los insumos por autocomplete y por tipo de mezcla
     * @param cadena
     * @return 
     */
// TODO: 01OCT2023-01 HRC - Buscar la asignación del almacen surtidor al servicio que gennera prescripción
// TODO : 10AGO2023 HHRC - LIMITAR INSUMOS POR TIPO DE MEZCLA
    public List<Medicamento_Extended> autoComplete(String cadena) {
        LOGGER.trace("mx.mc.magedbean.DispensacionSolucionMB.autoComplete()");
        medicamentoList = new ArrayList<>();
        try {
            if (usuarioSelected.getIdEstructura() == null || usuarioSelected.getIdEstructura().trim().isEmpty()) {
                throw new Exception("Seleccione un servicio válido.");

            } else if (idTipoSolucion == null || idTipoSolucion.trim().isEmpty()) {
                throw new Exception("Seleccione un tipo de solución.");
            } else {
                List<String> idEstructuraList = new ArrayList<>();
                // ONC
                if (idTipoSolucion.equalsIgnoreCase("e68ac222-0a63-11eb-a03a-000c29750049") || idTipoSolucion.equalsIgnoreCase("ONC")) {
                    idEstructuraList.add("c6365aac-5424-4632-9382-8e6c4a4bbe72"); // alm asep onc
                    idEstructuraList.add("cd775aad-2b0d-4aaf-9e0a-d37cdc1b8dfe"); // alm asep virt ind onc
                    idEstructuraList.add("bc23ea31-e62c-49a7-b8bc-c1b7ceb6a6ca"); // alm asep virt mat onc
                    // ANT
                } else if (idTipoSolucion.equalsIgnoreCase("e69f9857-0a63-11eb-a03a-000c29750049") || idTipoSolucion.equalsIgnoreCase("ANT")) {
                    idEstructuraList.add("e783588a-7076-4bc0-afd2-b1067cd8d38a"); // alm asep ant
                    idEstructuraList.add("2bd7621f-6c8c-48a0-afaf-3f14456075d1"); // alm asep virt ind ant
                    idEstructuraList.add("a4132042-a584-419b-b046-50d313c622d7"); // alm asep virt mat ant
                    // NPT
                } else if (idTipoSolucion.equalsIgnoreCase("e695144c-0a63-11eb-a03a-000c29750049") || idTipoSolucion.equalsIgnoreCase("NPT")) {
                    idEstructuraList.add("a79709db-128b-4a91-a85f-81cb5cd37774"); // alm asep npt
                    idEstructuraList.add("d811be54-e44a-4f27-986d-7b25380b5043"); // alm asep virt ind npt
                    idEstructuraList.add("5468f85d-14b1-4db5-bdf1-03f0a2636f8a"); // alm asep virt mat ant
                } else {
                    idEstructuraList.add("ff6d8e2f-6659-11ee-9444-e7654324e71b"); // se evía un uuid de almacén inexistente
                }
                if (usoRemanente) {
                    this.medicamentoList.addAll(
                            medicamentoService.obtenerMedicamentoRemanente(
                                    cadena.trim()
                                    , idEstructuraList
                                    , this.activaAutoCompleteInsumos
                                    , remanetesSoluciones ));
                }
                this.medicamentoList.addAll(medicamentoService.searchMedicamentoAutoCompleteMultipleAlm(cadena.trim(), idEstructuraList, this.activaAutoCompleteInsumos));
//            medicamentoList = medicamentoService.searchMedicamentoAutoComplete(cadena.trim(), idEstructura, activaAutoCompleteInsumos);
            }
        } catch (Exception ex) {
            LOGGER.error("Error al obtener Medicamentos: {}", ex.getMessage());
        }
        return medicamentoList;
    }

    public void handleSelect(SelectEvent e) {
        medicamento = (Medicamento_Extended) e.getObject();
    }

    public void handleUnSelect() {
        medicamento = new Medicamento_Extended();
    }

    public void onTabChange(TabChangeEvent evt) {
        listEstatusSurtimiento = new ArrayList<>();
        
        String valorStatus = evt.getTab().getId();
        if (valorStatus.equalsIgnoreCase(Constantes.VALIDADA)) {
            estatusSurtimiento = EstatusSurtimiento_Enum.PROGRAMADO.getValue();
            mostrarImpresionEtiqueta = false;
            
        } else if (valorStatus.equalsIgnoreCase(Constantes.SURTIDA)) {
            estatusSurtimiento = EstatusSurtimiento_Enum.SURTIDO.getValue();
            mostrarImpresionEtiqueta = true;
            
        } else if (valorStatus.equalsIgnoreCase(Constantes.CANCELADA)) {
            estatusSurtimiento = EstatusSurtimiento_Enum.CANCELADO.getValue();
            mostrarImpresionEtiqueta = true;
            
        }
        listEstatusSurtimiento.add(estatusSurtimiento);
        obtenerSurtimientos();
    }

    /**
     * Obtiene la lista de pacientes registrados
     */
    public void obtenerSurtimientos() {
        LOGGER.debug("mx.mc.magedbean.DispensacionSolucionMB.obtenerSurtimientos()");

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

// regla: Listar prescripciones solo con estatus de surtimiento programado
//                listEstatusSurtimiento.add(estatusSurtimiento);

// regla: filtro de tipo de prescripción: Normal, Dósis Única, Normal o todas
                if (tipoPrescripcionSelectedList != null && tipoPrescripcionSelectedList.isEmpty()) {
                    tipoPrescripcionSelectedList = null;
                }

// regla: listar prescripciones con fecha igual o menor a la fecha actual, nunca prescripciones futuras
                Date fechaProgramada = new java.util.Date();

                // TODO:    agregar reglas de Negocio
                // 1.- El surtimiento solo funciona en Almacenes (CEDIME, CEDIMAT, Farmacia Externa) y subalmacenes (Gabinetes o sin sistema)
                // 2.- Solo surten los insumos que tienen activos
                // 3.- CEDIME y CEDIMAT solo surten a Consulta Interna
                // 4.- 
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
                        listServiciosQueSurte
                        , esSolucion
                        , estatusSolucionLista
                        , null, tipoProceso, false);
                LOGGER.debug("Resultados: {}", dispensacionSolucionLazy.getTotalReg());

                status = Constantes.ACTIVO;

            } catch (Exception ex) {
                LOGGER.error("Error al obtenerSurtimientos. {} ", ex);
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.pac.lista"), null);
            }
        }
        PrimeFaces.current().ajax().addCallbackParam("modal", modal);
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    /**
     * Obtener listado de Justificación
     */
    private void obtenerJustificacion() {
        LOGGER.debug("mx.mc.magedbean.DispensacionSolucionMB.obtenerJustificacion()");
        justificacionList = new ArrayList<>();
        try {
            boolean activa = Constantes.ACTIVO;
            List<Integer> listTipoJustificacionSol = null;
            justificacionList.addAll(tipoJustificacionService.obtenerActivosPorListId(activa, listTipoJustificacionSol));
        } catch (Exception ex) {
            LOGGER.error("Error en obtenerJustificacion: {}", ex.getMessage());
        }
    }

    public void onRowSelectSurtimiento(SelectEvent event) {
        LOGGER.debug("mx.mc.magedbean.DispensacionSolucionMB.onRowSelectSurtimiento()");
        surtimientoInsumoExtendedSelected = (SurtimientoInsumo_Extend) event.getObject();
        if (surtimientoInsumoExtendedSelected != null) {
            elementoSeleccionado = true;
        }
    }

    public void onRowUnselectSurtimiento() {
        LOGGER.debug("mx.mc.magedbean.DispensacionSolucionMB.onRowUnselectSurtimiento()");
        surtimientoInsumoExtendedSelected = null;
        elementoSeleccionado = false;
    }

    /**
     * Muestra el Detalle de cada surtimiento de cada prescripción
     */
    public void verSurtimiento() {
        LOGGER.debug("mx.mc.magedbean.DispensacionSolucionMB.verSurtimiento()");
        xcantidad = 1;
        surtimientoExtendedSelected.setInsSanitDisp(Constantes.INACTIVO);
        sanitizacionCofirmada = Constantes.INACTIVO;
        boolean status = Constantes.INACTIVO;
        boolean modal = Constantes.INACTIVO;
        idTipoSolucion = null;

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

                Prescripcion p = prescripcionService.obtener(new Prescripcion(surtimientoExtendedSelected.getIdPrescripcion()));
                if (p != null){
                    idTipoSolucion = p.getIdTipoSolucion();
                }
                
                Date fechaProgramada = new java.util.Date();
                String idSurtimiento = surtimientoExtendedSelected.getIdSurtimiento();
                String idPrescripcion = surtimientoExtendedSelected.getIdPrescripcion();
                String idEstructura = usuarioSelected.getIdEstructura();
                
//                List<Integer> listStatusSurtimiento = new ArrayList<>();
//                listStatusSurtimiento.add(EstatusSurtimiento_Enum.SURTIDO.getValue());
//                listStatusSurtimiento.add(EstatusSurtimiento_Enum.SUSPENDIDO.getValue());
//                listStatusSurtimiento.add(EstatusSurtimiento_Enum.CANCELADO.getValue());
                
                boolean surtimientoMixto = false;

                surtimientoInsumoExtendedList = new ArrayList<>();
//                surtimientoInsumoExtendedList.addAll(surtimientoInsumoService.obtenerSurtimientosProgramados(
//                        fechaProgramada, idSurtimiento, idPrescripcion
//                        , listEstatusSurtimiento, listEstatusSurtimiento, idEstructura, surtimientoMixto)
//                        );
                surtimientoInsumoExtendedList.addAll(surtimientoInsumoService.obtenerListaByIdSurtimiento(surtimientoExtendedSelected.getIdSurtimiento()));
//                obtienelistaCapsulas();
                
                surtimientoEnviadoList = new ArrayList<>();
                surtimientoEnviadoList = surtimientoInsumoService.obtenerSurtimientoEnviados(idSurtimiento);

                if (surtimientoInsumoExtendedList != null) {
                    if (!surtimientoInsumoExtendedList.isEmpty()) {
                        for (SurtimientoInsumo_Extend surtIns : surtimientoInsumoExtendedList) {
                            if (Objects.equals(surtimientoExtendedSelected.getIdEstatusSurtimiento(), EstatusSurtimiento_Enum.PROGRAMADO.getValue())){
                                surtIns.setCantEnvPorConfirmar(surtIns.getCantidadEnviada());
                                surtIns.setCantidadEnviada(0);
                            }

                            List<SurtimientoEnviado_Extend> sel;
                            boolean mayorCero = false;
                            sel = surtimientoEnviadoService.detalleSurtimientoEnviadoXIdSurtimientoInsumo(surtIns.getIdSurtimientoInsumo(), mayorCero);
                            if (sel != null) {
                                if (Objects.equals(surtimientoExtendedSelected.getIdEstatusSurtimiento(), EstatusSurtimiento_Enum.PROGRAMADO.getValue())){
                                    for (SurtimientoEnviado_Extend item2 : sel) {
                                        item2.setCantidadPorEnviar(item2.getCantidadEnviado());
                                        item2.setCantidadEnviado(0);
                                        surtIns.setLoteSugerido(item2.getLote());
                                    }
                                }
                                surtIns.setSurtimientoEnviadoExtendList(sel);
                            }
                        }
                    }
                }

                status = Constantes.ACTIVO;
            } catch (Exception ex) {
                LOGGER.error(RESOURCES.getString(surIncorrecto), ex);
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surIncorrecto), null);
            }
        }
        PrimeFaces.current().ajax().addCallbackParam("modal", modal);
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    /**
     * Lee el codigo de barras de un medicamento y confirma la cantidad escaneda
     * para enviarse en el surtimento de prescripción
     *
     * @param e
     */
    public void validaLecturaPorCodigo(SelectEvent e) {
        medicamento = (Medicamento_Extended) e.getObject();
        LOGGER.debug("mx.mc.magedbean.DispensacionSolucionMB.validaLecturaPorCodigo()");
        boolean status = false;
        authorization = false;
        codigoBarras = CodigoBarras.generaCodigoDeBarras(medicamento.getClaveInstitucional(), medicamento.getLote(), medicamento.getFechaCaducidad(), null);
        try {
            if (codigoBarras == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.codigoincorrecto"), null);
                codigoBarras = "";
                medicamento = new Medicamento_Extended();
                return;
            } else if (codigoBarras.trim().isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.codigoincorrecto"), null);
                codigoBarras = "";
                medicamento = new Medicamento_Extended();
                return;

            } else if (surtimientoExtendedSelected == null || surtimientoInsumoExtendedList == null || surtimientoInsumoExtendedList.isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surIncorrecto), null);
                codigoBarras = "";
                medicamento = new Medicamento_Extended();
            } else if (eliminaCodigoBarras) {
                status = eliminarLotePorCodigo();
                codigoBarras = "";
                medicamento = new Medicamento_Extended();
            } else {

                status = agregarLotePorCodigo();
            }

            codigoBarras = "";
            medicamento = new Medicamento_Extended();
            xcantidad = 1;
            eliminaCodigoBarras = false;
            PrimeFaces.current().executeScript("PF('statusProcess').hide(); ");
        } catch (Exception ex) {
            LOGGER.error("Error en el metodo validaLecturaPorCodigo :: {}", ex.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    /**
     * Agrega un escaneo como subdetalle del resurtimiento
     *
     * @return
     */
    private boolean agregarLotePorCodigo() throws Exception {
        LOGGER.trace("mx.mc.magedbean.DispensacionSolucionMB.agregarLotePorCodigo()");
        boolean res = Constantes.INACTIVO;
        boolean encontrado = Constantes.INACTIVO;
//        boolean loteEncontrado = Constantes.INACTIVO;

        CodigoInsumo cin = CodigoBarras.parsearCodigoDeBarras(codigoBarras);
        if (cin == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.parser"), null);

        } else if (FechaUtil.sumarRestarDiasFecha(new java.util.Date(), noDiasCaducidad).after(cin.getFecha())) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surCaducidadvencida), null);

        } else {

            List<SurtimientoEnviado_Extend> surtimientoEnviadoExtenList;
            SurtimientoEnviado_Extend surtimientoEnviadoExtend;
            Integer cantidadEscaneada = 0;
            Integer cantidadEnviada = 0;
            surtimientoExtendedSelected.setIdEstructuraAlmacen(usuarioSelected.getIdEstructura());
            for (SurtimientoInsumo_Extend surtimientoInsumo : surtimientoInsumoExtendedList) {
                cantidadEscaneada = (xcantidad == null) ? 1 : xcantidad;

                // regla: factor multiplicador debe ser 1 o mayor
                if (cantidadEscaneada < 1) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.cantidadincorrecta"), null);
                    
                    // regla: solo escanea medicamentos si la clave escaneada existe en el detalle solicitado
                } else if (surtimientoInsumo.getClaveInstitucional().contains(cin.getClave())) {
                    encontrado = Constantes.ACTIVO;
                    
                    // regla: solo escanea medicamentos si no esta bloqueado a nivel catálogo
                    if (!surtimientoInsumo.isMedicamentoActivo()) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.clavebloqueada"), null);
                        
                    } else {
                        SurtimientoEnviado surtimientoEnviado = surtimientoEnviadoList.stream().filter
                                (p -> p.getClave().equals(cin.getClave()) 
                                        && p.getLote().equals(cin.getLote()) 
                                        && p.getCaducidad().equals(cin.getFecha())
                                ).findAny().orElse(null);
                        
                        if (surtimientoEnviado == null) {
                            if (permiso.isPuedeAutorizar()) {
                                Mensaje.showMessage(Constantes.MENSAJE_WARN, "Lote escaneado diferente al indicado en la orden de preparación.", null);
                                surtimientoEnviado = surtimientoEnviadoList.stream().filter
                                (p -> p.getClave().equals(cin.getClave())).findAny().orElse(null);
                            } else {
                                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Revise el lote indicado en la orden de preparación.", null);
                            }
                        }
                        if (surtimientoEnviado != null) {
                            Inventario invSurt = inventarioService.obtener(new Inventario(medicamento.getIdInventario()));
                            
                            if (invSurt == null){
                                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Inventario seleccionado inválido.", null);
                                
                            } else if (invSurt.getCantidadActual() < cantidadEscaneada){
                                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Cantidad disponible del insumo: " + invSurt.getCantidadActual() + " es menor que la cantidad requeridada : " + cantidadEscaneada , null);
                                
                            } else {
                                                        
                                cantidadEnviada = (surtimientoInsumo.getCantidadEnviada() == null) ? 0 : surtimientoInsumo.getCantidadEnviada();
                                cantidadEnviada = cantidadEnviada + cantidadEscaneada;

                                // regla: no puede surtir mas medicamento que el solicitado
                                if (cantidadEnviada > surtimientoInsumo.getCantidadSolicitada()) {
                                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.exedido"), null);

                                } else {                            
                                    surtimientoEnviadoExtenList = new ArrayList<>();
                                    if (surtimientoInsumo.getSurtimientoEnviadoExtendList() != null) {
                                        surtimientoEnviadoExtenList.addAll(surtimientoInsumo.getSurtimientoEnviadoExtendList());
                                    }

                                    // regla: si es primer Lote pistoleado solo muestra una linea en subdetalle
                                    if (surtimientoEnviadoExtenList.isEmpty()) {
                                        surtimientoEnviadoExtend = new SurtimientoEnviado_Extend();
                                        surtimientoEnviadoExtend.setIdSurtimientoEnviado(surtimientoEnviado.getIdSurtimientoEnviado());
                                        surtimientoEnviadoExtend.setIdSurtimientoInsumo(surtimientoInsumo.getIdSurtimientoInsumo());
                                        surtimientoEnviadoExtend.setIdInventarioSurtido(invSurt.getIdInventario());
                                        surtimientoEnviadoExtend.setCantidadEnviado(cantidadEscaneada);

                                        surtimientoEnviadoExtend.setLote(cin.getLote());
                                        surtimientoEnviadoExtend.setCaducidad(cin.getFecha());
                                        surtimientoEnviadoExtend.setIdInsumo(surtimientoEnviado.getIdInsumo());
                                        surtimientoEnviadoExtenList.add(surtimientoEnviadoExtend);

                                    } else {

                                        boolean agrupaLoteSol = false;
                                        for (SurtimientoEnviado_Extend surtimientoEnviadoRegistado : surtimientoEnviadoExtenList) {
                                            // regla: si se pistolea mas de un medicmento con el mismo lote se agrupan por lotes sumarizando las cantidades
                                            if (surtimientoEnviadoRegistado.getLote().equals(cin.getLote())
                                                    && surtimientoEnviadoRegistado.getCaducidad().equals(cin.getFecha())
                                                    && surtimientoEnviadoRegistado.getIdInsumo().equals(surtimientoEnviado.getIdInsumo())
                                                    && surtimientoEnviadoRegistado.getIdInventarioSurtido().equals(invSurt.getIdInventario())) {
                                                Integer cantidadEnviadoSol = surtimientoEnviadoRegistado.getCantidadEnviado() + cantidadEscaneada;
                                                surtimientoEnviadoRegistado.setCantidadEnviado(cantidadEnviadoSol);
                                                agrupaLoteSol = true;
                                                break;
                                            }
                                        }

                                        // regla: si es el único Lote pistoleado agrega una linea nueva en subdetalle
                                        if (!agrupaLoteSol) {
                                            surtimientoEnviadoExtend = new SurtimientoEnviado_Extend();
                                            surtimientoEnviadoExtend.setIdSurtimientoEnviado(Comunes.getUUID());
                                            surtimientoEnviadoExtend.setIdSurtimientoInsumo(surtimientoInsumo.getIdSurtimientoInsumo());
                                            surtimientoEnviadoExtend.setIdInventarioSurtido(invSurt.getIdInventario());
                                            surtimientoEnviadoExtend.setCantidadEnviado(cantidadEscaneada);

                                            surtimientoEnviadoExtend.setLote(cin.getLote());
                                            surtimientoEnviadoExtend.setCaducidad(cin.getFecha());
                                            surtimientoEnviadoExtend.setIdInsumo(surtimientoEnviado.getIdInsumo());
                                            surtimientoEnviadoExtend.setInsertFecha(new java.util.Date());
                                            surtimientoEnviadoExtend.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                                            surtimientoEnviadoExtenList.add(surtimientoEnviadoExtend);
                                        }
                                    }
                                    surtimientoInsumo.setFechaEnviada(new java.util.Date());
                                    surtimientoInsumo.setIdUsuarioEnviada(usuarioSelected.getIdUsuario());
                                    surtimientoInsumo.setCantidadEnviada(cantidadEnviada);
                                    surtimientoInsumo.setSurtimientoEnviadoExtendList(surtimientoEnviadoExtenList);
                                    if (Objects.equals(surtimientoInsumo.getCantidadSolicitada(), surtimientoInsumo.getCantidadEnviada())) {
                                        surtimientoInsumo.setRequiereJustificante(Constantes.INACTIVO);
                                        surtimientoInsumo.setIdTipoJustificante(null);
                                    } else {
                                        surtimientoInsumo.setRequiereJustificante(Constantes.ACTIVO);
                                        surtimientoInsumo.setIdTipoJustificante(TipoJustificacion_Enum.INSUFICIENTE.getValue());
                                    }
                                    res = Constantes.ACTIVO;
                                    msjMdlSurtimiento = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        if (!encontrado) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.claveincorrecta"), null);
        }
        return res;
    }

    /**
     * elimina un escaneo como subdetalle del resurtimiento
     *
     * @return
     */
    private boolean eliminarLotePorCodigo() {
        LOGGER.trace("mx.mc.magedbean.DispensacionSolucionMB.eliminarLotePorCodigo()");
        boolean res = Constantes.INACTIVO;
        boolean encontrado = Constantes.INACTIVO;

        CodigoInsumo ci = CodigoBarras.parsearCodigoDeBarras(codigoBarras);
        if (ci == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.parser"), null);

        } else if (FechaUtil.sumarRestarDiasFecha(new java.util.Date(), noDiasCaducidad).after(ci.getFecha())) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surCaducidadvencida), null);

        } else {

            List<SurtimientoEnviado_Extend> surtimientoEnviadoExtendList;
            Integer cantidadEscaneada = 0;
            Integer cantidadEnviada = 0;
            for (SurtimientoInsumo_Extend surtimientoInsumoSol : surtimientoInsumoExtendedList) {

// regla: puede escanear medicamentos mientras la clave escaneada exista en el detalle solicitado
                if (surtimientoInsumoSol.getClaveInstitucional().contains(ci.getClave())) {
                    encontrado = Constantes.ACTIVO;

                    cantidadEscaneada = (xcantidad == null) ? 1 : xcantidad;

// regla: factor multiplicador debe ser 1 o mayor
                    if (cantidadEscaneada < 1) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.cantidadincorrecta"), null);

// regla: solo escanea medicamentos si la clave escaneada existe en el detalle solicitado
                    } else {

                        cantidadEnviada = (surtimientoInsumoSol.getCantidadEnviada() == null) ? 0 : surtimientoInsumoSol.getCantidadEnviada();
                        cantidadEnviada = cantidadEnviada - cantidadEscaneada;
                        cantidadEnviada = (cantidadEnviada < 0) ? 0 : cantidadEnviada;

                        surtimientoEnviadoExtendList = new ArrayList<>();
                        if (surtimientoInsumoSol.getSurtimientoEnviadoExtendList() == null) {
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.lotesinescanear"), null);

                        } else {
                            surtimientoEnviadoExtendList.addAll(surtimientoInsumoSol.getSurtimientoEnviadoExtendList());

// regla: el lote a eliminar del surtimiento ya debió ser escaneado para eliminaro
                            if (surtimientoEnviadoExtendList.isEmpty()) {
                                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.lotesinescanear"), null);

                            } else {

                                Integer cantidadEnviadaPorLote = 0;
                                for (SurtimientoEnviado_Extend surtimientoEnviadoRegistado : surtimientoEnviadoExtendList) {

// regla: si el lote escaneado ya ha sido agregado se descuentan las cantidades
                                    if (surtimientoEnviadoRegistado.getLote().equals(ci.getLote())
                                            && surtimientoEnviadoRegistado.getCaducidad().equals(ci.getFecha())) {

                                        cantidadEnviadaPorLote = surtimientoEnviadoRegistado.getCantidadEnviado() - cantidadEscaneada;
                                        cantidadEnviadaPorLote = (cantidadEnviadaPorLote < 0) ? 0 : cantidadEnviadaPorLote;
                                        if (cantidadEnviadaPorLote < 1) {
                                            surtimientoEnviadoExtendList.remove(surtimientoEnviadoRegistado);
                                        } else {
                                            surtimientoEnviadoRegistado.setCantidadEnviado(cantidadEnviadaPorLote);
                                        }
                                        break;
                                    }
                                }

                                surtimientoInsumoSol.setFechaEnviada(new java.util.Date());
                                surtimientoInsumoSol.setIdUsuarioEnviada(usuarioSelected.getIdUsuario());
                                surtimientoInsumoSol.setCantidadEnviada(cantidadEnviada);
                                surtimientoInsumoSol.setSurtimientoEnviadoExtendList(surtimientoEnviadoExtendList);
                                if (Objects.equals(surtimientoInsumoSol.getCantidadSolicitada(), surtimientoInsumoSol.getCantidadEnviada())) {
                                    surtimientoInsumoSol.setRequiereJustificante(Constantes.INACTIVO);
                                    surtimientoInsumoSol.setIdTipoJustificante(null);
                                } else {
                                    surtimientoInsumoSol.setRequiereJustificante(Constantes.ACTIVO);
                                    surtimientoInsumoSol.setIdTipoJustificante(TipoJustificacion_Enum.INSUFICIENTE.getValue());
                                }

                                res = Constantes.ACTIVO;
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (!encontrado) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.claveincorrecta"), null);
        }
        return res;
    }

    private String obtenerClaveSolucion(Surtimiento_Extend surtimientoExtendedSelected) {
        LOGGER.trace("mx.mc.magedbean.DispensacionSolucionMB.obtenerClaveSolucion()");

        String folio = surtimientoExtendedSelected.getFolio();
        //Genera lote de claveAgrupada
        String complementoLote = "L";
        DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        String fechaProg = dateFormat.format(new Date()); // Es la fecha de cuando se surte y no de la prescripcion/surtimiento
        String lote = complementoLote.concat(fechaProg);
// TODO : corregir creación de lote de mezcla por un número irrepetible
        String cadena = GenerateAlphaNumericString.getRandomString(6);
        lote = complementoLote.concat(cadena);

        //Genera caducidad de clve Agrupada, la cual se le agrega 1 dia mas.
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(surtimientoExtendedSelected.getFechaProgramada());
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        return CodigoBarras.generaCodigoDeBarras(folio, lote, calendar.getTime(), null);
    }

    public void validaSurtimiento() {
        LOGGER.trace("mx.mc.magedbean.DispensacionSolucionMB.validaSurtimiento()");
        boolean status = Constantes.INACTIVO;
        try {

            if (surtimientoExtendedSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surInvalido), null);

            } else if (surtimientoInsumoExtendedList == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surInvalido), null);

            } else if (surtimientoInsumoExtendedList.isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surInvalido), null);

            } else {

                Integer numeroMedicamentosSurtidos = 0;
                List<SurtimientoInsumo> surtimientoInsumoList = new ArrayList<>();
                List<SurtimientoEnviado> surtimientoEnviadoList = new ArrayList<>();

                Inventario inventarioAfectadoSol;
                List<Inventario> inventarioList = new ArrayList<>();

                List<MovimientoInventario> movimientoInventarioList = new ArrayList<>();
                MovimientoInventario movimientoInventarioAfectadoSol;

                for (SurtimientoInsumo_Extend surtimientoInsumo_Ext : surtimientoInsumoExtendedList) {
                    if(!Objects.equals(surtimientoInsumo_Ext.getCantidadSolicitada(), surtimientoInsumo_Ext.getCantidadEnviada())) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Se debe realizar un surtido completo", null);
                        return;
                    }
                    if (surtimientoInsumo_Ext.getSurtimientoEnviadoExtendList() != null && !surtimientoInsumo_Ext.getSurtimientoEnviadoExtendList().isEmpty()) {
                        if (surtimientoInsumo_Ext.getCantidadEnviada().intValue()
                                != surtimientoInsumo_Ext.getCantidadSolicitada().intValue() ) {
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Debe realizar el surtimiento exacto de casa insumo.", null);
                            return;
                        }
                        Inventario inventarioSurtido;
                        for (SurtimientoEnviado_Extend surtimientoEnviado_Ext : surtimientoInsumo_Ext.getSurtimientoEnviadoExtendList()) {
                            surtimientoEnviado_Ext.setCantidadPorEnviar(0);
                            if (surtimientoEnviado_Ext.getIdInventarioSurtido() != null
                                    && surtimientoEnviado_Ext.getCantidadEnviado()>0) {

                                inventarioSurtido = inventarioService.obtener(new Inventario(surtimientoEnviado_Ext.getIdInventarioSurtido()));
                                surtimientoEnviado_Ext.setIdInventarioSurtido(inventarioSurtido.getIdInventario());
                                StringBuilder sb = new StringBuilder();
                                sb.append("Insumo: ");
                                sb.append(surtimientoInsumo_Ext.getNombreCorto());
                                sb.append(" Lote: ");
                                sb.append(surtimientoEnviado_Ext.getLote());
                                sb.append(" ");
                                    
                                if (inventarioSurtido == null) {
                                    sb.append(RESOURCES.getString("sur.loteincorrecto"));
                                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, sb.toString(), null);
                                    return;

                                } else if (inventarioSurtido.getActivo() != 1) {
                                    sb.append(RESOURCES.getString("sur.lotebloqueado"));
                                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, sb.toString(), null);
                                    return;

                                } else if (FechaUtil.sumarRestarDiasFecha(new java.util.Date(), noDiasCaducidad).after(inventarioSurtido.getFechaCaducidad())) {
                                    sb.append(RESOURCES.getString("sur.caducidadvencida"));
                                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, sb.toString(), null);
                                    return;

                                } else if (inventarioSurtido.getCantidadActual() < surtimientoEnviado_Ext.getCantidadEnviado()) {
                                    sb.append(RESOURCES.getString("sur.cantidadinsuficiente"));
                                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, sb.toString(), null);
                                    return;

                                } else {

                                    numeroMedicamentosSurtidos++;

                                    inventarioAfectadoSol = new Inventario();
                                    inventarioAfectadoSol.setIdInventario(surtimientoEnviado_Ext.getIdInventarioSurtido());
                                    inventarioAfectadoSol.setCantidadActual(surtimientoEnviado_Ext.getCantidadEnviado());
                                    inventarioList.add(inventarioAfectadoSol);

                                    movimientoInventarioAfectadoSol = new MovimientoInventario();
                                    movimientoInventarioAfectadoSol.setIdMovimientoInventario(Comunes.getUUID());
                                    Integer idTipoMotivo = TipoMotivo_Enum.SAL_PRESC_POR_SURTIMIENTO_DE_PRESCRIPCION.getMotivoValue();
                                    movimientoInventarioAfectadoSol.setIdTipoMotivo(idTipoMotivo);
                                    movimientoInventarioAfectadoSol.setFecha(new java.util.Date());
                                    movimientoInventarioAfectadoSol.setIdUsuarioMovimiento(usuarioSelected.getIdUsuario());
                                    movimientoInventarioAfectadoSol.setIdEstrutcuraOrigen(inventarioSurtido.getIdEstructura());
                                    movimientoInventarioAfectadoSol.setIdEstrutcuraDestino(surtimientoExtendedSelected.getIdEstructura());
                                    movimientoInventarioAfectadoSol.setIdInventario(surtimientoEnviado_Ext.getIdInventarioSurtido());
                                    movimientoInventarioAfectadoSol.setCantidad(surtimientoEnviado_Ext.getCantidadEnviado());
                                    movimientoInventarioAfectadoSol.setFolioDocumento(surtimientoExtendedSelected.getFolio());
                                    movimientoInventarioList.add(movimientoInventarioAfectadoSol);

                                }
                            }

                            surtimientoEnviado_Ext.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.SURTIDO.getValue());
                            surtimientoEnviado_Ext.setUpdateFecha(new java.util.Date());
                            surtimientoEnviado_Ext.setUpdateIdUsuario(usuarioSelected.getIdUsuario());

                            if (surtimientoEnviado_Ext.getCantidadRecibido() == null) {
                                surtimientoEnviado_Ext.setCantidadRecibido(0);
                            }
                            surtimientoEnviadoList.add((SurtimientoEnviado) surtimientoEnviado_Ext);
                        }
                    }
                    SurtimientoInsumo surtimientoInsumoSol = new SurtimientoInsumo();
                    surtimientoInsumoSol.setIdSurtimientoInsumo(surtimientoInsumo_Ext.getIdSurtimientoInsumo());
                    surtimientoInsumoSol.setIdUsuarioAutCanRazn(surtimientoInsumo_Ext.getIdUsuarioAutCanRazn());
                    surtimientoInsumoSol.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.SURTIDO.getValue());
                    surtimientoInsumoSol.setUpdateFecha(new java.util.Date());
                    surtimientoInsumoSol.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
                    surtimientoInsumoSol.setFechaEnviada(surtimientoInsumo_Ext.getFechaEnviada());
                    surtimientoInsumoSol.setIdUsuarioEnviada(usuarioSelected.getIdUsuario());
                    surtimientoInsumoSol.setCantidadEnviada(surtimientoInsumo_Ext.getCantidadEnviada());
                    surtimientoInsumoSol.setCantidadVale(0);
                    surtimientoInsumoSol.setIdTipoJustificante(surtimientoInsumo_Ext.getIdTipoJustificante());
                    surtimientoInsumoSol.setNotas(surtimientoInsumo_Ext.getNotas());
                    surtimientoInsumoList.add(surtimientoInsumoSol);
                    
                    //Se Valida que se deban surtir cada uno de los Insumos que conforman la solución
                    if (!Objects.equals(surtimientoInsumo_Ext.getCantidadSolicitada(), surtimientoInsumo_Ext.getCantidadEnviada())) {
                        Mensaje.showMessage("La cantidad surtida del insumo: " + surtimientoInsumo_Ext.getNombreCorto() + " difiere de la cantidad solicitada. " , null);
                        return;

                    }
                }
                surtimientoExtendedSelected.setIdEstructuraAlmacen(usuarioSelected.getIdEstructura());
                surtimientoExtendedSelected.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.SURTIDO.getValue());
                surtimientoExtendedSelected.setUpdateFecha(new java.util.Date());
                surtimientoExtendedSelected.setUpdateIdUsuario(usuarioSelected.getIdUsuario());

                Surtimiento surtimiento = (Surtimiento) surtimientoExtendedSelected;

                String claveAgrupada = obtenerClaveSolucion(surtimientoExtendedSelected);
                String idSolucion = null;
                String idSurtimiento = surtimientoExtendedSelected.getIdSurtimiento();
                Solucion o = solucionService.obtenerSolucion(idSolucion, idSurtimiento);
                if (o == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Solución Inválida", null);
                    return;

                } else {
                    Solucion s = new Solucion();
                    s.setIdSolucion(o.getIdSolucion());
                    s.setUpdateFecha(new java.util.Date());
                    s.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
                    s.setInsSanitDisp(surtimientoExtendedSelected.isInsSanitDisp());
                    s.setFechaInsSanitDisp(new java.util.Date());
                    s.setIdUsuarioInsSanitDisp(usuarioSelected.getIdUsuario());

                    status = surtimientoService.surtirSolucion(surtimiento, surtimientoInsumoList, surtimientoEnviadoList, inventarioList, movimientoInventarioList, s);

                    if (status) {
                        status = surtimientoService.actualizarClaveSolucionSurtimietoByFolio(surtimientoExtendedSelected.getFolio(), claveAgrupada);
                    }

                    if (status) {
                        Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("sur.exitoso"), "");
                        for (Surtimiento_Extend item : surtimientoExtendedList) {
                            if (item.getFolio().equals(surtimientoExtendedSelected.getFolio())) {
                                surtimientoExtendedList.remove(item);
                                break;
                            }
                        }
                        imprimirSurtimiento();
                    } else {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.error"), null);
                    }
                    vincularPrescripcionCapsula();
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo surtirInsumos :: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    /**
     * Preimpresión de etiqueta
     *
     * @param item
     */
    public void previewPrint(Surtimiento_Extend item) {
        LOGGER.trace("mx.mc.magedbean.DispensacionSolucionMB.previewPrint()");
        try {
            cantPrint = 1;
            surtimientoExtendedSelected = item;
            listaImpresoras = impresoraService.obtenerListaImpresoraByUsuario(usuarioSelected.getIdUsuario());

            listaImpresoras = listaImpresoras.stream().filter(i -> i.getTipo().equals(TipoImpresora_Enum.ETIQUETA.getValue())).collect(Collectors.toList());
            surtim = surtimientoService.obtenerPorFolio(surtimientoExtendedSelected.getFolio());
            if (surtim != null) {
                String claveAgr = surtim.getClaveAgrupada();
                CodigoInsumo ci = CodigoBarras.parsearCodigoDeBarras(claveAgr);
                if (ci != null) {
                    folioSolucion = ci.getClave();
                    loteSolucion = ci.getLote();
                    caducidadSolucion = ci.getFecha();
                }
            }

        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener datos del usuario: {}", ex.getMessage());
        }
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

    /**
     * Imprime la etiqueta con la ClaveAgrupada despues de surtirse la solucion.
     *
     * @param claveAgrupada
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
            LOGGER.error(Constantes.MENSAJE_ERROR, "Error al obtener los templates :: " + ex.getMessage());
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
     * ******* BBM ********
     */
    public void prueba() {
        authorization = true;
        Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Mensaje de prueba", null);
        Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Mensaje de prueba", null);

    }

    public void authorization() {
        try {

            Usuario usr = new Usuario();
            usr.setClaveAcceso(passResponsable);
            usr.setNombreUsuario(userResponsable);
            usr.setActivo(Constantes.ACTIVO);
            usr.setUsuarioBloqueado(Constantes.INACTIVO);
            msjMdlSurtimiento = false;

            Usuario user = usuarioService.obtener(usr);
            if (user != null) {
                List<TransaccionPermisos> permisosAutorizaListSol = transaccionService.permisosUsuarioTransaccion(user.getIdUsuario(), Transaccion_Enum.DISPENSA_PRESCRIPCION.getSufijo());
                if (permisosAutorizaListSol != null) {
                    permisosAutorizaListSol.stream().forEach(item -> {
                        if (item.getAccion().equals("AUTORIZAR")) {
                            exist = true;
                        }
                    });

                    if (exist) {
                        authorization = true;
                        authorizado = true;
                        idResponsable = user.getIdUsuario();
                        codigoBarras = codigoBarrasAutorizte;
                        xcantidad = xcantidadAutorizte;
                        agregarLotePorCodigo();
                        if (permiso.isPuedeAutorizar()) {
                            userResponsable = usuarioSelected.getNombreUsuario();
                            passResponsable = usuarioSelected.getClaveAcceso();
                        } else {
                            userResponsable = "";
                            passResponsable = "";
                        }
                        codigoBarras = "";
                        idResponsable = "";
                        authorizado = false;
                        xcantidad = 1;
                        eliminaCodigoBarras = false;
                        msjMdlSurtimiento = true;
                        PrimeFaces.current().executeScript("PF('statusProcess').hide(); PF('modalAlertaAutorizacion').hide(); ");
                    } else {
                        userResponsable = "";
                        passResponsable = "";
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El usuario no tiene permisos para Autorizar", null);
                    }
                } else {
                    userResponsable = "";
                    passResponsable = "";
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El usuario no tiene permisos para está transacción", null);
                }
            } else {
                passResponsable = "";
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El nombre de usuario o contraseña no son validos ", null);
            }
            PrimeFaces.current().executeScript("PF('statusProcess').hide(); ");
        } catch (Exception ex) {
            LOGGER.error("Error en Authorization: {}", ex.getMessage());
        }
    }

    public void cancelAuthorization() {
        authorization = false;
        msjMdlSurtimiento = true;
        PrimeFaces.current().executeScript("PF('statusProcess').hide(); PF('modalAlertaAutorizacion').hide(); ");
    }

    /**
     * ***************
     * @param idSurtimiento
     * @throws java.lang.Exception
     */
    public void imprimirPorId(String idSurtimiento) throws Exception {
        LOGGER.debug("mx.mc.magedbean.DispensacionSolucionMB.imprimirPorId()");
        surtimientoExtendedSelected = (Surtimiento_Extend) surtimientoService.obtener(new Surtimiento(idSurtimiento));
        imprimir();
    }

    public void imprimir() throws Exception {
        LOGGER.debug("mx.mc.magedbean.DispensacionSolucionMB.imprimir()");
        boolean status = Constantes.INACTIVO;
        try {
            RepSurtimientoPresc repSurtimientoPresc = obtenerDatosReporte();
            EstatusSurtimiento_Enum statusSurtimiento = EstatusSurtimiento_Enum.getStatusFromId(surtimientoExtendedSelected.getIdEstatusSurtimiento());
            byte[] buffer = reportesService.imprimeSurtimientoPrescInt(repSurtimientoPresc, statusSurtimiento.name(),
                    surtimientoExtendedSelected.getDetalle() != null ? surtimientoExtendedSelected.getDetalle().size() : 0);

            if (buffer != null) {
                status = Constantes.ACTIVO;
                sesion.setReportStream(buffer);
                sesion.setReportName(String.format("ticket%s.pdf", surtimientoExtendedSelected.getFolio()));
            }
        } catch (Exception e) {
            LOGGER.error("Error al generar la Impresión: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    public void imprimirSurtimiento() {
        LOGGER.debug("mx.mc.magedbean.DispensacionSolucionMB.imprimirSurtimiento()");
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

    public String getCadenaBusqueda() {
        return cadenaBusqueda;
    }

    public void setCadenaBusqueda(String cadenaBusqueda) {
        this.cadenaBusqueda = cadenaBusqueda;
    }

    public void setHuboError(boolean huboError) {
        this.huboError = huboError;
    }

    public boolean isHuboError() {
        return huboError;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public boolean isElementoSeleccionado() {
        return elementoSeleccionado;
    }

    public void setElementoSeleccionado(boolean elementoSeleccionado) {
        this.elementoSeleccionado = elementoSeleccionado;
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

    public Integer getSizeSurtimientoExtendList() {
        if (surtimientoExtendedList != null) {
            return surtimientoExtendedList.size();
        } else {
            return 0;
        }
    }

    public void setTipoPrescripcion(String tipoPrescripcion) {
        this.tipoPrescripcion = tipoPrescripcion;
    }

    public String getTipoPrescripcion() {
        return tipoPrescripcion;
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

    public Medicamento_Extended getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento_Extended medicamento) {
        this.medicamento = medicamento;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarra) {
        this.codigoBarras = codigoBarra;
    }

    public Surtimiento_Extend getSurtimientoExtendedSelected() {
        return surtimientoExtendedSelected;
    }

    public void setSurtimientoExtendedSelected(Surtimiento_Extend surtimientoExtendedSelected) {
        this.surtimientoExtendedSelected = surtimientoExtendedSelected;
    }

    public List<Medicamento_Extended> getMedicamentoList() {
        return medicamentoList;
    }

    public void setMedicamentoList(List<Medicamento_Extended> medicamentoList) {
        this.medicamentoList = medicamentoList;
    }

    public SurtimientoInsumo_Extend getSurtimientoInsumoExtendedSelected() {
        return surtimientoInsumoExtendedSelected;
    }

    public void setSurtimientoInsumoExtendedSelected(SurtimientoInsumo_Extend surtimientoInsumoExtendedSelected) {
        this.surtimientoInsumoExtendedSelected = surtimientoInsumoExtendedSelected;
    }

    public List<Surtimiento_Extend> getSurtimientoExtendedList() {
        return surtimientoExtendedList;
    }

    public void setSurtimientoExtendedList(List<Surtimiento_Extend> surtimientoExtendedList) {
        this.surtimientoExtendedList = surtimientoExtendedList;
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

    public List<SurtimientoInsumo_Extend> getSurtimientoInsumoExtendedList() {
        return surtimientoInsumoExtendedList;
    }

    public void setSurtimientoInsumoExtendedList(List<SurtimientoInsumo_Extend> surtimientoInsumoExtendedList) {
        this.surtimientoInsumoExtendedList = surtimientoInsumoExtendedList;
    }

    public Integer getSizeSurtimientoInsumoExtendedList() {
        if (surtimientoInsumoExtendedList != null) {
            return surtimientoInsumoExtendedList.size();
        } else {
            return 0;
        }
    }

    public boolean isEliminaCodigoBarras() {
        return eliminaCodigoBarras;
    }

    public void setEliminaCodigoBarras(boolean eliminaCodigoBarras) {
        this.eliminaCodigoBarras = eliminaCodigoBarras;
    }

    public List<TipoJustificacion> getJustificacionList() {
        return justificacionList;
    }

    public void setJustificacionList(List<TipoJustificacion> justificacionList) {
        this.justificacionList = justificacionList;
    }

    public DispensacionSolucionLazy getDispensacionSolucionLazy() {
        return dispensacionSolucionLazy;
    }

    public void setDispensacionSolucionLazy(DispensacionSolucionLazy dispensacionSolucionLazy) {
        this.dispensacionSolucionLazy = dispensacionSolucionLazy;
    }

    public Integer getXcantidad() {
        return xcantidad;
    }

    public void setXcantidad(Integer xcantidad) {
        this.xcantidad = xcantidad;
    }

    public ParamBusquedaReporte getParamBusquedaReporte() {
        return paramBusquedaReporte;
    }

    public void setParamBusquedaReporte(ParamBusquedaReporte paramBusquedaReporte) {
        this.paramBusquedaReporte = paramBusquedaReporte;
    }

    /**
     * Oliver Submodulo Capsulas
     */
    private int activo;
    private String capsula;
    private String codigoCapsula;
    private String estructura;
    private List<Capsula> idCapsula;
    private List<Capsula> listaCapsulas;
    private String codigoCpausla;
    private int idenvioNeumatico;
    private boolean capsulaDisponible;
    private String nombre;

    /**
     * Obtiene lista de capsulas que existen en la Tabla Capsula y estan activas
     */
    private void obtienelistaCapsulas() {
        try {
            listaCapsulas = capsulaService.obtieneListaCapsulasActivas(Constantes.ACTIVOS, surtimientoExtendedSelected.getIdEstructura());
        } catch (Exception ex) {
            LOGGER.info("Error al buscar la lista de Capsulas: {}", ex.getMessage());
        }

    }

    /**
     * Obtiene el id De la capsula seleccionada
     */
    private void obteneridCapsula() {
        try {
            idCapsula = capsulaService.obteneridCapsula(capsula);
        } catch (Exception ex) {
            LOGGER.info("Error al buscar la lista de Capsulas: {}", ex.getMessage());
        }
    }

    /**
     * Inserta el Id de la Capsula seleccionada en la Tabla surtimiento
     */
    public void vincularPrescripcionCapsula() {
        try {

            Surtimiento surtircapsula = new Surtimiento();
            EnvioNeumatico neumaticap = new EnvioNeumatico();
            if (capsula != null) {
                obteneridCapsula();
                surtircapsula.setIdCapsula(idCapsula.get(0).getIdCapsula());
                surtircapsula.setIdSurtimiento(surtimientoExtendedSelected.getIdSurtimiento());
                surtimientoService.ligaridCapsulaconSurtimiento(surtircapsula);
            }
            if (capsula != null) {
                //objeto para insertar datos en tabla EnvioNeumatico
                neumaticap.setIdenvioNeumatico(getIdenvioNeumatico());
                neumaticap.setIdCapsula(idCapsula.get(0).getIdCapsula());
                neumaticap.setNombreCap(idCapsula.get(0).getNombre());
                neumaticap.setFolioSurtimiento(surtimientoExtendedSelected.getFolio());
                neumaticap.setUsuario(usuarioSelected.getNombre());
                neumaticap.setFechaHoraSalida(new java.util.Date());
                neumaticap.setEstacionSalida(surtimientoExtendedSelected.getNombreEstructura());
                neumaticap.setFechaHoraLlegada(new java.util.Date());
                neumaticap.setEstacionLlegada(surtimientoExtendedSelected.getNombreEstructura());
                neumaticap.setFolioPrescripcion(surtimientoExtendedSelected.getFolioPrescripcion());
                envioNeumaticoService.insertNeumaticTable(neumaticap);
            }

        } catch (Exception ex) {
            LOGGER.info("Error al insertar id de Capsula: {}", ex.getMessage());
        }
    }

    public List<Capsula> getListaCapsulas() {
        return listaCapsulas;
    }

    public void setListaCapsulas(List<Capsula> listaCapsulas) {
        this.listaCapsulas = listaCapsulas;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public String getCapsula() {
        return capsula;
    }

    public void setCapsula(String capsula) {
        this.capsula = capsula;
    }

    public String getCodigoCapsula() {
        return codigoCapsula;
    }

    public void setCodigoCapsula(String codigoCapsula) {
        this.codigoCapsula = codigoCapsula;
    }

    public String getEstructura() {
        return estructura;
    }

    public void setEstructura(String estructura) {
        this.estructura = estructura;
    }

    public List<Capsula> getIdCapsula() {
        return idCapsula;
    }

    public void setIdCapsula(List<Capsula> idCapsula) {
        this.idCapsula = idCapsula;
    }

    public String getCodigoCpausla() {
        return codigoCpausla;
    }

    public void setCodigoCpausla(String codigoCpausla) {
        this.codigoCpausla = codigoCpausla;
    }

    public int getIdenvioNeumatico() {
        return idenvioNeumatico;
    }

    public void setIdenvioNeumatico(int idenvioNeumatico) {
        this.idenvioNeumatico = idenvioNeumatico;
    }

    public boolean isCapsulaDisponible() {
        return capsulaDisponible;
    }

    public void setCapsulaDisponible(boolean capsulaDisponible) {
        this.capsulaDisponible = capsulaDisponible;
    }

    public String getUserResponsable() {
        return userResponsable;
    }

    public void setUserResponsable(String userResponsable) {
        this.userResponsable = userResponsable;
    }

    public String getPassResponsable() {
        return passResponsable;
    }

    public void setPassResponsable(String passResponsable) {
        this.passResponsable = passResponsable;
    }

    public boolean isAuthorization() {
        return authorization;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setAuthorization(boolean authorization) {
        this.authorization = authorization;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public boolean isMsjMdlSurtimiento() {
        return msjMdlSurtimiento;
    }

    public void setMsjMdlSurtimiento(boolean msjMdlSurtimiento) {
        this.msjMdlSurtimiento = msjMdlSurtimiento;
    }

    public String getMsjAlert() {
        return msjAlert;
    }

    public void setMsjAlert(String msjAlert) {
        this.msjAlert = msjAlert;
    }

    public List<Impresora> getListaImpresoras() {
        return listaImpresoras;
    }

    public void setListaImpresoras(List<Impresora> listaImpresoras) {
        this.listaImpresoras = listaImpresoras;
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

    public Integer getNumTemp() {
        return numTemp;
    }

    public void setNumTemp(Integer numTemp) {
        this.numTemp = numTemp;
    }

    public Impresora getImpresoraSelect() {
        return impresoraSelect;
    }

    public void setImpresoraSelect(Impresora impresoraSelect) {
        this.impresoraSelect = impresoraSelect;
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

    public Integer getEstatusSurtimiento() {
        return estatusSurtimiento;
    }

    public void setEstatusSurtimiento(Integer estatusSurtimiento) {
        this.estatusSurtimiento = estatusSurtimiento;
    }

    public String getProgramada() {
        return programada;
    }

    public void setProgramada(String programada) {
        this.programada = programada;
    }

    public String getSurtida() {
        return surtida;
    }

    public void setSurtida(String surtida) {
        this.surtida = surtida;
    }

    public String getValidada() {
        return validada;
    }

    public void setValidada(String validada) {
        this.validada = validada;
    }

    public String getCancelada() {
        return cancelada;
    }

    public void setCancelada(String cancelada) {
        this.cancelada = cancelada;
    }

    public String getLoteSolucion() {
        return loteSolucion;
    }

    public void setLoteSolucion(String loteSolucion) {
        this.loteSolucion = loteSolucion;
    }

    public String getFolioSolucion() {
        return folioSolucion;
    }

    public void setFolioSolucion(String folioSolucion) {
        this.folioSolucion = folioSolucion;
    }

    public Date getCaducidadSolucion() {
        return caducidadSolucion;
    }

    public void setCaducidadSolucion(Date caducidadSolucion) {
        this.caducidadSolucion = caducidadSolucion;
    }

    public boolean isMostrarImpresionEtiqueta() {
        return mostrarImpresionEtiqueta;
    }

    public void setMostrarImpresionEtiqueta(boolean mostrarImpresionEtiqueta) {
        this.mostrarImpresionEtiqueta = mostrarImpresionEtiqueta;
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

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }
    
    public void setNuevaOrden() {
        pacienteExtended = new Paciente_Extended();
        medico = new Usuario();
        idTipoSolucion = "";
        perfusionContinua = true;
        viaAdministracion = new ViaAdministracion();
        surtimientoExtendedSelected = new Surtimiento_Extend();
//        surtimientoExtendedSelected.setVelocidad(0);
//        surtimientoExtendedSelected.setRitmo(0);
        surtimientoExtendedSelected.setComentario("");
        surtimientoExtendedSelected.setFechaProgramada(new Date());
        cadenaBusqueda = "";
        surtimientoInsumoExtendedList = new ArrayList<>();
        caducidadSolucion = null;
        loteSolucion = Constantes.LOTE_GENERICO;
    }

    public List<Estructura> getListServiciosQueSurte() {
        return listServiciosQueSurte;
    }

    public void setListServiciosQueSurte(List<Estructura> listServiciosQueSurte) {
        this.listServiciosQueSurte = listServiciosQueSurte;
    }

    public List<CamaExtended> getListaCamas() {
        return listaCamas;
    }

    public void setListaCamas(List<CamaExtended> listaCamas) {
        this.listaCamas = listaCamas;
    }

    public Usuario getMedico() {
        return medico;
    }

    public void setMedico(Usuario medico) {
        this.medico = medico;
    }
    
    public List<Usuario> autoCompleteMedicos(String cadena) {
        List<Usuario> listMedicos = new ArrayList<>();
        try {
            listMedicos = this.usuarioService.obtenerMedicosPorCriteriosBusqueda(
                        cadena.trim(), TipoUsuario_Enum.MEDICO.getValue(), Constantes.REGISTROS_PARA_MOSTRAR, null);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener autoCompleteMedicos :{}", ex.getMessage());
        }
        return listMedicos;
    }

    public Paciente_Extended getPacienteExtended() {
        return pacienteExtended;
    }

    public void setPacienteExtended(Paciente_Extended pacienteExtended) {
        this.pacienteExtended = pacienteExtended;
    }
    
    public List<Paciente_Extended> autoCompletePacientes(String cadena) {
        List<Paciente_Extended> listPacientes = new ArrayList<>();
        try {
            listPacientes = this.pacienteService.obtenerRegistrosPorCriterioDeBusqueda(
                    cadena.trim(), Constantes.REGISTROS_PARA_MOSTRAR);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener autoCompletePacientes : {}", ex.getMessage());
        }
        return listPacientes;
    }
    
    public void selectPaciente(SelectEvent evt) {
        Paciente_Extended paciente = (Paciente_Extended)evt.getObject();
        try {
            pacienteExtended = pacienteService.obtenerPacienteCompletoPorId(paciente
                    .getIdPaciente());
        } catch(Exception ex) {
            LOGGER.error("selectPaciente: Error al obtener paciente : {}", ex.getMessage());
        }
    }

    public List<ViaAdministracion> getViaAdministracionList() {
        return viaAdministracionList;
    }

    public void setViaAdministracionList(List<ViaAdministracion> viaAdministracionList) {
        this.viaAdministracionList = viaAdministracionList;
    }

    public String getIdTipoSolucion() {
        return idTipoSolucion;
    }

    public void setIdTipoSolucion(String idTipoSolucion) {
        this.idTipoSolucion = idTipoSolucion;
    }

    public ViaAdministracion getViaAdministracion() {
        return viaAdministracion;
    }

    public void setViaAdministracion(ViaAdministracion viaAdministracion) {
        this.viaAdministracion = viaAdministracion;
    }

    public Usuario getQuimico() {
        return quimico;
    }

    public void setQuimico(Usuario quimico) {
        this.quimico = quimico;
    }
    
    public List<Usuario> autoCompleteQumicos(String cadena) {
        List<Usuario> listaQuimicos = new ArrayList<>();
        try {
            listaQuimicos = this.usuarioService.obtenerMedicosPorCriteriosBusqueda(
                    cadena.trim(), TipoUsuario_Enum.ENFERMERA.getValue(), Constantes.REGISTROS_PARA_MOSTRAR, null);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener autoCompleteQumicos :{}", ex.getMessage());
        }
        return listaQuimicos;
    }

    public boolean isPerfusionContinua() {
        return perfusionContinua;
    }

    public void setPerfusionContinua(boolean perfusionContinua) {
        this.perfusionContinua = perfusionContinua;
    }
    
    private void agregaMedicamento() {
        LOGGER.debug("mx.mc.magedbean.DispensacionSolucionMB.agregaMedicamento()");
        if (medicamento == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.ins.elegido"), null);
        } else {
            SurtimientoInsumo_Extend surtInsumo = new SurtimientoInsumo_Extend();
            surtInsumo.setMedicamentoActivo(true);
            surtInsumo.setCantidadSolicitada(xcantidad);
            surtInsumo.setClaveInstitucional(medicamento.getClaveInstitucional());
            surtInsumo.setIdInsumo(medicamento.getIdMedicamento());
            surtInsumo.setNombreCorto(medicamento.getNombreCorto());
            surtimientoInsumoExtendedList.add(surtInsumo);
            medicamento = new Medicamento_Extended();
        }
    }
    
    private boolean existeMedicamentoEnLista() {
        boolean res = false;
        for (SurtimientoInsumo_Extend surtimientoInsumo : surtimientoInsumoExtendedList) {
            if (surtimientoInsumo.getClaveInstitucional().contains(medicamento.getClaveInstitucional())) {
                res = true;
                break;
            }
        }
        return res;
    }

    public boolean agregarLoteMedicamento() throws Exception {
        LOGGER.debug("mx.mc.magedbean.DispensacionSolucionMB.validaMedicamento()");
        boolean res = Constantes.INACTIVO;
        CodigoInsumo cin = CodigoBarras.parsearCodigoDeBarras(codigoBarras);
        if (cin == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.parser"), null);
        } else if (FechaUtil.sumarRestarDiasFecha(new java.util.Date(), noDiasCaducidad).after(cin.getFecha())) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surCaducidadvencida), null);
        } else {
            List<SurtimientoEnviado_Extend> surtimientoEnviadoExtenList;
            SurtimientoEnviado_Extend surtimientoEnviadoExtend;
            Integer cantidadEscaneada;
            Integer cantidadEnviada;
            if (!existeMedicamentoEnLista())
                agregaMedicamento();
            for (SurtimientoInsumo_Extend surtimientoInsumo : surtimientoInsumoExtendedList) {
                cantidadEscaneada = (xcantidad == null) ? 1 : xcantidad;
// regla: factor multiplicador debe ser 1 o mayor
                if (cantidadEscaneada < 1) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.cantidadincorrecta"), null);
                    codigoBarras = "";
                    medicamento = new Medicamento_Extended();
// regla: solo escanea medicamentos si la clave escaneada existe en el detalle solicitado
                } else if (surtimientoInsumo.getClaveInstitucional().contains(cin.getClave())) {
                    codigoBarras = "";
                    medicamento = new Medicamento_Extended();
// regla: solo escanea medicamentos si no esta bloqueado a nivel catálogo
                    if (!surtimientoInsumo.isMedicamentoActivo()) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.clavebloqueada"), null);
                        codigoBarras = "";
                        medicamento = new Medicamento_Extended();
                    } else {
                        cantidadEnviada = (surtimientoInsumo.getCantidadEnviada() == null) ? 0 : surtimientoInsumo.getCantidadEnviada();
                        cantidadEnviada = cantidadEnviada + cantidadEscaneada;
// regla: no puede surtir mas medicamento que el solicitado
                        if (cantidadEnviada > surtimientoInsumo.getCantidadSolicitada()) {
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.exedido"), null);
                            codigoBarras = "";
                            medicamento = new Medicamento_Extended();
                        } else {
                            Inventario inventarioSurtido = null;
                            try {
                                inventarioSurtido = inventarioService.obtenerInventariosPorInsumoEstructuraLoteYCantidadXCajaYFechaCad(
                                        surtimientoInsumo.getIdInsumo(), usuarioSelected.getIdEstructura(),
                                        cin.getLote(), null, null, cin.getFecha()
                                );
                            } catch (Exception ex) {
                                LOGGER.error(RESOURCES.getString(surLoteIncorrecto), ex);
                                codigoBarras = "";
                                medicamento = new Medicamento_Extended();
                            }
                            if (inventarioSurtido == null) {
                                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surLoteIncorrecto), null);
                                codigoBarras = "";
                                medicamento = new Medicamento_Extended();
                            } else if (inventarioSurtido.getActivo() == 0) {
                                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.lotebloqueado"), null);
                                codigoBarras = "";
                                medicamento = new Medicamento_Extended();
                            } else if (inventarioSurtido.getCantidadActual() < cantidadEscaneada) {
                                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.cantidadinsuficiente"), null);
                                codigoBarras = "";
                                medicamento = new Medicamento_Extended();
                            } else {
                                surtimientoInsumo.setLote(inventarioSurtido.getLote());
                                surtimientoInsumo.setIdInventario(inventarioSurtido.getIdInventario());
// regla: cantidadRazonada 
                                CantidadRazonada cantidadRazonadaSol = cantidadRazonadaService.cantidadRazonadaInsumo(cin.getClave());
                                // Se verifica que el parametro de cantidad razonada este activo
                                // 
                                if (cantidadRazonadaSol != null && !authorization && sesion.isCantidadRazonada()) {
                                    Paciente patient = new Paciente();
                                    patient.setPacienteNumero(surtimientoExtendedSelected.getPacienteNumero());
                                    Paciente paciente = pacienteService.obtener(patient);
                                    int totalDiaSol = 0;
                                    int totalMesSol = 0;
                                    int diasRestantes = 0;
                                    String ultimoSurtimiento = "";

                                    CantidadRazonadaExtended cantidadRazonadaExt = cantidadRazonadaService.cantidadRazonadaInsumoPaciente(paciente.getIdPaciente(), surtimientoInsumo.getIdInsumo());
                                    if (cantidadRazonadaExt != null) {
                                        totalDiaSol = cantidadRazonadaExt.getTotalSurtDia();
                                        totalMesSol = cantidadRazonadaExt.getTotalSurtMes();
                                        diasRestantes = cantidadRazonadaExt.getDiasRestantes();
                                        ultimoSurtimiento = cantidadRazonadaExt.getUltimoSurtimiento().toString();
                                    }

                                    //Consulta Interna
                                    if (surtimientoExtendedSelected.getTipoConsulta().equals(TipoConsulta_Enum.CONSULTA_INTERNA.getValue())) {
                                        cantidadEnviada = cantidadEnviada + totalDiaSol;
                                        if (cantidadEnviada > cantidadRazonadaSol.getCantidadDosisUnitaria()) {
                                            exist = false;
                                            msjMdlSurtimiento = false;
                                            msjAlert = "El siguiente Medicamento debe ser Autorizado para surtir la receta<br/> <b>"
                                                    + cin.getClave() + "</b> La cantidad del Medicamento debe ser menor o igual a <b>" + cantidadRazonadaSol.getCantidadDosisUnitaria() + "</b>, se solicita <b>" + cantidadEnviada + "</b>";

                                            PrimeFaces.current().executeScript("PF('modalAlertaAutorizacion').show();");
                                            codigoBarrasAutorizte = codigoBarras;
                                            xcantidadAutorizte = cantidadEscaneada;
                                            codigoBarras = "";
                                            medicamento = new Medicamento_Extended();
                                            return false;
                                        } else {
                                            authorization = true;
                                        }
                                        //Consulta Externa
                                    } else if (surtimientoExtendedSelected.getTipoConsulta().equals(TipoConsulta_Enum.CONSULTA_EXTERNA.getValue())) {
                                        cantidadEnviada = cantidadEnviada + totalMesSol;
                                        if (cantidadEnviada > cantidadRazonadaSol.getCantidadPresentacionComercial()) {
                                            exist = false;
                                            msjMdlSurtimiento = false;
                                            msjAlert = "El siguiente Medicamento debe ser Autorizado para surtir la receta\n"
                                                    + cin.getClave() + " El Medicamento solo puede surtirse cada " + cantidadRazonadaSol.getPeriodoPresentacionComercial() + " días, faltan " + diasRestantes + ", ultimo surtimiento: " + ultimoSurtimiento;

                                            PrimeFaces.current().executeScript("PF('modalAlertaAutorizacion').show();");
                                            codigoBarrasAutorizte = codigoBarras;
                                            xcantidadAutorizte = cantidadEscaneada;
                                            codigoBarras = "";
                                            medicamento = new Medicamento_Extended();
                                            return false;
                                        } else {
                                            authorization = true;
                                        }
                                    }
                                } else {
                                    authorization = true;
                                }
                                if (authorization) {
                                    surtimientoEnviadoExtenList = new ArrayList<>();
                                    if (surtimientoInsumo.getSurtimientoEnviadoExtendList() != null) {
                                        surtimientoEnviadoExtenList.addAll(surtimientoInsumo.getSurtimientoEnviadoExtendList());
                                    }
// regla: si es primer Lote pistoleado solo muestra una linea en subdetalle
                                    if (surtimientoEnviadoExtenList.isEmpty()) {
                                        surtimientoEnviadoExtend = new SurtimientoEnviado_Extend();
                                        surtimientoEnviadoExtend.setIdSurtimientoEnviado(Comunes.getUUID());
                                        surtimientoEnviadoExtend.setIdSurtimientoInsumo(surtimientoInsumo.getIdSurtimientoInsumo());
                                        surtimientoEnviadoExtend.setIdInventarioSurtido(inventarioSurtido.getIdInventario());
                                        surtimientoEnviadoExtend.setCantidadEnviado(cantidadEscaneada);

                                        surtimientoEnviadoExtend.setLote(cin.getLote());
                                        surtimientoEnviadoExtend.setCaducidad(cin.getFecha());
                                        surtimientoEnviadoExtend.setIdInsumo(inventarioSurtido.getIdInsumo());
                                        surtimientoEnviadoExtenList.add(surtimientoEnviadoExtend);
                                    } else {
                                        boolean agrupaLoteSol = false;
                                        for (SurtimientoEnviado_Extend surtimientoEnviadoRegistado : surtimientoEnviadoExtenList) {
// regla: si se pistolea mas de un medicmento con el mismo lote se agrupan por lotes sumarizando las cantidades
                                            if (surtimientoEnviadoRegistado.getLote().equals(cin.getLote())
                                                    && surtimientoEnviadoRegistado.getCaducidad().equals(cin.getFecha())
                                                    && surtimientoEnviadoRegistado.getIdInsumo().equals(inventarioSurtido.getIdInsumo())) {
                                                Integer cantidadEnviadoSol = surtimientoEnviadoRegistado.getCantidadEnviado() + cantidadEscaneada;
                                                surtimientoEnviadoRegistado.setCantidadEnviado(cantidadEnviadoSol);
                                                agrupaLoteSol = true;
                                                break;
                                            }
                                        }
// regla: si es el único Lote pistoleado agrega una linea nueva en subdetalle
                                        if (!agrupaLoteSol) {
                                            surtimientoEnviadoExtend = new SurtimientoEnviado_Extend();
                                            surtimientoEnviadoExtend.setIdSurtimientoEnviado(Comunes.getUUID());
                                            surtimientoEnviadoExtend.setIdSurtimientoInsumo(surtimientoInsumo.getIdSurtimientoInsumo());
                                            surtimientoEnviadoExtend.setIdInventarioSurtido(inventarioSurtido.getIdInventario());
                                            surtimientoEnviadoExtend.setCantidadEnviado(cantidadEscaneada);

                                            surtimientoEnviadoExtend.setLote(cin.getLote());
                                            surtimientoEnviadoExtend.setCaducidad(cin.getFecha());
                                            surtimientoEnviadoExtend.setIdInsumo(inventarioSurtido.getIdInsumo());
                                            surtimientoEnviadoExtenList.add(surtimientoEnviadoExtend);
                                        }
                                    }
                                    if (sesion.isCantidadRazonada() && authorizado) {
                                        surtimientoInsumo.setIdUsuarioAutCanRazn(idResponsable);
                                    }
                                    surtimientoInsumo.setFechaEnviada(new Date());
                                    surtimientoInsumo.setIdUsuarioEnviada(usuarioSelected.getIdUsuario());
                                    surtimientoInsumo.setCantidadEnviada(cantidadEnviada);
                                    surtimientoInsumo.setSurtimientoEnviadoExtendList(surtimientoEnviadoExtenList);
                                    if (Objects.equals(surtimientoInsumo.getCantidadSolicitada(), surtimientoInsumo.getCantidadEnviada())) {
                                        surtimientoInsumo.setRequiereJustificante(Constantes.INACTIVO);
                                        surtimientoInsumo.setIdTipoJustificante(null);
                                    } else {
                                        surtimientoInsumo.setRequiereJustificante(Constantes.ACTIVO);
                                        surtimientoInsumo.setIdTipoJustificante(TipoJustificacion_Enum.INSUFICIENTE.getValue());
                                    }
                                    res = Constantes.ACTIVO;
                                    msjMdlSurtimiento = true;
                                    codigoBarras = "";
                                    medicamento = new Medicamento_Extended();
                                }
                            }
                        }
                    }
                }
            }
        }
        return res;
    }

    public boolean isExistePrescripcion() {
        return existePrescripcion;
    }

    public void setExistePrescripcion(boolean existePrescripcion) {
        this.existePrescripcion = existePrescripcion;
    }
    
    public void obtenDatosPrescripcion() {
        existePrescripcion = false;
        try {
            Prescripcion presc = new Prescripcion();
            presc.setFolio(surtimientoExtendedSelected.getFolioPrescripcion());
            prescripcionSelected = prescripcionService.obtener(presc);
            if (prescripcionSelected != null) {
                existePrescripcion = true;
                PrescripcionInsumo_Extended prescripcionInsumo = prescripcionService.obtenerUltimaPrescripcionInsumoByIdPrescripcion(prescripcionSelected.getIdPrescripcion());
                PacienteServicio pacienteServicio = new PacienteServicio();
                pacienteServicio.setIdPacienteServicio(prescripcionSelected.getIdPacienteServicio());
                pacienteServicio = pacienteServicioService.obtener(pacienteServicio);
                Visita visita = new Visita();
                visita.setIdVisita(pacienteServicio.getIdVisita());
                visita = visitaService.obtener(visita);
                pacienteExtended = pacienteService.obtenerPacienteCompletoPorId(visita.getIdPaciente());
                surtimientoExtendedSelected = surtimientoService.obtenerUltimoByIdPrescripcion(prescripcionSelected.getIdPrescripcion());
                surtimientoExtendedSelected.setIdEstructura(prescripcionSelected.getIdPacienteServicio());
                surtimientoExtendedSelected.setTipoPrescripcion(prescripcionSelected.getTipoPrescripcion());
                surtimientoExtendedSelected.setTipoConsulta(prescripcionSelected.getTipoConsulta());
                surtimientoExtendedSelected.setVelocidad(prescripcionInsumo.getVelocidad());
                surtimientoExtendedSelected.setRitmo(prescripcionInsumo.getRitmo());
                //surtimientoExtendedSelected.setCama();
                medico = usuarioService.obtenerUsuarioByIdUsuario(prescripcionSelected.getIdMedico());
                viaAdministracion = viaAdministracionService.obtenerPorId(prescripcionSelected.getIdViaAdministracion());
                idTipoSolucion = prescripcionSelected.getIdTipoSolucion();
                perfusionContinua = prescripcionInsumo.getPerfusionContinua().equals(1);
            }
        } catch (Exception ex) {
            LOGGER.debug("Error en el metodo obtenDatosPrescripcion :: {}", ex.getMessage());
        }
    }
    
    public void validaLecturaMedicamento(SelectEvent e) {
        boolean status = false;
        medicamento = (Medicamento_Extended) e.getObject();
        LOGGER.debug("mx.mc.magedbean.DispensacionSolucionMB.validaLecturaMedicamento()");
        codigoBarras = CodigoBarras.generaCodigoDeBarras(medicamento.getClaveInstitucional(), medicamento.getLote(), medicamento.getFechaCaducidad(), null);
        try {
            if (codigoBarras == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.codigoincorrecto"), null);
                codigoBarras = "";
                medicamento = new Medicamento_Extended();
                return;
            } else if (codigoBarras.trim().isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.codigoincorrecto"), null);
                codigoBarras = "";
                medicamento = new Medicamento_Extended();
                return;
            } 
            status = agregarLoteMedicamento();
            codigoBarras = "";
            xcantidad = 1;
            PrimeFaces.current().executeScript("PF('statusProcess').hide(); ");
        } catch (Exception ex) {
            LOGGER.error("Error en el metodo validaLecturaMedicamento :: {}", ex.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }
    
    private boolean obtenerPrescripcion() {
        boolean exists = false;
        prescripcionSelected = null;
        try {
            Prescripcion presc = new Prescripcion();
            presc.setFolio(surtimientoExtendedSelected.getFolioPrescripcion());
            prescripcionSelected = prescripcionService.obtener(presc);
            if (prescripcionSelected == null) {
                prescripcionSelected = new Prescripcion();
                prescripcionSelected.setIdPrescripcion(Comunes.getUUID());
                prescripcionSelected.setIdEstructura(usuarioSelected.getIdEstructura());
                prescripcionSelected.setIdPacienteServicio(surtimientoExtendedSelected.getIdEstructura());
                prescripcionSelected.setFolio(surtimientoExtendedSelected.getFolioPrescripcion());
                prescripcionSelected.setTipoPrescripcion(surtimientoExtendedSelected.getTipoPrescripcion());
                prescripcionSelected.setTipoConsulta(surtimientoExtendedSelected.getTipoConsulta());
                prescripcionSelected.setIdMedico(medico.getIdUsuario());
                prescripcionSelected.setRecurrente(false);
                prescripcionSelected.setIdEstatusPrescripcion(EstatusPrescripcion_Enum.PROGRAMADA.getValue());
                prescripcionSelected.setIdEstatusGabinete(EstatusGabinete_Enum.OK.getValue());
                Date fechaPresc = new Date();
                prescripcionSelected.setFechaPrescripcion(fechaPresc);
                prescripcionSelected.setInsertFecha(fechaPresc);
                prescripcionSelected.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                prescripcionSelected.setIdTipoSolucion(idTipoSolucion);
                prescripcionSelected.setIdViaAdministracion(viaAdministracion.getIdViaAdministracion());
            } else {
                exists = true;
                if (!prescripcionSelected.getIdEstatusPrescripcion().equals(EstatusPrescripcion_Enum.PROGRAMADA.getValue())) {
                    prescripcionSelected = null;
                    Mensaje.showMessage("El estatus de la prescripción no válido", null);
                }
            }
        } catch(Exception ex) {
            LOGGER.error("mx.mc.magedbean.DispensacionMB.obtenerPrescripcion()", ex);
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.obtener"), null);
        }
        return exists;
    }
    
    private void generarSurtimiento() {
        surtimientoExtendedSelected.setIdSurtimiento(Comunes.getUUID());
        surtimientoExtendedSelected.setIdEstructuraAlmacen(usuarioSelected.getIdEstructura());
        surtimientoExtendedSelected.setIdPrescripcion(prescripcionSelected.getIdPrescripcion());
        surtimientoExtendedSelected.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.SURTIDO.getValue());
        Date fechaSurt = new Date();
        surtimientoExtendedSelected.setInsertFecha(fechaSurt);
        surtimientoExtendedSelected.setInsertIdUsuario(usuarioSelected.getIdUsuario());
        surtimientoExtendedSelected.setIdUsuarioPrepara(quimico.getIdUsuario());
    }
    
    private void generarListaPrescripcionInsumo() {
        boolean isFirst = true;
        Date fechaPresc = new Date();
        listaPrescripcionInsumo = new ArrayList<>();
        for (SurtimientoInsumo_Extend surtInsumo : surtimientoInsumoExtendedList) {
            PrescripcionInsumo prescripcionInsumo = new PrescripcionInsumo();
            prescripcionInsumo.setIdPrescripcionInsumo(Comunes.getUUID());
            surtInsumo.setIdPrescripcionInsumo(prescripcionInsumo.getIdPrescripcionInsumo());
            prescripcionInsumo.setIdPrescripcion(prescripcionSelected.getIdPrescripcion());
            prescripcionInsumo.setIdInsumo(surtInsumo.getIdInsumo());
            if (isFirst) {
                isFirst = false;
                prescripcionInsumo.setPerfusionContinua(perfusionContinua ? 1 : 0);
            }
            BigDecimal dosis = BigDecimal.ZERO;
            if (surtInsumo.getConcentracion() != null && !surtInsumo.getConcentracion().isEmpty())
                dosis = BigDecimal.valueOf(Double.valueOf(surtInsumo.getConcentracion()) * xcantidad);
            prescripcionInsumo.setDosis(dosis);
            prescripcionInsumo.setFrecuencia(24);
            prescripcionInsumo.setDuracion(1);
            prescripcionInsumo.setFechaInicio(fechaPresc);
            prescripcionInsumo.setInsertFecha(fechaPresc);
            prescripcionInsumo.setInsertIdUsuario(usuarioSelected.getIdUsuario());
            prescripcionInsumo.setIdEstatusPrescripcion(EstatusPrescripcion_Enum.PROGRAMADA.getValue());
            prescripcionInsumo.setVelocidad(surtimientoExtendedSelected.getVelocidad());
            prescripcionInsumo.setRitmo(surtimientoExtendedSelected.getRitmo());
            listaPrescripcionInsumo.add(prescripcionInsumo);
        }
    }

    public void registrarSurtirOrden() {
        LOGGER.trace("mx.mc.magedbean.DispensacionSolucionMB.registrarSurtirOrden()");
        boolean status = Constantes.INACTIVO;
        try {
            if (surtimientoExtendedSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surInvalido), null);

            } else if (surtimientoInsumoExtendedList == null || surtimientoInsumoExtendedList.isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No ha seleccionado los insumos por Surtir", null);
            } else {
                Integer numeroMedicamentosSurtidos = 0;
                List<SurtimientoEnviado> surtimientoEnviadoList = new ArrayList<>();
                InventarioExtended inventarioAfectadoSol;
                List<InventarioExtended> inventarioList = new ArrayList<>();
                List<MovimientoInventario> movimientoInventarioList = new ArrayList<>();
                MovimientoInventario movimientoInventarioAfectadoSol;
                
                boolean existePresc = obtenerPrescripcion();
                generarListaPrescripcionInsumo();
                generarSurtimiento();
                Date fechaSurt = new Date();
                for (SurtimientoInsumo_Extend surtimientoInsumo_Ext : surtimientoInsumoExtendedList) {
                    surtimientoInsumo_Ext.setIdSurtimientoInsumo(Comunes.getUUID());
                    surtimientoInsumo_Ext.setIdSurtimiento(surtimientoExtendedSelected.getIdSurtimiento());
                    surtimientoInsumo_Ext.setFechaProgramada(surtimientoExtendedSelected.getFechaProgramada());
                    surtimientoInsumo_Ext.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.SURTIDO.getValue());
                    surtimientoInsumo_Ext.setFechaEnviada(surtimientoInsumo_Ext.getFechaEnviada());
                    surtimientoInsumo_Ext.setIdUsuarioEnviada(usuarioSelected.getIdUsuario());
                    surtimientoInsumo_Ext.setCantidadVale(0);
                    surtimientoInsumo_Ext.setInsertFecha(fechaSurt);
                    surtimientoInsumo_Ext.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                    if (surtimientoInsumo_Ext.getSurtimientoEnviadoExtendList() != null && !surtimientoInsumo_Ext.getSurtimientoEnviadoExtendList().isEmpty()) {
                        if (surtimientoInsumo_Ext.getCantidadEnviada().intValue()
                                != surtimientoInsumo_Ext.getCantidadSolicitada().intValue() && surtimientoInsumo_Ext.getIdTipoJustificante() == null) {
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("dispensacion.err.surtmedicamento"), null);
                            PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
                            return;
                        }
                        Inventario inventarioSurtido;
                        for (SurtimientoEnviado_Extend surtimientoEnviado_Ext : surtimientoInsumo_Ext.getSurtimientoEnviadoExtendList()) {
                            if (surtimientoEnviado_Ext.getIdInventarioSurtido() != null) {            
                                inventarioSurtido = inventarioService.obtener(new Inventario(surtimientoEnviado_Ext.getIdInventarioSurtido()));
                                if (inventarioSurtido == null) {
                                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surLoteIncorrecto), null);
                                    PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
                                    return;

                                } else if (inventarioSurtido.getActivo() != 1) {
                                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.lotebloqueado"), null);
                                    PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
                                    return;

                                } else if (FechaUtil.sumarRestarDiasFecha(new java.util.Date(), noDiasCaducidad).after(inventarioSurtido.getFechaCaducidad())) {
                                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surCaducidadvencida), null);
                                    PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
                                    return;

                                } else if (inventarioSurtido.getCantidadActual() < surtimientoEnviado_Ext.getCantidadEnviado()) {
                                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.cantidadinsuficiente"), null);
                                    PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
                                    return;
                                } else {
                                    numeroMedicamentosSurtidos++;
                                    inventarioAfectadoSol = new InventarioExtended();
                                    inventarioAfectadoSol.setIdInventario(surtimientoEnviado_Ext.getIdInventarioSurtido());
                                    inventarioAfectadoSol.setCantidadActual(surtimientoEnviado_Ext.getCantidadEnviado());
                                    inventarioList.add(inventarioAfectadoSol);

                                    movimientoInventarioAfectadoSol = new MovimientoInventario();
                                    movimientoInventarioAfectadoSol.setIdMovimientoInventario(Comunes.getUUID());
                                    Integer idTipoMotivo = TipoMotivo_Enum.SAL_PRESC_POR_SURTIMIENTO_DE_PRESCRIPCION.getMotivoValue();
                                    movimientoInventarioAfectadoSol.setIdTipoMotivo(idTipoMotivo);
                                    movimientoInventarioAfectadoSol.setFecha(fechaSurt);
                                    movimientoInventarioAfectadoSol.setIdUsuarioMovimiento(usuarioSelected.getIdUsuario());
                                    movimientoInventarioAfectadoSol.setIdEstrutcuraOrigen(usuarioSelected.getIdEstructura());
                                    movimientoInventarioAfectadoSol.setIdEstrutcuraDestino(surtimientoExtendedSelected.getIdEstructura());
                                    movimientoInventarioAfectadoSol.setIdInventario(surtimientoEnviado_Ext.getIdInventarioSurtido());
                                    movimientoInventarioAfectadoSol.setCantidad(surtimientoEnviado_Ext.getCantidadEnviado());
                                    movimientoInventarioAfectadoSol.setFolioDocumento(surtimientoExtendedSelected.getFolio());
                                    movimientoInventarioList.add(movimientoInventarioAfectadoSol);
                                }
                            }
                            surtimientoEnviado_Ext.setIdSurtimientoInsumo(surtimientoInsumo_Ext.getIdSurtimientoInsumo());
                            surtimientoEnviado_Ext.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.SURTIDO.getValue());
                            surtimientoEnviado_Ext.setInsertFecha(fechaSurt);
                            surtimientoEnviado_Ext.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                            if (surtimientoEnviado_Ext.getCantidadRecibido() == null) {
                                surtimientoEnviado_Ext.setCantidadRecibido(0);
                            }
                            surtimientoEnviadoList.add((SurtimientoEnviado) surtimientoEnviado_Ext);
                        }
                    }
                }
                //Se Valida que se deban surtir cada uno de los Insumos que conforman la solución
                if (numeroMedicamentosSurtidos != surtimientoInsumoExtendedList.size()) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.error"), null);
                    PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
                    return;

                } else {
                    Visita v = null;
                    try {
                        v = visitaService.obtenerVisitaAbierta(new Visita(pacienteExtended.getIdPaciente()));
                    } catch (Exception ex) {
                        LOGGER.error(RESOURCES.getString("prc.visita"), ex);
                    }
        // regla: paciente debe tener visita abierta
                    if (v == null || v.getIdVisita() == null || v.getIdVisita().isEmpty()) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.visita"), null);
                        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
                        return;
                    }
                    PacienteServicio ps = null;
                    try {
                        ps = pacienteServicioService.obtener(new PacienteServicio(v.getIdVisita()));
                    } catch (Exception ex) {
                        LOGGER.error(RESOURCES.getString("prc.servicio"), ex);
                    }
        // regla: paciente debe estar asignado a un servicio
                    if (ps == null || ps.getIdPacienteServicio() == null) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.servicio"), null);
                        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
                        return;
                    }
                    prescripcionSelected.setIdPacienteServicio(ps.getIdPacienteServicio());
                    surtimientoExtendedSelected.setIdEstructuraAlmacen(usuarioSelected.getIdEstructura());
                    surtimientoExtendedSelected.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.SURTIDO.getValue());
                    surtimientoExtendedSelected.setUpdateFecha(new java.util.Date());
                    surtimientoExtendedSelected.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
                    Surtimiento surtimiento = surtimientoExtendedSelected;
                    status = prescripcionService.registrarSurtirOrdenSolucion(existePresc, prescripcionSelected, listaPrescripcionInsumo, surtimiento, surtimientoInsumoExtendedList, surtimientoEnviadoList, inventarioList, movimientoInventarioList);
                    surtimientoExtendedSelected.setFolio(surtimiento.getFolio());
                    if (status) {
                        status = surtimientoService.actualizarClaveSolucionSurtimietoByFolio(surtimiento.getFolio(), obtenerClaveSolucion(surtimientoExtendedSelected));
                    }

                    if (status) {
                        Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("sur.exitoso"), "");
                        for (Surtimiento_Extend item : surtimientoExtendedList) {
                            if (item.getFolio().equals(surtimientoExtendedSelected.getFolio())) {
                                surtimientoExtendedList.remove(item);
                                break;
                            }
                        }
                        imprimirSurtimiento();
                    } else {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.error"), null);
                    }
                    vincularPrescripcionCapsula();
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo surtirInsumos :: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    public boolean isSanitizacionCofirmada() {
        return sanitizacionCofirmada;
    }

    public void setSanitizacionCofirmada(boolean sanitizacionCofirmada) {
        this.sanitizacionCofirmada = sanitizacionCofirmada;
    }
    
    public void confirmarSanitizacion (){
        LOGGER.trace("mx.mc.magedbean.DispensacionSolucionMB.confirmarSanitizacion()");
        if (surtimientoExtendedSelected != null){
            sanitizacionCofirmada = surtimientoExtendedSelected.isInsSanitDisp();
            surtimientoExtendedSelected.setIdUsuarioInsSanitDisp(usuarioSelected.getIdUsuario());
            surtimientoExtendedSelected.setFechaInsSanitDisp(new java.util.Date());
        }
    }

    public boolean isUsoRemanente() {
        return usoRemanente;
    }

    public void setUsoRemanente(boolean usoRemanente) {
        this.usoRemanente = usoRemanente;
    }
    
    public void permitirUsoRemanentes(){
        LOGGER.trace("mx.mc.magedbean.DispensacionSolucionMB.permitirUsoRemanentes()");
        if (usoRemanente == true){
            LOGGER.trace("buscar en remanentes primero");
        }
    }

    public boolean isPermitirRemanentes() {
        return permitirRemanentes;
    }

    public void setPermitirRemanentes(boolean permitirRemanentes) {
        this.permitirRemanentes = permitirRemanentes;
    }
        
}
