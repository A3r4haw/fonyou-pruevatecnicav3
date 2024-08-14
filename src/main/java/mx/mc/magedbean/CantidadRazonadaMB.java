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
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import mx.mc.enums.EstatusGabinete_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.Almacen;
import mx.mc.model.AlmacenControl;
import mx.mc.model.AlmacenControl_Extended;
import mx.mc.model.CantidadRazonada;
import mx.mc.model.CantidadRazonadaExtended;
import mx.mc.model.CatalogoGeneral;
import mx.mc.model.Estructura;
import mx.mc.model.Medicamento;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.Usuario;
import mx.mc.service.AlmacenControlService;
import mx.mc.service.AlmacenService;
import mx.mc.service.CantidadRazonadaService;
import mx.mc.service.CatalogoGeneralService;
import mx.mc.service.EstructuraService;
import mx.mc.service.MedicamentoService;
import mx.mc.util.Comunes;
import mx.mc.util.Mensaje;
import mx.mc.util.UtilPath;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.primefaces.PrimeFaces;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author bbautista
 */
@Controller
@Scope(value = "view")
public class CantidadRazonadaMB implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(CantidadRazonadaMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    private boolean btnNew;
    private boolean activo;
    private int numeroRegistros;
    private int cantPC;
    private int periodoPC;
    private int cantPU;
    private int periodoPU;
    private int activeRow;
    private Pattern regexNumber;
    private Usuario currentUser;
    private String pathDefinition;
    private String namefile;
    private String claveIns;
    private String descripcion;
    private boolean status;
    private PermisoUsuario permiso;
    private Integer idTipoInsumo;
    private boolean restrictiva;
    private String comentarios;
    private Date date = new Date();
    private SimpleDateFormat sdf = new SimpleDateFormat("MMdd");
    private String motivo;
    private boolean noProcess;

    @Autowired
    private transient CatalogoGeneralService catalogoGeneralService;
    private CatalogoGeneral  catalogoGrl;
    private List<CatalogoGeneral> tipoInsumoList;
    
    @Autowired
    private transient MedicamentoService mediService;
    private Medicamento medicamento;
    private List<Medicamento> medicamentoList;
    private List<Medicamento> insumoList;

    @Autowired
    private transient CantidadRazonadaService cantidadRazonadaService;
    private CantidadRazonada cantidadRazonada;
    private List<CantidadRazonadaExtended> cantidadRazonadaList;
    private List<CantidadRazonada> cantidadRazonadaLayoutList;
    
    @PostConstruct
    public void init() {
        initialize();        
        permiso = Comunes.obtenerPermisos(Transaccion_Enum.CANTIDADRAZONADA.getSufijo());
        if (permiso.isPuedeVer()) {
            obtenerInsumoCantidadRazonada();
        }
    }

    /*  METHOD PRIVATE     */
    private void initialize() {

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");

        currentUser = sesion.getUsuarioSelected();
        regexNumber = Constantes.regexNumber;
        medicamento = new Medicamento();
        medicamentoList = new ArrayList<>();
        activeRow = -1;
        btnNew = Constantes.INACTIVO;
        claveIns = "";
        idTipoInsumo=-1;
        tipoInsumos();
    }
    
    private void tipoInsumos() {
        LOGGER.trace("Load Insumos");
        try {
            tipoInsumoList = catalogoGeneralService.obtenerCatalogosPorGrupo(Constantes.TIPO_INSUMO);
            idTipoInsumo = tipoInsumoList.size()>0 ? tipoInsumoList.get(0).getIdCatalogoGeneral():-1;
        } catch (Exception ex) {
            LOGGER.error("Error al obtener los tipos de Insumo: {}", ex.getMessage());
        }
    }                

    private int obtenerNumRowActive(RowEditEvent event) {
        AjaxBehaviorEvent evt = (AjaxBehaviorEvent) event;
        DataTable table = (DataTable) evt.getSource();
        return table.getRowIndex();
    }
    
    private boolean existsMedtoEnLista(String idInsumo) {
        boolean exits = Constantes.INACTIVO;
        int i = 0;
        activeRow = -1;
        for (CantidadRazonada aux : cantidadRazonadaList) {
            if (aux.getIdInsumo().contains(idInsumo)) {
                exits = Constantes.ACTIVO;
                activeRow = i;
                break;
            }
            i++;
        }
        return exits;
    }
    
    private void cleanObjects() {  
        btnNew = Constantes.INACTIVO; 
        activeRow = -1;     
        descripcion = "";
        comentarios ="";
        claveIns="";
        cantPC = 0;
        periodoPC = 0;
        cantPU = 0;
        periodoPU = 0;
        status = Constantes.INACTIVO;
        restrictiva = false;
        medicamento=null;
    }

    private String createFile(byte[] bites, String name) throws IOException, InterruptedException  {
        if (bites != null) {
            String path = UtilPath.getPathDefinida(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/"));
            String fileImgC = sdf.format(date) + name;
            pathDefinition = path + Constantes.PATH_TMP + fileImgC;
            try (FileOutputStream fos = new FileOutputStream(pathDefinition)) {
                fos.write(bites);
                fos.flush();
            }
            Thread.sleep(2000);
            this.setNamefile(Constantes.PATH_IMG + fileImgC);

            return pathDefinition;
        } else {
            this.setNamefile("");
        }
        return "";
    }

    private void readLayout2007(String excelFilePath) {
        boolean exitoLay = Constantes.ACTIVO;
        int num = 0;
        String aux = "";
        DataFormatter formatter = new DataFormatter();
        try (FileInputStream inputStream = new FileInputStream(new File(excelFilePath)); Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet firstSheets = workbook.getSheetAt(0);
            Iterator<Row> iterator = firstSheets.iterator();
            cantidadRazonadaLayoutList = new ArrayList<>();
            CellReference cellreference = new CellReference("C9");
            Row row = firstSheets.getRow(cellreference.getRow());
            Cell cel9 = row.getCell(cellreference.getCol());
            String data = cel9.getStringCellValue();
            if (data.toUpperCase().contains("CANTIDAD")) {
                while (iterator.hasNext()) {
                    try {
                        Row nextRow = iterator.next();
                        Iterator<Cell> cellIterator = nextRow.cellIterator();
                        cleanObjects();
                        if (num > 7) {
                            exitoLay = Constantes.ACTIVO;
                            insumoList = new ArrayList<>();
                            while (cellIterator.hasNext()) {
                                try {
                                    Cell cellAlmC = cellIterator.next();
                                    switch (cellAlmC.getColumnIndex()) {
                                        case 0://clave
                                            claveIns = formatter.formatCellValue(cellAlmC);
                                            break;
                                        case 1: //Nombre
                                            descripcion = cellAlmC.getStringCellValue();
                                            if (descripcion.length() > 299) {
                                                descripcion = descripcion.substring(0, 299);
                                            }
                                            break;
                                        case 2: //cantidad PC
                                            aux = formatter.formatCellValue(cellAlmC);
                                            cantPC = Integer.parseInt((aux));
                                            break;
                                        case 3: //periodo PC
                                            aux = formatter.formatCellValue(cellAlmC);
                                            periodoPC = Integer.parseInt((aux));
                                            break;
                                        case 4: //cantidad PU
                                            aux = formatter.formatCellValue(cellAlmC);
                                            cantPU = Integer.parseInt((aux));
                                            break;
                                        case 5: //periodo PU
                                            aux = formatter.formatCellValue(cellAlmC);
                                            periodoPU = Integer.parseInt((aux));
                                            break;
                                        case 6: // restrictiva                                            
                                            aux = formatter.formatCellValue(cellAlmC);
                                            restrictiva = aux.equals("1") ? Constantes.ACTIVO : Constantes.INACTIVO;
                                            break;
                                        case 7: //Estatus
                                            aux = formatter.formatCellValue(cellAlmC);
                                            status = aux.equals("1") ? Constantes.ACTIVO : Constantes.INACTIVO;
                                            break;
                                        default:                                              
                                            break;
                                    }
                                    if (claveIns.equals("") && cantPC == 0 && periodoPC == 0 && cantPU == 0) {
                                        break;
                                    }
                                } catch (NumberFormatException exc2) {
                                    LOGGER.error("Ocurrio un error al parsear: ", exc2);
                                }
                            }
                            exitoLay = validaDatos();
                            if (exitoLay) {
                                // Se busca por coincidencia de clave
                                insumoList = mediService.obtenerInsumoLikeClave(claveIns);
                                if (insumoList.size() > 1) {
                                    // Se busca por igualdad de clave
                                    Medicamento medicam = mediService.obtenerMedicaByClave(claveIns);
                                    if (medicam != null) {
                                        insumoList = new ArrayList<>();
                                        insumoList.add(medicam);
                                    }
                                }

                                switch (insumoList.size()) {
                                    case 0:
                                        motivo = RESOURCES.getString("almacControl.err.nofoundInsumo");
                                        exitoLay = Constantes.INACTIVO;
                                        break;
                                    case 1:
                                        exitoLay = agregarActualizar();
                                        break;
                                    default:
                                        motivo = RESOURCES.getString("almacControl.err.selectkey");
                                        exitoLay = Constantes.INACTIVO;
                                        break;
                                }
                            }

                            if (!claveIns.equals("") ) {
                                CantidadRazonadaExtended cantRzn = new CantidadRazonadaExtended();
                                cantRzn.setClaveInstitucional(claveIns);
                                cantRzn.setInsumo(descripcion);
                                if (insumoList.size() > 1) {
                                    cantRzn.setInsumosList(insumoList);
                                    cantRzn.setList(Constantes.ACTIVO);
                                } else {
                                    cantRzn.setList(Constantes.INACTIVO);
                                }
                                cantRzn.setCantidadPresentacionComercial(cantPC);
                                cantRzn.setPeriodoPresentacionComercial(periodoPC);
                                cantRzn.setCantidadDosisUnitaria(cantPU);
                                cantRzn.setPeriodoDosisUnitaira(periodoPU);
                                cantRzn.setRestrictiva(restrictiva?1:0);
                                cantRzn.setActivo(activo);
                                cantRzn.setMotivo(motivo);
                                cantRzn.setProcess(exitoLay);

                                cantidadRazonadaLayoutList.add(cantRzn);
                            }
                        }
                        num++;
                    } catch (NumberFormatException excp1) {
                        LOGGER.error("Ocurrio un error:", excp1);
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("medicamento.err.parseoIncorrecto"), null);
                    } catch (Exception exc3) {
                        LOGGER.error(RESOURCES.getString(""), exc3);
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, exc3.getMessage(), null);
                    }
                }
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("almacControl.err.formatoPuntos"), null);
            }
        } catch (Exception exc) {
            LOGGER.error(RESOURCES.getString(""), exc);
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(""), null);
        }

    }
    
    private boolean validaDatos() {
        boolean valid = Constantes.ACTIVO;
         if (claveIns.equals("")) {
            valid = Constantes.INACTIVO;
            motivo = RESOURCES.getString("almacControl.err.validClave");
        }
            if (cantPC < 0) {
                valid = Constantes.INACTIVO;
                motivo = RESOURCES.getString("");
            } else if (periodoPC < 0) {
                valid = Constantes.INACTIVO;
                motivo = RESOURCES.getString("");
            } else if (cantPU < 0) {
                valid = Constantes.INACTIVO;
                motivo = RESOURCES.getString("");
            } else if (periodoPU < 0) {
                valid = Constantes.INACTIVO;
                motivo = RESOURCES.getString("");
            } 
        
        return valid;
    }
    
    private boolean agregarActualizar() {
        boolean pross = Constantes.INACTIVO;
        try {
            CantidadRazonada cantRzn = new CantidadRazonada();
            cantRzn.setClaveInstitucional(insumoList.get(0).getClaveInstitucional());
            cantRzn.setIdInsumo(insumoList.get(0).getIdMedicamento());
            cantRzn.setActivo(status);
            //Verificamos que no se haya agregado antes
            cantRzn = cantidadRazonadaService.obtener(cantRzn);

            cantidadRazonada = new CantidadRazonada();
            cantidadRazonada.setIdInsumo(insumoList.get(0).getIdMedicamento());
            cantidadRazonada.setClaveInstitucional(insumoList.get(0).getClaveInstitucional());
            cantidadRazonada.setCantidadPresentacionComercial(cantPC);
            cantidadRazonada.setPeriodoPresentacionComercial(periodoPC);
            cantidadRazonada.setCantidadDosisUnitaria(cantPU);
            cantidadRazonada.setPeriodoDosisUnitaira(periodoPU);
            cantidadRazonada.setRestrictiva(restrictiva?1:0);
            cantidadRazonada.setActivo(status);
            if (cantRzn == null) {
                cantidadRazonada.setIdcantidadRazonada(Comunes.getUUID());
                cantidadRazonada.setInsertIdUsuario(currentUser.getIdUsuario());
                cantidadRazonada.setInsertFecha(new Date());
                pross = cantidadRazonadaService.insertar(cantidadRazonada);
                motivo = RESOURCES.getString("almacaControl.info.proccessSuccessful");

            } else {
                cantidadRazonada.setIdcantidadRazonada(cantRzn.getIdcantidadRazonada());
                cantidadRazonada.setUpdateIdUsuario(currentUser.getIdUsuario());
                cantidadRazonada.setUpdateFecha(new Date());
                pross = cantidadRazonadaService.actualizar(cantidadRazonada);
                motivo = RESOURCES.getString("almacControl.info.updateSuccessful");
            }
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al guardar:", ex);
            motivo = RESOURCES.getString("almacControl.err.notSuccessful");
            pross = Constantes.INACTIVO;
        }
        return pross;
    }
    /*  METHOD PUBLIC      */  

    public void obtenerInsumoCantidadRazonada(){
        try{
            cantidadRazonadaList = cantidadRazonadaService.obtenerCantidadRazonada(idTipoInsumo);
            numeroRegistros = cantidadRazonadaList.size();
        }catch(Exception ex){
            LOGGER.error("Error al obtener Insumos: {}", ex.getMessage());
        }
    }
    public void newInsumoCantidadRazonada(){
        try{            
            if (permiso.isPuedeCrear() && medicamento!=null){                
                if(!existsMedtoEnLista(medicamento.getIdMedicamento())){                    
                    Medicamento med =  mediService.obtenerPorIdMedicamento(medicamento.getIdMedicamento());
                    if(med != null && ((cantPC>0 && periodoPC>0)|| (cantPU>0 && periodoPU>0))){
                        CantidadRazonadaExtended cantRazonada = new CantidadRazonadaExtended();
                        cantRazonada.setIdcantidadRazonada(Comunes.getUUID());
                        cantRazonada.setInsumo(med.getNombreCorto());
                        cantRazonada.setIdInsumo(med.getIdMedicamento());
                        cantRazonada.setClaveInstitucional(med.getClaveInstitucional());
                        cantRazonada.setCantidadPresentacionComercial(cantPC);
                        cantRazonada.setPeriodoPresentacionComercial(periodoPC);
                        cantRazonada.setCantidadDosisUnitaria(cantPU);
                        cantRazonada.setPeriodoDosisUnitaira(periodoPU);
                        cantRazonada.setRestrictiva(restrictiva ? 1 : 0);
                        cantRazonada.setActivo(Constantes.ACTIVO);
                        cantRazonada.setComentarios(comentarios);

                        cantidadRazonada = new CantidadRazonada();                                
                        cantidadRazonada.setIdcantidadRazonada(cantRazonada.getIdcantidadRazonada());
                        cantidadRazonada.setIdInsumo(cantRazonada.getIdInsumo());
                        cantidadRazonada.setClaveInstitucional(cantRazonada.getClaveInstitucional());
                        cantidadRazonada.setCantidadPresentacionComercial(cantPC);
                        cantidadRazonada.setPeriodoPresentacionComercial(periodoPC);
                        cantidadRazonada.setCantidadDosisUnitaria(cantPU);
                        cantidadRazonada.setPeriodoDosisUnitaira(periodoPU);
                        cantidadRazonada.setRestrictiva(restrictiva ? 1 : 0);
                        cantidadRazonada.setActivo(Constantes.ACTIVO);
                        cantidadRazonada.setComentarios(comentarios);   
                        cantidadRazonada.setInsertFecha(new Date());
                        cantidadRazonada.setInsertIdUsuario(currentUser.getIdUsuario());
                        
                        cantidadRazonadaService.insertar(cantidadRazonada);
                        cantidadRazonadaList.add(cantRazonada);
                        numeroRegistros = cantidadRazonadaList.size();
                    }
                }else{
                    Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString("almacControl.warn.medicamExiste"), null);
                    PrimeFaces.current().executeScript("jQuery('span.ui-icon-pencil').eq(" + activeRow + ").each(function(){jQuery(this).click()});");
                }
            }
            cleanObjects();
        }catch(Exception ex){            
            LOGGER.error("Error al agregar Insumos: {}", ex.getMessage());
        }
    }
    
    public List<Medicamento> autoComplete(String cadena) {
        try {
            medicamentoList = mediService.obtenerAutoCompNombreClave(cadena);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener Medicamentos: {}", ex.getMessage());
        }
        return medicamentoList;
    }

    public void handleSelect(SelectEvent e) {
        medicamento = (Medicamento) e.getObject();
    }

    public void handleUnSelect(UnselectEvent e) {
        medicamento = (Medicamento) e.getObject();
    }

    public void onRowEdit(RowEditEvent event) {
        try {
            if (permiso.isPuedeEditar()) {
                CantidadRazonada cr = (CantidadRazonada) event.getObject();
                Medicamento med = mediService.obtenerMedicamento(cr.getIdInsumo());
                CantidadRazonada ucr = new CantidadRazonada();
                ucr.setIdcantidadRazonada(cr.getIdcantidadRazonada());
                if (med != null) {                    
                    ucr.setCantidadPresentacionComercial(cr.getCantidadPresentacionComercial());
                    ucr.setPeriodoPresentacionComercial(cr.getPeriodoPresentacionComercial());
                    ucr.setCantidadDosisUnitaria(cr.getCantidadDosisUnitaria());                    
                    ucr.setPeriodoDosisUnitaira(cr.getPeriodoDosisUnitaira());
                    ucr.setActivo(cr.isActivo());                        
                    ucr.setUpdateFecha(new Date());
                    ucr.setUpdateIdUsuario(currentUser.getIdUsuario());
                    cleanObjects();
                    Mensaje.showMessage("Info", RESOURCES.getString("almacControl.info.datGuadadosCorrec"), null);                    
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al Actualizar: {}", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("almacControl.err.guardDatos"), null);
        }
    }
    
    public void cambiarStatusCantRzn(String idCantidadRazonada, boolean activo) {
        LOGGER.trace("mx.mc.magedbean.CantidadRazonadaMB.cambiarStatusCantRzn()");
        CantidadRazonada cr = new CantidadRazonada(); 
        String mensajeStat = "";
        try {
            cr.setIdcantidadRazonada(idCantidadRazonada);
            cr.setUpdateFecha(new Date());
            cr.setUpdateIdUsuario(currentUser.getIdUsuario());
            if (activo) {
                cr.setActivo(Constantes.INACTIVO);
                mensajeStat = "ok.inactivar";
            } else {
                cr.setActivo(Constantes.ACTIVO);
                mensajeStat =  "ok.activar";
            }
            boolean stat = cantidadRazonadaService.actualizar(cr);
            if (stat) {
                Mensaje.showMessage("Info", RESOURCES.getString(mensajeStat), null);                
            }else{
                Mensaje.showMessage("Error", RESOURCES.getString("almacControl.err.guardDatos"), null);
            }
            cantidadRazonadaList.stream().filter((it) -> (it.getIdcantidadRazonada().equals(cr.getIdcantidadRazonada()))).forEachOrdered((it) -> {
                it.setActivo(cr.isActivo());
            });
        } catch (Exception e) {
            LOGGER.error("Error al cambiar estatus: {}", e.getMessage());
        }
    }
    public void onRowCancel(RowEditEvent event) {
        int row = obtenerNumRowActive(event);
        if (activeRow == row) {
            cleanObjects();
        }
    }

    public void onRowEditInit(RowEditEvent event) {
        int row = obtenerNumRowActive(event);
        if (permiso.isPuedeEditar()) {
            CantidadRazonada cr = (CantidadRazonada) event.getObject();
            if (activeRow == -1) {
                cantPC = cr.getCantidadPresentacionComercial();
                periodoPC = cr.getPeriodoPresentacionComercial();
                cantPU = cr.getCantidadDosisUnitaria();
                periodoPU = cr.getPeriodoDosisUnitaira();
                activeRow = row;
            } else if (activeRow > -1 && activeRow != row) {
                PrimeFaces.current().executeScript("jQuery('span.ui-icon-close').eq(" + row + ").each(function(){jQuery(this).click()});");
            }
        } else {
            PrimeFaces.current().executeScript("jQuery('span.ui-icon-close').eq(" + row + ").each(function(){jQuery(this).click()});");
        }

    }    

    public void agregarLayout() {
        if (permiso.isPuedeCrear()) {
            insumoList = new ArrayList<>();
            cantidadRazonadaLayoutList= new ArrayList<>();
            activo = Constantes.ACTIVO;
            noProcess = Constantes.INACTIVO;
            cleanObjects();
        } else {
            Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString("medicamento.warn.sinpermisosCrear"), null);
        }
    }

    public void layoutFileUpload(FileUploadEvent event) {
        try {
            UploadedFile upfile = event.getFile();
            String name = upfile.getFileName();
            String ext = name.substring(name.lastIndexOf('.'), name.length());
            String excelFilePath = createFile(upfile.getContents(), name);
            switch (ext) {
                case ".xlsx": readLayout2007(excelFilePath); break;
                default:
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(""), null);
                    break;
            }

            if (!cantidadRazonadaLayoutList.isEmpty()) {
                noProcess = Constantes.ACTIVO;
            }
        } catch (Exception ex) {
            LOGGER.error("ERROR al obtener la imagen: {}", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(""), null);
            noProcess = Constantes.INACTIVO;
        }
    }


