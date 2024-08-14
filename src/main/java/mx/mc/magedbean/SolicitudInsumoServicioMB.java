/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.magedbean;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Response;
import mx.gob.issste.ws.ColectivoSiam;
import mx.gob.issste.ws.client.ColectivoSIAMClient;
import mx.gob.issste.ws.model.ColectivoSurtidoDetalle;
import mx.gob.issste.ws.model.InsumoCTSiam;
import mx.gob.issste.ws.model.InsumoSIAM;
import mx.gob.issste.ws.model.SendColectivoSIAM;
import mx.gob.issste.ws.model.ResponseColectivoSIAM;
import mx.gob.issste.ws.model.ResponseDetalleSIAM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import mx.mc.init.Constantes;
import mx.mc.enums.EstatusReabasto_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.model.AlmacenControl;
import mx.mc.model.Inventario;
import mx.mc.model.CatalogoGeneral;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.Estructura;
import mx.mc.model.Medicamento;
import mx.mc.model.Reabasto;
import mx.mc.model.Proveedor;
import mx.mc.model.ReabastoInsumo;
import mx.mc.model.TransaccionPermisos;
import mx.mc.model.PresentacionMedicamento;
import mx.mc.model.Usuario;
import mx.mc.service.CatalogoGeneralService;
import mx.mc.service.EntidadHospitalariaService;
import mx.mc.service.EstructuraService;
import mx.mc.service.MedicamentoService;
import mx.mc.service.ReabastoInsumoService;
import mx.mc.service.ProveedorService;
import mx.mc.service.PresentacionMedicamentoService;
import mx.mc.util.Comunes;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import mx.mc.service.ReabastoService;
import mx.mc.service.InventarioService;
import mx.mc.service.ReportesService;
import org.primefaces.model.StreamedContent;
import mx.mc.lazy.SolicitudReabastoLazy;
import mx.mc.enums.TipoAlmacen_Enum;
import mx.mc.enums.TipoAreaEstructura_Enum;
import mx.mc.enums.TipoOrigen_Enum;
import mx.mc.init.CustomWebSecurityConfigurerAdapter;
import mx.mc.model.DetalleInsumoSiam;
import mx.mc.model.FolioAlternativoFolioMus;
import mx.mc.model.DiaFestivo;
import mx.mc.model.EstructuraAlmacenServicio;
import mx.mc.model.FolioAlternativoFolioMusExtended;
import mx.mc.model.InsumoServicio;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.ReabastoXServicio;
import mx.mc.service.AlmacenControlService;
import mx.mc.service.DetalleInsumoSiamService;
import mx.mc.service.FolioAlternativoFolioMusService;
import mx.mc.service.TransaccionService;
import mx.mc.service.UsuarioService;
import mx.mc.util.Mensaje;
import org.primefaces.PrimeFaces;
import mx.mc.service.DiaFestivoService;
import mx.mc.service.EstructuraAlmacenServicioService;
import org.apache.commons.math3.genetics.RandomKey;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;

/**
 *
 * @author bbautista
 */
