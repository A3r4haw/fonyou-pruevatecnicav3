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
//public class SolucionDistribuyeDetalle implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//    private String idSolucionDistribuyeDetalle;
//    private String idSolucionDistribuye;
//    private String idSolucion;
//    private Integer numeroMezcla;
//    private String descripcion;
//    private String presentacion;
//    private boolean entregada;
//    private Date insertFecha;
//    private String insertIdUsuario;
//    private Date updateFecha;
//    private String updateIdUsuario;
//
//    public SolucionDistribuyeDetalle() {
//    }
//
//    public SolucionDistribuyeDetalle(String idSolucionDistribuyeDetalle) {
//        this.idSolucionDistribuyeDetalle = idSolucionDistribuyeDetalle;
//    }
//
//    public String getIdSolucionDistribuyeDetalle() {
//        return idSolucionDistribuyeDetalle;
//    }
//
//    public void setIdSolucionDistribuyeDetalle(String idSolucionDistribuyeDetalle) {
//        this.idSolucionDistribuyeDetalle = idSolucionDistribuyeDetalle;
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
//    public String getIdSolucion() {
//        return idSolucion;
//    }
//
//    public void setIdSolucion(String idSolucion) {
//        this.idSolucion = idSolucion;
//    }
//
//    public Integer getNumeroMezcla() {
//        return numeroMezcla;
//    }
//
//    public void setNumeroMezcla(Integer numeroMezcla) {
//        this.numeroMezcla = numeroMezcla;
//    }
//
//    public String getDescripcion() {
//        return descripcion;
//    }
//
//    public void setDescripcion(String descripcion) {
//        this.descripcion = descripcion;
//    }
//
//    public String getPresentacion() {
//        return presentacion;
//    }
//
//    public void setPresentacion(String presentacion) {
//        this.presentacion = presentacion;
//    }
//
//    public boolean isEntregada() {
//        return entregada;
//    }
//
//    public void setEntregada(boolean entregada) {
//        this.entregada = entregada;
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
//    @Override
//    public String toString() {
//        return "SolucionDistribuyeDetalle{" + "idSolucionDistribuyeDetalle=" + idSolucionDistribuyeDetalle + ", idSolucionDistribuye=" + idSolucionDistribuye + ", idSolucion=" + idSolucion + ", numeroMezcla=" + numeroMezcla + ", descripcion=" + descripcion + ", presentacion=" + presentacion + ", entregada=" + entregada + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + '}';
//    }
//
//    @Override
//    public int hashCode() {
//        int hash = 7;
//        hash = 79 * hash + Objects.hashCode(this.idSolucionDistribuyeDetalle);
//        hash = 79 * hash + Objects.hashCode(this.idSolucionDistribuye);
//        hash = 79 * hash + Objects.hashCode(this.idSolucion);
//        hash = 79 * hash + Objects.hashCode(this.numeroMezcla);
//        hash = 79 * hash + Objects.hashCode(this.descripcion);
//        hash = 79 * hash + Objects.hashCode(this.presentacion);
//        hash = 79 * hash + (this.entregada ? 1 : 0);
//        hash = 79 * hash + Objects.hashCode(this.insertFecha);
//        hash = 79 * hash + Objects.hashCode(this.insertIdUsuario);
//        hash = 79 * hash + Objects.hashCode(this.updateFecha);
//        hash = 79 * hash + Objects.hashCode(this.updateIdUsuario);
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
//        final SolucionDistribuyeDetalle other = (SolucionDistribuyeDetalle) obj;
//        if (this.entregada != other.entregada) {
//            return false;
//        }
//        if (!Objects.equals(this.idSolucionDistribuyeDetalle, other.idSolucionDistribuyeDetalle)) {
//            return false;
//        }
//        if (!Objects.equals(this.idSolucionDistribuye, other.idSolucionDistribuye)) {
//            return false;
//        }
//        if (!Objects.equals(this.idSolucion, other.idSolucion)) {
//            return false;
//        }
//        if (!Objects.equals(this.descripcion, other.descripcion)) {
//            return false;
//        }
//        if (!Objects.equals(this.presentacion, other.presentacion)) {
//            return false;
//        }
//        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
//            return false;
//        }
//        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
//            return false;
//        }
//        if (!Objects.equals(this.numeroMezcla, other.numeroMezcla)) {
//            return false;
//        }
//        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
//            return false;
//        }
//        return Objects.equals(this.updateFecha, other.updateFecha);
//    }
//
//}
