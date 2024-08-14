package mx.mc.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.FacesContext;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import mx.mc.enums.TipoAlmacen_Enum;
import mx.mc.enums.TipoAreaEstructura_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.Cama;
import mx.mc.model.CamaExtended;
import mx.mc.model.EstatusCama;
import mx.mc.model.EstrucAlmacenServicio_Extend;
import mx.mc.model.Estructura;
import mx.mc.model.EstructuraAlmacenServicio;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.TipoAlmacen;
import mx.mc.model.TipoAreaEstructura;
import mx.mc.model.TipoCama;
import mx.mc.model.Usuario;
import mx.mc.service.CamaService;
import mx.mc.service.EstatusCamaService;
import mx.mc.service.EstructuraAlmacenServicioService;
import mx.mc.service.EstructuraService;
import mx.mc.service.TipoAlmacenService;
import mx.mc.service.TipoAreaEstructuraService;
import mx.mc.service.TipoCamaService;
import mx.mc.util.Comunes;
import mx.mc.util.Mensaje;
import org.primefaces.PrimeFaces;

@Controller
@Scope(value = "view")
public class UnidadesJerarquicasMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(UnidadesJerarquicasMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private Usuario usuarioSelect;
    private boolean renderBotonActualizar;
    private boolean renderBotonInsertar;
    private String nombreUnidadHospitalaria;
    private boolean renderPanelAlmacen;
    private boolean renderPanelConsulta;
    private boolean renderInsertaCama;
    private boolean renderActualizaCama;
    private boolean envioHl7;
    private boolean renderHl7;
    private boolean status;
    private boolean estructuraActivo;
    private boolean transAutomatica;
    private boolean activaUnidad;
    private boolean activarAsignaCama;

    private int tipoArea;
    private int tipoAreaModal;
    private int tipoAreaAlmacen;
    private int tipoAreaServicio;

    @Autowired
    private transient EstructuraService estructuraService;
    private Estructura estructura;
    private Estructura estructuraPeriferico;
    private List<Estructura> listEstructura;
    private List<Estructura> listEstructuraPeriferico;
    private Estructura estructuraAux;
    private Estructura estructuraAsignada;
    private String idEstructuraPeriferico;
    private String nombrePeriferico;
    private boolean eliminaPeriferico;
    @Autowired
    private transient TipoAreaEstructuraService tipoAreaEstructuraService;
    private TipoAreaEstructura tipoAreaEstructuraSelect;
    private List<TipoAreaEstructura> listTipoAreaEstructura;
    private List<TipoAreaEstructura> listTipoAreaEstructuraPeriferico;
    private List<TipoAreaEstructura> listTipoArea;
    private List<TipoAreaEstructura> listTipoAreaAsigna;

    private static TreeNode root;
    private static TreeNode root1;
    private static TreeNode rootP;
    private static TreeNode rootP1;
    private boolean bandera;
    private static TreeNode selectNode;
    private static TreeNode selectNode1;
    private String nameUnidad;
    private String pathNode;
    private List<String> listNode;
    private String unidadDesactivar;
    private String errServicioCama;
    private String paramEstatus1;
    private String paramEstatus3;
    private String errPermisos;
    private String errSelectTipoArea;
    private PermisoUsuario permiso;
    @Autowired
    private transient CamaService camaService;
    private List<CamaExtended> listCamas;
    private Cama camaSelect;
    private int cantidadCamas;
    @Autowired
    private transient TipoCamaService tipoCamaService;
    private List<TipoCama> listTipoCama;
    private int tipoCama;
    @Autowired
    private transient EstatusCamaService estatusCamaService;
    private List<EstatusCama> listEstatusCama;
    private int tipoEstado;

    @Autowired
    private transient TipoAlmacenService tipoAlmacenService;
    private List<TipoAlmacen> listTipoAlmacen;
    private int tipoAlmacen;
    private String almacenAsiganar;
    private String servicioAsiganar;
    private EstrucAlmacenServicio_Extend estructuraAsignar;
    private String areaTipo;
    private List<Estructura> listaEstructuraPadre;
    private String estructuraPadreSelect;
    private boolean mostrarComboPadre;

    public String getAreaTipo() {
        return areaTipo;
    }

    public void setAreaTipo(String areaTipo) {
        this.areaTipo = areaTipo;
    }

    @Autowired
    private transient EstructuraAlmacenServicioService estructuraAlmacenServicioService;

    @PostConstruct
    public void init() {
        LOGGER.trace("Estructura Jerarquica");
        errServicioCama = "estr.err.servicioCama";
        paramEstatus3 = "estatus3";
        errPermisos = "estr.err.permisos";
        paramEstatus1 = "estatus1";
        errSelectTipoArea = "estr.err.selectTipoArea";
        initialize();
        permiso = Comunes.obtenerPermisos(Transaccion_Enum.ESTRUCTURA.getSufijo());
    }

    /**
     *
     * Metodo para inicializar valores y llenar combos
     *
     */
    private void initialize() {

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");

        usuarioSelect = sesion.getUsuarioSelected();
        listEstructura = new ArrayList<>();
        listEstructuraPeriferico = new ArrayList<>();
        bandera = false;
        tipoAreaEstructuraSelect = new TipoAreaEstructura();
        idEstructuraPeriferico = "";
        listTipoAreaEstructura = new ArrayList<>();
        listTipoArea = new ArrayList<>();
        listTipoAreaAsigna = new ArrayList<>();
        listCamas = new ArrayList<>();
        listTipoCama = new ArrayList<>();
        listEstatusCama = new ArrayList<>();
        listTipoAlmacen = new ArrayList<>();
        renderBotonActualizar = true;
        renderBotonInsertar = false;
        renderPanelAlmacen = false;
        renderPanelConsulta = false;
        renderInsertaCama = false;
        renderActualizaCama = false;
        renderHl7 = false;
        activaUnidad = false;
        activarAsignaCama = false;
        estructuraActivo = false;
        transAutomatica = false;
        tipoArea = 0;
        tipoAreaModal = 0;
        tipoAreaAlmacen = 0;
        tipoAreaServicio = 0;
        tipoEstado = 0;
        cantidadCamas = 0;
        tipoAlmacen = 0;
        nameUnidad = null;
        unidadDesactivar = null;
        nombrePeriferico = null;
        mostrarComboPadre = false;
        root = null;
        estructuraPadre();
        tipoAreaEstructura();
        tipoArea();
        tipoAreaEstructuraAsigna();
        obtenerListadoCamas();
        obtenerTipoAlmacenes();
    }

    /**
     *
     * Metodo para inicializar el padre del arbol
     *
     */
    public void estructuraPadre() {
        try {
            estructura = estructuraService.obtenerEstructuraPadre();
            estructuraAux = estructura;
            nombreUnidadHospitalaria = estructura.getNombre();
            unidadDesactivar = estructura.getNombre();
        } catch (Exception ex) {
            LOGGER.error("Error al obtener estructura padre: {}", ex.getMessage());
        }
    }

    /**
     *
     * Metodo para obtener la lista de tipos de estructura para llenar combo
     *
     */
    public void tipoAreaEstructura() {
        try {
            List<Integer> listaTipoArea = null;
            listTipoAreaEstructura = tipoAreaEstructuraService.obtenerTodoByArea(listaTipoArea, Constantes.TIPO_AREA_GENERAL);
        } catch (Exception ex) {
            LOGGER.error("Error en tipoAreaEstructura: {}", ex.getMessage());
        }
    }

    /**
     *
     * Metodo para obtener la lista de tipos de estructura para llenar combo
     * referente al periferico
     *
     */
    public void tipoAreaEstructuraPeriferico() {
        try {
            List<Integer> listaTipoArea = new ArrayList<>();
            listaTipoArea.add(Constantes.ALMACEN_FARMACIA);
            listTipoAreaEstructuraPeriferico = tipoAreaEstructuraService.obtenerTodoByArea(listaTipoArea, Constantes.TIPO_AREA_GENERAL);
        } catch (Exception ex) {
            LOGGER.error("Error en tipoAreaEstructuraPeriferico: {}", ex.getMessage());
        }
    }

    public void tipoArea() {
        try {
            List<Integer> listTipo = null;
            listTipoArea = tipoAreaEstructuraService.obtenerTodoByArea(listTipo, Constantes.TIPO_AREA_DETALLE);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener tipos de area para modal: {}", ex.getMessage());
        }
    }

    public void tipoAreaEstructuraAsigna() {
        try {
            List<Integer> listaTipoArea = new ArrayList<>();
            listaTipoArea.add(TipoAreaEstructura_Enum.CONSULTAINTERNA.getValue());
            listaTipoArea.add(TipoAreaEstructura_Enum.CONSULTAEXTERNA.getValue());
            listTipoAreaAsigna = tipoAreaEstructuraService.obtenerTodoByArea(listaTipoArea, Constantes.TIPO_AREA_GENERAL);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener tipos de area para asignar: {}", ex.getMessage());
        }
    }

    public void obtenerArbolAsigna() {
        estructuraPadre();
        obtenerUnidadesJerarquicas(tipoAreaAlmacen);
        listEstructura.add(0, estructura);
        root1 = new DefaultTreeNode("Root", null);
        for (int i = 0; i < listEstructura.size(); i++) {
            for (int j = 0; j < listEstructura.size(); j++) {
                if (listEstructura.get(j).getIdEstructuraPadre() != null) {
                    if (i == 0) {
                        if (listEstructura.get(i).getIdEstructura().equals(listEstructura.get(j).getIdEstructuraPadre())) {
                            new DefaultTreeNode(listEstructura.get(j).getNombre(), root1);
                        }
                    } else {
                        if (listEstructura.get(i).getIdEstructura().equals(listEstructura.get(j).getIdEstructuraPadre())) {
                            obtenerPadre(root1, listEstructura.get(i).getNombre(), listEstructura.get(j).getNombre());

                        }
                    }

                }
            }
        }
    }

    /**
     *
     * Metodo para crear arbol de estructura
     *
     */
    public void obtenerArbol() {
        activaUnidad = false;
        activarAsignaCama = false;
        
        listCamas = new ArrayList<>();
        estructuraPadre();
        if (tipoArea != Constantes.TIPO_AREA_ALMACEN) {
            renderPanelAlmacen = false;
            renderPanelConsulta = true;
            //obtenerCamasByServicio();
        } else {
            renderPanelAlmacen = true;
            renderPanelConsulta = false;
        }        
        obtenerUnidadesJerarquicas(tipoArea);                     
        listEstructura.add(0, estructura);
        root = new DefaultTreeNode("Root", null);
        for (int i = 0; i < listEstructura.size(); i++) {
            for (int j = 0; j < listEstructura.size(); j++) {
                if (listEstructura.get(j).getIdEstructuraPadre() != null) {
                    if (i == 0) {
                        if (listEstructura.get(i).getIdEstructura().equals(listEstructura.get(j).getIdEstructuraPadre())) {
                            new DefaultTreeNode(listEstructura.get(j).getNombre(), root);
                        }
                    } else {
                        if (listEstructura.get(i).getIdEstructura().equals(listEstructura.get(j).getIdEstructuraPadre())) {
                            obtenerPadre(root, listEstructura.get(i).getNombre(), listEstructura.get(j).getNombre());

                        }
                    }

                }
            }
        }
        if(tipoArea == 1) {
          root = new DefaultTreeNode("Root", null);            
          obtenerTipoAreaSelect(tipoArea);
        } else {
            estructura.setNombre("");
            areaTipo = "";
            cantidadCamas = 0;
            nombrePeriferico = "";        
            estructuraAsignar = new EstrucAlmacenServicio_Extend();
        }
        
    }

    //obtener arbol para periferico
    public void obtenerArbolPeriferico() {
        try {
            Estructura estructuraArbol = estructuraService.obtenerEstructuraPadre();
            obtenerUnidadesJerarquicasPeriferico(tipoAreaServicio);
            listEstructuraPeriferico.add(0, estructuraArbol);
            rootP1 = new DefaultTreeNode("Root", null);
            for (int i = 0; i < listEstructuraPeriferico.size(); i++) {
                for (int j = 0; j < listEstructuraPeriferico.size(); j++) {
                    if (listEstructuraPeriferico.get(j).getIdEstructuraPadre() != null) {
                        if (i == 0) {
                            if (listEstructuraPeriferico.get(i).getIdEstructura().equals(listEstructuraPeriferico.get(j).getIdEstructuraPadre())) {
                                new DefaultTreeNode(listEstructuraPeriferico.get(j).getNombre(), rootP1);
                            }
                        } else {
                            if (listEstructuraPeriferico.get(i).getIdEstructura().equals(listEstructuraPeriferico.get(j).getIdEstructuraPadre())) {
                                obtenerPadre(rootP1, listEstructuraPeriferico.get(i).getNombre(), listEstructuraPeriferico.get(j).getNombre());
                            }
                        }

                    }
                }
            }
            if (!listEstructuraPeriferico.isEmpty()) {
                bandera = true;
            }
        } catch (Exception ex) {
            LOGGER.error("Error al obtenerArbolPeriferico: {}", ex.getMessage());
        }
    }

    /**
     *
     * Metodo recursivo para obtener el padre
     *
     * @param nodo
     * @param nodoPadre
     * @param nodoHijo
     *
     */
    private void obtenerPadre(TreeNode nodo, String nodoPadre, String nodoHijo) {
        List<TreeNode> nodoList = nodo.getChildren();
        for (TreeNode auxNodo : nodoList) {
            if (auxNodo.getData().equals(nodoPadre)) {
                auxNodo.getChildren().add(new DefaultTreeNode(nodoHijo));
            } else {
                obtenerPadre(auxNodo, nodoPadre, nodoHijo);
            }
        }
    }

    /**
     *
     * Metodo para obtener la lista de unidades
     *
     * @param idTipoAreaEstructura
     *
     */
    public void obtenerUnidadesJerarquicas(int idTipoAreaEstructura) {
        try {
            listEstructura = estructuraService.obtenerUnidadesJerarquicasByTipoArea(idTipoAreaEstructura);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener unidades jerarquicas: {}", ex.getMessage());
        }
    }

    /**
     *
     * Metodo para obtener la lista de unidades de periférico
     *
     * @param idTipoAreaEstructura
     *
     */
    public void obtenerUnidadesJerarquicasPeriferico(int idTipoAreaEstructura) {
        try {
            listEstructuraPeriferico = estructuraService.obtenerUnidadesJerarquicasByTipoArea(idTipoAreaEstructura);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener unidades jerarquicas: {}", ex.getMessage());
        }
    }

    /**
     *
     * Metodo para obtener el listado de tipos de cama para llenar combo
     *
     */
    public void obtenerListadoCamas() {
        try {
            listTipoCama = tipoCamaService.obtenerTodo();
            listEstatusCama = estatusCamaService.obtenerTodo();
        } catch (Exception e) {
            LOGGER.error("Ocurrio un error al obtener los listados de camas: {}", e.getMessage());
        }

    }

    /**
     *
     * Metodo para obtener la lista de tipos de almacenes para llenar combo
     *
     */
    public void obtenerTipoAlmacenes() {
        try {
            listTipoAlmacen = tipoAlmacenService.obtenerTiposAlmacen();
        } catch (Exception e) {
            LOGGER.error("Ocurrio un error al obtener listado de tipos de Almacen: {}", e.getMessage());
        }
    }

    /**
     *
     * Metodo para obtener la lista de camas con las que cuenta el servicio
     * seleccionado
     *
     */
    public void obtenerCamasByServicio() {
        try {
            if (estructura.getIdEstructuraPadre() != null) {
                listCamas = camaService.obtenerCamasByServicio(estructura.getIdEstructura());
                if (estructura.getIdTipoArea() != null) {
                    obtenerTipoAreaSelect(estructura.getIdTipoArea());
                } else {
                    areaTipo = "";
                }
                cantidadCamas = listCamas.size();
            } else {
                listCamas = new ArrayList<>();
                cantidadCamas = 0;
            }
        } catch (Exception e) {
            LOGGER.error("Ocurrio un error al obtener los listados de camas por servicio: {}", e.getMessage());
        }

    }

    /**
     *
     * Metodo para obtener datos de unidad a editar
     *
     */
    public void obtenerDatos() {
        mostrarComboPadre = false;
        if (this.estructura.getIdTipoAreaEstructura() == 1 && this.tipoArea != 1) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errServicioCama), null);
            status = false;
        } else {
            estructura = estructuraAux;
            if (estructura.getActiva() == 1) {
                this.estructuraActivo = true;
            } else {
                this.estructuraActivo = false;
            }
            renderBotonInsertar = false;
            renderBotonActualizar = true;
            tipoAlmacen = estructura.getIdTipoAlmacen();
            List<Integer> listaTipoAlmacen = new ArrayList<>();
            //Valida si es subalmacen para cambiar SU ALMACEN PADRE o si No APlica para servicios                         
            if (tipoAlmacen == TipoAlmacen_Enum.SUBALMACEN.getValue() || tipoAlmacen == TipoAlmacen_Enum.NO_APLICA.getValue()) {
                estructuraPadreSelect = estructura.getIdEstructuraPadre();
                try {
                    if (renderPanelConsulta) {
                        listaEstructuraPadre = estructuraService.obtenerPorTipoAreayTipoAreaEstructura(
                                TipoAreaEstructura_Enum.AREA.getValue(), Constantes.TIPO_AREA_SERVICIO);
                        if (estructura.getIdTipoArea().equals(TipoAreaEstructura_Enum.SERVICIO.getValue())
                                && estructura.getIdTipoAreaEstructura() == Constantes.TIPO_AREA_SERVICIO) {
                            mostrarComboPadre = true;
                        }
                    } else {
                        listaTipoAlmacen.add(TipoAlmacen_Enum.ALMACEN.getValue());
                        listaEstructuraPadre = estructuraService.getEstructuraByLisTipoAlmacen(listaTipoAlmacen);
                        if (estructura.getIdTipoAlmacen().equals(TipoAlmacen_Enum.SUBALMACEN.getValue())) {
                            mostrarComboPadre = true;
                        }
                    }
                } catch (Exception ex) {
                    LOGGER.error("Error al obtener lista de estructura padres: {}", ex.getMessage());
                }
            } else {
                estructuraPadreSelect = null;
            }
            if (estructura.getIdTipoArea() != null) {
                this.tipoAreaModal = estructura.getIdTipoArea();
            }
            status = true;
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus3, status);
    }

    /*
     */
    public void obtenerDatosParaPeriferico() {
        try {
            List<Integer> listaTipoArea = new ArrayList<>();
            listaTipoArea.add(Constantes.ALMACEN_FARMACIA);
            listTipoAreaEstructuraPeriferico = tipoAreaEstructuraService.obtenerTodoByArea(listaTipoArea, Constantes.TIPO_AREA_GENERAL);
            servicioAsiganar = nameUnidad;
            tipoAreaServicio = 0;
            rootP1 = new DefaultTreeNode("Root", null);
            obtenerPeriferico();
        } catch (Exception ex) {
            LOGGER.error("Error en obtenerDatosParaPeriferico: {}", ex.getMessage());
        }
        status = true;
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus3, status);
    }

    /**
     *
     * Metodo para reiniciar todos los valores del formulario para agregar una
     * nueva unidad
     *
     */
    public void nuevaUnidadJerarquica() {
        if (this.tipoArea == 1 || this.tipoArea == 0) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estr.err.unidadHospital"), null);
            status = false;
        } else {
            estructura = new Estructura();
            renderBotonInsertar = true;
            renderBotonActualizar = false;
            tipoAreaModal = 0;
            tipoAlmacen = 0;
            renderHl7 = false;
            status = true;
            estructuraActivo = false;
            envioHl7 = false;
            transAutomatica = false;
            mostrarComboPadre = false;
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus3, status);
    }

    /**
     *
     * Metodo para obtener la unidad que se selecciono y buscar sus camas que
     * tiene asigadas
     *
     * @param event
     */
    public void onNodeSelect(NodeSelectEvent event) {
        activaUnidad = true;
        activarAsignaCama = true;
        nameUnidad = event.getTreeNode().toString();
        unidadDesactivar = nameUnidad;
        listNode = new ArrayList<>();
        obtenerParentNode(event.getTreeNode());
        pathNode = "";
        String aux = "";
        for (int i = listNode.size() - 1; i >= 0; i--) {
            aux = i == 0 ? "" : "/";
            pathNode += listNode.get(i) + aux;
        }
        if (pathNode.length() > 200) {
            pathNode = pathNode.substring(0, 200);
        }
        if (usuarioSelect != null) {
            usuarioSelect.setPathEstructura(pathNode);
        }
        obtenerUnidadArbol();
        if (tipoArea != Constantes.TIPO_AREA_ALMACEN) {
            obtenerCamasByServicio();
            obtenerPeriferico();
        } else {
            //To DO aqui se buscan los medicamentos de puntos de control
            obtenerUnidadAsignada();
            obtenerTipoAreaSelect(tipoArea);
        }
    }

    public void obtenerPeriferico() {
        try {
            Estructura estructPeriferico = estructuraService.obtenerEstructuraAlmacenPerifericoPorNombreEstructura(nameUnidad);
            if (estructPeriferico != null) {
                nombrePeriferico = estructPeriferico.getNombre();
                eliminaPeriferico = true;
            } else {
                nombrePeriferico = "";
                eliminaPeriferico = false;
            }

        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener el almacen Periférico: {}", ex.getMessage());
        }
    }

    public void obtenerTipoAreaSelect(Integer idTipoArea) {
        try {
            TipoAreaEstructura tipo = tipoAreaEstructuraService.obtenerPorIdTipoArea(idTipoArea);
            if (tipo != null) {
                areaTipo = tipo.getNombreArea();
            }
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener el tipo de area: {}", ex.getMessage());
        }
    }

    /**
     * Metodo para obtener la unidad que tiene asiganda al almacen
     */
    public void obtenerUnidadAsignada() {
        try {

            estructuraAsignar = estructuraAlmacenServicioService.obtenerEstructuraAsignada(estructuraAux.getIdEstructura());
            estructuraAsignada = estructuraAux;
        } catch (Exception e) {
            LOGGER.error("Ocurrio un error al obtener la estructura asignada: {}", e.getMessage());
        }
    }

    /**
     *
     * Metodo para obtener el servicio que se asignara del arbol
     *
     * @param event
     *
     */
    public void onNodeSelect1(NodeSelectEvent event) {
        servicioAsiganar = event.getTreeNode().toString();
    }

    public void onNodeSelect2(NodeSelectEvent event) {
        almacenAsiganar = event.getTreeNode().toString();
        try {
            estructuraPeriferico = estructuraService.getEstructuraForName(almacenAsiganar);
        } catch (Exception ex) {
            LOGGER.error("error al buscar el periferico sellecionado: {}", ex.getMessage());
        }
    }

    /**
     *
     * Metodo para obtener el almacen que se le asignaran los servicio
     *
     */
    public void obtenerDatosAlmacen() {
        if (estructuraAsignada == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errServicioCama), null);
            status = false;
        } else {
            estructuraAux = estructuraAsignada;
            almacenAsiganar = estructuraAux.getNombre();
            renderPanelConsulta = false;
            renderPanelAlmacen = true;
            obtenerUnidadAsignada();
            tipoAreaAlmacen = 0;
            root1 = new DefaultTreeNode("Root", null);
            status = true;
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus3, status);
    }

    /**
     *
     * Metodo para ocultar o mostrar campos en formulario de almacen
     *
     */
    public void envioMensajeHl7() {
        if (envioHl7) {
            renderHl7 = true;
        } else {
            renderHl7 = false;
        }
    }

    /**
     *
     * Metodo que asigna un almacen a diferentes servicios
     *
     */
    public void guardarAsignacion() {
        if (!this.permiso.isPuedeCrear()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.accion"), null);
            return;
        }
        String valida = validaFormAsignacion();
        if (!valida.isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, valida, null);
            status = Constantes.INACTIVO;
            return;
        }

        EstructuraAlmacenServicio almacenServicio = new EstructuraAlmacenServicio();
        Estructura estructuraServicio = new Estructura();
        estructuraServicio.setNombre(servicioAsiganar);
        Estructura estructuraAlmacen = new Estructura();
        estructuraAlmacen.setNombre(almacenAsiganar);
        try {
            estructuraServicio = estructuraService.obtener(estructuraServicio);
            if (estructuraServicio != null) {
                EstrucAlmacenServicio_Extend almacenAsignado = estructuraAlmacenServicioService.validarAsignacionServicio(estructuraServicio.getIdEstructura());
                if (almacenAsignado != null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estr.err.estructuraAsignado") + almacenAsignado.getNombre(), null);
                    status = Constantes.INACTIVO;
                    return;
                }
            }
            if (estructuraAsignar != null) {
                almacenServicio.setIdEstructuraAlmacen(estructuraAsignar.getIdEstructuraAlmacen());
                almacenServicio.setIdEstructuraServicio(estructuraServicio != null ? estructuraServicio.getIdEstructura() : "");
                if (estructuraAlmacenServicioService.actualizar(almacenServicio)) {
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("estr.info.actualizaAsigna"), null);
                    obtenerDatosAlmacen();
                    status = Constantes.ACTIVO;
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estr.err.actualizaAsigna"), null);
                    status = Constantes.ACTIVO;
                }
            } else {
                estructuraAlmacen = estructuraService.obtener(estructuraAlmacen);
                almacenServicio.setIdEstructuraAlmacen(estructuraAlmacen.getIdEstructura());
                almacenServicio.setIdEstructuraServicio(estructuraServicio != null ? estructuraServicio.getIdEstructura() : "");
                if (estructuraAlmacenServicioService.insertar(almacenServicio)) {
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("estr.info.asigna"), null);
                    obtenerDatosAlmacen();
                    status = Constantes.ACTIVO;
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estr.err.asigna"), null);
                    status = Constantes.ACTIVO;
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error en guardarAsignacion: {}", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estr.err.asigna"), null);
            status = Constantes.ACTIVO;
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus3, status);
    }

    /**
     *
     * Metodo para obtener la informacion de la unidad que se esta seleccionando
     * del arbol
     *
     */
    public void obtenerUnidadArbol() {
        Estructura unaEstructura = new Estructura();
        unaEstructura.setNombre(nameUnidad);
        try {
            estructuraAux = estructuraService.obtener(unaEstructura);
            estructura = estructuraAux;
        } catch (Exception ex) {
            LOGGER.error("Error en obtenerUnidadArbol: {}", ex.getMessage());
        }
    }

    public void obtenerUnidadArbolPeriferico() {
        Estructura unaEstructura = new Estructura();
        unaEstructura.setNombre(nameUnidad);
        try {
            estructuraAux = estructuraService.obtener(unaEstructura);
            estructura = estructuraAux;
        } catch (Exception ex) {
            LOGGER.error("Error en obtenerUnidadArbolPeriferico: {}", ex.getMessage());
        }
    }

    /**
     *
     * Metodo utilizado para obtener el padre del arbol a generar
     *
     * @param nodo
     *
     */
    private void obtenerParentNode(TreeNode nodo) {

        TreeNode node = nodo.getParent();
        if (node != null && node.getData() != "Root") {
            listNode.add(nodo.getParent().toString());
            obtenerParentNode(node);
        }
    }

    /**
     *
     * Metodo que se utiliza para guardar una unidad nueva a determinada area o
     * unidad seleccionada
     *
     * @throws java.lang.Exception
     */
    public void guardarNuevaUnidad() throws Exception {
        boolean update = false;
        if (!this.permiso.isPuedeCrear()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errPermisos), null);
            return;
        }
        String valida = validaFormAgregarEditarUnidad(update);
        if (!valida.isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, valida, null);
            status = Constantes.INACTIVO;
            return;
        }
        int nivelAlmacen = 0;
        Usuario usuarioSesion = Comunes.obtenerUsuarioSesion();
        estructura.setDescripcion(estructura.getNombre());
        estructura.setIdEstructura(Comunes.getUUID());
        if (estructura.getClaveEstructura() != null && !estructura.getClaveEstructura().equals("")) {
            estructura.setClaveEstructura(estructura.getClaveEstructura());
        }
        if (estructura.getClavePresupuestal() != null && !estructura.getClavePresupuestal().equals("")) {
            estructura.setClavePresupuestal(estructura.getClavePresupuestal());
        }
        estructura.setInsertFecha(new Date());
        estructura.setInsertIdUsuario(usuarioSesion.getIdUsuario());
        if (this.estructuraActivo) {
            estructura.setActiva(Constantes.ESTATUS_ACTIVO);
        } else {
            estructura.setActiva(Constantes.ESTATUS_INACTIVO);
        }
        estructura.setIdTipoAreaEstructura(tipoArea);
        if (tipoArea != Constantes.TIPO_AREA_ALMACEN) {
            estructura.setIdTipoAlmacen(1);
            estructura.setIdTipoArea(tipoAreaModal);
        } else {
            estructura.setIdTipoAlmacen(tipoAlmacen);
        }
        if (nameUnidad != null) {
            Estructura unaEstructura = new Estructura();
            unaEstructura.setNombre(nameUnidad);
            try {
                Estructura estructuraPadre = estructuraService.obtener(unaEstructura);
                estructura.setIdEstructuraPadre(estructuraPadre.getIdEstructura());
                nivelAlmacen = estructuraPadre.getIdTipoAlmacen();
            } catch (Exception ex) {
                LOGGER.error("Error al obtener la estructura padre por nombre para ingresar nuevo: {}", ex.getMessage());
            }
        } else {
            estructura.setIdEstructuraPadre(estructuraAux.getIdEstructura());
            nivelAlmacen = estructuraAux.getIdTipoAlmacen();
        }
        String validaAlmacen = validaNivelAlmacen(nivelAlmacen);
        if (!validaAlmacen.isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, validaAlmacen, null);
            status = Constantes.INACTIVO;
        } else {
            try {
                if (estructuraService.insertar(estructura)) {
                    obtenerArbol();
                    status = Constantes.ACTIVO;
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("estr.info.guardarUnidad"), null);
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estr.err.guardarUnidad"), null);
                    status = Constantes.INACTIVO;
                }
            } catch (Exception e) {
                LOGGER.error("Error al ingresar un nuevo elemento al arbol: {}", e.getMessage());
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estr.err.agregarElementoArbol"), null);
                status = Constantes.ACTIVO;
            }
            PrimeFaces.current().ajax().addCallbackParam(paramEstatus1, status);
        }
    }

    public void actualizarAddPeriferico() throws Exception {
        LOGGER.info("Entrando");
        try {
            Estructura unaEstructura = new Estructura();
            if (estructuraPeriferico == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Seleccione un Almacén", null);
            } else {
                Estructura estructuraServicio = new Estructura();
                estructuraServicio.setNombre(servicioAsiganar);
                estructuraServicio = estructuraService.obtener(estructuraServicio);
                unaEstructura.setIdEstructura(estructuraServicio.getIdEstructura());
                unaEstructura.setUpdateIdUsuario(usuarioSelect.getIdUsuario());
                unaEstructura.setUpdateFecha(new Date());
                unaEstructura.setIdAlmacenPeriferico(estructuraPeriferico.getIdEstructura());
            }

            boolean resp = estructuraService.actualizar(unaEstructura);
            if (resp) {
                Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("estr.info.guardaPeriferico"), null);
                status = Constantes.ACTIVO;
                obtenerDatosParaPeriferico();
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estr.err.noPeriferico"), null);
                status = Constantes.INACTIVO;
            }
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al momento de asignar un almacén periférico al servicio: {}", ex.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus1, status);
    }

    /**
     *
     * Metodo que se utiliza para actuaizar una unidad al momento de estar en
     * formulario de edicion
     *
     * @throws java.lang.Exception
     */
    public void actualizarUnidad() throws Exception {
        boolean update = true;

        String valida = validaFormAgregarEditarUnidad(update);
        if (!valida.isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, valida, null);
            status = Constantes.INACTIVO;
            return;
        }

        Estructura est = estructuraService.obtenerEstructura(estructura.getIdEstructura());

        String nombreEstructura = estructuraService.validarExistenciaEstrucutra(estructura.getNombre());

        if (nombreEstructura != null && !nombreEstructura.equals(est.getNombre())) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estr.err.estructuraExistente") + " " + estructura.getNombre(), null);
            status = Constantes.INACTIVO;
            return;
        }
        String claveEstructura = null;
        if (!estructura.getClaveEstructura().isEmpty() && estructura.getClaveEstructura() != null) {
            claveEstructura = estructuraService.validarExistenciaClave(estructura.getClaveEstructura());
        }
        if (claveEstructura != null && !claveEstructura.equals(est.getClaveEstructura())) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estr.err.estructuraClaveExistente") + " " + estructura.getClaveEstructura(), null);
            status = Constantes.INACTIVO;
            return;
        }
        if (!this.permiso.isPuedeEditar()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errPermisos), null);
            return;
        }

        Usuario usuarioSesion = Comunes.obtenerUsuarioSesion();
        estructura.setUpdateFecha(new Date());
        estructura.setUpdateIdUsuario(usuarioSesion.getIdUsuario());
        estructura.setClaveEstructura(estructura.getClaveEstructura());
        estructura.setClavePresupuestal(estructura.getClavePresupuestal());
        if (estructuraPadreSelect != null) {
            if(!estructuraPadreSelect.isEmpty()) {
                estructura.setIdEstructuraPadre(estructuraPadreSelect);
            }            
        }

        if (tipoArea != Constantes.TIPO_AREA_ALMACEN) {
            estructura.setIdTipoArea(tipoAreaModal);
        }

        if (this.estructuraActivo) {
            estructura.setActiva(Constantes.ESTATUS_ACTIVO);
        } else {
            estructura.setActiva(Constantes.ESTATUS_INACTIVO);
        }
        try {
            if (estructuraService.actualizar(estructura)) {
                obtenerArbol();
                Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("estr.info.actualizaUnidad"), null);
                status = Constantes.ACTIVO;
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estr.err.actualizaUnidad"), null);
                status = Constantes.ACTIVO;
            }
        } catch (Exception e) {
            LOGGER.error("Error al actualizar una unidad del arbol: {}", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Probablemente ya existe un Registro con el nombre ", null);
            status = Constantes.ACTIVO;
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus1, status);
    }

    /**
     *
     * Metodo que se utiliza para desactivar una unidad
     *
     */
    public void desactivarUnidad() {
        if (!this.permiso.isPuedeAutorizar()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errPermisos), null);
            return;
        }
        estructura.setActiva(Constantes.ESTATUS_INACTIVO);
        try {
            if (estructuraService.actualizar(estructura)) {
                obtenerArbol();
                Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("estr.info.desactivaUnidad"), null);
            }
        } catch (Exception e) {
            LOGGER.error("Error al desactivar una unidad del arbol: {}", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estr.err.desactivaUnidad"), null);
        }
    }

    public void eliminarPeriferico() {
        if (!this.permiso.isPuedeAutorizar()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errPermisos), null);
            return;
        }
        try {
            estructura.setIdAlmacenPeriferico(null);
            if (estructuraService.quitarAlmacenPeriferico(estructura)) {
                eliminaPeriferico = false;
                nombrePeriferico = "";
                Mensaje.showMessage(Constantes.MENSAJE_INFO, "Se elimino el periférico correctamente.", null);
            }
        } catch (Exception e) {
            LOGGER.error("Error al eliminar el periférico del servicio: {}", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al eliminar el periférico del servicio", null);
        }
    }

    /**
     *
     * Metodo utilizado para reinicar valores para agregar una nueva cama
     *
     */
    public void nuevaCama() {
        if (this.tipoArea == 1 || this.tipoArea == 0) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estr.err.camaHospital"), null);
            status = false;
        } else {
            if (this.estructura.getIdTipoAreaEstructura() == 1) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errServicioCama), null);
                status = false;
            } else {
                camaSelect = new Cama();
                tipoEstado = 0;
                tipoCama = 0;
                renderActualizaCama = false;
                renderInsertaCama = true;
                status = true;
            }
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus3, status);
    }

    /**
     *
     * Metodo que se utiliza para obtener la informacion de la cama a editar
     *
     * @param idCama
     *
     */
    public void obtenerCama(String idCama) {
        renderActualizaCama = true;
        renderInsertaCama = false;
        if (!idCama.isEmpty()) {
            try {
                camaSelect = camaService.obtenerCama(idCama);
                tipoCama = camaSelect.getIdTipoCama();
                tipoEstado = camaSelect.getIdEstatusCama();
            } catch (Exception e) {
                LOGGER.error("Error al obtener la cama: {}", e.getMessage());
            }
        }
    }

    /**
     *
     * Metodo que se utiliza para agregar una cama a un servicio
     *
     */
    public void guardarCama() {
        if (!this.permiso.isPuedeCrear()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errPermisos), null);
            return;
        }
        String valida = validarFormCama();
        if (!valida.isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, valida, null);
            status = Constantes.INACTIVO;
            return;
        }
        if(existeCama()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El nombre de la cama ya existe en este servicio", null);
            status = Constantes.INACTIVO;
            return;
        }
        
        if (estructura.getIdEstructuraPadre() != null && !estructura.getIdEstructuraPadre().isEmpty()) {
            Usuario usuarioSesion = Comunes.obtenerUsuarioSesion();
            camaSelect.setIdCama(Comunes.getUUID());
            camaSelect.setIdEstructura(estructura.getIdEstructura());
            camaSelect.setIdEstatusCama(tipoEstado);
            camaSelect.setIdTipoCama(tipoCama);
            camaSelect.setInsertIdUsuario(usuarioSesion.getIdUsuario());
            camaSelect.setInsertFecha(new Date());
            try {
                if (camaService.insertar(camaSelect)) {
                    obtenerCamasByServicio();
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("estr.info.guardarCama"), null);
                    status = Constantes.ACTIVO;
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estr.err.guardarCama"), null);
                    status = Constantes.ACTIVO;
                }
            } catch (Exception e) {
                LOGGER.error("Error al desactivar una unidad del arbol: {}", e.getMessage());
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estr.err.desactivaUnidad"), null);
                status = Constantes.ACTIVO;
            }
        } else {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estr.err.camaHospital"), null);
            status = Constantes.INACTIVO;
        }
        PrimeFaces.current().ajax().addCallbackParam("estatus2", status);

    }

    public boolean existeCama() {
        try {
            Cama camaExiste = camaService.obterCamaPorNombreYEstructura(camaSelect.getNombreCama(), estructura.getIdEstructura());
            if(camaExiste != null) {
                return true;
            }
        } catch(Exception ex) {
            LOGGER.error("Error al validar la existencia de la cama, metodo : existeCama : linea 1149 UnidadesJerarquicasMB");
        }
        return false;
    }
    /**
     *
     * Metodo que se utiliza para actualizar la cama de un servicio
     *
     */
    public void actualizaCama() {
        if (!this.permiso.isPuedeEditar()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errPermisos), null);
            return;
        }
        String valida = validarFormCama();
        if (!valida.isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, valida, null);
            status = Constantes.INACTIVO;
            return;
        }        
        Usuario usuarioSesion = Comunes.obtenerUsuarioSesion();
        camaSelect.setUpdateFecha(new Date());
        camaSelect.setUpdateIdUsuario(usuarioSesion.getIdUsuario());
        camaSelect.setIdEstatusCama(tipoEstado);
        camaSelect.setIdTipoCama(tipoCama);
        try {
            Cama camaExiste = camaService.obterCamaPorNombreYEstructura(camaSelect.getNombreCama(), estructura.getIdEstructura());
            if(camaExiste != null){
                if(!camaExiste.getIdCama().equals(camaSelect.getIdCama())) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El nombre de la cama ya existe en este servicio", null);
                    status = Constantes.INACTIVO;
                    return;
                }
            }
            if (camaService.actualizar(camaSelect)) {
                obtenerCamasByServicio();
                Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("estr.info.actualizaCama"), null);
                status = Constantes.ACTIVO;
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estr.err.actualizaCama"), null);
                status = Constantes.ACTIVO;
            }
        } catch (Exception e) {
            LOGGER.error("Error al actualizar la cama: {}", e.getMessage());
            status = Constantes.ACTIVO;
        }
        PrimeFaces.current().ajax().addCallbackParam("estatus2", status);
    }

    /**
     *
     * Metodo para validar datos de formulario requeridos
     *
     * @param update
     * @return String
     * @throws java.lang.Exception
     *
     */
    public String validaFormAgregarEditarUnidad(boolean update) throws Exception {
        if (this.estructura.getNombre().isEmpty()) {
            return RESOURCES.getString("estr.err.nombreUnidad");
        }
        if (this.estructura.getIdEstructuraPadre() != null) {
            if (this.tipoArea == 2) {
                if (this.tipoAlmacen == 0) {
                    return RESOURCES.getString(errSelectTipoArea);
                }
            } else {
                if (this.tipoAreaModal == 0) {
                    return RESOURCES.getString(errSelectTipoArea);
                }
            }
        }
        if (!update) {
            String nombreEstructura = estructuraService.validarExistenciaEstrucutra(estructura.getNombre());
            if (nombreEstructura != null) {
                return RESOURCES.getString("estr.err.estructuraExistente") + " " + estructura.getNombre();
            }
            String claveEstructura = estructuraService.validarExistenciaClave(estructura.getClaveEstructura());
            if (claveEstructura != null) {
                return RESOURCES.getString("estr.err.estructuraClaveExistente") + " " + estructura.getClaveEstructura();
            }
        }
        return "";
    }

    public String validaFormAsignacion() {
        if (this.tipoAreaAlmacen == 0) {
            return RESOURCES.getString(errSelectTipoArea);
        }
        if (this.servicioAsiganar == null) {
            return RESOURCES.getString("estr.err.selectElemetArbol");
        }
        return "";
    }

    /**
     *
     * Metodo para validar datos de cama
     *
     * @return String
     */
    public String validarFormCama() {
        if (this.camaSelect.getNombreCama().isEmpty()) {
            return RESOURCES.getString("estr.err.cama");
        }
        if (this.tipoCama == 0) {
            return RESOURCES.getString("estr.err.tipoCama");
        }
        if (this.tipoEstado == 0) {
            return RESOURCES.getString("estr.err.estadoCama");
        }

        return "";
    }

    /**
     *
     * Metodo para validar los almacenes para agregar
     *
     * @param nivelAlmacen
     * @return String
     *
     */
    public String validaNivelAlmacen(int nivelAlmacen) {
        if (nivelAlmacen == Constantes.ALMACEN_FARMACIA && tipoAlmacen == Constantes.ALMACEN_FARMACIA) {
            return RESOURCES.getString("estr.err.farmFarma");
        }
        if (nivelAlmacen == Constantes.ALMACEN_FARMACIA && tipoAlmacen == Constantes.SUBALMACEN) {
            return RESOURCES.getString("estr.err.subalmFarma");
        }
        if (nivelAlmacen == Constantes.ALMACEN && tipoAlmacen == Constantes.ALMACEN_FARMACIA) {
            return RESOURCES.getString("estr.err.farAlma");
        }
        if (nivelAlmacen == Constantes.ALMACEN && tipoAlmacen == Constantes.ALMACEN) {
            return RESOURCES.getString("estr.err.almAlma");
        }
        if (nivelAlmacen == Constantes.NIVEL_SUBALMACEN && tipoAlmacen == Constantes.NIVEL_SUBALMACEN) {
            return RESOURCES.getString("estr.err.subalSubal");
        }
        if (nivelAlmacen == Constantes.NIVEL_SUBALMACEN && tipoAlmacen == Constantes.ALMACEN_FARMACIA) {
            return RESOURCES.getString("estr.err.farmSubalm");
        }
        if (nivelAlmacen == Constantes.NIVEL_SUBALMACEN && tipoAlmacen == Constantes.ALMACEN) {
            return RESOURCES.getString("estr.err.almSubalma");
        }
        return "";
    }

    public Estructura getEstructura() {
        return estructura;
    }

    public void setEstructura(Estructura estructura) {
        this.estructura = estructura;
    }

    public Estructura getEstructuraAux() {
        return estructuraAux;
    }

    public void setEstructuraAux(Estructura estructuraAux) {
        this.estructuraAux = estructuraAux;
    }

    public List<Estructura> getListEstructura() {
        return listEstructura;
    }

    public void setListEstructura(List<Estructura> listEstructura) {
        this.listEstructura = listEstructura;
    }

    public int getTipoArea() {
        return tipoArea;
    }

    public void setTipoArea(int tipoArea) {
        this.tipoArea = tipoArea;
    }

    public int getTipoAreaModal() {
        return tipoAreaModal;
    }

    public void setTipoAreaModal(int tipoAreaModal) {
        this.tipoAreaModal = tipoAreaModal;
    }

    public int getTipoAreaAlmacen() {
        return tipoAreaAlmacen;
    }

    public void setTipoAreaAlmacen(int tipoAreaAlmacen) {
        this.tipoAreaAlmacen = tipoAreaAlmacen;
    }

    public TipoAreaEstructura getTipoAreaEstructuraSelect() {
        return tipoAreaEstructuraSelect;
    }

    public void setTipoAreaEstructuraSelect(TipoAreaEstructura tipoAreaEstructuraSelect) {
        this.tipoAreaEstructuraSelect = tipoAreaEstructuraSelect;
    }

    public List<TipoAreaEstructura> getListTipoAreaEstructura() {
        return listTipoAreaEstructura;
    }

    public List<TipoAreaEstructura> getListTipoArea() {
        return listTipoArea;
    }

    public List<TipoAreaEstructura> getListTipoAreaAsigna() {
        return listTipoAreaAsigna;
    }

    public int getTipoAreaServicio() {
        return tipoAreaServicio;
    }

    public void setTipoAreaServicio(int tipoAreaServicio) {
        this.tipoAreaServicio = tipoAreaServicio;
    }

    public TreeNode getRoot() {
        return root;
    }

    public TreeNode getRoot1() {
        return root1;
    }

    public TreeNode getSelectNode() {
        return selectNode;
    }

    public void setSelectNode(TreeNode selectNode) {
        this.selectNode = selectNode;
    }

    public TreeNode getSelectNode1() {
        return selectNode1;
    }

    public void setSelectNode1(TreeNode selectNode1) {
        this.selectNode1 = selectNode1;
    }

    public String getNameUnidad() {
        return nameUnidad;
    }

    public void setNameUnidad(String nameUnidad) {
        this.nameUnidad = nameUnidad;
    }

    public String getPathNode() {
        return pathNode;
    }

    public void setPathNode(String pathNode) {
        this.pathNode = pathNode;
    }

    public String getNombreUnidadHospitalaria() {
        return nombreUnidadHospitalaria;
    }

    public void setNombreUnidadHospitalaria(String nombreUnidadHospitalaria) {
        this.nombreUnidadHospitalaria = nombreUnidadHospitalaria;
    }

    public boolean isRenderBotonActualizar() {
        return renderBotonActualizar;
    }

    public void setRenderBotonActualizar(boolean renderBotonActualizar) {
        this.renderBotonActualizar = renderBotonActualizar;
    }

    public boolean isRenderBotonInsertar() {
        return renderBotonInsertar;
    }

    public void setRenderBotonInsertar(boolean renderBotonInsertar) {
        this.renderBotonInsertar = renderBotonInsertar;
    }

    public boolean isRenderPanelAlmacen() {
        return renderPanelAlmacen;
    }

    public void setRenderPanelAlmacen(boolean renderPanelAlmacen) {
        this.renderPanelAlmacen = renderPanelAlmacen;
    }

    public boolean isRenderPanelConsulta() {
        return renderPanelConsulta;
    }

    public void setRenderPanelConsulta(boolean renderPanelConsulta) {
        this.renderPanelConsulta = renderPanelConsulta;
    }

    public boolean isRenderInsertaCama() {
        return renderInsertaCama;
    }

    public void setRenderInsertaCama(boolean renderInsertaCama) {
        this.renderInsertaCama = renderInsertaCama;
    }

    public boolean isRenderActualizaCama() {
        return renderActualizaCama;
    }

    public void setRenderActualizaCama(boolean renderActualizaCama) {
        this.renderActualizaCama = renderActualizaCama;
    }

    public boolean isEnvioHl7() {
        return envioHl7;
    }

    public void setEnvioHl7(boolean envioHl7) {
        this.envioHl7 = envioHl7;
    }

    public boolean isRenderHl7() {
        return renderHl7;
    }

    public void setRenderHl7(boolean renderHl7) {
        this.renderHl7 = renderHl7;
    }

    public List<String> getListNode() {
        return listNode;
    }

    public void setListNode(List<String> listNode) {
        this.listNode = listNode;
    }

    public String getUnidadDesactivar() {
        return unidadDesactivar;
    }

    public void setUnidadDesactivar(String unidadDesactivar) {
        this.unidadDesactivar = unidadDesactivar;
    }

    public int getCantidadCamas() {
        return cantidadCamas;
    }

    public void setCantidadCamas(int cantidadCamas) {
        this.cantidadCamas = cantidadCamas;
    }

    public int getTipoCama() {
        return tipoCama;
    }

    public void setTipoCama(int tipoCama) {
        this.tipoCama = tipoCama;
    }

    public int getTipoEstado() {
        return tipoEstado;
    }

    public void setTipoEstado(int tipoEstado) {
        this.tipoEstado = tipoEstado;
    }

    public List<CamaExtended> getListCamas() {
        return listCamas;
    }

    public Cama getCamaSelect() {
        return camaSelect;
    }

    public void setCamaSelect(Cama camaSelect) {
        this.camaSelect = camaSelect;
    }

    public List<TipoCama> getListTipoCama() {
        return listTipoCama;
    }

    public List<EstatusCama> getListEstatusCama() {
        return listEstatusCama;
    }

    public int getTipoAlmacen() {
        return tipoAlmacen;
    }

    public void setTipoAlmacen(int tipoAlmacen) {
        this.tipoAlmacen = tipoAlmacen;
    }

    public List<TipoAlmacen> getListTipoAlmacen() {
        return listTipoAlmacen;
    }

    public EstrucAlmacenServicio_Extend getEstructuraAsignar() {
        return estructuraAsignar;
    }

    public void setEstructuraAsignar(EstrucAlmacenServicio_Extend estructuraAsignar) {
        this.estructuraAsignar = estructuraAsignar;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isEstructuraActivo() {
        return estructuraActivo;
    }

    public void setEstructuraActivo(boolean estructuraActivo) {
        this.estructuraActivo = estructuraActivo;
    }

    public boolean isActivaUnidad() {
        return activaUnidad;
    }

    public void setActivaUnidad(boolean activaUnidad) {
        this.activaUnidad = activaUnidad;
    }

    public boolean isActivarAsignaCama() {
        return activarAsignaCama;
    }

    public void setActivarAsignaCama(boolean activarAsignaCama) {
        this.activarAsignaCama = activarAsignaCama;
    }

    public boolean isTransAutomatica() {
        return transAutomatica;
    }

    public void setTransAutomatica(boolean transAutomatica) {
        this.transAutomatica = transAutomatica;
    }

    public List<TipoAreaEstructura> getListTipoAreaEstructuraPeriferico() {
        return listTipoAreaEstructuraPeriferico;
    }

    public void setListTipoAreaEstructuraPeriferico(List<TipoAreaEstructura> listTipoAreaEstructuraPeriferico) {
        this.listTipoAreaEstructuraPeriferico = listTipoAreaEstructuraPeriferico;
    }

    public List<Estructura> getListEstructuraPeriferico() {
        return listEstructuraPeriferico;
    }

    public void setListEstructuraPeriferico(List<Estructura> listEstructuraPeriferico) {
        this.listEstructuraPeriferico = listEstructuraPeriferico;
    }

    public Estructura getEstructuraPeriferico() {
        return estructuraPeriferico;
    }

    public void setEstructuraPeriferico(Estructura estructuraPeriferico) {
        this.estructuraPeriferico = estructuraPeriferico;
    }

    public TreeNode getRootP() {
        return rootP;
    }

    public void setRootP(TreeNode rootP) {
        this.rootP = rootP;
    }

    public TreeNode getRootP1() {
        return rootP1;
    }

    public void setRootP1(TreeNode rootP1) {
        this.rootP1 = rootP1;
    }

    public boolean isBandera() {
        return bandera;
    }

    public void setBandera(boolean bandera) {
        this.bandera = bandera;
    }

    public String getIdEstructuraPeriferico() {
        return idEstructuraPeriferico;
    }

    public void setIdEstructuraPeriferico(String idEstructuraPeriferico) {
        this.idEstructuraPeriferico = idEstructuraPeriferico;
    }

    public String getNombrePeriferico() {
        return nombrePeriferico;
    }

    public void setNombrePeriferico(String nombrePeriferico) {
        this.nombrePeriferico = nombrePeriferico;
    }

    public boolean isEliminaPeriferico() {
        return eliminaPeriferico;
    }

    public void setEliminaPeriferico(boolean eliminaPeriferico) {
        this.eliminaPeriferico = eliminaPeriferico;
    }

    public List<Estructura> getListaEstructuraPadre() {
        return listaEstructuraPadre;
    }

    public void setListaEstructuraPadre(List<Estructura> listaEstructuraPadre) {
        this.listaEstructuraPadre = listaEstructuraPadre;
    }

    public String getEstructuraPadreSelect() {
        return estructuraPadreSelect;
    }

    public void setEstructuraPadreSelect(String estructuraPadreSelect) {
        this.estructuraPadreSelect = estructuraPadreSelect;
    }

    public boolean isMostrarComboPadre() {
        return mostrarComboPadre;
    }

    public void setMostrarComboPadre(boolean mostrarComboPadre) {
        this.mostrarComboPadre = mostrarComboPadre;
    }    

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }
    
}
