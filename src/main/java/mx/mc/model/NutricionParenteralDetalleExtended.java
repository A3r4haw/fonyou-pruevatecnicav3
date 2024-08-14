/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author gcruz
 */
public class NutricionParenteralDetalleExtended extends NutricionParenteralDetalle {

    private static final long serialVersionUID = 1L;
    
    private String claveMedicamento;
    private String nombreCorto;
    private String nombreLargo;
    private BigDecimal concentracion;
    private String nombrePresentacion;
    private String viaAdministracion;
    private boolean calculoMezcla;    
    private String nombreUnidadConcentracion;    
    private List<InventarioExtended> listaInventarios;
    private InventarioExtended inventarioExtended;
    
    public NutricionParenteralDetalleExtended() {
    
    }

    public NutricionParenteralDetalleExtended(String claveMedicamento, String nombreCorto, String nombreLargo, BigDecimal concentracion, String nombrePresentacion, String viaAdministracion, boolean calculoMezcla, List<InventarioExtended> listaInventarios, InventarioExtended inventarioExtended) {
        this.claveMedicamento = claveMedicamento;
        this.nombreCorto = nombreCorto;
        this.nombreLargo = nombreLargo;
        this.concentracion = concentracion;
        this.nombrePresentacion = nombrePresentacion;
        this.viaAdministracion = viaAdministracion;
        this.calculoMezcla = calculoMezcla;        
        this.listaInventarios = listaInventarios;
        this.inventarioExtended = inventarioExtended;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.claveMedicamento);
        hash = 53 * hash + Objects.hashCode(this.nombreCorto);
        hash = 53 * hash + Objects.hashCode(this.nombreLargo);
        hash = 53 * hash + Objects.hashCode(this.concentracion);
        hash = 53 * hash + Objects.hashCode(this.nombrePresentacion);
        hash = 53 * hash + Objects.hashCode(this.viaAdministracion);
        hash = 53 * hash + (this.calculoMezcla ? 1 : 0);        
        hash = 53 * hash + Objects.hashCode(this.listaInventarios);
        hash = 53 * hash + Objects.hashCode(this.inventarioExtended);
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
        final NutricionParenteralDetalleExtended other = (NutricionParenteralDetalleExtended) obj;
        if (this.calculoMezcla != other.calculoMezcla) {
            return false;
        }
        if (!Objects.equals(this.claveMedicamento, other.claveMedicamento)) {
            return false;
        }
        if (!Objects.equals(this.nombreCorto, other.nombreCorto)) {
            return false;
        }
        if (!Objects.equals(this.nombreLargo, other.nombreLargo)) {
            return false;
        }
        if (!Objects.equals(this.nombrePresentacion, other.nombrePresentacion)) {
            return false;
        }
        if (!Objects.equals(this.viaAdministracion, other.viaAdministracion)) {
            return false;
        }
        if (!Objects.equals(this.concentracion, other.concentracion)) {
            return false;
        }
        if (!Objects.equals(this.listaInventarios, other.listaInventarios)) {
            return false;
        }
        if (!Objects.equals(this.inventarioExtended, other.inventarioExtended)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "NutricionParenteralDetalleExtended{" + "claveMedicamento=" + claveMedicamento + ", nombreCorto=" + nombreCorto + ", nombreLargo=" + nombreLargo + ", concentracion=" + concentracion + ", nombrePresentacion=" + nombrePresentacion + ", viaAdministracion=" + viaAdministracion + ", calculoMezcla=" + calculoMezcla + ", listaInventarios=" + listaInventarios + ", inventarioExtended=" + inventarioExtended + '}';
    }    

    public String getClaveMedicamento() {
        return claveMedicamento;
    }

    public void setClaveMedicamento(String claveMedicamento) {
        this.claveMedicamento = claveMedicamento;
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

    public BigDecimal getConcentracion() {
        return concentracion;
    }

    public void setConcentracion(BigDecimal concentracion) {
        this.concentracion = concentracion;
    }

    public String getNombrePresentacion() {
        return nombrePresentacion;
    }

    public void setNombrePresentacion(String nombrePresentacion) {
        this.nombrePresentacion = nombrePresentacion;
    }

    public String getViaAdministracion() {
        return viaAdministracion;
    }

    public void setViaAdministracion(String viaAdministracion) {
        this.viaAdministracion = viaAdministracion;
    }

    public boolean isCalculoMezcla() {
        return calculoMezcla;
    }

    public void setCalculoMezcla(boolean calculoMezcla) {
        this.calculoMezcla = calculoMezcla;
    }
    
    public List<InventarioExtended> getListaInventarios() {
        return listaInventarios;
    }

    public void setListaInventarios(List<InventarioExtended> listaInventarios) {
        this.listaInventarios = listaInventarios;
    }

    public InventarioExtended getInventarioExtended() {
        return inventarioExtended;
    }

    public void setInventarioExtended(InventarioExtended inventarioExtended) {
        this.inventarioExtended = inventarioExtended;
    }

    public String getNombreUnidadConcentracion() {
        return nombreUnidadConcentracion;
    }

    public void setNombreUnidadConcentracion(String nombreUnidadConcentracion) {
        this.nombreUnidadConcentracion = nombreUnidadConcentracion;
    }

    
    
}
