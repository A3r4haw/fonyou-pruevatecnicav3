package mx.mc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author hramirez
 */
public class SurtimientoInsumo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idSurtimientoInsumo;
    private String idSurtimiento;
    private String idPrescripcionInsumo;
    private Date fechaProgramada;
    private Integer cantidadSolicitada;
    private Date fechaEnviada;
    private String idUsuarioEnviada;
    private Integer cantidadEnviada;
    private Integer cantidadVale;
    private String folioVale;
    private Date fechaRecepcion;
    private String idUsuarioRecepcion;
    private Integer cantidadRecepcion;
    private Date fechaCancela;
    private String idUsuarioCancela;
    private String idUsuarioAutCanRazn;
    private Integer idEstatusSurtimiento;
    private String firmaControlados;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;
    private Integer idEstatusMirth;
    private Integer numeroMedicacion;
    private Integer idTipoJustificante;
    private BigDecimal cantidadExcedente;
    private String notas;
    private Integer totalVale;
    private Integer totalEnviado;
    private String folioOrdenAVG;
    
    
    public SurtimientoInsumo() {
    }

    public SurtimientoInsumo(String idSurtimiento) {
        this.idSurtimiento = idSurtimiento;
    }

    public String getIdSurtimientoInsumo() {
        return idSurtimientoInsumo;
    }

    public void setIdSurtimientoInsumo(String idSurtimientoInsumo) {
        this.idSurtimientoInsumo = idSurtimientoInsumo;
    }

    public String getIdSurtimiento() {
        return idSurtimiento;
    }

    public void setIdSurtimiento(String idSurtimiento) {
        this.idSurtimiento = idSurtimiento;
    }

    public String getIdPrescripcionInsumo() {
        return idPrescripcionInsumo;
    }

    public void setIdPrescripcionInsumo(String idPrescripcionInsumo) {
        this.idPrescripcionInsumo = idPrescripcionInsumo;
    }

    public Date getFechaProgramada() {
        return fechaProgramada;
    }

    public void setFechaProgramada(Date fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
    }

    public Integer getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    public void setCantidadSolicitada(Integer cantidadSolicitada) {
        this.cantidadSolicitada = cantidadSolicitada;
    }

    public Date getFechaEnviada() {
        return fechaEnviada;
    }

    public void setFechaEnviada(Date fechaEnviada) {
        this.fechaEnviada = fechaEnviada;
    }

    public String getIdUsuarioEnviada() {
        return idUsuarioEnviada;
    }

    public void setIdUsuarioEnviada(String idUsuarioEnviada) {
        this.idUsuarioEnviada = idUsuarioEnviada;
    }

    public Integer getCantidadEnviada() {
        return cantidadEnviada;
    }

    public void setCantidadEnviada(Integer cantidadEnviada) {
        this.cantidadEnviada = cantidadEnviada;
    }

    public Date getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(Date fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public String getIdUsuarioRecepcion() {
        return idUsuarioRecepcion;
    }

    public void setIdUsuarioRecepcion(String idUsuarioRecepcion) {
        this.idUsuarioRecepcion = idUsuarioRecepcion;
    }

    public Integer getCantidadRecepcion() {
        return cantidadRecepcion;
    }

    public void setCantidadRecepcion(Integer cantidadRecepcion) {
        this.cantidadRecepcion = cantidadRecepcion;
    }

    public Date getFechaCancela() {
        return fechaCancela;
    }

    public void setFechaCancela(Date fechaCancela) {
        this.fechaCancela = fechaCancela;
    }

    public String getIdUsuarioCancela() {
        return idUsuarioCancela;
    }

    public void setIdUsuarioCancela(String idUsuarioCancela) {
        this.idUsuarioCancela = idUsuarioCancela;
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

    public Integer getCantidadVale() {
        return cantidadVale;
    }

    public void setCantidadVale(Integer cantidadVale) {
        this.cantidadVale = cantidadVale;
    }

    public String getFolioVale() {
        return folioVale;
    }

    public void setFolioVale(String folioVale) {
        this.folioVale = folioVale;
    }

    public Integer getIdTipoJustificante() {
        return idTipoJustificante;
    }

    public void setIdTipoJustificante(Integer idTipoJustificante) {
        this.idTipoJustificante = idTipoJustificante;
    }

    public Integer getIdEstatusMirth() {
        return idEstatusMirth;
    }

    public void setIdEstatusMirth(Integer idEstatusMirth) {
        this.idEstatusMirth = idEstatusMirth;
    }

    public String getFirmaControlados() {
        return firmaControlados;
    }

    public void setFirmaControlados(String firmaControlados) {
        this.firmaControlados = firmaControlados;
    }

    public Integer getNumeroMedicacion() {
        return numeroMedicacion;
    }

    public void setNumeroMedicacion(Integer numeroMedicacion) {
        this.numeroMedicacion = numeroMedicacion;
    }    

    public String getIdUsuarioAutCanRazn() {
        return idUsuarioAutCanRazn;
    }

    public void setIdUsuarioAutCanRazn(String idUsuarioAutCanRazn) {
        this.idUsuarioAutCanRazn = idUsuarioAutCanRazn;
    }
    
    /**
     * Get the value of notas
     *
     * @return the value of notas
     */
    public String getNotas() {
        return notas;
    }

    /**
     * Set the value of notas
     *
     * @param notas new value of notas
     */
    public void setNotas(String notas) {
        this.notas = notas;
    }

       public Integer getTotalVale() {
        return totalVale;
    }

    public void setTotalVale(Integer totalVale) {
        this.totalVale = totalVale;
    }

    public Integer getTotalEnviado() {
        return totalEnviado;
    }

    public void setTotalEnviado(Integer totalEnviado) {
        this.totalEnviado = totalEnviado;
    }

    public String getFolioOrdenAVG() {
        return folioOrdenAVG;
    }

    public void setFolioOrdenAVG(String folioOrdenAVG) {
        this.folioOrdenAVG = folioOrdenAVG;
    }

    public BigDecimal getCantidadExcedente() {
        return cantidadExcedente;
    }

    public void setCantidadExcedente(BigDecimal cantidadExcedente) {
        this.cantidadExcedente = cantidadExcedente;
    }
    
    


    @Override
    public String toString() {
        return "SurtimientoInsumo{" + "idSurtimientoInsumo=" + idSurtimientoInsumo + ", idSurtimiento=" + idSurtimiento + ", idPrescripcionInsumo=" + idPrescripcionInsumo + ", fechaProgramada=" + fechaProgramada + ", cantidadSolicitada=" + cantidadSolicitada + ", fechaEnviada=" + fechaEnviada + ", idUsuarioEnviada=" + idUsuarioEnviada + ", cantidadEnviada=" + cantidadEnviada + ", cantidadVale=" + cantidadVale + ", folioVale=" + folioVale + ", fechaRecepcion=" + fechaRecepcion + ", idUsuarioRecepcion=" + idUsuarioRecepcion + ", cantidadRecepcion=" + cantidadRecepcion + ", fechaCancela=" + fechaCancela + ", idUsuarioCancela=" + idUsuarioCancela + ", idUsuarioAutCanRazn=" + idUsuarioAutCanRazn + ", idEstatusSurtimiento=" + idEstatusSurtimiento + ", firmaControlados=" + firmaControlados + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + ", idEstatusMirth=" + idEstatusMirth + ", numeroMedicacion=" + numeroMedicacion + ", idTipoJustificante=" + idTipoJustificante + ", notas=" + notas + ", totalVale=" + totalVale + ", totalEnviado=" + totalEnviado + ", folioOrdenAVG=" + folioOrdenAVG  + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.idSurtimientoInsumo);
        hash = 41 * hash + Objects.hashCode(this.idSurtimiento);
        hash = 41 * hash + Objects.hashCode(this.idPrescripcionInsumo);
        hash = 41 * hash + Objects.hashCode(this.fechaProgramada);
        hash = 41 * hash + Objects.hashCode(this.cantidadSolicitada);
        hash = 41 * hash + Objects.hashCode(this.fechaEnviada);
        hash = 41 * hash + Objects.hashCode(this.idUsuarioEnviada);
        hash = 41 * hash + Objects.hashCode(this.cantidadEnviada);
        hash = 41 * hash + Objects.hashCode(this.cantidadVale);
        hash = 41 * hash + Objects.hashCode(this.folioVale);
        hash = 41 * hash + Objects.hashCode(this.fechaRecepcion);
        hash = 41 * hash + Objects.hashCode(this.idUsuarioRecepcion);
        hash = 41 * hash + Objects.hashCode(this.cantidadRecepcion);
        hash = 41 * hash + Objects.hashCode(this.fechaCancela);
        hash = 41 * hash + Objects.hashCode(this.idUsuarioCancela);
        hash = 41 * hash + Objects.hashCode(this.idUsuarioAutCanRazn);
        hash = 41 * hash + Objects.hashCode(this.idEstatusSurtimiento);
        hash = 41 * hash + Objects.hashCode(this.firmaControlados);
        hash = 41 * hash + Objects.hashCode(this.insertFecha);
        hash = 41 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 41 * hash + Objects.hashCode(this.updateFecha);
        hash = 41 * hash + Objects.hashCode(this.updateIdUsuario);
        hash = 41 * hash + Objects.hashCode(this.idEstatusMirth);
        hash = 41 * hash + Objects.hashCode(this.numeroMedicacion);
        hash = 41 * hash + Objects.hashCode(this.idTipoJustificante);
        hash = 41 * hash + Objects.hashCode(this.notas);
        hash = 41 * hash + Objects.hashCode(this.totalVale);
        hash = 41 * hash + Objects.hashCode(this.totalEnviado);
        hash = 41 * hash + Objects.hashCode(this.folioOrdenAVG);
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
        final SurtimientoInsumo other = (SurtimientoInsumo) obj;
        if (!Objects.equals(this.idSurtimientoInsumo, other.idSurtimientoInsumo)) {
            return false;
        }
        if (!Objects.equals(this.idSurtimiento, other.idSurtimiento)) {
            return false;
        }
        if (!Objects.equals(this.idPrescripcionInsumo, other.idPrescripcionInsumo)) {
            return false;
        }
        if (!Objects.equals(this.idUsuarioEnviada, other.idUsuarioEnviada)) {
            return false;
        }
        if (!Objects.equals(this.folioVale, other.folioVale)) {
            return false;
        }
        if (!Objects.equals(this.idUsuarioRecepcion, other.idUsuarioRecepcion)) {
            return false;
        }
        if (!Objects.equals(this.idUsuarioCancela, other.idUsuarioCancela)) {
            return false;
        }
        if (!Objects.equals(this.idUsuarioAutCanRazn, other.idUsuarioAutCanRazn)) {
            return false;
        }
        if (!Objects.equals(this.firmaControlados, other.firmaControlados)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.notas, other.notas)) {
            return false;
        }
        if (!Objects.equals(this.fechaProgramada, other.fechaProgramada)) {
            return false;
        }
        if (!Objects.equals(this.cantidadSolicitada, other.cantidadSolicitada)) {
            return false;
        }
        if (!Objects.equals(this.fechaEnviada, other.fechaEnviada)) {
            return false;
        }
        if (!Objects.equals(this.cantidadEnviada, other.cantidadEnviada)) {
            return false;
        }
        if (!Objects.equals(this.cantidadVale, other.cantidadVale)) {
            return false;
        }
        if (!Objects.equals(this.fechaRecepcion, other.fechaRecepcion)) {
            return false;
        }
        if (!Objects.equals(this.cantidadRecepcion, other.cantidadRecepcion)) {
            return false;
        }
        if (!Objects.equals(this.fechaCancela, other.fechaCancela)) {
            return false;
        }
        if (!Objects.equals(this.idEstatusSurtimiento, other.idEstatusSurtimiento)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        if (!Objects.equals(this.updateFecha, other.updateFecha)) {
            return false;
        }
        if (!Objects.equals(this.idEstatusMirth, other.idEstatusMirth)) {
            return false;
        }
        if (!Objects.equals(this.numeroMedicacion, other.numeroMedicacion)) {
            return false;
        }
        if (!Objects.equals(this.idTipoJustificante, other.idTipoJustificante)) {
            return false;
        }
        if (!Objects.equals(this.totalVale, other.totalVale)) {
            return false;
        }
        if (!Objects.equals(this.totalEnviado, other.totalEnviado)) {
            return false;
        }
         if (!Objects.equals(this.folioOrdenAVG, other.folioOrdenAVG)) {
            return false;
        }
        return true;
    }

    
}
