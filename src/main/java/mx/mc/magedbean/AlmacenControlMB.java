/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.magedbean;

import mx.mc.init.Constantes;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import mx.mc.enums.EstatusGabinete_Enum;
import mx.mc.enums.Transaccion_Enum;

import mx.mc.model.Almacen;
import mx.mc.model.AlmacenControl;
import mx.mc.model.Estructura;
import mx.mc.model.Medicamento;
import mx.mc.model.Usuario;

import mx.mc.service.AlmacenControlService;
import mx.mc.service.AlmacenService;
import mx.mc.service.EstructuraService;
import mx.mc.service.MedicamentoService;
import mx.mc.util.Comunes;
import mx.mc.util.UtilPath;
import org.primefaces.PrimeFaces;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.FileUploadEvent;

import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.UploadedFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import mx.mc.model.AlmacenControl_Extended;
import mx.mc.model.PermisoUsuario;
import mx.mc.util.Mensaje;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author bbautista
 */
@Controller
@Scope(value = "view")
public class AlmacenControlMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(AlmacenControlMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    private boolean btnNew;
    private boolean activo;
    private int numeroRegistros;
    private int minimo;
    private int reorden;
    private int maximo;
    private int dotacion;
    private boolean oldActivo;
    private int activeRow;
    private Pattern regexNumber;
    private Usuario currentUser;
    private boolean dotacionDiaria;
    private boolean noProcess;
    private String pathDefinition;
    private Date date = new Date();
    private SimpleDateFormat sdf = new SimpleDateFormat("MMdd");
    private String namefile;
    private String claveIns;
    private String motivo;
    private String nameAlmacen;
    private String idAlmacen;
    private String nombreCorto;
    private boolean status;
    private int presentacion;
    private boolean pointCtrl;
    private String valorMinimo;
    private String reordenMayor;
    private String reordenMayorMinimo;
    private String reordenMenor;
    private String maximoMayor;
    private String mayoralMinimo;
    private String maximoMayorReorden;
    private String formatoIncorrecto;
    private boolean solicitudXservicio;
    private PermisoUsuario permiso;

    @Autowired
    private transient MedicamentoService mediService;
    private Medicamento medicamento;
    private List<Medicamento> medicamentoList;
    private List<Medicamento> insumoList;

    @Autowired
    private transient AlmacenService almacenService;
    private Almacen almacenSelect;
    private List<Almacen> almacenList;

    @Autowired
    private transient AlmacenControlService almacenCtrlService;
    private AlmacenControl almacenCtrlSelect;
    private List<AlmacenControl> almacenCtrlList = new ArrayList<>();
    private List<AlmacenControl_Extended> almacenCtrlLayoutList = new ArrayList<>();

    @Autowired
    private transient EstructuraService estructuraService;
    private Estructura estructuraSelect;
    private List<Estructura> estructuraList;
    private List<Estructura> estructuraAux;

    @PostConstruct
    public void init() {
        valorMinimo = "almacControl.warn.valorMinimo";
        reordenMayor = "almacControl.warn.reordenMayor";
        reordenMayorMinimo = "almacControl.warn.reordenMayorMinimo";
        reordenMenor = "almacControl.warn.reordenMenor";
        maximoMayor = "almacControl.warn.maximoMayor";
        mayoralMinimo = "almacControl.warn.mayoralMinimo";
        maximoMayorReorden = "almacControl.warn.maximoMayorReorden";
        formatoIncorrecto = "medicamento.err.formatoIncorrecto";
        initialize();        
        permiso = Comunes.obtenerPermisos(Transaccion_Enum.ALMACENCONTROL.getSufijo());
        if (permiso.isPuedeVer()) {
            obtieneAlmacenList();
            obtieneAlmacenCtrlList();
        }
    }

    /*  METHOD PRIVATE     */
    private void initialize() {

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");

        currentUser = sesion.getUsuarioSelected();
        regexNumber = Constantes.regexNumber;
        medicamento = new Medicamento();
        almacenSelect = new Almacen();
        almacenCtrlSelect = new AlmacenControl();
        estructuraSelect = new Estructura();
        almacenList = new ArrayList<>();
        medicamentoList = new ArrayList<>();
        estructuraList = new ArrayList<>();
        activeRow = -1;
        btnNew = Constantes.INACTIVO;
        dotacionDiaria = sesion.isDotacionDiaria();
        boolean dotacionDiariaMat = sesion.isDotacionDiariaCuracion();
        pointCtrl = (dotacionDiaria || dotacionDiariaMat) ? Constantes.INACTIVO : Constantes.ACTIVO;
        solicitudXservicio = sesion.isFunSolicitudXServicio();
    }

    private void clean() {
        almacenCtrlSelect = new AlmacenControl();
        btnNew = Constantes.INACTIVO;
        minimo = 0;
        reorden = 0;
        maximo = 0;
        dotacion = 0;
        activeRow = -1;
    }

    private void validacion() {
        boolean activ = Constantes.INACTIVO;
        if (medicamento.getIdMedicamento() != null) {
            if (pointCtrl) {
                if (minimo > 0 && minimo < reorden && reorden < maximo) {
                    activ = Constantes.ACTIVO;
                }

            } else if (dotacion > 0) {
                activ = Constantes.ACTIVO;
            }
        }
        btnNew = activ;
    }

    public void cambiarStatusAlmContrl(String idAlmacenControl, boolean activo) {
        LOGGER.trace("mx.mc.magedbean.AlmacenControlMB.cambiarStatusAlmContrl()");
        AlmacenControl ac = new AlmacenControl(); 
        String mensajeStat = "";
        try {
            ac.setIdAlmacenPuntosControl(idAlmacenControl);
            ac.setUpdateFecha(new Date());
            ac.setUpdateIdUsuario(currentUser.getIdUsuario());
            if (activo) {
                ac.setActivo(Constantes.INACTIVO);
                mensajeStat = "ok.inactivar";
            } else {
                ac.setActivo(Constantes.ACTIVO);
                mensajeStat =  "ok.activar";
            }
            boolean stat = almacenCtrlService.actualizar(ac);
            if (stat) {
                Mensaje.showMessage("Info", RESOURCES.getString(mensajeStat), null);                
            }else{
                Mensaje.showMessage("Error", RESOURCES.getString("almacControl.err.guardDatos"), null);
            }
            almacenCtrlList.stream().filter((it) -> (it.getIdAlmacenPuntosControl().equals(ac.getIdAlmacenPuntosControl()))).forEachOrdered((it) -> {
                it.setActivo(ac.isActivo());
            });
        } catch (Exception e) {
            LOGGER.error("Error al cambiarStatusAlmContrl: {}", e.getMessage());
        }
    }
    
    private void obtieneAlmacenCtrlList() {
        try {            
            if (almacenCtrlList != null) {
                AlmacenControl ac = new AlmacenControl();
                ac.setIdAlmacen(estructuraList.get(0).getIdEstructura());
                almacenCtrlList = almacenCtrlService.obtenerLista(ac);
                numeroRegistros = almacenCtrlList.size();
            }
        } catch (Exception ex) {
            LOGGER.error("Error al obtener AlmacenCtrlList: {}", ex.getMessage());
        }

    }

    private int obtenerNumRowActive(RowEditEvent event) {
        AjaxBehaviorEvent evt = (AjaxBehaviorEvent) event;
        DataTable table = (DataTable) evt.getSource();
        return table.getRowIndex();
    }

    private void buscaEnList(Estructura padre) {
        try {
            if (padre != null) {
                for (int i = 0; i < estructuraAux.size(); i++) {
                    if (estructuraAux.get(i).getIdEstructuraPadre() != null
                            && estructuraAux.get(i).getIdEstructuraPadre().toLowerCase().contains(padre.getIdEstructura().toLowerCase())) {
                        estructuraList.add(estructuraAux.get(i));
                        buscaEnList(estructuraAux.get(i));
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al buscar: {}", ex.getMessage());
        }
    }

    private boolean existsMedtoEnLista(String clv, String idAlma) {
        boolean exits = Constantes.INACTIVO;
        int i = 0;
        activeRow = -1;
        for (AlmacenControl aux : almacenCtrlList) {
            if (aux.getIdMedicamento().contains(clv) && aux.getIdAlmacen().contains(idAlma)) {
                exits = Constantes.ACTIVO;
                activeRow = i;
                break;
            }
            i++;
        }
        return exits;
    }

    private int redondeaCantidad(int xcaja, int solicitada) {
        int resp = 0;
        if ((solicitada % xcaja) > 0) {
            int ent = (solicitada / xcaja) + 1;
            resp = xcaja * ent;
        } else {
            resp = solicitada;
        }
        return resp;
    }

    /*  METHOD PUBLIC      */
    public void searchAlmacen() {
        try {
            DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("mainForm:tblAlmacenCtrl");
            dataTable.resetValue();
            if (almacenCtrlSelect != null) {
                AlmacenControl ac = new AlmacenControl();
                ac.setIdAlmacen(almacenCtrlSelect.getIdAlmacen());
                almacenCtrlList = almacenCtrlService.obtenerLista(ac);
                numeroRegistros = almacenCtrlList.size();
            }
        } catch (Exception ex) {
            LOGGER.error("Error al obtener AlmacenCtrlList: {}", ex.getMessage());
        }
    }

    public void obtieneAlmacenList() {
        try {
            estructuraAux = solicitudXservicio ? estructuraService.obtenerAlmacenesServicioPCActivos() : estructuraService.obtenerAlmacenesActivos();
            if (Comunes.isAdministrador()) {
                estructuraList.addAll(estructuraAux);
            } else {
                estructuraList = new ArrayList<>();
                for (Estructura item : estructuraAux) {
                    if (item.getActiva() == 1
                            && item.getIdEstructura().equals(currentUser.getIdEstructura())) {
                        estructuraList.add(item);
                        estructuraList.addAll(getEstructuraList(item.getIdEstructura()));
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al obtener Almacen Puntos Cotrol: {}", ex.getMessage());
        }
    }

    private List<Estructura> getEstructuraList(String idEstructura) {
        List<Estructura> estructuraLista = new ArrayList<>();
        for (Estructura item : estructuraAux) {
            if (idEstructura.equals(item.getIdEstructuraPadre())) {
                estructuraLista.add(item);
                estructuraLista.addAll(getEstructuraList(item.getIdEstructura()));
            }
        }
        return estructuraLista;
    }

    public List<Medicamento> autoComplete(String cadena) {
        try {
            medicamentoList = mediService.obtenerAutoCompNombreClave(cadena);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener Medicamentos: {}", ex.getMessage());
        }
        return medicamentoList;
    }

    public void crearAlmacenControl() throws Exception {
        activo = Constantes.ACTIVO;
        try {
            if (permiso.isPuedeCrear() && almacenCtrlSelect != null) {
                Medicamento med;
                if (medicamento != null) {

                    almacenCtrlSelect.setIdMedicamento(medicamento.getIdMedicamento());
                    med = mediService.obtenerPorIdMedicamento(medicamento.getIdMedicamento());

                    if (med != null) {
                        if (med.getActivo() != 0) {
                            almacenCtrlSelect.setInsumo(med.getNombreCorto());

                            //Consultamos El nombre del almancen con el IdAlmacen (Aún no se termina la parte del Almacen)
                            if (almacenCtrlSelect.getIdAlmacen() != null) {
                                estructuraSelect = estructuraService.obtenerEstructura(almacenCtrlSelect.getIdAlmacen());
                                almacenCtrlSelect.setAlmacen(estructuraSelect.getNombre());

                                    if (estructuraSelect.getIdTipoAlmacen() < Constantes.SUBALMACEN) {
                                        almacenCtrlSelect.setMinimo(redondeaCantidad(med.getFactorTransformacion(), minimo));
                                        almacenCtrlSelect.setReorden(redondeaCantidad(med.getFactorTransformacion(), reorden));
                                        almacenCtrlSelect.setMaximo(redondeaCantidad(med.getFactorTransformacion(), maximo));
                                        almacenCtrlSelect.setDotacion(redondeaCantidad(med.getFactorTransformacion(), dotacion));
                                    } else {
                                        almacenCtrlSelect.setMinimo(minimo);
                                        almacenCtrlSelect.setReorden(reorden);
                                        almacenCtrlSelect.setMaximo(maximo);
                                        almacenCtrlSelect.setDotacion(dotacion);
                                    }
                                    almacenCtrlSelect.setIdAlmacenPuntosControl(Comunes.getUUID());

                                    almacenCtrlSelect.setActivo(Constantes.ACTIVO);
                                    almacenCtrlSelect.setSolicitud(0);
                                    almacenCtrlSelect.setEstatusGabinete(EstatusGabinete_Enum.PENDIENTE.getValue());
                                    almacenCtrlSelect.setClaveInstitucional(med.getClaveInstitucional());
                                    almacenCtrlSelect.setInsertIdUsuario(currentUser.getIdUsuario());
                                    almacenCtrlSelect.setInsertFecha(new Date());

                                    if (!existsMedtoEnLista(almacenCtrlSelect.getIdMedicamento(), almacenCtrlSelect.getIdAlmacen())) {
                                        almacenCtrlList.add(almacenCtrlSelect);
                                        almacenCtrlService.insertar(almacenCtrlSelect);
                                        numeroRegistros = almacenCtrlList.size();
                                    } else {
                                        Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString("almacControl.warn.medicamExiste"), null);
                                        PrimeFaces.current().executeScript("jQuery('span.ui-icon-pencil').eq(" + activeRow + ").each(function(){jQuery(this).click()});");
                                    }
                            }
                            clean();
                        } else {
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("inventarios.err.medicInactivo"), null);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al insertar insumo: {}", ex.getMessage());
        }
    }

    public void eliminaInsumoDeLista(String idAlmacenPuntosControl) {
        try {
            AlmacenControl almacenCtrl = new AlmacenControl();
            almacenCtrl.setIdAlmacenPuntosControl(idAlmacenPuntosControl);
            if(permiso.isPuedeEliminar() && permiso.isPuedeProcesar()) {
               boolean resp = almacenCtrlService.eliminar(almacenCtrl);
               if(resp) {
                   searchAlmacen();
                   Mensaje.showMessage(Constantes.MENSAJE_INFO, "Se elimino el registro correctamente.", null);                
               } else {
                   Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Revisar, debido a un error.", null);                
               }
               clean();
            }
        } catch(Exception ex) {
            LOGGER.error("Error al eliminar insumo de registro: {}", ex.getMessage());
        }
    }
    
    public void handleSelect(SelectEvent e) {
        medicamento = (Medicamento) e.getObject();
        validacion();
    }

    public void handleUnSelect(UnselectEvent e) {
        medicamento = (Medicamento) e.getObject();
        validacion();
    }

    public void onRowEdit(RowEditEvent event) {
        try {
            if (permiso.isPuedeEditar()) {
                boolean sigue = false;
                AlmacenControl ac = (AlmacenControl) event.getObject();
                Medicamento med = mediService.obtenerMedicamento(ac.getIdMedicamento());
                Estructura almacen = estructuraService.obtenerEstructura(ac.getIdAlmacen());
                AlmacenControl uac = new AlmacenControl();
                uac.setIdAlmacenPuntosControl(ac.getIdAlmacenPuntosControl());
                if (almacen != null && med != null) {
                    if (almacen.getIdTipoAlmacen() < Constantes.SUBALMACEN) {
                        int idx = almacenCtrlList.indexOf(ac);
                        if (pointCtrl) {
                            if (ac.getMinimo() > 0 && ac.getMinimo() < ac.getReorden() && ac.getReorden() < ac.getMaximo()) {
                                uac.setMinimo(redondeaCantidad(med.getFactorTransformacion(), ac.getMinimo()));
                                uac.setReorden(redondeaCantidad(med.getFactorTransformacion(), ac.getReorden()));
                                uac.setMaximo(redondeaCantidad(med.getFactorTransformacion(), ac.getMaximo()));
                                almacenCtrlList.get(idx).setMinimo(uac.getMinimo());
                                almacenCtrlList.get(idx).setReorden(uac.getReorden());
                                almacenCtrlList.get(idx).setMaximo(uac.getMaximo());
                                sigue = true;
                            }
                        } else if (ac.getDotacion() > 0) {
                            uac.setDotacion(redondeaCantidad(med.getFactorTransformacion(), ac.getDotacion()));
                            almacenCtrlList.get(idx).setDotacion(uac.getDotacion());
                            sigue = true;
                        }
                    } else {
                        if (pointCtrl) {
                            if (ac.getMinimo() > 0 && ac.getMinimo() < ac.getReorden() && ac.getReorden() < ac.getMaximo()) {
                                uac.setMinimo(ac.getMinimo());
                                uac.setReorden(ac.getReorden());
                                uac.setMaximo(ac.getMaximo());
                                sigue = true;
                            }
                        } else if (ac.getDotacion() > 0) {
                            uac.setDotacion(ac.getDotacion());
                            sigue = true;
                        }
                    }

                    if (sigue) {
                        uac.setActivo(ac.isActivo());
                        if (oldActivo != ac.isActivo()) {
                            if (ac.isActivo()) {
                                uac.setEstatusGabinete(EstatusGabinete_Enum.ACTIVAR.getValue());
                            } else {
                                uac.setEstatusGabinete(EstatusGabinete_Enum.INACTIVAR.getValue());
                            }
                        }
                        uac.setUpdateFecha(new Date());
                        uac.setUpdateIdUsuario(currentUser.getIdUsuario());
                        almacenCtrlService.actualizar(uac);
                        clean();
                        Mensaje.showMessage("Info", RESOURCES.getString("almacControl.info.datGuadadosCorrec"), null);
                    } else {
                        PrimeFaces.current().executeScript("jQuery('span.ui-icon-pencil').eq(" + activeRow + ").each(function(){jQuery(this).click()});");
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al Actualizar: {}", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("almacControl.err.guardDatos"), null);
        }
    }

    public void onRowCancel(RowEditEvent event) {
        int row = obtenerNumRowActive(event);
        if (activeRow == row) {
            almacenCtrlList.get(row).setMinimo(minimo);
            almacenCtrlList.get(row).setReorden(reorden);
            almacenCtrlList.get(row).setMaximo(maximo);
            almacenCtrlList.get(row).setDotacion(dotacion);
            almacenCtrlList.get(row).setActivo(oldActivo);
            clean();
        }
    }

    public void onRowEditInit(RowEditEvent event) {
        int row = obtenerNumRowActive(event);
        if (permiso.isPuedeEditar()) {
            AlmacenControl ac = (AlmacenControl) event.getObject();
            if (activeRow == -1) {
                minimo = ac.getMinimo();
                reorden = ac.getReorden();
                maximo = ac.getMaximo();
                dotacion = ac.getDotacion();
                oldActivo = ac.isActivo();
                activeRow = row;
            } else if (activeRow > -1 && activeRow != row) {
                PrimeFaces.current().executeScript("jQuery('span.ui-icon-close').eq(" + row + ").each(function(){jQuery(this).click()});");
            }
        } else {
            PrimeFaces.current().executeScript("jQuery('span.ui-icon-close').eq(" + row + ").each(function(){jQuery(this).click()});");
        }

    }

    public void valdMinimo() {
        if (minimo <= 0) {
            Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString(valorMinimo), null);
        }
        validacion();
    }

    public void valdReorden() {
        if (reorden <= 0) {
            Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString(reordenMayor), null);
        }
        if (reorden < minimo && minimo > 0) {
            Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString(reordenMayorMinimo), null);
        }
        if (reorden > maximo && maximo > 0) {
            Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString(reordenMenor), null);
        }

        validacion();
    }

    public void valdMaximo() {
        if (maximo <= 0) {
            Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString(maximoMayor), null);
        }
        if (maximo < minimo && minimo > 0) {
            Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString(mayoralMinimo), null);
        }
        if (maximo < reorden && reorden > 0) {
            Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString(maximoMayorReorden), null);
        }

        validacion();
    }

    public void valdDotacion() {
        if (dotacion < 0) {
            Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString("almacControl.warn.maximoDotacion"), null);
        }

        validacion();
    }

    public void valdMinimoRow(int newVal) {
        if (newVal <= 0) {
            Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString(valorMinimo), null);
        }

        if (newVal > reorden || newVal == reorden) {
            Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString("almacControl.warn.minMenReorden"), null);
        }

        if (newVal > maximo || newVal == maximo) {
            Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString("almacControl.warn.minMenMaxim"), null);
        }
    }

    public void valdReordenRow(int newVal) {
        if (newVal <= 0) {
            Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString(reordenMayor), null);
        }

        if (newVal < minimo || newVal == minimo) {
            Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString(reordenMayorMinimo), null);
        }

        if (newVal > maximo || newVal == maximo) {
            Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString(reordenMenor), null);
        }
    }

    public void valdMaximoRow(int newVal) {
        if (newVal <= 0) {
            Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString(maximoMayor), null);
        }

        if (newVal < minimo || newVal == minimo) {
            Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString(mayoralMinimo), null);
        }

        if (newVal < reorden || newVal == reorden) {
            Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString(maximoMayorReorden), null);
        }
    }

    private void cleanObjects() {
        nameAlmacen = "";
        idAlmacen = "";
        claveIns = "";
        nombreCorto = "";
        minimo = 0;
        reorden = 0;
        maximo = 0;
        dotacion = 0;
        status = Constantes.INACTIVO;
        presentacion = 0;
    }

    public void agregarPuntosCtrl() {
        if (permiso.isPuedeCrear()) {
            insumoList = new ArrayList<>();
            almacenCtrlSelect = new AlmacenControl();
            almacenCtrlLayoutList = new ArrayList<>();
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
                case ".xlsx":
                    if (pointCtrl) {
                        readLayout2007Point(excelFilePath);
                    } else {
                        readLayout2007Dotacion(excelFilePath);
                    }
                    break;
                default:
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(formatoIncorrecto), null);
                    break;
            }
            if (!almacenCtrlLayoutList.isEmpty()) {
                noProcess = Constantes.ACTIVO;
            }

        } catch (Exception ex) {
            LOGGER.error("ERROR al obtener la imagen: {}", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(formatoIncorrecto), null);
            noProcess = Constantes.INACTIVO;

        }
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

    private void readLayout2007Point(String excelFilePath) {
        boolean exitoLay = Constantes.ACTIVO;
        int num = 0;
        String aux = "";
        DataFormatter formatter = new DataFormatter();
        try (FileInputStream inputStream = new FileInputStream(new File(excelFilePath)); Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet firstSheets = workbook.getSheetAt(0);
            Iterator<Row> iterator = firstSheets.iterator();
            almacenCtrlLayoutList = new ArrayList<>();
            CellReference cellreference = new CellReference("D9");
            Row row = firstSheets.getRow(cellreference.getRow());
            Cell cell0 = row.getCell(cellreference.getCol());
            String data = cell0.getStringCellValue();
            if (data.toUpperCase().contains("MINIMO") || data.toUpperCase().contains("MÍNIMO")) {
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
                                        case 0: //almacen
                                            nameAlmacen = cellAlmC.getStringCellValue();
                                            if (!nameAlmacen.equals("")) {
                                                estructuraSelect = estructuraService.getEstructuraForName(nameAlmacen);
                                                idAlmacen = estructuraSelect != null ? estructuraSelect.getIdEstructura() : "";
                                            }
                                            break;
                                        case 1://clave
                                            claveIns = formatter.formatCellValue(cellAlmC);
                                            break;
                                        case 2: //Nombre
                                            nombreCorto = cellAlmC.getStringCellValue();
                                            if (nombreCorto.length() > 299) {
                                                nombreCorto = nombreCorto.substring(0, 299);
                                            }
                                            break;
                                        case 3: //minimo
                                            aux = formatter.formatCellValue(cellAlmC);
                                            minimo = Integer.parseInt((aux));
                                            break;
                                        case 4: //reorden
                                            aux = formatter.formatCellValue(cellAlmC);
                                            reorden = Integer.parseInt((aux));
                                            break;
                                        case 5: //maximo
                                            aux = formatter.formatCellValue(cellAlmC);
                                            maximo = Integer.parseInt((aux));
                                            break;
                                        case 6: //Estatus
                                            aux = formatter.formatCellValue(cellAlmC);
                                            status = aux.equals("1") ? Constantes.ACTIVO : Constantes.INACTIVO;
                                            break;
                                        case 7: //Presentacion
                                            aux = formatter.formatCellValue(cellAlmC);
                                            presentacion = Integer.parseInt((aux));
                                            break;
                                        default:  
                                            if (nameAlmacen.equals("") && claveIns.equals(""))
                                            {
                                                minimo=0;
                                                reorden=0;
                                                maximo=0;
                                            }
                                            break;
                                    }
                                    if (nameAlmacen.equals("") && claveIns.equals("") && minimo == 0 && reorden == 0 && maximo == 0) {
                                        break;
                                    }
                                } catch (NumberFormatException exc2) {
                                    LOGGER.error("Ocurrio un error al parsear: ", exc2);
                                }
                            }

                            exitoLay = validaDatosCtrl();

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

                            if (!claveIns.equals("") && !nameAlmacen.equals("")) {
                                AlmacenControl_Extended almacenCtnrl = new AlmacenControl_Extended();
                                almacenCtnrl.setAlmacen(nameAlmacen);
                                almacenCtnrl.setIdAlmacen(idAlmacen);
                                if (insumoList.size() > 1) {
                                    almacenCtnrl.setInsumosList(insumoList);
                                    almacenCtnrl.setList(Constantes.ACTIVO);
                                } else {
                                    almacenCtnrl.setList(Constantes.INACTIVO);
                                }
                                almacenCtnrl.setClaveInstitucional(claveIns);
                                almacenCtnrl.setInsumo(nombreCorto);
                                almacenCtnrl.setMinimo(minimo);
                                almacenCtnrl.setReorden(reorden);
                                almacenCtnrl.setMaximo(maximo);
                                almacenCtnrl.setDotacion(dotacion);
                                almacenCtnrl.setActivo(activo);
                                almacenCtnrl.setMotivo(motivo);
                                almacenCtnrl.setProcess(exitoLay);

                                almacenCtrlLayoutList.add(almacenCtnrl);

                            }
                        }
                        num++;
                    } catch (NumberFormatException excp1) {
                        LOGGER.error("Ocurrio un error:", excp1);
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("medicamento.err.parseoIncorrecto"), null);
                    } catch (Exception exc3) {
                        LOGGER.error(RESOURCES.getString(formatoIncorrecto), exc3);
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, exc3.getMessage(), null);
                    }
                }
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("almacControl.err.formatoPuntos"), null);
            }
        } catch (Exception exc) {
            LOGGER.error(RESOURCES.getString(formatoIncorrecto), exc);
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(formatoIncorrecto), null);
        }

    }

    private void readLayout2007Dotacion(String excelFilePath) {
        boolean exito = Constantes.ACTIVO;
        int num = 0;
        String aux = "";
        DataFormatter formatter = new DataFormatter();
        try (FileInputStream inputStream = new FileInputStream(new File(excelFilePath)); Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet firstSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = firstSheet.iterator();
            almacenCtrlLayoutList = new ArrayList<>();

            CellReference cellreference = new CellReference("D9");
            Row row = firstSheet.getRow(cellreference.getRow());
            Cell cell0 = row.getCell(cellreference.getCol());
            String data = cell0.getStringCellValue();
            if (data.toUpperCase().contains("DOTACION") || data.toUpperCase().contains("DOTACIÓN")) {
                while (iterator.hasNext()) {
                    try {
                        Row nextRow = iterator.next();
                        Iterator<Cell> cellIterator = nextRow.cellIterator();
                        cleanObjects();
                        if (num > 7) {
                            exito = Constantes.ACTIVO;
                            insumoList = new ArrayList<>();
                            while (cellIterator.hasNext()) {
                                try {
                                    Cell cell = cellIterator.next();
                                    switch (cell.getColumnIndex()) {
                                        case 0: //almacen
                                            nameAlmacen = cell.getStringCellValue();
                                            if (!nameAlmacen.equals("")) {
                                                estructuraSelect = estructuraService.getEstructuraForName(nameAlmacen);
                                                idAlmacen = estructuraSelect != null ? estructuraSelect.getIdEstructura() : "";
                                            }
                                            break;
                                        case 1://clave
                                            claveIns = formatter.formatCellValue(cell);
                                            break;
                                        case 2: //Nombre
                                            nombreCorto = cell.getStringCellValue();
                                            if (nombreCorto.length() > 299) {
                                                nombreCorto = nombreCorto.substring(0, 299);
                                            }
                                            break;
                                        case 3: //dotacion
                                            aux = formatter.formatCellValue(cell);
                                            dotacion = Integer.parseInt((aux));
                                            break;
                                        case 4: //Estatus
                                            aux = formatter.formatCellValue(cell);
                                            status = aux.equals("1") ? Constantes.ACTIVO : Constantes.INACTIVO;
                                            break;
                                        case 5: //Presentacion
                                            aux = formatter.formatCellValue(cell);
                                            presentacion = Integer.parseInt((aux));
                                            break;
                                        default: 
                                    }
                                    if (nameAlmacen.equals("") && claveIns.equals("") && dotacion == 0) {
                                        break;
                                    }
                                } catch (NumberFormatException ex2) {
                                    LOGGER.error("Ocurrio un error al parsear:", ex2);
                                }
                            }

                            exito = validaDatosCtrl();

                            if (exito) {
                                // Se busca por coincidencia de clave
                                insumoList = mediService.obtenerInsumoLikeClave(claveIns);
                                if (insumoList.size() > 1) {
                                    // Se busca por igualdad de clave
                                    Medicamento medica = mediService.obtenerMedicaByClave(claveIns);
                                    if (medica != null) {
                                        insumoList = new ArrayList<>();
                                        insumoList.add(medica);
                                    }
                                }

                                switch (insumoList.size()) {
                                    case 0:
                                        motivo = RESOURCES.getString("almacControl.err.nofoundInsumo");
                                        exito = Constantes.INACTIVO;
                                        break;
                                    case 1:
                                        exito = agregarActualizar();
                                        break;
                                    default:
                                        motivo = RESOURCES.getString("almacControl.err.selectkey");
                                        exito = Constantes.INACTIVO;
                                        break;
                                }
                            }

                            if (!claveIns.equals("") && !nameAlmacen.equals("")) {
                                AlmacenControl_Extended almacenCtrl = new AlmacenControl_Extended();
                                almacenCtrl.setAlmacen(nameAlmacen);
                                almacenCtrl.setIdAlmacen(idAlmacen);
                                if (insumoList.size() > 1) {
                                    almacenCtrl.setInsumosList(insumoList);
                                    almacenCtrl.setList(Constantes.ACTIVO);
                                } else {
                                    almacenCtrl.setList(Constantes.INACTIVO);
                                }
                                almacenCtrl.setClaveInstitucional(claveIns);
                                almacenCtrl.setInsumo(nombreCorto);
                                almacenCtrl.setMinimo(minimo);
                                almacenCtrl.setReorden(reorden);
                                almacenCtrl.setMaximo(maximo);
                                almacenCtrl.setDotacion(dotacion);
                                almacenCtrl.setActivo(activo);
                                almacenCtrl.setMotivo(motivo);
                                almacenCtrl.setProcess(exito);

                                almacenCtrlLayoutList.add(almacenCtrl);

                            }
                        }
                        num++;
                    } catch (NumberFormatException ex1) {
                        LOGGER.error("Ocurrio un error:", ex1);
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("medicamento.err.parseoIncorrecto"), null);
                    } catch (Exception ex3) {
                        LOGGER.error(RESOURCES.getString(formatoIncorrecto), ex3);
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, ex3.getMessage(), null);
                    }
                }
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("almacControl.err.formatoDotacion"), null);
            }
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString(formatoIncorrecto), ex);
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(formatoIncorrecto), null);
        }
    }

    private boolean validaDatosCtrl() {
        boolean valid = Constantes.ACTIVO;
        if (idAlmacen.equals("")) {
            valid = Constantes.INACTIVO;
            motivo = RESOURCES.getString("almacControl.err.validAlmacen");
        } else if (claveIns.equals("")) {
            valid = Constantes.INACTIVO;
            motivo = RESOURCES.getString("almacControl.err.validClave");
        }
        if (pointCtrl) {
            if (minimo <= 0) {
                valid = Constantes.INACTIVO;
                motivo = RESOURCES.getString(valorMinimo);
            } else if (reorden <= 0) {
                valid = Constantes.INACTIVO;
                motivo = RESOURCES.getString(reordenMayor);
            } else if (maximo <= 0) {
                valid = Constantes.INACTIVO;
                motivo = RESOURCES.getString(maximoMayor);
            } else if (reorden < minimo && minimo > 0) {
                valid = Constantes.INACTIVO;
                motivo = RESOURCES.getString(reordenMayorMinimo);
            } else if (reorden > maximo && maximo > 0) {
                valid = Constantes.INACTIVO;
                motivo = RESOURCES.getString(reordenMenor);
            } else if (maximo < minimo && minimo > 0) {
                valid = Constantes.INACTIVO;
                motivo = RESOURCES.getString(mayoralMinimo);
            } else if (maximo < reorden && reorden > 0) {
                valid = Constantes.INACTIVO;
                motivo = RESOURCES.getString(maximoMayorReorden);
            }
        } else if (dotacion < 0) {
            valid = Constantes.INACTIVO;
            motivo = RESOURCES.getString("almacControl.warn.maximoDotacion");
        }
        return valid;
    }

    private boolean agregarActualizar() {
        boolean pross = Constantes.INACTIVO;
        try {
            AlmacenControl almacenCtrl = new AlmacenControl();
            almacenCtrl.setIdAlmacen(idAlmacen);
            almacenCtrl.setIdMedicamento(insumoList.get(0).getIdMedicamento());
            almacenCtrl.setActivo(status);
            //Verificamos que no se haya agregado antes
            almacenCtrl = almacenCtrlService.obtener(almacenCtrl);

            if (presentacion > 0) {
                int factor = insumoList.get(0).getFactorTransformacion();
                if (pointCtrl) {
                    minimo = minimo * factor;
                    reorden = reorden * factor;
                    maximo = maximo * factor;
                } else {
                    dotacion = dotacion * factor;
                }
            }

            almacenCtrlSelect = new AlmacenControl();
            almacenCtrlSelect.setInsumo(insumoList.get(0).getNombreCorto());

            almacenCtrlSelect.setIdAlmacen(idAlmacen);
            almacenCtrlSelect.setIdMedicamento(insumoList.get(0).getIdMedicamento());
            if (pointCtrl) {
                almacenCtrlSelect.setMinimo(minimo);
                almacenCtrlSelect.setReorden(reorden);
                almacenCtrlSelect.setMaximo(maximo);
            } else {
                almacenCtrlSelect.setDotacion(dotacion);
            }
            almacenCtrlSelect.setActivo(status);
            almacenCtrlSelect.setSolicitud(0);
            almacenCtrlSelect.setEstatusGabinete(EstatusGabinete_Enum.PENDIENTE.getValue());
            almacenCtrlSelect.setClaveInstitucional(insumoList.get(0).getClaveInstitucional());

            if (almacenCtrl == null) {
                almacenCtrlSelect.setIdAlmacenPuntosControl(Comunes.getUUID());
                almacenCtrlSelect.setInsertIdUsuario(currentUser.getIdUsuario());
                almacenCtrlSelect.setInsertFecha(new Date());
                pross = almacenCtrlService.insertar(almacenCtrlSelect);
                motivo = RESOURCES.getString("almacControl.info.proccessSuccessful");

            } else {
                almacenCtrlSelect.setIdAlmacenPuntosControl(almacenCtrl.getIdAlmacenPuntosControl());
                almacenCtrlSelect.setUpdateIdUsuario(currentUser.getIdUsuario());
                almacenCtrlSelect.setUpdateFecha(new Date());
                pross = almacenCtrlService.actualizar(almacenCtrlSelect);
                motivo = RESOURCES.getString("almacControl.info.updateSuccessful");
            }
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al guardar:", ex);
            motivo = RESOURCES.getString("almacControl.err.notSuccessful");
            pross = Constantes.INACTIVO;
        }
        return pross;
    }
