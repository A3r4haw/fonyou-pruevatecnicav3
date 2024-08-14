/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.seidor.ulises.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author apalacios
 */
public class ExpLineasExpediciones implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private String expedicion;
    private String linea;
    private String articulo;
    private String lote;
    private String formato;
    private Double cantidadOriginal;
    private Double cantidadConfirmada;
    private String datoExtra1;
    private String datoExtra2;
    private String datoExtra3;
    private String tratado;
    private String error;
    private Date fechaTratado;
    private long version;
    private long idExped;
    private String planta;
    private String seccion;
    private String habitacion;
    private String cama;
    private String paciente;
    private String histPaciente;
    private String medico;
    private Double precio;
    private String tipoStock;
    private String clasificaciones;
    private String valorClasificaciones;
    private String eanValidado;
    private String tipoKit;

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

    public Double getCantidadOriginal() {
        return cantidadOriginal;
    }

    public void setCantidadOriginal(Double cantidadOriginal) {
        this.cantidadOriginal = cantidadOriginal;
    }

    public Double getCantidadConfirmada() {
        return cantidadConfirmada;
    }

    public void setCantidadConfirmada(Double cantidadConfirmada) {
        this.cantidadConfirmada = cantidadConfirmada;
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

    public long getIdExped() {
        return idExped;
    }

    public void setIdExped(long idExped) {
        this.idExped = idExped;
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

    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
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

    public String getValorClasificaciones() {
        return valorClasificaciones;
    }

    public void setValorClasificaciones(String valorClasificaciones) {
        this.valorClasificaciones = valorClasificaciones;
    }

    public String getEanValidado() {
        return eanValidado;
    }

    public void setEanValidado(String eanValidado) {
        this.eanValidado = eanValidado;
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
        hash = 89 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 89 * hash + Objects.hashCode(this.expedicion);
        hash = 89 * hash + Objects.hashCode(this.linea);
        hash = 89 * hash + Objects.hashCode(this.articulo);
        hash = 89 * hash + Objects.hashCode(this.lote);
        hash = 89 * hash + Objects.hashCode(this.formato);
        hash = 89 * hash + Objects.hashCode(this.cantidadOriginal);
        hash = 89 * hash + Objects.hashCode(this.cantidadConfirmada);
        hash = 89 * hash + Objects.hashCode(this.datoExtra1);
        hash = 89 * hash + Objects.hashCode(this.datoExtra2);
        hash = 89 * hash + Objects.hashCode(this.datoExtra3);
        hash = 89 * hash + Objects.hashCode(this.tratado);
        hash = 89 * hash + Objects.hashCode(this.error);
        hash = 89 * hash + Objects.hashCode(this.fechaTratado);
        hash = 89 * hash + (int) (this.version ^ (this.version >>> 32));
        hash = 89 * hash + (int) (this.idExped ^ (this.idExped >>> 32));
        hash = 89 * hash + Objects.hashCode(this.planta);
        hash = 89 * hash + Objects.hashCode(this.seccion);
        hash = 89 * hash + Objects.hashCode(this.habitacion);
        hash = 89 * hash + Objects.hashCode(this.cama);
        hash = 89 * hash + Objects.hashCode(this.paciente);
        hash = 89 * hash + Objects.hashCode(this.histPaciente);
        hash = 89 * hash + Objects.hashCode(this.medico);
        hash = 89 * hash + Objects.hashCode(this.precio);
        hash = 89 * hash + Objects.hashCode(this.tipoStock);
        hash = 89 * hash + Objects.hashCode(this.clasificaciones);
        hash = 89 * hash + Objects.hashCode(this.valorClasificaciones);
        hash = 89 * hash + Objects.hashCode(this.eanValidado);
        hash = 89 * hash + Objects.hashCode(this.tipoKit);
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
        final ExpLineasExpediciones other = (ExpLineasExpediciones) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.version != other.version) {
            return false;
        }
        if (this.idExped != other.idExped) {
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
        if (!Objects.equals(this.medico, other.medico)) {
            return false;
        }
        if (!Objects.equals(this.tipoStock, other.tipoStock)) {
            return false;
        }
        if (!Objects.equals(this.clasificaciones, other.clasificaciones)) {
            return false;
        }
        if (!Objects.equals(this.valorClasificaciones, other.valorClasificaciones)) {
            return false;
        }
        if (!Objects.equals(this.eanValidado, other.eanValidado)) {
            return false;
        }
        if (!Objects.equals(this.tipoKit, other.tipoKit)) {
            return false;
        }
        if (!Objects.equals(this.cantidadOriginal, other.cantidadOriginal)) {
            return false;
        }
        if (!Objects.equals(this.cantidadConfirmada, other.cantidadConfirmada)) {
            return false;
        }
        if (!Objects.equals(this.fechaTratado, other.fechaTratado)) {
            return false;
        }
        return Objects.equals(this.precio, other.precio);
    }
}
