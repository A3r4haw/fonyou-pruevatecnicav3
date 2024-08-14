/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.ws.movil.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.io.Serializable;
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
import mx.mc.enums.EstatusMinistracion_Enum;
import mx.mc.enums.EstatusSurtimiento_Enum;
import mx.mc.enums.TipoMotivo_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.init.CustomWebSecurityConfigurerAdapter;
import mx.mc.model.CodigoInsumo;
import mx.mc.model.MedicamentoOff_Extended;
import mx.mc.model.MovimientoInventario;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.SurtimientoMinistrado;
import mx.mc.model.TransaccionPermisos;
import mx.mc.model.Usuario;
import mx.mc.service.SurtimientoMinistradoService;
import mx.mc.service.TransaccionService;
import mx.mc.service.UsuarioService;
import mx.mc.util.CodigoBarras;
import mx.mc.util.Comunes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 *
 * @author unava
 */
@Path("ministracionOffline")
public class MinistrarMedicamentoMBMovilOffline extends SpringBeanAutowiringSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecepcionMedicamentoMBMovil.class);
    protected static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    Integer ministraPrevDays = 0;
    Integer ministraLaterHours = 0;
    private final ObjectMapper mapperMinistracion = new ObjectMapper();
    private final ObjectNode resultadoMinistracion = mapperMinistracion.createObjectNode();
    private final ArrayNode arrayMinistracion = mapperMinistracion.createArrayNode();

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TransaccionService transaccionService;

    @Autowired
    private SurtimientoMinistradoService surtimientoMinistradoService;

    @PostConstruct
    public void init() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    /**
     * Crea una nueva instancia de la clase
     */
    public MinistrarMedicamentoMBMovilOffline() {
        //No code needed in constructor
    }

    /**
     * Equivalente del método MinistrarMedicamentoMB.consultaPermisoUsuario()
     * El usuario no se toma de la sesion, en su lugar se pasa como parámetro
     *
     * @param usuario POJO del Usuario
     * @return Lista de TransaccionPermisos
     */
    public List<TransaccionPermisos> consultaPermisosUsuario(Usuario usuario) {
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
     * MinistrarMedicamentoMB.consultaPermisoUsuario() Obtiene una
     * representacion de los permisos del usuario para una Transaccion en
     * particular
     *
     * @param usuario POJO del Uuario
     * @param sufijoTransaccion Código de la Transacción
     * @return Objeto con la representacion de los permisos del Usuario
     */
    public PermisoUsuario obtenPermisosUsuario(Usuario usuario, String sufijoTransaccion) {
        LOGGER.debug("{}.obtenPermisoUsuario()", this.getClass().getCanonicalName());
        PermisoUsuario permisosUsuario = new PermisoUsuario();
        if (usuario.getPermisosList() != null) {
            for (TransaccionPermisos permiso : usuario.getPermisosList()) {
                if (permiso.getCodigo().equals(sufijoTransaccion))
                    permisosUsuario = mx.mc.ws.movil.util.Comunes.obtenPermiso(permiso.getAccion(), permisosUsuario);
            }
        }
        return permisosUsuario;
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
        Usuario usuarioParam = new Usuario();
        Usuario usuarioSelected = null;
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode resultado = objectMapper.createObjectNode();
        JsonNode params = objectMapper.readTree(filtrosJson);
        if (!params.hasNonNull("usuario") || !params.hasNonNull("pass") || !params.hasNonNull("ministracion")) {
            resultado.put("estatus", "ERROR");
            resultado.put("mensaje", RESOURCES.getString("webservice.err.invalidRequest"));
            return Response.ok(resultado.toString()).build();
        }
        String usuario = params.get("usuario").asText();
        String pass = params.get("pass").asText();
        JsonNode jn = params.get("ministracion");
        List<ministracionList> listaMedicamentos = objectMapper.readValue(jn.toString(), new TypeReference<List<ministracionList>>() {});
        usuarioParam.setNombreUsuario(usuario);
        PermisoUsuario permisoUsuario;
        try {
            usuarioSelected = this.usuarioService.verificaSiExisteUser(usuarioParam);
        } catch (Exception ex) {
            resultado.put("estatus", "EXCEPCION");
            resultado.put("mensaje", ex.getMessage());
            LOGGER.error("{}.escanearCodigoQR(): {}", this.getClass().getCanonicalName(), ex.getMessage());
        }
        if (usuarioSelected != null) {
            if (!CustomWebSecurityConfigurerAdapter.customPasswordEncoder().matches(pass, usuarioSelected.getClaveAcceso())) {
                resultadoMinistracion.put("estatus", "ERROR");
                resultadoMinistracion.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
                return Response.ok(resultadoMinistracion.toString()).build();
            } else {
                List<TransaccionPermisos> permisosList = consultaPermisosUsuario(usuarioSelected);
                if (permisosList != null && !permisosList.isEmpty()) {
                    usuarioSelected.setPermisosList(permisosList);
                    permisoUsuario = obtenPermisosUsuario(usuarioSelected, Transaccion_Enum.MINISTRMED.getSufijo());
                } else {
                    resultadoMinistracion.put("estatus", "ERROR");
                    resultadoMinistracion.put("mensaje", RESOURCES.getString("ses.obtener.datos"));
                    return Response.ok(resultadoMinistracion.toString()).build();
                }
                if (!permisoUsuario.isPuedeVer()) {
                    resultadoMinistracion.put("estatus", "ERROR");
                    resultadoMinistracion.put("mensaje", RESOURCES.getString("err.transaccion"));
                    return Response.ok(resultadoMinistracion.toString()).build();
                } else if (usuarioSelected.getIdEstructura() == null) {
                    resultadoMinistracion.put("estatus", "ERROR");
                    resultadoMinistracion.put("mensaje", RESOURCES.getString("sur.sin.almacen"));
                    return Response.ok(resultadoMinistracion.toString()).build();
                } else {
                    for (int i = 0; i < listaMedicamentos.size(); i++) {
                        ObjectMapper maps = new ObjectMapper();
                        ObjectNode res = maps.createObjectNode();
                        try {
                            for (String codigoMedicamento : listaMedicamentos.get(i).getCodigoBarras()) {
                                if (codigoMedicamento != null) {
                                    CodigoInsumo ci = CodigoBarras.parsearCodigoDeBarras(codigoMedicamento);
                                    String claveMedicamento = ci.getClave();
                                    String lote = ci.getLote();
                                    int surtidos = 0;
                                    for (MedicamentoOff_Extended item : listaMedicamentos.get(i).getListaMedicamentos()) {
                                        if (item.getEstatusMinistracion().equals(EstatusMinistracion_Enum.PENDIENTE.getValue())
                                                && claveMedicamento.equalsIgnoreCase(item.getClaveInstitucional()) && lote.equalsIgnoreCase(item.getLote())) {
                                            item.setEstatusMinistracion(EstatusMinistracion_Enum.MINISTRADO.getValue());
                                            item.setEstatus("OK");
                                            item.setMessage("MINISTRADO");
                                            for (int o = 0; o < listaMedicamentos.get(i).getCantidad().size(); o++) {
                                                cantidad cant = listaMedicamentos.get(i).getCantidad().get(o);
                                                if (item.getIdMinistrado().equals(cant.getIdMinistrado())) {
                                                    item.setCantidadMinistrada(cant.getCantidadMinistrada());
                                                }
                                            }
                                            break;
                                        }
                                    }
                                    for (MedicamentoOff_Extended item : listaMedicamentos.get(i).getListaMedicamentos()) {
                                        if (item.getEstatus() != null && !item.getEstatus().equals("OK")) {
                                            item.setEstatusMinistracion(EstatusMinistracion_Enum.NO_MINISTRADO.getValue());
                                            item.setEstatus("ERROR");
                                            item.setMessage(RESOURCES.getString("minismedicam.err.noCorresponde"));
                                        }
                                    }
                                    if (surtidos == listaMedicamentos.get(i).getListaMedicamentos().size()) {
                                        res.put("estatus", "ERROR");
                                        res.put("mensaje", RESOURCES.getString("minismedicam.err.sinMedicamen"));
                                    } else {
                                        JsonNode registrosNode = objectMapper.valueToTree(listaMedicamentos.get(i).getListaMedicamentos());
                                        res.set("listaMedicamentos", registrosNode);

                                    }
                                }
                            }
                            arrayMinistracion.add(res);
                        } catch (IllegalArgumentException ex) {
                            LOGGER.error("{}.escanearCodigoQR() : {}", this.getClass().getCanonicalName(), ex.getMessage());
                            resultadoMinistracion.put("estatus", "EXCEPCION");
                            resultadoMinistracion.put("mensaje", ex.getMessage());
                            return Response.ok(resultadoMinistracion.toString()).build();
                        }
                    }
                }
            }
        } else {
            resultadoMinistracion.put("estatus", "ERROR");
            resultadoMinistracion.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
            return Response.ok(resultadoMinistracion.toString()).build();
        }
        resultadoMinistracion.set("ministracion", arrayMinistracion);
        return Response.ok(resultadoMinistracion.toString()).build();
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
        JsonNode params = mapper.readTree(filtrosJson);
        if (!params.hasNonNull("usuario") || !params.hasNonNull("pass") || !params.hasNonNull("ministracion")) {
            resultadoMinistracion.put("estatus", "ERROR");
            resultadoMinistracion.put("mensaje", RESOURCES.getString("webservice.err.invalidRequest"));
            return Response.ok(resultadoMinistracion.toString()).build();
        }
        String usuario = params.get("usuario").asText();
        String pass = params.get("pass").asText();
        JsonNode jn = params.get("ministracion");
        List<ministracionValList> listaMedicamentos = mapper.readValue(jn.toString(), new TypeReference<List<ministracionValList>>() {
        });

        Usuario usuarioParam = new Usuario();
        Usuario usuarioSelected;
        usuarioParam.setNombreUsuario(usuario);
        PermisoUsuario permisosUsuario = new PermisoUsuario();
        try {
            usuarioSelected = this.usuarioService.verificaSiExisteUser(usuarioParam);
        } catch (Exception ex) {
            resultadoMinistracion.put("estatus", "EXCEPCION");
            resultadoMinistracion.put("mensaje", ex.getMessage());
            LOGGER.error("{}.ministrarMedicamentos(): {}",this.getClass().getCanonicalName() , ex.getMessage());
            return Response.ok(resultadoMinistracion.toString()).build();
        }
        if (usuarioSelected != null) {
            if (!CustomWebSecurityConfigurerAdapter.customPasswordEncoder().matches(pass, usuarioSelected.getClaveAcceso())) {
                resultadoMinistracion.put("estatus", "ERROR");
                resultadoMinistracion.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
                return Response.ok(resultadoMinistracion.toString()).build();
            } else {
                List<TransaccionPermisos> permisosList = consultaPermisosUsuario(usuarioSelected);
                if (permisosList != null && !permisosList.isEmpty()) {
                    usuarioSelected.setPermisosList(permisosList);
                    permisosUsuario = obtenPermisosUsuario(usuarioSelected, Transaccion_Enum.MINISTRMED.getSufijo());
                }
                if (!permisosUsuario.isPuedeProcesar()) {
                    resultadoMinistracion.put("estatus", "ERROR");
                    resultadoMinistracion.put("mensaje", RESOURCES.getString("estr.err.permisos"));
                    return Response.ok(resultadoMinistracion.toString()).build();
                } else if (usuarioSelected.getIdEstructura() == null) {
                    resultadoMinistracion.put("estatus", "ERROR");
                    resultadoMinistracion.put("mensaje", RESOURCES.getString("sur.sin.almacen"));
                    return Response.ok(resultadoMinistracion.toString()).build();
                } else {
                    try {
                        for (ministracionValList mlist : listaMedicamentos) {
                            List<SurtimientoMinistrado> listaSurtMin = new ArrayList<>();
                            List<MovimientoInventario> listaMovInv = new ArrayList<>();
                            for (MedicamentoOff_Extended item : mlist.getListaMedicamentos()) {
                                if (item.getEstatusMinistracion().equals(EstatusMinistracion_Enum.MINISTRADO.getValue())) {
                                    SurtimientoMinistrado surtMinist = new SurtimientoMinistrado();
                                    surtMinist.setIdMinistrado(item.getIdMinistrado());
                                    surtMinist.setIdUsuario(usuarioSelected.getIdUsuario());
                                    surtMinist.setFechaMinistrado(new Date());
                                    surtMinist.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.RECIBIDO.getValue());
                                    surtMinist.setIdEstatusMinistracion(EstatusMinistracion_Enum.MINISTRADO.getValue());
                                    surtMinist.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
                                    surtMinist.setUpdateFecha(new Date());
                                    surtMinist.setCantidadMinistrada(item.getCantidadMinistrada());
                                    listaSurtMin.add(surtMinist);

                                    MovimientoInventario movInventario = new MovimientoInventario();
                                    movInventario.setIdMovimientoInventario(Comunes.getUUID());                                    
                                    movInventario.setIdTipoMotivo(TipoMotivo_Enum.SAL_MINIS_POR_SURTIMIENTO_DE_PRESCRIPCION.getMotivoValue());
                                    movInventario.setIdUsuarioMovimiento(usuarioSelected.getIdUsuario());
                                    movInventario.setIdEstrutcuraOrigen(usuarioSelected.getIdEstructura());
                                    movInventario.setIdEstrutcuraDestino(usuarioSelected.getIdEstructura());
                                    movInventario.setFecha(new Date());
                                    movInventario.setIdInventario(item.getIdInventario());
                                    movInventario.setCantidad(item.getCantidadActual());
                                    movInventario.setCantidad(item.getCantidadMinistrada());
                                    movInventario.setFolioDocumento(item.getFolioPrescripcion());
                                    listaMovInv.add(movInventario);
                                }
                            }
                            boolean resp = surtimientoMinistradoService.actualizarSurtiminetoMinistrado(listaSurtMin, listaMovInv);
                            if (resp) {
                                for (MedicamentoOff_Extended item : mlist.getListaMedicamentos()) {
                                    ObjectMapper mapps = new ObjectMapper();
                                    ObjectNode ress = mapps.createObjectNode();
                                    ress.put("estatus", "OK");
                                    ress.put("idMinistrado", item.getIdMinistrado());
                                    ress.put("claveInstitucional", item.getClaveInstitucional());
                                    ress.put("idPaciente", item.getIdPaciente());
                                    arrayMinistracion.add(ress);
                                }
                            } else {
                                for (MedicamentoOff_Extended item : mlist.getListaMedicamentos()) {
                                    ObjectMapper mapps = new ObjectMapper();
                                    ObjectNode ress = mapps.createObjectNode();
                                    ress.put("estatus", "ERROR");
                                    ress.put("mensaje", RESOURCES.getString("err.guardar"));
                                    ress.put("idMinistrado", item.getIdMinistrado());
                                    ress.put("claveInstitucional", item.getClaveInstitucional());
                                    ress.put("idPaciente", item.getIdPaciente());
                                    arrayMinistracion.add(ress);
                                }
                            }
                        }

                    } catch (Exception ex) {
                        resultadoMinistracion.put("estatus", "EXCEPCION");
                        resultadoMinistracion.put("mensaje", ex.getMessage());
                        LOGGER.error("{}.ministrarMedicamentos() : {}", this.getClass().getCanonicalName(), ex.getMessage());
                        return Response.ok(resultadoMinistracion.toString()).build();
                    }
                }
            }
        } else {
            resultadoMinistracion.put("estatus", "ERROR");
            resultadoMinistracion.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
            return Response.ok(resultadoMinistracion.toString()).build();
        }
        resultadoMinistracion.set("ministracion", arrayMinistracion);
        return Response.ok(resultadoMinistracion.toString()).build();
    }

    public static class ministracionList implements Serializable {

        private static final long serialVersionUID = 1L;
        private String estatus;
        private List<MedicamentoOff_Extended> listaMedicamentos;
        private boolean eliminaCodigoBarras;
        private List<cantidad> cantidad;
        @JsonProperty("codigoBarras")
        private List<String> codigoBarras;

        public String getEstatus() {
            return estatus;
        }

        public void setEstatus(String estatus) {
            this.estatus = estatus;
        }

        public List<MedicamentoOff_Extended> getListaMedicamentos() {
            return listaMedicamentos;
        }

        public void setListaMedicamentos(List<MedicamentoOff_Extended> listaMedicamentos) {
            this.listaMedicamentos = listaMedicamentos;
        }

        public boolean isEliminaCodigoBarras() {
            return eliminaCodigoBarras;
        }

        public void setEliminaCodigoBarras(boolean eliminaCodigoBarras) {
            this.eliminaCodigoBarras = eliminaCodigoBarras;
        }

        public List<String> getCodigoBarras() {
            return codigoBarras;
        }

        public void setCodigoBarras(List<String> codigoBarras) {
            this.codigoBarras = codigoBarras;
        }

        public List<cantidad> getCantidad() {
            return cantidad;
        }

        public void setCantidad(List<cantidad> cantidad) {
            this.cantidad = cantidad;
        }
    }

    public static class cantidad implements Serializable {

        private static final long serialVersionUID = 1L;
        private String idMinistrado;
        private int cantidadMinistrada;

        public String getIdMinistrado() {
            return idMinistrado;
        }

        public void setIdMinistrado(String idMinistrado) {
            this.idMinistrado = idMinistrado;
        }

        public int getCantidadMinistrada() {
            return cantidadMinistrada;
        }

        public void setCantidadMinistrada(int cantidadMinistrada) {
            this.cantidadMinistrada = cantidadMinistrada;
        }
    }

    public static class ministracionValList implements Serializable {

        private static final long serialVersionUID = 1L;
        private String estatus;
        private List<MedicamentoOff_Extended> listaMedicamentos;

        public String getEstatus() {
            return estatus;
        }

        public void setEstatus(String estatus) {
            this.estatus = estatus;
        }

        public List<MedicamentoOff_Extended> getListaMedicamentos() {
            return listaMedicamentos;
        }

        public void setListaMedicamentos(List<MedicamentoOff_Extended> listaMedicamentos) {
            this.listaMedicamentos = listaMedicamentos;
        }
    }    
}
