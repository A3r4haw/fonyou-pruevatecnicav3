/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author unava
 */
public class SurtimientoInsumoOff_Extend extends SurtimientoInsumo implements Serializable {

    private static final long serialVersionUID = 1L;

   

    private String idSurtimientoInsumo;
    private String idSurtimiento;
    private String idPrescripcionInsumo;
    private Date fechaProgramada;
    private Integer cantidadSolicitada;
    private Date fechaEnviada;
    private String idUsuarioEnviada;
    private Integer cantidadEnviada;
    private Integer cantidadVale;
    private String folioVale;
    private Date fechaRecepcion;
    private String idUsuarioRecepcion;
    private Integer cantidadRecepcion;
    private Date fechaCancela;
    private String idUsuarioCancela;
    private Integer idEstatusSurtimiento;
    private String firmaControlados;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;
    private Integer idEstatusMirth;

    private Integer idTipoJustificante;
    private String notas;


    private String folioSurtimiento;
    private String folioPrescripcion;
    private String claveInstitucional;
    private String idInsumo;
    private Integer dosis;
    private Integer frecuencia;
    private Integer duracion;
    private String indicaciones;
    private String nombreCorto;
    private String nombreLargo;
    private String factorTransformacion;
    private boolean medicamentoActivo;
    private String nombreSustanciaActiva;
    private String nombrePresentacion;
    private Integer cantidadSurtida;
    private String loteSugerido;
    private String motivo;
    private boolean activo;
    private BigDecimal cajasSolicitadas;
    private String idInventario;
    private List<SurtimientoEnviadoOff_Extend> surtimientoEnviadoExtendList;
    private Integer numeroMedicacion;
    
    private boolean requiereJustificante;
    
    private String numeroPaciente;
    private String paciente;
    private String usuarioDispensacion;
    private String lote;
    private Integer cantidadActual;
    private String idEstructura;
    private String idEstructuraPadre;
    private String nombreUsuario;
    private String estructuraPrescripcion;


    @Override
    public String toString() {
        return "SurtimientoInsumo_Extend{" + "folioSurtimiento=" + folioSurtimiento + ", folioPrescripcion=" + folioPrescripcion + ", claveInstitucional=" + claveInstitucional + ", idInsumo=" + idInsumo + ", dosis=" + dosis + ", frecuencia=" + frecuencia + ", duracion=" + duracion + ", indicaciones=" + indicaciones + ", nombreCorto=" + nombreCorto + ", nombreLargo=" + nombreLargo + ", factorTransformacion=" + factorTransformacion + ", medicamentoActivo=" + medicamentoActivo + ", nombreSustanciaActiva=" + nombreSustanciaActiva + ", nombrePresentacion=" + nombrePresentacion + ", cantidadSurtida=" + cantidadSurtida + ", loteSugerido=" + loteSugerido + ", motivo=" + motivo + ", cantidadVale=" + cantidadVale + ", activo=" + activo + ", cajasSolicitadas=" + cajasSolicitadas + ", idInventario=" + idInventario + ", surtimientoEnviadoExtendList=" + surtimientoEnviadoExtendList + ", requiereJustificante=" + requiereJustificante + ", numeroPaciente=" + numeroPaciente + ", Paciente=" + paciente + ", usuarioDispensacion=" + usuarioDispensacion + ", lote=" + lote + ", cantidadActual=" + cantidadActual + ", idEstructura=" + idEstructura + ", idEstructuraPadre=" + idEstructuraPadre + '}';
    }
    @Override
    public String getIdSurtimientoInsumo() {
        return idSurtimientoInsumo;
    }

    @Override
    public void setIdSurtimientoInsumo(String idSurtimientoInsumo) {
        this.idSurtimientoInsumo = idSurtimientoInsumo;
    }

    @Override
    public String getIdSurtimiento() {
        return idSurtimiento;
    }

    @Override
    public void setIdSurtimiento(String idSurtimiento) {
        this.idSurtimiento = idSurtimiento;
    }

    @Override
    public String getIdPrescripcionInsumo() {
        return idPrescripcionInsumo;
    }

    @Override
    public void setIdPrescripcionInsumo(String idPrescripcionInsumo) {
        this.idPrescripcionInsumo = idPrescripcionInsumo;
    }

    @Override
    public Date getFechaProgramada() {
        return fechaProgramada;
    }

    @Override
    public void setFechaProgramada(Date fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
    }

    @Override
    public Integer getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    @Override
    public void setCantidadSolicitada(Integer cantidadSolicitada) {
        this.cantidadSolicitada = cantidadSolicitada;
    }

