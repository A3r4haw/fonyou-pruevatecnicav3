package mx.mc.magedbean;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Response;
import mx.mc.dto.MedicamentoDTO;
import mx.mc.dto.ParamBusquedaAlertaDTO;
import mx.mc.dto.RespuestaAlertasDTO;
import mx.mc.dto.ValidacionNoCumplidaDTO;
import mx.mc.enums.EstatusMinistracion_Enum;
import mx.mc.enums.EstatusPrescripcion_Enum;
import mx.mc.enums.EstatusSurtimiento_Enum;
import mx.mc.enums.TipoMotivo_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.AlertaFarmacovigilancia;
import mx.mc.model.Config;
import mx.mc.model.Diagnostico;
import mx.mc.model.Estructura;
import mx.mc.model.Medicamento_Extended;
import mx.mc.model.MovimientoInventario;
import mx.mc.model.Paciente_Extended;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.PrescripcionInsumo_Extended;
import mx.mc.model.Surtimiento;
import mx.mc.model.SurtimientoMinistrado;
import mx.mc.model.SurtimientoMinistrado_Extend;
import mx.mc.model.Surtimiento_Extend;
import mx.mc.model.TipoCancelacion;
import mx.mc.model.Usuario;
import mx.mc.service.ConfigService;
import mx.mc.service.DiagnosticoService;
import mx.mc.service.EstructuraService;
import mx.mc.service.MedicamentoService;
import mx.mc.service.PacienteService;
import mx.mc.service.SurtimientoMinistradoService;
import mx.mc.service.SurtimientoService;
import mx.mc.service.TipoCancelacionService;
import mx.mc.util.Comunes;
import mx.mc.util.FechaUtil;
import mx.mc.util.Mensaje;
import mx.mc.ws.farmacovigilancia.service.ReaccionesAdversas;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * @author AORTIZ
 */
