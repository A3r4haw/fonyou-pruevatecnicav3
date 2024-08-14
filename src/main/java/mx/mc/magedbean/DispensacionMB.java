package mx.mc.magedbean;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Response;
import mx.mc.dto.MedicamentoDTO;
import mx.mc.dto.ParamBusquedaAlertaDTO;
import mx.mc.dto.RespuestaAlertasDTO;
import mx.mc.dto.ValidacionNoCumplidaDTO;
import mx.mc.lazy.DispensacionLazy;
import mx.mc.enums.EstatusPaciente_Enum;
import mx.mc.enums.EstatusPrescripcion_Enum;
import mx.mc.enums.EstatusSurtimiento_Enum;
import mx.mc.enums.TipoAreaEstructura_Enum;
import mx.mc.enums.TipoConsulta_Enum;
import mx.mc.enums.TipoJustificacion_Enum;
import mx.mc.enums.TipoMotivo_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.init.CustomWebSecurityConfigurerAdapter;
import mx.mc.model.AlertaFarmacovigilancia;
import mx.mc.model.CantidadRazonada;
import mx.mc.model.CantidadRazonadaExtended;
import mx.mc.model.Capsula;
import mx.mc.model.CodigoInsumo;
import mx.mc.model.Diagnostico;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.EnvioNeumatico;
import mx.mc.model.Estructura;
import mx.mc.model.Inventario;
import mx.mc.model.Medicamento_Extended;
import mx.mc.model.MovimientoInventario;
import mx.mc.model.Paciente;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.RepSurtimientoPresc;
import mx.mc.model.Surtimiento;
import mx.mc.model.SurtimientoEnviado;
import mx.mc.model.SurtimientoEnviado_Extend;
import mx.mc.model.SurtimientoInsumo;
import mx.mc.model.SurtimientoInsumo_Extend;
import mx.mc.model.Surtimiento_Extend;
import mx.mc.model.TipoJustificacion;
import mx.mc.model.TransaccionPermisos;
import mx.mc.model.Usuario;
import mx.mc.service.CantidadRazonadaService;
import mx.mc.service.CapsulaService;
import mx.mc.service.DiagnosticoService;
import mx.mc.service.EntidadHospitalariaService;
import mx.mc.service.EnvioNeumaticoService;
import mx.mc.service.EstructuraService;
import mx.mc.service.InventarioService;
import mx.mc.service.MedicamentoService;
import mx.mc.service.PacienteService;
import mx.mc.service.ReportesService;
import mx.mc.service.SurtimientoInsumoService;
import mx.mc.service.SurtimientoService;
import mx.mc.service.TipoJustificacionService;
import mx.mc.service.TransaccionService;
import mx.mc.service.UsuarioService;
import mx.mc.util.CodigoBarras;
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
public class DispensacionMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(DispensacionMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);       
    private boolean editable;
    private boolean huboError;
    private String cadenaBusqueda;
    private String cadenaBusquedaS;
    private String cadenaBusquedaC;
    private boolean elementoSeleccionado;
    private Date fechaActual;
    private Usuario usuarioSelected;
    private String userResponsable;
    private boolean authorizado;
    private boolean exist;
    private String passResponsable;
    private String idResponsable;
    private String nombreCompleto;
    private boolean authorization;
    private boolean msjMdlSurtimiento;
    private String msjAlert;
    private boolean status;
    private boolean activaAutoCompleteInsumos;
    private String surSinAlmacen;
    private String surAlmacenIncorrectado;
    private String errTransaccion;
    private String prcPacLista;
    private String paramModal;   
    private String errRegistroIncorrecto;
    private String surIncorrecto;
    private String surCaducidadvencida;
    private String surLoteIncorrecto;
    private String surInvalido;
    private List<Estructura> listServiciosQueSurte;
    private Medicamento_Extended medicamento;
    private List<Medicamento_Extended> medicamentoList;
    private DispensacionLazy dispensacionLazy;
    private DispensacionLazy dispensacionLazyS;
    private DispensacionLazy dispensacionLazyC;
    private ParamBusquedaReporte paramBusquedaReporte;
    private Surtimiento_Extend surtimientoExtendedSelected;
    private Surtimiento_Extend surtimientoExtendedSelectedS;
    private Surtimiento_Extend surtimientoExtendedSelectedC;
    private List<Surtimiento_Extend> surtimientoExtendedList;
    private SurtimientoInsumo_Extend surtimientoInsumoExtendedSelected;
    private List<SurtimientoInsumo_Extend> surtimientoInsumoExtendedList;
    private List<TransaccionPermisos> permisosList;
    private String tipoPrescripcion;
    private List<String> tipoPrescripcionSelectedList;
    private String codigoBarras;
    private boolean eliminaCodigoBarras;
    private String codigoBarrasAutorizte;
    private Integer xcantidadAutorizte;
    private PermisoUsuario permiso;
    private List<TipoJustificacion> justificacionList;    
    private Integer xcantidad;
    private Integer noDiasCaducidad;
    private SesionMB sesion;
    
    //</editor-fold>
    /**
     * Oliver Submodulo Capsulas
     */
    private int activo;
    private String capsula;
    private String codigoCapsula;
    private String estructura;
    private List<Capsula> idCapsula;
    private String codigoCpausla;
    private int idenvioNeumatico;
    private boolean capsulaDisponible;
    private String nombre;
    private List<Capsula> listaCapsulas;
    private boolean funValidarFarmacoVigilancia;
    private RespuestaAlertasDTO alertasDTO;       

    @Autowired
    private transient EstructuraService estructuraService;    

    @Autowired
    private transient SurtimientoService surtimientoService;
    
    @Autowired
    private transient SurtimientoInsumoService surtimientoInsumoService;
    
    @Autowired
    private transient CantidadRazonadaService cantidadRazonadaService;
    
    @Autowired
    private transient PacienteService pacienteService;
    
    @Autowired
    private transient EnvioNeumaticoService envioNeumaticoService;
    
    @Autowired
    private transient EntidadHospitalariaService entidadHospitalariaService;
    
    @Autowired
    private transient TransaccionService transaccionService;    

    @Autowired
    private transient CapsulaService capsulaService;

    @Autowired
    private transient MedicamentoService medicamentoServiceautoComplete;

    @Autowired
    private transient UsuarioService usuarioService;        
    
    @Autowired
    private transient TipoJustificacionService tipoJustificacionService;
    
    @Autowired
    private transient InventarioService inventarioService;

    @Autowired
    private transient ReportesService reportesService;
    
    @Autowired
    private transient ServletContext servletContext;
    
    @Autowired
    private transient DiagnosticoService diagnosticoService;

    /**
     * Consulta los permisos del usuario y obtiene lospacientes registrados
     */
    @PostConstruct
    public void init() {
        LOGGER.debug("mx.mc.magedbean.DispensacionMB.init()");
        surSinAlmacen = "sur.sin.almacen";
        surAlmacenIncorrectado = "sur.almacen.incorrectado";
        errTransaccion = "err.transaccion";
        prcPacLista = "prc.pac.lista";
        paramModal = "modal";
        errRegistroIncorrecto = "err.registro.incorrecto";
        surIncorrecto = "sur.incorrecto";
        surCaducidadvencida = "sur.caducidadvencida";
        surLoteIncorrecto = "sur.loteincorrecto";
        surInvalido = "sur.invalido";

        limpia();
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        capsulaDisponible = sesion.isCapsulaDisponible();
        paramBusquedaReporte = new ParamBusquedaReporte();
        usuarioSelected = Comunes.obtenerUsuarioSesion();
        noDiasCaducidad = sesion.getNoDiasCaducidad();
        funValidarFarmacoVigilancia = sesion.isFunValidarFarmacoVigilancia();
        permiso = Comunes.obtenerPermisos(Transaccion_Enum.DISPENSA_PRESCRIPCION.getSufijo());
        obtieneServiciosQuePuedeSurtir();
        obtenerSurtimientos();
        obtenerSurtimientosSurtidos();
        obtenerSurtimientosCancelados();
        obtenerJustificacion();
        activaAutoCompleteInsumos = sesion.isActivaAutocompleteQrClave();
    }

    /**
     * Obtiene los Servicios que puede surtir el usuario, dependiendo de la
     * estructura jerarquica a la que esté asignada el usuario en sesion
     * Adicionalmente El area a la que el usuario este asignada, debe ser de
     * tipo almacen y que tenga una asignación de servicio hospitalario queF
     * puede surtir
     */
    private void obtieneServiciosQuePuedeSurtir() {
        LOGGER.debug("mx.mc.magedbean.DispensacionMB.obtieneServiciosQuePuedeSurtir()");
        listServiciosQueSurte = new ArrayList<>();
        permiso.setPuedeVer(false);

        Estructura est = null;
        try {
            est = estructuraService.obtener(new Estructura(usuarioSelected.getIdEstructura()));

        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }

        if (est == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surSinAlmacen), null);

        } else if (est.getIdTipoAreaEstructura() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surSinAlmacen), null);

        } else if (!Objects.equals(est.getIdTipoAreaEstructura(), TipoAreaEstructura_Enum.ALMACEN.getValue())) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surAlmacenIncorrectado), null);

        } else {
            permiso.setPuedeVer(true);
            try {
                if (usuarioSelected.getIdEstructura() != null) {
                    List<Estructura> estructuraServicioDis = estructuraService.obtenerServicioQueSurtePorIdEstructura(usuarioSelected.getIdEstructura());
                    for(Estructura servicio : estructuraServicioDis){                        
                        listServiciosQueSurte.add(servicio);
                        List<String> idsEstructura = estructuraService.obtenerIdsEstructuraJerarquica(servicio.getIdEstructura(), true);
                        for (String item : idsEstructura) {
                            listServiciosQueSurte.add(estructuraService.obtenerEstructura(item));
                        }
                    }
                }
            } catch (Exception ext) {
                LOGGER.error("Error al obtener Servicios que puede surtir el usuario: {}", ext.getMessage());
            }
        }

    }

    
    public List<Medicamento_Extended> autoCompleteMedicamentos(String cadena) {
        List<Medicamento_Extended> listaInsumos = new ArrayList<>();
        try {
            listaInsumos = medicamentoServiceautoComplete.searchMedicamentoAutoComplete(cadena.trim(), usuarioSelected.getIdEstructura(), activaAutoCompleteInsumos);
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al consultar el medicamento");
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("medicamento.err.autocomplete"), null);
        }
        return listaInsumos;
    }
    
    
    /**
     * Limpia las variables
     */
    private void limpia() {
        LOGGER.debug("mx.mc.magedbean.DispensacionMB.limpia()");        

        cadenaBusqueda = null;
        cadenaBusquedaS = null;
        cadenaBusquedaC = null;
        elementoSeleccionado = false;
        huboError = false;       
        msjMdlSurtimiento = true;
        exist = false;
        fechaActual = new java.util.Date();
        usuarioSelected = new Usuario();
        permisosList = new ArrayList<>();
        usuarioSelected.setPermisosList(permisosList);
        tipoPrescripcion = "";
        nombreCompleto = "";
        userResponsable = "";
        passResponsable = "";
        idResponsable = "";        
        msjAlert = "";
        tipoPrescripcionSelectedList = new ArrayList<>();
        codigoBarras = null;
        eliminaCodigoBarras = false;
        xcantidad = 1;
        surtimientoExtendedSelected = new Surtimiento_Extend(); 
    }
   

    public List<Medicamento_Extended> autoComplete(String cadena) {
        try {
            String idEstructura = surtimientoService.obtenerAlmacenPadreOfSurtimiento(usuarioSelected.getIdEstructura());
            if (idEstructura == null || idEstructura.isEmpty()) {
                idEstructura = usuarioSelected.getIdEstructura();
            }
            medicamentoList = medicamentoServiceautoComplete.searchMedicamentoAutoComplete(cadena.trim(), idEstructura, activaAutoCompleteInsumos);
            if (!this.eliminaCodigoBarras && !medicamentoList.isEmpty()) {
                List<Medicamento_Extended> medicamentos = medicamentoList;
                for (SurtimientoInsumo_Extend surtimientoInsumo : surtimientoInsumoExtendedList) {
                    if (surtimientoInsumo.getSurtimientoEnviadoExtendList() != null) {
                        for (SurtimientoEnviado_Extend surtimientoEnviado : surtimientoInsumo.getSurtimientoEnviadoExtendList()) {
                            for (Medicamento_Extended medicament : medicamentos) {
                                if (medicament.getLote().equals(surtimientoEnviado.getLote())
                                        && medicament.getFechaCaducidad().equals(surtimientoEnviado.getCaducidad())) {
                                    int result = medicament.getCantidadActual() - surtimientoEnviado.getCantidadEnviado();
                                    if (result <= 0) {
                                        medicamentoList.remove(medicament);
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception exc) {
            LOGGER.error("Error al obtener Medicamentos: {}", exc.getMessage());
        }
        if (medicamentoList.size() == 1) {
            String componentId = UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getClientId();
            String panel = componentId.replace(":", "\\\\:") + "_panel";
            PrimeFaces.current().executeScript("$('#" + panel + " .ui-autocomplete-item').trigger('click');");
        }
        return medicamentoList;
    }

    public void handleUnSelect() {
        medicamento = new Medicamento_Extended();
    }

    public void handleSelect(SelectEvent e) {
        medicamento = (Medicamento_Extended) e.getObject();
    }


    /**
     * Obtiene la lista de Surtimientos Programados
     */
    public void obtenerSurtimientos() {
        LOGGER.debug("mx.mc.magedbean.DispensacionMB.obtenerSurtimientos()");

        boolean estado = Constantes.INACTIVO;
        boolean modal = Constantes.INACTIVO;
        surtimientoExtendedList = new ArrayList<>();

        if (!permiso.isPuedeVer()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errTransaccion), null);
            estado = Constantes.ACTIVO;

        } else if (usuarioSelected == null || usuarioSelected.getIdEstructura() == null)  {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surSinAlmacen), null);
            estado = Constantes.ACTIVO;

        } else if (listServiciosQueSurte.isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surAlmacenIncorrectado), null);

        } else {
            try {
                if (cadenaBusqueda != null && cadenaBusqueda.trim().isEmpty()) {
                    cadenaBusqueda = null;
                }

// regla: Listar prescripciones donde el paciente tiene estatus: Asignado a Servicio o Asignación a Cama
                List<Integer> statusPacienteList = new ArrayList<>();
                statusPacienteList.add(EstatusPaciente_Enum.ASIGNADO_A_SERVICIO.getValue());
                statusPacienteList.add(EstatusPaciente_Enum.ASIGNADO_A_CAMA.getValue());

// regla: Listar prescripciones solo con estatus de PROGRAMADA
                List<Integer> statusPrescripcionList = new ArrayList<>();
                statusPrescripcionList.add(EstatusPrescripcion_Enum.PROGRAMADA.getValue());
                statusPrescripcionList.add(EstatusPrescripcion_Enum.FINALIZADA.getValue());

// regla: Listar prescripciones solo con estatus de surtimiento programado
                List<Integer> statusSurtimientoList = new ArrayList<>();
                statusSurtimientoList.add(EstatusSurtimiento_Enum.PROGRAMADO.getValue());

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

                dispensacionLazy = new DispensacionLazy(
                        surtimientoService,
                        paramBusquedaReporte,
                        fechaProgramada,
                        tipoPrescripcionSelectedList,
                        statusPacienteList,
                        statusPrescripcionList,
                        statusSurtimientoList,
                        listServiciosQueSurte);

                LOGGER.debug("Resultados: {}", dispensacionLazy.getTotalReg());

                estado = Constantes.ACTIVO;
            } catch (Exception ex) {
                LOGGER.error(RESOURCES.getString(prcPacLista), ex);
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcPacLista), null);
            }
        }
        PrimeFaces.current().ajax().addCallbackParam(paramModal, modal);
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, estado);
    }

    /**
     * Obtiene la lista de Surtimientos Surtidos
     */
    public void obtenerSurtimientosSurtidos() {
        LOGGER.debug("mx.mc.magedbean.DispensacionMB.obtenerSurtimientosSurtidos()");

        boolean estatus = Constantes.INACTIVO;
        boolean modal = Constantes.INACTIVO;
        surtimientoExtendedList = new ArrayList<>();

        if (!permiso.isPuedeVer()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errTransaccion), null);
            estatus = Constantes.ACTIVO;

        } else if (usuarioSelected == null || usuarioSelected.getIdEstructura() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surSinAlmacen), null);
            estatus = Constantes.ACTIVO;

        } else if (listServiciosQueSurte.isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surAlmacenIncorrectado), null);

        } else {
            try {
                if (cadenaBusquedaS != null && cadenaBusquedaS.trim().isEmpty()) {
                    cadenaBusquedaS = null;
                }

// regla: Listar prescripciones donde el paciente tiene estatus: Asignado a Servicio o Asignación a Cama
                List<Integer> listEstatusPatient = new ArrayList<>();
                listEstatusPatient.add(EstatusPaciente_Enum.ASIGNADO_A_SERVICIO.getValue());
                listEstatusPatient.add(EstatusPaciente_Enum.ASIGNADO_A_CAMA.getValue());

// regla: Listar prescripciones solo con estatus de PROGRAMADA
                List<Integer> listPrescripcionEstatus = new ArrayList<>();
                listPrescripcionEstatus.add(EstatusPrescripcion_Enum.PROGRAMADA.getValue());
                listPrescripcionEstatus.add(EstatusPrescripcion_Enum.FINALIZADA.getValue());
                listPrescripcionEstatus.add(EstatusPrescripcion_Enum.PROCESANDO.getValue());
                listPrescripcionEstatus.add(EstatusPrescripcion_Enum.SURTIDA_PARCIAL.getValue());

// regla: Listar prescripciones solo con estatus de surtimiento programado
                List<Integer> listStatusSurtimiento = new ArrayList<>();
                listStatusSurtimiento.add(EstatusSurtimiento_Enum.SURTIDO.getValue());
                listStatusSurtimiento.add(EstatusSurtimiento_Enum.EN_TRANSITO.getValue());
                listStatusSurtimiento.add(EstatusSurtimiento_Enum.RECIBIDO.getValue());

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
                paramBusquedaReporte.setCadenaBusqueda(cadenaBusquedaS);

                dispensacionLazyS = new DispensacionLazy(
                        surtimientoService,
                        paramBusquedaReporte,
                        fechaProgramada,
                        tipoPrescripcionSelectedList,
                        listEstatusPatient,
                        listPrescripcionEstatus,
                        listStatusSurtimiento,
                        listServiciosQueSurte);

                LOGGER.debug("Resultados: {}", dispensacionLazyS.getTotalReg());

                estatus = Constantes.ACTIVO;
            } catch (Exception ex) {
                LOGGER.error(RESOURCES.getString(prcPacLista), ex);
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcPacLista), null);
            }
        }
        PrimeFaces.current().ajax().addCallbackParam(paramModal, modal);
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, estatus);
    }

    /**
     * Obtiene la lista de Surtimientos Cancelados
     */
    public void obtenerSurtimientosCancelados() {
        LOGGER.debug("mx.mc.magedbean.DispensacionMB.obtenerSurtimientosCancelados()");

        boolean statuss = Constantes.INACTIVO;
        boolean modal = Constantes.INACTIVO;
        surtimientoExtendedList = new ArrayList<>();

        if (!permiso.isPuedeVer()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errTransaccion), null);
            statuss = Constantes.ACTIVO;

        } else if (usuarioSelected == null || usuarioSelected.getIdEstructura() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surSinAlmacen), null);
            statuss = Constantes.ACTIVO;

        } else if (listServiciosQueSurte.isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surAlmacenIncorrectado), null);

        } else {
            try {
                if (cadenaBusquedaC != null && cadenaBusquedaC.trim().isEmpty()) {
                    cadenaBusquedaC = null;
                }

// regla: Listar prescripciones donde el paciente tiene estatus: Asignado a Servicio o Asignación a Cama
                List<Integer> listEstatusPaciente = new ArrayList<>();
                listEstatusPaciente.add(EstatusPaciente_Enum.ASIGNADO_A_SERVICIO.getValue());
                listEstatusPaciente.add(EstatusPaciente_Enum.ASIGNADO_A_CAMA.getValue());

                List<Integer> listEstatusPrescripcion = null;

// regla: Listar prescripciones solo con estatus de surtimiento programado
                List<Integer> listEstatusSurtimiento = new ArrayList<>();
                listEstatusSurtimiento.add(EstatusSurtimiento_Enum.CANCELADO.getValue());

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
                paramBusquedaReporte.setCadenaBusqueda(cadenaBusquedaC);

                dispensacionLazyC = new DispensacionLazy(
                        surtimientoService,
                        paramBusquedaReporte,
                        fechaProgramada,
                        tipoPrescripcionSelectedList,
                        listEstatusPaciente,
                        listEstatusPrescripcion,
                        listEstatusSurtimiento,
                        listServiciosQueSurte);

                LOGGER.debug("Resultados: {}", dispensacionLazyC.getTotalReg());

                statuss = Constantes.ACTIVO;
            } catch (Exception ex) {
                LOGGER.error(RESOURCES.getString(prcPacLista), ex);
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcPacLista), null);
            }
        }
        PrimeFaces.current().ajax().addCallbackParam(paramModal, modal);
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, statuss);
    }

    /**
     * Obtener listado de Justificación
     */
    private void obtenerJustificacion() {
        LOGGER.debug("mx.mc.magedbean.DispensacionMB.obtenerJustificacion()");
        justificacionList = new ArrayList<>();
        try {
            boolean activo = Constantes.ACTIVO;
            List<Integer> listTipoJustificacion = null;
            justificacionList.addAll(tipoJustificacionService.obtenerActivosPorListId(activo, listTipoJustificacion));
        } catch (Exception ex) {
            LOGGER.error("Error en obtenerJustificacion: {}", ex.getMessage());
        }
    }

    
    public void onRowUnselectSurtimiento() {
        LOGGER.debug("mx.mc.magedbean.DispensacionMB.onRowUnselectSurtimiento()");
        surtimientoInsumoExtendedSelected = null;
        elementoSeleccionado = false;
    }
    
    public void onRowSelectSurtimiento(SelectEvent evento) {
        LOGGER.debug("mx.mc.magedbean.DispensacionMB.onRowSelectSurtimiento()");
        surtimientoInsumoExtendedSelected = (SurtimientoInsumo_Extend) evento.getObject();
        if (surtimientoInsumoExtendedSelected != null) {
            elementoSeleccionado = true;
        }
    }

    /**
     * Muestra el Detalle de cada surtimiento de cada prescripción
     */
    public void verSurtimiento() {
        LOGGER.debug("mx.mc.magedbean.DispensacionMB.verSurtimiento()");

        boolean statuss = Constantes.INACTIVO;
        boolean modal = Constantes.INACTIVO;

        if (!permiso.isPuedeVer()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errTransaccion), null);

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
                Date fechaProgramada = new java.util.Date();
                String idSurtimiento = surtimientoExtendedSelected.getIdSurtimiento();
                String idPrescripcion = surtimientoExtendedSelected.getIdPrescripcion();
                List<Integer> listEstatusSurtimientoInsumo = new ArrayList<>();
                listEstatusSurtimientoInsumo.add(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                List<Integer> listEstatusSurtimiento = new ArrayList<>();
                listEstatusSurtimiento.add(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                String idEstructura = usuarioSelected.getIdEstructura();

                boolean surtimientoMixto = false;

                surtimientoInsumoExtendedList = new ArrayList<>();
                surtimientoInsumoExtendedList
                        .addAll(
                                surtimientoInsumoService
                                        .obtenerSurtimientosProgramados(fechaProgramada, idSurtimiento, idPrescripcion, listEstatusSurtimientoInsumo, listEstatusSurtimiento, idEstructura, surtimientoMixto)
                        );
                obtienelistaCapsulas();
                statuss = Constantes.ACTIVO;
            } catch (Exception ex) {
                String errorMsg = RESOURCES.getString(surIncorrecto);
                LOGGER.error(errorMsg, ex);
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, errorMsg, null);
            }
        }

        PrimeFaces.current().ajax().addCallbackParam(paramModal, modal);
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, statuss);
    }

    /**
     * Muestra el Detalle de cada surtimiento para las prescripciones Surtidas
     */
    public void verSurtimientoDetalleS() {
        LOGGER.debug("mx.mc.magedbean.DispensacionMB.verSurtimientoDetalleS()");

        boolean estatus = Constantes.INACTIVO;
        boolean modal = Constantes.INACTIVO;

        if (!permiso.isPuedeVer()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errTransaccion), null);

        } else if (usuarioSelected == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surSinAlmacen), null);

        } else if (surtimientoExtendedSelectedS == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);

        } else if (surtimientoExtendedSelectedS.getIdPrescripcion() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);

        } else if (surtimientoExtendedSelectedS.getIdSurtimiento() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);

        } else if (surtimientoExtendedSelectedS.getIdEstatusSurtimiento() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);

        } else {
            try {
                Date fechaProgr = new java.util.Date();
                String idSurtimiento = surtimientoExtendedSelectedS.getIdSurtimiento();
                String idPrescripcion = surtimientoExtendedSelectedS.getIdPrescripcion();
                List<Integer> listEstatusSurtimientoInsumo = null;
                List<Integer> listEstatusSurtimiento = null;
                String idEstructura = usuarioSelected.getIdEstructura();

                boolean surtimientoMixto = false;
                surtimientoInsumoExtendedList = new ArrayList<>();
                surtimientoInsumoExtendedList.addAll(
                        surtimientoInsumoService
                                .obtenerSurtimientosProgramados(fechaProgr, idSurtimiento, idPrescripcion, listEstatusSurtimientoInsumo, listEstatusSurtimiento, idEstructura, surtimientoMixto)
                );
                estatus = Constantes.ACTIVO;
            } catch (Exception ext) {
                String errorMsg = RESOURCES.getString(surIncorrecto);
                LOGGER.error(errorMsg, ext);
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, errorMsg, null);
            }
        }
        PrimeFaces.current().ajax().addCallbackParam(paramModal, modal);
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, estatus);
    }

    /**
     * Muestra el Detalle de cada surtimiento para las prescripciones Canceladas
     */
    public void verSurtimientoDetalleC() {
        LOGGER.debug("mx.mc.magedbean.DispensacionMB.verSurtimientoDetalleC()");

        boolean estatus = Constantes.INACTIVO;
        boolean modal = Constantes.INACTIVO;

        if (!permiso.isPuedeVer()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errTransaccion), null);

        } else if (usuarioSelected == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surSinAlmacen), null);

        } else if (surtimientoExtendedSelectedC == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);

        } else if (surtimientoExtendedSelectedC.getIdPrescripcion() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);

        } else if (surtimientoExtendedSelectedC.getIdSurtimiento() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);

        } else if (surtimientoExtendedSelectedC.getIdEstatusSurtimiento() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);

        } else {
            try {
                Date fechaProgramada = new java.util.Date();
                String idSurtimiento = surtimientoExtendedSelectedC.getIdSurtimiento();
                String idPrescripcion = surtimientoExtendedSelectedC.getIdPrescripcion();
                List<Integer> listStatusSurtimientoInsumo = null;
                List<Integer> listEstatusSurtimiento = null;
                String idEstructura = usuarioSelected.getIdEstructura();

                boolean surtimientoMixto = false;
                surtimientoInsumoExtendedList = new ArrayList<>();
                surtimientoInsumoExtendedList.addAll(
                        surtimientoInsumoService
                                .obtenerSurtimientosProgramados(fechaProgramada, idSurtimiento, idPrescripcion, listStatusSurtimientoInsumo, listEstatusSurtimiento, idEstructura, surtimientoMixto)
                );
                estatus = Constantes.ACTIVO;
            } catch (Exception exc) {
                String errorMsg = RESOURCES.getString(surIncorrecto);
                LOGGER.error(errorMsg, exc);
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, errorMsg, null);
            }
        }
        PrimeFaces.current().ajax().addCallbackParam(paramModal, modal);
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, estatus);
    }

    /**
     * Lee el codigo de barras de un medicamento y confirma la cantidad escaneda
     * para enviarse en el surtimento de prescripción
     * @param e
     */
    public void validaLecturaPorCodigo(SelectEvent e) {
        medicamento = (Medicamento_Extended) e.getObject();
        LOGGER.debug("mx.mc.magedbean.DispensacionMB.validaLecturaPorCodigo()");
        status = false;
        authorization = false;
        codigoBarras = CodigoBarras.generaCodigoDeBarras(medicamento.getClaveInstitucional(), medicamento.getLote(), medicamento.getFechaCaducidad(), null);
        try {
            if (codigoBarras == null || codigoBarras.trim().isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.codigoincorrecto"), null);
                codigoBarras = "";
                medicamento = new Medicamento_Extended();
                return;
            } else if (surtimientoExtendedSelected == null || surtimientoInsumoExtendedList == null
                    || surtimientoInsumoExtendedList.isEmpty()) {
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
            eliminaCodigoBarras = false;
            codigoBarras = "";                        
            xcantidad = 1;
        } catch (Exception ex) {
            LOGGER.error("Error en el metodo validaLecturaPorCodigo :: {}", ex.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    /**
     * Agrega un escaneo como subdetalle del resurtimiento
     *
     * @return
     */
    private boolean agregarLotePorCodigo() throws Exception {
        LOGGER.trace("mx.mc.magedbean.DispensacionMB.agregarLotePorCodigo()");
        boolean res = Constantes.ACTIVO;

        //Respaldamos el valor de codigoBarras, por si se requiere Autorizacioon
        codigoBarrasAutorizte = codigoBarras;

        CodigoInsumo ci = CodigoBarras.parsearCodigoDeBarras(codigoBarras);
        if (ci == null) {
            codigoBarras = "";
            medicamento = new Medicamento_Extended();
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.parser"), null);
        } else if (FechaUtil.sumarRestarDiasFecha(new java.util.Date(), noDiasCaducidad).after(ci.getFecha())) {
            codigoBarras = "";
            medicamento = new Medicamento_Extended();
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surCaducidadvencida), null);
        } else {
            List<SurtimientoEnviado_Extend> surtimientoEnviadoExtendList;
            SurtimientoEnviado_Extend surtimientoEnviadoExtend;
            Integer cantidadEscaneada = 0;
            Integer cantEnviadaD = 0;
            surtimientoExtendedSelected.setIdEstructuraAlmacen(usuarioSelected.getIdEstructura());
            for (SurtimientoInsumo_Extend surtimientoInsumo : surtimientoInsumoExtendedList) {
                // regla: solo escanea medicamentos si la clave escaneada existe en el detalle solicitado
                if (surtimientoInsumo.getClaveInstitucional().equals(ci.getClave())) {
                    cantidadEscaneada = (xcantidad == null) ? 1 : xcantidad;

                    // regla: factor multiplicador debe ser 1 o mayor
                    if (cantidadEscaneada < 1) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.cantidadincorrecta"), null);
                        codigoBarras = "";
                        medicamento = new Medicamento_Extended();
                        // regla: solo escanea medicamentos si la clave escaneada existe en el detalle solicitado
                    } else {
                        codigoBarras = "";
                        medicamento = new Medicamento_Extended();
                        // regla: solo escanea medicamentos si no esta bloqueado a nivel catálogo
                        if (!surtimientoInsumo.isMedicamentoActivo()) {
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.clavebloqueada"), null);
                            codigoBarras = "";
                            medicamento = new Medicamento_Extended();
                        } else {
                            cantEnviadaD = (surtimientoInsumo.getCantidadEnviada() == null) ? 0 : surtimientoInsumo.getCantidadEnviada();
                            cantEnviadaD = cantEnviadaD + cantidadEscaneada;

                            // regla: no puede surtir mas medicamento que el solicitado
                            if (cantEnviadaD > surtimientoInsumo.getCantidadSolicitada()) {
                                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.exedido"), null);
                                codigoBarras = "";
                                medicamento = new Medicamento_Extended();
                            } else {
                                Inventario inventariSurtido = null;
                                Integer cantidadXCaja = null;
                                String claveProveedor = null;
                                try {
                                    inventariSurtido = inventarioService.obtenerInventariosPorInsumoEstructuraLoteYCantidadXCajaYFechaCad(
                                            surtimientoInsumo.getIdInsumo(), surtimientoExtendedSelected.getIdEstructuraAlmacen(),
                                            ci.getLote(), cantidadXCaja, claveProveedor, ci.getFecha()
                                    );
                                } catch (Exception ex) {
                                    LOGGER.error(RESOURCES.getString(surLoteIncorrecto), ex);
                                    codigoBarras = "";
                                    medicamento = new Medicamento_Extended();
                                }

                                if (inventariSurtido == null) {
                                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surLoteIncorrecto), null);
                                    codigoBarras = "";
                                    medicamento = new Medicamento_Extended();
                                } else if (inventariSurtido.getActivo() == 0) {
                                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.lotebloqueado"), null);
                                    codigoBarras = "";
                                    medicamento = new Medicamento_Extended();
                                } else if (inventariSurtido.getCantidadActual() < cantidadEscaneada) {
                                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.cantidadinsuficiente"), null);
                                    codigoBarras = "";
                                    medicamento = new Medicamento_Extended();
                                } else {
                                    // regla: cantidadRazonada 
                                    res = Constantes.INACTIVO;
                                   CantidadRazonada cantidadRazonada = cantidadRazonadaService.cantidadRazonadaInsumo(ci.getClave());

                                    // Se verifica que el parametro de cantidad razonada este activo
                                    // 
                                    if (cantidadRazonada != null && !authorization && sesion.isCantidadRazonada()) {
                                        Paciente patient = new Paciente();
                                        patient.setPacienteNumero(surtimientoExtendedSelected.getPacienteNumero());
                                        Paciente paciente = pacienteService.obtener(patient);
                                        int totalDia = 0;
                                        int totalMes = 0;
                                        int diasRestantes = 0;
                                        String ultimoSurtimiento = "";

                                         CantidadRazonadaExtended cantidadRazonadaExt = cantidadRazonadaService.cantidadRazonadaInsumoPaciente(paciente.getIdPaciente(), surtimientoInsumo.getIdInsumo());
                                        if (cantidadRazonadaExt != null) {
                                            totalDia = cantidadRazonadaExt.getTotalSurtDia();
                                            totalMes = cantidadRazonadaExt.getTotalSurtMes();
                                            diasRestantes = cantidadRazonadaExt.getDiasRestantes();
                                            ultimoSurtimiento = cantidadRazonadaExt.getUltimoSurtimiento().toString();
                                        }

                                        //Consulta Interna
                                        if (surtimientoExtendedSelected.getTipoConsulta().equals(TipoConsulta_Enum.CONSULTA_INTERNA.getValue())) {
                                            cantEnviadaD = cantEnviadaD + totalDia;
                                            if (cantEnviadaD > cantidadRazonada.getCantidadDosisUnitaria()) {
                                                msjMdlSurtimiento = false;
                                                msjAlert = "El siguiente Medicamento debe ser Autorizado para surtir la receta<br/> <b>"
                                                        + ci.getClave() + "</b> La cantidad del Medicamento debe ser menor o igual a <b>" + cantidadRazonada.getCantidadDosisUnitaria() + "</b>, se solicita <b>" + cantEnviadaD + "</b>";

                                                xcantidadAutorizte = cantidadEscaneada;
                                                codigoBarras = "";
                                                medicamento = new Medicamento_Extended();
                                                return false;
                                            } else {
                                                authorization = true;
                                            }
                                            //Consulta Externa
                                        } else if (surtimientoExtendedSelected.getTipoConsulta().equals(TipoConsulta_Enum.CONSULTA_EXTERNA.getValue())) {
                                            cantEnviadaD = cantEnviadaD + totalMes;
                                            if (cantEnviadaD > cantidadRazonada.getCantidadPresentacionComercial()) {
                                                msjMdlSurtimiento = false;
                                                msjAlert = "El siguiente Medicamento debe ser Autorizado para surtir la receta<br/> <b>"
                                                        + ci.getClave() + "</b> El Medicamento solo puede surtirse cada  <b>" + cantidadRazonada.getPeriodoPresentacionComercial() + "</b> días, faltan  <b>" + diasRestantes + "</b>, ultimo surtimiento: <b>" + ultimoSurtimiento + "</b>";

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

                                        surtimientoEnviadoExtendList = new ArrayList<>();
                                        if (surtimientoInsumo.getSurtimientoEnviadoExtendList() != null) {
                                            surtimientoEnviadoExtendList.addAll(surtimientoInsumo.getSurtimientoEnviadoExtendList());
                                        }

                                        // regla: si es primer Lote pistoleado solo muestra una linea en subdetalle
                                        if (surtimientoEnviadoExtendList.isEmpty()) {
                                            surtimientoEnviadoExtend = new SurtimientoEnviado_Extend();
                                            surtimientoEnviadoExtend.setIdSurtimientoEnviado(Comunes.getUUID());
                                            surtimientoEnviadoExtend.setIdSurtimientoInsumo(surtimientoInsumo.getIdSurtimientoInsumo());
                                            surtimientoEnviadoExtend.setIdInventarioSurtido(inventariSurtido.getIdInventario());
                                            surtimientoEnviadoExtend.setCantidadEnviado(cantidadEscaneada);

                                            surtimientoEnviadoExtend.setLote(ci.getLote());
                                            surtimientoEnviadoExtend.setCaducidad(ci.getFecha());
                                            surtimientoEnviadoExtend.setIdInsumo(inventariSurtido.getIdInsumo());
                                            surtimientoEnviadoExtendList.add(surtimientoEnviadoExtend);

                                        } else {

                                            boolean agrupaLote = false;
                                            for (SurtimientoEnviado_Extend surtimientoEnviadoRegistado : surtimientoEnviadoExtendList) {
                                                // regla: si se pistolea mas de un medicmento con el mismo lote se agrupan por lotes sumarizando las cantidades
                                                if (surtimientoEnviadoRegistado.getLote().equals(ci.getLote())
                                                        && surtimientoEnviadoRegistado.getCaducidad().equals(ci.getFecha())
                                                        && surtimientoEnviadoRegistado.getIdInsumo().equals(inventariSurtido.getIdInsumo())) {
                                                    Integer cantidadEnviado = surtimientoEnviadoRegistado.getCantidadEnviado() + cantidadEscaneada;
                                                    surtimientoEnviadoRegistado.setCantidadEnviado(cantidadEnviado);
                                                    agrupaLote = true;
                                                    break;
                                                }
                                            }

                                            // regla: si es el único Lote pistoleado agrega una linea nueva en subdetalle
                                            if (!agrupaLote) {
                                                surtimientoEnviadoExtend = new SurtimientoEnviado_Extend();
                                                surtimientoEnviadoExtend.setIdSurtimientoEnviado(Comunes.getUUID());
                                                surtimientoEnviadoExtend.setIdSurtimientoInsumo(surtimientoInsumo.getIdSurtimientoInsumo());
                                                surtimientoEnviadoExtend.setIdInventarioSurtido(inventariSurtido.getIdInventario());
                                                surtimientoEnviadoExtend.setCantidadEnviado(cantidadEscaneada);

                                                surtimientoEnviadoExtend.setLote(ci.getLote());
                                                surtimientoEnviadoExtend.setCaducidad(ci.getFecha());
                                                surtimientoEnviadoExtend.setIdInsumo(inventariSurtido.getIdInsumo());
                                                surtimientoEnviadoExtendList.add(surtimientoEnviadoExtend);
                                            }
                                        }
                                        if (sesion.isCantidadRazonada() && authorizado) {
                                            surtimientoInsumo.setIdUsuarioAutCanRazn(idResponsable);
                                        }
                                        surtimientoInsumo.setFechaEnviada(new java.util.Date());
                                        surtimientoInsumo.setIdUsuarioEnviada(usuarioSelected.getIdUsuario());
                                        surtimientoInsumo.setCantidadEnviada(cantEnviadaD);
                                        surtimientoInsumo.setSurtimientoEnviadoExtendList(surtimientoEnviadoExtendList);
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
            } //for
            if (cantidadEscaneada == 0) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.claveincorrecta"), null);
                codigoBarras = "";
                medicamento = new Medicamento_Extended();
            }
        }
        return res;
    }

    /**
     * elimina un escaneo como subdetalle del resurtimiento
     *
     * @return
     */
    private boolean eliminarLotePorCodigo() {
        LOGGER.trace("mx.mc.magedbean.DispensacionMB.eliminarLotePorCodigo()");
        boolean res = Constantes.INACTIVO;
        boolean encontrado = Constantes.INACTIVO;
        String mensaje = "";
        CodigoInsumo codIns = CodigoBarras.parsearCodigoDeBarras(codigoBarras);
        if (codIns == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.parser"), null);
        } else if (FechaUtil.sumarRestarDiasFecha(new java.util.Date(), noDiasCaducidad).after(codIns.getFecha())) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surCaducidadvencida), null);
        } else {
            List<SurtimientoEnviado_Extend> surtimientoEnviadoExtendList;
            Integer cantidadEscaneada = 0;
            Integer cantidadEnviada = 0;

            for (SurtimientoInsumo_Extend surtimInsumo : surtimientoInsumoExtendedList) {
// regla: puede escanear medicamentos mientras la clave escaneada exista en el detalle solicitado
                if (surtimInsumo.getClaveInstitucional().contains(codIns.getClave())) {
                    encontrado = Constantes.ACTIVO;
                    cantidadEscaneada = (xcantidad == null) ? 1 : xcantidad;
// regla: factor multiplicador debe ser 1 o mayor
                    if (cantidadEscaneada < 1) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.cantidadincorrecta"), null);
                    } else {
                        cantidadEnviada = (surtimInsumo.getCantidadEnviada() == null) ? 0 : surtimInsumo.getCantidadEnviada();
                        cantidadEnviada = cantidadEnviada - cantidadEscaneada;
                        cantidadEnviada = (cantidadEnviada < 0) ? 0 : cantidadEnviada;

                        surtimientoEnviadoExtendList = new ArrayList<>();
                        if (surtimInsumo.getSurtimientoEnviadoExtendList() == null) {
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.lotesinescanear"), null);
                            res = Constantes.ACTIVO;
                        } else {
                            surtimientoEnviadoExtendList.addAll(surtimInsumo.getSurtimientoEnviadoExtendList());

// regla: el lote a eliminar del surtimiento ya debió ser escaneado para eliminaro
                            if (surtimientoEnviadoExtendList.isEmpty()) {
                                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.lotesinescanear"), null);
                                res = Constantes.ACTIVO;
                            } else {
                                Integer cantidadEnviadaPorLote = 0;
                                for (SurtimientoEnviado_Extend surtimientoEnviadoRegistado : surtimientoEnviadoExtendList) {

// regla: si el lote escaneado ya ha sido agregado se descuentan las cantidades
                                    if (surtimientoEnviadoRegistado.getLote().equals(codIns.getLote())
                                            && surtimientoEnviadoRegistado.getCaducidad().equals(codIns.getFecha())) {
                                        cantidadEnviadaPorLote = surtimientoEnviadoRegistado.getCantidadEnviado() - cantidadEscaneada;
                                        if (cantidadEnviadaPorLote < 0) {
                                            mensaje = RESOURCES.getString("sur.error.cantidadMayorLote");
                                            encontrado = Constantes.INACTIVO;
                                            break;
                                        } else {
                                            if (cantidadEnviadaPorLote == 0) {
                                                surtimientoEnviadoExtendList.remove(surtimientoEnviadoRegistado);
                                            }
                                            surtimientoEnviadoRegistado.setCantidadEnviado(cantidadEnviadaPorLote);
                                            surtimInsumo.setCantidadEnviada(cantidadEnviada);
                                            surtimInsumo.setSurtimientoEnviadoExtendList(surtimientoEnviadoExtendList);
                                            if (Objects.equals(surtimInsumo.getCantidadSolicitada(), surtimInsumo.getCantidadEnviada())) {
                                                surtimInsumo.setRequiereJustificante(Constantes.INACTIVO);
                                                surtimInsumo.setIdTipoJustificante(null);
                                            } else {
                                                surtimInsumo.setRequiereJustificante(Constantes.ACTIVO);
                                                surtimInsumo.setIdTipoJustificante(TipoJustificacion_Enum.INSUFICIENTE.getValue());
                                            }
                                            encontrado = Constantes.ACTIVO;
                                            mensaje = "";
                                            break;
                                        }
                                    } else {
                                        encontrado = Constantes.INACTIVO;
                                        mensaje = RESOURCES.getString("sur.error.noExisteInsumoEliminar");
                                    }
                                }
                                res = Constantes.ACTIVO;
                                break;
                            }
                        }
                    }
                } else {
                    mensaje = RESOURCES.getString("sur.error.noExisteInsumoLista");
                    encontrado = Constantes.INACTIVO;
                    res = Constantes.ACTIVO;
                }
            }
        }
        if (!encontrado) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, mensaje, null);
        }
        return res;
    }

    public void validaSurtimiento() {
        LOGGER.trace("mx.mc.magedbean.DispensacionMB.validaSurtimiento()");
        boolean estatus = Constantes.INACTIVO;
        try {            
            if (surtimientoExtendedSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surInvalido), null);

            } else if (surtimientoInsumoExtendedList == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surInvalido), null);

            } else if (surtimientoInsumoExtendedList.isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surInvalido), null);

            } else {                
                Integer numeroMedicamentosSurtidos = 0;
                Surtimiento surtimiento;
                List<SurtimientoInsumo> surtimientoInsumoList = new ArrayList<>();
                List<SurtimientoEnviado> surtimientoEnviadoList = new ArrayList<>();

                Inventario inventarAfectado;
                List<Inventario> inventarioList = new ArrayList<>();

                List<MovimientoInventario> movimientoInventarioList = new ArrayList<>();
                MovimientoInventario movInventarioAfectado;

                for (SurtimientoInsumo_Extend surtimientoInsumo_Ext : surtimientoInsumoExtendedList) {
                    if (surtimientoInsumo_Ext.getSurtimientoEnviadoExtendList() != null && !surtimientoInsumo_Ext.getSurtimientoEnviadoExtendList().isEmpty()) {
                        if (surtimientoInsumo_Ext.getCantidadEnviada().intValue() != surtimientoInsumo_Ext.getCantidadSolicitada().intValue() && surtimientoInsumo_Ext.getIdTipoJustificante() == null) {
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("dispensacion.err.surtmedicamento"), null);
                            return;
                        }
                        Inventario inventarioSurtido;
                        for (SurtimientoEnviado_Extend surtimientoEnviadoExt : surtimientoInsumo_Ext.getSurtimientoEnviadoExtendList()) {
                            if (surtimientoEnviadoExt.getIdInventarioSurtido() != null) {
                                
                                inventarioSurtido = inventarioService.obtener(new Inventario(surtimientoEnviadoExt.getIdInventarioSurtido()));
                                
                                if (inventarioSurtido == null) {
                                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surLoteIncorrecto), null);
                                    return;

                                } else if (inventarioSurtido.getActivo() != 1) {
                                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.lotebloqueado"), null);
                                    return;

                                } else if (FechaUtil.sumarRestarDiasFecha(new java.util.Date(), noDiasCaducidad).after(inventarioSurtido.getFechaCaducidad())) {
                                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surCaducidadvencida), null);
                                    return;

                                } else if (inventarioSurtido.getCantidadActual() < surtimientoEnviadoExt.getCantidadEnviado()) {
                                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.cantidadinsuficiente"), null);
                                    return;

                                } else {

                                    numeroMedicamentosSurtidos++;

                                    inventarAfectado = new Inventario();
                                    inventarAfectado.setIdInventario(surtimientoEnviadoExt.getIdInventarioSurtido());
                                    inventarAfectado.setCantidadActual(surtimientoEnviadoExt.getCantidadEnviado());
                                    inventarioList.add(inventarAfectado);

                                    movInventarioAfectado = new MovimientoInventario();
                                    movInventarioAfectado.setIdMovimientoInventario(Comunes.getUUID());
                                    Integer idTipoMotivo = TipoMotivo_Enum.SAL_PRESC_POR_SURTIMIENTO_DE_PRESCRIPCION.getMotivoValue();
                                    movInventarioAfectado.setIdTipoMotivo(idTipoMotivo);
                                    movInventarioAfectado.setFecha(new java.util.Date());
                                    movInventarioAfectado.setIdUsuarioMovimiento(usuarioSelected.getIdUsuario());
                                    movInventarioAfectado.setIdEstrutcuraOrigen(usuarioSelected.getIdEstructura());
                                    movInventarioAfectado.setIdEstrutcuraDestino(surtimientoExtendedSelected.getIdEstructura());
                                    movInventarioAfectado.setIdInventario(surtimientoEnviadoExt.getIdInventarioSurtido());
                                    movInventarioAfectado.setCantidad(surtimientoEnviadoExt.getCantidadEnviado());
                                    movInventarioAfectado.setFolioDocumento(surtimientoExtendedSelected.getFolio());
                                    movimientoInventarioList.add(movInventarioAfectado);

                                }
                            }

                            surtimientoEnviadoExt.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.SURTIDO.getValue());
                            surtimientoEnviadoExt.setInsertFecha(new java.util.Date());
                            surtimientoEnviadoExt.setInsertIdUsuario(usuarioSelected.getIdUsuario());

                            if (surtimientoEnviadoExt.getCantidadRecibido() == null) {
                                surtimientoEnviadoExt.setCantidadRecibido(0);
                            }
                            surtimientoEnviadoList.add((SurtimientoEnviado) surtimientoEnviadoExt);
                        }
                    }
                    SurtimientoInsumo surtInsumo = new SurtimientoInsumo();
                    surtInsumo.setIdSurtimientoInsumo(surtimientoInsumo_Ext.getIdSurtimientoInsumo());
                    surtInsumo.setIdUsuarioAutCanRazn(surtimientoInsumo_Ext.getIdUsuarioAutCanRazn());
                    surtInsumo.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.SURTIDO.getValue());
                    surtInsumo.setUpdateFecha(new java.util.Date());
                    surtInsumo.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
                    surtInsumo.setFechaEnviada(surtimientoInsumo_Ext.getFechaEnviada());
                    surtInsumo.setIdUsuarioEnviada(usuarioSelected.getIdUsuario());
                    surtInsumo.setCantidadEnviada(surtimientoInsumo_Ext.getCantidadEnviada());
                    surtInsumo.setCantidadVale(0);
                    surtInsumo.setIdTipoJustificante(surtimientoInsumo_Ext.getIdTipoJustificante());
                    surtInsumo.setNotas(surtimientoInsumo_Ext.getNotas());
                    surtimientoInsumoList.add(surtInsumo);
                }

                if (numeroMedicamentosSurtidos == 0) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.error"), null);
                    return;

                } else {
                    surtimientoExtendedSelected.setIdEstructuraAlmacen(usuarioSelected.getIdEstructura());
                    surtimientoExtendedSelected.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.SURTIDO.getValue());
                    surtimientoExtendedSelected.setUpdateFecha(new java.util.Date());
                    surtimientoExtendedSelected.setUpdateIdUsuario(usuarioSelected.getIdUsuario());

                    surtimiento = (Surtimiento) surtimientoExtendedSelected;
                    
                    estatus = surtimientoService.surtirPrescripcion(surtimiento, surtimientoInsumoList, surtimientoEnviadoList, inventarioList, movimientoInventarioList);
                    
                    if (estatus) {
                        Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("sur.exitoso"), "");
                        for (Surtimiento_Extend itemSurttExt : surtimientoExtendedList) {
                            if (itemSurttExt.getFolio().equals(surtimientoExtendedSelected.getFolio())) {
                                surtimientoExtendedList.remove(itemSurttExt);
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
        } catch (Exception ex) {
            LOGGER.error("Error en el metodo surtirInsumos :: {}", ex.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, estatus);
    }

    public void validarFarmacovigilancia() {
        boolean existenAlertas = false;
        try {
            if(funValidarFarmacoVigilancia) {
                ParamBusquedaAlertaDTO  alertaDTO= new ParamBusquedaAlertaDTO();
                alertaDTO.setFolioPrescripcion(surtimientoExtendedSelected.getFolioPrescripcion());
                alertaDTO.setNumeroPaciente(surtimientoExtendedSelected.getPacienteNumero());
                //alertaDTO.setNumeroVisita(surtimientoExtendedSelected.get|);
                alertaDTO.setNumeroMedico(surtimientoExtendedSelected.getCedProfesional());

                List<Diagnostico> listDiagnostico =  diagnosticoService.obtenerPorIdPacienteIdVisitaIdPrescripcion(surtimientoExtendedSelected.getIdPaciente(),
                        surtimientoExtendedSelected.getIdVisita(), surtimientoExtendedSelected.getIdPrescripcion());
                List<String> listaDiagnosticos= new ArrayList<>();
                for(Diagnostico unDiagnostico: listDiagnostico){
                    listaDiagnosticos.add(unDiagnostico.getClave());
                }
                alertaDTO.setListaDiagnosticos(listaDiagnosticos);
                
                List<MedicamentoDTO> listaMedicametosDTO= new ArrayList<>();
                for(SurtimientoInsumo_Extend surtimientoInsumo: surtimientoInsumoExtendedList) {
                    MedicamentoDTO medicamentoDTO = new MedicamentoDTO();
                    medicamentoDTO.setClaveMedicamento(surtimientoInsumo.getClaveInstitucional());
                    medicamentoDTO.setDosis(surtimientoInsumo.getDosis());
                    medicamentoDTO.setDuracion(surtimientoInsumo.getDuracion());
                    medicamentoDTO.setFrecuencia(surtimientoInsumo.getFrecuencia());

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
                            validaSurtimiento();
                        }
                    }
                }
            } else {
                existenAlertas=false;
                validaSurtimiento();
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
        LOGGER.trace("mx.mc.magedbean.dispensacionMB.rechazarValidacionPrescripcion()");    
        boolean status = Constantes.INACTIVO;
        try{
            if(surtimientoExtendedSelected!=null){
                Surtimiento surtimiento = new Surtimiento();
                surtimiento.setIdSurtimiento(surtimientoExtendedSelected.getIdSurtimiento()); 
                surtimiento.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.RECHAZADA.getValue());
                surtimiento.setUpdateFecha(new Date());
                surtimiento.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
                
                List<SurtimientoInsumo> items = new ArrayList<>();
                for(SurtimientoInsumo_Extend insumo : surtimientoInsumoExtendedList){
                    SurtimientoInsumo item = new SurtimientoInsumo();
                    item.setIdSurtimientoInsumo(insumo.getIdSurtimientoInsumo());
                    item.setIdSurtimiento(insumo.getIdSurtimiento());
                    item.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.RECHAZADA.getValue());
                    item.setUpdateFecha(new Date());
                    item.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
                    
                    items.add(item);
                }
                
              status =  surtimientoService.actualizar(surtimiento);
              if(status)
                  status = surtimientoInsumoService.actualizarSurtimientoInsumoList(items);
              
              if(!status)
                  Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al rechazar la prescripción", null);  
            }
        }catch(Exception ex){
            LOGGER.error("ocurrio un error en: rechazarValidacionPrescripcion    ",ex);
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }
        
    /**
     * ******* BBM ********
     */
    public void authorization() {
        boolean estatus = false;
        try {

            Usuario usr = new Usuario();
            usr.setNombreUsuario(userResponsable);
            usr.setActivo(Constantes.ACTIVO);
            usr.setUsuarioBloqueado(Constantes.INACTIVO);
            exist = false;
            msjMdlSurtimiento = false;

            Usuario user = usuarioService.obtener(usr);
            if (user != null) {
                if (CustomWebSecurityConfigurerAdapter.customPasswordEncoder().matches(passResponsable, user.getClaveAcceso())) {
                    List<TransaccionPermisos> permisosAutorizaList = transaccionService.permisosUsuarioTransaccion(user.getIdUsuario(), Transaccion_Enum.DISPENSA_PRESCRIPCION.getSufijo());
                    if (permisosAutorizaList != null) {
                        permisosAutorizaList.stream().forEach(ite -> {
                            if (ite.getAccion().equals("AUTORIZAR")) {
                                exist = true;
                            }
                        });

                        if (exist) {
                            authorization = true;
                            authorizado = true;
                            idResponsable = user.getIdUsuario();
                            codigoBarras = codigoBarrasAutorizte;
                            xcantidad = xcantidadAutorizte;
                            estatus = agregarLotePorCodigo();
                            if (estatus) {
                                if (permiso.isPuedeAutorizar()) {
                                    userResponsable = usuarioSelected.getNombreUsuario();
                                    passResponsable = usuarioSelected.getClaveAcceso();
                                } else {
                                    userResponsable = "";
                                    passResponsable = "";
                                }
                                estatus = Constantes.ACTIVO;
                                codigoBarras = "";
                                idResponsable = "";
                                authorizado = false;
                                xcantidad = 1;
                                eliminaCodigoBarras = false;
                                msjMdlSurtimiento = true;
                            }
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
                }
            } else {
                userResponsable = "";
                passResponsable = "";
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El nombre de usuariono es valido", null);
            }
        } catch (Exception ex) {
            LOGGER.error("Ocurrio una excepcion: ", ex);
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, ex.getMessage(), null);
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, estatus);
    }

    public void cancelAuthorization() {
        authorization = false;
        msjMdlSurtimiento = true;
    }

    public void imprimir(char tipoPresc) throws Exception {
        LOGGER.debug("mx.mc.magedbean.DispensacionMB.imprimir()");
        boolean estatus = Constantes.INACTIVO;
        try {
            RepSurtimientoPresc repSurtimientoPresc = obtenerDatosReporte(tipoPresc);
            EstatusSurtimiento_Enum estatusSurtimiento = null;
            byte[] buffer;
            switch (tipoPresc) {
                case 'P':
                    estatusSurtimiento = EstatusSurtimiento_Enum.getStatusFromId(surtimientoExtendedSelected.getIdEstatusSurtimiento());
                    buffer = reportesService.imprimeSurtimientoPrescInt(repSurtimientoPresc, estatusSurtimiento.name(),
                            surtimientoExtendedSelected.getDetalle() != null ? surtimientoExtendedSelected.getDetalle().size() : 0);
                    break;
                case 'S':
                    estatusSurtimiento = EstatusSurtimiento_Enum.getStatusFromId(surtimientoExtendedSelectedS.getIdEstatusSurtimiento());
                    buffer = reportesService.imprimeSurtimientoPrescInt(repSurtimientoPresc, estatusSurtimiento.name(),
                            surtimientoExtendedSelectedS.getDetalle() != null ? surtimientoExtendedSelectedS.getDetalle().size() : 0);
                    break;
                case 'C':
                    estatusSurtimiento = EstatusSurtimiento_Enum.getStatusFromId(surtimientoExtendedSelectedC.getIdEstatusSurtimiento());
                    buffer = reportesService.imprimeSurtimientoPrescInt(repSurtimientoPresc, estatusSurtimiento.name(),
                            surtimientoExtendedSelectedC.getDetalle() != null ? surtimientoExtendedSelectedC.getDetalle().size() : 0);
                    break;
                default: buffer = null;
            }
            if (buffer != null) {
                status = Constantes.ACTIVO;
                sesion.setReportStream(buffer);
                sesion.setReportName(String.format("ticket%s.pdf", surtimientoExtendedSelected.getFolio()));
            }
        } catch (Exception e) {
            LOGGER.error("Error al generar la Impresión: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, estatus);
    }

    public void imprimirSurtimiento() {
        LOGGER.debug("mx.mc.magedbean.DispensacionMB.imprimirSurtimiento()");
        boolean estatus = Constantes.INACTIVO;
        try {                        
            RepSurtimientoPresc repSurtimientoPresc = obtenerDatosReporte('P');
            EstatusSurtimiento_Enum estatusSurtimiento = EstatusSurtimiento_Enum.getStatusFromId(surtimientoExtendedSelected.getIdEstatusSurtimiento());
            
            byte[] buffer = reportesService.imprimeSurtimientoPrescInt(repSurtimientoPresc, estatusSurtimiento.name(),
                    surtimientoExtendedSelected.getDetalle() != null ? surtimientoExtendedSelected.getDetalle().size() : 0);
            if (buffer != null) {
                status = Constantes.ACTIVO;
                SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "sesionMB");
                sesion.setReportStream(buffer);
                sesion.setReportName(String.format("ticket%s.pdf", surtimientoExtendedSelected.getFolio()));
            }
        } catch (Exception e) {
            LOGGER.error("Error al generar la Impresión: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, estatus);
    }

    private RepSurtimientoPresc obtenerDatosReporte(char tipoPresc) {
        Surtimiento_Extend surtExtSel = null;
        switch (tipoPresc) {
            case 'P':
                surtExtSel = surtimientoExtendedSelected;
                break;
            case 'S':
                surtExtSel = surtimientoExtendedSelectedS;
                break;
            case 'C':
                surtExtSel = surtimientoExtendedSelectedC;
                break;
            default:
                surtExtSel = surtimientoExtendedSelected;
        }
        RepSurtimientoPresc repSurtimientoPresc = new RepSurtimientoPresc();
        repSurtimientoPresc.setUnidadHospitalaria("");
        repSurtimientoPresc.setClasificacionPresupuestal("");
        try {
            Estructura est = estructuraService.obtenerEstructura(surtExtSel.getIdEstructura());
            repSurtimientoPresc.setClasificacionPresupuestal(est.getClavePresupuestal() == null ? "" : est.getClavePresupuestal());
            EntidadHospitalaria entidad = entidadHospitalariaService.obtenerEntidadById(est.getIdEntidadHospitalaria());
            if (entidad != null) {
                repSurtimientoPresc.setUnidadHospitalaria(entidad.getNombre());
            }
            repSurtimientoPresc.setPiso(est.getUbicacion());

            Surtimiento surti = surtimientoService.obtenerPorFolio(surtExtSel.getFolio());
            repSurtimientoPresc.setFechaSolicitado(surti.getFechaProgramada());
            repSurtimientoPresc.setIdEstatusSurtimiento(surti.getIdEstatusSurtimiento());

            SurtimientoInsumo si = new SurtimientoInsumo();
            si.setIdSurtimiento(surti.getIdSurtimiento());
            List<SurtimientoInsumo> lsi = surtimientoInsumoService.obtenerLista(si);
            if (lsi != null && !lsi.isEmpty()) {
                si = lsi.get(0);
                repSurtimientoPresc.setFechaAtendido(si.getFechaEnviada());
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }

        repSurtimientoPresc.setFolioPrescripcion(surtExtSel.getFolioPrescripcion());
        repSurtimientoPresc.setFolioSurtimiento(surtExtSel.getFolio());
        repSurtimientoPresc.setFechaActual(new Date());
        repSurtimientoPresc.setNombrePaciente(surtExtSel.getNombrePaciente());
        repSurtimientoPresc.setClavePaciente(surtExtSel.getClaveDerechohabiencia());
        repSurtimientoPresc.setServicio(surtExtSel.getNombreEstructura());
        repSurtimientoPresc.setCama(surtExtSel.getCama());
        repSurtimientoPresc.setTurno(surtExtSel.getTurno());
        return repSurtimientoPresc;
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
        
    
    /**
     * Obtiene lista de capsulas que existen en la Tabla Capsula y estan activas
     */
    private void obtienelistaCapsulas() {
        try {
            listaCapsulas = capsulaService.obtieneListaCapsulasActivas(Constantes.ACTIVOS, surtimientoExtendedSelected.getIdEstructura());
        } catch (Exception exc) {
            LOGGER.info("Error al buscar la lista de Capsulas: {}", exc.getMessage());
        }

    }

    /**
     * Obtiene el id De la capsula seleccionada
     */
    private void obteneridCapsula() {
        try {
            idCapsula = capsulaService.obteneridCapsula(capsula);
        } catch (Exception exc) {
            LOGGER.info("Error al buscar la lista de Capsulas: {}", exc.getMessage());
        }
    }

    /**
     * Inserta el Id de la Capsula seleccionada en la Tabla surtimiento
     */
    public void vincularPrescripcionCapsula() {
        try {
            Surtimiento surtirCapsula = new Surtimiento();
            EnvioNeumatico neumatiCap = new EnvioNeumatico();
            if (capsula != null) {
                obteneridCapsula();
                surtirCapsula.setIdCapsula(idCapsula.get(0).getIdCapsula());
                surtirCapsula.setIdSurtimiento(surtimientoExtendedSelected.getIdSurtimiento());
                surtimientoService.ligaridCapsulaconSurtimiento(surtirCapsula);
            }
            if (capsula != null) {
                //objeto para insertar datos en tabla EnvioNeumatico
                neumatiCap.setIdenvioNeumatico(getIdenvioNeumatico());
                neumatiCap.setIdCapsula(idCapsula.get(0).getIdCapsula());
                neumatiCap.setNombreCap(idCapsula.get(0).getNombre());
                neumatiCap.setFolioSurtimiento(surtimientoExtendedSelected.getFolio());
                neumatiCap.setUsuario(usuarioSelected.getNombre());
                neumatiCap.setFechaHoraSalida(new java.util.Date());
                neumatiCap.setEstacionSalida(surtimientoExtendedSelected.getNombreEstructura());
                neumatiCap.setFechaHoraLlegada(new java.util.Date());
                neumatiCap.setEstacionLlegada(surtimientoExtendedSelected.getNombreEstructura());
                neumatiCap.setFolioPrescripcion(surtimientoExtendedSelected.getFolioPrescripcion());
                envioNeumaticoService.insertNeumaticTable(neumatiCap);
            }

        } catch (Exception ex) {
            LOGGER.info("Error al insertar id de Capsula: {}", ex.getMessage());
        }
    }
    
    //<editor-fold  defaultstate="collapsed" desc="Getter and Setters...">
    public boolean isHuboError() {
        return huboError;
    }

    public void setHuboError(boolean huboError) {
        this.huboError = huboError;
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

    public List<Medicamento_Extended> getMedicamentoList() {
        return medicamentoList;
    }

    public void setMedicamentoList(List<Medicamento_Extended> medicamentoList) {
        this.medicamentoList = medicamentoList;
    }

    public Medicamento_Extended getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento_Extended medicamento) {
        this.medicamento = medicamento;
    }

    public Surtimiento_Extend getSurtimientoExtendedSelected() {
        return surtimientoExtendedSelected;
    }

    public void setSurtimientoExtendedSelected(Surtimiento_Extend surtimientoExtendedSelected) {
        this.surtimientoExtendedSelected = surtimientoExtendedSelected;
    }

    public Surtimiento_Extend getSurtimientoExtendedSelectedC() {
        return surtimientoExtendedSelectedC;
    }

    public void setSurtimientoExtendedSelectedC(Surtimiento_Extend surtimientoExtendedSelectedC) {
        this.surtimientoExtendedSelectedC = surtimientoExtendedSelectedC;
    }

    public Surtimiento_Extend getSurtimientoExtendedSelectedS() {
        return surtimientoExtendedSelectedS;
    }

    public void setSurtimientoExtendedSelectedS(Surtimiento_Extend surtimientoExtendedSelectedS) {
        this.surtimientoExtendedSelectedS = surtimientoExtendedSelectedS;
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

    public SurtimientoInsumo_Extend getSurtimientoInsumoExtendedSelected() {
        return surtimientoInsumoExtendedSelected;
    }

    public void setSurtimientoInsumoExtendedSelected(SurtimientoInsumo_Extend surtimientoInsumoExtendedSelected) {
        this.surtimientoInsumoExtendedSelected = surtimientoInsumoExtendedSelected;
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

    public void setEliminaCodigoBarras(boolean eliminaCodigoBarras) {
        this.eliminaCodigoBarras = eliminaCodigoBarras;
    }

    public Integer getXcantidad() {
        return xcantidad;
    }

    public void setXcantidad(Integer xcantidad) {
        this.xcantidad = xcantidad;
    }

    public void setJustificacionList(List<TipoJustificacion> justificacionList) {
        this.justificacionList = justificacionList;
    }

    public boolean isEliminaCodigoBarras() {
        return eliminaCodigoBarras;
    }

    public DispensacionLazy getDispensacionLazy() {
        return dispensacionLazy;
    }

    public void setDispensacionLazy(DispensacionLazy dispensacionLazy) {
        this.dispensacionLazy = dispensacionLazy;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ParamBusquedaReporte getParamBusquedaReporte() {
        return paramBusquedaReporte;
    }

    public void setParamBusquedaReporte(ParamBusquedaReporte paramBusquedaReporte) {
        this.paramBusquedaReporte = paramBusquedaReporte;
    }

    public List<Capsula> getListaCapsulas() {
        return listaCapsulas;
    }

    public void setListaCapsulas(List<Capsula> listaCapsulas) {
        this.listaCapsulas = listaCapsulas;
    }

    public String getCapsula() {
        return capsula;
    }

    public void setCapsula(String capsula) {
        this.capsula = capsula;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public String getCodigoCapsula() {
        return codigoCapsula;
    }

    public void setCodigoCapsula(String codigoCapsula) {
        this.codigoCapsula = codigoCapsula;
    }

    public List<Capsula> getIdCapsula() {
        return idCapsula;
    }

    public void setIdCapsula(List<Capsula> idCapsula) {
        this.idCapsula = idCapsula;
    }

    public String getEstructura() {
        return estructura;
    }

    public void setEstructura(String estructura) {
        this.estructura = estructura;
    }

    public String getCodigoCpausla() {
        return codigoCpausla;
    }

    public void setCodigoCpausla(String codigoCpausla) {
        this.codigoCpausla = codigoCpausla;
    }

    public boolean isCapsulaDisponible() {
        return capsulaDisponible;
    }

    public void setCapsulaDisponible(boolean capsulaDisponible) {
        this.capsulaDisponible = capsulaDisponible;
    }

    public int getIdenvioNeumatico() {
        return idenvioNeumatico;
    }

    public void setIdenvioNeumatico(int idenvioNeumatico) {
        this.idenvioNeumatico = idenvioNeumatico;
    }

    public String getUserResponsable() {
        return userResponsable;
    }

    public void setUserResponsable(String userResponsable) {
        this.userResponsable = userResponsable;
    }

    public boolean isAuthorization() {
        return authorization;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPassResponsable() {
        return passResponsable;
    }

    public void setPassResponsable(String passResponsable) {
        this.passResponsable = passResponsable;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public void setAuthorization(boolean authorization) {
        this.authorization = authorization;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getMsjAlert() {
        return msjAlert;
    }

    public void setMsjAlert(String msjAlert) {
        this.msjAlert = msjAlert;
    }

    public String getCadenaBusquedaC() {
        return cadenaBusquedaC;
    }

    public void setCadenaBusquedaC(String cadenaBusquedaC) {
        this.cadenaBusquedaC = cadenaBusquedaC;
    }

    public String getCadenaBusquedaS() {
        return cadenaBusquedaS;
    }

    public void setCadenaBusquedaS(String cadenaBusquedaS) {
        this.cadenaBusquedaS = cadenaBusquedaS;
    }

    public DispensacionLazy getDispensacionLazyS() {
        return dispensacionLazyS;
    }

    public void setDispensacionLazyS(DispensacionLazy dispensacionLazyS) {
        this.dispensacionLazyS = dispensacionLazyS;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

    public DispensacionLazy getDispensacionLazyC() {
        return dispensacionLazyC;
    }

    public void setDispensacionLazyC(DispensacionLazy dispensacionLazyC) {
        this.dispensacionLazyC = dispensacionLazyC;
    }

    public RespuestaAlertasDTO getAlertasDTO() {
        return alertasDTO;
    }

    public void setAlertasDTO(RespuestaAlertasDTO alertasDTO) {
        this.alertasDTO = alertasDTO;
    }
    
}
