/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.magedbean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import mx.mc.enums.RiesgoReaccion_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.lazy.IndTerapeuticaLazy;
import mx.mc.model.Diagnostico;
import mx.mc.model.Emisor;
import mx.mc.model.IndTerapeuticaExtended;
import mx.mc.model.Medicamento;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.TipoEdadPaciente;
import mx.mc.model.UnidadConcentracion;
import mx.mc.model.ViaAdministracion;
import mx.mc.model.VistaMedicamento;
import mx.mc.service.DiagnosticoService;
import mx.mc.service.EmisorService;
import mx.mc.service.IndTerapeuticaService;
import mx.mc.service.MedicamentoService;
import mx.mc.service.TipoEdadPacienteService;
import mx.mc.service.UnidadConcentracionService;
import mx.mc.service.ViaAdministracionService;
import mx.mc.util.Comunes;
import mx.mc.util.Mensaje;
import mx.mc.util.UtilPath;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author gcruz
 */
@Controller
@Scope(value = "view")
public class IndTerapeuticaMB implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(IndTerapeuticaMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    
    private PermisoUsuario permiso;
    private SesionMB sesion;
    private boolean isAdmin;
    private boolean isJefeArea;
    private String cadenaBusqueda;
    
    private IndTerapeuticaLazy indTerapeuticaLazy;
    private boolean status;
    private List<IndTerapeuticaExtended> listaIndTerapeutica;
    
    private List<TipoEdadPaciente> listaTipoEdadPaciente;
    private TipoEdadPaciente tipoEdadPAcienteSelect;
    private Integer idTipoEdadPaciente;
    private List<Emisor> listaEmisores;
    private Integer idEmisor;
    private List<Diagnostico> listaDiagnosticos;
    private List<Medicamento> listaMedicamentos;
    private List<ViaAdministracion> listaViasAdmon;
    private Integer idViaAdministracion;
    
    private IndTerapeuticaExtended indTerapeuticaSelect;
    private Diagnostico diagnostico;
    private VistaMedicamento medicamento;
    private Integer rangoInferior;
    private Integer rangoSuperior;
    private boolean editar;
    private boolean continuar;
    private String motivo;
    
    private String pathDefinition;
    private String fileImg;
    private String nameFile;
    private Date date = new Date();
    private SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss");
    private List<IndTerapeuticaExtended> indicacionLayout;
    
    private String tipoEdadPacienteL;
    private Integer idTipoEdadPacienteL;
    private boolean esTipoEdadPaciente;
    private String diagnosticoL;
    private String idDiagnosticoL;
    private boolean esDiagnostico;
    private String insumoL;
    private String idMedicamentoL;
    private boolean esMedicamento;
    private BigDecimal dosisMinL;
    private BigDecimal dosisMaxL;
    private Integer idUnidadL;
    private Integer frecuenciaMinL;
    private Integer frecuenciaMaxL;
    private Integer duracionMinL;
    private Integer duracionMaxL;
    private String descripcionL;
    private String fuenteL;
    private Integer idFuenteL;
    private boolean esFuente;
    private String estatusL;
    private Integer estatusInd;
    private String riesgoL;
    private Boolean esRiesgo;
    private List<String> riesgosReaccion;
    
    @Autowired
    private transient EmisorService emisorService;
    
    @Autowired
    private transient TipoEdadPacienteService tipoEdadPacienteService;
    
    @Autowired
    private transient IndTerapeuticaService indTerapeuticaService;
    
    @Autowired
    private transient DiagnosticoService diagnosticoService;
    
    @Autowired
    private transient MedicamentoService medicamentoService;
    
    @Autowired
    private transient UnidadConcentracionService unidadConcentracionService;   
    
    @Autowired
    private transient ViaAdministracionService viaAdmistracionService;
    
    /**
     * Consulta los permisos del usuario
     */
    @PostConstruct
    public void init() {
        
        permiso = Comunes.obtenerPermisos(Transaccion_Enum.INDICACIONTERAPEUTICA.getSufijo());
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        initialize();
        riesgosReaccion.add(RiesgoReaccion_Enum.ALTO.getValue());
        riesgosReaccion.add(RiesgoReaccion_Enum.MEDIO.getValue());
        riesgosReaccion.add(RiesgoReaccion_Enum.BAJO.getValue());
        validarUsuarioAdministrador();
        buscarEmisores();
        buscarTiposEdadesPaciente();
        buscarIndicacionesTerapeutcas();
        buscarTodosDiagnosticos();
        buscarTodosMedicamentos();
        buscarViasAdministracion();
    }
    
    private void initialize() {
        isAdmin = Constantes.INACTIVO;
        isJefeArea = Constantes.INACTIVO;
        status = false;
        rangoInferior = 0;
        rangoSuperior = 0;
        editar = false;
        pathDefinition = "";
        fileImg = "";
        nameFile = "";
        indicacionLayout = new ArrayList<>();
        riesgosReaccion = new ArrayList<>();
    }
    
    public void validarUsuarioAdministrador() {
        try {
            
            this.isAdmin = sesion.isAdministrador();
            this.isJefeArea = sesion.isJefeArea();
            
        } catch (Exception e) {
            LOGGER.error("Error en el metodo validarUsuarioAdministrador: ", e.getMessage());
        }
    }

    public void buscarEmisores() {
        try {
            listaEmisores = emisorService.obtenerTodo();
        } catch(Exception ex) {
            LOGGER.error("Error al momento de buscar la lista de emisores: " + ex.getMessage());
        }
    }
    
    public void buscarTiposEdadesPaciente() {
        try {
            listaTipoEdadPaciente = tipoEdadPacienteService.obtenerTodo();
        } catch(Exception ex) {
            LOGGER.error("Error al momento de buscar la lista de tipoEdadesPaciente: " + ex.getMessage());
        }
    }
    
    public void buscarTodosDiagnosticos() {
        try {
            Diagnostico unDiagnostico = new Diagnostico();
            unDiagnostico.setActivo(Constantes.ACTIVO);
            listaDiagnosticos = diagnosticoService.obtenerLista(unDiagnostico);
        } catch(Exception ex) {
            LOGGER.error("Error al momento de buscar la lista de diagnosticos: " + ex.getMessage());
        }
    }
    
    public void buscarTodosMedicamentos() {
        try {
            listaMedicamentos = medicamentoService.obtenerTodosMedicamentosActivos();
        } catch(Exception ex) {
            LOGGER.error("Error al momento de buscar la lista de medicamentos: " + ex.getMessage());
        }
    }
    
    public void buscarViasAdministracion() {
        try {
            listaViasAdmon = viaAdmistracionService.obtenerTodo();
        } catch(Exception ex) {
            LOGGER.error("Error al buscar vias de administración  " + ex.getMessage());
        }
    }
    
    public void buscarRangos() {
        
        try {
            TipoEdadPaciente tipoEdadPaciente = tipoEdadPacienteService.obtenerTipoPorId(idTipoEdadPaciente);
            if(tipoEdadPaciente != null) {
                rangoInferior = tipoEdadPaciente.getRangoInf()  != null ? tipoEdadPaciente.getRangoInf(): null;
                rangoSuperior = tipoEdadPaciente.getRangoSup() != null ? tipoEdadPaciente.getRangoSup() : null;
            }
        } catch(Exception ex) {
            LOGGER.error("Error al momento de buscar los rangos de tipo edad paciente: " + ex.getMessage());
        }
    }
    public void buscarIndicacionesTerapeutcas() {
        try {            
            if (!this.permiso.isPuedeVer()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.accion"), "");
                return;
            }
            if (cadenaBusqueda != null
                    && cadenaBusqueda.trim().isEmpty()) {
                cadenaBusqueda = null;
            }
            indTerapeuticaLazy = new IndTerapeuticaLazy(indTerapeuticaService, cadenaBusqueda);
            
            cadenaBusqueda = null;
        } catch(Exception ex) {
            LOGGER.error("Ocurrio un error al buscar las indicaciones terapeuticas:  " + ex.getMessage());
        }    
    }
    
    public void obtenerIndicacionTerapeutica(String idIndicacionTerap) {
        
        try {
            nuevaIndicacionTerapeutica();
            editar = true;
            indTerapeuticaSelect = indTerapeuticaService.obtenerIndicacionPoId(idIndicacionTerap);            
            if(indTerapeuticaSelect != null) {
                idTipoEdadPaciente = indTerapeuticaSelect.getIdTipoEdadPaciente();
                idEmisor = indTerapeuticaSelect.getRestrictiva();
                idViaAdministracion = indTerapeuticaSelect.getIdViaAdministracion();
                TipoEdadPaciente tipoEdadPac = tipoEdadPacienteService.obtenerTipoPorId(idTipoEdadPaciente);
                
                if(tipoEdadPac != null) {                    
                    rangoInferior = tipoEdadPac.getRangoInf();
                    rangoSuperior = tipoEdadPac.getRangoSup();
                }
                
                if(indTerapeuticaSelect.getIdDiagnostico() != null) {
                    diagnostico = diagnosticoService.obtenerDiagnosticoPorIdDiag(indTerapeuticaSelect.getIdDiagnostico());
                }
                
                if(indTerapeuticaSelect.getIdInsumo() != null) {
                    medicamento = medicamentoService.buscarMedicamentoPorId(indTerapeuticaSelect.getIdInsumo());
                }
                
            }
        } catch(Exception ex) {
            LOGGER.error("Error al buscar la indicación seleccionada con idIndTerapeutica: "+ idIndicacionTerap + ex.getMessage());
        }
    }
    
    public void deleteIndicacion(String idIndicacionTerap) {        
        try {
             LOGGER.info("Metodo de eliminación de indicación " + idIndicacionTerap);
            boolean resp = false;
            if (!this.permiso.isPuedeEliminar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.accion"), "");
                return;
            }
            resp = indTerapeuticaService.eliminarIndicacionTeraPorId(idIndicacionTerap);
            if(resp) {
                Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("indTerap.info.eliminar"), null);
                buscarIndicacionesTerapeutcas();
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("indTerap.err.eliminar"), null);
            }
        } catch(Exception ex) {
            LOGGER.error("Error al eliminar la indicación seleccionada con idIndTerapeutica: "+ idIndicacionTerap + ex.getMessage());
        }
    }
    
    public void nuevaIndicacionTerapeutica() {
        try {
            indTerapeuticaSelect = new IndTerapeuticaExtended();
            indTerapeuticaSelect.setDescripcion("");
            medicamento = null;
            diagnostico = null;
            idTipoEdadPaciente = null;
            idEmisor = null;
            idViaAdministracion = null;
            rangoInferior = null;
            rangoSuperior = null;
            editar = false;
            indicacionLayout = new ArrayList<>();
        } catch(Exception ex) {
            LOGGER.error("Ocurrio un error al abrir modal para generar nueva interacción:  " + ex.getMessage());
        }
    }
    
    /**
     * Consulta Diagnosticos por cadena de texto mayor a 3 caracteres y retorna
     * las coincidencias
     *
     * @param cadena
     * @return
     */
    public List<Diagnostico> autocompleteDiagnostico(String cadena) {
        LOGGER.debug("mx.mc.magedbean.IndTerapeuticaMB.autocompleteDiagnostico()");
        List<Diagnostico> diagList = new ArrayList<>();

        try {
            diagList.addAll(diagnosticoService.obtenerListaAutoComplete(cadena));

        } catch (Exception ex) {
            LOGGER.error("Error al buscar diagnóstico  ", ex);
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("indTerap.err.buscaDiagnostico"), null);
        }
        return diagList;
    }
    
    public List<VistaMedicamento> autoComplete(String cadena) {
        List<VistaMedicamento> medicamentoList = new ArrayList<>();
        try {
            medicamentoList = medicamentoService.obtenerBusqueda(cadena, Constantes.MEDI);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener Medicamentos: {}", ex.getMessage());
        }
        return medicamentoList;
    }
    
    public void handleSelect(SelectEvent e) {
        medicamento = (VistaMedicamento) e.getObject();
    }

    public void handleUnSelect(UnselectEvent e) {
        medicamento = (VistaMedicamento) e.getObject();
    }
    
    public void crearIndicacion() {
        try {
            status = Constantes.ACTIVO;
            
             String valida = "";            
            if(permiso.isPuedeCrear()) {                
                
                valida = validarCamposFormulario();
                if(valida.equals("")) {
                    
                    indTerapeuticaSelect.setIdTipoEdadPaciente(idTipoEdadPaciente);
                    indTerapeuticaSelect.setIdDiagnostico(diagnostico.getIdDiagnostico());
                    indTerapeuticaSelect.setIdInsumo(medicamento.getIdMedicamento());
                    indTerapeuticaSelect.setRestrictiva(idEmisor);
                    indTerapeuticaSelect.setIdViaAdministracion(idViaAdministracion);
                    
                    UnidadConcentracion unidadConcentracion = unidadConcentracionService.obtenerUnidadNombre(medicamento.getUnidadConcentracion());
                    if(unidadConcentracion != null) {
                        indTerapeuticaSelect.setIdUnidad(unidadConcentracion.getIdUnidadConcentracion());
                    }
                    
                    if(!editar) {
                        IndTerapeuticaExtended existeIndicacion = indTerapeuticaService.buscarExistencia(indTerapeuticaSelect);
                        if(existeIndicacion != null) {
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("indTerap.err.existe"), null);
                            status = Constantes.INACTIVO;
                        } else {
                            indTerapeuticaSelect.setIdIndTerapeutica(Comunes.getUUID());
                            indTerapeuticaSelect.setInsertIdUsuario(sesion.getUsuarioSelected().getIdUsuario());
                            indTerapeuticaSelect.setInsertFecha(new Date());
                            boolean resp = indTerapeuticaService.insertarIndicTerapeutica(indTerapeuticaSelect);
                            if(!resp) {
                               Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("indTerap.err.guardarInd"), null);
                               status = Constantes.INACTIVO;
                            } else {
                               Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("indTerap.info.guardarInd"), null);
                               buscarIndicacionesTerapeutcas();
                            }
                        }                                            
                    } else {
                        IndTerapeuticaExtended existeIndicacion = indTerapeuticaService.buscarExistencia(indTerapeuticaSelect);
                        if(existeIndicacion != null) {
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("indTerap.err.existe"), null);
                            status = Constantes.INACTIVO;
                        } else {
                            indTerapeuticaSelect.setUpdateIdUsuario(sesion.getUsuarioSelected().getIdUsuario());
                            indTerapeuticaSelect.setUpdateFecha(new Date());
                             boolean resp = indTerapeuticaService.actualizarIndicacionTerap(indTerapeuticaSelect);
                             if(!resp) {
                                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("indTerap.err.actualizaInd"), null);
                                status = Constantes.INACTIVO;
                             } else {
                                Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("indTerap.info.actualizaInd"), null);
                                buscarIndicacionesTerapeutcas();
                             }
                        }
                    }                                        
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, valida, null);
                    status = Constantes.INACTIVO;
                }
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString("err.accion"), null);
                status = Constantes.INACTIVO;
            }      
            
        } catch(Exception ex) {
            LOGGER.error("Ocurrio un error al guardar la indicacion:  " + ex.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }
    
    public String validarCamposFormulario() {
        String result = "";
        if(idTipoEdadPaciente == null) {
            return RESOURCES.getString("indTerap.err.tipoPaciente");
        }
        if(diagnostico == null) {
            return RESOURCES.getString("indTerap.err.diagnostico");
        }
        if(medicamento == null) {
            return RESOURCES.getString("indTerap.err.medicamento");
        }
        if(indTerapeuticaSelect.getDosisMin() == null) {
           return RESOURCES.getString("indTerap.err.dosisMin");
        }
        if(indTerapeuticaSelect.getDosisMin().compareTo(BigDecimal.ZERO) == 0) {
            return RESOURCES.getString("indTerap.err.dosisMinCero");
        }
        if(indTerapeuticaSelect.getDosisMax() == null) {
            return RESOURCES.getString("indTerap.err.dosisMax");
        } 
        if(indTerapeuticaSelect.getDosisMax().compareTo(BigDecimal.ZERO) == 0) {
            return RESOURCES.getString("indTerap.err.dosisMaxCero");
        }
        if(indTerapeuticaSelect.getDosisMin().compareTo(indTerapeuticaSelect.getDosisMax()) == 1) {
           return RESOURCES.getString("indTerap.err.dosisMayorQue"); 
        }
        if(indTerapeuticaSelect.getFrecuenciaInferior() == null) {
            return RESOURCES.getString("indTerap.err.frecuenciaMin");
        }
        if(indTerapeuticaSelect.getFrecuenciaInferior() <= 0) {
            return RESOURCES.getString("indTerap.err.frecMinCero");
        }
        if(indTerapeuticaSelect.getFrecuenciaSuperior() == null) {
            return RESOURCES.getString("indTerap.err.frecuenciaMax");
        }
        if(indTerapeuticaSelect.getFrecuenciaSuperior() <= 0) {
            return RESOURCES.getString("indTerap.err.frecMaxCero");
        }
        if(indTerapeuticaSelect.getFrecuenciaInferior() > indTerapeuticaSelect.getFrecuenciaSuperior()) {
            return RESOURCES.getString("indTerap.err.frecMayorQue");
        }
        if(indTerapeuticaSelect.getDuracionMinima() == null) {
            return RESOURCES.getString("indTerap.err.duracionMin");
        }
        if(indTerapeuticaSelect.getDuracionMinima() <= 0) {
            return RESOURCES.getString("indTerap.err.duracionMinCero");
        }
        if(indTerapeuticaSelect.getDuracionMaxima() == null) {
            return RESOURCES.getString("indTerap.err.duracionMax");
        }
        if(indTerapeuticaSelect.getDuracionMinima() <= 0) {
            return RESOURCES.getString("indTerap.err.duracionMaxCero");
        }
        if(indTerapeuticaSelect.getDuracionMinima() > indTerapeuticaSelect.getDuracionMaxima()) {
            return RESOURCES.getString("indTerap.err.duracionMayorQue");
        }
        if(idViaAdministracion == null) {
            return  RESOURCES.getString("indTerap.err.viaAdministracion");
        }
        if(idEmisor == null) {
            return RESOURCES.getString("indTerap.err.fuente");
        }
        if(indTerapeuticaSelect.getIdEstatus() == null) {
            return RESOURCES.getString("indTerap.err.estatus");
        }        
        
        return result;
    }
    
    /**
     * Carga layouts de usuarios
     *
     * @param event
     */
    public void layoutFileUpload(FileUploadEvent event) {
        LOGGER.trace("mx.mc.magedbean.UsuariosMB.layoutFileUpload()");
        try {
            //message = Constantes.INACTIVO;
            UploadedFile upfile = event.getFile();
            String name = upfile.getFileName();
            String ext = name.substring(name.lastIndexOf('.'), name.length());
            String excelFilePath = createFile(upfile.getContents(), name);
            switch (ext) {
                case ".xlsx":
                    readLayout2007(excelFilePath);
                    break;
                case ".xls":
                    readLayout2003(excelFilePath);
                    break;
                default:
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("interMedic.err.archivoIncorrecto"), null);
                    break;
            }
            if (!indicacionLayout.isEmpty()) {
                continuar = Constantes.ACTIVO;
            }

        } catch(Exception ex) {
            LOGGER.error("ERROR en layoutFileUpload: {}", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("interMedic.err.archivoIncorrecto"), null);
            continuar = Constantes.INACTIVO;

        }
    }

    /**
     * Parsea archivo Excel 2007
     *
     * @param excelFilePath
     */
    private void readLayout2007(String excelFilePath) {
        boolean exito = Constantes.ACTIVO;
        int num = 1;
        try (FileInputStream inputStream = new FileInputStream(new File(excelFilePath)); Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet firstSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = firstSheet.iterator();
            indicacionLayout = new ArrayList<>();
            while (iterator.hasNext()) {
                Row nextRow = iterator.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();
                cleanObjects();
                if (num > 5) {
                    exito = Constantes.ACTIVO;
                    while (cellIterator.hasNext()) {
                        Cell cell7 = cellIterator.next();
                        switch (cell7.getColumnIndex()) {
                            case 0: //Tipo Edad Paciente
                                tipoEdadPacienteL = getValue(cell7);
                                if (!tipoEdadPacienteL.equals("")) {
                                    listaTipoEdadPaciente.stream().filter(tep -> (tep.getDescripcion().toUpperCase().equals(tipoEdadPacienteL.toUpperCase()))).forEachOrdered(cnsmr -> {
                                        idTipoEdadPacienteL = cnsmr.getIdTipoEdadPaciente();
                                        esTipoEdadPaciente = Constantes.ACTIVO;
                                    });
                                }
                                break;
                            case 1: //Diagnostico                                
                                diagnosticoL = getValue(cell7);
                                if (!diagnosticoL.equals("")) {
                                    listaDiagnosticos.stream().filter(d -> (d.getClave().toUpperCase().equals(diagnosticoL.toUpperCase()))).forEachOrdered(cnsmr -> {
                                        idDiagnosticoL = cnsmr.getIdDiagnostico();
                                        esDiagnostico = Constantes.ACTIVO;
                                    });
                                }
                                break;
                            case 2: //insumo
                                insumoL = getValue(cell7);
                                if (!insumoL.equals("")) {
                                    listaMedicamentos.stream().filter(m -> (m.getClaveInstitucional().toUpperCase().equals(insumoL.toUpperCase()))).forEachOrdered(cnsmr -> {
                                        idMedicamentoL = cnsmr.getIdMedicamento();
                                        idUnidadL = cnsmr.getIdUnidadConcentracion();
                                        esMedicamento = Constantes.ACTIVO;
                                    });
                                }
                                break;
                            case 3:// dosis minima
                                dosisMinL = new BigDecimal(getValue(cell7));
                                break;
                            case 4: //dosis maxima
                                dosisMaxL = new BigDecimal(getValue(cell7));
                                break;
                            case 5: // frecuencia minima
                                String valor = getValue(cell7);
                                String frecMin[] = valor.split("\\.");
                                frecuenciaMinL = Integer.parseInt(frecMin[0]);
                                break;
                            case 6: // frecuencia maxima
                                String frecMax[] = getValue(cell7).split("\\.");
                                frecuenciaMaxL = Integer.parseInt(frecMax[0]);
                                break;
                            case 7: // duración minima
                                String durMin[] = getValue(cell7).split("\\.");
                                duracionMinL = Integer.parseInt(durMin[0]);
                                break;
                            case 8: // duración maxima
                                String durMax[] = getValue(cell7).split("\\.");
                                duracionMaxL = Integer.parseInt(durMax[0]);
                                break;
                            case 9: // descripción
                                descripcionL = getValue(cell7);
                                break;                            
                            case 10: //fuente
                                fuenteL = getValue(cell7);
                                if (!fuenteL.equals("")) {
                                    listaEmisores.stream().filter(e -> (e.getNombreEmisor().toUpperCase().equals(fuenteL.toUpperCase()))).forEachOrdered(cnsmr -> {
                                        idFuenteL = cnsmr.getIdEmisor();
                                        esFuente = Constantes.ACTIVO;
                                    });
                                }
                                break;
                            case 11: // estatus
                                estatusL = getValue(cell7);
                                if (!estatusL.equals("")) {
                                    estatusInd = estatusL.equals("Si") ? Constantes.ESTATUS_ACTIVO : Constantes.ESTATUS_INACTIVO;
                                }
                                break;
                            case 12: //riesgo
                                riesgoL = getValue(cell7);
                                if(!riesgoL.equals("")) {
                                    riesgosReaccion.stream().filter((r) -> (r.toUpperCase().equals(riesgoL.toUpperCase()))).forEachOrdered((cnsmr) -> {
                                        esRiesgo = Constantes.ACTIVO;
                                    });
                                }
                                break;
                            default:
                        }
                        
                    }
                    IndTerapeuticaExtended indicacionLayout7 = new IndTerapeuticaExtended();
                    IndTerapeuticaExtended indTerapeuticaExtended = new IndTerapeuticaExtended();                    
                    
                    exito = validaDatos();
                    if (exito) {
                        indTerapeuticaExtended.setIdDiagnostico(idDiagnosticoL);
                        indTerapeuticaExtended.setIdInsumo(idMedicamentoL);

                        IndTerapeuticaExtended indicacionExiste = indTerapeuticaService.buscarExistencia(indTerapeuticaExtended);
                        
                        if(indicacionExiste == null) {
                            exito = Constantes.ACTIVO;
                            indicacionLayout7.setIdIndTerapeutica(Comunes.getUUID());
                            indicacionLayout7.setIdTipoEdadPaciente(idTipoEdadPacienteL);
                            indicacionLayout7.setIdDiagnostico(idDiagnosticoL);
                            indicacionLayout7.setIdInsumo(idMedicamentoL);
                            indicacionLayout7.setDosisMin(dosisMaxL);
                            indicacionLayout7.setDosisMax(dosisMaxL);
                            indicacionLayout7.setIdUnidad(idUnidadL);
                            indicacionLayout7.setFrecuenciaInferior(frecuenciaMinL);
                            indicacionLayout7.setFrecuenciaSuperior(frecuenciaMaxL);
                            indicacionLayout7.setDuracionMinima(duracionMinL);
                            indicacionLayout7.setDuracionMaxima(duracionMaxL);
                            indicacionLayout7.setDescripcion(descripcionL);
                            indicacionLayout7.setRestrictiva(idFuenteL);
                            indicacionLayout7.setIdEstatus(estatusInd);
                            indicacionLayout7.setRiesgo(riesgoL);
                            indicacionLayout7.setInsertFecha(new Date());
                            indicacionLayout7.setInsertIdUsuario(sesion.getUsuarioSelected().getIdUsuario());                            
                            
                            exito = indTerapeuticaService.insertarIndicTerapeutica(indicacionLayout7);
                            
                             if (!exito) {
                                 motivo = RESOURCES.getString("indTerap.err.guardarInd");
                             }
                            
                        } else {
                            motivo = RESOURCES.getString("indTerap.err.existe");
                            exito = Constantes.INACTIVO;
                        }
                    }
                    
                    if(!exito) {
                        //llenar objeto que no se logro insertar
                        IndTerapeuticaExtended unaIndicacion =  new IndTerapeuticaExtended();
                        
                        unaIndicacion.setClaveInstitucional(insumoL);
                        unaIndicacion.setTipoPaciente(tipoEdadPacienteL);
                        unaIndicacion.setNombreDiagnostico(diagnosticoL);
                        unaIndicacion.setDosisMin(dosisMinL);
                        unaIndicacion.setDosisMax(dosisMaxL);
                        unaIndicacion.setFrecuenciaInferior(frecuenciaMinL);
                        unaIndicacion.setFrecuenciaSuperior(frecuenciaMaxL);
                        unaIndicacion.setDuracionMinima(duracionMinL);
                        unaIndicacion.setDuracionMaxima(duracionMaxL);
                        unaIndicacion.setDescripcion(descripcionL);
                        unaIndicacion.setRiesgo(riesgoL);
                        unaIndicacion.setUpdateIdUsuario(motivo);
                        
                        indicacionLayout.add(unaIndicacion);
                    }
                }
                num++;
            }
        } catch (Exception ex) {
            LOGGER.error("ERROR en readLayout2007: {}", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("interMedic.err.lerr2007"), null);
        }
    }
    
    /**
     * Parsea archivo Excel 2003
     *
     * @param excelFilePath
     */
    private void readLayout2003(String excelFilePath) {
        boolean exito = Constantes.ACTIVO;
        int num = 1;
        try (FileInputStream inputStream = new FileInputStream(new File(excelFilePath)); HSSFWorkbook workbook = new HSSFWorkbook(inputStream)) {
            HSSFSheet firstSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = firstSheet.iterator();
            indicacionLayout = new ArrayList<>();
            while (iterator.hasNext()) {
                Row nextRow = iterator.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();
                cleanObjects();
                if (num > 5) {
                    exito = Constantes.ACTIVO;
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        switch (cell.getColumnIndex()) {
                            case 0: //Tipo Edad Paciente
                                tipoEdadPacienteL = getValue(cell);
                                if (!tipoEdadPacienteL.equals("")) {
                                    listaTipoEdadPaciente.stream().filter(tep -> (tep.getDescripcion().toUpperCase().equals(tipoEdadPacienteL.toUpperCase()))).forEachOrdered(cnsmr -> {
                                        idTipoEdadPacienteL = cnsmr.getIdTipoEdadPaciente();
                                        esTipoEdadPaciente = Constantes.ACTIVO;
                                    });
                                }
                                break;
                            case 1: //Diagnostico                                
                                diagnosticoL = getValue(cell);
                                if (!diagnosticoL.equals("")) {
                                    listaDiagnosticos.stream().filter(d -> (d.getClave().toUpperCase().equals(diagnosticoL.toUpperCase()))).forEachOrdered(cnsmr -> {
                                        idDiagnosticoL = cnsmr.getIdDiagnostico();
                                        esDiagnostico = Constantes.ACTIVO;
                                    });
                                }
                                break;
                            case 2: //insumo
                                insumoL = getValue(cell);
                                if (!insumoL.equals("")) {
                                    listaMedicamentos.stream().filter(m -> (m.getClaveInstitucional().toUpperCase().equals(insumoL.toUpperCase()))).forEachOrdered(cnsmr -> {
                                        idMedicamentoL = cnsmr.getIdMedicamento();
                                        idUnidadL = cnsmr.getIdUnidadConcentracion();
                                        esMedicamento = Constantes.ACTIVO;
                                    });
                                }
                                break;
                            case 3:// dosis minima
                                dosisMinL = new BigDecimal(getValue(cell));
                                break;
                            case 4: //dosis maxima
                                dosisMaxL = new BigDecimal(getValue(cell));
                                break;
                            case 5: // frecuencia minima
                                frecuenciaMinL = Integer.parseInt(getValue(cell));
                                break;
                            case 6: // frecuencia maxima
                                frecuenciaMaxL = Integer.parseInt(getValue(cell));
                                break;
                            case 7: // duración minima
                                duracionMinL = Integer.parseInt(getValue(cell));
                                break;
                            case 8: // duración maxima
                                duracionMaxL = Integer.parseInt(getValue(cell));
                                break;
                            case 9: // descripción
                                descripcionL = getValue(cell);
                                break;                            
                            case 10: //fuente
                                fuenteL = getValue(cell);
                                if (!fuenteL.equals("")) {
                                    listaEmisores.stream().filter(e -> (e.getNombreEmisor().toUpperCase().equals(fuenteL.toUpperCase()))).forEachOrdered(cnsmr -> {
                                        idFuenteL = cnsmr.getIdEmisor();
                                        esFuente = Constantes.ACTIVO;
                                    });
                                }
                                break;
                            case 11: // estatus
                                estatusL = getValue(cell);
                                if (!estatusL.equals("")) {
                                    estatusInd = estatusL.equals("Si") ? Constantes.ESTATUS_ACTIVO : Constantes.ESTATUS_INACTIVO;
                                }
                                break; 
                            case 12: //riesgo
                                riesgoL = getValue(cell);
                                if(!riesgoL.equals("")) {
                                    riesgosReaccion.stream().filter((r) -> (r.toUpperCase().equals(riesgoL.toUpperCase()))).forEachOrdered((cnsmr) -> {
                                        esRiesgo = Constantes.ACTIVO;
                                    });
                                }
                                break;    
                            default:
                            
                        }
                    }
                    
                     IndTerapeuticaExtended indicacionLayout7 = new IndTerapeuticaExtended();
                    IndTerapeuticaExtended indTerapeuticaExtended = new IndTerapeuticaExtended();                                        

                    exito = validaDatos();
                    if (exito) {
                        indTerapeuticaExtended.setIdDiagnostico(idDiagnosticoL);
                        indTerapeuticaExtended.setIdInsumo(idMedicamentoL);

                        IndTerapeuticaExtended indicacionExiste = indTerapeuticaService.buscarExistencia(indTerapeuticaExtended);                       
                        if (indicacionExiste == null) {
                            exito = Constantes.ACTIVO;
                            indicacionLayout7.setIdIndTerapeutica(Comunes.getUUID());
                            indicacionLayout7.setIdTipoEdadPaciente(idTipoEdadPacienteL);
                            indicacionLayout7.setIdDiagnostico(idDiagnosticoL);
                            indicacionLayout7.setIdInsumo(idMedicamentoL);
                            indicacionLayout7.setDosisMin(dosisMaxL);
                            indicacionLayout7.setDosisMax(dosisMaxL);
                            indicacionLayout7.setIdUnidad(idUnidadL);
                            indicacionLayout7.setFrecuenciaInferior(frecuenciaMinL);
                            indicacionLayout7.setFrecuenciaSuperior(frecuenciaMaxL);
                            indicacionLayout7.setDuracionMinima(duracionMinL);
                            indicacionLayout7.setDuracionMaxima(duracionMaxL);
                            indicacionLayout7.setDescripcion(descripcionL);
                            indicacionLayout7.setRestrictiva(idFuenteL);
                            indicacionLayout7.setIdEstatus(estatusInd);
                            indicacionLayout7.setRiesgo(riesgoL);
                            indicacionLayout7.setInsertFecha(new Date());
                            indicacionLayout7.setInsertIdUsuario(sesion.getUsuarioSelected().getIdUsuario());
                            
                            exito = indTerapeuticaService.insertarIndicTerapeutica(indicacionLayout7);

                            if (!exito) {
                                motivo = motivo = RESOURCES.getString("indTerap.err.guardarInd");
                            }
                        } else {
                            motivo = motivo = RESOURCES.getString("indTerap.err.existe");
                            exito = Constantes.INACTIVO;
                        }
                    }

                    if (!exito) {
                       //llenar objeto que no se logro insertar
                        IndTerapeuticaExtended unaIndicacion =  new IndTerapeuticaExtended();
                        
                        unaIndicacion.setClaveInstitucional(insumoL);
                        unaIndicacion.setTipoPaciente(tipoEdadPacienteL);
                        unaIndicacion.setNombreDiagnostico(diagnosticoL);
                        unaIndicacion.setDosisMin(dosisMinL);
                        unaIndicacion.setDosisMax(dosisMaxL);
                        unaIndicacion.setFrecuenciaInferior(frecuenciaMinL);
                        unaIndicacion.setFrecuenciaSuperior(frecuenciaMaxL);
                        unaIndicacion.setDuracionMinima(duracionMinL);
                        unaIndicacion.setDuracionMinima(duracionMinL);
                        unaIndicacion.setDescripcion(descripcionL); 
                        unaIndicacion.setRiesgo(riesgoL);
                        unaIndicacion.setUpdateIdUsuario(motivo);
                        
                        indicacionLayout.add(unaIndicacion);
                    }
                }
                num++;
            }
        } catch (Exception ex) {
            LOGGER.error("ERROR en readLayout2003: {}", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("interMedic.err.lerr2003"), null);
        }
    }
    
    private boolean validaDatos() {
        boolean cont = Constantes.ACTIVO;
        motivo = "";
        
        if(!tipoEdadPacienteL.equals("")) {
            if(!esTipoEdadPaciente) {                
                cont = Constantes.INACTIVO;
                motivo = RESOURCES.getString("indTerap.err.noTipoPaciente");
            }
        } else {            
            cont = Constantes.INACTIVO;
            motivo = motivo + " " + RESOURCES.getString("indTerap.err.tipoPacienteReq");
        }
        if(!diagnosticoL.equals("")) {
            if(!esDiagnostico) {
                cont = Constantes.INACTIVO;
                motivo = motivo + " " + RESOURCES.getString("indTerap.err.noDiagnostico");
            }            
        } else {
            cont = Constantes.INACTIVO;
            motivo = motivo + " " + RESOURCES.getString("indTerap.err.diagnosticoReq");
        }
        if(!insumoL.equals("")) {
            if(!esMedicamento) {
                cont = Constantes.INACTIVO;
                motivo = motivo + " " + RESOURCES.getString("indTerap.err.noClaveInsumo");
            }
        } else {
            cont = Constantes.INACTIVO;
            motivo = motivo + " " + RESOURCES.getString("indTerap.err.claveInsumoReq");
        }
        if(dosisMinL != null) {
            if(dosisMinL.compareTo(BigDecimal.ZERO) == 0) {
                cont = Constantes.INACTIVO;
                motivo = motivo + " " + RESOURCES.getString("indTerap.err.dosisMinCero");
            }
        } else {
            cont = Constantes.INACTIVO;
            motivo = motivo + " " + RESOURCES.getString("indTerap.err.dosisMinReq");
        }
        if(dosisMaxL != null) {
            if(dosisMaxL.compareTo(BigDecimal.ZERO) == 0) {
                cont = Constantes.INACTIVO;
                motivo = motivo + " " + RESOURCES.getString("indTerap.err.dosisMaxCero");
            }
        } else {
            cont = Constantes.INACTIVO;
            motivo = motivo + " " + RESOURCES.getString("indTerap.err.dosisMaxReq");
        }
        if(dosisMinL.compareTo(dosisMaxL) == 0 || dosisMinL.compareTo(dosisMaxL) == 1) {
             motivo = motivo + " " + RESOURCES.getString("indTerap.err.dosisMayorQue");
        }        
        if(frecuenciaMinL != null) {
            if(frecuenciaMinL <= 0) {
                cont = Constantes.INACTIVO;
                motivo = motivo + " " + RESOURCES.getString("indTerap.err.frecMinCero");
            }
        } else {
            cont = Constantes.INACTIVO;
            motivo = motivo + " " + RESOURCES.getString("indTerap.err.frecuenciaMinReq");
        }
        if(frecuenciaMaxL != null) {
            if(frecuenciaMaxL <= 0) {
                cont = Constantes.INACTIVO;
                motivo = motivo + " " + RESOURCES.getString("indTerap.err.frecMaxCero");
            }
        } else {
            cont = Constantes.INACTIVO;
            motivo = motivo + " " + RESOURCES.getString("indTerap.err.frecuenciaMaxReq");
        }
        if(frecuenciaMinL >= frecuenciaMaxL) {
             motivo = motivo + " " + RESOURCES.getString("indTerap.err.frecMayorQue");
        }
        if(duracionMinL != null) {
            if(duracionMinL <= 0) {
                cont = Constantes.INACTIVO;
                motivo = motivo + " " + RESOURCES.getString("indTerap.err.duracionMinCero");
            }
        } else {
            cont = Constantes.INACTIVO;
            motivo = motivo + " " + RESOURCES.getString("indTerap.err.duracionMinReq");
        }
        if(duracionMaxL != null) {
            if(duracionMaxL <= 0) {
                cont = Constantes.INACTIVO;
                motivo = motivo + " " + RESOURCES.getString("indTerap.err.duracionMaxCero");
            }
        } else {
            cont = Constantes.INACTIVO;
            motivo = motivo + " " + RESOURCES.getString("indTerap.err.duracionMaxReq");
        }
        
        if(duracionMinL >= duracionMaxL) {
             motivo = motivo + " " + RESOURCES.getString("indTerap.err.duracionMayorQue");
        }
        if(!fuenteL.equals("")) {
            if(!esFuente) {
                cont = Constantes.INACTIVO;
                motivo = RESOURCES.getString("indTerap.err.noFuente");
            }
        } else {
            cont = Constantes.INACTIVO;
            motivo = motivo + " " + RESOURCES.getString("indTerap.err.fuenteReq");
        }
        if(estatusL.equals("")) {
            cont = Constantes.INACTIVO;
            motivo = motivo + " " + RESOURCES.getString("indTerap.err.estatusReq");
        }
        
        return cont;
    }    
    
    private void cleanObjects() {        
        tipoEdadPacienteL = "";
        esTipoEdadPaciente = Constantes.INACTIVO;
        diagnosticoL = "";
        esDiagnostico = Constantes.INACTIVO;
        insumoL = "";
        esMedicamento = Constantes.INACTIVO;
        dosisMinL = BigDecimal.ZERO;
        dosisMaxL = BigDecimal.ZERO;
        frecuenciaMinL = 0;
        frecuenciaMaxL = 0;
        duracionMinL = 0;
        duracionMaxL = 0;
        fuenteL = "";
        esFuente = Constantes.INACTIVO;
        descripcionL = "";
        estatusL = "";
        motivo = "";
    }
    
    /**
     * Lectura de Archivo de layout cargado
     *
     * @param bites
     * @param name
     * @return
     * @throws FileNotFoundException
     * @throws Exception
     */
    private String createFile(byte[] bites, String name) throws IOException, InterruptedException {
        if (bites != null) {
            String path = UtilPath.getPathDefinida(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/"));
            fileImg = sdf.format(date) + name;
            pathDefinition = path + Constantes.PATH_TMP + fileImg;
            try (FileOutputStream fos = new FileOutputStream(pathDefinition)) {
                fos.write(bites);
                fos.flush();
            }
            Thread.sleep(2000);
            this.setNameFile(Constantes.PATH_IMG + fileImg);

            return pathDefinition;
        } else {
            this.setNameFile("");
        }
        return "";
    }
    
    private String getValue(Cell cell) {
        switch (cell.getCellType()) {
            case BLANK:
                return "";

            case BOOLEAN:
                return "CELL_TYPE_BOOLEAN";

            case ERROR:
                return "CELL_TYPE_ERROR";

            case FORMULA:
                return cell.getStringCellValue();

            case NUMERIC:
                return cell.getNumericCellValue() + "";

            case STRING:
                return cell.getStringCellValue();

            default:
                return RESOURCES.getString("indTerap.err.noValor");

        }
    }
    
    public void actualizarTablaIndicaciones() {
        try {
            cadenaBusqueda = null;
            buscarIndicacionesTerapeutcas();
        } catch(Exception ex) {
            LOGGER.error("Error al momento de actualizar la tabla despues del Layout:   " + ex.getMessage());
        }
    }
    
    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

    public List<IndTerapeuticaExtended> getListaIndTerapeutica() {
        return listaIndTerapeutica;
    }

    public void setListaIndTerapeutica(List<IndTerapeuticaExtended> listaIndTerapeutica) {
        this.listaIndTerapeutica = listaIndTerapeutica;
    }

    public List<TipoEdadPaciente> getListaTipoEdadPaciente() {
        return listaTipoEdadPaciente;
    }

    public void setListaTipoEdadPaciente(List<TipoEdadPaciente> listaTipoEdadPaciente) {
        this.listaTipoEdadPaciente = listaTipoEdadPaciente;
    }

    public TipoEdadPaciente getTipoEdadPAcienteSelect() {
        return tipoEdadPAcienteSelect;
    }

    public void setTipoEdadPAcienteSelect(TipoEdadPaciente tipoEdadPAcienteSelect) {
        this.tipoEdadPAcienteSelect = tipoEdadPAcienteSelect;
    }

    public Integer getIdTipoEdadPaciente() {
        return idTipoEdadPaciente;
    }

    public void setIdTipoEdadPaciente(Integer idTipoEdadPaciente) {
        this.idTipoEdadPaciente = idTipoEdadPaciente;
    }

    public List<Emisor> getListaEmisores() {
        return listaEmisores;
    }

    public void setListaEmisores(List<Emisor> listaEmisores) {
        this.listaEmisores = listaEmisores;
    }

    public Integer getIdEmisor() {
        return idEmisor;
    }

    public void setIdEmisor(Integer idEmisor) {
        this.idEmisor = idEmisor;
    }

    public List<ViaAdministracion> getListaViasAdmon() {
        return listaViasAdmon;
    }

    public void setListaViasAdmon(List<ViaAdministracion> listaViasAdmon) {
        this.listaViasAdmon = listaViasAdmon;
    }

    public Integer getIdViaAdministracion() {
        return idViaAdministracion;
    }

    public void setIdViaAdministracion(Integer idViaAdministracion) {
        this.idViaAdministracion = idViaAdministracion;
    }

    public IndTerapeuticaExtended getIndTerapeuticaSelect() {
        return indTerapeuticaSelect;
    }

    public void setIndTerapeuticaSelect(IndTerapeuticaExtended indTerapeuticaSelect) {
        this.indTerapeuticaSelect = indTerapeuticaSelect;
    }

    public Diagnostico getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(Diagnostico diagnostico) {
        this.diagnostico = diagnostico;
    }

    public VistaMedicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(VistaMedicamento medicamento) {
        this.medicamento = medicamento;
    }

    public Integer getRangoInferior() {
        return rangoInferior;
    }

    public void setRangoInferior(Integer rangoInferior) {
        this.rangoInferior = rangoInferior;
    }

    public Integer getRangoSuperior() {
        return rangoSuperior;
    }

    public void setRangoSuperior(Integer rangoSuperior) {
        this.rangoSuperior = rangoSuperior;
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

    public String getCadenaBusqueda() {
        return cadenaBusqueda;
    }

    public void setCadenaBusqueda(String cadenaBusqueda) {
        this.cadenaBusqueda = cadenaBusqueda;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public IndTerapeuticaLazy getIndTerapeuticaLazy() {
        return indTerapeuticaLazy;
    }

    public void setIndTerapeuticaLazy(IndTerapeuticaLazy indTerapeuticaLazy) {
        this.indTerapeuticaLazy = indTerapeuticaLazy;
    }

    public boolean isEditar() {
        return editar;
    }

    public void setEditar(boolean editar) {
        this.editar = editar;
    }

    public String getPathDefinition() {
        return pathDefinition;
    }

    public void setPathDefinition(String pathDefinition) {
        this.pathDefinition = pathDefinition;
    }

    public String getFileImg() {
        return fileImg;
    }

    public void setFileImg(String fileImg) {
        this.fileImg = fileImg;
    }

    public String getNameFile() {
        return nameFile;
    }

    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }

    public boolean isContinuar() {
        return continuar;
    }

    public void setContinuar(boolean continuar) {
        this.continuar = continuar;
    }

    public List<IndTerapeuticaExtended> getIndicacionLayout() {
        return indicacionLayout;
    }

    public void setIndicacionLayout(List<IndTerapeuticaExtended> indicacionLayout) {
        this.indicacionLayout = indicacionLayout;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
    
    public List<String> getRiesgosReaccion() {
        return riesgosReaccion;
    }

    public void setRiesgosReaccion(List<String> riesgosReaccion) {
        this.riesgosReaccion = riesgosReaccion;
    }
    
}
