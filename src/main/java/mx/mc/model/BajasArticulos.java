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
 * @author olozada
 */
public class BajasArticulos implements Serializable {
    private static final long serialVersionUID = 1L;

    private Date fechaInicio;
    private Date fechaActual;
    private Date fechaElaboracion;
    private Date fechaCaducidad;
    private int cantidad;
    private String claveInstitucional;
    private String lote;
    private int unidad;
    private int existencia;
    private int consumo;
    private String descripcion;
    private String proveedorClave;
    private String proveedorNombre;
    private String noNotificación;
    private String nombreCorto;
    private int maximo;
    private int minimo;
    private int disponible;
    private int consumoPromedio;
    private int pendientePorRecibir;
    private int consumoEsperado;
    private int activo;
    private String cantidadActual;
    private int costo;

    public BajasArticulos() {
        //No code needed in constructor
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.fechaInicio);
        hash = 37 * hash + Objects.hashCode(this.fechaActual);
        hash = 37 * hash + Objects.hashCode(this.fechaElaboracion);
        hash = 37 * hash + Objects.hashCode(this.fechaCaducidad);
        hash = 37 * hash + this.cantidad;
        hash = 37 * hash + Objects.hashCode(this.claveInstitucional);
        hash = 37 * hash + Objects.hashCode(this.lote);
        hash = 37 * hash + this.unidad;
        hash = 37 * hash + this.existencia;
        hash = 37 * hash + this.consumo;
        hash = 37 * hash + Objects.hashCode(this.descripcion);
        hash = 37 * hash + Objects.hashCode(this.proveedorClave);
        hash = 37 * hash + Objects.hashCode(this.proveedorNombre);
        hash = 37 * hash + Objects.hashCode(this.noNotificación);
        hash = 37 * hash + Objects.hashCode(this.nombreCorto);
        hash = 37 * hash + this.maximo;
        hash = 37 * hash + this.minimo;
        hash = 37 * hash + this.disponible;
        hash = 37 * hash + this.consumoPromedio;
        hash = 37 * hash + this.pendientePorRecibir;
        hash = 37 * hash + this.consumoEsperado;
        hash = 37 * hash + this.activo;
        hash = 37 * hash + Objects.hashCode(this.cantidadActual);
        hash = 37 * hash + this.costo;
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
        final BajasArticulos other = (BajasArticulos) obj;
        if (!Objects.equals(this.fechaInicio, other.fechaInicio)) {
            return false;
        }
        if (!Objects.equals(this.fechaActual, other.fechaActual)) {
            return false;
        }
        if (!Objects.equals(this.fechaElaboracion, other.fechaElaboracion)) {
            return false;
        }
        if (!Objects.equals(this.fechaCaducidad, other.fechaCaducidad)) {
            return false;
        }
        if (this.cantidad != other.cantidad) {
            return false;
        }
        if (!Objects.equals(this.claveInstitucional, other.claveInstitucional)) {
            return false;
        }
        if (!Objects.equals(this.lote, other.lote)) {
            return false;
        }
        if (this.unidad != other.unidad) {
            return false;
        }
        if (this.existencia != other.existencia) {
            return false;
        }
        if (this.consumo != other.consumo) {
            return false;
        }
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        if (!Objects.equals(this.proveedorClave, other.proveedorClave)) {
            return false;
        }
        if (!Objects.equals(this.proveedorNombre, other.proveedorNombre)) {
            return false;
        }
        if (!Objects.equals(this.noNotificación, other.noNotificación)) {
            return false;
        }
        if (!Objects.equals(this.nombreCorto, other.nombreCorto)) {
            return false;
        }
        if (this.maximo != other.maximo) {
            return false;
        }
        if (this.minimo != other.minimo) {
            return false;
        }
        if (this.disponible != other.disponible) {
            return false;
        }
        if (this.consumoPromedio != other.consumoPromedio) {
            return false;
        }
        if (this.pendientePorRecibir != other.pendientePorRecibir) {
            return false;
        }
        if (this.consumoEsperado != other.consumoEsperado) {
            return false;
        }
        if (this.activo != other.activo) {
            return false;
        }
        if (!Objects.equals(this.cantidadActual, other.cantidadActual)) {
            return false;
        }
        return (this.costo == other.costo);
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public Date getFechaElaboracion() {
        return fechaElaboracion;
    }

    public void setFechaElaboracion(Date fechaElaboracion) {
        this.fechaElaboracion = fechaElaboracion;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getClaveInstitucional() {
        return claveInstitucional;
    }

    public void setClaveInstitucional(String claveInstitucional) {
        this.claveInstitucional = claveInstitucional;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public int getUnidad() {
        return unidad;
    }

    public void setUnidad(int unidad) {
        this.unidad = unidad;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    public int getConsumo() {
        return consumo;
    }

    public void setConsumo(int consumo) {
        this.consumo = consumo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getProveedorClave() {
        return proveedorClave;
    }

    public void setProveedorClave(String proveedorClave) {
        this.proveedorClave = proveedorClave;
    }

    public String getProveedorNombre() {
        return proveedorNombre;
    }

    public void setProveedorNombre(String proveedorNombre) {
        this.proveedorNombre = proveedorNombre;
    }

    public String getNoNotificacion() {
        return noNotificación;
    }

    public void setNoNotificacion(String noNotificación) {
        this.noNotificación = noNotificación;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    public int getMaximo() {
        return maximo;
    }

    public void setMaximo(int maximo) {
        this.maximo = maximo;
    }

    public int getMinimo() {
        return minimo;
    }

    public void setMinimo(int minimo) {
        this.minimo = minimo;
    }

    public int getDisponible() {
        return disponible;
    }

    public void setDisponible(int disponible) {
        this.disponible = disponible;
    }

    public int getConsumoPromedio() {
        return consumoPromedio;
    }

    public void setConsumoPromedio(int consumoPromedio) {
        this.consumoPromedio = consumoPromedio;
    }

    public int getPendientePorRecibir() {
        return pendientePorRecibir;
    }

    public void setPendientePorRecibir(int pendientePorRecibir) {
        this.pendientePorRecibir = pendientePorRecibir;
    }

    public int getConsumoEsperado() {
        return consumoEsperado;
    }

    public void setConsumoEsperado(int consumoEsperado) {
        this.consumoEsperado = consumoEsperado;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public String getCantidadActual() {
        return cantidadActual;
    }

    public void setCantidadActual(String cantidadActual) {
        this.cantidadActual = cantidadActual;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    
}
