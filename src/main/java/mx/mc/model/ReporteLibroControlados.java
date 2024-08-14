package mx.mc.model;

import java.io.Serializable;
import java.util.Date;

public class ReporteLibroControlados implements Serializable {
    private static final long serialVersionUID = 1L;

    private String lote;
    private Date fechaCaducidad;
    private Date fechaAno;
    private String procedencia;
    private String nombreMedico;
    private String direccion;
    private String cedula;
    private String numFactura;
    private String numReceta;
    private Integer cantidadAdquirida;
    private Integer cantidadVendida;
    private Integer saldo;
    private String firma;
    private String observaciones;
    private String idReabastoInsumo;
    private String idSurtimientoInsumo;
    private int refrigeracion;
    private String claveInstitucional;
    private int cantidadActual;
    private String nombreCorto;
    private String idEstructura;
    private String idMedicamento;
    private String nombreAlmacen;
    private Date fecha;
    private String nombreUsuario;
    private Integer existenciaAnterior;
    private Integer entradaPorSurtimiento;
    private Integer salidaPorReceta;
    private Integer existenciaActual;
    private String motivo;
    
    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public Date getFechaAno() {
        return fechaAno;
    }

    public void setFechaAno(Date fechaAno) {
        this.fechaAno = fechaAno;
    }

    public String getProcedencia() {
        return procedencia;
    }

    public void setProcedencia(String procedencia) {
        this.procedencia = procedencia;
    }

    public String getNombreMedico() {
        return nombreMedico;
    }

    public void setNombreMedico(String nombreMedico) {
        this.nombreMedico = nombreMedico;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNumFactura() {
        return numFactura;
    }

    public void setNumFactura(String numFactura) {
        this.numFactura = numFactura;
    }

    public String getNumReceta() {
        return numReceta;
    }

    public void setNumReceta(String numReceta) {
        this.numReceta = numReceta;
    }

    public Integer getCantidadAdquirida() {
        return cantidadAdquirida;
    }

    public void setCantidadAdquirida(Integer cantidadAdquirida) {
        this.cantidadAdquirida = cantidadAdquirida;
    }

    public Integer getCantidadVendida() {
        return cantidadVendida;
    }

    public void setCantidadVendida(Integer cantidadVendida) {
        this.cantidadVendida = cantidadVendida;
    }

    public Integer getSaldo() {
        return saldo;
    }

    public void setSaldo(Integer saldo) {
        this.saldo = saldo;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getIdReabastoInsumo() {
        return idReabastoInsumo;
    }

    public void setIdReabastoInsumo(String idReabastoInsumo) {
        this.idReabastoInsumo = idReabastoInsumo;
    }

    public String getIdSurtimientoInsumo() {
        return idSurtimientoInsumo;
    }

    public void setIdSurtimientoInsumo(String idSurtimientoInsumo) {
        this.idSurtimientoInsumo = idSurtimientoInsumo;
    }

    public int getRefrigeracion() {
        return refrigeracion;
    }

    public void setRefrigeracion(int refrigeracion) {
        this.refrigeracion = refrigeracion;
    }

    public String getClaveInstitucional() {
        return claveInstitucional;
    }

    public void setClaveInstitucional(String claveInstitucional) {
        this.claveInstitucional = claveInstitucional;
    }

    public int getCantidadActual() {
        return cantidadActual;
    }

    public void setCantidadActual(int cantidadActual) {
        this.cantidadActual = cantidadActual;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public String getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(String idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public String getNombreAlmacen() {
        return nombreAlmacen;
    }

    public void setNombreAlmacen(String nombreAlmacen) {
        this.nombreAlmacen = nombreAlmacen;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Integer getExistenciaAnterior() {
        return existenciaAnterior;
    }

    public void setExistenciaAnterior(Integer existenciaAnterior) {
        this.existenciaAnterior = existenciaAnterior;
    }

    public Integer getEntradaPorSurtimiento() {
        return entradaPorSurtimiento;
    }

    public void setEntradaPorSurtimiento(Integer entradaPorSurtimiento) {
        this.entradaPorSurtimiento = entradaPorSurtimiento;
    }

    public Integer getSalidaPorReceta() {
        return salidaPorReceta;
    }

    public void setSalidaPorReceta(Integer salidaPorReceta) {
        this.salidaPorReceta = salidaPorReceta;
    }

    public Integer getExistenciaActual() {
        return existenciaActual;
    }

    public void setExistenciaActual(Integer existenciaActual) {
        this.existenciaActual = existenciaActual;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

}
