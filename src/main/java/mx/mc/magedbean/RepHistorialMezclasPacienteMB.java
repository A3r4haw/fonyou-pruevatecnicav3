/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import mx.mc.dto.MedicamentoDTO;
import mx.mc.enums.TipoAlmacen_Enum;
import mx.mc.enums.TipoUsuario_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.lazy.RepHistorialMezclasPacienteLazy;
import mx.mc.model.AlertaFarmacovigilancia;
import mx.mc.model.Diagnostico;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.Estructura;
import mx.mc.model.HipersensibilidadExtended;
import mx.mc.model.HistoricoSolucion;
import mx.mc.model.InteraccionExtended;
import mx.mc.model.Paciente_Extended;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.ProtocoloExtended;
import mx.mc.model.ReaccionExtend;
import mx.mc.model.SolucionExtended;
import mx.mc.model.SurtimientoInsumo_Extend;
import mx.mc.model.Surtimiento_Extend;
import mx.mc.model.TipoSolucion;
import mx.mc.model.Usuario;
import mx.mc.service.DiagnosticoService;
import mx.mc.service.EntidadHospitalariaService;
import mx.mc.service.EstructuraService;
import mx.mc.service.HipersensibilidadService;
import mx.mc.service.InteraccionService;
import mx.mc.service.PacienteService;
import mx.mc.service.ProtocoloService;
import mx.mc.service.ReaccionService;
import mx.mc.service.RepHistorialMezclasPacienteService;
import mx.mc.service.ReporteHistoricoSolucionesService;
import mx.mc.service.ReportesService;
import mx.mc.service.SolucionService;
import mx.mc.service.SurtimientoInsumoService;
import mx.mc.service.SurtimientoMinistradoService;
import mx.mc.service.SurtimientoService;
import mx.mc.service.TipoSolucionService;
import mx.mc.service.UsuarioService;
import mx.mc.util.Comunes;
import mx.mc.util.FechaUtil;
import mx.mc.util.Mensaje;
import mx.mc.util.PacienteUtil;
import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author gcruz
 */
