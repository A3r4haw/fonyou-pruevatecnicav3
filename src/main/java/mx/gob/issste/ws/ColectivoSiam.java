package mx.gob.issste.ws;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import mx.gob.issste.ws.model.DerechoHabienteSiam;
import mx.gob.issste.ws.model.InsumoRecetaSiam;
import mx.gob.issste.ws.model.InsumoSIAM;
import mx.gob.issste.ws.model.MedicoSiam;
import mx.gob.issste.ws.model.RecetaSiam;
import mx.mc.service.RecetaSiamService;
import mx.mc.enums.EstatusGabinete_Enum;
import mx.mc.enums.EstatusPrescripcion_Enum;
import mx.mc.enums.EstatusReabasto_Enum;
import mx.mc.enums.TipoOrigen_Enum;
import mx.mc.init.Constantes;
import mx.mc.init.CustomWebSecurityConfigurerAdapter;
import mx.mc.model.BitacoraMensaje;
import mx.mc.model.Config;
import mx.mc.model.DetalleInsumoSiam;
import mx.mc.model.Estructura;
import mx.mc.model.FolioAlternativoFolioMus;
import mx.mc.model.Inventario;
import mx.mc.model.Medicamento;
import mx.mc.model.Paciente;
import mx.mc.model.PacienteServicio;
import mx.mc.model.Prescripcion;
import mx.mc.model.PrescripcionInsumo;
import mx.mc.model.ReabastoEnviado;
import mx.mc.model.ReabastoExtended;
import mx.mc.model.ReabastoInsumoExtended;
import mx.mc.model.Surtimiento;
import mx.mc.model.SurtimientoInsumo;
import mx.mc.model.Usuario;
import mx.mc.model.Visita;
import mx.mc.service.BitacoraMensajeService;
import mx.mc.service.ConfigService;
import mx.mc.service.ConfigServiceImpl;
import mx.mc.service.DetalleInsumoSiamService;
import mx.mc.service.EstructuraService;
import mx.mc.service.FolioAlternativoFolioMusService;
import mx.mc.service.InventarioService;
import mx.mc.service.MedicamentoService;
import mx.mc.service.PacienteService;
import mx.mc.service.PacienteServicioService;
import mx.mc.service.PrescripcionInsumoService;
import mx.mc.service.PrescripcionService;
import mx.mc.service.ReabastoEnviadoService;
import mx.mc.service.ReabastoEnviadoServiceImpl;
import mx.mc.service.ReabastoInsumoService;
import mx.mc.service.ReabastoInsumoServiceImpl;
import mx.mc.service.ReabastoService;
import mx.mc.service.ReabastoServiceImpl;
import mx.mc.service.SurtimientoService;
import mx.mc.service.UsuarioService;
import mx.mc.service.UsuarioServiceImpl;
import mx.mc.service.VisitaService;
import mx.mc.util.Comunes;
import mx.mc.util.FechaUtil;
import mx.mc.util.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.context.support.WebApplicationContextUtils;

