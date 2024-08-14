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

import java.util.List;
import java.util.ResourceBundle;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.servlet.ServletContext;
import mx.mc.lazy.MedicamentosLazy;

import org.primefaces.model.UploadedFile;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import mx.mc.init.Constantes;
import mx.mc.model.CatalogoGeneral;
import mx.mc.model.Usuario;
import mx.mc.model.Medicamento;
import mx.mc.model.VistaMedicamento;
import mx.mc.model.SustanciaActiva;
import mx.mc.model.ViaAdministracion;
import mx.mc.model.UnidadConcentracion;
import mx.mc.model.CategoriaMedicamento;
import mx.mc.model.PresentacionMedicamento;
import mx.mc.model.SubcategoriaMedicamento;
import mx.mc.service.CatalogoGeneralService;
import mx.mc.service.UsuarioService;
import mx.mc.service.MedicamentoService;
import mx.mc.service.SustanciaActivaService;
import mx.mc.service.ViaAdministracionService;
import mx.mc.service.UnidadConcentracionService;
import mx.mc.service.CategoriaMedicamentoService;
import mx.mc.service.PresentacionMedicamentoService;
import mx.mc.service.SubcategoriaMedicamentoService;
import mx.mc.service.AlmacenControlService;
import mx.mc.util.Comunes;
import mx.mc.util.UtilPath;
import mx.mc.enums.EstatusGabinete_Enum;
import mx.mc.enums.Subcategoria_Medicamento;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.model.LocalidadAVG;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.PermisoUsuario;
import mx.mc.service.LocalidadAVGService;
import mx.mc.util.Mensaje;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.PrimeFaces;
import org.primefaces.shaded.commons.io.IOUtils;

/*
 *
 * @author bbautista
 */
