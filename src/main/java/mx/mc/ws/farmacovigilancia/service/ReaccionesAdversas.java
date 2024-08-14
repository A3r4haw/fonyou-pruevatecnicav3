/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.ws.farmacovigilancia.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import mx.mc.dto.MedicamentoDTO;
import mx.mc.dto.ParamBusquedaAlertaDTO;
import mx.mc.dto.RespuestaAlertasDTO;
import mx.mc.dto.ValidacionNoCumplidaDTO;
import mx.mc.dto.ValidacionSolucionMezclaDetalleDTO;
import mx.mc.enums.CodigosRespuestaFarmacovigilancia_Enum;
import mx.mc.enums.RespuestasInfoMedicamento_Enum;
import mx.mc.init.Constantes;
import mx.mc.init.CustomWebSecurityConfigurerAdapter;
import mx.mc.model.AlertaFarmacovigilancia;
import mx.mc.model.TipoReaccion;
import mx.mc.model.Consecuencia;
import mx.mc.model.EstatusReaccion;
import mx.mc.model.Medicamento;
import mx.mc.model.MedicamentoConcomitante;
import mx.mc.model.Reaccion;
import mx.mc.model.SustanciaActiva;
import mx.mc.model.UnidadConcentracion;
import mx.mc.model.Usuario;
import mx.mc.model.ViaAdministracion;
import mx.mc.model.Config;
import mx.mc.model.Origen;
import mx.mc.model.ProtocoloExtended;
import mx.mc.model.TipoInforme;
import mx.mc.service.AlertaFarmacovigilanciaService;
import mx.mc.service.AlertaFarmacovigilanciaServiceImpl;
import mx.mc.service.ConfigService;
import mx.mc.service.ConfigServiceImpl;
import mx.mc.service.ConsecuenciaService;
import mx.mc.service.ConsecuenciaServiceImpl;
import mx.mc.service.EstatusReaccionService;
import mx.mc.service.EstatusReaccionServiceImpl;
import mx.mc.service.MedicamentoService;
import mx.mc.service.MedicamentoServiceImpl;
import mx.mc.service.OrigenService;
import mx.mc.service.OrigenServiceImpl;
import mx.mc.service.ReaccionService;
import mx.mc.service.ReaccionServiceImpl;
import mx.mc.service.SustanciaActivaService;
import mx.mc.service.SustanciaActivaServiceImpl;
import mx.mc.service.TipoInformeService;
import mx.mc.service.TipoInformeServiceImpl;
import mx.mc.service.UnidadConcentracionService;
import mx.mc.service.UsuarioService;
import mx.mc.service.ViaAdministracionService;
import mx.mc.util.Comunes;
import mx.mc.util.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import mx.mc.service.TipoReaccionService;
import mx.mc.service.TipoReaccionServiceImpl;
import mx.mc.service.UnidadConcentracionServiceImpl;
import mx.mc.service.UsuarioServiceImpl;
import mx.mc.service.ViaAdministracionServiceImpl;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * REST Web Service
 *
 * @author bbautista
 */
@Path("reaccion")
public class ReaccionesAdversas {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReaccionesAdversas.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    @Autowired
    private ReaccionService reaccionesService;

    @Autowired
    private EstatusReaccionService reaccionService;
    private List<EstatusReaccion> estatusReaccionList;

    @Autowired
    private TipoReaccionService tipoReaccionService;
    private List<TipoReaccion> tipoReaccionList;

    @Autowired
    private SustanciaActivaService sustanciaService;
    private List<SustanciaActiva> sustanciaActivaList;

    @Autowired
    private ViaAdministracionService viaService;
    private List<ViaAdministracion> viaList;

    @Autowired
    private UnidadConcentracionService unidadService;
    private List<UnidadConcentracion> unidadList;

    @Autowired
    private ConsecuenciaService consecuenciaService;
    private List<Consecuencia> consecuenciaList;

    @Autowired
    private TipoInformeService tipoInformeService;
    private List<TipoInforme> tipoInformeList;

    @Autowired
    private OrigenService origenService;
    private List<Origen> origenList;

    @Autowired
    private MedicamentoService mediService;

    @Autowired
    private ConfigService configService;
    private List<Config> configList;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AlertaFarmacovigilanciaService alertaFarmacoService;

    @PostConstruct
    public void init() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    /**
     * Creates a new instance of ReaccionesAdversas
     */
    public ReaccionesAdversas() {
    }

