package mx.mc.init;

import java.math.BigDecimal;
import java.util.Date;
import java.util.regex.Pattern;

/**
 *
 * @author hramirez
 */
public class Constantes {

    public static final String GLOBAL_MSG = "i18n.validation";
    public static final String GLOBAL_PARAM = "i18n.messages";

    public static final boolean ACTIVO = true;
    public static final boolean INACTIVO = false;
    public static final String NULO = null;

    public static final Integer ES_ACTIVO = 1;
    public static final Integer ES_INACTIVO = 0;

    public static final String DIA_ACTIVO = "1";
    public static final String DIA_INACTIVO = "0";

    public static final String INSERT = "INSERT";
    public static final String UPDATE = "UPDATE";

    public static final String URL_GATO = "#";
    public static final String TXT_VACIO = "";
    public static final String TXT_ESPACIO = " ";

    public static final Integer PRESCRIPCION_DURACION = 1;
    protected static final Date PRESCRIPCION_FECHA_INICIO = new java.util.Date();

    public static final String PARAM_SYSTEM_CORREO_SERVIDOR = "con_correoServidor";
    public static final String PARAM_SYSTEM_CORREO_PUERTO = "con_correoPuerto";
    public static final String PARAM_SYSTEM_CORREO_NOMBREUSUARIO = "con_correoNombreUsuario";
    public static final String PARAM_SYSTEM_CORREO_CONTRASENA = "con_correoContrasena";
    public static final String PARAM_SYSTEM_CORREO_PROTOCOLO = "con_correoProtocolo";
    public static final String PARAM_SYSTEM_CORREO_SMTPAUTH = "con_correoSmtpAuth";
    public static final String PARAM_SYSTEM_CORREO_STARTTLS = "con_correoStartTls";
    public static final String PARAM_SYSTEM_CORREO_DEPURA = "con_correoDepura";

    public static final String PARAM_SYSTEM_NODIASCONTRASENAEXPIRE = "con_noDiasContrasenaExpire";
    public static final String PARAM_SYSTEM_PRESCRIPCION24HRS = "fun_prescripcioneMayores24Hrs";
    public static final String PARAM_SYSTEM_HORACORTESURTIMIENTO = "fun_hora_corte_surtimiento";
    public static final String PARAM_SYSTEM_DIASPARACADUCIDAD = "fun_no_dias_para_caducidad";
    public static final String PARAM_SYSTEM_PRESCRIBE_SIN_EXISTENCIAS = "fun_permite_precribir_sin_existencias";
    public static final String PARAM_SYSTEM_DIASRESURTIMIENTO = "fun_no_dias_resurtimiento";
    public static final String PARAM_SYSTEM_PACIENTECONCITA = "fun_paciente_con_cita";
    public static final String PARAM_SYSTEM_HORASCANCELACION = "fun_no_horas_permite_cancelacion";
    public static final String HORA_CORTE_SURTIMIENTO = "00:01";

    public static final String PARAM_SYSTEM_PARAMETRO1_SEMAFORIZACION = "fun_parametro_semaforo:180";
    public static final String PARAM_SYSTEM_PARAMETRO2_SEMAFORIZACION = "fun_parametro2_semaforo:360";
    public static final String PARAM_SYSEM_HOSP_CHICONCUAC = "fun_hospChiconcuac";
    public static final String PARAM_SYSTEM_TRANSFORMACION_CLAVES = "fun_transformacion_claves";
    public static final String PARAM_SYSTEM_AUTOCOMPLETE_BY_QR_CLAVE_BARRAS = "fun_autocomplete_qr_clave";
    public static final String PARAM_SYSTEM_ACTIVA_CAMPO_REABASTO_CLAVE = "fun_solicitud_reabasto_cantidad_x_clave";
    public static final String PARAM_SYSTEM_ACTIVA_CAMPO_REABASTO_LOTE = "fun_solicitud_reabasto_cantidad_x_lote";
    public static final String PARAM_SYSEM_HOSP_TOLUCA = "fun_hospToluca";
    public static final String PARAM_SYSEM_CANT_CERO = "fun_cant_cero";
    public static final String PARAM_SYSEM_HOSP_PRODEMEX = "fun_hospProdemex";
    public static final String PARAM_SYSEM_DOTACION_DIARIA_CURACION = "fun_dotacion_diaria_med_curacion";
    public static final String PARAM_SYSEM_CANTIDAD_RAZONADA = "fun_cantidadRazonada";
    public static final String PARAM_SYSYTEM_DEVOLUCION_SIN_PISTOLEO = "fun_devolucion_sin_pistoleo";
    public static final String PARAM_SYSTEM_DOTACION_DIARIA = "fun_DotacionDiaria";
    public static final String PARAM_SYSYTEM_RECEPCION_LAYOUT_CON_INGRESO_PRELLENADO = "func_carga_reabasto_layout_con_ingreso_prelle";
    public static final String PARAM_SYSTEM_COLECTIVO_SERVICIOS = "fun_colectivo_servicios";
    public static final String PARAM_SYSYTEM_CANTIDAD_CERO = "fun_cant_cero";
    public static final String PARAM_SYSTEM_SEPARAR_INSUMOS = "fun_separacion_insumos_grupo";
    public static final String PARAM_SYSTEM_SURTIMIENTO_SABADO = "fun_Surtimiento_Sabado";
    public static final String PARAM_SYSTEM_SURTIMIENTO_DOMINGO = "fun_Surtimiento_Domingo";
    public static final String PARAM_SYSTEM_SURTIMIENTO_DURACION = "fun_Duracion_SurtManual_Hosp";
    public static final String PARAM_SYSTEM_SURT_MANUAL_HOSP_NOHORAS_POST_FECHA_PRESC = "fun_Surt_Manual_Hosp_NoHoras_Post_FechaPresc";
    public static final int SURT_MANUAL_HOSP_NOHORAS_POST_FECHA_PRESC = 24;
    public static final int SESION_LIMITE_MINUTOS = 30;
    public static final String UNIT_OF_MEASURE = "EA";
    public static final String USER_SUPERVISOR = "SUPERVISOR";
    public static final String USER_RESTOCK = "Restock";
    public static final String ORDER_TYPE = "STK";
    public static final String ORDER_ESTATUS_ACTIVE = "A";
    public static final String ORDER_NOMENCLATURE = "HTS";
    public static final Integer ESTATUS_ACTIVO_PROCESO = 1;
    public static final Integer IS_OK = 0;
    public static final String ORDER_NOMENCLATURE_SOLICITUD = "HSP";
    public static final String ORDER_TYPE_SOLICITUD = "PIK";
    public static final String COD_ESTATUS_RECETA_ACTIVA = "A";//    
    public static final String ORDER_NOMENCLATURE_RECETA = "HSP";
    public static final String ORDER_TYPE_RECETA = "PIK";
    public static final String ORDER_NOMENCLATURE_DEVOLUCION = "HTS";
    public static final String ORDER_TYPE_DEVOLUCION = "STK";

