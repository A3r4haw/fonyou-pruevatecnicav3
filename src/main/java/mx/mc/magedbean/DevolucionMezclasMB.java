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
import javax.faces.context.FacesContext;
import mx.mc.enums.EstatusSolucion_Enum;
import mx.mc.enums.TipoDocumento_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.lazy.DevolucionMezclaLazy;
import mx.mc.model.CatalogoGeneral;
import mx.mc.model.DevolucionMezcla;
import mx.mc.model.DevolucionMezclaDetalle;
import mx.mc.model.DevolucionMezclaExtended;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.Estructura;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.PresentacionMedicamento;
import mx.mc.model.Reabasto;
import mx.mc.model.SolucionExtended;
import mx.mc.model.SurtimientoInsumo_Extend;
import mx.mc.model.TipoSolucion;
import mx.mc.model.Usuario;
import mx.mc.model.ViaAdministracion;
import mx.mc.service.CatalogoGeneralService;
import mx.mc.service.DevolucionMezclaService;
import mx.mc.service.DevolucionService;
import mx.mc.service.EntidadHospitalariaService;
import mx.mc.service.EstructuraService;
import mx.mc.service.PresentacionMedicamentoService;
import mx.mc.service.ReportesService;
import mx.mc.service.SolucionService;
import mx.mc.service.SurtimientoInsumoService;
import mx.mc.service.TipoSolucionService;
import mx.mc.service.UsuarioService;
import mx.mc.service.ViaAdministracionService;
import mx.mc.util.Comunes;
import mx.mc.util.FechaUtil;
import mx.mc.util.Mensaje;
import org.primefaces.PrimeFaces;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
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
public class DevolucionMezclasMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(DevolucionMezclasMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    private Estructura estructuraSelect;
    private Usuario currentUser;
    private Reabasto reabastoSelect;

    private PermisoUsuario permiso;
    private boolean isAdmin, isJefeArea, btnNew, messaje;
    private boolean status, editable, newRetiro;
    private String tipoAlmacen;
    private String cadenaBusqueda;
    private String idEstructura;
    private int idAlmacen;
    private Boolean print;
    private Date today;
    private Date maxDate;
    private DevolucionMezclaLazy devolucionMezclaLazy;
    private DevolucionMezclaExtended devolucionMezclaSelect;
    private SolucionExtended solucionExtended;
    private Usuario usuarioSelect;
    private Estructura estructuraOrigen;
    private Estructura estructuraUser;
    private CatalogoGeneral motivo;
    private transient List<Estructura> estructuraList;
    private transient List<CatalogoGeneral> motivoRetiroList;
    private transient List<CatalogoGeneral> destinoMezclaList;
    private transient List<CatalogoGeneral> clasificacionList;
    private transient List<CatalogoGeneral> riesgoList;
    private transient List<PresentacionMedicamento> presentacionList;
    private transient List<ViaAdministracion> viaAdministracionList;
    private transient List<TipoSolucion> tipoMezclaList;
    private transient List<SolucionExtended> solucionExtendedList;
    private transient List<SurtimientoInsumo_Extend> detalleMezclaList;
    private transient List<Usuario> usuarioList;

    @Autowired
    private transient EstructuraService estructuraService;

    @Autowired
    private transient TipoSolucionService tipoSolucionService;

    @Autowired
    private transient SolucionService solucionService;

    @Autowired
    private transient SurtimientoInsumoService surtimientoInsumoService;

    @Autowired
    private transient CatalogoGeneralService tipoMotivoService;

    @Autowired
    private transient ViaAdministracionService viaAdministracionService;

    @Autowired
    private transient PresentacionMedicamentoService presentacionMedicamentoService;

    @Autowired
    private transient UsuarioService usuarioService;

    @Autowired
    private transient DevolucionService devolucionService;

    @Autowired
    private transient DevolucionMezclaService devolucionMezclaService;

    @Autowired
    private transient ReportesService reportesService;

    @Autowired
    private transient EntidadHospitalariaService entidadHospitalariaService;

    @PostConstruct
    public void init() {
        LOGGER.trace("mx.mc.magedbean.SolicitudReabastoMB.init()");
        initialize();
        buscarMezclas();
    }

    private void initialize() {

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        permiso = Comunes.obtenerPermisos(Transaccion_Enum.DEVOMEZCLAS.getSufijo());
        currentUser = sesion.getUsuarioSelected();
        estructuraList = new ArrayList<>();
        motivoRetiroList = new ArrayList<>();
        destinoMezclaList = new ArrayList<>();
        clasificacionList = new ArrayList<>();
        solucionExtendedList = new ArrayList<>();
        tipoMezclaList = new ArrayList<>();
        usuarioList = new ArrayList<>();
        detalleMezclaList = new ArrayList<>();
        solucionExtended = new SolucionExtended();
        devolucionMezclaSelect = new DevolucionMezclaExtended();
        usuarioSelect = new Usuario();
        tipoAlmacen = "";
        editable = false;
        newRetiro = false;
        estructuraUser = new Estructura();
        today = new Date();
        // Sumamos 3 años a la fecha actual
        maxDate = FechaUtil.sumarRestarDiasFecha(today, 1095);
        validarUsuarioAdministrador();
        llenaListas();
    }

    public void buscarMezclas() {
        LOGGER.trace("Buscando coincidencias de: {}", cadenaBusqueda);
        try {
            if (cadenaBusqueda != null && cadenaBusqueda.trim().isEmpty()) {
                cadenaBusqueda = null;
            }
            String almacen = "";

            if (tipoAlmacen.isEmpty()) {
                estructuraSelect = estructuraList.get(0);
            } else {
                estructuraSelect = estructuraList.stream().filter(p -> p.getIdEstructura().equals(tipoAlmacen)).findAny().orElse(null);
                if (estructuraSelect == null) {
                    estructuraSelect = estructuraList.get(0);
                }
            }

            devolucionMezclaLazy = new DevolucionMezclaLazy(devolucionMezclaService, cadenaBusqueda, estructuraSelect.getIdEstructura(), EstatusSolucion_Enum.DEVUELTA.getValue());

            LOGGER.debug("Resultados: {}", devolucionMezclaLazy.getTotalReg());

            cadenaBusqueda = null;
        } catch (Exception e) {
            LOGGER.error("Error al buscar Medicamentos: {}", e.getMessage());
        }
    }

    public void validarUsuarioAdministrador() {
        try {
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
            this.isAdmin = sesion.isAdministrador();
            this.isJefeArea = sesion.isJefeArea();
            estructuraUser = estructuraService.obtenerEstructura(currentUser.getIdEstructura());
            if (!this.isAdmin && !this.isJefeArea) {
                this.idEstructura = currentUser.getIdEstructura();
            }
            //mostrarFolioAlternativo = sesion.isMostrarFolioAlternativo();
        } catch (Exception e) {
            LOGGER.error("Error en el metodo validarUsuarioAdministrador :: {}", e.getMessage());
        }
    }

    private void llenaListas() {
        try {
            if (estructuraUser == null && !isAdmin) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("retiro.mezcla.err.sinEstructura"), null);
            } else {
                estructuraList = estructuraService.obtenerAlmacenesPorPerfil(estructuraUser, currentUser);
            }

            if (estructuraUser == null && !isAdmin) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("retiro.mezcla.err.sinEstructura"), null);
            } else {
                estructuraList = estructuraService.obtenerAlmacenesPorPerfil(estructuraUser, currentUser);
            }

            motivoRetiroList = tipoMotivoService.obtenerCatalogosPorGrupo(Constantes.MOTIVO_RETIRO_MEZCLA);

            destinoMezclaList = tipoMotivoService.obtenerCatalogosPorGrupo(Constantes.DESTINO_RETIRO_MEZCLA);

            clasificacionList = tipoMotivoService.obtenerCatalogosPorGrupo(Constantes.CLASIFICACION_MEZCLA);
            riesgoList = tipoMotivoService.obtenerCatalogosPorGrupo(Constantes.RIESGO_PACIENTE_MEZCLA);
            viaAdministracionList = viaAdministracionService.obtenerTodo();

            presentacionList = presentacionMedicamentoService.obtenerTodo();
            TipoSolucion tipoSolucion = new TipoSolucion();
            tipoSolucion.setActivo(1);
            tipoMezclaList = tipoSolucionService.obtenerLista(tipoSolucion);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    public void nuevoRetiro() {
        devolucionMezclaSelect = new DevolucionMezclaExtended();
        solucionExtended = new SolucionExtended();
        usuarioSelect = new Usuario();
        detalleMezclaList = new ArrayList<>();
        newRetiro = true;
        print = false;
    }

    private void getSolucion(String idSolucion) {
        try {
            devolucionMezclaSelect = devolucionMezclaService.obtenerSolucionByIdSolucion(idSolucion);
            devolucionMezclaSelect.setCreacion(new Date());
            devolucionMezclaSelect.setRegistro(currentUser.getNombre() + " " + currentUser.getApellidoPaterno() + " " + currentUser.getApellidoMaterno());
            estructuraOrigen = estructuraService.obtenerEstructura(devolucionMezclaSelect.getIdOrigen());
            Estructura destino = estructuraService.getEstructuraForClave(devolucionMezclaSelect.getClaveMezcla());
            devolucionMezclaSelect.setIdDestino(destino != null ? destino.getIdEstructura() : estructuraOrigen.getIdEstructura());
            devolucionMezclaSelect.setDestino(destino != null ? destino.getNombre() : estructuraOrigen.getNombre());
            detalleMezclaList = surtimientoInsumoService.obtenerDetalleSolucion(idSolucion);
            today = new Date();
            devolucionMezclaSelect.setFechaRetiro(new Date());
            maxDate = solucionExtended.getCaducidadMezcla();
            devolucionMezclaSelect.setFechaLimite(solucionExtended.getCaducidadMezcla());
            usuarioSelect = currentUser;
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener la solucion" + ex.getMessage());
        }
    }

    public void handleSelect(SelectEvent e) {
        try {
            solucionExtended = (SolucionExtended) e.getObject();
            String idSolucion = solucionExtended.getIdSolucion();
            String idSurtimiento = null;
            solucionExtended = solucionService.obtenerSolucion(idSolucion, idSurtimiento);
            getSolucion(solucionExtended.getIdSolucion());
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener la solucion" + ex.getMessage());
        }
    }

    public void handleUnSelect(UnselectEvent e) {
        solucionExtended = (SolucionExtended) e.getObject();
    }

    public void handleUserSelect(SelectEvent e) {
        try {
            usuarioSelect = (Usuario) e.getObject();
            usuarioSelect = usuarioService.obtenerUsuarioByIdUsuario(usuarioSelect.getIdUsuario());
            devolucionMezclaSelect.setUsuario(usuarioSelect.getIdUsuario());
            devolucionMezclaSelect.setIdUsuario(usuarioSelect.getIdUsuario());
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener la solucion" + ex.getMessage());
        }
    }

    public void handleUnUserSelect(UnselectEvent e) {
        usuarioSelect = (Usuario) e.getObject();
    }

    public List<SolucionExtended> autoComplete(String cadena) {
        try {
            String idEstructura = null; // estructuraSelect.getIdEstructura()
            solucionExtendedList = solucionService.obtenerAutoCompSolucion(cadena, null);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener Soluciones: {}", ex.getMessage());
        }
        return solucionExtendedList;
    }

    public List<Usuario> autoCompleteUser(String cadena) {
        try {
            usuarioList = usuarioService.obtenerAutoCompUsario(cadena);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener Usuarios: {}", ex.getMessage());
        }
        return usuarioList;
    }

    public void onCellEdit(CellEditEvent event) {
        int newValue = (int) event.getNewValue();
        int oldValue = (int) event.getOldValue();
        int position = (int) event.getRowIndex();
        if (newValue <= 0) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("retiro.mezcla.err.valorCero"), null);
            detalleMezclaList.get(position).setCantidadEnviada(oldValue);
        } else if (newValue > oldValue) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("retiro.mezcla.err.valorMayor"), null);
            detalleMezclaList.get(position).setCantidadEnviada(oldValue);
        }

    }

    public void activarCellEdit(SurtimientoInsumo_Extend item) {
        try {
            if (newRetiro) {
                int position = 0;
                for (SurtimientoInsumo_Extend itemm : detalleMezclaList) {
                    if (itemm.getClaveInstitucional().equals(item.getClaveInstitucional())) {
                        break;
                    }
                    position++;
                }

                if (item.getMerma() != null && item.getMerma() == 1 && item.getCantidadEnviada() > 1) {
                    item.setActivo(Constantes.INACTIVO);
                    PrimeFaces.current().executeScript("jQuery('td.ui-editable-column').eq(" + position + ").each(function(){jQuery(this).click()});");
                } else {
                    item.setActivo(Constantes.ACTIVO);
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al validar: " + ex.getMessage());
        }
    }

    public void validaDatos() {
        try {
            status = Constantes.ACTIVO;
            if (devolucionMezclaSelect.getOrden() == null || devolucionMezclaSelect.getOrden().trim().equals("")) {
                status = Constantes.INACTIVO;
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("retiro.mezcla.err.ordenPreparacionRequerido"), null);
            } //            else if(devolucionMezclaSelect.getNombre() == null || devolucionMezclaSelect.getNombre().equals("")){
            //                status = Constantes.INACTIVO;
            //                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("retiro.mezcla.err.nombreRequerido"), null);
            //            }
            //            else if(devolucionMezclaSelect.getIdTipoSolucion() == null || devolucionMezclaSelect.getIdTipoSolucion().trim().equals("")){
            //                status = Constantes.INACTIVO;
            //                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("retiro.mezcla.err.tipoMezclaRequerido"), null);
            //            }
            //            else if(devolucionMezclaSelect.getPresentacion() == null || devolucionMezclaSelect.getPresentacion().trim().equals("")){
            //                status = Constantes.INACTIVO;
            //                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("retiro.mezcla.err.presentacionRequerido"), null);
            //            }
            //            else if(devolucionMezclaSelect.getVia() == null || devolucionMezclaSelect.getVia().trim().equals("")){
            //                status = Constantes.INACTIVO;
            //                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("retiro.mezcla.err.viaRequerido"), null);
            //            }
            //            else if(devolucionMezclaSelect.getVolumenFinal() == null || devolucionMezclaSelect.getVolumenFinal().trim().equals("")){
            //                status = Constantes.INACTIVO;
            //                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("retiro.mezcla.err.volumenFinalRequerido"), null);
            //            }
            //            else if(devolucionMezclaSelect.getLote() == null || devolucionMezclaSelect.getLote().trim().equals("")){
            //                status = Constantes.INACTIVO;
            //                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("retiro.mezcla.err.loteRequerido"), null);
            //            }
            //            else if(devolucionMezclaSelect.getFechaCaducidad()==null){
            //                status = Constantes.INACTIVO;
            //                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("retiro.mezcla.err.fechaCaducidadRequerido"), null);
            //            }
            //            else if(devolucionMezclaSelect.getNoHorasestabilidad()==null){
            //                status = Constantes.INACTIVO;
            //                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("retiro.mezcla.err.estabilidadRequerido"), null);
            //            }
            else if (usuarioSelect == null || usuarioSelect.getIdUsuario().trim().equals("")) {
                status = Constantes.INACTIVO;
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("retiro.mezcla.err.usuarioRequerido"), null);
            } else if (devolucionMezclaSelect.getIdMotivo() == null) {
                status = Constantes.INACTIVO;
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("retiro.mezcla.err.motivoRetiro"), null);
            } else if (devolucionMezclaSelect.getRetencion() == null) {
                status = Constantes.INACTIVO;
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("retiro.mezcla.err.retencion"), null);
            } else if (devolucionMezclaSelect.getFechaRetiro() == null) {
                status = Constantes.INACTIVO;
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("retiro.mezcla.err.fechaRetiro"), null);
            } else if (devolucionMezclaSelect.getReutilizar() == null) {
                status = Constantes.INACTIVO;
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("retiro.mezcla.err.reutilizar"), null);
            } else if (devolucionMezclaSelect.getFechaLimite() == null) {
                status = Constantes.INACTIVO;
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("retiro.mezcla.err.fechaLimite"), null);
            } else if (devolucionMezclaSelect.getMerma() == null) {
                status = Constantes.INACTIVO;
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("retiro.mezcla.err.merma"), null);
            } else if (devolucionMezclaSelect.getDestruir() == null) {
                status = Constantes.INACTIVO;
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("retiro.mezcla.err.destruir"), null);
            }
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al validar: " + ex.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public void sendRetiroMezcla() {
        try {
            DevolucionMezcla devoMezcla = new DevolucionMezcla();
            devoMezcla.setIdDevolucionMezcla(Comunes.getUUID());
            devoMezcla.setCreacion(devolucionMezclaSelect.getCreacion());
            devoMezcla.setOrden(devolucionMezclaSelect.getOrden());
            devoMezcla.setIdOrigen(devolucionMezclaSelect.getIdOrigen());
            devoMezcla.setIdDestino(devolucionMezclaSelect.getIdDestino());
            devoMezcla.setIdSolucion(devolucionMezclaSelect.getIdSolucion());
            devoMezcla.setNombre(devolucionMezclaSelect.getNombre());
            devoMezcla.setIdPresentacion(devolucionMezclaSelect.getIdPresentacion());
            devoMezcla.setIdVia(devolucionMezclaSelect.getIdVia());
            devoMezcla.setIdUsuario(usuarioSelect.getIdUsuario());
            devoMezcla.setIdMotivo(devolucionMezclaSelect.getIdMotivo());
            devoMezcla.setRetencion(devolucionMezclaSelect.getRetencion());
            devoMezcla.setFechaRetiro(devolucionMezclaSelect.getFechaRetiro());
            devoMezcla.setReutilizar(devolucionMezclaSelect.getReutilizar());
            devoMezcla.setFechaLimite(devolucionMezclaSelect.getFechaLimite());
            devoMezcla.setComentarios(devolucionMezclaSelect.getComentarios());
            devoMezcla.setMerma(devolucionMezclaSelect.getMerma());
            devoMezcla.setDestruir(devolucionMezclaSelect.getDestruir());
            devoMezcla.setSugerencia(devolucionMezclaSelect.getSugerencia());
            devoMezcla.setIdEstatusSolucion(EstatusSolucion_Enum.DEVUELTA.getValue());
            devoMezcla.setInsertFecha(new Date());
            devoMezcla.setInsertIdUsuario(currentUser.getIdUsuario());

            List<DevolucionMezclaDetalle> detalleMezcla = new ArrayList<>();
            //Recorrer detalle de mezcla
            for (SurtimientoInsumo_Extend item : detalleMezclaList) {
                DevolucionMezclaDetalle detalle = new DevolucionMezclaDetalle();
                detalle.setIdDevolucionMezclaDetalle(Comunes.getUUID());
                detalle.setIdDevolucionMezcla(devoMezcla.getIdDevolucionMezcla());
                detalle.setIdInsumo(item.getIdInsumo());
                detalle.setIdInventario(item.getIdInventario());
                detalle.setMerma(item.getMerma());
                detalle.setPiezas(item.getCantidadEnviada());
                detalle.setInsertFecha(new Date());
                detalle.setInsertIdUsuario(currentUser.getIdUsuario());

                detalleMezcla.add(detalle);
            }

            boolean res = devolucionMezclaService.insertarDevolucionMezcla(devoMezcla, detalleMezcla, TipoDocumento_Enum.DEVOLUCION_MEZCLAS.getValue());
            if (res) {
                buscarMezclas();
                status = Constantes.ACTIVO;
                if (print) {
                    printOrdenReabasto(devoMezcla.getIdDevolucionMezcla());
                }
                Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("retiro.mezcla.info.guardar"), null);
            } else {
                status = Constantes.ACTIVO;
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("retiro.mezcla.err.guardar"), null);
            }

        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error en sendRetiroMezcla: " + ex.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public void obtenerDetalleRetiro() {
        try {
            String idSolucion = devolucionMezclaSelect.getIdSolucion();
            String idSurtimiento = null;
            solucionExtended = solucionService.obtenerSolucion(idSolucion, idSurtimiento);
            detalleMezclaList = devolucionMezclaService.detalleDevolucionMezcla(devolucionMezclaSelect.getIdDevolucionMezcla());
            estructuraOrigen = estructuraService.obtenerEstructura(devolucionMezclaSelect.getIdOrigen());
            Estructura destino = estructuraService.getEstructuraForClave(devolucionMezclaSelect.getIdDestino());
            devolucionMezclaSelect.setIdDestino(destino != null ? destino.getIdEstructura() : estructuraOrigen.getIdEstructura());
            devolucionMezclaSelect.setDestino(destino != null ? destino.getNombre() : estructuraOrigen.getNombre());
            usuarioSelect = usuarioService.obtenerUsuarioByIdUsuario(devolucionMezclaSelect.getIdUsuario());
            devolucionMezclaSelect.setUsuario(usuarioSelect.getIdUsuario());
            newRetiro = false;
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error en obtenerDetalleRetiro", ex);
        }
    }

    public void printOrdenReabasto(String idDevolucionMezcla) throws Exception {
        LOGGER.trace("RetiroMezclasMB.printOrdenReabasto(mezcla)");
        try {
            devolucionMezclaSelect = devolucionMezclaService.obtenerDevolucionMezclaById(idDevolucionMezcla);
            obtenerDetalleRetiro();
            devolucionMezclaSelect.setUsuario(usuarioSelect.getNombre() + " " + usuarioSelect.getApellidoPaterno() + " " + usuarioSelect.getApellidoMaterno());
            Usuario usuario = usuarioService.obtenerUsuarioByIdUsuario(devolucionMezclaSelect.getInsertIdUsuario());

            Estructura estruct = estructuraService.obtenerEstructura(devolucionMezclaSelect.getIdOrigen());
            if (estruct.getIdEntidadHospitalaria() == null) {
                estruct.setIdEntidadHospitalaria("");
            }

            EntidadHospitalaria enti = entidadHospitalariaService.obtenerEntidadById(estruct.getIdEntidadHospitalaria());
            if (enti == null) {
                enti = new EntidadHospitalaria();
                enti.setDomicilio("");
                enti.setNombre("");
            }

            byte[] buffer = null;
            buffer = reportesService.reporteDevolucionMezcla(
                    devolucionMezclaSelect,
                    enti,
                    usuario.getNombre() + " " + usuario.getApellidoPaterno() + " " + usuario.getApellidoMaterno());

            if (buffer != null) {
                SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "sesionMB");
                sesion.setReportStream(buffer);
                sesion.setReportName(String.format("DevMezcla_%s.pdf", devolucionMezclaSelect.getFolio()));
                status = Constantes.ACTIVO;
            }
        } catch (Exception e) {
            LOGGER.error("Error en printOrdenReabasto: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public void changeReutilizar() {
        if (devolucionMezclaSelect.getReutilizar() == 1) {
            if (motivo != null && motivo.getCondicion().equals("N")) {
                devolucionMezclaSelect.setMerma(0);
                maxDate = FechaUtil.sumarRestarDiasFecha(today, 1095);
            } else {
                devolucionMezclaSelect.setReutilizar(0);
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("retiro.mezcla.err.noReutilizar"), null);
            }
        } else {
            maxDate = new Date();
        }
    }

    public void changeMerma() {
        if (devolucionMezclaSelect.getMerma() == 1) {
            devolucionMezclaSelect.setReutilizar(0);
            maxDate = new Date();
        } else if (motivo != null && motivo.getCondicion().equals("R")) {
            devolucionMezclaSelect.setMerma(1);
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("retiro.mezcla.err.debeSerMerma"), null);
        }
    }

    public void changeDestruir() {
        if (devolucionMezclaSelect.getDestruir() == 0 && motivo != null && motivo.getCondicion().equals("R")) {
            devolucionMezclaSelect.setDestruir(1);
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("retiro.mezcla.err.seDebeDestruir"), null);
        }
    }

    public void changeMotivoDevolucion() {
        if (devolucionMezclaSelect.getIdMotivo() != null) {
            motivo = motivoRetiroList.stream().filter(p -> p.getIdCatalogoGeneral() == devolucionMezclaSelect.getIdMotivo()).findAny().orElse(null);
            if (motivo != null && motivo.getCondicion().equals("R")) {
                devolucionMezclaSelect.setReutilizar(0);
                devolucionMezclaSelect.setMerma(1);
                devolucionMezclaSelect.setDestruir(1);
            }
        }
    }

    //<editor-fold  defaultstate="collapsed" desc="Getter and Setters...">
    public boolean isEditable() {
        return editable;
    }

    public boolean isNewRetiro() {
        return newRetiro;
    }

    public void setNewRetiro(boolean newRetiro) {
        this.newRetiro = newRetiro;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public Estructura getEstructuraSelect() {
        return estructuraSelect;
    }

    public void setEstructuraSelect(Estructura estructuraSelect) {
        this.estructuraSelect = estructuraSelect;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean isIsJefeArea() {
        return isJefeArea;
    }

    public void setIsJefeArea(boolean isJefeArea) {
        this.isJefeArea = isJefeArea;
    }

    public String getTipoAlmacen() {
        return tipoAlmacen;
    }

    public void setTipoAlmacen(String tipoAlmacen) {
        this.tipoAlmacen = tipoAlmacen;
    }

    public String getCadenaBusqueda() {
        return cadenaBusqueda;
    }

    public void setCadenaBusqueda(String cadenaBusqueda) {
        this.cadenaBusqueda = cadenaBusqueda;
    }

    public List<Estructura> getEstructuraList() {
        return estructuraList;
    }

    public void setEstructuraList(List<Estructura> estructuraList) {
        this.estructuraList = estructuraList;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

    public Reabasto getReabastoSelect() {
        return reabastoSelect;
    }

    public void setReabastoSelect(Reabasto reabastoSelect) {
        this.reabastoSelect = reabastoSelect;
    }

    public boolean isBtnNew() {
        return btnNew;
    }

    public void setBtnNew(boolean btnNew) {
        this.btnNew = btnNew;
    }

    public boolean isMessaje() {
        return messaje;
    }

    public void setMessaje(boolean messaje) {
        this.messaje = messaje;
    }

    public DevolucionMezclaExtended getDevolucionMezclaSelect() {
        return devolucionMezclaSelect;
    }

    public void setDevolucionMezclaSelect(DevolucionMezclaExtended devolucionMezclaSelect) {
        this.devolucionMezclaSelect = devolucionMezclaSelect;
    }

    public List<CatalogoGeneral> getMotivoRetiroList() {
        return motivoRetiroList;
    }

    public void setMotivoRetiroList(List<CatalogoGeneral> motivoRetiroList) {
        this.motivoRetiroList = motivoRetiroList;
    }

    public List<CatalogoGeneral> getDestinoMezclaList() {
        return destinoMezclaList;
    }

    public void setDestinoMezclaList(List<CatalogoGeneral> destinoMezclaList) {
        this.destinoMezclaList = destinoMezclaList;
    }

    public List<CatalogoGeneral> getClasificacionList() {
        return clasificacionList;
    }

    public void setClasificacionList(List<CatalogoGeneral> clasificacionList) {
        this.clasificacionList = clasificacionList;
    }

    public List<CatalogoGeneral> getRiesgoList() {
        return riesgoList;
    }

    public void setRiesgoList(List<CatalogoGeneral> riesgoList) {
        this.riesgoList = riesgoList;
    }

    public SolucionExtended getSolucionExtended() {
        return solucionExtended;
    }

    public void setSolucionExtended(SolucionExtended solucionExtended) {
        this.solucionExtended = solucionExtended;
    }

    public List<SurtimientoInsumo_Extend> getDetalleMezclaList() {
        return detalleMezclaList;
    }

    public void setDetalleMezclaList(List<SurtimientoInsumo_Extend> detalleMezclaList) {
        this.detalleMezclaList = detalleMezclaList;
    }

    public List<PresentacionMedicamento> getPresentacionList() {
        return presentacionList;
    }

    public void setPresentacionList(List<PresentacionMedicamento> presentacionList) {
        this.presentacionList = presentacionList;
    }

    public List<ViaAdministracion> getViaAdministracionList() {
        return viaAdministracionList;
    }

    public void setViaAdministracionList(List<ViaAdministracion> viaAdministracionList) {
        this.viaAdministracionList = viaAdministracionList;
    }

    public List<TipoSolucion> getTipoMezclaList() {
        return tipoMezclaList;
    }

    public void setTipoMezclaList(List<TipoSolucion> tipoMezclaList) {
        this.tipoMezclaList = tipoMezclaList;
    }

    public Usuario getUsuarioSelect() {
        return usuarioSelect;
    }

    public void setUsuarioSelect(Usuario usuarioSelect) {
        this.usuarioSelect = usuarioSelect;
    }

    public Boolean getPrint() {
        return print;
    }

    public void setPrint(Boolean print) {
        this.print = print;
    }

    public Date getToday() {
        return today;
    }

    public void setToday(Date today) {
        this.today = today;
    }

    public Date getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
    }

    public DevolucionMezclaLazy getDevolucionMezclaLazy() {
        return devolucionMezclaLazy;
    }

    public void setDevolucionMezclaLazy(DevolucionMezclaLazy devolucionMezclaLazy) {
        this.devolucionMezclaLazy = devolucionMezclaLazy;
    }

    //</editor-fold>
}
