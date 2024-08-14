/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.seidor.ulises.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import mx.mc.model.Medicamento_Extended;
import mx.mc.model.Surtimiento_Extend;

/**
 *
 * @author apalacios
 */
public class ImpLineasExpediciones implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private String expedicion;
    private String linea;
    private String articulo;
    private String lote;
    private String formato;
    private double cantidad;
    private Double precio;
    private String ubicacion;
    private int manipulado;
    private String paciente;
    private String cama;
    private String planta;
    private String seccion;
    private String habitacion;
    private String histPaciente;
    private String obsMegaDosis1;
    private String obsMegaDosis2;
    private String obsMegaDosis3;
    private String tratado;
    private String error;
    private Date fechaTratado;
    private long version;
    private String tipoFlujo;
    private String tipoAlmacen;
    private int caducado;
    private Long idExped;
    private String descripcion;
    private String datoExtra1;
    private String observacion;
    private Integer orden;
    private String medico;
    private String bcValidacion;
    private String posologia;
    private String horarioPosologia;
    private Date fechaCreacion;
    private String descripcionTrad;
    private String tipoStock;
    private String clasificaciones;
    private String valoresClasificaciones;
    private String documento;
    private String ua;
    private String tipoLote;
    private int muestra;
    private String sustitutivo;
    private int servirDeCross;
    private String almacenLogico;
    private String estadoLote;
    private int autoConfirmada;
    private Double valorAseguradoUnitario;
    private int lineaNoOptimizable;
    private Integer latenciaEnvio;
    private String reserva;
    private String incoTerm;
    private String tipoKit;

    public ImpLineasExpediciones(Surtimiento_Extend surtimientoExtend, Medicamento_Extended medicamentoExtended, int numMedicamento) {
        this.expedicion = surtimientoExtend.getFolio();
        this.linea = String.valueOf(numMedicamento);
        this.articulo = medicamentoExtended.getClaveInstitucional();
        this.lote = medicamentoExtended.getLote();
        this.formato = null;
        this.cantidad = medicamentoExtended.getCantidadActual();
        this.precio = 0.0;
        this.ubicacion =  null;
        this.manipulado = 0;
        //Ulises tiene un tamaño máximo de 100 caracteres para el nombre del Paciente
        this.paciente = surtimientoExtend.getNombrePaciente().length() < 100 ? surtimientoExtend.getNombrePaciente() : surtimientoExtend.getNombrePaciente().substring(0, 100);
        this.cama = surtimientoExtend.getCama();
        this.planta = null;
        //Ulises tiene un tamaño máximo de 50 caracteres para la sección
        this.seccion = surtimientoExtend.getNombreEstructura().length() < 50 ? surtimientoExtend.getNombreEstructura() : surtimientoExtend.getNombreEstructura().substring(0, 50);
        this.habitacion = null;
        this.histPaciente = null;
        this.obsMegaDosis1 = null; 
        this.obsMegaDosis2 = null;
        this.obsMegaDosis3 = null;
        this.tratado = "N";
        this.error = null;
        this.fechaTratado = null;
        this.version = 1;
        this.tipoFlujo = null;
        this.tipoAlmacen = null;
        this.caducado = 0;
        this.idExped = null;
        this.descripcion = null;
        this.datoExtra1 = null;
        this.observacion = null;
        this.orden = 0;
        //Ulises tiene un tamaño máximo de 100 caracteres para el nombre del Médico
        this.medico = (surtimientoExtend.getNombreMedico() == null || surtimientoExtend.getNombreMedico().length() < 100) ? surtimientoExtend.getNombreMedico() : surtimientoExtend.getNombreMedico().substring(0, 100);
        this.bcValidacion = null;
        //Ulises tiene un tamaño máximo de 20 caracteres para la Posología
        this.posologia = (surtimientoExtend.getComentarios() == null || surtimientoExtend.getComentarios().length() < 20) ? surtimientoExtend.getComentarios() : surtimientoExtend.getComentarios().substring(0, 20);
        this.horarioPosologia = null;
        this.fechaCreacion = new Date();
        this.descripcionTrad = null;
        this.tipoStock = null;
        this.clasificaciones = null;
        this.valoresClasificaciones = null;
        this.documento = surtimientoExtend.getFolioPrescripcion();
        this.ua = null;
        this.tipoLote = null;
        this.muestra = 0;
        this.sustitutivo = null;
        this.servirDeCross = 0;
        this.almacenLogico = null;
        this.estadoLote = null;
        this.autoConfirmada = 0;
        this.valorAseguradoUnitario = 0.0;
        this.lineaNoOptimizable = 0;
        this.latenciaEnvio = 0;
        this.reserva = null;
        this.incoTerm = null;
        this.tipoKit = null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getExpedicion() {
        return expedicion;
    }

    public void setExpedicion(String expedicion) {
        this.expedicion = expedicion;
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

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int getManipulado() {
        return manipulado;
    }

    public void setManipulado(int manipulado) {
        this.manipulado = manipulado;
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public String getCama() {
        return cama;
    }

    public void setCama(String cama) {
        this.cama = cama;
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

    public int getCaducado() {
        return caducado;
    }

    public void setCaducado(int caducado) {
        this.caducado = caducado;
    }

    public Long getIdExped() {
        return idExped;
    }

    public void setIdExped(Long idExped) {
        this.idExped = idExped;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDatoExtra1() {
        return datoExtra1;
    }

    public void setDatoExtra1(String datoExtra1) {
        this.datoExtra1 = datoExtra1;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
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

    public String getHorarioPosologia() {
        return horarioPosologia;
    }

    public void setHorarioPosologia(String horarioPosologia) {
        this.horarioPosologia = horarioPosologia;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getDescripcionTrad() {
        return descripcionTrad;
    }

    public void setDescripcionTrad(String descripcionTrad) {
        this.descripcionTrad = descripcionTrad;
    }

    public String getTipoStock() {
        return tipoStock;
    }

    public void setTipoStock(String tipoStock) {
        this.tipoStock = tipoStock;
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

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
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

    public int getMuestra() {
        return muestra;
    }

    public void setMuestra(int muestra) {
        this.muestra = muestra;
    }

    public String getSustitutivo() {
        return sustitutivo;
    }

    public void setSustitutivo(String sustitutivo) {
        this.sustitutivo = sustitutivo;
    }

    public int getServirDeCross() {
        return servirDeCross;
    }

    public void setServirDeCross(int servirDeCross) {
        this.servirDeCross = servirDeCross;
    }

    public String getAlmacenLogico() {
        return almacenLogico;
    }

    public void setAlmacenLogico(String almacenLogico) {
        this.almacenLogico = almacenLogico;
    }

    public String getEstadoLote() {
        return estadoLote;
    }

    public void setEstadoLote(String estadoLote) {
        this.estadoLote = estadoLote;
    }

    public int getAutoConfirmada() {
        return autoConfirmada;
    }

    public void setAutoConfirmada(int autoConfirmada) {
        this.autoConfirmada = autoConfirmada;
    }

    public Double getValorAseguradoUnitario() {
        return valorAseguradoUnitario;
    }

    public void setValorAseguradoUnitario(Double valorAseguradoUnitario) {
        this.valorAseguradoUnitario = valorAseguradoUnitario;
    }

    public int getLineaNoOptimizable() {
        return lineaNoOptimizable;
    }

    public void setLineaNoOptimizable(int lineaNoOptimizable) {
        this.lineaNoOptimizable = lineaNoOptimizable;
    }

    public Integer getLatenciaEnvio() {
        return latenciaEnvio;
    }

    public void setLatenciaEnvio(Integer latenciaEnvio) {
        this.latenciaEnvio = latenciaEnvio;
    }

    public String getReserva() {
        return reserva;
    }

    public void setReserva(String reserva) {
        this.reserva = reserva;
    }

    public String getIncoTerm() {
        return incoTerm;
    }

    public void setIncoTerm(String incoTerm) {
        this.incoTerm = incoTerm;
    }

    public String getTipoKit() {
        return tipoKit;
    }

    public void setTipoKit(String tipoKit) {
        this.tipoKit = tipoKit;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 41 * hash + Objects.hashCode(this.expedicion);
        hash = 41 * hash + Objects.hashCode(this.linea);
        hash = 41 * hash + Objects.hashCode(this.articulo);
        hash = 41 * hash + Objects.hashCode(this.lote);
        hash = 41 * hash + Objects.hashCode(this.formato);
        hash = 41 * hash + (int) (Double.doubleToLongBits(this.cantidad) ^ (Double.doubleToLongBits(this.cantidad) >>> 32));
        hash = 41 * hash + Objects.hashCode(this.precio);
        hash = 41 * hash + Objects.hashCode(this.ubicacion);
        hash = 41 * hash + this.manipulado;
        hash = 41 * hash + Objects.hashCode(this.paciente);
        hash = 41 * hash + Objects.hashCode(this.cama);
        hash = 41 * hash + Objects.hashCode(this.planta);
        hash = 41 * hash + Objects.hashCode(this.seccion);
        hash = 41 * hash + Objects.hashCode(this.habitacion);
        hash = 41 * hash + Objects.hashCode(this.histPaciente);
        hash = 41 * hash + Objects.hashCode(this.obsMegaDosis1);
        hash = 41 * hash + Objects.hashCode(this.obsMegaDosis2);
        hash = 41 * hash + Objects.hashCode(this.obsMegaDosis3);
        hash = 41 * hash + Objects.hashCode(this.tratado);
        hash = 41 * hash + Objects.hashCode(this.error);
        hash = 41 * hash + Objects.hashCode(this.fechaTratado);
        hash = 41 * hash + (int) (this.version ^ (this.version >>> 32));
        hash = 41 * hash + Objects.hashCode(this.tipoFlujo);
        hash = 41 * hash + Objects.hashCode(this.tipoAlmacen);
        hash = 41 * hash + this.caducado;
        hash = 41 * hash + (int) (this.idExped ^ (this.idExped >>> 32));
        hash = 41 * hash + Objects.hashCode(this.descripcion);
        hash = 41 * hash + Objects.hashCode(this.datoExtra1);
        hash = 41 * hash + Objects.hashCode(this.observacion);
        hash = 41 * hash + Objects.hashCode(this.orden);
        hash = 41 * hash + Objects.hashCode(this.medico);
        hash = 41 * hash + Objects.hashCode(this.bcValidacion);
        hash = 41 * hash + Objects.hashCode(this.posologia);
        hash = 41 * hash + Objects.hashCode(this.horarioPosologia);
        hash = 41 * hash + Objects.hashCode(this.fechaCreacion);
        hash = 41 * hash + Objects.hashCode(this.descripcionTrad);
        hash = 41 * hash + Objects.hashCode(this.tipoStock);
        hash = 41 * hash + Objects.hashCode(this.clasificaciones);
        hash = 41 * hash + Objects.hashCode(this.valoresClasificaciones);
        hash = 41 * hash + Objects.hashCode(this.documento);
        hash = 41 * hash + Objects.hashCode(this.ua);
        hash = 41 * hash + Objects.hashCode(this.tipoLote);
        hash = 41 * hash + this.muestra;
        hash = 41 * hash + Objects.hashCode(this.sustitutivo);
        hash = 41 * hash + this.servirDeCross;
        hash = 41 * hash + Objects.hashCode(this.almacenLogico);
        hash = 41 * hash + Objects.hashCode(this.estadoLote);
        hash = 41 * hash + this.autoConfirmada;
        hash = 41 * hash + Objects.hashCode(this.valorAseguradoUnitario);
        hash = 41 * hash + this.lineaNoOptimizable;
        hash = 41 * hash + Objects.hashCode(this.latenciaEnvio);
        hash = 41 * hash + Objects.hashCode(this.reserva);
        hash = 41 * hash + Objects.hashCode(this.incoTerm);
        hash = 41 * hash + Objects.hashCode(this.tipoKit);
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
        final ImpLineasExpediciones other = (ImpLineasExpediciones) obj;
        if (this.id != other.id) {
            return false;
        }
        if (Double.doubleToLongBits(this.cantidad) != Double.doubleToLongBits(other.cantidad)) {
            return false;
        }
        if (this.manipulado != other.manipulado) {
            return false;
        }
        if (this.version != other.version) {
            return false;
        }
        if (this.caducado != other.caducado) {
            return false;
        }
        if (this.idExped != other.idExped) {
            return false;
        }
        if (this.muestra != other.muestra) {
            return false;
        }
        if (this.servirDeCross != other.servirDeCross) {
            return false;
        }
        if (this.autoConfirmada != other.autoConfirmada) {
            return false;
        }
        if (this.lineaNoOptimizable != other.lineaNoOptimizable) {
            return false;
        }
        if (!Objects.equals(this.expedicion, other.expedicion)) {
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
        if (!Objects.equals(this.paciente, other.paciente)) {
            return false;
        }
        if (!Objects.equals(this.cama, other.cama)) {
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
        if (!Objects.equals(this.histPaciente, other.histPaciente)) {
            return false;
        }
        if (!Objects.equals(this.obsMegaDosis1, other.obsMegaDosis1)) {
            return false;
        }
        if (!Objects.equals(this.obsMegaDosis2, other.obsMegaDosis2)) {
            return false;
        }
        if (!Objects.equals(this.obsMegaDosis3, other.obsMegaDosis3)) {
            return false;
        }
        if (!Objects.equals(this.tratado, other.tratado)) {
            return false;
        }
        if (!Objects.equals(this.error, other.error)) {
            return false;
        }
        if (!Objects.equals(this.tipoFlujo, other.tipoFlujo)) {
            return false;
        }
        if (!Objects.equals(this.tipoAlmacen, other.tipoAlmacen)) {
            return false;
        }
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        if (!Objects.equals(this.datoExtra1, other.datoExtra1)) {
            return false;
        }
        if (!Objects.equals(this.observacion, other.observacion)) {
            return false;
        }
        if (!Objects.equals(this.medico, other.medico)) {
            return false;
        }
        if (!Objects.equals(this.bcValidacion, other.bcValidacion)) {
            return false;
        }
        if (!Objects.equals(this.posologia, other.posologia)) {
            return false;
        }
        if (!Objects.equals(this.horarioPosologia, other.horarioPosologia)) {
            return false;
        }
        if (!Objects.equals(this.descripcionTrad, other.descripcionTrad)) {
            return false;
        }
        if (!Objects.equals(this.tipoStock, other.tipoStock)) {
            return false;
        }
        if (!Objects.equals(this.clasificaciones, other.clasificaciones)) {
            return false;
        }
        if (!Objects.equals(this.valoresClasificaciones, other.valoresClasificaciones)) {
            return false;
        }
        if (!Objects.equals(this.documento, other.documento)) {
            return false;
        }
        if (!Objects.equals(this.ua, other.ua)) {
            return false;
        }
        if (!Objects.equals(this.tipoLote, other.tipoLote)) {
            return false;
        }
        if (!Objects.equals(this.sustitutivo, other.sustitutivo)) {
            return false;
        }
        if (!Objects.equals(this.almacenLogico, other.almacenLogico)) {
            return false;
        }
        if (!Objects.equals(this.estadoLote, other.estadoLote)) {
            return false;
        }
        if (!Objects.equals(this.reserva, other.reserva)) {
            return false;
        }
        if (!Objects.equals(this.incoTerm, other.incoTerm)) {
            return false;
        }
        if (!Objects.equals(this.tipoKit, other.tipoKit)) {
            return false;
        }
        if (!Objects.equals(this.precio, other.precio)) {
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
        if (!Objects.equals(this.valorAseguradoUnitario, other.valorAseguradoUnitario)) {
            return false;
        }
        return Objects.equals(this.latenciaEnvio, other.latenciaEnvio);
    }
}
