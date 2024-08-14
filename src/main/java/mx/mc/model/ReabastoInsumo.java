/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author bbautista
 */
public class ReabastoInsumo implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String idReabastoInsumo;
    private String idReabasto;
    private String idInsumo;
    private String observaciones;
    private Integer cantidadSolicitada;
    private Integer cantidadComprometida;
    private Integer cantidadSurtida;
    private Integer cantidadRecibida;
    private Integer cantidadIngresada;
    private Integer idEstatusReabasto;
    private String firmaControlados;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;
    private Integer transferencia;
    private String idFolioAlternativo;
    private String idUsuarioAutoriza;
                   
    //Estos campos no estan en la tabla ReabastoInsumo solo son para consulta        
    private String nombreComercial;
    private String presentacion;
    private Integer piezasCaja;
    private String clave;
    private Integer reorden;
    private Integer maximo;
    private Integer minimo;
    private Integer dotacion;
    private Integer surtido;
    private String idAlmacenPuntosControl;
    private String nombreLargo;
    private Integer cantidadActual;
    private Integer cantidadPorClave;
    private String almacenServicio;
    //El siguiente campo es para identificar solo el grupo que pertenece el medicamento
    private String grupo;
    private int nivelSurt;
    private String folioAlternativo;
    
    private transient List<InsumoServicio> detalleInsumo;

    public ReabastoInsumo() {
        //No code needed in constructor
    }

    public ReabastoInsumo(String idReabastoInsumo) {
        this.idReabastoInsumo = idReabastoInsumo;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.idReabastoInsumo);
        hash = 97 * hash + Objects.hashCode(this.idReabasto);
        hash = 97 * hash + Objects.hashCode(this.idInsumo);
        hash = 97 * hash + Objects.hashCode(this.observaciones);
        hash = 97 * hash + Objects.hashCode(this.cantidadSolicitada);
        hash = 97 * hash + Objects.hashCode(this.cantidadComprometida);
        hash = 97 * hash + Objects.hashCode(this.cantidadSurtida);
        hash = 97 * hash + Objects.hashCode(this.cantidadRecibida);
        hash = 97 * hash + Objects.hashCode(this.cantidadIngresada);
        hash = 97 * hash + Objects.hashCode(this.idEstatusReabasto);
        hash = 97 * hash + Objects.hashCode(this.insertFecha);
        hash = 97 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 97 * hash + Objects.hashCode(this.updateFecha);
        hash = 97 * hash + Objects.hashCode(this.updateIdUsuario);
        hash = 97 * hash + Objects.hashCode(this.transferencia);
        hash = 97 * hash + Objects.hashCode(this.nombreComercial);
        hash = 97 * hash + Objects.hashCode(this.presentacion);
        hash = 97 * hash + Objects.hashCode(this.piezasCaja);
        hash = 97 * hash + Objects.hashCode(this.clave);
        hash = 97 * hash + Objects.hashCode(this.reorden);
        hash = 97 * hash + Objects.hashCode(this.maximo);
        hash = 97 * hash + Objects.hashCode(this.minimo);
        hash = 97 * hash + Objects.hashCode(this.dotacion);
        hash = 97 * hash + Objects.hashCode(this.surtido);
        hash = 97 * hash + Objects.hashCode(this.idAlmacenPuntosControl);
        hash = 97 * hash + Objects.hashCode(this.nombreLargo);
        hash = 97 * hash + Objects.hashCode(this.idFolioAlternativo);
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
        final ReabastoInsumo other = (ReabastoInsumo) obj;
        if (!Objects.equals(this.idReabastoInsumo, other.idReabastoInsumo)) {
            return false;
        }
        if (!Objects.equals(this.idReabasto, other.idReabasto)) {
            return false;
        }
        if (!Objects.equals(this.idInsumo, other.idInsumo)) {
            return false;
        }
        if (!Objects.equals(this.observaciones, other.observaciones)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.nombreComercial, other.nombreComercial)) {
            return false;
        }
        if (!Objects.equals(this.presentacion, other.presentacion)) {
            return false;
        }
        if (!Objects.equals(this.clave, other.clave)) {
            return false;
        }
        if (!Objects.equals(this.idAlmacenPuntosControl, other.idAlmacenPuntosControl)) {
            return false;
        }
        if (!Objects.equals(this.nombreLargo, other.nombreLargo)) {
            return false;
        }
        if (!Objects.equals(this.cantidadSolicitada, other.cantidadSolicitada)) {
            return false;
        }
        if (!Objects.equals(this.cantidadComprometida, other.cantidadComprometida)) {
            return false;
        }
        if (!Objects.equals(this.cantidadSurtida, other.cantidadSurtida)) {
            return false;
        }
        if (!Objects.equals(this.cantidadRecibida, other.cantidadRecibida)) {
            return false;
        }
        if (!Objects.equals(this.cantidadIngresada, other.cantidadIngresada)) {
            return false;
        }
        if (!Objects.equals(this.idEstatusReabasto, other.idEstatusReabasto)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        if (!Objects.equals(this.updateFecha, other.updateFecha)) {
            return false;
        }
        if (!Objects.equals(this.transferencia, other.transferencia)) {
            return false;
        }
        if (!Objects.equals(this.piezasCaja, other.piezasCaja)) {
            return false;
        }
        if (!Objects.equals(this.reorden, other.reorden)) {
            return false;
        }
        if (!Objects.equals(this.maximo, other.maximo)) {
            return false;
        }
        if (!Objects.equals(this.minimo, other.minimo)) {
            return false;
        }
        if (!Objects.equals(this.idFolioAlternativo, other.idFolioAlternativo)) {
            return false;
        }
        if (!Objects.equals(this.dotacion, other.dotacion)) {
            return false;
        }
        return Objects.equals(this.surtido, other.surtido);
    }

    @Override
    public String toString() {
        return "ReabastoInsumo{" + "idReabastoInsumo=" + idReabastoInsumo + ", idReabasto=" + idReabasto + ", idInsumo=" + idInsumo + ", observaciones=" + observaciones + ", cantidadSolicitada=" + cantidadSolicitada + ", cantidadComprometida=" + cantidadComprometida + ", cantidadSurtida=" + cantidadSurtida + ", cantidadRecibida=" + cantidadRecibida + ", cantidadIngresada=" + cantidadIngresada + ", idEstatusReabasto=" + idEstatusReabasto + ", firmaControlados=" + firmaControlados + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + ", transferencia=" + transferencia + ", idFolioAlternativo=" + idFolioAlternativo + ", nombreComercial=" + nombreComercial + ", presentacion=" + presentacion + ", piezasCaja=" + piezasCaja + ", clave=" + clave + ", reorden=" + reorden + ", maximo=" + maximo + ", minimo=" + minimo + ", dotacion=" + dotacion + ", surtido=" + surtido + ", idAlmacenPuntosControl=" + idAlmacenPuntosControl + ", nombreLargo=" + nombreLargo + ", cantidadActual=" + cantidadActual + ", cantidadPorClave=" + cantidadPorClave + ", grupo=" + grupo + '}';
    }
      
    public String getIdReabastoInsumo() {
        return idReabastoInsumo;
    }

    public void setIdReabastoInsumo(String idReabastoInsumo) {
        this.idReabastoInsumo = idReabastoInsumo;
    }

    public String getIdReabasto() {
        return idReabasto;
    }

    public void setIdReabasto(String idReabasto) {
        this.idReabasto = idReabasto;
    }

    public String getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(String idInsumo) {
        this.idInsumo = idInsumo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Integer getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    public void setCantidadSolicitada(Integer cantidadSolicitada) {
        this.cantidadSolicitada = cantidadSolicitada;
    }

    public Integer getCantidadComprometida() {
        return cantidadComprometida;
    }

    public void setCantidadComprometida(Integer cantidadComprometida) {
        this.cantidadComprometida = cantidadComprometida;
    }

    public Integer getCantidadSurtida() {
        return cantidadSurtida;
    }

    public void setCantidadSurtida(Integer cantidadSurtida) {
        this.cantidadSurtida = cantidadSurtida;
    }

    public Integer getCantidadRecibida() {
        return cantidadRecibida;
    }

    public void setCantidadRecibida(Integer cantidadRecibida) {
        this.cantidadRecibida = cantidadRecibida;
    }

    public Integer getCantidadIngresada() {
        return cantidadIngresada;
    }

    public void setCantidadIngresada(Integer cantidadIngresada) {
        this.cantidadIngresada = cantidadIngresada;
    }

    public Integer getIdEstatusReabasto() {
        return idEstatusReabasto;
    }

    public void setIdEstatusReabasto(Integer idEstatusReabasto) {
        this.idEstatusReabasto = idEstatusReabasto;
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

    public Integer getTransferencia() {
        return transferencia;
    }

    public void setTransferencia(Integer transferencia) {
        this.transferencia = transferencia;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public Integer getPiezasCaja() {
        return piezasCaja;
    }

    public void setPiezasCaja(Integer piezasCaja) {
        this.piezasCaja = piezasCaja;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Integer getReorden() {
        return reorden;
    }

    public void setReorden(Integer reorden) {
        this.reorden = reorden;
    }

    public Integer getMaximo() {
        return maximo;
    }

    public void setMaximo(Integer maximo) {
        this.maximo = maximo;
    }

    public Integer getMinimo() {
        return minimo;
    }

    public void setMinimo(Integer minimo) {
        this.minimo = minimo;
    }

    public String getIdAlmacenPuntosControl() {
        return idAlmacenPuntosControl;
    }

    public void setIdAlmacenPuntosControl(String idAlmacenPuntosControl) {
        this.idAlmacenPuntosControl = idAlmacenPuntosControl;
    }

    public String getNombreLargo() {
        return nombreLargo;
    }

    public void setNombreLargo(String nombreLargo) {
        this.nombreLargo = nombreLargo;
    }

    public Integer getCantidadActual() {
        return cantidadActual;
    }

    public void setCantidadActual(Integer cantidadActual) {
        this.cantidadActual = cantidadActual;
    }

    public Integer getCantidadPorClave() {
        return cantidadPorClave;
    }

    public void setCantidadPorClave(Integer cantidadPorClave) {
        this.cantidadPorClave = cantidadPorClave;
    }

    public String getFirmaControlados() {
        return firmaControlados;
    }

    public void setFirmaControlados(String firmaControlados) {
        this.firmaControlados = firmaControlados;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getIdFolioAlternativo() {
        return idFolioAlternativo;
    }

    public void setIdFolioAlternativo(String idFolioAlternativo) {
        this.idFolioAlternativo = idFolioAlternativo;
    }
    public Integer getDotacion() {
        return dotacion;
    }

    public void setDotacion(Integer dotacion) {
        this.dotacion = dotacion;
    }

    public Integer getSurtido() {
        return surtido;
    }

    public void setSurtido(Integer surtido) {
        this.surtido = surtido;
    }

    public String getIdUsuarioAutoriza() {
        return idUsuarioAutoriza;
    }

    public void setIdUsuarioAutoriza(String idUsuarioAutoriza) {
        this.idUsuarioAutoriza = idUsuarioAutoriza;
    }

    public int getNivelSurt() {
        return nivelSurt;
    }

    public void setNivelSurt(int nivelSurt) {
        this.nivelSurt = nivelSurt;
    }

    public String getFolioAlternativo() {
        return folioAlternativo;
    }

    public void setFolioAlternativo(String folioAlternativo) {
        this.folioAlternativo = folioAlternativo;
    }

    public List<InsumoServicio> getDetalleInsumo() {
        return detalleInsumo;
    }

    public void setDetalleInsumo(List<InsumoServicio> detalleInsumo) {
        this.detalleInsumo = detalleInsumo;
    }

    public String getAlmacenServicio() {
        return almacenServicio;
    }

    public void setAlmacenServicio(String almacenServicio) {
        this.almacenServicio = almacenServicio;
    }
    
    
}