@Controller
@Scope(value = "view")
public class MedicamentoMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(MedicamentoMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private boolean statusProcess;
    private boolean activo;
    private String cadenaBusqueda;
    private boolean status;
    private boolean btnNew;
    private boolean rndSave;
    private boolean rndEdit;
    private boolean combo;
    private boolean message;
    private int tipoInsumo;
    private int tipoInsumoL;
    private boolean medi;
    private boolean mate;
    private boolean prot;
    private boolean noProcess;
    private String pathDefinition;
    private String widthModal;
    private String heightModal;
    private String imagen;
    private String namefile;
    private String fileImg;
    private Date date = new Date();
    private SimpleDateFormat sdf = new SimpleDateFormat("MMdd");
    private int numeroRegistros;

    private String claveIns;
    private String susActiva;
    private String viaAdmin;
    private String nombreCorto;
    private String nombreLargo;
    private BigDecimal concentracion;
    private String unidad;
    private String presentacion;
    private String laboratorio;
    private String pEntrada;
    private String factor;
    private String pSalida;
    private String categoria;
    private String subcategoria;
    private String grupo;
    private String indivisible;
    private String cuadroB;
    private String activa;
    private String tInsumo;
    private String motivo;
    private boolean divisible;
    private int cuadro;
    private int activ;
    private String controlado;
    private int controla;
    private Pattern regexMed;
    private Pattern regexGrupo;
    private Pattern regexClave;
    private boolean hospChiconcuac;
    private String conversion;
    private boolean medicamentoControlado;
    private ParamBusquedaReporte paramBusquedaReporte;
    private MedicamentosLazy medicamentosLazy;
    private String ubicacion;
    private Integer refrigeracion;
    private String refrig;
    private boolean estatusRefrigeracion;
    private String claveAlt;
    private String errFormatoIncorrecto;
    private String warnSinPermTransac;
    private PermisoUsuario permiso;
    private Integer mezclaParental;
    private boolean parental;
    private boolean mostrarPropiedades;
    private String claveInstitucional;
            
    @Autowired
    private transient MedicamentoService medicamentoService;
    private Medicamento medicamentoSelect;
    private Medicamento medicamento;
    private VistaMedicamento viewMedicamento;
    private List<VistaMedicamento> medicamentoList;
    private List<VistaMedicamento> medicamentoList2;
    private List<VistaMedicamento> medicamentoLayout;

    @Autowired
    private transient SustanciaActivaService sustanciaService;
    private SustanciaActiva sustanciaSelect;
    private List<SustanciaActiva> sustanciaList;

    @Autowired
    private transient ViaAdministracionService viaAdministracionService;
    private ViaAdministracion viaAdministracionSelect;
    private List<ViaAdministracion> viaAdministracionList;

    @Autowired
    private transient LocalidadAVGService localidadAVGService;
    private List<LocalidadAVG> localidadList;

    @Autowired
    private transient UnidadConcentracionService unidadConcentracionService;
    private UnidadConcentracion unidadConcentracionSelect;
    private List<UnidadConcentracion> unidadConcentracionList;

    @Autowired
    private transient PresentacionMedicamentoService presentacionMedicamentoService;
    private PresentacionMedicamento presentacionMedicamentoSelect;
    private PresentacionMedicamento presentacionMedicamentoSalida;
    private List<PresentacionMedicamento> presentacionMedicamentoList;

    @Autowired
    private transient CategoriaMedicamentoService categoriaMedicamentoService;
    private CategoriaMedicamento categoriaMedicamentoSelect;
    private List<CategoriaMedicamento> categoriaMedicamentoList;

    @Autowired
    private transient SubcategoriaMedicamentoService subcategoriaMedicamentoService;
    private SubcategoriaMedicamento subcategoriaMedicamentoSelect;
    private List<SubcategoriaMedicamento> subcategoriaMedicamentoList;

    @Autowired
    private transient CatalogoGeneralService catalogoGeneralService;
    private transient List<CatalogoGeneral> tipoInsumoList;

    @Autowired
    private transient UsuarioService usuarioService;
    private Usuario usuarioSelect;

    @Autowired
    private transient AlmacenControlService almacenControlService;
    
    @PostConstruct
    public void init() {
        LOGGER.trace("Medicamentos");
        errFormatoIncorrecto = "medicamento.err.formatoIncorrecto";
        warnSinPermTransac = "medicamento.warn.sinPermTransac";       
        initialize();        
        permiso = Comunes.obtenerPermisos(Transaccion_Enum.MEDICAMENTO.getSufijo());        
        if (permiso.isPuedeCrear()) {
            btnNew = Constantes.INACTIVO;
        }
        buscarMedicamento();        
    }

    //==================     Methods Privates    ==================
    private void initialize() {
        this.setCadenaBusqueda("");
        medicamentoSelect = new Medicamento();
        medicamentoList = new ArrayList<>();
        sustanciaSelect = new SustanciaActiva();
        sustanciaList = new ArrayList<>();
        paramBusquedaReporte = new ParamBusquedaReporte();

        viaAdministracionSelect = new ViaAdministracion();
        viaAdministracionList = new ArrayList<>();
        localidadList = new ArrayList<>();

        unidadConcentracionSelect = new UnidadConcentracion();
        unidadConcentracionList = new ArrayList<>();

        presentacionMedicamentoSelect = new PresentacionMedicamento();
        presentacionMedicamentoList = new ArrayList<>();

        categoriaMedicamentoSelect = new CategoriaMedicamento();
        categoriaMedicamentoList = new ArrayList<>();

        subcategoriaMedicamentoSelect = new SubcategoriaMedicamento();
        subcategoriaMedicamentoList = new ArrayList<>();

        tipoInsumoList = new ArrayList<>();

        ubicacion = "";

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        hospChiconcuac = sesion.isHospChiconcuac();
        regexMed = Constantes.regexMed;
        regexGrupo = Constantes.regexUsuario;
        regexClave = Constantes.regexClave;
        usuarioSelect = sesion.getUsuarioSelected();
        tipoInsumo = 0;
        tipoInsumoL = 0;
        numeroRegistros = 0;
        message = Constantes.ACTIVO;
        this.medicamentoControlado = false;
        this.estatusRefrigeracion = false;
        insumos();
        viaAdministracion();
        localidades();
        unidadConcentracion();
        presentacionMedicamento();
        categoriaMedicamento();
        obtieneInsumo();
        conversion = "";
        combo = Constantes.INACTIVO;
        btnNew = Constantes.INACTIVO;
        status = Constantes.INACTIVO;
        noProcess = Constantes.INACTIVO;     
        this.parental = false;
    }

    public void insumos() {
        LOGGER.trace("Load Insumos");
        try {
            tipoInsumoList.addAll(catalogoGeneralService.obtenerCatalogosPorGrupo(Constantes.TIPO_INSUMO));
        } catch (Exception ex) {
            LOGGER.error("Error al obtener Insumos: {}", ex.getMessage());
        }
    }

    private void viaAdministracion() {
        LOGGER.trace("Load ViaAdministración");
        try {
            viaAdministracionList = viaAdministracionService.obtenerTodo();
        } catch (Exception ex) {
            LOGGER.error("Error al obtener datos: {}", ex.getMessage());
        }
    }

    private void localidades() {
        LOGGER.trace("Load localidades");
        try {
            localidadList = localidadAVGService.obtenerTodo();
        } catch (Exception ex) {
            LOGGER.error("Error al obtener datos: {}", ex.getMessage());
        }
    }

    private void unidadConcentracion() {
        LOGGER.trace("Load Unidad Concentracion.");
        try {
            unidadConcentracionList = unidadConcentracionService.obtenerTodo();
        } catch (Exception ex) {
            LOGGER.error("Error al obter Unidad concentracion: {}", ex.getMessage());
        }
    }

    private void presentacionMedicamento() {
        LOGGER.trace("Load PresentacionMedicamento");
        try {
            presentacionMedicamentoList = presentacionMedicamentoService.obtenerTodo();
        } catch (Exception ex) {
            LOGGER.error("Error al obtener PresentacionMedicamento: {}", ex.getMessage());
        }
    }

    private void categoriaMedicamento() {
        LOGGER.trace("Load CategoriaMedicamento.");
        try {
            categoriaMedicamentoList = categoriaMedicamentoService.obtenerTodo();
        } catch (Exception ex) {
            LOGGER.error("Error al obtener CategoriaMedicamento: {}", ex.getMessage());
        }
    }
    
    private void createImagen(byte[] image,String name)throws Exception {
        if(image != null){                    
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            File path = new File(servletContext.getRealPath("/resources/tmp/"));
            fileImg = name;
            pathDefinition = path.getPath() + "/" +fileImg;
                     
            try (FileOutputStream fos = new FileOutputStream(pathDefinition)) {
                fos.write(image);
                fos.flush();
            }
            Thread.sleep(2000);
            this.setImagen(Constantes.PATH_IMG+fileImg);
        }else
            this.setImagen("");
    }

    public boolean deleteImage(){
        boolean result = false;
        if (fileImg != null && !fileImg.isEmpty()) {
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            File path = new File(servletContext.getRealPath("/resources/tmp/"));
            pathDefinition = path.getPath() + "/" +fileImg;
            
            File file = new File(pathDefinition);
            if (file.exists()) {
                result = file.delete();
            } else {
                result = true;
            }
        }
        return result;
    }
    
    private void addNewMedicamento(Medicamento medto) {
        VistaMedicamento viewMed = new VistaMedicamento();
        viewMed.setIdMedicamento(medto.getIdMedicamento());
        viewMed.setClaveInstitucional(medto.getClaveInstitucional());
        for (SustanciaActiva lista : sustanciaList) {
            if (lista.getIdSustanciaActiva().equals(medto.getSustanciaActiva())) {
                viewMed.setSustanciaActiva(lista.getNombreSustanciaActiva());
                break;
            }
        }
        viewMed.setNombreCorto(medto.getNombreCorto());
        viewMed.setNombreLargo(medto.getNombreLargo());
        viewMed.setActivo(medto.getActivo());
        medicamentoList.add(viewMed);
    }

    private void updateListMedicamento(Medicamento medto) {
        for (VistaMedicamento aux : medicamentoList) {
            if (aux.getIdMedicamento().equals(medto.getIdMedicamento())) {
                aux.setClaveInstitucional(medto.getClaveInstitucional());
                for (SustanciaActiva lista : sustanciaList) {
                    if (lista.getIdSustanciaActiva().equals(medto.getSustanciaActiva())) {
                        aux.setSustanciaActiva(lista.getNombreSustanciaActiva());
                        break;
                    }
                }
                aux.setNombreCorto(medto.getNombreCorto());
                aux.setNombreLargo(medto.getNombreLargo());
                aux.setActivo(medto.getActivo());
                break;
            }
        }
    }

    private String createFile(byte[] bites, String name) throws IOException, InterruptedException  {
        if (bites != null) {
            String path = UtilPath.getPathDefinida(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/"));
            fileImg = sdf.format(date) + name;
            pathDefinition = path + Constantes.PATH_TMP + fileImg;
            try (FileOutputStream fous = new FileOutputStream(pathDefinition)) {
                fous.write(bites);
                fous.flush();
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

    private void cleanObjects() {
        claveIns = "";
        susActiva = "";
        viaAdmin = "";
        nombreCorto = "";
        nombreLargo = "";
        concentracion = BigDecimal.ZERO;
        unidad = "";
        presentacion = "";
        laboratorio = "";
        pEntrada = "";
        factor = "";
        pSalida = "";
        categoria = "";
        subcategoria = "";
        grupo = "";
        indivisible = "";
        cuadroB = "";
        activa = "";
        tInsumo = "";
        divisible = Constantes.INACTIVO;
        cuadro = 0;
        activ = 0;
        sustanciaSelect = new SustanciaActiva();
        viaAdministracionSelect = new ViaAdministracion();
        unidadConcentracionSelect = new UnidadConcentracion();
        presentacionMedicamentoSelect = new PresentacionMedicamento();
        presentacionMedicamentoSalida = new PresentacionMedicamento();
        categoriaMedicamentoSelect = new CategoriaMedicamento();
        subcategoriaMedicamentoSelect = new SubcategoriaMedicamento();
        this.medicamentoControlado = false;
        this.parental = false;
    }

    private boolean validaDatosMedicamento() {
        boolean medica = Constantes.ACTIVO;

        if (claveIns.equals("")) {
            medica = Constantes.INACTIVO;
            motivo = "La clave es obligatoria";
        } else if (susActiva.equals("") || sustanciaSelect == null) {
            medica = Constantes.INACTIVO;
            motivo = "La sustancia activa es obligatoria";
        } else if (viaAdmin.equals("")) {
            medica = Constantes.INACTIVO;
            motivo = "La via de Administración es obligatoria";
        } else if (nombreCorto.equals("")) {
            medica = Constantes.INACTIVO;
            motivo = "Es necesario un nombre corto";
        } else if (nombreLargo.equals("")) {
            medica = Constantes.INACTIVO;
            motivo = "Es necesario un nombre largo";
        } else if (concentracion == BigDecimal.ZERO) {
            medica = Constantes.INACTIVO;
            motivo = "Es necesario la concentración";
        } else if (unidad.equals("")) {
            medica = Constantes.INACTIVO;
            motivo = "Es necesario la unidad de Concentración";
        } else if (presentacionMedicamentoSelect == null) {
            medica = Constantes.INACTIVO;
            motivo = "Es necesario la presentación de entrada";
        } else if (factor.equals("")) {
            medica = Constantes.INACTIVO;
            motivo = "Es necesario el factor de transformación";
        } else if (presentacionMedicamentoSalida == null) {
            medica = Constantes.INACTIVO;
            motivo = "Es necesario la presentación de salida";
        } else if (categoria.equals("")) {
            medica = Constantes.INACTIVO;
            motivo = "Es necesario la categoria";
        } else if (subcategoria.equals("")) {
            medica = Constantes.INACTIVO;
            motivo = "Es necesario la subcategoria";
        } else if (activa.equals("")) {
            medica = Constantes.INACTIVO;
            motivo = "Es necesario un estatus";
        } else if (tInsumo.equals("")) {
            medica = Constantes.INACTIVO;
            motivo = "El tipo de insumo es obligatorio";
        }

        if (medica) {
            medicamento = new Medicamento();
            medicamento.setClaveInstitucional(claveIns);
            if (sustanciaSelect != null) {
                medicamento.setSustanciaActiva(sustanciaSelect.getIdSustanciaActiva());
            }
            if (viaAdministracionSelect != null) {
                medicamento.setIdViaAdministracion(viaAdministracionSelect.getIdViaAdministracion());
            }
            medicamento.setNombreCorto(nombreCorto);
            medicamento.setNombreLargo(nombreLargo);
            medicamento.setConcentracion(concentracion);
            if (unidadConcentracionSelect != null) {
                medicamento.setIdUnidadConcentracion(unidadConcentracionSelect.getIdUnidadConcentracion());
            }
            medicamento.setPresentacionLaboratorio(presentacion);
            medicamento.setLaboratorio(laboratorio);
            if (presentacionMedicamentoSelect != null) {
                medicamento.setIdPresentacionEntrada(presentacionMedicamentoSelect.getIdPresentacion());
            }
            medicamento.setFactorTransformacion(Integer.parseInt(factor));
            if (presentacionMedicamentoSalida != null) {
                medicamento.setIdPresentacionSalida(presentacionMedicamentoSalida.getIdPresentacion());
            }
            if (categoriaMedicamentoSelect != null) {
                medicamento.setIdCategoria(categoriaMedicamentoSelect.getIdCategoriaMedicamento());
            }
            if (subcategoriaMedicamentoSelect != null) {
                medicamento.setIdSubcategoria(subcategoriaMedicamentoSelect.getIdSubcategoriaMedicamento());
            }
            medicamento.setGrupo(grupo);
            medicamento.setIndivisible(divisible);
            medicamento.setCuadroBasico(cuadro);
            medicamento.setActivo(activ);
            medicamento.setTipo(tipoInsumoL);
            medicamento.setTipoMedicacion(controla);
            medicamento.setClaveAlterna(claveAlt);
            medicamento.setRefrigeracion(refrigeracion);
            medicamento.setMezclaParental(mezclaParental);
        }

        return medica;
    }

    private boolean validaDatosMaterial() {

        boolean mater = Constantes.ACTIVO;

        if (claveIns.equals("")) {
            mater = Constantes.INACTIVO;
            motivo = "La clave es obligatoria";
        } else if (viaAdmin.equals("")) {
            mater = Constantes.INACTIVO;
            motivo = "La via de Administración es obligatoria";
        } else if (nombreCorto.equals("")) {
            mater = Constantes.INACTIVO;
            motivo = "Es necesario un nombre corto";
        } else if (nombreLargo.equals("")) {
            mater = Constantes.INACTIVO;
            motivo = "Es necesario un nombre largo";
        } else if (concentracion == BigDecimal.ZERO) {
            mater = Constantes.INACTIVO;
            motivo = "Es necesario la concentración";
        } else if (unidad.equals("")) {
            mater = Constantes.INACTIVO;
            motivo = "Es necesario la unidad de Concentración";
        } else if (presentacion.equals("")) {
            mater = Constantes.INACTIVO;
            motivo = "Es necesario la presentación del laboratorio";
        } else if (laboratorio.equals("")) {
            mater = Constantes.INACTIVO;
            motivo = "El laboratorio es obligatorio";
        } else if (pEntrada.equals("")) {
            mater = Constantes.INACTIVO;
            motivo = "Es necesario la presentación de entrada";
        } else if (factor.equals("")) {
            mater = Constantes.INACTIVO;
            motivo = "Es necesario el factor de transformación";
        } else if (pSalida.equals("")) {
            mater = Constantes.INACTIVO;
            motivo = "Es necesario la presentación de salida";
        } else if (categoria.equals("")) {
            mater = Constantes.INACTIVO;
            motivo = "Es necesario la categoria";
        } else if (subcategoria.equals("")) {
            mater = Constantes.INACTIVO;
            motivo = "Es necesario la subcategoria";
        } else if (activa.equals("")) {
            mater = Constantes.INACTIVO;
            motivo = "Es necesario un estatus";
        } else if (tInsumo.equals("")) {
            mater = Constantes.INACTIVO;
            motivo = "El tipo de insumo es obligatorio";
        }

        if (mater) {
            medicamento = new Medicamento();
            medicamento.setIdMedicamento(Comunes.getUUID());
            medicamento.setClaveInstitucional(claveIns);
            medicamento.setIdViaAdministracion(viaAdministracionSelect.getIdViaAdministracion());
            medicamento.setNombreLargo(nombreLargo);
            medicamento.setNombreCorto(nombreCorto);                        
            medicamento.setIdUnidadConcentracion(unidadConcentracionSelect.getIdUnidadConcentracion());
            medicamento.setConcentracion(concentracion);
            medicamento.setPresentacionLaboratorio(presentacion);
            medicamento.setLaboratorio(laboratorio);
            medicamento.setIdPresentacionEntrada(presentacionMedicamentoSelect.getIdPresentacion());
            medicamento.setFactorTransformacion(Integer.parseInt(factor));
            medicamento.setIdPresentacionSalida(presentacionMedicamentoSalida.getIdPresentacion());
            if (sustanciaSelect != null) {
                medicamento.setSustanciaActiva(sustanciaSelect.getIdSustanciaActiva());
            }
            if (categoriaMedicamentoSelect != null) {
                medicamento.setIdCategoria(categoriaMedicamentoSelect.getIdCategoriaMedicamento());
            }
            if (subcategoriaMedicamentoSelect != null) {
                medicamento.setIdSubcategoria(subcategoriaMedicamentoSelect.getIdSubcategoriaMedicamento());
            }
            if (grupo != null) {
                medicamento.setGrupo(grupo);
            }
            medicamento.setIndivisible(divisible);
            medicamento.setCuadroBasico(cuadro);
            medicamento.setActivo(activ);
            medicamento.setTipo(tipoInsumoL);
            medicamento.setInsertFecha(new Date());
            medicamento.setInsertIdUsuario(usuarioSelect.getIdUsuario());
        }
        return mater;
    }

    private boolean validaDatosProtesis() {
        boolean prote = Constantes.ACTIVO;
        if (prote) {
            medicamento = new Medicamento();
            medicamento.setIdMedicamento(Comunes.getUUID());
            medicamento.setClaveInstitucional(claveIns);
            medicamento.setSustanciaActiva(sustanciaSelect.getIdSustanciaActiva());
            medicamento.setIdViaAdministracion(viaAdministracionSelect.getIdViaAdministracion());
            medicamento.setNombreCorto(nombreCorto);
            medicamento.setConcentracion(concentracion);
            medicamento.setNombreLargo(nombreLargo);
            medicamento.setIdUnidadConcentracion(unidadConcentracionSelect.getIdUnidadConcentracion());
            medicamento.setPresentacionLaboratorio(presentacion);            
            medicamento.setIdPresentacionEntrada(presentacionMedicamentoSelect.getIdPresentacion());
            medicamento.setLaboratorio(laboratorio);
            medicamento.setFactorTransformacion(Integer.parseInt(factor));
            medicamento.setIdPresentacionSalida(presentacionMedicamentoSalida.getIdPresentacion());
            medicamento.setIdCategoria(categoriaMedicamentoSelect.getIdCategoriaMedicamento());
            medicamento.setIdSubcategoria(subcategoriaMedicamentoSelect.getIdSubcategoriaMedicamento());
            medicamento.setGrupo(grupo);
            medicamento.setIndivisible(divisible);
            medicamento.setCuadroBasico(cuadro);
            medicamento.setActivo(activ);
            medicamento.setTipo(tipoInsumo);
            medicamento.setInsertFecha(new Date());
            medicamento.setInsertIdUsuario(usuarioSelect.getIdUsuario());
        }

        return prote;
    }

    private void readLayout2003(String excelFilePath) {
        boolean exito = Constantes.ACTIVO;
        int num = 1;
        try (FileInputStream inputStream = new FileInputStream(new File(excelFilePath)); HSSFWorkbook workbook = new HSSFWorkbook(inputStream)) {
            HSSFSheet firstSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = firstSheet.iterator();
            medicamentoLayout = new ArrayList<>();
            while (iterator.hasNext()) {
                Row nextRow = iterator.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();
                cleanObjects();
                if (num > 1) {
                    exito = Constantes.ACTIVO;
                    while (cellIterator.hasNext()) {
                        Cell celli = cellIterator.next();
                        switch (celli.getColumnIndex()) {
                            case 0://Padre
                                claveIns = getValue(celli);

                                //Se genera la clave solo si es chiconcuac, de o contrario sigue su flujo normal
                                if (hospChiconcuac) {
                                    claveIns = generaClave(claveIns);
                                }

                                break;
                            case 1: //Nombre Estructura
                                susActiva = getValue(celli);
                                if (!susActiva.equals("")) {
                                    sustanciaSelect = sustanciaService.obtenerSustanciaPorNombre(susActiva);
                                    if (sustanciaSelect == null) {
                                        SustanciaActiva unaSustanciaAc = new SustanciaActiva();
                                        Integer idSustancia = generarIdSustanciaActiva();
                                        unaSustanciaAc.setIdSustanciaActiva(idSustancia);
                                        unaSustanciaAc.setNombreSustanciaActiva(susActiva);
                                        unaSustanciaAc.setActiva(Constantes.ES_ACTIVO);
                                        unaSustanciaAc.setInsertFecha(new Date());
                                        unaSustanciaAc.setInsertUsuarioId(usuarioSelect.getIdUsuario());
                                        boolean resp = sustanciaService.insertar(unaSustanciaAc);
                                        if (resp) {
                                            sustanciaSelect = unaSustanciaAc;
                                        }
                                    }
                                }
                                break;
                            case 2: //Descripción
                                viaAdmin = getValue(celli);
                                if (!viaAdmin.equals("")) {
                                    viaAdministracionList.stream().filter(via -> (via.getNombreViaAdministracion().toLowerCase().equals(viaAdmin.trim().toLowerCase()))).forEachOrdered(via -> 
                                        viaAdministracionSelect = via
                                    );
                                    if (viaAdministracionSelect == null) {
                                        ViaAdministracion unaViaAdmint = new ViaAdministracion();
                                        Integer idViaAdministracion = generarIdViaAdministracion();
                                        unaViaAdmint.setIdViaAdministracion(idViaAdministracion);
                                        unaViaAdmint.setNombreViaAdministracion(viaAdmin);
                                        unaViaAdmint.setInsertFecha(new Date());
                                        unaViaAdmint.setInsertUsuarioId(usuarioSelect.getIdUsuario());
                                        boolean resp = viaAdministracionService.insertar(unaViaAdmint);
                                        if (resp) {
                                            viaAdministracionSelect = unaViaAdmint;
                                            viaAdministracion();
                                        }
                                    }
                                }
                                break;
                            case 3: //Tipo Estructura
                                nombreCorto = getValue(celli);
                                if (nombreCorto.length() > 299) {
                                    nombreCorto = nombreCorto.substring(0, 299);
                                }
                                break;
                            case 4: //Tipo Almacen                                
                                nombreLargo = getValue(celli);
                                if (nombreLargo.length() > 999) {
                                    nombreLargo = nombreLargo.substring(0, 999);
                                }
                                break;
                            case 5: //Estatus
                                concentracion = new BigDecimal(getValue(celli));
                                break;
                            case 6:
                                unidad = getValue(celli);
                                if (!unidad.equals("")) {
                                    unidadConcentracionList.stream().filter(uni -> (uni.getNombreUnidadConcentracion().toLowerCase().equals(unidad.toLowerCase()))).forEachOrdered(uni -> 
                                        unidadConcentracionSelect = uni
                                    );
                                    if (unidadConcentracionSelect == null) {
                                        UnidadConcentracion unaUnidadConcent = new UnidadConcentracion();
                                        Integer idunidad = generarIdUnidadConcentracion();
                                        unaUnidadConcent.setIdUnidadConcentracion(idunidad);
                                        unaUnidadConcent.setNombreUnidadConcentracion(unidad);
                                        unaUnidadConcent.setInsertFecha(new Date());
                                        unaUnidadConcent.setInsertUsuarioId(usuarioSelect.getIdUsuario());
                                        boolean resp = unidadConcentracionService.insertar(unaUnidadConcent);
                                        if (resp) {
                                            unidadConcentracionSelect = unaUnidadConcent;
                                            unidadConcentracion();
                                        }
                                    }
                                }
                                break;
                            case 7:
                                presentacion = getValue(celli);
                                break;
                            case 8:
                                laboratorio = getValue(celli);
                                break;
                            case 9:
                                pEntrada = getValue(celli);
                                if (!pEntrada.equals("")) {
                                    presentacionMedicamentoList.stream().filter(pre -> (pre.getNombrePresentacion().toLowerCase().equals(pEntrada.toLowerCase()))).forEachOrdered(pre -> 
                                        presentacionMedicamentoSelect = pre
                                    );
                                    if (presentacionMedicamentoSelect == null) {
                                        PresentacionMedicamento unaPresentacionMedEnt = new PresentacionMedicamento();
                                        Integer idPresentacion = generarIdPresenacionMedicamento();
                                        unaPresentacionMedEnt.setIdPresentacion(idPresentacion);
                                        unaPresentacionMedEnt.setNombrePresentacion(pEntrada);
                                        unaPresentacionMedEnt.setActiva(Constantes.ES_ACTIVO);
                                        unaPresentacionMedEnt.setInsertFecha(new Date());
                                        unaPresentacionMedEnt.setInsertUsuarioId(usuarioSelect.getIdUsuario());
                                        boolean resp = presentacionMedicamentoService.insertar(unaPresentacionMedEnt);
                                        if (resp) {
                                            presentacionMedicamentoSelect = unaPresentacionMedEnt;
                                            presentacionMedicamento();
                                        }
                                    }
                                }
                                break;
                            case 10:
                                int aux = 0;
                                String aux0 = getValue(celli);
                                if (aux0.indexOf(".") > 0) {
                                    double dou = Double.parseDouble(aux0);
                                    aux = (int) dou;
                                } else {
                                    aux = Integer.parseInt(aux0);
                                }
                                factor = aux + "";
                                break;
                            case 11:
                                pSalida = getValue(celli);
                                if (!pSalida.equals("")) {
                                    presentacionMedicamentoList.stream().filter(pres -> (pres.getNombrePresentacion().toLowerCase().equals(pSalida.toLowerCase()))).forEachOrdered(pres -> 
                                        presentacionMedicamentoSalida = pres
                                    );
                                    if (presentacionMedicamentoSalida == null) {
                                        PresentacionMedicamento presentacionMedEnt = new PresentacionMedicamento();
                                        Integer idPresentacion = generarIdPresenacionMedicamento();
                                        presentacionMedEnt.setIdPresentacion(idPresentacion);
                                        presentacionMedEnt.setNombrePresentacion(pSalida);
                                        presentacionMedEnt.setActiva(Constantes.ES_ACTIVO);
                                        presentacionMedEnt.setInsertFecha(new Date());
                                        presentacionMedEnt.setInsertUsuarioId(usuarioSelect.getIdUsuario());
                                        boolean resp = presentacionMedicamentoService.insertar(presentacionMedEnt);
                                        if (resp) {
                                            presentacionMedicamentoSalida = presentacionMedEnt;
                                            presentacionMedicamento();
                                        }
                                    }
                                }
                                break;
                            case 12:
                                categoria = getValue(celli);
                                if (!categoria.equals("")) {
                                    categoriaMedicamentoList.stream().filter(cate -> (cate.getNombreCategoriaMedicamento().toLowerCase().equals(categoria.toLowerCase()))).forEachOrdered(cate -> 
                                        categoriaMedicamentoSelect = cate
                                    );
                                }
                                break;
                            case 13:
                                subcategoria = getValue(celli);
                                if (!subcategoria.equals("")) {
                                    subcategoriaMedicamentoSelect = subcategoriaMedicamentoService.obtenerSubPorNombre(categoriaMedicamentoSelect.getIdCategoriaMedicamento(), subcategoria);
                                }
                                break;
                            case 14:
                                grupo = getValue(celli);
                                break;
                            case 15:
                                indivisible = getValue(celli);
                                if (!indivisible.equals("")) {
                                    divisible = indivisible.equals("Si") ? Constantes.ACTIVO : Constantes.INACTIVO;
                                }
                                break;
                            case 16:
                                cuadroB = getValue(celli);
                                if (!cuadroB.equals("")) {
                                    cuadro = cuadroB.equals("Si") ? 1 : 0;
                                }
                                break;
                            case 17:
                                activa = getValue(celli);
                                if (!activa.equals("")) {
                                    activ = activa.equals("Activo") ? 1 : 0;
                                }
                                break;
                            case 18:
                                tInsumo = getValue(celli);
                                if (!tInsumo.equals("")) {
                                    tipoInsumoList.stream().filter(tip -> (tip.getNombreCatalogo().toLowerCase().equals(tInsumo.toLowerCase()))).forEachOrdered(tip -> 
                                        tipoInsumoL = tip.getIdCatalogoGeneral()
                                    );
                                }
                                break;
                            case 19:
                                controlado = getValue(celli);
                                if (!controlado.equals("")) {
                                    controla = controlado.equals("Si") ? 1 : 0;
                                }
                                break;

                            case 20:
                                claveAlt = getValue(celli);
                                break;

                            case 21:
                                refrig = getValue(celli);
                                if (!refrig.equals("")) {
                                    refrigeracion = refrig.equals("Si") ? 1 : 0;
                                }
                                break;
                            default:
                        }
                        if (claveIns.equals("") && susActiva.equals("") && nombreCorto.equals("") && nombreLargo.equals("")) {
                            break;
                        }
                    }
                    String txt = tInsumo.trim().toUpperCase();
                    switch (txt) {
                        case "MEDICAMENTO":
                            exito = validaDatosMedicamento();
                            break;
                        case "MATERIAL":
                            exito = validaDatosMaterial();
                            break;
                        case "PROTESIS":
                            exito = validaDatosProtesis();
                            break;
                        default:
                    }

                    if (exito) {
                        Medicamento medto = new Medicamento();
                        medto = medicamentoService.obtenerMedicamento(medicamento);
                        if (medto == null) {
                            exito = Constantes.ACTIVO;
                            medicamento.setIdMedicamento(Comunes.getUUID());
                            medicamento.setInsertFecha(new Date());
                            medicamento.setInsertIdUsuario(usuarioSelect.getIdUsuario());
                            exito = medicamentoService.insertar(medicamento);
                        } else {
                            motivo = "El Insumo ya existe";
                            exito = Constantes.INACTIVO;
                        }
                    }

                    if (!exito) {
                        viewMedicamento = new VistaMedicamento();
                        viewMedicamento.setClaveInstitucional(claveIns);
                        viewMedicamento.setNombreCorto(nombreCorto);
                        viewMedicamento.setNombreLargo(nombreLargo);
                        viewMedicamento.setGrupo(activa);
                        viewMedicamento.setCategoria(motivo);

                        if (!claveIns.equals("") || !nombreCorto.equals("") || !nombreLargo.equals("")) {
                            medicamentoLayout.add(viewMedicamento);
                        }
                    }
                }
                num++;
            }
        } catch (Exception ex) {
            LOGGER.error("ERROR al obtener la imagen: {}", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errFormatoIncorrecto), null);
        }
    }

    public String generaClave(String claveInsti) {

        if (!claveInsti.contains(".")) {
            claveInsti = claveInsti + ".0";
        }
        String[] par = claveInsti.split("\\.");
        String parte1 = par[0];
        String parte2 = par[1];

        if (parte2.length() == 1) {
            Integer a = Integer.valueOf(parte2);
            if (a != 0) {
                parte2 = parte2 + "0";
            } else {
                parte2 = "00";
            }
        }
        if (parte1.length() <= 9) {
            conversion = String.format("%04d", Integer.parseInt(parte1));
            claveInsti = conversion + "." + parte2;
        } else {
            claveInsti = parte1 + "." + parte2;
        }

        return claveInsti;

    }

    private void readLayout2007(String excelFilePath) {
        boolean exito = Constantes.ACTIVO;
        int num = 1;
        try (FileInputStream inputStream = new FileInputStream(new File(excelFilePath)); Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet firstSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = firstSheet.iterator();
            medicamentoLayout = new ArrayList<>();
            while (iterator.hasNext()) {
                Row nextRow = iterator.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();
                cleanObjects();
                if (num > 1) {
                    exito = Constantes.ACTIVO;
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        switch (cell.getColumnIndex()) {
                            case 0://Padre
                                claveIns = getValue(cell);

                                //Se genera la clave solo si es chiconcuac, de o contrario sigue su flujo normal
                                if (hospChiconcuac) {
                                    claveIns = generaClave(claveIns);
                                }

                                break;
                            case 1: //Nombre Estructura
                                susActiva = getValue(cell);
                                if (!susActiva.equals("")) {
                                    sustanciaSelect = sustanciaService.obtenerSustanciaPorNombre(susActiva);
                                    if (sustanciaSelect == null) {
                                        SustanciaActiva unaSustancia = new SustanciaActiva();
                                        Integer idSustancia = generarIdSustanciaActiva();
                                        unaSustancia.setIdSustanciaActiva(idSustancia);
                                        unaSustancia.setNombreSustanciaActiva(susActiva);
                                        unaSustancia.setActiva(Constantes.ES_ACTIVO);
                                        unaSustancia.setInsertFecha(new Date());
                                        unaSustancia.setInsertUsuarioId(usuarioSelect.getIdUsuario());
                                        boolean resp = sustanciaService.insertar(unaSustancia);
                                        if (resp) {
                                            sustanciaSelect = unaSustancia;
                                        }
                                    }
                                }
                                break;
                            case 2: //Descripción
                                viaAdmin = getValue(cell);
                                if (!viaAdmin.equals("")) {
                                    viaAdministracionList.stream().filter(via -> (via.getNombreViaAdministracion().toLowerCase().equals(viaAdmin.trim().toLowerCase()))).forEachOrdered(via -> 
                                        viaAdministracionSelect = via
                                    );
                                    if (!(viaAdministracionSelect.getNombreViaAdministracion() != null)) {
                                        ViaAdministracion unaViaAdmon = new ViaAdministracion();
                                        Integer idViaAdministracion = generarIdViaAdministracion();
                                        unaViaAdmon.setIdViaAdministracion(idViaAdministracion);
                                        unaViaAdmon.setNombreViaAdministracion(viaAdmin);
                                        unaViaAdmon.setInsertFecha(new Date());
                                        unaViaAdmon.setInsertUsuarioId(usuarioSelect.getIdUsuario());
                                        boolean resp = viaAdministracionService.insertar(unaViaAdmon);
                                        if (resp) {
                                            viaAdministracionSelect = unaViaAdmon;
                                            viaAdministracion();
                                        }
                                    }
                                }
                                break;
                            case 3: //Tipo Estructura
                                nombreCorto = getValue(cell);
                                if (nombreCorto.length() > 299) {
                                    nombreCorto = nombreCorto.substring(0, 299);
                                }
                                break;
                            case 4: //Tipo Almacen                                
                                nombreLargo = getValue(cell);
                                if (nombreLargo.length() > 999) {
                                    nombreLargo = nombreLargo.substring(0, 999);
                                }
                                break;
                            case 5: //Estatus
                                concentracion = new BigDecimal(getValue(cell));
                                break;
                            case 6:
                                unidad = getValue(cell);
                                if (!unidad.equals("")) {
                                    unidadConcentracionList.stream().filter(uni -> (uni.getNombreUnidadConcentracion().toLowerCase().equals(unidad.toLowerCase()))).forEachOrdered(uni -> 
                                        unidadConcentracionSelect = uni
                                    );
                                    if (!(unidadConcentracionSelect.getNombreUnidadConcentracion() != null)) {
                                        UnidadConcentracion unaUnidadCon = new UnidadConcentracion();
                                        Integer idunidad = generarIdUnidadConcentracion();
                                        unaUnidadCon.setIdUnidadConcentracion(idunidad);
                                        unaUnidadCon.setNombreUnidadConcentracion(unidad);
                                        unaUnidadCon.setInsertFecha(new Date());
                                        unaUnidadCon.setInsertUsuarioId(usuarioSelect.getIdUsuario());
                                        boolean resp = unidadConcentracionService.insertar(unaUnidadCon);
                                        if (resp) {
                                            unidadConcentracionSelect = unaUnidadCon;
                                            unidadConcentracion();
                                        }
                                    }
                                }
                                break;
                            case 7:
                                presentacion = getValue(cell);
                                break;
                            case 8:
                                laboratorio = getValue(cell);
                                break;
                            case 9:
                                pEntrada = getValue(cell);
                                if (!pEntrada.equals("")) {
                                    presentacionMedicamentoList.stream().filter(pre -> (pre.getNombrePresentacion().toLowerCase().equals(pEntrada.toLowerCase()))).forEachOrdered(pre -> 
                                        presentacionMedicamentoSelect = pre
                                    );
                                    if (!(presentacionMedicamentoSelect.getNombrePresentacion() != null)) {
                                        PresentacionMedicamento unaPresentacionEnt = new PresentacionMedicamento();
                                        Integer idPresentacion = generarIdPresenacionMedicamento();
                                        unaPresentacionEnt.setIdPresentacion(idPresentacion);
                                        unaPresentacionEnt.setNombrePresentacion(pEntrada);
                                        unaPresentacionEnt.setActiva(Constantes.ES_ACTIVO);
                                        unaPresentacionEnt.setInsertFecha(new Date());
                                        unaPresentacionEnt.setInsertUsuarioId(usuarioSelect.getIdUsuario());
                                        boolean resp = presentacionMedicamentoService.insertar(unaPresentacionEnt);
                                        if (resp) {
                                            presentacionMedicamentoSelect = unaPresentacionEnt;
                                            presentacionMedicamento();
                                        }
                                    }
                                }
                                break;
                            case 10:
                                int aux = 0;
                                String aux0 = getValue(cell);
                                if (aux0.indexOf(".") > 0) {
                                    double dou = Double.parseDouble(aux0);
                                    aux = (int) dou;
                                } else {
                                    aux = Integer.parseInt(aux0);
                                }
                                factor = aux + "";
                                break;
                            case 11:
                                pSalida = getValue(cell);
                                if (!pSalida.equals("")) {
                                    presentacionMedicamentoList.stream().filter(pres -> (pres.getNombrePresentacion().toLowerCase().equals(pSalida.toLowerCase()))).forEachOrdered(pres -> 
                                        presentacionMedicamentoSalida = pres
                                    );
                                    if (!(presentacionMedicamentoSalida.getNombrePresentacion() != null)) {
                                        PresentacionMedicamento unaPresentacionEnt = new PresentacionMedicamento();
                                        Integer idPresentacion = generarIdPresenacionMedicamento();
                                        unaPresentacionEnt.setIdPresentacion(idPresentacion);
                                        unaPresentacionEnt.setNombrePresentacion(pSalida);
                                        unaPresentacionEnt.setActiva(Constantes.ES_ACTIVO);
                                        unaPresentacionEnt.setInsertFecha(new Date());
                                        unaPresentacionEnt.setInsertUsuarioId(usuarioSelect.getIdUsuario());
                                        boolean resp = presentacionMedicamentoService.insertar(unaPresentacionEnt);
                                        if (resp) {
                                            presentacionMedicamentoSalida = unaPresentacionEnt;
                                            presentacionMedicamento();
                                        }
                                    }
                                }
                                break;
                            case 12:
                                categoria = getValue(cell);
                                if (!categoria.equals("")) {
                                    categoriaMedicamentoList.stream().filter(cate -> (cate.getNombreCategoriaMedicamento().toLowerCase().equals(categoria.toLowerCase()))).forEachOrdered(cate -> 
                                        categoriaMedicamentoSelect = cate
                                    );
                                }
                                break;
                            case 13:
                                subcategoria = getValue(cell);
                                if (!subcategoria.equals("")) {
                                    subcategoriaMedicamentoSelect = subcategoriaMedicamentoService.obtenerSubPorNombre(categoriaMedicamentoSelect.getIdCategoriaMedicamento(), subcategoria);
                                }
                                break;
                            case 14:
                                grupo = getValue(cell);
                                break;
                            case 15:
                                indivisible = getValue(cell);
                                if (!indivisible.equals("")) {
                                    divisible = indivisible.equals("Si") ? Constantes.ACTIVO : Constantes.INACTIVO;
                                }
                                break;
                            case 16:
                                cuadroB = getValue(cell);
                                if (!cuadroB.equals("")) {
                                    cuadro = cuadroB.equals("Si") ? 1 : 0;
                                }
                                break;
                            case 17:
                                activa = getValue(cell);
                                if (!activa.equals("")) {
                                    activ = activa.equals("Activo") ? 1 : 0;
                                }
                                break;
                            case 18:
                                tInsumo = getValue(cell);
                                if (!tInsumo.equals("")) {
                                    tipoInsumoList.stream().filter(tip -> (tip.getNombreCatalogo().toLowerCase().equals(tInsumo.toLowerCase()))).forEachOrdered(tip -> 
                                        tipoInsumoL = tip.getIdCatalogoGeneral()
                                    );
                                }
                                break;
                            case 19:
                                controlado = getValue(cell);
                                if (!controlado.equals("")) {
                                    controla = controlado.equals("Si") ? 1 : 0;
                                }
                                break;

                            case 20:
                                claveAlt = getValue(cell);
                                break;

                            case 21:
                                refrig = getValue(cell);
                                if (!refrig.equals("")) {
                                    refrigeracion = refrig.equals("Si") ? 1 : 0;
                                }
                                break;
                            default:
                        }
                        if (claveIns.equals("") && susActiva.equals("") && nombreCorto.equals("") && nombreLargo.equals("")) {
                            break;
                        }
                    }
                    String text = tInsumo.trim().toUpperCase();
                    switch (text) {
                        case "MEDICAMENTO":
                            exito = validaDatosMedicamento();
                            break;
                        case "MATERIAL":
                            exito = validaDatosMaterial();
                            break;
                        case "PROTESIS":
                            exito = validaDatosProtesis();
                            break;
                        default:
                    }

                    if (exito) {
                        String clave = medicamentoService.validarExistencia(medicamento.getClaveInstitucional());
                        if (clave==null) {
                            exito = Constantes.ACTIVO;
                            medicamento.setIdMedicamento(Comunes.getUUID());
                            medicamento.setInsertFecha(new Date());
                            medicamento.setInsertIdUsuario(usuarioSelect.getIdUsuario());
                            exito = medicamentoService.insertar(medicamento);
                        } else {
                            motivo = "El insumo ya existe";
                            exito = Constantes.INACTIVO;
                        }
                    }

                    if (!exito) {
                        viewMedicamento = new VistaMedicamento();
                        viewMedicamento.setClaveInstitucional(claveIns);
                        viewMedicamento.setNombreCorto(nombreCorto);
                        viewMedicamento.setNombreLargo(nombreLargo);
                        viewMedicamento.setGrupo(activa);
                        viewMedicamento.setCategoria(motivo);

                        if (!claveIns.equals("") || !nombreCorto.equals("") || !nombreLargo.equals("")) {
                            medicamentoLayout.add(viewMedicamento);
                        }
                    }
                }
                num++;
            }
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString(errFormatoIncorrecto), ex);
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errFormatoIncorrecto), null);
        }

    }

    private Integer generarIdSustanciaActiva() {
        Integer idSustanciaactiva = 0;
        try {
            idSustanciaactiva = sustanciaService.obtenerSiguienteId();

        } catch (Exception ex) {
            LOGGER.error("Error al obtener idSustanciaActiva consecutivo: {}", ex.getMessage());
        }
        return idSustanciaactiva;
    }

    private Integer generarIdViaAdministracion() {
        Integer idViaAdmon = 0;
        try {
            idViaAdmon = viaAdministracionService.obtenerSiguienteId();
        } catch (Exception ex) {
            LOGGER.error("Error al obtener idViaAdministración consecutivo: {}", ex.getMessage());
        }
        return idViaAdmon;
    }

    private Integer generarIdUnidadConcentracion() {
        Integer idUnidadConc = 0;
        try {
            idUnidadConc = unidadConcentracionService.obtenerSiguienteId();
        } catch (Exception ex) {
            LOGGER.error("Error al obtener idUnidadConcentración consecutivo: {}", ex.getMessage());
        }
        return idUnidadConc;
    }

    private Integer generarIdPresenacionMedicamento() {
        Integer idPresentacion = 0;
        try {
            idPresentacion = presentacionMedicamentoService.obtenerSiguienteId();
        } catch (Exception ex) {
            LOGGER.error("Error al obtener idPresentacion consecutivo: {}", ex.getMessage());
        }
        return idPresentacion;
    }
