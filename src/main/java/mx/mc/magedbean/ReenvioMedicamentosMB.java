package mx.mc.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import mx.mc.enums.EstatusGabinete_Enum;
import mx.mc.enums.EstatusSurtimiento_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.ReenvioMedicamento;
import mx.mc.model.Surtimiento;
import mx.mc.model.SurtimientoInsumo;
import mx.mc.model.Usuario;
import mx.mc.service.ReenvioMedicamentoService;
import mx.mc.util.Comunes;
import mx.mc.util.Mensaje;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author mcalderon
 */
@Controller
@Scope(value = "view")
public class ReenvioMedicamentosMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(ReenvioMedicamentosMB.class);
    private String codigoCama;
    private String numPrescripcion;
    private String numPaciente;
    private Date fechaPrescripcion;

    private Usuario usuarioSession;
    private boolean administrador;
    private Date fechaActual;
    private boolean band;
    private int encontrados;
    private PermisoUsuario permiso;
    private int motivoSelect;
    private String comentario;

    private ReenvioMedicamento datosMedicamentoReenvio;
    private List<ReenvioMedicamento> datosMedicamentoReenvioList;
    private List<ReenvioMedicamento> datosSelectedList;

    private Surtimiento surtimientoSelect;

    private List<ReenvioMedicamento> motivoReenvioList;

    @Autowired
    private transient ReenvioMedicamentoService reenvioMedicamentoService;

    /**
     * Metodo que se ejecuta despues de cargar la pantalla de pacientes
     */
    @PostConstruct
    public void init() {
        try {
            initialize();
            this.usuarioSession = Comunes.obtenerUsuarioSesion();
            this.administrador = Comunes.isAdministrador();
            permiso = Comunes.obtenerPermisos(Transaccion_Enum.REENVIOMEDICAMENTOS.getSufijo());
            obtenerMotiosReenvio();
        } catch (Exception e) {
           LOGGER.error("Error en init: {}", e.getMessage());
        }
    }

    /**
     * Metodo que inicializa todos los atributos de la Clase
     *
     */
    public void initialize() {
        fechaActual = new java.util.Date();
        datosMedicamentoReenvio = new ReenvioMedicamento();
        datosMedicamentoReenvio.setFechaInicio(fechaActual);

        band = false;
        
        obtenerMotiosReenvio();

    }

    public void obtenerMotiosReenvio() {
        try {
            motivoReenvioList = reenvioMedicamentoService.obtenerMotivosReenvio();
        } catch (Exception e) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al Obtener los Motivos de Reenvio", null);
        }
    }

    public void limpiarCampos() {
        codigoCama = "";
        numPrescripcion = "";
        numPaciente = "";
        fechaPrescripcion = null;
    }

    public void consultar() {
             try {
            if (!numPaciente.isEmpty()) {
                if (fechaPrescripcion != null) {
                    datosMedicamentoReenvioList = reenvioMedicamentoService.obtenerDatosReenvioMedicamentos(codigoCama, numPrescripcion, numPaciente, fechaPrescripcion);
                }else{
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Debe Seleccionar una Fecha", null);
                }
            }else{
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Debe de Llenar el número del Paciente", null);
            }
        } catch (Exception e) {
            LOGGER.error(Constantes.MENSAJE_ERROR, "Error al buscar los Registros");
        }
    }

    public void procesarReenvio() {

        if (!permiso.isPuedeCrear()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No tiene permisos para Crear el Reenvio de Medicamentos", null);
        } else {

            List<SurtimientoInsumo> surtimientoInsumoList = new ArrayList<>();
            boolean status;

            //crea objeto  de tipo surtimiento 
            if (motivoSelect != 0) {
                if (!comentario.equals("")) {
                    surtimientoSelect = new Surtimiento();
                    surtimientoSelect.setIdSurtimiento(Comunes.getUUID());

                    surtimientoSelect.setIdEstructuraAlmacen(datosSelectedList.get(0).getIdEstructuraAlmacen());
                    surtimientoSelect.setIdPrescripcion(datosSelectedList.get(0).getIdPrescripcion());
                    ///Verificar la creacion del folio
                    surtimientoSelect.setFechaProgramada(new java.util.Date());
                    surtimientoSelect.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                    surtimientoSelect.setInsertFecha(new java.util.Date());
                    surtimientoSelect.setInsertIdUsuario(usuarioSession.getIdUsuario());
                    surtimientoSelect.setIdEstatusMirth(EstatusGabinete_Enum.PENDIENTE.getValue());
                    surtimientoSelect.setIdTipoMotivoReenvio(motivoSelect);
                    surtimientoSelect.setComentario(comentario);

                    for (ReenvioMedicamento item : datosSelectedList) {

                        //crea objeto de tipo surtimientoInsumo
                        SurtimientoInsumo surtInsumoSelect = new SurtimientoInsumo();
                        surtInsumoSelect.setIdSurtimientoInsumo(Comunes.getUUID());
                        surtInsumoSelect.setIdSurtimiento(surtimientoSelect.getIdSurtimiento());
                        surtInsumoSelect.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                        surtInsumoSelect.setIdPrescripcionInsumo(item.getIdPrescripcionInsumo());
                        surtInsumoSelect.setFechaProgramada(new java.util.Date());
                        surtInsumoSelect.setCantidadSolicitada(item.getCantidad());
                        surtInsumoSelect.setInsertFecha(new java.util.Date());
                        surtInsumoSelect.setInsertIdUsuario(usuarioSession.getIdUsuario());
                        surtInsumoSelect.setIdEstatusMirth(EstatusGabinete_Enum.PENDIENTE.getValue());

                        surtimientoInsumoList.add(surtInsumoSelect);              
                    }

                    try {
                        status = reenvioMedicamentoService.surtirReenvioMedicamento(surtimientoSelect, surtimientoInsumoList);
                        if (!status) {
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El Reenvío no fue Procesado", null);
                        } else {
                            Mensaje.showMessage(Constantes.MENSAJE_INFO, "Reenvío de Medicamentos Exitoso con el Folio " + surtimientoSelect.getFolio(), null);
                        }
                    } catch (Exception e) {
                        LOGGER.error("Error al realizar el Proceso: {}", e.getMessage());
                    }

                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El comentario Es Obligatorio", null);
                }
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Debe Seleccionar un Motivo de Reenvío", null);
            }
        }

    }

    public void onRowSelect(SelectEvent event) {
        LOGGER.debug("mx.mc.magedbean.ReenvioMedicamentosMB.onRowSelect()");
        datosSelectedList.add((ReenvioMedicamento) event.getObject());

        if (!datosSelectedList.isEmpty()) {
            band = true;
        } else {
            band = false;
        }

    }

    public void onRowUnselect(UnselectEvent event) {
        LOGGER.debug("mx.mc.magedbean.ReenvioMedicamentosMB.onRowUnselect()");
        datosSelectedList.remove((ReenvioMedicamento) event.getObject());
        if (!datosSelectedList.isEmpty()) {
            band = true;
        } else {
            band = false;
        }
    }

    public Usuario getUsuarioSession() {
        return usuarioSession;
    }

    public void setUsuarioSession(Usuario usuarioSession) {
        this.usuarioSession = usuarioSession;
    }

    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public ReenvioMedicamento getDatosMedicamentoReenvio() {
        return datosMedicamentoReenvio;
    }

    public void setDatosMedicamentoReenvio(ReenvioMedicamento datosMedicamentoReenvio) {
        this.datosMedicamentoReenvio = datosMedicamentoReenvio;
    }

    public String getCodigoCama() {
        return codigoCama;
    }

    public void setCodigoCama(String codigoCama) {
        this.codigoCama = codigoCama;
    }

    public String getNumPrescripcion() {
        return numPrescripcion;
    }

    public void setNumPrescripcion(String numPrescripcion) {
        this.numPrescripcion = numPrescripcion;
    }

    public String getNumPaciente() {
        return numPaciente;
    }

    public void setNumPaciente(String numPaciente) {
        this.numPaciente = numPaciente;
    }

    public Date getFechaPrescripcion() {
        return fechaPrescripcion;
    }

    public void setFechaPrescripcion(Date fechaPrescripcion) {
        this.fechaPrescripcion = fechaPrescripcion;
    }

    public List<ReenvioMedicamento> getDatosMedicamentoReenvioList() {
        return datosMedicamentoReenvioList;
    }

    public void setDatosMedicamentoReenvioList(List<ReenvioMedicamento> datosMedicamentoReenvioList) {
        this.datosMedicamentoReenvioList = datosMedicamentoReenvioList;
    }

    public ReenvioMedicamentoService getReenvioMedicamentoService() {
        return reenvioMedicamentoService;
    }

    public void setReenvioMedicamentoService(ReenvioMedicamentoService reenvioMedicamentoService) {
        this.reenvioMedicamentoService = reenvioMedicamentoService;
    }

    public List<ReenvioMedicamento> getDatosSelectedList() {
        return datosSelectedList;
    }

    public void setDatosSelectedList(List<ReenvioMedicamento> datosSelectedList) {
        this.datosSelectedList = datosSelectedList;
    }

    public boolean isBand() {
        return band;
    }

    public void setBand(boolean band) {
        this.band = band;
    }

    public int getEncontrados() {
        return encontrados;
    }

    public void setEncontrados(int encontrados) {
        this.encontrados = encontrados;
    }

    public List<ReenvioMedicamento> getMotivoReenvioList() {
        return motivoReenvioList;
    }

    public void setMotivoReenvioList(List<ReenvioMedicamento> motivoReenvioList) {
        this.motivoReenvioList = motivoReenvioList;
    }

    public int getMotivoSelect() {
        return motivoSelect;
    }

    public void setMotivoSelect(int motivoSelect) {
        this.motivoSelect = motivoSelect;
    }

    public Surtimiento getSurtimientoSelect() {
        return surtimientoSelect;
    }

    public void setSurtimientoSelect(Surtimiento surtimientoSelect) {
        this.surtimientoSelect = surtimientoSelect;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }
}