/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.magedbean;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import mx.mc.enums.Prevencion_Enum;
import mx.mc.enums.PruebaDiagnostica_Enum;
import mx.mc.enums.RiesgoReaccion_Enum;
import mx.mc.enums.TipoDocumento_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.lazy.HipersensibilidadLazy;
import mx.mc.model.Adjunto;
import mx.mc.model.AdjuntoExtended;
import mx.mc.model.Alergeno;
import mx.mc.model.Alteracion;
import mx.mc.model.Diagnostico;
import mx.mc.model.Folios;
import mx.mc.model.HipersensibilidadAdjunto;
import mx.mc.model.HipersensibilidadExtended;
import mx.mc.model.Medicamento;
import mx.mc.model.Paciente_Extended;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.SustanciaActiva;
import mx.mc.model.TipoAlergeno;
import mx.mc.model.TipoHipersensibilidad;
import mx.mc.model.VistaMedicamento;
import mx.mc.service.AdjuntoService;
import mx.mc.service.AlergenoService;
import mx.mc.service.AlteracionService;
import mx.mc.service.DiagnosticoService;
import mx.mc.service.FoliosService;
import mx.mc.service.HipersensibilidadService;
import mx.mc.service.MedicamentoService;
import mx.mc.service.PacienteService;
import mx.mc.service.SustanciaActivaService;
import mx.mc.service.TipoAlergenoService;
import mx.mc.service.TipoHipersensibilidadService;
import mx.mc.util.Comunes;
import mx.mc.util.FechaUtil;
import mx.mc.util.Mensaje;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.primefaces.model.UploadedFile;
import org.primefaces.shaded.commons.io.IOUtils;

/**
 *
 * @author gcruz
 */
