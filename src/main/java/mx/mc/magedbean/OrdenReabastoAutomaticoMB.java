package mx.mc.magedbean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import mx.com.dimesa.ws.client.model.ArrayOfTMAT;
import mx.com.dimesa.ws.client.model.TMAT;
import mx.mc.enums.Accion_Enum;
import mx.mc.enums.EstatusReabasto_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.ClaveProveedorBarras;
import mx.mc.model.ClaveProveedorBarras_Extend;
import mx.mc.model.DimesaUsuario;
import mx.mc.model.Estructura;
import mx.mc.model.Medicamento;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.Proveedor;
import mx.mc.model.Reabasto;
import mx.mc.model.ReabastoEnviado;
import mx.mc.model.ReabastoInsumo;
import mx.mc.model.ReabastoRecepcion;
import mx.mc.model.TipoOrigen;
import mx.mc.model.TransaccionPermisos;
import mx.mc.model.Usuario;
import mx.mc.service.ClaveProveedorBarrasService;
import mx.mc.service.EstructuraService;
import mx.mc.service.MedicamentoService;
import mx.mc.service.ProveedorService;
import mx.mc.service.ReabastoAutomaticoService;
import mx.mc.service.ReabastoService;
import mx.mc.service.TipoOrigenService;
import mx.mc.util.Comunes;
import mx.mc.util.FechaUtil;
import mx.mc.util.Mensaje;
import mx.mc.util.UtilPath;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author gcruz
 */
