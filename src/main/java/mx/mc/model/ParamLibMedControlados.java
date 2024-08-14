package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author AORTIZ
 */
public class ParamLibMedControlados implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Date fechaInicio;
    private Date fechaFin;
    private List<Medicamento> listInsumos;
    private List<Inventario> listaInventarios;
    private Integer tipoMovimiento;
    private List<Usuario> listUsuarios;
    private String idEstructura;
    private List<Integer> idTipoMovimientos;
    private List<Integer> listaSubCategorias;
    private String numeroPaciente;
    private String servicio;
    private String cama;
    private List<String> listTipoMensaje;
    private String ingresoMensaje;
    private boolean nuevaBusqueda;
    private String nombreEstructura;
    private Integer idEstatusPrescripcion;
    private String idMedicamento;
    private String idInventario;
    private List<Paciente> pacienteList;
    private String tipoPrescripcion;
    private Integer idTipoOrigen;
    private boolean cargaLayoutConIngresoPrellenado;
    private String cadenaBusqueda;
    private String folio;
    private String sortOrder;
    private String sortField;
    private String valorRefri5000;
    private Long total;
    private String claves;
    private String tipoReceta;
    private String refrigeracion;
    private String valueControlDiario;
    private String nombreEntidad;
    private String domicilio;
    private String usuarioGeneraReporte;
    private String claveUsuarioGenReporte;
    
    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public List<Medicamento> getListInsumos() {
        return listInsumos;
    }

    public void setListInsumos(List<Medicamento> listInsumos) {
        this.listInsumos = listInsumos;
    }

    public Integer getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(Integer tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public List<Usuario> getListUsuarios() {
        return listUsuarios;
    }

    public void setListUsuarios(List<Usuario> listUsuarios) {
        this.listUsuarios = listUsuarios;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public List<Integer> getIdTipoMovimientos() {
        return idTipoMovimientos;
    }

    public void setIdTipoMovimientos(List<Integer> idTipoMovimientos) {
        this.idTipoMovimientos = idTipoMovimientos;
    }

    public String getNumeroPaciente() {
        return numeroPaciente;
    }

    public void setNumeroPaciente(String numeroPaciente) {
        this.numeroPaciente = numeroPaciente;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getCama() {
        return cama;
    }

    public void setCama(String cama) {
        this.cama = cama;
    }

    public List<String> getListTipoMensaje() {
        return listTipoMensaje;
    }

    public void setListTipoMensaje(List<String> listTipoMensaje) {
        this.listTipoMensaje = listTipoMensaje;
    }

    public boolean isNuevaBusqueda() {
        return nuevaBusqueda;
    }

    public void setNuevaBusqueda(boolean nuevaBusqueda) {
        this.nuevaBusqueda = nuevaBusqueda;
    }

    public String getIngresoMensaje() {
        return ingresoMensaje;
    }

    public void setIngresoMensaje(String ingresoMensaje) {
        this.ingresoMensaje = ingresoMensaje;
    }

    public String getNombreEstructura() {
        return nombreEstructura;
    }

    public void setNombreEstructura(String nombreEstructura) {
        this.nombreEstructura = nombreEstructura;
    }

    public String getCadenaBusqueda() {
        return cadenaBusqueda;
    }

    public void setCadenaBusqueda(String cadenaBusqueda) {
        this.cadenaBusqueda = cadenaBusqueda;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public Integer getIdEstatusPrescripcion() {
        return idEstatusPrescripcion;
    }

    public void setIdEstatusPrescripcion(Integer idEstatusPrescripcion) {
        this.idEstatusPrescripcion = idEstatusPrescripcion;
    }

    public List<Paciente> getPacienteList() {
        return pacienteList;
    }

    public void setPacienteList(List<Paciente> pacienteList) {
        this.pacienteList = pacienteList;
    }

    public String getTipoPrescripcion() {
        return tipoPrescripcion;
    }

    public void setTipoPrescripcion(String tipoPrescripcion) {
        this.tipoPrescripcion = tipoPrescripcion;
    }

    public Integer getIdTipoOrigen() {
        return idTipoOrigen;
    }

    public void setIdTipoOrigen(Integer idTipoOrigen) {
        this.idTipoOrigen = idTipoOrigen;
    }

    public List<Integer> getListaSubCategorias() {
        return listaSubCategorias;
    }

    public void setListaSubCategorias(List<Integer> listaSubCategorias) {
        this.listaSubCategorias = listaSubCategorias;
    }

    public String getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(String idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public List<Inventario> getListaInventarios() {
        return listaInventarios;
    }

    public void setListaInventarios(List<Inventario> listaInventarios) {
        this.listaInventarios = listaInventarios;
    }

    public String getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(String idInventario) {
        this.idInventario = idInventario;
    }

    public String getValorRefri5000() {
        return valorRefri5000;
    }

    public void setValorRefri5000(String valorRefri5000) {
        this.valorRefri5000 = valorRefri5000;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public String getClaves() {
        return claves;
    }

    public void setClaves(String claves) {
        this.claves = claves;
    }

    public String getTipoReceta() {
        return tipoReceta;
    }

    public void setTipoReceta(String tipoReceta) {
        this.tipoReceta = tipoReceta;
    }

    public String getRefrigeracion() {
        return refrigeracion;
    }

    public void setRefrigeracion(String refrigeracion) {
        this.refrigeracion = refrigeracion;
    }

    public boolean isCargaLayoutConIngresoPrellenado() {
        return cargaLayoutConIngresoPrellenado;
    }

    public void setCargaLayoutConIngresoPrellenado(boolean cargaLayoutConIngresoPrellenado) {
        this.cargaLayoutConIngresoPrellenado = cargaLayoutConIngresoPrellenado;
    }

    public String getValueControlDiario() {
        return valueControlDiario;
    }

    public void setValueControlDiario(String valueControlDiario) {
        this.valueControlDiario = valueControlDiario;
    }

    public String getNombreEntidad() {
        return nombreEntidad;
    }

    public void setNombreEntidad(String nombreEntidad) {
        this.nombreEntidad = nombreEntidad;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getUsuarioGeneraReporte() {
        return usuarioGeneraReporte;
    }

    public void setUsuarioGeneraReporte(String usuarioGeneraReporte) {
        this.usuarioGeneraReporte = usuarioGeneraReporte;
    }

    public String getClaveUsuarioGenReporte() {
        return claveUsuarioGenReporte;
    }

    public void setClaveUsuarioGenReporte(String claveUsuarioGenReporte) {
        this.claveUsuarioGenReporte = claveUsuarioGenReporte;
    }
   
}
