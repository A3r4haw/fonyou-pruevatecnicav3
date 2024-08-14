/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.magedbean;

import java.io.File;
import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.CamaExtended;
import mx.mc.model.DispensacionMaterialExtended;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.Estructura;
import mx.mc.model.Medicamento;
import mx.mc.model.Paciente_Extended;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.Usuario;
import mx.mc.service.CamaService;
import mx.mc.service.DispensacionMaterialService;
import mx.mc.service.EntidadHospitalariaService;
import mx.mc.service.EstructuraService;
import mx.mc.service.MedicamentoService;
import mx.mc.service.PacienteService;
import mx.mc.service.ReportesService;
import mx.mc.service.UsuarioService;
import mx.mc.util.Comunes;
import mx.mc.util.FechaUtil;
import mx.mc.util.Mensaje;
import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Ulai
 */
@Controller
@Scope(value = "view")
public class RepDispensacionMaterialesMB implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(RepDispensacionMaterialesMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private PermisoUsuario permiso;
    private ParamBusquedaReporte paramBusquedaReporte;    
    private Date fechaActualInicio;
    private Date fechaActual;
    private Usuario usuarioSession;
    private String pathPdf;    
    
    private List<Estructura> listServices;
    private String idEstructura;
    private List<CamaExtended> listaCamas;
    private Estructura servicio;
    private String idCama;
    private List<DispensacionMaterialExtended> listDispensacionMaterialExtended;

    @Autowired
    private transient EstructuraService estructuraService;

    @Autowired
    private transient UsuarioService usuarioService;

    @Autowired
    private transient CamaService camaService; 
    
    @Autowired
    private transient MedicamentoService medicamentoService;
    
    @Autowired
    private transient PacienteService pacienteService;
    
    @Autowired
    private transient DispensacionMaterialService dispensacionMaterialService;
    
    @Autowired
    private transient ReportesService reportesService;

    @Autowired
    private transient EntidadHospitalariaService entidadHospitalariaService;
    
    @PostConstruct
    public void init() {
        try {
            this.usuarioSession = Comunes.obtenerUsuarioSesion();            
            initialize();
            permiso = Comunes.obtenerPermisos(Transaccion_Enum.REPDISPMATERIAL.getSufijo());
        } catch (Exception e) {
            LOGGER.error("Error en el metodo init :: {}", e.getMessage());
        }
    }
    
    public void initialize() throws Exception {
        try {            
            fechaActual = FechaUtil.obtenerFechaFin();
            usuarioSession = usuarioService.obtenerUsuarioPorId(usuarioSession.getIdUsuario());

            listDispensacionMaterialExtended = new ArrayList<>();
            listServices = estructuraService.getEstructuraServicios(null, null);
            listaCamas = camaService.obtenerCamaByEstructuraAndEstatus(null, null);
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener la lista de Servicios: {}", ex.getMessage());
        }
        paramBusquedaReporte = new ParamBusquedaReporte();
        paramBusquedaReporte.setFechaInicio(FechaUtil.obtenerFechaInicio());
        paramBusquedaReporte.setFechaFin(FechaUtil.obtenerFechaFin());
    }

    public void getCamas(){
        LOGGER.debug("mx.mc.magedbean.RepDispensacionMaterialesMB.getCamas()");
        try{
            this.listaCamas = camaService.obtenerCamaByEstructuraAndEstatus(this.idEstructura, null);
        }catch(Exception ex){
            LOGGER.error("Error en el método getCamas(): {}", ex.getMessage());
        }
    }
    
    public void generateReportDispensacionConsult(){
        LOGGER.debug("mx.mc.magedbean.RepDispensacionMaterialesMB.generateReportDispensacionConsult()");
        try{
            paramBusquedaReporte.setIdEstructura(idEstructura);
            paramBusquedaReporte.setCama(idCama);
            listDispensacionMaterialExtended = dispensacionMaterialService.obtenerDispensacionesReporte(paramBusquedaReporte);
        }catch(Exception ex){
            LOGGER.error("Ocurrio un error al consultar reporte: {}", ex.getMessage());
        }
    
    }
    
    public List<Medicamento> autocompleteInsumo(String query) {
        LOGGER.debug("mx.mc.magedbean.RepDispensacionMaterialesMB.autocompleteInsumo()");
        List<Medicamento> insumosList = new ArrayList<>();
        try {
            insumosList.addAll(medicamentoService.obtenerInsumos(query));

        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al consultar el medicamento");
            Mensaje.showMessage("Error", RESOURCES.getString("medicamento.err.autocomplete"), null);
        }
        return insumosList;
    }
    
    public List<Usuario> autocompleteUsuario(String query) {
        LOGGER.debug("mx.mc.magedbean.RepDispensacionMaterialesMB.autocompleteUsuario()");
        List<Usuario> listUsuarios = new ArrayList<>();
        try {
            listUsuarios.addAll(usuarioService.obtenerUsuarios(query));
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener los usuarios; {}", ex.getMessage());
        }
        return listUsuarios;
    }
    
    public List<Paciente_Extended> autoCompletePacientes(String cadena) {
        LOGGER.debug("mx.mc.magedbean.RepDispensacionMaterialesMB.autoCompletePacientes()");
        List<Paciente_Extended> listaPacientes = new ArrayList<>();
        try {
            listaPacientes = this.pacienteService.obtenerRegistrosPorCriterioDeBusqueda(
                    cadena.trim(), Constantes.REGISTROS_PARA_MOSTRAR);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener obtenerListaClavesSku : {}", ex.getMessage());
        }
        return listaPacientes;
    }
    
    public void obtenerFechaFinal() {        
        paramBusquedaReporte.setFechaFin(FechaUtil.obtenerFechaFinal(paramBusquedaReporte.getFechaFin()));
    }
    
    public void imprimeReporteMaterialesCuracion() throws Exception {
        LOGGER.debug("mx.mc.magedbean.RepDispensacionMaterialesMB.imprimeReporteMaterialesCuracion()");
        boolean status = Constantes.INACTIVO;
        EntidadHospitalaria entidad;
        Estructura est;
            paramBusquedaReporte.setIdEstructura(idEstructura);
            paramBusquedaReporte.setCama(idCama);
            est = estructuraService.obtenerEstructura(usuarioSession.getIdEstructura());            
            entidad = entidadHospitalariaService.obtenerEntidadById(est.getIdEntidadHospitalaria());
            try{                
                byte[] buffer = reportesService.imprimeReporteMaterialesCuracion(this.paramBusquedaReporte, entidad);
                if (buffer != null) {
                    status = Constantes.ACTIVO;
                    SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "sesionMB");
                    sesion.setReportStream(buffer);
                    sesion.setReportName(String.format("reporteMaterialesCuracion_%s.pdf", FechaUtil.formatoFecha(new Date(), "yyyyMMdd")));
                }
            }catch(Exception ex){
                LOGGER.error("Error al generar la Impresión: {}", ex.getMessage());
            }
            PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }
    
    public void imprimeExcelMaterialesCuracion() throws Exception {
        LOGGER.debug("mx.mc.magedbean.RepDispensacionMaterialesMB.imprimeReporteMaterialesCuracion()");
        boolean status = Constantes.INACTIVO;
        EntidadHospitalaria entidad;
        Estructura est;
        try {
            est = estructuraService.obtenerEstructura(usuarioSession.getIdEstructura());              
            entidad = entidadHospitalariaService.obtenerEntidadById(est.getIdEntidadHospitalaria());
            
            boolean res = reportesService.imprimeExcelMaterialesCuracion(this.paramBusquedaReporte,entidad);            
            if(res){
                status = Constantes.ACTIVO;
            }
            
        } catch (Exception e) {
            LOGGER.error("Error al generar Excel: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public List<DispensacionMaterialExtended> getListDispensacionMaterialExtended() {
        return listDispensacionMaterialExtended;
    }

    public void setListDispensacionMaterialExtended(List<DispensacionMaterialExtended> listDispensacionMaterialExtended) {
        this.listDispensacionMaterialExtended = listDispensacionMaterialExtended;
    }

    public List<Estructura> getListServices() {
        return listServices;
    }

    public void setListServices(List<Estructura> listServices) {
        this.listServices = listServices;
    }    

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public Estructura getServicio() {
        return servicio;
    }

    public void setServicio(Estructura servicio) {
        this.servicio = servicio;
    }

    public ParamBusquedaReporte getParamBusquedaReporte() {
        return paramBusquedaReporte;
    }

    public void setParamBusquedaReporte(ParamBusquedaReporte paramBusquedaReporte) {
        this.paramBusquedaReporte = paramBusquedaReporte;
    }

    public List<CamaExtended> getListaCamas() {
        return listaCamas;
    }

    public void setListaCamas(List<CamaExtended> listaCamas) {
        this.listaCamas = listaCamas;
    }

    public String getIdCama() {
        return idCama;
    }

    public void setIdCama(String idCama) {
        this.idCama = idCama;
    }

    public Usuario getUsuarioSession() {
        return usuarioSession;
    }

    public void setUsuarioSession(Usuario usuarioSession) {
        this.usuarioSession = usuarioSession;
    }

    public Date getFechaActualInicio() {
        return fechaActualInicio;
    }

    public void setFechaActualInicio(Date fechaActualInicio) {
        this.fechaActualInicio = fechaActualInicio;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public String getPathPdf() {
        return pathPdf;
    }

    public void setPathPdf(String pathPdf) {
        this.pathPdf = pathPdf;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

}
