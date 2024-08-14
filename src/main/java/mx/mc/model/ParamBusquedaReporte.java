package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import org.primefaces.model.SortOrder;

/**
 *
 * @author gcruz
 *
 */
public class ParamBusquedaReporte implements Serializable {

    private static final long serialVersionUID = 1L;

    private Date fechaInicio;
    private Date fechaFin;
    private List<Medicamento> listInsumos;
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
    private String fechaConvertInicio;
    private String fechaConvertFin;
    //repReceta
    private Integer idEstatusPrescripcion;
    private List<Paciente> pacienteList;
    private String tipoPrescripcion;
    private Integer idTipoOrigen;
    private boolean hospChiconcuac;
    private boolean permiteAjusteInventarioGlobal;
    private boolean activaCamposRepEmisionRecetas;
    private boolean activaCamposReporteMovimientosGenerales;
    private int cantidadCero;

    private String cadenaBusqueda;
    private int tipoMinistrado;
    private List<Usuario> listMedicos;
    private String estatusCantidadInsumo;
    private int tipoPerfil;
    private List<String> listaEstructuras;
    private List<String> listaAlmacenes;
    //ReporteAcumuladoChiconcuac
    private int tipoAcumulado;
    private int tipoInsumo;
    private String tipoReceta;
    private String idMedico;
    private int valTipoReceta;
    //folio
    private String folio;
    private String sortOrder;
    private String sortField;
    private int startingAt;
    private int maxPerPage;
    private SortOrder orden;

    private String idPaciente;
    private Long total;
    //Concentracion reporte
    private int intervalDate;
    private List<ReporteConcentracionArticulos> listConcentracion;
    private Estructura estructura;
    private EntidadHospitalaria entidadHospitalaria;
    private String nombreCorto;
    private List<RepInsumoNoMinistrado> listaInsumo;
    private String folioSurtimiento;
    private String idTipoSolucion;
    private Integer idEstatusSolucion;
    private String estatusSolucion;

    private transient List<String> listaMedicos;
    private transient List<String> listaPaciente;
    private transient List<String> listaUsuarios;
    private transient List<String> listaInsumos;
    private transient List<String> listaDiagostico;
    private transient List<Diagnostico> listDiagosticos;
	private Usuario usuarioGenera;

    public int getIntervalDate() {
        return intervalDate;
    }

    public void setIntervalDate(int intervalDate) {
        this.intervalDate = intervalDate;
    }

    private String idCapsula;

    private List<EnvioNeumatico> listaCapsulas;

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public List<ReporteConcentracionArticulos> getListConcentracion() {
        return listConcentracion;
    }

