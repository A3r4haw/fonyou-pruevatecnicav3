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
import mx.mc.enums.EstatusDevolucion_Enum;
import mx.mc.enums.EstatusSurtimiento_Enum;
import mx.mc.init.Constantes;
import mx.mc.init.CustomWebSecurityConfigurerAdapter;
import mx.mc.model.ClaveProveedorBarras;
import mx.mc.model.CodigoInsumo;
import mx.mc.model.DevolucionMinistracion;
import mx.mc.model.DevolucionMinistracionDetalleExtended;
import mx.mc.model.DevolucionMinistracionExtended;
import mx.mc.model.Inventario;
import mx.mc.model.InventarioSalida;
import mx.mc.model.MovimientoInventario;
import mx.mc.model.Surtimiento;
import mx.mc.model.SurtimientoEnviado_Extend;
import mx.mc.model.SurtimientoInsumo;
import mx.mc.model.Usuario;
import mx.mc.service.DevolucionMinistracionDetalleService;
import mx.mc.service.DevolucionMinistracionService;
import mx.mc.service.InventarioService;
import mx.mc.service.SurtimientoInsumoService;
import mx.mc.service.SurtimientoService;
import mx.mc.service.UsuarioService;
import mx.mc.util.CodigoBarras;
import mx.mc.util.Comunes;
import mx.mc.util.Mensaje;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 *
 * @author Ulai
 */
@Path("recepcionDevolucion")
public class RecepcionDevolucionMBMovil extends SpringBeanAutowiringSupport {
    private static final Logger LOGGER = LoggerFactory.getLogger(RecepcionDevolucionMBMovil.class);
    protected static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private List<DevolucionMinistracionDetalleExtended> listaDetalleDevolucion;
    private DevolucionMinistracionExtended devolucionMinSelect; 
    private Usuario currentUser;
    private boolean lote;
    private boolean fecha;
    private boolean clave;
    private boolean cant;
    private boolean resCant;

    @Autowired
    private DevolucionMinistracionService devolucionMinistracionService;

    @Autowired
    private DevolucionMinistracionDetalleService devolucionMinistracionDetalleService;

    @Autowired
    private InventarioService inventarioService;

    @Autowired
    private SurtimientoService surtimientoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private SurtimientoInsumoService surtimientoInsumoService;

    @PostConstruct
    public void init() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    public RecepcionDevolucionMBMovil() {
        //No code needed in constructor
    }

    @POST
    @Path("buscarDevolucion")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarDevolucion(String filtrosJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode resNode = mapper.createObjectNode();
        JsonNode params = mapper.readTree(filtrosJson);
        List<DevolucionMinistracionExtended> listaDevoucion;
        String cadenaBusqueda;
        if (!params.hasNonNull("usuario") || !params.hasNonNull("pass") || !params.hasNonNull("tipoPrescripcion") || !params.hasNonNull("cadenaBusqueda") || !params.hasNonNull("registroInicio") || !params.hasNonNull("maxPorPagina")) {
            resNode.put("estatus", "ERROR");
            resNode.put("mensaje", RESOURCES.getString("webservice.err.invalidRequest"));
            return Response.ok(resNode.toString()).build();
        }
        String usuarioString = params.get("usuario").asText();
        String pass = params.get("pass").asText();
        cadenaBusqueda = params.get("cadenaBusqueda").asText();

        Usuario usuarioParam = new Usuario();
        Usuario selected;
        usuarioParam.setNombreUsuario(usuarioString);
        try {
            selected = this.usuarioService.verificaSiExisteUser(usuarioParam);
        } catch (Exception ex) {
            resNode.put("estatus", "EXCEPCION");
            resNode.put("mensaje", ex.getMessage());
            LOGGER.error("{}.obtenerSurtimientos(): {}", this.getClass().getCanonicalName(), ex.getMessage());
            return Response.ok(resNode.toString()).build();
        }

        if (selected != null) {
            if (!CustomWebSecurityConfigurerAdapter.customPasswordEncoder().matches(pass, selected.getClaveAcceso())) {
                resNode.put("estatus", "ERROR");
                resNode.put("mensaje", RESOURCES.getString("devolmedica.warn.sinPermisos"));
                return Response.ok(resNode.toString()).build();
            } else {
                try {
                    DevolucionMinistracionExtended parametros = new DevolucionMinistracionExtended();
                    if (!cadenaBusqueda.equals("")) {
                        parametros.setCadenaBusqueda(cadenaBusqueda);
                    } else {
                        parametros.setCadenaBusqueda(null);
                    }
                    parametros.setIdEstatusDevolucion(EstatusDevolucion_Enum.ENTRANSITO.getValue());
                    listaDevoucion = this.devolucionMinistracionService.obtenerListaDevolucionExtended(parametros);
                } catch (Exception ex) {
                    resNode.put("estatus", "ERROR");
                    resNode.put("mensaje", "Error en el metodo buscarDevolucion :: " + ex.getMessage() + "\n");
                    return Response.ok(resNode.toString()).build();
                }
            }
        } else {
            resNode.put("estatus", "ERROR");
            resNode.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
            return Response.ok(resNode.toString()).build();
        }
        resNode.put("estatus", "OK");
        resNode.put("totalRegistros", listaDevoucion.size());
        ArrayNode registrosNode = mapper.valueToTree(listaDevoucion);
        resNode.set("registros", registrosNode);
        return Response.ok(resNode.toString()).build();
    }

