/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.io.Serializable;

/**
 *
 * @author bbautista
 */
public class ReporteCostosHospital implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String idEstructura;
    private String nombre;
    private String idInventario;
    private Integer periodo;
    private String mes;
    private Double  mes1;
    private Double  mes2;
    private Double  mes3;
    private Double  mes4;
    private Double  mes5;
    private Double  mes6;
    private Double  mes7;
    private Double  mes8;
    private Double  mes9;
    private Double  mes10;
    private Double  mes11;
    private Double  mes12;
    private Double costoUnidosis;
    private Integer cantidadEnviado;
    private Double montoporservicio;

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(String idInventario) {
        this.idInventario = idInventario;
    }

    public Integer getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Integer periodo) {
        this.periodo = periodo;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public Double getMes1() {
        return mes1;
    }

    public void setMes1(Double mes1) {
        this.mes1 = mes1;
    }

    public Double getMes2() {
        return mes2;
    }

    public void setMes2(Double mes2) {
        this.mes2 = mes2;
    }

    public Double getMes3() {
        return mes3;
    }

    public void setMes3(Double mes3) {
        this.mes3 = mes3;
    }

    public Double getMes4() {
        return mes4;
    }

    public void setMes4(Double mes4) {
        this.mes4 = mes4;
    }

    public Double getMes5() {
        return mes5;
    }

    public void setMes5(Double mes5) {
        this.mes5 = mes5;
    }

    public Double getMes6() {
        return mes6;
    }

    public void setMes6(Double mes6) {
        this.mes6 = mes6;
    }

    public Double getMes7() {
        return mes7;
    }

    public void setMes7(Double mes7) {
        this.mes7 = mes7;
    }

    public Double getMes8() {
        return mes8;
    }

    public void setMes8(Double mes8) {
        this.mes8 = mes8;
    }

    public Double getMes9() {
        return mes9;
    }

    public void setMes9(Double mes9) {
        this.mes9 = mes9;
    }

    public Double getMes10() {
        return mes10;
    }

    public void setMes10(Double mes10) {
        this.mes10 = mes10;
    }

    public Double getMes11() {
        return mes11;
    }

    public void setMes11(Double mes11) {
        this.mes11 = mes11;
    }

    public Double getMes12() {
        return mes12;
    }

    public void setMes12(Double mes12) {
        this.mes12 = mes12;
    }
    
    public Double getCostoUnidosis() {
        return costoUnidosis;
    }

    public void setCostoUnidosis(Double costoUnidosis) {
        this.costoUnidosis = costoUnidosis;
    }

    public Integer getCantidadEnviado() {
        return cantidadEnviado;
    }

    public void setCantidadEnviado(Integer cantidadEnviado) {
        this.cantidadEnviado = cantidadEnviado;
    }

    public Double getMontoporservicio() {
        return montoporservicio;
    }

    public void setMontoporservicio(Double montoporservicio) {
        this.montoporservicio = montoporservicio;
    }
    
    
}
