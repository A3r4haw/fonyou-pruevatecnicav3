package mx.mc.ws.movil.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.io.IOException;
import java.text.SimpleDateFormat;
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
import mx.mc.enums.EstatusMinistracion_Enum;
import mx.mc.enums.EstatusPrescripcion_Enum;
import mx.mc.enums.EstatusSurtimiento_Enum;
import mx.mc.enums.TipoMotivo_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.init.CustomWebSecurityConfigurerAdapter;
import mx.mc.model.CodigoInsumo;
import mx.mc.model.Config;
import mx.mc.model.MedicamentoOff_Extended;
import mx.mc.model.Medicamento_Extended;
import mx.mc.model.MovimientoInventario;
import mx.mc.model.Paciente;
import mx.mc.model.Paciente_Extended;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.SurtimientoMinistrado;
import mx.mc.model.SurtimientoMinistrado_Extend;
import mx.mc.model.Surtimiento_Extend;
import mx.mc.model.TransaccionPermisos;
import mx.mc.model.Usuario;
import mx.mc.service.ConfigService;
import mx.mc.service.EstructuraService;
import mx.mc.service.MedicamentoService;
import mx.mc.service.PacienteService;
import mx.mc.service.SurtimientoMinistradoService;
import mx.mc.service.SurtimientoService;
import mx.mc.service.TransaccionService;
import mx.mc.service.UsuarioService;
import mx.mc.util.CodigoBarras;
import mx.mc.util.Comunes;
import mx.mc.util.FechaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Implementa las interfaces REST para la Ministracion de Medicamentos
 *
 * @author Alberto Palacios
 * @version 1.0
 * @since 2019-03-01
 */
