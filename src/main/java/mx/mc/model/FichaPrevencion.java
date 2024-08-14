package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Cervanets
 */
public class FichaPrevencion implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idPrevencion;
    private String folio;
    private String idEstructura;
    private Integer idTurno;
    private String area;
    private String sanitizante;
    private String detergente;

    private boolean interiorEquipo;
    private boolean exteriorEquipo;
    private boolean mobiliario;
    private boolean paredes;
    private boolean herramientas;
    private boolean pisos;
    private boolean pasilloIngreso;
    private boolean areaLavado;
    private boolean areaAcondicionado;
    private boolean areaPreparado;
    private boolean vestidor;
    private boolean transfer;
    private boolean puertas;
    private boolean manijas;
    private boolean ventanas;
    private boolean areaDesinfeccion;
    private boolean areaInspeccion;
    private boolean areaAlmacenamiento;
    private Integer idEstatusPrevencion;

    private String idUsuarioRealizaLimpieza;
    private Date fechaRelizaLimpieza;
    private String comentariosRealizaLimpieza;

    private String idUsuarioSupervisa;
    private Date fechaSupervisa;
    private String comentariosSupervisa;

    private String idUsuarioAprueba;
    private Date fechaAprueba;
    private String comentariosAprueba;

    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;

    public FichaPrevencion() {
    }

    public FichaPrevencion(String idPrevencion) {
        this.idPrevencion = idPrevencion;
    }

    public FichaPrevencion(String idPrevencion, String folio, String idEstructura, Integer idTurno, String area, String sanitizante, String detergente, boolean interiorEquipo, boolean exteriorEquipo, boolean mobiliario, boolean paredes, boolean herramientas, boolean pisos, boolean pasilloIngreso, boolean areaLavado, boolean areaAcondicionado, boolean areaPreparado, boolean vestidor, boolean transfer, boolean puertas, boolean manijas, boolean ventanas, boolean areaDesinfeccion, boolean areaInspeccion, boolean areaAlmacenamiento, Integer idEstatusPrevencion, String idUsuarioRealizaLimpieza, Date fechaRelizaLimpieza, String comentariosRealizaLimpieza, String idUsuarioSupervisa, Date fechaSupervisa, String comentariosSupervisa, String idUsuarioAprueba, Date fechaAprueba, String comentariosAprueba, Date insertFecha, String insertIdUsuario, Date updateFecha, String updateIdUsuario) {
        this.idPrevencion = idPrevencion;
        this.folio = folio;
        this.idEstructura = idEstructura;
        this.idTurno = idTurno;
        this.area = area;
        this.sanitizante = sanitizante;
        this.detergente = detergente;
        this.interiorEquipo = interiorEquipo;
        this.exteriorEquipo = exteriorEquipo;
        this.mobiliario = mobiliario;
        this.paredes = paredes;
        this.herramientas = herramientas;
        this.pisos = pisos;
        this.pasilloIngreso = pasilloIngreso;
        this.areaLavado = areaLavado;
        this.areaAcondicionado = areaAcondicionado;
        this.areaPreparado = areaPreparado;
        this.vestidor = vestidor;
        this.transfer = transfer;
        this.puertas = puertas;
        this.manijas = manijas;
        this.ventanas = ventanas;
        this.areaDesinfeccion = areaDesinfeccion;
        this.areaInspeccion = areaInspeccion;
        this.areaAlmacenamiento = areaAlmacenamiento;
        this.idEstatusPrevencion = idEstatusPrevencion;
        this.idUsuarioRealizaLimpieza = idUsuarioRealizaLimpieza;
        this.fechaRelizaLimpieza = fechaRelizaLimpieza;
        this.comentariosRealizaLimpieza = comentariosRealizaLimpieza;
        this.idUsuarioSupervisa = idUsuarioSupervisa;
        this.fechaSupervisa = fechaSupervisa;
        this.comentariosSupervisa = comentariosSupervisa;
        this.idUsuarioAprueba = idUsuarioAprueba;
        this.fechaAprueba = fechaAprueba;
        this.comentariosAprueba = comentariosAprueba;
        this.insertFecha = insertFecha;
        this.insertIdUsuario = insertIdUsuario;
        this.updateFecha = updateFecha;
        this.updateIdUsuario = updateIdUsuario;
    }

    public String getIdPrevencion() {
        return idPrevencion;
    }

    public void setIdPrevencion(String idPrevencion) {
        this.idPrevencion = idPrevencion;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public Integer getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(Integer idTurno) {
        this.idTurno = idTurno;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSanitizante() {
        return sanitizante;
    }

    public void setSanitizante(String sanitizante) {
        this.sanitizante = sanitizante;
    }

    public String getDetergente() {
        return detergente;
    }

    public void setDetergente(String detergente) {
        this.detergente = detergente;
    }

    public boolean isInteriorEquipo() {
        return interiorEquipo;
    }

    public void setInteriorEquipo(boolean interiorEquipo) {
        this.interiorEquipo = interiorEquipo;
    }

    public boolean isExteriorEquipo() {
        return exteriorEquipo;
    }

    public void setExteriorEquipo(boolean exteriorEquipo) {
        this.exteriorEquipo = exteriorEquipo;
    }

    public boolean isMobiliario() {
        return mobiliario;
    }

    public void setMobiliario(boolean mobiliario) {
        this.mobiliario = mobiliario;
    }

    public boolean isParedes() {
        return paredes;
    }

    public void setParedes(boolean paredes) {
        this.paredes = paredes;
    }

    public boolean isHerramientas() {
        return herramientas;
    }

    public void setHerramientas(boolean herramientas) {
        this.herramientas = herramientas;
    }

    public boolean isPisos() {
        return pisos;
    }

    public void setPisos(boolean pisos) {
        this.pisos = pisos;
    }

    public boolean isPasilloIngreso() {
        return pasilloIngreso;
    }

    public void setPasilloIngreso(boolean pasilloIngreso) {
        this.pasilloIngreso = pasilloIngreso;
    }

    public boolean isAreaLavado() {
        return areaLavado;
    }

    public void setAreaLavado(boolean areaLavado) {
        this.areaLavado = areaLavado;
    }

    public boolean isAreaAcondicionado() {
        return areaAcondicionado;
    }

    public void setAreaAcondicionado(boolean areaAcondicionado) {
        this.areaAcondicionado = areaAcondicionado;
    }

    public boolean isAreaPreparado() {
        return areaPreparado;
    }

    public void setAreaPreparado(boolean areaPreparado) {
        this.areaPreparado = areaPreparado;
    }

    public boolean isVestidor() {
        return vestidor;
    }

    public void setVestidor(boolean vestidor) {
        this.vestidor = vestidor;
    }

    public boolean isTransfer() {
        return transfer;
    }

    public void setTransfer(boolean transfer) {
        this.transfer = transfer;
    }

    public boolean isPuertas() {
        return puertas;
    }

    public void setPuertas(boolean puertas) {
        this.puertas = puertas;
    }

    public boolean isManijas() {
        return manijas;
    }

    public void setManijas(boolean manijas) {
        this.manijas = manijas;
    }

    public boolean isVentanas() {
        return ventanas;
    }

    public void setVentanas(boolean ventanas) {
        this.ventanas = ventanas;
    }

    public boolean isAreaDesinfeccion() {
        return areaDesinfeccion;
    }

    public void setAreaDesinfeccion(boolean areaDesinfeccion) {
        this.areaDesinfeccion = areaDesinfeccion;
    }

    public boolean isAreaInspeccion() {
        return areaInspeccion;
    }

    public void setAreaInspeccion(boolean areaInspeccion) {
        this.areaInspeccion = areaInspeccion;
    }

    public boolean isAreaAlmacenamiento() {
        return areaAlmacenamiento;
    }

    public void setAreaAlmacenamiento(boolean areaAlmacenamiento) {
        this.areaAlmacenamiento = areaAlmacenamiento;
    }

    public Integer getIdEstatusPrevencion() {
        return idEstatusPrevencion;
    }

    public void setIdEstatusPrevencion(Integer idEstatusPrevencion) {
        this.idEstatusPrevencion = idEstatusPrevencion;
    }

    public String getIdUsuarioRealizaLimpieza() {
        return idUsuarioRealizaLimpieza;
    }

    public void setIdUsuarioRealizaLimpieza(String idUsuarioRealizaLimpieza) {
        this.idUsuarioRealizaLimpieza = idUsuarioRealizaLimpieza;
    }

    public Date getFechaRelizaLimpieza() {
        return fechaRelizaLimpieza;
    }

    public void setFechaRelizaLimpieza(Date fechaRelizaLimpieza) {
        this.fechaRelizaLimpieza = fechaRelizaLimpieza;
    }

    public String getComentariosRealizaLimpieza() {
        return comentariosRealizaLimpieza;
    }

    public void setComentariosRealizaLimpieza(String comentariosRealizaLimpieza) {
        this.comentariosRealizaLimpieza = comentariosRealizaLimpieza;
    }

    public String getIdUsuarioSupervisa() {
        return idUsuarioSupervisa;
    }

    public void setIdUsuarioSupervisa(String idUsuarioSupervisa) {
        this.idUsuarioSupervisa = idUsuarioSupervisa;
    }

    public Date getFechaSupervisa() {
        return fechaSupervisa;
    }

    public void setFechaSupervisa(Date fechaSupervisa) {
        this.fechaSupervisa = fechaSupervisa;
    }

    public String getComentariosSupervisa() {
        return comentariosSupervisa;
    }

    public void setComentariosSupervisa(String comentariosSupervisa) {
        this.comentariosSupervisa = comentariosSupervisa;
    }

    public String getIdUsuarioAprueba() {
        return idUsuarioAprueba;
    }

    public void setIdUsuarioAprueba(String idUsuarioAprueba) {
        this.idUsuarioAprueba = idUsuarioAprueba;
    }

    public Date getFechaAprueba() {
        return fechaAprueba;
    }

    public void setFechaAprueba(Date fechaAprueba) {
        this.fechaAprueba = fechaAprueba;
    }

    public String getComentariosAprueba() {
        return comentariosAprueba;
    }

    public void setComentariosAprueba(String comentariosAprueba) {
        this.comentariosAprueba = comentariosAprueba;
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
        return "PrevencionContaminacion{" + "idPrevencion=" + idPrevencion + ", folio=" + folio + ", idEstructura=" + idEstructura + ", idTurno=" + idTurno + ", area=" + area + ", sanitizante=" + sanitizante + ", detergente=" + detergente + ", interiorEquipo=" + interiorEquipo + ", exteriorEquipo=" + exteriorEquipo + ", mobiliario=" + mobiliario + ", paredes=" + paredes + ", herramientas=" + herramientas + ", pisos=" + pisos + ", pasilloIngreso=" + pasilloIngreso + ", areaLavado=" + areaLavado + ", areaAcondicionado=" + areaAcondicionado + ", areaPreparado=" + areaPreparado + ", vestidor=" + vestidor + ", transfer=" + transfer + ", puertas=" + puertas + ", manijas=" + manijas + ", ventanas=" + ventanas + ", areaDesinfeccion=" + areaDesinfeccion + ", areaInspeccion=" + areaInspeccion + ", areaAlmacenamiento=" + areaAlmacenamiento + ", idEstatusPrevencion=" + idEstatusPrevencion + ", idUsuarioRealizaLimpieza=" + idUsuarioRealizaLimpieza + ", fechaRelizaLimpieza=" + fechaRelizaLimpieza + ", comentariosRealizaLimpieza=" + comentariosRealizaLimpieza + ", idUsuarioSupervisa=" + idUsuarioSupervisa + ", fechaSupervisa=" + fechaSupervisa + ", comentariosSupervisa=" + comentariosSupervisa + ", idUsuarioAprueba=" + idUsuarioAprueba + ", fechaAprueba=" + fechaAprueba + ", comentariosAprueba=" + comentariosAprueba + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.idPrevencion);
        hash = 83 * hash + Objects.hashCode(this.folio);
        hash = 83 * hash + Objects.hashCode(this.idEstructura);
        hash = 83 * hash + Objects.hashCode(this.idTurno);
        hash = 83 * hash + Objects.hashCode(this.area);
        hash = 83 * hash + Objects.hashCode(this.sanitizante);
        hash = 83 * hash + Objects.hashCode(this.detergente);
        hash = 83 * hash + (this.interiorEquipo ? 1 : 0);
        hash = 83 * hash + (this.exteriorEquipo ? 1 : 0);
        hash = 83 * hash + (this.mobiliario ? 1 : 0);
        hash = 83 * hash + (this.paredes ? 1 : 0);
        hash = 83 * hash + (this.herramientas ? 1 : 0);
        hash = 83 * hash + (this.pisos ? 1 : 0);
        hash = 83 * hash + (this.pasilloIngreso ? 1 : 0);
        hash = 83 * hash + (this.areaLavado ? 1 : 0);
        hash = 83 * hash + (this.areaAcondicionado ? 1 : 0);
        hash = 83 * hash + (this.areaPreparado ? 1 : 0);
        hash = 83 * hash + (this.vestidor ? 1 : 0);
        hash = 83 * hash + (this.transfer ? 1 : 0);
        hash = 83 * hash + (this.puertas ? 1 : 0);
        hash = 83 * hash + (this.manijas ? 1 : 0);
        hash = 83 * hash + (this.ventanas ? 1 : 0);
        hash = 83 * hash + (this.areaDesinfeccion ? 1 : 0);
        hash = 83 * hash + (this.areaInspeccion ? 1 : 0);
        hash = 83 * hash + (this.areaAlmacenamiento ? 1 : 0);
        hash = 83 * hash + Objects.hashCode(this.idEstatusPrevencion);
        hash = 83 * hash + Objects.hashCode(this.idUsuarioRealizaLimpieza);
        hash = 83 * hash + Objects.hashCode(this.fechaRelizaLimpieza);
        hash = 83 * hash + Objects.hashCode(this.comentariosRealizaLimpieza);
        hash = 83 * hash + Objects.hashCode(this.idUsuarioSupervisa);
        hash = 83 * hash + Objects.hashCode(this.fechaSupervisa);
        hash = 83 * hash + Objects.hashCode(this.comentariosSupervisa);
        hash = 83 * hash + Objects.hashCode(this.idUsuarioAprueba);
        hash = 83 * hash + Objects.hashCode(this.fechaAprueba);
        hash = 83 * hash + Objects.hashCode(this.comentariosAprueba);
        hash = 83 * hash + Objects.hashCode(this.insertFecha);
        hash = 83 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 83 * hash + Objects.hashCode(this.updateFecha);
        hash = 83 * hash + Objects.hashCode(this.updateIdUsuario);
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
        final FichaPrevencion other = (FichaPrevencion) obj;
        if (this.interiorEquipo != other.interiorEquipo) {
            return false;
        }
        if (this.exteriorEquipo != other.exteriorEquipo) {
            return false;
        }
        if (this.mobiliario != other.mobiliario) {
            return false;
        }
        if (this.paredes != other.paredes) {
            return false;
        }
        if (this.herramientas != other.herramientas) {
            return false;
        }
        if (this.pisos != other.pisos) {
            return false;
        }
        if (this.pasilloIngreso != other.pasilloIngreso) {
            return false;
        }
        if (this.areaLavado != other.areaLavado) {
            return false;
        }
        if (this.areaAcondicionado != other.areaAcondicionado) {
            return false;
        }
        if (this.areaPreparado != other.areaPreparado) {
            return false;
        }
        if (this.vestidor != other.vestidor) {
            return false;
        }
        if (this.transfer != other.transfer) {
            return false;
        }
        if (this.puertas != other.puertas) {
            return false;
        }
        if (this.manijas != other.manijas) {
            return false;
        }
        if (this.ventanas != other.ventanas) {
            return false;
        }
        if (this.areaDesinfeccion != other.areaDesinfeccion) {
            return false;
        }
        if (this.areaInspeccion != other.areaInspeccion) {
            return false;
        }
        if (this.areaAlmacenamiento != other.areaAlmacenamiento) {
            return false;
        }
        if (!Objects.equals(this.idPrevencion, other.idPrevencion)) {
            return false;
        }
        if (!Objects.equals(this.folio, other.folio)) {
            return false;
        }
        if (!Objects.equals(this.idEstructura, other.idEstructura)) {
            return false;
        }
        if (!Objects.equals(this.idTurno, other.idTurno)) {
            return false;
        }
        if (!Objects.equals(this.area, other.area)) {
            return false;
        }
        if (!Objects.equals(this.sanitizante, other.sanitizante)) {
            return false;
        }
        if (!Objects.equals(this.detergente, other.detergente)) {
            return false;
        }
        if (!Objects.equals(this.idUsuarioRealizaLimpieza, other.idUsuarioRealizaLimpieza)) {
            return false;
        }
        if (!Objects.equals(this.comentariosRealizaLimpieza, other.comentariosRealizaLimpieza)) {
            return false;
        }
        if (!Objects.equals(this.idUsuarioSupervisa, other.idUsuarioSupervisa)) {
            return false;
        }
        if (!Objects.equals(this.comentariosSupervisa, other.comentariosSupervisa)) {
            return false;
        }
        if (!Objects.equals(this.idUsuarioAprueba, other.idUsuarioAprueba)) {
            return false;
        }
        if (!Objects.equals(this.comentariosAprueba, other.comentariosAprueba)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.idEstatusPrevencion, other.idEstatusPrevencion)) {
            return false;
        }
        if (!Objects.equals(this.fechaRelizaLimpieza, other.fechaRelizaLimpieza)) {
            return false;
        }
        if (!Objects.equals(this.fechaSupervisa, other.fechaSupervisa)) {
            return false;
        }
        if (!Objects.equals(this.fechaAprueba, other.fechaAprueba)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        return Objects.equals(this.updateFecha, other.updateFecha);
    }

}
