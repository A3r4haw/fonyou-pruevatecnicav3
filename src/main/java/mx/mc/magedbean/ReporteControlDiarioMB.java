package mx.mc.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import mx.mc.enums.Accion_Enum;
import mx.mc.enums.TipoAlmacen_Enum;
import mx.mc.enums.TipoDocumento_Enum;
import mx.mc.enums.TipoMedicamento_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.lazy.ReporteControlDiarioLazy;
import mx.mc.model.ConfirmacionReporteFoliado;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.Estructura;
import mx.mc.model.Folios;
import mx.mc.model.Medicamento;
import mx.mc.model.ParamLibMedControlados;
import mx.mc.model.ReporteEstatusInsumo;
import mx.mc.model.TransaccionPermisos;
import mx.mc.model.Usuario;
import mx.mc.service.ConfirmacionReporteFoliadoService;
import mx.mc.service.EntidadHospitalariaService;
import mx.mc.service.EstructuraService;
import mx.mc.service.FoliosService;
import mx.mc.service.MedicamentoService;
import mx.mc.service.ReporteMovimientosService;
import mx.mc.service.ReportesService;
import mx.mc.util.Comunes;
import mx.mc.util.FechaUtil;
import mx.mc.util.Mensaje;
import org.primefaces.PrimeFaces;

/**
 *
 * @author mcalderon
 *
 */
