/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.util.Date;

/**
 *
 * @author bbautista
 */
public class TipoReaccion {
   private Integer idTipoReaccion;
  private String claveReaccion;
  private String reaccion;
  private String descripcion;
  private Integer idMorbilidad;
  private Integer idMortalidad;
  private Integer activa;
  private Date insertFecha;
  private String insertIdUsuario;
  private Date updateFecha;
  private String updateIdUsuario;

    public Integer getIdTipoReaccion() {
        return idTipoReaccion;
    }

    public void setIdTipoReaccion(Integer idTipoReaccion) {
        this.idTipoReaccion = idTipoReaccion;
    }

    public String getClaveReaccion() {
        return claveReaccion;
    }

    public void setClaveReaccion(String claveReaccion) {
        this.claveReaccion = claveReaccion;
    }

    public String getReaccion() {
        return reaccion;
    }

    public void setReaccion(String reaccion) {
        this.reaccion = reaccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getIdMorbilidad() {
        return idMorbilidad;
    }

    public void setIdMorbilidad(Integer idMorbilidad) {
        this.idMorbilidad = idMorbilidad;
    }

    public Integer getIdMortalidad() {
        return idMortalidad;
    }

    public void setIdMortalidad(Integer idMortalidad) {
        this.idMortalidad = idMortalidad;
    }

    public Integer getActiva() {
        return activa;
    }

    public void setActiva(Integer activa) {
        this.activa = activa;
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
