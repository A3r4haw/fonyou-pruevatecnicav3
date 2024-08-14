/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.seidor.ulises.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import mx.mc.model.AlmacenControl;

/**
 *
 * @author apalacios
 */
public class ImpMaximosAlmacen implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String ALGORITMO_ENTRADA = "FIFOLE";
    private static final String ALGORITMO_SALIDA = "FEFOT";
    private long id;
    private String articulo;
    private String tipoAlmacen;
    private double minimo;
    private double maximo;
    private double maximoExtraccion;
    private double multiploReposicion;
    private String algoritmoEntrada;
    private String algoritmoSalida;
    private int entradas;
    private int salidas;
    private int repEnt;
    private int repSal;
    private int articuloEstatico;
    private Integer ordenSalida;
    private String tratado;
    private String error;
    private Date fechaTratado;
    private long version;
    private Integer orden;
    private Date fechaCreacion;
    private Integer maximoCanalesUa;
    private String rotacion;
    private int agrupaPicksSalida;
    private Double maximoReposicion;
    private int neMultiploReposicion;
    private int neMaximoReposicion;
    private int neMaximo;
    private int neMinimo;
    private Integer neMaximoExtraccion;

    public ImpMaximosAlmacen(AlmacenControl almacenControl, String tipoAlmacen) {
        this.articulo = almacenControl.getClaveInstitucional();
        this.tipoAlmacen = tipoAlmacen;
        this.minimo = almacenControl.getMinimo();
        this.maximo = almacenControl.getMaximo();
        this.maximoExtraccion = 99999;
        this.multiploReposicion = almacenControl.getReorden();
        this.algoritmoEntrada = ALGORITMO_ENTRADA;
        this.algoritmoSalida = ALGORITMO_SALIDA;
        this.entradas = 1;
        this.salidas = 1;
        this.repEnt = 1;
        this.repSal = 1;
        this.articuloEstatico = 0;
        this.ordenSalida = 0;
        this.tratado = "N";
        this.error = null;
        this.fechaTratado = null; 
        this.version = 1;
        this.orden = null;
        this.fechaCreacion = new Date(); 
        this.maximoCanalesUa = null;
        this.rotacion = null;
        this.agrupaPicksSalida = 1; 
        this.maximoReposicion = null;
        this.neMultiploReposicion = 1;
        this.neMaximoReposicion = 1;
        this.neMaximo = 1;
        this.neMinimo = 1;
        this.neMaximoExtraccion = null; 
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getArticulo() {
        return articulo;
    }

    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }

    public String getTipoAlmacen() {
        return tipoAlmacen;
    }

    public void setTipoAlmacen(String tipoAlmacen) {
        this.tipoAlmacen = tipoAlmacen;
    }

    public double getMinimo() {
        return minimo;
    }

    public void setMinimo(double minimo) {
        this.minimo = minimo;
    }

    public double getMaximo() {
        return maximo;
    }

    public void setMaximo(double maximo) {
        this.maximo = maximo;
    }

    public double getMaximoExtraccion() {
        return maximoExtraccion;
    }

    public void setMaximoExtraccion(double maximoExtraccion) {
        this.maximoExtraccion = maximoExtraccion;
    }

    public double getMultiploReposicion() {
        return multiploReposicion;
    }

    public void setMultiploReposicion(double multiploReposicion) {
        this.multiploReposicion = multiploReposicion;
    }

    public String getAlgoritmoEntrada() {
        return algoritmoEntrada;
    }

    public void setAlgoritmoEntrada(String algoritmoEntrada) {
        this.algoritmoEntrada = algoritmoEntrada;
    }

    public String getAlgoritmoSalida() {
        return algoritmoSalida;
    }

    public void setAlgoritmoSalida(String algoritmoSalida) {
        this.algoritmoSalida = algoritmoSalida;
    }

    public int getEntradas() {
        return entradas;
    }

    public void setEntradas(int entradas) {
        this.entradas = entradas;
    }

    public int getSalidas() {
        return salidas;
    }

    public void setSalidas(int salidas) {
        this.salidas = salidas;
    }

    public int getRepEnt() {
        return repEnt;
    }

    public void setRepEnt(int repEnt) {
        this.repEnt = repEnt;
    }

    public int getRepSal() {
        return repSal;
    }

    public void setRepSal(int repSal) {
        this.repSal = repSal;
    }

    public int getArticuloEstatico() {
        return articuloEstatico;
    }

    public void setArticuloEstatico(int articuloEstatico) {
        this.articuloEstatico = articuloEstatico;
    }

    public Integer getOrdenSalida() {
        return ordenSalida;
    }

    public void setOrdenSalida(Integer ordenSalida) {
        this.ordenSalida = ordenSalida;
    }

    public String getTratado() {
        return tratado;
    }

    public void setTratado(String tratado) {
        this.tratado = tratado;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Date getFechaTratado() {
        return fechaTratado;
    }

    public void setFechaTratado(Date fechaTratado) {
        this.fechaTratado = fechaTratado;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getMaximoCanalesUa() {
        return maximoCanalesUa;
    }

    public void setMaximoCanalesUa(Integer maximoCanalesUa) {
        this.maximoCanalesUa = maximoCanalesUa;
    }

    public String getRotacion() {
        return rotacion;
    }

    public void setRotacion(String rotacion) {
        this.rotacion = rotacion;
    }

    public int getAgrupaPicksSalida() {
        return agrupaPicksSalida;
    }

    public void setAgrupaPicksSalida(int agrupaPicksSalida) {
        this.agrupaPicksSalida = agrupaPicksSalida;
    }

    public Double getMaximoReposicion() {
        return maximoReposicion;
    }

    public void setMaximoReposicion(Double maximoReposicion) {
        this.maximoReposicion = maximoReposicion;
    }

    public int getNeMultiploReposicion() {
        return neMultiploReposicion;
    }

    public void setNeMultiploReposicion(int neMultiploReposicion) {
        this.neMultiploReposicion = neMultiploReposicion;
    }

    public int getNeMaximoReposicion() {
        return neMaximoReposicion;
    }

    public void setNeMaximoReposicion(int neMaximoReposicion) {
        this.neMaximoReposicion = neMaximoReposicion;
    }

    public int getNeMaximo() {
        return neMaximo;
    }

    public void setNeMaximo(int neMaximo) {
        this.neMaximo = neMaximo;
    }

    public int getNeMinimo() {
        return neMinimo;
    }

    public void setNeMinimo(int neMinimo) {
        this.neMinimo = neMinimo;
    }

    public Integer getNeMaximoExtraccion() {
        return neMaximoExtraccion;
    }

    public void setNeMaximoExtraccion(Integer neMaximoExtraccion) {
        this.neMaximoExtraccion = neMaximoExtraccion;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 53 * hash + Objects.hashCode(this.articulo);
        hash = 53 * hash + Objects.hashCode(this.tipoAlmacen);
        hash = 53 * hash + (int) (Double.doubleToLongBits(this.minimo) ^ (Double.doubleToLongBits(this.minimo) >>> 32));
        hash = 53 * hash + (int) (Double.doubleToLongBits(this.maximo) ^ (Double.doubleToLongBits(this.maximo) >>> 32));
        hash = 53 * hash + (int) (Double.doubleToLongBits(this.maximoExtraccion) ^ (Double.doubleToLongBits(this.maximoExtraccion) >>> 32));
        hash = 53 * hash + (int) (Double.doubleToLongBits(this.multiploReposicion) ^ (Double.doubleToLongBits(this.multiploReposicion) >>> 32));
        hash = 53 * hash + Objects.hashCode(this.algoritmoEntrada);
        hash = 53 * hash + Objects.hashCode(this.algoritmoSalida);
        hash = 53 * hash + this.entradas;
        hash = 53 * hash + this.salidas;
        hash = 53 * hash + this.repEnt;
        hash = 53 * hash + this.repSal;
        hash = 53 * hash + this.articuloEstatico;
        hash = 53 * hash + Objects.hashCode(this.ordenSalida);
        hash = 53 * hash + Objects.hashCode(this.tratado);
        hash = 53 * hash + Objects.hashCode(this.error);
        hash = 53 * hash + Objects.hashCode(this.fechaTratado);
        hash = 53 * hash + (int) (this.version ^ (this.version >>> 32));
        hash = 53 * hash + Objects.hashCode(this.orden);
        hash = 53 * hash + Objects.hashCode(this.fechaCreacion);
        hash = 53 * hash + Objects.hashCode(this.maximoCanalesUa);
        hash = 53 * hash + Objects.hashCode(this.rotacion);
        hash = 53 * hash + this.agrupaPicksSalida;
        hash = 53 * hash + Objects.hashCode(this.maximoReposicion);
        hash = 53 * hash + this.neMultiploReposicion;
        hash = 53 * hash + this.neMaximoReposicion;
        hash = 53 * hash + this.neMaximo;
        hash = 53 * hash + this.neMinimo;
        hash = 53 * hash + Objects.hashCode(this.neMaximoExtraccion);
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
        final ImpMaximosAlmacen other = (ImpMaximosAlmacen) obj;
        if (this.id != other.id) {
            return false;
        }
        if (Double.doubleToLongBits(this.minimo) != Double.doubleToLongBits(other.minimo)) {
            return false;
        }
        if (Double.doubleToLongBits(this.maximo) != Double.doubleToLongBits(other.maximo)) {
            return false;
        }
        if (Double.doubleToLongBits(this.maximoExtraccion) != Double.doubleToLongBits(other.maximoExtraccion)) {
            return false;
        }
        if (Double.doubleToLongBits(this.multiploReposicion) != Double.doubleToLongBits(other.multiploReposicion)) {
            return false;
        }
        if (this.entradas != other.entradas) {
            return false;
        }
        if (this.salidas != other.salidas) {
            return false;
        }
        if (this.repEnt != other.repEnt) {
            return false;
        }
        if (this.repSal != other.repSal) {
            return false;
        }
        if (this.articuloEstatico != other.articuloEstatico) {
            return false;
        }
        if (this.version != other.version) {
            return false;
        }
        if (this.agrupaPicksSalida != other.agrupaPicksSalida) {
            return false;
        }
        if (this.neMultiploReposicion != other.neMultiploReposicion) {
            return false;
        }
        if (this.neMaximoReposicion != other.neMaximoReposicion) {
            return false;
        }
        if (this.neMaximo != other.neMaximo) {
            return false;
        }
        if (this.neMinimo != other.neMinimo) {
            return false;
        }
        if (!Objects.equals(this.articulo, other.articulo)) {
            return false;
        }
        if (!Objects.equals(this.tipoAlmacen, other.tipoAlmacen)) {
            return false;
        }
        if (!Objects.equals(this.algoritmoEntrada, other.algoritmoEntrada)) {
            return false;
        }
        if (!Objects.equals(this.algoritmoSalida, other.algoritmoSalida)) {
            return false;
        }
        if (!Objects.equals(this.tratado, other.tratado)) {
            return false;
        }
        if (!Objects.equals(this.error, other.error)) {
            return false;
        }
        if (!Objects.equals(this.rotacion, other.rotacion)) {
            return false;
        }
        if (!Objects.equals(this.ordenSalida, other.ordenSalida)) {
            return false;
        }
        if (!Objects.equals(this.fechaTratado, other.fechaTratado)) {
            return false;
        }
        if (!Objects.equals(this.orden, other.orden)) {
            return false;
        }
        if (!Objects.equals(this.fechaCreacion, other.fechaCreacion)) {
            return false;
        }
        if (!Objects.equals(this.maximoCanalesUa, other.maximoCanalesUa)) {
            return false;
        }
        if (!Objects.equals(this.maximoReposicion, other.maximoReposicion)) {
            return false;
        }
        return Objects.equals(this.neMaximoExtraccion, other.neMaximoExtraccion);
    }
}
