package mx.mc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author hramirez
 */
public class DimesaRecetaMaterial implements Serializable {

    private static final long serialVersionUID = 1L;

    private BigDecimal cantidadEntregada;
    private BigDecimal cantidadSolicitada;
    private String clave;
    private String claveInterna;
    private String claveSap;
    private String descripcionSAP;
    private String dosis;
    private Integer posision;
    private String tratamiento;
    private String folio;
    private String folioPago;
    private BigDecimal cantidadXCaja;
    private BigDecimal cantidadVale;

    public DimesaRecetaMaterial() {
    }

    public DimesaRecetaMaterial(String folio, String folioPago) {
        this.folio = folio;
        this.folioPago = folioPago;
    }

    public BigDecimal getCantidadEntregada() {
        return cantidadEntregada;
    }

    public void setCantidadEntregada(BigDecimal cantidadEntregada) {
        this.cantidadEntregada = cantidadEntregada;
    }

    public BigDecimal getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    public void setCantidadSolicitada(BigDecimal cantidadSolicitada) {
        this.cantidadSolicitada = cantidadSolicitada;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getClaveInterna() {
        return claveInterna;
    }

    public void setClaveInterna(String claveInterna) {
        this.claveInterna = claveInterna;
    }

    public String getClaveSap() {
        return claveSap;
    }

    public void setClaveSap(String claveSap) {
        this.claveSap = claveSap;
    }

    public String getDescripcionSAP() {
        return descripcionSAP;
    }

    public void setDescripcionSAP(String descripcionSAP) {
        this.descripcionSAP = descripcionSAP;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public Integer getPosision() {
        return posision;
    }

    public void setPosision(Integer posision) {
        this.posision = posision;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getFolioPago() {
        return folioPago;
    }

    public void setFolioPago(String folioPago) {
        this.folioPago = folioPago;
    }

    public BigDecimal getCantidadXCaja() {
        return cantidadXCaja;
    }

    public void setCantidadXCaja(BigDecimal cantidadXCaja) {
        this.cantidadXCaja = cantidadXCaja;
    }

    public BigDecimal getCantidadVale() {
        return cantidadVale;
    }

    public void setCantidadVale(BigDecimal cantidadVale) {
        this.cantidadVale = cantidadVale;
    }

    @Override
    public String toString() {
        return "DimesaRecetaMaterial{" + "cantidadEntregada=" + cantidadEntregada + ", cantidadSolicitada=" + cantidadSolicitada + ", clave=" + clave + ", claveInterna=" + claveInterna + ", claveSap=" + claveSap + ", descripcionSAP=" + descripcionSAP + ", dosis=" + dosis + ", posision=" + posision + ", tratamiento=" + tratamiento + ", folio=" + folio + ", folioPago=" + folioPago + ", cantidadXCaja=" + cantidadXCaja + ", cantidadVale=" + cantidadVale + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.cantidadEntregada);
        hash = 53 * hash + Objects.hashCode(this.cantidadSolicitada);
        hash = 53 * hash + Objects.hashCode(this.clave);
        hash = 53 * hash + Objects.hashCode(this.claveInterna);
        hash = 53 * hash + Objects.hashCode(this.claveSap);
        hash = 53 * hash + Objects.hashCode(this.descripcionSAP);
        hash = 53 * hash + Objects.hashCode(this.dosis);
        hash = 53 * hash + Objects.hashCode(this.posision);
        hash = 53 * hash + Objects.hashCode(this.tratamiento);
        hash = 53 * hash + Objects.hashCode(this.folio);
        hash = 53 * hash + Objects.hashCode(this.folioPago);
        hash = 53 * hash + Objects.hashCode(this.cantidadXCaja);
        hash = 53 * hash + Objects.hashCode(this.cantidadVale);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DimesaRecetaMaterial other = (DimesaRecetaMaterial) obj;
        if (!Objects.equals(this.clave, other.clave)) {
            return false;
        }
        if (!Objects.equals(this.claveInterna, other.claveInterna)) {
            return false;
        }
        if (!Objects.equals(this.claveSap, other.claveSap)) {
            return false;
        }
        if (!Objects.equals(this.descripcionSAP, other.descripcionSAP)) {
            return false;
        }
        if (!Objects.equals(this.dosis, other.dosis)) {
            return false;
        }
        if (!Objects.equals(this.tratamiento, other.tratamiento)) {
            return false;
        }
        if (!Objects.equals(this.folio, other.folio)) {
            return false;
        }
        if (!Objects.equals(this.folioPago, other.folioPago)) {
            return false;
        }
        if (!Objects.equals(this.cantidadEntregada, other.cantidadEntregada)) {
            return false;
        }
        if (!Objects.equals(this.cantidadSolicitada, other.cantidadSolicitada)) {
            return false;
        }
        if (!Objects.equals(this.posision, other.posision)) {
            return false;
        }
        if (!Objects.equals(this.cantidadXCaja, other.cantidadXCaja)) {
            return false;
        }
        return Objects.equals(this.cantidadVale, other.cantidadVale);
    }

}
