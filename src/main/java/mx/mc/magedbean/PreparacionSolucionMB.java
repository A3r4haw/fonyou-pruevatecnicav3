package mx.mc.magedbean;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Response;
import mx.mc.commons.SolucionUtils;
import mx.mc.dto.MedicamentoDTO;
import mx.mc.dto.ParamBusquedaAlertaDTO;
import mx.mc.lazy.DispensacionSolucionLazy;
import mx.mc.enums.EstatusPaciente_Enum;
import mx.mc.enums.EstatusPrescripcion_Enum;
import mx.mc.enums.EstatusSolucion_Enum;
import mx.mc.enums.EstatusSurtimiento_Enum;
import mx.mc.enums.MotivoCancelacionRechazoSolucion_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.CodigoInsumo;
import mx.mc.model.Diagnostico;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.EnvaseContenedor;
import mx.mc.model.Equipo;
import mx.mc.model.Estructura;
import mx.mc.model.HorarioEntrega;
import mx.mc.model.Medicamento_Extended;
import mx.mc.model.Paciente;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.Prescripcion;
import mx.mc.model.Protocolo;
import mx.mc.model.Solucion;
import mx.mc.model.Surtimiento;
import mx.mc.model.SurtimientoEnviado;
import mx.mc.model.SurtimientoEnviado_Extend;
import mx.mc.model.SurtimientoInsumo;
import mx.mc.model.SurtimientoInsumo_Extend;
import mx.mc.model.Surtimiento_Extend;
import mx.mc.model.TipoSolucion;
import mx.mc.model.TransaccionPermisos;
import mx.mc.model.Usuario;
import mx.mc.model.ViaAdministracion;
import mx.mc.model.Visita;
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
import mx.mc.service.ImpresoraService;
import mx.mc.service.InventarioService;
import mx.mc.service.MedicamentoService;
import mx.mc.service.MotivosRechazoService;
import mx.mc.service.PacienteService;
import mx.mc.service.PacienteServicioService;
import mx.mc.service.PacienteUbicacionService;
import mx.mc.service.PlantillaCorreoService;
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
import mx.mc.util.CodigoBarras;
import mx.mc.util.Comunes;
import mx.mc.util.EnviaCorreoUtil;
import mx.mc.util.FechaUtil;
import mx.mc.util.Mensaje;
import mx.mc.ws.farmacovigilancia.service.ReaccionesAdversas;
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
 * @author bbautista
 */
