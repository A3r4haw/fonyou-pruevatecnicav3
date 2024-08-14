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
 * @author unava
 */
public class ReporteConcentracionArticulos implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String claveInstitucional;
    private String nombreCorto;
    private int cantidad;
    private int precioUnitario;
    private int importe;
    private String clavePresupuestal;
    private String nombreHosp;
    private String nombreEstructura;
    private String domicilio;
    private String gen;
    private String esp;
    private String dif;
    private String var;
    
    public ReporteConcentracionArticulos() {
    }
    
    public ReporteConcentracionArticulos(String clavePresupuestal, String nombreHosp, String nombreEstructura, String domicilio) {
        this.clavePresupuestal = clavePresupuestal;
        this.nombreHosp = nombreHosp;
        this.nombreEstructura = nombreEstructura;
        this.domicilio = domicilio;
    }

    @Override
    public String toString() {
        return "ReporteConcentracionArticulos{" + "claveInstitucional=" + claveInstitucional + ", nombreCorto=" + nombreCorto + ", cantidad=" + cantidad + ", precioUnitario=" + precioUnitario + ", importe=" + importe + ", clavePresupuestal=" + clavePresupuestal + ", nombreHosp=" + nombreHosp + ", nombreEstructura=" + nombreEstructura + ", domicilio=" + domicilio + ", gen=" + gen + ", esp=" + esp + ", dif=" + dif + ", var=" + var + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + Objects.hashCode(this.claveInstitucional);
        hash = 73 * hash + Objects.hashCode(this.nombreCorto);
        hash = 73 * hash + this.cantidad;
        hash = 73 * hash + this.precioUnitario;
        hash = 73 * hash + this.importe;
        hash = 73 * hash + Objects.hashCode(this.clavePresupuestal);
        hash = 73 * hash + Objects.hashCode(this.nombreHosp);
        hash = 73 * hash + Objects.hashCode(this.nombreEstructura);
        hash = 73 * hash + Objects.hashCode(this.domicilio);
        hash = 73 * hash + Objects.hashCode(this.gen);
        hash = 73 * hash + Objects.hashCode(this.esp);
        hash = 73 * hash + Objects.hashCode(this.dif);
        hash = 73 * hash + Objects.hashCode(this.var);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ReporteConcentracionArticulos other = (ReporteConcentracionArticulos) obj;
        if (!Objects.equals(this.claveInstitucional, other.claveInstitucional)) {
            return false;
        }
        if (!Objects.equals(this.nombreCorto, other.nombreCorto)) {
            return false;
        }
        if (this.cantidad != other.cantidad) {
            return false;
        }
        if (this.precioUnitario != other.precioUnitario) {
            return false;
        }
        if (this.importe != other.importe) {
            return false;
        }
        if (!Objects.equals(this.clavePresupuestal, other.clavePresupuestal)) {
            return false;
        }
        if (!Objects.equals(this.nombreHosp, other.nombreHosp)) {
            return false;
        }
        if (!Objects.equals(this.nombreEstructura, other.nombreEstructura)) {
            return false;
        }
        if (!Objects.equals(this.domicilio, other.domicilio)) {
            return false;
        }
        if (!Objects.equals(this.gen, other.gen)) {
            return false;
        }
        if (!Objects.equals(this.esp, other.esp)) {
            return false;
        }
        if (!Objects.equals(this.dif, other.dif)) {
            return false;
        }
        return Objects.equals(this.var, other.var);
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

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

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(int precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public int getImporte() {
        return importe;
    }

    public void setImporte(int importe) {
        this.importe = importe;
    }

    public String getClavePresupuestal() {
        return clavePresupuestal;
    }

    public void setClavePresupuestal(String clavePresupuestal) {
        this.clavePresupuestal = clavePresupuestal;
    }

    public String getNombreHosp() {
        return nombreHosp;
    }

    public void setNombreHosp(String nombreHosp) {
        this.nombreHosp = nombreHosp;
    }

    public String getNombreEstructura() {
        return nombreEstructura;
    }

    public void setNombreEstructura(String nombreEstructura) {
        this.nombreEstructura = nombreEstructura;
    }

    public String getGen() {
        return gen;
    }

    public void setGen(String gen) {
        this.gen = gen;
    }

    public String getEsp() {
        return esp;
    }

    public void setEsp(String esp) {
        this.esp = esp;
    }

    public String getDif() {
        return dif;
    }

    public void setDif(String dif) {
        this.dif = dif;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }
}