@Path("ministracion")
public class MinistrarMedicamentoMBMovil extends SpringBeanAutowiringSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(MinistrarMedicamentoMBMovil.class);
    protected static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private List<Config> configList;
    private Integer ministraPrevDays = 0;
    private Integer ministraLaterHours = 0;
    private List<Paciente_Extended> pacientesList;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TransaccionService transaccionService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private MedicamentoService medicamentoService;

    @Autowired
    private SurtimientoMinistradoService surtimientoMinistradoService;
    
    @Autowired
    private SurtimientoService surtimientoService;

    @Autowired
    private EstructuraService estructuraService;

    @Autowired
    private ConfigService configService;

    @PostConstruct
    public void init() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    /**
     * Crea una nueva instancia de la clase
     */
    public MinistrarMedicamentoMBMovil() {
        //No code needed in constructor
    }

    /**
     * Equivalente del método MinistrarMedicamentoMB.consultaPermisoUsuario()
     * El usuario no se toma de la sesion, en su lugar se pasa como parámetro
     *
     * @param usuario POJO del Usuario
     * @return Lista de TransaccionPermisos
     */
    public List<TransaccionPermisos> consultaPermisoUsuario(Usuario usuario) {
        List<TransaccionPermisos> permisosTrans = null;
        try {
            permisosTrans = transaccionService.obtenerPermisosPorIdUsuario(usuario.getIdUsuario());
            if (permisosTrans != null && !permisosTrans.isEmpty()) {
                usuario.setPermisosList(permisosTrans);
            }
        } catch (Exception ex) {
            LOGGER.error("{}.consultaPermisoUsuario(): {} {}", this.getClass().getCanonicalName(), RESOURCES.getString("ses.obtener.datos"), ex.getMessage());
        }
        return permisosTrans;
    }

    /**
     * Equivalente del método SesionMB.obtenerDatosSistema() Obtiene la lista de
     * configuraciones del Sistema
     */
    private void obtenerDatosSistema() {
        LOGGER.debug("mx.mc.magedbean.DispensacionMB.obtenerDatosSistema()");
        Config c = new Config();
        c.setActiva(Constantes.ACTIVO);
        try {
            configList = configService.obtenerLista(c);
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString("sys.config.err"), ex);
        }
    }

    /**
     * Relacionado con el método
     * MinistrarMedicamentoMB.consultaPermisoUsuario() Obtiene una
     * representacion de los permisos del usuario para una Transaccion en
     * particular
     *
     * @param usuario POJO del Uuario
     * @param sufijoTransaccion Código de la Transacción
     * @return Objeto con la representacion de los permisos del Usuario
     */
    public PermisoUsuario obtenPermisoUsuario(Usuario usuario, String sufijoTransaccion) {
        LOGGER.debug("{}.obtenPermisoUsuario()", this.getClass().getCanonicalName());
        PermisoUsuario userPermission = new PermisoUsuario();
        if (usuario.getPermisosList() != null) {
            for (TransaccionPermisos item : usuario.getPermisosList()) {
                if (item.getCodigo().equals(sufijoTransaccion))
                    userPermission = mx.mc.ws.movil.util.Comunes.obtenPermiso(item.getAccion(), userPermission);
            }
        }
        return userPermission;
    }

    /**
     * Equivalente del método MinistrarMedicamentoMB.cargarListaPacientes()
     * Obtiene la lista de Pacientes con Surtimientos para ministrar
     *
     * @param filtrosJson Cadena JSON con las credenciales del usuario y la
     * cadena de búsqueda
     * @return Respuesta en formato JSON con la lista de Pacientes
     * @throws java.io.IOException
     */
    @POST
    @Path("cargarListaPacientes")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cargarListaPacientes(String filtrosJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode listaPacientes = mapper.createObjectNode();
        JsonNode wsParams = mapper.readTree(filtrosJson);
        if (!wsParams.hasNonNull("usuario") || !wsParams.hasNonNull("pass") || !wsParams.hasNonNull("cadenaBusqueda")) {
            listaPacientes.put("estatus", "ERROR");
            listaPacientes.put("mensaje", RESOURCES.getString("webservice.err.invalidRequest"));
            return Response.ok(listaPacientes.toString()).build();
        }
        String usuario = wsParams.get("usuario").asText();
        String pass = wsParams.get("pass").asText();
        String cadenaBusqueda = wsParams.get("cadenaBusqueda").asText().toUpperCase();

        Usuario usuarioParam = new Usuario();
        Usuario usuarioSelected = null;
        usuarioParam.setNombreUsuario(usuario);
        PermisoUsuario permisosUsuario = new PermisoUsuario();
        try {
            usuarioSelected = this.usuarioService.verificaSiExisteUser(usuarioParam);
        } catch (Exception ex) {
            listaPacientes.put("estatus", "EXCEPCION");
            listaPacientes.put("mensaje", ex.getMessage());
            LOGGER.error("{}.cargarListaPacientes(): {}", this.getClass().getCanonicalName(), ex.getMessage());
        }
        if (usuarioSelected != null) {
            if (!CustomWebSecurityConfigurerAdapter.customPasswordEncoder().matches(pass, usuarioSelected.getClaveAcceso())) {
                listaPacientes.put("estatus", "ERROR");
                listaPacientes.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
            } else {
                List<TransaccionPermisos> permisosList = consultaPermisoUsuario(usuarioSelected);
                if (permisosList != null && !permisosList.isEmpty()) {
                    usuarioSelected.setPermisosList(permisosList);
                    permisosUsuario = obtenPermisoUsuario(usuarioSelected, Transaccion_Enum.MINISTRMED.getSufijo());
                } else {
                    listaPacientes.put("estatus", "ERROR");
                    listaPacientes.put("mensaje", RESOURCES.getString("ses.obtener.datos"));
                }
                if (!permisosUsuario.isPuedeVer()) {
                    listaPacientes.put("estatus", "ERROR");
                    listaPacientes.put("mensaje", RESOURCES.getString("err.transaccion"));
                } else if (usuarioSelected.getIdEstructura() == null) {
                    listaPacientes.put("estatus", "ERROR");
                    listaPacientes.put("mensaje", RESOURCES.getString("sur.sin.almacen"));
                } else {
                    try {
                        List<Integer> listaEstatusPresc = new ArrayList<>();
                        listaEstatusPresc.add(EstatusPrescripcion_Enum.PROGRAMADA.getValue());
                        listaEstatusPresc.add(EstatusPrescripcion_Enum.FINALIZADA.getValue());
                        List<String> idsEstructura = estructuraService.obtenerIdsEstructuraJerarquica(usuarioSelected.getIdEstructura(), false);
                        if (idsEstructura == null) {
                            idsEstructura = new ArrayList<>();
                        }
                        idsEstructura.add(usuarioSelected.getIdEstructura());
                        pacientesList = pacienteService.obtenerPacietesConPrescripcion(Constantes.REGISTROS_PARA_MOSTRAR,
                                idsEstructura, cadenaBusqueda, listaEstatusPresc);
                        listaPacientes.put("estatus", "OK");
                        listaPacientes.set("registros", mapper.valueToTree(pacientesList));
                    } catch (Exception ex) {
                        listaPacientes.put("estatus", "EXCEPCION");
                        listaPacientes.put("mensaje", ex.getMessage());
                        LOGGER.error("{}.cargarListaPacientes() : {}", this.getClass().getCanonicalName(), ex.getMessage());
                    }
                }
            }
        } else {
            listaPacientes.put("estatus", "ERROR");
            listaPacientes.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
        }
        return Response.ok(listaPacientes.toString()).build();
    }

    /**
     * Equivalente del método MinistrarMedicamentoMB.cargarModalMinistracion()
     * Obtiene la lista de Medicamentos para ministrar al Paciente seleccionado
     *
     * @param filtrosJson Cadena JSON con las credenciales del usuario y el Id
     * del Paciente
     * @return Respuesta en formato JSON con la lista de Medicamentos
     * @throws java.io.IOException
     */
    @POST
    @Path("cargarModalMinistracion")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cargarModalMinistracion(String filtrosJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<MedicamentoOff_Extended> listaMedicamentos;
        List<String> pacienteList = new ArrayList<>();
        List<Paciente> pacienteListA;
        ObjectNode resMedicamentos = mapper.createObjectNode();
        JsonNode params = mapper.readTree(filtrosJson);
        if (!params.hasNonNull("usuario") || !params.hasNonNull("pass") || !params.hasNonNull("idPaciente")) {
            resMedicamentos.put("estatus", "ERROR");
            resMedicamentos.put("mensaje", RESOURCES.getString("webservice.err.invalidRequest"));
            return Response.ok(resMedicamentos.toString()).build();
        }
        String usuario = params.get("usuario").asText();
        String pass = params.get("pass").asText();
        String idPaciente = params.get("idPaciente").asText();

        Usuario usuarioParam = new Usuario();
        Usuario usuarioSelected = null;
        usuarioParam.setNombreUsuario(usuario);
        PermisoUsuario permisosUsuario = new PermisoUsuario();
        try {
            usuarioSelected = this.usuarioService.verificaSiExisteUser(usuarioParam);
        } catch (Exception ex) {
            resMedicamentos.put("estatus", "EXCEPCION");
            resMedicamentos.put("mensaje", ex.getMessage());
            LOGGER.error("{}.cargarModalMinistracion(): {}", this.getClass().getCanonicalName(), ex.getMessage());
        }
        if (usuarioSelected != null) {
            if (!CustomWebSecurityConfigurerAdapter.customPasswordEncoder().matches(pass, usuarioSelected.getClaveAcceso())) {
                resMedicamentos.put("estatus", "ERROR");
                resMedicamentos.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
            } else {
                List<TransaccionPermisos> permisosList = consultaPermisoUsuario(usuarioSelected);
                if (permisosList != null && !permisosList.isEmpty()) {
                    usuarioSelected.setPermisosList(permisosList);
                    permisosUsuario = obtenPermisoUsuario(usuarioSelected, Transaccion_Enum.MINISTRMED.getSufijo());
                } else {
                    resMedicamentos.put("estatus", "ERROR");
                    resMedicamentos.put("mensaje", RESOURCES.getString("ses.obtener.datos"));
                }
                if (!permisosUsuario.isPuedeVer()) {
                    resMedicamentos.put("estatus", "ERROR");
                    resMedicamentos.put("mensaje", RESOURCES.getString("err.transaccion"));
                } else if (usuarioSelected.getIdEstructura() == null) {
                    resMedicamentos.put("estatus", "ERROR");
                    resMedicamentos.put("mensaje", RESOURCES.getString("sur.sin.almacen"));
                } else {
                    try {
                        obtenerDatosSistema();
                        ministraPrevDays = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_MINISTRA_PREVDAYS);
                        ministraLaterHours = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_MINISTRA_LATERHOURS);

                        if (!idPaciente.equals("-1")) {
                            List<Integer> listEstatusMinistracion = new ArrayList<>();
                            listEstatusMinistracion.add(EstatusMinistracion_Enum.PENDIENTE.getValue());
                            List<Medicamento_Extended> listaMedicamentosa = medicamentoService.obtenerMedicamentosPorPrescripcion(idPaciente, Constantes.REGISTROS_PARA_MOSTRAR, listEstatusMinistracion, ministraPrevDays, ministraLaterHours);
                            resMedicamentos.put("estatus", "OK");
                            resMedicamentos.set("registros", mapper.valueToTree(listaMedicamentosa));
                        } else {
                            List<String> idsEstructura = estructuraService.obtenerIdsEstructuraJerarquica(usuarioSelected.getIdEstructura(), false);
                            if (idsEstructura == null) {
                                idsEstructura = new ArrayList<>();
                            }
                            List<Integer> listEstatusMinistracion = new ArrayList<>();
                            listEstatusMinistracion.add(EstatusMinistracion_Enum.PENDIENTE.getValue());
                            List<Integer> listaEstatusPresc = new ArrayList<>();
                            listaEstatusPresc.add(EstatusPrescripcion_Enum.PROGRAMADA.getValue());
                            listaEstatusPresc.add(EstatusPrescripcion_Enum.FINALIZADA.getValue());
                            idsEstructura.add(usuarioSelected.getIdEstructura());
                            pacienteListA = pacienteService.obtenerPacietesConPrescripcionList(Constantes.REGISTROS_PARA_MOSTRAR,
                                    idsEstructura, null, listaEstatusPresc);

                            for (int i = 0; i < pacienteListA.size(); i++) {
                                pacienteList.add(i, pacienteListA.get(i).getIdPaciente());
                            }
                            listaMedicamentos = medicamentoService.obtenerMedicamentosPorPrescripcionList(pacienteList, Constantes.REGISTROS_PARA_MOSTRAR, listEstatusMinistracion, ministraPrevDays, ministraLaterHours);
                            resMedicamentos.put("estatus", "OK");
                            resMedicamentos.set("registros", mapper.valueToTree(listaMedicamentos));
                        }
                    } catch (Exception ex) {
                        resMedicamentos.put("estatus", "EXCEPCION");
                        resMedicamentos.put("mensaje", ex.getMessage());
                        LOGGER.error("{}.cargarModalMinistracion() : {}", this.getClass().getCanonicalName(), ex.getMessage());
                    }
                }
            }
        } else {
            resMedicamentos.put("estatus", "ERROR");
            resMedicamentos.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
        }
        return Response.ok(resMedicamentos.toString()).build();
    }

    /**
     * Equivalente del método MinistrarMedicamentoMB.escanearCodigoQR() Valida
     * el código de Medicamento escaneado y lo marca para ser o nó ministrado
     *
     * @param filtrosJson Cadena JSON con las credenciales del usuario, el
     * código de barras y la lista de medicamentos
     * @return Respuesta en formato JSON con la lista de Medicamentos
     * @throws java.io.IOException
     */
    @POST
    @Path("escanearCodigoQR")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response escanearCodigoQR(String filtrosJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode resEscaneo = mapper.createObjectNode();
        JsonNode params = mapper.readTree(filtrosJson);
        if (!params.hasNonNull("usuario") || !params.hasNonNull("pass") || !params.hasNonNull("cantidad") || !params.hasNonNull("codigoBarras")
                || !params.hasNonNull("listaMedicamentos") || !params.hasNonNull("eliminaCodigoBarras")) {
            resEscaneo.put("estatus", "ERROR");
            resEscaneo.put("mensaje", RESOURCES.getString("webservice.err.invalidRequest"));
            return Response.ok(resEscaneo.toString()).build();
        }
        String usuario = params.get("usuario").asText();
        String pass = params.get("pass").asText();
        int cantMinistrar = params.get("cantidad").asInt();
        String codigoMedicamento = params.get("codigoBarras").asText();
        boolean eliminarCodigo = params.get("eliminaCodigoBarras").asBoolean();
        JsonNode json = params.get("listaMedicamentos");
        List<Medicamento_Extended> listaMedicamentos = mapper.readValue(json.toString(), new TypeReference<List<Medicamento_Extended>>() {});

        Usuario usuarioSelected = null;
        Usuario usuarioParam = new Usuario();
        usuarioParam.setNombreUsuario(usuario);
        PermisoUsuario permisosUsuario = new PermisoUsuario();
        try {
            usuarioSelected = this.usuarioService.verificaSiExisteUser(usuarioParam);
        } catch (Exception ex) {
            resEscaneo.put("estatus", "EXCEPCION");
            resEscaneo.put("mensaje", ex.getMessage());
            LOGGER.error("{}.escanearCodigoQR(): {}", this.getClass().getCanonicalName(), ex.getMessage());
        }
        if (usuarioSelected != null) {
            if (!CustomWebSecurityConfigurerAdapter.customPasswordEncoder().matches(pass, usuarioSelected.getClaveAcceso())) {
                resEscaneo.put("estatus", "ERROR");
                resEscaneo.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
            } else {
                List<TransaccionPermisos> permisosList = consultaPermisoUsuario(usuarioSelected);
                if (permisosList != null && !permisosList.isEmpty()) {
                    usuarioSelected.setPermisosList(permisosList);
                    permisosUsuario = obtenPermisoUsuario(usuarioSelected, Transaccion_Enum.MINISTRMED.getSufijo());
                } else {
                    resEscaneo.put("estatus", "ERROR");
                    resEscaneo.put("mensaje", RESOURCES.getString("ses.obtener.datos"));
                }
                if (!permisosUsuario.isPuedeVer()) {
                    resEscaneo.put("estatus", "ERROR");
                    resEscaneo.put("mensaje", RESOURCES.getString("err.transaccion"));
                } else if (usuarioSelected.getIdEstructura() == null) {
                    resEscaneo.put("estatus", "ERROR");
                    resEscaneo.put("mensaje", RESOURCES.getString("sur.sin.almacen"));
                } else {
                    try {
                        if (codigoMedicamento != null) {
                            obtenerDatosSistema();
                            int noDiasCaducidad = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_DIASPARACADUCIDAD);
                            Boolean isCodificacionGS1 = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_CODIGOS_GS1) == 1;
                            CodigoInsumo ci = CodigoBarras.parsearCodigoDeBarras(codigoMedicamento, isCodificacionGS1);
                            if (ci == null) {
                                resEscaneo.put("estatus", "ERROR");
                                resEscaneo.put("mensaje", RESOURCES.getString("err.parser"));
                            } else {
                                String claveMedicamento = ci.getClave();
                                String lote = ci.getLote();
                                Date fechaCaducidad = ci.getFecha();
                                boolean match = false;
                                boolean matchLote = false;
                                boolean excede = false;
                                int surtidos = 0;
                                for (Medicamento_Extended item : listaMedicamentos) {
                                    if (lote.equalsIgnoreCase(item.getLote()) || codigoMedicamento.equalsIgnoreCase(item.getCodigoBarras())) {
                                        matchLote = true;
                                    }
                                    if ((item.getEstatusMinistracion().equals(EstatusMinistracion_Enum.PENDIENTE.getValue()) && !eliminarCodigo)
                                            || (item.getEstatusMinistracion().equals(EstatusMinistracion_Enum.MINISTRADO.getValue()) && !eliminarCodigo
                                            && item.getCantidadMinistrada() < item.getCantidadActual())) {
                                        if ((claveMedicamento.equalsIgnoreCase(item.getClaveInstitucional()) && lote.equalsIgnoreCase(item.getLote())
                                                && fechaCaducidad.equals(item.getFechaCaducidad())) || codigoMedicamento.equalsIgnoreCase(item.getCodigoBarras())) {
                                            match = true;
                                            if (item.getCantidadActual() >= item.getCantidadMinistrada() + cantMinistrar) {
                                                item.setEstatusMinistracion(EstatusMinistracion_Enum.MINISTRADO.getValue());
                                                item.setCantidadMinistrada(item.getCantidadMinistrada() + cantMinistrar);
                                            } else {
                                                excede = true;
                                            }
                                            break;
                                        }
                                    } else {
                                        if (item.getEstatusMinistracion().equals(EstatusMinistracion_Enum.MINISTRADO.getValue()) && eliminarCodigo) {
                                            if ((claveMedicamento.equalsIgnoreCase(item.getClaveInstitucional()) && lote.equalsIgnoreCase(item.getLote()))
                                                || codigoMedicamento.equalsIgnoreCase(item.getCodigoBarras())) {
                                                item.setEstatusMinistracion(EstatusMinistracion_Enum.PENDIENTE.getValue());
                                                int cant = item.getCantidadMinistrada() - cantMinistrar;
                                                if (cant < 0) {
                                                    cant = 0;
                                                }
                                                item.setCantidadMinistrada(cant);
                                                match = true;
                                                break;
                                            }
                                        } else {
                                            surtidos++;
                                        }
                                    }
                                }
                                if (surtidos == listaMedicamentos.size()) {
                                    resEscaneo.put("estatus", "ERROR");
                                    resEscaneo.put("mensaje", RESOURCES.getString("minismedicam.err.sinMedicamen"));
                                } else if (!matchLote) {
                                    resEscaneo.put("estatus", "ERROR");
                                    resEscaneo.put("mensaje", RESOURCES.getString("minismedicam.loteincorrecto"));
                                } else if (FechaUtil.sumarRestarDiasFecha(new java.util.Date(), noDiasCaducidad).after(ci.getFecha())
                                        && !match) {
                                    resEscaneo.put("estatus", "ERROR");
                                    resEscaneo.put("mensaje", RESOURCES.getString("sur.caducidadvencida"));
                                } else if (!match) {
                                    resEscaneo.put("estatus", "ERROR");
                                    resEscaneo.put("mensaje", RESOURCES.getString("minismedicam.err.noCorresponde"));
                                } else if (excede) {
                                    resEscaneo.put("estatus", "ERROR");
                                    resEscaneo.put("mensaje", RESOURCES.getString("minismedicam.err.cantExcede"));
                                } else {
                                    resEscaneo.put("estatus", "OK");
                                    resEscaneo.set("registros", mapper.valueToTree(listaMedicamentos));
                                }
                            }
                        }
                    } catch (IllegalArgumentException ex) {
                        resEscaneo.put("estatus", "EXCEPCION");
                        resEscaneo.put("mensaje", ex.getMessage());
                        LOGGER.error("{}.escanearCodigoQR() : {}", this.getClass().getCanonicalName(), ex.getMessage());
                    }
                }
            }
        } else {
            resEscaneo.put("estatus", "ERROR");
            resEscaneo.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
        }
        return Response.ok(resEscaneo.toString()).build();
    }

    /**
     * Equivalente del método MinistrarMedicamentoMB.ministrarMedicamentos()
     * Realiza la acción de Ministrar los medicamentos seleccionados
     *
     * @param filtrosJson Cadena JSON con las credenciales del usuario y la
     * lista de medicamentos por ministrar
     * @return Respuesta en formato JSON
     * @throws java.io.IOException
     */
    @POST
    @Path("ministrarMedicamentos")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response ministrarMedicamentos(String filtrosJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode resultadoMinistrar = mapper.createObjectNode();
        JsonNode params = mapper.readTree(filtrosJson);
        if (!params.hasNonNull("usuario") || !params.hasNonNull("pass") || !params.hasNonNull("listaMedicamentos")) {
            resultadoMinistrar.put("estatus", "ERROR");
            resultadoMinistrar.put("mensaje", RESOURCES.getString("webservice.err.invalidRequest"));
            return Response.ok(resultadoMinistrar.toString()).build();
        }
        String usuario = params.get("usuario").asText();
        String pass = params.get("pass").asText();
        String fechaInicio = params.get("fechaInicio").asText();
        String fechaTermino = params.get("fechaTermino").asText();
        String comentarios = params.get("comentarios").asText();
        JsonNode jNode = params.get("listaMedicamentos");
        List<Medicamento_Extended> listaMedicamentos = mapper.readValue(jNode.toString(), new TypeReference<List<Medicamento_Extended>>() {});

        Usuario usuarioParam = new Usuario();
        Usuario usuarioSelected = null;
        usuarioParam.setNombreUsuario(usuario);
        PermisoUsuario permisosUsuario = new PermisoUsuario();
        try {
            usuarioSelected = this.usuarioService.verificaSiExisteUser(usuarioParam);
        } catch (Exception ex) {
            resultadoMinistrar.put("estatus", "EXCEPCION");
            resultadoMinistrar.put("mensaje", ex.getMessage());
            LOGGER.error("{}.ministrarMedicamentos(): {}", this.getClass().getCanonicalName(), ex.getMessage());
        }
        if (usuarioSelected != null) {
            if (!CustomWebSecurityConfigurerAdapter.customPasswordEncoder().matches(pass, usuarioSelected.getClaveAcceso())) {
                resultadoMinistrar.put("estatus", "ERROR");
                resultadoMinistrar.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
            } else {
                List<TransaccionPermisos> permisosList = consultaPermisoUsuario(usuarioSelected);
                if (permisosList != null && !permisosList.isEmpty()) {
                    usuarioSelected.setPermisosList(permisosList);
                    permisosUsuario = obtenPermisoUsuario(usuarioSelected, Transaccion_Enum.MINISTRMED.getSufijo());
                } else {
                    resultadoMinistrar.put("estatus", "ERROR");
                    resultadoMinistrar.put("mensaje", RESOURCES.getString("ses.obtener.datos"));
                }
                if (!permisosUsuario.isPuedeProcesar()) {
                    resultadoMinistrar.put("estatus", "ERROR");
                    resultadoMinistrar.put("mensaje", RESOURCES.getString("estr.err.permisos"));
                } else if (usuarioSelected.getIdEstructura() == null) {
                    resultadoMinistrar.put("estatus", "ERROR");
                    resultadoMinistrar.put("mensaje", RESOURCES.getString("sur.sin.almacen"));
                } else {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd HH:mm");
                        List<SurtimientoMinistrado> listaSurtMin = new ArrayList<>();
                        List<MovimientoInventario> listaMovInv = new ArrayList<>();
                        for (Medicamento_Extended item : listaMedicamentos) {
                            if (item.getEstatusMinistracion().equals(EstatusMinistracion_Enum.MINISTRADO.getValue())) {
                                if (item.getIdTipoSolucion() != null) {
                                    List<SurtimientoMinistrado_Extend> listaSurtMinSolucion = surtimientoMinistradoService.obtenerListSurtimientoMinistradoSolucion(item.getCodigoBarras());
                                    for (SurtimientoMinistrado_Extend sms : listaSurtMinSolucion) {
                                        sms.setFechaInicio(sdf.parse(fechaInicio));
                                        sms.setFechaMinistrado(sdf.parse(fechaTermino));
                                        sms.setComentarios(comentarios);
                                        sms.setIdUsuario(usuarioSelected.getIdUsuario());
                                        sms.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.RECIBIDO.getValue());
                                        sms.setIdEstatusMinistracion(EstatusMinistracion_Enum.MINISTRADO.getValue());
                                        sms.setUpdateFecha(new Date());
                                        sms.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
                                        sms.setCantidadMinistrada(sms.getCantidad());
                                        listaSurtMin.add(sms);                            

                                        MovimientoInventario movInventario = new MovimientoInventario();
                                        movInventario.setIdMovimientoInventario(Comunes.getUUID());
                                        movInventario.setIdTipoMotivo(TipoMotivo_Enum.SAL_MINIS_POR_SURTIMIENTO_DE_PRESCRIPCION.getMotivoValue());
                                        movInventario.setFecha(new Date());
                                        movInventario.setIdUsuarioMovimiento(usuarioSelected.getIdUsuario());
                                        movInventario.setIdEstrutcuraOrigen(usuarioSelected.getIdEstructura());
                                        movInventario.setIdEstrutcuraDestino(usuarioSelected.getIdEstructura());
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
                                    surtMinist.setIdUsuario(usuarioSelected.getIdUsuario());
                                    surtMinist.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.RECIBIDO.getValue());
                                    surtMinist.setIdEstatusMinistracion(EstatusMinistracion_Enum.MINISTRADO.getValue());
                                    surtMinist.setUpdateFecha(new Date());
                                    surtMinist.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
                                    surtMinist.setCantidadMinistrada(item.getCantidadMinistrada());
                                    listaSurtMin.add(surtMinist);

                                    MovimientoInventario movInventario = new MovimientoInventario();
                                    movInventario.setIdMovimientoInventario(Comunes.getUUID());
                                    movInventario.setIdTipoMotivo(TipoMotivo_Enum.SAL_MINIS_POR_SURTIMIENTO_DE_PRESCRIPCION.getMotivoValue());
                                    movInventario.setFecha(new Date());
                                    movInventario.setIdUsuarioMovimiento(usuarioSelected.getIdUsuario());
                                    movInventario.setIdEstrutcuraOrigen(usuarioSelected.getIdEstructura());
                                    movInventario.setIdEstrutcuraDestino(usuarioSelected.getIdEstructura());
                                    movInventario.setIdInventario(item.getIdInventario());
                                    movInventario.setCantidad(item.getCantidadMinistrada());
                                    movInventario.setFolioDocumento(item.getFolioPrescripcion());
                                    listaMovInv.add(movInventario);
                                }
                            }
                        }
                        boolean resp = surtimientoMinistradoService.actualizarSurtiminetoMinistrado(listaSurtMin, listaMovInv);
                        if (resp) {
                            resultadoMinistrar.put("estatus", "OK");
                        } else {
                            resultadoMinistrar.put("estatus", "ERROR");
                            resultadoMinistrar.put("mensaje", RESOURCES.getString("err.guardar"));
                        }
                    } catch (Exception ex) {
                        resultadoMinistrar.put("estatus", "EXCEPCION");
                        resultadoMinistrar.put("mensaje", ex.getMessage());
                        LOGGER.error("{}.ministrarMedicamentos() : {}", this.getClass().getCanonicalName(), ex.getMessage());
                    }
                }
            }
        } else {
            resultadoMinistrar.put("estatus", "ERROR");
            resultadoMinistrar.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
        }
        return Response.ok(resultadoMinistrar.toString()).build();
    }

    /**
     * Equivalente del método MinistrarMedicamentoMB.cargarModalMinistracion()
     * Obtiene la lista de Medicamentos para ministrar al Paciente seleccionado
     *
     * @param filtrosJson Cadena JSON con las credenciales del usuario y el ID
     * del surtimiento
     * @return Respuesta en formato JSON con la lista de Medicamentos
     * @throws java.io.IOException
     */
    @POST
    @Path("cargarModalMinistracionSync")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cargarModalMinistracionSync(String filtrosJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<MedicamentoOff_Extended> listaMedicamentos;
        ObjectNode medicamentosMinistrar = mapper.createObjectNode();
        JsonNode params = mapper.readTree(filtrosJson);
        if (!params.hasNonNull("usuario")) {
            medicamentosMinistrar.put("estatus", "ERROR");
            medicamentosMinistrar.put("mensaje", RESOURCES.getString("webservice.err.invalidRequest"));
            return Response.ok(medicamentosMinistrar.toString()).build();
        }
        String usuario = params.get("usuario").asText();
        Usuario usuarioParam = new Usuario();
        Usuario usuarioSelected = null;
        usuarioParam.setNombreUsuario(usuario);
        try {
            usuarioSelected = this.usuarioService.verificaSiExisteUser(usuarioParam);
        } catch (Exception ex) {
            medicamentosMinistrar.put("estatus", "EXCEPCION");
            medicamentosMinistrar.put("mensaje", ex.getMessage());
            LOGGER.error("{}.cargarModalMinistracionSync(): {}", this.getClass().getCanonicalName(), ex.getMessage());
        }
        if (usuarioSelected != null) {
            if (usuarioSelected.getIdEstructura() == null) {
                medicamentosMinistrar.put("estatus", "ERROR");
                medicamentosMinistrar.put("mensaje", RESOURCES.getString("sur.sin.almacen"));
            } else {
                try {
                    obtenerDatosSistema();
                    ministraPrevDays = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_MINISTRA_PREVDAYS);
                    ministraLaterHours = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_MINISTRA_LATERHOURS);
                    List<String> idsEstructura = estructuraService.obtenerIdsEstructuraJerarquica(usuarioSelected.getIdEstructura(), false);
                    if (idsEstructura == null) {
                        idsEstructura = new ArrayList<>();
                    }
                    idsEstructura.add(usuarioSelected.getIdEstructura());
                    List<Integer> listEstatusMinistracion = new ArrayList<>();
                    listEstatusMinistracion.add(EstatusMinistracion_Enum.PENDIENTE.getValue());
                    List<Integer> listaEstatusPresc = new ArrayList<>();
                    listaEstatusPresc.add(EstatusPrescripcion_Enum.PROGRAMADA.getValue());
                    listaEstatusPresc.add(EstatusPrescripcion_Enum.FINALIZADA.getValue());
                    listaMedicamentos = medicamentoService.obtenerMedicamentosPorPrescripcionSync(idsEstructura, listaEstatusPresc, listEstatusMinistracion, ministraPrevDays, ministraLaterHours);
                    medicamentosMinistrar.put("estatus", "OK");
                    ArrayNode registrosNode = mapper.valueToTree(listaMedicamentos);
                    medicamentosMinistrar.set("registros", registrosNode);
                } catch (Exception ex) {
                    medicamentosMinistrar.put("estatus", "EXCEPCION");
                    medicamentosMinistrar.put("mensaje", ex.getMessage());
                    LOGGER.error("{}.cargarModalMinistracionSync() : {}", this.getClass().getCanonicalName(), ex.getMessage());
                }
            }
        } else {
            medicamentosMinistrar.put("estatus", "ERROR");
            medicamentosMinistrar.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
        }
        return Response.ok(medicamentosMinistrar.toString()).build();
    }

    /**
     * Equivalente del método MinistrarMedicamentoMB.cargarListaPacientes()
     * Obtiene la lista de Pacientes con Surtimientos para ministrar
     *
     * @param filtrosJson Cadena JSON con las credenciales del usuario y la
     * cadena de búsqueda
     * @return Respuesta en formato JSON con la lista de Pacientes
     * @throws java.io.IOException
     */
    @POST
    @Path("cargarListaPacientesSync")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cargarListaPacientesSync(String filtrosJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode pacientesSync = mapper.createObjectNode();
        JsonNode params = mapper.readTree(filtrosJson);
        if (!params.hasNonNull("usuario")) {
            pacientesSync.put("estatus", "ERROR");
            pacientesSync.put("mensaje", RESOURCES.getString("webservice.err.invalidRequest"));
            return Response.ok(pacientesSync.toString()).build();
        }
        String usuario = params.get("usuario").asText();
        Usuario usuarioParam = new Usuario();
        Usuario usuarioSelected = null;
        usuarioParam.setNombreUsuario(usuario);
        try {
            usuarioSelected = this.usuarioService.verificaSiExisteUser(usuarioParam);
        } catch (Exception ex) {
            pacientesSync.put("estatus", "EXCEPCION");
            pacientesSync.put("mensaje", ex.getMessage());
            LOGGER.error("{}.cargarListaPacientesSync(): {}", this.getClass().getCanonicalName(), ex.getMessage());
        }
        if (usuarioSelected != null) {
            if (usuarioSelected.getIdEstructura() == null) {
                pacientesSync.put("estatus", "ERROR");
                pacientesSync.put("mensaje", RESOURCES.getString("sur.sin.almacen"));
            } else {
                try {
                    List<Integer> listaEstatusPresc = new ArrayList<>();
                    listaEstatusPresc.add(EstatusPrescripcion_Enum.PROGRAMADA.getValue());
                    listaEstatusPresc.add(EstatusPrescripcion_Enum.FINALIZADA.getValue());
                    List<String> idsEstructura = estructuraService.obtenerIdsEstructuraJerarquica(usuarioSelected.getIdEstructura(), false);
                    if (idsEstructura == null) {
                        idsEstructura = new ArrayList<>();
                    }
                    idsEstructura.add(usuarioSelected.getIdEstructura());
                    pacientesList = pacienteService.obtenerPacietesConPrescripcion(-1, idsEstructura, null, listaEstatusPresc);
                    pacientesSync.put("estatus", "OK");
                    pacientesSync.set("registros", mapper.valueToTree(pacientesList));
                } catch (Exception ex) {
                    pacientesSync.put("estatus", "EXCEPCION");
                    pacientesSync.put("mensaje", ex.getMessage());
                    LOGGER.error("{}.cargarListaPacientesSync() : {}", this.getClass().getCanonicalName(), ex.getMessage());
                }
            }
        } else {
            pacientesSync.put("estatus", "ERROR");
            pacientesSync.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
        }
        return Response.ok(pacientesSync.toString()).build();
    }

    /**
     * Realiza la acción de No-Ministrar los medicamentos seleccionados
     *
     * @param filtrosJson Cadena JSON con las credenciales del usuario y la
     * lista de medicamentos por no-ministrar
     * @return Respuesta en formato JSON
     * @throws java.io.IOException
     */
    @POST
    @Path("noMinistrarMedicamentos")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response noMinistrarMedicamentos(String filtrosJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode noMinistracion = mapper.createObjectNode();
        JsonNode params = mapper.readTree(filtrosJson);
        if (!params.hasNonNull("usuario") || !params.hasNonNull("pass") || !params.hasNonNull("listaMedicamentos")) {
            noMinistracion.put("estatus", "ERROR");
            noMinistracion.put("mensaje", RESOURCES.getString("webservice.err.invalidRequest"));
            return Response.ok(noMinistracion.toString()).build();
        }
        String usuario = params.get("usuario").asText();
        String pass = params.get("pass").asText();
        JsonNode nodoJson = params.get("listaMedicamentos");
        List<Medicamento_Extended> listaMedicamentos = mapper.readValue(nodoJson.toString(), new TypeReference<List<Medicamento_Extended>>() {});

        Usuario usuarioParam = new Usuario();
        Usuario usuarioSelected = null;
        usuarioParam.setNombreUsuario(usuario);
        PermisoUsuario permisosUsuario = new PermisoUsuario();
        try {
            usuarioSelected = this.usuarioService.verificaSiExisteUser(usuarioParam);
        } catch (Exception ex) {
            noMinistracion.put("estatus", "EXCEPCION");
            noMinistracion.put("mensaje", ex.getMessage());
            LOGGER.error("{}.noMinistrarMedicamentos(): {}", this.getClass().getCanonicalName(), ex.getMessage());
        }
        if (usuarioSelected != null) {
            if (!CustomWebSecurityConfigurerAdapter.customPasswordEncoder().matches(pass, usuarioSelected.getClaveAcceso())) {
                noMinistracion.put("estatus", "ERROR");
                noMinistracion.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
            } else {
                List<TransaccionPermisos> permisosList = consultaPermisoUsuario(usuarioSelected);
                if (permisosList != null && !permisosList.isEmpty()) {
                    usuarioSelected.setPermisosList(permisosList);
                    permisosUsuario = obtenPermisoUsuario(usuarioSelected, Transaccion_Enum.MINISTRMED.getSufijo());
                } else {
                    noMinistracion.put("estatus", "ERROR");
                    noMinistracion.put("mensaje", RESOURCES.getString("ses.obtener.datos"));
                }
                if (!permisosUsuario.isPuedeProcesar()) {
                    noMinistracion.put("estatus", "ERROR");
                    noMinistracion.put("mensaje", RESOURCES.getString("estr.err.permisos"));
                } else if (usuarioSelected.getIdEstructura() == null) {
                    noMinistracion.put("estatus", "ERROR");
                    noMinistracion.put("mensaje", RESOURCES.getString("sur.sin.almacen"));
                } else {
                    try {
                        List<SurtimientoMinistrado> listaSurtMin = new ArrayList<>();
                        for (Medicamento_Extended item : listaMedicamentos) {
                            if (item.getEstatusMinistracion().equals(EstatusMinistracion_Enum.MINISTRADO.getValue())) {
                                SurtimientoMinistrado surtMinist = new SurtimientoMinistrado();
                                surtMinist.setIdMinistrado(item.getIdMinistrado());
                                surtMinist.setFechaMinistrado(new Date());
                                surtMinist.setIdUsuario(usuarioSelected.getIdUsuario());
                                surtMinist.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.RECIBIDO.getValue());
                                surtMinist.setIdEstatusMinistracion(EstatusMinistracion_Enum.NO_MINISTRADO.getValue());
                                surtMinist.setUpdateFecha(new Date());
                                surtMinist.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
                                surtMinist.setCantidadMinistrada(item.getCantidadMinistrada());
                                surtMinist.setIdMotivoNoMinistrado(item.getIdMotivoNoMinistrado());
                                listaSurtMin.add(surtMinist);
                            }
                        }
                        boolean resp = surtimientoMinistradoService.actualizarSurtiminetoMinistrado(listaSurtMin, null);
                        if (resp) {
                            noMinistracion.put("estatus", "OK");
                        } else {
                            noMinistracion.put("estatus", "ERROR");
                            noMinistracion.put("mensaje", RESOURCES.getString("err.guardar"));
                        }
                    } catch (Exception ex) {
                        noMinistracion.put("estatus", "EXCEPCION");
                        noMinistracion.put("mensaje", ex.getMessage());
                        LOGGER.error("{}.noMinistrarMedicamentos() : {}", this.getClass().getCanonicalName(), ex.getMessage());
                    }
                }
            }
        } else {
            noMinistracion.put("estatus", "ERROR");
            noMinistracion.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
        }
        return Response.ok(noMinistracion.toString()).build();
    }
    
    @POST
    @Path("obtenerSurtimientoPorFolio")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerSurtimientoPorFolio(String filtrosJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode surtNode = mapper.createObjectNode();
        JsonNode params = mapper.readTree(filtrosJson);
        if (!params.hasNonNull("usuario") || !params.hasNonNull("pass") || !params.hasNonNull("folioPrescripcion")) {
            surtNode.put("estatus", "ERROR");
            surtNode.put("mensaje", RESOURCES.getString("webservice.err.invalidRequest"));
            return Response.ok(surtNode.toString()).build();
        }
        String usuario = params.get("usuario").asText();
        String pass = params.get("pass").asText();
        String folioPrescripcion = params.get("folioPrescripcion").asText();

        Usuario usuarioParam = new Usuario();
        Usuario usuarioSelected = null;
        usuarioParam.setNombreUsuario(usuario);
        PermisoUsuario permisosUsuario = new PermisoUsuario();
        try {
            usuarioSelected = this.usuarioService.verificaSiExisteUser(usuarioParam);
        } catch (Exception ex) {
            surtNode.put("estatus", "EXCEPCION");
            surtNode.put("mensaje", ex.getMessage());
            LOGGER.error("{}.obtenerSurtimientoPorFolio(): {}", this.getClass().getCanonicalName(), ex.getMessage());
        }
        if (usuarioSelected != null) {
            if (!CustomWebSecurityConfigurerAdapter.customPasswordEncoder().matches(pass, usuarioSelected.getClaveAcceso())) {
                surtNode.put("estatus", "ERROR");
                surtNode.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
            } else {
                List<TransaccionPermisos> permisosList = consultaPermisoUsuario(usuarioSelected);
                if (permisosList != null && !permisosList.isEmpty()) {
                    usuarioSelected.setPermisosList(permisosList);
                    permisosUsuario = obtenPermisoUsuario(usuarioSelected, Transaccion_Enum.MINISTRMED.getSufijo());
                } else {
                    surtNode.put("estatus", "ERROR");
                    surtNode.put("mensaje", RESOURCES.getString("ses.obtener.datos"));
                }
                if (!permisosUsuario.isPuedeVer()) {
                    surtNode.put("estatus", "ERROR");
                    surtNode.put("mensaje", RESOURCES.getString("err.transaccion"));
                } else if (usuarioSelected.getIdEstructura() == null) {
                    surtNode.put("estatus", "ERROR");
                    surtNode.put("mensaje", RESOURCES.getString("sur.sin.almacen"));
                } else {
                    if (folioPrescripcion == null || folioPrescripcion.isEmpty()) {
                        surtNode.put("estatus", "ERROR");
                        surtNode.put("mensaje", RESOURCES.getString("webservice.ministracion.err.sinFolioPresc"));
                    } else {
                        try {
                            Surtimiento_Extend surtimiento = surtimientoService.obtenerDetallePrescripcionSolucion(folioPrescripcion);
                            List<Medicamento_Extended> detalleSolucion = medicamentoService.obtenerDetallePrescripcionSolucion(folioPrescripcion);
                            surtNode.put("estatus", "OK");
                            ObjectNode objectNode = mapper.valueToTree(surtimiento);
                            surtNode.set("surtimiento", objectNode);
                            ArrayNode registrosNode = mapper.valueToTree(detalleSolucion);
                            surtNode.set("detalleSolucion", registrosNode);
                        }
                        catch(Exception ex){
                            surtNode.put("estatus", "EXCEPCION");
                            surtNode.put("mensaje", ex.getMessage());
                            LOGGER.error("{}.obtenerSurtimientoPorFolio() : {}", this.getClass().getCanonicalName(), ex.getMessage());
                        }
                    }
                }
            }
        } else {
            surtNode.put("estatus", "ERROR");
            surtNode.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
        }
        return Response.ok(surtNode.toString()).build();
    }
}
