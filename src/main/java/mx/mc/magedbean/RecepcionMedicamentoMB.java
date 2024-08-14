/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.CodigoInsumo;
import mx.mc.model.Estructura;
import mx.mc.model.Medicamento_Extended;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.SurtimientoEnviado;
import mx.mc.model.SurtimientoInsumo_Extend;
import mx.mc.model.Usuario;
import mx.mc.model.VistaRecepcionMedicamento;
import mx.mc.service.EstructuraService;
import mx.mc.service.MedicamentoService;
import mx.mc.service.SurtimientoInsumoService;
import mx.mc.service.SurtimientoService;
import mx.mc.service.VistaRecepcionMedicamentoService;
import mx.mc.util.CodigoBarras;
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
public class RecepcionMedicamentoMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(RecepcionMedicamentoMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private boolean isAdmin;
    private PermisoUsuario permiso;
    private boolean status;
    private String idEstructuraUser;
    private String tipoPrescripcion;
    private String cadenaBusqueda;
    private String codigoBarras;
    private int cantRecibir;
    private int numeroRegistros;
    private int numeroMedDetalles;
    //datos del codigo de barras Prescripcion
    private String folioPrescripcion;
    private String fechaPrescripcion;
    //datos del codigo de barras del medicamento
    private String claveMedicamento;
    private String loteMedicamento;
    private Date caducidadMedicamento;
    private boolean eliminarCodigo;
    private boolean clave;
    private boolean lote;
    private boolean msgModal;
    private int resul = 0;
    private int enviado = 0;
    private Date fechaActual;
    private Usuario currentUser;
    private String nombreIngrediente;
    private double velocidad;
    private double ritmo;
    private Integer perfusionContinua;
    private String unidadConcentracion;
    private boolean mostrarInformacionSolucion;
    private String claveAgrupada;
    private boolean activaAutoCompleteInsumos;

    @Autowired
    private transient EstructuraService estructuraService;
    private Estructura estructuraSelect;
    private List<Estructura> estructuraList;

    @Autowired
    private transient SurtimientoService surtimientoService;
    @Autowired
    private transient SurtimientoInsumoService surtimientoInsumoService;

    private SurtimientoInsumo_Extend surtimientoInsumoExt;
    private List<SurtimientoInsumo_Extend> surtInsumoExtList;
    private SurtimientoEnviado surtimientoEnviado;
    private List<SurtimientoEnviado> surtEnviadoList;

    @Autowired
    private transient VistaRecepcionMedicamentoService viewRecepcionService;
    private VistaRecepcionMedicamento viewRecepcionMed;
    private List<VistaRecepcionMedicamento> viewRecepcionList;
    private VistaRecepcionMedicamento surtimientoSelect;
    private Medicamento_Extended medicamento;
    private List<Medicamento_Extended> medicamentoList;

    @Autowired
    private transient MedicamentoService medicamentoServiceautoComplete;

    @PostConstruct
    public void init() {
        initialize();
        permiso = Comunes.obtenerPermisos(Transaccion_Enum.RECEPMEDICAMENTO.getSufijo());
        obtieneAlmacen();
        obtenerSutimientoEnviado();
        fechaActual = new Date();
    }

    /*Methods Private*/
    private void initialize() {

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        msgModal = Constantes.INACTIVO;
        currentUser = sesion.getUsuarioSelected();
        estructuraSelect = new Estructura();
        surtimientoEnviado = new SurtimientoEnviado();
        viewRecepcionMed = new VistaRecepcionMedicamento();

        estructuraList = new ArrayList<>();
        surtEnviadoList = new ArrayList<>();
        viewRecepcionList = new ArrayList<>();
        this.claveAgrupada = "";
        nombreIngrediente = "";
        velocidad = 0;
        ritmo = 0;
        perfusionContinua = 0;
        unidadConcentracion = "";
        mostrarInformacionSolucion = false;
        activaAutoCompleteInsumos = sesion.isActivaAutocompleteQrClave();
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

    public void ingresarInsumosPorCodigo() throws Exception {
        LOGGER.debug("mx.mc.magedbean.RecepcionMedicamentoMB.ingresarInsumosPorCodigo()");
        if (this.claveAgrupada != null) {

            List<SurtimientoInsumo_Extend> surtInsumoList;
            surtInsumoList = surtimientoInsumoService.obtenerSurtimInsumoByClaveAgrupada(this.claveAgrupada);
            if (!surtInsumoList.isEmpty()) {

                for (SurtimientoInsumo_Extend surInsumoAgrupada : surtInsumoList) {
                    resul = 0;
                    enviado = 0;
                    claveAgrupada = CodigoBarras.generaCodigoDeBarras(surInsumoAgrupada.getClaveInstitucional(), surInsumoAgrupada.getLote(), surInsumoAgrupada.getCaducidadAgrupada(), null);
                    if (permiso.isPuedeCrear()) {
                        try {
                            clave = Constantes.INACTIVO;
                            lote = Constantes.INACTIVO;
                            if (tratarCodigoDeBarrasMedicamento(claveAgrupada)) {
                                cantRecibir = surInsumoAgrupada.getCantidadEnviada();
                                surtInsumoExtList.stream().filter(prdct -> prdct.getClaveInstitucional().equals(claveMedicamento)).forEach(cnsmr -> {
                                    clave = Constantes.ACTIVO;
                                    //Validar si el Medicamento esta Bloqueado
                                    if (cnsmr.isActivo()) {
                                        if (cnsmr.getSurtimientoEnviadoExtendList() != null) {
                                            cnsmr.getSurtimientoEnviadoExtendList().stream().filter(prdct2 -> prdct2.getLote().equals(loteMedicamento)).forEach(cnsmr2 -> {
                                                lote = Constantes.ACTIVO;
                                                //Validar si el lote esta bloqueado
                                                if (cnsmr2.getActivo() > 0) {
                                                    if (cnsmr2.getCaducidad().equals(caducidadMedicamento)) {
                                                        //Validar si la fecha de caducidad es mayor a la fecha actual
                                                        if (FechaUtil.isFechaMayorIgualQue(cnsmr2.getCaducidad(), fechaActual)) {
                                                            if (eliminarCodigo) {
                                                                resul = cnsmr2.getCantidadRecibido() - cantRecibir;
                                                                if (resul < 0) {
                                                                    resul = 0;
                                                                }
                                                            } else {
                                                                resul = cnsmr2.getCantidadRecibido() + cantRecibir;
                                                            }

                                                            if (resul <= cnsmr2.getCantidadEnviado()) {
                                                                enviado = cnsmr2.getCantidadRecibido();
                                                                cnsmr2.setCantidadRecibido(resul);
                                                                if (eliminarCodigo) {
                                                                    resul = enviado - cantRecibir;
                                                                    if (resul < 0) {
                                                                        cantRecibir = enviado;
                                                                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.lotesinescanear"), null);
                                                                        claveAgrupada = "";
                                                                        medicamento = new Medicamento_Extended();
                                                                    }

                                                                    cnsmr.setCantidadRecepcion(cnsmr.getCantidadRecepcion() - cantRecibir);
                                                                    claveAgrupada = "";
                                                                    medicamento = new Medicamento_Extended();
                                                                } else {
                                                                    cnsmr.setCantidadRecepcion(cnsmr.getCantidadRecepcion() + cantRecibir);
                                                                    claveAgrupada = "";
                                                                    medicamento = new Medicamento_Extended();
                                                                }

                                                            } else {
                                                                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("recepmedicamento.err.cantMayor"), null);
                                                                claveAgrupada = "";
                                                                medicamento = new Medicamento_Extended();
                                                            }
                                                        } else {
                                                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("recepmedicamento.err.medCaducado"), null);
                                                            claveAgrupada = "";
                                                            medicamento = new Medicamento_Extended();
                                                        }
                                                    } else {
                                                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("recepmedicamento.err.caducInvalida"), null);
                                                        claveAgrupada = "";
                                                        medicamento = new Medicamento_Extended();
                                                    }
                                                } else {
                                                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("recepmedicamento.err.lotebloqueado"), null);
                                                    claveAgrupada = "";
                                                    medicamento = new Medicamento_Extended();
                                                }
                                            });
                                            if (!lote) {
                                                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("recepmedicamento.err.noLote"), null);
                                                claveAgrupada = "";
                                                medicamento = new Medicamento_Extended();
                                            }
                                        }
                                    } else {
                                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("recepmedicamento.err.medibloqueado"), null);
                                        claveAgrupada = "";
                                        medicamento = new Medicamento_Extended();
                                    }
                                });
                                if (!clave) {
                                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("recepmedicamento.err.noencontrado"), null);
                                    claveAgrupada = "";
                                    medicamento = new Medicamento_Extended();
                                }
                            } else {
                                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("recepmedicamento.err.codigoIncorrecto"), null);
                                claveAgrupada = "";
                                medicamento = new Medicamento_Extended();
                            }
                            //se comenta ya que no tomamos en cuenta este valor si no los que vienen en la clave agrupada
                            claveAgrupada = "";
                            claveMedicamento = "";
                            loteMedicamento = "";
                            caducidadMedicamento = null;

                        } catch (Exception ex) {
                            LOGGER.error("Error al recibir el medicamento: {}", ex.getMessage());
                            claveAgrupada = "";
                            medicamento = new Medicamento_Extended();
                        }
                    } else {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("recepmedicamento.err.sinpermisoProces"), null);
                        claveAgrupada = "";
                        medicamento = new Medicamento_Extended();
                    }
                }
                cantRecibir = 1;
                eliminarCodigo = Constantes.INACTIVO;
            }
        }
    }

    public List<Medicamento_Extended> autoComplete(String cadena) {
        try {
            // TODO: MULTIALMACEN: Analizar el proceso de recepción sólo de los insumos surtidos por el almacén correspondiente
//            String idEstructura = surtimientoService.obtenerAlmacenPadreOfSurtimiento(currentUser.getIdEstructura());
//            if (idEstructura == null || idEstructura.isEmpty()) {
//                idEstructura = estructuraSelect.getIdEstructura();
//            }
            String idEstructura = surtimientoSelect.getIdEstructuraAlmacenPadre();
            medicamentoList = medicamentoServiceautoComplete.searchMedicamentoRecepcionAutoComplete(cadena.trim(), idEstructura, activaAutoCompleteInsumos);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener Medicamentos: {}", ex.getMessage());
        }
        if (medicamentoList.size() == 1) {
            String componentId = UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getClientId();
            String panel = componentId.replace(":", "\\\\:") + "_panel";
            PrimeFaces.current().executeScript("$('#" + panel + " .ui-autocomplete-item').trigger('click');");
        }
        return medicamentoList;
    }

    public void handleSelect(SelectEvent e) {
        medicamento = (Medicamento_Extended) e.getObject();
    }

    public void handleUnSelect() {
        medicamento = new Medicamento_Extended();
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
        viewRecepcionList = new ArrayList();
        try {
            List<String> idsEstructura = estructuraService.obtenerIdsEstructuraJerarquica(currentUser.getIdEstructura(), false);
            if (idsEstructura == null) {
                idsEstructura = new ArrayList<>();
            }
            idsEstructura.add(currentUser.getIdEstructura());
            viewRecepcionList = viewRecepcionService.obtenerSurtimientosPorRecibir(idsEstructura);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener Estructura: {}", ex.getMessage());
        }
        cadenaBusqueda = cadenaBusqueda.toUpperCase();
        List<VistaRecepcionMedicamento> listaPresc = new ArrayList<>();
        for (VistaRecepcionMedicamento vist : viewRecepcionList) {
            String nombre = "";
            String folioP = "";
            String folioS = "";
            String numPaciente = "";
            String cama = "";

            if (vist.getNombrePaciente() != null) {
                nombre = vist.getNombrePaciente().toUpperCase();
            }
            if (vist.getFolioPrescripcion() != null) {
                folioP = vist.getFolioPrescripcion().toUpperCase();
            }
            if (vist.getFolioSurtimiento() != null) {
                folioS = vist.getFolioSurtimiento().toUpperCase();
            }
            if (vist.getPacienteNumero() != null) {
                numPaciente = vist.getPacienteNumero().toUpperCase();
            }
            if (vist.getNombreCama() != null) {
                cama = vist.getNombreCama().toUpperCase();
            }
            if (nombre.contains(cadenaBusqueda) || folioP.contains(cadenaBusqueda) || folioS.contains(cadenaBusqueda)
                    || numPaciente.contains(cadenaBusqueda) || cama.contains(cadenaBusqueda)) {
                listaPresc.add(vist);
            }
        }
        return listaPresc;
    }

    private boolean tratarCodigoDeBarrasMedicamento(String codigo) {
        boolean resp = Constantes.INACTIVO;
        try {
            CodigoInsumo ci = CodigoBarras.parsearCodigoDeBarras(codigo);
            if (ci != null) {
                claveMedicamento = ci.getClave();
                loteMedicamento = ci.getLote();
                caducidadMedicamento = ci.getFecha();
                resp = Constantes.ACTIVO;
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo tratarCodigoDeBarras :: {}", e.getMessage());
        }
        return resp;
    }

    public void obtenerSutimientoEnviado() {
        if (permiso.isPuedeVer()) {
            try {
                viewRecepcionList = new ArrayList();
                List<String> idsEstructura = estructuraService.obtenerIdsEstructuraJerarquica(currentUser.getIdEstructura(), false);
                if (idsEstructura == null) {
                    idsEstructura = new ArrayList<>();
                }
                idsEstructura.add(currentUser.getIdEstructura());
                viewRecepcionList = viewRecepcionService.obtenerSurtimientosPorRecibir(idsEstructura);
                numeroRegistros = viewRecepcionList.size();
            } catch (Exception ex) {
                LOGGER.error("Error al obtener los surtimientos Enviados: {}", ex.getMessage());
            }
        } else {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("recepmedicamento.err.permTransacc"), null);
        }
    }

    public void onRowSelect(SelectEvent event) {
        this.surtimientoSelect = (VistaRecepcionMedicamento) event.getObject();
        obtenerDetalleSurtimiento();
    }

    public void obtenerDetalleSurtimiento(String idSurtimiento) {
        for (VistaRecepcionMedicamento item : viewRecepcionList) {
            if (item.getIdSurtimiento().equals(idSurtimiento)) {
                this.surtimientoSelect = item;
                break;
            }
        }
        obtenerDetalleSurtimiento();
    }

    public void obtenerDetalleSurtimiento() {
        if (permiso.isPuedeEditar()) {
            try {
                msgModal = Constantes.ACTIVO;
                codigoBarras = "";
                cantRecibir = 1;
                viewRecepcionMed = surtimientoSelect;
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
                surtInsumoExtList = surtimientoService.detalleSurtimientoInsumoExtRecepMedi(surtimientoSelect.getIdSurtimiento());
                numeroMedDetalles = surtInsumoExtList.size();
                mostrarInformacionSolucion = false;

                surtInsumoExtList.forEach(item -> {
                    if (item.getNombreIngrediente() != null) {
                        nombreIngrediente = item.getNombreIngrediente();
                        velocidad = item.getVelocidad();
                        ritmo = item.getRitmo();
                        perfusionContinua = item.getPerfusionContinua();
                        unidadConcentracion = item.getUnidadConcentracion();
                        mostrarInformacionSolucion = true;
                    }
                });
            } catch (Exception ex) {
                LOGGER.error("Error al obtener los Medicamentos: {}", ex.getMessage());
            }
        } else {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("recepmedicamento.err.permEditar"), null);
        }
    }

    public void receiveMedicamento(SelectEvent e) {
        medicamento = (Medicamento_Extended) e.getObject();
        resul = 0;
        enviado = 0;
        codigoBarras = CodigoBarras.generaCodigoDeBarras(medicamento.getClaveInstitucional(), medicamento.getLote(), medicamento.getFechaCaducidad(), null);
        if (codigoBarras != null) {
            if (permiso.isPuedeCrear()) {
                try {
                    clave = Constantes.INACTIVO;
                    lote = Constantes.INACTIVO;
                    if (tratarCodigoDeBarrasMedicamento(codigoBarras)) {
                        if (cantRecibir <= 0) {
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.error.cantidadIncorrecta"), null);
                            codigoBarras = "";
                            medicamento = new Medicamento_Extended();
                        } else {
                            surtInsumoExtList.stream().filter(prdct -> prdct.getClaveInstitucional().equals(claveMedicamento)).forEach(cnsmr -> {
                                clave = Constantes.ACTIVO;
                                //Validar si el Medicamento esta Bloqueado
                                if (cnsmr.isActivo()) {
                                    if (cnsmr.getSurtimientoEnviadoExtendList() != null) {
                                        cnsmr.getSurtimientoEnviadoExtendList().stream().filter(prdct2 -> prdct2.getLote().equals(loteMedicamento)).forEach(cnsmr2 -> {
                                            lote = Constantes.ACTIVO;
                                            //Validar si el lote esta bloqueado
                                            if (cnsmr2.getActivo() > 0) {
                                                if (cnsmr2.getCaducidad().equals(caducidadMedicamento)) {
                                                    //Validar si la fecha de caducidad es mayor a la fecha actual
                                                    if (FechaUtil.isFechaMayorIgualQue(cnsmr2.getCaducidad(), fechaActual)) {
                                                        if (cnsmr2.getCantidadRecibido() == null) {
                                                            cnsmr2.setCantidadRecibido(0);
                                                        }
                                                        if (eliminarCodigo) {
                                                            resul = cnsmr2.getCantidadRecibido() - cantRecibir;
                                                            if (resul < 0) {
                                                                resul = 0;
                                                            }
                                                        } else {
                                                            resul = cnsmr2.getCantidadRecibido() + cantRecibir;
                                                        }

                                                        if (resul <= cnsmr2.getCantidadEnviado()) {
                                                            enviado = cnsmr2.getCantidadRecibido();
                                                            if (eliminarCodigo) {
                                                                if (enviado == 0) {
                                                                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.lotesinescanear"), null);
                                                                    codigoBarras = "";
                                                                    medicamento = new Medicamento_Extended();
                                                                } else {
                                                                    resul = enviado - cantRecibir;
                                                                    if (resul < 0) {
                                                                        cantRecibir = enviado;
                                                                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.error.cantidadMayorLote"), null);
                                                                        codigoBarras = "";
                                                                        medicamento = new Medicamento_Extended();
                                                                    } else {
                                                                        cnsmr2.setCantidadRecibido(resul);
                                                                        cnsmr.setCantidadRecepcion(cnsmr.getCantidadRecepcion() - cantRecibir);
                                                                        codigoBarras = "";
                                                                        medicamento = new Medicamento_Extended();
                                                                    }
                                                                }

                                                            } else {
                                                                cnsmr2.setCantidadRecibido(resul);
                                                                cnsmr.setCantidadRecepcion(cnsmr.getCantidadRecepcion() + cantRecibir);
                                                                codigoBarras = "";
                                                                medicamento = new Medicamento_Extended();
                                                            }

                                                        } else {
                                                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("recepmedicamento.err.cantMayor"), null);
                                                            codigoBarras = "";
                                                            medicamento = new Medicamento_Extended();
                                                        }
                                                    } else {
                                                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("recepmedicamento.err.medCaducado"), null);
                                                        codigoBarras = "";
                                                        medicamento = new Medicamento_Extended();
                                                    }
                                                } else {
                                                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("recepmedicamento.err.caducInvalida"), null);
                                                    codigoBarras = "";
                                                    medicamento = new Medicamento_Extended();
                                                }
                                            } else {
                                                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("recepmedicamento.err.lotebloqueado"), null);
                                                codigoBarras = "";
                                                medicamento = new Medicamento_Extended();
                                            }
                                        });
                                        if (!lote) {
                                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("recepmedicamento.err.noLote"), null);
                                            codigoBarras = "";
                                            medicamento = new Medicamento_Extended();
                                        }
                                    }
                                } else {
                                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("recepmedicamento.err.medibloqueado"), null);
                                    codigoBarras = "";
                                    medicamento = new Medicamento_Extended();
                                }
                            });
                            if (!clave) {
                                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("recepmedicamento.err.noencontrado"), null);
                                codigoBarras = "";
                                medicamento = new Medicamento_Extended();
                            }
                        }

                    } else {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("recepmedicamento.err.codigoIncorrecto"), null);
                        codigoBarras = "";
                        medicamento = new Medicamento_Extended();
                    }

                    cantRecibir = 1;
                    codigoBarras = "";
                    claveMedicamento = "";
                    loteMedicamento = "";
                    caducidadMedicamento = null;
                    eliminarCodigo = Constantes.INACTIVO;
                } catch (Exception ex) {
                    LOGGER.error("Error al recibir el medicamento: {}", ex.getMessage());
                    codigoBarras = "";
                    medicamento = new Medicamento_Extended();
                }
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("recepmedicamento.err.sinpermisoProces"), null);
                codigoBarras = "";
                medicamento = new Medicamento_Extended();
            }
        }
    }

    public void buscarPrescripcion() {
        LOGGER.trace("Buscando coincidencias de: {}", cadenaBusqueda);
        codigoBarras = "";
        cantRecibir = 1;
        if (cadenaBusqueda != null
                && permiso.isPuedeCrear()) {
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
                surtEnviadoList = surtimientoService.obtenerListaSurtimientoEnviadoPorIdSurtimiento(viewRecepcionMed.getIdSurtimiento());
                status = Constantes.ACTIVO;
                numeroMedDetalles = surtEnviadoList.size();
                cadenaBusqueda = null;
            } catch (Exception e) {
                LOGGER.error("Error al buscar Medicamentos: {}", e.getMessage());
            }
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);

    }

    public void procesarMedicamentoRecibido() {
        int result = 0;
        if (permiso.isPuedeCrear()) {
            try {
                result = surtimientoService.actualizarListaCantidadRecbida(surtInsumoExtList, viewRecepcionMed, currentUser.getIdUsuario());
                if (result > 0) {
                    obtenerSutimientoEnviado();
                    Mensaje.showMessage("Info", RESOURCES.getString("recepmedicamento.info.guardado"), null);
                    msgModal = Constantes.INACTIVO;
                    if (result > 4) {
                        Mensaje.showMessage("Info", RESOURCES.getString("recepmedicamento.info.devolucionGenerada"), null);
                    }
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("recepmedicamento.err.actualizar"), null);
                }

            } catch (Exception ex) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("recepmedicamento.err.exActualizar"), null);
                LOGGER.error("Error al actualizar la cantidad recibida: {}", ex.getMessage());
            }

        } else {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("recepmedicamento.err.sinpermisoCrear"), null);
        }
    }

    /*Getter & Setter*/
    public boolean isEliminarCodigo() {
        return eliminarCodigo;
    }

    public void setEliminarCodigo(boolean eliminarCodigo) {
        this.eliminarCodigo = eliminarCodigo;
    }

    public boolean isMsgModal() {
        return msgModal;
    }

    public void setMsgModal(boolean msgModal) {
        this.msgModal = msgModal;
    }

    public SurtimientoInsumo_Extend getSurtimientoInsumoExt() {
        return surtimientoInsumoExt;
    }

    public void setSurtimientoInsumoExt(SurtimientoInsumo_Extend surtimientoInsumoExt) {
        this.surtimientoInsumoExt = surtimientoInsumoExt;
    }

    public List<SurtimientoInsumo_Extend> getSurtInsumoExtList() {
        return surtInsumoExtList;
    }

    public void setSurtInsumoExtList(List<SurtimientoInsumo_Extend> surtInsumoExtList) {
        this.surtInsumoExtList = surtInsumoExtList;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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

    public int getCantRecibir() {
        return cantRecibir;
    }

    public void setCantRecibir(int cantRecibir) {
        this.cantRecibir = cantRecibir;
    }

    public String getTipoPrescripcion() {
        return tipoPrescripcion;
    }

    public void setTipoPrescripcion(String tipoPrescripcion) {
        this.tipoPrescripcion = tipoPrescripcion;
    }

    public String getIdEstructuraUser() {
        return idEstructuraUser;
    }

    public void setIdEstructuraUser(String idEstructuraUser) {
        this.idEstructuraUser = idEstructuraUser;
    }

    public VistaRecepcionMedicamentoService getViewRecepcionService() {
        return viewRecepcionService;
    }

    public void setViewRecepcionService(VistaRecepcionMedicamentoService viewRecepcionService) {
        this.viewRecepcionService = viewRecepcionService;
    }

    public List<VistaRecepcionMedicamento> getViewRecepcionList() {
        return viewRecepcionList;
    }

    public void setViewRecepcionList(List<VistaRecepcionMedicamento> viewRecepcionList) {
        this.viewRecepcionList = viewRecepcionList;
    }

    public VistaRecepcionMedicamento getViewRecepcionMed() {
        return viewRecepcionMed;
    }

    public void setViewRecepcionMed(VistaRecepcionMedicamento viewRecepcionMed) {
        this.viewRecepcionMed = viewRecepcionMed;
    }

    public SurtimientoService getSurtimientoService() {
        return surtimientoService;
    }

    public void setSurtimientoService(SurtimientoService surtimientoService) {
        this.surtimientoService = surtimientoService;
    }

    public SurtimientoEnviado getSurtimientoEnviado() {
        return surtimientoEnviado;
    }

    public void setSurtimientoEnviado(SurtimientoEnviado surtimientoEnviado) {
        this.surtimientoEnviado = surtimientoEnviado;
    }

    public List<SurtimientoEnviado> getSurtEnviadoList() {
        return surtEnviadoList;
    }

    public void setSurtEnviadoList(List<SurtimientoEnviado> surtEnviadoList) {
        this.surtEnviadoList = surtEnviadoList;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
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

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public VistaRecepcionMedicamento getSurtimientoSelect() {
        return surtimientoSelect;
    }

    public void setSurtimientoSelect(VistaRecepcionMedicamento surtimientoSelect) {
        this.surtimientoSelect = surtimientoSelect;
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

    public String getNombreIngrediente() {
        return nombreIngrediente;
    }

    public void setNombreIngrediente(String nombreIngrediente) {
        this.nombreIngrediente = nombreIngrediente;
    }

    public double getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(double velocidad) {
        this.velocidad = velocidad;
    }

    public double getRitmo() {
        return ritmo;
    }

    public void setRitmo(double ritmo) {
        this.ritmo = ritmo;
    }

    public Integer getPerfusionContinua() {
        return perfusionContinua;
    }

    public void setPerfusionContinua(Integer perfusionContinua) {
        this.perfusionContinua = perfusionContinua;
    }

    public String getUnidadConcentracion() {
        return unidadConcentracion;
    }

    public void setUnidadConcentracion(String unidadConcentracion) {
        this.unidadConcentracion = unidadConcentracion;
    }

    public boolean isMostrarInformacionSolucion() {
        return mostrarInformacionSolucion;
    }

    public void setMostrarInformacionSolucion(boolean mostrarInformacionSolucion) {
        this.mostrarInformacionSolucion = mostrarInformacionSolucion;
    }

    public String getClaveAgrupada() {
        return claveAgrupada;
    }

    public void setClaveAgrupada(String claveAgrupada) {
        this.claveAgrupada = claveAgrupada;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

}