@Controller
@Scope(value = "view")
public class OrdenReabastoAutomaticoMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(OrdenReabastoAutomaticoMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private Usuario currentUser;
    private String idEstructura;
    private boolean administrador;

    private String busquedaFolio;
    private String busquedaNombreProveedor;
    private int numeroRegistros;
    private boolean layout;

    private boolean btnNew;
    private boolean noProcess;
    private boolean message;
    private String pathDefinition;
    private String nombreAlmace;
    //archivo
    private String fileImg;
    private SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss");
    private Date date = new Date();
    private String namefile;

    private String claveIns;
    private String clvProveedor;
    private String codBarras;
    private String lote;
    private Date fechaCaducidad;
    private Integer cantXCaja;
    private BigInteger cantRecibida;
    private Proveedor existeProveedor;
    private String fechaCad;
    private boolean validaValor;
    private boolean hospChiconcuac;
    private boolean cargaLayoutConIngresoPrellenado;
    private boolean recibir;
    private String claveInstitucional;
    private String claveProveedor;
    private String codigoBarras;
    private String loteManual;
    private Date fechaCaducidadManual;
    private Integer cantXCajaManual;
    private BigInteger cantRecibidaManual;
    private String conversion;
    private PermisoUsuario permiso;
    private ClaveProveedorBarras_Extend insumos;
    private List<Estructura> listaEstructuras;
    private List<ClaveProveedorBarras_Extend> listaInsumos;
    private List<ReabastoRecepcion> listReabastoRecepcion;
    private List<ClaveProveedorBarras> listClaveProveedorBarras;
    private List<ClaveProveedorBarras> listClaveProveedorBarrasExiste;
    private Integer idTipoOrigen;
    private List<TipoOrigen> tipoOrigenLista;
    private boolean conexionDimesa;
    private String dimesaURL;
    private String dimesaUsuario;
    private String dimesaClave;
    private String dimesaClaveHospital;
    private String dimesaClaveClinica;
    private boolean reabastoAutomaticoFormulario;
    private String dateFormat;

    @Autowired
    private transient ClaveProveedorBarrasService claveProveedorBarrasService;

    @Autowired
    private transient EstructuraService estructuraService;

    @Autowired
    private transient TipoOrigenService tipoOrigenService;

    @Autowired
    private transient ReabastoAutomaticoService reabastoAutomaticoService;

    @Autowired
    private transient MedicamentoService medicamentoService;

    @Autowired
    private transient ReabastoService reabastoService;

    @Autowired
    private transient ProveedorService proveedorService;
    private Medicamento medicamentoSelect;

    private ParamBusquedaReporte paramBusquedaReporte;

    @PostConstruct
    public void init() {
        dateFormat = "yyyy-MM-dd";
        initialize();
        alimentarComboAlmacen();
        layout = false;
        permiso = Comunes.obtenerPermisos(Transaccion_Enum.ORDENAUTOMATICA.getSufijo());
        if (permiso.isPuedeCrear()) {
            btnNew = Constantes.ACTIVO;
        }
        obtieneTipoOrigen();
    }

    private void initialize() {
        LOGGER.trace("mx.mc.magedbean.OrdenReabastoAutomaticoMB.initialize()");
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        currentUser = Comunes.obtenerUsuarioSesion();
        numeroRegistros = 0;
        this.listaEstructuras = new ArrayList<>();
        listaInsumos = new ArrayList<>();
        listReabastoRecepcion = new ArrayList<>();
        listClaveProveedorBarras = new ArrayList<>();
        listClaveProveedorBarrasExiste = new ArrayList<>();
        noProcess = Constantes.INACTIVO;
        btnNew = Constantes.INACTIVO;
        message = Constantes.ACTIVO;
        validaValor = false;
        recibir = false;
        hospChiconcuac = sesion.isHospChiconcuac();
        cargaLayoutConIngresoPrellenado = sesion.isCargaLayoutConIngresoPrellenado();
        conexionDimesa = sesion.isConexionDimesa();
        dimesaURL = sesion.getDimesaUrl();
        dimesaUsuario = sesion.getDimesaUsuario();
        dimesaClave = sesion.getDimesaClave();
        dimesaClaveHospital = sesion.getDimesaFarmaciaHospital();
        dimesaClaveClinica = sesion.getDimesaFarmaciaClinica();
        reabastoAutomaticoFormulario = sesion.isReabastoAutomaticoManual();

        this.idEstructura = "";
        conversion = "";
        this.administrador = Constantes.INACTIVO;
        medicamentoSelect = new Medicamento();
    }

    private void obtieneTipoOrigen() {
        LOGGER.trace("mx.mc.magedbean.OrdenReabastoAutomaticoMB.obtieneTipoOrigen()");
        tipoOrigenLista = new ArrayList<>();
        try {
            tipoOrigenLista = tipoOrigenService.obtenerLista(new TipoOrigen());
        } catch (Exception ex) {
            LOGGER.error("Error al listar tipos de Origen: {}", ex.getMessage());
        }
    }

    public void alimentarComboAlmacen() {
        LOGGER.trace("mx.mc.magedbean.OrdenReabastoAutomaticoMB.alimentarComboAlmacen()");
        try {
            Estructura est = new Estructura();
            est.setActiva(Constantes.ESTATUS_ACTIVO);
            if (!Comunes.isAdministrador()) {
                est.setIdEstructura(currentUser.getIdEstructura());
                this.listaEstructuras = this.estructuraService.obtenerLista(est);
                listaEstructuras.forEach(action -> {
                    if (action.getIdEstructura().equals(currentUser.getIdEstructura())) {
                        idEstructura = currentUser.getIdEstructura();
                        nombreAlmace = action.getNombre();
                    }
                });
                administrador = Constantes.INACTIVO;
            } else {
                est.setIdTipoAreaEstructura(Constantes.TIPO_AREA_ALMACEN);
                this.listaEstructuras = this.estructuraService.obtenerLista(est);
                administrador = Constantes.ACTIVO;
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo alimentarComboAlmacen :: {}", e.getMessage());
        }
    }

    public void buscarOrdenReabastoAutomatico() {
        LOGGER.trace("mx.mc.magedbean.OrdenReabastoAutomaticoMB.buscarOrdenReabastoAutomatico()");
        this.listaInsumos = new ArrayList<>();
        this.listReabastoRecepcion = new ArrayList<>();
        this.listClaveProveedorBarras = new ArrayList<>();
        this.listClaveProveedorBarrasExiste = new ArrayList<>();
        if (!conexionDimesa) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No hay Servicios WEB Configurados.", null);

        } else {
            mx.com.dimesa.ws.client.service.SAFWSService_Client wsc = new mx.com.dimesa.ws.client.service.SAFWSService_Client();
            DimesaUsuario du = new DimesaUsuario();
            du.setUrlWsdl(dimesaURL);
            du.setNombre(dimesaUsuario);
            du.setContrasenia(dimesaClave);
            du.setFarmacia("");

            if (currentUser != null
                    && currentUser.getIdEstructura() != null) {
                if (currentUser.getIdEstructura().equals(Constantes.IDESTRUCTURA_EXTERNA_HOSPITAL)) {
                    du.setFarmacia(dimesaClaveHospital);
                } else if (currentUser.getIdEstructura().equals(Constantes.IDESTRUCTURA_EXTERNA_CLINICA)) {
                    du.setFarmacia(dimesaClaveClinica);
                }
            }

            try {
                listaInsumos = claveProveedorBarrasService.obtenerByFolio(busquedaFolio);
                if (listaInsumos != null) {
                    recibir = false;
                    this.numeroRegistros = this.listaInsumos.size();
                    this.busquedaFolio = "";
                    return;
                }

                ArrayOfTMAT response = null;
                
                response = wsc.entrega(du, this.busquedaFolio);
                
                if (response != null) {
                    ClaveProveedorBarras_Extend unaClave;
                    ReabastoRecepcion unReabastoRecepcion;
                    for (TMAT unaRespuesta : response.getTMAT()) {
                        unReabastoRecepcion = new ReabastoRecepcion();
                        int pos = unaRespuesta.getCANTIDAD().getValue().indexOf(".");
                        String cantidadTMAT = unaRespuesta.getCANTIDAD().getValue().substring(0, pos);
                        BigInteger cantidadRecibida = new BigInteger(cantidadTMAT);
                        Date fechCaducidad = FechaUtil.formatoFecha(dateFormat, unaRespuesta.getFECHACADUCIDAD().getValue());
                        String lotee = unaRespuesta.getLOTE().getValue();
                        List<String> codigBarrasList = unaRespuesta.getCodigoBarras().getValue().getString();
                        String codigBarras = "";
                        boolean coma = true;
                        if (unaRespuesta.getMATERIAL().getValue() != null) {
                            String codigoMat = unaRespuesta.getMATERIAL().getValue();
                            String codigoSAP = codigoMat.replaceFirst("^0*", "");
                            unaClave = claveProveedorBarrasService.obtenerMedicamentoByClaveSAP(codigoSAP);
                            for (String unCodigoBarras : codigBarrasList) {
                                if (coma) {
                                    codigBarras = codigBarras + unCodigoBarras;
                                    coma = false;
                                } else {
                                    codigBarras = codigBarras + "," + unCodigoBarras;
                                }
                                ClaveProveedorBarras existeProveedorBarras = claveProveedorBarrasService.obtenerExistenciaByProveedorBarras(codigoSAP, unCodigoBarras);
                                if (existeProveedorBarras == null) {
                                    ClaveProveedorBarras noExisteClaveProveedor = new ClaveProveedorBarras();
                                    noExisteClaveProveedor.setClaveProveedor(codigoSAP);
                                    noExisteClaveProveedor.setCodigoBarras(unCodigoBarras);
                                    if (unaClave != null) {
                                        noExisteClaveProveedor.setClaveInstitucional(unaClave.getClaveInstitucional());
                                    }
                                    noExisteClaveProveedor.setInsertFecha(new Date());
                                    noExisteClaveProveedor.setInsertUsuario(currentUser.getIdUsuario());
                                    ArrayList<ClaveProveedorBarras> listaCveProvBarrasCopia = new ArrayList<>(this.listClaveProveedorBarras);
                                    buscarClaveProveedorEnLista(listaCveProvBarrasCopia, noExisteClaveProveedor);
                                } else {
                                    ArrayList<ClaveProveedorBarras> listaCveProvBarrasExisteCopia = new ArrayList<>(this.listClaveProveedorBarrasExiste);
                                    buscarClaveProveedorEnListExistente(listaCveProvBarrasExisteCopia, existeProveedorBarras);
                                }
                            }
                            if (unaClave != null) {
                                unaClave.setCantidadRecibida(cantidadRecibida);
                                unaClave.setLote(lotee);
                                unaClave.setClaveProveedor(codigoSAP);
                                unaClave.setFechaCaducidad(fechCaducidad);
                                unaClave.setIdEstructura(currentUser.getIdEstructura());
                                unaClave.setCodigoBarras(codigBarras);
                                ArrayList<ClaveProveedorBarras_Extend> listaInsumosCopia = new ArrayList<>(this.listaInsumos);
                                if (lotee != null && !lotee.isEmpty()) {
                                    validaMedicamentoDuplicado(unaClave, listaInsumosCopia);
                                }
                            } else {
                                ClaveProveedorBarras_Extend claveNoExiste = new ClaveProveedorBarras_Extend();
                                claveNoExiste.setLote(lotee);
                                claveNoExiste.setClaveProveedor(codigoSAP);
                                claveNoExiste.setFechaCaducidad(fechCaducidad);
                                claveNoExiste.setCodigoBarras(codigBarras);
                                claveNoExiste.setCantidadRecibida(cantidadRecibida);
                                claveNoExiste.setIdEstructura(currentUser.getIdEstructura());
                                ArrayList<ClaveProveedorBarras_Extend> listaInsumosCopia = new ArrayList<>(this.listaInsumos);
                                if (lotee != null && !lotee.isEmpty()) {
                                    validaMedicamentoDuplicado(claveNoExiste, listaInsumosCopia);
                                }
                            }

                            unReabastoRecepcion.setCantidad(cantidadRecibida);
                            unReabastoRecepcion.setCantidadEnviada(cantidadRecibida);
                            unReabastoRecepcion.setClaveProveedor(codigoSAP);
                            unReabastoRecepcion.setCodigoBarras(codigBarras);
                            unReabastoRecepcion.setFechaCaducidad(fechCaducidad);
                            unReabastoRecepcion.setLote(lotee);
                            unReabastoRecepcion.setFolioRecepcion(this.busquedaFolio);
                            unReabastoRecepcion.setIdReabastoRecepcion(Comunes.getUUID());
                            unReabastoRecepcion.setInsertFecha(new Date());
                            unReabastoRecepcion.setIdUsuarioInsert(currentUser.getIdUsuario());
                            this.listReabastoRecepcion.add(unReabastoRecepcion);
                        }
                    }
                    this.numeroRegistros = this.listaInsumos.size();
                }else {
                    LOGGER.error("Error al consultar el WS");
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al consultar el WS: ", null);
                }
            } catch (Exception ex) {
                LOGGER.error("Ocurrio un error al momento de biscar el folio: {}", ex.getMessage());
            }
        }
    }

    private void buscarClaveProveedorEnListExistente(ArrayList<ClaveProveedorBarras> listaCveProvBarrasExisteCopia, ClaveProveedorBarras existeProveedorBarras) {
        boolean cveExiste = true;
        if (!listaCveProvBarrasExisteCopia.isEmpty()) {
            for (ClaveProveedorBarras claveExiste : listaCveProvBarrasExisteCopia) {
                if (claveExiste.getClaveProveedor().equals(existeProveedorBarras.getClaveProveedor())
                        && claveExiste.getCodigoBarras().equals(existeProveedorBarras.getCodigoBarras())) {
                    cveExiste = false;
                    break;
                }
            }
            if (cveExiste) {
                this.listClaveProveedorBarrasExiste.add(existeProveedorBarras);
            }
        } else {
            this.listClaveProveedorBarrasExiste.add(existeProveedorBarras);
        }
    }

    private void buscarClaveProveedorEnLista(ArrayList<ClaveProveedorBarras> listaCveProvBarrasCopia, ClaveProveedorBarras noExisteClaveProveedor) {
        boolean banderaCve = true;
        if (!listaCveProvBarrasCopia.isEmpty()) {
            for (ClaveProveedorBarras unaCveProveedor : listaCveProvBarrasCopia) {
                if (unaCveProveedor.getCodigoBarras().equals(noExisteClaveProveedor.getCodigoBarras())
                        && unaCveProveedor.getClaveInstitucional().equals(noExisteClaveProveedor.getClaveInstitucional())) {
                    banderaCve = false;
                    break;
                }
            }
            if (banderaCve) {
                this.listClaveProveedorBarras.add(noExisteClaveProveedor);
            }
        } else {
            this.listClaveProveedorBarras.add(noExisteClaveProveedor);
        }
    }

    private void validaMedicamentoDuplicado(ClaveProveedorBarras_Extend unaClave, ArrayList<ClaveProveedorBarras_Extend> listaInsumosCopia) {
        boolean bandera = true;
        if (!listaInsumosCopia.isEmpty()) {
            for (ClaveProveedorBarras_Extend unMedicamento : listaInsumosCopia) {
                if (unMedicamento.getClaveInstitucional() != null) {
                    if (unMedicamento.getClaveInstitucional().equals(unaClave.getClaveInstitucional())
                            && unMedicamento.getLote().equals(unaClave.getLote())
                            && unMedicamento.getFechaCaducidad().equals(unaClave.getFechaCaducidad())) {
                        BigInteger cantidad = unMedicamento.getCantidadRecibida().add(unaClave.getCantidadRecibida());
                        unMedicamento.setCantidadRecibida(cantidad);
                        bandera = false;
                        break;
                    }
                } else {
                    bandera = true;
                    break;
                }
            }
            if (bandera) {
                this.listaInsumos.add(unaClave);
            }
        } else {
            this.listaInsumos.add(unaClave);
        }
    }

    public void recibirOrdenReabasto() throws Exception {
        LOGGER.info("Recibir orden Automatica!!!");
        //Se genera objeto de Reabasto
        Reabasto unReabasto = new Reabasto();
        try {
            if (!this.permiso.isPuedeProcesar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.accion"), null);
                return;
            }
            for (ClaveProveedorBarras_Extend unMedicamento : this.listaInsumos) {
                if (unMedicamento.getClaveInstitucional() == null || unMedicamento.getClaveInstitucional().isEmpty()) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Se debe de poner una clave al medicamento con clave SAP " + unMedicamento.getClaveProveedor(), null);
                    return;
                }
                if (unMedicamento.getCantidadRecibida().intValue() > 0
                        && unMedicamento.getCantidadXCaja() <= 0) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Teclear cantidad por caja mayor a cero en medicamento con clave SAP " + unMedicamento.getClaveProveedor(), null);
                    return;
                }
            }
            String idReabasto = Comunes.getUUID();
            unReabasto.setIdReabasto(idReabasto);
            unReabasto.setIdEstructura(this.idEstructura); //todo considerar tomar la estructura de un combo            
            Estructura unaEstructura = estructuraService.getEstructuraPadreIdEstructura(this.idEstructura);
            if (unaEstructura != null) {
                unReabasto.setIdEstructuraPadre(unaEstructura.getIdEstructura());
            }
            unReabasto.setIdTipoOrden(Constantes.TIPO_ORDEN_NORMAL);
            unReabasto.setFolio(this.busquedaFolio);
            unReabasto.setFechaSolicitud(new Date());
            unReabasto.setIdUsuarioSolicitud(currentUser.getIdUsuario());
            unReabasto.setIdEstatusReabasto(EstatusReabasto_Enum.RECIBIDA.getValue());

            unReabasto.setIdTipoOrigen(idTipoOrigen);

            if (layout) {
                if (existeProveedor != null) {
                    unReabasto.setIdProveedor(existeProveedor.getIdProveedor());
                    unaEstructura = estructuraService.obtenerEstructura(this.idEstructura);
                    if (hospChiconcuac) {
                        unReabasto.setIdEstructuraPadre(existeProveedor.getIdProveedor());
                    } else {
                        unReabasto.setIdEstructuraPadre(unaEstructura.getIdEstructuraPadre());
                    }
                }
            } else {
                unReabasto.setIdProveedor(Constantes.IDESTRUCTURA_PROVEEDOR); //Proveedor  DIMESA                                                
            }
            unReabasto.setInsertFecha(new Date());
            unReabasto.setInsertIdUsuario(currentUser.getIdUsuario());
            //Se genera el detalle de Reabasto en lista de ReabastoInsumo, asi como el reabastoEnviado
            List<ReabastoInsumo> listReabastoInsumo = new ArrayList<>();
            ReabastoInsumo unReabastoInsumo = null;
            List<ReabastoEnviado> listReabastoEnviado = new ArrayList<>();
            ReabastoEnviado unReabastoEnviado = null;
            for (ClaveProveedorBarras_Extend claveProveedorBarras : listaInsumos) {
                if (claveProveedorBarras.getIdMedicamento() == null) {
                    Medicamento unMedicamento = new Medicamento();
                    unMedicamento.setClaveInstitucional(claveProveedorBarras.getClaveInstitucional());
                    unMedicamento = medicamentoService.obtenerMedicamento(unMedicamento);
                    if (unMedicamento != null) {
                        claveProveedorBarras.setIdMedicamento(unMedicamento.getIdMedicamento());
                    } else {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, "La Clave " + claveProveedorBarras.getClaveInstitucional() + "  no existe", null);
                        return;
                    }
                }
                for (ClaveProveedorBarras claveProv : this.listClaveProveedorBarras) {
                    if (claveProveedorBarras.getClaveProveedor().equals(claveProv.getClaveProveedor())) {
                        claveProv.setClaveInstitucional(claveProveedorBarras.getClaveInstitucional());
                        claveProv.setCantidadXCaja(claveProveedorBarras.getCantidadXCaja());
                    }
                }
                for (ClaveProveedorBarras unaClaveProveedor : this.listClaveProveedorBarrasExiste) {
                    if (claveProveedorBarras.getClaveProveedor().equals(unaClaveProveedor.getClaveProveedor())) {
                        unaClaveProveedor.setCantidadXCaja(claveProveedorBarras.getCantidadXCaja());
                        unaClaveProveedor.setUpdateFecha(new Date());
                        unaClaveProveedor.setUpdateUsuario(currentUser.getIdUsuario());
                    }
                }
                //Se crea el objeto reabastoInsumo que se agregara a la lista
                String idReabastoInsumo = Comunes.getUUID();
                Integer cantidadCaja = claveProveedorBarras.getCantidadXCaja();
                unReabastoInsumo = new ReabastoInsumo();
                unReabastoInsumo.setIdReabastoInsumo(idReabastoInsumo);
                unReabastoInsumo.setIdReabasto(idReabasto);
                unReabastoInsumo.setIdInsumo(claveProveedorBarras.getIdMedicamento());
                unReabastoInsumo.setCantidadSolicitada(claveProveedorBarras.getCantidadRecibida().intValue() * cantidadCaja);
                unReabastoInsumo.setCantidadComprometida(claveProveedorBarras.getCantidadRecibida().intValue() * cantidadCaja);
                unReabastoInsumo.setCantidadSurtida(claveProveedorBarras.getCantidadRecibida().intValue() * cantidadCaja);
                unReabastoInsumo.setCantidadRecibida(claveProveedorBarras.getCantidadRecibida().intValue() * cantidadCaja);
                unReabastoInsumo.setIdEstatusReabasto(EstatusReabasto_Enum.RECIBIDA.getValue());

                if (cargaLayoutConIngresoPrellenado) {
                    unReabastoInsumo.setCantidadIngresada(unReabastoInsumo.getCantidadRecibida());
                }

                unReabastoInsumo.setInsertFecha(new Date());
                unReabastoInsumo.setInsertIdUsuario(currentUser.getIdUsuario());
                ArrayList<ReabastoInsumo> listaReabastoInsumosCopia = new ArrayList<>(listReabastoInsumo);
                idReabastoInsumo = validaReabastoInsumo(listReabastoInsumo, listaReabastoInsumosCopia, claveProveedorBarras, cantidadCaja, idReabastoInsumo, unReabastoInsumo);
                //Se crea el objeto reabastoEnviado que se insertara a la lista
                unReabastoEnviado = new ReabastoEnviado();
                unReabastoEnviado.setIdReabastoEnviado(Comunes.getUUID());
                unReabastoEnviado.setIdReabastoInsumo(idReabastoInsumo);
                unReabastoEnviado.setCantidadEnviado(claveProveedorBarras.getCantidadRecibida().intValue() * cantidadCaja);
                unReabastoEnviado.setCantidadRecibida(claveProveedorBarras.getCantidadRecibida().intValue() * cantidadCaja);

                if (cargaLayoutConIngresoPrellenado) {
                    unReabastoEnviado.setCantidadIngresada(unReabastoEnviado.getCantidadRecibida());
                }
                unReabastoEnviado.setIdEstatusReabasto(EstatusReabasto_Enum.RECIBIDA.getValue());
                unReabastoEnviado.setIdInsumo(claveProveedorBarras.getIdMedicamento());
                if (hospChiconcuac) {
                    unReabastoEnviado.setIdEstructura(Constantes.ID_ESTRUCTURA_ALMACEN_FARMACIA);//todo se va a poner la estructura del Hospital
                } else {
                    unReabastoEnviado.setIdEstructura(Constantes.ID_ESTRUCTURA_INTRAHOSPITALARIA);  // Se realiza cambio debido a que se debe colocar la farmacia Intrahospitalaria
                }
                unReabastoEnviado.setLoteEnv(claveProveedorBarras.getLote());
                unReabastoEnviado.setFechaCad(claveProveedorBarras.getFechaCaducidad());
                unReabastoEnviado.setCantidadXCaja(claveProveedorBarras.getCantidadXCaja());
                unReabastoEnviado.setClaveProveedor(claveProveedorBarras.getClaveProveedor());
                unReabastoEnviado.setPresentacionComercial(1);

                unReabastoEnviado.setInsertFecha(new Date());
                unReabastoEnviado.setInsertIdUsuario(currentUser.getIdUsuario());
                listReabastoEnviado.add(unReabastoEnviado);
                for (ReabastoRecepcion unaRecepcion : this.listReabastoRecepcion) {
                    if (unaRecepcion.getLote().equals(claveProveedorBarras.getLote())) {
                        if (unaRecepcion.getClaveProveedor() != null && claveProveedorBarras.getClaveProveedor() != null) {
                            if (unaRecepcion.getClaveProveedor().equals(claveProveedorBarras.getClaveProveedor())) {
                                unaRecepcion.setCantidadEnviada(claveProveedorBarras.getCantidadRecibida());
                            }
                        } else {
                            unaRecepcion.setCantidadEnviada(claveProveedorBarras.getCantidadRecibida());
                        }
                    }
                }
            }
            boolean response = reabastoAutomaticoService.recibirReabastoAutomatico(this.listReabastoRecepcion, this.listClaveProveedorBarras,
                    unReabasto, listReabastoInsumo, listReabastoEnviado, this.listClaveProveedorBarrasExiste);
            if (response) {
                Mensaje.showMessage("Info", "Se recibió la orden automática con éxito", null);
                numeroRegistros = 0;
                limpiarPantalla();
                recibir = false;
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No se pudo recibir la orden automatica", null);
            }

        } catch (Exception ex) {
            throw new Exception("Ocurrio un error al momento de recibir el reabasto automatico!!  " + ex.getMessage());
        }
    }

    private String validaReabastoInsumo(List<ReabastoInsumo> listReabastoInsumo, ArrayList<ReabastoInsumo> listaReabastoInsumosCopia, ClaveProveedorBarras_Extend claveProveedorBarras, Integer cantidadCaja, String idReabastoInsumo, ReabastoInsumo unReabastoInsumo) {
        boolean bandera = true;
        if (!listReabastoInsumo.isEmpty()) {
            for (ReabastoInsumo reabastoInsumo : listaReabastoInsumosCopia) {
                if (reabastoInsumo.getIdInsumo().equals(claveProveedorBarras.getIdMedicamento())) {
                    int cantidadCalculada = claveProveedorBarras.getCantidadRecibida().intValue() * cantidadCaja;
                    reabastoInsumo.setCantidadSolicitada(reabastoInsumo.getCantidadSolicitada() + cantidadCalculada);
                    reabastoInsumo.setCantidadComprometida(reabastoInsumo.getCantidadComprometida() + cantidadCalculada);
                    reabastoInsumo.setCantidadSurtida(reabastoInsumo.getCantidadSurtida() + cantidadCalculada);
                    reabastoInsumo.setCantidadRecibida(reabastoInsumo.getCantidadRecibida() + cantidadCalculada);
                    idReabastoInsumo = reabastoInsumo.getIdReabastoInsumo();
                    bandera = false;
                    break;
                }
            }
            if (bandera) {
                listReabastoInsumo.add(unReabastoInsumo);
            }
        } else {
            listReabastoInsumo.add(unReabastoInsumo);
        }
        return idReabastoInsumo;
    }

    public void limpiarPantalla() {
        this.listaInsumos = new ArrayList<>();
        this.busquedaFolio = "";
    }

    public void crear() {
        if (permiso.isPuedeCrear()) {
            listaInsumos = new ArrayList<>();
            noProcess = Constantes.INACTIVO;
        } else {
            Mensaje.showMessage("Warn", "No tiene permisos para Crear.", null);
        }
    }

    public void layoutFileUpload(FileUploadEvent event) {
        try {
            message = Constantes.INACTIVO;
            layout = true;
            this.listReabastoRecepcion = new ArrayList<>();
            UploadedFile upfile = event.getFile();
            String name = upfile.getFileName();
            String ext = name.substring(name.lastIndexOf('.'), name.length());
            String excelFilePath = createFile(upfile.getContents(), name);
            switch (ext) {
                case ".xlsx":
                    readLayout2007(excelFilePath);
                    break;
                default:
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El archivo no tiene el formato correcto", null);
                    break;
            }
            if (listaInsumos.isEmpty()) {
                noProcess = Constantes.ACTIVO;
            }

        } catch (Exception ex) {
            LOGGER.error("ERROR al obtener la imagen: {}", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El archivo no tiene el formato correcto", null);
            noProcess = Constantes.INACTIVO;
        }
    }

    private void setListaReabastoRecepcion() throws Exception {
        ReabastoRecepcion unReabastoRecepcion;
        ClaveProveedorBarras_Extend unaClave;

        unReabastoRecepcion = new ReabastoRecepcion();
        unReabastoRecepcion.setCantidad(this.cantRecibida);
        unReabastoRecepcion.setCantidadEnviada(cantRecibida);
        unReabastoRecepcion.setClaveProveedor(clvProveedor);
        unReabastoRecepcion.setCodigoBarras(codBarras);
        unReabastoRecepcion.setFechaCaducidad(fechaCaducidad);
        unReabastoRecepcion.setLote(lote);
        unReabastoRecepcion.setFolioRecepcion(this.busquedaFolio);
        unReabastoRecepcion.setIdReabastoRecepcion(Comunes.getUUID());
        unReabastoRecepcion.setInsertFecha(new Date());
        unReabastoRecepcion.setIdUsuarioInsert(currentUser.getIdUsuario());
        listReabastoRecepcion.add(unReabastoRecepcion);

        if (clvProveedor != null && !clvProveedor.isEmpty() && codBarras != null && !codBarras.isEmpty()) {
            unaClave = claveProveedorBarrasService.obtenerMedicamentoByClaveSAP(clvProveedor);
            ClaveProveedorBarras existeProveedorBarras = claveProveedorBarrasService.obtenerExistenciaByProveedorBarras(clvProveedor, codBarras);
            if (existeProveedorBarras == null) {
                ClaveProveedorBarras noExisteClaveProveedor = new ClaveProveedorBarras();
                noExisteClaveProveedor.setClaveProveedor(clvProveedor);
                noExisteClaveProveedor.setCodigoBarras(codBarras);
                if (unaClave != null) {
                    noExisteClaveProveedor.setClaveInstitucional(unaClave.getClaveInstitucional());
                }
                noExisteClaveProveedor.setInsertFecha(new Date());
                noExisteClaveProveedor.setInsertUsuario(currentUser.getIdUsuario());
                ArrayList<ClaveProveedorBarras> listaCveProvBarrasCopia = new ArrayList<>(this.listClaveProveedorBarras);
                buscarClaveProveedorEnLista(listaCveProvBarrasCopia, noExisteClaveProveedor);
            } else {
                ArrayList<ClaveProveedorBarras> listaCveProvBarrasExisteCopia = new ArrayList<>(this.listClaveProveedorBarrasExiste);
                buscarClaveProveedorEnListExistente(listaCveProvBarrasExisteCopia, existeProveedorBarras);
            }
        }
    }

    public String generaClave(String claveIns) {

        if (!claveIns.contains(".")) {
            claveIns = claveIns + ".0";
        }
        String[] par = claveIns.split("\\.");
        String part1 = par[0];
        String part2 = par[1];

        if (part2.length() == 1) {
            Integer a = Integer.valueOf(part2);
            if (a != 0) {
                part2 = part2 + "0";
            } else {
                part2 = "00";
            }
        }
        if (part1.length() <= 9) {
            conversion = String.format("%04d", Integer.parseInt(part1));
            claveIns = conversion + "." + part2;
        } else {
            claveIns = part1 + "." + part2;
        }

        return claveIns;

    }

    private void readLayout2007(String excelFilePath) {
        boolean exito = Constantes.ACTIVO;
        boolean folio = false;
        int num = 0;
        try (FileInputStream inputStream = new FileInputStream(new File(excelFilePath)); Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet firstSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = firstSheet.iterator();
            listaInsumos = new ArrayList<>();
            while (iterator.hasNext()) {
                Row nextRow = iterator.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();
                if (num == 0) {
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();

                        switch (cell.getColumnIndex()) {

                            case 1:
                                validaValor = true;
                                busquedaFolio = getValue(cell);
                                validaValor = false;

                                Reabasto existeReabasto = reabastoService.getReabastoByFolio(busquedaFolio);
                                if (existeReabasto != null) {
                                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El reabasto con folio " + this.busquedaFolio + "  ya existe", null);
                                    folio = true;
                                    this.busquedaFolio = "";
                                    return;
                                }
                                break;
                            case 5:
                                busquedaNombreProveedor = getValue(cell);

                                if (!busquedaNombreProveedor.isEmpty()) {
                                    existeProveedor = proveedorService.obtenerProveedorByName(busquedaNombreProveedor);

                                    if (!hospChiconcuac) {
                                        if (existeProveedor == null) {
                                            existeProveedor = new Proveedor();
                                            existeProveedor.setIdProveedor(Comunes.getUUID());
                                            existeProveedor.setNombreProveedor(busquedaNombreProveedor);
                                            existeProveedor.setInsertIdUsuario(currentUser.getIdUsuario());
                                            existeProveedor.setInsertFecha(new java.util.Date());
                                            int resp = proveedorService.insertarProveedor(existeProveedor);
                                            if (resp != 0) {
                                                Mensaje.showMessage("Info", RESOURCES.getString("ordenCompra.ok.proveedor"), null);
                                            } else {
                                                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("ordenCompra.error.proveedor"), null);
                                            }
                                        }
                                    } else {
                                        if (existeProveedor == null) {
                                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Ingrese un Proveedor Válido", null);
                                            return;
                                        }
                                    }
                                } else {
                                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Ingrese el Nombre del Proveedor  ", null);
                                    return;
                                }
                                break;
                            default:
                        }
                    }
                } else if (num > 1 && !folio) {
                    exito = Constantes.ACTIVO;
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();

                        switch (cell.getColumnIndex()) {

                            case 0:

                                claveIns = getValue(cell);

                                if (claveIns.equals("")) {
                                    return;
                                }

                                /*Se valida que solo se genere dicha clave cuando sea Chiconcuac, de lo contrario que siga normal su flujo.
                                 */
                                if (hospChiconcuac) {
                                    claveIns = generaClave(claveIns);
                                }

                                break;

                            case 1:
                                validaValor = true;
                                clvProveedor = getValue(cell);
                                break;

                            case 2:

                                codBarras = getValue(cell);
                                break;

                            case 3:

                                lote = getValue(cell);
                                break;

                            case 4:

                                String cad = getValue(cell);
                                fechaCaducidad = FechaUtil.formatoFecha("yyyy-MM-dd HH:mm:ss", cad);
                                break;

                            case 5:

                                if (hospChiconcuac) {

                                    Medicamento medi = medicamentoService.obtenerMedicaByClave(claveIns);
                                    if (medi == null) {
                                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No existen insumos con la Clave " + claveIns, null);
                                        return;
                                    }
                                    cantXCaja = medi.getFactorTransformacion();

                                } else {
                                    validaValor = true;
                                    cantXCaja = Integer.parseInt(getValue(cell));
                                    validaValor = false;
                                }

                                break;

                            case 6:
                                validaValor = true;
                                cantRecibida = new BigInteger(getValue(cell));
                                validaValor = false;
                                break;
                            default:
                        }
                        if (claveIns.isEmpty() && clvProveedor.isEmpty() && codBarras.isEmpty() && lote.isEmpty() && fechaCaducidad == null && cantXCaja == null && cantRecibida == null) {
                            break;
                        }

                    }

                    if (exito) {
                        insumos = new ClaveProveedorBarras_Extend();
                        insumos.setClaveInstitucional(claveIns);
                        insumos.setClaveProveedor(clvProveedor);
                        insumos.setCodigoBarras(codBarras);
                        insumos.setLote(lote);
                        insumos.setFechaCaducidad(fechaCaducidad);
                        if (cantXCaja == null) {
                            cantXCaja = 1;
                        }
                        insumos.setCantidadXCaja((cantXCaja.equals(0)) ? 1 : cantXCaja);
                        insumos.setCantidadRecibida(cantRecibida);

                        if (!claveIns.equals("")) {
                            listaInsumos.add(insumos);
                            // GCR Se crea metodo ya que se ocupa en otro modulo de la misma manera
                            setListaReabastoRecepcion();
                            this.numeroRegistros = this.listaInsumos.size();
                            recibir = true;
                        }
                    }
                }
                num++;
            }
        } catch (Exception ex) {
            LOGGER.error("ERROR al obtener los Datos: {}", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El archivo no tiene el formato esperado", null);
        }

    }

    public void agregarMedicamentoList() {
        insumos = new ClaveProveedorBarras_Extend();
        claveInstitucional = medicamentoSelect.getClaveInstitucional();
        try {
            if (!this.permiso.isPuedeProcesar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.accion"), null);
                return;
            }
            String valida = validaCamposObligatorios();
            if (!valida.isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, valida, null);
                return;
            }

            boolean noExiste = true;
            if (!this.listaInsumos.isEmpty()) {
                for (int i = 0; i < listaInsumos.size(); i++) {
                    if (listaInsumos.get(i).getClaveInstitucional().equalsIgnoreCase(this.claveIns)
                            && listaInsumos.get(i).getLote().equalsIgnoreCase(this.lote)) {
                        BigInteger cantRecibir = this.cantRecibida.add(listaInsumos.get(i).getCantidadRecibida());
                        listaInsumos.get(i).setCantidadRecibida(cantRecibir);
                        this.listReabastoRecepcion.get(i).setCantidad(cantRecibir);
                        this.listReabastoRecepcion.get(i).setCantidadEnviada(cantRecibir);
                        noExiste = false;
                        break;
                    }
                }

            }
            if (noExiste) {
                this.fechaCaducidad = FechaUtil.formatoFecha(dateFormat, fechaCad);

                insumos.setFechaCaducidad(this.fechaCaducidadManual);
                insumos.setClaveInstitucional(medicamentoSelect.getClaveInstitucional());
                insumos.setClaveProveedor(this.claveProveedor);
                insumos.setCodigoBarras(this.codigoBarras);
                insumos.setLote(this.loteManual);
                insumos.setCantidadXCaja((this.cantXCajaManual.equals(0)) ? 1 : this.cantXCajaManual);
                insumos.setCantidadRecibida(this.cantRecibidaManual);

                this.listaInsumos.add(insumos);
                this.numeroRegistros = this.listaInsumos.size();
                recibir = true;

                ReabastoRecepcion unReabastoRecepcion = new ReabastoRecepcion();

                unReabastoRecepcion.setCantidad(this.cantRecibidaManual);
                unReabastoRecepcion.setCantidadEnviada(this.cantRecibidaManual);
                unReabastoRecepcion.setFechaCaducidad(this.fechaCaducidadManual);
                unReabastoRecepcion.setLote(this.loteManual);
                unReabastoRecepcion.setFolioRecepcion(this.busquedaFolio);
                unReabastoRecepcion.setIdReabastoRecepcion(Comunes.getUUID());
                unReabastoRecepcion.setInsertFecha(new Date());
                unReabastoRecepcion.setIdUsuarioInsert(currentUser.getIdUsuario());
                unReabastoRecepcion.setClaveProveedor(this.claveProveedor);
                unReabastoRecepcion.setCodigoBarras(this.codigoBarras);

                listReabastoRecepcion.add(unReabastoRecepcion);
                if (claveProveedor != null && !claveProveedor.isEmpty() && codigoBarras != null && !codigoBarras.isEmpty()) {
                    ClaveProveedorBarras_Extend unaClave = claveProveedorBarrasService.obtenerMedicamentoByClaveSAP(claveProveedor);
                    ClaveProveedorBarras existeProveedorBarras = claveProveedorBarrasService.obtenerExistenciaByProveedorBarras(claveProveedor, codigoBarras);
                    if (existeProveedorBarras == null) {
                        ClaveProveedorBarras noExisteClaveProveedor = new ClaveProveedorBarras();
                        noExisteClaveProveedor.setClaveProveedor(claveProveedor);
                        noExisteClaveProveedor.setCodigoBarras(codigoBarras);
                        if (unaClave != null) {
                            noExisteClaveProveedor.setClaveInstitucional(unaClave.getClaveInstitucional());
                        }
                        noExisteClaveProveedor.setInsertFecha(new Date());
                        noExisteClaveProveedor.setInsertUsuario(currentUser.getIdUsuario());
                        ArrayList<ClaveProveedorBarras> listaCveProvBarrasCopia = new ArrayList<>(this.listClaveProveedorBarras);
                        buscarClaveProveedorEnLista(listaCveProvBarrasCopia, noExisteClaveProveedor);
                    } else {
                        ArrayList<ClaveProveedorBarras> listaCveProvBarrasExisteCopia = new ArrayList<>(this.listClaveProveedorBarrasExiste);
                        buscarClaveProveedorEnListExistente(listaCveProvBarrasExisteCopia, existeProveedorBarras);
                    }
                }
            }

            limpiarCampos();
        } catch (Exception ex) {
            LOGGER.error("ERROR al agregar elemento a la lista de medicamentos: {}", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Ocurrio un error al momento de agregar el medicamento", null);
        }

    }

    public String validaCamposObligatorios() throws Exception {
        if (claveInstitucional == null || claveInstitucional.isEmpty()) {
            return RESOURCES.getString("orepcio.err.claveInstitucional");
        }
        if (fechaCad == null) {
            return RESOURCES.getString("orepcio.err.caducidadMedicamento");
        } else {
            this.fechaCaducidadManual = FechaUtil.formatoFecha(dateFormat, fechaCad);
        }
        if (cantXCajaManual == null || cantXCajaManual == 0) {
            return RESOURCES.getString("orepcio.err.cantidadXcaja");
        }
        if (cantRecibidaManual == null || cantRecibidaManual.equals(BigInteger.ZERO)) {
            return RESOURCES.getString("orepcio.err.cantidadRecibida");
        }
        if (loteManual == null || loteManual.isEmpty()) {
            return RESOURCES.getString("orepcio.err.lote");
        }
        return "";
    }

    public void limpiarCampos() {
        claveInstitucional = "";
        clvProveedor = "";
        codBarras = "";
        lote = "";
        loteManual = "";
        medicamentoSelect = new Medicamento();
        fechaCaducidad = null;
        fechaCad = "";
        claveProveedor = "";
        codigoBarras = "";
        cantXCajaManual = 0;
        cantRecibidaManual = BigInteger.ZERO;
    }

    private String getValue(Cell cell) {
        switch (cell.getCellType()) {
            case BLANK:
                return "";

            case BOOLEAN:
                return "CELL_TYPE_BOOLEAN";

            case ERROR:
                return "CELL_TYPE_ERROR";

            case FORMULA:
                return cell.getStringCellValue();

            case NUMERIC:

                if (DateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat sdateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    return sdateFormat.format(cell.getDateCellValue());
                } else if (validaValor) {
                    return new java.text.DecimalFormat("0").format(cell.getNumericCellValue());
                } else {
                    return cell.getNumericCellValue() + "";
                }

            case STRING:
                return cell.getStringCellValue();

            default:
                return "valor desconocido";

        }
    }

    private String createFile(byte[] bites, String name) throws IOException, InterruptedException  {
        if (bites != null) {
            String path = UtilPath.getPathDefinida(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/"));
            fileImg = sdf.format(date) + name;
            pathDefinition = path + Constantes.PATH_TMP + fileImg;
            try (FileOutputStream fos = new FileOutputStream(pathDefinition)) {
                fos.write(bites);
                fos.flush();
            }
            Thread.sleep(2000);
            this.setNamefile(Constantes.PATH_IMG + fileImg);
            return pathDefinition;
        } else {
            this.setNamefile("");
        }
        return "";

    }

    /**
     * Metodo para autocompletar medicamento prueba
     *
     * @param query
     * @return
     */
    public List<Medicamento> autocompleteInsumo(String query) {

        List<Medicamento> insumosList = new ArrayList<>();
        try {
            insumosList.addAll(medicamentoService.obtenerInsumos(query));

        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al consultar el medicamento");
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("medicamento.err.autocomplete"), null);
        }
        return insumosList;
    }

    public String getBusquedaFolio() {
        return busquedaFolio;
    }

    public void setBusquedaFolio(String busquedaFolio) {
        this.busquedaFolio = busquedaFolio;
    }

    public int getNumeroRegistros() {
        return numeroRegistros;
    }

    public void setNumeroRegistros(int numeroRegistros) {
        this.numeroRegistros = numeroRegistros;
    }

    public List<ClaveProveedorBarras_Extend> getListaInsumos() {
        return listaInsumos;
    }

    public void setListaInsumos(List<ClaveProveedorBarras_Extend> listaInsumos) {
        this.listaInsumos = listaInsumos;
    }

    public boolean isBtnNew() {
        return btnNew;
    }

    public void setBtnNew(boolean btnNew) {
        this.btnNew = btnNew;
    }

    public boolean isNoProcess() {
        return noProcess;
    }

    public void setNoProcess(boolean noProcess) {
        this.noProcess = noProcess;
    }

    public boolean isMessage() {
        return message;
    }

    public void setMessage(boolean message) {
        this.message = message;
    }

    public String getPathDefinition() {
        return pathDefinition;
    }

    public void setPathDefinition(String pathDefinition) {
        this.pathDefinition = pathDefinition;
    }

    public String getClaveIns() {
        return claveIns;
    }

    public void setClaveIns(String claveIns) {
        this.claveIns = claveIns;
    }

    public String getClvProveedor() {
        return clvProveedor;
    }

    public void setClvProveedor(String clvProveedor) {
        this.clvProveedor = clvProveedor;
    }

    public String getCodBarras() {
        return codBarras;
    }

    public void setCodBarras(String codBarras) {
        this.codBarras = codBarras;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public Integer getCantXCaja() {
        return cantXCaja;
    }

    public void setCantXCaja(Integer cantXCaja) {
        this.cantXCaja = cantXCaja;
    }

    public BigInteger getCantRecibida() {
        return cantRecibida;
    }

    public void setCantRecibida(BigInteger cantRecibida) {
        this.cantRecibida = cantRecibida;
    }

    public String getFileImg() {
        return fileImg;
    }

    public void setFileImg(String fileImg) {
        this.fileImg = fileImg;
    }

    public SimpleDateFormat getSdf() {
        return sdf;
    }

    public void setSdf(SimpleDateFormat sdf) {
        this.sdf = sdf;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNamefile() {
        return namefile;
    }

    public void setNamefile(String namefile) {
        this.namefile = namefile;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }

    public List<Estructura> getListaEstructuras() {
        return listaEstructuras;
    }

    public void setListaEstructuras(List<Estructura> listaEstructuras) {
        this.listaEstructuras = listaEstructuras;
    }

    public String getNombreAlmace() {
        return nombreAlmace;
    }

    public void setNombreAlmace(String nombreAlmace) {
        this.nombreAlmace = nombreAlmace;
    }

    public String getBusquedaNombreProveedor() {
        return busquedaNombreProveedor;
    }

    public void setBusquedaNombreProveedor(String busquedaNombreProveedor) {
        this.busquedaNombreProveedor = busquedaNombreProveedor;
    }

    public boolean isReabastoAutomaticoFormulario() {
        return reabastoAutomaticoFormulario;
    }

    public void setReabastoAutomaticoFormulario(boolean reabastoAutomaticoFormulario) {
        this.reabastoAutomaticoFormulario = reabastoAutomaticoFormulario;
    }

    public String getFechaCad() {
        return fechaCad;
    }

    public void setFechaCad(String fechaCad) {
        this.fechaCad = fechaCad;
    }

    public boolean isRecibir() {
        return recibir;
    }

    public void setRecibir(boolean recibir) {
        this.recibir = recibir;
    }

    public String getClaveInstitucional() {
        return claveInstitucional;
    }

    public void setClaveInstitucional(String claveInstitucional) {
        this.claveInstitucional = claveInstitucional;
    }

    public String getClaveProveedor() {
        return claveProveedor;
    }

    public void setClaveProveedor(String claveProveedor) {
        this.claveProveedor = claveProveedor;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getLoteManual() {
        return loteManual;
    }

    public void setLoteManual(String loteManual) {
        this.loteManual = loteManual;
    }

    public Date getFechaCaducidadManual() {
        return fechaCaducidadManual;
    }

    public void setFechaCaducidadManual(Date fechaCaducidadManual) {
        this.fechaCaducidadManual = fechaCaducidadManual;
    }

    public Integer getCantXCajaManual() {
        return cantXCajaManual;
    }

    public void setCantXCajaManual(Integer cantXCajaManual) {
        this.cantXCajaManual = cantXCajaManual;
    }

    public BigInteger getCantRecibidaManual() {
        return cantRecibidaManual;
    }

    public void setCantRecibidaManual(BigInteger cantRecibidaManual) {
        this.cantRecibidaManual = cantRecibidaManual;
    }

    public ParamBusquedaReporte getParamBusquedaReporte() {
        return paramBusquedaReporte;
    }

    public void setParamBusquedaReporte(ParamBusquedaReporte paramBusquedaReporte) {
        this.paramBusquedaReporte = paramBusquedaReporte;
    }

    public void handleSelect(SelectEvent e) {
        medicamentoSelect = (Medicamento) e.getObject();
    }

    public void handleUnSelect(UnselectEvent e) {
        medicamentoSelect = (Medicamento) e.getObject();
    }

    public Medicamento getMedicamentoSelect() {
        return medicamentoSelect;
    }

    public void setMedicamentoSelect(Medicamento medicamentoSelect) {
        this.medicamentoSelect = medicamentoSelect;
    }

    public boolean isPermiteAjusteInventarioGlobal() {
        return hospChiconcuac;
    }

    public Integer getIdTipoOrigen() {
        return idTipoOrigen;
    }

    public void setIdTipoOrigen(Integer idTipoOrigen) {
        this.idTipoOrigen = idTipoOrigen;
    }

    public List<TipoOrigen> getTipoOrigenLista() {
        return tipoOrigenLista;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }
}
