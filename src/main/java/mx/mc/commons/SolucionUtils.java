package mx.mc.commons;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.mc.dto.MedicamentoDTO;
import mx.mc.enums.CatalogoGeneral_Enum;
import mx.mc.enums.EstatusCama_Enum;
import mx.mc.enums.EstatusDiagnostico_Enum;
import mx.mc.enums.EstatusGabinete_Enum;
import mx.mc.enums.EstatusPaciente_Enum;
import mx.mc.enums.EstatusPrescripcion_Enum;
import mx.mc.enums.EstatusSolucion_Enum;
import mx.mc.enums.EstatusSurtimiento_Enum;
import mx.mc.enums.GrupoCatalogoGeneral_Enum;
import mx.mc.enums.MotivoPacienteMovimiento_Enum;
import mx.mc.enums.PlantillaCorreo_Enum;
import mx.mc.enums.TipoAreaEstructura_Enum;
import mx.mc.enums.TipoIngrediente_Enum;
import mx.mc.enums.TipoPerfilUsuario_Enum;
import mx.mc.enums.TipoUsuario_Enum;
import mx.mc.init.Constantes;
import mx.mc.lazy.AlmacenServicioLazy;
import mx.mc.lazy.DispensacionSolucionLazy;
import mx.mc.model.AdjuntoExtended;
import mx.mc.model.CamaExtended;
import mx.mc.model.CatalogoGeneral;
import mx.mc.model.CodigoInsumo;
import mx.mc.model.Config;
import mx.mc.model.Diagnostico;
import mx.mc.model.DiagnosticoPaciente;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.EnvaseContenedor;
import mx.mc.model.Equipo;
import mx.mc.model.Estabilidad;
import mx.mc.model.Estabilidad_Extended;
import mx.mc.model.Estructura;
import mx.mc.model.EtiquetaInsumo;
import mx.mc.model.Frecuencia;
import mx.mc.model.HipersensibilidadExtended;
import mx.mc.model.HorarioEntrega;
import mx.mc.model.Impresora;
import mx.mc.model.Inventario;
import mx.mc.model.InventarioExtended;
import mx.mc.model.Medicamento;
import mx.mc.model.Medicamento_Extended;
import mx.mc.model.MotivosRechazo;
import mx.mc.model.NutricionParenteralDetalle;
import mx.mc.model.NutricionParenteralExtended;
import mx.mc.model.NutricionParenteralDetalleExtended;
import mx.mc.model.Paciente;
import mx.mc.model.PacienteDomicilio;
import mx.mc.model.PacienteServicio;
import mx.mc.model.PacienteUbicacion;
import mx.mc.model.Paciente_Extended;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.PlantillaCorreo;
import mx.mc.model.Prescripcion;
import mx.mc.model.PrescripcionInsumo;
import mx.mc.model.Protocolo;
import mx.mc.model.ReaccionExtend;
import mx.mc.model.RepSurtimientoPresc;
import mx.mc.model.Solucion;
import mx.mc.model.SolucionExtended;
import mx.mc.model.Surtimiento;
import mx.mc.model.SurtimientoEnviado;
import mx.mc.model.SurtimientoEnviado_Extend;
import mx.mc.model.SurtimientoInsumo;
import mx.mc.model.SurtimientoInsumo_Extend;
import mx.mc.model.Surtimiento_Extend;
import mx.mc.model.TemplateEtiqueta;
import mx.mc.model.TipoJustificacion;
import mx.mc.model.TipoSolucion;
import mx.mc.model.TipoUsuario;
import mx.mc.model.Turno;
import mx.mc.model.UnidadConcentracion;
import mx.mc.model.Usuario;
import mx.mc.model.ViaAdministracion;
import mx.mc.model.Visita;
import mx.mc.service.CamaService;
import mx.mc.service.CatalogoGeneralService;
import mx.mc.service.ConfigService;
import mx.mc.service.DiagnosticoService;
import mx.mc.service.EntidadHospitalariaService;
import mx.mc.service.EnvaseContenedorService;
import mx.mc.service.EquipoService;
import mx.mc.service.EstabilidadService;
import mx.mc.service.EstructuraService;
import mx.mc.service.FrecuenciaService;
import mx.mc.service.HipersensibilidadService;
import mx.mc.service.ImpresoraService;
import mx.mc.service.InventarioService;
import mx.mc.service.MedicamentoService;
import mx.mc.service.MotivosRechazoService;
import mx.mc.service.PacienteService;
import mx.mc.service.PacienteServicioService;
import mx.mc.service.PacienteUbicacionService;
import mx.mc.service.PlantillaCorreoService;
import mx.mc.service.PrescripcionService;
import mx.mc.service.ProtocoloService;
import mx.mc.service.ReaccionService;
import mx.mc.service.ReportesService;
import mx.mc.service.SolucionService;
import mx.mc.service.SurtimientoInsumoService;
import mx.mc.service.SurtimientoService;
import mx.mc.service.TemplateEtiquetaService;
import mx.mc.service.TipoJustificacionService;
import mx.mc.service.TipoSolucionService;
import mx.mc.service.TipoUsuarioService;
import mx.mc.service.TurnoService;
import mx.mc.service.UsuarioService;
import mx.mc.service.ViaAdministracionService;
import mx.mc.service.VisitaService;
import mx.mc.util.ClientInfo;
import mx.mc.util.CodigoBarras;
import mx.mc.util.Comunes;
import mx.mc.util.EnviaCorreoUtil;
import mx.mc.util.FechaUtil;
import mx.mc.util.Mensaje;
import mx.mc.util.ValidaUtil;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SoluciónUtils: Funciones comunes para todo el módulo de Soluciones
 *
 * @author Cervanets
 * @version 0.1, 2023/01/10
 */
