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
import java.math.BigInteger;
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
import mx.mc.enums.EstatusGabinete_Enum;
import mx.mc.enums.EstatusPaciente_Enum;
import mx.mc.enums.EstatusPrescripcion_Enum;
import mx.mc.enums.EstatusSurtimiento_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.Estructura;
import mx.mc.model.InventarioExtended;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.Prescripcion;
import mx.mc.model.Surtimiento;
import mx.mc.model.SurtimientoEnviado;
import mx.mc.model.SurtimientoInsumo_Extend;
import mx.mc.model.Surtimiento_Extend;
import mx.mc.model.Usuario;
import mx.mc.service.EntidadHospitalariaService;
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
 * @author aortiz
 */
@Controller
@Scope(value = "view")
public class CancelarSurtimientoIntMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private static final Logger LOGGER = LoggerFactory.getLogger(SurtPrescripcionExtMB.class);   
    private boolean editable;
    private Pattern regexNumber;
    private String folio;
    private Date fechaActual;
    private int wSProcesado;
    private String cadenaBusqueda;   
    private String rutaPdfs;
    private String pathTmp;
    private String archivo;
    private String archivoVale;    
    private String pathTmp2;
    private static PdfReader reader1;
    private static PdfReader reader2;
    private File dirTmp;
    private String urlf;    
    private Usuario currentSesionUser;
    private boolean permiteCancelarSurtimiento;
    private String errTransaccion;
    private String surIncorrecto;
    private String surSinAlmacen;
    private String registroIncorrecto;
    private String prcNopuedecancelar;
    private PermisoUsuario permiso;
    private Integer horasCancelacion;

    private List<String> tipoPrescripcionSelectedList;
    private List<InventarioExtended> inventarioBloqueadoList;

    private Surtimiento surtimientoSelect;
    private Surtimiento_Extend surtimientoExtendedSelected;
    private Prescripcion prescripcionSelected;
    private List<Surtimiento_Extend> surtimientoExtendedList;
    private List<Surtimiento_Extend> filterSurtimientoList;

    private List<SurtimientoEnviado> surtimientoEnviadoList;

    private SurtimientoInsumo_Extend surtimientoInsumoExtendedSelected;
    private List<SurtimientoInsumo_Extend> surtimientoInsumoExtendedList;
    @Autowired
    private transient SurtimientoService surtimientoService;
    @Autowired
    private transient SurtimientoInsumoService surtimientoInsumoService;
    @Autowired
    private transient EstructuraService estructuraService;
    private List<Estructura> listServiciosQueSurte;
    @Autowired
    private transient PrescripcionService prescripcionService;    
    @Autowired
    private transient InventarioService inventarioService;
    @Autowired
    private transient EntidadHospitalariaService entidadHospitalariaService;
    @Autowired
    private transient ReportesService reportesService;

    /**
     * Consulta los permisos del usuario y obtiene lospacientes registrados
     */
    @PostConstruct
    public void init() {
        LOGGER.debug("mx.mc.magedbean.DispensacionMB.init()");
        errTransaccion = "err.transaccion";
        surIncorrecto = "sur.incorrecto";
        surSinAlmacen = "sur.sin.almacen";
        registroIncorrecto = "err.registro.incorrecto";
        prcNopuedecancelar = "prc.nopuedecancelar";

        this.regexNumber = Constantes.regexNumber;
        this.currentSesionUser = Comunes.obtenerUsuarioSesion();
        limpia();
        permiso = Comunes.obtenerPermisos(Transaccion_Enum.CANCELSURTIMIENTO.getSufijo());
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        this.horasCancelacion = sesion.getHorasCancelacion();
        this.permiteCancelarSurtimiento = sesion.isPermiteCancelarSurtimiento();
        obtieneServiciosQuePuedeSurtir();
        obtenerSurtimientos();       
        dirTmp = new File(Comunes.getPath());
    }

    public void obtenerSurtimientos() {
        LOGGER.debug("mx.mc.magedbean.DispensacionMB.obtenerSurtimientos()");
        boolean sigue = true;
        if (this.cadenaBusqueda != null) {
            sigue = false;
        }
        boolean status = Constantes.ACTIVO;

        if (sigue) {
            this.surtimientoExtendedList = new ArrayList<>();

            if (!this.permiso.isPuedeVer()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errTransaccion), null);
                status = Constantes.ACTIVO;

            } else {
                try {
                    // regla: Listar prescripciones donde el paciente tiene estatus: Asignado a Servicio o Asignación a Cama
                    List<Integer> lisEstatusPaciente = new ArrayList<>();
                    lisEstatusPaciente.add(EstatusPaciente_Enum.ASIGNADO_A_SERVICIO.getValue());

                    // regla: Listar prescripciones solo con estatus de PROGRAMADA
                    List<Integer> lisEstatusPrescripcion = new ArrayList<>();
                    lisEstatusPrescripcion.add(EstatusPrescripcion_Enum.FINALIZADA.getValue());

                    // regla: Listar prescripciones solo con estatus de surtimiento programado
                    List<Integer> listEstatusSurtimiento = new ArrayList<>();
                    listEstatusSurtimiento.add(EstatusSurtimiento_Enum.SURTIDO.getValue());

                    // regla: filtro de tipo de prescripción: Normal, Dósis Única, Normal o todas
                    if (this.tipoPrescripcionSelectedList != null && this.tipoPrescripcionSelectedList.isEmpty()) {
                        this.tipoPrescripcionSelectedList = null;
                    }

                    if (this.listServiciosQueSurte.isEmpty()) {
                        this.listServiciosQueSurte = null;
                    }
                    // regla: listar prescripciones con fecha igual o mayor al resultado de la fecha actual menos las horas de cancelacion 
                    Date fechaProgramada = new java.util.Date();

                    Calendar cal = Calendar.getInstance();
                    cal.setTime(fechaProgramada);

                    cal.set(Calendar.HOUR, cal.get(Calendar.HOUR) - this.horasCancelacion);
                    fechaProgramada = cal.getTime();
                    // TODO:    agregar reglas de Negocio
                    // 1.- El surtimiento solo funciona en Almacenes (CEDIME, CEDIMAT, Farmacia Externa) y subalmacenes (Gabinetes o sin sistema)
                    // 2.- Solo surten los insumos que tienen activos
                    // 3.- CEDIME y CEDIMAT solo surten a Consulta Interna
                    // 4.- 
                    this.surtimientoExtendedList.addAll(this.surtimientoService.
                            obtenerPorFechaEstructuraPacienteCamaPrescripcionCancelacionInterna(
                                    fechaProgramada, this.cadenaBusqueda, this.tipoPrescripcionSelectedList,
                                    lisEstatusPaciente, lisEstatusPrescripcion,
                                    listEstatusSurtimiento, this.listServiciosQueSurte)
                    );
                    status = Constantes.ACTIVO;
                } catch (Exception ex) {
                    LOGGER.error(RESOURCES.getString("prc.pac.lista"), ex);
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.pac.lista"), null);
                }
            }
        }
        this.cadenaBusqueda = "";
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public void verSurtimientoEscaneado() {
        LOGGER.debug("mx.mc.magedbean.DispensacionMB.verSurtimiento()");

        boolean modal = Constantes.ACTIVO;

        try {
            List<Integer> listEstatusPrescripcion = new ArrayList<>();
            listEstatusPrescripcion.add(EstatusPrescripcion_Enum.PROGRAMADA.getValue());
            List<Integer> listEstatusSurtimiento = new ArrayList<>();
            listEstatusSurtimiento.add(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
            List<Integer> listEstatusPaciente = new ArrayList<>();
            listEstatusPaciente.add(EstatusPaciente_Enum.ASIGNADO_A_SERVICIO.getValue());

            List<Surtimiento_Extend> listaSurtimientos = surtimientoService
                    .obtenerPorFechaEstructuraPacienteCamaPrescripcion(
                            new Date(), cadenaBusqueda,
                            tipoPrescripcionSelectedList,
                            listEstatusPaciente, listEstatusPrescripcion,
                            listEstatusSurtimiento, listServiciosQueSurte);
            if (listaSurtimientos == null || listaSurtimientos.isEmpty()) {
                surtimientoExtendedSelected = null;
            } else {
                surtimientoExtendedSelected = listaSurtimientos.get(0);
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
            if (surtimientoExtendedSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(registroIncorrecto), null);
                modal = Constantes.INACTIVO;
            } else if (surtimientoExtendedSelected.getIdPrescripcion() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(registroIncorrecto), null);

            } else if (surtimientoExtendedSelected.getIdSurtimiento() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(registroIncorrecto), null);

            } else if (surtimientoExtendedSelected.getIdEstatusSurtimiento() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(registroIncorrecto), null);

            } else {

                try {
                    Date fechaProgr = new java.util.Date();
                    String idSurtimiento = surtimientoExtendedSelected.getIdSurtimiento();
                    String idPrescripcion = surtimientoExtendedSelected.getIdPrescripcion();
                    List<Integer> listEstatusSurtimientoInsumo = new ArrayList<>();
                    listEstatusSurtimientoInsumo.add(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                    List<Integer> listEstatusSurtimiento = new ArrayList<>();
                    listEstatusSurtimiento.add(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                    String idEstructura = currentSesionUser.getIdEstructura();

                    boolean surtimientMixto = false;

                    surtimientoInsumoExtendedList = new ArrayList<>();
                    surtimientoInsumoExtendedList
                            .addAll(
                                    surtimientoInsumoService
                                            .obtenerSurtimientosProgramados(fechaProgr, idSurtimiento, idPrescripcion, listEstatusSurtimientoInsumo, listEstatusSurtimiento, idEstructura, surtimientMixto)
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
        boolean status = Constantes.INACTIVO;

        if (this.permiso.isPuedeVer()) {
            if (this.permiso.isPuedeProcesar()) {
                if (this.folio.equals("") && itemSelect.getFolio().length() > 0) {
                    this.surtimientoExtendedSelected = itemSelect;
                } else {
                    this.surtimientoExtendedList.stream().filter(prdct -> prdct.getFolio().equals(this.folio)).forEach(cnsmr -> 
                        this.surtimientoExtendedSelected = cnsmr
                    );
                }
                this.folio = "";
                obtenerInsumos();
                status = enviarSurtimiento();
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errTransaccion), null);
            }
        } else {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errTransaccion), null);
        }

        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public void cancelaSurtimiento(Surtimiento_Extend itemSelect) {
        boolean res = Constantes.INACTIVO;
        try {
            if (this.permiso.isPuedeVer()) {
                if (this.permiso.isPuedeEliminar()) {

                    if (this.folio.equals("") && itemSelect.getFolio().length() > 0) {
                        this.folio = itemSelect.getFolio();
                    }
                    //Consultar surtimiento Procesado            
                    this.surtimientoSelect = this.surtimientoService.obtenerPorFolio(this.folio);
                    //Actualizar el campo procesado del surtimiento
                    this.surtimientoService.actualizarPorFolio(
                            this.surtimientoSelect.getFolio(), EstatusPrescripcion_Enum.CANCELADA.getValue());

                    //obtener Surtimiento Insumo Enviado
                    this.surtimientoEnviadoList = this.surtimientoService.obtenerListaSurtimientoEnviadoPorIdSurtimiento(this.surtimientoSelect.getIdSurtimiento());

                    //Revertir Inventario
                    res = this.inventarioService.revertirInventario(surtimientoSelect, surtimientoEnviadoList, currentSesionUser);
                    if (res && this.surtimientoSelect.getProcesado() > 0) {
                        String newFolio = "S" + this.surtimientoSelect.getFolio();
                        res = surtimientoService.clonarSurtimiento(this.surtimientoSelect.getIdSurtimiento(), newFolio);
                        if (res) {
                            this.surtimientoInsumoService.clonarSurtimientoInsumo(
                                    this.surtimientoSelect.getIdSurtimiento(), newFolio);
                        }
                    }

                    //Actualizar el campo procesado del surtimiento
                    this.surtimientoService.actualizarPorFolio(
                            this.surtimientoSelect.getFolio(), EstatusSurtimiento_Enum.CANCELADO.getValue());

                    this.folio = "";
                    //revomover de la lista de Cancelaciones / reimpresion
                    this.surtimientoExtendedList.stream().filter(prdct -> prdct.getFolio().equals(surtimientoSelect.getFolio())).forEach(cnsmr -> 
                        this.surtimientoExtendedList.remove(cnsmr)
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

    private void limpia() {
        LOGGER.debug("mx.mc.magedbean.DispensacionMB.limpia()");
        archivo = "";
        pathTmp = "";
        pathTmp2 = "";
        rutaPdfs = "";
        folio = "";
        cadenaBusqueda = null;
        fechaActual = new java.util.Date();
        tipoPrescripcionSelectedList = new ArrayList<>();
        surtimientoInsumoExtendedList = new ArrayList<>();
        surtimientoExtendedSelected = new Surtimiento_Extend();
        
    }

    private void obtieneServiciosQuePuedeSurtir() {
        LOGGER.debug("mx.mc.magedbean.DispensacionMB.obtieneServiciosQuePuedeSurtir()");
        this.listServiciosQueSurte = new ArrayList<>();
        Estructura estr = null;
        try {
            estr = estructuraService.obtener(new Estructura(currentSesionUser.getIdEstructura()));
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }

        if (estr == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surSinAlmacen), null);
        } else if (estr.getIdTipoAreaEstructura() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surSinAlmacen), null);
        } else {
            try {
                if (currentSesionUser.getIdEstructura() != null) {
                    listServiciosQueSurte.addAll(estructuraService.obtenerServiciosAlmcenPorIdEstructura(currentSesionUser.getIdEstructura()));
                }
            } catch (Exception e) {
                LOGGER.error("Error al obtener Servicios que puede surtir el usuario. {}", e.getMessage());
            }
        }
    }

    private void obtenerInsumos() {
        try {
            if (surtimientoExtendedSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surIncorrecto), null);
            } else {
                Date fechaProgramada = surtimientoExtendedSelected.getFechaProgramada();
                String idSurtimiento = surtimientoExtendedSelected.getIdSurtimiento();
                String idPrescripcion = surtimientoExtendedSelected.getIdPrescripcion();
                List<Integer> listEstatusSurtimientoInsumo = new ArrayList<>();
                listEstatusSurtimientoInsumo.add(EstatusSurtimiento_Enum.SURTIDO.getValue());
                List<Integer> listEstatusSurtimiento = new ArrayList<>();
                listEstatusSurtimiento.add(EstatusSurtimiento_Enum.SURTIDO.getValue());
                String idEstructura = currentSesionUser.getIdEstructura();

                boolean surtimientoMixto = false;
                surtimientoInsumoExtendedList
                        .addAll(
                                surtimientoInsumoService
                                        .obtenerSurtimientosProgramados(fechaProgramada, idSurtimiento, idPrescripcion, listEstatusSurtimientoInsumo, listEstatusSurtimiento, idEstructura, surtimientoMixto)
                        );
            }
        } catch (Exception e) {
            LOGGER.error(RESOURCES.getString(surIncorrecto), e);
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surIncorrecto), null);
        }

    }

    private void buscaMedicmanetosBloqueados() {
        LOGGER.trace("mx.mc.magedbean.DispensacionMB.buscaMedicmanetosBloqueados()");
        try {
            List<String> idInsumoLis = new ArrayList<>();
            for (SurtimientoInsumo_Extend item : surtimientoInsumoExtendedList) {
                idInsumoLis.add(item.getIdInsumo());
            }
            inventarioBloqueadoList = inventarioService.obtenerListaInactivosByIdInsumos(idInsumoLis);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }

    }

    private void limpiaVariables() {
        rutaPdfs = "";
        folio = "";
        archivo = "";
        pathTmp = "";
        pathTmp2 = "";        
    }

    private boolean enviarSurtimiento() {
        LOGGER.trace("mx.mc.magedbean.DispensacionMB.enviarSurtimiento()");
        boolean res = Constantes.INACTIVO;
        int vale = 0;
        try {

            for (SurtimientoInsumo_Extend item : this.surtimientoInsumoExtendedList) {

                if (item.getCantidadVale() != null && item.getCantidadVale() > 0) {
                    vale++;
                }
            }
            this.surtimientoExtendedSelected.setIdEstructuraAlmacen(currentSesionUser.getIdEstructura());
            this.surtimientoExtendedSelected.setUpdateFecha(new java.util.Date());
            this.surtimientoExtendedSelected.setUpdateIdUsuario(currentSesionUser.getIdUsuario());
            this.surtimientoExtendedSelected.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.SURTIDO.getValue());

            limpiaVariables();

            res = imprimirTcketVale(this.surtimientoExtendedSelected, this.currentSesionUser.getNombreUsuario(), true);

            if (vale > 0) {
                res = imprimirTcketVale(this.surtimientoExtendedSelected, this.currentSesionUser.getNombreUsuario(), false);
            }
            if (this.archivo.length() > 0) {
                String ticket = (System.getProperty("os.name").toLowerCase().contains("windows")) ? "\\" : "/";
                rutaPdfs = dirTmp.getPath() + ticket + surtimientoExtendedSelected.getFolio() + ".pdf";
                reader1 = new PdfReader(pathTmp);
                if (pathTmp2.length() > 0) {
                    reader2 = new PdfReader(pathTmp2);
                }
                PdfCopyFields copia = new PdfCopyFields(new FileOutputStream(rutaPdfs));
                copia.addDocument(reader1);

                if (pathTmp2.length() > 0) {
                    copia.addDocument(reader2);
                }
                copia.close();
                this.archivo = urlf + "/resources/tmp/" + surtimientoExtendedSelected.getFolio() + ".pdf";
            }
        } catch (Exception ex) {
            LOGGER.error("Error en el metodo enviarSurtimiento :: {}", ex.getMessage());
        }
        return res;
    }

    private boolean imprimirTcketVale(Surtimiento_Extend surtimientoExtendedSelected, String nombreUsuario, boolean ticket) throws Exception {
        LOGGER.debug("mx.mc.magedbean.DispensacionMB.imprimir()");
        boolean status = Constantes.INACTIVO;
        try {
            String rutTicket = (System.getProperty("os.name").toLowerCase().contains("windows")) ? "\\" : "/";
            if (ticket) {
                pathTmp = dirTmp.getPath() + rutTicket + surtimientoExtendedSelected.getFolio() + "T.pdf";
            } else {
                pathTmp2 = dirTmp.getPath() + rutTicket + surtimientoExtendedSelected.getFolio() + "V.pdf";
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
                status = reportesService.imprimeSurtimientoPrescExt(surtimientoExtendedSelected, nombreUsuario, pathTmp, url);
            } else {
                status = reportesService.imprimeSurtimientoVales(surtimientoExtendedSelected, nombreUsuario, pathTmp2, url);
            }

            if (ticket && status) {
                archivo = url + "/resources/tmp/ticket" + surtimientoExtendedSelected.getFolio() + ".pdf";
            }
        } catch (Exception e) {
            LOGGER.error("Error al generar la Impresión. {}", e.getMessage());
            status = Constantes.INACTIVO;
        }

        return status;

    }

    public void imprimirTcketChiconcuac(Surtimiento_Extend itemSelect) throws Exception {

        LOGGER.debug("mx.mc.magedbean.DispensacionMB.imprimir()");
        boolean status = Constantes.INACTIVO;
        try {
            Prescripcion prescripcion = new Prescripcion();
            prescripcion.setFolio(itemSelect.getFolioPrescripcion());
            prescripcion.setFechaPrescripcion(itemSelect.getFechaProgramada());
            EntidadHospitalaria entidadHospitalaria = this.entidadHospitalariaService.obtener(new EntidadHospitalaria());
            
            byte[] buffer = reportesService.imprimeSurtimientoPrescManualChiconcuac(
                    prescripcion,
                    entidadHospitalaria,
                    itemSelect,
                    this.currentSesionUser.getNombreUsuario());

            if (buffer != null) {
                status = Constantes.ACTIVO;
                SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "sesionMB");
                sesion.setReportStream(buffer);
                sesion.setReportName(String.format("ticket_%s.pdf",itemSelect.getFolio()));
            }
        } catch (Exception e) {
            LOGGER.error("Error al generar la Impresión. {}", e.getMessage());
        }

        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);

    }

    public void validaCancelarPrescripcion(String idPrescripcion) {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.validaCancelarPrescripcion()");

        boolean status = Constantes.INACTIVO;
        if (!permiso.isPuedeEditar()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errTransaccion), null);

        } else if (idPrescripcion == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcNopuedecancelar), null);

        } else {
            try {
                Prescripcion p = new Prescripcion();
                p.setIdPrescripcion(idPrescripcion);
                Prescripcion prescripcionSele = prescripcionService.obtener(p);
                if (prescripcionSele == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcNopuedecancelar), null);

                } else {
                    status = cancelarPrescripcion(idPrescripcion);
                }
                for (int i = 0; i < this.surtimientoExtendedList.size(); i++) {
                    Surtimiento_Extend item = this.surtimientoExtendedList.get(i);
                    if (item.getIdPrescripcion().equals(idPrescripcion)) {
                        this.surtimientoExtendedList.remove(i);
                        break;
                    }
                }
            } catch (Exception ex) {
                LOGGER.error(RESOURCES.getString(prcNopuedecancelar), ex);
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcNopuedecancelar), null);
            }
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    /**
     * Cancela una prescripción así como todos los surtimientos dependientes de
     * esta prescripción y todos medicamentos de la prescripción
     *
     * @return
     */
    private boolean cancelarPrescripcion(String idPrescripcion) {
        LOGGER.error("mx.mc.magedbean.PrescripcionMB.cancelarPrescripcion()");
        boolean res = Constantes.INACTIVO;
        try {
            if (this.permiteCancelarSurtimiento) {
                Prescripcion p = new Prescripcion();
                p.setIdPrescripcion(idPrescripcion);

                prescripcionSelected = this.prescripcionService.obtener(p);

                Prescripcion folioTemp = prescripcionService.verificarFolioCancelar(prescripcionSelected.getFolio() + "C");
                if (folioTemp == null) {
                    prescripcionSelected.setFolio(prescripcionSelected.getFolio() + "C");
                } else {
                    prescripcionSelected.setFolio(generarFolCancelacion(folioTemp.getFolio()));
                }

                res = this.prescripcionService.cancelarPrescripcionChiconcuac(prescripcionSelected,
                        this.currentSesionUser.getIdUsuario(), new java.util.Date(),
                        EstatusPrescripcion_Enum.CANCELADA.getValue(),
                        EstatusGabinete_Enum.CANCELADA.getValue(),
                        EstatusSurtimiento_Enum.CANCELADO.getValue());

            } else {
                res = this.prescripcionService.cancelarPrescripcion(idPrescripcion,
                        this.currentSesionUser.getIdUsuario(), new java.util.Date(),
                        EstatusPrescripcion_Enum.CANCELADA.getValue(),
                        EstatusGabinete_Enum.CANCELADA.getValue(),
                        EstatusSurtimiento_Enum.CANCELADO.getValue());
            }
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString(prcNopuedecancelar), ex);
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcNopuedecancelar), null);
        }
        return res;
    }

    public String generarFolCancelacion(String folio){
        char[] array = folio.toCharArray();
        boolean consta = false;
        String aux0 = "0";
        String aux1 = "";
        for (int j = 1; j < folio.length(); j++) {
            if (consta) {
                aux0 += array[j];
            }
            if (!Character.isDigit(array[j])) {
                consta = true;
            } else if (!consta) {
                aux1 += array[j];
            }
        }
        BigInteger secuencia1 = new BigInteger(aux0);
        secuencia1 = secuencia1.add(BigInteger.ONE);
        return array[0] + aux1 + "C" + secuencia1;
    }

    public Integer getHorasCancelacion() {
        return horasCancelacion;
    }

    public void setHorasCancelacion(Integer horasCancelacion) {
        this.horasCancelacion = horasCancelacion;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
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

    public List<SurtimientoEnviado> getSurtimientoEnviadoList() {
        return surtimientoEnviadoList;
    }

    public void setSurtimientoEnviadoList(List<SurtimientoEnviado> surtimientoEnviadoList) {
        this.surtimientoEnviadoList = surtimientoEnviadoList;
    }

    public ReportesService getReportesService() {
        return reportesService;
    }

    public void setReportesService(ReportesService reportesService) {
        this.reportesService = reportesService;
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

    public String getCadenaBusqueda() {
        return cadenaBusqueda;
    }

    public void setCadenaBusqueda(String cadenaBusqueda) {
        this.cadenaBusqueda = cadenaBusqueda;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }
    
    public String getRutaPdfs() {
        return rutaPdfs;
    }

    public void setRutaPdfs(String rutaPdfs) {
        this.rutaPdfs = rutaPdfs;
    }
    
    public String getArchivoVale() {
        return archivoVale;
    }

    public void setArchivoVale(String archivoVale) {
        this.archivoVale = archivoVale;
    }

    public String getPathTmp() {
        return pathTmp;
    }

    public void setPathTmp(String pathTmp) {
        this.pathTmp = pathTmp;
    }

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

    public String getUrlf() {
        return urlf;
    }

    public void setUrlf(String urlf) {
        this.urlf = urlf;
    }

    public PdfReader getReader1() {
        return reader1;
    }

    public void setReader1(PdfReader reader1) {
        this.reader1 = reader1;
    }

    public PdfReader getReader2() {
        return reader2;
    }

    public void setReader2(PdfReader reader2) {
        this.reader2 = reader2;
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

    public Surtimiento_Extend getSurtimientoExtendedSelected() {
        return surtimientoExtendedSelected;
    }

    public void setSurtimientoExtendedSelected(Surtimiento_Extend surtimientoExtendedSelected) {
        this.surtimientoExtendedSelected = surtimientoExtendedSelected;
    }

    public List<Surtimiento_Extend> getSurtimientoExtendedList() {
        return surtimientoExtendedList;
    }

    public void setSurtimientoExtendedList(List<Surtimiento_Extend> surtimientoExtendedList) {
        this.surtimientoExtendedList = surtimientoExtendedList;
    }

    public Integer getSizeSurtimientoExtendList() {
        if (surtimientoExtendedList != null) {
            return surtimientoExtendedList.size();
        } else {
            return 0;
        }
    }

    public SurtimientoInsumo_Extend getSurtimientoInsumoExtendedSelected() {
        return surtimientoInsumoExtendedSelected;
    }

    public void setSurtimientoInsumoExtendedSelected(SurtimientoInsumo_Extend surtimientoInsumoExtendedSelected) {
        this.surtimientoInsumoExtendedSelected = surtimientoInsumoExtendedSelected;
    }

    public List<SurtimientoInsumo_Extend> getSurtimientoInsumoExtendedList() {
        return surtimientoInsumoExtendedList;
    }

    public void setSurtimientoInsumoExtendedList(List<SurtimientoInsumo_Extend> surtimientoInsumoExtendedList) {
        this.surtimientoInsumoExtendedList = surtimientoInsumoExtendedList;
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

    public SurtimientoService getSurtimientoService() {
        return surtimientoService;
    }

    public void setSurtimientoService(SurtimientoService surtimientoService) {
        this.surtimientoService = surtimientoService;
    }

    public SurtimientoInsumoService getSurtimientoInsumoService() {
        return surtimientoInsumoService;
    }

    public void setSurtimientoInsumoService(SurtimientoInsumoService surtimientoInsumoService) {
        this.surtimientoInsumoService = surtimientoInsumoService;
    }

    public InventarioService getInventarioService() {
        return inventarioService;
    }

    public void setInventarioService(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    public List<Surtimiento_Extend> getFilterSurtimientoList() {
        return filterSurtimientoList;
    }

    public void setFilterSurtimientoList(List<Surtimiento_Extend> filterSurtimientoList) {
        this.filterSurtimientoList = filterSurtimientoList;
    }

    public Prescripcion getPrescripcionSelected() {
        return prescripcionSelected;
    }

    public void setPrescripcionSelected(Prescripcion prescripcionSelected) {
        this.prescripcionSelected = prescripcionSelected;
    }

    public boolean isPermiteCancelarSurtimiento() {
        return permiteCancelarSurtimiento;
    }

    public void setPermiteCancelarSurtimiento(boolean permiteCancelarSurtimiento) {
        this.permiteCancelarSurtimiento = permiteCancelarSurtimiento;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }
    
}