    public static final String PART_MASTER_CELL_SIZE = "S";
    public static final String PART_MASTER_PREF_CODE = "1";
    public static final Integer PART_MASTER_PREF_ZONE = 1;
    public static final Integer PART_MASTER_MAX_CELL_QTY = 999999;
    public static final String PART_MASTER_UNIT_OF_MEASURE = "EA";
    public static final String PART_MASTER_STORAGE_RULE = "T";
    public static final Integer PART_MASTER_REPLEN_TRIGGER_LEVEL = 0;
    public static final Integer PART_MASTER_PIECES_PER_CASE = 9999;
    public static final Integer PART_MASTER_MULTIPLE_SEC_PARTS_IN_LOC = 0;
    public static final Integer PART_MASTER_EXPIRATION_SENSITIVE = 0;
    public static final Integer PART_MASTER_PICK_FENCE_QTY = 0;
    public static final Integer PART_MASTER_ZONE_MAX_QTY = 999999;
    public static final Integer PART_MASTER_SECONDARY_ZONE_FLAG = 0;
    public static final Integer PART_MASTER_LOT_CONTROLLED = 0;
    public static final BigDecimal PART_MASTER_PIECE_WEIGTH = BigDecimal.ZERO;
    public static final String PART_MASTER_PIECE_WEIGTH_UOM = "          ";
    public static final Integer PART_MASTER_NO_TOPOFF_DAYS = 0;

    public static final String CTX_PARAM_APP_DEBUG = "APP_DEBUG";
    public static final String CTX_PARAM_APP_AMBIENTE = "APP_AMBIENTE";
    public static final String CTX_PARAM_APP_THEME = "primefaces.THEME";
    public static final Integer REGISTROS_PARA_MOSTRAR = 10;

    public static final int TIPO_DE_PACIENTE = 1;
    public static final int UNIDAD_MEDICA = 2;
    public static final int ESTADO_CIVIL = 3;
    public static final int ESCOLARIDAD = 4;
    public static final int OCUPACION = 5;
    public static final int GPO_ETNICO = 6;
    public static final int RELIGION = 7;
    public static final int GRUPO_SANGUINEO = 8;
    public static final int NVEL_SOC_ECONOM = 9;
    public static final int TIPO_VIVIENDA = 10;
    public static final int ID_PAIS_MEXICO = 141;
    public static final int TIPO_INSUMO = 12;

    public static final int ID_VACIO = 0;
    public static final int ESTATUS_ACTIVO = 1;
    public static final int ESTATUS_INACTIVO = 0;

    public static final String PROGRAMADA = "PROGRAMADA";
    public static final String POR_VALIDAR = "POR_VALIDAR";
    public static final String VALIDADA = "VALIDADA";
    public static final String RECHAZADA = "RECHAZADA";
    public static final String SURTIDA = "SURTIDA";
    public static final String SURTIDA_PARCIAL = "SURTIDA_PARCIAL";
    public static final String CANCELADA = "CANCELADA";
    public static final String SOSPECHA = "SOSPECHA";
    public static final String NOTIFICADA = "NOTIFICADA";
    public static final String CONFIRMADA = "CONFIRMADA";
    public static final String PREPARADA = "PREPARADA";
    public static final String ERROR_AL_PREPARAR = "ERROR_AL_PREPARAR";
    public static final String MEZCLA_CORRECTA = "MEZCLA_CORRECTA";
    public static final String MEZCLA_INCORRECTA = "MEZCLA_INCORRECTA";
    public static final String ACONDICIONADA = "ACONDICIONADA";
    public static final String ENTREGADA = "ENTREGADA";
    public static final String NO_ENTREGADA = "NO_ENTREGADA";
    public static final String NO_ACONDICIONADA = "NO_ACONDICIONADA";
    public static final String EN_TRANSITO = "EN_TRANSITO";

