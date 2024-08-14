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
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.init.CustomWebSecurityConfigurerAdapter;
import mx.mc.model.CodigoInsumo;
import mx.mc.model.Config;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.SurtimientoInsumo_Extend;
import mx.mc.model.TransaccionPermisos;
import mx.mc.model.Usuario;
import mx.mc.model.VistaRecepcionMedicamento;
import mx.mc.service.ConfigService;
import mx.mc.service.EstructuraService;
import mx.mc.service.SurtimientoService;
import mx.mc.service.TransaccionService;
import mx.mc.service.UsuarioService;
import mx.mc.service.VistaRecepcionMedicamentoService;
import mx.mc.util.CodigoBarras;
import mx.mc.util.Comunes;
import mx.mc.util.FechaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Implementa las interfaces REST para la Recepcion de Medicamentos
 *
 * @author Alberto Palacios
 * @version 1.0
 * @since 2019-02-13
 */
@Path("recepcion")
public class RecepcionMedicamentoMBMovil extends SpringBeanAutowiringSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecepcionMedicamentoMBMovil.class);
    protected static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private String claveMedicamento;
    private String loteMedicamento;
    private Date caducidadMedicamento;
    private int resul = 0;
    private int enviado = 0;
    private boolean eliminarCodigo;
    private int cantRecibir;
    private boolean clave = Constantes.INACTIVO;
    private boolean lote = Constantes.INACTIVO;
    private Date fechaActual;
    private List<Config> configList;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TransaccionService transaccionService;

    @Autowired
    private VistaRecepcionMedicamentoService viewRecepcionService;

    @Autowired
    private EstructuraService estructuraService;

    @Autowired
    private SurtimientoService surtimientoService;

    @Autowired
    private ConfigService configService;

    @PostConstruct
    public void init() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    /**
     * Crea una nueva instancia de la clase
     */
    public RecepcionMedicamentoMBMovil() {
        //No code needed in constructor
    }

    /**
     * Equivalente del método RecepcionMedicamentoMB.consultaPermisoUsuario()
     * El usuario no se toma de la sesion, en su lugar se pasa como parámetro
     *
     * @param usuario POJO del Usuario
     * @return Lista de TransaccionPermisos
     */
    public List<TransaccionPermisos> consultaPermisos(Usuario usuario) {
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
     * Relacionado con el método
     * RecepcionMedicamentoMB.consultaPermisoUsuario() Obtiene una
     * representacion de los permisos del usuario para una Transaccion en
     * particular
     *
     * @param usuario POJO del Uuario
     * @param sufijoTransaccion Código de la Transacción
     * @return Objeto con la representacion de los permisos del Usuario
     */
    public PermisoUsuario obtenPermisoUsuario(Usuario usuario, String sufijoTransaccion) {
        LOGGER.debug("{}.obtenPermisoUsuario()", this.getClass().getCanonicalName());
        PermisoUsuario permisosUsuario = new PermisoUsuario();
        if (usuario.getPermisosList() != null) {
            for (TransaccionPermisos item : usuario.getPermisosList()) {
                if (item.getCodigo().equals(sufijoTransaccion)) {
                    permisosUsuario = mx.mc.ws.movil.util.Comunes.obtenPermiso(item.getAccion(), permisosUsuario);
                }
            }
        }
        return permisosUsuario;
    }

    /**
     * Equivalente del método
     * RecepcionMedicamentoMB.filtrarListaPrescripciones() Obtiene la lista de
     * Surtimientos que pueden recibirse
     *
     * @param filtrosJson Cadena JSON con las credenciales del usuario y la
     * cadena de búsqueda
     * @return Respuesta en formato JSON con la lista de Surtimientos
     * @throws java.io.IOException
     */
    @POST
    @Path("filtrarListaPrescripcionesSurtidas")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response filtrarListaPrescripcionesSurtidas(String filtrosJson) throws IOException {
        ObjectMapper mapeo = new ObjectMapper();
        ObjectNode filtroLista = mapeo.createObjectNode();
        JsonNode params = mapeo.readTree(filtrosJson);
        if (!params.hasNonNull("usuario") || !params.hasNonNull("pass") || !params.hasNonNull("cadenaBusqueda")) {
            filtroLista.put("estatus", "ERROR");
            filtroLista.put("mensaje", RESOURCES.getString("webservice.err.invalidRequest"));
            return Response.ok(filtroLista.toString()).build();
        }
        String usuario = params.get("usuario").asText();
        String pass = params.get("pass").asText();
        String cadenaBusqueda = params.get("cadenaBusqueda").asText().toUpperCase();

        Usuario usuarioParam = new Usuario();
        Usuario usuarioSelected = null;
        usuarioParam.setNombreUsuario(usuario);
        PermisoUsuario permUsuario = new PermisoUsuario();
        try {
            usuarioSelected = this.usuarioService.verificaSiExisteUser(usuarioParam);
        } catch (Exception ex) {
            filtroLista.put("estatus", "EXCEPCION");
            filtroLista.put("mensaje", ex.getMessage());
            LOGGER.error("{}.filtrarListaPrescripciones(): {}",this.getClass().getCanonicalName() , ex.getMessage());
        }
        if (usuarioSelected != null) {
            if (!CustomWebSecurityConfigurerAdapter.customPasswordEncoder().matches(pass, usuarioSelected.getClaveAcceso())) {
                filtroLista.put("estatus", "ERROR");
                filtroLista.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
            } else {
                List<TransaccionPermisos> permisosList = consultaPermisos(usuarioSelected);
                if (permisosList != null && !permisosList.isEmpty()) {
                    usuarioSelected.setPermisosList(permisosList);
                    permUsuario = obtenPermisoUsuario(usuarioSelected, Transaccion_Enum.RECEPMEDICAMENTO.getSufijo());
                } else {
                    filtroLista.put("estatus", "ERROR");
                    filtroLista.put("mensaje", RESOURCES.getString("ses.obtener.datos"));
                }
                if (!permUsuario.isPuedeVer()) {
                    filtroLista.put("estatus", "ERROR");
                    filtroLista.put("mensaje", RESOURCES.getString("err.transaccion"));
                } else if (usuarioSelected.getIdEstructura() == null) {
                    filtroLista.put("estatus", "ERROR");
                    filtroLista.put("mensaje", RESOURCES.getString("sur.sin.almacen"));
                } else {
                    try {
                        List<String> idsEstructura = estructuraService.obtenerIdsEstructuraJerarquica(usuarioSelected.getIdEstructura(), false);
                        if (idsEstructura == null) {
                            idsEstructura = new ArrayList<>();
                        }
                        idsEstructura.add(usuarioSelected.getIdEstructura());
                        List<VistaRecepcionMedicamento> viewRecepcionList = viewRecepcionService.obtenerSurtimientosPorRecibir(idsEstructura);
                        List<VistaRecepcionMedicamento> listaPresc = new ArrayList<>();
                        for (VistaRecepcionMedicamento vist : viewRecepcionList) {
                            String nomPaciente = "";
                            String folioP = "";
                            String folioS = "";
                            String numPaciente = "";
                            String numCama = "";

                            if (vist.getNombrePaciente() != null) {
                                nomPaciente = vist.getNombrePaciente().toUpperCase();
                            }
                            if (vist.getFolioPrescripcion() != null) {
                                folioP = vist.getFolioPrescripcion().toUpperCase();
                            }
                            if (vist.getFolioSurtimiento() != null) {
                                folioS = vist.getFolioSurtimiento().toUpperCase();
                            }
                            if (vist.getPacienteNumero() != null) {
                                numPaciente = vist.getPacienteNumero().toUpperCase();
                            }
                            if (vist.getNombreCama() != null) {
                                numCama = vist.getNombreCama().toUpperCase();
                            }
                            if (nomPaciente.contains(cadenaBusqueda) || folioP.contains(cadenaBusqueda) || folioS.contains(cadenaBusqueda)
                                    || numPaciente.contains(cadenaBusqueda) || numCama.contains(cadenaBusqueda)) {
                                listaPresc.add(vist);
                            }
                        }
                        filtroLista.put("estatus", "OK");
                        filtroLista.set("registros", mapeo.valueToTree(listaPresc));
                    } catch (Exception ex) {
                        filtroLista.put("estatus", "EXCEPCION");
                        filtroLista.put("mensaje", ex.getMessage());
                        LOGGER.error("{}.filtrarListaPrescripciones() : {}",this.getClass().getCanonicalName() , ex.getMessage());
                    }
                }
            }
        } else {
            filtroLista.put("estatus", "ERROR");
            filtroLista.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
        }
        return Response.ok(filtroLista.toString()).build();
    }

    /**
     * Obtiene la lista de Surtimientos que se han recibido
     *
     * @param filtrosJson Cadena JSON con las credenciales del usuario y la
     * cadena de búsqueda
     * @return Respuesta en formato JSON con la lista de Surtimientos
     * @throws java.io.IOException
     */
    @POST
    @Path("filtrarListaPrescripcionesRecibidas")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response filtrarListaPrescripcionesRecibidas(String filtrosJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode filtroListaRec = mapper.createObjectNode();
        JsonNode params = mapper.readTree(filtrosJson);
        if (!params.hasNonNull("usuario") || !params.hasNonNull("pass") || !params.hasNonNull("cadenaBusqueda")) {
            filtroListaRec.put("estatus", "ERROR");
            filtroListaRec.put("mensaje", RESOURCES.getString("webservice.err.invalidRequest"));
            return Response.ok(filtroListaRec.toString()).build();
        }
        String usuario = params.get("usuario").asText();
        String password = params.get("pass").asText();
        String cadenaBusqueda = params.get("cadenaBusqueda").asText().toUpperCase();

        Usuario usuarioParam = new Usuario();
        Usuario usuarioSelected = null;
        usuarioParam.setNombreUsuario(usuario);
        PermisoUsuario permisosUsuario = new PermisoUsuario();
        try {
            usuarioSelected = this.usuarioService.verificaSiExisteUser(usuarioParam);
        } catch (Exception ex) {
            filtroListaRec.put("estatus", "EXCEPCION");
            filtroListaRec.put("mensaje", ex.getMessage());
            LOGGER.error("{}.filtrarListaPrescripcionesRecibidas(): {}",this.getClass().getCanonicalName() , ex.getMessage());
        }
        if (usuarioSelected != null) {
            if (!CustomWebSecurityConfigurerAdapter.customPasswordEncoder().matches(password, usuarioSelected.getClaveAcceso())) {
                filtroListaRec.put("estatus", "ERROR");
                filtroListaRec.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
            } else {
                List<TransaccionPermisos> permisosList = consultaPermisos(usuarioSelected);
                if (permisosList != null && !permisosList.isEmpty()) {
                    usuarioSelected.setPermisosList(permisosList);
                    permisosUsuario = obtenPermisoUsuario(usuarioSelected, Transaccion_Enum.RECEPMEDICAMENTO.getSufijo());
                } else {
                    filtroListaRec.put("estatus", "ERROR");
                    filtroListaRec.put("mensaje", RESOURCES.getString("ses.obtener.datos"));
                }
                if (!permisosUsuario.isPuedeVer()) {
                    filtroListaRec.put("estatus", "ERROR");
                    filtroListaRec.put("mensaje", RESOURCES.getString("err.transaccion"));
                } else if (usuarioSelected.getIdEstructura() == null) {
                    filtroListaRec.put("estatus", "ERROR");
                    filtroListaRec.put("mensaje", RESOURCES.getString("sur.sin.almacen"));
                } else {
                    try {
                        List<String> idsEstructura = estructuraService.obtenerIdsEstructuraJerarquica(usuarioSelected.getIdEstructura(), false);
                        if (idsEstructura == null) {
                            idsEstructura = new ArrayList<>();
                        }
                        idsEstructura.add(usuarioSelected.getIdEstructura());
                        List<VistaRecepcionMedicamento> viewRecepcionList = viewRecepcionService.obtenerSurtimientosRecibidos(idsEstructura);
                        List<VistaRecepcionMedicamento> listaPresc = new ArrayList<>();
                        for (VistaRecepcionMedicamento vist : viewRecepcionList) {
                            String nombrePac = "";
                            String folioP = "";
                            String folioS = "";
                            String numPaciente = "";
                            String numeroCama = "";

                            if (vist.getNombrePaciente() != null) {
                                nombrePac = vist.getNombrePaciente().toUpperCase();
                            }
                            if (vist.getFolioPrescripcion() != null) {
                                folioP = vist.getFolioPrescripcion().toUpperCase();
                            }
                            if (vist.getFolioSurtimiento() != null) {
                                folioS = vist.getFolioSurtimiento().toUpperCase();
                            }
                            if (vist.getPacienteNumero() != null) {
                                numPaciente = vist.getPacienteNumero().toUpperCase();
                            }
                            if (vist.getNombreCama() != null) {
                                numeroCama = vist.getNombreCama().toUpperCase();
                            }
                            if (nombrePac.contains(cadenaBusqueda) || folioP.contains(cadenaBusqueda) || folioS.contains(cadenaBusqueda)
                                    || numPaciente.contains(cadenaBusqueda) || numeroCama.contains(cadenaBusqueda)) {
                                listaPresc.add(vist);
                            }
                        }
                        filtroListaRec.put("estatus", "OK");
                        filtroListaRec.set("registros", mapper.valueToTree(listaPresc));
                    } catch (Exception ex) {
                        filtroListaRec.put("estatus", "EXCEPCION");
                        filtroListaRec.put("mensaje", ex.getMessage());
                        LOGGER.error("{}.filtrarListaPrescripcionesRecibidas() : {}",this.getClass().getCanonicalName() , ex.getMessage());
                    }
                }
            }
        } else {
            filtroListaRec.put("estatus", "ERROR");
            filtroListaRec.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
        }
        return Response.ok(filtroListaRec.toString()).build();
    }

    /**
     * Obtiene la lista de Surtimientos que se han cancelado
     *
     * @param filtrosJson Cadena JSON con las credenciales del usuario y la
     * cadena de búsqueda
     * @return Respuesta en formato JSON con la lista de Surtimientos
     * @throws java.io.IOException
     */
    @POST
    @Path("filtrarListaPrescripcionesCanceladas")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response filtrarListaPrescripcionesCanceladas(String filtrosJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode filtroListaCan = mapper.createObjectNode();
        JsonNode params = mapper.readTree(filtrosJson);
        if (!params.hasNonNull("usuario") || !params.hasNonNull("pass") || !params.hasNonNull("cadenaBusqueda")) {
            filtroListaCan.put("estatus", "ERROR");
            filtroListaCan.put("mensaje", RESOURCES.getString("webservice.err.invalidRequest"));
            return Response.ok(filtroListaCan.toString()).build();
        }
        String usuario = params.get("usuario").asText();
        String pass = params.get("pass").asText();
        String cadenaBusqueda = params.get("cadenaBusqueda").asText().toUpperCase();

        Usuario usuarioParametro = new Usuario();
        Usuario usuarioSelected = null;
        usuarioParametro.setNombreUsuario(usuario);
        PermisoUsuario permisosUsuario = new PermisoUsuario();
        try {
            usuarioSelected = this.usuarioService.verificaSiExisteUser(usuarioParametro);
        } catch (Exception ex) {
            filtroListaCan.put("estatus", "EXCEPCION");
            filtroListaCan.put("mensaje", ex.getMessage());
            LOGGER.error("{}.filtrarListaPrescripcionesCanceladas(): {}",this.getClass().getCanonicalName() , ex.getMessage());
        }
        if (usuarioSelected != null) {
            if (!CustomWebSecurityConfigurerAdapter.customPasswordEncoder().matches(pass, usuarioSelected.getClaveAcceso())) {
                filtroListaCan.put("estatus", "ERROR");
                filtroListaCan.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
            } else {
                List<TransaccionPermisos> permisosList = consultaPermisos(usuarioSelected);
                if (permisosList != null && !permisosList.isEmpty()) {
                    usuarioSelected.setPermisosList(permisosList);
                    permisosUsuario = obtenPermisoUsuario(usuarioSelected, Transaccion_Enum.RECEPMEDICAMENTO.getSufijo());
                } else {
                    filtroListaCan.put("estatus", "ERROR");
                    filtroListaCan.put("mensaje", RESOURCES.getString("ses.obtener.datos"));
                }
                if (!permisosUsuario.isPuedeVer()) {
                    filtroListaCan.put("estatus", "ERROR");
                    filtroListaCan.put("mensaje", RESOURCES.getString("err.transaccion"));
                } else if (usuarioSelected.getIdEstructura() == null) {
                    filtroListaCan.put("estatus", "ERROR");
                    filtroListaCan.put("mensaje", RESOURCES.getString("sur.sin.almacen"));
                } else {
                    try {
                        List<String> idsEstructura = estructuraService.obtenerIdsEstructuraJerarquica(usuarioSelected.getIdEstructura(), false);
                        if (idsEstructura == null) {
                            idsEstructura = new ArrayList<>();
                        }
                        idsEstructura.add(usuarioSelected.getIdEstructura());
                        List<VistaRecepcionMedicamento> viewRecepcionList = viewRecepcionService.obtenerSurtimientosCancelados(idsEstructura);
                        List<VistaRecepcionMedicamento> listaPresc = new ArrayList<>();
                        for (VistaRecepcionMedicamento vist : viewRecepcionList) {
                            String nombre = "";
                            String folioPresc = "";
                            String folioSurt = "";
                            String numPaciente = "";
                            String cama = "";

                            if (vist.getNombrePaciente() != null) {
                                nombre = vist.getNombrePaciente().toUpperCase();
                            }
                            if (vist.getFolioPrescripcion() != null) {
                                folioPresc = vist.getFolioPrescripcion().toUpperCase();
                            }
                            if (vist.getFolioSurtimiento() != null) {
                                folioSurt = vist.getFolioSurtimiento().toUpperCase();
                            }
                            if (vist.getPacienteNumero() != null) {
                                numPaciente = vist.getPacienteNumero().toUpperCase();
                            }
                            if (vist.getNombreCama() != null) {
                                cama = vist.getNombreCama().toUpperCase();
                            }
                            if (nombre.contains(cadenaBusqueda) || folioPresc.contains(cadenaBusqueda) || folioSurt.contains(cadenaBusqueda)
                                    || numPaciente.contains(cadenaBusqueda) || cama.contains(cadenaBusqueda)) {
                                listaPresc.add(vist);
                            }
                        }
                        filtroListaCan.put("estatus", "OK");
                        filtroListaCan.set("registros", mapper.valueToTree(listaPresc));
                    } catch (Exception ex) {
                        filtroListaCan.put("estatus", "EXCEPCION");
                        filtroListaCan.put("mensaje", ex.getMessage());
                        LOGGER.error("{}.filtrarListaPrescripcionesCanceladas() : {}",this.getClass().getCanonicalName() , ex.getMessage());
                    }
                }
            }
        } else {
            filtroListaCan.put("estatus", "ERROR");
            filtroListaCan.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
        }
        return Response.ok(filtroListaCan.toString()).build();
    }

    /**
     * Equivalente del método RecepcionMedicamentoMB.obtenerDetalleSurtimiento()
     * Obtiene la lista de Surtimientos de acuerdo al Id de Surtimiento
     *
     * @param filtrosJson Cadena JSON con las credenciales del usuario y el ID
     * del surtimiento
     * @return Respuesta en formato JSON con la lista de Surtimientos
     * @throws java.io.IOException
     */
    @POST
    @Path("obtenerDetalleSurtimiento")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerDetalleSurtimiento(String filtrosJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode detalleSurt = mapper.createObjectNode();
        JsonNode params = mapper.readTree(filtrosJson);
        if (!params.hasNonNull("usuario") || !params.hasNonNull("pass")
                || !params.hasNonNull("idSurtimiento")) {
            detalleSurt.put("estatus", "ERROR");
            detalleSurt.put("mensaje", RESOURCES.getString("webservice.err.invalidRequest"));
            return Response.ok(detalleSurt.toString()).build();
        }
        String usuario = params.get("usuario").asText();
        String pass = params.get("pass").asText();
        String idSurtimiento = params.get("idSurtimiento").asText();

        Usuario usuarioParam = new Usuario();
        Usuario usuarioSelected = null;
        usuarioParam.setNombreUsuario(usuario);
        PermisoUsuario permisosUsuario = new PermisoUsuario();
        try {
            usuarioSelected = this.usuarioService.verificaSiExisteUser(usuarioParam);
        } catch (Exception ex) {
            detalleSurt.put("estatus", "EXCEPCION");
            detalleSurt.put("mensaje", ex.getMessage());
            LOGGER.error("{}.obtenerDetalleSurtimiento(): {}",this.getClass().getCanonicalName() , ex.getMessage());
        }
        if (usuarioSelected != null) {
            if (!CustomWebSecurityConfigurerAdapter.customPasswordEncoder().matches(pass, usuarioSelected.getClaveAcceso())) {
                detalleSurt.put("estatus", "ERROR");
                detalleSurt.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
            } else {
                List<TransaccionPermisos> permisosList = consultaPermisos(usuarioSelected);
                if (permisosList != null && !permisosList.isEmpty()) {
                    usuarioSelected.setPermisosList(permisosList);
                    permisosUsuario = obtenPermisoUsuario(usuarioSelected, Transaccion_Enum.RECEPMEDICAMENTO.getSufijo());
                } else {
                    detalleSurt.put("estatus", "ERROR");
                    detalleSurt.put("mensaje", RESOURCES.getString("ses.obtener.datos"));
                }
                if (permisosUsuario.isPuedeEditar()) {
                    try {
                        List<SurtimientoInsumo_Extend> surtInsumoExtList = surtimientoService.detalleSurtimientoInsumoExtRecepMedi(idSurtimiento);
                        if (surtInsumoExtList != null) {
                            detalleSurt.put("estatus", "OK");
                            detalleSurt.set("registros", mapper.valueToTree(surtInsumoExtList));
                        } else {
                            detalleSurt.put("estatus", "ERROR");
                            detalleSurt.put("mensaje", RESOURCES.getString("sur.incorrecto"));
                        }
                    } catch (Exception ex) {
                        detalleSurt.put("estatus", "ERROR");
                        detalleSurt.put("mensaje", "Error al obtener los Medicamentos: " + ex.getMessage());
                        LOGGER.error("{}.obtenerDetalleSurtimiento(): {} {}", this.getClass().getCanonicalName(), "Error al obtener los Medicamentos: ", ex.getMessage());
                    }
                } else {
                    detalleSurt.put("estatus", "ERROR");
                    detalleSurt.put("mensaje", RESOURCES.getString("recepmedicamento.err.permEditar"));
                }
            }
        } else {
            detalleSurt.put("estatus", "ERROR");
            detalleSurt.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
        }
        return Response.ok(detalleSurt.toString()).build();
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
     * Equivalente del método
     * RecepcionMedicamentoMB.tratarCodigoDeBarrasMedicamento() Desglosa el
     * código de barras en sus componentes: Clave, Lote, Fecha de Caducidad
     *
     * @param codigo Cadena con el código de barras
     * @return True en caso de éxito, false en caso de error
     */
    private boolean tratarCodigoDeBarrasMedicamento(String codigo) {
        boolean resp = Constantes.INACTIVO;
        obtenerDatosSistema();
        try {
            Boolean isCodificacionGS1 = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_CODIGOS_GS1) == 1;
            CodigoInsumo ci = CodigoBarras.parsearCodigoDeBarras(codigo, isCodificacionGS1);
            if (ci != null) {
                claveMedicamento = ci.getClave();
                loteMedicamento = ci.getLote();
                caducidadMedicamento = ci.getFecha();
                resp = Constantes.ACTIVO;
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo tratarCodigoDeBarras :: {}", e.getMessage());
        }
        return resp;
    }

    /**
     * Equivalente del método RecepcionMedicamentoMB.receiveMedicamento() Valida
     * los datos y cantidades del medicamento a recibir
     *
     * @param filtrosJson Cadena JSON con las credenciales del usuario, el
     * código de barras y la lista de Surtimientos
     * @return Respuesta en formato JSON con la lista de Surtimientos
     * @throws java.io.IOException
     */
    @POST
    @Path("receiveMedicamento")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response receiveMedicamento(String filtrosJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode recibirMed = mapper.createObjectNode();
        JsonNode params = mapper.readTree(filtrosJson);
        if (!params.hasNonNull("usuario") || !params.hasNonNull("pass") || !params.hasNonNull("cantidad") || !params.hasNonNull("surtimientoInsumoExtendedList")
                || !params.hasNonNull("eliminaCodigoBarras") || !params.hasNonNull("codigoBarras")) {
            recibirMed.put("estatus", "ERROR");
            recibirMed.put("mensaje", RESOURCES.getString("webservice.err.invalidRequest"));
            return Response.ok(recibirMed.toString()).build();
        }
        String usuario = params.get("usuario").asText();
        String pass = params.get("pass").asText();
        cantRecibir = params.get("cantidad").asInt();
        eliminarCodigo = params.get("eliminaCodigoBarras").asBoolean();
        String codigoBarras = params.get("codigoBarras").asText();
        JsonNode jn = params.get("surtimientoInsumoExtendedList");
        List<SurtimientoInsumo_Extend> surtInsumoExtList = mapper.readValue(jn.toString(), new TypeReference<List<SurtimientoInsumo_Extend>>() {});

        Usuario usuarioParam = new Usuario();
        Usuario usuarioSelected = null;
        usuarioParam.setNombreUsuario(usuario);
        PermisoUsuario permisosUsuario = new PermisoUsuario();
        try {
            usuarioSelected = this.usuarioService.verificaSiExisteUser(usuarioParam);
        } catch (Exception ex) {
            recibirMed.put("estatus", "EXCEPCION");
            recibirMed.put("mensaje", ex.getMessage());
            LOGGER.error("{}.receiveMedicamento(): {}",this.getClass().getCanonicalName() , ex.getMessage());
        }
        if (usuarioSelected != null) {
            if (!CustomWebSecurityConfigurerAdapter.customPasswordEncoder().matches(pass, usuarioSelected.getClaveAcceso())) {
                recibirMed.put("estatus", "ERROR");
                recibirMed.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
            } else {
                List<TransaccionPermisos> permisosList = consultaPermisos(usuarioSelected);
                if (permisosList != null && !permisosList.isEmpty()) {
                    usuarioSelected.setPermisosList(permisosList);
                    permisosUsuario = obtenPermisoUsuario(usuarioSelected, Transaccion_Enum.RECEPMEDICAMENTO.getSufijo());
                } else {
                    recibirMed.put("estatus", "ERROR");
                    recibirMed.put("mensaje", RESOURCES.getString("ses.obtener.datos"));
                }
                resul = 0;
                enviado = 0;
                fechaActual = new Date();
                if (codigoBarras != null) {
                    if (permisosUsuario.isPuedeCrear()) {
                        try {
                            clave = Constantes.INACTIVO;
                            lote = Constantes.INACTIVO;
                            if (tratarCodigoDeBarrasMedicamento(codigoBarras)) {
                                surtInsumoExtList.stream().filter(prdct -> prdct.getClaveInstitucional().equals(claveMedicamento)).forEach(cnsmr1 -> {
                                    clave = Constantes.ACTIVO;
                                    //Validar si el Medicamento esta Bloqueado
                                    if (cnsmr1.isActivo()) {
                                        if (cnsmr1.getSurtimientoEnviadoExtendList() != null) {
                                            cnsmr1.getSurtimientoEnviadoExtendList().stream().filter(prdct2 -> prdct2.getLote().equals(loteMedicamento)).forEach(cnSurtEnv -> {
                                                lote = Constantes.ACTIVO;
                                                //Validar si el lote esta bloqueado
                                                if (cnSurtEnv.getActivo() > 0) {
                                                    if (cnSurtEnv.getCaducidad().equals(caducidadMedicamento)) {
                                                        //Validar si la fecha de caducidad es mayor a la fecha actual
                                                        if (FechaUtil.isFechaMayorIgualQue(cnSurtEnv.getCaducidad(), fechaActual)) {
                                                            if (cnSurtEnv.getCantidadRecibido() == null) {
                                                                cnSurtEnv.setCantidadRecibido(0);
                                                            }
                                                            if (eliminarCodigo) {
                                                                resul = cnSurtEnv.getCantidadRecibido() - cantRecibir;
                                                                if (resul < 0) {
                                                                    resul = 0;
                                                                }
                                                            } else {
                                                                resul = cnSurtEnv.getCantidadRecibido() + cantRecibir;
                                                            }
                                                            if (resul <= cnSurtEnv.getCantidadEnviado()) {
                                                                enviado = cnSurtEnv.getCantidadRecibido();
                                                                cnSurtEnv.setCantidadRecibido(resul);
                                                                if (eliminarCodigo) {
                                                                    resul = enviado - cantRecibir;
                                                                    if (resul < 0) {
                                                                        cantRecibir = enviado;
                                                                    }
                                                                    cnsmr1.setCantidadRecepcion(cnsmr1.getCantidadRecepcion() - cantRecibir);
                                                                } else {
                                                                    cnsmr1.setCantidadRecepcion(cnsmr1.getCantidadRecepcion() + cantRecibir);
                                                                }
                                                            } else {
                                                                recibirMed.put("estatus", "ERROR");
                                                                recibirMed.put("mensaje", RESOURCES.getString("recepmedicamento.err.cantMayor"));
                                                            }
                                                        } else {
                                                            recibirMed.put("estatus", "ERROR");
                                                            recibirMed.put("mensaje", RESOURCES.getString("recepmedicamento.err.medCaducado"));
                                                        }
                                                    } else {
                                                        recibirMed.put("estatus", "ERROR");
                                                        recibirMed.put("mensaje", RESOURCES.getString("recepmedicamento.err.caducInvalida"));
                                                    }
                                                } else {
                                                    recibirMed.put("estatus", "ERROR");
                                                    recibirMed.put("mensaje", RESOURCES.getString("recepmedicamento.err.lotebloqueado"));
                                                }
                                            });
                                            if (!lote) {
                                                recibirMed.put("estatus", "ERROR");
                                                recibirMed.put("mensaje", RESOURCES.getString("recepmedicamento.err.noLote"));
                                            }
                                        }
                                    } else {
                                        recibirMed.put("estatus", "ERROR");
                                        recibirMed.put("mensaje", RESOURCES.getString("recepmedicamento.err.medibloqueado"));
                                    }
                                });
                                if (!clave) {
                                    recibirMed.put("estatus", "ERROR");
                                    recibirMed.put("mensaje", RESOURCES.getString("recepmedicamento.err.noencontrado"));
                                } else if (recibirMed.get("estatus") == null || !recibirMed.get("estatus").asText().equals("ERROR")) {
                                    recibirMed.put("estatus", "OK");
                                    recibirMed.set("surtimientoInsumoExtendedList", mapper.valueToTree(surtInsumoExtList));
                                }
                            } else {
                                recibirMed.put("estatus", "ERROR");
                                recibirMed.put("mensaje", RESOURCES.getString("recepmedicamento.err.codigoIncorrecto"));
                            }
                        } catch (IllegalArgumentException ex) {
                            recibirMed.put("estatus", "ERROR");
                            recibirMed.put("mensaje", "Error al recibir el medicamento: " + ex.getMessage());
                            LOGGER.error("Error al recibir el medicamento: {}", ex.getMessage());
                        }
                    } else {
                        recibirMed.put("estatus", "ERROR");
                        recibirMed.put("mensaje", RESOURCES.getString("recepmedicamento.err.sinpermisoProces"));
                    }
                }
            }
        } else {
            recibirMed.put("estatus", "ERROR");
            recibirMed.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
        }
        return Response.ok(recibirMed.toString()).build();
    }

    /**
     * Equivalente del método
     * RecepcionMedicamentoMB.procesarMedicamentoRecibido() Ejecuta la acción de
     * recepción sobre el Medicamento Surtido
     *
     * @param filtrosJson Cadena JSON con las credenciales del usuario, la lista
     * de Surtimientos y el registro seleccionado
     * @return Respuesta en formato JSON con la lista de Surtimientos
     * @throws java.io.IOException
     */
    @POST
    @Path("procesarMedicamentoRecibido")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response procesarMedicamentoRecibido(String filtrosJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode procesarRecibido = mapper.createObjectNode();
        JsonNode params = mapper.readTree(filtrosJson);
        if (!params.hasNonNull("usuario") || !params.hasNonNull("pass") || !params.hasNonNull("surtimientoInsumoExtendedList") || !params.hasNonNull("viewRecepcionMed")) {
            procesarRecibido.put("estatus", "ERROR");
            procesarRecibido.put("mensaje", RESOURCES.getString("webservice.err.invalidRequest"));
            return Response.ok(procesarRecibido.toString()).build();
        }
        String usuario = params.get("usuario").asText();
        String pass = params.get("pass").asText();
        JsonNode jn = params.get("surtimientoInsumoExtendedList");
        List<SurtimientoInsumo_Extend> surtInsumoExtList = mapper.readValue(jn.toString(), new TypeReference<List<SurtimientoInsumo_Extend>>() {});
        jn = params.get("viewRecepcionMed");
        VistaRecepcionMedicamento viewRecepcionMed = mapper.readValue(jn.toString(), VistaRecepcionMedicamento.class);

        Usuario usuarioParam = new Usuario();
        Usuario usuarioSelected = null;
        usuarioParam.setNombreUsuario(usuario);
        PermisoUsuario permisosUsuario = new PermisoUsuario();
        try {
            usuarioSelected = this.usuarioService.verificaSiExisteUser(usuarioParam);
        } catch (Exception ex) {
            procesarRecibido.put("estatus", "EXCEPCION");
            procesarRecibido.put("mensaje", ex.getMessage());
            LOGGER.error("{}.procesarMedicamentoRecibido(): {}",this.getClass().getCanonicalName() , ex.getMessage());
        }
        if (usuarioSelected != null) {
            if (!CustomWebSecurityConfigurerAdapter.customPasswordEncoder().matches(pass, usuarioSelected.getClaveAcceso())) {
                procesarRecibido.put("estatus", "ERROR");
                procesarRecibido.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
            } else {
                List<TransaccionPermisos> permisosList = consultaPermisos(usuarioSelected);
                if (permisosList != null && !permisosList.isEmpty()) {
                    usuarioSelected.setPermisosList(permisosList);
                    permisosUsuario = obtenPermisoUsuario(usuarioSelected, Transaccion_Enum.RECEPMEDICAMENTO.getSufijo());
                } else {
                    procesarRecibido.put("estatus", "ERROR");
                    procesarRecibido.put("mensaje", RESOURCES.getString("ses.obtener.datos"));
                }
                int result;
                if (permisosUsuario.isPuedeCrear()) {
                    try {
                        result = surtimientoService.actualizarListaCantidadRecbida(surtInsumoExtList, viewRecepcionMed, usuarioSelected.getIdUsuario());
                        if (result > 0) {
                            procesarRecibido.put("estatus", "OK");
                            if (result > 4) {
                                procesarRecibido.put("mensaje", RESOURCES.getString("recepmedicamento.info.devolucionGenerada"));
                            }
                        } else {
                            procesarRecibido.put("estatus", "ERROR");
                            procesarRecibido.put("mensaje", RESOURCES.getString("recepmedicamento.err.actualizar"));
                        }

                    } catch (Exception ex) {
                        procesarRecibido.put("estatus", "ERROR");
                        procesarRecibido.put("mensaje", RESOURCES.getString("recepmedicamento.err.exActualizar"));
                        LOGGER.error("Error al actualizar la cantidad recibida: {}", ex.getMessage());
                    }
                } else {
                    procesarRecibido.put("estatus", "ERROR");
                    procesarRecibido.put("mensaje", RESOURCES.getString("recepmedicamento.err.sinpermisoCrear"));
                }
            }
        } else {
            procesarRecibido.put("estatus", "ERROR");
            procesarRecibido.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
        }
        return Response.ok(procesarRecibido.toString()).build();
    }

    /**
     * Equivalente del método
     * RecepcionMedicamentoMB.filtrarListaPrescripciones() Obtiene la lista de
     * Surtimientos que pueden recibirse
     *
     * @param filtrosJson Cadena JSON con el usuario firmado
     * @return Respuesta en formato JSON con la lista de Surtimientos
     * @throws java.io.IOException
     */
    @POST
    @Path("filtrarListaPrescripcionesSurtidasSync")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response filtrarListaPrescripcionesSurtidasSync(String filtrosJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode listaSurtSync = mapper.createObjectNode();
        JsonNode params = mapper.readTree(filtrosJson);
        if (!params.hasNonNull("usuario")) {
            listaSurtSync.put("estatus", "ERROR");
            listaSurtSync.put("mensaje", RESOURCES.getString("webservice.err.invalidRequest"));
            return Response.ok(listaSurtSync.toString()).build();
        }
        String usuario = params.get("usuario").asText();
        Usuario usuarioParam = new Usuario();
        Usuario usuarioSelected = null;
        usuarioParam.setNombreUsuario(usuario);
        try {
            usuarioSelected = this.usuarioService.verificaSiExisteUser(usuarioParam);
        } catch (Exception ex) {
            listaSurtSync.put("estatus", "EXCEPCION");
            listaSurtSync.put("mensaje", ex.getMessage());
            LOGGER.error("{}.filtrarListaPrescripcionesSurtidasSync(): {}",this.getClass().getCanonicalName() , ex.getMessage());
        }
        if (usuarioSelected != null) {
            if (usuarioSelected.getIdEstructura() == null) {
                listaSurtSync.put("estatus", "ERROR");
                listaSurtSync.put("mensaje", RESOURCES.getString("sur.sin.almacen"));
            } else {
                try {
                    List<String> idsEstructura = estructuraService.obtenerIdsEstructuraJerarquica(usuarioSelected.getIdEstructura(), false);
                    if (idsEstructura == null) {
                        idsEstructura = new ArrayList<>();
                    }
                    idsEstructura.add(usuarioSelected.getIdEstructura());
                    List<VistaRecepcionMedicamento> listaPresc = viewRecepcionService.obtenerSurtimientosPorRecibir(idsEstructura);
                    listaSurtSync.put("estatus", "OK");
                    listaSurtSync.set("registros", mapper.valueToTree(listaPresc));
                } catch (Exception ex) {
                    listaSurtSync.put("estatus", "EXCEPCION");
                    listaSurtSync.put("mensaje", ex.getMessage());
                    LOGGER.error("{}.filtrarListaPrescripcionesSurtidasSync() : {}",this.getClass().getCanonicalName() , ex.getMessage());
                }
            }
        } else {
            listaSurtSync.put("estatus", "ERROR");
            listaSurtSync.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
        }
        return Response.ok(listaSurtSync.toString()).build();
    }

    /**
     * Obtiene la lista de Surtimientos que se han recibido
     *
     * @param filtrosJson Cadena JSON con el usuario firmado
     * @return Respuesta en formato JSON con la lista de Surtimientos
     * @throws java.io.IOException
     */
    @POST
    @Path("filtrarListaPrescripcionesRecibidasSync")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response filtrarListaPrescripcionesRecibidasSync(String filtrosJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode listaRecSync = mapper.createObjectNode();
        JsonNode params = mapper.readTree(filtrosJson);
        if (!params.hasNonNull("usuario")) {
            listaRecSync.put("estatus", "ERROR");
            listaRecSync.put("mensaje", RESOURCES.getString("webservice.err.invalidRequest"));
            return Response.ok(listaRecSync.toString()).build();
        }
        String usuario = params.get("usuario").asText();
        Usuario usuarioParam = new Usuario();
        Usuario usuarioSelected = null;
        usuarioParam.setNombreUsuario(usuario);
        try {
            usuarioSelected = this.usuarioService.verificaSiExisteUser(usuarioParam);
        } catch (Exception ex) {
            listaRecSync.put("estatus", "EXCEPCION");
            listaRecSync.put("mensaje", ex.getMessage());
            LOGGER.error("{}.filtrarListaPrescripcionesRecibidasSync(): {}",this.getClass().getCanonicalName() , ex.getMessage());
        }
        if (usuarioSelected != null) {
            if (usuarioSelected.getIdEstructura() == null) {
                listaRecSync.put("estatus", "ERROR");
                listaRecSync.put("mensaje", RESOURCES.getString("sur.sin.almacen"));
            } else {
                try {
                    List<String> idsEstructura = estructuraService.obtenerIdsEstructuraJerarquica(usuarioSelected.getIdEstructura(), false);
                    if (idsEstructura == null) {
                        idsEstructura = new ArrayList<>();
                    }
                    idsEstructura.add(usuarioSelected.getIdEstructura());
                    List<VistaRecepcionMedicamento> listaPresc = viewRecepcionService.obtenerSurtimientosRecibidos(idsEstructura);
                    listaRecSync.put("estatus", "OK");
                    ArrayNode registrosNode = mapper.valueToTree(listaPresc);
                    listaRecSync.set("registros", registrosNode);
                } catch (Exception ex) {
                    listaRecSync.put("estatus", "EXCEPCION");
                    listaRecSync.put("mensaje", ex.getMessage());
                    LOGGER.error("{}.filtrarListaPrescripcionesRecibidasSync() : {}",this.getClass().getCanonicalName() , ex.getMessage());
                }
            }
        } else {
            listaRecSync.put("estatus", "ERROR");
            listaRecSync.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
        }
        return Response.ok(listaRecSync.toString()).build();
    }

    /**
     * Equivalente del método RecepcionMedicamentoMB.obtenerDetalleSurtimiento()
     * Obtiene la lista de Surtimientos de acuerdo al Id de Surtimiento
     *
     * @param filtrosJson Cadena JSON con las credenciales del usuario y el ID
     * del surtimiento
     * @return Respuesta en formato JSON con la lista de Surtimientos
     * @throws java.io.IOException
     */
    @POST
    @Path("obtenerDetalleSurtimientoSync")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerDetalleSurtimientoSync(String filtrosJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode detallSurtSync = mapper.createObjectNode();
        JsonNode params = mapper.readTree(filtrosJson);
        if (!params.hasNonNull("usuario")) {
            detallSurtSync.put("estatus", "ERROR");
            detallSurtSync.put("mensaje", RESOURCES.getString("webservice.err.invalidRequest"));
            return Response.ok(detallSurtSync.toString()).build();
        }
        String usuario = params.get("usuario").asText();
        Usuario usuarioParam = new Usuario();
        Usuario usuarioSelected = null;
        usuarioParam.setNombreUsuario(usuario);
        try {
            usuarioSelected = this.usuarioService.verificaSiExisteUser(usuarioParam);
        } catch (Exception ex) {
            detallSurtSync.put("estatus", "EXCEPCION");
            detallSurtSync.put("mensaje", ex.getMessage());
            LOGGER.error("{}.obtenerDetalleSurtimientoSync(): {}",this.getClass().getCanonicalName() , ex.getMessage());
        }
        if (usuarioSelected != null) {
            try {
                List<SurtimientoInsumo_Extend> surtInsumoExtList = surtimientoService.detalleSurtimientoInsumoExtRecepMedi("OFFLINE");
                if (surtInsumoExtList != null) {
                    detallSurtSync.put("estatus", "OK");
                    detallSurtSync.set("registros", mapper.valueToTree(surtInsumoExtList));
                } else {
                    detallSurtSync.put("estatus", "ERROR");
                    detallSurtSync.put("mensaje", RESOURCES.getString("sur.incorrecto"));
                }
            } catch (Exception ex) {
                detallSurtSync.put("estatus", "ERROR");
                detallSurtSync.put("mensaje", "Error al obtener los Medicamentos: " + ex.getMessage());
                LOGGER.error("{}.obtenerDetalleSurtimientoSync(): {} {}", this.getClass().getCanonicalName(), "Error al obtener los Medicamentos: ", ex.getMessage());
            }
        } else {
            detallSurtSync.put("estatus", "ERROR");
            detallSurtSync.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
        }
        return Response.ok(detallSurtSync.toString()).build();
    }

    public int getResul() {
        return resul;
    }

    public void setResul(int resul) {
        this.resul = resul;
    }

    public int getEnviado() {
        return enviado;
    }

    public void setEnviado(int enviado) {
        this.enviado = enviado;
    }

    public boolean isEliminarCodigo() {
        return eliminarCodigo;
    }

    public void setEliminarCodigo(boolean eliminarCodigo) {
        this.eliminarCodigo = eliminarCodigo;
    }

    public int getCantRecibir() {
        return cantRecibir;
    }

    public void setCantRecibir(int cantRecibir) {
        this.cantRecibir = cantRecibir;
    }

    public boolean isClave() {
        return clave;
    }

    public void setClave(boolean clave) {
        this.clave = clave;
    }

    public boolean isLote() {
        return lote;
    }

    public void setLote(boolean lote) {
        this.lote = lote;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }
    
}