@Controller
@Scope(value = "view")
public class HipersensibilidadMB implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(HipersensibilidadMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    
    private PermisoUsuario permiso;
    private SesionMB sesion;
    private boolean isAdmin;
    private boolean isJefeArea;
    private String cadenaBusqueda;
    private Date fechaActual;
    private boolean editar;
    
    private HipersensibilidadLazy hipersensibilidadLazy;
    private boolean status;
    private List<HipersensibilidadExtended> hipersensibilidadExtended;
    
    private List<TipoHipersensibilidad> listaTipoHipersensibilidades;
    private List<Alteracion> listaAlteraciones;
    private List<TipoAlergeno> listaTipoAlergenos;
    private List<Alergeno> listaAlergenos;
    private List<String> listaPruebas;
    
    private TipoHipersensibilidad tipoHipersensibilidad;
    private Alteracion alteracion;
    private TipoAlergeno tipoAlergeno;
    private Alergeno alergeno;
    private Paciente_Extended pacienteSelected;
    private VistaMedicamento medicamento;
    private Medicamento medicamentoSelect;
    private List<VistaMedicamento> medicamentoList;
    private List<Medicamento> listaMedicamento;
    
    private Integer idTipoHipersensibilidad;
    private Integer  idAlteracion;
    private Integer  idTipoAlergeno;
    private Integer  idAlergeno;    
    
    private List<SustanciaActiva> listaSustanciasActivas;
    private List<Diagnostico> listaDiagnosticos;
    
    private HipersensibilidadExtended hipersensibilidadSelect;
    
    Folios folioHipersensibilidad;
    
    private List<String> selectPrevenciones;
    private List<String> prevencionesSelect;
    
    private UploadedFile file;
    //private List<UploadedFile> listaFiles;
    private List<AdjuntoExtended> listaAdjuntos;
    private Adjunto adjuntoSelect;
    private List<AdjuntoExtended> listaAdjuntosGuardada;
    
    private String nombreRegistra;
    private DefaultStreamedContent archivoDescarga;
    private List<String> riesgosReaccion;
            
    @Autowired
    private transient HipersensibilidadService hipersensibilidadService;
    
    @Autowired
    TipoHipersensibilidadService tipoHipersensibilidadService;
    
    @Autowired
    AlteracionService alteracionService;
    
    @Autowired
    TipoAlergenoService tipoAlergenoService;
    
    @Autowired
    AlergenoService alergenoService;
    
    @Autowired
    PacienteService pacienteService;
    
    @Autowired
    MedicamentoService medicamentoService;
    
    @Autowired
    DiagnosticoService diagnosticoService;
    
    @Autowired
    FoliosService foliosService;
    
    @Autowired
    SustanciaActivaService sustanciaActivaService;
    
    @Autowired
    AdjuntoService adjuntoService;
    
    /**
     * Consulta los permisos del usuario
     */
    @PostConstruct
    public void init() {
        permiso = Comunes.obtenerPermisos(Transaccion_Enum.REACCHIPERSENSIBILIDAD.getSufijo());
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        initialize();
        validarUsuarioAdministrador();
        buscarReaccionesHipersensibilidades();
        buscarListaTiposDeHipersensibilidad();
        buscarListaAlteraciones();
        buscarListaTiposDeAlergenos();
        buscarListaAlergenos();
        riesgosReaccion.add(RiesgoReaccion_Enum.ALTO.getValue());
        riesgosReaccion.add(RiesgoReaccion_Enum.MEDIO.getValue());
        riesgosReaccion.add(RiesgoReaccion_Enum.BAJO.getValue());
    }
    
    private void initialize() {
        isAdmin = Constantes.INACTIVO;
        isJefeArea = Constantes.INACTIVO;
        status = false;
        fechaActual = FechaUtil.obtenerFechaInicio();
        editar = false;
        listaPruebas = new ArrayList<>();
        selectPrevenciones = new ArrayList<>();
        prevencionesSelect = new ArrayList<>();
        prevencionesSelect.add(Prevencion_Enum.NOTOMARFARMACO.getNombrePrevencion());
        prevencionesSelect.add(Prevencion_Enum.FILTROSPARTICULA.getNombrePrevencion());
        prevencionesSelect.add(Prevencion_Enum.NOCOMERALIMENTO.getNombrePrevencion());
        prevencionesSelect.add(Prevencion_Enum.MUDARSEUNAZONA.getNombrePrevencion());
        prevencionesSelect.add(Prevencion_Enum.ELIMINARELEMENTOS.getNombrePrevencion());
        prevencionesSelect.add(Prevencion_Enum.CUBRIRCOLCHONESALMOHADAS.getNombrePrevencion());
        prevencionesSelect.add(Prevencion_Enum.UTILIZARALMOHADAS.getNombrePrevencion());
        prevencionesSelect.add(Prevencion_Enum.LAVARROPA.getNombrePrevencion());
        prevencionesSelect.add(Prevencion_Enum.LIMPIARCASA.getNombrePrevencion());
        prevencionesSelect.add(Prevencion_Enum.APARATOSAIREACONDISIONADO.getNombrePrevencion());
        prevencionesSelect.add(Prevencion_Enum.APLICARVAPORCALIENTE.getNombrePrevencion());
        prevencionesSelect.add(Prevencion_Enum.EXTERMINARCUCARACHAS.getNombrePrevencion());
        riesgosReaccion = new ArrayList<>();
    }
    
    public void validarUsuarioAdministrador() {
        try {
            
            this.isAdmin = sesion.isAdministrador();
            this.isJefeArea = sesion.isJefeArea();
            
        } catch (Exception e) {
            LOGGER.error("Error en el metodo validarUsuarioAdministrador: ", e.getMessage());
        }
    }
    
    public void buscarReaccionesHipersensibilidades() {
        try {            
            if (!this.permiso.isPuedeVer()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.accion"), "");
                return;
            }
            if (cadenaBusqueda != null
                    && cadenaBusqueda.trim().isEmpty()) {
                cadenaBusqueda = null;
            }
            hipersensibilidadLazy = new HipersensibilidadLazy(hipersensibilidadService, cadenaBusqueda);
            
            cadenaBusqueda = null;
        } catch(Exception ex) {
            LOGGER.error("Ocurrio un error al buscar las reacciones de hipersensibilidad:  " + ex.getMessage());
        }    
    }
    
    public void buscarListaTiposDeHipersensibilidad() {
        try {
            listaTipoHipersensibilidades = tipoHipersensibilidadService.obtenerListaTiposHipersensibilidad();
        } catch(Exception ex) {
            LOGGER.error("Error al momento de buscar tipos de hipersensibilidad:  " + ex.getMessage());
        }
    }
    
    public void buscarListaAlteraciones() {
        try {
            listaAlteraciones = alteracionService.obtenerListaAlteraciones();
        } catch(Exception ex) {
            LOGGER.error("Error al buscar lista de alteraciones:  " + ex.getMessage());
        }
    }
    
    public void buscarListaTiposDeAlergenos() {
        try {
            listaTipoAlergenos = tipoAlergenoService.obtenerListaTiposAlergeno();
        } catch(Exception ex) {
            LOGGER.error("Error al buscar lista de tipos de Alergenos:  " + ex.getMessage());
        }
    }
    
    public void buscarListaAlergenos() {
        try {
            listaAlergenos = alergenoService.obtenerListaAlergenos();
        } catch(Exception ex) {
            LOGGER.error("Error al buscar lista de Alergenos:  " + ex.getMessage());
        }
    }    
    
    public List<Paciente_Extended> autoCompletePaciente(String cadena) {
        List<Paciente_Extended> pacienteList;
        try {
            pacienteList = pacienteService.searchPacienteAutoComplete(cadena.trim());
        } catch (Exception ex) {
            pacienteList = new ArrayList<>();
            LOGGER.error("Error al obtener Pacientes: {}", ex.getMessage());
        }
        return pacienteList;
    }
    
    public List<VistaMedicamento> autoComplete(String cadena) {
        try {           
            medicamentoList = medicamentoService.buscarSalMedicamento(cadena);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener Medicamentos autocomplete: ", ex.getMessage());
        }
        return medicamentoList;
    }
    
    public void selectSustancia(SelectEvent e) {
        medicamento = (VistaMedicamento) e.getObject();
        try {
            medicamentoSelect = medicamentoService.obtenerMedicamento(medicamento.getIdMedicamento());
        } catch (Exception ex) {
            LOGGER.error("Error al buscar el medicamento, despues de seleccionar la sustancia Activa:  " + ex.getMessage());
        }
        hipersensibilidadSelect.setNombreComercial(medicamento.getNombreCorto());
        
    }
    public void handleSelect(SelectEvent e) {
        medicamento = (VistaMedicamento) e.getObject();        
    }

    public void handleUnSelect(UnselectEvent e) {
        medicamento = (VistaMedicamento) e.getObject();
    }
    
    public List<Medicamento> autoCompleteMedicamento(String cadena) {
        try {
            listaMedicamento = medicamentoService.obtenerAutoCompNombreClave(cadena);
        } catch (Exception ex) {
            listaMedicamento = new ArrayList<>();
            LOGGER.error("Error al obtener Medicamentos: {}", ex.getMessage());
        }

        return listaMedicamento;
    }
    
    public void selectMedicamento(SelectEvent e) {
        medicamentoSelect = (Medicamento) e.getObject();
        try {
            medicamento = medicamentoService.buscarMedicamentoPorId(medicamentoSelect.getIdMedicamento());
        } catch (Exception ex) {
            LOGGER.error("Error al buscar la sustancia, despues de seleccionar el medicamento:  " + ex.getMessage());
        }
    }
    
    /**
     * Consulta Diagnosticos por cadena de texto mayor a 3 caracteres y retorna
     * las coincidencias
     *
     * @param cadena
     * @return
     */
    public List<Diagnostico> autocompleteDiagnostico(String cadena) {
        LOGGER.debug("mx.mc.magedbean.HipersensibilidadMB.autocompleteDiagnostico()");
        List<Diagnostico> diagnList = new ArrayList<>();

        try {
            diagnList.addAll(diagnosticoService.obtenerListaAutoComplete(cadena));

        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString("prc.dia.lista"), ex);
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.dia.lista"), null);
        }
        return diagnList;
    }
    
    public void nuevaReaccionHipersensiblidad() {
        try {
            hipersensibilidadSelect = new HipersensibilidadExtended();
            pacienteSelected = null;
            medicamento = null;
            medicamentoSelect = null;
            listaDiagnosticos = null;
            idTipoHipersensibilidad = null;
            idAlteracion = null;
            idTipoAlergeno = null;
            idAlergeno = null;
            listaPruebas = new ArrayList<>(); 
            selectPrevenciones = new ArrayList<>();
            editar = false;
            hipersensibilidadSelect.setFecha(new Date());
            //Se busca el folio de documento y se genera el folio de reaacion para setear el valor de folio en objeto
            int tipoDoc = TipoDocumento_Enum.REACCIONHIPERSENSIBILIDAD.getValue();            
            folioHipersensibilidad = foliosService.obtenerPrefixPorDocument(tipoDoc);
            String folio = Comunes.generaFolio(folioHipersensibilidad);
            //Setear valores a objeto folio para actualizar folio
            folioHipersensibilidad.setSecuencia(Comunes.separaFolio(folio));
            folioHipersensibilidad.setUpdateFecha(new Date());
            folioHipersensibilidad.setUpdateIdUsuario(sesion.getUsuarioSelected().getIdUsuario());
            //Setear valor de folio en objeto de vista
            hipersensibilidadSelect.setFolio(folio);
            //listaFiles = new ArrayList<>();            
            listaAdjuntos = new ArrayList<>();
            nombreRegistra = sesion.getUsuarioSelected().getNombre() +" " + sesion.getUsuarioSelected().getApellidoPaterno() +
                             " " + sesion.getUsuarioSelected().getApellidoMaterno();
        } catch(Exception ex) {
            LOGGER.error("Error al intentar generar la nueva reacción de hipersensibilidad:  " + ex.getMessage());
        }
    }
    
    public void limpiarCampos() {
        hipersensibilidadSelect = new HipersensibilidadExtended();
        pacienteSelected = null;
        medicamento = null;
        medicamentoSelect = null;
        listaDiagnosticos = null;
        idTipoHipersensibilidad = null;
        idAlteracion = null;
        idTipoAlergeno = null;
        idAlergeno = null;
        listaPruebas = new ArrayList<>();
        selectPrevenciones = new ArrayList<>();
        nombreRegistra = sesion.getUsuarioSelected().getNombre() +" " + sesion.getUsuarioSelected().getApellidoPaterno() +
                             " " + sesion.getUsuarioSelected().getApellidoMaterno();
    }
    
    public void obtenerReaccionHipersensibilidad(String idHipersensibilidad) {
        try {
            editar = true;
            limpiarCampos();
            
            if(permiso.isPuedeEditar()) {
                hipersensibilidadSelect = hipersensibilidadService.obtenerHipersensibilidadPorIdHiper(idHipersensibilidad);
                if(hipersensibilidadSelect != null) {
                    if(hipersensibilidadSelect.getEfectos() == null) { hipersensibilidadSelect.setEfectos(""); }
                    if(hipersensibilidadSelect.getManifestaciones() == null) { hipersensibilidadSelect.setManifestaciones(""); }
                    if(hipersensibilidadSelect.getIdMedicamento() != null) {
                        medicamento = medicamentoService.buscarMedicamentoPorId(hipersensibilidadSelect.getIdMedicamento());
                        medicamentoSelect = medicamentoService.obtenerPorIdMedicamento(hipersensibilidadSelect.getIdMedicamento());
                    }
                    
                }
            }
            idTipoHipersensibilidad = hipersensibilidadSelect.getIdTipoHipersensibilidad();
            idAlteracion = hipersensibilidadSelect.getIdAlteracion();
            idTipoAlergeno = hipersensibilidadSelect.getIdTipoAlergeno();
            idAlergeno = hipersensibilidadSelect.getIdAlergeno();
            
            if(hipersensibilidadSelect.getIdPaciente() != null) {
                pacienteSelected = pacienteService.obtenerPacienteCompletoPorId(hipersensibilidadSelect.getIdPaciente());
            }
            
            String[] sintomas = hipersensibilidadSelect.getSintomas().split("; ");
            if(sintomas.length > 0) {
                listaDiagnosticos = new ArrayList<>();
                for(int i=0; i < sintomas.length; i++) {
                    if(sintomas[i].toString() != "") {
                        Diagnostico unDiagnostico = diagnosticoService.obtenerDiagnosticoPorNombre(sintomas[i].toString());
                        if(unDiagnostico != null) {
                            listaDiagnosticos.add(unDiagnostico);
                        }
                    }
                    
                }
                
            }
            
            if(hipersensibilidadSelect.getEvaluacionMedica() == 1) {                
                listaPruebas.add(PruebaDiagnostica_Enum.EVALUACIONMEDICA.getNombrePrueba());
            }
            if(hipersensibilidadSelect.getAnalisisSangre() == 1) {
                listaPruebas.add(PruebaDiagnostica_Enum.ANALISISSANGRE.getNombrePrueba());
            }
            if(hipersensibilidadSelect.getPruebasCutaneas() == 1) {
                listaPruebas.add(PruebaDiagnostica_Enum.PRUEBACUTANEA.getNombrePrueba());
            }
            if(hipersensibilidadSelect.getPruebaLGE() == 1) {
                listaPruebas.add(PruebaDiagnostica_Enum.PRUEBALGE.getNombrePrueba());
            }

            if(hipersensibilidadSelect.getNoTomarFarmaco() == 1) {
                selectPrevenciones.add(Prevencion_Enum.NOTOMARFARMACO.getNombrePrevencion());
            }
            if(hipersensibilidadSelect.getFiltrosParticula() == 1) {
                selectPrevenciones.add(Prevencion_Enum.FILTROSPARTICULA.getNombrePrevencion());
            }
            if(hipersensibilidadSelect.getNoComeralimento() == 1) {
                selectPrevenciones.add(Prevencion_Enum.NOCOMERALIMENTO.getNombrePrevencion());
            }
            if(hipersensibilidadSelect.getMudarseUnaZona() == 1) {
                selectPrevenciones.add(Prevencion_Enum.MUDARSEUNAZONA.getNombrePrevencion());
            }
            if(hipersensibilidadSelect.getEliminarElementos() == 1) {
                selectPrevenciones.add(Prevencion_Enum.ELIMINARELEMENTOS.getNombrePrevencion());
            }
            if(hipersensibilidadSelect.getCubrirColchonesAlmohadas() == 1) {
                selectPrevenciones.add(Prevencion_Enum.CUBRIRCOLCHONESALMOHADAS.getNombrePrevencion());
            }
            if(hipersensibilidadSelect.getUtilizarAlmohadas() == 1) {
                selectPrevenciones.add(Prevencion_Enum.UTILIZARALMOHADAS.getNombrePrevencion());
            }
            if(hipersensibilidadSelect.getLavarFrecuentementeRopa() == 1) {
                selectPrevenciones.add(Prevencion_Enum.LAVARROPA.getNombrePrevencion());
            }
            if(hipersensibilidadSelect.getLimpiarCasaAMenudo() == 1) {
                selectPrevenciones.add(Prevencion_Enum.LIMPIARCASA.getNombrePrevencion());
            }
            if(hipersensibilidadSelect.getAparatosAireAcondisionado() == 1) {
                selectPrevenciones.add(Prevencion_Enum.APARATOSAIREACONDISIONADO.getNombrePrevencion());
            }
            if(hipersensibilidadSelect.getAplicarVaporCaliente() == 1) {
                selectPrevenciones.add(Prevencion_Enum.APLICARVAPORCALIENTE.getNombrePrevencion());
            }
            if(hipersensibilidadSelect.getExterminarCucarachas() == 1) {
                selectPrevenciones.add(Prevencion_Enum.EXTERMINARCUCARACHAS.getNombrePrevencion());
            }
            
            this.listaAdjuntos = adjuntoService.obtenerAdjuntosByIdHipersensibilidad(idHipersensibilidad);
            // se realiza nuevamente la busqueda para saber si al editar se agregan mas adjuntos
            this.listaAdjuntosGuardada = adjuntoService.obtenerAdjuntosByIdHipersensibilidad(idHipersensibilidad);
        } catch(Exception ex) {
            LOGGER.error("Error al momento de intentar buscar información de la reacción de hipersensibilidad a editar:  " + ex.getMessage());
        }
    }
    
    public void deleteReaccionHipersensibilidad(String idHipersensibilidad) {
        try {
            boolean resp = hipersensibilidadService.eliminarHipersensibilidad(idHipersensibilidad);
            if(resp) {
                Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("hipersensibilidad.info.eliminar"), null);
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("hipersensibilidad.error.eliminar"), null);
            }
        } catch(Exception ex) {
            LOGGER.error("Error al intentar eliminar la reacción de hipersensibilidad" + ex.getMessage());
        }
    }
    
    public void deleteAdjunto(Integer idAdjunto) {
        try {
            boolean resp = adjuntoService.eliminarArchivo(idAdjunto);
            if(resp) {
                for(AdjuntoExtended unAdjunto :listaAdjuntos) {
                    if(unAdjunto.getIdAdjunto().equals(idAdjunto)) {
                        listaAdjuntos.remove(unAdjunto);
                        Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("hipersensibilidad.info.eliminaAdjunto"), null);
                        break;
                    }
                }
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("hipersensibilidad.error.eliminaAdjunto"), null);
            }
        } catch(Exception ex) {
            LOGGER.error("Error al intentar eliminar el archivo adjunto   " + ex.getMessage());
        }
    }
    
    public void crearReaccionHipersensibilidad() {
        try {
            String valida = "";
            if(permiso.isPuedeCrear()) {
                valida = validarDatosFormulario();
                if(valida.equals("")) {
                    hipersensibilidadSelect.setIdTipoHipersensibilidad(idTipoHipersensibilidad);
                    hipersensibilidadSelect.setIdAlteracion(idAlteracion);
                    hipersensibilidadSelect.setIdTipoAlergeno(idTipoAlergeno);
                    hipersensibilidadSelect.setIdAlergeno(idAlergeno);
                    
                    // Se valida que no sea null la busqueda del medicamento
                    if(medicamento != null) {
                        // Se valida que no sea null el nombre de la sustancia
                        if(medicamento.getSustanciaActiva() != null) {
                            //Se busca la sustancia por nombre para setear el valor de idSustanciaActiva
                            SustanciaActiva unaSustancia = sustanciaActivaService.obtenerSustanciaPorNombre(medicamento.getSustanciaActiva());
                            if(unaSustancia != null) {
                                hipersensibilidadSelect.setIdSustanciaActiva(unaSustancia.getIdSustanciaActiva());
                            }
                        }                
                    }
                    // Se valda que no sea null el objeto de medicamento
                    if(medicamentoSelect != null) {
                        Medicamento unMedicamento = medicamentoService.obtenerMedicamento(medicamentoSelect.getIdMedicamento());
                        //Se valida que no sea null el nombre corto del medicamento
                        if(unMedicamento.getNombreCorto() != null) {
                             hipersensibilidadSelect.setNombreComercial(unMedicamento.getNombreCorto());
                        }
                    }
                    //Valida si se busco un paciente
                    if(pacienteSelected != null) {
                        hipersensibilidadSelect.setIdPaciente(pacienteSelected.getIdPaciente());
                    }

                    String cadenaDiagnosticos = "";
                    if(listaDiagnosticos != null) {
                        if(!listaDiagnosticos.isEmpty()) {
                            for(Diagnostico unDiagnostico: listaDiagnosticos) {
                                //Diagnostico unDiagnostico = diagnosticoService.obtenerDiagnosticoPorIdDiag(listaDiagnosticos.get(i).toString());
                                if(cadenaDiagnosticos.isEmpty()) {
                                    cadenaDiagnosticos = unDiagnostico.getNombre();
                                } else {
                                    cadenaDiagnosticos = cadenaDiagnosticos + "; " + unDiagnostico.getNombre();
                                }                    
                            }
                        }                        
                    }
                    hipersensibilidadSelect.setSintomas(cadenaDiagnosticos);
                    //Se setean todos los valores a cero para cuando es una edición cambie a el valor de 1
                    hipersensibilidadSelect.setEvaluacionMedica(Constantes.ESTATUS_INACTIVO);
                    hipersensibilidadSelect.setAnalisisSangre(Constantes.ESTATUS_INACTIVO);
                    hipersensibilidadSelect.setPruebasCutaneas(Constantes.ESTATUS_INACTIVO);
                    hipersensibilidadSelect.setPruebaLGE(Constantes.ESTATUS_INACTIVO);
                    if(!listaPruebas.isEmpty()) {
                        for(String prueba : listaPruebas) {
                            if(prueba.equalsIgnoreCase(PruebaDiagnostica_Enum.EVALUACIONMEDICA.getNombrePrueba())) {
                                hipersensibilidadSelect.setEvaluacionMedica(Constantes.ESTATUS_ACTIVO);
                            } 
                            if(prueba.equalsIgnoreCase(PruebaDiagnostica_Enum.ANALISISSANGRE.getNombrePrueba())) {
                                hipersensibilidadSelect.setAnalisisSangre(Constantes.ESTATUS_ACTIVO);
                            } 
                            if(prueba.equalsIgnoreCase(PruebaDiagnostica_Enum.PRUEBACUTANEA.getNombrePrueba())) {
                                hipersensibilidadSelect.setPruebasCutaneas(Constantes.ESTATUS_ACTIVO);
                            } 
                            if(prueba.equalsIgnoreCase(PruebaDiagnostica_Enum.PRUEBALGE.getNombrePrueba())) {
                                hipersensibilidadSelect.setPruebaLGE(Constantes.ESTATUS_ACTIVO);                                
                            } 
                        }
                    }
                    
                    hipersensibilidadSelect.setNoTomarFarmaco(Constantes.ESTATUS_INACTIVO);
                    hipersensibilidadSelect.setFiltrosParticula(Constantes.ESTATUS_INACTIVO);
                    hipersensibilidadSelect.setNoComeralimento(Constantes.ESTATUS_INACTIVO);
                    hipersensibilidadSelect.setMudarseUnaZona(Constantes.ESTATUS_INACTIVO);
                    hipersensibilidadSelect.setEliminarElementos(Constantes.ESTATUS_INACTIVO);
                    hipersensibilidadSelect.setCubrirColchonesAlmohadas(Constantes.ESTATUS_INACTIVO);
                    hipersensibilidadSelect.setUtilizarAlmohadas(Constantes.ESTATUS_INACTIVO);
                    hipersensibilidadSelect.setLavarFrecuentementeRopa(Constantes.ESTATUS_INACTIVO);
                    hipersensibilidadSelect.setLimpiarCasaAMenudo(Constantes.ESTATUS_INACTIVO);
                    hipersensibilidadSelect.setAparatosAireAcondisionado(Constantes.ESTATUS_INACTIVO);
                    hipersensibilidadSelect.setAplicarVaporCaliente(Constantes.ESTATUS_INACTIVO);
                    hipersensibilidadSelect.setExterminarCucarachas(Constantes.ESTATUS_INACTIVO);
                    
                    if(!selectPrevenciones.isEmpty()) {
                       for( String prevencion : selectPrevenciones) {
                           if(prevencion.equalsIgnoreCase(Prevencion_Enum.NOTOMARFARMACO.getNombrePrevencion())) {
                               hipersensibilidadSelect.setNoTomarFarmaco(Constantes.ESTATUS_ACTIVO);
                           }
                           if(prevencion.equalsIgnoreCase(Prevencion_Enum.FILTROSPARTICULA.getNombrePrevencion())) {
                               hipersensibilidadSelect.setFiltrosParticula(Constantes.ESTATUS_ACTIVO);
                           }
                           if(prevencion.equalsIgnoreCase(Prevencion_Enum.NOCOMERALIMENTO.getNombrePrevencion())) {
                               hipersensibilidadSelect.setNoComeralimento(Constantes.ESTATUS_ACTIVO);
                           }
                           if(prevencion.equalsIgnoreCase(Prevencion_Enum.MUDARSEUNAZONA.getNombrePrevencion())) {
                               hipersensibilidadSelect.setMudarseUnaZona(Constantes.ESTATUS_ACTIVO);

                           }
                           if(prevencion.equalsIgnoreCase(Prevencion_Enum.ELIMINARELEMENTOS.getNombrePrevencion())) {
                               hipersensibilidadSelect.setEliminarElementos(Constantes.ESTATUS_ACTIVO);                               
                           }
                           if(prevencion.equalsIgnoreCase(Prevencion_Enum.CUBRIRCOLCHONESALMOHADAS.getNombrePrevencion())) {
                               hipersensibilidadSelect.setCubrirColchonesAlmohadas(Constantes.ESTATUS_ACTIVO);

                           }
                           if(prevencion.equalsIgnoreCase(Prevencion_Enum.UTILIZARALMOHADAS.getNombrePrevencion())) {
                               hipersensibilidadSelect.setUtilizarAlmohadas(Constantes.ESTATUS_ACTIVO);
                           }
                           if(prevencion.equalsIgnoreCase(Prevencion_Enum.LAVARROPA.getNombrePrevencion())) {
                               hipersensibilidadSelect.setLavarFrecuentementeRopa(Constantes.ESTATUS_ACTIVO);
                           }
                           if(prevencion.equalsIgnoreCase(Prevencion_Enum.LIMPIARCASA.getNombrePrevencion())) {
                               hipersensibilidadSelect.setLimpiarCasaAMenudo(Constantes.ESTATUS_ACTIVO);
                           }
                           if(prevencion.equalsIgnoreCase(Prevencion_Enum.APARATOSAIREACONDISIONADO.getNombrePrevencion())) {
                               hipersensibilidadSelect.setAparatosAireAcondisionado(Constantes.ESTATUS_ACTIVO);
                           }
                           if(prevencion.equalsIgnoreCase(Prevencion_Enum.APLICARVAPORCALIENTE.getNombrePrevencion())) {
                               hipersensibilidadSelect.setAplicarVaporCaliente(Constantes.ESTATUS_ACTIVO);                               
                           }
                           if(prevencion.equalsIgnoreCase(Prevencion_Enum.EXTERMINARCUCARACHAS.getNombrePrevencion())) {
                               hipersensibilidadSelect.setExterminarCucarachas(Constantes.ESTATUS_ACTIVO);
                           }
                       } 
                    }
                    
                    Integer idAdjunto = adjuntoService.obtenerSiguienteAdjunto();
                    if(idAdjunto == null) {
                        idAdjunto = 1;
                    }
                    if(!editar) {
                        String idHipersensibilidad = Comunes.getUUID();
                        hipersensibilidadSelect.setIdHipersensibilidad(idHipersensibilidad);
                        hipersensibilidadSelect.setInsertFecha(new Date());
                        hipersensibilidadSelect.setInsertIdUsuario(sesion.getUsuarioSelected().getIdUsuario());
                        
                        //List<Adjunto> listAdjuntos = new ArrayList<>();
                        List<HipersensibilidadAdjunto> listaHiperAdju = new ArrayList<>();                        
                        if(!listaAdjuntos.isEmpty()) {
                            for(AdjuntoExtended unAdjunto : listaAdjuntos) {
                                //Se termina de llena objeto adjunto
                                unAdjunto.setIdAdjunto(idAdjunto);                                
                                unAdjunto.setInsertIdUsuario(sesion.getUsuarioSelected().getIdUsuario());
                                                                
                                //Se llena objeto de tabla intermedia
                                HipersensibilidadAdjunto hiperAdjunto = new HipersensibilidadAdjunto();
                                hiperAdjunto.setIdAdjunto(idAdjunto);
                                hiperAdjunto.setIdHipersensibilidad(idHipersensibilidad);
                                //Se agrega a la lista
                                listaHiperAdju.add(hiperAdjunto);
                                //Se aumenta el idAdjunto
                                idAdjunto+= 1;
                            }
                        }
                        
                        boolean resp = hipersensibilidadService.registrarReaccionHipersensibilidad(folioHipersensibilidad, hipersensibilidadSelect,
                                            listaAdjuntos, listaHiperAdju);
                        if(resp) {
                            Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("hipersensibilidad.info.guardar"), null);
                            status = Constantes.ACTIVO;
                        } else {
                            status = Constantes.INACTIVO;
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("hipersensibilidad.error.guardar"), null);
                        }
                    } else {                                              
                        hipersensibilidadSelect.setUpdateFecha(new Date());
                        hipersensibilidadSelect.setUpdateIdUsuario(sesion.getUsuarioSelected().getIdUsuario());
                        List<AdjuntoExtended> listaNueva = new ArrayList<>();
                        List<HipersensibilidadAdjunto> listaHiperAdjunto = new ArrayList<>();
                        if(!listaAdjuntos.isEmpty()) {
                            for(AdjuntoExtended unAdjunto : listaAdjuntos) {
                                boolean existe = false;
                                for(AdjuntoExtended adjuntoGuardado : listaAdjuntosGuardada) {
                                    if(unAdjunto.equals(adjuntoGuardado)) {
                                        existe = true;
                                    }
                                }
                                if(!existe) {
                                    //Se llenan datos de adjunto
                                    unAdjunto.setIdAdjunto(idAdjunto);
                                    unAdjunto.setInsertIdUsuario(sesion.getUsuarioSelected().getIdUsuario());
                                    //Se agrega a lista
                                    listaNueva.add(unAdjunto);
                                    //Se llena objeto de tabla intermedia y se agrega a la lista
                                    HipersensibilidadAdjunto hiperAdjunto = new HipersensibilidadAdjunto();
                                    hiperAdjunto.setIdAdjunto(unAdjunto.getIdAdjunto());
                                    hiperAdjunto.setIdHipersensibilidad(hipersensibilidadSelect.getIdHipersensibilidad());
                                    listaHiperAdjunto.add(hiperAdjunto);
                                     idAdjunto+= 1;
                                }
                            }
                        }
                        
                        boolean actualiza = hipersensibilidadService.actualizarReaccionHipersensibilidad(hipersensibilidadSelect, 
                                                                        listaNueva, listaHiperAdjunto);
                        
                        if(actualiza) {
                            Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("hipersensibilidad.info.actualizar"), null);
                            status = Constantes.ACTIVO;
                        } else {
                            status = Constantes.INACTIVO;
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("hipersensibilidad.error.actualizar"), null);
                        }
                    }                                                           
                   
                }
                buscarReaccionesHipersensibilidades();
            }
            editar = false;
            
        } catch(Exception ex) {
            LOGGER.error("Error al intentar guardar una hipersensibilidad: " + ex.getMessage());
            status = Constantes.INACTIVO;
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public String validarDatosFormulario() {
        String valida = "";
        if(idTipoHipersensibilidad == null) {
            return RESOURCES.getString("hipersensibilidad.error.tipoHipersensibilidad");
        } 
        if(idAlteracion == null) {
            return RESOURCES.getString("hipersensibilidad.error.alteracion");
        }
        if(idTipoAlergeno == null) {
            return RESOURCES.getString("hipersensibilidad.error.tipoAlergeno");            
        }
        if(idAlergeno == null) {
            return RESOURCES.getString("hipersensibilidad.error.alergeno");
        }
        
        return valida;
    }
    
    public void fileUpload(FileUploadEvent event) {

        try {            
            this.file = event.getFile();
            String name = file.getFileName();
            //String ext = name.substring(name.lastIndexOf('.'), name.length());            
            //listaFiles.add(file);
            AdjuntoExtended adjunto = new AdjuntoExtended();
            adjunto.setNombreAdjunto(name);
            adjunto.setInsertFecha(new Date());
            adjunto.setAdjunto(IOUtils.toByteArray(file.getInputstream()));
            adjunto.setNombreRegistro(nombreRegistra);
            listaAdjuntos.add(adjunto);
            LOGGER.info("Nombre archivo:    " + file.getFileName());            
        } catch(IOException ex) {
            LOGGER.error("Error al adjuntar archivos  " + ex.getMessage());
        }
    }
    
    public void descargarArchivo(Integer idAdjunto) {
        try {
            Adjunto adjunto = adjuntoService.obtenerAdjuntoByIdAdjunto(idAdjunto);
            if (adjunto != null) {
                byte[] buffer = adjunto.getAdjunto();                                
               
                InputStream stream = new ByteArrayInputStream(buffer);                
                
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                String tipoExtension[] = adjunto.getNombreAdjunto().split("\\.");
                String contentType = "";
                contentType = "application/"+tipoExtension[1];                 
                //    contentType = "image/"+tipoExtension[1];                 
                
                setArchivoDescarga(new DefaultStreamedContent(stream, contentType, adjunto.getNombreAdjunto()));
                
            }
        } catch(Exception ex) {
            LOGGER.error("Error al intentar descargar el archivo   " + ex.getMessage());
        }
       // PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, true);
    }
    
    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean isIsJefeArea() {
        return isJefeArea;
    }

    public void setIsJefeArea(boolean isJefeArea) {
        this.isJefeArea = isJefeArea;
    }

    public String getCadenaBusqueda() {
        return cadenaBusqueda;
    }

    public void setCadenaBusqueda(String cadenaBusqueda) {
        this.cadenaBusqueda = cadenaBusqueda;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public boolean isEditar() {
        return editar;
    }

    public void setEditar(boolean editar) {
        this.editar = editar;
    }

    public HipersensibilidadLazy getHipersensibilidadLazy() {
        return hipersensibilidadLazy;
    }

    public void setHipersensibilidadLazy(HipersensibilidadLazy hipersensibilidadLazy) {
        this.hipersensibilidadLazy = hipersensibilidadLazy;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<HipersensibilidadExtended> getHipersensibilidadExtended() {
        return hipersensibilidadExtended;
    }

    public void setHipersensibilidadExtended(List<HipersensibilidadExtended> hipersensibilidadExtended) {
        this.hipersensibilidadExtended = hipersensibilidadExtended;
    }

    public List<TipoHipersensibilidad> getListaTipoHipersensibilidades() {
        return listaTipoHipersensibilidades;
    }

    public void setListaTipoHipersensibilidades(List<TipoHipersensibilidad> listaTipoHipersensibilidades) {
        this.listaTipoHipersensibilidades = listaTipoHipersensibilidades;
    }

    public List<Alteracion> getListaAlteraciones() {
        return listaAlteraciones;
    }

    public void setListaAlteraciones(List<Alteracion> listaAlteraciones) {
        this.listaAlteraciones = listaAlteraciones;
    }

    public List<TipoAlergeno> getListaTipoAlergenos() {
        return listaTipoAlergenos;
    }

    public void setListaTipoAlergenos(List<TipoAlergeno> listaTipoAlergenos) {
        this.listaTipoAlergenos = listaTipoAlergenos;
    }

    public List<Alergeno> getListaAlergenos() {
        return listaAlergenos;
    }

    public void setListaAlergenos(List<Alergeno> listaAlergenos) {
        this.listaAlergenos = listaAlergenos;
    }

    public TipoHipersensibilidad getTipoHipersensibilidad() {
        return tipoHipersensibilidad;
    }

    public void setTipoHipersensibilidad(TipoHipersensibilidad tipoHipersensibilidad) {
        this.tipoHipersensibilidad = tipoHipersensibilidad;
    }

    public Alteracion getAlteracion() {
        return alteracion;
    }

    public void setAlteracion(Alteracion alteracion) {
        this.alteracion = alteracion;
    }

    public TipoAlergeno getTipoAlergeno() {
        return tipoAlergeno;
    }

    public void setTipoAlergeno(TipoAlergeno tipoAlergeno) {
        this.tipoAlergeno = tipoAlergeno;
    }

    public Alergeno getAlergeno() {
        return alergeno;
    }

    public void setAlergeno(Alergeno alergeno) {
        this.alergeno = alergeno;
    }

    public Paciente_Extended getPacienteSelected() {
        return pacienteSelected;
    }

    public void setPacienteSelected(Paciente_Extended pacienteSelected) {
        this.pacienteSelected = pacienteSelected;
    }

    public VistaMedicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(VistaMedicamento medicamento) {
        this.medicamento = medicamento;
    }

    public Medicamento getMedicamentoSelect() {
        return medicamentoSelect;
    }

    public void setMedicamentoSelect(Medicamento medicamentoSelect) {
        this.medicamentoSelect = medicamentoSelect;
    }

    public Integer getIdTipoHipersensibilidad() {
        return idTipoHipersensibilidad;
    }

    public void setIdTipoHipersensibilidad(Integer idTipoHipersensibilidad) {
        this.idTipoHipersensibilidad = idTipoHipersensibilidad;
    }

    public Integer getIdAlteracion() {
        return idAlteracion;
    }

    public void setIdAlteracion(Integer idAlteracion) {
        this.idAlteracion = idAlteracion;
    }

    public Integer getIdTipoAlergeno() {
        return idTipoAlergeno;
    }

    public void setIdTipoAlergeno(Integer idTipoAlergeno) {
        this.idTipoAlergeno = idTipoAlergeno;
    }

    public Integer getIdAlergeno() {
        return idAlergeno;
    }

    public void setIdAlergeno(Integer idAlergeno) {
        this.idAlergeno = idAlergeno;
    }

    public List<SustanciaActiva> getListaSustanciasActivas() {
        return listaSustanciasActivas;
    }

    public void setListaSustanciasActivas(List<SustanciaActiva> listaSustanciasActivas) {
        this.listaSustanciasActivas = listaSustanciasActivas;
    }

    public List<Diagnostico> getListaDiagnosticos() {
        return listaDiagnosticos;
    }

    public void setListaDiagnosticos(List<Diagnostico> listaDiagnosticos) {
        this.listaDiagnosticos = listaDiagnosticos;
    }

    public HipersensibilidadExtended getHipersensibilidadSelect() {
        return hipersensibilidadSelect;
    }

    public void setHipersensibilidadSelect(HipersensibilidadExtended hipersensibilidadSelect) {
        this.hipersensibilidadSelect = hipersensibilidadSelect;
    }

    public List<String> getListaPruebas() {
        return listaPruebas;
    }

    public void setListaPruebas(List<String> listaPruebas) {
        this.listaPruebas = listaPruebas;
    }

    public List<String> getSelectPrevenciones() {
        return selectPrevenciones;
    }

    public void setSelectPrevenciones(List<String> selectPrevenciones) {
        this.selectPrevenciones = selectPrevenciones;
    }   

    public List<String> getPrevencionesSelect() {
        return prevencionesSelect;
    }

    public void setPrevencionesSelect(List<String> prevencionesSelect) {
        this.prevencionesSelect = prevencionesSelect;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }   

    public List<AdjuntoExtended> getListaAdjuntos() {
        return listaAdjuntos;
    }

    public void setListaAdjuntos(List<AdjuntoExtended> listaAdjuntos) {
        this.listaAdjuntos = listaAdjuntos;
    }

    public Adjunto getAdjuntoSelect() {
        return adjuntoSelect;
    }

    public void setAdjuntoSelect(Adjunto adjuntoSelect) {
        this.adjuntoSelect = adjuntoSelect;
    }

    public DefaultStreamedContent getArchivoDescarga() {
        return archivoDescarga;
    }

    public void setArchivoDescarga(DefaultStreamedContent archivoDescarga) {
        this.archivoDescarga = archivoDescarga;
    }

    public List<String> getRiesgosReaccion() {
        return riesgosReaccion;
    }

    public void setRiesgosReaccion(List<String> riesgosReaccion) {
        this.riesgosReaccion = riesgosReaccion;
    }
        
}