    //tipo viaAdministracion = NO DEFINIDA
    public static final int NO_DEFINIDA = 51;

    //tipo idUnidadConcentracion = 3 Unidades
    public static final int UNIDADES = 3;

    //idSustanciaActiva = NO DEFINIDA 1937
    public static final int SUSTANCIA_ACTIVA_NO_DEFINIDA = 1937;

    //Tipo de Insumos
    public static final int MEDI = 38;
    public static final int MATC = 39;
    public static final int PROT = 40;
    public static final String PATH_TMP = "/WebContent/resources/tmp";
    public static final String PATH_APP = "/resources/app/";
    public static final String PATH_APP_TMP = "/resources/app/tmp/";
    public static final String PATH_IMG = "/resources/tmp/";
    public static final String PATH_IMG2 = "resources\\tmp\\";

    public static final int ESTATUS_ORDEN_CANCELADO = 1;

    public static final int TIPO_AREA_ALMACEN = 2;
    public static final int TIPO_AREA_SERVICIO = 3;
    public static final int NIVEL_SUBALMACEN = 4;

    public static final int ALMACEN_FARMACIA = 2;
    public static final int ALMACEN = 3;
    public static final int SUBALMACEN = 4;
    public static final String PREFIX_FARMACIA = "OC";
    public static final String PREFIX_ALMACEN = "RE";
    public static final String PREFIX_TRANSFER = "TR";
    public static final String PRESC_VERBAL = "V";
    public static final String PRESC_NORMAL = "N";
    public static final int TIPO_ORDEN_NORMAL = 1;
    public static final int TIPO_ORDEN_EXTRA = 2;

    public static final int ESTATUS_REABASTO_REGIS = 1;
    public static final int ESTATUS_REABASTO_SOLIC = 2;
    public static final int ESTATUS_REABASTO_FARM = 3;
    public static final int ESTATUS_REABASTO_ENTRANS = 4;
    public static final int ESTATUS_REABASTO_RECIBIDA_PARCIAL = 5;
    public static final int ESTATUS_REABASTO_RECIBIDA = 6;
    public static final int ESTATUS_REABASTO_CANCELADA = 7;
    public static final int ESTATUS_REABASTO_INGRESADA = 8;
    public static final char PACIENTE_PARTICULAR = 'N'; // Verificar el valor que debe de llevar.

    public static final String LOTE_GENERICO = "LOTE-UNICO";
    public static final String CADUCIDAD_GENERICA = "9999-12-31";

    public static final String PATRON_CAD_BUS = "[A-Z]{2}[0-9]{6}";
    public static final String PATRON_CAD_BUS_SURT_EXT = "[0-9a-zA-Z]{7}";
    public static final String SEPARADOR_CODIGO = ",";
    public static final int ID_MERMA = 19;
    public static final int TIPO_MOV_SURT_REABASTO = 1;
    public static final int ID_TIPO_USUARIO = 1;

    public static final int SALIDA_DEVOLUCION = 6;
    public static final int ENTRADA_DEVOLUCION = 2;
    public static final int MOTIVO_RETIRO_MEZCLA = 13;
    public static final int DESTINO_RETIRO_MEZCLA = 14;
    public static final int CLASIFICACION_MEZCLA = 15;
    public static final int RIESGO_PACIENTE_MEZCLA = 16;

    public static final int DEV_REGISTRADA = 1;
    public static final int DEV_GUARDADA = 2;
    public static final int DEV_EN_TRANSITO = 3;
    public static final int DEV_RECIBIDA = 4;
    public static final int DEV_INGRESADA = 5;
    public static final int INSUMO_DANIADO = 17;

    public static final int SIN_TIPO_PROCESO = 0;
    public static final int TIPO_PROCESO_MEDICO = 1;
    public static final int TIPO_PROCESO_QUIMICO = 2;

    public static final String TIPO_MOTIVO_EGRESO = "E";
    public static final String TIPO_MOTIVO_INGRESO = "I";

    public static final String IP_IMPRESORA = "192.168.10.186";
    public static final String NO_DEFINIDO = "No definido";
    public static final String PACIENTE_EMERG_NOMBRE = "PACIENTE";
    public static final String PACIENTE_EMERG_APEPAT = "DE EMERGENCIA";

    public static final String VISTA_DIA = "agendaDay";
    public static final String VISTA_MES = "month";

