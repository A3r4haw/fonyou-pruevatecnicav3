package mx.mc.model;

import java.io.Serializable;
import java.util.Date;

public class DataResultReport implements Serializable {
    private static final long serialVersionUID = 1L;

    private String idAlmacen;
    private String nombreAlmacen;
    private String tipoMovimiento;
    private String claveMedicamento;
    private String nombreMedicamento;
    private String lote;
    private int cantidad;
    private String folio;
    private String idUsuario;
    private String nombreUsuario;
    private Date fechaMovimiento;
    private String nombre;
    private Float montoPorServicio;
    private Integer pacientesAtendidos;
    private Float costoPerCapita;
    private String idServicio;
    private String ubicacion;
    private String cama;
    private String clave;
    private String estatus;
    private String paciente;
    private Integer cantidadXCaja;
    private Date fechaCaducidad;
    private Integer cantidadRecibida;
    private String claveArticulo;
    private String unidad;
    private int existenciaFisica;
    private int consumo;
    private String descripcion;
    private String proveedorClave;
    private String nombreProveedor;
    private int noNotificacion;

    //repGeneral Chiconcuac
    private String folioReceta;
    private String reabasto;
    private String medico;    
    private String justificacion;
    private Integer idTipoOrigen;    
    
    //repAcumulados
    private Integer cantidadEnviada;
    private Integer cantidadSurtida;
    private Integer cantidadActual;
    private String servicio;
    private Date fechaElaboracion;
    
    public String getIdAlmacen() {
        return idAlmacen;
    }

    public void setIdAlmacen(String idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    public String getNombreAlmacen() {
        return nombreAlmacen;
    }

    public void setNombreAlmacen(String nombreAlmacen) {
        this.nombreAlmacen = nombreAlmacen;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public String getClaveMedicamento() {
        return claveMedicamento;
    }

    public void setClaveMedicamento(String claveMedicamento) {
        this.claveMedicamento = claveMedicamento;
    }

    public String getNombreMedicamento() {
        return nombreMedicamento;
    }

    public void setNombreMedicamento(String nombreMedicamento) {
        this.nombreMedicamento = nombreMedicamento;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Date getFechaMovimiento() {
        return fechaMovimiento;
    }

    public void setFechaMovimiento(Date fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Float getMontoPorServicio() {
        return montoPorServicio;
    }

    public void setMontoPorServicio(Float montoPorServicio) {
        this.montoPorServicio = montoPorServicio;
    }

    public Integer getPacientesAtendidos() {
        return pacientesAtendidos;
    }

    public void setPacientesAtendidos(Integer pacientesAtendidos) {
        this.pacientesAtendidos = pacientesAtendidos;
    }

    public Float getCostoPerCapita() {
        return costoPerCapita;
    }

    public void setCostoPerCapita(Float costoPerCapita) {
        this.costoPerCapita = costoPerCapita;
    }

    public String getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(String idServicio) {
        this.idServicio = idServicio;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getCama() {
        return cama;
    }

    public void setCama(String cama) {
        this.cama = cama;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public Integer getCantidadXCaja() {
        return cantidadXCaja;
    }

    public void setCantidadXCaja(Integer cantidadXCaja) {
        this.cantidadXCaja = cantidadXCaja;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }    

    public String getFolioReceta() {
        return folioReceta;
    }

    public void setFolioReceta(String folioReceta) {
        this.folioReceta = folioReceta;
    }

    public String getReabasto() {
        return reabasto;
    }

    public void setReabasto(String reabasto) {
        this.reabasto = reabasto;
    }

    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

    public Integer getIdTipoOrigen() {
        return idTipoOrigen;
    }

    public void setIdTipoOrigen(Integer idTipoOrigen) {
        this.idTipoOrigen = idTipoOrigen;
    }

    public Integer getCantidadEnviada() {
        return cantidadEnviada;
    }

    public void setCantidadEnviada(Integer cantidadEnviada) {
        this.cantidadEnviada = cantidadEnviada;
    }

    public Integer getCantidadSurtida() {
        return cantidadSurtida;
    }

    public void setCantidadSurtida(Integer cantidadSurtida) {
        this.cantidadSurtida = cantidadSurtida;
    }

    public Integer getCantidadActual() {
        return cantidadActual;
    }

    public void setCantidadActual(Integer cantidadActual) {
        this.cantidadActual = cantidadActual;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public Date getFechaElaboracion() {
        return fechaElaboracion;
    }

    public void setFechaElaboracion(Date fechaElaboracion) {
        this.fechaElaboracion = fechaElaboracion;
    }

    public Integer getCantidadRecibida() {
        return cantidadRecibida;
    }

    public void setCantidadRecibida(Integer cantidadRecibida) {
        this.cantidadRecibida = cantidadRecibida;
    }

    public String getClaveArticulo() {
        return claveArticulo;
    }

    public void setClaveArticulo(String claveArticulo) {
        this.claveArticulo = claveArticulo;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public int getExistenciaFisica() {
        return existenciaFisica;
    }

    public void setExistenciaFisica(int existenciaFisica) {
        this.existenciaFisica = existenciaFisica;
    }

    public int getConsumo() {
        return consumo;
    }

    public void setConsumo(int consumo) {
        this.consumo = consumo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getProveedorClave() {
        return proveedorClave;
    }

    public void setProveedorClave(String proveedorClave) {
        this.proveedorClave = proveedorClave;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public int getNoNotificacion() {
        return noNotificacion;
    }

    public void setNoNotificacion(int noNotificacion) {
        this.noNotificacion = noNotificacion;
    }

    
    
}
