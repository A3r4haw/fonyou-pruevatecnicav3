/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.io.Serializable;

/**
 *
 * @author Ulai
 */
public class AplicationMovil implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private String idVersion;
    private String nombre;
    private Integer versionCode;
    private String versionName;
    private String url;
    private String rollBack;
    private String descripcion;
    private boolean activo;
    
    public AplicationMovil() {

    }

    public AplicationMovil(String nombre, Integer versionCode, String versionName, String Descripcion, boolean activo) {
        this.nombre = nombre;
        this.versionCode = versionCode;
        this.versionName = versionName;
        this.descripcion = Descripcion;
        this.activo = activo;
    }

    public String getIdVersion() {
        return idVersion;
    }

    public void setIdVersion(String idVersion) {
        this.idVersion = idVersion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
    }
    

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRollBack() {
        return rollBack;
    }

    public void setRollBack(String rollBack) {
        this.rollBack = rollBack;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
