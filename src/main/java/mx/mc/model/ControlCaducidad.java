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
public class ControlCaducidad implements Serializable{
    private static final long serialVersionUID = 1L;
    private Date fechaInicio;
    private Date fechaActual;
    private Date fechaElaboracion;
    private Date fechaCaducidad;
    private int cantidadRecibida;
    private String claveArticulo;
    private String lote;
    private String unidad;
    private int existenciaFisica;
    private int consumo;
    private String descripcion;
    private String proveedorClave;
    private String nombreProveedor;
    private String noNotificacion;
    private String tipoOrigen;
    private String nombreAlmacen;
    private String claveInstitucional;
    private String nombrePresentacion;
    private int cantidadActual;
    private int maximo;
    private String nombreCorto;
    private String empresa;
    private String nombre;

    public ControlCaducidad() {
        //No code needed in constructor
    }

    @Override
    public String toString() {
        return "ControlCaducidad{" + "fechaInicio=" + fechaInicio + ", fechaActual=" + fechaActual + ", fechaElaboracion=" + fechaElaboracion + ", fechaCaducidad=" + fechaCaducidad + ", cantidadRecibida=" + cantidadRecibida + ", claveArticulo=" + claveArticulo + ", lote=" + lote + ", unidad=" + unidad + ", existenciaFisica=" + existenciaFisica + ", consumo=" + consumo + ", descripcion=" + descripcion + ", proveedorClave=" + proveedorClave + ", nombreProveedor=" + nombreProveedor + ", noNotificacion=" + noNotificacion + ", tipoOrigen=" + tipoOrigen + ", nombreAlmacen=" + nombreAlmacen + ", claveInstitucional=" + claveInstitucional + ", nombrePresentacion=" + nombrePresentacion + ", cantidadActual=" + cantidadActual + ", maximo=" + maximo + ", nombreCorto=" + nombreCorto + ", empresa=" + empresa + ", nombre=" + nombre + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.fechaInicio);
        hash = 41 * hash + Objects.hashCode(this.fechaActual);
        hash = 41 * hash + Objects.hashCode(this.fechaElaboracion);
        hash = 41 * hash + Objects.hashCode(this.fechaCaducidad);
        hash = 41 * hash + this.cantidadRecibida;
        hash = 41 * hash + Objects.hashCode(this.claveArticulo);
        hash = 41 * hash + Objects.hashCode(this.lote);
        hash = 41 * hash + Objects.hashCode(this.unidad);
        hash = 41 * hash + this.existenciaFisica;
        hash = 41 * hash + this.consumo;
        hash = 41 * hash + Objects.hashCode(this.descripcion);
        hash = 41 * hash + Objects.hashCode(this.proveedorClave);
        hash = 41 * hash + Objects.hashCode(this.nombreProveedor);
        hash = 41 * hash + Objects.hashCode(this.noNotificacion);
        hash = 41 * hash + Objects.hashCode(this.tipoOrigen);
        hash = 41 * hash + Objects.hashCode(this.nombreAlmacen);
        hash = 41 * hash + Objects.hashCode(this.claveInstitucional);
        hash = 41 * hash + Objects.hashCode(this.nombrePresentacion);
        hash = 41 * hash + this.cantidadActual;
        hash = 41 * hash + this.maximo;
        hash = 41 * hash + Objects.hashCode(this.nombreCorto);
        hash = 41 * hash + Objects.hashCode(this.empresa);
        hash = 41 * hash + Objects.hashCode(this.nombre);
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
        final ControlCaducidad other = (ControlCaducidad) obj;
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
        if (this.cantidadRecibida != other.cantidadRecibida) {
            return false;
        }
        if (!Objects.equals(this.claveArticulo, other.claveArticulo)) {
            return false;
        }
        if (!Objects.equals(this.lote, other.lote)) {
            return false;
        }
        if (!Objects.equals(this.unidad, other.unidad)) {
            return false;
        }
        if (this.existenciaFisica != other.existenciaFisica) {
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
        if (!Objects.equals(this.nombreProveedor, other.nombreProveedor)) {
            return false;
        }
        if (!Objects.equals(this.noNotificacion, other.noNotificacion)) {
            return false;
        }
        if (!Objects.equals(this.tipoOrigen, other.tipoOrigen)) {
            return false;
        }
        if (!Objects.equals(this.nombreAlmacen, other.nombreAlmacen)) {
            return false;
        }
        if (!Objects.equals(this.claveInstitucional, other.claveInstitucional)) {
            return false;
        }
        if (!Objects.equals(this.nombrePresentacion, other.nombrePresentacion)) {
            return false;
        }
        if (this.cantidadActual != other.cantidadActual) {
            return false;
        }
        if (this.maximo != other.maximo) {
            return false;
        }
        if (!Objects.equals(this.nombreCorto, other.nombreCorto)) {
            return false;
        }
        if (!Objects.equals(this.empresa, other.empresa)) {
            return false;
        }
        return Objects.equals(this.nombre, other.nombre);
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

    public int getCantidadRecibida() {
        return cantidadRecibida;
    }

    public void setCantidadRecibida(int cantidadRecibida) {
        this.cantidadRecibida = cantidadRecibida;
    }

    public String getClaveArticulo() {
        return claveArticulo;
    }

    public void setClaveArticulo(String claveArticulo) {
        this.claveArticulo = claveArticulo;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public int getExistenciaFisica() {
        return existenciaFisica;
    }

    public void setExistenciaFisica(int existenciaFisica) {
        this.existenciaFisica = existenciaFisica;
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

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getNoNotificacion() {
        return noNotificacion;
    }

    public void setNoNotificacion(String noNotificacion) {
        this.noNotificacion = noNotificacion;
    }

    public String getTipoOrigen() {
        return tipoOrigen;
    }

    public void setTipoOrigen(String tipoOrigen) {
        this.tipoOrigen = tipoOrigen;
    }

    public String getNombreAlmacen() {
        return nombreAlmacen;
    }

    public void setNombreAlmacen(String nombreAlmacen) {
        this.nombreAlmacen = nombreAlmacen;
    }

    public String getClaveInstitucional() {
        return claveInstitucional;
    }

    public void setClaveInstitucional(String claveInstitucional) {
        this.claveInstitucional = claveInstitucional;
    }

    public String getNombrePresentacion() {
        return nombrePresentacion;
    }

    public void setNombrePresentacion(String nombrePresentacion) {
        this.nombrePresentacion = nombrePresentacion;
    }

    public int getCantidadActual() {
        return cantidadActual;
    }

    public void setCantidadActual(int cantidadActual) {
        this.cantidadActual = cantidadActual;
    }

    public int getMaximo() {
        return maximo;
    }

    public void setMaximo(int maximo) {
        this.maximo = maximo;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    
}   