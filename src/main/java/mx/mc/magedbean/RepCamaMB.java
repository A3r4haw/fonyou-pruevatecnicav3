/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.magedbean;

import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import mx.mc.lazy.RepCamaLazy;
import mx.mc.enums.TipoAreaEstructura_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.EstatusCama;
import mx.mc.model.Estructura;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.RepCama;
import mx.mc.model.Usuario;
import mx.mc.service.EntidadHospitalariaService;
import mx.mc.service.EstatusCamaService;
import mx.mc.service.EstructuraService;
import mx.mc.service.RepCamaService;
import mx.mc.service.ReportesService;
import mx.mc.util.Comunes;
import mx.mc.util.FechaUtil;
import mx.mc.util.Mensaje;
import org.primefaces.PrimeFaces;
import org.primefaces.event.NodeSelectEvent;
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
public class RepCamaMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(RepMovimientosGralesMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private Usuario currentUser;
    private static TreeNode root;
    private static TreeNode selectNode;
    private String nameUnidad;
    private String idUnidad;
    private String fullNameUnidad;
    private String pathNode;
    private String idRol;
    private List<String> listNode;
    private List<Integer> tipoEstatusCama;
    private Usuario usuarioSession;
    private boolean administrador;
    private String idEstructura;
    private String pathPdf;
    private boolean habilReporte;
    private PermisoUsuario permiso;
    private ParamBusquedaReporte paramReporte;
    private String cadenaFiltro;
    private RepCamaLazy repCamaLazy;

    @Autowired
    private transient RepCamaService camaServices;
    private RepCama repCama;
    private List<RepCama> repCamaList;

    @Autowired
    private transient EntidadHospitalariaService entidadHospitalariaService;

    @Autowired
    private transient EstatusCamaService estatusCamaService;
    private EstatusCama estatusCama;
    private List<EstatusCama> estatusCamaList;

    @Autowired
    private transient EstructuraService estructuraService;
    private Estructura estructuraSelect;
    private Estructura estructura;
    private List<Estructura> listEstructura;

    @Autowired
    private transient ReportesService reportesService;

    @PostConstruct
    public void init() {
        this.administrador = Comunes.isAdministrador();
        this.usuarioSession = Comunes.obtenerUsuarioSesion();
        initialize();
        permiso = Comunes.obtenerPermisos(Transaccion_Enum.REPORTEDISPOCAMA.getSufijo());
        obtenerEstatus();
    }

    private void initialize() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        currentUser = sesion.getUsuarioSelected();
        habilReporte = true;
        idUnidad = "";
        repCama = new RepCama();
        estatusCama = new EstatusCama();
        repCamaList = new ArrayList<>();
        estatusCamaList = new ArrayList<>();
        listEstructura = new ArrayList<>();
        obtenerArbol();
    }


    public void obtenerArbol() {
        try {
            obtenerUnidadesJerarquicas();
            root = new DefaultTreeNode("Root", null);
            TreeNode node = new DefaultTreeNode(listEstructura.get(0).getNombre(), root);
            for (int i = 0; i < listEstructura.size(); i++) {
                for (Estructura et : listEstructura) {
                    if (et.getIdEstructuraPadre() != null
                            && et.getIdEstructuraPadre().equals(listEstructura.get(i).getIdEstructura())) {
                        if (i == 0) {
                            node.getChildren().add(new DefaultTreeNode(et.getNombre()));
                        }
                        obtenerPadre(node, listEstructura.get(i).getNombre(), et.getNombre());
                    }
                }
            }

        } catch (Exception ex) {
            LOGGER.error("Error al obtener unidades jerarquicas: {}", ex.getMessage());
        }

    }

    public void obtenerUnidadesJerarquicas() {
        try {
            List<Estructura> listaux = estructuraService.obtenerUnidadesOrderTipo();
            for (Estructura lis : listaux) {
                if (!lis.getIdTipoAreaEstructura().equals(TipoAreaEstructura_Enum.ALMACEN.getValue())) {
                    listEstructura.add(lis);
                }
            }

        } catch (Exception ex) {
            LOGGER.error("Error al obtener unidades jerarquicas: {}", ex.getMessage());
        }
    }

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

    private void obtenerEstatus() {
        try {
            estatusCamaList = estatusCamaService.obtenerTodo();
        } catch (Exception ex) {
            LOGGER.error("Error al obtener los estatus de la cama: {}", ex.getMessage());
        }
    }

    private void obtenerParentNode(TreeNode nodo) {

        TreeNode node = nodo.getParent();
        if (node != null
                && node.getData() != "Root") {
            listNode.add(nodo.getParent().toString());
            obtenerParentNode(node);
        }
    }

    public void generaReporte() {
        try {
            if (!idUnidad.equals("")) {
                habilReporte = false;

                List<String> estructuraList = estructuraService.obtenerIdsEstructuraJerarquica(idUnidad, false);
                estructuraList.add(idUnidad);

                paramReporte = new ParamBusquedaReporte();
                paramReporte.setNuevaBusqueda(true);
                paramReporte.setIdTipoMovimientos(tipoEstatusCama);
                if (!cadenaFiltro.equals("")) {
                    paramReporte.setCadenaBusqueda(cadenaFiltro);
                }

                repCamaLazy = new RepCamaLazy(camaServices, paramReporte, estructuraList);

            } else {
                Mensaje.showMessage("Error", "Selecciona el Área", null);
            }
        } catch (Exception ex) {
            LOGGER.error("Error al consultar", ex);
        }
    }

    public void imprimeReporteCamas() throws Exception {
        LOGGER.debug("mx.mc.magedbean.RepCamaMB.imprimeReporteCamas()");
        boolean status = Constantes.INACTIVO;
        try {
            paramReporte = new ParamBusquedaReporte();
            Estructura est;

            est = estructuraService.obtenerEstructura(idUnidad);

            paramReporte.setIdTipoMovimientos(tipoEstatusCama);
            if (!cadenaFiltro.equals("")) {
                paramReporte.setCadenaBusqueda(cadenaFiltro);
            }
            List<String> estructuraList = estructuraService.obtenerIdsEstructuraJerarquica(idUnidad, false);
            estructuraList.add(idUnidad);

            EntidadHospitalaria entidad;
            entidad = entidadHospitalariaService.obtenerEntidadById(est.getIdEntidadHospitalaria());

            byte[] buffer = reportesService.imprimeReporteCamas(paramReporte, estructuraList, entidad);
            if (buffer != null) {
                status = Constantes.ACTIVO;
                SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "sesionMB");
                sesion.setReportStream(buffer);
                sesion.setReportName(String.format("reporteCamas_%s.pdf", FechaUtil.formatoFecha(new Date(), "yyyyMMdd")));
            }
        } catch (URISyntaxException e) {
            LOGGER.error("Error al generar la Impresión: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public void assignArea() {
        boolean status = Constantes.INACTIVO;
        if (repCama != null) {
            if (!nameUnidad.equals("")) {
                fullNameUnidad = repCama.getUbicacion() + "/" + nameUnidad;
                listEstructura.stream().filter(prdct -> prdct.getNombre().equals(nameUnidad)).forEachOrdered(cnsmr -> 
                    idUnidad = cnsmr.getIdEstructura()
                );
                status = Constantes.ACTIVO;
            } else {
                Mensaje.showMessage("Warn", RESOURCES.getString("repcama.warn.selectArea"), null);
            }
        }
        PrimeFaces.current().ajax().addCallbackParam("estatus3", status);
    }

    public void onNodeSelect(NodeSelectEvent event) {
        nameUnidad = event.getTreeNode().toString();
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
        if (repCama != null) {
            repCama.setUbiacion(pathNode);
        }
    }
    //Getter & Setter

    public RepCamaLazy getRepCamaLazy() {
        return repCamaLazy;
    }

    public void setRepCamaLazy(RepCamaLazy repCamaLazy) {
        this.repCamaLazy = repCamaLazy;
    }

    public List<Integer> getTipoEstatusCama() {
        return tipoEstatusCama;
    }

    public void setTipoEstatusCama(List<Integer> tipoEstatusCama) {
        this.tipoEstatusCama = tipoEstatusCama;
    }

    public String getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(String idUnidad) {
        this.idUnidad = idUnidad;
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

    public Estructura getEstructura() {
        return estructura;
    }

    public void setEstructura(Estructura estructura) {
        this.estructura = estructura;
    }

    public List<Estructura> getListEstructura() {
        return listEstructura;
    }

    public void setListEstructura(List<Estructura> listEstructura) {
        this.listEstructura = listEstructura;
    }

    public String getFullNameUnidad() {
        return fullNameUnidad;
    }

    public void setFullNameUnidad(String fullNameUnidad) {
        this.fullNameUnidad = fullNameUnidad;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public TreeNode getSelectNode() {
        return selectNode;
    }

    public void setSelectNode(TreeNode selectNode) {
        this.selectNode = selectNode;
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

    public String getIdRol() {
        return idRol;
    }

    public void setIdRol(String idRol) {
        this.idRol = idRol;
    }

    public List<String> getListNode() {
        return listNode;
    }

    public void setListNode(List<String> listNode) {
        this.listNode = listNode;
    }

    public RepCamaService getCamaServices() {
        return camaServices;
    }

    public void setCamaServices(RepCamaService camaServices) {
        this.camaServices = camaServices;
    }

    public RepCama getRepCama() {
        return repCama;
    }

    public void setRepCama(RepCama repCama) {
        this.repCama = repCama;
    }

    public List<RepCama> getRepCamaList() {
        return repCamaList;
    }

    public void setRepCamaList(List<RepCama> repCamaList) {
        this.repCamaList = repCamaList;
    }

    public EstatusCamaService getEstatusCamaService() {
        return estatusCamaService;
    }

    public void setEstatusCamaService(EstatusCamaService estatusCamaService) {
        this.estatusCamaService = estatusCamaService;
    }

    public EstatusCama getEstatusCama() {
        return estatusCama;
    }

    public void setEstatusCama(EstatusCama estatusCama) {
        this.estatusCama = estatusCama;
    }

    public List<EstatusCama> getEstatusCamaList() {
        return estatusCamaList;
    }

    public void setEstatusCamaList(List<EstatusCama> estatusCamaList) {
        this.estatusCamaList = estatusCamaList;
    }

    public Usuario getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Usuario currentUser) {
        this.currentUser = currentUser;
    }

    public Usuario getUsuarioSession() {
        return usuarioSession;
    }

    public void setUsuarioSession(Usuario usuarioSession) {
        this.usuarioSession = usuarioSession;
    }

    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }

    public ParamBusquedaReporte getParamReporte() {
        return paramReporte;
    }

    public void setParamReporte(ParamBusquedaReporte paramReporte) {
        this.paramReporte = paramReporte;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public String getPathPdf() {
        return pathPdf;
    }

    public void setPathPdf(String pathPdf) {
        this.pathPdf = pathPdf;
    }

    public boolean isHabilReporte() {
        return habilReporte;
    }

    public void setHabilReporte(boolean habilReporte) {
        this.habilReporte = habilReporte;
    }

    public String getCadenaFiltro() {
        return cadenaFiltro;
    }

    public void setCadenaFiltro(String cadenaFiltro) {
        this.cadenaFiltro = cadenaFiltro;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

}