// <editor-fold defaultstate="collapsed" desc="GETTER & SETTER" >        
    
    public void setEstructuraService(EstructuraService estructuraService) {
        this.estructuraService = estructuraService;
    }

    public EstructuraService getEstructuraService() {
        return estructuraService;
    }

    public void setEstructuraSelect(Estructura estructuraSelect) {
        this.estructuraSelect = estructuraSelect;
    }

    public Estructura getEstructuraSelect() {
        return estructuraSelect;
    }

    public List<Estructura> getEstructuraList() {
        return estructuraList;
    }

    public void setEstructuraList(List<Estructura> estructuraList) {
        this.estructuraList = estructuraList;
    }

    public List<Estructura> getEstructuraAux() {
        return estructuraAux;
    }    

    public Pattern getRegexNumber() {
        return regexNumber;
    }

    public void setRegexNumber(Pattern regexNumber) {
        this.regexNumber = regexNumber;
    }

    public void setEstructuraAux(List<Estructura> estructuraAux) {
        this.estructuraAux = estructuraAux;
    }

    public int getActiveRow() {
        return activeRow;
    }

    public void setActiveRow(int activeRow) {
        this.activeRow = activeRow;
    }

    public List<AlmacenControl> getAlmacenCtrlList() {
        return almacenCtrlList;
    }

    public void setAlmacenCtrlList(List<AlmacenControl> almacenCtrlList) {
        this.almacenCtrlList = almacenCtrlList;
    }

    public int getMinimo() {
        return minimo;
    }

    public void setMinimo(int minimo) {
        this.minimo = minimo;
    }

    public int getReorden() {
        return reorden;
    }

    public void setReorden(int reorden) {
        this.reorden = reorden;
    }

    public int getMaximo() {
        return maximo;
    }

    public void setMaximo(int maximo) {
        this.maximo = maximo;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public List<Almacen> getAlmacenList() {
        return almacenList;
    }

    public void setAlmacenList(List<Almacen> almacenList) {
        this.almacenList = almacenList;
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

    public AlmacenService getAlmacenService() {
        return almacenService;
    }

    public void setAlmacenService(AlmacenService almacenService) {
        this.almacenService = almacenService;
    }

    public Almacen getAlmacenSelect() {
        return almacenSelect;
    }

    public void setAlmacenSelect(Almacen almacenSelect) {
        this.almacenSelect = almacenSelect;
    }

    public AlmacenControlService getAlmacenCtrlService() {
        return almacenCtrlService;
    }

    public void setAlmacenCtrlService(AlmacenControlService almacenCtrlService) {
        this.almacenCtrlService = almacenCtrlService;
    }

    public AlmacenControl getAlmacenCtrlSelect() {
        return almacenCtrlSelect;
    }

    public void setAlmacenCtrlSelect(AlmacenControl almacenCtrlSelect) {
        this.almacenCtrlSelect = almacenCtrlSelect;
    }
    
    public boolean isDotacionDiaria() {
        return dotacionDiaria;
    }

    public void setDotacionDiaria(boolean dotacionDiaria) {
        this.dotacionDiaria = dotacionDiaria;
    }

    public int getDotacion() {
        return dotacion;
    }

    public void setDotacion(int dotacion) {
        this.dotacion = dotacion;
    }

    public boolean isNoProcess() {
        return noProcess;
    }

    public void setNoProcess(boolean noProcess) {
        this.noProcess = noProcess;
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

    public List<AlmacenControl_Extended> getAlmacenCtrlLayoutList() {
        return almacenCtrlLayoutList;
    }

    public void setAlmacenCtrlLayoutList(List<AlmacenControl_Extended> almacenCtrlLayoutList) {
        this.almacenCtrlLayoutList = almacenCtrlLayoutList;
    }

    public boolean isPointCtrl() {
        return pointCtrl;
    }

    public void setPointCtrl(boolean pointCtrl) {
        this.pointCtrl = pointCtrl;
    }    

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }
    
    // </editor-fold>
}
