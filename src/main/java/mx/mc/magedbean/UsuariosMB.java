/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.magedbean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import mx.mc.lazy.UsuariosLazy;
import mx.mc.enums.TipoPerfilUsuario_Enum;
import mx.mc.enums.TipoUsuario_Enum;
import mx.mc.enums.Transaccion_Enum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import mx.mc.init.Constantes;
import mx.mc.init.CustomWebSecurityConfigurerAdapter;
import mx.mc.model.Estructura;
import mx.mc.model.Impresora;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.Rol;
import mx.mc.model.TipoPerfilUsuario;
import mx.mc.model.TipoSolucion;
import mx.mc.model.Usuario;
import mx.mc.model.TipoUsuario;
import mx.mc.model.Turno;
import mx.mc.model.TurnoMedico;
import mx.mc.model.UsuarioImpresora;
import mx.mc.model.UsuarioRol;
import mx.mc.model.VistaUsuario;
import mx.mc.service.EstructuraService;
import mx.mc.service.ImpresoraService;
import mx.mc.service.RolService;
import mx.mc.service.TipoSolucionService;
import mx.mc.service.TipoUsuarioService;
import mx.mc.service.TurnoMedicoService;
import mx.mc.service.TurnoService;
import mx.mc.service.UsuarioImpresoraService;
import mx.mc.service.UsuarioRolService;
import mx.mc.service.UsuarioService;
import mx.mc.util.Comunes;
import mx.mc.util.FechaUtil;
import mx.mc.util.Mensaje;
import mx.mc.util.UtilPath;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.PrimeFaces;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author bbautista
 */
