/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.seidor.ulises.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import mx.mc.model.Medicamento_Extended;

/**
 *
 * @author apalacios
 */
public class ImpArticulos implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String ALGORITMO_ENTRADA = "FIFOLE";
    private static final String ALGORITMO_SALIDA = "FEFOT";
    private static final String TIPO_FLUJO = "TF-CU";
    private long id;
    private String articulo;
    private String descripcion;
    private String rotacion;
    private String algoritmoEntrada;
    private String algoritmoSalida;
    private int controlLote;
    private Double peso;
    private Double alto;
    private Double largo;
    private Double ancho;
    private String pathImagen;
    private int controlStock;
    private int agrupaPicksSalida;
    private String familia;
    private int controlNumeroSerie;
    private String unidadMedidaPicking;
    private int pesableMesa;
    private String unidadMedidaPeso;
    private String unidadMedidaVolumen;
    private String tipoKit;
    private String tipoFlujo;
    private String epicentro;
    private Double precio;
    private String datoExtra1;
    private String datoExtra2;
    private String datoExtra3;
    private int repartirCarruseles;
    private String tratado;
    private String error;
    private Date fechaTratado;
    private long version;
    private int controlFormato;
    private String tipoArticulo;
    private String tipoFisico;
    private Double nivEmbal1;
    private Double nivEmbal2;
    private Double nivEmbal3;
    private Double nivEmbal4;
    private Double nivEmbal5;
    private Double precioVenta;
    private String subFamilia;
    private String ubicacionExterna;
    private int controlExterno;
    private int pedirPassScanner;
    private int obsoleto;
    private Integer orden;
    private String tipoInventario;
    private String clasificaciones;
    private String valoresClasificaciones;
    private Date fechaCreacion;
    private int controlFechaCaducidad;
    private String estadoStockConteo;
    private String proveedor;
    private Double volumen;
    private int controlCalidad;
    private Integer latenciaEnvio;
    private Integer latenciaRecepcion;
    private String tipoEmpaquetado;
    private String codigoAntiguo;
    private String codigoProveedor;
    private Integer diasCaducidad;
    private String estadoCalidad;
    private String grupoCalidad;
    private int gestionCinta;
    private int mantenerLoteMismoLoteProveedor;
    private int controlLoteFabricacion;
    private int generarLoteInternoEntrada;
    private String checkListDescarga;
    private int refrigerado;
    private String propietario;

    public ImpArticulos(Medicamento_Extended medicamento) {
        articulo = medicamento.getClaveInstitucional();
        //Ulises maneja sólo 200 caracteres para la descripción del artículo
        descripcion = medicamento.getNombreCorto().length() <= 200 ? medicamento.getNombreCorto() : medicamento.getNombreCorto().substring(0, 200).trim();
        rotacion = null;
        algoritmoEntrada = ALGORITMO_ENTRADA;
        algoritmoSalida = ALGORITMO_SALIDA;
        controlLote = 1;
        peso = 0.0;
        alto = 0.0;
        largo = 0.0;
        ancho = 0.0;
        pathImagen = null;
        controlStock = 1;
        agrupaPicksSalida = 1;
        familia = medicamento.getNombreCategoria();
        controlNumeroSerie = 0;
        //Ulises maneja sólo 10 caracteres para la Unidad de Medida
        unidadMedidaPicking = medicamento.getNombreUnidadConcentracion().length() <= 10 ? medicamento.getNombreUnidadConcentracion() : medicamento.getNombreUnidadConcentracion().substring(0, 10).trim();
        pesableMesa = 0;
        unidadMedidaPeso = null;
        unidadMedidaVolumen = null;
        tipoKit = null;
        //TODO: en caso de medicamentos para Farmacia Extrahospitalaria, el tipoFlujo podría cambiar (TF-CA?)
        tipoFlujo = TIPO_FLUJO;
        epicentro = null;
        precio = medicamento.getPrecio() == null ? 0.0 : medicamento.getPrecio().doubleValue();
        datoExtra1 = null;
        datoExtra2 = null;
        datoExtra3 = null;
        repartirCarruseles = 0;
        tratado = "N";
        error = null;
        fechaTratado = null;
        version = 1;
        controlFormato = 0;
        // de acuerdo a lo comentado por Seidor, para que tipoArticulo = articulo, debemos enviar NULL y ellos copiarán el dato que traiga el campo articulo
        tipoArticulo = null;
        //-------------------------------------------------
        tipoFisico = null;//medicamento.getPresentacionSalida();
        nivEmbal1 = 1.0;
        nivEmbal2 = 0.0;
        nivEmbal3 = 0.0;
        nivEmbal4 = 0.0;
        nivEmbal5 = 0.0;
        precioVenta = 0.0;
        subFamilia = medicamento.getSubcategoria();
        ubicacionExterna = null;
        controlExterno = 0;
        pedirPassScanner = 0;
        obsoleto = 0;
        orden = 0;
        tipoInventario = null;
        clasificaciones = null;
        valoresClasificaciones = null;
        fechaCreacion = new Date();
        controlFechaCaducidad = 1;
        estadoStockConteo = null;
        proveedor = null;
        volumen = 0.0;
        controlCalidad = 0;
        latenciaEnvio = 0;
        latenciaRecepcion = 0;
        tipoEmpaquetado = null;
        codigoAntiguo = null;
        codigoProveedor = null;
        diasCaducidad = 0;
        estadoCalidad = null;
        grupoCalidad = null;
        gestionCinta = 1;
        mantenerLoteMismoLoteProveedor = 0;
        controlLoteFabricacion = 0;
        generarLoteInternoEntrada = 0;
        checkListDescarga = null;
        refrigerado = medicamento.getRefrigeracion() == null ? 0 : medicamento.getRefrigeracion();
        propietario = null;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRotacion() {
        return rotacion;
    }

    public void setRotacion(String rotacion) {
        this.rotacion = rotacion;
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

    public int getControlLote() {
        return controlLote;
    }

    public void setControlLote(int controlLote) {
        this.controlLote = controlLote;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getAlto() {
        return alto;
    }

    public void setAlto(Double alto) {
        this.alto = alto;
    }

    public Double getLargo() {
        return largo;
    }

    public void setLargo(Double largo) {
        this.largo = largo;
    }

    public Double getAncho() {
        return ancho;
    }

    public void setAncho(Double ancho) {
        this.ancho = ancho;
    }

    public String getPathImagen() {
        return pathImagen;
    }

    public void setPathImagen(String pathImagen) {
        this.pathImagen = pathImagen;
    }

    public int getControlStock() {
        return controlStock;
    }

    public void setControlStock(int controlStock) {
        this.controlStock = controlStock;
    }

    public int getAgrupaPicksSalida() {
        return agrupaPicksSalida;
    }

    public void setAgrupaPicksSalida(int agrupaPicksSalida) {
        this.agrupaPicksSalida = agrupaPicksSalida;
    }

    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }

    public int getControlNumeroSerie() {
        return controlNumeroSerie;
    }

    public void setControlNumeroSerie(int controlNumeroSerie) {
        this.controlNumeroSerie = controlNumeroSerie;
    }

    public String getUnidadMedidaPicking() {
        return unidadMedidaPicking;
    }

    public void setUnidadMedidaPicking(String unidadMedidaPicking) {
        this.unidadMedidaPicking = unidadMedidaPicking;
    }

    public int getPesableMesa() {
        return pesableMesa;
    }

    public void setPesableMesa(int pesableMesa) {
        this.pesableMesa = pesableMesa;
    }

    public String getUnidadMedidaPeso() {
        return unidadMedidaPeso;
    }

    public void setUnidadMedidaPeso(String unidadMedidaPeso) {
        this.unidadMedidaPeso = unidadMedidaPeso;
    }

    public String getUnidadMedidaVolumen() {
        return unidadMedidaVolumen;
    }

    public void setUnidadMedidaVolumen(String unidadMedidaVolumen) {
        this.unidadMedidaVolumen = unidadMedidaVolumen;
    }

    public String getTipoKit() {
        return tipoKit;
    }

    public void setTipoKit(String tipoKit) {
        this.tipoKit = tipoKit;
    }

    public String getTipoFlujo() {
        return tipoFlujo;
    }

    public void setTipoFlujo(String tipoFlujo) {
        this.tipoFlujo = tipoFlujo;
    }

    public String getEpicentro() {
        return epicentro;
    }

    public void setEpicentro(String epicentro) {
        this.epicentro = epicentro;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getDatoExtra1() {
        return datoExtra1;
    }

    public void setDatoExtra1(String datoExtra1) {
        this.datoExtra1 = datoExtra1;
    }

    public String getDatoExtra2() {
        return datoExtra2;
    }

    public void setDatoExtra2(String datoExtra2) {
        this.datoExtra2 = datoExtra2;
    }

    public String getDatoExtra3() {
        return datoExtra3;
    }

    public void setDatoExtra3(String datoExtra3) {
        this.datoExtra3 = datoExtra3;
    }

    public int getRepartirCarruseles() {
        return repartirCarruseles;
    }

    public void setRepartirCarruseles(int repartirCarruseles) {
        this.repartirCarruseles = repartirCarruseles;
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

    public int getControlFormato() {
        return controlFormato;
    }

    public void setControlFormato(int controlFormato) {
        this.controlFormato = controlFormato;
    }

    public String getTipoArticulo() {
        return tipoArticulo;
    }

    public void setTipoArticulo(String tipoArticulo) {
        this.tipoArticulo = tipoArticulo;
    }

    public String getTipoFisico() {
        return tipoFisico;
    }

    public void setTipoFisico(String tipoFisico) {
        this.tipoFisico = tipoFisico;
    }

    public Double getNivEmbal1() {
        return nivEmbal1;
    }

    public void setNivEmbal1(Double nivEmbal1) {
        this.nivEmbal1 = nivEmbal1;
    }

    public Double getNivEmbal2() {
        return nivEmbal2;
    }

    public void setNivEmbal2(Double nivEmbal2) {
        this.nivEmbal2 = nivEmbal2;
    }

    public Double getNivEmbal3() {
        return nivEmbal3;
    }

    public void setNivEmbal3(Double nivEmbal3) {
        this.nivEmbal3 = nivEmbal3;
    }

    public Double getNivEmbal4() {
        return nivEmbal4;
    }

    public void setNivEmbal4(Double nivEmbal4) {
        this.nivEmbal4 = nivEmbal4;
    }

    public Double getNivEmbal5() {
        return nivEmbal5;
    }

    public void setNivEmbal5(Double nivEmbal5) {
        this.nivEmbal5 = nivEmbal5;
    }

    public Double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(Double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public String getSubFamilia() {
        return subFamilia;
    }

    public void setSubFamilia(String subFamilia) {
        this.subFamilia = subFamilia;
    }

    public String getUbicacionExterna() {
        return ubicacionExterna;
    }

    public void setUbicacionExterna(String ubicacionExterna) {
        this.ubicacionExterna = ubicacionExterna;
    }

    public int getControlExterno() {
        return controlExterno;
    }

    public void setControlExterno(int controlExterno) {
        this.controlExterno = controlExterno;
    }

    public int getPedirPassScanner() {
        return pedirPassScanner;
    }

    public void setPedirPassScanner(int pedirPassScanner) {
        this.pedirPassScanner = pedirPassScanner;
    }

    public int getObsoleto() {
        return obsoleto;
    }

    public void setObsoleto(int obsoleto) {
        this.obsoleto = obsoleto;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public String getTipoInventario() {
        return tipoInventario;
    }

    public void setTipoInventario(String tipoInventario) {
        this.tipoInventario = tipoInventario;
    }

    public String getClasificaciones() {
        return clasificaciones;
    }

    public void setClasificaciones(String clasificaciones) {
        this.clasificaciones = clasificaciones;
    }

    public String getValoresClasificaciones() {
        return valoresClasificaciones;
    }

    public void setValoresClasificaciones(String valoresClasificaciones) {
        this.valoresClasificaciones = valoresClasificaciones;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public int getControlFechaCaducidad() {
        return controlFechaCaducidad;
    }

    public void setControlFechaCaducidad(int controlFechaCaducidad) {
        this.controlFechaCaducidad = controlFechaCaducidad;
    }

    public String getEstadoStockConteo() {
        return estadoStockConteo;
    }

    public void setEstadoStockConteo(String estadoStockConteo) {
        this.estadoStockConteo = estadoStockConteo;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public Double getVolumen() {
        return volumen;
    }

    public void setVolumen(Double volumen) {
        this.volumen = volumen;
    }

    public int getControlCalidad() {
        return controlCalidad;
    }

    public void setControlCalidad(int controlCalidad) {
        this.controlCalidad = controlCalidad;
    }

    public Integer getLatenciaEnvio() {
        return latenciaEnvio;
    }

    public void setLatenciaEnvio(Integer latenciaEnvio) {
        this.latenciaEnvio = latenciaEnvio;
    }

    public Integer getLatenciaRecepcion() {
        return latenciaRecepcion;
    }

    public void setLatenciaRecepcion(Integer latenciaRecepcion) {
        this.latenciaRecepcion = latenciaRecepcion;
    }

    public String getTipoEmpaquetado() {
        return tipoEmpaquetado;
    }

    public void setTipoEmpaquetado(String tipoEmpaquetado) {
        this.tipoEmpaquetado = tipoEmpaquetado;
    }

    public String getCodigoAntiguo() {
        return codigoAntiguo;
    }

    public void setCodigoAntiguo(String codigoAntiguo) {
        this.codigoAntiguo = codigoAntiguo;
    }

    public String getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoProveedor(String codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }

    public Integer getDiasCaducidad() {
        return diasCaducidad;
    }

    public void setDiasCaducidad(Integer diasCaducidad) {
        this.diasCaducidad = diasCaducidad;
    }

    public String getEstadoCalidad() {
        return estadoCalidad;
    }

    public void setEstadoCalidad(String estadoCalidad) {
        this.estadoCalidad = estadoCalidad;
    }

    public String getGrupoCalidad() {
        return grupoCalidad;
    }

    public void setGrupoCalidad(String grupoCalidad) {
        this.grupoCalidad = grupoCalidad;
    }

    public int getGestionCinta() {
        return gestionCinta;
    }

    public void setGestionCinta(int gestionCinta) {
        this.gestionCinta = gestionCinta;
    }

    public int getMantenerLoteMismoLoteProveedor() {
        return mantenerLoteMismoLoteProveedor;
    }

    public void setMantenerLoteMismoLoteProveedor(int mantenerLoteMismoLoteProveedor) {
        this.mantenerLoteMismoLoteProveedor = mantenerLoteMismoLoteProveedor;
    }

    public int getControlLoteFabricacion() {
        return controlLoteFabricacion;
    }

    public void setControlLoteFabricacion(int controlLoteFabricacion) {
        this.controlLoteFabricacion = controlLoteFabricacion;
    }

    public int getGenerarLoteInternoEntrada() {
        return generarLoteInternoEntrada;
    }

    public void setGenerarLoteInternoEntrada(int generarLoteInternoEntrada) {
        this.generarLoteInternoEntrada = generarLoteInternoEntrada;
    }

    public String getCheckListDescarga() {
        return checkListDescarga;
    }

    public void setCheckListDescarga(String checkListDescarga) {
        this.checkListDescarga = checkListDescarga;
    }

    public int getRefrigerado() {
        return refrigerado;
    }

    public void setRefrigerado(int refrigerado) {
        this.refrigerado = refrigerado;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 79 * hash + Objects.hashCode(this.articulo);
        hash = 79 * hash + Objects.hashCode(this.descripcion);
        hash = 79 * hash + Objects.hashCode(this.rotacion);
        hash = 79 * hash + Objects.hashCode(this.algoritmoEntrada);
        hash = 79 * hash + Objects.hashCode(this.algoritmoSalida);
        hash = 79 * hash + this.controlLote;
        hash = 79 * hash + Objects.hashCode(this.peso);
        hash = 79 * hash + Objects.hashCode(this.alto);
        hash = 79 * hash + Objects.hashCode(this.largo);
        hash = 79 * hash + Objects.hashCode(this.ancho);
        hash = 79 * hash + Objects.hashCode(this.pathImagen);
        hash = 79 * hash + this.controlStock;
        hash = 79 * hash + this.agrupaPicksSalida;
        hash = 79 * hash + Objects.hashCode(this.familia);
        hash = 79 * hash + this.controlNumeroSerie;
        hash = 79 * hash + Objects.hashCode(this.unidadMedidaPicking);
        hash = 79 * hash + this.pesableMesa;
        hash = 79 * hash + Objects.hashCode(this.unidadMedidaPeso);
        hash = 79 * hash + Objects.hashCode(this.unidadMedidaVolumen);
        hash = 79 * hash + Objects.hashCode(this.tipoKit);
        hash = 79 * hash + Objects.hashCode(this.tipoFlujo);
        hash = 79 * hash + Objects.hashCode(this.epicentro);
        hash = 79 * hash + Objects.hashCode(this.precio);
        hash = 79 * hash + Objects.hashCode(this.datoExtra1);
        hash = 79 * hash + Objects.hashCode(this.datoExtra2);
        hash = 79 * hash + Objects.hashCode(this.datoExtra3);
        hash = 79 * hash + this.repartirCarruseles;
        hash = 79 * hash + Objects.hashCode(this.tratado);
        hash = 79 * hash + Objects.hashCode(this.error);
        hash = 79 * hash + Objects.hashCode(this.fechaTratado);
        hash = 79 * hash + (int) (this.version ^ (this.version >>> 32));
        hash = 79 * hash + this.controlFormato;
        hash = 79 * hash + Objects.hashCode(this.tipoArticulo);
        hash = 79 * hash + Objects.hashCode(this.tipoFisico);
        hash = 79 * hash + Objects.hashCode(this.nivEmbal1);
        hash = 79 * hash + Objects.hashCode(this.nivEmbal2);
        hash = 79 * hash + Objects.hashCode(this.nivEmbal3);
        hash = 79 * hash + Objects.hashCode(this.nivEmbal4);
        hash = 79 * hash + Objects.hashCode(this.nivEmbal5);
        hash = 79 * hash + Objects.hashCode(this.precioVenta);
        hash = 79 * hash + Objects.hashCode(this.subFamilia);
        hash = 79 * hash + Objects.hashCode(this.ubicacionExterna);
        hash = 79 * hash + this.controlExterno;
        hash = 79 * hash + this.pedirPassScanner;
        hash = 79 * hash + this.obsoleto;
        hash = 79 * hash + Objects.hashCode(this.orden);
        hash = 79 * hash + Objects.hashCode(this.tipoInventario);
        hash = 79 * hash + Objects.hashCode(this.clasificaciones);
        hash = 79 * hash + Objects.hashCode(this.valoresClasificaciones);
        hash = 79 * hash + Objects.hashCode(this.fechaCreacion);
        hash = 79 * hash + this.controlFechaCaducidad;
        hash = 79 * hash + Objects.hashCode(this.estadoStockConteo);
        hash = 79 * hash + Objects.hashCode(this.proveedor);
        hash = 79 * hash + Objects.hashCode(this.volumen);
        hash = 79 * hash + this.controlCalidad;
        hash = 79 * hash + Objects.hashCode(this.latenciaEnvio);
        hash = 79 * hash + Objects.hashCode(this.latenciaRecepcion);
        hash = 79 * hash + Objects.hashCode(this.tipoEmpaquetado);
        hash = 79 * hash + Objects.hashCode(this.codigoAntiguo);
        hash = 79 * hash + Objects.hashCode(this.codigoProveedor);
        hash = 79 * hash + Objects.hashCode(this.diasCaducidad);
        hash = 79 * hash + Objects.hashCode(this.estadoCalidad);
        hash = 79 * hash + Objects.hashCode(this.grupoCalidad);
        hash = 79 * hash + this.gestionCinta;
        hash = 79 * hash + this.mantenerLoteMismoLoteProveedor;
        hash = 79 * hash + this.controlLoteFabricacion;
        hash = 79 * hash + this.generarLoteInternoEntrada;
        hash = 79 * hash + Objects.hashCode(this.checkListDescarga);
        hash = 79 * hash + this.refrigerado;
        hash = 79 * hash + Objects.hashCode(this.propietario);
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
        final ImpArticulos other = (ImpArticulos) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.controlLote != other.controlLote) {
            return false;
        }
        if (this.controlStock != other.controlStock) {
            return false;
        }
        if (this.agrupaPicksSalida != other.agrupaPicksSalida) {
            return false;
        }
        if (this.controlNumeroSerie != other.controlNumeroSerie) {
            return false;
        }
        if (this.pesableMesa != other.pesableMesa) {
            return false;
        }
        if (this.repartirCarruseles != other.repartirCarruseles) {
            return false;
        }
        if (this.version != other.version) {
            return false;
        }
        if (this.controlFormato != other.controlFormato) {
            return false;
        }
        if (this.controlExterno != other.controlExterno) {
            return false;
        }
        if (this.pedirPassScanner != other.pedirPassScanner) {
            return false;
        }
        if (this.obsoleto != other.obsoleto) {
            return false;
        }
        if (this.controlFechaCaducidad != other.controlFechaCaducidad) {
            return false;
        }
        if (this.controlCalidad != other.controlCalidad) {
            return false;
        }
        if (this.gestionCinta != other.gestionCinta) {
            return false;
        }
        if (this.mantenerLoteMismoLoteProveedor != other.mantenerLoteMismoLoteProveedor) {
            return false;
        }
        if (this.controlLoteFabricacion != other.controlLoteFabricacion) {
            return false;
        }
        if (this.generarLoteInternoEntrada != other.generarLoteInternoEntrada) {
            return false;
        }
        if (this.refrigerado != other.refrigerado) {
            return false;
        }
        if (!Objects.equals(this.articulo, other.articulo)) {
            return false;
        }
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        if (!Objects.equals(this.rotacion, other.rotacion)) {
            return false;
        }
        if (!Objects.equals(this.algoritmoEntrada, other.algoritmoEntrada)) {
            return false;
        }
        if (!Objects.equals(this.algoritmoSalida, other.algoritmoSalida)) {
            return false;
        }
        if (!Objects.equals(this.pathImagen, other.pathImagen)) {
            return false;
        }
        if (!Objects.equals(this.familia, other.familia)) {
            return false;
        }
        if (!Objects.equals(this.unidadMedidaPicking, other.unidadMedidaPicking)) {
            return false;
        }
        if (!Objects.equals(this.unidadMedidaPeso, other.unidadMedidaPeso)) {
            return false;
        }
        if (!Objects.equals(this.unidadMedidaVolumen, other.unidadMedidaVolumen)) {
            return false;
        }
        if (!Objects.equals(this.tipoKit, other.tipoKit)) {
            return false;
        }
        if (!Objects.equals(this.tipoFlujo, other.tipoFlujo)) {
            return false;
        }
        if (!Objects.equals(this.epicentro, other.epicentro)) {
            return false;
        }
        if (!Objects.equals(this.datoExtra1, other.datoExtra1)) {
            return false;
        }
        if (!Objects.equals(this.datoExtra2, other.datoExtra2)) {
            return false;
        }
        if (!Objects.equals(this.datoExtra3, other.datoExtra3)) {
            return false;
        }
        if (!Objects.equals(this.tratado, other.tratado)) {
            return false;
        }
        if (!Objects.equals(this.error, other.error)) {
            return false;
        }
        if (!Objects.equals(this.tipoArticulo, other.tipoArticulo)) {
            return false;
        }
        if (!Objects.equals(this.tipoFisico, other.tipoFisico)) {
            return false;
        }
        if (!Objects.equals(this.subFamilia, other.subFamilia)) {
            return false;
        }
        if (!Objects.equals(this.ubicacionExterna, other.ubicacionExterna)) {
            return false;
        }
        if (!Objects.equals(this.tipoInventario, other.tipoInventario)) {
            return false;
        }
        if (!Objects.equals(this.clasificaciones, other.clasificaciones)) {
            return false;
        }
        if (!Objects.equals(this.valoresClasificaciones, other.valoresClasificaciones)) {
            return false;
        }
        if (!Objects.equals(this.estadoStockConteo, other.estadoStockConteo)) {
            return false;
        }
        if (!Objects.equals(this.proveedor, other.proveedor)) {
            return false;
        }
        if (!Objects.equals(this.tipoEmpaquetado, other.tipoEmpaquetado)) {
            return false;
        }
        if (!Objects.equals(this.codigoAntiguo, other.codigoAntiguo)) {
            return false;
        }
        if (!Objects.equals(this.codigoProveedor, other.codigoProveedor)) {
            return false;
        }
        if (!Objects.equals(this.estadoCalidad, other.estadoCalidad)) {
            return false;
        }
        if (!Objects.equals(this.grupoCalidad, other.grupoCalidad)) {
            return false;
        }
        if (!Objects.equals(this.checkListDescarga, other.checkListDescarga)) {
            return false;
        }
        if (!Objects.equals(this.propietario, other.propietario)) {
            return false;
        }
        if (!Objects.equals(this.peso, other.peso)) {
            return false;
        }
        if (!Objects.equals(this.alto, other.alto)) {
            return false;
        }
        if (!Objects.equals(this.largo, other.largo)) {
            return false;
        }
        if (!Objects.equals(this.ancho, other.ancho)) {
            return false;
        }
        if (!Objects.equals(this.precio, other.precio)) {
            return false;
        }
        if (!Objects.equals(this.fechaTratado, other.fechaTratado)) {
            return false;
        }
        if (!Objects.equals(this.nivEmbal1, other.nivEmbal1)) {
            return false;
        }
        if (!Objects.equals(this.nivEmbal2, other.nivEmbal2)) {
            return false;
        }
        if (!Objects.equals(this.nivEmbal3, other.nivEmbal3)) {
            return false;
        }
        if (!Objects.equals(this.nivEmbal4, other.nivEmbal4)) {
            return false;
        }
        if (!Objects.equals(this.nivEmbal5, other.nivEmbal5)) {
            return false;
        }
        if (!Objects.equals(this.precioVenta, other.precioVenta)) {
            return false;
        }
        if (!Objects.equals(this.orden, other.orden)) {
            return false;
        }
        if (!Objects.equals(this.fechaCreacion, other.fechaCreacion)) {
            return false;
        }
        if (!Objects.equals(this.volumen, other.volumen)) {
            return false;
        }
        if (!Objects.equals(this.latenciaEnvio, other.latenciaEnvio)) {
            return false;
        }
        if (!Objects.equals(this.latenciaRecepcion, other.latenciaRecepcion)) {
            return false;
        }
        return Objects.equals(this.diasCaducidad, other.diasCaducidad);
    }
}
