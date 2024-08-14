package mx.mc.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author cervanets
 */
public class Consumo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idSurtimientoEnviado;
    private String idInventario;
    private String idPrescripcion;
    private String idSurtimiento;
    private String idSurtimientoInsumo;
    private String idSolucion;
    private Date fechaPrescripcion;
    private String folioPrescripcion;
    private String estatusPrescripcion;
    private String nombreServicio;
    private String nombreMedico;
    private String nombrePaciente;
    private String claveInstitucional;
    private String nombreCorto;
    private Double concentracion;
    private Double cantPorEnvase;
    private Integer noHorasestabilidad;
    private Double osmolaridad;
    private Double densidad;
    private Double dosis;
    private Integer frecuencia;
    private Integer duracion;
    private String nombreUnidadConcentracion;
    private String folioSurtimiento;
    private Date fechaProgramada;
    private String nombreAlmacen;
    private String estatusSurtimiento;
    private Integer cantidadEnviado;
    private Integer cantidadRecibido;
    private String lote;
    private Date fechaCaducidad;
    private String nombreFabricante;
    private Date fechaValPrescr;
    private String comentValPrescr;
    private String usuarioValidaPresc;
    private Date fechaValida;
    private String comentValOrdenPrep;
    private String usuarioValidaOP;
    private Date fechaEnviada;
    private String usuarioDispensa;
    private Date fechaPrepara;
    private String comentariosPreparacion;
    private String usuarioPrepara;
    private Date fechaInspeccion;
    private String comentarioInspeccion;
    private String usuarioInspecciona;
    private Date fechaAcondicionamiento;
    private String comentarioAcondicionada;
    private String usuarioAcondiciona;
    private Date fechaEntrega;
    private String comentariosEntrega;
    private String usuarioEntrega;
    private String usuarioRecibe;

    public Consumo() {
    }

    public Consumo(String idSurtimientoEnviado) {
        this.idSurtimientoEnviado = idSurtimientoEnviado;
    }

    public String getIdSurtimientoEnviado() {
        return idSurtimientoEnviado;
    }

    public void setIdSurtimientoEnviado(String idSurtimientoEnviado) {
        this.idSurtimientoEnviado = idSurtimientoEnviado;
    }

    public String getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(String idInventario) {
        this.idInventario = idInventario;
    }

    public String getIdPrescripcion() {
        return idPrescripcion;
    }

    public void setIdPrescripcion(String idPrescripcion) {
        this.idPrescripcion = idPrescripcion;
    }

    public String getIdSurtimiento() {
        return idSurtimiento;
    }

    public void setIdSurtimiento(String idSurtimiento) {
        this.idSurtimiento = idSurtimiento;
    }

    public String getIdSurtimientoInsumo() {
        return idSurtimientoInsumo;
    }

    public void setIdSurtimientoInsumo(String idSurtimientoInsumo) {
        this.idSurtimientoInsumo = idSurtimientoInsumo;
    }

    public String getIdSolucion() {
        return idSolucion;
    }

    public void setIdSolucion(String idSolucion) {
        this.idSolucion = idSolucion;
    }

    public Date getFechaPrescripcion() {
        return fechaPrescripcion;
    }

    public void setFechaPrescripcion(Date fechaPrescripcion) {
        this.fechaPrescripcion = fechaPrescripcion;
    }

    public String getFolioPrescripcion() {
        return folioPrescripcion;
    }

    public void setFolioPrescripcion(String folioPrescripcion) {
        this.folioPrescripcion = folioPrescripcion;
    }

    public String getEstatusPrescripcion() {
        return estatusPrescripcion;
    }

    public void setEstatusPrescripcion(String estatusPrescripcion) {
        this.estatusPrescripcion = estatusPrescripcion;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    public String getNombreMedico() {
        return nombreMedico;
    }

    public void setNombreMedico(String nombreMedico) {
        this.nombreMedico = nombreMedico;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
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

    public Double getConcentracion() {
        return concentracion;
    }

    public void setConcentracion(Double concentracion) {
        this.concentracion = concentracion;
    }

    public Double getCantPorEnvase() {
        return cantPorEnvase;
    }

    public void setCantPorEnvase(Double cantPorEnvase) {
        this.cantPorEnvase = cantPorEnvase;
    }

    public Integer getNoHorasestabilidad() {
        return noHorasestabilidad;
    }

    public void setNoHorasestabilidad(Integer noHorasestabilidad) {
        this.noHorasestabilidad = noHorasestabilidad;
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

    public Double getDosis() {
        return dosis;
    }

    public void setDosis(Double dosis) {
        this.dosis = dosis;
    }

    public Integer getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(Integer frecuencia) {
        this.frecuencia = frecuencia;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public String getNombreUnidadConcentracion() {
        return nombreUnidadConcentracion;
    }

    public void setNombreUnidadConcentracion(String nombreUnidadConcentracion) {
        this.nombreUnidadConcentracion = nombreUnidadConcentracion;
    }

    public String getFolioSurtimiento() {
        return folioSurtimiento;
    }

    public void setFolioSurtimiento(String folioSurtimiento) {
        this.folioSurtimiento = folioSurtimiento;
    }

    public Date getFechaProgramada() {
        return fechaProgramada;
    }

    public void setFechaProgramada(Date fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
    }

    public String getNombreAlmacen() {
        return nombreAlmacen;
    }

    public void setNombreAlmacen(String nombreAlmacen) {
        this.nombreAlmacen = nombreAlmacen;
    }

    public String getEstatusSurtimiento() {
        return estatusSurtimiento;
    }

    public void setEstatusSurtimiento(String estatusSurtimiento) {
        this.estatusSurtimiento = estatusSurtimiento;
    }

    public Integer getCantidadEnviado() {
        return cantidadEnviado;
    }

    public void setCantidadEnviado(Integer cantidadEnviado) {
        this.cantidadEnviado = cantidadEnviado;
    }

    public Integer getCantidadRecibido() {
        return cantidadRecibido;
    }

    public void setCantidadRecibido(Integer cantidadRecibido) {
        this.cantidadRecibido = cantidadRecibido;
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

    public String getNombreFabricante() {
        return nombreFabricante;
    }

    public void setNombreFabricante(String nombreFabricante) {
        this.nombreFabricante = nombreFabricante;
    }

    public Date getFechaValPrescr() {
        return fechaValPrescr;
    }

    public void setFechaValPrescr(Date fechaValPrescr) {
        this.fechaValPrescr = fechaValPrescr;
    }

    public String getComentValPrescr() {
        return comentValPrescr;
    }

    public void setComentValPrescr(String comentValPrescr) {
        this.comentValPrescr = comentValPrescr;
    }

    public String getUsuarioValidaPresc() {
        return usuarioValidaPresc;
    }

    public void setUsuarioValidaPresc(String usuarioValidaPresc) {
        this.usuarioValidaPresc = usuarioValidaPresc;
    }

    public Date getFechaValida() {
        return fechaValida;
    }

    public void setFechaValida(Date fechaValida) {
        this.fechaValida = fechaValida;
    }

    public String getComentValOrdenPrep() {
        return comentValOrdenPrep;
    }

    public void setComentValOrdenPrep(String comentValOrdenPrep) {
        this.comentValOrdenPrep = comentValOrdenPrep;
    }

    public String getUsuarioValidaOP() {
        return usuarioValidaOP;
    }

    public void setUsuarioValidaOP(String usuarioValidaOP) {
        this.usuarioValidaOP = usuarioValidaOP;
    }

    public Date getFechaEnviada() {
        return fechaEnviada;
    }

    public void setFechaEnviada(Date fechaEnviada) {
        this.fechaEnviada = fechaEnviada;
    }

    public String getUsuarioDispensa() {
        return usuarioDispensa;
    }

    public void setUsuarioDispensa(String usuarioDispensa) {
        this.usuarioDispensa = usuarioDispensa;
    }

    public Date getFechaPrepara() {
        return fechaPrepara;
    }

    public void setFechaPrepara(Date fechaPrepara) {
        this.fechaPrepara = fechaPrepara;
    }

    public String getComentariosPreparacion() {
        return comentariosPreparacion;
    }

    public void setComentariosPreparacion(String comentariosPreparacion) {
        this.comentariosPreparacion = comentariosPreparacion;
    }

    public String getUsuarioPrepara() {
        return usuarioPrepara;
    }

    public void setUsuarioPrepara(String usuarioPrepara) {
        this.usuarioPrepara = usuarioPrepara;
    }

    public Date getFechaInspeccion() {
        return fechaInspeccion;
    }

    public void setFechaInspeccion(Date fechaInspeccion) {
        this.fechaInspeccion = fechaInspeccion;
    }

    public String getComentarioInspeccion() {
        return comentarioInspeccion;
    }

    public void setComentarioInspeccion(String comentarioInspeccion) {
        this.comentarioInspeccion = comentarioInspeccion;
    }

    public String getUsuarioInspecciona() {
        return usuarioInspecciona;
    }

    public void setUsuarioInspecciona(String usuarioInspecciona) {
        this.usuarioInspecciona = usuarioInspecciona;
    }

    public Date getFechaAcondicionamiento() {
        return fechaAcondicionamiento;
    }

    public void setFechaAcondicionamiento(Date fechaAcondicionamiento) {
        this.fechaAcondicionamiento = fechaAcondicionamiento;
    }

    public String getComentarioAcondicionada() {
        return comentarioAcondicionada;
    }

    public void setComentarioAcondicionada(String comentarioAcondicionada) {
        this.comentarioAcondicionada = comentarioAcondicionada;
    }

    public String getUsuarioAcondiciona() {
        return usuarioAcondiciona;
    }

    public void setUsuarioAcondiciona(String usuarioAcondiciona) {
        this.usuarioAcondiciona = usuarioAcondiciona;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getComentariosEntrega() {
        return comentariosEntrega;
    }

    public void setComentariosEntrega(String comentariosEntrega) {
        this.comentariosEntrega = comentariosEntrega;
    }

    public String getUsuarioEntrega() {
        return usuarioEntrega;
    }

    public void setUsuarioEntrega(String usuarioEntrega) {
        this.usuarioEntrega = usuarioEntrega;
    }

    public String getUsuarioRecibe() {
        return usuarioRecibe;
    }

    public void setUsuarioRecibe(String usuarioRecibe) {
        this.usuarioRecibe = usuarioRecibe;
    }

    @Override
    public String toString() {
        return "Consumo{" + "idSurtimientoEnviado=" + idSurtimientoEnviado + ", idInventario=" + idInventario + ", idPrescripcion=" + idPrescripcion + ", idSurtimiento=" + idSurtimiento + ", idSurtimientoInsumo=" + idSurtimientoInsumo + ", idSolucion=" + idSolucion + ", fechaPrescripcion=" + fechaPrescripcion + ", folioPrescripcion=" + folioPrescripcion + ", estatusPrescripcion=" + estatusPrescripcion + ", nombreServicio=" + nombreServicio + ", nombreMedico=" + nombreMedico + ", nombrePaciente=" + nombrePaciente + ", claveInstitucional=" + claveInstitucional + ", nombreCorto=" + nombreCorto + ", concentracion=" + concentracion + ", cantPorEnvase=" + cantPorEnvase + ", noHorasestabilidad=" + noHorasestabilidad + ", osmolaridad=" + osmolaridad + ", densidad=" + densidad + ", dosis=" + dosis + ", frecuencia=" + frecuencia + ", duracion=" + duracion + ", nombreUnidadConcentracion=" + nombreUnidadConcentracion + ", folioSurtimiento=" + folioSurtimiento + ", fechaProgramada=" + fechaProgramada + ", nombreAlmacen=" + nombreAlmacen + ", estatusSurtimiento=" + estatusSurtimiento + ", cantidadEnviado=" + cantidadEnviado + ", cantidadRecibido=" + cantidadRecibido + ", lote=" + lote + ", fechaCaducidad=" + fechaCaducidad + ", nombreFabricante=" + nombreFabricante + ", fechaValPrescr=" + fechaValPrescr + ", comentValPrescr=" + comentValPrescr + ", usuarioValidaPresc=" + usuarioValidaPresc + ", fechaValida=" + fechaValida + ", comentValOrdenPrep=" + comentValOrdenPrep + ", usuarioValidaOP=" + usuarioValidaOP + ", fechaEnviada=" + fechaEnviada + ", usuarioDispensa=" + usuarioDispensa + ", fechaPrepara=" + fechaPrepara + ", comentariosPreparacion=" + comentariosPreparacion + ", usuarioPrepara=" + usuarioPrepara + ", fechaInspeccion=" + fechaInspeccion + ", comentarioInspeccion=" + comentarioInspeccion + ", usuarioInspecciona=" + usuarioInspecciona + ", fechaAcondicionamiento=" + fechaAcondicionamiento + ", comentarioAcondicionada=" + comentarioAcondicionada + ", usuarioAcondiciona=" + usuarioAcondiciona + ", fechaEntrega=" + fechaEntrega + ", comentariosEntrega=" + comentariosEntrega + ", usuarioEntrega=" + usuarioEntrega + ", usuarioRecibe=" + usuarioRecibe + '}';
    }

}
