package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author hcervantes
 */
public class Estabilidad implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idEstabilidad;
    private String idInsumo;
    private String nombreGenerico;
    private Integer idFabricante;
    private Integer idMarca;
    private Integer idPresentacion;
    private String presentacionEnvase;
    private Double volumenReconst;
    private String diluyenteReconst;
    private Double concentracionReconst;
    private Integer idUnidadReconst;
    private String almaceamiento;
    private String solucionesCompatibles;
    private Double concentMinMezcla;
    private Double concentMaxMezclat;
    private Integer idUnidadMezcla;
    private Integer limHrsUsoRedSeca;
    private Integer limHrsUsoRedFria;
    private Integer idViaAdministracion;
    private String observacionesPreparacion;
    private String reglasDePreparacion;
    private String referenciaBibliografica;
    private Integer noHrsEstabilidad;
    private Integer noHrsEstabilidadMezcla;
    private Double temperaturaMin;
    private Double temperaturaMax;
    private Integer idContenedor;
    private Double costo;
    private Integer refrigeracionInsumo;
    private Integer tempAmbiente;
    private Integer tempRefrigeracion;
    private Integer noAgitar;
    private Integer autorizar;
    private Integer fotosensible;
    private Integer incluyeDiluyente;
    private Integer requiereDiluyente;
    private Integer diluyente;
    private Integer contenedor;
    private Integer esAgua;
    private Integer mezclaParental;
    private Double osmolaridad;
    private Double densidad;
    private Double calorias;
    private Integer calcularNCA;
    private Integer ordePreparacion;
    private Double ordenEtiqueta;
    private Double ordenAdicion;
    private Integer calculoMezcla;
    private Double dosisPorMl;
    private Integer idUnidadMedidaDosis;
    private Double cantPorEnvase;
    private Integer idUnidadMedida;
    private Integer noHorasestabilidad;
    private Integer noHorasEstabilidadAbierto;
    private String dosificacion;
    private String riesgos;
    private String contraindicaciones;
    private String interacciones;
    private String composicion;
    private String precauciones;
    private String reacciones;
    private String farmacocinetica;
    private String farmacodinamica;
    private String indicacionesTerapeuticas;
    private Integer idEstatus;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;

    public Estabilidad() {
    }

    public Estabilidad(String idEstabilidad) {
        this.idEstabilidad = idEstabilidad;
    }

    public String getIdEstabilidad() {
        return idEstabilidad;
    }

    public void setIdEstabilidad(String idEstabilidad) {
        this.idEstabilidad = idEstabilidad;
    }

    public String getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(String idInsumo) {
        this.idInsumo = idInsumo;
    }

    public String getNombreGenerico() {
        return nombreGenerico;
    }

    public void setNombreGenerico(String nombreGenerico) {
        this.nombreGenerico = nombreGenerico;
    }

    public Integer getIdFabricante() {
        return idFabricante;
    }

    public void setIdFabricante(Integer idFabricante) {
        this.idFabricante = idFabricante;
    }

    public Integer getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(Integer idMarca) {
        this.idMarca = idMarca;
    }

    public Integer getIdPresentacion() {
        return idPresentacion;
    }

    public void setIdPresentacion(Integer idPresentacion) {
        this.idPresentacion = idPresentacion;
    }

    public String getPresentacionEnvase() {
        return presentacionEnvase;
    }

    public void setPresentacionEnvase(String presentacionEnvase) {
        this.presentacionEnvase = presentacionEnvase;
    }

    public Double getVolumenReconst() {
        return volumenReconst;
    }

    public void setVolumenReconst(Double volumenReconst) {
        this.volumenReconst = volumenReconst;
    }

    public String getDiluyenteReconst() {
        return diluyenteReconst;
    }

    public void setDiluyenteReconst(String diluyenteReconst) {
        this.diluyenteReconst = diluyenteReconst;
    }

    public Double getConcentracionReconst() {
        return concentracionReconst;
    }

    public void setConcentracionReconst(Double concentracionReconst) {
        this.concentracionReconst = concentracionReconst;
    }

    public Integer getIdUnidadReconst() {
        return idUnidadReconst;
    }

    public void setIdUnidadReconst(Integer idUnidadReconst) {
        this.idUnidadReconst = idUnidadReconst;
    }

    public String getAlmaceamiento() {
        return almaceamiento;
    }

    public void setAlmaceamiento(String almaceamiento) {
        this.almaceamiento = almaceamiento;
    }

    public String getSolucionesCompatibles() {
        return solucionesCompatibles;
    }

    public void setSolucionesCompatibles(String solucionesCompatibles) {
        this.solucionesCompatibles = solucionesCompatibles;
    }

    public Double getConcentMinMezcla() {
        return concentMinMezcla;
    }

    public void setConcentMinMezcla(Double concentMinMezcla) {
        this.concentMinMezcla = concentMinMezcla;
    }

    public Double getConcentMaxMezclat() {
        return concentMaxMezclat;
    }

    public void setConcentMaxMezclat(Double concentMaxMezclat) {
        this.concentMaxMezclat = concentMaxMezclat;
    }

    public Integer getIdUnidadMezcla() {
        return idUnidadMezcla;
    }

    public void setIdUnidadMezcla(Integer idUnidadMezcla) {
        this.idUnidadMezcla = idUnidadMezcla;
    }

    public Integer getLimHrsUsoRedSeca() {
        return limHrsUsoRedSeca;
    }

    public void setLimHrsUsoRedSeca(Integer limHrsUsoRedSeca) {
        this.limHrsUsoRedSeca = limHrsUsoRedSeca;
    }

    public Integer getLimHrsUsoRedFria() {
        return limHrsUsoRedFria;
    }

    public void setLimHrsUsoRedFria(Integer limHrsUsoRedFria) {
        this.limHrsUsoRedFria = limHrsUsoRedFria;
    }

    public Integer getIdViaAdministracion() {
        return idViaAdministracion;
    }

    public void setIdViaAdministracion(Integer idViaAdministracion) {
        this.idViaAdministracion = idViaAdministracion;
    }

    public String getObservacionesPreparacion() {
        return observacionesPreparacion;
    }

    public void setObservacionesPreparacion(String observacionesPreparacion) {
        this.observacionesPreparacion = observacionesPreparacion;
    }

    public String getReglasDePreparacion() {
        return reglasDePreparacion;
    }

    public void setReglasDePreparacion(String reglasDePreparacion) {
        this.reglasDePreparacion = reglasDePreparacion;
    }

    public String getReferenciaBibliografica() {
        return referenciaBibliografica;
    }

    public void setReferenciaBibliografica(String referenciaBibliografica) {
        this.referenciaBibliografica = referenciaBibliografica;
    }

    public Integer getNoHrsEstabilidad() {
        return noHrsEstabilidad;
    }

    public void setNoHrsEstabilidad(Integer noHrsEstabilidad) {
        this.noHrsEstabilidad = noHrsEstabilidad;
    }

    public Integer getNoHrsEstabilidadMezcla() {
        return noHrsEstabilidadMezcla;
    }

    public void setNoHrsEstabilidadMezcla(Integer noHrsEstabilidadMezcla) {
        this.noHrsEstabilidadMezcla = noHrsEstabilidadMezcla;
    }

    public Double getTemperaturaMin() {
        return temperaturaMin;
    }

    public void setTemperaturaMin(Double temperaturaMin) {
        this.temperaturaMin = temperaturaMin;
    }

    public Double getTemperaturaMax() {
        return temperaturaMax;
    }

    public void setTemperaturaMax(Double temperaturaMax) {
        this.temperaturaMax = temperaturaMax;
    }

    public Integer getIdContenedor() {
        return idContenedor;
    }

    public void setIdContenedor(Integer idContenedor) {
        this.idContenedor = idContenedor;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public Integer getRefrigeracionInsumo() {
        return refrigeracionInsumo;
    }

    public void setRefrigeracionInsumo(Integer refrigeracionInsumo) {
        this.refrigeracionInsumo = refrigeracionInsumo;
    }

    public Integer getTempAmbiente() {
        return tempAmbiente;
    }

    public void setTempAmbiente(Integer tempAmbiente) {
        this.tempAmbiente = tempAmbiente;
    }

    public Integer getTempRefrigeracion() {
        return tempRefrigeracion;
    }

    public void setTempRefrigeracion(Integer tempRefrigeracion) {
        this.tempRefrigeracion = tempRefrigeracion;
    }

    public Integer getNoAgitar() {
        return noAgitar;
    }

    public void setNoAgitar(Integer noAgitar) {
        this.noAgitar = noAgitar;
    }

    public Integer getAutorizar() {
        return autorizar;
    }

    public void setAutorizar(Integer autorizar) {
        this.autorizar = autorizar;
    }

    public Integer getFotosensible() {
        return fotosensible;
    }

    public void setFotosensible(Integer fotosensible) {
        this.fotosensible = fotosensible;
    }

    public Integer getIncluyeDiluyente() {
        return incluyeDiluyente;
    }

    public void setIncluyeDiluyente(Integer incluyeDiluyente) {
        this.incluyeDiluyente = incluyeDiluyente;
    }

    public Integer getRequiereDiluyente() {
        return requiereDiluyente;
    }

    public void setRequiereDiluyente(Integer requiereDiluyente) {
        this.requiereDiluyente = requiereDiluyente;
    }

    public Integer getDiluyente() {
        return diluyente;
    }

    public void setDiluyente(Integer diluyente) {
        this.diluyente = diluyente;
    }

    public Integer getContenedor() {
        return contenedor;
    }

    public void setContenedor(Integer contenedor) {
        this.contenedor = contenedor;
    }

    public Integer getEsAgua() {
        return esAgua;
    }

    public void setEsAgua(Integer esAgua) {
        this.esAgua = esAgua;
    }

    public Integer getMezclaParental() {
        return mezclaParental;
    }

    public void setMezclaParental(Integer mezclaParental) {
        this.mezclaParental = mezclaParental;
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

    public Double getCalorias() {
        return calorias;
    }

    public void setCalorias(Double calorias) {
        this.calorias = calorias;
    }

    public Integer getCalcularNCA() {
        return calcularNCA;
    }

    public void setCalcularNCA(Integer calcularNCA) {
        this.calcularNCA = calcularNCA;
    }

    public Integer getOrdePreparacion() {
        return ordePreparacion;
    }

    public void setOrdePreparacion(Integer ordePreparacion) {
        this.ordePreparacion = ordePreparacion;
    }

    public Double getOrdenEtiqueta() {
        return ordenEtiqueta;
    }

    public void setOrdenEtiqueta(Double ordenEtiqueta) {
        this.ordenEtiqueta = ordenEtiqueta;
    }

    public Double getOrdenAdicion() {
        return ordenAdicion;
    }

    public void setOrdenAdicion(Double ordenAdicion) {
        this.ordenAdicion = ordenAdicion;
    }

    public Integer getCalculoMezcla() {
        return calculoMezcla;
    }

    public void setCalculoMezcla(Integer calculoMezcla) {
        this.calculoMezcla = calculoMezcla;
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

    public Integer getNoHorasestabilidad() {
        return noHorasestabilidad;
    }

    public void setNoHorasestabilidad(Integer noHorasestabilidad) {
        this.noHorasestabilidad = noHorasestabilidad;
    }

    public Integer getNoHorasEstabilidadAbierto() {
        return noHorasEstabilidadAbierto;
    }

    public void setNoHorasEstabilidadAbierto(Integer noHorasEstabilidadAbierto) {
        this.noHorasEstabilidadAbierto = noHorasEstabilidadAbierto;
    }

    public String getDosificacion() {
        return dosificacion;
    }

    public void setDosificacion(String dosificacion) {
        this.dosificacion = dosificacion;
    }

    public String getRiesgos() {
        return riesgos;
    }

    public void setRiesgos(String riesgos) {
        this.riesgos = riesgos;
    }

    public String getContraindicaciones() {
        return contraindicaciones;
    }

    public void setContraindicaciones(String contraindicaciones) {
        this.contraindicaciones = contraindicaciones;
    }

    public String getInteracciones() {
        return interacciones;
    }

    public void setInteracciones(String interacciones) {
        this.interacciones = interacciones;
    }

    public String getComposicion() {
        return composicion;
    }

    public void setComposicion(String composicion) {
        this.composicion = composicion;
    }

    public String getPrecauciones() {
        return precauciones;
    }

    public void setPrecauciones(String precauciones) {
        this.precauciones = precauciones;
    }

    public String getReacciones() {
        return reacciones;
    }

    public void setReacciones(String reacciones) {
        this.reacciones = reacciones;
    }

    public String getFarmacocinetica() {
        return farmacocinetica;
    }

    public void setFarmacocinetica(String farmacocinetica) {
        this.farmacocinetica = farmacocinetica;
    }

    public String getFarmacodinamica() {
        return farmacodinamica;
    }

    public void setFarmacodinamica(String farmacodinamica) {
        this.farmacodinamica = farmacodinamica;
    }

    public String getIndicacionesTerapeuticas() {
        return indicacionesTerapeuticas;
    }

    public void setIndicacionesTerapeuticas(String indicacionesTerapeuticas) {
        this.indicacionesTerapeuticas = indicacionesTerapeuticas;
    }

    public Integer getIdEstatus() {
        return idEstatus;
    }

    public void setIdEstatus(Integer idEstatus) {
        this.idEstatus = idEstatus;
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
    public String toString() {
        return "Estabilidad{" + "idEstabilidad=" + idEstabilidad + ", idInsumo=" + idInsumo + ", nombreGenerico=" + nombreGenerico + ", idFabricante=" + idFabricante + ", idMarca=" + idMarca + ", idPresentacion=" + idPresentacion + ", presentacionEnvase=" + presentacionEnvase + ", volumenReconst=" + volumenReconst + ", diluyenteReconst=" + diluyenteReconst + ", concentracionReconst=" + concentracionReconst + ", idUnidadReconst=" + idUnidadReconst + ", almaceamiento=" + almaceamiento + ", solucionesCompatibles=" + solucionesCompatibles + ", concentMinMezcla=" + concentMinMezcla + ", concentMaxMezclat=" + concentMaxMezclat + ", idUnidadMezcla=" + idUnidadMezcla + ", limHrsUsoRedSeca=" + limHrsUsoRedSeca + ", limHrsUsoRedFria=" + limHrsUsoRedFria + ", idViaAdministracion=" + idViaAdministracion + ", observacionesPreparacion=" + observacionesPreparacion + ", reglasDePreparacion=" + reglasDePreparacion + ", referenciaBibliografica=" + referenciaBibliografica + ", noHrsEstabilidad=" + noHrsEstabilidad + ", noHrsEstabilidadMezcla=" + noHrsEstabilidadMezcla + ", temperaturaMin=" + temperaturaMin + ", temperaturaMax=" + temperaturaMax + ", idContenedor=" + idContenedor + ", costo=" + costo + ", refrigeracionInsumo=" + refrigeracionInsumo + ", tempAmbiente=" + tempAmbiente + ", tempRefrigeracion=" + tempRefrigeracion + ", noAgitar=" + noAgitar + ", autorizar=" + autorizar + ", fotosensible=" + fotosensible + ", incluyeDiluyente=" + incluyeDiluyente + ", requiereDiluyente=" + requiereDiluyente + ", diluyente=" + diluyente + ", contenedor=" + contenedor + ", esAgua=" + esAgua + ", mezclaParental=" + mezclaParental + ", osmolaridad=" + osmolaridad + ", densidad=" + densidad + ", calorias=" + calorias + ", calcularNCA=" + calcularNCA + ", ordePreparacion=" + ordePreparacion + ", ordenEtiqueta=" + ordenEtiqueta + ", ordenAdicion=" + ordenAdicion + ", calculoMezcla=" + calculoMezcla + ", dosisPorMl=" + dosisPorMl + ", idUnidadMedidaDosis=" + idUnidadMedidaDosis + ", cantPorEnvase=" + cantPorEnvase + ", idUnidadMedida=" + idUnidadMedida + ", noHorasestabilidad=" + noHorasestabilidad + ", noHorasEstabilidadAbierto=" + noHorasEstabilidadAbierto + ", dosificacion=" + dosificacion + ", riesgos=" + riesgos + ", contraindicaciones=" + contraindicaciones + ", interacciones=" + interacciones + ", composicion=" + composicion + ", precauciones=" + precauciones + ", reacciones=" + reacciones + ", farmacocinetica=" + farmacocinetica + ", farmacodinamica=" + farmacodinamica + ", indicacionesTerapeuticas=" + indicacionesTerapeuticas + ", idEstatus=" + idEstatus + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.idEstabilidad);
        hash = 89 * hash + Objects.hashCode(this.idInsumo);
        hash = 89 * hash + Objects.hashCode(this.nombreGenerico);
        hash = 89 * hash + Objects.hashCode(this.idFabricante);
        hash = 89 * hash + Objects.hashCode(this.idMarca);
        hash = 89 * hash + Objects.hashCode(this.idPresentacion);
        hash = 89 * hash + Objects.hashCode(this.presentacionEnvase);
        hash = 89 * hash + Objects.hashCode(this.volumenReconst);
        hash = 89 * hash + Objects.hashCode(this.diluyenteReconst);
        hash = 89 * hash + Objects.hashCode(this.concentracionReconst);
        hash = 89 * hash + Objects.hashCode(this.idUnidadReconst);
        hash = 89 * hash + Objects.hashCode(this.almaceamiento);
        hash = 89 * hash + Objects.hashCode(this.solucionesCompatibles);
        hash = 89 * hash + Objects.hashCode(this.concentMinMezcla);
        hash = 89 * hash + Objects.hashCode(this.concentMaxMezclat);
        hash = 89 * hash + Objects.hashCode(this.idUnidadMezcla);
        hash = 89 * hash + Objects.hashCode(this.limHrsUsoRedSeca);
        hash = 89 * hash + Objects.hashCode(this.limHrsUsoRedFria);
        hash = 89 * hash + Objects.hashCode(this.idViaAdministracion);
        hash = 89 * hash + Objects.hashCode(this.observacionesPreparacion);
        hash = 89 * hash + Objects.hashCode(this.reglasDePreparacion);
        hash = 89 * hash + Objects.hashCode(this.referenciaBibliografica);
        hash = 89 * hash + Objects.hashCode(this.noHrsEstabilidad);
        hash = 89 * hash + Objects.hashCode(this.noHrsEstabilidadMezcla);
        hash = 89 * hash + Objects.hashCode(this.temperaturaMin);
        hash = 89 * hash + Objects.hashCode(this.temperaturaMax);
        hash = 89 * hash + Objects.hashCode(this.idContenedor);
        hash = 89 * hash + Objects.hashCode(this.costo);
        hash = 89 * hash + Objects.hashCode(this.refrigeracionInsumo);
        hash = 89 * hash + Objects.hashCode(this.tempAmbiente);
        hash = 89 * hash + Objects.hashCode(this.tempRefrigeracion);
        hash = 89 * hash + Objects.hashCode(this.noAgitar);
        hash = 89 * hash + Objects.hashCode(this.autorizar);
        hash = 89 * hash + Objects.hashCode(this.fotosensible);
        hash = 89 * hash + Objects.hashCode(this.incluyeDiluyente);
        hash = 89 * hash + Objects.hashCode(this.requiereDiluyente);
        hash = 89 * hash + Objects.hashCode(this.diluyente);
        hash = 89 * hash + Objects.hashCode(this.contenedor);
        hash = 89 * hash + Objects.hashCode(this.esAgua);
        hash = 89 * hash + Objects.hashCode(this.mezclaParental);
        hash = 89 * hash + Objects.hashCode(this.osmolaridad);
        hash = 89 * hash + Objects.hashCode(this.densidad);
        hash = 89 * hash + Objects.hashCode(this.calorias);
        hash = 89 * hash + Objects.hashCode(this.calcularNCA);
        hash = 89 * hash + Objects.hashCode(this.ordePreparacion);
        hash = 89 * hash + Objects.hashCode(this.ordenEtiqueta);
        hash = 89 * hash + Objects.hashCode(this.ordenAdicion);
        hash = 89 * hash + Objects.hashCode(this.calculoMezcla);
        hash = 89 * hash + Objects.hashCode(this.dosisPorMl);
        hash = 89 * hash + Objects.hashCode(this.idUnidadMedidaDosis);
        hash = 89 * hash + Objects.hashCode(this.cantPorEnvase);
        hash = 89 * hash + Objects.hashCode(this.idUnidadMedida);
        hash = 89 * hash + Objects.hashCode(this.noHorasestabilidad);
        hash = 89 * hash + Objects.hashCode(this.noHorasEstabilidadAbierto);
        hash = 89 * hash + Objects.hashCode(this.dosificacion);
        hash = 89 * hash + Objects.hashCode(this.riesgos);
        hash = 89 * hash + Objects.hashCode(this.contraindicaciones);
        hash = 89 * hash + Objects.hashCode(this.interacciones);
        hash = 89 * hash + Objects.hashCode(this.composicion);
        hash = 89 * hash + Objects.hashCode(this.precauciones);
        hash = 89 * hash + Objects.hashCode(this.reacciones);
        hash = 89 * hash + Objects.hashCode(this.farmacocinetica);
        hash = 89 * hash + Objects.hashCode(this.farmacodinamica);
        hash = 89 * hash + Objects.hashCode(this.indicacionesTerapeuticas);
        hash = 89 * hash + Objects.hashCode(this.idEstatus);
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
        final Estabilidad other = (Estabilidad) obj;
        if (!Objects.equals(this.idEstabilidad, other.idEstabilidad)) {
            return false;
        }
        if (!Objects.equals(this.idInsumo, other.idInsumo)) {
            return false;
        }
        if (!Objects.equals(this.nombreGenerico, other.nombreGenerico)) {
            return false;
        }
        if (!Objects.equals(this.presentacionEnvase, other.presentacionEnvase)) {
            return false;
        }
        if (!Objects.equals(this.diluyenteReconst, other.diluyenteReconst)) {
            return false;
        }
        if (!Objects.equals(this.almaceamiento, other.almaceamiento)) {
            return false;
        }
        if (!Objects.equals(this.solucionesCompatibles, other.solucionesCompatibles)) {
            return false;
        }
        if (!Objects.equals(this.observacionesPreparacion, other.observacionesPreparacion)) {
            return false;
        }
        if (!Objects.equals(this.reglasDePreparacion, other.reglasDePreparacion)) {
            return false;
        }
        if (!Objects.equals(this.referenciaBibliografica, other.referenciaBibliografica)) {
            return false;
        }
        if (!Objects.equals(this.dosificacion, other.dosificacion)) {
            return false;
        }
        if (!Objects.equals(this.riesgos, other.riesgos)) {
            return false;
        }
        if (!Objects.equals(this.contraindicaciones, other.contraindicaciones)) {
            return false;
        }
        if (!Objects.equals(this.interacciones, other.interacciones)) {
            return false;
        }
        if (!Objects.equals(this.composicion, other.composicion)) {
            return false;
        }
        if (!Objects.equals(this.precauciones, other.precauciones)) {
            return false;
        }
        if (!Objects.equals(this.reacciones, other.reacciones)) {
            return false;
        }
        if (!Objects.equals(this.farmacocinetica, other.farmacocinetica)) {
            return false;
        }
        if (!Objects.equals(this.farmacodinamica, other.farmacodinamica)) {
            return false;
        }
        if (!Objects.equals(this.indicacionesTerapeuticas, other.indicacionesTerapeuticas)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.idFabricante, other.idFabricante)) {
            return false;
        }
        if (!Objects.equals(this.idMarca, other.idMarca)) {
            return false;
        }
        if (!Objects.equals(this.idPresentacion, other.idPresentacion)) {
            return false;
        }
        if (!Objects.equals(this.volumenReconst, other.volumenReconst)) {
            return false;
        }
        if (!Objects.equals(this.concentracionReconst, other.concentracionReconst)) {
            return false;
        }
        if (!Objects.equals(this.idUnidadReconst, other.idUnidadReconst)) {
            return false;
        }
        if (!Objects.equals(this.concentMinMezcla, other.concentMinMezcla)) {
            return false;
        }
        if (!Objects.equals(this.concentMaxMezclat, other.concentMaxMezclat)) {
            return false;
        }
        if (!Objects.equals(this.idUnidadMezcla, other.idUnidadMezcla)) {
            return false;
        }
        if (!Objects.equals(this.limHrsUsoRedSeca, other.limHrsUsoRedSeca)) {
            return false;
        }
        if (!Objects.equals(this.limHrsUsoRedFria, other.limHrsUsoRedFria)) {
            return false;
        }
        if (!Objects.equals(this.idViaAdministracion, other.idViaAdministracion)) {
            return false;
        }
        if (!Objects.equals(this.noHrsEstabilidad, other.noHrsEstabilidad)) {
            return false;
        }
        if (!Objects.equals(this.noHrsEstabilidadMezcla, other.noHrsEstabilidadMezcla)) {
            return false;
        }
        if (!Objects.equals(this.temperaturaMin, other.temperaturaMin)) {
            return false;
        }
        if (!Objects.equals(this.temperaturaMax, other.temperaturaMax)) {
            return false;
        }
        if (!Objects.equals(this.idContenedor, other.idContenedor)) {
            return false;
        }
        if (!Objects.equals(this.costo, other.costo)) {
            return false;
        }
        if (!Objects.equals(this.refrigeracionInsumo, other.refrigeracionInsumo)) {
            return false;
        }
        if (!Objects.equals(this.tempAmbiente, other.tempAmbiente)) {
            return false;
        }
        if (!Objects.equals(this.tempRefrigeracion, other.tempRefrigeracion)) {
            return false;
        }
        if (!Objects.equals(this.noAgitar, other.noAgitar)) {
            return false;
        }
        if (!Objects.equals(this.autorizar, other.autorizar)) {
            return false;
        }
        if (!Objects.equals(this.fotosensible, other.fotosensible)) {
            return false;
        }
        if (!Objects.equals(this.incluyeDiluyente, other.incluyeDiluyente)) {
            return false;
        }
        if (!Objects.equals(this.requiereDiluyente, other.requiereDiluyente)) {
            return false;
        }
        if (!Objects.equals(this.diluyente, other.diluyente)) {
            return false;
        }
        if (!Objects.equals(this.contenedor, other.contenedor)) {
            return false;
        }
        if (!Objects.equals(this.esAgua, other.esAgua)) {
            return false;
        }
        if (!Objects.equals(this.mezclaParental, other.mezclaParental)) {
            return false;
        }
        if (!Objects.equals(this.osmolaridad, other.osmolaridad)) {
            return false;
        }
        if (!Objects.equals(this.densidad, other.densidad)) {
            return false;
        }
        if (!Objects.equals(this.calorias, other.calorias)) {
            return false;
        }
        if (!Objects.equals(this.calcularNCA, other.calcularNCA)) {
            return false;
        }
        if (!Objects.equals(this.ordePreparacion, other.ordePreparacion)) {
            return false;
        }
        if (!Objects.equals(this.ordenEtiqueta, other.ordenEtiqueta)) {
            return false;
        }
        if (!Objects.equals(this.ordenAdicion, other.ordenAdicion)) {
            return false;
        }
        if (!Objects.equals(this.calculoMezcla, other.calculoMezcla)) {
            return false;
        }
        if (!Objects.equals(this.dosisPorMl, other.dosisPorMl)) {
            return false;
        }
        if (!Objects.equals(this.idUnidadMedidaDosis, other.idUnidadMedidaDosis)) {
            return false;
        }
        if (!Objects.equals(this.cantPorEnvase, other.cantPorEnvase)) {
            return false;
        }
        if (!Objects.equals(this.idUnidadMedida, other.idUnidadMedida)) {
            return false;
        }
        if (!Objects.equals(this.noHorasestabilidad, other.noHorasestabilidad)) {
            return false;
        }
        if (!Objects.equals(this.noHorasEstabilidadAbierto, other.noHorasEstabilidadAbierto)) {
            return false;
        }
        if (!Objects.equals(this.idEstatus, other.idEstatus)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        return Objects.equals(this.updateFecha, other.updateFecha);
    }

}
