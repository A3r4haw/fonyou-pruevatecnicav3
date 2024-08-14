package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Cervanets
 */
public class ImpresionEtiqueta implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idImpresionEtiqueta;
    private String idSurtimiento;
    private String folio;
    private String contenido;
    private boolean impresionExitosa;
    private Integer numeroImpresiones;
    private boolean reimpresion;
    private Integer idMotivoReimpresion;
    private String notas;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;

    public ImpresionEtiqueta() {
    }

    public ImpresionEtiqueta(String idImpresionEtiqueta) {
        this.idImpresionEtiqueta = idImpresionEtiqueta;
    }

    public ImpresionEtiqueta(String idImpresionEtiqueta, String idSurtimiento, String folio) {
        this.idImpresionEtiqueta = idImpresionEtiqueta;
        this.idSurtimiento = idSurtimiento;
        this.folio = folio;
    }

    public String getIdImpresionEtiqueta() {
        return idImpresionEtiqueta;
    }

    public void setIdImpresionEtiqueta(String idImpresionEtiqueta) {
        this.idImpresionEtiqueta = idImpresionEtiqueta;
    }

    public String getIdSurtimiento() {
        return idSurtimiento;
    }

    public void setIdSurtimiento(String idSurtimiento) {
        this.idSurtimiento = idSurtimiento;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public boolean isImpresionExitosa() {
        return impresionExitosa;
    }

    public void setImpresionExitosa(boolean impresionExitosa) {
        this.impresionExitosa = impresionExitosa;
    }

    public Integer getNumeroImpresiones() {
        return numeroImpresiones;
    }

    public void setNumeroImpresiones(Integer numeroImpresiones) {
        this.numeroImpresiones = numeroImpresiones;
    }

    public boolean isReimpresion() {
        return reimpresion;
    }

    public void setReimpresion(boolean reimpresion) {
        this.reimpresion = reimpresion;
    }

    public Integer getIdMotivoReimpresion() {
        return idMotivoReimpresion;
    }

    public void setIdMotivoReimpresion(Integer idMotivoReimpresion) {
        this.idMotivoReimpresion = idMotivoReimpresion;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
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
        return "ImpresionEtiqueta{" + "idImpresionEtiqueta=" + idImpresionEtiqueta + ", idSurtimiento=" + idSurtimiento + ", folio=" + folio + ", contenido=" + contenido + ", impresionExitosa=" + impresionExitosa + ", numeroImpresiones=" + numeroImpresiones + ", reimpresion=" + reimpresion + ", idMotivoReimpresion=" + idMotivoReimpresion + ", notas=" + notas + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.idImpresionEtiqueta);
        hash = 83 * hash + Objects.hashCode(this.idSurtimiento);
        hash = 83 * hash + Objects.hashCode(this.folio);
        hash = 83 * hash + Objects.hashCode(this.contenido);
        hash = 83 * hash + (this.impresionExitosa ? 1 : 0);
        hash = 83 * hash + Objects.hashCode(this.numeroImpresiones);
        hash = 83 * hash + (this.reimpresion ? 1 : 0);
        hash = 83 * hash + Objects.hashCode(this.idMotivoReimpresion);
        hash = 83 * hash + Objects.hashCode(this.notas);
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
        final ImpresionEtiqueta other = (ImpresionEtiqueta) obj;
        if (this.impresionExitosa != other.impresionExitosa) {
            return false;
        }
        if (this.reimpresion != other.reimpresion) {
            return false;
        }
        if (!Objects.equals(this.idImpresionEtiqueta, other.idImpresionEtiqueta)) {
            return false;
        }
        if (!Objects.equals(this.idSurtimiento, other.idSurtimiento)) {
            return false;
        }
        if (!Objects.equals(this.folio, other.folio)) {
            return false;
        }
        if (!Objects.equals(this.contenido, other.contenido)) {
            return false;
        }
        if (!Objects.equals(this.notas, other.notas)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.numeroImpresiones, other.numeroImpresiones)) {
            return false;
        }
        if (!Objects.equals(this.idMotivoReimpresion, other.idMotivoReimpresion)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        return Objects.equals(this.updateFecha, other.updateFecha);
    }

}
