/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.magedbean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.lazy.ConfigLazy;
import mx.mc.model.Config;
import mx.mc.model.ConfigExtended;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.Usuario;
import mx.mc.service.ConfigService;
import mx.mc.util.Comunes;
import mx.mc.util.Mensaje;
import mx.mc.util.UtilPath;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author bbautista
 */
@Controller
@Scope(value = "view")
public class ConfiguracionMB {    
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(CantidadRazonadaMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    
    private String cadenaBusqueda;
    private boolean message;
    private boolean noProcess;
    private PermisoUsuario permiso;
    private ConfigLazy configLazy;
    private Pattern regexNumber;
    private Pattern regexBinario;
    private Pattern regexDecimal;
    private Pattern regexIp;
    private Pattern regexMail;
    private Pattern regexNumSing;
    private Pattern regexUrl;
    private Integer number;
    private Boolean bolean;
    private Double decimal;
    private String cadena;
    private String pathDefinition;
    private String fileImg;
    private String nameFile;
    private Date date = new Date();
    private SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss");
    private String nombre;
    private String valor;
    private String descripcion;
    private boolean estatus;
    
    @Autowired
    private ConfigService configService;
    private Config configSelect;
    private Config paramConfig;
    private List<Config> configList;
    private List<ConfigExtended> configListLayout;
    
    private Usuario currentUser;
    
    //Methods Private 
    private void initialize(){
        paramConfig= new Config();
        configSelect= new Config();
        regexNumber = Constantes.regexNumber;
        regexBinario = Constantes.regexBinario;
        regexDecimal = Constantes.regexDecimal;
        regexIp = Constantes.regexIp;
        regexUrl = Constantes.regexUrl;
        regexMail = Constantes.regexMail;
        regexNumSing = Constantes.regexNumSing;
        noProcess = false;
        fileImg="";
    }
    
    public void limpiar(){
        configSelect= new Config();        
        fileImg="";
        pathDefinition="";
        nameFile="";
        noProcess=false;
    } 
    
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
            nameFile=Constantes.PATH_IMG + fileImg;

            return pathDefinition;
        } else {
            nameFile="";
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
                return "valor desconocido";

        }
    }
    
    private boolean validaDatos() {
        boolean cont = Constantes.ACTIVO;

        if (nombre.equals("")) {
                cont = Constantes.INACTIVO;            
        } 
        
        return cont;
    }
    //Methods Publics
    @PostConstruct
    public void init(){
        permiso = Comunes.obtenerPermisos(Transaccion_Enum.CONFIGURACION.getSufijo());
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        currentUser = sesion.getUsuarioSelected();
        initialize();
        obtenerListaConfig();
    }
    
    public void obtenerListaConfig(){
        LOGGER.trace("mx.mc.magedbean.ConfiguracionMB.obtenerListaConfig()");
        try {
            if(cadenaBusqueda!= null)
                paramConfig.setDescripcion(cadenaBusqueda);
            configLazy = new ConfigLazy(configService, paramConfig);
            LOGGER.debug("Resultados: {}", configLazy.getTotalReg());
        } catch (Exception e) {
            LOGGER.error("Error al buscar Config: {}", e.getMessage());
        }
    }
    
    public void readLayout(String excelFilePath) {
        boolean exito = Constantes.ACTIVO;
        int num = 1;
        try (FileInputStream inputStream = new FileInputStream(new File(excelFilePath)); Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet firstSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = firstSheet.iterator();
            configListLayout = new ArrayList<>();
            while (iterator.hasNext()) {
                Row nextRow = iterator.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();
                limpiar();
                if (num > 3) {
                    exito = Constantes.ACTIVO;
                    String motivo="";
                    while (cellIterator.hasNext()) {
                        Cell cell7 = cellIterator.next();
                        switch (cell7.getColumnIndex()) {
                            case 0://nombre                                                                
                                nombre = getValue(cell7);
                                break;
                            case 1: //Valor 
                                valor = cell7.toString();
                                if(valor.contains(".0"))
                                { valor = valor.replace(".0", "");}
                                break;
                            case 2: //descripcion                                
                                descripcion = getValue(cell7);
                                break;
                            case 3: //Estatus
                                estatus = getValue(cell7).toUpperCase().equals("SI");
                                break;
                            default:
                        }
                        if (nombre.equals("") && valor.equals("") && descripcion.equals("")) {
                            break;
                        }
                    }
                    ConfigExtended configLayout = new ConfigExtended();

                    exito = validaDatos();
                    if (exito) {
                        Config config = new Config();
                        configLayout.setNombre(nombre);
                        configLayout.setValor(valor);
                        configLayout.setDescripcion(descripcion);
                        configLayout.setActiva(estatus);

                        config = configService.obtenerByNombre(nombre);
                        if (config != null) {
                            exito = Constantes.ACTIVO;
                            configLayout.setIdConfig(config.getIdConfig());
                            configLayout.setUpdateFecha(new Date());
                            configLayout.setUpdateIdUsuario(currentUser.getIdUsuario());

                            exito = configService.actualizar(configLayout);
                            motivo = exito ? "Se actualizó correctamente.": "No se pudo actualizar el parametro.";
                            
                        } else {
                            motivo = "El parametro NO existe.";
                            exito = Constantes.INACTIVO;
                        }
                    }                    
                    configLayout.setMotivo(motivo);
                    configLayout.setProcess(exito);
                    configListLayout.add(configLayout);                    
                }
                num++;
            }
        } catch (Exception ex) {
            LOGGER.error("ERROR en readLayout2007: {}", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.formatInesperado"), null);
        }
    }
    
    public void layoutFileUpload(FileUploadEvent event) {
        LOGGER.trace("mx.mc.magedbean.ConfiguracionMB.layoutFileUpload()");
        try {
            message = Constantes.INACTIVO;
            UploadedFile upfile = event.getFile();
            String name = upfile.getFileName();
            String ext = name.substring(name.lastIndexOf('.'), name.length());
            String excelFilePath = createFile(upfile.getContents(), name);
            switch (ext) {
                case ".xlsx":
                    readLayout(excelFilePath);
                    break;
                default:
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.formatIncorrecto"), null);
                    break;
            }
            if (!configListLayout.isEmpty()) {
                noProcess = Constantes.ACTIVO;
            }

        } catch (Exception ex) {
            LOGGER.error("ERROR en layoutFileUpload: {}", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.formatIncorrecto"), null);
            noProcess = Constantes.INACTIVO;

        }
    }
    
    public void detalleConfigSelect(){
        try{
            switch(configSelect.getIdTipoParam()){
                case 1: number = Integer.parseInt(configSelect.getValor()); break;
                case 2: decimal = Double.parseDouble(configSelect.getValor()); break;
                case 3: bolean = configSelect.getValor().equals("1"); break;
                case 4: //cadena = configSelect.getValor(); break;
                case 5: //date = new SimpleDateFormat("HH:mm").parse(configSelect.getValor());  break;
                case 6: //date = new SimpleDateFormat("dd/MM/yyyy").parse(configSelect.getValor());  break;
                case 7: 
                case 8: 
                case 9: 
                case 10: cadena = configSelect.getValor(); break;
            }
            
        }catch(Exception ex){
            LOGGER.error("Ocurrio un error al parsear",ex);
        }
    }
    
    public void saveConfig(){
        boolean status=false;
        try{
            if(configSelect!=null){
                switch(configSelect.getIdTipoParam()){
                    case 1: configSelect.setValor(number.toString());break;
                    case 2: configSelect.setValor(decimal.toString()); break;
                    case 3: configSelect.setValor(bolean ? "1":"0"); break;
                    case 4: configSelect.setValor(cadena); break;
                    case 5: SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                        configSelect.setValor(dateFormat.format(date)); break;
                    case 6: SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        configSelect.setValor(formatter.format(date)); break;
                    case 7: 
                    case 8: 
                    case 9: 
                    case 10: configSelect.setValor(cadena); break;
                }
                configSelect.setUpdateFecha(new Date());
                configSelect.setUpdateIdUsuario(currentUser.getIdUsuario());

                status = configService.actualizar(configSelect);
            }
        }catch(Exception ex){
            
            LOGGER.error("Ocurrio un error al guardar",ex);
        }
        PrimeFaces.current().ajax().addCallbackParam("estatus", status);
    }
    
    
    
    //<editor-fold  defaultstate="collapsed" desc="Getters & Setters...">
    
    public String getCadenaBusqueda() {
        return cadenaBusqueda;
    }

    public void setCadenaBusqueda(String cadenaBusqueda) {
        this.cadenaBusqueda = cadenaBusqueda;
    }

    public boolean isMessage() {
        return message;
    }

    public void setMessage(boolean message) {
        this.message = message;
    }

    public ConfigLazy getConfigLazy() {
        return configLazy;
    }

    public void setConfigLazy(ConfigLazy configLazy) {
        this.configLazy = configLazy;
    }

    public List<Config> getConfigList() {
        return configList;
    }

    public void setConfigList(List<Config> configList) {
        this.configList = configList;
    }

    public boolean isNoProcess() {
        return noProcess;
    }

    public void setNoProcess(boolean noProcess) {
        this.noProcess = noProcess;
    }

    public Pattern getRegexNumber() {
        return regexNumber;
    }

    public void setRegexNumber(Pattern regexNumber) {
        this.regexNumber = regexNumber;
    }    

    public Pattern getRegexBinario() {
        return regexBinario;
    }

    public void setRegexBinario(Pattern regexBinario) {
        this.regexBinario = regexBinario;
    }

    public Config getConfigSelect() {
        return configSelect;
    }

    public void setConfigSelect(Config configSelect) {
        this.configSelect = configSelect;
    }

    public List<ConfigExtended> getConfigListLayout() {
        return configListLayout;
    }

    public void setConfigListLayout(List<ConfigExtended> configListLayout) {
        this.configListLayout = configListLayout;
    }

    public Pattern getRegexDecimal() {
        return regexDecimal;
    }

    public void setRegexDecimal(Pattern regexDecimal) {
        this.regexDecimal = regexDecimal;
    }
    public Pattern getRegexIp() {
        return regexIp;
    }

    public void setRegexIp(Pattern regexIp) {
        this.regexIp = regexIp;
    }

    public Pattern getRegexMail() {
        return regexMail;
    }

    public void setRegexMail(Pattern regexMail) {
        this.regexMail = regexMail;
    }

    public Pattern getRegexNumSing() {
        return regexNumSing;
    }

    public void setRegexNumSing(Pattern regexNumSing) {
        this.regexNumSing = regexNumSing;
    }

    public Pattern getRegexUrl() {
        return regexUrl;
    }

    public void setRegexUrl(Pattern regexUrl) {
        this.regexUrl = regexUrl;
    }
    

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Boolean getBolean() {
        return bolean;
    }

    public void setBolean(Boolean bolean) {
        this.bolean = bolean;
    }

    public Double getDecimal() {
        return decimal;
    }

    public void setDecimal(Double decimal) {
        this.decimal = decimal;
    }

    public String getCadena() {
        return cadena;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

    //</editor-fold>
}
