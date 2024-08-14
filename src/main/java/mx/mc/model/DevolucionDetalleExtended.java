package mx.mc.model;

import java.util.Date;
/**
 * 
 * @author gcruz
 *
 */
public class DevolucionDetalleExtended extends DevolucionDetalle{
	
    private static final long serialVersionUID = 1L;

    private String claveInstitucional;
    private String nombreCorto;
    private String nombrePresentacion;
    private Integer factorTransformacion;
    private String nombreMotivo;
    private Integer idTipoAlmacen;
    private Integer cantidadActual;
    private String lote;
    private Integer cantidadXCaja;
    private Integer presentacionComercial;
    private Date fechaCaducidad;
    private String idInventarioOrigen;
    private int cantidadActualOrigen;

    public String getClaveInstitucional() {
            return claveInstitucional;
    }

    public void setClaveInstitucional(String claveInstitucional) {
            this.claveInstitucional = claveInstitucional;
    }

    public String getNombreCorto() {
            return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
            this.nombreCorto = nombreCorto;
    }

    public String getNombrePresentacion() {
            return nombrePresentacion;
    }

    public void setNombrePresentacion(String nombrePresentacion) {
            this.nombrePresentacion = nombrePresentacion;
    }

    public Integer getFactorTransformacion() {
            return factorTransformacion;
    }

    public void setFactorTransformacion(Integer factorTransformacion) {
            this.factorTransformacion = factorTransformacion;
    }

    public String getNombreMotivo() {
            return nombreMotivo;
    }

    public void setNombreMotivo(String nombreMotivo) {
            this.nombreMotivo = nombreMotivo;
    }

    public Integer getIdTipoAlmacen() {
            return idTipoAlmacen;
    }

    public void setIdTipoAlmacen(Integer idTipoAlmacen) {
            this.idTipoAlmacen = idTipoAlmacen;
    }

    public Integer getCantidadActual() {
            return cantidadActual;
    }

    public void setCantidadActual(Integer cantidadActual) {
            this.cantidadActual = cantidadActual;
    }

    public String getLote() {
            return lote;
    }

    public void setLote(String lote) {
            this.lote = lote;
    }

    public Integer getCantidadXCaja() {
        return cantidadXCaja;
    }

    public void setCantidadXCaja(Integer cantidadXCaja) {
        this.cantidadXCaja = cantidadXCaja;
    }

    public Integer getPresentacionComercial() {
        return presentacionComercial;
    }

    public void setPresentacionComercial(Integer presentacionComercial) {
        this.presentacionComercial = presentacionComercial;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public String getIdInventarioOrigen() {
        return idInventarioOrigen;
    }

    public void setIdInventarioOrigen(String idInventarioOrigen) {
        this.idInventarioOrigen = idInventarioOrigen;
    }

    public int getCantidadActualOrigen() {
        return cantidadActualOrigen;
    }

    public void setCantidadActualOrigen(int cantidadActualOrigen) {
        this.cantidadActualOrigen = cantidadActualOrigen;
    }
        
}
