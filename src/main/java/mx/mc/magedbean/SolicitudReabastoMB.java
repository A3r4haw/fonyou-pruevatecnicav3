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
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.FacesContext;

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
import mx.mc.enums.TipoOrigen_Enum;
import mx.mc.init.CustomWebSecurityConfigurerAdapter;
import mx.mc.model.FolioAlternativoFolioMus;
import mx.mc.model.DiaFestivo;
import mx.mc.model.PermisoUsuario;
import mx.mc.service.FolioAlternativoFolioMusService;
import mx.mc.service.TransaccionService;
import mx.mc.service.UsuarioService;
import mx.mc.util.Mensaje;
import org.primefaces.PrimeFaces;
import mx.mc.service.DiaFestivoService;

/**
 *
 * @author bbautista
 */
@Controller
@Scope(value = "view")
public class SolicitudReabastoMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(SolicitudReabastoMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private PermisoUsuario permiso;
    private boolean btnNew;
    private boolean activo;
    private boolean isAdmin;
    private boolean isJefeArea;
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
    private String idReabastoAnalgesia;
    private String idReabastoAnestesia;
    private String idReabastoCardiologia;
    private String idReabastoDermatologia;
    private String idReabastoEndo;
    private String idReabastoEIP;
    private String idReabastoEI;
    private String idReabastoGastro;
    private String idReabastoGineco;
    private String idReabastoHemato;
    private String idReabastoIntoxic;
    private String idReabastoNefroUro;
    private String idReabastoNeumo;
    private String idReabastoNeuro;
    private String idReabastoOftalmo;
    private String idReabastoOncolo;
    private String idReabastoOtorrino;
    private String idReabastoPlaniFamiliar;
    private String idReabastoPsiquiatria;
    private String idReabastoRT;
    private String idReabastoSolucPlasma;
    private String idReabastoVacunas;
    private String idGrupoI;
    private String idGrupoII;
    private String idGrupoIII;
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
    private Usuario currentUser;

    @Autowired
    private transient EstructuraService estructuraService;
    private Estructura estructuraSelect;
    private List<Estructura> estructuraList;
    private List<Estructura> estructuraAux;

    @Autowired
    private transient ReabastoService reabastoService;
    private Reabasto reabastoSelect;
    private List<Reabasto> listReabastoSelect;
    private List<FolioAlternativoFolioMus> listFolioAlternativos;
    private SolicitudReabastoLazy solicitudReabastoLazy;

    @Autowired
    private transient ReabastoInsumoService insumoService;
    private ReabastoInsumo insumoSelect;
    private List<ReabastoInsumo> insumoList;
    private List<ReabastoInsumo> insumoListCopy;

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

    @PostConstruct
    public void init() {
        LOGGER.trace("mx.mc.magedbean.SolicitudReabastoMB.init()");
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
        listReabastoSelect = new ArrayList<>();
        listFolioAlternativos = new ArrayList<>();
        insumoSelect = new ReabastoInsumo();
        estructuraList = new ArrayList<>();
        tipoInsumoList = new ArrayList<>();
        insumoList = new ArrayList<>();
        insumoListCopy = new ArrayList<>();
        /*Se comentan lineas por cambio de separación de grupo, ya no sera por el valor de SubcategoriaMedicamento*/
        feriadosList = new ArrayList<>();
        idReabastoAnalgesia = null;
        idReabastoAnestesia = null;
        idReabastoCardiologia = null;
        idReabastoDermatologia = null;
        idReabastoEndo = null;
        idReabastoEIP = null;
        idReabastoEI = null;
        idReabastoGastro = null;
        idReabastoGineco = null;
        idReabastoHemato = null;
        idReabastoIntoxic = null;
        idReabastoNefroUro = null;
        idReabastoNeumo = null;
        idReabastoNeuro = null;
        idReabastoOftalmo = null;
        idReabastoOncolo = null;
        idReabastoOtorrino = null;
        idReabastoPlaniFamiliar = null;
        idReabastoPsiquiatria = null;
        idReabastoRT = null;
        idReabastoSolucPlasma = null;
        idReabastoVacunas = null;
        idGrupoI = null;
        idGrupoII = null;
        idGrupoIII = null;
        /*Ya no se ocuparan por cambio de separación de grupo, ya no sera por el valor de SubcategoriaMedicamento*/
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
        isJefeArea = Constantes.INACTIVO;
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
        permiso = Comunes.obtenerPermisos(Transaccion_Enum.ORDENREABASTO.getSufijo());
        nombreCompleto = (currentUser.getNombre() + " " + currentUser.getApellidoPaterno() + " " + currentUser.getApellidoMaterno()).toUpperCase();
        validarUsuarioAdministrador();
        obtieneAlmacen();
        obtieneProveedor();
        insumos();
    }

    private void clean() {
        reabastoSelect = new Reabasto();
        listReabastoSelect = new ArrayList<>();
        listFolioAlternativos = new ArrayList<>();
        insumoList = new ArrayList<>();
        /*Se comentan lineas por cambio de separación de grupo, ya no sera por el valor de SubcategoriaMedicamento*/
        idReabastoAnalgesia = null;
        idReabastoAnestesia = null;
        idReabastoCardiologia = null;
        idReabastoDermatologia = null;
        idReabastoEndo = null;
        idReabastoEIP = null;
        idReabastoEI = null;
        idReabastoGastro = null;
        idReabastoGineco = null;
        idReabastoHemato = null;
        idReabastoIntoxic = null;
        idReabastoNefroUro = null;
        idReabastoNeumo = null;
        idReabastoNeuro = null;
        idReabastoOftalmo = null;
        idReabastoOncolo = null;
        idReabastoOtorrino = null;
        idReabastoPlaniFamiliar = null;
        idReabastoPsiquiatria = null;
        idReabastoRT = null;
        idReabastoSolucPlasma = null;
        idReabastoVacunas = null;
        idGrupoI = null;
        idGrupoII = null;
        idGrupoIII = null;
        /*Ya no se ocuparan por cambio de separación de grupo, ya no sera por el valor de SubcategoriaMedicamento*/
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
            this.isJefeArea = sesion.isJefeArea();
            if (!this.isAdmin && !this.isJefeArea) {
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
                idAlmacen = estructuraSelect.getIdTipoAlmacen();
                if (this.isAdmin || this.isJefeArea) {
                    if(this.isAdmin) {
                        estructuraAux = estructuraService.obtenerAlmacenes();
                    } else {
                        estructuraList = estructuraService.obtenerAlmacenesQueSurtenServicio(currentUser.getIdEstructura());
                        if(estructuraList.isEmpty()) {
                            //Se cambia valor para que no muestre combo y pueda visualizar el almacen
                            isJefeArea = false;
                            idEstructura = estructuraSelect.getIdEstructura();
                            tipoAlmacen = currentUser.getIdEstructura();
                        }
                    }                
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
        LOGGER.trace("SolicitudReabastoMB.printOrdenReabasto(reabasto)");
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
        LOGGER.trace("SolicitudReabastoMB.printOrdenReabasto(reabasto)");
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
    
    public void buscarReabasto() {
        LOGGER.trace("Buscando coincidencias de: {}", cadenaBusqueda);
        try {
            if (cadenaBusqueda != null && cadenaBusqueda.trim().isEmpty()) {
                cadenaBusqueda = null;
            }
            String almacen = "";

            if (tipoAlmacen.isEmpty()) {
                almacen = estructuraList.get(0).getIdEstructura();
            } else {
                almacen = tipoAlmacen;
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
// TODO : 17ABR2024 agregar validación  de selección de estructura
            if (permiso.isPuedeCrear()) {
                nuevaOrden = Constantes.ACTIVO;
                if (tipoOrden > 0) {
                    clean();
                    if (isAdmin || isJefeArea) {
                        Estructura estruct = new Estructura();
                        estruct.setIdEstructura(tipoAlmacen);
                        estructuraSelect = estructuraService.obtener(estruct);
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
                        ordenAutomatica(estructuraSelect, estructuraSelect.getIdTipoAlmacen());
                    }
                    status = Constantes.ACTIVO;
                    messaje = Constantes.INACTIVO;
                    contEdit = Constantes.ACTIVO;
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
                if (medicamento != null && cantSolicitada > 0) {
                    Medicamento medto = mediService.obtenerPorIdMedicamento(medicamento.getIdMedicamento());
                    ReabastoInsumo datosPC = insumoService.obtenerMaxMinReorCantActual(idEstructura, medicamento.getIdMedicamento());
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
                        if (separaInsumos && estructuraSelect.getIdTipoAlmacen().equals(TipoAlmacen_Enum.ALMACEN.getValue()) && this.tipoInsumo == 38) {
                            /* Se setea la clave del medicamento para despues poder generar los grupos de reabasto */
                            insumoSelect.setGrupo(insumoSelect.getClave());
                        }
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
                        if (dotacionDiaria || dotacionMC) {
                            int dias = calDiasDotacion();
                            if (insumoSelect.getDotacion() != null) {
                                insumoSelect.setDotacion(insumoSelect.getDotacion() * dias);
                                if (insumoSelect.getSurtido() < insumoSelect.getDotacion()) {
                                    if (insumoSelect.getCantidadSolicitada() > (insumoSelect.getDotacion() - insumoSelect.getCantidadActual())) {
                                        if (tipoOrden == 2) {
                                            int maximo = (insumoSelect.getDotacion() - insumoSelect.getCantidadActual());
                                            if (maximo < 0) {
                                                maximo = 0;
                                            }
                                            msjAlert = RESOURCES.getString(solicRebastoInfoRequiereAutorizacion) + " <br/> <b>"
                                                    + insumoSelect.getClave() + "</b> " + RESOURCES.getString(solicRebastoInfoCantidadMenor) + " <b>"
                                                    + maximo + "</b>, se solicita <b>" + cantSolicitada + "</b>";
                                            idInsumoXAutorizar = insumoSelect.getIdInsumo();
                                            sigue = false;
                                        } else {
                                            insumoSelect.setCantidadSolicitada(insumoSelect.getDotacion() - insumoSelect.getSurtido());
                                        }
                                    } else if (tipoOrden == 2) {
                                        Mensaje.showMessage("Warn", RESOURCES.getString("solicRebasto.war.dotacionpendiente") + ": " + insumoSelect.getClave(), null);
                                        sigue2 = false;
                                    }
                                } else {
                                    if (tipoOrden == 2) {
                                        int maximo = (insumoSelect.getDotacion() - insumoSelect.getCantidadActual());
                                        if (maximo < 0) {
                                            maximo = 0;
                                        }
                                        msjAlert = RESOURCES.getString(solicRebastoInfoRequiereAutorizacion) + " <br/> <b>"
                                                + insumoSelect.getClave() + "</b> " + RESOURCES.getString(solicRebastoInfoCantidadMenor) + " <b>"
                                                + maximo + "</b>, se solicita <b>" + cantSolicitada + "</b>";
                                        idInsumoXAutorizar = insumoSelect.getIdInsumo();
                                        sigue = false;
                                    } else {
                                        insumoSelect.setCantidadSolicitada(0);
                                    }
                                }
                                //calcular nivel de surtimiento
                                insumoSelect.setNivelSurt(nivelSurtimiento(insumoSelect));
                            }
                        }

                        if (sigue && sigue2) {
                            insumoList.add(insumoSelect);
                            numeroRegistros = insumoList.size();
                        }
                    } else {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("solicRebasto.err.existeMedicamento"), null);
                        PrimeFaces.current().executeScript("jQuery('td.ui-editable-column').eq(" + activeRow + ").each(function(){jQuery(this).click()});");
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
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, sigue);
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

    public void deleteInsumoOfList(String clv) {
        int index = -1;
        try {
            for (ReabastoInsumo aux : insumoList) {
                if (aux.getIdInsumo().equals(clv)) {
                    insumoService.eliminar(aux);
                    index = insumoList.indexOf(aux);
                    break;
                }
            }
            if (index >= 0) {
                insumoList.remove(index);
            }
            numeroRegistros = insumoList.size();
        } catch (Exception ex) {
            LOGGER.error("Error al obtener el Insumo: {}", ex.getMessage());
        }
    }

    public void cancelReabastoOfList(String idReabasto) {
        try {
            if (permiso.isPuedeEditar()) {
                Estructura estruct = new Estructura();
                Reabasto reabasto = reabastoService.obtenerReabastoPorID(idReabasto);
                estruct.setIdEstructura(reabasto.getIdEstructura());
                estructuraSelect = estructuraService.obtener(estruct);

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
                        reabastoSelect.setIdTipoOrigen(TipoOrigen_Enum.ADMINISTRACION.getValue());
                        reabastoSelect.setInsertFecha(new Date());
                        reabastoSelect.setInsertIdUsuario(currentUser.getIdUsuario());
                        // agregamos los campos insertFecha, insertUsuario, estatus, etc.
                        listaFiltrada = insertInsumoMasivo(listaFiltrada, estatusReabasto);

                        //Validar si se encuentra activo parametro para separar medicamentos por grupo
                        if (separaInsumos && estructuraSelect.getIdTipoAlmacen().equals(TipoAlmacen_Enum.ALMACEN.getValue()) && tipoInsumo == 38) {
                            boolean grupoEspecial = true;
                            boolean grupoUno = true;
                            boolean grupoDos = true;
                            boolean grupoTres = true;
                            boolean grupoCuatro = true;
                            boolean grupoCinco = true;
                            crearReabastosPorGrupo(listaFiltrada, grupoEspecial, grupoUno, grupoDos, grupoTres, grupoCuatro, grupoCinco);
                            reabastoService.insertarSolicitud(listFolioAlternativos, reabastoSelect, listaFiltrada, idAlmacen, separaInsumos);
                        } else {
                            reabastoService.insertarSolicitud(reabastoSelect, listaFiltrada, estructuraSelect.getIdTipoAlmacen(),false);
                        }
                    }
                }
                if (printSolicitud) {
                    if (separaInsumos && estructuraSelect.getIdTipoAlmacen().equals(TipoAlmacen_Enum.ALMACEN.getValue()) && tipoInsumo == 38) {
                        printOrdenReabasto(reabastoSelect, listFolioAlternativos);
                    } else {
                        printOrdenReabasto(reabastoSelect);
                    }
                }
                messaje = Constantes.ACTIVO;
            }
        } catch (Exception ex) {
            LOGGER.error("Error al Enviar el reabasto: {}", ex.getMessage());
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
        LOGGER.trace("mx.mc.magedbean.SolicitudReabastoMB.obtenerReabastoInsumo()");
        try {
            if (permiso.isPuedeVer()) {
                nuevaOrden = Constantes.INACTIVO;
                Estructura estruct = new Estructura();
                estruct.setIdEstructura(reabastoSelect.getIdEstructura());
                estructuraSelect = estructuraService.obtener(estruct);
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

                insumoList = insumoService.obtenerInsumosdeReabastoporIdReabasto(reabastoSelect.getIdReabasto(), estructuraSelect.getIdEstructura(), estructuraSelect.getIdTipoAlmacen());
                if (!insumoList.isEmpty()) {
                    tipoInsumo = insumoList.get(0).getReorden();
                }

                if (insumoListCopy.isEmpty()) {
                    insumoList.forEach(reabInsu -> 
                        insumoListCopy.add(reabInsu)
                    );
                }

                numeroRegistros = insumoList.size();
            }
        } catch (Exception ex) {
            LOGGER.error("Error al obtener los ReabastosInsumos: " + ex.getMessage());
        }
    }

    public void obtenerReabastoInsumo(String idReabasto) {
        LOGGER.trace("mx.mc.magedbean.SolicitudReabastoMB.obtenerReabastoInsumo()");
        try {
            if (permiso.isPuedeVer()) {
                Estructura estruct = new Estructura();
                reabastoSelect = reabastoService.obtenerReabastoPorID(idReabasto);
                estruct.setIdEstructura(reabastoSelect.getIdEstructura());
                estructuraSelect = estructuraService.obtener(estruct);
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
                    tipoInsumo = insumoList.get(0).getReorden();
                }

                if (insumoListCopy.isEmpty()) {
                    insumoList.forEach(reabInsu -> 
                        insumoListCopy.add(reabInsu)
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
            if (isAdmin || isJefeArea) {
                idEstructura = tipoAlmacen;
                for (int i = 0; i < estructuraAux.size(); i++) {
                    Estructura est = estructuraAux.get(i);
                    if (idEstructura.equals(est.getIdEstructura()) && est.getIdTipoAlmacen() == 4) {
                        idEstructura = est.getIdEstructura();
                    }
                }
            }
            medicamentoList = mediService.obtenerXNombreTipo(cadena, tipoInsumo, idEstructura);
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
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("solicRebasto.err.cantidad"),null);
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

    public void handleSelect(SelectEvent e) {
        medicamento = (Medicamento) e.getObject();
    }

    public void handleUnSelect(UnselectEvent e) {
        medicamento = (Medicamento) e.getObject();
    }

    public void validaProveedor() {
        boolean sigue = Constantes.ACTIVO;
        boolean validaCantSolicitada = false;
        if (isAdmin && isJefeArea && reabastoSelect.getIdProveedor() == null) {
            sigue = Constantes.INACTIVO;
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("solicRebasto.err.proveedor"),null);
        }
        for(ReabastoInsumo unReabastoInsumo : insumoList) {
            if(unReabastoInsumo.getCantidadSolicitada() != 0){
                validaCantSolicitada = true;
                break;
            }
        }
        if(!validaCantSolicitada) {
            sigue = Constantes.INACTIVO;
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Al menos un medicamento se debe de solicitar con cantidad mayor a cero",null);
        }
        for(ReabastoInsumo unReabastoInsumo : insumoList) {
            if(unReabastoInsumo.getCantidadSolicitada() != 0){
                validaCantSolicitada = true;
                break;
            }
        }
        if(!validaCantSolicitada) {
            sigue = Constantes.INACTIVO;
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Al menos un medicamento se debe de solicitar con cantidad mayor a cero",null);
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

    public boolean isIsJefeArea() {
        return isJefeArea;
    }

    public void setIsJefeArea(boolean isJefeArea) {
        this.isJefeArea = isJefeArea;
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
    
    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }        
//</editor-fold>
}