@Controller
@Scope(value = "view")
public class UsuariosMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(UsuariosMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    private boolean activo;
    private String cadenaBusqueda;
    private boolean status;
    private boolean btnNew;
    private boolean rndSave;
    private boolean rndEdit;
    private boolean message;
    private boolean noProcess;
    private int numeroRegistros;
    private Date fechaActual;
    private Date fecha;
    private String pathDefinition;
    private String fileImg;
    private String nameFile;
    private Date date = new Date();
    private SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss");

    private boolean estr;
    private boolean esrol;
    private boolean etip;
    private String motivo;
    private String usuario;
    private String claveTemporal;
    private String correo;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String fechaVigencia;
    private String uestructura;
    private String rol;
    private String tipoUsuario;
    private String cedulaProf;
    private String cedulaEsp;
    private String uactivo;
    private String ubloqueado;
    private boolean activ;
    private boolean bloq;
    private int tipou;
    private String padre;
    private String hijo;
    private Boolean esMedico;
    private Boolean prescribeControlados;
    private PermisoUsuario permiso;
    private boolean mostrarBloqueoUsuario;
    private Pattern regexNomAp;
    private Pattern regexUser;
    private Pattern regexMail;
    private Pattern regexClave;
    private String confirmacion;

    private static TreeNode root;
    private static TreeNode selectNode;
    private String nameUnidad;
    private String pathNode;
    private String idRol;
    private List<String> listNode;
    private String usuarioErrUsuObligatorio;

    @Autowired
    private transient TurnoService turnoService;
    private List<String> listaIdTurnos;
    private List<Turno> listaTurnos;
    private List<String> listaIdPerfiles;
    private List<TipoPerfilUsuario> listaPerfiles;

    @Autowired
    private transient ImpresoraService impresoraService;
    private List<String> listaIdImpresoras;
    private List<Impresora> listaImpresoras;
    private boolean checkBoxImpresora;

    @Autowired
    private transient TurnoMedicoService turnoMedicoService;

    @Autowired
    private transient UsuarioImpresoraService usuarioImpresoraService;

    @Autowired
    private transient EstructuraService estructuraService;
    private Estructura estructuraSelect;
    private Estructura estructura;
    private List<Estructura> listEstructura;

    @Autowired
    private transient UsuarioService usuarioService;
    private Usuario usuarioSelect;
    private Usuario currentSesionUser;
    private Usuario userLayout;

    @Deprecated
    private List<VistaUsuario> usuarioList;
    @Deprecated
    private List<VistaUsuario> usuarioList2;
    private List<Usuario> usuarioLayout;

    @Autowired
    private transient RolService rolService;
    private Rol rolSelect;
    private List<Rol> rolList;

    @Autowired
    private transient UsuarioRolService usuarioRolService;
    private UsuarioRol usuarioRolSelect;
    private UsuarioRol usuarioRolLayout;
    private List<UsuarioRol> usuarioRolList;

    @Autowired
    private transient TipoUsuarioService tipoUsuarioService;
    private TipoUsuario tipoUsuarioSelect;
    private List<TipoUsuario> tipoUserList;

    private UsuariosLazy usuariosLazy;
    private ParamBusquedaReporte paramBusquedaReporte;
    private Integer passwordNumCaracter;

    @Autowired
    private transient TipoSolucionService tipoSolucionService;
    private List<TipoSolucion> tipoSolucionList;
    private TipoSolucion tipoSolucionSelect;
    private List<String> listaIdTipoSolucionSelect;

    @PostConstruct
    public void init() {
        usuarioErrUsuObligatorio = "usuario.err.usuObligatorio";
        passwordNumCaracter = 4;
        
        initialize();
        permiso = Comunes.obtenerPermisos(Transaccion_Enum.USUARIOS.getSufijo());
                
        obtenerListaUsuarios();

        //Llenamos los nodos de la estructura
        obtenerArbol();
        obtenerRoles();
        obtenerRolesUsuarios();
        obtenerTipoUsuarios();
        obtenerTipoPerfiles();
        obtenerTipoSoluciones();
    }

    /**
     * Inicializa variables
     */
    private void initialize() {
        usuarioSelect = new Usuario();
        confirmacion = "";
        usuarioRolSelect = new UsuarioRol();
        userLayout = new Usuario();
        usuarioList = new ArrayList<>();
        usuarioLayout = new ArrayList<>();
        usuarioList2 = null;
        pathDefinition = "";
        fileImg = "";
        nameFile = "";
        prescribeControlados = false;
        esMedico = false;
        usuario = "";
        claveTemporal = "";
        correo = "";
        nombre = "";
        apellidoPaterno = "";
        apellidoMaterno = "";
        fechaVigencia = "";
        uestructura = "";
        rol = "";
        tipoUsuario = "";
        cedulaProf = "";
        cedulaEsp = "";
        uactivo = "";
        ubloqueado = "";
        activ = Constantes.INACTIVO;
        bloq = Constantes.INACTIVO;
        tipou = 0;
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        currentSesionUser = sesion.getUsuarioSelected();
        passwordNumCaracter = sesion.getPasswordNumCaracter();

        regexNomAp = Constantes.regexNomAp;
        regexUser = Constantes.regexUser;
        regexMail = Constantes.regexMail;
        regexClave = Constantes.regexClave;

        message = Constantes.ACTIVO;
        estr = Constantes.INACTIVO;
        esrol = Constantes.INACTIVO;
        etip = Constantes.INACTIVO;
        cadenaBusqueda = "";
        numeroRegistros = 0;
        fechaActual = new Date();
        noProcess = Constantes.INACTIVO;
        paramBusquedaReporte = new ParamBusquedaReporte();
        this.checkBoxImpresora = false;
        this.listaPerfiles = new ArrayList<>();
        this.tipoSolucionList = new ArrayList<>();
        this.tipoSolucionSelect = new TipoSolucion();
        this.listaIdTipoSolucionSelect = new ArrayList<>();

    }

    private void obtenerTipoPerfiles() {
        TipoPerfilUsuario perfil = new TipoPerfilUsuario();
        perfil.setIdTipoPerfilUsuario(TipoPerfilUsuario_Enum.ADMIN.getIdTipoPerfil());
        perfil.setNombrePerfil(TipoPerfilUsuario_Enum.ADMIN.getNombrePerfil());
        listaPerfiles.add(perfil);
        perfil = new TipoPerfilUsuario();
        perfil.setIdTipoPerfilUsuario(TipoPerfilUsuario_Enum.JEFE_AREA.getIdTipoPerfil());
        perfil.setNombrePerfil(TipoPerfilUsuario_Enum.JEFE_AREA.getNombrePerfil());
        listaPerfiles.add(perfil);
    }

    private void obtenerParentNode(TreeNode nodo) {
        TreeNode node = nodo.getParent();
        if (node != null && node.getData() != "Root") {
            listNode.add(nodo.getParent().toString());
            obtenerParentNode(node);
        }
    }

    private void fillParentList(String idEstr) {
        listEstructura.stream().filter(est -> (est.getIdEstructura().equals(idEstr))).forEachOrdered(cnsmr -> {
            listNode.add(cnsmr.getNombre());
            if (cnsmr.getIdEstructuraPadre() != null) {
                fillParentList(cnsmr.getIdEstructuraPadre());
            }
        });

    }

    private void selectNode(String path, String name, int p, TreeNode nodo) {
        String[] aux = path.split("/");
        if (aux.length > p) {
            if (nodo.getData().equals(aux[p])) {
                p++;
                nodo.setExpanded(true);
            }
            if (nodo.getChildCount() > 0) {
                List<TreeNode> ln = nodo.getChildren();
                for (int i = 0; i < ln.size(); i++) {
                    selectNode(path, name, p, ln.get(i));
                }
            }
        }
        if (nodo.getData().equals(name)) {
            nodo.setSelected(true);
            return;
        }
    }

    /**
     * Obtiene el idEstructura de la estructura seleccionada del usuario
     *
     * @return
     */
    private String obtenerIdEstructura() {
        try {
            if (!nameUnidad.equals("")) {
                listEstructura.stream().filter(est -> (est.getNombre().equals(nameUnidad))).forEachOrdered(cnsmr
                        -> uestructura = cnsmr.getIdEstructura()
                );
            }
        } catch (Exception ex) {
            LOGGER.error("Error al obtener IdEstructura: {}", ex.getMessage());
        }
        return uestructura;
    }

    /**
     * Lectura de Archivo de layout cargado
     *
     * @param bites
     * @param name
     * @return
     * @throws FileNotFoundException
     * @throws Exception
     */
    private String createFile(byte[] bites, String name) throws IOException, InterruptedException {
        if (bites != null) {
            String path = UtilPath.getPathDefinida(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/"));
            fileImg = sdf.format(date) + name;
            pathDefinition = path + Constantes.PATH_TMP + fileImg;
            try (FileOutputStream fos = new FileOutputStream(pathDefinition)) {
                fos.write(bites);
                fos.flush();
            }
            Thread.sleep(2000);
            this.setNameFile(Constantes.PATH_IMG + fileImg);

            return pathDefinition;
        } else {
            this.setNameFile("");
        }
        return "";
    }

    private String getValue(Cell cell) {
        switch (cell.getCellType()) {
            case BLANK:
                return "";

            case BOOLEAN:
                return "CELL_TYPE_BOOLEAN";

            case ERROR:
                return "CELL_TYPE_ERROR";

            case FORMULA:
                return cell.getStringCellValue();

            case NUMERIC:
                return cell.getNumericCellValue() + "";

            case STRING:
                return cell.getStringCellValue();

            default:
                return "valor desconocido";

        }
    }

    /**
     * Limpia variables
     */
    private void cleanObjects() {
        usuario = "";
        claveTemporal = "";
        correo = "";
        nombre = "";
        apellidoPaterno = "";
        apellidoMaterno = "";
        fechaVigencia = "";
        uestructura = "";
        pathNode = "";
        rol = "";
        tipoUsuario = "";
        cedulaProf = "";
        cedulaEsp = "";
        uactivo = "";
        ubloqueado = "";
        motivo = "";
        activ = Constantes.INACTIVO;
        bloq = Constantes.INACTIVO;
        tipou = 0;
        listNode = new ArrayList<>();
        estr = Constantes.INACTIVO;
        esrol = Constantes.INACTIVO;
        etip = Constantes.INACTIVO;
        fecha = null;
        this.listaTurnos = new ArrayList<>();
        this.listaImpresoras = new ArrayList<>();
        this.checkBoxImpresora = false;
        this.prescribeControlados = false;
    }

    /**
     * Valida contraseña ingresada
     *
     * @param user
     * @return
     */
    private boolean validaDatosClave(Usuario user) {
        boolean cont = Constantes.ACTIVO;

        if (user.getClaveAcceso().equals("")) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.claveobligatoria"), null);
            cont = Constantes.INACTIVO;
        } else if (user.getClaveAcceso().length()<passwordNumCaracter) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.claveInvalida") + " " + passwordNumCaracter , null);
            cont = Constantes.INACTIVO;
        
        }
// TODO : Implementar la expresión regular de la contraseña
//        else {
//            Matcher mat = Constantes.regexClave.matcher(user.getClaveAcceso());
//            if (!mat.find()) {
//                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.amaternoInvalido"), null);
//                cont = Constantes.INACTIVO;
//            }
//        }
        return cont;
    }

    /**
     * Valida datos de usuarios
     *
     * @return
     */
    private boolean validaDatos() {
        boolean cont = Constantes.ACTIVO;
        Matcher mat;

        if (!usuario.equals("")) {
            mat = Constantes.regexUser.matcher(usuario);
            if (!mat.find()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.usuInvalido"), null);
                cont = Constantes.INACTIVO;
            }
        } else {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(usuarioErrUsuObligatorio), null);
            cont = Constantes.INACTIVO;
        }
        if (claveTemporal.equals("")) {
            cont = Constantes.INACTIVO;
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.clvaccesObligatorio"), null);
        }

        if (!correo.equals("")) {
            mat = Constantes.regexMail.matcher(correo);
            if (!mat.find()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.correoinvalido"), null);
                cont = Constantes.INACTIVO;
            }
        } else {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.correobligatorio"), null);
            cont = Constantes.INACTIVO;
        }
        if (!nombre.equals("")) {
            mat = Constantes.regexNomAp.matcher(nombre);
            if (!mat.find()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.nombreinvalido"), null);
                cont = Constantes.INACTIVO;
            }
        } else {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.nombreobligatorio"), null);
            cont = Constantes.INACTIVO;
        }

        if (!apellidoPaterno.equals("")) {
            mat = Constantes.regexNomAp.matcher(apellidoPaterno);
            if (!mat.find()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.apaternoInvalido"), null);
                cont = Constantes.INACTIVO;
            }
        } else {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.apaternObligatorio"), null);
            cont = Constantes.INACTIVO;
        }

        if (!apellidoMaterno.equals("")) {
            mat = Constantes.regexNomAp.matcher(apellidoMaterno);
            if (!mat.find()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.amaternoInvalido"), null);
                cont = Constantes.INACTIVO;
            }
        }

        if (!fechaVigencia.equals("")) {
            if (fecha != null && !FechaUtil.isFechaMayorIgualQue(fecha, fechaActual)) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.fechavigMenor"), null);
                cont = Constantes.INACTIVO;
            }
        } else {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.fechavigMenorObligatorio"), null);
            cont = Constantes.INACTIVO;
        }

        if (!uestructura.equals("")) {
            if (!estr) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.estructNoExiste"), null);
                cont = Constantes.INACTIVO;
            }
        } else {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.estructObligatorio"), null);
            cont = Constantes.INACTIVO;
        }

        if (!rol.equals("")) {
            if (!esrol) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.rolNoExiste"), null);
                cont = Constantes.INACTIVO;
            }
        } else {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.rolObligatorio"), null);
            cont = Constantes.INACTIVO;
        }

        if (!tipoUsuario.equals("")) {
            if (!etip) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.tipUsuarioNoExiste"), null);
                cont = Constantes.INACTIVO;
            }
        } else {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.tipUsuariobligatorio"), null);
            cont = Constantes.INACTIVO;
        }

        if (cedulaProf.equals("") && tipou == TipoUsuario_Enum.MEDICO.getValue()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.cedulaobligatoria"), null);
            cont = Constantes.INACTIVO;
        }

        return cont;
    }

    /**
     * Parsea archivo Excel 2003
     *
     * @param excelFilePath
     */
    private void readLayout2003(String excelFilePath) {
        boolean exito = Constantes.ACTIVO;
        int num = 1;
        try (FileInputStream inputStream = new FileInputStream(new File(excelFilePath)); HSSFWorkbook workbook = new HSSFWorkbook(inputStream)) {
            HSSFSheet firstSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = firstSheet.iterator();
            usuarioLayout = new ArrayList<>();
            while (iterator.hasNext()) {
                Row nextRow = iterator.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();
                cleanObjects();
                if (num > 1) {
                    exito = Constantes.ACTIVO;
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        switch (cell.getColumnIndex()) {
                            case 0://Usuario                                                                
                                usuario = getValue(cell);
                                break;
                            case 1: //Clave Temporal
                                claveTemporal = cell.toString();
                                if (claveTemporal.contains(".0")) {
                                    int aux = claveTemporal.indexOf(".");
                                    claveTemporal = claveTemporal.substring(0, aux);
                                }
                                break;
                            case 2: //Correo                                
                                correo = getValue(cell);
                                break;
                            case 3: //Nombre
                                nombre = getValue(cell);
                                break;
                            case 4: //Apellido Paterno                                
                                apellidoPaterno = getValue(cell);
                                break;
                            case 5: //Apellido Materno
                                apellidoMaterno = getValue(cell);
                                break;
                            case 6: //Fecha Vigencia
                                fechaVigencia = cell.getDateCellValue().toString();
                                if (!fechaVigencia.equals("")) {
                                    fecha = cell.getDateCellValue();
                                }
                                break;
                            case 7: //Estructura                                
                                uestructura = getValue(cell);
                                if (!uestructura.equals("")) {
                                    listEstructura.stream().filter(est -> (est.getNombre().equals(uestructura))).forEachOrdered(cnsmr -> {
                                        uestructura = cnsmr.getIdEstructura();
                                        estr = Constantes.ACTIVO;
                                    });
                                    fillParentList(uestructura);

                                    if (!listNode.isEmpty()) {
                                        String ax = "";
                                        for (int i = listNode.size() - 1; i >= 0; i--) {
                                            ax = i == 0 ? "" : "/";
                                            pathNode += listNode.get(i) + ax;
                                        }
                                        if (pathNode.length() > 200) {
                                            pathNode = pathNode.substring(0, 200);
                                        }
                                    }
                                }
                                break;
                            case 8: //Rol
                                rol = getValue(cell);
                                if (!rol.equals("")) {
                                    rolList.stream().filter(ro -> (ro.getNombre().equals(rol))).forEachOrdered(cnsmr -> {
                                        rol = cnsmr.getIdRol();
                                        esrol = Constantes.ACTIVO;
                                    });
                                }
                                break;
                            case 9: //Tipo de Usuario
                                tipoUsuario = getValue(cell);
                                if (!tipoUsuario.equals("")) {
                                    tipoUserList.stream().filter(tipo -> (tipo.getDescripcion().equals(tipoUsuario))).forEachOrdered(cnsmr -> {
                                        tipou = cnsmr.getIdTipoUsuario();
                                        etip = Constantes.ACTIVO;
                                    });
                                }
                                break;
                            case 10: //Cedula Prof.
                                cedulaProf = cell.toString();
                                if (cedulaProf.contains(".")) {
                                    double dou = Double.parseDouble(cedulaProf);
                                    cedulaProf = (int) dou + "";
                                }
                                break;
                            case 11: //Cedula Esp.
                                cedulaEsp = getValue(cell);
                                if (cedulaEsp.contains(".")) {
                                    double dou = Double.parseDouble(cedulaEsp);
                                    cedulaEsp = (int) dou + "";
                                }
                                break;
                            case 12:  //Activo
                                uactivo = getValue(cell);
                                if (!uactivo.equals("")) {
                                    activ = uactivo.toUpperCase().equals("SI") ? Constantes.ACTIVO : Constantes.INACTIVO;
                                }
                                break;
                            case 13: //Bloqueado   
                                ubloqueado = getValue(cell);
                                if (!ubloqueado.equals("")) {
                                    bloq = ubloqueado.toUpperCase().equals("SI") ? Constantes.ACTIVO : Constantes.INACTIVO;
                                }
                                break;
                            default:
                        }
                        if (usuario.equals("") && nombre.equals("") && apellidoPaterno.equals("") && apellidoMaterno.equals("")) {
                            break;
                        }
                    }
                    userLayout = new Usuario();
                    usuarioRolLayout = new UsuarioRol();

                    exito = validaDatos();
                    if (exito) {
                        Usuario user = new Usuario();
                        userLayout.setNombreUsuario(usuario);
                        userLayout.setCorreoElectronico(correo);
                        userLayout.setNombre(nombre);

                        user = usuarioService.verificaSiExisteUser(userLayout);
                        if (user == null) {
                            exito = Constantes.ACTIVO;
                            userLayout.setIdUsuario(Comunes.getUUID());
                            userLayout.setApellidoPaterno(apellidoPaterno);
                            userLayout.setApellidoMaterno(apellidoMaterno);
                            userLayout.setActivo(activ);
                            userLayout.setUsuarioBloqueado(bloq);
                            userLayout.setFechaVigencia(fecha);
                            userLayout.setIdEstructura(uestructura);
                            userLayout.setPathEstructura(pathNode);
                            userLayout.setIdTipoUsuario(tipou);
                            userLayout.setCedProfesional(cedulaProf);
                            userLayout.setCedEspecialidad(cedulaEsp);
                            userLayout.setClaveAcceso(CustomWebSecurityConfigurerAdapter.argon2Encode(claveTemporal));
                            userLayout.setInsertFecha(new Date());
                            userLayout.setInsertIdUsuario(currentSesionUser.getIdUsuario());

                            usuarioRolLayout.setIdRol(rol);
                            usuarioRolLayout.setIdUsuario(userLayout.getIdUsuario());
                            usuarioRolLayout.setInsertFecha(new Date());
                            usuarioRolLayout.setInsertIdUsuario(currentSesionUser.getIdUsuario());

                            exito = usuarioService.insertarLayout(userLayout, usuarioRolLayout);
                            if (!exito) {
                                motivo = "No se pudo insertar el usuario";
                            }
                        } else {
                            motivo = "El usuario ya existe";
                            exito = Constantes.INACTIVO;
                        }
                    }

                    if (!exito && !usuario.equals("") || !nombre.equals("") || !correo.equals("")) {
                        userLayout.setNombreUsuario(usuario);
                        userLayout.setCorreoElectronico(correo);
                        userLayout.setNombre(nombre);
                        userLayout.setApellidoPaterno(apellidoPaterno);
                        userLayout.setApellidoMaterno(apellidoMaterno);
                        userLayout.setUpdateIdUsuario(motivo);
                        usuarioLayout.add(userLayout);
                    }
                }
                num++;
            }
        } catch (Exception ex) {
            LOGGER.error("ERROR en readLayout2003: {}", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.formatInesperado"), null);
        }
    }

    /**
     * Parsea archivo Excel 2007
     *
     * @param excelFilePath
     */
    private void readLayout2007(String excelFilePath) {
        boolean exito = Constantes.ACTIVO;
        int num = 1;
        try (FileInputStream inputStream = new FileInputStream(new File(excelFilePath)); Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet firstSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = firstSheet.iterator();
            usuarioLayout = new ArrayList<>();
            while (iterator.hasNext()) {
                Row nextRow = iterator.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();
                cleanObjects();
                if (num > 1) {
                    exito = Constantes.ACTIVO;
                    while (cellIterator.hasNext()) {
                        Cell cell7 = cellIterator.next();
                        switch (cell7.getColumnIndex()) {
                            case 0://Usuario                                                                
                                usuario = getValue(cell7);
                                break;
                            case 1: //Clave Temporal
                                claveTemporal = cell7.toString();
                                if (claveTemporal.contains(".0")) {
                                    int aux = claveTemporal.indexOf(".");
                                    claveTemporal = claveTemporal.substring(0, aux);
                                }
                                break;
                            case 2: //Correo                                
                                correo = getValue(cell7);
                                break;
                            case 3: //Nombre
                                nombre = getValue(cell7);
                                break;
                            case 4: //Apellido Paterno                                
                                apellidoPaterno = getValue(cell7);
                                break;
                            case 5: //Apellido Materno
                                apellidoMaterno = getValue(cell7);
                                break;
                            case 6: //Fecha Vigencia
                                fechaVigencia = cell7.getDateCellValue().toString();
                                if (!fechaVigencia.equals("")) {
                                    fecha = cell7.getDateCellValue();
                                }
                                break;
                            case 7: //Estructura                                
                                uestructura = getValue(cell7);
                                if (!uestructura.equals("")) {
                                    listEstructura.stream().filter(est -> (est.getNombre().equals(uestructura))).forEachOrdered(cnsmr -> {
                                        uestructura = cnsmr.getIdEstructura();
                                        estr = Constantes.ACTIVO;
                                    });
                                    fillParentList(uestructura);

                                    if (!listNode.isEmpty()) {
                                        String aux = "";
                                        for (int i = listNode.size() - 1; i >= 0; i--) {
                                            aux = i == 0 ? "" : "/";
                                            pathNode += listNode.get(i) + aux;
                                        }
                                        if (pathNode.length() > 200) {
                                            pathNode = pathNode.substring(0, 200);
                                        }
                                    }
                                }
                                break;
                            case 8: //Rol
                                rol = getValue(cell7);
                                if (!rol.equals("")) {
                                    rolList.stream().filter(ro -> (ro.getNombre().toUpperCase().equals(rol.toUpperCase()))).forEachOrdered(cnsmr -> {
                                        rol = cnsmr.getIdRol();
                                        esrol = Constantes.ACTIVO;
                                    });
                                }
                                break;
                            case 9: //Tipo de Usuario
                                tipoUsuario = getValue(cell7);
                                if (!tipoUsuario.equals("")) {
                                    tipoUserList.stream().filter(tipo -> (tipo.getDescripcion().equals(tipoUsuario))).forEachOrdered(cnsmr -> {
                                        tipou = cnsmr.getIdTipoUsuario();
                                        etip = Constantes.ACTIVO;
                                    });
                                }
                                break;
                            case 10: //Cedula Prof.
                                cedulaProf = cell7.toString();
                                if (cedulaProf.contains(".")) {
                                    double dou = Double.parseDouble(cedulaProf);
                                    cedulaProf = (int) dou + "";
                                }
                                break;
                            case 11: //Cedula Esp.
                                cedulaEsp = getValue(cell7);
                                if (cedulaEsp.contains(".")) {
                                    double dou = Double.parseDouble(cedulaEsp);
                                    cedulaEsp = (int) dou + "";
                                }
                                break;
                            case 12:  //Activo
                                uactivo = getValue(cell7);
                                if (!uactivo.equals("")) {
                                    activ = uactivo.toUpperCase().equals("SI") ? Constantes.ACTIVO : Constantes.INACTIVO;
                                }
                                break;
                            case 13: //Bloqueado   
                                ubloqueado = getValue(cell7);
                                if (!ubloqueado.equals("")) {
                                    bloq = ubloqueado.toUpperCase().equals("SI") ? Constantes.ACTIVO : Constantes.INACTIVO;
                                }
                                break;
                            default:
                        }
                        if (usuario.equals("") && nombre.equals("") && apellidoPaterno.equals("") && apellidoMaterno.equals("")) {
                            break;
                        }
                    }
                    Usuario userLayout7 = new Usuario();
                    UsuarioRol usuarioRolLayout7 = new UsuarioRol();

                    exito = validaDatos();
                    if (exito) {
                        Usuario user = new Usuario();
                        userLayout7.setNombreUsuario(usuario);
                        userLayout7.setCorreoElectronico(correo);
                        userLayout7.setNombre(nombre);

                        user = usuarioService.verificaSiExisteUser(userLayout7);
                        if (user == null) {
                            exito = Constantes.ACTIVO;
                            userLayout7.setIdUsuario(Comunes.getUUID());
                            userLayout7.setApellidoPaterno(apellidoPaterno);
                            userLayout7.setApellidoMaterno(apellidoMaterno);
                            userLayout7.setActivo(activ);
                            userLayout7.setUsuarioBloqueado(bloq);
                            userLayout7.setFechaVigencia(fecha);
                            userLayout7.setIdEstructura(uestructura);
                            userLayout7.setPathEstructura(pathNode);
                            userLayout7.setIdTipoUsuario(tipou);
                            userLayout7.setCedProfesional(cedulaProf);
                            userLayout7.setCedEspecialidad(cedulaEsp);
                            userLayout7.setClaveAcceso(CustomWebSecurityConfigurerAdapter.argon2Encode(claveTemporal));
                            userLayout7.setInsertFecha(new Date());
                            userLayout7.setInsertIdUsuario(currentSesionUser.getIdUsuario());

                            usuarioRolLayout7.setIdRol(rol);
                            usuarioRolLayout7.setIdUsuario(userLayout7.getIdUsuario());
                            usuarioRolLayout7.setInsertFecha(new Date());
                            usuarioRolLayout7.setInsertIdUsuario(currentSesionUser.getIdUsuario());

                            exito = usuarioService.insertarLayout(userLayout7, usuarioRolLayout7);
                            if (!exito) {
                                motivo = "No se pudo insertar el usuario";
                            }
                        } else {
                            motivo = "El usuario ya existe";
                            exito = Constantes.INACTIVO;
                        }
                    }

                    if (!exito && !usuario.equals("") || !nombre.equals("") || !correo.equals("")) {
                        userLayout7.setNombreUsuario(usuario);
                        userLayout7.setCorreoElectronico(correo);
                        userLayout7.setNombre(nombre);
                        userLayout7.setApellidoPaterno(apellidoPaterno);
                        userLayout7.setApellidoMaterno(apellidoMaterno);
                        userLayout7.setUpdateIdUsuario(motivo);
                        usuarioLayout.add(userLayout7);
                    }
                }
                num++;
            }
        } catch (Exception ex) {
            LOGGER.error("ERROR en readLayout2007: {}", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.formatInesperado"), null);
        }
    }

    /**
     * Valida datos de usuario que se guardará
     *
     * @param user
     * @param urol
     * @return
     */
    private boolean validaDatosUser(Usuario user, UsuarioRol urol) {
        boolean cont = Constantes.ACTIVO;

        Matcher mat;
        //String mensaje = "";
        if (!user.getNombreUsuario().equals("")) {
            mat = Constantes.regexUser.matcher(user.getNombreUsuario());
            if (!mat.find()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.usuInvalido"), null);                
                cont = Constantes.INACTIVO;
            }
        } else {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(usuarioErrUsuObligatorio), null);
            cont = Constantes.INACTIVO;
        }

        if(rndEdit) {
            if (!user.getClaveAcceso().equals("")) {
                if (user.getClaveAcceso().length() <= 4) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.claveInvalida") + " " + passwordNumCaracter , null);
                    cont = Constantes.INACTIVO;
                }
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.clvaccesObligatorio"), null);
                cont = Constantes.INACTIVO;
            }
            if(!confirmacion.equals("") && !user.getClaveAcceso().equals("")) {
                if(!confirmacion.equals(user.getClaveAcceso())) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.claveNoCoincide"), null);
                    cont = Constantes.INACTIVO;
                }
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.confirmacionObligatorio"), null);
                cont = Constantes.INACTIVO;
            }
        }        

        if (!user.getCorreoElectronico().equals("")) {
            mat = Constantes.regexMail.matcher(user.getCorreoElectronico());
            if (!mat.find()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.correoinvalido"), null);
                cont = Constantes.INACTIVO;
            }
        } else {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.correobligatorio"), null);
            cont = Constantes.INACTIVO;
        }
        if (!user.getNombre().equals("")) {
            mat = Constantes.regexNomAp.matcher(user.getNombre());
            if (!mat.find()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.nombreinvalido"), null);
                cont = Constantes.INACTIVO;
            }
        } else {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.nombreobligatorio"), null);
            cont = Constantes.INACTIVO;
        }

        if (!user.getApellidoPaterno().equals("")) {
            mat = Constantes.regexNomAp.matcher(user.getApellidoPaterno());
            if (!mat.find()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.apaternoInvalido"), null);
                cont = Constantes.INACTIVO;
            }
        } else {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.apaternObligatorio"), null);
            cont = Constantes.INACTIVO;
        }

        if (!user.getApellidoMaterno().equals("")) {
            mat = Constantes.regexNomAp.matcher(user.getApellidoMaterno());
            if (!mat.find()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.amaternoInvalido"), null);
                cont = Constantes.INACTIVO;
            }
        }

        if (user.getFechaVigencia() != null ) {
            if (!FechaUtil.isFechaMayorIgualQue(user.getFechaVigencia(), fechaActual)) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.fechavigMenor"), null);
                cont = Constantes.INACTIVO;
            }
        } else {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.fechavigMenorObligatorio"), null);
            //motivo = "El fecha de vigencia es Obligatorio";
            cont = Constantes.INACTIVO;
        }

        if (user.getIdEstructura().equals("")) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.estructObligatorio"), null);
            cont = Constantes.INACTIVO;
        }

        if (urol.getIdRol().equals("")) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.rolObligatorio"), null);
            cont = Constantes.INACTIVO;
        }

        if (user.getIdTipoUsuario() <= 0) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.tipUsuariobligatorio"), null);
            cont = Constantes.INACTIVO;
        }

        if (user.getCedProfesional().equals("") && user.getIdTipoUsuario().equals(TipoUsuario_Enum.MEDICO.getValue())) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.cedulaobligatoria"), null);
            cont = Constantes.INACTIVO;
        }
        
        if(!existeUsuario().equals("")) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, existeUsuario(), null);                                                    
            cont = Constantes.INACTIVO;
        }
        
        if(!existeMail().equals("")) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, existeMail(), null);
            cont = Constantes.INACTIVO;
        }
        
        return cont;
    }

    public String existeUsuario() {
        String existeUser = "";
        try {            
            Usuario user;
            if (usuarioSelect != null && !usuarioSelect.getNombreUsuario().equals("")) {
                user = usuarioService.getUserByUserName(usuarioSelect.getNombreUsuario());
                if (user != null) {
                    if (usuarioSelect.getIdUsuario() != null) {
                        if (!user.getIdUsuario().equals(usuarioSelect.getIdUsuario()) && user.getNombreUsuario().equals(usuarioSelect.getNombreUsuario())) {                            
                            existeUser = RESOURCES.getString("usuario.warn.existeUsuario");
                        }
                    } else {
                        existeUser = RESOURCES.getString("usuario.warn.existeUsuario");
                    }
                }
            }            
        } catch (Exception ex) {
            LOGGER.error("Error al consultar existencia del Usuario: {}", ex.getMessage());
        }
        return existeUser;
    }
    
    public String existeMail() {
        String existeMail = "";
        try {            
            Usuario user;
            if (usuarioSelect != null && !usuarioSelect.getCorreoElectronico().equals("")) {
                user = usuarioService.getUserByEmail(usuarioSelect.getCorreoElectronico());
                if (user != null) {
                    if (usuarioSelect.getIdUsuario() != null) {
                        if (!user.getIdUsuario().equals(usuarioSelect.getIdUsuario()) && user.getCorreoElectronico().equals(usuarioSelect.getCorreoElectronico())) {                            
                            existeMail = RESOURCES.getString("usuario.warn.existeCorreo");
                        }
                    } else {
                        existeMail = RESOURCES.getString("usuario.warn.existeCorreo");                        
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al insertar el Email: {}", ex.getMessage());
        }
        return existeMail;
    }
    
    /**
     * Metodo para crear arbol de estructura
     */
    private void obtenerArbol() {
        try {
            obtenerUnidadesJerarquicas();
            root = new DefaultTreeNode("Root", null);
            TreeNode nodo = new DefaultTreeNode(listEstructura.get(0).getNombre(), root);
            for (int i = 0; i < listEstructura.size(); i++) {
                for (Estructura et : listEstructura) {
                    if (et.getIdEstructuraPadre() != null && et.getIdEstructuraPadre().equals(listEstructura.get(i).getIdEstructura())) {
                        if (i == 0) {
                            nodo.getChildren().add(new DefaultTreeNode(et.getNombre()));
                        }
                        obtenerPadre(nodo, listEstructura.get(i).getNombre(), et.getNombre());
                    }
                }
            }

        } catch (Exception exc) {
            LOGGER.error("Error al obtener unidades jerarquicas: {}", exc.getMessage());
        }

    }

    /**
     * Metodo para obtener la lista de unidades
     */
    public void obtenerUnidadesJerarquicas() {
        try {
            listEstructura = estructuraService.obtenerUnidadesOrderTipo();
        } catch (Exception ex) {
            LOGGER.error("Error al obtener unidades jerarquicas: {}", ex.getMessage());
        }
    }

    /**
     *
     * Metodo recursivo para obtener el padre
     *
     * @param nodo
     * @param nodoPadre
     * @param nodoHijo
     *
     */
    private void obtenerPadre(TreeNode nodo, String nodoPadre, String nodoHijo) {
        List<TreeNode> nodoList = nodo.getChildren();
        for (TreeNode auxNodo : nodoList) {
            if (auxNodo.getData().equals(nodoPadre)) {
                auxNodo.getChildren().add(new DefaultTreeNode(nodoHijo));
            } else {
                obtenerPadre(auxNodo, nodoPadre, nodoHijo);
            }
        }

    }

    public void onNodeSelect(NodeSelectEvent event) {
        nameUnidad = event.getTreeNode().toString();
        listNode = new ArrayList<>();
        obtenerParentNode(event.getTreeNode());
        pathNode = "";
        String auxili = "";
        for (int i = listNode.size() - 1; i >= 0; i--) {
            auxili = i == 0 ? "" : "/";
            pathNode += listNode.get(i) + auxili;
        }
        if (pathNode.length() > 200) {
            pathNode = pathNode.substring(0, 200);
        }
        if (usuarioSelect != null) {
            usuarioSelect.setPathEstructura(pathNode);
        }
    }

    /**
     * Obtener lista de roles disponibles para asignar a usuario
     */
    private void obtenerRoles() {
        LOGGER.trace("mx.mc.magedbean.UsuariosMB.obtenerRoles()");
        try {
            Rol _rol = new Rol();
            rolList = rolService.obtenerLista(_rol);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener Roles registrados: {}", ex.getMessage());
        }
    }

    /**
     * obtiene Roles asignado a Usuario
     */
    public void obtenerRolesUsuarios() {
        LOGGER.trace("mx.mc.magedbean.UsuariosMB.obtenerRolesUsuarios()");
        try {
            UsuarioRol userRol = new UsuarioRol();
            usuarioRolList = usuarioRolService.obtenerLista(userRol);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener Roles asignado a Usuario: {}", ex.getMessage());
        }
    }

    /**
     * Obtiene tipos de Usuario para registrar
     */
    public void obtenerTipoUsuarios() {
        LOGGER.trace("mx.mc.magedbean.UsuariosMB.obtenerTipoUsuarios()");
        try {
            TipoUsuario tuser = new TipoUsuario();
            tipoUserList = tipoUsuarioService.obtenerLista(tuser);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener tipos de Usuarios: {}", ex.getMessage());
        }
    }

    /**
     * Se obtienen los tipos de soluciones que existen en base de datos activos
     */
    public void obtenerTipoSoluciones() {
        LOGGER.info("mx.mc.magedbean.UsuariosMB.obtenerTipoSolucion");
        try {
            TipoSolucion tipoSolucion = new TipoSolucion();
            tipoSolucion.setActivo(1);
            tipoSolucionList = tipoSolucionService.obtenerLista(tipoSolucion);
        } catch(Exception ex) {
            LOGGER.error("Error al obtener los tipos de solución: {}", ex.getMessage());
        }
    }

    /**
     * Crea usuario nuevo
     */
    public void crearUsuario() {
        LOGGER.trace("mx.mc.magedbean.UsuariosMB.crearUsuario()");
        mostrarBloqueoUsuario = false;
        usuarioLayout = new ArrayList<>();
        usuarioSelect = new Usuario();
        confirmacion = "";
        activo = Constantes.ACTIVO;
        rndEdit = Constantes.ACTIVO;
        usuarioRolSelect = new UsuarioRol();
        tipoSolucionSelect = new TipoSolucion();
        listaIdTipoSolucionSelect = new ArrayList<>();
        nameUnidad = "";
        this.listaIdTurnos = new ArrayList<>();
        this.listaIdPerfiles = new ArrayList<>();
        this.listaIdImpresoras = new ArrayList<>();
        this.checkBoxImpresora = false;
        this.prescribeControlados = false;
        obtenerArbol();
        try {
            this.listaTurnos = turnoService.obtenerLista(new Turno());
        } catch (Exception ex) {
            LOGGER.error("Error al cargar lista de turnos disponibles: {}", ex.getMessage());
        }
        try {
            this.listaImpresoras = impresoraService.obtenerListaImpresora();
        } catch (Exception ex) {
            LOGGER.error("Error al cargar lista de impresoras disponibles: {}", ex.getMessage());
        }
        if (this.listaIdImpresoras == null || this.listaImpresoras.isEmpty()) {
            this.checkBoxImpresora = true;
        }
    }

    /**
     * Lista
     */
    public void obtenerListaUsuarios() {
        LOGGER.trace("mx.mc.magedbean.UsuariosMB.obtenerListaUsuarios()");
        try {
            paramBusquedaReporte.setNuevaBusqueda(true);
            paramBusquedaReporte.setCadenaBusqueda(cadenaBusqueda);
            usuariosLazy = new UsuariosLazy(usuarioService, paramBusquedaReporte);
            LOGGER.debug("Resultados: {}", usuariosLazy.getTotalReg());

        } catch (Exception e) {
            LOGGER.error("Error al buscar Usuarios: {}", e.getMessage());
        }

    }

    /**
     * Obtiene Usuario que se desea editar
     *
     * @param idUsuario
     */
    public void obtenerUsuario(String idUsuario) {
        LOGGER.trace("mx.mc.magedbean.UsuariosMB.obtenerUsuario()");
        try {
            listaIdTipoSolucionSelect = new ArrayList<>();
            mostrarBloqueoUsuario = true;
            if (permiso.isPuedeVer() && !idUsuario.isEmpty()) {
                this.checkBoxImpresora = false;
                rndSave = Constantes.ACTIVO;
                usuarioSelect = usuarioService.obtenerUsuarioPorId(idUsuario);

                this.userLayout.setClaveAcceso("");
                if (usuarioSelect != null) {
                    usuarioRolList.stream().filter(prdct -> prdct.getIdUsuario().equals(usuarioSelect.getIdUsuario())).forEachOrdered(cnsmr -> {
                        usuarioRolSelect = cnsmr;
                        idRol = cnsmr.getIdRol();
                    });
                    nameUnidad = "";
                    listEstructura.stream().filter(prdct -> prdct.getIdEstructura().equals(usuarioSelect.getIdEstructura())).forEachOrdered(cnsmr -> {
                        estructuraSelect = cnsmr;
                        nameUnidad = cnsmr.getNombre();
                    });
                    if (!nameUnidad.equals("") && usuarioSelect.getPathEstructura() != null) {
                        selectNode(usuarioSelect.getPathEstructura(), nameUnidad, 0, root);
                    }
                    if (permiso.isPuedeEditar()) {
                        activo = Constantes.INACTIVO;
                        rndEdit = Constantes.INACTIVO;
                        rndSave = Constantes.ACTIVO;
                    }

                    List<String> listaTemp = new ArrayList<>();
                    List<String> listIdPerfil = new ArrayList<>();
                    List<String> listaTempImpre = new ArrayList<>();
                    List<String> listIdTipoSolucion = new ArrayList<>();
                    List<TurnoMedico> listaIdTurno = turnoMedicoService.obtenerLista(
                            new TurnoMedico(null, null, this.usuarioSelect.getIdUsuario()));
                    for (TurnoMedico item : listaIdTurno) {
                        listaTemp.add(item.getIdTurno().toString());
                    }
                    this.listaIdTurnos = listaTemp;
                    if (usuarioSelect.getAdministrador().equals(Constantes.ADMINISTRADOR)) {
                        listIdPerfil.add(TipoPerfilUsuario_Enum.ADMIN.getIdTipoPerfil().toString());
                    }
                    if (usuarioSelect.getIdTipoPerfil().equals(Constantes.JEFE_AREA)) {
                        listIdPerfil.add(TipoPerfilUsuario_Enum.JEFE_AREA.getIdTipoPerfil().toString());
                    }
                    this.listaIdPerfiles = listIdPerfil;
                     if(usuarioSelect.getIdTipoSolucion() != null) {
                        String listaTipoSoluciones[] = usuarioSelect.getIdTipoSolucion().split(",");
                        for(String idTipoSolucion :listaTipoSoluciones) {
                            listIdTipoSolucion.add(idTipoSolucion.trim());
                        }
                        this.listaIdTipoSolucionSelect = listIdTipoSolucion;   
                    }  
                    this.listaTurnos = turnoService.obtenerLista(new Turno());
                    List<UsuarioImpresora> listaIdImpresora = usuarioImpresoraService.obtenerLista(
                            new UsuarioImpresora(this.usuarioSelect.getIdUsuario(), null, null, null, null, null));
                    for (UsuarioImpresora usuarioImpresora : listaIdImpresora) {
                        listaTempImpre.add(usuarioImpresora.getIdImpresora());
                    }
                    this.listaIdImpresoras = listaTempImpre;
                    this.listaImpresoras = impresoraService.obtenerListaImpresora();
                    if (this.listaImpresoras == null || this.listaImpresoras.isEmpty()) {
                        this.checkBoxImpresora = true;
                    }
                    if (this.usuarioSelect.getPrescribeControlados() != null) {
                        this.prescribeControlados = this.usuarioSelect.getPrescribeControlados();
                    } else {
                        this.prescribeControlados = false;
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al obtener el Usuario: {}", ex.getMessage());
        }
    }

    /**
     * Guarda datos de usuario ingresados en la forma de creación o edición
     */
    public void saveUsuario() {
        LOGGER.trace("mx.mc.magedbean.UsuariosMB.saveUsuario()");
        message = Constantes.INACTIVO;
        try {
            if (this.nameUnidad.equalsIgnoreCase("Almacenes") || this.nameUnidad.equalsIgnoreCase("Consulta Interna")
                    || this.nameUnidad.equalsIgnoreCase("Consulta Externa")) {
                status = Constantes.INACTIVO;
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usr.estructura.err"), null);
            } else {
                if (permiso.isPuedeEditar()) {
                    if (usuarioSelect != null) {
                        if (usuarioSelect.getIdUsuario() == null) {
                            usuarioSelect.setIdUsuario(Comunes.getUUID());
                            usuarioSelect.setInsertFecha(new Date());
                            usuarioSelect.setInsertIdUsuario(currentSesionUser.getIdUsuario());
                            usuarioSelect.setIdEstructura(obtenerIdEstructura());

                            List<TurnoMedico> listaTemp = new ArrayList<>();
                            if (this.listaIdTurnos != null || !this.listaIdTurnos.isEmpty()) {
                                for (String item : this.listaIdTurnos) {
                                    TurnoMedico turnoMedico = new TurnoMedico();
                                    turnoMedico.setIdTurnoMedico(Comunes.getUUID());
                                    turnoMedico.setIdTurno(Integer.valueOf(item));
                                    turnoMedico.setIdMedico(this.usuarioSelect.getIdUsuario());
                                    listaTemp.add(turnoMedico);
                                }

                            }
                            List<UsuarioImpresora> listaTempImpr = new ArrayList<>();
                            if (this.listaIdImpresoras != null || !this.listaIdImpresoras.isEmpty()) {
                                for (String id : this.listaIdImpresoras) {
                                    UsuarioImpresora usuarioImpresora = new UsuarioImpresora();
                                    usuarioImpresora.setIdUsuario(this.usuarioSelect.getIdUsuario());
                                    usuarioImpresora.setIdImpresora(id);
                                    usuarioImpresora.setInsertFecha(new Date());
                                    usuarioImpresora.setInsertIdUsuario(currentSesionUser.getIdUsuario());
                                    listaTempImpr.add(usuarioImpresora);
                                }
                            }
                            UsuarioRol nrol = new UsuarioRol();
                            if (usuarioRolSelect != null) {
                                nrol.setIdUsuario(usuarioSelect.getIdUsuario());
                                nrol.setIdRol(usuarioRolSelect.getIdRol());
                                nrol.setInsertFecha(new Date());
                                nrol.setInsertIdUsuario(currentSesionUser.getIdUsuario());
                            }

                            if (Objects.equals(this.usuarioSelect.getIdTipoUsuario(), TipoUsuario_Enum.MEDICO.getValue())) {
                                this.usuarioSelect.setPrescribeControlados(this.prescribeControlados);
                            } else {
                                this.usuarioSelect.setPrescribeControlados(false);
                            }
                            for (String idPerfil : this.listaIdPerfiles) {
                                if (Integer.valueOf(idPerfil).equals(TipoPerfilUsuario_Enum.ADMIN.getIdTipoPerfil())) {
                                    usuarioSelect.setAdministrador(Constantes.ADMINISTRADOR);
                                }
                                if (Integer.valueOf(idPerfil).equals(TipoPerfilUsuario_Enum.JEFE_AREA.getIdTipoPerfil())) {
                                    usuarioSelect.setIdTipoPerfil(Constantes.JEFE_AREA);
                                }
                            }
                            String idTiposSoluciones = "";
                            for (String idTipoSolucion : this.listaIdTipoSolucionSelect) {
                                if(idTiposSoluciones == "") {
                                    idTiposSoluciones = idTipoSolucion;
                                } else {
                                    idTiposSoluciones = idTiposSoluciones + ", " + idTipoSolucion;
                                }
                            }
                            if (validaDatosUser(usuarioSelect, nrol)) {

                                usuarioRolService.insertar(nrol);
                                usuarioSelect.setClaveAcceso(CustomWebSecurityConfigurerAdapter.argon2Encode(usuarioSelect.getClaveAcceso()));
                                usuarioService.insertarUser(usuarioSelect);
                                if (!listaTemp.isEmpty()) {
                                    turnoMedicoService.insertarListaTurnos(listaTemp);
                                }
                                if (!listaTempImpr.isEmpty()) {
                                    usuarioImpresoraService.insertarListaImpresoras(listaTempImpr);
                                }
                                Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("user.info.ok.guardado"), null);
                                status = Constantes.ACTIVO;
                                obtenerListaUsuarios();

                            } else {                                
                                usuarioSelect.setIdUsuario(null);                                
                            }

                        } else {
                            usuarioSelect.setClaveAcceso(null);
                            usuarioSelect.setUpdateFecha(new Date());
                            usuarioSelect.setUpdateIdUsuario(currentSesionUser.getIdUsuario());
                            usuarioSelect.setIdEstructura(obtenerIdEstructura());
                            this.usuarioSelect.setPrescribeControlados(this.prescribeControlados);
                            String idTiposSoluciones = "";
                            for (String idTipoSolucion : this.listaIdTipoSolucionSelect) {
                                if(idTiposSoluciones == "") {
                                    idTiposSoluciones = idTipoSolucion;
                                } else {
                                    idTiposSoluciones = idTiposSoluciones + ", " + idTipoSolucion;
                                }
                            }
                            this.usuarioSelect.setIdTipoSolucion(idTiposSoluciones);
                            UsuarioRol nrol = new UsuarioRol();
                            if (usuarioRolSelect != null) {                                
                                nrol.setIdUsuario(usuarioSelect.getIdUsuario());

                                UsuarioRol existe = usuarioRolService.obtener(nrol);
                                if (existe != null) {
                                    nrol.setIdRol(usuarioRolSelect.getIdRol());
                                    nrol.setUpdateFecha(new Date());
                                    nrol.setUpdateIdUsuario(currentSesionUser.getIdUsuario());
                                    usuarioRolService.actualizar(nrol);
                                } else {
                                    nrol.setIdRol(usuarioRolSelect.getIdRol());
                                    nrol.setInsertFecha(new Date());
                                    nrol.setInsertIdUsuario(currentSesionUser.getIdUsuario());
                                    usuarioRolService.insertar(nrol);

                                }

                            }
                            usuarioSelect.setAdministrador(Constantes.NO_ADMINISTRADOR);
                            usuarioSelect.setIdTipoPerfil(Constantes.NO_JEFE_AREA);
                            for (String idPerfil : this.listaIdPerfiles) {
                                if (Integer.valueOf(idPerfil).equals(TipoPerfilUsuario_Enum.ADMIN.getIdTipoPerfil())) {
                                    usuarioSelect.setAdministrador(Constantes.ADMINISTRADOR);
                                }
                                if (Integer.valueOf(idPerfil).equals(TipoPerfilUsuario_Enum.JEFE_AREA.getIdTipoPerfil())) {
                                    usuarioSelect.setIdTipoPerfil(Constantes.JEFE_AREA);
                                }
                            }

                            if (usuarioSelect.getIdTipoUsuario().equals(Constantes.ID_TIPO_USUARIO)
                                    && usuarioSelect.getCedProfesional().equals("")) {
                                status = Constantes.INACTIVO;
                                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("user.error.cedProf"), null);
                                return;
                            }
                            if(validaDatosUser(usuarioSelect, nrol)) {
                                if (usuarioService.actualizar(usuarioSelect)) {
                                    List<TurnoMedico> listaTemp = new ArrayList<>();
                                    if (this.listaIdTurnos != null || !this.listaIdTurnos.isEmpty()) {
                                        for (String item : this.listaIdTurnos) {
                                            TurnoMedico turnoMedico = new TurnoMedico();
                                            turnoMedico.setIdTurnoMedico(Comunes.getUUID());
                                            turnoMedico.setIdTurno(Integer.valueOf(item));
                                            turnoMedico.setIdMedico(this.usuarioSelect.getIdUsuario());
                                            listaTemp.add(turnoMedico);
                                        }

                                    }
                                    if (!listaTemp.isEmpty()) {
                                        turnoMedicoService.actualizarListaTurnos(listaTemp, this.usuarioSelect.getIdUsuario());
                                    }
                                    List<UsuarioImpresora> listaTempImpr = new ArrayList<>();
                                    if (this.listaIdImpresoras != null || !listaIdImpresoras.isEmpty()) {
                                        for (String id : this.listaIdImpresoras) {
                                            UsuarioImpresora usuarioImpresora = new UsuarioImpresora();
                                            usuarioImpresora.setIdUsuario(this.usuarioSelect.getIdUsuario());
                                            usuarioImpresora.setIdImpresora(id);
                                            usuarioImpresora.setInsertIdUsuario(currentSesionUser.getIdUsuario());
                                            usuarioImpresora.setInsertFecha(new Date());
                                            usuarioImpresora.setUpdateFecha(new Date());
                                            usuarioImpresora.setUpdateIdUsuario(currentSesionUser.getIdUsuario());
                                            listaTempImpr.add(usuarioImpresora);
                                        }
                                    }
                                    usuarioImpresoraService.actualizarListaImpresoras(listaTempImpr, this.usuarioSelect.getIdUsuario());
                                }
                                obtenerListaUsuarios();
                                status = Constantes.ACTIVO;
                                Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("user.error.ok.guardado"), null);
                                usuarioSelect = new Usuario();
                            }
                        }
                    } else {
                        status = Constantes.INACTIVO;
                    }
                }
            }
        } catch (Exception ex1) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.guardarUsuario") + ex1.getMessage(), null);
            LOGGER.error("Error al Guardar Usuario: {}", ex1.getMessage());
        }

        if (status) {
            message = Constantes.ACTIVO;
        }
        PrimeFaces.current().ajax().addCallbackParam("estatus1", status);
    }

    /**
     * Modifica el estatus del usuario
     *
     * @param idUsuario
     * @param status
     */
    public void statusUsuario(String idUsuario, boolean status) {
        LOGGER.trace("mx.mc.magedbean.UsuariosMB.statusUsuario()");
        message = Constantes.ACTIVO;
        try {
            if (permiso.isPuedeEditar()) {
                Usuario usr = new Usuario();
                usr.setIdUsuario(idUsuario);
                usr.setUpdateIdUsuario(currentSesionUser.getIdUsuario());
                usr.setUpdateFecha(new Date());
                if (status) {
                    usr.setActivo(Constantes.INACTIVO);
                } else {
                    usr.setActivo(Constantes.ACTIVO);
                }
                if (usuarioService.actualizar(usr)) {
                    for (VistaUsuario view : usuarioList) {
                        if (view.getIdUsuario().equals(idUsuario)) {
                            view.setActivo(usr.isActivo());
                            break;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al actualizar status Medicamento: {}", ex.getMessage());
        }
    }

    /**
     * Cambia la contraseña de usuario
     */
    public void changePasswordUsuario() {
        LOGGER.trace("mx.mc.magedbean.UsuariosMB.changePasswordUsuario()");
        boolean exito = Constantes.ACTIVO;
        try {
            if (permiso.isPuedeEditar()) {
                if (usuarioSelect != null) {
                    Usuario user = new Usuario();
                    user.setClaveAcceso("");
                    user.setIdUsuario(usuarioSelect.getIdUsuario());
                    user.setUpdateFecha(new Date());
                    user.setUpdateIdUsuario(currentSesionUser.getIdUsuario());
                    user.setActivo(usuarioSelect.isActivo());
                    user.setNumErrorAcceso(0);
                    
                    exito = validaDatosClave(usuarioSelect);
                    if (exito) {
                        user.setUsuarioBloqueado(usuarioSelect.isUsuarioBloqueado());
                        user.setClaveAcceso(CustomWebSecurityConfigurerAdapter.argon2Encode(usuarioSelect.getClaveAcceso()));
                        usuarioService.actualizar(user);
                        status = Constantes.ACTIVO;
                    }
                } else {
                    status = Constantes.INACTIVO;
                }
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam("estatus2", status);

    }

    public void assignAreaUsuario() {
        try {
            if (permiso.isPuedeEditar()) {
                if (usuarioSelect != null) {
                    if (nameUnidad != null) {
                        Usuario user = new Usuario();
                        user.setIdUsuario(usuarioSelect.getIdUsuario());
                        user.setIdEstructura(obtenerIdEstructura());
                        user.setPathEstructura(usuarioSelect.getPathEstructura());
                        user.setActivo(usuarioSelect.isActivo());
                        user.setUpdateFecha(new Date());
                        user.setUpdateIdUsuario(currentSesionUser.getIdUsuario());

                        usuarioService.actualizar(user);
                        for (VistaUsuario aux : usuarioList) {
                            if (aux.getIdUsuario().equals(user.getIdUsuario())) {
                                if (!nameUnidad.isEmpty()) {
                                    aux.setEstructura(nameUnidad);
                                    aux.setPathEstructura(user.getPathEstructura());
                                }
                                break;
                            }
                        }
                        status = Constantes.ACTIVO;
                    }
                } else {
                    status = Constantes.INACTIVO;
                }
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam("estatus3", status);
    }

    public TipoUsuarioService getTipoUsuarioService() {
        return tipoUsuarioService;
    }

    public void setTipoUsuarioService(TipoUsuarioService tipoUsuarioService) {
        this.tipoUsuarioService = tipoUsuarioService;
    }

    public TipoUsuario getTipoUsuarioSelect() {
        return tipoUsuarioSelect;
    }

    public boolean isMessage() {
        return message;
    }

    /**
     * Carga layouts de usuarios
     *
     * @param event
     */
    public void layoutFileUpload(FileUploadEvent event) {
        LOGGER.trace("mx.mc.magedbean.UsuariosMB.layoutFileUpload()");
        try {
            message = Constantes.INACTIVO;
            UploadedFile upfile = event.getFile();
            String name = upfile.getFileName();
            String ext = name.substring(name.lastIndexOf('.'), name.length());
            String excelFilePath = createFile(upfile.getContents(), name);
            switch (ext) {
                case ".xlsx":
                    readLayout2007(excelFilePath);
                    break;
                case ".xls":
                    readLayout2003(excelFilePath);
                    break;
                default:
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.formatIncorrecto"), null);
                    break;
            }
            if (!usuarioLayout.isEmpty()) {
                noProcess = Constantes.ACTIVO;
            }

        } catch (Exception ex) {
            LOGGER.error("ERROR en layoutFileUpload: {}", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("usuario.err.formatIncorrecto"), null);
            noProcess = Constantes.INACTIVO;

        }
    }

    public void actualizaTablaUser() {
        try {
            cadenaBusqueda = null;
            usuarioList = usuarioService.obtenerVista(cadenaBusqueda);
            usuarioList2 = usuarioList;
            numeroRegistros = usuarioList.size();
        } catch (Exception e) {
            LOGGER.error("Error al buscar Medicamentos: {}", e.getMessage());
        }
    }

    public void validaUser() {
        try {
            Usuario user;
            if (usuarioSelect != null && !usuarioSelect.getNombreUsuario().equals("")) {
                user = usuarioService.getUserByUserName(usuarioSelect.getNombreUsuario());
                if (user != null) {
                    if (usuarioSelect.getIdUsuario() != null) {
                        if (!user.getIdUsuario().equals(usuarioSelect.getIdUsuario()) && user.getNombreUsuario().equals(usuarioSelect.getNombreUsuario())) {
                            message = Constantes.INACTIVO;
                            Mensaje.showMessage("Warn", RESOURCES.getString("usuario.warn.existeUsuario"), null);
                        }
                    } else {
                        message = Constantes.INACTIVO;
                        Mensaje.showMessage("Warn", RESOURCES.getString("usuario.warn.existeUsuario"), null);
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al insertar el Usuario: {}", ex.getMessage());
        }
    }

    public void validaMail() {
        try {
            Usuario user;
            if (usuarioSelect != null && !usuarioSelect.getCorreoElectronico().equals("")) {
                user = usuarioService.getUserByEmail(usuarioSelect.getCorreoElectronico());
                if (user != null) {
                    if (usuarioSelect.getIdUsuario() != null) {
                        if (!user.getIdUsuario().equals(usuarioSelect.getIdUsuario()) && user.getCorreoElectronico().equals(usuarioSelect.getCorreoElectronico())) {
                            message = Constantes.INACTIVO;
                            Mensaje.showMessage("Warn", RESOURCES.getString("usuario.warn.existeCorreo"), null);
                        }
                    } else {
                        message = Constantes.INACTIVO;
                        Mensaje.showMessage("Warn", RESOURCES.getString("usuario.warn.existeCorreo"), null);
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al insertar el Email: {}", ex.getMessage());
        }
    }

    @Deprecated
    public void habilitaPrescribeControlados() {
        this.esMedico = Objects.equals(this.usuarioSelect.getIdTipoUsuario(), TipoUsuario_Enum.MEDICO.getValue());
    }

    /*
    Section Getters & Setters
     */
    public Pattern getRegexMail() {
        return regexMail;
    }

    public void setRegexMail(Pattern regexMail) {
        this.regexMail = regexMail;
    }

    public Pattern getRegexUser() {
        return regexUser;
    }

    public void setRegexUser(Pattern regexUser) {
        this.regexUser = regexUser;
    }

    public Pattern getRegexNomAp() {
        return regexNomAp;
    }

    public void setRegexNomAp(Pattern regexNomAp) {
        this.regexNomAp = regexNomAp;
    }

    public Pattern getRegexClave() {
        return regexClave;
    }

    public void setRegexClave(Pattern regexClave) {
        this.regexClave = regexClave;
    }

    public String getPadre() {
        return padre;
    }

    public void setPadre(String padre) {
        this.padre = padre;
    }

    public String getHijo() {
        return hijo;
    }

    public void setHijo(String hijo) {
        this.hijo = hijo;
    }

    public UsuarioRol getUsuarioRolLayout() {
        return usuarioRolLayout;
    }

    public void setUsuarioRolLayout(UsuarioRol usuarioRolLayout) {
        this.usuarioRolLayout = usuarioRolLayout;
    }

    public boolean isNoProcess() {
        return noProcess;
    }

    public void setNoProcess(boolean noProcess) {
        this.noProcess = noProcess;
    }

    public String getPathDefinition() {
        return pathDefinition;
    }

    public void setPathDefinition(String pathDefinition) {
        this.pathDefinition = pathDefinition;
    }

    public String getFileImg() {
        return fileImg;
    }

    public void setFileImg(String fileImg) {
        this.fileImg = fileImg;
    }

    public String getNameFile() {
        return nameFile;
    }

    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public SimpleDateFormat getSdf() {
        return sdf;
    }

    public void setSdf(SimpleDateFormat sdf) {
        this.sdf = sdf;
    }

    public String getIdRol() {
        return idRol;
    }

    public void setIdRol(String idRol) {
        this.idRol = idRol;
    }

    public EstructuraService getEstructuraService() {
        return estructuraService;
    }

    public void setEstructuraService(EstructuraService estructuraService) {
        this.estructuraService = estructuraService;
    }

    public Estructura getEstructuraSelect() {
        return estructuraSelect;
    }

    public void setEstructuraSelect(Estructura estructuraSelect) {
        this.estructuraSelect = estructuraSelect;
    }

    public List<Usuario> getUsuarioLayout() {
        return usuarioLayout;
    }

    public void setUsuarioLayout(List<Usuario> usuarioLayout) {
        this.usuarioLayout = usuarioLayout;
    }

    public Estructura getEstructura() {
        return estructura;
    }

    public void setEstructura(Estructura estructura) {
        this.estructura = estructura;
    }

    public List<Estructura> getListEstructura() {
        return listEstructura;
    }

    public void setListEstructura(List<Estructura> listEstructura) {
        this.listEstructura = listEstructura;
    }

    public void setMessage(boolean message) {
        this.message = message;
    }

    public void setTipoUsuarioSelect(TipoUsuario tipoUsuarioSelect) {
        this.tipoUsuarioSelect = tipoUsuarioSelect;
    }

    public List<TipoUsuario> getTipoUserList() {
        return tipoUserList;
    }

    public void setTipoUserList(List<TipoUsuario> tipoUserList) {
        this.tipoUserList = tipoUserList;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public String getNameUnidad() {
        return nameUnidad;
    }

    public List<Rol> getRolList() {
        return rolList;
    }

    public void setRolList(List<Rol> rolList) {
        this.rolList = rolList;
    }

    public RolService getRolService() {
        return rolService;
    }

    public void setRolService(RolService rolService) {
        this.rolService = rolService;
    }

    public Rol getRolSelect() {
        return rolSelect;
    }

    public void setRolSelect(Rol rolSelect) {
        this.rolSelect = rolSelect;
    }

    public UsuarioRol getUsuarioRolSelect() {
        return usuarioRolSelect;
    }

    public void setUsuarioRolSelect(UsuarioRol usuarioRolSelect) {
        this.usuarioRolSelect = usuarioRolSelect;
    }

    public String getPathNode() {
        return pathNode;
    }

    public void setPathNode(String pathNode) {
        this.pathNode = pathNode;
    }

    public void setNameUnidad(String nameUnidad) {
        this.nameUnidad = nameUnidad;
    }

    public TreeNode getSelectNode() {
        return selectNode;
    }

    public void setSelectNode(TreeNode selectNode) {
        this.selectNode = selectNode;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode nodes) {
        this.root = nodes;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getCadenaBusqueda() {
        return cadenaBusqueda;
    }

    public void setCadenaBusqueda(String cadenaBusqueda) {
        this.cadenaBusqueda = cadenaBusqueda;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isBtnNew() {
        return btnNew;
    }

    public void setBtnNew(boolean btnNew) {
        this.btnNew = btnNew;
    }

    public boolean isRndSave() {
        return rndSave;
    }

    public void setRndSave(boolean rndSave) {
        this.rndSave = rndSave;
    }

    public boolean isRndEdit() {
        return rndEdit;
    }

    public void setRndEdit(boolean rndEdit) {
        this.rndEdit = rndEdit;
    }

    public int getNumeroRegistros() {
        return numeroRegistros;
    }

    public void setNumeroRegistros(int numeroRegistros) {
        this.numeroRegistros = numeroRegistros;
    }

    public List<VistaUsuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<VistaUsuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    public UsuarioService getUsuarioService() {
        return usuarioService;
    }

    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public Usuario getUsuarioSelect() {
        return usuarioSelect;
    }

    public void setUsuarioSelect(Usuario usuarioSelect) {
        this.usuarioSelect = usuarioSelect;
    }

    public List<String> getListaIdTurnos() {
        return listaIdTurnos;
    }

    public void setListaIdTurnos(List<String> listaIdTurnos) {
        this.listaIdTurnos = listaIdTurnos;
    }

    public List<Turno> getListaTurnos() {
        return listaTurnos;
    }

    public void setListaTurnos(List<Turno> listaTurnos) {
        this.listaTurnos = listaTurnos;
    }

    public List<String> getListaIdImpresoras() {
        return listaIdImpresoras;
    }

    public void setListaIdImpresoras(List<String> listaIdImpresoras) {
        this.listaIdImpresoras = listaIdImpresoras;
    }

    public List<Impresora> getListaImpresoras() {
        return listaImpresoras;
    }

    public void setListaImpresoras(List<Impresora> listaImpresoras) {
        this.listaImpresoras = listaImpresoras;
    }

    public UsuariosLazy getUsuariosLazy() {
        return usuariosLazy;
    }

    public void setUsuariosLazy(UsuariosLazy usuariosLazy) {
        this.usuariosLazy = usuariosLazy;
    }

    public ParamBusquedaReporte getParamBusquedaReporte() {
        return paramBusquedaReporte;
    }

    public void setParamBusquedaReporte(ParamBusquedaReporte paramBusquedaReporte) {
        this.paramBusquedaReporte = paramBusquedaReporte;
    }

    public Boolean getPrescribeControlados() {
        return prescribeControlados;
    }

    public void setPrescribeControlados(Boolean prescribeControlados) {
        this.prescribeControlados = prescribeControlados;
    }

    public Boolean getEsMedico() {
        return esMedico;
    }

    public void setEsMedico(Boolean esMedico) {
        this.esMedico = esMedico;
    }

    public boolean isCheckBoxImpresora() {
        return checkBoxImpresora;
    }

    public void setCheckBoxImpresora(boolean checkBoxImpresora) {
        this.checkBoxImpresora = checkBoxImpresora;
    }

    public List<String> getListaIdPerfiles() {
        return listaIdPerfiles;
    }

    public void setListaIdPerfiles(List<String> listaIdPerfiles) {
        this.listaIdPerfiles = listaIdPerfiles;
    }

    public List<TipoPerfilUsuario> getListaPerfiles() {
        return listaPerfiles;
    }

    public void setListaPerfiles(List<TipoPerfilUsuario> listaPerfiles) {
        this.listaPerfiles = listaPerfiles;
    }

    public boolean isMostrarBloqueoUsuario() {
        return mostrarBloqueoUsuario;
    }

    public void setMostrarBloqueoUsuario(boolean mostrarBloqueoUsuario) {
        this.mostrarBloqueoUsuario = mostrarBloqueoUsuario;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

    public String getConfirmacion() {
        return confirmacion;
    }

    public void setConfirmacion(String confirmacion) {
        this.confirmacion = confirmacion;
    }   

    public Integer getPasswordNumCaracter() {
        return passwordNumCaracter;
    }

    public void setPasswordNumCaracter(Integer passwordNumCaracter) {
        this.passwordNumCaracter = passwordNumCaracter;
    }
    
    public List<TipoSolucion> getTipoSolucionList() {
        return tipoSolucionList;
    }
    
    public void setTipoSolucionList(List<TipoSolucion> tipoSolucionList) {
        this.tipoSolucionList = tipoSolucionList;
    }
    
    public TipoSolucion getTipoSolucionSelect() {
        return tipoSolucionSelect;
}

    public void setTipoSolucionSelect(TipoSolucion tipoSolucionSelect) {
        this.tipoSolucionSelect = tipoSolucionSelect;
    }

    public List<String> getListaIdTipoSolucionSelect() {
        return listaIdTipoSolucionSelect;
    }

    public void setListaIdTipoSolucionSelect(List<String> listaIdTipoSolucionSelect) {
        this.listaIdTipoSolucionSelect = listaIdTipoSolucionSelect;
    }
    
}