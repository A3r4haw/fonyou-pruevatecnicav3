/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author gcruz
 */
public class NutricionParenteralDetalle implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idNutricionParenteralDetalle;
    private String idNutricionParenteral;
    private String idMedicamento;
    private BigDecimal volPrescrito;
    private BigDecimal cantCalculada;
    private BigDecimal porcentajeConcentracion;
    private BigDecimal porcentajeCalculado;
    private BigDecimal osmolaridad;
    private BigDecimal densidad;
    private BigDecimal peso;
    private BigDecimal calorias;
    private BigDecimal porcentajeEnergia;
    private BigDecimal nitrogeno;
    private BigDecimal calculoOsmolaridad;
    private Integer remanente;
    private Integer numPiezas;
    private String idInventario;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;

    private Double cantPorEnvase;
    private Double Dosis;
    private boolean tempAmbiente;
    private boolean refrigeracion;
    private boolean fotosensible;
    private boolean diluyente;
    private BigDecimal ordenAdicion;
    private BigDecimal ordenEtiqueta;
    private BigDecimal sobrellenado;

    public NutricionParenteralDetalle() {

    }

    public NutricionParenteralDetalle(String idNutricionParenteralDetalle) {
        this.idNutricionParenteralDetalle = idNutricionParenteralDetalle;
    }

    public NutricionParenteralDetalle(String idNutricionParenteralDetalle, String idNutricionParenteral) {
        this.idNutricionParenteralDetalle = idNutricionParenteralDetalle;
        this.idNutricionParenteral = idNutricionParenteral;
    }

    /*public NutricionParenteralDetalle(String idMedicamento, String claveMedicamento, String nombreCorto, String nombreLargo, BigDecimal concentracion, String nombrePresentacion, String viaAdministracion, BigDecimal osmolaridad, BigDecimal densidad, boolean calculoMezcla) {
        this.idMedicamento = idMedicamento;
        this.claveMedicamento = claveMedicamento;
        this.nombreCorto = nombreCorto;
        this.nombreLargo = nombreLargo;
        this.concentracion = concentracion;
        this.nombrePresentacion = nombrePresentacion;
        this.viaAdministracion = viaAdministracion;        
        this.osmolaridad = osmolaridad;
        this.densidad = densidad;   
        this.calculoMezcla = calculoMezcla;
    }*/
    public NutricionParenteralDetalle(String idNutricionParenteralDetalle, String idNutricionParenteral, String idMedicamento, BigDecimal volPrescrito, BigDecimal cantCalculada, BigDecimal porcentajeConcentracion, BigDecimal porcentajeCalculado, BigDecimal osmolaridad, BigDecimal densidad, BigDecimal peso, BigDecimal calorias, BigDecimal porcentajeEnergia, BigDecimal nitrogeno, BigDecimal calculoOsmolaridad, Integer remanente, Integer numPiezas, String idInventario, Date insertFecha, String insertIdUsuario, Date updateFecha, String updateIdUsuario) {
        this.idNutricionParenteralDetalle = idNutricionParenteralDetalle;
        this.idNutricionParenteral = idNutricionParenteral;
        this.idMedicamento = idMedicamento;
        this.volPrescrito = volPrescrito;
        this.cantCalculada = cantCalculada;
        this.porcentajeConcentracion = porcentajeConcentracion;
        this.porcentajeCalculado = porcentajeCalculado;
        this.osmolaridad = osmolaridad;
        this.densidad = densidad;
        this.peso = peso;
        this.calorias = calorias;
        this.porcentajeEnergia = porcentajeEnergia;
        this.nitrogeno = nitrogeno;
        this.calculoOsmolaridad = calculoOsmolaridad;
        this.remanente = remanente;
        this.numPiezas = numPiezas;
        this.idInventario = idInventario;
        this.insertFecha = insertFecha;
        this.insertIdUsuario = insertIdUsuario;
        this.updateFecha = updateFecha;
        this.updateIdUsuario = updateIdUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.idNutricionParenteralDetalle);
        hash = 89 * hash + Objects.hashCode(this.idNutricionParenteral);
        hash = 89 * hash + Objects.hashCode(this.idMedicamento);
        hash = 89 * hash + Objects.hashCode(this.volPrescrito);
        hash = 89 * hash + Objects.hashCode(this.cantCalculada);
        hash = 89 * hash + Objects.hashCode(this.porcentajeConcentracion);
        hash = 89 * hash + Objects.hashCode(this.porcentajeCalculado);
        hash = 89 * hash + Objects.hashCode(this.osmolaridad);
        hash = 89 * hash + Objects.hashCode(this.densidad);
        hash = 89 * hash + Objects.hashCode(this.peso);
        hash = 89 * hash + Objects.hashCode(this.calorias);
        hash = 89 * hash + Objects.hashCode(this.porcentajeEnergia);
        hash = 89 * hash + Objects.hashCode(this.nitrogeno);
        hash = 89 * hash + Objects.hashCode(this.calculoOsmolaridad);
        hash = 89 * hash + Objects.hashCode(this.remanente);
        hash = 89 * hash + Objects.hashCode(this.numPiezas);
        hash = 53 * hash + Objects.hashCode(this.idInventario);
        hash = 89 * hash + Objects.hashCode(this.insertFecha);
        hash = 89 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 89 * hash + Objects.hashCode(this.updateFecha);
        hash = 89 * hash + Objects.hashCode(this.updateIdUsuario);
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
        final NutricionParenteralDetalle other = (NutricionParenteralDetalle) obj;
        if (!Objects.equals(this.idNutricionParenteralDetalle, other.idNutricionParenteralDetalle)) {
            return false;
        }
        if (!Objects.equals(this.idNutricionParenteral, other.idNutricionParenteral)) {
            return false;
        }
        if (!Objects.equals(this.idMedicamento, other.idMedicamento)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.volPrescrito, other.volPrescrito)) {
            return false;
        }
        if (!Objects.equals(this.cantCalculada, other.cantCalculada)) {
            return false;
        }
        if (!Objects.equals(this.porcentajeConcentracion, other.porcentajeConcentracion)) {
            return false;
        }
        if (!Objects.equals(this.porcentajeCalculado, other.porcentajeCalculado)) {
            return false;
        }
        if (!Objects.equals(this.osmolaridad, other.osmolaridad)) {
            return false;
        }
        if (!Objects.equals(this.densidad, other.densidad)) {
            return false;
        }
        if (!Objects.equals(this.peso, other.peso)) {
            return false;
        }
        if (!Objects.equals(this.calorias, other.calorias)) {
            return false;
        }
        if (!Objects.equals(this.porcentajeEnergia, other.porcentajeEnergia)) {
            return false;
        }
        if (!Objects.equals(this.nitrogeno, other.nitrogeno)) {
            return false;
        }
        if (!Objects.equals(this.calculoOsmolaridad, other.calculoOsmolaridad)) {
            return false;
        }
        if (!Objects.equals(this.remanente, other.remanente)) {
            return false;
        }
        if (!Objects.equals(this.numPiezas, other.numPiezas)) {
            return false;
        }
        if (!Objects.equals(this.idInventario, other.idInventario)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        if (!Objects.equals(this.updateFecha, other.updateFecha)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "NutricionParenteralDetalle{" + "idNutricionParenteralDetalle=" + idNutricionParenteralDetalle + ", idNutricionParenteral=" + idNutricionParenteral + ", idMedicamento=" + idMedicamento + ", volPrescrito=" + volPrescrito + ", cantCalculada=" + cantCalculada + ", porcentajeConcentracion=" + porcentajeConcentracion + ", porcentajeCalculado=" + porcentajeCalculado + ", osmolaridad=" + osmolaridad + ", densidad=" + densidad + ", peso=" + peso + ", calorias=" + calorias + ", porcentajeEnergia=" + porcentajeEnergia + ", nitrogeno=" + nitrogeno + ", calculoOsmolaridad=" + calculoOsmolaridad + ", remanente=" + remanente + ", numPiezas=" + numPiezas + ", idInventario=" + idInventario + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + '}';
    }

    public String getIdNutricionParenteralDetalle() {
        return idNutricionParenteralDetalle;
    }

    public void setIdNutricionParenteralDetalle(String idNutricionParenteralDetalle) {
        this.idNutricionParenteralDetalle = idNutricionParenteralDetalle;
    }

    public String getIdNutricionParenteral() {
        return idNutricionParenteral;
    }

    public void setIdNutricionParenteral(String idNutricionParenteral) {
        this.idNutricionParenteral = idNutricionParenteral;
    }

    public String getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(String idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public BigDecimal getVolPrescrito() {
        return volPrescrito;
    }

    public void setVolPrescrito(BigDecimal volPrescrito) {
        this.volPrescrito = volPrescrito;
    }

    public BigDecimal getCantCalculada() {
        return cantCalculada;
    }

    public void setCantCalculada(BigDecimal cantCalculada) {
        this.cantCalculada = cantCalculada;
    }

    public BigDecimal getPorcentajeConcentracion() {
        return porcentajeConcentracion;
    }

    public void setPorcentajeConcentracion(BigDecimal porcentajeConcentracion) {
        this.porcentajeConcentracion = porcentajeConcentracion;
    }

    public BigDecimal getPorcentajeCalculado() {
        return porcentajeCalculado;
    }

    public void setPorcentajeCalculado(BigDecimal porcentajeCalculado) {
        this.porcentajeCalculado = porcentajeCalculado;
    }

    public BigDecimal getOsmolaridad() {
        return osmolaridad;
    }

    public void setOsmolaridad(BigDecimal osmolaridad) {
        this.osmolaridad = osmolaridad;
    }

    public BigDecimal getDensidad() {
        return densidad;
    }

    public void setDensidad(BigDecimal densidad) {
        this.densidad = densidad;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public BigDecimal getCalorias() {
        return calorias;
    }

    public void setCalorias(BigDecimal calorias) {
        this.calorias = calorias;
    }

    public BigDecimal getPorcentajeEnergia() {
        return porcentajeEnergia;
    }

    public void setPorcentajeEnergia(BigDecimal porcentajeEnergia) {
        this.porcentajeEnergia = porcentajeEnergia;
    }

    public BigDecimal getNitrogeno() {
        return nitrogeno;
    }

    public void setNitrogeno(BigDecimal nitrogeno) {
        this.nitrogeno = nitrogeno;
    }

    public BigDecimal getCalculoOsmolaridad() {
        return calculoOsmolaridad;
    }

    public void setCalculoOsmolaridad(BigDecimal calculoOsmolaridad) {
        this.calculoOsmolaridad = calculoOsmolaridad;
    }

    public Integer getRemanente() {
        return remanente;
    }

    public void setRemanente(Integer remanente) {
        this.remanente = remanente;
    }

    public Integer getNumPiezas() {
        return numPiezas;
    }

    public void setNumPiezas(Integer numPiezas) {
        this.numPiezas = numPiezas;
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

    public String getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(String idInventario) {
        this.idInventario = idInventario;
    }

    public Double getCantPorEnvase() {
        return cantPorEnvase;
    }

    public void setCantPorEnvase(Double cantPorEnvase) {
        this.cantPorEnvase = cantPorEnvase;
    }

    public Double getDosis() {
        return Dosis;
    }

    public void setDosis(Double Dosis) {
        this.Dosis = Dosis;
    }

    public boolean isTempAmbiente() {
        return tempAmbiente;
    }

    public void setTempAmbiente(boolean tempAmbiente) {
        this.tempAmbiente = tempAmbiente;
    }

    public boolean isRefrigeracion() {
        return refrigeracion;
    }

    public void setRefrigeracion(boolean refrigeracion) {
        this.refrigeracion = refrigeracion;
    }

    public boolean isFotosensible() {
        return fotosensible;
    }

    public void setFotosensible(boolean fotosensible) {
        this.fotosensible = fotosensible;
    }

    public boolean isDiluyente() {
        return diluyente;
    }

    public void setDiluyente(boolean diluyente) {
        this.diluyente = diluyente;
    }

    public BigDecimal getOrdenAdicion() {
        return ordenAdicion;
    }

    public void setOrdenAdicion(BigDecimal ordenAdicion) {
        this.ordenAdicion = ordenAdicion;
    }

    public BigDecimal getOrdenEtiqueta() {
        return ordenEtiqueta;
    }

    public void setOrdenEtiqueta(BigDecimal ordenEtiqueta) {
        this.ordenEtiqueta = ordenEtiqueta;
    }

    public BigDecimal getSobrellenado() {
        return sobrellenado;
    }

    public void setSobrellenado(BigDecimal sobrellenado) {
        this.sobrellenado = sobrellenado;
    }

}
