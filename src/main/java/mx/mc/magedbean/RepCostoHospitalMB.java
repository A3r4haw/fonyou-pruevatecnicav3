/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.magedbean;

import java.util.Calendar;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.ReporteCostosHospital;
import mx.mc.model.Usuario;
import mx.mc.service.ReporteCostosHospitalService;
import mx.mc.util.Comunes;
import mx.mc.util.FechaUtil;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.LineChartSeries;
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
public class RepCostoHospitalMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(RepMovimientosGralesMB.class);

    private Date fechaInicio;
    private Date fechaFin;
    private Date fechaActual;
    private Double mayor;
    private PermisoUsuario permiso;
    private Usuario currentUser;
    private CartesianChartModel combinedModel;

    @Autowired
    private transient ReporteCostosHospitalService costoHospitalServices;
    private List<ReporteCostosHospital> costoHospitalList;

    @PostConstruct
    public void init() {
        initialize();
        permiso = Comunes.obtenerPermisos(Transaccion_Enum.REPORTESCOSTOS.getSufijo());
        reporteInicial();
    }

    private void initialize() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        currentUser = sesion.getUsuarioSelected();
        combinedModel = new BarChartModel();       
        fechaInicio = FechaUtil.getFechaActual();
        fechaFin = FechaUtil.obtenerFechaFin();
        mayor = 0.0;
        fechaActual = new Date();
    }

    private void obtenerMayor(ReporteCostosHospital rep) {
        if (rep.getMes1() != null && rep.getMes1() > mayor) {
            mayor = rep.getMes1();
        }

        if (rep.getMes2() != null && rep.getMes2() > mayor) {
            mayor = rep.getMes2();
        }

        if (rep.getMes3() != null && rep.getMes3() > mayor) {
            mayor = rep.getMes3();
        }

        if (rep.getMes4() != null && rep.getMes4() > mayor) {
            mayor = rep.getMes4();
        }

        if (rep.getMes5() != null && rep.getMes5() > mayor) {
            mayor = rep.getMes5();
        }

        if (rep.getMes6() != null && rep.getMes6() > mayor) {
            mayor = rep.getMes6();
        }

        if (rep.getMes7() != null && rep.getMes7() > mayor) {
            mayor = rep.getMes7();
        }

        if (rep.getMes8() != null && rep.getMes8() > mayor) {
            mayor = rep.getMes8();
        }

        if (rep.getMes9() != null && rep.getMes9() > mayor) {
            mayor = rep.getMes9();
        }

        if (rep.getMes10() != null && rep.getMes10() > mayor) {
            mayor = rep.getMes10();
        }

        if (rep.getMes11() != null && rep.getMes11() > mayor) {
            mayor = rep.getMes11();
        }

        if (rep.getMes12() != null && rep.getMes12() > mayor) {
            mayor = rep.getMes12();
        }
    }

    private void llenaChart(List<ReporteCostosHospital> costoList) {
        combinedModel = new BarChartModel();
        double mes1 = 0;
        double mes2 = 0;
        double mes3 = 0;
        double mes4 = 0;
        double mes5 = 0;
        double mes6 = 0; 
        double mes7 = 0;
        double mes8 = 0;
        double mes9 = 0;
        double mes10 = 0;
        double mes11 = 0;
        double mes12 = 0;        
        int i = 0;
        int ii = 0;
        int iii = 0;
        int iv = 0;
        int v = 0;
        int vi = 0;
        int vii = 0;
        int viii = 0;
        int ix = 0;
        int x = 0;
        int xi = 0;
        int xii = 0;
        for (ReporteCostosHospital repo : costoList) {
            BarChartSeries meses = new BarChartSeries();
            meses.setLabel(repo.getPeriodo().toString());
            if (repo.getMes1() != null) {
                mes1 += repo.getMes1();
                i++;
            }
            if (repo.getMes2() != null) {
                mes2 += repo.getMes2();
                ii++;
            }
            if (repo.getMes3() != null) {
                mes3 += repo.getMes3();
                iii++;
            }
            if (repo.getMes4() != null) {
                mes4 += repo.getMes4();
                iv++;
            }
            if (repo.getMes5() != null) {
                mes5 += repo.getMes5();
                v++;
            }
            if (repo.getMes6() != null) {
                mes6 += repo.getMes6();
                vi++;
            }
            if (repo.getMes7() != null) {
                mes7 += repo.getMes7();
                vii++;
            }
            if (repo.getMes8() != null) {
                mes8 += repo.getMes8();
                viii++;
            }
            if (repo.getMes9() != null) {
                mes9 += repo.getMes9();
                ix++;
            }
            if (repo.getMes10() != null) {
                mes10 += repo.getMes10();
                x++;
            }
            if (repo.getMes11() != null) {
                mes11 += repo.getMes11();
                xi++;
            }
            if (repo.getMes12() != null) {
                mes12 += repo.getMes12();
                xii++;
            }

            meses.set("Enero", repo.getMes1());
            meses.set("Febrero", repo.getMes2());
            meses.set("Marzo", repo.getMes3());
            meses.set("Abril", repo.getMes4());
            meses.set("Mayo", repo.getMes5());
            meses.set("Junio", repo.getMes6());
            meses.set("Julio", repo.getMes7());
            meses.set("Agosto", repo.getMes8());
            meses.set("Septiembre", repo.getMes9());
            meses.set("Octubre", repo.getMes10());
            meses.set("Noviembre", repo.getMes11());
            meses.set("Diciembre", repo.getMes12());
            combinedModel.addSeries(meses);
            obtenerMayor(repo);
        }
        LineChartSeries prom = new LineChartSeries();
        prom.set("Enero", mes1 / i);
        prom.set("Febrero", mes2 / ii);
        prom.set("Marzo", mes3 / iii);
        prom.set("Abril", mes4 / iv);
        prom.set("Mayo", mes5 / v);
        prom.set("Junio", mes6 / vi);
        prom.set("Julio", mes7 / vii);
        prom.set("Agosto", mes8 / viii);
        prom.set("Septiembre", mes9 / ix);
        prom.set("Octubre", mes10 / x);
        prom.set("Noviembre", mes11 / xi);
        prom.set("Diciembre", mes12 / xii);

        prom.setLabel("Promedio");
        combinedModel.addSeries(prom);
        combinedModel.setTitle("Reporte de costos del Hospital");
        combinedModel.setLegendPosition("ne");
        combinedModel.setMouseoverHighlight(true);
        combinedModel.setShowDatatip(true);
        combinedModel.setShowPointLabels(true);
        Axis yAxis = combinedModel.getAxis(AxisType.Y);
        yAxis.setMin(0);
        mayor++;
        yAxis.setMax(mayor);
    }

    public void reporteInicial() {
        try {
            Calendar fecha = Calendar.getInstance();
            int anio = fecha.get(Calendar.YEAR);
            String fecIni = "1900-01-01";
            String fecFin = anio + "-12-31";
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            costoHospitalList = costoHospitalServices.obtenerDatosReporte(format.parse(fecIni), format.parse(fecFin));
            llenaChart(costoHospitalList);
        } catch (Exception ex) {
            LOGGER.error("Error al generar la grafica :: {}", ex.getMessage());
        }
    }

    /**
     * Metodo para obtener la fecha fin con el formato 23:59 en lugar de 00:00
     */
    public void obtenerFechaFinal() {
       fechaFin = FechaUtil.obtenerFechaFinal(fechaFin);
    }

    public void generaReporte() {
        try {
            costoHospitalList = costoHospitalServices.obtenerDatosReporte(fechaInicio, fechaFin);
            llenaChart(costoHospitalList);
        } catch (Exception ex) {
            LOGGER.error("Error al generar la grafica :: {}", ex.getMessage());
        }
    }

    public Double getMayor() {
        return mayor;
    }

    public void setMayor(Double mayor) {
        this.mayor = mayor;
    }

    public ReporteCostosHospitalService getCostoHospitalServices() {
        return costoHospitalServices;
    }

    public void setCostoHospitalServices(ReporteCostosHospitalService costoHospitalServices) {
        this.costoHospitalServices = costoHospitalServices;
    }

    public List<ReporteCostosHospital> getCostoHospitalList() {
        return costoHospitalList;
    }

    public void setCostoHospitalList(List<ReporteCostosHospital> costoHospitalList) {
        this.costoHospitalList = costoHospitalList;
    }

    public CartesianChartModel getCombinedModel() {
        return combinedModel;
    }

    public void setCombinedModel(CartesianChartModel combinedModel) {
        this.combinedModel = combinedModel;
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

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public Usuario getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Usuario currentUser) {
        this.currentUser = currentUser;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }
}
