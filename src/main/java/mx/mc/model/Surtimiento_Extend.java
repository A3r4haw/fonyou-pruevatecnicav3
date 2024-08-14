package mx.mc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author hramirez
 */
public class Surtimiento_Extend extends Surtimiento implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nombrePaciente;
    private String pacienteNumero;
    private String claveDerechohabiencia;
    private String nombreEstructura;
    private String cama;
    private String nombreMedico;
    private String estatusSurtimiento;
    private String estatusPrescripcion;
    private String folioPrescripcion;
    private String tipoPrescripcion;
    private String idEstructura;
    private String isEstatusSurtimiento;
    private String pacienteSexo;
    private String cedProfesional;
    private String tipoConsulta;
    private String turno;

    private Double velocidad;
    private Double ritmo;
    private boolean perfusionContinua;
    private String nombreIngrediente;
    private String unidadConcentracion;
    private Integer resurtimiento;
    private Date fechaMinistrado;
    private Date fechaInicio;
    private Date fechaTermino;
    private String comentarios;
    private Integer idProtocolo;
    private String tipoSolucion;

    private double pesoPaciente;
    private double edadPaciente;
    private double tallaPaciente;
    private double areaCorporal;

    private String idPaciente;
    private String idVisita;
    private Integer idEstatusSolucion;
    private String estatusSolucion;

    private String instruccionPreparacion;
    private String observaciones;

    private boolean tempRefrigeracion;
    private boolean tempAmbiente;
    private boolean proteccionLuz;
    private boolean noAgitar;
    private Integer idContenedor;
    private String idDiagnostico;
    private BigDecimal volumenTotal;

    private String nombreEnvaseContenedor;
    private String nombreViaAdministracion;
    private String nombreProtoclo;
    private String nombreDiagnostico;
    private String idCama;

    private Date fechaNacimiento;
    private Double volumenPorDrenar;
    private String nombreDiluyente;
    private Double volumenAforo;

    private Integer idUsuarioEntrega;
    private Integer comentariosEntrega;
    private boolean fuga;
    private boolean aire;
    private boolean burbujas;
    private boolean sedimentacion;
    private boolean precipitado;
    private boolean selladoNoCoforme;
    private boolean escalaVolIlegible;
    private boolean separacionFases;
    private String idUsuarioAprobo;
    private Integer dictamen;
    private Integer aprobado;
    private String reclasificacion;
    private Integer idMotivoCancelacion;
    private String comentariosCancelacion;
    private String comentariosRechazo;
    private String idUsuarioCancela;
    private String idUsuarioRechaza;
    private Date fechaParaEntregar;
    private Integer idHorarioParaEntregar;
    private String idTipoSolucion;

    private boolean insSanitDisp;
    private boolean insSanitPrep;
    private Date fechaInsSanitDisp;
    private Date fechaInsSanitPrep;
    private String idUsuarioInsSanitDisp;
    private String idUsuarioInsSanitPrep;

    private String horasInfusion;
    private String prioridad;
    private Integer idViaAdministracion;
    private Integer minutosInfusion;
    private String indicaciones;
    private Date fechaRechazo;
    private String usuarioRechazo;
    private String descripcion;
    private String idUsuarioAutoriza;
    private String usuarioAutoriza;
    private Date fechaAutoriza;
    private String comentarioAutoriza;

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getPacienteNumero() {
        return pacienteNumero;
    }

    public void setPacienteNumero(String pacienteNumero) {
        this.pacienteNumero = pacienteNumero;
    }

    public String getClaveDerechohabiencia() {
        return claveDerechohabiencia;
    }

    public void setClaveDerechohabiencia(String claveDerechohabiencia) {
        this.claveDerechohabiencia = claveDerechohabiencia;
    }

    public String getNombreEstructura() {
        return nombreEstructura;
    }

    public void setNombreEstructura(String nombreEstructura) {
        this.nombreEstructura = nombreEstructura;
    }

    public String getCama() {
        return cama;
    }

    public void setCama(String cama) {
        this.cama = cama;
    }

    public String getNombreMedico() {
        return nombreMedico;
    }

    public void setNombreMedico(String nombreMedico) {
        this.nombreMedico = nombreMedico;
    }

    public String getEstatusSurtimiento() {
        return estatusSurtimiento;
    }

    public void setEstatusSurtimiento(String estatusSurtimiento) {
        this.estatusSurtimiento = estatusSurtimiento;
    }

    public String getEstatusPrescripcion() {
        return estatusPrescripcion;
    }

    public void setEstatusPrescripcion(String estatusPrescripcion) {
        this.estatusPrescripcion = estatusPrescripcion;
    }

    public String getFolioPrescripcion() {
        return folioPrescripcion;
    }

    public void setFolioPrescripcion(String folioPrescripcion) {
        this.folioPrescripcion = folioPrescripcion;
    }

    public String getTipoPrescripcion() {
        return tipoPrescripcion;
    }

    public void setTipoPrescripcion(String tipoPrescripcion) {
        this.tipoPrescripcion = tipoPrescripcion;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public String getIsEstatusSurtimiento() {
        return isEstatusSurtimiento;
    }

    public void setIsEstatusSurtimiento(String isEstatusSurtimiento) {
        this.isEstatusSurtimiento = isEstatusSurtimiento;
    }

    public String getPacienteSexo() {
        return pacienteSexo;
    }

    public void setPacienteSexo(String pacienteSexo) {
        this.pacienteSexo = pacienteSexo;
    }

    public String getCedProfesional() {
        return cedProfesional;
    }

    public void setCedProfesional(String cedProfesional) {
        this.cedProfesional = cedProfesional;
    }

    public String getTipoConsulta() {
        return tipoConsulta;
    }

    public void setTipoConsulta(String tipoConsulta) {
        this.tipoConsulta = tipoConsulta;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
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

    public boolean isPerfusionContinua() {
        return perfusionContinua;
    }

    public void setPerfusionContinua(boolean perfusionContinua) {
        this.perfusionContinua = perfusionContinua;
    }

    public String getNombreIngrediente() {
        return nombreIngrediente;
    }

    public void setNombreIngrediente(String nombreIngrediente) {
        this.nombreIngrediente = nombreIngrediente;
    }

    public String getUnidadConcentracion() {
        return unidadConcentracion;
    }

    public void setUnidadConcentracion(String unidadConcentracion) {
        this.unidadConcentracion = unidadConcentracion;
    }

    public Integer getResurtimiento() {
        return resurtimiento;
    }

    public void setResurtimiento(Integer resurtimiento) {
        this.resurtimiento = resurtimiento;
    }

    public Date getFechaMinistrado() {
        return fechaMinistrado;
    }

    public void setFechaMinistrado(Date fechaMinistrado) {
        this.fechaMinistrado = fechaMinistrado;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaTermino() {
        return fechaTermino;
    }

    public void setFechaTermino(Date fechaTermino) {
        this.fechaTermino = fechaTermino;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Integer getIdProtocolo() {
        return idProtocolo;
    }

    public void setIdProtocolo(Integer idProtocolo) {
        this.idProtocolo = idProtocolo;
    }

    public String getTipoSolucion() {
        return tipoSolucion;
    }

    public void setTipoSolucion(String tipoSolucion) {
        this.tipoSolucion = tipoSolucion;
    }

    public double getPesoPaciente() {
        return pesoPaciente;
    }

    public void setPesoPaciente(double pesoPaciente) {
        this.pesoPaciente = pesoPaciente;
    }

    public double getEdadPaciente() {
        return edadPaciente;
    }

    public void setEdadPaciente(double edadPaciente) {
        this.edadPaciente = edadPaciente;
    }

    public double getTallaPaciente() {
        return tallaPaciente;
    }

    public void setTallaPaciente(double tallaPaciente) {
        this.tallaPaciente = tallaPaciente;
    }

    public double getAreaCorporal() {
        return areaCorporal;
    }

    public void setAreaCorporal(double areaCorporal) {
        this.areaCorporal = areaCorporal;
    }

    public String getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(String idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getIdVisita() {
        return idVisita;
    }

    public void setIdVisita(String idVisita) {
        this.idVisita = idVisita;
    }

    public Integer getIdEstatusSolucion() {
        return idEstatusSolucion;
    }

    public void setIdEstatusSolucion(Integer idEstatusSolucion) {
        this.idEstatusSolucion = idEstatusSolucion;
    }

    public String getInstruccionPreparacion() {
        return instruccionPreparacion;
    }

    public void setInstruccionPreparacion(String instruccionPreparacion) {
        this.instruccionPreparacion = instruccionPreparacion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public boolean isTempRefrigeracion() {
        return tempRefrigeracion;
    }

    public void setTempRefrigeracion(boolean tempRefrigeracion) {
        this.tempRefrigeracion = tempRefrigeracion;
    }

    public boolean isTempAmbiente() {
        return tempAmbiente;
    }

    public void setTempAmbiente(boolean tempAmbiente) {
        this.tempAmbiente = tempAmbiente;
    }

    public boolean isProteccionLuz() {
        return proteccionLuz;
    }

    public void setProteccionLuz(boolean proteccionLuz) {
        this.proteccionLuz = proteccionLuz;
    }

    public boolean isNoAgitar() {
        return noAgitar;
    }

    public void setNoAgitar(boolean noAgitar) {
        this.noAgitar = noAgitar;
    }

    public Integer getIdContenedor() {
        return idContenedor;
    }

    public void setIdContenedor(Integer idContenedor) {
        this.idContenedor = idContenedor;
    }

    public String getIdDiagnostico() {
        return idDiagnostico;
    }

    public void setIdDiagnostico(String idDiagnostico) {
        this.idDiagnostico = idDiagnostico;
    }

    public BigDecimal getVolumenTotal() {
        return volumenTotal;
    }

    public void setVolumenTotal(BigDecimal volumenTotal) {
        this.volumenTotal = volumenTotal;
    }

    @Override
    public String toString() {
        return "Surtimiento_Extend{" + "nombrePaciente=" + nombrePaciente + ", pacienteNumero=" + pacienteNumero + ", claveDerechohabiencia=" + claveDerechohabiencia + ", nombreEstructura=" + nombreEstructura + ", cama=" + cama + ", nombreMedico=" + nombreMedico + ", estatusSurtimiento=" + estatusSurtimiento + ", estatusPrescripcion=" + estatusPrescripcion + ", folioPrescripcion=" + folioPrescripcion + ", tipoPrescripcion=" + tipoPrescripcion + ", idEstructura=" + idEstructura + ", isEstatusSurtimiento=" + isEstatusSurtimiento + ", pacienteSexo=" + pacienteSexo + ", cedProfesional=" + cedProfesional + ", tipoConsulta=" + tipoConsulta + ", turno=" + turno + ", velocidad=" + velocidad + ", ritmo=" + ritmo + ", perfusionContinua=" + perfusionContinua + ", nombreIngrediente=" + nombreIngrediente + ", unidadConcentracion=" + unidadConcentracion + ", resurtimiento=" + resurtimiento + ", fechaMinistrado=" + fechaMinistrado + ", fechaInicio=" + fechaInicio + ", fechaTermino=" + fechaTermino + ", comentarios=" + comentarios + ", idProtocolo=" + idProtocolo + ", tipoSolucion=" + tipoSolucion + ", pesoPaciente=" + pesoPaciente + ", edadPaciente=" + edadPaciente + ", tallaPaciente=" + tallaPaciente + ", areaCorporal=" + areaCorporal + ", idPaciente=" + idPaciente + ", idVisita=" + idVisita + ", idEstatusSolucion=" + idEstatusSolucion + ", instruccionPreparacion=" + instruccionPreparacion + ", observaciones=" + observaciones + ", tempRefrigeracion=" + tempRefrigeracion + ", tempAmbiente=" + tempAmbiente + ", proteccionLuz=" + proteccionLuz + ", noAgitar=" + noAgitar + ", idContenedor=" + idContenedor + ", idDiagnostico=" + idDiagnostico + ", volumenTotal=" + volumenTotal + ", nombreEnvaseContenedor=" + nombreEnvaseContenedor + ", nombreViaAdministracion=" + nombreViaAdministracion + ", nombreProtoclo=" + nombreProtoclo + ", nombreDiagnostico=" + nombreDiagnostico + ", idCama=" + idCama + ", fechaNacimiento=" + fechaNacimiento + ", volumenPorDrenar=" + volumenPorDrenar + ", nombreDiluyente=" + nombreDiluyente + ", volumenAforo=" + volumenAforo + ", horasInfusion= " + horasInfusion + ", prioridad= " + prioridad + '}';
    }

    public String getNombreEnvaseContenedor() {
        return nombreEnvaseContenedor;
    }

    public void setNombreEnvaseContenedor(String nombreEnvaseContenedor) {
        this.nombreEnvaseContenedor = nombreEnvaseContenedor;
    }

    public String getNombreViaAdministracion() {
        return nombreViaAdministracion;
    }

    public void setNombreViaAdministracion(String nombreViaAdministracion) {
        this.nombreViaAdministracion = nombreViaAdministracion;
    }

    public String getNombreProtoclo() {
        return nombreProtoclo;
    }

    public void setNombreProtoclo(String nombreProtoclo) {
        this.nombreProtoclo = nombreProtoclo;
    }

    public String getNombreDiagnostico() {
        return nombreDiagnostico;
    }

    public void setNombreDiagnostico(String nombreDiagnostico) {
        this.nombreDiagnostico = nombreDiagnostico;
    }

    public String getIdCama() {
        return idCama;
    }

    public void setIdCama(String idCama) {
        this.idCama = idCama;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Double getVolumenPorDrenar() {
        return volumenPorDrenar;
    }

    public void setVolumenPorDrenar(Double volumenPorDrenar) {
        this.volumenPorDrenar = volumenPorDrenar;
    }

    public String getNombreDiluyente() {
        return nombreDiluyente;
    }

    public void setNombreDiluyente(String nombreDiluyente) {
        this.nombreDiluyente = nombreDiluyente;
    }

    public Double getVolumenAforo() {
        return volumenAforo;
    }

    public void setVolumenAforo(Double volumenAforo) {
        this.volumenAforo = volumenAforo;
    }

    public Integer getIdUsuarioEntrega() {
        return idUsuarioEntrega;
    }

    public void setIdUsuarioEntrega(Integer idUsuarioEntrega) {
        this.idUsuarioEntrega = idUsuarioEntrega;
    }

    public Integer getComentariosEntrega() {
        return comentariosEntrega;
    }

    public void setComentariosEntrega(Integer comentariosEntrega) {
        this.comentariosEntrega = comentariosEntrega;
    }

    public boolean getFuga() {
        return fuga;
    }

    public void setFuga(boolean fuga) {
        this.fuga = fuga;
    }

    public boolean getAire() {
        return aire;
    }

    public void setAire(boolean aire) {
        this.aire = aire;
    }

    public boolean getBurbujas() {
        return burbujas;
    }

    public void setBurbujas(boolean burbujas) {
        this.burbujas = burbujas;
    }

    public boolean getSedimentacion() {
        return sedimentacion;
    }

    public void setSedimentacion(boolean sedimentacion) {
        this.sedimentacion = sedimentacion;
    }

    public boolean getPrecipitado() {
        return precipitado;
    }

    public void setPrecipitado(boolean precipitado) {
        this.precipitado = precipitado;
    }

    public boolean getSelladoNoCoforme() {
        return selladoNoCoforme;
    }

    public void setSelladoNoCoforme(boolean selladoNoCoforme) {
        this.selladoNoCoforme = selladoNoCoforme;
    }

    public boolean getEscalaVolIlegible() {
        return escalaVolIlegible;
    }

    public void setEscalaVolIlegible(boolean escalaVolIlegible) {
        this.escalaVolIlegible = escalaVolIlegible;
    }

    public boolean getSeparacionFases() {
        return separacionFases;
    }

    public void setSeparacionFases(boolean separacionFases) {
        this.separacionFases = separacionFases;
    }

    public String getIdUsuarioAprobo() {
        return idUsuarioAprobo;
    }

    public void setIdUsuarioAprobo(String idUsuarioAprobo) {
        this.idUsuarioAprobo = idUsuarioAprobo;
    }

    public Integer getDictamen() {
        return dictamen;
    }

    public void setDictamen(Integer dictamen) {
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

    public Integer getIdHorarioParaEntregar() {
        return idHorarioParaEntregar;
    }

    public void setIdHorarioParaEntregar(Integer idHorarioParaEntregar) {
        this.idHorarioParaEntregar = idHorarioParaEntregar;
    }

    public String getEstatusSolucion() {
        return estatusSolucion;
    }

    public void setEstatusSolucion(String estatusSolucion) {
        this.estatusSolucion = estatusSolucion;
    }

    public String getIdTipoSolucion() {
        return idTipoSolucion;
    }

    public void setIdTipoSolucion(String idTipoSolucion) {
        this.idTipoSolucion = idTipoSolucion;
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

    public String getHorasInfusion() {
        return horasInfusion;
    }

    public void setHorasInfusion(String horasInfusion) {
        this.horasInfusion = horasInfusion;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public Integer getIdViaAdministracion() {
        return idViaAdministracion;
    }

    public void setIdViaAdministracion(Integer idViaAdministracion) {
        this.idViaAdministracion = idViaAdministracion;
    }

    public Integer getMinutosInfusion() {
        return minutosInfusion;
    }

    public void setMinutosInfusion(Integer minutosInfusion) {
        this.minutosInfusion = minutosInfusion;
    }

    public String getIndicaciones() {
        return indicaciones;
    }

    public void setIndicaciones(String indicaciones) {
        this.indicaciones = indicaciones;
    }

    public Date getFechaRechazo() {
        return fechaRechazo;
    }

    public void setFechaRechazo(Date fechaRechazo) {
        this.fechaRechazo = fechaRechazo;
    }

    public String getUsuarioRechazo() {
        return usuarioRechazo;
    }

    public void setUsuarioRechazo(String usuarioRechazo) {
        this.usuarioRechazo = usuarioRechazo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIdUsuarioAutoriza() {
        return idUsuarioAutoriza;
    }

    public void setIdUsuarioAutoriza(String idUsuarioAutoriza) {
        this.idUsuarioAutoriza = idUsuarioAutoriza;
    }

    public String getUsuarioAutoriza() {
        return usuarioAutoriza;
    }

    public void setUsuarioAutoriza(String usuarioAutoriza) {
        this.usuarioAutoriza = usuarioAutoriza;
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

    public void setComentariosAutoriza(String comentariosAutoriza) {
        this.comentarioAutoriza = comentariosAutoriza;
    }

}