@Controller
@Scope(value = "view")
public class SolicitudInsumoServicioMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(SolicitudInsumoServicioMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private PermisoUsuario permiso;
    private boolean btnNew;
    private boolean activo;
    private boolean isAdmin;
    private boolean status;
    private boolean edit;
    private boolean manual;
    private boolean printSolicitud;
    private boolean contEdit;
    private boolean farmacia;
    private boolean orgdes;
    private boolean separaInsumos;
    private boolean exits;
    private int idAlmacen;
    private int numeroRegistros;
    private String tipoAlmacen;
    private String cadenaBusqueda;
    private int tipoInsumo;
    private String idEstructura;
    private Date fechaActual;
    private static StreamedContent streamedContent;
    private File pdf;
    private String pathPdf;

    private String origen;
    private String destino;
    private int tipoOrden;
    private int cantSolicitada;
    private int activeRow;
    private boolean messaje;
    private boolean dotacionDiaria;
    private boolean dotacionMC;
    private Pattern regexNumber;
    private boolean surtSabado;
    private boolean surtDomingo;
    private String msjAlert;
    private String userResponsable;
    private String passResponsable;
    private String idResponsable;
    private String idInsumoXAutorizar;
    private String nombreCompleto;
    private boolean exist;
    private boolean exist2;
    private boolean msjMdlSurtimiento;
    private int oldValue;
    private int position;
    private boolean pointCtrl;
    /*Ya no se ocuparan por cambio de separación de grupo, ya no sera por el valor de SubcategoriaMedicamento*/
    private String idGrupoEspecial;
    private String idGrupoUno;
    private String idGrupoDos;
    private String idGrupoTres;
    private String idGrupoCuatro;
    private String idGrupoCinco;
    private boolean campoCantidadXLote;
    private boolean campoCantidadXClave;
    private boolean mostrarFolioAlternativo;
    private boolean nuevaOrden;
    private String solicRebastoInfoRequiereAutorizacion;
    private String solicRebastoInfoCantidadMenor;
    private boolean urlSIAM;
    private String urlGeneraColectivoSIAM;
    private String urlConsultaColectivoSIAM;
    private String urlConsultaInsumoEnCT;
    private Usuario currentUser;
    private SendColectivoSIAM colectivoSIAM;
    private ColectivoSurtidoDetalle consultaColectivoSIAM;
    private boolean colectivoXServicio;
    private boolean destinoCole;
    private boolean showExpansion;
    private List<FolioAlternativoFolioMusExtended> colectivosSIAM;
    private List<InsumoServicio> insumos;
    private String idServiceAdd;
    private EstatusReabasto_Enum estatuReabastoMus;
    private List<InsumoCTSiam> insumoSiamList;
    private ColectivoSIAMClient clientSiam;
    private boolean campSiam;
    private int suma;
    

    @Autowired
    private transient EstructuraService estructuraService;
    private Estructura estructuraSelect;
    private List<Estructura> estructuraList;
    private List<Estructura> estructuraAux;
    private List<Estructura> almacenesServiList;
    private List<Estructura> serviciosList;
    private List<Estructura> serviciosItemList;

    @Autowired
    private transient ReabastoService reabastoService;
    private Reabasto reabastoSelect;
    private List<FolioAlternativoFolioMus> listFolioAlternativos;
    private SolicitudReabastoLazy solicitudReabastoLazy;

    @Autowired
    private transient ReabastoInsumoService insumoService;
    private ReabastoInsumo insumoSelect;
    private List<ReabastoInsumo> insumoList;
    private List<ReabastoInsumo> insumoListCopy;
    private List<ReabastoXServicio> reabastoXServicio;
    private ReabastoInsumo itemSelect;

    @Autowired
    private transient CatalogoGeneralService catalogoGeneralService;
    private List<CatalogoGeneral> tipoInsumoList;

    @Autowired
    private transient MedicamentoService mediService;
    private Medicamento medicamento;
    private List<Medicamento> medicamentoList;

    @Autowired
    private transient ProveedorService provService;
    private Proveedor proveedorSelect;
    private List<Proveedor> provList;

    @Autowired
    private transient PresentacionMedicamentoService presentacionMedicamentoService;

    @Autowired
    private transient InventarioService inventarioService;
    private Inventario inventarioSelect;
    private List<Inventario> inventarioList;

    @Autowired
    private transient ReportesService reportesService;

    @Autowired
    private transient EntidadHospitalariaService entidadHospitalariaService;

    @Autowired
    private transient FolioAlternativoFolioMusService folioAlternativoFolioMusService;

    @Autowired
    private transient DiaFestivoService feriadosService;
    private List<DiaFestivo> feriadosList;

    @Autowired
    private transient UsuarioService usuarioService;

    @Autowired
    private transient TransaccionService transaccionService;

    @Autowired
    private transient EstructuraAlmacenServicioService almacenServicioService;

    @Autowired
    private transient DetalleInsumoSiamService detalleInsumoSiamService;

    @Autowired
    private transient AlmacenControlService controlService;

    @Autowired
    private transient ServletContext servletContext;

    @PostConstruct
    public void init() {
        LOGGER.trace("mx.mc.magedbean.SolicitudInsumoServicioMB.init()");
        solicRebastoInfoCantidadMenor = "solicRebasto.info.cantidadMenor";
        solicRebastoInfoRequiereAutorizacion = "solicRebasto.info.requiereAutorizacion";
        initialize();
        buscarReabasto();
    }

    /*  METHOD PRIVATE     */
    private void initialize() {

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        separaInsumos = sesion.isSepararInsumos();
        orgdes = Constantes.INACTIVO;
        regexNumber = Constantes.regexNumber;
        currentUser = sesion.getUsuarioSelected();
        dotacionDiaria = sesion.isDotacionDiaria();
        campoCantidadXClave = sesion.isActivaCampoReabastoCantidadXClave();
        campoCantidadXLote = sesion.isActivaCampoReabastoCantidadXLote();
        dotacionMC = sesion.isDotacionDiariaCuracion();
        surtSabado = sesion.isSurtimientoSabado();
        surtDomingo = sesion.isSurtimientoDomingo();
        fechaActual = new Date();
        estructuraSelect = new Estructura();
        reabastoSelect = new Reabasto();
        listFolioAlternativos = new ArrayList<>();
        insumoSelect = new ReabastoInsumo();
        estructuraList = new ArrayList<>();
        tipoInsumoList = new ArrayList<>();
        insumoList = new ArrayList<>();
        insumoListCopy = new ArrayList<>();
        feriadosList = new ArrayList<>();
        idGrupoEspecial = null;
        idGrupoUno = null;
        idGrupoDos = null;
        idGrupoTres = null;
        idGrupoCuatro = null;
        idGrupoCinco = null;
        provList = new ArrayList<>();
        numeroRegistros = 0;
        cadenaBusqueda = "";
        contEdit = Constantes.ACTIVO;
        activo = Constantes.INACTIVO;
        isAdmin = Constantes.INACTIVO;
        edit = Constantes.INACTIVO;
        farmacia = Constantes.INACTIVO;
        printSolicitud = Constantes.INACTIVO;
        messaje = Constantes.ACTIVO;
        tipoAlmacen = "";
        idEstructura = "";
        msjAlert = "";
        userResponsable = "";
        passResponsable = "";
        tipoInsumo = 0;
        cantSolicitada = 1;
        msjMdlSurtimiento = true;
        mostrarFolioAlternativo = Constantes.INACTIVO;
        nuevaOrden = Constantes.INACTIVO;
        pointCtrl = (dotacionDiaria || dotacionMC) ? Constantes.INACTIVO : Constantes.ACTIVO;
        urlSIAM = sesion.isFunWsSiamActivo();
        urlGeneraColectivoSIAM = sesion.getConSiamGeneraColectivo();
        urlConsultaColectivoSIAM = sesion.getFunSiamConsultaDetalle();
        urlConsultaInsumoEnCT = sesion.getConSiamConsultaInsumoEnCT();
        colectivoXServicio = sesion.isFunSolicitudXServicio();
        colectivoSIAM = null;
        almacenesServiList = new ArrayList<>();
        reabastoXServicio = new ArrayList<>();
        serviciosList = new ArrayList<>();
        permiso = Comunes.obtenerPermisos(Transaccion_Enum.ORDENREABASTOSERVICIO.getSufijo());
        nombreCompleto = (currentUser.getNombre() + " " + currentUser.getApellidoPaterno() + " " + currentUser.getApellidoMaterno()).toUpperCase();
        validarUsuarioAdministrador();
        obtieneAlmacen();
        obtieneProveedor();
        insumos();
        destinoCole = false;
        showExpansion = false;
        idServiceAdd = "";
        medicamento = null;       
        campSiam = Constantes.INACTIVO;
        insumoSiamList = new ArrayList<>();
        clientSiam = new ColectivoSIAMClient();
    }

    private void clean() {
        reabastoSelect = new Reabasto();
        listFolioAlternativos = new ArrayList<>();
        insumoList = new ArrayList<>();
        idGrupoEspecial = null;
        idGrupoUno = null;
        idGrupoDos = null;
        idGrupoTres = null;
        idGrupoCuatro = null;
        idGrupoCinco = null;
        numeroRegistros = 0;
        msjMdlSurtimiento = true;
    }

    public void validarUsuarioAdministrador() {
        try {
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
            this.isAdmin = sesion.isAdministrador();
            if (!this.isAdmin) {
                this.idEstructura = currentUser.getIdEstructura();
            }
            mostrarFolioAlternativo = sesion.isMostrarFolioAlternativo();
        } catch (Exception e) {
            LOGGER.error("Error en el metodo validarUsuarioAdministrador :: {}", e.getMessage());
        }
    }

    private void obtieneAlmacen() {
        try {
            Estructura estruct = new Estructura();
            estruct.setIdEstructura(currentUser.getIdEstructura());
            estructuraSelect = estructuraService.obtener(estruct);
            if (estructuraSelect != null) {
                campSiam = estructuraSelect.getClaveEstructura().equals(Constantes.PARA_CLAVE_CEDIME);
                idAlmacen = estructuraSelect.getIdTipoAlmacen();
                if (this.isAdmin) {
                    estructuraAux = estructuraService.obtenerAlmacenes();
                    if (estructuraAux != null) {
                        for (Estructura est : estructuraAux) {
                            if (est.getIdTipoAreaEstructura() == 1) {
                                buscaEnList(est);
                                break;
                            }
                        }
                    }
                } else {
                    idEstructura = estructuraSelect.getIdEstructura();
                    tipoAlmacen = currentUser.getIdEstructura();
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al obtener Estructura: {}", ex.getMessage());
        }
    }

    private void obtieneProveedor() {
        try {
            provList = provService.obtenerListaProveedores();
        } catch (Exception ex) {
            LOGGER.error("Error al obtener los Proveedores: {}", ex.getMessage());
        }
    }

    private void insumos() {
        LOGGER.trace("Load Insumos");
        try {
            tipoInsumoList = catalogoGeneralService.obtenerCatalogosPorGrupo(Constantes.TIPO_INSUMO);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener Insumos: {}", ex.getMessage());
        }
    }

    private void buscaEnList(Estructura padre) {
        try {
            if (padre != null) {
                for (int i = 0; i < estructuraAux.size(); i++) {
                    if (estructuraAux.get(i).getIdEstructuraPadre() != null
                            && estructuraAux.get(i).getIdEstructuraPadre().toLowerCase().contains(padre.getIdEstructura().toLowerCase())) {
                        if (estructuraAux.get(i).getActiva() == 1) {
                            estructuraList.add(estructuraAux.get(i));
                        }
                        buscaEnList(estructuraAux.get(i));
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al buscar: {}", ex.getMessage());
        }
    }

    private int nivelSurtimiento(ReabastoInsumo item) {
        int x = 0;
        if (item.getSurtido() == 0) //No se ha surtido
        {
            x = 1;
        } else if (item.getDotacion() > item.getSurtido()) //Surtido Parcial
        {
            x = 2;
        } else if (item.getDotacion().equals(item.getSurtido())) //surtido Completo
        {
            x = 3;
        } else if (item.getSurtido() > item.getDotacion()) //Surtido extra
        {
            x = 4;
        }

        return x;
    }

    private void ordenAutomatica(Estructura estu, int tAlmacen) throws Exception {
        if (estu != null) {

            insumoList = insumoService.obtenerListaNormal(estu.getIdEstructura(), estu.getIdEstructuraPadre(), tAlmacen, tipoInsumo);
            
            if (estructuraSelect != null
                    && estructuraSelect.getIdTipoAlmacen() < Constantes.SUBALMACEN) {
                insumoList.forEach(item -> {
                    if (item.getDotacion() > 0) {
                        item.setDotacion(redondeaCantidad(item.getPiezasCaja(), item.getDotacion()));
                    }
                });
            }
            if (dotacionDiaria && tipoInsumo == Constantes.MEDI) {
                int dias = calDiasDotacion();
                if (dias > 1) {
                    insumoList.forEach(item -> {
                        if (item.getDotacion() > 0) {
                            item.setDotacion(item.getDotacion() * dias);
                            if ((item.getDotacion() - item.getSurtido()) >= 0) {
                                item.setCantidadSolicitada(item.getDotacion() - item.getSurtido());
                            } else {
                                item.setCantidadSolicitada(0);
                            }
                            //calcular nivel de surtimiento
                            item.setNivelSurt(nivelSurtimiento(item));
                        }
                    });
                } else {
                    insumoList.forEach(item -> {
                        if (item.getDotacion() > 0) {
                            int cant = redondeaCantidad(item.getPiezasCaja(), item.getDotacion() - item.getCantidadActual());
                            if (cant < 0) {
                                cant = 0;
                            }

                            item.setCantidadSolicitada(cant);
                            //calcular nivel de surtimiento
                            item.setNivelSurt(nivelSurtimiento(item));
                        }
                    });
                }
            } else if (dotacionMC && tipoInsumo == Constantes.MATC) {
                int dias = calDiasDotacion();
                insumoList.forEach(item -> {
                    if (item.getDotacion() > 0) {
                        item.setDotacion(item.getDotacion() * dias);
                        if ((item.getDotacion() - item.getSurtido()) >= 0) {
                            item.setCantidadSolicitada(item.getDotacion() - item.getSurtido());
                        } else {
                            item.setCantidadSolicitada(0);
                        }
                        //calcular nivel de surtimiento
                        item.setNivelSurt(nivelSurtimiento(item));
                    }
                });
            }
            numeroRegistros = insumoList.size();
        }
    }

    private int calDiasDotacion() {
        int dias = 1;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            feriadosList = feriadosService.topDiasFeriados();
            if (!feriadosList.isEmpty()) {
                // Por default se le suma un día    
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                do {
                    // verificamos si el siguente día es sabado
                    if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY && !surtSabado) {
                        calendar.add(Calendar.DAY_OF_YEAR, 1);
                    }
                    // Verificamos si el dia siguiente es Domingo
                    if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY && !surtDomingo) {
                        calendar.add(Calendar.DAY_OF_YEAR, 1);
                    }
                    exits = false;
                    //Buscamos el dia en la lista de diasFeriados
                    feriadosList.forEach(fecha -> {
                        if (dateFormat.format(fecha.getFecha()).equals(dateFormat.format(calendar.getTime()))) {
                            exits = true;
                        }
                    });
                    //si lo encontro en la lista se le suma un dia mas
                    if (exits) {
                        calendar.add(Calendar.DAY_OF_YEAR, 1);
                    }
                } while (exits);

                dias = (int) ((calendar.getTime().getTime() - date.getTime()) / 86400000);
            }
        } catch (Exception ex) {
            LOGGER.error("Error al obtener el numero de dias para la dotación diaria: {}", ex.getMessage());
        }
        return dias;
    }

    private boolean existsMedtoEnLista(String clv) {
        boolean exite = Constantes.INACTIVO;
        int i = 0;
        for (ReabastoInsumo aux : insumoList) {
            if (aux.getClave().contains(clv)) {
                exite = Constantes.ACTIVO;
                activeRow = i;
                break;
            }
            i++;
        }
        return exite;
    }

    private int redondeaCantidad(int xcaja, int solicitada) {
        int redon = 0;
        if ((solicitada % xcaja) > 0) {
            int ent = (solicitada / xcaja) + 1;
            redon = xcaja * ent;
        } else {
            redon = solicitada;
        }
        return redon;
    }

    private boolean existeReabasto(String id) {
        boolean exite = false;
        try {
            Reabasto rea = new Reabasto();
            rea.setIdReabasto(id);
            Reabasto reab = reabastoService.obtener(rea);
            if (reab != null) {
                exite = Constantes.ACTIVO;
            }
        } catch (Exception ex) {
            LOGGER.error("Error al Buscar el Reabasto: {}", ex.getMessage());
        }
        return exite;
    }

    private String ucFirst(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        } else {
            return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
        }
    }

    private String enviarColectivoSIAM(Reabasto reabasto, String idEstructura, List<ReabastoInsumo> listaInsumos,int idx) {
        String reenvioSIAM = null;
        boolean sigue=true;
        try {
            Estructura estru = estructuraService.obtenerEstructura(idEstructura);
            //Si la estructura es de tipo almacen, se buscara el servicio al que surte
            if (Objects.equals(estru.getIdTipoAreaEstructura(), TipoAreaEstructura_Enum.ALMACEN.getValue())) {
                List<EstructuraAlmacenServicio> almacenServicio = almacenServicioService.obtenerAlmacenServicio(estru.getIdEstructura());
                if (!almacenServicio.isEmpty()) {
                    estru = estructuraService.obtenerEstructura(almacenServicio.get(0).getIdEstructuraServicio());
                }
            }

            List<InsumoSIAM> insumos = new ArrayList<>();
            List<DetalleInsumoSiam> detalleSiam = new ArrayList<>();
            List<AlmacenControl> insumosPC = new ArrayList<>();
            String idFolioArternativo = Comunes.getUUID();
            for (ReabastoInsumo aux : listaInsumos) {
                if (aux.getCantidadSolicitada() > 0) {
                    Medicamento medicam = mediService.obtenerPorIdMedicamento(aux.getIdInsumo());
                    if(tipoInsumo==0)
                        tipoInsumo = medicam.getTipo();
                    InsumoSIAM insumo = new InsumoSIAM();
                    insumo.setClaveInsumo(medicam.getClaveInstitucional().replace(".", ""));
                    int piezas = redondeaCantidad(aux.getPiezasCaja(), aux.getCantidadSolicitada());
                    int cajas = piezas / aux.getPiezasCaja();
                    insumo.setCantidad(cajas);

                    DetalleInsumoSiam insumoSiam = new DetalleInsumoSiam();
                    insumoSiam.setIdDetalleInsumoSiam(Comunes.getUUID());
                    insumoSiam.setIdFolioAlternativo(idFolioArternativo);
                    insumoSiam.setIdInsumo(medicam.getIdMedicamento());
                    insumoSiam.setCantidadSolicitada(cajas);
                    insumoSiam.setCantidadSurtida(0);
                    insumoSiam.setIdEstructura(estru.getIdEstructura());
                    insumoSiam.setInsertFecha(new Date());
                    insumoSiam.setInsertIdUsuario(currentUser.getIdUsuario());

                    if (aux.getIdAlmacenPuntosControl() != null) {
                        AlmacenControl ac = new AlmacenControl();
                        ac.setIdAlmacenPuntosControl(aux.getIdAlmacenPuntosControl());
                        ac.setSolicitud(1);
                        ac.setUpdateFecha(new Date());
                        ac.setUpdateIdUsuario(aux.getInsertIdUsuario());
                        insumosPC.add(ac);
                    }
                    insumos.add(insumo);
                    detalleSiam.add(insumoSiam);
                }
            }  
            colectivoSIAM = new SendColectivoSIAM();
            colectivoSIAM.setTipoInsumo(tipoInsumo == Constantes.MEDI ? "M" : "C");
            colectivoSIAM.setEspecialidad(estru.getNombre().toUpperCase());//"ADMISIÓN CONTINUA ADULTOS");//);
            colectivoSIAM.setFolioExterno(reabasto.getFolio()+idx);
            colectivoSIAM.setNombreUsuario(currentUser.getMatriculaPersonal());
            colectivoSIAM.setAppQueSolicita("APP-Tabasco");
            colectivoSIAM.setClaveCentroTrabajo("027-204-00"); 
            
            if(insumos.size()>0){
                colectivoSIAM.setInsumos(insumos); 
                FolioAlternativoFolioMus folioAlt = new FolioAlternativoFolioMus();
                folioAlt.setIdFolioAlternativo(idFolioArternativo);
                folioAlt.setFolioMUS(reabasto.getFolio());
                folioAlt.setInsertFecha(new Date());
                folioAlt.setInsertIdUsuario(currentUser.getIdUsuario());

                ResponseColectivoSIAM response = clientSiam.generaColectivo(colectivoSIAM,urlGeneraColectivoSIAM);
                if(response!= null && response.getGenerado() == 1 && response.getNuevoFolio().length()<36){                      
                    try {
                        FolioAlternativoFolioMus folioAlter = folioAlternativoFolioMusService.obtenerFolioAlternativo(response.getNuevoFolio(),reabasto.getFolio());
                        if(folioAlter!=null){
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR,"Error al generar el colectivo, el folio: "+folioAlter.getFolioAlternativo()+" ya éxiste.",null);
                            folioAlt.setEstatus(EstatusReabasto_Enum.REGISTRADA.toString());
                            sigue=false;
                        }
                        for (InsumoSIAM item : response.getInsumos()) {                             
                            for(DetalleInsumoSiam detalle: detalleSiam){
                                Medicamento insumo = mediService.obtenerMedicamento(detalle.getIdInsumo());
                                if(insumo.getClaveInstitucional().replace(".","").equals(item.getClaveInsumo())){
                                   detalle.setCantidadAprobada(item.getAprobado());
                                   break;
                                }
                            }
                        }
                        
                        if(sigue){
                            folioAlt.setFolioAlternativo(response.getNuevoFolio());
                            folioAlt.setEstatus(EstatusReabasto_Enum.SOLICITADA.toString());
                            estatuReabastoMus = EstatusReabasto_Enum.SOLICITADA;

                            controlService.actualizarMasivo(insumosPC);
                            reenvioSIAM = response.getNuevoFolio();
                        }else{
                            insumosPC.forEach(c ->{
                                c.setSolicitud(0);
                            });
                            controlService.actualizarMasivo(insumosPC);
                        }
                    } catch (Exception ex) {
                        LOGGER.error("Ocurrio unn error al validar la existencia del folio alternativo.",ex);
                        folioAlt.setEstatus(EstatusReabasto_Enum.REGISTRADA.toString());
                        sigue=false;
                    }
                } else {
                    if (response != null) {
                        LOGGER.error(response.getNuevoFolio() + ":" + response.getMensaje());
                    }
                    String mensaje = "No se proceso el servicio: " + estru.getNombre() + ", Motivo: ";
                    mensaje += response != null ? (response.getMensaje() != null ? response.getMensaje() : response.getNuevoFolio()) : "Error al conectarse con SIAM.";
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, mensaje, null);
                    folioAlt.setEstatus(EstatusReabasto_Enum.REGISTRADA.toString());
                }

                FolioAlternativoFolioMus alternativo = folioAlternativoFolioMusService.obtenerFolioAlt(reabasto.getFolio(), estru.getIdEstructura());
                if (alternativo == null) {
                    folioAlternativoFolioMusService.insertar(folioAlt);
                    detalleInsumoSiamService.insertarLista(detalleSiam);
                }else{
                    folioAlt.setIdFolioAlternativo(alternativo.getIdFolioAlternativo());
                    folioAlt.setUpdateFecha(new Date());
                    folioAlt.setUpdateIdUsuario(currentUser.getIdUsuario());
                    folioAlternativoFolioMusService.actualizarFolioAlt(folioAlt.getFolioAlternativo(), folioAlt.getFolioMUS(), folioAlt.getUpdateIdUsuario(), folioAlt.getEstatus(), estru.getIdEstructura());
                    detalleSiam.forEach(item -> {
                        item.setIdFolioAlternativo(alternativo.getIdFolioAlternativo());
                    });
                    detalleInsumoSiamService.actualizarLista(detalleSiam);
                }
            }else
                reenvioSIAM ="";
            
            tipoInsumo=0;
        }catch(Exception ex){
            LOGGER.error("Ocurrio un error al enviar el colectivo a SIAM",ex);
        }

        return reenvioSIAM;
    }

    private ResponseDetalleSIAM ConsultaColectivoSIAM(String folio, String fExterno) {
        ResponseDetalleSIAM responseSIAM = new ResponseDetalleSIAM();
        try {
            consultaColectivoSIAM = new ColectivoSurtidoDetalle();
            consultaColectivoSIAM.setFolio(folio);
            consultaColectivoSIAM.setFolioExterno(fExterno);

            responseSIAM = clientSiam.consultaColectivoSIAM(consultaColectivoSIAM, urlConsultaColectivoSIAM);            
        }catch(Exception ex){
            LOGGER.error("Ocurrio un error al consultar el colectivo::", ex);
        }
        return responseSIAM;
    }

    private List<Estructura> obtenerAlmacenesServicios(String idAlmacen) {
        List<Estructura> lista = new ArrayList<>();
        List<String> list = new ArrayList<>();

        try {
            //Obtenemos la lista de almacenes hijos
            list = estructuraService.obtenerIdsEstructuraJerarquica(idAlmacen, true);
            for (String id : list) {
                Estructura item = estructuraService.obtenerEstructura(id);
                if (item != null) {
                    lista.add(item);
                }
            }
            //Obtenemos la lista de servicios que surte el almacen
            List<EstructuraAlmacenServicio> almacenServicio = almacenServicioService.obtenerAlmacenServicio(idAlmacen);
            for (EstructuraAlmacenServicio servicio : almacenServicio) {
                Estructura item = estructuraService.obtenerEstructura(servicio.getIdEstructuraServicio());
                if (item != null && item.isMostrarPC()) {
                    lista.add(item);
                }                               
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return lista;
    }

    private void calculaColectivoXServicio(Reabasto reabastoSel) {
        try {
            if (almacenesServiList.isEmpty()) {
                almacenesServiList = obtenerAlmacenesServicios(reabastoSel.getIdEstructura());
            }

            if(campSiam){
                insumoSiamList = clientSiam.consultaListaExistensiaSIAM(urlConsultaInsumoEnCT);
            }
            insumoList = insumoService.obtenerListaGlobalCEDIME();
            numeroRegistros = insumoList.size();

            reabastoXServicio = new ArrayList<>();
            if (almacenesServiList != null && !almacenesServiList.isEmpty()) {
                List<ReabastoInsumo> insumosServicio = new ArrayList<>();
                for (Estructura estruc : almacenesServiList) {
                    Reabasto reabasto = new Reabasto();
                    reabasto.setIdReabasto(Comunes.getUUID());
                    reabasto.setIdEstructura(estruc.getIdEstructura());
                    reabasto.setIdEstructuraPadre(reabastoSel.getIdEstructura());
                    reabasto.setIdTipoOrden(reabastoSel.getIdTipoOrden());
                    reabasto.setFechaSolicitud(reabastoSel.getFechaSolicitud());
                    reabasto.setIdUsuarioSolicitud(reabastoSel.getIdUsuarioSolicitud());
                    reabasto.setIdProveedor(reabastoSel.getIdEstructura());
                    reabasto.setInsertFecha(reabastoSel.getInsertFecha());
                    reabasto.setInsertIdUsuario(reabastoSel.getInsertIdUsuario());
                    reabasto.setAlmacen(estruc.getNombre());

                    insumosServicio = insumoService.obtenerListaInsumoByEstructura(estruc.getIdEstructura(), null, "0");

                    for (ReabastoInsumo insumo : insumosServicio) {
                        insumo.setIdReabastoInsumo(Comunes.getUUID());
                        insumo.setIdReabasto(reabasto.getIdReabasto());
                        insumo.setCantidadSolicitada(redondeaCantidad(insumo.getPiezasCaja(), insumo.getCantidadSolicitada()));

                        insumoList.forEach(ite -> {
                            if (ite.getIdInsumo().equals(insumo.getIdInsumo())) {
                                if (ite.getDetalleInsumo() == null) {
                                    insumos = new ArrayList<>();
                                    ite.setDetalleInsumo(insumos);
                                }
                                ite.setMinimo(ite.getMinimo() + insumo.getMinimo());
                                ite.setReorden(ite.getReorden() + insumo.getReorden());
                                ite.setMaximo(ite.getMaximo() + insumo.getMaximo());                                
                                ite.setCantidadActual(ite.getCantidadActual() + insumo.getCantidadActual());
                                if(ite.getSurtido() == null) ite.setSurtido(0);
                                
                                if(dotacionDiaria){
                                    int dotacion=0;
                                    int dias = calDiasDotacion();
                                    if (dias > 1 && insumo.getDotacion() > 0) {
                                         dotacion = insumo.getDotacion() * dias;
                                        if ((dotacion - insumo.getSurtido()) >= 0) {
                                            ite.setCantidadSolicitada(ite.getCantidadSolicitada() + (dotacion - insumo.getSurtido()));
                                        } else {
                                            ite.setCantidadSolicitada(0);
                                        }
                                        //calcular nivel de surtimiento
                                        ite.setNivelSurt(nivelSurtimiento(ite));                                                                                
                                    } else if (insumo.getDotacion() > 0) {
                                        dotacion = insumo.getDotacion();
                                        int cant = redondeaCantidad(ite.getPiezasCaja(), dotacion - insumo.getSurtido());
                                        if (cant < 0) {
                                            cant = 0;
                                        }
                                        ite.setCantidadSolicitada(ite.getCantidadSolicitada() + cant);
                                        //calcular nivel de surtimiento
                                        ite.setNivelSurt(nivelSurtimiento(ite));
                                    }
                                    ite.setDotacion(ite.getDotacion()+dotacion);
                                }else
                                    ite.setCantidadSolicitada(ite.getCantidadSolicitada() + insumo.getCantidadSolicitada());

                                InsumoServicio ins = new InsumoServicio();
                                ins.setIdEstructura(estruc.getIdEstructura());
                                ins.setServicio(estruc.getNombre());
                                ins.setIdInsumo(insumo.getIdInsumo());
                                ins.setClave(insumo.getClave());
                                ins.setMinimo(insumo.getMinimo());
                                ins.setReorden(insumo.getReorden());
                                ins.setMaximo(insumo.getMaximo());
                                ins.setDotacion(insumo.getDotacion());
                                ins.setCantidadActual(insumo.getCantidadActual());
                                if(dotacionDiaria){
                                    int dias = calDiasDotacion();
                                    if (dias > 1 && ins.getDotacion() > 0) {
                                        ins.setDotacion(ins.getDotacion() * dias);
                                        if ((ins.getDotacion() - insumo.getSurtido()) >= 0) {
                                            ins.setCantidadSolicitada(ins.getDotacion() - ite.getSurtido());
                                        } else {
                                            ins.setCantidadSolicitada(0);
                                        }                                                                               
                                    } else if (ins.getDotacion() > 0) {
                                        int cant = redondeaCantidad(insumo.getPiezasCaja(), ins.getDotacion() - ins.getCantidadActual());
                                        if (cant < 0) {
                                            cant = 0;
                                        }
                                        ins.setCantidadSolicitada(cant);
                                    }                                        
                                }
                                else
                                    ins.setCantidadSolicitada(insumo.getCantidadSolicitada());

                                ite.getDetalleInsumo().add(ins);
                            }
                        });
                    }

                    if (insumosServicio.size() > 0) {
                        ReabastoXServicio servicio = new ReabastoXServicio();
                        servicio.setReabasto(reabasto);
                        servicio.setInsumos(insumosServicio);

                        reabastoXServicio.add(servicio);
                    }
                    if(!insumoSiamList.isEmpty())
                    {
                        insumoList.forEach(item ->{
                            InsumoCTSiam insumoSiam = insumoSiamList.stream().filter(f-> f.getCveInsumo().equals(item.getClave())).findAny().orElse(null);
                            if(insumoSiam!=null){
                                item.setCantidadPorClave((insumoSiam.getExistencia()-insumoSiam.getExistenciaApartados()) * item.getPiezasCaja());
                            }else{
                                item.setCantidadPorClave(0);
                            }
                            
                        });
                    }
                }                
            }

        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    private List<Reabasto> obtenerColectivosGenerados(Reabasto reabasto) {
        List<Reabasto> colectivos = new ArrayList<>();
        try {
            List<Estructura> servicios = estructuraService.estructurasByFolioMUS(reabasto.getFolio(), null);

            for (Estructura estruct : servicios) {
                Reabasto colectivo = new Reabasto();
                colectivo.setIdReabasto(Comunes.getUUID());
                colectivo.setIdEstructura(estruct.getIdEstructura());
                colectivo.setFolio(estruct.getUbicacion());// Se utiliza el campo ubicacion para pasar el folio siam                
                colectivo.setIdEstructuraPadre(reabasto.getIdEstructura());
                colectivo.setIdTipoOrden(reabasto.getIdTipoOrden());
                colectivo.setFechaSolicitud(reabasto.getFechaSolicitud());
                colectivo.setIdUsuarioSolicitud(reabasto.getIdUsuarioSolicitud());
                colectivo.setIdProveedor(reabasto.getIdEstructura());
                colectivo.setInsertFecha(reabasto.getInsertFecha());
                colectivo.setInsertIdUsuario(reabasto.getInsertIdUsuario());
                colectivo.setAlmacen(estruct.getNombre());

                colectivos.add(colectivo);
            }

        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return colectivos;
    }

    private ReabastoXServicio newReabastoXServ(String idService, String idInsumo, Integer cantSol) {
        ReabastoXServicio xserv = new ReabastoXServicio();
        Reabasto reabasto = new Reabasto();
        List<ReabastoInsumo> items = new ArrayList<>();

        try {
            reabasto.setIdReabasto(Comunes.getUUID());
            reabasto.setIdEstructura(idService);
            reabasto.setIdEstructuraPadre(reabastoSelect.getIdEstructura());
            reabasto.setIdTipoOrden(reabastoSelect.getIdTipoOrden());
            reabasto.setFechaSolicitud(reabastoSelect.getFechaSolicitud());
            reabasto.setIdUsuarioSolicitud(reabastoSelect.getIdUsuarioSolicitud());
            reabasto.setIdProveedor(reabastoSelect.getIdEstructura());
            reabasto.setInsertFecha(reabastoSelect.getInsertFecha());
            reabasto.setInsertIdUsuario(reabastoSelect.getInsertIdUsuario());

            ReabastoInsumo insumo = insumoService.obtenerListaInsumoByEstructura(idService, idInsumo, "0").get(0);

            if (insumo != null) {
                reabasto.setAlmacen(insumo.getAlmacenServicio());
                insumo.setCantidadSolicitada(cantSol);
                items.add(insumo);
            }
            xserv.setReabasto(reabasto);
            xserv.setInsumos(items);

        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al crear el reabastoXservicio", ex);
        }
        return xserv;
    }

    private List<ReabastoXServicio> generarColectivos(Reabasto reabasto) {
        List<ReabastoXServicio> colectivos = new ArrayList<>();
        try {
            List<Estructura> servicios = estructuraService.estructurasByFolioMUS(reabasto.getFolio(), EstatusReabasto_Enum.REGISTRADA.toString());
            servicios.forEach(servicio -> {
                try {
                    Reabasto reaba = new Reabasto();
                    reaba.setIdReabasto(Comunes.getUUID());
                    reaba.setFolio(reabasto.getFolio());
                    reaba.setIdEstructura(servicio.getIdEstructura());
                    reaba.setIdEstructuraPadre(reabasto.getIdEstructura());
                    reaba.setIdTipoOrden(reabasto.getIdTipoOrden());
                    reaba.setFechaSolicitud(new Date());
                    reaba.setIdUsuarioSolicitud(reabasto.getIdUsuarioSolicitud());
                    reaba.setIdProveedor(reabasto.getIdEstructura());
                    reaba.setInsertFecha(reabasto.getInsertFecha());
                    reaba.setInsertIdUsuario(reabasto.getInsertIdUsuario());
                    reaba.setAlmacen(servicio.getNombre());

                    List<DetalleInsumoSiam> insumosServ = detalleInsumoSiamService.obtenerDetalleXFolioEstructura(reaba.getFolio(), servicio.getIdEstructura(), EstatusReabasto_Enum.REGISTRADA.toString());
                    List<ReabastoInsumo> insumosServicio = new ArrayList<>();
                    for (DetalleInsumoSiam detalle : insumosServ) {
                        Medicamento item = mediService.obtenerPorIdMedicamento(detalle.getIdInsumo());
                        AlmacenControl ctrl = controlService.obtenerIdPuntosControl(servicio.getIdEstructura(), detalle.getIdInsumo());
                        ReabastoInsumo insumo = new ReabastoInsumo();
                        insumo.setIdReabastoInsumo(Comunes.getUUID());
                        insumo.setIdReabasto(reaba.getIdReabasto());
                        insumo.setIdInsumo(detalle.getIdInsumo());
                        insumo.setCantidadSolicitada(detalle.getCantidadSolicitada() * item.getFactorTransformacion());
                        insumo.setPiezasCaja(item.getFactorTransformacion());
                        insumo.setIdAlmacenPuntosControl(ctrl.getIdAlmacenPuntosControl());
                        insumo.setMinimo(ctrl.getMinimo());
                        insumo.setReorden(ctrl.getReorden());
                        insumo.setMaximo(ctrl.getMaximo());
                        insumo.setDotacion(ctrl.getDotacion());

                        insumosServicio.add(insumo);
                    }

                    if (insumosServicio.size() > 0) {
                        ReabastoXServicio item = new ReabastoXServicio();
                        item.setReabasto(reaba);
                        item.setInsumos(insumosServicio);

                        colectivos.add(item);
                    }

                } catch (Exception ex) {
                    LOGGER.error("Ocurrio un error al obtener el detalle de insumos", ex);
                }
            });

        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al generar los colectivos", ex);
        }
        return colectivos;
    }

    /*  METHOD PUBLIC     */
    public List<ReabastoInsumo> insertInsumoMasivo(List<ReabastoInsumo> list, int idEstatus) {
        try {
            for (ReabastoInsumo aux : list) {
                aux.setIdReabasto(reabastoSelect.getIdReabasto());
                aux.setIdReabastoInsumo(Comunes.getUUID());
                aux.setIdEstatusReabasto(idEstatus);
                aux.setCantidadComprometida(0);
                aux.setCantidadSurtida(0);
                aux.setCantidadRecibida(0);
                aux.setInsertFecha(new Date());
                aux.setInsertIdUsuario(currentUser.getIdUsuario());
            }
        } catch (Exception ex) {
            LOGGER.error("Error al Insertar Insumo Masivo: {}", ex.getMessage());
        }
        return list;
    }

    public List<ReabastoInsumo> updateInsumoMasivo(List<ReabastoInsumo> list, int idEstatus) {
        try {
            for (ReabastoInsumo aux : list) {
                aux.setIdReabasto(reabastoSelect.getIdReabasto());
                aux.setIdEstatusReabasto(idEstatus);
                aux.setCantidadComprometida(0);
                aux.setCantidadSurtida(0);
                aux.setCantidadRecibida(0);
                aux.setUpdateFecha(new Date());
                aux.setUpdateIdUsuario(currentUser.getIdUsuario());
            }
        } catch (Exception ex) {
            LOGGER.error("Error al Actualizar Insumo Masivo: {}", ex.getMessage());
        }
        return list;
    }

    public List<ReabastoInsumo> obtenerInsumosParaActualizar(List<ReabastoInsumo> list) {
        List<ReabastoInsumo> newList = new ArrayList<>();
        List<Integer> index = new ArrayList<>();

        for (ReabastoInsumo insumo : list) {
            for (ReabastoInsumo copy : insumoListCopy) {
                if (copy.getIdInsumo().equals(insumo.getIdInsumo())) {
                    index.add(list.indexOf(insumo));
                    newList.add(insumo);
                    break;
                }
            }
        }
        for (int i = index.size() - 1; i >= 0; i--) {
            list.remove((int) index.get(i));
        }

        return newList;
    }

    public String generateRandom() {
        return java.util.UUID.randomUUID().toString();
    }

    public void printOrdenReabasto(Reabasto reabasto) throws Exception {
        LOGGER.trace("SolicitudInsumoServicioMB.printOrdenReabasto(reabasto)");
        try {
            Estructura estruct = new Estructura();
            estruct.setIdEstructura(reabasto.getIdEstructura());
            estruct = estructuraService.obtenerEstructura(reabasto.getIdEstructura());
            if (estruct.getIdEntidadHospitalaria() == null) {
                estruct.setIdEntidadHospitalaria("");
            }

            EntidadHospitalaria enti = entidadHospitalariaService.obtenerEntidadById(estruct.getIdEntidadHospitalaria());
            if (enti == null) {
                enti = new EntidadHospitalaria();
                enti.setDomicilio("");
                enti.setNombre("");

            }
            reabasto.setDomicilio(enti.getDomicilio());
            reabasto.setNombreEntidad(enti.getNombre());
            byte[] buffer = null;
            if (separaInsumos) {
                listFolioAlternativos = folioAlternativoFolioMusService.obtenerFoliosAlternativos(reabasto.getFolio());
                if (listFolioAlternativos.isEmpty()) {
                    buffer = reportesService.reporteSolicitudReabasto(
                            reabasto,
                            estruct.getIdTipoAlmacen(),
                            currentUser.getNombre() + ' ' + currentUser.getApellidoPaterno() + ' ' + currentUser.getApellidoMaterno(),
                            currentUser.getCedProfesional());
                } else {
                    buffer = reportesService.reporteSolicitudReabasto(
                            reabasto, listFolioAlternativos,
                            estruct.getIdTipoAlmacen(),
                            currentUser.getNombre() + ' ' + currentUser.getApellidoPaterno() + ' ' + currentUser.getApellidoMaterno(),
                            currentUser.getCedProfesional());
                }
            } else if (colectivoXServicio) {
                buffer = printOrdenReabastoServicioAll(reabasto, false);
            } else {
                buffer = reportesService.reporteSolicitudReabasto(
                        reabasto,
                        estruct.getIdTipoAlmacen(),
                        currentUser.getNombre() + ' ' + currentUser.getApellidoPaterno() + ' ' + currentUser.getApellidoMaterno(),
                        currentUser.getCedProfesional());
            }
            if (buffer != null) {
                SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "sesionMB");
                sesion.setReportStream(buffer);
                sesion.setReportName(String.format("SolicitudOrdenReabasto_%s.pdf", reabasto.getFolio()));
                status = Constantes.ACTIVO;
            }
        } catch (Exception e) {
            LOGGER.error("Error al procesar Reabasto AVG: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public void printOrdenReabasto(Reabasto reabasto, List<FolioAlternativoFolioMus> listFolioAlternativos) throws Exception {
        LOGGER.trace("SolicitudInsumoServicioMB.printOrdenReabasto(reabasto)");
        try {
            Estructura estruct = new Estructura();
            estruct.setIdEstructura(reabasto.getIdEstructura());
            estruct = estructuraService.obtenerEstructura(reabasto.getIdEstructura());
            if (estruct.getIdEntidadHospitalaria() == null) {
                estruct.setIdEntidadHospitalaria("");
            }

            EntidadHospitalaria enti = entidadHospitalariaService.obtenerEntidadById(estruct.getIdEntidadHospitalaria());
            if (enti == null) {
                enti = new EntidadHospitalaria();
                enti.setDomicilio("");
                enti.setNombre("");

            }
            reabasto.setDomicilio(enti.getDomicilio());
            reabasto.setNombreEntidad(enti.getNombre());

            byte[] buffer = reportesService.reporteSolicitudReabasto(
                    reabasto, listFolioAlternativos,
                    estruct.getIdTipoAlmacen(),
                    currentUser.getNombre() + ' ' + currentUser.getApellidoPaterno() + ' ' + currentUser.getApellidoMaterno(),
                    currentUser.getCedProfesional());
            if (buffer != null) {
                SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "sesionMB");
                sesion.setReportStream(buffer);
                sesion.setReportName(String.format("SolicitudOrdenReabasto_%s.pdf", reabasto.getFolio()));
                status = Constantes.ACTIVO;
            }
        } catch (Exception e) {
            LOGGER.error("Error al imprimir orden de  Reabasto: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public byte[] printOrdenReabastoServicioAll(Reabasto reabasto, boolean print) throws Exception {
        LOGGER.trace("SolicitudInsumoServicioMB.printOrdenReabasto(reabasto)");
        byte[] buffer = null;

        try {
            List<Reabasto> colectivos = obtenerColectivosGenerados(reabasto);
            Estructura estruct = new Estructura();
            estruct.setIdEstructura(reabasto.getIdEstructura());
            estruct = estructuraService.obtenerEstructura(reabasto.getIdEstructura());
            if (estruct.getIdEntidadHospitalaria() == null) {
                estruct.setIdEntidadHospitalaria("");
            }

            EntidadHospitalaria enti = entidadHospitalariaService.obtenerEntidadById(estruct.getIdEntidadHospitalaria());
            if (enti == null) {
                enti = new EntidadHospitalaria();
                enti.setDomicilio("");
                enti.setNombre("");

            }
            reabasto.setDomicilio(enti.getDomicilio());
            reabasto.setNombreEntidad(enti.getNombre());

            buffer = reportesService.reporteSolicitudColectivo(
                    reabasto, colectivos,
                    estruct.getIdTipoAlmacen(),
                    currentUser.getNombre() + ' ' + currentUser.getApellidoPaterno() + ' ' + currentUser.getApellidoMaterno(),
                    currentUser.getCedProfesional());

            if (buffer != null && print) {
                SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "sesionMB");
                sesion.setReportStream(buffer);
                sesion.setReportName(String.format("SolicitudOrdenReabasto_%s.pdf", reabasto.getFolio()));
                status = Constantes.ACTIVO;
            }
        } catch (Exception e) {
            LOGGER.error("Error al procesar Reabasto AVG. " + e.getMessage());
        }
        if (print) {
            PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
        }

        return buffer;
    }

    public void printOrdenReabastoServicioUnitario(FolioAlternativoFolioMusExtended colect) throws Exception {
        LOGGER.trace("SolicitudInsumoServicioMB.printOrdenReabasto(reabasto)");
        try {
            byte[] buffer = null;
            status = Constantes.INACTIVO;

            List<Reabasto> colectivos = new ArrayList<>();

            Reabasto reabasto = new Reabasto();
            reabasto.setIdReabasto(Comunes.getUUID());
            reabasto.setIdEstructura(colect.getIdEstructura());
            reabasto.setFolio(colect.getFolioAlternativo());// Se utiliza el campo ubicacion para pasar el folio siam                
            reabasto.setIdEstructuraPadre(reabastoSelect.getIdEstructura());
            reabasto.setIdTipoOrden(reabastoSelect.getIdTipoOrden());
            reabasto.setFechaSolicitud(reabastoSelect.getFechaSolicitud());
            reabasto.setIdUsuarioSolicitud(reabastoSelect.getIdUsuarioSolicitud());
            reabasto.setIdProveedor(reabastoSelect.getIdEstructura());
            reabasto.setInsertFecha(reabastoSelect.getInsertFecha());
            reabasto.setInsertIdUsuario(reabastoSelect.getInsertIdUsuario());
            reabasto.setAlmacen(colect.getServicio());

            colectivos.add(reabasto);

            Estructura estruct = new Estructura();
            estruct.setIdEstructura(reabastoSelect.getIdEstructura());
            estruct = estructuraService.obtenerEstructura(reabastoSelect.getIdEstructura());
            if (estruct.getIdEntidadHospitalaria() == null) {
                estruct.setIdEntidadHospitalaria("");
            }

            EntidadHospitalaria enti = entidadHospitalariaService.obtenerEntidadById(estruct.getIdEntidadHospitalaria());
            if (enti == null) {
                enti = new EntidadHospitalaria();
                enti.setDomicilio("");
                enti.setNombre("");

            }
            reabastoSelect.setDomicilio(enti.getDomicilio());
            reabastoSelect.setNombreEntidad(enti.getNombre());

            buffer = reportesService.reporteSolicitudColectivo(
                    reabastoSelect, colectivos,
                    estruct.getIdTipoAlmacen(),
                    currentUser.getNombre() + ' ' + currentUser.getApellidoPaterno() + ' ' + currentUser.getApellidoMaterno(),
                    currentUser.getCedProfesional());

            if (buffer != null) {
                SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "sesionMB");
                sesion.setReportStream(buffer);
                sesion.setReportName(String.format("SolicitudOrdenReabasto_%s.pdf", reabastoSelect.getFolio()));
                status = Constantes.ACTIVO;
                status = Constantes.ACTIVO;
            }
        } catch (Exception e) {
            LOGGER.error("Error al procesar Reabasto AVG. " + e.getMessage());
        }

        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);

    }

    public void buscarReabasto() {
        LOGGER.trace("Buscando coincidencias de: {}", cadenaBusqueda);
        try {
            if (cadenaBusqueda != null && cadenaBusqueda.trim().isEmpty()) {
                cadenaBusqueda = null;
            }
            String almacen = "";

            almacen = tipoAlmacen.isEmpty() ? estructuraList.get(0).getIdEstructura() : tipoAlmacen;
            Estructura temp = estructuraService.obtenerEstructura(almacen);
            campSiam = temp.getClaveEstructura().equals(Constantes.PARA_CLAVE_CEDIME);
            // Buscamos los almacenes y servicios que surte el almacen seleccionado
            if (colectivoXServicio) {
                almacenesServiList = obtenerAlmacenesServicios(almacen);
                serviciosList = estructuraService.obtenerAlmacenesServicioPCActivos();
                int x = -1;
                int y = -1;
                String idProveedor = "";
                for (Estructura es : serviciosList) {
                    if (es.getIdEstructura().equals(almacen)) {
                        x = serviciosList.indexOf(es);
                        idProveedor = es.getIdEstructuraPadre();
                        break;
                    }
                }
                serviciosList.remove(x);
                for (Estructura es : serviciosList) {
                    if (es.getIdEstructura().equals(idProveedor)) {
                        y = serviciosList.indexOf(es);
                        break;
                    }
                }
                serviciosList.remove(y);
            }

            solicitudReabastoLazy = new SolicitudReabastoLazy(reabastoService, cadenaBusqueda, almacen);

            LOGGER.debug("Resultados: {}", solicitudReabastoLazy.getTotalReg());

            cadenaBusqueda = null;
        } catch (Exception e) {
            LOGGER.error("Error al buscar Medicamentos: {}", e.getMessage());
        }
    }

    public void newOrdenReabasto() {
        try {
            if (permiso.isPuedeCrear()) {
                nuevaOrden = Constantes.ACTIVO;
                if (tipoOrden > 0) {
                    clean();
                    if (isAdmin) {
                        Estructura estruct = new Estructura();
                        estruct.setIdEstructura(tipoAlmacen);
                        estructuraSelect = estructuraService.obtener(estruct);
                        campSiam = estructuraSelect.getClaveEstructura().equals(Constantes.PARA_CLAVE_CEDIME);
                    }
                    reabastoSelect.setIdReabasto(Comunes.getUUID());
                    reabastoSelect.setIdEstructura(estructuraSelect.getIdEstructura());
                    reabastoSelect.setAlmacen(estructuraSelect.getNombre());
                    String idPadre = estructuraSelect.getIdEstructuraPadre();
                    reabastoSelect.setIdEstructuraPadre(idPadre);
                    reabastoSelect.setIdTipoOrden(tipoOrden);
                    switch (estructuraSelect.getIdTipoAlmacen()) {
                        case Constantes.ALMACEN_FARMACIA:
                            edit = Constantes.ACTIVO;
                            activo = Constantes.ACTIVO;
                            farmacia = Constantes.ACTIVO;
                            break;
                        case Constantes.ALMACEN:
                        case Constantes.SUBALMACEN:
                            if (idPadre != null) {
                                //Si no es Admin el Proveedor es el Padre                                
                                Estructura est = new Estructura();
                                est.setIdEstructura(idPadre);
                                String pdr = estructuraService.obtener(est).getNombre();
                                if (pdr != null) {
                                    reabastoSelect.setProveedor(pdr);
                                    reabastoSelect.setIdProveedor(idPadre);
                                }
                            }
                            farmacia = Constantes.INACTIVO;
                            edit = Constantes.INACTIVO;
                            activo = Constantes.INACTIVO;
                            break;
                        default:
                    }
                    reabastoSelect.setFechaSolicitud(new Date());
                    reabastoSelect.setIdUsuarioSolicitud(currentUser.getIdUsuario());
                    reabastoSelect.setInsertFecha(fechaActual);
                    reabastoSelect.setInsertIdUsuario(currentUser.getIdUsuario());

                    if (tipoOrden == Constantes.TIPO_ORDEN_NORMAL) {
                        if (colectivoXServicio && estructuraSelect.getClaveEstructura().equals(Constantes.PARA_CLAVE_CEDIME)) {
                            calculaColectivoXServicio(reabastoSelect);
                            showExpansion = true;
                        } else {
                            ordenAutomatica(estructuraSelect, estructuraSelect.getIdTipoAlmacen());
                            showExpansion = false;
                        }
                        destinoCole = false;
                    } else if (colectivoXServicio && estructuraSelect.getIdTipoAlmacen().equals(TipoAlmacen_Enum.ALMACEN.getValue())) {
                        destinoCole = true;
                    }
                    status = Constantes.ACTIVO;
                    messaje = Constantes.INACTIVO;
                    contEdit = Constantes.ACTIVO;
                    medicamento = null;
                } else {
                    status = Constantes.INACTIVO;
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al Obtener los datos: {}", ex.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public void addOrdenReabasto() {
        boolean sigue = true;
        boolean sigue2 = true;
        try {
            if (permiso.isPuedeCrear()) {
                if (medicamento != null && cantSolicitada > 0 && idServiceAdd != null) {
                    Medicamento medto = mediService.obtenerPorIdMedicamento(medicamento.getIdMedicamento());
                    Estructura est1 = estructuraService.obtenerEstructura(tipoAlmacen);
                    campSiam = est1.getClaveEstructura().equals(Constantes.PARA_CLAVE_CEDIME);
                    if (!existsMedtoEnLista(medto.getClaveInstitucional())) {
                        int cantSol = 0;
                        ReabastoInsumo datosPC = insumoService.obtenerMaxMinReorCantActual(est1.getIdEstructura(), medicamento.getIdMedicamento());

                        insumoSelect = new ReabastoInsumo();
                        insumoSelect.setIdReabastoInsumo(Comunes.getUUID());
                        insumoSelect.setIdInsumo(medto.getIdMedicamento());
                        insumoSelect.setClave(medto.getClaveInstitucional());
                        insumoSelect.setNombreComercial(medto.getNombreCorto());
                        insumoSelect.setPiezasCaja(medto.getFactorTransformacion());
                        insumoSelect.setMinimo(0);
                        insumoSelect.setMaximo(0);
                        insumoSelect.setReorden(0);
                        insumoSelect.setCantidadActual(0);
                        insumoSelect.setIdAlmacenPuntosControl(datosPC.getIdAlmacenPuntosControl());
                        insumoSelect.setSurtido(0);
                        insumoSelect.setDotacion(0);
                        
                        if(campSiam)
                        {                                                           
                            InsumoCTSiam insumoSiam = clientSiam.consultaExistensiaInsumoSIAM(urlConsultaInsumoEnCT,insumoSelect.getClave());
                            if(insumoSiam!=null){
                                insumoSelect.setCantidadPorClave((insumoSiam.getExistencia()-insumoSiam.getExistenciaApartados()) * insumoSelect.getPiezasCaja());
                            }else{
                                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("solicRebasto.err.insumoEliminadoSiam"), null);
                                insumoSelect.setCantidadPorClave(0);
                                sigue=false;
                            }                           
                        }
                        if(idServiceAdd.equals(Constantes.ALMACENES_TODOS)){                            
                            insumoSelect.setCantidadActual(0);
                            insumoSelect.setMinimo(0);
                            insumoSelect.setReorden(0);
                            insumoSelect.setMaximo(0);
                            insumoSelect.setDotacion(0);

                            for (Estructura estruc : serviciosItemList) {
                                if (!estruc.getNombre().equals(Constantes.ALMACENES_TODOS)) {
                                    ReabastoInsumo insumo = insumoService.obtenerListaInsumoByEstructura(estruc.getIdEstructura(), medicamento.getIdMedicamento(), "0").get(0);

                                    insumo.setCantidadSolicitada(redondeaCantidad(insumo.getPiezasCaja(), insumo.getCantidadSolicitada()));

                                    if (insumoSelect.getDetalleInsumo() == null) {
                                        insumos = new ArrayList<>();
                                        insumoSelect.setDetalleInsumo(insumos);
                                    }
                                    InsumoServicio ins = new InsumoServicio();
                                    ins.setIdEstructura(estruc.getIdEstructura());
                                    ins.setServicio(estruc.getNombre());
                                    ins.setIdInsumo(insumo.getIdInsumo());
                                    ins.setClave(insumo.getClave());
                                    ins.setMinimo(insumo.getMinimo());
                                    ins.setDotacion(insumo.getDotacion());
                                    ins.setReorden(insumo.getReorden());
                                    ins.setMaximo(insumo.getMaximo());
                                    ins.setCantidadActual(insumo.getCantidadActual());
                                    ins.setCantidadSolicitada(redondeaCantidad(insumo.getPiezasCaja(), cantSolicitada));

                                    insumoSelect.setMinimo(insumoSelect.getMinimo() + insumo.getMinimo());
                                    insumoSelect.setReorden(insumoSelect.getReorden() + insumo.getReorden());
                                    insumoSelect.setMaximo(insumoSelect.getMaximo() + insumo.getMaximo());
                                    insumoSelect.setDotacion(insumoSelect.getDotacion() + insumo.getDotacion());
                                    insumoSelect.setCantidadActual(insumoSelect.getCantidadActual() + insumo.getCantidadActual());
                                    //insumoSelect.setCantidadSolicitada(insumoSelect.getCantidadSolicitada()+insumo.getCantidadSolicitada());
                                    insumoSelect.getDetalleInsumo().add(ins);
                                    cantSol += redondeaCantidad(insumo.getPiezasCaja(), cantSolicitada);
                                }
                            }
                        } else {
                            Estructura estruc = estructuraService.obtenerEstructura(idServiceAdd);
                            ReabastoInsumo insumoServ = insumoService.obtenerListaInsumoByEstructura(estruc.getIdEstructura(),medicamento.getIdMedicamento(),"0").get(0);
                            if(insumoServ!=null){                               
                                InsumoServicio ins = new InsumoServicio();
                                ins.setIdEstructura(estruc.getIdEstructura());
                                ins.setServicio(estruc.getNombre());
                                ins.setIdInsumo(insumoServ.getIdInsumo());
                                ins.setClave(insumoServ.getClave());
                                ins.setMinimo(insumoServ.getMinimo());
                                ins.setDotacion(insumoServ.getDotacion());
                                ins.setReorden(insumoServ.getReorden());
                                ins.setMaximo(insumoServ.getMaximo());
                                ins.setCantidadActual(insumoServ.getCantidadActual());
                                cantSol = est1.getIdTipoAlmacen() < Constantes.SUBALMACEN ? redondeaCantidad(medto.getFactorTransformacion(), cantSolicitada): cantSolicitada;
                                ins.setCantidadSolicitada(cantSol);

                                
                                insumoSelect.setMinimo(insumoSelect.getMinimo()+ins.getMinimo());
                                insumoSelect.setReorden(insumoSelect.getReorden()+ins.getReorden());
                                insumoSelect.setMaximo(insumoSelect.getMaximo()+ins.getMaximo());
                                insumoSelect.setDotacion(insumoSelect.getDotacion()+ins.getDotacion());                                    
                                insumoSelect.setCantidadActual(insumoSelect.getCantidadActual()+ins.getCantidadActual());
                                
                                insumos = new ArrayList<>();
                                insumoSelect.setDetalleInsumo(insumos);
                                insumoSelect.getDetalleInsumo().add(ins);
                            }else{
                                Mensaje.showMessage(Constantes.MENSAJE_WARN, "El Insumo no esta dado de alta en el servicio", null);
                                sigue=false;
                            }
                        }

                        if (separaInsumos && estructuraSelect.getIdTipoAlmacen().equals(TipoAlmacen_Enum.ALMACEN.getValue()) && this.tipoInsumo == Constantes.MEDI) {
                            /* Se setea la clave del medicamento para despues poder generar los grupos de reabasto */
                            insumoSelect.setGrupo(insumoSelect.getClave());
                        }
                        PresentacionMedicamento pm;

                        if (est1.getIdTipoAlmacen() < Constantes.SUBALMACEN) {
                            pm = presentacionMedicamentoService.obtenerPorId(medto.getIdPresentacionEntrada());
                            int result = idServiceAdd.equals(Constantes.ALMACENES_TODOS) ? cantSol : redondeaCantidad(medto.getFactorTransformacion(), cantSolicitada);
                            insumoSelect.setCantidadSolicitada(result);
                        } else {
                            pm = presentacionMedicamentoService.obtenerPorId(medto.getIdPresentacionSalida());
                            insumoSelect.setCantidadSolicitada(cantSolicitada);
                        }
                        insumoSelect.setPresentacion(pm.getNombrePresentacion());

                        if (sigue && sigue2) {
                            insumoList.add(insumoSelect);
                            numeroRegistros = insumoList.size();

                            for (InsumoServicio itemServ : insumoSelect.getDetalleInsumo()) {
                                exits = false;
                                for (ReabastoXServicio servicio : reabastoXServicio) {
                                    if (itemServ.getIdEstructura().equals(servicio.getReabasto().getIdEstructura())) {
                                        ReabastoInsumo item = new ReabastoInsumo();
                                        item.setNombreComercial(insumoSelect.getNombreComercial());
                                        item.setCantidadPorClave(insumoSelect.getCantidadPorClave());
                                        item.setClave(insumoSelect.getClave());
                                        item.setCantidadActual(itemServ.getCantidadActual());
                                        item.setCantidadSolicitada(itemServ.getCantidadSolicitada());
                                        item.setIdInsumo(itemServ.getIdInsumo());
                                        item.setMinimo(itemServ.getMinimo());
                                        item.setReorden(itemServ.getReorden());
                                        item.setMaximo(itemServ.getMaximo());
                                        item.setDotacion(itemServ.getDotacion());

                                        servicio.getInsumos().add(item);
                                        exits = true;
                                        break;
                                    }
                                }
                                if (!exits) {
                                    reabastoXServicio.add(newReabastoXServ(itemServ.getIdEstructura(), itemServ.getIdInsumo(), itemServ.getCantidadSolicitada()));
                                }
                            }
                        }
                    } else if (!idServiceAdd.equals(Constantes.ALMACENES_TODOS)) {
                        exits = false;
                        idEstructura = est1.getClaveEstructura().equals(Constantes.PARA_CLAVE_CEDIME) ? idServiceAdd : idEstructura;
                        insumoList.forEach(item -> {
                            if (item.getIdInsumo().equals(medto.getIdMedicamento())) {
                                item.getDetalleInsumo().forEach(serv -> {
                                    if (serv.getIdEstructura().equals(idEstructura)) {
                                        exits = true;
                                    }
                                });
                            }
                        });

                        if (!exits) {
                            Estructura estruc = serviciosItemList.stream()
                                    .filter(item -> item.getIdEstructura().equals(idServiceAdd))
                                    .findAny()
                                    .orElse(null);
                            ReabastoInsumo insumo = insumoService.obtenerListaInsumoByEstructura(idEstructura, medicamento.getIdMedicamento(), "0").get(0);
                            insumo.setCantidadSolicitada(redondeaCantidad(insumo.getPiezasCaja(), insumo.getCantidadSolicitada()));

                            insumoList.forEach(item -> {
                                if (item.getIdInsumo().equals(medicamento.getIdMedicamento())) {
                                    InsumoServicio ins = new InsumoServicio();
                                    ins.setIdEstructura(estruc.getIdEstructura());
                                    ins.setServicio(estruc.getNombre());
                                    ins.setIdInsumo(insumo.getIdInsumo());
                                    ins.setClave(insumo.getClave());
                                    ins.setDotacion(insumo.getDotacion());
                                    ins.setMinimo(insumo.getMinimo());
                                    ins.setReorden(insumo.getReorden());
                                    ins.setMaximo(insumo.getMaximo());
                                    ins.setCantidadActual(insumo.getCantidadActual());
                                    ins.setCantidadSolicitada(redondeaCantidad(insumo.getPiezasCaja(), cantSolicitada));

                                    item.setMinimo(item.getMinimo() + insumo.getMinimo());
                                    item.setReorden(item.getReorden() + insumo.getReorden());
                                    item.setMaximo(item.getMaximo() + insumo.getMaximo());
                                    item.setDotacion(item.getDotacion() + insumo.getDotacion());
                                    item.setCantidadActual(item.getCantidadActual() + insumo.getCantidadActual());
                                    item.setCantidadSolicitada(item.getCantidadSolicitada() + ins.getCantidadSolicitada());
                                    item.getDetalleInsumo().add(ins);
                                }
                            });
                            exits = false;
                            for (ReabastoXServicio servicio : reabastoXServicio) {
                                if (estruc.getIdEstructura().equals(servicio.getReabasto().getIdEstructura())) {
                                    ReabastoInsumo item = new ReabastoInsumo();
                                    item.setNombreComercial(insumoSelect.getNombreComercial());
                                    item.setCantidadPorClave(insumoSelect.getCantidadPorClave());
                                    item.setClave(insumoSelect.getClave());
                                    item.setCantidadActual(insumo.getCantidadActual());
                                    item.setCantidadSolicitada(insumo.getCantidadSolicitada());
                                    item.setIdInsumo(insumo.getIdInsumo());
                                    item.setMinimo(insumo.getMinimo());
                                    item.setReorden(insumo.getReorden());
                                    item.setMaximo(insumo.getMaximo());
                                    item.setDotacion(insumo.getDotacion());

                                    servicio.getInsumos().add(item);
                                    exits = true;
                                    break;
                                }
                            }
                            if (!exits) {
                                reabastoXServicio.add(newReabastoXServ(idEstructura, insumo.getIdInsumo(), redondeaCantidad(insumo.getPiezasCaja(), cantSolicitada)));
                            }
                        } else {
                            Mensaje.showMessage(Constantes.MENSAJE_WARN, "El medicamento y servicio ya existe", null);
                        }

                    }else{
                        for(Estructura servicio: serviciosItemList){
                            if(!servicio.getNombre().equals(Constantes.ALMACENES_TODOS)){
                                exits = false;
                                insumoList.forEach(item->{
                                    if(item.getIdInsumo().equals(medto.getIdMedicamento())){
                                        item.getDetalleInsumo().forEach(serv->{
                                            if(serv.getIdEstructura().equals(servicio.getIdEstructura()))
                                                exits=true;
                                        });
                                    }
                                });
                                
                                ReabastoInsumo insumo = insumoService.obtenerListaInsumoByEstructura(servicio.getIdEstructura(),medicamento.getIdMedicamento(),"0").get(0);
                                insumo.setCantidadSolicitada(redondeaCantidad(insumo.getPiezasCaja(), insumo.getCantidadSolicitada()));

                                if(!exits){
                                    insumoList.forEach(item->{
                                        if(item.getIdInsumo().equals(medicamento.getIdMedicamento())){
                                            InsumoServicio ins = new InsumoServicio();
                                            ins.setIdEstructura(servicio.getIdEstructura());
                                            ins.setServicio(servicio.getNombre());
                                            ins.setIdInsumo(insumo.getIdInsumo());
                                            ins.setClave(insumo.getClave());
                                            ins.setDotacion(insumo.getDotacion());
                                            ins.setMinimo(insumo.getMinimo());
                                            ins.setReorden(insumo.getReorden());
                                            ins.setMaximo(insumo.getMaximo());
                                            ins.setCantidadActual(insumo.getCantidadActual());
                                            ins.setCantidadSolicitada(redondeaCantidad(insumo.getPiezasCaja(), cantSolicitada));

                                            item.setMinimo(item.getMinimo()+insumo.getMinimo());
                                            item.setReorden(item.getReorden()+insumo.getReorden());
                                            item.setMaximo(item.getMaximo()+insumo.getMaximo());
                                            item.setDotacion(item.getDotacion()+insumo.getDotacion());
                                            item.setCantidadActual(item.getCantidadActual()+insumo.getCantidadActual());
                                            item.setCantidadSolicitada(item.getCantidadSolicitada()+ins.getCantidadSolicitada());
                                            item.getDetalleInsumo().add(ins);
                                        }
                                    });                            
                                    exits=false;
                                    for (ReabastoXServicio servi : reabastoXServicio) {
                                        if(servicio.getIdEstructura().equals(servi.getReabasto().getIdEstructura()))                                        
                                        {
                                            ReabastoInsumo item = new ReabastoInsumo();
                                            item.setNombreComercial(insumoSelect.getNombreComercial());
                                            item.setCantidadPorClave(insumoSelect.getCantidadPorClave());
                                            item.setClave(insumoSelect.getClave());
                                            item.setCantidadActual(insumo.getCantidadActual());
                                            item.setCantidadSolicitada(insumo.getCantidadSolicitada());
                                            item.setIdInsumo(insumo.getIdInsumo());
                                            item.setMinimo(insumo.getMinimo());
                                            item.setReorden(insumo.getReorden());
                                            item.setMaximo(insumo.getMaximo());
                                            item.setDotacion(insumo.getDotacion());

                                            servi.getInsumos().add(item);
                                            exits=true;
                                            break;
                                        }
                                    }
                                    if(!exits)
                                        reabastoXServicio.add(newReabastoXServ(servicio.getIdEstructura(), insumo.getIdInsumo(), redondeaCantidad(insumo.getPiezasCaja(), cantSolicitada)));
                                }else{  
                                    insumoList.forEach(item->{
                                        if(item.getIdInsumo().equals(medicamento.getIdMedicamento())){
                                            suma=0;
                                            item.getDetalleInsumo().forEach(ite -> {
                                                if(ite.getIdInsumo().equals(insumo.getIdInsumo())){
                                                    ite.setCantidadSolicitada(redondeaCantidad(insumo.getPiezasCaja(), cantSolicitada));
                                                    suma += ite.getCantidadSolicitada();
                                                }
                                            });
                                            item.setCantidadSolicitada(suma);
                                        }
                                    });        
                                    for (ReabastoXServicio servi : reabastoXServicio) {
                                        if(servicio.getIdEstructura().equals(servi.getReabasto().getIdEstructura()))                                        
                                        {
                                            servi.getInsumos().forEach(a -> {
                                                if(a.getIdInsumo().equals(insumo.getIdInsumo())){
                                                    a.setCantidadSolicitada(redondeaCantidad(insumo.getPiezasCaja(), cantSolicitada));
                                                }
                                            });
                                        }
                                    }
                                }
                            }
                        }
                    }
                    showExpansion = Constantes.ACTIVO;
                    cantSolicitada = 1;
                    medicamento = null;
                    serviciosItemList = null;
                    idServiceAdd = null;
                } else if (medicamento == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("solicRebasto.err.requerInsumo"), null);
                } else if (idServiceAdd == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Seleccione un servicio", null);
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("solicRebasto.err.cantidadRequerida"), null);
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al Agregar Insumo: {}", ex.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, sigue);
    }

    public void addOrdenReabastoExt() {
        boolean sigue = true;
        try {
            if (permiso.isPuedeCrear()) {
                campSiam = estructuraSelect.getClaveEstructura().equals(Constantes.PARA_CLAVE_CEDIME);
                if (medicamento != null && cantSolicitada > 0) {
                    Medicamento medto = mediService.obtenerPorIdMedicamento(medicamento.getIdMedicamento());
                    idEstructura = reabastoSelect.getDestino();
                    ReabastoInsumo datosPC = insumoService.obtenerMaxMinReorCantActual(idEstructura, medicamento.getIdMedicamento());
                    if(datosPC!=null){
                        if (!existsMedtoEnLista(medto.getClaveInstitucional())) {
                            
                            insumoSelect = new ReabastoInsumo();
                            insumoSelect.setIdReabastoInsumo(Comunes.getUUID());
                            insumoSelect.setIdInsumo(medto.getIdMedicamento());
                            insumoSelect.setClave(medto.getClaveInstitucional());
                            insumoSelect.setNombreComercial(medto.getNombreCorto());
                            insumoSelect.setPiezasCaja(medto.getFactorTransformacion());
                            insumoSelect.setMinimo(datosPC.getMinimo());
                            insumoSelect.setMaximo(datosPC.getMaximo());
                            insumoSelect.setReorden(datosPC.getReorden());
                            insumoSelect.setCantidadActual(datosPC.getCantidadActual());
                            insumoSelect.setIdAlmacenPuntosControl(datosPC.getIdAlmacenPuntosControl());
                            insumoSelect.setSurtido(datosPC.getSurtido());
                            insumoSelect.setDotacion(datosPC.getDotacion());                        
                            PresentacionMedicamento pm;
                            Estructura est1 = estructuraService.obtenerEstructura(tipoAlmacen);

                            if (est1.getIdTipoAlmacen() < Constantes.SUBALMACEN) {
                                pm = presentacionMedicamentoService.obtenerPorId(medto.getIdPresentacionEntrada());
                                int result = redondeaCantidad(medto.getFactorTransformacion(), cantSolicitada);
                                insumoSelect.setCantidadSolicitada(result);
                            } else {
                                pm = presentacionMedicamentoService.obtenerPorId(medto.getIdPresentacionSalida());
                                insumoSelect.setCantidadSolicitada(cantSolicitada);
                            }
                            insumoSelect.setPresentacion(pm.getNombrePresentacion());

                            if(campSiam)
                            {                                                           
                                InsumoCTSiam insumoSiam = clientSiam.consultaExistensiaInsumoSIAM(urlConsultaInsumoEnCT,insumoSelect.getClave());
                                if(insumoSiam!=null){
                                    insumoSelect.setCantidadPorClave((insumoSiam.getExistencia()-insumoSiam.getExistenciaApartados()) * insumoSelect.getPiezasCaja());
                                }else{
                                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("solicRebasto.err.insumoEliminadoSiam"), null);
                                    insumoSelect.setCantidadPorClave(0);
                                    sigue=false;
                                }                           
                            }

                            if (sigue) {
                                showExpansion = Constantes.INACTIVO;
                                insumoList.add(insumoSelect);
                                numeroRegistros = insumoList.size();
                            }
                        } else {
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("solicRebasto.err.existeMedicamento"), null);
                            PrimeFaces.current().executeScript("jQuery('td.ui-editable-column').eq(" + activeRow + ").each(function(){jQuery(this).click()});");
                        }
                    }else{
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("solicRebasto.err.medicamentoNoExiste"), null);
                    }
                    cantSolicitada = 1;
                    medicamento = null;
                } else if (medicamento == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("solicRebasto.err.requerInsumo"), null);
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("solicRebasto.err.cantidadRequerida"), null);
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al Agregar Insumo: {}", ex.getMessage());
        }
    }

    public void deleteReabasto(String idReabasto) {
        try {
            if (permiso.isPuedeEliminar()) {
                Reabasto reabas = reabastoService.obtenerReabastoPorID(idReabasto);
                if (reabas != null) {
                    AlmacenControl almacenControl = new AlmacenControl();
                    almacenControl.setIdAlmacen(tipoAlmacen);
                    almacenControl.setSolicitud(Constantes.ES_INACTIVO);
                    reabastoService.eliminarPorId(reabas.getIdReabasto(), reabas.getFolio(), almacenControl);
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al Eliminar el Insumo: {}", ex.getMessage());
        }
    }

    public void deleteInsumoOfList(String idInsumo) {
        int index = -1;
        ReabastoInsumo insumo= null; 
        try {
            for (ReabastoInsumo aux : insumoList) {
                if (aux.getIdInsumo().equals(idInsumo)) {
                    insumoService.eliminar(aux);
                    index = insumoList.indexOf(aux);
                    insumo = aux;
                    break;
                }
            }
            if (index >= 0) {
                insumoList.remove(index);
            }
            numeroRegistros = insumoList.size();
            if (colectivoXServicio) {
                oldValue = -1;
                reabastoXServicio.stream().forEach((reab) -> {
                    int idx = -1;
                    for(ReabastoInsumo insu: reab.getInsumos()){
                        if(insu.getIdInsumo().equals(idInsumo)){
                            idx= reab.getInsumos().indexOf(insu);
                            break;
                        }
                    }
                    if (idx>=0) {                        
                        reab.getInsumos().remove(idx);                        
                        oldValue = reab.getInsumos().isEmpty() ? reabastoXServicio.indexOf(reab) : -1;
                        try {
                            String idFolioalternativo = detalleInsumoSiamService.eliminaInsumo(reab.getReabasto(),idInsumo);
                            if(oldValue>=0 && !idFolioalternativo.equals("")){
                                folioAlternativoFolioMusService.eliminarIdFolio(idFolioalternativo);
                            }else if(!reab.getReabasto().getFolio().equals("")){
                                
                            }
                            
                        } catch (Exception e) {
                            LOGGER.error("Error al eliminar el Insumo de la lista: {}", e.getMessage());
                        }
                    }
                });
                if(oldValue>=0){
                    reabastoXServicio.remove(oldValue);
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al eliminar el Insumo de la lista: {}", ex.getMessage());
        }
    }

    public void deleteServiceOfList(String idItem, String idServicio) {
        try {
            int idx = -1;
            int solicitada = 0;
            int minimo = 0;
            int reorden = 0;
            int maximo = 0;
            int dotacion = 0;
            int actual = 0;
            for (ReabastoInsumo item : insumoList) {
                if (item.getIdInsumo().equals(idItem)) {
                    for (InsumoServicio service : item.getDetalleInsumo()) {
                        if (service.getIdEstructura().equals(idServicio)) {
                            idx = item.getDetalleInsumo().indexOf(service);
                            solicitada = service.getCantidadSolicitada();
                            minimo = service.getMinimo();
                            reorden = service.getReorden();
                            maximo = service.getMaximo();
                            dotacion = service.getDotacion();
                            actual = service.getCantidadActual();
                            break;
                        }
                    }
                    if (idx >= 0) {
                        item.setMinimo(item.getMinimo() - minimo);
                        item.setReorden(item.getReorden() - reorden);
                        item.setMaximo(item.getMaximo() - maximo);
                        item.setDotacion(item.getDotacion() - dotacion);
                        item.setCantidadActual(item.getCantidadActual() - actual);
                        item.setCantidadSolicitada(redondeaCantidad(item.getPiezasCaja(), item.getCantidadSolicitada() - solicitada));
                        item.getDetalleInsumo().remove(idx);
                        idx = item.getDetalleInsumo().isEmpty() ? insumoList.indexOf(item) : -1;
                    }
                    break;
                }
            }
            if (idx >= 0) {
                insumoList.remove(idx);
            }
            //Eliminar el insumo del servicio eliminado
            if (colectivoXServicio) {
                for (ReabastoXServicio cole : reabastoXServicio) {
                    if (cole.getReabasto().getIdEstructura().equals(idServicio)) {
                        idx = -1;
                        for (ReabastoInsumo insu : cole.getInsumos()) {
                            if (insu.getIdInsumo().equals(idItem)) {
                                idx = cole.getInsumos().indexOf(insu);
                                break;
                            }
                        }
                        if (idx >= 0) {
                            cole.getInsumos().remove(idx);
                            idx = cole.getInsumos().isEmpty() ? reabastoXServicio.indexOf(cole) : -1;
                        }
                        break;
                    }
                }
                if (idx >= 0) {
                    reabastoXServicio.remove(idx);
                }
            }

        } catch (Exception ex) {
            LOGGER.error("Error al eliminar el servicio del insumo: {}", ex.getMessage());
        }
    }

    public void cancelReabastoOfList(String idReabasto) {
        try {
            if (permiso.isPuedeEditar()) {
                Estructura estruct = new Estructura();
                Reabasto reabasto = reabastoService.obtenerReabastoPorID(idReabasto);
                estruct.setIdEstructura(reabasto.getIdEstructura());
                estructuraSelect = estructuraService.obtener(estruct);
                
                campSiam = estructuraSelect.getClaveEstructura().equals(Constantes.PARA_CLAVE_CEDIME);
                insumoList = insumoService.obtenerReabastosInsumos(reabasto.getIdReabasto(), estructuraSelect.getIdTipoAlmacen());

                reabasto.setIdReabasto(idReabasto);
                reabasto.setIdEstatusReabasto(EstatusReabasto_Enum.CANCELADA.getValue());
                reabasto.setUpdateFecha(new Date());
                reabasto.setUpdateIdUsuario(currentUser.getIdUsuario());

                for (ReabastoInsumo ri : insumoList) {
                    ri.setIdReabasto(idReabasto);
                    ri.setIdEstatusReabasto(EstatusReabasto_Enum.CANCELADA.getValue());
                    ri.setUpdateFecha(new Date());
                    ri.setUpdateIdUsuario(reabasto.getUpdateIdUsuario());
                }

                reabastoService.cancelarSolicitud(reabasto, insumoList, estructuraSelect.getIdTipoAlmacen());

            }
        } catch (Exception ex) {
            LOGGER.error("Error al cancelar el Reabasto: {}", ex.getMessage());
        }
    }

    public void sendOrdenReabasto(int opc) {
        try {
            int estatusReabasto;
            String nameEstatus = "";
            List<ReabastoInsumo> listaFiltrada = new ArrayList<>();
            for (short i = 0; i < this.insumoList.size(); i++) {
                ReabastoInsumo item = this.insumoList.get(i);
                if (item.getCantidadSolicitada() != 0) {
                    item.setIdReabasto(reabastoSelect.getIdReabasto());
                    listaFiltrada.add(item);
                }
            }

            if (opc == 1) {
                estatusReabasto = EstatusReabasto_Enum.SOLICITADA.getValue();
                nameEstatus = EstatusReabasto_Enum.SOLICITADA.name();
            } else {
                estatusReabasto = EstatusReabasto_Enum.REGISTRADA.getValue();
                nameEstatus = EstatusReabasto_Enum.REGISTRADA.name();
            }
            if (permiso.isPuedeCrear()) {
                if (reabastoSelect != null && insumoList != null) {
                    reabastoSelect.setIdEstatusReabasto(estatusReabasto);
                    reabastoSelect.setEstatus(ucFirst(nameEstatus));
                    reabastoSelect.setSolicitante(currentUser.getNombre() + " " + currentUser.getApellidoPaterno() + " " + currentUser.getApellidoMaterno());
                    reabastoSelect.setIdTipoOrigen(TipoOrigen_Enum.ADMINISTRACION.getValue());
                    //Si es TRUE es ALMACEN FARMACIA
                    if (farmacia) {
                        Proveedor proveedor = new Proveedor();
                        proveedor.setIdProveedor(reabastoSelect.getIdProveedor());
                        proveedorSelect = provService.obtener(proveedor);

                        reabastoSelect.setProveedor(proveedorSelect.getNombreProveedor());
                        reabastoSelect.setIdProveedor(proveedorSelect.getIdProveedor());
                    }

                    if (existeReabasto(reabastoSelect.getIdReabasto())) {
                        reabastoSelect.setUpdateFecha(new Date());
                        reabastoSelect.setUpdateIdUsuario(currentUser.getIdUsuario());
                        //llenamos los campos   insertFecha, insertUsuario, estatus, etc. por si hay nuevos insumos
                        for (ReabastoInsumo aux : listaFiltrada) {
                            aux.setInsertFecha(new Date());
                            aux.setInsertIdUsuario(currentUser.getIdUsuario());
                        }
                        // agregamos los campos updateFecha, updateUsuario, estatus, etc.                        
                        listaFiltrada = updateInsumoMasivo(listaFiltrada, estatusReabasto);

                        reabastoService.actualizarSolicitud(reabastoSelect, listaFiltrada);
                    } else {
                        reabastoSelect.setInsertFecha(new Date());
                        reabastoSelect.setInsertIdUsuario(currentUser.getIdUsuario());
                        // agregamos los campos insertFecha, insertUsuario, estatus, etc.
                        listaFiltrada = insertInsumoMasivo(listaFiltrada, estatusReabasto);

                        //Validar si se encuentra activo parametro para separar medicamentos por grupo
                        if (separaInsumos && estructuraSelect.getIdTipoAlmacen().equals(TipoAlmacen_Enum.ALMACEN.getValue()) && tipoInsumo == Constantes.MEDI && !colectivoXServicio) {
                            boolean grupoEspecial = true;
                            boolean grupoUno = true;
                            boolean grupoDos = true;
                            boolean grupoTres = true;
                            boolean grupoCuatro = true;
                            boolean grupoCinco = true;
                            crearReabastosPorGrupo(listaFiltrada, grupoEspecial, grupoUno, grupoDos, grupoTres, grupoCuatro, grupoCinco);
                            reabastoService.insertarSolicitud(listFolioAlternativos, reabastoSelect, listaFiltrada, idAlmacen, separaInsumos);
                        } else {
                            reabastoService.insertarSolicitud(reabastoSelect, listaFiltrada, estructuraSelect.getIdTipoAlmacen(), colectivoXServicio);
                        }
                    }
                    if (urlSIAM && estatusReabasto == EstatusReabasto_Enum.SOLICITADA.getValue()) {
                        if (tipoOrden == Constantes.TIPO_ORDEN_NORMAL) {
                            // Filtrar la lista para eliminar los reabastos que estan en ceros, despues enviarlos a SIAM
                            List<ReabastoXServicio> filter = new ArrayList<>();
                            for (short i = 0; i < this.reabastoXServicio.size(); i++) {
                                ReabastoXServicio servicio = this.reabastoXServicio.get(i);
                                for (ReabastoInsumo item : servicio.getInsumos()) {
                                    if (item.getCantidadSolicitada() > 0) {
                                        filter.add(servicio);
                                        break;
                                    }
                                }
                            }
                            estatuReabastoMus = EstatusReabasto_Enum.REGISTRADA; 
                            reabastoSelect.setIdEstatusReabasto(estatuReabastoMus.getValue());
                            listaFiltrada = updateInsumoMasivo(listaFiltrada,estatuReabastoMus.getValue());
                            reabastoService.actualizarSolicitud(reabastoSelect, listaFiltrada);                           
                            // Recorrer la lista filtrada para enviarlo a SIAM
                            filter.forEach((service) -> {
                                int idx = filter.indexOf(service);                                
                                enviarColectivoSIAM(reabastoSelect, service.getReabasto().getIdEstructura(), service.getInsumos(),idx);
                            });

                        } else if (enviarColectivoSIAM(reabastoSelect, reabastoSelect.getDestino(), listaFiltrada,0) == null) {
                            reabastoSelect.setIdEstatusReabasto(EstatusReabasto_Enum.REGISTRADA.getValue());
                            listaFiltrada = updateInsumoMasivo(listaFiltrada, EstatusReabasto_Enum.REGISTRADA.getValue());
                            reabastoService.actualizarSolicitud(reabastoSelect, listaFiltrada);
                            printSolicitud = false;
                        }
                    }
                }
                if (printSolicitud) {
                    if (colectivoXServicio) {
                        byte[] buffer = printOrdenReabastoServicioAll(reabastoSelect, true);
                        if (buffer != null) {
                            printSolicitud = true;
                        } else {
                            printSolicitud = false;
                        }
                    } else if (separaInsumos && estructuraSelect.getIdTipoAlmacen().equals(TipoAlmacen_Enum.ALMACEN.getValue()) && tipoInsumo == Constantes.MEDI) {
                        printOrdenReabasto(reabastoSelect, listFolioAlternativos);
                    } else {
                        printOrdenReabasto(reabastoSelect);
                    }
                }
                messaje = Constantes.ACTIVO;
            }
        } catch (Exception ex) {
            LOGGER.error("Error al Enviar el reabasto.", ex);
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, printSolicitud);
    }

    private void crearReabastosPorGrupo(List<ReabastoInsumo> listaFiltrada, boolean grupoEspecial, boolean grupoUno, boolean grupoDos, boolean grupoTres,
            boolean grupoCuatro, boolean grupoCinco) {

        for (ReabastoInsumo insumo : listaFiltrada) {
            if (("1").equals(insumo.getGrupo().substring(1, 2)) && ("5").equals(insumo.getGrupo().substring(8, 9))) {
                if (grupoEspecial) {
                    idGrupoEspecial = creaFolioAlternativo();
                }
                insumo.setIdFolioAlternativo(idGrupoEspecial);
                grupoEspecial = false;
            } else {
                if (("1").equals(insumo.getGrupo().substring(1, 2))) {
                    if (grupoUno) {
                        idGrupoUno = creaFolioAlternativo();
                    }
                    insumo.setIdFolioAlternativo(idGrupoUno);
                    grupoUno = false;
                } else {
                    if (("2").equals(insumo.getGrupo().substring(1, 2))) {
                        if (grupoDos) {
                            idGrupoDos = creaFolioAlternativo();
                        }
                        insumo.setIdFolioAlternativo(idGrupoDos);
                        grupoDos = false;
                    } else {
                        if (("3").equals(insumo.getGrupo().substring(1, 2))) {
                            if (grupoTres) {
                                idGrupoTres = creaFolioAlternativo();
                            }
                            insumo.setIdFolioAlternativo(idGrupoTres);
                            grupoTres = false;
                        } else {
                            if (("4").equals(insumo.getGrupo().substring(1, 2))) {
                                if (grupoCuatro) {
                                    idGrupoCuatro = creaFolioAlternativo();
                                }
                                insumo.setIdFolioAlternativo(idGrupoCuatro);
                                grupoCuatro = false;
                            } else {
                                if (("5").equals(insumo.getGrupo().substring(1, 2))) {
                                    if (grupoCinco) {
                                        idGrupoCinco = creaFolioAlternativo();
                                    }
                                    insumo.setIdFolioAlternativo(idGrupoCinco);
                                    grupoCinco = false;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /*Metodo utilizado para generar folios Alternativos
        asociados a un folio mus
     */
    private String creaFolioAlternativo() {
        String idFolioAlternativo = Comunes.getUUID();
        FolioAlternativoFolioMus folioAlternativo = new FolioAlternativoFolioMus();
        folioAlternativo.setIdFolioAlternativo(idFolioAlternativo);
        folioAlternativo.setInsertFecha(new Date());
        folioAlternativo.setInsertIdUsuario(currentUser.getIdUsuario());
        folioAlternativo.setEstatus("Enviada");

        this.listFolioAlternativos.add(folioAlternativo);

        return idFolioAlternativo;
    }

    public void obtenerDetalleInsumos() {
        LOGGER.trace("mx.mc.magedbean.SolicitudInsumoServicioMB.obtenerReabastoInsumo()");  
        try {
            if (permiso.isPuedeVer()) {
                nuevaOrden = Constantes.INACTIVO;
                mostrarFolioAlternativo = Constantes.INACTIVO;
                Estructura estruct = new Estructura();
                estruct.setIdEstructura(reabastoSelect.getIdEstructura());
                estructuraSelect = estructuraService.obtener(estruct);
                campSiam = estructuraSelect.getClaveEstructura().equals(Constantes.PARA_CLAVE_CEDIME);
                showExpansion = reabastoSelect.getIdTipoOrden()==1;                
                destinoCole = reabastoSelect.getIdTipoOrden()==2;
                contEdit = Constantes.INACTIVO;
                activo = Constantes.INACTIVO;
                switch (estructuraSelect.getIdTipoAlmacen()) {
                    case Constantes.ALMACEN_FARMACIA:
                        Proveedor proveedor = new Proveedor();
                        proveedor.setIdProveedor(reabastoSelect.getIdProveedor());
                        proveedorSelect = provService.obtener(proveedor);

                        reabastoSelect.setProveedor(proveedorSelect.getNombreProveedor());
                        reabastoSelect.setIdProveedor(proveedorSelect.getIdProveedor());
                        if (reabastoSelect.getIdEstatusReabasto().equals(EstatusReabasto_Enum.REGISTRADA.getValue())) {
                            activo = Constantes.ACTIVO;
                            contEdit = Constantes.ACTIVO;
                        }
                        break;
                    case Constantes.ALMACEN:
                    case Constantes.SUBALMACEN:
                        //Si no es Admin el Proveedor es el Padre                                
                        Estructura est = new Estructura();
                        est.setIdEstructura(reabastoSelect.getIdEstructuraPadre());
                        String pdr = estructuraService.obtener(est).getNombre();
                        activo = Constantes.INACTIVO;
                        if (pdr != null) {
                            reabastoSelect.setProveedor(pdr);
                            reabastoSelect.setIdProveedor(reabastoSelect.getIdEstructuraPadre());
                        }
                        if (reabastoSelect.getIdEstatusReabasto().equals(EstatusReabasto_Enum.REGISTRADA.getValue())) {
                            contEdit = Constantes.ACTIVO;
                        }
                        break;
                    default:
                }

                if(campSiam){
                    insumoSiamList = clientSiam.consultaListaExistensiaSIAM(urlConsultaInsumoEnCT);
                }
                insumoList = insumoService.obtenerInsumosdeReabastoporIdReabasto(reabastoSelect.getIdReabasto(), estructuraSelect.getIdEstructura(), estructuraSelect.getIdTipoAlmacen());

                if (!insumoList.isEmpty()) {
                    if (estructuraSelect.getIdTipoAlmacen().equals(TipoAlmacen_Enum.ALMACEN.getValue())) {
                        insumoList.forEach(ite -> {
                            ite.setMinimo(0);
                            ite.setReorden(0);
                            ite.setMaximo(0);
                            ite.setCantidadActual(0);
                            ite.setCantidadSolicitada(0);
                        });
                        List<DetalleInsumoSiam> insumosServ = detalleInsumoSiamService.obtenerDetalleXFolioEstructura(reabastoSelect.getFolio(), null, null);

                        for(DetalleInsumoSiam detalle: insumosServ){                            
                            reabastoSelect.setDestino(reabastoSelect.getIdTipoOrden()==2 ? detalle.getIdEstructura():"");
                            List<ReabastoInsumo> lista= insumoService.obtenerListaInsumoByEstructura(detalle.getIdEstructura(),detalle.getIdInsumo(),null);                            
                            if(lista.size()>0){
                                ReabastoInsumo insumo = lista.get(0);

                                insumoList.forEach(ite -> {
                                    if (ite.getIdInsumo().equals(detalle.getIdInsumo())) {
                                        if(ite.getDetalleInsumo()==null){
                                            insumos = new ArrayList<>();
                                            ite.setDetalleInsumo(insumos);
                                        }
                                        ite.setMinimo(ite.getMinimo()+insumo.getMinimo());
                                        ite.setReorden(ite.getReorden()+insumo.getReorden());
                                        ite.setMaximo(ite.getMaximo()+insumo.getMaximo());
                                        ite.setDotacion(ite.getDotacion()+insumo.getDotacion());
                                        ite.setCantidadActual(ite.getCantidadActual()+insumo.getCantidadActual());
                                        ite.setCantidadSolicitada(ite.getCantidadSolicitada()+(detalle.getCantidadSolicitada()* insumo.getPiezasCaja()));

                                        InsumoServicio ins = new InsumoServicio();
                                        ins.setIdEstructura(detalle.getIdEstructura());
                                        ins.setServicio(insumo.getAlmacenServicio());
                                        ins.setIdInsumo(detalle.getIdInsumo());
                                        ins.setClave(insumo.getClave());
                                        ins.setMinimo(insumo.getMinimo());
                                        ins.setReorden(insumo.getReorden());
                                        ins.setMaximo(insumo.getMaximo());
                                        ins.setDotacion( insumo.getDotacion());
                                        ins.setCantidadActual(insumo.getCantidadActual());
                                        ins.setCantidadSolicitada(detalle.getCantidadSolicitada()* insumo.getPiezasCaja());

                                        ite.getDetalleInsumo().add(ins);
                                    }
                                });  
                           
                            }
                        }
                    }
                    
                    if(!insumoSiamList.isEmpty())
                    {
                        insumoList.forEach(item ->{
                            InsumoCTSiam insumoSiam = insumoSiamList.stream().filter(f-> f.getCveInsumo().equals(item.getClave())).findAny().orElse(null);
                            if(insumoSiam!=null){
                                item.setCantidadPorClave((insumoSiam.getExistencia()-insumoSiam.getExistenciaApartados()) * item.getPiezasCaja());
                            }else{
                                item.setCantidadPorClave(0);
                            }
                            
                        });
                    }
                    obtenerReabastoXServicioDetalleSiam(reabastoSelect);
                    
                    Medicamento medicam = mediService.obtenerPorIdMedicamento(insumoList.get(0).getIdInsumo());
                    tipoInsumo = medicam.getTipo();
                    tipoOrden = reabastoSelect.getIdTipoOrden();
                }

                if (insumoListCopy.isEmpty()) {
                    insumoList.forEach(reabInsu
                            -> insumoListCopy.add(reabInsu)
                    );
                }

                numeroRegistros = insumoList.size();
            }
        } catch (Exception ex) {
            LOGGER.error("Error al obtener los ReabastosInsumos: " + ex.getMessage());
        }
    }
    private void obtenerReabastoXServicioDetalleSiam(Reabasto reabasto){
        try {
            reabastoXServicio = new ArrayList<>();
            List<FolioAlternativoFolioMus> foliosAlternativos = folioAlternativoFolioMusService.obtenerFoliosAlternativos(reabasto.getFolio());
            for(FolioAlternativoFolioMus folioAlternativo : foliosAlternativos){
                List<DetalleInsumoSiam> detalleSiam = detalleInsumoSiamService.obtenerDetalleSIAM(folioAlternativo.getIdFolioAlternativo());
                if(detalleSiam.size()>0){
                    Reabasto serv =new Reabasto();
                    serv.setFolio(reabasto.getFolio());
                    List<ReabastoInsumo> insumos= new ArrayList<>();
                    for (DetalleInsumoSiam item :detalleSiam) {
                        if(serv.getIdEstructura()==null)
                            serv.setIdEstructura(item.getIdEstructura());
                        ReabastoInsumo rInsumo = insumoService.obtenerReabastoInsumoForSIAM(reabasto.getIdReabasto(), item.getIdInsumo(),item.getIdEstructura()); 
                        
                        insumos.add(rInsumo);
                    }
                    
                    ReabastoXServicio reabXServ = new ReabastoXServicio();
                    reabXServ.setReabasto(serv);
                    reabXServ.setInsumos(insumos);
                    
                    reabastoXServicio.add(reabXServ);
                }
            }
        } catch (Exception e) {
        }
    }//*/
    public void obtenerReabastoInsumo(String idReabasto) {
        LOGGER.trace("mx.mc.magedbean.SolicitudInsumoServicioMB.obtenerReabastoInsumo()");
        try {
            if (permiso.isPuedeVer()) {
                Estructura estruct = new Estructura();
                reabastoSelect = reabastoService.obtenerReabastoPorID(idReabasto);
                estruct.setIdEstructura(reabastoSelect.getIdEstructura());
                estructuraSelect = estructuraService.obtener(estruct);
                campSiam = estructuraSelect.getClaveEstructura().equals(Constantes.PARA_CLAVE_CEDIME);
                contEdit = Constantes.INACTIVO;
                activo = Constantes.INACTIVO;
                switch (estructuraSelect.getIdTipoAlmacen()) {
                    case Constantes.ALMACEN_FARMACIA:
                        Proveedor proveedor = new Proveedor();
                        proveedor.setIdProveedor(reabastoSelect.getIdProveedor());
                        proveedorSelect = provService.obtener(proveedor);

                        reabastoSelect.setProveedor(proveedorSelect.getNombreProveedor());
                        reabastoSelect.setIdProveedor(proveedorSelect.getIdProveedor());
                        if (reabastoSelect.getIdEstatusReabasto().equals(EstatusReabasto_Enum.REGISTRADA.getValue())) {
                            activo = Constantes.ACTIVO;
                            contEdit = Constantes.ACTIVO;
                        }
                        break;
                    case Constantes.ALMACEN:
                    case Constantes.SUBALMACEN:
                        //Si no es Admin el Proveedor es el Padre                                
                        Estructura est = new Estructura();
                        est.setIdEstructura(reabastoSelect.getIdEstructuraPadre());
                        String pdr = estructuraService.obtener(est).getNombre();
                        activo = Constantes.INACTIVO;
                        if (pdr != null) {
                            reabastoSelect.setProveedor(pdr);
                            reabastoSelect.setIdProveedor(reabastoSelect.getIdEstructuraPadre());
                        }
                        if (reabastoSelect.getIdEstatusReabasto().equals(EstatusReabasto_Enum.REGISTRADA.getValue())) {
                            contEdit = Constantes.ACTIVO;
                        }
                        break;
                    default:
                }

                insumoList = insumoService.obtenerInsumosdeReabastoporIdReabasto(idReabasto, estructuraSelect.getIdEstructura(), estructuraSelect.getIdTipoAlmacen());
                if (!insumoList.isEmpty()) {
                    Medicamento medicam = mediService.obtenerPorIdMedicamento(insumoList.get(0).getIdInsumo());
                    tipoInsumo = medicam.getTipo();
                }

                if (insumoListCopy.isEmpty()) {
                    insumoList.forEach(reabInsu
                            -> insumoListCopy.add(reabInsu)
                    );
                }

                numeroRegistros = insumoList.size();
            }
        } catch (Exception ex) {
            LOGGER.error("Error al obtener los ReabastosInsumos: {}", ex.getMessage());
        }
    }

    public List<Medicamento> autoComplete(String cadena) {
        try {
            medicamentoList = mediService.obtenerXNombreTipoCatalog(cadena, tipoInsumo);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener Medicamentos: {}", ex.getMessage());
        }
        return medicamentoList;
    }

    public void onCellEdit(CellEditEvent event) {
        boolean error = false;
        boolean estatus = true;
        int newValue = (int) event.getNewValue();
        oldValue = (int) event.getOldValue();
        position = (int) event.getRowIndex();
        ReabastoInsumo item = insumoList.get(position);

        if (estructuraSelect != null && estructuraSelect.getIdTipoAlmacen() < Constantes.SUBALMACEN) {
            newValue = redondeaCantidad(item.getPiezasCaja(), newValue);
        }

        if (newValue < 0) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("solicRebasto.err.cantidad"), null);
            error = true;
        } else if ((dotacionDiaria || dotacionMC)
                && item.getDotacion() != null
                && item.getDotacion() > 0
                && newValue > (item.getDotacion() - item.getSurtido())) {
            if (tipoOrden == 2) {
                msjAlert = RESOURCES.getString(solicRebastoInfoRequiereAutorizacion) + " <br/> <b>"
                        + item.getClave() + "</b> " + RESOURCES.getString(solicRebastoInfoCantidadMenor) + " <b>"
                        + (item.getDotacion() - item.getSurtido()) + "</b>, se solicita <b>" + newValue + "</b>";
                idInsumoXAutorizar = item.getIdInsumo();
                error = false;
                estatus = false;
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("solicRebasto.err.cantidadDotacion"), null);
                error = true;
            }
        }

        if (error) {
            insumoList.get(position).setCantidadSolicitada(oldValue);
        } else {
            insumoList.get(position).setCantidadSolicitada(newValue);
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, estatus);
    }

    public void onCellEditDetalle(CellEditEvent event) {
        int newValue = (int) event.getNewValue();
        oldValue = (int) event.getOldValue();
        position = (int) event.getRowIndex();
        String idServicio = "";

        if (newValue < 0) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("solicRebasto.err.cantidad"), null);
        } else if (itemSelect != null) {
            // Recorremos la lista mostradapara actualizar los valores
            for (ReabastoInsumo item : insumoList) {
                if (item.getIdInsumo().equals(itemSelect.getIdInsumo())) {
                    item.setCantidadSolicitada(0);
                    item.getDetalleInsumo().forEach((insumo) -> {
                        item.setCantidadSolicitada(redondeaCantidad(item.getPiezasCaja(), item.getCantidadSolicitada() + insumo.getCantidadSolicitada()));
                    });
                    item.getDetalleInsumo().get(position).setCantidadSolicitada(redondeaCantidad(item.getPiezasCaja(), newValue));
                    idServicio = item.getDetalleInsumo().get(position).getIdEstructura();
                    break;
                }
            }
            // recorremos la lista por servicio para actualizar
            for (ReabastoXServicio servicio : reabastoXServicio) {
                if (servicio.getReabasto().getIdEstructura().equals(idServicio)) {
                    for (ReabastoInsumo item : servicio.getInsumos()) {
                        if (item.getIdInsumo().equals(itemSelect.getIdInsumo())) {
                            item.setCantidadSolicitada(redondeaCantidad(item.getPiezasCaja(), newValue));
                            break;
                        }
                    }
                    break;
                }
            }
        }
    }

    public void setItemSelect(ReabastoInsumo item) {
        itemSelect = item;
    }

    public void handleSelect(SelectEvent e) throws Exception {
        try {
            medicamento = (Medicamento) e.getObject();
            if (colectivoXServicio) {
                serviciosItemList = new ArrayList<>();
                if (almacenesServiList.isEmpty()) {
                    String almacen = tipoAlmacen.isEmpty() ? estructuraList.get(0).getIdEstructura() : tipoAlmacen;
                    almacenesServiList = obtenerAlmacenesServicios(almacen);
                }
                serviciosItemList = estructuraService.estructuraItemList(medicamento.getIdMedicamento(), almacenesServiList);
                if (!serviciosItemList.isEmpty()) {
                    Estructura todo = new Estructura();
                    todo.setNombre(Constantes.ALMACENES_TODOS);
                    todo.setIdEstructura(Constantes.ALMACENES_TODOS);
                    serviciosItemList.add(todo);
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Ocurrio una excepcion: ", ex);
        }
    }

    public void handleUnSelect(UnselectEvent e) {
        medicamento = (Medicamento) e.getObject();
    }

    public void validaProveedor() {
        boolean sigue = Constantes.ACTIVO;
        if (isAdmin && reabastoSelect.getIdProveedor() == null) {
            sigue = Constantes.INACTIVO;
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("solicRebasto.err.proveedor"), null);
        } else if (colectivoXServicio
                && reabastoSelect.getIdTipoOrden().equals(Constantes.TIPO_ORDEN_EXTRA)
                && reabastoSelect.getDestino() == null
                && estructuraSelect.getIdTipoAlmacen().equals(TipoAlmacen_Enum.ALMACEN.getValue())) {
            sigue = Constantes.INACTIVO;
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("solicRebasto.err.idServicio"), null);
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, sigue);
    }

    public void authorization() throws Exception {

        try {
            Usuario usr = new Usuario();
            usr.setNombreUsuario(userResponsable);
            usr.setActivo(Constantes.ACTIVO);
            usr.setUsuarioBloqueado(Constantes.INACTIVO);
            exist = false;
            exist2 = false;
            msjMdlSurtimiento = false;
            status = Constantes.INACTIVO;

            Usuario user = usuarioService.obtener(usr);
            if (user != null) {
                boolean pass = passResponsable.equals(user.getClaveAcceso()) || CustomWebSecurityConfigurerAdapter.customPasswordEncoder().matches(passResponsable, user.getClaveAcceso());
                if (pass) {
                    List<TransaccionPermisos> permisosAutorizaList = transaccionService.permisosUsuarioTransaccion(user.getIdUsuario(), Transaccion_Enum.ORDENREABASTO.getSufijo());
                    if (permisosAutorizaList != null) {
                        permisosAutorizaList.stream().forEach(item -> {
                            if (item.getAccion().equals("AUTORIZAR")) {
                                exist = true;
                            }
                        });

                        if (exist) {
                            status = Constantes.ACTIVO;
                            idResponsable = user.getIdUsuario();
                            //Buscar el insumo en la listaInsumos
                            insumoList.forEach(aux -> {
                                if (aux.getIdInsumo().equals(idInsumoXAutorizar)) {
                                    exist2 = true;
                                }
                            });
                            if (exist2) {
                                insumoList.get(position).setIdUsuarioAutoriza(idResponsable);
                            } else if (insumoSelect != null) {
                                insumoSelect.setIdUsuarioAutoriza(idResponsable);
                                insumoList.add(insumoSelect);
                                numeroRegistros = insumoList.size();
                            } else {
                                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("solicRebasto.err.nohayInsumo"), null);
                            }
                            if (permiso.isPuedeAutorizar()) {
                                userResponsable = currentUser.getNombreUsuario();
                                passResponsable = currentUser.getClaveAcceso();
                            } else {
                                userResponsable = "";
                                passResponsable = "";
                            }
                            idInsumoXAutorizar = "";
                            msjMdlSurtimiento = true;
                        } else {
                            userResponsable = "";
                            passResponsable = "";
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("solicRebasto.err.noAutorizar"), null);
                        }
                    } else {
                        userResponsable = "";
                        passResponsable = "";
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("solicRebasto.err.noTransaccion"), null);
                    }
                }
            } else {
                userResponsable = "";
                passResponsable = "";
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("solicRebasto.err.noUser"), null);
            }
        } catch (Exception ex) {
            LOGGER.error("Ocurrio una excepcion: ", ex);
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, ex.getMessage(), null);
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public void cancelAuthorization() {
        insumoList.get(position).setCantidadSolicitada(oldValue);
        msjMdlSurtimiento = true;
    }

    public void reenviarSolicitudSIAM(Reabasto reabasto) {
        boolean reSIAM = false;
        messaje = Constantes.ACTIVO;
        try {
            Estructura estruct = estructuraService.obtenerEstructura(reabasto.getIdEstructura());

            Reabasto reabas = reabastoService.obtener(reabasto);
            if (reabas.getIdEstatusReabasto().equals(EstatusReabasto_Enum.REGISTRADA.getValue())) {
                List<ReabastoInsumo> listInsumo = insumoService.obtenerReabastosInsumos(reabas.getIdReabasto(), estruct.getIdTipoAlmacen());//, estruct.getIdEstructura(), estruct.getIdTipoAlmacen());

                List<ReabastoXServicio> colectivos = generarColectivos(reabas);
                estatuReabastoMus = EstatusReabasto_Enum.REGISTRADA;
                // Recorrer la lista filtrada para reenviarlo a SIAM
                colectivos.forEach((service) -> {
                    int idx = colectivos.indexOf(service);
                    enviarColectivoSIAM(reabas, service.getReabasto().getIdEstructura(), service.getInsumos(),idx);
                });
                reabas.setIdEstatusReabasto(estatuReabastoMus.getValue());
                listInsumo = updateInsumoMasivo(listInsumo, estatuReabastoMus.getValue());
                reabastoService.actualizarSolicitud(reabas, listInsumo);

                //Imprimimos la solicitud
                if (estatuReabastoMus.equals(EstatusReabasto_Enum.SOLICITADA)) {
                    printOrdenReabastoServicioAll(reabas, true);
                }

            }
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al reenviar la solicitud a SIAM", ex);
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, reSIAM);
    }

    public void reenviarServicioSIAM(FolioAlternativoFolioMusExtended servicio) {
        try {
            messaje = Constantes.INACTIVO;
            Estructura estruct = estructuraService.obtenerEstructura(servicio.getIdEstructura());
            Reabasto reaba = reabastoService.getReabastoByFolio(servicio.getFolioMUS());
            reaba.setIdEstructura(servicio.getIdEstructura());
            reaba.setAlmacen(estruct.getNombre());
            reaba.setIdTipoOrigen(TipoOrigen_Enum.ADMINISTRACION.getValue());

            List<DetalleInsumoSiam> insumosServ = detalleInsumoSiamService.obtenerDetalleXFolioEstructura(reaba.getFolio(), servicio.getIdEstructura(), EstatusReabasto_Enum.REGISTRADA.toString());
            List<ReabastoInsumo> insumosServicio = new ArrayList<>();
            for (DetalleInsumoSiam detalle : insumosServ) {
                Medicamento item = mediService.obtenerPorIdMedicamento(detalle.getIdInsumo());
                AlmacenControl ctrl = controlService.obtenerIdPuntosControl(servicio.getIdEstructura(), detalle.getIdInsumo());
                ReabastoInsumo insumo = new ReabastoInsumo();
                insumo.setIdReabastoInsumo(Comunes.getUUID());
                insumo.setIdReabasto(reaba.getIdReabasto());
                insumo.setIdInsumo(detalle.getIdInsumo());
                insumo.setCantidadSolicitada(detalle.getCantidadSolicitada() * item.getFactorTransformacion());
                insumo.setPiezasCaja(item.getFactorTransformacion());
                insumo.setIdAlmacenPuntosControl(ctrl.getIdAlmacenPuntosControl());
                insumo.setMinimo(ctrl.getMinimo());
                insumo.setReorden(ctrl.getReorden());
                insumo.setMaximo(ctrl.getMaximo());
                insumo.setDotacion(ctrl.getDotacion());

                insumosServicio.add(insumo);
            }

            if (insumosServicio.size() > 0) {
                FolioAlternativoFolioMusExtended cole = new FolioAlternativoFolioMusExtended();
                cole.setIdEstructura(reaba.getIdEstructura());

                estatuReabastoMus = EstatusReabasto_Enum.REGISTRADA;
                // Recorrer la lista filtrada para reenviarlo a SIAM

                cole.setFolioAlternativo(enviarColectivoSIAM(reaba, servicio.getIdEstructura(), insumosServicio,0));

                //Imprimimos la solicitud
                if (estatuReabastoMus.equals(EstatusReabasto_Enum.SOLICITADA)) {
                    printOrdenReabastoServicioUnitario(cole);
                }
            }
        } catch (Exception ex) {
        }
    }

    public void consultarColectivoSIAM(Reabasto reabasto) {
        colectivosSIAM = new ArrayList<>();
        reabastoSelect = reabasto;
        boolean reSIAM = false;
        try {
            colectivosSIAM = folioAlternativoFolioMusService.obtenerFoliosAlternativosExtended(reabasto.getFolio());
            for (FolioAlternativoFolioMusExtended alternativo : colectivosSIAM) {
                alternativo.setResponse(ConsultaColectivoSIAM(alternativo.getFolioAlternativo(), alternativo.getFolioMUS()));
                if (alternativo.getResponse() != null) {
                    if (!alternativo.getResponse().getRespuestaMensaje().contains("Error") && alternativo.getFolioAlternativo() != null) {
                        alternativo.setEstatus(alternativo.getResponse().getRespuestaMensaje().toUpperCase());
                        //alternativo.setRecibir(Constantes.ACTIVO);
                    } else {
                        alternativo.setEstatus(alternativo.getEstatus());
                        //alternativo.setRecibir(Constantes.INACTIVO);
                        LOGGER.error("Codigo: {}, Mensaje: {}", alternativo.getResponse().getRespuestaCodigo(), alternativo.getResponse().getRespuestaMensaje());
                    }
                    reSIAM = true;
                } else {
                    LOGGER.error("Ocurrio un error al conectarse con SIAM.");
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Ocurrio un error al conectarse con SIAM.", null);
                    break;
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al consultar colectivo SIAM", ex);
            reSIAM = false;
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, reSIAM);
    }

    public void selectAll() {
        try {
            for (FolioAlternativoFolioMusExtended alternativo : colectivosSIAM) {
                if (!alternativo.isRecibir() && alternativo.getFolioAlternativo() != null) {
                    alternativo.setRecibir(Constantes.ACTIVO);
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al seleccionar colectivos SIAM", ex);
        }
    }

    public void procesarColectivosSelect() {
        try {
            colectivosSIAM.stream().filter((colectivo) -> (colectivo.isRecibir())).forEachOrdered((colectivo) -> {
                ResponseDetalleSIAM respSiam = ConsultaColectivoSIAM(colectivo.getFolioAlternativo(), colectivo.getFolioMUS());
                if (respSiam != null && respSiam.getDatosExtra() != null) {
                    JSONObject myObj = new JSONObject();
                    myObj.put("token", "");
                    myObj.put("Folio", colectivo.getFolioAlternativo());

                    JSONArray myArr = new JSONArray();
                    for (InsumoSIAM item : respSiam.getDatosExtra()) {
                        JSONObject myOb = new JSONObject();
                        myOb.put("Cantidad", item.getCantidad());
                        myOb.put("ClaveInsumo", item.getClaveInsumo().replace(".", ""));
                        myOb.put("Existencia", false);

                        myArr.put(myOb);
                    }
                    myObj.put("Insumos", myArr);
                    ColectivoSiam cs = new ColectivoSiam(servletContext);
                    Response respMus = cs.recepcionColectivo(myObj.toString());
                    //Mensaje.showMessage(Constantes.MENSAJE_INFO, );
                }
            });
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al consultar colectivo SIAM", ex);
        }
    }

    //<editor-fold  defaultstate="collapsed" desc="Getter and Setters...">
    public boolean isOrgdes() {
        return orgdes;
    }

    public void setOrgdes(boolean orgdes) {
        this.orgdes = orgdes;
    }

    public Pattern getRegexNumber() {
        return regexNumber;
    }

    public void setRegexNumber(Pattern regexNumber) {
        this.regexNumber = regexNumber;
    }

    public boolean isMessaje() {
        return messaje;
    }

    public void setMessaje(boolean messaje) {
        this.messaje = messaje;
    }

    public String getPathPdf() {
        return pathPdf;
    }

    public void setPathPdf(String pathPdf) {
        this.pathPdf = pathPdf;
    }

    public File getPdf() {
        return pdf;
    }

    public void setPdf(File pdf) {
        this.pdf = pdf;
    }

    public StreamedContent getStreamedContent() {
        return streamedContent;
    }

    public void setStreamedContent(StreamedContent streamedContent) {
        this.streamedContent = streamedContent;
    }

    public ReportesService getReportesService() {
        return reportesService;
    }

    public void setReportesService(ReportesService reportesService) {
        this.reportesService = reportesService;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isContEdit() {
        return contEdit;
    }

    public void setContEdit(boolean contEdit) {
        this.contEdit = contEdit;
    }

    public List<Estructura> getEstructuraAux() {
        return estructuraAux;
    }

    public void setEstructuraAux(List<Estructura> estructuraAux) {
        this.estructuraAux = estructuraAux;
    }

    public InventarioService getInventarioService() {
        return inventarioService;
    }

    public void setInventarioService(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    public Inventario getInventarioSelect() {
        return inventarioSelect;
    }

    public void setInventarioSelect(Inventario inventarioSelect) {
        this.inventarioSelect = inventarioSelect;
    }

    public List<Inventario> getInventarioList() {
        return inventarioList;
    }

    public void setInventarioList(List<Inventario> inventarioList) {
        this.inventarioList = inventarioList;
    }

    public boolean isPrintSolicitud() {
        return printSolicitud;
    }

    public void setPrintSolicitud(boolean printSolicitud) {
        this.printSolicitud = printSolicitud;
    }

    public ProveedorService getProvService() {
        return provService;
    }

    public void setProvService(ProveedorService provService) {
        this.provService = provService;
    }

    public List<Proveedor> getProvList() {
        return provList;
    }

    public void setProvList(List<Proveedor> provList) {
        this.provList = provList;
    }

    public boolean isManual() {
        return manual;
    }

    public void setManual(boolean manual) {
        this.manual = manual;
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public int getTipoOrden() {
        return tipoOrden;
    }

    public void setTipoOrden(int tipoOrden) {
        this.tipoOrden = tipoOrden;
    }

    public ReabastoInsumoService getInsumoService() {
        return insumoService;
    }

    public void setInsumoService(ReabastoInsumoService insumoService) {
        this.insumoService = insumoService;
    }

    public ReabastoInsumo getInsumoSelect() {
        return insumoSelect;
    }

    public void setInsumoSelect(ReabastoInsumo insumoSelect) {
        this.insumoSelect = insumoSelect;
    }

    public List<ReabastoInsumo> getInsumoList() {
        return insumoList;
    }

    public void setInsumoList(List<ReabastoInsumo> insumoList) {
        this.insumoList = insumoList;
    }

    public int getCantSolicitada() {
        return cantSolicitada;
    }

    public void setCantSolicitada(int cantSolicitada) {
        this.cantSolicitada = cantSolicitada;
    }

    public CatalogoGeneralService getCatalogoGeneralService() {
        return catalogoGeneralService;
    }

    public void setCatalogoGeneralService(CatalogoGeneralService catalogoGeneralService) {
        this.catalogoGeneralService = catalogoGeneralService;
    }

    public MedicamentoService getMediService() {
        return mediService;
    }

    public void setMediService(MedicamentoService mediService) {
        this.mediService = mediService;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    public List<Medicamento> getMedicamentoList() {
        return medicamentoList;
    }

    public void setMedicamentoList(List<Medicamento> medicamentoList) {
        this.medicamentoList = medicamentoList;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public int getTipoInsumo() {
        return tipoInsumo;
    }

    public void setTipoInsumo(int tipoInsumo) {
        this.tipoInsumo = tipoInsumo;
    }

    public List<CatalogoGeneral> getTipoInsumoList() {
        return tipoInsumoList;
    }

    public void setTipoInsumoList(List<CatalogoGeneral> tipoInsumoList) {
        this.tipoInsumoList = tipoInsumoList;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getTipoAlmacen() {
        return tipoAlmacen;
    }

    public void setTipoAlmacen(String tipoAlmacen) {
        this.tipoAlmacen = tipoAlmacen;
    }

    public String getCadenaBusqueda() {
        return cadenaBusqueda;
    }

    public void setCadenaBusqueda(String cadenaBusqueda) {
        this.cadenaBusqueda = cadenaBusqueda;
    }

    public EstructuraService getEstructuraService() {
        return estructuraService;
    }

    public void setEstructuraService(EstructuraService estructuraService) {
        this.estructuraService = estructuraService;
    }

    public Estructura getEstructuraSelect() {
        return estructuraSelect;
    }

    public void setEstructuraSelect(Estructura estructuraSelect) {
        this.estructuraSelect = estructuraSelect;
    }

    public List<Estructura> getEstructuraList() {
        return estructuraList;
    }

    public void setEstructuraList(List<Estructura> estructuraList) {
        this.estructuraList = estructuraList;
    }

    public ReabastoService getReabastoService() {
        return reabastoService;
    }

    public void setReabastoService(ReabastoService reabastoService) {
        this.reabastoService = reabastoService;
    }

    public Reabasto getReabastoSelect() {
        return reabastoSelect;
    }

    public void setReabastoSelect(Reabasto reabastoSelect) {
        this.reabastoSelect = reabastoSelect;
    }

    public boolean isBtnNew() {
        return btnNew;
    }

    public void setBtnNew(boolean btnNew) {
        this.btnNew = btnNew;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public int getNumeroRegistros() {
        return numeroRegistros;
    }

    public void setNumeroRegistros(int numeroRegistros) {
        this.numeroRegistros = numeroRegistros;
    }

    public SolicitudReabastoLazy getSolicitudReabastoLazy() {
        return solicitudReabastoLazy;
    }

    public void setSolicitudReabastoLazy(SolicitudReabastoLazy solicitudReabastoLazy) {
        this.solicitudReabastoLazy = solicitudReabastoLazy;
    }

    public boolean isDotacionDiaria() {
        return dotacionDiaria;
    }

    public void setDotacionDiaria(boolean dotacionDiaria) {
        this.dotacionDiaria = dotacionDiaria;
    }

    public boolean isCampoCantidadXLote() {
        return campoCantidadXLote;
    }

    public void setCampoCantidadXLote(boolean campoCantidadXLote) {
        this.campoCantidadXLote = campoCantidadXLote;
    }

    public boolean isCampoCantidadXClave() {
        return campoCantidadXClave;
    }

    public void setCampoCantidadXClave(boolean campoCantidadXClave) {
        this.campoCantidadXClave = campoCantidadXClave;
    }

    public String getMsjAlert() {
        return msjAlert;
    }

    public void setMsjAlert(String msjAlert) {
        this.msjAlert = msjAlert;
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

    public String getIdResponsable() {
        return idResponsable;
    }

    public void setIdResponsable(String idResponsable) {
        this.idResponsable = idResponsable;
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isPointCtrl() {
        return pointCtrl;
    }

    public void setPointCtrl(boolean pointCtrl) {
        this.pointCtrl = pointCtrl;
    }

    public boolean isMostrarFolioAlternativo() {
        return mostrarFolioAlternativo;
    }

    public void setMostrarFolioAlternativo(boolean mostrarFolioAlternativo) {
        this.mostrarFolioAlternativo = mostrarFolioAlternativo;
    }

    public boolean isNuevaOrden() {
        return nuevaOrden;
    }

    public void setNuevaOrden(boolean nuevaOrden) {
        this.nuevaOrden = nuevaOrden;
    }

    public boolean isExits() {
        return exits;
    }

    public void setExits(boolean exits) {
        this.exits = exits;
    }

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    public boolean isExist2() {
        return exist2;
    }

    public void setExist2(boolean exist2) {
        this.exist2 = exist2;
    }

    public boolean isDestinoCole() {
        return destinoCole;
    }

    public void setDestinoCole(boolean destinoCole) {
        this.destinoCole = destinoCole;
    }

    public List<Estructura> getServiciosList() {
        return serviciosList;
    }

    public void setServiciosList(List<Estructura> ServiciosList) {
        this.serviciosList = ServiciosList;
    }

    public List<FolioAlternativoFolioMusExtended> getColectivosSIAM() {
        return colectivosSIAM;
    }

    public void setColectivosSIAM(List<FolioAlternativoFolioMusExtended> colectivosSIAM) {
        this.colectivosSIAM = colectivosSIAM;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

    public boolean isColectivoXServicio() {
        return colectivoXServicio;
    }

    public void setColectivoXServicio(boolean colectivoXServicio) {
        this.colectivoXServicio = colectivoXServicio;
    }

    public List<Estructura> getServiciosItemList() {
        return serviciosItemList;
    }

    public void setServiciosItemList(List<Estructura> serviciosItemList) {
        this.serviciosItemList = serviciosItemList;
    }

    public String getIdServiceAdd() {
        return idServiceAdd;
    }

    public void setIdServiceAdd(String idServiceAdd) {
        this.idServiceAdd = idServiceAdd;
    }

    public boolean isShowExpansion() {
        return showExpansion;
    }

    public void setShowExpansion(boolean showExpansion) {
        this.showExpansion = showExpansion;
    }    

    public boolean isCampSiam() {
        return campSiam;
    }

    public void setCampSiam(boolean campSiam) {
        this.campSiam = campSiam;
    }
//</editor-fold>
}
