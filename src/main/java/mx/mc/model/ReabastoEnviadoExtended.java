package mx.mc.model;

import java.util.Date;

/**
 *
 * @author AORTIZ
 */
public class ReabastoEnviadoExtended extends ReabastoEnviado {

    private static final long serialVersionUID = 1L;

    private String lote;
    private Date fechaCaducidad;
    private String idMedicamento;
    private Integer activo;
    private String idReabasto;
    private Date fecha;
    private Integer cantidadCaja;
    private String claveInstitucional;

    private String folio;
    private Date fechaSolicita;
    private String nombrePersonaSolicita;
    private Date fechaSurte;
    private String nombrePersonaSurte;

    private String nombreProveedor;
    private String nombreFabricante;
    private Integer cantidadSolicitada;
    private Date fechaSolicitud;
    private String nombrePersonaRecibe;

    public ReabastoEnviadoExtended() {
        //No code needed in constructor
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public String getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(String idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }

    public String getIdReabasto() {
        return idReabasto;
    }

    public void setIdReabasto(String idReabasto) {
        this.idReabasto = idReabasto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getCantidadCaja() {
        return cantidadCaja;
    }

    public void setCantidadCaja(Integer cantidadCaja) {
        this.cantidadCaja = cantidadCaja;
    }

    public String getClaveInstitucional() {
        return claveInstitucional;
    }

    public void setClaveInstitucional(String claveInstitucional) {
        this.claveInstitucional = claveInstitucional;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public Date getFechaSolicita() {
        return fechaSolicita;
    }

    public void setFechaSolicita(Date fechaSolicita) {
        this.fechaSolicita = fechaSolicita;
    }

    public String getNombrePersonaSolicita() {
        return nombrePersonaSolicita;
    }

    public void setNombrePersonaSolicita(String nombrePersonaSolicita) {
        this.nombrePersonaSolicita = nombrePersonaSolicita;
    }

    public Date getFechaSurte() {
        return fechaSurte;
    }

    public void setFechaSurte(Date fechaSurte) {
        this.fechaSurte = fechaSurte;
    }

    public String getNombrePersonaSurte() {
        return nombrePersonaSurte;
    }

    public void setNombrePersonaSurte(String nombrePersonaSurte) {
        this.nombrePersonaSurte = nombrePersonaSurte;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getNombreFabricante() {
        return nombreFabricante;
    }

    public void setNombreFabricante(String nombreFabricante) {
        this.nombreFabricante = nombreFabricante;
    }

    public Integer getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    public void setCantidadSolicitada(Integer cantidadSolicitada) {
        this.cantidadSolicitada = cantidadSolicitada;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getNombrePersonaRecibe() {
        return nombrePersonaRecibe;
    }

    public void setNombrePersonaRecibe(String nombrePersonaRecibe) {
        this.nombrePersonaRecibe = nombrePersonaRecibe;
    }

}
