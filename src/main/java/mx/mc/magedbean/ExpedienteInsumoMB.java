package mx.mc.magedbean;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import mx.mc.enums.Accion_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.lazy.ExpedienteInsumoLazy;
import mx.mc.model.Adjunto;
import mx.mc.model.AdjuntoExtended;
import mx.mc.model.Estructura;
import mx.mc.model.InventarioExtended;
import mx.mc.model.Medicamento;
import mx.mc.model.Medicamento_Extended;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.ReabastoEnviadoExtended;
import mx.mc.model.ReabastoInsumoExtended;
import mx.mc.model.SurtimientoEnviado_Extend;
import mx.mc.model.TransaccionPermisos;
import mx.mc.model.Usuario;
import mx.mc.service.EstructuraService;
import mx.mc.service.InventarioService;
import mx.mc.service.MedicamentoService;
import mx.mc.service.ReabastoEnviadoService;
import mx.mc.service.ReabastoInsumoService;
import mx.mc.service.SurtimientoEnviadoService;
import mx.mc.util.Comunes;
import mx.mc.util.Mensaje;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author hramirez
 */
@Controller
@Scope(value = "view")
public class ExpedienteInsumoMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(ExpedienteInsumoMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    private String cadenaBusqueda;
    private Date fechaActual;
    private Usuario usuarioEnSesion;

    private transient List<TransaccionPermisos> permisosLista;
    private PermisoUsuario permiso;

    private transient UploadedFile file;
    private transient List<AdjuntoExtended> adjuntoLista;
    private transient Adjunto adjuntoSelecionado;
    private transient DefaultStreamedContent adjuntoDescarga;

    private ExpedienteInsumoLazy expedienteInsumoLazy;

    private transient List<Estructura> estructuraLista;
    private Estructura estructuraSeleccionada;
    @Autowired
    private transient EstructuraService estructuraService;

    private transient List<ReabastoInsumoExtended> reabastoInsumoExtendedLista;
    private ReabastoInsumoExtended reabastoInsumoExtendedSeleccionado;
    @Autowired
    private transient ReabastoInsumoService reabastoInsumoService;

    private transient List<ReabastoEnviadoExtended> reabastoEnviadoLista;
    private ReabastoEnviadoExtended reabastoEnviadoSeleccionado;
    @Autowired
    private transient ReabastoEnviadoService reabastoEnviadoService;

    private transient List<InventarioExtended> inventarioLista;
    private InventarioExtended inventarioSeleccionado;
    @Autowired
    private transient InventarioService inventarioService;

    private transient List<SurtimientoEnviado_Extend> surtimientoEnviadoExtendedLista;
    private SurtimientoEnviado_Extend surtimientoEnviadoSeleccionado;
    @Autowired
    private transient SurtimientoEnviadoService surtimientoEnviadoService;

    private transient List<Medicamento_Extended> insumosLista;
    private Medicamento_Extended medicamentoSeleccionado;
    @Autowired
    private transient MedicamentoService medicamentoService;

    /**
     * Metodo inicializador
     */
    @PostConstruct
    public void init() {
        LOGGER.trace("mx.mc.magedbean.ExpedienteInsumoMB.init()");
        limpia();
        this.permiso = Comunes.obtenerPermisos(Transaccion_Enum.EXPEDIENTEINSUMO.getSufijo());
        obtenerInsumoLista();
    }

    /**
     * Limpia las variables
     */
    private void limpia() {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.limpia()");
        this.cadenaBusqueda = null;
        this.fechaActual = new java.util.Date();
        this.usuarioEnSesion = new Usuario();
        this.permisosLista = new ArrayList<>();
        this.usuarioEnSesion.setPermisosList(this.permisosLista);
        this.medicamentoSeleccionado = new Medicamento_Extended();
        this.estructuraLista = new ArrayList<>();
        this.estructuraSeleccionada = new Estructura();
        this.reabastoEnviadoLista = new ArrayList<>();
        this.reabastoEnviadoSeleccionado = new ReabastoEnviadoExtended();
    }

    /**
     * Busca los Insumos del inventario, activos o inactivos, con o sin existencia
     */
    public void obtenerInsumoLista() {
        LOGGER.trace("mx.mc.magedbean.ExpedienteInsumoMB.obtenerInsumoLista()");
        try {
            this.insumosLista = new ArrayList<>();
            ParamBusquedaReporte paramBusquedaReporte = new ParamBusquedaReporte();     //            this.documentoLista.addAll(medicamentoService.obtenerListaMedicamentoActivo(this.cadenaBusqueda));
            if (this.cadenaBusqueda != null && this.cadenaBusqueda.trim().isEmpty()) {
                this.cadenaBusqueda = null;
            }
            paramBusquedaReporte.setNuevaBusqueda(true);
            paramBusquedaReporte.setCadenaBusqueda(this.cadenaBusqueda);
            this.expedienteInsumoLazy = new ExpedienteInsumoLazy(medicamentoService, paramBusquedaReporte);

        } catch (Exception e) {
            LOGGER.error("Error al obtener Medicamentos del expediente de la central de mezclas {} ", e.getMessage());
        }
    }

    /**
     * Realiza validaciones necesarias por documento
     *
     * @param idMedicamento
     * @return
     */
    private String validaRegistro(String idMedicamento, String accion) {
        LOGGER.trace("mx.mc.magedbean.ExpedienteInsumoMB.validaRegistro()");
        String msj = "";
        if (this.usuarioEnSesion == null) {
            msj = RESOURCES.getString("dcto.err.usuario.sinsesion");

        } else if (accion.equals(Accion_Enum.VER.getValue()) && !this.permiso.isPuedeVer()) {
            msj = RESOURCES.getString("dcto.err.ver");

        } else if (accion.equals(Accion_Enum.CREAR.getValue()) && !this.permiso.isPuedeCrear()) {
            msj = RESOURCES.getString("dcto.err.crea");

        } else if (accion.equals(Accion_Enum.EDITAR.getValue()) && !this.permiso.isPuedeEditar()) {
            msj = RESOURCES.getString("dcto.err.edita");

        } else if (accion.equals(Accion_Enum.ELIMINAR.getValue()) && !this.permiso.isPuedeEliminar()) {
            msj = RESOURCES.getString("dcto.err.cancela");

        } else if (accion.equals(Accion_Enum.AUTORIZAR.getValue()) && !this.permiso.isPuedeAutorizar()) {
            msj = RESOURCES.getString("dcto.err.autioriza");

        } else if (idMedicamento.trim().equals("")) {
            msj = RESOURCES.getString("dcto.err.incorrecto");

        }
        return msj;
    }
    
    /**
     * Método que ontiene un documento por su id
     *
     * @param idMedicamento
     */
    public void consultaInsumo(String idMedicamento) {
        LOGGER.trace("mx.mc.magedbean.ExpedienteInsumoMB.consultaInsumo()");
        boolean status = Constantes.INACTIVO;
        
        this.estructuraLista = new ArrayList<>();
        this.estructuraSeleccionada = new Estructura();
        this.reabastoInsumoExtendedLista = new ArrayList<>();
        this.reabastoInsumoExtendedSeleccionado = new ReabastoInsumoExtended();
        this.reabastoEnviadoLista = new ArrayList<>();
        this.reabastoEnviadoSeleccionado = new ReabastoEnviadoExtended();
        this.surtimientoEnviadoExtendedLista = new ArrayList<>();
        this.surtimientoEnviadoSeleccionado = new SurtimientoEnviado_Extend();
        this.inventarioLista = new ArrayList<>();
        this.inventarioSeleccionado = new InventarioExtended();
        
        String msjValidacion = validaRegistro(idMedicamento, Accion_Enum.EDITAR.getValue());
        if (!msjValidacion.equals("")) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, msjValidacion, null);

        } else {
            obtenerAlmacenesPorIdInsumo(idMedicamento);
            status = Constantes.ACTIVO;
        }
        PrimeFaces.current().ajax().addCallbackParam("estatus", status);
    }

    /**
     * Método que obtiene los almacenes donde hay registros de un insumo, si
     * importar si hay existencias, o si esta activo
     *
     * @param idInsumo
     */
    private void obtenerAlmacenesPorIdInsumo(String idInsumo) {
        LOGGER.trace("mx.mc.magedbean.ExpedienteInsumoMB.obtenerAlmacenesPorIdInsumo()");
        
        try {
            this.medicamentoSeleccionado = medicamentoService.obtenerMedicamentoPorIdInsumo(idInsumo);
            if (this.medicamentoSeleccionado != null) {
                this.estructuraLista = estructuraService.obtenerAlmacenesPorInsumo(new Estructura(), idInsumo);
            }
        } catch (Exception e) {
            String expin_err_obtienealmacenes = "Error al obtener Almacenes por insumo ";
            LOGGER.error(expin_err_obtienealmacenes + "{}", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, expin_err_obtienealmacenes, null);
        }
    }

    public List<Estructura> getEstructuraLista() {
        return estructuraLista;
    }

    public void setEstructuraLista(List<Estructura> estructuraLista) {
        this.estructuraLista = estructuraLista;
    }

    public Estructura getEstructuraSeleccionada() {
        return estructuraSeleccionada;
    }

    public void setEstructuraSeleccionada(Estructura estructuraSeleccionada) {
        this.estructuraSeleccionada = estructuraSeleccionada;
    }

    /**
     * Obtiene el inventario por cada Almacen y por cada insumo diferenciando
     * lote, provedor y fabricante
     */
    public void obtenerInventarioPorIdEstructurayporIdInsumo() {
        LOGGER.trace("mx.mc.magedbean.ExpedienteInsumoMB.obtenerInventarioPorIdEstructurayporIdInsumo()");
        
        this.reabastoInsumoExtendedLista = new ArrayList<>();
        this.reabastoInsumoExtendedSeleccionado = new ReabastoInsumoExtended();
        this.reabastoEnviadoLista = new ArrayList<>();
        this.reabastoEnviadoSeleccionado = new ReabastoEnviadoExtended();
        this.surtimientoEnviadoExtendedLista = new ArrayList<>();
        this.surtimientoEnviadoSeleccionado = new SurtimientoEnviado_Extend();

        
        if (this.medicamentoSeleccionado == null) {

        } else if (this.medicamentoSeleccionado.getIdMedicamento() == null) {

        } else if (this.estructuraSeleccionada == null) {

        } else if (this.estructuraSeleccionada.getIdEstructura() == null) {

        } else {
            try {
                String idInsumo = this.medicamentoSeleccionado.getIdMedicamento();
                String idEstructura = this.estructuraSeleccionada.getIdEstructura();
                this.inventarioLista = inventarioService.obtenerInventarioPorIdEstructurayporIdInsumo(idEstructura, idInsumo);
            } catch (Exception e) {
                String expin_err_obtieneInventario = "Error al obtener Inventario de Insumos por idEstructura y por idInsumo. ";
                LOGGER.error(expin_err_obtieneInventario + "{}", e.getMessage());
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, expin_err_obtieneInventario, null);
            }
        }
    }

    public List<InventarioExtended> getInventarioLista() {
        return inventarioLista;
    }

    public void setInventarioLista(List<InventarioExtended> inventarioLista) {
        this.inventarioLista = inventarioLista;
    }

    public InventarioExtended getInventarioSeleccionado() {
        return inventarioSeleccionado;
    }

    public void setInventarioSeleccionado(InventarioExtended inventarioSeleccionado) {
        this.inventarioSeleccionado = inventarioSeleccionado;
    }

    /**
     * Método que busca todos los reabastos por cada almacen e insumo
     */
    public void obtenerReabastoInsumoPorIdEstructuraYporIdInsumo() {
        LOGGER.trace("mx.mc.magedbean.ExpedienteInsumoMB.obtenerReabastoInsumoPorIdEstructuraYporIdInsumo()");
        
        this.reabastoInsumoExtendedLista = new ArrayList<>();
        this.reabastoInsumoExtendedSeleccionado = new ReabastoInsumoExtended();
        this.reabastoEnviadoLista = new ArrayList<>();
        this.reabastoEnviadoSeleccionado = new ReabastoEnviadoExtended();
        this.surtimientoEnviadoExtendedLista = new ArrayList<>();
        this.surtimientoEnviadoSeleccionado = new SurtimientoEnviado_Extend();
                
        if (this.medicamentoSeleccionado == null) {

        } else if (this.medicamentoSeleccionado.getIdMedicamento() == null) {

        } else if (this.estructuraSeleccionada == null) {

        } else if (this.estructuraSeleccionada.getIdEstructura() == null) {

        } else {
            try {
                String idInsumo = this.medicamentoSeleccionado.getIdMedicamento();
                String idEstructura = this.estructuraSeleccionada.getIdEstructura();
                this.reabastoInsumoExtendedLista = reabastoInsumoService.obtenerReabastoInsumoPorIdEstructuraYporIdInsumo(idEstructura, idInsumo);
                
                if (this.inventarioSeleccionado == null) {
        
                } else if (this.inventarioSeleccionado.getIdInventario()== null) {
                
                } else {
                    String idInventario = inventarioSeleccionado.getIdInventario();
                    this.reabastoEnviadoLista.addAll(obtenerReabastoEnviadoPorIdEstructuraIdInsumoIdInventario(idEstructura, idInsumo, idInventario));
                    this.surtimientoEnviadoExtendedLista.addAll(obtieneSurtimientoEnviado(idEstructura, idInsumo, idInventario));
                }

            } catch (Exception e) {
                String expin_err_obtienereabastoInsumos = "Error al obtener reabastoInsumo por idEstructura y  por idInsumo ";
                LOGGER.error(expin_err_obtienereabastoInsumos + "{}", e.getMessage());
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, expin_err_obtienereabastoInsumos, null);
            }

        }

    }

    public List<ReabastoInsumoExtended> getReabastoInsumoExtendedLista() {
        return reabastoInsumoExtendedLista;
    }

    public void setReabastoInsumoExtendedLista(List<ReabastoInsumoExtended> reabastoInsumoExtendedLista) {
        this.reabastoInsumoExtendedLista = reabastoInsumoExtendedLista;
    }

    public ReabastoInsumoExtended getReabastoInsumoExtendedSeleccionado() {
        return reabastoInsumoExtendedSeleccionado;
    }

    public void setReabastoInsumoExtendedSeleccionado(ReabastoInsumoExtended reabastoInsumoExtendedSeleccionado) {
        this.reabastoInsumoExtendedSeleccionado = reabastoInsumoExtendedSeleccionado;
    }

    /**
     * * Método que busca todos los recepciones por cada almacen e insumo
     * @param idInventario
     * @param idEstructura
     * @param idInsumo 
     */
    private List<ReabastoEnviadoExtended> obtenerReabastoEnviadoPorIdEstructuraIdInsumoIdInventario(String idEstructura, String idInsumo, String idInventario) {
        LOGGER.trace("mx.mc.magedbean.ExpedienteInsumoMB.obtenerReabastoEnviadoPorIdEstructuraIdInsumoIdInventario()");
        try {
            return reabastoEnviadoService.obtenerReabastoEnviadoPorIdEstructuraIdInsumoIdInventario(idEstructura, idInsumo, idInventario);
        } catch (Exception e) {
            String expin_err_obtienereabastoInsumos = "Error al obtener reabasto por almacenes y  por insumos ";
            LOGGER.error(expin_err_obtienereabastoInsumos + "{}", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, expin_err_obtienereabastoInsumos, null);
        }
        return new ArrayList<>();

    }

    public List<ReabastoEnviadoExtended> getReabastoEnviadoLista() {
        return reabastoEnviadoLista;
    }

    public void setReabastoEnviadoLista(List<ReabastoEnviadoExtended> reabastoEnviadoLista) {
        this.reabastoEnviadoLista = reabastoEnviadoLista;
    }

    public ReabastoEnviadoExtended getReabastoEnviadoSeleccionado() {
        return reabastoEnviadoSeleccionado;
    }

    public void setReabastoEnviadoSeleccionado(ReabastoEnviadoExtended reabastoEnviadoSeleccionado) {
        this.reabastoEnviadoSeleccionado = reabastoEnviadoSeleccionado;
    }

    /**
     * * Método que obtiene los surtimientos de prescripción por Almacen e Insumos, considerando lote y fecha de cadudidad
     * @param idInventario
     * @param idEstructura
     * @param idInsumo
     * @return 
     */
    private List<SurtimientoEnviado_Extend> obtieneSurtimientoEnviado(String idEstructura, String idInsumo, String idInventario) {
        LOGGER.trace("mx.mc.magedbean.ExpedienteInsumoMB.obtieneSurtimientoEnviado()");
        try {
            return surtimientoEnviadoService.obtenerSurtimientoEnviadoPorIdEstructuraIdInsumoIdInventario(idEstructura, idInsumo, idInventario);

        } catch (Exception e) {
            String expin_err_obtieneSurtimientos = "Error al obtener surtimientos de prescripción por idEstructura, idInsumo y idInventario ";
            LOGGER.error(expin_err_obtieneSurtimientos + "{}", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, expin_err_obtieneSurtimientos, null);
        }
        return new ArrayList<>();

    }

    public List<SurtimientoEnviado_Extend> getSurtimientoEnviadoExtendedLista() {
        return surtimientoEnviadoExtendedLista;
    }

    public void setSurtimientoEnviadoExtendedLista(List<SurtimientoEnviado_Extend> surtimientoEnviadoExtendedLista) {
        this.surtimientoEnviadoExtendedLista = surtimientoEnviadoExtendedLista;
    }

    public SurtimientoEnviado_Extend getSurtimientoEnviadoSeleccionado() {
        return surtimientoEnviadoSeleccionado;
    }

    public void setSurtimientoEnviadoSeleccionado(SurtimientoEnviado_Extend surtimientoEnviadoSeleccionado) {
        this.surtimientoEnviadoSeleccionado = surtimientoEnviadoSeleccionado;
    }

    /**
     * Método para descargar adjunto
     *
     * @param idAdjunto
     */
    public void descargarArchivo(Integer idAdjunto) {
        LOGGER.trace("mx.mc.magedbean.ExpedienteInsumoMB.descargarArchivo()");
        try {
            if (this.adjuntoLista != null) {
                for (AdjuntoExtended item : adjuntoLista) {
                    if (Objects.equals(idAdjunto, item.getIdAdjunto())) {
                        byte[] buffer = item.getAdjunto();
                        InputStream stream = new ByteArrayInputStream(buffer);
                        String tipoExtension[] = item.getNombreAdjunto().split("\\.");
                        String contentType = "application/" + tipoExtension[1];
                        this.adjuntoDescarga = (new DefaultStreamedContent(stream, contentType, item.getNombreAdjunto()));
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al intentar descargar el archivo   " + ex.getMessage());
        }
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public DefaultStreamedContent getAdjuntoDescarga() {
        return adjuntoDescarga;
    }

    public void setAdjuntoDescarga(DefaultStreamedContent adjuntoDescarga) {
        this.adjuntoDescarga = adjuntoDescarga;
    }

    public List<AdjuntoExtended> getAdjuntoLista() {
        return adjuntoLista;
    }

    public void setAdjuntoLista(List<AdjuntoExtended> adjuntoLista) {
        this.adjuntoLista = adjuntoLista;
    }

    public Adjunto getAdjuntoSelecionado() {
        return adjuntoSelecionado;
    }

    public void setAdjuntoSelecionado(Adjunto adjuntoSelecionado) {
        this.adjuntoSelecionado = adjuntoSelecionado;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public ExpedienteInsumoLazy getExpedienteInsumoLazy() {
        return expedienteInsumoLazy;
    }

    public void setExpedienteInsumoLazy(ExpedienteInsumoLazy expedienteInsumoLazy) {
        this.expedienteInsumoLazy = expedienteInsumoLazy;
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

    public List<TransaccionPermisos> getPermisosLista() {
        return permisosLista;
    }

    public void setPermisosLista(List<TransaccionPermisos> permisosLista) {
        this.permisosLista = permisosLista;
    }

    public Medicamento_Extended getMedicamentoSeleccionado() {
        return medicamentoSeleccionado;
    }

    public void setMedicamentoSeleccionado(Medicamento_Extended medicamentoSeleccionado) {
        this.medicamentoSeleccionado = medicamentoSeleccionado;
    }

    public List<Medicamento_Extended> getInsumosLista() {
        return insumosLista;
    }

    public void setInsumosLista(List<Medicamento_Extended> insumosLista) {
        this.insumosLista = insumosLista;
    }

}
