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
import mx.com.avg.model.DoveTrack;
import mx.mc.enums.TipoAreaEstructura_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.Estructura;
import mx.mc.model.Medicamento;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.Usuario;
import mx.mc.service.DoveTrackService;
import mx.mc.service.MedicamentoService;
import mx.mc.service.UsuarioService;
import mx.mc.util.Comunes;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import mx.mc.util.FechaUtil;
import mx.mc.util.Mensaje;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Ulai
 */
@Controller
@Scope(value = "view")
public class DoveTrackMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(DoveTrackMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private ParamBusquedaReporte paramBusquedaReporte;
    private List<String> selectServiceList;
    private Date fechaActualInicio;
    private Date fechaActual;
    private Usuario usuarioSession;
    private PermisoUsuario permiso;
    private List<Estructura> listAreaEstructura;
    private List<DoveTrack> listDataResultReporte;
    
    @Autowired
    private transient MedicamentoService medicamentoService;

    @Autowired
    private transient UsuarioService usuarioService;
    
    @Autowired
    private transient DoveTrackService doveTrackService;

    /**
     * Metodo que se ejecuta despues de cargar la pantalla de pacientes
     */
    @PostConstruct
    public void init() {
        try {
            listAreaEstructura = new ArrayList<>();
            listDataResultReporte = new ArrayList<>();
            this.usuarioSession = Comunes.obtenerUsuarioSesion();            
            initialize();
            permiso = Comunes.obtenerPermisos(Transaccion_Enum.REPORTEDOVETRACK.getSufijo());
        } catch (Exception e) {
            LOGGER.error("Error en el metodo init :: {}", e.getMessage());
        }
    }

    /**
     * Metodo que inicializa todos los atributos de la clase
     */
    public void initialize() {
        try {
            List<Integer> listTipoAreaEstructuraIntAlmacen = new ArrayList<>();
            listTipoAreaEstructuraIntAlmacen.add(TipoAreaEstructura_Enum.CONSULTAINTERNA.getValue());           
            listAreaEstructura = doveTrackService.getAllEstructura(listTipoAreaEstructuraIntAlmacen);            
            fechaActual = new java.util.Date();            
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener la lista de tipos de Movimiento: {}", ex.getMessage());
        }
        paramBusquedaReporte = new ParamBusquedaReporte();        
        paramBusquedaReporte.setFechaInicio(FechaUtil.obtenerFechaInicio());
        paramBusquedaReporte.setFechaFin(FechaUtil.obtenerFechaFin());

    }
    
    /**
     * Metodo para realizar la consulta desde la vista
     *
     */
    public void consultar() {
        LOGGER.debug("mx.mc.magedbean.DoveTrackMB.consultar()");
        try {
            paramBusquedaReporte.setNuevaBusqueda(true);
            paramBusquedaReporte.setListaEstructuras(selectServiceList);
                       
            listDataResultReporte = doveTrackService.getDoveTrack(paramBusquedaReporte);

        } catch (Exception e1) {
            LOGGER.error("Error al consultar", e1);
        }
    }

    /**
     * Consulta de insumos por cadena de texto mayor a 3 caracteres y retorna
     * las coincidencias
     *
     * @param query
     * @return
     */
    public List<Medicamento> autocompleteInsumo(String query) {
        LOGGER.debug("mx.mc.magedbean.DoveTrackMB.autocompleteInsumo()");
        List<Medicamento> insumosList = new ArrayList<>();
        try {
            insumosList.addAll(medicamentoService.obtenerInsumos(query));

        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al consultar el medicamento");
            Mensaje.showMessage("Error", RESOURCES.getString("medicamento.err.autocomplete"), null);
        }
        return insumosList;
    }

    public List<Usuario> autocompleteUsuario(String query) {
        LOGGER.debug("mx.mc.magedbean.DoveTrackMB.autocompleteUsuario()");
        List<Usuario> listUsuarios = new ArrayList<>();
        try {
            listUsuarios.addAll(usuarioService.obtenerUsuarios(query));
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener los usuarios: {}", ex.getMessage());
        }
        return listUsuarios;
    }

    public ParamBusquedaReporte getParamBusquedaReporte() {
        return paramBusquedaReporte;
    }

    public void setParamBusquedaReporte(ParamBusquedaReporte paramBusquedaReporte) {
        this.paramBusquedaReporte = paramBusquedaReporte;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public List<String> getSelectServiceList() {
        return selectServiceList;
    }

    public void setSelectServiceList(List<String> selectServiceList) {
        this.selectServiceList = selectServiceList;
    }

    public Usuario getUsuarioSession() {
        return usuarioSession;
    }

    public void setUsuarioSession(Usuario usuarioSession) {
        this.usuarioSession = usuarioSession;
    }

    public List<Estructura> getListAreaEstructura() {
        return listAreaEstructura;
    }

    public void setListAreaEstructura(List<Estructura> listAreaEstructura) {
        this.listAreaEstructura = listAreaEstructura;
    }
    public Date getFechaActualInicio() {
        return fechaActualInicio;
    }

    public void setFechaActualInicio(Date fechaActualInicio) {
        this.fechaActualInicio = fechaActualInicio;
    }

    public List<DoveTrack> getListDataResultReporte() {
        return listDataResultReporte;
    }

    public void setListDataResultReporte(List<DoveTrack> listDataResultReporte) {
        this.listDataResultReporte = listDataResultReporte;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }
    
}
