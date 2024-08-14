/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author bbautista
 */
public class Reaccion {
  private String idReaccion;
  private String folio;
  private String numeroNotificacion;
  @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
  private Date fecha;
  private Integer idEstatusReaccion;
  private String estatusReaccion;
  private String rfcPaciente;
  private String curpPaciente;
  private String idPaciente;
  private String numeroPaciente;
  private String nombrePaciente;
  @JsonFormat(pattern = "dd/MM/yyyy")
  private Date fechaNacimiento;
  private Integer edad;
  private Integer sexo;
  private Double peso;
  private Double estatura;
  @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
  private Date fechaInicioSospecha;
  private Integer idTipoReaccion;
  private String tipoReaccion;
  private Integer idConsecuencia;
  private String consecuencia;
  private String descripcion;
  private String idInsumo;
  private String medicamento;
  private String clave;
  private Integer idSustanciaActiva;
  private String sustanciaActiva;
  private Integer idViaAdministracion;
  private String viaAdministracion;
  private String lote;
  @JsonFormat(pattern = "dd/MM/yyyy")
  private Date caducidad;
  private String laboratorio;
  private String denomincacionDistintiva;
  private Double dosis;
  private Integer idUnidad;
  private String unidad;
  private String frecuencia;
  private Integer duracion;
  private String motivoPrescripcion;
  @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
  private Date inicioAdministracion;
  @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
  private Date finAdministracion;
  private Integer medicamentoSuspendido;
  private Integer desaparecioReaccion;
  private Integer disminuyoDosis;
  private Double cuanto;
  private Integer cambioFarmacoTerapia;
  private String cual;
  private Integer reaparecioReaccionReadministrar;
  private Integer persistioReaccion;
  private String historiaClinica;
  private Integer idTipoInformeLab;
  private String tipoInformeLab;
  private Integer idOrigenLab;
  private String origenLab;
  private Integer idTipoInformeProf;
  private String tipoInformeProf;
  private Integer idOrigenProf;
  private String origenProf;
  private String rfcInformante;
  private String curpInformante;
  private String numeroInformante;
  private String cedula;
  private String nombre;
  private String apellidoPat;
  private String apellidoMat;
  private String telefono;
  private String correoElectronico;
  private String calle;
  private String numeroExt;
  private String numeroInt;
  private String colonia;
  private String localidad;
  private String municipio;
  private String estado;
  private String pais;
  private String cp;
  private Integer publicarInformacion;
  @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
  private String riesgo;
  private Date insertFecha;
  private String insertIdUsuario;
  @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
  private Date updateFecha;
  private String updateIdUsuario;
  
