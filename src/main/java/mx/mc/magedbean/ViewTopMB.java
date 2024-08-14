package mx.mc.magedbean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.Usuario;
import mx.mc.model.ViewTop;
import mx.mc.service.ViewTopService;
import mx.mc.util.Comunes;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * @author hramirez
 */
@Controller
@Scope(value = "view")
public class ViewTopMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(ViewTopMB.class);
    private Usuario usuarioSession;
    private Date fechaInicio;
    private Date fechaFin;
    private BarChartModel topMedicamento;
    private BarChartModel topMedico;
    private BarChartModel topServicio;
    private BarChartModel topPaciente;
    private BarChartModel topNivel;
    private Integer maxMedico;
    private Integer maxMedicamento;
    private Integer maxPaciente;
    private Integer maxServicio;
    private Integer maxNivel;
    private PermisoUsuario permiso;
    @Autowired
    private transient ViewTopService viewTopService;

    /**
     * Metodo que se inicializa al invocar la pantalla de agenda
     */
    @PostConstruct
    public void init() {
        try {
            initialize();
            permiso = Comunes.obtenerPermisos(Transaccion_Enum.DASH.getSufijo());
            generaTopMedicamento();
            generaTopMedico();
            generaTopPaciente();
            generaTopServicio();
            generaTopNivel();

        } catch (Exception e) {
            LOGGER.error("Error en el metodo init :: {}", e.getMessage());
        }

    }

    /**
     * Metodo que inicializa los valores de la pantalla
     */
    public void initialize() {
        this.fechaInicio = new Date();
        this.fechaFin = new Date();
        maxMedicamento = 0;
        maxMedico = 0;
        maxPaciente = 0;
        maxServicio = 0;
        maxNivel = 0;
    }

    private void generaTopMedicamento() {
        topMedicamento = initViewTopMedicamento();
        topMedicamento.setTitle("Top Medicamentos");
        topMedicamento.setAnimate(true);
        topMedicamento.setLegendPosition("ne");
        Axis yAxis = topMedicamento.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(maxMedicamento + 5);
    }

    private BarChartModel initViewTopMedicamento() {
        List<ViewTop> medicoList = null;
        try {
            medicoList = viewTopService.obtenerTopMedicamento();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        BarChartModel model = new BarChartModel();
        if (medicoList != null) {          
            for (ViewTop item : medicoList) {
                ChartSeries chart = new ChartSeries();
                chart.getData().put("Medicamentos", item.getNumero());
                chart.setLabel(item.getClave());
                if (item.getNumero() > maxMedicamento) {
                    maxMedicamento = item.getNumero();
                }
                model.addSeries(chart);
            }
            
            model.setTitle("Top Medicamentos");
            model.setLegendPosition("ne"); // w ne  se
            model.setShowPointLabels(true);
            model.setMouseoverHighlight(true);
            model.setShadow(true);
            model.setShowDatatip(false);
            model.setShowPointLabels(true);
            model.setZoom(false);
            model.setAnimate(true);
            model.setStacked(false);
            model.setBarPadding(20);
            model.setBarMargin(20);
        }
        return model;
    }

    private void generaTopMedico() {
        topMedico = initViewTopMedico();
        topMedico.setTitle("Top Médicos");
        topMedico.setAnimate(true);
        topMedico.setLegendPosition("ne");
        Axis yAxis = topMedico.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(maxMedico + 5);
    }

    private BarChartModel initViewTopMedico() {
        List<ViewTop> medicoList = null;
        try {
            medicoList = viewTopService.obtenerTopMedico();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        BarChartModel model = new BarChartModel();
        if (medicoList != null) {
            for (ViewTop item : medicoList) {
                ChartSeries chart = new ChartSeries();
                chart.getData().put("Médicos", item.getNumero());
                chart.setLabel(item.getClave());
                if (item.getNumero() > maxMedico) {
                    maxMedico = item.getNumero();
                }
                model.addSeries(chart);
            }            
            model.setTitle("Top Médicos");
            model.setLegendPosition("ne"); // w ne
            model.setShowPointLabels(true);
            model.setMouseoverHighlight(true);
            model.setShadow(true);
            model.setShowDatatip(false);
            model.setShowPointLabels(true);
            model.setZoom(false);
            model.setAnimate(true);
            model.setStacked(false);
            model.setBarPadding(20);
            model.setBarMargin(20);
        }
        return model;
    }

    private void generaTopPaciente() {
        topPaciente = initViewTopPaciente();
        topPaciente.setTitle("Top Pacientes");
        topPaciente.setAnimate(true);
        topPaciente.setLegendPosition("ne");
        Axis yAxis = topPaciente.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(maxPaciente + 5);
    }

    private BarChartModel initViewTopPaciente() {
        List<ViewTop> medicoList = null;
        try {
            medicoList = viewTopService.obtenerTopPaciente();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        BarChartModel model = new BarChartModel();
        if (medicoList != null) {
            for (ViewTop item : medicoList) {
                ChartSeries chart = new ChartSeries();
                chart.getData().put("Pacientes", item.getNumero());
                chart.setLabel(item.getClave());
                if (item.getNumero() > maxPaciente) {
                    maxPaciente = item.getNumero();
                }
                model.addSeries(chart);
            }            
            model.setTitle("Top Pacientes");
            model.setLegendPosition("ne"); // w ne
            model.setShowPointLabels(true);
            model.setMouseoverHighlight(true);
            model.setShadow(true);
            model.setShowDatatip(false);
            model.setShowPointLabels(true);
            model.setZoom(false);
            model.setAnimate(true);
            model.setStacked(false);
            model.setBarPadding(20);
            model.setBarMargin(20);
        }
        return model;
    }

    private void generaTopServicio() {
        topServicio = initViewTopServicio();
        topServicio.setTitle("Top Servicio");
        topServicio.setAnimate(true);
        topServicio.setLegendPosition("ne");
        Axis yAxis = topServicio.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(maxServicio + 5);
    }

    private BarChartModel initViewTopServicio() {
        List<ViewTop> medicoServicio = null;
        try {
            medicoServicio = viewTopService.obtenerTopServicio();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        BarChartModel model = new BarChartModel();
        if (medicoServicio != null) {
            for (ViewTop item : medicoServicio) {
                ChartSeries chart = new ChartSeries();
                chart.getData().put("Servicios", item.getNumero());
                chart.setLabel(item.getClave());
                if (item.getNumero() > maxServicio) {
                    maxServicio = item.getNumero();
                }
                model.addSeries(chart);
            }
            
            model.setTitle("Top Servicio");
            model.setLegendPosition("ne"); // w ne
            model.setShowPointLabels(true);
            model.setMouseoverHighlight(false);
            model.setShadow(true);
            model.setShowDatatip(false);
            model.setShowPointLabels(true);
            model.setZoom(false);
            model.setAnimate(true);
            model.setStacked(false);
            model.setBarPadding(20);
            model.setBarMargin(20);
        }
        return model;
    }

    private void generaTopNivel() {
        topNivel = initViewTopNivel();
        topNivel.setTitle("Top Nivel");
        topNivel.setAnimate(true);
        topNivel.setLegendPosition("ne");
        Axis yAxis = topNivel.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(maxNivel + 5);
    }

    private BarChartModel initViewTopNivel() {
        List<ViewTop> nivel = null;
        try {
            nivel = viewTopService.obtenerTopNivel();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        BarChartModel model = new BarChartModel();
        if (nivel != null) {
            for (ViewTop item : nivel) {
                ChartSeries chart = new ChartSeries();
                chart.setLabel(item.getNombre());
                chart.set("Niveles Socieconómicos", item.getNumero());                
                if (item.getNumero() > maxNivel) {
                    maxNivel = item.getNumero();
                }
                model.addSeries(chart);
            }
            model.setTitle("Top Nivel");
            model.setLegendPosition("ne"); // w ne
            model.setShowPointLabels(true);
            model.setMouseoverHighlight(true);
            model.setShadow(true);
            model.setShowDatatip(false);
            model.setShowPointLabels(true);
            model.setZoom(false);
            model.setAnimate(true);
            model.setStacked(false);
            model.setBarPadding(20);
            model.setBarMargin(20);
        }
        return model;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Usuario getUsuarioSession() {
        return usuarioSession;
    }

    public void setUsuarioSession(Usuario usuarioSession) {
        this.usuarioSession = usuarioSession;
    }

    public BarChartModel getTopMedicamento() {
        return topMedicamento;
    }

    public void setTopMedicamento(BarChartModel topMedicamento) {
        this.topMedicamento = topMedicamento;
    }

    public BarChartModel getTopMedico() {
        return topMedico;
    }

    public void setTopMedico(BarChartModel topMedico) {
        this.topMedico = topMedico;
    }

    public BarChartModel getTopServicio() {
        return topServicio;
    }

    public void setTopServicio(BarChartModel topServicio) {
        this.topServicio = topServicio;
    }

    public BarChartModel getTopPaciente() {
        return topPaciente;
    }

    public void setTopPaciente(BarChartModel topPaciente) {
        this.topPaciente = topPaciente;
    }

    public ViewTopService getViewTopService() {
        return viewTopService;
    }

    public void setViewTopService(ViewTopService viewTopService) {
        this.viewTopService = viewTopService;
    }

    public BarChartModel getTopNivel() {
        return topNivel;
    }

    public void setTopNivel(BarChartModel topNivel) {
        this.topNivel = topNivel;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

}
