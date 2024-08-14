/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author bbautista
 */
public class ClaveMedicamentoSku implements Serializable {
    private static final long serialVersionUID = 1L;

    private String claveIssemym;
    private String sku;
    private Integer cantXCaja;
    private String claveProveedor;
    private String propiedadInstituto;

    public ClaveMedicamentoSku() {
    }

    public ClaveMedicamentoSku(String claveIssemym, String sku, Integer cantXCaja, String claveProveedor, String propiedadInstituto) {
        this.claveIssemym = claveIssemym;
        this.sku = sku;
        this.cantXCaja = cantXCaja;
        this.claveProveedor = claveProveedor;
        this.propiedadInstituto = propiedadInstituto;
    }

    public String getClaveIssemym() {
        return claveIssemym;
    }

    public void setClaveIssemym(String claveIssemym) {
        this.claveIssemym = claveIssemym;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getCantXCaja() {
        return cantXCaja;
    }

    public void setCantXCaja(Integer cantXCaja) {
        this.cantXCaja = cantXCaja;
    }

    public String getClaveProveedor() {
        return claveProveedor;
    }

    public void setClaveProveedor(String claveProveedor) {
        this.claveProveedor = claveProveedor;
    }

    public String getPropiedadInstituto() {
        return propiedadInstituto;
    }

    public void setPropiedadInstituto(String propiedadInstituto) {
        this.propiedadInstituto = propiedadInstituto;
    }

    @Override
    public String toString() {
        return "ClaveMedicamentoSku{" + "claveIssemym=" + claveIssemym + ", sku=" + sku + ", cantXCaja=" + cantXCaja + ", claveProveedor=" + claveProveedor + ", propiedadInstituto=" + propiedadInstituto + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.claveIssemym);
        hash = 67 * hash + Objects.hashCode(this.sku);
        hash = 67 * hash + Objects.hashCode(this.cantXCaja);
        hash = 67 * hash + Objects.hashCode(this.claveProveedor);
        hash = 67 * hash + Objects.hashCode(this.propiedadInstituto);
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
        final ClaveMedicamentoSku other = (ClaveMedicamentoSku) obj;
        if (!Objects.equals(this.claveIssemym, other.claveIssemym)) {
            return false;
        }
        if (!Objects.equals(this.sku, other.sku)) {
            return false;
        }
        if (!Objects.equals(this.claveProveedor, other.claveProveedor)) {
            return false;
        }
        if (!Objects.equals(this.propiedadInstituto, other.propiedadInstituto)) {
            return false;
        }
        return Objects.equals(this.cantXCaja, other.cantXCaja);
    }

}