  List<MedicamentoConcomitante> insumos;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.idReaccion);
        hash = 83 * hash + Objects.hashCode(this.folio);
        hash = 83 * hash + Objects.hashCode(this.numeroNotificacion);
        hash = 83 * hash + Objects.hashCode(this.fecha);
        hash = 83 * hash + Objects.hashCode(this.idEstatusReaccion);
        hash = 83 * hash + Objects.hashCode(this.estatusReaccion);
        hash = 83 * hash + Objects.hashCode(this.rfcPaciente);
        hash = 83 * hash + Objects.hashCode(this.curpPaciente);
        hash = 83 * hash + Objects.hashCode(this.idPaciente);
        hash = 83 * hash + Objects.hashCode(this.numeroPaciente);
        hash = 83 * hash + Objects.hashCode(this.nombrePaciente);
        hash = 83 * hash + Objects.hashCode(this.fechaNacimiento);
        hash = 83 * hash + Objects.hashCode(this.edad);
        hash = 83 * hash + Objects.hashCode(this.sexo);
        hash = 83 * hash + Objects.hashCode(this.peso);
        hash = 83 * hash + Objects.hashCode(this.estatura);
        hash = 83 * hash + Objects.hashCode(this.fechaInicioSospecha);
        hash = 83 * hash + Objects.hashCode(this.idTipoReaccion);
        hash = 83 * hash + Objects.hashCode(this.tipoReaccion);
        hash = 83 * hash + Objects.hashCode(this.idConsecuencia);
        hash = 83 * hash + Objects.hashCode(this.consecuencia);
        hash = 83 * hash + Objects.hashCode(this.descripcion);
        hash = 83 * hash + Objects.hashCode(this.idInsumo);
        hash = 83 * hash + Objects.hashCode(this.medicamento);
        hash = 83 * hash + Objects.hashCode(this.clave);
        hash = 83 * hash + Objects.hashCode(this.idSustanciaActiva);
        hash = 83 * hash + Objects.hashCode(this.sustanciaActiva);
        hash = 83 * hash + Objects.hashCode(this.idViaAdministracion);
        hash = 83 * hash + Objects.hashCode(this.viaAdministracion);
        hash = 83 * hash + Objects.hashCode(this.lote);
        hash = 83 * hash + Objects.hashCode(this.caducidad);
        hash = 83 * hash + Objects.hashCode(this.laboratorio);
        hash = 83 * hash + Objects.hashCode(this.denomincacionDistintiva);
        hash = 83 * hash + Objects.hashCode(this.dosis);
        hash = 83 * hash + Objects.hashCode(this.idUnidad);
        hash = 83 * hash + Objects.hashCode(this.unidad);
        hash = 83 * hash + Objects.hashCode(this.frecuencia);
        hash = 83 * hash + Objects.hashCode(this.duracion);
        hash = 83 * hash + Objects.hashCode(this.motivoPrescripcion);
        hash = 83 * hash + Objects.hashCode(this.inicioAdministracion);
        hash = 83 * hash + Objects.hashCode(this.finAdministracion);
        hash = 83 * hash + Objects.hashCode(this.medicamentoSuspendido);
        hash = 83 * hash + Objects.hashCode(this.desaparecioReaccion);
        hash = 83 * hash + Objects.hashCode(this.disminuyoDosis);
        hash = 83 * hash + Objects.hashCode(this.cuanto);
        hash = 83 * hash + Objects.hashCode(this.cambioFarmacoTerapia);
        hash = 83 * hash + Objects.hashCode(this.cual);
        hash = 83 * hash + Objects.hashCode(this.reaparecioReaccionReadministrar);
        hash = 83 * hash + Objects.hashCode(this.persistioReaccion);
        hash = 83 * hash + Objects.hashCode(this.historiaClinica);
        hash = 83 * hash + Objects.hashCode(this.idTipoInformeLab);
        hash = 83 * hash + Objects.hashCode(this.tipoInformeLab);
        hash = 83 * hash + Objects.hashCode(this.idOrigenLab);
        hash = 83 * hash + Objects.hashCode(this.origenLab);
        hash = 83 * hash + Objects.hashCode(this.idTipoInformeProf);
        hash = 83 * hash + Objects.hashCode(this.tipoInformeProf);
        hash = 83 * hash + Objects.hashCode(this.idOrigenProf);
        hash = 83 * hash + Objects.hashCode(this.origenProf);
        hash = 83 * hash + Objects.hashCode(this.rfcInformante);
        hash = 83 * hash + Objects.hashCode(this.curpInformante);
        hash = 83 * hash + Objects.hashCode(this.numeroInformante);
        hash = 83 * hash + Objects.hashCode(this.cedula);
        hash = 83 * hash + Objects.hashCode(this.nombre);
        hash = 83 * hash + Objects.hashCode(this.apellidoPat);
        hash = 83 * hash + Objects.hashCode(this.apellidoMat);
        hash = 83 * hash + Objects.hashCode(this.telefono);
        hash = 83 * hash + Objects.hashCode(this.correoElectronico);
        hash = 83 * hash + Objects.hashCode(this.calle);
        hash = 83 * hash + Objects.hashCode(this.numeroExt);
        hash = 83 * hash + Objects.hashCode(this.numeroInt);
        hash = 83 * hash + Objects.hashCode(this.colonia);
        hash = 83 * hash + Objects.hashCode(this.localidad);
        hash = 83 * hash + Objects.hashCode(this.municipio);
        hash = 83 * hash + Objects.hashCode(this.estado);
        hash = 83 * hash + Objects.hashCode(this.pais);
        hash = 83 * hash + Objects.hashCode(this.cp);
        hash = 83 * hash + Objects.hashCode(this.publicarInformacion);
        hash = 83 * hash + Objects.hashCode(this.riesgo);
        hash = 83 * hash + Objects.hashCode(this.insertFecha);
        hash = 83 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 83 * hash + Objects.hashCode(this.updateFecha);
        hash = 83 * hash + Objects.hashCode(this.updateIdUsuario);
        hash = 83 * hash + Objects.hashCode(this.insumos);
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
        final Reaccion other = (Reaccion) obj;
        if (!Objects.equals(this.idReaccion, other.idReaccion)) {
            return false;
        }
        if (!Objects.equals(this.folio, other.folio)) {
            return false;
        }
        if (!Objects.equals(this.numeroNotificacion, other.numeroNotificacion)) {
            return false;
        }
        if (!Objects.equals(this.estatusReaccion, other.estatusReaccion)) {
            return false;
        }
        if (!Objects.equals(this.rfcPaciente, other.rfcPaciente)) {
            return false;
        }
        if (!Objects.equals(this.curpPaciente, other.curpPaciente)) {
            return false;
        }
        if (!Objects.equals(this.idPaciente, other.idPaciente)) {
            return false;
        }
        if (!Objects.equals(this.numeroPaciente, other.numeroPaciente)) {
            return false;
        }
        if (!Objects.equals(this.nombrePaciente, other.nombrePaciente)) {
            return false;
        }
        if (!Objects.equals(this.tipoReaccion, other.tipoReaccion)) {
            return false;
        }
        if (!Objects.equals(this.consecuencia, other.consecuencia)) {
            return false;
        }
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        if (!Objects.equals(this.idInsumo, other.idInsumo)) {
            return false;
        }
        if (!Objects.equals(this.medicamento, other.medicamento)) {
            return false;
        }
        if (!Objects.equals(this.clave, other.clave)) {
            return false;
        }
        if (!Objects.equals(this.sustanciaActiva, other.sustanciaActiva)) {
            return false;
        }
        if (!Objects.equals(this.viaAdministracion, other.viaAdministracion)) {
            return false;
        }
        if (!Objects.equals(this.lote, other.lote)) {
            return false;
        }
        if (!Objects.equals(this.laboratorio, other.laboratorio)) {
            return false;
        }
        if (!Objects.equals(this.denomincacionDistintiva, other.denomincacionDistintiva)) {
            return false;
        }
        if (!Objects.equals(this.unidad, other.unidad)) {
            return false;
        }
        if (!Objects.equals(this.frecuencia, other.frecuencia)) {
            return false;
        }
        if (!Objects.equals(this.motivoPrescripcion, other.motivoPrescripcion)) {
            return false;
        }
        if (!Objects.equals(this.cual, other.cual)) {
            return false;
        }
        if (!Objects.equals(this.historiaClinica, other.historiaClinica)) {
            return false;
        }
        if (!Objects.equals(this.tipoInformeLab, other.tipoInformeLab)) {
            return false;
        }
        if (!Objects.equals(this.idOrigenLab, other.idOrigenLab)) {
            return false;
        }
        if (!Objects.equals(this.origenLab, other.origenLab)) {
            return false;
        }
        if (!Objects.equals(this.tipoInformeProf, other.tipoInformeProf)) {
            return false;
        }
        if (!Objects.equals(this.idOrigenProf, other.idOrigenProf)) {
            return false;
        }
        if (!Objects.equals(this.origenProf, other.origenProf)) {
            return false;
        }
        if (!Objects.equals(this.rfcInformante, other.rfcInformante)) {
            return false;
        }
        if (!Objects.equals(this.curpInformante, other.curpInformante)) {
            return false;
        }
        if (!Objects.equals(this.numeroInformante, other.numeroInformante)) {
            return false;
        }
        if (!Objects.equals(this.cedula, other.cedula)) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.apellidoPat, other.apellidoPat)) {
            return false;
        }
        if (!Objects.equals(this.apellidoMat, other.apellidoMat)) {
            return false;
        }
        if (!Objects.equals(this.telefono, other.telefono)) {
            return false;
        }
        if (!Objects.equals(this.correoElectronico, other.correoElectronico)) {
            return false;
        }
        if (!Objects.equals(this.calle, other.calle)) {
            return false;
        }
        if (!Objects.equals(this.numeroExt, other.numeroExt)) {
            return false;
        }
        if (!Objects.equals(this.numeroInt, other.numeroInt)) {
            return false;
        }
        if (!Objects.equals(this.colonia, other.colonia)) {
            return false;
        }
        if (!Objects.equals(this.localidad, other.localidad)) {
            return false;
        }
        if (!Objects.equals(this.municipio, other.municipio)) {
            return false;
        }
        if (!Objects.equals(this.estado, other.estado)) {
            return false;
        }
        if (!Objects.equals(this.pais, other.pais)) {
            return false;
        }
        if (!Objects.equals(this.cp, other.cp)) {
            return false;
        }
        if (!Objects.equals(this.riesgo, other.riesgo)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.fecha, other.fecha)) {
            return false;
        }
        if (!Objects.equals(this.idEstatusReaccion, other.idEstatusReaccion)) {
            return false;
        }
        if (!Objects.equals(this.fechaNacimiento, other.fechaNacimiento)) {
            return false;
        }
        if (!Objects.equals(this.edad, other.edad)) {
            return false;
        }
        if (!Objects.equals(this.sexo, other.sexo)) {
            return false;
        }
        if (!Objects.equals(this.peso, other.peso)) {
            return false;
        }
        if (!Objects.equals(this.estatura, other.estatura)) {
            return false;
        }
        if (!Objects.equals(this.fechaInicioSospecha, other.fechaInicioSospecha)) {
            return false;
        }
        if (!Objects.equals(this.idTipoReaccion, other.idTipoReaccion)) {
            return false;
        }
        if (!Objects.equals(this.idConsecuencia, other.idConsecuencia)) {
            return false;
        }
        if (!Objects.equals(this.idSustanciaActiva, other.idSustanciaActiva)) {
            return false;
        }
        if (!Objects.equals(this.idViaAdministracion, other.idViaAdministracion)) {
            return false;
        }
        if (!Objects.equals(this.caducidad, other.caducidad)) {
            return false;
        }
        if (!Objects.equals(this.dosis, other.dosis)) {
            return false;
        }
        if (!Objects.equals(this.idUnidad, other.idUnidad)) {
            return false;
        }
        if (!Objects.equals(this.duracion, other.duracion)) {
            return false;
        }
        if (!Objects.equals(this.inicioAdministracion, other.inicioAdministracion)) {
            return false;
        }
        if (!Objects.equals(this.finAdministracion, other.finAdministracion)) {
            return false;
        }
        if (!Objects.equals(this.medicamentoSuspendido, other.medicamentoSuspendido)) {
            return false;
        }
        if (!Objects.equals(this.desaparecioReaccion, other.desaparecioReaccion)) {
            return false;
        }
        if (!Objects.equals(this.disminuyoDosis, other.disminuyoDosis)) {
            return false;
        }
        if (!Objects.equals(this.cuanto, other.cuanto)) {
            return false;
        }
        if (!Objects.equals(this.cambioFarmacoTerapia, other.cambioFarmacoTerapia)) {
            return false;
        }
        if (!Objects.equals(this.reaparecioReaccionReadministrar, other.reaparecioReaccionReadministrar)) {
            return false;
        }
        if (!Objects.equals(this.persistioReaccion, other.persistioReaccion)) {
            return false;
        }
        if (!Objects.equals(this.idTipoInformeLab, other.idTipoInformeLab)) {
            return false;
        }
        if (!Objects.equals(this.idTipoInformeProf, other.idTipoInformeProf)) {
            return false;
        }
        if (!Objects.equals(this.publicarInformacion, other.publicarInformacion)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        if (!Objects.equals(this.updateFecha, other.updateFecha)) {
            return false;
        }
        if (!Objects.equals(this.insumos, other.insumos)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Reaccion{" + "idReaccion=" + idReaccion + ", folio=" + folio + ", numeroNotificacion=" + numeroNotificacion + ", fecha=" + fecha + ", idEstatusReaccion=" + idEstatusReaccion + ", estatusReaccion=" + estatusReaccion + ", rfcPaciente=" + rfcPaciente + ", curpPaciente=" + curpPaciente + ", idPaciente=" + idPaciente + ", numeroPaciente=" + numeroPaciente + ", nombrePaciente=" + nombrePaciente + ", fechaNacimiento=" + fechaNacimiento + ", edad=" + edad + ", sexo=" + sexo + ", peso=" + peso + ", estatura=" + estatura + ", fechaInicioSospecha=" + fechaInicioSospecha + ", idTipoReaccion=" + idTipoReaccion + ", tipoReaccion=" + tipoReaccion + ", idConsecuencia=" + idConsecuencia + ", consecuencia=" + consecuencia + ", descripcion=" + descripcion + ", idInsumo=" + idInsumo + ", medicamento=" + medicamento + ", clave=" + clave + ", idSustanciaActiva=" + idSustanciaActiva + ", sustanciaActiva=" + sustanciaActiva + ", idViaAdministracion=" + idViaAdministracion + ", viaAdministracion=" + viaAdministracion + ", lote=" + lote + ", caducidad=" + caducidad + ", laboratorio=" + laboratorio + ", denomincacionDistintiva=" + denomincacionDistintiva + ", dosis=" + dosis + ", idUnidad=" + idUnidad + ", unidad=" + unidad + ", frecuencia=" + frecuencia + ", duracion=" + duracion + ", motivoPrescripcion=" + motivoPrescripcion + ", inicioAdministracion=" + inicioAdministracion + ", finAdministracion=" + finAdministracion + ", medicamentoSuspendido=" + medicamentoSuspendido + ", desaparecioReaccion=" + desaparecioReaccion + ", disminuyoDosis=" + disminuyoDosis + ", cuanto=" + cuanto + ", cambioFarmacoTerapia=" + cambioFarmacoTerapia + ", cual=" + cual + ", reaparecioReaccionReadministrar=" + reaparecioReaccionReadministrar + ", persistioReaccion=" + persistioReaccion + ", historiaClinica=" + historiaClinica + ", idTipoInformeLab=" + idTipoInformeLab + ", tipoInformeLab=" + tipoInformeLab + ", idOrigenLab=" + idOrigenLab + ", origenLab=" + origenLab + ", idTipoInformeProf=" + idTipoInformeProf + ", tipoInformeProf=" + tipoInformeProf + ", idOrigenProf=" + idOrigenProf + ", origenProf=" + origenProf + ", rfcInformante=" + rfcInformante + ", curpInformante=" + curpInformante + ", numeroInformante=" + numeroInformante + ", cedula=" + cedula + ", nombre=" + nombre + ", apellidoPat=" + apellidoPat + ", apellidoMat=" + apellidoMat + ", telefono=" + telefono + ", correoElectronico=" + correoElectronico + ", calle=" + calle + ", numeroExt=" + numeroExt + ", numeroInt=" + numeroInt + ", colonia=" + colonia + ", localidad=" + localidad + ", municipio=" + municipio + ", estado=" + estado + ", pais=" + pais + ", cp=" + cp + ", publicarInformacion=" + publicarInformacion + ",riesgo=" + riesgo + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + ", insumos=" + insumos + '}';
    }

    

    public String getIdReaccion() {
        return idReaccion;
    }

    public void setIdReaccion(String idReaccion) {
        this.idReaccion = idReaccion;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getNumeroNotificacion() {
        return numeroNotificacion;
    }

    public void setNumeroNotificacion(String numeroNotificacion) {
        this.numeroNotificacion = numeroNotificacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getIdEstatusReaccion() {
        return idEstatusReaccion;
    }

    public void setIdEstatusReaccion(Integer idEstatusReaccion) {
        this.idEstatusReaccion = idEstatusReaccion;
    }

    public String getEstatusReaccion() {
        return estatusReaccion;
    }

    public void setEstatusReaccion(String estatusReaccion) {
        this.estatusReaccion = estatusReaccion;
    }

    public String getRfcPaciente() {
        return rfcPaciente;
    }

    public void setRfcPaciente(String rfcPaciente) {
        this.rfcPaciente = rfcPaciente;
    }

    public String getCurpPaciente() {
        return curpPaciente;
    }

    public void setCurpPaciente(String curpPaciente) {
        this.curpPaciente = curpPaciente;
    }

    public String getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(String idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getNumeroPaciente() {
        return numeroPaciente;
    }

    public void setNumeroPaciente(String numeroPaciente) {
        this.numeroPaciente = numeroPaciente;
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

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Integer getSexo() {
        return sexo;
    }

    public void setSexo(Integer sexo) {
        this.sexo = sexo;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getEstatura() {
        return estatura;
    }

    public void setEstatura(Double estatura) {
        this.estatura = estatura;
    }

    public Date getFechaInicioSospecha() {
        return fechaInicioSospecha;
    }

    public void setFechaInicioSospecha(Date fechaInicioSospecha) {
        this.fechaInicioSospecha = fechaInicioSospecha;
    }

    public Integer getIdTipoReaccion() {
        return idTipoReaccion;
    }

    public void setIdTipoReaccion(Integer idTipoReaccion) {
        this.idTipoReaccion = idTipoReaccion;
    }

    public String getTipoReaccion() {
        return tipoReaccion;
    }

    public void setTipoReaccion(String tipoReaccion) {
        this.tipoReaccion = tipoReaccion;
    }

    public Integer getIdConsecuencia() {
        return idConsecuencia;
    }

    public void setIdConsecuencia(Integer idConsecuencia) {
        this.idConsecuencia = idConsecuencia;
    }

    public String getConsecuencia() {
        return consecuencia;
    }

    public void setConsecuencia(String consecuencia) {
        this.consecuencia = consecuencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(String idInsumo) {
        this.idInsumo = idInsumo;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Integer getIdSustanciaActiva() {
        return idSustanciaActiva;
    }

    public void setIdSustanciaActiva(Integer idSustanciaActiva) {
        this.idSustanciaActiva = idSustanciaActiva;
    }

    public String getSustanciaActiva() {
        return sustanciaActiva;
    }

    public void setSustanciaActiva(String sustanciaActiva) {
        this.sustanciaActiva = sustanciaActiva;
    }

    public Integer getIdViaAdministracion() {
        return idViaAdministracion;
    }

    public void setIdViaAdministracion(Integer idViaAdministracion) {
        this.idViaAdministracion = idViaAdministracion;
    }

    public String getViaAdministracion() {
        return viaAdministracion;
    }

    public void setViaAdministracion(String viaAdministracion) {
        this.viaAdministracion = viaAdministracion;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public Date getCaducidad() {
        return caducidad;
    }

    public void setCaducidad(Date caducidad) {
        this.caducidad = caducidad;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    public String getDenomincacionDistintiva() {
        return denomincacionDistintiva;
    }

    public void setDenomincacionDistintiva(String denomincacionDistintiva) {
        this.denomincacionDistintiva = denomincacionDistintiva;
    }

    public Double getDosis() {
        return dosis;
    }

    public void setDosis(Double dosis) {
        this.dosis = dosis;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public String getMotivoPrescripcion() {
        return motivoPrescripcion;
    }

    public void setMotivoPrescripcion(String motivoPrescripcion) {
        this.motivoPrescripcion = motivoPrescripcion;
    }

    public Date getInicioAdministracion() {
        return inicioAdministracion;
    }

    public void setInicioAdministracion(Date inicioAdministracion) {
        this.inicioAdministracion = inicioAdministracion;
    }

    public Date getFinAdministracion() {
        return finAdministracion;
    }

    public void setFinAdministracion(Date finAdministracion) {
        this.finAdministracion = finAdministracion;
    }

    public Integer getMedicamentoSuspendido() {
        return medicamentoSuspendido;
    }

    public void setMedicamentoSuspendido(Integer medicamentoSuspendido) {
        this.medicamentoSuspendido = medicamentoSuspendido;
    }

    public Integer getDesaparecioReaccion() {
        return desaparecioReaccion;
    }

    public void setDesaparecioReaccion(Integer desaparecioReaccion) {
        this.desaparecioReaccion = desaparecioReaccion;
    }

    public Integer getDisminuyoDosis() {
        return disminuyoDosis;
    }

    public void setDisminuyoDosis(Integer disminuyoDosis) {
        this.disminuyoDosis = disminuyoDosis;
    }

    public Double getCuanto() {
        return cuanto;
    }

    public void setCuanto(Double cuanto) {
        this.cuanto = cuanto;
    }

    public Integer getCambioFarmacoTerapia() {
        return cambioFarmacoTerapia;
    }

    public void setCambioFarmacoTerapia(Integer cambioFarmacoTerapia) {
        this.cambioFarmacoTerapia = cambioFarmacoTerapia;
    }

    public String getCual() {
        return cual;
    }

    public void setCual(String cual) {
        this.cual = cual;
    }

    public Integer getReaparecioReaccionReadministrar() {
        return reaparecioReaccionReadministrar;
    }

    public void setReaparecioReaccionReadministrar(Integer reaparecioReaccionReadministrar) {
        this.reaparecioReaccionReadministrar = reaparecioReaccionReadministrar;
    }

    public Integer getPersistioReaccion() {
        return persistioReaccion;
    }

    public void setPersistioReaccion(Integer persistioReaccion) {
        this.persistioReaccion = persistioReaccion;
    }

    public String getHistoriaClinica() {
        return historiaClinica;
    }

    public void setHistoriaClinica(String historiaClinica) {
        this.historiaClinica = historiaClinica;
    }

    public Integer getIdTipoInformeLab() {
        return idTipoInformeLab;
    }

    public void setIdTipoInformeLab(Integer idTipoInformeLab) {
        this.idTipoInformeLab = idTipoInformeLab;
    }

    public String getTipoInformeLab() {
        return tipoInformeLab;
    }

    public void setTipoInformeLab(String tipoInformeLab) {
        this.tipoInformeLab = tipoInformeLab;
    }

    public String getOrigenLab() {
        return origenLab;
    }

    public void setOrigenLab(String origenLab) {
        this.origenLab = origenLab;
    }

    public Integer getIdTipoInformeProf() {
        return idTipoInformeProf;
    }

    public void setIdTipoInformeProf(Integer idTipoInformeProf) {
        this.idTipoInformeProf = idTipoInformeProf;
    }

    public String getTipoInformeProf() {
        return tipoInformeProf;
    }

    public void setTipoInformeProf(String tipoInformeProf) {
        this.tipoInformeProf = tipoInformeProf;
    }

    public String getOrigenProf() {
        return origenProf;
    }

    public void setOrigenProf(String origenProf) {
        this.origenProf = origenProf;
    }

    public String getRfcInformante() {
        return rfcInformante;
    }

    public void setRfcInformante(String rfcInformante) {
        this.rfcInformante = rfcInformante;
    }

    public String getCurpInformante() {
        return curpInformante;
    }

    public void setCurpInformante(String curpInformante) {
        this.curpInformante = curpInformante;
    }

    public String getNumeroInformante() {
        return numeroInformante;
    }

    public void setNumeroInformante(String numeroInformante) {
        this.numeroInformante = numeroInformante;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPat() {
        return apellidoPat;
    }

    public void setApellidoPat(String apellidoPat) {
        this.apellidoPat = apellidoPat;
    }

    public String getApellidoMat() {
        return apellidoMat;
    }

    public void setApellidoMat(String apellidoMat) {
        this.apellidoMat = apellidoMat;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumeroExt() {
        return numeroExt;
    }

    public void setNumeroExt(String numeroExt) {
        this.numeroExt = numeroExt;
    }

    public String getNumeroInt() {
        return numeroInt;
    }

    public void setNumeroInt(String numeroInt) {
        this.numeroInt = numeroInt;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
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

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public Integer getPublicarInformacion() {
        return publicarInformacion;
    }

    public void setPublicarInformacion(Integer publicarInformacion) {
        this.publicarInformacion = publicarInformacion;
    }

    public String getRiesgo() {
        return riesgo;
    }

    public void setRiesgo(String riesgo) {
        this.riesgo = riesgo;
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

    public List<MedicamentoConcomitante> getInsumos() {
        return insumos;
    }

    public void setInsumos(List<MedicamentoConcomitante> insumos) {
        this.insumos = insumos;
    }

    public Integer getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(Integer idUnidad) {
        this.idUnidad = idUnidad;
    }

    public Integer getIdOrigenLab() {
        return idOrigenLab;
    }

    public void setIdOrigenLab(Integer idOrigenLab) {
        this.idOrigenLab = idOrigenLab;
    }

    public Integer getIdOrigenProf() {
        return idOrigenProf;
    }

    public void setIdOrigenProf(Integer idOrigenProf) {
        this.idOrigenProf = idOrigenProf;
    }

    
}
