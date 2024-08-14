package mx.mc.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import mx.mc.lazy.CensoReglaLazy;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.CensoRegla;
import mx.mc.model.CensoReglaDetalle;
import mx.mc.model.CensoReglaExtended;
import mx.mc.model.Diagnostico;
import mx.mc.model.Medicamento_Extended;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.Usuario;
import mx.mc.service.DiagnosticoService;
import mx.mc.service.MedicamentoService;
import mx.mc.util.CodigoBarras;
import static mx.mc.util.Comunes.obtenerUsuarioSesion;
import mx.mc.util.Mensaje;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import mx.mc.service.CensoReglaService;
import mx.mc.util.Comunes;

/**
 * @author apalacios
 */
@Controller
@Scope(value = "view")
public class CensoReglaMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(CensoReglaMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    private String textoBusqueda;
    private List<CensoReglaExtended> listaCensoRegla;
    private CensoReglaExtended censoReglaSelected;
    private List<CensoReglaDetalle> listaCensoReglaDetalle;
    private String errPermisos;
    private CensoReglaLazy censoReglaLazy;
    private Medicamento_Extended medicamentoSelected;
    private Diagnostico diagnosticoSelected;
    private boolean vistaDetalle;
    private boolean renderBotonGuardar;
    private boolean renderBotonActualizar;
    private PermisoUsuario permiso;
    @Autowired
    private transient CensoReglaService censoReglaService;
    @Autowired
    private transient MedicamentoService medicamentoService;
    @Autowired
    private transient DiagnosticoService diagnosticoService;

    @PostConstruct
    public void init() {
        try {
            initialize();
            permiso = Comunes.obtenerPermisos(Transaccion_Enum.REGLASCENSO.getSufijo());
            buscarRegistros();
        } catch (Exception e) {
            LOGGER.error("Error en el metodo init :: {}", e.getMessage());
        }
    }

    public void initialize() {
        textoBusqueda = "";
        vistaDetalle = true;
        renderBotonGuardar = false;
        renderBotonActualizar = false;
        censoReglaSelected = new CensoReglaExtended();
    }

    public void buscarRegistros() {
        try {
            if (!this.permiso.isPuedeVer()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errPermisos), "");
                return;
            }
            ParamBusquedaReporte paramBusquedaReporte = new ParamBusquedaReporte();
            paramBusquedaReporte.setNuevaBusqueda(true);
            paramBusquedaReporte.setCadenaBusqueda(textoBusqueda);
            censoReglaLazy = new CensoReglaLazy(censoReglaService, paramBusquedaReporte);
        } catch (Exception e) {
            LOGGER.error("Error en el metodo buscarRegistros :: {}", e.getMessage());
        }
    }  

    public String getTextoBusqueda() {
        return textoBusqueda;
    }

    public void setTextoBusqueda(String textoBusqueda) {
        this.textoBusqueda = textoBusqueda;
    }

    public String getErrPermisos() {
        return errPermisos;
    }

    public void setErrPermisos(String errPermisos) {
        this.errPermisos = errPermisos;
    }

    public List<CensoReglaExtended> getListaCensoRegla() {
        return listaCensoRegla;
    }

    public void setListaCensoRegla(List<CensoReglaExtended> listaCensoRegla) {
        this.listaCensoRegla = listaCensoRegla;
    }

    public List<CensoReglaDetalle> getListaCensoReglaDetalle() {
        return listaCensoReglaDetalle;
    }

    public void setListaCensoReglaDetalle(List<CensoReglaDetalle> listaCensoReglaDetalle) {
        this.listaCensoReglaDetalle = listaCensoReglaDetalle;
    }

    public CensoReglaLazy getCensoReglaLazy() {
        return censoReglaLazy;
    }

    public void setCensoReglaLazy(CensoReglaLazy censoReglaLazy) {
        this.censoReglaLazy = censoReglaLazy;
    }

    public CensoReglaExtended getCensoReglaSelected() {
        return censoReglaSelected;
    }

    public void setCensoReglaSelected(CensoReglaExtended censoReglaSelected) {
        this.censoReglaSelected = censoReglaSelected;
    }

    public boolean isVistaDetalle() {
        return vistaDetalle;
    }

    public void setVistaDetalle(boolean vistaDetalle) {
        this.vistaDetalle = vistaDetalle;
    }

    public boolean isRenderBotonGuardar() {
        return renderBotonGuardar;
    }

    public void setRenderBotonGuardar(boolean renderBotonGuardar) {
        this.renderBotonGuardar = renderBotonGuardar;
    }

    public boolean isRenderBotonActualizar() {
        return renderBotonActualizar;
    }

    public void setRenderBotonActualizar(boolean renderBotonActualizar) {
        this.renderBotonActualizar = renderBotonActualizar;
    }

    public Medicamento_Extended getMedicamentoSelected() {
        return medicamentoSelected;
    }

    public void setMedicamentoSelected(Medicamento_Extended medicamentoSelected) {
        this.medicamentoSelected = medicamentoSelected;
    }

    public Diagnostico getDiagnosticoSelected() {
        return diagnosticoSelected;
    }

    public void setDiagnosticoSelected(Diagnostico diagnosticoSelected) {
        this.diagnosticoSelected = diagnosticoSelected;
    }
 
    public List<Medicamento_Extended> autoCompleteMedicamento(String cadena) {
        List<Medicamento_Extended> medicamentoList;
        try {
            medicamentoList = medicamentoService.searchMedicamentoAutoComplete(cadena.trim(), null, true);
        } catch (Exception ex) {
            medicamentoList = new ArrayList<>();
            LOGGER.error("Error al obtener Medicamentos: {}", ex.getMessage());
        }
        if (medicamentoList.size() == 1) {
            medicamentoSelected = medicamentoList.get(0);
            String componentId = UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getClientId();
            String panel = componentId.replace(":", "\\\\:") + "_panel";
            PrimeFaces.current().executeScript("$('#" + panel + " .ui-autocomplete-item').trigger('click');");
        }
        return medicamentoList;
    }
    
    public void validaLecturaPorCodigo(SelectEvent e) {
        LOGGER.debug("mx.mc.magedbean.censoPacienteMB.validaLecturaPorCodigo()");
        medicamentoSelected = (Medicamento_Extended) e.getObject();
        String codigoBarras = CodigoBarras.generaCodigoDeBarras(medicamentoSelected.getClaveInstitucional(), medicamentoSelected.getLote(), medicamentoSelected.getFechaCaducidad(), null);
        try {
            if ((codigoBarras == null) || (codigoBarras.trim().isEmpty())) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.codigoincorrecto"), null);
                medicamentoSelected = new Medicamento_Extended();
            }
        } catch (Exception ex) {
            LOGGER.error("Error en el metodo validaLecturaPorCodigo :: {}", ex.getMessage());
        }
    }
    
    public List<Diagnostico> autoCompleteDiagnostico(String cadena) {
        LOGGER.debug("mx.mc.magedbean.CensoPacienteMB.autoCompleteDiagnostico()");
        List<Diagnostico> diagList = new ArrayList<>();
        try {
            diagList.addAll(diagnosticoService.obtenerListaAutoComplete(cadena));
        } catch (Exception ex) {
            LOGGER.error("Error en autocomplete de Diagnósticos: {}", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error en autocomplete de Diagnósticos", null);
        }
        return diagList;
    }
    
    private void cargaReglas() {
        if (medicamentoSelected != null && diagnosticoSelected != null) {
            try {
                /* TODO: falta cargar las reglas modificar:
                  censoRegla = (CensoReglaExtended)censoReglaService.obtenerRegla(medicamentoSelected.getIdMedicamento(), diagnosticoSelected.getIdDiagnostico());
                  if (censoRegla != null)
                      censoRegla.setListaCensoReglaDetalle(censoReglaService.obtenerReglaDetalle(censoRegla.getIdCensoRegla()));
                */
            }
            catch (Exception ex) {
                LOGGER.error("Error al obtener Reglas del Censo: {}", ex.getMessage());
            }
        }
    }
    
    public void agregaDetalle() {
        if (listaCensoReglaDetalle == null)
            listaCensoReglaDetalle = new ArrayList<>();
        CensoReglaDetalle crd = new CensoReglaDetalle();
        crd.setNumeroSurtimiento(listaCensoReglaDetalle.size() + 1);
        listaCensoReglaDetalle.add(crd);
    }
    
    public void selectDiagnostico(SelectEvent e) {
        diagnosticoSelected = (Diagnostico) e.getObject();
        try {
            diagnosticoSelected.setActivo(true);
            diagnosticoSelected = diagnosticoService.obtener(diagnosticoSelected);
            if (renderBotonActualizar || vistaDetalle) {
                cargaReglas();
            }
            else {
                listaCensoReglaDetalle = new ArrayList<>();
            }
        }
        catch (Exception ex) {
            LOGGER.error("Error al obtener el Diagnóstico: {}", ex.getMessage());
        }
    }
    
    public void mostrarModalRegistrar() {
        medicamentoSelected = null;
        diagnosticoSelected = null;
        vistaDetalle = false;
        renderBotonGuardar = true;
        renderBotonActualizar = false;
    }
    
    public void mostrarModalActualizar(CensoReglaExtended censoInsumoRegla) {
        //TODO: Falta obtenerDatosCenso(censoPaciente);
        vistaDetalle = false;
        renderBotonGuardar = false;
        renderBotonActualizar = true;
    }
    
    public void mostrarModalDetalle() {
        //TODO: Falta obtenerDatosCenso(censoPacienteSelect);
        vistaDetalle = true;
        renderBotonGuardar = false;
        renderBotonActualizar = false;
    }
    
    public boolean validarDatosModal() {
        if (medicamentoSelected == null || medicamentoSelected.getIdMedicamento() == null || medicamentoSelected.getIdMedicamento().isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El campo Medicamento es obligatorio.", "");
            return false;
        }
        if (diagnosticoSelected == null || diagnosticoSelected.getIdDiagnostico() == null || diagnosticoSelected.getIdDiagnostico().isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El campo Diagnostico es obligatorio.", "");
            return false;
        }
        if (listaCensoReglaDetalle == null || listaCensoReglaDetalle.isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Las reglas de los surtimientos son obligatorias.", "");
            return false;
        }
        for (CensoReglaDetalle crd : listaCensoReglaDetalle) {
            if (crd.getMinimo() == null || crd.getMinimo() <= 0 || crd.getMaximo() == null || crd.getMaximo() <= 0 ||
                crd.getNumeroDias() == null || crd.getNumeroDias() <= 0) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Las reglas contienen datos inválidos.", "");
                return false;
            }
        }
        return true;
    }
    
    public void eliminarSurtimiento(int index) {
        if (index >= 0 && index < listaCensoReglaDetalle.size()) {
            listaCensoReglaDetalle.remove(index);
            int nvoIdx = 1;
            for (CensoReglaDetalle crd : listaCensoReglaDetalle) {
                crd.setNumeroSurtimiento(nvoIdx++);
            }
        }
    }
    
    public void registrarRegla() {
        try {
            if (!this.permiso.isPuedeCrear()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errPermisos), "");
                return;
            }
            Usuario usuarioSesion = obtenerUsuarioSesion();
            if (!validarDatosModal()) {
                PrimeFaces.current().ajax().addCallbackParam("estatusModal", false);
                return;
            }
            Date fecha = new Date();
            CensoRegla censoRegla = new CensoRegla();
            censoRegla.setIdCensoRegla(Comunes.getUUID());
            censoRegla.setIdMedicamento(medicamentoSelected.getIdMedicamento());
            censoRegla.setIdDiagnostico(diagnosticoSelected.getIdDiagnostico());
            censoRegla.setInsertFecha(fecha);
            censoRegla.setInsertIdUsuario(usuarioSesion.getIdUsuario());
            for (CensoReglaDetalle detalle : listaCensoReglaDetalle) {
                detalle.setIdCensoReglaDetalle(Comunes.getUUID());
                detalle.setIdCensoRegla(censoRegla.getIdCensoRegla());
                detalle.setInsertIdUsuario(usuarioSesion.getIdUsuario());
                detalle.setInsertFecha(fecha);
            }
            if (censoReglaService.registrarCensoRegla(censoRegla, listaCensoReglaDetalle)) {
                Mensaje.showMessage("Info", "Se registró la regla del Censo", "");
                PrimeFaces.current().ajax().addCallbackParam("estatusModal", true);
            } else {
                PrimeFaces.current().ajax().addCallbackParam("estatusModal", false);
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No se pudo registrar la regla del Censo", "");
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo registrarCensoPaciente :: {}", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al registrar la regla del Censo", "");
        }
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }
    
}