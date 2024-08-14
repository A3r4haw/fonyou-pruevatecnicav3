package mx.mc.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.CloseEvent;
import org.primefaces.event.DashboardReorderEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;
import org.primefaces.model.chart.PieChartModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import mx.mc.enums.Transaccion_Enum;
import mx.mc.model.DashboardResult;
import mx.mc.model.PermisoUsuario;
import mx.mc.service.DashboardService;
import mx.mc.util.Comunes;

/**
 * 
 * @author gcruz
 *
 */
@Controller
@Scope(value = "view")
public class DashboardMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(DashboardMB.class);    
    
    private long unidoMinistradas;
    private long unidoSurtidas;
    private long pacientesRegistrados;
    private long defunciones;
    private long altas;
    private PermisoUsuario permiso;
    private List<DashboardResult> listIndicadores;
    private List<DashboardResult> listMedicamentos;
    private List<DashboardResult> listMedicos;
    private List<DashboardResult> listPacientes;
    private List<DashboardResult> listServicios;
    private List<DashboardResult> listNivel;

    private List<DashboardResult> listPrescripciones;
    
    @Autowired
    private transient DashboardService dashboardService; 
    
    private PieChartModel livePieModel;
    
    private static DashboardModel dashboardModel;
    
    /**
     * Metodo que se ejecuta despues de cargar la pantalla de pacientes
     */
    @PostConstruct
    public void init() {
        try {
            unidoMinistradas = 0;
            unidoSurtidas = 0;
            pacientesRegistrados = 0;
            defunciones = 0;
            altas = 0;
            listIndicadores = new ArrayList<>();
            listMedicamentos = new ArrayList<>();
            listMedicos = new ArrayList<>();
            listPrescripciones = new ArrayList<>();
            listPacientes = new ArrayList<>();
            listServicios = new ArrayList<>();
            listNivel = new ArrayList<>();
            livePieModel = new PieChartModel();
            initialize();
            permiso = Comunes.obtenerPermisos(Transaccion_Enum.DASH.getSufijo());
            
        } catch (Exception e) {
            LOGGER.error("Error en el metodo init :: {}", e.getMessage());
        }
    }
    
    /**
     * Metodo que inicializa todos los atributos de la clase
     */
    public void initialize() {
    	try {
            getIndicadoresGenerales();
            getTopMedicamentos();
            getTopMedicos();
            getTopPacientes();
            getTopServicios();
            getTopNivel();
            dashboardModel = new DefaultDashboardModel();
            DashboardColumn column1 = new DefaultDashboardColumn();
            DashboardColumn column2 = new DefaultDashboardColumn();            
            
            column1.addWidget("Ahorro con sitema unidosis");
            column1.addWidget("Etatus General De prescripciones");             
            column2.addWidget("Indicadores Generales");
            column2.addWidget("Estatus General De Prescripciones");             

            dashboardModel.addColumn(column1);
            dashboardModel.addColumn(column2);
	} catch (Exception ex) {
            LOGGER.error("Error al inicializar el Dashboard: {}", ex.getMessage());
	}
    }
    
    public void getIndicadoresGenerales() {
    	try {
    		this.listIndicadores = dashboardService.getIndicadoresGenerales();
    		for (DashboardResult dashboardResult : listIndicadores) {
				if(dashboardResult != null) {
					switch (dashboardResult.getEstatus()) {
					case "Recibido":
						this.unidoMinistradas = dashboardResult.getCantidad();
						break;
					case "Surtido":
						this.unidoSurtidas = dashboardResult.getCantidad();
						break;
					case "Registrado":
						this.pacientesRegistrados = dashboardResult.getCantidad();
						break;
					case "Alta":
						this.altas = dashboardResult.getCantidad();
						break;
					case "Defunción":
						this.defunciones = dashboardResult.getCantidad();
						break;
					default:
						break;
					}
				}
			}				
    	} catch (Exception ex) {
    		LOGGER.error("Error al obtener los indicadores Generales del Dashboard: {} ", ex.getMessage());
		}    	
    }

    public void getTopMedicamentos() {
    	try {
    		listMedicamentos = dashboardService.getTopTenMedicaments();
    	} catch(Exception ex) {
    		LOGGER.error("Error al obtener top ten de medicamentos en el dashboard: {}", ex.getMessage());
    	}
    }
    
    public void getTopMedicos() {
        try {
            listMedicos = dashboardService.getTopMedicos();
        } catch(Exception ex) {
    		LOGGER.error("Error al obtener top ten de medicos en el dashboard: {}", ex.getMessage());
    	}
    }
    
    public void getTopPacientes() {
        try {
            listPacientes = dashboardService.getTopPacientes();
        } catch(Exception ex) {
    		LOGGER.error("Error al obtener top ten de pacientes en el dashboard: {}", ex.getMessage());
    	}
    }
    
    public void getTopServicios() {
        try {
            listServicios = dashboardService.getTopServicios();
        } catch(Exception ex) {
    		LOGGER.error("Error al obtener top ten de servicios en el dashboard: {}", ex.getMessage());
    	}
    }
    
    public void getTopNivel() {
        try {
            listNivel = dashboardService.getTopNivel();
        } catch(Exception ex) {
    		LOGGER.error("Error al obtener top ten de nivel socieconomico en el dashboard: {}", ex.getMessage());
    	}
    }    
    
    public PieChartModel getLivePieModel() {
    	//todo hacer la consulta para cada objeto
    	/*presRegistrada.setEstatusPrescripcion("Registrada");
    	presProgramada.setEstatusPrescripcion("Programada");
    	presEnviada.setEstatusPrescripcion("Enviada");
    	presRegistrada.setCantidadPrescipciones((long) (Math.random() * 1000));
    	presProgramada.setCantidadPrescipciones((long) (Math.random() * 1000));
    	presEnviada.setCantidadPrescipciones((long) (Math.random() * 1000));
    	livePieModel.getData().put(this.presRegistrada.getEstatusPrescripcion(), this.presRegistrada.getCantidadPrescipciones());
    	livePieModel.getData().put(this.presProgramada.getEstatusPrescripcion(), this.presProgramada.getCantidadPrescipciones());
    	livePieModel.getData().put(this.presEnviada.getEstatusPrescripcion(), this.presEnviada.getCantidadPrescipciones());*/
    	try {
    		listPrescripciones = dashboardService.getPrescripciones();
    		for (DashboardResult dashboardResult : listPrescripciones) {
				livePieModel.getData().put(dashboardResult.getEstatus(), dashboardResult.getCantidad());		    	
			}
    		livePieModel.setTitle("Estatus General de las prescripciones");
        	livePieModel.setLegendPosition("ne");
    		
    	} catch(Exception ex) {
    		LOGGER.error("Error al obtener el estatus de prescripciones en dashboard: {}", ex.getMessage());
    	}
    	
		return livePieModel;
	}

    //Aqui empiezan los metodos para el DasboardModel
    public void handleReorder(DashboardReorderEvent event) {
        FacesMessage message = new FacesMessage();
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        message.setSummary("Reordered: " + event.getWidgetId());
        message.setDetail("Item index: " + event.getItemIndex() + ", Column index: " + event.getColumnIndex() + ", Sender index: " + event.getSenderColumnIndex());
         
        addMessage(message);
    }
     
    public void handleClose(CloseEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Panel Closed", "Closed panel id:'" + event.getComponent().getId() + "'");
         
        addMessage(message);
    }
     
    public void handleToggle(ToggleEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, event.getComponent().getId() + " toggled", "Status:" + event.getVisibility().name());
         
        addMessage(message);
    }
     
    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
     
    public DashboardModel getdashboardModel() {
        return dashboardModel;
    }
    public long getUnidoMinistradas() {
        return unidoMinistradas;
    }

    public void setUnidoMinistradas(long unidoMinistradas) {
        this.unidoMinistradas = unidoMinistradas;
    }

    public long getUnidoSurtidas() {
        return unidoSurtidas;
    }

    public void setUnidoSurtidas(long unidoSurtidas) {
        this.unidoSurtidas = unidoSurtidas;
    }

    public long getPacientesRegistrados() {
        return pacientesRegistrados;
    }

    public void setPacientesRegistrados(long pacientesRegistrados) {
        this.pacientesRegistrados = pacientesRegistrados;
    }

    public long getDefunciones() {
        return defunciones;
    }

    public void setDefunciones(long defunciones) {
        this.defunciones = defunciones;
    }

    public long getAltas() {
        return altas;
    }

    public void setAltas(long altas) {
        this.altas = altas;
    }

    public List<DashboardResult> getListPrescripciones() {
        return listPrescripciones;
    }

    public List<DashboardResult> getListIndicadores() {
        return listIndicadores;
    }

    public List<DashboardResult> getListMedicamentos() {
        return listMedicamentos;
    }

    public List<DashboardResult> getListMedicos() {
        return listMedicos;
    }

    public void setListMedicos(List<DashboardResult> listMedicos) {
        this.listMedicos = listMedicos;
    }

    public List<DashboardResult> getListPacientes() {
        return listPacientes;
    }

    public void setListPacientes(List<DashboardResult> listPacientes) {
        this.listPacientes = listPacientes;
    }

    public List<DashboardResult> getListServicios() {
        return listServicios;
    }

    public void setListServicios(List<DashboardResult> listServicios) {
        this.listServicios = listServicios;
    }

    public List<DashboardResult> getListNivel() {
        return listNivel;
    }

    public void setListNivel(List<DashboardResult> listNivel) {
        this.listNivel = listNivel;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }
    
}
