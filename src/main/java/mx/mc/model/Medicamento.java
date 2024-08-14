/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 *
 * @author bbautista
 */
public class Medicamento implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idMedicamento;
    @Size(min = 5, max = 20, message = "Clave Institucional: Debe introducir un valor entre 5 y 20 caracteres")
    @NotNull(message = "Clave Institucional: Debe introducir un valor")
    private String claveInstitucional;
    @NotNull(message = "Sustancia Activa: Debe introducir un valor")
    private Integer sustanciaActiva;
    @NotNull(message = "Via de Administración: Debe introducir un valor")
    private Integer idViaAdministracion;
    @NotNull(message = "Nombre Corto: Debe introducir un valor")
    private String nombreCorto;
    @NotNull(message = "Nombre Largo: Debe introducir un valor")
    private String nombreLargo;
    @NotNull(message = "Concentración: Debe introducir un valor")
    private BigDecimal concentracion;
    @NotNull(message = "Unidad Concentración: Debe introducir un valor")
    private Integer idUnidadConcentracion;
    private String presentacionLaboratorio;
    private String laboratorio;
    @NotNull(message = "Presentación Entrada: Debe introducir un valor")
    private Integer idPresentacionEntrada;
    @Digits(integer = 3, fraction = 0)
    @NotNull(message = "Factor de Transformación: Debe introducir un valor")
    private Integer factorTransformacion;
    @NotNull(message = "Presentación Salida: Debe introducir un valor")
    private Integer idPresentacionSalida;
    @NotNull(message = "Categoria: Debe introducir un valor")
    private Integer idCategoria;
    @NotNull(message = "Subcategoria: Debe introducir un valor")
    private Integer idSubcategoria;
    private String grupo;
    private boolean indivisible;
    private byte[] imagenPresentacion;
    private String nameImage;
    private Integer cuadroBasico;
    private Integer activo;
    private Integer tipo;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;
    private String idLocalidadAVG;
    private Integer tipoMedicacion;
    private Integer refrigeracion;
    private String ubicacion;
    private boolean medicamentoControlado;
    private boolean estatusRefrigeracion;
    private String codigoBarrasAlterno;
    private String claveAlterna;
    private Integer mezclaParental;
    private boolean parental;
    private boolean calculoMezcla;
    private Double osmolaridad;
    private Double densidad;
    private Double calorias;
    private Integer noHorasEstabilidad;
    private Double cantPorEnvase;
    private Integer idUnidadMedida;
    private boolean generaRemanente;

    private boolean incluyeDiluyente;
    private boolean requiereDiluyente;
    private boolean diluyente;
    private boolean fotosensible;
    private boolean tempAmbiente;
    private boolean tempRefrigeracion;
    private boolean noAgitar;

    private Integer noHorasEstabilidadAbierto;
    private Double presentacion;
    private boolean contenedor;
    private Integer ordePreparacion;
    private Double dosisPorMl;
    private Integer idUnidadMedidaDosis;
    private boolean calcularNCA;
    private BigDecimal ordenAdicion;
    private BigDecimal ordenEtiqueta;
    private boolean esAgua;
    private boolean autorizar;
    private Integer tipoCalorias;
    
    public Medicamento() {
        claveInstitucional = "";
        sustanciaActiva = 0;
        idViaAdministracion = 0;
        nombreCorto = "";
        nombreLargo = "";
        concentracion = BigDecimal.ZERO;
        idUnidadConcentracion = 0;
        idPresentacionEntrada = 0;
        factorTransformacion = 0;
        idPresentacionSalida = 0;
        idCategoria = 0;
        idSubcategoria = 0;
    }

    public Medicamento(String idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + Objects.hashCode(this.idMedicamento);
        hash = 43 * hash + Objects.hashCode(this.claveInstitucional);
        hash = 43 * hash + Objects.hashCode(this.sustanciaActiva);
        hash = 43 * hash + Objects.hashCode(this.idViaAdministracion);
        hash = 43 * hash + Objects.hashCode(this.nombreCorto);
        hash = 43 * hash + Objects.hashCode(this.nombreLargo);
        hash = 43 * hash + Objects.hashCode(this.concentracion);
        hash = 43 * hash + Objects.hashCode(this.idUnidadConcentracion);
        hash = 43 * hash + Objects.hashCode(this.presentacionLaboratorio);
        hash = 43 * hash + Objects.hashCode(this.laboratorio);
        hash = 43 * hash + Objects.hashCode(this.idPresentacionEntrada);
        hash = 43 * hash + Objects.hashCode(this.factorTransformacion);
        hash = 43 * hash + Objects.hashCode(this.idPresentacionSalida);
        hash = 43 * hash + Objects.hashCode(this.idCategoria);
        hash = 43 * hash + Objects.hashCode(this.idSubcategoria);
        hash = 43 * hash + Objects.hashCode(this.grupo);
        hash = 43 * hash + (this.indivisible ? 1 : 0);
        hash = 43 * hash + Arrays.hashCode(this.imagenPresentacion);
        hash = 43 * hash + Objects.hashCode(this.nameImage);
        hash = 43 * hash + Objects.hashCode(this.cuadroBasico);
        hash = 43 * hash + Objects.hashCode(this.activo);
        hash = 43 * hash + Objects.hashCode(this.tipo);
        hash = 43 * hash + Objects.hashCode(this.insertFecha);
        hash = 43 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 43 * hash + Objects.hashCode(this.updateFecha);
        hash = 43 * hash + Objects.hashCode(this.updateIdUsuario);
        hash = 43 * hash + Objects.hashCode(this.idLocalidadAVG);
        hash = 43 * hash + Objects.hashCode(this.tipoMedicacion);
        hash = 43 * hash + Objects.hashCode(this.refrigeracion);
        hash = 43 * hash + Objects.hashCode(this.ubicacion);
        hash = 43 * hash + (this.medicamentoControlado ? 1 : 0);
        hash = 43 * hash + Objects.hashCode(this.codigoBarrasAlterno);
        hash = 43 * hash + Objects.hashCode(this.claveAlterna);
        hash = 43 * hash + Objects.hashCode(this.parental);
        hash = 43 * hash + Objects.hashCode(this.mezclaParental);
        hash = 43 * hash + Objects.hashCode(this.osmolaridad);
        hash = 43 * hash + Objects.hashCode(this.densidad);
        hash = 43 * hash + Objects.hashCode(this.calculoMezcla);
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
        final Medicamento other = (Medicamento) obj;
        if (this.indivisible != other.indivisible) {
            return false;
        }
        if (this.medicamentoControlado != other.medicamentoControlado) {
            return false;
        }
        if (this.parental != other.parental) {
            return false;
        }
        if (!Objects.equals(this.idMedicamento, other.idMedicamento)) {
            return false;
        }
        if (!Objects.equals(this.claveInstitucional, other.claveInstitucional)) {
            return false;
        }
        if (!Objects.equals(this.nombreCorto, other.nombreCorto)) {
            return false;
        }
        if (!Objects.equals(this.nombreLargo, other.nombreLargo)) {
            return false;
        }
        if (!Objects.equals(this.concentracion, other.concentracion)) {
            return false;
        }
        if (!Objects.equals(this.presentacionLaboratorio, other.presentacionLaboratorio)) {
            return false;
        }
        if (!Objects.equals(this.laboratorio, other.laboratorio)) {
            return false;
        }
        if (!Objects.equals(this.grupo, other.grupo)) {
            return false;
        }
        if (!Objects.equals(this.nameImage, other.nameImage)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.idLocalidadAVG, other.idLocalidadAVG)) {
            return false;
        }
        if (!Objects.equals(this.ubicacion, other.ubicacion)) {
            return false;
        }
        if (!Objects.equals(this.sustanciaActiva, other.sustanciaActiva)) {
            return false;
        }
        if (!Objects.equals(this.idViaAdministracion, other.idViaAdministracion)) {
            return false;
        }
        if (!Objects.equals(this.idUnidadConcentracion, other.idUnidadConcentracion)) {
            return false;
        }
        if (!Objects.equals(this.idPresentacionEntrada, other.idPresentacionEntrada)) {
            return false;
        }
        if (!Objects.equals(this.factorTransformacion, other.factorTransformacion)) {
            return false;
        }
        if (!Objects.equals(this.idPresentacionSalida, other.idPresentacionSalida)) {
            return false;
        }
        if (!Objects.equals(this.idCategoria, other.idCategoria)) {
            return false;
        }
        if (!Objects.equals(this.idSubcategoria, other.idSubcategoria)) {
            return false;
        }
        if (!Arrays.equals(this.imagenPresentacion, other.imagenPresentacion)) {
            return false;
        }
        if (!Objects.equals(this.cuadroBasico, other.cuadroBasico)) {
            return false;
        }
        if (!Objects.equals(this.activo, other.activo)) {
            return false;
        }
        if (!Objects.equals(this.tipo, other.tipo)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        if (!Objects.equals(this.updateFecha, other.updateFecha)) {
            return false;
        }
        if (!Objects.equals(this.tipoMedicacion, other.tipoMedicacion)) {
            return false;
        }
        if (!Objects.equals(this.refrigeracion, other.refrigeracion)) {
            return false;
        }
        if (!Objects.equals(this.mezclaParental, other.mezclaParental)) {
            return false;
        }
        if (!Objects.equals(this.codigoBarrasAlterno, other.codigoBarrasAlterno)) {
            return false;
        }
        if (!Objects.equals(this.osmolaridad, other.osmolaridad)) {
            return false;
        }
        if (!Objects.equals(this.densidad, other.densidad)) {
            return false;
        }
        if (!Objects.equals(this.calculoMezcla, other.calculoMezcla)) {
            return false;
        }
        return Objects.equals(this.claveAlterna, other.claveAlterna);
    }

    @Override
    public String toString() {
        return "Medicamento{" + "idMedicamento=" + idMedicamento + ", claveInstitucional=" + claveInstitucional + ", sustanciaActiva=" + sustanciaActiva + ", idViaAdministracion=" + idViaAdministracion + ", nombreCorto=" + nombreCorto + ", nombreLargo=" + nombreLargo + ", concentracion=" + concentracion + ", idUnidadConcentracion=" + idUnidadConcentracion + ", presentacionLaboratorio=" + presentacionLaboratorio + ", laboratorio=" + laboratorio + ", idPresentacionEntrada=" + idPresentacionEntrada + ", factorTransformacion=" + factorTransformacion + ", idPresentacionSalida=" + idPresentacionSalida + ", idCategoria=" + idCategoria + ", idSubcategoria=" + idSubcategoria + ", grupo=" + grupo + ", indivisible=" + indivisible + ", imagenPresentacion=" + imagenPresentacion + ", nameImage=" + nameImage + ", cuadroBasico=" + cuadroBasico + ", activo=" + activo + ", tipo=" + tipo + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + ", idLocalidadAVG=" + idLocalidadAVG + ", tipoMedicacion=" + tipoMedicacion + ", refrigeracion=" + refrigeracion + ", mezclaParental=" + mezclaParental + ", ubicacion=" + ubicacion + ", medicamentoControlado=" + medicamentoControlado + ", estatusRefrigeracion=" + estatusRefrigeracion + ", parental=" + parental + ", osmolaridad=" + osmolaridad + ", densidad=" + densidad + ", calculoMezcla=" + calculoMezcla + '}';
    }

    public String getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(String idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public String getClaveInstitucional() {
        return claveInstitucional;
    }

    public void setClaveInstitucional(String claveInstitucional) {
        this.claveInstitucional = claveInstitucional;
    }

    public Integer getSustanciaActiva() {
        return sustanciaActiva;
    }

    public void setSustanciaActiva(Integer sustanciaActiva) {
        this.sustanciaActiva = sustanciaActiva;
    }

    public Integer getIdViaAdministracion() {
        return idViaAdministracion;
    }

    public void setIdViaAdministracion(Integer idViaAdministracion) {
        this.idViaAdministracion = idViaAdministracion;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    public String getNombreLargo() {
        return nombreLargo;
    }

    public void setNombreLargo(String nombreLargo) {
        this.nombreLargo = nombreLargo;
    }

    public BigDecimal getConcentracion() {
        return concentracion;
    }

    public void setConcentracion(BigDecimal concentracion) {
        this.concentracion = concentracion;
    }

    public Integer getIdUnidadConcentracion() {
        return idUnidadConcentracion;
    }

    public void setIdUnidadConcentracion(Integer idUnidadConcentracion) {
        this.idUnidadConcentracion = idUnidadConcentracion;
    }

    public String getPresentacionLaboratorio() {
        return presentacionLaboratorio;
    }

    public void setPresentacionLaboratorio(String presentacionLaboratorio) {
        this.presentacionLaboratorio = presentacionLaboratorio;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    public Integer getIdPresentacionEntrada() {
        return idPresentacionEntrada;
    }

    public void setIdPresentacionEntrada(Integer idPresentacionEntrada) {
        this.idPresentacionEntrada = idPresentacionEntrada;
    }

    public Integer getFactorTransformacion() {
        return factorTransformacion;
    }

    public void setFactorTransformacion(Integer factorTransformacion) {
        this.factorTransformacion = factorTransformacion;
    }

    public Integer getIdPresentacionSalida() {
        return idPresentacionSalida;
    }

    public void setIdPresentacionSalida(Integer idPresentacionSalida) {
        this.idPresentacionSalida = idPresentacionSalida;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Integer getIdSubcategoria() {
        return idSubcategoria;
    }

    public void setIdSubcategoria(Integer idSubcategoria) {
        this.idSubcategoria = idSubcategoria;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public boolean isIndivisible() {
        return indivisible;
    }

    public void setIndivisible(boolean indivisible) {
        this.indivisible = indivisible;
    }

    public byte[] getImagenPresentacion() {
        return imagenPresentacion;
    }

    public void setImagenPresentacion(byte[] imagenPresentacion) {
        this.imagenPresentacion = imagenPresentacion;
    }

    public String getNameImage() {
        return nameImage;
    }

    public void setNameImage(String nameImage) {
        this.nameImage = nameImage;
    }

    public Integer getCuadroBasico() {
        return cuadroBasico;
    }

    public void setCuadroBasico(Integer cuadroBasico) {
        this.cuadroBasico = cuadroBasico;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
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

    public String getIdLocalidadAVG() {
        return idLocalidadAVG;
    }

    public void setIdLocalidadAVG(String idLocalidadAVG) {
        this.idLocalidadAVG = idLocalidadAVG;
    }

    public Integer getTipoMedicacion() {
        return tipoMedicacion;
    }

    public void setTipoMedicacion(Integer tipoMedicacion) {
        this.tipoMedicacion = tipoMedicacion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Integer getRefrigeracion() {
        return refrigeracion;
    }

    public void setRefrigeracion(Integer refrigeracion) {
        this.refrigeracion = refrigeracion;
    }

    public Integer getMezclaParental() {
        return mezclaParental;
    }

    public void setMezclaParental(Integer mezclaParental) {
        this.mezclaParental = mezclaParental;
    }

    public boolean isCalculoMezcla() {
        return calculoMezcla;
    }

    public void setCalculoMezcla(boolean calculoMezcla) {
        this.calculoMezcla = calculoMezcla;
    }

    public boolean isEstatusRefrigeracion() {
        return estatusRefrigeracion;
    }

    public void setEstatusRefrigeracion(boolean estatusRefrigeracion) {
        this.estatusRefrigeracion = estatusRefrigeracion;
    }

    public boolean isMedicamentoControlado() {
        return medicamentoControlado;
    }

    public void setMedicamentoControlado(boolean medicamentoControlado) {
        this.medicamentoControlado = medicamentoControlado;
    }

    public String getCodigoBarrasAlterno() {
        return codigoBarrasAlterno;
    }

    public void setCodigoBarrasAlterno(String codigoBarrasAlterno) {
        this.codigoBarrasAlterno = codigoBarrasAlterno;
    }

    public String getClaveAlterna() {
        return claveAlterna;
    }

    public void setClaveAlterna(String claveAlterna) {
        this.claveAlterna = claveAlterna;
    }

    public Double getOsmolaridad() {
        return osmolaridad;
    }

    public void setOsmolaridad(Double osmolaridad) {
        this.osmolaridad = osmolaridad;
    }

    public Double getDensidad() {
        return densidad;
    }

    public void setDensidad(Double densidad) {
        this.densidad = densidad;
    }

    public Integer getNoHorasEstabilidad() {
        return noHorasEstabilidad;
    }

    public void setNoHorasEstabilidad(Integer noHorasEstabilidad) {
        this.noHorasEstabilidad = noHorasEstabilidad;
    }

    public Double getCantPorEnvase() {
        return cantPorEnvase;
    }

    public void setCantPorEnvase(Double cantPorEnvase) {
        this.cantPorEnvase = cantPorEnvase;
    }

    public Integer getIdUnidadMedida() {
        return idUnidadMedida;
    }

    public void setIdUnidadMedida(Integer idUnidadMedida) {
        this.idUnidadMedida = idUnidadMedida;
    }

    public boolean isParental() {
        return parental;
    }

    public void setParental(boolean parental) {
        this.parental = parental;
    }

    public boolean isIncluyeDiluyente() {
        return incluyeDiluyente;
    }

    public void setIncluyeDiluyente(boolean incluyeDiluyente) {
        this.incluyeDiluyente = incluyeDiluyente;
    }

    public boolean isRequiereDiluyente() {
        return requiereDiluyente;
    }

    public void setRequiereDiluyente(boolean requiereDiluyente) {
        this.requiereDiluyente = requiereDiluyente;
    }

    public boolean isDiluyente() {
        return diluyente;
    }

    public void setDiluyente(boolean diluyente) {
        this.diluyente = diluyente;
    }

    public boolean isFotosensible() {
        return fotosensible;
    }

    public void setFotosensible(boolean fotosensible) {
        this.fotosensible = fotosensible;
    }

    public boolean isTempAmbiente() {
        return tempAmbiente;
    }

    public void setTempAmbiente(boolean tempAmbiente) {
        this.tempAmbiente = tempAmbiente;
    }

    public boolean isTempRefrigeracion() {
        return tempRefrigeracion;
    }

    public void setTempRefrigeracion(boolean tempRefrigeracion) {
        this.tempRefrigeracion = tempRefrigeracion;
    }

    public Integer getNoHorasEstabilidadAbierto() {
        return noHorasEstabilidadAbierto;
    }

    public void setNoHorasEstabilidadAbierto(Integer noHorasEstabilidadAbierto) {
        this.noHorasEstabilidadAbierto = noHorasEstabilidadAbierto;
    }

    public Double getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(Double presentacion) {
        this.presentacion = presentacion;
    }

    public boolean isContenedor() {
        return contenedor;
    }

    public void setContenedor(boolean contenedor) {
        this.contenedor = contenedor;
    }

    public boolean isNoAgitar() {
        return noAgitar;
    }

    public void setNoAgitar(boolean noAgitar) {
        this.noAgitar = noAgitar;
    }

    public Integer getOrdePreparacion() {
        return ordePreparacion;
    }

    public void setOrdePreparacion(Integer ordePreparacion) {
        this.ordePreparacion = ordePreparacion;
    }

    public Double getDosisPorMl() {
        return dosisPorMl;
    }

    public void setDosisPorMl(Double dosisPorMl) {
        this.dosisPorMl = dosisPorMl;
    }

    public Integer getIdUnidadMedidaDosis() {
        return idUnidadMedidaDosis;
    }

    public void setIdUnidadMedidaDosis(Integer idUnidadMedidaDosis) {
        this.idUnidadMedidaDosis = idUnidadMedidaDosis;
    }

    public boolean isCalcularNCA() {
        return calcularNCA;
    }

    public void setCalcularNCA(boolean calcularNCA) {
        this.calcularNCA = calcularNCA;
    }

    public Double getCalorias() {
        return calorias;
    }

    public void setCalorias(Double calorias) {
        this.calorias = calorias;
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

    public boolean isEsAgua() {
        return esAgua;
    }

    public void setEsAgua(boolean esAgua) {
        this.esAgua = esAgua;
    }

    public boolean isAutorizar() {
        return autorizar;
    }

    public void setAutorizar(boolean autorizar) {
        this.autorizar = autorizar;
    }

    public Integer getTipoCalorias() {
        return tipoCalorias;
    }

    public void setTipoCalorias(Integer tipoCalorias) {
        this.tipoCalorias = tipoCalorias;
    }

}
