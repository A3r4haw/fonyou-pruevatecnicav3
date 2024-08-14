/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author mcalderon
 */

public class BitacoraMensaje implements Serializable {
    private static final long serialVersionUID = 1L;

    private String idBitacoraMensaje;
    private Date fecha;
    private String tipoMensaje;
    private String entradaSalida;
    private String idMensaje;
    private String mensaje;
    private String codigoRespuesta;

    public BitacoraMensaje() {
    }

    public BitacoraMensaje(String idBitacoraMensaje, Date fecha, String tipoMensaje, String entradaSalida, String idMensaje, String mensaje, String codigoRespuesta) {
        this.idBitacoraMensaje = idBitacoraMensaje;
        this.fecha = fecha;
        this.tipoMensaje = tipoMensaje;
        this.entradaSalida = entradaSalida;
        this.idMensaje = idMensaje;
        this.mensaje = mensaje;
        this.codigoRespuesta = codigoRespuesta;
    }

    @Override
    public String toString() {
        return "BitacoraMensaje{" + "idBitacoraMensaje=" + idBitacoraMensaje + ", fecha=" + fecha + ", tipoMensaje=" + tipoMensaje + ", entradaSalida=" + entradaSalida + ", idMensaje=" + idMensaje + ", mensaje=" + mensaje + ", codigoRespuesta=" + codigoRespuesta + '}';
    }

    public String getIdBitacoraMensaje() {
        return idBitacoraMensaje;
    }

    public void setIdBitacoraMensaje(String idBitacoraMensaje) {
        this.idBitacoraMensaje = idBitacoraMensaje;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTipoMensaje() {
        return tipoMensaje;
    }

    public void setTipoMensaje(String tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }

    public String getEntradaSalida() {
        return entradaSalida;
    }

    public void setEntradaSalida(String entradaSalida) {
        this.entradaSalida = entradaSalida;
    }

    public String getIdMensaje() {
        return idMensaje;
    }

    public void setIdMensaje(String idMensaje) {
        this.idMensaje = idMensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(String codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

}