    @POST
    @Path("mostrarDetalleDevolucion")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response mostrarDetalleDevolucion(String filtrosJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode resDetalle = mapper.createObjectNode();
        JsonNode params = mapper.readTree(filtrosJson);
        List<SurtimientoEnviado_Extend> surtInsumoExtList;
        if (!params.hasNonNull("usuario") || !params.hasNonNull("pass") || !params.hasNonNull("devolucionMinSelect")) {
            resDetalle.put("estatus", "ERROR");
            resDetalle.put("mensaje", RESOURCES.getString("webservice.err.invalidRequest"));
            return Response.ok(resDetalle.toString()).build();
        }
        String usuario = params.get("usuario").asText();
        String pass = params.get("pass").asText();
        JsonNode jn = params.get("devolucionMinSelect");
        devolucionMinSelect = mapper.readValue(jn.toString(), new TypeReference<DevolucionMinistracionExtended>() {});
        Usuario usuarioParam = new Usuario();
        Usuario selected;
        usuarioParam.setNombreUsuario(usuario);
        try {
            selected = this.usuarioService.verificaSiExisteUser(usuarioParam);
        } catch (Exception ex) {
            resDetalle.put("estatus", "EXCEPCION");
            resDetalle.put("mensaje", ex.getMessage());
            LOGGER.error("{}.obtenerSurtimientos(): {}",this.getClass().getCanonicalName() , ex.getMessage());
            return Response.ok(resDetalle.toString()).build();
        }

        if (selected != null) {
            if (!CustomWebSecurityConfigurerAdapter.customPasswordEncoder().matches(pass, selected.getClaveAcceso())) {
                resDetalle.put("estatus", "ERROR");
                resDetalle.put("mensaje", RESOURCES.getString("devolmedica.warn.sinPermisos"));
                return Response.ok(resDetalle.toString()).build();
            } else {
                try {
                    DevolucionMinistracionDetalleExtended parametros = new DevolucionMinistracionDetalleExtended();
                    parametros.setIdDevolucionMinistracion(this.devolucionMinSelect.getIdDevolucionMinistracion());
                    parametros.setIdEstatusDevolucion(EstatusDevolucion_Enum.ENTRANSITO.getValue());
                    this.listaDetalleDevolucion = devolucionMinistracionDetalleService.obtenerListaDetalleExtended(parametros, this.devolucionMinSelect.getIdSurtimiento());
                    surtInsumoExtList = surtimientoService.obtenerByIdSurtimientoEnviadoDevMedi(devolucionMinSelect.getIdSurtimiento());

                    for (int i = 0; i < listaDetalleDevolucion.size(); i++) {
                        DevolucionMinistracionDetalleExtended surteMinDetailExt = listaDetalleDevolucion.get(i);
                        List<SurtimientoEnviado_Extend> auxlist = new ArrayList<>();
                        for (int j = 0; j < surtInsumoExtList.size(); j++) {
                            SurtimientoEnviado_Extend surteIext = surtInsumoExtList.get(j);
                            if (surteMinDetailExt.getIdSurtimientoInsumo().equals(surteIext.getIdSurtimientoInsumo())) {
                                auxlist.add(surteIext);
                            }
                        }
                        surteMinDetailExt.setSurtimientoEnviadoExtList(auxlist);
                    }

                } catch (Exception ex) {
                    LOGGER.error("Error en el metodo mostrarDetalleDevolucion :: {}", ex.getMessage());
                }
            }
        } else {
            resDetalle.put("estatus", "ERROR");
            resDetalle.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
            return Response.ok(resDetalle.toString()).build();
        }
        if (!listaDetalleDevolucion.isEmpty()) {
            resDetalle.put("estatus", "OK");
            ArrayNode registrosNode = mapper.valueToTree(this.listaDetalleDevolucion);
            resDetalle.set("registros", registrosNode);
        } else {
            resDetalle.put("estatus", "EXCEPCION");
            resDetalle.put("mensaje", "No hay medicamentos por mostrar");
        }

        return Response.ok(resDetalle.toString()).build();
    }

