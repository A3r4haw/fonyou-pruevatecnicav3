/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.ws.movil.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import mx.mc.enums.EstatusSurtimiento_Enum;
import mx.mc.enums.TipoJustificacion_Enum;
import mx.mc.enums.TipoMotivo_Enum;
import mx.mc.init.Constantes;
import mx.mc.init.CustomWebSecurityConfigurerAdapter;
import mx.mc.model.CantidadOffline;
import mx.mc.model.CodigoInsumo;
import mx.mc.model.Config;
import mx.mc.model.Inventario;
import mx.mc.model.MovimientoInventario;
import mx.mc.model.Surtimiento;
import mx.mc.model.SurtimientoEnviado;
import mx.mc.model.SurtimientoEnviado_Extend;
import mx.mc.model.SurtimientoInsumo;
import mx.mc.model.SurtimientoInsumo_Extend;
import mx.mc.model.Offline;
import mx.mc.model.Offline_Extend;
import mx.mc.model.Surtimiento_Extend;
import mx.mc.model.Usuario;
import mx.mc.service.ConfigService;
import mx.mc.service.InventarioService;
import mx.mc.service.SurtimientoService;
import mx.mc.service.UsuarioService;
import mx.mc.util.CodigoBarras;
import mx.mc.util.Comunes;
import mx.mc.util.FechaUtil;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 *
 * @author unava
 */
