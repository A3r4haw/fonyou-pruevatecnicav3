package mx.mc.magedbean;

import java.util.Calendar;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import mx.mc.lazy.RepCostPerCapServLazy;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.DataResultReport;
import mx.mc.model.Estructura;
import mx.mc.model.PermisoUsuario;
import mx.mc.service.EstructuraService;
import mx.mc.service.RepCostPerCapServService;
import mx.mc.util.Comunes;
import mx.mc.util.FechaUtil;
import mx.mc.util.Mensaje;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author aortiz
 *
 */
@Controller
@Scope(value = "view")
public class RepCostPerCapServMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(RepCostPerCapServMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);


    private static TreeNode root;
    private static TreeNode selectedNode;
    private Date fechaInicio;
    private Date fechaFin;
    private LazyDataModel<DataResultReport> lazyModel;
    private List<Estructura> listEstructura;
    private PermisoUsuario permiso;
    @Autowired
    private transient RepCostPerCapServService repCostPerCapServService;

    @Autowired
    private transient EstructuraService estructuraService;

    /**
     * Metodo que se ejecuta despues de cargar la pantalla de pacientes
     */
    @PostConstruct
    public void init() {
        try {
            initialize();
            permiso = Comunes.obtenerPermisos(Transaccion_Enum.REPORTESCOSTOS.getSufijo());
            obtenerArbol();
        } catch (Exception e) {
            LOGGER.error("Error en el metodo init :: {}", e.getMessage());
        }
    }

    /**
     * Metodo que inicializa todos los atributos de la clase
     */
    public void initialize() {
        try {            
            this.fechaInicio = FechaUtil.getFechaActual();
            this.fechaFin = FechaUtil.obtenerFechaFin();
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error en el metodo initialize: {}", ex.getMessage());
        }
    }

    /**
     * Metodo para realizar la consulta desde la vista
     *
     */
    public void consultar() {
        try {
            if (!this.permiso.isPuedeVer()) {
                Mensaje.showMessage("Error", RESOURCES.getString("estr.err.permisos"), null);
                return;
            }
            if (this.selectedNode == null) {
                Mensaje.showMessage("Error", RESOURCES.getString("repCostPerCapServ.err.selecUnidad"), null);
                return;
            }
            Estructura estructura = (Estructura) this.selectedNode.getData();
            List<DataResultReport> listaDatos = repCostPerCapServService.obtenerDatosRepCostPerCapServ(
                    estructura.getIdEstructura(), this.fechaInicio, this.fechaFin);
            this.lazyModel = new RepCostPerCapServLazy(listaDatos);
        } catch (Exception e1) {
            LOGGER.error("Error en el metodo consultar", e1);
        }
    }

    /**
     * Metodo para obtener la fecha fin con el formato 23:59 en lugar de 00:00
     */
    public void obtenerFechaFinal() {        
        fechaFin = FechaUtil.obtenerFechaFinal(fechaFin);
    }

    /**
     *
     * Metodo para crear arbol de estructura
     *
     */
    public void obtenerArbol() {
        try {
            obtenerUnidadesJerarquicas();
            root = new DefaultTreeNode("Root", null);
            TreeNode node = new DefaultTreeNode(listEstructura.get(0), root);
            for (int i = 0; i < listEstructura.size(); i++) {
                for (Estructura et : listEstructura) {
                    if (et.getIdEstructuraPadre() != null
                            && et.getIdEstructuraPadre().equals(listEstructura.get(i).getIdEstructura())) {
                        if (i == 0) {
                            node.getChildren().add(new DefaultTreeNode(et));
                        }
                        obtenerPadre(node, listEstructura.get(i), et);
                    }
                }
            }

        } catch (Exception ex) {
            LOGGER.error("Error al obtener unidades jerarquicas: {}", ex.getMessage());
        }

    }

    /**
     *
     * Metodo para obtener la lista de unidades
     *
     */
    public void obtenerUnidadesJerarquicas() {
        try {
            listEstructura = estructuraService.obtenerUnidadesOrderTipo();
        } catch (Exception ex) {
            LOGGER.error("Error al obtener unidades jerarquicas: {}", ex.getMessage());
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
    private void obtenerPadre(TreeNode nodo, Estructura nodoPadre, Estructura nodoHijo) {

        List<TreeNode> nodoList = nodo.getChildren();
        for (TreeNode auxNodo : nodoList) {
            if (auxNodo.getData().equals(nodoPadre)) {
                auxNodo.getChildren().add(new DefaultTreeNode(nodoHijo));
            } else {
                obtenerPadre(auxNodo, nodoPadre, nodoHijo);
            }
        }

    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public LazyDataModel<DataResultReport> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<DataResultReport> lazyModel) {
        this.lazyModel = lazyModel;
    }

    public List<Estructura> getListEstructura() {
        return listEstructura;
    }

    public void setListEstructura(List<Estructura> listEstructura) {
        this.listEstructura = listEstructura;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }
    
}
