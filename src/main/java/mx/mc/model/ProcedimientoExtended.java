package mx.mc.model;

/**
 * @author apalacios
 */
public class ProcedimientoExtended extends Procedimiento {
    private static final long serialVersionUID = 1L;

    private String estatusProced;
    private String claveEstudio;
    private String descEstudio;
    private String tipoSolicitud;
    private int prioridad;
    private String medioContraste;
    private String sedante;
    private String usuarioSolicita;
    private String usuarioPrograma;
    private String usuarioConfirma;
    private String usuarioAsigna;
    private String usuarioRealiza;
    private String usuarioInterpreta;

    public ProcedimientoExtended() {
        //No code needed in constructor
    }

    public String getEstatusProced() {
        return estatusProced;
    }

    public void setEstatusProced(String estatusProced) {
        this.estatusProced = estatusProced;
    }

    public String getClaveEstudio() {
        return claveEstudio;
    }

    public void setClaveEstudio(String claveEstudio) {
        this.claveEstudio = claveEstudio;
    }

    public String getDescEstudio() {
        return descEstudio;
    }

    public void setDescEstudio(String descEstudio) {
        this.descEstudio = descEstudio;
    }

    public String getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(String tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public String getMedioContraste() {
        return medioContraste;
    }

    public void setMedioContraste(String medioContraste) {
        this.medioContraste = medioContraste;
    }

    public String getSedante() {
        return sedante;
    }

    public void setSedante(String sedante) {
        this.sedante = sedante;
    }

    public String getUsuarioSolicita() {
        return usuarioSolicita;
    }

    public void setUsuarioSolicita(String usuarioSolicita) {
        this.usuarioSolicita = usuarioSolicita;
    }

    public String getUsuarioPrograma() {
        return usuarioPrograma;
    }

    public void setUsuarioPrograma(String usuarioPrograma) {
        this.usuarioPrograma = usuarioPrograma;
    }

    public String getUsuarioConfirma() {
        return usuarioConfirma;
    }

    public void setUsuarioConfirma(String usuarioConfirma) {
        this.usuarioConfirma = usuarioConfirma;
    }

    public String getUsuarioAsigna() {
        return usuarioAsigna;
    }

    public void setUsuarioAsigna(String usuarioAsigna) {
        this.usuarioAsigna = usuarioAsigna;
    }

    public String getUsuarioRealiza() {
        return usuarioRealiza;
    }

    public void setUsuarioRealiza(String usuarioRealiza) {
        this.usuarioRealiza = usuarioRealiza;
    }

    public String getUsuarioInterpreta() {
        return usuarioInterpreta;
    }

    public void setUsuarioInterpreta(String usuarioInterpreta) {
        this.usuarioInterpreta = usuarioInterpreta;
    }    
}
