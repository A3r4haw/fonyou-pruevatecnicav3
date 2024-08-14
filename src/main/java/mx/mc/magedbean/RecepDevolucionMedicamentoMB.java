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
import java.util.Objects;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import mx.mc.enums.EstatusDevolucion_Enum;
import mx.mc.enums.EstatusSurtimiento_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.ClaveProveedorBarras;
import mx.mc.model.CodigoInsumo;
import mx.mc.model.DevolucionMinistracion;
import mx.mc.model.DevolucionMinistracionDetalleExtended;
import mx.mc.model.DevolucionMinistracionExtended;
import mx.mc.model.Estructura;
import mx.mc.model.Inventario;
import mx.mc.model.InventarioSalida;
import mx.mc.model.Medicamento_Extended;
import mx.mc.model.MovimientoInventario;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.Surtimiento;
import mx.mc.model.SurtimientoEnviado_Extend;
import mx.mc.model.SurtimientoInsumo;
import mx.mc.model.TipoMotivo;
import mx.mc.model.Usuario;
import mx.mc.service.DevolucionMinistracionDetalleService;
import mx.mc.service.DevolucionMinistracionService;
import mx.mc.service.InventarioService;
import mx.mc.service.MedicamentoService;
import mx.mc.service.SurtimientoInsumoService;
import mx.mc.service.SurtimientoService;
import mx.mc.service.TipoMotivoService;
import mx.mc.util.CodigoBarras;
import mx.mc.util.Comunes;
import mx.mc.util.Mensaje;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * @author aortiz
 */
