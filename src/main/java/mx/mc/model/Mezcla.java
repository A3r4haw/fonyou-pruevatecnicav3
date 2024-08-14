package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author cervanets
 */
public class Mezcla implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idPrescripcion;
    private String idSurtimiento;
    private String idSolucion;
    private Date fechaFirma;
    private String folioPrescripcion;
    private String estatusPrescripcion;
    private String nombreServicio;
    private String nombrePaciente;

    private Date fechaProgramada;
    private String folioSurtimiento;
    private String descripcionMezcla;
    private String nombreAlmacen;
    private String estatusSurtimiento;

    private String loteMezcla;
    private Date caducidadMezcla;

    private Date fechaPrescripcion;
    private String nombreMedico;

    private Date fechaValPrescr;
    private String comentValPrescr;
    private String usuarioValidaPresc;

    private Date fechaValida;
    private String comentValOrdenPrep;
    private String usuarioValidaOP;

    private Date fechaDispensacion;
    private String usuarioDispensa;

    private Date fechaPrepara;
    private String comentariosPreparacion;
    private String usuarioPrepara;

    private Date fechaInspeccion;
    private String comentarioInspeccion;
    private String usuarioInspecciona;

    private Date fechaAcondicionamiento;
    private String comentarioAcondicionada;
    private String usuarioAcondiciona;

    private Date fechaEntrega;
    private String comentariosEntrega;
    private String usuarioEntrega;

    private Date fechaRecibe;
    private String usuarioRecibe;

    private String estatusSolucion;
    private String posologia;
    private Date ultimoMovimiento;
    private Integer idEstatusSolucion;
    
    private String usuarioRechaza;
    private String comentariosRechazo;
    private Date updateFecha;
    
    private String usuarioAutoriza;
    private String comentarioAutoriza;
    private Date fechaAutoriza;

    public Mezcla() {
    }

    public String getIdPrescripcion() {
        return idPrescripcion;
    }

    public void setIdPrescripcion(String idPrescripcion) {
        this.idPrescripcion = idPrescripcion;
    }

    public String getIdSurtimiento() {
        return idSurtimiento;
    }

    public void setIdSurtimiento(String idSurtimiento) {
        this.idSurtimiento = idSurtimiento;
    }

    public String getIdSolucion() {
        return idSolucion;
    }

    public void setIdSolucion(String idSolucion) {
        this.idSolucion = idSolucion;
    }

    public Date getFechaFirma() {
        return fechaFirma;
    }

    public void setFechaFirma(Date fechaFirma) {
        this.fechaFirma = fechaFirma;
    }

    public String getFolioPrescripcion() {
        return folioPrescripcion;
    }

    public void setFolioPrescripcion(String folioPrescripcion) {
        this.folioPrescripcion = folioPrescripcion;
    }

    public String getEstatusPrescripcion() {
        return estatusPrescripcion;
    }

    public void setEstatusPrescripcion(String estatusPrescripcion) {
        this.estatusPrescripcion = estatusPrescripcion;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public Date getFechaProgramada() {
        return fechaProgramada;
    }

    public void setFechaProgramada(Date fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
    }

    public String getFolioSurtimiento() {
        return folioSurtimiento;
    }

    public void setFolioSurtimiento(String folioSurtimiento) {
        this.folioSurtimiento = folioSurtimiento;
    }

    public String getDescripcionMezcla() {
        return descripcionMezcla;
    }

    public void setDescripcionMezcla(String descripcionMezcla) {
        this.descripcionMezcla = descripcionMezcla;
    }

    public String getNombreAlmacen() {
        return nombreAlmacen;
    }

    public void setNombreAlmacen(String nombreAlmacen) {
        this.nombreAlmacen = nombreAlmacen;
    }

    public String getEstatusSurtimiento() {
        return estatusSurtimiento;
    }

    public void setEstatusSurtimiento(String estatusSurtimiento) {
        this.estatusSurtimiento = estatusSurtimiento;
    }

    public String getLoteMezcla() {
        return loteMezcla;
    }

    public void setLoteMezcla(String loteMezcla) {
        this.loteMezcla = loteMezcla;
    }

    public Date getCaducidadMezcla() {
        return caducidadMezcla;
    }

    public void setCaducidadMezcla(Date caducidadMezcla) {
        this.caducidadMezcla = caducidadMezcla;
    }

    public Date getFechaPrescripcion() {
        return fechaPrescripcion;
    }

    public void setFechaPrescripcion(Date fechaPrescripcion) {
        this.fechaPrescripcion = fechaPrescripcion;
    }

    public String getNombreMedico() {
        return nombreMedico;
    }

    public void setNombreMedico(String nombreMedico) {
        this.nombreMedico = nombreMedico;
    }

    public Date getFechaValPrescr() {
        return fechaValPrescr;
    }

    public void setFechaValPrescr(Date fechaValPrescr) {
        this.fechaValPrescr = fechaValPrescr;
    }

    public String getComentValPrescr() {
        return comentValPrescr;
    }

    public void setComentValPrescr(String comentValPrescr) {
        this.comentValPrescr = comentValPrescr;
    }

    public String getUsuarioValidaPresc() {
        return usuarioValidaPresc;
    }

    public void setUsuarioValidaPresc(String usuarioValidaPresc) {
        this.usuarioValidaPresc = usuarioValidaPresc;
    }

    public Date getFechaValida() {
        return fechaValida;
    }

    public void setFechaValida(Date fechaValida) {
        this.fechaValida = fechaValida;
    }

    public String getComentValOrdenPrep() {
        return comentValOrdenPrep;
    }

    public void setComentValOrdenPrep(String comentValOrdenPrep) {
        this.comentValOrdenPrep = comentValOrdenPrep;
    }

    public String getUsuarioValidaOP() {
        return usuarioValidaOP;
    }

    public void setUsuarioValidaOP(String usuarioValidaOP) {
        this.usuarioValidaOP = usuarioValidaOP;
    }

    public Date getFechaDispensacion() {
        return fechaDispensacion;
    }

    public void setFechaDispensacion(Date fechaDispensacion) {
        this.fechaDispensacion = fechaDispensacion;
    }

    public String getUsuarioDispensa() {
        return usuarioDispensa;
    }

    public void setUsuarioDispensa(String usuarioDispensa) {
        this.usuarioDispensa = usuarioDispensa;
    }

    public Date getFechaPrepara() {
        return fechaPrepara;
    }

    public void setFechaPrepara(Date fechaPrepara) {
        this.fechaPrepara = fechaPrepara;
    }

    public String getComentariosPreparacion() {
        return comentariosPreparacion;
    }

    public void setComentariosPreparacion(String comentariosPreparacion) {
        this.comentariosPreparacion = comentariosPreparacion;
    }

    public String getUsuarioPrepara() {
        return usuarioPrepara;
    }

    public void setUsuarioPrepara(String usuarioPrepara) {
        this.usuarioPrepara = usuarioPrepara;
    }

    public Date getFechaInspeccion() {
        return fechaInspeccion;
    }

    public void setFechaInspeccion(Date fechaInspeccion) {
        this.fechaInspeccion = fechaInspeccion;
    }

    public String getComentarioInspeccion() {
        return comentarioInspeccion;
    }

    public void setComentarioInspeccion(String comentarioInspeccion) {
        this.comentarioInspeccion = comentarioInspeccion;
    }

    public String getUsuarioInspecciona() {
        return usuarioInspecciona;
    }

    public void setUsuarioInspecciona(String usuarioInspecciona) {
        this.usuarioInspecciona = usuarioInspecciona;
    }

    public Date getFechaAcondicionamiento() {
        return fechaAcondicionamiento;
    }

    public void setFechaAcondicionamiento(Date fechaAcondicionamiento) {
        this.fechaAcondicionamiento = fechaAcondicionamiento;
    }

    public String getComentarioAcondicionada() {
        return comentarioAcondicionada;
    }

    public void setComentarioAcondicionada(String comentarioAcondicionada) {
        this.comentarioAcondicionada = comentarioAcondicionada;
    }

    public String getUsuarioAcondiciona() {
        return usuarioAcondiciona;
    }

    public void setUsuarioAcondiciona(String usuarioAcondiciona) {
        this.usuarioAcondiciona = usuarioAcondiciona;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getComentariosEntrega() {
        return comentariosEntrega;
    }

    public void setComentariosEntrega(String comentariosEntrega) {
        this.comentariosEntrega = comentariosEntrega;
    }

    public String getUsuarioEntrega() {
        return usuarioEntrega;
    }

    public void setUsuarioEntrega(String usuarioEntrega) {
        this.usuarioEntrega = usuarioEntrega;
    }

    public Date getFechaRecibe() {
        return fechaRecibe;
    }

    public void setFechaRecibe(Date fechaRecibe) {
        this.fechaRecibe = fechaRecibe;
    }

    public String getUsuarioRecibe() {
        return usuarioRecibe;
    }

    public void setUsuarioRecibe(String usuarioRecibe) {
        this.usuarioRecibe = usuarioRecibe;
    }

    public String getEstatusSolucion() {
        return estatusSolucion;
    }

    public void setEstatusSolucion(String estatusSolucion) {
        this.estatusSolucion = estatusSolucion;
    }

    public String getPosologia() {
        return posologia;
    }

    public void setPosologia(String posologia) {
        this.posologia = posologia;
    }

    public Date getUltimoMovimiento() {
        return ultimoMovimiento;
    }

    public void setUltimoMovimiento(Date ultimoMovimiento) {
        this.ultimoMovimiento = ultimoMovimiento;
    }

    public Integer getIdEstatusSolucion() {
        return idEstatusSolucion;
    }

    public void setIdEstatusSolucion(Integer idEstatusSolucion) {
        this.idEstatusSolucion = idEstatusSolucion;
    }

    public String getUsuarioRechaza() {
        return usuarioRechaza;
    }

    public void setUsuarioRechaza(String usuarioRechaza) {
        this.usuarioRechaza = usuarioRechaza;
    }

    public String getComentariosRechazo() {
        return comentariosRechazo;
    }

    public void setComentariosRechazo(String comentariosRechazo) {
        this.comentariosRechazo = comentariosRechazo;
    }

    public Date getUpdateFecha() {
        return updateFecha;
    }

    public void setUpdateFecha(Date updateFecha) {
        this.updateFecha = updateFecha;
    }

    public String getUsuarioAutoriza() {
        return usuarioAutoriza;
    }

    public void setUsuarioAutoriza(String usuarioAutoriza) {
        this.usuarioAutoriza = usuarioAutoriza;
    }

    public String getComentarioAutoriza() {
        return comentarioAutoriza;
    }

    public void setComentarioAutoriza(String comentarioAutoriza) {
        this.comentarioAutoriza = comentarioAutoriza;
    }

    public Date getFechaAutoriza() {
        return fechaAutoriza;
    }

    public void setFechaAutoriza(Date fechaAutoriza) {
        this.fechaAutoriza = fechaAutoriza;
    }

    @Override
    public String toString() {
        return "Mezcla{" + "idPrescripcion=" + idPrescripcion + ", idSurtimiento=" + idSurtimiento + ", idSolucion=" + idSolucion + ", fechaFirma=" + fechaFirma + ", folioPrescripcion=" + folioPrescripcion + ", estatusPrescripcion=" + estatusPrescripcion + ", nombreServicio=" + nombreServicio + ", nombrePaciente=" + nombrePaciente + ", fechaProgramada=" + fechaProgramada + ", folioSurtimiento=" + folioSurtimiento + ", descripcionMezcla=" + descripcionMezcla + ", nombreAlmacen=" + nombreAlmacen + ", estatusSurtimiento=" + estatusSurtimiento + ", loteMezcla=" + loteMezcla + ", caducidadMezcla=" + caducidadMezcla + ", fechaPrescripcion=" + fechaPrescripcion + ", nombreMedico=" + nombreMedico + ", fechaValPrescr=" + fechaValPrescr + ", comentValPrescr=" + comentValPrescr + ", usuarioValidaPresc=" + usuarioValidaPresc + ", fechaValida=" + fechaValida + ", comentValOrdenPrep=" + comentValOrdenPrep + ", usuarioValidaOP=" + usuarioValidaOP + ", fechaDispensacion=" + fechaDispensacion + ", usuarioDispensa=" + usuarioDispensa + ", fechaPrepara=" + fechaPrepara + ", comentariosPreparacion=" + comentariosPreparacion + ", usuarioPrepara=" + usuarioPrepara + ", fechaInspeccion=" + fechaInspeccion + ", comentarioInspeccion=" + comentarioInspeccion + ", usuarioInspecciona=" + usuarioInspecciona + ", fechaAcondicionamiento=" + fechaAcondicionamiento + ", comentarioAcondicionada=" + comentarioAcondicionada + ", usuarioAcondiciona=" + usuarioAcondiciona + ", fechaEntrega=" + fechaEntrega + ", comentariosEntrega=" + comentariosEntrega + ", usuarioEntrega=" + usuarioEntrega + ", fechaRecibe=" + fechaRecibe + ", usuarioRecibe=" + usuarioRecibe + ", estatusSolucion=" + estatusSolucion + ", posologia=" + posologia + ", ultimoMovimiento=" + ultimoMovimiento + ", idEstatusSolucion=" + idEstatusSolucion + ", usuarioRechaza=" + usuarioRechaza + ", comentariosRechazo=" + comentariosRechazo + ", updateFecha=" + updateFecha + ", usuarioAutoriza=" + usuarioAutoriza + ", comentarioAutoriza=" + comentarioAutoriza + ", fechaAutoriza=" + fechaAutoriza + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.idPrescripcion);
        hash = 79 * hash + Objects.hashCode(this.idSurtimiento);
        hash = 79 * hash + Objects.hashCode(this.idSolucion);
        hash = 79 * hash + Objects.hashCode(this.fechaFirma);
        hash = 79 * hash + Objects.hashCode(this.folioPrescripcion);
        hash = 79 * hash + Objects.hashCode(this.estatusPrescripcion);
        hash = 79 * hash + Objects.hashCode(this.nombreServicio);
        hash = 79 * hash + Objects.hashCode(this.nombrePaciente);
        hash = 79 * hash + Objects.hashCode(this.fechaProgramada);
        hash = 79 * hash + Objects.hashCode(this.folioSurtimiento);
        hash = 79 * hash + Objects.hashCode(this.descripcionMezcla);
        hash = 79 * hash + Objects.hashCode(this.nombreAlmacen);
        hash = 79 * hash + Objects.hashCode(this.estatusSurtimiento);
        hash = 79 * hash + Objects.hashCode(this.loteMezcla);
        hash = 79 * hash + Objects.hashCode(this.caducidadMezcla);
        hash = 79 * hash + Objects.hashCode(this.fechaPrescripcion);
        hash = 79 * hash + Objects.hashCode(this.nombreMedico);
        hash = 79 * hash + Objects.hashCode(this.fechaValPrescr);
        hash = 79 * hash + Objects.hashCode(this.comentValPrescr);
        hash = 79 * hash + Objects.hashCode(this.usuarioValidaPresc);
        hash = 79 * hash + Objects.hashCode(this.fechaValida);
        hash = 79 * hash + Objects.hashCode(this.comentValOrdenPrep);
        hash = 79 * hash + Objects.hashCode(this.usuarioValidaOP);
        hash = 79 * hash + Objects.hashCode(this.fechaDispensacion);
        hash = 79 * hash + Objects.hashCode(this.usuarioDispensa);
        hash = 79 * hash + Objects.hashCode(this.fechaPrepara);
        hash = 79 * hash + Objects.hashCode(this.comentariosPreparacion);
        hash = 79 * hash + Objects.hashCode(this.usuarioPrepara);
        hash = 79 * hash + Objects.hashCode(this.fechaInspeccion);
        hash = 79 * hash + Objects.hashCode(this.comentarioInspeccion);
        hash = 79 * hash + Objects.hashCode(this.usuarioInspecciona);
        hash = 79 * hash + Objects.hashCode(this.fechaAcondicionamiento);
        hash = 79 * hash + Objects.hashCode(this.comentarioAcondicionada);
        hash = 79 * hash + Objects.hashCode(this.usuarioAcondiciona);
        hash = 79 * hash + Objects.hashCode(this.fechaEntrega);
        hash = 79 * hash + Objects.hashCode(this.comentariosEntrega);
        hash = 79 * hash + Objects.hashCode(this.usuarioEntrega);
        hash = 79 * hash + Objects.hashCode(this.fechaRecibe);
        hash = 79 * hash + Objects.hashCode(this.usuarioRecibe);
        hash = 79 * hash + Objects.hashCode(this.estatusSolucion);
        hash = 79 * hash + Objects.hashCode(this.posologia);
        hash = 79 * hash + Objects.hashCode(this.ultimoMovimiento);
        hash = 79 * hash + Objects.hashCode(this.idEstatusSolucion);
        hash = 79 * hash + Objects.hashCode(this.usuarioRechaza);
        hash = 79 * hash + Objects.hashCode(this.comentariosRechazo);
        hash = 79 * hash + Objects.hashCode(this.updateFecha);
        hash = 79 * hash + Objects.hashCode(this.usuarioAutoriza);
        hash = 79 * hash + Objects.hashCode(this.comentarioAutoriza);
        hash = 79 * hash + Objects.hashCode(this.fechaAutoriza);
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
        final Mezcla other = (Mezcla) obj;
        if (!Objects.equals(this.idPrescripcion, other.idPrescripcion)) {
            return false;
        }
        if (!Objects.equals(this.idSurtimiento, other.idSurtimiento)) {
            return false;
        }
        if (!Objects.equals(this.idSolucion, other.idSolucion)) {
            return false;
        }
        if (!Objects.equals(this.folioPrescripcion, other.folioPrescripcion)) {
            return false;
        }
        if (!Objects.equals(this.estatusPrescripcion, other.estatusPrescripcion)) {
            return false;
        }
        if (!Objects.equals(this.nombreServicio, other.nombreServicio)) {
            return false;
        }
        if (!Objects.equals(this.nombrePaciente, other.nombrePaciente)) {
            return false;
        }
        if (!Objects.equals(this.folioSurtimiento, other.folioSurtimiento)) {
            return false;
        }
        if (!Objects.equals(this.descripcionMezcla, other.descripcionMezcla)) {
            return false;
        }
        if (!Objects.equals(this.nombreAlmacen, other.nombreAlmacen)) {
            return false;
        }
        if (!Objects.equals(this.estatusSurtimiento, other.estatusSurtimiento)) {
            return false;
        }
        if (!Objects.equals(this.loteMezcla, other.loteMezcla)) {
            return false;
        }
        if (!Objects.equals(this.nombreMedico, other.nombreMedico)) {
            return false;
        }
        if (!Objects.equals(this.comentValPrescr, other.comentValPrescr)) {
            return false;
        }
        if (!Objects.equals(this.usuarioValidaPresc, other.usuarioValidaPresc)) {
            return false;
        }
        if (!Objects.equals(this.comentValOrdenPrep, other.comentValOrdenPrep)) {
            return false;
        }
        if (!Objects.equals(this.usuarioValidaOP, other.usuarioValidaOP)) {
            return false;
        }
        if (!Objects.equals(this.usuarioDispensa, other.usuarioDispensa)) {
            return false;
        }
        if (!Objects.equals(this.comentariosPreparacion, other.comentariosPreparacion)) {
            return false;
        }
        if (!Objects.equals(this.usuarioPrepara, other.usuarioPrepara)) {
            return false;
        }
        if (!Objects.equals(this.comentarioInspeccion, other.comentarioInspeccion)) {
            return false;
        }
        if (!Objects.equals(this.usuarioInspecciona, other.usuarioInspecciona)) {
            return false;
        }
        if (!Objects.equals(this.comentarioAcondicionada, other.comentarioAcondicionada)) {
            return false;
        }
        if (!Objects.equals(this.usuarioAcondiciona, other.usuarioAcondiciona)) {
            return false;
        }
        if (!Objects.equals(this.comentariosEntrega, other.comentariosEntrega)) {
            return false;
        }
        if (!Objects.equals(this.usuarioEntrega, other.usuarioEntrega)) {
            return false;
        }
        if (!Objects.equals(this.usuarioRecibe, other.usuarioRecibe)) {
            return false;
        }
        if (!Objects.equals(this.estatusSolucion, other.estatusSolucion)) {
            return false;
        }
        if (!Objects.equals(this.posologia, other.posologia)) {
            return false;
        }
        if (!Objects.equals(this.usuarioRechaza, other.usuarioRechaza)) {
            return false;
        }
        if (!Objects.equals(this.comentariosRechazo, other.comentariosRechazo)) {
            return false;
        }
        if (!Objects.equals(this.updateFecha, other.updateFecha)) {
            return false;
        }
        if (!Objects.equals(this.usuarioAutoriza, other.usuarioAutoriza)) {
            return false;
        }
        if (!Objects.equals(this.comentarioAutoriza, other.comentarioAutoriza)) {
            return false;
        }
        if (!Objects.equals(this.fechaAutoriza, other.fechaAutoriza)) {
            return false;
        }
        if (!Objects.equals(this.fechaFirma, other.fechaFirma)) {
            return false;
        }
        if (!Objects.equals(this.fechaProgramada, other.fechaProgramada)) {
            return false;
        }
        if (!Objects.equals(this.caducidadMezcla, other.caducidadMezcla)) {
            return false;
        }
        if (!Objects.equals(this.fechaPrescripcion, other.fechaPrescripcion)) {
            return false;
        }
        if (!Objects.equals(this.fechaValPrescr, other.fechaValPrescr)) {
            return false;
        }
        if (!Objects.equals(this.fechaValida, other.fechaValida)) {
            return false;
        }
        if (!Objects.equals(this.fechaDispensacion, other.fechaDispensacion)) {
            return false;
        }
        if (!Objects.equals(this.fechaPrepara, other.fechaPrepara)) {
            return false;
        }
        if (!Objects.equals(this.fechaInspeccion, other.fechaInspeccion)) {
            return false;
        }
        if (!Objects.equals(this.fechaAcondicionamiento, other.fechaAcondicionamiento)) {
            return false;
        }
        if (!Objects.equals(this.fechaEntrega, other.fechaEntrega)) {
            return false;
        }
        if (!Objects.equals(this.fechaRecibe, other.fechaRecibe)) {
            return false;
        }
        if (!Objects.equals(this.ultimoMovimiento, other.ultimoMovimiento)) {
            return false;
        }
        return Objects.equals(this.idEstatusSolucion, other.idEstatusSolucion);
    }
    
}
