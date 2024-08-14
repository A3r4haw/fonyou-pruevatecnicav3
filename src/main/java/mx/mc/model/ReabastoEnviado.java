package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author AORTIZ
 */
public class ReabastoEnviado implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idReabastoEnviado;
    private String idReabastoInsumo;
    private String idInventarioSurtido;
    private int cantidadEnviado;
    private int cantidadRecibida;
    private int cantidadIngresada;
    private int idEstatusReabasto;
    private String idInsumo;
    private String idEstructura;
    private String loteEnv;
    private Date fechaCad;
    private Integer presentacionComercial;
    private Integer cantidadXCaja;
    private String claveProveedor;
    private Integer estatusAVG;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;
    private String claveOriginalSurtida;

    private Integer idFabricante;
    private String idProveedor;
    private Double osmolaridad;
    private Double densidad;
    private Double calorias;
    private Integer noHorasEstabilidad;
    private String noRegistro;
    private double costo;

    public ReabastoEnviado() {
    }

    public ReabastoEnviado(String idReabastoEnviado) {
        this.idReabastoEnviado = idReabastoEnviado;
    }

    public String getIdReabastoEnviado() {
        return idReabastoEnviado;
    }

    public void setIdReabastoEnviado(String idReabastoEnviado) {
        this.idReabastoEnviado = idReabastoEnviado;
    }

    public String getIdReabastoInsumo() {
        return idReabastoInsumo;
    }

    public void setIdReabastoInsumo(String idReabastoInsumo) {
        this.idReabastoInsumo = idReabastoInsumo;
    }

    public String getIdInventarioSurtido() {
        return idInventarioSurtido;
    }

    public void setIdInventarioSurtido(String idInventarioSurtido) {
        this.idInventarioSurtido = idInventarioSurtido;
    }

    public int getCantidadEnviado() {
        return cantidadEnviado;
    }

    public void setCantidadEnviado(int cantidadEnviado) {
        this.cantidadEnviado = cantidadEnviado;
    }

    public int getCantidadRecibida() {
        return cantidadRecibida;
    }

    public void setCantidadRecibida(int cantidadRecibida) {
        this.cantidadRecibida = cantidadRecibida;
    }

    public int getCantidadIngresada() {
        return cantidadIngresada;
    }

    public void setCantidadIngresada(int cantidadIngresada) {
        this.cantidadIngresada = cantidadIngresada;
    }

    public int getIdEstatusReabasto() {
        return idEstatusReabasto;
    }

    public void setIdEstatusReabasto(int idEstatusReabasto) {
        this.idEstatusReabasto = idEstatusReabasto;
    }

    public String getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(String idInsumo) {
        this.idInsumo = idInsumo;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public String getLoteEnv() {
        return loteEnv;
    }

    public void setLoteEnv(String loteEnv) {
        this.loteEnv = loteEnv;
    }

    public Date getFechaCad() {
        return fechaCad;
    }

    public void setFechaCad(Date fechaCad) {
        this.fechaCad = fechaCad;
    }

    public Integer getPresentacionComercial() {
        return presentacionComercial;
    }

    public void setPresentacionComercial(Integer presentacionComercial) {
        this.presentacionComercial = presentacionComercial;
    }

    public Integer getCantidadXCaja() {
        return cantidadXCaja;
    }

    public void setCantidadXCaja(Integer cantidadXCaja) {
        this.cantidadXCaja = cantidadXCaja;
    }

    public String getClaveProveedor() {
        return claveProveedor;
    }

    public void setClaveProveedor(String claveProveedor) {
        this.claveProveedor = claveProveedor;
    }

    public Integer getEstatusAVG() {
        return estatusAVG;
    }

    public void setEstatusAVG(Integer estatusAVG) {
        this.estatusAVG = estatusAVG;
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

    public String getClaveOriginalSurtida() {
        return claveOriginalSurtida;
    }

    public void setClaveOriginalSurtida(String claveOriginalSurtida) {
        this.claveOriginalSurtida = claveOriginalSurtida;
    }

    public Integer getIdFabricante() {
        return idFabricante;
    }

    public void setIdFabricante(Integer idFabricante) {
        this.idFabricante = idFabricante;
    }

    public String getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(String idProveedor) {
        this.idProveedor = idProveedor;
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

    public Integer getNoHorasEstabilidad() {
        return noHorasEstabilidad;
    }

    public void setNoHorasEstabilidad(Integer noHorasEstabilidad) {
        this.noHorasEstabilidad = noHorasEstabilidad;
    }

    public String getNoRegistro() {
        return noRegistro;
    }

    public void setNoRegistro(String noRegistro) {
        this.noRegistro = noRegistro;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    @Override
    public String toString() {
        return "ReabastoEnviado{" + "idReabastoEnviado=" + idReabastoEnviado + ", idReabastoInsumo=" + idReabastoInsumo + ", idInventarioSurtido=" + idInventarioSurtido + ", cantidadEnviado=" + cantidadEnviado + ", cantidadRecibida=" + cantidadRecibida + ", cantidadIngresada=" + cantidadIngresada + ", idEstatusReabasto=" + idEstatusReabasto + ", idInsumo=" + idInsumo + ", idEstructura=" + idEstructura + ", loteEnv=" + loteEnv + ", fechaCad=" + fechaCad + ", presentacionComercial=" + presentacionComercial + ", cantidadXCaja=" + cantidadXCaja + ", claveProveedor=" + claveProveedor + ", estatusAVG=" + estatusAVG + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + ", claveOriginalSurtida=" + claveOriginalSurtida + ", idFabricante=" + idFabricante + ", idProveedor=" + idProveedor + ", osmolaridad=" + osmolaridad + ", densidad=" + densidad + ", calorias=" + calorias + ", noHorasEstabilidad=" + noHorasEstabilidad + ", noRegistro=" + noRegistro + ", costo=" + costo + '}';
    }
        
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.idReabastoEnviado);
        hash = 11 * hash + Objects.hashCode(this.idReabastoInsumo);
        hash = 11 * hash + Objects.hashCode(this.idInventarioSurtido);
        hash = 11 * hash + this.cantidadEnviado;
        hash = 11 * hash + this.cantidadRecibida;
        hash = 11 * hash + this.cantidadIngresada;
        hash = 11 * hash + this.idEstatusReabasto;
        hash = 11 * hash + Objects.hashCode(this.idInsumo);
        hash = 11 * hash + Objects.hashCode(this.idEstructura);
        hash = 11 * hash + Objects.hashCode(this.loteEnv);
        hash = 11 * hash + Objects.hashCode(this.fechaCad);
        hash = 11 * hash + Objects.hashCode(this.presentacionComercial);
        hash = 11 * hash + Objects.hashCode(this.cantidadXCaja);
        hash = 11 * hash + Objects.hashCode(this.claveProveedor);
        hash = 11 * hash + Objects.hashCode(this.estatusAVG);
        hash = 11 * hash + Objects.hashCode(this.insertFecha);
        hash = 11 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 11 * hash + Objects.hashCode(this.updateFecha);
        hash = 11 * hash + Objects.hashCode(this.updateIdUsuario);
        hash = 11 * hash + Objects.hashCode(this.claveOriginalSurtida);
        hash = 11 * hash + Objects.hashCode(this.idFabricante);
        hash = 11 * hash + Objects.hashCode(this.idProveedor);
        hash = 11 * hash + Objects.hashCode(this.osmolaridad);
        hash = 11 * hash + Objects.hashCode(this.densidad);
        hash = 11 * hash + Objects.hashCode(this.calorias);
        hash = 11 * hash + Objects.hashCode(this.noHorasEstabilidad);
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
        final ReabastoEnviado other = (ReabastoEnviado) obj;
        if (this.cantidadEnviado != other.cantidadEnviado) {
            return false;
        }
        if (this.cantidadRecibida != other.cantidadRecibida) {
            return false;
        }
        if (this.cantidadIngresada != other.cantidadIngresada) {
            return false;
        }
        if (this.idEstatusReabasto != other.idEstatusReabasto) {
            return false;
        }
        if (!Objects.equals(this.idReabastoEnviado, other.idReabastoEnviado)) {
            return false;
        }
        if (!Objects.equals(this.idReabastoInsumo, other.idReabastoInsumo)) {
            return false;
        }
        if (!Objects.equals(this.idInventarioSurtido, other.idInventarioSurtido)) {
            return false;
        }
        if (!Objects.equals(this.idInsumo, other.idInsumo)) {
            return false;
        }
        if (!Objects.equals(this.idEstructura, other.idEstructura)) {
            return false;
        }
        if (!Objects.equals(this.loteEnv, other.loteEnv)) {
            return false;
        }
        if (!Objects.equals(this.claveProveedor, other.claveProveedor)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.claveOriginalSurtida, other.claveOriginalSurtida)) {
            return false;
        }
        if (!Objects.equals(this.idFabricante, other.idFabricante)) {
            return false;
        }
        if (!Objects.equals(this.idProveedor, other.idProveedor)) {
            return false;
        }
        if (!Objects.equals(this.fechaCad, other.fechaCad)) {
            return false;
        }
        if (!Objects.equals(this.presentacionComercial, other.presentacionComercial)) {
            return false;
        }
        if (!Objects.equals(this.cantidadXCaja, other.cantidadXCaja)) {
            return false;
        }
        if (!Objects.equals(this.estatusAVG, other.estatusAVG)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        if (!Objects.equals(this.updateFecha, other.updateFecha)) {
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
        return Objects.equals(this.noHorasEstabilidad, other.noHorasEstabilidad);
    }

    
    
}
