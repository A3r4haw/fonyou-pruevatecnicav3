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
import mx.mc.enums.Accion_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.lazy.DocumentoLazy;
import mx.mc.model.Adjunto;
import mx.mc.model.AdjuntoExtended;
import mx.mc.model.Documento;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.TipoArchivo;
import mx.mc.model.TransaccionPermisos;
import mx.mc.model.Usuario;
import mx.mc.service.DocumentoService;
import mx.mc.service.TipoArchivoService;
import mx.mc.util.Comunes;
import mx.mc.util.Mensaje;
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
 * @author hramirez
 */
@Controller
@Scope(value = "view")
public class ExpedienteCentralMezclasMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(ExpedienteCentralMezclasMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    private String cadenaBusqueda;
    private Date fechaActual;
    private Usuario usuarioEnSesion;
    private PermisoUsuario permiso;
    private String tipoAccionCreaEdita;

    private transient List<TransaccionPermisos> permisosLista;
    private transient List<Documento> documentoLista;
    private transient UploadedFile file;
    private transient List<AdjuntoExtended> adjuntoLista;
    private transient Adjunto adjuntoSelecionado;
    private transient DefaultStreamedContent adjuntoDescarga;

    private Documento documentoSeleccionado;
    private DocumentoLazy documentoLazy;
    private String nombreRegistra;
    private transient List<TipoArchivo> tipoArchivoLista;

    @Autowired
    private transient DocumentoService documentoService;

    @Autowired
    private transient TipoArchivoService tipoArchivoService;

    /**
     * Metodo inicializador
     */
    @PostConstruct
    public void init() {
        LOGGER.trace("mx.mc.magedbean.ExpedienteCentralMezclasMB.init()");
        limpia();
        this.permiso = Comunes.obtenerPermisos(Transaccion_Enum.EXPEDIENTECENTRALMEZCLAS.getSufijo());
        obtenerTipoArchivoLista();
        obtenerListaDocumentoActivo();
    }

    /**
     * Limpia las variables
     */
    private void limpia() {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.limpia()");
        this.cadenaBusqueda = null;
        this.fechaActual = new java.util.Date();
        this.usuarioEnSesion = new Usuario();
        this.permisosLista = new ArrayList<>();
        this.usuarioEnSesion.setPermisosList(this.permisosLista);
        this.documentoLista = new ArrayList<>();
        this.documentoSeleccionado = new Documento();
        inicializaUploadFile();
    }

    /**
     * Inicializa la propiedad file
     */
    private void inicializaUploadFile() {
        this.file = new UploadedFile() {
            @Override
            public String getFileName() {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public List<String> getFileNames() {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public InputStream getInputstream() throws IOException {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public long getSize() {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public byte[] getContents() {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public String getContentType() {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void write(String filePath) throws Exception {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        };
    }

    /**
     * Busca los documentos almacenados de forma
     */
    public void obtenerListaDocumentoActivo() {
        LOGGER.trace("mx.mc.magedbean.ExpedienteCentralMezclasMB.obtenerListaDocumentoActivo()");
        try {
            this.documentoLista = new ArrayList<>();
            ParamBusquedaReporte paramBusquedaReporte = new ParamBusquedaReporte();     //            this.documentoLista.addAll(documentoService.obtenerListaDocumentoActivo(this.cadenaBusqueda));
            if (this.cadenaBusqueda != null && this.cadenaBusqueda.trim().isEmpty()) {
                this.cadenaBusqueda = null;
            }
            paramBusquedaReporte.setNuevaBusqueda(true);
            paramBusquedaReporte.setCadenaBusqueda(this.cadenaBusqueda);
            this.documentoLazy = new DocumentoLazy(documentoService, paramBusquedaReporte);

        } catch (Exception e) {
            LOGGER.error("Error al obtener Documentos del expediente de la central de mezclas {} ", e.getMessage());
        }
    }

    /**
     * Realiza validaciones necesarias por documento
     *
     * @param idDocumento
     * @return
     */
    private String validaRegistro(String idDocumento, String accion) {
        LOGGER.trace("mx.mc.magedbean.ExpedienteCentralMezclasMB.validaRegistro()");
        String msj = "";
        if (this.usuarioEnSesion == null) {
            msj = RESOURCES.getString("dcto.err.usuario.sinsesion");

        } else if (accion.equals(Accion_Enum.VER.getValue()) && !this.permiso.isPuedeVer()) {
            msj = RESOURCES.getString("dcto.err.ver");

        } else if (accion.equals(Accion_Enum.CREAR.getValue()) && !this.permiso.isPuedeCrear()) {
            msj = RESOURCES.getString("dcto.err.crea");

        } else if (accion.equals(Accion_Enum.EDITAR.getValue()) && !this.permiso.isPuedeEditar()) {
            msj = RESOURCES.getString("dcto.err.edita");

        } else if (accion.equals(Accion_Enum.ELIMINAR.getValue()) && !this.permiso.isPuedeEliminar()) {
            msj = RESOURCES.getString("dcto.err.cancela");

        } else if (accion.equals(Accion_Enum.AUTORIZAR.getValue()) && !this.permiso.isPuedeAutorizar()) {
            msj = RESOURCES.getString("dcto.err.autioriza");

        } else if (idDocumento.trim().equals("")) {
            msj = RESOURCES.getString("dcto.err.incorrecto");

        }
        return msj;
    }

    /**
     * Valida los campos mandatorios del formulario al registrar o actualizar
     *
     * @param d
     * @return res
     */
    private String validaFormulariodocumento(Documento d, String accion) {
        LOGGER.trace("mx.mc.magedbean.ExpedienteCentralMezclasMB.validaFormulariodocumento()");
        String msj = "";

        if (accion.equals(Accion_Enum.CREAR.getValue()) || accion.equals(Accion_Enum.EDITAR.getValue())) {
            if (d.getNombre().trim().equals("")) {
                msj = RESOURCES.getString("dcto.err.nombre");

            } else if (d.getDescripcion().trim().equals("")) {
                msj = RESOURCES.getString("dcto.err.descripcion");

            } else if (d.getIdTipoArchivo() == null) {
                msj = RESOURCES.getString("dcto.err.idTipoArchivo");

            } else if (d.getArchivo() == null) {
                msj = RESOURCES.getString("dcto.err.archivo");

            }
        }
        return msj;
    }

    /**
     * Obtiene lista de tipos de archivos
     */
    private void obtenerTipoArchivoLista() {
        LOGGER.trace("mx.mc.magedbean.ExpedienteCentralMezclasMB.obtenerTipoArchivoLista()");
        try {
            this.tipoArchivoLista = new ArrayList<>();
            try {
                this.tipoArchivoLista.addAll(tipoArchivoService.obtenerListaActivos());
            } catch (Exception ex) {
                LOGGER.error("Error al obtener lista de tipos de archivos {} ", ex.getMessage());
            }
        } catch (Exception ex) {

        }
    }

    /**
     * Prepara datos para creación de registros
     *
     */
    public void preparaNuevoRegistro() {
        LOGGER.trace("mx.mc.magedbean.ExpedienteCentralMezclasMB.preparaNuevoRegistro()");
        boolean status;
        this.tipoAccionCreaEdita = "";
        this.documentoSeleccionado = new Documento();
        this.documentoSeleccionado.setIdDocumento(Comunes.getUUID());
        adjuntoLista = new ArrayList<>();
        obtenerTipoArchivoLista();
        String msjValidacion = validaRegistro(documentoSeleccionado.getIdDocumento(), Accion_Enum.CREAR.getValue());
        if (!msjValidacion.equals("")) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, msjValidacion, null);

        } else {
            this.tipoAccionCreaEdita = Accion_Enum.CREAR.getValue();
            status = Constantes.ACTIVO;
            PrimeFaces.current().ajax().addCallbackParam("estatus", status);
        }
    }

    /**
     * Prepara datos para actualización de registros
     */
    public void preparaActualizacionRegistro() {
        LOGGER.trace("mx.mc.magedbean.ExpedienteCentralMezclasMB.preparaActualizacionRegistro()");
        if (documentoSeleccionado == null){
            preparaActualizacion(documentoSeleccionado.getIdDocumento());
        }
    }
    
    
    /**
     * Método que ontiene un documento por su id
     * @param idDocumento 
     */
    public void preparaActualizacion(String idDocumento) {
        LOGGER.trace("mx.mc.magedbean.ExpedienteCentralMezclasMB.preparaActualizacionRegistro()");
        boolean status = Constantes.INACTIVO;
        this.tipoAccionCreaEdita = "";
        adjuntoLista = new ArrayList<>();
        String msjValidacion = validaRegistro(idDocumento, Accion_Enum.EDITAR.getValue());
        if (!msjValidacion.equals("")) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, msjValidacion, null);

        } else {
            try {
                Documento d = new Documento(idDocumento);
                this.documentoSeleccionado = documentoService.obtener(d);
                if (this.documentoSeleccionado != null) {
                    AdjuntoExtended a = new AdjuntoExtended();
                    a.setIdAdjunto(1);
                    a.setAdjunto(this.documentoSeleccionado.getArchivo());
                    a.setNombreAdjunto(this.documentoSeleccionado.getNombreArchivo());
                    a.setInsertFecha(this.documentoSeleccionado.getFecha());
                    adjuntoLista.add(a);
                    this.tipoAccionCreaEdita = Accion_Enum.EDITAR.getValue();
                    status = Constantes.ACTIVO;
                }
            } catch (Exception e){
                LOGGER.error("Error al obtener el documento {} " , e.getMessage());
            }
            PrimeFaces.current().ajax().addCallbackParam("estatus", status);
        }
    }

    /**
     * Guarda los cambios en un documento nuevo o actualización de uno existene
     */
    public void guardarRegistroDocumento() {
        LOGGER.trace("mx.mc.magedbean.ExpedienteCentralMezclasMB.guardarRegistro()");
        boolean estatus = false;

        String msjValidacion = validaFormulariodocumento(this.documentoSeleccionado, tipoAccionCreaEdita);
        if (!msjValidacion.equals("")) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, msjValidacion, null);

        } else {

            if (tipoAccionCreaEdita.equals(Accion_Enum.CREAR.getValue())) {
                msjValidacion = insertaDocumento(documentoSeleccionado);

            } else if (tipoAccionCreaEdita.equals(Accion_Enum.EDITAR.getValue())) {
                msjValidacion = actualizaDocumento(documentoSeleccionado);

            } else {
                msjValidacion = RESOURCES.getString("dcto.err.incorrecto");

            }
            if (!msjValidacion.equals("")) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("dcto.err.guardar"), null);

            } else {
                estatus = true;
                Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("dcto.ok.guardado"), null);
            }
        }

        PrimeFaces.current().ajax().addCallbackParam("estatus", estatus);
    }

    /**
     * Cancela la edicion o creación de un registro
     */
    public void cancelarRegistro() {
        LOGGER.trace("mx.mc.magedbean.ExpedienteCentralMezclasMB.cancelarRegistro()");
        this.documentoSeleccionado = new Documento();
        this.tipoAccionCreaEdita = "";
        boolean estatus = Constantes.ACTIVO;
        PrimeFaces.current().ajax().addCallbackParam("estatus", estatus);
    }

    /**
     * Inserta un nuedo registro de documento en base de datos
     *
     * @param d
     * @return
     */
    private String insertaDocumento(Documento d) {
        LOGGER.trace("mx.mc.magedbean.ExpedienteCentralMezclasMB.insertaDocumento()");
        String msj = "";
        boolean res;
        try {
            d.setActivo(true);
            d.setInsertIdUsuario(this.usuarioEnSesion.getIdUsuario());
            d.setInsertFecha(new java.util.Date());
            d.setUpdateIdUsuario(this.usuarioEnSesion.getIdUsuario());
            d.setUpdateFecha(new java.util.Date());
            res = documentoService.insertar(d);
            if (!res) {
                msj = "Error al registrar el documento";
            }
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString("dcto.err.incorrecto") + " {} ", ex.getMessage());
            msj = RESOURCES.getString("dcto.err.incorrecto");
        }
        return msj;
    }

    /**
     * Actualiza un nuedo registro de documento en base de datos
     *
     * @param d
     * @return
     */
    private String actualizaDocumento(Documento d) {
        LOGGER.trace("mx.mc.magedbean.ExpedienteCentralMezclasMB.actualizaDocumento()");
        String msj = "";
        boolean res;
        try {
            res = documentoService.actualizar(d);
            if (!res) {
                msj = "Error al actualizar el documento";
            }
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString("dcto.err.incorrecto") + " {} ", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("dcto.err.incorrecto"), null);
        }
        return msj;
    }

    /**
     * Método para subir archivo adjunto
     *
     * @param event
     */
    public void subirAdjunto(FileUploadEvent event) {
        LOGGER.trace("mx.mc.magedbean.ExpedienteCentralMezclasMB.subirAdjunto()");
        try {
            if (this.adjuntoLista == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("dcto.err.adjuntos"), null);

            } else if (!this.adjuntoLista.isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("dcto.err.noadjuntos"), null);

            } else {
                this.file = event.getFile();
                String name = this.file.getFileName();
                AdjuntoExtended adjunto = new AdjuntoExtended();
                adjunto.setIdAdjunto(this.adjuntoLista.size() + 1);
                adjunto.setNombreAdjunto(name);
                adjunto.setInsertFecha(new Date());
                adjunto.setAdjunto(IOUtils.toByteArray(this.file.getInputstream()));
                adjunto.setNombreRegistro(this.usuarioEnSesion.getIdUsuario());
                documentoSeleccionado.setArchivo(adjunto.getAdjunto());
                documentoSeleccionado.setNombreArchivo(name);
                documentoSeleccionado.setFecha(new java.util.Date());
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
        LOGGER.trace("mx.mc.magedbean.ExpedienteCentralMezclasMB.descargarArchivo()");
        try {
            if (this.adjuntoLista != null) {
                for (AdjuntoExtended item : adjuntoLista) {
                    if (Objects.equals(idAdjunto, item.getIdAdjunto())) {
                        byte[] buffer = item.getAdjunto();
                        InputStream stream = new ByteArrayInputStream(buffer);
//                        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                        String tipoExtension[] = item.getNombreAdjunto().split("\\.");
                        String contentType = "application/" + tipoExtension[1];
                        this.adjuntoDescarga = (new DefaultStreamedContent(stream, contentType, item.getNombreAdjunto()));
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al intentar descargar el archivo   " + ex.getMessage());
        }
    }
    
    /**
     * Descarga el adjunto de 1 documento
     * @param idAdjunto 
     */
    public void descargarArchivoAdjunto(String idDocumento) {
        LOGGER.trace("mx.mc.magedbean.ExpedienteCentralMezclasMB.descargarArchivoAdjunto()");
        adjuntoDescarga = null;
        if (idDocumento != null && !idDocumento.isEmpty()) {
            try {
                Documento d = new Documento(idDocumento);
                d = documentoService.obtener(d);
                if (d != null) {
                    if (d.getArchivo() != null) {
                        AdjuntoExtended a = new AdjuntoExtended();
                        a.setIdAdjunto(1);
                        a.setAdjunto(d.getArchivo());
                        a.setNombreAdjunto(d.getNombreArchivo());
                        a.setInsertFecha(d.getFecha());
                        
                        byte[] buffer = a.getAdjunto();
                        InputStream stream = new ByteArrayInputStream(buffer);
                        String tipoExtension[] = a.getNombreAdjunto().split("\\.");
                        String contentType = "application/" + tipoExtension[1];
                        this.adjuntoDescarga = (new DefaultStreamedContent(stream, contentType, a.getNombreAdjunto()));
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
     */
    public void eliminarAdjunto(Integer idAdjunto) {
        LOGGER.trace("mx.mc.magedbean.ExpedienteCentralMezclasMB.eliminarAdjunto()");
        try {
            if (this.adjuntoLista != null) {
                for (AdjuntoExtended unAdjunto : adjuntoLista) {
                    if (unAdjunto.getIdAdjunto().equals(idAdjunto)) {
                        this.adjuntoLista.remove(unAdjunto);
                        Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("hipersensibilidad.info.eliminaAdjunto"), null);
                        break;
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al intentar eliminar el archivo adjunto   " + ex.getMessage());
        }
    }

    /**
     * Permite Inactivar Documentos
     *
     * @param idDocumento
     * @param autorizado
     */
    public void cambiarAutorización(String idDocumento, boolean autorizado) {
        LOGGER.trace("mx.mc.magedbean.ExpedienteCentralMezclasMB.statusUsuario()");
        boolean estatus = Constantes.ACTIVO;
        try {
            if (this.permiso.isPuedeAutorizar()) {
                Documento d = new Documento(idDocumento);
                d = documentoService.obtener(d);
                d.setUpdateFecha(new java.util.Date());
                d.setUpdateIdUsuario(usuarioEnSesion.getIdUsuario());
                if (autorizado) {
                    d.setAutorizado(Constantes.INACTIVO);
                } else {
                    d.setAutorizado(Constantes.ACTIVO);
                    d.setFechaAutorizado(new java.util.Date());
                    d.setIdUsuarioAutoriza(usuarioEnSesion.getIdUsuario());
                }
                estatus = documentoService.actualizar(d);
            }
        } catch (Exception ex) {
            LOGGER.error("Error al actualizar status de autorización documento: {}", ex.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam("estatus", estatus);
    }
    
    /**
     * Inactivar un registro de documento
     * @param idDocumento
     * @param inactivo 
     */
    public void inactivarRegistro(String idDocumento, boolean inactivo) {
        LOGGER.trace("mx.mc.magedbean.ExpedienteCentralMezclasMB.inactivarRegistro()");
        boolean estatus = Constantes.ACTIVO;
        try {
            if (this.permiso.isPuedeEliminar()) {
                Documento d = new Documento(idDocumento);
                d = documentoService.obtener(d);
                d.setUpdateFecha(new java.util.Date());
                d.setUpdateIdUsuario(usuarioEnSesion.getIdUsuario());
                d.setActivo(Constantes.INACTIVO);
                estatus = documentoService.actualizar(d);
            }
        } catch (Exception ex) {
            LOGGER.error("Error al actualizar status de activo documento: {}", ex.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam("estatus", estatus);
    }
    

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public DefaultStreamedContent getAdjuntoDescarga() {
        return adjuntoDescarga;
    }

    public void setAdjuntoDescarga(DefaultStreamedContent adjuntoDescarga) {
        this.adjuntoDescarga = adjuntoDescarga;
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

    public String getNombreRegistra() {
        return nombreRegistra;
    }

    public void setNombreRegistra(String nombreRegistra) {
        this.nombreRegistra = nombreRegistra;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public DocumentoLazy getDocumentoLazy() {
        return documentoLazy;
    }

    public void setDocumentoLazy(DocumentoLazy documentoLazy) {
        this.documentoLazy = documentoLazy;
    }

    public String getCadenaBusqueda() {
        return cadenaBusqueda;
    }

    public void setCadenaBusqueda(String cadenaBusqueda) {
        this.cadenaBusqueda = cadenaBusqueda;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public List<TransaccionPermisos> getPermisosLista() {
        return permisosLista;
    }

    public void setPermisosLista(List<TransaccionPermisos> permisosLista) {
        this.permisosLista = permisosLista;
    }

    public Documento getDocumentoSeleccionado() {
        return documentoSeleccionado;
    }

    public void setDocumentoSeleccionado(Documento documentoSeleccionado) {
        this.documentoSeleccionado = documentoSeleccionado;
    }

    public List<Documento> getDocumentoLista() {
        return this.documentoLista;
    }

    public List<TipoArchivo> getTipoArchivoLista() {
        return tipoArchivoLista;
    }

    public void setTipoArchivoLista(List<TipoArchivo> tipoArchivoLista) {
        this.tipoArchivoLista = tipoArchivoLista;
    }

}