    public static final Pattern regexUser = Pattern.compile("^[A-Za-z0-9ü][A-Za-z0-9áéíóúü_]{2,15}$");
    public static final Pattern regexNomAp = Pattern.compile("^[A-Za-z0-9áéíóúüÁÉÍÓÚ\\s]{2,20}$");
    public static final Pattern regexMail = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    public static final Pattern regexCedu = Pattern.compile("^[0-9]{3,10}$");
    //Regex para el modulo usuarios (keyFilter)
    public static final Pattern regexNombApell = Pattern.compile("/[A-Za-záéíóúü\\s]/i");
    public static final Pattern regexUsuario = Pattern.compile("/[A-Za-z0-9]/i");
    //Regex para el modulo Medicamento (keyFilter)
    public static final Pattern regexClave = Pattern.compile("^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{6,10}$");
    public static final Pattern regexMed = Pattern.compile("/[A-Za-z0-9_\\-\\.\\%\\(\\)\\:\\s,áéíóúüÁÉÍÓÚ//]/i");
    public static final Pattern regexNumber = Pattern.compile("/[0-9]/i");
    public static final Pattern regexBinario = Pattern.compile("/[0-1]/i");
    public static final Pattern regexDecimal = Pattern.compile("/[0-9-.]/i");
    public static final Pattern regexNumSing = Pattern.compile("/[+]?[-]?[1-9]/i");
    public static final Pattern regexIp = Pattern.compile("^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
    public static final Pattern regexUrl = Pattern.compile("/(https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|www\\.[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9]+\\.[^\\s]{2,}|www\\.[a-zA-Z0-9]+\\.[^\\s]{2,})/i");
    public static final Pattern regexFormatFecha = Pattern.compile("^\\d{4}([\\-/.])(0?[1-9]|1[1-2])\\1(3[01]|[12][0-9]|0?[1-9])$");
    public static final Pattern regexFormatFechas = Pattern.compile("^([1-9]|([012][0-9])|(3[01]))-([0]{0,1}[1-9]|1[012])-\\d\\d\\d\\d");
    public static final Pattern regexFormatFechas_9999 = Pattern.compile("^([1-9]|([012][0-9])|(3[01]))-([0]{0,1}[1-9]|1[012])-\\d\\d\\d\\d (20|21|22|23|[0-1]?\\d):[0-5]?\\d:[0-5]?\\d$");
    public static final Pattern regexNumeros = Pattern.compile("/^[0-9]+$/");
    public static final Pattern regexFechaCompleta = Pattern.compile("[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]) (2[0-3]|[01][0-9]):[0-5][0-9]:[0-5][0-9]");
    public static final Pattern fechaFormatoRefill = Pattern.compile("^([1-9]|([012][0-9])|(3[01]))-([0]{0,1}[1-9]|1[012])-\\d\\d\\d\\d (20|21|22|23|[0-1]?\\d):[0-5]?\\d:[0-5]?\\d$"); //ejemplo de formato "22-04-2020 15:30:00"
    public static final Pattern folioRecetaColectiva = Pattern.compile("[0-9]{2}/[0-9]{3}");
    public static final Pattern fechaDisp = Pattern.compile("^(((0[1-9]|[12]\\d|3[01])[\\/\\.-](0[13578]|1[02])[\\/\\.-]((19|[2-9]\\d)\\d{2})\\s(0[0-9]|1[0-2]):(0[0-9]|[1-59]\\d):(0[0-9]|[1-59]\\d)\\s(A. M.|a. m.|P. M.|p. m.))|((0[1-9]|[12]\\d|30)[\\/\\.-](0[13456789]|1[012])[\\/\\.-]((19|[2-9]\\d)\\d{2})\\s(0[0-9]|1[0-2]):(0[0-9]|[1-59]\\d):(0[0-9]|[1-59]\\d)\\s(A. M.|a. m.|P. M.|p. m.))|((0[1-9]|1\\d|2[0-8])[\\/\\.-](02)[\\/\\.-]((19|[2-9]\\d)\\d{2})\\s(0[0-9]|1[0-2]):(0[0-9]|[1-59]\\d):(0[0-9]|[1-59]\\d)\\s(A. M.|a. m.|P. M.|p. m.))|((29)[\\/\\.-](02)[\\/\\.-]((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))\\s(0[0-9]|1[0-2]):(0[0-9]|[1-59]\\d):(0[0-9]|[1-59]\\d)\\s(A. M.|a. m.|P. M.|p. m.)))$");

    //Regex para modulo de inventarioSalida
    public static final Pattern regexLote = Pattern.compile("/[A-Za-z0-9-.]/i");

    public static final String TIPO_AREA_GENERAL = "G";
    public static final String TIPO_AREA_DETALLE = "D";

    public static final Integer PARAMETRO_RECEPCION_MIXTA = 1;
    public static final boolean PARAMETRO_SURTIR_SOLO_SOLICITADO_MAX = false;
    public static final String ESTATUS_VIEW = "estatus";
    public static final String EXISTE_ALERTA = "existeAlerta";
    public static final String ESTATUS_PRINT = "printReport";

    public static final String IDESTRUCTURA_PROVEEDOR = "37c814e6-56d9-49e1-bff1-364da7d31617";
    public static final String IDESTRUCTURA_EXTERNA_HOSPITAL = "50cb40df-6fac-4745-a8fd-5b6949521700";
    public static final String IDESTRUCTURA_EXTERNA_CLINICA = "504c2e4c-123b-4c70-9e06-3f1981591978";
    public static final String ID_ESTRUCTURA_HOSPITAL = "980704e1-7136-11e7-9ae3-525400dd67aa";
    public static final String ID_ESTRUCTURA_ALMACEN_FARMACIA = "13f4c578-0108-49bf-8604-3600777b04d7";
    public static final String ID_ESTRUCTURA_INTRAHOSPITALARIA = "4284311e-49e7-4d87-9c06-8d3fefdfeb4b";
    public static final String ID_ADMIN = "15261c4b-4071-11e9-9cba-525400dd67aa";

    public static final String IDESTRUCTURA_CONSULTA_EXTERNA_HOSPITAL = "6be6a6dd-4295-4b22-8c3b-6c7cb349a3ea";
    public static final String IDESTRUCTURA_CONSULTA_EXTERNA_CLINICA = "38aca63a-fc2a-44e4-bed8-c56889f8fdfe";
    public static final String IDESTRUCTURA_CADIT = "5b43ef53-9a01-433d-bb85-8197abd116a0";

    public static final String PARAM_SYSTEM_FUNC_DIMESA = "fun_ws_dimesa_activo";
    public static final String PARAM_SYSTEM_DIMESA_WS_USUARIO = "con_dimesa_usuario";
    public static final String PARAM_SYSTEM_DIMESA_WS_CONTRASENIA = "con_dimesa_clave";
    public static final String PARAM_SYSTEM_DIMESA_WS_URL = "con_dimesa_url";
    public static final String PARAM_SYSTEM_DIMESA_WS_FARMACIA_HOSPITAL = "con_dimesa_num_Hospital";
    public static final String PARAM_SYSTEM_DIMESA_WS_FARMACIA_CLINICA = "con_dimesa_num_clinica";
    public static final String PARAM_SYSTEM_SURTIMIENTO_MIXTO = "fun_surtimiento_receta_mixta";
    public static final String PARAM_SYSTEM_SURTIMIENTO_SIN_PISTOLEO = "fun_surtimiento_sin_pistoleo";
    public static final String PARAM_SYSTEM_RECIBIR_SIN_PISTOLEO = "fun_recibir_sin_pistoleo";
    public static final String PARAM_SYSTEM_INGRESO_SIN_PISTOLEO = "fun_ingreso_sin_pistoleo";
    public static final String PARAM_REABASTO_AUTOMATICO_MANUAL = "fun_reabasto_automatico_manual";
    public static final String PARAM_SURTIMIENTO_PROVEEDOR_AUTOMATICO = "mod_surt_proveedor_automatico";

    public static final String PARAM_SYSTEM_USAR_CANTIDAD_COMPROMETIDA = "fun_utilizar_cantidad_comprometida";
    public static final String PARAM_SYSTEM_GESTION_INSUMOS_POR_AUTOCOMPLETE = "fun_gestion_insumos_autocomplete";
    public static final String PARAM_SYSTEM_REGISTRAR_ORIGEN_INSUMOS = "fun_registrar_origen_insumos";
    public static final String PARAM_SYSTEM_SESSION_TIMEOUT = "con_session_timeout";
    public static final String PARAM_SYSTEM_CUADRA_GABINETE_MUS = "fun_cuadrar_inventario_gabinete_mus";
    public static final int HTTP_SESSION_TIMEOUT_60_SEGUNDOS = 60;
    public static final String PARAM_SYSTEM_CANTIDAD_CAJA = "fun_muestra_cantidad_por_caja";

    public static final String DIRECCION_EXTERNA_HOSPITAL = "HOSPITAL CENTRO MÉDICO MOTION CORP, BLVD ADOLFO LÓPEZ MATEOS 2777, PROGRESO, 01080, DEL ALVARO OBREGON CDMX";
    public static final String DIRECCION_EXTERNA_CLINICA = "HOSPITAL CENTRO MÉDICO MOTION CORP, BLVD ADOLFO LÓPEZ MATEOS 2777, PROGRESO, 01080, DEL ALVARO OBREGON CDMX";
    public static final String ESTRUCTURA_MEDICO_CONSEXT = "9509455b-8836-4db7-b781-67fefb01663f";
    public static final String CLAVE_GENERICA = "usuario123";

    public static final String DIMESA_ID_PADECIMIENTO_NO_DEFINIDO = "999";
    public static final String DIMESA_DESCRIPCION_PROGRAMA = "ISMYM";

    public static final Pattern REGEX_NAME_MEDICAM = Pattern.compile("[a-z,A-Z]");
    public static final Integer POR_REABASTO = 0;
    public static final Integer POR_TRANSFER = 1;
    public static final Integer POR_DEVOLUCION = 2;

    public static final String ROL_MEDICO_FAMILIAR = "7418df44-650f-4227-b2c5-ccbdb985c611";

    public static final String USUARIO_GENERICO = "Mirth";
    public static final String USUARIO_SERVICIOS_WEB = "WebService";

    public static final String DOMINIO_CORREO = "@dominio.com";
    public static final Integer ANIOS_VIGENCIA_USUARIO = 5;
    public static final double COSTO_GENERICO = 1;

    public static final String COMPLETE = "C";
    public static final String RECEIVED = "R";
    public static final String CANCELLED = "X";
    public static final String MSJ_RECIBIDO = "RECEIVED";
    public static final String MSJ_NO_RECIBIDO = "NOT RECEIVED";
    public static final String MSJ_COMPLETADO = "COMPLETED";
    public static final String MSJ_NO_COMPLETADO = "NOT COMPLETED";
    public static final String MSJ_OBTENIDO = "DATOS OBTENIDOS CORRECTAMENTE";
    public static final String MSJ_VACIO_NULO = "LOS VALORES ESTAN VACIOS Ó NULOS";
    public static final String MSJ_FOLIO_PACIENTE = "ERROR, EL FOLIO y/O PACIENTE_NUMERO NO EXISTEN";
    public static final String SIN_INSUMO = "NO SE ENCONTRÓ EL INSUMO";
    public static final String SIN_HABILITADO_HL7 = "EL ALMACÉN/SERVICIO ES INCORRECTO";
    public static final String ACTU_SURT_INTIPHARM = "SE ACTUALIZÓ CORRECTAMENTE EL SURTIMIENTO";
    public static final String ERR_ACTU_SURT_INTIPHARM = "ERROR AL ACTUALIZAR EL SURTIMIENTO";
    public static final String ESTATUS_INTIPHARM_INCORRECT = "EL ESTATUS ES INCORRECTO";
    public static final String DATOS_VACIOS = "LOS DATOS DEBEN LLENARSE";

    public static final String PARAM_SYSTEM_MINISTRA_PREVDAYS = "fun_ministra_previo";
    public static final String PARAM_SYSTEM_MINISTRA_LATERHOURS = "fun_ministra_posterior";

    public static final int ACTIVOS = 1;

    public static final String PARAM_SYSTEM_CUPSPRINT_IP = "con_cups_url";
    public static final String PARAM_SYSTEM_CUPSPRINT_PORT = "con_cups_port";

    public static final String PARAM_SYSTEM_SURTIMIENTO_CAPSULA = "fun_registro_capsula_envio";

    public static final int MINISTRACION_NO_CONFIRMADA = 0;
    public static final int ADMINISTRADOR = 1;
    public static final int JEFE_AREA = 2;
    public static final int NO_ADMINISTRADOR = 0;
    public static final int NO_JEFE_AREA = 0;
    public static final String SEPARADOR_CLAVE = "\\.";

    public static final String PARAM_SYSEM_PERMITE_AJUSTE_INEVTARIO_GLOBAL = "fun_permiteAjusteInventarioGlobal";
    public static final String PARAM_SYSTEM_PERMITE_VISUALIZAR_SEMAFORO = "fun_mostrarSemaforo";
    public static final String PARAM_SYSTEM_PERMITE_CANCELAR_SURTIMIENTO = "fun_cancelar_surtimiento";
    public static final String PARAM_SYSTEM_ACTIVA_CANCELAR_EDITAR_SURTMANUAL = "fun_surtimento_receta_manual";
    public static final String PARAM_SYSTEM_PERMITE_SURTIMIENTO_MANUAL_HOSPITALIZACION = "fun_surtimento_manual_hospitalizacion";
    public static final String PARAM_SYSTEM_ACTIVA_ELIMINAR_CODIGOMEDICAMENTO = "fun_orden_reabasto";
    public static final String PARAM_SYSTEM_ACTIVA_CAMPOS_REP_CONSULTA_RECETAS = "fun_activaParametrosEmicionRecetas";
    public static final String PARAM_SYSTEM_ACTIVA_CAMPOS_REP_MOVIMIENTOSGENERALES = "fun_acticaCamposRepMovGenerales";
    public static final String PARAM_SYSTEM_ACTIVA_CAMPOS_REP_ACUMULADOS = "fun_activaCamposReporteAcumulados";
    public static final String PARAM_SYSTEM_ACTIVA_CAMPOS_CANCEL_SURT_COLECTIVO = "fun_activa_CancelarSurtColectivo";
    public static final String TITULO_EDIT_ROL = "Editar Rol";
    public static final String TITULO_CREA_ROL = "Crear Rol";

    public static final String PARAM_SYSTEM_FUNC_SIAP = "fun_validacion_siap";
    public static final String PARAM_SYSTEM_SIAP_WS_URL = "con_siap_url";
    public static final String PARAM_SYSTEM_DELEGACION = "con_delegacion";
    public static final String PARAM_SYSTEM_MAXIMO_NUMERO_INTENTOS_ACCESO_FALLIDOS = "con_maximoNumeroIntentosAccesoFallidos";
    public static final String PARAM_SYSTEM_URL_PORATL = "con_urlPortal";
    public static final String PARAM_SYSTEM_REMITENTE_CORREO = "con_remitenteCorreo";
    public static final String PARAM_SYSTEM_REMITENTE_NOMBRE = "con_remitenteNombre";
    public static final String PARAM_SYSTEM_METODO_CALCULO = "con_metodoCalculaSuperfCorp";

    public static final String PARAM_SYSTEM_FUNC_NUMHRSPREV_PRESCRIPCION = "fun_numHrsPrevPrescripcion";
    public static final String PARAM_SYSTEM_FUNC_NUMHRSPOST_PRESCRIPCION = "fun_numHrsPostPrescripcion";
    public static final String PARAM_SYSTEM_FUNC_TIPOINSUMO_PRESCRIPCION = "fun_tipoInsumotPrescripcion";

    public static final String ENTIDAD_HOSPTALARIA_ACTIVA = "1";
    public static final String ENTIDAD_HOSPTALARIA_INACTIVA = "0";

    public static final String PARAM_SYSTEM_ACTIVA_MOSTRAR_ORIGEN_INSUMOS = "fun_mostrar_origen_de_insumo";
    public static final String PARAM_SYSTEM_ACTIVA_MOSTRAR_COSTE_POR_LOTE = "fun_mostrar_coste_por_lote";
    public static final String PARAM_SYSTEM_ACTIVA_MOSTRAR_CLAVE_PROVEEDOR = "fun_mostrar_clave_proveedor";
    public static final String PARAM_SYSTEM_ACTIVA_MOSTRAR_UNIDOSIS = "fun_mostar_unidosis";

    public static final String ALMACENES_TODOS = "Todos";

    public static final String PARAM_SYSTEM_HOSP_ORAN = "fun_HospOran";
    public static final String PARAM_SYSTEM_CODIGOS_GS1 = "fun_codigos_gs1";
    public static final String PARAM_SYSTEM_NUM_HRS_ANTERIORES_RECETA = "fun_numHorPrevReceta";
    public static final String PARAM_SYSTEM_NUM_HRS_POSTERIORES_RECETA = "fun_numHorPostReceta";
    public static final String PARAM_SYSTEM_ACTIVA_HABILITA_VALES = "fun_habilitaVales";
    public static final String PARAM_SYSTEM_ACTIVA_NUMERO_MAXIMO_DIAS_RESURTIBLE = "fun_max_numDias_resurtible";
    public static final String PARAM_MOSTRAR_FOLIO_ALTERNATIVO = "fun_mostrar_folioAlternativo";

    public static final String MENSAJE_INFO = "Info";
    public static final String MENSAJE_WARN = "Warn";
    public static final String MENSAJE_ERROR = "Error";
    public static final String MENSAJE_FATAL = "Fatal";

    public static final String PARAM_SYSTEM_WS_SIAM = "fun_ws_siam_activo";
    public static final String PARAM_SYSTEM_WS_SIAM_TOKEN = "fun_ws_siam_token";
    public static final String PARAM_SYSTEM_SIGNATURE_KEY = "con_jwt_signature_key";
    public static final String PARAM_SYSTEM_URL_GENERACOLECTIVO_SIAM = "con_siam_GeneraColectivo";
    public static final String PARAM_SYSTEM_SOLICITUD_INSUMO_X_SERVICIO = "fun_solicitudXServicio";
    public static final String PARAM_SYSTEM_URL_CONSULTAFOLIORECETA = "con_siam_consultaFolioReceta";
    public static final String PARAM_SYSTEM_FUNC_CONEVER_CLAVES = "fun_conversion_claves";
    public static final String PARAM_SYSTEM_URL_CONSULTAINSUMOENCT_SIAM = "con_siam_ConsultaInsumoEnCT";

    public static final String REPORTEcONTROLDIARIO_TODOS_MEDICAMENTOS = "Todos los medicamentos";
    public static final String PARAM_SYSTEM_URL_CONSULTADETALLE_SIAM = "funSiamConsultaDetalle";
    public static final String PARA_CLAVE_CEDIME = "CEDI";
    public static final String PARAM_FUNVALIDARFARMACOVIGILANCIA = "funValidarFarmacoVigilancia";
    public static final String PARAM_SYSTEM_ALMACEN_SERVICIO_MULTIPLE = "funAsigAlmacenServicioMultiple";
    public static final String PARAM_SYSTEM_NUM_DIAS_CICLO = "fun_no_dias_ciclo";
    public static final String PARAM_SYSTEM_NUM_ETIQUETA_IMPRIME_MEZCLA = "fun_mezcla_numEtiquetaImpresas";
    
    public static final String PARAM_SYSTEM_FUNC_HOSPORAN ="fun_HospOran";
    public static final String PARAM_SYSTEM_FUNC_COPIA_PRESCRIPCION ="fun_copiarPrescripcion";
    public static final String PARAM_SYSTEM_FUNC_MEDICAMENTO_PRN ="fun_MedicamentoPRN";
    public static final String PARAM_SYSTEM_TIPO_PRESCRIPCION_MEDICAMENTO="con_parametroTipoPrescripcionMedicamento";
    public static final String PARAM_SYSTEM_ALMACEN_PERIFERICO ="fun_AlmacenPeriferico";
    public static final String PARAM_SYSTEM_TRANSFERIR_ALMACEN_SUPERIOR ="fun_TransferAlmacenSuperior";
    public static final String PARAM_SYSTEM_FUNC_INP_SIA = "fun_interface_INP_SIA";
    public static final String PARAM_SYSTEM_FUN_DIASPREV_CONTRATO="fun_diasPrevContrato";
    public static final String PARAM_SYSTEM_FUN_DIASPOST_CONTRATO="fun_diasPostContrato";
    public static final String PARAM_SYSTEM_FUNC_SELECCION_ESTRUCTURA ="fun_seleccionEstructura";
    public static final String PARAM_SYSTEM_FUNC_SOLIC_EXTRA_SIN_PUNTOS_CTRL="solic_extra_sin_puntos_control";
    public static final String PARAM_SYSTEM_FUN_MEDICO_SIN_ASIGNAR="fun_medicoSinAsignar";
    public static final String PARAM_SYSTEM_FUN_NO_REDONDEAR="fun_noRedondear";
    public static final String PARAM_SYSTEM_FUN_DEVOLUCION_A_FARMACIA="fun_devolucionAFarmacia";

    public static final String TIPO_PRESCRIPCION_CONTROLADO="Medicamento Controlado";
    public static final String TIPO_PRESCRIPCION_SOLUCION_CONTROLADO="Solución Controlado";
    public static final String TIPO_PRESCRIPCION_SOLUCION="Solución no Controlado";
    public static final String TIPO_PRESCRIPCION_NORMAL="Medicamentos No Controlado";
    public static final String TIPO_PRESCRIPCION_CARRO_ROJO="Prescripción Emergencia";
    
    public static final String PARAM_SYSTEM_FUN_REPORTE_SOLICITUD_REABASTO_MTY="fun_reporteSolicitudReabastoMty";
    public static final String PARAM_SYSTEM_FUN_SOLICITUD_POR_SERVICIO="fun_solicitudPorServicio";
    public static final String PARAM_SYSTEM_CON_OCULTAR_DATOS_COMPLEMENTARIOS_REABASTO="con_ocultar_datos_complementarios_reabasto";
    
    public static final String PARAM_SYSTEM_PASS_NUM_CARACTER = "fun_password_numCaracteres";
    public static final String PARAM_SYSTEM_PROTOCOLO_VACIO_PORDEFAULT = "fun_protocoloVacioDefault";

    public static final String PARAM_SYSTEM_NUM_MINUTOS_ENTREGA_MEZCLA = "fun_numMinutosEntregaMezcla";
    public static final String PARAM_SYSTEM_NUM_DIAS_MAX_PRESCR_MEZCLA = "fun_numDiasMaximoPrecripMezcla";
    public static final String PARAM_SYSTEM_RECEPCION_MANUAL_PRELLENADA = "fun_recepcionManualPrellenada";
    
    public static final String PARAM_SYSTEM_NUM_MAX_COMPATIBILIDAD = "con_parametroCompatibilidad";
    public static final String PARAM_SYSTEM_FACTOR_MOL_MONOVA_POTASIO = "con_factorMolMonovaPotasio";
    public static final String PARAM_SYSTEM_FACTOR_MOL_DIVAL_ZINC = "con_factorMolDivalZinc";
    public static final String PARAM_SYSTEM_FACTOR_MOL_DIVAL_ESTABILIDAD = "con_factorMolesDivalEstabilidad";
    public static final String PARAM_SYSTEM_NUM_MAX_ESTABILIDAD = "con_parametroEstabilidad";
    public static final String PARAM_SYSTEM_NUM_MAX_OSMOLARIDAD = "con_parametroOsmolaridad";
    public static final String PARAM_SYSTEM_NUM_MIN_EDITACANCELA = "con_numMinEditaCacela";
    public static final String PARAM_SYSTEM_CORREO_DEST_GRAL_CEN_MEZ = "con_correoDestinatarioGralCenMez";
    
    public static final String PARAM_SYSTEM_MAX_EDAD_PAC = "con_maxEdadPac";
    public static final String PARAM_SYSTEM_MAX_PESO_PAC = "con_maxPesoPac";
    public static final String PARAM_SYSTEM_MAX_ESTATURA_PAC = "con_maxEstaturaPac";
    public static final String PARAM_SYSTEM_MAX_AREA_CORP_PAC = "con_maxAreaCorpPac";
    public static final String PARAM_SYSTEM_MAX_VOL_TOT_MEZ = "con_maxVolTotMez";
    public static final String PARAM_SYSTEM_USO_REMANENTES = "con_usoRemanentes";
    public static final String PARAM_SYSTEM_USO_REMANENTES_SOLUCIONES = "con_usoRemanentesSoluciones";

    public static final String PARAM_SYSTEM_NPT_VIAADMON_DEFAULT = "con_nptDefaultViaAdministracion";
    public static final String PARAM_SYSTEM_NPT_ENVCONT_DEFAULT = "con_nptDefaultEnvaseContenedor";
    public static final String PARAM_SYSTEM_CARACTER_SEPARADOR = ";";

    public static final String PARAM_SYSTEM_LISTA_DIAGNOSTICO = "fun_listarDiagnosticos";
    public static final String PARAM_SYSTEM_FUNC_ULISES = "fun_ulises";

    public static final String PARAM_SYSTEM_CAPTURA_LOTE_CADUCIDAD = "fun_capturaLoteCaducidadManual";
    public static final String PARAM_SYSTEM_NUMERO_FARMACOS_PERMITIDOS = "fun_no_farmacos_permitidos";
    public static final String PARAM_SYSTEM_NUMERO_DILUYENTES_PERMITIDOS = "fun_no_diluyentes_permitidos";
    public static final String PARAM_SYSTEM_FUNC_MODULO_CENTRAL_MEZCLAS = "fun_modulo_central_mezclas";
    public static final String PARAM_SYSTEM_AGRUPAR_MEZCLAS_POR_PRESCRIPCION_AUTORIZAR = "fun_agrupa_mezcla_prescripcion_autorizar";
    
    public static final String PARAM_SYSTEM_VALIDAR_EXISTENCIAS_RESTRICTIVA = "fun_valida_existencias_restrictiva";
    public static final String PARAM_SYSTEM_VALIDAR_EXISTENCIAS_1ER_TOMA = "fun_valida_existencias_1er_toma";
    public static final String PARAM_SYSTEM_VALIDAR_EXISTENCIAS_1ER_DIA = "fun_valida_existencias_1er_dia";
    public static final String PARAM_SYSTEM_VALIDAR_EXISTENCIAS_TOMAS_TOTALES = "fun_valida_existencias_tomas_totales";

    public static final String PARAM_SYSTEM_ENVIA_CORREO_AL_VALIDAR_MEZCLA = "fun_envia_correo_al_validar_mezcla";
    
}
