package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author hramirez
 */
public class Surtimiento implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idSurtimiento;
    private String idEstructuraAlmacen;
    private String idPrescripcion;
    private Date fechaProgramada;
    private String folio;
    private Integer idEstatusSurtimiento;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;
    private Integer idEstatusMirth;
    private Integer procesado;

    private int idTipoMotivoReenvio;
    private String comentario;
    private Integer numeroMedicacion;
    private boolean actualizar;
    private int idTipoCancelacion;
    private boolean parcial;
    private String claveAgrupada;
    private Date fechaPrepara;
    private String idUsuarioPrepara;
    // todo: Cambiar a surtimiento_Extended
    private boolean padecimientoCronico;
    private boolean diabetes;
    private boolean hipertension;
    private boolean problemasRenales;

    private List<SurtimientoInsumo> detalle;

    private String idCapsula;

    public Surtimiento() {
    }

    public Surtimiento(String idSurtimiento) {
        this.idSurtimiento = idSurtimiento;
    }

    public Surtimiento(String idSurtimiento, Integer procesado) {
        this.idSurtimiento = idSurtimiento;
        this.procesado = procesado;
    }

    public String getIdSurtimiento() {
        return idSurtimiento;
    }

    public void setIdSurtimiento(String idSurtimiento) {
        this.idSurtimiento = idSurtimiento;
    }

    public String getIdEstructuraAlmacen() {
        return idEstructuraAlmacen;
    }

    public void setIdEstructuraAlmacen(String idEstructuraAlmacen) {
        this.idEstructuraAlmacen = idEstructuraAlmacen;
    }

    public String getIdPrescripcion() {
        return idPrescripcion;
    }

    public void setIdPrescripcion(String idPrescripcion) {
        this.idPrescripcion = idPrescripcion;
    }

    public Date getFechaProgramada() {
        return fechaProgramada;
    }

    public void setFechaProgramada(Date fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public Integer getIdEstatusSurtimiento() {
        return idEstatusSurtimiento;
    }

    public void setIdEstatusSurtimiento(Integer idEstatusSurtimiento) {
        this.idEstatusSurtimiento = idEstatusSurtimiento;
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

    public List<SurtimientoInsumo> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<SurtimientoInsumo> detalle) {
        this.detalle = detalle;
    }

    public Integer getProcesado() {
        return procesado;
    }

    public void setProcesado(Integer procesado) {
        this.procesado = procesado;
    }

    public Integer getIdEstatusMirth() {
        return idEstatusMirth;
    }

    public void setIdEstatusMirth(Integer idEstatusMirth) {
        this.idEstatusMirth = idEstatusMirth;
    }

    public int getIdTipoMotivoReenvio() {
        return idTipoMotivoReenvio;
    }

    public void setIdTipoMotivoReenvio(int idTipoMotivoReenvio) {
        this.idTipoMotivoReenvio = idTipoMotivoReenvio;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getIdTipoCancelacion() {
        return idTipoCancelacion;
    }

    public void setIdTipoCancelacion(int idTipoCancelacion) {
        this.idTipoCancelacion = idTipoCancelacion;
    }

    public String getIdCapsula() {
        return idCapsula;
    }

    public void setIdCapsula(String idCapsula) {
        this.idCapsula = idCapsula;
    }

    public Integer getNumeroMedicacion() {
        return numeroMedicacion;
    }

    public void setNumeroMedicacion(Integer numeroMedicacion) {
        this.numeroMedicacion = numeroMedicacion;
    }

    public boolean isActualizar() {
        return actualizar;
    }

    public void setActualizar(boolean actualizar) {
        this.actualizar = actualizar;
    }

    public boolean isParcial() {
        return parcial;
    }

    public void setParcial(boolean parcial) {
        this.parcial = parcial;
    }

    public String getClaveAgrupada() {
        return claveAgrupada;
    }

    public void setClaveAgrupada(String claveAgrupada) {
        this.claveAgrupada = claveAgrupada;
    }

    @Override
    public String toString() {
        return "Surtimiento{" + "idSurtimiento=" + idSurtimiento + ", idEstructuraAlmacen=" + idEstructuraAlmacen + ", idPrescripcion=" + idPrescripcion + ", fechaProgramada=" + fechaProgramada + ", folio=" + folio + ", idEstatusSurtimiento=" + idEstatusSurtimiento + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + ", idEstatusMirth=" + idEstatusMirth + ", procesado=" + procesado + ", idTipoMotivoReenvio=" + idTipoMotivoReenvio + ", comentario=" + comentario + ", idTipoCancelacion=" + idTipoCancelacion + ", detalle=" + detalle + ", idCapsula=" + idCapsula + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.idSurtimiento);
        hash = 43 * hash + Objects.hashCode(this.idEstructuraAlmacen);
        hash = 43 * hash + Objects.hashCode(this.idPrescripcion);
        hash = 43 * hash + Objects.hashCode(this.fechaProgramada);
        hash = 43 * hash + Objects.hashCode(this.folio);
        hash = 43 * hash + Objects.hashCode(this.idEstatusSurtimiento);
        hash = 43 * hash + Objects.hashCode(this.insertFecha);
        hash = 43 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 43 * hash + Objects.hashCode(this.updateFecha);
        hash = 43 * hash + Objects.hashCode(this.updateIdUsuario);
        hash = 43 * hash + Objects.hashCode(this.idEstatusMirth);
        hash = 43 * hash + Objects.hashCode(this.procesado);
        hash = 43 * hash + this.idTipoMotivoReenvio;
        hash = 43 * hash + Objects.hashCode(this.comentario);
        hash = 43 * hash + this.idTipoCancelacion;
        hash = 43 * hash + Objects.hashCode(this.detalle);
        hash = 43 * hash + Objects.hashCode(this.idCapsula);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Surtimiento other = (Surtimiento) obj;
        if (!Objects.equals(this.idSurtimiento, other.idSurtimiento)) {
            return false;
        }
        if (!Objects.equals(this.idEstructuraAlmacen, other.idEstructuraAlmacen)) {
            return false;
        }
        if (!Objects.equals(this.idPrescripcion, other.idPrescripcion)) {
            return false;
        }
        if (!Objects.equals(this.fechaProgramada, other.fechaProgramada)) {
            return false;
        }
        if (!Objects.equals(this.folio, other.folio)) {
            return false;
        }
        if (!Objects.equals(this.idEstatusSurtimiento, other.idEstatusSurtimiento)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateFecha, other.updateFecha)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.idEstatusMirth, other.idEstatusMirth)) {
            return false;
        }
        if (!Objects.equals(this.procesado, other.procesado)) {
            return false;
        }
        if (this.idTipoMotivoReenvio != other.idTipoMotivoReenvio) {
            return false;
        }
        if (!Objects.equals(this.comentario, other.comentario)) {
            return false;
        }
        if (this.idTipoCancelacion != other.idTipoCancelacion) {
            return false;
        }
        if (!Objects.equals(this.detalle, other.detalle)) {
            return false;
        }
        return Objects.equals(this.idCapsula, other.idCapsula);
    }

    public Date getFechaPrepara() {
        return fechaPrepara;
    }

    public void setFechaPrepara(Date fechaPrepara) {
        this.fechaPrepara = fechaPrepara;
    }

    public String getIdUsuarioPrepara() {
        return idUsuarioPrepara;
    }

    public void setIdUsuarioPrepara(String idUsuarioPrepara) {
        this.idUsuarioPrepara = idUsuarioPrepara;
    }

    public boolean isPadecimientoCronico() {
        return padecimientoCronico;
    }

    public void setPadecimientoCronico(boolean padecimientoCronico) {
        this.padecimientoCronico = padecimientoCronico;
    }

    public boolean isDiabetes() {
        return diabetes;
    }

    public void setDiabetes(boolean diabetes) {
        this.diabetes = diabetes;
    }

    public boolean isHipertension() {
        return hipertension;
    }

    public void setHipertension(boolean hipertension) {
        this.hipertension = hipertension;
    }

    public boolean isProblemasRenales() {
        return problemasRenales;
    }

    public void setProblemasRenales(boolean problemasRenales) {
        this.problemasRenales = problemasRenales;
    }

}
