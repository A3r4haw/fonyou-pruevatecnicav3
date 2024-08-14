/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.magedbean;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import mx.mc.enums.Accion_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.Config;
import mx.mc.model.Impresora;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.TransaccionPermisos;
import mx.mc.model.Usuario;
import mx.mc.service.ConfigService;
import mx.mc.service.ImpresoraService;
import mx.mc.util.Comunes;
import mx.mc.util.Mensaje;
import org.cups4j.CupsClient;
import org.cups4j.CupsPrinter;
import org.primefaces.PrimeFaces;
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
public class ImpresoraMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(ImpresoraMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    private boolean status;
    private String warnSinPermTransac;

    private Usuario currentSesionUser;
    private String cadenaBusqueda;
    private boolean message;
    private int numeroRegistros;

    private String cupsIP = "";
    private List<Config> configList;
    private String impresoraCupsSelected;
    private static List<CupsPrinter> cupsPrinters;
    private PermisoUsuario permiso;
    @Autowired
    private transient ImpresoraService impresoraService;
    private Impresora impresoraSelect;
    private List<Impresora> impresoraList;

    @Autowired
    private transient ConfigService configService;

    @PostConstruct
    public void init() {
        warnSinPermTransac = "impresora.warn.sinPermTransac";
        permiso = Comunes.obtenerPermisos(Transaccion_Enum.IMPRESORA.getSufijo());
        initialize();
        if (permiso.isPuedeVer()){
            buscarImpresoras();
            obtenImpresorasCups();
        }
    }

    private void initialize() {
        this.setCadenaBusqueda("");
        impresoraSelect = new Impresora();
        impresoraList = new ArrayList<>();
        cupsPrinters = new ArrayList<>();
        message = Constantes.ACTIVO;
        status = Constantes.INACTIVO;
        numeroRegistros = 0;
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        currentSesionUser = sesion.getUsuarioSelected();
    }

    public void buscarImpresoras() {
        LOGGER.trace("Buscando Impresoras..");
        try {
            impresoraList = impresoraService.obtenerListaImpresora();
            numeroRegistros = impresoraList.size();
        } catch (Exception e) {
            LOGGER.error("Error al buscar Impresoras: {}", e.getMessage());
        }
    }

    public void crearImpresora() {
        if (permiso.isPuedeCrear()) {
            impresoraSelect = new Impresora();

        } else {
            Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString("impresora.warn.sinpermisosCrear"), null);
        }
    }

    public void obtenerImpresora(String idImpresora) {
        try {
            if (permiso.isPuedeVer() && !idImpresora.isEmpty()) {
                this.setImpresoraSelect(impresoraService.obtenerPorIdImpresora(idImpresora));
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString(warnSinPermTransac), null);
            }
        } catch (Exception ex) {
            LOGGER.error("Error al obtener impresora por id: {}", ex.getMessage());
        }
    }

    public void saveImpresora() {
        status = Constantes.INACTIVO;
        boolean res = Constantes.INACTIVO;
        try {
            if (impresoraSelect != null) {
                String valida = validaFormAgregarEditarImpresora();
                if (!valida.isEmpty()) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, valida, null);
                    return;
                }
                if (impresoraSelect.getTipo().equals("N") || validarIpImpresora()) {
                    if (impresoraSelect.getIdImpresora() == null) {
                        if (permiso.isPuedeCrear()) {
                            impresoraSelect.setIdImpresora(Comunes.getUUID());
                            impresoraSelect.setInsertFecha(new Date());
                            impresoraSelect.setInsertIdUsuario(currentSesionUser.getIdUsuario());
                            Impresora impres = impresoraService.obtenerImpresoraByIpAndDescripcion(impresoraSelect);
                            if (impres != null && !impresoraSelect.getTipo().equals("N")) {
                                Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString("impresora.err.existeImpresora"), null);
                            } else {
                                res = impresoraService.insertar(impresoraSelect);
                                if (res) {
                                    impresoraList.add(impresoraSelect);
                                    status = Constantes.ACTIVO;
                                }
                            }
                        } else {
                            LOGGER.error("No puede Crear");
                            Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString(warnSinPermTransac), null);
                            impresoraSelect.setIdImpresora(null);
                        }
                    } else {
                        if (permiso.isPuedeEditar()) {
                            impresoraSelect.setUpdateFecha(new Date());
                            impresoraSelect.setUpdateIdUsuario(currentSesionUser.getIdUsuario());
                            res = impresoraService.actualizar(impresoraSelect);
                            if (res) {
                                updateListImpresora(impresoraSelect);
                                status = Constantes.ACTIVO;
                            }
                        } else {
                            LOGGER.error("No puede Editar");
                            Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString(warnSinPermTransac), null);
                        }
                    }
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString("impresora.warn.sinPing"), null);
                }
                numeroRegistros = impresoraList.size();
            }
        } catch (Exception ex) {
            LOGGER.error("ERROR al guardar la impresora", ex);
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("impresora.err.guardar"), null);
            if (!status) {
                impresoraSelect.setIdImpresora(null);
            }
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public String validaFormAgregarEditarImpresora() {
        if (this.impresoraSelect.getDescripcion().isEmpty()) {
            return RESOURCES.getString("impresora.err.descripcion");
        }
        if (this.impresoraSelect.getIpImpresora().isEmpty()) {
            return RESOURCES.getString("impresora.err.ipImpresora");
        }
        if (this.impresoraSelect.getTipo().isEmpty()) {
            return RESOURCES.getString("impresora.err.tipo");
        }
        return "";
    }

    private void updateListImpresora(Impresora impresora) {
        for (Impresora unaImpresora : impresoraList) {
            if (impresora.getIdImpresora().equals(unaImpresora.getIdImpresora())) {
                unaImpresora.setIpImpresora(impresora.getIpImpresora());
                unaImpresora.setDescripcion(impresora.getDescripcion());
                unaImpresora.setTipo(impresora.getTipo());
                break;
            }
        }
    }

    public boolean validarIpImpresora() {
        boolean impresoraIp = false;
        try {
            InetAddress ping;
            ping = InetAddress.getByName(impresoraSelect.getIpImpresora());
            if (ping.isReachable(5000)) {
                impresoraIp = true;
            }

        } catch (IOException ex) {
            LOGGER.error("Error al validar ip de impresora: {}", ex.getMessage());
        }
        return impresoraIp;
    }

    public String getCadenaBusqueda() {
        return cadenaBusqueda;
    }

    public void setCadenaBusqueda(String cadenaBusqueda) {
        this.cadenaBusqueda = cadenaBusqueda;
    }

    public List<Impresora> getImpresoraList() {
        return impresoraList;
    }

    public void setImpresoraList(List<Impresora> impresoraList) {
        this.impresoraList = impresoraList;
    }

    public Impresora getImpresoraSelect() {
        return impresoraSelect;
    }

    public void setImpresoraSelect(Impresora impresoraSelect) {
        this.impresoraSelect = impresoraSelect;
    }

    public boolean isMessage() {
        return message;
    }

    public void setMessage(boolean message) {
        this.message = message;
    }

    public int getNumeroRegistros() {
        return numeroRegistros;
    }

    public void setNumeroRegistros(int numeroRegistros) {
        this.numeroRegistros = numeroRegistros;
    }

    public String getImpresoraCupsSelected() {
        return impresoraCupsSelected;
    }

    public void setImpresoraCupsSelected(String impresoraCupsSelected) {
        this.impresoraCupsSelected = impresoraCupsSelected;
    }

    public List<CupsPrinter> getCupsPrinters() {
        return cupsPrinters;
    }

    public void setCupsPrinters(List<CupsPrinter> cupsPrinters) {
        this.cupsPrinters = cupsPrinters;
    }

    private void obtenerDatosSistema() {
        LOGGER.debug("mx.mc.magedbean.ImpresoraMB.obtenerDatosSistema()");
        Config c = new Config();
        c.setActiva(Constantes.ACTIVO);
        try {
            configList = configService.obtenerLista(c);
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString("sys.config.err"), ex);
        }
    }

    public void obtenImpresorasCups() {
        cupsPrinters = new ArrayList<>();
        obtenerDatosSistema();
        int cupsPort = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_CUPSPRINT_PORT);
        cupsIP = Comunes.obtenValorConfiguracion(configList, Constantes.PARAM_SYSTEM_CUPSPRINT_IP);
        try {
            if (!cupsIP.isEmpty()) {
                CupsClient cupsClient = new CupsClient(cupsIP, cupsPort);
                cupsPrinters = cupsClient.getPrinters();
            }
        } catch (Exception ex) {
            LOGGER.error("{}.obtenImpresorasCups(): {}", this.getClass().getCanonicalName(), ex.getMessage());
        }
    }

    public void setDescripcionImpresora() {
        if (impresoraCupsSelected != null) {
            CupsPrinter selected = null;
            for (CupsPrinter cp : cupsPrinters) {
                if (cp.getName().equals(impresoraCupsSelected)) {
                    selected = cp;
                    break;
                }
            }
            if (selected != null) {
                impresoraSelect.setIpImpresora(cupsIP);
                impresoraSelect.setDescripcion(selected.getName());
            }
        }
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }
        
}
