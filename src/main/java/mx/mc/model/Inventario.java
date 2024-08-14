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
 * @author bbautista
 */
public class Inventario implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idInventario;
    private Date fechaIngreso;
    private String idEstructura;
    private String idInsumo;
    private Integer idPresentacion;
    private String lote;
    private Date fechaCaducidad;
    private Double costo;
    private Double costoUnidosis;
    private String idDictamenMedico;
    private String idProveedor;
    private String accesorios;
    private Integer cantidadActual;
    private Integer existenciaInicial;
    private Integer activo;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;
    private Integer idTipoOrigen;

    private String clave;
    private Integer presentacionComercial;
    private Integer cantidadXCaja;
    private String claveProveedor;
    private Integer enviarAVG;
    private Integer enviooHL7;
    private String nombreEstructura;
    private String fecha;
    private String claveEstructura;
    private String idEstructuraOrigen;
    private String idEstructuraDestino;
    private String nombre;

    private Integer idFabricante;
    private Double osmolaridad;
    private Double densidad;
    private Double calorias;
    private Integer noHorasEstabilidad;
    private String noRegistro;

    public Inventario() {
    }

    public Inventario(String idInventario) {
        this.idInventario = idInventario;
    }

    public Inventario(String idInventario, Date fechaIngreso, String idEstructura, String idInsumo, Integer idPresentacion, String lote, Date fechaCaducidad, Double costo, Double costoUnidosis, String idDictamenMedico, String idProveedor, String accesorios, Integer cantidadActual, Integer existenciaInicial, Integer activo, Date insertFecha, String insertIdUsuario, Date updateFecha, String updateIdUsuario, Integer idTipoOrigen, String clave, Integer presentacionComercial, Integer cantidadXCaja, String claveProveedor, Integer enviarAVG, Integer enviooHL7, String nombreEstructura, String fecha, String claveEstructura, String idEstructuraOrigen, String idEstructuraDestino, String nombre, Integer idFabricante, Double osmolaridad, Double densidad, Double calorias, Integer noHorasEstabilidad, String noRegistro) {
        this.idInventario = idInventario;
        this.fechaIngreso = fechaIngreso;
        this.idEstructura = idEstructura;
        this.idInsumo = idInsumo;
        this.idPresentacion = idPresentacion;
        this.lote = lote;
        this.fechaCaducidad = fechaCaducidad;
        this.costo = costo;
        this.costoUnidosis = costoUnidosis;
        this.idDictamenMedico = idDictamenMedico;
        this.idProveedor = idProveedor;
        this.accesorios = accesorios;
        this.cantidadActual = cantidadActual;
        this.existenciaInicial = existenciaInicial;
        this.activo = activo;
        this.insertFecha = insertFecha;
        this.insertIdUsuario = insertIdUsuario;
        this.updateFecha = updateFecha;
        this.updateIdUsuario = updateIdUsuario;
        this.idTipoOrigen = idTipoOrigen;
        this.clave = clave;
        this.presentacionComercial = presentacionComercial;
        this.cantidadXCaja = cantidadXCaja;
        this.claveProveedor = claveProveedor;
        this.enviarAVG = enviarAVG;
        this.enviooHL7 = enviooHL7;
        this.nombreEstructura = nombreEstructura;
        this.fecha = fecha;
        this.claveEstructura = claveEstructura;
        this.idEstructuraOrigen = idEstructuraOrigen;
        this.idEstructuraDestino = idEstructuraDestino;
        this.nombre = nombre;
        this.idFabricante = idFabricante;
        this.osmolaridad = osmolaridad;
        this.densidad = densidad;
        this.calorias = calorias;
        this.noHorasEstabilidad = noHorasEstabilidad;
        this.noRegistro = noRegistro;
    }

    public String getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(String idInventario) {
        this.idInventario = idInventario;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public String getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(String idInsumo) {
        this.idInsumo = idInsumo;
    }

    public Integer getIdPresentacion() {
        return idPresentacion;
    }

    public void setIdPresentacion(Integer idPresentacion) {
        this.idPresentacion = idPresentacion;
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

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public Double getCostoUnidosis() {
        return costoUnidosis;
    }

    public void setCostoUnidosis(Double costoUnidosis) {
        this.costoUnidosis = costoUnidosis;
    }

    public String getIdDictamenMedico() {
        return idDictamenMedico;
    }

    public void setIdDictamenMedico(String idDictamenMedico) {
        this.idDictamenMedico = idDictamenMedico;
    }

    public String getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(String idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getAccesorios() {
        return accesorios;
    }

    public void setAccesorios(String accesorios) {
        this.accesorios = accesorios;
    }

    public Integer getCantidadActual() {
        return cantidadActual;
    }

    public void setCantidadActual(Integer cantidadActual) {
        this.cantidadActual = cantidadActual;
    }

    public Integer getExistenciaInicial() {
        return existenciaInicial;
    }

    public void setExistenciaInicial(Integer existenciaInicial) {
        this.existenciaInicial = existenciaInicial;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }

    public Date getInsertFecha() {
        return insertFecha;
    }

    public void setInsertFecha(Date insertFecha) {
        this.insertFecha = insertFecha;
    }

    public String getInsertIdUsuario() {
        return insertIdUsuario;
    }

    public void setInsertIdUsuario(String insertIdUsuario) {
        this.insertIdUsuario = insertIdUsuario;
    }

    public Date getUpdateFecha() {
        return updateFecha;
    }

    public void setUpdateFecha(Date updateFecha) {
        this.updateFecha = updateFecha;
    }

    public String getUpdateIdUsuario() {
        return updateIdUsuario;
    }

    public void setUpdateIdUsuario(String updateIdUsuario) {
        this.updateIdUsuario = updateIdUsuario;
    }

    public Integer getIdTipoOrigen() {
        return idTipoOrigen;
    }

    public void setIdTipoOrigen(Integer idTipoOrigen) {
        this.idTipoOrigen = idTipoOrigen;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Integer getPresentacionComercial() {
        return presentacionComercial;
    }

    public void setPresentacionComercial(Integer presentacionComercial) {
        this.presentacionComercial = presentacionComercial;
    }

    public Integer getCantidadXCaja() {
        return cantidadXCaja;
    }

    public void setCantidadXCaja(Integer cantidadXCaja) {
        this.cantidadXCaja = cantidadXCaja;
    }

    public String getClaveProveedor() {
        return claveProveedor;
    }

    public void setClaveProveedor(String claveProveedor) {
        this.claveProveedor = claveProveedor;
    }

    public Integer getEnviarAVG() {
        return enviarAVG;
    }

    public void setEnviarAVG(Integer enviarAVG) {
        this.enviarAVG = enviarAVG;
    }

    public Integer getEnviooHL7() {
        return enviooHL7;
    }

    public void setEnviooHL7(Integer enviooHL7) {
        this.enviooHL7 = enviooHL7;
    }

    public String getNombreEstructura() {
        return nombreEstructura;
    }

    public void setNombreEstructura(String nombreEstructura) {
        this.nombreEstructura = nombreEstructura;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getClaveEstructura() {
        return claveEstructura;
    }

    public void setClaveEstructura(String claveEstructura) {
        this.claveEstructura = claveEstructura;
    }

    public String getIdEstructuraOrigen() {
        return idEstructuraOrigen;
    }

    public void setIdEstructuraOrigen(String idEstructuraOrigen) {
        this.idEstructuraOrigen = idEstructuraOrigen;
    }

    public String getIdEstructuraDestino() {
        return idEstructuraDestino;
    }

    public void setIdEstructuraDestino(String idEstructuraDestino) {
        this.idEstructuraDestino = idEstructuraDestino;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getIdFabricante() {
        return idFabricante;
    }

    public void setIdFabricante(Integer idFabricante) {
        this.idFabricante = idFabricante;
    }

    public Double getOsmolaridad() {
        return osmolaridad;
    }

    public void setOsmolaridad(Double osmolaridad) {
        this.osmolaridad = osmolaridad;
    }

    public Double getDensidad() {
        return densidad;
    }

    public void setDensidad(Double densidad) {
        this.densidad = densidad;
    }

    public Double getCalorias() {
        return calorias;
    }

    public void setCalorias(Double calorias) {
        this.calorias = calorias;
    }

    public Integer getNoHorasEstabilidad() {
        return noHorasEstabilidad;
    }

    public void setNoHorasEstabilidad(Integer noHorasEstabilidad) {
        this.noHorasEstabilidad = noHorasEstabilidad;
    }

    public String getNoRegistro() {
        return noRegistro;
    }

    public void setNoRegistro(String noRegistro) {
        this.noRegistro = noRegistro;
    }
    
    @Override
    public String toString() {
        return "Inventario{" + "idInventario=" + idInventario + ", fechaIngreso=" + fechaIngreso + ", idEstructura=" + idEstructura + ", idInsumo=" + idInsumo + ", idPresentacion=" + idPresentacion + ", lote=" + lote + ", fechaCaducidad=" + fechaCaducidad + ", costo=" + costo + ", costoUnidosis=" + costoUnidosis + ", idDictamenMedico=" + idDictamenMedico + ", idProveedor=" + idProveedor + ", accesorios=" + accesorios + ", cantidadActual=" + cantidadActual + ", existenciaInicial=" + existenciaInicial + ", activo=" + activo + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + ", idTipoOrigen=" + idTipoOrigen + ", clave=" + clave + ", presentacionComercial=" + presentacionComercial + ", cantidadXCaja=" + cantidadXCaja + ", claveProveedor=" + claveProveedor + ", enviarAVG=" + enviarAVG + ", enviooHL7=" + enviooHL7 + ", nombreEstructura=" + nombreEstructura + ", fecha=" + fecha + ", claveEstructura=" + claveEstructura + ", idEstructuraOrigen=" + idEstructuraOrigen + ", idEstructuraDestino=" + idEstructuraDestino + ", nombre=" + nombre + ", idFabricante=" + idFabricante + ", osmolaridad=" + osmolaridad + ", densidad=" + densidad + ", calorias=" + calorias + ", noHorasEstabilidad=" + noHorasEstabilidad + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.idInventario);
        hash = 89 * hash + Objects.hashCode(this.fechaIngreso);
        hash = 89 * hash + Objects.hashCode(this.idEstructura);
        hash = 89 * hash + Objects.hashCode(this.idInsumo);
        hash = 89 * hash + Objects.hashCode(this.idPresentacion);
        hash = 89 * hash + Objects.hashCode(this.lote);
        hash = 89 * hash + Objects.hashCode(this.fechaCaducidad);
        hash = 89 * hash + Objects.hashCode(this.costo);
        hash = 89 * hash + Objects.hashCode(this.costoUnidosis);
        hash = 89 * hash + Objects.hashCode(this.idDictamenMedico);
        hash = 89 * hash + Objects.hashCode(this.idProveedor);
        hash = 89 * hash + Objects.hashCode(this.accesorios);
        hash = 89 * hash + Objects.hashCode(this.cantidadActual);
        hash = 89 * hash + Objects.hashCode(this.existenciaInicial);
        hash = 89 * hash + Objects.hashCode(this.activo);
        hash = 89 * hash + Objects.hashCode(this.insertFecha);
        hash = 89 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 89 * hash + Objects.hashCode(this.updateFecha);
        hash = 89 * hash + Objects.hashCode(this.updateIdUsuario);
        hash = 89 * hash + Objects.hashCode(this.idTipoOrigen);
        hash = 89 * hash + Objects.hashCode(this.clave);
        hash = 89 * hash + Objects.hashCode(this.presentacionComercial);
        hash = 89 * hash + Objects.hashCode(this.cantidadXCaja);
        hash = 89 * hash + Objects.hashCode(this.claveProveedor);
        hash = 89 * hash + Objects.hashCode(this.enviarAVG);
        hash = 89 * hash + Objects.hashCode(this.enviooHL7);
        hash = 89 * hash + Objects.hashCode(this.nombreEstructura);
        hash = 89 * hash + Objects.hashCode(this.fecha);
        hash = 89 * hash + Objects.hashCode(this.claveEstructura);
        hash = 89 * hash + Objects.hashCode(this.idEstructuraOrigen);
        hash = 89 * hash + Objects.hashCode(this.idEstructuraDestino);
        hash = 89 * hash + Objects.hashCode(this.nombre);
        hash = 89 * hash + Objects.hashCode(this.idFabricante);
        hash = 89 * hash + Objects.hashCode(this.osmolaridad);
        hash = 89 * hash + Objects.hashCode(this.densidad);
        hash = 89 * hash + Objects.hashCode(this.calorias);
        hash = 89 * hash + Objects.hashCode(this.noHorasEstabilidad);
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
        final Inventario other = (Inventario) obj;
        if (!Objects.equals(this.idInventario, other.idInventario)) {
            return false;
        }
        if (!Objects.equals(this.idEstructura, other.idEstructura)) {
            return false;
        }
        if (!Objects.equals(this.idInsumo, other.idInsumo)) {
            return false;
        }
        if (!Objects.equals(this.lote, other.lote)) {
            return false;
        }
        if (!Objects.equals(this.idDictamenMedico, other.idDictamenMedico)) {
            return false;
        }
        if (!Objects.equals(this.idProveedor, other.idProveedor)) {
            return false;
        }
        if (!Objects.equals(this.accesorios, other.accesorios)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.clave, other.clave)) {
            return false;
        }
        if (!Objects.equals(this.claveProveedor, other.claveProveedor)) {
            return false;
        }
        if (!Objects.equals(this.nombreEstructura, other.nombreEstructura)) {
            return false;
        }
        if (!Objects.equals(this.fecha, other.fecha)) {
            return false;
        }
        if (!Objects.equals(this.claveEstructura, other.claveEstructura)) {
            return false;
        }
        if (!Objects.equals(this.idEstructuraOrigen, other.idEstructuraOrigen)) {
            return false;
        }
        if (!Objects.equals(this.idEstructuraDestino, other.idEstructuraDestino)) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.idFabricante, other.idFabricante)) {
            return false;
        }
        if (!Objects.equals(this.fechaIngreso, other.fechaIngreso)) {
            return false;
        }
        if (!Objects.equals(this.idPresentacion, other.idPresentacion)) {
            return false;
        }
        if (!Objects.equals(this.fechaCaducidad, other.fechaCaducidad)) {
            return false;
        }
        if (!Objects.equals(this.costo, other.costo)) {
            return false;
        }
        if (!Objects.equals(this.costoUnidosis, other.costoUnidosis)) {
            return false;
        }
        if (!Objects.equals(this.cantidadActual, other.cantidadActual)) {
            return false;
        }
        if (!Objects.equals(this.existenciaInicial, other.existenciaInicial)) {
            return false;
        }
        if (!Objects.equals(this.activo, other.activo)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        if (!Objects.equals(this.updateFecha, other.updateFecha)) {
            return false;
        }
        if (!Objects.equals(this.idTipoOrigen, other.idTipoOrigen)) {
            return false;
        }
        if (!Objects.equals(this.presentacionComercial, other.presentacionComercial)) {
            return false;
        }
        if (!Objects.equals(this.cantidadXCaja, other.cantidadXCaja)) {
            return false;
        }
        if (!Objects.equals(this.enviarAVG, other.enviarAVG)) {
            return false;
        }
        if (!Objects.equals(this.enviooHL7, other.enviooHL7)) {
            return false;
        }
        if (!Objects.equals(this.osmolaridad, other.osmolaridad)) {
            return false;
        }
        if (!Objects.equals(this.densidad, other.densidad)) {
            return false;
        }
        if (!Objects.equals(this.calorias, other.calorias)) {
            return false;
        }
        return Objects.equals(this.noHorasEstabilidad, other.noHorasEstabilidad);
    }

}
