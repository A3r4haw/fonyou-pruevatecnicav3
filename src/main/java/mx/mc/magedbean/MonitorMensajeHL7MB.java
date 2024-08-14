package mx.mc.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import mx.mc.lazy.MonitorMensajeHL7Lazy;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.model.DataResultMensajeHL7;
import mx.mc.model.MensajeHL7;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.PermisoUsuario;
import mx.mc.service.MonitorMensajeHL7Service;
import mx.mc.util.Comunes;
import mx.mc.util.FechaUtil;

/**
 * 
 * @author gcruz
 *
 */
@Controller
@Scope(value = "view")
public class MonitorMensajeHL7MB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(MonitorMensajeHL7MB.class);    
    
    private List<DataResultMensajeHL7> listaTiposMensaje;
    private List<String> tipoMensajesSelects;
    
    private ParamBusquedaReporte paramBusquedaReporte;
    
    private List<DataResultMensajeHL7> listMensajesHL7;
    
    private Date fechaActual;
    
    @Autowired
    private transient MonitorMensajeHL7Service monitorMensajeHL7Service;
    
    private MonitorMensajeHL7Lazy monitorMensajeHL7Lazy;
    
    private DataResultMensajeHL7 mensajeHL7Select;
    private MensajeHL7 mensajeHL7;
    
    private List<String> listIngresoMensajes;
    private String ingreso;
    private PermisoUsuario permiso;
    /**
     * Metodo que se ejecuta despues de cargar la pantalla de pacientes
     */
    @PostConstruct
    public void init() {
        try {
        	
            initialize();
            permiso = Comunes.obtenerPermisos(Transaccion_Enum.REPORTESMENSAJERIA.getSufijo());
            obtenerTiposMensajeHL7();
            obtenerIngresosMensaje();
        } catch (Exception e) {
            LOGGER.error("Error en el metodo init :: {}", e.getMessage());
        }
    }
    
    /**
     * Metodo que inicializa todos los atributos de la clase
     */
    public void initialize() {
        try {
            this.listaTiposMensaje = new ArrayList<>();
            this.mensajeHL7Select = new DataResultMensajeHL7();
            this.mensajeHL7 = new MensajeHL7();
            this.listIngresoMensajes = new ArrayList<>();
            this.ingreso = null;
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener la lista de tipos de Movimiento: {}", ex.getMessage());
        }
        paramBusquedaReporte = new ParamBusquedaReporte();                
        paramBusquedaReporte.setFechaInicio(FechaUtil.obtenerFechaInicio());
        paramBusquedaReporte.setFechaFin(FechaUtil.obtenerFechaFin());
    }

    public void obtenerTiposMensajeHL7(){
    	try {
    		this.listaTiposMensaje = monitorMensajeHL7Service.obtenerTiposMensajeHL7();
    	} catch (Exception ex) {
			LOGGER.info("Ocurrio un error al obtener los tipos de mensaje: {}", ex.getMessage());
		}
    }
            
    
    public void obtenerFechaFinal() {
        paramBusquedaReporte.setFechaFin(FechaUtil.obtenerFechaFinal(paramBusquedaReporte.getFechaFin()));
    }
    
    
    public void obtenerIngresosMensaje() {
    	try {
			this.listIngresoMensajes = monitorMensajeHL7Service.obtenerIngresosMensaje();
		} catch (Exception ex) {
			LOGGER.info("Ocurrio un error al obtener ingresos para filtro: {}", ex.getMessage());
		}
    }        
    
    /**
     * Metodo para realizar la consulta desde la vista
     * 
     */
    public void consultar() {
    	  try {
    		  paramBusquedaReporte.setNuevaBusqueda(true);
    		  paramBusquedaReporte.setIngresoMensaje(this.ingreso);
    		  paramBusquedaReporte.setListTipoMensaje(tipoMensajesSelects);
    		  monitorMensajeHL7Lazy = new MonitorMensajeHL7Lazy(
    				  monitorMensajeHL7Service, paramBusquedaReporte);    		  

    	   LOGGER.debug("Resultados: {}", monitorMensajeHL7Lazy.getTotalReg());
    	   
    	  } catch (Exception e1) {
    	    LOGGER.error("Error al consultar", e1);
    	  }
    }
    
    public void obtenerDetalleMensajeHL7() {
    	try {
    		mensajeHL7 = monitorMensajeHL7Service.obtenerDetalleMensajeHL7(mensajeHL7Select.getIdMensaje());
    	} catch (Exception ex) {
			LOGGER.info("Error al obtener el detalle del mensaje HL7: {}", ex.getMessage());
		}
    }

    public ParamBusquedaReporte getParamBusquedaReporte() {
        return paramBusquedaReporte;
    }

    public void setParamBusquedaReporte(ParamBusquedaReporte paramBusquedaReporte) {
        this.paramBusquedaReporte = paramBusquedaReporte;
    }

    public List<DataResultMensajeHL7> getListMensajesHL7() {
        return listMensajesHL7;
    }

    public void setListMensajesHL7(List<DataResultMensajeHL7> listMensajesHL7) {
        this.listMensajesHL7 = listMensajesHL7;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public MonitorMensajeHL7Lazy getMonitorMensajeHL7Lazy() {
        return monitorMensajeHL7Lazy;
    }

    public void setMonitorMensajeHL7Lazy(MonitorMensajeHL7Lazy monitorMensajeHL7Lazy) {
        this.monitorMensajeHL7Lazy = monitorMensajeHL7Lazy;
    }

    public List<DataResultMensajeHL7> getListaTiposMensaje() {
        return listaTiposMensaje;
    }

    public void setListaTiposMensaje(List<DataResultMensajeHL7> listaTiposMensaje) {
        this.listaTiposMensaje = listaTiposMensaje;
    }

    public List<String> getTipoMensajesSelects() {
        return tipoMensajesSelects;
    }

    public void setTipoMensajesSelects(List<String> tipoMensajesSelects) {
        this.tipoMensajesSelects = tipoMensajesSelects;
    }

    public DataResultMensajeHL7 getMensajeHL7Select() {
        return mensajeHL7Select;
    }

    public void setMensajeHL7Select(DataResultMensajeHL7 mensajeHL7Select) {
        this.mensajeHL7Select = mensajeHL7Select;
    }

    public MensajeHL7 getMensajeHL7() {
        return mensajeHL7;
    }

    public void setMensajeHL7(MensajeHL7 mensajeHL7) {
        this.mensajeHL7 = mensajeHL7;
    }

    public String getIngreso() {
        return ingreso;
    }

    public void setIngreso(String ingreso) {
        this.ingreso = ingreso;
    }

    public List<String> getListIngresoMensajes() {
        return listIngresoMensajes;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }
    
}
