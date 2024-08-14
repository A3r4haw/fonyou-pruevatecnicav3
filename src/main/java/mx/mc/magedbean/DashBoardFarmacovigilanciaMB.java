/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.model.DashboardResult;
import mx.mc.model.PermisoUsuario;
import mx.mc.service.DashBoardFarmacovigilanciaService;
import mx.mc.util.Comunes;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.donut.DonutChartDataSet;
import org.primefaces.model.charts.donut.DonutChartModel;
import org.primefaces.model.charts.donut.DonutChartOptions;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.primefaces.model.charts.polar.PolarAreaChartDataSet;
import org.primefaces.model.charts.polar.PolarAreaChartModel;
import org.primefaces.model.charts.polar.PolarAreaChartOptions;
import org.primefaces.model.timeline.TimelineEvent;
import org.primefaces.model.timeline.TimelineModel;
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
public class DashBoardFarmacovigilanciaMB  implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(DashBoardFarmacovigilanciaMB.class);     
    
    private PermisoUsuario permiso;
    
    private static DashboardModel dashboardModel;
    private List<DashboardResult> listTopFarmacosMayorReaccion;
    private DonutChartModel donutTopFarmacosMayorReaccion;
    
    private BarChartModel barChartModel;
    private List<DashboardResult> listTopTiposHipersensibilidad;
    
    private PolarAreaChartModel polarAreaModel;
    private List<DashboardResult> listTopFarmacosRAM;
    
    private BarChartModel barChartModelPaciente;
    private List<DashboardResult> lisTopPacientesReaccion; 
    
    private DonutChartModel donutTopUsuariosReaccion;
    private List<DashboardResult> listTopUsuariosReaccion;
   
    private TimelineModel modelFirst;
    private String cadena;
    private List<DashboardResult> listtTopPacientesBusqueda;
    
    @Autowired
    private transient DashBoardFarmacovigilanciaService dashBoardFarmacovigilanciaService;
    
     /**
     * Metodo que se ejecuta despues de cargar la pantalla de pacientes
     */
    @PostConstruct
    public void init() {
        try {
            donutTopFarmacosMayorReaccion = new DonutChartModel();
            barChartModel = new BarChartModel();
            polarAreaModel = new PolarAreaChartModel();            
            barChartModelPaciente = new BarChartModel();
            donutTopUsuariosReaccion = new DonutChartModel();
            modelFirst = new TimelineModel();
            cadena = null;
            initialize();
            permiso = Comunes.obtenerPermisos(Transaccion_Enum.DASHFARMACO.getSufijo());
        } catch(Exception ex) {
            LOGGER.error("Error en el metodo init :: {}", ex.getMessage());
        }
    }

    public void initialize() {
        try {
            crearListaTopFarmacosMayorReaccion();
            crearListaTopTiposHipersensibilidad();
            crearListaTopFarmacosRAM();
            crearListaTopPacientesReaccion();
            crearListaTopUsuariosReaccion();
            createFirstTimeline();
        } catch(Exception ex) {
            LOGGER.error("Error al inicializar el Dashboard: {}", ex.getMessage());
        }
    }
    
    public void crearListaTopFarmacosMayorReaccion() {
        try {
            listTopFarmacosMayorReaccion = dashBoardFarmacovigilanciaService.getTopFarmacosMayorReaccion();
            DonutChartDataSet dataSet = new DonutChartDataSet();
            ChartData data = new ChartData();
            List<Number> listaValores = new ArrayList<>();
            List<String> listaFarmacos = new ArrayList<>();
            List<String> listaColores = new ArrayList<>();
            List<String> listaBordes = new ArrayList<>();
            Random rand = new Random();
            
            for(DashboardResult dashboardResult : listTopFarmacosMayorReaccion) {
                listaValores.add(dashboardResult.getCantidad());
                listaFarmacos.add(dashboardResult.getNombreCorto());  
                int r = (int)(Math.random()*256+1);
                int g = (int)(Math.random()*256+1);
                int b = (int)(Math.random()*256+1);
                
                listaColores.add("rgb("+r+","+g+","+b+",0.4)");
                listaBordes.add("rgb("+r+","+g+","+b+")");
            }
            dataSet.setData(listaValores);
            dataSet.setBackgroundColor(listaColores);
            dataSet.setBorderColor(listaBordes);
            data.addChartDataSet(dataSet);
            data.setLabels(listaFarmacos);
            
            DonutChartOptions opciones = new DonutChartOptions();
            Title title = new Title();
            title.setText("Top Farmacos Con Mayor Reacción");
            title.setPosition("top");
            title.setDisplay(true);
            title.setFontSize(18);
            title.setFontStyle("bold");
            title.setFontColor("#2980B9");
            opciones.setTitle(title);
            donutTopFarmacosMayorReaccion.setOptions(opciones);
            donutTopFarmacosMayorReaccion.setData(data);
            
        } catch(Exception ex) {
            LOGGER.error("Error al obtener datos de farmacon con mayor reacción       "  + ex.getMessage());
        }        
    }
    
    public void crearListaTopTiposHipersensibilidad() {
        try {
            listTopTiposHipersensibilidad = dashBoardFarmacovigilanciaService.getTopTiposHipersensibilidad();
            ChartData data = new ChartData();
            BarChartDataSet barDataSet = new BarChartDataSet();           
            barDataSet.setLabel("Tipos");
            List<Number> listaValores = new ArrayList<>();
            List<String> listaTipos = new ArrayList<>();
            List<String> listaColores = new ArrayList<>();
            List<String> listaBordes = new ArrayList<>();
            for(DashboardResult dashboardResult : listTopTiposHipersensibilidad) {
                listaValores.add(dashboardResult.getCantidad());
                listaTipos.add(dashboardResult.getNombre());
                int r = (int)(Math.random()*256+1);
                int g = (int)(Math.random()*256+1);
                int b = (int)(Math.random()*256+1);
                
                listaColores.add("rgb("+r+","+g+","+b+",0.2)");
                listaBordes.add("rgb("+r+","+g+","+b+")");
            }
            barDataSet.setData(listaValores);
            barDataSet.setBackgroundColor(listaColores);
            barDataSet.setBorderColor(listaBordes);                        
            barDataSet.setBorderWidth(1);
            
            data.addChartDataSet(barDataSet);
            data.setLabels(listaTipos);
            barChartModel.setData(data);
            
            BarChartOptions options = new BarChartOptions();
            CartesianScales cScales = new CartesianScales();
            CartesianLinearAxes linearAxes = new CartesianLinearAxes();
            
            linearAxes.setOffset(true);
            CartesianLinearTicks ticks = new CartesianLinearTicks();
            ticks.setBeginAtZero(true);
            linearAxes.setTicks(ticks);
            cScales.addYAxesData(linearAxes);
            options.setScales(cScales);
            
            Title title = new Title();
            title.setDisplay(true);
            title.setFontSize(18);
            title.setFontStyle("bold");
            title.setFontColor("#2980B9");
            title.setText("Top Tipos De Hipersensibilidad");
            options.setTitle(title);
            
            barChartModel.setOptions(options);
            
        } catch(Exception ex) {
            LOGGER.error("Error al buscar top tipos de hipersensibilidad     " + ex.getMessage());
        }       
    }
    
    public void crearListaTopFarmacosRAM() {
        try {
            listTopFarmacosRAM = dashBoardFarmacovigilanciaService.getTopFarmacosMayorReaccion();
            
            ChartData data = new ChartData();
            PolarAreaChartDataSet dataSet = new PolarAreaChartDataSet();
             List<Number> listaValores = new ArrayList<>();
            List<String> listaFarmacos = new ArrayList<>();
            List<String> listaColores = new ArrayList<>();
            List<String> listaBordes = new ArrayList<>();
            for(DashboardResult dashboardResult : listTopFarmacosRAM) {
                listaValores.add(dashboardResult.getCantidad());
                listaFarmacos.add(dashboardResult.getNombreCorto());
                int r = (int)(Math.random()*256+1);
                int g = (int)(Math.random()*256+1);
                int b = (int)(Math.random()*256+1);
                
                listaColores.add("rgb("+r+","+g+","+b+",0.2)");
                listaBordes.add("rgb("+r+","+g+","+b+")");
            }
            
            dataSet.setData(listaValores);
            dataSet.setBackgroundColor(listaColores);
            dataSet.setBorderColor(listaBordes);
            
            data.addChartDataSet(dataSet);
            data.setLabels(listaFarmacos);
            
            Title title = new Title();
            title.setText("Top Farmacos Reacciones Adversas");
            title.setPosition("top");
            title.setDisplay(true);
            title.setFontSize(18);
            title.setFontStyle("bold");
            title.setFontColor("#2980B9");
            PolarAreaChartOptions opciones = new PolarAreaChartOptions();
            opciones.setTitle(title);
            polarAreaModel.setOptions(opciones);
            polarAreaModel.setData(data);
            
        } catch(Exception ex) {
            LOGGER.error("Error al buscar top de farmacos RAM  " + ex.getMessage());
        }
    }
    
    public void crearListaTopPacientesReaccion() {
        try {
            lisTopPacientesReaccion = dashBoardFarmacovigilanciaService.getTopPacientesReaccion();
            ChartData data = new ChartData();
            BarChartDataSet barDataSet = new BarChartDataSet();           
            barDataSet.setLabel("Pacientes");
            List<Number> listaValores = new ArrayList<>();
            List<String> listaTipos = new ArrayList<>();
            List<String> listaColores = new ArrayList<>();
            List<String> listaBordes = new ArrayList<>();
            for(DashboardResult dashboardResult : lisTopPacientesReaccion) {
                listaValores.add(dashboardResult.getCantidad());
                listaTipos.add(dashboardResult.getNombre());
                int r = (int)(Math.random()*256+1);
                int g = (int)(Math.random()*256+1);
                int b = (int)(Math.random()*256+1);
                
                listaColores.add("rgb("+r+","+g+","+b+",0.2)");
                listaBordes.add("rgb("+r+","+g+","+b+")");
            }
            barDataSet.setData(listaValores);
            barDataSet.setBackgroundColor(listaColores);
            barDataSet.setBorderColor(listaBordes);                        
            barDataSet.setBorderWidth(1);
            
            data.addChartDataSet(barDataSet);
            data.setLabels(listaTipos);
            barChartModelPaciente.setData(data);
            
            BarChartOptions options = new BarChartOptions();
            CartesianScales cScales = new CartesianScales();
            CartesianLinearAxes linearAxes = new CartesianLinearAxes();
            
            linearAxes.setOffset(true);
            CartesianLinearTicks ticks = new CartesianLinearTicks();
            ticks.setBeginAtZero(true);
            linearAxes.setTicks(ticks);
            cScales.addYAxesData(linearAxes);
            options.setScales(cScales);
            
            Title title = new Title();
            title.setDisplay(true);
            title.setFontSize(18);
            title.setFontStyle("bold");
            title.setFontColor("#2980B9");
            title.setText("Top Pacientes Mayor Reacción");
            options.setTitle(title);
            
            barChartModelPaciente.setOptions(options);
            
        } catch(Exception ex) {
            LOGGER.error("Error al buscar top pacientes con mayor reacción     " + ex.getMessage());
        }
    }
    
    public void crearListaTopUsuariosReaccion() {
        try {
            listTopUsuariosReaccion = dashBoardFarmacovigilanciaService.getTopUsuariosReaccion();
            DonutChartDataSet dataSet = new DonutChartDataSet();
            ChartData data = new ChartData();
            List<Number> listaValores = new ArrayList<>();
            List<String> listaFarmacos = new ArrayList<>();
            List<String> listaColores = new ArrayList<>();
            List<String> listaBordes = new ArrayList<>();
            Random rand = new Random();
            
            for(DashboardResult dashboardResult : listTopUsuariosReaccion) {
                listaValores.add(dashboardResult.getCantidad());
                listaFarmacos.add(dashboardResult.getNombre());  
                int r = (int)(Math.random()*256+1);
                int g = (int)(Math.random()*256+1);
                int b = (int)(Math.random()*256+1);
                
                listaColores.add("rgb("+r+","+g+","+b+",0.4)");
                listaBordes.add("rgb("+r+","+g+","+b+")");
            }
            dataSet.setData(listaValores);
            dataSet.setBackgroundColor(listaColores);
            dataSet.setBorderColor(listaBordes);
            data.addChartDataSet(dataSet);
            data.setLabels(listaFarmacos);
            
            DonutChartOptions opciones = new DonutChartOptions();
            Title title = new Title();
            title.setText("Top Usuarios Registraron Mayor Reacción");
            title.setPosition("top");
            title.setDisplay(true);
            title.setFontSize(18);
            title.setFontStyle("bold");
            title.setFontColor("#2980B9");
            opciones.setTitle(title);
            donutTopUsuariosReaccion.setOptions(opciones);
            donutTopUsuariosReaccion.setData(data);
            
        } catch(Exception ex) {
            LOGGER.error("Error al obtener datos de usuarios con mayor reacción       "  + ex.getMessage());
        }        
    }
    
    public void createFirstTimeline() {
        try {
            String RAM = "RAM";
            String HIPER = "HIPERSENSIBILIDAD";
            this.modelFirst = new TimelineModel();
            
            listtTopPacientesBusqueda = dashBoardFarmacovigilanciaService.getTopPacientesReaccionByIdPaciente(cadena);
            for(DashboardResult dashboardResult : listtTopPacientesBusqueda) {
                this.modelFirst.add(new TimelineEvent(dashboardResult.getNombre(), dashboardResult.getFecha()));
                /*if(dashboardResult.getFolio().charAt(0) == 'H') {
                    this.modelFirst.add(new TimelineEvent(RAM, dashboardResult.getFecha()));
                } else {
                    this.modelFirst.add(new TimelineEvent(HIPER, dashboardResult.getFecha()));
                }*/
            }
        } catch(Exception ex) {
            LOGGER.error("Error al buscar reacciones de paciente:  " + ex.getMessage());
        }
        
    }

    public DonutChartModel getDonutTopFarmacosMayorReaccion() {
        return donutTopFarmacosMayorReaccion;
    }

    public void setDonutTopFarmacosMayorReaccion(DonutChartModel donutTopFarmacosMayorReaccion) {
        this.donutTopFarmacosMayorReaccion = donutTopFarmacosMayorReaccion;
    }     

    public BarChartModel getBarChartModel() {
        return barChartModel;
    }

    public void setBarChartModel(BarChartModel barChartModel) {
        barChartModel = barChartModel;
    }

    public PolarAreaChartModel getPolarAreaModel() {
        return polarAreaModel;
    }

    public void setPolarAreaModel(PolarAreaChartModel polarAreaModel) {
        polarAreaModel = polarAreaModel;
    }

    public BarChartModel getBarChartModelPaciente() {
        return barChartModelPaciente;
    }

    public void setBarChartModelPaciente(BarChartModel barChartModelPaciente) {
        barChartModelPaciente = barChartModelPaciente;
    }

    public DonutChartModel getDonutTopUsuariosReaccion() {
        return donutTopUsuariosReaccion;
    }

    public void setDonutTopUsuariosReaccion(DonutChartModel donutTopUsuariosReaccion) {
        this.donutTopUsuariosReaccion = donutTopUsuariosReaccion;
    }
    
    public TimelineModel getModelFirst() {
        return modelFirst;
    }

    public void setModelFirst(TimelineModel modelFirst) {
        this.modelFirst = modelFirst;
    }

    public String getCadena() {
        return cadena;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }
        
}


