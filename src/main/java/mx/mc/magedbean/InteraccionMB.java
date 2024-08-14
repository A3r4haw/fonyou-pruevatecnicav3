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
import mx.mc.lazy.InteraccionLazy;
import mx.mc.model.Emisor;
import mx.mc.model.Interaccion;
import mx.mc.model.InteraccionExtended;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.SustanciaActiva;
import mx.mc.model.TipoInteraccion;
import mx.mc.model.VistaMedicamento;
import mx.mc.service.EmisorService;
import mx.mc.service.InteraccionService;
import mx.mc.service.MedicamentoService;
import mx.mc.service.SustanciaActivaService;
import mx.mc.service.TipoInteraccionService;
import mx.mc.util.Comunes;
import mx.mc.util.FechaUtil;
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
public class InteraccionMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(InteraccionMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    
    private PermisoUsuario permiso;
    private SesionMB sesion;
    private boolean isAdmin;
    private boolean isJefeArea;
    
    private InteraccionLazy interaccionLazy;
    
    private TipoInteraccion tipoInteraccionSelect;
    private Integer idTipoInteraccion;
    private Emisor emisorSelect;
    private Integer idEmisor;
    private List<TipoInteraccion> listaTipoInteraccion;
    private List<Emisor> listaEmisores;
    private List<InteraccionExtended> listaInteracciones;
    private TipoInteraccion tipoInteraccionSelect1;
    private Integer idTipoInteraccion1;
    
    private InteraccionExtended interaccionSelect;    
    
    private String cadenaBusqueda;
    private Date fechaActual;
    private boolean messaje;
    private VistaMedicamento medicamento;
    private VistaMedicamento medicamento1;
    private List<VistaMedicamento> medicamentoList;
    private boolean status;
    
    private String pathDefinition;
    private String fileImg;
    private String nameFile;
    private Date date = new Date();
    private SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss");
    private List<InteraccionExtended> interaccionLayout;
    
    private Integer idInteraccionL1;
    private String interaccionL1;
    private Boolean esInteraccion1;
    private Integer idInteraccionL2;
    private String interaccionL2;
    private Boolean esInteraccion2;
    private Integer idTipoInteraccionL;
    private String tipoInteraccionL;
    private Boolean esTipo;
    private String notasL;
    private Integer idEmisorL;
    private String emisorL;
    private Boolean esEmisor;
    private String riesgoL;
    private Boolean esRiesgo;
    private String motivo;
    private boolean continuar;
    private boolean editar;
    private List<String> riesgosReaccion;
    
    private List<SustanciaActiva> listaSustanciasActivas;
    
    @Autowired
    private transient TipoInteraccionService tipoInteraccionService;
    
    @Autowired
    private transient EmisorService emisorService;
    
    @Autowired
    private transient InteraccionService interaccionService;
    
    @Autowired
    private transient MedicamentoService medicamentoService;
    
    @Autowired
    private transient SustanciaActivaService sustanciaActivaService;
    
    
    
    /**
     * Consulta los permisos del usuario
     */
    @PostConstruct
    public void init() {
        
        permiso = Comunes.obtenerPermisos(Transaccion_Enum.INTERACCION.getSufijo());
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        initialize();
        validarUsuarioAdministrador();
        buscarEmisores();
        buscarTipoInteracciones();
        buscarTodasSustancias();
        buscarInteracciones();
        riesgosReaccion.add(RiesgoReaccion_Enum.ALTO.getValue());
        riesgosReaccion.add(RiesgoReaccion_Enum.MEDIO.getValue());
        riesgosReaccion.add(RiesgoReaccion_Enum.BAJO.getValue());
    }
    
    private void initialize() {
        isAdmin = Constantes.INACTIVO;
        isJefeArea = Constantes.INACTIVO;
        fechaActual = FechaUtil.obtenerFechaInicio();
        pathDefinition = "";
        fileImg = "";
        nameFile = "";
        editar = false;
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
    
    public void buscarTipoInteracciones() {
        try {
            listaTipoInteraccion = tipoInteraccionService.obtenerTodo();
        } catch(Exception ex) {
            LOGGER.error("Error al obtener lista de tipo de interacción:  " + ex.getMessage());
        }
    }
    
    public void buscarEmisores() {
        try {
            listaEmisores = emisorService.obtenerTodo();
        } catch(Exception ex) {
            LOGGER.error("Error al momento de buscar la lista de emisores: " + ex.getMessage());
        }
    }
    
    public void obtenerInteraccion(Integer idInteraccion) {
        try{
            nuevaInteraccion();
            editar = true;
            interaccionSelect = interaccionService.obtenerInteraccionById(idInteraccion);
            idTipoInteraccion1 = interaccionSelect.getIdTipoInteraccion();
            idEmisor = interaccionSelect.getIdEmisor();
            medicamento = new VistaMedicamento();
            medicamento1 = new VistaMedicamento();
            medicamento.setSustanciaActiva(interaccionSelect.getMedicamento());
            medicamento1.setSustanciaActiva(interaccionSelect.getMedicamentoInteraccion());
        } catch(Exception ex) {
            LOGGER.error("Error al obtener la interacción:  " + ex.getMessage());
        }
    }
    
    public void buscarTodasSustancias() {
        try {
            listaSustanciasActivas = sustanciaActivaService.obtenerTodo();
        } catch(Exception ex) {
            LOGGER.error("Error al buscar todas las sustancias  " + ex.getMessage());
        }
    }
    
    public void deleteInteraccion(Integer idInteraccion) {
        try {
            LOGGER.info("Metodo de eliminación de interacción " + idInteraccion);
            boolean resp = false;
            if (!this.permiso.isPuedeEliminar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.accion"), "");
                return;
            }
            resp = interaccionService.eliminarInteraccionPorId(idInteraccion);
            if(resp) {
                Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("interMedic.info.elimminar"), "");
                buscarInteracciones();
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("interMedic.err.eliminar"), "");
            }
        } catch(Exception ex) {
            LOGGER.error("Error al eliminar una interacción:   " + ex.getMessage());
        }
    }
    
    public List<VistaMedicamento> autoComplete(String cadena) {
        try {           
            medicamentoList = medicamentoService.buscarSalMedicamento(cadena);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener Medicamentos autocomplete: ", ex.getMessage());
        }
        return medicamentoList;
    }
    
     public void handleSelect(SelectEvent e) {
        medicamento = (VistaMedicamento) e.getObject();
    }

    public void handleUnSelect(UnselectEvent e) {
        medicamento = (VistaMedicamento) e.getObject();
    }
    
    public void buscarInteracciones() {
        try {            
            if (!this.permiso.isPuedeVer()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.accion"), "");
                return;
            }
            if (cadenaBusqueda != null
                    && cadenaBusqueda.trim().isEmpty()) {
                cadenaBusqueda = null;
            }            
            if(idTipoInteraccion == null) {
                idTipoInteraccion = 0;
            }
            interaccionLazy = new InteraccionLazy(interaccionService, idTipoInteraccion, cadenaBusqueda);

            cadenaBusqueda = null;
        } catch(Exception ex) {
            LOGGER.error("Ocurrio un error al buscar las interacciones:  " + ex.getMessage());
        }
    }
    
    public void nuevaInteraccion() {
        try {
            interaccionSelect = new InteraccionExtended();
            interaccionSelect.setFecha(new Date());
            interaccionSelect.setNotas("");
            medicamento = null;
            medicamento1 = null;
            idTipoInteraccion1 = null;
            idEmisor = null;
            interaccionLayout = new ArrayList<>();
            editar = false;
        } catch(Exception ex) {
            LOGGER.error("Ocurrio un error al abrir modal para generar nueva interacción:  " + ex.getMessage());
        }
    }
    
    public void crearInteraccion() {
        try {
            status = Constantes.ACTIVO;
            Integer idInteraccion;
            SustanciaActiva sustancia;
            SustanciaActiva sustanciaInteraccion;
            String valida = "";            
            if(permiso.isPuedeCrear()) {                
                
                valida = validarCamposFormulario();
                if(valida.equals("")) {
                    interaccionSelect.setIdTipoInteraccion(idTipoInteraccion1);
                    if(medicamento.getSustanciaActiva() != null) {
                       sustancia = new SustanciaActiva();
                       sustancia = sustanciaActivaService.obtenerSustanciaPorNombre(medicamento.getSustanciaActiva());
                        if(sustancia != null) {
                            interaccionSelect.setIdSustanciaUno(sustancia.getIdSustanciaActiva());
                        }
                    }
                    if(medicamento.getSustanciaActiva() != null) {                        
                        sustanciaInteraccion = sustanciaActivaService.obtenerSustanciaPorNombre(medicamento1.getSustanciaActiva());
                        if(sustanciaInteraccion != null) {
                            interaccionSelect.setIdSustanciaDos(sustanciaInteraccion.getIdSustanciaActiva());
                        }
                    }
                    if(medicamento.getSustanciaActiva().equals(medicamento1.getSustanciaActiva())) {
                         Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("interMedic.err.sustanciaIgual") + 
                                 "  " + medicamento.getSustanciaActiva() + " - " + medicamento1.getSustanciaActiva(), null);
                          status = Constantes.INACTIVO;
                          return;
                    }
                    interaccionSelect.setIdEmisor(idEmisor);
                    
                    if(!editar) {                       
                        InteraccionExtended interaccionExiste = interaccionService.buscarInteraccion(interaccionSelect);
                        if(interaccionExiste != null) {
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("interMedic.err.existe") + 
                                    "  " + medicamento.getSustanciaActiva() + " - " + medicamento1.getSustanciaActiva(), null);
                            status = Constantes.INACTIVO;
                        } else {
                            idInteraccion = interaccionService.obtenerSiguienteIdInteraccion();
                            if(idInteraccion != null) {
                                interaccionSelect.setIdInteraccion(idInteraccion);
                            } else {
                                interaccionSelect.setIdInteraccion(Constantes.ES_ACTIVO);
                            }
                            interaccionSelect.setInsertFecha(new Date());
                            interaccionSelect.setInsertIdUsuario(sesion.getUsuarioSelected().getIdUsuario());
                            boolean resp = interaccionService.insertarInteraccion(interaccionSelect);
                            if(!resp) {
                                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("interMedic.err.guardar"), null);
                                status = Constantes.INACTIVO;
                            } else {
                                 Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("interMedic.info.guardar"), null);
                                 buscarInteracciones();
                            }
                        }   
                    } else {
                        InteraccionExtended  interaccionExiste = interaccionService.buscarInteraccion(interaccionSelect);
                          
                        if(interaccionExiste != null) {
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("interMedic.err.existe") + 
                                     "  " + medicamento.getSustanciaActiva() + " - " + medicamento1.getSustanciaActiva(), null);
                            status = Constantes.INACTIVO;
                        } else {
                            interaccionSelect.setUpdateFecha(new Date());
                            interaccionSelect.setUpdateIdUsuario(sesion.getUsuarioSelected().getIdUsuario());
                            boolean resp = interaccionService.actualizarInteraccion(interaccionSelect);
                            if(!resp) {
                                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("interMedic.err.guardar"), null);
                                status = Constantes.INACTIVO;
                            } else {
                                 Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("interMedic.info.guardar"), null);
                                 buscarInteracciones();
                            }
                        }                        
                    }
                                    
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, valida, null);
                    status = Constantes.INACTIVO;
                }
                
            }else {
                Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString("err.accion"), null);
                status = Constantes.INACTIVO;
            }   
            
        } catch(Exception ex) {
            LOGGER.error("Ocurrio un error al guardar la interaccion:  " + ex.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }
    
    public String validarCamposFormulario() {
        String result = "";
        if(interaccionSelect.getFecha() == null) {
            return  RESOURCES.getString("interMedic.err.fecha");
        }
        if(idTipoInteraccion1 == null) {
            return RESOURCES.getString("interMedic.err.tipo");
        }
        if(medicamento == null) {
            return RESOURCES.getString("interMedic.err.medicamento1");
        }
        if(medicamento1 == null) {
            return RESOURCES.getString("interMedic.err.medicamento2");
        }
        if(idEmisor == null) {
            return RESOURCES.getString("interMedic.err.origen");
        }
        if(interaccionSelect.getRiesgo() == null){
            return RESOURCES.getString("interMedic.err.riesgo");
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
            if (!interaccionLayout.isEmpty()) {
                continuar = Constantes.ACTIVO;
            }

        } catch(Exception ex) {
            LOGGER.error("ERROR en layoutFileUpload: {}", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("interMedic.err.archivoIncorrecto"), null);
            continuar = Constantes.INACTIVO;

        }
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
            interaccionLayout = new ArrayList<>();
            while (iterator.hasNext()) {
                Row nextRow = iterator.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();
                cleanObjects();
                if (num > 5) {
                    exito = Constantes.ACTIVO;
                    while (cellIterator.hasNext()) {
                        Cell cell7 = cellIterator.next();
                        switch (cell7.getColumnIndex()) {
                            case 0: //Interaccion1
                                interaccionL1 = getValue(cell7);
                                if (!interaccionL1.equals("")) {
                                    listaSustanciasActivas.stream().filter(sa -> (sa.getNombreSustanciaActiva().toUpperCase().equals(interaccionL1.toUpperCase()))).forEachOrdered(cnsmr -> {
                                        idInteraccionL1 = cnsmr.getIdSustanciaActiva();
                                        esInteraccion1 = Constantes.ACTIVO;
                                    });
                                }
                                break;
                            case 1: //Interaccion2                                
                                interaccionL2 = getValue(cell7);
                                if (!interaccionL2.equals("")) {
                                    listaSustanciasActivas.stream().filter(sa -> (sa.getNombreSustanciaActiva().toUpperCase().equals(interaccionL2.toUpperCase()))).forEachOrdered(cnsmr -> {
                                        idInteraccionL2 = cnsmr.getIdSustanciaActiva();
                                        esInteraccion2 = Constantes.ACTIVO;
                                    });
                                }
                                break;
                            case 2: //tipoInteraccion
                                tipoInteraccionL = getValue(cell7);
                                if (!tipoInteraccionL.equals("")) {
                                    listaTipoInteraccion.stream().filter(ti -> (ti.getTipoInteraccion().toUpperCase().equals(tipoInteraccionL.toUpperCase()))).forEachOrdered(cnsmr -> {
                                        idTipoInteraccionL = cnsmr.getIdTipoInteraccion();
                                        esTipo = Constantes.ACTIVO;
                                    });
                                }
                                break;
                            case 3: //notas                                
                                notasL = getValue(cell7);
                                break;
                            case 4: //emisor u origen
                                emisorL = getValue(cell7);
                                if (!emisorL.equals("")) {
                                    listaEmisores.stream().filter(e -> (e.getNombreEmisor().toUpperCase().equals(emisorL.toUpperCase()))).forEachOrdered(cnsmr -> {
                                        idEmisorL = cnsmr.getIdEmisor();
                                        esEmisor = Constantes.ACTIVO;
                                    });
                                }
                                break;
                            case 5: //riesgo
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
                    InteraccionExtended interaccionLayout7 = new InteraccionExtended();
                    InteraccionExtended interaccionExtended = new InteraccionExtended();
                    
                    exito = validaDatos();
                    if (exito) {
                        interaccionExtended.setIdSustanciaUno(idInteraccionL1);
                        interaccionExtended.setIdSustanciaDos(idInteraccionL2);

                        
                        InteraccionExtended interaccionExiste = interaccionService.buscarInteraccion(interaccionExtended);                        
                        if (interaccionExiste == null) {
                            exito = Constantes.ACTIVO;
                            Integer idInteraccion = interaccionService.obtenerSiguienteIdInteraccion();
                            if(idInteraccion != null) {
                                interaccionLayout7.setIdInteraccion(idInteraccion);
                            } else {
                                interaccionLayout7.setIdInteraccion(Constantes.ES_ACTIVO);
                            }
                            interaccionLayout7.setFecha(new Date());
                            interaccionLayout7.setIdSustanciaUno(idInteraccionL1);
                            interaccionLayout7.setIdSustanciaDos(idInteraccionL2);
                            interaccionLayout7.setIdTipoInteraccion(idTipoInteraccionL);
                            interaccionLayout7.setNotas(notasL);
                            interaccionLayout7.setIdEmisor(idEmisorL);
                            interaccionLayout7.setRiesgo(riesgoL);
                            interaccionLayout7.setInsertFecha(new Date());
                            interaccionLayout7.setInsertIdUsuario(sesion.getUsuarioSelected().getIdUsuario());
                            
                            exito = interaccionService.insertarInteraccion(interaccionLayout7);

                            if (!exito) {
                                motivo = RESOURCES.getString("interMedic.err.insertInter");
                            }
                        } else {
                            motivo = RESOURCES.getString("interMedic.err.existe");
                            exito = Constantes.INACTIVO;
                        }
                    }

                    if (!exito) {
                        //llenar objeto que no se logro insertar
                        InteraccionExtended unaInteraccion = new InteraccionExtended();
                        unaInteraccion.setFecha(new Date());
                        unaInteraccion.setMedicamento(interaccionL1);
                        unaInteraccion.setMedicamentoInteraccion(interaccionL2);
                        unaInteraccion.setNombreEmisor(emisorL);
                        unaInteraccion.setTipoInteraccion(tipoInteraccionL);
                        unaInteraccion.setRiesgo(riesgoL);
                        unaInteraccion.setUpdateIdUsuario(motivo);
                        interaccionLayout.add(unaInteraccion);
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
            interaccionLayout = new ArrayList<>();
            while (iterator.hasNext()) {
                Row nextRow = iterator.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();
                cleanObjects();
                if (num > 5) {
                    exito = Constantes.ACTIVO;
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        switch (cell.getColumnIndex()) {
                            case 0: //Interaccion1
                                interaccionL1 = getValue(cell);
                                if (!interaccionL1.equals("")) {
                                    listaSustanciasActivas.stream().filter(sa -> (sa.getNombreSustanciaActiva().toUpperCase().equals(interaccionL1.toUpperCase()))).forEachOrdered(cnsmr -> {
                                        idInteraccionL1 = cnsmr.getIdSustanciaActiva();
                                        esInteraccion1 = Constantes.ACTIVO;
                                    });
                                }
                                break;  
                            case 1: //Interaccion2                                
                                interaccionL2 = getValue(cell);
                                if (!interaccionL2.equals("")) {
                                    listaSustanciasActivas.stream().filter(sa -> (sa.getNombreSustanciaActiva().toUpperCase().equals(interaccionL2.toUpperCase()))).forEachOrdered(cnsmr -> {
                                        idInteraccionL2 = cnsmr.getIdSustanciaActiva();
                                        esInteraccion2 = Constantes.ACTIVO;
                                    });
                                }
                                break;
                            case 2: //tipoInteraccion
                                tipoInteraccionL = getValue(cell);
                                if (!tipoInteraccionL.equals("")) {
                                    listaTipoInteraccion.stream().filter(ti -> (ti.getTipoInteraccion().toUpperCase().equals(tipoInteraccionL.toUpperCase()))).forEachOrdered(cnsmr -> {
                                        idTipoInteraccionL = cnsmr.getIdTipoInteraccion();
                                        esTipo = Constantes.ACTIVO;
                                    });
                                }
                                break;
                            case 3: //notas                                
                                notasL = getValue(cell);
                                break;
                            case 4: //emisor u origen
                                emisorL = getValue(cell);
                                if (!emisorL.equals("")) {
                                    listaEmisores.stream().filter(e -> (e.getNombreEmisor().toUpperCase().equals(emisorL.toUpperCase()))).forEachOrdered(cnsmr -> {
                                        idEmisorL = cnsmr.getIdEmisor();
                                        esEmisor = Constantes.ACTIVO;
                                    });
                                }
                                break;
                             case 5: //riesgo
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
                    
                    Interaccion interaccionLayout7 = new Interaccion();
                    InteraccionExtended interaccionExtended = new InteraccionExtended();

                    exito = validaDatos();
                    if (exito) {
                        interaccionExtended.setIdSustanciaUno(idInteraccionL1);
                        interaccionExtended.setIdSustanciaDos(idInteraccionL2);

                        
                        InteraccionExtended interaccionExiste = interaccionService.buscarInteraccion(interaccionExtended);                        
                        if (interaccionExiste == null) {
                            exito = Constantes.ACTIVO;
                            interaccionLayout7.setFecha(new Date());
                            interaccionLayout7.setIdSustanciaUno(idInteraccionL1);
                            interaccionLayout7.setIdSustanciaDos(idInteraccionL2);
                            interaccionLayout7.setIdTipoInteraccion(idTipoInteraccionL);
                            interaccionLayout7.setNotas(notasL);
                            interaccionLayout7.setIdEmisor(idEmisorL);
                            interaccionLayout7.setRiesgo(riesgoL);
                            interaccionLayout7.setInsertFecha(new Date());
                            interaccionLayout7.setInsertIdUsuario(sesion.getUsuarioSelected().getIdUsuario());
                            
                            exito = interaccionService.insertar(interaccionLayout7);

                            if (!exito) {
                                motivo = RESOURCES.getString("interMedic.err.insertInter");
                            }
                        } else {
                            motivo = RESOURCES.getString("interMedic.err.existe");
                            exito = Constantes.INACTIVO;
                        }
                    }

                    if (!exito) {
                        //llenar objeto que no se logro insertar
                        InteraccionExtended unaInteraccion = new InteraccionExtended();
                        unaInteraccion.setFecha(new Date());
                        unaInteraccion.setMedicamento(interaccionL1);
                        unaInteraccion.setMedicamentoInteraccion(interaccionL2);
                        unaInteraccion.setNombreEmisor(emisorL);
                        unaInteraccion.setTipoInteraccion(tipoInteraccionL);
                        unaInteraccion.setRiesgo(riesgoL);
                        unaInteraccion.setUpdateIdUsuario(motivo);
                        interaccionLayout.add(unaInteraccion);
                    }
                }
                num++;
            }
        } catch (Exception ex) {
            LOGGER.error("ERROR en readLayout2003: {}", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("interMedic.err.lerr2003"), null);
        }
    }
    
    private void cleanObjects() {        
        interaccionL1 = "";
        esInteraccion1 = Constantes.INACTIVO;
        interaccionL2 = "";
        esInteraccion2 = Constantes.INACTIVO;
        tipoInteraccionL = "";
        esTipo = Constantes.INACTIVO;
        esEmisor = Constantes.INACTIVO;
        notasL = "";
        emisorL = "";
        motivo = "";
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
                return "valor desconocido";

        }
    }
     
    
    
    private boolean validaDatos() {
        boolean cont = Constantes.ACTIVO;
        motivo = "";
        
        if(!interaccionL1.equals("")) {
            if(!esInteraccion1) {                
                cont = Constantes.INACTIVO;
                motivo = RESOURCES.getString("interMedic.err.noExisteSustancia1");
            }
        } else {            
            cont = Constantes.INACTIVO;
            motivo = motivo + " " + RESOURCES.getString("interMedic.err.sustancaiaUno");
        }
        
        if(!interaccionL2.equals("")){
            if(!esInteraccion2) {                
                cont = Constantes.INACTIVO;
                motivo = motivo + " " + RESOURCES.getString("interMedic.err.noExisteSustancia2");
            }
        } else {            
            cont = Constantes.INACTIVO;
            motivo = motivo + " "+ RESOURCES.getString("interMedic.err.sustancaiaDos");
        }                   

        if (!tipoInteraccionL.equals("")) {
            if (!esTipo) {                
                cont = Constantes.INACTIVO;
                motivo = motivo + " " + RESOURCES.getString("interMedic.err.noExisteTipoInteraccion");
            }
        } else {            
            cont = Constantes.INACTIVO;
            motivo = motivo + " " + RESOURCES.getString("interMedic.err.tipoInteraccion");
        }

        if (!emisorL.equals("")) {
            if (!esEmisor) {                
                cont = Constantes.INACTIVO;
                motivo = motivo + " " + RESOURCES.getString("interMedic.err.noExisteEmisor");
            }
        } else {            
            cont = Constantes.INACTIVO;
            motivo = motivo + " " + RESOURCES.getString("interMedic.err.emisor");
        }
        
        if (!riesgoL.equals("")) {
            if(!esRiesgo) {
                cont =Constantes.INACTIVO;
                motivo = motivo + " " + RESOURCES.getString("interMedic.err.noExisteRiesgo");
            }
        } else {
            cont =Constantes.INACTIVO;
            motivo = motivo + " " + RESOURCES.getString("interMedic.err.riesgo");
        }

        return cont;
    }
    
    public void actualizarTablaInteracciones() {
        try {
            cadenaBusqueda = null;
            buscarInteracciones();
        } catch(Exception ex) {
            LOGGER.error("Error al momento de actualizar la tabla despues del Layout:   " + ex.getMessage());
        }
    }
    
    public InteraccionLazy getInteraccionLazy() {
        return interaccionLazy;
    }

    public void setInteraccionLazy(InteraccionLazy interaccionLazy) {
        this.interaccionLazy = interaccionLazy;
    }

    public TipoInteraccion getTipoInteraccionSelect() {
        return tipoInteraccionSelect;
    }

    public void setTipoInteraccionSelect(TipoInteraccion tipoInteraccionSelect) {
        this.tipoInteraccionSelect = tipoInteraccionSelect;
    }

    public Emisor getEmisorSelect() {
        return emisorSelect;
    }

    public void setEmisorSelect(Emisor emisorSelect) {
        this.emisorSelect = emisorSelect;
    }

    public List<TipoInteraccion> getListaTipoInteraccion() {
        return listaTipoInteraccion;
    }

    public void setListaTipoInteraccion(List<TipoInteraccion> listaTipoInteraccion) {
        this.listaTipoInteraccion = listaTipoInteraccion;
    }

    public List<Emisor> getListaEmisores() {
        return listaEmisores;
    }

    public void setListaEmisores(List<Emisor> listaEmisores) {
        this.listaEmisores = listaEmisores;
    }

    public List<InteraccionExtended> getListaInteracciones() {
        return listaInteracciones;
    }

    public void setListaInteracciones(List<InteraccionExtended> listaInteracciones) {
        this.listaInteracciones = listaInteracciones;
    }

    public InteraccionExtended getInteraccionSelect() {
        return interaccionSelect;
    }

    public void setInteraccionSelect(InteraccionExtended interaccionSelect) {
        this.interaccionSelect = interaccionSelect;
    }

    public String getCadenaBusqueda() {
        return cadenaBusqueda;
    }

    public void setCadenaBusqueda(String cadenaBusqueda) {
        this.cadenaBusqueda = cadenaBusqueda;
    }

    public TipoInteraccion getTipoInteraccionSelect1() {
        return tipoInteraccionSelect1;
    }

    public void setTipoInteraccionSelect1(TipoInteraccion tipoInteraccionSelect1) {
        this.tipoInteraccionSelect1 = tipoInteraccionSelect1;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public boolean isMessaje() {
        return messaje;
    }

    public void setMessaje(boolean messaje) {
        this.messaje = messaje;
    }    

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

    public VistaMedicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(VistaMedicamento medicamento) {
        this.medicamento = medicamento;
    }

    public VistaMedicamento getMedicamento1() {
        return medicamento1;
    }

    public void setMedicamento1(VistaMedicamento medicamento1) {
        this.medicamento1 = medicamento1;
    }

    public List<VistaMedicamento> getMedicamentoList() {
        return medicamentoList;
    }

    public void setMedicamentoList(List<VistaMedicamento> medicamentoList) {
        this.medicamentoList = medicamentoList;
    }

    public Integer getIdTipoInteraccion() {
        return idTipoInteraccion;
    }

    public void setIdTipoInteraccion(Integer idTipoInteraccion) {
        this.idTipoInteraccion = idTipoInteraccion;
    }

    public Integer getIdEmisor() {
        return idEmisor;
    }

    public void setIdEmisor(Integer idEmisor) {
        this.idEmisor = idEmisor;
    }

    public Integer getIdTipoInteraccion1() {
        return idTipoInteraccion1;
    }

    public void setIdTipoInteraccion1(Integer idTipoInteraccion1) {
        this.idTipoInteraccion1 = idTipoInteraccion1;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public SimpleDateFormat getSdf() {
        return sdf;
    }

    public void setSdf(SimpleDateFormat sdf) {
        this.sdf = sdf;
    }

    public List<InteraccionExtended> getInteraccionLayout() {
        return interaccionLayout;
    }

    public void setInteraccionLayout(List<InteraccionExtended> interaccionLayout) {
        this.interaccionLayout = interaccionLayout;
    }

    public boolean isContinuar() {
        return continuar;
    }

    public void setContinuar(boolean continuar) {
        this.continuar = continuar;
    }

    public boolean isEditar() {
        return editar;
    }

    public void setEditar(boolean editar) {
        this.editar = editar;
    }

    public List<String> getRiesgosReaccion() {
        return riesgosReaccion;
    }

    public void setRiesgosReaccion(List<String> riesgosReaccion) {
        this.riesgosReaccion = riesgosReaccion;
    }

}