@Controller
@Scope(value = "view")
public class RecepDevolucionMedicamentoMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(RecepDevolucionMedicamentoMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private Usuario usuarioSession;
    private List<DevolucionMinistracionExtended> listaDevoucion;
    private List<DevolucionMinistracionDetalleExtended> listaDetalleDevolucion;
    private DevolucionMinistracionExtended devolucionMinSelect;
    private String cadenaBusqueda;
    private String codigoBarras;
    private int xcantidad;
    private boolean activaAutoCompleteInsumos;
    boolean conforme = false;
    private PermisoUsuario permiso;
    @Autowired
    private transient DevolucionMinistracionService devolucionMinistracionService;

    @Autowired
    private transient SurtimientoService surtimientoService;

    @Autowired
    private transient DevolucionMinistracionDetalleService devolucionMinistracionDetalleService;

    @Autowired
    private transient InventarioService inventarioService;

    @Autowired
    private transient SurtimientoInsumoService surtimientoInsumoService;

    @Autowired
    private transient MedicamentoService medicamentoService;
    private Medicamento_Extended medicamento;
    private List<Medicamento_Extended> medicamentoList;

    @Autowired
    private transient TipoMotivoService tipoMotivoService;
    private List<TipoMotivo> tipoMotivoList;
    private List<TipoMotivo> tipoMotivoListAux;

    @PostConstruct
    public void init() {
        initialize();
        permiso = Comunes.obtenerPermisos(Transaccion_Enum.RECDEVM.getSufijo());
        buscarDevolucion();
        obtieneListaMotivo();
    }

    /**
     * Metodo que inicializa los valores de las propiedades.
     */
    private void initialize() {
        this.usuarioSession = Comunes.obtenerUsuarioSesion();
        this.xcantidad = 1;
        listaDevoucion = new ArrayList<>();
        try {
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
            Estructura estruct = new Estructura();
            estruct.setIdEstructura(usuarioSession.getIdEstructura());
            activaAutoCompleteInsumos = sesion.isActivaAutocompleteQrClave();
        } catch (Exception e) {
            LOGGER.error("Error al obtener estructuras :: ", e);
        }
    }

    public void buscarDevolucion() {
        try {
            DevolucionMinistracionExtended parametros = new DevolucionMinistracionExtended();
            parametros.setCadenaBusqueda(this.cadenaBusqueda);
            parametros.setIdEstatusDevolucion(EstatusDevolucion_Enum.ENTRANSITO.getValue());
            if (!this.listaDevoucion.isEmpty()) {
                this.listaDevoucion.clear();
            }
            this.listaDevoucion = this.devolucionMinistracionService.
                    obtenerListaDevolucionExtended(parametros);
        } catch (Exception ex) {
            LOGGER.error("Error en el metodo buscarDevolucion :: ", ex);
        }
    }

    public List<Medicamento_Extended> autoComplete(String cadena) {
        try {
            String idEstructura = this.surtimientoService.obtenerAlmacenPadreOfSurtimiento(usuarioSession.getIdEstructura());
            if (idEstructura == null || idEstructura.isEmpty()) {
                idEstructura = usuarioSession.getIdEstructura();
            }
            medicamentoList = medicamentoService.searchMedicamentoAutoComplete(cadena.trim(), idEstructura, activaAutoCompleteInsumos);
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

    public void mostrarDetalleDevolucion() {
        try {
            DevolucionMinistracionDetalleExtended parametros = new DevolucionMinistracionDetalleExtended();
            parametros.setIdDevolucionMinistracion(this.devolucionMinSelect.getIdDevolucionMinistracion());
            parametros.setIdEstatusDevolucion(EstatusDevolucion_Enum.ENTRANSITO.getValue());
            this.listaDetalleDevolucion = devolucionMinistracionDetalleService.obtenerListaDetalleExtended(parametros, this.devolucionMinSelect.getIdSurtimiento());
            this.xcantidad = 1;
            this.codigoBarras = "";
        } catch (Exception ex) {
            LOGGER.error("Error en el metodo mostrarDetalleDevolucion :: {}", ex.getMessage());
        }
    }

    public void mostrarDetalleDevolucionPorBoton(DevolucionMinistracionExtended itemSelected) {
        try {
            DevolucionMinistracionDetalleExtended parametros = new DevolucionMinistracionDetalleExtended();
            parametros.setIdDevolucionMinistracion(itemSelected.getIdDevolucionMinistracion());
            parametros.setIdEstatusDevolucion(EstatusDevolucion_Enum.ENTRANSITO.getValue());
            this.listaDetalleDevolucion = devolucionMinistracionDetalleService.
                    obtenerListaDetalleExtended(parametros, itemSelected.getIdSurtimiento());
            this.devolucionMinSelect = itemSelected;
            this.xcantidad = 1;
            this.codigoBarras = "";
        } catch (Exception ex) {
            LOGGER.error("Error en el metodo mostrarDetalleDevolucion :: {}", ex.getMessage());
        }
    }

    public void devolverMedicamentoPorCodigo(SelectEvent e) {
        medicamento = (Medicamento_Extended) e.getObject();
        codigoBarras = CodigoBarras.generaCodigoDeBarras(medicamento.getClaveInstitucional(), medicamento.getLote(), medicamento.getFechaCaducidad(), null);
        DevolucionMinistracionDetalleExtended codigoBarrasSeparado = tratarCodigoDeBarras(this.codigoBarras);
        if (this.xcantidad == 0) {
            this.xcantidad = 1;
        }

        for (DevolucionMinistracionDetalleExtended item : this.listaDetalleDevolucion) {
            if (item.getClaveInstitucional().equals(codigoBarrasSeparado.getClaveInstitucional())) {
                int tol = item.getCantidadRecibida();
                int sumLotCad = 0;
                for (SurtimientoEnviado_Extend detalle : item.getSurtimientoEnviadoExtList()) {
                    sumLotCad++;
                    if (detalle.getLote().equals(codigoBarrasSeparado.getLote())) {
                        if (detalle.getCaducidad().equals(codigoBarrasSeparado.getFechaCaducidad())) {
                            if (codigoBarrasSeparado.getFechaCaducidad().compareTo(new Date()) < 0) {
                                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("surtimiento.error.medCaducado"), null);
                                codigoBarras = "";
                                xcantidad = 1;
                                medicamento = new Medicamento_Extended();
                                return;
                            }
                            int total = detalle.getCantidadRecibida() + this.xcantidad;
                            if (total > detalle.getCantidadDevolver() && sumLotCad < item.getSurtimientoEnviadoExtList().size()) {
                                continue;
                            } else if (total > detalle.getCantidadDevolver()) {
                                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("devomedicamento.err.cantSobrepasada"), null);
                                codigoBarras = "";
                                xcantidad = 1;
                                medicamento = new Medicamento_Extended();
                                return;
                            } else {
                                detalle.setCantidadRecibida(total);
                                item.setCantidadRecibida(tol + this.xcantidad);
                                codigoBarras = "";
                                xcantidad = 1;
                                medicamento = new Medicamento_Extended();
                            }
                            break;
                        } else if (sumLotCad == item.getSurtimientoEnviadoExtList().size()) {
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("recepmedicamento.err.caducInvalida"), null);
                            codigoBarras = "";
                            xcantidad = 1;
                            medicamento = new Medicamento_Extended();
                            return;
                        }
                    } else if (sumLotCad == item.getSurtimientoEnviadoExtList().size()) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("recepmedicamento.err.noLote"), null);
                        codigoBarras = "";
                        xcantidad = 1;
                        medicamento = new Medicamento_Extended();
                        return;
                    }
                }
            }
        }
        this.codigoBarras = "";
        this.xcantidad = 1;
        medicamento = new Medicamento_Extended();
    }

    public void recibirDevolucion() throws Exception {
        boolean resp = false;
        List<MovimientoInventario> listaMovInv = new ArrayList<>();
        DevolucionMinistracion devMinistracion = new DevolucionMinistracion();
        Surtimiento surtimiento = null;
        boolean surtCheck = false;
        InventarioSalida inventarioSalida = null;
        List<Inventario> inventarioLista = new ArrayList<>();
        Inventario inventario = null;
        List<InventarioSalida> inventarioSalidaList = new ArrayList<>();
        List<String> idSurtimientoInsumoList = new ArrayList<>();
        for (DevolucionMinistracionDetalleExtended item : this.listaDetalleDevolucion) {
            for (SurtimientoEnviado_Extend detalle : item.getSurtimientoEnviadoExtList()) {
                MovimientoInventario movInvent = new MovimientoInventario();
                movInvent.setIdMovimientoInventario(Comunes.getUUID());
                movInvent.setIdTipoMotivo(detalle.getTipoDevolucion());
                movInvent.setFecha(new Date());
                movInvent.setIdUsuarioMovimiento(usuarioSession.getIdUsuario());
                movInvent.setIdEstrutcuraOrigen(this.devolucionMinSelect.getIdAlmacenOrigen());
                movInvent.setIdEstrutcuraDestino(this.devolucionMinSelect.getIdAlmacenDestino());
                movInvent.setIdInventario(detalle.getIdInventarioSurtido());
                movInvent.setCantidad(detalle.getCantidadRecibida());
                movInvent.setFolioDocumento(this.devolucionMinSelect.getFolio());
                listaMovInv.add(movInvent);

                if (!detalle.isConforme()) {
                    if (!surtCheck
                            && surtimiento == null) {
                        surtimiento = new Surtimiento();
                        surtimiento.setIdSurtimiento(Comunes.getUUID());
                        surtimiento.setIdEstructuraAlmacen(this.devolucionMinSelect.getIdAlmacenOrigen());
                        surtimiento.setIdPrescripcion(this.devolucionMinSelect.getIdPrescripcion());
                        surtimiento.setFechaProgramada(new Date());
                        //surtimiento.setFolio(Comunes.getUUID().substring(1, 8));
                        surtimiento.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                        surtimiento.setInsertFecha(new Date());
                        surtimiento.setInsertIdUsuario(usuarioSession.getIdUsuario());
                        surtCheck = true;
                    }
                    idSurtimientoInsumoList.add(detalle.getIdSurtimientoInsumo());
                }
                if (detalle.isMerma()) {
                    inventarioSalida = new InventarioSalida();
                    inventarioSalida.setIdInventarioSalida(Comunes.getUUID());
                    inventarioSalida.setIdInventario(detalle.getIdInventarioSurtido());
                    inventarioSalida.setIdEstructura(this.devolucionMinSelect.getIdAlmacenDestino());
                    inventarioSalida.setCantidad(detalle.getCantidadRecibida());
                    inventarioSalida.setCostoUnidosis(1.0);
                    inventarioSalida.setIdTipoMotivo(detalle.getTipoDevolucion());
                    inventarioSalida.setInsertFecha(new Date());
                    inventarioSalida.setInsertIdUsuario(usuarioSession.getIdUsuario());
                    inventarioSalidaList.add(inventarioSalida);
                } else {
                    inventario = new Inventario();
                    inventario.setIdInventario(detalle.getIdInventarioSurtido());
                    inventario.setCantidadActual(detalle.getCantidadRecibida());
                    inventario.setUpdateFecha(new Date());
                    inventario.setUpdateIdUsuario(usuarioSession.getIdUsuario());
                    inventarioLista.add(inventario);
                }
            }
        }
        List<SurtimientoInsumo> listSurtimeinto = new ArrayList<>();
        if (surtimiento != null
                && !idSurtimientoInsumoList.isEmpty()) {
            List<SurtimientoInsumo> sl = surtimientoInsumoService.obtenerListaSurtiminetoInsumo(idSurtimientoInsumoList);
            listSurtimeinto.addAll(sl);
            for (int i = 0; i < listSurtimeinto.size(); i++) {
                SurtimientoInsumo surte = listSurtimeinto.get(i);
                for (int j = 0; j < listaDetalleDevolucion.size(); j++) {
                    DevolucionMinistracionDetalleExtended dev = listaDetalleDevolucion.get(j);
                    if (surte.getIdSurtimientoInsumo().equals(dev.getIdSurtimientoInsumo())) {
                        surte.setIdSurtimiento(surtimiento.getIdSurtimiento());
                        surte.setIdSurtimientoInsumo(Comunes.getUUID());
                        surte.setFechaProgramada(new Date());
                        surte.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                        surte.setInsertFecha(new Date());
                        surte.setCantidadEnviada(0);
                        surte.setCantidadRecepcion(0);
                        surte.setUpdateFecha(new Date());
                        surte.setFechaEnviada(new Date());
                        surte.setFechaRecepcion(new Date());
                        surte.setInsertIdUsuario(usuarioSession.getIdUsuario());
                        surte.setCantidadSolicitada(dev.getCantidadRecibida());
                    }
                }
            }
        }

        if (!listSurtimeinto.isEmpty()) {
            surtimiento.setDetalle(listSurtimeinto);
        }
        devMinistracion.setIdDevolucionMinistracion(this.devolucionMinSelect.getIdDevolucionMinistracion());
        devMinistracion.setIdEstatusDevolucion(EstatusDevolucion_Enum.RECIBIDA.getValue());
        devMinistracion.setUpdateFecha(new Date());
        devMinistracion.setUpdateIdUsuario(usuarioSession.getIdUsuario());
        try {
            resp = inventarioService.recivirMedicamentosDevolucion(inventarioLista, inventarioSalidaList, surtimiento, listaMovInv, devMinistracion);
        } catch (Exception ex) {
            LOGGER.error("Error en el metodo tratarCodigoDeBarras :: {}", ex.getMessage());
        }

        if (resp) {
            Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("devomedicamento.inforecibieron"), null);
            PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, resp);
            buscarDevolucion();
        } else {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("devomedicamento.err.devolucion"), null);
            PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, resp);
        }
    }

    /**
     * Metodo utilizado para convertir el codigo de barras en un objeto de tipo
     * ReabastoEnviadoExtended
     *
     * @param codigo String
     * @return ReabastoEnviadoExtended
     */
    private DevolucionMinistracionDetalleExtended tratarCodigoDeBarras(String codigo) {
        DevolucionMinistracionDetalleExtended medicamentoDev = new DevolucionMinistracionDetalleExtended();
        try {
            CodigoInsumo ci = CodigoBarras.parsearCodigoDeBarras(codigo);
            if (ci != null) {
                medicamentoDev.setClaveInstitucional(ci.getClave());
                medicamentoDev.setLote(ci.getLote());
                medicamentoDev.setFechaCaducidad(ci.getFecha());
            } else {
                ClaveProveedorBarras clave = CodigoBarras.buscaClaveSKU(codigo);
                if (clave != null) {
                    medicamentoDev.setClaveInstitucional(clave.getClaveInstitucional());
                    medicamentoDev.setLote(clave.getClaveProveedor());
                    medicamentoDev.setFechaCaducidad(Mensaje.generaCaducidadSKU(clave.getCodigoBarras()));
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("devomedicamento.err.novalido"), null);
                }
            }
            return medicamentoDev;
        } catch (Exception e) {
            LOGGER.error("Error en el metodo tratarCodigoDeBarras :: {}", e.getMessage());
        }
        return medicamentoDev;
    }

    public void obtieneListaMotivo() {
        try {
            tipoMotivoListAux = new ArrayList<>();
            List<Integer> integer = new ArrayList<>();
            tipoMotivoList = tipoMotivoService.listaDevolucionEnPrescripcion();
            if (conforme) {
                for (int i = 0; i < tipoMotivoList.size(); i++) {
                    if (tipoMotivoList.get(i).getIdTipoMotivo() != 0
                            && tipoMotivoList.get(i).getPermiteResurtimiento() == 0) {
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
                    if (tipoMotivoList.get(i).getIdTipoMotivo() != 0
                            && tipoMotivoList.get(i).getPermiteResurtimiento() == 1) {
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

    public Usuario getUsuarioSession() {
        return usuarioSession;
    }

    public void setUsuarioSession(Usuario usuarioSession) {
        this.usuarioSession = usuarioSession;
    }

    public List<DevolucionMinistracionExtended> getListaDevoucion() {
        return listaDevoucion;
    }

    public void setListaDevoucion(List<DevolucionMinistracionExtended> listaDevoucion) {
        this.listaDevoucion = listaDevoucion;
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

    public DevolucionMinistracionExtended getDevolucionMinSelect() {
        return devolucionMinSelect;
    }

    public void setDevolucionMinSelect(DevolucionMinistracionExtended devolucionMinSelect) {
        this.devolucionMinSelect = devolucionMinSelect;
    }

    public List<DevolucionMinistracionDetalleExtended> getListaDetalleDevolucion() {
        return listaDetalleDevolucion;
    }

    public void setListaDetalleDevolucion(List<DevolucionMinistracionDetalleExtended> listaDetalleDevolucion) {
        this.listaDetalleDevolucion = listaDetalleDevolucion;
    }

    public int getXcantidad() {
        return xcantidad;
    }

    public void setXcantidad(int xcantidad) {
        this.xcantidad = xcantidad;
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

    public List<TipoMotivo> getTipoMotivoList() {
        return tipoMotivoList;
    }

    public void setTipoMotivoList(List<TipoMotivo> tipoMotivoList) {
        this.tipoMotivoList = tipoMotivoList;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

}
