package mx.mc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlTransient;
import mx.mc.util.CodigoBarras;

/**
 * @author AORTIZ
 */
public class Medicamento_Extended extends Medicamento {

    private static final long serialVersionUID = 1L;

    private Integer cantidadActual;
    private Integer cantidadComprometida;
    private Integer estatusMinistracion;
    private Date fechaProgramado;
    private String idMinistrado;
    private String lote;
    private Date fechaCaducidad;
    private String idInventario;
    private String folioPrescripcion;
    private String claveEstructura;
    private String nombreUnidadConcentracion;
    private String nombreSustanciaActiva;
    private Integer cantidadMinistrada;
    private String indicaciones;
    private Double dosis;
    private Integer frecuencia;
    private Integer duracion;
    private String nombrePresentacion;
    private Integer idMotivoNoMinistrado;
    private String idTipoSolucion;
    private boolean nuevaBusqueda;
    private String idEstructura;
    private List<Medicamento> listInsumos;
    private String tipoInsumo;
    private Integer cantidadReservada;
    private Integer cantidadAutorizada;
    private BigDecimal precio;
    private Double cantidadDisponible;
    private BigDecimal importe;
    private String nombreAlmacen;
    private Integer partida;
    private String nombreEntidad;
    private String domicilio;
    private ParamBusquedaReporte paramBusquedaReporte;
    private String nombreEstructura;
    private String codigoBarras;

    private String nombreCategoria;
    private String presentacionEntrada;
    private String presentacionSalida;
    private String subcategoria;
    private String viaAdministracion;
    private String idPrescripcion;
    private String idPaciente;
    private String pacienteNumero;
    private String cedProfesional;
    private String idVisita;
    private Integer cantRequerida;
    private Integer remanente;
    private Integer numPiezas;

    private String nombreUsuarioRegistra;
    private String nombreUsuarioModifica;
    private String nombreUnidadMedida;
    private String idEstructuraAlmacenSurte;
    private String idFabricante;
    private String nombreFabricante;
    private Integer noHorasestabilidad;
    private Integer hrsEstabilidad;
    private String unidadPorEnvase;
    private Date ultimaFechaUso;
    private Double dosisSobrante;

    public Medicamento_Extended() {
        //No code needed in constructor
    }

    public Integer getCantidadActual() {
        return cantidadActual;
    }

    public void setCantidadActual(Integer cantidadActual) {
        this.cantidadActual = cantidadActual;
    }

    public Integer getCantidadComprometida() {
        return cantidadComprometida;
    }

    public void setCantidadComprometida(Integer cantidadComprometida) {
        this.cantidadComprometida = cantidadComprometida;
    }

    public Integer getEstatusMinistracion() {
        return estatusMinistracion;
    }

    public void setEstatusMinistracion(Integer estatusMinistracion) {
        this.estatusMinistracion = estatusMinistracion;
    }

    public Date getFechaProgramado() {
        return fechaProgramado;
    }

    public void setFechaProgramado(Date fechaProgramado) {
        this.fechaProgramado = fechaProgramado;
    }

    public String getIdMinistrado() {
        return idMinistrado;
    }

