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
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.Cama;
import mx.mc.model.EstatusCama;
import mx.mc.service.CamaService;
import mx.mc.model.Estructura;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.TipoAlmacen;
import mx.mc.model.TipoAreaEstructura;
import mx.mc.model.TipoCama;
import mx.mc.model.Usuario;
import mx.mc.service.EstatusCamaService;
import mx.mc.service.EstructuraService;
import mx.mc.service.TipoAlmacenService;
import mx.mc.service.TipoCamaService;
import mx.mc.service.TipoAreaEstructuraService;
import mx.mc.service.UsuarioService;
import mx.mc.util.Comunes;
import mx.mc.util.Mensaje;
import mx.mc.util.UtilPath;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

 
/**
 *
 * @author bbautista
 */
@Controller
@Scope(value = "view")
public class CargaMasivaMB  implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(MedicamentoMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    private boolean skip;
    private String file;
    private String fileImg;
    private String area;
    private String servicio;
    private String ubicacion;
    private String nombre;    
    private String tipo;
    private String status;
    private String padre;
    private String descripcion;
    private String almacen;
    private String tipoArea;
    private int activa;
    private String cargMasivErrFormatoIncorrecto;
    private PermisoUsuario permiso;
    
    private boolean noProcess;
    private boolean noProcessExtr;
    private String pathDefinition;
    private Date date = new Date();            
    private SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss");
    
    @Autowired
    private transient CamaService camaservice;
    private Cama cama;
    private List<Cama> camaList;
    
    @Autowired
    private transient EstructuraService   estructuraService;
    private Estructura  estructura;
    private Estructura  estructuraPadre;
    private List<Estructura> estructuraList;
    private List<Estructura> listEstructura;
    
    @Autowired
    private transient EstatusCamaService estatusCamaService;
    private EstatusCama estatusCama;
    
    @Autowired
    private transient TipoCamaService tipocamaService;
    private TipoCama    tipoCama;
    
    @Autowired
    private transient TipoAreaEstructuraService  areaEstructuraService;
    private TipoAreaEstructura  tipoEstructura;
    private TipoAreaEstructura  tipoEstructuraAlmacen;
    private List<TipoAreaEstructura>  listTipoEstructura;
    
    @Autowired
    private transient TipoAlmacenService  tipoAlmacenService;
    private TipoAlmacen         tipoAlmacen;
    private List<TipoAlmacen>   listTipoAlmacen;
    
    @Autowired
    private transient UsuarioService usuarioService;
    private Usuario currentUser;        
    
    @PostConstruct
    public void init() {
        cargMasivErrFormatoIncorrecto = "cargMasiv.err.formatoIncorrecto";
        initialize();
        permiso = Comunes.obtenerPermisos(Transaccion_Enum.CARGAMASIVA.getSufijo());
        obtieneAreaEstructura();
        obtenerEstructura();
        obtenerTipoAlmacen();
    } 
    private void initialize(){
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");            
        currentUser = sesion.getUsuarioSelected();
        tipoEstructura= new TipoAreaEstructura();
        tipoEstructuraAlmacen= new TipoAreaEstructura();
        tipoAlmacen = new TipoAlmacen();
        estructura= new Estructura();
        
        cleanObjects();
        noProcess=Constantes.INACTIVO;
        camaList = new ArrayList<>();
        listTipoEstructura= new ArrayList<>();
        listTipoAlmacen= new ArrayList<>();
    }
    
    private void obtieneAreaEstructura(){
        try{
           listTipoEstructura = areaEstructuraService.obtenerLista(tipoEstructura);
        }catch(Exception ex){
            LOGGER.error("ERROR al obtener la lista AreaEstructura: {}", ex.getMessage());
        }
    }
    
    private void obtenerEstructura(){
        try{
            listEstructura=estructuraService.obtenerLista(estructura);
        }catch(Exception ex){
            LOGGER.error("ERROR al obtener la lista Estructura: {}", ex.getMessage());
        }
    }
    
    private void obtenerTipoAlmacen(){
        try{
            listTipoAlmacen=tipoAlmacenService.obtenerLista(tipoAlmacen);
        }catch(Exception ex){
            LOGGER.error("ERROR al obtener la lista TipoAlmacen: {}", ex.getMessage());
        }
    }
    
    private void cleanObjects(){
        estructura = new Estructura();
        estatusCama= new EstatusCama();
        tipoCama = new TipoCama();
        area="";
        servicio="";
        ubicacion="";
        nombre="";
        tipo="";
        status="";
        padre="";  
        descripcion="";
        almacen="";
    }
    
    private String createFile(byte[] bites,String name) throws IOException, InterruptedException {
        if(bites != null){
            String path = UtilPath.getPathDefinida(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/"));                
            fileImg = sdf.format(date) + name;
            pathDefinition = path + Constantes.PATH_TMP +  fileImg;
            try (FileOutputStream fos = new FileOutputStream(pathDefinition)) {
                fos.write(bites);
                fos.flush();
            }
            Thread.sleep(2000);
            this.setFile(Constantes.PATH_IMG+fileImg);
            
            return pathDefinition;
        }else
            this.setFile("");
        return "";
    }
    
    private String getValue(Cell cellCM) {
        switch (cellCM.getCellType()) {
            case BLANK:
                return "";

            case BOOLEAN:
                 return "CELL_TYPE_BOOLEAN";

            case ERROR:
                return "CELL_TYPE_ERROR";

            case FORMULA:
                return cellCM.getStringCellValue();

            case NUMERIC:
                return cellCM.getNumericCellValue()+"";

            case STRING:
                return cellCM.getStringCellValue();

            default:
                return "valor desconocido";

        }
    }
    
    private void readCamas2003(String excelFilePath){
        boolean exito=Constantes.ACTIVO;
        int num=1;
        try (FileInputStream inputStream = new FileInputStream(new File(excelFilePath)); HSSFWorkbook workbook = new HSSFWorkbook(inputStream)) {            
            HSSFSheet firstSheet = workbook.getSheetAt(1);
            Iterator<Row> iteratorC3 = firstSheet.iterator();
            camaList = new ArrayList<>();
            while (iteratorC3.hasNext()) {
                Row nextRow = iteratorC3.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();                    
                cleanObjects();
                if(num>1){
                    exito=Constantes.ACTIVO;
                    boolean salir=false;
                    while (cellIterator.hasNext()) {
                        Cell cellC3 = cellIterator.next();      
                        switch (cellC3.getColumnIndex()) {
                            case 0://Area
                                area = getValue(cellC3);
                                break;
                            case 1: //Nombre Servicio
                                servicio = getValue(cellC3);
                                break;
                            case 2: //Pabellon                                
                                ubicacion = getValue(cellC3);
                                if(!ubicacion.equals(""))
                                    estructura = estructuraService.getEstructuraForName(ubicacion);                                                                        
                                break;
                            case 3: //Nombre
                                nombre = getValue(cellC3);                                    
                                break;                            
                            case 4: //Tipo cama
                                tipo = getValue(cellC3);
                                if(!tipo.equals(""))
                                    tipoCama= tipocamaService.obteerPorNombre(tipo);
                                break;
                            case 5:
                                status = getValue(cellC3);
                                if(!status.equals(""))
                                    estatusCama=estatusCamaService.obtenerPorNombre(status);                                    
                                break;//Estatus
                            default: 
                                if(area.equals("") && servicio.equals("") && ubicacion.equals("") && nombre.equals(""))
                                    salir = true;
                                break;
                        }   
                        if(salir)
                            break;
                    }
                    cama= new Cama();

                    if(tipoCama!=null && !tipo.equals("")){
                        if(estatusCama!=null && !status.equals("")){
                            if(estructura!=null && !ubicacion.equals("")){
                                cama.setIdCama(Comunes.getUUID());
                                cama.setArea(area);
                                cama.setServicio(servicio);
                                cama.setNombreCama(nombre);
                                cama.setUbicacion(ubicacion);
                                cama.setTipoCama(tipo);
                                cama.setIdTipoCama(tipoCama.getIdTipoCama());
                                cama.setIdEstatusCama(estatusCama.getIdEstatusCama());
                                cama.setEstatus(status);
                                cama.setIdEstructura(estructura.getIdEstructura());                            
                                cama.setInsertIdUsuario(currentUser.getIdUsuario());
                                cama.setInsertFecha(new Date());
                            }else{
                                cama.setRazon("La ubicación no existe");
                                exito=Constantes.INACTIVO;
                            }
                        }else{
                            cama.setRazon("La cama no tiene un Estatus");
                            exito=Constantes.INACTIVO;
                        }
                    }else{
                        cama.setRazon("No tiene un tipo de Cama");
                        exito=Constantes.INACTIVO;
                    }

                    if(area == null || area.equals("")){
                        cama.setRazon("El area No existe");
                        exito=Constantes.INACTIVO;
                    }else if(servicio==null || servicio.equals("") || servicio.equals("null")){
                        cama.setRazon("El servicio no existe");
                        exito=Constantes.INACTIVO;
                    }else if(nombre==null || nombre.equals("")){
                        cama.setRazon("La cama debe tener un nombre");
                        exito=Constantes.INACTIVO;
                    }

                    if(exito){
                        Cama cm= new Cama();
                        cm=camaservice.obtenerPorNombre(cama);                            
                        if(cm==null){
                            exito=Constantes.ACTIVO;
                            exito = camaservice.insertar(cama);
                        }else{                            
                            cama.setRazon("La cama ya existe");
                            exito=Constantes.INACTIVO;
                        }
                    }                        

                    if(!exito){
                        cama.setArea(area);
                        cama.setServicio(servicio);
                        cama.setNombreCama(nombre);
                        cama.setUbicacion(ubicacion);
                        cama.setTipoCama(tipo);
                        cama.setEstatus(status);

                        if(!"".equals(area) || !"".equals(servicio) || !"".equals(nombre) || !"".equals(ubicacion) || !"".equals(tipo) || !"".equals(status))
                            camaList.add(cama);                            
                    }
                }    
                num++;                                        
            }
        }catch(Exception ex){
            LOGGER.error("ERROR en readCamas2003: {}", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(cargMasivErrFormatoIncorrecto), null);
        }
    }
    private void readCamas2007(String excelFilePath){
        boolean exito=Constantes.ACTIVO;
        int num=1;
        try (FileInputStream inputStream = new FileInputStream(new File(excelFilePath)); Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet firstSheet = workbook.getSheetAt(1);
            Iterator<Row> iteratorC7 = firstSheet.iterator();
            camaList = new ArrayList<>();
            while (iteratorC7.hasNext()) {
                Row nextRow = iteratorC7.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();                    
                cleanObjects();
                if(num>1){
                    exito=Constantes.ACTIVO;
                    boolean salir =false;
                    while (cellIterator.hasNext()) {
                        Cell cellC7 = cellIterator.next();      
                        switch (cellC7.getColumnIndex()) {
                            case 0://Area
                                area = getValue(cellC7);
                                break;
                            case 1: //Nombre Servicio
                                servicio = getValue(cellC7);
                                break;
                            case 2: //Pabellon                                
                                ubicacion = getValue(cellC7);
                                if(!ubicacion.equals(""))
                                    estructura = estructuraService.getEstructuraForName(ubicacion);                                                                        
                                break;
                            case 3: //Nombre
                                nombre = getValue(cellC7);                                    
                                break;                            
                            case 4: //Tipo cama
                                tipo = getValue(cellC7);
                                if(!tipo.equals(""))
                                    tipoCama= tipocamaService.obteerPorNombre(tipo);
                                break;
                            case 5:
                                status = getValue(cellC7);
                                if(!status.equals(""))
                                    estatusCama=estatusCamaService.obtenerPorNombre(status);                                    
                                break;//Estatus
                            default: 
                                if(area.equals("") && servicio.equals("") && nombre.equals("") && ubicacion.equals(""))
                                    salir=true;
                                break;
                        } 
                        if(salir)
                            break;
                    }
                    Cama camaL03= new Cama();

                    if(tipoCama.getIdTipoCama()>0 && !tipo.equals("")){
                        if(estatusCama.getIdEstatusCama()>0 && !status.equals("")){
                            if(estructura!=null && !ubicacion.equals("")){
                                camaL03.setIdCama(Comunes.getUUID());
                                camaL03.setArea(area);
                                camaL03.setServicio(servicio);
                                camaL03.setNombreCama(nombre);
                                camaL03.setUbicacion(ubicacion);
                                camaL03.setTipoCama(tipo);
                                camaL03.setIdTipoCama(tipoCama.getIdTipoCama());
                                camaL03.setIdEstatusCama(estatusCama.getIdEstatusCama());
                                camaL03.setEstatus(status);
                                camaL03.setIdEstructura(estructura.getIdEstructura());                            
                                camaL03.setInsertIdUsuario(currentUser.getIdUsuario());
                                camaL03.setInsertFecha(new Date());
                            }else{
                                camaL03.setRazon("La ubicación no existe");
                                exito=Constantes.INACTIVO;
                            }
                        }else{
                            camaL03.setRazon("La cama no tiene un Estatus");
                            exito=Constantes.INACTIVO;
                        }
                    }else{
                        camaL03.setRazon("No tiene un tipo de Cama");
                        exito=Constantes.INACTIVO;
                    }

                    if(area == null || area.equals("")){
                        camaL03.setRazon("El area No existe");
                        exito=Constantes.INACTIVO;
                    }else if(servicio==null || servicio.equals("") || servicio.equals("null")){
                        camaL03.setRazon("El servicio no existe");
                        exito=Constantes.INACTIVO;
                    }else if(nombre==null || nombre.equals("")){
                        camaL03.setRazon("La cama debe tener un nombre");
                        exito=Constantes.INACTIVO;
                    }

                    if(exito){
                        Cama cm= new Cama();
                        cm=camaservice.obtenerPorNombre(camaL03);                            
                        if(cm==null){
                            exito=Constantes.ACTIVO;
                            exito = camaservice.insertar(camaL03);
                        }else{                            
                            camaL03.setRazon("La cama ya existe");
                            exito=Constantes.INACTIVO;
                        }
                    }                        

                    if(!exito){
                        camaL03.setArea(area);
                        camaL03.setServicio(servicio);
                        camaL03.setNombreCama(nombre);
                        camaL03.setUbicacion(ubicacion);
                        camaL03.setTipoCama(tipo);
                        camaL03.setEstatus(status);

                        if(!"".equals(area) || !"".equals(servicio) || !"".equals(nombre) || !"".equals(ubicacion) || !"".equals(tipo) || !"".equals(status))
                            camaList.add(camaL03);                            
                    }
                }    
                num++;                                        
            }
        }catch(Exception ex){
            LOGGER.error("ERROR en readCamas2007: {}", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(cargMasivErrFormatoIncorrecto), null);
        }
    }
    
    private void readEstruct2003(String excelFilePath){
        boolean exito=Constantes.ACTIVO;
        int num=1;
        try (FileInputStream inputStream = new FileInputStream(new File(excelFilePath)); HSSFWorkbook workbook = new HSSFWorkbook(inputStream)) {            
            HSSFSheet firstSheet = workbook.getSheetAt(0);
            Iterator<Row> iteratorE3 = firstSheet.iterator();
            estructuraList = new ArrayList<>();
            while (iteratorE3.hasNext()) {
                Row nextRow = iteratorE3.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();                    
                cleanObjects();
                if(num>1){
                    exito=Constantes.ACTIVO;
                    boolean salir =false;
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();                           
                        switch (cell.getColumnIndex()) {
                            case 0://Padre
                                padre = getValue(cell);
                                if(!padre.equals(""))
                                    estructuraPadre = estructuraService.getEstructuraForName(padre);
                                break;
                            case 1: //Nombre Estructura
                                nombre = getValue(cell);
                                break;
                            case 2: //Descripción
                                descripcion = getValue(cell);                                
                                break;
                            case 3: //Tipo Estructura
                                tipo = getValue(cell);
                                if(!tipo.equals(""))
                                    tipoEstructura = areaEstructuraService.obtenerPorNombre(tipo);
                                break;
                            case 4: //Tipo Almacen                                
                                almacen = getValue(cell);
                                if(!almacen.equals(""))
                                    tipoAlmacen= tipoAlmacenService.obtenerPorNombre(almacen);
                                break;
                            case 5: //Area
                                tipoArea= getValue(cell);
                                if(!tipoArea.equals(""))
                                    listTipoEstructura.stream().filter(prdct->prdct.getNombreArea().equals(tipoArea)).forEachOrdered(cnsmr->
                                        tipoEstructuraAlmacen=cnsmr
                                    );                                    
                                break;
                            case 6: //Estatus
                                status = getValue(cell);
                                if(!status.equals(""))
                                    activa = getValue(cell).equals("Activo") ? 1: 0;
                                break;
                            default: 
                                if(padre.equals("") && nombre.equals("") && descripcion.equals(""))
                                    salir=true;
                                break;
                        }
                        if(salir)
                            break;
                    }
                    
                    estructura = new Estructura();
                    if(tipoEstructura!=null && !tipo.equals("")){
                        if(tipoAlmacen!=null && !almacen.equals("")){                            
                            if(estructuraPadre!=null && !padre.equals("")){
                                estructura.setIdEstructura(Comunes.getUUID());
                                estructura.setIdEstructuraPadre(estructuraPadre.getIdEstructura());
                                estructura.setNombre(nombre);
                                estructura.setDescripcion(descripcion);
                                estructura.setActiva(activa);
                                estructura.setIdTipoAreaEstructura(tipoEstructura.getIdTipoAreaEstructura());
                                estructura.setIdTipoAlmacen(tipoAlmacen.getIdTipoAlmacen());
                                estructura.setIdTipoArea(tipoEstructuraAlmacen.getIdTipoAreaEstructura());                                    
                                estructura.setInsertIdUsuario(currentUser.getIdUsuario());
                                estructura.setInsertFecha(new Date());                                
                            }else{
                                estructura.setMotivo("La estructura padre No existe");
                                exito=Constantes.INACTIVO;
                            }
                        }else{
                            estructura.setMotivo("El tipo de Almacen invalido");
                            exito=Constantes.INACTIVO;
                        }
                    }else{
                        estructura.setMotivo("El tipo de Estructura invalido");
                        exito=Constantes.INACTIVO;
                    }

                    if(nombre == null || nombre.equals("")){
                        estructura.setMotivo("El nombre de la estructura es necesario");
                        exito=Constantes.INACTIVO;
                    }

                    if(exito){
                        Estructura estruc= new Estructura();
                        estruc= estructuraService.getEstructuraForName(nombre);
                        if(estruc==null){
                            exito=Constantes.ACTIVO;
                            exito = estructuraService.insertar(estructura);
                        }else{                            
                            estructura.setMotivo("La estructura ya existe");
                            exito=Constantes.INACTIVO;
                        }
                    }                        

                    if(!exito){
                        estructura.setNombrePadre(padre);
                        estructura.setNombre(nombre);
                        estructura.setDescripcion(descripcion);
                        estructura.setTipoAreaEstructura(tipo);
                        estructura.setTipoAlmacen(almacen);
                        estructura.setEstatus(status);
                        
                        if(!"".equals(nombre) || !"".equals(padre) || !"".equals(almacen) || !"".equals(tipo))
                            estructuraList.add(estructura);                            
                    }
                }    
                num++;                                        
            }
        }catch(Exception ex){
            LOGGER.error("ERROR en readEstruct2003: {}", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(cargMasivErrFormatoIncorrecto), null);
        }
    }
    
    private void readEstruct2007(String excelFilePath){
            boolean exito=Constantes.ACTIVO;
        int num=1;
        try (FileInputStream inputStream = new FileInputStream(new File(excelFilePath)); Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet firstSheet = workbook.getSheetAt(0);
            Iterator<Row> iteratorE7 = firstSheet.iterator();
            estructuraList = new ArrayList<>();
            while (iteratorE7.hasNext()) {
                Row nextRow = iteratorE7.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();                    
                cleanObjects();
                if(num>1){
                    exito=Constantes.ACTIVO;
                    boolean salir =false;
                    while (cellIterator.hasNext()) {
                        Cell cellE7 = cellIterator.next();                           
                        switch (cellE7.getColumnIndex()) {
                            case 0://Padre
                                padre = getValue(cellE7);
                                if(!padre.equals(""))
                                    listEstructura.stream().filter(prdct-> prdct.getNombre().equals(padre)).forEachOrdered(cnsmr->
                                        estructuraPadre =cnsmr
                                    );
                                break;
                            case 1: //Nombre Estructura
                                nombre = getValue(cellE7);
                                break;
                            case 2: //Descripción
                                descripcion = getValue(cellE7);                                
                                break;
                            case 3: //Tipo Estructura
                                tipo = getValue(cellE7);
                                if(!tipo.equals(""))                                    
                                    listTipoEstructura.stream().filter(prdct -> prdct.getNombreArea().equals(tipo)).forEachOrdered(cnsmr->
                                        tipoEstructura = cnsmr
                                    );
                                break;
                            case 4: //Tipo Almacen                                
                                almacen = getValue(cellE7);
                                if(!almacen.equals(""))
                                    listTipoAlmacen.stream().filter(prdct->prdct.getNombreTipoAlmacen().equals(almacen)).forEachOrdered(cnsmr->
                                        tipoAlmacen = cnsmr
                                    );
                                break;
                            case 5: //Area
                                tipoArea= getValue(cellE7);
                                if(!tipoArea.equals(""))
                                    listTipoEstructura.stream().filter(prdct->prdct.getNombreArea().equals(tipoArea)).forEachOrdered(cnsmr->
                                        tipoEstructuraAlmacen=cnsmr
                                    );
                                    
                                break;
                            case 6: //Estatus
                                status = getValue(cellE7);
                                if(!status.equals(""))
                                    activa = getValue(cellE7).equals("Activo") ? 1: 0;
                                break;
                            default:
                                if(padre.equals("") && nombre.equals("") && descripcion.equals(""))
                                    salir =true;
                                break;
                                
                        }  
                        if(salir)
                            break;
                    }
                    
                    Estructura estructuraLay07 = new Estructura();
                    if(tipoEstructura!=null && !tipo.equals("")){
                        if(tipoAlmacen!=null && !almacen.equals("")){                            
                            if(estructuraPadre!=null && !padre.equals("")){
                                estructuraLay07.setIdEstructura(Comunes.getUUID());
                                estructuraLay07.setIdEstructuraPadre(estructuraPadre.getIdEstructura());
                                estructuraLay07.setNombre(nombre);
                                estructuraLay07.setDescripcion(descripcion);
                                estructuraLay07.setActiva(activa);
                                estructuraLay07.setIdTipoAreaEstructura(tipoEstructura.getIdTipoAreaEstructura());
                                estructuraLay07.setIdTipoAlmacen(tipoAlmacen.getIdTipoAlmacen());
                                estructuraLay07.setIdTipoArea(tipoEstructuraAlmacen.getIdTipoAreaEstructura());
                                estructuraLay07.setInsertIdUsuario(currentUser.getIdUsuario());
                                estructuraLay07.setInsertFecha(new Date());                                
                            }else{
                                estructuraLay07.setMotivo("La estructura padre No existe");
                                exito=Constantes.INACTIVO;
                            }
                        }else{
                            estructuraLay07.setMotivo("El tipo de Almacen invalido");
                            exito=Constantes.INACTIVO;
                        }
                    }else{
                        estructuraLay07.setMotivo("El tipo de Estructura invalido");
                        exito=Constantes.INACTIVO;
                    }

                    if(nombre == null || nombre.equals("")){
                        estructuraLay07.setMotivo("El nombre de la estructura es necesario");
                        exito=Constantes.INACTIVO;
                    }

                    if(exito){
                        Estructura estruc= new Estructura();
                        estruc= estructuraService.getEstructuraForName(nombre);
                        if(estruc==null){
                            exito=Constantes.ACTIVO;
                            exito = estructuraService.insertar(estructura);
                        }else{                            
                            estructuraLay07.setMotivo("La estructura ya existe");
                            exito=Constantes.INACTIVO;
                        }
                    }                        

                    if(!exito){
                        estructuraLay07.setNombrePadre(padre);
                        estructuraLay07.setNombre(nombre);
                        estructuraLay07.setDescripcion(descripcion);
                        estructuraLay07.setTipoAreaEstructura(tipo);
                        estructuraLay07.setTipoAlmacen(almacen);
                        estructuraLay07.setEstatus(status);
                        
                        if(!"".equals(nombre) || !"".equals(padre) || !"".equals(almacen) || !"".equals(tipo))
                            estructuraList.add(estructuraLay07);                            
                    }
                }    
                num++;                                        
            }
        }catch(Exception ex){
            LOGGER.error("ERROR en readEstruct2007: {}", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(cargMasivErrFormatoIncorrecto), null);
        }
    
    }
    
    /**
     *
     * @param event
     * @return 
     */
    public String onFlowProcess(FlowEvent event) {
        if(skip) {
            skip = false;   //reset in case user goes back
            return "confirm";
        }
        else {
            return event.getNewStep();
        }
    }    
    
    public void estructuraFileUpload(FileUploadEvent event) {    
        try{
            UploadedFile upfile = event.getFile();                        
            String name = upfile.getFileName();
            String ext = name.substring(name.lastIndexOf('.'), name.length());
            String excelFilePath = createFile(upfile.getContents(),name);
            switch(ext){
                case ".xlsx": readEstruct2007(excelFilePath); break;
                case ".xls": readEstruct2003(excelFilePath); break;
                default: Mensaje.showMessage(Constantes.MENSAJE_ERROR,RESOURCES.getString(cargMasivErrFormatoIncorrecto), null); break;
            }            
            if(!estructuraList.isEmpty())
                noProcessExtr=Constantes.ACTIVO;
                        
        }catch(Exception ex){
            LOGGER.error("ERROR en estructuraFileUpload: {}", ex.getMessage());
            noProcessExtr=Constantes.INACTIVO;
        }
    }    
    
    public void camaFileUpload(FileUploadEvent event) {                    
        try{
            UploadedFile upfile = event.getFile();                        
            String name = upfile.getFileName();
            String ext = name.substring(name.lastIndexOf('.'), name.length());
            String excelFilePath = createFile(upfile.getContents(),name);
            switch(ext){
                case ".xlsx": readCamas2007(excelFilePath); break;
                case ".xls": readCamas2003(excelFilePath); break;
                default: Mensaje.showMessage(Constantes.MENSAJE_ERROR,RESOURCES.getString(cargMasivErrFormatoIncorrecto), null); break;
            }            
            if(!camaList.isEmpty())
                noProcess=Constantes.ACTIVO;
                        
        }catch(IOException | InterruptedException ex){
            LOGGER.error("ERROR en camaFileUpload: {}", ex.getMessage());
            noProcess=Constantes.INACTIVO;
            Thread.currentThread().interrupt();
        }
    } 

    public List<Estructura> getEstructuraList() {
        return estructuraList;
    }

    public void setEstructuraList(List<Estructura> estructuraList) {
        this.estructuraList = estructuraList;
    }

    public TipoAreaEstructuraService getAreaEstructuraService() {
        return areaEstructuraService;
    }

    public void setAreaEstructuraService(TipoAreaEstructuraService areaEstructuraService) {
        this.areaEstructuraService = areaEstructuraService;
    }

    public TipoAreaEstructura getTipoEstructura() {
        return tipoEstructura;
    }

    public void setTipoEstructura(TipoAreaEstructura areaEstructura) {
        this.tipoEstructura = areaEstructura;
    }

    public TipoAlmacenService getTipoAlmacenService() {
        return tipoAlmacenService;
    }

    public void setTipoAlmacenService(TipoAlmacenService tipoAlmacenService) {
        this.tipoAlmacenService = tipoAlmacenService;
    }

    public TipoAlmacen getTipoAlmacen() {
        return tipoAlmacen;
    }

    public void setTipoAlmacen(TipoAlmacen tipoAlmacen) {
        this.tipoAlmacen = tipoAlmacen;
    }

    public boolean isNoProcessExtr() {
        return noProcessExtr;
    }

    public void setNoProcessExtr(boolean noProcessExtr) {
        this.noProcessExtr = noProcessExtr;
    }

    public boolean isNoProcess() {
        return noProcess;
    }

    public void setNoProcess(boolean noProcess) {
        this.noProcess = noProcess;
    }

    public List<Cama> getCamaList() {
        return camaList;
    }

    public void setCamaList(List<Cama> camaList) {
        this.camaList = camaList;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPathDefinition() {
        return pathDefinition;
    }

    public void setPathDefinition(String pathDefinition) {
        this.pathDefinition = pathDefinition;
    }   

    public CamaService getCamaservice() {
        return camaservice;
    }

    public void setCamaservice(CamaService camaservice) {
        this.camaservice = camaservice;
    }

    public Cama getCama() {
        return cama;
    }

    public void setCama(Cama cama) {
        this.cama = cama;
    }

    public EstructuraService getEstructuraService() {
        return estructuraService;
    }

    public void setEstructuraService(EstructuraService estructuraService) {
        this.estructuraService = estructuraService;
    }

    public Estructura getEstructura() {
        return estructura;
    }

    public void setEstructura(Estructura estructura) {
        this.estructura = estructura;
    }

    public EstatusCamaService getEstatusCamaService() {
        return estatusCamaService;
    }

    public void setEstatusCamaService(EstatusCamaService estatusCamaService) {
        this.estatusCamaService = estatusCamaService;
    }

    public EstatusCama getEstatusCama() {
        return estatusCama;
    }

    public void setEstatusCama(EstatusCama estatusCama) {
        this.estatusCama = estatusCama;
    }

    public TipoCamaService getTipocamaService() {
        return tipocamaService;
    }

    public void setTipocamaService(TipoCamaService tipocamaService) {
        this.tipocamaService = tipocamaService;
    }

    public TipoCama getTipoCama() {
        return tipoCama;
    }

    public void setTipoCama(TipoCama tipoCama) {
        this.tipoCama = tipoCama;
    }

    public UsuarioService getUsuarioService() {
        return usuarioService;
    }

    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public Usuario getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Usuario currentUser) {
        this.currentUser = currentUser;
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

    public String getFileImg() {
        return fileImg;
    }

    public void setFileImg(String fileImg) {
        this.fileImg = fileImg;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
    
    public boolean isSkip() {
        return skip;
    }
    
    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }
    
}
