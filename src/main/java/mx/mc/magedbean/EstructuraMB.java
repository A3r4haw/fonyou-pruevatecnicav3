/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import mx.mc.enums.TipoAlmacen_Enum;
import mx.mc.enums.TipoAreaEstructura_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.lazy.AlmacenServicioLazy;
import mx.mc.lazy.ServicioCamasLazy;
import mx.mc.model.Cama;
import mx.mc.model.CamaExtended;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.EstatusCama;
import mx.mc.model.EstrucAlmacenServicio_Extend;
import mx.mc.model.Estructura;
import mx.mc.model.EstructuraExtended;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.TipoAlmacen;
import mx.mc.model.TipoAreaEstructura;
import mx.mc.model.TipoCama;
import mx.mc.model.TipoSurtimiento;
import mx.mc.model.Usuario;
import mx.mc.service.CamaService;
import mx.mc.service.EntidadHospitalariaService;
import mx.mc.service.EstatusCamaService;
import mx.mc.service.EstructuraAlmacenServicioService;
import mx.mc.service.EstructuraService;
import mx.mc.service.EstructuraTipoSurtimientoService;
import mx.mc.service.TipoAlmacenService;
import mx.mc.service.TipoAreaEstructuraService;
import mx.mc.service.TipoCamaService;
import mx.mc.service.TipoSurtimientoService;
import mx.mc.util.Comunes;
import mx.mc.util.Mensaje;
import org.primefaces.PrimeFaces;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
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
public class EstructuraMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(EstructuraMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    private String inicio;
    private String servicios;
    private String almacenes;
    private String asignacion;
    private String prioridad;
    private String idEntidadHospitalaria;
    private String idGrupo;
    private String idTipoSurtimiento;
    private String almacenServicio;
    private String paramEstatus;
    private String titleServicio;
    private String idServicioSelect;
    private String[] tipoSurtimientosSelected;
    private Integer idEstatusCama;
    private Integer idTipoCama;
    private Integer suma;

    private boolean msjMdlEstructura;
    private boolean tblAlmacenServicio;
    private boolean renderPanelConsulta;
    private boolean existNode;
    private boolean esPadre;
    private boolean insertaCama;
    private boolean insertaServicio;
    private boolean insertaAlmacen;
    private boolean crearCama;
    private boolean crearServicio;
    private boolean crearAlmacen;
    private boolean servicioActivo;
    private boolean almacenActivo;
    private boolean mostrarComboPadre;
    private boolean transAutomatica;
    private boolean funAlmacenServicioMultiple;
    private boolean mostrarPrioridad;
    private boolean savePrioridad;

    private TreeNode rootServicio;
    private TreeNode rootServicios;
    private TreeNode rootAlmacen;
    private TreeNode selectNodeServicio;
    private TreeNode selectNodeAlmacen;
    private TreeNode selectedNodeServicio;
    private TreeNode selectedNodeAlmacen;
    private TreeNode[] selectedNodesServicio;

    private List<TipoCama> tipoCamaList;
    private List<TipoAlmacen> tipoAlmacenList;
    private List<EstatusCama> estatusCamaList;
    private List<EstructuraExtended> serviciosList;
    private List<EstructuraExtended> almacenList;
    private List<EstructuraExtended> serviciosAsigList;
    private List<Estructura> serviciosPadreList;
    private List<Estructura> almacenPadreList;
    private List<CamaExtended> listCamas;
    private List<EstrucAlmacenServicio_Extend> almacenServicioList;
    private List<EstrucAlmacenServicio_Extend> serviciosXAlmacenList;
    private List<EstrucAlmacenServicio_Extend> serviciosXOrdenarList;
    private List<EstrucAlmacenServicio_Extend> almacenesXOrdenarList;
    private List<TipoAreaEstructura> listTipoArea;
    private List<TipoSurtimiento> tipoSurtimientoList;
    private List<String> almacenesOrdenadosList;
    private AlmacenServicioLazy almacenServicioLazy;
    private ServicioCamasLazy servicioCamasLazy;
    private Usuario usuarioSelected;
    private PermisoUsuario permiso;
    private SesionMB sesion;
    private EstrucAlmacenServicio_Extend almacenServicioSelect;
    private CamaExtended CamaExtSelect;
    private Estructura entidadEstrucSelect;
    private EstructuraExtended estructuraServicio;
    private EstructuraExtended estructuraAlmacen;
    private EstructuraExtended servicioPadreSelect;
    private EstructuraExtended estrucServicioSelect;
    private EstructuraExtended almacenPadreSelect;
    private EstructuraExtended estrucAlmacenSelect;
    private EstructuraExtended almacenSelect;
    private Cama camaSelect;

    @Autowired
    private transient EntidadHospitalariaService entidadService;
    private List<EntidadHospitalaria> entidadList;
    private EntidadHospitalaria entidadSelect;

    @Autowired
    private transient EstructuraAlmacenServicioService almacenServicioService;

    @Autowired
    private transient EstructuraService estructuraService;

    @Autowired
    private CamaService camaService;

    @Autowired
    private transient TipoCamaService tipoCamaService;

    @Autowired
    private transient EstatusCamaService estatusCamaService;

    @Autowired
    private transient TipoAreaEstructuraService tipoAreaEstructuraService;

    @Autowired
    private transient TipoAlmacenService tipoAlmacenService;

    @Autowired
    private transient TipoSurtimientoService tipoSurtimientoService;

    @Autowired
    private transient EstructuraTipoSurtimientoService estructuraTipoSurtimiento; 
    @PostConstruct
    public void init() {
        LOGGER.debug("mx.mc.magedbean.EstructurasMB.init()");

        inicio = "Inicio";
        almacenes = "Almacenes";
        servicios = "Servicios";
        asignacion = "Asignacion";
        prioridad = "Prioridad";
        almacenServicio = "almacen";
        paramEstatus = "estatus";
        idServicioSelect="";
        tblAlmacenServicio = Constantes.ACTIVO;
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        usuarioSelected = sesion.getUsuarioSelected();
        funAlmacenServicioMultiple = sesion.isFunAsigAlmacenServicioMultiple();
        permiso = Comunes.obtenerPermisos(Transaccion_Enum.ESTRUCTURA.getSufijo());
        msjMdlEstructura = false;
        crearCama = Constantes.INACTIVO;
        crearServicio = Constantes.INACTIVO;
        crearAlmacen = Constantes.INACTIVO;
        mostrarComboPadre = Constantes.INACTIVO;
        mostrarPrioridad = Constantes.INACTIVO;
        savePrioridad = Constantes.INACTIVO;
        tipoCamaList = new ArrayList<>();
        tipoAlmacenList = new ArrayList<>();
        estatusCamaList = new ArrayList<>();
        serviciosList = new ArrayList<>();
        almacenList = new ArrayList<>();
        serviciosAsigList = new ArrayList<>();
        serviciosPadreList = new ArrayList<>();
        almacenPadreList = new ArrayList<>();
        serviciosXAlmacenList = new ArrayList<>();
        serviciosXOrdenarList = new ArrayList<>();
        almacenesXOrdenarList = new ArrayList<>();
        almacenesOrdenadosList = new ArrayList<>();
        listCamas = new ArrayList<>();
        almacenServicioList = new ArrayList<>();
        listTipoArea = new ArrayList<>();
        tipoSurtimientoList = new ArrayList<>();
        idEntidadHospitalaria = null;
        entidadEstrucSelect = new Estructura();
        estructuraServicio = new EstructuraExtended();
        estructuraAlmacen = new EstructuraExtended();
        obtenerEntidades();
        obtenerAlmacenServicio();
        obtenerListadoCamas();
        obtenerTipoAlmacenes();
        tipoArea();
        obtenerTipoSurtimiento();
        tipoSurtimientosSelected = null;
    }

    // <editor-fold defaultstate="collapsed" desc="Methods General ">
    private void obtenerEntidades() {
        try {
            entidadList = entidadService.obtenerEntidadesdHospitalActivas();
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString("estr.err.entidad"), ex);
        }
    }

    public void onTabChange(TabChangeEvent evt) {
        String valorStatus = evt.getTab().getId();
        if (valorStatus.equalsIgnoreCase(inicio)) {
            tblAlmacenServicio = Constantes.ACTIVO;
            obtenerAlmacenServicio();
        } else if (valorStatus.equalsIgnoreCase(servicios)) {
            crearCama = Constantes.INACTIVO;
            crearServicio = Constantes.INACTIVO;
            llenarArbolServicios();
            if (selectNodeServicio != null) {
                crearCama = selectNodeServicio.getChildren().isEmpty() ? Constantes.ACTIVO : Constantes.INACTIVO;
                crearServicio = !selectNodeServicio.getRowKey().equals("0") ? Constantes.ACTIVO : Constantes.INACTIVO;
                selectNodeTree(rootServicio, selectNodeServicio.getData().toString(), Constantes.ACTIVO);
            }
        } else if (valorStatus.equalsIgnoreCase(almacenes)) {
            crearAlmacen = Constantes.INACTIVO;
            llenarArbolAlmacen();
            if (selectNodeAlmacen != null) {
                crearAlmacen = !selectNodeAlmacen.getRowKey().equals("0") ? Constantes.ACTIVO : Constantes.INACTIVO;
                selectNodeTree(rootAlmacen, selectNodeAlmacen.getData().toString(), Constantes.ACTIVO);
            }
        } else if (valorStatus.equalsIgnoreCase(asignacion)) {
            llenarArbolAlmacen();
            llenarRootServicios();
            if (selectedNodeAlmacen != null) {
                selectNodeTree(rootAlmacen, selectedNodeAlmacen.getData().toString(), Constantes.ACTIVO);
                for (EstrucAlmacenServicio_Extend item : serviciosXAlmacenList) {
                    if (funAlmacenServicioMultiple) {
                        selectNodeTree(rootServicios, item.getServicio(), Constantes.ACTIVO);
                    } else {
                        selectNodeTree(rootServicio, item.getServicio(), Constantes.ACTIVO);
                    }
                    serviciosAsigList.stream().filter(prdct -> prdct.getNombre().equals(item.getServicio())).forEach(trans -> {
                        trans.setSelected(true);
                    });
                }
            }
        }else if(valorStatus.equalsIgnoreCase(prioridad)){
            
        }
    }

    public void obtenerEntidad() {
        try {
            entidadSelect = entidadList.stream().filter(p -> p.getIdEntidadHospitalaria().equals(idEntidadHospitalaria)).findAny().orElse(null);
            if (entidadSelect != null) {
                entidadEstrucSelect = estructuraService.getEstructuraForName(entidadSelect.getNombre());
                obtenerAlmacenServicio();
                llenarArbolServicios();
                llenarArbolAlmacen();
                llenarRootServicios();
                obtenerServiciosXOrdenar();
            }
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString("estr.err.estructurasEntidad"), ex);
        }
    }

    private void existNodeTree(TreeNode nodo, String name) {
        for (TreeNode node : nodo.getChildren()) {
            if (node.getData().toString().equals(name)) {
                existNode = Constantes.ACTIVO;
                break;
            } else {
                existNodeTree(node, name);
            }
        }
    }

    private void selectNodeTree(TreeNode nodo, String name, boolean status) {
        for (TreeNode node : nodo.getChildren()) {
            if (node.getData().toString().equals(name)) {
                node.setSelected(status);
                break;
            } else {
                selectNodeTree(node, name, status);
            }
        }
    }

    private void selectedTreeNode(TreeNode node, boolean estatus) {
        try {
            for (TreeNode child : node.getChildren()) {
                node.setSelected(estatus);
                if (child.getChildCount() > 0) {
                    selectedTreeNode(child, estatus);
                }
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    private void unselectListNodeTreeAll(TreeNode treeNode, boolean status) {

        for (TreeNode node : treeNode.getChildren()) {
            node.setSelected(status);
            serviciosAsigList.stream().filter(p -> p.getIdNode().equals(node.getRowKey())).forEach(a -> {
                a.setSelected(status);
            });
            if (node.getChildCount() > 0) {
                unselectListNodeTreeAll(node, status);
            }
        }
    }

    public List<EstructuraExtended> obtenerUnidadesJerarquicas(int idTipoAreaEstructura) {
        List<EstructuraExtended> listEstructura = new ArrayList<>();
        try {
            listEstructura = estructuraService.obtenerEstructurasByEntidadTipoArea(idTipoAreaEstructura, idEntidadHospitalaria);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener unidades jerarquicas: {}", ex.getMessage());
        }
        return listEstructura;
    }

    public void tipoArea() {
        try {
            List<Integer> listTipo = null;
            listTipoArea = tipoAreaEstructuraService.obtenerTodoByArea(listTipo, Constantes.TIPO_AREA_DETALLE);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener tipos de area para modal: {}", ex.getMessage());
        }
    }

    private void obtenerTipoSurtimiento() {
        try {
            tipoSurtimientoList = tipoSurtimientoService.obtenerLista(null);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    private void obtenerPadre(TreeNode nodo, EstructuraExtended tp) {

        List<TreeNode> nodoList = nodo.getChildren();
        for (TreeNode auxNodo : nodoList) {
            if (auxNodo.getData().equals(tp.getNombrePadre())) {
                TreeNode chil = new DefaultTreeNode(tp.getNombre(), auxNodo);
                chil.setExpanded(true);
                tp.setIdNodeParent(auxNodo.getRowKey());
                tp.setIdNode(chil.getRowKey());
                break;
            } else {
                obtenerPadre(auxNodo, tp);
            }
        }
    }

    public String validaFormAgregarEditarUnidad(Estructura estructura, boolean servicio, boolean insert) {
        try {
            if (estructura.getNombre().trim().isEmpty()) {
                return RESOURCES.getString("estr.err.nombreUnidad");
            }
            if (estructura.getClaveEstructura().trim().isEmpty()) {
                return RESOURCES.getString("estr.err.claveUnidad");
            }
            if (!servicio) {
                if (estructura.getIdTipoAlmacen() == null || estructura.getIdTipoAlmacen() == 0) {
                    return RESOURCES.getString("estr.err.selectTipoAlmacen");
                }
                if (tipoSurtimientosSelected == null || tipoSurtimientosSelected.length == 0) {
                    return RESOURCES.getString("estr.err.selectTipoSurtimiento");
                }
            }
            if (servicio) {
                if (estructura.getIdTipoArea()==0) {
                    return RESOURCES.getString("estr.err.selectTipoArea");
                }
                if (servicioPadreSelect == null) {
                    return RESOURCES.getString("estr.err.padreVacio");
                }
            }
            
            if (!servicio) {
                
                if (almacenPadreSelect == null) {
                    return RESOURCES.getString("estr.err.padreVacio");
                }
            }

            Estructura estructuraTmp = new Estructura();
            estructuraTmp.setNombre(estructura.getNombre());
            Estructura e = estructuraService.obtener(estructuraTmp);
            if (e != null) {
                if (!e.getIdEstructura().equals(estructura.getIdEstructura())) {
                    return RESOURCES.getString("estr.err.estructuraExistente") + " " + estructura.getNombre();
                }
            }
            estructuraTmp = new Estructura();
            estructuraTmp.setClaveEstructura(estructura.getClaveEstructura());
            e = estructuraService.obtener(estructuraTmp);
            if (e != null) {
                if (!e.getIdEstructura().equals(estructura.getIdEstructura())) {
                    return RESOURCES.getString("estr.err.estructuraClaveExistente") + " " + estructura.getClaveEstructura();
                }
            }
            

//            if (insert) {
//                String nombreEstructura = estructuraService.validarExistenciaEstrucutra(estructura.getNombre());
//                if (nombreEstructura != null) {
//                    return RESOURCES.getString("estr.err.estructuraExistente") + " " + estructura.getNombre();
//                }
//                String claveEstructura = estructuraService.validarExistenciaClave(estructura.getClaveEstructura());
//                if (claveEstructura != null) {
//                    return RESOURCES.getString("estr.err.estructuraClaveExistente") + " " + estructura.getClaveEstructura();
//                }
//            }
        } catch (Exception ex) {
            LOGGER.error("Error al validaFormAgregarEditarUnidad: {}", ex.getMessage());
        }
        return "";
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Tab Inicio ">
    public void obtenerAlmacenServicio() {
        try {
            if (!permiso.isPuedeVer()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.transaccion"), null);
                return;
            }
            if (idEntidadHospitalaria != null) {
                if (almacenServicio.equals("almacen")) {
                    tblAlmacenServicio = Constantes.ACTIVO;
                    almacenServicioLazy = new AlmacenServicioLazy(almacenServicioService, idEntidadHospitalaria);
                    LOGGER.debug("Resultados: {}", almacenServicioLazy.getTotalReg());
                } else {
                    tblAlmacenServicio = Constantes.INACTIVO;
                    servicioCamasLazy = new ServicioCamasLazy(camaService, idEntidadHospitalaria);
                    LOGGER.debug("Resultados: {}", servicioCamasLazy.getTotalReg());
                }
            }
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString("estr.err.almacenServicio"), ex);
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estr.err.almacenServicio"), null);
        }
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Tab Servicios ">
    public void onNodeServiceSelect(NodeSelectEvent event) {
        try {
            TreeNode aux = event.getTreeNode();
            estrucServicioSelect = serviciosList.stream().filter(s -> s.getIdNode().equals(aux.getRowKey())).findAny().orElse(null);
            if (selectNodeServicio != null && selectNodeServicio != aux) {
                selectNodeTree(rootServicio, selectNodeServicio.getData().toString(), Constantes.INACTIVO);
            }

            if (aux.getChildren().isEmpty()) {
                esPadre = Constantes.INACTIVO;
                if (estrucServicioSelect != null) {
                    listCamas = camaService.obtenerCamasByServicio(estrucServicioSelect.getIdEstructura());
                    if (permiso.isPuedeCrear() || permiso.isPuedeEditar()) {
                        crearCama = Constantes.ACTIVO;
                    }
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estr.err.nivelInorrecto"), null);
                }
            } else {
                esPadre = Constantes.ACTIVO;
                crearCama = Constantes.INACTIVO;
                listCamas = new ArrayList<>();
            }

            titleServicio = estrucServicioSelect.getIdTipoAreaEstructura().equals(TipoAreaEstructura_Enum.CONSULTAEXTERNA.getValue()) ? "Consultorio" : "Servicio";

            crearServicio = aux.getRowKey().equals("0") ? Constantes.INACTIVO : Constantes.ACTIVO;
            selectNodeServicio = aux;
            selectNodeServicio.setSelected(Constantes.ACTIVO);
        } catch (Exception e) {
            LOGGER.error("Error al seleccionar el nodo: {}", e.getMessage());
        }
    }

    public void onNodeServiceUnselect(NodeUnselectEvent event) {

    }

    public void crearServicio() {
        boolean status = Constantes.INACTIVO;

        estructuraServicio = new EstructuraExtended();
        if (estrucServicioSelect != null) {
            estructuraServicio.setIdEstructuraPadre(estrucServicioSelect.getIdEstructura());
            estructuraServicio.setNombrePadre(estrucServicioSelect.getNombre());
            servicioPadreSelect = serviciosList.stream().filter(p -> p.getIdEstructura().equals(estrucServicioSelect.getIdEstructura())).findAny().orElse(null);
        }
        servicioActivo = estrucServicioSelect.getActiva() == 1;
        insertaServicio = Constantes.ACTIVO;
        status = true;

        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    public void obtenerServicio() {
        boolean status = Constantes.INACTIVO;
        if (estrucServicioSelect != null) {
            estructuraServicio = estrucServicioSelect;
            servicioActivo = estrucServicioSelect.getActiva() == 1;
            status = Constantes.ACTIVO;
            insertaServicio = Constantes.INACTIVO;
            servicioPadreSelect = serviciosList.stream().filter(p -> p.getIdEstructura().equals(estrucServicioSelect.getIdEstructuraPadre())).findAny().orElse(null);
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    public void guardarServicio() {
        boolean status = Constantes.INACTIVO;
        if (!this.permiso.isPuedeCrear()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estr.err.permisos"), null);
            return;
        }
        String valida = validaFormAgregarEditarUnidad(estructuraServicio, Constantes.ACTIVO, insertaServicio);
        if (!valida.isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, valida, null);
            return;
        }
        estructuraServicio.setDescripcion(estructuraServicio.getNombre());
        estructuraServicio.setIdTipoAlmacen(1);
        estructuraServicio.setActiva(servicioActivo ? 1 : 0);
        estructuraServicio.setIdEstructuraPadre(servicioPadreSelect.getIdEstructura());
        estructuraServicio.setIdTipoAreaEstructura(servicioPadreSelect.getIdTipoAreaEstructura());

        if (insertaServicio) {

            estructuraServicio.setIdEntidadHospitalaria(buscaEntidadActiva());
            estructuraServicio.setIdEntidadHospitalaria(idEntidadHospitalaria);
            estructuraServicio.setIdEstructura(Comunes.getUUID());
            estructuraServicio.setInsertFecha(new Date());
            estructuraServicio.setInsertIdUsuario(usuarioSelected.getIdUsuario());
            try {
                if (estructuraService.insertar(estructuraServicio)) {
                    status = Constantes.ACTIVO;
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("estr.info.guardarUnidad"), null);
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estr.err.guardarUnidad"), null);
                }
            } catch (Exception e) {
                LOGGER.error("Error al ingresar un nuevo elemento al arbol: {}", e.getMessage());
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estr.err.agregarElementoArbol"), null);
            }
        } else {
            estructuraServicio.setUpdateFecha(new Date());
            estructuraServicio.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
            try {
                if (estructuraService.actualizar(estructuraServicio)) {
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("estr.info.actualizaUnidad"), null);
                    status = Constantes.ACTIVO;
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estr.err.actualizaUnidad"), null);
                }
            } catch (Exception e) {
                LOGGER.error("Error al actualizar una unidad del arbol: {}", e.getMessage());
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Probablemente ya existe un Registro con el nombre ", null);
            }
        }
        if (status) {
            llenarArbolServicios();
            selectNodeServicio = null;
            selectedNodeServicio = null;
            estrucServicioSelect = null;
            servicioPadreSelect = null;
            crearServicio = false;
        }
        PrimeFaces.current().ajax().addCallbackParam("estatus", status);
    }

    public void obtenerCama(String idCama) {
        insertaCama = false;
        if (!idCama.isEmpty()) {
            try {
                camaSelect = camaService.obtenerCama(idCama);
                idTipoCama = camaSelect.getIdTipoCama();
                idEstatusCama = camaSelect.getIdEstatusCama();
            } catch (Exception e) {
                LOGGER.error("Error al obtener la cama: {}", e.getMessage());
            }
        }
    }

    public void crearCama() {
        boolean status = Constantes.INACTIVO;
        if (esPadre) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estr.err.camaHospital"), null);
        } else {
            camaSelect = new Cama();
            if (estrucServicioSelect != null) {
                camaSelect.setUbicacion(estrucServicioSelect.getUbicacion());
            }
            idEstatusCama = 0;
            idTipoCama = 0;
            insertaCama = true;
            status = true;
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    public void guardarCama() {
        boolean status = Constantes.INACTIVO;
        if (!this.permiso.isPuedeCrear()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estr.err.permisos"), null);
            return;
        }
        String valida = validarFormCama();
        if (!valida.isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, valida, null);
            status = Constantes.INACTIVO;
            return;
        }

        camaSelect.setIdEstructura(estrucServicioSelect.getIdEstructura());
        camaSelect.setIdEstatusCama(idEstatusCama);
        camaSelect.setIdTipoCama(idTipoCama);

        if (insertaCama) {
            if (existeCama()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El nombre de la cama ya existe en este servicio", null);
                status = Constantes.INACTIVO;
                return;
            }
            camaSelect.setIdCama(Comunes.getUUID());
            camaSelect.setInsertIdUsuario(usuarioSelected.getIdUsuario());
            camaSelect.setInsertFecha(new Date());
            try {
                if (camaService.insertar(camaSelect)) {
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("estr.info.guardarCama"), null);
                    status = Constantes.ACTIVO;
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estr.err.guardarCama"), null);
                    status = Constantes.INACTIVO;
                }
            } catch (Exception e) {
                LOGGER.error("Error al desactivar una unidad del arbol: {}", e.getMessage());
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estr.err.desactivaUnidad"), null);
                status = Constantes.ACTIVO;
            }

        } else {
            camaSelect.setUpdateFecha(new Date());
            camaSelect.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
            try {
                Cama camaExiste = camaService.obterCamaPorNombreYEstructura(camaSelect.getNombreCama(), estrucServicioSelect.getIdEstructura());
                if (camaExiste != null) {
                    if (!camaExiste.getIdCama().equals(camaSelect.getIdCama())) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El nombre de la cama ya existe en este servicio", null);
                        status = Constantes.INACTIVO;
                        return;
                    }
                }
                if (camaService.actualizar(camaSelect)) {
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("estr.info.actualizaCama"), null);
                    status = Constantes.ACTIVO;
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estr.err.actualizaCama"), null);
                    status = Constantes.INACTIVO;
                }
            } catch (Exception e) {
                LOGGER.error("Error al actualizar la cama: {}", e.getMessage());
                status = Constantes.ACTIVO;
            }
        }

        if (status) {
            try {
                listCamas = camaService.obtenerCamasByServicio(estrucServicioSelect.getIdEstructura());
            } catch (Exception e) {
                LOGGER.error("Error al obtener la lista de las camas: {}", e.getMessage());
            }
        }

        PrimeFaces.current().ajax().addCallbackParam("estatus2", status);
    }

    public String validarFormCama() {
        if (this.camaSelect.getNombreCama().isEmpty()) {
            return RESOURCES.getString("estr.err.cama");
        }
        if (idTipoCama == null || idTipoCama == 0) {
            return RESOURCES.getString("estr.err.tipoCama");
        }
        if (idEstatusCama == null || idEstatusCama == 0) {
            return RESOURCES.getString("estr.err.estadoCama");
        }

        return "";
    }

    public boolean existeCama() {
        try {
            Cama camaExiste = camaService.obterCamaPorNombreYEstructura(camaSelect.getNombreCama(), estrucServicioSelect.getIdEstructura());
            if (camaExiste != null) {
                return true;
            }
        } catch (Exception ex) {
            LOGGER.error("Error al validar la existencia de la cama, metodo : existeCama : linea 1149 UnidadesJerarquicasMB");
        }
        return false;
    }

    public void llenarArbolServicios() {
        try {
            serviciosPadreList = estructuraService.obtenerPorTipoAreayTipoAreaEstructura(TipoAreaEstructura_Enum.AREA.getValue(), null);
            serviciosPadreList.remove(entidadEstrucSelect);
            serviciosList = obtenerUnidadesJerarquicas(Constantes.TIPO_AREA_SERVICIO);
            rootServicio = new DefaultTreeNode("Root", null);
            TreeNode node = new DefaultTreeNode(entidadEstrucSelect.getNombre(), rootServicio);
            node.setExpanded(true);
            for (EstructuraExtended tp : serviciosList) {
                existNode = false;
                existNodeTree(rootServicio, tp.getNombrePadre());
                if (!existNode) {
                    TreeNode chil = new DefaultTreeNode(tp.getNombre(), node);
                    tp.setIdNodeParent(node.getRowKey());
                    tp.setIdNode(chil.getRowKey());
                } else {
                    obtenerPadre(rootServicio, tp);
                }
            }
        } catch (Exception exc) {
            LOGGER.error("Error al obtener unidades jerarquicas: {}", exc.getMessage());
        }
    }

    public void obtenerListadoCamas() {
        try {
            tipoCamaList = tipoCamaService.obtenerTodo();
            estatusCamaList = estatusCamaService.obtenerTodo();
        } catch (Exception e) {
            LOGGER.error("Ocurrio un error al obtener los listados de camas: {}", e.getMessage());
        }

    }

    public void asignarPadre() {
        if (estructuraServicio != null) {
            servicioPadreSelect = serviciosList.stream().filter(p -> p.getIdEstructura().equals(estructuraServicio.getIdEstructuraPadre())).findAny().orElse(null);
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Tab Almacenes ">
    public void onNodeAlmacenSelect(NodeSelectEvent event) {
        try {
            TreeNode aux = event.getTreeNode();
            if(aux != selectNodeAlmacen){
                almacenServicioList = new ArrayList<>();

                estrucAlmacenSelect = almacenList.stream().filter(s -> s.getIdNode().equals(aux.getRowKey())).findAny().orElse(null);
                if (selectNodeAlmacen != null && selectNodeAlmacen != aux) {
                    selectNodeTree(rootAlmacen, selectNodeAlmacen.getData().toString(), Constantes.INACTIVO);
                }

                if (estrucAlmacenSelect != null) {
                    almacenServicioList = almacenServicioService.obtenerServiciosOfAlmacen(estrucAlmacenSelect.getIdEstructura());
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estr.err.nivelInorrecto"), null);
                }

                if (permiso.isPuedeCrear() || permiso.isPuedeEditar()) {
                    crearAlmacen = aux.getRowKey().equals("0") ? Constantes.INACTIVO : Constantes.ACTIVO;
                }

                esPadre = aux.getChildren().isEmpty() ? Constantes.INACTIVO : Constantes.ACTIVO;
                selectNodeAlmacen = aux;
                selectNodeAlmacen.setSelected(Constantes.ACTIVO);
            }else{
                almacenServicioList = new ArrayList<>();
                selectNodeTree(rootAlmacen, aux.getData().toString(), Constantes.INACTIVO);
                estrucAlmacenSelect=null;
                selectNodeAlmacen=null;
            }
        } catch (Exception e) {
            LOGGER.error("Error al seleccionar el nodo: {}", e.getMessage());
        }
    }

    public void onNodeAlmacenUnselect(NodeUnselectEvent event) {  
        almacenServicioList = new ArrayList<>();
        selectNodeTree(rootAlmacen, selectNodeAlmacen.getData().toString(), Constantes.INACTIVO);
        estrucAlmacenSelect=null;
        selectNodeAlmacen=null;
    }

    public void obtenerAlmacen() {
        boolean status = Constantes.INACTIVO;
        try {
            if (estrucAlmacenSelect != null) {
                estructuraAlmacen = estrucAlmacenSelect;
                almacenActivo = estrucAlmacenSelect.getActiva() == 1;
                mostrarComboPadre = esPadre ? Constantes.INACTIVO : Constantes.ACTIVO;
                status = Constantes.ACTIVO;
                insertaAlmacen = Constantes.INACTIVO;
                tipoSurtimientosSelected= estructuraTipoSurtimiento.obtenerIdAlmacen(estrucAlmacenSelect.getIdEstructura());
                almacenPadreList = estructuraService.obtenerPorTipoAlmacen(TipoAlmacen_Enum.ALMACEN.getValue(), entidadSelect.getIdEntidadHospitalaria());
                almacenPadreList.addAll(estructuraService.obtenerPorTipoAlmacen(TipoAlmacen_Enum.FARMACIA.getValue(), entidadSelect.getIdEntidadHospitalaria()));
                almacenPadreList.addAll(estructuraService.obtenerPorTipoAlmacen(TipoAlmacen_Enum.SUBALMACEN.getValue(), entidadSelect.getIdEntidadHospitalaria()));
                for (Estructura almacen : almacenPadreList)
                    if (almacen.getIdEstructura().equals(estrucAlmacenSelect.getIdEstructura())) {
                        almacenPadreList.remove(almacen);
                        break;
                    }
                almacenPadreSelect = almacenList.stream().filter(p -> p.getIdEstructura().equals(estrucAlmacenSelect.getIdEstructuraPadre())).findAny().orElse(null);
            }
        } catch (Exception e) {
            LOGGER.error("Error al obtener el almacen: {}", e.getMessage());
        }
        
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    public void crearAlmacen() {
        boolean status = Constantes.ACTIVO;
        estructuraAlmacen = new EstructuraExtended();
        tipoSurtimientosSelected = null;
        if (estrucAlmacenSelect != null) {
            estructuraAlmacen.setIdEstructuraPadre(estrucAlmacenSelect.getIdEstructura());
            estructuraAlmacen.setNombrePadre(estrucAlmacenSelect.getNombre());
            almacenPadreSelect = estrucAlmacenSelect; // almacenList.stream().filter(p -> p.getIdEstructura().equals(estrucAlmacenSelect.getIdEstructuraPadre())).findAny().orElse(null);
        }
        
        mostrarComboPadre = esPadre ? Constantes.INACTIVO : Constantes.ACTIVO;
        almacenActivo = estrucAlmacenSelect.getActiva() == 1;
        insertaAlmacen = Constantes.ACTIVO;
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }
    
    private String buscaEntidadActiva(){
        String res = null;
        if (entidadList!= null){
            for (EntidadHospitalaria item : entidadList) {
                if (item.getEstatus()) {
                    res = item.getIdEntidadHospitalaria();
                }
            }
        }
        return res;
    }

    public void guardarAlmacen() {
        boolean status = Constantes.INACTIVO;
        if (!this.permiso.isPuedeCrear()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estr.err.permisos"), null);
            return;
        }
//        almacenPadreSelect = estrucAlmacenSelect;

        String valida = validaFormAgregarEditarUnidad(estructuraAlmacen, Constantes.INACTIVO, insertaAlmacen);
        if (!valida.isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, valida, null);
            return;
        }
        estructuraAlmacen.setDescripcion(estructuraAlmacen.getNombre());
        estructuraAlmacen.setActiva(almacenActivo ? Constantes.ESTATUS_ACTIVO : Constantes.ESTATUS_INACTIVO);
        estructuraAlmacen.setIdTipoAreaEstructura(TipoAreaEstructura_Enum.ALMACEN.getValue());        
        if (insertaAlmacen) {
            estructuraAlmacen.setIdEntidadHospitalaria(buscaEntidadActiva());
            estructuraAlmacen.setIdEstructura(Comunes.getUUID());
            estructuraAlmacen.setInsertFecha(new Date());
            estructuraAlmacen.setInsertIdUsuario(usuarioSelected.getIdUsuario());
            try {
                if (estructuraService.insertarAlmacen(estructuraAlmacen,tipoSurtimientosSelected)) {
                    status = Constantes.ACTIVO;
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("estr.info.guardarUnidad"), null);
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estr.err.guardarUnidad"), null);
                }
            } catch (Exception e) {
                LOGGER.error("Error al ingresar un nuevo elemento al arbol: {}", e.getMessage());
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estr.err.agregarElementoArbol"), null);
            }
        } else {
            estructuraAlmacen.setUpdateFecha(new Date());
            estructuraAlmacen.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
            try {
                if (estructuraService.actualizarAlmacen(estructuraAlmacen,tipoSurtimientosSelected)) {
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("estr.info.actualizaUnidad"), null);
                    status = Constantes.ACTIVO;
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estr.err.actualizaUnidad"), null);
                }
            } catch (Exception e) {
                LOGGER.error("Error al actualizar una unidad del arbol: {}", e.getMessage());
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estr.err.agregarElementoArbol"), null);
            }
        }
        
        if (status) {
            llenarArbolAlmacen();
            selectNodeAlmacen = null;
            selectedNodeAlmacen = null;
            estrucAlmacenSelect = null;
            almacenPadreSelect = null;
            tipoSurtimientosSelected = null;
            crearAlmacen = false;
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    private void llenarArbolAlmacen() {
        try {
            almacenPadreList = estructuraService.obtenerPorTipoAlmacen(TipoAlmacen_Enum.ALMACEN.getValue(), entidadSelect.getIdEntidadHospitalaria());
            almacenPadreList.remove(entidadEstrucSelect);
            almacenList = obtenerUnidadesJerarquicas(Constantes.TIPO_AREA_ALMACEN);
            rootAlmacen = new DefaultTreeNode("Root", null);
            TreeNode node = new DefaultTreeNode(entidadEstrucSelect.getNombre(), rootAlmacen);
            node.setExpanded(true);
            for (EstructuraExtended tp : almacenList) {
                existNode = false;
                existNodeTree(rootAlmacen, tp.getNombrePadre());
                if (!existNode) {
                    TreeNode chil = new DefaultTreeNode(tp.getNombre(), node);
                    tp.setIdNodeParent(node.getRowKey());
                    tp.setIdNode(chil.getRowKey());
                } else {
                    obtenerPadre(rootAlmacen, tp);
                }
            }
        } catch (Exception exc) {
            LOGGER.error("Error al obtener unidades jerarquicas: {}", exc.getMessage());
        }
    }

    private void obtenerTipoAlmacenes() {
        try {
            tipoAlmacenList = tipoAlmacenService.obtenerTiposAlmacen();
        } catch (Exception e) {
            LOGGER.error("Ocurrio un error al obtener listado de tipos de Almacen: {}", e.getMessage());
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Tab Asignacion ">        
    public void onNodeAsigAlmacenSelect(NodeSelectEvent event) {
        try {
            TreeNode aux = event.getTreeNode();
            serviciosXAlmacenList = new ArrayList<>();
            unselectListNodeTreeAll(rootAlmacen, Constantes.INACTIVO);

            if (funAlmacenServicioMultiple) {
                unselectListNodeTreeAll(rootServicios, Constantes.INACTIVO);
            } else {
                unselectListNodeTreeAll(rootServicio, Constantes.INACTIVO);
            }

            almacenSelect = almacenList.stream().filter(s -> s.getIdNode().equals(aux.getRowKey())).findAny().orElse(null);
            if (almacenSelect != null) {
                serviciosXAlmacenList = almacenServicioService.obtenerServiciosOfAlmacen(almacenSelect.getIdEstructura());
                for (EstrucAlmacenServicio_Extend item : serviciosXAlmacenList) {
                    if (funAlmacenServicioMultiple) {
                        selectNodeTree(rootServicios, item.getServicio(), Constantes.ACTIVO);
                    } else {
                        selectNodeTree(rootServicio, item.getServicio(), Constantes.ACTIVO);
                    }
                    serviciosAsigList.stream().filter(prdct -> prdct.getNombre().equals(item.getServicio())).forEach(trans -> {
                        trans.setSelected(true);
                    });
                }
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estr.err.nivelInorrecto"), null);
            }

            selectedNodeAlmacen = aux;
            selectedNodeAlmacen.setSelected(Constantes.ACTIVO);
        } catch (Exception e) {
            LOGGER.error("Error al seleccionar el nodo: {}", e.getMessage());
        }
    }

    public void onNodeAsigAlmacenUnselect(NodeUnselectEvent event) {
        try {
            TreeNode aux = event.getTreeNode();
            if (aux.equals(selectedNodeAlmacen)) {
                selectedNodeAlmacen = null;
                serviciosXAlmacenList = new ArrayList<>();
                unselectListNodeTreeAll(rootServicios, Constantes.INACTIVO);
            }
        } catch (Exception e) {
            LOGGER.error("Error al seleccionar el nodo: {}", e.getMessage());
        }
    }

    public void onNodeMultServiceSelect(NodeSelectEvent event) {
        try {
            TreeNode aux = event.getTreeNode();
            if (selectedNodeAlmacen != null) {                
                serviciosAsigList.stream().filter(prdct -> prdct.getIdNode().equals(aux.getRowKey())).forEach(trans -> {
                    trans.setSelected(true);                    
                });
                
                if (aux.getChildCount() > 0) {
                    unselectListNodeTreeAll(aux, true);
                }

                selectedTreeNode(rootServicios, Constantes.INACTIVO);
                serviciosAsigList.stream().filter(p->p.isSelected()).forEach(a->{
                    selectNodeTree(rootServicios, a.getNombre(), Constantes.ACTIVO);
                });
                
            } else {
                unselectListNodeTreeAll(rootServicios, false);
                Mensaje.showMessage(Constantes.MENSAJE_WARN, "Primero seleccione Almacen", null);
            }
        } catch (Exception e) {
            LOGGER.error("Error al seleccionar el nodo: {}", e.getMessage());
        }
    }

    public void onNodeMultAsigServiceUnselect(NodeUnselectEvent event) {
        try {
            TreeNode aux = event.getTreeNode();
            if (selectedNodeAlmacen != null) {
                serviciosAsigList.stream().filter(prdct -> prdct.getIdNode().equals(aux.getRowKey())).forEach(trans -> {
                    trans.setSelected(false);
                    selectNodeTree(rootServicios,aux.getData().toString(),false);
                });
                if (aux.getChildCount() > 0) {
                    unselectListNodeTreeAll(aux, false);
                }
                
            } else {
                unselectListNodeTreeAll(rootServicios, false);
            }
        } catch (Exception e) {
            LOGGER.error("Error al seleccionar el nodo: {}", e.getMessage());
        }
    }

    public void onNodeSingAsigServiceSelect(NodeSelectEvent event) {
        try {
            unselectListNodeTreeAll(rootServicios, false);
            if (selectedNodeAlmacen != null) {
                TreeNode aux = event.getTreeNode();
                serviciosAsigList.stream().filter(prdct -> prdct.getIdNode().equals(aux.getRowKey())).forEach(trans -> {
                    trans.setSelected(true);
                });
                selectedNodeServicio = aux;
                selectNodeTree(rootServicio, selectedNodeServicio.getData().toString(), true);
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_WARN, "Primero seleccione Almacen", null);
            }
        } catch (Exception e) {
            LOGGER.error("Error al seleccionar el nodo: {}", e.getMessage());
        }
    }

    public void onNodeSingServiceUnselect(NodeUnselectEvent event) {
        try {
            TreeNode aux = event.getTreeNode();
            serviciosAsigList.stream().filter(prdct -> prdct.getIdNode().equals(aux.getRowKey())).forEach(trans -> {
                trans.setSelected(false);
            });
            selectedNodeServicio = null;
            selectNodeTree(rootServicio, aux.getData().toString(), true);
        } catch (Exception e) {
            LOGGER.error("Error al seleccionar el nodo: {}", e.getMessage());
        }
    }

    public void guardarAsignacion() {
        boolean resp=false;
        try {
            List<EstrucAlmacenServicio_Extend> serviciosXAlmacenList = new ArrayList<>();
            serviciosAsigList.stream().filter(p -> p.isSelected()).forEach(serv -> {
                EstrucAlmacenServicio_Extend estruc = new EstrucAlmacenServicio_Extend();
                estruc.setIdAlmacenServicio(Comunes.getUUID());
                estruc.setIdEstructuraAlmacen(almacenSelect.getIdEstructura());
                estruc.setIdEstructuraServicio(serv.getIdEstructura());

                serviciosXAlmacenList.add(estruc);
            });

//            if (serviciosXAlmacenList.size() > 0) {
//                almacenServicioService.eliminarAlmacenServicioIdAlmacen(almacenSelect.getIdEstructura());
                if(almacenServicioService.insertarAlmacenServicioList(almacenSelect.getIdEstructura(), serviciosXAlmacenList)){
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, "Se guardaron los datos correctamente", null);
                }
//            }
        } catch (Exception e) {
            LOGGER.error("Error al guardar los cambios: {}", e.getMessage());
        }
    }

    private void llenarRootServicios() {
        try {
            serviciosAsigList = obtenerUnidadesJerarquicas(Constantes.TIPO_AREA_SERVICIO);
            rootServicios = new DefaultTreeNode("root", null);
            TreeNode node = new DefaultTreeNode(entidadEstrucSelect.getNombre(), rootServicios);
            node.setExpanded(true);
            for (EstructuraExtended tp : serviciosAsigList) {
                existNode = false;
                existNodeTree(rootServicios, tp.getNombrePadre());
                if (!existNode) {
                    TreeNode chil = new DefaultTreeNode(tp.getNombre(), node);
                    tp.setIdNodeParent(node.getRowKey());
                    tp.setIdNode(chil.getRowKey());
                } else {
                    obtenerPadre(rootServicios, tp);
                }
            }
        } catch (Exception exc) {
            LOGGER.error("Error al obtener unidades jerarquicas: {}", exc.getMessage());
        }
    }
    // </editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Tab Prioridad ">
    private void obtenerServiciosXOrdenar(){
        try {
            serviciosXOrdenarList = almacenServicioService.obtenerServiciosXOrdenar();
            if(serviciosXOrdenarList.size()>0)
                mostrarPrioridad=true;
        } catch (Exception e) {
        }            
    }
    
    public void llenarListaAlmacenes(){
        try {
            if(!idServicioSelect.isEmpty()){
                almacenesXOrdenarList = almacenServicioService.obtenerAlmacenesOfServicio(idServicioSelect);
                almacenesOrdenadosList.clear();
                almacenesXOrdenarList.forEach((item) -> {            
                    almacenesOrdenadosList.add(item.getAlmacen());
                });
            }
        } catch (Exception e) {
        }
            
    }
    
    public void guardarPrioridad(){
        try {
            boolean save=false;
            suma=1;        
            for(String item : almacenesOrdenadosList){            
                almacenesXOrdenarList.forEach(a ->{
                    if(a.getAlmacen().equals(item)){
                        a.setPrioridadSurtir(suma);
                    }
                });            
                LOGGER.debug("Servicio: "+almacenesXOrdenarList.get(0).getServicio()+" ->  Almacen:"+ item+" idx:"+suma );
                suma++;
            }
            
            save = almacenServicioService.guardarPrioridadAlmacenes(almacenesXOrdenarList);
            if(save){                
                Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("estr.info.actualizarPrioridad"), null);
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estr.err.actualizarPrioridad"), null);
            }
        } catch (Exception e) {
            LOGGER.error("Error al guardar la prioridad de surtimiento de almacenes pot servicio {} ", e.getMessage());
        }
    
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Getters and Setters ">
    public Estructura getEntidadEstrucSelect() {
        return entidadEstrucSelect;
    }

    public void setEntidadEstrucSelect(Estructura entidadEstrucSelect) {
        this.entidadEstrucSelect = entidadEstrucSelect;
    }

    public List<EntidadHospitalaria> getEntidadList() {
        return entidadList;
    }

    public void setEntidadList(List<EntidadHospitalaria> entidadList) {
        this.entidadList = entidadList;
    }

    public String getIdEntidadHospitalaria() {
        return idEntidadHospitalaria;
    }

    public void setIdEntidadHospitalaria(String idEntidadHospitalaria) {
        this.idEntidadHospitalaria = idEntidadHospitalaria;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getServicios() {
        return servicios;
    }

    public void setServicios(String servicios) {
        this.servicios = servicios;
    }

    public String getAlmacenes() {
        return almacenes;
    }

    public void setAlmacenes(String almacenes) {
        this.almacenes = almacenes;
    }

    public String getAsignacion() {
        return asignacion;
    }

    public void setAsignacion(String asignacion) {
        this.asignacion = asignacion;
    }

    public String getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(String idGrupo) {
        this.idGrupo = idGrupo;
    }

    public AlmacenServicioLazy getAlmacenServicioLazy() {
        return almacenServicioLazy;
    }

    public void setAlmacenServicioLazy(AlmacenServicioLazy almacenServicioLazy) {
        this.almacenServicioLazy = almacenServicioLazy;
    }

    public EstrucAlmacenServicio_Extend getAlmacenServicioSelect() {
        return almacenServicioSelect;
    }

    public void setAlmacenServicioSelect(EstrucAlmacenServicio_Extend almacenServicioSelect) {
        this.almacenServicioSelect = almacenServicioSelect;
    }

    public String getAlmacenServicio() {
        return almacenServicio;
    }

    public void setAlmacenServicio(String almacenServicio) {
        this.almacenServicio = almacenServicio;
    }

    public ServicioCamasLazy getServicioCamasLazy() {
        return servicioCamasLazy;
    }

    public void setServicioCamasLazy(ServicioCamasLazy servicioCamasLazy) {
        this.servicioCamasLazy = servicioCamasLazy;
    }

    public CamaExtended getCamaExtSelect() {
        return CamaExtSelect;
    }

    public void setCamaExtSelect(CamaExtended CamaExtSelect) {
        this.CamaExtSelect = CamaExtSelect;
    }

    public boolean isMsjMdlEstructura() {
        return msjMdlEstructura;
    }

    public void setMsjMdlEstructura(boolean msjMdlEstructura) {
        this.msjMdlEstructura = msjMdlEstructura;
    }

    public boolean isTblAlmacenServicio() {
        return tblAlmacenServicio;
    }

    public void setTblAlmacenServicio(boolean tblAlmacenServicio) {
        this.tblAlmacenServicio = tblAlmacenServicio;
    }

    public TreeNode getRootServicio() {
        return rootServicio;
    }

    public void setRootServicio(TreeNode rootServicio) {
        rootServicio = rootServicio;
    }

    public TreeNode getRootAlmacen() {
        return rootAlmacen;
    }

    public void setRootAlmacen(TreeNode rootAlmacen) {
        rootAlmacen = rootAlmacen;
    }

    public TreeNode getSelectNodeServicio() {
        return selectNodeServicio;
    }

    public void setSelectNodeServicio(TreeNode selectNodeServicio) {
        selectNodeServicio = selectNodeServicio;
    }

    public TreeNode getSelectNodeAlmacen() {
        return selectNodeAlmacen;
    }

    public void setSelectNodeAlmacen(TreeNode selectNodeAlmacen) {
        selectNodeAlmacen = selectNodeAlmacen;
    }

    public TreeNode[] getSelectedNodesServicio() {
        return selectedNodesServicio;
    }

    public void setSelectedNodesServicio(TreeNode[] selectedNodesServicio) {
        selectedNodesServicio = selectedNodesServicio;
    }

    public List<CamaExtended> getListCamas() {
        return listCamas;
    }

    public void setListCamas(List<CamaExtended> listCamas) {
        this.listCamas = listCamas;
    }

    public boolean isRenderPanelConsulta() {
        return renderPanelConsulta;
    }

    public void setRenderPanelConsulta(boolean renderPanelConsulta) {
        this.renderPanelConsulta = renderPanelConsulta;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

    public Cama getCamaSelect() {
        return camaSelect;
    }

    public void setCamaSelect(Cama camaSelect) {
        this.camaSelect = camaSelect;
    }

    public Integer getIdTipoCama() {
        return idTipoCama;
    }

    public void setIdTipoCama(Integer idTipoCama) {
        this.idTipoCama = idTipoCama;
    }

    public boolean isInsertaCama() {
        return insertaCama;
    }

    public void setInsertaCama(boolean insertaCama) {
        this.insertaCama = insertaCama;
    }

    public List<TipoCama> getTipoCamaList() {
        return tipoCamaList;
    }

    public void setTipoCamaList(List<TipoCama> tipoCamaList) {
        this.tipoCamaList = tipoCamaList;
    }

    public List<EstatusCama> getEstatusCamaList() {
        return estatusCamaList;
    }

    public void setEstatusCamaList(List<EstatusCama> estatusCamaList) {
        this.estatusCamaList = estatusCamaList;
    }

    public Integer getIdEstatusCama() {
        return idEstatusCama;
    }

    public void setIdEstatusCama(Integer idEstatusCama) {
        this.idEstatusCama = idEstatusCama;
    }
    
    public boolean isCrearCama() {
        return crearCama;
    }

    public void setCrearCama(boolean crearCama) {
        this.crearCama = crearCama;
    }

    public boolean isCrearServicio() {
        return crearServicio;
    }

    public void setCrearServicio(boolean crearServicio) {
        this.crearServicio = crearServicio;
    }

    public EstructuraExtended getEstructuraServicio() {
        return estructuraServicio;
    }

    public void setEstructuraServicio(EstructuraExtended estructuraServicio) {
        this.estructuraServicio = estructuraServicio;
    }

    public List<TipoAreaEstructura> getListTipoArea() {
        return listTipoArea;
    }

    public void setListTipoArea(List<TipoAreaEstructura> listTipoArea) {
        this.listTipoArea = listTipoArea;
    }

    public boolean isServicioActivo() {
        return servicioActivo;
    }

    public void setServicioActivo(boolean servicioActivo) {
        this.servicioActivo = servicioActivo;
    }

    public List<EstructuraExtended> getServiciosList() {
        return serviciosList;
    }

    public void setServiciosList(List<EstructuraExtended> serviciosList) {
        this.serviciosList = serviciosList;
    }

    public EstructuraExtended getServicioPadreSelect() {
        return servicioPadreSelect;
    }

    public void setServicioPadreSelect(EstructuraExtended servicioPadreSelect) {
        this.servicioPadreSelect = servicioPadreSelect;
    }

    public List<Estructura> getServiciosPadreList() {
        return serviciosPadreList;
    }

    public void setServiciosPadreList(List<Estructura> serviciosPadreList) {
        this.serviciosPadreList = serviciosPadreList;
    }

    public String getTitleServicio() {
        return titleServicio;
    }

    public void setTitleServicio(String titleServicio) {
        this.titleServicio = titleServicio;
    }

    public List<EstructuraExtended> getAlmacenList() {
        return almacenList;
    }

    public void setAlmacenList(List<EstructuraExtended> almacenList) {
        this.almacenList = almacenList;
    }

    public List<EstrucAlmacenServicio_Extend> getAlmacenServicioList() {
        return almacenServicioList;
    }

    public void setAlmacenServicioList(List<EstrucAlmacenServicio_Extend> almacenServicioList) {
        this.almacenServicioList = almacenServicioList;
    }

    public boolean isCrearAlmacen() {
        return crearAlmacen;
    }

    public void setCrearAlmacen(boolean crearAlmacen) {
        this.crearAlmacen = crearAlmacen;
    }

    public boolean isMostrarComboPadre() {
        return mostrarComboPadre;
    }

    public void setMostrarComboPadre(boolean mostrarComboPadre) {
        this.mostrarComboPadre = mostrarComboPadre;
    }

    public EstructuraExtended getEstructuraAlmacen() {
        return estructuraAlmacen;
    }

    public void setEstructuraAlmacen(EstructuraExtended estructuraAlmacen) {
        this.estructuraAlmacen = estructuraAlmacen;
    }

    public boolean isAlmacenActivo() {
        return almacenActivo;
    }

    public void setAlmacenActivo(boolean almacenActivo) {
        this.almacenActivo = almacenActivo;
    }

    public List<Estructura> getAlmacenPadreList() {
        return almacenPadreList;
    }

    public void setAlmacenPadreList(List<Estructura> almacenPadreList) {
        this.almacenPadreList = almacenPadreList;
    }

    public boolean isTransAutomatica() {
        return transAutomatica;
    }

    public void setTransAutomatica(boolean transAutomatica) {
        this.transAutomatica = transAutomatica;
    }

    public List<TipoAlmacen> getTipoAlmacenList() {
        return tipoAlmacenList;
    }

    public void setTipoAlmacenList(List<TipoAlmacen> tipoAlmacenList) {
        this.tipoAlmacenList = tipoAlmacenList;
    }

    public boolean isFunAlmacenServicioMultiple() {
        return funAlmacenServicioMultiple;
    }

    public void setFunAlmacenServicioMultiple(boolean funAlmacenServicioMultiple) {
        this.funAlmacenServicioMultiple = funAlmacenServicioMultiple;
    }

    public TreeNode getSelectedNodeServicio() {
        return selectedNodeServicio;
    }

    public void setSelectedNodeServicio(TreeNode selectedNodeServicio) {
        this.selectedNodeServicio = selectedNodeServicio;
    }

    public TreeNode getSelectedNodeAlmacen() {
        return selectedNodeAlmacen;
    }

    public void setSelectedNodeAlmacen(TreeNode selectedNodeAlmacen) {
        this.selectedNodeAlmacen = selectedNodeAlmacen;
    }

    public TreeNode getRootServicios() {
        return rootServicios;
    }

    public void setRootServicios(TreeNode rootServicios) {
        this.rootServicios = rootServicios;
    }

    public String getIdTipoSurtimiento() {
        return idTipoSurtimiento;
    }

    public void setIdTipoSurtimiento(String idTipoSurtimiento) {
        this.idTipoSurtimiento = idTipoSurtimiento;
    }

    public List<TipoSurtimiento> getTipoSurtimientoList() {
        return tipoSurtimientoList;
    }

    public void setTipoSurtimientoList(List<TipoSurtimiento> tipoSurtimientoList) {
        this.tipoSurtimientoList = tipoSurtimientoList;
    }

    public String[] getTipoSurtimientosSelected() {
        return tipoSurtimientosSelected;
    }

    public void setTipoSurtimientosSelected(String[] tipoSurtimientosSelected) {
        this.tipoSurtimientosSelected = tipoSurtimientosSelected;
    }

    public boolean isMostrarPrioridad() {
        return mostrarPrioridad;
    }

    public void setMostrarPrioridad(boolean mostrarPrioridad) {
        this.mostrarPrioridad = mostrarPrioridad;
    }

    public boolean isSavePrioridad() {
        return savePrioridad;
    }

    public void setSavePrioridad(boolean savePrioridad) {
        this.savePrioridad = savePrioridad;
    }

    public String getIdServicioSelect() {
        return idServicioSelect;
    }

    public void setIdServicioSelect(String idServicioSelect) {
        this.idServicioSelect = idServicioSelect;
    }

    public List<EstrucAlmacenServicio_Extend> getServiciosXOrdenarList() {
        return serviciosXOrdenarList;
    }

    public void setServiciosXOrdenarList(List<EstrucAlmacenServicio_Extend> serviciosXOrdenarList) {
        this.serviciosXOrdenarList = serviciosXOrdenarList;
    }

    public List<EstrucAlmacenServicio_Extend> getAlmacenesXOrdenarList() {
        return almacenesXOrdenarList;
    }

    public void setAlmacenesXOrdenarList(List<EstrucAlmacenServicio_Extend> almacenesXOrdenarList) {
        this.almacenesXOrdenarList = almacenesXOrdenarList;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public List<String> getAlmacenesOrdenadosList() {
        return almacenesOrdenadosList;
    }

    public void setAlmacenesOrdenadosList(List<String> almacenesOrdenadosList) {
        this.almacenesOrdenadosList = almacenesOrdenadosList;
    }
    
    // </editor-fold>
}
