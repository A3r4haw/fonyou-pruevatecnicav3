package mx.mc.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import mx.mc.enums.EstatusCama_Enum;
import mx.mc.enums.EstatusGabinete_Enum;
import mx.mc.enums.EstatusPaciente_Enum;
import mx.mc.enums.TipoAreaEstructura_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.Cama;
import mx.mc.model.CamaExtended;
import mx.mc.model.Estructura;
import mx.mc.model.Paciente;
import mx.mc.model.PacienteServicio;
import mx.mc.model.PacienteUbicacion;
import mx.mc.model.Paciente_Extended;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.Usuario;
import mx.mc.model.Visita;
import mx.mc.service.CamaService;
import mx.mc.service.EstructuraService;
import mx.mc.service.PacienteService;
import mx.mc.service.PacienteServicioService;
import mx.mc.service.PacienteUbicacionService;
import mx.mc.service.VisitaService;
import mx.mc.util.Comunes;
import mx.mc.util.Mensaje;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * @author AORTIZ
 */
@Controller
@Scope(value = "view")
public class AsignacionCamaMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(AsignacionCamaMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    private String textoBusqueda;
    private Usuario usuarioSession;
    private boolean administrador;
    private List<Estructura> listaEstructuras;
    private List<Paciente_Extended> listaPacientes;
    private Paciente_Extended pacienteSelect;
    private String idEstructura;
    private List<CamaExtended> listaCamas;
    private CamaExtended camaSelect;
    private String numeroCama;
    private Paciente_Extended pacienteDetalle;
    private String errorOperacion;
    private PermisoUsuario permiso;
    @Autowired
    private transient PacienteService pacienteService;

    @Autowired
    private transient EstructuraService estructuraService;

    @Autowired
    private transient CamaService camaService;

    @Autowired
    private transient PacienteUbicacionService pacienteUbicacionService;

    @Autowired
    private transient PacienteServicioService pacienteServicioService;

    @Autowired
    private transient VisitaService visitaService;

    Estructura estructuraUsuario;

    /**
     * Metodo que se ejecuta despues de cargar la pantalla de pacientes
     */
    @PostConstruct
    public void init() {
        try {
            errorOperacion = "surtimiento.error.operacion";
            initialize();
            permiso = Comunes.obtenerPermisos(Transaccion_Enum.ASIGNACIONCAMA.getSufijo());
            this.usuarioSession = Comunes.obtenerUsuarioSesion();
            this.administrador = Comunes.isAdministrador();
            this.listaEstructuras = obtenerEstructuras();
            this.listaPacientes = obtenerPacientes();
        } catch (Exception e) {
            LOGGER.error("Error en el metodo init :: {}", e.getMessage());
        }
    }

    /**
     * Metodo que inicializa todos los atributos de la clase
     */
    public void initialize() {
        this.textoBusqueda = "";
        this.usuarioSession = new Usuario();
        this.administrador = false;
        this.listaEstructuras = new ArrayList<>();
        this.listaPacientes = new ArrayList<>();
        this.pacienteSelect = new Paciente_Extended();
        this.idEstructura = "";
        this.numeroCama = "";
        this.pacienteDetalle = new Paciente_Extended();
    }

    private List<Paciente_Extended> obtenerPacientes() {
        List<Paciente_Extended> listPacientes = null;
        try {
            if (this.administrador) {
                listPacientes = pacienteService.obtenerPacientesYCamas(
                        this.listaEstructuras.get(0).getIdEstructura(), Constantes.REGISTROS_PARA_MOSTRAR, null);
                idEstructura = this.listaEstructuras.get(0).getIdEstructura();
            } else {
                listPacientes = pacienteService.obtenerPacientesYCamas(
                        null, Constantes.REGISTROS_PARA_MOSTRAR, listaEstructuras);
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo guardarPaciente :: {}", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errorOperacion), null);
        }
        return listPacientes;
    }

    public void obtenerPacientesPorComboServicio() {
        try {
            this.listaPacientes = pacienteService.obtenerPacientesYCamas(
                    this.idEstructura, Constantes.REGISTROS_PARA_MOSTRAR, null);
        } catch (Exception e) {
            LOGGER.error("Error en el metodo obtenerPacientesPorComboServicio :: {}", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errorOperacion), null);
        }
    }

    private List<Estructura> obtenerEstructuras() {
        List<Estructura> listEstructuras = new ArrayList<>();
        try {
            if (this.administrador) {
                List<Integer> listaTipos = new ArrayList<>();
                listaTipos.add(TipoAreaEstructura_Enum.HOSPITAL.getValue());
                listaTipos.add(TipoAreaEstructura_Enum.CONSULTAEXTERNA.getValue());
                listaTipos.add(TipoAreaEstructura_Enum.CONSULTAINTERNA.getValue());
                listEstructuras.addAll(estructuraService.obtenerEstructurasPorTipo(listaTipos));
            } else {
// TODO: Generar un método que busqye estructuras de forma descendiente en la estructura jerárquica
                Estructura estructura = new Estructura();
                estructura.setIdEstructura(this.usuarioSession.getIdEstructura());
                estructuraUsuario = estructuraService.obtener(estructura);
                List<Estructura> listaEstructurasNivel = estructuraService.getEstructurDugthersYMe(estructuraUsuario.getIdEstructuraPadre());
                for (Estructura estruct : listaEstructurasNivel) {
                    //Se valida que no sea el padre para buscar las estructuras descentientes
                    if (!estruct.getIdEstructura().equalsIgnoreCase(estructuraUsuario.getIdEstructuraPadre())) {
                        listEstructuras.addAll(estructuraService.getEstructurDugthersYMe(estruct.getIdEstructura()));
                    }
                }
                //listEstructuras.addAll(estructuraService.getEstructurDugthersYMe(estructuraPadre.getIdEstructuraPadre()));
                //listEstructuras.addAll(estructuraService.getEstructurDugthersYMe(usuarioSession.getIdEstructura()));
                /*listEstructuras.addAll(estructuraService.obtenerLista(estructura));
                listEstructuras.addAll(estructuraService.obtenerUnidadesJerarquicasByTipoArea(TipoAreaEstructura_Enum.CONSULTAEXTERNA.getValue()));
                listEstructuras.addAll(estructuraService.obtenerUnidadesJerarquicasByTipoArea(TipoAreaEstructura_Enum.CONSULTAINTERNA.getValue()));*/
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo guardarPaciente :: {}", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errorOperacion), null);
        }
        return listEstructuras;
    }

    public void cargarModalAsignacionCama(Paciente_Extended paciente) {
        try {
            this.listaCamas = camaService.obtenerCamaByEstructuraAndEstatus(
                    paciente.getIdEstructura(), null);
            this.pacienteSelect = paciente;
        } catch (Exception e) {
            LOGGER.error("Error en el metodo cargarModalAsignacionCama :: {}", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errorOperacion), null);
        }
    }

    public void seleccionarCama() {
        try {
            this.numeroCama = this.camaSelect.getNombreCama();
        } catch (Exception e) {
            LOGGER.error("Error en el metodo cargarModalAsignacionCama :: {}", e.getMessage());
        }
    }

    public void asignarCama() {
        try {
            if (!this.permiso.isPuedeProcesar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estr.err.permisos"), null);
                return;
            }
            if (this.camaSelect == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("asigCama.err.selectCama"), null);
                return;
            }
            if (this.camaSelect.getIdEstatusCama().intValue() != EstatusCama_Enum.DISPONIBLE.getValue()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("asigCama.err.coloCamaDisp"), null);
                return;
            }
            Visita visitaAbierta = obtenerVisitaAbierta(this.pacienteSelect.getIdPaciente());
            PacienteServicio servicioAbierto = obtenerServicioAbierto(visitaAbierta);
            PacienteUbicacion pacienteUbicacion = new PacienteUbicacion();
            pacienteUbicacion.setIdPacienteUbicacion(Comunes.getUUID());
            pacienteUbicacion.setIdPacienteServicio(servicioAbierto.getIdPacienteServicio());
            pacienteUbicacion.setIdCama(camaSelect.getIdCama());
            pacienteUbicacion.setFechaUbicaInicio(new Date());
            pacienteUbicacion.setIdUsuarioUbicaInicio(this.pacienteSelect.getIdPaciente());
            pacienteUbicacion.setIdEstatusUbicacion(Integer.MIN_VALUE);
            pacienteUbicacion.setInsertIdUsuario(this.usuarioSession.getIdUsuario());
            pacienteUbicacion.setInsertFecha(new Date());

            Cama cama = new Cama();
            cama.setIdCama(this.camaSelect.getIdCama());
            cama.setIdEstatusCama(EstatusCama_Enum.OCUPADA.getValue());
            cama.setUpdateIdUsuario(this.usuarioSession.getIdUsuario());
            cama.setUpdateFecha(new Date());

            Paciente paciente = new Paciente();
            paciente.setIdPaciente(this.pacienteSelect.getIdPaciente());
            paciente.setIdEstatusPaciente(EstatusPaciente_Enum.ASIGNADO_A_CAMA.getValue());
            paciente.setEstatusGabinete(EstatusGabinete_Enum.PENDIENTE.getValue());
            paciente.setUpdateIdUsuario(this.getUsuarioSession().getIdUsuario());
            paciente.setUpdateFecha(new Date());

            boolean resp = pacienteUbicacionService.asignarCamaPaciente(pacienteUbicacion,
                    cama, paciente);
            if (this.administrador) {
                this.listaPacientes = pacienteService.obtenerPacientesYCamas(
                        idEstructura != null ? idEstructura : this.listaEstructuras.get(0).getIdEstructura(), Constantes.REGISTROS_PARA_MOSTRAR, null);
            } else {
                this.listaPacientes = pacienteService.obtenerPacientesYCamas(
                        null, Constantes.REGISTROS_PARA_MOSTRAR, listaEstructuras);
            }

            if (resp) {
                Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("asigCama.info.camAsigCorr"), null);
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("asigCama.err.asignCama"), null);
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo asignarCama :: {}", e.getMessage());
        }
    }

    public void liberarCama(Paciente_Extended pacienteExtended) {
        try {
            boolean resp = false;
            Visita visitaAbierta = obtenerVisitaAbierta(pacienteExtended.getIdPaciente());
            PacienteServicio servicioAbierto = obtenerServicioAbierto(visitaAbierta);

            Paciente paciente = new Paciente();
            paciente.setIdPaciente(pacienteExtended.getIdPaciente());
            paciente.setIdEstatusPaciente(EstatusPaciente_Enum.ASIGNADO_A_SERVICIO.getValue());
            paciente.setEstatusGabinete(EstatusGabinete_Enum.PENDIENTE.getValue());
            paciente.setUpdateIdUsuario(this.usuarioSession.getIdUsuario());
            paciente.setUpdateFecha(new Date());

            PacienteUbicacion pacienteUbicacion = new PacienteUbicacion();
            pacienteUbicacion.setIdPacienteServicio(servicioAbierto.getIdPacienteServicio());
            pacienteUbicacion.setIdUsuarioUbicaFin(this.usuarioSession.getIdUsuario());
            pacienteUbicacion.setFechaUbicaFin(new Date());
            pacienteUbicacion.setUpdateIdUsuario(this.usuarioSession.getIdUsuario());
            pacienteUbicacion.setUpdateFecha(new Date());

            resp = pacienteUbicacionService.liberarCamaPaciente(
                    pacienteUbicacion, paciente, EstatusCama_Enum.DISPONIBLE.getValue());

            if (this.administrador) {
                this.listaPacientes = pacienteService.obtenerPacientesYCamas(
                        idEstructura != null ? idEstructura : this.listaEstructuras.get(0).getIdEstructura(), Constantes.REGISTROS_PARA_MOSTRAR, null);
            } else {
                this.listaPacientes = pacienteService.obtenerPacientesYCamas(
                        null, Constantes.REGISTROS_PARA_MOSTRAR, listaEstructuras);
            }
            if (resp) {
                Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("asigCama.info.camaLiberada"), null);
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("asigCama.err.liberarCama"), null);
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo liberarCama :: {}", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("asigCama.err.realOpera"), null);
        }
    }

    private PacienteServicio obtenerServicioAbierto(Visita visitaAbierta) throws Exception {
        PacienteServicio pacienteServicio = new PacienteServicio();
        pacienteServicio.setIdVisita(visitaAbierta.getIdVisita());
        return pacienteServicioService.
                obtenerPacienteServicioAbierto(pacienteServicio);
    }

    private Visita obtenerVisitaAbierta(String idPaciente) throws Exception {
        Visita visita = new Visita();
        visita.setIdPaciente(idPaciente);
        return visitaService.obtenerVisitaAbierta(visita);
    }

    /**
     * Metodo utilizado para buscar pacientes de acuerdo a un paramentro de
     * busqueda
     */
    public void buscarRegistros() {
        try {
            if (!this.permiso.isPuedeVer()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estr.err.permisos"), "");
                return;
            }

            if (this.administrador) {
                this.listaPacientes = pacienteService.obtenerPacientesYCamasPorCriterioBusqueda(
                        this.idEstructura,
                        Constantes.REGISTROS_PARA_MOSTRAR,
                        this.textoBusqueda);
            } else {
                this.listaPacientes = pacienteService.obtenerPacientesYCamasPorCriterioBusqueda(
                        this.usuarioSession.getIdEstructura(),
                        Constantes.REGISTROS_PARA_MOSTRAR,
                        this.textoBusqueda);
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo buscarRegistros :: {}", e.getMessage());
        }
    }

    /**
     * Metodo utilizado para obener los datos del detalle al pulsar el boton en
     * el registro
     */
    public void mostrarDetallePacienteBotonRegistro(String idPaciente) {
        try {
            this.pacienteDetalle = pacienteService.obtenerPacienteCompletoPorId(
                    idPaciente);
        } catch (Exception e) {
            LOGGER.error("Error en el metodo mostrarDetallePaciente :: {}", e.getMessage());
        }
    }

    /**
     * Metodo utilizado para obener los datos del detalle
     */
    public void mostrarDetallePacienteSelect() {
        try {
            this.pacienteDetalle = pacienteService.obtenerPacienteCompletoPorId(
                    this.pacienteSelect.getIdPaciente());
        } catch (Exception e) {
            LOGGER.error("Error en el metodo mostrarDetallePacienteSelect :: {}", e.getMessage());
        }
    }

    public String getTextoBusqueda() {
        return textoBusqueda;
    }

    public void setTextoBusqueda(String textoBusqueda) {
        this.textoBusqueda = textoBusqueda;
    }

    public Usuario getUsuarioSession() {
        return usuarioSession;
    }

    public void setUsuarioSession(Usuario usuarioSession) {
        this.usuarioSession = usuarioSession;
    }

    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }

    public List<Estructura> getListaEstructuras() {
        return listaEstructuras;
    }

    public void setListaEstructuras(List<Estructura> listaEstructuras) {
        this.listaEstructuras = listaEstructuras;
    }

    public List<Paciente_Extended> getListaPacientes() {
        return listaPacientes;
    }

    public void setListaPacientes(List<Paciente_Extended> listaPacientes) {
        this.listaPacientes = listaPacientes;
    }

    public Paciente_Extended getPacienteSelect() {
        return pacienteSelect;
    }

    public void setPacienteSelect(Paciente_Extended pacienteSelect) {
        this.pacienteSelect = pacienteSelect;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public List<CamaExtended> getListaCamas() {
        return listaCamas;
    }

    public void setListaCamas(List<CamaExtended> listaCamas) {
        this.listaCamas = listaCamas;
    }

    public CamaExtended getCamaSelect() {
        return camaSelect;
    }

    public void setCamaSelect(CamaExtended camaSelect) {
        this.camaSelect = camaSelect;
    }

    public String getNumeroCama() {
        return numeroCama;
    }

    public void setNumeroCama(String numeroCama) {
        this.numeroCama = numeroCama;
    }

    public Paciente_Extended getPacienteDetalle() {
        return pacienteDetalle;
    }

    public void setPacienteDetalle(Paciente_Extended pacienteDetalle) {
        this.pacienteDetalle = pacienteDetalle;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

    public Estructura getEstructuraUsuario() {
        return estructuraUsuario;
    }

    public void setEstructuraUsuario(Estructura estructuraUsuario) {
        this.estructuraUsuario = estructuraUsuario;
    }

}
