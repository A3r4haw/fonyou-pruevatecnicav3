/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import mx.mc.enums.TipoAlmacen_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.lazy.BitacoraAccionesUsuarioLazy;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.Estructura;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.Usuario;
import mx.mc.service.BitacoraAccionUsuarioService;
import mx.mc.service.EntidadHospitalariaService;
import mx.mc.service.EstructuraService;
import mx.mc.service.ReportesService;
import mx.mc.service.UsuarioService;
import mx.mc.util.Comunes;
import mx.mc.util.FechaUtil;
import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author gcruz
 */
@Controller
@Scope(value = "view")
public class BitacoraAccionesUsuarioMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(BitacoraAccionesUsuarioMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private PermisoUsuario permiso;    
     private Date fechaActual;
     private ParamBusquedaReporte paramBusquedaReporte;
    private Usuario usuarioSession;
    private boolean administrador;
    private boolean jefeArea;
    private Estructura estructura;
    private String idEstructura;
    private List<Estructura> listAlmacenesSubAlm;
    private List<Estructura> listaAuxiliar;
    private BitacoraAccionesUsuarioLazy bitacoraAccionesUsuarioLazy;       
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private EstructuraService estructuraService;
    
    @Autowired
    private BitacoraAccionUsuarioService bitacoraAccionUsuarioService;
    
    @Autowired
    private EntidadHospitalariaService entidadHospitalariaService;
    
    @Autowired
    private ReportesService reportesService;
    
    @PostConstruct
    public void init() {
        try {
            initialize();
            listAlmacenesSubAlm = new ArrayList<>();
            this.administrador = Comunes.isAdministrador();
            this.jefeArea = Comunes.isJefeArea();
            this.usuarioSession = Comunes.obtenerUsuarioSesion();
            paramBusquedaReporte = new ParamBusquedaReporte();        
            paramBusquedaReporte.setFechaInicio(FechaUtil.obtenerFechaInicio());
            paramBusquedaReporte.setFechaFin(FechaUtil.obtenerFechaFin());
            permiso = Comunes.obtenerPermisos(Transaccion_Enum.BITACORACCIONESUSER.getSufijo());
            buscarServicio();
        } catch (Exception e) {
            LOGGER.error("Error en el metodo init :: {}", e.getMessage());
        }
    }
    
    /**
    * Metodo que inicializa todos los atributos de la clase
    */
    public void initialize() {
        try {              
            listAlmacenesSubAlm = new ArrayList<>();            
            fechaActual = new java.util.Date();
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al inicializar las variables: {}", ex.getMessage());
        }
    }        
    
    public void consultarAcciones() {
        try {  
            //if(permiso.isPuedeVer()) {
                if (administrador || jefeArea) {
                    this.paramBusquedaReporte.setIdEstructura(idEstructura);
                } else {
                    this.paramBusquedaReporte.setIdEstructura(usuarioSession.getIdEstructura());                        
                }
                bitacoraAccionesUsuarioLazy =  new BitacoraAccionesUsuarioLazy(bitacoraAccionUsuarioService, paramBusquedaReporte);
            /*}else {
                // Error de permiso para el usuario
                Mensaje.showMessage("Error", "No tienes permiso para realizar esta acción", null);//RESOURCES.getString("medicamento.err.autocomplete"), null);
            }*/
        } catch(Exception ex) {
            LOGGER.error("Ocurrio un error al consultar las acciones de usuarios:  " + ex.getMessage());
        }
    }  
    
    /**
     * Metodo para obtener la fecha fin con el formato 23:59 en lugar de 00:00
     */
    public void obtenerFechaFinal() {
        paramBusquedaReporte.setFechaFin(FechaUtil.obtenerFechaFinal(paramBusquedaReporte.getFechaFin()));
    }
    
    public List<Usuario> autocompleteUsuario(String query) {
        LOGGER.debug("mx.mc.magedbean.BitacoraAccionesUsuarioMB.autocompleteUsuario()");
        List<Usuario> listUsuarios = new ArrayList<>();
        try {
            listUsuarios.addAll(usuarioService.obtenerUsuarios(query));
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener los usuarios: {}", ex.getMessage());
        }
        return listUsuarios;
    }
    
    public void buscarServicio() {
        
        boolean noEncontroRegistro = Constantes.INACTIVO;
        List<Integer> listTipoAlmacen = new ArrayList<>();
        listTipoAlmacen.add(TipoAlmacen_Enum.NO_APLICA.getValue());
        try {
            if (this.administrador) {
                this.listaAuxiliar = estructuraService.getEstructuraByLisTipoAlmacen(listTipoAlmacen);
                this.listAlmacenesSubAlm.add(listaAuxiliar.get(0));
                ordenarListaEstructura(listaAuxiliar.get(0));
            } else if(jefeArea) {
                listAlmacenesSubAlm = estructuraService.obtenerAlmacenesQueSurtenServicio(this.usuarioSession.getIdEstructura());
                if(listAlmacenesSubAlm.isEmpty()) {
                    noEncontroRegistro = Constantes.ACTIVO;
                    this.jefeArea = Constantes.INACTIVO;
                } else {
                    this.estructura = listAlmacenesSubAlm.get(0);
                }
            } else {
                 noEncontroRegistro = Constantes.ACTIVO;                
            }
            if(noEncontroRegistro) {
                this.estructura = estructuraService.obtenerEstructura(this.usuarioSession.getIdEstructura());
            }
        } catch(Exception ex) {
            LOGGER.error("Error al buscar la lista de servicios :: {}", ex.getMessage());
        }
        
    }
    
    public void ordenarListaEstructura(Estructura estrucPrincipal) {
        try {
            if (estrucPrincipal != null) {
                for (int i = 1; i < listaAuxiliar.size(); i++) {
                    if (listaAuxiliar.get(i).getIdEstructuraPadre() != null
                            && listaAuxiliar.get(i).getIdEstructuraPadre().toLowerCase().contains(estrucPrincipal.getIdEstructura().toLowerCase())) {
                        listAlmacenesSubAlm.add(listaAuxiliar.get(i));
                        ordenarListaEstructura(listaAuxiliar.get(i));
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al ordenar la lista :: {}", ex.getMessage());
        }
    }           

    public void imprimeReporteAccionesUsuario() throws Exception {
        LOGGER.debug("mx.mc.magedbean.BitacoraAccionesUsuarioMB.imprimeReporteAccionesUsuario()");
        boolean status = Constantes.INACTIVO;
        try {
            if (administrador || jefeArea) {
                if (this.idEstructura != null) {
                    this.paramBusquedaReporte.setIdEstructura(idEstructura);
                } else {
                    this.paramBusquedaReporte.setIdEstructura(null);
                }
            } else {
                this.paramBusquedaReporte.setIdEstructura(usuarioSession.getIdEstructura());
            }
            EntidadHospitalaria entidad;
            if (this.paramBusquedaReporte.getIdEstructura() != null) {
                Estructura est = new Estructura();
                est.setIdEstructura(this.paramBusquedaReporte.getIdEstructura());
                Estructura e = estructuraService.obtenerEstructura(est.getIdEstructura());
                this.paramBusquedaReporte.setNombreEstructura(e.getNombre());

                EntidadHospitalaria ent = new EntidadHospitalaria();
                ent.setIdEntidadHospitalaria(e.getIdEntidadHospitalaria());
                entidad = entidadHospitalariaService.obtener(ent);
            } else {
                this.paramBusquedaReporte.setNombreEstructura(Constantes.ALMACENES_TODOS);
                entidad = entidadHospitalariaService.obtenerEntidadHospital();
            }

            byte[] buffer = reportesService.imprimeReporteAccionesUsuario(paramBusquedaReporte, entidad);
            if (buffer != null) {
                status = Constantes.ACTIVO;
                SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "sesionMB");
                sesion.setReportStream(buffer);
                sesion.setReportName(String.format("bitacoraAccionesUsuario_%s.pdf", FechaUtil.formatoFecha(new Date(), "yyyyMMdd")));
            }
        } catch (Exception e) {
            LOGGER.error("Error al generar la Impresión: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }
    
    public void generaExcelBitacoraAcciones() throws Exception {
        LOGGER.debug("mx.mc.magedbean.BitacoraAccionesUsuarioMB.imprimirExcel()");
        boolean status = Constantes.INACTIVO;
        try {            
            if (!administrador && !jefeArea) {
                this.paramBusquedaReporte.setIdEstructura(usuarioSession.getIdEstructura());
            } else {
                this.paramBusquedaReporte.setIdEstructura(idEstructura);
            }            

            Estructura est;
            if (this.paramBusquedaReporte.getIdEstructura() == null) {
                est = estructuraService.obtenerEstructura(usuarioSession.getIdEstructura());
            } else {
                est = estructuraService.obtenerEstructura(paramBusquedaReporte.getIdEstructura());
            }

            EntidadHospitalaria entidad = entidadHospitalariaService.obtenerEntidadById(est.getIdEntidadHospitalaria());

            boolean res = reportesService.imprimeExcelBitacoraAcciones(this.paramBusquedaReporte, entidad);
            if (res) {
                status = Constantes.ACTIVO;
            }

        } catch (Exception e) {
            LOGGER.error("Error al generar Excel: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }
    
    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public ParamBusquedaReporte getParamBusquedaReporte() {
        return paramBusquedaReporte;
    }

    public void setParamBusquedaReporte(ParamBusquedaReporte paramBusquedaReporte) {
        this.paramBusquedaReporte = paramBusquedaReporte;
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

    public boolean isJefeArea() {
        return jefeArea;
    }

    public void setJefeArea(boolean jefeArea) {
        this.jefeArea = jefeArea;
    }

    public Estructura getEstructura() {
        return estructura;
    }

    public void setEstructura(Estructura estructura) {
        this.estructura = estructura;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public List<Estructura> getListAlmacenesSubAlm() {
        return listAlmacenesSubAlm;
    }

    public void setListAlmacenesSubAlm(List<Estructura> listAlmacenesSubAlm) {
        this.listAlmacenesSubAlm = listAlmacenesSubAlm;
    }

    public List<Estructura> getListaAuxiliar() {
        return listaAuxiliar;
    }

    public void setListaAuxiliar(List<Estructura> listaAuxiliar) {
        this.listaAuxiliar = listaAuxiliar;
    }

    public BitacoraAccionesUsuarioLazy getBitacoraAccionesUsuarioLazy() {
        return bitacoraAccionesUsuarioLazy;
    }

    public void setBitacoraAccionesUsuarioLazy(BitacoraAccionesUsuarioLazy bitacoraAccionesUsuarioLazy) {
        this.bitacoraAccionesUsuarioLazy = bitacoraAccionesUsuarioLazy;
    }

}
