package mx.mc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ReportInventarioExistencias implements Serializable {
    private static final long serialVersionUID = 1L;

    private String idAlmacen;
    private String nombreAlmacen;
    private String claveMedicamento;
    private String nombreMedicamento;
    private String lote;
    private Integer existencias;
    private Integer comprometidas;
    private Date fechaCaducidad;
    private Double costoUnidosis;
    private Double costeXLote;
    private Integer cantidadXCaja;
    private String claveSap;
    private String idInventario; 
    private int presentacionComercial;
    private Integer estatusCaducidad;    
    private String idInsumo;
    private int cantidadActual;
    private int factorTransformacion;
    private String nombrePresentacion;
    private String nombreOrigen;
    private String calculoCajaUnidosis;
    private String codigoBarras;
    private BigDecimal concentracion;
    private String barrasProveedor;
    private String claveInstitucional;
    private String newCodBarInsert;
    private String insertIdUsuario;
    private Date insertFecha;
    private String claveProveedor;
    private String insertUsuario;
    private String updateUsuario;
    private Date updateFecha;
    
    
    public String getClaveSap() {
        return claveSap;
    }

    public void setClaveSap(String claveSap) {
        this.claveSap = claveSap;
    }
    

    public String getIdAlmacen() {
        return idAlmacen;
    }

    public void setIdAlmacen(String idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    public String getNombreAlmacen() {
        return nombreAlmacen;
    }

    public void setNombreAlmacen(String nombreAlmacen) {
        this.nombreAlmacen = nombreAlmacen;
    }

    public String getClaveMedicamento() {
        return claveMedicamento;
    }

    public void setClaveMedicamento(String claveMedicamento) {
        this.claveMedicamento = claveMedicamento;
    }

    public String getNombreMedicamento() {
        return nombreMedicamento;
    }

    public void setNombreMedicamento(String nombreMedicamento) {
        this.nombreMedicamento = nombreMedicamento;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public Integer getExistencias() {
        return existencias;
    }

    public void setExistencias(Integer existencias) {
        this.existencias = existencias;
    }

    public Integer getComprometidas() {
        return comprometidas;
    }

    public void setComprometidas(Integer comprometidas) {
        this.comprometidas = comprometidas;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public Double getCostoUnidosis() {
        return costoUnidosis;
    }

    public void setCostoUnidosis(Double costoUnidosis) {
        this.costoUnidosis = costoUnidosis;
    }

    public Double getCosteXLote() {
        return costeXLote;
    }

    public void setCosteXLote(Double costeXLote) {
        this.costeXLote = costeXLote;
    }

    public Integer getCantidadXCaja() {
        return cantidadXCaja;
    }

    public void setCantidadXCaja(Integer cantidadXCaja) {
        this.cantidadXCaja = cantidadXCaja;
    }

    public String getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(String idInventario) {
        this.idInventario = idInventario;
    }

    public int getPresentacionComercial() {
        return presentacionComercial;
    }

    public void setPresentacionComercial(int presentacionComercial) {
        this.presentacionComercial = presentacionComercial;
    }    

    public Integer getEstatusCaducidad() {
        return estatusCaducidad;
    }

    public void setEstatusCaducidad(Integer estatusCaducidad) {
        this.estatusCaducidad = estatusCaducidad;
    }

    public String getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(String idInsumo) {
        this.idInsumo = idInsumo;
    }

    public int getCantidadActual() {
        return cantidadActual;
    }

    public void setCantidadActual(int cantidadActual) {
        this.cantidadActual = cantidadActual;
    }

    public int getFactorTransformacion() {
        return factorTransformacion;
    }

    public void setFactorTransformacion(int factorTransformacion) {
        this.factorTransformacion = factorTransformacion;
    }

    public String getNombrePresentacion() {
        return nombrePresentacion;
    }

    public void setNombrePresentacion(String nombrePresentacion) {
        this.nombrePresentacion = nombrePresentacion;
    }   

    public String getNombreOrigen() {
        return nombreOrigen;
    }

    public void setNombreOrigen(String nombreOrigen) {
        this.nombreOrigen = nombreOrigen;
    }

    public String getCalculoCajaUnidosis() {
        return calculoCajaUnidosis;
    }

    public void setCalculoCajaUnidosis(String calculoCajaUnidosis) {
        this.calculoCajaUnidosis = calculoCajaUnidosis;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public BigDecimal getConcentracion() {
        return concentracion;
    }

    public void setConcentracion(BigDecimal concentracion) {
        this.concentracion = concentracion;
    }

    public String getBarrasProveedor() {
        return barrasProveedor;
    }

    public void setBarrasProveedor(String barrasProveedor) {
        this.barrasProveedor = barrasProveedor;
    }

    public String getClaveInstitucional() {
        return claveInstitucional;
    }

    public void setClaveInstitucional(String claveInstitucional) {
        this.claveInstitucional = claveInstitucional;
    }

    public String getNewCodBarInsert() {
        return newCodBarInsert;
    }

    public void setNewCodBarInsert(String newCodBarInsert) {
        this.newCodBarInsert = newCodBarInsert;
    }

    public String getInsertIdUsuario() {
        return insertIdUsuario;
    }

    public void setInsertIdUsuario(String insertIdUsuario) {
        this.insertIdUsuario = insertIdUsuario;
    }

    public Date getInsertFecha() {
        return insertFecha;
    }

    public void setInsertFecha(Date insertFecha) {
        this.insertFecha = insertFecha;
    }

    public String getClaveProveedor() {
        return claveProveedor;
    }

    public void setClaveProveedor(String claveProveedor) {
        this.claveProveedor = claveProveedor;
    }

    public String getInsertUsuario() {
        return insertUsuario;
    }

    public void setInsertUsuario(String insertUsuario) {
        this.insertUsuario = insertUsuario;
    }

    public String getUpdateUsuario() {
        return updateUsuario;
    }

    public void setUpdateUsuario(String updateUsuario) {
        this.updateUsuario = updateUsuario;
    }

    public Date getUpdateFecha() {
        return updateFecha;
    }

    public void setUpdateFecha(Date updateFecha) {
        this.updateFecha = updateFecha;
    }


    
    
       
}
