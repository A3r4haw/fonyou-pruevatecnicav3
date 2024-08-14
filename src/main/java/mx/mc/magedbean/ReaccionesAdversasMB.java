/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.magedbean;

import com.lowagie.text.pdf.PdfCopyFields;
import com.lowagie.text.pdf.PdfReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import mx.mc.init.Constantes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Response;
import mx.mc.enums.EstatusReaccionAbversa_Enum;
import mx.mc.enums.RiesgoReaccion_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.lazy.ReaccionAdversaLazy;
import mx.mc.model.TipoReaccion;
import mx.mc.model.Consecuencia;
import mx.mc.model.EstatusReaccion;
import mx.mc.model.Medicamento;
import mx.mc.model.MedicamentoConcomitante;
import mx.mc.model.MedicamentoConcomitanteExtended;
import mx.mc.model.Medicamento_Extended;
import mx.mc.model.Paciente;
import mx.mc.model.Paciente_Extended;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.Reaccion;
import mx.mc.model.ReaccionExtend;
import mx.mc.model.Diagnostico;
import mx.mc.model.Origen;
import mx.mc.model.SurtimientoMinistrado_Extend;
import mx.mc.model.SustanciaActiva;
import mx.mc.model.TipoInforme;
import mx.mc.model.UnidadConcentracion;
import mx.mc.model.Usuario;
import mx.mc.model.ViaAdministracion;
import mx.mc.service.ConsecuenciaService;
import mx.mc.service.DiagnosticoService;
import mx.mc.service.EstatusReaccionService;
import mx.mc.service.MedicamentoConcomitanteService;
import mx.mc.service.MedicamentoService;
import mx.mc.service.OrigenService;
import mx.mc.service.PacienteService;
import mx.mc.service.ReaccionService;
import mx.mc.service.ReportesService;
import mx.mc.service.SurtimientoService;
import mx.mc.service.SurtimientoMinistradoService;
import mx.mc.service.SustanciaActivaService;
import mx.mc.service.TipoInformeService;
import mx.mc.service.UnidadConcentracionService;
import mx.mc.service.ViaAdministracionService;
import mx.mc.util.Comunes;
import mx.mc.util.Mensaje;
import mx.mc.util.PacienteUtil;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.springframework.beans.factory.annotation.Autowired;
import mx.mc.service.TipoReaccionService;
import mx.mc.util.FechaUtil;
import mx.mc.ws.farmacovigilancia.service.ReaccionesAdversas;
import org.primefaces.PrimeFaces;

/**
 *
 * @author bbautista
 */
