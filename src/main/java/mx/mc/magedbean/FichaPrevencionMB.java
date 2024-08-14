package mx.mc.magedbean;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import mx.mc.enums.Accion_Enum;
import mx.mc.enums.EstatusFichaPrevencion_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.Adjunto;
import mx.mc.model.AdjuntoExtended;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.EstatusFichaPrevencion;
import mx.mc.model.Estructura;
import mx.mc.model.FichaPrevencion;
import mx.mc.model.FichaPrevencionExtended;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.TransaccionPermisos;
import mx.mc.model.Turno;
import mx.mc.model.Usuario;
import mx.mc.service.AdjuntoService;
import mx.mc.service.EntidadHospitalariaService;
import mx.mc.service.EstatusFichaPrevencionService;
import mx.mc.service.EstructuraService;
import mx.mc.service.FichaPrevencionService;
import mx.mc.service.ReportesService;
import mx.mc.service.TurnoService;
import mx.mc.service.UsuarioService;
import mx.mc.util.Comunes;
import mx.mc.util.FechaUtil;
import mx.mc.util.Mensaje;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;
import org.primefaces.shaded.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Cervanets
 */
@Controller
@Scope(value = "view")
public class FichaPrevencionMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(FichaPrevencionMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    private transient boolean admin;
    private transient boolean jefeArea;
    private transient List<TransaccionPermisos> permisosLista;
    private PermisoUsuario permiso;
    private Date fechaActual;
    private String cadenaBusqueda;
    private String tipoAccionCreaEdita;
    private Usuario usuarioEnSesion;
    private transient List<FichaPrevencionExtended> fichaPrevencionLista;
    private FichaPrevencionExtended fichaPrevencionSeleccionado;
    private transient UploadedFile file;
    private transient List<AdjuntoExtended> adjuntoLista;
    private transient Adjunto adjuntoSelecionado;
    private transient DefaultStreamedContent adjuntoDescarga;
    private transient List<Turno> turnoLista;
    private transient Turno turnoSeleccionado;
    private transient List<Estructura> estructuraLista;
    private transient Estructura estructuraSeleccionado;
    private transient List<EstatusFichaPrevencion> estatusFichaPrevencionLista;
    private transient EstatusFichaPrevencion estatusFichaPrevencionSeleccionado;
    private transient Usuario usuarioRegistraPreseleccionado;

    @Autowired
    private transient FichaPrevencionService fichaPrevencionService;

    @Autowired
    private transient TurnoService turnoService;

    @Autowired
    private transient EstructuraService estructuraService;

    @Autowired
    private transient EstatusFichaPrevencionService estatusFichaPrevencionService;

    @Autowired
    private transient UsuarioService usuariosService;

    @Autowired
    private transient AdjuntoService adjuntoService;

    @Autowired
    private transient EntidadHospitalariaService entidadHospitalariaService;

    @Autowired
    private transient ReportesService reportesService;

    /**
     * Postconsrtuctor que gestiona lo necesario
     */
    @PostConstruct
    public void init() {
        LOGGER.trace("mx.mc.magedbean.PrevencionContaminacionMB.init()");
        inicializaVariables();
        limpiaDatos();
        obtenerFichasDePrevencion();
        obtenerEstructura();
        obtenerTurnos();
        obtenerEstatusPrevencion();
    }

    /**
     * Inicializa las variables necesarias
     */
    private void inicializaVariables() {
        LOGGER.trace("mx.mc.magedbean.PrevencionContaminacionMB.inicializaVariables()");
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        this.usuarioEnSesion = sesion.getUsuarioSelected();
        this.permiso = Comunes.obtenerPermisos(Transaccion_Enum.PREVENCIONCONTAMINACION.getSufijo());
        this.admin = sesion.isAdministrador();
        this.jefeArea = sesion.isJefeArea();
        this.fechaActual = new java.util.Date();
        this.usuarioRegistraPreseleccionado = usuarioEnSesion;
    }

    /**
     * Limpia las variables
     */
    private void limpiaDatos() {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.limpia()");
        this.cadenaBusqueda = null;
        this.fichaPrevencionLista = new ArrayList<>();
        this.fichaPrevencionSeleccionado = new FichaPrevencionExtended();
    }

    /**
     * Obtiene la lista de fichas de registro de prevención de contaminación
     */
    public void obtenerFichasDePrevencion() {
        LOGGER.trace("mx.mc.magedbean.PrevencionContaminacionMB.obtenerFichasDePrevencion()");
        boolean status = Constantes.INACTIVO;
        try {
            this.fichaPrevencionLista = new ArrayList<>();
            FichaPrevencionExtended fpe = new FichaPrevencionExtended();
            this.fichaPrevencionLista.addAll(fichaPrevencionService.obtenerListaFichas(fpe));
            status = Constantes.ACTIVO;
        } catch (Exception e) {
            String err_ficprev_obtener_lista = "No se pudo obtener la lista de fichas de Prevención de contaminación ";
            LOGGER.error(err_ficprev_obtener_lista + " ", e.getMessage());
            PrimeFaces.current().ajax().addCallbackParam("estatus", status);
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, err_ficprev_obtener_lista, null);
        }
    }

    /**
     * Obtiene la lista de estatusPrevencion activo para llenar el combo
     */
    private void obtenerEstatusPrevencion() {
        LOGGER.trace("mx.mc.magedbean.FichaPrevencionMB.obtenerEstatusPrevencion()");
        try {
            this.estatusFichaPrevencionLista = new ArrayList<>();
            EstatusFichaPrevencion e = new EstatusFichaPrevencion();
            e.setActiva(Constantes.ACTIVOS);
            this.estatusFichaPrevencionLista.addAll(estatusFichaPrevencionService.obtenerLista(e));

        } catch (Exception e) {
            String err_ficprev_obtener_listaturno = "No se pudo obtener la lista Turnos ";
            LOGGER.error(err_ficprev_obtener_listaturno + " ", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, err_ficprev_obtener_listaturno, null);
        }
    }

    /**
     * Obtiene la lista de turno activo para llenar el combo
     */
    private void obtenerTurnos() {
        LOGGER.trace("mx.mc.magedbean.FichaPrevencionMB.obtenerTurnos()");
        try {
            this.turnoLista = new ArrayList<>();
            this.turnoLista.addAll(turnoService.obtenerLista(new Turno()));
        } catch (Exception e) {
            String err_ficprev_obtener_listaturno = "No se pudo obtener la lista Turnos ";
            LOGGER.error(err_ficprev_obtener_listaturno + " ", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, err_ficprev_obtener_listaturno, null);
        }
    }

    /**
     * Obtiene la lista de estructuras activo para llenar el combo
     */
    private void obtenerEstructura() {
        LOGGER.trace("mx.mc.magedbean.FichaPrevencionMB.obtenerEstructura()");
        try {
            this.estructuraLista = new ArrayList<>();
            Estructura e = new Estructura();
            e.setActiva(Constantes.ACTIVOS);
            this.estructuraLista.addAll(estructuraService.obtenerLista(e));
        } catch (Exception e) {
            String err_ficprev_obtener_listaEstructuras = "No se pudo obtener la lista Estructuras ";
            LOGGER.error(err_ficprev_obtener_listaEstructuras + " ", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, err_ficprev_obtener_listaEstructuras, null);
        }
    }

    /**
     * Método que nusca las coincidencias de usuario paara obtener el usuario
     * que registra
     *
     * @param cadena
     * @return
     */
    public List<Usuario> obtieneListaUsuarioRegistra(String cadena) {
        LOGGER.debug("mx.mc.magedbean.FichaPrevencionMB.obtieneListaUsuarioRegistra()");
        List<Usuario> usuarioLista = new ArrayList<>();
        try {
            usuarioLista.addAll(usuariosService.obtenerUsuarios(cadena));

        } catch (Exception ex) {
            String err_ficprev_usuarios_reg_busca = "Error al buscar los usuarios que reailzan la desinfección. ";
            LOGGER.error(err_ficprev_usuarios_reg_busca, ex);
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, err_ficprev_usuarios_reg_busca, null);
        }
        return usuarioLista;
    }

    /**
     * Inicializa objeto y propiedades para una nueva inserción. Este método se
     * ejecuta desde el boton new
     */
    public void preparaInsertar() {
        LOGGER.trace("mx.mc.magedbean.FichaPrevencionMB.preparaInsertar()");
        boolean status = Constantes.INACTIVO;
        if (!this.permiso.isPuedeCrear()) {
            String err_ficprev_guardar = "No tiene permiso de esta acción. ";
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, err_ficprev_guardar, null);
        } else {
            this.fichaPrevencionSeleccionado = new FichaPrevencionExtended();
            this.fichaPrevencionSeleccionado.setInsertFecha(fechaActual);
            this.fichaPrevencionSeleccionado.setNombreUsuarioRegistra(usuarioEnSesion.getNombre() + " " + usuarioEnSesion.getApellidoPaterno() + " " + usuarioEnSesion.getApellidoMaterno());
            this.fichaPrevencionSeleccionado.setIdEstatusPrevencion(EstatusFichaPrevencion_Enum.REGISTRADO.getValue());
            this.fichaPrevencionSeleccionado.setFolio("0");
            this.estatusFichaPrevencionLista = new ArrayList<>();
            EstatusFichaPrevencion efp = new EstatusFichaPrevencion();
            efp.setIdEstatusPrevencion(EstatusFichaPrevencion_Enum.REGISTRADO.getValue());
            efp.setDescripcion(EstatusFichaPrevencion_Enum.REGISTRADO.name());
            this.estatusFichaPrevencionLista.add(efp);
            this.tipoAccionCreaEdita = Accion_Enum.CREAR.getValue();
            this.adjuntoLista = new ArrayList<>();
            this.adjuntoSelecionado = new Adjunto();
            this.usuarioRegistraPreseleccionado = this.usuarioEnSesion;
            status = Constantes.ACTIVO;
        }
        PrimeFaces.current().ajax().addCallbackParam("estatus", status);
    }

    /**
     * Inicializa objeto y propiedades para una actualización de un registro
     * previamente almacenado. Este método se ejecuta por consulta de un
     * idPrevencion
     *
     * @param idPrevencion
     */
    public void preparaActualizar(String idPrevencion) {
        LOGGER.trace("mx.mc.magedbean.FichaPrevencionMB.preparaActualizar()");
        boolean status = Constantes.INACTIVO;
        if (!this.permiso.isPuedeEditar()) {
            String err_ficprev_editar = "No tiene permiso de esta acción. ";
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, err_ficprev_editar, null);

        } else if (idPrevencion == null || idPrevencion.trim().isEmpty()) {
            String err_ficprev_buscar_registro = "Registro seleccionado incorrecto. ";
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, err_ficprev_buscar_registro, null);

        } else {
            try {
                FichaPrevencionExtended fpe = new FichaPrevencionExtended();
                fpe.setIdPrevencion(idPrevencion);
                this.fichaPrevencionSeleccionado = fichaPrevencionService.obtenerFicha(fpe);
                if (this.fichaPrevencionSeleccionado == null) {
                    String err_ficprev_buscar_registro = "Registro seleccionado incorrecto. ";
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, err_ficprev_buscar_registro, null);

                } else {
                    this.tipoAccionCreaEdita = Accion_Enum.EDITAR.getValue();
                    this.estatusFichaPrevencionLista = new ArrayList<>();
                    EstatusFichaPrevencion efp = new EstatusFichaPrevencion();
                    efp.setIdEstatusPrevencion(this.fichaPrevencionSeleccionado.getIdEstatusPrevencion());
                    this.estatusFichaPrevencionLista.add(estatusFichaPrevencionService.obtener(efp));
                    obtieneAdjunstos(idPrevencion);

                    if (this.fichaPrevencionSeleccionado.getIdUsuarioRealizaLimpieza().equals("") || this.fichaPrevencionSeleccionado.getIdUsuarioRealizaLimpieza().isEmpty()) {
                        this.usuarioRegistraPreseleccionado = this.usuarioEnSesion;
                    }

                    status = Constantes.ACTIVO;

                }
            } catch (Exception e) {
                String err_ficprev_obtener_ficha = "No se pudo obtener el registro que desea actualizar ";
                LOGGER.error(err_ficprev_obtener_ficha + " ", e.getMessage());
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, err_ficprev_obtener_ficha, null);
            }
        }
        PrimeFaces.current().ajax().addCallbackParam("estatus", status);
    }

    /**
     * Obtiene los archivos adjuntos a la ficha de prevencion de contaminación
     *
     * @param idPrevencion
     */
    private void obtieneAdjunstos(String idPrevencion) {
        LOGGER.trace("mx.mc.magedbean.FichaPrevencionMB.obtieneAdjunstos()");
        try {
            this.adjuntoLista = new ArrayList<>();
            this.adjuntoLista.addAll(adjuntoService.obtenerAdjuntosByIdFichaPrevencion(idPrevencion));
        } catch (Exception e) {
            String err_ficprev_obtener_ficha_adjuntos = "No se pudo obtener los adjuntos de la registro ";
            LOGGER.error(err_ficprev_obtener_ficha_adjuntos + " ", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, err_ficprev_obtener_ficha_adjuntos, null);
        }
    }

    /**
     * Inicializa objeto y propiedades para una actualización de un registro
     * previamente almacenado. Este método se ejecuta a partir de un objeto
     * fichaPrevencionSeleccionado
     */
    public void preparaActualizarDblClick() {
        LOGGER.trace("mx.mc.magedbean.FichaPrevencionMB.preparaActualizarDblClick()");
        String idPrevencion = null;
        if (this.fichaPrevencionSeleccionado == null) {
            idPrevencion = this.fichaPrevencionSeleccionado.getIdPrevencion();
        }
        preparaActualizacion(idPrevencion);
    }

    /**
     * Valida el registro que se desea guardar
     *
     * @param fp
     * @return
     */
    private String validaRegistro(FichaPrevencion fp) {
        LOGGER.trace("mx.mc.magedbean.FichaPrevencionMB.validaRegistro()");
        String res = null;

        if (fp == null) {
            String err_ficprev_registroVacio = "Registro nulo. ";
            res = err_ficprev_registroVacio;

        } else if (fp.getIdEstructura() == null || fp.getIdEstructura().trim().isEmpty()) {
            String err_ficprev_areavacio = "El campo Area es requerido. ";
            res = err_ficprev_areavacio;

        } else if (fp.getIdTurno() == null || fp.getIdTurno() < 1) {
            String err_ficprev_turnoVacio = "El campo Turno es requerido. ";
            res = err_ficprev_turnoVacio;

        } else if (fp.getArea() == null || fp.getArea().trim().isEmpty()) {
            String err_ficprev_turnoVacio = "El campo Ubicación es requerido. ";
            res = err_ficprev_turnoVacio;

        } else if (fp.getSanitizante() == null || fp.getSanitizante().trim().isEmpty()) {
            String err_ficprev_sanitizantevacio = "El campo Sanitizante es requerido. ";
            res = err_ficprev_sanitizantevacio;

        } else if (fp.getDetergente() == null || fp.getDetergente().trim().isEmpty()) {
            String err_ficprev_detergentevacio = "El campo Detergente es requerido. ";
            res = err_ficprev_detergentevacio;

        } else if (fp.getComentariosRealizaLimpieza() == null || fp.getComentariosRealizaLimpieza().trim().isEmpty()) {
            String err_ficprev_detergentevacio = "El campo Comentario es requerido. ";
            res = err_ficprev_detergentevacio;

        }
        return res;

    }

    /**
     * Realiza validaciones mediante el llamado a la función que valida el
     * registro, si el registro es correcto llama función de insertar o
     * actualizar
     */
    public void validar() {
        LOGGER.trace("mx.mc.magedbean.FichaPrevencionMB.validar()");

        boolean status = Constantes.INACTIVO;
        String res = validaRegistro(fichaPrevencionSeleccionado);

        if (res != null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, res, null);

        } else {
            if (this.fichaPrevencionSeleccionado.getIdUsuarioRealizaLimpieza() == null || this.fichaPrevencionSeleccionado.getIdUsuarioRealizaLimpieza().equals("")) {
                this.fichaPrevencionSeleccionado.setIdUsuarioRealizaLimpieza(usuarioEnSesion.getIdUsuario());
            }

            if (this.tipoAccionCreaEdita.equals(Accion_Enum.CREAR.getValue())) {
                res = insertar(fichaPrevencionSeleccionado);

                if (res != null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, res, null);

                } else {
                    String ok_ficprev_guardado = "El registro se guardó correctamente";
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, ok_ficprev_guardado, null);
                    status = Constantes.ACTIVO;
                    obtenerFichasDePrevencion();
                }

            } else {
                res = actualizar(fichaPrevencionSeleccionado);
                if (res != null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, res, null);

                } else {
                    status = Constantes.ACTIVO;
                    String ok_ficprev_actualizado = "El registro se actualió correctamente";
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, ok_ficprev_actualizado, null);
                    obtenerFichasDePrevencion();
                }

            }
        }

        PrimeFaces.current().ajax().addCallbackParam("estatus", status);
    }

    /**
     * Inserta en la base de datos el registro deseado
     *
     * @param fp
     * @return
     */
    private String insertar(FichaPrevencion fp) {
        LOGGER.trace("mx.mc.magedbean.FichaPrevencionMB.insertar()");

        String res = null;
        if (this.usuarioEnSesion == null) {
            String err_ficprev_usuariosesion = "Sesión de usuario incorrecta. ";
            res = err_ficprev_usuariosesion;

        } else if (this.usuarioEnSesion.getIdUsuario() == null) {
            String err_ficprev_usuariosesion = "Sesión de usuario incorrecta. ";
            res = err_ficprev_usuariosesion;

        } else if (!this.permiso.isPuedeCrear()) {
            String err_ficprev_guardar = "No tiene permiso de esta acción. ";
            res = err_ficprev_guardar;

        } else {
            try {
                fp.setIdPrevencion(Comunes.getUUID());
                fp.setInsertIdUsuario(usuarioEnSesion.getIdUsuario());
                fp.setInsertFecha(new java.util.Date());
                boolean result = fichaPrevencionService.insertaFichaConAdjuntos(fp, adjuntoLista);

                if (!result) {
                    String err_ficprev_insertar = "Error al guardar. ";
                    res = err_ficprev_insertar;
                }

            } catch (Exception e) {
                String err_ficprev_insertar = "Error al guardar. " + e.getMessage();
                res = err_ficprev_insertar;
            }
        }
        return res;
    }

    /**
     * Método que actualiza en base de datos un registro
     *
     * @param fp
     * @return
     */
    private String actualizar(FichaPrevencion fp) {
        LOGGER.trace("mx.mc.magedbean.FichaPrevencionMB.actualizar()");

        String res = null;
        if (fp.getIdPrevencion() == null || fp.getIdPrevencion().trim().isEmpty()) {
            String err_ficprev_registroVacio = "Registro nulo. ";
            res = err_ficprev_registroVacio;

        } else if (this.usuarioEnSesion == null) {
            String err_ficprev_usuariosesion = "Sesión de usuario incorrecta. ";
            res = err_ficprev_usuariosesion;

        } else if (this.usuarioEnSesion.getIdUsuario() == null) {
            String err_ficprev_usuariosesion = "Sesión de usuario incorrecta. ";
            res = err_ficprev_usuariosesion;

        } else if (!this.permiso.isPuedeCrear()) {
            String err_ficprev_guardar = "No tiene permiso de esta acción. ";
            res = err_ficprev_guardar;

        } else {
            try {
                fp.setUpdateFecha(new java.util.Date());
                fp.setUpdateIdUsuario(usuarioEnSesion.getIdUsuario());

                if (adjuntoLista != null) {
                    for (AdjuntoExtended item : adjuntoLista) {
                        if (item.getInsertFecha() == null) {
                            item.setInsertFecha(new java.util.Date());
                            item.setInsertIdUsuario(usuarioEnSesion.getInsertIdUsuario());
                        }
                        item.setUpdateFecha(new java.util.Date());
                        item.setUpdateIdUsuario(usuarioEnSesion.getInsertIdUsuario());
                    }
                }

                boolean result = fichaPrevencionService.actualizaFichaConAdjuntos(fp, adjuntoLista);

                if (!result) {
                    String err_ficprev_actualizar = "Error al actualziar. ";
                    res = err_ficprev_actualizar;
                }

            } catch (Exception e) {
                String err_ficprev_insertar = "Error al actualizar. " + e.getMessage();
                res = err_ficprev_insertar;
            }
        }

        return res;

    }

    /**
     * Método para subir archivo adjunto
     *
     * @param event
     */
    public void subirAdjunto(FileUploadEvent event) {
        LOGGER.trace("mx.mc.magedbean.FichaPrevencionMB.subirAdjunto()");
        try {
            if (this.adjuntoLista == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("dcto.err.adjuntos"), null);

            } else if (this.adjuntoLista.size() == 20) {
                String err_ficprev_no_adjuntos = "Sólo se permiten adjuntar 20 archivos. ";
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, err_ficprev_no_adjuntos, null);

            } else {
                this.file = event.getFile();
                String name = this.file.getFileName();
                if (name.length() > 100) {
                    String err_ficprev_nombre_adjuntos = "El nombre del adjunto no puede superar 100 carcateres. ";
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, err_ficprev_nombre_adjuntos, null);
                }

                AdjuntoExtended adjunto = new AdjuntoExtended();
                adjunto.setIdAdjunto(0);
                adjunto.setNombreAdjunto(name);
                adjunto.setInsertFecha(new Date());
                adjunto.setInsertIdUsuario(usuarioEnSesion.getIdUsuario());
                adjunto.setAdjunto(IOUtils.toByteArray(this.file.getInputstream()));
                adjunto.setNombreRegistro(this.usuarioEnSesion.getIdUsuario());
                adjunto.setUpdateFecha(new java.util.Date());
                adjunto.setUpdateIdUsuario(usuarioEnSesion.getInsertIdUsuario());
                adjunto.setEliminado(0);
                this.adjuntoLista.add(adjunto);
            }
        } catch (IOException ex) {
            LOGGER.error("Error al adjuntar archivos  " + ex.getMessage());
        }
    }

    /**
     * Método para descargar adjunto
     *
     * @param idAdjunto
     */
    public void descargarArchivo(Integer idAdjunto) {
        LOGGER.trace("mx.mc.magedbean.FichaPrevencionMB.descargarArchivo()");
        try {
            if (this.adjuntoLista != null) {
                for (AdjuntoExtended item : adjuntoLista) {
                    if (Objects.equals(idAdjunto, item.getIdAdjunto())) {
                        Adjunto a = adjuntoService.obtenerAdjuntoByIdAdjunto(idAdjunto);
                        byte[] buffer = a.getAdjunto();
                        InputStream stream = new ByteArrayInputStream(buffer);
                        String tipoExtension[] = item.getNombreAdjunto().split("\\.");
                        String contentType = "application/" + tipoExtension[1];
                        this.adjuntoDescarga = (new DefaultStreamedContent(stream, contentType, a.getNombreAdjunto()));
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al intentar descargar el archivo   " + ex.getMessage());
        }
    }

    /**
     * Descarga el adjunto de 1 documento
     *
     * @param idAdjunto
     */
    public void descargarArchivoAdjunto(String idAdjunto) {
        LOGGER.trace("mx.mc.magedbean.ExpedienteCentralMezclasMB.descargarArchivoAdjunto()");
        adjuntoDescarga = null;
        if (idAdjunto != null && !idAdjunto.isEmpty()) {
            try {
                if (adjuntoLista != null) {
                    if (!adjuntoLista.isEmpty()) {
                        for (AdjuntoExtended item : adjuntoLista) {
                            byte[] buffer = item.getAdjunto();
                            InputStream stream = new ByteArrayInputStream(buffer);
                            String tipoExtension[] = item.getNombreAdjunto().split("\\.");
                            String contentType = "application/" + tipoExtension[1];
                            this.adjuntoDescarga = (new DefaultStreamedContent(stream, contentType, item.getNombreAdjunto()));
                        }
                    }
                }
            } catch (Exception e) {
                LOGGER.error("Error al obtener el documento {} ", e.getMessage());
            }

        }
    }

    /**
     * Método para eliminar un adjunto
     *
     * @param idAdjunto
     * @param nombreadjunto
     */
    public void eliminarAdjunto(Integer idAdjunto, String nombreadjunto) {
        LOGGER.trace("mx.mc.magedbean.FichaPrevencionMB.eliminarAdjunto()");
        boolean status = Constantes.ACTIVO;
        if (this.adjuntoLista != null) {
            for (AdjuntoExtended unAdjunto : new ArrayList<>(adjuntoLista)) {
                if (idAdjunto == 0 && unAdjunto.getNombreAdjunto().equals(nombreadjunto)) {
                    this.adjuntoLista.remove(unAdjunto);
                    status = Constantes.ACTIVO;
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("hipersensibilidad.info.eliminaAdjunto"), null);

                } else if (unAdjunto.getIdAdjunto().equals(idAdjunto)) {
                    try {
                        status = adjuntoService.eliminarArchivo(idAdjunto);
                        this.adjuntoLista.remove(unAdjunto);
                        status = Constantes.ACTIVO;
                        Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("hipersensibilidad.info.eliminaAdjunto"), null);

                    } catch (Exception ex) {
                        String err_ficprev_adjunto_eliminar = "Error al intentar eliminar el archivo adjunto. ";
                        LOGGER.error(err_ficprev_adjunto_eliminar, ex.getMessage());
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, err_ficprev_adjunto_eliminar, null);
                    }
                    break;
                }
            }
        }
        PrimeFaces.current().ajax().addCallbackParam("estatus", status);
    }

    public void preparaActualizacion(String idFicha) {

    }

    /**
     * Método para preparar el formulario para aprobar un registro
     *
     * @param idPrevencion
     */
    public void preparaAprobar(String idPrevencion) {
        LOGGER.trace("mx.mc.magedbean.FichaPrevencionMB.preparaAprobar()");
        boolean status = false;
        if (idPrevencion == null || idPrevencion.equals("")) {
            String err_ficprev_no_registro_incorrecto = "No se encontró el registro. ";
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, err_ficprev_no_registro_incorrecto, null);

        } else if (this.usuarioEnSesion == null) {
            String err_ficprev_usuariosesion = "Sesión de usuario incorrecta. ";
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, err_ficprev_usuariosesion, null);

        } else if (this.usuarioEnSesion.getIdUsuario() == null) {
            String err_ficprev_usuariosesion = "Sesión de usuario incorrecta. ";
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, err_ficprev_usuariosesion, null);

        } else if (!this.permiso.isPuedeAutorizar()) {
            String err_ficprev_guardar = "No tiene permiso de esta acción. ";
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, err_ficprev_guardar, null);

        } else {

            try {
                FichaPrevencionExtended fpe = new FichaPrevencionExtended();
                fpe.setIdPrevencion(idPrevencion);
                this.fichaPrevencionSeleccionado = fichaPrevencionService.obtenerFicha(fpe);

                if (this.fichaPrevencionSeleccionado == null) {
                    String err_ficprev_no_registro_incorrecto = "No se encontró el registro. ";
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, err_ficprev_no_registro_incorrecto, null);

                } else if (!Objects.equals(this.fichaPrevencionSeleccionado.getIdEstatusPrevencion(), EstatusFichaPrevencion_Enum.REGISTRADO.getValue())
                        && !Objects.equals(this.fichaPrevencionSeleccionado.getIdEstatusPrevencion(), EstatusFichaPrevencion_Enum.REVISADO.getValue())) {
                    String err_ficprev_no_registro_incorrecto = "El estatus de la ficha no permite la aprobación. ";
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, err_ficprev_no_registro_incorrecto, null);

                } else {

                    this.estatusFichaPrevencionLista = new ArrayList<>();

                    EstatusFichaPrevencion epl = new EstatusFichaPrevencion();
                    epl.setIdEstatusPrevencion(EstatusFichaPrevencion_Enum.NO_CONFORME.getValue());
                    epl.setDescripcion(EstatusFichaPrevencion_Enum.NO_CONFORME.name());
                    this.estatusFichaPrevencionLista.add(epl);
                    epl = new EstatusFichaPrevencion();
                    epl.setIdEstatusPrevencion(EstatusFichaPrevencion_Enum.APROBADO.getValue());
                    epl.setDescripcion(EstatusFichaPrevencion_Enum.APROBADO.name());
                    this.estatusFichaPrevencionLista.add(epl);

                    this.fichaPrevencionSeleccionado.setFechaAprueba(new java.util.Date());
                    this.fichaPrevencionSeleccionado.setIdEstatusPrevencion(EstatusFichaPrevencion_Enum.APROBADO.getValue());
                    this.fichaPrevencionSeleccionado.setIdUsuarioAprueba(usuarioEnSesion.getIdUsuario());
                    String sb = usuarioEnSesion.getNombre() + StringUtils.SPACE + usuarioEnSesion.getApellidoPaterno() + StringUtils.SPACE + usuarioEnSesion.getApellidoMaterno();

                    this.fichaPrevencionSeleccionado.setNombreUsuarioAprueba(sb);
                    status = true;

                }

            } catch (Exception e) {
                String err_ficprev_consultaaprobacionok = "Error al consultar el registro para Aprobación. ";
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, err_ficprev_consultaaprobacionok, null);
                LOGGER.error(err_ficprev_consultaaprobacionok, e.getMessage());
            }
        }
        PrimeFaces.current().ajax().addCallbackParam("estatus", status);
    }

    /**
     * Método para registrar la aprobación de una ficha de prevención
     *
     * @param idPrevencion
     */
    public void aprobar(String idPrevencion) {
        LOGGER.trace("mx.mc.magedbean.FichaPrevencionMB.aprobar()");

        boolean status = false;
        if (idPrevencion == null || idPrevencion.equals("")) {
            String err_ficprev_no_registro_incorrecto = "No se encontró el registro. ";
            Mensaje.showMessage(Constantes.MENSAJE_INFO, err_ficprev_no_registro_incorrecto, null);

        } else if (!this.permiso.isPuedeAutorizar()) {
            String err_ficprev_guardar = "No tiene permiso de esta acción. ";
            Mensaje.showMessage(Constantes.MENSAJE_INFO, err_ficprev_guardar, null);

        } else {
            try {
                FichaPrevencion fp = new FichaPrevencion();
                fp.setIdPrevencion(this.fichaPrevencionSeleccionado.getIdPrevencion());
                fp.setIdEstatusPrevencion(EstatusFichaPrevencion_Enum.APROBADO.getValue());
                fp.setUpdateFecha(new java.util.Date());
                fp.setIdUsuarioAprueba(usuarioEnSesion.getIdUsuario());
                fp.setFechaAprueba(new java.util.Date());
                fp.setUpdateIdUsuario(usuarioEnSesion.getIdUsuario());

                status = fichaPrevencionService.actualizar(fp);

                if (!status) {
                    String err_ficprev_aprobacion = "Error al realizar la Aprobarción. ";
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, err_ficprev_aprobacion, null);

                } else {
                    String err_ficprev_aprobacionok = "Aprobación registrada con éxito. ";
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, err_ficprev_aprobacionok, null);

                }

            } catch (Exception e) {
                String err_ficprev_aprobacionok = "Error al registrar la Aprobación. ";
                Mensaje.showMessage(Constantes.MENSAJE_INFO, err_ficprev_aprobacionok, null);
                LOGGER.error(err_ficprev_aprobacionok, e.getMessage());

            }

        }

        PrimeFaces.current().ajax().addCallbackParam("estatus", status);

    }

    /**
     * Método que genera la impresión de la ficha de prevención de contaminación
     *
     * @param idFichaPrevencion
     */
    public void imprimir(String idFichaPrevencion) {
        LOGGER.trace("mx.mc.magedbean.FichaPrevencionMB.imprimir()");
        boolean estatus = Constantes.INACTIVO;

        if (idFichaPrevencion == null || idFichaPrevencion.trim().isEmpty()) {

        } else {
            try {
                FichaPrevencionExtended fpeTemp = new FichaPrevencionExtended();
                fpeTemp.setIdPrevencion(idFichaPrevencion);
                this.fichaPrevencionSeleccionado = fichaPrevencionService.obtenerFicha(fpeTemp);

                if (this.fichaPrevencionSeleccionado == null) {

                } else {
                    EntidadHospitalaria entidad = entidadHospitalariaService.obtenerEntidadHospByIdUsuaurio(usuarioEnSesion.getIdUsuario());
                    byte[] reporteEnBytes = reportesService.reporteFichaPrevencion(this.fichaPrevencionSeleccionado, entidad);

                    if (reporteEnBytes == null) {

                    } else {
                        estatus = Constantes.ACTIVO;
                        SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "sesionMB");
                        sesion.setReportStream(reporteEnBytes);
                        String sb = "FichaPrevCont-" + this.fichaPrevencionSeleccionado.getFolio() + "-" + FechaUtil.formatoFecha(new Date(), "yyyyMMdd") + ".pdf";
                        String nombreArchivo = sb;
                        sesion.setReportName(nombreArchivo);
                    }
                }
            } catch (Exception ex) {
                LOGGER.error("Ocurrió un error al generar la impresión de la Ficha de PRevención. {} ", ex);
            }
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, estatus);
    }
    
    
    /**
     * Método para eliminar una ficha
     * @param idFichaPrevencion 
     */
    public void eliminarFichaPrevencion(String idFichaPrevencion){
        LOGGER.trace("mx.mc.magedbean.FichaPrevencionMB.eliminarFichaPrevencion()");
        boolean estatus = Constantes.INACTIVO;

        if (idFichaPrevencion == null || idFichaPrevencion.trim().isEmpty()) {

        } else if (this.usuarioEnSesion == null){
                    
        } else if (!this.permiso.isPuedeEliminar()){
        
        } else {
            try {
                FichaPrevencionExtended fpeTemp = new FichaPrevencionExtended();
                fpeTemp.setIdPrevencion(idFichaPrevencion);
                this.fichaPrevencionSeleccionado = fichaPrevencionService.obtenerFicha(fpeTemp);

                if (this.fichaPrevencionSeleccionado == null) {

                } else if (this.fichaPrevencionSeleccionado.getIdEstatusPrevencion() != EstatusFichaPrevencion_Enum.REGISTRADO.getValue()){
                    
                } else {
                    FichaPrevencion fp = new FichaPrevencion();
                    fp.setIdPrevencion(idFichaPrevencion);
                    fp.setIdEstatusPrevencion(EstatusFichaPrevencion_Enum.CANCELADO.getValue());
                    fp.setUpdateFecha(new java.util.Date());
                    fp.setUpdateIdUsuario(this.usuarioEnSesion.getIdUsuario());
                    
                    boolean result = fichaPrevencionService.actualizaFichaConAdjuntos(fp, adjuntoLista);
                    String ok_ficprev_elimina_ficha= "Registro de Ficha Eliminado. ";
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, ok_ficprev_elimina_ficha, null);
                    obtenerFichasDePrevencion();
                    
                }
            } catch (Exception ex) {
                String err_ficprev_elimina_ficha= "Error al eliminar la Ficha de Prevención. ";
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, err_ficprev_elimina_ficha, null);
                LOGGER.error(err_ficprev_elimina_ficha + "{}", ex);
            }
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, estatus);
    }
    
    

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
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

    public List<FichaPrevencionExtended> getFichaPrevencionLista() {
        return fichaPrevencionLista;
    }

    public void setFichaPrevencionLista(List<FichaPrevencionExtended> fichaPrevencionLista) {
        this.fichaPrevencionLista = fichaPrevencionLista;
    }

    public FichaPrevencionExtended getFichaPrevencionSeleccionado() {
        return fichaPrevencionSeleccionado;
    }

    public void setFichaPrevencionSeleccionado(FichaPrevencionExtended fichaPrevencionSeleccionado) {
        this.fichaPrevencionSeleccionado = fichaPrevencionSeleccionado;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public List<AdjuntoExtended> getAdjuntoLista() {
        return adjuntoLista;
    }

    public void setAdjuntoLista(List<AdjuntoExtended> adjuntoLista) {
        this.adjuntoLista = adjuntoLista;
    }

    public Adjunto getAdjuntoSelecionado() {
        return adjuntoSelecionado;
    }

    public void setAdjuntoSelecionado(Adjunto adjuntoSelecionado) {
        this.adjuntoSelecionado = adjuntoSelecionado;
    }

    public DefaultStreamedContent getAdjuntoDescarga() {
        return adjuntoDescarga;
    }

    public void setAdjuntoDescarga(DefaultStreamedContent adjuntoDescarga) {
        this.adjuntoDescarga = adjuntoDescarga;
    }

    public List<Turno> getTurnoLista() {
        return turnoLista;
    }

    public void setTurnoLista(List<Turno> turnoLista) {
        this.turnoLista = turnoLista;
    }

    public Turno getTurnoSeleccionado() {
        return turnoSeleccionado;
    }

    public void setTurnoSeleccionado(Turno turnoSeleccionado) {
        this.turnoSeleccionado = turnoSeleccionado;
    }

    public List<Estructura> getEstructuraLista() {
        return estructuraLista;
    }

    public void setEstructuraLista(List<Estructura> estructuraLista) {
        this.estructuraLista = estructuraLista;
    }

    public Estructura getEstructuraSeleccionado() {
        return estructuraSeleccionado;
    }

    public void setEstructuraSeleccionado(Estructura estructuraSeleccionado) {
        this.estructuraSeleccionado = estructuraSeleccionado;
    }

    public List<EstatusFichaPrevencion> getEstatusFichaPrevencionLista() {
        return estatusFichaPrevencionLista;
    }

    public void setEstatusFichaPrevencionLista(List<EstatusFichaPrevencion> estatusFichaPrevencionLista) {
        this.estatusFichaPrevencionLista = estatusFichaPrevencionLista;
    }

    public EstatusFichaPrevencion getEstatusFichaPrevencionSeleccionado() {
        return estatusFichaPrevencionSeleccionado;
    }

    public void setEstatusFichaPrevencionSeleccionado(EstatusFichaPrevencion estatusFichaPrevencionSeleccionado) {
        this.estatusFichaPrevencionSeleccionado = estatusFichaPrevencionSeleccionado;
    }

    public Usuario getUsuarioRegistraPreseleccionado() {
        return usuarioRegistraPreseleccionado;
    }

    public void setUsuarioRegistraPreseleccionado(Usuario usuarioRegistraPreseleccionado) {
        this.usuarioRegistraPreseleccionado = usuarioRegistraPreseleccionado;
    }

}
