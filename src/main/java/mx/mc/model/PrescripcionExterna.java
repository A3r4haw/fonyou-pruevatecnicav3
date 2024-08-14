package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author hramirez
 */
public class PrescripcionExterna implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idPrescripcion;
    private String idEstructura;
    private String idPacienteServicio;
    private String folio;
    private Date fechaPrescripcion;
    private Date fechaFirma;
    private String tipoPrescripcion;
    private String tipoConsulta;
    private String idMedico;
    private Integer recurrente;
    private String comentarios;
    private Integer idEstatusPrescripcion;
    private Integer idEstatusGabinete;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;

    private List<PrescripcionInsumo> insumosList;

    public PrescripcionExterna() {
    }

    public PrescripcionExterna(String idPrescripcion) {
        this.idPrescripcion = idPrescripcion;
    }

    public PrescripcionExterna(String idPrescripcion, Integer idEstatusPrescripcion, Date updateFecha, String updateIdUsuario) {
        this.idPrescripcion = idPrescripcion;
        this.idEstatusPrescripcion = idEstatusPrescripcion;
        this.updateFecha = updateFecha;
        this.updateIdUsuario = updateIdUsuario;
    }
 
    public String getIdPrescripcion() {
        return idPrescripcion;
    }

    public void setIdPrescripcion(String idPrescripcion) {
        this.idPrescripcion = idPrescripcion;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public String getIdPacienteServicio() {
        return idPacienteServicio;
    }

    public void setIdPacienteServicio(String idPacienteServicio) {
        this.idPacienteServicio = idPacienteServicio;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public Date getFechaPrescripcion() {
        return fechaPrescripcion;
    }

    public void setFechaPrescripcion(Date fechaPrescripcion) {
        this.fechaPrescripcion = fechaPrescripcion;
    }

    public Date getFechaFirma() {
        return fechaFirma;
    }

    public void setFechaFirma(Date fechaFirma) {
        this.fechaFirma = fechaFirma;
    }

    public String getTipoPrescripcion() {
        return tipoPrescripcion;
    }

    public void setTipoPrescripcion(String tipoPrescripcion) {
        this.tipoPrescripcion = tipoPrescripcion;
    }

    public String getTipoConsulta() {
        return tipoConsulta;
    }

    public void setTipoConsulta(String tipoConsulta) {
        this.tipoConsulta = tipoConsulta;
    }

    public String getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(String idMedico) {
        this.idMedico = idMedico;
    }

    public Integer getRecurrente() {
        return recurrente;
    }

    public void setRecurrente(Integer recurrente) {
        this.recurrente = recurrente;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Integer getIdEstatusPrescripcion() {
        return idEstatusPrescripcion;
    }

    public void setIdEstatusPrescripcion(Integer idEstatusPrescripcion) {
        this.idEstatusPrescripcion = idEstatusPrescripcion;
    }

    public Integer getIdEstatusGabinete() {
        return idEstatusGabinete;
    }

    public void setIdEstatusGabinete(Integer idEstatusGabinete) {
        this.idEstatusGabinete = idEstatusGabinete;
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

    public List<PrescripcionInsumo> getInsumosList() {
        return insumosList;
    }

    public void setInsumosList(List<PrescripcionInsumo> insumosList) {
        this.insumosList = insumosList;
    }

    @Override
    public String toString() {
        return "Prescripcion{" + "idPrescripcion=" + idPrescripcion + ", idEstructura=" + idEstructura + ", idPacienteServicio=" + idPacienteServicio + ", folio=" + folio + ", fechaPrescripcion=" + fechaPrescripcion + ", fechaFirma=" + fechaFirma + ", tipoPrescripcion=" + tipoPrescripcion + ", tipoConsulta=" + tipoConsulta + ", idMedico=" + idMedico + ", recurrente=" + recurrente + ", comentarios=" + comentarios + ", idEstatusPrescripcion=" + idEstatusPrescripcion + ", idEstatusGabinete=" + idEstatusGabinete + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + ", insumosList=" + insumosList + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.idPrescripcion);
        hash = 29 * hash + Objects.hashCode(this.idEstructura);
        hash = 29 * hash + Objects.hashCode(this.idPacienteServicio);
        hash = 29 * hash + Objects.hashCode(this.folio);
        hash = 29 * hash + Objects.hashCode(this.fechaPrescripcion);
        hash = 29 * hash + Objects.hashCode(this.fechaFirma);
        hash = 29 * hash + Objects.hashCode(this.tipoPrescripcion);
        hash = 29 * hash + Objects.hashCode(this.tipoConsulta);
        hash = 29 * hash + Objects.hashCode(this.idMedico);
        hash = 29 * hash + Objects.hashCode(this.recurrente);
        hash = 29 * hash + Objects.hashCode(this.comentarios);
        hash = 29 * hash + Objects.hashCode(this.idEstatusPrescripcion);
        hash = 29 * hash + Objects.hashCode(this.idEstatusGabinete);
        hash = 29 * hash + Objects.hashCode(this.insertFecha);
        hash = 29 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 29 * hash + Objects.hashCode(this.updateFecha);
        hash = 29 * hash + Objects.hashCode(this.updateIdUsuario);
        hash = 29 * hash + Objects.hashCode(this.insumosList);
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
        final PrescripcionExterna other = (PrescripcionExterna) obj;
        if (!Objects.equals(this.idPrescripcion, other.idPrescripcion)) {
            return false;
        }
        if (!Objects.equals(this.idEstructura, other.idEstructura)) {
            return false;
        }
        if (!Objects.equals(this.idPacienteServicio, other.idPacienteServicio)) {
            return false;
        }
        if (!Objects.equals(this.folio, other.folio)) {
            return false;
        }
        if (!Objects.equals(this.tipoPrescripcion, other.tipoPrescripcion)) {
            return false;
        }
        if (!Objects.equals(this.tipoConsulta, other.tipoConsulta)) {
            return false;
        }
        if (!Objects.equals(this.idMedico, other.idMedico)) {
            return false;
        }
        if (!Objects.equals(this.comentarios, other.comentarios)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.fechaPrescripcion, other.fechaPrescripcion)) {
            return false;
        }
        if (!Objects.equals(this.fechaFirma, other.fechaFirma)) {
            return false;
        }
        if (!Objects.equals(this.recurrente, other.recurrente)) {
            return false;
        }
        if (!Objects.equals(this.idEstatusPrescripcion, other.idEstatusPrescripcion)) {
            return false;
        }
        if (!Objects.equals(this.idEstatusGabinete, other.idEstatusGabinete)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        if (!Objects.equals(this.updateFecha, other.updateFecha)) {
            return false;
        }
        return Objects.equals(this.insumosList, other.insumosList);
    }
}
