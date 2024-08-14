package mx.mc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author bbautista
 */
public class Solucion implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idSolucion;
    private String idSurtimiento;
    private Integer idEnvaseContenedor;
    private Integer muestreo;
    private Integer piso;
    private String instruccionesPreparacion;
    private BigDecimal volumen;
    private String observaciones;
    private Date fechaPrepara;
    private String idUsuarioPrepara;
    private String comentariosPreparacion;
    private Date fechaInspeccion;
    private String idUsuarioInspeccion;
    private String comentarioInspeccion;
    private Integer idEstatusSolucion;
    private String volumenFinal;
    private String integridadFisica;
    private boolean particulas;
    private String cuales;
    private String apariencia;
    private Integer proteccionLuz;
    private Integer proteccionTemp;
    private Integer coloracion;
    private Integer etiquetado;
    private Date fechaAcondicionamiento;
    private String idUsuarioAcondiciona;
    private String comentarioAcondicionada;
    private Integer resguardo;
    private String donde;
    private Date fechaEntrega;
    private String idUsuarioRecibe;
    private String idServicioRecibe;
    private String comentarioRecibe;
    private Double costo;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;
    private Integer proteccionTempRefrig;
    private Integer noAgitar;
    private Integer idViaAdministracion;
    private Double edadPaciente;
    private Double pesoPaciente;
    private Double tallaPaciente;
    private Double areaCorporal;
    private Integer perfusionContinua;
    private Double velocidad;
    private Double ritmo;
    private Integer idProtocolo;
    private String idDiagnostico;
    private String unidadConcentracion;
    private String idUsuarioValida;
    private Date fechaValida;
    private String claveMezcla;
    private String loteMezcla;
    private Date caducidadMezcla;
    private Integer estabilidadMezcla;
    private Integer idMotivoRechazo;
    private String motivoCancela;
    private Integer noEtiquetas;
    private String motivoReimpresion;
    private String idUsuarioEntrega;
    private String idUsuarioReimprime;

    private Double caloriasTotales;
    private Double pesoTotal;
    private Double omolairdadTotal;

    private Date fechaValPrescr;
    private String idUsuarioValPrescr;
    private String comentValPrescr;

    private Date fechaValOrdenPrep;
    private String idUsuarioValOrdenPrep;
    private String comentValOrdenPrep;

    private Integer fuga;
    private Integer aire;
    private Integer burbujas;
    private Integer sedimentacion;
    private Integer precipitado;
    private Integer selladoNoCoforme;
    private Integer escalaVolIlegible;
    private Integer separacionFases;
    private String idUsuarioAprobo;
    private String dictamen;
    private Integer aprobado;
    private String reclasificacion;

    private Integer idMotivoCancelacion;
    private String comentariosCancelacion;
    private String comentariosRechazo;

    private String idUsuarioCancela;
    private String idUsuarioRechaza;
    private Date fechaParaEntregar;

    private Integer minutosInfusion;

    private String idUsuarioAutoriza;
    private Date fechaAutoriza;
    private String comentarioAutoriza;    
    private String idUsuarioNoAutoriza;
    private String motivoNoAutoriza;

    private boolean insSanitDisp;
    private boolean insSanitPrep;
    private Date fechaInsSanitDisp;
    private Date fechaInsSanitPrep;
    private String idUsuarioInsSanitDisp;
    private String idUsuarioInsSanitPrep;

    private BigDecimal kcalProteicas;
    private BigDecimal kcalNoProteicas;
    private Integer sobrellenado;
    private String indicaciones;
    private String descripcion;

    public Solucion() {
    }

    public Solucion(String idSolucion) {
        this.idSolucion = idSolucion;
    }

    public String getIdSolucion() {
        return idSolucion;
    }

    public void setIdSolucion(String idSolucion) {
        this.idSolucion = idSolucion;
    }

    public String getIdSurtimiento() {
        return idSurtimiento;
    }

    public void setIdSurtimiento(String idSurtimiento) {
        this.idSurtimiento = idSurtimiento;
    }

    public Integer getIdEnvaseContenedor() {
        return idEnvaseContenedor;
    }

    public void setIdEnvaseContenedor(Integer idEnvaseContenedor) {
        this.idEnvaseContenedor = idEnvaseContenedor;
    }

    public Integer getMuestreo() {
        return muestreo;
    }

    public void setMuestreo(Integer muestreo) {
        this.muestreo = muestreo;
    }

    public Integer getPiso() {
        return piso;
    }

    public void setPiso(Integer piso) {
        this.piso = piso;
    }

    public String getInstruccionesPreparacion() {
        return instruccionesPreparacion;
    }

    public void setInstruccionesPreparacion(String instruccionesPreparacion) {
        this.instruccionesPreparacion = instruccionesPreparacion;
    }

    public BigDecimal getVolumen() {
        return volumen;
    }

    public void setVolumen(BigDecimal volumen) {
        this.volumen = volumen;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Date getFechaPrepara() {
        return fechaPrepara;
    }

    public void setFechaPrepara(Date fechaPrepara) {
        this.fechaPrepara = fechaPrepara;
    }

    public String getIdUsuarioPrepara() {
        return idUsuarioPrepara;
    }

    public void setIdUsuarioPrepara(String idUsuarioPrepara) {
        this.idUsuarioPrepara = idUsuarioPrepara;
    }

    public String getComentariosPreparacion() {
        return comentariosPreparacion;
    }

    public void setComentariosPreparacion(String comentariosPreparacion) {
        this.comentariosPreparacion = comentariosPreparacion;
    }

    public Date getFechaInspeccion() {
        return fechaInspeccion;
    }

    public void setFechaInspeccion(Date fechaInspeccion) {
        this.fechaInspeccion = fechaInspeccion;
    }

    public String getIdUsuarioInspeccion() {
        return idUsuarioInspeccion;
    }

    public void setIdUsuarioInspeccion(String idUsuarioInspeccion) {
        this.idUsuarioInspeccion = idUsuarioInspeccion;
    }

    public String getComentarioInspeccion() {
        return comentarioInspeccion;
    }

    public void setComentarioInspeccion(String comentarioInspeccion) {
        this.comentarioInspeccion = comentarioInspeccion;
    }

    public Integer getIdEstatusSolucion() {
        return idEstatusSolucion;
    }

    public void setIdEstatusSolucion(Integer idEstatusSolucion) {
        this.idEstatusSolucion = idEstatusSolucion;
    }

    public String getVolumenFinal() {
        return volumenFinal;
    }

    public void setVolumenFinal(String volumenFinal) {
        this.volumenFinal = volumenFinal;
    }

    public String getIntegridadFisica() {
        return integridadFisica;
    }

    public void setIntegridadFisica(String integridadFisica) {
        this.integridadFisica = integridadFisica;
    }

    public boolean getParticulas() {
        return particulas;
    }

    public void setParticulas(boolean particulas) {
        this.particulas = particulas;
    }

    public String getCuales() {
        return cuales;
    }

    public void setCuales(String cuales) {
        this.cuales = cuales;
    }

    public String getApariencia() {
        return apariencia;
    }

    public void setApariencia(String apariencia) {
        this.apariencia = apariencia;
    }

    public Integer getProteccionLuz() {
        return proteccionLuz;
    }

    public void setProteccionLuz(Integer proteccionLuz) {
        this.proteccionLuz = proteccionLuz;
    }

    public Integer getProteccionTemp() {
        return proteccionTemp;
    }

    public void setProteccionTemp(Integer proteccionTemp) {
        this.proteccionTemp = proteccionTemp;
    }

    public Integer getColoracion() {
        return coloracion;
    }

    public void setColoracion(Integer coloracion) {
        this.coloracion = coloracion;
    }

    public Integer getEtiquetado() {
        return etiquetado;
    }

    public void setEtiquetado(Integer etiquetado) {
        this.etiquetado = etiquetado;
    }

    public Date getFechaAcondicionamiento() {
        return fechaAcondicionamiento;
    }

    public void setFechaAcondicionamiento(Date fechaAcondicionamiento) {
        this.fechaAcondicionamiento = fechaAcondicionamiento;
    }

    public String getIdUsuarioAcondiciona() {
        return idUsuarioAcondiciona;
    }

    public void setIdUsuarioAcondiciona(String idUsuarioAcondiciona) {
        this.idUsuarioAcondiciona = idUsuarioAcondiciona;
    }

    public String getComentarioAcondicionada() {
        return comentarioAcondicionada;
    }

    public void setComentarioAcondicionada(String comentarioAcondicionada) {
        this.comentarioAcondicionada = comentarioAcondicionada;
    }

    public Integer getResguardo() {
        return resguardo;
    }

    public void setResguardo(Integer resguardo) {
        this.resguardo = resguardo;
    }

    public String getDonde() {
        return donde;
    }

    public void setDonde(String donde) {
        this.donde = donde;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getIdUsuarioRecibe() {
        return idUsuarioRecibe;
    }

    public void setIdUsuarioRecibe(String idUsuarioRecibe) {
        this.idUsuarioRecibe = idUsuarioRecibe;
    }

    public String getIdServicioRecibe() {
        return idServicioRecibe;
    }

    public void setIdServicioRecibe(String idServicioRecibe) {
        this.idServicioRecibe = idServicioRecibe;
    }

    public String getComentarioRecibe() {
        return comentarioRecibe;
    }

    public void setComentarioRecibe(String comentarioRecibe) {
        this.comentarioRecibe = comentarioRecibe;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
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

    public Integer getProteccionTempRefrig() {
        return proteccionTempRefrig;
    }

    public void setProteccionTempRefrig(Integer proteccionTempRefrig) {
        this.proteccionTempRefrig = proteccionTempRefrig;
    }

    public Integer getNoAgitar() {
        return noAgitar;
    }

    public void setNoAgitar(Integer noAgitar) {
        this.noAgitar = noAgitar;
    }

    public Integer getIdViaAdministracion() {
        return idViaAdministracion;
    }

    public void setIdViaAdministracion(Integer idViaAdministracion) {
        this.idViaAdministracion = idViaAdministracion;
    }

    public Double getEdadPaciente() {
        return edadPaciente;
    }

    public void setEdadPaciente(Double edadPaciente) {
        this.edadPaciente = edadPaciente;
    }

    public Double getPesoPaciente() {
        return pesoPaciente;
    }

    public void setPesoPaciente(Double pesoPaciente) {
        this.pesoPaciente = pesoPaciente;
    }

    public Double getTallaPaciente() {
        return tallaPaciente;
    }

    public void setTallaPaciente(Double tallaPaciente) {
        this.tallaPaciente = tallaPaciente;
    }

    public Double getAreaCorporal() {
        return areaCorporal;
    }

    public void setAreaCorporal(Double areaCorporal) {
        this.areaCorporal = areaCorporal;
    }

    public Integer getPerfusionContinua() {
        return perfusionContinua;
    }

    public void setPerfusionContinua(Integer perfusionContinua) {
        this.perfusionContinua = perfusionContinua;
    }

    public Double getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(Double velocidad) {
        this.velocidad = velocidad;
    }

    public Double getRitmo() {
        return ritmo;
    }

    public void setRitmo(Double ritmo) {
        this.ritmo = ritmo;
    }

    public Integer getIdProtocolo() {
        return idProtocolo;
    }

    public void setIdProtocolo(Integer idProtocolo) {
        this.idProtocolo = idProtocolo;
    }

    public String getIdDiagnostico() {
        return idDiagnostico;
    }

    public void setIdDiagnostico(String idDiagnostico) {
        this.idDiagnostico = idDiagnostico;
    }

    public String getUnidadConcentracion() {
        return unidadConcentracion;
    }

    public void setUnidadConcentracion(String unidadConcentracion) {
        this.unidadConcentracion = unidadConcentracion;
    }

    public String getIdUsuarioValida() {
        return idUsuarioValida;
    }

    public void setIdUsuarioValida(String idUsuarioValida) {
        this.idUsuarioValida = idUsuarioValida;
    }

    public Date getFechaValida() {
        return fechaValida;
    }

    public void setFechaValida(Date fechaValida) {
        this.fechaValida = fechaValida;
    }

    public String getClaveMezcla() {
        return claveMezcla;
    }

    public void setClaveMezcla(String claveMezcla) {
        this.claveMezcla = claveMezcla;
    }

    public String getLoteMezcla() {
        return loteMezcla;
    }

    public void setLoteMezcla(String loteMezcla) {
        this.loteMezcla = loteMezcla;
    }

    public Date getCaducidadMezcla() {
        return caducidadMezcla;
    }

    public void setCaducidadMezcla(Date caducidadMezcla) {
        this.caducidadMezcla = caducidadMezcla;
    }

    public Integer getEstabilidadMezcla() {
        return estabilidadMezcla;
    }

    public void setEstabilidadMezcla(Integer estabilidadMezcla) {
        this.estabilidadMezcla = estabilidadMezcla;
    }

    public Integer getIdMotivoRechazo() {
        return idMotivoRechazo;
    }

    public void setIdMotivoRechazo(Integer idMotivoRechazo) {
        this.idMotivoRechazo = idMotivoRechazo;
    }

    public String getMotivoCancela() {
        return motivoCancela;
    }

    public void setMotivoCancela(String motivoCancela) {
        this.motivoCancela = motivoCancela;
    }

    public Integer getNoEtiquetas() {
        return noEtiquetas;
    }

    public void setNoEtiquetas(Integer noEtiquetas) {
        this.noEtiquetas = noEtiquetas;
    }

    public String getMotivoReimpresion() {
        return motivoReimpresion;
    }

    public void setMotivoReimpresion(String motivoReimpresion) {
        this.motivoReimpresion = motivoReimpresion;
    }

    public String getIdUsuarioEntrega() {
        return idUsuarioEntrega;
    }

    public void setIdUsuarioEntrega(String idUsuarioEntrega) {
        this.idUsuarioEntrega = idUsuarioEntrega;
    }

    public String getIdUsuarioReimprime() {
        return idUsuarioReimprime;
    }

    public void setIdUsuarioReimprime(String idUsuarioReimprime) {
        this.idUsuarioReimprime = idUsuarioReimprime;
    }

    public Double getCaloriasTotales() {
        return caloriasTotales;
    }

    public void setCaloriasTotales(Double caloriasTotales) {
        this.caloriasTotales = caloriasTotales;
    }

    public Double getPesoTotal() {
        return pesoTotal;
    }

    public void setPesoTotal(Double pesoTotal) {
        this.pesoTotal = pesoTotal;
    }

    public Double getOmolairdadTotal() {
        return omolairdadTotal;
    }

    public void setOmolairdadTotal(Double omolairdadTotal) {
        this.omolairdadTotal = omolairdadTotal;
    }

    public Date getFechaValPrescr() {
        return fechaValPrescr;
    }

    public void setFechaValPrescr(Date fechaValPrescr) {
        this.fechaValPrescr = fechaValPrescr;
    }

    public String getIdUsuarioValPrescr() {
        return idUsuarioValPrescr;
    }

    public void setIdUsuarioValPrescr(String idUsuarioValPrescr) {
        this.idUsuarioValPrescr = idUsuarioValPrescr;
    }

    public String getComentValPrescr() {
        return comentValPrescr;
    }

    public void setComentValPrescr(String comentValPrescr) {
        this.comentValPrescr = comentValPrescr;
    }

    public Date getFechaValOrdenPrep() {
        return fechaValOrdenPrep;
    }

    public void setFechaValOrdenPrep(Date fechaValOrdenPrep) {
        this.fechaValOrdenPrep = fechaValOrdenPrep;
    }

    public String getIdUsuarioValOrdenPrep() {
        return idUsuarioValOrdenPrep;
    }

    public void setIdUsuarioValOrdenPrep(String idUsuarioValOrdenPrep) {
        this.idUsuarioValOrdenPrep = idUsuarioValOrdenPrep;
    }

    public String getComentValOrdenPrep() {
        return comentValOrdenPrep;
    }

    public void setComentValOrdenPrep(String comentValOrdenPrep) {
        this.comentValOrdenPrep = comentValOrdenPrep;
    }

    public Integer getFuga() {
        return fuga;
    }

    public void setFuga(Integer fuga) {
        this.fuga = fuga;
    }

    public Integer getAire() {
        return aire;
    }

    public void setAire(Integer aire) {
        this.aire = aire;
    }

    public Integer getBurbujas() {
        return burbujas;
    }

    public void setBurbujas(Integer burbujas) {
        this.burbujas = burbujas;
    }

    public Integer getSedimentacion() {
        return sedimentacion;
    }

    public void setSedimentacion(Integer sedimentacion) {
        this.sedimentacion = sedimentacion;
    }

    public Integer getPrecipitado() {
        return precipitado;
    }

    public void setPrecipitado(Integer precipitado) {
        this.precipitado = precipitado;
    }

    public Integer getSelladoNoCoforme() {
        return selladoNoCoforme;
    }

    public void setSelladoNoCoforme(Integer selladoNoCoforme) {
        this.selladoNoCoforme = selladoNoCoforme;
    }

    public Integer getEscalaVolIlegible() {
        return escalaVolIlegible;
    }

    public void setEscalaVolIlegible(Integer escalaVolIlegible) {
        this.escalaVolIlegible = escalaVolIlegible;
    }

    public Integer getSeparacionFases() {
        return separacionFases;
    }

    public void setSeparacionFases(Integer separacionFases) {
        this.separacionFases = separacionFases;
    }

    public String getIdUsuarioAprobo() {
        return idUsuarioAprobo;
    }

    public void setIdUsuarioAprobo(String idUsuarioAprobo) {
        this.idUsuarioAprobo = idUsuarioAprobo;
    }

    public String getDictamen() {
        return dictamen;
    }

    public void setDictamen(String dictamen) {
        this.dictamen = dictamen;
    }

    public Integer getAprobado() {
        return aprobado;
    }

    public void setAprobado(Integer aprobado) {
        this.aprobado = aprobado;
    }

    public String getReclasificacion() {
        return reclasificacion;
    }

    public void setReclasificacion(String reclasificacion) {
        this.reclasificacion = reclasificacion;
    }

    public Integer getIdMotivoCancelacion() {
        return idMotivoCancelacion;
    }

    public void setIdMotivoCancelacion(Integer idMotivoCancelacion) {
        this.idMotivoCancelacion = idMotivoCancelacion;
    }

    public String getComentariosCancelacion() {
        return comentariosCancelacion;
    }

    public void setComentariosCancelacion(String comentariosCancelacion) {
        this.comentariosCancelacion = comentariosCancelacion;
    }

    public String getComentariosRechazo() {
        return comentariosRechazo;
    }

    public void setComentariosRechazo(String comentariosRechazo) {
        this.comentariosRechazo = comentariosRechazo;
    }

    public String getIdUsuarioCancela() {
        return idUsuarioCancela;
    }

    public void setIdUsuarioCancela(String idUsuarioCancela) {
        this.idUsuarioCancela = idUsuarioCancela;
    }

    public String getIdUsuarioRechaza() {
        return idUsuarioRechaza;
    }

    public void setIdUsuarioRechaza(String idUsuarioRechaza) {
        this.idUsuarioRechaza = idUsuarioRechaza;
    }

    public Date getFechaParaEntregar() {
        return fechaParaEntregar;
    }

    public void setFechaParaEntregar(Date fechaParaEntregar) {
        this.fechaParaEntregar = fechaParaEntregar;
    }

    public Integer getMinutosInfusion() {
        return minutosInfusion;
    }

    public void setMinutosInfusion(Integer minutosInfusion) {
        this.minutosInfusion = minutosInfusion;
    }

    public String getIdUsuarioAutoriza() {
        return idUsuarioAutoriza;
    }

    public void setIdUsuarioAutoriza(String idUsuarioAutoriza) {
        this.idUsuarioAutoriza = idUsuarioAutoriza;
    }

    public Date getFechaAutoriza() {
        return fechaAutoriza;
    }

    public void setFechaAutoriza(Date fechaAutoriza) {
        this.fechaAutoriza = fechaAutoriza;
    }

    public String getComentarioAutoriza() {
        return comentarioAutoriza;
    }

    public void setComentarioAutoriza(String comentarioAutoriza) {
        this.comentarioAutoriza = comentarioAutoriza;
    }

    public String getIdUsuarioNoAutoriza() {
        return idUsuarioNoAutoriza;
    }

    public void setIdUsuarioNoAutoriza(String idUsuarioNoAutoriza) {
        this.idUsuarioNoAutoriza = idUsuarioNoAutoriza;
    }

    public String getMotivoNoAutoriza() {
        return motivoNoAutoriza;
    }

    public void setMotivoNoAutoriza(String motivoNoAutoriza) {
        this.motivoNoAutoriza = motivoNoAutoriza;
    }

    public boolean isInsSanitDisp() {
        return insSanitDisp;
    }

    public void setInsSanitDisp(boolean insSanitDisp) {
        this.insSanitDisp = insSanitDisp;
    }

    public boolean isInsSanitPrep() {
        return insSanitPrep;
    }

    public void setInsSanitPrep(boolean insSanitPrep) {
        this.insSanitPrep = insSanitPrep;
    }

    public Date getFechaInsSanitDisp() {
        return fechaInsSanitDisp;
    }

    public void setFechaInsSanitDisp(Date fechaInsSanitDisp) {
        this.fechaInsSanitDisp = fechaInsSanitDisp;
    }

    public Date getFechaInsSanitPrep() {
        return fechaInsSanitPrep;
    }

    public void setFechaInsSanitPrep(Date fechaInsSanitPrep) {
        this.fechaInsSanitPrep = fechaInsSanitPrep;
    }

    public String getIdUsuarioInsSanitDisp() {
        return idUsuarioInsSanitDisp;
    }

    public void setIdUsuarioInsSanitDisp(String idUsuarioInsSanitDisp) {
        this.idUsuarioInsSanitDisp = idUsuarioInsSanitDisp;
    }

    public String getIdUsuarioInsSanitPrep() {
        return idUsuarioInsSanitPrep;
    }

    public void setIdUsuarioInsSanitPrep(String idUsuarioInsSanitPrep) {
        this.idUsuarioInsSanitPrep = idUsuarioInsSanitPrep;
    }

    public BigDecimal getKcalProteicas() {
        return kcalProteicas;
    }

    public void setKcalProteicas(BigDecimal kcalProteicas) {
        this.kcalProteicas = kcalProteicas;
    }

    public BigDecimal getKcalNoProteicas() {
        return kcalNoProteicas;
    }

    public void setKcalNoProteicas(BigDecimal kcalNoProteicas) {
        this.kcalNoProteicas = kcalNoProteicas;
    }

    public Integer getSobrellenado() {
        return sobrellenado;
    }

    public void setSobrellenado(Integer sobrellenado) {
        this.sobrellenado = sobrellenado;
    }

    public String getIndicaciones() {
        return indicaciones;
    }

    public void setIndicaciones(String indicaciones) {
        this.indicaciones = indicaciones;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Solucion{" + "idSolucion=" + idSolucion + ", idSurtimiento=" + idSurtimiento + ", idEnvaseContenedor=" + idEnvaseContenedor + ", muestreo=" + muestreo + ", piso=" + piso + ", instruccionesPreparacion=" + instruccionesPreparacion + ", volumen=" + volumen + ", observaciones=" + observaciones + ", fechaPrepara=" + fechaPrepara + ", idUsuarioPrepara=" + idUsuarioPrepara + ", comentariosPreparacion=" + comentariosPreparacion + ", fechaInspeccion=" + fechaInspeccion + ", idUsuarioInspeccion=" + idUsuarioInspeccion + ", comentarioInspeccion=" + comentarioInspeccion + ", idEstatusSolucion=" + idEstatusSolucion + ", volumenFinal=" + volumenFinal + ", integridadFisica=" + integridadFisica + ", particulas=" + particulas + ", cuales=" + cuales + ", apariencia=" + apariencia + ", proteccionLuz=" + proteccionLuz + ", proteccionTemp=" + proteccionTemp + ", coloracion=" + coloracion + ", etiquetado=" + etiquetado + ", fechaAcondicionamiento=" + fechaAcondicionamiento + ", idUsuarioAcondiciona=" + idUsuarioAcondiciona + ", comentarioAcondicionada=" + comentarioAcondicionada + ", resguardo=" + resguardo + ", donde=" + donde + ", fechaEntrega=" + fechaEntrega + ", idUsuarioRecibe=" + idUsuarioRecibe + ", idServicioRecibe=" + idServicioRecibe + ", comentarioRecibe=" + comentarioRecibe + ", costo=" + costo + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + ", proteccionTempRefrig=" + proteccionTempRefrig + ", noAgitar=" + noAgitar + ", idViaAdministracion=" + idViaAdministracion + ", edadPaciente=" + edadPaciente + ", pesoPaciente=" + pesoPaciente + ", tallaPaciente=" + tallaPaciente + ", areaCorporal=" + areaCorporal + ", perfusionContinua=" + perfusionContinua + ", velocidad=" + velocidad + ", ritmo=" + ritmo + ", idProtocolo=" + idProtocolo + ", idDiagnostico=" + idDiagnostico + ", unidadConcentracion=" + unidadConcentracion + ", idUsuarioValida=" + idUsuarioValida + ", fechaValida=" + fechaValida + ", claveMezcla=" + claveMezcla + ", loteMezcla=" + loteMezcla + ", caducidadMezcla=" + caducidadMezcla + ", estabilidadMezcla=" + estabilidadMezcla + ", idMotivoRechazo=" + idMotivoRechazo + ", motivoCancela=" + motivoCancela + ", noEtiquetas=" + noEtiquetas + ", motivoReimpresion=" + motivoReimpresion + ", idUsuarioEntrega=" + idUsuarioEntrega + ", idUsuarioReimprime=" + idUsuarioReimprime + ", caloriasTotales=" + caloriasTotales + ", pesoTotal=" + pesoTotal + ", omolairdadTotal=" + omolairdadTotal + ", fechaValPrescr=" + fechaValPrescr + ", idUsuarioValPrescr=" + idUsuarioValPrescr + ", comentValPrescr=" + comentValPrescr + ", fechaValOrdenPrep=" + fechaValOrdenPrep + ", idUsuarioValOrdenPrep=" + idUsuarioValOrdenPrep + ", comentValOrdenPrep=" + comentValOrdenPrep + ", fuga=" + fuga + ", aire=" + aire + ", burbujas=" + burbujas + ", sedimentacion=" + sedimentacion + ", precipitado=" + precipitado + ", selladoNoCoforme=" + selladoNoCoforme + ", escalaVolIlegible=" + escalaVolIlegible + ", separacionFases=" + separacionFases + ", idUsuarioAprobo=" + idUsuarioAprobo + ", dictamen=" + dictamen + ", aprobado=" + aprobado + ", reclasificacion=" + reclasificacion + ", idMotivoCancelacion=" + idMotivoCancelacion + ", comentariosCancelacion=" + comentariosCancelacion + ", comentariosRechazo=" + comentariosRechazo + ", idUsuarioCancela=" + idUsuarioCancela + ", idUsuarioRechaza=" + idUsuarioRechaza + ", fechaParaEntregar=" + fechaParaEntregar + ", minutosInfusion=" + minutosInfusion + ", idUsuarioAutoriza=" + idUsuarioAutoriza + ", fechaAutoriza=" + fechaAutoriza + ", comentarioAutoriza=" + comentarioAutoriza + ", idUsuarioNoAutoriza=" + idUsuarioNoAutoriza + ", motivoNoAutoriza=" + motivoNoAutoriza + ", insSanitDisp=" + insSanitDisp + ", insSanitPrep=" + insSanitPrep + ", fechaInsSanitDisp=" + fechaInsSanitDisp + ", fechaInsSanitPrep=" + fechaInsSanitPrep + ", idUsuarioInsSanitDisp=" + idUsuarioInsSanitDisp + ", idUsuarioInsSanitPrep=" + idUsuarioInsSanitPrep + ", kcalProteicas=" + kcalProteicas + ", kcalNoProteicas=" + kcalNoProteicas + ", sobrellenado=" + sobrellenado + ", indicaciones=" + indicaciones + ", descripcion=" + descripcion + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.idSolucion);
        hash = 89 * hash + Objects.hashCode(this.idSurtimiento);
        hash = 89 * hash + Objects.hashCode(this.idEnvaseContenedor);
        hash = 89 * hash + Objects.hashCode(this.muestreo);
        hash = 89 * hash + Objects.hashCode(this.piso);
        hash = 89 * hash + Objects.hashCode(this.instruccionesPreparacion);
        hash = 89 * hash + Objects.hashCode(this.volumen);
        hash = 89 * hash + Objects.hashCode(this.observaciones);
        hash = 89 * hash + Objects.hashCode(this.fechaPrepara);
        hash = 89 * hash + Objects.hashCode(this.idUsuarioPrepara);
        hash = 89 * hash + Objects.hashCode(this.comentariosPreparacion);
        hash = 89 * hash + Objects.hashCode(this.fechaInspeccion);
        hash = 89 * hash + Objects.hashCode(this.idUsuarioInspeccion);
        hash = 89 * hash + Objects.hashCode(this.comentarioInspeccion);
        hash = 89 * hash + Objects.hashCode(this.idEstatusSolucion);
        hash = 89 * hash + Objects.hashCode(this.volumenFinal);
        hash = 89 * hash + Objects.hashCode(this.integridadFisica);
        hash = 89 * hash + Objects.hashCode(this.particulas);
        hash = 89 * hash + Objects.hashCode(this.cuales);
        hash = 89 * hash + Objects.hashCode(this.apariencia);
        hash = 89 * hash + Objects.hashCode(this.proteccionLuz);
        hash = 89 * hash + Objects.hashCode(this.proteccionTemp);
        hash = 89 * hash + Objects.hashCode(this.coloracion);
        hash = 89 * hash + Objects.hashCode(this.etiquetado);
        hash = 89 * hash + Objects.hashCode(this.fechaAcondicionamiento);
        hash = 89 * hash + Objects.hashCode(this.idUsuarioAcondiciona);
        hash = 89 * hash + Objects.hashCode(this.comentarioAcondicionada);
        hash = 89 * hash + Objects.hashCode(this.resguardo);
        hash = 89 * hash + Objects.hashCode(this.donde);
        hash = 89 * hash + Objects.hashCode(this.fechaEntrega);
        hash = 89 * hash + Objects.hashCode(this.idUsuarioRecibe);
        hash = 89 * hash + Objects.hashCode(this.idServicioRecibe);
        hash = 89 * hash + Objects.hashCode(this.comentarioRecibe);
        hash = 89 * hash + Objects.hashCode(this.costo);
        hash = 89 * hash + Objects.hashCode(this.insertFecha);
        hash = 89 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 89 * hash + Objects.hashCode(this.updateFecha);
        hash = 89 * hash + Objects.hashCode(this.updateIdUsuario);
        hash = 89 * hash + Objects.hashCode(this.proteccionTempRefrig);
        hash = 89 * hash + Objects.hashCode(this.noAgitar);
        hash = 89 * hash + Objects.hashCode(this.idViaAdministracion);
        hash = 89 * hash + Objects.hashCode(this.edadPaciente);
        hash = 89 * hash + Objects.hashCode(this.pesoPaciente);
        hash = 89 * hash + Objects.hashCode(this.tallaPaciente);
        hash = 89 * hash + Objects.hashCode(this.areaCorporal);
        hash = 89 * hash + Objects.hashCode(this.perfusionContinua);
        hash = 89 * hash + Objects.hashCode(this.velocidad);
        hash = 89 * hash + Objects.hashCode(this.ritmo);
        hash = 89 * hash + Objects.hashCode(this.idProtocolo);
        hash = 89 * hash + Objects.hashCode(this.idDiagnostico);
        hash = 89 * hash + Objects.hashCode(this.unidadConcentracion);
        hash = 89 * hash + Objects.hashCode(this.idUsuarioValida);
        hash = 89 * hash + Objects.hashCode(this.fechaValida);
        hash = 89 * hash + Objects.hashCode(this.claveMezcla);
        hash = 89 * hash + Objects.hashCode(this.loteMezcla);
        hash = 89 * hash + Objects.hashCode(this.caducidadMezcla);
        hash = 89 * hash + Objects.hashCode(this.estabilidadMezcla);
        hash = 89 * hash + Objects.hashCode(this.idMotivoRechazo);
        hash = 89 * hash + Objects.hashCode(this.motivoCancela);
        hash = 89 * hash + Objects.hashCode(this.noEtiquetas);
        hash = 89 * hash + Objects.hashCode(this.motivoReimpresion);
        hash = 89 * hash + Objects.hashCode(this.idUsuarioEntrega);
        hash = 89 * hash + Objects.hashCode(this.idUsuarioReimprime);
        hash = 89 * hash + Objects.hashCode(this.caloriasTotales);
        hash = 89 * hash + Objects.hashCode(this.pesoTotal);
        hash = 89 * hash + Objects.hashCode(this.omolairdadTotal);
        hash = 89 * hash + Objects.hashCode(this.fechaValPrescr);
        hash = 89 * hash + Objects.hashCode(this.idUsuarioValPrescr);
        hash = 89 * hash + Objects.hashCode(this.comentValPrescr);
        hash = 89 * hash + Objects.hashCode(this.fechaValOrdenPrep);
        hash = 89 * hash + Objects.hashCode(this.idUsuarioValOrdenPrep);
        hash = 89 * hash + Objects.hashCode(this.comentValOrdenPrep);
        hash = 89 * hash + Objects.hashCode(this.fuga);
        hash = 89 * hash + Objects.hashCode(this.aire);
        hash = 89 * hash + Objects.hashCode(this.burbujas);
        hash = 89 * hash + Objects.hashCode(this.sedimentacion);
        hash = 89 * hash + Objects.hashCode(this.precipitado);
        hash = 89 * hash + Objects.hashCode(this.selladoNoCoforme);
        hash = 89 * hash + Objects.hashCode(this.escalaVolIlegible);
        hash = 89 * hash + Objects.hashCode(this.separacionFases);
        hash = 89 * hash + Objects.hashCode(this.idUsuarioAprobo);
        hash = 89 * hash + Objects.hashCode(this.dictamen);
        hash = 89 * hash + Objects.hashCode(this.aprobado);
        hash = 89 * hash + Objects.hashCode(this.reclasificacion);
        hash = 89 * hash + Objects.hashCode(this.idMotivoCancelacion);
        hash = 89 * hash + Objects.hashCode(this.comentariosCancelacion);
        hash = 89 * hash + Objects.hashCode(this.comentariosRechazo);
        hash = 89 * hash + Objects.hashCode(this.idUsuarioCancela);
        hash = 89 * hash + Objects.hashCode(this.idUsuarioRechaza);
        hash = 89 * hash + Objects.hashCode(this.fechaParaEntregar);
        hash = 89 * hash + Objects.hashCode(this.minutosInfusion);
        hash = 89 * hash + Objects.hashCode(this.idUsuarioAutoriza);
        hash = 89 * hash + Objects.hashCode(this.fechaAutoriza);
        hash = 89 * hash + Objects.hashCode(this.comentarioAutoriza);
        hash = 89 * hash + Objects.hashCode(this.idUsuarioNoAutoriza);
        hash = 89 * hash + Objects.hashCode(this.motivoNoAutoriza);
        hash = 89 * hash + (this.insSanitDisp ? 1 : 0);
        hash = 89 * hash + (this.insSanitPrep ? 1 : 0);
        hash = 89 * hash + Objects.hashCode(this.fechaInsSanitDisp);
        hash = 89 * hash + Objects.hashCode(this.fechaInsSanitPrep);
        hash = 89 * hash + Objects.hashCode(this.idUsuarioInsSanitDisp);
        hash = 89 * hash + Objects.hashCode(this.idUsuarioInsSanitPrep);
        hash = 89 * hash + Objects.hashCode(this.kcalProteicas);
        hash = 89 * hash + Objects.hashCode(this.kcalNoProteicas);
        hash = 89 * hash + Objects.hashCode(this.sobrellenado);
        hash = 89 * hash + Objects.hashCode(this.indicaciones);
        hash = 89 * hash + Objects.hashCode(this.descripcion);
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
        final Solucion other = (Solucion) obj;
        if (this.insSanitDisp != other.insSanitDisp) {
            return false;
        }
        if (this.insSanitPrep != other.insSanitPrep) {
            return false;
        }
        if (!Objects.equals(this.idSolucion, other.idSolucion)) {
            return false;
        }
        if (!Objects.equals(this.idSurtimiento, other.idSurtimiento)) {
            return false;
        }
        if (!Objects.equals(this.instruccionesPreparacion, other.instruccionesPreparacion)) {
            return false;
        }
        if (!Objects.equals(this.observaciones, other.observaciones)) {
            return false;
        }
        if (!Objects.equals(this.idUsuarioPrepara, other.idUsuarioPrepara)) {
            return false;
        }
        if (!Objects.equals(this.comentariosPreparacion, other.comentariosPreparacion)) {
            return false;
        }
        if (!Objects.equals(this.idUsuarioInspeccion, other.idUsuarioInspeccion)) {
            return false;
        }
        if (!Objects.equals(this.comentarioInspeccion, other.comentarioInspeccion)) {
            return false;
        }
        if (!Objects.equals(this.volumenFinal, other.volumenFinal)) {
            return false;
        }
        if (!Objects.equals(this.integridadFisica, other.integridadFisica)) {
            return false;
        }
        if (!Objects.equals(this.cuales, other.cuales)) {
            return false;
        }
        if (!Objects.equals(this.apariencia, other.apariencia)) {
            return false;
        }
        if (!Objects.equals(this.idUsuarioAcondiciona, other.idUsuarioAcondiciona)) {
            return false;
        }
        if (!Objects.equals(this.comentarioAcondicionada, other.comentarioAcondicionada)) {
            return false;
        }
        if (!Objects.equals(this.donde, other.donde)) {
            return false;
        }
        if (!Objects.equals(this.idUsuarioRecibe, other.idUsuarioRecibe)) {
            return false;
        }
        if (!Objects.equals(this.idServicioRecibe, other.idServicioRecibe)) {
            return false;
        }
        if (!Objects.equals(this.comentarioRecibe, other.comentarioRecibe)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.idDiagnostico, other.idDiagnostico)) {
            return false;
        }
        if (!Objects.equals(this.unidadConcentracion, other.unidadConcentracion)) {
            return false;
        }
        if (!Objects.equals(this.idUsuarioValida, other.idUsuarioValida)) {
            return false;
        }
        if (!Objects.equals(this.claveMezcla, other.claveMezcla)) {
            return false;
        }
        if (!Objects.equals(this.loteMezcla, other.loteMezcla)) {
            return false;
        }
        if (!Objects.equals(this.motivoCancela, other.motivoCancela)) {
            return false;
        }
        if (!Objects.equals(this.motivoReimpresion, other.motivoReimpresion)) {
            return false;
        }
        if (!Objects.equals(this.idUsuarioEntrega, other.idUsuarioEntrega)) {
            return false;
        }
        if (!Objects.equals(this.idUsuarioReimprime, other.idUsuarioReimprime)) {
            return false;
        }
        if (!Objects.equals(this.idUsuarioValPrescr, other.idUsuarioValPrescr)) {
            return false;
        }
        if (!Objects.equals(this.comentValPrescr, other.comentValPrescr)) {
            return false;
        }
        if (!Objects.equals(this.idUsuarioValOrdenPrep, other.idUsuarioValOrdenPrep)) {
            return false;
        }
        if (!Objects.equals(this.comentValOrdenPrep, other.comentValOrdenPrep)) {
            return false;
        }
        if (!Objects.equals(this.idUsuarioAprobo, other.idUsuarioAprobo)) {
            return false;
        }
        if (!Objects.equals(this.dictamen, other.dictamen)) {
            return false;
        }
        if (!Objects.equals(this.reclasificacion, other.reclasificacion)) {
            return false;
        }
        if (!Objects.equals(this.comentariosCancelacion, other.comentariosCancelacion)) {
            return false;
        }
        if (!Objects.equals(this.comentariosRechazo, other.comentariosRechazo)) {
            return false;
        }
        if (!Objects.equals(this.idUsuarioCancela, other.idUsuarioCancela)) {
            return false;
        }
        if (!Objects.equals(this.idUsuarioRechaza, other.idUsuarioRechaza)) {
            return false;
        }
        if (!Objects.equals(this.idUsuarioAutoriza, other.idUsuarioAutoriza)) {
            return false;
        }
        if (!Objects.equals(this.fechaAutoriza, other.fechaAutoriza)) {
            return false;
        }
        if (!Objects.equals(this.comentarioAutoriza, other.comentarioAutoriza)) {
            return false;
        }
        if (!Objects.equals(this.idUsuarioNoAutoriza, other.idUsuarioNoAutoriza)) {
            return false;
        }
        if (!Objects.equals(this.motivoNoAutoriza, other.motivoNoAutoriza)) {
            return false;
        }
        if (!Objects.equals(this.idUsuarioInsSanitDisp, other.idUsuarioInsSanitDisp)) {
            return false;
        }
        if (!Objects.equals(this.idUsuarioInsSanitPrep, other.idUsuarioInsSanitPrep)) {
            return false;
        }
        if (!Objects.equals(this.indicaciones, other.indicaciones)) {
            return false;
        }
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        if (!Objects.equals(this.idEnvaseContenedor, other.idEnvaseContenedor)) {
            return false;
        }
        if (!Objects.equals(this.muestreo, other.muestreo)) {
            return false;
        }
        if (!Objects.equals(this.piso, other.piso)) {
            return false;
        }
        if (!Objects.equals(this.volumen, other.volumen)) {
            return false;
        }
        if (!Objects.equals(this.fechaPrepara, other.fechaPrepara)) {
            return false;
        }
        if (!Objects.equals(this.fechaInspeccion, other.fechaInspeccion)) {
            return false;
        }
        if (!Objects.equals(this.idEstatusSolucion, other.idEstatusSolucion)) {
            return false;
        }
        if (!Objects.equals(this.particulas, other.particulas)) {
            return false;
        }
        if (!Objects.equals(this.proteccionLuz, other.proteccionLuz)) {
            return false;
        }
        if (!Objects.equals(this.proteccionTemp, other.proteccionTemp)) {
            return false;
        }
        if (!Objects.equals(this.coloracion, other.coloracion)) {
            return false;
        }
        if (!Objects.equals(this.etiquetado, other.etiquetado)) {
            return false;
        }
        if (!Objects.equals(this.fechaAcondicionamiento, other.fechaAcondicionamiento)) {
            return false;
        }
        if (!Objects.equals(this.resguardo, other.resguardo)) {
            return false;
        }
        if (!Objects.equals(this.fechaEntrega, other.fechaEntrega)) {
            return false;
        }
        if (!Objects.equals(this.costo, other.costo)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        if (!Objects.equals(this.updateFecha, other.updateFecha)) {
            return false;
        }
        if (!Objects.equals(this.proteccionTempRefrig, other.proteccionTempRefrig)) {
            return false;
        }
        if (!Objects.equals(this.noAgitar, other.noAgitar)) {
            return false;
        }
        if (!Objects.equals(this.idViaAdministracion, other.idViaAdministracion)) {
            return false;
        }
        if (!Objects.equals(this.edadPaciente, other.edadPaciente)) {
            return false;
        }
        if (!Objects.equals(this.pesoPaciente, other.pesoPaciente)) {
            return false;
        }
        if (!Objects.equals(this.tallaPaciente, other.tallaPaciente)) {
            return false;
        }
        if (!Objects.equals(this.areaCorporal, other.areaCorporal)) {
            return false;
        }
        if (!Objects.equals(this.perfusionContinua, other.perfusionContinua)) {
            return false;
        }
        if (!Objects.equals(this.velocidad, other.velocidad)) {
            return false;
        }
        if (!Objects.equals(this.ritmo, other.ritmo)) {
            return false;
        }
        if (!Objects.equals(this.idProtocolo, other.idProtocolo)) {
            return false;
        }
        if (!Objects.equals(this.fechaValida, other.fechaValida)) {
            return false;
        }
        if (!Objects.equals(this.caducidadMezcla, other.caducidadMezcla)) {
            return false;
        }
        if (!Objects.equals(this.estabilidadMezcla, other.estabilidadMezcla)) {
            return false;
        }
        if (!Objects.equals(this.idMotivoRechazo, other.idMotivoRechazo)) {
            return false;
        }
        if (!Objects.equals(this.noEtiquetas, other.noEtiquetas)) {
            return false;
        }
        if (!Objects.equals(this.caloriasTotales, other.caloriasTotales)) {
            return false;
        }
        if (!Objects.equals(this.pesoTotal, other.pesoTotal)) {
            return false;
        }
        if (!Objects.equals(this.omolairdadTotal, other.omolairdadTotal)) {
            return false;
        }
        if (!Objects.equals(this.fechaValPrescr, other.fechaValPrescr)) {
            return false;
        }
        if (!Objects.equals(this.fechaValOrdenPrep, other.fechaValOrdenPrep)) {
            return false;
        }
        if (!Objects.equals(this.fuga, other.fuga)) {
            return false;
        }
        if (!Objects.equals(this.aire, other.aire)) {
            return false;
        }
        if (!Objects.equals(this.burbujas, other.burbujas)) {
            return false;
        }
        if (!Objects.equals(this.sedimentacion, other.sedimentacion)) {
            return false;
        }
        if (!Objects.equals(this.precipitado, other.precipitado)) {
            return false;
        }
        if (!Objects.equals(this.selladoNoCoforme, other.selladoNoCoforme)) {
            return false;
        }
        if (!Objects.equals(this.escalaVolIlegible, other.escalaVolIlegible)) {
            return false;
        }
        if (!Objects.equals(this.separacionFases, other.separacionFases)) {
            return false;
        }
        if (!Objects.equals(this.aprobado, other.aprobado)) {
            return false;
        }
        if (!Objects.equals(this.idMotivoCancelacion, other.idMotivoCancelacion)) {
            return false;
        }
        if (!Objects.equals(this.fechaParaEntregar, other.fechaParaEntregar)) {
            return false;
        }
        if (!Objects.equals(this.minutosInfusion, other.minutosInfusion)) {
            return false;
        }
        if (!Objects.equals(this.fechaInsSanitDisp, other.fechaInsSanitDisp)) {
            return false;
        }
        if (!Objects.equals(this.fechaInsSanitPrep, other.fechaInsSanitPrep)) {
            return false;
        }
        if (!Objects.equals(this.kcalProteicas, other.kcalProteicas)) {
            return false;
        }
        if (!Objects.equals(this.kcalNoProteicas, other.kcalNoProteicas)) {
            return false;
        }
        return Objects.equals(this.sobrellenado, other.sobrellenado);
    }

}
