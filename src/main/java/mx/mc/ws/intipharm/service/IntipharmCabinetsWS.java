/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.ws.intipharm.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import org.jdom2.Element;
import org.jdom2.Document;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import mx.mc.enums.EstatusMinistracion_Enum;
import mx.mc.enums.EstatusSurtimiento_Enum;
import mx.mc.enums.TipoEntradaSalida_Enum;
import mx.mc.enums.TipoMensaje_Enum;
import mx.mc.enums.TipoMotivo_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.BitacoraMensaje;
import mx.mc.model.Estructura;
import mx.mc.model.IntipharmItem;
import mx.mc.model.IntipharmMedicine;
import mx.mc.model.IntipharmPrescription;
import mx.mc.model.IntipharmPrescriptionDispense;
import mx.mc.model.IntipharmReabasto;
import mx.mc.model.IntipharmSurtimiento;
import mx.mc.model.IntipharmRespuesta;
import mx.mc.model.IntipharmVerfStock;
import mx.mc.model.Inventario;
import mx.mc.model.Medicamento;
import mx.mc.model.MovimientoInventario;
import mx.mc.model.Reabasto;
import mx.mc.model.Surtimiento;
import mx.mc.model.SurtimientoEnviado;
import mx.mc.model.SurtimientoInsumo;
import mx.mc.model.SurtimientoInsumo_Extend;
import mx.mc.model.SurtimientoMinistrado;
import mx.mc.model.Usuario;
import mx.mc.service.EstructuraService;
import mx.mc.service.InventarioService;
import mx.mc.service.MedicamentoService;
import mx.mc.service.PrescripcionService;
import mx.mc.service.ReabastoService;
import mx.mc.service.SurtimientoInsumoService;
import mx.mc.service.SurtimientoService;
import mx.mc.service.UsuarioService;
import mx.mc.util.Comunes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import mx.mc.service.BitacoraMensajeService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import mx.mc.enums.CatalogoGeneral_Enum;
import mx.mc.enums.EstatusGabinete_Enum;
import mx.mc.enums.EstatusPrescripcion_Enum;
import mx.mc.enums.GrupoCatalogoGeneral_Enum;
import mx.mc.enums.MotivoPacienteMovimiento_Enum;
import mx.mc.enums.TipoConsulta_Enum;
import mx.mc.model.Config;
import mx.mc.model.DispensacionDirecta;
import mx.mc.model.EstrucAlmacenServicio_Extend;
import mx.mc.model.InventarioExtended;
import mx.mc.model.Paciente;
import mx.mc.model.PacienteServicio;
import mx.mc.model.Prescripcion;
import mx.mc.model.PrescripcionInsumo;
import mx.mc.model.Visita;
import mx.mc.service.ConfigService;
import mx.mc.service.DispensacionDirectaService;
import mx.mc.service.EstructuraAlmacenServicioService;
import mx.mc.service.PacienteService;
import mx.mc.service.PacienteServicioService;
import mx.mc.service.VisitaService;
import mx.mc.util.Mensaje;

/**
 *
 * @author mcalderon
 */