//==================     Methods Publics    ==================

    public void crearMedicamento() {
        if (permiso.isPuedeCrear()) {
            medicamentoSelect = new Medicamento();
            sustanciaSelect = new SustanciaActiva();
            medicamentoLayout = new ArrayList<>();
            medicamentoSelect.setConcentracion(new BigDecimal(0.00));
            this.setImagen("");
            rndEdit = Constantes.ACTIVO;
            rndSave = Constantes.INACTIVO;
            activo = Constantes.ACTIVO;
            noProcess = Constantes.INACTIVO;
            medicamentoControlado = false;
            estatusRefrigeracion = false;
            this.mostrarPropiedades = (this.tipoInsumo ==38);
            parental = false;
        } else {
            Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString("medicamento.warn.sinpermisosCrear"), null);
        }
    }

    public void editMedicamento() {
        if (permiso.isPuedeEditar()) {
            activo = Constantes.ACTIVO;
            combo = Constantes.ACTIVO;
            rndSave = Constantes.INACTIVO;
            rndEdit = Constantes.ACTIVO;
            this.mostrarPropiedades = (this.tipoInsumo ==38);
        } else {
            Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString("medicamento.warn.sinpermisosEdit"), null);
        }
    }     
    
    public void validaEnTiempoClave() {                
        try {
            if(!claveInstitucional.equalsIgnoreCase(medicamentoSelect.getClaveInstitucional())) {
            Medicamento medicamentoEncontrado = medicamentoService.obtenerMedicaByClave(medicamentoSelect.getClaveInstitucional());
            if(medicamentoEncontrado != null){
                Mensaje.showMessage(Constantes.MENSAJE_WARN, "La clave: "+ medicamentoEncontrado.getClaveInstitucional() + " ya existe", null);
            }
            }
           
        } catch (Exception e) {
            LOGGER.error("Error al obtener validaEnTiempoClave: {}", e.getMessage());
        }
        
    }
    

    public void subcategoriaMedicamento() {
        LOGGER.trace("Load SubcategoriaMedicamento.");
        combo = false;
        try {
            if (medicamentoSelect.getIdCategoria() != null) {
                subcategoriaMedicamentoSelect.setIdCategoriaMedicamento(medicamentoSelect.getIdCategoria());
                subcategoriaMedicamentoList = subcategoriaMedicamentoService.obtenerLista(subcategoriaMedicamentoSelect);
                if (!subcategoriaMedicamentoList.isEmpty()) {
                    combo = true;
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al obtener SubcategoriaMedicamento: {}", ex.getMessage());
        }
    }

    public void buscarMedicamento() {
        LOGGER.trace("Buscando conincidencias de: {}", cadenaBusqueda);
        try {
            paramBusquedaReporte.setNuevaBusqueda(true);
            paramBusquedaReporte.setCadenaBusqueda(cadenaBusqueda);
            if (paramBusquedaReporte.getCadenaBusqueda().equals("")) {
                paramBusquedaReporte.setCadenaBusqueda(null);
            }

            medicamentosLazy = new MedicamentosLazy(medicamentoService, paramBusquedaReporte, tipoInsumo
                    //, ordenAscClave
            );

            LOGGER.debug("Resultados: {}", medicamentosLazy.getTotalReg());

        } catch (Exception e) {
            LOGGER.error("Error al buscar Medicamentos: {}", e.getMessage());
        }
    }

    public void obtenerMedicamento(String idMedicamento) {
        try {
            this.claveInstitucional = "";
            if (permiso.isPuedeVer() && !idMedicamento.isEmpty()) {
                rndEdit = Constantes.ACTIVO;
                rndSave = Constantes.ACTIVO;
                //Si no esta vacio eliminamos la imagen preview
                if (fileImg != null && !fileImg.isEmpty()) {
                    deleteImage();
                }

                this.setMedicamentoSelect(medicamentoService.obtenerPorIdMedicamento(idMedicamento));
                this.mostrarPropiedades = this.medicamentoSelect.getTipo() == Constantes.MEDI;

                fileImg = medicamentoSelect.getNameImage();
                if (fileImg != null && !fileImg.isEmpty()) {
                    //Se elimina antes de volverlo a crear
                    deleteImage();
                    createImagen(medicamentoSelect.getImagenPresentacion(), medicamentoSelect.getNameImage());
                } else {
                    imagen = null;
                }
                if (permiso.isPuedeEditar()) {
                    activo = Constantes.INACTIVO;
                    combo = Constantes.INACTIVO;
                    rndEdit = Constantes.INACTIVO;
                    rndSave = Constantes.ACTIVO;
                }
                
                if (this.medicamentoSelect.getTipo() == Constantes.MEDI) {
                    if (medicamentoSelect != null) {
                        medicamento = medicamentoSelect;
                        if (medicamentoSelect.getTipoMedicacion() == 1) {
                            this.medicamentoControlado = true;
                        } else {
                            this.medicamentoControlado = false;
                        }
                        if (medicamentoSelect.getRefrigeracion() == 1) {
                            this.estatusRefrigeracion = true;
                        } else {
                            this.estatusRefrigeracion = false;
                        }
                        if (medicamentoSelect.getMezclaParental() == 1) {
                            this.parental = true;
                        } else {
                            this.parental = false;
                        }
                        subcategoriaMedicamento();
                        if (medicamentoSelect.getSustanciaActiva() != null) {
                            sustanciaActivaPorId(medicamentoSelect.getSustanciaActiva());
                        }
                    }
                }
                this.claveInstitucional = medicamentoSelect.getClaveInstitucional();
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString(warnSinPermTransac), null);
            }
        } catch (Exception ex) {
            LOGGER.error("Error al obtener medicamento: {}", ex.getMessage());
        }
    }

    public void obtenerLocalidad(String idMedicamento) {
        try {
            if (permiso.isPuedeProcesar() && !idMedicamento.isEmpty()) {
                rndEdit = Constantes.ACTIVO;
                rndSave = Constantes.ACTIVO;
                this.setMedicamentoSelect(medicamentoService.obtenerMedicaLocalidad(idMedicamento));
                if (medicamentoSelect != null) {
                    medicamento = medicamentoSelect;
                }
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString(warnSinPermTransac), null);
            }
        } catch (Exception ex) {
            LOGGER.error("Error al obtener medicamento: {}", ex.getMessage());
        }
    }

    public void asignarLocalidad() {
        status = Constantes.INACTIVO;
        try {
            if (medicamentoSelect != null && medicamentoSelect.getIdMedicamento() != null) {
                if (permiso.isPuedeCrear()) {
                    Date fecha = new Date();
                    LocalidadAVG localidad = new LocalidadAVG();
                    localidad.setIdLocalidadAVG(medicamento.getIdLocalidadAVG());
                    localidad.setEstatus(1);
                    localidad.setUpdateFecha(fecha);
                    localidad.setUpdateIdUsuario(usuarioSelect.getIdUsuario());
                    boolean result = localidadAVGService.asignarLocalidadAVG(medicamento, localidad);
                    if (result) {
                        status = Constantes.ACTIVO;
                        Mensaje.showMessage(Constantes.MENSAJE_INFO, "Se asigno correctamente la localidad del medicamento!!", null);
                    }
                } else {
                    LOGGER.error("No puede Crear");
                    Mensaje.showMessage(Constantes.MENSAJE_WARN, "Ocurrio un error al intentar asignar la localidad", null);
                    medicamentoSelect.setIdMedicamento(null);
                }
            }
        } catch (Exception ex) {
            LOGGER.error("ERROR al asignar Medicamento a una localidad AVG", ex);
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("medicamento.err.guardar"), null);
            if (!status) {
                medicamentoSelect.setIdMedicamento(null);
            }
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public void saveMedicamento() {
        status = Constantes.INACTIVO;
        boolean res;
        try {
            if (tipoInsumo ==Constantes.MATC || tipoInsumo ==Constantes.PROT) {
                sustanciaSelect = new SustanciaActiva();
                sustanciaSelect.setIdSustanciaActiva(Constantes.SUSTANCIA_ACTIVA_NO_DEFINIDA);                
            }else{
                if (this.sustanciaSelect == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("medicamento.err.sustActiva"), null);
                    return;
                }
            }            
            if (medicamentoSelect != null) {
                if (medicamentoSelect.getIdMedicamento() == null) {
                    if (permiso.isPuedeCrear()) {
                        String clave = medicamentoService.validarExistencia(medicamentoSelect.getClaveInstitucional());
                        if (clave == null) {
                            medicamentoSelect.setIdMedicamento(Comunes.getUUID());
                            medicamentoSelect.setSustanciaActiva(sustanciaSelect.getIdSustanciaActiva());
                            medicamentoSelect.setTipo(tipoInsumo);
                            medicamentoSelect.setActivo(1);
                            medicamentoSelect.setInsertIdUsuario(usuarioSelect.getIdUsuario());
                            medicamentoSelect.setInsertFecha(new Date());
                            if(medicamentoSelect.getTipo().equals(Constantes.MATC) || medicamentoSelect.getTipo().equals(Constantes.PROT)){
                                medicamentoSelect.setIdCategoria(medicamentoSelect.getCuadroBasico());
                                medicamentoSelect.setIdSubcategoria(Subcategoria_Medicamento.NO_DEFINIDO.getValue());
                                medicamentoSelect.setIdViaAdministracion(Constantes.NO_DEFINIDA);
                                medicamentoSelect.setIdUnidadConcentracion(Constantes.UNIDADES);
                            }                            
                            medicamentoSelect.setUbicacion(ubicacion);
                            if (this.medicamentoControlado) {
                                medicamentoSelect.setTipoMedicacion(Constantes.ESTATUS_ACTIVO);
                            } else {
                                medicamentoSelect.setTipoMedicacion(Constantes.ESTATUS_INACTIVO);
                            }
                            if (this.estatusRefrigeracion) {
                                medicamentoSelect.setRefrigeracion(Constantes.ESTATUS_ACTIVO);
                            } else {
                                medicamentoSelect.setRefrigeracion(Constantes.ESTATUS_INACTIVO);
                            }
                            if (this.parental) {
                                medicamentoSelect.setMezclaParental(Constantes.ESTATUS_ACTIVO);
                            } else {
                                medicamentoSelect.setMezclaParental(Constantes.ESTATUS_INACTIVO);
                            }
                            res = medicamentoService.insertar(medicamentoSelect);
                            if (res) {
                                addNewMedicamento(medicamentoSelect);
                                status = Constantes.ACTIVO;
                            }
                        } else {
                            LOGGER.error("Error la clave de Insumo ya existe!! ");
                            Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString("medicamento.err.existeMedicamento"), null);
                        }
                    } else {
                        LOGGER.error("No puede Crear");
                        Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString(warnSinPermTransac), null);
                        medicamentoSelect.setIdMedicamento(null);
                    }
                } else {
                    if (permiso.isPuedeEditar()) {
                        Medicamento unMedicamento = medicamentoService.obtenerMedicamento(medicamentoSelect.getIdMedicamento());
                        String clave = medicamentoService.validarExistencia(medicamentoSelect.getClaveInstitucional());
                        boolean actualiza = false;
                        if(clave != null) {
                            if(unMedicamento.getClaveInstitucional().equals(clave)) {
                                actualiza = true;
                            } else {
                                actualiza = false;
                            }
                        } else {
                            actualiza = true;
                        }
                        if (actualiza) {
                            medicamentoSelect.setOsmolaridad((medicamentoSelect.getOsmolaridad() != null) ? medicamentoSelect.getOsmolaridad() : 0.0);
                            medicamentoSelect.setDensidad((medicamentoSelect.getDensidad() != null) ? medicamentoSelect.getDensidad() : 0.0);
                            medicamentoSelect.setNoHorasEstabilidad((medicamentoSelect.getNoHorasEstabilidad() != null) ? medicamentoSelect.getNoHorasEstabilidad() : 0);
                            medicamentoSelect.setCalorias((medicamentoSelect.getCalorias() != null) ? medicamentoSelect.getCalorias() : 0);
                            medicamentoSelect.setSustanciaActiva(sustanciaSelect.getIdSustanciaActiva());
                            medicamentoSelect.setTipo(tipoInsumo);
                            medicamentoSelect.setUpdateFecha(new Date());
                            medicamentoSelect.setUpdateIdUsuario(usuarioSelect.getIdUsuario());
                            if (this.medicamentoControlado) {
                                medicamentoSelect.setTipoMedicacion(Constantes.ESTATUS_ACTIVO);
                            } else {
                                medicamentoSelect.setTipoMedicacion(Constantes.ESTATUS_INACTIVO);
                            }
                            if (this.estatusRefrigeracion) {
                                medicamentoSelect.setRefrigeracion(Constantes.ESTATUS_ACTIVO);
                            } else {
                                medicamentoSelect.setRefrigeracion(Constantes.ESTATUS_INACTIVO);
                            }
                            if (this.parental) {
                                medicamentoSelect.setMezclaParental(Constantes.ESTATUS_ACTIVO);
                            } else {
                                medicamentoSelect.setMezclaParental(Constantes.ESTATUS_INACTIVO);
                            }
                            if (medicamentoSelect.isAutorizar()) {
                                medicamentoSelect.setAutorizar(Constantes.ACTIVO);
                            } else {
                                medicamentoSelect.setAutorizar(Constantes.INACTIVO);
                            }
                            res = medicamentoService.actualizar(medicamentoSelect);
                            if (res) {
                                almacenControlService.actualizaEstatusGabinete(medicamentoSelect.getIdMedicamento(), EstatusGabinete_Enum.ACTUALIZAR.getValue());
                                updateListMedicamento(medicamentoSelect);
                                status = Constantes.ACTIVO;
                            }
                        } else {
                            LOGGER.error("Error la clave de medicamento ya existe!! ");
                            Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString("medicamento.err.existeMedicamento"), null);
                        }
                    } else {
                        LOGGER.error("No puede Editar");
                        Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString(warnSinPermTransac), null);
                    }
                }
                if (status) {
                    deleteImage();
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("medicamento.info.datGuardados"), null);
                    rndSave = Constantes.INACTIVO;
                    activo = Constantes.INACTIVO;
                    combo = Constantes.INACTIVO;
                    buscarMedicamento();
                }
            }
        } catch (Exception ex) {
            LOGGER.error("ERROR al guardar el Medicamento", ex);
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("medicamento.err.guardar"), null);
            if (!status) {
                medicamentoSelect.setIdMedicamento(null);
            }
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public void statusMedicamento(String idMedicamento, int status) {
        try {
            if (permiso.isPuedeEditar()) {
                Medicamento mS = new Medicamento(); 
                mS.setIdMedicamento(idMedicamento);
                mS.setUpdateIdUsuario(usuarioSelect.getIdUsuario());
                mS.setUpdateFecha(new Date());
                if (status > 0) {
                    mS.setActivo(0);
                } else {
                    mS.setActivo(1);
                }
                boolean res = medicamentoService.updateEstatusInsumo(mS);
                if(res){
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, "Se cambió el estatus del insumo correctamente", null);
                }
                for (VistaMedicamento view : medicamentoList) {
                    if (view.getIdMedicamento().equals(idMedicamento)) {
                        view.setActivo(mS.getActivo());
                        break;
                    }
                }
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString(warnSinPermTransac), null);
            }
        } catch (Exception ex) {
            LOGGER.error("Error al actualizar status Medicamento: {}", ex.getMessage());
        }
    }

    public void obtieneInsumo() {
        message = Constantes.ACTIVO;
        if (tipoInsumoList != null) {
            medi = false;
            mate = false;
            prot = false;
            if (tipoInsumo == 0) {
                tipoInsumo = tipoInsumoList.get(0).getIdCatalogoGeneral();
            }

            switch (tipoInsumo) {
                case Constantes.MEDI:
                    medi = true;
                    widthModal = "800";
                    heightModal = "550";
                    break;
                case Constantes.MATC:
                    mate = true;
                    widthModal = "600";
                    heightModal = "400";
                    break;
                case Constantes.PROT:
                    prot = true;
                    break;
                default:
            }
            medicamentoList2 = null;
            cadenaBusqueda = "";
            buscarMedicamento();
        }
    }

    public List<SustanciaActiva> autoComplete(String cadena) {
        try {
            sustanciaList = sustanciaService.obtenerListaPorNombre(cadena);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener autoComplete Sustancia Activa: {}", ex.getMessage());
        }
        return sustanciaList;
    }

    public void sustanciaActivaPorId(int cadena) {
        try {
            sustanciaSelect = new SustanciaActiva();
            sustanciaSelect = sustanciaService.obtenerPorId(cadena);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener autoComplete Sustancia Activa: {}", ex.getMessage());
        }
    }

    public void layoutFileUpload(FileUploadEvent event) {

        try {
            message = Constantes.INACTIVO;
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
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errFormatoIncorrecto), null);
                    break;
            }
            if (!medicamentoLayout.isEmpty()) {
                noProcess = Constantes.ACTIVO;
            }

        } catch (Exception ex) {
            LOGGER.error("ERROR al obtener la imagen: {}", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errFormatoIncorrecto), null);
            noProcess = Constantes.INACTIVO;

        }
    }
    
    
   public void uploadAttachment(FileUploadEvent event) throws IOException {
        try{
            if (null != event.getFile()) {
                UploadedFile file = event.getFile();
                String nameImage= sdf.format(date) + file.getFileName();                
                createImagen(file.getContents(),nameImage);
                medicamentoSelect.setImagenPresentacion(IOUtils.toByteArray(file.getInputstream()));
                medicamentoSelect.setNameImage(nameImage);
            }
        }catch(Exception ex){
            LOGGER.error("ERROR al obtener la imagen: {}", ex.getMessage());
        }
    }  
