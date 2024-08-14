package mx.mc.magedbean;

import com.ibm.icu.util.Calendar;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import mx.mc.enums.EstatusCensoInsumo_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.lazy.CensoPacientesLazy;
import mx.mc.model.CensoInsumo;
import mx.mc.model.CensoInsumoDetalle;
import mx.mc.model.CensoPaciente;
import mx.mc.model.CensoPacienteExtended;
import mx.mc.model.CensoReglaDetalle;
import mx.mc.model.CensoReglaExtended;
import mx.mc.model.Diagnostico;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.Medicamento;
import mx.mc.model.Medicamento_Extended;
import mx.mc.model.Paciente;
import mx.mc.model.Paciente_Extended;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.Usuario;
import mx.mc.service.CensoInsumoDetalleService;
import mx.mc.service.CensoInsumoService;
import mx.mc.service.CensoPacienteService;
import mx.mc.service.DiagnosticoService;
import mx.mc.service.EntidadHospitalariaService;
import mx.mc.service.MedicamentoService;
import mx.mc.service.PacienteService;
import mx.mc.util.CodigoBarras;
import mx.mc.util.Comunes;
import static mx.mc.util.Comunes.obtenerUsuarioSesion;
import mx.mc.util.Mensaje;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.primefaces.shaded.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import mx.mc.service.CensoReglaService;

/**
 * @author apalacios
 */
