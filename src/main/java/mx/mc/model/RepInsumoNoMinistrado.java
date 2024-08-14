package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author olozada
 */
public class RepInsumoNoMinistrado implements Serializable {
    private static final long serialVersionUID = 1L;
   
   private String folioPrescripcion;
   private Date fechaPrescripcion; 
   private String estatusPrescripcion;
   private String folioSurtimiento;  
   private Date fechaSurtimiento;
   private String usuarioSurte;  
   private int cantidadEnviada; 
   private String claveInstitucional;
   private String nombreCorto;  
   private String lote; 
   private Date fechaCaducidad;  
   private String usuarioRecibe; 
   private int cantidadRecibido;  
   private String estatusSurtimiento;
   private Date fechaProgramadaMinistracion;
   private int cantidadMinistrada;
   private int diaSinMovimiento;  
   private Date fechaInicio;
   private Date fechaFin;

    public RepInsumoNoMinistrado() {
        //No code needed in constructor
    }

    @Override
    public String toString() {
        return "RepInsumoNoMinistrado{" + "folioPrescripcion=" + folioPrescripcion + ", fechaPrescripcion=" + fechaPrescripcion + ", estatusPrescripcion=" + estatusPrescripcion + ", folioSurtimiento=" + folioSurtimiento + ", fechaSurtimiento=" + fechaSurtimiento + ", usuarioSurte=" + usuarioSurte + ", cantidadEnviada=" + cantidadEnviada + ", claveInstitucional=" + claveInstitucional + ", nombreCorto=" + nombreCorto + ", lote=" + lote + ", fechaCaducidad=" + fechaCaducidad + ", usuarioRecibe=" + usuarioRecibe + ", cantidadRecibido=" + cantidadRecibido + ", estatusSurtimiento=" + estatusSurtimiento + ", fechaProgramadaMinistracion=" + fechaProgramadaMinistracion + ", cantidadMinistrada=" + cantidadMinistrada + ", diaSinMovimiento=" + diaSinMovimiento + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.folioPrescripcion);
        hash = 53 * hash + Objects.hashCode(this.fechaPrescripcion);
        hash = 53 * hash + Objects.hashCode(this.estatusPrescripcion);
        hash = 53 * hash + Objects.hashCode(this.folioSurtimiento);
        hash = 53 * hash + Objects.hashCode(this.fechaSurtimiento);
        hash = 53 * hash + Objects.hashCode(this.usuarioSurte);
        hash = 53 * hash + this.cantidadEnviada;
        hash = 53 * hash + Objects.hashCode(this.claveInstitucional);
        hash = 53 * hash + Objects.hashCode(this.nombreCorto);
        hash = 53 * hash + Objects.hashCode(this.lote);
        hash = 53 * hash + Objects.hashCode(this.fechaCaducidad);
        hash = 53 * hash + Objects.hashCode(this.usuarioRecibe);
        hash = 53 * hash + this.cantidadRecibido;
        hash = 53 * hash + Objects.hashCode(this.estatusSurtimiento);
        hash = 53 * hash + Objects.hashCode(this.fechaProgramadaMinistracion);
        hash = 53 * hash + this.cantidadMinistrada;
        hash = 53 * hash + this.diaSinMovimiento;
        hash = 53 * hash + Objects.hashCode(this.fechaInicio);
        hash = 53 * hash + Objects.hashCode(this.fechaFin);
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
        final RepInsumoNoMinistrado other = (RepInsumoNoMinistrado) obj;
        if (this.cantidadEnviada != other.cantidadEnviada) {
            return false;
        }
        if (this.cantidadRecibido != other.cantidadRecibido) {
            return false;
        }
        if (this.cantidadMinistrada != other.cantidadMinistrada) {
            return false;
        }
        if (this.diaSinMovimiento != other.diaSinMovimiento) {
            return false;
        }
        if (!Objects.equals(this.folioPrescripcion, other.folioPrescripcion)) {
            return false;
        }
        if (!Objects.equals(this.estatusPrescripcion, other.estatusPrescripcion)) {
            return false;
        }
        if (!Objects.equals(this.folioSurtimiento, other.folioSurtimiento)) {
            return false;
        }
        if (!Objects.equals(this.usuarioSurte, other.usuarioSurte)) {
            return false;
        }
        if (!Objects.equals(this.claveInstitucional, other.claveInstitucional)) {
            return false;
        }
        if (!Objects.equals(this.nombreCorto, other.nombreCorto)) {
            return false;
        }
        if (!Objects.equals(this.lote, other.lote)) {
            return false;
        }
        if (!Objects.equals(this.usuarioRecibe, other.usuarioRecibe)) {
            return false;
        }
        if (!Objects.equals(this.estatusSurtimiento, other.estatusSurtimiento)) {
            return false;
        }
        if (!Objects.equals(this.fechaPrescripcion, other.fechaPrescripcion)) {
            return false;
        }
        if (!Objects.equals(this.fechaSurtimiento, other.fechaSurtimiento)) {
            return false;
        }
        if (!Objects.equals(this.fechaCaducidad, other.fechaCaducidad)) {
            return false;
        }
        if (!Objects.equals(this.fechaProgramadaMinistracion, other.fechaProgramadaMinistracion)) {
            return false;
        }
        if (!Objects.equals(this.fechaInicio, other.fechaInicio)) {
            return false;
        }
        return Objects.equals(this.fechaFin, other.fechaFin);
    }

    public String getFolioPrescripcion() {
        return folioPrescripcion;
    }

    public void setFolioPrescripcion(String folioPrescripcion) {
        this.folioPrescripcion = folioPrescripcion;
    }

    public Date getFechaPrescripcion() {
        return fechaPrescripcion;
    }

    public void setFechaPrescripcion(Date fechaPrescripcion) {
        this.fechaPrescripcion = fechaPrescripcion;
    }

    public String getEstatusPrescripcion() {
        return estatusPrescripcion;
    }

    public void setEstatusPrescripcion(String estatusPrescripcion) {
        this.estatusPrescripcion = estatusPrescripcion;
    }

    public String getFolioSurtimiento() {
        return folioSurtimiento;
    }

    public void setFolioSurtimiento(String folioSurtimiento) {
        this.folioSurtimiento = folioSurtimiento;
    }

    public Date getFechaSurtimiento() {
        return fechaSurtimiento;
    }

    public void setFechaSurtimiento(Date fechaSurtimiento) {
        this.fechaSurtimiento = fechaSurtimiento;
    }

    public String getUsuarioSurte() {
        return usuarioSurte;
    }

    public void setUsuarioSurte(String usuarioSurte) {
        this.usuarioSurte = usuarioSurte;
    }

    public int getCantidadEnviada() {
        return cantidadEnviada;
    }

    public void setCantidadEnviada(int cantidadEnviada) {
        this.cantidadEnviada = cantidadEnviada;
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

    public String getUsuarioRecibe() {
        return usuarioRecibe;
    }

    public void setUsuarioRecibe(String usuarioRecibe) {
        this.usuarioRecibe = usuarioRecibe;
    }

    public int getCantidadRecibido() {
        return cantidadRecibido;
    }

    public void setCantidadRecibido(int cantidadRecibido) {
        this.cantidadRecibido = cantidadRecibido;
    }

    public String getEstatusSurtimiento() {
        return estatusSurtimiento;
    }

    public void setEstatusSurtimiento(String estatusSurtimiento) {
        this.estatusSurtimiento = estatusSurtimiento;
    }

    public Date getFechaProgramadaMinistracion() {
        return fechaProgramadaMinistracion;
    }

    public void setFechaProgramadaMinistracion(Date fechaProgramadaMinistracion) {
        this.fechaProgramadaMinistracion = fechaProgramadaMinistracion;
    }

    public int getCantidadMinistrada() {
        return cantidadMinistrada;
    }

    public void setCantidadMinistrada(int cantidadMinistrada) {
        this.cantidadMinistrada = cantidadMinistrada;
    }

    public int getDiaSinMovimiento() {
        return diaSinMovimiento;
    }

    public void setDiaSinMovimiento(int diaSinMovimiento) {
        this.diaSinMovimiento = diaSinMovimiento;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

   
}
