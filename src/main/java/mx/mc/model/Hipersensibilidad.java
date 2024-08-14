/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author gcruz
 */
public class Hipersensibilidad  implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String idHipersensibilidad;
    private Date fecha;
    private String folio;
    private Integer idTipoHipersensibilidad;
    private Integer idAlteracion;
    private Integer idTipoAlergeno;
    private Integer idAlergeno;
    private String idPaciente;
    private String nombreConvencional;
    private Integer idSustanciaActiva;
    private String nombreComercial;
    private String efectos;
    private String manifestaciones;
    private Date fechaPrimerEvento;
    private Date fechaUltimoEvento;
    private Integer confirmada;
    private String sintomas;
    private Integer evaluacionMedica;
    private Integer analisisSangre;
    private Integer pruebasCutaneas;
    private Integer pruebaLGE;
    private Integer noTomarFarmaco;
    private Integer filtrosParticula;
    private Integer noComeralimento;
    private Integer mudarseUnaZona;
    private Integer eliminarElementos;
    private Integer cubrirColchonesAlmohadas;
    private Integer utilizarAlmohadas;
    private Integer lavarFrecuentementeRopa;
    private Integer limpiarCasaAMenudo;
    private Integer aparatosAireAcondisionado;
    private Integer aplicarVaporCaliente;
    private Integer exterminarCucarachas;
    private Integer activo;
    private String riesgo;
    private Date insertFecha;
    private String insertIdUsuario;    
    private Date updateFecha;
    private String updateIdUsuario;
    
    public Hipersensibilidad() {
        
    }

    public Hipersensibilidad(String idHipersensibilidad, Date fecha, String folio, Integer idTipoHipersensibilidad, Integer idAlteracion, Integer idTipoAlergeno, Integer idAlergeno, String idPaciente, String nombreConvencional, Integer idSustanciaActiva, String nombreComercial, String efectos, String manifestaciones, Date fechaPrimerEvento, Date fechaUltimoEvento, Integer confirmada, String sintomas, Integer evaluacionMedica, Integer analisisSangre, Integer pruebasCutaneas, Integer pruebaLGE, Integer noTomarFarmaco, Integer filtrosParticula, Integer noComeralimento, Integer mudarseUnaZona, Integer eliminarElementos, Integer cubrirColchonesAlmohadas, Integer utilizarAlmohadas, Integer lavarFrecuentementeRopa, Integer limpiarCasaAMenudo, Integer aparatosAireAcondisionado, Integer aplicarVaporCaliente, Integer exterminarCucarachas, Integer activo, String riesgo, Date insertFecha, String insertIdUsuario, Date updateFecha, String updateIdUsuario) {
        this.idHipersensibilidad = idHipersensibilidad;
        this.fecha = fecha;
        this.folio = folio;
        this.idTipoHipersensibilidad = idTipoHipersensibilidad;
        this.idAlteracion = idAlteracion;
        this.idTipoAlergeno = idTipoAlergeno;
        this.idAlergeno = idAlergeno;
        this.idPaciente = idPaciente;
        this.nombreConvencional = nombreConvencional;
        this.idSustanciaActiva = idSustanciaActiva;
        this.nombreComercial = nombreComercial;
        this.efectos = efectos;
        this.manifestaciones = manifestaciones;
        this.fechaPrimerEvento = fechaPrimerEvento;
        this.fechaUltimoEvento = fechaUltimoEvento;
        this.confirmada = confirmada;
        this.sintomas = sintomas;
        this.evaluacionMedica = evaluacionMedica;
        this.analisisSangre = analisisSangre;
        this.pruebasCutaneas = pruebasCutaneas;
        this.pruebaLGE = pruebaLGE;
        this.noTomarFarmaco = noTomarFarmaco;
        this.filtrosParticula = filtrosParticula;
        this.noComeralimento = noComeralimento;
        this.mudarseUnaZona = mudarseUnaZona;
        this.eliminarElementos = eliminarElementos;
        this.cubrirColchonesAlmohadas = cubrirColchonesAlmohadas;
        this.utilizarAlmohadas = utilizarAlmohadas;
        this.lavarFrecuentementeRopa = lavarFrecuentementeRopa;
        this.limpiarCasaAMenudo = limpiarCasaAMenudo;
        this.aparatosAireAcondisionado = aparatosAireAcondisionado;
        this.aplicarVaporCaliente = aplicarVaporCaliente;
        this.exterminarCucarachas = exterminarCucarachas;
        this.activo = activo;
        this.riesgo = riesgo;
        this.insertFecha = insertFecha;
        this.insertIdUsuario = insertIdUsuario;
        this.updateFecha = updateFecha;
        this.updateIdUsuario = updateIdUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.idHipersensibilidad);
        hash = 53 * hash + Objects.hashCode(this.fecha);
        hash = 53 * hash + Objects.hashCode(this.folio);
        hash = 53 * hash + Objects.hashCode(this.idTipoHipersensibilidad);
        hash = 53 * hash + Objects.hashCode(this.idAlteracion);
        hash = 53 * hash + Objects.hashCode(this.idTipoAlergeno);
        hash = 53 * hash + Objects.hashCode(this.idAlergeno);
        hash = 53 * hash + Objects.hashCode(this.idPaciente);
        hash = 53 * hash + Objects.hashCode(this.nombreConvencional);
        hash = 53 * hash + Objects.hashCode(this.idSustanciaActiva);
        hash = 53 * hash + Objects.hashCode(this.nombreComercial);
        hash = 53 * hash + Objects.hashCode(this.efectos);
        hash = 53 * hash + Objects.hashCode(this.manifestaciones);
        hash = 53 * hash + Objects.hashCode(this.fechaPrimerEvento);
        hash = 53 * hash + Objects.hashCode(this.fechaUltimoEvento);
        hash = 53 * hash + Objects.hashCode(this.confirmada);
        hash = 53 * hash + Objects.hashCode(this.sintomas);
        hash = 53 * hash + Objects.hashCode(this.evaluacionMedica);
        hash = 53 * hash + Objects.hashCode(this.analisisSangre);
        hash = 53 * hash + Objects.hashCode(this.pruebasCutaneas);
        hash = 53 * hash + Objects.hashCode(this.pruebaLGE);
        hash = 53 * hash + Objects.hashCode(this.noTomarFarmaco);
        hash = 53 * hash + Objects.hashCode(this.filtrosParticula);
        hash = 53 * hash + Objects.hashCode(this.noComeralimento);
        hash = 53 * hash + Objects.hashCode(this.mudarseUnaZona);
        hash = 53 * hash + Objects.hashCode(this.eliminarElementos);
        hash = 53 * hash + Objects.hashCode(this.cubrirColchonesAlmohadas);
        hash = 53 * hash + Objects.hashCode(this.utilizarAlmohadas);
        hash = 53 * hash + Objects.hashCode(this.lavarFrecuentementeRopa);
        hash = 53 * hash + Objects.hashCode(this.limpiarCasaAMenudo);
        hash = 53 * hash + Objects.hashCode(this.aparatosAireAcondisionado);
        hash = 53 * hash + Objects.hashCode(this.aplicarVaporCaliente);
        hash = 53 * hash + Objects.hashCode(this.exterminarCucarachas);
        hash = 53 * hash + Objects.hashCode(this.activo);
        hash = 53 * hash + Objects.hashCode(this.riesgo);
        hash = 53 * hash + Objects.hashCode(this.insertFecha);
        hash = 53 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 53 * hash + Objects.hashCode(this.updateFecha);
        hash = 53 * hash + Objects.hashCode(this.updateIdUsuario);
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
        final Hipersensibilidad other = (Hipersensibilidad) obj;
        if (!Objects.equals(this.idHipersensibilidad, other.idHipersensibilidad)) {
            return false;
        }
        if (!Objects.equals(this.folio, other.folio)) {
            return false;
        }
        if (!Objects.equals(this.idPaciente, other.idPaciente)) {
            return false;
        }
        if (!Objects.equals(this.nombreConvencional, other.nombreConvencional)) {
            return false;
        }
        if (!Objects.equals(this.nombreComercial, other.nombreComercial)) {
            return false;
        }
        if (!Objects.equals(this.efectos, other.efectos)) {
            return false;
        }
        if (!Objects.equals(this.manifestaciones, other.manifestaciones)) {
            return false;
        }
        if (!Objects.equals(this.sintomas, other.sintomas)) {
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
        if (!Objects.equals(this.idTipoHipersensibilidad, other.idTipoHipersensibilidad)) {
            return false;
        }
        if (!Objects.equals(this.idAlteracion, other.idAlteracion)) {
            return false;
        }
        if (!Objects.equals(this.idTipoAlergeno, other.idTipoAlergeno)) {
            return false;
        }
        if (!Objects.equals(this.idAlergeno, other.idAlergeno)) {
            return false;
        }
        if (!Objects.equals(this.idSustanciaActiva, other.idSustanciaActiva)) {
            return false;
        }
        if (!Objects.equals(this.activo, other.activo)) {
            return false;
        }
        if (!Objects.equals(this.riesgo, other.riesgo)) {
            return false;
        }
        if (!Objects.equals(this.fechaPrimerEvento, other.fechaPrimerEvento)) {
            return false;
        }
        if (!Objects.equals(this.fechaUltimoEvento, other.fechaUltimoEvento)) {
            return false;
        }
        if (!Objects.equals(this.confirmada, other.confirmada)) {
            return false;
        }
        if (!Objects.equals(this.evaluacionMedica, other.evaluacionMedica)) {
            return false;
        }
        if (!Objects.equals(this.analisisSangre, other.analisisSangre)) {
            return false;
        }
        if (!Objects.equals(this.pruebasCutaneas, other.pruebasCutaneas)) {
            return false;
        }
        if (!Objects.equals(this.pruebaLGE, other.pruebaLGE)) {
            return false;
        }
        if (!Objects.equals(this.noTomarFarmaco, other.noTomarFarmaco)) {
            return false;
        }
        if (!Objects.equals(this.filtrosParticula, other.filtrosParticula)) {
            return false;
        }
        if (!Objects.equals(this.noComeralimento, other.noComeralimento)) {
            return false;
        }
        if (!Objects.equals(this.mudarseUnaZona, other.mudarseUnaZona)) {
            return false;
        }
        if (!Objects.equals(this.eliminarElementos, other.eliminarElementos)) {
            return false;
        }
        if (!Objects.equals(this.cubrirColchonesAlmohadas, other.cubrirColchonesAlmohadas)) {
            return false;
        }
        if (!Objects.equals(this.utilizarAlmohadas, other.utilizarAlmohadas)) {
            return false;
        }
        if (!Objects.equals(this.lavarFrecuentementeRopa, other.lavarFrecuentementeRopa)) {
            return false;
        }
        if (!Objects.equals(this.limpiarCasaAMenudo, other.limpiarCasaAMenudo)) {
            return false;
        }
        if (!Objects.equals(this.aparatosAireAcondisionado, other.aparatosAireAcondisionado)) {
            return false;
        }
        if (!Objects.equals(this.aplicarVaporCaliente, other.aplicarVaporCaliente)) {
            return false;
        }
        if (!Objects.equals(this.exterminarCucarachas, other.exterminarCucarachas)) {
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
        return "Hipersensibilidad{" + "idHipersensibilidad=" + idHipersensibilidad + ", fecha=" + fecha + ", folio=" + folio + ", idTipoHipersensibilidad=" + idTipoHipersensibilidad + ", idAlteracion=" + idAlteracion + ", idTipoAlergeno=" + idTipoAlergeno + ", idAlergeno=" + idAlergeno + ", idPaciente=" + idPaciente + ", nombreConvencional=" + nombreConvencional + ", idSustanciaActiva=" + idSustanciaActiva + ", nombreComercial=" + nombreComercial + ", efectos=" + efectos + ", manifestaciones=" + manifestaciones + ", fechaPrimerEvento=" + fechaPrimerEvento + ", fechaUltimoEvento=" + fechaUltimoEvento + ", confirmada=" + confirmada + ", sintomas=" + sintomas + ", evaluacionMedica=" + evaluacionMedica + ", analisisSangre=" + analisisSangre + ", pruebasCutaneas=" + pruebasCutaneas + ", pruebaLGE=" + pruebaLGE + ", noTomarFarmaco=" + noTomarFarmaco + ", filtrosParticula=" + filtrosParticula + ", noComeralimento=" + noComeralimento + ", mudarseUnaZona=" + mudarseUnaZona + ", eliminarElementos=" + eliminarElementos + ", cubrirColchonesAlmohadas=" + cubrirColchonesAlmohadas + ", utilizarAlmohadas=" + utilizarAlmohadas + ", lavarFrecuentementeRopa=" + lavarFrecuentementeRopa + ", limpiarCasaAMenudo=" + limpiarCasaAMenudo + ", aparatosAireAcondisionado=" + aparatosAireAcondisionado + ", aplicarVaporCaliente=" + aplicarVaporCaliente + ", exterminarCucarachas=" + exterminarCucarachas + ", activo=" + activo + ",riesgo=" + riesgo + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + '}';
    }

    public String getIdHipersensibilidad() {
        return idHipersensibilidad;
    }

    public void setIdHipersensibilidad(String idHipersensibilidad) {
        this.idHipersensibilidad = idHipersensibilidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public Integer getIdTipoHipersensibilidad() {
        return idTipoHipersensibilidad;
    }

    public void setIdTipoHipersensibilidad(Integer idTipoHipersensibilidad) {
        this.idTipoHipersensibilidad = idTipoHipersensibilidad;
    }

    public Integer getIdAlteracion() {
        return idAlteracion;
    }

    public void setIdAlteracion(Integer idAlteracion) {
        this.idAlteracion = idAlteracion;
    }

    public Integer getIdTipoAlergeno() {
        return idTipoAlergeno;
    }

    public void setIdTipoAlergeno(Integer idTipoAlergeno) {
        this.idTipoAlergeno = idTipoAlergeno;
    }

    public Integer getIdAlergeno() {
        return idAlergeno;
    }

    public void setIdAlergeno(Integer idAlergeno) {
        this.idAlergeno = idAlergeno;
    }

    public String getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(String idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getNombreConvencional() {
        return nombreConvencional;
    }

    public void setNombreConvencional(String nombreConvencional) {
        this.nombreConvencional = nombreConvencional;
    }

    public Integer getIdSustanciaActiva() {
        return idSustanciaActiva;
    }

    public void setIdSustanciaActiva(Integer idSustanciaActiva) {
        this.idSustanciaActiva = idSustanciaActiva;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getEfectos() {
        return efectos;
    }

    public void setEfectos(String efectos) {
        this.efectos = efectos;
    }

    public String getManifestaciones() {
        return manifestaciones;
    }

    public void setManifestaciones(String manifestaciones) {
        this.manifestaciones = manifestaciones;
    }

    public Date getFechaPrimerEvento() {
        return fechaPrimerEvento;
    }

    public void setFechaPrimerEvento(Date fechaPrimerEvento) {
        this.fechaPrimerEvento = fechaPrimerEvento;
    }

    public Date getFechaUltimoEvento() {
        return fechaUltimoEvento;
    }

    public void setFechaUltimoEvento(Date fechaUltimoEvento) {
        this.fechaUltimoEvento = fechaUltimoEvento;
    }

    public Integer getConfirmada() {
        return confirmada;
    }

    public void setConfirmada(Integer confirmada) {
        this.confirmada = confirmada;
    }

    public String getSintomas() {
        return sintomas;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }

    public Integer getEvaluacionMedica() {
        return evaluacionMedica;
    }

    public void setEvaluacionMedica(Integer evaluacionMedica) {
        this.evaluacionMedica = evaluacionMedica;
    }

    public Integer getAnalisisSangre() {
        return analisisSangre;
    }

    public void setAnalisisSangre(Integer analisisSangre) {
        this.analisisSangre = analisisSangre;
    }

    public Integer getPruebasCutaneas() {
        return pruebasCutaneas;
    }

    public void setPruebasCutaneas(Integer pruebasCutaneas) {
        this.pruebasCutaneas = pruebasCutaneas;
    }

    public Integer getPruebaLGE() {
        return pruebaLGE;
    }

    public void setPruebaLGE(Integer pruebaLGE) {
        this.pruebaLGE = pruebaLGE;
    }

    public Integer getNoTomarFarmaco() {
        return noTomarFarmaco;
    }

    public void setNoTomarFarmaco(Integer noTomarFarmaco) {
        this.noTomarFarmaco = noTomarFarmaco;
    }

    public Integer getFiltrosParticula() {
        return filtrosParticula;
    }

    public void setFiltrosParticula(Integer filtrosParticula) {
        this.filtrosParticula = filtrosParticula;
    }

    public Integer getNoComeralimento() {
        return noComeralimento;
    }

    public void setNoComeralimento(Integer noComeralimento) {
        this.noComeralimento = noComeralimento;
    }

    public Integer getMudarseUnaZona() {
        return mudarseUnaZona;
    }

    public void setMudarseUnaZona(Integer mudarseUnaZona) {
        this.mudarseUnaZona = mudarseUnaZona;
    }

    public Integer getEliminarElementos() {
        return eliminarElementos;
    }

    public void setEliminarElementos(Integer eliminarElementos) {
        this.eliminarElementos = eliminarElementos;
    }

    public Integer getCubrirColchonesAlmohadas() {
        return cubrirColchonesAlmohadas;
    }

    public void setCubrirColchonesAlmohadas(Integer cubrirColchonesAlmohadas) {
        this.cubrirColchonesAlmohadas = cubrirColchonesAlmohadas;
    }

    public Integer getUtilizarAlmohadas() {
        return utilizarAlmohadas;
    }

    public void setUtilizarAlmohadas(Integer utilizarAlmohadas) {
        this.utilizarAlmohadas = utilizarAlmohadas;
    }

    public Integer getLavarFrecuentementeRopa() {
        return lavarFrecuentementeRopa;
    }

    public void setLavarFrecuentementeRopa(Integer lavarFrecuentementeRopa) {
        this.lavarFrecuentementeRopa = lavarFrecuentementeRopa;
    }

    public Integer getLimpiarCasaAMenudo() {
        return limpiarCasaAMenudo;
    }

    public void setLimpiarCasaAMenudo(Integer limpiarCasaAMenudo) {
        this.limpiarCasaAMenudo = limpiarCasaAMenudo;
    }

    public Integer getAparatosAireAcondisionado() {
        return aparatosAireAcondisionado;
    }

    public void setAparatosAireAcondisionado(Integer aparatosAireAcondisionado) {
        this.aparatosAireAcondisionado = aparatosAireAcondisionado;
    }

    public Integer getAplicarVaporCaliente() {
        return aplicarVaporCaliente;
    }

    public void setAplicarVaporCaliente(Integer aplicarVaporCaliente) {
        this.aplicarVaporCaliente = aplicarVaporCaliente;
    }

    public Integer getExterminarCucarachas() {
        return exterminarCucarachas;
    }

    public void setExterminarCucarachas(Integer exterminarCucarachas) {
        this.exterminarCucarachas = exterminarCucarachas;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
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
    
    
}   