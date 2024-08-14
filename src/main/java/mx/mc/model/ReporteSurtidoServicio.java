/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author unava
 */
public class ReporteSurtidoServicio implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String claveInstitucional;
    private Date fechaSolicitud;
    private String folio;
    private String nombreCorto;
    private int cantidadSolicitada;
    private int cantidadSurtida;
    private float costoUnidosis;
    private String gpo;
    private String gen;
    private String esp;
    private String di;
    private String nombreHosp;
    private String nombreEstructura;
    private String domicilio;
    private String va;
    private String td;
    private String cantidad;
    private float importe;
    private String clavePresupuestal;
    public ReporteSurtidoServicio() {
    }
    
    public ReporteSurtidoServicio(String clavePresupuestal, String nombreHosp, String nombreEstructura, String domicilio) {
        this.clavePresupuestal = clavePresupuestal;
        this.nombreHosp = nombreHosp;
        this.nombreEstructura = nombreEstructura;
        this.domicilio = domicilio;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.claveInstitucional);
        hash = 67 * hash + Objects.hashCode(this.fechaSolicitud);
        hash = 67 * hash + Objects.hashCode(this.folio);
        hash = 67 * hash + Objects.hashCode(this.nombreCorto);
        hash = 67 * hash + this.cantidadSolicitada;
        hash = 67 * hash + this.cantidadSurtida;
        hash = 67 * hash + Float.floatToIntBits(this.costoUnidosis);
        hash = 67 * hash + Objects.hashCode(this.gpo);
        hash = 67 * hash + Objects.hashCode(this.gen);
        hash = 67 * hash + Objects.hashCode(this.esp);
        hash = 67 * hash + Objects.hashCode(this.di);
        hash = 67 * hash + Objects.hashCode(this.va);
        hash = 67 * hash + Objects.hashCode(this.td);
        hash = 67 * hash + Objects.hashCode(this.cantidad);
        hash = 67 * hash + Float.floatToIntBits(this.importe);
        return hash;
    }

    @Override
    public String toString() {
        return "ReporteSurtidoServicio{" + "claveInstitucional=" + claveInstitucional + ", fechaSolicitud=" + fechaSolicitud + ", folio=" + folio + ", nombreCorto=" + nombreCorto + ", cantidadSolicitada=" + cantidadSolicitada + ", cantidadSurtida=" + cantidadSurtida + ", costoUnidosis=" + costoUnidosis + ", gpo=" + gpo + ", gen=" + gen + ", esp=" + esp + ", di=" + di + ", va=" + va + ", td=" + td + ", cantidad=" + cantidad + ", importe=" + importe + '}';
    }

    

    

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ReporteSurtidoServicio other = (ReporteSurtidoServicio) obj;
        if (!Objects.equals(this.claveInstitucional, other.claveInstitucional)) {
            return false;
        }
        if (!Objects.equals(this.fechaSolicitud, other.fechaSolicitud)) {
            return false;
        }
        if (!Objects.equals(this.folio, other.folio)) {
            return false;
        }
        if (!Objects.equals(this.nombreCorto, other.nombreCorto)) {
            return false;
        }
        if (this.cantidadSolicitada != other.cantidadSolicitada) {
            return false;
        }
        if (this.cantidadSurtida != other.cantidadSurtida) {
            return false;
        }
        if (Float.floatToIntBits(this.costoUnidosis) != Float.floatToIntBits(other.costoUnidosis)) {
            return false;
        }
        if (!Objects.equals(this.gpo, other.gpo)) {
            return false;
        }
        if (!Objects.equals(this.gen, other.gen)) {
            return false;
        }
        if (!Objects.equals(this.esp, other.esp)) {
            return false;
        }
        if (!Objects.equals(this.di, other.di)) {
            return false;
        }
        if (!Objects.equals(this.va, other.va)) {
            return false;
        }
        if (!Objects.equals(this.td, other.td)) {
            return false;
        }
        if (!Objects.equals(this.cantidad, other.cantidad)) {
            return false;
        }
        return Objects.equals(this.importe, other.importe);
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

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getClavePresupuestal() {
        return clavePresupuestal;
    }

    public void setClavePresupuestal(String clavePresupuestal) {
        this.clavePresupuestal = clavePresupuestal;
    }

    public String getClaveInstitucional() {
        return claveInstitucional;
    }

    public void setClaveInstitucional(String claveInstitucional) {
        this.claveInstitucional = claveInstitucional;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    public int getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    public void setCantidadSolicitada(int cantidadSolicitada) {
        this.cantidadSolicitada = cantidadSolicitada;
    }

    public int getCantidadSurtida() {
        return cantidadSurtida;
    }

    public void setCantidadSurtida(int cantidadSurtida) {
        this.cantidadSurtida = cantidadSurtida;
    }

    public float getCostoUnidosis() {
        return costoUnidosis;
    }

    public void setCostoUnidosis(float costoUnidosis) {
        this.costoUnidosis = costoUnidosis;
    }

    public String getGpo() {
        return gpo;
    }

    public void setGpo(String gpo) {
        this.gpo = gpo;
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

    public String getDi() {
        return di;
    }

    public void setDi(String di) {
        this.di = di;
    }

    public String getVa() {
        return va;
    }

    public void setVa(String va) {
        this.va = va;
    }

    public String getTd() {
        return td;
    }

    public void setTd(String td) {
        this.td = td;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public float getImporte() {
        return importe;
    }

    public void setImporte(float importe) {
        this.importe = importe;
    }

    

    
    
}
