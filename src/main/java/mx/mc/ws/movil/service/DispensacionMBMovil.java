package mx.mc.ws.movil.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import mx.mc.enums.EstatusPaciente_Enum;
import mx.mc.enums.EstatusPrescripcion_Enum;
import mx.mc.enums.EstatusSurtimiento_Enum;
import mx.mc.enums.TipoAreaEstructura_Enum;
import mx.mc.enums.TipoJustificacion_Enum;
import mx.mc.enums.TipoMotivo_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.init.CustomWebSecurityConfigurerAdapter;
import mx.mc.model.CodigoInsumo;
import mx.mc.model.Config;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.Estructura;
import mx.mc.model.Inventario;
import mx.mc.model.MovimientoInventario;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.RepSurtimientoPresc;
import mx.mc.model.Surtimiento;
import mx.mc.model.SurtimientoEnviado;
import mx.mc.model.SurtimientoEnviado_Extend;
import mx.mc.model.SurtimientoInsumo;
import mx.mc.model.SurtimientoInsumo_Extend;
import mx.mc.model.Surtimiento_Extend;
import mx.mc.model.TipoCancelacion;
import mx.mc.model.TransaccionPermisos;
import mx.mc.model.Usuario;
import mx.mc.service.ConfigService;
import mx.mc.service.EntidadHospitalariaService;
import mx.mc.service.EstructuraService;
import mx.mc.service.InventarioService;
import mx.mc.service.ReportesService;
import mx.mc.service.SurtimientoInsumoService;
import mx.mc.service.SurtimientoService;
import mx.mc.service.TipoCancelacionService;
import mx.mc.service.TransaccionService;
import mx.mc.service.UsuarioService;
import mx.mc.util.CodigoBarras;
import mx.mc.util.Comunes;
import mx.mc.util.FechaUtil;
import org.cups4j.CupsClient;
import org.cups4j.CupsPrinter;
import org.cups4j.PrintJob;
import org.cups4j.PrintRequestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Implementa las interfaces REST para la Dispensación de Prescripciones
 *
 * @author Alberto Palacios
 * @version 1.0
 * @since 2018-12-11
 */