    public void setIdMinistrado(String idMinistrado) {
        this.idMinistrado = idMinistrado;
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

    public String getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(String idInventario) {
        this.idInventario = idInventario;
    }

    public String getFolioPrescripcion() {
        return folioPrescripcion;
    }

    public void setFolioPrescripcion(String folioPrescripcion) {
        this.folioPrescripcion = folioPrescripcion;
    }

    public String getClaveEstructura() {
        return claveEstructura;
    }

    public void setClaveEstructura(String claveEstructura) {
        this.claveEstructura = claveEstructura;
    }

    public String getNombreUnidadConcentracion() {
        return nombreUnidadConcentracion;
    }

    public void setNombreUnidadConcentracion(String nombreUnidadConcentracion) {
        this.nombreUnidadConcentracion = nombreUnidadConcentracion;
    }

    public String getNombreSustanciaActiva() {
        return nombreSustanciaActiva;
    }

    public void setNombreSustanciaActiva(String nombreSustanciaActiva) {
        this.nombreSustanciaActiva = nombreSustanciaActiva;
    }

    public Integer getCantidadMinistrada() {
        return cantidadMinistrada;
    }

    public void setCantidadMinistrada(Integer cantidadMinistrada) {
        this.cantidadMinistrada = cantidadMinistrada;
    }

    public String getIndicaciones() {
        return indicaciones;
    }

    public void setIndicaciones(String indicaciones) {
        this.indicaciones = indicaciones;
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

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getNombrePresentacion() {
        return nombrePresentacion;
    }

    public void setNombrePresentacion(String nombrePresentacion) {
        this.nombrePresentacion = nombrePresentacion;
    }

    public Integer getIdMotivoNoMinistrado() {
        return idMotivoNoMinistrado;
    }

    public void setIdMotivoNoMinistrado(Integer idMotivoNoMinistrado) {
        this.idMotivoNoMinistrado = idMotivoNoMinistrado;
    }

    public String getIdTipoSolucion() {
        return idTipoSolucion;
    }

    public void setIdTipoSolucion(String idTipoSolucion) {
        this.idTipoSolucion = idTipoSolucion;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public String getPresentacionEntrada() {
        return presentacionEntrada;
    }

    public void setPresentacionEntrada(String presentacionEntrada) {
        this.presentacionEntrada = presentacionEntrada;
    }

    public String getPresentacionSalida() {
        return presentacionSalida;
    }

    public void setPresentacionSalida(String presentacionSalida) {
        this.presentacionSalida = presentacionSalida;
    }

    public String getSubcategoria() {
        return subcategoria;
    }

    public void setSubcategoria(String subcategoria) {
        this.subcategoria = subcategoria;
    }

    public String getViaAdministracion() {
        return viaAdministracion;
    }

    public void setViaAdministracion(String viaAdministracion) {
        this.viaAdministracion = viaAdministracion;
    }

    public boolean isNuevaBusqueda() {
        return nuevaBusqueda;
    }

    public void setNuevaBusqueda(boolean nuevaBusqueda) {
        this.nuevaBusqueda = nuevaBusqueda;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public List<Medicamento> getListInsumos() {
        return listInsumos;
    }

    public void setListInsumos(List<Medicamento> listInsumos) {
        this.listInsumos = listInsumos;
    }

    public String getTipoInsumo() {
        return tipoInsumo;
    }

    public void setTipoInsumo(String tipoInsumo) {
        this.tipoInsumo = tipoInsumo;
    }

    public Integer getCantidadReservada() {
        return cantidadReservada;
    }

    public void setCantidadReservada(Integer cantidadReservada) {
        this.cantidadReservada = cantidadReservada;
    }

    public Integer getCantidadAutorizada() {
        return cantidadAutorizada;
    }

    public void setCantidadAutorizada(Integer cantidadAutorizada) {
        this.cantidadAutorizada = cantidadAutorizada;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Double getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(Double cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public String getNombreAlmacen() {
        return nombreAlmacen;
    }

    public void setNombreAlmacen(String nombreAlmacen) {
        this.nombreAlmacen = nombreAlmacen;
    }

    public Integer getPartida() {
        return partida;
    }

    public void setPartida(Integer partida) {
        this.partida = partida;
    }

    public String getNombreEntidad() {
        return nombreEntidad;
    }

    public void setNombreEntidad(String nombreEntidad) {
        this.nombreEntidad = nombreEntidad;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public ParamBusquedaReporte getParamBusquedaReporte() {
        return paramBusquedaReporte;
    }

    public void setParamBusquedaReporte(ParamBusquedaReporte paramBusquedaReporte) {
        this.paramBusquedaReporte = paramBusquedaReporte;
    }

    public String getNombreEstructura() {
        return nombreEstructura;
    }

    public void setNombreEstructura(String nombreEstructura) {
        this.nombreEstructura = nombreEstructura;
    }

    public String getPacienteNumero() {
        return pacienteNumero;
    }

    public void setPacienteNumero(String pacienteNumero) {
        this.pacienteNumero = pacienteNumero;
    }

    public String getCedProfesional() {
        return cedProfesional;
    }

    public void setCedProfesional(String cedProfesional) {
        this.cedProfesional = cedProfesional;
    }

    public String getIdVisita() {
        return idVisita;
    }

    public void setIdVisita(String idVisita) {
        this.idVisita = idVisita;
    }

    public String getIdPrescripcion() {
        return idPrescripcion;
    }

    public void setIdPrescripcion(String idPrescripcion) {
        this.idPrescripcion = idPrescripcion;
    }

    public String getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(String idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Integer getCantRequerida() {
        return cantRequerida;
    }

    public void setCantRequerida(Integer cantRequerida) {
        this.cantRequerida = cantRequerida;
    }

    public Integer getRemanente() {
        return remanente;
    }

    public void setRemanente(Integer remanente) {
        this.remanente = remanente;
    }

    public Integer getNumPiezas() {
        return numPiezas;
    }

    public void setNumPiezas(Integer numPiezas) {
        this.numPiezas = numPiezas;
    }

    @XmlTransient
    @JsonIgnore
    public boolean isSolucion() {
        return idTipoSolucion != null && !idTipoSolucion.isEmpty();
    }

    @XmlTransient
    @JsonIgnore
    public CodigoInsumo getCodigoInsumo() {
        CodigoInsumo codigoInsumo = null;
        if (codigoBarras != null && !codigoBarras.isEmpty()) {
            codigoInsumo = CodigoBarras.parsearCodigoDeBarras(codigoBarras);
        }
        return codigoInsumo;
    }

    public String getNombreUsuarioRegistra() {
        return nombreUsuarioRegistra;
    }

    public void setNombreUsuarioRegistra(String nombreUsuarioRegistra) {
        this.nombreUsuarioRegistra = nombreUsuarioRegistra;
    }

    public String getNombreUsuarioModifica() {
        return nombreUsuarioModifica;
    }

    public void setNombreUsuarioModifica(String nombreUsuarioModifica) {
        this.nombreUsuarioModifica = nombreUsuarioModifica;
    }

    public String getNombreUnidadMedida() {
        return nombreUnidadMedida;
    }

    public void setNombreUnidadMedida(String nombreUnidadMedida) {
        this.nombreUnidadMedida = nombreUnidadMedida;
    }

    public String getIdEstructuraAlmacenSurte() {
        return idEstructuraAlmacenSurte;
    }

    public void setIdEstructuraAlmacenSurte(String idEstructuraAlmacenSurte) {
        this.idEstructuraAlmacenSurte = idEstructuraAlmacenSurte;
    }

    public String getIdFabricante() {
        return idFabricante;
    }

    public void setIdFabricante(String idFabricante) {
        this.idFabricante = idFabricante;
    }

    public String getNombreFabricante() {
        return nombreFabricante;
    }

    public void setNombreFabricante(String nombreFabricante) {
        this.nombreFabricante = nombreFabricante;
    }

    public Integer getNoHorasestabilidad() {
        return noHorasestabilidad;
    }

    public void setNoHorasestabilidad(Integer noHorasestabilidad) {
        this.noHorasestabilidad = noHorasestabilidad;
    }

    public Integer getHrsEstabilidad() {
        return hrsEstabilidad;
    }

    public void setHrsEstabilidad(Integer hrsEstabilidad) {
        this.hrsEstabilidad = hrsEstabilidad;
    }

    public String getUnidadPorEnvase() {
        return unidadPorEnvase;
    }

    public void setUnidadPorEnvase(String unidadPorEnvase) {
        this.unidadPorEnvase = unidadPorEnvase;
    }

    public Date getUltimaFechaUso() {
        return ultimaFechaUso;
    }

    public void setUltimaFechaUso(Date ultimaFechaUso) {
        this.ultimaFechaUso = ultimaFechaUso;
    }

    public Double getDosisSobrante() {
        return dosisSobrante;
    }

    public void setDosisSobrante(Double dosisSobrante) {
        this.dosisSobrante = dosisSobrante;
    }


}
