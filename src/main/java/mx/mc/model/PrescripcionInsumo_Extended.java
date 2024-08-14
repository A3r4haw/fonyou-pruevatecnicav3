package mx.mc.model;

import java.util.List;

/**
 * @author AORTIZ
 */
public class PrescripcionInsumo_Extended extends PrescripcionInsumo {

    private static final long serialVersionUID = 1L;

    private String claveInstitucional;
    private String nombreCorto;
    private String nombreLargo;
    private String concentracion;
    private Integer idPresentacionSalida;
    private String cadenaBusqueda;
    private Integer cantidad24Horas;
    private Integer cantidadEntregada;
    private boolean indivisible;
    private String idEstructura;
    private String lote;
    private String idInventario;
    private Integer cantidadXCaja;
    private List<SurtimientoEnviado_Extend> surtimientoEnviadoExtendedList;
    private String idSurtimientoInsumo;
    private Integer cantidadSolicitada;

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

    public String getNombreLargo() {
        return nombreLargo;
    }

    public void setNombreLargo(String nombreLargo) {
        this.nombreLargo = nombreLargo;
    }

    public String getConcentracion() {
        return concentracion;
    }

    public void setConcentracion(String concentracion) {
        this.concentracion = concentracion;
    }

    public Integer getIdPresentacionSalida() {
        return idPresentacionSalida;
    }

    public void setIdPresentacionSalida(Integer idPresentacionSalida) {
        this.idPresentacionSalida = idPresentacionSalida;
    }

    public String getCadenaBusqueda() {
        return cadenaBusqueda;
    }

    public void setCadenaBusqueda(String cadenaBusqueda) {
        this.cadenaBusqueda = cadenaBusqueda;
    }

    public boolean isIndivisible() {
        return indivisible;
    }

    public void setIndivisible(boolean indivisible) {
        this.indivisible = indivisible;
    }

    public Integer getCantidad24Horas() {
        return cantidad24Horas;
    }

    public void setCantidad24Horas(Integer cantidad24Horas) {
        this.cantidad24Horas = cantidad24Horas;
    }

    public Integer getCantidadEntregada() {
        return cantidadEntregada;
    }

    public void setCantidadEntregada(Integer cantidadEntregada) {
        this.cantidadEntregada = cantidadEntregada;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(String idInventario) {
        this.idInventario = idInventario;
    }

    public Integer getCantidadXCaja() {
        return cantidadXCaja;
    }

    public void setCantidadXCaja(Integer cantidadXCaja) {
        this.cantidadXCaja = cantidadXCaja;
    }

    public List<SurtimientoEnviado_Extend> getSurtimientoEnviadoExtendedList() {
        return surtimientoEnviadoExtendedList;
    }

    public void setSurtimientoEnviadoExtendedList(List<SurtimientoEnviado_Extend> surtimientoEnviadoExtendedList) {
        this.surtimientoEnviadoExtendedList = surtimientoEnviadoExtendedList;
    }

    public String getIdSurtimientoInsumo() {
        return idSurtimientoInsumo;
    }

    public void setIdSurtimientoInsumo(String idSurtimientoInsumo) {
        this.idSurtimientoInsumo = idSurtimientoInsumo;
    }

    public Integer getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    public void setCantidadSolicitada(Integer cantidadSolicitada) {
        this.cantidadSolicitada = cantidadSolicitada;
    }

}