//region Getter & Setter

    public Pattern getRegexClave() {
        return regexClave;
    }

    public void setRegexClave(Pattern regexClave) {
        this.regexClave = regexClave;
    }

    public Pattern getRegexGrupo() {
        return regexGrupo;
    }

    public void setRegexGrupo(Pattern regexGrupo) {
        this.regexGrupo = regexGrupo;
    }

    public Pattern getRegexMed() {
        return regexMed;
    }

    public void setRegexMed(Pattern regexMed) {
        this.regexMed = regexMed;
    }

    public boolean isMessage() {
        return message;
    }

    public void setMessage(boolean message) {
        this.message = message;
    }

    public int getTipoInsumoL() {
        return tipoInsumoL;
    }

    public void setTipoInsumoL(int tipoInsumoL) {
        this.tipoInsumoL = tipoInsumoL;
    }

    public boolean isDivisible() {
        return divisible;
    }

    public void setDivisible(boolean divisible) {
        this.divisible = divisible;
    }

    public int getCuadro() {
        return cuadro;
    }

    public void setCuadro(int cuadro) {
        this.cuadro = cuadro;
    }

    public int getActiv() {
        return activ;
    }

    public void setActiv(int activ) {
        this.activ = activ;
    }

    public PresentacionMedicamento getPresentacionMedicamentoSalida() {
        return presentacionMedicamentoSalida;
    }

    public void setPresentacionMedicamentoSalida(PresentacionMedicamento presentacionMedicamentoSalida) {
        this.presentacionMedicamentoSalida = presentacionMedicamentoSalida;
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

    public String getClaveIns() {
        return claveIns;
    }

    public void setClaveIns(String claveIns) {
        this.claveIns = claveIns;
    }

    public String getSusActiva() {
        return susActiva;
    }

    public void setSusActiva(String susActiva) {
        this.susActiva = susActiva;
    }

    public String getViaAdmin() {
        return viaAdmin;
    }

    public void setViaAdmin(String viaAdmin) {
        this.viaAdmin = viaAdmin;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    public BigDecimal getConcentracion() {
        return concentracion;
    }

    public void setConcentracion(BigDecimal concentracion) {
        this.concentracion = concentracion;
    }
    
    public String getNombreLargo() {
        return nombreLargo;
    }

    public void setNombreLargo(String nombreLargo) {
        this.nombreLargo = nombreLargo;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    public String getpEntrada() {
        return pEntrada;
    }

    public void setpEntrada(String pEntrada) {
        this.pEntrada = pEntrada;
    }

    public String getFactor() {
        return factor;
    }

    public void setFactor(String factor) {
        this.factor = factor;
    }

    public String getpSalida() {
        return pSalida;
    }

    public void setpSalida(String pSalida) {
        this.pSalida = pSalida;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getSubcategoria() {
        return subcategoria;
    }

    public void setSubcategoria(String subcategoria) {
        this.subcategoria = subcategoria;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getIndivisible() {
        return indivisible;
    }

    public void setIndivisible(String indivisible) {
        this.indivisible = indivisible;
    }

    public String getCuadroB() {
        return cuadroB;
    }

    public void setCuadroB(String cuadroB) {
        this.cuadroB = cuadroB;
    }

    public String getActiva() {
        return activa;
    }

    public void setActiva(String activa) {
        this.activa = activa;
    }

    public String gettInsumo() {
        return tInsumo;
    }

    public void settInsumo(String tInsumo) {
        this.tInsumo = tInsumo;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    public VistaMedicamento getViewMedicamento() {
        return viewMedicamento;
    }

    public void setViewMedicamento(VistaMedicamento viewMedicamento) {
        this.viewMedicamento = viewMedicamento;
    }

    public List<VistaMedicamento> getMedicamentoList2() {
        return medicamentoList2;
    }

    public void setMedicamentoList2(List<VistaMedicamento> medicamentoList2) {
        this.medicamentoList2 = medicamentoList2;
    }

    public List<VistaMedicamento> getMedicamentoLayout() {
        return medicamentoLayout;
    }

    public void setMedicamentoLayout(List<VistaMedicamento> medicamentoLayout) {
        this.medicamentoLayout = medicamentoLayout;
    }

    public String getNameFile() {
        return namefile;
    }

    public void setNameFile(String file) {
        this.namefile = file;
    }

    public boolean isNoProcess() {
        return noProcess;
    }

    public void setNoProcess(boolean noProcess) {
        this.noProcess = noProcess;
    }

    public int getNumeroRegistros() {
        return numeroRegistros;
    }

    public void setNumeroRegistros(int numeroRegistros) {
        this.numeroRegistros = numeroRegistros;
    }

    public String getWidthModal() {
        return widthModal;
    }

    public void setWidthModal(String widthModal) {
        this.widthModal = widthModal;
    }

    public String getHeightModal() {
        return heightModal;
    }

    public void setHeightModal(String heightModal) {
        this.heightModal = heightModal;
    }

    public boolean isMedi() {
        return medi;
    }

    public void setMedi(boolean medi) {
        this.medi = medi;
    }

    public boolean isMate() {
        return mate;
    }

    public void setMate(boolean mate) {
        this.mate = mate;
    }

    public boolean isProt() {
        return prot;
    }

    public void setProt(boolean prot) {
        this.prot = prot;
    }

    public CatalogoGeneralService getCatalogoGeneralService() {
        return catalogoGeneralService;
    }

    public void setCatalogoGeneralService(CatalogoGeneralService catalogoGeneralService) {
        this.catalogoGeneralService = catalogoGeneralService;
    }

    public List<CatalogoGeneral> getTipoInsumoList() {
        return tipoInsumoList;
    }

    public void setTipoInsumoList(List<CatalogoGeneral> tipoInsumoList) {
        this.tipoInsumoList = tipoInsumoList;
    }

    public int getTipoInsumo() {
        return tipoInsumo;
    }

    public void setTipoInsumo(int tipoInsumo) {
        this.tipoInsumo = tipoInsumo;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isRndSave() {
        return rndSave;
    }

    public void setRndSave(boolean rndSave) {
        this.rndSave = rndSave;
    }

    public boolean isRndEdit() {
        return rndEdit;
    }

    public void setRndEdit(boolean rndEdit) {
        this.rndEdit = rndEdit;
    }

    public boolean isStatusProcess() {
        return statusProcess;
    }

    public void setStatusProcess(boolean statusProcess) {
        this.statusProcess = statusProcess;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Usuario getUsuarioSelect() {
        return usuarioSelect;
    }

    public void setUsuarioSelect(Usuario usuarioSelect) {
        this.usuarioSelect = usuarioSelect;
    }

    public UsuarioService getUsuarioService() {
        return usuarioService;
    }

    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public String getFileImg() {
        return fileImg;
    }

    public void setFileImg(String fileImg) {
        this.fileImg = fileImg;
    }
    
    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public boolean isCombo() {
        return combo;
    }

    public void setCombo(boolean combo) {
        this.combo = combo;
    }

    public boolean isBtnNew() {
        return btnNew;
    }

    public void setBtnNew(boolean btnNew) {
        this.btnNew = btnNew;
    }

    public SustanciaActivaService getSustanciaService() {
        return sustanciaService;
    }

    public void setSustanciaService(SustanciaActivaService sustanciaService) {
        this.sustanciaService = sustanciaService;
    }

    public void handleSelect(SelectEvent e) {
        sustanciaSelect = (SustanciaActiva) e.getObject();
    }

    public void handleUnSelect(UnselectEvent e) {
        sustanciaSelect = (SustanciaActiva) e.getObject();
    }

    public void phaseListener(PhaseEvent e) {
        List<FacesMessage> messages = e.getFacesContext().getMessageList();        
    }

    public SustanciaActiva getSustanciaSelect() {
        return sustanciaSelect;
    }

    public void setSustanciaSelect(SustanciaActiva sustanciaSelect) {
        this.sustanciaSelect = sustanciaSelect;
    }

    public List<SustanciaActiva> getSustanciaList() {
        return sustanciaList;
    }

    public void setSustanciaList(List<SustanciaActiva> sustanciaList) {
        this.sustanciaList = sustanciaList;
    }

    public PresentacionMedicamentoService getPresentacionMedicamentoService() {
        return presentacionMedicamentoService;
    }

    public void setPresentacionMedicamentoService(PresentacionMedicamentoService presentacionMedicamentoService) {
        this.presentacionMedicamentoService = presentacionMedicamentoService;
    }

    public PresentacionMedicamento getPresentacionMedicamentoSelect() {
        return presentacionMedicamentoSelect;
    }

    public void setPresentacionMedicamentoSelect(PresentacionMedicamento presentacionMedicamentoSelect) {
        this.presentacionMedicamentoSelect = presentacionMedicamentoSelect;
    }

    public List<PresentacionMedicamento> getPresentacionMedicamentoList() {
        return presentacionMedicamentoList;
    }

    public void setPresentacionMedicamentoList(List<PresentacionMedicamento> presentacionMedicamentoList) {
        this.presentacionMedicamentoList = presentacionMedicamentoList;
    }

    public SubcategoriaMedicamentoService getSubcategoriaMedicamentoService() {
        return subcategoriaMedicamentoService;
    }

    public void setSubcategoriaMedicamentoService(SubcategoriaMedicamentoService subcategoriaMedicamentoService) {
        this.subcategoriaMedicamentoService = subcategoriaMedicamentoService;
    }

    public SubcategoriaMedicamento getSubcategoriaMedicamentoSelect() {
        return subcategoriaMedicamentoSelect;
    }

    public void setSubcategoriaMedicamentoSelect(SubcategoriaMedicamento subcategoriaMedicamentoSelect) {
        this.subcategoriaMedicamentoSelect = subcategoriaMedicamentoSelect;
    }

    public List<SubcategoriaMedicamento> getSubcategoriaMedicamentoList() {
        return subcategoriaMedicamentoList;
    }

    public void setSubcategoriaMedicamentoList(List<SubcategoriaMedicamento> subcategoriaMedicamentoList) {
        this.subcategoriaMedicamentoList = subcategoriaMedicamentoList;
    }

    public UnidadConcentracionService getUnidadConcentracionService() {
        return unidadConcentracionService;
    }

    public void setUnidadConcentracionService(UnidadConcentracionService unidadConcentracionService) {
        this.unidadConcentracionService = unidadConcentracionService;
    }

    public UnidadConcentracion getUnidadConcentracionSelect() {
        return unidadConcentracionSelect;
    }

    public void setUnidadConcentracionSelect(UnidadConcentracion unidadConcentracionSelect) {
        this.unidadConcentracionSelect = unidadConcentracionSelect;
    }

    public List<UnidadConcentracion> getUnidadConcentracionList() {
        return unidadConcentracionList;
    }

    public void setUnidadConcentracionList(List<UnidadConcentracion> unidadConcentracionList) {
        this.unidadConcentracionList = unidadConcentracionList;
    }

    public CategoriaMedicamentoService getCategoriaMedicamentoService() {
        return categoriaMedicamentoService;
    }

    public void setCategoriaMedicamentoService(CategoriaMedicamentoService categoriaMedicamentoService) {
        this.categoriaMedicamentoService = categoriaMedicamentoService;
    }

    public ViaAdministracionService getViaAdministracionService() {
        return viaAdministracionService;
    }

    public void setViaAdministracionService(ViaAdministracionService viaAdministracionService) {
        this.viaAdministracionService = viaAdministracionService;
    }

    public ViaAdministracion getViaAdministracionSelect() {
        return viaAdministracionSelect;
    }

    public void setViaAdministracionSelect(ViaAdministracion viaAdministracionSelect) {
        this.viaAdministracionSelect = viaAdministracionSelect;
    }

    public List<ViaAdministracion> getViaAdministracionList() {
        return viaAdministracionList;
    }

    public void setViaAdministracionList(List<ViaAdministracion> viaAdministracionList) {
        this.viaAdministracionList = viaAdministracionList;
    }

    public List<LocalidadAVG> getLocalidadList() {
        return localidadList;
    }

    public void setLocalidadList(List<LocalidadAVG> localidadList) {
        this.localidadList = localidadList;
    }

    public CategoriaMedicamento getCategoriaMedicamentoSelect() {
        return categoriaMedicamentoSelect;
    }

    public void setCategoriaMedicamentoSelect(CategoriaMedicamento categoriaMedicamentoSelect) {
        this.categoriaMedicamentoSelect = categoriaMedicamentoSelect;
    }

    public List<CategoriaMedicamento> getCategoriaMedicamentoList() {
        return categoriaMedicamentoList;
    }

    public void setCategoriaMedicamentoList(List<CategoriaMedicamento> categoriaMedicamentoList) {
        this.categoriaMedicamentoList = categoriaMedicamentoList;
    }

    public MedicamentoService getMedicamentoService() {
        return medicamentoService;
    }

    public void setMedicamentoService(MedicamentoService medicamentoService) {
        this.medicamentoService = medicamentoService;
    }

    public Medicamento getMedicamentoSelect() {
        return medicamentoSelect;
    }

    public void setMedicamentoSelect(Medicamento medicamentoSelect) {
        this.medicamentoSelect = medicamentoSelect;
    }

    public List<VistaMedicamento> getMedicamentoList() {
        return medicamentoList;
    }

    public void setMedicamentoList(List<VistaMedicamento> medicamentoList) {
        this.medicamentoList = medicamentoList;
    }

    public String getCadenaBusqueda() {
        return cadenaBusqueda;
    }

    public void setCadenaBusqueda(String cadenaBusqueda) {
        this.cadenaBusqueda = cadenaBusqueda;
    }
//endregion

    public boolean isMedicamentoControlado() {
        return medicamentoControlado;
    }

    public void setMedicamentoControlado(boolean medicamentoControlado) {
        this.medicamentoControlado = medicamentoControlado;
    }

    public int getControla() {
        return controla;
    }

    public void setControla(int controla) {
        this.controla = controla;
    }

    public ParamBusquedaReporte getParamBusquedaReporte() {
        return paramBusquedaReporte;
    }

    public void setParamBusquedaReporte(ParamBusquedaReporte paramBusquedaReporte) {
        this.paramBusquedaReporte = paramBusquedaReporte;
    }

    public MedicamentosLazy getMedicamentosLazy() {
        return medicamentosLazy;
    }

    public void setMedicamentosLazy(MedicamentosLazy medicamentosLazy) {
        this.medicamentosLazy = medicamentosLazy;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getControlado() {
        return controlado;
    }

    public void setControlado(String controlado) {
        this.controlado = controlado;
    }

    public Integer getRefrigeracion() {
        return refrigeracion;
    }

    public void setRefrigeracion(Integer refrigeracion) {
        this.refrigeracion = refrigeracion;
    }

    public boolean isEstatusRefrigeracion() {
        return estatusRefrigeracion;
    }

    public void setEstatusRefrigeracion(boolean estatusRefrigeracion) {
        this.estatusRefrigeracion = estatusRefrigeracion;
    }

    public String getClaveAlt() {
        return claveAlt;
    }

    public void setClaveAlt(String claveAlt) {
        this.claveAlt = claveAlt;
    }

    public String getRefrig() {
        return refrig;
    }

    public void setRefrig(String refrig) {
        this.refrig = refrig;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

    public boolean isMostrarPropiedades() {
        return mostrarPropiedades;
    }

    public void setMostrarPropiedades(boolean mostrarPropiedades) {
        this.mostrarPropiedades = mostrarPropiedades;
    }
    
    public Integer getMezclaParental() {
        return mezclaParental;
    }

    public void setMezclaParental(Integer mezclaParental) {
        this.mezclaParental = mezclaParental;
    }

    public boolean isParental() {
        return parental;
    }

    public void setParental(boolean parental) {
        this.parental = parental;
    }
}
