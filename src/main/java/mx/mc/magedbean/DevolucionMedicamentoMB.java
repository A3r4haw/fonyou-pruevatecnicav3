/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.magedbean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import mx.mc.lazy.DevolucionMinistracionLazy;
import mx.mc.enums.EstatusDevolucion_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.DevolucionMinistracionExtended;
import mx.mc.model.DevolucionMinistracionDetalleExtended;
import mx.mc.model.Estructura;
import mx.mc.model.Medicamento_Extended;
import mx.mc.model.Paciente;
import mx.mc.model.Paciente_Extended;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.PermisoUsuario;

import mx.mc.model.SurtimientoEnviado_Extend;
import mx.mc.model.SurtimientoInsumo_Extend;
import mx.mc.model.SurtimientoMinistrado;
import mx.mc.model.TipoMotivo;
import mx.mc.model.Usuario;
import mx.mc.model.VistaRecepcionMedicamento;
import mx.mc.service.DevolucionMinistracionDetalleService;
import mx.mc.service.DevolucionMinistracionService;
import mx.mc.service.EstructuraService;
import mx.mc.service.MedicamentoService;
import mx.mc.service.PacienteService;
import mx.mc.service.SurtimientoMinistradoService;
import mx.mc.service.SurtimientoService;
import mx.mc.service.TipoMotivoService;
import mx.mc.service.VistaRecepcionMedicamentoService;
import mx.mc.util.Comunes;
import mx.mc.util.FechaUtil;
import mx.mc.util.Mensaje;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
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
public class DevolucionMedicamentoMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(DevolucionMedicamentoMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private boolean isAdmin;
    private boolean status;
    private boolean edit;
    private boolean isNew;
    private boolean showMjs;
    private boolean showMjsSearch;

    private String idEstructuraUser;
    private String tipoPrescripcion;
    private String cadenaBusqueda;
    private String codigoBarras;
    private ParamBusquedaReporte paramBusquedaReporte;
    private String filter;
    private Paciente pacienteExtended;
    private String comentarios;
    private int numeroRegistros;
    private int numeroMedDetalles;
    int cantidad = 0;
    int restante = 0;
    //datos del codigo de barras Prescripcion
    private String folioPrescripcion;
    private String fechaPrescripcion;
    //datos del codigo de barras del medicamento
    private String claveMedicamento;
    private String loteMedicamento;
    private Date caducidadMedicamento;
    private int tipoDevolucion;
    private int cantDevolver;
    private int cantidadMaxima;
    private Paciente pacienteDev;
    boolean surtimiento = false;
    boolean devolucion = false;
    boolean caducidad = false;
    boolean detallExis = false;
    boolean lote = false;
    boolean clave = false;
    boolean caduc = false;
    boolean conforme = false;
    boolean eliminar = false;
    boolean delete = false;
    boolean cant = false;
    boolean checkCant = false;
    boolean check = false;
    private String idSurtimientoInsumo;
    private Usuario currentUser;
    private List<String> messageError;
    private boolean activaAutoCompleteInsumos;
    private String warnPresNoEncontr;
    private String errBuscarPrescrip;
    private String soloPuedenDevolver;
    private String medicamentosParaLote;
    private String laCaducidadNOCorrecta;
    private String loteNOcorrecto;
    private String medicamentoSinPz;
    private PermisoUsuario permiso;
    private DevolucionMinistracionLazy devolucionMinistracionLazy;

    @Autowired
    private transient MedicamentoService medicamentoServiceautoComplete;
    private Medicamento_Extended medicamento;
    private List<Medicamento_Extended> medicamentoList;

    @Autowired
    private transient EstructuraService estructuraService;
    private Estructura estructuraSelect;
    private List<Estructura> estructuraList;

    @Autowired
    private transient SurtimientoService surtimientoService;
    private SurtimientoEnviado_Extend surtimientoEnviado;
    private List<SurtimientoEnviado_Extend> surtEnviadoList;
    private List<SurtimientoInsumo_Extend> surtInsumoExtList;

    @Autowired
    private transient VistaRecepcionMedicamentoService viewRecepcionService;
    private VistaRecepcionMedicamento viewRecepcionMed;
    private List<VistaRecepcionMedicamento> viewRecepcionList;

    @Autowired
    private transient TipoMotivoService tipoMotivoService;
    private List<TipoMotivo> tipoMotivoList;
    private List<TipoMotivo> tipoMotivoListAux;
    private TipoMotivo tipoMotivoListObject;

    @Autowired
    private transient DevolucionMinistracionService devolucionMiniExtService;
    private DevolucionMinistracionExtended devMinistracionExt;
    private List<DevolucionMinistracionExtended> devolucionList;
    private List<DevolucionMinistracionExtended> devolucionListFolio;

    @Autowired
    private transient DevolucionMinistracionDetalleService devolucionDetalleService;
    private DevolucionMinistracionDetalleExtended devMiniDetalleExt;
    private List<DevolucionMinistracionDetalleExtended> devolucionDetalleExtList;

    @Autowired
    private transient SurtimientoMinistradoService ministracionService;
    private SurtimientoMinistrado ministracion;
    private List<SurtimientoMinistrado> ministracionList;

    @Autowired
    private transient PacienteService pacienteService;

    @PostConstruct
    public void init() {
        errBuscarPrescrip = "devolmedica.err.buscarPrescrip";
        warnPresNoEncontr = "devolmedica.warn.presNoEncontr";
        soloPuedenDevolver = "Solo se pueden devolver: ";
        medicamentosParaLote = " medicamentos para el lote: ";
        laCaducidadNOCorrecta = "La caducidad no es correcta para el medicamento: ";
        loteNOcorrecto = "El lote no es correcto para el medicamento: ";
        medicamentoSinPz = "De este medicamento ya no hay más pz por devolver.";
        initialize();
        permiso = Comunes.obtenerPermisos(Transaccion_Enum.DEVOLMEDICAMENTO.getSufijo());
        obtieneAlmacen();
        obtenerSutimientoEnviado();
        obtieneListaMotivo();
    }

    private void initialize() {

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        messageError.add("");
        messageError.add("");
        showMjs = Constantes.ACTIVO;
        showMjsSearch = Constantes.ACTIVO;
        tipoDevolucion = 0;
        cantidadMaxima = 0;
        cantDevolver = 1;
        currentUser = sesion.getUsuarioSelected();
        estructuraSelect = new Estructura();
        surtimientoEnviado = new SurtimientoEnviado_Extend();
        devMinistracionExt = new DevolucionMinistracionExtended();
        devMiniDetalleExt = new DevolucionMinistracionDetalleExtended();
        paramBusquedaReporte = new ParamBusquedaReporte();
        estructuraList = new ArrayList<>();
        viewRecepcionList = new ArrayList<>();
        devolucionDetalleExtList = new ArrayList<>();
        devolucionList = new ArrayList<>();
        activaAutoCompleteInsumos = sesion.isActivaAutocompleteQrClave();
        messageError = new ArrayList();
    }

    private void obtieneAlmacen() {
        try {
            Estructura estruct = new Estructura();
            estruct.setIdEstructura(currentUser.getIdEstructura());
            estructuraSelect = estructuraService.obtener(estruct);
            if (estructuraSelect != null) {
                idEstructuraUser = estructuraSelect.getIdEstructura();
                estructuraList = estructuraService.obtenerAlmacenesTransfer(idEstructuraUser);
                if (estructuraSelect.getIdTipoAreaEstructura() == 1 && estructuraSelect.getIdTipoAlmacen() == 1) {
                    isAdmin = Constantes.ACTIVO;
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al obtener Estructura: {}", ex.getMessage());
        }
    }

    public void obtieneListaMotivo() {
        try {
            tipoMotivoListAux = new ArrayList<>();
            List<Integer> integer = new ArrayList<>();
            tipoMotivoList = tipoMotivoService.listaDevolucionEnPrescripcion();
            if (conforme) {
                for (int i = 0; i < tipoMotivoList.size(); i++) {
                    if (tipoMotivoList.get(i).getIdTipoMotivo() != 0 && tipoMotivoList.get(i).getPermiteResurtimiento() == 0) {
                        integer.add(tipoMotivoList.get(i).getIdTipoMotivo());
                    }
                }
                for (int i = 0; i < integer.size(); i++) {
                    for (int j = 0; j < tipoMotivoList.size(); j++) {
                        if (Objects.equals(tipoMotivoList.get(j).getIdTipoMotivo(), integer.get(i))) {
                            tipoMotivoListAux.add(tipoMotivoList.get(j));
                        }
                    }
                }
            } else {
                for (int i = 0; i < tipoMotivoList.size(); i++) {
                    if (tipoMotivoList.get(i).getIdTipoMotivo() != 0 && tipoMotivoList.get(i).getPermiteResurtimiento() == 1) {
                        integer.add(tipoMotivoList.get(i).getIdTipoMotivo());
                    }
                }
                for (int i = 0; i < integer.size(); i++) {
                    for (int j = 0; j < tipoMotivoList.size(); j++) {
                        if (Objects.equals(tipoMotivoList.get(j).getIdTipoMotivo(), integer.get(i))) {
                            tipoMotivoListAux.add(tipoMotivoList.get(j));
                        }
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al obtener Lista de Motivos Ajuste: {}", ex.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam("tipoMotivoListAux", tipoMotivoListAux);
    }

    public List<Paciente> autoCompletePacientes(String cadena) {
        List<Paciente> listaPacientes = new ArrayList<>();
        try {
            listaPacientes = this.pacienteService.obtenerRegistrosPorCriterioDeBusqueda2(
                    cadena.trim(), Constantes.REGISTROS_PARA_MOSTRAR);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener obtenerListaClavesSku : {}", ex.getMessage());
        }
        return listaPacientes;
    }

    private VistaRecepcionMedicamento buscarPrescripcionEnLista(String folio) {
        showMjsSearch = Constantes.ACTIVO;
        VistaRecepcionMedicamento item = new VistaRecepcionMedicamento();
        for (VistaRecepcionMedicamento vist : viewRecepcionList) {
            if (vist.getFolioPrescripcion().equals(folio)) {
                item = vist;
                break;
            }
        }
        return item;
    }

    public List<Medicamento_Extended> autoComplete(String cadena) {
        try {
            String idEstructura = surtimientoService.obtenerAlmacenPadreOfSurtimiento(estructuraSelect.getIdEstructura());
            if (idEstructura == null || idEstructura.isEmpty()) {
                idEstructura = estructuraSelect.getIdEstructura();
            }
            medicamentoList = medicamentoServiceautoComplete.searchMedicamentoAutoComplete(cadena.trim(), idEstructura, activaAutoCompleteInsumos);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener Medicamentos: {}", ex.getMessage());
        }
        return medicamentoList;
    }

    public void handleSelect(SelectEvent e) {
        medicamento = (Medicamento_Extended) e.getObject();
    }

    public void handleUnSelect() {
        medicamento = new Medicamento_Extended();
    }

    private boolean tratarCodigoDeBarrasMedicamento(String codigo) {
        boolean resp = Constantes.INACTIVO;
        try {
            String[] parts = codigo.split(Constantes.SEPARADOR_CODIGO);
            if (parts.length > 1) {
                claveMedicamento = parts[0];
                loteMedicamento = parts[1];
                caducidadMedicamento = FechaUtil.formatoFecha("ddMMyyyy", parts[2]);
                resp = Constantes.ACTIVO;
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo tratarCodigoDeBarras :: {}", e.getMessage());
        }
        return resp;
    }

    /*Methods Public*/
    public void obtenerSutimientoEnviado() {
        if (permiso.isPuedeVer()) {
            try {
                if (cadenaBusqueda != null && cadenaBusqueda.trim().isEmpty()) {
                    cadenaBusqueda = null;
                }
                estructuraSelect = estructuraService.obtenerEstructura(currentUser.getIdEstructura());

                devolucionMinistracionLazy = new DevolucionMinistracionLazy(devolucionMiniExtService, cadenaBusqueda, estructuraSelect);
                numeroRegistros = devolucionMinistracionLazy.getRowCount();
                cadenaBusqueda = "";
                LOGGER.debug("Resultados: {}", devolucionMinistracionLazy.getTotalReg());

            } catch (Exception ex) {
                LOGGER.error("Error al obtener los surtimientos Enviados: {}", ex.getMessage());
            }
        } else {
            Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString("devolmedica.warn.sinPermisos"), null);
        }
    }

    public void buscarPrescripcion() {
        LOGGER.trace("Buscando conincidencias de: {}", cadenaBusqueda);
        codigoBarras = "";
        if (cadenaBusqueda != null && permiso.isPuedeVer()) {
            try {
                viewRecepcionMed = buscarPrescripcionEnLista(cadenaBusqueda);

                surtEnviadoList = surtimientoService.obtenerDetalleDevolucionPorIdPrescripcionExtend(viewRecepcionMed.getIdPrescripcion());
                status = Constantes.ACTIVO;
                isNew = Constantes.INACTIVO;
                edit = Constantes.INACTIVO;
                switch (viewRecepcionMed.getIdEstatusSurtimiento()) {
                    case 2:
                        isNew = Constantes.ACTIVO;
                    case 1:
                        edit = Constantes.ACTIVO;
                        break;
                    default:
                }
                numeroMedDetalles = surtEnviadoList.size();
                cadenaBusqueda = null;
                codigoBarras = "";
            } catch (Exception e) {
                LOGGER.error("Error al buscar Medicamentos: {}", e.getMessage());
            }
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public void buscarFoliorPaciente() {
        showMjsSearch = Constantes.ACTIVO;
        if (permiso.isPuedeCrear()) {
            try {
                if (pacienteExtended != null && pacienteExtended.getIdEstructura() != null && pacienteExtended.getNombreCompleto() != null) {
                    pacienteDev = new Paciente(pacienteExtended.getIdEstructura(),
                            ((pacienteExtended.getNombreCompleto() != null ? pacienteExtended.getNombreCompleto() : "") + " "
                            + (pacienteExtended.getApellidoPaterno() != null ? pacienteExtended.getApellidoPaterno() : "") + " "
                            + (pacienteExtended.getApellidoMaterno() != null ? pacienteExtended.getApellidoMaterno() : "")));
                }
                if (devolucionListFolio != null && !devolucionListFolio.isEmpty()) {
                    devolucionListFolio.clear();
                }

                devolucionListFolio = devolucionMiniExtService.obtenerByFolioSurtimientoForDevList(codigoBarras, pacienteDev, filter);
                if (devolucionListFolio != null) {
                    status = Constantes.ACTIVO;
                    pacienteExtended = new Paciente_Extended();
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString(warnPresNoEncontr), null);
                    pacienteExtended = new Paciente_Extended();
                }

            } catch (Exception ex) {
                LOGGER.trace("Ocurrio una exception al buscar el Folio de la Prescripción: {}", ex.getMessage());
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errBuscarPrescrip), null);
                pacienteExtended = new Paciente_Extended();
            }
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public void newDevolucion() {
        showMjs = Constantes.INACTIVO;
        status = Constantes.INACTIVO;
        showMjsSearch = Constantes.ACTIVO;
        if (permiso.isPuedeCrear()) {
            try {
                if (pacienteExtended != null && pacienteExtended.getIdEstructura() != null && pacienteExtended.getNombreCompleto() != null) {
                    pacienteDev = new Paciente(pacienteExtended.getIdEstructura(),
                            ((pacienteExtended.getNombreCompleto() != null ? pacienteExtended.getNombreCompleto() : "") + " "
                            + (pacienteExtended.getApellidoPaterno() != null ? pacienteExtended.getApellidoPaterno() : "") + " "
                            + (pacienteExtended.getApellidoMaterno() != null ? pacienteExtended.getApellidoMaterno() : "")));
                }
                devMinistracionExt = null;
                devMinistracionExt = devolucionMiniExtService.obtenerByFolioSurtimientoForDev(codigoBarras, pacienteDev, filter);
                if (devMinistracionExt != null) {
                    surtInsumoExtList = surtimientoService.detalleSurtimientoInsumoExtRecepMedi(devMinistracionExt.getIdSurtimiento());
                    devolucionDetalleExtList = null;
                    isNew = Constantes.ACTIVO;
                    edit = Constantes.ACTIVO;
                    status = Constantes.ACTIVO;
                    codigoBarras = "";
                    cantDevolver = 1;
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString(warnPresNoEncontr), null);
                }
                codigoBarras = "";
            } catch (Exception ex) {
                LOGGER.trace("Ocurrio una exception en newDevolucion: {}", ex.getMessage());
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errBuscarPrescrip), null);
                codigoBarras = "";
            }
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public void newDevolucionFolio(DevolucionMinistracionExtended item) {
        showMjs = Constantes.INACTIVO;
        status = Constantes.INACTIVO;
        showMjsSearch = Constantes.INACTIVO;
        if (permiso.isPuedeCrear()) {
            try {
                devMinistracionExt = null;
                devMinistracionExt = item;
                if (devMinistracionExt != null) {
                    surtInsumoExtList = surtimientoService.detalleSurtimientoInsumoExtRecepMedi(item.getIdSurtimiento());
                    devolucionDetalleExtList = null;
                    isNew = Constantes.ACTIVO;
                    edit = Constantes.ACTIVO;
                    status = Constantes.ACTIVO;
                    codigoBarras = "";
                    cantDevolver = 1;
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString(warnPresNoEncontr), null);
                }
            } catch (Exception ex) {
                LOGGER.trace("Ocurrio una exception en newDevolucionFolio: {}", ex.getMessage());
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errBuscarPrescrip), null);
            }
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public void cancel() {
        status = true;
        pacienteExtended = null;
        pacienteDev = null;
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public void addMedicamentoList(SelectEvent e) {
        medicamento = (Medicamento_Extended) e.getObject();
        surtimientoEnviado = new SurtimientoEnviado_Extend();
        surtimiento = false;
        devolucion = false;
        caducidad = false;
        lote = false;
        clave = false;
        caduc = false;
        delete = false;
        cant = false;
        checkCant = false;
        check = false;
        detallExis = false;
        boolean error = true;
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        String date = dateFormat.format(medicamento.getFechaCaducidad());
        codigoBarras = medicamento.getClaveInstitucional() + "," + medicamento.getLote() + "," + date;
        if (permiso.isPuedeCrear()) {

            if (tratarCodigoDeBarrasMedicamento(codigoBarras)) {
                if (cantDevolver < 1) {
                    cantDevolver = 1;
                }
                surtimiento = false;
                devolucion = false;
                surtInsumoExtList.forEach(item -> {
                    if (item.getClaveInstitucional().equals(claveMedicamento)) {
                        surtimiento = true;
                    }
                });
                if (devolucionDetalleExtList != null) {
                    devolucionDetalleExtList.forEach(item -> {
                        if (item.getClaveInstitucional().equals(claveMedicamento)) {
                            devolucion = true;
                        }
                    });
                }

                //Si es verdadero, el medicamento si existe en el surtimiento
                if (surtimiento) {
                    if (surtimiento && devolucion && devolucionDetalleExtList != null) {

                        for (int index = 0; index < devolucionDetalleExtList.size(); index++) {
                            try {
                                DevolucionMinistracionDetalleExtended devMinExt = devolucionDetalleExtList.get(index);
                                if (devMinExt.getClaveInstitucional().equals(claveMedicamento) && devMinExt.getSurtimientoEnviadoExtList() != null) {
                                    cantidad = devMinExt.getCantidadMaxima();
                                    surtimiento = true;
                                    devolucion = true;
                                    if (devMinExt.getTipoDevolucion() == 0) {
                                        devMinExt.setCantidadDevuelta(0);
                                    }
                                    for (SurtimientoEnviado_Extend surt_env_ext : devMinExt.getSurtimientoEnviadoExtList()) {
                                        if (surt_env_ext.getLote().equals(loteMedicamento)) {
                                            lote = true;
                                            surtimiento = false;
                                            devolucion = false;
                                            if (surt_env_ext.getCaducidad().equals(caducidadMedicamento)) {
                                                surtimiento = false;
                                                devolucion = false;
                                                caducidad = true;
                                                if (surt_env_ext.getTipoDevolucion() == null) {
                                                    devMinExt.setTipoDevolucion(tipoDevolucion);
                                                    surt_env_ext.setTipoDevolucion(tipoDevolucion);
                                                }
                                                if (surt_env_ext.getTipoDevolucion() == tipoDevolucion) {
                                                    if (!eliminar) {
                                                        if ((devMinExt.getCantidadDevuelta() + cantDevolver) <= devMinExt.getCantidadMaxima()) {
                                                            if ((surt_env_ext.getCantidadDevolver() + cantDevolver) <= surt_env_ext.getCantidadEnviado()) {
                                                                surt_env_ext.setCantidadDevolver(surt_env_ext.getCantidadDevolver() + cantDevolver);
                                                                devMinExt.setCantidadDevuelta(devMinExt.getCantidadDevuelta() + cantDevolver);
                                                                error = false;
                                                                codigoBarras = "";
                                                                medicamento = new Medicamento_Extended();
                                                                break;
                                                            } else {
                                                                messageError.set(0, Constantes.MENSAJE_ERROR);
                                                                messageError.set(1, soloPuedenDevolver + surt_env_ext.getCantidadRecibido() + medicamentosParaLote + loteMedicamento);
                                                                codigoBarras = "";
                                                                medicamento = new Medicamento_Extended();
                                                            }
                                                        } else {
                                                            messageError.set(0, Constantes.MENSAJE_ERROR);
                                                            messageError.set(1, soloPuedenDevolver + surt_env_ext.getCantidadRecibido() + medicamentosParaLote + loteMedicamento);
                                                            codigoBarras = "";
                                                            medicamento = new Medicamento_Extended();
                                                        }
                                                    } else {
                                                        if ((devMinExt.getCantidadDevuelta() - cantDevolver) > 0) {
                                                            if ((surt_env_ext.getCantidadDevolver() - cantDevolver) > 0) {
                                                                surt_env_ext.setCantidadDevolver(surt_env_ext.getCantidadDevolver() - cantDevolver);
                                                                devMinExt.setCantidadDevuelta(devMinExt.getCantidadDevuelta() - cantDevolver);
                                                                error = false;
                                                                codigoBarras = "";
                                                                medicamento = new Medicamento_Extended();
                                                                delete = false;
                                                                break;
                                                            } else {
                                                                idSurtimientoInsumo = surt_env_ext.getIdSurtimientoInsumo();
                                                                delete = true;

                                                            }
                                                        } else {
                                                            idSurtimientoInsumo = surt_env_ext.getIdSurtimientoInsumo();
                                                            delete = true;

                                                        }
                                                    }
                                                } else {
                                                    if (!eliminar) {
                                                        devMinExt.getSurtimientoEnviadoExtList().forEach(dtll -> {
                                                            if (dtll.getLote().equals(loteMedicamento) && dtll.getCaducidad().equals(caducidadMedicamento) && dtll.isConforme() == conforme) {
                                                                detallExis = true;
                                                            }
                                                        });
                                                        if (detallExis) {
                                                            continue;
                                                        } else {
                                                            if ((devMinExt.getCantidadDevuelta() + cantDevolver) <= devMinExt.getCantidadMaxima()) {
                                                                if ((surt_env_ext.getCantidadDevolver() + cantDevolver) <= surt_env_ext.getCantidadRecibido()) {
                                                                    devMinExt.setCantidadDevuelta(devMinExt.getCantidadDevuelta() + cantDevolver);
                                                                    SurtimientoEnviado_Extend detall = new SurtimientoEnviado_Extend();
                                                                    detall.setLote(loteMedicamento);
                                                                    detall.setCantidadDevolver(cantDevolver);
                                                                    detall.setCantidadEnviado(surt_env_ext.getCantidadEnviado());
                                                                    detall.setCantidadRecibido(surt_env_ext.getCantidadRecibido());
                                                                    detall.setIdInsumo(surt_env_ext.getIdInsumo());
                                                                    detall.setIdInventarioSurtido(surt_env_ext.getIdInventarioSurtido());
                                                                    detall.setIdSurtimientoEnviado(surt_env_ext.getIdSurtimientoEnviado());
                                                                    detall.setIdSurtimientoInsumo(surt_env_ext.getIdSurtimientoInsumo());
                                                                    detall.setCaducidad(caducidadMedicamento);
                                                                    detall.setConforme(conforme);
                                                                    detall.setTipoDevolucion(tipoDevolucion);
                                                                    devMinExt.getSurtimientoEnviadoExtList().add(detall);
                                                                    error = false;
                                                                    codigoBarras = "";
                                                                    medicamento = new Medicamento_Extended();
                                                                    break;
                                                                } else {
                                                                    messageError.set(0, Constantes.MENSAJE_ERROR);
                                                                    messageError.set(1, soloPuedenDevolver + surt_env_ext.getCantidadRecibido() + medicamentosParaLote + loteMedicamento);
                                                                    codigoBarras = "";
                                                                    medicamento = new Medicamento_Extended();
                                                                }
                                                            } else {
                                                                messageError.set(0, Constantes.MENSAJE_ERROR);
                                                                messageError.set(1, soloPuedenDevolver + surt_env_ext.getCantidadRecibido() + medicamentosParaLote + loteMedicamento);
                                                                codigoBarras = "";
                                                                medicamento = new Medicamento_Extended();
                                                            }
                                                        }
                                                    } else {
                                                        if (!delete) {
                                                            messageError.set(0, Constantes.MENSAJE_ERROR);
                                                            messageError.set(1, "No se encontró nungún registro con el motivo seleccionado");
                                                            codigoBarras = "";
                                                            medicamento = new Medicamento_Extended();
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                if (delete && eliminar) {
                                    for (int i = 0; i < devMinExt.getSurtimientoEnviadoExtList().size(); i++) {
                                        SurtimientoEnviado_Extend surtEnvExtRemove = devMinExt.getSurtimientoEnviadoExtList().get(i);
                                        if (surtEnvExtRemove.getIdSurtimientoInsumo().equals(idSurtimientoInsumo)
                                                && surtEnvExtRemove.getLote().equals(loteMedicamento)
                                                && surtEnvExtRemove.getTipoDevolucion() == tipoDevolucion) {
                                            devMinExt.setCantidadDevuelta(devMinExt.getCantidadDevuelta() - cantDevolver);
                                            devMinExt.getSurtimientoEnviadoExtList().remove(i);
                                            check = true;
                                            break;
                                        }
                                    }
                                    if (devMinExt.getSurtimientoEnviadoExtList().isEmpty()) {
                                        devolucionDetalleExtList.remove(index);
                                    }
                                    if (!check) {
                                        messageError.set(0, Constantes.MENSAJE_ERROR);
                                        messageError.set(1, "No se encontró nungún registro con el motivo seleccionado");
                                        surtimiento = false;
                                        devolucion = false;
                                        codigoBarras = "";
                                        medicamento = new Medicamento_Extended();
                                    } else {
                                        error = false;
                                        surtimiento = false;
                                        devolucion = false;
                                        codigoBarras = "";
                                        medicamento = new Medicamento_Extended();
                                    }
                                }
                                if (lote && !caducidad) {
                                    messageError.set(0, Constantes.MENSAJE_ERROR);
                                    messageError.set(1, laCaducidadNOCorrecta + claveMedicamento);
                                    codigoBarras = "";
                                    medicamento = new Medicamento_Extended();
                                }

                                if (!lote) {
                                    messageError.set(0, Constantes.MENSAJE_ERROR);
                                    messageError.set(1, loteNOcorrecto + claveMedicamento);
                                    codigoBarras = "";
                                    medicamento = new Medicamento_Extended();
                                }
                            } catch (Exception ex) {
                                LOGGER.error("Error al agregar medicamento: {}", ex.getMessage());
                            }
                        }
                    }
                    // Agrega Lote nuevo
                    if (surtimiento && devolucion) {
                        surtimiento = false;
                        devolucion = false;
                        if (surtInsumoExtList != null) {
                            for (SurtimientoInsumo_Extend surte : surtInsumoExtList) {
                                if (surte.getClaveInstitucional().equals(claveMedicamento) && surte.getSurtimientoEnviadoExtendList() != null) {
                                    for (SurtimientoEnviado_Extend surte_env : surte.getSurtimientoEnviadoExtendList()) {
                                        try {
                                            if (surte_env.getLote().equals(loteMedicamento)) {
                                                lote = true;
                                                if (surte_env.getCaducidad().equals(caducidadMedicamento)) {
                                                    caducidad = true;
                                                    if (devolucionDetalleExtList != null) {
                                                        for (DevolucionMinistracionDetalleExtended dev_ext : devolucionDetalleExtList) {
                                                            if (surte.getClaveInstitucional().equals(dev_ext.getClaveInstitucional())) {
                                                                surte_env.setCantidadDevolver(0);
                                                                if (dev_ext.getCantidadMaxima() == 1) {
                                                                    cantidadMaxima = dev_ext.getCantidadMaxima();
                                                                } else if ((dev_ext.getCantidadMaxima() - dev_ext.getCantidadDevuelta()) <= 0) {
                                                                    messageError.set(0, Constantes.MENSAJE_ERROR);
                                                                    messageError.set(1, "De este medicamento ya no hay más pz por devolver ");
                                                                    codigoBarras = "";
                                                                    medicamento = new Medicamento_Extended();
                                                                    cant = true;
                                                                } else {
                                                                    cantidadMaxima = dev_ext.getCantidadMaxima() - dev_ext.getCantidadDevuelta();
                                                                }
                                                                if (surte_env.getCantidadDevolver() > cantidadMaxima) {
                                                                    cantidad = cantidadMaxima;
                                                                } else {
                                                                    cantidad = surte_env.getCantidadDevolver();
                                                                }
                                                                if ((surte_env.getCantidadDevolver() + cantDevolver) <= surte_env.getCantidadEnviado() && (dev_ext.getCantidadDevuelta() + cantDevolver) != dev_ext.getCantidadDevuelta()) {
                                                                    surte_env.setCantidadDevolver(surte_env.getCantidadDevolver() + cantDevolver);
                                                                    dev_ext.setCantidadDevuelta(dev_ext.getCantidadDevuelta() + cantDevolver);
                                                                    surte_env.setTipoDevolucion(tipoDevolucion);
                                                                    surte_env.setTipoDevolucion(tipoDevolucion);
                                                                    surte_env.setConforme(conforme);
                                                                    dev_ext.getSurtimientoEnviadoExtList().add(surte_env);
                                                                    error = false;
                                                                    codigoBarras = "";
                                                                    medicamento = new Medicamento_Extended();
                                                                    checkCant = true;
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            if (!lote) {
                                                messageError.set(0, Constantes.MENSAJE_ERROR);
                                                messageError.set(1, loteNOcorrecto + claveMedicamento);
                                                codigoBarras = "";
                                                medicamento = new Medicamento_Extended();
                                            }

                                            if (lote && !caducidad) {
                                                messageError.set(0, Constantes.MENSAJE_ERROR);
                                                messageError.set(1, laCaducidadNOCorrecta + claveMedicamento);
                                                codigoBarras = "";
                                                medicamento = new Medicamento_Extended();
                                            }
                                            if (caducidad && !checkCant) {
                                                messageError.set(0, Constantes.MENSAJE_ERROR);
                                                messageError.set(1, soloPuedenDevolver + surte_env.getCantidadRecibido() + medicamentosParaLote + loteMedicamento);
                                                codigoBarras = "";
                                                medicamento = new Medicamento_Extended();
                                            }
                                        } catch (Exception ex) {
                                            LOGGER.error("Error al agregar medicamento: {}", ex.getMessage());
                                        }
                                    }
                                }
                            }
                        }
                    }
                        //Motivo diferente                        

                    if (surtimiento && !devolucion) {
                        for (int i = 0; i < surtInsumoExtList.size(); i++) {
                            SurtimientoInsumo_Extend surInsEx = surtInsumoExtList.get(i);
                            if (surInsEx.getClaveInstitucional().equals(claveMedicamento) && surInsEx.getSurtimientoEnviadoExtendList() != null) {
                                lote = false;
                                caducidad = false;
                                cantidad = 0;
                                exit:
                                for (int j = 0; j < surInsEx.getSurtimientoEnviadoExtendList().size(); j++) {
                                    SurtimientoEnviado_Extend surEnvExt = surInsEx.getSurtimientoEnviadoExtendList().get(j);
                                    if (surEnvExt.getLote().equals(loteMedicamento)) {
                                        lote = true;
                                        if (surEnvExt.getCaducidad().equals(caducidadMedicamento)) {
                                            try {
                                                caducidad = true;
                                                if (surEnvExt.getCantidadDevolver() == null) {
                                                    surEnvExt.setCantidadDevolver(0);
                                                }
                                                if ((surEnvExt.getCantidadDevolver() + cantDevolver) <= surEnvExt.getCantidadRecibido()) {
                                                    surEnvExt.setCantidadDevolver(surEnvExt.getCantidadDevolver() + cantDevolver);
                                                    cant = true;
                                                } else {
                                                    messageError.set(0, Constantes.MENSAJE_ERROR);
                                                    messageError.set(1, soloPuedenDevolver + surEnvExt.getCantidadEnviado() + medicamentosParaLote + loteMedicamento);
                                                    cant = false;
                                                    codigoBarras = "";
                                                    medicamento = new Medicamento_Extended();
                                                }
                                                List<DevolucionMinistracionDetalleExtended> devolucionDetalleExtList2 = surtimientoService.obtenerDevolucionDetalleExtPorIdSurtimiento(devMinistracionExt.getIdSurtimiento());
                                                for (DevolucionMinistracionDetalleExtended cnsmr : devolucionDetalleExtList2) {
                                                    if (cnsmr.getClaveInstitucional().equals(claveMedicamento)) {
                                                        if (cnsmr.getCantidadMaxima() == 0 || (cnsmr.getCantidadMaxima() - cnsmr.getCantidadDevuelta()) <= 0) {
                                                            messageError.set(0, Constantes.MENSAJE_ERROR);
                                                            messageError.set(1, medicamentoSinPz);
                                                            codigoBarras = "";
                                                            medicamento = new Medicamento_Extended();
                                                        } else {
                                                            cantidadMaxima = cnsmr.getCantidadMaxima() - cnsmr.getCantidadDevuelta();
                                                        }
                                                    }
                                                }

                                                if (cant) {
                                                    if (surEnvExt.getCantidadDevolver() <= cantidadMaxima) {
                                                        cantidad = surEnvExt.getCantidadDevolver();
                                                    } else {
                                                        messageError.set(0, Constantes.MENSAJE_ERROR);
                                                        messageError.set(1, medicamentoSinPz);
                                                        codigoBarras = "";
                                                        medicamento = new Medicamento_Extended();
                                                    }
                                                    DevolucionMinistracionDetalleExtended devo = new DevolucionMinistracionDetalleExtended();
                                                    devo.setCantidadDevuelta(cantidad);
                                                    devo.setCantidadMaxima(surInsEx.getCantidadRecepcion());
                                                    devo.setCantidadMaxima(cantidadMaxima);

                                                    devo.setClaveInstitucional(claveMedicamento);
                                                    devo.setIdSurtimientoInsumo(surEnvExt.getIdSurtimientoInsumo());
                                                    devo.setNombreCorto(surInsEx.getNombreCorto());
                                                    devo.setIdInsumo(surEnvExt.getIdInsumo());
                                                    devo.setIdEstatusDevolucion(EstatusDevolucion_Enum.ENTRANSITO.getValue());
                                                    devo.setCantidad(cantidad);
                                                    devo.setIdMotivoDevolucion(tipoDevolucion);
                                                    devo.setTipoDevolucion(tipoDevolucion);
                                                    devo.setConforme(conforme);
                                                    devo.setIdInventario(surEnvExt.getIdInventarioSurtido());

                                                    SurtimientoEnviado_Extend detall = new SurtimientoEnviado_Extend();
                                                    detall.setLote(loteMedicamento);
                                                    detall.setCantidadDevolver(cantidad);
                                                    detall.setCantidadEnviado(surEnvExt.getCantidadRecibido());
                                                    detall.setCantidadRecibido(surEnvExt.getCantidadRecibido());
                                                    detall.setIdInsumo(surEnvExt.getIdInsumo());
                                                    detall.setIdInventarioSurtido(surEnvExt.getIdInventarioSurtido());
                                                    detall.setIdSurtimientoEnviado(surEnvExt.getIdSurtimientoEnviado());
                                                    detall.setIdSurtimientoInsumo(surEnvExt.getIdSurtimientoInsumo());
                                                    detall.setCaducidad(caducidadMedicamento);
                                                    detall.setConforme(conforme);
                                                    detall.setTipoDevolucion(tipoDevolucion);

                                                    List<SurtimientoEnviado_Extend> detallList = new ArrayList<>();
                                                    detallList.add(detall);
                                                    devo.setSurtimientoEnviadoExtList(detallList);
                                                    if (devolucionDetalleExtList == null) {
                                                        devolucionDetalleExtList = new ArrayList<>();
                                                    }

                                                    devolucionDetalleExtList.add(devo);
                                                    PrimeFaces.current().ajax().addCallbackParam("devolucionDetalleExtList", devolucionDetalleExtList);
                                                    error = false;
                                                    numeroMedDetalles = devolucionDetalleExtList.size();
                                                    codigoBarras = "";
                                                    medicamento = new Medicamento_Extended();
                                                    break;
                                                }
                                            } catch (Exception ex) {
                                                LOGGER.error("Error al agregar medicamento: {}", ex.getMessage());
                                                codigoBarras = "";
                                                medicamento = new Medicamento_Extended();
                                            }
                                        }
                                    }
                                    if (!lote) {
                                        messageError.set(0, Constantes.MENSAJE_ERROR);
                                        messageError.set(1, loteNOcorrecto + claveMedicamento);
                                        codigoBarras = "";
                                        medicamento = new Medicamento_Extended();
                                    }
                                    if (!caducidad && lote) {
                                        messageError.set(0, Constantes.MENSAJE_ERROR);
                                        messageError.set(1, laCaducidadNOCorrecta + claveMedicamento);
                                        codigoBarras = "";
                                        medicamento = new Medicamento_Extended();
                                    }
                                }
                            }
                        }
                    }
                } else {
                    messageError.set(0, Constantes.MENSAJE_ERROR);
                    messageError.set(1, "El medicamento no se encontró en el surtimiento");
                    codigoBarras = "";
                    medicamento = new Medicamento_Extended();
                }

            } else {
                messageError.set(0, Constantes.MENSAJE_ERROR);
                messageError.set(1, "El código no tiene el formato correcto");
                codigoBarras = "";
                cantDevolver = 1;
                codigoBarras = "";
                medicamento = new Medicamento_Extended();
            }
        }
        if (error) {
            Mensaje.showMessage(messageError.get(0), messageError.get(1), null);
        }
    }

    public void obtenerDetalleSurtimiento(DevolucionMinistracionExtended item) {
        showMjs = Constantes.INACTIVO;
        showMjsSearch = Constantes.INACTIVO;
        if (permiso.isPuedeEditar()) {
            try {
                codigoBarras = "";
                comentarios = "";
                tipoDevolucion = 0;
                devMinistracionExt = item;
                if (item.getIdEstatusDevolucion() == 0 || item.getIdEstatusDevolucion() == 1 || item.getIdEstatusDevolucion() == 2) {
                    edit = Constantes.ACTIVO;
                    isNew = Constantes.ACTIVO;
                } else {
                    isNew = Constantes.INACTIVO;
                    edit = Constantes.INACTIVO;
                }
                if (!edit) {
                    tipoDevolucion = item.getIdEstatusDevolucion();
                }
                //Obtenemos el total de los medicamentos del 
                surtInsumoExtList = surtimientoService.detalleSurtimientoInsumoExtRecepMedi(item.getIdSurtimiento());
                devolucionDetalleExtList = surtimientoService.obtenerDevolucionDetalleExtPorIdSurtimiento(item.getIdSurtimiento());

                numeroMedDetalles = devolucionDetalleExtList.size();
            } catch (Exception ex) {
                LOGGER.error("Error al obtener los Medicamentos: {}", ex.getMessage());
            }
        } else {
            Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString("devolmedica.warn.sinPermisos"), null);
        }
    }

    public void procesarMedicamentoDevolucion() {
        boolean resp = Constantes.INACTIVO;
        showMjs = Constantes.INACTIVO;
        showMjsSearch = Constantes.INACTIVO;
        status = Constantes.INACTIVO;
        try {
            if (permiso.isPuedeCrear()) {
                if (!devolucionDetalleExtList.isEmpty()) {
                    for (DevolucionMinistracionDetalleExtended detalle : devolucionDetalleExtList) {
                        if (detalle.getCantidadDevuelta() > 0) {
                            for (SurtimientoEnviado_Extend detEnv : detalle.getSurtimientoEnviadoExtList()) {

                                if (detEnv.getTipoDevolucion() != null && detEnv.getTipoDevolucion() > 0) {
                                    detalle.setInsertFecha(new Date());
                                    detalle.setInsertIdUsuario(currentUser.getIdUsuario());

                                    devMinistracionExt.setFechaDevolucion(new Date());
                                    devMinistracionExt.setIdMotivoDevolucion(detalle.getTipoDevolucion());
                                    devMinistracionExt.setComentarios(comentarios);
                                    devMinistracionExt.setIdEstatusDevolucion(EstatusDevolucion_Enum.ENTRANSITO.getValue());
                                }
                            }

                        }
                    }
                    if (isNew) {
                        devMinistracionExt.setIdDevolucionMinistracion(Comunes.getUUID());
                        devMinistracionExt.setInsertFecha(new Date());
                        devMinistracionExt.setInsertIdUsuario(currentUser.getIdUsuario());
                        devMinistracionExt.setIdUsuarioDevolucion(currentUser.getIdUsuario());
                        resp = devolucionMiniExtService.insertDevolucionExt(devMinistracionExt, devolucionDetalleExtList);
                    } else {
                        devMinistracionExt.setIdUsuarioDevolucion(currentUser.getIdUsuario());
                        devMinistracionExt.setUpdateFecha(new Date());
                        devMinistracionExt.setUpdateIdUsuario(currentUser.getIdUsuario());
                        resp = devolucionMiniExtService.actualizaDevolucionExt(devMinistracionExt, devolucionDetalleExtList);
                    }
                    if (resp) {
                        boolean exis = Constantes.INACTIVO;
                        for (DevolucionMinistracionExtended devo : devolucionList) {
                            if (devo.getIdDevolucionMinistracion().equals(devMinistracionExt.getIdDevolucionMinistracion())) {
                                exis = Constantes.ACTIVO;
                                devo.setEstatusDevolicion("En transito");
                                break;
                            }
                        }
                        if (!exis) {
                            devMinistracionExt.setEstatusDevolicion("En transito");
                            devolucionList.add(devMinistracionExt);
                        }
                        status = Constantes.ACTIVO;
                        Mensaje.showMessage(Constantes.MENSAJE_INFO, "Se ha enviado la devolución", null);
                    } else {
                        Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString("Hubó un error al momento de realizar la devolución verifique la información"), null);
                    }

                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Debe agregar al menos un medicamento.", null);
                }
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString("devolmedica.warsn.sinPermisoSufic"), null);
            }
        } catch (Exception ex) {
            LOGGER.error("Ocurrio una Excepcion al procesar el envio: {}", ex.getMessage());
        }
        pacienteExtended = null;
        pacienteDev = null;
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    /*Getter & Setter*/
    public List<SurtimientoMinistrado> getMinistracionList() {
        return ministracionList;
    }

    public void setMinistracionList(List<SurtimientoMinistrado> ministracionList) {
        this.ministracionList = ministracionList;
    }

    public boolean isShowMjs() {
        return showMjs;
    }

    public void setShowMjs(boolean showMjs) {
        this.showMjs = showMjs;
    }

    public SurtimientoMinistradoService getMinistracionService() {
        return ministracionService;
    }

    public void setMinistracionService(SurtimientoMinistradoService ministracionService) {
        this.ministracionService = ministracionService;
    }

    public SurtimientoMinistrado getMinistracion() {
        return ministracion;
    }

    public void setMinistracion(SurtimientoMinistrado ministracion) {
        this.ministracion = ministracion;
    }

    public int getCantDevolver() {
        return cantDevolver;
    }

    public void setCantDevolver(int cantDevolver) {
        this.cantDevolver = cantDevolver;
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public boolean isIsNew() {
        return isNew;
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    public List<DevolucionMinistracionExtended> getDevolucionList() {
        return devolucionList;
    }

    public void setDevolucionList(List<DevolucionMinistracionExtended> devolucionList) {
        this.devolucionList = devolucionList;
    }

    public DevolucionMinistracionService getDevolucionMiniExtService() {
        return devolucionMiniExtService;
    }

    public void setDevolucionMiniExtService(DevolucionMinistracionService devolucionService) {
        this.devolucionMiniExtService = devolucionService;
    }

    public DevolucionMinistracionExtended getDevMinistracionExt() {
        return devMinistracionExt;
    }

    public void setDevMinistracionExt(DevolucionMinistracionExtended devMinistracionExt) {
        this.devMinistracionExt = devMinistracionExt;
    }

    public DevolucionMinistracionDetalleService getDevolucionDetalleService() {
        return devolucionDetalleService;
    }

    public void setDevolucionDetalleService(DevolucionMinistracionDetalleService devolucionDetalleService) {
        this.devolucionDetalleService = devolucionDetalleService;
    }

    public DevolucionMinistracionDetalleExtended getDevMiniDetalleExt() {
        return devMiniDetalleExt;
    }

    public void setDevMiniDetalleExt(DevolucionMinistracionDetalleExtended devMiniDetalleExt) {
        this.devMiniDetalleExt = devMiniDetalleExt;
    }

    public List<DevolucionMinistracionDetalleExtended> getDevolucionDetalleExtList() {
        return devolucionDetalleExtList;
    }

    public void setDevolucionDetalleExtList(List<DevolucionMinistracionDetalleExtended> devolucionDetalleExtList) {
        this.devolucionDetalleExtList = devolucionDetalleExtList;
    }

    public int getTipoDevolucion() {
        return tipoDevolucion;
    }

    public void setTipoDevolucion(int tipoDevolucion) {
        this.tipoDevolucion = tipoDevolucion;
    }

    public TipoMotivoService getTipoMotivoService() {
        return tipoMotivoService;
    }

    public void setTipoMotivoService(TipoMotivoService tipoMotivoService) {
        this.tipoMotivoService = tipoMotivoService;
    }

    public List<TipoMotivo> getTipoMotivoList() {
        return tipoMotivoList;
    }

    public void setTipoMotivoList(List<TipoMotivo> tipoMotivoList) {
        this.tipoMotivoList = tipoMotivoList;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getIdEstructuraUser() {
        return idEstructuraUser;
    }

    public void setIdEstructuraUser(String idEstructuraUser) {
        this.idEstructuraUser = idEstructuraUser;
    }

    public String getTipoPrescripcion() {
        return tipoPrescripcion;
    }

    public void setTipoPrescripcion(String tipoPrescripcion) {
        this.tipoPrescripcion = tipoPrescripcion;
    }

    public String getCadenaBusqueda() {
        return cadenaBusqueda;
    }

    public void setCadenaBusqueda(String cadenaBusqueda) {
        this.cadenaBusqueda = cadenaBusqueda;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getComentarios() {
        return comentarios;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public int getNumeroRegistros() {
        return numeroRegistros;
    }

    public void setNumeroRegistros(int numeroRegistros) {
        this.numeroRegistros = numeroRegistros;
    }

    public int getNumeroMedDetalles() {
        return numeroMedDetalles;
    }

    public void setNumeroMedDetalles(int numeroMedDetalles) {
        this.numeroMedDetalles = numeroMedDetalles;
    }

    public String getFolioPrescripcion() {
        return folioPrescripcion;
    }

    public void setFolioPrescripcion(String folioPrescripcion) {
        this.folioPrescripcion = folioPrescripcion;
    }

    public String getFechaPrescripcion() {
        return fechaPrescripcion;
    }

    public void setFechaPrescripcion(String fechaPrescripcion) {
        this.fechaPrescripcion = fechaPrescripcion;
    }

    public String getClaveMedicamento() {
        return claveMedicamento;
    }

    public void setClaveMedicamento(String claveMedicamento) {
        this.claveMedicamento = claveMedicamento;
    }

    public String getLoteMedicamento() {
        return loteMedicamento;
    }

    public void setLoteMedicamento(String loteMedicamento) {
        this.loteMedicamento = loteMedicamento;
    }

    public Date getCaducidadMedicamento() {
        return caducidadMedicamento;
    }

    public void setCaducidadMedicamento(Date caducidadMedicamento) {
        this.caducidadMedicamento = caducidadMedicamento;
    }

    public Usuario getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Usuario currentUser) {
        this.currentUser = currentUser;
    }

    public EstructuraService getEstructuraService() {
        return estructuraService;
    }

    public void setEstructuraService(EstructuraService estructuraService) {
        this.estructuraService = estructuraService;
    }

    public Estructura getEstructuraSelect() {
        return estructuraSelect;
    }

    public void setEstructuraSelect(Estructura estructuraSelect) {
        this.estructuraSelect = estructuraSelect;
    }

    public List<Estructura> getEstructuraList() {
        return estructuraList;
    }

    public void setEstructuraList(List<Estructura> estructuraList) {
        this.estructuraList = estructuraList;
    }

    public SurtimientoService getSurtimientoService() {
        return surtimientoService;
    }

    public void setSurtimientoService(SurtimientoService surtimientoService) {
        this.surtimientoService = surtimientoService;
    }

    public SurtimientoEnviado_Extend getSurtimientoEnviado() {
        return surtimientoEnviado;
    }

    public void setSurtimientoEnviado(SurtimientoEnviado_Extend surtimientoEnviado) {
        this.surtimientoEnviado = surtimientoEnviado;
    }

    public List<SurtimientoEnviado_Extend> getSurtEnviadoList() {
        return surtEnviadoList;
    }

    public void setSurtEnviadoList(List<SurtimientoEnviado_Extend> surtEnviadoList) {
        this.surtEnviadoList = surtEnviadoList;
    }

    public VistaRecepcionMedicamentoService getViewRecepcionService() {
        return viewRecepcionService;
    }

    public void setViewRecepcionService(VistaRecepcionMedicamentoService viewRecepcionService) {
        this.viewRecepcionService = viewRecepcionService;
    }

    public VistaRecepcionMedicamento getViewRecepcionMed() {
        return viewRecepcionMed;
    }

    public void setViewRecepcionMed(VistaRecepcionMedicamento viewRecepcionMed) {
        this.viewRecepcionMed = viewRecepcionMed;
    }

    public List<VistaRecepcionMedicamento> getViewRecepcionList() {
        return viewRecepcionList;
    }

    public void setViewRecepcionList(List<VistaRecepcionMedicamento> viewRecepcionList) {
        this.viewRecepcionList = viewRecepcionList;
    }

    public ParamBusquedaReporte getParamBusquedaReporte() {
        return paramBusquedaReporte;
    }

    public void setParamBusquedaReporte(ParamBusquedaReporte paramBusquedaReporte) {
        this.paramBusquedaReporte = paramBusquedaReporte;
    }

    public Paciente getPacienteExtended() {
        return pacienteExtended;
    }

    public void setPacienteExtended(Paciente pacienteExtended) {
        this.pacienteExtended = pacienteExtended;
    }

    public List<DevolucionMinistracionExtended> getDevolucionListFolio() {
        return devolucionListFolio;
    }

    public void setDevolucionListFolio(List<DevolucionMinistracionExtended> devolucionListFolio) {
        this.devolucionListFolio = devolucionListFolio;
    }

    public boolean isConforme() {
        return conforme;
    }

    public void setConforme(boolean conforme) {
        this.conforme = conforme;
    }

    public List<TipoMotivo> getTipoMotivoListAux() {
        return tipoMotivoListAux;
    }

    public void setTipoMotivoListAux(List<TipoMotivo> tipoMotivoListAux) {
        this.tipoMotivoListAux = tipoMotivoListAux;
    }

    public TipoMotivo getTipoMotivoListObject() {
        return tipoMotivoListObject;
    }

    public void setTipoMotivoListObject(TipoMotivo tipoMotivoListObject) {
        this.tipoMotivoListObject = tipoMotivoListObject;
    }

    public boolean isEliminar() {
        return eliminar;
    }

    public void setEliminar(boolean eliminar) {
        this.eliminar = eliminar;
    }

    public Medicamento_Extended getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento_Extended medicamento) {
        this.medicamento = medicamento;
    }

    public List<Medicamento_Extended> getMedicamentoList() {
        return medicamentoList;
    }

    public void setMedicamentoList(List<Medicamento_Extended> medicamentoList) {
        this.medicamentoList = medicamentoList;
    }

    public boolean isShowMjsSearch() {
        return showMjsSearch;
    }

    public void setShowMjsSearch(boolean showMjsSearch) {
        this.showMjsSearch = showMjsSearch;
    }

    public DevolucionMinistracionLazy getDevolucionMinistracionLazy() {
        return devolucionMinistracionLazy;
    }

    public void setDevolucionMinistracionLazy(DevolucionMinistracionLazy devolucionMinistracionLazy) {
        this.devolucionMinistracionLazy = devolucionMinistracionLazy;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }
    
}                   
