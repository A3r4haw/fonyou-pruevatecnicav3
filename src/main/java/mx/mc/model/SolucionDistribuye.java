//package mx.mc.model;
//
//import java.io.Serializable;
//import java.util.Date;
//import java.util.Objects;
//
///**
// *
// * @author Cervanets
// */
//public class SolucionDistribuye implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//    private String idSolucionDistribuye;
//    private String folio;
//    private Date fecha;
//    private String idEstructura;
//    private String descripcion;
//    private String comentarios;
//    private Integer idEstatusDistribuye;
//    private Date insertFecha;
//    private String insertIdUsuario;
//    private Date updateFecha;
//    private String updateIdUsuario;
//    private String numeroVale;
//    private String conceptoSalida;
//    private Integer idTurno;
//    private String idUsuarioDistribuye;
//    private String idEstructuraDispensa;
//
//    public SolucionDistribuye() {
//    }
//
//    public SolucionDistribuye(String idSolucionDistribuye) {
//        this.idSolucionDistribuye = idSolucionDistribuye;
//    }
//
//    public String getIdSolucionDistribuye() {
//        return idSolucionDistribuye;
//    }
//
//    public void setIdSolucionDistribuye(String idSolucionDistribuye) {
//        this.idSolucionDistribuye = idSolucionDistribuye;
//    }
//
//    public String getFolio() {
//        return folio;
//    }
//
//    public void setFolio(String folio) {
//        this.folio = folio;
//    }
//
//    public Date getFecha() {
//        return fecha;
//    }
//
//    public void setFecha(Date fecha) {
//        this.fecha = fecha;
//    }
//
//    public String getIdEstructura() {
//        return idEstructura;
//    }
//
//    public void setIdEstructura(String idEstructura) {
//        this.idEstructura = idEstructura;
//    }
//
////    public String getDescripcion() {
////        return descripcion;
////    }
//
//    public void setDescripcion(String descripcion) {
//        this.descripcion = descripcion;
//    }
//
//    public String getComentarios() {
//        return comentarios;
//    }
//
//    public void setComentarios(String comentarios) {
//        this.comentarios = comentarios;
//    }
//
//    public Integer getIdEstatusDistribuye() {
//        return idEstatusDistribuye;
//    }
//
//    public void setIdEstatusDistribuye(Integer idEstatusDistribuye) {
//        this.idEstatusDistribuye = idEstatusDistribuye;
//    }
//
//    public Date getInsertFecha() {
//        return insertFecha;
//    }
//
//    public void setInsertFecha(Date insertFecha) {
//        this.insertFecha = insertFecha;
//    }
//
//    public String getInsertIdUsuario() {
//        return insertIdUsuario;
//    }
//
//    public void setInsertIdUsuario(String insertIdUsuario) {
//        this.insertIdUsuario = insertIdUsuario;
//    }
//
//    public Date getUpdateFecha() {
//        return updateFecha;
//    }
//
//    public void setUpdateFecha(Date updateFecha) {
//        this.updateFecha = updateFecha;
//    }
//
//    public String getUpdateIdUsuario() {
//        return updateIdUsuario;
//    }
//
//    public void setUpdateIdUsuario(String updateIdUsuario) {
//        this.updateIdUsuario = updateIdUsuario;
//    }
//
//    public String getNumeroVale() {
//        return numeroVale;
//    }
//
//    public void setNumeroVale(String numeroVale) {
//        this.numeroVale = numeroVale;
//    }
//
//    public String getConceptoSalida() {
//        return conceptoSalida;
//    }
//
//    public void setConceptoSalida(String conceptoSalida) {
//        this.conceptoSalida = conceptoSalida;
//    }
//
//    public Integer getIdTurno() {
//        return idTurno;
//    }
//
//    public void setIdTurno(Integer idTurno) {
//        this.idTurno = idTurno;
//    }
//
//    public String getIdUsuarioDistribuye() {
//        return idUsuarioDistribuye;
//    }
//
//    public void setIdUsuarioDistribuye(String idUsuarioDistribuye) {
//        this.idUsuarioDistribuye = idUsuarioDistribuye;
//    }
//
//    public String getIdEstructuraDispensa() {
//        return idEstructuraDispensa;
//    }
//
//    public void setIdEstructuraDispensa(String idEstructuraDispensa) {
//        this.idEstructuraDispensa = idEstructuraDispensa;
//    }
//
//    @Override
//    public String toString() {
//        return "SolucionDistribuye{" + "idSolucionDistribuye=" + idSolucionDistribuye + ", folio=" + folio + ", fecha=" + fecha + ", idEstructura=" + idEstructura + ", descripcion=" + descripcion + ", comentarios=" + comentarios + ", idEstatusDistribuye=" + idEstatusDistribuye + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + ", numeroVale=" + numeroVale + ", conceptoSalida=" + conceptoSalida + ", idTurno=" + idTurno + ", idUsuarioDistribuye=" + idUsuarioDistribuye + ", idEstructuraDispensa=" + idEstructuraDispensa + '}';
//    }
//
//    @Override
//    public int hashCode() {
//        int hash = 7;
//        hash = 29 * hash + Objects.hashCode(this.idSolucionDistribuye);
//        hash = 29 * hash + Objects.hashCode(this.folio);
//        hash = 29 * hash + Objects.hashCode(this.fecha);
//        hash = 29 * hash + Objects.hashCode(this.idEstructura);
//        hash = 29 * hash + Objects.hashCode(this.descripcion);
//        hash = 29 * hash + Objects.hashCode(this.comentarios);
//        hash = 29 * hash + Objects.hashCode(this.idEstatusDistribuye);
//        hash = 29 * hash + Objects.hashCode(this.insertFecha);
//        hash = 29 * hash + Objects.hashCode(this.insertIdUsuario);
//        hash = 29 * hash + Objects.hashCode(this.updateFecha);
//        hash = 29 * hash + Objects.hashCode(this.updateIdUsuario);
//        hash = 29 * hash + Objects.hashCode(this.numeroVale);
//        hash = 29 * hash + Objects.hashCode(this.conceptoSalida);
//        hash = 29 * hash + Objects.hashCode(this.idTurno);
//        hash = 29 * hash + Objects.hashCode(this.idUsuarioDistribuye);
//        hash = 29 * hash + Objects.hashCode(this.idEstructuraDispensa);
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) {
//            return true;
//        }
//        if (obj == null) {
//            return false;
//        }
//        if (getClass() != obj.getClass()) {
//            return false;
//        }
//        final SolucionDistribuye other = (SolucionDistribuye) obj;
//        if (!Objects.equals(this.idSolucionDistribuye, other.idSolucionDistribuye)) {
//            return false;
//        }
//        if (!Objects.equals(this.folio, other.folio)) {
//            return false;
//        }
//        if (!Objects.equals(this.idEstructura, other.idEstructura)) {
//            return false;
//        }
//        if (!Objects.equals(this.descripcion, other.descripcion)) {
//            return false;
//        }
//        if (!Objects.equals(this.comentarios, other.comentarios)) {
//            return false;
//        }
//        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
//            return false;
//        }
//        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
//            return false;
//        }
//        if (!Objects.equals(this.numeroVale, other.numeroVale)) {
//            return false;
//        }
//        if (!Objects.equals(this.conceptoSalida, other.conceptoSalida)) {
//            return false;
//        }
//        if (!Objects.equals(this.idUsuarioDistribuye, other.idUsuarioDistribuye)) {
//            return false;
//        }
//        if (!Objects.equals(this.idEstructuraDispensa, other.idEstructuraDispensa)) {
//            return false;
//        }
//        if (!Objects.equals(this.fecha, other.fecha)) {
//            return false;
//        }
//        if (!Objects.equals(this.idEstatusDistribuye, other.idEstatusDistribuye)) {
//            return false;
//        }
//        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
//            return false;
//        }
//        if (!Objects.equals(this.updateFecha, other.updateFecha)) {
//            return false;
//        }
//        return Objects.equals(this.idTurno, other.idTurno);
//    }
//
//}
