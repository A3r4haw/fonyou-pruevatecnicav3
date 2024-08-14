package mx.mc.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import mx.mc.enums.Accion_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.Rol;
import mx.mc.model.TransaccionPermisos;
import mx.mc.model.Usuario;
import mx.mc.model.Accion;
import mx.mc.model.PermisoUsuario;
import mx.mc.service.AccionService;
import mx.mc.service.UsuarioService;
import mx.mc.service.RolService;
import mx.mc.service.TransaccionService;
import mx.mc.util.Comunes;
import mx.mc.util.Mensaje;
import org.primefaces.PrimeFaces;
import org.primefaces.context.PrimeRequestContext;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
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
public class SeguridadMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(SeguridadMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    private boolean checkCrear;
    private boolean checkVer;
    private boolean checkEditar;
    private boolean checkEliminar;
    private boolean checkProcesar;
    private boolean checkAutorizar;

    private boolean msjRol;
    private boolean huboError;
    private String cadenaBusqueda;
    private boolean elementoSeleccionado;
    private boolean encontrado;
    private boolean seleccionado;
    private boolean select;
    private boolean modulo;
    private Date fechaActual;
    private String nombreUsuario;
    private int suma;
    private String tituloModal;
    private String errLista;    
    private String errAccion;
    private Usuario usuarioSelected;
    private PermisoUsuario permiso;
    private static TreeNode root;
    private static TreeNode selectNode;
    private static TreeNode[] selectedNodes;
    private List<TransaccionPermisos> transaccionesList;
    private TransaccionPermisos crear;
    private TransaccionPermisos ver;
    private TransaccionPermisos editar;
    private TransaccionPermisos eliminar;
    private TransaccionPermisos procesar;
    private TransaccionPermisos autorizar;
    private boolean editarRol;
    private String nombreAuxRol;

    @Autowired
    private transient RolService rolService;
    private Rol rolSelected;
    private List<Rol> rolList;
    private List<Rol> rolListSelected;    

    @Autowired
    private transient TransaccionService transaccionService;
     
    @Autowired
    private UsuarioService usuarioService;

    private List<TransaccionPermisos> permisosList;

    @Autowired
    private transient AccionService accionService;
    private List<Accion> accionesLis;

    /**
     * Consulta los permisos del usuario logueado Obtiene los roles registrados
     * obtiene la totalidad de permisos disponibles, para relacionarlos con cada
     * módulo
     */
    @PostConstruct
    public void init() {
        LOGGER.debug("mx.mc.magedbean.SeguridadMB.init()");
        errLista = "err.lista";        
        errAccion = "err.accion";
        initialize();
        obtieneAcciones();
        permiso = Comunes.obtenerPermisos(Transaccion_Enum.SEGURIDAD.getSufijo());
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        usuarioSelected = sesion.getUsuarioSelected();
        obtenerRoles();
        obtenerArbol();

    }

    /**
     * Inicializa las variables necesarias
     */
    private void initialize() {
        LOGGER.debug("mx.mc.magedbean.SeguridadMB.Initialize()");     
        editarRol = false;
        nombreAuxRol = "";
        elementoSeleccionado = false;
        msjRol = false;
        huboError = false;
        cadenaBusqueda = null;

        fechaActual = new java.util.Date();
        nombreUsuario = null;

        usuarioSelected = new Usuario();
        permisosList = new ArrayList<>();
        usuarioSelected.setPermisosList(permisosList);
        select = false;
        encontrado = false;
        selectNode = null;
        seleccionado = Constantes.INACTIVO;
        modulo = false;
        suma = 0;        
    }

    private void limpia() {
        LOGGER.debug("mx.mc.magedbean.SeguridadMB.limpia()");
        elementoSeleccionado = false;
        huboError = false;
        cadenaBusqueda = null;
        select = false;
        encontrado = false;
        selectNode = null;
        seleccionado = Constantes.ACTIVO;
        modulo = false;
        suma = 0;
        checkCrear = Constantes.INACTIVO;
        checkVer = Constantes.INACTIVO;
        checkEditar = Constantes.INACTIVO;
        checkEliminar = Constantes.INACTIVO;
        checkProcesar = Constantes.INACTIVO;
        checkAutorizar = Constantes.INACTIVO;
    }

    private void obtieneAcciones() {
        LOGGER.debug("mx.mc.magedbean.SeguridadMB.obtieneAcciones()");
        try {
            Accion a = new Accion();
            a.setActivo(Constantes.ACTIVO);
            accionesLis = new ArrayList<>();
            accionesLis.addAll(accionService.obtenerLista(a));
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString(errLista), ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errLista), null);
        }
    }

    /**
     * Busca Roles por normbe
     */
    public void obtenerRoles() {
        LOGGER.debug("mx.mc.magedbean.SeguridadMB.obtenerRoles()");

        boolean status = Constantes.INACTIVO;
        try {
            Rol r = new Rol();
            if (cadenaBusqueda != null && !cadenaBusqueda.trim().isEmpty()) {
                r.setNombre(cadenaBusqueda);
            }
            rolList = new ArrayList<>();
            rolList.addAll(rolService.obtenerListaTotal(r));
            if (rolList.isEmpty()) {
                rolList = new ArrayList<>();
            }
            status = Constantes.ACTIVO;

        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString(errLista), ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errLista), null);
        }
        PrimeRequestContext.getCurrentInstance().getCallbackParams().put(Constantes.ESTATUS_VIEW, status);
    }

    public void onRowSelect(SelectEvent event) {
        LOGGER.debug("mx.mc.magedbean.SeguridadMB.onRowSelect()");
        rolSelected = (Rol) event.getObject();
        elementoSeleccionado = rolSelected != null;
    }

    public void onRowUnselect() {
        LOGGER.debug("mx.mc.magedbean.SeguridadMB.onRowUnselect()");
        //usuarioSelected = null;
        elementoSeleccionado = false;
    }

    /**
     * Limpia Variables antes de crear un Rol
     */
    public void creaRegistro() {
        LOGGER.debug("mx.mc.magedbean.SeguridadMB.creaRegistro()");
        tituloModal = Constantes.TITULO_CREA_ROL;
        msjRol = true;
        rolSelected = new Rol();
        selectNode = null;
        elementoSeleccionado = false;
        for (TreeNode node : root.getChildren()) {
            node.setSelected(false);
            if (node.getChildCount() > 0) {
                unselectNodeTreeAll(node, false);
            }
        }
        cleanCkeckPermiso();
        limpia();
    }

    public void cancelarRegistro() {
        LOGGER.debug("mx.mc.magedbean.SeguridadMB.creaRegistro()");
        msjRol = false;
        rolSelected = new Rol();
        selectNode = null;
        elementoSeleccionado = false;
        for (TreeNode node : root.getChildren()) {
            node.setSelected(false);
            if (node.getChildCount() > 0) {
                unselectNodeTreeAll(node, false);
            }
        }
        cleanCkeckPermiso();
        limpia();
    }
    
    /**
     * Carga los valores de un Rol
     */
    public void editaRegistro() {
        LOGGER.debug("mx.mc.magedbean.SeguridadMB.editaRegistro()");
        try {
            editarRol = true;
            nombreAuxRol = rolSelected.getNombre();
            tituloModal = Constantes.TITULO_EDIT_ROL;
            msjRol = true;
            List<TransaccionPermisos> permisosAsignadosRol = new ArrayList<>();
            String idTrans = "";
            if (rolSelected != null
                    && rolSelected.getIdRol() != null) {
                
                selectNode = null;
                for (TreeNode node : root.getChildren()) {
                    node.setSelected(false);
                    if (node.getChildCount() > 0) {
                        unselectNodeTreeAll(node, false);
                    }
                }

                limpia();
                permisosAsignadosRol.addAll(transaccionService.obtenerPermisosAsignadosyDisponiblesRol(rolSelected.getIdRol()));
                for (TransaccionPermisos ta : permisosAsignadosRol) {
                    transaccionesList.stream().filter(prdct -> prdct.getNombreTransaccion().equals(ta.getNombreTransaccion())).forEach(trans -> {
                        switch (ta.getAccion()) {
                            case "CREAR":
                                trans.setPuedeCrear(Constantes.ACTIVO);
                                break;
                            case "VER":
                                trans.setPuedeVer(Constantes.ACTIVO);
                                break;
                            case "EDITAR":
                                trans.setPuedeEditar(Constantes.ACTIVO);
                                break;
                            case "ELIMINAR":
                                trans.setPuedeEliminar(Constantes.ACTIVO);
                                break;
                            case "PROCESAR":
                                trans.setPuedeProcesar(Constantes.ACTIVO);
                                break;
                            case "AUTORIZAR":
                                trans.setPuedeAutorizar(Constantes.ACTIVO);
                                break;
                            default:
                        }

                    });
                    if (!idTrans.equals(ta.getIdTransaccion())) {
                        selectNodeTree(ta.getNombreTransaccion(), Constantes.ACTIVO);
                    }
                }
                cleanCkeckPermiso();
                limpia();
                
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());

        }
    }

    /**
     * Valida si puede inactivar un Rol
     */
    public void validaIactivaRegistro() {
        LOGGER.debug("mx.mc.magedbean.SeguridadMB.validaIactivaRegistro()");
        if (!permiso.isPuedeEditar()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errAccion), null);

        } else if (rolSelected == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.registro.incorrecto"), null);
        }

    }

    /**
     * Inactiva un Rol
     */
    public void inactivaRegistro() {
        LOGGER.debug("mx.mc.magedbean.SeguridadMB.inactivaRegistro()");

        boolean estatus = Constantes.INACTIVO;
        String valorActivo = "";
        String valorError = "";
        try {
            Rol r = new Rol();
            r.setIdRol(rolSelected.getIdRol());
            r.setActivo(!rolSelected.isActivo());
            r.setUpdateFecha(new java.util.Date());
            r.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
            
            if(r.isActivo()){
                 valorActivo = "ok.activar";
                 valorError = "err.activar";
            }else{
                 valorActivo = "ok.inactivar";
                 valorError = "err.inactivar";                         
            }
            estatus = rolService.actualizar(r);
            if (estatus) {
                Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString(valorActivo) + " " + rolSelected.getNombre(), null);
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(valorError) + " " + rolSelected.getNombre(), null);
            }
            rolList.stream().filter((rol) -> (rol.getIdRol().equals(r.getIdRol()))).forEachOrdered((rol) -> {
                rol.setActivo(!rolSelected.isActivo());
            });
        } catch (Exception ex) {
            LOGGER.error("{} {} {}", RESOURCES.getString(valorError), rolSelected.getNombre(), ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(valorError) + " " + rolSelected.getNombre(), null);

        }

        PrimeRequestContext.getCurrentInstance().getCallbackParams().put(Constantes.ESTATUS_VIEW, estatus);        
    }

    /**
     * valida si puede eliminar un Rol
     */
    public void validaEliminaRegistro() {
        LOGGER.debug("mx.mc.magedbean.SeguridadMB.validaEliminaRegistro()");

        if (!permiso.isPuedeEliminar()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errAccion), null);

        } else if (rolSelected == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.registro.incorrecto"), null);

        }

    }

    /**
     * Elimina un Rol
     */
    public void eliminaRegistro() {
        LOGGER.debug("mx.mc.magedbean.SeguridadMB.eliminaRegistro()");
        String errEliminar = "rol.err.eliminar";
        List<Usuario> usuarioRoles = new ArrayList<>();
        String cadenaUsers = "";
        StringBuilder sb = new StringBuilder();
        boolean estatus = false;
        elementoSeleccionado = false;
        onRowUnselect();
        try {                                   
            usuarioRoles = usuarioService.getAllUsersByIdRol(rolSelected.getIdRol());
            if(usuarioRoles != null && !usuarioRoles.isEmpty()){
                for(Usuario user : usuarioRoles){
                    sb.append(user.getNombreUsuario()).append(",  ");                    
                }
                cadenaUsers = sb.toString();
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errEliminar) + rolSelected.getNombre()+" .Debido a los Usuarios Ligados: "+ cadenaUsers, null);
            } else {
                Rol r = new Rol();
                r.setIdRol(rolSelected.getIdRol());

                estatus = rolService.eliminaRolyPermisos(r);
                if (estatus) {
                    List<Rol> rolesRegistradosList = new ArrayList<>();
                    rolesRegistradosList.addAll(rolList);
                    ListIterator<Rol> iter = rolesRegistradosList.listIterator();
                    while (iter.hasNext()) {
                        if (iter.next().getIdRol().equals(rolSelected.getIdRol())) {
                            iter.remove();
                        }
                    }
                    rolList = new ArrayList<>();
                    rolList.addAll(rolesRegistradosList);
                    rolSelected = new Rol();

                    Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("ok.eliminar"), null);

                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errEliminar), null);
                }
            }
            
        } catch (Exception ex) {
            LOGGER.error("{} {} {}", RESOURCES.getString(errEliminar), rolSelected.getNombre(), ex.getMessage());
            //Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errEliminar) + rolSelected.getNombre()+" .Debido a los Usuarios Ligados: "+ cadenaUsers, null);

        }

        PrimeRequestContext.getCurrentInstance().getCallbackParams().put(Constantes.ESTATUS_VIEW, estatus);
    }

    /**
     * Valida la creación o edición de un Rol
     */
    public void validaRol() {
        LOGGER.debug("mx.mc.magedbean.SeguridadMB.validaRol()");
        boolean status = Constantes.INACTIVO;

        if (!permiso.isPuedeCrear() && !permiso.isPuedeEditar()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errAccion), null);
        } else {
            try {
                String nombreRol = rolService.getRolByName(rolSelected.getNombre());
                if (rolSelected.getNombre().equals(nombreRol)
                        && editarRol) {
                    if (nombreAuxRol.equals(nombreRol)) {
                        nombreRol = null;
                    } else {
                        rolSelected.setNombre(nombreAuxRol);
                    }
                }

                if (rolSelected.getNombre() == null || rolSelected.getNombre().trim().isEmpty()) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("req.nombre"), null);
                    msjRol = false;
                } else if (rolSelected.getDescripcion() == null || rolSelected.getDescripcion().trim().isEmpty()) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("req.desc"), null);
                    msjRol = false;
                } else if (nombreRol != null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, nombreRol + ", " + RESOURCES.getString("rol.err.rolExistente"), null);
                    msjRol = false;
                } else {
                    List<TransaccionPermisos> listaPermisos = new ArrayList<>();

                    transaccionesList.forEach(trans -> {
                        if (trans.isPuedeCrear()) {
                            crear = new TransaccionPermisos();
                            crear.setPermitido(Constantes.ACTIVO);
                            crear.setIdTransaccion(trans.getIdTransaccion());
                            accionesLis.stream().filter(prdct -> prdct.getNombre().equals(Accion_Enum.CREAR.getValue())).forEach(accion -> 
                                crear.setIdAccion(accion.getIdAccion())
                            );
                            listaPermisos.add(crear);
                        }
                        if (trans.isPuedeVer()) {
                            ver = new TransaccionPermisos();
                            ver.setPermitido(Constantes.ACTIVO);
                            ver.setIdTransaccion(trans.getIdTransaccion());
                            accionesLis.stream().filter(prdct -> prdct.getNombre().equals(Accion_Enum.VER.getValue())).forEach(accion -> 
                                ver.setIdAccion(accion.getIdAccion())
                            );
                            listaPermisos.add(ver);
                        }
                        if (trans.isPuedeEditar()) {
                            editar = new TransaccionPermisos();
                            editar.setPermitido(Constantes.ACTIVO);
                            editar.setIdTransaccion(trans.getIdTransaccion());
                            accionesLis.stream().filter(prdct -> prdct.getNombre().equals(Accion_Enum.EDITAR.getValue())).forEach(accion -> 
                                editar.setIdAccion(accion.getIdAccion())
                            );
                            listaPermisos.add(editar);
                        }
                        if (trans.isPuedeEliminar()) {
                            eliminar = new TransaccionPermisos();
                            eliminar.setPermitido(Constantes.ACTIVO);
                            eliminar.setIdTransaccion(trans.getIdTransaccion());
                            accionesLis.stream().filter(prdct -> prdct.getNombre().equals(Accion_Enum.ELIMINAR.getValue())).forEach(accion -> 
                                eliminar.setIdAccion(accion.getIdAccion())
                            );
                            listaPermisos.add(eliminar);
                        }
                        if (trans.isPuedeProcesar()) {
                            procesar = new TransaccionPermisos();
                            procesar.setPermitido(Constantes.ACTIVO);
                            procesar.setIdTransaccion(trans.getIdTransaccion());
                            accionesLis.stream().filter(prdct -> prdct.getNombre().equals(Accion_Enum.PROCESAR.getValue())).forEach(accion -> 
                                procesar.setIdAccion(accion.getIdAccion())
                            );
                            listaPermisos.add(procesar);
                        }
                        if (trans.isPuedeAutorizar()) {
                            autorizar = new TransaccionPermisos();
                            autorizar.setPermitido(Constantes.ACTIVO);
                            autorizar.setIdTransaccion(trans.getIdTransaccion());
                            accionesLis.stream().filter(prdct -> prdct.getNombre().equals(Accion_Enum.AUTORIZAR.getValue())).forEach(accion -> 
                                autorizar.setIdAccion(accion.getIdAccion())
                            );
                            listaPermisos.add(autorizar);
                        }
                    });

                    if (!listaPermisos.isEmpty()) {
                        status = registrarRol(listaPermisos);
                        limpia();
                        if (status) {
                            msjRol = false;
                        }
                    } else {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sec.rol.permisomin"), null);
                        msjRol = false;
                    }
                }

            } catch (Exception ex) {
                LOGGER.error(RESOURCES.getString("err.validar"), ex);
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.validar"), null);
            }
        }

        PrimeFaces.current().executeScript("PF('tblRoles').unselectAllRows()");
        PrimeRequestContext.getCurrentInstance().getCallbackParams().put(Constantes.ESTATUS_VIEW, status);
        editarRol = false;
    }

    /**
     * Creación o Actualización de un Rol
     *
     * @param listaPermisos
     * @return
     */
    private boolean registrarRol(List<TransaccionPermisos> listaPermisos) {
        LOGGER.debug("mx.mc.magedbean.SeguridadMB.registrarRol()");
        boolean status = Constantes.INACTIVO;
        String errRegistrar = "err.registrar";
        try {
            if (rolSelected.getIdRol() == null) {
                rolSelected.setIdRol(Comunes.getUUID());
                rolSelected.setInsertFecha(new java.util.Date());
                rolSelected.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                if (permiso.isPuedeCrear()) {
                    status = rolService.registraRolyPermisos(rolSelected, listaPermisos, Constantes.INSERT);
                    rolList.add(rolSelected);
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("ok.registrar"), null);

                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistrar), null);
                }

            } else {
                rolSelected.setUpdateFecha(new java.util.Date());
                rolSelected.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
                if (permiso.isPuedeEditar()) {
                    status = rolService.registraRolyPermisos(rolSelected, listaPermisos, Constantes.UPDATE);
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("ok.actualizar"), null);

                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.actualizar"), null);

                }
            }

        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString(errRegistrar), ex);
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistrar), null);
            rolSelected = new Rol();
        }

        return status;
    }

    /**
     * Metodo que llena el arol de Transacciones
     *
     */
    public void obtenerArbol() {
        try {
            String aux = "";
            obtenerUnidadesJerarquicas();
            root = new DefaultTreeNode("Root", null);
            for (TransaccionPermisos tp : transaccionesList) {
                if (!tp.getModulo().equals(aux)) {
                    aux = tp.getModulo();
                    TreeNode node = new DefaultTreeNode(tp.getModulo(), root);
                    tp.setIdNodeParent(node.getRowKey());

                    TreeNode chil = new DefaultTreeNode(tp.getNombreTransaccion(), node);
                    tp.setIdNode(chil.getRowKey());
                } else {
                    obtenerPadre(root, tp);
                }
            }
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
    private void obtenerPadre(TreeNode nodo, TransaccionPermisos tp) {

        List<TreeNode> nodoList = nodo.getChildren();
        for (TreeNode auxNodo : nodoList) {
            if (auxNodo.getData().equals(tp.getModulo())) {
                TreeNode chil = new DefaultTreeNode(tp.getNombreTransaccion(), auxNodo);
                tp.setIdNodeParent(auxNodo.getRowKey());
                tp.setIdNode(chil.getRowKey());
            } else {
                obtenerPadre(auxNodo, tp);
            }
        }
    }

    /**
     *
     * Metodo para obtener la lista de unidades
     *
     */
    public void obtenerUnidadesJerarquicas() {
        try {
            transaccionesList = transaccionService.obtenerTraccionesAll();
            int x=-1;
            for(TransaccionPermisos tr :transaccionesList){
                x++;
                if(tr.getNombreTransaccion().equals(Transaccion_Enum.CONFIGURACION.getValue()))
                    break;
            }
            if(x>-1 && !usuarioSelected.getPermisosList().get(0).getRol().equals("Total"))
                transaccionesList.remove(x);
            
        } catch (Exception ex) {
            LOGGER.error("Error al obtener unidades jerarquicas: {}", ex.getMessage());
        }
    }

    public void onNodeSelect(NodeSelectEvent event) {
        try {
            TreeNode aux = event.getTreeNode();

            if (selectNode != null && selectNode != aux) {
                select = Constantes.INACTIVO;
                encontrado = Constantes.INACTIVO;
                modulo = selectNode.getParent().getRowKey().equals("root");
                if (modulo) {
                    suma = 0;
                    for (TreeNode nod : selectNode.getChildren()) {
                        select = Constantes.INACTIVO;
                        transaccionesList.stream().filter(prdct -> prdct.getIdNode().equals(nod.getRowKey())).forEach(trans -> {
                            if (trans.isPuedeVer() || trans.isPuedeCrear() || trans.isPuedeEditar() || trans.isPuedeEliminar() || trans.isPuedeProcesar() || trans.isPuedeAutorizar()) {
                                select = Constantes.ACTIVO;
                                suma++;
                            }
                        });
                        selectNodeTree(nod.getData().toString(), select);
                    }
                    if (selectNode.getChildCount() == suma) {
                        selectNodeTree(selectNode.getData().toString(), true);
                    }
                } else {
                    transaccionesList.stream().filter(prdct -> prdct.getIdNode().equals(selectNode.getRowKey())).forEach(trans -> {
                        if ((trans.isPuedeVer() || trans.isPuedeCrear() || trans.isPuedeEditar() || trans.isPuedeEliminar() || trans.isPuedeProcesar() || trans.isPuedeAutorizar()) && !encontrado) {
                            select = Constantes.ACTIVO;
                            encontrado = Constantes.ACTIVO;
                        }
                    });
                    selectNodeTree(selectNode.getData().toString(), select);
                }

            }

            selectNode = aux;
            seleccionado = Constantes.INACTIVO;
            selectNodeTree(selectNode.getData().toString(), true);
            searchNodeList(selectNode.getRowKey());
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    public void onNodeUnselect(NodeUnselectEvent event) {
        try {
            TreeNode aux = event.getTreeNode();
            if (selectNode != null && selectNode != aux) {
                select = Constantes.INACTIVO;
                encontrado = Constantes.INACTIVO;
                modulo = selectNode.getParent().getRowKey().equals("root");
                if (modulo) {
                    suma = 0;
                    for (TreeNode nod : selectNode.getChildren()) {
                        select = Constantes.INACTIVO;
                        transaccionesList.stream().filter(prdct -> prdct.getIdNode().equals(nod.getRowKey())).forEach(trans -> {
                            if (trans.isPuedeVer() || trans.isPuedeCrear() || trans.isPuedeEditar() || trans.isPuedeEliminar() || trans.isPuedeProcesar() || trans.isPuedeAutorizar()) {
                                select = Constantes.ACTIVO;
                                suma++;
                            }
                        });
                        selectNodeTree(nod.getData().toString(), select);
                    }
                    if (selectNode.getChildCount() == suma) {
                        selectNodeTree(selectNode.getData().toString(), true);
                    }
                } else {
                    transaccionesList.stream().filter(prdct -> prdct.getIdNode().equals(selectNode.getRowKey())).forEach(trans -> {
                        if ((trans.isPuedeVer() || trans.isPuedeCrear() || trans.isPuedeEditar() || trans.isPuedeEliminar() || trans.isPuedeProcesar() || trans.isPuedeAutorizar()) && !encontrado) {
                            select = Constantes.ACTIVO;
                            encontrado = Constantes.ACTIVO;
                        }
                    });
                    selectNodeTree(selectNode.getData().toString(), select);
                }

            }

            select = Constantes.INACTIVO;
            encontrado = Constantes.INACTIVO;
            transaccionesList.stream().filter(prdct -> (prdct.getIdNode().equals(aux.getRowKey())) || prdct.getIdNodeParent().equals(aux.getRowKey())).forEach(trans -> {
                if ((trans.isPuedeVer() || trans.isPuedeCrear() || trans.isPuedeEditar() || trans.isPuedeEliminar() || trans.isPuedeProcesar() || trans.isPuedeAutorizar()) && !encontrado) {
                    select = Constantes.ACTIVO;
                    encontrado = Constantes.ACTIVO;
                }
            });

            if (aux != null) {
                if (selectNode == aux && !select) {
                    selectNode = null;
                    seleccionado = Constantes.ACTIVO;
                    selectNodeTree(aux.getData().toString(), select);
                } else {
                    selectNode = aux;
                    seleccionado = Constantes.INACTIVO;
                    selectNodeTree(aux.getData().toString(), true);
                }
                searchNodeList(aux.getRowKey());
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    private void unselectNodeTree(TreeNode treeNode, boolean status) {
        treeNode.setExpanded(status);
        for (TreeNode node : treeNode.getChildren()) {
            if (!status) {
                select = Constantes.INACTIVO;
                encontrado = Constantes.INACTIVO;
                transaccionesList.stream().filter(prdct -> (prdct.getIdNode().equals(node.getRowKey())) || prdct.getIdNodeParent().equals(node.getRowKey())).forEach(trans -> {
                    if ((trans.isPuedeVer() || trans.isPuedeCrear() || trans.isPuedeEditar() || trans.isPuedeEliminar() || trans.isPuedeProcesar() || trans.isPuedeAutorizar()) && !encontrado) {
                        select = Constantes.ACTIVO;
                        encontrado = Constantes.ACTIVO;
                    }
                });
                node.setSelected(select);
            } else {
                node.setSelected(status);
            }

            if (node.getChildCount() > 0) {
                unselectNodeTree(node, status);
            }
        }
    }

    private void unselectNode(TreeNode treeNode, boolean status) {
        treeNode.setExpanded(status);
        for (TreeNode node : treeNode.getChildren()) {
            node.setSelected(status);
            if (!status) {
                transaccionesList.stream().filter(prdct -> prdct.getNombreTransaccion().equals(node.getData())).forEach(trans -> {
                    trans.setPuedeCrear(Constantes.INACTIVO);
                    trans.setPuedeVer(Constantes.INACTIVO);
                    trans.setPuedeEditar(Constantes.INACTIVO);
                    trans.setPuedeEliminar(Constantes.INACTIVO);
                    trans.setPuedeProcesar(Constantes.INACTIVO);
                    trans.setPuedeAutorizar(Constantes.INACTIVO);
                });
            }
            if (node.getChildCount() > 0) {
                unselectNode(node, status);
            }
        }
    }

    private void unselectNodeTreeAll(TreeNode treeNode, boolean status) {
        treeNode.setExpanded(status);
        for (TreeNode node : treeNode.getChildren()) {
            node.setSelected(status);
            if (node.getChildCount() > 0) {
                unselectNodeTree(node, status);
            }

            if (!status) {
                transaccionesList.stream().filter(prdct -> prdct.getNombreTransaccion().equals(node.getData())).forEach(trans -> {
                    trans.setPuedeCrear(Constantes.INACTIVO);
                    trans.setPuedeVer(Constantes.INACTIVO);
                    trans.setPuedeEditar(Constantes.INACTIVO);
                    trans.setPuedeEliminar(Constantes.INACTIVO);
                    trans.setPuedeProcesar(Constantes.INACTIVO);
                    trans.setPuedeAutorizar(Constantes.INACTIVO);
                });
            }
        }
    }

    public void chilParent(TreeNode parent) {
        if (parent.getChildCount() > 0) {
            int chilCount = parent.getChildCount();
            suma = 0;
            for (TreeNode chil : parent.getChildren()) {
                if (chil.isSelected()) {
                    transaccionesList.stream().filter(prdct -> prdct.getIdNode().equals(chil.getRowKey())).forEach(trans -> {
                        if (trans.isPuedeVer() || trans.isPuedeCrear() || trans.isPuedeEditar() || trans.isPuedeEliminar() || trans.isPuedeProcesar() || trans.isPuedeAutorizar()) {
                            suma++;
                        }
                    });
                }
            }
            parent.setSelected((suma == chilCount));
        }
    }

    private void cleanCkeckPermiso() {
        checkCrear = Constantes.INACTIVO;
        checkVer = Constantes.INACTIVO;
        checkEditar = Constantes.INACTIVO;
        checkEliminar = Constantes.INACTIVO;
        checkProcesar = Constantes.INACTIVO;
        checkAutorizar = Constantes.INACTIVO;
    }

    private void selectNodeTree(String name, boolean estatus) {
        try {
            encontrado = Constantes.INACTIVO;
            for (TreeNode node : root.getChildren()) {
                if (node.getData().toString().equals(name)) {
                    node.setSelected(estatus);
                    if (node.getChildCount() > 0) {
                        unselectNodeTree(node, estatus);
                    }
                    break;
                } else if (node.getChildCount() > 0) {
                    searchNodeTree(node, name, estatus);
                    if (encontrado) {
                        break;
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    private void searchNodeTree(TreeNode node, String name, boolean estatus) {
        for (TreeNode _node : node.getChildren()) {
            if (_node.getData().toString().equals(name)) {
                _node.setSelected(estatus);
                if (_node.getParent() != null && _node.getParent().getData() != "Root") {
                    _node.getParent().setExpanded(true);
                    chilParent(_node.getParent());
                }
                if (_node.getChildCount() > 0) {
                    unselectNodeTree(_node, estatus);
                }
                encontrado = true;
                break;
            } else if (_node.getChildCount() > 0) {
                searchNodeTree(_node, name, estatus);
            }
        }
    }

    private void searchNodeList(String idTrans) {
        encontrado = Constantes.INACTIVO;
        transaccionesList.stream().filter(prdct -> (prdct.getIdNode().equals(idTrans) || prdct.getIdNodeParent().equals(idTrans))).forEach(trans -> {
            if (!encontrado) {
                checkCrear = trans.isPuedeCrear();
                checkVer = trans.isPuedeVer();
                checkEditar = trans.isPuedeEditar();
                checkEliminar = trans.isPuedeEliminar();
                checkProcesar = trans.isPuedeProcesar();
                checkAutorizar = trans.isPuedeAutorizar();
                encontrado = Constantes.ACTIVO;
            }
        });
    }

    public void changeCheck(int opc) {
        try {
            if (selectNode != null) {
                if (!selectNode.getChildren().isEmpty()) {
                    for (TreeNode nod : selectNode.getChildren()) {
                        transaccionesList.stream().filter(prdct -> prdct.getIdNode().equals(nod.getRowKey())).forEach(trans -> {
                            switch (opc) {
                                case 1:
                                    trans.setPuedeCrear(checkCrear);
                                    break;
                                case 2:
                                    trans.setPuedeVer(checkVer);
                                    break;
                                case 3:
                                    trans.setPuedeEditar(checkEditar);
                                    break;
                                case 4:
                                    trans.setPuedeEliminar(checkEliminar);
                                    break;
                                case 5:
                                    trans.setPuedeProcesar(checkProcesar);
                                    break;
                                case 6:
                                    trans.setPuedeAutorizar(checkAutorizar);
                                    break;
                                default:
                            }
                        });
                    }
                } else {
                    transaccionesList.stream().filter(prdct -> prdct.getIdNode().equals(selectNode.getRowKey())).forEach(trans -> {
                        switch (opc) {
                            case 1:
                                trans.setPuedeCrear(checkCrear);
                                break;
                            case 2:
                                trans.setPuedeVer(checkVer);
                                break;
                            case 3:
                                trans.setPuedeEditar(checkEditar);
                                break;
                            case 4:
                                trans.setPuedeEliminar(checkEliminar);
                                break;
                            case 5:
                                trans.setPuedeProcesar(checkProcesar);
                                break;
                            case 6:
                                trans.setPuedeAutorizar(checkAutorizar);
                                break;
                            default:
                        }
                    });
                }
                if (selectNode.getParent() != null && selectNode.getParent().getData() != "Root") {
                    chilParent(selectNode.getParent());
                }
            } else {
                checkCrear = Constantes.INACTIVO;
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

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

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Usuario getUsuarioSelected() {
        return usuarioSelected;
    }

    public void setUsuarioSelected(Usuario usuarioSelected) {
        this.usuarioSelected = usuarioSelected;
    }

    public Rol getRolSelected() {
        return rolSelected;
    }

    public void setRolSelected(Rol rolSelected) {
        this.rolSelected = rolSelected;
    }

    public List<Rol> getRolList() {
        return rolList;
    }

    public void setRolList(List<Rol> rolList) {
        this.rolList = rolList;
    }

    public Integer getSizeRolList() {
        if (rolList != null) {
            return rolList.size();
        } else {
            return 0;
        }
    }

    public List<Rol> getRolListSelected() {
        return rolListSelected;
    }

    public void setRolListSelected(List<Rol> rolListSelected) {
        this.rolListSelected = rolListSelected;
    }

    public boolean isElementoSeleccionado() {
        return elementoSeleccionado;
    }

    public void setElementoSeleccionado(boolean elementoSeleccionado) {
        this.elementoSeleccionado = elementoSeleccionado;
    }

    public List<TransaccionPermisos> getPermisosList() {
        return permisosList;
    }

    public void setPermisosList(List<TransaccionPermisos> permisosList) {
        this.permisosList = permisosList;
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

    public List<TransaccionPermisos> getTransaccionesList() {
        return transaccionesList;
    }

    public void setTransaccionesList(List<TransaccionPermisos> transaccionesList) {
        this.transaccionesList = transaccionesList;
    }

    public boolean isCheckCrear() {
        return checkCrear;
    }

    public void setCheckCrear(boolean checkCrear) {
        this.checkCrear = checkCrear;
    }

    public boolean isCheckVer() {
        return checkVer;
    }

    public void setCheckVer(boolean checkVer) {
        this.checkVer = checkVer;
    }

    public boolean isCheckEditar() {
        return checkEditar;
    }

    public void setCheckEditar(boolean checkEditar) {
        this.checkEditar = checkEditar;
    }

    public boolean isCheckEliminar() {
        return checkEliminar;
    }

    public void setCheckEliminar(boolean checkEliminar) {
        this.checkEliminar = checkEliminar;
    }

    public boolean isCheckProcesar() {
        return checkProcesar;
    }

    public void setCheckProcesar(boolean checkProcesar) {
        this.checkProcesar = checkProcesar;
    }

    public boolean isCheckAutorizar() {
        return checkAutorizar;
    }

    public void setCheckAutorizar(boolean checkAutorizar) {
        this.checkAutorizar = checkAutorizar;
    }

    public TreeNode[] getSelectedNodes() {
        return selectedNodes;
    }

    public void setSelectedNodes(TreeNode[] selectedNodes) {
        this.selectedNodes = selectedNodes;
    }

    public boolean isMsjRol() {
        return msjRol;
    }

    public void setMsjRol(boolean msjRol) {
        this.msjRol = msjRol;
    }

    public String getTituloModal() {
        return tituloModal;
    }

    public void setTituloModal(String tituloModal) {
        this.tituloModal = tituloModal;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

}
