package mx.mc.magedbean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.lazy.EstudiosLazy;
import mx.mc.model.Estudio;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.TipoEstudio;
import mx.mc.model.Usuario;
import mx.mc.service.EstudioService;
import mx.mc.service.TipoEstudioService;
import mx.mc.util.Comunes;
import mx.mc.util.Mensaje;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope(value = "view")
public class EstudioMB implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(EstudioMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private int tipoEstudio;
    private List<TipoEstudio> tipoEstudioList;
    private PermisoUsuario permiso;
    private String cadenaBusqueda;
    private ParamBusquedaReporte paramBusquedaReporte;
    private Estudio estudioSelect;
    private List<Estudio> estudioList;
    private EstudiosLazy estudiosLazy;
    private boolean status;
    private Usuario usuarioSelect;
    private boolean message;
    private List<Estudio> estudioLayout;
    private boolean noProcess;
    
    @Autowired
    private transient EstudioService estudioService;
    
    @Autowired
    private transient TipoEstudioService tipoEstudioService;
    
    @PostConstruct
    public void init() {
        LOGGER.trace("Estudios");
        initialize();        
        permiso = Comunes.obtenerPermisos(Transaccion_Enum.ESTUDIOS.getSufijo());        
        buscarEstudios();
    }
    
    private void initialize() {
        cadenaBusqueda = "";
        status = Constantes.INACTIVO;
        noProcess = Constantes.INACTIVO;
        estudioSelect = new Estudio();
        estudioList = new ArrayList<>();
        paramBusquedaReporte = new ParamBusquedaReporte();
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        usuarioSelect = sesion.getUsuarioSelected();
        obtenTiposEstudio();
    }
    
    public void obtenTiposEstudio() {
        LOGGER.trace("obtenTiposEstudio()");
        try {
            tipoEstudioList = tipoEstudioService.obtenerLista(new TipoEstudio());
        } catch (Exception ex) {
            LOGGER.error("Error al obtener los Tipos de Estudio: {}", ex.getMessage());
        }
    }
    
    public void buscarEstudios() {
        estudioLayout = new ArrayList<>();
        LOGGER.trace("Buscando conincidencias de: {}", cadenaBusqueda);
        try {
            paramBusquedaReporte.setNuevaBusqueda(true);
            paramBusquedaReporte.setCadenaBusqueda(cadenaBusqueda);
            if (paramBusquedaReporte.getCadenaBusqueda().equals("")) {
                paramBusquedaReporte.setCadenaBusqueda(null);
            }
            estudiosLazy = new EstudiosLazy(estudioService, paramBusquedaReporte, tipoEstudio);
            LOGGER.debug("Resultados: {}", estudiosLazy.getTotalReg());

        } catch (Exception e) {
            LOGGER.error("Error al buscar Estudios: {}", e.getMessage());
        }
    }
    
    public void obtenerEstudio(String idEstudio) {
        try {
            if (permiso.isPuedeVer() && !idEstudio.isEmpty()) {
                estudioSelect = estudioService.obtenerPorIdEstudio(idEstudio);
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString("estudio.warn.sinPermTransac"), null);
            }
        } catch (Exception ex) {
            LOGGER.error("Error al obtener Estudio: {}", ex.getMessage());
        }
    }
    
    public void crearEstudio() {
        if (permiso.isPuedeCrear()) {
            estudioSelect = new Estudio();
        } else {
            Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString("estudio.warn.sinpermisosCrear"), null);
        }
    }
    
    private void updateListEstudios(Estudio estudio) {
        for (Estudio stud : estudioList) {
            if (stud.getIdEstudio().equals(estudio.getIdEstudio())) {
                stud.setClaveEstudio(estudio.getClaveEstudio());
                stud.setIdTipoEstudio(estudio.getIdTipoEstudio());
                stud.setDescripcion(estudio.getDescripcion());
                stud.setActivo(estudio.getActivo());
                break;
            }
        }
    }
    
    public void saveEstudio() {
        status = Constantes.INACTIVO;
        try {
            if (estudioSelect != null) {
                if (estudioSelect.getIdEstudio() == null) {
                    if (permiso.isPuedeCrear()) {
                        Estudio found = estudioService.obtenerPorClaveEstudio(estudioSelect.getClaveEstudio());
                        if (found == null) {
                            estudioSelect.setIdEstudio(Comunes.getUUID());
                            estudioSelect.setActivo(1);
                            estudioSelect.setInsertIdUsuario(usuarioSelect.getIdUsuario());
                            estudioSelect.setInsertFecha(new Date());
                            if (estudioService.insertar(estudioSelect)) {
                                estudioList.add(estudioSelect);
                                status = Constantes.ACTIVO;
                            }
                            else {
                                LOGGER.error("No puede Crear Estudio");
                                Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString("estudio.warn.sinPermTransac"), null);
                            }
                        } else {
                            LOGGER.error("Error la clave de Estudio ya existe!! ");
                            Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString("estudio.err.existeEstudio"), null);
                        }
                    } else {
                        LOGGER.error("No puede Crear Estudio");
                        Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString("estudio.warn.sinPermTransac"), null);
                        estudioSelect.setIdEstudio(null);
                    }
                } else {
                    if (permiso.isPuedeEditar()) {
                        Estudio found = estudioService.obtenerPorClaveEstudio(estudioSelect.getClaveEstudio());
                        if (found == null || found.getIdEstudio().equals(estudioSelect.getIdEstudio())) {
                            Estudio unEstudio = estudioService.obtenerPorIdEstudio(estudioSelect.getIdEstudio());
                            unEstudio.setClaveEstudio(estudioSelect.getClaveEstudio());
                            unEstudio.setDescripcion(estudioSelect.getDescripcion());
                            unEstudio.setUpdateFecha(new Date());
                            unEstudio.setUpdateIdUsuario(usuarioSelect.getIdUsuario());
                            if (estudioService.actualizar(unEstudio)) {
                                updateListEstudios(unEstudio);
                                status = Constantes.ACTIVO;
                            }
                            else {
                                LOGGER.error("ERROR al guardar el Estudio");
                                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estudio.err.guardar"), null);
                            }
                        } else {
                            LOGGER.error("Error la clave de Estudio ya existe!! ");
                            Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString("estudio.err.existeEstudio"), null);
                        }
                    } else {
                        LOGGER.error("No puede Editar");
                        Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString("estudio.warn.sinPermTransac"), null);
                    }
                }
                if (status) {
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("estudio.info.datGuardados"), null);
                }
            }
        } catch (Exception ex) {
            LOGGER.error("ERROR al guardar el Estudio", ex);
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estudio.err.guardar"), null);
            if (!status) {
                estudioSelect.setIdEstudio(null);
            }
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }
    
    public void statusEstudio(String idEstudio, int status) {
        try {
            if (permiso.isPuedeEditar()) {
                Estudio stud = new Estudio(); 
                stud.setIdEstudio(idEstudio);
                stud.setUpdateIdUsuario(usuarioSelect.getIdUsuario());
                stud.setUpdateFecha(new Date());
                stud.setActivo(status == 0 ? 1 : 0);
                if (estudioService.actualizar(stud)) {
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, "Se cambió el estatus del Estudio correctamente", null);
                }
                for (Estudio st : estudioList) {
                    if (st.getIdEstudio().equals(idEstudio)) {
                        st.setActivo(stud.getActivo());
                        break;
                    }
                }
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString("estudio.warn.sinPermTransac"), null);
            }
        } catch (Exception ex) {
            LOGGER.error("Error al actualizar estatus del Estudio: {}", ex.getMessage());
        }
    }
    
    private String createFile(byte[] bites, String name) throws IOException, InterruptedException  {
        if (bites != null) {         
            File tempFile = File.createTempFile(name, null);
            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                fos.write(bites);
                fos.flush();
            }
            return tempFile.getAbsolutePath();
        }
        return "";
    }
    
    private String getValue(Cell cell) {
        String resultado = "";
        switch (cell.getCellType()) {
            case BLANK:
                resultado = "";
                break;
            case BOOLEAN:
                resultado = "CELL_TYPE_BOOLEAN";
                break;
            case ERROR:
                resultado = "CELL_TYPE_ERROR";
                break;
            case FORMULA:
                resultado = cell.getStringCellValue();
                break;

            case NUMERIC:
                resultado = cell.getNumericCellValue() + "";
                break;
            case STRING:
                resultado = cell.getStringCellValue();
                break;
            default:
                resultado = "valor desconocido";
        }
        return resultado.trim().replaceAll("\n", "");
    }
    
    private String validaDatosEstudio(Estudio estudio) {
        String motivo = "";
        if (estudio.getClaveEstudio().equals("")) {
            motivo = "La Clave del Estudio es obligatoria";
        } else if (estudio.getDescripcion().equals("")) {
            motivo = "La Descripción del Estudio es obligatoria";
        } else if (estudio.getIdTipoEstudio() == 0) {
            motivo = "El Tipo de Estudio es obligatorio";
        }
        return motivo;
    }
    
    private void readLayout2003(String excelFilePath) {
        int num = 1;
        try (FileInputStream inputStream = new FileInputStream(new File(excelFilePath)); HSSFWorkbook workbook = new HSSFWorkbook(inputStream)) {
            HSSFSheet firstSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = firstSheet.iterator();
            estudioLayout = new ArrayList<>();
            while (iterator.hasNext()) {
                Row nextRow = iterator.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();
                if (num > 1) {
                    Estudio estudio = new Estudio();
                    while (cellIterator.hasNext()) {
                        Cell celli = cellIterator.next();
                        switch (celli.getColumnIndex()) {
                            case 0://ClaveEstudio
                                estudio.setClaveEstudio(getValue(celli));
                                break;
                            case 1: //Descripcion
                                estudio.setDescripcion(getValue(celli));
                                break;
                            case 2: //Tipo de Estudio
                                String descTipoEstudio = getValue(celli);
                                TipoEstudio tipo = tipoEstudioList.stream().filter(stud -> descTipoEstudio.equals(stud.getDescripcion())).findAny().orElse(null);
                                if (tipo != null) {
                                    estudio.setIdTipoEstudio(tipo.getIdTipoEstudio());
                                    estudio.setDescTipoEstudio(tipo.getDescripcion());
                                }
                                break;
                            default:
                        }
                    }
                    estudio.setMotivo(validaDatosEstudio(estudio));
                    if (estudio.getDescTipoEstudio().isEmpty()) {
                        Estudio found = estudioService.obtenerPorClaveEstudio(estudio.getClaveEstudio());
                        if (found == null) {
                            estudio.setIdEstudio(Comunes.getUUID());
                            estudio.setInsertFecha(new Date());
                            estudio.setInsertIdUsuario(usuarioSelect.getIdUsuario());
                            if (!estudioService.insertar(estudio))
                                estudio.setMotivo("Ocurrió un error al agregar el Estudio");
                            else
                                estudio.setMotivo("OK");
                        } else {
                            estudio.setMotivo("La Clave del Estudio ya existe");
                        }
                    }
                    estudioLayout.add(estudio);
                }
                num++;
            }
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString("estudio.err.formatoIncorrecto"), ex);
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estudio.err.formatoIncorrecto"), null);
        }
    }
    
    private void readLayout2007(String excelFilePath) {
        int num = 1;
        try (FileInputStream inputStream = new FileInputStream(new File(excelFilePath)); Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet firstSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = firstSheet.iterator();
            estudioLayout = new ArrayList<>();
            while (iterator.hasNext()) {
                Row nextRow = iterator.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();
                if (num > 1) {
                    Estudio estudio = new Estudio();
                    while (cellIterator.hasNext()) {
                        Cell celli = cellIterator.next();
                        switch (celli.getColumnIndex()) {
                            case 0://ClaveEstudio
                                estudio.setClaveEstudio(getValue(celli));
                                break;
                            case 1: //Descripcion
                                estudio.setDescripcion(getValue(celli));
                                break;
                            case 2: //Tipo de Estudio
                                String descTipoEstudio = getValue(celli);
                                TipoEstudio tipo = tipoEstudioList.stream().filter(stud -> descTipoEstudio.equals(stud.getDescripcion())).findAny().orElse(null);
                                if (tipo != null) {
                                    estudio.setIdTipoEstudio(tipo.getIdTipoEstudio());
                                    estudio.setDescTipoEstudio(tipo.getDescripcion());
                                }
                                break;
                            default:
                        }
                    }
                    estudio.setMotivo(validaDatosEstudio(estudio));
                    if (estudio.getMotivo().isEmpty()) {
                        Estudio found = estudioService.obtenerPorClaveEstudio(estudio.getClaveEstudio());
                        if (found == null) {
                            estudio.setIdEstudio(Comunes.getUUID());
                            estudio.setInsertFecha(new Date());
                            estudio.setInsertIdUsuario(usuarioSelect.getIdUsuario());
                            estudio.setActivo(Constantes.ES_ACTIVO);
                            if (!estudioService.insertar(estudio))
                                estudio.setMotivo("Ocurrió un error al agregar el Estudio");
                            else
                                estudio.setMotivo("OK");
                        } else {
                            estudio.setMotivo("La Clave del Estudio ya existe");
                        }
                    }
                    estudioLayout.add(estudio);
                }
                num++;
            }
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString("estudio.err.formatoIncorrecto"), ex);
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estudio.err.formatoIncorrecto"), null);
        }

    }
    
    public void layoutFileUpload(FileUploadEvent event) {
        try {
            message = Constantes.INACTIVO;
            noProcess = Constantes.INACTIVO;
            UploadedFile uplfile = event.getFile();
            String name = uplfile.getFileName();
            String ext = name.substring(name.lastIndexOf('.'), name.length());
            String excelFilePath = createFile(uplfile.getContents(), name);
            switch (ext) {
                case ".xlsx":
                    readLayout2007(excelFilePath);
                    break;
                case ".xls":
                    readLayout2003(excelFilePath);
                    break;
                default:
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estudio.err.formatoIncorrecto"), null);
                    break;
            }
            if (!estudioLayout.isEmpty()) {
                noProcess = Constantes.ACTIVO;
            }

        } catch (IOException | InterruptedException ex) {
            LOGGER.error("ERROR al obtener la imagen: {}", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estudio.err.formatoIncorrecto"), null);
            noProcess = Constantes.INACTIVO;

        }
    }

    public int getTipoEstudio() {
        return tipoEstudio;
    }

    public void setTipoEstudio(int tipoEstudio) {
        this.tipoEstudio = tipoEstudio;
    }

    public List<TipoEstudio> getTipoEstudioList() {
        return tipoEstudioList;
    }

    public void setTipoEstudioList(List<TipoEstudio> tipoEstudioList) {
        this.tipoEstudioList = tipoEstudioList;
    }

    public String getCadenaBusqueda() {
        return cadenaBusqueda;
    }

    public void setCadenaBusqueda(String cadenaBusqueda) {
        this.cadenaBusqueda = cadenaBusqueda;
    }

    public Estudio getEstudioSelect() {
        return estudioSelect;
    }

    public void setEstudioSelect(Estudio estudioSelect) {
        this.estudioSelect = estudioSelect;
    }

    public List<Estudio> getEstudioList() {
        return estudioList;
    }

    public void setEstudioList(List<Estudio> estudioList) {
        this.estudioList = estudioList;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

    public EstudiosLazy getEstudiosLazy() {
        return estudiosLazy;
    }

    public void setEstudiosLazy(EstudiosLazy estudiosLazy) {
        this.estudiosLazy = estudiosLazy;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isMessage() {
        return message;
    }

    public void setMessage(boolean message) {
        this.message = message;
    }

    public List<Estudio> getEstudioLayout() {
        return estudioLayout;
    }

    public void setEstudioLayout(List<Estudio> estudioLayout) {
        this.estudioLayout = estudioLayout;
    }

    public boolean isNoProcess() {
        return noProcess;
    }

    public void setNoProcess(boolean noProcess) {
        this.noProcess = noProcess;
    }
    
    
}
