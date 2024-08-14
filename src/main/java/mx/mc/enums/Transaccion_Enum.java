package mx.mc.enums;

/**
 *
 * @author hramirez
 */
public enum Transaccion_Enum {

    LOGIN("LOGIN", "Inicio Sesión"),
    INICIO("INICIO", "HOME"),
    SEGURIDAD("Seguridad", "SECURE"),
    MEDICAMENTO("Medicamentos","MEDICIN"),
    PRESCRIPCION("prescripciones","PRESCR"),
    PACIENTES("Pacientes","PACIENT"),
    PACIENTES_EGR_ING("Pacientes Egreso Ingreso","INGEGRP"),
    SURTIMIENTO("Surtimiento","SURTIM"),
    USUARIOS("Usuarios","USERS"),
    ALMACENCONTROL("Puntos de Control","CONTRL"),
    ESTRUCTURA("Estructura","ESTRUCT"),
    ORDENCOMPRA("Ordenes de Compra","ORDCOMP"),
    ORDENREABASTO("Ordenes de Reabasto","OREABST"),
    ORDENREABASTOSERVICIO("Solicitud Insumos Servicio","COLINS"),
    DISPENSA_PRESCRIPCION("Dispensa Prescripcion","DISPENS"),
    ORDENRECEPCION("Ordenes de Recepcion","OREPCIO"),
    ORDENINGRESO("Ordenes de Ingreso","ORDING"),
    ASIGNACIONCAMA("Asignacion de cama","ASIGCAM"),
    DEVOLUCION("Devoluciones","DEVOL"),
    DEVOLUCION_INSUMOS("Devoluciones","DEVINSU"),
    INGRESODEVOLUCION("Ingreso Devolucion","INGDEV"),
    MINISTRMED("Ministracion Medicamento","MINSMED"),
    REPORTESBASICOS("Reportes Basicos","REPBAS"),
    REPORTEDOVETRACK("Reportes DOVETRACK","RODV"),
    REPORTESCOSTOS("Reportes de costos","REPCOST"),
    REPORTESMENSAJERIA("Reportes de Mensajería","REPMENS"),
    RECEPMEDICAMENTO("Recepción Medicament","REMED"),
    DEVOLMEDICAMENTO("Devolucion Medicamen","DEVMED"),
    SURTPRESCEXT("Prescripciones externas","SURTEXT"),
    PRESCEXT("Prescripciones externas","PRESCEXT"),
    CARGAMASIVA("Carga Masiva","MASVO"),
    REPORTECOSTOSHOSPITAL("Costos Hospital","REPCOSHOSP"),
    REPORTEDISPOCAMA("Disponibilidad de Camas","REPCAMA"),
    CANCELSURTIMIENTO("Cancelar Surtimientos","CANSUR"),
    SURRECM("Surtimiento y registros de recetas","SURRECM"),
    SRMHOS("Surtimiento Manual de Prescripción","SRMHOS"),
    RECDEVM("Recepción de la devolución de medicamentos","RECDEVM"),
    ORDENAUTOMATICA("Orden de Reabasto Automatico", "ORAUTOM"),
    AJUSTEINVENTARIO("Ajuste de Inventario","AJSINV"),
    TRANSFERENCIAINVENTARIO("Transferencia de Inventario","TRANSF"),
    AGENDA("Agenda consulta externa","AGENEXT"),
    RECETACOLECTIVA("Receta Colectiva","RECCOL"),
    IMPRESORA("Impresora","IMPRESO"),
    TEMPLATE("Template Etiquetas","ETIQUET"),
    HISTERAPEUTICO("Historial Terapeutico","HISTEP"),
    AJUSTE_INVGLOB("Ajuste Inventario Global","AJUINVG"),
    MINISTRAPRESCRI("Ministración de Prescripcion","MINIPRE"),
    DISPENSDIRECTA("Dispensación Directa","DISPDIR"),
    CONTROLCADUCIDADES("Control Caducidades", "CONTROLCAD"),
    DISPENSMATCUR("Dispensación Material de Curación","DISPMAT"),
    PROGRAMARACTUALMEDTOS("PROGRAMAR ACTUALIZA MEDICAMENTOS","PRACTMED"),
    SURTRECETASIAM("SURTE RECETA SIAM","WSSUREX"),
    DIASFESTIVOS("Días Festivos","DIASFES"),
    DISPENSAPRESCSOLUCION("Dispensación de Solución","DISPSOL"),
    REPORTREFRIGE5000("Reportes de Refrigeración y 5000","RE-5000"),
    ENTIDADHOSPITALARIA("Entidad Hospitalaria","ENTIDAD"),
    RECEPCIONMANUAL("Recepción Manual","RECMANU"),
    REENVIOMEDICAMENTOS("Reenvío de Medicamentos","REEVMED"),
    REPDISPMATERIAL("Reporte de Dispensación de Material","REPDISP"),
    SURTNOMINISTADO("Modulo Surtimento No Ministrado","NOMINIS"),
    REPINVENTARIOEXISTENCIAS("Reporte de Inventario y Existencias","REINVEX"),
    REPORTMOVGENERALES("Reporte de Movimientos Generales","REMOGEN"),
    REPMINISTRACION("Reporte de Ministración","REPMINI"),
    REPORTECONCENTRACIONES("Reportes de Concentraciones","REPCONA"),
    REPESTATUSINSUMO("Reporte Estatus Insumo","REPESIN"),
    REPORTSURTSERVIC("Reportes de Surtido a Servicio","REPSS"),
    LIBRMEDICAMECONTROL("Libreta Electrónica para Registro de Medicame","LIBMEDC"),
    DASH("Estadísticas del Sistema en General","REDASHB"),
    REPCONTRCADUCIDADES("Reporte Control Caducidades","REPCONC"),
    CENSOPACIENTES("REGISTRO DEL CENSO DE PACIENTES","CENSOPAC"),
    REGLASCENSO("ADMINISTRACIÓN DE REGLAS DEL CENSO PACIENTES","CENSOREG"),
    CONTROLDIARIO("REPORTECONTROLDIARIO","CONTDIA"),
    REPORTEPRESCRIPCIONCONSOLIDADA("Surtimiento de Prescripcion Consolidado","PRECONS"),    
    EXISTENCIACONSILIDADA("EXISTENCIACONSILIDADA","EXCONSO"),
    REPORTERECETAS("Reporte de Recetas","REPRECT"),
    CANTIDADRAZONADA("Cantidad Razonada","CANRZN"),
    INTERACCION("Interacciones Medicamentosas","INTERMED"),
    INDICACIONTERAPEUTICA("Indicaciones Terapeuticas","INDTERAP"),
    CONFIGURACION("Configuración","CONFIG"),
    REACCIONESADVERSAS("Reacciones Adversas","READV"),
    VALIDACIONDESOLUCIONES("Validacion de Soluciones","VASOL"),
    REACCHIPERSENSIBILIDAD("Reacciones Hipersensibilidad", "HIPERSEN"),
    PREPARARSOLUCION("Preparación de Soluciones","PRESOL"),
    INSPECCSOLUCION("Inspeccionar Mezcla de Soluciones", "INSPSOL"),
    ACONDICSOLUCION("Acondicionar Soluciones", "ACONSOL"),
    DASHFARMACO("Dashboard Farmacovigilancia", "DASHFARM"),
    REPORTEHISTSOLUCIONES("Reporte Historico De Soluciones", "RPHISSOL"),
    ORDPREMEZCLA("Orden Preparación de Mezcla", "ORDPRMEZ"),
    DEVOMEZCLAS("Devolución de Mezclas", "DEVMEZ"),
    EXPEDIENTECENTRALMEZCLAS("Expediente de Central de Mezclas", "EXPCM"),
    EXPEDIENTEINSUMO("Expediente de Insumo", "EXPINS"),
    PREVENCIONCONTAMINACION("Expediente de Insumo", "FICPC"),
    REGISTROSOLUCION("Registro de órdenes de solución", "REGSOL"),
    ORDNUTRIPARENTERAL("Orden de nutrición parenteral", "ORNUPARE"),
    VITALESANTROPOMETRICO("Registro de Vitales y Antropometrico", "VITANTR"),
    PRESCRIBESOLUCION("Registro de prescripción de mezcla", "PREMEZ"),
    ENTREGASOLUCION("Registro de entrega de mezcla", "ENTRSOL"),
    REPHISTMEZPACIENTES("Reporte Historial De Mezcla De Paciente", "HIMEZPAC"),
    BITACORACCIONESUSER("Bitacora de Acciones de Usuario", "BITACCUS"),
    DISPENSACIONCOLECTIVAMEZCLAS("Dispensación Colectiva Mezclas", "DISCOLM"),
    PROTOCOLOSONCOLOGICOS("Protocolos oncológicos", "PROTONC"),
    REPORTECONSUMOS("Reporte de Consumos", "REPCONS"),
    PRESCRIPCIONPT("Prescripción NPT", "PRESPARE"),
    ESTABILIDADINSUMO("Estabilidades Insumos", "ESTINS"),
    DIAGNOSTICO("Diagnosticos", "DIANOSTI"),
    VIAADMINISTRACION("Vía Administración", "VIAADMON"),
    ESTUDIOS("Catálogo de Estudios", "ESTUDIO"),
    HOJATRABAJO("Hoja de Trabajo", "HOJATRAB"),
    AUTORIZACIONSOLUCION("Autorización de Soluciones", "AUTOSOLU"),
    REPORTEMEZCLAS("Reporte de Mezclas", "REPMEZC"),
    FABRICANTE("Catálogo de Fabricante","FABRICA"),
    ENTREGAEXISTENCIAS("Reporte de entrega de existencias","REENEX"),
    ;

    private final String value;
    private final String sufijo;

    private Transaccion_Enum(String value, String sufijo) {
        this.value = value;
        this.sufijo = sufijo;
    }

    public String getValue() {
        return value;
    }

    public String getSufijo() {
        return sufijo;
    }

}