@Controller
@Scope(value = "view")
public class RepHistorialMezclasPacienteMB  implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(RepHistorialMezclasPacienteMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private PermisoUsuario permiso;
    private ParamBusquedaReporte paramBusquedaReporte;
    private Date fechaActual;
    private Usuario usuarioSession;
    private boolean administrador;
    private boolean jefeArea;
    private Estructura estructura;
    private String idEstructura;
    private Paciente_Extended pacienteSelected;
    private Usuario medicoSelected;
    private List<Estructura> listAlmacenesSubAlm;
    private List<Estructura> listaAuxiliar;
    private List<TipoSolucion> listTipoSolucion;
    private String idTipoSolucion;
    private RepHistorialMezclasPacienteLazy repHistorialMezclasPacienteLazy;            
    private List<HistoricoSolucion> listaHistoricoSoluciones;
    private Paciente_Extended paciente;
    private SolucionExtended solucion;    
    private List<Diagnostico> listaDiagnosticos;
    private List<ProtocoloExtended> listaProtocolos;
    List<SurtimientoInsumo_Extend> listaSurtimientoInsumos;
    List<AlertaFarmacovigilancia> listaAlertaFarmacovigilancia;
    List<String> listaDiag;
    List<String> listaMedClaves;
    List<String> listaIdMedica;
    List<String> listaMedicamentos;
    //private String idPrescripcion;
    //private String idSurtimiento;
    private boolean verHistorialMezcla;
    private boolean verTablaProtocolo;
    private boolean verTablaDiagnosticos;
    private boolean verEventosAdversos;
    private String idPrescripcion;
    private String idSurtimiento; 
    private String folioPrescripcionHist;
    private String folioSurtimientoHist;
    
    @Autowired
    private EstructuraService estructuraService;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private TipoSolucionService tipoSolucionService;
    
    @Autowired
    private RepHistorialMezclasPacienteService RepHistorialMezclasPacienteService;
    
    @Autowired
    private ReporteHistoricoSolucionesService reporteHistoricoSolucionesService;
    
    @Autowired
    private SurtimientoService surtimientoService;
    
    @Autowired
    private PacienteService pacienteService;
    
    @Autowired 
    SolucionService solucionService;
    
    @Autowired
    SurtimientoMinistradoService surtimientoMinistradoService;
    
    @Autowired
    DiagnosticoService diagnosticoService;
    
    @Autowired
    ProtocoloService protocoloService;
    
    @Autowired
    SurtimientoInsumoService surtimientoInsumoService;
    
    @Autowired
    InteraccionService interaccionService;
    
    @Autowired
    ReaccionService reaccionService;
    
    @Autowired
    HipersensibilidadService hipersensibilidadService;
    
    @Autowired
    EntidadHospitalariaService entidadHospitalariaService;
    
    @Autowired
    ReportesService reporteService;
    
    @PostConstruct
    public void init() {
        try {
            initialize();
            listAlmacenesSubAlm = new ArrayList<>();
            this.administrador = Comunes.isAdministrador();
            this.jefeArea = Comunes.isJefeArea();
            this.usuarioSession = Comunes.obtenerUsuarioSesion();
            paramBusquedaReporte = new ParamBusquedaReporte();        
            paramBusquedaReporte.setFechaInicio(FechaUtil.obtenerFechaInicio());
            paramBusquedaReporte.setFechaFin(FechaUtil.obtenerFechaFin());
            permiso = Comunes.obtenerPermisos(Transaccion_Enum.REPHISTMEZPACIENTES.getSufijo());
            buscarServicio();
            buscaTipoSolucion();
        } catch (Exception e) {
            LOGGER.error("Error en el metodo init :: {}", e.getMessage());
        }
    }
    
    /**
    * Metodo que inicializa todos los atributos de la clase
    */
    public void initialize() {
        try {
            listaHistoricoSoluciones = new ArrayList<>();
            paciente = new Paciente_Extended();
            pacienteSelected = new Paciente_Extended();
            medicoSelected = new Usuario();
            solucion = new SolucionExtended();
            listaDiagnosticos = new ArrayList<>();
            listaProtocolos = new ArrayList<>();
            listaSurtimientoInsumos = new ArrayList<>();
            listaAlertaFarmacovigilancia = new ArrayList<>();
            listaDiag = new ArrayList<>();
            listaIdMedica = new ArrayList<>();
            listaMedClaves = new ArrayList<>();
            listaMedicamentos = new ArrayList<>();
            listAlmacenesSubAlm = new ArrayList<>();
            listTipoSolucion = new ArrayList<>();
            fechaActual = new java.util.Date();
            verHistorialMezcla = Constantes.INACTIVO;
            verTablaProtocolo = Constantes.INACTIVO;
            verEventosAdversos = Constantes.INACTIVO;
            verTablaDiagnosticos = Constantes.INACTIVO;
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al inicializar las variables: {}", ex.getMessage());
        }
    }    

    public void buscarServicio() {
        
        boolean noEncontroRegistro = Constantes.INACTIVO;
        List<Integer> listTipoAlmacen = new ArrayList<>();
        listTipoAlmacen.add(TipoAlmacen_Enum.NO_APLICA.getValue());
        try {
            if (this.administrador) {
                this.listaAuxiliar = estructuraService.getEstructuraByLisTipoAlmacen(listTipoAlmacen);
                this.listAlmacenesSubAlm.add(listaAuxiliar.get(0));
                ordenarListaEstructura(listaAuxiliar.get(0));
            } else if(jefeArea) {
                listAlmacenesSubAlm = estructuraService.obtenerAlmacenesQueSurtenServicio(this.usuarioSession.getIdEstructura());
                if(listAlmacenesSubAlm.isEmpty()) {
                    noEncontroRegistro = Constantes.ACTIVO;
                    this.jefeArea = Constantes.INACTIVO;
                } else {
                    this.estructura = listAlmacenesSubAlm.get(0);
                }
            } else {
                 noEncontroRegistro = Constantes.ACTIVO;                
            }
            if(noEncontroRegistro) {
                this.estructura = estructuraService.obtenerEstructura(this.usuarioSession.getIdEstructura());
            }
        } catch(Exception ex) {
            LOGGER.error("Error al buscar la lista de servicios :: {}", ex.getMessage());
        }
        
    }
    
    public void buscaTipoSolucion() {
        try {
            TipoSolucion tipoSolucion = new TipoSolucion();
            tipoSolucion.setActivo(Constantes.ACTIVOS);
            listTipoSolucion = tipoSolucionService.obtenerLista(tipoSolucion);
        } catch(Exception ex) {
            LOGGER.error("Error al buscar la lista de tipo solución :: {}", ex.getMessage());
        }
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
    
    public List<Usuario> autoCompleteMedicos(String cadena) {
        List<Usuario> listaMedicos = new ArrayList<>();
        try {                            
            listaMedicos = this.usuarioService.obtenerMedicosPorCriteriosBusqueda(cadena, TipoUsuario_Enum.MEDICO.getValue(), Constantes.REGISTROS_PARA_MOSTRAR, null);                        
        } catch (Exception ex) {
            LOGGER.error("Error al obtener los Médicos: {}", ex.getMessage());
        }
        return listaMedicos;
    }
    
    public List<Paciente_Extended> autoCompletePacientes(String cadena) {
        List<Paciente_Extended> listaPacientes = new ArrayList<>();
        try {
            listaPacientes = this.pacienteService.obtenerRegistrosPorCriterioDeBusqueda(
                    cadena.trim(), Constantes.REGISTROS_PARA_MOSTRAR);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener lista de paciente : {}", ex.getMessage());
        }
        return listaPacientes;
    } 
    
    public void consultar() {
        try {           
            verHistorialMezcla = Constantes.INACTIVO;
            verTablaProtocolo = Constantes.INACTIVO;
            verEventosAdversos = Constantes.INACTIVO;
            verTablaDiagnosticos = Constantes.INACTIVO;
            listaIdMedica = new ArrayList<>();
            listaMedClaves = new ArrayList<>();
            listaDiag = new ArrayList<>();
            if(permiso.isPuedeVer()) {
                if(medicoSelected != null && medicoSelected.getIdUsuario() != null) {
                    this.paramBusquedaReporte.setIdMedico(medicoSelected.getIdUsuario());
                } else {
                    this.paramBusquedaReporte.setIdMedico(null);
                }
                if(pacienteSelected != null && pacienteSelected.getIdPaciente() != null) {
                    paramBusquedaReporte.setIdPaciente(pacienteSelected.getIdPaciente());
                    if (administrador || jefeArea) {
                        this.paramBusquedaReporte.setIdEstructura(idEstructura);
                    } else {
                        this.paramBusquedaReporte.setIdEstructura(usuarioSession.getIdEstructura());                        
                    }
                    if(idTipoSolucion != null) {
                        this.paramBusquedaReporte.setIdTipoSolucion(idTipoSolucion);
                    }
                    repHistorialMezclasPacienteLazy =  new RepHistorialMezclasPacienteLazy(RepHistorialMezclasPacienteService, paramBusquedaReporte);
                } else {
                    //Error de campo paciente
                    Mensaje.showMessage("Error", "El campo de paciente es obligatorio", null);//RESOURCES.getString("medicamento.err.autocomplete"), null);
                }                
            } else {
                // Error de permiso para el usuario
                Mensaje.showMessage("Error", "No tienes permiso para realizar esta acción", null);//RESOURCES.getString("medicamento.err.autocomplete"), null);
            }
            
            
        } catch(Exception ex) {
            LOGGER.error("Ocurrio un error al consultar un folio para obtener historico de soluciones:  " + ex.getMessage());
        }
    }
    
    /**
     * Metodo para obtener la fecha fin con el formato 23:59 en lugar de 00:00
     */
    public void obtenerFechaFinal() {
        paramBusquedaReporte.setFechaFin(FechaUtil.obtenerFechaFinal(paramBusquedaReporte.getFechaFin()));
    }
    
    @SuppressWarnings("unchecked")
    public void buscarDetalleOrden(String folioSurtimiento, String folioPrescripcion) {
        try {
            verHistorialMezcla = Constantes.ACTIVO;
            Surtimiento_Extend unSurtimiento = surtimientoService.obtenerSurtimientoByFolioSurtimientoOrFolioPrescripcion(folioSurtimiento, folioPrescripcion);

            if (unSurtimiento != null) {
               /*if (unSurtimiento.getIdPrescripcion() != null) {
                    idPrescripcion = unSurtimiento.getIdPrescripcion();
                    folioPrescripcion = unSurtimiento.getFolioPrescripcion();
                }
                if (unSurtimiento.getIdSurtimiento() != null) {
                    idSurtimiento = unSurtimiento.getIdSurtimiento();
                    folioSurtimiento = unSurtimiento.getFolio();
                }*/
               idPrescripcion = unSurtimiento.getIdPrescripcion();
               folioPrescripcionHist = folioPrescripcion;
               folioSurtimientoHist = folioSurtimiento;
               idSurtimiento = unSurtimiento.getIdSurtimiento();
                listaHistoricoSoluciones = reporteHistoricoSolucionesService.obtenerHistoricoSoluciones(unSurtimiento.getIdPrescripcion(), unSurtimiento.getIdSurtimiento());
                //Busqueda de información de paciente
                paciente = pacienteService.obtenerPacienteByIdPrescripcion(unSurtimiento.getIdPrescripcion());
//                paciente.setEdadPaciente(PacienteUtil.calcularEdad(paciente.getFechaNacimiento()));
                //Busqueda de datos de la solución
                solucion = solucionService.obtenerDatosSolucionByIdSurtimiento(unSurtimiento.getIdSurtimiento());
                

                //TODO quitar esta consulta si funciona bien en la anterior donde se busca los datos de solucion Busqueda de surtimientoMinistrado
                /*SurtimientoMinistrado_Extend surtimientoMinistrado = surtimientoMinistradoService.obtenerSurtimientoMinistradoByIdSurtimiento(idSurtimiento);                
                            if(surtimientoMinistrado != null) {
                                solucion.setFechaMinistracion(surtimientoMinistrado.getFechaMinistrado());
                                solucion.setMinistro(surtimientoMinistrado.getNombreUsuarioMinistro());
                            }*/
                //Busqueda de diagnosticos
                listaDiagnosticos = diagnosticoService.obtenerPorIdPacienteIdVisitaIdPrescripcion(paciente.getIdPaciente(), paciente.getIdVisita(), unSurtimiento.getIdPrescripcion());
                List listaDiagnostico = new ArrayList<>();
                for (Diagnostico unDiagnostico : listaDiagnosticos) {
                    listaDiagnostico.add(unDiagnostico.getClave());
                    listaDiag.add(unDiagnostico.getClave());
                }
                
                if(listaDiagnostico.isEmpty())
                    listaDiagnostico = null;
                boolean mayorCero = true;
                listaSurtimientoInsumos = surtimientoInsumoService.obtenerSurtimientoInsumosByIdSurtimiento(unSurtimiento.getIdSurtimiento(), true);

                List<MedicamentoDTO> listaClavesMedicamento = new ArrayList<>();

                for (SurtimientoInsumo_Extend surtimientoInsumo : listaSurtimientoInsumos) {
                    MedicamentoDTO unMedicamento = new MedicamentoDTO();
                    unMedicamento.setClaveMedicamento(surtimientoInsumo.getClaveInstitucional());
                    listaClavesMedicamento.add(unMedicamento);

                    listaIdMedica.add(surtimientoInsumo.getIdInsumo());
                    listaMedClaves.add(surtimientoInsumo.getClaveInstitucional());
                    //listaMed.add(surtimientoInsumo.getClaveInstitucional());
                    listaMedicamentos.add(surtimientoInsumo.getIdInsumo());
                }
                if(listaIdMedica.isEmpty())
                    listaIdMedica = null;
                //Busqueda de protocolos
                listaProtocolos = protocoloService.buscarProtocolosDiagnosticoAndMedicamentos(listaDiagnostico, listaIdMedica);
                
                if(listaClavesMedicamento.isEmpty())
                    listaClavesMedicamento = null;                
                if(listaClavesMedicamento != null) {
                    //Se buscan las interacciones que se tienen en la lista de medicamentos
                    List<InteraccionExtended> listaInteraccion = interaccionService.obtenerInteraccionesClavesMedicamento(listaClavesMedicamento);
                    for (InteraccionExtended interaccion : listaInteraccion) {
                        AlertaFarmacovigilancia alertaInteraccion = new AlertaFarmacovigilancia();
                        alertaInteraccion.setFactor1(interaccion.getMedicamento());
                        alertaInteraccion.setFactor2(interaccion.getMedicamentoInteraccion());
                        alertaInteraccion.setTipo(interaccion.getTipoInteraccion());
                        alertaInteraccion.setClasificacion("Interacción Medicamentosa");
                        listaAlertaFarmacovigilancia.add(alertaInteraccion);
                    }
                    
                    List<HipersensibilidadExtended> listaHipersensibilidad = hipersensibilidadService.obtenerListaReacHiperPorIdPaciente(paciente.getIdPaciente(), listaClavesMedicamento);
                    for (HipersensibilidadExtended hipersensibilidad : listaHipersensibilidad) {
                        AlertaFarmacovigilancia alertaHipersensibilidad = new AlertaFarmacovigilancia();
                        alertaHipersensibilidad.setFactor1(hipersensibilidad.getNombrePaciente());
                        alertaHipersensibilidad.setFactor2(hipersensibilidad.getNombreComercial());
                        alertaHipersensibilidad.setTipo(hipersensibilidad.tipoAlergia);
                        alertaHipersensibilidad.setClasificacion("Reacción de Hipersensibilidad");
                        listaAlertaFarmacovigilancia.add(alertaHipersensibilidad);
                    }
                }
                
                if(!listaMedicamentos.isEmpty()) {
                    //Se buscan las reacciones RAM medicamento y paciente
                    List<ReaccionExtend> listaReacciones = reaccionService.obtenerReaccionesByIdPacienteIdInsumos(paciente.getIdPaciente(), listaMedicamentos);
                    for (ReaccionExtend reaccion : listaReacciones) {
                        AlertaFarmacovigilancia alertaRam = new AlertaFarmacovigilancia();
                        alertaRam.setFactor1(reaccion.getNombrePaciente());
                        alertaRam.setFactor2(reaccion.getMedicamento());
                        alertaRam.setTipo(reaccion.getTipo());
                        alertaRam.setClasificacion("RAM");
                        listaAlertaFarmacovigilancia.add(alertaRam);
                    }
                }
            }

            if(!listaProtocolos.isEmpty())
                verTablaProtocolo = Constantes.ACTIVO;
            if(!listaDiagnosticos.isEmpty())
                verTablaDiagnosticos = Constantes.ACTIVO;
            if(!listaAlertaFarmacovigilancia.isEmpty())
                verEventosAdversos = Constantes.ACTIVO;
        }catch(Exception ex) {
            LOGGER.error("Ocurrio un error al consultar un folio para obtener historico de soluciones:  " + ex.getMessage());
            }
    }

    public void imprimeReporteHistoricoPaciente() throws Exception {
        LOGGER.debug("mx.mc.magedbean.ReporteHistoricoSoluciones.imprimeReporteHistoricoSoluciones()");
        boolean status = Constantes.INACTIVO;
        try {                       
            //consultar();
            buscarDetalleOrden(folioPrescripcionHist, folioSurtimientoHist);
            EntidadHospitalaria entidad = entidadHospitalariaService.obtenerEntidadHospital();

            byte[] buffer = reporteService.imprimirReporteHistoricoSoluciones(idPrescripcion, idSurtimiento, listaDiag, paciente, solucion, entidad,
                                            folioPrescripcionHist, folioSurtimientoHist, listaIdMedica, listaMedClaves);

            if (buffer != null) {
                status = Constantes.ACTIVO;
                SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "sesionMB");
                sesion.setReportStream(buffer);
                sesion.setReportName(String.format("reporteHistoricoSoluciones_%s.pdf", FechaUtil.formatoFecha(new Date(), "yyyyMMdd")));
            }

        } catch(Exception ex) {
            LOGGER.error("Ocurrio error al imprimir PDF de reporte historico de soluciones   "  +ex.getMessage());
            }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);

    }

    public void imprimeExcelReporteHistoricoPaciente() throws Exception {        
        LOGGER.debug("mx.mc.magedbean.ReporteHistoricoSoluciones.imprimeExcelReporteHistoricoSoluciones()");
        boolean status = Constantes.INACTIVO;
        try {                       
            //consultar();
            EntidadHospitalaria entidad = entidadHospitalariaService.obtenerEntidadHospital();
            
            boolean resp = reporteService.imprimirExcelReporteHistoricoSoluciones(idPrescripcion, idSurtimiento, listaDiag, paciente, solucion, entidad,
                                            folioPrescripcionHist, folioSurtimientoHist, listaIdMedica, listaMedClaves);
            
            if (resp) {
                status = Constantes.ACTIVO;                
            }
            
        } catch(Exception ex) {
            LOGGER.error("Ocurrio error al imprimir PDF de reporte historico de soluciones   "  +ex.getMessage());
            }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
            }
   
    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }    

    public ParamBusquedaReporte getParamBusquedaReporte() {
        return paramBusquedaReporte;
    }

    public void setParamBusquedaReporte(ParamBusquedaReporte paramBusquedaReporte) {
        this.paramBusquedaReporte = paramBusquedaReporte;
    }

    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }

    public boolean isJefeArea() {
        return jefeArea;
    }

    public void setJefeArea(boolean jefeArea) {
        this.jefeArea = jefeArea;
    }

    public Paciente_Extended getPacienteSelected() {
        return pacienteSelected;
    }

    public void setPacienteSelected(Paciente_Extended pacienteSelected) {
        this.pacienteSelected = pacienteSelected;
    }

    public Usuario getMedicoSelected() {
        return medicoSelected;
    }

    public void setMedicoSelected(Usuario medicoSelected) {
        this.medicoSelected = medicoSelected;
    }

    public Estructura getEstructura() {
        return estructura;
    }

    public void setEstructura(Estructura estructura) {
        this.estructura = estructura;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public List<Estructura> getListAlmacenesSubAlm() {
        return listAlmacenesSubAlm;
    }

    public void setListAlmacenesSubAlm(List<Estructura> listAlmacenesSubAlm) {
        this.listAlmacenesSubAlm = listAlmacenesSubAlm;
    }

    public List<TipoSolucion> getListTipoSolucion() {
        return listTipoSolucion;
    }

    public void setListTipoSolucion(List<TipoSolucion> listTipoSolucion) {
        this.listTipoSolucion = listTipoSolucion;
    }

    public String getIdTipoSolucion() {
        return idTipoSolucion;
    }

    public void setIdTipoSolucion(String idTipoSolucion) {
        this.idTipoSolucion = idTipoSolucion;
    }

    public RepHistorialMezclasPacienteLazy getRepHistorialMezclasPacienteLazy() {
        return repHistorialMezclasPacienteLazy;
    }

    public void setRepHistorialMezclasPacienteLazy(RepHistorialMezclasPacienteLazy repHistorialMezclasPacienteLazy) {
        this.repHistorialMezclasPacienteLazy = repHistorialMezclasPacienteLazy;
    }

    public List<HistoricoSolucion> getListaHistoricoSoluciones() {
        return listaHistoricoSoluciones;
    }

    public void setListaHistoricoSoluciones(List<HistoricoSolucion> listaHistoricoSoluciones) {
        this.listaHistoricoSoluciones = listaHistoricoSoluciones;
    }

    public Paciente_Extended getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente_Extended paciente) {
        this.paciente = paciente;
    }

    public SolucionExtended getSolucion() {
        return solucion;
    }

    public void setSolucion(SolucionExtended solucion) {
        this.solucion = solucion;
    }

    public List<Diagnostico> getListaDiagnosticos() {
        return listaDiagnosticos;
    }

    public void setListaDiagnosticos(List<Diagnostico> listaDiagnosticos) {
        this.listaDiagnosticos = listaDiagnosticos;
    }

    public List<String> getListaDiag() {
        return listaDiag;
    }

    public void setListaDiag(List<String> listaDiag) {
        this.listaDiag = listaDiag;
    }
    
    public List<ProtocoloExtended> getListaProtocolos() {
        return listaProtocolos;
    }

    public void setListaProtocolos(List<ProtocoloExtended> listaProtocolos) {
        this.listaProtocolos = listaProtocolos;
    }

    public List<SurtimientoInsumo_Extend> getListaSurtimientoInsumos() {
        return listaSurtimientoInsumos;
    }

    public void setListaSurtimientoInsumos(List<SurtimientoInsumo_Extend> listaSurtimientoInsumos) {
        this.listaSurtimientoInsumos = listaSurtimientoInsumos;
    }

    public List<AlertaFarmacovigilancia> getListaAlertaFarmacovigilancia() {
        return listaAlertaFarmacovigilancia;
    }

    public void setListaAlertaFarmacovigilancia(List<AlertaFarmacovigilancia> listaAlertaFarmacovigilancia) {
        this.listaAlertaFarmacovigilancia = listaAlertaFarmacovigilancia;
    }

    public boolean isVerHistorialMezcla() {
        return verHistorialMezcla;
    }

    public void setVerHistorialMezcla(boolean verHistorialMezcla) {
        this.verHistorialMezcla = verHistorialMezcla;
    }

    public boolean isVerTablaProtocolo() {
        return verTablaProtocolo;
    }

    public void setVerTablaProtocolo(boolean verTablaProtocolo) {
        this.verTablaProtocolo = verTablaProtocolo;
    }

    public boolean isVerTablaDiagnosticos() {
        return verTablaDiagnosticos;
    }

    public void setVerTablaDiagnosticos(boolean verTablaDiagnosticos) {
        this.verTablaDiagnosticos = verTablaDiagnosticos;
    }

    public boolean isVerEventosAdversos() {
        return verEventosAdversos;
    }

    public void setVerEventosAdversos(boolean verEventosAdversos) {
        this.verEventosAdversos = verEventosAdversos;
    }
}