    public void setListConcentracion(List<ReporteConcentracionArticulos> listConcentracion) {
        this.listConcentracion = listConcentracion;
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

    public SortOrder getOrden() {
        return orden;
    }

    public void setOrden(SortOrder orden) {
        this.orden = orden;
    }

    public int getStartingAt() {
        return startingAt;
    }

    public void setStartingAt(int startingAt) {
        this.startingAt = startingAt;
    }

    public int getMaxPerPage() {
        return maxPerPage;
    }

    public void setMaxPerPage(int maxPerPage) {
        this.maxPerPage = maxPerPage;
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

    public boolean isHospChiconcuac() {
        return hospChiconcuac;
    }

    public void setHospChiconcuac(boolean hospChiconcuac) {
        this.hospChiconcuac = hospChiconcuac;
    }

    public List<Integer> getListaSubCategorias() {
        return listaSubCategorias;
    }

    public void setListaSubCategorias(List<Integer> listaSubCategorias) {
        this.listaSubCategorias = listaSubCategorias;
    }

    public int getTipoAcumulado() {
        return tipoAcumulado;
    }

    public void setTipoAcumulado(int tipoAcumulado) {
        this.tipoAcumulado = tipoAcumulado;
    }

    public int getTipoInsumo() {
        return tipoInsumo;
    }

    public void setTipoInsumo(int tipoInsumo) {
        this.tipoInsumo = tipoInsumo;
    }

    public String getTipoReceta() {
        return tipoReceta;
    }

    public void setTipoReceta(String tipoReceta) {
        this.tipoReceta = tipoReceta;
    }

    public String getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(String idMedico) {
        this.idMedico = idMedico;
    }

    public String getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(String idPaciente) {
        this.idPaciente = idPaciente;
    }

    public int getValTipoReceta() {
        return valTipoReceta;
    }

    public void setValTipoReceta(int valTipoReceta) {
        this.valTipoReceta = valTipoReceta;
    }

    public int getCantidadCero() {
        return cantidadCero;
    }

    public void setCantidadCero(int cantidadCero) {
        this.cantidadCero = cantidadCero;
    }

    public int getTipoMinistrado() {
        return tipoMinistrado;
    }

    public void setTipoMinistrado(int tipoMinistrado) {
        this.tipoMinistrado = tipoMinistrado;
    }

    public List<Usuario> getListMedicos() {
        return listMedicos;
    }

    public void setListMedicos(List<Usuario> listMedicos) {
        this.listMedicos = listMedicos;
    }

    public String getEstatusCantidadInsumo() {
        return estatusCantidadInsumo;
    }

    public void setEstatusCantidadInsumo(String estatusCantidadInsumo) {
        this.estatusCantidadInsumo = estatusCantidadInsumo;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Estructura getEstructura() {
        return estructura;
    }

    public void setEstructura(Estructura estructura) {
        this.estructura = estructura;
    }

    public EntidadHospitalaria getEntidadHospitalaria() {
        return entidadHospitalaria;
    }

    public void setEntidadHospitalaria(EntidadHospitalaria entidadHospitalaria) {
        this.entidadHospitalaria = entidadHospitalaria;
    }

    public String getIdCapsula() {
        return idCapsula;
    }

    public void setIdCapsula(String idCapsula) {
        this.idCapsula = idCapsula;
    }

    public List<EnvioNeumatico> getListaCapsulas() {
        return listaCapsulas;
    }

    public void setListaCapsulas(List<EnvioNeumatico> listaCapsulas) {
        this.listaCapsulas = listaCapsulas;
    }

    public int getTipoPerfil() {
        return tipoPerfil;
    }

    public void setTipoPerfil(int tipoPerfil) {
        this.tipoPerfil = tipoPerfil;
    }

    public List<String> getListaEstructuras() {
        return listaEstructuras;
    }

    public void setListaEstructuras(List<String> listaEstructuras) {
        this.listaEstructuras = listaEstructuras;
    }

    public List<String> getListaAlmacenes() {
        return listaAlmacenes;
    }

    public void setListaAlmacenes(List<String> listaAlmacenes) {
        this.listaAlmacenes = listaAlmacenes;
    }

    public boolean isPermiteAjusteInventarioGlobal() {
        return permiteAjusteInventarioGlobal;
    }

    public void setPermiteAjusteInventarioGlobal(boolean permiteAjusteInventarioGlobal) {
        this.permiteAjusteInventarioGlobal = permiteAjusteInventarioGlobal;
    }

    public boolean isActivaCamposRepEmisionRecetas() {
        return activaCamposRepEmisionRecetas;
    }

    public void setActivaCamposRepEmisionRecetas(boolean activaCamposRepEmisionRecetas) {
        this.activaCamposRepEmisionRecetas = activaCamposRepEmisionRecetas;
    }

    public boolean isActivaCamposReporteMovimientosGenerales() {
        return activaCamposReporteMovimientosGenerales;
    }

    public void setActivaCamposReporteMovimientosGenerales(boolean activaCamposReporteMovimientosGenerales) {
        this.activaCamposReporteMovimientosGenerales = activaCamposReporteMovimientosGenerales;
    }

    public List<RepInsumoNoMinistrado> getListaInsumo() {
        return listaInsumo;
    }

    public void setListaInsumo(List<RepInsumoNoMinistrado> listaInsumo) {
        this.listaInsumo = listaInsumo;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    public String getFechaConvertInicio() {
        return fechaConvertInicio;
    }

    public void setFechaConvertInicio(String fechaConvertInicio) {
        this.fechaConvertInicio = fechaConvertInicio;
    }

    public String getFechaConvertFin() {
        return fechaConvertFin;
    }

    public void setFechaConvertFin(String fechaConvertFin) {
        this.fechaConvertFin = fechaConvertFin;
    }

    public String getFolioSurtimiento() {
        return folioSurtimiento;
    }

    public void setFolioSurtimiento(String folioSurtimiento) {
        this.folioSurtimiento = folioSurtimiento;
    }

    public String getIdTipoSolucion() {
        return idTipoSolucion;
    }

    public void setIdTipoSolucion(String idTipoSolucion) {
        this.idTipoSolucion = idTipoSolucion;
    }

    public List<String> getListaMedicos() {
        return listaMedicos;
    }

    public void setListaMedicos(List<String> listaMedicos) {
        this.listaMedicos = listaMedicos;
    }

    public List<String> getListaPaciente() {
        return listaPaciente;
    }

    public void setListaPaciente(List<String> listaPaciente) {
        this.listaPaciente = listaPaciente;
    }

    public List<String> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<String> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public List<String> getListaInsumos() {
        return listaInsumos;
    }

    public void setListaInsumos(List<String> listaInsumos) {
        this.listaInsumos = listaInsumos;
    }

    public List<String> getListaDiagostico() {
        return listaDiagostico;
    }

    public void setListaDiagostico(List<String> listaDiagostico) {
        this.listaDiagostico = listaDiagostico;
    }

    public List<Diagnostico> getListDiagosticos() {
        return listDiagosticos;
    }

    public void setListDiagosticos(List<Diagnostico> listDiagosticos) {
        this.listDiagosticos = listDiagosticos;
    }

    public Integer getIdEstatusSolucion() {
        return idEstatusSolucion;
    }

    public void setIdEstatusSolucion(Integer idEstatusSolucion) {
        this.idEstatusSolucion = idEstatusSolucion;
    }

    public String getEstatusSolucion() {
        return estatusSolucion;
    }

    public void setEstatusSolucion(String estatusSolucion) {
        this.estatusSolucion = estatusSolucion;
    }
	
	public Usuario getUsuarioGenera() {
        return usuarioGenera;
    }

    public void setUsuarioGenera(Usuario usuarioGenera) {
        this.usuarioGenera = usuarioGenera;
    }
        
}
