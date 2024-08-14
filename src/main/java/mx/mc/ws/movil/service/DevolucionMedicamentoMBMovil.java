/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.ws.movil.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import mx.mc.enums.EstatusDevolucion_Enum;
import mx.mc.init.Constantes;
import mx.mc.init.CustomWebSecurityConfigurerAdapter;
import mx.mc.model.CodigoInsumo;
import mx.mc.model.Config;
import mx.mc.model.DevolucionMinistracionDetalleExtended;
import mx.mc.model.DevolucionMinistracionExtended;
import mx.mc.model.Paciente;
import mx.mc.model.SurtimientoEnviado_Extend;
import mx.mc.model.SurtimientoInsumo_Extend;
import mx.mc.model.TipoMotivo;
import mx.mc.model.Usuario;
import mx.mc.model.VistaRecepcionMedicamento;
import mx.mc.service.ConfigService;
import mx.mc.service.DevolucionMinistracionService;
import mx.mc.service.PacienteService;
import mx.mc.service.SurtimientoService;
import mx.mc.service.TipoMotivoService;
import mx.mc.service.UsuarioService;
import mx.mc.util.CodigoBarras;
import mx.mc.util.Comunes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 *
 * @author Ulai
 */
@Path("devolucion")
public class DevolucionMedicamentoMBMovil extends SpringBeanAutowiringSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(DevolucionMedicamentoMBMovil.class);
    protected static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    protected static final ResourceBundle PARAMETERS = ResourceBundle.getBundle(Constantes.GLOBAL_PARAM);
    private List<Config> configList;
    private boolean status;
    private boolean edit;
    private boolean isNew;
    private String cadenaBusqueda;
    private String idSurtimientoInsumo;
    private boolean delete;
    private String folio;
    private String filter;
    private Paciente pacienteExtended;
    private int numeroRegistros;
    private int numeroMedDetalles;
    private String codigoBarras;
    int cantidad = 0;
    int restante = 0;
    //datos del codigo de barras del medicamento
    private Paciente pacienteDev;
    private String claveMedicamento;
    private String loteMedicamento;
    private Date caducidadMedicamento;
    private int tipoDevolucion;
    private int cantDevolver;
    private int cantidadMaxima;
    boolean surtimiento = false;
    boolean devolucion = false;
    boolean caducidad = false;
    boolean lote = false;
    boolean clave = false;
    boolean cant = false;
    boolean checkCant = false;
    boolean conforme = false;
    boolean eliminar = false;
    boolean check = false;
    Integer motivo = 0;

    @Autowired
    private ConfigService configService;

    @Autowired
    private SurtimientoService surtimientoService;
    private List<SurtimientoInsumo_Extend> surtInsumoExtList;

    @Autowired
    private TipoMotivoService tipoMotivoService;
    private VistaRecepcionMedicamento viewRecepcionMed;

    @Autowired
    private DevolucionMinistracionService devolucionMiniExtService;
    private DevolucionMinistracionExtended devMinistracionExt;
    private List<DevolucionMinistracionExtended> devolucionList;

    private List<DevolucionMinistracionDetalleExtended> devolucionDetalleExtList;

    @Autowired
    PacienteService pacienteService;

    @Autowired
    private UsuarioService usuarioService;

    @PostConstruct
    public void init() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    /**
     * Crea una nueva instancia de la clase
     */
    public DevolucionMedicamentoMBMovil() {
        //No code needed in constructor
    }

    /**
     * Equivalente del método SesionMB.obtenerDatosSistema() Obtiene la lista de
     * configuraciones del Sistema
     */
    private void obtenerDatosSistema() {
        LOGGER.debug("mx.mc.ws.movil.service.DevolucionMedicamentoMBMovil.obtenerDatosSistema()");
        Config c = new Config();
        c.setActiva(Constantes.ACTIVO);
        try {
            configList = configService.obtenerLista(c);
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString("sys.config.err"), ex);
        }
    }

    @POST
    @Path("ObtenerDevoluciones")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerSutimientoEnviado(String filtrosJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode resultado = mapper.createObjectNode();
        JsonNode params = mapper.readTree(filtrosJson);
        if (!params.hasNonNull("usuario") || !params.hasNonNull("pass") || !params.hasNonNull("cadenaBusqueda") || !params.hasNonNull("registroInicio") || !params.hasNonNull("maxPorPagina") || !params.hasNonNull("tipoPrescripcion")) {
            resultado.put("estatus", "ERROR");
            resultado.put("mensaje", RESOURCES.getString("webservice.err.invalidRequest"));
            return Response.ok(resultado.toString()).build();
        }
        String usuario = params.get("usuario").asText();
        String pass = params.get("pass").asText();
        cadenaBusqueda = params.get("cadenaBusqueda").asText();
        int startingAt = params.get("registroInicio").asInt();
        int maxPerPage = params.get("maxPorPagina").asInt();
        JsonNode tipoNode = params.get("tipoPrescripcion");
        List<String> tipo = mapper.readValue(tipoNode.toString(), new TypeReference<List<String>>() {});
        Usuario usuarioSeleccionado;
        Usuario usuarioParam = new Usuario();
        usuarioParam.setNombreUsuario(usuario);
        try {
            usuarioSeleccionado = this.usuarioService.verificaSiExisteUser(usuarioParam);
        } catch (Exception ex) {
            resultado.put("estatus", "EXCEPCION");
            resultado.put("mensaje", ex.getMessage());
            LOGGER.error("{}.obtenerSurtimientos(): {}", this.getClass().getCanonicalName(), ex.getMessage());
            return Response.ok(resultado.toString()).build();
        }
        if (usuarioSeleccionado != null) {
            if (!CustomWebSecurityConfigurerAdapter.customPasswordEncoder().matches(pass, usuarioSeleccionado.getClaveAcceso())) {
                resultado.put("estatus", "ERROR");
                resultado.put("mensaje", RESOURCES.getString("devolmedica.warn.sinPermisos"));
                return Response.ok(resultado.toString()).build();
            } else {
                try {
                    if (tipo.isEmpty()) {
                        tipo = null;
                    }
                    devolucionList = devolucionMiniExtService.obtenerListaDevMedMinistracion(usuarioSeleccionado.getIdEstructura(), cadenaBusqueda, startingAt, maxPerPage, tipo);
                    numeroRegistros = devolucionList.size();
                } catch (Exception ex) {
                    LOGGER.error("Error al obtener los surtimientos Enviados: {}", ex.getMessage());
                    resultado.put("estatus", "ERROR");
                    resultado.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
                    return Response.ok(resultado.toString()).build();
                }
            }
        } else {
            resultado.put("estatus", "ERROR");
            resultado.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
            return Response.ok(resultado.toString()).build();
        }
        resultado.put("estatus", "OK");
        resultado.put("totalRegistros", numeroRegistros);
        ArrayNode registrosNode = mapper.valueToTree(devolucionList);
        resultado.set("registros", registrosNode);
        return Response.ok(resultado.toString()).build();
    }

    @POST
    @Path("autoCompletePacientes")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response autoCompletePacientes(String filtrosJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode resultado = mapper.createObjectNode();
        JsonNode params = mapper.readTree(filtrosJson);
        List<Paciente> listaPacientes = new ArrayList<>();
        if (!params.hasNonNull("usuario") || !params.hasNonNull("pass") || !params.hasNonNull("cadenaBusqueda")) {
            resultado.put("estatus", "ERROR");
            resultado.put("mensaje", RESOURCES.getString("webservice.err.invalidRequest"));
            return Response.ok(resultado.toString()).build();
        }
        String usuario = params.get("usuario").asText();
        String cadBusqueda = params.get("cadenaBusqueda").asText();

        Usuario usuarioVerifica = new Usuario();
        Usuario usuarioSeleccionado = null;
        usuarioVerifica.setNombreUsuario(usuario);
        try {
            usuarioSeleccionado = this.usuarioService.verificaSiExisteUser(usuarioVerifica);
        } catch (Exception ex) {
            resultado.put("estatus", "EXCEPCION");
            resultado.put("mensaje", ex.getMessage());
            LOGGER.error("{}.obtenerSurtimientos(): {}", this.getClass().getCanonicalName(), ex.getMessage());
        }
        if (usuarioSeleccionado != null) {
            try {
                listaPacientes = this.pacienteService.obtenerRegistrosPorCriterioDeBusqueda2(
                        cadBusqueda.trim(), Constantes.REGISTROS_PARA_MOSTRAR);
            } catch (Exception ex) {
                LOGGER.error("Error al obtener obtenerListaClavesSku : {}", ex.getMessage());
            }
        } else {
            resultado.put("estatus", "ERROR");
            resultado.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
        }

        resultado.put("estatus", "OK");
        ArrayNode registrosNode = mapper.valueToTree(listaPacientes);
        resultado.set("listaPacientes", registrosNode);
        return Response.ok(resultado.toString()).build();
    }

    @POST
    @Path("buscarPrescripcion")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPrescripcion(String filtrosJson) throws IOException {
        LOGGER.trace("Buscando coincidencias de: {}", cadenaBusqueda);
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode resultado = mapper.createObjectNode();
        codigoBarras = "";
        if (cadenaBusqueda != null) {
            try {
                List<SurtimientoEnviado_Extend> surtEnviadoList = surtimientoService.obtenerDetalleDevolucionPorIdPrescripcionExtend(viewRecepcionMed.getIdPrescripcion());
                status = Constantes.ACTIVO;
                isNew = Constantes.INACTIVO;
                edit = Constantes.INACTIVO;
                switch (viewRecepcionMed.getIdEstatusSurtimiento()) {
                    case 1:
                        edit = Constantes.ACTIVO;
                        break;
                    case 2:
                        isNew = Constantes.ACTIVO;
                    default:
                }
                numeroMedDetalles = surtEnviadoList.size();
                cadenaBusqueda = null;
            } catch (Exception e) {
                LOGGER.error("Error al buscar Medicamentos: {}", e.getMessage());
            }
        }
        return Response.ok(resultado.toString()).build();
    }

    @POST
    @Path("newDevolucion")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response newDevolucion(String filtrosJson) throws IOException {
        ObjectMapper objectMap = new ObjectMapper();
        ObjectNode resultado = objectMap.createObjectNode();
        JsonNode params = objectMap.readTree(filtrosJson);
        status = Constantes.INACTIVO;
        if (!params.hasNonNull("usuario") || !params.hasNonNull("pass") || !params.hasNonNull("folio") || !params.hasNonNull("pacienteExtended") || !params.hasNonNull("filter")) {
            resultado.put("estatus", "ERROR");
            resultado.put("mensaje", RESOURCES.getString("webservice.err.invalidRequest"));
            return Response.ok(resultado.toString()).build();
        }
        String usuario = params.get("usuario").asText();
        JsonNode pacientes = params.get("pacienteExtended");
        pacienteExtended = objectMap.readValue(pacientes.toString(), new TypeReference<Paciente>() {});
        filter = params.get("filter").asText();
        folio = params.get("folio").asText();
        Usuario existUser = new Usuario();
        Usuario usuarioSeleccionado = null;
        existUser.setNombreUsuario(usuario);
        try {
            usuarioSeleccionado = this.usuarioService.verificaSiExisteUser(existUser);
        } catch (Exception ex) {
            LOGGER.error("{}.obtenerSurtimientos(): {}", this.getClass().getCanonicalName(), ex.getMessage());
            resultado.put("mensaje", ex.getMessage());
            resultado.put("estatus", "EXCEPCION");
        }
        if (usuarioSeleccionado != null) {
            try {
                if (pacienteExtended != null && pacienteExtended.getIdEstructura() != null && pacienteExtended.getNombreCompleto() != null) {
                    pacienteDev = new Paciente(pacienteExtended.getIdEstructura(),
                            ((pacienteExtended.getNombreCompleto() != null ? pacienteExtended.getNombreCompleto() : "") + " "
                            + (pacienteExtended.getApellidoPaterno() != null ? pacienteExtended.getApellidoPaterno() : "") + " "
                            + (pacienteExtended.getApellidoMaterno() != null ? pacienteExtended.getApellidoMaterno() : "")));
                }
                devMinistracionExt = null;
                devMinistracionExt = devolucionMiniExtService.obtenerByFolioSurtimientoForDev(folio, pacienteDev, filter);
                status = true;
                if (devMinistracionExt != null) {
                    surtInsumoExtList = surtimientoService.detalleSurtimientoInsumoExtRecepMedi(devMinistracionExt.getIdSurtimiento());
                    status = true;
                } else {
                    resultado.put("estatus", "ERROR");
                    resultado.put("mensaje", RESOURCES.getString("devolmedica.warn.presNoEncontr"));
                    status = false;
                }

            } catch (Exception ex) {
                LOGGER.trace("Ocurrio una exception al buscar el Folio de la Prescripción: {}", ex.getMessage());
                resultado.put("estatus", "ERROR");
                resultado.put("mensaje", RESOURCES.getString("devolmedica.err.buscarPrescrip"));
            }
        } else {
            resultado.put("estatus", "ERROR");
            resultado.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
        }
        if (status) {
            resultado.put("estatus", "OK");
            JsonNode devMinistracionExtNode = objectMap.valueToTree(devMinistracionExt);
            resultado.set("devMinistracionExt", devMinistracionExtNode);
            JsonNode pacienteExtendedNode = objectMap.valueToTree(pacienteExtended);
            resultado.set("paciente", pacienteExtendedNode);
            ArrayNode surtInsumoExtListNode = objectMap.valueToTree(surtInsumoExtList);
            resultado.set("surtInsumoExtList", surtInsumoExtListNode);
            JsonNode tipoMotivoListNode = objectMap.valueToTree(obtieneListaMotivo());
            resultado.set("tipoMotivoList", tipoMotivoListNode);
        }
        return Response.ok(resultado.toString()).build();
    }

    @POST
    @Path("buscarFoliorPaciente")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarFoliorPaciente(String filtrosJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode resultado = mapper.createObjectNode();
        JsonNode params = mapper.readTree(filtrosJson);
        status = Constantes.INACTIVO;
        if (!params.hasNonNull("usuario") || !params.hasNonNull("pass") || !params.hasNonNull("folio") || !params.hasNonNull("pacienteExtended") || !params.hasNonNull("filter")) {
            resultado.put("estatus", "ERROR");
            resultado.put("mensaje", RESOURCES.getString("webservice.err.invalidRequest"));
            return Response.ok(resultado.toString()).build();
        }
        folio = params.get("folio").asText();
        filter = "Paciente";
        String usuarioAsText = params.get("usuario").asText();
        JsonNode pacientes = params.get("pacienteExtended");
        pacienteExtended = mapper.readValue(pacientes.toString(), new TypeReference<Paciente>() {});        
        Usuario usuarioParam = new Usuario();
        Usuario usuarioSeleccionado = null;
        usuarioParam.setNombreUsuario(usuarioAsText);
        try {
            usuarioSeleccionado = this.usuarioService.verificaSiExisteUser(usuarioParam);
        } catch (Exception ex) {
            LOGGER.error("{}.obtenerSurtimientos(): {}", this.getClass().getCanonicalName(), ex.getMessage());
            resultado.put("estatus", "EXCEPCION");
            resultado.put("mensaje", ex.getMessage());
        }
        if (usuarioSeleccionado != null) {
            try {
                if (pacienteExtended != null && pacienteExtended.getIdEstructura() != null && pacienteExtended.getNombreCompleto() != null) {
                    pacienteDev = new Paciente(pacienteExtended.getIdEstructura(),
                            ((pacienteExtended.getNombreCompleto() != null ? pacienteExtended.getNombreCompleto() : "") + " "
                            + (pacienteExtended.getApellidoPaterno() != null ? pacienteExtended.getApellidoPaterno() : "") + " "
                            + (pacienteExtended.getApellidoMaterno() != null ? pacienteExtended.getApellidoMaterno() : "")));
                }
                devolucionList = devolucionMiniExtService.obtenerByFolioSurtimientoForDevList(folio, pacienteDev, filter);
                if (devolucionList != null && !devolucionList.isEmpty()) {
                    status = true;
                } else {
                    resultado.put("estatus", "ERROR");
                    resultado.put("mensaje", "No se encontraron surtimientos para devolver. Validé folio para una devolución parcial.");
                    return Response.ok(resultado.toString()).build();
                }
            } catch (Exception ex) {
                LOGGER.trace("Ocurrio una exception al buscar el Folio de la Prescripción: {}", ex.getMessage());
                resultado.put("estatus", "ERROR");
                resultado.put("mensaje", RESOURCES.getString("devolmedica.err.buscarPrescrip"));
                return Response.ok(resultado.toString()).build();
            }
        } else {
            resultado.put("estatus", "ERROR");
            resultado.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
            return Response.ok(resultado.toString()).build();
        }
        if (status) {
            resultado.put("estatus", "OK");
            ArrayNode surtInsumoExtListNode = mapper.valueToTree(devolucionList);
            resultado.set("devolucionList", surtInsumoExtListNode);
        }
        return Response.ok(resultado.toString()).build();
    }

    private boolean tratarCodigoDeBarrasMedicamento(String codigo) {
        boolean resp = Constantes.INACTIVO;
        try {
            obtenerDatosSistema();
            Boolean isCodificacionGS1 = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_CODIGOS_GS1) == 1;
            CodigoInsumo codi = CodigoBarras.parsearCodigoDeBarras(codigo, isCodificacionGS1);
            if (codi != null) {
                claveMedicamento = codi.getClave();
                loteMedicamento = codi.getLote();
                caducidadMedicamento = codi.getFecha();
                resp = Constantes.ACTIVO;
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo tratarCodigoDeBarras :: {}", e.getMessage());
        }
        return resp;
    }

    @POST
    @Path("obtenerDetalleSurtimiento")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerDetalleSurtimiento(String filtrosJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode resultado = mapper.createObjectNode();
        JsonNode params = mapper.readTree(filtrosJson);
        status = Constantes.INACTIVO;
        if (!params.hasNonNull("usuario") || !params.hasNonNull("pass") || !params.hasNonNull("item")) {
            resultado.put("estatus", "ERROR");
            resultado.put("mensaje", RESOURCES.getString("webservice.err.invalidRequest"));
            return Response.ok(resultado.toString()).build();
        }
        String usuario = params.get("usuario").asText();
        JsonNode pacientes = params.get("item");
        devMinistracionExt = mapper.readValue(pacientes.toString(), new TypeReference<DevolucionMinistracionExtended>() {});
        Usuario param = new Usuario();
        Usuario usuarioSeleccionado = null;
        param.setNombreUsuario(usuario);
        try {
            usuarioSeleccionado = this.usuarioService.verificaSiExisteUser(param);
        } catch (Exception ex) {
            resultado.put("estatus", "EXCEPCION");
            resultado.put("mensaje", ex.getMessage());
            LOGGER.error("{}.obtenerSurtimientos(): {}", this.getClass().getCanonicalName(), ex.getMessage());
        }
        if (usuarioSeleccionado != null) {
            try {
                codigoBarras = "";
                tipoDevolucion = 0;
                isNew = Constantes.INACTIVO;
                edit = Constantes.INACTIVO;
                switch (devMinistracionExt.getIdEstatusDevolucion()) {
                    case 2:
                    case 1:
                    case 0:
                        edit = Constantes.ACTIVO;
                        isNew = Constantes.ACTIVO;
                        break;
                    case 3:
                        edit = Constantes.ACTIVO;
                        break;
                    default:
                }
                if (!edit) {
                    tipoDevolucion = devMinistracionExt.getIdEstatusDevolucion();
                }

                surtInsumoExtList = surtimientoService.detalleSurtimientoInsumoExtRecepMedi(devMinistracionExt.getIdSurtimiento());
                devolucionDetalleExtList = surtimientoService.obtenerDevolucionDetalleExtPorIdSurtimiento(devMinistracionExt.getIdSurtimiento());
            } catch (Exception ex) {
                LOGGER.error("Error al obtener los Medicamentos: {}", ex.getMessage());
            }
        } else {
            resultado.put("estatus", "ERROR");
            resultado.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
        }
        if (edit) {
            resultado.put("estatus", "OK");
            JsonNode surtInsumoExtListNode = mapper.valueToTree(surtInsumoExtList);
            resultado.set("surtInsumoExtList", surtInsumoExtListNode);
            ArrayNode devolucionDetalleExtListNode = mapper.valueToTree(devolucionDetalleExtList);
            resultado.set("devolucionDetalleExtList", devolucionDetalleExtListNode);
            JsonNode tipoMotivoListNode = mapper.valueToTree(obtieneListaMotivo());
            resultado.set("tipoMotivoList", tipoMotivoListNode);
        }
        return Response.ok(resultado.toString()).build();
    }

    @POST
    @Path("addMedicamentoList")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addMedicamentoList(String filtrosJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode resultado = mapper.createObjectNode();
        JsonNode params = mapper.readTree(filtrosJson);
        if (!params.hasNonNull("usuario") || !params.hasNonNull("pass") || !params.hasNonNull("codigoBarras") || !params.hasNonNull("cantidad") || !params.hasNonNull("surtInsumoExtList") || !params.hasNonNull("devMinistracionExt")) {
            resultado.put("estatus", "ERROR");
            resultado.put("mensaje", RESOURCES.getString("webservice.err.invalidRequest"));
            return Response.ok(resultado.toString()).build();
        }

        String usuario = params.get("usuario").asText();
        codigoBarras = params.get("codigoBarras").asText();
        conforme = params.get("conforme").asBoolean();
        tipoDevolucion = params.get("idTipoMotivo").asInt();
        cantDevolver = params.get("cantidad").asInt();
        eliminar = params.get("eliminaCodigoBarras").asBoolean();
        JsonNode surtInsumoExtListJson = params.get("surtInsumoExtList");
        surtInsumoExtList = mapper.readValue(surtInsumoExtListJson.toString(), new TypeReference<List<SurtimientoInsumo_Extend>>() {});
        JsonNode devolucionDetalleExtListJson = params.get("devolucionDetalleExtList");
        devolucionDetalleExtList = mapper.readValue(devolucionDetalleExtListJson.toString(), new TypeReference<List<DevolucionMinistracionDetalleExtended>>() {});
        JsonNode devMinistracionExtJson = params.get("devMinistracionExt");
        devMinistracionExt = mapper.readValue(devMinistracionExtJson.toString(), new TypeReference<DevolucionMinistracionExtended>() {});
        Usuario usuarioParam = new Usuario();
        Usuario usuarioAplica = null;
        usuarioParam.setNombreUsuario(usuario);
        try {
            usuarioAplica = this.usuarioService.verificaSiExisteUser(usuarioParam);
        } catch (Exception ex) {
            resultado.put("estatus", "EXCEPCION");
            resultado.put("mensaje", ex.getMessage());
            LOGGER.error("{}.obtenerSurtimientos(): {}", this.getClass().getCanonicalName(), ex.getMessage());
        }
        if (usuarioAplica != null) {
            try {
                if (tratarCodigoDeBarrasMedicamento(codigoBarras)) {
                    if (cantDevolver < 1) {
                        cantDevolver = 1;
                    }
                    surtimiento = false;
                    devolucion = false;
                    surtInsumoExtList.forEach(item -> {
                        if (item.getClaveInstitucional().equals(claveMedicamento)) {
                            surtimiento = true;
                        }
                    });

                    devolucionDetalleExtList.forEach(item -> {
                        if (item.getClaveInstitucional().equals(claveMedicamento)) {
                            devolucion = true;
                        }
                    });

                    if (surtimiento) {
                        if (devolucion && devolucionDetalleExtList != null) {
                            exit:
                            for (int index = 0; index < devolucionDetalleExtList.size(); index++) {
                                DevolucionMinistracionDetalleExtended devMinExt = devolucionDetalleExtList.get(index);
                                if (devMinExt.getClaveInstitucional().equals(claveMedicamento)
                                        && devMinExt.getSurtimientoEnviadoExtList() != null) {
                                    cantidad = devMinExt.getCantidadMaxima();
                                    surtimiento = true;
                                    devolucion = true;
                                    for (SurtimientoEnviado_Extend surt_env_ext : devMinExt.getSurtimientoEnviadoExtList()) {
                                        if (surt_env_ext.getLote().equals(loteMedicamento)) {
                                            surtimiento = false;
                                            lote = true;
                                            devolucion = false;
                                            if (surt_env_ext.getCaducidad().equals(caducidadMedicamento)) {
                                                surtimiento = false;
                                                devolucion = false;
                                                caducidad = true;
                                                if (surt_env_ext.getTipoDevolucion() == null) {
                                                    devMinExt.setCantidadDevuelta(0);
                                                    surt_env_ext.setTipoDevolucion(tipoDevolucion);
                                                }
                                                if (surt_env_ext.getTipoDevolucion() == tipoDevolucion) {
                                                    if (!eliminar) {
                                                        if ((devMinExt.getCantidadDevuelta() + cantDevolver) <= devMinExt.getCantidadMaxima()) {
                                                            if ((surt_env_ext.getCantidadDevolver() + cantDevolver) <= surt_env_ext.getCantidadRecibido()) {
                                                                surt_env_ext.setCantidadDevolver(surt_env_ext.getCantidadDevolver() + cantDevolver);
                                                                devMinExt.setCantidadDevuelta(devMinExt.getCantidadDevuelta() + cantDevolver);
                                                                JsonNode devMinistracionExtNode = mapper.valueToTree(devolucionDetalleExtList);
                                                                resultado.set("devolucionDetalleExtList", devMinistracionExtNode);
                                                                resultado.put("estatus", "OK");
                                                                break exit;
                                                            } else {
                                                                resultado.put("estatus", "ERROR");
                                                                resultado.put("mensaje", "Solo se pueden devolver: " + surt_env_ext.getCantidadRecibido() + " medicamentos para el lote:" + loteMedicamento);
                                                            }
                                                        } else {
                                                            resultado.put("estatus", "ERROR");
                                                            resultado.put("mensaje", "Solo se pueden devolver: " + surt_env_ext.getCantidadRecibido() + " medicamentos para el lote:" + loteMedicamento);
                                                        }
                                                    } else {
                                                        if ((devMinExt.getCantidadDevuelta() - cantDevolver) > 0) {
                                                            if ((surt_env_ext.getCantidadDevolver() - cantDevolver) > 0) {
                                                                surt_env_ext.setCantidadDevolver(surt_env_ext.getCantidadDevolver() - cantDevolver);
                                                                devMinExt.setCantidadDevuelta(devMinExt.getCantidadDevuelta() - cantDevolver);
                                                                resultado.put("estatus", "OK");
                                                                JsonNode devMinisExtNode = mapper.valueToTree(devolucionDetalleExtList);
                                                                resultado.set("devolucionDetalleExtList", devMinisExtNode);
                                                                delete = false;
                                                                break exit;
                                                            } else {
                                                                idSurtimientoInsumo = surt_env_ext.getIdSurtimientoInsumo();
                                                                delete = true;
                                                            }
                                                        } else {
                                                            idSurtimientoInsumo = surt_env_ext.getIdSurtimientoInsumo();
                                                            delete = true;

                                                        }
                                                    }
                                                } else {
                                                    if (!eliminar) {
                                                        if ((devMinExt.getCantidadDevuelta() + cantDevolver) <= devMinExt.getCantidadMaxima()) {
                                                            if ((surt_env_ext.getCantidadDevolver() + cantDevolver) <= surt_env_ext.getCantidadRecibido()) {
                                                                devMinExt.setCantidadDevuelta(devMinExt.getCantidadDevuelta() + cantDevolver);
                                                                SurtimientoEnviado_Extend detall = new SurtimientoEnviado_Extend();
                                                                detall.setLote(loteMedicamento);
                                                                detall.setCantidadDevolver(cantDevolver);
                                                                detall.setCaducidad(caducidadMedicamento);
                                                                detall.setConforme(conforme);
                                                                detall.setCantidadEnviado(surt_env_ext.getCantidadEnviado());
                                                                detall.setCantidadRecibido(surt_env_ext.getCantidadRecibido());
                                                                detall.setIdInsumo(surt_env_ext.getIdInsumo());
                                                                detall.setIdInventarioSurtido(surt_env_ext.getIdInventarioSurtido());
                                                                detall.setIdSurtimientoEnviado(surt_env_ext.getIdSurtimientoEnviado());
                                                                detall.setIdSurtimientoInsumo(surt_env_ext.getIdSurtimientoInsumo());
                                                                detall.setTipoDevolucion(tipoDevolucion);
                                                                devMinExt.getSurtimientoEnviadoExtList().add(detall);
                                                                resultado.put("estatus", "OK");
                                                                JsonNode devMinistracionExtNode = mapper.valueToTree(devolucionDetalleExtList);
                                                                resultado.set("devolucionDetalleExtList", devMinistracionExtNode);
                                                                break exit;
                                                            } else {
                                                                resultado.put("estatus", "ERROR");
                                                                resultado.put("mensaje", "Solo se pueden devolver: " + surt_env_ext.getCantidadRecibido() + " medicamentos para el lote:" + loteMedicamento);
                                                            }
                                                        } else {
                                                            resultado.put("estatus", "ERROR");
                                                            resultado.put("mensaje", "Solo se pueden devolver: " + surt_env_ext.getCantidadRecibido() + " medicamentos para el lote:" + loteMedicamento);
                                                        }
                                                    } else if (!delete) {
                                                            resultado.put("estatus", "ERROR");
                                                            resultado.put("mensaje", "No se encontró nungún registro con el motivo seleccionado");
                                                    }
                                                }
                                            }
                                        }
                                    }

                                }
                                if (delete && eliminar) {
                                    for (int i = 0; i < devMinExt.getSurtimientoEnviadoExtList().size(); i++) {
                                        SurtimientoEnviado_Extend surtEnvExtRemove = devMinExt.getSurtimientoEnviadoExtList().get(i);
                                        if (surtEnvExtRemove.getIdSurtimientoInsumo().equals(idSurtimientoInsumo)
                                                && surtEnvExtRemove.getLote().equals(loteMedicamento)
                                                && surtEnvExtRemove.getTipoDevolucion() == tipoDevolucion) {
                                            check = true;
                                            devMinExt.setCantidadDevuelta(devMinExt.getCantidadDevuelta() - cantDevolver);
                                            devMinExt.getSurtimientoEnviadoExtList().remove(i);
                                            break;
                                        }

                                    }
                                    if (devMinExt.getSurtimientoEnviadoExtList().isEmpty()) {
                                        devolucionDetalleExtList.remove(index);
                                    }
                                    if (!check) {
                                        resultado.put("estatus", "ERROR");
                                        resultado.put("mensaje", "No se encontró nungún registro con el motivo seleccionado");
                                        surtimiento = false;
                                        devolucion = false;
                                    } else {
                                        resultado.put("estatus", "OK");
                                        JsonNode devMinistracionExtNode = mapper.valueToTree(devolucionDetalleExtList);
                                        resultado.set("devolucionDetalleExtList", devMinistracionExtNode);
                                        surtimiento = false;
                                        devolucion = false;
                                    }
                                }
                                if (lote && !caducidad) {
                                    resultado.put("estatus", "ERROR");
                                    resultado.put("mensaje", "La caducidad no es correcta para el medicamento: " + claveMedicamento);
                                }

                                if (!lote) {
                                    resultado.put("estatus", "ERROR");
                                    resultado.put("mensaje", "El lote no es correcto para el medicamento: " + claveMedicamento);
                                }
                            }
                        }
                        // Agrega Lote nuevo
                        if (surtimiento && devolucion) {
                            surtimiento = false;
                            devolucion = false;
                            boolean loteCheck = false;
                            if (surtInsumoExtList != null) {
                                for (SurtimientoInsumo_Extend surte : surtInsumoExtList) {
                                    if (surte.getClaveInstitucional().equals(claveMedicamento)
                                            && surte.getSurtimientoEnviadoExtendList() != null) {
                                        for (SurtimientoEnviado_Extend surte_env : surte.getSurtimientoEnviadoExtendList()) {
                                            if (surte_env.getLote().equals(loteMedicamento)) {
                                                loteCheck = true;
                                                if (surte_env.getCaducidad().equals(caducidadMedicamento)) {
                                                    caducidad = true;
                                                    if (devolucionDetalleExtList != null) {
                                                        for (DevolucionMinistracionDetalleExtended dev_ext : devolucionDetalleExtList) {
                                                            if (surte.getClaveInstitucional().equals(dev_ext.getClaveInstitucional())) {
                                                                if (surte_env.getCantidadDevolver() == null) {
                                                                    surte_env.setCantidadDevolver(0);
                                                                }
                                                                if (dev_ext.getCantidadMaxima() == 1) {
                                                                    cantidadMaxima = dev_ext.getCantidadMaxima();
                                                                } else if ((dev_ext.getCantidadMaxima() - dev_ext.getCantidadDevuelta()) <= 0) {
                                                                    cant = true;
                                                                    resultado.put("estatus", "ERROR");
                                                                    resultado.put("mensaje", "De este medicamento ya no hay más pz por devolver");
                                                                } else {
                                                                    cantidadMaxima = dev_ext.getCantidadMaxima() - dev_ext.getCantidadDevuelta();
                                                                }
                                                                if (surte_env.getCantidadDevolver() > cantidadMaxima) {
                                                                    cantidad = cantidadMaxima;
                                                                } else {
                                                                    cantidad = surte_env.getCantidadDevolver();
                                                                }
                                                                if ((surte_env.getCantidadDevolver() + cantDevolver) <= surte_env.getCantidadEnviado()
                                                                        && (dev_ext.getCantidadDevuelta() + cantDevolver) != dev_ext.getCantidadDevuelta()) {
                                                                    surte_env.setConforme(conforme);
                                                                    surte_env.setCantidadDevolver(surte_env.getCantidadDevolver() + cantDevolver);
                                                                    dev_ext.setCantidadDevuelta(dev_ext.getCantidadDevuelta() + cantDevolver);
                                                                    surte_env.setTipoDevolucion(tipoDevolucion);
                                                                    surte_env.setTipoDevolucion(tipoDevolucion);
                                                                    dev_ext.getSurtimientoEnviadoExtList().add(surte_env);
                                                                    resultado.put("estatus", "OK");
                                                                    JsonNode devMinistracionExtNode = mapper.valueToTree(devolucionDetalleExtList);
                                                                    resultado.set("devolucionDetalleExtList", devMinistracionExtNode);
                                                                    checkCant = true;
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }

                                            if (!loteCheck) {
                                                resultado.put("estatus", "ERROR");
                                                resultado.put("mensaje", "El lote no es correcto para el medicamento: " + claveMedicamento);
                                            }

                                            if (loteCheck && !caducidad) {
                                                resultado.put("estatus", "ERROR");
                                                resultado.put("mensaje", "La caducidad no es correcta para el medicamento: " + claveMedicamento);
                                            }

                                            if (caducidad && !checkCant) {
                                                resultado.put("estatus", "ERROR");
                                                resultado.put("mensaje", "Solo se pueden devolver: " + surte_env.getCantidadRecibido() + " medicamentos para el lote:" + loteMedicamento);
                                            }
                                        }
                                    }

                                }
                            }
                        }
                        //Motivo diferente

                        if (surtimiento && !devolucion) {
                            for (int i = 0; i < surtInsumoExtList.size(); i++) {
                                SurtimientoInsumo_Extend surInsEx = surtInsumoExtList.get(i);
                                if (surInsEx.getClaveInstitucional().equals(claveMedicamento)
                                        && surInsEx.getSurtimientoEnviadoExtendList() != null) {
                                    lote = false;
                                    caducidad = false;
                                    cantidad = 0;
                                    for (int j = 0; j < surInsEx.getSurtimientoEnviadoExtendList().size(); j++) {
                                        SurtimientoEnviado_Extend surEnvExt = surInsEx.getSurtimientoEnviadoExtendList().get(j);
                                        if (surEnvExt.getLote().equals(loteMedicamento)) {
                                            lote = true;
                                            if (surEnvExt.getCaducidad().equals(caducidadMedicamento)) {
                                                caducidad = true;
                                                if (surEnvExt.getCantidadDevolver() == null) {
                                                    surEnvExt.setCantidadDevolver(0);
                                                }
                                                if ((surEnvExt.getCantidadDevolver() + cantDevolver) <= surEnvExt.getCantidadRecibido()) {
                                                    surEnvExt.setCantidadDevolver(surEnvExt.getCantidadDevolver() + cantDevolver);
                                                    cant = true;
                                                } else {
                                                    resultado.put("estatus", "ERROR");
                                                    resultado.put("mensaje", "Solo se pueden devolver: " + surEnvExt.getCantidadEnviado() + " medicamentos para el lote:" + loteMedicamento);
                                                    cant = false;
                                                }
                                                
                                                List<DevolucionMinistracionDetalleExtended> devolucionDetalleExtList2 = surtimientoService.obtenerDevolucionDetalleExtPorIdSurtimiento(devMinistracionExt.getIdSurtimiento());
                                                for (DevolucionMinistracionDetalleExtended cnsmr : devolucionDetalleExtList2) {
                                                    if (cnsmr.getClaveInstitucional().equals(claveMedicamento)) {
                                                        if (cnsmr.getCantidadMaxima() == 0 || (cnsmr.getCantidadMaxima() - cnsmr.getCantidadDevuelta()) <= 0) {
                                                            resultado.put("estatus", "ERROR");
                                                            resultado.put("mensaje", "De este medicamento ya no hay más pz por devolver.");
                                                            return Response.ok(resultado.toString()).build();
                                                        } else {
                                                            cantidadMaxima = cnsmr.getCantidadMaxima() - cnsmr.getCantidadDevuelta();
                                                        }
                                                    }
                                                }

                                                if (cant) {
                                                    if (surEnvExt.getCantidadDevolver() <= cantidadMaxima) {
                                                        cantidad = surEnvExt.getCantidadDevolver();
                                                    } else {
                                                        resultado.put("estatus", "ERROR");
                                                        resultado.put("mensaje", "De este medicamento ya no hay más pz por devolver");
                                                        return Response.ok(resultado.toString()).build();
                                                    }
                                                    DevolucionMinistracionDetalleExtended devo = new DevolucionMinistracionDetalleExtended();
                                                    devo.setCantidadDevuelta(cantidad);
                                                    devo.setCantidadMaxima(surInsEx.getCantidadRecepcion());

                                                    devo.setClaveInstitucional(claveMedicamento);
                                                    devo.setIdSurtimientoInsumo(surEnvExt.getIdSurtimientoInsumo());
                                                    devo.setNombreCorto(surInsEx.getNombreCorto());
                                                    devo.setIdInsumo(surEnvExt.getIdInsumo());
                                                    devo.setIdEstatusDevolucion(EstatusDevolucion_Enum.ENTRANSITO.getValue());
                                                    devo.setCantidad(cantidad);
                                                    devo.setIdMotivoDevolucion(tipoDevolucion);
                                                    devo.setConforme(surEnvExt.isConforme());
                                                    devo.setIdInventario(surEnvExt.getIdInventarioSurtido());

                                                    SurtimientoEnviado_Extend detall = new SurtimientoEnviado_Extend();
                                                    detall.setLote(loteMedicamento);
                                                    detall.setCantidadDevolver(cantidad);
                                                    detall.setCantidadRecibido(surEnvExt.getCantidadRecibido());
                                                    detall.setCantidadEnviado(surEnvExt.getCantidadEnviado());
                                                    detall.setIdInsumo(surEnvExt.getIdInsumo());
                                                    detall.setIdInventarioSurtido(surEnvExt.getIdInventarioSurtido());
                                                    detall.setIdSurtimientoEnviado(surEnvExt.getIdSurtimientoEnviado());
                                                    detall.setIdSurtimientoInsumo(surEnvExt.getIdSurtimientoInsumo());
                                                    detall.setConforme(conforme);
                                                    detall.setCaducidad(caducidadMedicamento);
                                                    detall.setTipoDevolucion(tipoDevolucion);

                                                    List<SurtimientoEnviado_Extend> detallList = new ArrayList<>();
                                                    detallList.add(detall);
                                                    devo.setSurtimientoEnviadoExtList(detallList);
                                                    if (devolucionDetalleExtList == null) {
                                                        devolucionDetalleExtList = new ArrayList<>();
                                                    }
                                                    devolucionDetalleExtList.add(devo);
                                                    resultado.put("estatus", "OK");
                                                    JsonNode devMinistracionExtNode = mapper.valueToTree(devolucionDetalleExtList);
                                                    resultado.set("devolucionDetalleExtList", devMinistracionExtNode);
                                                }                                                
                                            }
                                        }
                                        if (!lote) {
                                            resultado.put("estatus", "ERROR");
                                            resultado.put("mensaje", "El lote no es correcto para el medicamento: " + claveMedicamento);
                                        }
                                        if (!caducidad && lote) {
                                            resultado.put("estatus", "ERROR");
                                            resultado.put("mensaje", "La caducidad no es correcta para el medicamento: " + claveMedicamento);
                                        }
                                    }

                                }
                            }
                        }
                    } else {
                        resultado.put("estatus", "ERROR");
                        resultado.put("mensaje", "El medicamento no se encontró en el surtimiento");
                    }
                } else {
                    resultado.put("estatus", "ERROR");
                    resultado.put("mensaje", "El código no tiene el formato correcto");
                }
                codigoBarras = "";
                cantDevolver = 1;
            } catch (Exception ex) {
                LOGGER.error("Error al agregar medicamento: {}", ex.getMessage());
            }
        } else {
            resultado.put("estatus", "ERROR");
            resultado.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
        }
        return Response.ok(resultado.toString()).build();
    }

    private List<TipoMotivo> obtieneListaMotivo() {
        try {
            return tipoMotivoService.listaDevolucionEnPrescripcion();
        } catch (Exception ex) {
            LOGGER.error("Error al obtener Lista de Motivos Ajuste: {}", ex.getMessage());
        }
        return null;
    }

    @POST
    @Path("procesarMedicamentoDevolucion")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response procesarMedicamentoDevolucion(String filtrosJson) throws IOException {
        boolean resp;
        status = Constantes.INACTIVO;
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode resultado = mapper.createObjectNode();
        JsonNode params = mapper.readTree(filtrosJson);
        if (!params.hasNonNull("usuario") || !params.hasNonNull("pass") || !params.hasNonNull("devMinistracionExt") || !params.hasNonNull("devolucionDetalleExtList") || !params.hasNonNull("isNew")) {
            resultado.put("estatus", "ERROR");
            resultado.put("mensaje", RESOURCES.getString("webservice.err.invalidRequest"));
            return Response.ok(resultado.toString()).build();
        }

        String usuarioText = params.get("usuario").asText();
        isNew = params.get("isNew").asBoolean();
        JsonNode devMinistracionExtJson = params.get("devMinistracionExt");
        devMinistracionExt = mapper.readValue(devMinistracionExtJson.toString(), new TypeReference<DevolucionMinistracionExtended>() {
        });
        JsonNode devolucionDetalleExtListJson = params.get("devolucionDetalleExtList");
        devolucionDetalleExtList = mapper.readValue(devolucionDetalleExtListJson.toString(), new TypeReference<List<DevolucionMinistracionDetalleExtended>>() {
        });
        Usuario usuarioParam = new Usuario();
        Usuario usuarioSeleccionado = null;
        usuarioParam.setNombreUsuario(usuarioText);
        try {
            usuarioSeleccionado = this.usuarioService.verificaSiExisteUser(usuarioParam);
        } catch (Exception ex) {
            resultado.put("estatus", "EXCEPCION");
            resultado.put("mensaje", ex.getMessage());
            LOGGER.error("{}.obtenerSurtimientos(): {}", this.getClass().getCanonicalName(), ex.getMessage());
        }
        if (usuarioSeleccionado != null) {
            try {
                if (!devolucionDetalleExtList.isEmpty()) {
                    for (DevolucionMinistracionDetalleExtended detalle : devolucionDetalleExtList) {
                        if (detalle.getTipoDevolucion() > 0) {
                            detalle.setInsertFecha(new Date());
                            detalle.setInsertIdUsuario(usuarioSeleccionado.getIdUsuario());

                            devMinistracionExt.setFechaDevolucion(new Date());
                            devMinistracionExt.setIdMotivoDevolucion(detalle.getTipoDevolucion());
                            devMinistracionExt.setComentarios(detalle.getComentario());
                            devMinistracionExt.setIdEstatusDevolucion(EstatusDevolucion_Enum.ENTRANSITO.getValue());

                        } else {
                            resultado.put("estatus", "ERROR");
                            resultado.put("mensaje", "¿Cual es el motivo?");
                            return Response.ok(resultado.toString()).build();
                        }
                    }
                    if (isNew) {
                        devMinistracionExt.setIdDevolucionMinistracion(Comunes.getUUID());
                        devMinistracionExt.setInsertFecha(new Date());
                        devMinistracionExt.setInsertIdUsuario(usuarioSeleccionado.getIdUsuario());
                        devMinistracionExt.setIdUsuarioDevolucion(usuarioSeleccionado.getIdUsuario());

                        resp = devolucionMiniExtService.insertDevolucionExt(devMinistracionExt, devolucionDetalleExtList);
                    } else {
                        devMinistracionExt.setIdUsuarioDevolucion(usuarioSeleccionado.getIdUsuario());
                        devMinistracionExt.setUpdateFecha(new Date());
                        devMinistracionExt.setUpdateIdUsuario(usuarioSeleccionado.getIdUsuario());

                        resp = devolucionMiniExtService.actualizaDevolucionExt(devMinistracionExt, devolucionDetalleExtList);
                    }
                    if (resp) {
                        status = Constantes.ACTIVO;
                        resultado.put("estatus", "OK");
                        resultado.put("mensaje", "Se ha enviado la devolución");
                        return Response.ok(resultado.toString()).build();
                    } else {
                        resultado.put("estatus", "ERROR");
                        resultado.put("mensaje", RESOURCES.getString("devolmedica.warn.faltaMotivo"));
                        return Response.ok(resultado.toString()).build();
                    }
                } else {
                    resultado.put("estatus", "ERROR");
                    resultado.put("mensaje", "Debe agregar al menos un medicamento.");
                    return Response.ok(resultado.toString()).build();
                }

            } catch (Exception ex) {
                resultado.put("estatus", "ERROR");
                resultado.put("mensaje", "Ocurrio una Excepcion al procesar el envio:" + ex.getMessage());
                return Response.ok(resultado.toString()).build();
            }
        }
        pacienteExtended = null;
        pacienteDev = null;
        return Response.ok(resultado.toString()).build();
    }
    
    public int getNumeroRegistros() {
        return numeroRegistros;
    }

    public void setNumeroRegistros(int numeroRegistros) {
        this.numeroRegistros = numeroRegistros;
    }

    public int getNumeroMedDetalles() {
        return numeroMedDetalles;
    }

    public void setNumeroMedDetalles(int numeroMedDetalles) {
        this.numeroMedDetalles = numeroMedDetalles;
    }

    public int getCantDevolver() {
        return cantDevolver;
    }

    public void setCantDevolver(int cantDevolver) {
        this.cantDevolver = cantDevolver;
    }
    
}
