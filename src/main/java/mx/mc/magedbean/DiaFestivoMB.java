package mx.mc.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.DiaFestivo;
import mx.mc.model.DiaFestivo_Extended;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.Usuario;
import mx.mc.util.Comunes;
import mx.mc.util.Mensaje;
import org.primefaces.PrimeFaces;
import mx.mc.service.DiaFestivoService;
import mx.mc.util.FechaUtil;

/**
 *
 * @author mcalderon
 *
 */
@Controller
@Scope(value = "view")
public class DiaFestivoMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(DiaFestivoMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private Date fechaFestiva;
    private Date fechaActual;
    private Usuario usuarioSession;
    private boolean administrador;
    private String laborable;
    private String evento;
    private boolean festCorrecta;
    private List<Integer> meses;
    private List<DiaFestivo_Extended> mesesAnio;
    private DiaFestivo_Extended diaFestivoExtendedSelected;
    private List<DiaFestivo_Extended> diasFestivosList;
    private String nombreMes;
    private DiaFestivo_Extended diaFestivoExtended;
    private DiaFestivo diaFestivo;
    private String diaActivo;
    private String diaInactivo;
    private PermisoUsuario permiso;
    private Integer numeroDia;
    private Date aux;
    
    @Autowired
    private transient DiaFestivoService diaFestivoService;

    /**
     * Metodo que se ejecuta despues de cargar la pantalla de pacientes
     */
    @PostConstruct
    public void init() {
        try {
            meses = new ArrayList<>();
            mesesAnio = new ArrayList<>();
            this.usuarioSession = Comunes.obtenerUsuarioSesion();
            this.administrador = Comunes.isAdministrador();
            diasFestivosList = new ArrayList<>();
            obtenerMeses();
            initialize();
            permiso = Comunes.obtenerPermisos(Transaccion_Enum.DIASFESTIVOS.getSufijo());

        } catch (Exception e) {
            LOGGER.error("Error en el metodo init :: {}", e.getMessage());
        }
    }

    /**
     * Metodo que inicializa todos los atributos de la clase
     */
    public void initialize() {
        diaFestivoExtendedSelected = new DiaFestivo_Extended();
        laborable = Constantes.DIA_ACTIVO;
        diaActivo = Constantes.DIA_ACTIVO;
        diaInactivo = Constantes.DIA_INACTIVO;
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        numeroDia  = cal.get(Calendar.DAY_OF_YEAR);
        nombreMes = "";
        fechaFestiva = FechaUtil.obtenerFechaInicio();
        fechaActual = FechaUtil.obtenerFechaInicio();
        festCorrecta = true;
        diaFestivoExtended = new DiaFestivo_Extended();
        diaFestivo = new DiaFestivo();

    }

    public DiaFestivo_Extended obtenerMesImeges(DiaFestivo_Extended feriados) {
        switch (feriados.getNumMes()) {
            case 1:
                feriados.setMes("Enero");
                feriados.setUrlMes("enero.png");
                break;
            case 2:
                feriados.setMes("Febrero");
                feriados.setUrlMes("febrero.png");
                break;
            case 3:
                feriados.setMes("Marzo");
                feriados.setUrlMes("marzo.png");
                break;
            case 4:
                feriados.setMes("Abril");
                feriados.setUrlMes("Abril.png");
                break;
            case 5:
                feriados.setMes("Mayo");
                feriados.setUrlMes("Mayo_1.png");
                break;
            case 6:
                feriados.setMes("Junio");
                feriados.setUrlMes("junio_1.jpg");
                break;
            case 7:
                feriados.setMes("Julio");
                feriados.setUrlMes("julio.png");
                break;
            case 8:
                feriados.setMes("Agosto");
                feriados.setUrlMes("agosto.jpg");
                break;
            case 9:
                feriados.setMes("Septiembre");
                feriados.setUrlMes("septiembre.jpg");
                break;
            case 10:
                feriados.setMes("Octubre");
                feriados.setUrlMes("octubre.jpg");
                break;
            case 11:
                feriados.setMes("Noviembre");
                feriados.setUrlMes("noviembre.png");
                break;
            case 12:
                feriados.setMes("Diciembre");
                feriados.setUrlMes("diciembre.png");
                break;
            default:
                break;
        }
        return feriados;
    }

    public void obtenerMeses() {
        Calendar c1 = Calendar.getInstance();
        int mes;
        mes = c1.get(Calendar.MONTH) + 1;
        while (mes <= 12) {
            DiaFestivo_Extended mesAls = new DiaFestivo_Extended();
            // Obtienes el mes actual            
            mesAls.setNumMes(mes);
            mesAls = obtenerMesImeges(mesAls);
            mesesAnio.add(mesAls);
            mes++;
        }

    }

    /**
     * Metodo para realizar la consulta desde la vista
     *
     */
    public void consultarDiasFestivoByMes() {
        try {
            diasFestivosList = diaFestivoService.obtenerDiasFeriadosByMes(diaFestivoExtendedSelected);
            nombreMes = diaFestivoExtendedSelected.getMes();
        } catch (Exception e1) {
            LOGGER.error("Error al consultar", e1);
        }
    }
    
    public DiaFestivo validaExistenciaFestiva(Date fechaFestiva, int numeroDiaVal){
        LOGGER.debug("mx.mc.magedbean.DiasFestivosMB.validaExistenciaFestiva()");       
        DiaFestivo dia = new DiaFestivo();
        try {
            dia = diaFestivoService.validaExistenciaFestiva(fechaFestiva, numeroDiaVal);
        } catch (Exception e) {
            LOGGER.error("Error al consultar", e.getMessage());
        }
        return dia;
    }
    

    public boolean validarFestividad() {
        LOGGER.debug("mx.mc.magedbean.DiasFestivosMB.vaidarFestividad()");
        if (evento.isEmpty()) {
            Mensaje.showMessage("Error", RESOURCES.getString("diaFestivo.err.evento"), null);
            festCorrecta = false;
        }
        if (laborable == null) {
            Mensaje.showMessage("Error", RESOURCES.getString("diaFestivo.err.laborable"), null);
            festCorrecta = false;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaFestiva);
        int diaNum  = cal.get(Calendar.DAY_OF_YEAR);
        DiaFestivo dia = validaExistenciaFestiva(fechaFestiva,diaNum);
        if(dia != null){
            Mensaje.showMessage("Error", RESOURCES.getString("diaFestivo.err.duplicado"), null);
            festCorrecta = false;
        }
        return festCorrecta;
    }

    public void llenarFestividad(DiaFestivo dias) {
        LOGGER.debug("mx.mc.magedbean.DiasFestivosMB.llenarFestividad()");
        dias.setIdDia(Comunes.getUUID());
        dias.setFecha(fechaFestiva);
        dias.setEvento(evento);
        dias.setLaborable(laborable);
        dias.setInsertIdUsuario(usuarioSession.getIdUsuario());
        dias.setInsertFecha(new Date());
    }

    public void agregarFestividad() throws Exception {
        LOGGER.debug("mx.mc.magedbean.DiasFestivosMB.agregarFestividad()");
        boolean ingreso;
        festCorrecta = true;
        boolean validaDtos = validarFestividad();
        if (validaDtos) {
            DiaFestivo diaFeriados = new DiaFestivo();
            llenarFestividad(diaFeriados);
            try {
                ingreso = diaFestivoService.insertar(diaFeriados);
                if (ingreso) {
                    evento = "";
                    fechaFestiva = FechaUtil.obtenerFechaInicio();
                    Mensaje.showMessage("Info", RESOURCES.getString("diaFestivo.info.correcto"), null);
                }
            } catch (Exception e) {
                throw new Exception("Error al ingresar el dia festivo " + e.getMessage());
            }
        }
    }

    public DiaFestivo llenarDiaFeriadoUpdate(DiaFestivo_Extended diasfestiExtended) {
        diaFestivo.setIdDia(diasfestiExtended.getIdDia());
        diaFestivo.setFecha(diasfestiExtended.getFecha());
        diaFestivo.setEvento(diasfestiExtended.getEvento());
        diaFestivo.setLaborable(diasfestiExtended.getLaborable());
        diaFestivo.setUpdateIdUsuario(usuarioSession.getIdUsuario());
        diaFestivo.setUpdateFecha(new Date());

        return diaFestivo;
    }

    public void editarDiaFestivo() throws Exception {
        LOGGER.debug("mx.mc.magedbean.DiasFestivosMB.EditarDiaFestivo()");
        if (diaFestivoExtended != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(diaFestivoExtended.getFecha());
            int numeroDiaEditar = cal.get(Calendar.DAY_OF_YEAR);
            if (numeroDiaEditar < numeroDia) {
                diaFestivoExtended.setFecha(aux);
                Mensaje.showMessage("Error", RESOURCES.getString("diaFestivo.err.fueraRango"), null);
            } else {
                DiaFestivo day = validaExistenciaFestiva(diaFestivoExtended.getFecha(), numeroDiaEditar);
                if (day == null) {
                    diaFestivo = llenarDiaFeriadoUpdate(diaFestivoExtended);
                    if (diaFestivo != null) {
                        boolean actualizarDia = diaFestivoService.actualizar(diaFestivo);
                        if (actualizarDia) {
                            consultarDiasFestivoByMes();
                            boolean status = Constantes.ACTIVO;
                            Mensaje.showMessage("Info", RESOURCES.getString("diaFestivo.info.actializadoCorrecto"), null);
                            PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
                        }
                    }
                } else {
                    diaFestivoExtended.setFecha(aux);
                    Mensaje.showMessage("Error", RESOURCES.getString("diaFestivo.err.duplicado"), null);
                }
            }
        }
    }

    public void eliminarDiaFestivo(String idDia) throws Exception {
        LOGGER.debug("mx.mc.magedbean.DiasFestivosMB.eliminarDiaFestivo()");
        try {
            boolean resp = diaFestivoService.deleteDiaFeriadoDate(idDia);
            if (resp) {
                consultarDiasFestivoByMes();
                boolean status = Constantes.ACTIVO;
                Mensaje.showMessage("Info", RESOURCES.getString("diaFestivo.info.eliminadoCorrecto"), null);
                PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
            }
        } catch (Exception e) {
            throw new Exception("Error al Eliminar el dia festivo " + e.getMessage());
        }
    }

    public void showDiasFestivo(DiaFestivo_Extended diaFestivoExtended) {
        if (diaFestivoExtended != null) {
            this.diaFestivoExtended = diaFestivoExtended;
            aux = diaFestivoExtended.getFecha();
        }
    }

    public Date getFechaFestiva() {
        return fechaFestiva;
    }

    public void setFechaFestiva(Date fechaFestiva) {
        this.fechaFestiva = fechaFestiva;
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

    public String getLaborable() {
        return laborable;
    }

    public void setLaborable(String laborable) {
        this.laborable = laborable;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public List<Integer> getMeses() {
        return meses;
    }

    public void setMeses(List<Integer> meses) {
        this.meses = meses;
    }

    public List<DiaFestivo_Extended> getMesesAnio() {
        return mesesAnio;
    }

    public void setMesesAnio(List<DiaFestivo_Extended> mesesAnio) {
        this.mesesAnio = mesesAnio;
    }

    public DiaFestivo_Extended getDiaFestivoExtendedSelected() {
        return diaFestivoExtendedSelected;
    }

    public void setDiaFestivoExtendedSelected(DiaFestivo_Extended diaFestivoExtendedSelected) {
        this.diaFestivoExtendedSelected = diaFestivoExtendedSelected;
        consultarDiasFestivoByMes();
    }

    public List<DiaFestivo_Extended> getDiasFestivosList() {
        return diasFestivosList;
    }

    public void setDiasFestivosList(List<DiaFestivo_Extended> diasFestivosList) {
        this.diasFestivosList = diasFestivosList;
    }

    public String getNombreMes() {
        return nombreMes;
    }

    public void setNombreMes(String nombreMes) {
        this.nombreMes = nombreMes;
    }

    public DiaFestivo_Extended getDiaFestivoExtended() {
        return diaFestivoExtended;
    }

    public void setDiaFestivoExtended(DiaFestivo_Extended diaFestivoExtended) {
        this.diaFestivoExtended = diaFestivoExtended;
    }

    public String getDiaActivo() {
        return diaActivo;
    }

    public void setDiaActivo(String diaActivo) {
        this.diaActivo = diaActivo;
    }

    public String getDiaInactivo() {
        return diaInactivo;
    }

    public void setDiaInactivo(String diaInactivo) {
        this.diaInactivo = diaInactivo;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

    public Integer getNumeroDia() {
        return numeroDia;
    }

    public void setNumeroDia(Integer numeroDia) {
        this.numeroDia = numeroDia;
    }

    public Date getAux() {
        return aux;
    }

    public void setAux(Date aux) {
        this.aux = aux;
    }    

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

}
