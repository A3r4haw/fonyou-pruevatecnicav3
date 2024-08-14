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
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.AlertaFarmacovigilancia;
import mx.mc.model.Diagnostico;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.HipersensibilidadExtended;
import mx.mc.model.HistoricoSolucion;
import mx.mc.model.InteraccionExtended;
import mx.mc.model.Paciente_Extended;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.ProtocoloExtended;
import mx.mc.model.ReaccionExtend;
import mx.mc.model.SolucionExtended;
import mx.mc.model.SurtimientoInsumo_Extend;
import mx.mc.model.Surtimiento_Extend;
import mx.mc.service.DiagnosticoService;
import mx.mc.service.EntidadHospitalariaService;
import mx.mc.service.HipersensibilidadService;
import mx.mc.service.InteraccionService;
import mx.mc.service.PacienteService;
import mx.mc.service.ProtocoloService;
import mx.mc.service.ReaccionService;
import mx.mc.service.ReporteHistoricoSolucionesService;
import mx.mc.service.ReportesService;
import mx.mc.service.SolucionService;
import mx.mc.service.SurtimientoInsumoService;
import mx.mc.service.SurtimientoMinistradoService;
import mx.mc.service.SurtimientoService;
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
public class ReporteHistoricoSolucionesMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(ReporteHistoricoSolucionesMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private PermisoUsuario permiso;
    
    private String folioPrescripcion;
    private String folioSurtimiento;
    private transient List<HistoricoSolucion> listaHistoricoSoluciones;
    private Paciente_Extended paciente;
    private SolucionExtended solucion;    
    private transient List<Diagnostico> listaDiagnosticos;
    private transient List<ProtocoloExtended> listaProtocolos;
    private transient List<SurtimientoInsumo_Extend> listaSurtimientoInsumos;
    private transient List<AlertaFarmacovigilancia> listaAlertaFarmacovigilancia;
    private transient List<String> listaDiag;
    private transient List<String> listaMedClaves;
    private transient List<String> listaIdMedica;
    private transient List<String> listaMedicamentos;
    private String idPrescripcion;
    private String idSurtimiento;
    
    @Autowired
    private transient ReporteHistoricoSolucionesService reporteHistoricoSolucionesService;
    
    @Autowired
    private transient SurtimientoService surtimientoService;
    
    @Autowired
    private transient PacienteService pacienteService;
    
    @Autowired 
    private transient SolucionService solucionService;
    
    @Autowired
    private transient SurtimientoMinistradoService surtimientoMinistradoService;
    
    @Autowired
    private transient DiagnosticoService diagnosticoService;
    
    @Autowired
    private transient ProtocoloService protocoloService;
    
    @Autowired
    private transient SurtimientoInsumoService surtimientoInsumoService;
    
    @Autowired
    private transient InteraccionService interaccionService;
    
    @Autowired
    private transient ReaccionService reaccionService;
    
    @Autowired
    private transient HipersensibilidadService hipersensibilidadService;
    
    @Autowired
    private transient EntidadHospitalariaService entidadHospitalariaService;
    
    @Autowired
    private transient ReportesService reporteService;
    
    @PostConstruct
    public void init() {
        try {
            initialize();
            permiso = Comunes.obtenerPermisos(Transaccion_Enum.REPORTEHISTSOLUCIONES.getSufijo());
        } catch (Exception e) {
            LOGGER.error("Error en el metodo init :: {}", e.getMessage());
        }
    }
    
    /**
    * Metodo que inicializa todos los atributos de la clase
    */
    public void initialize() {
        try {
            folioPrescripcion = null;
            folioSurtimiento = null;
            listaHistoricoSoluciones = new ArrayList<>();
            paciente = new Paciente_Extended();
            solucion = new SolucionExtended();
            listaDiagnosticos = new ArrayList<>();
            listaProtocolos = new ArrayList<>();
            listaSurtimientoInsumos = new ArrayList<>();
            listaAlertaFarmacovigilancia = new ArrayList<>();
            listaDiag = new ArrayList<>();
            listaIdMedica = new ArrayList<>();
            listaMedClaves = new ArrayList<>();
            listaMedicamentos = new ArrayList<>();
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al inicializar las variables: {}", ex.getMessage());
        }
    }    

    public void consultar() {
        try {
            if (folioPrescripcion == null || folioPrescripcion.trim().isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_INFO, "Ingrese un Folio de Prescripción. ", null);
                return;
            }
            if (folioSurtimiento == null || folioSurtimiento.trim().isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_INFO, "Ingrese un Folio de surtimiento. ", null);
                return;
            }

            listaIdMedica = new ArrayList<>();
            listaMedClaves = new ArrayList<>();
            listaDiag = new ArrayList<>();
            Surtimiento_Extend unSurtimiento = surtimientoService.obtenerSurtimientoByFolioSurtimientoOrFolioPrescripcion(folioSurtimiento, folioPrescripcion);
            
            if(unSurtimiento == null) {
                Mensaje.showMessage(Constantes.MENSAJE_INFO, "No se encontraron datos con los folios ingresados. ", null);
                
            } else {
                if(unSurtimiento.getIdPrescripcion() != null) {
                    idPrescripcion = unSurtimiento.getIdPrescripcion();
                    folioPrescripcion = unSurtimiento.getFolioPrescripcion();
                }
                if(unSurtimiento.getIdSurtimiento() != null) {
                    idSurtimiento = unSurtimiento.getIdSurtimiento();
                    folioSurtimiento = unSurtimiento.getFolio();
                }
                listaHistoricoSoluciones = reporteHistoricoSolucionesService.obtenerHistoricoSoluciones(unSurtimiento.getIdPrescripcion(), unSurtimiento.getIdSurtimiento());
                //Busqueda de información de paciente
                paciente = pacienteService.obtenerPacienteByIdPrescripcion(unSurtimiento.getIdPrescripcion());
                paciente.setEdadPaciente(PacienteUtil.calcularEdad(paciente.getFechaNacimiento()));
                //Busqueda de datos de la solución
                solucion = solucionService.obtenerDatosSolucionByIdSurtimiento(unSurtimiento.getIdSurtimiento());
                if(solucion != null) {
                    paciente.setPesoPaciente(solucion.getPesoPaciente() != null ? solucion.getPesoPaciente() : ' ');
                    paciente.setTallaPaciente(solucion.getTallaPaciente() != null ? solucion.getTallaPaciente() : 0);
                }
                //TODO quitar esta consulta si funciona bien en la anterior donde se busca los datos de solucion Busqueda de surtimientoMinistrado
                /*SurtimientoMinistrado_Extend surtimientoMinistrado = surtimientoMinistradoService.obtenerSurtimientoMinistradoByIdSurtimiento(idSurtimiento);                
                if(surtimientoMinistrado != null) {
                    solucion.setFechaMinistracion(surtimientoMinistrado.getFechaMinistrado());
                    solucion.setMinistro(surtimientoMinistrado.getNombreUsuarioMinistro());
                }*/
                
                //Busqueda de diagnosticos
                listaDiagnosticos = diagnosticoService.obtenerPorIdPacienteIdVisitaIdPrescripcion(paciente.getIdPaciente(), paciente.getIdVisita(), unSurtimiento.getIdPrescripcion());
                List<String> listaDiagnostico = new ArrayList<>();
                for(Diagnostico unDiagnostico : listaDiagnosticos) {
                    listaDiagnostico.add(unDiagnostico.getClave());
                    listaDiag.add(unDiagnostico.getClave());                    
                }                
                boolean mayorCero = true;
                listaSurtimientoInsumos = surtimientoInsumoService.obtenerSurtimientoInsumosByIdSurtimiento(unSurtimiento.getIdSurtimiento(), mayorCero);
                
                List<MedicamentoDTO> listaClavesMedicamento = new ArrayList<>();
                
                for(SurtimientoInsumo_Extend surtimientoInsumo :listaSurtimientoInsumos) {
                    MedicamentoDTO unMedicamento = new MedicamentoDTO();
                    unMedicamento.setClaveMedicamento(surtimientoInsumo.getClaveInstitucional());
                    listaClavesMedicamento.add(unMedicamento);
                    
                    listaIdMedica.add(surtimientoInsumo.getIdInsumo());
                    listaMedClaves.add(surtimientoInsumo.getClaveInstitucional());                    
                    //listaMed.add(surtimientoInsumo.getClaveInstitucional());
                    listaMedicamentos.add(surtimientoInsumo.getIdInsumo());
                }
                
                //Busqueda de protocolos
                listaProtocolos = protocoloService.buscarProtocolosDiagnosticoAndMedicamentos(listaDiagnostico, listaIdMedica);
                listaAlertaFarmacovigilancia = new ArrayList<>();
                //Se buscan las interacciones que se tienen en la lista de medicamentos
                List<InteraccionExtended> listaInteraccion = interaccionService.obtenerInteraccionesClavesMedicamento(listaClavesMedicamento);
                for(InteraccionExtended interaccion : listaInteraccion) {
                    AlertaFarmacovigilancia alertaInteraccion = new AlertaFarmacovigilancia();
                    alertaInteraccion.setFactor1(interaccion.getMedicamento());
                    alertaInteraccion.setFactor2(interaccion.getMedicamentoInteraccion());
                    alertaInteraccion.setTipo(interaccion.getTipoInteraccion());
                    alertaInteraccion.setClasificacion("Interacción Medicamentosa");
                    listaAlertaFarmacovigilancia.add(alertaInteraccion);
                }
                //Se buscan las reacciones RAM medicamento y paciente
                List<ReaccionExtend> listaReacciones = reaccionService.obtenerReaccionesByIdPacienteIdInsumos(paciente.getIdPaciente(), listaMedicamentos);
                for(ReaccionExtend reaccion : listaReacciones) {
                    AlertaFarmacovigilancia alertaRam = new AlertaFarmacovigilancia();
                    alertaRam.setFactor1(reaccion.getNombrePaciente());
                    alertaRam.setFactor2(reaccion.getMedicamento());
                    alertaRam.setTipo(reaccion.getTipo());
                    alertaRam.setClasificacion("RAM");
                    listaAlertaFarmacovigilancia.add(alertaRam);
                }
                //
                List<HipersensibilidadExtended> listaHipersensibilidad  = hipersensibilidadService.obtenerListaReacHiperPorIdPaciente(paciente.getIdPaciente(), listaClavesMedicamento);
                for(HipersensibilidadExtended hipersensibilidad : listaHipersensibilidad) {
                    AlertaFarmacovigilancia alertaHipersensibilidad = new AlertaFarmacovigilancia();
                    alertaHipersensibilidad.setFactor1(hipersensibilidad.getNombrePaciente());
                    alertaHipersensibilidad.setFactor2(hipersensibilidad.getNombreComercial());
                    alertaHipersensibilidad.setTipo(hipersensibilidad.tipoAlergia);
                    alertaHipersensibilidad.setClasificacion("Reacción de Hipersensibilidad");
                    listaAlertaFarmacovigilancia.add(alertaHipersensibilidad);
                }
            }
            
        } catch(Exception ex) {
            LOGGER.error("Ocurrio un error al consultar un folio para obtener historico de soluciones:  " + ex.getMessage());
        }
    }
    
    public void imprimeReporteHistoricoSoluciones() throws Exception {
        LOGGER.debug("mx.mc.magedbean.ReporteHistoricoSoluciones.imprimeReporteHistoricoSoluciones()");
        boolean status = Constantes.INACTIVO;
        try {                       
            consultar();
            EntidadHospitalaria entidad = entidadHospitalariaService.obtenerEntidadHospital();
            
            byte[] buffer = reporteService.imprimirReporteHistoricoSoluciones(idPrescripcion, idSurtimiento, listaDiag, paciente, solucion, entidad,
                                            folioPrescripcion, folioSurtimiento, listaIdMedica, listaMedClaves);
            
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
    
    public void imprimeExcelReporteHistoricoSoluciones() throws Exception {        
        LOGGER.debug("mx.mc.magedbean.ReporteHistoricoSoluciones.imprimeExcelReporteHistoricoSoluciones()");
        boolean status = Constantes.INACTIVO;
        try {                       
            consultar();
            EntidadHospitalaria entidad = entidadHospitalariaService.obtenerEntidadHospital();
            
            boolean resp = reporteService.imprimirExcelReporteHistoricoSoluciones(idPrescripcion, idSurtimiento, listaDiag, paciente, solucion, entidad,
                                            folioPrescripcion, folioSurtimiento, listaIdMedica, listaMedClaves);
            
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

    public String getFolioPrescripcion() {
        return folioPrescripcion;
    }

    public void setFolioPrescripcion(String folioPrescripcion) {
        this.folioPrescripcion = folioPrescripcion;
    }

    public String getFolioSurtimiento() {
        return folioSurtimiento;
    }

    public void setFolioSurtimiento(String folioSurtimiento) {
        this.folioSurtimiento = folioSurtimiento;
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
        
}