@Path("dispensacionOffline")
public class DispensacionMBMovilOffline extends SpringBeanAutowiringSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(DispensacionMBMovilOffline.class);
    protected static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private List<Config> configList;
    private final ObjectMapper mapperSurtimiento = new ObjectMapper();
    private final ObjectNode resultadoSurtimiento = mapperSurtimiento.createObjectNode();
    private final ArrayNode arraySurtimiento = mapperSurtimiento.createArrayNode();
    private final ObjectNode resultadoValidarSurtimiento = mapperSurtimiento.createObjectNode();

    @Context
    ServletContext servletContext;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private SurtimientoService surtimientoService;

    @Autowired
    private InventarioService inventarioService;

    @Autowired
    private ConfigService configService;

    @PostConstruct
    public void init() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    public DispensacionMBMovilOffline() {
        //No code needed in constructor
    }

    /**
     * Equivalente del método SesionMB.obtenerDatosSistema() Obtiene la lista de
     * configuraciones del Sistema
     */
    private void obtenerDatosSistema() {
        LOGGER.debug("mx.mc.magedbean.DispensacionMBMovilOffline.obtenerDatosSistema()");
        Config c = new Config();
        c.setActiva(Constantes.ACTIVO);
        try {
            configList = configService.obtenerLista(c);
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
    private ObjectNode agregarLotePorCodigo(List<Offline> surtimiento, Usuario usuarioSelected) {
        LOGGER.trace("{}.agregarLotePorCodigo()", this.getClass().getCanonicalName());
        boolean error = true;
        boolean encontrado = Constantes.INACTIVO;
        obtenerDatosSistema();
        int noDiasCaducidad = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_DIASPARACADUCIDAD);
        Boolean isCodificacionGS1 = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_CODIGOS_GS1) == 1;
        for (Offline surte : surtimiento) {
            ObjectNode resultado = mapperSurtimiento.createObjectNode();
            List<CodigoInsumo> ci = CodigoBarras.parsearCodigoDeBarrasList(surte.getCodigoBarras(), isCodificacionGS1);
            ObjectMapper mapper = new ObjectMapper();
            List<String> idsurte = new ArrayList<>();
            ObjectNode resultadoError = mapper.createObjectNode();
            for (CodigoInsumo cs : ci) {
                /* TODO:
                if (cs == null) {
                *** Si el parseo falla, no se agrega un registro a la lista de CodigoInsumo
                *** por lo que no se puede notificar de regreso a la App en caso de un error en la código de barras
                for (SurtimientoInsumo_Extend surtimientoInsumo : surte.getSurtimientoInsumoExtendedList()) {
                    if (surte.getCodigoBarras().equals(surtimientoInsumo.getClaveInstitucional())) {
                        resultadoError.put("estatus", "ERROR");
                        resultadoError.put("idSurtimientoInsumo",surtimientoInsumo.getIdSurtimientoInsumo() );
                        resultadoError.put("idSurtimiento",surtimientoInsumo.getIdSurtimiento() );
                        resultadoError.put("mensaje", RESOURCES.getString("err.parser"));
                    }
                }
                error = true;
                arraySurtimiento.add(resultadoError);
                continue;
            } else */ if (FechaUtil.sumarRestarDiasFecha(new java.util.Date(), noDiasCaducidad).after(cs.getFecha())) {
                    for (SurtimientoInsumo_Extend surtimientoInsumo : surte.getSurtimientoInsumoExtendedList()) {
                        if (cs.getClave().equals(surtimientoInsumo.getClaveInstitucional())) {
                            resultadoError.put("estatus", "ERROR");
                            resultadoError.put("idSurtimientoInsumo", surtimientoInsumo.getIdSurtimientoInsumo());
                            resultadoError.put("idSurtimiento", surtimientoInsumo.getIdSurtimiento());
                            resultadoError.put("mensaje", RESOURCES.getString("sur.caducidadvencida") + " " + cs.getClave() + " " + cs.getLote() + " " + String.valueOf(cs.getFecha()));
                        }
                    }
                    error = true;
                    arraySurtimiento.add(resultadoError);
                } else {
                    List<SurtimientoEnviado_Extend> surtimientoEnviadoExtendList;
                    SurtimientoEnviado_Extend surtimientoEnviadoExtend;
                    surte.getSurtimientoExtend().setIdEstructuraAlmacen(usuarioSelected.getIdEstructura());

                    for (SurtimientoInsumo_Extend surtimientoInsumo : surte.getSurtimientoInsumoExtendedList()) {
                        ObjectMapper mapp = new ObjectMapper();
                        ObjectNode resError = mapp.createObjectNode();
                        for (CantidadOffline cantidad : surte.getCantidad()) {
                            if (cantidad.getCantidadSurtida() < 1) {
                                resError.put("estatus", "ERROR");
                                resError.put("mensaje", RESOURCES.getString("sur.cantidadincorrecta"));
                                resError.put("idSurtimientoInsumo", surtimientoInsumo.getIdSurtimientoInsumo());
                                resError.put("idSurtimiento", surtimientoInsumo.getIdSurtimiento());
                                error = true;
                            } else if (surtimientoInsumo.getClaveInstitucional().equals(cs.getClave())) {
                                encontrado = Constantes.ACTIVO;
                                if (!surtimientoInsumo.isMedicamentoActivo()) {
                                    resError.put("estatus", "ERROR");
                                    resError.put("mensaje", RESOURCES.getString("sur.clavebloqueada"));
                                    resError.put("idSurtimientoInsumo", surtimientoInsumo.getIdSurtimientoInsumo());
                                    resError.put("idSurtimiento", surtimientoInsumo.getIdSurtimiento());

                                    error = true;
                                } else {
                                    Inventario inventarioSurtido = null;
                                    Integer cantidadXCaja = null;
                                    String claveProveedor = null;
                                    try {
                                        inventarioSurtido = inventarioService.obtenerInventariosPorInsumoEstructuraLoteYCantidadXCajaYFechaCad(
                                                surtimientoInsumo.getIdInsumo(), surte.getSurtimientoExtend().getIdEstructuraAlmacen(),
                                                 cs.getLote(), cantidadXCaja, claveProveedor, cs.getFecha()
                                        );
                                    } catch (Exception ex) {
                                        LOGGER.error(RESOURCES.getString("sur.loteincorrecto"), ex);
                                    }

                                    if (inventarioSurtido == null) {
                                        resError.put("estatus", "ERROR");
                                        resError.put("mensaje", RESOURCES.getString("sur.loteincorrecto"));
                                        resError.put("idSurtimientoInsumo", surtimientoInsumo.getIdSurtimientoInsumo());
                                        resError.put("idSurtimiento", surtimientoInsumo.getIdSurtimiento());
                                        error = true;
                                    } else if (inventarioSurtido.getActivo() == 0) {
                                        resError.put("estatus", "ERROR");
                                        resError.put("mensaje", RESOURCES.getString("sur.lotebloqueado"));
                                        resError.put("idSurtimientoInsumo", surtimientoInsumo.getIdSurtimientoInsumo());
                                        resError.put("idSurtimiento", surtimientoInsumo.getIdSurtimiento());
                                        error = true;
                                    } else if (inventarioSurtido.getCantidadActual() < cantidad.getCantidadSurtida()) {
                                        resError.put("estatus", "ERROR");
                                        resError.put("mensaje", RESOURCES.getString("sur.cantidadinsuficiente"));
                                        resError.put("idSurtimientoInsumo", surtimientoInsumo.getIdSurtimientoInsumo());
                                        resError.put("idSurtimiento", surtimientoInsumo.getIdSurtimiento());
                                        error = true;
                                    } else {
                                        surtimientoEnviadoExtendList = new ArrayList<>();
                                        if (surtimientoInsumo.getSurtimientoEnviadoExtendList() != null) {
                                            surtimientoEnviadoExtendList.addAll(surtimientoInsumo.getSurtimientoEnviadoExtendList());
                                        }
                                        if (cantidad.getIdSurtimientoInsumo().equals(surtimientoInsumo.getIdSurtimientoInsumo())) {

                                            // regla: si es primer Lote pistoleado solo muestra una linea en subdetalle
                                            if (surtimientoEnviadoExtendList.isEmpty()) {
                                                surtimientoEnviadoExtend = new SurtimientoEnviado_Extend();
                                                surtimientoEnviadoExtend.setIdSurtimientoEnviado(Comunes.getUUID());
                                                surtimientoEnviadoExtend.setIdSurtimientoInsumo(surtimientoInsumo.getIdSurtimientoInsumo());
                                                surtimientoEnviadoExtend.setIdInventarioSurtido(inventarioSurtido.getIdInventario());
                                                surtimientoEnviadoExtend.setCantidadEnviado(cantidad.getCantidadSurtida());

                                                surtimientoEnviadoExtend.setLote(cs.getLote());
                                                surtimientoEnviadoExtend.setCaducidad(cs.getFecha());
                                                surtimientoEnviadoExtend.setIdInsumo(inventarioSurtido.getIdInsumo());
                                                surtimientoEnviadoExtendList.add(surtimientoEnviadoExtend);

                                            } else {
                                                boolean agrupaLote = false;
                                                for (SurtimientoEnviado_Extend surtimientoEnviadoRegistado : surtimientoEnviadoExtendList) {
                                                    // regla: si se pistolea mas de un medicmento con el mismo lote se agrupan por lotes sumarizando las cantidades
                                                    if (surtimientoEnviadoRegistado.getLote().equals(cs.getLote())
                                                            && surtimientoEnviadoRegistado.getCaducidad().equals(cs.getFecha())
                                                            && surtimientoEnviadoRegistado.getIdInsumo().equals(inventarioSurtido.getIdInsumo())) {
                                                        surtimientoEnviadoRegistado.setCantidadEnviado(cantidad.getCantidadSurtida());
                                                        agrupaLote = true;
                                                        break;
                                                    }
                                                }

                                                // regla: si es el único Lote pistoleado agrega una linea nueva en subdetalle
                                                if (!agrupaLote) {
                                                    surtimientoEnviadoExtend = new SurtimientoEnviado_Extend();
                                                    surtimientoEnviadoExtend.setIdSurtimientoEnviado(Comunes.getUUID());
                                                    surtimientoEnviadoExtend.setIdSurtimientoInsumo(surtimientoInsumo.getIdSurtimientoInsumo());
                                                    surtimientoEnviadoExtend.setIdInventarioSurtido(inventarioSurtido.getIdInventario());
                                                    surtimientoEnviadoExtend.setCantidadEnviado(cantidad.getCantidadSurtida());

                                                    surtimientoEnviadoExtend.setLote(cs.getLote());
                                                    surtimientoEnviadoExtend.setCaducidad(cs.getFecha());
                                                    surtimientoEnviadoExtend.setIdInsumo(inventarioSurtido.getIdInsumo());
                                                    surtimientoEnviadoExtendList.add(surtimientoEnviadoExtend);
                                                }
                                            }
                                            surtimientoInsumo.setFechaEnviada(new java.util.Date());
                                            surtimientoInsumo.setIdUsuarioEnviada(usuarioSelected.getIdUsuario());
                                            surtimientoInsumo.setCantidadEnviada(cantidad.getCantidadSurtida());
                                            surtimientoInsumo.setSurtimientoEnviadoExtendList(surtimientoEnviadoExtendList);
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
                            } else {
                                idsurte.add(surtimientoInsumo.getIdSurtimientoInsumo());
                            }
                        }
                        if (!resError.toString().equals("{}") || resError.toString().contains("estatus")) {
                            arraySurtimiento.add(resError);
                        }
                        if (!error) {
                            resultado.put("estatus", "OK");
                            ArrayNode registrosNode = mapperSurtimiento.valueToTree(surte.getSurtimientoInsumoExtendedList());
                            resultado.set("surtimientoInsumoExtendedList", registrosNode);
                        }
                    }
                }
            }
            if (!encontrado) {
                for (int i = 0; i < idsurte.size(); i++) {
                    resultado.put("estatus", "ERROR");
                    resultado.put("idSurtimientoInsumo", idsurte.get(i));
                    resultado.put("idSurtimiento", surte.getSurtimientoExtend().getIdSurtimiento());
                    resultado.put("mensaje", RESOURCES.getString("sur.claveincorrecta"));
                }

                arraySurtimiento.add(resultado);
            }
            arraySurtimiento.add(resultado);
        }

        resultadoSurtimiento.set("surtimientos", arraySurtimiento);
        return resultadoSurtimiento;
    }

    @POST
    @Path("obtenerSurtimeintosTransito")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerSurtimeintosTransito(String filtrosJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode resSurTrans = mapper.createObjectNode();
        JsonNode params = mapper.readTree(filtrosJson);
        if (!params.hasNonNull("usuario") || !params.hasNonNull("pass") || !params.hasNonNull("surteList")) {
            resSurTrans.put("estatus", "ERROR");
            resSurTrans.put("mensaje", RESOURCES.getString("webservice.err.invalidRequest"));
            return Response.ok(resSurTrans.toString()).build();
        }
        String usuario = params.get("usuario").asText();
        JsonNode jn = params.get("surtimiento_Extend");
        List<Surtimiento_Extend> surtimientoExtendedList = mapper.readValue(jn.toString(), new TypeReference<List<Surtimiento_Extend>>() {});
        Usuario usuarioParam = new Usuario();
        Usuario usuarioSelected = null;
        usuarioParam.setNombreUsuario(usuario);
        try {
            usuarioSelected = this.usuarioService.verificaSiExisteUser(usuarioParam);
        } catch (Exception ex) {
            resSurTrans.put("estatus", "EXCEPCION");
            resSurTrans.put("mensaje", ex.getMessage());
            LOGGER.error("{}.verSurtimiento(): {}", this.getClass().getCanonicalName(), ex.getMessage());
        }
        if (usuarioSelected != null) {
            List<Integer> listEstatusSurtimiento = new ArrayList<>();
            List<String> listSurtimientos = new ArrayList<>();
            listEstatusSurtimiento.add(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
            for (Surtimiento_Extend surte : surtimientoExtendedList) {
                listSurtimientos.add(surte.getIdSurtimiento());
            }
            try {
                if (!listSurtimientos.isEmpty()) {
                    if (surtimientoExtendedList != null) {
                        surtimientoExtendedList.clear();
                    }
                    surtimientoExtendedList = surtimientoService.obtenerSurtimientoConEstatus(listSurtimientos, listEstatusSurtimiento);
                    resSurTrans.put("estatus", "OK");
                    ArrayNode registrosNode = mapperSurtimiento.valueToTree(surtimientoExtendedList);
                    resSurTrans.set("SurtidosList", registrosNode);
                    arraySurtimiento.add(resSurTrans);
                } else {
                    resSurTrans.put("estatus", "EXCEPCION");
                    resSurTrans.put("mensaje", "Erro al obtener surtimientos, contacté a soporte");
                    return Response.ok(resSurTrans.toString()).build();
                }
            } catch (Exception ex) {
                LOGGER.error("{}.verSurtimiento(): {}", this.getClass().getCanonicalName(), ex.getMessage());
            }

        } else {
            resSurTrans.put("estatus", "ERROR");
            resSurTrans.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
            return Response.ok(resSurTrans.toString()).build();
        }
        return Response.ok(arraySurtimiento.toString()).build();
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
        ObjectNode resultado = mapper.createObjectNode();
        JsonNode params = mapper.readTree(filtrosJson);
        Offline surte;
        if (!params.hasNonNull("usuario") || !params.hasNonNull("pass") || !params.hasNonNull("surtimientos")) {
            resultadoSurtimiento.put("estatus", "ERROR");
            resultadoSurtimiento.put("mensaje", RESOURCES.getString("webservice.err.invalidRequest"));
            return Response.ok(resultadoSurtimiento.toString()).build();
        }
        String usuario = params.get("usuario").asText();
        String pass = params.get("pass").asText();
        JsonNode surtimientosJ = params.get("surtimientos");
        List<Offline> surtimientos = mapper.readValue(surtimientosJ.toString(), new TypeReference<List<Offline>>() {
        });
        List<Offline> surtimientosA = new ArrayList<>();
        Usuario usuarioParam = new Usuario();
        Usuario usuarioSelected;
        usuarioParam.setNombreUsuario(usuario);
        try {
            usuarioSelected = this.usuarioService.verificaSiExisteUser(usuarioParam);
        } catch (Exception ex) {
            resultadoSurtimiento.put("estatus", "EXCEPCION");
            resultadoSurtimiento.put("mensaje", ex.getMessage());
            LOGGER.error("{}.verSurtimiento(): {}", this.getClass().getCanonicalName(), ex.getMessage());
            return Response.ok(resultadoSurtimiento.toString()).build();
        }
        if (usuarioSelected != null) {
            if (!CustomWebSecurityConfigurerAdapter.customPasswordEncoder().matches(pass, usuarioSelected.getClaveAcceso())) {
                resultadoSurtimiento.put("estatus", "ERROR");
                resultadoSurtimiento.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
                return Response.ok(resultadoSurtimiento.toString()).build();
            } else {
                try {
                    for (int i = 0; i < surtimientos.size(); i++) {
                        surte = surtimientos.get(i);
                        if (surte.getCodigoBarras() == null || surte.getCodigoBarras().isEmpty()) {
                            resultado.put("estatus", "ERROR");
                            resultado.put("idSurtimiento", surte.getSurtimientoExtend().getIdSurtimiento());
                            resultado.put("mensaje", RESOURCES.getString("sur.codigoincorrecto"));
                            arraySurtimiento.add(resultado);
                        } else if (surte.getSurtimientoExtend() == null || surte.getSurtimientoInsumoExtendedList() == null || surte.getSurtimientoInsumoExtendedList().isEmpty()) {
                            resultado.put("estatus", "ERROR");
                            resultado.put("idSurtimiento", surte.getSurtimientoExtend().getIdSurtimiento());
                            resultado.put("mensaje", RESOURCES.getString("sur.incorrecto"));
                            arraySurtimiento.add(resultado);
                        } else {
                            surtimientosA.add(surte);
                        }
                    }
                    agregarLotePorCodigo(surtimientosA, usuarioSelected);

                } catch (Exception e) {
                    LOGGER.error("{}.validaLecturaPorCodigo(): {}", this.getClass().getCanonicalName(), e.getMessage());
                }
            }
        } else {
            resultadoSurtimiento.put("estatus", "ERROR");
            resultadoSurtimiento.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
            return Response.ok(resultadoSurtimiento.toString()).build();
        }
        resultadoSurtimiento.set("surtimientos", arraySurtimiento);
        return Response.ok(resultadoSurtimiento.toString()).build();
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

        JSONObject jsonObjList = new JSONObject(filtrosJson);
        List<String> surteOK;
        JSONArray surtimientos = jsonObjList.getJSONArray("surtimientos");
        List<Offline_Extend> surtimientosVal = mapper.readValue(surtimientos.toString(), new TypeReference<List<Offline_Extend>>() {
        });
        JsonNode params = mapper.readTree(filtrosJson);
        if (!params.hasNonNull("usuario") || !params.hasNonNull("pass")
                || !params.hasNonNull("surtimientos")) {
            resultadoValidarSurtimiento.put("estatus", "ERROR");
            resultadoValidarSurtimiento.put("mensaje", RESOURCES.getString("webservice.err.invalidRequest"));
            return Response.ok(resultadoValidarSurtimiento.toString()).build();
        }
        String usuario = params.get("usuario").asText();
        String pass = params.get("pass").asText();

        Usuario usuarioParam = new Usuario();
        Usuario usuarioSelected;
        usuarioParam.setNombreUsuario(usuario);

        try {
            usuarioSelected = this.usuarioService.verificaSiExisteUser(usuarioParam);
        } catch (Exception ex) {
            resultadoValidarSurtimiento.put("estatus", "EXCEPCION");
            resultadoValidarSurtimiento.put("mensaje", ex.getMessage());
            LOGGER.error("{}.validaSurtimiento(): {}", this.getClass().getCanonicalName(), ex.getMessage());
            return Response.ok(resultadoValidarSurtimiento.toString()).build();
        }
        if (usuarioSelected != null
                && !CustomWebSecurityConfigurerAdapter.customPasswordEncoder().matches(pass, usuarioSelected.getClaveAcceso())) {
            resultadoValidarSurtimiento.put("estatus", "ERROR");
            resultadoValidarSurtimiento.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
            return Response.ok(resultadoValidarSurtimiento.toString()).build();
        }

        int noDiasCaducidad = 1;
        obtenerDatosSistema();
        try {
            for (int i = 0; i < surtimientosVal.size(); i++) {
                boolean parcial = false;
                surteOK = new ArrayList<>();
                ObjectMapper mappeRes = new ObjectMapper();
                ObjectNode resultado = mappeRes.createObjectNode();
                String estatus = surtimientosVal.get(i).getEstatus();
                if (estatus.equals("ERROR")) {
                    resultado.put("mensaje", surtimientosVal.get(i).getMensaje());
                    resultado.put("idSurtimientoInsumo", surtimientosVal.get(i).getIdSurtimientoInsumo());
                    resultado.put("idSurtimiento", surtimientosVal.get(i).getIdSurtimiento());
                    resultado.put("estatus", surtimientosVal.get(i).getEstatus());
                    arraySurtimiento.add(resultado);
                } else if (surtimientosVal.get(i).getSurtimientoExtend() == null || surtimientosVal.get(i).getSurtimientoInsumoExtendedList() == null
                        || surtimientosVal.get(i).getSurtimientoInsumoExtendedList().isEmpty()) {
                    resultado.put("estatus", "ERROR");
                    resultado.put("idSurtimientoInsumo", surtimientosVal.get(i).getIdSurtimientoInsumo());
                    resultado.put("idSurtimiento", surtimientosVal.get(i).getIdSurtimiento());
                    resultado.put("mensaje", RESOURCES.getString("sur.invalido"));
                    arraySurtimiento.add(resultado);
                } else {
                    Integer numeroMedicamentosSurtidos = 0;
                    Surtimiento surtimiento = null;
                    List<SurtimientoInsumo> surtimientoInsumoList = new ArrayList<>();
                    List<SurtimientoInsumo> surtimientoInsumoListParcial = new ArrayList<>();
                    Surtimiento surtiminetoParcial = new Surtimiento();
                    List<SurtimientoEnviado> surtimientoEnviadoList = new ArrayList<>();

                    Inventario inventarioAfectado;
                    List<Inventario> inventarioList = new ArrayList<>();

                    List<MovimientoInventario> movimientoInventarioList = new ArrayList<>();
                    MovimientoInventario movimientoInventarioAfectado;
                    for (SurtimientoInsumo_Extend surtimientoInsumo_Ext : surtimientosVal.get(i).getSurtimientoInsumoExtendedList()) {
                        if (surtimientoInsumo_Ext.getCantidadEnviada() == null) {
                            surtimientoInsumo_Ext.setCantidadEnviada(0);
                        }
                        if (surtimientoInsumo_Ext.getSurtimientoEnviadoExtendList() != null
                                && !surtimientoInsumo_Ext.getSurtimientoEnviadoExtendList().isEmpty()) {
                            if (surtimientoInsumo_Ext.getCantidadEnviada().intValue() != surtimientoInsumo_Ext.getCantidadSolicitada().intValue()) {
                                Integer folio = surtimientoInsumo_Ext.getIdTipoJustificante();
                                if (folio == null) {
                                    resultado.put("estatus", "ERROR");
                                    resultado.put("idSurtimientoInsumo", surtimientoInsumo_Ext.getIdSurtimientoInsumo());
                                    resultado.put("idSurtimiento", surtimientoInsumo_Ext.getIdSurtimiento());
                                    resultado.put("mensaje", RESOURCES.getString("dispensacion.err.surtmedicamento"));
                                    arraySurtimiento.add(resultado);
                                }
                            }
                            Inventario inventarioSurtido;
                            for (SurtimientoEnviado_Extend surtimientoEnviado_Ext : surtimientoInsumo_Ext.getSurtimientoEnviadoExtendList()) {
                                inventarioSurtido = null;
                                if (!surtimientoEnviado_Ext.getIdInventarioSurtido().isEmpty()) {
                                    
                                    inventarioSurtido = inventarioService.obtener(new Inventario(surtimientoEnviado_Ext.getIdInventarioSurtido()));                                    

                                    if (inventarioSurtido == null) {
                                        resultado.put("estatus", "ERROR");
                                        resultado.put("idSurtimientoInsumo", surtimientoInsumo_Ext.getIdSurtimientoInsumo());
                                        resultado.put("idSurtimiento", surtimientoInsumo_Ext.getIdSurtimiento());
                                        resultado.put("mensaje", RESOURCES.getString("sur.loteincorrecto"));
                                        arraySurtimiento.add(resultado);
                                    } else if (inventarioSurtido.getActivo() != 1) {
                                        resultado.put("estatus", "ERROR");
                                        resultado.put("idSurtimientoInsumo", surtimientoInsumo_Ext.getIdSurtimientoInsumo());
                                        resultado.put("idSurtimiento", surtimientoInsumo_Ext.getIdSurtimiento());
                                        resultado.put("mensaje", RESOURCES.getString("sur.lotebloqueado"));
                                        arraySurtimiento.add(resultado);
                                    } else if (FechaUtil.sumarRestarDiasFecha(new java.util.Date(), noDiasCaducidad).after(inventarioSurtido.getFechaCaducidad())) {
                                        resultado.put("estatus", "ERROR");
                                        resultado.put("idSurtimientoInsumo", surtimientoInsumo_Ext.getIdSurtimientoInsumo());
                                        resultado.put("idSurtimiento", surtimientoInsumo_Ext.getIdSurtimiento());
                                        resultado.put("mensaje", RESOURCES.getString("sur.caducidadvencida"));
                                        arraySurtimiento.add(resultado);
                                    } else if (inventarioSurtido.getCantidadActual() < surtimientoEnviado_Ext.getCantidadEnviado()) {
                                        resultado.put("estatus", "ERROR");
                                        resultado.put("idSurtimientoInsumo", surtimientoInsumo_Ext.getIdSurtimientoInsumo());
                                        resultado.put("idSurtimiento", surtimientoInsumo_Ext.getIdSurtimiento());
                                        resultado.put("mensaje", RESOURCES.getString("sur.cantidadinsuficiente"));
                                        arraySurtimiento.add(resultado);
                                    } else {
                                        numeroMedicamentosSurtidos++;
                                        inventarioAfectado = new Inventario();
                                        inventarioAfectado.setIdInventario(surtimientoEnviado_Ext.getIdInventarioSurtido());
                                        inventarioAfectado.setCantidadActual(surtimientoEnviado_Ext.getCantidadEnviado());
                                        inventarioList.add(inventarioAfectado);

                                        movimientoInventarioAfectado = new MovimientoInventario();
                                        movimientoInventarioAfectado.setIdMovimientoInventario(Comunes.getUUID());
                                        movimientoInventarioAfectado.setIdTipoMotivo(TipoMotivo_Enum.SAL_PRESC_POR_SURTIMIENTO_DE_PRESCRIPCION.getMotivoValue());
                                        movimientoInventarioAfectado.setFecha(new java.util.Date());
                                        movimientoInventarioAfectado.setIdUsuarioMovimiento(usuarioSelected.getIdUsuario());
                                        movimientoInventarioAfectado.setIdEstrutcuraOrigen(usuarioSelected.getIdEstructura());
                                        movimientoInventarioAfectado.setIdEstrutcuraDestino(surtimientosVal.get(i).getSurtimientoExtend().getIdEstructura());
                                        movimientoInventarioAfectado.setIdInventario(surtimientoEnviado_Ext.getIdInventarioSurtido());
                                        movimientoInventarioAfectado.setCantidad(surtimientoEnviado_Ext.getCantidadEnviado());
                                        movimientoInventarioAfectado.setFolioDocumento(surtimientosVal.get(i).getSurtimientoExtend().getFolio());
                                        movimientoInventarioList.add(movimientoInventarioAfectado);

                                    }
                                }
                                surtimientoEnviado_Ext.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.SURTIDO.getValue());
                                surtimientoEnviado_Ext.setInsertFecha(new java.util.Date());
                                surtimientoEnviado_Ext.setInsertIdUsuario(usuarioSelected.getIdUsuario());

                                if (surtimientoEnviado_Ext.getCantidadRecibido() == null) {
                                    surtimientoEnviado_Ext.setCantidadRecibido(0);
                                }
                                surtimientoEnviadoList.add((SurtimientoEnviado) surtimientoEnviado_Ext);
                                surteOK.add(surtimientoInsumo_Ext.getIdSurtimientoInsumo());

                            }
                        }
                        SurtimientoInsumo surtimientoInsumo = new SurtimientoInsumo();
                        surtimientoInsumo.setIdSurtimientoInsumo(surtimientoInsumo_Ext.getIdSurtimientoInsumo());
                        surtimientoInsumo.setIdUsuarioAutCanRazn(surtimientoInsumo_Ext.getIdUsuarioAutCanRazn());
                        surtimientoInsumo.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.SURTIDO.getValue());
                        surtimientoInsumo.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
                        surtimientoInsumo.setUpdateFecha(new java.util.Date());
                        surtimientoInsumo.setFechaEnviada(surtimientoInsumo_Ext.getFechaEnviada());
                        surtimientoInsumo.setIdUsuarioEnviada(usuarioSelected.getIdUsuario());
                        surtimientoInsumo.setCantidadEnviada(surtimientoInsumo_Ext.getCantidadEnviada());
                        surtimientoInsumo.setIdTipoJustificante(surtimientoInsumo_Ext.getIdTipoJustificante());
                        surtimientoInsumo.setCantidadVale(0);
                        surtimientoInsumo.setNotas(surtimientoInsumo_Ext.getNotas());
                        surtimientoInsumoList.add(surtimientoInsumo);

                        if (surtimientoInsumo_Ext.getCantidadEnviada() < surtimientoInsumo_Ext.getCantidadSolicitada()) {
                            if (!parcial) {
                                surtiminetoParcial.setIdSurtimiento(Comunes.getUUID());
                                surtiminetoParcial.setIdEstructuraAlmacen(surtimientosVal.get(i).getSurtimientoExtend().getIdEstructuraAlmacen());
                                DecimalFormat twodigits = new DecimalFormat("00");
                                if (surtimientosVal.get(i).getSurtimientoExtend().isParcial()) {
                                    String str = surtimientosVal.get(i).getSurtimientoExtend().getFolio().substring(0, surtimientosVal.get(i).getSurtimientoExtend().getFolio().length() - 2);
                                    String s = surtimientosVal.get(i).getSurtimientoExtend().getFolio().split(str)[1];
                                    int n = Integer.valueOf(s);
                                    n++;
                                    surtiminetoParcial.setFolio(str + twodigits.format(n));
                                } else {
                                    String f = surtimientosVal.get(i).getSurtimientoExtend().getFolio();
                                    surtiminetoParcial.setFolio(f + twodigits.format(1));
                                }
                                surtiminetoParcial.setIdPrescripcion(surtimientosVal.get(i).getSurtimientoExtend().getIdPrescripcion());
                                surtiminetoParcial.setFechaProgramada(surtimientosVal.get(i).getSurtimientoExtend().getFechaProgramada());
                                surtiminetoParcial.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                                surtiminetoParcial.setIdEstatusMirth(surtimientosVal.get(i).getSurtimientoExtend().getIdEstatusMirth());
                                surtiminetoParcial.setInsertFecha(new Date());
                                surtiminetoParcial.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                                parcial = true;
                                surtiminetoParcial.setParcial(parcial);
                                
                            }
                            SurtimientoInsumo surtimientoInsumoParcial = new SurtimientoInsumo();
                            surtimientoInsumoParcial.setIdSurtimientoInsumo(Comunes.getUUID());
                            surtimientoInsumoParcial.setIdSurtimiento(surtiminetoParcial.getIdSurtimiento());
                            surtimientoInsumoParcial.setIdPrescripcionInsumo(surtimientoInsumo_Ext.getIdPrescripcionInsumo());
                            surtimientoInsumoParcial.setFechaProgramada(surtimientoInsumo_Ext.getFechaProgramada());
                            surtimientoInsumoParcial.setCantidadSolicitada(surtimientoInsumo_Ext.getCantidadSolicitada() - surtimientoInsumo_Ext.getCantidadEnviada());
                            surtimientoInsumoParcial.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                            surtimientoInsumoParcial.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                            surtimientoInsumoParcial.setInsertFecha(new Date());
                            surtimientoInsumoParcial.setIdEstatusMirth(surtimientoInsumo_Ext.getIdEstatusMirth());
                            surtimientoInsumoParcial.setNumeroMedicacion(surtimientoInsumo_Ext.getNumeroMedicacion());
                            surtimientoInsumoListParcial.add(surtimientoInsumoParcial);
                        }
                    }
                    if (numeroMedicamentosSurtidos == 0) {
                        resultado.put("estatus", "ERROR");
                        resultado.put("idSurtimiento", surtimientosVal.get(i).getSurtimientoExtend().getIdSurtimiento());
                        resultado.put("mensaje", RESOURCES.getString("sur.error"));
                        arraySurtimiento.add(resultado);
                    } else {
                        surtimientosVal.get(i).getSurtimientoExtend().setIdEstructuraAlmacen(usuarioSelected.getIdEstructura());
                        surtimientosVal.get(i).getSurtimientoExtend().setIdEstatusSurtimiento(EstatusSurtimiento_Enum.SURTIDO.getValue());
                        surtimientosVal.get(i).getSurtimientoExtend().setUpdateFecha(new java.util.Date());
                        surtimientosVal.get(i).getSurtimientoExtend().setUpdateIdUsuario(usuarioSelected.getIdUsuario());

                        surtimiento = (Surtimiento) surtimientosVal.get(i).getSurtimientoExtend();
                        
                        boolean status = surtimientoService.surtirPrescripcion(
                                surtimiento,
                                surtimientoInsumoList,
                                surtimientoEnviadoList,
                                inventarioList,
                                movimientoInventarioList);
                        if (status && parcial) {
                            surtimientoService.surtimientoParcial(surtiminetoParcial, surtimientoInsumoListParcial);
                        }
                        
                        if (status) {
                            for (String str : surteOK) {
                                ObjectNode resultadoAux = mappeRes.createObjectNode();
                                resultadoAux.put("estatus", (status ? "OK" : "ERROR"));
                                resultadoAux.put("idSurtimientoInsumo", str);
                                resultadoAux.put("idSurtimiento", surtimientosVal.get(i).getSurtimientoExtend().getIdSurtimiento());
                                arraySurtimiento.add(resultadoAux);
                            }
                        } else {
                            resultado.put("estatus", (status ? "OK" : "ERROR"));
                            resultado.put("mensaje", RESOURCES.getString("sur.error"));
                            resultado.put("errorPrescripcion", true);
                            resultado.put("idSurtimiento", surtimientosVal.get(i).getSurtimientoExtend().getIdSurtimiento());
                            arraySurtimiento.add(resultado);
                        }
                    }
                }
            }
        } catch (Exception e) {
            ObjectMapper mappeRes = new ObjectMapper();
            ObjectNode resultado = mappeRes.createObjectNode();
            resultado.put("mensaje", e.getMessage());
            arraySurtimiento.add(resultado);
        }
        resultadoValidarSurtimiento.set("surtimientos", arraySurtimiento);
        return Response.ok(resultadoValidarSurtimiento.toString()).build();
    }
}