    @POST
    @Path("devolverMedicamentoPorCodigo")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response devolverMedicamentoPorCodigo(String filtrosJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode resultadoObj = mapper.createObjectNode();
        JsonNode params = mapper.readTree(filtrosJson);
        String codigoBarras;
        if (!params.hasNonNull("usuario") || !params.hasNonNull("pass") || !params.hasNonNull("cantidad") || !params.hasNonNull("eliminaCodigoBarras") || !params.hasNonNull("codigoBarras") || !params.hasNonNull("listaDetalleDevolucion")) {
            resultadoObj.put("estatus", "ERROR");
            resultadoObj.put("mensaje", RESOURCES.getString("webservice.err.invalidRequest"));
            return Response.ok(resultadoObj.toString()).build();
        }
        boolean res = false;
        String usuario = params.get("usuario").asText();
        String pass = params.get("pass").asText();
        int xcantidad = params.get("cantidad").asInt();
        boolean eliminaCodigoBarras = params.get("eliminaCodigoBarras").asBoolean();
        codigoBarras = params.get("codigoBarras").asText();
        JsonNode jn = params.get("listaDetalleDevolucion");
        this.listaDetalleDevolucion = mapper.readValue(jn.toString(), new TypeReference<List<DevolucionMinistracionDetalleExtended>>() {});
        Usuario usuarioParam = new Usuario();
        Usuario selected;
        usuarioParam.setNombreUsuario(usuario);
        try {
            selected = this.usuarioService.verificaSiExisteUser(usuarioParam);
        } catch (Exception ex) {
            resultadoObj.put("estatus", "EXCEPCION");
            resultadoObj.put("mensaje", ex.getMessage());
            LOGGER.error("{}.obtenerSurtimientos(): {}",this.getClass().getCanonicalName() , ex.getMessage());
            return Response.ok(resultadoObj.toString()).build();
        }

        if (selected != null) {
            if (!CustomWebSecurityConfigurerAdapter.customPasswordEncoder().matches(pass, selected.getClaveAcceso())) {
                resultadoObj.put("estatus", "ERROR");
                resultadoObj.put("mensaje", RESOURCES.getString("devolmedica.warn.sinPermisos"));
                return Response.ok(resultadoObj.toString()).build();
            } else {
                DevolucionMinistracionDetalleExtended codigoBarrasSeparado = tratarCodigoDeBarras(codigoBarras);
                if (codigoBarrasSeparado == null) {
                    resultadoObj.put("estatus", "ERROR");
                    resultadoObj.put("mensaje", RESOURCES.getString("devomedicamento.err.novalido"));
                    return Response.ok(resultadoObj.toString()).build();
                }
                if (xcantidad == 0) {
                    xcantidad = 1;
                }
                exit:
                for (DevolucionMinistracionDetalleExtended item : this.listaDetalleDevolucion) {
                    for (int i = 0; i < item.getSurtimientoEnviadoExtList().size(); i++) {
                        SurtimientoEnviado_Extend surteEnv = item.getSurtimientoEnviadoExtList().get(i);
                        if (surteEnv.getClave().equals(codigoBarrasSeparado.getClaveInstitucional())) {
                            clave = true;
                            if (surteEnv.getCaducidad().equals(codigoBarrasSeparado.getFechaCaducidad())) {
                                fecha = true;
                                if (surteEnv.getLote().equals(codigoBarrasSeparado.getLote())) {
                                    lote = true;
                                    if (!eliminaCodigoBarras) {
                                        if (surteEnv.getCantidad() == null) {
                                            surteEnv.setCantidad(0);
                                        }
                                        if ((surteEnv.getCantidad() + xcantidad) <= surteEnv.getCantidadDevolver()) {
                                            surteEnv.setCantidad(surteEnv.getCantidad() + xcantidad);
                                            item.setCantidadRecibida(item.getCantidadRecibida() + xcantidad);
                                            cant = true;
                                            resCant = false;
                                            res = true;
                                            break exit;
                                        }
                                    } else {
                                        if (surteEnv.getCantidad() == null) {
                                            resultadoObj.put("estatus", "ERROR");
                                            resultadoObj.put("mensaje", "No se pueden devolver medicamentos iguales a cero");
                                            return Response.ok(resultadoObj.toString()).build();
                                        }
                                        resCant = true;
                                        if (surteEnv.getCantidad() == null) {
                                            surteEnv.setCantidad(0);
                                        }
                                        if ((surteEnv.getCantidad() - xcantidad) >= 0) {
                                            surteEnv.setCantidad(surteEnv.getCantidad() - xcantidad);
                                            item.setCantidadRecibida(item.getCantidadRecibida() - xcantidad);
                                            cant = true;
                                            resCant = false;
                                            res = true;
                                            break exit;
                                        }
                                        if ((surteEnv.getCantidad() - xcantidad) < 0) {
                                            surteEnv.setCantidad(0);
                                            item.setCantidadRecibida(0);
                                            cant = true;
                                            resCant = false;
                                            res = true;
                                            break exit;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (!clave) {
                        resultadoObj.put("estatus", "ERROR");
                        resultadoObj.put("mensaje", "El medicamento no se encontró en el surtimiento");
                        break;
                    }
                    if (!fecha) {
                        resultadoObj.put("estatus", "ERROR");
                        resultadoObj.put("mensaje", "La caducidad no es correcta para le medicamento " + item.getClaveInstitucional());
                        break;
                    }
                    if (!lote) {
                        resultadoObj.put("estatus", "ERROR");
                        resultadoObj.put("mensaje", "El lote no es correcto para le medicamento " + item.getClaveInstitucional());
                        break;
                    }
                    if (!cant) {
                        resultadoObj.put("estatus", "ERROR");
                        resultadoObj.put("mensaje", "No puede sobrepasar la cantidad a devolver para el medicamento " + item.getClaveInstitucional());
                        break;
                    }
                    if (resCant) {
                        resultadoObj.put("estatus", "ERROR");
                        resultadoObj.put("mensaje", "No se pueden devolver medicamentos iguales a cero");
                        break;
                    }
                }
            }

        } else {
            resultadoObj.put("estatus", "ERROR");
            resultadoObj.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
            return Response.ok(resultadoObj.toString()).build();
        }
        if (res) {
            resultadoObj.put("estatus", "OK");
            ArrayNode registrosNode = mapper.valueToTree(this.listaDetalleDevolucion);
            resultadoObj.set("registros", registrosNode);
        }
        return Response.ok(resultadoObj.toString()).build();
    }

    /**
     * Metodo utilizado para convertir el codigo de barras en un objeto de tipo
     * ReabastoEnviadoExtended
     *
     * @param codigo String
     * @return ReabastoEnviadoExtended
     */
    private DevolucionMinistracionDetalleExtended tratarCodigoDeBarras(String codigo) {
        DevolucionMinistracionDetalleExtended medicamentoDev = new DevolucionMinistracionDetalleExtended();
        try {
            CodigoInsumo ci = CodigoBarras.parsearCodigoDeBarras(codigo);
            if (ci != null) {
                medicamentoDev.setClaveInstitucional(ci.getClave());
                medicamentoDev.setLote(ci.getLote());
                medicamentoDev.setFechaCaducidad(ci.getFecha());
            } else {
                ClaveProveedorBarras clavee = CodigoBarras.buscaClaveSKU(codigo);
                if (clavee != null) {
                    medicamentoDev.setClaveInstitucional(clavee.getClaveInstitucional());
                    medicamentoDev.setLote(clavee.getClaveProveedor());
                    medicamentoDev.setFechaCaducidad(Mensaje.generaCaducidadSKU(clavee.getCodigoBarras()));
                } else {
                    return null;
                }
            }
            return medicamentoDev;
        } catch (Exception e) {
            LOGGER.error("Error en el metodo tratarCodigoDeBarras :: {}", e.getMessage());
        }
        return medicamentoDev;
    }

    @POST
    @Path("recibirDevolucion")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response recibirDevolucion(String filtrosJson) throws Exception {
        boolean resp;
        List<MovimientoInventario> listaMovInv = new ArrayList<>();
        DevolucionMinistracion devMinistracion = new DevolucionMinistracion();
        Surtimiento surtimiento = null;
        boolean surtCheck = false;
        InventarioSalida inventarioSalida;
        List<Inventario> inventarioLista = new ArrayList<>();
        Inventario inventario;
        List<surtCant> cantidadSolicitadaList = new ArrayList<>();
        List<InventarioSalida> inventarioSalidaList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode resultado = mapper.createObjectNode();
        JsonNode params = mapper.readTree(filtrosJson);
        if (!params.hasNonNull("usuario") || !params.hasNonNull("pass") || !params.hasNonNull("devolucionMinSelect") || !params.hasNonNull("listaDetalleDevolucion")) {
            resultado.put("estatus", "ERROR");
            resultado.put("mensaje", RESOURCES.getString("webservice.err.invalidRequest"));
            return Response.ok(resultado.toString()).build();
        }
        String usuario = params.get("usuario").asText();
        JsonNode jn = params.get("devolucionMinSelect");
        this.devolucionMinSelect = mapper.readValue(jn.toString(), DevolucionMinistracionExtended.class);
        jn = params.get("listaDetalleDevolucion");
        this.listaDetalleDevolucion = mapper.readValue(jn.toString(), new TypeReference<List<DevolucionMinistracionDetalleExtended>>() {});
        Usuario usuarioParam = new Usuario();
        usuarioParam.setNombreUsuario(usuario);
        try {
            currentUser = this.usuarioService.verificaSiExisteUser(usuarioParam);
        } catch (Exception ex) {
            resultado.put("estatus", "EXCEPCION");
            resultado.put("mensaje", ex.getMessage());
            LOGGER.error("{}.obtenerSurtimientos(): {}",this.getClass().getCanonicalName() , ex.getMessage());
            return Response.ok(resultado.toString()).build();
        }

        if (currentUser != null) {
            List<String> idSurtimientoInsumoList = new ArrayList<>();
            for (DevolucionMinistracionDetalleExtended item : this.listaDetalleDevolucion) {
                for (SurtimientoEnviado_Extend surte : item.getSurtimientoEnviadoExtList()) {
                    MovimientoInventario movInvent = new MovimientoInventario();
                    movInvent.setIdMovimientoInventario(Comunes.getUUID());
                    movInvent.setIdTipoMotivo(surte.getTipoDevolucion());
                    movInvent.setFecha(new Date());
                    movInvent.setIdUsuarioMovimiento(currentUser.getIdUsuario());
                    movInvent.setIdEstrutcuraOrigen(this.devolucionMinSelect.getIdAlmacenOrigen());
                    movInvent.setIdEstrutcuraDestino(this.devolucionMinSelect.getIdAlmacenDestino());
                    movInvent.setIdInventario(surte.getIdInventarioSurtido());
                    movInvent.setCantidad(surte.getCantidadDevolver());
                    movInvent.setFolioDocumento(this.devolucionMinSelect.getFolio());
                    listaMovInv.add(movInvent);

                    if (surte.getTipoDevolucion() == 6 || surte.getTipoDevolucion() == 7 || surte.getTipoDevolucion() == 10 || surte.getTipoDevolucion() == 14) {
                        inventario = new Inventario();
                        inventario.setIdInventario(surte.getIdInventarioSurtido());
                        inventario.setCantidadActual(surte.getCantidadDevolver());
                        inventario.setUpdateFecha(new Date());
                        inventario.setUpdateIdUsuario(currentUser.getIdUsuario());
                        inventarioLista.add(inventario);

                        if (surte.isMerma()) {
                            inventarioSalida = new InventarioSalida();
                            inventarioSalida.setIdInventarioSalida(Comunes.getUUID());
                            inventarioSalida.setIdInventario(surte.getIdInventarioSurtido());
                            inventarioSalida.setIdEstructura(this.devolucionMinSelect.getIdAlmacenDestino());
                            inventarioSalida.setCantidad(surte.getCantidadDevolver());
                            inventarioSalida.setInsertFecha(new Date());
                            inventarioSalida.setInsertIdUsuario(currentUser.getIdUsuario());
                            inventarioSalida.setCostoUnidosis(1.0);
                            inventarioSalida.setIdTipoMotivo(surte.getTipoDevolucion());
                            inventarioSalidaList.add(inventarioSalida);
                        } else {
                            inventario = new Inventario();
                            inventario.setIdInventario(surte.getIdInventarioSurtido());
                            inventario.setUpdateFecha(new Date());
                            inventario.setUpdateIdUsuario(currentUser.getIdUsuario());
                            inventario.setCantidadActual(surte.getCantidadDevolver());
                            inventarioLista.add(inventario);
                        }
                    }
                    if (surte.getTipoDevolucion() == 8 || surte.getTipoDevolucion() == 9 || surte.getTipoDevolucion() == 11 || surte.getTipoDevolucion() == 12 || surte.getTipoDevolucion() == 13) {
                        if (!surtCheck && surtimiento == null) {
                            surtimiento = new Surtimiento();
                            surtimiento.setIdSurtimiento(Comunes.getUUID());
                            surtimiento.setIdEstructuraAlmacen(this.devolucionMinSelect.getIdAlmacenOrigen());
                            surtimiento.setIdPrescripcion(this.devolucionMinSelect.getIdPrescripcion());
                            surtimiento.setFechaProgramada(new Date());
                            //surtimiento.setFolio(Comunes.getUUID().substring(1, 8));
                            surtimiento.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                            surtimiento.setInsertFecha(new Date());
                            surtimiento.setInsertIdUsuario(currentUser.getIdUsuario());
                            surtCheck = true;
                        }
                        if (cantidadSolicitadaList.isEmpty()) {
                            idSurtimientoInsumoList.add(surte.getIdSurtimientoInsumo());
                            cantidadSolicitadaList.add(new surtCant(surte.getIdSurtimientoInsumo(), surte.getCantidadDevolver()));
                        } else {
                            for (int i = 0; i < cantidadSolicitadaList.size(); i++) {
                                if (!cantidadSolicitadaList.get(i).getId().equals(surte.getIdSurtimientoInsumo())) {
                                    idSurtimientoInsumoList.add(surte.getIdSurtimientoInsumo());
                                    cantidadSolicitadaList.add(new surtCant(surte.getIdSurtimientoInsumo(), surte.getCantidadDevolver()));
                                } else {
                                    cantidadSolicitadaList.get(i).setCant(cantidadSolicitadaList.get(i).getCant() + surte.getCantidadDevolver());
                                }
                            }
                        }

                        if (surte.isMerma()) {
                            inventarioSalida = new InventarioSalida();
                            inventarioSalida.setIdInventarioSalida(Comunes.getUUID());
                            inventarioSalida.setIdInventario(surte.getIdInventarioSurtido());
                            inventarioSalida.setIdEstructura(this.devolucionMinSelect.getIdAlmacenDestino());
                            inventarioSalida.setCantidad(surte.getCantidadDevolver());
                            inventarioSalida.setInsertIdUsuario(currentUser.getIdUsuario());
                            inventarioSalida.setCostoUnidosis(1.0);
                            inventarioSalida.setIdTipoMotivo(surte.getTipoDevolucion());
                            inventarioSalida.setInsertFecha(new Date());
                            inventarioSalidaList.add(inventarioSalida);
                        } else {
                            inventario = new Inventario();
                            inventario.setIdInventario(surte.getIdInventarioSurtido());
                            inventario.setCantidadActual(surte.getCantidadDevolver());
                            inventario.setUpdateIdUsuario(currentUser.getIdUsuario());
                            inventario.setUpdateFecha(new Date());
                            inventarioLista.add(inventario);
                        }
                    }
                }
            }

            List<SurtimientoInsumo> listSurtimeinto = new ArrayList<>();
            if (surtimiento != null && !idSurtimientoInsumoList.isEmpty()) {
                List<SurtimientoInsumo> sl = surtimientoInsumoService.obtenerListaSurtiminetoInsumo(idSurtimientoInsumoList);
                listSurtimeinto.addAll(sl);
                for (int i = 0; i < listSurtimeinto.size(); i++) {
                    SurtimientoInsumo surtInsumo = listSurtimeinto.get(i);
                    for (int j = 0; j < listaDetalleDevolucion.size(); j++) {
                        DevolucionMinistracionDetalleExtended dev = listaDetalleDevolucion.get(j);
                        if (surtInsumo.getIdSurtimientoInsumo().equals(dev.getIdSurtimientoInsumo())) {
                            surtInsumo.setIdSurtimiento(surtimiento.getIdSurtimiento());
                            surtInsumo.setIdSurtimientoInsumo(Comunes.getUUID());
                            surtInsumo.setFechaProgramada(new Date());
                            surtInsumo.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                            surtInsumo.setInsertFecha(new Date());
                            surtInsumo.setCantidadEnviada(0);
                            surtInsumo.setCantidadRecepcion(0);
                            surtInsumo.setUpdateFecha(new Date());
                            surtInsumo.setFechaEnviada(new Date());
                            surtInsumo.setFechaRecepcion(new Date());
                            surtInsumo.setInsertIdUsuario(currentUser.getIdUsuario());
                        }
                        for (int k = 0; k < cantidadSolicitadaList.size(); k++) {
                            if (dev.getIdSurtimientoInsumo().equals(cantidadSolicitadaList.get(k).getId())) {
                                surtInsumo.setCantidadSolicitada(cantidadSolicitadaList.get(k).getCant());
                            }
                        }
                    }

                }

            }

            if (surtimiento != null && !listSurtimeinto.isEmpty()) {
                surtimiento.setDetalle(listSurtimeinto);
            }
            devMinistracion.setIdDevolucionMinistracion(this.devolucionMinSelect.getIdDevolucionMinistracion());
            devMinistracion.setIdEstatusDevolucion(EstatusDevolucion_Enum.RECIBIDA.getValue());
            devMinistracion.setUpdateFecha(new Date());
            devMinistracion.setUpdateIdUsuario(currentUser.getIdUsuario());
            try {
                resp = inventarioService.recivirMedicamentosDevolucion(inventarioLista, inventarioSalidaList, surtimiento, listaMovInv, devMinistracion);
            } catch (Exception ex) {
                resultado.put("estatus", "ERROR");
                resultado.put("mensaje", "Error en el metodo tratarCodigoDeBarras :: " + ex.getMessage());
                return Response.ok(resultado.toString()).build();
            }

            if (resp) {
                resultado.put("estatus", "OK");
                resultado.put("mensaje", RESOURCES.getString("devomedicamento.inforecibieron"));
                return Response.ok(resultado.toString()).build();
            } else {
                resultado.put("estatus", "ERROR");
                resultado.put("mensaje", RESOURCES.getString("devomedicamento.err.devolucion"));
                return Response.ok(resultado.toString()).build();
            }
        } else {
            resultado.put("estatus", "ERROR");
            resultado.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
            return Response.ok(resultado.toString()).build();
        }
    }

    public class surtCant {

        private String id;
        private Integer cant;

        public surtCant(String id, Integer cant) {
            this.id = id;
            this.cant = cant;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Integer getCant() {
            return cant;
        }

        public void setCant(Integer cant) {
            this.cant = cant;
        }
    }
}