@Controller
@Scope(value = "view")
public class PreparacionSolucionMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(PreparacionSolucionMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    private boolean imprimeReporte;
    private boolean imprimeEtiqueta;
    private boolean esSolucion;
    private boolean msjMdlConfirmacion;
    private boolean msjMdlCancelar;
    private boolean elementoSeleccionado;
    private boolean editable;
    private boolean huboError;
    private boolean otro;
    private Date caducidadSolucion;
    private Date fechaActual;
    private Integer sobrante;
    private Integer motivoCancel;
    private Integer estatusSurtimiento;
    private String paramEstatus;
    private String errRegistroIncorrecto;
    private String surIncorrecto;
    private String comentarios;
    private String cadenaBusqueda;
    private String nombreCompleto;
    private String surSinAlmacen;
    private String tipoPrescripcion;
    private String codigoBarras;
    private String pendiente;
    private String preparada;
//    private Integer mezclaPreparada;
    private String error_al_preparar;
    private String folioSolucion;
    private String loteSolucion;
    private Usuario usuarioSelected;
    private SesionMB sesion;
    private PermisoUsuario permiso;
    private DispensacionSolucionLazy dispensacionSolucionLazy;
    private ParamBusquedaReporte paramBusquedaReporte;
    private transient List<String> tipoPrescripcionSelectedList;
    private transient List<Integer> listEstatusSurtimiento;
    private transient List<String> sobranteList;

    private transient List<Estructura> listServiciosQueSurte;
    private Medicamento_Extended medicamento;
    private transient List<Medicamento_Extended> medicamentoList;
    private Surtimiento_Extend surtimientoExtendedSelected;
    private transient List<Surtimiento_Extend> surtimientoExtendedList;

    private SurtimientoInsumo_Extend surtimientoInsumoExtendedSelected;
    private transient List<SurtimientoInsumo_Extend> surtimientoInsumoExtendedList;
    private transient List<TransaccionPermisos> permisosList;

    @Autowired
    private transient EstructuraService estructuraService;
    @Autowired
    private transient SurtimientoService surtimientoService;
    @Autowired
    private transient SurtimientoInsumoService surtimientoInsumoService;
    @Autowired
    private transient PacienteService pacienteService;
    @Autowired
    private transient PacienteServicioService pacienteServicioService;
    @Autowired
    private transient ReportesService reportesService;
    @Autowired
    private transient EntidadHospitalariaService entidadHospitalariaService;
    @Autowired
    private transient MedicamentoService medicamentoService;
    @Autowired
    private transient UsuarioService usuarioService;
    @Autowired
    private transient VisitaService visitaService;
    @Autowired
    private transient ServletContext servletContext;
    @Autowired
    private transient DiagnosticoService diagnosticoService;
    @Autowired
    private transient PrescripcionService prescripcionService;
    @Autowired
    private transient EnvaseContenedorService envaseService;
    @Autowired
    private transient SurtimientoEnviadoService enviadoService;
    @Autowired
    private transient SolucionService solucionService;
    @Autowired
    private transient ViaAdministracionService viaAdministracionService;
    @Autowired
    private transient TipoSolucionService tiposolucionService;
    @Autowired
    private transient ProtocoloService protocoloService;
    @Autowired
    private transient HorarioEntregaService horarioEntregaService;

    @Autowired
    private transient TemplateEtiquetaService templateService;
    @Autowired
    private transient ImpresoraService impresoraService;
    @Autowired
    private transient MotivosRechazoService motivosRechazoService;
    @Autowired
    private transient TipoSolucionService tipoSolucionService;
    @Autowired
    private transient CamaService camaService;
    @Autowired
    private transient FrecuenciaService frecuenciaService;
    @Autowired
    private transient CatalogoGeneralService catalogoGeneralService;
    @Autowired
    private transient TurnoService turnoService;
    @Autowired
    private transient TipoUsuarioService tipoUsuarioService;
    @Autowired
    private transient PacienteUbicacionService pacienteUbicacionService;
    @Autowired
    private transient EnviaCorreoUtil enviaCorreoUtil;
    @Autowired
    private transient PlantillaCorreoService plantillaCorreoService;
    @Autowired
    private transient ConfigService configService;
    @Autowired
    private transient TipoJustificacionService tipoJustificacionService;
    @Autowired
    private transient HipersensibilidadService hipersensibilidadService;
    @Autowired
    private transient EquipoService equipoService;
    @Autowired
    private transient ReaccionService reaccionService;
    @Autowired
    private transient EstabilidadService estabilidadService;
    @Autowired
    private transient InventarioService inventarioService;

    private Solucion solucionSelect;
    private Prescripcion prescripcionSelected;

    private transient List<Integer> estatusSolucionLista;
    private String claveMezcla;
    private String loteMezcla;
    private Date caducidadMezcla;
    private Integer estabilidadMezcla;
    private SolucionUtils solUtils;
    
    private boolean insSanitDisp;
    private boolean insSanitPrep;
    private Date fechaInsSanitDisp;
    private Date fechaInsSanitPrep;
    private String idUsuarioInsSanitDisp;
    private String idUsuarioInsSanitPrep;
    private boolean sanitizacionCofirmada;

    /**
     * Consulta los permisos del usuario y obtiene lospacientes registrados
     */
    @PostConstruct
    public void init() {
        LOGGER.trace("mx.mc.magedbean.PreparacionSolucionMB.init()");
        inicializa();

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        this.sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        this.usuarioSelected = this.sesion.getUsuarioSelected();
        this.nombreCompleto = (this.usuarioSelected.getNombre() + " " + this.usuarioSelected.getApellidoPaterno() + " " + this.usuarioSelected.getApellidoMaterno()).toUpperCase();
        this.permiso = Comunes.obtenerPermisos(Transaccion_Enum.PREPARARSOLUCION.getSufijo());

        this.listEstatusSurtimiento = new ArrayList<>();
        this.estatusSurtimiento = EstatusSurtimiento_Enum.SURTIDO.getValue();
        this.listEstatusSurtimiento.add(this.estatusSurtimiento);

        this.esSolucion = true;
        this.estatusSolucionLista = new ArrayList<>();
        this.estatusSolucionLista.add(EstatusSolucion_Enum.OP_VALIDADA.getValue());

        try {
            this.solUtils = new SolucionUtils(estructuraService, surtimientoService, templateService, impresoraService, motivosRechazoService, prescripcionService, surtimientoInsumoService, solucionService, reportesService, entidadHospitalariaService, diagnosticoService, envaseService, viaAdministracionService, tipoSolucionService, protocoloService, visitaService, camaService, frecuenciaService, catalogoGeneralService, turnoService, tipoUsuarioService, pacienteServicioService, pacienteUbicacionService, enviaCorreoUtil, plantillaCorreoService, configService, tipoJustificacionService, medicamentoService, usuarioService, pacienteService, hipersensibilidadService, reaccionService, equipoService, estabilidadService, inventarioService);
            listServiciosQueSurte = this.solUtils.obtieneServiciosQuePuedeSurtir(usuarioSelected);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }

        limpia();
        obtenerIdTipoSolucion();
//        obtieneServiciosQuePuedeSurtir();
        obtenerSurtimientos();

    }
    

    private void inicializa() {
        LOGGER.trace("mx.mc.magedbean.PreparacionSolucionMB.inicializa()");
        surSinAlmacen = "sur.sin.almacen";
        paramEstatus = "estatus";
        errRegistroIncorrecto = "err.registro.incorrecto";
        surIncorrecto = "sur.incorrecto";
        pendiente = Constantes.SURTIDA;
        preparada = Constantes.PREPARADA;
        error_al_preparar = Constantes.ERROR_AL_PREPARAR;
        sobrante = 0;
        sobranteList = new ArrayList<>();
        paramBusquedaReporte = new ParamBusquedaReporte();
        folioSolucion = "";
        loteSolucion = "";
        caducidadSolucion = new Date();
        imprimeReporte = true;
        this.claveMezcla = "";
        this.loteMezcla = "";
        this.caducidadMezcla = new java.util.Date();
        this.estabilidadMezcla = 0;

    }

    /**
     * Obtiene los Servicios que puede surtir el usuario, dependiendo de la
     * estructura jerarquica a la que esté asignada el usuario en sesion
     * Adicionalmente El area a la que el usuario este asignada, debe ser de
     * tipo almacen y que tenga una asignación de servicio hospitalario queF
     * puede surtir
     */
    private void obtieneServiciosQuePuedeSurtir() {
        LOGGER.trace("mx.mc.magedbean.PreparacionSolucionMB.obtieneServiciosQuePuedeSurtir()");
        listServiciosQueSurte = new ArrayList<>();

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

    /**
     * Limpia las variables
     */
    private void limpia() {
        LOGGER.trace("mx.mc.magedbean.PreparacionSolucionMB.limpia()");
        elementoSeleccionado = false;
        huboError = false;
        cadenaBusqueda = null;
        fechaActual = new java.util.Date();
        tipoPrescripcionSelectedList = new ArrayList<>();
        surtimientoExtendedSelected = new Surtimiento_Extend();
        codigoBarras = null;
        nombreCompleto = "";
        tipoPrescripcion = "";
        msjMdlConfirmacion = true;
        solucion = new Solucion();
    }

    public void handleSelect(SelectEvent e) {
        medicamento = (Medicamento_Extended) e.getObject();
    }

    public void handleUnSelect() {
        medicamento = new Medicamento_Extended();
    }

    public void onTabChange(TabChangeEvent evt) {
// regla: Listar prescripciones solo con estatus de surtimiento surtido
        this.estatusSolucionLista = new ArrayList<>();

        String valorStatus = evt.getTab().getId();
        switch (valorStatus) {
            case Constantes.SURTIDA:
                this.estatusSolucionLista.add(EstatusSolucion_Enum.OP_VALIDADA.getValue());

                break;
            case Constantes.PREPARADA:
                this.estatusSolucionLista.add(EstatusSolucion_Enum.MEZCLA_PREPARADA.getValue());

                break;
            case Constantes.ERROR_AL_PREPARAR:
                this.estatusSolucionLista.add(EstatusSolucion_Enum.MEZCLA_ERROR_AL_PREPARAR.getValue());
                this.estatusSolucionLista.add(EstatusSolucion_Enum.MEZCLA_RECHAZADA.getValue());

                break;
        }
        obtenerSurtimientos();
    }

    public void obtenerSurtimientos() {
        LOGGER.trace("mx.mc.magedbean.PreparacionSolucionMB.obtenerSoluciones()");

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
                        estatusSolucionLista,
                         idTipoSolucion, tipoProceso, false
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
        LOGGER.trace("mx.mc.magedbean.PreparacionSolucionMB.onRowSelectSurtimiento()");
        surtimientoInsumoExtendedSelected = (SurtimientoInsumo_Extend) event.getObject();
        if (surtimientoInsumoExtendedSelected != null) {
            elementoSeleccionado = true;
        }
    }

    public void onRowUnselectSurtimiento() {
        LOGGER.trace("mx.mc.magedbean.PreparacionSolucionMB.onRowUnselectSurtimiento()");
        surtimientoInsumoExtendedSelected = null;
        elementoSeleccionado = false;
    }

    /**
     * Muestra el Detalle de cada surtimiento de cada prescripción
     */
    public void verSolucion() {
        LOGGER.trace("mx.mc.magedbean.PreparacionSolucionMB.verSolucion()");
        sanitizacionCofirmada = Constantes.INACTIVO;
        
        boolean status = Constantes.INACTIVO;
        boolean modal = Constantes.INACTIVO;
        this.surtimientoExtendedSelected.setInsSanitPrep(false);

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
                boolean insSanitPrep = surtimientoExtendedSelected.isInsSanitPrep();
                Date fechaInsSanitPrep = surtimientoExtendedSelected.getFechaInsSanitPrep();
                String idUsuarioInsSanitPrep = surtimientoExtendedSelected.getIdUsuarioInsSanitPrep();
                boolean mayorCero = true;
                surtimientoInsumoExtendedList.addAll(surtimientoInsumoService.obtenerSurtimientoInsumosByIdSurtimiento(idSurtimiento, mayorCero));
                for (SurtimientoInsumo_Extend item : surtimientoInsumoExtendedList) {
                    BigDecimal dosis = item.getDosis();
                    BigDecimal cocentracion = new BigDecimal( Double.parseDouble(item.getConcentracion()) * item.getCantidadEnviada());
                    switch (dosis.compareTo(cocentracion)) {
                        case -1:
                            item.setCantidadExcedente(cocentracion.subtract(dosis));
                            item.setIdTipoJustificante(3);
                            break;
                        case 0:
                            item.setCantidadExcedente(BigDecimal.ZERO);
                            item.setIdTipoJustificante(0);
                            break;
                        default:
                            item.setCantidadExcedente(dosis.subtract(cocentracion));
                            item.setIdTipoJustificante(2);
                            break;
                    }
                    List<SurtimientoEnviado_Extend> seList = enviadoService.detalleSurtimientoEnviadoXIdSurtimientoInsumo(item.getIdSurtimientoInsumo(), mayorCero);
                    item.setSurtimientoEnviadoExtendList(seList);
                    
                }
                obtenerPrescripcion();
                obtenerSolucion();
                obtenerMinimaEstabilidaInsumo();
                obtenerIdentificacionMezcla();
                calculaCaducidadMezcla();

// RN: Valida estabilidad de diluyente
//                List<String> msjEstabilidades = this.solUtils.evaluaEstabilidadDiluyente(surtimientoInsumoExtendedList, surtimientoExtendedSelected.getIdViaAdministracion());
//                for (String item : msjEstabilidades) {
//                    Mensaje.showMessage(Constantes.MENSAJE_WARN, item, null);
//                }

// RN: Valida estabilidad de Reconsitución, condiciones de conservacion, almaceamiento y observaciones de preparación
//                StringBuilder indicaciones = new StringBuilder();
//                msjEstabilidades = this.solUtils.evaluaEstabilidadReconstitucion(surtimientoInsumoExtendedList, surtimientoExtendedSelected.getIdViaAdministracion());
//                for (String item : msjEstabilidades) {
//                    if (indicaciones.length() > 0) {
//                        indicaciones.append(StringUtils.LF);
//                    }
//                    indicaciones.append(item);
//                }
//                if (indicaciones.length() > 0) {
//                    surtimientoExtendedSelected.setComentario(indicaciones.toString());
//                }
            

                evaluaEdicion();

                msjMdlConfirmacion = Constantes.ACTIVO;
                if(solucionSelect.getComentariosPreparacion() != null || solucionSelect.getComentariosPreparacion() != "") {
                    comentarios = solucionSelect.getComentariosPreparacion();
                } else {
                comentarios = "";
                }
                
                otro = Constantes.INACTIVO;
                status = Constantes.ACTIVO;
            } catch (Exception ex) {
                LOGGER.error(RESOURCES.getString(surIncorrecto), ex);
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surIncorrecto), null);
            }
        }
        PrimeFaces.current().ajax().addCallbackParam("modal", modal);
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    private void obtenerIdentificacionMezcla() {
        LOGGER.trace("mx.mc.magedbean.PreparacionSolucionMB.obtenerIdentificacionMezcla()");
        if (this.surtimientoExtendedSelected != null) {
            if (this.surtimientoExtendedSelected.getClaveAgrupada() != null) {
                CodigoInsumo cin = CodigoBarras.parsearCodigoDeBarras(this.surtimientoExtendedSelected.getClaveAgrupada());
                if (cin != null) {
                    this.claveMezcla = cin.getClave();
                    this.loteMezcla = cin.getLote();
                    this.caducidadMezcla = this.caducidadMezcla = FechaUtil.sumarRestarHorasFecha(new java.util.Date(), estabilidadMezcla);
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

    private void obtenerMinimaEstabilidaInsumo() {
        LOGGER.trace("mx.mc.magedbean.PreparacionSolucionMB.obtenerMinimaEstabilidaInsumo()");
        
        this.estabilidadMezcla = this.solUtils.obtenerMinimaEstabilidaInsumo(
                surtimientoInsumoExtendedList
                , surtimientoExtendedSelected.getIdViaAdministracion());
        
//        Integer estabilidadProxima = 24;
//        boolean encontrado = false;
//        Estabilidad e = null;
//        try {
//            SurtimientoInsumo_Extend reactivo = null;
//            SurtimientoInsumo_Extend diluyente = null;
//            for (SurtimientoInsumo_Extend sins : this.surtimientoInsumoExtendedList) {
//                Medicamento m = medicamentoService.obtenerMedicamento(sins.getIdInsumo());
//                if (m != null && m.getTipo() == CatalogoGeneral_Enum.MEDICAMENTO.getValue()) {
//                    if (!m.isDiluyente() && reactivo == null) {
//                        reactivo = sins;
//                    } else if (m.isDiluyente() && diluyente == null) {
//                        diluyente = sins;
//                    }
//                }
//            }
//            
//            if (reactivo != null && surtimientoExtendedSelected.getIdViaAdministracion() != null){
//                List<String> reactivosPrescritos = new ArrayList<>();
//                reactivosPrescritos.add(reactivo.getIdInsumo());
//                List<Estabilidad_Extended> estabilidadList = new ArrayList<>();
//                estabilidadList.addAll(estabilidadService.buscarEstabilidadFarmacos(reactivosPrescritos));
//                for (Estabilidad_Extended item : estabilidadList) {
//                    if (!encontrado) {
//    // Si la estabilidad del farmaco registrada contiene vía de administración y coincide con la prescripción
//                        if (Objects.equals(item.getIdViaAdministracion(), surtimientoExtendedSelected.getIdViaAdministracion())) {
//                            if (item.getReglasDePreparacion() != null) {
//                                e = item;
//                                encontrado = true;
//                                break;
//                            }
//                        }
//                    }
//                }
//            }
//        } catch (Exception ex){
//            LOGGER.error("Error al buscar estabilidad :: {} ", ex.getMessage());
//        }
//        
//        if (encontrado) {
//            estabilidadProxima = e.getLimHrsUsoRedFria();
//            
//        } else {
//            if (this.surtimientoInsumoExtendedList != null) {
//                for (SurtimientoInsumo_Extend item : this.surtimientoInsumoExtendedList) {
//                    if (item.getNoHorasestabilidad() != null) {
//                        if (item.getNoHorasestabilidad() > 0) {
//                            if (item.getNoHorasestabilidad() < estabilidadProxima) {
//                                estabilidadProxima = item.getNoHorasestabilidad();
//                            }
//                        }
//                    }
//                }
//            }
//            this.estabilidadMezcla = estabilidadProxima;
//        }
    }

    private void obtenerPrescripcion() {
        LOGGER.trace("mx.mc.magedbean.PreparacionSolucionMB.obtenerPrescripcion()");
        prescripcionSelected = new Prescripcion();
        try {
            if (surtimientoExtendedSelected != null) {
                prescripcionSelected = prescripcionService.obtener(new Prescripcion(surtimientoExtendedSelected.getIdPrescripcion()));
            }
        } catch (Exception e) {
            LOGGER.error("Error al obtenerPrescripcion :: {} ", e.getMessage());
        }
    }

//    private List<String> obtenerDiagnosticos() {
//        LOGGER.error("mx.mc.magedbean.PreparacionSolucionMB.obtenerDiagnosticos()");
//        List<String> diagnosticoLista = new ArrayList<>();
//        try {
//            if (surtimientoExtendedSelected != null) {
//                if (surtimientoExtendedSelected.getIdPaciente() != null) {
//                    Visita v = visitaService.obtener(new Visita(surtimientoExtendedSelected.getIdPaciente()));
//                    if (v != null) {
//                        List<Diagnostico> listDiagnostico = diagnosticoService.obtenerPorIdPacienteIdVisitaIdPrescripcion(
//                                surtimientoExtendedSelected.getIdPaciente(),
//                                v.getIdVisita(),
//                                prescripcionSelected.getIdPrescripcion());
//                        if (listDiagnostico != null) {
//                            for (Diagnostico d : listDiagnostico) {
//                                diagnosticoLista.add(d.getClave() + " - " + d.getNombre());
//                            }
//                        }
//                    }
//                }
//            }
//        } catch (Exception e) {
//            LOGGER.error("Error al obtenerDiagnosticos {} ", e.getMessage());
//        }
//        return diagnosticoLista;
//    }
    private void evaluaEdicion() {
        LOGGER.trace("mx.mc.magedbean.PreparacionSolucionMB.evaluaEdicion()");
        this.editable = false;
        if (this.solucionSelect != null) {
            if (this.solucionSelect.getIdEstatusSolucion() != null) {
                if (permiso.isPuedeAutorizar()
                        || ((permiso.isPuedeCrear() || permiso.isPuedeEditar())
                        && Objects.equals(this.solucionSelect.getIdEstatusSolucion(), EstatusSolucion_Enum.OP_VALIDADA.getValue()))) {
                    this.editable = true;
                }
            }
        }
    }

    private void obtenerSolucion() {
        LOGGER.trace("mx.mc.magedbean.PreparacionSolucionMB.obtenerSolucion()");
        Solucion s = new Solucion();
        try {
            if (this.surtimientoExtendedSelected != null) {
                s.setIdSurtimiento(surtimientoExtendedSelected.getIdSurtimiento());
                this.solucionSelect = solucionService.obtener(s);
                if (this.solucionSelect != null) {
                    if (solucionSelect.getIdEstatusSolucion() != null) {
                        this.surtimientoExtendedSelected.setEstatusSurtimiento(EstatusSolucion_Enum.getStatusFromId(solucionSelect.getIdEstatusSolucion()).name());
                    }
                    surtimientoExtendedSelected.setPesoPaciente(solucionSelect.getPesoPaciente());
                    surtimientoExtendedSelected.setTallaPaciente(solucionSelect.getTallaPaciente());
                    surtimientoExtendedSelected.setAreaCorporal(solucionSelect.getAreaCorporal());
                    surtimientoExtendedSelected.setObservaciones(solucionSelect.getObservaciones());
                    surtimientoExtendedSelected.setInstruccionPreparacion(solucionSelect.getInstruccionesPreparacion());
                    surtimientoExtendedSelected.setFechaParaEntregar(solucionSelect.getFechaParaEntregar());

                    surtimientoExtendedSelected.setFechaParaEntregar(solucionSelect.getFechaParaEntregar());
                    surtimientoExtendedSelected.setProteccionLuz(solucionSelect.getProteccionLuz() == 1);
                    surtimientoExtendedSelected.setTempAmbiente(solucionSelect.getProteccionTemp() == 1);
                    surtimientoExtendedSelected.setTempRefrigeracion(solucionSelect.getProteccionTempRefrig() == 1);
                    surtimientoExtendedSelected.setNoAgitar(solucionSelect.getNoAgitar() == 1);
                    surtimientoExtendedSelected.setIndicaciones(solucionSelect.getIndicaciones());

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
                            surtimientoExtendedSelected.setIdViaAdministracion(vad.getIdViaAdministracion());
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
//                    if (Objects.equals(solucionSelect.getIdEstatusSolucion(), EstatusSolucion_Enum.MEZCLA_PREPARADA.getValue())) {
//                        mezclaPreparada = 1;
//                    } else {
//                        mezclaPreparada = 0;
//                    }
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
            LOGGER.error("Error al obtenerProtocolo :: {} ", e.getMessage());
        }
        return null;
    }

    private Diagnostico obtenerDiagnostico(String idDiagnostico) {
        try {
            return diagnosticoService.obtenerDiagnosticoPorIdDiag(idDiagnostico);
        } catch (Exception e) {
            LOGGER.error("Error al obtenerDiagnostico :: {} ", e.getMessage());
        }
        return null;
    }

    public void validaSolucion() {
        LOGGER.trace("mx.mc.magedbean.PreparacionSolucionMB.validaSolucion()");
        boolean status = Constantes.INACTIVO;
        try {

            for (SurtimientoInsumo_Extend insumo : surtimientoInsumoExtendedList) {
                if (insumo.getSurtimientoEnviadoExtendList() == null || insumo.getSurtimientoEnviadoExtendList().isEmpty()) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Faltan insumos por escanear", null);
                    return;
                } else {
                    int sum = 0;
                    for (SurtimientoEnviado_Extend item : insumo.getSurtimientoEnviadoExtendList()) {
                        sum += item.getCantidadEnviado();
                    }
                    if (sum < insumo.getCantidadSolicitada() && insumo.getIdTipoJustificante() == null) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Faltan insumos por escanear", null);
                        return;
                    }
                }
            }

            Visita visita = new Visita();
            if (!surtimientoExtendedSelected.getPacienteNumero().equals("")) {
                Paciente patient = new Paciente();
                patient.setPacienteNumero(surtimientoExtendedSelected.getPacienteNumero());
                Paciente paciente = pacienteService.obtener(patient);

                Visita visit = new Visita();
                visit.setIdPaciente(paciente.getIdPaciente());
                visita = visitaService.obtenerVisitaAbierta(visit);
            }
            ParamBusquedaAlertaDTO alertaDTO = new ParamBusquedaAlertaDTO();
            alertaDTO.setFolioPrescripcion(surtimientoExtendedSelected.getFolioPrescripcion());
            alertaDTO.setNumeroPaciente(surtimientoExtendedSelected.getPacienteNumero());
            alertaDTO.setNumeroVisita(visita.getNumVisita());
            alertaDTO.setNumeroMedico(surtimientoExtendedSelected.getCedProfesional());

            List<Diagnostico> listDiagnostico = diagnosticoService.obtenerPorIdPacienteIdVisitaIdPrescripcion(visita.getIdPaciente(), visita.getIdVisita(), surtimientoExtendedSelected.getIdPrescripcion());
            List<String> diagnosticos = new ArrayList<>();
            for (Diagnostico diag : listDiagnostico) {
                diagnosticos.add(diag.getClave());
            }
            alertaDTO.setListaDiagnosticos(diagnosticos);

            List<MedicamentoDTO> listaMedicametos = new ArrayList<>();
            for (SurtimientoInsumo_Extend insumo : surtimientoInsumoExtendedList) {
                MedicamentoDTO item = new MedicamentoDTO();
                item.setClaveMedicamento(insumo.getClaveInstitucional());
                item.setDosis(insumo.getDosis());
                item.setDuracion(insumo.getDuracion());
                item.setFrecuencia(insumo.getFrecuencia());

                listaMedicametos.add(item);
            }
            alertaDTO.setListaMedicametos(listaMedicametos);

            ObjectMapper Obj = new ObjectMapper();
            String json = Obj.writeValueAsString(alertaDTO);
            ReaccionesAdversas cs = new ReaccionesAdversas(servletContext);
            Response respMus = cs.validaFarmacoVigilancia(json);

            if (respMus != null) {
                if (respMus.getStatus() == 200) {
//                    alertasDTO = parseResponseDTO(respMus.getEntity().toString());
                    status = true;
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("reacciones.error.guardar"), null);
                }
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("reacciones.error.guardar"), null);
            }

        } catch (Exception ex) {
            LOGGER.error("Error al validar la solucion :: {} ", ex.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    public void confirmaSolucion() {
        LOGGER.trace("mx.mc.magedbean.PreparacionSolucionMB.confirmaSolucion()");
        boolean status = Constantes.INACTIVO;
        try {
            if (!permiso.isPuedeCrear()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No tiene permiso para esta acción.", null);

            } else {
                if (surtimientoExtendedSelected == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El surtimiento de la mezcla es inválido.", null);

                } else if (solucionSelect == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "La mezcla es inválida", null);

//                } else if (mezclaPreparada == 0) {
//                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Confirme preparación de mezcla.", null);

                } else if (!Objects.equals(solucionSelect.getIdEstatusSolucion(), EstatusSolucion_Enum.OP_VALIDADA.getValue())) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Prescripción inválida.", null);

                } else {
                    Surtimiento surtimiento = new Surtimiento();
                    surtimiento.setIdSurtimiento(surtimientoExtendedSelected.getIdSurtimiento());
                    surtimiento.setUpdateFecha(new Date());
                    surtimiento.setUpdateIdUsuario(usuarioSelected.getIdUsuario());

                    List<SurtimientoInsumo> surInsumoLista = new ArrayList<>();
                    List<SurtimientoEnviado> surEnviadoLista = new ArrayList<>();
                    for (SurtimientoInsumo_Extend item : surtimientoInsumoExtendedList) {
                        SurtimientoInsumo insumo = new SurtimientoInsumo();
                        insumo.setIdSurtimientoInsumo(item.getIdSurtimientoInsumo());
                        insumo.setIdSurtimiento(item.getIdSurtimiento());
                        insumo.setUpdateFecha(new Date());
                        insumo.setUpdateIdUsuario(usuarioSelected.getIdUsuario());

                        if (item.getIdTipoJustificante() != 0 && item.getIdSurtimientoInsumo() != null && item.getIdInsumo() != null) {
                            boolean mayorCero = false;
                            List<SurtimientoEnviado_Extend> sel = enviadoService.detalleSurtimientoEnviadoXIdSurtimientoInsumo(item.getIdSurtimientoInsumo(), mayorCero);
                            if (sel != null) {
                                for (SurtimientoEnviado_Extend item2 : sel) {
                                    if (item.getLote().equals(item2.getLote())
                                            && item.getCaducidad().equals(item2.getCaducidad())
                                            && item.getIdInsumo().equals(item2.getIdInsumo())) {
                                        item2.setExisteSobrante(true);
                                        item2.setIdTipoJustificante(item.getIdTipoJustificante());
                                        item2.setCantidadExcedente(item.getCantidadExcedente());
                                        item2.setUpdateFecha(new java.util.Date());
                                        item2.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
                                    }
                                }
                                surEnviadoLista.addAll(sel);
                            }
                        }
                        surInsumoLista.add(insumo);
                    }

                    solucionSelect.setFechaPrepara(new Date());
                    solucionSelect.setIdUsuarioPrepara(usuarioSelected.getIdUsuario());
                    solucionSelect.setIdEstatusSolucion(EstatusSolucion_Enum.MEZCLA_PREPARADA.getValue());
                    solucionSelect.setUpdateFecha(new Date());
                    solucionSelect.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
                    solucionSelect.setClaveMezcla(this.claveMezcla);
                    solucionSelect.setLoteMezcla(this.loteMezcla);
                    this.caducidadMezcla = FechaUtil.sumarRestarHorasFecha(new java.util.Date(), estabilidadMezcla);
                    solucionSelect.setCaducidadMezcla(this.caducidadMezcla);
                    solucionSelect.setEstabilidadMezcla(this.estabilidadMezcla);
                    solucionSelect.setIdUsuarioInsSanitPrep(surtimientoExtendedSelected.getIdUsuarioInsSanitPrep());
                    solucionSelect.setFechaInsSanitPrep(surtimientoExtendedSelected.getFechaInsSanitPrep());
                    solucionSelect.setInsSanitPrep(surtimientoExtendedSelected.isInsSanitPrep());
                    solucionSelect.setComentariosPreparacion(comentarios);
                    status = solucionService.actualizaSolucion(solucionSelect, surtimiento, surInsumoLista, surEnviadoLista);
                    if (!status) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al confirmar la preparación.", null);
                    } else {
                        Mensaje.showMessage(Constantes.MENSAJE_INFO, "La confirmación se realizó correctamente.", null);
                    }

                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al actualizar confirmar la Preparación :: {} ", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al confirmar la Preparación.", null);
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    public void cancelarSolucion() {
        LOGGER.trace("mx.mc.magedbean.PreparacionSolucionMB.cancelarSolucion()");
        boolean status = Constantes.INACTIVO;
        try {
            if (!permiso.isPuedeEliminar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No tiene permiso para esta acción.", null);

            } else if (surtimientoExtendedSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El surtimiento de la mezcla es inválido.", null);
            
            } else if (solucion == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El surtimiento de la mezcla es inválido.", null);

            } else if (solucion.getIdMotivoCancelacion() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Especifique el motivo de rechazo.", null);

            } else if (Objects.equals(solucion.getIdMotivoCancelacion(), MotivoCancelacionRechazoSolucion_Enum.OTRO.getValue())
                    && solucion.getMotivoCancela().isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Especifique el motivo de rechazo.", null);

            } else {
                String idSolucion = null;
                String idSurtimiento = surtimientoExtendedSelected.getIdSurtimiento();
                solucionSelect = solucionService.obtenerSolucion(idSolucion, idSurtimiento);

                if (solucionSelect == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "La mezcla es inválida.", null);

                } else {
                    Surtimiento surtimiento; // = new Surtimiento();
                    surtimiento = surtimientoService.obtenerByIdSurtimiento(surtimientoExtendedSelected.getIdSurtimiento());
                    surtimiento.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.CANCELADO.getValue());
                    surtimiento.setUpdateFecha(new Date());
                    surtimiento.setUpdateIdUsuario(usuarioSelected.getIdUsuario());

                    List<SurtimientoInsumo> surInsumoLista = new ArrayList<>();
                    List<SurtimientoEnviado> surEnviadoLista = new ArrayList<>();
                    for (SurtimientoInsumo_Extend item : surtimientoInsumoExtendedList) {
                        SurtimientoInsumo insumo = item;
                        insumo.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.CANCELADO.getValue());
                        insumo.setUpdateFecha(new Date());
                        insumo.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
                        boolean mayorCero = true;
                        List<SurtimientoEnviado_Extend> sel = enviadoService.detalleSurtimientoEnviadoXIdSurtimientoInsumo(item.getIdSurtimientoInsumo(), mayorCero);
                        if (sel != null) {
                            for (SurtimientoEnviado_Extend item2 : sel) {
                                item2.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.CANCELADO.getValue());
                                item2.setInsertFecha(new java.util.Date());
                                item2.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                            }
                            surEnviadoLista.addAll(sel);
                        }
                        surInsumoLista.add(insumo);
                    }

                    solucionSelect.setFechaPrepara(new Date());
                    solucionSelect.setIdUsuarioPrepara(usuarioSelected.getIdUsuario());
                    solucionSelect.setComentariosPreparacion(comentarios);
                    Integer idEstatusSoIucion = this.solUtils.obtenerEstatusSolucionPorMotivoRechazo(solucion.getIdMotivoCancelacion());
                    solucionSelect.setIdEstatusSolucion(idEstatusSoIucion);
                    solucionSelect.setIdMotivoCancelacion(motivoCancel);
                    solucionSelect.setUpdateFecha(new Date());
                    solucionSelect.setUpdateIdUsuario(usuarioSelected.getIdUsuario());

                    status = solucionService.mezclaRechazoYReproceso(solucionSelect, surtimiento, surInsumoLista, surEnviadoLista);

                    if (!status) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al rechazar la mezcla.", null);
                    } else {
                        Mensaje.showMessage(Constantes.MENSAJE_INFO, "Mezcla rechazada.", null);
                        Prescripcion p = this.solUtils.obtenerPrescripcion(surtimientoExtendedSelected);
                        Usuario u = null;
                        if (p != null) {
                            String IdUsuario = solucionSelect.getIdUsuarioValPrescr();
                            u = this.solUtils.obtenerUsuarioPorId(IdUsuario);
                            if (u != null) {
                                String asunto = "Preparación de mezcla " + surtimientoExtendedSelected.getFolio() + " Cancelada. ";
                                String msj = "";
                                if (solucionSelect != null) {
                                    msj = solucionSelect.getComentariosCancelacion();

                                }
                                this.solUtils.enviarCorreo(u, this.usuarioSelected, asunto, msj, surtimientoExtendedSelected);
                            }
                        }
                        obtenerSurtimientos();
                        this.solucionSelect = new Solucion();
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al rechazar la mezcla :: {} ", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al rechazar la mezcla.", null);
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

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

    /**
     * Preimpresión de order de preparación
     *
     * @param surtimiento
     */
    public void previewPrint(Surtimiento_Extend surtimiento) {
        LOGGER.trace("mx.mc.magedbean.PreparacionSolucionMB.previewPrint()");
        boolean status = false;
        byte[] buffer = null;

        try {
            if (this.usuarioSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El usuario es inválido.", null);

            } else if (!permiso.isPuedeVer()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No tiene permiso para realizar esta acción.", null);

            } else if (surtimiento == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El surtimiento de la mezcla es inválido.", null);

            } else if (surtimiento.getIdPrescripcion() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El surtimiento de la mezcla es inválido.", null);

            } else if (surtimiento.getIdSurtimiento() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El surtimiento de la mezcla es inválido.", null);

            } else {

                this.surtimientoExtendedSelected = surtimientoService.obtenerByIdSurtimiento(surtimiento.getIdSurtimiento());
                if (this.surtimientoExtendedSelected == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El surtimiento de la prescripción de mezcla es inválido.", null);

                } else {
                    String nombreUsuario = usuarioSelected.getNombre() + " " + usuarioSelected.getApellidoPaterno() + " " + usuarioSelected.getApellidoMaterno();
                    Integer idTipoReporte = 3;
                    buffer = this.solUtils.imprimeDocumento(surtimiento, nombreUsuario, idTipoReporte);
                    if (buffer == null) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al generar la impresión de la orden de preparación.", null);

                    } else {
                        sesion.setReportStream(buffer);
                        sesion.setReportName(String.format("OrdenPreparacion_%s.pdf", surtimiento.getFolio()));
                        status = Constantes.ACTIVO;
                    }
                }
//                Prescripcion prescripcion = prescripcionService.obtener(new Prescripcion(surtimiento.getIdPrescripcion()));
//
//                if (prescripcion == null) {
//                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "La prescripción de mezcla es inválida.", null);
//
//                } else {
//
//                    this.surtimientoExtendedSelected = surtimientoService.obtenerByIdSurtimiento(surtimiento.getIdSurtimiento());
//                    if (this.surtimientoExtendedSelected == null) {
//                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El surtimiento de la mezcla es inválido.", null);
//
//                    } else {
//
//                        String idSolucion = null;
//                        String idSurtimiento = surtimiento.getIdSurtimiento();
//                        Solucion solucion = solucionService.obtenerSolucion(idSolucion, idSurtimiento);
//                        String nombreContenedor = "";
//                        if (solucion == null) {
//                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "La mezcla es inválida.", null);
//
//                        } else {
//
//                            this.surtimientoExtendedSelected.setEstatusSurtimiento(EstatusSurtimiento_Enum.getStatusFromId(surtimientoExtendedSelected.getIdEstatusSurtimiento()).name());
//
//                            Estructura estruct = estructuraService.obtenerEstructura(surtimiento.getIdEstructura());
//                            EntidadHospitalaria entidadHosp = null;
//                            if (estruct == null) {
//                                estruct = new Estructura();
//                                estruct.setIdEntidadHospitalaria("");
//
//                            } else {
//                                entidadHosp = entidadHospitalariaService.obtenerEntidadById(estruct.getIdEntidadHospitalaria());
//                                if (entidadHosp == null) {
//                                    entidadHosp = new EntidadHospitalaria();
//                                    entidadHosp.setDomicilio("");
//                                    entidadHosp.setNombre("");
//                                }
//
//                            }
//
//                            String listaDiagnosticos = "";
//                            List<Diagnostico> diagnosticolista = diagnosticoService.obtenerPorIdPacienteIdVisitaIdPrescripcion(
//                                    this.surtimientoExtendedSelected.getIdPaciente(),
//                                    this.surtimientoExtendedSelected.getIdVisita(),
//                                    this.surtimientoExtendedSelected.getIdPrescripcion());
//                            for (Diagnostico item : diagnosticolista) {
//                                if (item.getClave().length() > 2) {
//                                    listaDiagnosticos += " * " + item.getClave() + " - " + item.getNombre();
//                                }
//                            }
//
////TODO :: agregar las alergias del paciente
//                            String alergias = "";
//
//                            EnvaseContenedor contenedor = contenedor = envaseService.obtenerXidEnvase(solucion.getIdEnvaseContenedor());
//                            if (contenedor != null) {
//                                nombreContenedor = contenedor.getDescripcion();
//                            }
//
//                            String nombreUsuario = usuarioSelected.getNombre() + ' ' + usuarioSelected.getApellidoPaterno() + ' ' + usuarioSelected.getApellidoMaterno();
//
//                            buffer = reportesService.reporteValidacionSoluciones(
//                                    surtimiento,
//                                    solucion,
//                                    entidadHosp,
//                                    nombreUsuario,
//                                    alergias,
//                                    listaDiagnosticos,
//                                    nombreContenedor,
//                                    prescripcion
//                            );
//
//                            if (buffer == null) {
//                                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al generar la impresión de la orden de preparación.", null);
//
//                            } else {
//                                sesion.setReportStream(buffer);
//                                sesion.setReportName(String.format("OrdenPreparacion_%s.pdf", surtimiento.getFolio()));
//                                status = Constantes.ACTIVO;
//                            }
//                        }
//                    }
//                }

            }

        } catch (Exception ex) {
            LOGGER.error("Error al generar la impresión de la orden de preparación :: {}", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al generar la impresión de la orden de preparación.", null);
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    public void onRowSelectInsumo(SelectEvent event) {
        LOGGER.trace("mx.mc.magedbean.PrescripcionMB.onRowSelectInsumo()");
        surtimientoInsumoExtendedSelected = (SurtimientoInsumo_Extend) event.getObject();
        if (surtimientoInsumoExtendedSelected != null) {
            elementoSeleccionado = true;
        }
    }

    public void onRowUnselectInsumo() {
        LOGGER.trace("mx.mc.magedbean.PrescripcionMB.onRowUnselectInsumo()");
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

    public Integer getSizeSurtimientoExtendList() {
        if (surtimientoExtendedList != null) {
            return surtimientoExtendedList.size();
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

    public List<SurtimientoInsumo_Extend> getSurtimientoInsumoExtendedList() {
        return surtimientoInsumoExtendedList;
    }

    public void setSurtimientoInsumoExtendedList(List<SurtimientoInsumo_Extend> surtimientoInsumoExtendedList) {
        this.surtimientoInsumoExtendedList = surtimientoInsumoExtendedList;
    }

    public DispensacionSolucionLazy getDispensacionSolucionLazy() {
        return dispensacionSolucionLazy;
    }

    public void setDispensacionSolucionLazy(DispensacionSolucionLazy dispensacionSolucionLazy) {
        this.dispensacionSolucionLazy = dispensacionSolucionLazy;
    }

    public ParamBusquedaReporte getParamBusquedaReporte() {
        return paramBusquedaReporte;
    }

    public void setParamBusquedaReporte(ParamBusquedaReporte paramBusquedaReporte) {
        this.paramBusquedaReporte = paramBusquedaReporte;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public boolean isMsjMdlConfirmacion() {
        return msjMdlConfirmacion;
    }

    public void setMsjMdlConfirmacion(boolean msjMdlConfirmacion) {
        this.msjMdlConfirmacion = msjMdlConfirmacion;
    }

    public Integer getEstatusSurtimiento() {
        return estatusSurtimiento;
    }

    public void setEstatusSurtimiento(Integer estatusSurtimiento) {
        this.estatusSurtimiento = estatusSurtimiento;
    }

    public String getPendiente() {
        return pendiente;
    }

    public void setPendiente(String pendiente) {
        this.pendiente = pendiente;
    }

    public String getError_al_preparar() {
        return error_al_preparar;
    }

    public void setError_al_preparar(String error_al_preparar) {
        this.error_al_preparar = error_al_preparar;
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

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

    public String getPreparada() {
        return preparada;
    }

    public void setPreparada(String preparada) {
        this.preparada = preparada;
    }

    public List<String> getSobranteList() {
        return sobranteList;
    }

    public void setSobranteList(List<String> sobranteList) {
        this.sobranteList = sobranteList;
    }

    public Integer getSobrante() {
        return sobrante;
    }

    public void setSobrante(Integer sobrante) {
        this.sobrante = sobrante;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public boolean isMsjMdlCancelar() {
        return msjMdlCancelar;
    }

    public void setMsjMdlCancelar(boolean msjMdlCancelar) {
        this.msjMdlCancelar = msjMdlCancelar;
    }

    public Integer getMotivoCancel() {
        return motivoCancel;
    }

    public void setMotivoCancel(Integer motivoCancel) {
        this.motivoCancel = motivoCancel;
    }

    public boolean isOtro() {
        return otro;
    }

    public void setOtro(boolean otro) {
        this.otro = otro;
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

    private Date fechaParaEntregar;
    private Integer idHorarioParaEntregar;
    private transient List<HorarioEntrega> horarioEntregaLista;
    private HorarioEntrega horarioEntrega;

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

    public List<HorarioEntrega> getHorarioEntregaLista() {
        return horarioEntregaLista;
    }

    public void setHorarioEntregaLista(List<HorarioEntrega> horarioEntregaLista) {
        this.horarioEntregaLista = horarioEntregaLista;
    }

    public HorarioEntrega getHorarioEntrega() {
        return horarioEntrega;
    }

    public void setHorarioEntrega(HorarioEntrega horarioEntrega) {
        this.horarioEntrega = horarioEntrega;
    }

//    public Integer getMezclaPreparada() {
//        return mezclaPreparada;
//    }
//
//    public void setMezclaPreparada(Integer mezclaPreparada) {
//        this.mezclaPreparada = mezclaPreparada;
//    }

    private String idTipoSolucion;

    private void obtenerIdTipoSolucion() {
        try {
            Equipo equipo = null;
            String ipMaquina = solUtils.obtenerIpMaquina();
            if (ipMaquina != null) {
                equipo = solUtils.obtenerInformacionEquipo(ipMaquina);
            }
            if (equipo != null) {
                idTipoSolucion = equipo.getIdTipoSolucion();
            }
        } catch (Exception ex) {
            LOGGER.error("Error al obtener la lista de equipos  " + ex.getMessage());
        }
    }

    private Solucion solucion;

    /**
     * Realiza las acciones de validación y para rechazo de orden de preparación
     * prescripción
     */
    public void validaRechazo() {
        LOGGER.trace("mx.mc.magedbean.PreparacionSolucionMB.validaRechazo()");
        boolean status = false;
        this.solucion = new Solucion();

        if (this.usuarioSelected == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Usuario inválido.", null);

        } else if (this.usuarioSelected.getIdUsuario() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Usuario inválido.", null);

        } else if (!permiso.isPuedeEliminar()) {
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

            } else if (!Objects.equals(solucion.getIdEstatusSolucion(), EstatusSolucion_Enum.OP_VALIDADA.getValue())) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El estatus no permite registrar el rechazar.", null);

            } else {
                solucion.setComentariosRechazo("");
                status = true;
            }
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    public Solucion getSolucion() {
        return solucion;
    }

    public void setSolucion(Solucion solucion) {
        this.solucion = solucion;
    }

    public void rechazarValidacionSolucion() {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.rechazarValidacionSolucion()");
        boolean status = Constantes.INACTIVO;
        try {
            if (this.usuarioSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Usuario inválido.", null);

            } else if (!this.permiso.isPuedeEditar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No tiene permiso para realizar esta acción.", null);

            } else if (surtimientoExtendedSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Surtimiento de Mezcla inválido.", null);

            } else if (surtimientoExtendedSelected.getIdSurtimiento() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Surtimiento de Mezcla inválido.", null);

            } else if (!Objects.equals(surtimientoExtendedSelected.getIdEstatusSolucion(), EstatusSolucion_Enum.OP_VALIDADA.getValue())) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Estatus de Mezcla inválido.", null);

            } else if (solucion == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Datos de rechazo de mezcla inválido.", null);

            } else if (solucion.getIdMotivoRechazo() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Motivo de rechazo de mezcla inválido.", null);

            } else if (solucion.getComentariosRechazo() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Registre comentarios.", null);

            } else {

                Surtimiento surtimiento = new Surtimiento();
                surtimiento.setIdSurtimiento(surtimientoExtendedSelected.getIdSurtimiento());
                surtimiento.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                surtimiento.setUpdateFecha(new Date());
                surtimiento.setUpdateIdUsuario(usuarioSelected.getIdUsuario());

                String idSolucion = null;
                String idSurtimiento = this.surtimientoExtendedSelected.getIdSurtimiento();
                Solucion o = this.solUtils.obtenerSolucion(idSolucion, idSurtimiento);

                if (o == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Solución inválida.", null);

                } else if (!Objects.equals(o.getIdEstatusSolucion(), EstatusSolucion_Enum.OP_VALIDADA.getValue())) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Estatus de solución no permite el rechazo.", null);

                } else {
                    Solucion sol = new Solucion();
                    sol.setIdSolucion(o.getIdSolucion());
                    sol.setUpdateFecha(new java.util.Date());
                    sol.setUpdateIdUsuario(usuarioSelected.getIdUsuario());

                    sol.setFechaPrepara(new java.util.Date());
                    sol.setIdUsuarioRechaza(usuarioSelected.getIdUsuario());

                    Integer idEstatusSolucion = this.solUtils.obtenerEstatusSolucionPorMotivoRechazo(solucion.getIdMotivoRechazo());
                    sol.setIdEstatusSolucion(idEstatusSolucion);
                    sol.setIdUsuarioValOrdenPrep(usuarioSelected.getIdUsuario());

                    status = solucionService.rechazarMezcla(sol, surtimiento, surtimientoInsumoExtendedList, false);

                    if (!status) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("reacciones.error.guardar"), null);

                    } else {
                        Mensaje.showMessage(Constantes.MENSAJE_INFO, "Orden de Preparación Rechazada.", null);

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
                            String IdUsuario = o.getIdUsuarioValOrdenPrep();
                            u = this.solUtils.obtenerUsuarioPorId(IdUsuario);
                            if (u != null) {
                                String asunto = "Mezcla " + surtimientoExtendedSelected.getFolio() + " Rechazada. ";
                                String msj = sol.getComentariosRechazo();
                                this.solUtils.enviarCorreo(u, this.usuarioSelected, asunto, msj, surtimientoExtendedSelected);
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("ocurrio un error en: rechazarValidacionSolucion", ex);
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }
    
    /**
     * Realiza las acciones de validación y para cancelación de orden de preparación
     */
    public void validaCancelar() {
        LOGGER.trace("mx.mc.magedbean.PreparacionSolucionMB.validaCancelar()");
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

            } else if (!Objects.equals(solucion.getIdEstatusSolucion(), EstatusSolucion_Enum.OP_VALIDADA.getValue())
                    && !Objects.equals(solucion.getIdEstatusSolucion(), EstatusSolucion_Enum.MEZCLA_PREPARADA.getValue())
                    && !Objects.equals(solucion.getIdEstatusSolucion(), EstatusSolucion_Enum.MEZCLA_RECHAZADA.getValue())
                    && !Objects.equals(solucion.getIdEstatusSolucion(), EstatusSolucion_Enum.MEZCLA_ERROR_AL_PREPARAR.getValue())) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El estatus no permite cancelación.", null);

            } else {
                status = true;
            }
        }
        this.solucion = new Solucion();
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    public void confirmarSanitizacion (){
        LOGGER.trace("mx.mc.magedbean.PreparacionSolucionMB.confirmarSanitizacion()");
        if (surtimientoExtendedSelected != null){
            sanitizacionCofirmada = surtimientoExtendedSelected.isInsSanitPrep();
            surtimientoExtendedSelected.setIdUsuarioInsSanitPrep(usuarioSelected.getIdUsuario());
            surtimientoExtendedSelected.setFechaInsSanitPrep(new java.util.Date());
        }
    }

    public boolean isSanitizacionCofirmada() {
        return sanitizacionCofirmada;
    }

    public void setSanitizacionCofirmada(boolean sanitizacionCofirmada) {
        this.sanitizacionCofirmada = sanitizacionCofirmada;
    }
    
    
    
}
