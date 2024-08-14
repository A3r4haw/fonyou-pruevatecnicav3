package mx.mc.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author olozada
 */
public class ReporteTerapeutico implements Serializable {
    private static final long serialVersionUID = 1L;

    //Rango de Fechas  
    private Date fechaInicio;
    private Date fechaActual;
    
    //Datos Visita
    private String numVisita;
    private String paciente;
    private String idVisita;
    private String tipoVisita;
    
    //Datos Ingreso y Egreso
    private Date fechaIngreso;
    private Date fechaEgreso;
    
    //Datos de Servivion y Cama
    private String especialidad;
    private String nombreCama;
    
    //Datos del Medico y Prescripcion
    private String medico;
    private String folio;
    private String estausPrescripcion;
    
    //datos de Medicamento
    private String claveMedicamento;
    private String tipoMedicamento;
    private String nombreLargo;
    private String presentacion;
    private Integer cantidadSurtida;

    /**
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaActual
     */
    public Date getFechaActual() {
        return fechaActual;
    }

    /**
     * @param fechaActual the fechaActual to set
     */
    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    /**
     * @return the paciente
     */
    public String getPaciente() {
        return paciente;
    }

    /**
     * @param paciente the paciente to set
     */
    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    /**
     * @return the idVisita
     */
    public String getIdVisita() {
        return idVisita;
    }

    /**
     * @param idVisita the idVisita to set
     */
    public void setIdVisita(String idVisita) {
        this.idVisita = idVisita;
    }

    /**
     * @return the tipoVisita
     */
    public String getTipoVisita() {
        return tipoVisita;
    }

    /**
     * @param tipoVisita the tipoVisita to set
     */
    public void setTipoVisita(String tipoVisita) {
        this.tipoVisita = tipoVisita;
    }

    /**
     * @return the fechaIngreso
     */
    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    /**
     * @param fechaIngreso the fechaIngreso to set
     */
    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    /**
     * @return the fechaEgreso
     */
    public Date getFechaEgreso() {
        return fechaEgreso;
    }

    /**
     * @param fechaEgreso the fechaEgreso to set
     */
    public void setFechaEgreso(Date fechaEgreso) {
        this.fechaEgreso = fechaEgreso;
    }

    /**
     * @return the especialidad
     */
    public String getEspecialidad() {
        return especialidad;
    }

    /**
     * @param especialidad the especialidad to set
     */
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    /**
     * @return the nombreCama
     */
    public String getNombreCama() {
        return nombreCama;
    }

    /**
     * @param nombreCama the nombreCama to set
     */
    public void setNombreCama(String nombreCama) {
        this.nombreCama = nombreCama;
    }

    /**
     * @return the medico
     */
    public String getMedico() {
        return medico;
    }

    /**
     * @param medico the medico to set
     */
    public void setMedico(String medico) {
        this.medico = medico;
    }

    /**
     * @return the folio
     */
    public String getFolio() {
        return folio;
    }

    /**
     * @param folio the folio to set
     */
    public void setFolio(String folio) {
        this.folio = folio;
    }

    /**
     * @return the estausPrescripcion
     */
    public String getEstausPrescripcion() {
        return estausPrescripcion;
    }

    /**
     * @param estausPrescripcion the estausPrescripcion to set
     */
    public void setEstausPrescripcion(String estausPrescripcion) {
        this.estausPrescripcion = estausPrescripcion;
    }

    /**
     * @return the claveMedicamento
     */
    public String getClaveMedicamento() {
        return claveMedicamento;
    }

    /**
     * @param claveMedicamento the claveMedicamento to set
     */
    public void setClaveMedicamento(String claveMedicamento) {
        this.claveMedicamento = claveMedicamento;
    }

    /**
     * @return the tipoMedicamento
     */
    public String getTipoMedicamento() {
        return tipoMedicamento;
    }

    /**
     * @param tipoMedicamento the tipoMedicamento to set
     */
    public void setTipoMedicamento(String tipoMedicamento) {
        this.tipoMedicamento = tipoMedicamento;
    }

    /**
     * @return the nombreLargo
     */
    public String getNombreLargo() {
        return nombreLargo;
    }

    /**
     * @param nombreLargo the nombreLargo to set
     */
    public void setNombreLargo(String nombreLargo) {
        this.nombreLargo = nombreLargo;
    }

    /**
     * @return the presentacion
     */
    public String getPresentacion() {
        return presentacion;
    }

    /**
     * @param presentacion the presentacion to set
     */
    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    /**
     * @return the cantidadSurtida
     */
    public Integer getCantidadSurtida() {
        return cantidadSurtida;
    }

    /**
     * @param cantidadSurtida the cantidadSurtida to set
     */
    public void setCantidadSurtida(Integer cantidadSurtida) {
        this.cantidadSurtida = cantidadSurtida;
    }

    /**
     * @return the numVisita
     */
    public String getNumVisita() {
        return numVisita;
    }

    /**
     * @param numVisita the numVisita to set
     */
    public void setNumVisita(String numVisita) {
        this.numVisita = numVisita;
    }

    
}