// <editor-fold defaultstate="collapsed" desc="GETTER & SETTER" >        

    public List<Medicamento> getMedicamentoList() {
        return medicamentoList;
    }

    public void setMedicamentoList(List<Medicamento> medicamentoList) {
        this.medicamentoList = medicamentoList;
    }

    public List<Medicamento> getInsumoList() {
        return insumoList;
    }

    public void setInsumoList(List<Medicamento> insumoList) {
        this.insumoList = insumoList;
    }

    public List<CantidadRazonadaExtended> getCantidadRazonadaList() {
        return cantidadRazonadaList;
    }

    public void setCantidadRazonadaList(List<CantidadRazonadaExtended> cantidadRazonadaList) {
        this.cantidadRazonadaList = cantidadRazonadaList;
    }

    public List<CantidadRazonada> getCantidadRazonadaLayoutList() {
        return cantidadRazonadaLayoutList;
    }

    public void setCantidadRazonadaLayoutList(List<CantidadRazonada> cantidadRazonadaLayoutList) {
        this.cantidadRazonadaLayoutList = cantidadRazonadaLayoutList;
    }        

    public Pattern getRegexNumber() {
        return regexNumber;
    }

    public void setRegexNumber(Pattern regexNumber) {
        this.regexNumber = regexNumber;
    }   

    public int getActiveRow() {
        return activeRow;
    }

    public void setActiveRow(int activeRow) {
        this.activeRow = activeRow;
    }    

    public int getCantPC() {
        return cantPC;
    }

    public void setCantPC(int cantPC) {
        this.cantPC = cantPC;
    }

    public int getPeriodoPC() {
        return periodoPC;
    }

    public void setPeriodoPC(int periodoPC) {
        this.periodoPC = periodoPC;
    }

    public int getCantPU() {
        return cantPU;
    }

    public void setCantPU(int cantPU) {
        this.cantPU = cantPU;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public boolean isBtnNew() {
        return btnNew;
    }

    public void setBtnNew(boolean btnNew) {
        this.btnNew = btnNew;
    }

    public int getNumeroRegistros() {
        return numeroRegistros;
    }

    public void setNumeroRegistros(int numeroRegistros) {
        this.numeroRegistros = numeroRegistros;
    }

    public MedicamentoService getMediService() {
        return mediService;
    }

    public void setMediService(MedicamentoService mediService) {
        this.mediService = mediService;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    public int getPeriodoPU() {
        return periodoPU;
    }

    public void setPeriodoPU(int periodoPU) {
        this.periodoPU = periodoPU;
    }

    public String getPathDefinition() {
        return pathDefinition;
    }

    public void setPathDefinition(String pathDefinition) {
        this.pathDefinition = pathDefinition;
    }

    public String getNamefile() {
        return namefile;
    }

    public void setNamefile(String namefile) {
        this.namefile = namefile;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }    

    public List<CatalogoGeneral> getTipoInsumoList() {
        return tipoInsumoList;
    }

    public void setTipoInsumoList(List<CatalogoGeneral> tipoInsumoList) {
        this.tipoInsumoList = tipoInsumoList;
    }

    public Integer getIdTipoInsumo() {
        return idTipoInsumo;
    }

    public void setIdTipoInsumo(Integer idTipoInsumo) {
        this.idTipoInsumo = idTipoInsumo;
    }

    public boolean isRestrictiva() {
        return restrictiva;
    }

    public void setRestrictiva(boolean restrictiva) {
        this.restrictiva = restrictiva;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
    
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isNoProcess() {
        return noProcess;
    }

    public void setNoProcess(boolean noProcess) {
        this.noProcess = noProcess;
    }
    
    
    // </editor-fold>
}
