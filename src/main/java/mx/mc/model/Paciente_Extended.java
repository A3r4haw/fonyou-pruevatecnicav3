package mx.mc.model;

/**
 * @author AORTIZ
 */
public class Paciente_Extended extends Paciente {

    private static final long serialVersionUID = 1L;

    private String estatusPaciente;
    private String tipoPaciente;
    private String nombreEstructura;
    private String nombreEstructuraPeriferico;

    private Integer edadPaciente;

    private String calle;
    private String numeroInterior;
    private String numeroExterior;
    private String colonia;
    private String codigoPostal;
    private String municipio;
    private String estado;
    private String pais;

    private String telefonoCasa;
    private String telefonoCelular;
    private String telefonoOficina;
    private String extension;
    private String correoElectronico;

    private String apellidoPaternoResponsable;
    private String apellidoMaternoResponsable;
    private String nombreResponsable;
    private String responsableLegal;
    private String rfcResponsable;
    private String curpResponsable;
    private String telefonoPrincipalResponsable;
    private String telefonoSecundarioResponsable;
    private String correoElectronicoResponsable;

    private String estadoCivil;
    private String escolaridad;
    private String grupoEtnico;
    private String grupoSanguineo;
    private String religion;
    private String nivelSocioeconomico;
    private String ocupacion;
    private String tipoVivienda;
    private String parentesco;
    private String unidadMedica;
    private String nombrePaciente;
    private String nombreCama;
    private String claveEstructura;
    private String idVisita;

//    private String peso;
//    private String talla;
//    private String areaCorporal;
    private double pesoPaciente;
//    private double edadPaciente;
    private double tallaPaciente;
    private double areaCorporal;

    public String getEstatusPaciente() {
        return estatusPaciente;
    }

    public void setEstatusPaciente(String estatusPaciente) {
        this.estatusPaciente = estatusPaciente;
    }

    public String getTipoPaciente() {
        return tipoPaciente;
    }

    public void setTipoPaciente(String tipoPaciente) {
        this.tipoPaciente = tipoPaciente;
    }

    public String getNombreEstructura() {
        return nombreEstructura;
    }

    public void setNombreEstructura(String nombreEstructura) {
        this.nombreEstructura = nombreEstructura;
    }

    public String getNombreEstructuraPeriferico() {
        return nombreEstructuraPeriferico;
    }

    public void setNombreEstructuraPeriferico(String nombreEstructuraPeriferico) {
        this.nombreEstructuraPeriferico = nombreEstructuraPeriferico;
    }

    public Integer getEdadPaciente() {
        return edadPaciente;
    }

    public void setEdadPaciente(Integer edadPaciente) {
        this.edadPaciente = edadPaciente;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumeroInterior() {
        return numeroInterior;
    }

    public void setNumeroInterior(String numeroInterior) {
        this.numeroInterior = numeroInterior;
    }

    public String getNumeroExterior() {
        return numeroExterior;
    }

    public void setNumeroExterior(String numeroExterior) {
        this.numeroExterior = numeroExterior;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getTelefonoCasa() {
        return telefonoCasa;
    }

    public void setTelefonoCasa(String telefonoCasa) {
        this.telefonoCasa = telefonoCasa;
    }

    public String getTelefonoCelular() {
        return telefonoCelular;
    }

    public void setTelefonoCelular(String telefonoCelular) {
        this.telefonoCelular = telefonoCelular;
    }

    public String getTelefonoOficina() {
        return telefonoOficina;
    }

    public void setTelefonoOficina(String telefonoOficina) {
        this.telefonoOficina = telefonoOficina;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getApellidoPaternoResponsable() {
        return apellidoPaternoResponsable;
    }

    public void setApellidoPaternoResponsable(String apellidoPaternoResponsable) {
        this.apellidoPaternoResponsable = apellidoPaternoResponsable;
    }

    public String getApellidoMaternoResponsable() {
        return apellidoMaternoResponsable;
    }

    public void setApellidoMaternoResponsable(String apellidoMaternoResponsable) {
        this.apellidoMaternoResponsable = apellidoMaternoResponsable;
    }

    public String getNombreResponsable() {
        return nombreResponsable;
    }

    public void setNombreResponsable(String nombreResponsable) {
        this.nombreResponsable = nombreResponsable;
    }

    public String getResponsableLegal() {
        return responsableLegal;
    }

    public void setResponsableLegal(String responsableLegal) {
        this.responsableLegal = responsableLegal;
    }

    public String getRfcResponsable() {
        return rfcResponsable;
    }

    public void setRfcResponsable(String rfcResponsable) {
        this.rfcResponsable = rfcResponsable;
    }

    public String getCurpResponsable() {
        return curpResponsable;
    }

    public void setCurpResponsable(String curpResponsable) {
        this.curpResponsable = curpResponsable;
    }

    public String getTelefonoPrincipalResponsable() {
        return telefonoPrincipalResponsable;
    }

    public void setTelefonoPrincipalResponsable(String telefonoPrincipalResponsable) {
        this.telefonoPrincipalResponsable = telefonoPrincipalResponsable;
    }

    public String getTelefonoSecundarioResponsable() {
        return telefonoSecundarioResponsable;
    }

    public void setTelefonoSecundarioResponsable(String telefonoSecundarioResponsable) {
        this.telefonoSecundarioResponsable = telefonoSecundarioResponsable;
    }

    public String getCorreoElectronicoResponsable() {
        return correoElectronicoResponsable;
    }

    public void setCorreoElectronicoResponsable(String correoElectronicoResponsable) {
        this.correoElectronicoResponsable = correoElectronicoResponsable;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getEscolaridad() {
        return escolaridad;
    }

    public void setEscolaridad(String escolaridad) {
        this.escolaridad = escolaridad;
    }

    public String getGrupoEtnico() {
        return grupoEtnico;
    }

    public void setGrupoEtnico(String grupoEtnico) {
        this.grupoEtnico = grupoEtnico;
    }

    public String getGrupoSanguineo() {
        return grupoSanguineo;
    }

    public void setGrupoSanguineo(String grupoSanguineo) {
        this.grupoSanguineo = grupoSanguineo;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getNivelSocioeconomico() {
        return nivelSocioeconomico;
    }

    public void setNivelSocioeconomico(String nivelSocioeconomico) {
        this.nivelSocioeconomico = nivelSocioeconomico;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public String getTipoVivienda() {
        return tipoVivienda;
    }

    public void setTipoVivienda(String tipoVivienda) {
        this.tipoVivienda = tipoVivienda;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public String getUnidadMedica() {
        return unidadMedica;
    }

    public void setUnidadMedica(String unidadMedica) {
        this.unidadMedica = unidadMedica;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getNombreCama() {
        return nombreCama;
    }

    public void setNombreCama(String nombreCama) {
        this.nombreCama = nombreCama;
    }

    public String getClaveEstructura() {
        return claveEstructura;
    }

    public void setClaveEstructura(String claveEstructura) {
        this.claveEstructura = claveEstructura;
    }

    public String getIdVisita() {
        return idVisita;
    }

    public void setIdVisita(String idVisita) {
        this.idVisita = idVisita;
    }

    public double getPesoPaciente() {
        return pesoPaciente;
    }

    public void setPesoPaciente(double pesoPaciente) {
        this.pesoPaciente = pesoPaciente;
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

}
