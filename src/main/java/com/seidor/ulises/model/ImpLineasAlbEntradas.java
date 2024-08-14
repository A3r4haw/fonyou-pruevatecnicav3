/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.seidor.ulises.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author apalacios
 */
public class ImpLineasAlbEntradas implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private String albaran;
    private String linea;
    private String articulo;
    private String lote;
    private String formato;
    private double cantidad;
    private String ubicacion;
    private Integer bulto;
    private String tipoFlujo;
    private String tipoAlmacen;
    private Long idAlb;
    private String tratado;
    private String error;
    private Date fechaTratado;
    private long version;
    private String descripcion;
    private String observacion;
    private int autoconfirmada;
    private Integer orden;
    private String fechaCaducidad;
    private String planta;
    private String seccion;
    private String habitacion;
    private String cama;
    private String paciente;
    private String histPaciente;
    private String obsMegaDosis1;
    private String medico;
    private String obsMegaDosis2;
    private String obsMegaDosis3;
    private String bcValidacion;
    private String posologia;
    private String fechaRecepcion;
    private Date fechaCreacion;
    private String tipoStock;
    private String documento;
    private String estadoLote;
    private String ua;
    private String tipoLote;
    private int controlCalidad;
    private String documentoTransporte;
    private String clasificaciones;
    private String valoresClasificaciones;
    private String reserva;
    private String fechaFabricacion;
    private long toleranciaConteo;
    private int ignoraCapacidad;
    private int ignoraMaximos;
    private String identificador;
    private String checkListDescarga;
    private String almacenLogico;
    private int conteoUnitario;
    private String estadoStockDespuesContar;
    private String tipoUa;

    public ImpLineasAlbEntradas(String folioReabasto, String claveInstitucional, String lote, Date fechaCaducidad, int cantidadIngresada, int consecutivo) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        this.albaran = folioReabasto;
        this.linea = String.valueOf(consecutivo);
        this.articulo = claveInstitucional;
        this.lote = lote;
        this.formato = null;
        this.cantidad = cantidadIngresada;
        this.ubicacion = null;
        this.bulto = 0;
        this.tipoFlujo = null;
        this.tipoAlmacen = null;
        this.idAlb = null;
        this.tratado = "N";
        this.error = null;
        this.fechaTratado = null;
        this.version = 1;
        this.descripcion = null;
        this.observacion = null;
        this.autoconfirmada = 0;
        this.orden = 0;
        this.fechaCaducidad = sdf.format(fechaCaducidad);
        this.planta = null;
        this.seccion = null;
        this.habitacion = null;
        this.cama = null;
        this.paciente = null;
        this.histPaciente = null;
        this.obsMegaDosis1 = null;
        this.medico = null;
        this.obsMegaDosis2 = null;
        this.obsMegaDosis3 = null;
        this.bcValidacion = null;
        this.posologia = null;
        this.fechaRecepcion = null;
        this.fechaCreacion = new Date();
        this.tipoStock = null;
        this.documento = null;
        this.estadoLote = null;
        this.ua = null;
        this.tipoLote = null;
        this.controlCalidad = 0;
        this.documentoTransporte = null;
        this.clasificaciones = null;
        this.valoresClasificaciones = null;
        this.reserva = null;
        this.fechaFabricacion = null;
        this.toleranciaConteo = 0;
        this.ignoraCapacidad = 0;
        this.ignoraMaximos = 0;
        this.identificador = null;
        this.checkListDescarga = null;
        this.almacenLogico = null;
        this.conteoUnitario = 0;
        this.estadoStockDespuesContar = null;
        this.tipoUa = null;
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAlbaran() {
        return albaran;
    }

    public void setAlbaran(String albaran) {
        this.albaran = albaran;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public String getArticulo() {
        return articulo;
    }

    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Integer getBulto() {
        return bulto;
    }

    public void setBulto(Integer bulto) {
        this.bulto = bulto;
    }

    public String getTipoFlujo() {
        return tipoFlujo;
    }

    public void setTipoFlujo(String tipoFlujo) {
        this.tipoFlujo = tipoFlujo;
    }

    public String getTipoAlmacen() {
        return tipoAlmacen;
    }

    public void setTipoAlmacen(String tipoAlmacen) {
        this.tipoAlmacen = tipoAlmacen;
    }

    public Long getIdAlb() {
        return idAlb;
    }

    public void setIdAlb(Long idAlb) {
        this.idAlb = idAlb;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public int getAutoconfirmada() {
        return autoconfirmada;
    }

    public void setAutoconfirmada(int autoconfirmada) {
        this.autoconfirmada = autoconfirmada;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public String getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(String fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public String getPlanta() {
        return planta;
    }

    public void setPlanta(String planta) {
        this.planta = planta;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(String habitacion) {
        this.habitacion = habitacion;
    }

    public String getCama() {
        return cama;
    }

    public void setCama(String cama) {
        this.cama = cama;
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public String getHistPaciente() {
        return histPaciente;
    }

    public void setHistPaciente(String histPaciente) {
        this.histPaciente = histPaciente;
    }

    public String getObsMegaDosis1() {
        return obsMegaDosis1;
    }

    public void setObsMegaDosis1(String obsMegaDosis1) {
        this.obsMegaDosis1 = obsMegaDosis1;
    }

    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public String getObsMegaDosis2() {
        return obsMegaDosis2;
    }

    public void setObsMegaDosis2(String obsMegaDosis2) {
        this.obsMegaDosis2 = obsMegaDosis2;
    }

    public String getObsMegaDosis3() {
        return obsMegaDosis3;
    }

    public void setObsMegaDosis3(String obsMegaDosis3) {
        this.obsMegaDosis3 = obsMegaDosis3;
    }

    public String getBcValidacion() {
        return bcValidacion;
    }

    public void setBcValidacion(String bcValidacion) {
        this.bcValidacion = bcValidacion;
    }

    public String getPosologia() {
        return posologia;
    }

    public void setPosologia(String posologia) {
        this.posologia = posologia;
    }

    public String getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(String fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getTipoStock() {
        return tipoStock;
    }

    public void setTipoStock(String tipoStock) {
        this.tipoStock = tipoStock;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getEstadoLote() {
        return estadoLote;
    }

    public void setEstadoLote(String estadoLote) {
        this.estadoLote = estadoLote;
    }

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public String getTipoLote() {
        return tipoLote;
    }

    public void setTipoLote(String tipoLote) {
        this.tipoLote = tipoLote;
    }

    public int getControlCalidad() {
        return controlCalidad;
    }

    public void setControlCalidad(int controlCalidad) {
        this.controlCalidad = controlCalidad;
    }

    public String getDocumentoTransporte() {
        return documentoTransporte;
    }

    public void setDocumentoTransporte(String documentoTransporte) {
        this.documentoTransporte = documentoTransporte;
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

    public String getReserva() {
        return reserva;
    }

    public void setReserva(String reserva) {
        this.reserva = reserva;
    }

    public String getFechaFabricacion() {
        return fechaFabricacion;
    }

    public void setFechaFabricacion(String fechaFabricacion) {
        this.fechaFabricacion = fechaFabricacion;
    }

    public long getToleranciaConteo() {
        return toleranciaConteo;
    }

    public void setToleranciaConteo(long toleranciaConteo) {
        this.toleranciaConteo = toleranciaConteo;
    }

    public int getIgnoraCapacidad() {
        return ignoraCapacidad;
    }

    public void setIgnoraCapacidad(int ignoraCapacidad) {
        this.ignoraCapacidad = ignoraCapacidad;
    }

    public int getIgnoraMaximos() {
        return ignoraMaximos;
    }

    public void setIgnoraMaximos(int ignoraMaximos) {
        this.ignoraMaximos = ignoraMaximos;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getCheckListDescarga() {
        return checkListDescarga;
    }

    public void setCheckListDescarga(String checkListDescarga) {
        this.checkListDescarga = checkListDescarga;
    }

    public String getAlmacenLogico() {
        return almacenLogico;
    }

    public void setAlmacenLogico(String almacenLogico) {
        this.almacenLogico = almacenLogico;
    }

    public int getConteoUnitario() {
        return conteoUnitario;
    }

    public void setConteoUnitario(int conteoUnitario) {
        this.conteoUnitario = conteoUnitario;
    }

    public String getEstadoStockDespuesContar() {
        return estadoStockDespuesContar;
    }

    public void setEstadoStockDespuesContar(String estadoStockDespuesContar) {
        this.estadoStockDespuesContar = estadoStockDespuesContar;
    }

    public String getTipoUa() {
        return tipoUa;
    }

    public void setTipoUa(String tipoUa) {
        this.tipoUa = tipoUa;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 97 * hash + Objects.hashCode(this.albaran);
        hash = 97 * hash + Objects.hashCode(this.linea);
        hash = 97 * hash + Objects.hashCode(this.articulo);
        hash = 97 * hash + Objects.hashCode(this.lote);
        hash = 97 * hash + Objects.hashCode(this.formato);
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.cantidad) ^ (Double.doubleToLongBits(this.cantidad) >>> 32));
        hash = 97 * hash + Objects.hashCode(this.ubicacion);
        hash = 97 * hash + Objects.hashCode(this.bulto);
        hash = 97 * hash + Objects.hashCode(this.tipoFlujo);
        hash = 97 * hash + Objects.hashCode(this.tipoAlmacen);
        hash = 97 * hash + (int) (this.idAlb ^ (this.idAlb >>> 32));
        hash = 97 * hash + Objects.hashCode(this.tratado);
        hash = 97 * hash + Objects.hashCode(this.error);
        hash = 97 * hash + Objects.hashCode(this.fechaTratado);
        hash = 97 * hash + (int) (this.version ^ (this.version >>> 32));
        hash = 97 * hash + Objects.hashCode(this.descripcion);
        hash = 97 * hash + Objects.hashCode(this.observacion);
        hash = 97 * hash + this.autoconfirmada;
        hash = 97 * hash + Objects.hashCode(this.orden);
        hash = 97 * hash + Objects.hashCode(this.fechaCaducidad);
        hash = 97 * hash + Objects.hashCode(this.planta);
        hash = 97 * hash + Objects.hashCode(this.seccion);
        hash = 97 * hash + Objects.hashCode(this.habitacion);
        hash = 97 * hash + Objects.hashCode(this.cama);
        hash = 97 * hash + Objects.hashCode(this.paciente);
        hash = 97 * hash + Objects.hashCode(this.histPaciente);
        hash = 97 * hash + Objects.hashCode(this.obsMegaDosis1);
        hash = 97 * hash + Objects.hashCode(this.medico);
        hash = 97 * hash + Objects.hashCode(this.obsMegaDosis2);
        hash = 97 * hash + Objects.hashCode(this.obsMegaDosis3);
        hash = 97 * hash + Objects.hashCode(this.bcValidacion);
        hash = 97 * hash + Objects.hashCode(this.posologia);
        hash = 97 * hash + Objects.hashCode(this.fechaRecepcion);
        hash = 97 * hash + Objects.hashCode(this.fechaCreacion);
        hash = 97 * hash + Objects.hashCode(this.tipoStock);
        hash = 97 * hash + Objects.hashCode(this.documento);
        hash = 97 * hash + Objects.hashCode(this.estadoLote);
        hash = 97 * hash + Objects.hashCode(this.ua);
        hash = 97 * hash + Objects.hashCode(this.tipoLote);
        hash = 97 * hash + this.controlCalidad;
        hash = 97 * hash + Objects.hashCode(this.documentoTransporte);
        hash = 97 * hash + Objects.hashCode(this.clasificaciones);
        hash = 97 * hash + Objects.hashCode(this.valoresClasificaciones);
        hash = 97 * hash + Objects.hashCode(this.reserva);
        hash = 97 * hash + Objects.hashCode(this.fechaFabricacion);
        hash = 97 * hash + (int) (this.toleranciaConteo ^ (this.toleranciaConteo >>> 32));
        hash = 97 * hash + this.ignoraCapacidad;
        hash = 97 * hash + this.ignoraMaximos;
        hash = 97 * hash + Objects.hashCode(this.identificador);
        hash = 97 * hash + Objects.hashCode(this.checkListDescarga);
        hash = 97 * hash + Objects.hashCode(this.almacenLogico);
        hash = 97 * hash + this.conteoUnitario;
        hash = 97 * hash + Objects.hashCode(this.estadoStockDespuesContar);
        hash = 97 * hash + Objects.hashCode(this.tipoUa);
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
        final ImpLineasAlbEntradas other = (ImpLineasAlbEntradas) obj;
        if (this.id != other.id) {
            return false;
        }
        if (Double.doubleToLongBits(this.cantidad) != Double.doubleToLongBits(other.cantidad)) {
            return false;
        }
        if (this.idAlb != other.idAlb) {
            return false;
        }
        if (this.version != other.version) {
            return false;
        }
        if (this.autoconfirmada != other.autoconfirmada) {
            return false;
        }
        if (this.controlCalidad != other.controlCalidad) {
            return false;
        }
        if (this.toleranciaConteo != other.toleranciaConteo) {
            return false;
        }
        if (this.ignoraCapacidad != other.ignoraCapacidad) {
            return false;
        }
        if (this.ignoraMaximos != other.ignoraMaximos) {
            return false;
        }
        if (this.conteoUnitario != other.conteoUnitario) {
            return false;
        }
        if (!Objects.equals(this.albaran, other.albaran)) {
            return false;
        }
        if (!Objects.equals(this.linea, other.linea)) {
            return false;
        }
        if (!Objects.equals(this.articulo, other.articulo)) {
            return false;
        }
        if (!Objects.equals(this.lote, other.lote)) {
            return false;
        }
        if (!Objects.equals(this.formato, other.formato)) {
            return false;
        }
        if (!Objects.equals(this.ubicacion, other.ubicacion)) {
            return false;
        }
        if (!Objects.equals(this.tipoFlujo, other.tipoFlujo)) {
            return false;
        }
        if (!Objects.equals(this.tipoAlmacen, other.tipoAlmacen)) {
            return false;
        }
        if (!Objects.equals(this.tratado, other.tratado)) {
            return false;
        }
        if (!Objects.equals(this.error, other.error)) {
            return false;
        }
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        if (!Objects.equals(this.observacion, other.observacion)) {
            return false;
        }
        if (!Objects.equals(this.fechaCaducidad, other.fechaCaducidad)) {
            return false;
        }
        if (!Objects.equals(this.planta, other.planta)) {
            return false;
        }
        if (!Objects.equals(this.seccion, other.seccion)) {
            return false;
        }
        if (!Objects.equals(this.habitacion, other.habitacion)) {
            return false;
        }
        if (!Objects.equals(this.cama, other.cama)) {
            return false;
        }
        if (!Objects.equals(this.paciente, other.paciente)) {
            return false;
        }
        if (!Objects.equals(this.histPaciente, other.histPaciente)) {
            return false;
        }
        if (!Objects.equals(this.obsMegaDosis1, other.obsMegaDosis1)) {
            return false;
        }
        if (!Objects.equals(this.medico, other.medico)) {
            return false;
        }
        if (!Objects.equals(this.obsMegaDosis2, other.obsMegaDosis2)) {
            return false;
        }
        if (!Objects.equals(this.obsMegaDosis3, other.obsMegaDosis3)) {
            return false;
        }
        if (!Objects.equals(this.bcValidacion, other.bcValidacion)) {
            return false;
        }
        if (!Objects.equals(this.posologia, other.posologia)) {
            return false;
        }
        if (!Objects.equals(this.fechaRecepcion, other.fechaRecepcion)) {
            return false;
        }
        if (!Objects.equals(this.tipoStock, other.tipoStock)) {
            return false;
        }
        if (!Objects.equals(this.documento, other.documento)) {
            return false;
        }
        if (!Objects.equals(this.estadoLote, other.estadoLote)) {
            return false;
        }
        if (!Objects.equals(this.ua, other.ua)) {
            return false;
        }
        if (!Objects.equals(this.tipoLote, other.tipoLote)) {
            return false;
        }
        if (!Objects.equals(this.documentoTransporte, other.documentoTransporte)) {
            return false;
        }
        if (!Objects.equals(this.clasificaciones, other.clasificaciones)) {
            return false;
        }
        if (!Objects.equals(this.valoresClasificaciones, other.valoresClasificaciones)) {
            return false;
        }
        if (!Objects.equals(this.reserva, other.reserva)) {
            return false;
        }
        if (!Objects.equals(this.fechaFabricacion, other.fechaFabricacion)) {
            return false;
        }
        if (!Objects.equals(this.identificador, other.identificador)) {
            return false;
        }
        if (!Objects.equals(this.checkListDescarga, other.checkListDescarga)) {
            return false;
        }
        if (!Objects.equals(this.almacenLogico, other.almacenLogico)) {
            return false;
        }
        if (!Objects.equals(this.estadoStockDespuesContar, other.estadoStockDespuesContar)) {
            return false;
        }
        if (!Objects.equals(this.tipoUa, other.tipoUa)) {
            return false;
        }
        if (!Objects.equals(this.bulto, other.bulto)) {
            return false;
        }
        if (!Objects.equals(this.fechaTratado, other.fechaTratado)) {
            return false;
        }
        if (!Objects.equals(this.orden, other.orden)) {
            return false;
        }
        return Objects.equals(this.fechaCreacion, other.fechaCreacion);
    }
}
