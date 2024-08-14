package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author apalacios
 */
public class Procedimiento implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String idProcedimiento;
    private String folio;
    private Date fechaRegistro;
    private int idEstatusProced;
    private String idPaciente;
    private String nombrePaciente;
    private Date fechaNacimiento;
    private String numeroExpediente;
    private char sexo;
    private String derechohabiencia;
    private String unidadMedicaSolicita;
    private String servicioSolicita;
    private String especialidadSolicita;
    private String idUsuarioSolicita;
    private String cedula;
    private String cedulaEspecialidad;
    private String idEstudio;
    private String codigoEstudio;
    private Date fechaSolicitada;
    private int idTipoSolicitud;
    private Integer procedimientoInvasivo;
    private Integer idMedioContraste;
    private Integer idSedante;
    private String motivoSolicitud;
    private String atencionEspecial;
    private String notasSolicitud;
    private String diagnosticoPresuntivo;
    private String justificacionClinica;
    private String idAreaRealiza;
    private String idModalidad;
    private Date fechaProgramada;
    private String idUsuarioPrograma;
    private String observacionesProgramacion;
    private Date fechaArribo;
    private String idUsuarioConfirmaArr;
    private String notasArribo;
    private Date fechaAsignaMod;
    private String idUsuarioAsignaMod;
    private String notasAsignaMod;
    private Date fechaRealiza;
    private String idUsuarioRealiza;
    private String idModalidadRealiza;
    private String notasRealiza;
    private String posicion;
    private String protocolo;
    private String objetivo;
    private String observacionesRealiza;
    private String eventoAdverso;
    private String cualEvento;
    private String equipoMaterialEvenAdv;
    private Integer estudioRealizado;
    private String rutaEstudio;
    private Integer reprogramado;
    private Integer idMotivoReprogramacion;
    private Date fechaReprogramado;
    private String notasReprogramado;
    private String complicaciones;
    private int cancelado;
    private String tecnicaExploracion;
    private Date fechaHoraInterpreta;
    private String idUsuarioInterpreta;
    private String informe;
    private String impresionDiagnostica;
    private int concluido;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;

    public String getIdProcedimiento() {
        return idProcedimiento;
    }

    public void setIdProcedimiento(String idProcedimiento) {
        this.idProcedimiento = idProcedimiento;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public int getIdEstatusProced() {
        return idEstatusProced;
    }

    public void setIdEstatusProced(int idEstatusProced) {
        this.idEstatusProced = idEstatusProced;
    }

    public String getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(String idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNumeroExpediente() {
        return numeroExpediente;
    }

    public void setNumeroExpediente(String numeroExpediente) {
        this.numeroExpediente = numeroExpediente;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public String getDerechohabiencia() {
        return derechohabiencia;
    }

    public void setDerechohabiencia(String derechohabiencia) {
        this.derechohabiencia = derechohabiencia;
    }

    public String getUnidadMedicaSolicita() {
        return unidadMedicaSolicita;
    }

    public void setUnidadMedicaSolicita(String unidadMedicaSolicita) {
        this.unidadMedicaSolicita = unidadMedicaSolicita;
    }

    public String getServicioSolicita() {
        return servicioSolicita;
    }

    public void setServicioSolicita(String servicioSolicita) {
        this.servicioSolicita = servicioSolicita;
    }

    public String getEspecialidadSolicita() {
        return especialidadSolicita;
    }

    public void setEspecialidadSolicita(String especialidadSolicita) {
        this.especialidadSolicita = especialidadSolicita;
    }

    public String getIdUsuarioSolicita() {
        return idUsuarioSolicita;
    }

    public void setIdUsuarioSolicita(String idUsuarioSolicita) {
        this.idUsuarioSolicita = idUsuarioSolicita;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getCedulaEspecialidad() {
        return cedulaEspecialidad;
    }

    public void setCedulaEspecialidad(String cedulaEspecialidad) {
        this.cedulaEspecialidad = cedulaEspecialidad;
    }

    public String getIdEstudio() {
        return idEstudio;
    }

    public void setIdEstudio(String idEstudio) {
        this.idEstudio = idEstudio;
    }

    public String getCodigoEstudio() {
        return codigoEstudio;
    }

    public void setCodigoEstudio(String codigoEstudio) {
        this.codigoEstudio = codigoEstudio;
    }

    public Date getFechaSolicitada() {
        return fechaSolicitada;
    }

    public void setFechaSolicitada(Date fechaSolicitada) {
        this.fechaSolicitada = fechaSolicitada;
    }

    public int getIdTipoSolicitud() {
        return idTipoSolicitud;
    }

    public void setIdTipoSolicitud(int idTipoSolicitud) {
        this.idTipoSolicitud = idTipoSolicitud;
    }

    public Integer getProcedimientoInvasivo() {
        return procedimientoInvasivo;
    }

    public void setProcedimientoInvasivo(Integer procedimientoInvasivo) {
        this.procedimientoInvasivo = procedimientoInvasivo;
    }

    public Integer getIdMedioContraste() {
        return idMedioContraste;
    }

    public void setIdMedioContraste(Integer idMedioContraste) {
        this.idMedioContraste = idMedioContraste;
    }

    public Integer getIdSedante() {
        return idSedante;
    }

    public void setIdSedante(Integer idSedante) {
        this.idSedante = idSedante;
    }

    public String getMotivoSolicitud() {
        return motivoSolicitud;
    }

    public void setMotivoSolicitud(String motivoSolicitud) {
        this.motivoSolicitud = motivoSolicitud;
    }

    public String getAtencionEspecial() {
        return atencionEspecial;
    }

    public void setAtencionEspecial(String atencionEspecial) {
        this.atencionEspecial = atencionEspecial;
    }

    public String getNotasSolicitud() {
        return notasSolicitud;
    }

    public void setNotasSolicitud(String notasSolicitud) {
        this.notasSolicitud = notasSolicitud;
    }

    public String getDiagnosticoPresuntivo() {
        return diagnosticoPresuntivo;
    }

    public void setDiagnosticoPresuntivo(String diagnosticoPresuntivo) {
        this.diagnosticoPresuntivo = diagnosticoPresuntivo;
    }

    public String getJustificacionClinica() {
        return justificacionClinica;
    }

    public void setJustificacionClinica(String justificacionClinica) {
        this.justificacionClinica = justificacionClinica;
    }

    public String getIdAreaRealiza() {
        return idAreaRealiza;
    }

    public void setIdAreaRealiza(String idAreaRealiza) {
        this.idAreaRealiza = idAreaRealiza;
    }

    public String getIdModalidad() {
        return idModalidad;
    }

    public void setIdModalidad(String idModalidad) {
        this.idModalidad = idModalidad;
    }

    public Date getFechaProgramada() {
        return fechaProgramada;
    }

    public void setFechaProgramada(Date fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
    }

    public String getIdUsuarioPrograma() {
        return idUsuarioPrograma;
    }

    public void setIdUsuarioPrograma(String idUsuarioPrograma) {
        this.idUsuarioPrograma = idUsuarioPrograma;
    }

    public String getObservacionesProgramacion() {
        return observacionesProgramacion;
    }

    public void setObservacionesProgramacion(String observacionesProgramacion) {
        this.observacionesProgramacion = observacionesProgramacion;
    }

    public Date getFechaArribo() {
        return fechaArribo;
    }

    public void setFechaArribo(Date fechaArribo) {
        this.fechaArribo = fechaArribo;
    }

    public String getIdUsuarioConfirmaArr() {
        return idUsuarioConfirmaArr;
    }

    public void setIdUsuarioConfirmaArr(String idUsuarioConfirmaArr) {
        this.idUsuarioConfirmaArr = idUsuarioConfirmaArr;
    }

    public String getNotasArribo() {
        return notasArribo;
    }

    public void setNotasArribo(String notasArribo) {
        this.notasArribo = notasArribo;
    }

    public Date getFechaAsignaMod() {
        return fechaAsignaMod;
    }

    public void setFechaAsignaMod(Date fechaAsignaMod) {
        this.fechaAsignaMod = fechaAsignaMod;
    }

    public String getIdUsuarioAsignaMod() {
        return idUsuarioAsignaMod;
    }

    public void setIdUsuarioAsignaMod(String idUsuarioAsignaMod) {
        this.idUsuarioAsignaMod = idUsuarioAsignaMod;
    }

    public String getNotasAsignaMod() {
        return notasAsignaMod;
    }

    public void setNotasAsignaMod(String notasAsignaMod) {
        this.notasAsignaMod = notasAsignaMod;
    }

    public Date getFechaRealiza() {
        return fechaRealiza;
    }

    public void setFechaRealiza(Date fechaRealiza) {
        this.fechaRealiza = fechaRealiza;
    }

    public String getIdUsuarioRealiza() {
        return idUsuarioRealiza;
    }

    public void setIdUsuarioRealiza(String idUsuarioRealiza) {
        this.idUsuarioRealiza = idUsuarioRealiza;
    }

    public String getIdModalidadRealiza() {
        return idModalidadRealiza;
    }

    public void setIdModalidadRealiza(String idModalidadRealiza) {
        this.idModalidadRealiza = idModalidadRealiza;
    }

    public String getNotasRealiza() {
        return notasRealiza;
    }

    public void setNotasRealiza(String notasRealiza) {
        this.notasRealiza = notasRealiza;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getObservacionesRealiza() {
        return observacionesRealiza;
    }

    public void setObservacionesRealiza(String observacionesRealiza) {
        this.observacionesRealiza = observacionesRealiza;
    }

    public String getEventoAdverso() {
        return eventoAdverso;
    }

    public void setEventoAdverso(String eventoAdverso) {
        this.eventoAdverso = eventoAdverso;
    }

    public String getCualEvento() {
        return cualEvento;
    }

    public void setCualEvento(String cualEvento) {
        this.cualEvento = cualEvento;
    }

    public String getEquipoMaterialEvenAdv() {
        return equipoMaterialEvenAdv;
    }

    public void setEquipoMaterialEvenAdv(String equipoMaterialEvenAdv) {
        this.equipoMaterialEvenAdv = equipoMaterialEvenAdv;
    }

    public Integer getEstudioRealizado() {
        return estudioRealizado;
    }

    public void setEstudioRealizado(Integer estudioRealizado) {
        this.estudioRealizado = estudioRealizado;
    }

    public String getRutaEstudio() {
        return rutaEstudio;
    }

    public void setRutaEstudio(String rutaEstudio) {
        this.rutaEstudio = rutaEstudio;
    }

    public Integer getReprogramado() {
        return reprogramado;
    }

    public void setReprogramado(Integer reprogramado) {
        this.reprogramado = reprogramado;
    }

    public Integer getIdMotivoReprogramacion() {
        return idMotivoReprogramacion;
    }

    public void setIdMotivoReprogramacion(Integer idMotivoReprogramacion) {
        this.idMotivoReprogramacion = idMotivoReprogramacion;
    }

    public Date getFechaReprogramado() {
        return fechaReprogramado;
    }

    public void setFechaReprogramado(Date fechaReprogramado) {
        this.fechaReprogramado = fechaReprogramado;
    }

    public String getNotasReprogramado() {
        return notasReprogramado;
    }

    public void setNotasReprogramado(String notasReprogramado) {
        this.notasReprogramado = notasReprogramado;
    }

    public String getComplicaciones() {
        return complicaciones;
    }

    public void setComplicaciones(String complicaciones) {
        this.complicaciones = complicaciones;
    }

    public int getCancelado() {
        return cancelado;
    }

    public void setCancelado(int cancelado) {
        this.cancelado = cancelado;
    }

    public String getTecnicaExploracion() {
        return tecnicaExploracion;
    }

    public void setTecnicaExploracion(String tecnicaExploracion) {
        this.tecnicaExploracion = tecnicaExploracion;
    }

    public Date getFechaHoraInterpreta() {
        return fechaHoraInterpreta;
    }

    public void setFechaHoraInterpreta(Date fechaHoraInterpreta) {
        this.fechaHoraInterpreta = fechaHoraInterpreta;
    }

    public String getIdUsuarioInterpreta() {
        return idUsuarioInterpreta;
    }

    public void setIdUsuarioInterpreta(String idUsuarioInterpreta) {
        this.idUsuarioInterpreta = idUsuarioInterpreta;
    }

    public String getInforme() {
        return informe;
    }

    public void setInforme(String informe) {
        this.informe = informe;
    }

    public String getImpresionDiagnostica() {
        return impresionDiagnostica;
    }

    public void setImpresionDiagnostica(String impresionDiagnostica) {
        this.impresionDiagnostica = impresionDiagnostica;
    }

    public int getConcluido() {
        return concluido;
    }

    public void setConcluido(int concluido) {
        this.concluido = concluido;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.idProcedimiento);
        hash = 29 * hash + Objects.hashCode(this.folio);
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
        final Procedimiento other = (Procedimiento) obj;
        if (!Objects.equals(this.idProcedimiento, other.idProcedimiento)) {
            return false;
        }
        return Objects.equals(this.folio, other.folio);
    }

    @Override
    public String toString() {
        return folio;
    }
}