@WebService(serviceName = "IntipharmCabinetsWS")
@Controller
@Scope(value = "view")
public class IntipharmCabinetsWS extends SpringBeanAutowiringSupport implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(IntipharmCabinetsWS.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    @Autowired
    private transient UsuarioService usuarioService;

    @Autowired
    private transient BitacoraMensajeService bitacoraService;

    @Autowired
    private transient SurtimientoService surtimientoService;

    @Autowired
    private transient MedicamentoService medicamentoService;

    @Autowired
    private transient PrescripcionService prescripcionService;

    @Autowired
    private transient SurtimientoInsumoService surtimientoInsumoService;

    @Autowired
    private transient EstructuraService estructuraService;

    @Autowired
    private transient ReabastoService reabastoService;

    @Autowired
    private transient InventarioService inventarioService;

    @Autowired
    private transient DispensacionDirectaService dispensacionDirectaService;

    @Autowired
    private transient VisitaService visitaService;

    @Autowired
    private transient PacienteService pacienteService;

    @Autowired
    private transient PacienteServicioService pacienteServicioService;

    @Autowired
    private transient EstructuraAlmacenServicioService estructuraAlmacenServicioService;

    @Autowired
    private transient ConfigService configService;

    /**
     *
     * @return retorna la lista de surtimientos de las prescripciones.
     */
    @WebMethod(operationName = "SelectCabiDrgOrdList")
    @WebResult(name = "IntipharmPrescription")
    public ArrayList<IntipharmPrescription> selectCabiDrgOrdList1() {

        ArrayList<IntipharmPrescription> presIntiPharm = new ArrayList<>();
        try {
            presIntiPharm = prescripcionService.selectCabiDrgOrdList();

            for (IntipharmPrescription item : presIntiPharm) {
                item.setMedicacion(prescripcionService.selectCabiDrgOrdListMedication(item.getFolioSurtimiento()));
            }

        } catch (Exception e) {
            LOGGER.error("Error en el metodo SelectCabiDrgOrdList1: {}", e.getMessage());
        }

        return presIntiPharm;
    }

    private Usuario generarUsuarioGenerio(String nombre) {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(Comunes.getUUID());
        usuario.setNombre(nombre);
        usuario.setNombreUsuario(nombre);
        generarClaveCorreo(usuario);
        usuario.setFechaVigencia(generateDate(new java.util.Date(), Constantes.ANIOS_VIGENCIA_USUARIO));
        usuario.setInsertFecha(new java.util.Date());
        usuario.setActivo(true);
        usuario.setUsuarioBloqueado(false);
        usuario.setInsertIdUsuario(usuario.getIdUsuario());
        return usuario;
    }

    private boolean validaDatosUpdate(String folio, String estatusSurtimiento, Integer numeroMedicacion, StringBuilder valsError, Reabasto reabasto){
        boolean val = false;
        if (folio == null || folio.equals("")) {
            valsError.append(RESOURCES.getString("intiPharm.err.folioVacio")).append("\n");
            val = true;
        } else {
            if (estatusSurtimiento == null || estatusSurtimiento.equals("")) {
                valsError.append(RESOURCES.getString("intiPharm.err.estatusVacio")).append("\n");
                val = true;
            } else if (estatusSurtimiento.equalsIgnoreCase(Constantes.COMPLETE)
                    && (numeroMedicacion == null || numeroMedicacion == 0)) {
                valsError.append(RESOURCES.getString("intiPharm.err.numeroMedicacion"));
                val = true;
            }
        }
        return val;
    }

    /**
     *
     * @param folio Este parametro es el folio de la Prescripción
     * @param estatusSurtimiento El estatus de la prescripción
     * @param numeroMedicacion
     * @return retorna los 10 primeras prescripciones con sus surtimientos
     * @throws Exception
     */
    @WebMethod(operationName = "UpdateCabiDrgOrdStus")
    @WebResult(name = "Respuesta")
    public IntipharmRespuesta updateCabiDrgOrdStus(@WebParam(name = "folio") String folio, @WebParam(name = "estatusSurtimiento") String estatusSurtimiento, @WebParam(name = "numeroMedicacion") Integer numeroMedicacion) throws Exception {
        LOGGER.debug("folio: {}, estatusSurtimiento: {}, numeroMedicacion: {}", folio, estatusSurtimiento, numeroMedicacion);
        IntipharmRespuesta respuesta = new IntipharmRespuesta();
        StringBuilder valsError = new StringBuilder();
        boolean existeUsuario = true;

        try {

            Reabasto reabasto = new Reabasto();

            boolean result = validaDatosUpdate(folio, estatusSurtimiento, numeroMedicacion, valsError, reabasto);

            if (result) {
                respuesta.setError(true);
                respuesta.setMensaje(valsError.toString());
                //Se sustituira el mensaje de error por un mensaje correcto, ya que se esta recibiendo este valor demasiadas veces y es mejor cambiarlo de nuestro lado a que lo cambie Mr.Park.
                if (estatusSurtimiento.equalsIgnoreCase(Constantes.COMPLETE) && numeroMedicacion.equals(0)) {
                    respuesta.setError(false);
                    respuesta.setMensaje(Constantes.MSJ_COMPLETADO);
                    respuesta.setFolio(folio);
                }
            } else {
                respuesta.setFolio(folio);
                Integer idEstatusSurtimiento = null;
                Date updateFecha = new java.util.Date();

                Usuario user = new Usuario();
                user.setNombreUsuario(Constantes.USUARIO_GENERICO);
                user = usuarioService.getUserByUserName(user.getNombreUsuario());
                if (user == null) {
                    existeUsuario = false;
                    user = generarUsuarioGenerio(Constantes.USUARIO_GENERICO);
                }
                boolean actSurtInsumo = false;
                String updateIdUsuario = user.getIdUsuario();

                if (estatusSurtimiento.equalsIgnoreCase(Constantes.RECEIVED)) {
                    numeroMedicacion = 0;
                    actSurtInsumo = true;
                    idEstatusSurtimiento = EstatusSurtimiento_Enum.RECIBIDO.getValue();

                    boolean val = surtimientoService.actualizaSurtimientoRecibidoPorGabinetes(folio, idEstatusSurtimiento, updateIdUsuario, updateFecha, existeUsuario, user, numeroMedicacion, actSurtInsumo);
                    if (val) {
                        respuesta.setError(false);
                        respuesta.setMensaje(Constantes.MSJ_RECIBIDO);
                        respuesta.setFolio(folio);
                    } else {
                        respuesta.setError(true);
                        respuesta.setMensaje(Constantes.MSJ_NO_RECIBIDO);
                        respuesta.setFolio(folio);
                    }

                } else if (estatusSurtimiento.equalsIgnoreCase(Constantes.COMPLETE)) {
                    idEstatusSurtimiento = EstatusSurtimiento_Enum.COMPLETADO.getValue();

                    //busca si aun existe una medicacion con estatus = 4 -> recibido
                    int datoSurInsumo = surtimientoService.obtenerTotalSurtimiento(folio);

                    if (datoSurInsumo == 0) {
                        actSurtInsumo = true;
                    }

                    boolean val = surtimientoService.actualizaSurtimientoRecibidoPorGabinetes(folio, idEstatusSurtimiento, updateIdUsuario, updateFecha, existeUsuario, user, numeroMedicacion, actSurtInsumo);

                    if (val) {

                        datoSurInsumo = surtimientoService.obtenerTotalSurtimiento(folio);
                        if (datoSurInsumo == 0) {
                            actSurtInsumo = true;
                            surtimientoService.actualizaSurtimientoRecibidoPorGabinetes(folio, idEstatusSurtimiento, updateIdUsuario, updateFecha, existeUsuario, user, numeroMedicacion, actSurtInsumo);
                        }

                        String folioPres = prescripcionService.obtenerFolioPrescBySurt(folio);

                        int dato = surtimientoService.obtenerSurtPrescripcion(folioPres);

                        if (dato == 0) {
                            //Metodo para cambiar el estatusPrescripción
                            prescripcionService.actualizarByfolioSurt(folioPres);
                        }

                        respuesta.setError(false);
                        respuesta.setMensaje(Constantes.MSJ_COMPLETADO);
                        respuesta.setFolio(folio);
                    } else {
                        respuesta.setError(true);
                        respuesta.setMensaje(Constantes.MSJ_NO_COMPLETADO);
                        respuesta.setFolio(folio);
                    }
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje(Constantes.ESTATUS_INTIPHARM_INCORRECT);
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            respuesta.setError(true);
            respuesta.setMensaje(e.getMessage());
        }
        return respuesta;
    }

    /**
     *
     * @return Retorna una lista de de medicamentos
     */
    @WebMethod(operationName = "SelectCabiDrgList")
    @WebResult(name = "IntipharmItem")
    public List<IntipharmItem> selectCabiDrgList() {

        List<IntipharmItem> listInthIthem = new ArrayList<>();

        try {
            listInthIthem = medicamentoService.obtenerSelectCabiDrgList();
        } catch (Exception e) {
            LOGGER.error("Error en el metodo SelectCabiDrgList: {}", e.getMessage());
        }
        return listInthIthem;
    }

    //Se genero la clave de acceso y el correo 
    private Usuario generarClaveCorreo(Usuario user) {

        String nombreCorreo = user.getNombreUsuario();
        nombreCorreo = nombreCorreo.replaceAll(" ", "_");

        int valor = 0;
        String valCorreo = Constantes.DOMINIO_CORREO;
        valor = (int) (100000 * Math.random());
        String cadena = String.valueOf(valor);
        user.setClaveAcceso(nombreCorreo.concat(cadena));
        user.setCorreoElectronico(nombreCorreo.concat(valCorreo));
        return user;
    }

    //Se genera una fecha de vigencia, la cual es la fecha actual + 5 años
    private Date generateDate(Date fecha, int anio) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.YEAR, anio);
        return calendar.getTime();
    }

    private Usuario validarUsuario(IntipharmReabasto prescriptionDispensed) {

        //Verificar los datos que faltan, ya que algunos no deben de ir NULOS.
        Usuario userNew = new Usuario();
        userNew.setNombreUsuario(prescriptionDispensed.getNombreUsuario());
        userNew.setActivo(true);
        userNew.setUsuarioBloqueado(false);
        userNew.setIdUsuario(Comunes.getUUID());
        userNew.setNombreUsuario(prescriptionDispensed.getNombreUsuario());
        userNew.setNombre(prescriptionDispensed.getNombreUsuario());
        userNew.setCedProfesional(prescriptionDispensed.getNombreUsuario());
        generarClaveCorreo(userNew);
        userNew.setInsertFecha(new java.util.Date());
        userNew.setInsertIdUsuario(userNew.getIdUsuario());

        userNew.setFechaVigencia(generateDate(new java.util.Date(), Constantes.ANIOS_VIGENCIA_USUARIO));

        return userNew;
    }

    private boolean validaCampos(boolean verifValor, IntipharmReabasto prescriptionDispensed, StringBuilder valsError, Estructura codigoArea){
        if (prescriptionDispensed.getFolio() == null || prescriptionDispensed.getFolio().equals("")) {
            verifValor = false;
            valsError.append(RESOURCES.getString("intiPharm.err.folioVacio")).append("\n");
        }
        if (prescriptionDispensed.getNombreUsuario() == null || prescriptionDispensed.getNombreUsuario().equals("")) {
            verifValor = false;
            valsError.append(RESOURCES.getString("intiPharm.err.nombreUsuario")).append("\n");
        }
        if (prescriptionDispensed.getCodigoArea() == null || prescriptionDispensed.getCodigoArea().equals("")) {
            verifValor = false;
            valsError.append(RESOURCES.getString("intiPharm.err.codigoArea")).append("\n");
        }

        if (codigoArea == null) {
            //Se puso el valor de -->   verifValor = true; para que truene para las pruebas
            verifValor = true;
            valsError.append(RESOURCES.getString("intiPharm.err.noExisteCodigoArea")).append("\n");
        }
        return verifValor;
    }

    //Se genera la cedena en format XML, para ingresar a la bitacora.
    private String convertirAXmlRefill(IntipharmReabasto prescriptionDispense) {
        LOGGER.info("Entrando a convertirAXml");
        Document docu = new Document();
        Element channelElement = new Element("IntipharmReabasto");
        docu.setRootElement(channelElement);

        Element folio = new Element("folio");
        if (prescriptionDispense.getFolio() == null) {
            prescriptionDispense.setFolio("NULL");
            folio.addContent(prescriptionDispense.getFolio());
        } else {
            switch (prescriptionDispense.getFolio()) {
                case "":
                    prescriptionDispense.setFolio("VACIO");
                    folio.addContent(prescriptionDispense.getFolio());
                    break;
                default:
                    folio.addContent(prescriptionDispense.getFolio());
                    break;
            }
        }
        channelElement.addContent(folio);

        Element nombreUsuario = new Element("nombreUsuario");
        if (prescriptionDispense.getNombreUsuario() == null) {
            prescriptionDispense.setNombreUsuario("NULL");
            nombreUsuario.addContent(prescriptionDispense.getNombreUsuario());
        } else {
            switch (prescriptionDispense.getNombreUsuario()) {
                case "":
                    prescriptionDispense.setNombreUsuario("VACIO");
                    nombreUsuario.addContent(prescriptionDispense.getNombreUsuario());
                    break;
                default:
                    nombreUsuario.addContent(prescriptionDispense.getNombreUsuario());
                    break;
            }
        }
        channelElement.addContent(nombreUsuario);

        Element codigoArea = new Element("codigoArea");
        if (prescriptionDispense.getCodigoArea() == null) {
            prescriptionDispense.setCodigoArea("NULL");
            codigoArea.addContent(prescriptionDispense.getCodigoArea());
        } else {
            switch (prescriptionDispense.getCodigoArea()) {
                case "":
                    prescriptionDispense.setCodigoArea("VACIO");
                    codigoArea.addContent(prescriptionDispense.getCodigoArea());
                    break;
                default:
                    codigoArea.addContent(prescriptionDispense.getCodigoArea());
                    break;
            }
        }
        channelElement.addContent(codigoArea);

        Element fecha = new Element("fecha");
        if (prescriptionDispense.getFecha() == null) {
            prescriptionDispense.setFecha("NULL");
            fecha.addContent(prescriptionDispense.getFecha());
        } else {
            switch (prescriptionDispense.getFecha()) {
                case "":
                    prescriptionDispense.setFecha("VACIO");
                    fecha.addContent(prescriptionDispense.getFecha());
                    break;
                default:
                    fecha.addContent(prescriptionDispense.getFecha());
                    break;
            }
        }
        channelElement.addContent(fecha);

        if (prescriptionDispense.getListMedicamentos() == null) {
            Element intipharmMedicine = new Element("intipharmMedicine");
            channelElement.addContent(intipharmMedicine);
            IntipharmMedicine medicines = new IntipharmMedicine();
            medicines.setCantidaIngresada(0);
            medicines.setClaveInstitucional("NULL");
            medicines.setFechaCaducidad("NULL");
            medicines.setLote("NULL");
            List<IntipharmMedicine> listMedic = new ArrayList<>();
            listMedic.add(medicines);
            prescriptionDispense.setListMedicamentos(listMedic);
        }

        for (IntipharmMedicine medicine : prescriptionDispense.getListMedicamentos()) {
            Element intipharmMedicine = new Element("intipharmMedicine");
            channelElement.addContent(intipharmMedicine);

            Element claveInstitucional = new Element("claveInstitucional");
            if (medicine.getClaveInstitucional() == null) {
                medicine.setClaveInstitucional("NULL");
                claveInstitucional.addContent(medicine.getClaveInstitucional());
            } else if (medicine.getClaveInstitucional().isEmpty()) {
                medicine.setClaveInstitucional("VACÍO");
                claveInstitucional.addContent(medicine.getClaveInstitucional());
            } else {
                claveInstitucional.addContent(medicine.getClaveInstitucional());
            }
            intipharmMedicine.addContent(claveInstitucional);

            Element lote = new Element("lote");
            if (medicine.getLote() == null) {
                medicine.setLote("NULL");
                lote.addContent(medicine.getLote());
            } else if (medicine.getLote().isEmpty()) {
                medicine.setLote("VACÍO");
                lote.addContent(medicine.getLote());
            } else {
                lote.addContent(medicine.getLote());
            }
            intipharmMedicine.addContent(lote);

            Element fechaCaducidad = new Element("fechaCaducidad");
            if (medicine.getFechaCaducidad() == null) {
                medicine.setFechaCaducidad("NULL");
                fechaCaducidad.addContent(medicine.getFechaCaducidad());
            } else if (medicine.getFechaCaducidad().isEmpty()) {
                medicine.setFechaCaducidad("VACÍO");
                fechaCaducidad.addContent(medicine.getFechaCaducidad());
            } else {
                fechaCaducidad.addContent(medicine.getFechaCaducidad());
            }
            intipharmMedicine.addContent(fechaCaducidad);

            Element cantidadIngresada = new Element("cantidadIngresada");
            String cantidad = Integer.toString(medicine.getCantidaIngresada());
            cantidadIngresada.addContent(cantidad);
            intipharmMedicine.addContent(cantidadIngresada);

        }

        return new XMLOutputter(Format.getPrettyFormat()).outputString(docu);
    }

    private String insertaBitacoraRefill(IntipharmReabasto prescriptionDispensed, String idMensaje) throws Exception {
        LOGGER.info("Entrando a insertaBitacora Refilll");
        String idBitacora = null;
        try {
            BitacoraMensaje bitaMensaje = new BitacoraMensaje();
            bitaMensaje.setIdBitacoraMensaje(Comunes.getUUID());
            bitaMensaje.setFecha(new java.util.Date());
            bitaMensaje.setTipoMensaje(TipoMensaje_Enum.INTIPHARM.getValue());
            bitaMensaje.setEntradaSalida(TipoEntradaSalida_Enum.ENTRADA.getValue());
            bitaMensaje.setIdMensaje(idMensaje);

            String cadenaXml = convertirAXmlRefill(prescriptionDispensed);

            bitaMensaje.setMensaje(cadenaXml);
            bitaMensaje.setCodigoRespuesta(RESOURCES.getString("intiPharm.info.refillIncorrecto"));
            boolean insertar = bitacoraService.insertar(bitaMensaje);
            if (insertar) {
                idBitacora = bitaMensaje.getIdBitacoraMensaje();
            }
        } catch (Exception e) {
            LOGGER.info("Error en insertaBitacora del Refill: {}", e.getMessage());
            throw new Exception("Error al Insertar la BitacoraMensaje del Refill");
        }
        return idBitacora;
    }

    /**
     * Genera el idMensaje de la bitacora
     *
     * @param prescriptionDispensed
     * @return
     */
    private String generaIdMensajeRefill(IntipharmReabasto prescriptionDispensed) {
        String cabezera = prescriptionDispensed.getCodigoArea().concat(prescriptionDispensed.getFolio()).concat(prescriptionDispensed.getFecha()).concat(prescriptionDispensed.getNombreUsuario());
        List<String> valorMensaje = new ArrayList<>();
        String clave;
        String fechaCaducidad;
        String lote;
        int cantidad;
        for (IntipharmMedicine medicine : prescriptionDispensed.getListMedicamentos()) {
            clave = medicine.getClaveInstitucional();
            fechaCaducidad = medicine.getFechaCaducidad();
            lote = medicine.getLote();
            cantidad = medicine.getCantidaIngresada();
            String mensaje = clave.concat(fechaCaducidad).concat(lote).concat(Integer.toString(cantidad));
            valorMensaje.add(mensaje);
        }
        return cabezera.concat(valorMensaje.toString());
    }

    /**
     *
     * @param prescriptionDispensed
     * @return
     * @throws java.lang.Exception
     */
    @WebMethod(operationName = "InsertForRefill")
    @WebResult(name = "respuesta")
    public IntipharmRespuesta insertForRefill(@WebParam(name = "prescriptionDispensed") IntipharmReabasto prescriptionDispensed) throws Exception {
        IntipharmRespuesta respuesta = new IntipharmRespuesta();

        try {

            String idMensaje = generaIdMensajeRefill(prescriptionDispensed);

            BitacoraMensaje bita = bitacoraService.validaExistenciaBitacora(idMensaje);
            if (bita != null) {
                respuesta.setFolio(prescriptionDispensed.getFolio());
                respuesta.setError(false);
                respuesta.setMensaje(RESOURCES.getString("intiPharm.info.refillCorrecto"));
            } else {

                String idBitacoraMensActualizar = insertaBitacoraRefill(prescriptionDispensed, idMensaje);

                if (prescriptionDispensed != null) {
                    boolean verifValor = true;
                    StringBuilder valsError = new StringBuilder();
                    Matcher mat;

                    Estructura codigoArea;
                    codigoArea = estructuraService.getEstructuraForClave(prescriptionDispensed.getCodigoArea());

                    if (validaCampos(verifValor, prescriptionDispensed, valsError, codigoArea)) {
                        Date fecha = new java.util.Date();

                        if (prescriptionDispensed.getFecha().equals("VACIO") || prescriptionDispensed.getFecha().equals("NULL")) {
                            fecha = new java.util.Date();
                        } else {
                            mat = Constantes.fechaFormatoRefill.matcher(prescriptionDispensed.getFecha());
                            if (!mat.find()) {
                                verifValor = false;
                                valsError.append(RESOURCES.getString("intiPharm.err.fechaDispensacion")).append("\n");
                            } else {
                                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                                fecha = formatter.parse(prescriptionDispensed.getFecha());
                            }
                        }

                        Usuario user;
                        boolean existUsuario = true;
                        user = usuarioService.getUserByUserName(prescriptionDispensed.getNombreUsuario());

                        if (user == null) {
                            user = validarUsuario(prescriptionDispensed);
                            existUsuario = false;
                        }
                        List<Inventario> inventarioList = new ArrayList<>();
                        List<MovimientoInventario> movInventarioList = new ArrayList<>();
                        List<Inventario> lisInventarioInsert = new ArrayList<>();

                        if (prescriptionDispensed.getListMedicamentos() != null && !prescriptionDispensed.getListMedicamentos().isEmpty()) {

                            List<IntipharmMedicine> listaMedicamentos = new ArrayList<>();
                            validarExistenciaMedicamento(prescriptionDispensed, listaMedicamentos);

                            for (IntipharmMedicine medicine : listaMedicamentos) {
                                if (medicine != null) {
                                    Date date = new java.util.Date();
                                    if (medicine.getClaveInstitucional() == null || medicine.getClaveInstitucional().isEmpty()) {
                                        verifValor = false;
                                        valsError.append(RESOURCES.getString("intiPharm.err.claveInstitucional")).append("\n");
                                    }
                                    if (medicine.getLote() == null || medicine.getLote().isEmpty()) {
                                        verifValor = false;
                                        valsError.append(RESOURCES.getString("intiPharm.err.lote")).append("\n");
                                    }

                                    if (medicine.getFechaCaducidad() != null) {
                                        mat = Constantes.regexFormatFechas.matcher(medicine.getFechaCaducidad());
                                        if (!mat.find()) {
                                            verifValor = false;
                                            valsError.append(RESOURCES.getString("intiPharm.err.fechaCaducidad")).append("\n");
                                        } else {
                                            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                                            date = formatter.parse(medicine.getFechaCaducidad());
                                        }
                                    } else {
                                        verifValor = false;
                                        valsError.append(RESOURCES.getString("intiPharm.err.fechaCaducidad")).append("\n");
                                    }
                                    if (medicine.getCantidaIngresada() <= 0) {
                                        verifValor = false;
                                        valsError.append(RESOURCES.getString("intiPharm.err.cantidaIngresada")).append("\n");
                                    }

                                    if (!verifValor) {
                                        respuesta.setFolio(prescriptionDispensed.getFolio());
                                        respuesta.setError(true);
                                        respuesta.setMensaje(valsError.toString());
                                        break;
                                    }

                                    //CREAMOS LOS OBJETOS A LLENAR
                                    InventarioExtended inventarioExt;
                                    Inventario invn = new Inventario();
                                    Inventario invNew = new Inventario();
                                    MovimientoInventario movInventario = new MovimientoInventario();

                                    /**
                                     * Se comenta el metodo,y se genera 1
                                     * similar pero solo consultando inventario,
                                     * medicamento, estructura
                                     */
                                    //CONSULTAMOS SI EXISTE EL INSUMO EN EL INVENTARIO
                                    inventarioExt = inventarioService.getInsumoByFolioClaveInvRefill(medicine.getClaveInstitucional(), medicine.getLote(), date, prescriptionDispensed.getCodigoArea());
                                    Medicamento medica;
                                    verifValor = true;

                                    //sustituimos el objeto por una lista   reabastoInsumoExtented ->  listaReabastoInsumo
                                    if (inventarioExt == null) {
                                        verifValor = true;
                                        medica = medicamentoService.obtenerMedicaByClave(medicine.getClaveInstitucional());
                                        if (medica == null) {
                                            valsError.append(RESOURCES.getString("intiPharm.err.sinInsumo"));
                                            //cambiamos el valor de    false a true para que siguiera el flujo solo si no existe el medicamento, contemplando nadmas
                                            // que no tronara y que solo afectara a los medicamentos existentes en MUS.                                           
                                            verifValor = false;
                                        } else {
                                            invNew.setIdInventario(Comunes.getUUID());
                                            invNew.setFechaIngreso(fecha);
                                            invNew.setIdEstructura(codigoArea.getIdEstructura());
                                            invNew.setIdInsumo(medica.getIdMedicamento());
                                            invNew.setIdPresentacion(medica.getIdPresentacionSalida()); // Va a ser el valor de Salida
                                            invNew.setLote(medicine.getLote());
                                            invNew.setFechaCaducidad(date);
                                            invNew.setCosto(Constantes.COSTO_GENERICO);
                                            invNew.setCostoUnidosis(Constantes.COSTO_GENERICO);
                                            invNew.setExistenciaInicial(medicine.getCantidaIngresada());
                                            invNew.setActivo(Constantes.ES_ACTIVO);
                                            invNew.setInsertIdUsuario(user.getIdUsuario());
                                            invNew.setCantidadXCaja(1); // Si es gabinete debe de ser cantidadXCaja = 1 y no el factorTransformacion
                                            invNew.setCantidadActual(medicine.getCantidaIngresada());
                                            invNew.setClaveProveedor("0");//Se ingreso 0 para que se pueda activar la UNIQUE y no deje agregar valores repetidos.                                        
                                            invNew.setPresentacionComercial(0);//verificada, valor  = 0 por que es unidosis
                                            invNew.setInsertFecha(fecha);

                                            lisInventarioInsert.add(invNew);

                                            //Se ingreso el movimientoinvenetario en esta parte de código
                                            movInventario.setIdMovimientoInventario(Comunes.getUUID());
                                            movInventario.setIdTipoMotivo(TipoMotivo_Enum.ENT_REABA_POR_SURTIMIENTO_DE_REABASTO.getMotivoValue());
                                            movInventario.setFecha(fecha);
                                            movInventario.setIdUsuarioMovimiento(user.getIdUsuario());
                                            movInventario.setIdEstrutcuraOrigen(codigoArea.getIdEstructuraPadre());
                                            movInventario.setIdEstrutcuraDestino(codigoArea.getIdEstructura());
                                            movInventario.setIdInventario(invNew.getIdInventario());
                                            movInventario.setCantidad(medicine.getCantidaIngresada());
                                            movInventario.setFolioDocumento(prescriptionDispensed.getFolio());

                                            //Llenan listas
                                            movInventarioList.add(movInventario);

                                        }
                                    } else {
                                        invn.setIdInventario(inventarioExt.getIdInventario());
                                        //aqui solo se toma para sumarla en la transaccion
                                        invn.setCantidadActual(medicine.getCantidaIngresada());
                                        invn.setUpdateIdUsuario(user.getIdUsuario());
                                        invn.setUpdateFecha(fecha);
                                        inventarioList.add(invn);
                                        /*Se ingreso el movimientoInventario en esta parte de código */
                                        movInventario.setIdMovimientoInventario(Comunes.getUUID());
                                        movInventario.setIdTipoMotivo(TipoMotivo_Enum.ENT_REABA_POR_SURTIMIENTO_DE_REABASTO.getMotivoValue());                                        
                                        movInventario.setFecha(fecha);
                                        movInventario.setIdUsuarioMovimiento(user.getIdUsuario());
                                        movInventario.setIdEstrutcuraOrigen(codigoArea.getIdEstructuraPadre());
                                        movInventario.setIdEstrutcuraDestino(codigoArea.getIdEstructura());
                                        movInventario.setIdInventario(invn.getIdInventario());
                                        movInventario.setCantidad(medicine.getCantidaIngresada());
                                        movInventario.setFolioDocumento(prescriptionDispensed.getFolio());

                                        //Llenan listas
                                        movInventarioList.add(movInventario);
                                    }

                                    if (!verifValor) {
                                        respuesta.setFolio(prescriptionDispensed.getFolio());
                                        respuesta.setError(true);
                                        respuesta.setMensaje(valsError.toString());
                                        break;
                                    }
                                } else {
                                    verifValor = false;
                                    respuesta.setFolio(prescriptionDispensed.getFolio());
                                    respuesta.setError(false);
                                    valsError.append(RESOURCES.getString("intiPharm.err.vaciosNulos"));
                                    respuesta.setMensaje(valsError.toString());
                                }
                            }
                            if (verifValor) {
                                boolean respInse = reabastoService.insertForRefillInv(existUsuario, user, inventarioList, movInventarioList, lisInventarioInsert);
                                if (respInse) {
                                    respuesta.setFolio(prescriptionDispensed.getFolio());
                                    respuesta.setError(false);
                                    respuesta.setMensaje(RESOURCES.getString("intiPharm.info.refillCorrecto"));
                                    bitacoraService.updateMensajeBitacora(idBitacoraMensActualizar, respuesta.getMensaje());
                                } else {
                                    respuesta.setError(false);
                                    respuesta.setMensaje(RESOURCES.getString("intiPharm.info.refillIncorrecto"));
                                }
                            } else {
                                respuesta.setFolio(prescriptionDispensed.getFolio());
                                respuesta.setError(true);
                                respuesta.setMensaje(valsError.toString());
                            }
                        } else {
                            respuesta.setFolio(prescriptionDispensed.getFolio());
                            respuesta.setError(true);
                            respuesta.setMensaje(RESOURCES.getString("intiPharm.err.listaMedicaVacia"));
                        }
                    } else {
                        respuesta.setFolio(prescriptionDispensed.getFolio());
                        respuesta.setError(true);
                        respuesta.setMensaje(valsError.toString());
                    }
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje(RESOURCES.getString("intiPharm.err.vaciosNulos"));
                }
            }
        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            respuesta.setError(true);
            respuesta.setMensaje("Error en el Método InsertForRefill");
        }
        return respuesta;
    }

    private boolean validarSurtimiento(IntipharmSurtimiento surtimientoDispensed, StringBuilder valmensaje, boolean valValor) {
        if (surtimientoDispensed.getFolioSurtimiento() == null || surtimientoDispensed.getFolioSurtimiento().equals("")) {
            valValor = false;
            valmensaje.append(RESOURCES.getString("intiPharm.err.folio")).append("\n");
        }
        if (surtimientoDispensed.getNumeroPaciente() == null || surtimientoDispensed.getNumeroPaciente().equals("")) {
            valValor = false;
            valmensaje.append(RESOURCES.getString("intiPharm.err.numPaciente")).append("\n");
        }
        if (surtimientoDispensed.getNombrePaciente() == null || surtimientoDispensed.getNombrePaciente().equals("")) {
            valValor = false;
            valmensaje.append(RESOURCES.getString("intiPharm.err.nombrePaciente")).append("\n");
        }
        if (surtimientoDispensed.getNombreUsuarioDispensacion() == null || surtimientoDispensed.getNombreUsuarioDispensacion().equals("")) {
            valValor = false;
            valmensaje.append(RESOURCES.getString("intiPharm.err.nombreUsuarioDisp")).append("\n");
        } else if (surtimientoDispensed.getNombreUsuarioDispensacion() == null || surtimientoDispensed.getNombreUsuarioDispensacion().isEmpty()) {
            valValor = false;
            valmensaje.append(RESOURCES.getString("intiPharm.err.nombreUsuarioDispensacionCorto")).append("\n");
        }

        return valValor;
    }

    private Usuario validarUsuario(Usuario user) {
        //Verificar los datos que faltan, ya que algunos no deben de ir NULOS.            
        user.setActivo(true);
        user.setUsuarioBloqueado(false);
        user.setIdUsuario(Comunes.getUUID());
        user.setNombre(user.getNombreUsuario());
        user.setCedProfesional(user.getNombreUsuario());
        generarClaveCorreo(user);
        user.setUsuarioBloqueado(false);
        user.setInsertFecha(new java.util.Date());
        user.setInsertIdUsuario(user.getIdUsuario());
        user.setFechaVigencia(generateDate(new java.util.Date(), Constantes.ANIOS_VIGENCIA_USUARIO));

        return user;
    }

    private String convertirAXml(IntipharmSurtimiento surtimiento, boolean ban) {
        LOGGER.info("Entrando a convertirAXml");
        Document docu = new Document();
        Element channelElement = new Element("Surtimiento");
        docu.setRootElement(channelElement);

        Element folio = new Element("folio");
        folio.addContent(surtimiento.getFolioSurtimiento());
        channelElement.addContent(folio);

        Element prescriptionType = new Element("prescriptionType");
        prescriptionType.addContent(surtimiento.getPrescriptionType());
        channelElement.addContent(prescriptionType);

        Element nombreElemento = new Element("nombrePaciente");
        nombreElemento.addContent(surtimiento.getNombrePaciente());
        channelElement.addContent(nombreElemento);

        Element numeroPaciente = new Element("numeroPaciente");
        numeroPaciente.addContent(surtimiento.getNumeroPaciente());
        channelElement.addContent(numeroPaciente);

        Element nombreUsuDisp = new Element("usuarioDispensacion");
        nombreUsuDisp.addContent(surtimiento.getNombreUsuarioDispensacion());
        channelElement.addContent(nombreUsuDisp);

        Element precripcionDispense = new Element("precripcionDispense");
        channelElement.addContent(precripcionDispense);

        for (int i = 0; i < surtimiento.getListPrescriptions().size(); i++) {
            IntipharmPrescriptionDispense item = surtimiento.getListPrescriptions().get(i);

            Element idAlmacen = new Element("almacen");
            idAlmacen.addContent(item.getIdAlmacen());
            precripcionDispense.addContent(idAlmacen);

            Element fechaDispensacion = new Element("fechaDispensacion");
            if (item.getFechaDispensacion() == null || item.getFechaDispensacion().equals("")) {
                item.setFechaDispensacion("Nulo ó vacio");
            }
            fechaDispensacion.addContent(item.getFechaDispensacion());
            precripcionDispense.addContent(fechaDispensacion);

            Element claveInstitucional = new Element("claveInstitucional");
            claveInstitucional.addContent(item.getClaveInstitucional());
            precripcionDispense.addContent(claveInstitucional);

            Element cantidadDispensada = new Element("cantidadDispensada");
            String cantidadDis = Integer.toString(item.getCantidadDispensada());
            cantidadDispensada.addContent(cantidadDis);
            precripcionDispense.addContent(cantidadDispensada);

            Element lote = new Element("lote");
            lote.addContent(item.getLote());
            precripcionDispense.addContent(lote);

            Element fechaCaducidad = new Element("fechaCaducidad");
            fechaCaducidad.addContent(item.getFechaCaducidad());
            precripcionDispense.addContent(fechaCaducidad);

            Element numeroMedicacion = new Element("numeroMedicacion");
            if (item.getNumeroMedicacion() == 0) {
                ban = true;
            }
            String numMedic = Integer.toString(item.getNumeroMedicacion());
            numeroMedicacion.addContent(numMedic);
            precripcionDispense.addContent(numeroMedicacion);

            if (ban) {
                item.setNumeroMedicacion(0);
            }
        }
        return new XMLOutputter(Format.getPrettyFormat()).outputString(docu);
    }

    private Date fechaMinistrado(Date fecha) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) + 2);
        fecha = cal.getTime();

        return fecha;
    }

    private Date calculaFechaProgramadoMinistracion(SurtimientoInsumo_Extend surtimientoInsumoExtended) {
        Calendar cal = Calendar.getInstance();
        int hrsAumentar;
        if (surtimientoInsumoExtended.getNumeroMedicacion() == 1) {
            hrsAumentar = 0;
        } else {
            hrsAumentar = (surtimientoInsumoExtended.getFrecuencia() * surtimientoInsumoExtended.getNumeroMedicacion()) - surtimientoInsumoExtended.getFrecuencia();
        }
        cal.setTime(surtimientoInsumoExtended.getFechaInicio());
        cal.set(Calendar.HOUR, cal.get(Calendar.HOUR) + hrsAumentar);
        return cal.getTime();
    }

    private String insertaBitacora(IntipharmSurtimiento surtDispense, String idMensaje) throws Exception {
        LOGGER.info("Entrando a insertaBitacora");
        String idBitacora = null;
        try {

            if (surtDispense.getListPrescriptions().get(0).getFechaDispensacion() == null || surtDispense.getListPrescriptions().get(0).getFechaDispensacion().isEmpty()) {
                surtDispense.getListPrescriptions().get(0).setFechaDispensacion("vacio");
            }

            BitacoraMensaje bitaMensaje = new BitacoraMensaje();
            bitaMensaje.setIdBitacoraMensaje(Comunes.getUUID());
            bitaMensaje.setFecha(new java.util.Date());
            bitaMensaje.setTipoMensaje(TipoMensaje_Enum.INTIPHARM.getValue());
            bitaMensaje.setEntradaSalida(TipoEntradaSalida_Enum.SALIDA.getValue());
            bitaMensaje.setIdMensaje(idMensaje);

            boolean band = false;
            String cadenaXml = convertirAXml(surtDispense, band);

            bitaMensaje.setMensaje(cadenaXml);
            bitaMensaje.setCodigoRespuesta(RESOURCES.getString("intiPharm.err.actSurtimiento"));
            boolean insertar = bitacoraService.insertar(bitaMensaje);
            if (insertar) {
                idBitacora = bitaMensaje.getIdBitacoraMensaje();
            }
        } catch (Exception e) {
            LOGGER.info("Error en insertaBitacora: {}", e.getMessage());
            throw new Exception("Error al Insertar la BitacoraMensaje ");
        }
        return idBitacora;
    }

    private void cargarObjetosTracking(SurtimientoInsumo_Extend surtimientoInsumoExtended,
            SurtimientoEnviado surtEnviado, SurtimientoMinistrado surtMinistrado,
            InventarioExtended inventa, MovimientoInventario movInventario, Usuario user, Date dateDispensacion,
            IntipharmPrescriptionDispense prescript, IntipharmSurtimiento surtimientoDispensed) {

        surtimientoInsumoExtended.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.COMPLETADO.getValue());
        surtimientoInsumoExtended.setUpdateFecha(dateDispensacion);
        surtimientoInsumoExtended.setUpdateIdUsuario(user.getIdUsuario());
        surtimientoInsumoExtended.setIdUsuarioRecepcion(user.getIdUsuario());
        surtimientoInsumoExtended.setCantidadRecepcion(prescript.getCantidadDispensada());

        //GENERA EL OBJETO DE SURTIMIENTO_ENVIADO PARA    INSERTARLO/CREARLO
        surtEnviado.setIdSurtimientoEnviado(Comunes.getUUID());
        surtEnviado.setIdSurtimientoInsumo(surtimientoInsumoExtended.getIdSurtimientoInsumo());
        surtEnviado.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.COMPLETADO.getValue());
        surtEnviado.setIdInventarioSurtido(inventa.getIdInventario());
        surtEnviado.setCantidadRecibido(prescript.getCantidadDispensada());
        surtEnviado.setCantidadEnviado(prescript.getCantidadDispensada());
        surtEnviado.setInsertFecha(dateDispensacion);
        surtEnviado.setInsertIdUsuario(user.getIdUsuario());

        /*Se generea el objeto de surtimientoMinistrado*/
        surtMinistrado.setIdMinistrado(Comunes.getUUID());
        surtMinistrado.setIdSurtimientoEnviado(surtEnviado.getIdSurtimientoEnviado());
        surtMinistrado.setFechaProgramado(dateDispensacion);
        surtMinistrado.setCantidad(surtimientoInsumoExtended.getCantidadSolicitada());
        surtMinistrado.setFechaProgramado(calculaFechaProgramadoMinistracion(surtimientoInsumoExtended));
        surtMinistrado.setCantidadMinistrada(prescript.getCantidadDispensada());
        surtMinistrado.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.COMPLETADO.getValue());
        surtMinistrado.setIdEstatusMinistracion(EstatusMinistracion_Enum.MINISTRADO.getValue());
        surtMinistrado.setInsertFecha(dateDispensacion);
        surtMinistrado.setInsertIdUsuario(user.getIdUsuario());
        surtMinistrado.setIdUsuario(user.getIdUsuario());
        surtMinistrado.setDosis(surtimientoInsumoExtended.getDosis());
        surtMinistrado.setFechaMinistrado(fechaMinistrado(dateDispensacion));

        //datos para actualizar el objeto Inventario
        inventa.setUpdateFecha(dateDispensacion);
        inventa.setUpdateIdUsuario(user.getIdUsuario());
        inventa.setCantidadEntregada(prescript.getCantidadDispensada());
        //CREAR BJETO MOVIMIENTOINVENTARIO                                            

        movInventario.setIdMovimientoInventario(Comunes.getUUID());
        movInventario.setIdInventario(inventa.getIdInventario());
        //verificar tipoMotivo
        movInventario.setIdTipoMotivo(TipoMotivo_Enum.SAL_PRESC_POR_SURTIMIENTO_DE_PRESCRIPCION.getMotivoValue());        
        movInventario.setFecha(dateDispensacion);
        movInventario.setIdUsuarioMovimiento(user.getIdUsuario());
        movInventario.setIdEstrutcuraOrigen(surtimientoInsumoExtended.getIdEstructuraAlmacen());
        movInventario.setIdEstrutcuraDestino(surtimientoInsumoExtended.getIdEstructura());
        movInventario.setCantidad(prescript.getCantidadDispensada());
        movInventario.setFolioDocumento(surtimientoDispensed.getFolioSurtimiento());
    }

    private boolean validarListaSurtByMedication(boolean valValor, IntipharmPrescriptionDispense prescript, StringBuilder valmensaje) {
        Matcher mat;

        if (prescript.getIdAlmacen() == null || prescript.getIdAlmacen().equals("")) {
            valValor = false;
            valmensaje.append(RESOURCES.getString("intiPharm.err.idAlmacen")).append("\n");
        }

        if (!Boolean.TRUE.equals(prescript.isBanderaInventario())) {
            prescript.setBanderaInventario(false);
            LOGGER.info("Es el valor null");
        }

        if (prescript.getClaveInstitucional() == null || prescript.getClaveInstitucional().equals("")) {
            valValor = false;
            valmensaje.append(RESOURCES.getString("intiPharm.err.claveInstitucional")).append("\n");
        }
        if (prescript.getCantidadDispensada() <= 0) {
            valValor = false;
            valmensaje.append(RESOURCES.getString("intiPharm.err.cantidadDispensada")).append("\n");
        }
        if (prescript.getLote() == null || prescript.getLote().equals("")) {
            valValor = false;
            valmensaje.append(RESOURCES.getString("intiPharm.err.lote")).append("\n");
        }
        if (prescript.getNumeroMedicacion() == 0) {
            valValor = false;
            valmensaje.append(RESOURCES.getString("intiPharm.err.numeroMedicacionNulo")).append("\n");
        } else {
            if (prescript.getNumeroMedicacion() <= 0) {
                valValor = false;
                valmensaje.append(RESOURCES.getString("intiPharm.err.numeroMedicacion")).append("\n");
            }
        }

        if (prescript.getFechaCaducidad() != null) {
            mat = Constantes.regexFormatFechas.matcher(prescript.getFechaCaducidad());
            if (!mat.find()) {
                valValor = false;
                valmensaje.append(RESOURCES.getString("intiPharm.err.fechaCaducidad")).append("\n");
            }
        } else {
            valValor = false;
            valmensaje.append(RESOURCES.getString("intiPharm.err.fechaCaducidad")).append("\n");
        }

        if (prescript.getFechaDispensacion() == null || prescript.getFechaDispensacion().isEmpty()) {
            valValor = false;
            valmensaje.append(RESOURCES.getString("intiPharm.err.fechaDispensacion")).append("\n");
        } else {
            mat = Constantes.regexFechaCompleta.matcher(prescript.getFechaDispensacion());
            if (!mat.find()) {
                valValor = false;
                valmensaje.append(RESOURCES.getString("intiPharm.err.fechaDispensacion")).append("\n");
            }
        }
        return valValor;
    }

    private void validarExistenciaMedicamento(IntipharmReabasto prescriptionDispensed, List<IntipharmMedicine> listaMedicamentos) {
        for (IntipharmMedicine medicine : prescriptionDispensed.getListMedicamentos()) {
            if (listaMedicamentos.isEmpty()) {
                listaMedicamentos.add(medicine);
            } else {
                boolean band = true;
                for (IntipharmMedicine medicamento : listaMedicamentos) {
                    if (medicamento.getLote().equals(medicine.getLote())
                            && medicamento.getClaveInstitucional().equals(medicine.getClaveInstitucional())
                            && medicamento.getFechaCaducidad().equals(medicine.getFechaCaducidad())) {
                        medicamento.setCantidaIngresada(medicamento.getCantidaIngresada() + medicine.getCantidaIngresada());
                        band = false;
                        break;
                    }
                }
                if (band) {
                    listaMedicamentos.add(medicine);
                }
            }
        }
    }

    private String buscarMedicamentoByClaveInstitucional(String claveMedicamento) {
        String idMedicamento = null;
        try {
            Medicamento unMedicamento = medicamentoService.obtenerMedicaByClave(claveMedicamento);
            idMedicamento = unMedicamento.getIdMedicamento();
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString("dispensacionDirecta.err.buscarMedicamento"), ex);
        }
        return idMedicamento;
    }

    private String validaVisitaPacienteServicio(DispensacionDirecta unaDispensacionDirecta) {
        String idPacienteServicio = null;
        //Realizar validacion si tiene paciente servicio el paciente o generarlo
        Visita visitaPaciente;
        PacienteServicio pacienteServicio;
        try {
            String idVisita;
            Visita visita = new Visita();
            visita.setIdPaciente(unaDispensacionDirecta.getIdPaciente());
            visitaPaciente = visitaService.obtenerVisitaAbierta(visita);
            if (visitaPaciente == null) {
                // crear la visita
                visitaPaciente = new Visita();
                idVisita = Comunes.getUUID();
                visitaPaciente.setIdVisita(idVisita);
                visitaPaciente.setIdPaciente(unaDispensacionDirecta.getIdPaciente());
                visitaPaciente.setFechaIngreso(new Date());
                visitaPaciente.setIdUsuarioIngresa(unaDispensacionDirecta.getIdMedico());  //Revisar que usuario dara el ingreso
                visitaPaciente.setIdMotivoPacienteMovimiento(MotivoPacienteMovimiento_Enum.ADMISION.getValue());
                visitaPaciente.setMotivoConsulta("Admision de Paciente"); //Motivo de Consulta
                visitaPaciente.setInsertFecha(new Date());
                visitaPaciente.setInsertIdUsuario(unaDispensacionDirecta.getIdUsuario());
                visitaService.insertar(visitaPaciente);
            } else {
                idVisita = visitaPaciente.getIdVisita();
            }
            // Buscar su paciente servicio por idVisita
            pacienteServicio = pacienteServicioService.obtenerPacienteServicioAbierto(new PacienteServicio(idVisita));
            if (pacienteServicio == null) {
                // crear su paciente Servicio
                idPacienteServicio = Comunes.getUUID();
                pacienteServicio = new PacienteServicio();
                pacienteServicio.setIdPacienteServicio(idPacienteServicio);
                pacienteServicio.setIdVisita(idVisita);
                pacienteServicio.setIdEstructura(unaDispensacionDirecta.getIdEstructura());
                pacienteServicio.setFechaAsignacionInicio(new Date());
                pacienteServicio.setIdMotivoPacienteMovimiento(MotivoPacienteMovimiento_Enum.ADMISION.getValue());
                pacienteServicio.setInsertFecha(new Date());
                pacienteServicio.setInsertIdUsuario(unaDispensacionDirecta.getIdUsuario());
                pacienteServicioService.insertar(pacienteServicio);
            } else {
                idPacienteServicio = pacienteServicio.getIdPacienteServicio();
            }

        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString("dispensacionDirecta.err.validarVisPaciServ"), ex);
        }
        return idPacienteServicio;
    }

    private void llenarPrescripcionSurtimiento(DispensacionDirecta unaDispensacionDirecta, Prescripcion unaPrescripcion,
            PrescripcionInsumo unaPrescripcionInsumo, Surtimiento unSurtimiento, SurtimientoInsumo unSurtimientoInsumo) {

        //se llenan objetos
        String idPrescripcion = Comunes.getUUID();
        unaPrescripcion.setIdPrescripcion(idPrescripcion);
        unaPrescripcion.setIdEstructura(unaDispensacionDirecta.getIdEstructura());

        String idPacienteServicio = validaVisitaPacienteServicio(unaDispensacionDirecta);
        unaPrescripcion.setIdPacienteServicio(idPacienteServicio);

        //Se genera el folio al momento de registrar la prescripcion
        unaPrescripcion.setFechaPrescripcion(new Date());
        unaPrescripcion.setTipoPrescripcion(unaDispensacionDirecta.getTipoPrescripcion());// Ponerlo en una Constante
        unaPrescripcion.setTipoConsulta(unaDispensacionDirecta.getTipoConsulta());
        unaPrescripcion.setIdMedico(unaDispensacionDirecta.getIdMedico());
        unaPrescripcion.setRecurrente(false);
        unaPrescripcion.setIdEstatusPrescripcion(EstatusPrescripcion_Enum.FINALIZADA.getValue());
        unaPrescripcion.setIdEstatusGabinete(EstatusGabinete_Enum.OK.getValue());
        unaPrescripcion.setInsertFecha(new Date());
        unaPrescripcion.setInsertIdUsuario(unaDispensacionDirecta.getIdUsuario());

        String idPrescripcionInsumo = Comunes.getUUID();
        unaPrescripcionInsumo.setIdPrescripcionInsumo(idPrescripcionInsumo);
        unaPrescripcionInsumo.setIdPrescripcion(idPrescripcion);

        //Obtener idInsumo de medicamento       
        String idMedicamento = buscarMedicamentoByClaveInstitucional(unaDispensacionDirecta.getClaveMedicamento());
        unaPrescripcionInsumo.setIdInsumo(idMedicamento);

        unaPrescripcionInsumo.setFechaInicio(new Date());
        unaPrescripcionInsumo.setDosis(unaDispensacionDirecta.getDosis()); // Revisar si se pondra la cantidad total del medicamento
        unaPrescripcionInsumo.setFrecuencia(24); // ponerlo en constante   
        unaPrescripcionInsumo.setDuracion(1); // ponerlo en constante
        unaPrescripcionInsumo.setIdEstatusPrescripcion(EstatusPrescripcion_Enum.FINALIZADA.getValue());
        unaPrescripcionInsumo.setInsertFecha(new Date());
        unaPrescripcionInsumo.setInsertIdUsuario(unaDispensacionDirecta.getIdUsuario());
        unaPrescripcionInsumo.setIdEstatusMirth(EstatusGabinete_Enum.OK.getValue());

        String idSurtimiento = Comunes.getUUID();
        unSurtimiento.setIdSurtimiento(idSurtimiento);
        //Revisar que estructura Almacen
        unSurtimiento.setIdEstructuraAlmacen(unaDispensacionDirecta.getIdEstructuraAlmacen());
        unSurtimiento.setIdPrescripcion(idPrescripcion);
        unSurtimiento.setFechaProgramada(new Date());
        //Se genera el folio de surtimiento al momento de registrar
        unSurtimiento.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.RECIBIDO.getValue());
        unSurtimiento.setInsertFecha(new Date());
        unSurtimiento.setInsertIdUsuario(unaDispensacionDirecta.getIdUsuario());
        unSurtimiento.setIdEstatusMirth(EstatusGabinete_Enum.OK.getValue());

        unSurtimientoInsumo.setIdSurtimientoInsumo(Comunes.getUUID());
        unSurtimientoInsumo.setIdSurtimiento(idSurtimiento);
        unSurtimientoInsumo.setIdPrescripcionInsumo(idPrescripcionInsumo);
        unSurtimientoInsumo.setFechaProgramada(new Date());
        unSurtimientoInsumo.setCantidadSolicitada(unaDispensacionDirecta.getCantidadSolicitada());
        unSurtimientoInsumo.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.RECIBIDO.getValue());
        unSurtimientoInsumo.setInsertFecha(new Date());
        unSurtimientoInsumo.setInsertIdUsuario(unaDispensacionDirecta.getIdUsuario());
        unSurtimientoInsumo.setIdEstatusMirth(EstatusGabinete_Enum.OK.getValue());
        unSurtimientoInsumo.setNumeroMedicacion(1);
    }

    /*Se altero el metodo interno para el tracking, en lugar de devolver un String devuelva un boolean ya que 
    internamente es mejor, independientemente de MB DispensacionDirectaMB
     */
    private String registrarPrescripcionDirecta(DispensacionDirecta unaDispensacionDirecta) throws Exception {

        String folioSurtimiento = null;
        boolean res = false;
        //Se genera los objetos de Prescripcion, prescripcionInsumo, surtimiento y surtimientoInsumo
        Prescripcion unaPrescripcion = new Prescripcion();
        PrescripcionInsumo unaPrescripcionInsumo = new PrescripcionInsumo();
        Surtimiento unSurtimiento = new Surtimiento();
        SurtimientoInsumo unSurtimientoInsumo = new SurtimientoInsumo();
        // Llamar a metodo de llenado de prescripcion, prescripcionInsumo, surtimiento, surtimientoInsumo e insertar en tablas correspondientes
        llenarPrescripcionSurtimiento(unaDispensacionDirecta, unaPrescripcion, unaPrescripcionInsumo, unSurtimiento, unSurtimientoInsumo);
        // Generar metodo para realizar la insercion de prescripcion, prescripcionInsumo, surtimiento, surtimientoInsumo
        res = dispensacionDirectaService.registrarPrescripcion(unaPrescripcion, unaPrescripcionInsumo, unSurtimiento, unSurtimientoInsumo);

        if (!res) {
            Mensaje.showMessage("Error", RESOURCES.getString("dispensacionDirecta.err.regisPrescr"), null);
        } else {
            folioSurtimiento = unSurtimiento.getFolio();
        }
        return folioSurtimiento;
    }

    /**
     * Genera paciente cuando se hace una prescripcion de Tipo Verbal V
     *
     * @param paciente
     * @return
     */
    private Paciente generaPaciente(IntipharmSurtimiento surtimientoDispensed) {
        Paciente pacienteNew = new Paciente();
        pacienteNew.setIdPaciente(Comunes.getUUID());
        String nombre[] = surtimientoDispensed.getNombrePaciente().split(" ");
        if (nombre.length > 1) {
            pacienteNew.setNombreCompleto(nombre[0]);
            pacienteNew.setApellidoPaterno(nombre[1]);
        }
        pacienteNew.setNombreCompleto(surtimientoDispensed.getNombrePaciente());
        pacienteNew.setApellidoPaterno(surtimientoDispensed.getNombrePaciente());
        pacienteNew.setSexo('M');//Verificar Valor
        pacienteNew.setFechaNacimiento(new Date());
        pacienteNew.setIdTipoPaciente(1);
        pacienteNew.setIdUnidadMedica(GrupoCatalogoGeneral_Enum.UNIDAD_MEDICA.getValue()); //Verificar
        pacienteNew.setIdEstatusPaciente(1);//Registrado
        pacienteNew.setPacienteNumero(surtimientoDispensed.getNumeroPaciente());
        pacienteNew.setIdEstadoCivil(CatalogoGeneral_Enum.ESTADO_CIVIL_NO_DEFINIDO.getValue());
        pacienteNew.setIdEscolaridad(CatalogoGeneral_Enum.ESCOLARIDAD_NO_DEFINIDA.getValue());
        pacienteNew.setIdGrupoEtnico(CatalogoGeneral_Enum.GRUPO_ETNICO_NO_DEFINIDO.getValue());
        pacienteNew.setIdGrupoSanguineo(CatalogoGeneral_Enum.GRUPO_SANGUINEO_NO_DEFINIDO.getValue());
        pacienteNew.setIdReligion(CatalogoGeneral_Enum.RELIGION_NO_DEFINIDA.getValue());
        pacienteNew.setIdNivelSocioEconomico(CatalogoGeneral_Enum.NIVEL_SOCIOECONOMICO_NO_DEFINIDO.getValue());
        pacienteNew.setIdTipoVivienda(CatalogoGeneral_Enum.TIPO_VIVIENDA_NO_DEFINIDA.getValue());
        pacienteNew.setIdOcupacion(CatalogoGeneral_Enum.OCUPACION_NO_DEFINIDA.getValue());
        pacienteNew.setInsertFecha(new Date());

        return pacienteNew;

    }

    private String validaExistenciaBitacoraTracking(IntipharmSurtimiento surtimientoDispensed) {
        String cabezera = surtimientoDispensed.getFolioSurtimiento().concat(surtimientoDispensed.getNumeroPaciente()).concat(surtimientoDispensed.getPrescriptionType());
        List<String> valorMensaje = new ArrayList<>();
        surtimientoDispensed.getListPrescriptions().stream().map(prescript -> {
            int numeroMedicacion = prescript.getNumeroMedicacion();
            String clave = prescript.getClaveInstitucional();
            String almacen = prescript.getIdAlmacen();
            String lote = prescript.getLote();
            String caducidad = prescript.getFechaCaducidad();
            String dispensacion = prescript.getFechaDispensacion();
            return clave.concat(almacen).concat(lote).concat(caducidad).concat(dispensacion).concat(Integer.toString(numeroMedicacion));
        }).forEachOrdered(idClave ->
            valorMensaje.add(idClave)
        );
        return cabezera.concat(valorMensaje.toString());
    }

    /**
     * Web service operation
     *
     * @param surtimientoDispensed
     * @return
     * @throws java.lang.Exception
     */
    @WebMethod(operationName = "InsertforPrescriptionTracking")
    @WebResult(name = "respuesta")
    public IntipharmRespuesta insertforPrescriptionTracking(@WebParam(name = "InsertforPrescriptionTracking") IntipharmSurtimiento surtimientoDispensed) throws Exception {

        IntipharmRespuesta respuesta = new IntipharmRespuesta();
        try {
            String idMensaje = validaExistenciaBitacoraTracking(surtimientoDispensed);

//            BitacoraMensaje bita = bitacoraService.validaExistenciaBitacora(idMensaje);
//            if (bita != null) {
//                respuesta.setMensaje(RESOURCES.getString("intiPharm.info.obtenidoCorrecto"));
//                LOGGER.info(RESOURCES.getString("intiPharm.info.obtenidoCorrecto"));
//                respuesta.setError(false);
//                respuesta.setFolio(surtimientoDispensed.getFolioSurtimiento());
//            } else {

//                String idBitacoraMensActualizar = insertaBitacora(surtimientoDispensed, idMensaje);
                boolean valValor = true;
                StringBuilder valmensaje = new StringBuilder();
                if (surtimientoDispensed != null) {
                    LOGGER.info(surtimientoDispensed.getFolioSurtimiento());

                    if (surtimientoDispensed.getListPrescriptions() != null) {

                        valValor = validarSurtimiento(surtimientoDispensed, valmensaje, valValor);
                        if (valValor) {
                            Usuario user = new Usuario();
                            user.setNombreUsuario(surtimientoDispensed.getNombreUsuarioDispensacion());
                            boolean existUsuario = true;

                            user = usuarioService.getUserByUserName(user.getNombreUsuario());

                            if (user == null) {
                                user = new Usuario();
                                user.setNombreUsuario(surtimientoDispensed.getNombreUsuarioDispensacion());
                                user = validarUsuario(user);
                                existUsuario = usuarioService.insertar(user);
                            }
                            String folioSurtimiento = null;
                            //Se genera el objeto de surtimiento a actualizar
                            Surtimiento surtimiento = new Surtimiento();
                            if (surtimientoDispensed.getPrescriptionType().equals(Constantes.PRESC_VERBAL)) {

                                DispensacionDirecta respDispDirecta = new DispensacionDirecta();
                                EstrucAlmacenServicio_Extend estAlmServ;
                                Paciente pac;

                                for (IntipharmPrescriptionDispense prescript : surtimientoDispensed.getListPrescriptions()) {

                                    estAlmServ = estructuraAlmacenServicioService.getAlmacenServicioByClaveEstructura(prescript.getIdAlmacen());

                                    if (estAlmServ == null) {
                                        respuesta.setMensaje("No existe el Gabinete con ClaveEstructura : " + prescript.getIdAlmacen());
                                        respuesta.setError(true);
                                        break;
                                    }

                                    Medicamento medica = medicamentoService.obtenerMedicaByClave(prescript.getClaveInstitucional());
                                    if (medica == null) {
                                        respuesta.setMensaje("No existe el Insumo : " + prescript.getClaveInstitucional());
                                        respuesta.setError(true);
                                        break;
                                    }
                                    /*Cuando es Verbal, al registrar Dispensacion directa siempre Setean el valor de 1 en numeroMedicación,
                                por ello se le asigna directamente aqui.
                                     */
                                    prescript.setNumeroMedicacion(1);
                                    pac = pacienteService.obtenerPacienteByNumeroPaciente(surtimientoDispensed.getNumeroPaciente());
                                    if (pac == null) {
                                        pac = generaPaciente(surtimientoDispensed);
                                        pac.setInsertIdUsuario(user.getIdUsuario());

                                        boolean ingresoPaciente = pacienteService.insertar(pac);

                                        if (!ingresoPaciente) {
                                            respuesta.setMensaje("Error al Ingresar el paciente Verbal");
                                            respuesta.setError(true);
                                            break;
                                        }

                                    }

                                    respDispDirecta.setCantidadSolicitada(prescript.getCantidadDispensada());
                                    respDispDirecta.setIdUsuario(user.getIdUsuario());
                                    respDispDirecta.setIdMedico(user.getIdUsuario());
                                    respDispDirecta.setTipoPrescripcion(Constantes.PRESC_VERBAL);
                                    respDispDirecta.setIdEstructuraAlmacen(estAlmServ.getIdEstructuraAlmacen());
                                    respDispDirecta.setIdEstructura(estAlmServ.getIdEstructuraServicio());
                                    respDispDirecta.setTipoConsulta(TipoConsulta_Enum.CONSULTA_INTERNA.getValue());
                                    BigDecimal dosis = medica.getConcentracion().multiply(new BigDecimal(prescript.getCantidadDispensada()));
                                    respDispDirecta.setDosis(dosis);
                                    respDispDirecta.setIdPaciente(pac.getIdPaciente());
                                    respDispDirecta.setClaveMedicamento(prescript.getClaveInstitucional());

                                    folioSurtimiento = registrarPrescripcionDirecta(respDispDirecta);
                                }

                            } else {
                                folioSurtimiento = surtimientoDispensed.getFolioSurtimiento();
                            }

                            surtimiento.setFolio(folioSurtimiento);
                            surtimiento.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.COMPLETADO.getValue());
                            surtimiento.setUpdateFecha(new java.util.Date());
                            surtimiento.setUpdateIdUsuario(user.getIdUsuario());

                            List<SurtimientoInsumo> surtimientoInsumoList = new ArrayList<>();
                            List<SurtimientoEnviado> surtimientoEnviadoList = new ArrayList<>();
                            List<InventarioExtended> inventarioList = new ArrayList<>();
                            List<MovimientoInventario> movimientoInventarioList = new ArrayList<>();
                            List<SurtimientoMinistrado> surtMinistradoList = new ArrayList<>();

                            for (IntipharmPrescriptionDispense prescript : surtimientoDispensed.getListPrescriptions()) {
                                if (prescript != null) {
                                    Date dateDispensacion;
                                    Date date;

                                    valValor = true;//validarListaSurtByMedication(valValor, prescript, valmensaje);

                                    if (valValor) {
                                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                                        date = formatter.parse("9999-12-31");//prescript.getFechaCaducidad());

                                        SimpleDateFormat formatterDisp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                        dateDispensacion = formatterDisp.parse(prescript.getFechaDispensacion());

                                        //CONSULTAMOS SI EXISTE EL INSUMO EN EL INVENTARIO                        
                                        SurtimientoInsumo_Extend surtimientoInsumoExtended = surtimientoInsumoService.getSurtimientoByTrackingByFolio(folioSurtimiento, surtimientoDispensed.getNumeroPaciente(), prescript.getClaveInstitucional(), prescript.getNumeroMedicacion());

                                        if (surtimientoInsumoExtended != null) {

                                            int presentacionComercial = 0;
                                            InventarioExtended inventa = inventarioService.getInventarioTracking(prescript.getClaveInstitucional(), prescript.getLote(), prescript.getIdAlmacen(), date, presentacionComercial, prescript.isBanderaInventario());

                                            if (inventa == null) {
                                                respuesta.setError(true);
                                                respuesta.setFolio(surtimientoDispensed.getFolioSurtimiento());
                                                respuesta.setMensaje(RESOURCES.getString("intiPharm.err.sinInventario"));
                                                valValor = false;
                                                break;
                                            }
                                            if (inventa.getIdEstructura() == null || inventa.getIdEstructura().equals("")) {
                                                user.setIdEstructura(surtimientoInsumoExtended.getEstructuraPrescripcion());
                                            }

                                            surtimiento.setIdSurtimiento(surtimientoInsumoExtended.getIdSurtimiento());

                                            SurtimientoEnviado surtEnviado = new SurtimientoEnviado();
                                            SurtimientoMinistrado surtMinistrado = new SurtimientoMinistrado();
                                            MovimientoInventario movInventario = new MovimientoInventario();

                                            cargarObjetosTracking(surtimientoInsumoExtended, surtEnviado, surtMinistrado, inventa, movInventario, user, dateDispensacion, prescript, surtimientoDispensed);

                                            //Llenamos las listas con los objetos
                                            surtimientoInsumoList.add(surtimientoInsumoExtended);
                                            surtimientoEnviadoList.add(surtEnviado);
                                            inventarioList.add(inventa);
                                            movimientoInventarioList.add(movInventario);
                                            surtMinistradoList.add(surtMinistrado);
                                        } else {
                                            respuesta.setError(true);
                                            respuesta.setFolio(surtimientoDispensed.getFolioSurtimiento());
                                            respuesta.setMensaje(RESOURCES.getString("intiPharm.err.sinSurtimiento"));
                                            valValor = false;
                                            break;
                                        }
                                    } else {
                                        respuesta.setError(true);
                                        respuesta.setFolio(RESOURCES.getString("intiPharm.err.vaciosNulos"));
                                        respuesta.setMensaje(valmensaje.toString());
                                        break;
                                    }
                                } else {
                                    respuesta.setError(true);
                                    respuesta.setFolio(surtimientoDispensed.getFolioSurtimiento());
                                    respuesta.setMensaje(RESOURCES.getString("intiPharm.err.vaciosNulos"));
                                    valValor = false;
                                    break;
                                }
                            }

                            if (valValor) {

                                boolean respSurtimiento = surtimientoService.actualizarRegistrosTracking(existUsuario, user, surtimiento, surtimientoInsumoList, surtimientoEnviadoList, inventarioList, movimientoInventarioList, surtMinistradoList);

                                if (respSurtimiento) {
                                    respuesta.setMensaje(RESOURCES.getString("intiPharm.info.obtenidoCorrecto"));
                                    LOGGER.info(RESOURCES.getString("intiPharm.info.obtenidoCorrecto"));
                                    respuesta.setError(false);
                                    respuesta.setFolio(surtimientoDispensed.getFolioSurtimiento());
//                                    bitacoraService.updateMensajeBitacora(idBitacoraMensActualizar, respuesta.getMensaje());
                                } else {
                                    //Metodo en caso de fallar, se actualiza que no se hizo nada actualizando dichaTabla con código.
                                    respuesta.setError(true);
                                    respuesta.setFolio(surtimientoDispensed.getFolioSurtimiento());
                                    respuesta.setMensaje(RESOURCES.getString("intiPharm.err.actSurtimiento"));
                                    LOGGER.error(RESOURCES.getString("intiPharm.info.obtenidoCorrecto"));
                                }
                            }
                        } else {
                            respuesta.setError(true);
                            respuesta.setFolio(RESOURCES.getString("intiPharm.err.vaciosNulos"));
                            respuesta.setMensaje(valmensaje.toString());
                        }
                    } else {
                        respuesta.setError(true);
                        respuesta.setMensaje(RESOURCES.getString("intiPharm.err.listaMedicaVacia"));
                    }

                } else {
                    respuesta.setError(valValor);
                    respuesta.setMensaje(RESOURCES.getString("intiPharm.err.vaciosNulos"));
                }
//            }
        } catch (Exception e) {
            LOGGER.error("Entro a la Exception: {}", e.getMessage());
            respuesta.setError(true);
            respuesta.setMensaje("Error en el Método InsertforPrescriptionTracking \n" + e.getMessage());
        }
        return respuesta;
    }

    private Inventario getInventarioUpdate(IntipharmVerfStock stocked) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date caducidad = sdf.parse(stocked.getFechaCaducidad());
        Inventario inventario = new Inventario();
        int cantidadXCaja = 1;
        try {
            inventario = inventarioService.getInventarioByStock(stocked.getLote(), stocked.getClaveInstitucional(), stocked.getAlmacen(), caducidad, cantidadXCaja);
        } catch (Exception e) {
            throw new Exception("Error al obtener el Inventario " + e.getMessage());
        }
        return inventario;
    }

    /**
     * Valida Campos de la lista del Método InsertForRestockList
     *
     * @param valValor
     * @param stocked
     * @param valmensaje
     * @return
     */
    private boolean validaCamposRestock(boolean valValor, IntipharmVerfStock stocked, StringBuilder valmensaje) {
        Matcher mat;
        if (stocked.getAlmacen() == null || stocked.getAlmacen().isEmpty()) {
            valValor = false;
            valmensaje.append(RESOURCES.getString("intiPharm.err.nombreAlmacen")).append("\n");
        }
        if (stocked.getCantidad() < 0) {
            valValor = false;
            valmensaje.append(RESOURCES.getString("intiPharm.err.canridadCorrecta")).append("\n");
        }
        if (stocked.getClaveInstitucional() == null || stocked.getClaveInstitucional().isEmpty()) {
            valValor = false;
            valmensaje.append(RESOURCES.getString("intiPharm.err.claveInstitucional")).append("\n");
        }
        if (stocked.getLote() == null || stocked.getLote().isEmpty()) {
            valValor = false;
            valmensaje.append(RESOURCES.getString("intiPharm.err.lote")).append("\n");
        }
        if (stocked.getFechaCaducidad() != null) {
            mat = Constantes.regexFormatFechas.matcher(stocked.getFechaCaducidad());
            if (!mat.find()) {
                valValor = false;
                valmensaje.append(RESOURCES.getString("intiPharm.err.fechaCaducidad")).append("\n");
            }
        } else {
            valValor = false;
            valmensaje.append(RESOURCES.getString("intiPharm.err.fechaCaducidad")).append("\n");
        }

        if (stocked.getFechaHora() == null || stocked.getFechaHora().isEmpty()) {
            valValor = false;
            valmensaje.append(RESOURCES.getString("intiPharm.err.fechaHora")).append("\n");
        } else {
            mat = Constantes.regexFechaCompleta.matcher(stocked.getFechaHora());
            if (!mat.find()) {
                valValor = false;
                valmensaje.append(RESOURCES.getString("intiPharm.err.fechaHora")).append("\n");
            }
        }
        return valValor;
    }

    private void cargarInventarioMovInventario(Inventario inventario, MovimientoInventario movInventario, Date dateHora, IntipharmVerfStock stocked, Medicamento medica,
            Estructura claveAlmacen, boolean existeInventario) {
        if (!existeInventario) {
            //llenar objeto inventario
            inventario.setIdInventario(Comunes.getUUID());
            inventario.setFechaIngreso(dateHora);
            inventario.setIdEstructura(claveAlmacen.getIdEstructura());
            inventario.setIdInsumo(medica.getIdMedicamento());
            inventario.setIdPresentacion(medica.getIdPresentacionSalida());// VERIFICAR
            inventario.setLote(stocked.getLote());
            inventario.setCosto(Constantes.COSTO_GENERICO);
            inventario.setCostoUnidosis(Constantes.COSTO_GENERICO);
            inventario.setExistenciaInicial(stocked.getCantidad());
            inventario.setActivo(Constantes.ACTIVOS);
            inventario.setInsertIdUsuario(Constantes.ID_ADMIN);  //idusuario  DE ADMIN
            inventario.setInsertFecha(dateHora);
            inventario.setClaveProveedor("0");//para que active la Unique key y no genere inventarios duplicados.
            inventario.setCantidadXCaja(1); //validar, por ahora 1 como en gabinete es unidosis
            inventario.setCantidadActual(stocked.getCantidad());
            inventario.setPresentacionComercial(0);  //validar,  0 = unidosis , 1 = caja
        }
        //llenar objeto movimientoInventario
        movInventario.setIdMovimientoInventario(Comunes.getUUID());
        movInventario.setIdInventario(inventario.getIdInventario());
        movInventario.setCantidad(stocked.getCantidad());
        movInventario.setFecha(dateHora);
        movInventario.setIdEstrutcuraOrigen(claveAlmacen.getIdEstructura());
        movInventario.setIdEstrutcuraDestino(claveAlmacen.getIdEstructura());
        movInventario.setIdUsuarioMovimiento(Constantes.ID_ADMIN);
        movInventario.setFolioDocumento(Comunes.getUUID().substring(1, 8));
        if (!existeInventario) {
            movInventario.setIdTipoMotivo(TipoMotivo_Enum.ENT_REABA_POR_SURTIMIENTO_DE_REABASTO.getMotivoValue());            
        } else {            
            movInventario.setIdTipoMotivo(TipoMotivo_Enum.MODIFICACION_CANTIDAD_ACTUAL.getMotivoValue());//Tiene que ser ajuste        
        }

    }

    private MovimientoInventario llenaMovInventarioDeCeros(MovimientoInventario movimientoInventario, Inventario idInventarioConCeros) {
        movimientoInventario.setIdMovimientoInventario(Comunes.getUUID());
        movimientoInventario.setIdInventario(idInventarioConCeros.getIdInventario());
        movimientoInventario.setCantidad(0);// 0 este valor ya que no existe en el gabinete.
        movimientoInventario.setFecha(new Date());
        movimientoInventario.setIdEstrutcuraOrigen(idInventarioConCeros.getIdEstructura());
        movimientoInventario.setIdEstrutcuraDestino(idInventarioConCeros.getIdEstructura());
        movimientoInventario.setIdUsuarioMovimiento(Constantes.ID_ADMIN);
        movimientoInventario.setFolioDocumento(Comunes.getUUID().substring(1, 8));
        movimientoInventario.setIdTipoMotivo(TipoMotivo_Enum.SAL_AJUSTE_INVENTARIO.getMotivoValue());        
        return movimientoInventario;
    }

    /**
     * Web service operation
     *
     * @param stockList
     * @return
     * @throws java.text.ParseException
     */
    @WebMethod(operationName = "InsertForRestockList")
    @WebResult(name = "respuesta")
    public IntipharmRespuesta intsertForRestock(@WebParam(name = "InsertForRestock") List<IntipharmVerfStock> stockList) throws Exception{
        if(stockList != null){
            LOGGER.trace(String.format(stockList.toString()));
        }        

        IntipharmRespuesta respuesta = new IntipharmRespuesta();
        StringBuilder valmensaje = new StringBuilder();
        boolean actualizaInventarioExistente = false;
        boolean valValor;
        List<Inventario> inventarioList = new ArrayList<>();        
        List<MovimientoInventario> movimientoInventarioList = new ArrayList<>();
        boolean existeInventario;
        Inventario inventario;
        Inventario inventarioInsert;
        MovimientoInventario movimientoInventario;
        List<String> idInventarioList = new ArrayList<>();
        Config config = new Config();
        Estructura claveAlmacen;
        config.setNombre(Constantes.PARAM_SYSTEM_CUADRA_GABINETE_MUS);
        config.setActiva(Constantes.ACTIVO);
        try {
            if (!stockList.isEmpty()) {
                config = configService.obtener(config);
                if (config != null
                        && config.isActiva()
                        && config.getValor().equals(String.valueOf(Constantes.ACTIVOS))) {
                    actualizaInventarioExistente = true;
                }
                for (IntipharmVerfStock stocked : stockList) {
                    //Se agrega a true nuevamente.
                    valValor = true;

                    valValor = validaCamposRestock(valValor, stocked, valmensaje);
                    if (valValor) {
                        //fechaHora
                        Date dateHora;
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        dateHora = formatter.parse(stocked.getFechaHora());
                        movimientoInventario = new MovimientoInventario();
                        Medicamento medica = medicamentoService.obtenerMedicaByClave(stocked.getClaveInstitucional());
                        if (medica != null) {
                            claveAlmacen = estructuraService.getEstructuraForClave(stocked.getAlmacen());
                            if (claveAlmacen != null) {
                                inventario = getInventarioUpdate(stocked);
                                if (inventario != null) {
                                    existeInventario = true;
                                    if (!inventario.getCantidadActual().equals(stocked.getCantidad())) {
                                        inventario.setUpdateFecha(dateHora);
                                        inventario.setCantidadActual(stocked.getCantidad());
                                        inventarioList.add(inventario);
                                        cargarInventarioMovInventario(inventario, movimientoInventario, dateHora, stocked, medica, claveAlmacen, existeInventario);
                                        if (actualizaInventarioExistente) {
                                            movimientoInventarioList.add(movimientoInventario);
                                        }
                                    }
                                    inventarioList.add(inventario);
                                    idInventarioList.add(inventario.getIdInventario());                                   
                                } else {
                                    existeInventario = false;
                                    inventarioInsert = new Inventario();
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                                    Date caducidad = sdf.parse(stocked.getFechaCaducidad());
                                    inventarioInsert.setFechaCaducidad(caducidad);
                                    cargarInventarioMovInventario(inventarioInsert, movimientoInventario, dateHora, stocked, medica, claveAlmacen, existeInventario);                                    
                                    inventarioService.insertaNuevoInventario(inventarioInsert, movimientoInventario);
                                }
                            }
                        }
                    }
                }
                if (!inventarioList.isEmpty()) {
                    List<Inventario> idInventariosConCeros;
                    idInventariosConCeros = inventarioService.getListByIdInventario(idInventarioList);
                    if (!idInventariosConCeros.isEmpty()) {

                        for (Inventario idInventarioCero : idInventariosConCeros) {
                            movimientoInventario = new MovimientoInventario();
                            movimientoInventario = llenaMovInventarioDeCeros(movimientoInventario, idInventarioCero);
                            movimientoInventarioList.add(movimientoInventario);
                        }
                    }

                    valValor = inventarioService.actualizarDatosForStocksList(//idEstructura,
                            inventarioList, idInventariosConCeros, movimientoInventarioList, actualizaInventarioExistente);
                    if (valValor) {
                        respuesta.setError(false);
                        respuesta.setMensaje(RESOURCES.getString("intiPharm.info.updateRestock"));
                    } else {
                        respuesta.setError(true);
                        respuesta.setMensaje(RESOURCES.getString("intiPharm.err.updateRestock"));
                    }
                }
            } else {
                respuesta.setError(true);
                respuesta.setMensaje("Error en el Método InsertForStockList, Lista Vacía");
            }
        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            respuesta.setError(true);
            respuesta.setMensaje("Error en el Método InsertForStock");
        }

        return respuesta;
    }
}
