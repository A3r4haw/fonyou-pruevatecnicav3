package mx.mc.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import mx.mc.enums.EstatusOrdenesCompra_Enum;
import mx.mc.enums.EstatusReabasto_Enum;
import mx.mc.enums.TipoDocumento_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.lazy.OrdenCompraLazy;
import mx.mc.model.Estructura;
import mx.mc.model.Folios;
import mx.mc.model.Medicamento;
import mx.mc.model.OrdenCompra;
import mx.mc.model.OrdenCompraDetalleExtended;
import mx.mc.model.OrdenCompra_Extended;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.PresentacionMedicamento;
import mx.mc.model.Proveedor;
import mx.mc.model.Reabasto;
import mx.mc.model.ReabastoInsumo;
import mx.mc.model.TipoOrigen;
import mx.mc.model.Usuario;
import mx.mc.service.EstructuraService;
import mx.mc.service.FoliosService;
import mx.mc.service.MedicamentoService;
import mx.mc.service.OrdenCompraDetalleService;
import mx.mc.service.OrdenCompraService;
import mx.mc.service.PresentacionMedicamentoService;
import mx.mc.service.ProveedorService;
import mx.mc.service.TipoOrigenService;
import mx.mc.util.Comunes;
import mx.mc.util.FechaUtil;
import mx.mc.util.Mensaje;
import org.primefaces.PrimeFaces;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
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
public class OrdenCompraMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(OrdenCompraMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    private List<OrdenCompra_Extended> listaOrdenesCompra;
    private OrdenCompra_Extended ordenCompraSelect;
    private List<Proveedor> listaProveedores;
    private Proveedor proveedor;
    private Proveedor proveedorSelected;
    private String proveedorId;
    private String textoBusqueda;
    private PermisoUsuario permiso;
    private String errPermisos;
    private Usuario usuarioSelect;    
    private Map<String, Integer> listaEstatusOrdenes;
    private List<Proveedor> listProveedores;
    private List<Estructura> listaEstructuras;
    private List<TipoOrigen> listaTipoOrigen;
    private Medicamento medicamento;
    private List<Medicamento> medicamentoList;
    private double precio;
    private Integer cantidad;
    private List<OrdenCompraDetalleExtended> listaOrdenCompraDetalle;
    private Pattern regexNumber;
    private Integer idEstatusOrdenCompra;    
    private String idEstructura;
    private Integer idTipoOrigen;
    private Date fecha;
    private Date fechaActual;
    private Folios folioOrdenCompra;
    private Folios folioReabasto;
    private boolean editarOrdenCompra;
    private List<OrdenCompraDetalleExtended> listaAuxiliarDetalle;
    private int oldValue;
    private int position;
    private int activaRenglon;    
    private boolean esAdministrador;
    private boolean esJefeArea;
    private OrdenCompraLazy ordenCompraLazy;    
    private String nombreEstructura;
    private Estructura estructura;
    

    @Autowired
    private transient OrdenCompraService ordenCompraService;

    @Autowired
    private transient ProveedorService proveedorService;
    
    @Autowired
    private transient EstructuraService estructuraService;
    
    @Autowired
    private transient TipoOrigenService tipoOrigenService;
    
    @Autowired
    private transient MedicamentoService medicamentoService;
    
    @Autowired
    private transient FoliosService foliosService;
    
    @Autowired
    private transient PresentacionMedicamentoService presentacionMedicamentoService;
    
    @Autowired
    private transient OrdenCompraDetalleService OrdenCompraDetalleService;

    /**
     * Metodo que se ejecuta despues de cargar la pantalla de pacientes
     */
    @PostConstruct
    public void init() {
        try {
            errPermisos = "estr.err.permisos";
            initialize();
            permiso = Comunes.obtenerPermisos(Transaccion_Enum.ORDENCOMPRA.getSufijo());
            obtenerUsuarioSesion();
            /*this.listaOrdenesCompra = ordenCompraService.
                    obtenerUltimosRegistrosOrdenCompra(
                            Constantes.REGISTROS_PARA_MOSTRAR);*/        
            
            this.listaProveedores = proveedorService.obtenerListaProveedores();
            if(esAdministrador) {
                //obtener todos las farmacias, almacenes y subalmacenes
                this.listaEstructuras = estructuraService.estFarmAlmacenSubalmacen(null);
//                if (!listaEstructuras.isEmpty()) {
//                    ordenCompraSelect.setIdEstructura(listaEstructuras.get(0).getIdEstructura());
//                    ordenCompraSelect.setNombreEstructura(listaEstructuras.get(0).getNombre());
//                    idEstructura = listaEstructuras.get(0).getIdEstructura();
//                    estructura = estructuraService.obtenerEstructura(idEstructura);
//                }                
            } else {
                if(esJefeArea) {
                    //obtener su estructura e hijas que pertenecen a el
                    List<String> listaIdEstructuraServicios = new ArrayList<>();
                    listaIdEstructuraServicios.add(usuarioSelect.getIdEstructura());
                    listaIdEstructuraServicios.addAll(estructuraService.obtenerIdsEstructuraJerarquica(usuarioSelect.getIdEstructura(), false));
                    
                    this.listaEstructuras.addAll(estructuraService.obtenerEstructurasActivosPorId(listaIdEstructuraServicios)); 
//                    if (!listaEstructuras.isEmpty()) {
//                        ordenCompraSelect.setIdEstructura(listaEstructuras.get(0).getIdEstructura());
//                        ordenCompraSelect.setNombreEstructura(listaEstructuras.get(0).getNombre());
//                        idEstructura = listaEstructuras.get(0).getIdEstructura();
//                        estructura = estructuraService.obtenerEstructura(idEstructura);
//                    }                                    
                } else {
                    // solo traer el almacen al que se encuentra asignado
                    estructura = estructuraService.obtenerEstructura(usuarioSelect.getIdEstructura());
                    idEstructura = estructura.getIdEstructura();
                    nombreEstructura = estructura.getNombre();
                    this.listaEstructuras = estructuraService.estFarmAlmacenSubalmacen(usuarioSelect.getIdEstructura());
                    estructura = estructuraService.obtenerEstructura(idEstructura);
                }
            }
            
            this.listaTipoOrigen = tipoOrigenService.obtenerLista(new TipoOrigen());
            
            listaEstatusOrdenes = new HashMap<>();
            listaEstatusOrdenes.put(EstatusOrdenesCompra_Enum.REGISTRADA.getNombreEstatus(), EstatusOrdenesCompra_Enum.REGISTRADA.getIdEstatusOrdenCompra());
            listaEstatusOrdenes.put(EstatusOrdenesCompra_Enum.ENTREGADA.getNombreEstatus(), EstatusOrdenesCompra_Enum.ENTREGADA.getIdEstatusOrdenCompra());
            listaEstatusOrdenes.put(EstatusOrdenesCompra_Enum.TRANSITO.getNombreEstatus(), EstatusOrdenesCompra_Enum.TRANSITO.getIdEstatusOrdenCompra());
            listaEstatusOrdenes.put(EstatusOrdenesCompra_Enum.CANCELADA.getNombreEstatus(), EstatusOrdenesCompra_Enum.CANCELADA.getIdEstatusOrdenCompra());                        
            regexNumber = Constantes.regexNumber;
            cantidad = 0;
            buscarRegistros();
        } catch (Exception e) {
            LOGGER.error("Error en el metodo init :: {}", e.getMessage());
        }
    }

    /**
     * Metodo que inicializa todos los atributos de la clase
     */
    public void initialize() {
        this.listaOrdenesCompra = new ArrayList<>();
        this.ordenCompraSelect = new OrdenCompra_Extended();
        this.listaProveedores = new ArrayList<>();
        this.listaEstatusOrdenes = new HashMap<String, Integer>();
        this.listProveedores = new ArrayList<>();
        this.listaEstructuras = new ArrayList<>();
        this.listaTipoOrigen = new ArrayList<>();
        this.proveedor = new Proveedor("", "", "", "", "", "", "", new Date(), "", null, null);
        this.proveedorSelected = new Proveedor();
        this.proveedorId = "";
        this.textoBusqueda = "";
        this.listaOrdenCompraDetalle = new ArrayList<>();
        this.fecha = FechaUtil.getFechaActual();
        this.fechaActual = new java.util.Date();
        this.editarOrdenCompra = false;
        this.listaAuxiliarDetalle = new ArrayList<>();  
        this.esAdministrador = false;
        this.esJefeArea = false;
        this.estructura = new Estructura();
        this.idEstructura = "";
    }

    /**
     * Metodo utilizado para insertar proveedores en la bd.
     */
    public void insertarProveedor() {
        try {
            if (!this.permiso.isPuedeCrear()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errPermisos), "");
                return;
            }
            String mensajeError = validarDatosProveedor();
            if (!mensajeError.isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, mensajeError, "");
                return;
            }
           // Usuario usuarioSesion = obtenerUsuarioSesion();
            this.proveedor.setIdProveedor(Comunes.getUUID());
            this.proveedor.setInsertIdUsuario(usuarioSelect.getIdUsuario());
            this.proveedor.setInsertFecha(new Date());
            int resp = proveedorService.insertarProveedor(this.proveedor);
            if (resp != 0) {
                Mensaje.showMessage("Info", RESOURCES.getString("ordenCompra.ok.proveedor"), "");
                this.listaProveedores = proveedorService.obtenerListaProveedores();
                limpiar();
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("ordenCompra.error.proveedor"), "");
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo insertarProveedor :: {}", e.getMessage());
        }
    }

    /**
     * Metodo que obtiene los datos del usuario en la sesion
     *
     * @return Usuario
     */
    private void obtenerUsuarioSesion() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        usuarioSelect = sesion.getUsuarioSelected();
        esAdministrador = sesion.isAdministrador();
        esJefeArea = sesion.isJefeArea();
    }

    /**
     * Metodo que obtiene una lista de proveedores
     */
    public void obtenerListaProveedores() {
        try {
            limpiar();
            this.listaProveedores = proveedorService.obtenerListaProveedores();
        } catch (Exception e) {
            LOGGER.error("Error en el metodo obtenerListaProveedores :: {}", e.getMessage());
        }
    }

    /**
     * Metodo que limpia los campos de los formularios
     */
    public void limpiar() {
        this.proveedor = new Proveedor("", "", "", "", "", "", "", new Date(), "", null, null);
        this.proveedorSelected = new Proveedor("", "", "", "", "", "", "", new Date(), "", null, null);
    }

    /**
     * Metodo que valida si la operacion es una insercion o una actualizacion
     */
    public void validarOperacion() {
        try {
            if (this.proveedorSelected == null) {
                this.proveedorSelected = new Proveedor();
                this.proveedorSelected.setIdProveedor(Constantes.TXT_VACIO);
            }
            Proveedor proveedorExistente = proveedorService.obtener(this.proveedorSelected);
            if (proveedorExistente != null) {
                editarProveedor();
            } else {
                insertarProveedor();
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo validarOperacion :: {}", e.getMessage());
        }
    }

    /**
     * Metodo utilizado para eliminar los proveedores
     */
    public void eliminarProveedor() {
        try {
            if (!this.permiso.isPuedeEliminar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errPermisos), "");
                return;
            }
            String idProveedorSelect
                    = obtenerIdProveedorRegistroSeleccionado();
            int resp = proveedorService.eliminarProveedor(idProveedorSelect);
            if (resp != 0) {
                Mensaje.showMessage("Info", RESOURCES.getString("ordencompra.info.eliminado"), null);
                this.listaProveedores = proveedorService.obtenerListaProveedores();
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo eliminarProveedor :: {}", e.getMessage());
        }
    }

    /**
     * Metodo utilizado para editar el proveedor
     */
    public void editarProveedor() {
        try {
            if (!this.permiso.isPuedeEditar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errPermisos), null);
                return;
            }
            String mensajeError = validarDatosProveedor();
            if (!mensajeError.isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, mensajeError, "");
                return;
            }
            this.proveedor.setUpdateFecha(new Date());
            int resp = proveedorService.editarProveedor(this.proveedor);
            if (resp != 0) {
                limpiar();
                Mensaje.showMessage("Info", RESOURCES.getString("ordencompra.info.actualizado"), null);
                this.listaProveedores = proveedorService.obtenerListaProveedores();
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo eliminarProveedor :: {}", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("ordencompra.err.modificar"), null);
        }
    }

    public void obtenerProveedorSeleccionado() {
        try {
            this.proveedor = this.proveedorSelected;
        } catch (Exception e) {
            LOGGER.error("Error en el metodo obtenerProveedorSeleccionado :: {}", e.getMessage());
        }
    }

    /**
     * Metodo que obtiene el id del proveedor del registro que se selecciono
     *
     * @return idProveedor String
     */
    public String obtenerIdProveedorRegistroSeleccionado() {
        Map<String, String> params
                = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        return params.get("idProveedorParam");
    }

    /**
     * Metodo que obtiene el id de la orden de compra del registro que se
     * selecciono
     *
     * @return idProveedor String
     */
    public String obtenerIdOrdenCompraRegistroSeleccionado() {
        Map<String, String> params
                = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        return params.get("idOrdenCompraParam");
    }

    /**
     * Metodo utilizado para cancelar la orden de compra
     */
    public void cancelarOrdenCompra(String idOrdenCompra) {
        try {
            if (!this.permiso.isPuedeEliminar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errPermisos), null);
                return;
            }
            //String idOrdenCompra = obtenerIdOrdenCompraRegistroSeleccionado();
            OrdenCompra ordenCompra = new OrdenCompra();
            ordenCompra.setIdOrdenCompra(idOrdenCompra);
            ordenCompra.setIdEstatusOrdenCompra(EstatusOrdenesCompra_Enum.CANCELADA.getIdEstatusOrdenCompra());
            ordenCompra.setUpdateFecha(new Date());
            ordenCompra.setUpdateIdUsuario(usuarioSelect.getIdUsuario());
            ordenCompraService.actualizar(ordenCompra);
            //this.listaOrdenesCompra = ordenCompraService.
              //      obtenerUltimosRegistrosOrdenCompra(Constantes.REGISTROS_PARA_MOSTRAR);
        } catch (Exception e) {
            LOGGER.error("Error en el metodo cancelarOrdenCompra :: {}", e.getMessage());
        }
    }

    /**
     * Metodo utilizado para edita el proveedor en la orden de compra
     *
     * @param event
     */
    public void onCellEdit(CellEditEvent event) {
        try {
            int newValue = (int) event.getNewValue();
        oldValue = (int) event.getOldValue();
        position = (int) event.getRowIndex();
        OrdenCompraDetalleExtended item = listaOrdenCompraDetalle.get(position);
        if (!this.permiso.isPuedeEditar()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errPermisos), null);
            return;
        }
            
            /*OrdenCompra_Extended ordenCompraEdit = (OrdenCompra_Extended) event.getObject();
            if (ordenCompraEdit != null && !ordenCompraEdit.getIdProveedor().equalsIgnoreCase(this.proveedorId)) {
                OrdenCompra ordenCompra = new OrdenCompra();
                ordenCompra.setIdOrdenCompra(ordenCompraEdit.getIdOrdenCompra());
                ordenCompra.setIdProveedor(this.getProveedorId());
                ordenCompraService.actualizar(ordenCompra);
                this.listaOrdenesCompra = ordenCompraService.
                        obtenerUltimosRegistrosOrdenCompra(Constantes.REGISTROS_PARA_MOSTRAR);
            }
            Mensaje.showMessage("Info", RESOURCES.getString("ordencompra.info.editar"), null);*/
        } catch (Exception e) {
            LOGGER.error("Error en el metodo cancelarOrdenCompra :: {}", e.getMessage());
        }
    }

    /**
     * Metodo utilizado para validar los campos del proveedor
     *
     * @return String
     */
    private String validarDatosProveedor() {
        if (this.proveedor != null) {
            if (this.proveedor.getNombreProveedor().isEmpty()) {
                return RESOURCES.getString("paciente.error.nombre");
            }
            if (this.proveedor.getEmpresa().isEmpty()) {
                return RESOURCES.getString("ordenCompra.error.empresa");
            }
            if (this.proveedor.getDomicilio().isEmpty()) {
                return RESOURCES.getString("ordenCompra.error.domicilio");
            }
            if (this.proveedor.getCorreo().isEmpty()) {
                return RESOURCES.getString("prc.manual.correo.medico");
            }
            if (!this.proveedor.getRfc().isEmpty()) {
                String regexp = "([A-ZÑ\\x26]{3,4}([0-9]{2})(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1]))((-)?([A-Z\\d]{3}))?$";
                if (!Pattern.matches(regexp, this.proveedor.getRfc().toUpperCase())) {
                    return RESOURCES.getString("paciente.error.formatoRfc");
                }
            }
        }
        return "";
    }

    /**
     * Metodo utilizado para buscar ordenes de acuerdo a un paramentro de
     * busqueda
     */
    public void buscarRegistros() {
        try {
            if(idEstructura != null) {
                estructura = estructuraService.obtenerEstructura(idEstructura);
            }            
            if (textoBusqueda != null && textoBusqueda.trim().isEmpty()) {
                textoBusqueda = null;
            }
            ordenCompraLazy = new OrdenCompraLazy(ordenCompraService, textoBusqueda, idEstructura);
            LOGGER.debug("Resultados: {}", ordenCompraLazy.getTotalReg());

        } catch (Exception e) {
            LOGGER.error("Error en el metodo buscarRegistros :: {}", e.getMessage());
        }
    }

    public void obtenerOrdenCompra(String idOrdenCompra) {
        try {
            editarOrdenCompra = true;
            listaAuxiliarDetalle = new ArrayList<>();
            ordenCompraSelect = ordenCompraService.obtenerOrdenCompra(idOrdenCompra);
             
            listaOrdenCompraDetalle = OrdenCompraDetalleService.obtenerListaDetalleOrden(idOrdenCompra);
            listaOrdenCompraDetalle.forEach((detalle) -> {
                listaAuxiliarDetalle.add(detalle);
            });
             if(ordenCompraSelect.getIdEstatusOrdenCompra() > 0) {
                    switch(ordenCompraSelect.getIdEstatusOrdenCompra()) {
                        case 1:
                            ordenCompraSelect.setNombreEstatus(EstatusOrdenesCompra_Enum.REGISTRADA.getNombreEstatus());
                            break;
                        case 2:
                            ordenCompraSelect.setNombreEstatus(EstatusOrdenesCompra_Enum.ENTREGADA.getNombreEstatus());
                            break;
                        case 3:
                            ordenCompraSelect.setNombreEstatus(EstatusOrdenesCompra_Enum.TRANSITO.getNombreEstatus());
                            break;    
                        case 4:
                            ordenCompraSelect.setNombreEstatus(EstatusOrdenesCompra_Enum.CANCELADA.getNombreEstatus());
                            break;
                        default:
                            ordenCompraSelect.setNombreEstatus(EstatusOrdenesCompra_Enum.REGISTRADA.getNombreEstatus());
                            break;
                    }
                } 
        } catch(Exception ex) {
            LOGGER.error("Error al obtener información de la orden de compra :: {}", ex.getMessage());
        }
    }
    
    public List<Medicamento> autocompleteInsumo(String query) {
        LOGGER.debug("mx.mc.magedbean.OrdenCompraMB.autocompleteInsumo()");
        
        try {
            medicamentoList =  medicamentoService.obtenerInsumos(query);

        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al consultar el medicamento  " + ex.getMessage());
            Mensaje.showMessage("Error", RESOURCES.getString("medicamento.err.autocomplete"),null);
        }
        return medicamentoList;
    }
    
    public void iniciarOrdenCompra() {
        try {
            cantidad = 0;
            listaOrdenCompraDetalle = new ArrayList<>();
            listaAuxiliarDetalle = new ArrayList<>();
            ordenCompraSelect = new OrdenCompra_Extended();
            if(esAdministrador || esJefeArea) {
                estructuraService.getEstructuraForName(nombreEstructura);
            }
            // crear el folio de orden y crear el uuid            
            ordenCompraSelect.setIdOrdenCompra(Comunes.getUUID());
            
            int tipoDoc = TipoDocumento_Enum.ORDEN_COMPRA.getValue();            
            folioOrdenCompra = foliosService.obtenerPrefixPorDocument(tipoDoc);
            
            String folioOrden = Comunes.generaFolio(folioOrdenCompra);
            //Agregamos los datos para actualizar el Folio
            folioOrdenCompra.setSecuencia(Comunes.separaFolio(folioOrden));
            folioOrdenCompra.setUpdateFecha(new Date());
            folioOrdenCompra.setUpdateIdUsuario(usuarioSelect.getIdUsuario());
            
            ordenCompraSelect.setFolioOrdenCompra(folioOrden);
            editarOrdenCompra = false;
            ordenCompraSelect.setNombreEstatus(EstatusOrdenesCompra_Enum.REGISTRADA.getNombreEstatus());
        } catch(Exception ex) {
             LOGGER.error("Error al iniciar la orden de compra  " + ex.getMessage());
        }
    }
    
    public void addDetalleOrdenCompra() {
        try {
            if (permiso.isPuedeCrear()) {
                boolean existe = Constantes.INACTIVO;
                int i = 0;
                if(cantidad <= 0) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "La cantidad debe de ser mayor a cero", null);
                    limpiarCamposInsumo();
                } else {
                    if(precio <= 0) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El precio debe de ser mayor a cero", null);      
                        limpiarCamposInsumo();
                    } else {
                        for(OrdenCompraDetalleExtended detalle : listaOrdenCompraDetalle) {
                            if(detalle.getClaveInstitucional().contains(medicamento.getClaveInstitucional())) {
                                existe = Constantes.ACTIVO;
                                activaRenglon = i;
                                break;
                            }
                            i++;
                        }
                        if(!existe) {
                            Medicamento unMedicamento = medicamentoService.obtenerMedicamento(medicamento.getIdMedicamento());
                            if(unMedicamento != null) {
                                OrdenCompraDetalleExtended unDetalle = new OrdenCompraDetalleExtended();
                                unDetalle.setIdOrdenCompraDetalle(Comunes.getUUID());
                                unDetalle.setIdOrdenCompra(ordenCompraSelect.getIdOrdenCompra());
                                unDetalle.setIdInsumo(medicamento.getIdMedicamento());
                                unDetalle.setCantidad(cantidad * unMedicamento.getFactorTransformacion());
                                unDetalle.setPrecio(precio);
                                unDetalle.setClaveInstitucional(unMedicamento.getClaveInstitucional());
                                unDetalle.setNombreCorto(unMedicamento.getNombreCorto());
                                unDetalle.setIdPresentacionEntrada(unMedicamento.getIdPresentacionEntrada());
                                PresentacionMedicamento presentacion = presentacionMedicamentoService.obtenerPorId(unMedicamento.getIdPresentacionEntrada());
                                if(presentacion != null) {
                                    unDetalle.setPresentacion(presentacion.getNombrePresentacion());
                                }
                                unDetalle.setInsertFecha(new Date());
                                unDetalle.setInsertIdUsuario(usuarioSelect.getIdUsuario());
                                listaOrdenCompraDetalle.add(unDetalle);
                                limpiarCamposInsumo();
                            } 
                        } else {
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El medicamento ya existe", null); //RESOURCES.getString("solicRebasto.err.existeMedicamento"), null);
                            PrimeFaces.current().executeScript("jQuery('td.ui-editable-column').eq(" + activaRenglon + ").each(function(){jQuery(this).click()});");
                        }
                    }
                }
            }
             
        } catch (Exception ex) {
            LOGGER.error("Error al agregar un medicamento  " + ex.getMessage());
        }
    }
    
    public void crearOrdenCompra(boolean registrar) {
        try {
            Reabasto reabasto = new Reabasto();
            List<ReabastoInsumo> listaDetalleInsumo = new ArrayList<>();
            boolean resp = false;
            
            String valida = validacionCampos();
            
            if(!valida.isEmpty()) {
                resp = false;
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, valida, null);//RESOURCES.getString("solicRebasto.err.cantidadRequerida"), null);
            } else {
                //Revisar si existe un nuevo elemento para guardar la lista una vez que se edita
                List<OrdenCompraDetalleExtended> nuevaListaDetalle = new ArrayList<>();
                if(!listaAuxiliarDetalle.isEmpty()) {
                    for(OrdenCompraDetalleExtended detalleOrden : listaOrdenCompraDetalle) {
                        boolean noExiste = true;
                        for(OrdenCompraDetalleExtended auxiliarDetalle : listaAuxiliarDetalle) {
                            if(detalleOrden.getIdInsumo().equalsIgnoreCase(auxiliarDetalle.getIdInsumo())) {
                                noExiste = false;
                                break;
                            }
                        }
                        if(noExiste) {
                            nuevaListaDetalle.add(detalleOrden);
                        }
                    }                    
                }

                /* Se coentan lineas de codigo ya que por el momento no se estara colocando el combo
                if(idEstatusOrdenCompra != null) {
                    switch(idEstatusOrdenCompra) {
                        case 1:
                            ordenCompraSelect.setIdEstatusOrdenCompra(EstatusOrdenesCompra_Enum.REGISTRADA.getIdEstatusOrdenCompra());
                            break;
                        case 2:
                            ordenCompraSelect.setIdEstatusOrdenCompra(EstatusOrdenesCompra_Enum.ENTREGADA.getIdEstatusOrdenCompra());
                            break;
                        case 3:
                            ordenCompraSelect.setIdEstatusOrdenCompra(EstatusOrdenesCompra_Enum.TRANSITO.getIdEstatusOrdenCompra());
                            break;    
                        case 4:
                            ordenCompraSelect.setIdEstatusOrdenCompra(EstatusOrdenesCompra_Enum.CANCELADA.getIdEstatusOrdenCompra());
                            break;
                        default:
                            ordenCompraSelect.setIdEstatusOrdenCompra(EstatusOrdenesCompra_Enum.REGISTRADA.getIdEstatusOrdenCompra());
                            break;
                    }
                }  */                      

                if(!(esAdministrador || esJefeArea)) {
                    ordenCompraSelect.setIdEstructura(idEstructura);
                }
                if(registrar) {
                    if(!editarOrdenCompra) {
                        //Se realiza set de nuevos insert para registrar la orden de compra
                        ordenCompraSelect.setInsertFecha(new Date());
                        ordenCompraSelect.setInsertIdUsuario(usuarioSelect.getIdUsuario());
                        ordenCompraSelect.setIdEstatusOrdenCompra(EstatusOrdenesCompra_Enum.REGISTRADA.getIdEstatusOrdenCompra());
                        resp = ordenCompraService.registrarOrdenCompra(ordenCompraSelect, listaOrdenCompraDetalle, folioOrdenCompra, 
                                 reabasto, listaDetalleInsumo, folioReabasto, false);
                    } else {
                        // solo se actualiza la orden de compra con su detalle
                        ordenCompraSelect.setUpdateFecha(new Date());
                        ordenCompraSelect.setUpdateIdUsuario(usuarioSelect.getIdUsuario());
                        resp = ordenCompraService.actualizarOrdenCompra(ordenCompraSelect, nuevaListaDetalle);
                    }

                } else {
                    ordenCompraSelect.setInsertFecha(new Date());
                    ordenCompraSelect.setInsertIdUsuario(usuarioSelect.getIdUsuario());
                    ordenCompraSelect.setIdEstatusOrdenCompra(EstatusOrdenesCompra_Enum.TRANSITO.getIdEstatusOrdenCompra());


                    String idReabasto = Comunes.getUUID();
                    reabasto.setIdReabasto(idReabasto);
                    reabasto.setIdEstructura(ordenCompraSelect.getIdEstructura());
                    //todo Falta obtener la estructura padre 
                    Estructura estructuraPadre = estructuraService.getEstructuraPadreIdEstructura(ordenCompraSelect.getIdEstructura());
                    reabasto.setIdEstructuraPadre(estructuraPadre.getIdEstructura());
                    reabasto.setIdTipoOrden(Constantes.TIPO_ORDEN_NORMAL);
                    int tipoDoc = TipoDocumento_Enum.ORDEN_REABASTO.getValue();            
                    folioReabasto = foliosService.obtenerPrefixPorDocument(tipoDoc);                    
                    String folReabasto = Comunes.generaFolio(folioReabasto);
                    //Se actualizan datos para folio de reabasto
                    folioReabasto.setSecuencia(Comunes.separaFolio(folReabasto));
                    folioReabasto.setUpdateFecha(new Date());
                    folioReabasto.setUpdateIdUsuario(usuarioSelect.getIdUsuario());
                    
                    reabasto.setFolio(folReabasto);
                    reabasto.setFechaSolicitud(new Date());
                    reabasto.setIdUsuarioSolicitud(usuarioSelect.getIdUsuario());
                    reabasto.setIdEstatusReabasto(EstatusReabasto_Enum.SOLICITADA.getValue());
                    reabasto.setIdProveedor(ordenCompraSelect.getIdProveedor());
                    reabasto.setIdTipoOrigen(ordenCompraSelect.getIdTipoOrigen());
                    reabasto.setInsertFecha(new Date());
                    reabasto.setInsertIdUsuario(usuarioSelect.getIdUsuario());

                    for(OrdenCompraDetalleExtended ordenDetalle : listaOrdenCompraDetalle) {
                        ReabastoInsumo reabastoInsumo = new ReabastoInsumo();
                        reabastoInsumo.setIdReabastoInsumo(Comunes.getUUID());
                        reabastoInsumo.setIdReabasto(idReabasto);
                        reabastoInsumo.setIdInsumo(ordenDetalle.getIdInsumo());
                        reabastoInsumo.setCantidadSolicitada(ordenDetalle.getCantidad());
                        reabastoInsumo.setCantidadComprometida(Constantes.ES_INACTIVO);
                        reabastoInsumo.setIdEstatusReabasto(EstatusReabasto_Enum.SOLICITADA.getValue());
                        reabastoInsumo.setInsertFecha(new Date());
                        reabastoInsumo.setInsertIdUsuario(usuarioSelect.getIdUsuario());
                        listaDetalleInsumo.add(reabastoInsumo);
                        nuevaListaDetalle.add(ordenDetalle);
                    }


                    if(!editarOrdenCompra) {
                        // Entra aqui por que aun no se realiza un registro de orden de Compra
                        resp = ordenCompraService.registrarOrdenCompra(ordenCompraSelect, listaOrdenCompraDetalle, folioOrdenCompra,
                             reabasto, listaDetalleInsumo, folioReabasto, true);

                    } else {
                        //Actualiza solo la orden de compra y se registra la orden de reabasto
                        resp = ordenCompraService.registrarReabastoOrdenCompra(ordenCompraSelect, nuevaListaDetalle, reabasto, listaDetalleInsumo, folioReabasto);
                    }                

                }    
                if(resp) {
                   // this.listaOrdenesCompra = ordenCompraService.obtenerUltimosRegistrosOrdenCompra(Constantes.REGISTROS_PARA_MOSTRAR);
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, "Se registró la orden de compra correctamente", null);//RESOURCES.getString("solicRebasto.err.cantidadRequerida"), null);
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Ocurrió un error al registrar o actualizar la orden de compra", null);//RESOURCES.getString("solicRebasto.err.cantidadRequerida"), null);
                }
            }
            
            PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, resp);
        } catch(Exception ex) {
            LOGGER.error("Error al crear la orden de compra  " + ex.getMessage());
        }
    }        

    private String validacionCampos() {
        //Validar campos requeridos
        String mensaje = "";
        if(ordenCompraSelect.getFecha() == null) {
            mensaje = "El campo fecha es obligatorio";
        } else {
            if(ordenCompraSelect.getIdProveedor() == null) {
                mensaje = "El campo proveedor es obligartorio";
            } else {
                if(esAdministrador || esJefeArea) {
                    if(ordenCompraSelect.getIdEstructura() == null) {
                        mensaje = "El campo almacén / servicio es obligatorio";
                        return mensaje;
                    }  
                }
                if(ordenCompraSelect.getIdTipoOrigen() == null) {
                    mensaje = "El campo tipo orden es obligatorio";
                }                    
                if(listaOrdenCompraDetalle.isEmpty()) {
                    mensaje = "Se deben agregar insumos a la orden";
                }
                
            }
        }
        return mensaje;
    }
    
    public void limpiarCamposInsumo() {
        cantidad = 0;
        precio = 0.0;
        medicamento = new Medicamento();
    }
     
    public void handleSelect(SelectEvent e) {
        medicamento = (Medicamento) e.getObject();
    }

    public void handleUnSelect(UnselectEvent e) {
        medicamento = (Medicamento) e.getObject();
    }
    
    public List<OrdenCompra_Extended> getListaOrdenesCompra() {
        return listaOrdenesCompra;
    }

    public void setListaOrdenesCompra(List<OrdenCompra_Extended> listaOrdenesCompra) {
        this.listaOrdenesCompra = listaOrdenesCompra;
    }

    public OrdenCompraService getOrdenCompraService() {
        return ordenCompraService;
    }

    public void setOrdenCompraService(OrdenCompraService ordenCompraService) {
        this.ordenCompraService = ordenCompraService;
    }

    public OrdenCompra_Extended getOrdenCompraSelect() {
        return ordenCompraSelect;
    }

    public void setOrdenCompraSelect(OrdenCompra_Extended ordenCompraSelect) {
        this.ordenCompraSelect = ordenCompraSelect;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public List<Proveedor> getListaProveedores() {
        return listaProveedores;
    }

    public void setListaProveedores(List<Proveedor> listaProveedores) {
        this.listaProveedores = listaProveedores;
    }

    public Proveedor getProveedorSelected() {
        return proveedorSelected;
    }

    public void setProveedorSelected(Proveedor proveedorSelected) {
        this.proveedorSelected = proveedorSelected;
    }

    public String getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(String proveedorId) {
        this.proveedorId = proveedorId;
    }

    public String getTextoBusqueda() {
        return textoBusqueda;
    }

    public void setTextoBusqueda(String textoBusqueda) {
        this.textoBusqueda = textoBusqueda;
    }

    public Usuario getUsuarioSelect() {
        return usuarioSelect;
    }

    public void setUsuarioSelect(Usuario usuarioSelect) {
        this.usuarioSelect = usuarioSelect;
    }

    public ProveedorService getProveedorService() {
        return proveedorService;
    }

    public void setProveedorService(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

    public Map<String, Integer> getListaEstatusOrdenes() {
        return listaEstatusOrdenes;
    }

    public void setListaEstatusOrdenes(Map<String, Integer> listaEstatusOrdenes) {
        this.listaEstatusOrdenes = listaEstatusOrdenes;
    }   

    public List<Proveedor> getListProveedores() {
        return listProveedores;
    }

    public void setListProveedores(List<Proveedor> listProveedores) {
        this.listProveedores = listProveedores;
    }

    public List<Estructura> getListaEstructuras() {
        return listaEstructuras;
    }

    public void setListaEstructuras(List<Estructura> listaEstructuras) {
        this.listaEstructuras = listaEstructuras;
    }

    public List<TipoOrigen> getListaTipoOrigen() {
        return listaTipoOrigen;
    }

    public void setListaTipoOrigen(List<TipoOrigen> listaTipoOrigen) {
        this.listaTipoOrigen = listaTipoOrigen;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    public List<Medicamento> getMedicamentoList() {
        return medicamentoList;
    }

    public void setMedicamentoList(List<Medicamento> medicamentoList) {
        this.medicamentoList = medicamentoList;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public List<OrdenCompraDetalleExtended> getListaOrdenCompraDetalle() {
        return listaOrdenCompraDetalle;
    }

    public void setListaOrdenCompraDetalle(List<OrdenCompraDetalleExtended> listaOrdenCompraDetalle) {
        this.listaOrdenCompraDetalle = listaOrdenCompraDetalle;
    }    

    public Pattern getRegexNumber() {
        return regexNumber;
    }

    public void setRegexNumber(Pattern regexNumber) {
        this.regexNumber = regexNumber;
    }

    public Integer getIdEstatusOrdenCompra() {
        return idEstatusOrdenCompra;
    }

    public void setIdEstatusOrdenCompra(Integer idEstatusOrdenCompra) {
        this.idEstatusOrdenCompra = idEstatusOrdenCompra;
    }

    public Integer getIdTipoOrigen() {
        return idTipoOrigen;
    }

    public void setIdTipoOrigen(Integer idTipoOrigen) {
        this.idTipoOrigen = idTipoOrigen;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public boolean isEditarOrdenCompra() {
        return editarOrdenCompra;
    }

    public void setEditarOrdenCompra(boolean editarOrdenCompra) {
        this.editarOrdenCompra = editarOrdenCompra;
    }

    public OrdenCompraLazy getOrdenCompraLazy() {
        return ordenCompraLazy;
    }

    public void setOrdenCompraLazy(OrdenCompraLazy ordenCompraLazy) {
        this.ordenCompraLazy = ordenCompraLazy;
    }

    public boolean isEsAdministrador() {
        return esAdministrador;
    }

    public void setEsAdministrador(boolean esAdministrador) {
        this.esAdministrador = esAdministrador;
    }

    public boolean isEsJefeArea() {
        return esJefeArea;
    }

    public void setEsJefeArea(boolean esJefeArea) {
        this.esJefeArea = esJefeArea;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public String getNombreEstructura() {
        return nombreEstructura;
    }

    public void setNombreEstructura(String nombreEstructura) {
        this.nombreEstructura = nombreEstructura;
    }
        
}