@Controller
@Scope(value = "view")
public class ReporteControlDiarioMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(ReporteControlDiarioMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    private boolean puedeCrear;
    private boolean puedeVer;
    private boolean puedeEditar;
    private boolean puedeEliminar;
    private boolean puedeProcesar;
    private boolean puedeAutorizar;

    private List<ReporteEstatusInsumo> listEstatusInsumo;
    private ParamLibMedControlados paramLibMedControlados;

    private Date fechaActualInicio;
    private Date fechaActual;
    private Usuario usuarioSession;
    private boolean administrador;
    private boolean jefeArea;
    private String pathPdf;

    private List<Estructura> listAlmacenesSubAlm;
    private List<Estructura> listaAuxiliar;
    private String idEstructura;
    private Estructura estructuraUsuario;
    private String refrigeracion;
    private String clave5000;
    private String valor_refri_5000;
    private String clave4000;
    private String valor_refri_4000;
    private String controlado;
    private String valueControlDiario;
    private Folios folioConfirmado;
    private ConfirmacionReporteFoliado confirmacionReporteFoliado;
    private String nombreServicio;
    private String tipoMedicamento;    
    private Map<String, Integer> tiposMedicamento = new HashMap<String, Integer>();
    private boolean mostrarCombo;

    @Autowired
    private transient EstructuraService estructuraService;

    @Autowired
    private transient ReporteMovimientosService reporteMovimientosService;

    @Autowired
    private transient MedicamentoService medicamentoService;

    @Autowired
    private transient ReportesService reportesService;

    @Autowired
    private transient FoliosService foliosService;    
    
    @Autowired
    private ConfirmacionReporteFoliadoService confirmacionReporteFoliadoService;
    
    @Autowired
    private EntidadHospitalariaService entidadHospitalariaService;
    
    private ReporteControlDiarioLazy reporteControlDiarioLazy;

    /**
     * Metodo que se ejecuta despues de cargar la pantalla de pacientes
     */
    @PostConstruct
    public void init() {
        try {
            mostrarCombo = false;
            listAlmacenesSubAlm = new ArrayList<>();
            this.administrador = Comunes.isAdministrador();
            jefeArea = Comunes.isJefeArea();
            if(administrador || jefeArea) {
                mostrarCombo = true;
            }
            this.usuarioSession = Comunes.obtenerUsuarioSesion();
            initialize();

            obtienePermisos();
            tiposMedicamento = new HashMap<>();
            tiposMedicamento.put(TipoMedicamento_Enum.REFRIGERACION.getNombre(), TipoMedicamento_Enum.REFRIGERACION.getSufijo());
            tiposMedicamento.put(TipoMedicamento_Enum.CLAVES5000.getNombre(), TipoMedicamento_Enum.CLAVES5000.getSufijo());
            tiposMedicamento.put(TipoMedicamento_Enum.CLAVES4000.getNombre(), TipoMedicamento_Enum.CLAVES4000.getSufijo());
            tiposMedicamento.put(TipoMedicamento_Enum.CONTROLADO.getNombre(), TipoMedicamento_Enum.CONTROLADO.getSufijo());
            
        } catch (Exception e) {
            LOGGER.error("Error en el metodo init :: {}", e.getMessage());
        }
    }

    /**
     * Metodo que inicializa todos los atributos de la clase
     */
    public void initialize() {
        try {
            fechaActualInicio = FechaUtil.getFechaActual();
            fechaActual = new java.util.Date();
            List<Integer> listTipoAlmacen = new ArrayList<>();

            listTipoAlmacen.add(TipoAlmacen_Enum.ALMACEN.getValue());
            listTipoAlmacen.add(TipoAlmacen_Enum.SUBALMACEN.getValue());
            if (this.administrador) {
                this.listaAuxiliar = estructuraService.getEstructuraByLisTipoAlmacen(listTipoAlmacen);
            } else if (jefeArea) {
                //Si es jefe de area se muestra su servicio y servicios hijos
                this.listaAuxiliar = estructuraService.getEstructurDugthersYMe(this.usuarioSession.getIdEstructura());
            } else {
                this.estructuraUsuario = estructuraService.obtenerEstructura(this.usuarioSession.getIdEstructura());
                nombreServicio = estructuraUsuario.getNombre();
                idEstructura = estructuraUsuario.getIdEstructura();
            }

        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener la lista de tipos de Movimiento. {}", ex.getMessage());
        }
        paramLibMedControlados = new ParamLibMedControlados();
        paramLibMedControlados.setFechaInicio(this.fechaActualInicio);
        paramLibMedControlados.setFechaFin(this.fechaActual);
    }

    public void ordenarListaEstructura(Estructura estrucPrincipal) {
        try {
            if (estrucPrincipal != null) {
                for (int i = 1; i < listaAuxiliar.size(); i++) {
                    if (listaAuxiliar.get(i).getIdEstructuraPadre() != null
                            && listaAuxiliar.get(i).getIdEstructuraPadre().toLowerCase().contains(estrucPrincipal.getIdEstructura().toLowerCase())) {
                        listAlmacenesSubAlm.add(listaAuxiliar.get(i));
                        ordenarListaEstructura(listaAuxiliar.get(i));
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al ordenar la lista :: {}", ex.getMessage());
        }
    }

    /**
     * Metodo para realizar la consulta desde la vista
     *
     */
    public void consultar() {
        try {
            paramLibMedControlados.setNuevaBusqueda(true);
            paramLibMedControlados.setValueControlDiario(tipoMedicamento);
            
            if (!administrador) {
                this.paramLibMedControlados.setIdEstructura(usuarioSession.getIdEstructura());
            } else {
                this.paramLibMedControlados.setIdEstructura(idEstructura);
            }

            reporteControlDiarioLazy = new ReporteControlDiarioLazy(this.reporteMovimientosService, this.paramLibMedControlados);

            LOGGER.debug("Resultados: {}", reporteControlDiarioLazy.getTotalReg());

        } catch (Exception e1) {
            LOGGER.error("Error al consultar", e1);
        }
    }

    /**
     * Metodo utilizado para obtener los permisos de el usuario en session
     */
    private void obtienePermisos() {
        Usuario usuario = Comunes.obtenerUsuarioSesion();
        if (usuario != null) {
            for (TransaccionPermisos item : usuario.getPermisosList()) {
                if (item.getCodigo().equals(Transaccion_Enum.REPORTREFRIGE5000.getSufijo())) {
                    switch (item.getAccion()) {
                        case "CREAR":
                            puedeCrear = item.getAccion().equals(Accion_Enum.CREAR.getValue());
                            break;
                        case "VER":
                            puedeVer = item.getAccion().equals(Accion_Enum.VER.getValue());
                            break;
                        case "EDITAR":
                            puedeEditar = item.getAccion().equals(Accion_Enum.EDITAR.getValue());
                            break;
                        case "ELIMINAR":
                            puedeEliminar = item.getAccion().equals(Accion_Enum.ELIMINAR.getValue());
                            break;
                        case "PROCESAR":
                            puedeProcesar = item.getAccion().equals(Accion_Enum.PROCESAR.getValue());
                            break;
                        case "AUTORIZAR":
                            puedeAutorizar = item.getAccion().equals(Accion_Enum.AUTORIZAR.getValue());
                            break;
                        default:
                            puedeVer = true;
                            break;
                    }
                }
            }
        }
    }

    /**
     * Consulta de insumos por cadena de texto mayor a 3 caracteres y retorna
     * las coincidencias
     *
     * @param query
     * @return
     */
    public List<Medicamento> autocompleteInsumo(String query) {
        LOGGER.debug("mx.mc.magedbean.ReporteMovimientosGralesMB.autocompleteInsumo()");
        List<Medicamento> insumosList = new ArrayList<>();
        try {
            insumosList.addAll(medicamentoService.obtenerInsumos(query));

        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al consultar el medicamento");
            Mensaje.showMessage("Error", RESOURCES.getString("medicamento.err.autocomplete"),null);
        }
        return insumosList;
    }

    public void generarFolioReporteControlado() throws Exception {
        LOGGER.debug("Generar folio de Reporte de Control Diario mx.mc.magedbeanReporteControlDiarioMB");
        boolean status1 = Constantes.INACTIVO;
        try {
            int tipoDoc = TipoDocumento_Enum.REPORTECONTROLDIARIO.getValue();            
            Folios folio = foliosService.obtenerPrefixPorDocument(tipoDoc);
            //reabasto.setFolio(Comunes.GeneraFolio(folio));
            String folioReporte = Comunes.generaFolio(folio);
            //Agregamos los datos para actualizar el Folio
            folio.setSecuencia(Comunes.separaFolio(folioReporte));
            folio.setUpdateFecha(new Date());
            folio.setUpdateIdUsuario(usuarioSession.getIdUsuario());                                    
            
            confirmacionReporteFoliado = new ConfirmacionReporteFoliado();
            confirmacionReporteFoliado.setIdConfirmacionReporteFoliado(Comunes.getUUID());
            confirmacionReporteFoliado.setFolio(folioReporte);
            confirmacionReporteFoliado.setFecha(new Date());
            if(tipoMedicamento != null) {
                confirmacionReporteFoliado.setTipo(tipoMedicamento);
            }            
            
            confirmacionReporteFoliado.setIdTurno(1);
            if(idEstructura != null) {
                confirmacionReporteFoliado.setIdEstructura(idEstructura);
            } else {
                confirmacionReporteFoliado.setIdEstructura(usuarioSession.getIdEstructura());
            }
            
            confirmacionReporteFoliado.setInsertIdUsuario(usuarioSession.getIdUsuario());
            
            if (administrador) {
                Estructura servicio = estructuraService.obtenerEstructura(idEstructura);
                if(servicio != null) {
                    nombreServicio = servicio.getNombre();
                }
            }
            if(tipoMedicamento != null) {
                switch(tipoMedicamento) {
                    case "1":
                        confirmacionReporteFoliado.setTipo(TipoMedicamento_Enum.REFRIGERACION.getNombre());                        
                        break;
                    case "2":
                        confirmacionReporteFoliado.setTipo(TipoMedicamento_Enum.CLAVES5000.getNombre());                        
                        break;
                    case "3":
                        confirmacionReporteFoliado.setTipo(TipoMedicamento_Enum.CLAVES4000.getNombre());                        
                        break;
                    case "4":
                        confirmacionReporteFoliado.setTipo(TipoMedicamento_Enum.CONTROLADO.getNombre());                        
                        break;    
                    default:    
                }
            } else {
                confirmacionReporteFoliado.setTipo(Constantes.REPORTEcONTROLDIARIO_TODOS_MEDICAMENTOS);
            }    
            folioConfirmado = folio;
            status1 = true;         
            
        } catch(Exception ex) {
            LOGGER.error("Error al generar la generarFolioReporteControlado: {}", ex.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam("estatus1", status1);
    }
    
    public void registrarFolioReporteControlDiario() throws Exception {
        LOGGER.debug("mx.mc.magedbean.Reporte.registrarFolioReporteControlDiario()");
        
        try {
            boolean actualizaFolio = confirmacionReporteFoliadoService.registrarFolioReporteDiario(folioConfirmado, confirmacionReporteFoliado);
            if(actualizaFolio) {
                imprimeReporteControlDiario();
            }
        } catch(Exception ex) {
            LOGGER.error("Error al generar la registrarFolioReporteControlDiario: {}", ex.getMessage());
        }
    }
    
    public void imprimeReporteControlDiario() throws Exception {
        LOGGER.debug("mx.mc.magedbean.Reporte.imprimeReporteControlDiario()");
        boolean status = Constantes.INACTIVO;
        try {
            //paramLibMedControlados.setValorRefri5000(valor_refri_5000);
            Estructura est;
                    
            if (!administrador) {
                this.paramLibMedControlados.setIdEstructura(usuarioSession.getIdEstructura());
                est = estructuraService.obtenerEstructura(usuarioSession.getIdEstructura());
            } else {
                this.paramLibMedControlados.setIdEstructura(idEstructura);
                est = estructuraService.obtenerEstructura(idEstructura);
            }
            EntidadHospitalaria entidad;
            entidad = entidadHospitalariaService.obtenerEntidadById(est.getIdEntidadHospitalaria());
            paramLibMedControlados.setNombreEntidad(entidad.getNombre());
            paramLibMedControlados.setDomicilio(entidad.getDomicilio());
            paramLibMedControlados.setUsuarioGeneraReporte(usuarioSession.getNombre() + " " + 
                    usuarioSession.getApellidoPaterno() + " " + usuarioSession.getApellidoMaterno());
            paramLibMedControlados.setClaveUsuarioGenReporte(usuarioSession.getMatriculaPersonal());
            paramLibMedControlados.setFolio(confirmacionReporteFoliado.getFolio());
            byte[] buffer = reportesService.imprimeReporteControlDiario(paramLibMedControlados);
            if (buffer != null) {
                status = Constantes.ACTIVO;
                SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "sesionMB");
                sesion.setReportStream(buffer);
                sesion.setReportName(String.format("ReporteControlDiario_%s.pdf", FechaUtil.formatoFecha(new Date(), "yyyyMMdd")));
            }
    } catch (Exception e) {
            LOGGER.error("Error al generar la Impresión: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam("estatus", status);
    }

    public boolean isPuedeCrear() {
        return puedeCrear;
    }

    public void setPuedeCrear(boolean puedeCrear) {
        this.puedeCrear = puedeCrear;
    }

    public boolean isPuedeVer() {
        return puedeVer;
    }

    public void setPuedeVer(boolean puedeVer) {
        this.puedeVer = puedeVer;
    }

    public boolean isPuedeEditar() {
        return puedeEditar;
    }

    public void setPuedeEditar(boolean puedeEditar) {
        this.puedeEditar = puedeEditar;
    }

    public boolean isPuedeEliminar() {
        return puedeEliminar;
    }

    public void setPuedeEliminar(boolean puedeEliminar) {
        this.puedeEliminar = puedeEliminar;
    }

    public boolean isPuedeProcesar() {
        return puedeProcesar;
    }

    public void setPuedeProcesar(boolean puedeProcesar) {
        this.puedeProcesar = puedeProcesar;
    }

    public boolean isPuedeAutorizar() {
        return puedeAutorizar;
    }

    public void setPuedeAutorizar(boolean puedeAutorizar) {
        this.puedeAutorizar = puedeAutorizar;
    }

    public ParamLibMedControlados getParamLibMedControlados() {
        return paramLibMedControlados;
    }

    public void setParamLibMedControlados(ParamLibMedControlados paramLibMedControlados) {
        this.paramLibMedControlados = paramLibMedControlados;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public Usuario getUsuarioSession() {
        return usuarioSession;
    }

    public void setUsuarioSession(Usuario usuarioSession) {
        this.usuarioSession = usuarioSession;
    }

    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }

    public String getPathPdf() {
        return pathPdf;
    }

    public void setPathPdf(String pathPdf) {
        this.pathPdf = pathPdf;
    }

    public List<Estructura> getListAlmacenesSubAlm() {
        return listAlmacenesSubAlm;
    }

    public void setListAlmacenesSubAlm(List<Estructura> listAlmacenesSubAlm) {
        this.listAlmacenesSubAlm = listAlmacenesSubAlm;
    }

    public List<Estructura> getListaAuxiliar() {
        return listaAuxiliar;
    }

    public void setListaAuxiliar(List<Estructura> listaAuxiliar) {
        this.listaAuxiliar = listaAuxiliar;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public Estructura getEstructuraUsuario() {
        return estructuraUsuario;
    }

    public void setEstructuraUsuario(Estructura estructuraUsuario) {
        this.estructuraUsuario = estructuraUsuario;
    }

    public Date getFechaActualInicio() {
        return fechaActualInicio;
    }

    public void setFechaActualInicio(Date fechaActualInicio) {
        this.fechaActualInicio = fechaActualInicio;
    }

    public ReporteControlDiarioLazy getReporteControlDiarioLazy() {
        return reporteControlDiarioLazy;
    }

    public void setReporteControlDiarioLazy(ReporteControlDiarioLazy reporteControlDiarioLazy) {
        this.reporteControlDiarioLazy = reporteControlDiarioLazy;
    }

    public List<ReporteEstatusInsumo> getListEstatusInsumo() {
        return listEstatusInsumo;
    }

    public void setListEstatusInsumo(List<ReporteEstatusInsumo> listEstatusInsumo) {
        this.listEstatusInsumo = listEstatusInsumo;
    }

    public String getRefrigeracion() {
        return refrigeracion;
    }

    public void setRefrigeracion(String refrigeracion) {
        this.refrigeracion = refrigeracion;
    }

    public String getClave5000() {
        return clave5000;
    }

    public void setClave5000(String clave5000) {
        this.clave5000 = clave5000;
    }

    public String getValorRefri5000() {
        return valor_refri_5000;
    }

    public void setValorRefri5000(String valorRefri5000) {
        this.valor_refri_5000 = valorRefri5000;
    }



    public String getClave4000() {
        return clave4000;
    }

    public void setClave4000(String clave4000) {
        this.clave4000 = clave4000;
    }

    public String getValorRefri4000() {
        return valor_refri_4000;
    }

    public void setValorRefri4000(String valorRefri4000) {
        this.valor_refri_4000 = valorRefri4000;
    }

    public String getControlado() {
        return controlado;
    }

    public void setControlado(String controlado) {
        this.controlado = controlado;
    }

    public String getValueControlDiario() {
        return valueControlDiario;
    }

    public void setValueControlDiario(String valueControlDiario) {
        this.valueControlDiario = valueControlDiario;
    }

    public ConfirmacionReporteFoliado getConfirmacionReporteFoliado() {
        return confirmacionReporteFoliado;
    }

    public void setConfirmacionReporteFoliado(ConfirmacionReporteFoliado confirmacionReporteFoliado) {
        this.confirmacionReporteFoliado = confirmacionReporteFoliado;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    public String getTipoMedicamento() {
        return tipoMedicamento;
    }

    public void setTipoMedicamento(String tipoMedicamento) {
        this.tipoMedicamento = tipoMedicamento;
    }

    public Map<String, Integer> getTiposMedicamento() {
        return tiposMedicamento;
    }

    public void setTiposMedicamento(Map<String, Integer> tiposMedicamento) {
        this.tiposMedicamento = tiposMedicamento;
    }

    public boolean isJefeArea() {
        return jefeArea;
    }

    public void setJefeArea(boolean jefeArea) {
        this.jefeArea = jefeArea;
    }

    public boolean isMostrarCombo() {
        return mostrarCombo;
    }

    public void setMostrarCombo(boolean mostrarCombo) {
        this.mostrarCombo = mostrarCombo;
    }
    
}