@Controller
@Scope(value = "view")
public class ReaccionesAdversasMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(ReaccionesAdversasMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    private PermisoUsuario permiso;
    private String cadenaBusqueda;
    private boolean elementoSeleccionado;
    private String sospecha;
    private String notificada;
    private String confirmada;
    private Integer estatusReaccion;
    private ReaccionAdversaLazy reaccionesAdversasLazy;
    private ReaccionExtend reaccionesExtendedSelected;
    private SesionMB sesion;
    private Usuario usuarioSelected;
    private boolean save;
    private ParamBusquedaReporte paramBusquedaReporte;
    private List<Integer> tipoReaccionSelectedList;
    private Date fecha;
    private Date fechaInicioMan;
    private Date fechaFinMan;
    private String archivo;  
    private String rutaPdfs;
    private String pathTmp1;
    private String pathTmp2;
    private String pathTmp3;
    private String pathTmp4;
    private File dirTmp;
    private String urlf; 
    private static PdfReader reader1;
    private static PdfReader reader2;
    private static PdfReader reader3;
    private static PdfReader reader4;
    private boolean activaAutoCompleteInsumos;
    private List<String> riesgosReaccion;

    @Autowired
    private transient EstatusReaccionService reaccionService;
    private List<EstatusReaccion> estatusReaccionList;

    @Autowired
    private transient ReaccionService reaccionesService;
    private List<Reaccion> reaccionesList;
    private Reaccion reaccionSelect;

    @Autowired
    private transient PacienteService pacienteService;
    private Paciente pacienteSelect;
    private Paciente pacienteManSelect;
    
    
    @Autowired
    private transient MedicamentoService medicamentoService;
    private List<Medicamento_Extended> medicamentoList;
    private Medicamento_Extended medicamentoSelect;
    private Medicamento mediConcomitante;
    private Medicamento mediCon;

    @Autowired
    private transient TipoReaccionService tipoReaccionService;
    private List<TipoReaccion> tipoReaccionList;
    private TipoReaccion tipoReaccionSelect;

    @Autowired
    private transient SustanciaActivaService sustanciaService;
    private List<SustanciaActiva> sustanciaActivaList;
    private SustanciaActiva sustanciaSelect;

    @Autowired
    private transient ViaAdministracionService viaService;
    private List<ViaAdministracion> viaList;
    private ViaAdministracion viaSelect;

    @Autowired
    private transient UnidadConcentracionService unidadService;
    private List<UnidadConcentracion> unidadList;

    @Autowired
    private transient MedicamentoConcomitanteService concomitanteService;
    private MedicamentoConcomitanteExtended insumoConcomitante;
    private MedicamentoConcomitanteExtended insumoConcomitanteSelect;
    private List<MedicamentoConcomitanteExtended> insumoConcomitanteList;

    @Autowired
    private transient ConsecuenciaService consecuenciaService;
    private List<Consecuencia> consecuenciaList;

    @Autowired
    private transient TipoInformeService tipoInformeService;
    private List<TipoInforme> tipoInformeList;
    
    @Autowired
    private transient OrigenService origenService;
    private List<Origen> origenListLab;
    private List<Origen> origenListPro;
    
    @Autowired
    private transient ServletContext servletContext;
    
    @Autowired
    private transient SurtimientoMinistradoService surtimientoMinistradoService;
    private List<SurtimientoMinistrado_Extend> ministracionList;
    private List<SurtimientoMinistrado_Extend> ministracionInsumosList;
    private SurtimientoMinistrado_Extend ministracionSelect;
    
    @Autowired
    private transient DiagnosticoService diagnosticoService;
    private Diagnostico diagnostico;
    
    @Autowired
    private transient ReportesService reportesService;
    
    @Autowired
    private transient SurtimientoService surtimientoService;
    
    @PostConstruct
    public void init() {
        LOGGER.debug("mx.mc.magedbean.ReaccionesAdversasMB.init()");
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        permiso = Comunes.obtenerPermisos(Transaccion_Enum.REACCIONESADVERSAS.getSufijo());
        usuarioSelected = sesion.getUsuarioSelected();
        sospecha = Constantes.SOSPECHA;
        notificada = Constantes.NOTIFICADA;
        confirmada = Constantes.CONFIRMADA;
        save = Constantes.INACTIVO;
        estatusReaccion = EstatusReaccionAbversa_Enum.REGISTRADO.getValue();
        paramBusquedaReporte = new ParamBusquedaReporte();      
        estatusReaccionList = new ArrayList<>();
        tipoReaccionList = new ArrayList<>();

        reaccionSelect = new Reaccion();
        dirTmp = new File(Comunes.getPath());
        activaAutoCompleteInsumos = sesion.isActivaAutocompleteQrClave();
        inicialice();
        riesgosReaccion.add(RiesgoReaccion_Enum.ALTO.getValue());
        riesgosReaccion.add(RiesgoReaccion_Enum.MEDIO.getValue());
        riesgosReaccion.add(RiesgoReaccion_Enum.BAJO.getValue());
        obtenerReacciones();
    }

    public void inicialice() {
        try {
            EstatusReaccion estatusReac = new EstatusReaccion();
            TipoReaccion tipoReac = new TipoReaccion();
            Consecuencia cons = new Consecuencia();
            TipoInforme info = new TipoInforme();
            estatusReaccionList = reaccionService.obtenerLista(estatusReac);
            tipoReaccionList = tipoReaccionService.obtenerLista(tipoReac);
            sustanciaActivaList = sustanciaService.obtenerTodo();
            tipoInformeList = tipoInformeService.obtenerLista(info);
            origenListLab = origenService.obtenerLista(Constantes.INACTIVO);
            origenListPro = origenService.obtenerLista(Constantes.ACTIVO);
            viaList = viaService.obtenerTodo();
            unidadList = unidadService.obtenerTodo();
            consecuenciaList = consecuenciaService.obtenerLista(cons);
            pacienteManSelect = new Paciente();
            fecha = new Date();
            riesgosReaccion = new ArrayList<>();
        } catch (Exception ex) {
            LOGGER.error("Error en inicialice(): {}", ex);
        }
    }

    public void obtenerFechaFinal() {
        fechaFinMan = FechaUtil.obtenerFechaFinal(fechaFinMan);
    }
    
    public void obtenerReacciones() {
        LOGGER.debug("mx.mc.magedbean.ReaccionesAdversasMB.obtenerReacciones()");

        boolean status = Constantes.INACTIVO;
        boolean modal = Constantes.INACTIVO;

        if (!permiso.isPuedeVer()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.transaccion"), null);
            status = Constantes.ACTIVO;

        } else {
            try {

                if (cadenaBusqueda != null && cadenaBusqueda.trim().isEmpty()) {
                    cadenaBusqueda = null;
                }

                paramBusquedaReporte.setNuevaBusqueda(true);
                paramBusquedaReporte.setCadenaBusqueda(cadenaBusqueda);

                reaccionesAdversasLazy = new ReaccionAdversaLazy(
                        reaccionesService,
                        paramBusquedaReporte,
                        estatusReaccion);
                LOGGER.debug("Resultados: {}", reaccionesAdversasLazy.getTotalReg());

                status = Constantes.ACTIVO;

            } catch (Exception ex) {
                LOGGER.error(RESOURCES.getString("prc.pac.lista"), ex);
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.pac.lista"), null);
            }
        }
        //PrimeFaces.current().ajax().addCallbackParam("modal", modal);
        //PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    public void nuevoRegistroRam() {
        LOGGER.debug("mx.mc.magedbean.ReaccionesAdversasMB.newRegistroRam()");
        reaccionSelect = new Reaccion();
        insumoConcomitante = new MedicamentoConcomitanteExtended();
        insumoConcomitanteSelect = new MedicamentoConcomitanteExtended();
        insumoConcomitanteList = new ArrayList<>();        
        pacienteSelect = new Paciente();
        medicamentoSelect = new Medicamento_Extended();
        mediConcomitante = new Medicamento();
        mediCon = new Medicamento();
        save = Constantes.INACTIVO;

        reaccionSelect.setFecha(new Date());
        reaccionSelect.setIdEstatusReaccion(EstatusReaccionAbversa_Enum.REGISTRADO.getValue());
        reaccionSelect.setEstado(EstatusReaccionAbversa_Enum.REGISTRADO.toString());
        reaccionSelect.setNombre(usuarioSelected.getNombre());
        reaccionSelect.setApellidoPat(usuarioSelected.getApellidoPaterno());
        reaccionSelect.setApellidoMat(usuarioSelected.getApellidoMaterno());
        reaccionSelect.setCedula(usuarioSelected.getCedProfesional());
        reaccionSelect.setCorreoElectronico(usuarioSelected.getCorreoElectronico());
    }

    public void registroPrellenadoRam(){
        fechaInicioMan = null;
        fechaFinMan = null;
        pacienteManSelect = new Paciente();
        fecha = new Date();
        ministracionList = new ArrayList<>();
    }
    
    public void onTabChange(TabChangeEvent evt) {
    LOGGER.debug("mx.mc.magedbean.ReaccionesAdversasMB.onTabChange()");
        String valorStatus = evt.getTab().getId();
        if (valorStatus.equalsIgnoreCase(Constantes.SOSPECHA)) {
            estatusReaccion = EstatusReaccionAbversa_Enum.REGISTRADO.getValue();            

        } else if (valorStatus.equalsIgnoreCase(Constantes.NOTIFICADA)) {
            estatusReaccion = EstatusReaccionAbversa_Enum.NOTIFICADA.getValue();

        } else if (valorStatus.equalsIgnoreCase(Constantes.CONFIRMADA)) {
            estatusReaccion = EstatusReaccionAbversa_Enum.CONFIRMADA.getValue();

        }
        obtenerReacciones();
    }

    public void verReaccion() {
        LOGGER.debug("mx.mc.magedbean.ReaccionesAdversasMB.verReaccion()");
        try {
            save = Constantes.ACTIVO;
            insumoConcomitante = new MedicamentoConcomitanteExtended();
            insumoConcomitanteList = new ArrayList<>();
            reaccionSelect = new Reaccion();
            reaccionSelect = (Reaccion) reaccionesExtendedSelected;

            if (reaccionSelect.getIdPaciente() != null) {
                pacienteSelect = pacienteService.obtenerPacienteByIdPaciente(reaccionSelect.getIdPaciente());
            }
            if (reaccionSelect.getIdInsumo() != null) {
                medicamentoSelect = medicamentoService.obtenerMedicamentoByIdMedicamento(reaccionSelect.getIdInsumo());
                medicamentoSelect.setFechaCaducidad(reaccionSelect.getCaducidad());
                medicamentoSelect.setLote(reaccionSelect.getLote());                
            }

            insumoConcomitanteList = concomitanteService.obtenerListaByIdReaccion(reaccionSelect.getIdReaccion());
                        
            save = reaccionSelect.getIdEstatusReaccion().equals(EstatusReaccionAbversa_Enum.CONFIRMADA.getValue()) ? Constantes.ACTIVO : Constantes.INACTIVO;
            
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener la reaccion", ex);
        }
    }

    public List<Paciente_Extended> autoCompletePaciente(String cadena) {
        List<Paciente_Extended> pacienteList;
        try {
            pacienteList = pacienteService.searchPacienteAutoComplete(cadena.trim());
        } catch (Exception ex) {
            pacienteList = new ArrayList<>();
            LOGGER.error("Error al obtener Pacientes: {}", ex.getMessage());
        }
        return pacienteList;
    }

    public List<Medicamento_Extended> autoComplete(String cadena) {
       try {
            String idEstructura = surtimientoService.obtenerAlmacenPadreOfSurtimiento(usuarioSelected.getIdEstructura());
            if (idEstructura == null || idEstructura.isEmpty()) {
                idEstructura = usuarioSelected.getIdEstructura();
            }
            medicamentoList = medicamentoService.obtenerMedicamentoParaSRAM(cadena.trim(), idEstructura, activaAutoCompleteInsumos);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener Medicamentos: {}", ex.getMessage());
        }
        return medicamentoList;
    }

    public void selectPaciente(SelectEvent e) {
    LOGGER.debug("mx.mc.magedbean.ReaccionesAdversasMB.selectPaciente()");
        try{
            pacienteSelect = pacienteService.obtenerPacienteByIdPaciente(((Paciente_Extended) e.getObject()).getIdPaciente());
            if (pacienteSelect != null) {
                reaccionSelect.setIdPaciente(pacienteSelect.getIdPaciente());
                reaccionSelect.setNombrePaciente(pacienteSelect.getNombreCompleto() + " " + pacienteSelect.getApellidoPaterno() + " " + pacienteSelect.getApellidoMaterno());
                reaccionSelect.setNumeroPaciente(pacienteSelect.getPacienteNumero());
                reaccionSelect.setFechaNacimiento(pacienteSelect.getFechaNacimiento());
                reaccionSelect.setEdad(PacienteUtil.calcularEdad(pacienteSelect.getFechaNacimiento()));
                reaccionSelect.setSexo(pacienteSelect.getSexo()=='M' ? 1:0);
                reaccionSelect.setRfcPaciente(pacienteSelect.getRfc());
                reaccionSelect.setCurpPaciente(pacienteSelect.getCurp());
            }
        }catch(Exception ex){
            LOGGER.error("Ocurrio un error al obtener el paciente",ex);
        }
    }

    public void selectMedicamento(SelectEvent e) {
    LOGGER.debug("mx.mc.magedbean.ReaccionesAdversasMB.selectMedicamento()");
        try{
            medicamentoSelect = (Medicamento_Extended) e.getObject();            
            
            if (medicamentoSelect != null) {
                reaccionSelect.setIdInsumo(medicamentoSelect.getIdMedicamento());
                reaccionSelect.setClave(medicamentoSelect.getClaveInstitucional());
                reaccionSelect.setMedicamento(medicamentoSelect.getNombreCorto());
                SustanciaActiva sustancia = sustanciaActivaList.stream().filter(m -> m.getIdSustanciaActiva().equals(medicamentoSelect.getSustanciaActiva())).findFirst().orElse(null);
                reaccionSelect.setIdSustanciaActiva(sustancia.getIdSustanciaActiva());
                reaccionSelect.setSustanciaActiva(sustancia.getNombreSustanciaActiva());
                ViaAdministracion via = viaList.stream().filter(p -> p.getIdViaAdministracion().equals(medicamentoSelect.getIdViaAdministracion())).findFirst().orElse(null);
                reaccionSelect.setIdViaAdministracion(via.getIdViaAdministracion());
                reaccionSelect.setViaAdministracion(via.getNombreViaAdministracion());                             
                UnidadConcentracion unidad = unidadList.stream().filter(p->p.getIdUnidadConcentracion().equals(medicamentoSelect.getIdUnidadConcentracion())).findFirst().orElse(null);
                reaccionSelect.setIdUnidad(unidad.getIdUnidadConcentracion());
                reaccionSelect.setUnidad(unidad.getNombreUnidadConcentracion());
                reaccionSelect.setLote(medicamentoSelect.getLote());
                reaccionSelect.setCaducidad(medicamentoSelect.getFechaCaducidad());
                reaccionSelect.setLaboratorio(medicamentoSelect.getNombreFabricante() != null ? medicamentoSelect.getNombreFabricante() : "");
            }
        }catch(Exception ex){
            LOGGER.error("Ocurrio un error en al obtener el medicamento",ex);
        }
    }

    public void selectConcomitante(SelectEvent e) {
        try{
            String idMedicamento = ((Medicamento) e.getObject()).getIdMedicamento();
            mediConcomitante = medicamentoService.obtenerMedicamento(idMedicamento);
        }catch(Exception ex){
            LOGGER.error("Ocurrio un error al obtener el insumo.",ex);
        }
    }

    public void addInsumoConcomitante() {
            LOGGER.info("mx.mc.magedbean.ReaccionesAdversasMB.addInsumoConcomitante().");
        try {
            if (mediConcomitante != null) {
                insumoConcomitante.setIdConcomitante(Comunes.getUUID());
                insumoConcomitante.setIdReaccion(reaccionSelect.getIdReaccion());
                insumoConcomitante.setClave(mediConcomitante.getClaveInstitucional());
                insumoConcomitante.setIdInsumo(mediConcomitante.getIdMedicamento());
                insumoConcomitante.setMedicamento(mediConcomitante.getNombreCorto());
                ViaAdministracion via = viaList.stream().filter(p->p.getIdViaAdministracion().equals(mediConcomitante.getIdViaAdministracion())).findFirst().orElse(null);
                if(via!=null)
                    insumoConcomitante.setViaAdministracion(via.getNombreViaAdministracion());
                insumoConcomitante.setMotivoPrescripcion(reaccionSelect.getMotivoPrescripcion());
                
                insumoConcomitanteList.add(insumoConcomitante);   
                insumoConcomitante = new MedicamentoConcomitanteExtended();
                mediCon = new Medicamento();
            }
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al agregar el insumo", ex);
        }
    }

    public void deleteItem(String idItem){
        LOGGER.debug("mx.mc.magedbean.ReaccionesAdversasMB.deleteItem()");
        try{
            MedicamentoConcomitanteExtended medi = insumoConcomitanteList.stream().filter(p-> p.getIdConcomitante().equals(idItem)).findAny().orElse(null);
            if(medi!=null)
                insumoConcomitanteList.remove(medi);
        }catch(Exception ex)
        {
            LOGGER.error("Ocurrio un error al momento de eliminar el elemento",ex);
        }
    }
    
    public void cancelRAM() {
        nuevoRegistroRam();
    }

    public void saveRAM() {
            LOGGER.info("mx.mc.magedbean.ReaccionesAdversasMB.saveRAM()."); 
        boolean status=false;
        try {                       
            String valida = validaDatos();
            if(valida == "") {
                EstatusReaccion estatus =  estatusReaccionList.stream().filter(p->p.getIdEstatusReaccion().equals(reaccionSelect.getIdEstatusReaccion())).findAny().orElse(null);
                reaccionSelect.setEstatusReaccion(estatus!=null ? estatus.getDescripcion():null);

                TipoReaccion tipoR = tipoReaccionList.stream().filter(p->p.getIdTipoReaccion().equals(reaccionSelect.getIdTipoReaccion())).findAny().orElse(null);
                reaccionSelect.setTipoReaccion(tipoR!=null ? tipoR.getReaccion():null);

                Consecuencia conse = consecuenciaList.stream().filter(p->p.getIdConsecuencia().equals(reaccionSelect.getIdConsecuencia())).findAny().orElse(null);
                reaccionSelect.setConsecuencia(conse!=null ? conse.getDescripcion():null);

                SustanciaActiva sustancia = sustanciaActivaList.stream().filter(p->p.getIdSustanciaActiva().equals(reaccionSelect.getIdSustanciaActiva())).findAny().orElse(null);
                reaccionSelect.setSustanciaActiva(sustancia!=null ? sustancia.getNombreSustanciaActiva():null);

                ViaAdministracion via = viaList.stream().filter(p->p.getIdViaAdministracion().equals(reaccionSelect.getIdViaAdministracion())).findAny().orElse(null);
                reaccionSelect.setViaAdministracion(via.getNombreViaAdministracion());

                List<MedicamentoConcomitante> insumos = parseInsumos(reaccionSelect.getIdReaccion(), insumoConcomitanteList); 
                reaccionSelect.setInsumos(insumos);

                TipoInforme infoLab = tipoInformeList.stream().filter(p->p.getIdTipoInforme().equals(reaccionSelect.getIdTipoInformeLab())).findAny().orElse(null);
                reaccionSelect.setTipoInformeLab(infoLab != null ? infoLab.getDescripcion():null);

                TipoInforme infoPro = tipoInformeList.stream().filter(p->p.getIdTipoInforme().equals(reaccionSelect.getIdTipoInformeProf())).findAny().orElse(null);
                reaccionSelect.setTipoInformeProf(infoPro != null ? infoPro.getDescripcion():null);

                Origen lab = origenListLab.stream().filter(p->p.getIdOrigen().equals(reaccionSelect.getIdOrigenLab())).findAny().orElse(null);
                reaccionSelect.setOrigenLab(lab != null ? lab.getDescripcion():null);

                Origen pro = origenListLab.stream().filter(p->p.getIdOrigen().equals(reaccionSelect.getIdOrigenProf())).findAny().orElse(null);
                reaccionSelect.setOrigenLab(pro != null ? pro.getDescripcion():null);
                //EnviarReaccion send= new EnviarReaccion();
                //send.setReaccion(reaccionSelect);
                //send.setToken("");                
                
                ObjectMapper Obj = new ObjectMapper();            
                String json = Obj.writeValueAsString(reaccionSelect);
                ReaccionesAdversas cs = new ReaccionesAdversas(servletContext);
                Response respMus = cs.registroReaccion(json);
                if(respMus!=null){
                    if(respMus.getStatus()==200){
                        Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("reacciones.info.guardar"), null);
                        status=true;
                    }else
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("reacciones.error.guardar"), null);                 
                }else{
                   Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("reacciones.error.guardar"), null); 
                }
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, valida, null);
            }
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al guardar la RAM", ex);
        }
        PrimeFaces.current().ajax().addCallbackParam("status", status);
    }

     private String validaDatos(){
        String valida = "";
        if(reaccionSelect.getFechaInicioSospecha() == null){
            valida = RESOURCES.getString("reacciones.error.fechaInicioSospecha");
            return valida;
        }
        if(reaccionSelect.getIdTipoReaccion() == 0 || reaccionSelect.getIdTipoReaccion() == null) {
            valida = RESOURCES.getString("reacciones.error.tipoReaccion");
            return valida;
        }
        if(medicamentoSelect == null) {
            valida = RESOURCES.getString("reacciones.error.medicaSospechoso");
            return valida;
        }
        if(reaccionSelect.getRiesgo() == null) {
            valida = RESOURCES.getString("reacciones.error.riesgo");
            return valida;
        }
        return valida;
     }
     
    private List<MedicamentoConcomitante> parseInsumos(String idReaccion, List<MedicamentoConcomitanteExtended> insumoList ){
        LOGGER.debug("mx.mc.magedbean.ReaccionesAdversasMB.parseInsumos()");
        List<MedicamentoConcomitante> insumos= new ArrayList<>();
        try{
            if (insumoList != null && insumoList.size()>0 ) {
                for(MedicamentoConcomitanteExtended item : insumoList){
                    MedicamentoConcomitante insumo = new MedicamentoConcomitante();
                    insumo.setIdConcomitante(Comunes.getUUID());
                    insumo.setIdReaccion(idReaccion);
                    insumo.setIdInsumo(item.getIdInsumo());
                    insumo.setClave(item.getClave());
                    insumo.setIdDiagnostico(item.getIdDiagnostico());
                    insumo.setMotivoPrescripcion(item.getMotivoPrescripcion());
                    insumo.setDosis(item.getDosis());
                    insumo.setInicioTratamiento(item.getInicioTratamiento());
                    insumo.setFinTratamiento(item.getFinTratamiento());
                    insumo.setInsertFecha(new Date());
                    insumo.setInsertIdUsuario(usuarioSelected != null ? usuarioSelected.getIdUsuario():null);

                    insumos.add(insumo);
                }
            }
        }catch(Exception ex){
            LOGGER.error("Ocurrio un error al parsear los insumos",ex);
        }
        return insumos;
    }
    
    public void selectPacienteMan(SelectEvent e) {
        try{
            pacienteManSelect = pacienteService.obtenerPacienteByIdPaciente(((Paciente) e.getObject()).getIdPaciente()); 
            
        }catch(Exception ex){
            LOGGER.error("Ocurrio un error al obtener el paciente",ex);
        }
    }
    
    public void buscarMinistraciones(){
        LOGGER.debug("mx.mc.magedbean.ReaccionesAdversasMB.buscarMinistraciones()");
        try{
            if (pacienteManSelect.getIdPaciente() == null){
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("reacciones.error.pacienteRequerido"), null);
                return;
            }
            if(fechaInicioMan==null){
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("reacciones.error.fechaInicioRequerido"), null);
                return;
            }
            if(fechaFinMan==null){
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("reacciones.error.fechaFinRequerido"), null); 
                return;
            }
            
            ministracionList = surtimientoMinistradoService.obtenerMinistracionesPaciente(pacienteManSelect.getIdPaciente(), fechaInicioMan, fechaFinMan);
                 
        }catch(Exception ex){
            LOGGER.error("Ocurrio un error al buscar las ministraciones.",ex);
        }
    
    }
    
    public void rellenarRAM(SurtimientoMinistrado_Extend ministrado){
        LOGGER.debug("mx.mc.magedbean.ReaccionesAdversasMB.rellenarRAM()");
        boolean status = false;
        try{
            insumoConcomitante = new MedicamentoConcomitanteExtended();
            reaccionSelect = new Reaccion();
            pacienteSelect = pacienteService.obtenerPacienteByIdPaciente(ministrado.getIdPaciente());
            medicamentoSelect = medicamentoService.obtenerMedicamentoByIdMedicamento(ministrado.getIdInsumo());
            EstatusReaccion estatusReac = new EstatusReaccion();
            
            estatusReaccionList = reaccionService.obtenerLista(estatusReac);
            estatusReaccionList.removeIf(n-> !n.getDescripcion().toUpperCase().equals(EstatusReaccionAbversa_Enum.REGISTRADO.toString()));
            
            reaccionSelect.setFecha(new Date());
            reaccionSelect.setIdEstatusReaccion(EstatusReaccionAbversa_Enum.REGISTRADO.getValue());
            reaccionSelect.setEstado(EstatusReaccionAbversa_Enum.REGISTRADO.toString());
            reaccionSelect.setIdPaciente(pacienteSelect.getIdPaciente());
            reaccionSelect.setNombrePaciente(pacienteSelect.getNombreCompleto() + " " + pacienteSelect.getApellidoPaterno() + " " + pacienteSelect.getApellidoMaterno());
            reaccionSelect.setNumeroPaciente(pacienteSelect.getPacienteNumero());
            reaccionSelect.setFechaNacimiento(pacienteSelect.getFechaNacimiento());
            reaccionSelect.setEdad(PacienteUtil.calcularEdad(pacienteSelect.getFechaNacimiento()));
            reaccionSelect.setSexo(pacienteSelect.getSexo()=='M' ? 1:0);
            reaccionSelect.setRfcPaciente(pacienteSelect.getRfc());
            reaccionSelect.setCurpPaciente(pacienteSelect.getCurp());
            
            reaccionSelect.setIdInsumo(medicamentoSelect.getIdMedicamento());
            reaccionSelect.setClave(medicamentoSelect.getClaveInstitucional());
            reaccionSelect.setMedicamento(medicamentoSelect.getNombreCorto());
            SustanciaActiva sustancia = sustanciaActivaList.stream().filter(m -> m.getIdSustanciaActiva().equals(medicamentoSelect.getSustanciaActiva())).findFirst().orElse(null);
            reaccionSelect.setIdSustanciaActiva(sustancia.getIdSustanciaActiva());
            reaccionSelect.setSustanciaActiva(sustancia.getNombreSustanciaActiva());
            ViaAdministracion via = viaList.stream().filter(p -> p.getIdViaAdministracion().equals(medicamentoSelect.getIdViaAdministracion())).findFirst().orElse(null);
            reaccionSelect.setIdViaAdministracion(via.getIdViaAdministracion());
            reaccionSelect.setViaAdministracion(via.getNombreViaAdministracion());
            reaccionSelect.setLote(ministrado.getLote());
            reaccionSelect.setCaducidad(ministrado.getFechaCaducidad());                    
            reaccionSelect.setLaboratorio(ministrado.getNombreFabricante() != null ? medicamentoSelect.getNombreFabricante() : "");
            reaccionSelect.setDosis(Double.parseDouble(ministrado.getDosis().toString()));
            UnidadConcentracion unidad = unidadList.stream().filter(p->p.getIdUnidadConcentracion().equals(medicamentoSelect.getIdUnidadConcentracion())).findFirst().orElse(null);
            reaccionSelect.setIdUnidad(unidad.getIdUnidadConcentracion());
            reaccionSelect.setUnidad(unidad.getNombreUnidadConcentracion());
            reaccionSelect.setFrecuencia(""+ministrado.getFrecuencia());
            reaccionSelect.setDuracion(ministrado.getDuracion());
            diagnostico = diagnosticoService.obtenerPorIdPacienteIdVisitaIdPrescripcion(ministrado.getIdPaciente(), ministrado.getIdVisita(), ministrado.getIdPrescripcion()).get(0);
            reaccionSelect.setMotivoPrescripcion(diagnostico.getNombre());
            reaccionSelect.setInicioAdministracion(ministrado.getFechaInicioTratamiento());                       
            reaccionSelect.setFinAdministracion(FechaUtil.sumarRestarDiasFecha(ministrado.getFechaInicioTratamiento(),ministrado.getDuracion()));
            
            reaccionSelect.setNumeroInformante(usuarioSelected.getMatriculaPersonal());
            reaccionSelect.setCedula(usuarioSelected.getCedProfesional());
            reaccionSelect.setNombre(usuarioSelected.getNombre());
            reaccionSelect.setApellidoPat(usuarioSelected.getApellidoPaterno());
            reaccionSelect.setApellidoMat(usuarioSelected.getApellidoMaterno());
            reaccionSelect.setCorreoElectronico(usuarioSelected.getCorreoElectronico());
            
            ministracionInsumosList = surtimientoMinistradoService.obtenerUltimasMinistracionesPac(pacienteSelect.getIdPaciente());
            insumoConcomitanteList = new ArrayList<>();
            for(SurtimientoMinistrado_Extend item: ministracionInsumosList){
                try{
                    if(!item.getIdInsumo().equals(ministrado.getIdInsumo())){
                        MedicamentoConcomitanteExtended insumo = new MedicamentoConcomitanteExtended();
                        insumo.setIdInsumo(item.getIdInsumo());
                        insumo.setIdConcomitante(item.getIdInsumo());
                        insumo.setClave(item.getClaveInstitucional());
                        insumo.setMedicamento(item.getNombreCorto());
                        insumo.setDosis(Double.parseDouble(item.getDosis().toString()));
                        via = viaList.stream().filter(p -> p.getIdViaAdministracion().equals(item.getIdViaAdministracion())).findFirst().orElse(null);            
                        insumo.setViaAdministracion(via!= null ? via.getNombreViaAdministracion():"");
                        insumo.setInicioTratamiento(item.getFechaInicioTratamiento());
                        insumo.setFinTratamiento(FechaUtil.sumarRestarDiasFecha(item.getFechaInicioTratamiento(),item.getDuracion()));
                        diagnostico = diagnosticoService.obtenerPorIdPacienteIdVisitaIdPrescripcion(item.getIdPaciente(), item.getIdVisita(), item.getIdPrescripcion()).get(0);
                        insumo.setMotivoPrescripcion(diagnostico.getNombre());
                        insumo.setIdDiagnostico(diagnostico.getIdDiagnostico());

                        insumoConcomitanteList.add(insumo);
                    }
                }catch(Exception ex){
                    LOGGER.error("Ocurrio un error al parsear la ministracion.",ex);
                }
            }
            status=true;
        }catch(Exception ex){
            status = false;
            LOGGER.error("Ocurrio un error al llenar la reacción.",ex);
        }  
        PrimeFaces.current().ajax().addCallbackParam("status", status);
    }
        
    public void cambiarStatusReaccion(String idReaccion, Integer idEstatusReaccion) {
        LOGGER.trace("mx.mc.magedbean.ReaccionesAdversasMB.cambiarStatusReaccion()");
        Reaccion reaccion = new Reaccion(); 
        String mensajeStat = "";
        try {
            reaccion.setIdReaccion(idReaccion);
            reaccion.setUpdateFecha(new Date());
            reaccion.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
            if (idEstatusReaccion.equals(EstatusReaccionAbversa_Enum.REGISTRADO.getValue())) {
                reaccion.setIdEstatusReaccion(EstatusReaccionAbversa_Enum.NOTIFICADA.getValue());
                mensajeStat = "ok.notificada";
            } else if(idEstatusReaccion.equals(EstatusReaccionAbversa_Enum.NOTIFICADA.getValue())) {
                reaccion.setIdEstatusReaccion(EstatusReaccionAbversa_Enum.CONFIRMADA.getValue());
                mensajeStat =  "ok.confirmada";
            }
            
            boolean stat = reaccionesService.actualizar(reaccion);
            if (stat) {
                Mensaje.showMessage("Info", RESOURCES.getString(mensajeStat), null);                
            }else{
                Mensaje.showMessage("Error", RESOURCES.getString("almacControl.err.guardDatos"), null);
            }
//            almacenCtrlList.stream().filter((it) -> (it.getIdAlmacenPuntosControl().equals(reaccion.getIdAlmacenPuntosControl()))).forEachOrdered((it) -> {
//                it.setActivo(reaccion.isActivo());
//            });
        } catch (Exception e) {
            LOGGER.error("Error al cambiarStatusAlmContrl: {}", e.getMessage());
        }
    }
    
    public void imprimirReaccion(String idReaccion) throws Exception {
    LOGGER.debug("mx.mc.magedbean.ReaccionesAdversasMB.imprimirReaccion()");
        boolean res = Constantes.INACTIVO;
        boolean estatus = Constantes.INACTIVO;
        try{
            
            Reaccion reaccion = (Reaccion) reaccionesAdversasLazy.getRowData(idReaccion);//    reaccionesList.stream().filter(p-> p.getIdReaccion().equals(idReaccion)).findAny().orElse(null);
            if(reaccion!= null){
                
                res = generaReporte(reaccion,1, true);
                if (res) {
                    List<MedicamentoConcomitanteExtended> insumoConiList = concomitanteService.obtenerListaByIdReaccion(reaccionSelect.getIdReaccion());                    
                    List<MedicamentoConcomitante> insumos = parseInsumos(idReaccion, insumoConiList); 
                    reaccion.setInsumos(insumos);
                    res = generaReporte(reaccion,2, false);
                    if (res) {
                        res = generaReporte(reaccion,3, false);
                        if (res) {
                            res = generaReporte(reaccion,4, false);
                        }
                    }

                }
                if (this.archivo.length() > 0 && res) {
                    String backslash = (System.getProperty("os.name").toLowerCase().contains("windows")) ? "\\" : "/";
                    rutaPdfs = dirTmp.getPath() + backslash + reaccion.getFolio() + ".pdf";
                    reader1 = new PdfReader(pathTmp1);
                    if (pathTmp2.length() > 0) {
                        reader2 = new PdfReader(pathTmp2);
                    }
                    if (pathTmp3.length() > 0) {
                        reader3 = new PdfReader(pathTmp3);
                    }
                    if (pathTmp4.length() > 0) {
                        reader4 = new PdfReader(pathTmp4);
                    }
                    PdfCopyFields copia = new PdfCopyFields(new FileOutputStream(rutaPdfs));
                    copia.addDocument(reader1);

                    if (pathTmp2.length() > 0) {
                        copia.addDocument(reader2);
                    }                    
                    if (pathTmp3.length() > 0) {
                        copia.addDocument(reader3);
                    }                    
                    if (pathTmp4.length() > 0) {
                        copia.addDocument(reader4);
                    }
                    copia.close();
                    this.archivo = urlf + "/resources/tmp/" + reaccion.getFolio() + ".pdf";
                    estatus = Constantes.ACTIVO;
                }
            
            }
            
        }catch(Exception ex){        
            LOGGER.error("Ocurrio un error en imprimirReaccion", ex);
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, estatus);
    }
    
    private boolean generaReporte(Reaccion reaccion,Integer opc,boolean exits){
    LOGGER.debug("mx.mc.magedbean.ReaccionesAdversasMB.generaReporte()");
        boolean status = Constantes.INACTIVO;
        try {
            String rutTicket = (System.getProperty("os.name").toLowerCase().contains("windows")) ? "\\" : "/";
            switch(opc) {
                case 1: pathTmp1 = dirTmp.getPath() + rutTicket + reaccion.getFolio() + "1.pdf"; break;
                case 2: pathTmp2 = dirTmp.getPath() + rutTicket + reaccion.getFolio() + "2.pdf"; break;
                case 3: pathTmp3 = dirTmp.getPath() + rutTicket + reaccion.getFolio() + "3.pdf"; break;
                case 4: pathTmp4 = dirTmp.getPath() + rutTicket + reaccion.getFolio() + "4.pdf"; break;
            }

            FacesContext ctxt = FacesContext.getCurrentInstance();
            ExternalContext ext = ctxt.getExternalContext();
            URI uri = new URI(ext.getRequestScheme(),
                    null,
                    ext.getRequestServerName(),
                    ext.getRequestServerPort(),
                    ext.getRequestContextPath(),
                    null,
                    null);
            String url = uri.toASCIIString();
            urlf = url;

            switch (opc) {
                case 1: status = reportesService.generaReporteReaccion(reaccion, opc, pathTmp1, url); break;
                case 2: status = reportesService.generaReporteReaccion(reaccion, opc, pathTmp2, url); break;
                case 3: status = reportesService.generaReporteReaccion(reaccion, opc, pathTmp3, url); break;
                case 4: status = reportesService.generaReporteReaccion(reaccion, opc, pathTmp4, url); break;
            } 

            if (exits && status) {
                archivo = url + "/resources/tmp/" + reaccion.getFolio() + ".pdf";
            }
        } catch (Exception e) {
            LOGGER.error("Error en generaReporte", e);
            status = Constantes.INACTIVO;
        }

        return status;
    }
        
    //<editor-fold  defaultstate="collapsed" desc="Getter and Setters...">
    public MedicamentoConcomitanteExtended getInsumoConcomitanteSelect() {
        return insumoConcomitanteSelect;
    }

    public void setInsumoConcomitanteSelect(MedicamentoConcomitanteExtended insumoConcomitanteSelect) {
        this.insumoConcomitanteSelect = insumoConcomitanteSelect;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

    public String getCadenaBusqueda() {
        return cadenaBusqueda;
    }

    public void setCadenaBusqueda(String cadenaBusqueda) {
        this.cadenaBusqueda = cadenaBusqueda;
    }

    public boolean isElementoSeleccionado() {
        return elementoSeleccionado;
    }

    public void setElementoSeleccionado(boolean elementoSeleccionado) {
        this.elementoSeleccionado = elementoSeleccionado;
    }

    public String getSospecha() {
        return sospecha;
    }

    public void setSospecha(String sospecha) {
        this.sospecha = sospecha;
    }

    public String getNotificada() {
        return notificada;
    }

    public void setNotificada(String notificada) {
        this.notificada = notificada;
    }

    public String getConfirmada() {
        return confirmada;
    }

    public void setConfirmada(String confirmada) {
        this.confirmada = confirmada;
    }

    public Integer getEstatusReaccion() {
        return estatusReaccion;
    }

    public void setEstatusReaccion(Integer estatusReaccion) {
        this.estatusReaccion = estatusReaccion;
    }

    public ReaccionAdversaLazy getReaccionesAdversasLazy() {
        return reaccionesAdversasLazy;
    }

    public void setReaccionesAdversasLazy(ReaccionAdversaLazy reaccionesAdversasLazy) {
        this.reaccionesAdversasLazy = reaccionesAdversasLazy;
    }

    public ReaccionExtend getReaccionesExtendedSelected() {
        return reaccionesExtendedSelected;
    }

    public void setReaccionesExtendedSelected(ReaccionExtend reaccionesExtendedSelected) {
        this.reaccionesExtendedSelected = reaccionesExtendedSelected;
    }

    public ReaccionService getReaccionesService() {
        return reaccionesService;
    }

    public void setReaccionesService(ReaccionService reaccionesService) {
        this.reaccionesService = reaccionesService;
    }

    public List<Reaccion> getReaccionesList() {
        return reaccionesList;
    }

    public void setReaccionesList(List<Reaccion> reaccionesList) {
        this.reaccionesList = reaccionesList;
    }

    public SesionMB getSesion() {
        return sesion;
    }

    public void setSesion(SesionMB sesion) {
        this.sesion = sesion;
    }

    public Usuario getUsuarioSelected() {
        return usuarioSelected;
    }

    public void setUsuarioSelected(Usuario usuarioSelected) {
        this.usuarioSelected = usuarioSelected;
    }

    public List<Integer> getTipoReaccionSelectedList() {
        return tipoReaccionSelectedList;
    }

    public void setTipoReaccionSelectedList(List<Integer> tipoReaccionSelectedList) {
        this.tipoReaccionSelectedList = tipoReaccionSelectedList;
    }

    public Reaccion getReaccionSelect() {
        return reaccionSelect;
    }

    public void setReaccionSelect(Reaccion reaccionSelect) {
        this.reaccionSelect = reaccionSelect;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<EstatusReaccion> getEstatusReaccionList() {
        return estatusReaccionList;
    }

    public void setEstatusReaccionList(List<EstatusReaccion> estatusReaccionList) {
        this.estatusReaccionList = estatusReaccionList;
    }

    public Paciente getPacienteSelect() {
        return pacienteSelect;
    }

    public void setPacienteSelect(Paciente pacienteSelect) {
        this.pacienteSelect = pacienteSelect;
    }

    public Medicamento_Extended getMedicamentoSelect() {
        return medicamentoSelect;
    }

    public void setMedicamentoSelect(Medicamento_Extended medicamentoSelect) {
        this.medicamentoSelect = medicamentoSelect;
    }

    public List<Medicamento_Extended> getMedicamentoList() {
        return medicamentoList;
    }

    public void setMedicamentoList(List<Medicamento_Extended> medicamentoList) {
        this.medicamentoList = medicamentoList;
    }

    public List<TipoReaccion> getTipoReaccionList() {
        return tipoReaccionList;
    }

    public void setTipoReaccionList(List<TipoReaccion> tipoReaccionList) {
        this.tipoReaccionList = tipoReaccionList;
    }

    public TipoReaccion getTipoReaccionSelect() {
        return tipoReaccionSelect;
    }

    public void setTipoReaccionSelect(TipoReaccion tipoReaccionSelect) {
        this.tipoReaccionSelect = tipoReaccionSelect;
    }

    public List<SustanciaActiva> getSustanciaActivaList() {
        return sustanciaActivaList;
    }

    public void setSustanciaActivaList(List<SustanciaActiva> sustanciaActivaList) {
        this.sustanciaActivaList = sustanciaActivaList;
    }

    public List<ViaAdministracion> getViaList() {
        return viaList;
    }

    public void setViaList(List<ViaAdministracion> viaList) {
        this.viaList = viaList;
    }

    public ViaAdministracion getViaSelect() {
        return viaSelect;
    }

    public void setViaSelect(ViaAdministracion viaSelect) {
        this.viaSelect = viaSelect;
    }

    public List<UnidadConcentracion> getUnidadList() {
        return unidadList;
    }

    public void setUnidadList(List<UnidadConcentracion> unidadList) {
        this.unidadList = unidadList;
    }

    public MedicamentoConcomitanteExtended getInsumoConcomitante() {
        return insumoConcomitante;
    }

    public void setInsumoConcomitante(MedicamentoConcomitanteExtended insumoConcomitante) {
        this.insumoConcomitante = insumoConcomitante;
    }

    public List<MedicamentoConcomitanteExtended> getInsumoConcomitanteList() {
        return insumoConcomitanteList;
    }

    public void setInsumoConcomitanteList(List<MedicamentoConcomitanteExtended> insumoConcomitanteList) {
        this.insumoConcomitanteList = insumoConcomitanteList;
    }

    public Medicamento getMediConcomitante() {
        return mediConcomitante;
    }

    public void setMediConcomitante(Medicamento mediConcomitante) {
        this.mediConcomitante = mediConcomitante;
    }

    public List<Consecuencia> getConsecuenciaList() {
        return consecuenciaList;
    }

    public void setConsecuenciaList(List<Consecuencia> ConsecuenciaList) {
        this.consecuenciaList = ConsecuenciaList;
    }

    public boolean isSave() {
        return save;
    }

    public void setSave(boolean save) {
        this.save = save;
    }
    
    public List<TipoInforme> getTipoInformeList() {
        return tipoInformeList;
    }

    public void setTipoInformeList(List<TipoInforme> tipoInformeList) {
        this.tipoInformeList = tipoInformeList;
    }    

    public Date getFechaInicioMan() {
        return fechaInicioMan;
    }

    public void setFechaInicioMan(Date fechaInicioMan) {
        this.fechaInicioMan = fechaInicioMan;
    }

    public Date getFechaFinMan() {
        return fechaFinMan;
    }

    public void setFechaFinMan(Date fechaFinMan) {
        this.fechaFinMan = fechaFinMan;
    }

    public Paciente getPacienteManSelect() {
        return pacienteManSelect;
    }

    public void setPacienteManSelect(Paciente pacienteManSelect) {
        this.pacienteManSelect = pacienteManSelect;
    }

    public List<SurtimientoMinistrado_Extend> getMinistracionList() {
        return ministracionList;
    }

    public void setMinistracionList(List<SurtimientoMinistrado_Extend> ministracionList) {
        this.ministracionList = ministracionList;
    }

    public SurtimientoMinistrado_Extend getMinistracionSelect() {
        return ministracionSelect;
    }

    public void setMinistracionSelect(SurtimientoMinistrado_Extend ministracionSelect) {
        this.ministracionSelect = ministracionSelect;
    }

    public Medicamento getMediCon() {
        return mediCon;
    }

    public void setMediCon(Medicamento mediCon) {
        this.mediCon = mediCon;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public List<Origen> getOrigenListLab() {
        return origenListLab;
    }

    public void setOrigenListLab(List<Origen> origenListLab) {
        this.origenListLab = origenListLab;
    }

    public List<Origen> getOrigenListPro() {
        return origenListPro;
    }

    public void setOrigenListPro(List<Origen> origenListPro) {
        this.origenListPro = origenListPro;
    }        
    
    //</editor-fold>

    public List<String> getRiesgosReaccion() {
        return riesgosReaccion;
    }

    public void setRiesgosReaccion(List<String> riesgosReaccion) {
        this.riesgosReaccion = riesgosReaccion;
    }
}
