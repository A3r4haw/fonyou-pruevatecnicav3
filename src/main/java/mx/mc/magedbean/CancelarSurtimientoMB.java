/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.magedbean;

import com.ibm.icu.util.Calendar;
import com.lowagie.text.pdf.PdfCopyFields;
import com.lowagie.text.pdf.PdfReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import mx.mc.enums.EstatusPaciente_Enum;
import mx.mc.enums.EstatusPrescripcion_Enum;
import mx.mc.enums.EstatusSurtimiento_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.Estructura;
import mx.mc.model.InventarioExtended;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.Surtimiento;
import mx.mc.model.SurtimientoEnviado;
import mx.mc.model.SurtimientoInsumo_Extend;
import mx.mc.model.Surtimiento_Extend;
import mx.mc.model.Usuario;
import mx.mc.service.EstructuraService;
import mx.mc.service.InventarioService;
import mx.mc.service.PrescripcionService;
import mx.mc.service.ReportesService;
import mx.mc.service.SurtimientoInsumoService;
import mx.mc.service.SurtimientoService;
import mx.mc.util.Comunes;
import mx.mc.util.Mensaje;
import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author bbautista
 */
@Controller
@Scope(value = "view")
public class CancelarSurtimientoMB implements Serializable {
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(SurtPrescripcionExtMB.class);    
    private String pathTmp;
    private String pathTmp2;
    private File dirTmp;
    private String urlf;
    private boolean editable;
    private Pattern regexNumber;
    private String cadenaBusqueda;
    private String archivo;
    private String archivoVale;
    private String rutaPdfs;
    private String folio;
    private static PdfReader reader1;
    private static PdfReader reader2;
    private Date fechaActual;
    private int wSProcesado;    
    private String errTransaccion;
    private String surIncorrecto;
    private String surSinAlmacen;
    private String errRegistroIncorrecto;
    private PermisoUsuario permiso;
    private Integer horasCancelacion;

    private List<String> tipoPrescripcionSelectedList;
    private List<InventarioExtended> inventarioBloqueadoList;

    private Surtimiento surtimientoSelect;
    private Surtimiento_Extend surtimientoExtendSelected;
    private List<Surtimiento_Extend> surtimientoExtendedList;
    private List<Surtimiento_Extend> filterSurtimientoList;

    private List<SurtimientoEnviado> surtimientoEnviadoList;

    private SurtimientoInsumo_Extend surtimientoInsumoExtendedSelected;
    private List<SurtimientoInsumo_Extend> surtimientoInsumoExtendedList;

    private Usuario currentSesionUser;

    @Autowired
    private transient ReportesService reportesService;

    @Autowired
    private transient InventarioService inventarioService;
    @Autowired
    private transient EstructuraService estructuraService;
    private List<Estructura> listServiciosQueSurte;
    @Autowired
    private transient PrescripcionService prescripcionService;   
    @Autowired
    private transient SurtimientoInsumoService surtimientoInsumoService;
    @Autowired
    private transient SurtimientoService surtimientoService;

   

    /**
     * Consulta los permisos del usuario y obtiene lospacientes registrados
     */
    @PostConstruct
    public void init() {
        LOGGER.debug("mx.mc.magedbean.CancelarSurtimientoMB.init()");
        errTransaccion = "err.transaccion";
        surIncorrecto = "sur.incorrecto";
        surSinAlmacen = "sur.sin.almacen";
        errRegistroIncorrecto = "err.registro.incorrecto";

        regexNumber = Constantes.regexNumber;
        limpia();
        permiso = Comunes.obtenerPermisos(Transaccion_Enum.CANCELSURTIMIENTO.getSufijo());
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        
        obtieneServiciosQuePuedeSurtir();
        horasCancelacion = sesion.getHorasCancelacion();
        obtenerSurtimientos();                       
        dirTmp = new File(Comunes.getPath());
    }

