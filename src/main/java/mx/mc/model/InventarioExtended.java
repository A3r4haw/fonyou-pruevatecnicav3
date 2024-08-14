package mx.mc.model;

import java.util.Date;

public class InventarioExtended extends Inventario {

    private static final long serialVersionUID = 1L;

    private Integer cantidadAjuste;
    private Integer idTipoMotivo;
    private int cantidadEntregada;
    private String descMovimiento;
    private String claveInstitucional;

    private String nombreProveedor;
    private String nombreFabricante;
    private String folioPrescripcion;
    private String folioSurtimiento;
    private Date fechaMovimiento;
    private String folioMovimiento;

    public Integer getCantidadAjuste() {
        return cantidadAjuste;
    }

    public void setCantidadAjuste(Integer cantidadAjuste) {
        this.cantidadAjuste = cantidadAjuste;
    }

    public Integer getIdTipoMotivo() {
        return idTipoMotivo;
    }

    public void setIdTipoMotivo(Integer idTipoMotivo) {
        this.idTipoMotivo = idTipoMotivo;
    }

    public String getDescMovimiento() {
        return descMovimiento;
    }

    public void setDescMovimiento(String descMovimiento) {
        this.descMovimiento = descMovimiento;
    }

    public int getCantidadEntregada() {
        return cantidadEntregada;
    }

    public void setCantidadEntregada(int cantidadEntregada) {
        this.cantidadEntregada = cantidadEntregada;
    }

    public String getClaveInstitucional() {
        return claveInstitucional;
    }

    public void setClaveInstitucional(String claveInstitucional) {
        this.claveInstitucional = claveInstitucional;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getNombreFabricante() {
        return nombreFabricante;
    }

    public void setNombreFabricante(String nombreFabricante) {
        this.nombreFabricante = nombreFabricante;
    }

    public String getFolioPrescripcion() {
        return folioPrescripcion;
    }

    public void setFolioPrescripcion(String folioPrescripcion) {
        this.folioPrescripcion = folioPrescripcion;
    }

    public String getFolioSurtimiento() {
        return folioSurtimiento;
    }

    public void setFolioSurtimiento(String folioSurtimiento) {
        this.folioSurtimiento = folioSurtimiento;
    }

    public Date getFechaMovimiento() {
        return fechaMovimiento;
    }

    public void setFechaMovimiento(Date fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    public String getFolioMovimiento() {
        return folioMovimiento;
    }

    public void setFolioMovimiento(String folioMovimiento) {
        this.folioMovimiento = folioMovimiento;
    }

}
