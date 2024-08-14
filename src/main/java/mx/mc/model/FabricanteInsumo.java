/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Cervanets
 */
public class FabricanteInsumo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idFabricanteInsumo;
    private String idFabricante;
    private String idInsumo;
    private Double osmolaridad;
    private Double densidad;
    private Double calorias;
    private Integer estabilidad;
    private String claveProveedor;
    private Integer idEstatus;
    private String insertIdUsuario;
    private Date insertFecha;
    private String updateIdUsuario;
    private Date updateFecha;

    public FabricanteInsumo() {
    }

    public FabricanteInsumo(String idFabricanteInsumo) {
        this.idFabricanteInsumo = idFabricanteInsumo;
    }

    public FabricanteInsumo(String idFabricanteInsumo, String idFabricante, String idInsumo, Double osmolaridad, Double densidad, Double calorias, Integer estabilidad, String claveProveedor, Integer idEstatus, String insertIdUsuario, Date insertFecha, String updateIdUsuario, Date updateFecha) {
        this.idFabricanteInsumo = idFabricanteInsumo;
        this.idFabricante = idFabricante;
        this.idInsumo = idInsumo;
        this.osmolaridad = osmolaridad;
        this.densidad = densidad;
        this.calorias = calorias;
        this.estabilidad = estabilidad;
        this.claveProveedor = claveProveedor;
        this.idEstatus = idEstatus;
        this.insertIdUsuario = insertIdUsuario;
        this.insertFecha = insertFecha;
        this.updateIdUsuario = updateIdUsuario;
        this.updateFecha = updateFecha;
    }

    public String getIdFabricanteInsumo() {
        return idFabricanteInsumo;
    }

    public void setIdFabricanteInsumo(String idFabricanteInsumo) {
        this.idFabricanteInsumo = idFabricanteInsumo;
    }

    public String getIdFabricante() {
        return idFabricante;
    }

    public void setIdFabricante(String idFabricante) {
        this.idFabricante = idFabricante;
    }

    public String getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(String idInsumo) {
        this.idInsumo = idInsumo;
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

    public Integer getEstabilidad() {
        return estabilidad;
    }

    public void setEstabilidad(Integer estabilidad) {
        this.estabilidad = estabilidad;
    }

    public String getClaveProveedor() {
        return claveProveedor;
    }

    public void setClaveProveedor(String claveProveedor) {
        this.claveProveedor = claveProveedor;
    }

    public Integer getIdEstatus() {
        return idEstatus;
    }

    public void setIdEstatus(Integer idEstatus) {
        this.idEstatus = idEstatus;
    }

    public String getInsertIdUsuario() {
        return insertIdUsuario;
    }

    public void setInsertIdUsuario(String insertIdUsuario) {
        this.insertIdUsuario = insertIdUsuario;
    }

    public Date getInsertFecha() {
        return insertFecha;
    }

    public void setInsertFecha(Date insertFecha) {
        this.insertFecha = insertFecha;
    }

    public String getUpdateIdUsuario() {
        return updateIdUsuario;
    }

    public void setUpdateIdUsuario(String updateIdUsuario) {
        this.updateIdUsuario = updateIdUsuario;
    }

    public Date getUpdateFecha() {
        return updateFecha;
    }

    public void setUpdateFecha(Date updateFecha) {
        this.updateFecha = updateFecha;
    }

    @Override
    public String toString() {
        return "FabricanteInsumo{" + "idFabricanteInsumo=" + idFabricanteInsumo + ", idFabricante=" + idFabricante + ", idInsumo=" + idInsumo + ", osmolaridad=" + osmolaridad + ", densidad=" + densidad + ", calorias=" + calorias + ", estabilidad=" + estabilidad + ", claveProveedor=" + claveProveedor + ", idEstatus=" + idEstatus + ", insertIdUsuario=" + insertIdUsuario + ", insertFecha=" + insertFecha + ", updateIdUsuario=" + updateIdUsuario + ", updateFecha=" + updateFecha + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.idFabricanteInsumo);
        hash = 29 * hash + Objects.hashCode(this.idFabricante);
        hash = 29 * hash + Objects.hashCode(this.idInsumo);
        hash = 29 * hash + Objects.hashCode(this.osmolaridad);
        hash = 29 * hash + Objects.hashCode(this.densidad);
        hash = 29 * hash + Objects.hashCode(this.calorias);
        hash = 29 * hash + Objects.hashCode(this.estabilidad);
        hash = 29 * hash + Objects.hashCode(this.claveProveedor);
        hash = 29 * hash + Objects.hashCode(this.idEstatus);
        hash = 29 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 29 * hash + Objects.hashCode(this.insertFecha);
        hash = 29 * hash + Objects.hashCode(this.updateIdUsuario);
        hash = 29 * hash + Objects.hashCode(this.updateFecha);
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
        final FabricanteInsumo other = (FabricanteInsumo) obj;
        if (!Objects.equals(this.idFabricanteInsumo, other.idFabricanteInsumo)) {
            return false;
        }
        if (!Objects.equals(this.idFabricante, other.idFabricante)) {
            return false;
        }
        if (!Objects.equals(this.idInsumo, other.idInsumo)) {
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
        if (!Objects.equals(this.osmolaridad, other.osmolaridad)) {
            return false;
        }
        if (!Objects.equals(this.densidad, other.densidad)) {
            return false;
        }
        if (!Objects.equals(this.calorias, other.calorias)) {
            return false;
        }
        if (!Objects.equals(this.estabilidad, other.estabilidad)) {
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