@Path("dispensacion")
public class DispensacionMBMovil extends SpringBeanAutowiringSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(DispensacionMBMovil.class);
    protected static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    protected static final ResourceBundle PARAMETERS = ResourceBundle.getBundle(Constantes.GLOBAL_PARAM);
    private List<Config> configList;

    @Context
    ServletContext servletContext;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private SurtimientoService surtimientoService;

    @Autowired
    private SurtimientoInsumoService surtimientoInsumoService;

    @Autowired
    private TransaccionService transaccionService;

    @Autowired
    private EstructuraService estructuraService;

    @Autowired
    private InventarioService inventarioService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private TipoCancelacionService tipoCancelacionService;

    @Autowired
    private ReportesService reportesService;

    @Autowired
    private EntidadHospitalariaService entidadHospitalariaService;

    @PostConstruct
    public void init() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    /**
     * Crea una nueva instancia de la clase
     */
    public DispensacionMBMovil() {
        //No code needed in constructor
    }

    /**
     * Equivalente del método DispensacionMB.consultaPermisoUsuario() El
     * usuario no se toma de la sesion, en su lugar se pasa como parámetro
     *
     * @param usuario POJO del Usuario
     * @return Lista de TransaccionPermisos
     */
    public List<TransaccionPermisos> consultaPermisoUsuario(Usuario usuario) {
        List<TransaccionPermisos> permisosList = null;
        try {
            permisosList = transaccionService.obtenerPermisosPorIdUsuario(usuario.getIdUsuario());
            if (permisosList != null && !permisosList.isEmpty()) {
                usuario.setPermisosList(permisosList);
            }
        } catch (Exception ex) {
            LOGGER.error("{}.consultaPermisoUsuario(): {} {}", this.getClass().getCanonicalName(), RESOURCES.getString("ses.obtener.datos"), ex.getMessage());
        }
        return permisosList;
    }

    /**
     * Equivalente del método DispensacionMB.obtieneServiciosQuePuedeSurtir() El
     * usuario no se toma de la sesion, en su lugar se pasa como parámetro
     *
     * @param usuario POJO del Uuario
     * @return Lista de Servicios que surte y mensaje en caso de error
     */
    private Object[] obtieneServiciosQuePuedeSurtir(Usuario usuario) {
        LOGGER.debug("{}.obtieneServiciosQuePuedeSurtir()", this.getClass().getCanonicalName());
        List<Estructura> listServiciosQueSurte = null;
        boolean puedeVer = false;
        Estructura e = null;
        Object respuesta[] = new Object[2];
        respuesta[0] = listServiciosQueSurte;
        respuesta[1] = "";
        try {
            e = estructuraService.obtener(new Estructura(usuario.getIdEstructura()));
        } catch (Exception ex) {
            LOGGER.error("{}.obtieneServiciosQuePuedeSurtir(): {}", this.getClass().getCanonicalName(), ex.getMessage());
            respuesta[1] = ex.getMessage();
        }
        if (e == null || e.getIdTipoAreaEstructura() == null) {
            respuesta[1] = RESOURCES.getString("sur.sin.almacen");
        } else if (!Objects.equals(e.getIdTipoAreaEstructura(), TipoAreaEstructura_Enum.ALMACEN.getValue())) {
            respuesta[1] = RESOURCES.getString("sur.almacen.incorrectado");
        } else {
            puedeVer = true;
            listServiciosQueSurte = new ArrayList<>();
            try {
                if (usuario.getIdEstructura() != null) {
                    List<Estructura> estructuraServicio = estructuraService.obtenerServicioQueSurtePorIdEstructura(usuario.getIdEstructura());
                    listServiciosQueSurte.addAll(estructuraService.obtenerServiciosAlmcenPorIdEstructura(usuario.getIdEstructura()));
                    for(Estructura servicio : estructuraServicio){
                        listServiciosQueSurte.add(servicio);
                    }
                    respuesta[0] = listServiciosQueSurte;
                    respuesta[1] = "OK";
                }
            } catch (Exception ex) {
                respuesta[1] = "Error al obtener Servicios que puede surtir el usuario. ";
                LOGGER.error("{}.obtieneServiciosQuePuedeSurtir(): {} {}", this.getClass().getCanonicalName(), respuesta[1], ex.getMessage());
            }
        }
        if (!puedeVer) {
            if (((String) respuesta[1]).isEmpty()) {
                respuesta[1] = RESOURCES.getString("err.transaccion");
            } else {
                respuesta[1] = respuesta[1] + "|" + RESOURCES.getString("err.transaccion");
            }
        }
        return respuesta;
    }

    /**
     * Relacionado con el método DispensacionMB.consultaPermisoUsuario()
     * Obtiene una representacion de los permisos del usuario para una
     * Transaccion en particular
     *
     * @param usuario POJO del Uuario
     * @param sufijoTransaccion Código de la Transacción
     * @return Objeto con la representacion de los permisos del Usuario
     */
    public PermisoUsuario obtenPermisoUsuario(Usuario usuario, String sufijoTransaccion) {
        LOGGER.debug("{}.obtenPermisoUsuario()", this.getClass().getCanonicalName());
        PermisoUsuario permisosUsuario = new PermisoUsuario();
        if (usuario.getPermisosList() != null) {
            for (TransaccionPermisos trans : usuario.getPermisosList()) {
                if (trans.getCodigo().equals(sufijoTransaccion)) {
                    permisosUsuario = mx.mc.ws.movil.util.Comunes.obtenPermiso(trans.getAccion(), permisosUsuario);
                }
            }
        }
        return permisosUsuario;
    }
    
    /**
     * Obtiene la lista de Prescripciones Surtidas
     *
     * @param filtrosJson Cadena JSON con las credenciales del usuario, la
     * cadena de búsqueda
     * @return Respuesta en formato JSON con la lista de Surtimientos
     * @throws java.io.IOException
     */
    @POST
    @Path("obtenerPrescripcionesSurtidas")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPrescripcionesSurtidas(String filtrosJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode prescSurt = mapper.createObjectNode();
        JsonNode params = mapper.readTree(filtrosJson);
        if (!params.hasNonNull("usuario") || !params.hasNonNull("pass") || !params.hasNonNull("cadenaBusqueda")
                || !params.hasNonNull("tipoPrescripcion") || !params.hasNonNull("registroInicio") || !params.hasNonNull("maxPorPagina")) {
            prescSurt.put("estatus", "ERROR");
            prescSurt.put("mensaje", RESOURCES.getString("webservice.err.invalidRequest"));
            return Response.ok(prescSurt.toString()).build();
        }
        String userStr = params.get("usuario").asText();
        String pass = params.get("pass").asText();
        String cadenaBusqueda = params.get("cadenaBusqueda").asText();
        String tipoPrescripcionParam = params.get("tipoPrescripcion").asText();
        int startingAt = params.get("registroInicio").asInt();
        int maxPerPage = params.get("maxPorPagina").asInt();
        List<String> tipoPrescripcionList = (tipoPrescripcionParam.isEmpty() ? new ArrayList<>() : Arrays.asList(tipoPrescripcionParam.split("\\|")));
        Usuario usuarioParam = new Usuario();
        Usuario usuarioSelected = null;
        usuarioParam.setNombreUsuario(userStr);
        PermisoUsuario permisosUsuario = new PermisoUsuario();
        try {
            usuarioSelected = this.usuarioService.verificaSiExisteUser(usuarioParam);
        } catch (Exception ex) {
            prescSurt.put("estatus", "EXCEPCION");
            prescSurt.put("mensaje", ex.getMessage());
            LOGGER.error("{}.obtenerPrescripcionesSurtidas(): {}", this.getClass().getCanonicalName(), ex.getMessage());
        }
        if (usuarioSelected != null) {
            if (!CustomWebSecurityConfigurerAdapter.customPasswordEncoder().matches(pass, usuarioSelected.getClaveAcceso())) {
                prescSurt.put("estatus", "ERROR");
                prescSurt.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
            } else {
                List<TransaccionPermisos> permisosList = consultaPermisoUsuario(usuarioSelected);
                if (permisosList != null && !permisosList.isEmpty()) {
                    usuarioSelected.setPermisosList(permisosList);
                    permisosUsuario = obtenPermisoUsuario(usuarioSelected, Transaccion_Enum.DISPENSA_PRESCRIPCION.getSufijo());
                } else {
                    prescSurt.put("estatus", "ERROR");
                    prescSurt.put("mensaje", RESOURCES.getString("ses.obtener.datos"));
                }
                if (!permisosUsuario.isPuedeVer()) {
                    prescSurt.put("estatus", "ERROR");
                    prescSurt.put("mensaje", RESOURCES.getString("err.transaccion"));
                } else if (usuarioSelected.getIdEstructura() == null) {
                    prescSurt.put("estatus", "ERROR");
                    prescSurt.put("mensaje", RESOURCES.getString("sur.sin.almacen"));
                } else {
                    Object serviciosSurte[] = obtieneServiciosQuePuedeSurtir(usuarioSelected);
                    List<Estructura> listServiciosQueSurte = (List<Estructura>) serviciosSurte[0];
                    String resultadoConsulta = (String) serviciosSurte[1];
                    if (!resultadoConsulta.equalsIgnoreCase("OK")) {
                        prescSurt.put("estatus", "ERROR");
                        prescSurt.put("mensaje", resultadoConsulta);
                    } else {
                        try {
                            // regla: Listar prescripciones donde el paciente tiene estatus: Asignado a Servicio o Asignación a Cama
                            List<Integer> listEstatusPaciente = new ArrayList<>();
                            listEstatusPaciente.add(EstatusPaciente_Enum.ASIGNADO_A_SERVICIO.getValue());
                            listEstatusPaciente.add(EstatusPaciente_Enum.ASIGNADO_A_CAMA.getValue());

                            // regla: Listar prescripciones solo con estatus de ... (*** VALIDAR CONTRA REGLAS DE NEGOCIO ***)
                            List<Integer> listEstatusPrescripcion = new ArrayList<>();
                            listEstatusPrescripcion.add(EstatusPrescripcion_Enum.PROGRAMADA.getValue());
                            listEstatusPrescripcion.add(EstatusPrescripcion_Enum.FINALIZADA.getValue());
                            listEstatusPrescripcion.add(EstatusPrescripcion_Enum.PROCESANDO.getValue());
                            listEstatusPrescripcion.add(EstatusPrescripcion_Enum.SURTIDA_PARCIAL.getValue());

                            // regla: Listar prescripciones solo con estatus de surtimiento SURTIDO, EN TRANSITO o RECIBIDO
                            List<Integer> listEstatusSurtimiento = new ArrayList<>();
                            listEstatusSurtimiento.add(EstatusSurtimiento_Enum.SURTIDO.getValue());
                            listEstatusSurtimiento.add(EstatusSurtimiento_Enum.EN_TRANSITO.getValue());
                            listEstatusSurtimiento.add(EstatusSurtimiento_Enum.RECIBIDO.getValue());

                            // regla: filtro de tipo de prescripción: Normal, Dósis Única, Normal o todas
                            if (tipoPrescripcionList != null && tipoPrescripcionList.isEmpty()) {
                                tipoPrescripcionList = null;
                            }

                            // regla: listar prescripciones con fecha igual o menor a la fecha actual, nunca prescripciones futuras
                            Date fechaProgramada = new java.util.Date();

                            // TODO:    agregar reglas de Negocio
                            // 1.- El surtimiento solo funciona en Almacenes (CEDIME, CEDIMAT, Farmacia Externa) y subalmacenes (Gabinetes o sin sistema)
                            // 2.- Solo surten los insumos que tienen activos
                            // 3.- CEDIME y CEDIMAT solo surten a Consulta Interna
                            // 4.- 
                            ParamBusquedaReporte paramBusquedaReporte = new ParamBusquedaReporte();
                            paramBusquedaReporte.setNuevaBusqueda(true);
                            if (cadenaBusqueda != null && cadenaBusqueda.trim().isEmpty()) {
                                cadenaBusqueda = null;
                            }
                            paramBusquedaReporte.setCadenaBusqueda(cadenaBusqueda);

                            List<Surtimiento_Extend> registros = surtimientoService.obtenerPorFechaEstructuraPacienteCamaPrescripcionLazy(fechaProgramada, paramBusquedaReporte, startingAt, maxPerPage, tipoPrescripcionList, listEstatusPaciente, listEstatusPrescripcion, listEstatusSurtimiento, listServiciosQueSurte);
                            Long total = surtimientoService.obtenerTotalPorFechaEstructuraPacienteCamaPrescripcionLazy(fechaProgramada, paramBusquedaReporte, tipoPrescripcionList, listEstatusPaciente, listEstatusPrescripcion, listEstatusSurtimiento, listServiciosQueSurte);
                            prescSurt.put("estatus", "OK");
                            prescSurt.put("totalRegistros", total);
                            prescSurt.set("registros", mapper.valueToTree(registros));

                        } catch (Exception ex) {
                            prescSurt.put("estatus", "EXCEPCION");
                            prescSurt.put("mensaje", RESOURCES.getString("prc.pac.lista"));
                            LOGGER.error("{}.obtenerPrescripcionesSurtidas(): {} {}", this.getClass().getCanonicalName(), RESOURCES.getString("prc.pac.lista"), ex.getMessage());
                        }
                    }
                }
            }
        } else {
            prescSurt.put("estatus", "ERROR");
            prescSurt.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
        }
        return Response.ok(prescSurt.toString()).build();
    }

    /**
     * Equivalente del método DispensacionMB.obtenerSurtimientos() Obtiene la
     * lista de Prescripciones de acuerdo a los criterios y filtros de búsqueda
     *
     * @param filtrosJson Cadena JSON con las credenciales del usuario, la
     * cadena de búsqueda el tipo de prescripción y los datos de paginación
     * @return Respuesta en formato JSON con la lista de Surtimientos
     * @throws java.io.IOException
     */
    @POST
    @Path("obtenerSurtimientos")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerSurtimientos(String filtrosJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode resultado = mapper.createObjectNode();
        JsonNode params = mapper.readTree(filtrosJson);
        if (!params.hasNonNull("usuario") || !params.hasNonNull("pass") || !params.hasNonNull("cadenaBusqueda")
                || !params.hasNonNull("tipoPrescripcion") || !params.hasNonNull("registroInicio") || !params.hasNonNull("maxPorPagina")) {
            resultado.put("mensaje", RESOURCES.getString("webservice.err.invalidRequest"));
            resultado.put("estatus", "ERROR");
            return Response.ok(resultado.toString()).build();
        }
        String usuario = params.get("usuario").asText();
        String pass = params.get("pass").asText();
        String tipoPrescripcionParam = params.get("tipoPrescripcion").asText();
        String searchString = params.get("cadenaBusqueda").asText();
        int startingAt = params.get("registroInicio").asInt();
        int maxPerPage = params.get("maxPorPagina").asInt();

        List<String> tipoPrescripcionSelectedList = (tipoPrescripcionParam.isEmpty() ? new ArrayList<>() : Arrays.asList(tipoPrescripcionParam.split("\\|")));

        Usuario usuarioVerifica = new Usuario();
        Usuario usuarioVerificado = null;
        usuarioVerifica.setNombreUsuario(usuario);
        PermisoUsuario permisosUsuario = new PermisoUsuario();
        try {
            usuarioVerificado = this.usuarioService.verificaSiExisteUser(usuarioVerifica);
        } catch (Exception ex) {
            resultado.put("estatus", "EXCEPCION");
            resultado.put("mensaje", ex.getMessage());
            LOGGER.error("{}.obtenerSurtimientos(): {}", this.getClass().getCanonicalName(), ex.getMessage());
        }
        if (usuarioVerificado != null) {
            if (!CustomWebSecurityConfigurerAdapter.customPasswordEncoder().matches(pass, usuarioVerificado.getClaveAcceso())) {
                resultado.put("estatus", "ERROR");
                resultado.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
            } else {
                List<TransaccionPermisos> permisosList = consultaPermisoUsuario(usuarioVerificado);
                if (permisosList != null && !permisosList.isEmpty()) {
                    usuarioVerificado.setPermisosList(permisosList);
                    permisosUsuario = obtenPermisoUsuario(usuarioVerificado, Transaccion_Enum.DISPENSA_PRESCRIPCION.getSufijo());
                } else {
                    resultado.put("estatus", "ERROR");
                    resultado.put("mensaje", RESOURCES.getString("ses.obtener.datos"));
                }
                if (!permisosUsuario.isPuedeVer()) {
                    resultado.put("estatus", "ERROR");
                    resultado.put("mensaje", RESOURCES.getString("err.transaccion"));
                } else if (usuarioVerificado.getIdEstructura() == null) {
                    resultado.put("estatus", "ERROR");
                    resultado.put("mensaje", RESOURCES.getString("sur.sin.almacen"));
                } else {
                    Object serviciosSurte[] = obtieneServiciosQuePuedeSurtir(usuarioVerificado);
                    List<Estructura> listServiciosSurte = (List<Estructura>) serviciosSurte[0];
                    String resultadoCons = (String) serviciosSurte[1];
                    if (!resultadoCons.equalsIgnoreCase("OK")) {
                        resultado.put("estatus", "ERROR");
                        resultado.put("mensaje", resultadoCons);
                    } else {
                        try {
                            if (searchString != null && searchString.trim().isEmpty()) {
                                searchString = null;
                            }
                            // regla: Listar prescripciones donde el paciente tiene estatus: Asignado a Servicio o Asignación a Cama
                            List<Integer> listEstatusPaciente = new ArrayList<>();
                            listEstatusPaciente.add(EstatusPaciente_Enum.ASIGNADO_A_SERVICIO.getValue());
                            listEstatusPaciente.add(EstatusPaciente_Enum.ASIGNADO_A_CAMA.getValue());

                            // regla: Listar prescripciones solo con estatus de PROGRAMADA
                            List<Integer> listEstatusPrescripcion = new ArrayList<>();
                            listEstatusPrescripcion.add(EstatusPrescripcion_Enum.PROGRAMADA.getValue());
                            listEstatusPrescripcion.add(EstatusPrescripcion_Enum.FINALIZADA.getValue());

                            // regla: Listar prescripciones solo con estatus de surtimiento programado
                            List<Integer> listEstatusSurtimiento = new ArrayList<>();
                            listEstatusSurtimiento.add(EstatusSurtimiento_Enum.PROGRAMADO.getValue());

                            // regla: filtro de tipo de prescripción: Normal, Dósis Única, Normal o todas
                            if (tipoPrescripcionSelectedList != null && tipoPrescripcionSelectedList.isEmpty()) {
                                tipoPrescripcionSelectedList = null;
                            }

                            // regla: listar prescripciones con fecha igual o menor a la fecha actual, nunca prescripciones futuras
                            Date fechaProgramada = new java.util.Date();

                            // TODO:    agregar reglas de Negocio
                            // 1.- El surtimiento solo funciona en Almacenes (CEDIME, CEDIMAT, Farmacia Externa) y subalmacenes (Gabinetes o sin sistema)
                            // 2.- Solo surten los insumos que tienen activos
                            // 3.- CEDIME y CEDIMAT solo surten a Consulta Interna
                            // 4.- 
                            ParamBusquedaReporte paramBusquedaReporte = new ParamBusquedaReporte();
                            paramBusquedaReporte.setNuevaBusqueda(true);
                            paramBusquedaReporte.setCadenaBusqueda(searchString);
                            List<Surtimiento_Extend> registros = surtimientoService.obtenerPorFechaEstructuraPacienteCamaPrescripcionLazy(fechaProgramada, paramBusquedaReporte, startingAt, maxPerPage, tipoPrescripcionSelectedList, listEstatusPaciente, listEstatusPrescripcion, listEstatusSurtimiento, listServiciosSurte);
                            Long total = surtimientoService.obtenerTotalPorFechaEstructuraPacienteCamaPrescripcionLazy(fechaProgramada, paramBusquedaReporte, tipoPrescripcionSelectedList, listEstatusPaciente, listEstatusPrescripcion, listEstatusSurtimiento, listServiciosSurte);
                            resultado.put("estatus", "OK");
                            resultado.put("totalRegistros", total);
                            resultado.set("registros", mapper.valueToTree(registros));
                        } catch (Exception ex) {
                            resultado.put("estatus", "EXCEPCION");
                            resultado.put("mensaje", RESOURCES.getString("prc.pac.lista"));
                            LOGGER.error("{}.obtenerSurtimientos(): {} {}", this.getClass().getCanonicalName(), RESOURCES.getString("prc.pac.lista"), ex.getMessage());
                        }
                    }
                }
            }
        } else {
            resultado.put("estatus", "ERROR");
            resultado.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
        }
        return Response.ok(resultado.toString()).build();
    }

    /**
     * Obtiene la lista de Prescripciones Surtidas
     *
     * @param filtrosJson Cadena JSON con las credenciales del usuario, la
     * cadena de búsqueda
     * @return Respuesta en formato JSON con la lista de Surtimientos
     * @throws java.io.IOException
     */
    @POST
    @Path("obtenerPrescripcionesCanceladas")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPrescripcionesCanceladas(String filtrosJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode prescCan = mapper.createObjectNode();
        JsonNode params = mapper.readTree(filtrosJson);
        if (!params.hasNonNull("usuario") || !params.hasNonNull("pass") || !params.hasNonNull("cadenaBusqueda")
                || !params.hasNonNull("tipoPrescripcion") || !params.hasNonNull("registroInicio") || !params.hasNonNull("maxPorPagina")) {
            prescCan.put("estatus", "ERROR");
            prescCan.put("mensaje", RESOURCES.getString("webservice.err.invalidRequest"));
            return Response.ok(prescCan.toString()).build();
        }
        String usuario = params.get("usuario").asText();
        String pass = params.get("pass").asText();
        String cadenaBusqueda = params.get("cadenaBusqueda").asText();
        String tipoPrescripcion = params.get("tipoPrescripcion").asText();
        int startingAt = params.get("registroInicio").asInt();
        int maxPerPage = params.get("maxPorPagina").asInt();

        List<String> tipoPrescripcionSelectedList = (tipoPrescripcion.isEmpty() ? new ArrayList<>() : Arrays.asList(tipoPrescripcion.split("\\|")));

        Usuario usuarioParam = new Usuario();
        Usuario usuarioSelected = null;
        usuarioParam.setNombreUsuario(usuario);
        PermisoUsuario permisosUsuario = new PermisoUsuario();
        try {
            usuarioSelected = this.usuarioService.verificaSiExisteUser(usuarioParam);
        } catch (Exception ex) {
            prescCan.put("estatus", "EXCEPCION");
            prescCan.put("mensaje", ex.getMessage());
            LOGGER.error("{}.obtenerPrescripcionesCanceladas(): {}", this.getClass().getCanonicalName(), ex.getMessage());
        }
        if (usuarioSelected != null) {
            if (!CustomWebSecurityConfigurerAdapter.customPasswordEncoder().matches(pass, usuarioSelected.getClaveAcceso())) {
                prescCan.put("estatus", "ERROR");
                prescCan.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
            } else {
                List<TransaccionPermisos> permisosList = consultaPermisoUsuario(usuarioSelected);
                if (permisosList != null && !permisosList.isEmpty()) {
                    usuarioSelected.setPermisosList(permisosList);
                    permisosUsuario = obtenPermisoUsuario(usuarioSelected, Transaccion_Enum.DISPENSA_PRESCRIPCION.getSufijo());
                } else {
                    prescCan.put("estatus", "ERROR");
                    prescCan.put("mensaje", RESOURCES.getString("ses.obtener.datos"));
                }
                if (!permisosUsuario.isPuedeVer()) {
                    prescCan.put("estatus", "ERROR");
                    prescCan.put("mensaje", RESOURCES.getString("err.transaccion"));
                } else if (usuarioSelected.getIdEstructura() == null) {
                    prescCan.put("estatus", "ERROR");
                    prescCan.put("mensaje", RESOURCES.getString("sur.sin.almacen"));
                } else {
                    Object serviciosSurte[] = obtieneServiciosQuePuedeSurtir(usuarioSelected);
                    List<Estructura> listServiciosQueSurte = (List<Estructura>) serviciosSurte[0];
                    String resultadoConsulta = (String) serviciosSurte[1];
                    if (!resultadoConsulta.equalsIgnoreCase("OK")) {
                        prescCan.put("estatus", "ERROR");
                        prescCan.put("mensaje", resultadoConsulta);
                    } else {
                        try {
                            if (cadenaBusqueda != null && cadenaBusqueda.trim().isEmpty()) {
                                cadenaBusqueda = null;
                            }

                            // regla: Listar prescripciones donde el paciente tiene estatus: Asignado a Servicio o Asignación a Cama
                            List<Integer> listEstatusPaciente = new ArrayList<>();
                            listEstatusPaciente.add(EstatusPaciente_Enum.ASIGNADO_A_SERVICIO.getValue());
                            listEstatusPaciente.add(EstatusPaciente_Enum.ASIGNADO_A_CAMA.getValue());
                            List<Integer> listEstatusPrescripcion = null;

                            // regla: Listar prescripciones solo con estatus de surtimiento CANCELADO
                            List<Integer> listEstatusSurtimiento = new ArrayList<>();
                            listEstatusSurtimiento.add(EstatusSurtimiento_Enum.CANCELADO.getValue());

                            // regla: filtro de tipo de prescripción: Normal, Dósis Única, Normal o todas
                            if (tipoPrescripcionSelectedList != null
                                    && tipoPrescripcionSelectedList.isEmpty()) {
                                tipoPrescripcionSelectedList = null;
                            }

                            // regla: listar prescripciones con fecha igual o menor a la fecha actual, nunca prescripciones futuras
                            Date fechaProgramada = new java.util.Date();

                            // TODO:    agregar reglas de Negocio
                            // 1.- El surtimiento solo funciona en Almacenes (CEDIME, CEDIMAT, Farmacia Externa) y subalmacenes (Gabinetes o sin sistema)
                            // 2.- Solo surten los insumos que tienen activos
                            // 3.- CEDIME y CEDIMAT solo surten a Consulta Interna
                            // 4.- 
                            ParamBusquedaReporte paramBusquedaReporte = new ParamBusquedaReporte();
                            paramBusquedaReporte.setNuevaBusqueda(true);
                            paramBusquedaReporte.setCadenaBusqueda(cadenaBusqueda);

                            List<Surtimiento_Extend> registros = surtimientoService.obtenerPorFechaEstructuraPacienteCamaPrescripcionLazy(fechaProgramada, paramBusquedaReporte, startingAt, maxPerPage, tipoPrescripcionSelectedList, listEstatusPaciente, listEstatusPrescripcion, listEstatusSurtimiento, listServiciosQueSurte);
                            Long total = surtimientoService.obtenerTotalPorFechaEstructuraPacienteCamaPrescripcionLazy(fechaProgramada, paramBusquedaReporte, tipoPrescripcionSelectedList, listEstatusPaciente, listEstatusPrescripcion, listEstatusSurtimiento, listServiciosQueSurte);
                            prescCan.put("estatus", "OK");
                            prescCan.put("totalRegistros", total);
                            prescCan.set("registros", mapper.valueToTree(registros));

                        } catch (Exception ex) {
                            prescCan.put("estatus", "EXCEPCION");
                            prescCan.put("mensaje", RESOURCES.getString("prc.pac.lista"));
                            LOGGER.error("{}.obtenerPrescripcionesCanceladas(): {} {}", this.getClass().getCanonicalName(), RESOURCES.getString("prc.pac.lista"), ex.getMessage());
                        }
                    }
                }
            }
        } else {
            prescCan.put("estatus", "ERROR");
            prescCan.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
        }
        return Response.ok(prescCan.toString()).build();
    }

    /**
     * Obtiene la lista de Surtimientos solicitados para una prescripción
     *
     * @param idStructura Identificador de la estructura del Usuario
     * @param idSurtimiento Identificador del Surtimiento
     * @param idPrescripcion Identificador de la Prescripción
     * @param modulo Módulo de la App que realiza la petición
     * @return Lista de Surtimientos
     */
    private List<SurtimientoInsumo_Extend> obtenListaSurtimientoInsumo(String idEstructura, String idSurtimiento, String idPrescripcion, String modulo) {
        Date fechaProgramada = new java.util.Date();
        List<Integer> listEstatusSurtimiento = new ArrayList<>();
        List<Integer> listEstatusSurtimientoInsumo = new ArrayList<>();
        if (modulo == null) {
            listEstatusSurtimiento.add(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
            listEstatusSurtimientoInsumo.add(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
        } else if (modulo.equalsIgnoreCase("SURTIDA")) {
            listEstatusSurtimiento.add(EstatusSurtimiento_Enum.SURTIDO.getValue());
            listEstatusSurtimiento.add(EstatusSurtimiento_Enum.EN_TRANSITO.getValue());
            listEstatusSurtimiento.add(EstatusSurtimiento_Enum.RECIBIDO.getValue());

            listEstatusSurtimientoInsumo.add(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
            listEstatusSurtimientoInsumo.add(EstatusSurtimiento_Enum.SURTIDO.getValue());
            listEstatusSurtimientoInsumo.add(EstatusSurtimiento_Enum.EN_TRANSITO.getValue());
            listEstatusSurtimientoInsumo.add(EstatusSurtimiento_Enum.SUTIDO_PARCIAL.getValue());
        } else if (modulo.equalsIgnoreCase("CANCELADA")) {
            listEstatusSurtimiento.add(EstatusSurtimiento_Enum.CANCELADO.getValue());
            listEstatusSurtimientoInsumo = null;
        }
        boolean surtimientoMixto = false;
        List<SurtimientoInsumo_Extend> surtimientoInsumoExtendedList = new ArrayList<>();
        try {
            surtimientoInsumoExtendedList
                    .addAll(surtimientoInsumoService
                            .obtenerSurtimientosProgramados(fechaProgramada, idSurtimiento, idPrescripcion, listEstatusSurtimientoInsumo, listEstatusSurtimiento, idEstructura, surtimientoMixto)
                    );
            if (!idSurtimiento.equals("OFFLINE") && !idPrescripcion.equals("OFFLINE")) {
                for (SurtimientoInsumo_Extend surt_ext : surtimientoInsumoExtendedList) {
                    List<SurtimientoEnviado_Extend> enviadosPorSurtimientoInsumo = surtimientoService.obtenerDetalleEnviadoPorIdSurtimientoInsumo(surt_ext.getIdSurtimientoInsumo());
                    if (enviadosPorSurtimientoInsumo != null && !enviadosPorSurtimientoInsumo.isEmpty()) {
                        surt_ext.setSurtimientoEnviadoExtendList(enviadosPorSurtimientoInsumo);
                    }
                }
            } else {
                List<SurtimientoEnviado_Extend> enviadosPorSurtimientoInsumo = surtimientoService.obtenerDetalleEnviadoPorIdSurtimientoInsumo(null);
                if (enviadosPorSurtimientoInsumo != null && !enviadosPorSurtimientoInsumo.isEmpty()) {
                    for (SurtimientoInsumo_Extend surt_ext : surtimientoInsumoExtendedList) {
                        for (SurtimientoEnviado_Extend surt_env : enviadosPorSurtimientoInsumo) {
                            if (surt_ext.getIdSurtimientoInsumo().equals(surt_env.getIdSurtimientoInsumo())) {
                                surt_ext.setSurtimientoEnviadoExtendList(Arrays.asList(surt_env));
                            }
                        }
                    }
                }
            }

        } catch (Exception ex) {
            surtimientoInsumoExtendedList = null;
            LOGGER.error("{}.obtenListaSurtimientoInsumo(): {}", this.getClass().getCanonicalName(), ex.getMessage());
        }
        return surtimientoInsumoExtendedList;
    }

    /**
     * Equivalente del método DispensacionMB.verSurtimiento() Obtiene la lista
     * de Surtimientos de acuerdo al paciente y a la prescripción
     *
     * @param filtrosJson Cadena JSON con las credenciales del usuario, el ID
     * del surtimiento y el ID de la prescripción
     * @return Respuesta en formato JSON con la lista de Surtimientos
     * @throws java.io.IOException
     */
    @POST
    @Path("verSurtimiento")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response verSurtimiento(String filtrosJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode surtimiento = mapper.createObjectNode();
        JsonNode params = mapper.readTree(filtrosJson);
        if (!params.hasNonNull("usuario") || !params.hasNonNull("pass")
                || !params.hasNonNull("idSurtimiento") || !params.hasNonNull("idPrescripcion")) {
            surtimiento.put("estatus", "ERROR");
            surtimiento.put("mensaje", RESOURCES.getString("webservice.err.invalidRequest"));
            return Response.ok(surtimiento.toString()).build();
        }
        String usuario = params.get("usuario").asText();
        String pass = params.get("pass").asText();
        String idSurtimiento = params.get("idSurtimiento").asText();
        String idPrescripcion = params.get("idPrescripcion").asText();
        String modulo = null;
        if (params.hasNonNull("modulo")) {
            modulo = params.get("modulo").asText();
        }

        Usuario usuarioParam = new Usuario();
        Usuario usuarioSelected = null;
        usuarioParam.setNombreUsuario(usuario);
        PermisoUsuario permisosUsuario = new PermisoUsuario();
        try {
            usuarioSelected = this.usuarioService.verificaSiExisteUser(usuarioParam);
        } catch (Exception ex) {
            surtimiento.put("estatus", "EXCEPCION");
            surtimiento.put("mensaje", ex.getMessage());
            LOGGER.error("{}.verSurtimiento(): {}", this.getClass().getCanonicalName(), ex.getMessage());
        }
        if (usuarioSelected != null) {
            if (!CustomWebSecurityConfigurerAdapter.customPasswordEncoder().matches(pass, usuarioSelected.getClaveAcceso())) {
                surtimiento.put("estatus", "ERROR");
                surtimiento.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
            } else {
                List<TransaccionPermisos> permisosList = consultaPermisoUsuario(usuarioSelected);
                if (permisosList != null && !permisosList.isEmpty()) {
                    usuarioSelected.setPermisosList(permisosList);
                    permisosUsuario = obtenPermisoUsuario(usuarioSelected, Transaccion_Enum.DISPENSA_PRESCRIPCION.getSufijo());
                } else {
                    surtimiento.put("estatus", "ERROR");
                    surtimiento.put("mensaje", RESOURCES.getString("ses.obtener.datos"));
                }
                if (!permisosUsuario.isPuedeVer()) {
                    surtimiento.put("estatus", "ERROR");
                    surtimiento.put("mensaje", RESOURCES.getString("err.transaccion"));
                } else if (usuarioSelected.getIdEstructura() == null) {
                    surtimiento.put("estatus", "ERROR");
                    surtimiento.put("mensaje", RESOURCES.getString("sur.sin.almacen"));
                } else {
                    if (idSurtimiento.isEmpty() || idPrescripcion.isEmpty()) {
                        surtimiento.put("estatus", "ERROR");
                        surtimiento.put("mensaje", RESOURCES.getString("webservice.surtimiento.err.sinSurtimiento"));
                    } else {
                        List<SurtimientoInsumo_Extend> surtimientoInsumoExtendedList = obtenListaSurtimientoInsumo(usuarioSelected.getIdEstructura(), idSurtimiento, idPrescripcion, modulo);
                        if (surtimientoInsumoExtendedList != null) {
                            surtimiento.put("estatus", "OK");
                            ArrayNode registrosNode = mapper.valueToTree(surtimientoInsumoExtendedList);
                            surtimiento.set("registros", registrosNode);
                        } else {
                            surtimiento.put("estatus", "ERROR");
                            surtimiento.put("mensaje", RESOURCES.getString("sur.incorrecto"));
                            LOGGER.error("{}.verSurtimiento(): {}", this.getClass().getCanonicalName(), RESOURCES.getString("sur.incorrecto"));
                        }
                    }
                }
            }
        } else {
            surtimiento.put("estatus", "ERROR");
            surtimiento.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
        }
        return Response.ok(surtimiento.toString()).build();
    }

    /**
     * Equivalente del método SesionMB.obtenerDatosSistema() Obtiene la lista de
     * configuraciones del Sistema
     */
    private void obtenerDatosSistema() {
        LOGGER.debug("mx.mc.ws.movil.service.DispensacionMBMovil.obtenerDatosSistema()");
        Config config = new Config();
        config.setActiva(Constantes.ACTIVO);
        try {
            configList = configService.obtenerLista(config);
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString("sys.config.err"), ex);
        }
    }

    /**
     * Equivalente del método DispensacionMB.agregarLotePorCodigo() Agrega un
     * escaneo como subdetalle del resurtimiento
     *
     * @param codigoBarras Código de Barras escaneado
     * @param cantidad Cantidad a Surtir
     * @param usuarioSelected Usuario que realiza la operación
     * @param surtimientoExtendedSelected Registro del Surtimiento Seleccionado
     * @param surtimientoInsumoExtendedList Lista de Surtimientos
     * @return Respuesta en formato JSON
     */
    private ObjectNode agregarLotePorCodigo(String codigoBarras, int cantidad, Usuario usuarioSelected, Surtimiento_Extend surtimientoExtendedSelected, List<SurtimientoInsumo_Extend> surtimientoInsumoExtendedList) {
        LOGGER.trace("{}.agregarLotePorCodigo()", this.getClass().getCanonicalName());
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode listaSurte = mapper.createObjectNode();
        boolean error = true;
        boolean found = Constantes.INACTIVO;
        obtenerDatosSistema();
        int noDiasCaducidad = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_DIASPARACADUCIDAD);
        Boolean isCodificacionGS1 = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_CODIGOS_GS1) == 1;
        CodigoInsumo codigo = CodigoBarras.parsearCodigoDeBarras(codigoBarras, isCodificacionGS1);
        if (codigo == null) {
            listaSurte.put("mensaje", RESOURCES.getString("err.parser"));
        } else {
            List<SurtimientoEnviado_Extend> surtimientoEnviadoList;
            SurtimientoEnviado_Extend surtimientoEnviadoExtend;
            Integer cantidadEscaneada;
            Integer cantidadEnviada;
            surtimientoExtendedSelected.setIdEstructuraAlmacen(usuarioSelected.getIdEstructura());
            for (SurtimientoInsumo_Extend surtimientoInsumo : surtimientoInsumoExtendedList) {
                cantidadEscaneada = (cantidad == 0) ? 1 : cantidad;

// regla: factor multiplicador debe ser 1 o mayor
                if (cantidadEscaneada < 1) {
                    listaSurte.put("mensaje", RESOURCES.getString("sur.cantidadincorrecta"));
// regla: solo escanea medicamentos si la clave escaneada existe en el detalle solicitado
                } else if (surtimientoInsumo.getClaveInstitucional().equals(codigo.getClave())) {
                    found = Constantes.ACTIVO;

// regla: solo escanea medicamentos si no esta bloqueado a nivel catálogo
                    if (!surtimientoInsumo.isMedicamentoActivo()) {
                        listaSurte.put("mensaje", RESOURCES.getString("sur.clavebloqueada"));
                    } else {

                        cantidadEnviada = (surtimientoInsumo.getCantidadEnviada() == null) ? 0 : surtimientoInsumo.getCantidadEnviada();
                        cantidadEnviada = cantidadEnviada + cantidadEscaneada;

// regla: no puede surtir mas medicamento que el solicitado
                        if (cantidadEnviada > surtimientoInsumo.getCantidadSolicitada()) {
                            listaSurte.put("mensaje", RESOURCES.getString("sur.exedido"));
                        } else if (FechaUtil.sumarRestarDiasFecha(new java.util.Date(), noDiasCaducidad).after(codigo.getFecha())) {
                            listaSurte.put("mensaje", RESOURCES.getString("sur.caducidadvencida"));
                        } else {
                            Inventario inventarioSurtido = null;
                            Integer cantidadXCaja = null;
                            String claveProveedor = null;
                            try {
                                inventarioSurtido = inventarioService.obtenerInventariosPorInsumoEstructuraLoteYCantidadXCajaYFechaCad(
                                        surtimientoInsumo.getIdInsumo(), surtimientoExtendedSelected.getIdEstructuraAlmacen(),
                                        codigo.getLote(), cantidadXCaja, claveProveedor, codigo.getFecha()
                                );
                            } catch (Exception ex) {
                                LOGGER.error(RESOURCES.getString("sur.loteincorrecto"), ex);
                            }

                            int cantidadPrevia = 0;
                            if (surtimientoInsumo.getSurtimientoEnviadoExtendList() != null) {
                                for (SurtimientoEnviado_Extend surtimientoEnviado : surtimientoInsumo.getSurtimientoEnviadoExtendList()) {
                                    if (codigo.getLote().equals(surtimientoEnviado.getLote()) && codigo.getFecha().equals(surtimientoEnviado.getCaducidad())) {
                                        cantidadPrevia = surtimientoEnviado.getCantidadEnviado();
                                        break;
                                    }
                                }
                            }

                            if (inventarioSurtido == null) {
                                listaSurte.put("mensaje", RESOURCES.getString("sur.loteincorrecto"));
                            } else if (inventarioSurtido.getActivo() == 0) {
                                listaSurte.put("mensaje", RESOURCES.getString("sur.lotebloqueado"));
                            } else if (inventarioSurtido.getCantidadActual() < (cantidadEscaneada + cantidadPrevia)) {
                                listaSurte.put("mensaje", RESOURCES.getString("sur.cantidadinsuficiente"));
                            } else {
                                surtimientoEnviadoList = new ArrayList<>();
                                if (surtimientoInsumo.getSurtimientoEnviadoExtendList() != null) {
                                    surtimientoEnviadoList.addAll(surtimientoInsumo.getSurtimientoEnviadoExtendList());
                                }

// regla: si es primer Lote pistoleado solo muestra una linea en subdetalle
                                if (surtimientoEnviadoList.isEmpty()) {
                                    surtimientoEnviadoExtend = new SurtimientoEnviado_Extend();
                                    surtimientoEnviadoExtend.setIdSurtimientoEnviado(Comunes.getUUID());
                                    surtimientoEnviadoExtend.setIdSurtimientoInsumo(surtimientoInsumo.getIdSurtimientoInsumo());
                                    surtimientoEnviadoExtend.setIdInventarioSurtido(inventarioSurtido.getIdInventario());
                                    surtimientoEnviadoExtend.setCantidadEnviado(cantidadEscaneada);

                                    surtimientoEnviadoExtend.setLote(codigo.getLote());
                                    surtimientoEnviadoExtend.setCaducidad(codigo.getFecha());
                                    surtimientoEnviadoExtend.setIdInsumo(inventarioSurtido.getIdInsumo());
                                    surtimientoEnviadoList.add(surtimientoEnviadoExtend);

                                } else {

                                    boolean agrupaLote = false;
                                    for (SurtimientoEnviado_Extend surtimientoEnviadoRegistado : surtimientoEnviadoList) {
// regla: si se pistolea mas de un medicmento con el mismo lote se agrupan por lotes sumarizando las cantidades
                                        if (surtimientoEnviadoRegistado.getLote().equals(codigo.getLote())
                                                && surtimientoEnviadoRegistado.getCaducidad().equals(codigo.getFecha())
                                                && surtimientoEnviadoRegistado.getIdInsumo().equals(inventarioSurtido.getIdInsumo())) {
                                            agrupaLote = true;
                                            Integer cantidadEnviado = surtimientoEnviadoRegistado.getCantidadEnviado() + cantidadEscaneada;
                                            surtimientoEnviadoRegistado.setCantidadEnviado(cantidadEnviado);
                                            break;
                                        }
                                    }
// regla: si es el único Lote pistoleado agrega una linea nueva en subdetalle
                                    if (!agrupaLote) {
                                        surtimientoEnviadoExtend = new SurtimientoEnviado_Extend();
                                        surtimientoEnviadoExtend.setIdSurtimientoEnviado(Comunes.getUUID());
                                        surtimientoEnviadoExtend.setIdSurtimientoInsumo(surtimientoInsumo.getIdSurtimientoInsumo());
                                        surtimientoEnviadoExtend.setIdInventarioSurtido(inventarioSurtido.getIdInventario());
                                        surtimientoEnviadoExtend.setCantidadEnviado(cantidadEscaneada);

                                        surtimientoEnviadoExtend.setLote(codigo.getLote());
                                        surtimientoEnviadoExtend.setCaducidad(codigo.getFecha());
                                        surtimientoEnviadoExtend.setIdInsumo(inventarioSurtido.getIdInsumo());
                                        surtimientoEnviadoList.add(surtimientoEnviadoExtend);
                                    }
                                }

                                surtimientoInsumo.setFechaEnviada(new java.util.Date());
                                surtimientoInsumo.setIdUsuarioEnviada(usuarioSelected.getIdUsuario());
                                surtimientoInsumo.setCantidadEnviada(cantidadEnviada);
                                surtimientoInsumo.setSurtimientoEnviadoExtendList(surtimientoEnviadoList);
                                if (Objects.equals(surtimientoInsumo.getCantidadSolicitada(), surtimientoInsumo.getCantidadEnviada())) {
                                    surtimientoInsumo.setRequiereJustificante(Constantes.INACTIVO);
                                    surtimientoInsumo.setIdTipoJustificante(null);
                                } else {
                                    surtimientoInsumo.setRequiereJustificante(Constantes.ACTIVO);
                                    surtimientoInsumo.setIdTipoJustificante(TipoJustificacion_Enum.INSUFICIENTE.getValue());
                                }
                                error = false;
                            }
                        }
                    }
                }
            }
        }
        if (!found) {
            listaSurte.put("estatus", "ERROR");
            listaSurte.put("mensaje", RESOURCES.getString("sur.claveincorrecta"));
        } else if (error) {
            listaSurte.put("estatus", "ERROR");
        } else {
            listaSurte.put("estatus", "OK");
            listaSurte.set("surtimientoInsumoExtendedList", mapper.valueToTree(surtimientoInsumoExtendedList));
        }
        return listaSurte;
    }

    /**
     * Equivalente del método DispensacionMB.eliminarLotePorCodigo() Elimina un
     * escaneo como subdetalle del resurtimiento
     *
     * @param codigoBarras Código de Barras escaneado
     * @param cantidad Cantidad a Surtir
     * @param usuarioSelected Usuario que realiza la operación
     * @param surtimientoInsumoExtendedList Lista de Surtimientos
     * @return Respuesta en formato JSON
     */
    private ObjectNode eliminarLotePorCodigo(String codigoBarras, int cantidad, List<SurtimientoInsumo_Extend> surtimientoInsumoExtendedList) {
        LOGGER.trace("{}.eliminarLotePorCodigo()", this.getClass().getCanonicalName());
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode resultado = mapper.createObjectNode();
        boolean error = true;
        boolean encontrado = Constantes.INACTIVO;
        obtenerDatosSistema();
        int noDiasCaducidad = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_DIASPARACADUCIDAD);
        Boolean isCodificacionGS1 = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_CODIGOS_GS1) == 1;
        CodigoInsumo codInsumo = CodigoBarras.parsearCodigoDeBarras(codigoBarras, isCodificacionGS1);
        if (codInsumo == null) {
            resultado.put("mensaje", RESOURCES.getString("err.parser"));
        } else if (FechaUtil.sumarRestarDiasFecha(new java.util.Date(), noDiasCaducidad).after(codInsumo.getFecha())) {
            resultado.put("mensaje", RESOURCES.getString("sur.caducidadvencida"));
        } else {
            List<SurtimientoEnviado_Extend> surtimientoEnviadoExtendList;
            Integer cantidadEscaneada;
            Integer cantidadEnviada;

            for (SurtimientoInsumo_Extend surtimientoInsumo : surtimientoInsumoExtendedList) {
// regla: puede escanear medicamentos mientras la clave escaneada exista en el detalle solicitado
                if (surtimientoInsumo.getClaveInstitucional().equals(codInsumo.getClave())) {
                    encontrado = Constantes.ACTIVO;
                    cantidadEscaneada = (cantidad == 0) ? 1 : cantidad;
// regla: factor multiplicador debe ser 1 o mayor
                    if (cantidadEscaneada < 1) {
                        resultado.put("mensaje", RESOURCES.getString("sur.cantidadincorrecta"));
                    } else {
                        cantidadEnviada = (surtimientoInsumo.getCantidadEnviada() == null) ? 0 : surtimientoInsumo.getCantidadEnviada();
                        cantidadEnviada = cantidadEnviada - cantidadEscaneada;
                        cantidadEnviada = (cantidadEnviada < 0) ? 0 : cantidadEnviada;

                        surtimientoEnviadoExtendList = new ArrayList<>();
                        if (surtimientoInsumo.getSurtimientoEnviadoExtendList() == null) {
                            resultado.put("mensaje", RESOURCES.getString("sur.lotesinescanear"));
                        } else {
                            surtimientoEnviadoExtendList.addAll(surtimientoInsumo.getSurtimientoEnviadoExtendList());

// regla: el lote a eliminar del surtimiento ya debió ser escaneado para eliminaro
                            if (surtimientoEnviadoExtendList.isEmpty()) {
                                resultado.put("mensaje", RESOURCES.getString("sur.lotesinescanear"));
                            } else {
                                boolean correspondeLote = false;
                                Integer cantidadEnviadaPorLote;
                                for (SurtimientoEnviado_Extend surtimientoEnviadoRegistado : surtimientoEnviadoExtendList) {

// regla: si el lote escaneado ya ha sido agregado se descuentan las cantidades
                                    if (surtimientoEnviadoRegistado.getLote().equals(codInsumo.getLote())
                                            && surtimientoEnviadoRegistado.getCaducidad().equals(codInsumo.getFecha())) {
                                        correspondeLote = true;
                                        cantidadEnviadaPorLote = surtimientoEnviadoRegistado.getCantidadEnviado() - cantidadEscaneada;
                                        cantidadEnviadaPorLote = (cantidadEnviadaPorLote < 0) ? 0 : cantidadEnviadaPorLote;
                                        if (cantidadEnviadaPorLote < 1) {
                                            surtimientoEnviadoExtendList.remove(surtimientoEnviadoRegistado);
                                        } else {
                                            surtimientoEnviadoRegistado.setCantidadEnviado(cantidadEnviadaPorLote);
                                        }
                                        break;
                                    }
                                }
                                if (correspondeLote) {
                                    surtimientoInsumo.setCantidadEnviada(cantidadEnviada);
                                    surtimientoInsumo.setSurtimientoEnviadoExtendList(surtimientoEnviadoExtendList);
                                    if (Objects.equals(surtimientoInsumo.getCantidadSolicitada(), surtimientoInsumo.getCantidadEnviada())) {
                                        surtimientoInsumo.setRequiereJustificante(Constantes.INACTIVO);
                                        surtimientoInsumo.setIdTipoJustificante(null);
                                    } else {
                                        surtimientoInsumo.setRequiereJustificante(Constantes.ACTIVO);
                                        surtimientoInsumo.setIdTipoJustificante(TipoJustificacion_Enum.INSUFICIENTE.getValue());
                                    }
                                } else {
                                    encontrado = Constantes.INACTIVO;
                                }
                                error = false;
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (!encontrado) {
            resultado.put("estatus", "ERROR");
            resultado.put("mensaje", RESOURCES.getString("sur.error.noExisteInsumoEliminar"));
        } else if (error) {
            resultado.put("estatus", "ERROR");
            resultado.put("mensaje", RESOURCES.getString("sur.error"));
        } else {
            resultado.put("estatus", "OK");
            ArrayNode registrosNode = mapper.valueToTree(surtimientoInsumoExtendedList);
            resultado.set("surtimientoInsumoExtendedList", registrosNode);
        }
        return resultado;
    }

    /**
     * Equivalente del método DispensacionMB.validaLecturaPorCodigo() Lee el
     * codigo de barras de un medicamento y confirma la cantidad escaneda para
     * enviarse en el surtimento de prescripción
     *
     * @param filtrosJson Cadena JSON con las credenciales del usuario, el
     * código de barras y el registro de Surtimiento
     * @return Respuesta en formato JSON
     * @throws java.io.IOException
     */
    @POST
    @Path("validaLecturaPorCodigo")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response validaLecturaPorCodigo(String filtrosJson) throws IOException {
        LOGGER.debug("{}.validaLecturaPorCodigo()", this.getClass().getCanonicalName());
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode resultNode = mapper.createObjectNode();
        JsonNode params = mapper.readTree(filtrosJson);
        if (!params.hasNonNull("usuario") || !params.hasNonNull("pass") || !params.hasNonNull("cantidad") || !params.hasNonNull("surtimientoInsumoExtendedList")
                || !params.hasNonNull("eliminaCodigoBarras") || !params.hasNonNull("codigoBarras") || !params.hasNonNull("surtimiento_Extend")) {
            resultNode.put("estatus", "ERROR");
            resultNode.put("mensaje", RESOURCES.getString("webservice.err.invalidRequest"));
            return Response.ok(resultNode.toString()).build();
        }
        String usuario = params.get("usuario").asText();
        String pass = params.get("pass").asText();
        int cantidad = params.get("cantidad").asInt();
        boolean eliminaCodigoBarras = params.get("eliminaCodigoBarras").asBoolean();
        String codigoBarras = params.get("codigoBarras").asText();
        JsonNode jn = params.get("surtimiento_Extend");
        Surtimiento_Extend surtimientoExtend = mapper.readValue(jn.toString(), Surtimiento_Extend.class);

        jn = params.get("surtimientoInsumoExtendedList");
        List<SurtimientoInsumo_Extend> surtimientoInsumoExtendedList = mapper.readValue(jn.toString(), new TypeReference<List<SurtimientoInsumo_Extend>>() {
        });

        Usuario usuarioParam = new Usuario();
        Usuario usuarioSelected = null;
        usuarioParam.setNombreUsuario(usuario);
        try {
            usuarioSelected = this.usuarioService.verificaSiExisteUser(usuarioParam);
        } catch (Exception ex) {
            resultNode.put("estatus", "EXCEPCION");
            resultNode.put("mensaje", ex.getMessage());
            LOGGER.error("{}.validaLecturaPorCodigo(): {} ", this.getClass().getCanonicalName(), ex.getMessage());
        }
        if (usuarioSelected != null) {
            if (!CustomWebSecurityConfigurerAdapter.customPasswordEncoder().matches(pass, usuarioSelected.getClaveAcceso())) {
                resultNode.put("estatus", "ERROR");
                resultNode.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
            } else {
                try {
                    if (codigoBarras == null || codigoBarras.trim().isEmpty()) {
                        resultNode.put("estatus", "ERROR");
                        resultNode.put("mensaje", RESOURCES.getString("sur.codigoincorrecto"));
                    } else if (surtimientoExtend == null || surtimientoInsumoExtendedList == null || surtimientoInsumoExtendedList.isEmpty()) {
                        resultNode.put("estatus", "ERROR");
                        resultNode.put("mensaje", RESOURCES.getString("sur.incorrecto"));
                    } else if (eliminaCodigoBarras) {
                        resultNode = eliminarLotePorCodigo(codigoBarras, cantidad, surtimientoInsumoExtendedList);
                    } else {
                        resultNode = agregarLotePorCodigo(codigoBarras, cantidad, usuarioSelected, surtimientoExtend, surtimientoInsumoExtendedList);
                    }
                } catch (Exception ex) {
                    resultNode.put("estatus", "EXCEPCION");
                    resultNode.put("mensaje", ex.getMessage());
                    LOGGER.error("{}.validaLecturaPorCodigo():  {}", this.getClass().getCanonicalName(), ex.getMessage());
                }
            }
        } else {
            resultNode.put("estatus", "ERROR");
            resultNode.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
        }
        return Response.ok(resultNode.toString()).build();
    }

    /**
     * Equivalente del método DispensacionMB.validaSurtimiento() Ejecuta la
     * acción de surtir sobre el Surtimiento Programado
     *
     * @param filtrosJson Cadena JSON con las credenciales del usuario, el
     * surtimiento programado y el registro de medicamentos por surtir
     * @return Respuesta en formato JSON
     * @throws java.io.IOException
     */
    @POST
    @Path("validaSurtimiento")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response validaSurtimiento(String filtrosJson) throws IOException {
        LOGGER.trace("{}.validaSurtimiento()", this.getClass().getCanonicalName());
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode validacion = mapper.createObjectNode();
        JsonNode params = mapper.readTree(filtrosJson);
        boolean parcial = false;
        if (!params.hasNonNull("usuario") || !params.hasNonNull("pass")
                || !params.hasNonNull("surtimientoInsumoExtendedList") || !params.hasNonNull("surtimiento_Extend")) {
            validacion.put("estatus", "ERROR");
            validacion.put("mensaje", RESOURCES.getString("webservice.err.invalidRequest"));
            return Response.ok(validacion.toString()).build();
        }
        String usuario = params.get("usuario").asText();
        String pass = params.get("pass").asText();
        JsonNode jn = params.get("surtimiento_Extend");
        Surtimiento_Extend surtimientoExtendedSelected = mapper.readValue(jn.toString(), Surtimiento_Extend.class);
        jn = params.get("surtimientoInsumoExtendedList");
        List<SurtimientoInsumo_Extend> surtimientoInsumoExtendedList = mapper.readValue(jn.toString(), new TypeReference<List<SurtimientoInsumo_Extend>>() {
        });

        Usuario usuarioParam = new Usuario();
        Usuario usuarioSelected;
        usuarioParam.setNombreUsuario(usuario);
        try {
            usuarioSelected = this.usuarioService.verificaSiExisteUser(usuarioParam);
        } catch (Exception ex) {
            validacion.put("estatus", "EXCEPCION");
            validacion.put("mensaje", ex.getMessage());
            LOGGER.error("{}.validaSurtimiento(): {}", this.getClass().getCanonicalName(), ex.getMessage());
            return Response.ok(validacion.toString()).build();
        }
        if (usuarioSelected != null
                && !CustomWebSecurityConfigurerAdapter.customPasswordEncoder().matches(pass, usuarioSelected.getClaveAcceso())) {
            validacion.put("estatus", "ERROR");
            validacion.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
            return Response.ok(validacion.toString()).build();
        }
        boolean status = Constantes.INACTIVO;
        obtenerDatosSistema();
        int noDiasCaducidad = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_DIASPARACADUCIDAD);
        try {
            if (surtimientoExtendedSelected == null) {
                validacion.put("mensaje", RESOURCES.getString("sur.invalido"));
            } else if (surtimientoInsumoExtendedList == null) {
                validacion.put("mensaje", RESOURCES.getString("sur.invalido"));
            } else if (surtimientoInsumoExtendedList.isEmpty()) {
                validacion.put("mensaje", RESOURCES.getString("sur.invalido"));
            } else {

                Integer medicamentosSurtidos = 0;
                Surtimiento surtimiento;
                List<SurtimientoInsumo> surtimientoInsumoList = new ArrayList<>();
                List<SurtimientoInsumo> surtimientoInsumoListParcial = new ArrayList<>();
                Surtimiento surtiminetoParcial = new Surtimiento();
                List<SurtimientoEnviado> surtimientoEnviadoList = new ArrayList<>();

                Inventario inventarioAfectado;
                List<Inventario> inventarioList = new ArrayList<>();

                List<MovimientoInventario> movimientoInventarioList = new ArrayList<>();
                MovimientoInventario movimientoInventarioAfectado;

                for (SurtimientoInsumo_Extend surtimientoInsumo_Ext : surtimientoInsumoExtendedList) {
                    if (surtimientoInsumo_Ext.getCantidadEnviada() == null) {
                        surtimientoInsumo_Ext.setCantidadEnviada(0);
                    }
                    if (surtimientoInsumo_Ext.getSurtimientoEnviadoExtendList() != null
                            && !surtimientoInsumo_Ext.getSurtimientoEnviadoExtendList().isEmpty()) {
                        if (surtimientoInsumo_Ext.getCantidadEnviada().intValue() != surtimientoInsumo_Ext.getCantidadSolicitada().intValue()
                                && surtimientoInsumo_Ext.getIdTipoJustificante() == null) {
                            validacion.put("estatus", "ERROR");
                            validacion.put("mensaje", RESOURCES.getString("dispensacion.err.surtmedicamento"));
                            return Response.ok(validacion.toString()).build();
                        }
                        Inventario inventarioSurtido;
                        for (SurtimientoEnviado_Extend surtimientoEnviado_Ext : surtimientoInsumo_Ext.getSurtimientoEnviadoExtendList()) {
                            if (surtimientoEnviado_Ext.getIdInventarioSurtido() != null) {

                                inventarioSurtido = inventarioService.obtener(new Inventario(surtimientoEnviado_Ext.getIdInventarioSurtido()));

                                if (inventarioSurtido == null) {
                                    validacion.put("estatus", "ERROR");
                                    validacion.put("mensaje", RESOURCES.getString("sur.loteincorrecto"));
                                    return Response.ok(validacion.toString()).build();
                                } else if (inventarioSurtido.getActivo() != 1) {
                                    validacion.put("estatus", "ERROR");
                                    validacion.put("mensaje", RESOURCES.getString("sur.lotebloqueado"));
                                    return Response.ok(validacion.toString()).build();
                                } else if (FechaUtil.sumarRestarDiasFecha(new java.util.Date(), noDiasCaducidad).after(inventarioSurtido.getFechaCaducidad())) {
                                    validacion.put("estatus", "ERROR");
                                    validacion.put("mensaje", RESOURCES.getString("sur.caducidadvencida"));
                                    return Response.ok(validacion.toString()).build();
                                } else if (inventarioSurtido.getCantidadActual() < surtimientoEnviado_Ext.getCantidadEnviado()) {
                                    validacion.put("estatus", "ERROR");
                                    validacion.put("mensaje", RESOURCES.getString("sur.cantidadinsuficiente"));
                                    return Response.ok(validacion.toString()).build();
                                } else {
                                    medicamentosSurtidos++;
                                    inventarioAfectado = new Inventario();
                                    inventarioAfectado.setIdInventario(surtimientoEnviado_Ext.getIdInventarioSurtido());
                                    inventarioAfectado.setCantidadActual(surtimientoEnviado_Ext.getCantidadEnviado());
                                    inventarioList.add(inventarioAfectado);
                                    movimientoInventarioAfectado = new MovimientoInventario();
                                    movimientoInventarioAfectado.setIdMovimientoInventario(Comunes.getUUID());
                                    Integer idTipoMotivo = TipoMotivo_Enum.SAL_PRESC_POR_SURTIMIENTO_DE_PRESCRIPCION.getMotivoValue();
                                    movimientoInventarioAfectado.setIdTipoMotivo(idTipoMotivo);
                                    movimientoInventarioAfectado.setFecha(new java.util.Date());
                                    movimientoInventarioAfectado.setIdEstrutcuraOrigen(usuarioSelected.getIdEstructura());
                                    movimientoInventarioAfectado.setIdEstrutcuraDestino(surtimientoExtendedSelected.getIdEstructura());
                                    movimientoInventarioAfectado.setIdUsuarioMovimiento(usuarioSelected.getIdUsuario());
                                    movimientoInventarioAfectado.setIdInventario(surtimientoEnviado_Ext.getIdInventarioSurtido());
                                    movimientoInventarioAfectado.setCantidad(surtimientoEnviado_Ext.getCantidadEnviado());
                                    movimientoInventarioAfectado.setFolioDocumento(surtimientoExtendedSelected.getFolio());
                                    movimientoInventarioList.add(movimientoInventarioAfectado);
                                }
                            }
                            surtimientoEnviado_Ext.setInsertFecha(new java.util.Date());
                            surtimientoEnviado_Ext.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.SURTIDO.getValue());
                            surtimientoEnviado_Ext.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                            if (surtimientoEnviado_Ext.getCantidadRecibido() == null) {
                                surtimientoEnviado_Ext.setCantidadRecibido(0);
                            }
                            surtimientoEnviadoList.add((SurtimientoEnviado) surtimientoEnviado_Ext);
                        }
                    }
                    SurtimientoInsumo surtInsumo = new SurtimientoInsumo();
                    surtInsumo.setIdSurtimientoInsumo(surtimientoInsumo_Ext.getIdSurtimientoInsumo());
                    surtInsumo.setUpdateFecha(new java.util.Date());
                    surtInsumo.setIdUsuarioAutCanRazn(surtimientoInsumo_Ext.getIdUsuarioAutCanRazn());
                    surtInsumo.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.SURTIDO.getValue());
                    surtInsumo.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
                    surtInsumo.setFechaEnviada(surtimientoInsumo_Ext.getFechaEnviada());
                    surtInsumo.setIdUsuarioEnviada(usuarioSelected.getIdUsuario());
                    surtInsumo.setCantidadEnviada(surtimientoInsumo_Ext.getCantidadEnviada());
                    surtInsumo.setIdTipoJustificante(surtimientoInsumo_Ext.getIdTipoJustificante());
                    surtInsumo.setNotas(surtimientoInsumo_Ext.getNotas());
                    surtInsumo.setCantidadVale(0);
                    surtimientoInsumoList.add(surtInsumo);

                    if (surtimientoInsumo_Ext.getCantidadEnviada() < surtimientoInsumo_Ext.getCantidadSolicitada()) {
                        if (!parcial) {
                            surtiminetoParcial.setIdSurtimiento(Comunes.getUUID());
                            surtiminetoParcial.setIdEstructuraAlmacen(surtimientoExtendedSelected.getIdEstructuraAlmacen());
                            DecimalFormat twodigits = new DecimalFormat("00");
                            if (surtimientoExtendedSelected.isParcial()) {
                                String str = surtimientoExtendedSelected.getFolio().substring(0, surtimientoExtendedSelected.getFolio().length() - 2);
                                String s = surtimientoExtendedSelected.getFolio().split(str)[1];
                                int n = Integer.valueOf(s);
                                n++;
                                surtiminetoParcial.setFolio(str + twodigits.format(n));
                            } else {
                                String f = surtimientoExtendedSelected.getFolio();
                                surtiminetoParcial.setFolio(f + twodigits.format(1));
                            }
                            surtiminetoParcial.setIdPrescripcion(surtimientoExtendedSelected.getIdPrescripcion());
                            surtiminetoParcial.setFechaProgramada(surtimientoExtendedSelected.getFechaProgramada());
                            surtiminetoParcial.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                            surtiminetoParcial.setIdEstatusMirth(surtimientoExtendedSelected.getIdEstatusMirth());
                            surtiminetoParcial.setInsertFecha(new Date());
                            surtiminetoParcial.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                            surtiminetoParcial.setParcial(true);
                            parcial = true;
                        }
                        SurtimientoInsumo surtimientoInsumoParcial = new SurtimientoInsumo();
                        surtimientoInsumoParcial.setIdSurtimientoInsumo(Comunes.getUUID());
                        surtimientoInsumoParcial.setIdSurtimiento(surtiminetoParcial.getIdSurtimiento());
                        surtimientoInsumoParcial.setIdPrescripcionInsumo(surtimientoInsumo_Ext.getIdPrescripcionInsumo());
                        surtimientoInsumoParcial.setFechaProgramada(surtimientoInsumo_Ext.getFechaProgramada());
                        surtimientoInsumoParcial.setCantidadSolicitada(surtimientoInsumo_Ext.getCantidadSolicitada() - surtimientoInsumo_Ext.getCantidadEnviada());
                        surtimientoInsumoParcial.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                        surtimientoInsumoParcial.setInsertFecha(new Date());
                        surtimientoInsumoParcial.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                        surtimientoInsumoParcial.setIdEstatusMirth(surtimientoInsumo_Ext.getIdEstatusMirth());
                        surtimientoInsumoParcial.setNumeroMedicacion(surtimientoInsumo_Ext.getNumeroMedicacion());
                        surtimientoInsumoListParcial.add(surtimientoInsumoParcial);
                    }
                }

                if (medicamentosSurtidos == 0) {
                    validacion.put("estatus", "ERROR");
                    validacion.put("mensaje", RESOURCES.getString("sur.error"));
                    return Response.ok(validacion.toString()).build();
                } else {

                    surtimientoExtendedSelected.setIdEstructuraAlmacen(usuarioSelected.getIdEstructura());
                    surtimientoExtendedSelected.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.SURTIDO.getValue());
                    surtimientoExtendedSelected.setUpdateFecha(new java.util.Date());
                    surtimientoExtendedSelected.setUpdateIdUsuario(usuarioSelected.getIdUsuario());

                    surtimiento = (Surtimiento) surtimientoExtendedSelected;

                    status = surtimientoService.surtirPrescripcion(
                            surtimiento,
                            surtimientoInsumoList,
                            surtimientoEnviadoList,
                            inventarioList,
                            movimientoInventarioList);
                    if (status && parcial) {
                        surtimientoService.surtimientoParcial(surtiminetoParcial, surtimientoInsumoListParcial);
                    }

                    if (!status) {
                        validacion.put("mensaje", RESOURCES.getString("sur.error"));
                    }
                }
            }
        } catch (Exception e) {
            validacion.put("mensaje", e.getMessage());
        }
        validacion.put("estatus", (status ? "OK" : "ERROR"));
        return Response.ok(validacion.toString()).build();
    }

    /**
     * Obtiene la lista de Tipos de Cancelación de Surtimiento
     *
     * @param filtrosJson Cadena JSON con las credenciales del usuario
     * @return Respuesta en formato JSON
     * @throws java.io.IOException
     */
    @POST
    @Path("obtenerTiposCancelacion")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerTiposCancelacion(String filtrosJson) throws IOException {
        LOGGER.trace("{}.obtenerTiposCancelacion()", this.getClass().getCanonicalName());
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode tiposCanc = mapper.createObjectNode();
        JsonNode params = mapper.readTree(filtrosJson);
        if (!params.hasNonNull("usuario") || !params.hasNonNull("pass")) {
            tiposCanc.put("estatus", "ERROR");
            tiposCanc.put("mensaje", RESOURCES.getString("webservice.err.invalidRequest"));
            return Response.ok(tiposCanc.toString()).build();
        }
        String usuario = params.get("usuario").asText();
        String pass = params.get("pass").asText();
        Usuario usuarioParam = new Usuario();
        Usuario usuarioSelected;
        usuarioParam.setNombreUsuario(usuario);
        try {
            usuarioSelected = this.usuarioService.verificaSiExisteUser(usuarioParam);
        } catch (Exception ex) {
            tiposCanc.put("estatus", "EXCEPCION");
            tiposCanc.put("mensaje", ex.getMessage());
            LOGGER.error("{}.obtenerTiposCancelacion(): {}", this.getClass().getCanonicalName(), ex.getMessage());
            return Response.ok(tiposCanc.toString()).build();
        }
        if (usuarioSelected != null
                && !CustomWebSecurityConfigurerAdapter.customPasswordEncoder().matches(pass, usuarioSelected.getClaveAcceso())) {
            tiposCanc.put("estatus", "ERROR");
            tiposCanc.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
            return Response.ok(tiposCanc.toString()).build();
        }
        try {
            TipoCancelacion tipoCan = new TipoCancelacion();
            List<TipoCancelacion> listaTiposCancelacion = tipoCancelacionService.obtenerLista(tipoCan);
            tiposCanc.put("estatus", "OK");
            tiposCanc.set("registros", mapper.valueToTree(listaTiposCancelacion));
        } catch (Exception e) {
            tiposCanc.put("estatus", "ERROR");
            tiposCanc.put("mensaje", e.getMessage());
        }
        return Response.ok(tiposCanc.toString()).build();
    }

    /**
     * Ejecuta la acción de cancelar el Surtimiento Programado
     *
     * @param filtrosJson Cadena JSON con las credenciales del usuario y el
     * surtimiento programado
     * @return Respuesta en formato JSON
     * @throws java.io.IOException
     */
    @POST
    @Path("cancelaSurtimiento")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cancelaSurtimiento(String filtrosJson) throws IOException {
        LOGGER.trace("{}.cancelaSurtimiento()", this.getClass().getCanonicalName());
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode cancelacion = mapper.createObjectNode();
        JsonNode params = mapper.readTree(filtrosJson);
        if (!params.hasNonNull("usuario") || !params.hasNonNull("pass") || !params.hasNonNull("tipoCancelacion")
                || !params.hasNonNull("resurtir") || !params.hasNonNull("folio")) {
            cancelacion.put("estatus", "ERROR");
            cancelacion.put("mensaje", RESOURCES.getString("webservice.err.invalidRequest"));
            return Response.ok(cancelacion.toString()).build();
        }
        String usuario = params.get("usuario").asText();
        String pass = params.get("pass").asText();
        boolean resurtir = params.get("resurtir").asBoolean();
        String folio = params.get("folio").asText();
        JsonNode jn = params.get("tipoCancelacion");
        TipoCancelacion tipoCancelacion = mapper.readValue(jn.toString(), TipoCancelacion.class);

        Usuario usuarioParam = new Usuario();
        Usuario usuarioSelected;
        usuarioParam.setNombreUsuario(usuario);
        try {
            usuarioSelected = this.usuarioService.verificaSiExisteUser(usuarioParam);
        } catch (Exception ex) {
            cancelacion.put("estatus", "EXCEPCION");
            cancelacion.put("mensaje", ex.getMessage());
            LOGGER.error("{}.cancelaSurtimiento(): {}", this.getClass().getCanonicalName(), ex.getMessage());
            return Response.ok(cancelacion.toString()).build();
        }
        if (usuarioSelected != null
                && !CustomWebSecurityConfigurerAdapter.customPasswordEncoder().matches(pass, usuarioSelected.getClaveAcceso())) {
            cancelacion.put("estatus", "ERROR");
            cancelacion.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
            return Response.ok(cancelacion.toString()).build();
        }
        boolean status = true;
        try {
            if (folio == null || folio.isEmpty()) {
                cancelacion.put("mensaje", RESOURCES.getString("sur.invalido"));
            } else {
                Surtimiento surtimiento = surtimientoService.obtenerPorFolio(folio);
                List<SurtimientoEnviado> surtimientoEnviadoList = surtimientoService.obtenerListaSurtimientoEnviadoPorIdSurtimiento(surtimiento.getIdSurtimiento());
                if (resurtir) {
                    String folioOriginal = surtimiento.getFolio();
                    String folioCancelado = "C" + folioOriginal;
                    surtimiento.setFolio(folioCancelado);
                    surtimientoService.actualizarFolio(surtimiento.getIdSurtimiento(), folioCancelado);
                    status = surtimientoService.clonarSurtimientoCancelado(surtimiento.getIdSurtimiento(), folioOriginal);
                    if (status) {
                        status = surtimientoInsumoService.clonarSurtimientoInsumoCancelado(surtimiento.getIdSurtimiento(), folioOriginal);
                    }
                }
                if (status) {
                    status = inventarioService.revertirInventario(surtimiento, surtimientoEnviadoList, usuarioSelected);
                    if (status) {
                        surtimientoService.actualizarTipoCancelacion(surtimiento.getIdSurtimiento(), tipoCancelacion.getIdTipoCancelacion());
                    }
                }
                if (!status) {
                    cancelacion.put("mensaje", RESOURCES.getString("sur.error"));
                }
            }
        } catch (Exception e) {
            status = false;
            cancelacion.put("mensaje", e.getMessage());
        }
        cancelacion.put("estatus", (status ? "OK" : "ERROR"));
        return Response.ok(cancelacion.toString()).build();
    }

    private RepSurtimientoPresc obtenerDatosReporte(Surtimiento_Extend surtimientoExtendedSelected) {
        RepSurtimientoPresc repSurtimientoPrescripcion = new RepSurtimientoPresc();
        repSurtimientoPrescripcion.setUnidadHospitalaria("");
        repSurtimientoPrescripcion.setClasificacionPresupuestal("");
        try {
            Estructura est = estructuraService.obtenerEstructura(surtimientoExtendedSelected.getIdEstructura());
            repSurtimientoPrescripcion.setClasificacionPresupuestal(est.getClavePresupuestal() == null ? "" : est.getClavePresupuestal());
            EntidadHospitalaria entidad = entidadHospitalariaService.obtenerEntidadById(est.getIdEntidadHospitalaria());
            if (entidad != null) {
                repSurtimientoPrescripcion.setUnidadHospitalaria(entidad.getNombre());
            }
            repSurtimientoPrescripcion.setPiso(est.getUbicacion());

            Surtimiento surt = surtimientoService.obtenerPorFolio(surtimientoExtendedSelected.getFolio());
            repSurtimientoPrescripcion.setFechaSolicitado(surt.getFechaProgramada());
            repSurtimientoPrescripcion.setIdEstatusSurtimiento(surt.getIdEstatusSurtimiento());

            SurtimientoInsumo si = new SurtimientoInsumo();
            si.setIdSurtimiento(surt.getIdSurtimiento());
            List<SurtimientoInsumo> lsi = surtimientoInsumoService.obtenerLista(si);
            if (lsi != null && !lsi.isEmpty()) {
                si = lsi.get(0);
                repSurtimientoPrescripcion.setFechaAtendido(si.getFechaEnviada());
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }

        repSurtimientoPrescripcion.setFolioPrescripcion(surtimientoExtendedSelected.getFolioPrescripcion());
        repSurtimientoPrescripcion.setFolioSurtimiento(surtimientoExtendedSelected.getFolio());
        repSurtimientoPrescripcion.setFechaActual(new Date());
        repSurtimientoPrescripcion.setNombrePaciente(surtimientoExtendedSelected.getNombrePaciente());
        repSurtimientoPrescripcion.setClavePaciente(surtimientoExtendedSelected.getClaveDerechohabiencia());
        repSurtimientoPrescripcion.setServicio(surtimientoExtendedSelected.getNombreEstructura());
        repSurtimientoPrescripcion.setCama(surtimientoExtendedSelected.getCama());
        repSurtimientoPrescripcion.setTurno(surtimientoExtendedSelected.getTurno());
        return repSurtimientoPrescripcion;
    }

    /**
     * Obtiene el archivo PDF del Ticket de Surtimiento
     *
     * @param filtrosJson Cadena JSON con las credenciales del usuario
     * @return Stream del archivo PDF
     * @throws java.io.IOException
     */
    @POST
    @Path("obtenerTicketSurtimiento")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response obtenerTicketSurtimiento(String filtrosJson) throws IOException {
        LOGGER.trace("{}.obtenerTicketSurtimiento()", this.getClass().getCanonicalName());
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode ticket = mapper.createObjectNode();
        JsonNode params = mapper.readTree(filtrosJson);
        if (!params.hasNonNull("usuario") || !params.hasNonNull("pass") || !params.hasNonNull("surtimiento_Extend")) {
            ticket.put("estatus", "ERROR");
            ticket.put("mensaje", RESOURCES.getString("webservice.err.invalidRequest"));
            return Response.ok(ticket.toString()).build();
        }
        String usuario = params.get("usuario").asText();
        String pass = params.get("pass").asText();
        JsonNode jn = params.get("surtimiento_Extend");
        Surtimiento_Extend surtimientoExtendedSelected = mapper.readValue(jn.toString(), Surtimiento_Extend.class);
        Usuario usuarioParam = new Usuario();
        Usuario usuarioSelected;
        usuarioParam.setNombreUsuario(usuario);
        try {
            usuarioSelected = this.usuarioService.verificaSiExisteUser(usuarioParam);
        } catch (Exception ex) {
            ticket.put("estatus", "EXCEPCION");
            ticket.put("mensaje", ex.getMessage());
            LOGGER.error("{}.obtenerTicketSurtimiento(): {}", this.getClass().getCanonicalName(), ex.getMessage());
            return Response.ok(ticket.toString()).build();
        }
        if (usuarioSelected != null
                && !CustomWebSecurityConfigurerAdapter.customPasswordEncoder().matches(pass, usuarioSelected.getClaveAcceso())) {
            ticket.put("estatus", "ERROR");
            ticket.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
            return Response.ok(ticket.toString()).build();
        }
        InputStream is;
        try {
            RepSurtimientoPresc repSurtimientoPresc = obtenerDatosReporte(surtimientoExtendedSelected);
            EstatusSurtimiento_Enum estatusSurtimiento = EstatusSurtimiento_Enum.getStatusFromId(surtimientoExtendedSelected.getIdEstatusSurtimiento());
            byte[] buffer = reportesService.imprimeSurtimientoPrescInt(repSurtimientoPresc, estatusSurtimiento.name(),
                    surtimientoExtendedSelected.getDetalle() != null ? surtimientoExtendedSelected.getDetalle().size() : 0);
            is = new ByteArrayInputStream(buffer);
        } catch (Exception e) {
            String msg = "ERROR: " + e.getMessage();
            is = new ByteArrayInputStream(msg.getBytes());
        }
        return Response.ok(is).build();
    }

    public CupsPrinter obtenImpresoraCups(String descripcion) {
        CupsPrinter cupsPrinter = null;
        obtenerDatosSistema();
        int cupsPort = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_CUPSPRINT_PORT);
        String cupsIP = Comunes.obtenValorConfiguracion(configList, Constantes.PARAM_SYSTEM_CUPSPRINT_IP);
        try {
            if (!cupsIP.isEmpty()) {
                CupsClient cupsClient = new CupsClient(cupsIP, cupsPort);
                List<CupsPrinter> cupsPrinters = cupsClient.getPrinters();
                for (CupsPrinter cp : cupsPrinters) {
                    if (cp.getName().equals(descripcion)) {
                        cupsPrinter = cp;
                        break;
                    }
                }
                if (cupsPrinter == null) {
                    cupsPrinter = cupsClient.getDefaultPrinter();
                }
            }
        } catch (Exception ex) {
            LOGGER.error("{}.obtenImpresoras(): {}", this.getClass().getCanonicalName(), ex.getMessage());
        }
        return cupsPrinter;
    }

    private boolean imprimeSurtimiento(InputStream is, String impresora) {
        boolean estatus = false;
        CupsPrinter cupsPrinter = obtenImpresoraCups(impresora);
        if (cupsPrinter != null) {
            try {
                String pageMedia = "letter";
                List<String> mediaSupportedList = cupsPrinter.getMediaSupported();
                for (String media : mediaSupportedList) {
                    if (media.toLowerCase().contains("letter")) {
                        pageMedia = media;
                        break;
                    }
                }
                HashMap<String, String> map = new HashMap<>();
                map.put("document-format", "application/vnd.cups-raw");
                map.put("job-originating-user-name", "mus-movil");
                map.put("job-attributes", "media:keyword:" + pageMedia);

                PrintJob printJob = new PrintJob.Builder(is)
                        .userName("mus-movil")
                        .jobName("mus-surt-job")
                        .copies(1)
                        .pageRanges("1-1")
                        .duplex(false)
                        .portrait(true)
                        .color(true)
                        .attributes(map)
                        .build();
                PrintRequestResult printRequestResult = cupsPrinter.print(printJob);
                int jobID = printRequestResult.getJobId();
                LOGGER.info("Prescripción enviada a: {}, jobID: {}", cupsPrinter.getPrinterURL(), jobID);
                estatus = printRequestResult.isSuccessfulResult();
            } catch (Exception ex) {
                LOGGER.error("{}.imprimeSurtimiento(): {}", this.getClass().getCanonicalName(), ex.getMessage());
            }
        }
        return estatus;
    }

    /**
     * Imprime el Ticket de Surtimiento
     *
     * @param filtrosJson Cadena JSON con las credenciales del usuario
     * @return Stream del archivo PDF
     * @throws java.io.IOException
     */
    @POST
    @Path("imprimirTicketSurtimiento")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response imprimirTicketSurtimiento(String filtrosJson) throws IOException {
        LOGGER.trace("{}.imprimirTicketSurtimiento()", this.getClass().getCanonicalName());
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode impresion = mapper.createObjectNode();
        JsonNode params = mapper.readTree(filtrosJson);
        if (!params.hasNonNull("usuario") || !params.hasNonNull("pass") || !params.hasNonNull("surtimiento_Extend") || !params.hasNonNull("impresora")) {
            impresion.put("estatus", "ERROR");
            impresion.put("mensaje", RESOURCES.getString("webservice.err.invalidRequest"));
            return Response.ok(impresion.toString()).build();
        }
        String usuario = params.get("usuario").asText();
        String pass = params.get("pass").asText();
        JsonNode jn = params.get("surtimiento_Extend");
        Surtimiento_Extend surtimientoExtendedSelected = mapper.readValue(jn.toString(), Surtimiento_Extend.class);
        String impresora = params.get("impresora").asText();
        Usuario usuarioParam = new Usuario();
        Usuario usuarioSelected;
        usuarioParam.setNombreUsuario(usuario);
        try {
            usuarioSelected = this.usuarioService.verificaSiExisteUser(usuarioParam);
        } catch (Exception ex) {
            impresion.put("estatus", "EXCEPCION");
            impresion.put("mensaje", ex.getMessage());
            LOGGER.error("{}.imprimirTicketSurtimiento(): {}", this.getClass().getCanonicalName(), ex.getMessage());
            return Response.ok(impresion.toString()).build();
        }
        if (usuarioSelected != null
                && !CustomWebSecurityConfigurerAdapter.customPasswordEncoder().matches(pass, usuarioSelected.getClaveAcceso())) {
            impresion.put("estatus", "ERROR");
            impresion.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
            return Response.ok(impresion.toString()).build();
        }
        InputStream is = null;
        try {
            RepSurtimientoPresc repSurtimientoPresc = obtenerDatosReporte(surtimientoExtendedSelected);
            EstatusSurtimiento_Enum estatusSurtimiento = EstatusSurtimiento_Enum.getStatusFromId(surtimientoExtendedSelected.getIdEstatusSurtimiento());
            byte[] buffer = reportesService.imprimeSurtimientoPrescInt(repSurtimientoPresc, estatusSurtimiento.name(),
                    surtimientoExtendedSelected.getDetalle() != null ? surtimientoExtendedSelected.getDetalle().size() : 0);
            is = new ByteArrayInputStream(buffer);
            if (imprimeSurtimiento(is, impresora)) {
                impresion.put("estatus", "OK");
            } else {
                impresion.put("estatus", "ERROR");
                impresion.put("mensaje", RESOURCES.getString("sur.error.impresion"));
            }
        } catch (Exception e) {
            impresion.put("estatus", "ERROR");
            impresion.put("mensaje", e.getMessage());
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return Response.ok(impresion.toString()).build();
    }

    /**
     * Equivalente del método DispensacionMB.obtenerSurtimientos() Obtiene la
     * lista de Prescripciones para la Sincronización de la Handheld
     *
     * @param filtrosJson Cadena JSON con el usuario firmado
     * @return Respuesta en formato JSON con la lista de Surtimientos
     * @throws java.io.IOException
     */
    @POST
    @Path("obtenerSurtimientosSync")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerSurtimientosSync(String filtrosJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode resSurtimientos = mapper.createObjectNode();
        JsonNode params = mapper.readTree(filtrosJson);
        if (!params.hasNonNull("usuario")) {
            resSurtimientos.put("estatus", "ERROR");
            resSurtimientos.put("mensaje", RESOURCES.getString("webservice.err.invalidRequest"));
            return Response.ok(resSurtimientos.toString()).build();
        }
        String usuario = params.get("usuario").asText();
        Usuario usuarioParam = new Usuario();
        Usuario usuarioSelected = null;
        usuarioParam.setNombreUsuario(usuario);
        try {
            usuarioSelected = this.usuarioService.verificaSiExisteUser(usuarioParam);
        } catch (Exception ex) {
            resSurtimientos.put("estatus", "EXCEPCION");
            resSurtimientos.put("mensaje", ex.getMessage());
            LOGGER.error("{}.obtenerSurtimientosSync(): {}", this.getClass().getCanonicalName(), ex.getMessage());
        }
        if (usuarioSelected != null) {
            if (usuarioSelected.getIdEstructura() == null) {
                resSurtimientos.put("estatus", "ERROR");
                resSurtimientos.put("mensaje", RESOURCES.getString("sur.sin.almacen"));
            } else {
                List<Estructura> listServiciosSurte = new ArrayList<>();
                try {
                    listServiciosSurte.addAll(estructuraService.obtenerServiciosAlmcenPorIdEstructura(usuarioSelected.getIdEstructura()));
                } catch (Exception ex) {
                    listServiciosSurte = null;
                    LOGGER.error("{}.obtenerSurtimientosSync(): Error al obtener Servicios que puede surtir el usuario. {}", this.getClass().getCanonicalName(), ex.getMessage());
                }
                try {
                    List<Integer> listEstatusPaciente = new ArrayList<>();
                    listEstatusPaciente.add(EstatusPaciente_Enum.ASIGNADO_A_SERVICIO.getValue());
                    listEstatusPaciente.add(EstatusPaciente_Enum.ASIGNADO_A_CAMA.getValue());

                    List<Integer> listEstatusPrescripcion = new ArrayList<>();
                    listEstatusPrescripcion.add(EstatusPrescripcion_Enum.PROGRAMADA.getValue());
                    listEstatusPrescripcion.add(EstatusPrescripcion_Enum.FINALIZADA.getValue());

                    List<Integer> listEstatusSurtimiento = new ArrayList<>();
                    listEstatusSurtimiento.add(EstatusSurtimiento_Enum.PROGRAMADO.getValue());

                    Date fechaProgramada = new java.util.Date();
                    ParamBusquedaReporte paramBusquedaReporte = new ParamBusquedaReporte();
                    paramBusquedaReporte.setNuevaBusqueda(true);

                    List<Surtimiento_Extend> registros = surtimientoService.obtenerPorFechaEstructuraPacienteCamaPrescripcionLazy(fechaProgramada, paramBusquedaReporte, -1, -1, null, listEstatusPaciente, listEstatusPrescripcion, listEstatusSurtimiento, listServiciosSurte);
                    resSurtimientos.put("estatus", "OK");
                    resSurtimientos.put("totalRegistros", 0);
                    resSurtimientos.set("registros", mapper.valueToTree(registros));
                } catch (Exception ex) {
                    resSurtimientos.put("estatus", "EXCEPCION");
                    resSurtimientos.put("mensaje", RESOURCES.getString("prc.pac.lista"));
                    LOGGER.error("{}.obtenerSurtimientosSync(): {} {}", this.getClass().getCanonicalName(), RESOURCES.getString("prc.pac.lista"), ex.getMessage());
                }
            }
        } else {
            resSurtimientos.put("estatus", "ERROR");
            resSurtimientos.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
        }
        return Response.ok(resSurtimientos.toString()).build();
    }

    /**
     * Equivalente del método DispensacionMB.verSurtimiento() Obtiene el detalle
     * de los surtimientos para la Sincronización de la Handheld
     *
     * @param filtrosJson Cadena JSON con el usuario firmado
     * @return Respuesta en formato JSON con la lista de Surtimientos
     * @throws java.io.IOException
     */
    @POST
    @Path("verSurtimientoSync")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response verSurtimientoSync(String filtrosJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode surtInsumoList = mapper.createObjectNode();
        JsonNode params = mapper.readTree(filtrosJson);
        if (!params.hasNonNull("usuario")) {
            surtInsumoList.put("estatus", "ERROR");
            surtInsumoList.put("mensaje", RESOURCES.getString("webservice.err.invalidRequest"));
            return Response.ok(surtInsumoList.toString()).build();
        }
        String usuario = params.get("usuario").asText();
        Usuario usuarioParam = new Usuario();
        Usuario usuarioSelected = null;
        usuarioParam.setNombreUsuario(usuario);
        try {
            usuarioSelected = this.usuarioService.verificaSiExisteUser(usuarioParam);
        } catch (Exception ex) {
            surtInsumoList.put("estatus", "EXCEPCION");
            surtInsumoList.put("mensaje", ex.getMessage());
            LOGGER.error("{}.verSurtimientoSync(): {}", this.getClass().getCanonicalName(), ex.getMessage());
        }
        if (usuarioSelected != null) {
            if (usuarioSelected.getIdEstructura() == null) {
                surtInsumoList.put("estatus", "ERROR");
                surtInsumoList.put("mensaje", RESOURCES.getString("sur.sin.almacen"));
            } else {
                List<SurtimientoInsumo_Extend> surtimientoInsumoExtendedList = obtenListaSurtimientoInsumo(usuarioSelected.getIdEstructura(), "OFFLINE", "OFFLINE", null);
                if (surtimientoInsumoExtendedList != null) {
                    surtInsumoList.put("estatus", "OK");
                    ArrayNode registrosNode = mapper.valueToTree(surtimientoInsumoExtendedList);
                    surtInsumoList.set("registros", registrosNode);
                } else {
                    surtInsumoList.put("estatus", "ERROR");
                    surtInsumoList.put("mensaje", RESOURCES.getString("sur.incorrecto"));
                    LOGGER.error("{}.verSurtimientoSync(): {}", this.getClass().getCanonicalName(), RESOURCES.getString("sur.incorrecto"));
                }
            }
        } else {
            surtInsumoList.put("estatus", "ERROR");
            surtInsumoList.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
        }
        return Response.ok(surtInsumoList.toString()).build();
    }
}