    public void obtenerSurtimientos() {
        LOGGER.debug("mx.mc.magedbean.CancelarSurtimientoMB.obtenerSurtimientos()");
        boolean sigue = true;
        if (cadenaBusqueda != null) {
            sigue = false;
        }
        boolean status = Constantes.ACTIVO;

        if (sigue) {
            surtimientoExtendedList = new ArrayList<>();

            if (!permiso.isPuedeVer()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errTransaccion), null);
                status = Constantes.ACTIVO;
            } else {
                try {
                    // regla: Listar prescripciones donde el paciente tiene estatus: Asignado a Servicio o Asignación a Cama
                    List<Integer> listaEstatusPaciente = new ArrayList<>();
                    listaEstatusPaciente.add(EstatusPaciente_Enum.ASIGNADO_A_SERVICIO.getValue());

                    // regla: Listar prescripciones solo con estatus de PROGRAMADA
                    List<Integer> listaEstatusPrescripcion = new ArrayList<>();
                    listaEstatusPrescripcion.add(EstatusPrescripcion_Enum.FINALIZADA.getValue());

                    // regla: Listar prescripciones solo con estatus de surtimiento programado
                    List<Integer> listaEstatusSurtimiento = new ArrayList<>();
                    listaEstatusSurtimiento.add(EstatusSurtimiento_Enum.SURTIDO.getValue());
                    listaEstatusSurtimiento.add(EstatusSurtimiento_Enum.SUTIDO_PARCIAL.getValue());
                    listaEstatusSurtimiento.add(EstatusSurtimiento_Enum.CANCELADO.getValue());

                    // regla: filtro de tipo de prescripción: Normal, Dósis Única, Normal o todas
                    if (tipoPrescripcionSelectedList != null && tipoPrescripcionSelectedList.isEmpty()) {
                        tipoPrescripcionSelectedList = null;
                    }

                    if (listServiciosQueSurte.isEmpty()) {
                        this.listServiciosQueSurte = null;
                    }
                    // regla: listar prescripciones con fecha igual o mayor al resultado de la fecha actual menos las horas de cancelacion 
                    Date fechaProgramada = new java.util.Date();

                    Calendar cal = Calendar.getInstance();
                    cal.setTime(fechaProgramada);

                    cal.set(Calendar.HOUR, cal.get(Calendar.HOUR) - horasCancelacion);
                    fechaProgramada = cal.getTime();
                    // TODO:    agregar reglas de Negocio
                    // 1.- El surtimiento solo funciona en Almacenes (CEDIME, CEDIMAT, Farmacia Externa) y subalmacenes (Gabinetes o sin sistema)
                    // 2.- Solo surten los insumos que tienen activos
                    // 3.- CEDIME y CEDIMAT solo surten a Consulta Interna
                    // 4.- 
                    surtimientoExtendedList
                            .addAll(
                                    surtimientoService
                                            .obtenerPorFechaEstructuraPacienteCamaPrescripcionCancelacion(
                                                    fechaProgramada, cadenaBusqueda, tipoPrescripcionSelectedList, listaEstatusPaciente, listaEstatusPrescripcion, listaEstatusSurtimiento, listServiciosQueSurte)
                            );
                    status = Constantes.ACTIVO;
                } catch (Exception ex) {
                    LOGGER.error(RESOURCES.getString("prc.pac.lista"), ex);
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.pac.lista"), null);
                }
            }
        }
        cadenaBusqueda = "";
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public void verSurtimientoEscaneado() {
        LOGGER.debug("mx.mc.magedbean.CancelarSurtimientoMB.verSurtimientoEscaneado()");

        boolean modal = Constantes.ACTIVO;

        try {
            List<Integer> estatusPrescripcionList = new ArrayList<>();
            estatusPrescripcionList.add(EstatusPrescripcion_Enum.PROGRAMADA.getValue());
            List<Integer> estatusSurtimientoList = new ArrayList<>();
            estatusSurtimientoList.add(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
            List<Integer> estatusPacienteList = new ArrayList<>();
            estatusPacienteList.add(EstatusPaciente_Enum.ASIGNADO_A_SERVICIO.getValue());

            List<Surtimiento_Extend> listSurtimientos = surtimientoService
                    .obtenerPorFechaEstructuraPacienteCamaPrescripcion(
                            new Date(), cadenaBusqueda,
                            tipoPrescripcionSelectedList,
                            estatusPacienteList, estatusPrescripcionList,
                            estatusSurtimientoList, listServiciosQueSurte);
            if (listSurtimientos == null || listSurtimientos.isEmpty()) {
                surtimientoExtendSelected = null;
            } else {
                surtimientoExtendSelected = listSurtimientos.get(0);
            }
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString(surIncorrecto), ex);
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surIncorrecto), null);
        }

        if (!permiso.isPuedeVer()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("canceSurt.err.noRegistros"), null);

        } else if (currentSesionUser == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surSinAlmacen), null);

        } else {
            if (surtimientoExtendSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);
                modal = Constantes.INACTIVO;
            } else if (surtimientoExtendSelected.getIdPrescripcion() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);

            } else if (surtimientoExtendSelected.getIdSurtimiento() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);

            } else if (surtimientoExtendSelected.getIdEstatusSurtimiento() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);

            } else {

                try {
                    Date fechaProgram = new java.util.Date();
                    String idSurtimiento = surtimientoExtendSelected.getIdSurtimiento();
                    String idPrescripcion = surtimientoExtendSelected.getIdPrescripcion();
                    List<Integer> listEstatSurtimientoInsumo = new ArrayList<>();
                    listEstatSurtimientoInsumo.add(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                    List<Integer> listEstatSurtimiento = new ArrayList<>();
                    listEstatSurtimiento.add(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                    String idEstructura = currentSesionUser.getIdEstructura();

                    boolean surtimientoMixto = false;

                    surtimientoInsumoExtendedList = new ArrayList<>();
                    surtimientoInsumoExtendedList
                            .addAll(
                                    surtimientoInsumoService
                                            .obtenerSurtimientosProgramados(fechaProgram, idSurtimiento, idPrescripcion, listEstatSurtimientoInsumo, listEstatSurtimiento, idEstructura, surtimientoMixto)
                            );

                    if (!surtimientoInsumoExtendedList.isEmpty()) {
                        buscaMedicmanetosBloqueados();
                    }

                } catch (Exception ex) {
                    LOGGER.error(RESOURCES.getString(surIncorrecto), ex);
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surIncorrecto), null);
                }
            }
        }
        PrimeFaces.current().ajax().addCallbackParam("modal", modal);
    }

    public void reimprimirTicket(Surtimiento_Extend itemSelect) {
        boolean estatus = Constantes.INACTIVO;

        if (permiso.isPuedeVer()) {
            if (permiso.isPuedeProcesar()) {
                if (folio.equals("") && itemSelect.getFolio().length() > 0) {
                    surtimientoExtendSelected = itemSelect;
                } else {
                    surtimientoExtendedList.stream().filter(prdct -> prdct.getFolio().equals(folio)).forEach(cnsmr -> 
                        surtimientoExtendSelected = cnsmr
                    );
                }
                folio = "";
                obtenerInsumos();
                estatus = enviarSurtimiento();
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errTransaccion), null);
            }
        } else {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errTransaccion), null);
        }

        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, estatus);
    }

    public void cancelaSurtimiento(Surtimiento_Extend itemSelect) {
        boolean res = Constantes.INACTIVO;
        try {
            if (permiso.isPuedeVer()) {
                if (permiso.isPuedeEliminar()) {
                    if (folio.equals("") && itemSelect.getFolio().length() > 0) {
                        folio = itemSelect.getFolio();
                    }
                    //Consultar surtimiento Procesado            
                    surtimientoSelect = surtimientoService.obtenerPorFolio(folio);
                    //Actualizar el campo procesado del surtimiento
                    surtimientoService.actualizarPorFolio(surtimientoSelect.getFolio(), EstatusPrescripcion_Enum.CANCELADA.getValue());
                    //obtener Surtimiento Insumo Enviado
                    surtimientoEnviadoList = surtimientoService.obtenerListaSurtimientoEnviadoPorIdSurtimiento(this.surtimientoSelect.getIdSurtimiento());
                    //Revertir Inventario
                    res = inventarioService.revertirInventario(surtimientoSelect, surtimientoEnviadoList, currentSesionUser);
                    if (res && surtimientoSelect.getProcesado() > 0) {
                        String newFolio = "S" + surtimientoSelect.getFolio();
                        res = surtimientoService.clonarSurtimiento(surtimientoSelect.getIdSurtimiento(), newFolio);
                        if (res) {
                            surtimientoInsumoService.clonarSurtimientoInsumo(surtimientoSelect.getIdSurtimiento(), newFolio);
                        }
                    }
                    //Actualizar el campo procesado del surtimiento
                    surtimientoService.actualizarPorFolio(surtimientoSelect.getFolio(), EstatusSurtimiento_Enum.CANCELADO.getValue());

                    folio = "";
                    //revomover de la lista de Cancelaciones / reimpresion
                    surtimientoExtendedList.stream().filter(prdct -> prdct.getFolio().equals(surtimientoSelect.getFolio())).forEach(cnsmr -> 
                        surtimientoExtendedList.remove(cnsmr)
                    );
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errTransaccion), null);
                }
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errTransaccion), null);
            }
        } catch (Exception ex) {
            LOGGER.error("Error al Cancelar el Surtimiento. {}", ex.getMessage());
        }
    }
    
    private void obtieneServiciosQuePuedeSurtir() {
        LOGGER.debug("mx.mc.magedbean.CancelarSurtimientoMB.obtieneServiciosQuePuedeSurtir()");
        listServiciosQueSurte = new ArrayList<>();
        Estructura est = null;
        try {
            est = estructuraService.obtener(new Estructura(currentSesionUser.getIdEstructura()));

        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        if (est == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surSinAlmacen), null);

        } else if (est.getIdTipoAreaEstructura() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surSinAlmacen), null);
        } else {
            try {
                if (currentSesionUser.getIdEstructura() != null) {
                    listServiciosQueSurte.addAll(estructuraService.obtenerServiciosAlmcenPorIdEstructura(currentSesionUser.getIdEstructura()));
                }
            } catch (Exception ex) {
                LOGGER.error("Error al obtener Servicios que puede surtir el usuario. {}", ex.getMessage());
            }
        }
    }

    private void limpia() {
        LOGGER.debug("mx.mc.magedbean.CancelarSurtimientoMB.limpia()");
        cadenaBusqueda = null;
        fechaActual = new java.util.Date();
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        currentSesionUser = sesion.getUsuarioSelected();
        surtimientoInsumoExtendedList = new ArrayList<>();
        surtimientoExtendSelected = new Surtimiento_Extend();
        tipoPrescripcionSelectedList = new ArrayList<>();
        pathTmp2 = "";
        rutaPdfs = "";
        folio = "";
        archivo = "";
        pathTmp = "";        
    }    
    
    private void obtenerInsumos() {
        try {
            if (surtimientoExtendSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surIncorrecto), null);
            } else {
                Date fechaProgramad = surtimientoExtendSelected.getFechaProgramada();
                String idSurtimiento = surtimientoExtendSelected.getIdSurtimiento();
                String idPrescripcion = surtimientoExtendSelected.getIdPrescripcion();
                List<Integer> listEstSurtimientoInsumo = new ArrayList<>();
                listEstSurtimientoInsumo.add(EstatusSurtimiento_Enum.SURTIDO.getValue());
                List<Integer> listEstatusSurtimiento = new ArrayList<>();
                listEstatusSurtimiento.add(EstatusSurtimiento_Enum.SURTIDO.getValue());
                String idEstructura = currentSesionUser.getIdEstructura();

                boolean surtimientoMixto = false;
                surtimientoInsumoExtendedList
                        .addAll(
                                surtimientoInsumoService
                                        .obtenerSurtimientosProgramados(fechaProgramad, idSurtimiento, idPrescripcion, listEstSurtimientoInsumo, listEstatusSurtimiento, idEstructura, surtimientoMixto)
                        );
            }
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString(surIncorrecto), ex);
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surIncorrecto), null);
        }

    }
    
    private void buscaMedicmanetosBloqueados() {
        LOGGER.trace("mx.mc.magedbean.CancelarSurtimientoMB.buscaMedicmanetosBloqueados()");
        try {
            List<String> idInsumoLista = new ArrayList<>();
            for (SurtimientoInsumo_Extend item : surtimientoInsumoExtendedList) {
                idInsumoLista.add(item.getIdInsumo());
            }
            inventarioBloqueadoList = inventarioService.obtenerListaInactivosByIdInsumos(idInsumoLista);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }

    }

    
    private void limpiaVariables() {
        pathTmp2 = "";
        rutaPdfs = "";
        folio = "";
        archivo = "";
        pathTmp = "";        
    }
    
   

    private boolean enviarSurtimiento() {
        LOGGER.trace("mx.mc.magedbean.CancelarSurtimientoMB.enviarSurtimiento()");
        boolean res = Constantes.INACTIVO;
        int vale = 0;
        try {

            for (SurtimientoInsumo_Extend item : surtimientoInsumoExtendedList) {

                if (item.getCantidadVale() != null && item.getCantidadVale() > 0) {
                    vale++;
                }
            }
            surtimientoExtendSelected.setIdEstructuraAlmacen(currentSesionUser.getIdEstructura());
            surtimientoExtendSelected.setUpdateFecha(new java.util.Date());
            surtimientoExtendSelected.setUpdateIdUsuario(currentSesionUser.getIdUsuario());
            surtimientoExtendSelected.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.SURTIDO.getValue());

            limpiaVariables();

            res = imprimirTcketVale(surtimientoExtendSelected, currentSesionUser.getNombreUsuario(), true);

            if (vale > 0) {
                res = imprimirTcketVale(surtimientoExtendSelected, currentSesionUser.getNombreUsuario(), false);
            }
            if (archivo.length() > 0) {
                String ticket = (System.getProperty("os.name").toLowerCase().contains("windows")) ? "\\" : "/";
                rutaPdfs = dirTmp.getPath() + ticket + surtimientoExtendSelected.getFolio() + ".pdf";
                reader1 = new PdfReader(pathTmp);
                if (pathTmp2.length() > 0) {
                    reader2 = new PdfReader(pathTmp2);
                }
                PdfCopyFields copy = new PdfCopyFields(new FileOutputStream(rutaPdfs));
                copy.addDocument(reader1);

                if (pathTmp2.length() > 0) {
                    copy.addDocument(reader2);
                }
                copy.close();
                archivo = urlf + "/resources/tmp/" + surtimientoExtendSelected.getFolio() + ".pdf";
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo enviarSurtimiento :: {}", e.getMessage());
        }
        return res;
    }

    private boolean imprimirTcketVale(Surtimiento_Extend surtimientoExtendSelected, String nombreUsuario, boolean ticket) throws Exception {
        LOGGER.debug("mx.mc.magedbean.CancelarSurtimientoMB.imprimir()");
        boolean status = Constantes.INACTIVO;
        try {
            String rutaTicket = (System.getProperty("os.name").toLowerCase().contains("windows")) ? "\\" : "/";
            if (ticket) {
                pathTmp = dirTmp.getPath() + rutaTicket + surtimientoExtendSelected.getFolio() + "T.pdf";
            } else {
                pathTmp2 = dirTmp.getPath() + rutaTicket + surtimientoExtendSelected.getFolio() + "V.pdf";
            }
            FacesContext ctxt = FacesContext.getCurrentInstance();
            ExternalContext ext = ctxt.getExternalContext();
            URI uri = new URI(ext.getRequestScheme(),
                    null,
                    ext.getRequestServerName(),
                    ext.getRequestServerPort(),
                    ext.getRequestContextPath(),
                    null,
                    null);
            String url = uri.toASCIIString();
            urlf = url;

            if (ticket) {
                status = reportesService.imprimeSurtimientoPrescExt(surtimientoExtendSelected, nombreUsuario, pathTmp, url);
            } else {
                status = reportesService.imprimeSurtimientoVales(surtimientoExtendSelected, nombreUsuario, pathTmp2, url);
            }

            if (ticket && status) {
                archivo = url + "/resources/tmp/ticket" + surtimientoExtendSelected.getFolio() + ".pdf";
            }
        } catch (Exception e) {
            LOGGER.error("Error al generar la Impresión. {}", e.getMessage());
            status = Constantes.INACTIVO;
        }

        return status;

    }
    //***************************************************************************  

   
    public File getDirTmp() {
        return dirTmp;
    }

    public void setDirTmp(File dirTmp) {
        this.dirTmp = dirTmp;
    }
    
    public String getPathTmp2() {
        return pathTmp2;
    }

    public void setPathTmp2(String pathTmp2) {
        this.pathTmp2 = pathTmp2;
    }

    public PdfReader getReader1() {
        return reader1;
    }

    public void setReader1(PdfReader reader1) {
        this.reader1 = reader1;
    }    

    public String getUrlf() {
        return urlf;
    }

    public void setUrlf(String urlf) {
        this.urlf = urlf;
    }
    
    public String getArchivoVale() {
        return archivoVale;
    }

    public void setArchivoVale(String archivoVale) {
        this.archivoVale = archivoVale;
    }

    public String getRutaPdfs() {
        return rutaPdfs;
    }

    public void setRutaPdfs(String rutaPdfs) {
        this.rutaPdfs = rutaPdfs;
    }   

    public List<String> getTipoPrescripcionSelectedList() {
        return tipoPrescripcionSelectedList;
    }

    public void setTipoPrescripcionSelectedList(List<String> tipoPrescripcionSelectedList) {
        this.tipoPrescripcionSelectedList = tipoPrescripcionSelectedList;
    }

    public List<InventarioExtended> getInventarioBloqueadoList() {
        return inventarioBloqueadoList;
    }

    public void setInventarioBloqueadoList(List<InventarioExtended> inventarioBloqueadoList) {
        this.inventarioBloqueadoList = inventarioBloqueadoList;
    }

    public Surtimiento_Extend getSurtimientoExtendSelected() {
        return surtimientoExtendSelected;
    }

    public void setSurtimientoExtendSelected(Surtimiento_Extend surtimientoExtendSelected) {
        this.surtimientoExtendSelected = surtimientoExtendSelected;
    }

    public Integer getSizeSurtimientoExtendList() {
        if (surtimientoExtendedList != null) {
            return surtimientoExtendedList.size();
        } else {
            return 0;
        }
    }

    public List<Surtimiento_Extend> getSurtimientoExtendedList() {
        return surtimientoExtendedList;
    }

    public void setSurtimientoExtendedList(List<Surtimiento_Extend> surtimientoExtendedList) {
        this.surtimientoExtendedList = surtimientoExtendedList;
    }
    
    public SurtimientoInsumo_Extend getSurtimientoInsumoExtendedSelected() {
        return surtimientoInsumoExtendedSelected;
    }

    public void setSurtimientoInsumoExtendedSelected(SurtimientoInsumo_Extend surtimientoInsumoExtendedSelected) {
        this.surtimientoInsumoExtendedSelected = surtimientoInsumoExtendedSelected;
    }
    
     public String getPathTmp() {
        return pathTmp;
    }

    public void setPathTmp(String pathTmp) {
        this.pathTmp = pathTmp;
    }
    
    public PdfReader getReader2() {
        return reader2;
    }

    public void setReader2(PdfReader reader2) {
        this.reader2 = reader2;
    }

    public List<SurtimientoInsumo_Extend> getSurtimientoInsumoExtendedList() {
        return surtimientoInsumoExtendedList;
    }

    public void setSurtimientoInsumoExtendedList(List<SurtimientoInsumo_Extend> surtimientoInsumoExtendedList) {
        this.surtimientoInsumoExtendedList = surtimientoInsumoExtendedList;
    }  

    public List<Estructura> getListServiciosQueSurte() {
        return listServiciosQueSurte;
    }

    public void setListServiciosQueSurte(List<Estructura> listServiciosQueSurte) {
        this.listServiciosQueSurte = listServiciosQueSurte;
    }

    public PrescripcionService getPrescripcionService() {
        return prescripcionService;
    }

    public void setPrescripcionService(PrescripcionService prescripcionService) {
        this.prescripcionService = prescripcionService;
    }
    
    public Usuario getCurrentSesionUser() {
        return currentSesionUser;
    }

    public void setCurrentSesionUser(Usuario currentSesionUser) {
        this.currentSesionUser = currentSesionUser;
    }

    public EstructuraService getEstructuraService() {
        return estructuraService;
    }

    public void setEstructuraService(EstructuraService estructuraService) {
        this.estructuraService = estructuraService;
    }
    
    public SurtimientoService getSurtimientoService() {
        return surtimientoService;
    }

    public void setSurtimientoService(SurtimientoService surtimientoService) {
        this.surtimientoService = surtimientoService;
    }

    public InventarioService getInventarioService() {
        return inventarioService;
    }

    public void setInventarioService(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }
    
    public SurtimientoInsumoService getSurtimientoInsumoService() {
        return surtimientoInsumoService;
    }

    public void setSurtimientoInsumoService(SurtimientoInsumoService surtimientoInsumoService) {
        this.surtimientoInsumoService = surtimientoInsumoService;
    }

    public List<Surtimiento_Extend> getFilterSurtimientoList() {
        return filterSurtimientoList;
    }

    public void setFilterSurtimientoList(List<Surtimiento_Extend> filterSurtimientoList) {
        this.filterSurtimientoList = filterSurtimientoList;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }
    
     
    public Integer getHorasCancelacion() {
        return horasCancelacion;
    }

    public void setHorasCancelacion(Integer horasCancelacion) {
        this.horasCancelacion = horasCancelacion;
    }

    public int getwSProcesado() {
        return wSProcesado;
    }

    public void setwSProcesado(int wSProcesado) {
        this.wSProcesado = wSProcesado;
    }

    public Surtimiento getSurtimientoSelect() {
        return surtimientoSelect;
    }

    public void setSurtimientoSelect(Surtimiento surtimientoSelect) {
        this.surtimientoSelect = surtimientoSelect;
    }

    public ReportesService getReportesService() {
        return reportesService;
    }

    public void setReportesService(ReportesService reportesService) {
        this.reportesService = reportesService;
    }
    
    
    public List<SurtimientoEnviado> getSurtimientoEnviadoList() {
        return surtimientoEnviadoList;
    }

    public void setSurtimientoEnviadoList(List<SurtimientoEnviado> surtimientoEnviadoList) {
        this.surtimientoEnviadoList = surtimientoEnviadoList;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public Pattern getRegexNumber() {
        return regexNumber;
    }

    public void setRegexNumber(Pattern regexNumber) {
        this.regexNumber = regexNumber;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }
    
    public String getCadenaBusqueda() {
        return cadenaBusqueda;
    }

    public void setCadenaBusqueda(String cadenaBusqueda) {
        this.cadenaBusqueda = cadenaBusqueda;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }
   
}
