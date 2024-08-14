/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.Estructura;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.SurtimientoInsumo_Extend;
import mx.mc.model.SurtimientoMinistrado;
import mx.mc.model.Usuario;
import mx.mc.model.VistaRecepcionMedicamento;
import mx.mc.service.EstructuraService;
import mx.mc.service.SurtimientoMinistradoService;
import mx.mc.service.SurtimientoService;
import mx.mc.service.VistaRecepcionMedicamentoService;
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
 * @author gcruz
 */
@Controller
@Scope(value = "view")
public class MinistracionPrescripcionMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(MinistracionPrescripcionMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private String cadenaBusqueda;
    private String codigoBarras;
    private String tipoPrescripcion;
    private String idEstructura;

    private int numeroMedDetalles;
    private boolean msgModal;
    private boolean isAdmin;
    private boolean status;
    private Usuario currentUser;
    private PermisoUsuario permiso;
    private List<Estructura> estructuraList;
    private List<Estructura> listaAuxiliar;
    private VistaRecepcionMedicamento viewRecepcionMed;
    private List<SurtimientoInsumo_Extend> listaSurtimiento;
    private List<VistaRecepcionMedicamento> viewRecepcionList;
    private List<String> listaIdSurtimientoInsumo;
    private int numeroRegistros;

    @Autowired
    private transient VistaRecepcionMedicamentoService viewRecepcionService;
    @Autowired
    private transient SurtimientoService surtimientoService;

    @Autowired
    private transient SurtimientoMinistradoService surtimientoMinistradoService;

    @Autowired
    private transient EstructuraService estructuraService;

    @PostConstruct
    public void init() {
        initialize();
        permiso = Comunes.obtenerPermisos(Transaccion_Enum.MINISTRAPRESCRI.getSufijo());
        validarUsuarioAdministrador();
        alimentarComboAlmacen();
        obtenerSutimientoEnviado();
    }

    private void initialize() {

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        currentUser = sesion.getUsuarioSelected();
        this.isAdmin = false;
        estructuraList = new ArrayList<>();
        listaIdSurtimientoInsumo = new ArrayList<>();
    }

    public void validarUsuarioAdministrador() {
        try {
            this.isAdmin = Comunes.isAdministrador();
            if (!this.isAdmin) {
                this.idEstructura = currentUser.getIdEstructura();
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo validarUsuarioAdministrador :: {}", e.getMessage());
        }
    }

    public void alimentarComboAlmacen() {
        try {

            Estructura est = new Estructura();

            if (!this.isAdmin) {
                est.setIdEstructura(currentUser.getIdEstructura());
                this.estructuraList = this.estructuraService.obtenerLista(est);

            } else {
                est.setIdTipoAreaEstructura(Constantes.TIPO_AREA_SERVICIO);
                est.setEnvioHL7(true);
                this.estructuraList = this.estructuraService.obtenerLista(est);
                this.idEstructura = estructuraList.get(0).getIdEstructura();
            }

        } catch (Exception e) {
            LOGGER.error("Error en el metodo alimentarComboAlmacen :: {}", e.getMessage());
        }
    }

    public void ordenarListaEstructura(Estructura estrucPrincipal) {
        try {
            if (estrucPrincipal != null) {
                for (int i = 1; i < listaAuxiliar.size(); i++) {
                    if (listaAuxiliar.get(i).getIdEstructuraPadre() != null && listaAuxiliar.get(i).getIdEstructuraPadre().toLowerCase().contains(estrucPrincipal.getIdEstructura().toLowerCase())) {
                        estructuraList.add(listaAuxiliar.get(i));
                        ordenarListaEstructura(listaAuxiliar.get(i));
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al ordenar la lista :: {}", ex.getMessage());
        }
    }

    public void buscarPrescripcionesMinis() {
        try {
            viewRecepcionList = viewRecepcionService.obtenerSurtimientosGabinetes(estructuraList);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener prescripciones: {}", ex.getMessage());
        }
    }

    public void obtenerSutimientoEnviado() {
        if (permiso.isPuedeVer()) {
            try {
                List<Estructura> listaEstructura = new ArrayList<>();
                if (cadenaBusqueda != null && cadenaBusqueda.trim().isEmpty()) {
                    cadenaBusqueda = null;
                }
                if (isAdmin) {
                    Estructura unaEstructura = estructuraService.obtenerEstructura(idEstructura);
                    listaEstructura.add(unaEstructura);
                    viewRecepcionList = viewRecepcionService.obtenerSurtimientosGabinetes(listaEstructura);
                } else {
                    viewRecepcionList = viewRecepcionService.obtenerSurtimientosGabinetes(estructuraList);
                }

                numeroRegistros = viewRecepcionList.size();
            } catch (Exception ex) {
                LOGGER.error("Error al obtener los surtimientos Enviados: {}", ex.getMessage());
            }
        } else {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("minismedicamento.err.permTransacc"), null);
        }
    }

    public void buscarPrescripcion() {
        LOGGER.trace("Buscando conincidencias de: {}", cadenaBusqueda);
        if (cadenaBusqueda != null && permiso.isPuedeCrear()) {
            try {
                viewRecepcionMed = buscarPrescripcionEnLista(cadenaBusqueda);
                if (viewRecepcionMed == null) {
                    viewRecepcionList = filtrarListaPrescripciones(cadenaBusqueda);
                    return;
                }
                switch (viewRecepcionMed.getTipoPrescripcion()) {
                    case "N":
                        tipoPrescripcion = "Normal";
                        break;
                    case "U":
                        tipoPrescripcion = "Urgente";
                        break;
                    case "D":
                        tipoPrescripcion = "Dosis Única";
                        break;
                    default:
                }
                listaSurtimiento = surtimientoService.obtenerDetalleSurtimiento(viewRecepcionMed.getIdSurtimiento());
                status = Constantes.ACTIVO;
                numeroMedDetalles = listaSurtimiento.size();
                cadenaBusqueda = null;
            } catch (Exception e) {
                LOGGER.error("Error al buscar Medicamentos: {}", e.getMessage());
            }
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    private VistaRecepcionMedicamento buscarPrescripcionEnLista(String folio) {
        VistaRecepcionMedicamento item = null;
        for (VistaRecepcionMedicamento vist : viewRecepcionList) {
            if (vist.getFolioPrescripcion().equals(folio)) {
                item = vist;
                break;
            }
        }
        return item;
    }

    private List<VistaRecepcionMedicamento> filtrarListaPrescripciones(String cadenaBusqueda) {
        try {
            viewRecepcionList = viewRecepcionService.obtenerSurtimientosGabinetes(estructuraList);;
        } catch (Exception ex) {
            LOGGER.error("Error al obtener Estructura: {}", ex.getMessage());
        }
        List<VistaRecepcionMedicamento> listaPresc = new ArrayList<>();
        for (VistaRecepcionMedicamento vist : viewRecepcionList) {
            String nombre = vist.getNombrePaciente().toUpperCase();
            if (nombre.contains(cadenaBusqueda.toUpperCase())) {
                listaPresc.add(vist);
            }
        }
        return listaPresc;
    }

    public void obtenerDetallePrescripcion(VistaRecepcionMedicamento item) {
        if (permiso.isPuedeEditar()) {
            try {
                listaIdSurtimientoInsumo = new ArrayList<>();
                msgModal = Constantes.ACTIVO;
                codigoBarras = "";
                viewRecepcionMed = item;
                switch (viewRecepcionMed.getTipoPrescripcion()) {
                    case "N":
                        tipoPrescripcion = "Normal";
                        break;
                    case "U":
                        tipoPrescripcion = "Urgente";
                        break;
                    case "D":
                        tipoPrescripcion = "Dosis Única";
                        break;
                    default:
                }
                listaSurtimiento = surtimientoService.obtenerDetalleSurtimiento(item.getIdSurtimiento());
                numeroMedDetalles = listaSurtimiento.size();
            } catch (Exception ex) {
                LOGGER.error("Error al obtener los Medicamentos: {}", ex.getMessage());
            }
        } else {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("minismedicamento.err.permEditar"), null);
        }
    }

    public void eliminarMedicamentoList(SurtimientoInsumo_Extend item) {
        try {
            listaSurtimiento.remove(item);
            listaIdSurtimientoInsumo.add(item.getIdSurtimientoInsumo());
            numeroMedDetalles = listaSurtimiento.size();
        } catch (Exception ex) {
            LOGGER.error("Error al eliminar medicamento: {}", ex.getMessage());
        }
    }

    public void guardarSurtimiento() {
        try {
            List<SurtimientoMinistrado> listaSurtMinistrado = new ArrayList<>();
            boolean resp = false;
            if (!listaIdSurtimientoInsumo.isEmpty()) {
                // Se buscan los surtimientos ministrados para actualizar
                listaSurtMinistrado = surtimientoMinistradoService.obtenerSurtimientosMinistrados(listaIdSurtimientoInsumo);
            }
            if (!listaSurtMinistrado.isEmpty()) {
                //actualizar campo ministracionConfirmada = 0
                for (SurtimientoMinistrado unaMinistracion : listaSurtMinistrado) {
                    unaMinistracion.setMinistracionConfirmada(Constantes.MINISTRACION_NO_CONFIRMADA);
                }
                resp = surtimientoMinistradoService.actualizarSurtMinistradoConfirmado(listaSurtMinistrado);

                if (resp) {
                    Mensaje.showMessage("Info", RESOURCES.getString("minismedicamento.info.guardar"), null);
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("minismedicamento.err.guardar"), null);
                }

            }
            obtenerSutimientoEnviado();
        } catch (Exception ex) {
            LOGGER.error("Error al guardar el surtimiento: {}", ex.getMessage());
        }
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public List<Estructura> getEstructuraList() {
        return estructuraList;
    }

    public void setEstructuraList(List<Estructura> estructuraList) {
        this.estructuraList = estructuraList;
    }

    public int getNumeroRegistros() {
        return numeroRegistros;
    }

    public void setNumeroRegistros(int numeroRegistros) {
        this.numeroRegistros = numeroRegistros;
    }

    public List<VistaRecepcionMedicamento> getViewRecepcionList() {
        return viewRecepcionList;
    }

    public void setViewRecepcionList(List<VistaRecepcionMedicamento> viewRecepcionList) {
        this.viewRecepcionList = viewRecepcionList;
    }

    public String getCadenaBusqueda() {
        return cadenaBusqueda;
    }

    public void setCadenaBusqueda(String cadenaBusqueda) {
        this.cadenaBusqueda = cadenaBusqueda;
    }

    public boolean isMsgModal() {
        return msgModal;
    }

    public void setMsgModal(boolean msgModal) {
        this.msgModal = msgModal;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getTipoPrescripcion() {
        return tipoPrescripcion;
    }

    public void setTipoPrescripcion(String tipoPrescripcion) {
        this.tipoPrescripcion = tipoPrescripcion;
    }

    public int getNumeroMedDetalles() {
        return numeroMedDetalles;
    }

    public void setNumeroMedDetalles(int numeroMedDetalles) {
        this.numeroMedDetalles = numeroMedDetalles;
    }

    public VistaRecepcionMedicamento getViewRecepcionMed() {
        return viewRecepcionMed;
    }

    public void setViewRecepcionMed(VistaRecepcionMedicamento viewRecepcionMed) {
        this.viewRecepcionMed = viewRecepcionMed;
    }

    public List<SurtimientoInsumo_Extend> getListaSurtimiento() {
        return listaSurtimiento;
    }

    public void setListaSurtimiento(List<SurtimientoInsumo_Extend> listaSurtimiento) {
        this.listaSurtimiento = listaSurtimiento;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }
    
}
