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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.init.CustomWebSecurityConfigurerAdapter;
import mx.mc.model.CantidadOffline;
import mx.mc.model.CantidadOffline_Extended;
import mx.mc.model.CodigoInsumo;
import mx.mc.model.Offline_Extend;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.SurtimientoEnviado_Extend;
import mx.mc.model.SurtimientoInsumo_Extend;
import mx.mc.model.TransaccionPermisos;
import mx.mc.model.Usuario;
import mx.mc.service.SurtimientoService;
import mx.mc.service.TransaccionService;
import mx.mc.service.UsuarioService;
import mx.mc.util.CodigoBarras;
import mx.mc.util.FechaUtil;
import mx.mc.ws.movil.util.Comunes;
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
@Path("recepcionOffline")
public class RecepcionMedicamentoMBMovilOffline {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecepcionMedicamentoMBMovil.class);
    protected static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private String claveMedicamento;
    private String loteMedicamento;
    private String idSurtimientoInsumo;
    private Date caducidadMedicamento;
    private int resul = 0;
    private boolean eliminarCodigo;
    private final ObjectMapper mapperRecepcion = new ObjectMapper();
    private final ObjectNode resultadoRecepcion = mapperRecepcion.createObjectNode();
    private ArrayNode arrayRecepcion = mapperRecepcion.createArrayNode();
    private ArrayNode arraySurte = mapperRecepcion.createArrayNode();

    private Date fechaActual;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TransaccionService transaccionService;

    @Autowired
    private SurtimientoService surtimientoService;

    @PostConstruct
    public void init() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    /**
     * Crea una nueva instancia de la clase
     */
    public RecepcionMedicamentoMBMovilOffline() {
        //No code needed in constructor
    }

    /**
     * Equivalente del método RecepcionMedicamentoMB.consultaPermisosUsuario()
     * El usuario no se toma de la sesion, en su lugar se pasa como parámetro
     *
     * @param usuario POJO del Usuario
     * @param id ID del Usuario
     * @return Lista de TransaccionPermisos
     */
    public List<TransaccionPermisos> consultaPermisosUsuario(Usuario usuario, String id) {
        List<TransaccionPermisos> listPermisos = null;
        try {
            if (id == null || id.isEmpty()) {
                listPermisos = transaccionService.obtenerPermisosPorIdUsuario(usuario.getIdUsuario());
                if (listPermisos != null && !listPermisos.isEmpty()) {
                    usuario.setPermisosList(listPermisos);
                }
            } else {
                listPermisos = transaccionService.obtenerPermisosPorIdUsuario(id);
                if (listPermisos != null && !listPermisos.isEmpty()) {
                    usuario.setPermisosList(listPermisos);
                }
            }
        } catch (Exception ex) {
            LOGGER.error("{}.consultaPermisosUsuario(): {} {}",this.getClass().getCanonicalName(), RESOURCES.getString("ses.obtener.datos"), ex.getMessage());
        }
        return listPermisos;
    }

    /**
     * Relacionado con el método
     * RecepcionMedicamentoMB.consultaPermisosUsuario() Obtiene una
     * representacion de los permisos del usuario para una Transaccion en
     * particular
     *
     * @param usuario POJO del Uuario
     * @param sufijoTransaccion Código de la Transacción
     * @return Objeto con la representacion de los permisos del Usuario
     */
    public PermisoUsuario obtenPermisosUsuario(Usuario usuario, String sufijoTransaccion) {
        LOGGER.debug("{}.obtenPermisosUsuario()", this.getClass().getCanonicalName());
        PermisoUsuario permisosUser = new PermisoUsuario();
        if (usuario.getPermisosList() != null) {
            for (TransaccionPermisos item : usuario.getPermisosList()) {
                if (item.getCodigo().equals(sufijoTransaccion))
                    permisosUser = Comunes.obtenPermiso(item.getAccion(), permisosUser);
            }
        }
        return permisosUser;
    }

    /**
     * Equivalente del método
     * RecepcionMedicamentoMB.tratarCodigoDeBarrasMedicamento() Desglosa el
     * código de barras en sus componentes: Clave, Lote, Fecha de Caducidad
     *
     * @param codigo Cadena con el código de barras
     * @return True en caso de éxito, false en caso de error
     */
    private boolean tratarCodigoDeBarrasMedicamento(String codigo, String idSurtimientoInsumo) {
        boolean resp = Constantes.INACTIVO;
        try {
            CodigoInsumo ci = CodigoBarras.parsearCodigoDeBarras(codigo);
            if (ci != null) {
                claveMedicamento = ci.getClave();
                loteMedicamento = ci.getLote();
                caducidadMedicamento = ci.getFecha();
                this.idSurtimientoInsumo = idSurtimientoInsumo;
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
        JsonNode params = mapperRecepcion.readTree(filtrosJson);
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode resultado = mapper.createObjectNode();
        if (!params.hasNonNull("usuario") || !params.hasNonNull("pass") || !params.hasNonNull("surtimientos")) {
            resultadoRecepcion.put("estatus", "ERROR");
            resultadoRecepcion.put("mensaje", RESOURCES.getString("webservice.err.invalidRequest"));
            return Response.ok(resultadoRecepcion.toString()).build();
        }
        String usuario = params.get("usuario").asText();
        String pass = params.get("pass").asText();
        JsonNode jn = params.get("surtimientos");
        List<Offline_Extend> surtInsumoExtList = mapperRecepcion.readValue(jn.toString(), new TypeReference<List<Offline_Extend>>() {
        });
        eliminarCodigo = false;
        Usuario usuarioParam = new Usuario();
        Usuario usuarioSelected;
        usuarioParam.setNombreUsuario(usuario);
        PermisoUsuario permisosUsuario = new PermisoUsuario();
        try {
            usuarioSelected = this.usuarioService.verificaSiExisteUser(usuarioParam);
        } catch (Exception ex) {
            resultado.put("estatus", "EXCEPCION");
            resultado.put("mensaje", ex.getMessage());
            LOGGER.error("{}.receiveMedicamento(): {}",this.getClass().getCanonicalName() , ex.getMessage());
            return Response.ok(resultado.toString()).build();
        }

        ObjectMapper map = new ObjectMapper();
        ArrayNode arrayRecepcionA = map.createArrayNode();
        if (usuarioSelected != null) {
            if (!CustomWebSecurityConfigurerAdapter.customPasswordEncoder().matches(pass, usuarioSelected.getClaveAcceso())) {
                resultado.put("estatus", "ERROR");
                resultado.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
                return Response.ok(resultado.toString()).build();
            } else {
                List<TransaccionPermisos> permisosList = consultaPermisosUsuario(usuarioSelected, null);
                if (permisosList != null && !permisosList.isEmpty()) {
                    usuarioSelected.setPermisosList(permisosList);
                    permisosUsuario = obtenPermisosUsuario(usuarioSelected, Transaccion_Enum.RECEPMEDICAMENTO.getSufijo());
                } else {
                    resultado.put("estatus", "ERROR");
                    resultado.put("mensaje", RESOURCES.getString("ses.obtener.datos"));
                }
                resul = 0;
                fechaActual = new Date();

                if (permisosUsuario.isPuedeCrear()) {
                    for (int i = 0; i < surtInsumoExtList.size(); i++) {
                        Offline_Extend recep = surtInsumoExtList.get(i);
                        arrayRecepcion = mapper.createArrayNode();
                        for (int j = 0; j < recep.getCodigo().size(); j++) {
                            CantidadOffline_Extended codigoBarras = recep.getCodigo().get(j);
                            int posI = 0;
                            boolean codigo = Constantes.INACTIVO;
                            boolean clave = Constantes.INACTIVO;
                            boolean lote = Constantes.INACTIVO;
                            boolean caducidad = Constantes.INACTIVO;
                            boolean mediActivo = Constantes.INACTIVO;
                            boolean loteActivo = Constantes.INACTIVO;
                            boolean esFechaActual = Constantes.INACTIVO;
                            boolean cantidad = Constantes.INACTIVO;
                            resultado = mapper.createObjectNode();
                            arraySurte = mapper.createArrayNode();
                            if (codigoBarras.getCodigoBarras() != null && permisosUsuario.isPuedeCrear()) {
                                try {
                                    if (tratarCodigoDeBarrasMedicamento(codigoBarras.getCodigoBarras(), codigoBarras.getIdSurtimientoInsumo())) {
                                        codigo = Constantes.ACTIVO;
                                        for (int k = 0; k < recep.getSurtimientoInsumoExtendedList().size(); k++) {
                                            SurtimientoInsumo_Extend surtimientoInsumo = recep.getSurtimientoInsumoExtendedList().get(k);
                                            posI = k;
                                            if (surtimientoInsumo.getClaveInstitucional().equals(claveMedicamento)) {
                                                clave = Constantes.ACTIVO;
                                                if (surtimientoInsumo.isActivo()) {
                                                    mediActivo = Constantes.ACTIVO;
                                                    if (surtimientoInsumo.getSurtimientoEnviadoExtendList() != null) {
                                                        for (int l = 0; l < surtimientoInsumo.getSurtimientoEnviadoExtendList().size(); l++) {
                                                            SurtimientoEnviado_Extend surtimientoEnviado = surtimientoInsumo.getSurtimientoEnviadoExtendList().get(l);
                                                            if (surtimientoEnviado.getLote().equals(loteMedicamento)) {
                                                                lote = Constantes.ACTIVO;
                                                                if (surtimientoEnviado.getActivo() > 0) {
                                                                    loteActivo = Constantes.ACTIVO;
                                                                    if (surtimientoEnviado.getCaducidad().equals(caducidadMedicamento)) {
                                                                        caducidad = Constantes.ACTIVO;
                                                                        if (FechaUtil.isFechaMayorIgualQue(surtimientoEnviado.getCaducidad(), this.fechaActual)) {
                                                                            esFechaActual = Constantes.ACTIVO;
                                                                            for (CantidadOffline cant : recep.getCantidad()) {
                                                                                cantidad = Constantes.ACTIVO;
                                                                                if (cant.getIdSurtimientoInsumo().equals(surtimientoEnviado.getIdSurtimientoInsumo())) {
                                                                                    if (surtimientoEnviado.getCantidadRecibido() == null) {
                                                                                        surtimientoEnviado.setCantidadRecibido(0);
                                                                                    }
                                                                                    resul = surtimientoEnviado.getCantidadRecibido() + cant.getCantidadRecibido();
                                                                                    if (resul <= surtimientoEnviado.getCantidadEnviado()) {
                                                                                        surtimientoEnviado.setCantidadRecibido(resul);
                                                                                        surtimientoInsumo.setCantidadRecepcion(surtimientoInsumo.getCantidadRecepcion() + cant.getCantidadRecibido());
                                                                                        resultado.put("estatus", "OK");
                                                                                        ObjectNode registrosNode = mapper.valueToTree(surtimientoInsumo);
                                                                                        arraySurte.add(registrosNode);
                                                                                        resultado.set("surtimientoInsumoExtendedList", arraySurte);
                                                                                        arrayRecepcion.add(resultado);

                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } catch (IllegalArgumentException ex) {
                                    resultado.put("estatus", "ERROR");
                                    resultado.put("mensaje", "Error al recibir el medicamento: " + ex.getMessage());
                                    LOGGER.error("Error al recibir el medicamento: {}", ex.getMessage());
                                    return Response.ok(resultado.toString()).build();
                                }
                            }
                            if (!codigo) {
                                resultado.put("estatus", "ERROR");
                                resultado.put("mensaje", RESOURCES.getString("recepmedicamento.err.codigoIncorrecto"));
                                if (recep.getSurtimientoInsumoExtendedList().get(posI) != null) {
                                    ObjectNode registrosNode = mapper.valueToTree(codigoBarras);
                                    arraySurte.add(registrosNode);
                                }
                            } else if (codigo && !clave) {
                                resultado.put("estatus", "ERROR");
                                resultado.put("mensaje", RESOURCES.getString("recepmedicamento.err.noencontrado"));
                                if (recep.getSurtimientoInsumoExtendedList().get(posI) != null) {
                                    ObjectNode registrosNode = mapper.valueToTree(recep.getSurtimientoInsumoExtendedList().get(posI));
                                    arraySurte.add(registrosNode);
                                    resultado.set("surtimientoInsumoExtendedList", arraySurte);
                                    arrayRecepcion.add(resultado);
                                }
                            } else if (codigo && clave && !mediActivo) {
                                resultado.put("estatus", "ERROR");
                                resultado.put("mensaje", RESOURCES.getString("recepmedicamento.err.medibloqueado"));
                                if (recep.getSurtimientoInsumoExtendedList().get(posI) != null) {
                                    ObjectNode registrosNode = mapper.valueToTree(recep.getSurtimientoInsumoExtendedList().get(posI));
                                    arraySurte.add(registrosNode);
                                    resultado.set("surtimientoInsumoExtendedList", arraySurte);
                                    arrayRecepcion.add(resultado);
                                }
                            } else if (codigo && clave && mediActivo && !lote) {
                                resultado.put("estatus", "ERROR");
                                resultado.put("mensaje", RESOURCES.getString("recepmedicamento.err.noLote"));
                                if (recep.getSurtimientoInsumoExtendedList().get(posI) != null) {
                                    ObjectNode registrosNode = mapper.valueToTree(recep.getSurtimientoInsumoExtendedList().get(posI));
                                    arraySurte.add(registrosNode);
                                    resultado.set("surtimientoInsumoExtendedList", arraySurte);
                                    arrayRecepcion.add(resultado);
                                }
                            } else if (codigo && clave && mediActivo && lote && !loteActivo) {
                                resultado.put("estatus", "ERROR");
                                resultado.put("mensaje", RESOURCES.getString("recepmedicamento.err.lotebloqueado"));
                                if (recep.getSurtimientoInsumoExtendedList().get(posI) != null) {
                                    ObjectNode registrosNode = mapper.valueToTree(recep.getSurtimientoInsumoExtendedList().get(posI));
                                    arraySurte.add(registrosNode);
                                    resultado.set("surtimientoInsumoExtendedList", arraySurte);
                                    arrayRecepcion.add(resultado);
                                }
                            } else if (codigo && clave && mediActivo && lote && loteActivo && !caducidad) {
                                resultado.put("estatus", "ERROR");
                                resultado.put("mensaje", RESOURCES.getString("recepmedicamento.err.caducInvalida"));
                                if (recep.getSurtimientoInsumoExtendedList().get(posI) != null) {
                                    ObjectNode registrosNode = mapper.valueToTree(recep.getSurtimientoInsumoExtendedList().get(posI));
                                    arraySurte.add(registrosNode);
                                    resultado.set("surtimientoInsumoExtendedList", arraySurte);
                                    arrayRecepcion.add(resultado);
                                }
                            } else if (codigo && clave && mediActivo && lote && loteActivo && caducidad && !esFechaActual) {
                                resultado.put("estatus", "ERROR");
                                resultado.put("mensaje", RESOURCES.getString("recepmedicamento.err.medCaducado"));
                                if (recep.getSurtimientoInsumoExtendedList().get(posI) != null) {
                                    ObjectNode registrosNode = mapper.valueToTree(recep.getSurtimientoInsumoExtendedList().get(posI));
                                    arraySurte.add(registrosNode);
                                    resultado.set("surtimientoInsumoExtendedList", arraySurte);
                                    arrayRecepcion.add(resultado);
                                }
                            } else if (codigo && clave && mediActivo && lote && loteActivo && caducidad && esFechaActual && !cantidad) {
                                resultado.put("estatus", "ERROR");
                                resultado.put("mensaje", RESOURCES.getString("recepmedicamento.err.cantMayor"));
                                ObjectNode registrosNode = mapper.valueToTree(codigoBarras);
                                arraySurte.add(registrosNode);
                                resultado.set("surtimientoInsumoExtendedList", arraySurte);
                                arrayRecepcion.add(resultado);
                            }
                        }
                        arrayRecepcionA.add(arrayRecepcion);
                    }
                } else {
                    resultado.put("estatus", "ERROR");
                    resultado.put("mensaje", RESOURCES.getString("recepmedicamento.err.sinpermisoProces"));
                    return Response.ok(resultado.toString()).build();
                }
            }
        } else {
            resultado.put("estatus", "ERROR");
            resultado.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
            return Response.ok(resultado.toString()).build();
        }

        resultadoRecepcion.set("surtimientos", arrayRecepcionA);
        return Response.ok(resultadoRecepcion.toString()).build();
    }

    /**
     * Equivalente del método
     * RecepcionMedicamentoMB.procesarMedicamentoRecibido() Ejecuta la acción de
     * recepción sobre el Medicamento Surtido
     *
     * @param filtrosJson Cadena JSON con las credenciales del usuario, la lista
     * de Surtimientos y el registro seleccionado
     * @return Respuesta en formato JSON con la lista de Surtim
     * @throws java.io.IOExceptionientos
     */
    @POST
    @Path("procesarMedicamentoRecibido")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response procesarMedicamentoRecibido(String filtrosJson) throws IOException {
        ObjectMapper mappers = new ObjectMapper();
        JsonNode params = mappers.readTree(filtrosJson);
        if (!params.hasNonNull("usuario") || !params.hasNonNull("pass") || !params.hasNonNull("surtimientos")) {
            resultadoRecepcion.put("estatus", "ERROR");
            resultadoRecepcion.put("mensaje", RESOURCES.getString("webservice.err.invalidRequest"));
            return Response.ok(resultadoRecepcion.toString()).build();
        }
        List<Offline_Extend> surtInsumoExtList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(filtrosJson);
        String usuario = params.get("usuario").asText();
        String pass = params.get("pass").asText();

        JSONArray surtimientos = jsonObject.getJSONArray("surtimientos");
        JSONArray surtimientoInsumoExtendedList;
        JSONObject object21;
        for (int i = 0; i < surtimientos.length(); i++) {
            surtimientoInsumoExtendedList = surtimientos.getJSONArray(i);
            for (int j = 0; j < surtimientoInsumoExtendedList.length(); j++) {
                object21 = surtimientoInsumoExtendedList.getJSONObject(j);
                surtInsumoExtList.add(mappers.readValue(object21.toString(), new TypeReference<Offline_Extend>() {
                }));
            }
        }

        Usuario usuarioParam = new Usuario();
        Usuario usuarioSelected;
        usuarioParam.setNombreUsuario(usuario);
        PermisoUsuario permisosUsuario;
        try {
            usuarioSelected = this.usuarioService.verificaSiExisteUser(usuarioParam);
        } catch (Exception ex) {
            resultadoRecepcion.put("estatus", "EXCEPCION");
            resultadoRecepcion.put("mensaje", ex.getMessage());
            LOGGER.error("{}.procesarMedicamentoRecibido(): {}",this.getClass().getCanonicalName() , ex.getMessage());
            return Response.ok(resultadoRecepcion.toString()).build();
        }
        if (usuarioSelected != null) {
            if (!CustomWebSecurityConfigurerAdapter.customPasswordEncoder().matches(pass, usuarioSelected.getClaveAcceso())) {
                resultadoRecepcion.put("estatus", "ERROR");
                resultadoRecepcion.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
                return Response.ok(resultadoRecepcion.toString()).build();
            } else {
                List<TransaccionPermisos> permisosList = consultaPermisosUsuario(usuarioSelected, null);
                if (permisosList != null && !permisosList.isEmpty()) {
                    usuarioSelected.setPermisosList(permisosList);
                    permisosUsuario = obtenPermisosUsuario(usuarioSelected, Transaccion_Enum.RECEPMEDICAMENTO.getSufijo());
                } else {
                    resultadoRecepcion.put("estatus", "ERROR");
                    resultadoRecepcion.put("mensaje", RESOURCES.getString("ses.obtener.datos"));
                    return Response.ok(resultadoRecepcion.toString()).build();

                }
                for (int i = 0; i < surtInsumoExtList.size(); i++) {
                    ObjectMapper mapper = new ObjectMapper();
                    ObjectNode resultado = mapper.createObjectNode();
                    ObjectNode registrosNode;
                    String estatus = surtInsumoExtList.get(i).getEstatus();
                    if (permisosUsuario.isPuedeCrear()) {
                        try {
                            if (estatus.equals("ERROR")) {
                                registrosNode = mapper.valueToTree(surtInsumoExtList.get(i).getSurtimientoInsumoExtendedList().get(0));
                                registrosNode.put("estatus", surtInsumoExtList.get(i).getEstatus());
                                registrosNode.put("mensaje", surtInsumoExtList.get(i).getMensaje());
                                arraySurte.add(registrosNode);
                            } else {
                                resul = surtimientoService.actualizarListaCantidadRecbidaOff(surtInsumoExtList.get(i).getSurtimientoInsumoExtendedList(), surtInsumoExtList.get(i).getViewRecepcionMed(), usuarioSelected.getIdUsuario());
                                if (resul > 0) {
                                    if (resul > 4) {
                                        registrosNode = mapper.valueToTree(surtInsumoExtList.get(i).getSurtimientoInsumoExtendedList().get(0));
                                        registrosNode.put("estatus", "DEVOLUCION");
                                        registrosNode.put("mensaje", RESOURCES.getString("recepmedicamento.info.devolucionGenerada"));
                                        arraySurte.add(registrosNode);
                                    } else {
                                        registrosNode = mapper.valueToTree(surtInsumoExtList.get(i).getSurtimientoInsumoExtendedList().get(0));
                                        registrosNode.put("estatus", surtInsumoExtList.get(i).getEstatus());
                                        registrosNode.put("mensaje", "Recibido");
                                        arraySurte.add(registrosNode);
                                    }
                                } else {
                                    registrosNode = mapper.valueToTree(surtInsumoExtList.get(i).getSurtimientoInsumoExtendedList().get(0));
                                    registrosNode.put("estatus", "ERROR");
                                    registrosNode.put("mensaje", RESOURCES.getString("recepmedicamento.err.actualizar"));
                                    arraySurte.add(registrosNode);
                                }
                            }
                        } catch (Exception ex) {
                            resultado.put("estatus", "ERROR");
                            resultado.put("mensaje", RESOURCES.getString("recepmedicamento.err.exActualizar"));
                            LOGGER.error("Error al actualizar la cantidad recibida: {}", ex.getMessage());
                            return Response.ok(resultado.toString()).build();
                        }
                    } else {
                        resultado.put("estatus", "ERROR");
                        resultado.put("mensaje", RESOURCES.getString("recepmedicamento.err.sinpermisoCrear"));
                        return Response.ok(resultado.toString()).build();
                    }
                }
            }
        } else {
            resultadoRecepcion.put("estatus", "ERROR");
            resultadoRecepcion.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
            return Response.ok(resultadoRecepcion.toString()).build();
        }

        resultadoRecepcion.set("recepcion", arraySurte);
        return Response.ok(resultadoRecepcion.toString()).build();
    }

    public String getIdSurtimientoInsumo() {
        return idSurtimientoInsumo;
    }

    public void setIdSurtimientoInsumo(String idSurtimientoInsumo) {
        this.idSurtimientoInsumo = idSurtimientoInsumo;
    }

    public boolean isEliminarCodigo() {
        return eliminarCodigo;
    }

    public void setEliminarCodigo(boolean eliminarCodigo) {
        this.eliminarCodigo = eliminarCodigo;
    }

    public ArrayNode getArrayRecepcion() {
        return arrayRecepcion;
    }

    public void setArrayRecepcion(ArrayNode arrayRecepcion) {
        this.arrayRecepcion = arrayRecepcion;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }
    
}
