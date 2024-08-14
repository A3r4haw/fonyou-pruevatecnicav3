package mx.mc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author hramirez
 */
public class SurtimientoInsumo_Extend extends SurtimientoInsumo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String folioSurtimiento;
    private String folioPrescripcion;
    private String claveInstitucional;
    private String idInsumo;
    private BigDecimal dosis;
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
    private Integer cantidadVale;
    private boolean activo;
    private BigDecimal cajasSolicitadas;
    private BigDecimal cajasSurtidas;
    private String idInventario;
    private String idEstructuraAlmacen;
    private String nombreMedico;

    private List<SurtimientoEnviado_Extend> surtimientoEnviadoExtendList;

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
//    private String idPrescripcionInsumo;

    private Date fechaInicio;
    private String concentracion;
    private String unidadConcentracion;
    private boolean indivisible;
    private Date ultimoSurtimiento;

    private String nombreIngrediente;
    private double velocidad;
    private double ritmo;
    private Integer perfusionContinua;
    private Date caducidadAgrupada;
    private Date caducidad;
    private String envase;
    private String nombreProveedor;
    private String nombreSurtio;
    private Integer merma;
    private Integer noHorasestabilidad;
    private Integer tipoInsumo;

    private String nombreFabricante;
    private Integer cantEnvPorConfirmar;

    private boolean incluyeDiluyente;
    private boolean requiereDiluyente;
    private boolean diluyente;
    private boolean fotosensible;
    private boolean tempAmbiente;
    private boolean refrigeracion;
    private Double densidad;
    private Integer noHorasEstabilidadAbierto;
    private boolean mezclaParental;
    private Double cantPorEnvase;
    private Integer idUnidadMedida;
    private Double osmolaridad;
    private boolean calculoMezcla;
    private boolean prescritaPorMedico;
    private String idPrescripcion;

    private Integer idUnidadConcentracion;
    private Integer idEstatusPrescripcion;
    private Integer idTipoIngrediente;
    private boolean remanente;

    @Override
    public String toString() {
        return "SurtimientoInsumo_Extend{" + "folioSurtimiento=" + folioSurtimiento + ", folioPrescripcion=" + folioPrescripcion + ", claveInstitucional=" + claveInstitucional + ", idInsumo=" + idInsumo + ", dosis=" + dosis + ", frecuencia=" + frecuencia + ", duracion=" + duracion + ", indicaciones=" + indicaciones + ", nombreCorto=" + nombreCorto + ", nombreLargo=" + nombreLargo + ", factorTransformacion=" + factorTransformacion + ", medicamentoActivo=" + medicamentoActivo + ", nombreSustanciaActiva=" + nombreSustanciaActiva + ", nombrePresentacion=" + nombrePresentacion + ", cantidadSurtida=" + cantidadSurtida + ", loteSugerido=" + loteSugerido + ", motivo=" + motivo + ", cantidadVale=" + cantidadVale + ", activo=" + activo + ", cajasSolicitadas=" + cajasSolicitadas + ", idInventario=" + idInventario + ", idEstructuraAlmacen=" + idEstructuraAlmacen + ", surtimientoEnviadoExtendList=" + surtimientoEnviadoExtendList + ", requiereJustificante=" + requiereJustificante + ", numeroPaciente=" + numeroPaciente + ", Paciente=" + paciente + ", usuarioDispensacion=" + usuarioDispensacion + ", lote=" + lote + ", cantidadActual=" + cantidadActual + ", idEstructura=" + idEstructura + ", idEstructuraPadre=" + idEstructuraPadre + ", nombreUsuario=" + nombreUsuario + ", estructuraPrescripcion=" + estructuraPrescripcion /*+ ", idPrescripcionInsumo=" + idPrescripcionInsumo */+ ", fechaInicio=" + fechaInicio + ", concentracion=" + concentracion + ", unidadConcentracion=" + unidadConcentracion + ", indivisible=" + indivisible + ", ultimoSurtimiento=" + ultimoSurtimiento + '}';
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

    public BigDecimal getDosis() {
        return dosis;
    }

    public void setDosis(BigDecimal dosis) {
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

    public List<SurtimientoEnviado_Extend> getSurtimientoEnviadoExtendList() {
        return surtimientoEnviadoExtendList;
    }

    public void setSurtimientoEnviadoExtendList(List<SurtimientoEnviado_Extend> surtimientoEnviadoExtendList) {
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

//    @Override
//    public String getIdPrescripcionInsumo() {
//        return idPrescripcionInsumo;
//    }
//
//    @Override
//    public void setIdPrescripcionInsumo(String idPrescripcionInsumo) {
//        this.idPrescripcionInsumo = idPrescripcionInsumo;
//    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getConcentracion() {
        return concentracion;
    }

    public void setConcentracion(String concentracion) {
        this.concentracion = concentracion;
    }

    public String getUnidadConcentracion() {
        return unidadConcentracion;
    }

    public void setUnidadConcentracion(String unidadConcentracion) {
        this.unidadConcentracion = unidadConcentracion;
    }

    public boolean isIndivisible() {
        return indivisible;
    }

    public void setIndivisible(boolean indivisible) {
        this.indivisible = indivisible;
    }

    public Date getUltimoSurtimiento() {
        return ultimoSurtimiento;
    }

    public void setUltimoSurtimiento(Date ultimoSurtimiento) {
        this.ultimoSurtimiento = ultimoSurtimiento;
    }

    public String getIdEstructuraAlmacen() {
        return idEstructuraAlmacen;
    }

    public void setIdEstructuraAlmacen(String idEstructuraAlmacen) {
        this.idEstructuraAlmacen = idEstructuraAlmacen;
    }

    public String getNombreIngrediente() {
        return nombreIngrediente;
    }

    public void setNombreIngrediente(String nombreIngrediente) {
        this.nombreIngrediente = nombreIngrediente;
    }

    public double getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(double velocidad) {
        this.velocidad = velocidad;
    }

    public double getRitmo() {
        return ritmo;
    }

    public void setRitmo(double ritmo) {
        this.ritmo = ritmo;
    }

    public Integer getPerfusionContinua() {
        return perfusionContinua;
    }

    public void setPerfusionContinua(Integer perfusionContinua) {
        this.perfusionContinua = perfusionContinua;
    }

    public Date getCaducidadAgrupada() {
        return caducidadAgrupada;
    }

    public void setCaducidadAgrupada(Date caducidadAgrupada) {
        this.caducidadAgrupada = caducidadAgrupada;
    }

    public BigDecimal getCajasSurtidas() {
        return cajasSurtidas;
    }

    public void setCajasSurtidas(BigDecimal cajasSurtidas) {
        this.cajasSurtidas = cajasSurtidas;
    }

    public Integer getCantidadVale() {
        return cantidadVale;
    }

    public void setCantidadVale(Integer cantidadVale) {
        this.cantidadVale = cantidadVale;
    }

    public String getEnvase() {
        return envase;
    }

    public void setEnvase(String envase) {
        this.envase = envase;
    }

    public Date getCaducidad() {
        return caducidad;
    }

    public void setCaducidad(Date caducidad) {
        this.caducidad = caducidad;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getNombreSurtio() {
        return nombreSurtio;
    }

    public void setNombreSurtio(String nombreSurtio) {
        this.nombreSurtio = nombreSurtio;
    }

    public Integer getMerma() {
        return merma;
    }

    public void setMerma(Integer merma) {
        this.merma = merma;
    }

    public Integer getNoHorasestabilidad() {
        return noHorasestabilidad;
    }

    public void setNoHorasestabilidad(Integer noHorasestabilidad) {
        this.noHorasestabilidad = noHorasestabilidad;
    }

    public String getNombreFabricante() {
        return nombreFabricante;
    }

    public void setNombreFabricante(String nombreFabricante) {
        this.nombreFabricante = nombreFabricante;
    }

    public Integer getTipoInsumo() {
        return tipoInsumo;
    }

    public void setTipoInsumo(Integer tipoInsumo) {
        this.tipoInsumo = tipoInsumo;
    }

    public Integer getCantEnvPorConfirmar() {
        return cantEnvPorConfirmar;
    }

    public void setCantEnvPorConfirmar(Integer cantEnvPorConfirmar) {
        this.cantEnvPorConfirmar = cantEnvPorConfirmar;
    }

    public boolean isIncluyeDiluyente() {
        return incluyeDiluyente;
    }

    public void setIncluyeDiluyente(boolean incluyeDiluyente) {
        this.incluyeDiluyente = incluyeDiluyente;
    }

    public boolean isRequiereDiluyente() {
        return requiereDiluyente;
    }

    public void setRequiereDiluyente(boolean requiereDiluyente) {
        this.requiereDiluyente = requiereDiluyente;
    }

    public boolean isDiluyente() {
        return diluyente;
    }

    public void setDiluyente(boolean diluyente) {
        this.diluyente = diluyente;
    }

    public boolean isFotosensible() {
        return fotosensible;
    }

    public void setFotosensible(boolean fotosensible) {
        this.fotosensible = fotosensible;
    }

    public boolean isTempAmbiente() {
        return tempAmbiente;
    }

    public void setTempAmbiente(boolean tempAmbiente) {
        this.tempAmbiente = tempAmbiente;
    }

    public boolean isRefrigeracion() {
        return refrigeracion;
    }

    public void setRefrigeracion(boolean refrigeracion) {
        this.refrigeracion = refrigeracion;
    }

    public Double getDensidad() {
        return densidad;
    }

    public void setDensidad(Double densidad) {
        this.densidad = densidad;
    }

    public Integer getNoHorasEstabilidadAbierto() {
        return noHorasEstabilidadAbierto;
    }

    public void setNoHorasEstabilidadAbierto(Integer noHorasEstabilidadAbierto) {
        this.noHorasEstabilidadAbierto = noHorasEstabilidadAbierto;
    }

    public boolean isMezclaParental() {
        return mezclaParental;
    }

    public void setMezclaParental(boolean mezclaParental) {
        this.mezclaParental = mezclaParental;
    }

    public Double getCantPorEnvase() {
        return cantPorEnvase;
    }

    public void setCantPorEnvase(Double cantPorEnvase) {
        this.cantPorEnvase = cantPorEnvase;
    }

    public Integer getIdUnidadMedida() {
        return idUnidadMedida;
    }

    public void setIdUnidadMedida(Integer idUnidadMedida) {
        this.idUnidadMedida = idUnidadMedida;
    }

    public Double getOsmolaridad() {
        return osmolaridad;
    }

    public void setOsmolaridad(Double osmolaridad) {
        this.osmolaridad = osmolaridad;
    }

    public boolean isCalculoMezcla() {
        return calculoMezcla;
    }

    public void setCalculoMezcla(boolean calculoMezcla) {
        this.calculoMezcla = calculoMezcla;
    }

    public String getNombreMedico() {
        return nombreMedico;
    }

    public void setNombreMedico(String nombreMedico) {
        this.nombreMedico = nombreMedico;
    }

    public boolean isPrescritaPorMedico() {
        return prescritaPorMedico;
    }

    public void setPrescritaPorMedico(boolean prescritaPorMedico) {
        this.prescritaPorMedico = prescritaPorMedico;
    }

    public String getIdPrescripcion() {
        return idPrescripcion;
    }

    public void setIdPrescripcion(String idPrescripcion) {
        this.idPrescripcion = idPrescripcion;
    }

    public Integer getIdUnidadConcentracion() {
        return idUnidadConcentracion;
    }

    public void setIdUnidadConcentracion(Integer idUnidadConcentracion) {
        this.idUnidadConcentracion = idUnidadConcentracion;
    }

    public Integer getIdEstatusPrescripcion() {
        return idEstatusPrescripcion;
    }

    public void setIdEstatusPrescripcion(Integer idEstatusPrescripcion) {
        this.idEstatusPrescripcion = idEstatusPrescripcion;
    }

    public Integer getIdTipoIngrediente() {
        return idTipoIngrediente;
    }

    public void setIdTipoIngrediente(Integer idTipoIngrediente) {
        this.idTipoIngrediente = idTipoIngrediente;
    }

    public boolean isRemanente() {
        return remanente;
    }

    public void setRemanente(boolean remanente) {
        this.remanente = remanente;
    }
    
}
