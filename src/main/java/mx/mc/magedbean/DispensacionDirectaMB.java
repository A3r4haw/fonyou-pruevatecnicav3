/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.magedbean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import mx.mc.lazy.DispensacionDirectaLazy;
import mx.mc.enums.EstatusGabinete_Enum;
import mx.mc.enums.EstatusPrescripcion_Enum;
import mx.mc.enums.EstatusSurtimiento_Enum;
import mx.mc.enums.MotivoPacienteMovimiento_Enum;
import mx.mc.enums.TipoConsulta_Enum;
import mx.mc.enums.TipoPrescripcion_Enum;
import mx.mc.enums.TipoUsuario_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.ClaveProveedorBarras_Extend;
import mx.mc.model.CodigoInsumo;
import mx.mc.model.DetalleReabastoInsumo;
import mx.mc.model.DispensacionDirecta;
import mx.mc.model.Estructura;
import mx.mc.model.IntipharmPrescriptionDispense;
import mx.mc.model.IntipharmRespuesta;
import mx.mc.model.IntipharmSurtimiento;
import mx.mc.model.Medicamento;
import mx.mc.model.Paciente;
import mx.mc.model.PacienteServicio;
import mx.mc.model.Paciente_Extended;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.Prescripcion;
import mx.mc.model.PrescripcionInsumo;
import mx.mc.model.PrescripcionInsumo_Extended;
import mx.mc.model.Prescripcion_Extended;
import mx.mc.model.Surtimiento;
import mx.mc.model.SurtimientoEnviado_Extend;
import mx.mc.model.SurtimientoInsumo;
import mx.mc.model.SurtimientoInsumo_Extend;
import mx.mc.model.SurtimientoMinistrado_Extend;
import mx.mc.model.Surtimiento_Extend;
import mx.mc.model.Usuario;
import mx.mc.model.Visita;
import mx.mc.service.ClaveProveedorBarrasService;
import mx.mc.service.DispensacionDirectaService;
import mx.mc.service.EstructuraService;
import mx.mc.service.MedicamentoService;
import mx.mc.service.PacienteService;
import mx.mc.service.PacienteServicioService;
import mx.mc.service.PrescripcionService;
import mx.mc.service.SurtimientoInsumoService;
import mx.mc.service.SurtimientoMinistradoService;
import mx.mc.service.SurtimientoService;
import mx.mc.service.UsuarioService;
import mx.mc.service.VisitaService;
import mx.mc.util.CodigoBarras;
import mx.mc.util.Comunes;
import mx.mc.util.FechaUtil;
import mx.mc.util.Mensaje;
import mx.mc.ws.intipharm.service.IntipharmCabinetsWS;
import org.primefaces.PrimeFaces;
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
public class DispensacionDirectaMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(MinistracionPrescripcionMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private boolean estatus;
    private String cadenaBusqueda;
    private String codigoBarras;
    private String tipoPrescripcion;
    private String idEstructura;
    private String idEstructuraAlmacen;
    private Date fechaCaducidad;
    private String filter;
    private int numeroMedDetalles;
    private Paciente pacienteObj;
    private boolean isAdmin;
    private boolean isJefeArea;
    private String folio;
    private SurtimientoMinistrado_Extend surtimientoMinistradoExtendObj;

    private Usuario currentUser;
    private Paciente_Extended pacienteExtended;
    private Usuario medico;
    private List<Prescripcion_Extended> prescripcionExtendedList;
    private List<Estructura> estructuraList;
    private IntipharmSurtimiento surtimiento;
    private List<SurtimientoMinistrado_Extend> listaSurtmientoMinistrado;
    private List<Paciente> listaPacientes;
    private IntipharmPrescriptionDispense medicamento;
    private List<IntipharmPrescriptionDispense> listaInsumos;
    private int numeroRegistros;
    private BigDecimal concentracion;
    private PermisoUsuario permiso;
    private DispensacionDirectaLazy dispensacionDirectaLazy;

    @Autowired
    private transient DispensacionDirectaService dispensacionDirectaService;

    @Autowired
    private transient SurtimientoMinistradoService surtimientoMinistradoService;

    @Autowired
    private transient EstructuraService estructuraService;

    @Autowired
    private transient PacienteService pacienteService;

    @Autowired
    private transient UsuarioService usuarioService;

    @Autowired
    private transient ClaveProveedorBarrasService claveProveedorBarrasService;

    @Autowired
    private transient VisitaService visitaService;

    @Autowired
    private transient PacienteServicioService pacienteServicioService;

    @Autowired
    private transient MedicamentoService medicamentoService;

    @Autowired
    private transient IntipharmCabinetsWS intipharmCabinetsWS;

    @Autowired
    private transient PrescripcionService prescripcionService;

    @Autowired
    private transient SurtimientoInsumoService surtimientoInsumoService;

    @Autowired
    private transient SurtimientoService surtimientoService;

    @PostConstruct
    public void init() {
        initialize();
        permiso = Comunes.obtenerPermisos(Transaccion_Enum.DISPENSDIRECTA.getSufijo());
        validarUsuarioAdministrador();
        obtenerServiciosQueSurte();
        obtenerSutimientoMinistrado();
    }

    private void initialize() {

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        currentUser = sesion.getUsuarioSelected();
        this.isAdmin = false;
        this.isJefeArea = false;
        estructuraList = new ArrayList<>();
        listaSurtmientoMinistrado = new ArrayList<>();
        numeroRegistros = 0;
        this.fechaCaducidad = null;
        this.codigoBarras = "";
        this.pacienteExtended = new Paciente_Extended();
        this.medico = new Usuario();
        this.idEstructuraAlmacen = currentUser.getIdEstructura();
        this.concentracion = null;
    }

    public void validarUsuarioAdministrador() {
        try {
            this.isAdmin = Comunes.isAdministrador();
            this.isJefeArea = Comunes.isJefeArea();
        } catch (Exception e) {
            LOGGER.error("Error en el metodo validarUsuarioAdministrador :: {}", e.getMessage());
        }
    }

    /**
     * Lista los servicios en los que puede dispensar el usuario en sesión
     * dependiendo si estos servicios tienen definido un almacén Periférico
     */
    public void obtenerServiciosQueSurte() {
        LOGGER.trace("mx.mc.magedbean.DispensacionDirectaMB.obtenerServiciosQueSurte()");
        try {
            if (this.isAdmin) {
                List<Integer> listaTipoAreaAlmacen = new ArrayList<>();
                listaTipoAreaAlmacen.add(Constantes.TIPO_AREA_SERVICIO);
                this.estructuraList = this.estructuraService.getEstructuraListTipoAreaEstructura(listaTipoAreaAlmacen);
                if (!estructuraList.isEmpty()) {
                    this.idEstructura = estructuraList.get(0).getIdEstructura();
                }

            } else if (this.isJefeArea) {
// Si es Jefe de área lista su servicios que surte el almacen Periferico y servisios dependientes
                if (currentUser.getIdEstructura() != null && !currentUser.getIdEstructura().isEmpty()) {
                    List<Estructura> serviciosPorSurtir = estructuraService.obtenerEstructurasPorIdAlmacenPeriferico(currentUser.getIdEstructura());
                    List<String> listaIdEstructuraServicios = new ArrayList<>();
                    for (Estructura temp : serviciosPorSurtir) {
                        listaIdEstructuraServicios.add(temp.getIdEstructura());
                        boolean almacen = false;
                        listaIdEstructuraServicios.addAll(estructuraService.obtenerIdsEstructuraJerarquica(temp.getIdEstructura(), almacen));
                    }
                    estructuraList.addAll(estructuraService.obtenerEstructurasActivosPorId(listaIdEstructuraServicios));
                    if (!estructuraList.isEmpty()) {
                        this.idEstructura = estructuraList.get(0).getIdEstructura();
                    }
                }
            } else {
                estructuraList = estructuraService.obtenerEstructurasPorIdAlmacenPeriferico(currentUser.getIdEstructura());
                if (!estructuraList.isEmpty()) {
                    this.idEstructura = estructuraList.get(0).getIdEstructura();
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo alimentarComboAlmacen :: {}", e.getMessage());
        }
    }

    public void crearDispensacion() {
        surtimiento = new IntipharmSurtimiento();
        this.medicamento = new IntipharmPrescriptionDispense();
        this.codigoBarras = "";
        this.listaInsumos = new ArrayList<>();
        this.fechaCaducidad = null;
        this.pacienteExtended = null;
        this.medico = null;
        try {
            // Se obtiene un folio de prescripcion
            String folioo = dispensacionDirectaService.obtenerFolioPrescripcion();
            surtimiento.setFolioSurtimiento(folioo);
            surtimiento.setPrescriptionType(TipoPrescripcion_Enum.NORMAL.getValue());
        } catch (Exception ex) {
            LOGGER.error("Error al obtener folioPrescripción : {}", ex.getMessage());
        }
    }

    public List<Paciente_Extended> autoCompletePacientes(String cadena) {
        List<Paciente_Extended> lista = new ArrayList<>();
        try {
            lista = this.pacienteService.obtenerRegistrosPorCriterioDeBusquedaYEstructura(
                    cadena.trim(), this.idEstructura, Constantes.REGISTROS_PARA_MOSTRAR);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener obtenerListaClavesSku : {}", ex.getMessage());
        }
        return lista;
    }

    public List<Usuario> autoCompleteMedicos(String cadena) {
        List<Usuario> listaMedicos = new ArrayList<>();
        try {
            listaMedicos = this.usuarioService.obtenerMedicosPorCriteriosBusquedaYEstructura(
                    cadena.trim(), TipoUsuario_Enum.MEDICO.getValue(), this.idEstructura,
                    Constantes.REGISTROS_PARA_MOSTRAR, true);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener obtenerListaClavesSku : {}", ex.getMessage());
        }
        return listaMedicos;
    }

    public void buscarFoliorPaciente() {
        if (permiso.isPuedeCrear()) {
            try {
                if (pacienteExtended != null && pacienteExtended.getIdEstructura() != null && pacienteExtended.getNombreCompleto() != null) {
                    pacienteObj = new Paciente(pacienteExtended.getIdEstructura(),
                            ((pacienteExtended.getNombreCompleto() != null ? pacienteExtended.getNombreCompleto() : "") + " "
                            + (pacienteExtended.getApellidoPaterno() != null ? pacienteExtended.getApellidoPaterno() : "") + " "
                            + (pacienteExtended.getApellidoMaterno() != null ? pacienteExtended.getApellidoMaterno() : "")));
                }
                if (prescripcionExtendedList != null && !prescripcionExtendedList.isEmpty()) {
                    prescripcionExtendedList.clear();
                }

                prescripcionExtendedList = prescripcionService.obtenerByFolioPrescripcionForList(folio, pacienteObj, filter);
                if (!prescripcionExtendedList.isEmpty()) {
                    if (filter.equals("Folio")) {
                        asignarMinistrado(prescripcionExtendedList.get(0));
                    }
                    estatus = Constantes.ACTIVO;
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString("devolmedica.warn.presNoEncontr"), null);
                    pacienteExtended = new Paciente_Extended();
                    estatus = Constantes.INACTIVO;
                }

            } catch (Exception ex) {
                LOGGER.trace("Ocurrio una exception al buscar el Folio de la Prescripción: {}", ex.getMessage());
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("devolmedica.err.buscarPrescrip"), null);
                pacienteExtended = new Paciente_Extended();
                estatus = Constantes.INACTIVO;
            }
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, estatus);
    }

    public void recibirInsumosPorCodigo() {
        try {
            if (this.codigoBarras.isEmpty()) {
                return;
            }
            DetalleReabastoInsumo medicamentoDetalle = tratarCodigoDeBarras(this.codigoBarras);
            if (!this.listaInsumos.isEmpty()) {
                boolean existe = validaMedicamento(medicamentoDetalle);
                if (!existe) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("dispensacionDirecta.err.medicaDiferente"), null);
                    this.codigoBarras = "";
                    return;
                }
                for (IntipharmPrescriptionDispense unInsumo : this.listaInsumos) {
                    unInsumo.setCantidadDispensada(unInsumo.getCantidadDispensada() + 1);
                }
            } else {
                listaInsumos = new ArrayList<>();
                this.medicamento.setLote(medicamentoDetalle.getLote());
                String fechaCad = FechaUtil.formatoFecha(medicamentoDetalle.getFecha(), "dd-MM-yyyy");
                this.medicamento.setFechaCaducidad(fechaCad);
                this.fechaCaducidad = medicamentoDetalle.getFecha();
                this.medicamento.setCantidadDispensada(1);
                this.medicamento.setClaveInstitucional(medicamentoDetalle.getIdMedicamento());
                this.medicamento.setNumeroMedicacion(1);
                Medicamento unMedicamento = medicamentoService.obtenerMedicaByClave(medicamentoDetalle.getIdMedicamento());
                if (unMedicamento != null && unMedicamento.getConcentracion() != null) {
                    concentracion = unMedicamento.getConcentracion();
                }
                //Todo Realizar validación para saber si es un servicio o almacen, en caso que sea servicio
                //Buscar quien es el que surte y si no solo buscar la clave de almacén
                String claveEstructura = dispensacionDirectaService.obtenerclaveEstructuraPeriferico(idEstructura);
                if (claveEstructura != null) {
                    this.medicamento.setIdAlmacen(claveEstructura);
                }
                String fechaDispensacion = FechaUtil.formatoFecha(new Date(), "yyyy-MM-dd HH:mm:ss");
                this.medicamento.setFechaDispensacion(fechaDispensacion);
                this.medicamento.setBanderaInventario(true);
                listaInsumos.add(medicamento);
            }

            this.codigoBarras = "";
        } catch (Exception e) {
            this.fechaCaducidad = null;
            listaInsumos = new ArrayList<>();
            LOGGER.error("Error al buscar por codigo de barras :: {}", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("dispensacionDirecta.err.excepcionClave"), null);
        }
    }

    public void obtenerPacienteLazy(SurtimientoMinistrado_Extend dta) {
        folio = dta.getFolio();
        surtimientoMinistradoExtendObj = dta;
    }

    private void llenarPrescripcionSurtimiento(DispensacionDirecta unaDispensacionDirecta, Prescripcion unaPrescripcion,
            PrescripcionInsumo unaPrescripcionInsumo, Surtimiento unSurtimiento, SurtimientoInsumo unSurtimientoInsumo) {

        //se llenan objetos
        String idPrescripcion = Comunes.getUUID();
        unaPrescripcion.setIdPrescripcion(idPrescripcion);
        unaPrescripcion.setIdEstructura(unaDispensacionDirecta.getIdEstructura());

        String idPacienteServicio = validaVisitaPacienteServicio(unaDispensacionDirecta);
        unaPrescripcion.setIdPacienteServicio(idPacienteServicio);

        //Se genera el folio al momento de registrar la prescripcion
        unaPrescripcion.setFechaPrescripcion(new Date());
        unaPrescripcion.setTipoPrescripcion(unaDispensacionDirecta.getTipoPrescripcion());// Ponerlo en una Constante
        unaPrescripcion.setTipoConsulta(unaDispensacionDirecta.getTipoConsulta());
        unaPrescripcion.setIdMedico(unaDispensacionDirecta.getIdMedico());
        unaPrescripcion.setRecurrente(false);
        unaPrescripcion.setIdEstatusPrescripcion(EstatusPrescripcion_Enum.FINALIZADA.getValue());
        unaPrescripcion.setIdEstatusGabinete(EstatusGabinete_Enum.OK.getValue());
        unaPrescripcion.setInsertFecha(new Date());
        unaPrescripcion.setInsertIdUsuario(unaDispensacionDirecta.getIdUsuario());

        String idPrescripcionInsumo = Comunes.getUUID();
        unaPrescripcionInsumo.setIdPrescripcionInsumo(idPrescripcionInsumo);
        unaPrescripcionInsumo.setIdPrescripcion(idPrescripcion);

        //Obtener idInsumo de medicamento       
        String idMedicamento = buscarMedicamentoByClaveInstitucional(unaDispensacionDirecta.getClaveMedicamento());
        unaPrescripcionInsumo.setIdInsumo(idMedicamento);

        unaPrescripcionInsumo.setFechaInicio(new Date());
        unaPrescripcionInsumo.setDosis(unaDispensacionDirecta.getDosis()); // Revisar si se pondra la cantidad total del medicamento
        unaPrescripcionInsumo.setFrecuencia(24); // ponerlo en constante   
        unaPrescripcionInsumo.setDuracion(1); // ponerlo en constante
        unaPrescripcionInsumo.setIdEstatusPrescripcion(EstatusPrescripcion_Enum.FINALIZADA.getValue());
        unaPrescripcionInsumo.setInsertFecha(new Date());
        unaPrescripcionInsumo.setInsertIdUsuario(unaDispensacionDirecta.getIdUsuario());
        unaPrescripcionInsumo.setIdEstatusMirth(EstatusGabinete_Enum.OK.getValue());

        String idSurtimiento = Comunes.getUUID();
        unSurtimiento.setIdSurtimiento(idSurtimiento);
        //Revisar que estructura Almacen
        unSurtimiento.setIdEstructuraAlmacen(unaDispensacionDirecta.getIdEstructuraAlmacen());
        unSurtimiento.setIdPrescripcion(idPrescripcion);
        unSurtimiento.setFechaProgramada(new Date());
        //Se genera el folio de surtimiento al momento de registrar
        unSurtimiento.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.RECIBIDO.getValue());
        unSurtimiento.setInsertFecha(new Date());
        unSurtimiento.setInsertIdUsuario(unaDispensacionDirecta.getIdUsuario());
        unSurtimiento.setIdEstatusMirth(EstatusGabinete_Enum.OK.getValue());
        unSurtimientoInsumo.setIdSurtimientoInsumo(Comunes.getUUID());
        unSurtimientoInsumo.setIdSurtimiento(idSurtimiento);
        unSurtimientoInsumo.setIdPrescripcionInsumo(idPrescripcionInsumo);
        unSurtimientoInsumo.setFechaProgramada(new Date());
        unSurtimientoInsumo.setCantidadSolicitada(unaDispensacionDirecta.getCantidadSolicitada());
        unSurtimientoInsumo.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.RECIBIDO.getValue());
        unSurtimientoInsumo.setInsertFecha(new Date());
        unSurtimientoInsumo.setInsertIdUsuario(unaDispensacionDirecta.getIdUsuario());
        unSurtimientoInsumo.setIdEstatusMirth(EstatusGabinete_Enum.OK.getValue());
        unSurtimientoInsumo.setNumeroMedicacion(1);
    }

    private String buscarMedicamentoByClaveInstitucional(String claveMedicamento) {
        String idMedicamento = null;
        try {
            Medicamento unMedicamento = medicamentoService.obtenerMedicaByClave(claveMedicamento);
            idMedicamento = unMedicamento.getIdMedicamento();
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString("dispensacionDirecta.err.buscarMedicamento"), ex);
            estatus = Constantes.INACTIVO;
        }
        return idMedicamento;
    }

    private String validaVisitaPacienteServicio(DispensacionDirecta unaDispensacionDirecta) {
        String idPacienteServicio = null;
        //Realizar validacion si tiene paciente servicio el paciente o generarlo
        Visita visitaPaciente;
        PacienteServicio pacienteServicio;
        try {
            String idVisita;
            Visita visita = new Visita();
            visita.setIdPaciente(unaDispensacionDirecta.getIdPaciente());
            visitaPaciente = visitaService.obtenerVisitaAbierta(visita);
            if (visitaPaciente == null) {
                // crear la visita
                visitaPaciente = new Visita();
                idVisita = Comunes.getUUID();
                visitaPaciente.setIdVisita(idVisita);
                visitaPaciente.setIdPaciente(unaDispensacionDirecta.getIdPaciente());
                visitaPaciente.setFechaIngreso(new Date());
                visitaPaciente.setIdUsuarioIngresa(unaDispensacionDirecta.getIdMedico());  //Revisar que usuario dara el ingreso
                visitaPaciente.setIdMotivoPacienteMovimiento(MotivoPacienteMovimiento_Enum.ADMISION.getValue());
                visitaPaciente.setMotivoConsulta("Admision de Paciente"); //Motivo de Consulta
                visitaPaciente.setInsertFecha(new Date());
                visitaPaciente.setInsertIdUsuario(unaDispensacionDirecta.getIdUsuario());
                visitaService.insertar(visitaPaciente);
            } else {
                idVisita = visitaPaciente.getIdVisita();
            }
            // Buscar su paciente servicio por idVisita
            pacienteServicio = pacienteServicioService.obtenerPacienteServicioAbierto(new PacienteServicio(idVisita));
            if (pacienteServicio == null) {
                // crear su paciente Servicio
                idPacienteServicio = Comunes.getUUID();
                pacienteServicio = new PacienteServicio();
                pacienteServicio.setIdPacienteServicio(idPacienteServicio);
                pacienteServicio.setIdVisita(idVisita);
                pacienteServicio.setIdEstructura(unaDispensacionDirecta.getIdEstructura());
                pacienteServicio.setFechaAsignacionInicio(new Date());
                pacienteServicio.setIdMotivoPacienteMovimiento(MotivoPacienteMovimiento_Enum.ADMISION.getValue());
                pacienteServicio.setInsertFecha(new Date());
                pacienteServicio.setInsertIdUsuario(unaDispensacionDirecta.getIdUsuario());
                pacienteServicioService.insertar(pacienteServicio);
            } else {
                idPacienteServicio = pacienteServicio.getIdPacienteServicio();
            }

        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString("dispensacionDirecta.err.validarVisPaciServ"), ex);
            estatus = false;
        }
        return idPacienteServicio;
    }

    private DetalleReabastoInsumo tratarCodigoDeBarras(String codigo) {
        DetalleReabastoInsumo detalleReabasto = new DetalleReabastoInsumo();
        try {
            CodigoInsumo ci = CodigoBarras.parsearCodigoDeBarras(codigo);
            if (ci != null) {
                detalleReabasto.setIdMedicamento(ci.getClave());
                detalleReabasto.setLote(ci.getLote());
                detalleReabasto.setFecha(ci.getFecha());
            } else {
                ClaveProveedorBarras_Extend claveProveedorBarras = claveProveedorBarrasService.obtenerClave(codigo);
                if (claveProveedorBarras != null) {
                    detalleReabasto.setIdMedicamento(claveProveedorBarras.getClaveInstitucional());
                    detalleReabasto.setLote(claveProveedorBarras.getClaveProveedor());
                    detalleReabasto.setFecha(Mensaje.generaCaducidadSKU(claveProveedorBarras.getCodigoBarras()));
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("dispensacionDirecta.err.codigoInvalido"), null);
                }
            }

            return detalleReabasto;
        } catch (Exception e) {
            LOGGER.error(RESOURCES.getString("dispensacionDirecta.err.escaneo"), e);
        }
        return detalleReabasto;
    }

    public boolean validaMedicamento(DetalleReabastoInsumo detalle) {
        boolean registro = false;
        for (IntipharmPrescriptionDispense item : this.listaInsumos) {
            if (item.getClaveInstitucional().equalsIgnoreCase(detalle.getIdMedicamento())) {
                return true;
            }
        }
        return registro;
    }

    public void obtenerSutimientoMinistrado() {
        if (permiso.isPuedeVer()) {
            try {
                if (isAdmin || isJefeArea) {
                    dispensacionDirectaLazy = new DispensacionDirectaLazy(surtimientoMinistradoService, idEstructura, null);
                } else {
                    dispensacionDirectaLazy = new DispensacionDirectaLazy(surtimientoMinistradoService, idEstructura, currentUser.getIdUsuario());
                }

                LOGGER.debug("Resultados: {}", dispensacionDirectaLazy.getTotalReg());

                numeroRegistros = listaSurtmientoMinistrado.size();
            } catch (Exception ex) {
                LOGGER.error(RESOURCES.getString("dispensacionDirecta.err.listaSurtMin"), ex);
            }
        } else {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("dispensacionDirecta.err.permTransacc"), null);
        }
    }

    public void asignarMinistrado(Prescripcion_Extended presscripcionByHis) {
        try {

            Prescripcion_Extended PrescripcionExtended = prescripcionService.obtenerPrescripcionByFolio(surtimientoMinistradoExtendObj.getFolio());
            List<String> listValidadorMedicamentos = surtimientoService.surtimientoEnviadoByPrecripcion(presscripcionByHis.getIdPrescripcion());

            if (PrescripcionExtended != null) {
                if (listValidadorMedicamentos.size() < 2) {
                    if (listValidadorMedicamentos.get(0).equals(surtimientoMinistradoExtendObj.getClaveInstitucional())) {
                        PrescripcionInsumo_Extended prescripcionInsumoDispensacionDirecta = prescripcionService.obtenerPrescripcionInsumoByIdPrescripcion(PrescripcionExtended.getIdPrescripcion());
                        Surtimiento_Extend surtimientoDispensacionDirecta = surtimientoService.obtenerByIdPrescripcion(PrescripcionExtended.getIdPrescripcion());
                        SurtimientoInsumo_Extend surtimientoInsumoDispensacionDirecta = surtimientoInsumoService.obtenerSurtimientoInsumoByIdSurtimiento(surtimientoDispensacionDirecta.getIdSurtimiento());
                        SurtimientoEnviado_Extend surtimientoEnviadoDispensacionDirecta = surtimientoService.obtenerSurtimientoEnviadoByIdSurtimientoInsumo(surtimientoInsumoDispensacionDirecta.getIdSurtimientoInsumo());

                        PrescripcionInsumo_Extended prescripcionInsumoHIS = prescripcionService.obtenerPrescripcionInsumoByIdPrescripcion(presscripcionByHis.getIdPrescripcion());
                        Surtimiento_Extend surtimientoHIS = surtimientoService.obtenerByIdPrescripcion(presscripcionByHis.getIdPrescripcion());
                        SurtimientoInsumo_Extend surtimientoInsumoHIS = surtimientoInsumoService.obtenerSurtimientoInsumoByIdSurtimiento(surtimientoHIS.getIdSurtimiento());

                        List<Prescripcion_Extended> prescripcionList = new ArrayList<>();
                        PrescripcionExtended.setIdEstatusPrescripcion(EstatusPrescripcion_Enum.CANCELADA.getValue());
                        PrescripcionExtended.setUpdateFecha(new Date());
                        PrescripcionExtended.setFolio(presscripcionByHis.getFolio());
                        PrescripcionExtended.setComentarios("FOLIO ASIGNADO: " + presscripcionByHis.getFolio());
                        PrescripcionExtended.setUpdateIdUsuario(currentUser.getIdUsuario());
                        prescripcionList.add(PrescripcionExtended);

                        presscripcionByHis.setIdEstatusPrescripcion(EstatusPrescripcion_Enum.FINALIZADA.getValue());
                        presscripcionByHis.setUpdateFecha(new Date());
                        presscripcionByHis.setFolio(surtimientoMinistradoExtendObj.getFolio());
                        presscripcionByHis.setComentarios("FOLIO DP: " + surtimientoMinistradoExtendObj.getFolio() + " COMENT MEDICO: " + presscripcionByHis.getFolio() + "." + presscripcionByHis.getComentarios());
                        presscripcionByHis.setUpdateIdUsuario(currentUser.getIdUsuario());
                        prescripcionList.add(presscripcionByHis);

                        List<PrescripcionInsumo_Extended> prescripcionInsumoList = new ArrayList<>();
                        prescripcionInsumoDispensacionDirecta.setIdEstatusPrescripcion(EstatusPrescripcion_Enum.CANCELADA.getValue());
                        prescripcionInsumoDispensacionDirecta.setUpdateFecha(new Date());
                        prescripcionInsumoDispensacionDirecta.setUpdateIdUsuario(currentUser.getIdUsuario());
                        prescripcionInsumoList.add(prescripcionInsumoDispensacionDirecta);

                        prescripcionInsumoHIS.setIdEstatusPrescripcion(EstatusPrescripcion_Enum.FINALIZADA.getValue());
                        prescripcionInsumoHIS.setUpdateFecha(new Date());
                        prescripcionInsumoHIS.setUpdateIdUsuario(currentUser.getIdUsuario());
                        prescripcionInsumoList.add(prescripcionInsumoHIS);

                        List<Surtimiento_Extend> surtimientoList = new ArrayList<>();

                        surtimientoDispensacionDirecta.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.CANCELADO.getValue());
                        surtimientoDispensacionDirecta.setUpdateFecha(new Date());
                        surtimientoDispensacionDirecta.setUpdateIdUsuario(currentUser.getIdUsuario());
                        surtimientoDispensacionDirecta.setIdEstatusMirth(EstatusGabinete_Enum.OK.getValue());
                        surtimientoList.add(surtimientoDispensacionDirecta);

                        surtimientoHIS.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.COMPLETADO.getValue());
                        surtimientoHIS.setUpdateFecha(new Date());
                        surtimientoHIS.setUpdateIdUsuario(currentUser.getIdUsuario());
                        surtimientoHIS.setIdEstatusMirth(EstatusGabinete_Enum.OK.getValue());
                        surtimientoList.add(surtimientoHIS);

                        List<SurtimientoInsumo_Extend> surtimientoInsumoList = new ArrayList<>();
                        surtimientoInsumoHIS.setUpdateFecha(new Date());
                        surtimientoInsumoHIS.setUpdateIdUsuario(currentUser.getIdUsuario());
                        surtimientoInsumoHIS.setFechaProgramada(surtimientoInsumoDispensacionDirecta.getFechaProgramada());
                        surtimientoInsumoHIS.setFechaEnviada(surtimientoInsumoDispensacionDirecta.getFechaEnviada());
                        surtimientoInsumoHIS.setCantidadEnviada(surtimientoInsumoDispensacionDirecta.getCantidadEnviada());
                        surtimientoInsumoHIS.setFechaRecepcion(surtimientoInsumoDispensacionDirecta.getFechaRecepcion());
                        surtimientoInsumoHIS.setCantidadRecepcion(surtimientoInsumoDispensacionDirecta.getCantidadRecepcion());
                        surtimientoInsumoHIS.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.COMPLETADO.getValue());
                        surtimientoInsumoHIS.setIdEstatusMirth(EstatusGabinete_Enum.OK.getValue());
                        surtimientoInsumoList.add(surtimientoInsumoHIS);

                        surtimientoInsumoDispensacionDirecta.setUpdateFecha(new Date());
                        surtimientoInsumoDispensacionDirecta.setUpdateIdUsuario(currentUser.getIdUsuario());
                        surtimientoInsumoDispensacionDirecta.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.CANCELADO.getValue());
                        surtimientoInsumoDispensacionDirecta.setIdEstatusMirth(EstatusGabinete_Enum.OK.getValue());
                        surtimientoInsumoList.add(surtimientoInsumoDispensacionDirecta);

                        surtimientoEnviadoDispensacionDirecta.setIdSurtimientoInsumo(surtimientoInsumoHIS.getIdSurtimientoInsumo());
                        surtimientoEnviadoDispensacionDirecta.setUpdateFecha(new Date());
                        surtimientoEnviadoDispensacionDirecta.setUpdateIdUsuario(currentUser.getIdUsuario());
                        boolean check = surtimientoService.updateAsignPrescripcion(prescripcionList, prescripcionInsumoList, surtimientoList, surtimientoInsumoList, surtimientoEnviadoDispensacionDirecta);

                        if (!check) {
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("dispensacionDirecta.err.folioSelec"), null);
                            estatus = false;

                        } else {
                            Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("dispensacionDirecta.info.asignExito"), null);
                            estatus = true;
                        }

                    } else {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("dispensacionDirecta.err.medicaIncorr"), null);
                        estatus = false;
                    }
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("dispensacionDirecta.err.prescripIncorr"), null);
                    estatus = false;
                }
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("dispensacionDirecta.err.folioSelec"), null);
                estatus = false;
            }
        } catch (Exception ex) {
            LOGGER.error("Error al asignar prescripción: {}", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("dispensacionDirecta.err.asignPres"), null);
            estatus = false;
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, estatus);
    }

    public void guardarSurtimientoMinistrado() {
        try {
            String valida = validarCamposObligatorios();
            if (!valida.isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, valida, "");
                estatus = Constantes.INACTIVO;
                return;
            }
            //Se crea objeto y se llena con valores de la lista
            DispensacionDirecta unaDispensacionDirecta = new DispensacionDirecta();
            unaDispensacionDirecta.setIdEstructura(this.idEstructura);
            unaDispensacionDirecta.setTipoPrescripcion(TipoPrescripcion_Enum.NORMAL.getValue());
            unaDispensacionDirecta.setTipoConsulta(TipoConsulta_Enum.CONSULTA_INTERNA.getValue());
            unaDispensacionDirecta.setIdMedico(this.medico.getIdUsuario());
            unaDispensacionDirecta.setIdUsuario(currentUser.getIdUsuario());
            unaDispensacionDirecta.setIdPaciente(pacienteExtended.getIdPaciente());
            unaDispensacionDirecta.setClaveMedicamento(this.medicamento.getClaveInstitucional());
            if (concentracion != null) {
                unaDispensacionDirecta.setDosis(concentracion.multiply(new BigDecimal(medicamento.getCantidadDispensada())));
            }
            unaDispensacionDirecta.setIdEstructuraAlmacen(this.idEstructuraAlmacen);
            unaDispensacionDirecta.setCantidadSolicitada(this.medicamento.getCantidadDispensada());

            String folioSurtimiento = registrarPrescripcionDirecta(unaDispensacionDirecta);
            // Validar de insercion anterior 
            if (folioSurtimiento != null) {
                // Se llena Objeto a enviar al servicio web 
                surtimiento.setFolioSurtimiento(folioSurtimiento);
                Paciente unPaciente = pacienteService.obtenerPacienteByIdPaciente(this.pacienteExtended.getIdPaciente());
                if (unPaciente != null) {
                    surtimiento.setNombrePaciente(unPaciente.getNombreCompleto());
                    surtimiento.setNumeroPaciente(unPaciente.getPacienteNumero());
                    surtimiento.setNombreUsuarioDispensacion(this.currentUser.getNombreUsuario());
                    surtimiento.setListPrescriptions(listaInsumos);

                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("dispensacionDirecta.info.pacieNoEncontrado"), null);
                    estatus = Constantes.INACTIVO;
                }
                IntipharmRespuesta respuesta = intipharmCabinetsWS.insertforPrescriptionTracking(surtimiento);
                if (respuesta.isError()) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, respuesta.getMensaje(), null);
                    estatus = Constantes.INACTIVO;
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("dispensacionDirecta.info.exito"), null);
                    estatus = Constantes.ACTIVO;
                    obtenerSutimientoMinistrado();
                }
            }
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString("dispensacionDirecta.err.excRegSurti"), ex);
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, estatus);
    }

    //Metodo creado para que pueda ser utilizado para otra transaccion
    public String registrarPrescripcionDirecta(DispensacionDirecta unaDispensacionDirecta) throws Exception {
        String folioSurtimiento = null;
        //Se genera los objetos de Prescripcion, prescripcionInsumo, surtimiento y surtimientoInsumo
        Prescripcion unaPrescripcion = new Prescripcion();
        PrescripcionInsumo unaPrescripcionInsumo = new PrescripcionInsumo();
        Surtimiento unSurtimiento = new Surtimiento();
        SurtimientoInsumo unSurtimientoInsumo = new SurtimientoInsumo();
        // Llamar a metodo de llenado de prescripcion, prescripcionInsumo, surtimiento, surtimientoInsumo e insertar en tablas correspondientes
        llenarPrescripcionSurtimiento(unaDispensacionDirecta, unaPrescripcion, unaPrescripcionInsumo, unSurtimiento, unSurtimientoInsumo);
        // Generar metodo para realizar la insercion de prescripcion, prescripcionInsumo, surtimiento, surtimientoInsumo
        boolean res = dispensacionDirectaService.registrarPrescripcion(unaPrescripcion, unaPrescripcionInsumo, unSurtimiento, unSurtimientoInsumo);

        if (!res) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("dispensacionDirecta.err.regisPrescr"), null);
            estatus = Constantes.INACTIVO;
        } else {
            folioSurtimiento = unSurtimiento.getFolio();
        }
        return folioSurtimiento;
    }

    public String validarCamposObligatorios() {
        if (!(this.pacienteExtended != null)) {
            return RESOURCES.getString("dispensacionDirecta.err.paciente");
        }
        if (!(this.medico != null)) {
            return RESOURCES.getString("dispensacionDirecta.err.medico");
        }
        if (!this.medicamento.isBanderaInventario() || this.fechaCaducidad == null) {
            return RESOURCES.getString("dispensacionDirecta.err.medicamento");
        }
        return "";
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

    public List<Estructura> getEstructuraList() {
        return estructuraList;
    }

    public void setEstructuraList(List<Estructura> estructuraList) {
        this.estructuraList = estructuraList;
    }

    public int getNumeroRegistros() {
        return numeroRegistros;
    }

    public void setNumeroRegistros(int numeroRegistros) {
        this.numeroRegistros = numeroRegistros;
    }

    public String getCadenaBusqueda() {
        return cadenaBusqueda;
    }

    public void setCadenaBusqueda(String cadenaBusqueda) {
        this.cadenaBusqueda = cadenaBusqueda;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getTipoPrescripcion() {
        return tipoPrescripcion;
    }

    public void setTipoPrescripcion(String tipoPrescripcion) {
        this.tipoPrescripcion = tipoPrescripcion;
    }

    public int getNumeroMedDetalles() {
        return numeroMedDetalles;
    }

    public void setNumeroMedDetalles(int numeroMedDetalles) {
        this.numeroMedDetalles = numeroMedDetalles;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public List<SurtimientoMinistrado_Extend> getListaSurtmientoMinistrado() {
        return listaSurtmientoMinistrado;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public void setListaSurtmientoMinistrado(List<SurtimientoMinistrado_Extend> listaSurtmientoMinistrado) {
        this.listaSurtmientoMinistrado = listaSurtmientoMinistrado;
    }

    public boolean isEstatus() {
        return estatus;
    }

    public void setEstatus(boolean estatus) {
        this.estatus = estatus;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public Paciente_Extended getPacienteExtended() {
        return pacienteExtended;
    }

    public void setPacienteExtended(Paciente_Extended pacienteExtended) {
        this.pacienteExtended = pacienteExtended;
    }

    public Usuario getMedico() {
        return medico;
    }

    public void setMedico(Usuario medico) {
        this.medico = medico;
    }

    public IntipharmSurtimiento getSurtimiento() {
        return surtimiento;
    }

    public void setSurtimiento(IntipharmSurtimiento surtimiento) {
        this.surtimiento = surtimiento;
    }

    public List<Paciente> getListaPacientes() {
        return listaPacientes;
    }

    public void setListaPacientes(List<Paciente> listaPacientes) {
        this.listaPacientes = listaPacientes;
    }

    public IntipharmPrescriptionDispense getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(IntipharmPrescriptionDispense medicamento) {
        this.medicamento = medicamento;
    }

    public List<IntipharmPrescriptionDispense> getListaInsumos() {
        return listaInsumos;
    }

    public void setListaInsumos(List<IntipharmPrescriptionDispense> listaInsumos) {
        this.listaInsumos = listaInsumos;
    }

    public DispensacionDirectaLazy getDispensacionDirectaLazy() {
        return dispensacionDirectaLazy;
    }

    public void setDispensacionDirectaLazy(DispensacionDirectaLazy dispensacionDirectaLazy) {
        this.dispensacionDirectaLazy = dispensacionDirectaLazy;
    }

    public SurtimientoMinistrado_Extend getSurtimientoMinistradoExtendObj() {
        return surtimientoMinistradoExtendObj;
    }

    public void setSurtimientoMinistradoExtendObj(SurtimientoMinistrado_Extend surtimientoMinistradoExtendObj) {
        this.surtimientoMinistradoExtendObj = surtimientoMinistradoExtendObj;
    }

    public List<Prescripcion_Extended> getPrescripcionExtendedList() {
        return prescripcionExtendedList;
    }

    public void setPrescripcionExtendedList(List<Prescripcion_Extended> prescripcionExtendedList) {
        this.prescripcionExtendedList = prescripcionExtendedList;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }
    
}
