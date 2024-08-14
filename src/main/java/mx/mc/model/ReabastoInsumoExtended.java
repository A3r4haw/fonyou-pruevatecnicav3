package mx.mc.model;

import java.util.Date;
import java.util.List;

/**
 * @author AORTIZ
 */
public class ReabastoInsumoExtended extends ReabastoInsumo {

    private static final long serialVersionUID = 1L;

    private String claveInstitucional;
    private String nombreCorto;
    private String nombrePresentacion;
    private Integer idPresentacion;
    private Integer factorTransformacion;
    private Double costo;
    private transient List<ReabastoEnviadoExtended> listaDetalleReabIns;
    private Integer activo;
    private Integer cantidadActual;
    private Integer cantidadXCaja;
    private String folio;
    private String idEstructura;
    private String idEstructuraPadre;
    private String idInventario;
    private String lote;
    private Date fechaCaducidad;
    private String claveEstructura;
    private String almacen;
    private String nombreProveedor;

    private Integer solicitado;
    private Integer enviado;
    private Integer maximo;
    private Date fechaEnviada;
    private String tipoInsumo;

    private String fechaSolicitud;
    private String fechaRecepcion;
    private String idUsuarioSolicitud;
    private String idUsuarioRecepcion;
    private String nombrePersonaSolicita;
    private String nombrePersonaRecibe;
    private String estatusReabasto;
    private String proveedorCompra;
    private String idProveedor;
    private Integer idFabricante;
    private String claveProveedor;
    private Double noHorasEstabilidad;
    private Double osmolaridad;
    private Double densidad;
    private Double calorias;
    private String loteEnv;
    private Date fechaCad;

    public ReabastoInsumoExtended() {
        //No code needed in constructor
    }

    public String getClaveInstitucional() {
        return claveInstitucional;
    }

    public void setClaveInstitucional(String claveInstitucional) {
        this.claveInstitucional = claveInstitucional;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    public String getNombrePresentacion() {
        return nombrePresentacion;
    }

    public void setNombrePresentacion(String nombrePresentacion) {
        this.nombrePresentacion = nombrePresentacion;
    }

    public Integer getIdPresentacion() {
        return idPresentacion;
    }

    public void setIdPresentacion(Integer idPresentacion) {
        this.idPresentacion = idPresentacion;
    }

    public Integer getFactorTransformacion() {
        return factorTransformacion;
    }

    public void setFactorTransformacion(Integer factorTransformacion) {
        this.factorTransformacion = factorTransformacion;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public List<ReabastoEnviadoExtended> getListaDetalleReabIns() {
        return listaDetalleReabIns;
    }

    public void setListaDetalleReabIns(List<ReabastoEnviadoExtended> listaDetalleReabIns) {
        this.listaDetalleReabIns = listaDetalleReabIns;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }

    public Integer getCantidadActual() {
        return cantidadActual;
    }

    public void setCantidadActual(Integer cantidadActual) {
        this.cantidadActual = cantidadActual;
    }

    public Integer getCantidadXCaja() {
        return cantidadXCaja;
    }

    public void setCantidadXCaja(Integer cantidadXCaja) {
        this.cantidadXCaja = cantidadXCaja;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public String getIdEstructuraPadre() {
        return idEstructuraPadre;
    }

    public void setIdEstructuraPadre(String idEstructuraPadre) {
        this.idEstructuraPadre = idEstructuraPadre;
    }

    public String getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(String idInventario) {
        this.idInventario = idInventario;
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

    public String getClaveEstructura() {
        return claveEstructura;
    }

    public void setClaveEstructura(String claveEstructura) {
        this.claveEstructura = claveEstructura;
    }

    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public Integer getSolicitado() {
        return solicitado;
    }

    public void setSolicitado(Integer solicitado) {
        this.solicitado = solicitado;
    }

    public Integer getEnviado() {
        return enviado;
    }

    public void setEnviado(Integer enviado) {
        this.enviado = enviado;
    }

    public Integer getMaximo() {
        return maximo;
    }

    public void setMaximo(Integer maximo) {
        this.maximo = maximo;
    }

    public Date getFechaEnviada() {
        return fechaEnviada;
    }

    public void setFechaEnviada(Date fechaEnviada) {
        this.fechaEnviada = fechaEnviada;
    }

    public String getTipoInsumo() {
        return tipoInsumo;
    }

    public void setTipoInsumo(String tipoInsumo) {
        this.tipoInsumo = tipoInsumo;
    }

    public String getNombrePersonaSolicita() {
        return nombrePersonaSolicita;
    }

    public void setNombrePersonaSolicita(String nombrePersonaSolicita) {
        this.nombrePersonaSolicita = nombrePersonaSolicita;
    }

    public String getNombrePersonaRecibe() {
        return nombrePersonaRecibe;
    }

    public void setNombrePersonaRecibe(String nombrePersonaRecibe) {
        this.nombrePersonaRecibe = nombrePersonaRecibe;
    }

    public String getEstatusReabasto() {
        return estatusReabasto;
    }

    public void setEstatusReabasto(String estatusReabasto) {
        this.estatusReabasto = estatusReabasto;
    }

    public String getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(String fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(String fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public String getIdUsuarioSolicitud() {
        return idUsuarioSolicitud;
    }

    public void setIdUsuarioSolicitud(String idUsuarioSolicitud) {
        this.idUsuarioSolicitud = idUsuarioSolicitud;
    }

    public String getIdUsuarioRecepcion() {
        return idUsuarioRecepcion;
    }

    public void setIdUsuarioRecepcion(String idUsuarioRecepcion) {
        this.idUsuarioRecepcion = idUsuarioRecepcion;
    }

    public String getProveedorCompra() {
        return proveedorCompra;
    }

    public void setProveedorCompra(String proveedorCompra) {
        this.proveedorCompra = proveedorCompra;
    }

    public String getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(String idProveedor) {
        this.idProveedor = idProveedor;
    }

    public Integer getIdFabricante() {
        return idFabricante;
    }

    public void setIdFabricante(Integer idFabricante) {
        this.idFabricante = idFabricante;
    }

    public String getClaveProveedor() {
        return claveProveedor;
    }

    public void setClaveProveedor(String claveProveedor) {
        this.claveProveedor = claveProveedor;
    }

    public Double getNoHorasEstabilidad() {
        return noHorasEstabilidad;
    }

    public void setNoHorasEstabilidad(Double noHorasEstabilidad) {
        this.noHorasEstabilidad = noHorasEstabilidad;
    }

    public Double getOsmolaridad() {
        return osmolaridad;
    }

    public void setOsmolaridad(Double osmolaridad) {
        this.osmolaridad = osmolaridad;
    }

    public Double getDensidad() {
        return densidad;
    }

    public void setDensidad(Double densidad) {
        this.densidad = densidad;
    }

    public Double getCalorias() {
        return calorias;
    }

    public void setCalorias(Double calorias) {
        this.calorias = calorias;
    }

    public String getLoteEnv() {
        return loteEnv;
    }

    public void setLoteEnv(String loteEnv) {
        this.loteEnv = loteEnv;
    }

    public Date getFechaCad() {
        return fechaCad;
    }

    public void setFechaCad(Date fechaCad) {
        this.fechaCad = fechaCad;
    }

    

}
