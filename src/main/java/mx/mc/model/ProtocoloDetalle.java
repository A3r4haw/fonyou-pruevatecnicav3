package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author hramirez
 */
public class ProtocoloDetalle implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idProtocoloDetalle;
    private String idProtocolo;
    private String idInsumo;
    private Double dosis;
    private Double area;
    private Double peso;
    private Integer frecuencia;
    private Integer periodo;
    private Integer ciclos;
    private Integer receso;
    private Integer idEstatus;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;

    public ProtocoloDetalle() {
    }

    public ProtocoloDetalle(String idProtocoloDetalle) {
        this.idProtocoloDetalle = idProtocoloDetalle;
    }

    public String getIdProtocoloDetalle() {
        return idProtocoloDetalle;
    }

    public void setIdProtocoloDetalle(String idProtocoloDetalle) {
        this.idProtocoloDetalle = idProtocoloDetalle;
    }

    public String getIdProtocolo() {
        return idProtocolo;
    }

    public void setIdProtocolo(String idProtocolo) {
        this.idProtocolo = idProtocolo;
    }

    public String getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(String idInsumo) {
        this.idInsumo = idInsumo;
    }

    public Double getDosis() {
        return dosis;
    }

    public void setDosis(Double dosis) {
        this.dosis = dosis;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Integer getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(Integer frecuencia) {
        this.frecuencia = frecuencia;
    }

    public Integer getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Integer periodo) {
        this.periodo = periodo;
    }

    public Integer getCiclos() {
        return ciclos;
    }

    public void setCiclos(Integer ciclos) {
        this.ciclos = ciclos;
    }

    public Integer getReceso() {
        return receso;
    }

    public void setReceso(Integer receso) {
        this.receso = receso;
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
        return "ProtocoloDetalle{" + "idProtocoloDetalle=" + idProtocoloDetalle + ", idProtocolo=" + idProtocolo + ", idInsumo=" + idInsumo + ", dosis=" + dosis + ", area=" + area + ", peso=" + peso + ", frecuencia=" + frecuencia + ", periodo=" + periodo + ", ciclos=" + ciclos + ", receso=" + receso + ", idEstatus=" + idEstatus + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.idProtocoloDetalle);
        hash = 67 * hash + Objects.hashCode(this.idProtocolo);
        hash = 67 * hash + Objects.hashCode(this.idInsumo);
        hash = 67 * hash + Objects.hashCode(this.dosis);
        hash = 67 * hash + Objects.hashCode(this.area);
        hash = 67 * hash + Objects.hashCode(this.peso);
        hash = 67 * hash + Objects.hashCode(this.frecuencia);
        hash = 67 * hash + Objects.hashCode(this.periodo);
        hash = 67 * hash + Objects.hashCode(this.ciclos);
        hash = 67 * hash + Objects.hashCode(this.receso);
        hash = 67 * hash + Objects.hashCode(this.idEstatus);
        hash = 67 * hash + Objects.hashCode(this.insertFecha);
        hash = 67 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 67 * hash + Objects.hashCode(this.updateFecha);
        hash = 67 * hash + Objects.hashCode(this.updateIdUsuario);
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
        final ProtocoloDetalle other = (ProtocoloDetalle) obj;
        if (!Objects.equals(this.idProtocoloDetalle, other.idProtocoloDetalle)) {
            return false;
        }
        if (!Objects.equals(this.idProtocolo, other.idProtocolo)) {
            return false;
        }
        if (!Objects.equals(this.idInsumo, other.idInsumo)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.dosis, other.dosis)) {
            return false;
        }
        if (!Objects.equals(this.area, other.area)) {
            return false;
        }
        if (!Objects.equals(this.peso, other.peso)) {
            return false;
        }
        if (!Objects.equals(this.frecuencia, other.frecuencia)) {
            return false;
        }
        if (!Objects.equals(this.periodo, other.periodo)) {
            return false;
        }
        if (!Objects.equals(this.ciclos, other.ciclos)) {
            return false;
        }
        if (!Objects.equals(this.receso, other.receso)) {
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
