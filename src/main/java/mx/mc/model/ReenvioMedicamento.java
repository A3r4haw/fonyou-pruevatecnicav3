/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

/**
 *
 * @author mcalderon
 */
public class ReenvioMedicamento extends SurtimientoEnviado {
    private static final long serialVersionUID = 1L;
    private String cama;
    private String numeroPaciente;
    private String paciente;
    private String apellidoPaterno;
    private String apellidoMaterno;            
    private String prescripcion;
    private String tipo;
    
    private int idTipoMotivoReenvio;
    private String motivo;
    private String estatus;
    private String idEstructuraAlmacen;
    private String idPrescripcion;
    private String idPrescripcionInsumo;

    public String getCama() {
        return cama;
    }

    public void setCama(String cama) {
        this.cama = cama;
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

    public String getPrescripcion() {
        return prescripcion;
    }

    public void setPrescripcion(String prescripcion) {
        this.prescripcion = prescripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getIdTipoMotivoReenvio() {
        return idTipoMotivoReenvio;
    }

    public void setIdTipoMotivoReenvio(int idTipoMotivoReenvio) {
        this.idTipoMotivoReenvio = idTipoMotivoReenvio;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getIdEstructuraAlmacen() {
        return idEstructuraAlmacen;
    }

    public void setIdEstructuraAlmacen(String idEstructuraAlmacen) {
        this.idEstructuraAlmacen = idEstructuraAlmacen;
    }

    public String getIdPrescripcion() {
        return idPrescripcion;
    }

    public void setIdPrescripcion(String idPrescripcion) {
        this.idPrescripcion = idPrescripcion;
    }

    public String getIdPrescripcionInsumo() {
        return idPrescripcionInsumo;
    }

    public void setIdPrescripcionInsumo(String idPrescripcionInsumo) {
        this.idPrescripcionInsumo = idPrescripcionInsumo;
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
}