@Path("siam")
public class ColectivoSiam {
    private static final Logger LOGGER = LoggerFactory.getLogger(ColectivoSiam.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private List<Config> configList;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private ReabastoService reabastoService;
    
    @Autowired
    private ReabastoInsumoService reabastoInsumoService;
    
    @Autowired
    private ReabastoEnviadoService reabastoEnviadoService;

    @Autowired
    private DetalleInsumoSiamService detalleInsumoSiamService;
    
    @Autowired
    private ConfigService configService;
    
    @Autowired
    private InventarioService inventarioService;
    
    @Autowired
    private FolioAlternativoFolioMusService folioAlternativoFolioMusService;
    
    @Autowired
    private BitacoraMensajeService bitacoraService;
    private BitacoraMensaje bitacora;        
    
    @Autowired
    private RecetaSiamService recetaSiamService;
    
    
    
    @PostConstruct
    public void init() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
            
    // <editor-fold defaultstate="collapsed" desc="Methods" > 
    public ColectivoSiam() {
        //No code needed in constructor
    }
    
    public ColectivoSiam(ServletContext servletContext) {
        ApplicationContext springContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
        AutowireCapableBeanFactory factory = springContext.getAutowireCapableBeanFactory();
        if (usuarioService == null) {
            usuarioService = factory.createBean(UsuarioServiceImpl.class);
        }
        if (reabastoService == null) {
            reabastoService = factory.createBean(ReabastoServiceImpl.class);
        }
        if (reabastoInsumoService == null) {
            reabastoInsumoService = factory.createBean(ReabastoInsumoServiceImpl.class);
        }
        if (reabastoEnviadoService == null) {
            reabastoEnviadoService = factory.createBean(ReabastoEnviadoServiceImpl.class);
        }
        if (configService == null) {
            configService = factory.createBean(ConfigServiceImpl.class);
        }
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
                return Response.status(Response.Status.BAD_REQUEST).entity(RESOURCES.getString("siam.ws.login.err.datosRequeridos")).build();
            }
            String usuario = params.get("usuario").asText();
            String password = params.get("password").asText();
            Usuario usuarioSel = obtenerUsuario(usuario);
            if (usuarioSel == null || !CustomWebSecurityConfigurerAdapter.customPasswordEncoder().matches(password, usuarioSel.getClaveAcceso())) {
                return Response.status(Response.Status.UNAUTHORIZED).entity(RESOURCES.getString("siam.ws.login.err.datosInvalidos")).build();
            }
            Token tok = new Token(usuario);
            resultado.put("token", tok.getValue());
        }
        catch (IOException ex) {
            LOGGER.error("{}.login(): {}", this.getClass().getCanonicalName(), ex.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.ok(resultado.toString()).build();
    }
    
    private ReabastoExtended obtenerReabasto(String folioAlternativo) {
        ReabastoExtended reabastoExtended;
        try {
            reabastoExtended = reabastoService.obtenerReabastoByFolioAlternativo(folioAlternativo);
        }
        catch (Exception ex) {
            reabastoExtended = null;
            LOGGER.error("{}.obtenerReabasto(): {}", this.getClass().getCanonicalName(), ex.getMessage());
        }
        return reabastoExtended;
    }
    
    private List<ReabastoInsumoExtended> obtenerDetalleInsumos(ReabastoExtended reabasto) {
        List<ReabastoInsumoExtended> listaReabastoInsumo = new ArrayList<>();
        try {
            List<Integer> listEstatusReabasto = new ArrayList<>();
            listEstatusReabasto.add(EstatusReabasto_Enum.SOLICITADA.getValue());
            listEstatusReabasto.add(EstatusReabasto_Enum.ENTRANSITO.getValue());
            listaReabastoInsumo = reabastoInsumoService.obtenerReabastoInsumoProveedorFarmacia(reabasto.getIdReabasto(), null, listEstatusReabasto);
        } catch (Exception ex) {
            LOGGER.error("{}.obtenerDetalleInsumos(): {}", this.getClass().getCanonicalName(), ex.getMessage());
        }
        return listaReabastoInsumo;
    }
    
    private List<ReabastoInsumoExtended> obtenerDetalleInsumoByFolioAlternativo(String folioAlternativo){
        List<ReabastoInsumoExtended> listDetalle = new ArrayList<>();
        try {
            listDetalle = reabastoInsumoService.detalleInsumoByFolioAlternativo(folioAlternativo);
        } catch (Exception ex) {
            LOGGER.error("{}.obtenerDetalleInsumoByFolioAlternativo(): {}", this.getClass().getCanonicalName(), ex.getMessage());
        }
        return listDetalle;
    }
    
    private DetalleInsumoSiam obtenerDetalleInsumoSiam(String folioAlternativo, String idInsumo) {
        DetalleInsumoSiam detalleInsumoSiam = null;
        try {
            detalleInsumoSiam = detalleInsumoSiamService.obtenerDetalleSIAM(folioAlternativo, idInsumo);
        } catch (Exception ex) {
            LOGGER.error("{}.obtenerDetalleInsumoSiam(): {}", this.getClass().getCanonicalName(), ex.getMessage());
        }
        return detalleInsumoSiam;
    }
    
    private ReabastoInsumoExtended buscarReabastoInsumo(List<ReabastoInsumoExtended> listaReabastoInsumo, InsumoSIAM insumoSIAM) {
        ReabastoInsumoExtended insumo = null;
        if (insumoSIAM.getClaveInsumo() != null && !insumoSIAM.getClaveInsumo().isEmpty()) {
            for (ReabastoInsumoExtended insumoTemp : listaReabastoInsumo) {
                 if (insumoTemp.getClaveInstitucional().equals(insumoSIAM.getClaveInsumo()) || 
                         insumoSIAM.getClaveInsumo().replace(".", "").equals(insumoTemp.getClaveInstitucional())) {
                     insumo = insumoTemp;
                     break;
                 }
            }
        }
        return insumo;
    }
    
    private String convierteClave(String claveInsumo) {
        String temp = "";
        if (!claveInsumo.contains(".")) {
            if (claveInsumo.length() >= 3)
                temp = claveInsumo.substring(0, 3);
            if (claveInsumo.length() >= 6)
                temp += "." + claveInsumo.substring(3, 6);
            if (claveInsumo.length() >= 10)
                temp += "." + claveInsumo.substring(6, 10);
            if (claveInsumo.length() >= 12)
                temp += "." + claveInsumo.substring(10, 12);
            if (claveInsumo.length() >= 14)
                temp += "." + claveInsumo.substring(10, 12);
            claveInsumo = temp;
        }
        return claveInsumo;
    }
    
    private boolean esOrdenReabastoCompleta(String folioMUS) throws Exception {
        boolean completa = true;
        List<FolioAlternativoFolioMus> lfafm = folioAlternativoFolioMusService.obtenerFoliosAlternativos(folioMUS);
        for (FolioAlternativoFolioMus fafm : lfafm) {
            if (!fafm.getEstatus().equals(EstatusReabasto_Enum.REGISTRADA.name()) &&
                !fafm.getEstatus().equals(EstatusReabasto_Enum.ENTRANSITO.name())) {
                completa = false;
                break;
            }
        }
        return completa;
    }
    
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Colectivos SIAM" >  
    @POST
    @Path("recepcionColectivo")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response recepcionColectivo(String request) {
        boolean estatus;
        String folioAlternativo;
        Usuario usuarioSel;
        ReabastoExtended reabasto;
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode resultado = mapper.createObjectNode();
        try {
            StringBuilder result = new StringBuilder();
            JsonNode params = mapper.readTree(request);
            if (tokenEnabled()) {
                if (!params.hasNonNull("token") || !(new Token()).parseJWT(params.get("token").asText(), result)) {
                    return Response.status(Response.Status.UNAUTHORIZED).entity(RESOURCES.getString("siam.ws.login.err.noAutorizado") + " - " + result).build();
                }
                usuarioSel = obtenerUsuario(result.toString());
                if (usuarioSel == null) {
                    return Response.status(Response.Status.UNAUTHORIZED).entity(RESOURCES.getString("siam.ws.login.err.noAutorizado") + " - " + result).build();
                }
            }
            else {
                usuarioSel = obtenerUsuario(Constantes.USUARIO_SERVICIOS_WEB);
                if (usuarioSel == null) {
                    return Response.status(Response.Status.UNAUTHORIZED).entity(RESOURCES.getString("siam.ws.login.err.noUsuarioGenerico") + " - " + result).build();
                }
            }
            if (!params.hasNonNull("Folio") || !params.hasNonNull("Insumos")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(RESOURCES.getString("siam.ws.recepcion.err.datosRequeridos")).build();
            }
            folioAlternativo = params.get("Folio").asText();
            reabasto = obtenerReabasto(folioAlternativo);
            if (folioAlternativo.isEmpty() || reabasto == null) {
                resultado.put("respuestaCodigo", CodigosRespuestaSiam_Enum.FOLIO_INEXISTENTE.getValue());
                resultado.put("respuestaMensaje", RESOURCES.getString("siam.ws.recepcion.err.folioInexistente"));
                resultado.put("datosExtra", folioAlternativo);
                return Response.ok(resultado.toString()).build();
            }
            if (!reabasto.getIdEstatusReabasto().equals(EstatusReabasto_Enum.SOLICITADA.getValue()) &&
                !reabasto.getIdEstatusReabasto().equals(EstatusReabasto_Enum.SURTIDAPARCIAL.getValue())) {
                switch (reabasto.getIdEstatusReabasto()) {
                    case 1: //Estos casos (1 y 3) no deberían ocurrir
                    case 3: resultado.put("respuestaCodigo", CodigosRespuestaSiam_Enum.ESTATUS_INVALIDO.getValue());
                            resultado.put("respuestaMensaje", RESOURCES.getString("siam.ws.recepcion.err.estatusInvalido"));
                            break;
                    case 7: resultado.put("respuestaCodigo", CodigosRespuestaSiam_Enum.SOLICITUD_CANCELADA.getValue());
                            resultado.put("respuestaMensaje", RESOURCES.getString("siam.ws.recepcion.err.solicitudCancelada"));
                            break;
                    case 9: resultado.put("respuestaCodigo", CodigosRespuestaSiam_Enum.SOLICITUD_NO_SURTIDA.getValue());
                            resultado.put("respuestaMensaje", RESOURCES.getString("siam.ws.recepcion.err.noSurtida"));
                            break;
                    case 10: resultado.put("respuestaCodigo", CodigosRespuestaSiam_Enum.SOLICITUD_CANCELADA_VIGENCIA.getValue());
                            resultado.put("respuestaMensaje", RESOURCES.getString("siam.ws.recepcion.err.solicitudCanceladaVigencia"));
                            break;
                    //default: En Tránsito(4), Recibida Parcial(5), Recibida(6), Ingresada(8)
                    default:resultado.put("respuestaCodigo", CodigosRespuestaSiam_Enum.SOLICITUD_PROCESADA.getValue());
                            resultado.put("respuestaMensaje", RESOURCES.getString("siam.ws.recepcion.err.solicitudProcesada"));
                            break;
                }
                resultado.put("datosExtra", folioAlternativo);
                return Response.ok(resultado.toString()).build();
            }
            //JsonNode jn = params.get("Insumos");            
            //List<InsumoSIAM> insumos = mapper.readValue(jn.toString(), new TypeReference<List<InsumoSIAM>>(){});
            // Se cambia la forma de recibir los insumos porque la clase InsumoSIAM cambio a minusculas            
            List<InsumoSIAM> insumos =new ArrayList<>();
            ArrayNode items = (ArrayNode)params.get("Insumos");
            for(JsonNode item : items ){
                InsumoSIAM insumo = new InsumoSIAM();
                insumo.setClaveInsumo(item.get("ClaveInsumo").asText());
                insumo.setCantidad(item.get("Cantidad").asInt());
                
                insumos.add(insumo);
            } 
            
            if (insumos.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity(RESOURCES.getString("siam.ws.recepcion.err.listaVacia")).build();
            }
            List<ReabastoInsumoExtended> listaDetalleInsumos = obtenerDetalleInsumos(reabasto);
            if (listaDetalleInsumos.isEmpty()) {
                resultado.put("respuestaCodigo", CodigosRespuestaSiam_Enum.ERROR_RECIBIR.getValue());
                resultado.put("respuestaMensaje", RESOURCES.getString("siam.ws.recepcion.err.sinMedicamentos"));
                return Response.ok(resultado.toString()).build();
            }
            Date fechaUpdate = new Date();
            List<ReabastoInsumoExtended> listaReabastoInsumo = new ArrayList();
            List<ReabastoEnviado> listaReabEnviadoInsert = new ArrayList();
            List<ReabastoEnviado> listaReabEnviadoUpdate = new ArrayList();
            List<DetalleInsumoSiam> listaDetalleInsumoSiam = new ArrayList();
            List<Inventario> inventarioCedimeInsert = new ArrayList();
            List<Inventario> inventarioCedimeUpdate = new ArrayList();
            for (InsumoSIAM insumo : insumos) {
                if (insumo.getCantidad() == null || insumo.getCantidad() < 0) {
                    resultado.put("respuestaCodigo", CodigosRespuestaSiam_Enum.CANTIDAD_INVALIDA.getValue());
                    resultado.put("respuestaMensaje", RESOURCES.getString("siam.ws.recepcion.err.cantidadInvalida"));
                    resultado.put("datosExtra", insumo.getClaveInsumo());
                    return Response.ok(resultado.toString()).build();
                }
                String claveSIAM = insumo.getClaveInsumo();//       010000010400
                insumo.setClaveInsumo(convierteClave(claveSIAM));// 010000010400
                ReabastoInsumoExtended reabastoInsumo = buscarReabastoInsumo(listaDetalleInsumos, insumo);
                DetalleInsumoSiam detalleInsumoSiam = obtenerDetalleInsumoSiam(folioAlternativo, reabastoInsumo.getIdInsumo());
                Inventario inventarioCedime = inventarioService.obtenerInventariosPorInsumoEstructuraLoteYCantidadXCajaYFechaCad(reabastoInsumo.getIdInsumo(), reabasto.getIdEstructura(), Constantes.LOTE_GENERICO, reabastoInsumo.getFactorTransformacion(), null, FechaUtil.formatoFecha("yyyy-MM-dd", Constantes.CADUCIDAD_GENERICA));
                Inventario inventarioAlmacenFarmacia = inventarioService.obtenerInventariosPorInsumoEstructuraLoteYCantidadXCajaYFechaCad(reabastoInsumo.getIdInsumo(), reabasto.getIdEstructuraPadre(), Constantes.LOTE_GENERICO, reabastoInsumo.getFactorTransformacion(), null, FechaUtil.formatoFecha("yyyy-MM-dd", Constantes.CADUCIDAD_GENERICA));
                if (detalleInsumoSiam != null) {
                    int estatusReabastoInsumo = reabastoInsumo.getIdEstatusReabasto();
                    if (reabastoInsumo.getCantidadSurtida() == null)
                        reabastoInsumo.setCantidadSurtida(0);
                    reabastoInsumo.setCantidadSurtida(reabastoInsumo.getCantidadSurtida() + (insumo.getCantidad() * reabastoInsumo.getFactorTransformacion()));
                    DetalleInsumoSiam ultimaSurtida;
                    try {
                        ultimaSurtida = detalleInsumoSiamService.ultimaColectivaSurtida(detalleInsumoSiam.getIdEstructura(), reabastoInsumo.getIdInsumo());
                    }
                    catch(Exception oex) {
                        ultimaSurtida = null;
                        LOGGER.debug("{}.recepcionColectivo(): {}", this.getClass().getCanonicalName(), oex.getMessage());
                    }
                    detalleInsumoSiam.setCantidadSurtida(insumo.getCantidad());
                    if (ultimaSurtida == null || ultimaSurtida.getCantidadActual() == null) {
                        detalleInsumoSiam.setCantidadActual(insumo.getCantidad());
                    }
                    else {
                        detalleInsumoSiam.setCantidadActual(insumo.getCantidad() + ultimaSurtida.getCantidadActual());
                    }
                    detalleInsumoSiam.setFechaRecepcion(fechaUpdate);
                    detalleInsumoSiam.setIdUsuarioRecepcion(usuarioSel.getIdUsuario());
                    detalleInsumoSiam.setUpdateFecha(fechaUpdate);
                    detalleInsumoSiam.setUpdateIdUsuario(usuarioSel.getIdUsuario());
                    listaDetalleInsumoSiam.add(detalleInsumoSiam);
                    if (inventarioCedime == null) {
                        inventarioCedime = new Inventario();
                        inventarioCedime.setIdInventario(Comunes.getUUID());
                        inventarioCedime.setFechaIngreso(fechaUpdate);
                        inventarioCedime.setIdEstructura(reabasto.getIdEstructura());
                        inventarioCedime.setIdInsumo(reabastoInsumo.getIdInsumo());
                        inventarioCedime.setIdPresentacion(reabastoInsumo.getIdPresentacion());
                        inventarioCedime.setLote(Constantes.LOTE_GENERICO);
                        inventarioCedime.setCantidadXCaja(reabastoInsumo.getFactorTransformacion());
                        inventarioCedime.setFechaCaducidad(FechaUtil.formatoFecha("yyyy-MM-dd", Constantes.CADUCIDAD_GENERICA));
                        inventarioCedime.setCosto(1.0);
                        inventarioCedime.setCostoUnidosis(1.0 / reabastoInsumo.getFactorTransformacion());
                        inventarioCedime.setCantidadActual(insumo.getCantidad() * reabastoInsumo.getFactorTransformacion());
                        inventarioCedime.setExistenciaInicial(0);
                        inventarioCedime.setPresentacionComercial(0);
                        inventarioCedime.setActivo(1);
                        inventarioCedime.setInsertFecha(fechaUpdate);
                        inventarioCedime.setInsertIdUsuario(usuarioSel.getIdUsuario());
                        inventarioCedime.setIdTipoOrigen(TipoOrigen_Enum.ADMINISTRACION.getValue());
                        inventarioCedime.setEnviarAVG(0);
                        inventarioCedimeInsert.add(inventarioCedime);
                    }
                    else {
                        inventarioCedime.setCantidadActual(inventarioCedime.getCantidadActual() + insumo.getCantidad() * reabastoInsumo.getFactorTransformacion());
                        inventarioCedime.setUpdateFecha(fechaUpdate);
                        inventarioCedime.setUpdateIdUsuario(usuarioSel.getIdUsuario());
                        inventarioCedimeUpdate.add(inventarioCedime);
                    }
                    if (inventarioAlmacenFarmacia != null) {
                        inventarioAlmacenFarmacia.setCantidadActual(inventarioAlmacenFarmacia.getCantidadActual() - insumo.getCantidad() * reabastoInsumo.getFactorTransformacion());
                        inventarioAlmacenFarmacia.setUpdateFecha(fechaUpdate);
                        inventarioAlmacenFarmacia.setUpdateIdUsuario(usuarioSel.getIdUsuario());
                        inventarioCedimeUpdate.add(inventarioAlmacenFarmacia);
                    }
                    int idEstatus = EstatusReabasto_Enum.ENTRANSITO.getValue();
                    if (insumo.getCantidad() == 0) {
                        idEstatus = EstatusReabasto_Enum.NO_SURTIDO.getValue();
                    }
                    else if (insumo.getCantidad() < detalleInsumoSiam.getCantidadSolicitada()) {
                        idEstatus = EstatusReabasto_Enum.SURTIDAPARCIAL.getValue();
                    }
                    reabastoInsumo.setIdEstatusReabasto(idEstatus);
                    reabastoInsumo.setUpdateFecha(fechaUpdate);
                    reabastoInsumo.setUpdateIdUsuario(usuarioSel.getIdUsuario());
                    listaReabastoInsumo.add(reabastoInsumo);

                    if (estatusReabastoInsumo == EstatusReabasto_Enum.SOLICITADA.getValue()) {
                        ReabastoEnviado reabastoEnviado = new ReabastoEnviado();
                        reabastoEnviado.setIdReabastoEnviado(Comunes.getUUID());
                        reabastoEnviado.setIdReabastoInsumo(reabastoInsumo.getIdReabastoInsumo());
                        reabastoEnviado.setCantidadEnviado(insumo.getCantidad() * reabastoInsumo.getFactorTransformacion());
                        reabastoEnviado.setIdEstatusReabasto(idEstatus);                        
                        reabastoEnviado.setIdInsumo(reabastoInsumo.getIdInsumo());
                        reabastoEnviado.setIdEstructura(reabasto.getIdEstructura());
                        reabastoEnviado.setLoteEnv(Constantes.LOTE_GENERICO);
                        reabastoEnviado.setFechaCad(FechaUtil.formatoFecha("yyyy-MM-dd", Constantes.CADUCIDAD_GENERICA));
                        reabastoEnviado.setCantidadXCaja(reabastoInsumo.getFactorTransformacion());
                        reabastoEnviado.setPresentacionComercial(1);    //CAJA
                        reabastoEnviado.setClaveProveedor(null);
                        reabastoEnviado.setEstatusAVG(0);
                        reabastoEnviado.setInsertFecha(fechaUpdate);
                        reabastoEnviado.setInsertIdUsuario(usuarioSel.getIdUsuario());
                        reabastoEnviado.setClaveOriginalSurtida(null);
                        listaReabEnviadoInsert.add(reabastoEnviado);
                    } else {
                        ReabastoEnviado reabastoEnviado = new ReabastoEnviado();
                        reabastoEnviado.setIdReabastoInsumo(reabastoInsumo.getIdReabastoInsumo());
                        reabastoEnviado.setIdInsumo(reabastoInsumo.getIdInsumo());
                        reabastoEnviado.setIdEstructura(reabasto.getIdEstructura());
                        reabastoEnviado.setIdEstatusReabasto(idEstatus);
                        reabastoEnviado = reabastoEnviadoService.obtener(reabastoEnviado);                        
                        reabastoEnviado.setCantidadEnviado(reabastoEnviado.getCantidadEnviado() + (insumo.getCantidad() * reabastoInsumo.getFactorTransformacion()));
                        reabastoEnviado.setIdEstatusReabasto(idEstatus);
                        reabastoEnviado.setUpdateFecha(fechaUpdate);
                        reabastoEnviado.setUpdateIdUsuario(usuarioSel.getIdUsuario());
                        listaReabEnviadoUpdate.add(reabastoEnviado);
                    }
                }
                else {
                    resultado.put("respuestaCodigo", CodigosRespuestaSiam_Enum.INSUMO_INEXISTENTE.getValue());
                    resultado.put("respuestaMensaje", RESOURCES.getString("siam.ws.recepcion.err.insumoInexistente"));
                    resultado.put("datosExtra", claveSIAM);
                    return Response.ok(resultado.toString()).build();
                }
            }
            FolioAlternativoFolioMus fafm = new FolioAlternativoFolioMus();
            fafm.setFolioAlternativo(folioAlternativo);
            fafm.setFolioMUS(reabasto.getFolio());
            fafm = folioAlternativoFolioMusService.obtener(fafm);
            fafm.setEstatus(EstatusReabasto_Enum.ENTRANSITO.name());
            estatus = folioAlternativoFolioMusService.actualizar(fafm);
            reabasto.setFechaSurtida(fechaUpdate);
            reabasto.setIdUsuarioSurtida(usuarioSel.getIdUsuario());
            reabasto.setUpdateFecha(fechaUpdate);
            reabasto.setUpdateIdUsuario(usuarioSel.getIdUsuario());
            if (esOrdenReabastoCompleta(reabasto.getFolio()))
                reabasto.setIdEstatusReabasto(EstatusReabasto_Enum.ENTRANSITO.getValue());
            else
                reabasto.setIdEstatusReabasto(EstatusReabasto_Enum.SURTIDAPARCIAL.getValue());
            
            if (estatus)
                estatus = reabastoService.surtirReabastoRecepcionColectivo(reabasto, listaReabastoInsumo, listaReabEnviadoInsert, listaReabEnviadoUpdate, listaDetalleInsumoSiam, inventarioCedimeUpdate, inventarioCedimeInsert);
        }
        catch (Exception ex) {
            LOGGER.error("{}.recepcionColectivo(): {}", this.getClass().getCanonicalName(), ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        if (!estatus) {
            resultado.put("respuestaCodigo", CodigosRespuestaSiam_Enum.ERROR_RECIBIR.getValue());
            resultado.put("respuestaMensaje", RESOURCES.getString("siam.ws.recepcion.err.noProcesada"));
            resultado.put("datosExtra", folioAlternativo);
            return Response.ok(resultado.toString()).build();
        }
        resultado.put("respuestaCodigo", CodigosRespuestaSiam_Enum.EXITOSO.getValue());
        resultado.put("respuestaMensaje", RESOURCES.getString("siam.ws.recepcion.ok.procesada"));
        resultado.put("datosExtra", reabasto.getFolio());
        return Response.ok(resultado.toString()).build();
    }

    @POST
    @Path("consultaColectivo")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultaColectivo(String request){        
        String folioAlternativo;
        Usuario usuarioSel;
        ReabastoExtended reabasto;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode resultado = mapper.createObjectNode();
        try {
            StringBuilder result = new StringBuilder();
            JsonNode params = mapper.readTree(request);
            if (tokenEnabled()) {
                if (!params.hasNonNull("token") || !(new Token()).parseJWT(params.get("token").asText(), result)) {
                    return Response.status(Response.Status.UNAUTHORIZED).entity(RESOURCES.getString("siam.ws.login.err.noAutorizado") + " - " + result).build();
                }
                usuarioSel = obtenerUsuario(result.toString());
                if (usuarioSel == null) {
                    return Response.status(Response.Status.UNAUTHORIZED).entity(RESOURCES.getString("siam.ws.login.err.noAutorizado") + " - " + result).build();
                }
            }
            else {
                usuarioSel = obtenerUsuario(Constantes.USUARIO_SERVICIOS_WEB);
                if (usuarioSel == null) {
                    return Response.status(Response.Status.UNAUTHORIZED).entity(RESOURCES.getString("siam.ws.login.err.noUsuarioGenerico") + " - " + result).build();
                }
            }
            if (!params.hasNonNull("folio")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(RESOURCES.getString("siam.ws.recepcion.err.datosRequeridos")).build();
            }
            folioAlternativo = params.get("folio").asText();
            reabasto = obtenerReabasto(folioAlternativo);
            
            
            if (folioAlternativo.isEmpty() || reabasto == null) {
                resultado.put("tipoInsumo", "");
                resultado.put("folio", "");
                resultado.put("especialidad", "");
                //resultado.put("claveCentroTrabajo","027-204-00");
                resultado.put("fechaSolicitud", "");
                resultado.put("fechaSurtido", "");
                ArrayNode insumos = resultado.putArray("insumos");
                resultado.put("mensaje",RESOURCES.getString("siam.ws.recepcion.err.folioInexistente"));
                return Response.ok(resultado.toString()).build();
            }else{                            
                List<ReabastoInsumoExtended> listaDetalleInsumos = obtenerDetalleInsumoByFolioAlternativo(folioAlternativo);
                if(!listaDetalleInsumos.isEmpty()){
                    ReabastoInsumoExtended tmp = listaDetalleInsumos.get(0);
                    resultado.put("tipoInsumo",tmp.getTipoInsumo().equals(Constantes.MEDI) ? "M":"C");
                    resultado.put("folio", folioAlternativo);
                    resultado.put("especialidad", tmp.getAlmacenServicio().toUpperCase()); 
                    //resultado.put("claveCentroTrabajo","027-204-00");                   
                    resultado.put("fechaSolicitud", reabasto.getFechaSolicitud() != null ? dateFormat.format(reabasto.getFechaSolicitud()):"");                    
                    resultado.put("fechaSurtido", tmp.getFechaEnviada() != null ? dateFormat.format(tmp.getFechaEnviada()):"");

                    ArrayNode insumos = resultado.putArray("insumos");
                    for (ReabastoInsumoExtended item : listaDetalleInsumos) {
                        ObjectNode insumo = mapper.createObjectNode();
                        insumo
                            .put("claveInsumo",item.getClaveInstitucional())
                            .put("solicitado",item.getCantidadSolicitada())
                            .put("surtido",item.getEnviado());

                        insumos.add(insumo);
                    }
                    resultado.put("mensaje","Éxito");
                }else{
                    resultado.put("tipoInsumo", "");
                    resultado.put("folio", folioAlternativo);
                    resultado.put("especialidad", "");
                    //resultado.put("claveCentroTrabajo","027-204-00");
                    resultado.put("fechaSolicitud", reabasto.getFechaSolicitud() != null ? dateFormat.format(reabasto.getFechaSolicitud()):"");
                    resultado.put("fechaSurtido", "");
                    ArrayNode insumos = resultado.putArray("insumos");
                    resultado.put("mensaje",RESOURCES.getString("siam.ws.recepcion.err.listaVacia"));
                    return Response.ok(resultado.toString()).build();
                }
            }
        }
        catch (Exception ex) {
            LOGGER.error("{}.recepcionColectivo(): {}", this.getClass().getCanonicalName(), ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        
        return Response.ok(resultado.toString()).build();
    }
    // </editor-fold>
        
    // <editor-fold defaultstate="collapsed" desc="Recetas SIAM" >  
    
    @POST
    @Path("recetaSurtida")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response recetaSurtida(String request){
        String idBitacora = Comunes.getUUID();
        boolean sigue=false;       
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"); 
        RecetaSiam recetaSiam = new RecetaSiam();
        String folioMus="";
        String codigoRespuesta="";
        try {
            bitacora = new BitacoraMensaje();
            bitacora.setIdBitacoraMensaje(idBitacora);
            bitacora.setFecha(new Date());
            bitacora.setTipoMensaje("EXT");
            bitacora.setEntradaSalida("E");
            bitacora.setIdMensaje(idBitacora);
            bitacora.setMensaje(request);
            
            sigue = bitacoraService.insertar(bitacora);            
        } catch (Exception ex) {
            LOGGER.error("{}.recetaSurtida(): {}", this.getClass().getCanonicalName(), ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        
        Usuario usuarioSel;
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode resultado = mapper.createObjectNode();
        try {
            StringBuilder result = new StringBuilder();
            JsonNode params = mapper.readTree(request);
            if (tokenEnabled()) {
                if (!params.hasNonNull("token") || !(new Token()).parseJWT(params.get("token").asText(), result)) {
                    return Response.status(Response.Status.UNAUTHORIZED).entity(RESOURCES.getString("siam.ws.login.err.noAutorizado") + " - " + result).build();
                }
                usuarioSel = obtenerUsuario(result.toString());
                if (usuarioSel == null) {
                    return Response.status(Response.Status.UNAUTHORIZED).entity(RESOURCES.getString("siam.ws.login.err.noAutorizado") + " - " + result).build();
                }
            }
            else {               
                Usuario  usuarioGen = obtenerUsuario(Constantes.USUARIO_SERVICIOS_WEB);
                if (usuarioGen == null) {
                    return Response.status(Response.Status.UNAUTHORIZED).entity(RESOURCES.getString("siam.ws.login.err.noUsuarioGenerico") + " - " + result).build();
                }
            
                usuarioSel = obtenerUsuario(params.get("usuario").asText());
                if(usuarioSel==null){
                    try{
                        Usuario user = new Usuario();
                        user.setIdUsuario(Comunes.getUUID());
                        user.setNombreUsuario(params.get("usuario").asText());
                        user.setClaveAcceso(user.getIdUsuario());
                        user.setCorreoElectronico(params.get("usuario").asText()+"@email.com");
                        user.setNombre(params.get("usuario").asText());
                        user.setActivo(Constantes.ACTIVO);
                        user.setUsuarioBloqueado(Constantes.INACTIVO);
                        Date vigencia = sdf.parse("2040-12-31T23:59:59.986Z");
                        user.setFechaVigencia(vigencia);
                        user.setCedProfesional("");
                        user.setInsertFecha(new Date());
                        user.setInsertIdUsuario(usuarioGen.getIdUsuario());

                        usuarioService.insertar(user);
                        usuarioSel = user;
                    }catch(Exception ex){
                        usuarioSel = usuarioGen;
                        LOGGER.error("Ocurrio un error al registrar el Usuario",ex);
                    }
                }
            }                        
            
            if (!params.hasNonNull("folio") || !params.hasNonNull("insumos")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(RESOURCES.getString("siam.ws.receta.err.folioRequerido")).build();
            }
            if (!params.hasNonNull("fecha_surtimiento")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(RESOURCES.getString("siam.ws.receta.err.fechaRequerido")).build();
            }
            
            recetaSiam.setId(params.get("id").asInt());
            recetaSiam.setFolio(params.get("folio").asText());
            recetaSiam.setClaveUnidadMedica(params.get("clave_unidad_medica").asText());
            recetaSiam.setFechaSurtimiento(params.get("fecha_surtimiento").asText());
            recetaSiam.setIdTipoMovimiento(params.hasNonNull("id_tipo_movimiento") ? params.get("id_tipo_movimiento").asInt():0);
            recetaSiam.setUsuario(params.get("usuario").asText());
            recetaSiam.setEstatus(params.hasNonNull("estatus") ? params.get("estatus").asText():"");
            
            if (!params.hasNonNull("derechoHabiente")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(RESOURCES.getString("siam.ws.receta.err.derechoHabienteRequerido")).build();
            }
            JsonNode jnDerecho = params.get("derechoHabiente");                    
            DerechoHabienteSiam  derechoHabiente = new DerechoHabienteSiam();
            derechoHabiente.setId(jnDerecho.get("id").asInt());
            
            if (!jnDerecho.hasNonNull("num_issste")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(RESOURCES.getString("siam.ws.receta.err.numIsssteRequerido")).build();
            }
            derechoHabiente.setNumIssste(jnDerecho.get("num_issste").asInt());
            derechoHabiente.setRfc(jnDerecho.hasNonNull("rfc") ? jnDerecho.get("rfc").asText():"");
            derechoHabiente.setApaTra(jnDerecho.get("apa_tra").asText());
            derechoHabiente.setAmaTra(jnDerecho.get("ama_tra").asText());
            derechoHabiente.setNomTra(jnDerecho.get("nom_tra").asText());
            derechoHabiente.setCurpTra(jnDerecho.hasNonNull("curp_tra") ? jnDerecho.get("curp_tra").asText():"");
            derechoHabiente.setApellidoPaterno(jnDerecho.get("apellido_paterno").asText());
            derechoHabiente.setApellidoMaterno(jnDerecho.get("apellido_materno").asText());
            derechoHabiente.setNombre(jnDerecho.get("nombre").asText());
            derechoHabiente.setCurp(jnDerecho.hasNonNull("curp") ? jnDerecho.get("curp").asText():"");
            derechoHabiente.setParentescoCve(jnDerecho.get("parentesco_cve").asInt());
            
            recetaSiam.setDerechoHabiente(derechoHabiente);
            
            if (!params.hasNonNull("medico")){
                return Response.status(Response.Status.BAD_REQUEST).entity(RESOURCES.getString("siam.ws.receta.err.medicoRequerido")).build();
            }
            JsonNode jnMedico = params.get("medico");
            MedicoSiam medicoSiam = new MedicoSiam();
            medicoSiam.setId(jnMedico.get("id").asInt());
            
            if (!jnMedico.hasNonNull("clave") || !jnMedico.hasNonNull("especialidad")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(RESOURCES.getString("siam.ws.receta.err.claveEspecialidadRequerido")).build();
            }
            medicoSiam.setClave(jnMedico.get("clave").asText());
            medicoSiam.setNombre(jnMedico.get("nombre").asText());
            medicoSiam.setEspecialidad(jnMedico.get("especialidad").asText());
            
            recetaSiam.setMedico(medicoSiam);            
            
            if (!params.hasNonNull("insumos")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(RESOURCES.getString("siam.ws.receta.err.insumosRequerido")).build();
            }            
            List<InsumoRecetaSiam> insumos =new ArrayList<>();
            ArrayNode items = (ArrayNode)params.get("insumos");
            for(JsonNode item : items ){
                InsumoRecetaSiam insumo = new InsumoRecetaSiam();
                insumo.setClaveInsumo(item.get("clave_insumo").asText());
                insumo.setEsAntibiotico(item.hasNonNull("esAntibiotico") ? item.get("esAntibiotico").asBoolean():false);
                insumo.setPrecio(item.get("precio").asDouble());
                insumo.setCantidad(item.get("cantidad").asInt());
                insumo.setNegado(item.get("negado").asInt());
                insumo.setTipoInsumo(item.get("tipo_insumo").asText());
                
                insumos.add(insumo);
            }            
            recetaSiam.setInsumoRecetaList(insumos);
                    
            folioMus = recetaSiamService.RegistrarRecetaSiam(recetaSiam,usuarioSel);
            
            if(!"".equals(folioMus)){
                resultado.put("folio", recetaSiam.getFolio());
                resultado.put("folioExterno",folioMus);
                resultado.put("respuesta_mensaje","OK");
                codigoRespuesta = folioMus;
                
            }else{
                resultado.put("folio", recetaSiam.getFolio());
                resultado.put("folioExterno","");
                resultado.put("respuesta_mensaje","No se pudo registrar la receta");
                codigoRespuesta ="No se pudo registrar la receta";
            }
                        
            bitacoraService.updateMensajeBitacora(idBitacora,codigoRespuesta);
        } catch (Exception ex) {
            LOGGER.error("{}.recetaSurtida(): {}",this.getClass().getCanonicalName(),ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        
        return Response.ok(resultado.toString()).build();
    }
    // </editor-fold>
}
