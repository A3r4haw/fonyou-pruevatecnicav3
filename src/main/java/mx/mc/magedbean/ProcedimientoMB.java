package mx.mc.magedbean;

import java.io.Serializable;

import java.util.List;
import java.util.ResourceBundle;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import mx.mc.init.Constantes;
import mx.mc.model.Usuario;
import mx.mc.service.UsuarioService;
import mx.mc.util.Comunes;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.lazy.ProcedimientosLazy;
import mx.mc.model.Estudio;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.Procedimiento;
import mx.mc.service.EstudioService;
import mx.mc.service.ProcedimientoService;

/*
 *
 * @author apalacios
 */
@Controller
@Scope(value = "view")
public class ProcedimientoMB implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcedimientoMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private boolean btnNew;
    private Estudio estudioSelect;
    private List<Estudio> estudiosList;
    private Procedimiento procedimientoSelect;
    private List<Procedimiento> procedimientoList;
    private PermisoUsuario permiso;
    private ParamBusquedaReporte paramBusquedaReporte;
    private ProcedimientosLazy procedimientosLazy;
    private String cadenaBusqueda;
            
    @Autowired
    private transient EstudioService estudioService;

    @Autowired
    private transient ProcedimientoService procedimientoService;

    @Autowired
    private transient UsuarioService usuarioService;
    private Usuario usuarioSelect;
    
    @PostConstruct
    public void init() {
        LOGGER.trace("Procedimientos");
        initialize();
        permiso = Comunes.obtenerPermisos(Transaccion_Enum.HOJATRABAJO.getSufijo());
        if (permiso.isPuedeCrear()) {
            btnNew = Constantes.INACTIVO;
        }
        buscarProcedimientos();
    }

    private void initialize() {
        this.setCadenaBusqueda("");
        estudioSelect = new Estudio();
        estudiosList = new ArrayList<>();
        procedimientoSelect = new Procedimiento();
        procedimientoList = new ArrayList<>();
        paramBusquedaReporte = new ParamBusquedaReporte();
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        usuarioSelect = sesion.getUsuarioSelected();
        obtenEstudios();
        btnNew = Constantes.INACTIVO;
    }

    public void obtenEstudios() {
        LOGGER.trace("obtenEstudios()");
        try {
            Estudio estudio = new Estudio();
            estudio.setActivo(Constantes.ESTATUS_ACTIVO);
            estudiosList = estudioService.obtenerLista(estudioSelect);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener Estudios: {}", ex.getMessage());
        }
    }

    public void buscarProcedimientos() {
        LOGGER.trace("Buscando conincidencias de: {}", cadenaBusqueda);
        try {
            paramBusquedaReporte.setNuevaBusqueda(true);
            paramBusquedaReporte.setCadenaBusqueda(cadenaBusqueda);
            if (paramBusquedaReporte.getCadenaBusqueda().equals("")) {
                paramBusquedaReporte.setCadenaBusqueda(null);
            }
            procedimientosLazy = new ProcedimientosLazy(procedimientoService, paramBusquedaReporte, 0);
            LOGGER.debug("Resultados: {}", procedimientosLazy.getTotalReg());
        } catch (Exception e) {
            LOGGER.error("Error al buscar Procedimientos: {}", e.getMessage());
        }
    }

    public boolean isBtnNew() {
        return btnNew;
    }

    public void setBtnNew(boolean btnNew) {
        this.btnNew = btnNew;
    }

    public Estudio getEstudioSelect() {
        return estudioSelect;
    }

    public void setEstudioSelect(Estudio estudioSelect) {
        this.estudioSelect = estudioSelect;
    }

    public List<Estudio> getEstudiosList() {
        return estudiosList;
    }

    public void setEstudiosList(List<Estudio> estudiosList) {
        this.estudiosList = estudiosList;
    }

    public Procedimiento getProcedimientoSelect() {
        return procedimientoSelect;
    }

    public void setProcedimientoSelect(Procedimiento procedimientoSelect) {
        this.procedimientoSelect = procedimientoSelect;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

    public ParamBusquedaReporte getParamBusquedaReporte() {
        return paramBusquedaReporte;
    }

    public void setParamBusquedaReporte(ParamBusquedaReporte paramBusquedaReporte) {
        this.paramBusquedaReporte = paramBusquedaReporte;
    }

    public ProcedimientosLazy getProcedimientosLazy() {
        return procedimientosLazy;
    }

    public void setProcedimientosLazy(ProcedimientosLazy procedimientosLazy) {
        this.procedimientosLazy = procedimientosLazy;
    }

    public String getCadenaBusqueda() {
        return cadenaBusqueda;
    }

    public void setCadenaBusqueda(String cadenaBusqueda) {
        this.cadenaBusqueda = cadenaBusqueda;
    }

    public Usuario getUsuarioSelect() {
        return usuarioSelect;
    }

    public void setUsuarioSelect(Usuario usuarioSelect) {
        this.usuarioSelect = usuarioSelect;
    }

    
}