@Controller
@Scope(value = "view")
public class CensoPacienteMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(CensoPacienteMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    private String textoBusqueda;
    private List<CensoPacienteExtended> listaCensoPacientes;
    private List<CensoPacienteExtended> listaCensoHistorico;
    private CensoPacienteExtended censoPacienteSelect;
    private PermisoUsuario permiso;    
    private String errPermisos;
    private CensoPacientesLazy censoPacientesLazy;
    private Paciente_Extended pacienteSelected;
    private Paciente derechohabienteSelected;
    private List<Paciente> listaDerechohabientes;
    private Medicamento_Extended medicamentoSelected;
    private Diagnostico diagnosticoSelected;
    private CensoReglaExtended censoRegla;
    private CensoInsumo censoInsumo;
    private List<EntidadHospitalaria> listaEntidadHospitalaria;
    private boolean vistaDetalle;
    private boolean renderBotonGuardar;
    private boolean renderBotonActualizar;
    private StreamedContent oficioStreamed;
    
    @Autowired
    private transient CensoPacienteService censoPacienteService;
    @Autowired
    private transient CensoInsumoService censoInsumoService;
    @Autowired
    private transient CensoInsumoDetalleService censoInsumoDetalleService;
    @Autowired
    private transient PacienteService pacienteService;
    @Autowired
    private transient MedicamentoService medicamentoService;
    @Autowired
    private transient DiagnosticoService diagnosticoService;
    @Autowired
    private transient CensoReglaService censoReglaService;
    @Autowired
    private transient EntidadHospitalariaService entidadHospitalariaService;

    @PostConstruct
    public void init() {
        try {
            initialize();
            permiso = Comunes.obtenerPermisos(Transaccion_Enum.CENSOPACIENTES.getSufijo());
            buscarRegistros();
        } catch (Exception e) {
            LOGGER.error("Error en el metodo init :: {}", e.getMessage());
        }
    }
    
    public Date today() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    
    public Date nextYear() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.YEAR, 1);
        return cal.getTime();
    }

    public void initialize() {
        textoBusqueda = "";
        vistaDetalle = true;
        renderBotonGuardar = false;
        renderBotonActualizar = false;
        listaCensoPacientes = new ArrayList<>();
        censoPacienteSelect = new CensoPacienteExtended();
        pacienteSelected = null;
        listaDerechohabientes = new ArrayList<>();
        medicamentoSelected = null;
        diagnosticoSelected = null;
        derechohabienteSelected = null;
        censoInsumo = new CensoInsumo();
        censoInsumo.setSexo('M');
        censoInsumo.setCantidadMinima(true);
        censoInsumo.setVigenciaInicio(today());
        censoInsumo.setVigenciaFin(nextYear());
        try {
            listaEntidadHospitalaria = entidadHospitalariaService.obtenerBusquedaEntidad(null);
        }
        catch (Exception ex) {
            listaEntidadHospitalaria = new ArrayList<>();
        }
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
            censoPacientesLazy = new CensoPacientesLazy(censoPacienteService, paramBusquedaReporte);
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

    public List<CensoPacienteExtended> getListaCensoPacientes() {
        return listaCensoPacientes;
    }

    public void setListaCensoPacientes(List<CensoPacienteExtended> listaCensoPacientes) {
        this.listaCensoPacientes = listaCensoPacientes;
    }

    public List<CensoPacienteExtended> getListaCensoHistorico() {
        return listaCensoHistorico;
    }

    public void setListaCensoHistorico(List<CensoPacienteExtended> listaCensoHistorico) {
        this.listaCensoHistorico = listaCensoHistorico;
    }

    public CensoPacienteExtended getCensoPacienteSelect() {
        return censoPacienteSelect;
    }

    public void setCensoPacienteSelect(CensoPacienteExtended censoPacienteSelect) {
        this.censoPacienteSelect = censoPacienteSelect;
    }

    public String getErrPermisos() {
        return errPermisos;
    }

    public void setErrPermisos(String errPermisos) {
        this.errPermisos = errPermisos;
    }

    public CensoPacientesLazy getCensoPacientesLazy() {
        return censoPacientesLazy;
    }

    public void setCensoPacientesLazy(CensoPacientesLazy censoPacientesLazy) {
        this.censoPacientesLazy = censoPacientesLazy;
    }

    public Paciente_Extended getPacienteSelected() {
        return pacienteSelected;
    }

    public void setPacienteSelected(Paciente_Extended pacienteSelected) {
        this.pacienteSelected = pacienteSelected;
    }

    public Paciente getDerechohabienteSelected() {
        return derechohabienteSelected;
    }

    public void setDerechohabienteSelected(Paciente derechohabienteSelected) {
        this.derechohabienteSelected = derechohabienteSelected;
    }

    public List<Paciente> getListaDerechohabientes() {
        return listaDerechohabientes;
    }

    public void setListaDerechohabientes(List<Paciente> listaDerechohabientes) {
        this.listaDerechohabientes = listaDerechohabientes;
    }
    
    public List<Paciente_Extended> autoCompletePaciente(String cadena) {
        List<Paciente_Extended> pacienteList;
        try {
            pacienteList = pacienteService.searchPacienteAutoComplete(cadena.trim());
        } catch (Exception ex) {
            pacienteList = new ArrayList<>();
            LOGGER.error("Error al obtener Pacientes: {}", ex.getMessage());
        }
        if (pacienteList.size() == 1) {
            pacienteSelected = pacienteList.get(0);
            String componentId = UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getClientId();
            String panel = componentId.replace(":", "\\\\:") + "_panel";
            PrimeFaces.current().executeScript("$('#" + panel + " .ui-autocomplete-item').trigger('click');");
        }
        return pacienteList;
    }
    
    private void buscarDerechohabientes(String idPaciente, String claveDerechohabiencia) {
        listaDerechohabientes = new ArrayList<>();
        if (claveDerechohabiencia != null && !claveDerechohabiencia.isEmpty() && !claveDerechohabiencia.equalsIgnoreCase("No definido")) {
            try {
                listaDerechohabientes = pacienteService.obtenerDerechohabientes(claveDerechohabiencia, idPaciente);
            } catch (Exception ex) {
                LOGGER.error("Error al obtener Derechohabientes: {}", ex.getMessage());
            }
        }
    }

    private void buscarHistorico(String idPaciente, String claveDerechohabiencia) {
        listaCensoHistorico = new ArrayList<>();
        try {
            String idCensoPaciente = null;
            if (censoPacienteSelect != null)
                idCensoPaciente = censoPacienteSelect.getIdCensoPaciente();
            listaCensoHistorico = censoPacienteService.obtenerRegistrosHistorico(idCensoPaciente, idPaciente, claveDerechohabiencia);
        } catch (Exception e) {
            LOGGER.error("Error en el metodo buscarHistorico :: {}", e.getMessage());
        }
    }
    
    public void selectPaciente(SelectEvent e) {
        pacienteSelected = (Paciente_Extended) e.getObject();
        if (pacienteSelected != null) {
            censoInsumo.setSexo(pacienteSelected.getSexo());
            buscarDerechohabientes(pacienteSelected.getIdPaciente(), pacienteSelected.getClaveDerechohabiencia());
            buscarHistorico(pacienteSelected.getIdPaciente(), pacienteSelected.getClaveDerechohabiencia());
        }
    }

    public Medicamento_Extended getMedicamentoSelected() {
        return medicamentoSelected;
    }

    public void setMedicamentoSelected(Medicamento_Extended medicamentoSelected) {
        this.medicamentoSelected = medicamentoSelected;
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
    
    private void cargaReglas() {
        censoRegla = null;
        if (medicamentoSelected != null && diagnosticoSelected != null) {
            try {
                censoRegla = censoReglaService.obtenerRegla(medicamentoSelected.getIdMedicamento(), diagnosticoSelected.getIdDiagnostico());
                if (censoRegla != null)
                    censoRegla.setListaCensoReglaDetalle(censoReglaService.obtenerReglaDetalle(censoRegla.getIdCensoRegla()));
            }
            catch (Exception ex) {
                LOGGER.error("Error al obtener Reglas del Censo: {}", ex.getMessage());
            }
        }
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

    public Diagnostico getDiagnosticoSelected() {
        return diagnosticoSelected;
    }

    public void setDiagnosticoSelected(Diagnostico diagnosticoSelected) {
        this.diagnosticoSelected = diagnosticoSelected;
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

    public void selectDiagnostico(SelectEvent e) {
        diagnosticoSelected = (Diagnostico) e.getObject();
        try {
            diagnosticoSelected.setActivo(true);
            diagnosticoSelected = diagnosticoService.obtener(diagnosticoSelected);
            cargaReglas();
        }
        catch (Exception ex) {
            LOGGER.error("Error al obtener el Diagnóstico: {}", ex.getMessage());
        }
    }

    
    
    public void uploadOficio(FileUploadEvent event) {
        try {
            if (null != event.getFile()) {
                UploadedFile file = event.getFile();
                censoInsumo.setFileName(file.getFileName());
                censoInsumo.setOficio(IOUtils.toByteArray(file.getInputstream()));
            }
        } catch(IOException ex) {
            LOGGER.error("ERROR al cargar el archivo: {}", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "ERROR al cargar el archivo!", "");
        }
    }
    
    public boolean validarDatosModal() {
        if (pacienteSelected == null || pacienteSelected.getIdPaciente() == null || pacienteSelected.getIdPaciente().isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El campo Paciente es obligatorio.", "");
            return false;
        }
        if (medicamentoSelected == null || medicamentoSelected.getIdMedicamento() == null || medicamentoSelected.getIdMedicamento().isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El campo Medicamento es obligatorio.", "");
            return false;
        }
        if (diagnosticoSelected == null || diagnosticoSelected.getIdDiagnostico() == null || diagnosticoSelected.getIdDiagnostico().isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El campo Diagnostico es obligatorio.", "");
            return false;
        }
        if (censoRegla == null || censoRegla.getListaCensoReglaDetalle() == null || censoRegla.getListaCensoReglaDetalle().isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Las reglas del censo son obligatorias.", "");
            return false;
        }
        if (censoInsumo == null || censoInsumo.getIdDelegacion() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El campo Delegación es obligatorio.", "");
            return false;
        }
        if (censoInsumo == null || censoInsumo.getVigenciaInicio() == null || censoInsumo.getVigenciaFin() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El periodo de vigencia es obligatorio.", "");
            return false;
        }
        if (censoInsumo == null || censoInsumo.getDosis() == null || censoInsumo.getDosis().compareTo(BigDecimal.ZERO) <= 0) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El campo Dosis es obligatorio y debe ser un número positivo.", "");
            return false;
        }
        if (censoInsumo == null || censoInsumo.getFrecuencia() == null || censoInsumo.getFrecuencia() <= 0) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El campo Frecuencia es obligatorio y debe ser un número positivo.", "");
            return false;
        }
        if (censoInsumo == null || censoInsumo.getPeriodo() == null || censoInsumo.getPeriodo() <= 0) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El campo Duración es obligatorio y debe ser un número positivo.", "");
            return false;
        }
        if (censoInsumo == null || censoInsumo.getAutorizacion() == null || censoInsumo.getAutorizacion().isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El campo Autorización es obligatorio.", "");
            return false;
        }
        return true;
    }
    
    public void registrarCensoPaciente() {
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
            CensoPaciente censoPaciente = new CensoPaciente();
            censoPaciente.setIdCensoPaciente(Comunes.getUUID());
            censoPaciente.setIdPaciente(pacienteSelected.getIdPaciente());
            
            if (derechohabienteSelected == null || derechohabienteSelected.getIdPaciente() == null || 
                derechohabienteSelected.getIdPaciente().equals(pacienteSelected.getIdPaciente())) {
                censoPaciente.setTitular(true);
                censoPaciente.setIdDerechohabiente(null);
            } else {
                censoPaciente.setTitular(false);
                censoPaciente.setIdDerechohabiente(derechohabienteSelected.getIdPaciente());
            }
            censoPaciente.setInsertFecha(fecha);
            censoPaciente.setInsertIdUsuario(usuarioSesion.getIdUsuario());

            censoInsumo.setIdCensoInsumo(Comunes.getUUID());
            censoInsumo.setIdCensoPaciente(censoPaciente.getIdCensoPaciente());
            censoInsumo.setIdMedicamento(medicamentoSelected.getIdMedicamento());
            censoInsumo.setIdDiagnostico(diagnosticoSelected.getIdDiagnostico());
            censoInsumo.setIdUsuarioRegistra(usuarioSesion.getIdUsuario());
            censoInsumo.setFechaRegistro(fecha);
            censoInsumo.setIdEstatusCensoInsumo(EstatusCensoInsumo_Enum.REGISTRADO.getValue());
            censoInsumo.setInsertIdUsuario(usuarioSesion.getIdUsuario());
            censoInsumo.setInsertFecha(fecha);
            
            List<CensoInsumoDetalle> listaCensoInsumoDetalle = new ArrayList<>();
            for (CensoReglaDetalle censoReglaDetalle : censoRegla.getListaCensoReglaDetalle()) {
                CensoInsumoDetalle detalle = new CensoInsumoDetalle();
                detalle.setIdCensoInsumoDetalle(Comunes.getUUID());
                detalle.setIdCensoInsumo(censoInsumo.getIdCensoInsumo());
                detalle.setNumeroSurtimiento(censoReglaDetalle.getNumeroSurtimiento());
                detalle.setMinimo(censoReglaDetalle.getMinimo());
                detalle.setMaximo(censoReglaDetalle.getMaximo());
                detalle.setNumeroDias(censoReglaDetalle.getNumeroDias());
                detalle.setIdEstatusCensoInsumo(EstatusCensoInsumo_Enum.REGISTRADO.getValue());
                detalle.setInsertIdUsuario(usuarioSesion.getIdUsuario());
                detalle.setInsertFecha(fecha);
                listaCensoInsumoDetalle.add(detalle);
            }
            
            if (censoPacienteService.registrarCensoPaciente(censoPaciente, censoInsumo, listaCensoInsumoDetalle)) {
                Mensaje.showMessage("Info", "Se registró el Censo de Paciente", "");
                PrimeFaces.current().ajax().addCallbackParam("estatusModal", true);
            } else {
                PrimeFaces.current().ajax().addCallbackParam("estatusModal", false);
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No se pudo registrar el Censo de Paciente", "");
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo registrarCensoPaciente :: {}", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al registrar el Censo de Paciente", "");
        }
    }
    
    private void obtenerDatosCenso(CensoPacienteExtended censoPaciente) {
        censoPacienteSelect = censoPaciente;
        censoInsumo = new CensoInsumo();
        try {
            censoInsumo.setIdCensoPaciente(censoPaciente.getIdCensoPaciente());
            censoInsumo = censoInsumoService.obtenerCensoInsumo(censoInsumo);
            pacienteSelected = pacienteService.obtenerPacienteCompletoPorId(censoPaciente.getIdPaciente());
            buscarDerechohabientes(pacienteSelected.getIdPaciente(), pacienteSelected.getClaveDerechohabiencia());
            if (censoPaciente.isTitular()) {
                derechohabienteSelected = null;
            } else {
                derechohabienteSelected = pacienteService.obtenerPacienteCompletoPorId(censoPaciente.getIdDerechohabiente());
            }
            buscarHistorico(pacienteSelected.getIdPaciente(), pacienteSelected.getClaveDerechohabiencia());
            Medicamento medicamento = medicamentoService.obtenerPorIdMedicamento(censoInsumo.getIdMedicamento());
            medicamentoSelected = new Medicamento_Extended();
            medicamentoSelected.setIdMedicamento(medicamento.getIdMedicamento());
            medicamentoSelected.setClaveInstitucional(medicamento.getClaveInstitucional());
            medicamentoSelected.setNombreCorto(medicamento.getNombreCorto());
            medicamentoSelected.setNombreLargo(medicamento.getNombreLargo());
            diagnosticoSelected = new Diagnostico();
            diagnosticoSelected.setIdDiagnostico(censoInsumo.getIdDiagnostico());
            diagnosticoSelected.setActivo(true);
            diagnosticoSelected = diagnosticoService.obtener(diagnosticoSelected);
            cargaReglas();
        }
        catch(Exception ex) {
            LOGGER.error("Error en el metodo obtenerDatosCenso :: {}", ex.getMessage());
        }
    }
    
    public void mostrarModalRegistrarCenso() {
        censoInsumo = new CensoInsumo();
        censoInsumo.setSexo('M');
        censoInsumo.setCantidadMinima(true);
        censoInsumo.setVigenciaInicio(today());
        censoInsumo.setVigenciaFin(nextYear());
        pacienteSelected = null;
        derechohabienteSelected = null;
        listaDerechohabientes = new ArrayList<>();
        listaCensoHistorico = null;
        medicamentoSelected = null;
        diagnosticoSelected = null;
        censoRegla = null;
        vistaDetalle = false;
        renderBotonGuardar = true;
        renderBotonActualizar = false;
        oficioStreamed = null;
    }
    
    public void generaOficioStreamed() {
        oficioStreamed = null;
        if (censoInsumo.getOficio() != null) {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            session.setAttribute("bytesToStream", censoInsumo.getOficio());
        }
    }
    
    public void mostrarModalActualizarCenso(CensoPacienteExtended censoPaciente) {
        obtenerDatosCenso(censoPaciente);
        vistaDetalle = false;
        renderBotonGuardar = false;
        renderBotonActualizar = true;
        oficioStreamed = null;
    }
    
    public void mostrarModalDetalleCenso() {
        obtenerDatosCenso(censoPacienteSelect);
        vistaDetalle = true;
        renderBotonGuardar = false;
        renderBotonActualizar = false;
        oficioStreamed = null;
    }

    public StreamedContent getOficioStreamed() {
        return oficioStreamed;
    }

    public void setOficioStreamed(StreamedContent oficioStreamed) {
        this.oficioStreamed = oficioStreamed;
    }
    
    public void actualizarCensoPaciente() {
        try {
            if (!this.permiso.isPuedeCrear()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errPermisos), "");
                return;
            }
            Usuario usuarioSesion = obtenerUsuarioSesion();
            /* TODO: Falta establecer cuáles datos se permitirán modificar y cuales no
            if (!validarDatosModal()) {
                PrimeFaces.current().ajax().addCallbackParam("estatusModal", false);
                return;
            }*/
            Date fecha = new Date();            
            if (derechohabienteSelected == null || derechohabienteSelected.getIdPaciente() == null || 
                derechohabienteSelected.getIdPaciente().equals(pacienteSelected.getIdPaciente())) {
                censoPacienteSelect.setTitular(true);
                censoPacienteSelect.setIdDerechohabiente(null);
            } else {
                censoPacienteSelect.setTitular(false);
                censoPacienteSelect.setIdDerechohabiente(derechohabienteSelected.getIdPaciente());
            }
            censoPacienteSelect.setUpdateFecha(fecha);
            censoPacienteSelect.setUpdateIdUsuario(usuarioSesion.getIdUsuario());
            censoInsumo.setIdMedicamento(medicamentoSelected.getIdMedicamento());
            censoInsumo.setIdDiagnostico(diagnosticoSelected.getIdDiagnostico());
            censoInsumo.setUpdateIdUsuario(usuarioSesion.getIdUsuario());
            censoInsumo.setUpdateFecha(fecha);
            
            List<CensoInsumoDetalle> listaCensoInsumoDetalle = censoInsumoDetalleService.obtenerListaCensoInsumoDetalle(censoInsumo.getIdCensoInsumo());
            if (censoPacienteService.actualizarCensoPaciente(censoPacienteSelect, censoInsumo, listaCensoInsumoDetalle)) {
                Mensaje.showMessage("Info", "Se actualizó el Censo de Paciente", "");
                PrimeFaces.current().ajax().addCallbackParam("estatusModal", true);
            } else {
                PrimeFaces.current().ajax().addCallbackParam("estatusModal", false);
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No se pudo actualizar el Censo de Paciente", "");
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo actualizarCensoPaciente :: {}", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al actualizar el Censo de Paciente", "");
        }
    }
    
    
    public CensoReglaExtended getCensoRegla() {
        return censoRegla;
    }

    public void setCensoRegla(CensoReglaExtended censoRegla) {
        this.censoRegla = censoRegla;
    }

    public CensoInsumo getCensoInsumo() {
        return censoInsumo;
    }

    public void setCensoInsumo(CensoInsumo censoInsumo) {
        this.censoInsumo = censoInsumo;
    }

    public List<EntidadHospitalaria> getListaEntidadHospitalaria() {
        return listaEntidadHospitalaria;
    }

    public void setListaEntidadHospitalaria(List<EntidadHospitalaria> listaEntidadHospitalaria) {
        this.listaEntidadHospitalaria = listaEntidadHospitalaria;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }
    
}