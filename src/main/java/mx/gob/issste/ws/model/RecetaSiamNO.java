/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.issste.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbProperty;
import java.io.Serializable;
import java.util.List;
/**
 *
 * @author mcalderon
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecetaSiamNO implements Serializable{

    private static final long serialVersionUID = 1L;
    
    private Integer id;

    
    private String folio;

    //@JsonbProperty("fecha")//"fecha": "2020-10-26T21:19:01.987Z",
    private String fecha;

    //@JsonbProperty("anio") //: 0,
    private Integer anio;

    @JsonbProperty("id_centro_trabajo") //: 0,
    private Integer idCentroTrabajo;

    @JsonbProperty("id_usuario") //: 0,
    private Integer idUsuario;

    @JsonbProperty("id_tipo_movimiento")//  : 0,
    private Integer idTipoMovimiento;

    //@JsonbProperty("tipo")//: "string",
    private String tipo;

    @JsonbProperty("nombre_genera")//: "string",
    private String nombreGenera;

    @JsonbProperty("id_medico") //: 0,
    private Integer idMedico;

    @JsonbProperty("clave_medico") //: "string",
    private String claveMedico;

    @JsonbProperty("nombre_medico")  //: "string",
    private String nombreMedico;

    @JsonbProperty("rfc_medico")  //: "string",
    private String rfcMedico;

    @JsonbProperty("id_dh")  //: 0,
    private Integer idDh;

    @JsonbProperty("curp_dh") //: "string",
    private String curpDh;

    @JsonbProperty("numissste_dh")  //: 0,
    private Integer numIsssteDh;

    @JsonbProperty("rfc_dh")  //: "string",
    private String rfcDh;

    @JsonbProperty("nombre_completo_dh")  //: "string",
    private String nombreCompletoDh;

    @JsonbProperty("id_indirecto_benef")  //: 0,
    private Integer idIndirectoBenef;

    @JsonbProperty("curp_benef")   //: "string",
    private String curpBenef;

    @JsonbProperty("nombre_completo_benef")  //: "string",
    private String nombreCompletoBenef;

    @JsonbProperty("parentesco_des")  //: "string",
    private String parentescoDes;

    //@JsonbProperty("apartados") //: 0,
    private Integer apartados;

    //@JsonbProperty("estatus") //: "string",
    private String estatus;

    @JsonbProperty("estatus_id")  //: 0,
    private Integer estatusID;

    @JsonbProperty("id_usuario_surte")  //: 0,
    private Integer idUsuarioSurte;

    @JsonbProperty("nombre_surte")  //: "string",
    private String nombreSurte;

    @JsonbProperty("fecha_cierre")  //: "2020-10-26T21:19:01.987Z",
    private String fechaCierre;

    @JsonbProperty("clave_ct")  //: "string",
    private String claveCt;

    @JsonbProperty("nombre_ct")  //: "string",
    private String nombreCt;

    @JsonbProperty("tipologia_ct")  //: "string",
    private String tipologiaCt;

    @JsonbProperty("tieneVale")  //: 0,
    private Integer tieneVale;

    @JsonbProperty("anio_descripcion)")  //: "string",
    private String anioDescripcion;
    
    @JsonbProperty("insumos")
    private List<InsumoRecetaSiam> insumoRecetaSiamList;

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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }
    
    public Integer getIdCentroTrabajo() {
        return idCentroTrabajo;
    }
    
    public void setIdCentroTrabajo(Integer idCentroTrabajo) {
        this.idCentroTrabajo = idCentroTrabajo;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdTipoMovimiento() {
        return idTipoMovimiento;
    }

    public void setIdTipoMovimiento(Integer idTipoMovimiento) {
        this.idTipoMovimiento = idTipoMovimiento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombreGenera() {
        return nombreGenera;
    }

    public void setNombreGenera(String nombreGenera) {
        this.nombreGenera = nombreGenera;
    }

    public Integer getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(Integer idMedico) {
        this.idMedico = idMedico;
    }

    public String getClaveMedico() {
        return claveMedico;
    }

    public void setClaveMedico(String claveMedico) {
        this.claveMedico = claveMedico;
    }

    public String getNombreMedico() {
        return nombreMedico;
    }

    public void setNombreMedico(String nombreMedico) {
        this.nombreMedico = nombreMedico;
    }

    public String getRfcMedico() {
        return rfcMedico;
    }

    public void setRfcMedico(String rfcMedico) {
        this.rfcMedico = rfcMedico;
    }

    public Integer getIdDh() {
        return idDh;
    }

    public void setIdDh(Integer idDh) {
        this.idDh = idDh;
    }

    public String getCurpDh() {
        return curpDh;
    }

    public void setCurpDh(String curpDh) {
        this.curpDh = curpDh;
    }

    public Integer getNumIsssteDh() {
        return numIsssteDh;
    }

    public void setNumIsssteDh(Integer numIsssteDh) {
        this.numIsssteDh = numIsssteDh;
    }

    public String getRfcDh() {
        return rfcDh;
    }

    public void setRfcDh(String rfcDh) {
        this.rfcDh = rfcDh;
    }

    public String getNombreCompletoDh() {
        return nombreCompletoDh;
    }

    public void setNombreCompletoDh(String nombreCompletoDh) {
        this.nombreCompletoDh = nombreCompletoDh;
    }

    public Integer getIdIndirectoBenef() {
        return idIndirectoBenef;
    }

    public void setIdIndirectoBenef(Integer idIndirectoBenef) {
        this.idIndirectoBenef = idIndirectoBenef;
    }

    public String getCurpBenef() {
        return curpBenef;
    }

    public void setCurpBenef(String curpBenef) {
        this.curpBenef = curpBenef;
    }

    public String getNombreCompletoBenef() {
        return nombreCompletoBenef;
    }

    public void setNombreCompletoBenef(String nombreCompletoBenef) {
        this.nombreCompletoBenef = nombreCompletoBenef;
    }

    public String getParentescoDes() {
        return parentescoDes;
    }

    public void setParentescoDes(String parentescoDes) {
        this.parentescoDes = parentescoDes;
    }

    public Integer getApartados() {
        return apartados;
    }

    public void setApartados(Integer apartados) {
        this.apartados = apartados;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public Integer getEstatusID() {
        return estatusID;
    }

    public void setEstatusID(Integer estatusID) {
        this.estatusID = estatusID;
    }

    public Integer getIdUsuarioSurte() {
        return idUsuarioSurte;
    }

    public void setIdUsuarioSurte(Integer idUsuarioSurte) {
        this.idUsuarioSurte = idUsuarioSurte;
    }

    public String getNombreSurte() {
        return nombreSurte;
    }

    public void setNombreSurte(String nombreSurte) {
        this.nombreSurte = nombreSurte;
    }

    public String getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(String fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public String getClaveCt() {
        return claveCt;
    }

    public void setClaveCt(String claveCt) {
        this.claveCt = claveCt;
    }

    public String getNombreCt() {
        return nombreCt;
    }

    public void setNombreCt(String nombreCt) {
        this.nombreCt = nombreCt;
    }

    public String getTipologiaCt() {
        return tipologiaCt;
    }

    public void setTipologiaCt(String tipologiaCt) {
        this.tipologiaCt = tipologiaCt;
    }

    public Integer getTieneVale() {
        return tieneVale;
    }

    public void setTieneVale(Integer tieneVale) {
        this.tieneVale = tieneVale;
    }

    public String getAnioDescripcion() {
        return anioDescripcion;
    }

    public void setAnioDescripcion(String anioDescripcion) {
        this.anioDescripcion = anioDescripcion;
    }

    public List<InsumoRecetaSiam> getInsumoRecetaSiamList() {
        return insumoRecetaSiamList;
    }

    public void setInsumoRecetaSiamList(List<InsumoRecetaSiam> insumoRecetaSiamList) {
        this.insumoRecetaSiamList = insumoRecetaSiamList;
    }

    @Override
    public String toString() {
        return "RecetaSiamNO{" + "id=" + id + ", folio=" + folio + ", fecha=" + fecha + ", anio=" + anio + ", idCentroTrabajo=" + idCentroTrabajo + ", idUsuario=" + idUsuario + ", idTipoMovimiento=" + idTipoMovimiento + ", tipo=" + tipo + ", nombreGenera=" + nombreGenera + ", idMedico=" + idMedico + ", claveMedico=" + claveMedico + ", nombreMedico=" + nombreMedico + ", rfcMedico=" + rfcMedico + ", idDh=" + idDh + ", curpDh=" + curpDh + ", numIsssteDh=" + numIsssteDh + ", rfcDh=" + rfcDh + ", nombreCompletoDh=" + nombreCompletoDh + ", idIndirectoBenef=" + idIndirectoBenef + ", curpBenef=" + curpBenef + ", nombreCompletoBenef=" + nombreCompletoBenef + ", parentescoDes=" + parentescoDes + ", apartados=" + apartados + ", estatus=" + estatus + ", estatusID=" + estatusID + ", idUsuarioSurte=" + idUsuarioSurte + ", nombreSurte=" + nombreSurte + ", fechaCierre=" + fechaCierre + ", claveCt=" + claveCt + ", nombreCt=" + nombreCt + ", tipologiaCt=" + tipologiaCt + ", tieneVale=" + tieneVale + ", anioDescripcion=" + anioDescripcion + ", insumoRecetaSiamList=" + insumoRecetaSiamList + '}';
    }    
    
}
