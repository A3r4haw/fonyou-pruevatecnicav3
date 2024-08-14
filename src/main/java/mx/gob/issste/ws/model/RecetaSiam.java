/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.issste.ws.model;

import java.io.Serializable;
import java.util.List;
import javax.json.bind.annotation.JsonbProperty;

/**
 *
 * @author Admin
 */
public class RecetaSiam  implements Serializable{

    private static final long serialVersionUID = 1L;
    
    private Integer id;
    private String folio;
    @JsonbProperty("clave_unidad_medica")
    private String claveUnidadMedica;
    @JsonbProperty("fecha_surtimiento")
    private String fechaSurtimiento;
    @JsonbProperty("id_tipo_movimiento")
    private Integer idTipoMovimiento;
    @JsonbProperty("usuario")
    private String usuario;
    @JsonbProperty("estatus")
    private String estatus;
    @JsonbProperty("insumos")
    private List<InsumoRecetaSiam> insumoRecetaList;
    private DerechoHabienteSiam derechoHabiente;
    private MedicoSiam medico;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getClaveUnidadMedica() {
        return claveUnidadMedica;
    }

    public void setClaveUnidadMedica(String claveUnidadMedica) {
        this.claveUnidadMedica = claveUnidadMedica;
    }

    public String getFechaSurtimiento() {
        return fechaSurtimiento;
    }

    public void setFechaSurtimiento(String fechaSurtimiento) {
        this.fechaSurtimiento = fechaSurtimiento;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public List<InsumoRecetaSiam> getInsumoRecetaList() {
        return insumoRecetaList;
    }

    public void setInsumoRecetaList(List<InsumoRecetaSiam> insumoRecetaList) {
        this.insumoRecetaList = insumoRecetaList;
    }

    public DerechoHabienteSiam getDerechoHabiente() {
        return derechoHabiente;
    }

    public void setDerechoHabiente(DerechoHabienteSiam derechoHabiente) {
        this.derechoHabiente = derechoHabiente;
    }

    public MedicoSiam getMedico() {
        return medico;
    }

    public void setMedico(MedicoSiam medico) {
        this.medico = medico;
    }

    public Integer getIdTipoMovimiento() {
        return idTipoMovimiento;
    }

    public void setIdTipoMovimiento(Integer idTipoMovimiento) {
        this.idTipoMovimiento = idTipoMovimiento;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }
    
    
}