public class SolucionUtils implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(AlmacenServicioLazy.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    private final transient EstructuraService estService;
    private final transient SurtimientoService surService;
    private final transient TemplateEtiquetaService temEtiService;
    private final transient ImpresoraService impService;
    private final transient MotivosRechazoService motRecService;
    private final transient PrescripcionService preService;
    private final transient SurtimientoInsumoService surInsService;
//    private transient SurtimientoEnviadoService surEnvService;
    private final transient SolucionService solService;
//    private transient PrescripcionInsumoService preInsService;
    private final transient ReportesService repService;
    private final transient EntidadHospitalariaService entHosService;
    private final transient DiagnosticoService diaService;
    private final transient EnvaseContenedorService envConService;
    private final transient ViaAdministracionService viaAdmService;
    private final transient TipoSolucionService tipSolService;
    private final transient ProtocoloService proService;
    private final transient VisitaService visService;
    private final transient CamaService camService;
    private final transient FrecuenciaService freService;
    private final transient CatalogoGeneralService catGenService;
    private final transient TurnoService turService;
    private final transient TipoUsuarioService tipUsuService;
    private final transient PacienteServicioService pacSerService;
    private final transient PacienteUbicacionService pacUbiService;
    private final transient EnviaCorreoUtil envCorUtil;
    private final transient PlantillaCorreoService plaCorService;
    private final transient ConfigService conService;
    private final transient TipoJustificacionService tipJusService;
    private final transient MedicamentoService medService;
    private final transient UsuarioService usuService;
    private final transient PacienteService pacService;
    private final transient HipersensibilidadService hipService;
    private final transient ReaccionService reaService;
    private final transient EquipoService equipoService;
    private final transient EstabilidadService estabiService;
    private final transient InventarioService invenService;
//    private Usuario_Extended usuExt;
//    private Prescripcion_Extended preExt;
//    private transient List<PrescripcionInsumo_Extended> preInsExtLista;
//    private Surtimiento_Extend surExt;
//    private transient List<SurtimientoInsumo_Extend> surInsExtLista;
//    private transient List<SurtimientoEnviado_Extend> surEnvExtLista;
//    private SolucionExtended solExt;

    public SolucionUtils(EstructuraService estService, SurtimientoService surService, TemplateEtiquetaService temEtiService, ImpresoraService impService, MotivosRechazoService motRecService, PrescripcionService preService, SurtimientoInsumoService surInsService, SolucionService solService, ReportesService repService, EntidadHospitalariaService entHosService, DiagnosticoService diaService, EnvaseContenedorService envConService, ViaAdministracionService viaAdmService, TipoSolucionService tipSolService, ProtocoloService proService, VisitaService visService, CamaService camService, FrecuenciaService freService, CatalogoGeneralService catGenService, TurnoService turService, TipoUsuarioService tipUsuService, PacienteServicioService pacSerService, PacienteUbicacionService pacUbiService, EnviaCorreoUtil envCorUtil, PlantillaCorreoService plaCorService, ConfigService conService, TipoJustificacionService tipJusService, MedicamentoService medService, UsuarioService usuService, PacienteService pacService, HipersensibilidadService hipService, ReaccionService reaService, EquipoService equipoService, EstabilidadService estabiService, InventarioService invenService) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.<init>()");
        this.estService = estService;
        this.surService = surService;
        this.temEtiService = temEtiService;
        this.impService = impService;
        this.motRecService = motRecService;
        this.preService = preService;
        this.surInsService = surInsService;
        this.solService = solService;
        this.repService = repService;
        this.entHosService = entHosService;
        this.diaService = diaService;
        this.envConService = envConService;
        this.viaAdmService = viaAdmService;
        this.tipSolService = tipSolService;
        this.proService = proService;
        this.visService = visService;
        this.camService = camService;
        this.freService = freService;
        this.catGenService = catGenService;
        this.turService = turService;
        this.tipUsuService = tipUsuService;
        this.pacSerService = pacSerService;
        this.pacUbiService = pacUbiService;
        this.plaCorService = plaCorService;
        this.envCorUtil = envCorUtil;
        this.conService = conService;
        this.tipJusService = tipJusService;
        this.medService = medService;
        this.usuService = usuService;
        this.pacService = pacService;
        this.hipService = hipService;
        this.reaService = reaService;
        this.equipoService = equipoService;
        this.estabiService = estabiService;
        this.invenService = invenService;
    }

    /**
     * Método que obtiene lista de impresoras con base en los parámetros de
     * consulta
     *
     * @author Cervanets
     * @param u Objeto usuario que conntiene el id de usuario
     * @param descripcion Descripción de la impresora
     * @param tipo Tipo de la impresora E:Etiquetas, P:Pulsera o brazalete, N:
     * Normal
     * @see TipoImpresora_Enum relación con los tipos de impresoras
     * @return Lista de impresoras encontradas
     */
    public List<Impresora> obtenerListaImpresoras(Usuario u, String descripcion, String tipo) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.ontenerListaImpresoras()");
        String idUsuario = null;
        if (u != null) {
            idUsuario = u.getIdUsuario();
        }
        List<Impresora> impLista = new ArrayList<>();
        try {
            impLista.addAll(impService.obtenerListaImpresoras(idUsuario, descripcion, tipo));
        } catch (Exception ex) {
            LOGGER.error("Error al obtener lista de impresoras :: {} ", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al obtener lista de impresoras. ", null);
        }
        return impLista;
    }

    /**
     * Método que obtiene una impresoras por su identificador
     *
     * @author Cervanets
     * @param idImpresora
     * @return impresora encontrada
     */
    public Impresora obtenerImpresora(String idImpresora) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.ontenerImpresora()");
        Impresora i = null;
        try {
            if (idImpresora == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Impresora desconocida. ", null);

            } else {
                i = impService.obtenerPorIdImpresora(idImpresora);
            }
        } catch (Exception ex) {
            LOGGER.error("Error al obtener impresora :: {} ", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al obtener la impresora seleccionada. ", null);
        }
        return i;
    }

    /**
     * Método que obtiene lista de templates de etiquetas con base en los
     * parámetros de consulta
     *
     * @author Cervanets
     * @param tipoTemplate tipo de templates buscados
     * @return Lista de templates de etiquetas
     */
    public List<TemplateEtiqueta> obtenerListaTemplates(String tipoTemplate) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.obtenerListaTemplates()");
        List<TemplateEtiqueta> temLista = new ArrayList<>();
        try {
            temLista.addAll(temEtiService.obtenerListaTipo(tipoTemplate));
        } catch (Exception ex) {
            LOGGER.error("Error al obtener templates de etiquetas :: {} " + ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al obtener lista de Plantillas de impresión. ", null);
        }
        return temLista;
    }

    /**
     * Método que obtiene el template etiqueta con base en su identificador
     * único
     *
     * @author Cervanets
     * @param idTemplateEtiqueta identificador unico del templeta de impresión
     * @return Lista de templates de etiquetas
     */
    public TemplateEtiqueta obtenerTemplateEtiqueta(String idTemplateEtiqueta) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.obtenerListaTemplates()");
        TemplateEtiqueta t = null;
        try {
            if (idTemplateEtiqueta == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Plantilla de impresión seleccioada desconocida. ", null);

            } else {
                t = temEtiService.obtenerById(idTemplateEtiqueta);
            }
        } catch (Exception ex) {
            LOGGER.error("Error al obtener template de etiqueta :: {} ", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al obtener lista de Plantillas de impresión. ", null);
        }
        return t;
    }

    /**
     * Método que obtiene lista de impresoras con base en los parámetros de
     * consulta
     *
     * @author Cervanets
     * @see TipoImpresora_Enum relación con los tipos de impresoras
     * @return
     */
    public List<MotivosRechazo> obtenerMotivosRechazo() {
        LOGGER.debug("mx.mc.magedbean.SolucionUtils.obtenerMotivosRechazo()");
        List<MotivosRechazo> motivoLista = new ArrayList<>();
        try {
            motivoLista.addAll(this.motRecService.obtenerMotivosRechazoActivos());
        } catch (Exception ex) {
            LOGGER.error("Error al obtener motivos de rechazo :: {} ", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al obtener lista de motivos de Rechazo. ", null);
        }
        return motivoLista;
    }

    /**
     * Obtiene los Servicios que puede surtir el usuario, dependiendo de la
     * idEstructura de la unidad jerarquica por la que consulta el usuario en
     * sesion Adicionalmente El area a la que el usuario este asignada, debe ser
     * de tipo almacen y que tenga una asignación de servicio hospitalario que
     * puede surtir
     *
     * @param u usuario en sesión que listará surtimientos
     * @return
     * @throws java.lang.Exception
     */
    public List<Estructura> obtieneServiciosQuePuedeSurtir(Usuario u) throws Exception {
        LOGGER.debug("mx.mc.commons.SolucionUtils.obtieneServiciosQuePuedeSurtir()");
        List<Estructura> estLista = new ArrayList<>();

        if (u == null) {
            throw new Exception("Usuario inválido.");

        } else if (u.getIdEstructura() == null) {
            throw new Exception("Área de usuario inválido.");

        } else {
            if (Objects.equals(u.getIdTipoPerfil(), TipoPerfilUsuario_Enum.ADMIN.getIdTipoPerfil())
                    || Objects.equals(u.getIdTipoPerfil(), TipoPerfilUsuario_Enum.JEFE_AREA.getIdTipoPerfil())) {

                try {
                    List<Integer> tipoEstructuraLista = new ArrayList<>();
                    tipoEstructuraLista.add(TipoAreaEstructura_Enum.AREA.getValue());
                    tipoEstructuraLista.add(TipoAreaEstructura_Enum.CONSULTAEXTERNA.getValue());
                    tipoEstructuraLista.add(TipoAreaEstructura_Enum.CONSULTAINTERNA.getValue());
                    tipoEstructuraLista.add(TipoAreaEstructura_Enum.ESPECIALIDAD.getValue());
                    tipoEstructuraLista.add(TipoAreaEstructura_Enum.PABELLO.getValue());
                    tipoEstructuraLista.add(TipoAreaEstructura_Enum.SERVICIO.getValue());

                    List<Estructura> estructuraServicio = estService.obtenerEstructurasPorTipo(tipoEstructuraLista);
                    for (Estructura servicio : estructuraServicio) {
                        estLista.add(servicio);
                        List<String> idsEstructura = estService.obtenerIdsEstructuraJerarquica(servicio.getIdEstructura(), true);
                        for (String item : idsEstructura) {
                            estLista.add(estService.obtenerEstructura(item));
                        }
                    }
                } catch (Exception ex) {
                    LOGGER.error(ex.getMessage());
                }

            } else {
                try {
                    List<Estructura> estructuraServicio = estService.obtenerServicioQueSurtePorIdEstructura(u.getIdEstructura());
                    for (Estructura servicio : estructuraServicio) {
                        estLista.add(servicio);
                        List<String> idsEstructura = estService.obtenerIdsEstructuraJerarquica(servicio.getIdEstructura(), true);
                        for (String item : idsEstructura) {
                            estLista.add(estService.obtenerEstructura(item));
                        }
                    }
                } catch (Exception excp) {
                    LOGGER.error("Error al obtener Servicios que puede surtir el usuario: {}", excp.getMessage());
                    throw new Exception("Error al obtener Servicios que puede surtir el usuario.");
                }
            }
        }
        return estLista;
    }
    
    /**
     * Obtiene los Servicios que puede surtir el usuario, dependiendo de la
     * idEstructura de la unidad jerarquica por la que consulta el usuario en
     * sesion Adicionalmente El area a la que el usuario este asignada, debe ser
     * de tipo almacen y que tenga una asignación de servicio hospitalario que
     * puede surtir
     *
     * @param idEstructura idetificador único de la estructura
     * @return
     */
    public List<Estructura> obtieneServiciosQuePuedeSurtir(String idEstructura) {
        LOGGER.debug("mx.mc.commons.SolucionUtils.obtieneServiciosQuePuedeSurtir()");
        List<Estructura> estLista = new ArrayList<>();
        String surSinAlmacen = "sur.sin.almacen";

        if (idEstructura == null || idEstructura.trim().isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surSinAlmacen), null);

        } else {
            Estructura estSol = obtenerEstructura(idEstructura);

            if (estSol == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surSinAlmacen), null);

            } else if (estSol.getIdTipoAreaEstructura() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surSinAlmacen), null);

            } else if (!Objects.equals(estSol.getIdTipoAreaEstructura(), TipoAreaEstructura_Enum.ALMACEN.getValue())) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.almacen.incorrectado"), null);

            } else {
                try {
                    for (Estructura e : this.estService.obtenerServicioQueSurtePorIdEstructura(idEstructura)) {
                        estLista.add(e);
                        List<String> idsEstructura = this.estService.obtenerIdsEstructuraJerarquica(e.getIdEstructura(), true);
                        for (String item : idsEstructura) {
                            estLista.add(this.estService.obtenerEstructura(item));
                        }
                    }
                } catch (Exception excp) {
                    LOGGER.error("Error al obtener lista de servicios :: {} ", excp.getMessage());
                }
            }
        }
        return estLista;
    }

    /**
     * Método que Obtiene una estructura por medio del idEstructura si existe
     *
     * @param idEstructura Identificador único de la estructura
     * @return La estructura encontrada
     */
    public Estructura obtenerEstructura(String idEstructura) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.obtenerEstructura()");
        Estructura est = null;
        try {
            est = this.estService.obtener(new Estructura(idEstructura));
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        return est;
    }

    /**
     * Método que busca las surtimientos de prescripción de soluciones o mezclas
     * dependiendo de los diferentes parámetros de búsqeuda
     *
     * @param u
     * @param pu
     * @param estLista
     * @param estPacLista
     * @param estSurLista
     * @param estSolLista
     * @param estPresLista
     * @param tipPreLista
     * @param esSolucion
     * @param parBusRep
     * @return
     */
    public DispensacionSolucionLazy obtenerSurtimientos(Usuario u, PermisoUsuario pu,
            List<Estructura> estLista, List<Integer> estPacLista,
            List<Integer> estSurLista, List<Integer> estSolLista,
            List<Integer> estPresLista, List<String> tipPreLista,
            boolean esSolucion, ParamBusquedaReporte parBusRep, int tipoProceso, boolean agruparMezclaXPrescripcion) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.obtenerSurtimientos()");

        DispensacionSolucionLazy dispensacionSolucionLazy = new DispensacionSolucionLazy();

        boolean status = Constantes.INACTIVO;
        boolean modal = Constantes.INACTIVO;

        if (u == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Usuario inválido. ", null);
            status = Constantes.ACTIVO;

        } else if (u.getIdEstructura() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Usuario inválido. ", null);
            status = Constantes.ACTIVO;

        } else if (!pu.isPuedeVer()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.transaccion"), null);
            status = Constantes.ACTIVO;

        } else if (estLista == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.almacen.incorrectado"), null);

        } else if (estLista.isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.almacen.incorrectado"), null);

        } else {
            try {
                parBusRep.setNuevaBusqueda(true);
                String cadenaBusqueda = parBusRep.getCadenaBusqueda();
                if (cadenaBusqueda != null && cadenaBusqueda.trim().isEmpty()) {
                    cadenaBusqueda = null;
                }
                parBusRep.setCadenaBusqueda(cadenaBusqueda);

                if (tipPreLista != null && tipPreLista.isEmpty()) {
                    tipPreLista = null;
                }

                Date fechaProgramada = new java.util.Date();
                dispensacionSolucionLazy = new DispensacionSolucionLazy(
                        this.surService,
                        parBusRep,
                        fechaProgramada,
                        tipPreLista,
                        estPacLista,
                        estPresLista,
                        estSurLista,
                        estLista,
                        esSolucion,
                        estSolLista,
                        null, tipoProceso, agruparMezclaXPrescripcion
                );

                LOGGER.trace("Resultados: {}", dispensacionSolucionLazy.getTotalReg());
                status = Constantes.ACTIVO;

            } catch (Exception ex) {
                LOGGER.error("Error al obtener surtimientos-soluciones lazy :: {} ", ex.getMessage());
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.pac.lista"), null);
            }
        }
        PrimeFaces.current().ajax().addCallbackParam("modal", modal);
        PrimeFaces.current().ajax().addCallbackParam("estatus", status);
        return dispensacionSolucionLazy;
    }

    public Prescripcion obtenerPrescripcion(Surtimiento_Extend s) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.obtenerPrescripcion()");
        Prescripcion p = null;
        try {
            if (s == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Prescripción inválida", null);

            } else if (s.getIdPrescripcion() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Prescripción inválida", null);

            } else {
                p = this.preService.obtener(new Prescripcion(s.getIdPrescripcion()));
            }
        } catch (Exception e) {
            LOGGER.error("Error al obtener Prescripción :: {} ", e.getMessage());
        }
        return p;
    }

    /**
     * Obtiene solución idSurtimiento
     *
     * @param s
     * @return
     */
    public Solucion obtenerSolucion(Surtimiento_Extend s) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.obtenerSolucion()");
        Solucion sol = null;
        try {
            if (s == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Identificador de mezcla inválido", null);

            } else if (s.getIdSurtimiento() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Identificador de mezcla inválido", null);

            } else {
                SolucionExtended se = new SolucionExtended();
                String idSolucion = null;
                String idSurtimiento =  s.getIdSurtimiento();
                sol = this.solService.obtenerSolucion(idSolucion, idSurtimiento);
                if (sol == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Identificador de mezcla inválido", null);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al obtener Solucion :: {} ", e.getMessage());
        }
        return sol;
    }

    /**
     * Método que obtiene la estabilidad en horas mas proxima de un insumo que
     * forma parte de la mezcla
     *
     * @param surInsLista lista de insumos de la mezcla
     * @return el valor en horas más proximo a vencer
     */
    public Integer evaluaEstabilidaMinima(List<SurtimientoInsumo_Extend> siel, Integer idViaAdmon) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.evaluaEstabilidaMinima()");
        
        Integer estabilidadProxima = 24;
        boolean encontrado = false;
        Estabilidad e = null;
        try {
            SurtimientoInsumo_Extend reactivo = obtenerFarmacoDiluyente(siel, "farmaco");
            SurtimientoInsumo_Extend diluyente = obtenerFarmacoDiluyente(siel, "diluyente");
            
            if (reactivo != null && reactivo.getIdInsumo() != null ) {                
                String idInsumo = reactivo.getIdInsumo();
                String claveDiluyente = diluyente.getClaveInstitucional();
                Integer idViaAdministracion = idViaAdmon;
                Integer idFabricante = null;
                Integer idContenedor = null;  // AQUI CAMBIO
                List<Estabilidad_Extended> estabilidadList = estabiService.buscarEstabilidad(idInsumo , claveDiluyente, idViaAdministracion , idFabricante, idContenedor );
                
                for (Estabilidad_Extended item : estabilidadList) {
                    if (!encontrado) {
    // Si la estabilidad del farmaco registrada contiene vía de administración y coincide con la prescripción
                        if (Objects.equals(item.getIdViaAdministracion(), idViaAdministracion)) {
                            if (item.getReglasDePreparacion() != null) {
                                e = item;
                                encontrado = true;
                                break;
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al buscar estabilidad :: {} ", ex.getMessage());
        }
        
        if (encontrado) {
            if (e.getLimHrsUsoRedFria() != null && e.getLimHrsUsoRedSeca() != null) {
                if (e.getLimHrsUsoRedFria() > 0){
                    estabilidadProxima = e.getLimHrsUsoRedFria();
                }
                if (e.getLimHrsUsoRedSeca()> 0){
                    estabilidadProxima = e.getLimHrsUsoRedSeca();
                }
            }
            
        } else {
            if (siel != null) {
                for (SurtimientoInsumo_Extend si : siel) {
                    if (si.getNoHorasestabilidad() != null) {
                        if (si.getNoHorasestabilidad() > 0) {
                            if (si.getNoHorasestabilidad() < estabilidadProxima) {
                                estabilidadProxima = si.getNoHorasestabilidad();
                            }
                        }
                    }
                }
            }
        }
        return estabilidadProxima;
    }

    /**
     * Evalua si una solució es editable con base enlos permisos y un estatus
     * definido
     *
     * @param s Solucion
     * @param pu Permisos de usuario a la transaccíón
     * @param idEstatusSolucion idEstatus Solución, es el valor que permite
     * editar o no
     * @return valor bolleano
     */
    public boolean evaluaEdicion(Solucion s, PermisoUsuario pu, Integer idEstatusSolucion) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.evaluaEdicion()");
        boolean editable = false;
        if (s != null) {
            if (s.getIdEstatusSolucion() != null) {
                if (pu.isPuedeAutorizar()
                        || ((pu.isPuedeCrear() || pu.isPuedeEditar())
                        && Objects.equals(s.getIdEstatusSolucion(), idEstatusSolucion))) {
                    editable = true;
                }
            }
        }
        return editable;
    }

    /**
     * Devuelve el valor de la clave agrupada
     *
     * @param s
     * @return
     */
    public CodigoInsumo obtenerIdentificacionMezcla(Surtimiento_Extend s) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.obtenerIdentificacionMezcla()");
        if (s != null) {
            if (s.getClaveAgrupada() != null) {
                CodigoInsumo cin = CodigoBarras.parsearCodigoDeBarras(s.getClaveAgrupada());
                if (cin != null) {
                    return cin;
                }
            }
        }
        return null;
    }

    /**
     * Calcula la estabilidad de la mezcla con base en la fecha y las fecha hora
     * de estabilidad mínima del insusmo con menos estabildad
     *
     * @param estabilidadMezcla
     * @param s Solución
     * @return retorna la fecha de estabilidad mínima
     */
    public Date calculaCaducidadMezcla(Integer estabilidadMezcla, Solucion s) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.calculaCaducidadMezcla()");
        Date caducidadMezcla = new Date();
        if (estabilidadMezcla > 0) {
            if (s != null) {
                if (s.getFechaPrepara() != null) {
                    caducidadMezcla = FechaUtil.sumarRestarHorasFecha(s.getFechaPrepara(), estabilidadMezcla);
                } else {
                    caducidadMezcla = FechaUtil.sumarRestarHorasFecha(new java.util.Date(), estabilidadMezcla);
                }
            }
        }
        return caducidadMezcla;
    }

    /**
     * Obtiene los diagnósticos de un paciente relacionados a una transacción de
     * la última visita abierta
     *
     * @param s Surtimiento
     * @return Lista de nombre de surtimientos
     */
    public List<String> obtenerDiagnosticos(Surtimiento_Extend s) {
        LOGGER.error("mx.mc.commons.SolucionUtils.obtenerDiagnosticos()");
        List<String> diagnosticoLista = new ArrayList<>();
        try {
            if (s != null) {
                if (s.getIdPrescripcion() != null) {
                    if (s.getIdPaciente() != null) {
                        Visita v = this.visService.obtenerVisitaAbierta(new Visita(s.getIdPaciente()));
                        if (v != null) {
                            List<Diagnostico> listDiagnostico = this.diaService.obtenerPorIdPacienteIdVisitaIdPrescripcion(
                                    s.getIdPaciente(),
                                    v.getIdVisita(),
                                    s.getIdPrescripcion());
                            if (listDiagnostico != null) {
                                for (Diagnostico d : listDiagnostico) {
                                    diagnosticoLista.add(d.getClave() + " - " + d.getNombre());
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al obtener diagnósticos :: {} ", e.getMessage());
        }
        return diagnosticoLista;
    }

    /**
     * Obtiene Diagnósticos de Paciente
     *
     * @param idPaciente
     * @return
     */
    public List<Diagnostico> obtenerDiagnosticos(String idPaciente) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.obtenerDiagnosticos()");
        List<Diagnostico> diagnosticos = new ArrayList<>();
        try {
            diagnosticos.addAll(this.diaService.obtenerDiagnosticoByIdPaciente(idPaciente));
        } catch (Exception e) {
            LOGGER.error("Error al obtenerDiagnosticos de paciente :: {} ", e.getMessage());
        }
        return diagnosticos;
    }

    /**
     * Obtiene Diagnósticos de Paciente por Visita y Prescripcion
     *
     * @param idPaciente
     * @param idVisita
     * @param idPrescripcion
     * @return
     */
    public List<Diagnostico> obtenerDiagnosticos(String idPaciente, String idVisita, String idPrescripcion) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.obtenerDiagnosticos()");
        List<Diagnostico> diagnosticos = new ArrayList<>();
        try {
            diagnosticos.addAll(this.diaService.obtenerPorIdPacienteIdVisitaIdPrescripcion(
                    idPaciente,
                    idVisita,
                    idPrescripcion));
        } catch (Exception e) {
            LOGGER.error("Error al obtenerDiagnosticos de paciente :: {} ", e.getMessage());
        }
        return diagnosticos;
    }

    /**
     * Genera una cadena de texto con los diagnosticos con base en una lista
     *
     * @param lista
     * @return
     */
    public String diagnosticosEnTexto(List<Diagnostico> lista) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.diagnosticosEnTexto()");
        String diagnosticos = "";
        try {
            if (lista != null) {
                for (Diagnostico d : lista) {
                    diagnosticos = diagnosticos + " | " + d.getNombre();
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error en diagnosticosEnTexto de paciente :: {} ", e.getMessage());
        }
        return diagnosticos;
    }

    /**
     * Obtiene Reacciones de hipersensibiildiad de paciente
     *
     * @param idPaciente
     * @param medicamentos
     * @return
     */
    public List<HipersensibilidadExtended> obtenerReaccHiper(String idPaciente, List<MedicamentoDTO> medicamentos) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.obtenerReaccHiper()");
        List<HipersensibilidadExtended> hipersensibilidades = new ArrayList<>();
        try {
            if (medicamentos != null && !medicamentos.isEmpty()) {
                hipersensibilidades.addAll(this.hipService.obtenerListaReacHiperPorIdPaciente(idPaciente, medicamentos));
            }
        } catch (Exception e) {
            LOGGER.error("Error al obtenerReaccHiper de paciente :: {} ", e.getMessage());
        }
        return hipersensibilidades;
    }

    /**
     * Genera una cadena de las reacciones de hipersensibilidad con base en una
     * lista
     *
     * @param lista
     * @return
     */
    public String reaccHiperEnTexto(List<HipersensibilidadExtended> lista) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.reaccHiperEnTexto()");
        String reacciones = "";
        try {
            if (lista != null) {
                for (HipersensibilidadExtended h : lista) {
                    reacciones = reacciones + " | " + h.getNombreComercial() + ": " + h.getTipoAlergia();
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al reaccHiperEnTexto de paciente :: {} ", e.getMessage());
        }
        return reacciones;
    }

    /**
     * Obtiene Reacciones de adversas de paciente
     *
     * @param idPaciente
     * @param listaInsumos
     * @return
     */
    public List<ReaccionExtend> obteneReacciones(String idPaciente, List<String> listaInsumos) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.obteneReacciones()");
        List<ReaccionExtend> reacciones = new ArrayList<>();
        try {
            reacciones.addAll(this.reaService.obtenerReaccionesByIdPacienteIdInsumos(idPaciente, listaInsumos));
        } catch (Exception e) {
            LOGGER.error("Error al obteneReacciones de paciente :: {} ", e.getMessage());
        }
        return reacciones;
    }

    /**
     * Genera una cadena de las reacciones de hipersensibilidad con base en una
     * lista
     *
     * @param lista
     * @return
     */
    public String reaccionesEnTexto(List<ReaccionExtend> lista) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.reaccionesEnTexto()");
        String reacciones = "";
        try {
            if (lista != null) {
                for (ReaccionExtend r : lista) {
                    reacciones = reacciones + " | " + r.getMedicamento() + ": " + r.getTipo();
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al reaccionesEnTexto de paciente :: {} ", e.getMessage());
        }
        return reacciones;
    }

    /**
     * Retorna el objeto del tipo de solución mediante busqueda por Id
     *
     * @param idTiposolucion
     * @return
     */
    public TipoSolucion obtenerTipoSolucion(String idTiposolucion) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.obtenerTipoSolucion()");
        TipoSolucion ts = null;
        try {
            if (idTiposolucion != null && !idTiposolucion.trim().isEmpty()) {
                ts = this.tipSolService.obtener(new TipoSolucion(idTiposolucion));
            }
        } catch (Exception e) {
            LOGGER.error("Error al obtener tipo de Solucion :: {} ", e.getMessage());
        }
        return ts;
    }

    /**
     * Retorna el objeto de la vía de admiistración con base en el ID
     *
     * @param idViaAdministracion
     * @return
     */
    public ViaAdministracion obtenerViaAdministracion(Integer idViaAdministracion) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.obtenerViaAdministracion()");
        ViaAdministracion va  = null;
        try {
            va  = this.viaAdmService.obtenerPorId(idViaAdministracion);
        } catch (Exception e) {
            LOGGER.error("Error al obtener vías de admiistración :: {}", e.getMessage());
        }
        return va;
    }

    /**
     * Obtiene el envase contenedor con base en el idEnvase contenedor
     *
     * @param idEnvaseContenedor identificador único del envase contenedor
     * @return objeto buscado
     */
    public EnvaseContenedor obtenerContenedor(Integer idEnvaseContenedor) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.obtenerContenedor()");
        EnvaseContenedor ec = null;
        try {
            if (idEnvaseContenedor != null && idEnvaseContenedor > 0) {
                ec = this.envConService.obtenerXidEnvase(idEnvaseContenedor);
            }
        } catch (Exception e) {
            LOGGER.error("Error al obtener contenedor :: {} ", e.getMessage());
        }
        return ec;
    }

    /**
     * Obtiene el protocolo con base en su identificador
     *
     * @param idProtocolo identificador unido del protocolo
     * @return objeto buscado
     */
    public Protocolo obtenerProtocolo(Integer idProtocolo) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.obtenerProtocolo()");
        Protocolo p = null;
        try {
            if (idProtocolo != null) {
                p = proService.obtener(new Protocolo(idProtocolo));
            }
        } catch (Exception e) {
            LOGGER.error("Error obtener Protocolo :: {} ", e.getMessage());
        }
        return p;
    }

    /**
     * Obtiene el diagnostico con base en su identificador
     *
     * @param idDiagnostico identificador único del protocolo
     * @return objeto deseado
     */
    public Diagnostico obtenerDiagnostico(String idDiagnostico) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.obtenerDiagnostico()");
        Diagnostico d = null;
        try {
            if (idDiagnostico != null && !idDiagnostico.trim().isEmpty()) {
                d = diaService.obtenerDiagnosticoPorIdDiag(idDiagnostico);
            }
        } catch (Exception e) {
            LOGGER.error("Error al obtener diagnóstico :: {}", e.getMessage());
        }
        return d;
    }

    /**
     * Obtiene los datos requeridos por el reporte
     *
     * @param s Surtimiento de la mezcla
     * @param u Usurio que realiza el movimiento
     * @return el objeto con los datos del reporte
     */
    public RepSurtimientoPresc obtenerDatosReporte(Surtimiento_Extend s, Usuario u) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.obtenerDatosReporte()");
        RepSurtimientoPresc repSurPres = null;
        try {

            Estructura e = this.estService.obtenerEstructura(s.getIdEstructura());
            Surtimiento surt = this.surService.obtenerPorFolio(s.getFolio());
            Estructura valorServicio = this.estService.obtenerEstructura(u.getIdEstructura());

            if (e == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Área solicitante de mezcla inválida. ", null);

            } else if (e.getIdEntidadHospitalaria() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Entidad hospitalaria inválida. ", null);

            } else if (surt == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Surtimiento de mezcla inválido. ", null);

            } else if (valorServicio == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Surtimiento de mezcla inválido. ", null);

            } else {

                EntidadHospitalaria eh = this.entHosService.obtenerEntidadById(e.getIdEntidadHospitalaria());
                if (eh == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Entidad hospitalaria inválida. ", null);

                } else {
                    repSurPres = new RepSurtimientoPresc();
                    repSurPres.setClasificacionPresupuestal(e.getClavePresupuestal() != null ? e.getClavePresupuestal() : "");
                    repSurPres.setPiso(e.getUbicacion());
                    repSurPres.setUnidadHospitalaria(e.getNombre());
                    repSurPres.setClasificacionPresupuestal(e.getClavePresupuestal());
                    repSurPres.setFechaSolicitado(surt.getFechaProgramada());
                    repSurPres.setNombreCopia(valorServicio.getNombre());
                    repSurPres.setFolioPrescripcion(s.getFolioPrescripcion());
                    repSurPres.setFolioSurtimiento(s.getFolio());
                    repSurPres.setFechaActual(new Date());
                    repSurPres.setNombrePaciente(s.getNombrePaciente());
                    repSurPres.setClavePaciente(s.getClaveDerechohabiencia());
                    repSurPres.setServicio(s.getNombreEstructura());
                    repSurPres.setCama(s.getIdCama());
                    repSurPres.setTurno(s.getTurno());
                    repSurPres.setIdEstatusSurtimiento(s.getIdEstatusSurtimiento());

                    List<SurtimientoInsumo> surInsLista = this.surInsService.obtenerLista(new SurtimientoInsumo(surt.getIdSurtimiento()));
                    if (surInsLista != null && !surInsLista.isEmpty()) {
                        SurtimientoInsumo surIns = surInsLista.get(0);
                        repSurPres.setFechaAtendido(surIns.getFechaEnviada());
                    }
                }
            }

        } catch (Exception exc) {
            LOGGER.error("Error al obtener datos del reporte :: {} ", exc.getMessage());
        }
        return repSurPres;
    }

    private String eliminaCarEspe(String texto) {
        texto = Normalizer.normalize(texto, Normalizer.Form.NFD);
        texto = texto.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return texto;
    }

    /**
     * Obtiene la lista de Camas co la opción de obtener todas o sólo las de un
     * servivio
     *
     * @param idServicio identificador del area o servicio de donde se desean
     * las camas
     * @return lista de camas
     */
    public List<CamaExtended> obtenerListaCamas(String idServicio) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.obtenerListaCamas()");
        List<CamaExtended> camaLista = new ArrayList<>();
        try {
            List<Integer> estatusCamaLista = new ArrayList<>();
            estatusCamaLista.add(EstatusCama_Enum.DISPONIBLE.getValue());
            estatusCamaLista.add(EstatusCama_Enum.OCUPADA.getValue());
            camaLista.addAll(this.camService.obtenerCamaByEstructuraAndEstatus(idServicio, estatusCamaLista));
        } catch (Exception ex) {
            LOGGER.error("Error al obtener la lista de camas :: {} ", ex.getMessage());
        }
        return camaLista;
    }

    /**
     * Obtiene la lista de envasesContenedores
     *
     * @return lista de envases
     */
    public List<EnvaseContenedor> obtenerListaEnvases() {
        LOGGER.trace("mx.mc.commons.SolucionUtils.obtenerListaEnvases()");
        List<EnvaseContenedor> envaseList = new ArrayList<>();
        try {
            envaseList.addAll(this.envConService.obtenerLista(new EnvaseContenedor()));
        } catch (Exception ex) {
            LOGGER.error("Error al obtener la lista de envases :: {} ", ex.getMessage());
        }
        return envaseList;
    }

    /**
     * obtiene lista de vía de admiistración con base en el ID
     *
     * @return La lista de las vias
     */
    public List<ViaAdministracion> obtenerListaViaAdministracion() {
        LOGGER.trace("mx.mc.commons.SolucionUtils.obtenerListaViaAdministracion()");
        List<ViaAdministracion> vaLista = new ArrayList<>();
        try {
            vaLista.addAll(this.viaAdmService.obtenerTodo());
        } catch (Exception e) {
            LOGGER.error("Error al obtener la lista de Vías de Administración :: {} ", e.getMessage());
        }
        return vaLista;
    }

    /**
     * Obtiene la lista de protocolos
     *
     * @return la lista de protocolos
     */
    public List<Protocolo> obtenerProtocolos() {
        LOGGER.trace("mx.mc.commons.SolucionUtils.obtenerProtocolos()");
        List<Protocolo> listaProtocolos = new ArrayList<>();
        try {
            Protocolo p = new Protocolo();
            p.setIdEstatus(1);
            listaProtocolos.addAll(this.proService.obtenerLista(p));
        } catch (Exception ex) {
            LOGGER.error("Error al obtener la lista de protocolos :: {} ", ex.getMessage());
        }
        return listaProtocolos;
    }

    /**
     * Obtiene los tipos de soluciones o mezclas
     *
     * @param listaClaves
     * @return La lista de soluciones
     */
    public List<TipoSolucion> obtenerListaTiposSolucion(List<String> listaClaves) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.obtenerTiposSolucion()");
        List<TipoSolucion> tipoSolucionList = new ArrayList<>();
        try {
            tipoSolucionList.addAll(this.tipSolService.obtenerByListaClaves(listaClaves));
        } catch (Exception ex) {
            LOGGER.error("Error al obtener la lista de tipos de soluciones :: {} ", ex.getMessage());
        }
        return tipoSolucionList;
    }

    /**
     * Obtiene la lista de frecuencias
     *
     * @return lista de frecuencios
     */
    public List<Frecuencia> obtenerListaFrecuencias() {
        LOGGER.debug("mx.mc.magedbean.SolucionUtilsMB.obtenerListaFrecuencias()");
        List<Frecuencia> listaFrecuencias = new ArrayList<>();
        try {
            listaFrecuencias.addAll(this.freService.obtenerLista(new Frecuencia()));
        } catch (Exception ex) {
            LOGGER.error("Error al obtener la lista de frecuencias :: {} ", ex.getMessage());
        }
        return listaFrecuencias;
    }

    /**
     * Obtiene lista de tipos de pacientes
     *
     * @return lista de tipos de pacientes
     */
    public List<CatalogoGeneral> obtenerListaTipoPacientes() {
        LOGGER.trace("mx.mc.commons.SolucionUtils.obtenerListaTipoPacientes()");
        List<CatalogoGeneral> listaTipoPacientes = new ArrayList<>();
        try {
            listaTipoPacientes.addAll(this.catGenService.obtenerCatalogosPorGrupo(GrupoCatalogoGeneral_Enum.TIPO_DE_PACIENTE.getValue()));
        } catch (Exception ex) {
            LOGGER.error("Error al obtener la lista de tipos de paciente :: {} ", ex.getMessage());
        }
        return listaTipoPacientes;
    }

    /**
     * Obtiene la lista de turnos
     *
     * @return lista de turnos encontrada
     */
    public List<Turno> obtenerListaTurnos() {
        LOGGER.trace("mx.mc.commons.SolucionUtils.obtenerListaTurnos()");
        List<Turno> listaTurnos = new ArrayList<>();
        try {
            listaTurnos.addAll(turService.obtenerLista(new Turno()));
        } catch (Exception exp) {
            LOGGER.error("Error al obtener la lista de turnos :: {} ", exp.getMessage());
        }
        return listaTurnos;
    }

    public List<TipoUsuario> obtenerListaTipoUsuario() {
        LOGGER.trace("mx.mc.commons.SolucionUtils.obtenerListaTipoUsuario()");
        List<TipoUsuario> tipoUsarioLista = new ArrayList<>();
        try {
            tipoUsarioLista.addAll(this.tipUsuService.obtenerLista(new TipoUsuario()));
        } catch (Exception exp) {
            LOGGER.error("Error al obtner lista de tipos de usuario :: {} ", exp.getMessage());
        }
        return tipoUsarioLista;
    }

    /**
     * Permite una gestión rápida de cama o pacienteUbicacion, en cada nueva
     * prescripcion u orden de solucion, si no existe una cama seleccionada y
     * asignada en el servicio elegido, se agrega una nueva asignación de cama
     * en automático en el nuevo servicio seleccionado Autor: hhrc Fecha:
     * 28dic2022
     *
     * @param idPacienteServicio
     * @param idUsuario
     * @param idCama
     * @param idPaciente
     * @return
     */
    public PacienteUbicacion recuperaPacienteUbicacion(String idPacienteServicio, String idUsuario, String idCama, String idPaciente) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.recuperaPacienteUbicacion()");
        PacienteUbicacion pu = null;
        try {
            if (idPacienteServicio != null && !idPacienteServicio.trim().isEmpty()
                    && idCama != null && !idCama.trim().isEmpty() ){
                pu = new PacienteUbicacion();
                pu.setIdPacienteServicio(idPacienteServicio);
                pu.setIdCama(idCama);
                pu = this.pacUbiService.obtenerCamaAsignada(pu);
            }
            if (pu == null) {
                Date fecha = new java.util.Date();
                boolean res = pacUbiService.cierraAsigCamaAbiertas(fecha, idUsuario, idPaciente);
// TODO: verificar si se mete en una transacción
//                if (res1) {
                pu = new PacienteUbicacion();
                pu.setIdPacienteUbicacion(Comunes.getUUID());
                pu.setIdPacienteServicio(idPacienteServicio);
                pu.setIdCama(idCama);
                pu.setFechaUbicaInicio(new java.util.Date());
                pu.setIdUsuarioUbicaInicio(idUsuario);
                pu.setIdEstatusUbicacion(2);
                pu.setInsertFecha(new java.util.Date());
                pu.setInsertIdUsuario(idUsuario);
                res = this.pacUbiService.insertar(pu);
                if (!res) {
                    pu = null;
                }
//                }
            }
            if (pu != null) {
                Paciente paciente = new Paciente();
                paciente.setUpdateFecha(new java.util.Date());
                paciente.setUpdateIdUsuario(idUsuario);
                paciente.setIdPaciente(idPaciente);
                paciente.setIdEstatusPaciente(EstatusPaciente_Enum.ASIGNADO_A_CAMA.getValue());
                boolean act = this.pacService.actualizar(paciente);
                if (!act) {
                    pu = null;
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al obtener la cama asignada de paciente :: {} ", e.getMessage());
        }
        return pu;
    }

    /**
     * Permite una gestión rápida de asignación de servicio o PacienteServicio,
     * si no existe, se agrega en automático. La lógica es que en cada nuevo
     * registro de prescripción, si diferente del preregistrado se agrega nuevo
     * servicio y se cierran los anteriores Autor: hhrc Fecha: 28dic2022
     *
     * @param idVisita
     * @param idEstructura
     * @param idUsuario
     * @param idMedico
     * @return
     */
    public PacienteServicio recuperaPacienteServicio(String idVisita, String idEstructura, String idUsuario, String idMedico, String idPaciente) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.recuperaPacienteServicio()");
        PacienteServicio ps = null;
        try {
            if (idVisita != null && !idVisita.trim().isEmpty()
                    && idEstructura != null && !idEstructura.trim().isEmpty()) {
                ps = new PacienteServicio();
                ps.setIdVisita(idVisita);
                ps.setIdEstructura(idEstructura);
                ps = this.pacSerService.obtenerPacienteServicioAbierto(ps);
            }
            if (ps == null) {
                Date fechaAsignacionFin = new java.util.Date();
                Integer idMotivoPacienteMovimiento = MotivoPacienteMovimiento_Enum.TRANS_SERV.getValue();
                String idUsuarioAsignacionFin = idUsuario;
                Date updateFecha = new java.util.Date();
                String updateIdUsuario = idUsuario;
                this.pacSerService.cierraAsignacionesAbiertas(
                        fechaAsignacionFin,
                         idMotivoPacienteMovimiento,
                         idUsuarioAsignacionFin,
                         updateFecha,
                         updateIdUsuario,
                         idPaciente);
//                PacienteServicio psTmp = new PacienteServicio();
//                psTmp.setIdVisita(idVisita);
//                psTmp = this.pacSerService.obtenerPacienteServicioAbierto(psTmp);
//                if (psTmp != null) {
//                    psTmp.setUpdateFecha(new java.util.Date());
//                    psTmp.setUpdateIdUsuario(idUsuario);
//                    psTmp.setFechaAsignacionFin(new java.util.Date());
//                    psTmp.setIdUsuarioAsignacionFin(idUsuario);
//                    psTmp.setIdMotivoPacienteMovimiento(MotivoPacienteMovimiento_Enum.TRANS_SERV.getValue());
//                    psTmp.setJustificacion("Transfer");
//                    this.pacSerService.cerrarAsignacionesporIdVisita(psTmp);
//                }
// TODO: verificar si se mete en una transacción
                ps = new PacienteServicio();
                ps.setIdPacienteServicio(Comunes.getUUID());
                ps.setIdVisita(idVisita);
                ps.setIdEstructura(idEstructura);
                ps.setFechaAsignacionInicio(new java.util.Date());
                ps.setIdUsuarioAsignacionInicio(idUsuario);
                ps.setIdMotivoPacienteMovimiento(MotivoPacienteMovimiento_Enum.TRANS_SERV.getValue());
                ps.setIdMedico(idMedico);
                ps.setInsertFecha(new java.util.Date());
                ps.setInsertIdUsuario(idUsuario);
                boolean res = this.pacSerService.insertar(ps);
                if (!res) {
                    ps = null;
                }
            }
            if (ps != null) {
                Paciente paciente = new Paciente();
                paciente.setUpdateFecha(new java.util.Date());
                paciente.setUpdateIdUsuario(idUsuario);
                paciente.setIdPaciente(idPaciente);
                paciente.setIdEstructura(idEstructura);
                paciente.setIdEstatusPaciente(EstatusPaciente_Enum.ASIGNADO_A_SERVICIO.getValue());
                boolean act = this.pacService.actualizar(paciente);
                if (!act) {
                    ps = null;
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al recuperar la asignación de servicio a paciete :: {} ", e.getMessage());
        }
        return ps;
    }

    /**
     * Permite una gestión rápida de visita, en cada nueva prescripcion u orden
     * de solucion si no existe una visita de paciente se agrega en automático
     * una nueva visita Autor: hhrc Fecha: 28dic2022
     *
     * @param idPaciente
     * @param idUsuario
     * @return
     */
    public Visita recuperaVisita(String idPaciente, String idUsuario) {
        LOGGER.trace("mx.mc.magedbean.SolucionUtilsMB.obtieneVisita()");
        Visita v = null;
        try {
            if (idPaciente!= null && !idPaciente.trim().isEmpty()){
                v = new Visita();
                v.setIdPaciente(idPaciente);
                v = this.visService.obtenerVisitaAbierta(v);
            }
            if (v == null) {
                //todo: 28dic2022 pendiente la gestión de cierre de visita
                v = new Visita();
                v.setIdVisita(Comunes.getUUID());
                v.setIdPaciente(idPaciente);
                v.setFechaIngreso(new java.util.Date());
                v.setIdUsuarioIngresa(idUsuario);
                v.setMotivoConsulta(" ");
                v.setIdMotivoPacienteMovimiento(MotivoPacienteMovimiento_Enum.ADMISION.getValue());
                v.setTipoVisita("1");
                v.setNumVisita(FechaUtil.formatoCadena(new java.util.Date(), "yyMMddhhmm"));
                v.setInsertFecha(new java.util.Date());
                v.setInsertIdUsuario(idUsuario);
                boolean res = this.visService.insertar(v);
                if (!res) {
                    v = null;
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al recuperar la visita asignada a paciente :: {} ", e.getMessage());
        }
        return v;
    }

    /**
     * Obtiene la visita de paciente registrada y relacionada a una prescripcion
     *
     * @param idVisita identificado de visita
     * @return el objeto de visita encontrado
     */
    public Visita obtenerVisitaRegistrada(String idVisita) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.obtenerVisitaRegistrada()");
        Visita o = null;
        try {
            if (idVisita != null && !idVisita.trim().isEmpty()) {
                Visita v = new Visita();
                v.setIdVisita(idVisita);
                o = visService.obtener(v);
            }
        } catch (Exception e) {
            LOGGER.error("Error al obtener la visita registrada de paciente :: {}", e.getMessage());
        }
        return o;
    }

    /**
     * Obtiene el registro de asignacio a servicio registrado en una
     * prescripcion
     *
     * @param idPacienteServicio id asignacion a servicio en prescripcion
     * @return paciente servicio registrado
     */
    public PacienteServicio obtenerServicioRegistrada(String idPacienteServicio) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.obtenerServicioRegistrada()");
        PacienteServicio o = null;
        try {
            if (idPacienteServicio != null && !idPacienteServicio.trim().isEmpty()) {
                PacienteServicio ps = new PacienteServicio();
                ps.setIdPacienteServicio(idPacienteServicio);
                o = this.pacSerService.obtener(ps);
            }
        } catch (Exception e) {
            LOGGER.error("Error al obtener Servicio Registrado :: {}", e.getMessage());
        }
        return o;
    }

    /**
     * Obtiene la asignacion de cama de un paciente asociado a una prescripcion
     *
     * @param idPacienteUbicacion identificado de la asignacion de ubicacion o
     * cama
     * @return ubicacion registrada
     */
    public PacienteUbicacion obtenerUbicacionRegistrada(String idPacienteUbicacion) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.obtenerUbicacionRegistrada()");
        PacienteUbicacion o = null;
        try {
            if (idPacienteUbicacion != null && !idPacienteUbicacion.trim().isEmpty()) {
                PacienteUbicacion pu = new PacienteUbicacion();
                pu.setIdPacienteUbicacion(idPacienteUbicacion);
                o = this.pacUbiService.obtener(pu);
            }
        } catch (Exception e) {
            LOGGER.error("Error al obtener la ubicación Registrada de Paciente :: {} ", e.getMessage());
        }
        return o;
    }

    /**
     * Obtiene el registro de solución con base en los parámetros
     *
     * @param idSolucion
     * @param idSurtimiento
     * @return
     */
    public Solucion obtenerSolucion(String idSolucion, String idSurtimiento) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.obtenerSolucion()");
        Solucion o = null;
        try {
            o = this.solService.obtenerSolucion(idSolucion, idSurtimiento);
        } catch (Exception e) {
            LOGGER.error("Error al obtener la solución :: {} ", e.getMessage());
        }
        return o;
    }

    /**
     * Llena un objeto de surtimiento con datos de la solucion
     *
     * @param su
     * @return
     */
    public Surtimiento_Extend llenarSurtimientoConSolucion(Surtimiento_Extend su) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.llenarSurtimientoConSolucion()");
        try {
            String idSolucion = null;
            if (su != null) {
                Solucion s = obtenerSolucion(idSolucion, su.getIdSurtimiento());
                if (s != null) {
                    su.setIdContenedor(s.getIdEnvaseContenedor());
                    su.setObservaciones(s.getObservaciones());
                    su.setInstruccionPreparacion(s.getInstruccionesPreparacion());
                    su.setEdadPaciente(s.getEdadPaciente());
                    su.setPesoPaciente(s.getPesoPaciente());
                    su.setTallaPaciente(s.getTallaPaciente());
                    su.setAreaCorporal(s.getAreaCorporal());
                    su.setPerfusionContinua((s.getPerfusionContinua() != null && s.getPerfusionContinua() != 0));
                    su.setVelocidad(s.getVelocidad());
                    su.setRitmo(s.getRitmo());
                    su.setIdProtocolo(s.getIdProtocolo());
                    su.setIdDiagnostico(s.getIdDiagnostico());
                    su.setVolumenTotal((s.getVolumenFinal() == null || s.getVolumenFinal().trim().isEmpty()) ? BigDecimal.ZERO : new BigDecimal(s.getVolumenFinal()));
                    su.setUnidadConcentracion(s.getUnidadConcentracion());
                    su.setIdEstatusSolucion(s.getIdEstatusSolucion());
                    su.setEstatusSolucion(EstatusSolucion_Enum.getStatusFromId(s.getIdEstatusSolucion()).name().replace("_", " "));
                    su.setFechaParaEntregar(s.getFechaParaEntregar());
                    su.setProteccionLuz((s.getProteccionLuz() == 1));
                    su.setTempAmbiente((s.getProteccionTemp() == 1));
                    su.setTempRefrigeracion((s.getProteccionTempRefrig() == 1));
                    su.setNoAgitar((s.getNoAgitar() == 1));
                    su.setComentariosRechazo(s.getComentariosRechazo());
                    su.setIdUsuarioRechaza(s.getIdUsuarioRechaza());
                    su.setFechaRechazo(s.getFechaValPrescr());
                    su.setIndicaciones(s.getIndicaciones());
                    if(s.getIdUsuarioAutoriza() != null) {
                        Usuario userAutoriza = usuService.obtenerUsuarioByIdUsuario(s.getIdUsuarioAutoriza());
                        su.setIdUsuarioAutoriza(s.getIdUsuarioAutoriza());
                        su.setUsuarioAutoriza(userAutoriza.getNombre() + " " + userAutoriza.getApellidoPaterno() + " " + userAutoriza.getApellidoMaterno());
                    }             
                    su.setFechaAutoriza(s.getFechaAutoriza());
                    su.setComentariosAutoriza(s.getComentarioAutoriza());
                    if (s.getIdEstatusSolucion() != null) {
                        if (Objects.equals(s.getIdEstatusSolucion(), EstatusSolucion_Enum.PRESCRIPCION_VALIDADA.getValue())
                                || Objects.equals(s.getIdEstatusSolucion(), EstatusSolucion_Enum.PRESCRIPCION_RECHAZADA.getValue())
                                || Objects.equals(s.getIdEstatusSolucion(), EstatusSolucion_Enum.OP_RECHAZADA.getValue()) ) {
                            su.setIndicaciones("");
                            su.setInstruccionPreparacion("");
                            su.setProteccionLuz(false);
                            su.setTempAmbiente(false);
                            su.setTempRefrigeracion(false);
                            su.setNoAgitar(false);
                        }
                    }
                    if(s.getMinutosInfusion() > 0) {
                        int hrs = s.getMinutosInfusion() / 60;
                        int min = s.getMinutosInfusion() - (hrs * 60);
                        String hora = String.valueOf(hrs);                        
                        if(hora.length() <= 1) {
                            hora = "00" + hora ;
                        } else {
                            if(hora.length() <= 2) {
                                hora = "0" + hora ;
                            }
                        }
//                        }
                        String minutos = String.valueOf(min);
                        if(minutos.equals("0") || minutos.length() <= 1) {
                            minutos =  "0" + minutos;
                        } 
                        String horaInfusion = hora + ":" + minutos;                        
                        
                        //Calendar cal = Calendar.getInstance();
                        //cal.set(Calendar.HOUR, hrs);
                        //cal.set(Calendar.HOUR_OF_DAY, hrs);
                        //cal.set(Calendar.MINUTE, min);
                        //su.setHorasInfusion(cal.getTime());
//                        su.setHorasInfusion(horaInfusion);
//                    }
                    
//                }
//                        String minutos = String.valueOf(min);
//                        if(minutos.equals("0") || minutos.length() <= 1) {
//                            minutos =  "0" + minutos;
//            }
//                        String horaInfusion = hora + ":" + minutos;                        
                        
                        //Calendar cal = Calendar.getInstance();
                        //cal.set(Calendar.HOUR, hrs);
                        //cal.set(Calendar.HOUR_OF_DAY, hrs);
                        //cal.set(Calendar.MINUTE, min);
                        //su.setHorasInfusion(cal.getTime());
                        su.setHorasInfusion(horaInfusion);
                    }                    
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al llenar Surtimiento :: {} ", e.getMessage());
        }
        return su;
    }

    /**
     * * Valida los datos mínimos mandatorios de la orden
     *
     * @param p
     * @param s
     * @param m
     * @param pa
     * @param ld
     * @param vad
     * @param isOncologica
     * @param protocolo
     * @param d
     * @param listaInsumos
     * @param idTipoSol
     * @return
     */
    public String validaOrden(Prescripcion p, Surtimiento_Extend s, Usuario m,
            Paciente_Extended pa, List<Diagnostico> ld, ViaAdministracion vad,
            boolean isOncologica, String protocolo, Diagnostico d,
            List<SurtimientoInsumo_Extend> listaInsumos, String idTipoSol) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.validaOrden()");

        String res = null;
        if (s == null) {
            res = "Mezcla inválida, capture información requerida. ";

        } else if (s.getIdSurtimiento() == null || s.getIdSurtimiento().trim().isEmpty()) {
            res = "Mezcla inválida, capture información requerida. ";

        } else if (s.getFechaProgramada() == null) {
            res = "Fecha programada requerida. ";

        } else if (s.getTipoPrescripcion() == null) {
            res = "Tipo de prescripción requerida. ";

        } else if (s.getIdEstructura() == null || s.getIdEstructura().trim().isEmpty()) {
            res = "Seleccione servicio. ";

        } else if (s.getIdCama() == null || s.getIdCama().trim().isEmpty()) {
            res = "Seleccione cama. ";

        } else if (m == null) {
            res = "Médico Requerido. ";

        } else if (m.getIdUsuario() == null || m.getIdUsuario().trim().isEmpty()) {
            res = "Médico Requerido. ";

        } else if (s.getTipoConsulta() == null) {
            res = "Tipo de consulta requerida. ";

        } else if (ld == null) {
            res = "Al menos 1 diagnóstico es requerido. ";

        } else if (ld.isEmpty()) {
            res = "Al menos 1 diagnóstico es requerido. ";

        } else if (idTipoSol == null) {
            res = "Tipo de solucion requerido. ";

        } else if (vad == null) {
            res = "Seleccione vía de administración. ";

        } else if (vad.getIdViaAdministracion() == null || vad.getIdViaAdministracion() <= 0) {
            res = "Seleccione vía de administración. ";

        } else if (s.getVolumenTotal() == null || s.getVolumenTotal().compareTo(BigDecimal.ZERO) != 1 ) {
            res = "Capture volumen total. ";

        } else if (s.getIdContenedor() == null) {
            res = "Seleccione contenedor. ";
        
        } else if (s.getHorasInfusion() == null || s.getHorasInfusion().trim().equals(StringUtils.EMPTY)) {
            res = "Capture tiempo de Infusión. ";

        } else if (s.isPerfusionContinua() && (s.getVelocidad() == null || s.getVelocidad() <= 0d) ) {
            res = "Capture velocidad. ";

        } //      else if (s.isPerfusionContinua() && s.getRitmo() <= 0d) {
        //          res = "El ritmo es requerido";
        //      }
        else if (isOncologica && (protocolo == null || protocolo.trim().isEmpty())) {
            res = "Protocolo requerido";
            
// TODO : corregir la validación de protocolo
//        } else if (isOncologica && protocolo != null && !protocolo.trim().isEmpty() && d == null) {
//            res = "Diagnóstico de protocolo requerido";
//        } else if (isOncologica && protocolo != null && !protocolo.trim().isEmpty() && (d.getIdDiagnostico() == null || d.getIdDiagnostico().trim().isEmpty())) {
//            res = "Diagnóstico de protocolo requerido";

        } else if (listaInsumos == null) {
            res = "Se requiere almenos un insumo";

        } else if (listaInsumos.isEmpty()) {
            res = "Se requiere almenos un insumo";

        } else if (pa == null) {
            res = "Paciente requerido. ";

        } else if (pa.getIdPaciente() == null || pa.getIdPaciente().trim().isEmpty()) {
            res = "Paciente requerido. ";

        } else {
            Paciente_Extended pe = null;
            try {
                pe = this.pacService.obtenerPacienteCompletoPorId(pa.getIdPaciente());
            } catch (Exception e) {
                LOGGER.trace("Error al validar paciente :: {} ", e.getMessage());
                res = "Paciente inválido.";
            }

            if (pe == null) {
                res = "Paciente inválido.";
            }

            if (res == null) {
                res = validaNumeroDecimal("Edad Paciente", s.getEdadPaciente(), 120, 2);
            }

            if (res == null) {
                res = validaNumeroDecimal("Peso Paciente", s.getPesoPaciente(), 180, 2);
            }

            if (res == null) {
                res = validaNumeroDecimal("Estatura Paciente", s.getTallaPaciente(), 3, 2);
            }

            if (res == null) {
                res = validaNumeroDecimal("Superficie Corporal", s.getAreaCorporal(), 6, 2);
            }
            
            if (res == null) {
                if (s.getVelocidad() != null){
                    res = validaNumeroDecimal("Volumen total", s.getVelocidad(), 9999, 2);
                }
            }

//            if (res == null && s.getIdHorarioParaEntregar() == null) {
//                res = validaNumeroDecimal("Horario entrega invalido", s.getAreaCorporal(), 2, 2);
//            }
//
//            if (res == null && s.getFechaParaEntregar() == null) {
//                res = validaNumeroDecimal("Fecha de entrega invalida", s.getAreaCorporal(), 2, 2);
//            }

        }

        return res;
    }

    /**
     * Valida la cantidad de enteros y el máximo valor permitido
     * @param campo
     * @param num
     * @param maximoValor
     * @param numDecimales
     * @return
     */
    public String validaNumeroDecimal(String campo, double num, int maximoValor, int numDecimales) {
        String res = null;
        if (num < 0d) {
            res = campo + " debe ser positivo ";
        } else if (num > 0d) {
            BigDecimal n = BigDecimal.valueOf(num);
            if (n.scale() > numDecimales) {
                res = campo + " permite " + numDecimales + " decimales ";
            } else {
                if (n.intValue() > maximoValor) {
                    res = campo + " permite " + maximoValor + " como valor máximo ";
                }
            }
        }

        return res;
    }

    /**
     * Obtiene los almacenes (Centro de Mezclas) que pueden surtir soluciones
     *
     * @param u
     * @return
     */
    public List<Estructura> obtieneAreasPorTipo(Usuario u) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.obtieneAreasPorTipo()");
        List<Estructura> listaEstructuras = new ArrayList<>();

        if (u == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Usuario inválido. ", null);

        } else if (u.getIdUsuario() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Usuario inválido. ", null);

        } else if (u.getIdEstructura() == null || u.getIdEstructura().trim().isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Área de usuario inválida. ", null);

        } else {
            try {
                Estructura estSol = this.estService.obtener(new Estructura(u.getIdEstructura()));
                if (estSol == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Área de usuario inválida. ", null);

                } else {
                    List<Integer> tipoAreaEstructuraLista = new ArrayList<>();
                    tipoAreaEstructuraLista.add(TipoAreaEstructura_Enum.CONSULTAEXTERNA.getValue());
                    tipoAreaEstructuraLista.add(TipoAreaEstructura_Enum.CONSULTAINTERNA.getValue());
                    tipoAreaEstructuraLista.add(TipoAreaEstructura_Enum.ESPECIALIDAD.getValue());
                    tipoAreaEstructuraLista.add(TipoAreaEstructura_Enum.AREA.getValue());
                    tipoAreaEstructuraLista.add(TipoAreaEstructura_Enum.SERVICIO.getValue());
                    tipoAreaEstructuraLista.add(TipoAreaEstructura_Enum.ALA.getValue());
                    tipoAreaEstructuraLista.add(TipoAreaEstructura_Enum.PABELLO.getValue());
                    listaEstructuras.addAll(this.estService.getEstructuraByLisTipoAreaEstructura(tipoAreaEstructuraLista));
                }
            } catch (Exception ex) {
                LOGGER.error("Error al estructuras por tipo de área :: {} ", ex.getMessage());
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al estructuras por tipo de área. ", null);
            }

        }
        return listaEstructuras;
    }

    /**
     *
     * @param dest
     * @param remit
     * @param asunto
     * @param msj
     * @param se
     * @return
     */
    public boolean enviarCorreo(Usuario dest, Usuario remit, String asunto, String msj, Surtimiento_Extend se) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.enviarCorreo()");

        boolean res = Constantes.INACTIVO;
        try {
            asunto = (asunto == null) ? " " : asunto;
            msj = (msj == null) ? " " : msj;
            String correoRemitente = null;
            List<String> destinatarios = new ArrayList<>();
            List<String> copiara = new ArrayList<>();
            
            
            if (dest != null) {
                if (ValidaUtil.correoValido(dest.getCorreoElectronico().trim())) {
                    destinatarios.add(dest.getCorreoElectronico());
                }
            }
            
            if (remit != null) {
                if (ValidaUtil.correoValido(remit.getCorreoElectronico())){
                    correoRemitente = remit.getCorreoElectronico();
                    copiara.add(remit.getCorreoElectronico());
                }
            }
            
            List<Config> configList = obtenerDatosSistema();
            String correoDestGenCenMez = Comunes.obtenValorConfiguracion(configList, Constantes.PARAM_SYSTEM_CORREO_DEST_GRAL_CEN_MEZ);
            if (ValidaUtil.correoValido(correoDestGenCenMez)){
                copiara.add(correoDestGenCenMez);
            }
            
            String remitenteCorreo = Comunes.obtenValorConfiguracion(configList, Constantes.PARAM_SYSTEM_REMITENTE_CORREO);
            String remitenteNombre = Comunes.obtenValorConfiguracion(configList, Constantes.PARAM_SYSTEM_REMITENTE_NOMBRE);
            String urlportal = Comunes.obtenValorConfiguracion(configList, Constantes.PARAM_SYSTEM_URL_PORATL);
            Object[] parametros = {(dest != null) ? dest.getNombreUsuario() : "", remitenteCorreo, urlportal};
            
            List<AdjuntoExtended> adjuntosLista = null;

            PlantillaCorreo pc = new PlantillaCorreo();
            pc.setClave(PlantillaCorreo_Enum.NOTIFICACION_MEZCLA.getClave());
            PlantillaCorreo plantilla = plaCorService.obtener(pc);

            if (plantilla == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Plantilla de correo no encontrada. ", null);

            } else {
                String cuerpo = plantilla.getContenido();
                cuerpo = cuerpo.replace("#ASUNTO_CORREO#", asunto);
                String folio = (se.getFolio() != null) ? se.getFolio() : "";
                cuerpo = cuerpo.replace("#FOLIO#", folio);
                String fecha = (se.getFechaProgramada() != null) ? FechaUtil.formatoCadena(se.getFechaProgramada(), "yyyy-MM-dd HH:mm") : "";
                cuerpo = cuerpo.replace("#FECHA#", fecha);
                String numExpediente = (se.getPacienteNumero() != null) ? se.getPacienteNumero() : "";
                cuerpo = cuerpo.replace("#NUM_EXPEDIENTE#", numExpediente);
                String nombrePaciente = (se.getNombrePaciente() != null) ? se.getNombrePaciente() : "";
                cuerpo = cuerpo.replace("#NOM_PACIENTE#", nombrePaciente);
                cuerpo = cuerpo.replace("#COMENTARIOS#", msj);
                cuerpo = cuerpo.replace("#MENSAJE#", msj);
                cuerpo = cuerpo.replace("#REMITENTE#", remitenteNombre);

                plantilla.setContenido(cuerpo);

                String [] destinatariosCorreos = destinatarios.toArray(new String[destinatarios.size()]);
                String [] copiaraCorreos = copiara.toArray(new String[copiara.size()]);
                
                res = envCorUtil.enviarCorreoConPlantillaYAdjuntos(
                        remitenteCorreo,
                        destinatariosCorreos,
                        copiaraCorreos,
                        asunto,
                        parametros,
                        plantilla,
                        adjuntosLista);
            }

        } catch (Exception e) {
            LOGGER.error("Error al enviar correo notificación :: {} ", e);
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al enviar correo notificación. ", null);
        }

        return res;
    }

    /**
     *
     * @return
     */
    private List<Config> obtenerDatosSistema() {
        LOGGER.trace("mx.mc.commons.SolucionUtils.obtenerDatosSistema()");
        List<Config> configList = null;
        Config c = new Config();
        c.setActiva(Constantes.ACTIVO);
        try {
            configList = conService.obtenerLista(c);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener datos de cofiguración de sistema :: {} ", ex);
        }
        return configList;
    }

    /**
     * Calcula la superficie corporal del paciente
     *
     * @param peso
     * @param altura
     * @return
     */
    public Double obtenerSuperficieCorporal(Double peso, Double altura) {
        LOGGER.trace("mx.mc.magedbean.SolucionUtilsMB.obtenerSuperficieCorporal()");
        Double res = 0.0;
        try {
            List<Config> configList = obtenerDatosSistema();
            String metodo = Comunes.obtenValorConfiguracion(configList, Constantes.PARAM_SYSTEM_METODO_CALCULO);
            double pesoExp = 0.0;
            double alturaExp = 0.0;
            DecimalFormat df = new DecimalFormat("#.##");
            switch (metodo) {
                // DuBois y DuBois
                case "1":
                    pesoExp = Math.pow(peso, 0.425);
                    alturaExp = Math.pow(altura * 100, 0.725);
                    res = 0.007184 * pesoExp * alturaExp;
                    break;
                // Gehan y George
                case "2":
                    pesoExp = Math.pow(peso, 0.51456);
                    alturaExp = Math.pow(altura * 100, 0.42246);
                    res = 0.0235 * pesoExp * alturaExp;
                    break;
                // Haycock
                case "3":
                    pesoExp = Math.pow(peso, 0.5378);
                    alturaExp = Math.pow(altura * 100, 0.3964);
                    res = 0.024265 * pesoExp * alturaExp;
                    break;
                // Boyd
                case "5":
                    pesoExp = Math.pow(peso * 1000, 0.7285);
                    alturaExp = Math.pow(altura * 100, 0.3);
                    res = 0.0003207 * alturaExp * pesoExp - (0.0188 * Math.log10(peso * 1000));
                    break;
                // Mosteller
                case "4":
                    res = (peso * (altura * 100) / 3600) / 0.5;
                    break;
                default:
                    res = (peso * (altura * 100) / 3600) / 0.5;
                    break;
            }

        } catch (Exception e) {
            LOGGER.error("Error al calcular superficie corporal :: {} ", e.getMessage());
        }
        return roundAvoid(res, 2);
    }

    private double roundAvoid(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

    /**
     * Busca tratamientos duplicados
     *
     * @param s
     * @param si
     * @return
     */
    private List<SurtimientoInsumo_Extend> obtenerMedicamentosPrescritosPorPaciente(
            Surtimiento_Extend s ,
            List<String> surtIdInsumoLista ,
            Integer tipoInsumo ,
            Integer numHrsPrev ,
            Integer numHrsPost) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.obtenerMedicamentosPrescritosPorPaciente()");
        List<SurtimientoInsumo_Extend> surInsumoLista = new ArrayList<>();
        try {

            List<Integer> listEstatusPrescripcion = new ArrayList<>();
            listEstatusPrescripcion.add(EstatusPrescripcion_Enum.REGRISTRADA.getValue());
            listEstatusPrescripcion.add(EstatusPrescripcion_Enum.PROGRAMADA.getValue());
            listEstatusPrescripcion.add(EstatusPrescripcion_Enum.PROCESANDO.getValue());
            listEstatusPrescripcion.add(EstatusPrescripcion_Enum.SURTIDA_PARCIAL.getValue());
            listEstatusPrescripcion.add(EstatusPrescripcion_Enum.FINALIZADA.getValue());
            List<Integer> listEstatusSurtimientoInsumo = new ArrayList<>();
            listEstatusSurtimientoInsumo.add(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
            listEstatusSurtimientoInsumo.add(EstatusSurtimiento_Enum.COMPLETADO.getValue());
            listEstatusSurtimientoInsumo.add(EstatusSurtimiento_Enum.EN_TRANSITO.getValue());
            listEstatusSurtimientoInsumo.add(EstatusSurtimiento_Enum.RECIBIDO.getValue());
            listEstatusSurtimientoInsumo.add(EstatusSurtimiento_Enum.SURTIDO.getValue());
            listEstatusSurtimientoInsumo.add(EstatusSurtimiento_Enum.SUTIDO_PARCIAL.getValue());
            if (s.getIdPaciente() != null
                    && surtIdInsumoLista != null && !surtIdInsumoLista.isEmpty()) {
                surInsumoLista = this.surInsService.obtenerMedicamentosPrescritosPorPaciente(
                        s.getIdPaciente(),
                        surtIdInsumoLista,
                        tipoInsumo,
                        numHrsPrev,
                        numHrsPost,
                        listEstatusPrescripcion,
                        listEstatusSurtimientoInsumo);
            }
        } catch (Exception e) {
            LOGGER.error("Error al buscar insumos prescritos por paciente :: {} ", e.getMessage());
        }
        return surInsumoLista;
    }

    /**
     * Busca tratamienntos de medicamento duplicado en un periodo de tiempo previo y posterior a la mezcla que se valida
     * @param s
     * @param surtIdInsumoLista
     * @param tipoInsumo
     * @param numHrsPrev
     * @param numHrsPost
     * @return
     */
    public String validaTratamientoDuplicado(
            Surtimiento_Extend s ,
            List<String> surtIdInsumoLista ,
            Integer tipoInsumo ,
            Integer numHrsPrev ,
            Integer numHrsPost) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.validaTratamientoDuplicado()");
        String res = null;
        try {
            List<SurtimientoInsumo_Extend> insumoPrescriList = obtenerMedicamentosPrescritosPorPaciente(s, surtIdInsumoLista, tipoInsumo, numHrsPrev, numHrsPost);
            if (insumoPrescriList != null && !insumoPrescriList.isEmpty()) {
                
                StringBuilder sb = new StringBuilder();
                Integer cont = 1;
                SurtimientoInsumo_Extend siAnt = new SurtimientoInsumo_Extend();
                for (SurtimientoInsumo_Extend o : insumoPrescriList) {
                    if (cont > 1) {
// TODO : corregir validar tratamietos duplicados sólo de prescripciones vigentes
                        if (!siAnt.getIdPrescripcion().equalsIgnoreCase(o.getIdPrescripcion())) {
                            sb = new StringBuilder();
                            sb.append("Posible duplicidad: ");
                            sb.append("Folio Pres: ").append(o.getFolioPrescripcion());
                            sb.append(" Insumo: ").append(o.getNombreCorto());
                            sb.append(" Fecha: ").append(FechaUtil.formatoFecha(o.getFechaProgramada(), "dd/MM/yyyy HH:mm"));
                            sb.append(" Médico: ").append(o.getNombreMedico());
                            sb.append(" Posología: ").append(o.getDosis()).append(StringUtils.SPACE).append(o.getUnidadConcentracion());
                            sb.append("|").append(o.getFrecuencia()).append(" hrs");
                            sb.append("|").append(o.getDuracion()).append(" días");
                        }
                    }
                    siAnt = new SurtimientoInsumo_Extend();
                    siAnt.setIdPrescripcion(o.getIdPrescripcion());
                    cont++;
                }
                if (cont > 1) {
                    res = sb.toString();
                }
            }
        } catch (Exception ex) {
            LOGGER.error(Constantes.MENSAJE_ERROR, "Error al obtener Tratamientos duplicados :: {} ", ex.getMessage());
        }
        return res;
    }

    
    /**
     * 
     * @param siLista
     * @param tipoInsumo
     * @return 
     */
    private SurtimientoInsumo_Extend obtenerFarmacoDiluyente(List<SurtimientoInsumo_Extend> siLista, String tipoInsumo) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.obtenerFarmacoDiluyente()");
        SurtimientoInsumo_Extend insumos = null;
        try {
            if (siLista != null && !siLista.isEmpty()) {
                for (SurtimientoInsumo_Extend sie : siLista) {
                    Medicamento m = medService.obtener(new Medicamento(sie.getIdInsumo()));
                    if (m != null && m.getTipo() == CatalogoGeneral_Enum.MEDICAMENTO.getValue()){
                        sie.setClaveInstitucional(m.getClaveInstitucional());
                        sie.setNombreCorto(m.getNombreCorto());
                        if ( tipoInsumo.equals("farmaco")  && !m.isDiluyente()) {
                            insumos = sie;
                            break;
                        } else if (tipoInsumo.equals("diluyente") && m.isDiluyente() ) {
                            insumos = sie;
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al obtener los obtenerFarmacoDiluyente :: {} ", e.getMessage());
        }
        return insumos;
    }
        
    
    /**
     * Genera instrucciones de preparación co Base en Envase contenedor y lista
     * de insumos
     *
     * @param s
     * @param siLista
     * @return
     * @throws java.lang.Exception
     */
    public String generaInstruccionPreparacion(Surtimiento_Extend s, List<SurtimientoInsumo_Extend> siLista, ViaAdministracion va) throws Exception {
        LOGGER.trace("mx.mc.commons.SolucionUtils.generaInstruccionPreparacion()");
        String cont = "";
        try {
            
            SurtimientoInsumo_Extend reactivo = obtenerFarmacoDiluyente(siLista, "farmaco");
            SurtimientoInsumo_Extend diluyente = obtenerFarmacoDiluyente(siLista, "diluyente");
            
            boolean encontrado = false;
            Integer idFabricante = null;
            
            if (reactivo != null && reactivo.getIdInsumo() != null && va.getIdViaAdministracion() != null){
                
                for (SurtimientoInsumo_Extend item : siLista){
                    if (reactivo.getIdInsumo().equals(item.getIdInsumo())){
                        if (item.getSurtimientoEnviadoExtendList() == null){
                            break;
                        } else {
                            if (!item.getSurtimientoEnviadoExtendList().isEmpty()) {
                                if (item.getSurtimientoEnviadoExtendList().get(0) != null ){
                                    if (item.getSurtimientoEnviadoExtendList().get(0).getIdInventarioSurtido() != null) {
                                        Inventario o = new Inventario();
                                        o.setIdInventario(item.getSurtimientoEnviadoExtendList().get(0).getIdInventarioSurtido());
                                        Inventario i = invenService.obtener(o);
                                        if (i != null) {
                                            idFabricante = i.getIdFabricante();
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                
                if (reactivo.getIdInsumo() != null) {
                    String claveInstitucional = null;
                    if (diluyente != null) {
                        claveInstitucional = diluyente.getClaveInstitucional();
                    }

                    if (idFabricante == null) {
//TODO 21abr2024 , Cuendo no hay fabricante mandar una throw new exception alertando no hay fabricante
                    } else {
//TODO 21abr2024 , Eliminar código duro
                        Integer idContenedor = (s.getIdContenedor() == 2 || s.getIdContenedor() == 32) ? s.getIdContenedor() : null;
                        List<Estabilidad_Extended> estabilidadList = new ArrayList<>();
                        estabilidadList.addAll(estabiService.buscarEstabilidad(
                                reactivo.getIdInsumo(),
                                claveInstitucional,
                                va.getIdViaAdministracion(),
                                idFabricante,
                                idContenedor
                        ));

                        Estabilidad_Extended e = null;
                        for (Estabilidad_Extended item : estabilidadList) {
// Si la estabilidad del farmaco registrada contiene vía de administración y coincide con la prescripción
                            if (Objects.equals(item.getIdViaAdministracion(), va.getIdViaAdministracion()) &&
                                    Objects.equals(item.getIdContenedor(), idContenedor)) {
                                if (item.getReglasDePreparacion() != null) {
                                    e = item;
                                    encontrado = true;
                                    break;
                                }
                            }
                        }

                        if (e != null && reactivo.getDosis().compareTo(BigDecimal.ZERO) == 1) {
                            BigDecimal v1 = reactivo.getDosis().multiply(BigDecimal.ONE);
                            BigDecimal volCalculado = v1.divide(new BigDecimal(e.getConcentracionReconst()), 2, RoundingMode.HALF_UP);

                            BigDecimal volResidual = BigDecimal.ZERO;
                            BigDecimal volContenedor = BigDecimal.ZERO;
                            if (diluyente != null) {
                                Medicamento m = medService.obtener(new Medicamento(diluyente.getIdInsumo()));
                                if (m != null && m.getCantPorEnvase() > 0) {
                                    volContenedor = new BigDecimal(m.getCantPorEnvase());
                                }
                            }

                            if (volContenedor.compareTo(BigDecimal.ZERO) == 1) {
                                volResidual = volContenedor.subtract(s.getVolumenTotal());
                            }

                            BigDecimal volPorDesplazar = volResidual.add(volCalculado);

                            cont = e.getReglasDePreparacion();

                            cont = cont.replace("[DOSIS_CALCULADA]", (volCalculado == null) ? "" : volCalculado.toString());

                            BigDecimal volPorDesplazar2 = volPorDesplazar.add(new BigDecimal(5.5));

                            cont = cont.replace("[VOLUMEN_POR_DESPLAZAR]", (volPorDesplazar == null) ? "" : volPorDesplazar.toString());
                            cont = cont.replace("[VOLUMEN_POR_DESPLAZAR2]", (volPorDesplazar2 == null) ? "" : volPorDesplazar2.toString());
                            cont = cont.replace("[VOLUMEN_TOTAL]", (s.getVolumenTotal() == null) ? "" : s.getVolumenTotal().toString());
                            cont = cont.replace("[CLAVE_REACTIVO]", (e.getNombreCorto() == null) ? "" : e.getNombreCorto());
                            cont = cont.replace("[VOLUMEN_RECONST]", (e.getVolumenReconst() == null) ? "" : e.getVolumenReconst().toString());
                            cont = cont.replace("[CONCENTRACION_RECONST]", (e.getConcentracionReconst() == null) ? "" : e.getConcentracionReconst().toString());
//                            Medicamento medDil = medService.obtenerMedicaByClave(e.getDiluyenteReconst());
                            cont = cont.replace("[DILUYENTE_RECONST]", (e.getDiluyenteReconst() == null ) ? "" : e.getDiluyenteReconst());
                            cont = cont.replace("[CONCET_RECONST]", (e.getConcentracionReconst() == null) ? "" : e.getConcentracionReconst().toString());
                            cont = cont.replace("[UNI_CONCEN_RECONS]", (e.getNombreUnidadConcentracion() == null) ? "" : e.getNombreUnidadConcentracion() );
                            cont = cont.replace("[CONTENEDOR_SOLUCION]", (diluyente == null || diluyente.getNombreCorto() == null) ? "" : diluyente.getNombreCorto());
                            cont = cont.replace("[NO_FRASCOS]", (reactivo.getCantidadEnviada() == null ) ? "" : reactivo.getCantidadEnviada().toString());
                            cont = cont.replace("[NOMBRE_FARMACO]", (reactivo.getNombreCorto() == null) ? "" : reactivo.getNombreCorto());

                        }
                    }
                }
// RN si no hay instrucción de preparación en la estabilidad, Tomar la regla del contenedor
                if (!encontrado) {
                    EnvaseContenedor ec = this.envConService.obtenerXidEnvase(s.getIdContenedor());
                    if (ec == null) {
                        throw new Exception("Envase contenedor inválido.");
                    } else if (ec.getInstruccionPreparacion() == null || ec.getInstruccionPreparacion().trim().isEmpty()) {
                        throw new Exception("Plantilla de instrucciones vacía.");
                    } else {
                        cont = ec.getInstruccionPreparacion();
                        cont = cont.replace("#NOMBRE_CONTENEDOR#", (ec.getDescripcion() == null) ? "" : eliminaCarEspe(ec.getDescripcion()));

                        int noInsumos = siLista.size();
                        BigDecimal volPorDrenar = BigDecimal.ZERO;
                        DecimalFormat df = new DecimalFormat("###.##");
                        Medicamento_Extended m;
                        String idInsumo;
                        BigDecimal concentracion;
                        BigDecimal cantidadEnvase;
                        BigDecimal dosisPrescrita;
                        BigDecimal volumenRequerido;
                        BigDecimal cantSobrate = BigDecimal.ZERO;

                        for (int i = 0; i < noInsumos; i++) {
                            String num = String.format("%02d", i + 1);
                            idInsumo = siLista.get(i).getIdInsumo();
                            m = this.medService.obtenerMedicamentoByIdMedicamento(idInsumo);

                            if (m != null) {
                                if (m.getTipo() == CatalogoGeneral_Enum.MEDICAMENTO.getValue()) {
                                    if (m.isDiluyente()) {
                                        if (siLista.get(i).getDosis().compareTo(new BigDecimal(m.getCantPorEnvase())) == -1) {
                                            BigDecimal volumenCalculado = obtenerVolumenCalculado(siLista.get(i).getDosis(), m);
                                            cantSobrate = new BigDecimal(m.getCantPorEnvase()).subtract(siLista.get(i).getDosis());
                                        }
                                        cont = cont.replace("#VOLUMEN_DILUYENTE#", (siLista.get(i).getDosis() == null) ? "" : siLista.get(i).getDosis().toString());
                                        cont = cont.replace("#NOMBRE_INSUMO_DILUYENTE#", (m.getNombreCorto() == null) ? "" : m.getNombreCorto());
                                        cont = cont.replace("#VOLUMEN_PRESCRITO_" + num + "#", (siLista.get(i).getDosis() == null) ? "" : siLista.get(i).getDosis().toString());
                                        cont = cont.replace("#NOMBRE_INSUMO_PRESCRITO_" + num + "#", (m.getNombreCorto() == null) ? "" : m.getNombreCorto());
                                        // TODO: validar regla de preparación de mezcla con contenedor
                                    } else if (!m.isContenedor()) {
                                        concentracion = (m.getConcentracion() != null) ? m.getConcentracion() : BigDecimal.ZERO;
                                        cantidadEnvase = (m.getCantPorEnvase() != null) ? new BigDecimal(m.getCantPorEnvase()) : BigDecimal.ZERO;
                                        dosisPrescrita = (siLista.get(i).getDosis() != null) ? siLista.get(i).getDosis() : BigDecimal.ZERO;

                                        if (concentracion.compareTo(BigDecimal.ZERO) == 1
                                                && cantidadEnvase.compareTo(BigDecimal.ZERO) == 1
                                                && dosisPrescrita.compareTo(BigDecimal.ZERO) == 1) {
                                            volumenRequerido = (dosisPrescrita.multiply(cantidadEnvase)).divide(concentracion, 2, RoundingMode.HALF_UP);
                                        } else {
                                            volumenRequerido = BigDecimal.ZERO;
                                        }
                                        volPorDrenar = volPorDrenar.add(volumenRequerido);

                                        cont = cont.replace("#VOLUMEN_PRESCRITO_" + num + "#", (volumenRequerido.compareTo(BigDecimal.ZERO) == 1) ? df.format(volumenRequerido) : BigDecimal.ZERO.toString());
                                        cont = cont.replace("#NOMBRE_INSUMO_PRESCRITO_" + num + "#", (siLista.get(i).getNombreCorto() == null) ? "" : siLista.get(i).getNombreCorto());
                                    }
                                }
                            }
                        }
                        volPorDrenar = volPorDrenar.add(cantSobrate);
                        cont = cont.replace("#CANTIDAD_TOTAL_A_DRENAR#", (volPorDrenar.compareTo(BigDecimal.ZERO) == 1) ? df.format(volPorDrenar) : BigDecimal.ZERO.toString());
                        cont = cont.replace("#VOLUMEN_INDICADO_MEDICO#", (s.getVolumenTotal() == null) ? "" : s.getVolumenTotal().toString());

                        cont = cont.replace("Tomar #VOLUMEN_PRESCRITO_01# mL de #NOMBRE_INSUMO_PRESCRITO_01#", "");
                        cont = cont.replace("Tomar #VOLUMEN_PRESCRITO_02# mL de #NOMBRE_INSUMO_PRESCRITO_02#", "");
                        cont = cont.replace("Tomar #VOLUMEN_PRESCRITO_03# mL de #NOMBRE_INSUMO_PRESCRITO_03#", "");
                        cont = cont.replace("Tomar #VOLUMEN_PRESCRITO_04# mL de #NOMBRE_INSUMO_PRESCRITO_04#", "");
                        cont = cont.replace("Tomar #VOLUMEN_PRESCRITO_05# mL de #NOMBRE_INSUMO_PRESCRITO_05#", "");

                        cont = cont.replace("agregar #VOLUMEN_PRESCRITO_01# mL de #NOMBRE_INSUMO_PRESCRITO_01#", "");
                        cont = cont.replace("agregar #VOLUMEN_PRESCRITO_02# mL de #NOMBRE_INSUMO_PRESCRITO_02#", "");
                        cont = cont.replace("agregar #VOLUMEN_PRESCRITO_03# mL de #NOMBRE_INSUMO_PRESCRITO_03#", "");
                        cont = cont.replace("agregar #VOLUMEN_PRESCRITO_04# mL de #NOMBRE_INSUMO_PRESCRITO_04#", "");
                        cont = cont.replace("agregar #VOLUMEN_PRESCRITO_05# mL de #NOMBRE_INSUMO_PRESCRITO_05#", "");

                        cont = cont.replace("Adicionar #VOLUMEN_DILUYENTE# Del diluyente #NOMBRE_INSUMO_DILUYENTE# al contenedor ", "");
                        cont = cont.replace("\n\n", "");
                    }
                    
                    
                }
                
            }
        } catch (Exception e) {
            LOGGER.error("Error al generar instrucciones de preparación generaInstruPrep :: {} ", e);
            throw new Exception("Error al generar instrucciones de preparación.");
        }
        return cont;
    }

    /**
     * Obtiene nombre del médico en sesion si es que la vista está como
     * prescripción
     *
     * @param isValidaPrescripcion
     * @param u
     * @return
     */
    public Usuario obtenerMedico(boolean isValidaPrescripcion, Usuario u) {
        Usuario o = new Usuario();
        if (!isValidaPrescripcion) {
            if (u != null) {
                o = u;
            }
        }
        return o;
    }

    /**
     * Obtener listado de Justificación
     *
     * @param activa
     * @param listTipoJustificacion
     * @return
     */
    public List<TipoJustificacion> obtenerJustificacion(boolean activa, List<Integer> listTipoJustificacion) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.obtenerJustificacion()");
        List<TipoJustificacion> justificacionList = new ArrayList<>();
        try {
            justificacionList.addAll(tipJusService.obtenerActivosPorListId(activa, listTipoJustificacion));
        } catch (Exception ex) {
            LOGGER.error("Error en obtenerJustificacion :: {}", ex.getMessage());
        }
        return justificacionList;
    }

    /**
     * Obtiene lista de insumos por coincidencia
     *
     * @param cadena
     * @param idEstructuraServicio
     * @param listaTipoSurtimiento
     * @return
     */
    public List<Medicamento_Extended> obtenerInsumosPorCoincidencia(String cadena, String idEstructuraServicio, List<String> listaTipoSurtimiento) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.obtenerInsumosPorCoincidencia()");
        List<Medicamento_Extended> lista = new ArrayList<>();
        try {
            lista.addAll(this.medService.obtenerAutoCompleteMedicamento(cadena, idEstructuraServicio, listaTipoSurtimiento));
        } catch (Exception ex) {
            LOGGER.error("Error al consultar medicamentos por coincidencia :: {} ", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("medicamento.err.autocomplete"), null);
        }
        return lista;
    }

    public String obtenerClaveProtocolo(List<Protocolo> listaProtocolos, Integer idProtocolo) {
        String clave = null;
        for (Protocolo proto : listaProtocolos) {
            if (proto.getIdProtocolo().equals(idProtocolo)) {
                clave = proto.getClaveProtocolo();
                break;
            }
        }
        return clave;
    }

    /**
     * Obtiene Usuario por Coincidencia
     *
     * @param cadena
     * @param tipoUsuario
     * @param cantRegistros
     * @param prescribeControlados
     * @return
     */
    public List<Usuario> obtenerUsuarioPorCoincidencia(String cadena, Integer tipoUsuario, Integer cantRegistros, Integer prescribeControlados) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.obtenerUsuarioPorCoincidencia()");
        List<Usuario> listMedicos = new ArrayList<>();
        try {
            listMedicos.addAll(this.usuService.obtenerMedicosPorCriteriosBusqueda(
                    cadena.trim(), tipoUsuario, cantRegistros, prescribeControlados));
        } catch (Exception ex) {
            LOGGER.error("Error al obtener buscar usuarios por coincidencia :: {}", ex.getMessage());
        }
        return listMedicos;
    }

    /**
     * Obtener usuario por ID
     *
     * @param idUsuario
     * @return
     */
    public Usuario obtenerUsuarioPorId(String idUsuario) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.obtenerUsuarioPorId()");
        Usuario u = new Usuario();
        try {
            if (idUsuario != null && !idUsuario.isEmpty()) {
                u = this.usuService.obtenerUsuarioPorId(idUsuario);
            }
        } catch (Exception ex) {
            LOGGER.error("Error al obtener usuario por ID :: {}", ex.getMessage());
        }
        return u;
    }

    /**
     * Valid que la fecha de entrega no sea superior a la actual
     *
     * @param fechaEntrega
     * @param he
     * @param fechaRegistro
     * @return
     */
    public String fechaHoraEntregaValido(Date fechaEntrega, HorarioEntrega he, Date fechaRegistro) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.fechaHoraEntregaValido()");
        String res = null;
        try {
            Date fechaAct = new java.util.Date();
//            Date fechaEntCalc = null;

            Integer noDiasEtreFechas = FechaUtil.obtenerNoDiasEntreFechas(fechaRegistro, fechaEntrega);
            String fechaLimite = FechaUtil.formatoCadena(fechaEntrega, "yyyy-MM-dd");
            String horaProgramada = FechaUtil.formatoCadena(fechaEntrega, "HH:mm");

            if (fechaEntrega.before(fechaRegistro)) {
                res = "Fecha de entrega " + FechaUtil.formatoCadena(fechaEntrega, "yyyy-MM-dd HH:mm")
                        + " debe ser mayor a la fecha de registro " + FechaUtil.formatoCadena(fechaRegistro, "yyyy-MM-dd HH:mm") + ". ";

            } else if (fechaEntrega.before(fechaAct)) {
                res = "Fecha de entrega " + FechaUtil.formatoCadena(fechaEntrega, "yyyy-MM-dd HH:mm")
                        + " debe ser mayor a la fecha actual " + FechaUtil.formatoCadena(fechaAct, "yyyy-MM-dd HH:mm") + ". ";

            } else if (noDiasEtreFechas < 1) {
                String horaMinSol = FechaUtil.formatoCadena(he.getHoraMinimaSolicitud(), "HH:mm");
                String horaMaxSol = FechaUtil.formatoCadena(he.getHoraMaximaSolicitud(), "HH:mm");
                Date fechaMinSol = FechaUtil.formatoFecha("yyyy-MM-dd HH:mm", fechaLimite + " " + horaMinSol);
                Date fechaMaxSol = FechaUtil.formatoFecha("yyyy-MM-dd HH:mm", fechaLimite + " " + horaMaxSol);
//                fechaEntCalc = FechaUtil.sumarRestarDiasFecha(fechaEntrega, -1);            

                if (horaProgramada.trim().equalsIgnoreCase("07:30:00")) {
                    res = "Para horario programado \"07:30:00\" las solicitudes deben ser de días anteriores. ";

                } else if (fechaRegistro.before(fechaMinSol)) {
                    res = "El horario seleccionado " + horaProgramada
                            + " tiene fecha mínima de solicitud : "
                            + FechaUtil.formatoCadena(fechaMinSol, "yyyy-MM-dd HH:mm");

                } else if (fechaRegistro.after(fechaMaxSol)) {
                    res = "El horario seleccionado " + horaProgramada
                            + " tiene fecha máxima de solicitud : " + FechaUtil.formatoCadena(fechaMaxSol, "yyyy-MM-dd HH:mm");

                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al validar fecha de entrega. :: {} ", e.getMessage());
        }
        return res;
    }

    /**
     * @param fechaCancela
     * @return
     */
    public String fechaHoraCancelacionValida(Date fechaCancela) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.fechaHoraCancelacionValida()");
        String res = null;
        try {
            Date fechaAct = new java.util.Date();
            if (FechaUtil.obtenerNoDiasEntreFechas(fechaAct, fechaCancela) >= 0) {
                if (fechaAct.after(fechaCancela)) {
                    res = "Fecha máxima de cancelacion: " + FechaUtil.formatoFecha(fechaCancela, "yyyy-MM-dd HH:mm");
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al validar fecha de entrega. :: {} ", e.getMessage());
        }
        return res;
    }

    public String validaDiagnosticoProtocolo(String claveProtocolo, String idDiagnostico, String idTipoSolucion, List<TipoSolucion> tipoSolucionList) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.validaDiagnosticoProtocolo()");
        String res = null;
        try {
            boolean isOncologica = obtenClaveTipoSolucion(idTipoSolucion, tipoSolucionList).equalsIgnoreCase("ONC");
            if (isOncologica) {
                Protocolo p = new Protocolo();
                p.setClaveProtocolo(claveProtocolo);
                p = this.proService.obtener(p);
                if (p == null) {
                    res = "Protocolo inválido.";
                } else if (p.getIdDiagnostico() == null) {
                    res = "Protocolo inválido.";
                } else if (p.isValidaProtocolo()
                        && !p.getClaveProtocolo().equalsIgnoreCase(claveProtocolo)
                        && !p.getIdDiagnostico().equalsIgnoreCase(idDiagnostico)) {
                    res = "El Diagnóstico del protocolo no coincide con el asignado";
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al validar el protocolo {} ", ex.getMessage());
            res = "El Diagnóstico del protocolo no coincide con el asignado";
        }
        return res;
    }

    private String obtenClaveTipoSolucion(String idTipoSolucion, List<TipoSolucion> tipoSolucionList) {
        String clave = "";
        for (TipoSolucion tipoSol : tipoSolucionList) {
            if (tipoSol.getIdTipoSolucion().equals(idTipoSolucion)) {
                clave = tipoSol.getClave();
                break;
            }
        }
        return clave;
    }

    /**
     * Genera la impresión de un documento, PRescripción, orden de preparación
     *
     * @param se Surtimiento de la mezcla relacionado a la prescripción
     * @param nombreUsuario Nombre usuario en sesión
     * @param idTipoReporte Tipo de documento a imprimir
     * @return objeto pdf en bytes para generar l aimpresión
     * @throws java.lang.Exception
     */
    public byte[] imprimeDocumento(Surtimiento_Extend se, String nombreUsuario, Integer idTipoReporte) throws Exception {
        LOGGER.trace("mx.mc.commons.SolucionUtils.imprimeDocumento()");
        byte[] buffer = null;
        try {
            if (se == null) {
                throw new Exception("Solicitud de mezcla no encontrada.");

            } else {
                String diagnosticos = "";
                String alergias = "";

                List<Diagnostico> list = this.diaService.obtenerPorIdPacienteIdVisitaIdPrescripcion(se.getIdPaciente(), se.getIdVisita(), se.getIdPrescripcion());
                for (Diagnostico d : list) {
                    if (d.getNombre().length() > 2) {
                        diagnosticos = (diagnosticos.trim().isEmpty()) ? d.getNombre() : diagnosticos + " | " + d.getNombre();
                    }
                }

                Prescripcion pr = this.preService.obtener(new Prescripcion(se.getIdPrescripcion()));
                if (pr == null) {
                    throw new Exception("Prescripción no encontrada.");

                } else {
                    String idSolucion = null;
                    String idSurtimiento = se.getIdSurtimiento();
                    Solucion sol = this.solService.obtenerSolucion(idSolucion, idSurtimiento);
                    if (sol == null) {
                        throw new Exception("Solicitud de mezcla no encontrada.");

                    } else {                        
                        se.setNoAgitar((sol.getNoAgitar() != null && sol.getNoAgitar()==1));
                        se.setTempAmbiente((sol.getProteccionTemp() != null && sol.getProteccionTemp()==1));
                        se.setTempRefrigeracion((sol.getProteccionTempRefrig() != null && sol.getProteccionTempRefrig()==1));
                        se.setProteccionLuz((sol.getProteccionLuz() != null && sol.getProteccionLuz()==1));
                        se.setHorasInfusion((sol.getMinutosInfusion() != null) ? sol.getMinutosInfusion().toString(): "");
                        se.setPerfusionContinua((sol.getPerfusionContinua() != null && sol.getPerfusionContinua() ==1) ? true : false);
                        Estructura e = this.estService.obtenerEstructura(pr.getIdEstructura());
                        if (e.getIdEntidadHospitalaria() == null) {
                            e.setIdEntidadHospitalaria("");
                        }
                        EntidadHospitalaria enti = this.entHosService.obtenerEntidadById(e.getIdEntidadHospitalaria());
                        if (enti == null) {
                            enti = new EntidadHospitalaria();
                            enti.setDomicilio("");
                            enti.setNombre("");
                        }

                        se.setFolioPrescripcion(pr.getFolio());
                        se.setIdPrescripcion(pr.getIdPrescripcion());
                        
                        if (Objects.equals(sol.getIdEstatusSolucion(), EstatusSolucion_Enum.CANCELADA.getValue())) {
                            se.setEstatusPrescripcion(EstatusSolucion_Enum.getStatusFromId(sol.getIdEstatusSolucion()).name());
                        } else {
                            se.setEstatusPrescripcion(EstatusPrescripcion_Enum.getStatusFromId(pr.getIdEstatusPrescripcion()).name());
                        }

                        EnvaseContenedor contenedor = this.envConService.obtenerXidEnvase(sol.getIdEnvaseContenedor());

                        switch (idTipoReporte) {
                            case 1: //  // Prescripción
                                buffer = this.repService.imprimePrescripcionMezcla(
                                        se,
                                        sol,
                                        enti,
                                        nombreUsuario,
                                        alergias,
                                        diagnosticos,
                                        contenedor.getDescripcion(),
                                        pr);
                                break;
                            case 2: //
                                buffer = this.repService.reporteValidacionSoluciones(
                                        se,
                                        sol,
                                        enti,
                                        nombreUsuario,
                                        alergias,
                                        diagnosticos,
                                        contenedor.getDescripcion(),
                                        pr);
                                break;
                            case 3: // Orden de preparación
                                buffer = this.repService.reporteValidacionSoluciones(
                                        se,
                                        sol,
                                        enti,
                                        nombreUsuario,
                                        alergias,
                                        diagnosticos,
                                        contenedor.getDescripcion(),
                                        pr
                                );
                                break;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al generar la impresión :: {} ", ex.getMessage());
            throw new Exception("Prescripción no encontrada. " + ex.getMessage());
        }
        return buffer;
    }

    /**
     * Método que envía a la impresora de etiquetas zebra las impresiones
     * deseadas.
     *
     * @param p Presceipción asociada a la etiqueta
     * @param s Surtimiento de la prescripción asocidad a la etiqeuta
     * @param so Solución registro de la mezcla de la etiqueta
     * @param idImpresora Impresora seleccionada
     * @param idTemplate Plantilla de impresión seleccionada
     * @param canImpresiones Cantidad de Impresiones eviadas a la impresora
     * @return valor booleano, indica si se envió la impresión o no,
     * independientemete de que la impresión no salga.
     */
    public boolean imprimirEtiqueta(Prescripcion p, Surtimiento_Extend s, Solucion so, String idImpresora, String idTemplate, Integer canImpresiones) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.imprimirEtiqueta()");

        boolean status = false;

        if (p == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No se pueden ontener datos de la prescripción de mezcla para imprimir. ", null);

        } else if (s == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Surtimiento asociado a la mezcla inválido. ", null);

        } else if (s.getIdSurtimiento() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Surtimiento asociado a la mezcla inválido. ", null);

        } else if (s.getIdPrescripcion() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Prescripcion asociadaa a la mezcla inválida. ", null);

        } else if (idImpresora == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Impresora seleccionada inválida. ", null);

        } else if (idTemplate == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Plantilla de etiqueta seleccionada inválida. ", null);

        } else if (so == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No se pueden ontener datos de la mezcla para imprimir. ", null);

        } else {
            Impresora impresora = obtenerImpresora(idImpresora);
            TemplateEtiqueta templaEtiqueta = obtenerTemplateEtiqueta(idTemplate);
            ViaAdministracion va  = null;
            if (so.getIdViaAdministracion() != null) {
                va  = obtenerViaAdministracion(so.getIdViaAdministracion());
            }

            String nombreArea = "";
            String nombreHospital = "";

            try {
                Estructura e = this.estService.obtenerEstructura(s.getIdEstructuraAlmacen());
                if (e.getNombre() != null) {
                    nombreArea = e.getNombre();
                    if (e.getIdEntidadHospitalaria() != null) {
                        EntidadHospitalaria eh = this.entHosService.obtenerEntidadById(e.getIdEntidadHospitalaria());
                        if (eh != null) {
                            nombreHospital = eh.getNombre();
                        }
                    }
                }
            } catch (Exception e) {
                LOGGER.error("Error la buscar la entidad hospitalaria {} ", e.getMessage());
            }

            if (impresora == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No se pueden obetener datos de la impresora seleccionada. ", null);

            } else if (impresora.getIpImpresora() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No se pueden obetener datos de la impresora seleccionada. ", null);

            } else if (templaEtiqueta == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No se pueden obetener datos de la plantilla de impresión seleccionada. ", null);

            } else if (templaEtiqueta.getContenido() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No se pueden obetener datos de la plantilla de impresión seleccionada. ", null);

            } else {
                String cont = templaEtiqueta.getContenido();

                cont = cont.replace("#TITULO1#", (!nombreHospital.equals(StringUtils.EMPTY)) ? eliminaCarEspe(nombreHospital) : "");
                cont = cont.replace("#TITULO2#", (!nombreArea.equals(StringUtils.EMPTY)) ? eliminaCarEspe(nombreArea) : "");

                cont = cont.replace("#FOL_OPREP#", (s.getFolio() != null) ? s.getFolio() : "");
                cont = cont.replace("#FOL_PRES#", (p.getFolio() != null) ? p.getFolio() : "");
                cont = cont.replace("#FECHA#", (s.getFechaProgramada() != null) ? FechaUtil.formatoFecha(s.getFechaProgramada(), "dd/MM/yyyy HH:mm") : "");

                cont = cont.replace("#NOM_COM_PAC#", (s.getNombrePaciente() != null) ? eliminaCarEspe(s.getNombrePaciente()) : "");
                cont = cont.replace("#CLA_PAC#", (s.getPacienteNumero() != null) ? s.getPacienteNumero() : "");
                cont = cont.replace("#EDAD#", (s.getEdadPaciente() > 0) ? String.valueOf(s.getEdadPaciente()) : "");
                cont = cont.replace("#PESO#", (s.getPesoPaciente() > 0) ? String.valueOf(s.getPesoPaciente()) : "");
                cont = cont.replace("#NUM_PAC#", (s.getPacienteNumero() != null) ? s.getPacienteNumero() : "");

                cont = cont.replace("#NOM_CAM#", (s.getIdCama() != null) ? eliminaCarEspe(s.getIdCama()) : "");
                cont = cont.replace("#NOM_SER#", (s.getNombreEstructura() != null) ? eliminaCarEspe(s.getNombreEstructura()) : "");
                cont = cont.replace("#NOM_MED#", (s.getNombreMedico() != null) ? eliminaCarEspe(s.getNombreMedico()) : "");
                cont = cont.replace("#CED_MED#", (s.getCedProfesional() != null) ? s.getCedProfesional() : "");

                cont = cont.replace("#FEC_PREP#", (so.getFechaPrepara() != null) ? FechaUtil.formatoFecha(so.getFechaPrepara(), "dd/MM/yyyy HH:mm") : "");
                cont = cont.replace("#FEC_MAX_MIN#", (so.getCaducidadMezcla() != null) ? FechaUtil.formatoFecha(so.getCaducidadMezcla(), "dd/MM/yyyy HH:mm") : "");
                cont = cont.replace("#VEL_INF#", (so.getVelocidad() != null && so.getVelocidad() > 0) ? String.valueOf(so.getVelocidad()) : "");
                cont = cont.replace("#VOL_TOT#", (so.getVolumen() != null) ? String.valueOf(so.getVolumen()) : "");
                cont = cont.replace("#TIP_SOL#", (s.getTipoSolucion() != null) ? eliminaCarEspe(s.getTipoSolucion()) : "");
                cont = cont.replace("#TIP_INF#", (s.getTipoSolucion() != null) ? eliminaCarEspe(s.getTipoSolucion()) : "");
                cont = cont.replace("#VIA_ADM#", (va  != null) ? eliminaCarEspe(va.getNombreViaAdministracion()) : "");
                cont = cont.replace("#PES_TOT#", (so.getPesoTotal() > 0) ? String.valueOf(so.getPesoTotal()) : "");
                cont = cont.replace("#CAL_TOT#", (so.getCaloriasTotales() > 0) ? String.valueOf(so.getCaloriasTotales()) : "");
                cont = cont.replace("#OSM_TOT#", (so.getOmolairdadTotal() > 0) ? String.valueOf(so.getOmolairdadTotal()) : "");

                cont = cont.replace("#COMENTARIOS#", (so.getComentariosPreparacion() != null) ? eliminaCarEspe(so.getComentariosPreparacion()) : "");
                cont = cont.replace("#CON_MINISTRACION#", "");
                cont = cont.replace("#CON_CONSERVACION#", (so.getObservaciones() != null) ? eliminaCarEspe(so.getObservaciones()) : "");

                List<SurtimientoInsumo_Extend> surInsLista = new ArrayList<>();
                try {
                    boolean mayorCero = true;
                    surInsLista = this.surInsService.obtenerSurtimientoInsumosByIdSurtimiento(s.getIdSurtimiento(), mayorCero);
                } catch (Exception e) {
                    LOGGER.error("Error al obtener los medicamentos de la mezcla. ");
                }
                for (int i = 0; i < 25; i++) {
                    String num = String.format("%02d", i + 1);
                    if (surInsLista != null && surInsLista.size() >= i + 1) {
                        cont = cont.replace("#NOMBRE_MEDICAMENTO_" + num + "#",
                                (surInsLista.get(i).getNombreCorto() != null)
                                ? eliminaCarEspe(surInsLista.get(i).getNombreCorto())
                                : "");
                        String cantUmedida = (surInsLista.get(i).getDosis() != null)
                                ? surInsLista.get(i).getDosis().toString() : "";
                        cantUmedida = (surInsLista.get(i).getUnidadConcentracion() != null)
                                ? cantUmedida + " " + surInsLista.get(i).getUnidadConcentracion()
                                : cantUmedida + "";
                        cont = cont.replace("#CANT_UMEDIDA_" + num + "#", cantUmedida);
                    } else {
                        cont = cont.replace("#NOMBRE_MEDICAMENTO_" + num + "#", "");
                        cont = cont.replace("#CANT_UMEDIDA_" + num + "#", "");
                    }
                }

                /*NOMBRE_MEDICAMENTO_02  CANT_UMEDIDA_01
FECHA_PREP      FOL_OPREP       FECHA_PREP
NOM_COM_PAC     CLA_PAC         EDAD        PESO                NUM_PAC
NOM_CAM         NOM_SER         NOM_MED     CED_MED
FEC_MAX_MIN     VEL_INF         TIP_INF     CAL_TOT             PES_TOT       VOL_TOT       OSM_TOT
COMENTARIOS     CON_MINISTRACION            CON_CONSERVACION    NUM_ETQ                        */
                templaEtiqueta.setContenido(cont);
                try {
                    status = this.repService.imprimirEtiquetaCM(templaEtiqueta, canImpresiones, impresora.getIpImpresora());
                } catch (Exception e) {
                    LOGGER.error("Error al Imprimir Etiqueta :: {} ", e.getMessage());
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No se pudo generar la impresión.", null);
                }
            }
        }
        return status;
    }

//    
//    public Solucion obtenerSolucion(Surtimiento_Extend s) {
//        LOGGER.trace("mx.mc.commons.SolucionUtils.obtenerSolucion()");
//        Solucion sol = null;
//        try {
//            if (s == null) {
//            } else if (s.getIdSurtimiento()==null){
//                
//            } else {
//                sol = new Solucion();
//                sol.setIdSurtimiento(s.getIdSurtimiento());
//                sol = solService.obtener(sol);
//                if (sol == null) {
//                    
//                } else {
//                    if (sol.getIdEstatusSolucion() == null) {
//                        s.setEstatusSurtimiento(EstatusSolucion_Enum.getStatusFromId(solucionSelect.getIdEstatusSolucion()).name());
//                    }
//                    surtimientoExtendedSelected.setPesoPaciente(solucionSelect.getPesoPaciente());
//                    surtimientoExtendedSelected.setTallaPaciente(solucionSelect.getTallaPaciente());
//                    surtimientoExtendedSelected.setAreaCorporal(solucionSelect.getAreaCorporal());
//                    surtimientoExtendedSelected.setObservaciones(solucionSelect.getObservaciones());
//                    surtimientoExtendedSelected.setInstruccionPreparacion(solucionSelect.getInstruccionesPreparacion());
//                    if (solucionSelect.getVolumen() != null) {
//                        surtimientoExtendedSelected.setVolumenTotal(solucionSelect.getVolumen());
//                    }
//
//                    if (prescripcionSelected != null) {
//                        TipoSolucion ts = obtenerTipoSolucion(prescripcionSelected.getIdTipoSolucion());
//                        if (ts != null) {
//                            surtimientoExtendedSelected.setTipoSolucion(ts.getClave() + " - " + ts.getDescripcion());
//                        }
//
//                        ViaAdministracion vad = obtenerViaAdministracion(prescripcionSelected.getIdViaAdministracion());
//                        if (vad != null) {
//                            surtimientoExtendedSelected.setNombreViaAdministracion(vad.getNombreViaAdministracion());
//                        }
//
//                        Protocolo pr = obtenerProtocolo(prescripcionSelected.getIdProtocolo());
//                        if (pr != null) {
//                            surtimientoExtendedSelected.setNombreProtoclo(pr.getClaveProtocolo() + " - " + pr.getDescripcionProtocolo());
//                        }
//                        Diagnostico di = obtenerDiagnostico(prescripcionSelected.getIdDiagnostico());
//                        if (di != null) {
//                            surtimientoExtendedSelected.setNombreDiagnostico(di.getClave() + " - " + di.getNombre());
//                        }
//                    }
//                    EnvaseContenedor ec = obtenerContenedor(solucionSelect.getIdEnvaseContenedor());
//                    if (ec != null) {
//                        surtimientoExtendedSelected.setNombreEnvaseContenedor(ec.getDescripcion());
//                    }
//                }
//            }
//        } catch (Exception e) {
//            LOGGER.error("Error al obtenerSolucion {} ", e.getMessage());
//        }
//    }
//
//    /**
//     * Obtiene los servicios que puede surtir a partir de un idEstructura que
//     * puede ser el idEstructura del usuario o una opción seleccionada de un
//     * combo de selección
//     *
//     * @param u Usuario que realiza la búsqueda
//     * @param pu presmiso de usuario
//     * @param idEstructura idEstructura de almacén a partir de donde busca
//     * servicios por surtir
//     * @return La lista de servicios deseada
//     */
//    public List<Estructura> obtieneServiciosQuePuedeSurtir(Usuario u, PermisoUsuario pu, String idEstructura) {
//        LOGGER.trace("mx.mc.commons.SolucionUtils.obtieneServiciosQuePuedeSurtir()");
//        List<Estructura> listaEstructuras = new ArrayList<>();
//
//        if (u == null) {
//            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Usuario inválido. ", null);
//
//        } else if (u.getIdUsuario() == null) {
//            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Usuario inválido. ", null);
//
//        } else if (idEstructura == null || idEstructura.trim().isEmpty()) {
//            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Usuario sin acceso de almacén. ", null);
//
//        } else if (pu == null) {
//            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No tiene permiso para esta transacción. ", null);
//
//        } else if (!pu.isPuedeVer()) {
//            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No tiene permiso para esta transacción. ", null);
//
//        } else {
//            Estructura estSol = null;
//            try {
//                estSol = this.estService.obtener(new Estructura(idEstructura));
//            } catch (Exception ex) {
//                LOGGER.error(ex.getMessage());
//            }
//            
//            if (estSol == null) {
//                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Usuario sin acceso de almacén. ", null);
//
//            } else if (estSol.getIdTipoAreaEstructura() == null) {
//                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Usuario sin acceso de almacén. ", null);
//
//            } else if (!Objects.equals(estSol.getIdTipoAreaEstructura(), TipoAreaEstructura_Enum.ALMACEN.getValue())) {
//                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Usuario sin acceso de almacén. ", null);
//
//            } else {
//                try {
//                    listaEstructuras.addAll(this.estService.obtenerServicioQueSurtePorIdEstructura(idEstructura));
//                } catch (Exception excp) {
//                    LOGGER.error(excp.getMessage());
//                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al buscar serivios que puede surtir el usuario. ", null);
//                }
//            }
//        }
//        return listaEstructuras;
//    }
//    /**
//     * Obtiene el valor entero de un parametro de confuguracion si esta activo
//     * @param parametro
//     * @return 
//     */
//    public Integer obtenerValorParametro(String parametro) {
//        LOGGER.trace("mx.mc.commons.SolucionUtils.obtenerValorParametro()");
//        Integer value = null;
//        try {
//        List<Config> configList = obtenerDatosSistema();
//        String tempValue = Comunes.obtenValorConfiguracion(configList, parametro);
//        tempValue = (!tempValue.isEmpty()) ? tempValue : "0";
//        value = Integer.parseInt(tempValue);
//        } catch(NumberFormatException e){
//            LOGGER.trace("Error al obtener valor de parámetro :: {} ", e.getMessage());
//        }
//        return value;
//    }
//    
//    /**
//     * Valida posibles tratamiento duplicados, no considera diluyentes
//     * @param idPac
//     * @param surtIdInsumoLista
//     * @param numHrsPrev
//     * @param numHrsPost
//     * @param tipoInsumo
//     * @return 
//     */
//    public ValidacionNoCumplidaDTO tratamientoDuplicidado(String idPac, List<String> surtIdInsumoLista, Integer numHrsPrev, Integer numHrsPost, Integer tipoInsumo) {
//        LOGGER.trace("mx.mc.commons.SolucionUtils.tratamientoDuplicidado()");
//        ValidacionNoCumplidaDTO res = null;
//        try {
//            List<Integer> listEstatusPrescripcion = new ArrayList<>();
//            listEstatusPrescripcion.add(EstatusPrescripcion_Enum.REGRISTRADA.getValue());
//            listEstatusPrescripcion.add(EstatusPrescripcion_Enum.PROGRAMADA.getValue());
//            listEstatusPrescripcion.add(EstatusPrescripcion_Enum.PROCESANDO.getValue());
//            listEstatusPrescripcion.add(EstatusPrescripcion_Enum.SURTIDA_PARCIAL.getValue());
//            listEstatusPrescripcion.add(EstatusPrescripcion_Enum.FINALIZADA.getValue());
//            List<Integer> listEstatusSurtimientoInsumo = new ArrayList<>();
//            listEstatusSurtimientoInsumo.add(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
//            listEstatusSurtimientoInsumo.add(EstatusSurtimiento_Enum.COMPLETADO.getValue());
//            listEstatusSurtimientoInsumo.add(EstatusSurtimiento_Enum.EN_TRANSITO.getValue());
//            listEstatusSurtimientoInsumo.add(EstatusSurtimiento_Enum.RECIBIDO.getValue());
//            listEstatusSurtimientoInsumo.add(EstatusSurtimiento_Enum.SURTIDO.getValue());
//            listEstatusSurtimientoInsumo.add(EstatusSurtimiento_Enum.SUTIDO_PARCIAL.getValue());
//
//            List<SurtimientoInsumo_Extend> surInsumoLista;
//            surInsumoLista = this.surInsService.obtenerMedicamentosPrescritosPorPaciente(
//                    idPac,
//                    surtIdInsumoLista,
//                    tipoInsumo,
//                    numHrsPrev,
//                    numHrsPost,
//                    listEstatusPrescripcion,
//                    listEstatusSurtimientoInsumo);
//            if (surInsumoLista != null && !surInsumoLista.isEmpty()) {
//                StringBuilder sb;
//                for (SurtimientoInsumo_Extend o : surInsumoLista) {
//                    sb = new StringBuilder();
//                    sb.append(o.getClaveInstitucional());
//                    sb.append(" Prescrito: ");
//                    sb.append(FechaUtil.formatoFecha(o.getFechaProgramada(), "dd/MM/yyyy HH:mm:ss"));
//                    sb.append(" Médico: ");
//                    sb.append(o.getNombreMedico());
//                    sb.append(" Posología: ");
//                    sb.append(o.getDosis());
//                    sb.append(StringUtils.SPACE);
//                    sb.append(o.getUnidadConcentracion());
//                    sb.append("|");
//                    sb.append(o.getFrecuencia());
//                    sb.append("|");
//                    sb.append(o.getDuracion());
//
//                    res = new ValidacionNoCumplidaDTO();
//                    res.setCodigo("00");
//                    res.setDescripcion("Posible duplicidad: " + sb.toString());
//                }
//            }
//        } catch (Exception e) {
//            LOGGER.error("Error al buscar duplicidad de tratamiento :: {} ", e.getMessage());
//        }
//        return res;
//    }
    public String obtenerIpMaquina() {
        String ipMaquina = "";
        try {
            ipMaquina = ClientInfo.getClientIpAddr((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest());
        } catch (Exception ex) {
            LOGGER.error("Error al intentar obtener la ip de la maquina  :: " + ex.getMessage());
        }
        return ipMaquina;
    }

    public Equipo obtenerInformacionEquipo(String ipCliente) {
        Equipo equipoResult = new Equipo();
        try {
            Equipo equipo = new Equipo();
            equipo.setIpEquipo(ipCliente);
            equipoResult = equipoService.obtenerByCampo(equipo);
        } catch (Exception ex) {
            LOGGER.error("Error al intentar obtener la información del equipo  :: " + ex.getMessage());
        }
        return equipoResult;
    }

    public StringBuilder generarInstrucPreparaNPT(NutricionParenteralExtended nutricionParenteralSelect) throws Exception {
        StringBuilder indicacion = new StringBuilder();
        List<NutricionParenteralDetalleExtended> listaMedicamentosNPT = new ArrayList<>();
        try {
            if (nutricionParenteralSelect.getIdContenedor() != null) {
                EnvaseContenedor ec = this.envConService.obtenerXidEnvase(nutricionParenteralSelect.getIdContenedor());
                if (ec == null) {
                    throw new Exception("Envase contenedor inválido.");
                } else {
                    indicacion.append("Del contenedor ").append(ec.getDescripcion() == null ? "" : eliminaCarEspe(ec.getDescripcion())).append(" Agregar: ").append("\n");
                }
            }
            String aguaInyectable = "";
            BigDecimal volumenAguaDiez = new BigDecimal(0);
            String nombreUnidadConcentracion = "";

            List<Medicamento> listaMedicamentosPrioritarios = medService.obtenerMedicamentoAdicionarParaPrepararAsc();
            for (Medicamento medicamento : listaMedicamentosPrioritarios) {
                for (NutricionParenteralDetalleExtended nutricionParenteralDeta : nutricionParenteralSelect.getListaMezclaMedicamentos()) {
                    if (nutricionParenteralDeta.getCantCalculada() != null) {
                        if (nutricionParenteralDeta.getCantCalculada().compareTo(new BigDecimal(0)) == 1) {
                            if (medicamento.getClaveInstitucional().equals(nutricionParenteralDeta.getClaveMedicamento())) {
                                indicacion.append("    * ").append(nutricionParenteralDeta.getNombreCorto()).append(" ");
                                //Revisar cual sera la clave del agua
//                                if (nutricionParenteralDeta.getOrdenAdicion().compareTo(new BigDecimal(8)) == 1
//                                        && nutricionParenteralDeta.getOrdenAdicion().compareTo(new BigDecimal(9)) == -1) {
                                BigDecimal cantCalcSobre = nutricionParenteralDeta.getCantCalculada().add(nutricionParenteralDeta.getSobrellenado());
                                if (medicamento.isEsAgua()) {
                                    aguaInyectable = nutricionParenteralDeta.getNombreCorto();
                                    BigDecimal noventaPorciento = cantCalcSobre.multiply(new BigDecimal(0.90)).setScale(2, RoundingMode.HALF_UP);
                                    indicacion.append(noventaPorciento).append(" ").append(nutricionParenteralDeta.getNombreUnidadConcentracion()).append("\n");
                                    volumenAguaDiez = cantCalcSobre.subtract(noventaPorciento);
                                    nombreUnidadConcentracion = nutricionParenteralDeta.getNombreUnidadConcentracion();
                                } else {
                                    indicacion.append(cantCalcSobre).append(" ").append("mL").append("\n");
                                }
                                listaMedicamentosNPT.add(nutricionParenteralDeta);
                                break;
                            }
                        }
                    }
                }
            }
            if (volumenAguaDiez.compareTo(BigDecimal.ZERO) == 1) {
                indicacion.append("    * ").append(aguaInyectable).append(" ").append(volumenAguaDiez).append(" mL").append("mL").append("\n");;
            }
        } catch (Exception ex) {
            LOGGER.error("Error al generar instrucciones de preparación NPT :: {} ", ex.getMessage());
            throw new Exception("Error al generar instrucciones de NPT.");
        }
        return indicacion;
    }

    public String concatenaDiagnosticos(NutricionParenteralExtended nutricionParenteralSelect) {
        String diagnosticosPaciente = "";
        if (nutricionParenteralSelect.getListaDiagnosticos() != null) {
            for (Diagnostico diagnostico : nutricionParenteralSelect.getListaDiagnosticos()) {
                if (diagnosticosPaciente.isEmpty()) {
                    diagnosticosPaciente = diagnostico.getNombre();
                } else {
                    diagnosticosPaciente = diagnosticosPaciente + "; " + diagnostico.getNombre();
                }
            }
        }
        return diagnosticosPaciente;
    }

    public EntidadHospitalaria obtieneEntidad(String idEstructura) {
        EntidadHospitalaria enti = new EntidadHospitalaria();
        enti.setIdEntidadHospitalaria(null);
        enti.setDomicilio("");
        enti.setNombre("");
        try {
            if (idEstructura != null) {
                Estructura estruct = estService.obtenerEstructura(idEstructura);
                if (estruct != null && estruct.getIdEntidadHospitalaria() != null) {
                    enti = entHosService.obtenerEntidadById(estruct.getIdEntidadHospitalaria());
                }
            }
        } catch (Exception e) {

        }
        return enti;
    }

    public Prescripcion creaPrescripcion(String idPacienteServicio, String idPacienteUbicacion, NutricionParenteralExtended nutricionParenteralSelect, Usuario usuarioSelected) {
        Prescripcion prescripcion = new Prescripcion();
        String idPrescripcion = Comunes.getUUID();
        prescripcion.setIdPrescripcion(idPrescripcion);
        prescripcion.setIdEstructura(nutricionParenteralSelect.getIdEstructura());
        prescripcion.setIdPacienteServicio(idPacienteServicio);
        prescripcion.setIdPacienteUbicacion(idPacienteUbicacion);
        prescripcion.setFechaPrescripcion(new Date());
        prescripcion.setFechaFirma(new Date());
        prescripcion.setTipoPrescripcion(nutricionParenteralSelect.getTipoPrescripcion());
        prescripcion.setTipoConsulta(nutricionParenteralSelect.getTipoConsulta());
        prescripcion.setIdMedico(nutricionParenteralSelect.getIdMedico());
        prescripcion.setRecurrente(Constantes.INACTIVO);
        prescripcion.setIdEstatusPrescripcion(EstatusPrescripcion_Enum.PROGRAMADA.getValue());
        prescripcion.setIdEstatusGabinete(EstatusGabinete_Enum.PENDIENTE.getValue());
        prescripcion.setResurtimiento(0);
        prescripcion.setInsertIdUsuario(usuarioSelected.getIdUsuario());
        prescripcion.setInsertFecha(new Date());
        EntidadHospitalaria e = obtieneEntidad(nutricionParenteralSelect.getIdEstructura());
        prescripcion.setIdEntidadHospitalaria(e.getIdEntidadHospitalaria());
//TODO Revisar el tippo de solución
        prescripcion.setIdTipoSolucion(nutricionParenteralSelect.getIdTipoSolucion());
        prescripcion.setIdViaAdministracion(nutricionParenteralSelect.getIdViaAdministracion());

        return prescripcion;
    }

    public Surtimiento creaSurtimiento(String idPrescripcion,
            NutricionParenteralExtended nutricionParenteralSelect,
            Usuario usuarioSelected) {
        Surtimiento surtimiento = new Surtimiento();
        String idSurtimiento = Comunes.getUUID();
        nutricionParenteralSelect.setIdSurtimiento(idSurtimiento);
        surtimiento.setIdSurtimiento(idSurtimiento);
        surtimiento.setIdPrescripcion(idPrescripcion);
//TODO Revisar el almacen que surte
        surtimiento.setIdEstructuraAlmacen("e39332fa-9344-49e7-b588-6a3353f86bd6");
        surtimiento.setIdPrescripcion(idPrescripcion);
        surtimiento.setFechaProgramada(nutricionParenteralSelect.getFechaProgramada());
        surtimiento.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
        surtimiento.setIdEstatusMirth(EstatusGabinete_Enum.PENDIENTE.getValue());
        surtimiento.setInsertFecha(new Date());
        surtimiento.setInsertIdUsuario(usuarioSelected.getIdUsuario());
        return surtimiento;

    }

    public PrescripcionInsumo creaPrescripcionInsumo(String idPrescripcion,
            NutricionParenteralDetalle nutricionDetalleParental,
            NutricionParenteralExtended nutricionParenteralSelect,
            Usuario usuarioSelected) {
        PrescripcionInsumo prescripcionInsumo = new PrescripcionInsumo();
        String idPrescripcionInsumo = Comunes.getUUID();
        prescripcionInsumo.setIdPrescripcionInsumo(idPrescripcionInsumo);
        prescripcionInsumo.setIdPrescripcion(idPrescripcion);
        prescripcionInsumo.setIdInsumo(nutricionDetalleParental.getIdMedicamento());
        prescripcionInsumo.setFechaInicio(nutricionParenteralSelect.getFechaProgramada());
        prescripcionInsumo.setDosis(nutricionDetalleParental.getVolPrescrito());
        prescripcionInsumo.setFrecuencia(Constantes.SURT_MANUAL_HOSP_NOHORAS_POST_FECHA_PRESC);
        prescripcionInsumo.setDuracion(Constantes.PRESCRIPCION_DURACION);
        prescripcionInsumo.setIdEstatusPrescripcion(EstatusPrescripcion_Enum.PROGRAMADA.getValue());
        prescripcionInsumo.setIdEstatusMirth(EstatusGabinete_Enum.PENDIENTE.getValue());
        prescripcionInsumo.setInsertFecha(new Date());
        prescripcionInsumo.setInsertIdUsuario(usuarioSelected.getIdUsuario());
//        prescripcionInsumo.setEnvioJV(0);
//        prescripcionInsumo.setComentarios();
//        prescripcionInsumo.setIdUnidadConcentracion();
//        prescripcionInsumo.setIndicaciones()
//        prescripcionInsumo.setRitmo()
        prescripcionInsumo.setPerfusionContinua((nutricionParenteralSelect.isPerfusionContinua()) ? 1 : 0);
        try {
            Medicamento m = medService.obtenerMedicaLocalidad(nutricionDetalleParental.getIdMedicamento());
            if (m != null) {
                if (m.isDiluyente()) {
                    prescripcionInsumo.setIdTipoIngrediente(TipoIngrediente_Enum.DILIUYENTE.getValue());
                } else {
                    prescripcionInsumo.setIdTipoIngrediente(TipoIngrediente_Enum.FARMACO.getValue());
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al obtener tipo de ingrediente.");
        }
        prescripcionInsumo.setVelocidad(nutricionParenteralSelect.getVelocidad().doubleValue());

        return prescripcionInsumo;
    }

    public SurtimientoInsumo creaSurtimientoInsumo(String idSurtimiento,
            String idPrescripcionInsumo,
            NutricionParenteralDetalle nutricionDetalleParental,
            NutricionParenteralExtended nutricionParenteralSelect,
            Usuario usuarioSelected) {
        SurtimientoInsumo surtimientoInsumo = new SurtimientoInsumo();
        String idSurtimientoInsumo = Comunes.getUUID();
        surtimientoInsumo.setIdSurtimientoInsumo(idSurtimientoInsumo);
        surtimientoInsumo.setIdSurtimiento(idSurtimiento);
        surtimientoInsumo.setIdPrescripcionInsumo(idPrescripcionInsumo);
        surtimientoInsumo.setFechaProgramada(new Date());
        Integer cantidad = 1;

        if (nutricionDetalleParental.getVolPrescrito() != null && nutricionDetalleParental.getCantCalculada() != null) {
            if (nutricionDetalleParental.getCantCalculada().compareTo(BigDecimal.ZERO) == 1) {
                cantidad = nutricionDetalleParental.getVolPrescrito().divide(nutricionDetalleParental.getCantCalculada(), 2, RoundingMode.HALF_UP).intValue();
            }
        }

        surtimientoInsumo.setCantidadSolicitada(cantidad);
        surtimientoInsumo.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
//        surtimientoInsumo.setFolioOrdenAVG(nutricionParenteralSelect.getFolio());
        surtimientoInsumo.setInsertFecha(new Date());
        surtimientoInsumo.setInsertIdUsuario(usuarioSelected.getIdUsuario());
        surtimientoInsumo.setCantidadEnviada(cantidad);
        return surtimientoInsumo;
    }
    
    public SurtimientoEnviado creaSurtimientoEnviado(String idSurtimientoInsumo,
            NutricionParenteralDetalle nutricionDetalleParental,
            NutricionParenteralExtended nutricionParenteralSelect,
            Usuario usuarioSelected) {
        
        SurtimientoEnviado surtimientoEnviado = new SurtimientoEnviado();
        String idSurtimientoEnviado = Comunes.getUUID();
        surtimientoEnviado.setIdSurtimientoEnviado(idSurtimientoEnviado);
        surtimientoEnviado.setIdSurtimientoInsumo(idSurtimientoInsumo);
        surtimientoEnviado.setIdInventarioSurtido(nutricionDetalleParental.getIdInventario());
        surtimientoEnviado.setCantidadEnviado(0);
        surtimientoEnviado.setInsertFecha(new Date());
        surtimientoEnviado.setInsertIdUsuario(usuarioSelected.getIdUsuario());
        return surtimientoEnviado;
    }

    public Solucion creaSolucion(String idSurtimiento, NutricionParenteralExtended np, Usuario usuarioSelected) {
        Solucion solucion = new Solucion();
        solucion.setIdSolucion(Comunes.getUUID());
        solucion.setIdSurtimiento(idSurtimiento);
        solucion.setIdEnvaseContenedor(np.getIdContenedor());
        solucion.setMuestreo(0);
        solucion.setInstruccionesPreparacion(np.getInstruccionPreparacion());
        solucion.setVolumen(np.getVolumenTotal());
        solucion.setObservaciones(np.getObservaciones());
        solucion.setIdEstatusSolucion(EstatusSolucion_Enum.OP_VALIDADA.getValue());
        solucion.setProteccionLuz(np.isProteccionLuz() ? 1: 0);
        solucion.setProteccionTemp(np.isTempAmbiente() ? 1: 0);
        solucion.setProteccionTempRefrig(np.isTempRefrigeracion() ? 1: 0);
        solucion.setNoAgitar(np.isNoAgitar() ? 1 : 0);
        
        if (np.getEdad() != null){
            solucion.setEdadPaciente(np.getEdad().doubleValue());
        }
        
        if (np.getPeso() != null){
            solucion.setPesoPaciente(np.getPeso().doubleValue());
        }
        
        if (np.getTallaPaciente() != null){    
            solucion.setTallaPaciente(np.getTallaPaciente().doubleValue());
        }
        
        if (np.getAreaCorporal() != null){
            solucion.setAreaCorporal(np.getAreaCorporal().doubleValue());
        }
        
        solucion.setPerfusionContinua(np.isPerfusionContinua() ? 1 : 0);
        
        if (np.getVelocidad() != null){
            solucion.setVelocidad(np.getVelocidad().doubleValue());
        }
        
        solucion.setIdViaAdministracion(np.getIdViaAdministracion());
        solucion.setKcalNoProteicas(np.getKcalNoProteicas());
        solucion.setKcalProteicas(np.getKcalProteicas());
        
        if (np.getVolumenTotal() != null){
            solucion.setVolumenFinal(np.getVolumenTotal().toString());
        }
        solucion.setInsertFecha(new Date());
        solucion.setInsertIdUsuario(usuarioSelected.getIdUsuario());
        
        if (np.getPesoTotal() != null){
            solucion.setPesoTotal(np.getPesoTotal().doubleValue());
        }
        
        if (np.getCaloriasTotal() != null){
            solucion.setCaloriasTotales(np.getCaloriasTotal().doubleValue());
        }
        
        if (np.getTotalOsmolaridad() != null){
            solucion.setOmolairdadTotal(np.getTotalOsmolaridad().doubleValue());
        }
        
        return solucion;
    }

    public DiagnosticoPaciente creaDiagnosticoPaciente(String idPrescripcion,
            Diagnostico diagnostico,
            Usuario usuarioSelected) {
        DiagnosticoPaciente diagnosticoPaciente = new DiagnosticoPaciente();
        diagnosticoPaciente.setIdDiagnosticoPaciente(Comunes.getUUID());
        diagnosticoPaciente.setIdPrescripcion(idPrescripcion);
        diagnosticoPaciente.setFechaDiagnostico(new java.util.Date());
        diagnosticoPaciente.setIdUsuarioDiagnostico(usuarioSelected.getIdUsuario());
        diagnosticoPaciente.setIdDiagnostico(diagnostico.getIdDiagnostico());
        diagnosticoPaciente.setIdEstatusDiagnostico(EstatusDiagnostico_Enum.DIAGNOSTICADO.getValue());
        diagnosticoPaciente.setInsertFecha(new java.util.Date());
        diagnosticoPaciente.setInsertIdUsuario(usuarioSelected.getIdUsuario());
        return diagnosticoPaciente;
    }

    /**
     * Valida una orden de preparación de una mezcla de nutricion NPT
     * @param nutricionParenteralSelect
     * @param medicoSelect
     * @param idTipoSolucion
     * @param viaAdministracion
     * @return 
     */
    public String validaDatosOrdenPreparacion(NutricionParenteralExtended nutricionParenteralSelect,
            Usuario medicoSelect,
            String idTipoSolucion,
            ViaAdministracion viaAdministracion) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.validaDatosOrdenPreparacion()");
        String valida = "";
        if (nutricionParenteralSelect.getFolio().isEmpty()) {
            return RESOURCES.getString("ord.nutr.parenteral.req.prescripcion");
        } else if (nutricionParenteralSelect.getFechaProgramada() == null) {
            return RESOURCES.getString("ord.nutr.parenteral.req.fechaProgramada");
        } else if (nutricionParenteralSelect.getTipoPrescripcion() == null) {
            return "Tipo de prescripción inválida.";
        } else if (nutricionParenteralSelect.getIdEstructura() == null) {
            return "Área o servicio inválido.";
        } else if (nutricionParenteralSelect.getIdCama() == null) {
            return "Cama inválida.";
        } else if (nutricionParenteralSelect.getIdPaciente() == null) {
            return RESOURCES.getString("ord.nutr.parenteral.req.paciente");
        } else if (nutricionParenteralSelect.getPacienteNumero().isEmpty()) {
            return RESOURCES.getString("ord.nutr.parenteral.req.pacienteNumero");
        } else if (medicoSelect == null) {
            return RESOURCES.getString("ord.nutr.parenteral.req.medico");
        } else if (nutricionParenteralSelect.getTipoConsulta() == null) {
            return "Tipo de consulta inválida.";
        } else if (nutricionParenteralSelect.getPeso() == null) {
            return "Peso inválido.";
        } else if (nutricionParenteralSelect.getPeso().compareTo(BigDecimal.ZERO) < 1) {
            return "Peso inválido.";
        } else if (nutricionParenteralSelect.getPeso().compareTo(new BigDecimal(200)) == 1) {
            return "Peso inválido.";
        } else if (nutricionParenteralSelect.getTallaPaciente() == null) {
            return "Estatura inválida.";
        } else if (nutricionParenteralSelect.getTallaPaciente().compareTo(BigDecimal.ZERO) < 1) {
            return "Estatura inválida.";
        } else if (nutricionParenteralSelect.getTallaPaciente().compareTo(new BigDecimal(2.3d)) == 1) {
            return "Estatura inválida.";
        } else if (nutricionParenteralSelect.getAreaCorporal() == null) {
            return "Superficie corporal inválida.";
        } else if (nutricionParenteralSelect.getAreaCorporal().compareTo(BigDecimal.ZERO) < 1) {
            return "Superficie corporal inválida.";
        } else if (nutricionParenteralSelect.getListaDiagnosticos() == null) {
            return "Registre al menos 1 diagnóstico.";
        } else if (nutricionParenteralSelect.getListaDiagnosticos().isEmpty()) {
            return "Registre al menos 1 diagnóstico.";
        } else if (idTipoSolucion == null || idTipoSolucion.isEmpty()) {
            return "Seleccione tipo de solución";
        } else if (nutricionParenteralSelect.getIdViaAdministracion() == null) {
            return "Seleccione vía de administración";
        } else if (nutricionParenteralSelect.getIdViaAdministracion() == null) {
            return "Seleccione vía de administración";
//        } else if (nutricionParenteralSelect.getIdContenedor() == null){
//            return "Seleccione contenedor";
        } else if (nutricionParenteralSelect.getSobrellenado() == null) {
            return "Debe seleccionnar una opción de sobrellenado";
        } else if (nutricionParenteralSelect.getHorasInfusion() == null) {
            return "Debe seleccionnar las horas de infusión.";
        } else if (nutricionParenteralSelect.getVolumenTotal() == null) {
            return "Capture los insumos prescritos";
        } else if (nutricionParenteralSelect.getVolumenTotal().compareTo(BigDecimal.ZERO) < 1) {
            return "No se reconoce volumen total, capture los insumos prescritos";
        }
//        if (nutricionParenteralSelect.getFechaParaEntregar() == null){
//            return "Día de entrega incorrecto";
//        }
//        if (nutricionParenteralSelect.getFechaParaEntregar().after(new java.util.Date())){
//            return "Día de entrega incorrecto";
//        }
//        if (nutricionParenteralSelect.getIdHorarioParaEntregar() == null 
//                || nutricionParenteralSelect.getIdHorarioParaEntregar() < 1){
//            return "Horario de entrega incorrecto";
//        }
//        if (nutricionParenteralSelect.isPerfusionContinua()){
//            if (nutricionParenteralSelect.getVelocidad() == null){
//                return "Velocidad inválida";
//            } else if (nutricionParenteralSelect.getVelocidad().compareTo(BigDecimal.ZERO)<1){
//                return "Velocidad inválida";
//            }
//        }
//        if(nutricionParenteralSelect.getNumeroVisita().isEmpty()) {
//            valida = RESOURCES.getString("ord.nutr.parenteral.req.numeroVisita");
//            return valida;
//        }
//        if(nutricionParenteralSelect.getFechaIngreso() == null) {
//            valida = RESOURCES.getString("ord.nutr.parenteral.req.fechaIngreso");
//            return valida;
//        }
        if (nutricionParenteralSelect.getIdEstatusSolucion() != null
                && Objects.equals(nutricionParenteralSelect.getIdEstatusSolucion(), EstatusSolucion_Enum.PRESCRIPCION_VALIDADA.getValue())){
            for (NutricionParenteralDetalleExtended nutricionDetalle : nutricionParenteralSelect.getListaMezclaMedicamentos()) {
                if (nutricionDetalle.getVolPrescrito().compareTo(BigDecimal.ZERO) == 1) {
                    if (nutricionDetalle.getIdInventario() == null){
                        return "Seleccione el Fabricante y lote del insumo : " + nutricionDetalle.getClaveMedicamento();
                    }
                }
            }
        }
        return valida;
    }

    public Paciente obtenerDatosPaciente(Paciente pacienteNuevo, Usuario usuarioSelected) {
        Paciente pacientTem = new Paciente();
        pacientTem.setNombreCompleto(pacienteNuevo.getNombreCompleto());
        pacientTem.setApellidoMaterno(pacienteNuevo.getApellidoMaterno());
        pacientTem.setApellidoPaterno(pacienteNuevo.getApellidoPaterno());
        pacientTem.setPacienteNumero(pacienteNuevo.getPacienteNumero());
        pacientTem.setClaveDerechohabiencia(pacienteNuevo.getClaveDerechohabiencia());
        
//        if (pacienteNuevo.getPacienteNumero() == null || pacienteNuevo.getPacienteNumero().trim().isEmpty()){
//            String numeroPacienteTmp = generarPacNumero();
//            pacientTem.setPacienteNumero(numeroPacienteTmp);
//            pacientTem.setClaveDerechohabiencia(numeroPacienteTmp);
//        } else {
//            pacientTem.setClaveDerechohabiencia(pacienteNuevo.getPacienteNumero());
//            pacientTem.setPacienteNumero(pacienteNuevo.getPacienteNumero());
//        }
        
        pacientTem.setCurp(pacienteNuevo.getCurp());
        pacientTem.setRfc(pacienteNuevo.getRfc());
        pacientTem.setFechaNacimiento(pacienteNuevo.getFechaNacimiento());
        pacientTem.setIdEscolaridad(CatalogoGeneral_Enum.ESCOLARIDAD_NO_DEFINIDA.getValue());
        pacientTem.setIdEstadoCivil(CatalogoGeneral_Enum.ESTADO_CIVIL_NO_DEFINIDO.getValue());
        pacientTem.setIdEstatusPaciente(EstatusPaciente_Enum.REGISTRADO.getValue());
        pacientTem.setIdGrupoEtnico(CatalogoGeneral_Enum.GRUPO_ETNICO_NO_DEFINIDO.getValue());
        pacientTem.setIdGrupoSanguineo(CatalogoGeneral_Enum.GRUPO_SANGUINEO_NO_DEFINIDO.getValue());
        pacientTem.setIdNivelSocioEconomico(CatalogoGeneral_Enum.NIVEL_SOCIOECONOMICO_NO_DEFINIDO.getValue());
        pacientTem.setIdOcupacion(CatalogoGeneral_Enum.OCUPACION_NO_DEFINIDA.getValue());
        pacientTem.setIdPaciente(Comunes.getUUID());
        pacientTem.setIdReligion(CatalogoGeneral_Enum.RELIGION_NO_DEFINIDA.getValue());
//        pacientTem.setIdTipoPaciente(pacienteNuevo.getIdTipoPaciente());
        pacientTem.setIdTipoPaciente(CatalogoGeneral_Enum.DERECHOHABIENTE.getValue());
        pacientTem.setIdTipoVivienda(CatalogoGeneral_Enum.TIPO_VIVIENDA_NO_DEFINIDA.getValue());
        pacientTem.setIdUnidadMedica(CatalogoGeneral_Enum.UNIDAD_MEDICA_OTRA.getValue());
        pacientTem.setInsertFecha(new Date());
        pacientTem.setInsertIdUsuario(usuarioSelected.getIdUsuario());
        pacientTem.setSexo(pacienteNuevo.getSexo());
        pacientTem.setLugarNacimiento(pacienteNuevo.getLugarNacimiento());
        pacientTem.setPaisResidencia(pacienteNuevo.getPaisResidencia());
        pacientTem.setDescripcionDiscapacidad(pacienteNuevo.getDescripcionDiscapacidad());
        
        return pacientTem;
    }

    
    private String generarPacNumero() {
        Date fecha = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmm");
        return sdf.format(fecha);
    }
    
    /**
     * Generación de datos genericos de domicilio de paciente
     * @param paciente
     * @param usuario
     * @return 
     */
    public PacienteDomicilio obtenerDatosPacienteDomicilioGenerico(Paciente paciente, Usuario usuario) {
        PacienteDomicilio pacienteDom = new PacienteDomicilio();
        pacienteDom.setIdPaciente(paciente.getIdPaciente());
        pacienteDom.setIdPacienteDomicilio(Comunes.getUUID());
        pacienteDom.setCalle(Constantes.NO_DEFINIDO);
        pacienteDom.setNumeroExterior(Constantes.NO_DEFINIDO);
        pacienteDom.setTelefonoCasa(Constantes.NO_DEFINIDO);
        pacienteDom.setIdPais(Constantes.ID_PAIS_MEXICO);
        pacienteDom.setInsertFecha(new Date());
        pacienteDom.setInsertIdUsuario(usuario.getIdUsuario());
        return pacienteDom;
    }
    

    /**
     * Obtiene el surtimiento a partir del idSurtimiento
     *
     * @param idSurtimiento
     * @return
     */
    public Surtimiento_Extend obtenerSurtimiento(String idSurtimiento) {
        Surtimiento_Extend res = null;
        try {
            if (idSurtimiento != null && !idSurtimiento.trim().isEmpty()) {
                res = surService.obtenerSurtimientoExtendedByIdSurtimiento(idSurtimiento);
            }
        } catch (Exception e) {
            LOGGER.error("Error al obtenerSurtimiento :: {} ", e.getMessage());
        }
        return res;
    }

    /**
     * Obtener la prescripcio almacennada e nbase de datos por el idPrescripcion
     * @param idPrescripcion
     * @return
     */
    public Prescripcion obtenerPrescripcion(String idPrescripcion) {
        Prescripcion res = null;
        try {
            if (idPrescripcion != null && !idPrescripcion.trim().isEmpty()) {
                res = preService.obtener(new Prescripcion(idPrescripcion));
            }
        } catch (Exception e) {
            LOGGER.error("Error al obtenerPrescripcion :: {} ", e.getMessage());
        }
        return res;
    }

    /**
     * Valida que la fecha y hora de cancelación o edicion sea menor a la fecha
     * de programacion de la prescripción basandose en el parámetro de
     * configuración, inicialmente 30 minutos
     *
     * @param fechaPrescripcionProgramada
     * @param fechaCancelacion
     * @param noMinutosParaCancelar
     * @return
     */
    public boolean isFechaHotaCancelaValida(Date fechaPrescripcionProgramada, Date fechaCancelacion, Integer noMinutosParaCancelar) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.isFechaHotaCancelaValida()");
        boolean res = false;
        try {
            Date fechaMaximaCancelacion = FechaUtil.sumarRestarMinutosFecha(fechaPrescripcionProgramada, -noMinutosParaCancelar);
            Integer difDias = FechaUtil.obtenerNoDiasEntreFechas(fechaPrescripcionProgramada, fechaCancelacion);
            if (difDias != 0) {
                res = true;
            } else if (fechaCancelacion.before(fechaMaximaCancelacion)) {
                res = true;
            }
        } catch (Exception e) {
            LOGGER.error("Error al validar la fecha y hora de edicion cancelación :: {} ", e.getMessage());
        }
        return res;
    }

    /**
     * Obtienne el volumen calculado de la dosis prescrita con base en la regla
     * de 3 concentracion => Cannt por Envase, dosis => vol calculado
     * @param dosis
     * @param m
     * @return
     */
    public BigDecimal obtenerVolumenCalculado(BigDecimal dosis, Medicamento m) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.calcularVolumenPrescrito()");
        BigDecimal volumenCalculado = BigDecimal.ZERO;
        try {
            if (m != null && dosis != null) {
                if (m.getConcentracion().compareTo(BigDecimal.ZERO) == 1 && m.getCantPorEnvase() > 0) {
                    BigDecimal concentracion = m.getConcentracion();
                    BigDecimal cantPorEnvase = new BigDecimal(m.getCantPorEnvase());
                    volumenCalculado = (dosis.multiply(cantPorEnvase)).divide(concentracion, 2, RoundingMode.HALF_UP);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al obtener el volumen calculado de la dosis prescrita :: {} ", e.getMessage());
        }
        return volumenCalculado;
    }
    
    
    /**
     * 
     * @param s
     * @param prescripcion
     * @param surtimiento
     * @return 
     */
    public Solucion llenarSolucionConSurtPres(Solucion s, Prescripcion prescripcion, Surtimiento_Extend surtimiento) {
        try {
            if (s == null) {
                s = new Solucion();
                s.setIdSolucion(Comunes.getUUID());
                s.setIdSurtimiento(surtimiento.getIdSurtimiento());
                s.setIdEstatusSolucion(EstatusSolucion_Enum.PRESCRIPCION_SOLICITADA.getValue());
                s.setIdEnvaseContenedor(surtimiento.getIdContenedor());
                s.setObservaciones(surtimiento.getObservaciones());
                s.setVolumenFinal((surtimiento.getVolumenTotal() != null) ? surtimiento.getVolumenTotal().toString() : "0");
                s.setVolumen((surtimiento.getVolumenTotal() != null) ? BigDecimal.valueOf(surtimiento.getVolumenTotal().doubleValue()) : BigDecimal.ZERO);
                s.setInstruccionesPreparacion(surtimiento.getInstruccionPreparacion());
                s.setProteccionLuz((surtimiento.isProteccionLuz()) ? 1 : 0);
                s.setProteccionTemp((surtimiento.isTempAmbiente()) ? 1 : 0);
                s.setProteccionTempRefrig((surtimiento.isTempRefrigeracion()) ? 1 : 0);
                s.setNoAgitar((surtimiento.isNoAgitar()) ? 1 : 0);
                s.setEdadPaciente(surtimiento.getEdadPaciente());
                s.setPesoPaciente(surtimiento.getPesoPaciente());
                s.setTallaPaciente(surtimiento.getTallaPaciente());
                s.setAreaCorporal(surtimiento.getAreaCorporal());
                s.setIdViaAdministracion(prescripcion.getIdViaAdministracion());
                s.setPerfusionContinua((surtimiento.isPerfusionContinua()) ? 1 : 0);
                s.setVelocidad(surtimiento.getVelocidad());
                s.setRitmo(surtimiento.getRitmo());
                s.setIdProtocolo(surtimiento.getIdProtocolo());
                s.setIdDiagnostico(surtimiento.getIdDiagnostico());
//todo: 28dic2022 obtener la unidad de concentracion 
                s.setUnidadConcentracion(surtimiento.getUnidadConcentracion());
                s.setInsertFecha(new java.util.Date());
                s.setInsertIdUsuario(surtimiento.getInsertIdUsuario());
                s.setUpdateFecha(new java.util.Date());
                s.setUpdateIdUsuario(surtimiento.getInsertIdUsuario());
            } else {
                s.setIdEnvaseContenedor(surtimiento.getIdContenedor());
                s.setObservaciones(surtimiento.getObservaciones());
                s.setVolumenFinal((surtimiento.getVolumenTotal() != null) ? surtimiento.getVolumenTotal().toString() : "0");
                s.setVolumen((surtimiento.getVolumenTotal() != null) ? BigDecimal.valueOf(surtimiento.getVolumenTotal().doubleValue()) : BigDecimal.ZERO);
                s.setInstruccionesPreparacion(surtimiento.getInstruccionPreparacion());
                s.setProteccionLuz((surtimiento.isProteccionLuz()) ? 1 : 0);
                s.setProteccionTemp((surtimiento.isTempAmbiente()) ? 1 : 0);
                s.setProteccionTempRefrig((surtimiento.isTempRefrigeracion()) ? 1 : 0);
                s.setNoAgitar((surtimiento.isNoAgitar()) ? 1 : 0);
                s.setEdadPaciente(surtimiento.getEdadPaciente());
                s.setPesoPaciente(surtimiento.getPesoPaciente());
                s.setTallaPaciente(surtimiento.getTallaPaciente());
                s.setAreaCorporal(surtimiento.getAreaCorporal());
                s.setIdViaAdministracion(prescripcion.getIdViaAdministracion());
                s.setPerfusionContinua((surtimiento.isPerfusionContinua()) ? 1 : 0);
                s.setVelocidad(surtimiento.getVelocidad());
                s.setRitmo(surtimiento.getRitmo());
                s.setIdProtocolo(prescripcion.getIdProtocolo());
                s.setIdDiagnostico(prescripcion.getIdDiagnostico());
//todo: 28dic2022 obtener la unidad de concentracion 
                s.setUnidadConcentracion(surtimiento.getUnidadConcentracion());
                s.setUpdateFecha(new java.util.Date());
                s.setUpdateIdUsuario(surtimiento.getInsertIdUsuario());
            }
            return s;
        } catch (Exception e) {
            LOGGER.error("Error al  llenar solucion con surtimiento :: {} ", e.getMessage());
            return null;
        }
    }
    
    
    /**
     * Busca insumos en los almaceens específicos por tipo de mezcla : ONC: NPT: ANT
     * @param cadena
     * @param idTipoSolucion
     * @param idEstructura
     * @return 
     * @throws java.lang.Exception 
     */
    public List<Medicamento_Extended> autoCompleteMedicamentosPorTipoMezcla(
            String cadena
            , String idTipoSolucion
            , String idEstructura )  throws Exception {
        LOGGER.trace("mx.mc.commons.SolucionUtils.autoCompleteMedicamentos()");
        List<Medicamento_Extended> list = new ArrayList<>();
        try {
            if (idEstructura == null || idEstructura.trim().isEmpty()) {
                throw new Exception("Seleccione un servicio válido.");

            } else if (idTipoSolucion == null || idTipoSolucion.trim().isEmpty()) {
                throw new Exception("Seleccione un tipo de solución.");
                
            } else {
                List<String> idEstructuraList = new ArrayList<>();                
                
                idEstructuraList.addAll(obtieneAlmacenesPadresPorIdTipoSolucion(idTipoSolucion));
                idEstructuraList.addAll(obtieneAlmacenesPorIdTipoSolucion(idTipoSolucion));
                
                boolean activaAutoCompleteInsumos = true;
                list.addAll(this.medService.searchMedicamentoAutoCompleteMultipleAlmConsolida(cadena.trim(), idEstructuraList, activaAutoCompleteInsumos));
            }
        } catch (Exception e) {
            LOGGER.error("Error al buscar insumos por autocomplete :: {} ", e.getMessage());
            throw new Exception("Error al consultar insumos por autocomplete .");
        }
        return list;
    }
    
    
    /**
     * Determina los almacenes que pueden tener existencias 
     * para de susrtir los innsumos para cada mezcla por tipo de solucion
     * Pero que sólo reabastecerán las áreas asépticas
     * 
     * @param idTipoSolucion
     * @return 
     */
    public List<String> obtieneAlmacenesPadresPorIdTipoSolucion(String idTipoSolucion){
        LOGGER.trace("mx.mc.commons.SolucionUtils.obtieneAlmacenesPadresPorIdTipoSolucion()");
        List<String> idEstructuraList = new ArrayList<>();
// TODO: 30abr2024 : Extraer de código Duro los almacenes y subalmacenes
        idEstructuraList.add("475c1ce7-b86a-455f-925f-f6033dd0be28"); //	Materiales Indirectos
        idEstructuraList.add("f4010316-b096-447c-a9f5-b4bfb6037498"); //	Materiales Generales
// ONC
        if (idTipoSolucion.equalsIgnoreCase("e68ac222-0a63-11eb-a03a-000c29750049") || idTipoSolucion.equalsIgnoreCase("ONC")) {
            idEstructuraList.add("22d73e40-c32d-4d39-9ddd-932ac645e11d"); //	Almacén Oncológicos
// ANT
        } else if (idTipoSolucion.equalsIgnoreCase("e69f9857-0a63-11eb-a03a-000c29750049") || idTipoSolucion.equalsIgnoreCase("ANT")) {
            idEstructuraList.add("7d5746b2-2362-46ff-a40d-2292e78a2f34"); //	Almacén Antimicrobianos
// NPT
        } else if (idTipoSolucion.equalsIgnoreCase("e695144c-0a63-11eb-a03a-000c29750049") || idTipoSolucion.equalsIgnoreCase("NPT")){
            idEstructuraList.add("3ef5250a-b098-497b-b178-217dd2619ca9"); //	Almacén NPT
        } else {
            idEstructuraList.add("ff6d8e2f-6659-11ee-9444-e7654324e71b"); // se evía un uuid de almacén inexistente
        }
        return idEstructuraList;
    }
    
  
    /**
     * Determina los almacenes que deben de susrtir los innsumos para cada mezcla por tipo de solucion
     * @param idTipoSolucion
     * @return 
     */
    public List<String> obtieneAlmacenesPorIdTipoSolucion(String idTipoSolucion){
        LOGGER.trace("mx.mc.commons.SolucionUtils.obtieneAlmacenesPorTipoSolucion()");
        List<String> idEstructuraList = new ArrayList<>();
// TODO: 30abr2024 : Extraer de código Duro los almacenes y subalmacenes
// ONC
        if (idTipoSolucion.equalsIgnoreCase("e68ac222-0a63-11eb-a03a-000c29750049") || idTipoSolucion.equalsIgnoreCase("ONC")) {
            idEstructuraList.add("c6365aac-5424-4632-9382-8e6c4a4bbe72"); // alm asep onc
            idEstructuraList.add("cd775aad-2b0d-4aaf-9e0a-d37cdc1b8dfe"); // alm asep virt ind onc
            idEstructuraList.add("bc23ea31-e62c-49a7-b8bc-c1b7ceb6a6ca"); // alm asep virt mat onc
// ANT
        } else if (idTipoSolucion.equalsIgnoreCase("e69f9857-0a63-11eb-a03a-000c29750049") || idTipoSolucion.equalsIgnoreCase("ANT")) {
            idEstructuraList.add("e783588a-7076-4bc0-afd2-b1067cd8d38a"); // alm asep ant
            idEstructuraList.add("2bd7621f-6c8c-48a0-afaf-3f14456075d1"); // alm asep virt ind ant
            idEstructuraList.add("a4132042-a584-419b-b046-50d313c622d7"); // alm asep virt mat ant
// NPT
        } else if (idTipoSolucion.equalsIgnoreCase("e695144c-0a63-11eb-a03a-000c29750049") || idTipoSolucion.equalsIgnoreCase("NPT")){
            idEstructuraList.add("a79709db-128b-4a91-a85f-81cb5cd37774"); // alm asep npt
            idEstructuraList.add("d811be54-e44a-4f27-986d-7b25380b5043"); // alm asep virt ind npt
            idEstructuraList.add("5468f85d-14b1-4db5-bdf1-03f0a2636f8a"); // alm asep virt mat ant
        } else {
            idEstructuraList.add("ff6d8e2f-6659-11ee-9444-e7654324e71b"); // se evía un uuid de almacén inexistente
        }
        return idEstructuraList;
    }

    public boolean permisoVerTipoSolucion(Usuario usuarioSelected, String tipoSoluicion) {
        boolean noPuedeVerRegistro = false;
        if(usuarioSelected.getIdTipoUsuario() == TipoUsuario_Enum.MEDICO.getValue()) {
            if(usuarioSelected.getIdTipoSolucion() != null || usuarioSelected.getIdTipoSolucion() != "") {
                String listaTipoSoluciones[] = usuarioSelected.getIdTipoSolucion().split(",");
                for(String idTipoSolucion :listaTipoSoluciones) {
                    if(tipoSoluicion.equals(idTipoSolucion.trim())) {
                        noPuedeVerRegistro = true;
                        break;
                    }
                }
            }
        } else {
            noPuedeVerRegistro = true;
        }
    
        return noPuedeVerRegistro;
    }
    
    public boolean permisoCrearSolucionNPT(Usuario usuarioSelected) throws Exception {
        boolean puedeCrearSolNPT = false;
        try {
            TipoSolucion tipoSol = new TipoSolucion();
            tipoSol.setClave("NPT");
            TipoSolucion tipoSolNPT = tipSolService.obtener(tipoSol);
            if(usuarioSelected.getIdTipoSolucion() != null || usuarioSelected.getIdTipoSolucion() != "") {
                String listaTipoSoluciones[] = usuarioSelected.getIdTipoSolucion().split(",");
                for(String idTipoSolucion :listaTipoSoluciones) {
                    if(tipoSolNPT.getIdTipoSolucion().equals(idTipoSolucion.trim())) {
                        puedeCrearSolNPT = true;
                        break;
                    }
                }
            }
        } catch(Exception ex) {
            LOGGER.error("Error evaluar si puede crear soluciones NPT :: {} ", ex.getMessage());
            throw new Exception("Error al consultar y validar si el usuario puede crear soluciones NPT.");
        }        
        return puedeCrearSolNPT;
    }
    
//    /**
//     * Método que obtiene las listas a actualizar, insertar y cancelar de una prescripción insumo
//     * Posterior a la modificación de una mezcla (validación de la mezcla)
//     * @param listaPrescripcionInsumo
//     * @param listaPrescripcionInsumoAnt
//     * @param idUsuario
//     * @param piListActulizar
//     * @param piListInsertar
//     * @param piListBorrar
//     * @return 
//     */
//    public List<PrescripcionInsumo> obtenerListasPrescripcionInsumo(
//            List<PrescripcionInsumo> listaPrescripcionInsumo 
//            , List<PrescripcionInsumo> listaPrescripcionInsumoAnt
//            , String idUsuario
//            , List<PrescripcionInsumo> piListActulizar
//            , List<PrescripcionInsumo> piListInsertar
//            , List<PrescripcionInsumo> piListBorrar) {
//        
//        boolean encontrado = false;
//
//        for (PrescripcionInsumo nueva : listaPrescripcionInsumo) {                   // vinc, cloru
//            for (PrescripcionInsumo anterior : listaPrescripcionInsumoAnt) {                   // vinc, cloru
//                if (nueva.getIdInsumo().equalsIgnoreCase(anterior.getIdInsumo())) {
//                    anterior.setUpdateFecha(new java.util.Date());
//                    anterior.setUpdateIdUsuario(idUsuario);
//                    piListActulizar.add(anterior);
//                    encontrado = true;
//                    break;
//                }
//            }
//            if (!encontrado) {
//                piListInsertar.add(nueva);
//            }
//            encontrado = false;
//        }
//        for (PrescripcionInsumo anterior : listaPrescripcionInsumoAnt) {
//            for (PrescripcionInsumo actual : piListActulizar) {
//                if (anterior.getIdInsumo().equalsIgnoreCase(actual.getIdInsumo())) {
//                    encontrado = true;
//                    break;
//                }
//            }
//            if (!encontrado) {
//                anterior.setIdEstatusPrescripcion(EstatusPrescripcion_Enum.CANCELADA.getValue());
//                anterior.setUpdateFecha(new java.util.Date());
//                anterior.setUpdateIdUsuario(idUsuario);
//                piListBorrar.add(anterior);
//            }
//        }
//        return null;
//    }
//    
//    /**
//     * Método que obtiene las listas a actualizar, insertar y cancelar de un surtimiento insumo
//     * Posterior a la modificación de una mezcla (validación de la mezcla)
//     * @param listaSurtimientoInsumo
//     * @param listaSurtimientoInsumoAnt
//     * @param idUsuario
//     * @param siListActulizar
//     * @param siListInsertar
//     * @param siListBorrar
//     * @return 
//     */
//    public List<SurtimientoInsumo_Extend> obtenerListasSurtimientoInsumo(
//            Surtimiento_Extend se,
//            List<SurtimientoInsumo_Extend> listaSurtimientoInsumo,
//             List<SurtimientoInsumo_Extend> listaSurtimientoInsumoAnt,
//             String idUsuario,
//             List<SurtimientoInsumo> siListActulizar,
//             List<SurtimientoInsumo> siListInsertar,
//             List<SurtimientoInsumo> siListBorrar) {
//        boolean encontrado = false;
//
//        for (SurtimientoInsumo_Extend nueva : listaSurtimientoInsumo) {                   // vinc, cloru
//            for (SurtimientoInsumo_Extend anterior : listaSurtimientoInsumoAnt) {                   // vinc, cloru
//                if (nueva.getIdPrescripcionInsumo().equalsIgnoreCase(anterior.getIdPrescripcionInsumo())) {
//                    anterior.setUpdateFecha(new java.util.Date());
//                    anterior.setUpdateIdUsuario(idUsuario);
//                    siListActulizar.add(anterior);
//                    encontrado = true;
//                    break;
//                }
//            }
//            if (!encontrado) {
//                nueva.setIdSurtimiento(se.getIdSurtimiento());
//                nueva.setFechaProgramada(se.getFechaProgramada());
//                nueva.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
//                nueva.setCantidadVale(0);
//                nueva.setInsertFecha(new java.util.Date());
//                nueva.setInsertIdUsuario(idUsuario);
//                nueva.setIdEstatusMirth(1);
//                siListInsertar.add(nueva);
//            }
//            encontrado = false;
//        }
//        for (SurtimientoInsumo anterior : listaSurtimientoInsumoAnt) {
//            for (SurtimientoInsumo actual : siListActulizar) {
//                if (anterior.getIdPrescripcionInsumo().equalsIgnoreCase(actual.getIdPrescripcionInsumo())) {
//                    encontrado = true;
//                    break;
//                }
//            }
//            if (!encontrado) {
//                anterior.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.CANCELADO.getValue());
//                anterior.setUpdateFecha(new java.util.Date());
//                anterior.setUpdateIdUsuario(idUsuario);
//                siListBorrar.add(anterior);
//            }
//        }
//
//        return null;
//
//    }


    public String periodoFrecuenciaDistinto(List<SurtimientoInsumo_Extend> sel, List<String> surtIdInsumoLista) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.periodoFrecuenciaDistinto()");
        String res = null;
        Integer dias = 0;
        Integer frec = 0;
        Integer cont = 0;
        for (SurtimientoInsumo_Extend item : sel) {
            surtIdInsumoLista.add(item.getIdInsumo());
            if (cont == 0) {
                dias = item.getDuracion();
                frec = item.getFrecuencia();
            } else {
                if (!Objects.equals(item.getDuracion(), dias)) {
                    res = "Periodo ";
                    break;
                } else if (!Objects.equals(item.getFrecuencia(), frec)) {
                    res = "Frecuencia ";
                    break;
                }
            }
            cont++;
        }
        return res;
    }

    /**
     * Determina el estatus de la solución con base en el idMotivoRechazo de la mezcla
     * @param idMotivoRechazo
     * @return 
     */
    public Integer obtenerEstatusSolucionPorMotivoRechazo(Integer idMotivoRechazo){
        LOGGER.trace("mx.mc.commons.SolucionUtils.obtenerEstatusSolucionPorMotivoRechazo()");
        Integer res = null;
        try {
            switch (idMotivoRechazo){
                default : res = null; break;
                case 1:     // Alta de paciente
                case 2:     // Cambio de Tratamiento
                case 4:     // Diagnóstico de alta
                case 5:     // Egreso de paciente
                case 16:    // OTRO
                    res = EstatusSolucion_Enum.CANCELADA.getValue();
                    break; 
                case 6:     // Error en preparación 
                case 14:    // Condiciones de preparación incorrectas
                case 15:    // Condiciones de conservación incorrectas
                case 13:    // Medicamento caduco
                case 12:    // Medicamento contaminado
                    res = EstatusSolucion_Enum.MEZCLA_ERROR_AL_PREPARAR.getValue();
                    break;
                case 3:     // Datos incompletos
                case 7:     // Error en prescripción
                case 8:     // Mezcla inestable
                case 9:     // Sospecha de evento Adverso 
                case 10:    // Reacción de hipersensibilidad
                case 11:    // Sospecha de RAM
                case 17:    // Cantidades insuficientes
                    res = EstatusSolucion_Enum.OP_RECHAZADA.getValue();
                    break;
            }
        } catch(Exception e){
            LOGGER.error("Error al evaluar el motivo de rechazo :: {} ", e.getMessage());
        }
        return res;
    }
    
    
    /**
     * 
     * @param diluyentes
     * @return 
     */
    private List<String> obtenerDiluyentesPermitidos(String diluyentes) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.obtenerDiluyentesPermitidos()");
        List<String> diluyentesCompatiblesLista = null;
        if (diluyentes!= null){
            String[] diluyentesCompatibles = diluyentes.split(Constantes.PARAM_SYSTEM_CARACTER_SEPARADOR);
            diluyentesCompatiblesLista = new ArrayList<>(Arrays.asList(diluyentesCompatibles));
        }
        return diluyentesCompatiblesLista;
    }
    
        
    /**
     * 
     * @param siel 
     * @param va
     * @return  
     */
    public List<String> evaluaEstabilidadDiluyente(List<SurtimientoInsumo_Extend> siel, Integer idViaAdministracion) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.evaluaEstabilidadDiluyente()");
        List<String> msjValidacion = new ArrayList<>();
        try {
            SurtimientoInsumo_Extend reactivo = obtenerFarmacoDiluyente(siel, "farmaco");
            SurtimientoInsumo_Extend diluyente = obtenerFarmacoDiluyente(siel, "diluyente");
            boolean encontrado = false;
            
            if (reactivo != null && reactivo.getIdInsumo() != null && idViaAdministracion != null) {
                String idInsumo = reactivo.getIdInsumo();
                String claveDiluyente = null;
                Integer idFabricante = null;
                Integer idContenedor = null;
                List<Estabilidad_Extended> estabilidadList = estabiService.buscarEstabilidad(
                        idInsumo , claveDiluyente, idViaAdministracion , idFabricante, idContenedor);
                if (estabilidadList != null){
                    
                    for (Estabilidad_Extended e : estabilidadList) {
                        
                        if (diluyente == null) {
                            if (e.getSolucionesCompatibles() == null) {
                                encontrado = true;
                                break;
                            }
                        } else {
                            if (e.getSolucionesCompatibles().contains(diluyente.getClaveInstitucional())) {
                                encontrado = true;
                                break;
                            }
                        }
                    }
                }
            }
            if (!encontrado) {
                msjValidacion.add("No se detecta regla de estabilidad para: " + reactivo.getNombreCorto() + " y diluyete: " + ((diluyente != null) ? diluyente.getNombreCorto() : " ") + " verifique estabilidad.");
            }
        } catch (Exception e) {
            LOGGER.error("Error al validar estabilidades. :: {} ", e.getMessage());
        }
        List<String> msjValidacionSinDuplicados = new ArrayList<>(new LinkedHashSet<>(msjValidacion));
        return msjValidacionSinDuplicados;
    }
    
    /**
     * 
     * @param siel 
     * @param va
     * @return  
     */
    public List<String> evaluaEstabilidadReconstitucion(List<SurtimientoInsumo_Extend> siel, Integer idViaAdministracion, Integer contenedor) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.evaluaEstabilidadReconstitucion()");
        try {
            SurtimientoInsumo_Extend reactivo = obtenerFarmacoDiluyente(siel, "farmaco");
            SurtimientoInsumo_Extend diluyente = obtenerFarmacoDiluyente(siel, "diluyente");
            
            if (reactivo != null && reactivo.getIdInsumo() != null ) {
                String idInsumo = reactivo.getIdInsumo();
                String claveDiluyente = null;
                if (diluyente != null) {
                    claveDiluyente = diluyente.getClaveInstitucional();
                }
//                Integer idViaAdministracion = va.getIdViaAdministracion();
                Integer idFabricante = null;
                
                for (SurtimientoInsumo_Extend item : siel){
                    if (reactivo.getIdInsumo().equals(item.getIdInsumo())){
                        if (item.getSurtimientoEnviadoExtendList() == null){
                            break;
                        } else {
                            if (item.getSurtimientoEnviadoExtendList().get(0) != null ){
                                if (item.getSurtimientoEnviadoExtendList().get(0).getIdInventarioSurtido() != null) {
                                    Inventario o = new Inventario();
                                    o.setIdInventario(item.getSurtimientoEnviadoExtendList().get(0).getIdInventarioSurtido());
                                    Inventario i = invenService.obtener(o);
                                    if (i != null) {
                                        idFabricante = i.getIdFabricante();
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                Integer idContenedor = (contenedor == 2 || contenedor == 32) ? contenedor : null;
                List<Estabilidad_Extended> estabilidadList = estabiService.buscarEstabilidad(idInsumo , claveDiluyente, idViaAdministracion , idFabricante, idContenedor );
                
                List<String> msjValidacion = new ArrayList<>();

                if (estabilidadList == null){
                    msjValidacion.add("El insumo " + reactivo.getNombreCorto() + " es inestable en el diluyete prescrito. ");
                    
                } else if (estabilidadList.isEmpty()){
                    msjValidacion.add("El insumo " + reactivo.getNombreCorto() + " es inestable en el diluyete prescrito. ");
                    
                } else {
                    for (Estabilidad_Extended e : estabilidadList) {
                        msjValidacion.add(reactivo.getNombreCorto() );
                        
                        if (e.getDiluyenteReconst() != null && !e.getDiluyenteReconst().trim().isEmpty() && !e.getDiluyenteReconst().trim().equals("NO APLICA")) {
                            msjValidacion.add("Debe reconstituirse en: " + e.getVolumenReconst() + " mL de " + e.getDiluyenteReconst() + " para una concetración de: " + e.getConcentracionReconst() + " " + obtieneUniConcen(e.getIdUnidadReconst()) + " / mL." );
                        }
// TODO: 02abr2024: se restringe las condiciones de coservación del frasco 

                        if (e.getLimHrsUsoRedFria() != null ) {
                            msjValidacion.add("Estable " + e.getLimHrsUsoRedFria() + " hrs en red fría.");
                        }

                        if (e.getLimHrsUsoRedSeca() != null ) {
                            msjValidacion.add("Estable " + e.getLimHrsUsoRedSeca() + " hrs en temperatura ambiente.");
                        }
                        
                        if (e.getFotosensible() != null && e.getFotosensible() == 1) {
                            msjValidacion.add("Es fotosensible.");
                        }
                        if (e.getNoAgitar() != null && e.getNoAgitar() == 1) {
                            msjValidacion.add("No agitar.");
                        }
                        
                        if (e.getObservacionesPreparacion() != null && !e.getObservacionesPreparacion().trim().isEmpty()) {
                            msjValidacion.add(e.getObservacionesPreparacion() );
                        }
                    }
                    
                }
                
                List<String> msjValidacionSinDuplicados = new ArrayList<>(new LinkedHashSet<>(msjValidacion));
                return msjValidacionSinDuplicados;
            }
            
        } catch (Exception e) {
            LOGGER.error("Error al validar estabilidades. :: {} ", e.getMessage());
        }
        return null;
    }
    
    private String obtieneUniConcen(Integer idUniConcet){
        String res = "";
        try {
            switch (idUniConcet) {
                case 1 : 	res = "mg"; break;
                case 2 :	res = "mL"; break;
                case 3 :	res = "unidad"; break;
                case 4 :	res = "g"; break;
                case 5 :	res = "UI"; break;
                case 6 :	res = "gr"; break;
                case 7 :	res = "µg"; break;
                case 8 :	res = "NO DEFINIDA"; break;
                case 9 :	res = "%"; break;
                case 10 :	res = "mEq/ml"; break;
                case 11 :	res = "% P/V"; break;
                case 12 :	res = "mEq"; break;
                case 13 :       res = "mg"; break;
            }
        } catch (Exception e) {
            LOGGER.error("Error al obtener concetracion :: {} ", e.getMessage());
        }
        return res;
    }
    
    /**
     * Obtiene Medicamento
     * @param idMedicamento
     * @return 
     */
    public Medicamento obtenerInsumoPorId(String idMedicamento) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.obtenerInsumoPorId()");
        Medicamento m = null;
        try {
            m = medService.obtenerPorIdMedicamento(idMedicamento);
        } catch (Exception ex) {
            LOGGER.error("Error al consultar medicamento por idMedicameto :: {} ", ex.getMessage());
        }
        return m;
    }
    
    /**
     * Obtener la clave de la solución que es un nuevo insumo
     *
     * @param surtimientoExtendedSelected
     * @return
     */
    public String obtenerClaveSolucion(Surtimiento_Extend surtimientoExtendedSelected) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.obtenerClaveSolucion()");
        String res = null;
        try {
            String folio = surtimientoExtendedSelected.getFolio();
            String complementoLote = "";
//            DateFormat dateFormat = new SimpleDateFormat("ddMMyy");
// Es la fecha de cuando se surte y no de la prescripcion/surtimiento
//            String fechaProg = dateFormat.format(new Date());
//            String lote = complementoLote.concat(fechaProg);
            String lote = folio;

//Genera caducidad de clve Agrupada, la cual se le agrega 1 dia mas.
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(surtimientoExtendedSelected.getFechaProgramada());
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            res = CodigoBarras.generaCodigoDeBarras(folio, lote, calendar.getTime(), null);
        } catch (Exception e) {
            LOGGER.error("Error al obtenerClaveSolucion {} ", e.getMessage());
        }
        return res;
    }
    
//    /**
//     * 
//     * @param fechaPrepara      solucionSelect.getFechaPrepara()
//     * @param estabilidadMezcla 
//     */
//    public Date calculaCaducidadMezcla(Date fechaPrepara, Integer estabilidadMezcla) {
//        LOGGER.trace("mx.mc.commons.SolucionUtils.calculaCaducidadMezcla()");
//        Date caducidadMezcla = new java.util.Date();
//        
//        if (estabilidadMezcla > 0) {
//            if (fechaPrepara != null) {
//                caducidadMezcla = FechaUtil.sumarRestarHorasFecha(fechaPrepara, estabilidadMezcla);
//            } else {
//                caducidadMezcla = FechaUtil.sumarRestarHorasFecha(new java.util.Date(), estabilidadMezcla);
//            }
//        }
//        return caducidadMezcla;
//    }


    /**
     * Método que obtiene la estabilidad en horas mas proxima de un insumo que
     * forma parte de la mezcla
     * @param siel
     * @param idViaAdmon
     * @return 
     */
    public Integer obtenerMinimaEstabilidaInsumo(List<SurtimientoInsumo_Extend> siel, Integer idviaAdministracion) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.obtenerMinimaEstabilidaInsumo()");
        
        Integer estabilidadProxima = 24;
        boolean encontrado = false;
        
        Estabilidad e = null;
        try {
            SurtimientoInsumo_Extend reactivo = obtenerFarmacoDiluyente(siel, "farmaco");
            SurtimientoInsumo_Extend diluyente = obtenerFarmacoDiluyente(siel, "diluyente");
            String claveDiluyente = null;
            if (reactivo != null && reactivo.getIdInsumo() != null) {
                String idInsumo = reactivo.getIdInsumo();
                if(diluyente != null) {
                    claveDiluyente = diluyente.getClaveInstitucional();
                }                
                Integer idViaAdministracion = idviaAdministracion;
                Integer idFabricante = null;

                for (SurtimientoInsumo_Extend item : siel) {
                    if (reactivo.getIdInsumo().equals(item.getIdInsumo())) {
                        if (item.getSurtimientoEnviadoExtendList() == null) {
                            break;
                        } else {
                            if (item.getSurtimientoEnviadoExtendList().get(0) != null) {
                                if (item.getSurtimientoEnviadoExtendList().get(0).getIdInventarioSurtido() != null) {
                                    Inventario o = new Inventario();
                                    o.setIdInventario(item.getSurtimientoEnviadoExtendList().get(0).getIdInventarioSurtido());
                                    Inventario i = invenService.obtener(o);
                                    if (i != null) {
                                        idFabricante = i.getIdFabricante();
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                Integer idContenedor = null;
                List<Estabilidad_Extended> estabilidadList = estabiService.buscarEstabilidad(idInsumo, claveDiluyente, idViaAdministracion, idFabricante, idContenedor);

                for (Estabilidad_Extended item : estabilidadList) {
                    if (!encontrado) {
                        // Si la estabilidad del farmaco registrada contiene vía de administración y coincide con la prescripción
                        if (Objects.equals(item.getIdViaAdministracion(), idViaAdministracion)) {
                            if (item.getReglasDePreparacion() != null) {
                                e = item;
                                encontrado = true;
                                break;
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al buscar estabilidad :: {} ", ex.getMessage());
        }
        
        Integer estabilidadRedFria = 0;
        Integer estabilidadTemAmb = 0;
        if (encontrado && e != null) {
            if (e.getLimHrsUsoRedFria() != null 
                    && e.getLimHrsUsoRedFria() > 0 ) {
                estabilidadRedFria = e.getLimHrsUsoRedFria();
            }
            if (e.getLimHrsUsoRedSeca() != null 
                    && e.getLimHrsUsoRedSeca() > 0 ) {
                estabilidadTemAmb = e.getLimHrsUsoRedSeca();
            }
            if (estabilidadRedFria > estabilidadTemAmb) {
                estabilidadProxima  = estabilidadRedFria;
            } else {
                estabilidadProxima  = estabilidadTemAmb;
            }
            
        } else {
            if (siel != null) {
                for (SurtimientoInsumo_Extend si : siel) {
                    if (si.getNoHorasestabilidad() != null) {
                        if (si.getNoHorasestabilidad() > 0) {
                            if (si.getNoHorasestabilidad() < estabilidadProxima) {
                                estabilidadProxima = si.getNoHorasestabilidad();
                            }
                        }
                    }
                }
            }
        }
        return estabilidadProxima;
    }
    
    /**
     * 
     * @param siel
     * @param va
     * @return 
     */
    public Estabilidad_Extended obtenerEstabilidad (List<SurtimientoInsumo_Extend> siel, ViaAdministracion va) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.obtenerEstabilidad()");
        Estabilidad_Extended e = null;
        try {
            SurtimientoInsumo_Extend reactivo = obtenerFarmacoDiluyente(siel, "farmaco");
            SurtimientoInsumo_Extend diluyente = obtenerFarmacoDiluyente(siel, "diluyente");
            
            if (reactivo != null && reactivo.getIdInsumo() != null ) {                
                String idInsumo = reactivo.getIdInsumo();
                String claveDiluyente = null;
                if (diluyente != null){
                    claveDiluyente = diluyente.getClaveInstitucional();
                }
                Integer idViaAdministracion = va.getIdViaAdministracion();
                Integer idFabricante = null;
                
                for (SurtimientoInsumo_Extend item : siel){
                    if (reactivo.getIdInsumo().equals(item.getIdInsumo())){
                        if (item.getSurtimientoEnviadoExtendList() == null){
                            break;
                        } else {
                            if (item.getSurtimientoEnviadoExtendList().get(0) != null ){
                                if (item.getSurtimientoEnviadoExtendList().get(0).getIdInventarioSurtido() != null) {
                                    Inventario o = new Inventario();
                                    o.setIdInventario(item.getSurtimientoEnviadoExtendList().get(0).getIdInventarioSurtido());
                                    Inventario i = invenService.obtener(o);
                                    if (i != null) {
                                        idFabricante = i.getIdFabricante();
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                if (idFabricante != null) {
                    e = estabiService.obtenerEstabilidad(idInsumo , claveDiluyente, idViaAdministracion , idFabricante );
                }
            }
        } catch (Exception ex) {
            LOGGER.trace("Error al obtener la estabildad :: {} ", ex.getMessage());
        }
        return e;
    }

    /**
     * Valida si ya existe un paciente por su Número de Paciente
     * @param paciente
     * @return 
     */
    private String validarClavePaciente(Paciente paciente) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.validarClavePaciente()");
        String resp = null;
        try {
            Paciente pacienteTem = new Paciente();
            pacienteTem.setPacienteNumero(paciente.getPacienteNumero());
            Paciente pacienteResp = pacService.obtener(pacienteTem);
            if (pacienteResp != null) {
                resp = RESOURCES.getString("prc.manual.clave.paciente");
            }
        } catch (Exception ex) {
            LOGGER.error("error en el metodo validarClavePaciente :: {}", ex.getMessage());
        }
        return resp;
    }

    /**
     * Valida que los datos del nuevo paciente estén completos y no se duplique el campo pacienteNumero
     * @param paciente
     * @param idEstructura
     * @param idCama
     * @return 
     */
    public String validarDatosPaciente(Paciente paciente, String idEstructura, String idCama) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.validarDatosPaciente()");
        if (idEstructura == null || idEstructura.trim().isEmpty()) {
            return RESOURCES.getString("prc.manual.servicio.req");

        } else if (idCama== null || idCama.trim().isEmpty()) {
            return RESOURCES.getString("prc.manual.cama.req");

        } else if (paciente == null) {
            return RESOURCES.getString("prc.manual.numeroPaciente.paciente");

        } else if (paciente.getPacienteNumero()== null || paciente.getPacienteNumero().isEmpty()) {
            return RESOURCES.getString("prc.manual.numeroPaciente.paciente");

        } else if (paciente.getNombreCompleto() == null || paciente.getNombreCompleto().isEmpty()) {
            return RESOURCES.getString("prc.manual.nombre.paciente");

        } else if (paciente.getApellidoPaterno() == null || paciente.getApellidoPaterno().isEmpty()) {
            return RESOURCES.getString("prc.manual.app.paciente");

        } else if (paciente.getSexo() == '\0' || paciente.getSexo() == '\u0000') {
            return RESOURCES.getString("prc.manual.sex.paciente");

        } else if (paciente.getFechaNacimiento() == null) {
            return RESOURCES.getString("prc.manual.fechaNac.paciente");

        } else {
            return validarClavePaciente(paciente);
        }
    }
    
    
    /**
     * Genera los datos de una visita para el paciente registrado
     * @param idPaciente
     * @param idUsuario
     * @param fechaAct
     * @return 
     */
    public Visita generaVisita(String idPaciente, String idUsuario, Date fechaAct) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.generaVisita()");
        Visita v = new Visita();
        v.setIdVisita(Comunes.getUUID());
        v.setIdPaciente(idPaciente);
        v.setFechaIngreso(fechaAct);
        v.setIdUsuarioIngresa(idUsuario);
        v.setIdMotivoPacienteMovimiento(MotivoPacienteMovimiento_Enum.ADMISION.getValue());
        v.setMotivoConsulta("");
        v.setInsertFecha(fechaAct);
        v.setInsertIdUsuario(idUsuario);
        return v;
    }

    /**
     * Genera un objeti de Paciente Servicio para el paciente registrado
     * @param idVisita
     * @param idEstructura
     * @param idUsuario
     * @param fechaAct
     * @return 
     */
    public PacienteServicio generaPacienteServicio(String idVisita, String idEstructura, String idUsuario, Date fechaAct) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.generaPacienteServicio()");
        PacienteServicio pacSer = new PacienteServicio();
        pacSer.setIdPacienteServicio(Comunes.getUUID());
        pacSer.setIdVisita(idVisita);
        pacSer.setIdEstructura(idEstructura);
        pacSer.setFechaAsignacionInicio(fechaAct);
        pacSer.setIdUsuarioAsignacionInicio(idUsuario);
        pacSer.setIdMotivoPacienteMovimiento(MotivoPacienteMovimiento_Enum.ADMISION.getValue());
        pacSer.setInsertFecha(fechaAct);
        pacSer.setInsertIdUsuario(idUsuario);
        return pacSer;
    }

    /**
     * Geera los datos de la asignación a cama para el paciente registrado
     * @param idPacienteServicio
     * @param idCama
     * @param idUsuario
     * @param fechaAct
     * @return 
     */
    public PacienteUbicacion generaPacienteUbicacion(String idPacienteServicio, String idCama, String idUsuario, Date fechaAct) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.generaPacienteUbicacion()");
        PacienteUbicacion pacUbi = new PacienteUbicacion();
        pacUbi.setIdPacienteUbicacion(Comunes.getUUID());
        pacUbi.setIdPacienteServicio(idPacienteServicio);
        pacUbi.setIdCama(idCama);
        pacUbi.setFechaUbicaInicio(fechaAct);
        pacUbi.setIdUsuarioUbicaInicio(idUsuario);
        pacUbi.setIdEstatusUbicacion(1);
        pacUbi.setInsertFecha(fechaAct);
        pacUbi.setInsertIdUsuario(idUsuario);
        return pacUbi;
    }
    

//    /**
//     * Compara el volumen total contra dosis prescrita de diluyente
//     * @param idInsumo
//     * @param volumenTotal
//     * @param dosis
//     * @return 
//     */
//    public String comparaVolTotalVolPrescrito(String idInsumo, BigDecimal volumenTotal, BigDecimal dosis) {
//        LOGGER.trace("mx.mc.commons.SolucionUtils.comparaVolTotalVolPrescrito()");
//        String msg = null;
//        try {
//            if (idInsumo != null && !idInsumo.trim().isEmpty()) {
//                Medicamento m = medService.obtenerMedicamento(idInsumo);
//                if (m.isDiluyente()) {
//                    if (dosis != volumenTotal) {
//                        msg = "El Volumen Total de la mezcla difiere de la dosis prescrita del diluyente. ";
//                    }
//                }
//            }
//        } catch (Exception e) {
//            LOGGER.error("Error al validar volumen total contra volumen Prescrito :: {} ", e.getMessage());
//        }
//        return msg;
//    }


    /**
     * Valida queel volumen total coincida con la dosis prescrita del diluente.
     * @param m
     * @param dosisPres
     * @param volumenTotal
     * @return 
     */
    public String validaVolTotalVolDiluyente(BigDecimal dosisPres, BigDecimal volumenTotal) {
        LOGGER.trace("mx.mc.commons.SolucionUtils.validaVolTotalVolDiluyente()");
        String res = null;
        if (dosisPres != null && volumenTotal != null) {
            if (dosisPres.compareTo(volumenTotal) != 0) {
                res = "El Volumen Total de la mezcla " + volumenTotal.toString() + " mL, difiere de la dosis prescrita del diluyente " + dosisPres.toString() + " mL.";
            }
        }
        return res;
    }

    /**
     * Obtiene el numero de prescripciones procesadas para validar 
     * la modificación de datos de prescripción de meclas ya preparadas
     * @param idPrescripcion
     * @return 
     */
    public Integer obtenerNumeroSolucionesProcesadas(String idPrescripcion){
        LOGGER.trace("mx.mc.commons.SolucionUtils.obtenerNumeroSolucionesProcesadas()");
        Integer numeroMezclasProcesadas = 0;
        try {
            numeroMezclasProcesadas = solService.obtenerNumeroSolucionesProcesadas(idPrescripcion);
        }catch(Exception e){
            LOGGER.error("Error al buscar prescripcion de mezcla procesada :: {} ", e.getMessage());
        }
        return numeroMezclasProcesadas;
    }
    
    public List<SurtimientoInsumo_Extend> eliminarRegistrosDuplicados(List<SurtimientoInsumo_Extend> surtimientoInsumoExtendedList) {
        boolean agrega = false;
        List<SurtimientoInsumo_Extend> listaAux = new ArrayList<>();
        for (SurtimientoInsumo_Extend si : surtimientoInsumoExtendedList) {
            if (listaAux.isEmpty()) {
                listaAux.add(si);
            } else {
                for (SurtimientoInsumo_Extend siAux : listaAux) {
                    if (si.getIdSurtimientoInsumo().equals(siAux.getIdSurtimientoInsumo())) {
                        agrega = false;
                        break;
                    } else {
                        agrega = true;
                    }
                }
                if (agrega) {
                    listaAux.add(si);
                }
            }
        }
        return listaAux;
    }
    
    public List<SolucionExtended> eliminaSolucionDeLista(List<SolucionExtended> listaSoluciones, String idSolucion) {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.eliminaSolucionDeLista()");
        List<SolucionExtended> lista = new ArrayList<>();
        try {
            lista.addAll(listaSoluciones);
            if (listaSoluciones != null && !listaSoluciones.isEmpty()){
                for (SolucionExtended item: lista){
                    if (idSolucion.equalsIgnoreCase(item.getIdSolucion())){
                        lista.remove(item);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al eliminar solucion de la lista multiple :: {} " , e.getMessage() );
        }
        return lista;
    }
    
    public List<Surtimiento> eliminaSurtimientoDeLista(List<Surtimiento> listaSurtimiento, String idSurtimiento) {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.eliminaSurtimientoDeLista()");
        List<Surtimiento> lista = new ArrayList<>();
        try {
            lista.addAll(listaSurtimiento);
            if (listaSurtimiento != null && !listaSurtimiento.isEmpty()) {
                for (Surtimiento item: lista){
                    if (idSurtimiento.equalsIgnoreCase(item.getIdSurtimiento())){
                        lista.remove(item);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al eliminar surtimiento de la lista multiple :: {} " , e.getMessage() );
        }
        return lista;
    }
    
    public List<SurtimientoInsumo_Extend> eliminaSurtimientoInsumoDeLista(List<SurtimientoInsumo_Extend> listaSurtimientoInsumo, String idSurtimiento) {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.eliminaSurtimientoInsumoDeLista()");
        List<SurtimientoInsumo_Extend> lista = new ArrayList<>();
        try {
            lista.addAll(listaSurtimientoInsumo);
            if (listaSurtimientoInsumo != null && !listaSurtimientoInsumo.isEmpty()) {
                for (SurtimientoInsumo_Extend item: lista){
                    if (idSurtimiento.equalsIgnoreCase(item.getIdSurtimiento())){
                        lista.remove(item);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al eliminar surtimientoInsumo de la lista multiple :: {} " , e.getMessage() );
        }
        return lista;
    }
    
    public List<PrescripcionInsumo> eliminaPrescripcionInsumoDeLista(List<PrescripcionInsumo> listaPrescripcionInsumo, String idPrescripcion) {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.eliminaPrescripcionInsumoDeLista()");
        List<PrescripcionInsumo> lista = new ArrayList<>();
        try {
            lista.addAll(listaPrescripcionInsumo);
            if (listaPrescripcionInsumo != null && !listaPrescripcionInsumo.isEmpty()) {
                for (PrescripcionInsumo item: lista){
                    if (idPrescripcion.equalsIgnoreCase(item.getIdPrescripcion())){
                        lista.remove(item);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al eliminar prescripcionInsumo de la lista multiple :: {} " , e.getMessage() );
        }
        return lista;
    }
    
    public List<Prescripcion> eliminaPrescripcionDeLista(List<Prescripcion> listaPrescripcion, String idPrescripcion) {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.eliminaPrescripcionDeLista()");
        List<Prescripcion> lista = new ArrayList<>();
        try {
            lista.addAll(listaPrescripcion);
            if (listaPrescripcion != null && !listaPrescripcion.isEmpty()) {
                for (Prescripcion item: lista){
                    if (idPrescripcion.equalsIgnoreCase(item.getIdPrescripcion())){
                        lista.remove(item);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al eliminar prescripcion de la lista multiple :: {} " , e.getMessage() );
        }
        return lista;
    }
    
    public String obtienePrescripcionDeLista(List<Surtimiento> listaSurtimiento, String idSurtimiento) {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.obtienePrescripcionDeLista()");
        String idPrescripcion = null;
        try {
            for (Surtimiento item: listaSurtimiento){
                if (idSurtimiento.equalsIgnoreCase(item.getIdSurtimiento())){
                    idPrescripcion = item.getIdPrescripcion();
                    break;
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al obtener prescripcion de la lista multiple :: {} " , e.getMessage() );
        }
        return idPrescripcion;
    }


/*    
    public void imprimirEtiquetaItem(
            Prescripcion prescripcionSelected
            , Surtimiento_Extend surtimientoExtendedSelected
            , SolucionExtended solucionSelect
    )  throws Exception {
        LOGGER.trace("mx.mc.commons.SolucionUtils.imprimirEtiquetaItem()");
        boolean status = false;

        if (prescripcionSelected == null) {
            throw new Exception("No se pueden ontener datos de la prescripción de mezcla para imprimir. ");

        } else if (surtimientoExtendedSelected == null) {
            throw new Exception("Surtimiento asociado a la mezcla inválido. ");

        } else if (surtimientoExtendedSelected.getIdSurtimiento() == null) {
            throw new Exception("Surtimiento asociado a la mezcla inválido. ");

        } else if (surtimientoExtendedSelected.getIdPrescripcion() == null) {
            throw new Exception("Prescripcion asociadaa a la mezcla inválida. ");

        } else if (solucionSelect == null) {
            throw new Exception("No se pueden ontener datos de la mezcla para imprimir. ");
            
        } else if (idPrintSelect == null) {
            throw new Exception("Impresora seleccionada inválida. ");

        } else if (template == null) {
            throw new Exception("Plantilla de etiqueta seleccionada inválida. ");

        } else {

            Impresora print = null;
            EtiquetaInsumo etiqueta = null;
            try {
                print = impService.obtenerPorIdImpresora(idPrintSelect);
            } catch (Exception e) {
                LOGGER.error("Error al obtener datos de la impresora seleccionada {} ", e.getMessage());
                throw new Exception("Error al obtener datos de la impresora seleccionada. ");
            }

            TemplateEtiqueta templaEtiqueta = null;
            try {
                templaEtiqueta = tempService.obtenerById(template);
            } catch (Exception e) {
                LOGGER.error("Error al obtener datos de la platilla de impresion seleccionada {} ", e.getMessage());
                throw new Exception("Error al obtener datos de la platilla de impresion seleccionada.");
            }

            ViaAdministracion va  = null;
            if (solucionSelect.getIdViaAdministracion() != null) {
                try {
                    va  = viaAdmService.obtenerPorId(solucionSelect.getIdViaAdministracion());
                } catch (Exception e) {
                    LOGGER.error("Error al obtener la vía de administración para la impresion seleccionada {} ", e.getMessage());
                    throw new Exception("Error al obtener la vía de administración para la impresion seleccionada. " );
                }
            }

            String nombreArea = "";
            String nombreHospital = "";
            String direccionHospital = "";

            try {
                Estructura e = estService.obtenerEstructura(surtimientoExtendedSelected.getIdEstructuraAlmacen());
                if (e.getNombre() != null) {
                    nombreArea = e.getNombre();
                    if (e.getIdEntidadHospitalaria() != null) {
                        EntidadHospitalaria eh = entHosService.obtenerEntidadById(e.getIdEntidadHospitalaria());
                        if (eh != null) {
                            nombreHospital = eh.getNombre();
                            direccionHospital = eh.getDomicilio();
                        }
                    }
                }
            } catch (Exception e) {
                LOGGER.error("Error la buscar la entidad hospitalaria {} ", e.getMessage());
                throw new Exception("Error la buscar la entidad hospitalaria. ");
            }

            if (print == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No se pueden obetener datos de la impresora seleccionada. ", null);

            } else if (print.getIdImpresora() == null) {
                throw new Exception("No se pueden obetener datos de la impresora seleccionada. ");

            } else if (templaEtiqueta == null) {
                throw new Exception("No se pueden obetener datos de la plantilla de impresión seleccionada. ");

            } else if (templaEtiqueta.getContenido() == null) {
                throw new Exception("No se pueden obetener datos de la plantilla de impresión seleccionada. ");

            } else {
                String cont = templaEtiqueta.getContenido();

                cont = cont.replace("#TITULO1#", (!nombreHospital.equals(StringUtils.EMPTY)) ? eliminaCarEspe(nombreHospital) : "");
                cont = cont.replace("#TITULO2#", (!nombreArea.equals(StringUtils.EMPTY)) ? eliminaCarEspe(nombreArea) : "");

                cont = cont.replace("#FOL_OPREP#", (surtimientoExtendedSelected.getFolio() != null) ? surtimientoExtendedSelected.getFolio() : "");
                cont = cont.replace("#FOL_PRES#", (prescripcionSelected.getFolio() != null) ? prescripcionSelected.getFolio() : "");
                cont = cont.replace("#FECHA#", (surtimientoExtendedSelected.getFechaProgramada() != null) ? FechaUtil.formatoFecha(surtimientoExtendedSelected.getFechaProgramada(), "dd/MM/yyyy HH:mm") : "");
                
                
                
                String codQr = eliminaCarEspe(surtimientoExtendedSelected.getClaveAgrupada());
                cont = cont.replace("#COD_QR#", (codQr != null && !codQr.trim().isEmpty()) ? codQr : "0");
                String numEt = "1";
                cont = cont.replace("#NUM_ETQ#", numEt);
                
                cont = cont.replace("#NOM_COM_PAC#", (surtimientoExtendedSelected.getNombrePaciente() != null) ? eliminaCarEspe(surtimientoExtendedSelected.getNombrePaciente()) : "");
                cont = cont.replace("#CLA_PAC#", (surtimientoExtendedSelected.getPacienteNumero() != null) ? surtimientoExtendedSelected.getPacienteNumero() : "");
//                cont = cont.replace("#FECHA_NAC#", (surtimientoExtendedSelected.getEdadPaciente() > 0) ? String.valueOf(surtimientoExtendedSelected.getEdadPaciente()) : "");
                cont = cont.replace("#FECHA_NAC#", (surtimientoExtendedSelected.getFechaNacimiento() != null) ? FechaUtil.formatoCadena(surtimientoExtendedSelected.getFechaNacimiento(), "yyyy-MM-dd") : "");
                cont = cont.replace("#PESO#", (surtimientoExtendedSelected.getPesoPaciente() > 0) ? String.valueOf(surtimientoExtendedSelected.getPesoPaciente()) : "");
                cont = cont.replace("#NUM_PAC#", (surtimientoExtendedSelected.getPacienteNumero() != null) ? surtimientoExtendedSelected.getPacienteNumero() : "");

                cont = cont.replace("#NOM_CAM#", (surtimientoExtendedSelected.getCama() != null) ? eliminaCarEspe(surtimientoExtendedSelected.getCama()) : "");
                cont = cont.replace("#NOM_SER#", (surtimientoExtendedSelected.getNombreEstructura() != null) ? eliminaCarEspe(surtimientoExtendedSelected.getNombreEstructura()) : "");
                cont = cont.replace("#NOM_MED#", (surtimientoExtendedSelected.getNombreMedico() != null) ? eliminaCarEspe(surtimientoExtendedSelected.getNombreMedico()) : "");
                cont = cont.replace("#CED_MED#", (surtimientoExtendedSelected.getCedProfesional() != null) ? surtimientoExtendedSelected.getCedProfesional() : "");

                cont = cont.replace("#FEC_PREP#", (solucionSelect.getFechaPrepara() != null) ? FechaUtil.formatoFecha(solucionSelect.getFechaPrepara(), "dd/MM/yyyy HH:mm") : "");
                
                cont = cont.replace("#VEL_INF#", (solucionSelect.getVelocidad() != null && solucionSelect.getVelocidad() > 0) ? String.valueOf(solucionSelect.getVelocidad()) : "");
                cont = cont.replace("#TIE_INF#", (solucionSelect.getMinutosInfusion() != null && solucionSelect.getMinutosInfusion() > 0) ? String.valueOf(solucionSelect.getMinutosInfusion()) : "");
                cont = cont.replace("#VOL_TOT#", (solucionSelect.getVolumen() != null) ? String.valueOf(solucionSelect.getVolumen()) : "");
                cont = cont.replace("#TIP_SOL#", (surtimientoExtendedSelected.getTipoSolucion() != null) ? eliminaCarEspe(surtimientoExtendedSelected.getTipoSolucion()) : "");
                cont = cont.replace("#TIP_INF#", (surtimientoExtendedSelected.getTipoSolucion() != null) ? eliminaCarEspe(surtimientoExtendedSelected.getTipoSolucion()) : "");
                cont = cont.replace("#VIA_ADM#", (va  != null) ? eliminaCarEspe(va.getNombreViaAdministracion()) : "");
                cont = cont.replace("#PES_TOT#", (solucionSelect.getPesoTotal() !=null && solucionSelect.getPesoTotal() > 0) ? String.valueOf(solucionSelect.getPesoTotal()) : "");
                cont = cont.replace("#CAL_TOT#", (solucionSelect.getPesoTotal() !=null && solucionSelect.getCaloriasTotales() > 0) ? String.valueOf(solucionSelect.getCaloriasTotales()) : "");
                cont = cont.replace("#OSM_TOT#", (solucionSelect.getPesoTotal() !=null && solucionSelect.getOmolairdadTotal() > 0) ? String.valueOf(solucionSelect.getOmolairdadTotal()) : "");
// TODO: Agregar el overfill
                cont = cont.replace("#OVERFILL#", (solucionSelect.getSobrellenado() !=null && solucionSelect.getSobrellenado()> 0) ? String.valueOf(solucionSelect.getSobrellenado()) : "");
                cont = cont.replace("#CON_MINISTRACION#", "");
                
                cont = cont.replace("#LOTE_MEZ#", (solucionSelect.getLoteMezcla()!= null) ? solucionSelect.getLoteMezcla(): "");
                List<SurtimientoInsumo_Extend> surtimientoInsumoExtendedList = new ArrayList<>();
                try {
                    
                    boolean mayorCero = true;
                    surtimientoInsumoExtendedList.addAll(surInsService.obtenerSurtimientoInsumosByIdSurtimiento(surtimientoExtendedSelected.getIdSurtimiento(), mayorCero));

                    for (SurtimientoInsumo_Extend item : surtimientoInsumoExtendedList) {
                        List<SurtimientoEnviado_Extend> seList = surEnvService.detalleSurtimientoEnviadoXIdSurtimientoInsumo(item.getIdSurtimientoInsumo(), mayorCero);
                        item.setSurtimientoEnviadoExtendList(seList);
                    }
                    Estabilidad_Extended est = obtenerEstabilidad(surtimientoInsumoExtendedList, va);
                    cont = cont.replace("#TIE_RF#", (est != null && est.getLimHrsUsoRedFria() != null && est.getLimHrsUsoRedFria() > 0) ? est.getLimHrsUsoRedFria().toString() : "");
                    cont = cont.replace("#TIE_RS#", (est != null && est.getLimHrsUsoRedSeca() != null) ? est.getLimHrsUsoRedSeca().toString() : "");
                    cont = cont.replace("#FEC_MAX_MIN#", (est != null && est.getLimHrsUsoRedFria() != null && est.getLimHrsUsoRedFria() > 0 && solucionSelect.getFechaPrepara() != null) ? FechaUtil.formatoFecha( FechaUtil.sumarRestarHorasFecha(solucionSelect.getFechaPrepara(), est.getLimHrsUsoRedFria() ), "dd/MM/yyyy HH:mm") : "");
                    cont = cont.replace("#FEC_MAX_MIN_RS#", (est != null && est.getLimHrsUsoRedSeca()!= null && solucionSelect.getFechaPrepara() != null) ? FechaUtil.formatoFecha( FechaUtil.sumarRestarHorasFecha(solucionSelect.getFechaPrepara(), est.getLimHrsUsoRedSeca() ), "dd/MM/yyyy HH:mm") : "");
                
                } catch (Exception exc ){
                    LOGGER.error("Error al buscar las máximas fechas de uso en red fria y seca de la estabilidad :: {} ", exc.getMessage());
                }
                cont = cont.replace("#COMENTARIOS#", (solucionSelect.getObservaciones() != null) ? eliminaCarEspe(solucionSelect.getObservaciones()) : "");
                
                StringBuilder condConserv = new StringBuilder();
                if (solucionSelect.getProteccionLuz() != null && solucionSelect.getProteccionLuz()>0) {
                    condConserv.append("Protoger de la luz. | ");
                }
                if (solucionSelect.getProteccionTempRefrig()!= null && solucionSelect.getProteccionTempRefrig()>0) {
                    condConserv.append("Mant. temp. Refrig. | ");
                }
                if (solucionSelect.getProteccionTemp()!= null && solucionSelect.getProteccionTemp()>0) {
                    condConserv.append("Mant. temp. ambiente. | ");
                }

                String condConservacion = condConserv.toString();
                
                cont = cont.replace("#CON_CONSERVACION#", (condConservacion != null) ? eliminaCarEspe(condConservacion) : "");

                String usuarioPrepara = "";
                if (solucionSelect.getIdUsuarioPrepara() != null) {
                    try {
                        Usuario u = usuService.obtenerUsuarioByIdUsuario(solucionSelect.getIdUsuarioPrepara());
                        if (u != null) {
                            usuarioPrepara = eliminaCarEspe(u.getNombre() + " " + u.getApellidoPaterno() + " " + u.getApellidoMaterno());
                        }
                    } catch (Exception e) {
                        LOGGER.error("Error al buscar el envase para la etiqueta {} ", e.getMessage());
                    }
                }

                cont = cont.replace("#NOMBRE_PREPARADOR#", usuarioPrepara);

                String nombreContenedor = "";
                if (solucionSelect.getIdEnvaseContenedor() != null) {
                    try {
                        EnvaseContenedor e = envaseService.obtenerXidEnvase(solucionSelect.getIdEnvaseContenedor());
                        if (e != null) {
                            nombreContenedor = eliminaCarEspe(e.getDescripcion());
                        }
                    } catch (Exception e) {
                        LOGGER.error("Error al buscar el envase para la etiqueta {} ", e.getMessage());
                    }
                }

                cont = cont.replace("#NOMBRE_CONTENEDOR#", nombreContenedor);

                try {
                    boolean mayorCero = true;
                    surtimientoInsumoExtendedList = surInsService.obtenerSurtimientoInsumosByIdSurtimiento(surtimientoExtendedSelected.getIdSurtimiento(), mayorCero);
                } catch (Exception e) {
                    LOGGER.error("Error al obtener los medicamentos de la mezcla. ");
                }
                List<String> listaIdSurtimientoInsumo = new ArrayList<>();
                boolean agrega = false;
                for (int i = 0; i < 25; i++) {
                    String num = String.format("%02d", i + 1);
                    if (surtimientoInsumoExtendedList != null && surtimientoInsumoExtendedList.size() >= i + 1) {
                        if (surtimientoInsumoExtendedList.get(i) != null &&
                                surtimientoInsumoExtendedList.get(i).getTipoInsumo() == CatalogoGeneral_Enum.MEDICAMENTO.getValue() ){
                            if(listaIdSurtimientoInsumo.isEmpty()) {
                                listaIdSurtimientoInsumo.add(surtimientoInsumoExtendedList.get(i).getIdSurtimientoInsumo());
                                agrega = true;
                            } else {
                                for(String idSurtimientoInsumo : listaIdSurtimientoInsumo) {
                                    if(idSurtimientoInsumo.equals(surtimientoInsumoExtendedList.get(i).getIdSurtimientoInsumo())) {
                                        agrega = false;
                                        break;
                                    } else {
                                       agrega = true; 
                                    }
                                }
                                listaIdSurtimientoInsumo.add(surtimientoInsumoExtendedList.get(i).getIdSurtimientoInsumo());
                            }                             
                            if(agrega) {
                                cont = cont.replace("#NOMBRE_MEDICAMENTO_" + num + "#",
                                        (surtimientoInsumoExtendedList.get(i).getNombreCorto() != null)
                                       ? eliminaCarEspe(surtimientoInsumoExtendedList.get(i).getNombreCorto())
                                       : "");
                               String cantUmedida = (surtimientoInsumoExtendedList.get(i).getDosis() != null)
                                       ? surtimientoInsumoExtendedList.get(i).getDosis().toString() : "";
                               cantUmedida = (surtimientoInsumoExtendedList.get(i).getUnidadConcentracion() != null)
                                       ? cantUmedida + " " + surtimientoInsumoExtendedList.get(i).getUnidadConcentracion()
                                       : cantUmedida + "";
                               cont = cont.replace("#VOL_" + num + "#", cantUmedida);
                               String dosis = (surtimientoInsumoExtendedList.get(i).getDosis() != null)
                                       ? surtimientoInsumoExtendedList.get(i).getDosis().toString() : "";
                               dosis = (surtimientoInsumoExtendedList.get(i).getDosis()!= null)
                                       ? dosis + " mL"
                                       : dosis + "";
                               cont = cont.replace("#DOSIS_" + num + "#", dosis);

                               String datoComp = (surtimientoInsumoExtendedList.get(i).getNombreFabricante() != null)
                                       ? "|FAB:" + surtimientoInsumoExtendedList.get(i).getNombreFabricante() : "|FAB: ";

                               datoComp = (surtimientoInsumoExtendedList.get(i).getLote() != null)
                                       ? datoComp + " |LOT:" + surtimientoInsumoExtendedList.get(i).getLote() : "|LOT: ";

                               cont = cont.replace("#DATO_INSUMO_" + num + "#", datoComp);
                               agrega = false;
                            }

                        } else {
                           cont = cont.replace("#NOMBRE_MEDICAMENTO_" + num + "#", "");
                           cont = cont.replace("#VOL_" + num + "#", "");
                           cont = cont.replace("#DOSIS_" + num + "#", "");
                           cont = cont.replace("#VOL_" + num + "#", "");
                           cont = cont.replace("#DATO_INSUMO_" + num + "#", "");
                        }
                    }                        
                }
                
                cont = cont.replace("#TIE_RF#", "");
                cont = cont.replace("#TIE_RS#", "");
                cont = cont.replace("#FEC_MAX_MIN#", "");
                cont = cont.replace("#FEC_MAX_MIN_RS#", "");
                
                cont = cont.replace("#NOMBRE_MEDICAMENTO_01#", "");
                cont = cont.replace("#VOL_01#", "");
                
                cont = cont.replace("#NOMBRE_MEDICAMENTO_02#", "");
                cont = cont.replace("#VOL_02#", "");
                
                cont = cont.replace("#NOMBRE_MEDICAMENTO_03#", "");
                cont = cont.replace("#VOL_03#", "");
                
                cont = cont.replace("#NOMBRE_MEDICAMENTO_04#", "");
                cont = cont.replace("#VOL_04#", "");
                
                cont = cont.replace("#DOM_HOSP#", eliminaCarEspe(direccionHospital));

                templaEtiqueta.setContenido(cont);
                Integer band = 1;
                if (band == 1) {
                    try {
                        status = repService.imprimirEtiquetaCM(templaEtiqueta, cantPrint, print.getIpImpresora());
                    } catch (Exception e) {
                        LOGGER.error("Error al generar la impresión {} ", e.getMessage());
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, e.getMessage(), null);
                    }
                }
                if (status) {
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("paciente.info.impCorrectamente"), null);
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No se pudo generar la impresión.", null);
                }
            }
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }
*/
    
}