@Controller
@Scope(value = "view")
public class MinistrarMedicamentoMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(MinistrarMedicamentoMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    private String textoBusqueda;
    private List<Paciente_Extended> listaPacientes;
    private List<Medicamento_Extended> listaMedicamentos;
    private List<Medicamento_Extended> detalleSolucion;
    private Paciente_Extended pacienteSelect;
    private Usuario usuarioSession;
    private Integer ministraPrevDays = 0;
    private Integer ministraLaterHours = 0;
    private int cantMinistrar;
    private boolean eliminarCodigo;
    private List<Config> configList;
    private boolean activaAutoCompleteInsumos;
    private Integer idMotivoNoMinistrado;
    private List<TipoCancelacion> listaMotivos;
    private boolean medPorMinistrar;
    private boolean solucion;
    private boolean showsolution;
    private PermisoUsuario permiso;
    private boolean funValidarFarmacoVigilancia;
    private RespuestaAlertasDTO alertasDTO;       
    private String idEstructuraAlmacensurte;
    
    @Autowired
    private transient PacienteService pacienteService;
    @Autowired
    private transient SurtimientoService surtimientoService;
    private Surtimiento_Extend surtimientoSelected;

    @Autowired
    private transient MedicamentoService medicamentoService;
    private Medicamento_Extended medicamento;
    private List<Medicamento_Extended> medicamentoList;

    @Autowired
    private transient EstructuraService estructuraService;
    private Estructura estructuraSelect;

    @Autowired
    private transient SurtimientoMinistradoService surtimientoMinistradoService;

    @Autowired
    private transient ConfigService configService;

    @Autowired
    private transient TipoCancelacionService tipoCancelacionService;
    
    @Autowired
    private transient DiagnosticoService diagnosticoService;

    @Autowired
    private transient ServletContext servletContext;
    
    /**
     * Metodo que se ejecuta despues de cargar la pantalla de pacientes
     */
    @PostConstruct
    public void init() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        try {
            initialize();
            this.usuarioSession = Comunes.obtenerUsuarioSesion();
            cargarListaPacientes();
            permiso = Comunes.obtenerPermisos(Transaccion_Enum.MINISTRMED.getSufijo());
            obtenerDatosSistema();
            ministraPrevDays = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_MINISTRA_PREVDAYS);
            ministraLaterHours = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_MINISTRA_LATERHOURS);
            Estructura estruct = new Estructura();
            estruct.setIdEstructura(usuarioSession.getIdEstructura());
            estructuraSelect = estructuraService.obtener(estruct);
            activaAutoCompleteInsumos = sesion.isActivaAutocompleteQrClave();
            TipoCancelacion tipoCan = new TipoCancelacion();
            listaMotivos = tipoCancelacionService.obtenerLista(tipoCan);
            surtimientoSelected = new Surtimiento_Extend();
            surtimientoSelected.setFechaProgramada(new Date());
            funValidarFarmacoVigilancia = sesion.isFunValidarFarmacoVigilancia();
        } catch (Exception e) {
            LOGGER.error("Error en el metodo init :: {}", e.getMessage());
        }
    }

    /**
     * Metodo que inicializa todos los atributos de la clase
     */
    public void initialize() {
        this.textoBusqueda = "";
    }

    public void cargarListaPacientes() {
        try {
            List<Integer> listaEstatusPresc = new ArrayList<>();
            listaEstatusPresc.add(EstatusPrescripcion_Enum.PROGRAMADA.getValue());
            listaEstatusPresc.add(EstatusPrescripcion_Enum.FINALIZADA.getValue());
            
            List<String> idsEstructura = new ArrayList<>();
            idsEstructura.add(this.usuarioSession.getIdEstructura());
            idsEstructura.addAll(estructuraService.obtenerIdsEstructuraJerarquica(this.usuarioSession.getIdEstructura(), false));
            
            this.listaPacientes = pacienteService.obtenerPacietesConPrescripcion(
                    Constantes.REGISTROS_PARA_MOSTRAR,
                    idsEstructura,
                    null,
                    listaEstatusPresc);
        } catch (Exception e) {
            LOGGER.error("Error en el metodo cargarListaPacientes :: {}", e.getMessage());
        }
    }

    public void cargarModalMinistracion(Paciente_Extended paciente) {
        cantMinistrar = 1;
        eliminarCodigo = false;
        medPorMinistrar = false;
        solucion = false;
        try {
            List<Integer> listEstatusMinistracion = new ArrayList<>();
            listEstatusMinistracion.add(EstatusMinistracion_Enum.PENDIENTE.getValue());
            this.pacienteSelect = paciente;
            this.listaMedicamentos = medicamentoService.
                    obtenerMedicamentosPorPrescripcion(
                            paciente.getIdPaciente(), Constantes.REGISTROS_PARA_MOSTRAR,
                            listEstatusMinistracion, ministraPrevDays, ministraLaterHours);
            for(Medicamento_Extended item: listaMedicamentos){
                this.idEstructuraAlmacensurte = item.getIdEstructuraAlmacenSurte();
                if(item.getIdTipoSolucion() != null){
                    solucion = true;
                    break;
                }
            }
            
        } catch (Exception e) {
            LOGGER.error("Error en el metodo cargarModalMinistracion :: {}", e.getMessage());
        }
    }

    public void detallePrescripcionSolucion(String folio){
        try{
            surtimientoSelected = surtimientoService.obtenerDetallePrescripcionSolucion(folio);
            detalleSolucion = medicamentoService.obtenerDetallePrescripcionSolucion(folio);            
        }catch(Exception ex){
        LOGGER.error("Ocurrio un error al obtener el detalle de la solucion.",ex);
        }
    }
    
    private void obtenerDatosSistema() {
        LOGGER.debug("mx.mc.magedbean.MinistrarMedicamentoMB.obtenerDatosSistema()");
        Config c = new Config();
        c.setActiva(Constantes.ACTIVO);
        try {
            configList = configService.obtenerLista(c);
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString("sys.config.err"), ex);
        }
    }

    public List<Medicamento_Extended> autoComplete(String cadena) {
        try {
//            String idEstructura = surtimientoService.obtenerAlmacenPadreOfSurtimiento(estructuraSelect.getIdEstructura());
//            if (idEstructura == null || idEstructura.isEmpty()) {
//                idEstructura = estructuraSelect.getIdEstructura();
//            }
            medicamentoList = new ArrayList<>();
            if (this.idEstructuraAlmacensurte!= null) {
                String idEstructura = this.idEstructuraAlmacensurte;
                medicamentoList = medicamentoService.searchMedicamentoAutoComplete(cadena.trim(), idEstructura, activaAutoCompleteInsumos);
            }
        } catch (Exception ex) {
            LOGGER.error("Error al obtener Medicamentos: {}", ex.getMessage());
        }
        if (medicamentoList.size() == 1) {
            String componentId = UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getClientId();
            String panel = componentId.replace(":", "\\\\:") + "_panel";
            PrimeFaces.current().executeScript("$('#" + panel + " .ui-autocomplete-item').trigger('click');");
        }
        return medicamentoList;
    }

    public void handleSelect(SelectEvent e) {
        medicamento = (Medicamento_Extended) e.getObject();
    }

    public void handleUnSelect() {
        medicamento = new Medicamento_Extended();
    }

    public void escanearCodigoQR(SelectEvent e) {
        medicamento = (Medicamento_Extended) e.getObject();
        showsolution=false;
        try {
            if (medicamento != null) {
                int noDiasCaducidad = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_DIASPARACADUCIDAD);
                if (FechaUtil.sumarRestarDiasFecha(new java.util.Date(), noDiasCaducidad).after(medicamento.getFechaCaducidad())) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.caducidadvencida"), null);
                    medicamento = new Medicamento_Extended();
                } else if (cantMinistrar <= 0) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.error.cantidadIncorrecta"), null);
                    medicamento = new Medicamento_Extended();
                } else {
                    String claveMedicamento = medicamento.getClaveInstitucional();
                    String lote = medicamento.getLote();
                    Date fechaCaducidad = medicamento.getFechaCaducidad();
                    boolean match = false;
                    boolean matchLote = false;
                    boolean excede = false;
                    int surtidos = 0;
                    int noSurtidos = 0;
                    for (Medicamento_Extended item : this.listaMedicamentos) {
                        if (lote.equalsIgnoreCase(item.getLote())) {
                            matchLote = true;
                        }
                        if (item.getCantidadMinistrada() == null) {
                            item.setCantidadMinistrada(0);
                        }
                        if ((item.getEstatusMinistracion().equals(EstatusMinistracion_Enum.PENDIENTE.getValue()) && !eliminarCodigo)
                                || (item.getEstatusMinistracion().equals(EstatusMinistracion_Enum.MINISTRADO.getValue()) && !eliminarCodigo
                                && item.getCantidadMinistrada() < item.getCantidadActual())) {
                            if (claveMedicamento.equalsIgnoreCase(item.getClaveInstitucional()) && lote.equalsIgnoreCase(item.getLote())
                                    && fechaCaducidad.equals(item.getFechaCaducidad())) {
                                match = true;
                                if (item.getCantidadActual() >= item.getCantidadMinistrada() + cantMinistrar) {
                                    item.setEstatusMinistracion(EstatusMinistracion_Enum.MINISTRADO.getValue());
                                    item.setCantidadMinistrada(item.getCantidadMinistrada() + cantMinistrar);
                                    
                                    if(item.getIdTipoSolucion()!=null)
                                    {
                                        Date fechaActual = new Date();
                                        surtimientoSelected.setFechaInicio(fechaActual);
                                        surtimientoSelected.setFechaTermino(fechaActual);
                                        detallePrescripcionSolucion(item.getFolioPrescripcion());
                                        showsolution = true;
                                    }
                                } else {
                                    excede = true;
                                }
                                medicamento = new Medicamento_Extended();
                                break;
                            }
                        } else {
                            if (item.getEstatusMinistracion().equals(EstatusMinistracion_Enum.MINISTRADO.getValue()) && eliminarCodigo) {
                                if (claveMedicamento.equalsIgnoreCase(item.getClaveInstitucional()) && lote.equalsIgnoreCase(item.getLote())) {
                                    int cant = item.getCantidadMinistrada() - cantMinistrar;
                                    match = true;
                                    if (cant < 0) {
                                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.error.cantidadMayorLote"), null);
                                        medicamento = new Medicamento_Extended();
                                        break;
                                    } else {
                                        item.setEstatusMinistracion(EstatusMinistracion_Enum.PENDIENTE.getValue());
                                        item.setCantidadMinistrada(cant);
                                        medicamento = new Medicamento_Extended();
                                        break;
                                    }

                                } else {
                                    noSurtidos++;
                                }
                            } else {
                                if (item.getEstatusMinistracion().equals(EstatusMinistracion_Enum.MINISTRADO.getValue())) {
                                    surtidos++;
                                } else {
                                    noSurtidos++;
                                }
                            }

                        }
                    }
                    if (noSurtidos == this.listaMedicamentos.size()) {
                        match = true;
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("minismedicam.err.medNoMinistrado"), null);
                        medicamento = new Medicamento_Extended();
                    }
                    if (surtidos == this.listaMedicamentos.size()) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("minismedicam.err.sinMedicamen"), null);
                        medicamento = new Medicamento_Extended();
                    } else if (!match) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("minismedicam.err.noCorresponde"), null);
                        medicamento = new Medicamento_Extended();
                    } else if (!matchLote) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("minismedicam.loteincorrecto"), null);
                        medicamento = new Medicamento_Extended();
                    } else if (FechaUtil.sumarRestarDiasFecha(new java.util.Date(), noDiasCaducidad).after(fechaCaducidad)) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.caducidadvencida"), null);
                        medicamento = new Medicamento_Extended();
                    } else if (excede) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("minismedicam.err.cantExcede"), null);
                        medicamento = new Medicamento_Extended();
                    }
                }
                limpiarCampos();
            }
        } catch (NumberFormatException ex) {
            LOGGER.error("Error en el metodo escanearCodigoQR :: {}", ex.getMessage());
            medicamento = new Medicamento_Extended();
        }
        medPorMinistrar = false;
        for (Medicamento_Extended item : this.listaMedicamentos) {
            if (item.getEstatusMinistracion().equals(EstatusMinistracion_Enum.MINISTRADO.getValue())) {
                medPorMinistrar = true;
                break;
            }
        }
        
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, showsolution);
    }

    
    public void escanearClaveDePaciente() {
        try {
            List<Integer> listaEstatusPresc = new ArrayList<>();
            listaEstatusPresc.add(EstatusPrescripcion_Enum.PROGRAMADA.getValue());
            listaEstatusPresc.add(EstatusPrescripcion_Enum.FINALIZADA.getValue());
            List<String> idsEstructura = estructuraService.obtenerIdsEstructuraJerarquica(this.usuarioSession.getIdEstructura(), false);
            if (idsEstructura == null) {
                idsEstructura = new ArrayList<>();
            }
            idsEstructura.add(usuarioSession.getIdEstructura());
            List<Paciente_Extended> pacientes = pacienteService.
                    obtenerPacietesConPrescripcion(Constantes.REGISTROS_PARA_MOSTRAR,
                            idsEstructura,
                            this.textoBusqueda, listaEstatusPresc);
            if (pacientes.size() == 1) {
                this.listaPacientes = pacientes;
                cargarModalMinistracion(pacientes.get(0));
                PrimeFaces.current().ajax().addCallbackParam("estatusModal", true);
            } else {
                listaEstatusPresc.add(EstatusPrescripcion_Enum.PROGRAMADA.getValue());
                this.listaPacientes = pacienteService.
                        obtenerPacietesConPrescripcion(Constantes.REGISTROS_PARA_MOSTRAR,
                                idsEstructura, this.textoBusqueda, listaEstatusPresc);
            }
            limpiarCampos();

        } catch (Exception e) {
            LOGGER.error("Error en el metodo escanearClaveDePaciente :: {}", e.getMessage());
        }
    }
    
    public void validarFarmacovigilancia() {
        boolean existenAlertas = false;
        try {
            if(funValidarFarmacoVigilancia) {
                if(!listaMedicamentos.isEmpty()) {
                    ParamBusquedaAlertaDTO  alertaDTO= new ParamBusquedaAlertaDTO();
                    alertaDTO.setFolioPrescripcion(listaMedicamentos.get(0).getFolioPrescripcion());
                    alertaDTO.setNumeroPaciente(listaMedicamentos.get(0).getPacienteNumero());
                    //alertaDTO.setNumeroVisita(surtimientoExtendedSelected.get|);
                    alertaDTO.setNumeroMedico(listaMedicamentos.get(0).getCedProfesional());

                    List<Diagnostico> listDiagnostico =  diagnosticoService.obtenerPorIdPacienteIdVisitaIdPrescripcion(listaMedicamentos.get(0).getIdPaciente(),
                            listaMedicamentos.get(0).getIdVisita(), listaMedicamentos.get(0).getIdPrescripcion());
                    List<String> listaDiagnosticos= new ArrayList<>();
                    for(Diagnostico unDiagnostico: listDiagnostico){
                        listaDiagnosticos.add(unDiagnostico.getClave());
                    }
                    alertaDTO.setListaDiagnosticos(listaDiagnosticos);

                     List<MedicamentoDTO> listaMedicametosDTO= new ArrayList<>();
                
                    for (Medicamento_Extended item : listaMedicamentos) {
                        MedicamentoDTO medicamentoDTO = new MedicamentoDTO();
                        medicamentoDTO.setClaveMedicamento(item.getClaveInstitucional());
                        //medicamentoDTO.setDosis(item.getDosis());
                        //medicamentoDTO.setDuracion(item.getDuracion());
                        //medicamentoDTO.setFrecuencia(item.getFrecuencia());

                        listaMedicametosDTO.add(medicamentoDTO);
                    }

                    alertaDTO.setListaMedicametos(listaMedicametosDTO);
                
                    ObjectMapper Obj = new ObjectMapper();
                    String json = Obj.writeValueAsString(alertaDTO);
                    ReaccionesAdversas cs = new ReaccionesAdversas(servletContext);
                    Response respMus = cs.validaFarmacoVigilancia(json);

                    if(respMus!=null){
                        if(respMus.getStatus()==200){
                            alertasDTO = parseResponseDTO(respMus.getEntity().toString());
                            if(alertasDTO.getCodigo().equals("06")) {
                                existenAlertas=true;
                            } else {
                                existenAlertas=false;
                                ministrarMedicamentos();
                            }
                        }
                    }
                } 
            } else {
                existenAlertas=false;
                ministrarMedicamentos();
            }                
        } catch(Exception ex) {
            LOGGER.error("Error al momento de validar la existencia de alertas de farmacovigilancia:    " + ex.getMessage());
        }        
        PrimeFaces.current().ajax().addCallbackParam(Constantes.EXISTE_ALERTA, existenAlertas);
        
    }

    private RespuestaAlertasDTO parseResponseDTO(String request){
        RespuestaAlertasDTO dto= new RespuestaAlertasDTO();
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode params = mapper.readTree(request);
            
            dto.setCodigo(params.hasNonNull("codigo") ? params.get("codigo").asText() : null);
            dto.setDescripcion(params.hasNonNull("descripcion") ? params.get("descripcion").asText() : null);
            
            List<AlertaFarmacovigilancia> listaAlerta = new ArrayList<>();
            if (params.hasNonNull("listaAlertaFarmacovigilancia")) {
                for (Iterator<JsonNode> iter = params.get("listaAlertaFarmacovigilancia").elements(); iter.hasNext();) {
                    JsonNode node = iter.next();
                    AlertaFarmacovigilancia item = new AlertaFarmacovigilancia();
                    item.setNumero(node.hasNonNull("numero") ? node.get("numero").asInt():null);
                    item.setFactor1(node.hasNonNull("factor1") ? node.get("factor1").asText() : null);
                    item.setFactor2(node.hasNonNull("factor2") ? node.get("factor2").asText() : null);
                    item.setRiesgo(node.hasNonNull("riesgo") ? node.get("riesgo").asText() : null);
                    item.setTipo(node.hasNonNull("tipo") ? node.get("tipo").asText() : null);
                    item.setOrigen(node.hasNonNull("origen") ? node.get("origen").asText() : null);
                    item.setClasificacion(node.hasNonNull("clasificacion") ? node.get("clasificacion").asText() : null);
                    item.setMotivo(node.hasNonNull("motivo") ? node.get("motivo").asText() : null); 
                    if(node.get("riesgo").asText().equals("Alto")) {
                        item.setColorRiesgo("#FDCAE1");
                    } else {
                        if(node.get("riesgo").asText().equals("Medio")) {
                            item.setColorRiesgo("#F4FAB4");
                        }else {
                            if(node.get("riesgo").asText().equals("Bajo")) {
                                item.setColorRiesgo("#D8F8E1");
                            } else {
                                item.setColorRiesgo("");
                            }
                        }
                    }

                    listaAlerta.add(item);                    
                }
            }
            dto.setListaAlertaFarmacovigilancia(listaAlerta);
            
            List<ValidacionNoCumplidaDTO> listaValidacion = new ArrayList<>();
            if(params.hasNonNull("ValidacionNoCumplidas")){
                for (Iterator<JsonNode> iter = params.get("ValidacionNoCumplidas").elements(); iter.hasNext();) {
                    JsonNode node = iter.next();
                    ValidacionNoCumplidaDTO item = new ValidacionNoCumplidaDTO();
                    item.setCodigo(node.hasNonNull("codigo") ? node.get("codigo").asText():null);
                    item.setDescripcion(node.hasNonNull("descripcion") ? node.get("descripcion").asText():null);
                    
                    listaValidacion.add(item);
                }
            }
            dto.setValidacionNoCumplidas(listaValidacion);
            
        }catch(IOException ex){
            LOGGER.error("Ocurrio un error al parsear el request:",ex);
        }
        return dto;
    }
    
    public void rechazarValidacionPrescripcion(){
        LOGGER.trace("mx.mc.magedbean.ministracionMedicamentoMB.rechazarValidacionPrescripcion()");    
        boolean status = Constantes.INACTIVO;
        try{
            Surtimiento surtimiento = new Surtimiento();
            surtimiento.setIdSurtimiento(surtimientoSelected.getIdSurtimiento()); 
            surtimiento.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.RECHAZADA.getValue());
            surtimiento.setUpdateFecha(new Date());
            surtimiento.setUpdateIdUsuario(usuarioSession.getIdUsuario());
            for (Medicamento_Extended item : this.listaMedicamentos) {
                if (item.getIdTipoSolucion() != null) {
                    List<SurtimientoMinistrado_Extend> listaSurtMinSolucion = surtimientoMinistradoService.obtenerListSurtimientoMinistradoSolucion(surtimientoSelected.getClaveAgrupada());
                    for (SurtimientoMinistrado_Extend sms : listaSurtMinSolucion) {                        
                        sms.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.RECHAZADA.getValue());
                        sms.setIdEstatusMinistracion(EstatusMinistracion_Enum.CANCELADO.getValue());
                        sms.setUpdateFecha(new Date());
                        sms.setUpdateIdUsuario(this.usuarioSession.getIdUsuario());
                    }
                } else {
                    SurtimientoMinistrado surtMinist = new SurtimientoMinistrado();
                }        
            }
        }catch(Exception ex){
            LOGGER.error("ocurrio un error en: rechazarValidacionPrescripcion    ",ex);
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }
    
    public void ministrarMedicamentos() {
        try {
            if (!this.permiso.isPuedeProcesar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estr.err.permisos"), null);
                return;
            }            
            List<SurtimientoMinistrado> listaSurtMin = new ArrayList<>();
            List<MovimientoInventario> listaMovInv = new ArrayList<>();
            for (Medicamento_Extended item : this.listaMedicamentos) {
                if (item.getEstatusMinistracion().equals(EstatusMinistracion_Enum.MINISTRADO.getValue())) {
                    if (item.getIdTipoSolucion() != null) {
                        List<SurtimientoMinistrado_Extend> listaSurtMinSolucion = surtimientoMinistradoService.obtenerListSurtimientoMinistradoSolucion(surtimientoSelected.getClaveAgrupada());
                        for (SurtimientoMinistrado_Extend sms : listaSurtMinSolucion) {
                            sms.setFechaInicio(surtimientoSelected.getFechaInicio());
                            sms.setFechaMinistrado(surtimientoSelected.getFechaTermino());
                            sms.setComentarios(surtimientoSelected.getComentarios());
                            sms.setIdUsuario(this.usuarioSession.getIdUsuario());
                            sms.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.RECIBIDO.getValue());
                            sms.setIdEstatusMinistracion(EstatusMinistracion_Enum.MINISTRADO.getValue());
                            sms.setUpdateFecha(new Date());
                            sms.setUpdateIdUsuario(this.usuarioSession.getIdUsuario());
                            sms.setCantidadMinistrada(sms.getCantidad());
                            listaSurtMin.add(sms);                            
                            
                            MovimientoInventario movInventario = new MovimientoInventario();
                            movInventario.setIdMovimientoInventario(Comunes.getUUID());
                            movInventario.setIdTipoMotivo(TipoMotivo_Enum.SAL_MINIS_POR_SURTIMIENTO_DE_PRESCRIPCION.getMotivoValue());
                            movInventario.setFecha(new Date());
                            movInventario.setIdUsuarioMovimiento(this.usuarioSession.getIdUsuario());
                            movInventario.setIdEstrutcuraOrigen(this.usuarioSession.getIdEstructura());
                            movInventario.setIdEstrutcuraDestino(this.usuarioSession.getIdEstructura());
                            movInventario.setIdInventario(sms.getIdInventario());
                            movInventario.setCantidad(sms.getCantidad());
                            movInventario.setFolioDocumento(item.getFolioPrescripcion());
                            listaMovInv.add(movInventario);
                        }
                    }
                    else {
                        SurtimientoMinistrado surtMinist = new SurtimientoMinistrado();
                        surtMinist.setIdMinistrado(item.getIdMinistrado());
                        surtMinist.setFechaMinistrado(new Date());
                        surtMinist.setIdUsuario(this.usuarioSession.getIdUsuario());
                        surtMinist.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.RECIBIDO.getValue());
                        surtMinist.setIdEstatusMinistracion(EstatusMinistracion_Enum.MINISTRADO.getValue());
                        surtMinist.setUpdateFecha(new Date());
                        surtMinist.setUpdateIdUsuario(this.usuarioSession.getIdUsuario());
                        surtMinist.setCantidadMinistrada(item.getCantidadMinistrada());
                        listaSurtMin.add(surtMinist);
                        
                        MovimientoInventario movInventario = new MovimientoInventario();
                        movInventario.setIdMovimientoInventario(Comunes.getUUID());
                        movInventario.setIdTipoMotivo(TipoMotivo_Enum.SAL_MINIS_POR_SURTIMIENTO_DE_PRESCRIPCION.getMotivoValue());
                        movInventario.setFecha(new Date());
                        movInventario.setIdUsuarioMovimiento(this.usuarioSession.getIdUsuario());
                        movInventario.setIdEstrutcuraOrigen(this.usuarioSession.getIdEstructura());
                        movInventario.setIdEstrutcuraDestino(this.usuarioSession.getIdEstructura());
                        movInventario.setIdInventario(item.getIdInventario());
                        movInventario.setCantidad(item.getCantidadMinistrada());
                        movInventario.setFolioDocumento(item.getFolioPrescripcion());
                        listaMovInv.add(movInventario);
                    }
                }
            }
            boolean resp = surtimientoMinistradoService.actualizarSurtiminetoMinistrado(listaSurtMin, listaMovInv);
            if (resp) {
                Mensaje.showMessage("Info", RESOURCES.getString("minismedicam.info.ministrado"), null);
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo ministrarMedicamentos :: {}", e.getMessage());
        }
    }

    private void limpiarCampos() {
        this.textoBusqueda = "";
        this.eliminarCodigo = false;
        this.cantMinistrar = 1;
    }

    public String getTextoBusqueda() {
        return textoBusqueda;
    }

    public void setTextoBusqueda(String textoBusqueda) {
        this.textoBusqueda = textoBusqueda;
    }

    public List<Paciente_Extended> getListaPacientes() {
        return listaPacientes;
    }

    public void setListaPacientes(List<Paciente_Extended> listaPacientes) {
        this.listaPacientes = listaPacientes;
    }

    public List<Medicamento_Extended> getListaMedicamentos() {
        return listaMedicamentos;
    }

    public void setListaMedicamentos(List<Medicamento_Extended> listaMedicamentos) {
        this.listaMedicamentos = listaMedicamentos;
    }

    public Paciente_Extended getPacienteSelect() {
        return pacienteSelect;
    }

    public void setPacienteSelect(Paciente_Extended pacienteSelect) {
        this.pacienteSelect = pacienteSelect;
    }

    public Usuario getUsuarioSession() {
        return usuarioSession;
    }

    public void setUsuarioSession(Usuario usuarioSession) {
        this.usuarioSession = usuarioSession;
    }

    public int getCantMinistrar() {
        return cantMinistrar;
    }

    public void setCantMinistrar(int cantMinistrar) {
        this.cantMinistrar = cantMinistrar;
    }

    public boolean isEliminarCodigo() {
        return eliminarCodigo;
    }

    public void setEliminarCodigo(boolean eliminarCodigo) {
        this.eliminarCodigo = eliminarCodigo;
    }

    public Medicamento_Extended getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento_Extended medicamento) {
        this.medicamento = medicamento;
    }

    public List<Medicamento_Extended> getMedicamentoList() {
        return medicamentoList;
    }

    public void setMedicamentoList(List<Medicamento_Extended> medicamentoList) {
        this.medicamentoList = medicamentoList;
    }

    public Integer getIdMotivoNoMinistrado() {
        return idMotivoNoMinistrado;
    }

    public void setIdMotivoNoMinistrado(Integer idMotivoNoMinistrado) {
        this.idMotivoNoMinistrado = idMotivoNoMinistrado;
    }

    public List<TipoCancelacion> getListaMotivos() {
        return listaMotivos;
    }

    public void setListaMotivos(List<TipoCancelacion> listaMotivos) {
        this.listaMotivos = listaMotivos;
    }

    public boolean isMedPorMinistrar() {
        return medPorMinistrar;
    }

    public void setMedPorMinistrar(boolean medPorMinistrar) {
        this.medPorMinistrar = medPorMinistrar;
    }

    public List<Medicamento_Extended> getDetalleSolucion() {
        return detalleSolucion;
    }

    public void setDetalleSolucion(List<Medicamento_Extended> detalleSolucion) {
        this.detalleSolucion = detalleSolucion;
    }

    public Surtimiento_Extend getSurtimientoSelected() {
        return surtimientoSelected;
    }

    public void setSurtimientoSelected(Surtimiento_Extend surtimientoSelected) {
        this.surtimientoSelected = surtimientoSelected;
    }

    public boolean isSolucion() {
        return solucion;
    }

    public void setSolucion(boolean solucion) {
        this.solucion = solucion;
    }

    public boolean isShowsolution() {
        return showsolution;
    }

    public void setShowsolution(boolean showsolution) {
        this.showsolution = showsolution;
    }

    public void noMinistrarMedicamentos() {
        try {
            if (!this.permiso.isPuedeProcesar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estr.err.permisos"), null);
                return;
            }
            List<SurtimientoMinistrado> listaSurtMin = new ArrayList<>();
            for (Medicamento_Extended item : this.listaMedicamentos) {
                if (item.getEstatusMinistracion().equals(EstatusMinistracion_Enum.MINISTRADO.getValue())) {
                    SurtimientoMinistrado surtMinist = new SurtimientoMinistrado();
                    surtMinist.setIdMinistrado(item.getIdMinistrado());
                    surtMinist.setFechaMinistrado(new Date());
                    surtMinist.setIdUsuario(this.usuarioSession.getIdUsuario());
                    surtMinist.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.RECIBIDO.getValue());
                    surtMinist.setIdEstatusMinistracion(EstatusMinistracion_Enum.NO_MINISTRADO.getValue());
                    surtMinist.setUpdateFecha(new Date());
                    surtMinist.setUpdateIdUsuario(this.usuarioSession.getIdUsuario());
                    surtMinist.setCantidadMinistrada(item.getCantidadMinistrada());
                    surtMinist.setIdMotivoNoMinistrado(idMotivoNoMinistrado);
                    listaSurtMin.add(surtMinist);
                }
            }
            boolean resp = surtimientoMinistradoService.actualizarSurtiminetoMinistrado(listaSurtMin, null);
            if (resp) {
                Mensaje.showMessage("Info", RESOURCES.getString("minismedicam.info.noministrado"), null);
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo noMinistrarMedicamentos :: {}", e.getMessage());
        }
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }
    
    public RespuestaAlertasDTO getAlertasDTO() {
        return alertasDTO;
    }

    public void setAlertasDTO(RespuestaAlertasDTO alertasDTO) {
        this.alertasDTO = alertasDTO;
    }
    
}
