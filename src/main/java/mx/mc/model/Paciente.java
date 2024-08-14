package mx.mc.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author AORTIZ
 */
public class Paciente implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idPaciente;
    private String nombreCompleto;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private char sexo;
    private Date fechaNacimiento;
    private String rfc;
    private String curp;
    private Integer idTipoPaciente;
    private Integer idUnidadMedica;
    private String claveDerechohabiencia;
    private Integer idEstatusPaciente;
    private String pacienteNumero;
    private Integer idEstadoCivil;
    private Integer idEscolaridad;
    private Integer idGrupoEtnico;
    private Integer idGrupoSanguineo;
    private Integer idReligion;
    private Integer idNivelSocioEconomico;
    private Integer idTipoVivienda;
    private Integer idOcupacion;
    private String idEstructura;
    private String idEstructuraPeriferico;
    private Integer estatusGabinete;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;
    
    private String lugarNacimiento;
    private String paisResidencia;
    private Integer presentaDiscapacidad;
    private String descripcionDiscapacidad;

    public Paciente() {
    }

    public Paciente(String idPaciente) {
        this.idPaciente = idPaciente;
    }
       
    public Paciente(String idEstructura, String nombreCompleto) {
            this.idEstructura = idEstructura;
            this.nombreCompleto = nombreCompleto;
        }

    public String getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(String idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public Integer getIdTipoPaciente() {
        return idTipoPaciente;
    }

    public void setIdTipoPaciente(Integer idTipoPaciente) {
        this.idTipoPaciente = idTipoPaciente;
    }

    public Integer getIdUnidadMedica() {
        return idUnidadMedica;
    }

    public void setIdUnidadMedica(Integer idUnidadMedica) {
        this.idUnidadMedica = idUnidadMedica;
    }

    public String getClaveDerechohabiencia() {
        return claveDerechohabiencia;
    }

    public void setClaveDerechohabiencia(String claveDerechohabiencia) {
        this.claveDerechohabiencia = claveDerechohabiencia;
    }

    public Integer getIdEstatusPaciente() {
        return idEstatusPaciente;
    }

    public void setIdEstatusPaciente(Integer idEstatusPaciente) {
        this.idEstatusPaciente = idEstatusPaciente;
    }
    
    public String getPacienteNumero() {
        return pacienteNumero;
    }

    public void setPacienteNumero(String pacienteNumero) {
        this.pacienteNumero = pacienteNumero;
    }

    public Integer getIdEstadoCivil() {
        return idEstadoCivil;
    }

    public void setIdEstadoCivil(Integer idEstadoCivil) {
        this.idEstadoCivil = idEstadoCivil;
    }

    public Integer getIdEscolaridad() {
        return idEscolaridad;
    }

    public void setIdEscolaridad(Integer idEscolaridad) {
        this.idEscolaridad = idEscolaridad;
    }

    public Integer getIdGrupoEtnico() {
        return idGrupoEtnico;
    }

    public void setIdGrupoEtnico(Integer idGrupoEtnico) {
        this.idGrupoEtnico = idGrupoEtnico;
    }

    public Integer getIdGrupoSanguineo() {
        return idGrupoSanguineo;
    }

    public void setIdGrupoSanguineo(Integer idGrupoSanguineo) {
        this.idGrupoSanguineo = idGrupoSanguineo;
    }

    public Integer getIdReligion() {
        return idReligion;
    }

    public void setIdReligion(Integer idReligion) {
        this.idReligion = idReligion;
    }

    public Integer getIdNivelSocioEconomico() {
        return idNivelSocioEconomico;
    }

    public void setIdNivelSocioEconomico(Integer idNivelSocioEconomico) {
        this.idNivelSocioEconomico = idNivelSocioEconomico;
    }

    public Integer getIdTipoVivienda() {
        return idTipoVivienda;
    }

    public void setIdTipoVivienda(Integer idTipoVivienda) {
        this.idTipoVivienda = idTipoVivienda;
    }

    public Integer getIdOcupacion() {
        return idOcupacion;
    }

    public void setIdOcupacion(Integer idOcupacion) {
        this.idOcupacion = idOcupacion;
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

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public String getIdEstructuraPeriferico() {
        return idEstructuraPeriferico;
    }

    public void setIdEstructuraPeriferico(String idEstructuraPeriferico) {
        this.idEstructuraPeriferico = idEstructuraPeriferico;
    }

    public Integer getEstatusGabinete() {
        return estatusGabinete;
    }

    public void setEstatusGabinete(Integer estatusGabinete) {
        this.estatusGabinete = estatusGabinete;
    }

    public String getLugarNacimiento() {
        return lugarNacimiento;
    }

    public void setLugarNacimiento(String lugarNacimiento) {
        this.lugarNacimiento = lugarNacimiento;
    }

    public String getPaisResidencia() {
        return paisResidencia;
    }

    public void setPaisResidencia(String paisResidencia) {
        this.paisResidencia = paisResidencia;
    }

    public Integer getPresentaDiscapacidad() {
        return presentaDiscapacidad;
    }

    public void setPresentaDiscapacidad(Integer presentaDiscapacidad) {
        this.presentaDiscapacidad = presentaDiscapacidad;
    }

    public String getDescripcionDiscapacidad() {
        return descripcionDiscapacidad;
    }

    public void setDescripcionDiscapacidad(String descripcionDiscapacidad) {
        this.descripcionDiscapacidad = descripcionDiscapacidad;
    }
    
    

}