    public ReaccionesAdversas(ServletContext servletContext) {
        ApplicationContext springContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
        AutowireCapableBeanFactory factory = springContext.getAutowireCapableBeanFactory();
        if (usuarioService == null) {
            usuarioService = factory.createBean(UsuarioServiceImpl.class);
        }
        if (reaccionesService == null) {
            reaccionesService = factory.createBean(ReaccionServiceImpl.class);
        }
        if (reaccionService == null) {
            reaccionService = factory.createBean(EstatusReaccionServiceImpl.class);
        }
        if (tipoReaccionService == null) {
            tipoReaccionService = factory.createBean(TipoReaccionServiceImpl.class);
        }
        if (sustanciaService == null) {
            sustanciaService = factory.createBean(SustanciaActivaServiceImpl.class);
        }
        if (viaService == null) {
            viaService = factory.createBean(ViaAdministracionServiceImpl.class);
        }
        if (unidadService == null) {
            unidadService = factory.createBean(UnidadConcentracionServiceImpl.class);
        }
        if (consecuenciaService == null) {
            consecuenciaService = factory.createBean(ConsecuenciaServiceImpl.class);
        }
        if (mediService == null) {
            mediService = factory.createBean(MedicamentoServiceImpl.class);
        }
        if (configService == null) {
            configService = factory.createBean(ConfigServiceImpl.class);
        }
        if (tipoInformeService == null) {
            tipoInformeService = factory.createBean(TipoInformeServiceImpl.class);
        }
        if (origenService == null) {
            origenService = factory.createBean(OrigenServiceImpl.class);
        }
        if (alertaFarmacoService == null) {
            alertaFarmacoService = factory.createBean(AlertaFarmacovigilanciaServiceImpl.class);
        }
    }

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(String credentials) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode resultado = mapper.createObjectNode();
        if (!tokenEnabled()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        try {
            JsonNode params = mapper.readTree(credentials);
            if (!params.hasNonNull("usuario") || !params.hasNonNull("password")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(RESOURCES.getString("farmaco.ws.login.err.datosRequeridos")).build();
            }
            String usuario = params.get("usuario").asText();
            String password = params.get("password").asText();
            Usuario usuarioSel = obtenerUsuario(usuario);
            if (usuarioSel == null || !CustomWebSecurityConfigurerAdapter.customPasswordEncoder().matches(password, usuarioSel.getClaveAcceso())) {
                return Response.status(Response.Status.UNAUTHORIZED).entity(RESOURCES.getString("farmaco.ws.login.err.datosInvalidos")).build();
            }
            Token tok = new Token(usuario);
            resultado.put("token", tok.getValue());
        } catch (IOException ex) {
            LOGGER.error("{}.login(): {}", this.getClass().getCanonicalName(), ex.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.ok(resultado.toString()).build();
    }

    @PUT
    @Path("registroReaccion")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registroReaccion(String request) {
        Reaccion reaccion;
        InicializaCatalogos();
        List<MedicamentoConcomitante> insumosList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode resultado = mapper.createObjectNode();
        Usuario usuarioSel = new Usuario();
        try {
            StringBuilder result = new StringBuilder();
            JsonNode params = mapper.readTree(request);

            reaccion = params.get("reaccion") != null ? parseReaccion(params.get("reaccion")) : parseReaccion(params);

            if (tokenEnabled()) {
                if (!params.hasNonNull("token") || !(new Token()).parseJWT(params.get("token").asText(), result)) {
                    return Response.status(Response.Status.UNAUTHORIZED).entity(RESOURCES.getString("farmaco.ws.login.err.noAutorizado") + " - " + result).build();
                }
                usuarioSel = obtenerUsuario(result.toString());
                if (usuarioSel == null) {
                    return Response.status(Response.Status.UNAUTHORIZED).entity(RESOURCES.getString("farmaco.ws.login.err.noAutorizado") + " - " + result).build();
                }
            } else {
                usuarioSel = obtenerUsuario(Constantes.USUARIO_SERVICIOS_WEB);
                if (usuarioSel == null) {
                    return Response.status(Response.Status.UNAUTHORIZED).entity(RESOURCES.getString("farmaco.ws.login.err.noUsuarioGenerico") + " - " + result).build();
                }
            }
            if (reaccion.getIdReaccion() != null && !reaccion.getIdReaccion().isEmpty()) {
                reaccion.setUpdateFecha(new Date());
                reaccion.setUpdateIdUsuario(usuarioSel.getIdUsuario());

                if (reaccion.getInsumos().size() > 0) {
                    insumosList = reaccion.getInsumos();
                }

                for (MedicamentoConcomitante item : insumosList) {
                    item.setIdReaccion(reaccion.getIdReaccion());
                    item.setInsertFecha(new Date());
                    item.setInsertIdUsuario(usuarioSel.getIdUsuario());
                }
                reaccionesService.actualizarReaccion(reaccion, insumosList);
                 resultado.put("respuestaCodigo", CodigosRespuestaFarmacovigilancia_Enum.EXITOSO.getValue());
                resultado.put("respuestaMensaje", RESOURCES.getString("farmaco.ws.guardar.ok"));
            } else {
                reaccion.setIdReaccion(Comunes.getUUID());
                if (reaccion.getClave() != null && !reaccion.getClave().equals("")) {
                    Medicamento insumo = mediService.obtenerMedicaByClave(reaccion.getClave());
                    if (insumo != null) {
                        reaccion.setIdInsumo(insumo.getIdMedicamento());
                        reaccion.setIdSustanciaActiva(insumo.getSustanciaActiva());
                    }
                }
                reaccion.setInsertFecha(new Date());
                reaccion.setInsertIdUsuario(usuarioSel.getIdUsuario());

                if (reaccion.getInsumos().size() > 0) {
                    insumosList = reaccion.getInsumos();
                }

                for (MedicamentoConcomitante item : insumosList) {
                    item.setIdReaccion(reaccion.getIdReaccion());
                    item.setInsertFecha(new Date());
                    item.setInsertIdUsuario(usuarioSel.getIdUsuario());
                }

                reaccionesService.insertarReaccion(reaccion, insumosList);
                resultado.put("respuestaCodigo", CodigosRespuestaFarmacovigilancia_Enum.EXITOSO.getValue());
                resultado.put("respuestaMensaje", RESOURCES.getString("farmaco.ws.guardar.ok"));
                LOGGER.info("Se inserto la reacción de forma correcta.");
            }

        } catch (Exception ex) {
            resultado.put("respuestaCodigo", CodigosRespuestaFarmacovigilancia_Enum.ERROR_RECIBIR.getValue());
            resultado.put("respuestaMensaje", RESOURCES.getString("farmaco.ws.guardar.error"));
            LOGGER.error("Ocurrio nu error en guardarReaccionAdversa.", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        return Response.ok(resultado.toString()).build();
    }

    @PUT
    @Path("validaFarmacoVigilancia")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response validaFarmacoVigilancia(String request) {

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode resultado = mapper.createObjectNode();
        try {
            JsonNode params = mapper.readTree(request);

            ParamBusquedaAlertaDTO dto = parseAlertaDTO(params);
            RespuestaAlertasDTO response = alertaFarmacoService.buscarAlertasFarmacovigilancia(dto);
            resultado.put("codigo", response.getCodigo());
            resultado.put("descripcion", response.getDescripcion());

            ArrayNode alertList = resultado.putArray("listaAlertaFarmacovigilancia");
            if(response.getListaAlertaFarmacovigilancia() != null)
                for (AlertaFarmacovigilancia alerta : response.getListaAlertaFarmacovigilancia()) {
                    ObjectNode item = alertList.addObject();
                    item.put("numero", alerta.getNumero());
                    item.put("factor1", alerta.getFactor1());
                    item.put("factor2", alerta.getFactor2());
                    item.put("riesgo", alerta.getRiesgo());
                    item.put("tipo", alerta.getTipo());
                    item.put("origen", alerta.getOrigen());
                    item.put("clasificacion", alerta.getClasificacion());
                    item.put("motivo", alerta.getMotivo());                    
                }
            
            ArrayNode validaList = resultado.putArray("ValidacionNoCumplidas");
            if(response.getValidacionNoCumplidas()!= null)
                for (ValidacionNoCumplidaDTO val : response.getValidacionNoCumplidas()) {
                    ObjectNode item = validaList.addObject();
                    item.put("codigo",val.getCodigo());
                    item.put("descripcion",val.getDescripcion());
            }
            
            ArrayNode alertListaProtocolo = resultado.putArray("listaAlertaProtocolo");
            if(response.getListaProtocolos() != null) {
                for(ProtocoloExtended protocolo : response.getListaProtocolos()) {
                    ObjectNode item = alertListaProtocolo.addObject();
                    item.put("claveProtocolo", protocolo.getClaveProtocolo());
                    item.put("diagnostico", protocolo.getDiagnostico());
                    item.put("claveMedicamento", protocolo.getClaveMedicamento());
                    item.put("nombreMedicamento", protocolo.getNombreMedicamento());
                    item.put("dosis", protocolo.getDosis());
                    item.put("frecuencia", protocolo.getFrecuencia());
                    item.put("ciclos", protocolo.getCiclos());
                    item.put("estabilidad", protocolo.getEstabilidad());
                    item.put("area", protocolo.getArea());
                    item.put("peso", protocolo.getPeso());
                    
                    //ArrayNode alertListaDetalleProtocolo = resultado.putArray("listaAlertaDetalleProtocolo");
                    ArrayNode alertListaDetalleProtocolo = item.putArray("listaAlertaDetalleProtocolo");                    
                    if(protocolo.getListaDetalleValidacionSolucion() != null) {
                        for(ValidacionSolucionMezclaDetalleDTO protocoloDetalle : protocolo.getListaDetalleValidacionSolucion()) {
                            ObjectNode item1 = alertListaDetalleProtocolo.addObject();
                            item1.put("nombre", protocoloDetalle.getNombre());
                            item1.put("indicada", protocoloDetalle.getIndicada());
                            item1.put("comentarios", protocoloDetalle.getCausa());
                            item1.put("prescrita", protocoloDetalle.getPrescrita());
                        }
                    }
                }
            }

        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al validar el farmaco.", ex);            
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok(resultado.toString()).build();
    }

    private ParamBusquedaAlertaDTO parseAlertaDTO(JsonNode params) {
        ParamBusquedaAlertaDTO dto = new ParamBusquedaAlertaDTO();
        try {
            dto.setNumeroPaciente(params.hasNonNull("numeroPaciente") ? params.get("numeroPaciente").asText() : null);
            dto.setNumeroVisita(params.hasNonNull("numeroVisita") ? params.get("numeroVisita").asText() : null);
            dto.setNumeroMedico(params.hasNonNull("numeroMedico") ? params.get("numeroMedico").asText() : null);
            dto.setFolioPrescripcion(params.hasNonNull("folioPrescripcion") ? params.get("folioPrescripcion").asText() : null);
            dto.setEsSolucionOncologica(params.hasNonNull("esSolucionOncologica") ? params.get("esSolucionOncologica").asBoolean() : null);
            dto.setFolioSurtimiento(params.hasNonNull("folioSurtimiento") ? params.get("folioSurtimiento").asText() : null);

            List<MedicamentoDTO> insumos = new ArrayList<>();
            if (params.hasNonNull("listaMedicametos")) {
                for (Iterator<JsonNode> iter = params.get("listaMedicametos").elements(); iter.hasNext();) {
                    JsonNode node = iter.next();
                    Medicamento insumo = mediService.obtenerMedicaByClave(node.hasNonNull("claveMedicamento") ? node.get("claveMedicamento").asText() : null);
                    if (insumo != null) {
                        MedicamentoDTO item = new MedicamentoDTO();
                        item.setClaveMedicamento(insumo.getClaveInstitucional());
                        item.setDosis(new BigDecimal(node.hasNonNull("dosis") ? node.get("dosis").asText() : "0"));
                        item.setDuracion(node.hasNonNull("duracion") ? node.get("duracion").asInt() : null);
                        item.setFrecuencia(node.hasNonNull("frecuencia") ? node.get("frecuencia").asInt() : null);

                        insumos.add(item);
                    }

                }
            }
            dto.setListaMedicametos(insumos);

            List<String> diagnosticos = new ArrayList<>();
            if (params.hasNonNull("listaDiagnosticos")) {
                for (Iterator<JsonNode> iter = params.get("listaDiagnosticos").elements(); iter.hasNext();) {
                    JsonNode node = iter.next();
                    diagnosticos.add(node.textValue());
                }
            }
            dto.setListaDiagnosticos(diagnosticos);

        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al parsear ParamBusquedaAlertaDTO.", ex);
        }
        return dto;
    }

    private Reaccion parseReaccion(JsonNode params) {
        Reaccion reaccion = new Reaccion();
        try {
            //JsonNode params = mapper.readTree(request);           
            reaccion.setIdReaccion(params.hasNonNull("idReaccion") ? params.get("idReaccion").asText() : null);
            reaccion.setFolio(params.hasNonNull("folio") ? params.get("folio").asText() : null);
            reaccion.setNumeroNotificacion(params.hasNonNull("numeroNotificacion") ? params.get("numeroNotificacion").asText() : null);
            if (params.hasNonNull("fecha")) {
                Date fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(params.hasNonNull("fecha") ? params.get("fecha").asText() : null);
                reaccion.setFecha(fecha);
            }
            String aux = params.hasNonNull("estatusReaccion") ? params.get("estatusReaccion").asText().toUpperCase() : null;
            EstatusReaccion estatus = estatusReaccionList != null ? estatusReaccionList.stream().filter(p -> p.getDescripcion().toUpperCase().equals(aux)).findAny().orElse(null) : null;
            reaccion.setIdEstatusReaccion(estatus != null ? estatus.getIdEstatusReaccion() : null);

            reaccion.setRfcPaciente(params.hasNonNull("rfcPaciente") ? params.get("rfcPaciente").asText() : null);
            reaccion.setCurpPaciente(params.hasNonNull("curpPaciente") ? params.get("curpPaciente").asText() : null);
            reaccion.setIdPaciente(params.hasNonNull("idPaciente") ? params.get("idPaciente").asText() : null);
            reaccion.setNumeroPaciente(params.hasNonNull("numeroPaciente") ? params.get("numeroPaciente").asText() : null);
            reaccion.setNombrePaciente(params.hasNonNull("nombrePaciente") ? params.get("nombrePaciente").asText() : null);
            if (params.hasNonNull("fechaNacimiento")) {
                Date naci = new SimpleDateFormat("dd/MM/yyyy").parse(params.hasNonNull("fechaNacimiento") ? params.get("fechaNacimiento").asText() : null);
                reaccion.setFechaNacimiento(naci);
            }
            reaccion.setEdad(params.hasNonNull("edad") ? params.get("edad").asInt() : null);
            reaccion.setSexo(params.hasNonNull("sexo") ? params.get("sexo").asText().toUpperCase().equals("MASCULINO") ? 1 : 0 : null);
            reaccion.setPeso(params.hasNonNull("peso") ? params.get("peso").asDouble() : null);
            reaccion.setEstatura(params.hasNonNull("estatura") ? params.get("estatura").asDouble() : null);
            if (params.hasNonNull("fechaInicioSospecha")) {
                Date inicio = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(params.hasNonNull("fechaInicioSospecha") ? params.get("fechaInicioSospecha").asText() : null);
                reaccion.setFechaInicioSospecha(inicio);
            }
            String tiporeaccion = params.hasNonNull("tipoReaccion") ? params.get("tipoReaccion").asText().toUpperCase() : null;
            TipoReaccion tipoR = tipoReaccionList != null ? tipoReaccionList.stream().filter(p -> p.getReaccion().toUpperCase().equals(tiporeaccion)).findAny().orElse(null) : null;
            reaccion.setIdTipoReaccion(tipoR != null ? tipoR.getIdTipoReaccion() : null);

            String consecuencia = params.hasNonNull("consecuencia") ? params.get("consecuencia").asText().toUpperCase() : null;
            Consecuencia conse = consecuenciaList != null ? consecuenciaList.stream().filter(p -> p.getDescripcion().toUpperCase().equals(consecuencia)).findAny().orElse(null) : null;
            reaccion.setIdConsecuencia(conse != null ? conse.getIdConsecuencia() : null);
            reaccion.setDescripcion(params.hasNonNull("descripcion") ? params.get("descripcion").asText() : null);

            reaccion.setIdInsumo(params.hasNonNull("idInsumo") ? params.get("idInsumo").asText() : null);
            reaccion.setMedicamento(params.hasNonNull("medicamento") ? params.get("medicamento").asText() : null);
            reaccion.setClave(params.hasNonNull("clave") ? params.get("clave").asText() : null);

            String sustancia = params.hasNonNull("sustanciaActiva") ? params.get("sustanciaActiva").asText().toUpperCase() : null;
            SustanciaActiva sus = sustanciaActivaList != null ? sustanciaActivaList.stream().filter(p -> p.getNombreSustanciaActiva().toUpperCase().equals(sustancia)).findAny().orElse(null) : null;
            reaccion.setIdSustanciaActiva(sus != null ? sus.getIdSustanciaActiva() : null);
            reaccion.setSustanciaActiva(sus != null ? sus.getNombreSustanciaActiva() : null);

            String viaAd = params.hasNonNull("viaAdministracion") ? params.get("viaAdministracion").asText().toUpperCase() : null;
            ViaAdministracion via = viaList != null ? viaList.stream().filter(p -> p.getNombreViaAdministracion().toUpperCase().equals(viaAd)).findAny().orElse(null) : null;
            reaccion.setIdViaAdministracion(via != null ? via.getIdViaAdministracion() : null);
            reaccion.setViaAdministracion(via != null ? via.getIdViaAdministracion().toString() : null);

            reaccion.setLote(params.hasNonNull("lote") ? params.get("lote").asText() : null);
            if (params.hasNonNull("caducidad")) {
                Date cadu = new SimpleDateFormat("dd/MM/yyyy").parse(params.hasNonNull("caducidad") ? params.get("caducidad").asText() : null);
                reaccion.setCaducidad(cadu);
            }
            reaccion.setLaboratorio(params.hasNonNull("laboratorio") ? params.get("laboratorio").asText() : null);
            reaccion.setDenomincacionDistintiva(params.hasNonNull("denomincacionDistintiva") ? params.get("denomincacionDistintiva").asText() : null);
            reaccion.setDosis(params.hasNonNull("dosis") ? params.get("dosis").asDouble() : null);
            reaccion.setUnidad(params.hasNonNull("unidad") ? params.get("unidad").asText() : null);
            reaccion.setFrecuencia(params.hasNonNull("frecuencia") ? params.get("frecuencia").asText() : null);
            reaccion.setDuracion(params.hasNonNull("duracion") ? params.get("duracion").asInt() : null);
            reaccion.setMotivoPrescripcion(params.hasNonNull("motivoPrescripcion") ? params.get("motivoPrescripcion").asText() : null);
            if (params.hasNonNull("inicioAdministracion")) {
                Date iniAd = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(params.hasNonNull("inicioAdministracion") ? params.get("inicioAdministracion").asText() : null);
                reaccion.setInicioAdministracion(iniAd);
            }
            if (params.hasNonNull("finAdministracion")) {
                Date finAd = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(params.hasNonNull("finAdministracion") ? params.get("finAdministracion").asText() : null);
                reaccion.setFinAdministracion(finAd);
            }

            RespuestasInfoMedicamento_Enum info = null;

            info = obtenerEnum(params.hasNonNull("medicamentoSuspendido") ? params.get("medicamentoSuspendido").asText().toUpperCase() : null);
            reaccion.setMedicamentoSuspendido(info != null ? info.getValue() : null);

            info = obtenerEnum(params.hasNonNull("desaparecioReaccion") ? params.get("desaparecioReaccion").asText().toUpperCase() : null);
            reaccion.setDesaparecioReaccion(info != null ? info.getValue() : null);

            info = obtenerEnum(params.hasNonNull("disminuyoDosis") ? params.get("disminuyoDosis").asText().toUpperCase() : null);
            reaccion.setDisminuyoDosis(info != null ? info.getValue() : null);

            reaccion.setCuanto(params.hasNonNull("cuanto") ? params.get("cuanto").asDouble() : null);

            info = obtenerEnum(params.hasNonNull("cambioFarmacoTerapia") ? params.get("cambioFarmacoTerapia").asText().toUpperCase() : null);
            reaccion.setCambioFarmacoTerapia(info != null ? info.getValue() : null);
            reaccion.setCual(params.hasNonNull("cual") ? params.get("cual").asText() : null);

            info = obtenerEnum(params.hasNonNull("reaparecioReaccionReadministrar") ? params.get("reaparecioReaccionReadministrar").asText().toUpperCase() : null);
            reaccion.setReaparecioReaccionReadministrar(info != null ? info.getValue() : null);

            info = obtenerEnum(params.hasNonNull("persistioReaccion") ? params.get("persistioReaccion").asText().toUpperCase() : null);
            reaccion.setPersistioReaccion(info != null ? info.getValue() : null);
            reaccion.setHistoriaClinica(params.hasNonNull("historiaClinica") ? params.get("historiaClinica").asText() : null);

            String infoLab = params.hasNonNull("tipoInformeLab") ? params.get("tipoInformeLab").asText().toUpperCase() : null;
            TipoInforme tipoLab = tipoInformeList != null ? tipoInformeList.stream().filter(p -> p.getDescripcion().toUpperCase().equals(infoLab)).findAny().orElse(null) : null;
            reaccion.setTipoInformeLab(tipoLab != null ? tipoLab.getIdTipoInforme().toString() : null);

            reaccion.setOrigenLab(params.hasNonNull("origenLab") ? params.get("origenLab").asText() : null);

            String infoPro = params.hasNonNull("tipoInformeProf") ? params.get("tipoInformeProf").asText().toUpperCase() : null;
            TipoInforme tipoPro = tipoInformeList != null ? tipoInformeList.stream().filter(p -> p.getDescripcion().toUpperCase().equals(infoPro)).findAny().orElse(null) : null;
            reaccion.setTipoInformeProf(tipoPro != null ? tipoPro.getIdTipoInforme().toString() : null);

            String lab = params.hasNonNull("tipoInformeLab") ? params.get("tipoInformeLab").asText().toUpperCase() : null;
            Origen origenLab = origenList != null ? origenList.stream().filter(p -> p.getDescripcion().toUpperCase().equals(lab)).findAny().orElse(null) : null;
            reaccion.setOrigenLab(origenLab != null ? origenLab.getIdOrigen().toString() : null);

            String pro = params.hasNonNull("tipoInformeLab") ? params.get("tipoInformeLab").asText().toUpperCase() : null;
            Origen origenPro = origenList != null ? origenList.stream().filter(p -> p.getDescripcion().toUpperCase().equals(pro)).findAny().orElse(null) : null;
            reaccion.setTipoInformeLab(origenPro != null ? origenPro.getIdOrigen().toString() : null);

            reaccion.setOrigenProf(params.hasNonNull("origenProf") ? params.get("origenProf").asText() : null);
            reaccion.setRfcInformante(params.hasNonNull("rfcInformante") ? params.get("rfcInformante").asText() : null);
            reaccion.setCurpInformante(params.hasNonNull("curpInformante") ? params.get("curpInformante").asText() : null);
            reaccion.setNumeroInformante(params.hasNonNull("numeroInformante") ? params.get("numeroInformante").asText() : null);
            reaccion.setCedula(params.hasNonNull("cedula") ? params.get("cedula").asText() : null);
            reaccion.setNombre(params.hasNonNull("nombre") ? params.get("nombre").asText() : null);
            reaccion.setApellidoPat(params.hasNonNull("apellidoPat") ? params.get("apellidoPat").asText() : null);
            reaccion.setApellidoMat(params.hasNonNull("apellidoMat") ? params.get("apellidoMat").asText() : null);
            reaccion.setTelefono(params.hasNonNull("telefono") ? params.get("telefono").asText() : null);
            reaccion.setCorreoElectronico(params.hasNonNull("correoElectronico") ? params.get("correoElectronico").asText() : null);
            reaccion.setCalle(params.hasNonNull("calle") ? params.get("calle").asText() : null);
            reaccion.setNumeroExt(params.hasNonNull("numeroExt") ? params.get("numeroExt").asText() : null);
            reaccion.setNumeroInt(params.hasNonNull("numeroInt") ? params.get("numeroInt").asText() : null);
            reaccion.setColonia(params.hasNonNull("colonia") ? params.get("colonia").asText() : null);
            reaccion.setLocalidad(params.hasNonNull("localidad") ? params.get("localidad").asText() : null);
            reaccion.setMunicipio(params.hasNonNull("municipio") ? params.get("municipio").asText() : null);
            reaccion.setEstado(params.hasNonNull("estado") ? params.get("estado").asText() : null);
            reaccion.setPais(params.hasNonNull("pais") ? params.get("pais").asText() : null);
            reaccion.setCp(params.hasNonNull("cp") ? params.get("cp").asText() : null);
            reaccion.setPublicarInformacion(params.hasNonNull("publicarInformacion") ? params.get("publicarInformacion").asInt() : null);
            reaccion.setRiesgo(params.hasNonNull("riesgo") ? params.get("riesgo").asText() : null);
            List<MedicamentoConcomitante> insus = new ArrayList<>();
            if (params.hasNonNull("insumos")) {
                for (Iterator<JsonNode> iter = params.get("insumos").elements(); iter.hasNext();) {
                    JsonNode node = iter.next();
                    Medicamento item = mediService.obtenerMedicaByClave(node.hasNonNull("clave") ? node.get("clave").asText() : null);
                    if (item != null) {
                        MedicamentoConcomitante medc = new MedicamentoConcomitante();
                        medc.setIdConcomitante(Comunes.getUUID());
                        medc.setIdReaccion(reaccion.getIdReaccion());
                        medc.setIdInsumo(item.getIdMedicamento());
                        medc.setClave(item.getClaveInstitucional());
                        medc.setDosis(node.hasNonNull("dosis") ? node.get("dosis").asDouble() : null);
                        if (node.hasNonNull("inicioTratamiento")) {
                            Date start = new SimpleDateFormat("dd/MM/yyyy").parse(node.hasNonNull("inicioTratamiento") ? node.get("inicioTratamiento").asText() : null);
                            medc.setInicioTratamiento(start);
                        }
                        if (node.hasNonNull("finTratamiento")) {
                            Date end = new SimpleDateFormat("dd/MM/yyyy").parse(node.hasNonNull("finTratamiento") ? node.get("finTratamiento").asText() : null);
                            medc.setFinTratamiento(end);
                        }
                        medc.setMotivoPrescripcion(node.hasNonNull("motivoPrescripcion") ? node.get("motivoPrescripcion").asText() : null);
                        if (node.hasNonNull("insertFecha")) {
                            Date insert = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(node.get("insertFecha").asText());
                            medc.setInsertFecha(insert);
                        }
                        if (params.hasNonNull("insertIdUsuario")) {
                            medc.setInsertIdUsuario(params.get("insertIdUsuario").asText());
                        }
                        if (node.hasNonNull("updateFecha")) {
                            Date update = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(node.get("updateFecha").asText());
                            medc.setUpdateFecha(update);
                        }
                        if (params.hasNonNull("updateIdUsuario")) {
                            medc.setUpdateIdUsuario(params.get("updateIdUsuario").asText());
                        }
                        insus.add(medc);
                    }
                }
                reaccion.setInsumos(insus);
            }
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al parsear", ex);
        }
        return reaccion;
    }

    private void InicializaCatalogos() {
        try {
            EstatusReaccion estatusReac = new EstatusReaccion();
            TipoReaccion tipoReac = new TipoReaccion();
            Consecuencia cons = new Consecuencia();
            TipoInforme info = new TipoInforme();
            estatusReaccionList = reaccionService.obtenerLista(estatusReac);
            tipoReaccionList = tipoReaccionService.obtenerLista(tipoReac);
            sustanciaActivaList = sustanciaService.obtenerTodo();
            viaList = viaService.obtenerTodo();
            unidadList = unidadService.obtenerTodo();
            consecuenciaList = consecuenciaService.obtenerLista(cons);
            tipoInformeList = tipoInformeService.obtenerLista(info);
            origenList = origenService.obtenerLista(Constantes.INACTIVO);
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al cargar los catalogos", ex);
        }
    }

    private boolean tokenEnabled() {
        Config config = new Config();
        config.setActiva(Constantes.ACTIVO);
        try {
            configList = configService.obtenerLista(config);
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString("sys.config.err"), ex);
            return true;
        }
        return Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_WS_SIAM_TOKEN) == 1;
    }

    private Usuario obtenerUsuario(String username) {
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(username);
        try {
            usuario = this.usuarioService.verificaSiExisteUser(usuario);
        } catch (Exception ex) {
            usuario = null;
            LOGGER.error("{}.obtenerUsuario(): {}", this.getClass().getCanonicalName(), ex.getMessage());
        }
        return usuario;
    }

    private RespuestasInfoMedicamento_Enum obtenerEnum(String cadena) {
        RespuestasInfoMedicamento_Enum resp = null;
        try {
            if (cadena != null) {
                cadena = cadena.replace(" ", "_");
                boolean numero = cadena.matches("[+-]?\\d*(\\.\\d+)?");

                resp = numero ? RespuestasInfoMedicamento_Enum.values()[Integer.parseInt(cadena) - 1] : RespuestasInfoMedicamento_Enum.valueOf(cadena.toUpperCase());
            }
        } catch (NumberFormatException ex) {
            resp = null;
            LOGGER.error("Ocurrio un error al obtener el enum RespuestasInfoMedicamento", ex);
        }
        return resp;
    }

}