    @Override
    public Date getFechaEnviada() {
        return fechaEnviada;
    }

    @Override
    public void setFechaEnviada(Date fechaEnviada) {
        this.fechaEnviada = fechaEnviada;
    }

    @Override
    public String getIdUsuarioEnviada() {
        return idUsuarioEnviada;
    }

    @Override
    public void setIdUsuarioEnviada(String idUsuarioEnviada) {
        this.idUsuarioEnviada = idUsuarioEnviada;
    }

    @Override
    public Integer getCantidadEnviada() {
        return cantidadEnviada;
    }

    @Override
    public void setCantidadEnviada(Integer cantidadEnviada) {
        this.cantidadEnviada = cantidadEnviada;
    }

    @Override
    public Integer getCantidadVale() {
        return cantidadVale;
    }

    @Override
    public void setCantidadVale(Integer cantidadVale) {
        this.cantidadVale = cantidadVale;
    }

    @Override
    public String getFolioVale() {
        return folioVale;
    }

    @Override
    public void setFolioVale(String folioVale) {
        this.folioVale = folioVale;
    }

    @Override
    public Date getFechaRecepcion() {
        return fechaRecepcion;
    }

    @Override
    public void setFechaRecepcion(Date fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    @Override
    public String getIdUsuarioRecepcion() {
        return idUsuarioRecepcion;
    }

    @Override
    public void setIdUsuarioRecepcion(String idUsuarioRecepcion) {
        this.idUsuarioRecepcion = idUsuarioRecepcion;
    }

    @Override
    public Integer getCantidadRecepcion() {
        return cantidadRecepcion;
    }

    @Override
    public void setCantidadRecepcion(Integer cantidadRecepcion) {
        this.cantidadRecepcion = cantidadRecepcion;
    }

    @Override
    public Date getFechaCancela() {
        return fechaCancela;
    }

    @Override
    public void setFechaCancela(Date fechaCancela) {
        this.fechaCancela = fechaCancela;
    }

    @Override
    public String getIdUsuarioCancela() {
        return idUsuarioCancela;
    }

    @Override
    public void setIdUsuarioCancela(String idUsuarioCancela) {
        this.idUsuarioCancela = idUsuarioCancela;
    }

    @Override
    public Integer getIdEstatusSurtimiento() {
        return idEstatusSurtimiento;
    }

    @Override
    public void setIdEstatusSurtimiento(Integer idEstatusSurtimiento) {
        this.idEstatusSurtimiento = idEstatusSurtimiento;
    }

    @Override
    public String getFirmaControlados() {
        return firmaControlados;
    }

    @Override
    public void setFirmaControlados(String firmaControlados) {
        this.firmaControlados = firmaControlados;
    }

    @Override
    public Date getInsertFecha() {
        return insertFecha;
    }

    @Override
    public void setInsertFecha(Date insertFecha) {
        this.insertFecha = insertFecha;
    }

    @Override
    public String getInsertIdUsuario() {
        return insertIdUsuario;
    }

    @Override
    public void setInsertIdUsuario(String insertIdUsuario) {
        this.insertIdUsuario = insertIdUsuario;
    }

    @Override
    public Date getUpdateFecha() {
        return updateFecha;
    }

    @Override
    public void setUpdateFecha(Date updateFecha) {
        this.updateFecha = updateFecha;
    }

    @Override
    public String getUpdateIdUsuario() {
        return updateIdUsuario;
    }

    @Override
    public void setUpdateIdUsuario(String updateIdUsuario) {
        this.updateIdUsuario = updateIdUsuario;
    }

    @Override
    public Integer getIdEstatusMirth() {
        return idEstatusMirth;
    }

    @Override
    public void setIdEstatusMirth(Integer idEstatusMirth) {
        this.idEstatusMirth = idEstatusMirth;
    }

    @Override
    public Integer getIdTipoJustificante() {
        return idTipoJustificante;
    }

    @Override
    public void setIdTipoJustificante(Integer idTipoJustificante) {
        this.idTipoJustificante = idTipoJustificante;
    }

    @Override
    public String getNotas() {
        return notas;
    }

    @Override
    public void setNotas(String notas) {
        this.notas = notas;
    }

    @Override
    public Integer getNumeroMedicacion() {
        return numeroMedicacion;
    }

    @Override
    public void setNumeroMedicacion(Integer numeroMedicacion) {
        this.numeroMedicacion = numeroMedicacion;
    }

    public String getFolioSurtimiento() {
        return folioSurtimiento;
    }

    public void setFolioSurtimiento(String folioSurtimiento) {
        this.folioSurtimiento = folioSurtimiento;
    }

    public String getFolioPrescripcion() {
        return folioPrescripcion;
    }

    public void setFolioPrescripcion(String folioPrescripcion) {
        this.folioPrescripcion = folioPrescripcion;
    }

    public String getClaveInstitucional() {
        return claveInstitucional;
    }

    public void setClaveInstitucional(String claveInstitucional) {
        this.claveInstitucional = claveInstitucional;
    }

    public String getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(String idInsumo) {
        this.idInsumo = idInsumo;
    }
    
    

    public Integer getDosis() {
        return dosis;
    }

    public void setDosis(Integer dosis) {
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

    public String getIndicaciones() {
        return indicaciones;
    }

    public void setIndicaciones(String indicaciones) {
        this.indicaciones = indicaciones;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    public String getNombreLargo() {
        return nombreLargo;
    }

    public void setNombreLargo(String nombreLargo) {
        this.nombreLargo = nombreLargo;
    }

    public String getFactorTransformacion() {
        return factorTransformacion;
    }

    public void setFactorTransformacion(String factorTransformacion) {
        this.factorTransformacion = factorTransformacion;
    }

    public boolean isMedicamentoActivo() {
        return medicamentoActivo;
    }

    public void setMedicamentoActivo(boolean medicamentoActivo) {
        this.medicamentoActivo = medicamentoActivo;
    }

    public String getNombreSustanciaActiva() {
        return nombreSustanciaActiva;
    }

    public void setNombreSustanciaActiva(String nombreSustanciaActiva) {
        this.nombreSustanciaActiva = nombreSustanciaActiva;
    }

    public String getNombrePresentacion() {
        return nombrePresentacion;
    }

    public void setNombrePresentacion(String nombrePresentacion) {
        this.nombrePresentacion = nombrePresentacion;
    }

    public Integer getCantidadSurtida() {
        return cantidadSurtida;
    }

    public void setCantidadSurtida(Integer cantidadSurtida) {
        this.cantidadSurtida = cantidadSurtida;
    }

    public String getLoteSugerido() {
        return loteSugerido;
    }

    public void setLoteSugerido(String loteSugerido) {
        this.loteSugerido = loteSugerido;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public List<SurtimientoEnviadoOff_Extend> getSurtimientoEnviadoExtendList() {
        return surtimientoEnviadoExtendList;
    }

    public void setSurtimientoEnviadoExtendList(List<SurtimientoEnviadoOff_Extend> surtimientoEnviadoExtendList) {
        this.surtimientoEnviadoExtendList = surtimientoEnviadoExtendList;
    }
    
    public BigDecimal getCajasSolicitadas() {
        return cajasSolicitadas;
    }

    public void setCajasSolicitadas(BigDecimal cajasSolicitadas) {
        this.cajasSolicitadas = cajasSolicitadas;
    }

    public String getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(String idInventario) {
        this.idInventario = idInventario;
    }

    /**
     * Get the value of requiereJustificante
     *
     * @return the value of requiereJustificante
     */
    public boolean isRequiereJustificante() {
        return requiereJustificante;
    }

    /**
     * Set the value of requiereJustificante
     *
     * @param requiereJustificante new value of requiereJustificante
     */
    public void setRequiereJustificante(boolean requiereJustificante) {
        this.requiereJustificante = requiereJustificante;
    }

    public String getNumeroPaciente() {
        return numeroPaciente;
    }

    public void setNumeroPaciente(String numeroPaciente) {
        this.numeroPaciente = numeroPaciente;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getEstructuraPrescripcion() {
        return estructuraPrescripcion;
    }

    public void setEstructuraPrescripcion(String estructuraPrescripcion) {
        this.estructuraPrescripcion = estructuraPrescripcion;
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public String getUsuarioDispensacion() {
        return usuarioDispensacion;
    }

    public void setUsuarioDispensacion(String usuarioDispensacion) {
        this.usuarioDispensacion = usuarioDispensacion;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public Integer getCantidadActual() {
        return cantidadActual;
    }

    public void setCantidadActual(Integer cantidadActual) {
        this.cantidadActual = cantidadActual;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public String getIdEstructuraPadre() {
        return idEstructuraPadre;
    }

    public void setIdEstructuraPadre(String idEstructuraPadre) {
        this.idEstructuraPadre = idEstructuraPadre;
    }
}
