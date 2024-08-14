package mx.mc.ws.suministro.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import mx.mc.enums.EstatusReabasto_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.Config;
import mx.mc.model.EntidadHospitalariaExtended;
import mx.mc.model.FolioAlternativoFolioMus;
import mx.mc.model.Medicamento;
import mx.mc.model.ReabastoEnviado;
import mx.mc.model.ReabastoExtended;
import mx.mc.model.ReabastoInsumo;
import mx.mc.model.ReabastoInsumoExtended;
import mx.mc.service.ConfigService;
import mx.mc.service.EntidadHospitalariaService;
import mx.mc.service.FolioAlternativoFolioMusService;
import mx.mc.service.MedicamentoService;
import mx.mc.service.ReabastoInsumoService;
import mx.mc.service.ReabastoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import mx.mc.util.Comunes;
import mx.mc.util.FechaUtil;
import mx.mc.ws.suministro.enums.EstatusSolicitud_Enum;
import mx.mc.ws.suministro.enums.RespuestaOrdenReabasto_Enum;
import mx.mc.ws.suministro.enums.TipoSolicitudReabasto_Enum;
import mx.mc.ws.suministro.model.OrdenReabasto;
import mx.mc.ws.suministro.model.OrdenReabastoMedicamento;
import mx.mc.ws.suministro.model.RespuestaOrdenReabasto;

/**
 *
 * @author hramirez
 */
@WebService(serviceName = "ReabastoInventarioWS")
public class ReabastoInventarioWS extends SpringBeanAutowiringSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReabastoInventarioWS.class);
    protected final static ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private List<Config> configList;
    
    @Autowired
    private MedicamentoService medicamentoService;

    @Autowired
    private ReabastoInsumoService reabastoInsumoService;

    @Autowired
    private ReabastoService reabastoService;
    
    @Autowired 
    private FolioAlternativoFolioMusService folioAlternativoFolioMusService;
    
    @Autowired
    private EntidadHospitalariaService entidadHospitalariaService;
    
    @Autowired
    private ConfigService configService;


    /**
     * Consulta Solicitudes de Reabasto de Inventario por folio de Solcitiud.
     *
     * @param folio
     * @return
     */
    @WebMethod(operationName = "ConsultaSolicitud")
    @WebResult(name = "SolicitudReabasto")
    public RespuestaOrdenReabasto ConsultaSolicitud(@WebParam(name = "folioSAIF") String folioSAIF, @WebParam(name = "folioMUS") String folioMUS) {

        RespuestaOrdenReabasto res = new RespuestaOrdenReabasto();
        List<OrdenReabasto> listaOrdenReaba = new ArrayList<>();
        String folio = null;
        try {
            
            EntidadHospitalariaExtended entidad = new EntidadHospitalariaExtended();            
            entidad = entidadHospitalariaService.obtenerEntidadHospital();
            
            String DEPTID = entidad.getCentroCostos();            
            String IMNF_UMF = entidad.getCodigoUmf();
            String descrCC = entidad.getNombre();
            String classPtal = entidad.getClavePresupuestal();
            String claveHospital = entidad.getClaveEntidad();
                                                
            if (folioMUS != null && !folioMUS.trim().isEmpty()) {
                folio = folioMUS;
                List<ReabastoExtended> listaReaba = reabastoService.obtenerReabastoByFolio(folioMUS);
                if (listaReaba == null || listaReaba.isEmpty()) {
                    res.setCodigoRespuesta(RespuestaOrdenReabasto_Enum.FOLIO_NO_EXISTE.getCodigoRespuesta());
                    res.setDescripcionRespuesta(RespuestaOrdenReabasto_Enum.FOLIO_NO_EXISTE.getDescripcion());
                    res.setSolicitudReabasto(null);
                } else {
                    if (listaReaba.get(0).getIdEstatusReabasto().equals(EstatusReabasto_Enum.SOLICITADA.getValue())) {
                        for (ReabastoExtended reaba : listaReaba) {
                            OrdenReabasto ordenReabasto = new OrdenReabasto();
                            ordenReabasto.setSETID(claveHospital);  // Clave de Hospital
                            ordenReabasto.setIMNF_UMF(IMNF_UMF);  // 
                            ordenReabasto.setIMCADIT_SAIFOLIO(reaba.getFolioAlternativo());
                            ordenReabasto.setIMCEUCONDI_NFOLIO(reaba.getFolio());
                            ordenReabasto.setCREATE_DATE(reaba.getFechaSolicitud());// Fecha Solicitud
                            ordenReabasto.setLAST_DTTM_UPDATE(reaba.getUpdateFecha()); // Update de Solicitud
                            ordenReabasto.setIMCADIT_PROCESSFLG(EstatusSolicitud_Enum.PROCESANDO.getClave());   // Enum estatus proceso  todo revisar para enviar el estatus de solicitud
                            if (reaba.getIdTipoOrden() == Constantes.TIPO_ORDEN_NORMAL) {
                                ordenReabasto.setIMCEUCONDI_TIPSOL(TipoSolicitudReabasto_Enum.SOLICITUDORDINARIA.getClave()); /// Enum tipo de solicitud 
                            } else {
                                if (reaba.getIdTipoOrden() == Constantes.TIPO_ORDEN_EXTRA) {
                                    ordenReabasto.setIMCEUCONDI_TIPSOL(TipoSolicitudReabasto_Enum.SOLICITUDEXTRAORDINARIA.getClave()); /// Enum tipo de solicitud 
                                }
                            }
                            FolioAlternativoFolioMus folioAlterMus = folioAlternativoFolioMusService.obtener(new FolioAlternativoFolioMus(reaba.getFolioAlternativo()));
                            if (folioAlterMus != null) {
                                ordenReabasto.setDESCR1(folioAlterMus.getEstatus());
                                if (folioAlterMus.getEstatus().equalsIgnoreCase(EstatusSolicitud_Enum.ENVIADA.getDescripcion())) {
                                    ordenReabasto.setIMCEUCONDI_ESTSOL(EstatusSolicitud_Enum.ENVIADA.getClave());
                                } else {
                                    if (folioAlterMus.getEstatus().equalsIgnoreCase(EstatusSolicitud_Enum.PROCESADA.getDescripcion())) {
                                        ordenReabasto.setIMCEUCONDI_ESTSOL(EstatusSolicitud_Enum.PROCESADA.getClave());
                                    }
                                }
                            }
                            List<ReabastoInsumoExtended> listaReabastoInsumo = folioAlternativoFolioMusService.obtenerDetalleReabasto(reaba.getFolioAlternativo());
                            List<OrdenReabastoMedicamento> ordenReabastoMedicamentoList = new ArrayList<>();
                            for (ReabastoInsumoExtended unInsumo : listaReabastoInsumo) {
                                OrdenReabastoMedicamento ordenReabastoMedicamento = new OrdenReabastoMedicamento();
                                String[] clave = unInsumo.getClave().split(Constantes.SEPARADOR_CLAVE);
                                ordenReabastoMedicamento.setIMCEUCONDI_GPO_ART(clave[0]);
                                ordenReabastoMedicamento.setIMCEUCONDI_GEN_ART(clave[1]);
                                ordenReabastoMedicamento.setIMCEUCONDI_CD_ESP(clave[2]);
                                ordenReabastoMedicamento.setIMCEUCONDI_CD_DIF(clave[3]);
                                if (clave.length == 5) {
                                    ordenReabastoMedicamento.setIMCEUCONDI_CD_VAR(clave[4]);
                                } else {
                                    ordenReabastoMedicamento.setIMCEUCONDI_CD_VAR(clave[3]);
                                }
                                ordenReabastoMedicamento.setDESCR120(unInsumo.getNombreComercial());
                                ordenReabastoMedicamento.setIMCEUCONDI_UNISOL(unInsumo.getCantidadSolicitada() / unInsumo.getFactorTransformacion());
                                ordenReabastoMedicamento.setIMCEUCONDI_UNISUR(unInsumo.getCantidadSurtida() / unInsumo.getFactorTransformacion());
                                ordenReabastoMedicamento.setIMCEUCONDI_UM_UNIT(unInsumo.getNombrePresentacion());  // presentacion entrada medicamento                       

                                ordenReabastoMedicamentoList.add(ordenReabastoMedicamento);
                            }

                            ordenReabasto.setIM_CLASS_PTAL(classPtal);   // Clave presupuestal de CADIT
                            ordenReabasto.setDEPTID(DEPTID);  //
                            ordenReabasto.setDESCR(descrCC);  // Descripcion de Almacen
                            ordenReabasto.setIM_ESPECIALIDAD("A4");   // ENUM Clave de Almacen especialidad
                            ordenReabasto.setDESCR50(reaba.getDescripcionEstructura());  // Descripcion de Almacen
                            ordenReabasto.setAREA_CODE(reaba.getClaveEstructura()); // CLAVE CADIT
                            ordenReabasto.setAREA_DESCR(reaba.getDescripcionEstructura()); //DESCRIPCION ALMACEN
                            ordenReabasto.setOrdenReabastoMedicamentoList(ordenReabastoMedicamentoList);
                            listaOrdenReaba.add(ordenReabasto);
                        }
                        res.setSolicitudReabasto(listaOrdenReaba);
                        res.setCodigoRespuesta(RespuestaOrdenReabasto_Enum.REABASTO_ENCONTRADO.getCodigoRespuesta());
                        res.setDescripcionRespuesta(RespuestaOrdenReabasto_Enum.REABASTO_ENCONTRADO.getDescripcion());
                    } else {
                        if (listaReaba.get(0).getIdEstatusReabasto().equals(EstatusReabasto_Enum.CANCELADA.getValue())) {
                            res.setSolicitudReabasto(null);
                            res.setCodigoRespuesta(RespuestaOrdenReabasto_Enum.REABASTO_CANCELADO.getCodigoRespuesta());
                            res.setDescripcionRespuesta(RespuestaOrdenReabasto_Enum.REABASTO_CANCELADO.getDescripcion());
                        } else {
                            if (listaReaba.get(0).getIdEstatusReabasto().equals(EstatusReabasto_Enum.ENTRANSITO.getValue())
                                    || listaReaba.get(0).getIdEstatusReabasto().equals(EstatusReabasto_Enum.RECIBIDAPARCIAL.getValue())
                                    || listaReaba.get(0).getIdEstatusReabasto().equals(EstatusReabasto_Enum.RECIBIDA.getValue())
                                    || listaReaba.get(0).getIdEstatusReabasto().equals(EstatusReabasto_Enum.INGRESADA.getValue())) {
                                res.setSolicitudReabasto(null);
                                res.setCodigoRespuesta(RespuestaOrdenReabasto_Enum.PROCESADA.getCodigoRespuesta());
                                res.setDescripcionRespuesta(RespuestaOrdenReabasto_Enum.PROCESADA.getDescripcion());
                            }
                        }
                    }
                }
            } else {
                if (folioSAIF != null && !folioSAIF.trim().isEmpty()) {
                    folio = folioSAIF;
                    ReabastoExtended reaba = reabastoService.obtenerReabastoByFolioAlternativo(folioSAIF);
                    if (reaba == null) {
                        res.setCodigoRespuesta(RespuestaOrdenReabasto_Enum.FOLIO_NO_EXISTE.getCodigoRespuesta());
                        res.setDescripcionRespuesta(RespuestaOrdenReabasto_Enum.FOLIO_NO_EXISTE.getDescripcion());
                        res.setSolicitudReabasto(null);
                    } else {
                        if (reaba.getEstatusFolioAlternativo().equalsIgnoreCase(EstatusSolicitud_Enum.ENVIADA.getDescripcion())) {
                            OrdenReabasto ordenReabasto = new OrdenReabasto();
                            ordenReabasto.setSETID(claveHospital);  // Clave de Hospital
                            ordenReabasto.setIMNF_UMF(IMNF_UMF);  // 
                            ordenReabasto.setIMCADIT_SAIFOLIO(reaba.getFolioAlternativo());
                            ordenReabasto.setIMCEUCONDI_NFOLIO(reaba.getFolio());
                            ordenReabasto.setCREATE_DATE(reaba.getFechaSolicitud());// Fecha Solicitud
                            ordenReabasto.setLAST_DTTM_UPDATE(reaba.getUpdateFecha()); // Update de Solicitud
                            ordenReabasto.setIMCADIT_PROCESSFLG(EstatusSolicitud_Enum.PROCESANDO.getClave());   // Enum estatus proceso
                            if (reaba.getIdTipoOrden() == Constantes.TIPO_ORDEN_NORMAL) {
                                ordenReabasto.setIMCEUCONDI_TIPSOL(TipoSolicitudReabasto_Enum.SOLICITUDORDINARIA.getClave()); /// Enum tipo de solicitud 
                            } else {
                                if (reaba.getIdTipoOrden() == Constantes.TIPO_ORDEN_EXTRA) {
                                    ordenReabasto.setIMCEUCONDI_TIPSOL(TipoSolicitudReabasto_Enum.SOLICITUDEXTRAORDINARIA.getClave()); /// Enum tipo de solicitud 
                                }
                            }
                            FolioAlternativoFolioMus folioAlterMus = folioAlternativoFolioMusService.obtener(new FolioAlternativoFolioMus(folioSAIF));
                            if (folioAlterMus != null) {
                                ordenReabasto.setDESCR1(folioAlterMus.getEstatus());
                                if (folioAlterMus.getEstatus().equalsIgnoreCase(EstatusSolicitud_Enum.ENVIADA.getDescripcion())) {
                                    ordenReabasto.setIMCEUCONDI_ESTSOL(EstatusSolicitud_Enum.ENVIADA.getClave());
                                } else {
                                    if (folioAlterMus.getEstatus().equalsIgnoreCase(EstatusSolicitud_Enum.PROCESADA.getDescripcion())) {
                                        ordenReabasto.setIMCEUCONDI_ESTSOL(EstatusSolicitud_Enum.PROCESADA.getClave());
                                    }
                                }
                            }
                            List<ReabastoInsumoExtended> listaReabastoInsumo = folioAlternativoFolioMusService.obtenerDetalleReabasto(reaba.getFolioAlternativo());// reabastoInsumoService.obtenerDetalleReabasto(reaba.getIdReabasto());                    
                            List<OrdenReabastoMedicamento> ordenReabastoMedicamentoList = new ArrayList<>();
                            for (ReabastoInsumoExtended unInsumo : listaReabastoInsumo) {
                                OrdenReabastoMedicamento ordenReabastoMedicamento = new OrdenReabastoMedicamento();
                                String[] clave = unInsumo.getClave().split(Constantes.SEPARADOR_CLAVE);
                                ordenReabastoMedicamento.setIMCEUCONDI_GPO_ART(clave[0]);
                                ordenReabastoMedicamento.setIMCEUCONDI_GEN_ART(clave[1]);
                                ordenReabastoMedicamento.setIMCEUCONDI_CD_ESP(clave[2]);
                                ordenReabastoMedicamento.setIMCEUCONDI_CD_DIF(clave[3]);
                                if (clave.length == 5) {
                                    ordenReabastoMedicamento.setIMCEUCONDI_CD_VAR(clave[4]);
                                } else {
                                    ordenReabastoMedicamento.setIMCEUCONDI_CD_VAR(clave[3]);
                                }
                                ordenReabastoMedicamento.setDESCR120(unInsumo.getNombreComercial());
                                ordenReabastoMedicamento.setIMCEUCONDI_UNISOL(unInsumo.getCantidadSolicitada() / unInsumo.getFactorTransformacion());
                                ordenReabastoMedicamento.setIMCEUCONDI_UNISUR(unInsumo.getCantidadSurtida() / unInsumo.getFactorTransformacion());
                                ordenReabastoMedicamento.setIMCEUCONDI_UM_UNIT(unInsumo.getNombrePresentacion());  // presentacion entrada medicamento                       
                                ordenReabastoMedicamentoList.add(ordenReabastoMedicamento);
                            }

                            ordenReabasto.setIM_CLASS_PTAL(classPtal);   // Clave presupuestal de CADIT
                            ordenReabasto.setDEPTID(DEPTID);  //
                            ordenReabasto.setDESCR(descrCC);  // Descripcion de Almacen
                            ordenReabasto.setIM_ESPECIALIDAD("A4");   // ENUM Clave de Almacen especialidad
                            ordenReabasto.setDESCR50(reaba.getDescripcionEstructura());  // Descripcion de Almacen
                            ordenReabasto.setAREA_CODE(reaba.getClaveEstructura()); // CLAVE CADIT
                            ordenReabasto.setAREA_DESCR(reaba.getDescripcionEstructura()); //DESCRIPCION ALMACEN
                            ordenReabasto.setOrdenReabastoMedicamentoList(ordenReabastoMedicamentoList);
                            listaOrdenReaba.add(ordenReabasto);

                            res.setSolicitudReabasto(listaOrdenReaba);
                            res.setCodigoRespuesta(RespuestaOrdenReabasto_Enum.REABASTO_ENCONTRADO.getCodigoRespuesta());
                            res.setDescripcionRespuesta(RespuestaOrdenReabasto_Enum.REABASTO_ENCONTRADO.getDescripcion());
                        } else {
                            if (reaba.getIdEstatusReabasto().equals(EstatusReabasto_Enum.CANCELADA.getValue())) {
                                res.setSolicitudReabasto(null);
                                res.setCodigoRespuesta(RespuestaOrdenReabasto_Enum.REABASTO_CANCELADO.getCodigoRespuesta());
                                res.setDescripcionRespuesta(RespuestaOrdenReabasto_Enum.REABASTO_CANCELADO.getDescripcion());
                            } else {
                                if (reaba.getEstatusFolioAlternativo().equalsIgnoreCase(EstatusSolicitud_Enum.PROCESADA.getDescripcion())) {
                                    res.setSolicitudReabasto(null);
                                    res.setCodigoRespuesta(RespuestaOrdenReabasto_Enum.PROCESADA.getCodigoRespuesta());
                                    res.setDescripcionRespuesta(RespuestaOrdenReabasto_Enum.PROCESADA.getDescripcion());
                                } else {
                                    if (reaba.getEstatusFolioAlternativo().equalsIgnoreCase(EstatusSolicitud_Enum.REABASTO_CANCELADO_VIGENCIA.getDescripcion())) {
                                        res.setSolicitudReabasto(null);
                                        res.setCodigoRespuesta(RespuestaOrdenReabasto_Enum.REABASTO_CANCELADO_VIGENCIA.getCodigoRespuesta());
                                        res.setDescripcionRespuesta(RespuestaOrdenReabasto_Enum.REABASTO_CANCELADO_VIGENCIA.getDescripcion());
                                    }
                                }
                            }
                        }
                    }
                } else {
                    res.setCodigoRespuesta(RespuestaOrdenReabasto_Enum.FOLIO_NULO.getCodigoRespuesta());
                    res.setDescripcionRespuesta(RespuestaOrdenReabasto_Enum.FOLIO_NULO.getDescripcion());
                    res.setSolicitudReabasto(null);
                }
            }

        } catch (Exception e) {
            LOGGER.error("Error al buscar la solicitud folio: {} {}", folio, e.getMessage());
            res.setCodigoRespuesta(RespuestaOrdenReabasto_Enum.ERROR_CONSULTA.getCodigoRespuesta());
            res.setDescripcionRespuesta(RespuestaOrdenReabasto_Enum.ERROR_CONSULTA.getDescripcion());
            res.setSolicitudReabasto(null);
        }

        return res;
    }

    
    private void obtenerDatosSistema() {
        Config c = new Config();
        c.setActiva(Constantes.ACTIVO);
        try {
            configList = configService.obtenerLista(c);
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString("sys.config.err") + ex.getMessage());
        }
    }
    /**
     * Registra Respuesta del Surtimiento de ReabastoReabasto de Inventario por
     * folio de Solcitiud.
     *
     * @param ordenReabasto
     * @return
     */
    @WebMethod(operationName = "RegistraReabasto")
    @WebResult(name = "RespuestaOrdenReabasto")
    public RespuestaOrdenReabasto RegistraReabasto(@WebParam(name = "ordenReabasto") OrdenReabasto ordenReabasto) {
        RespuestaOrdenReabasto res = new RespuestaOrdenReabasto();
        obtenerDatosSistema();
        List<Config> cl = configList.stream().filter(p -> p.getNombre().equals(Constantes.PARAM_SYSTEM_FUNC_CONEVER_CLAVES))
                        .collect(Collectors.toList());
        try {
            if (ordenReabasto == null || ordenReabasto.getIMCADIT_SAIFOLIO() == null || ordenReabasto.getIMCADIT_SAIFOLIO().trim().isEmpty()) {
                res.setCodigoRespuesta(RespuestaOrdenReabasto_Enum.FOLIO_NULO.getCodigoRespuesta());
                res.setDescripcionRespuesta(RespuestaOrdenReabasto_Enum.FOLIO_NULO.getDescripcion());
                res.setSolicitudReabasto(null);

            } else if (ordenReabasto.getOrdenReabastoMedicamentoList() == null || ordenReabasto.getOrdenReabastoMedicamentoList().isEmpty()) {
                res.setCodigoRespuesta(RespuestaOrdenReabasto_Enum.SIN_MEDICAMENTOS.getCodigoRespuesta());
                res.setDescripcionRespuesta(RespuestaOrdenReabasto_Enum.SIN_MEDICAMENTOS.getDescripcion());
                res.setSolicitudReabasto(null);

            } else {
                ReabastoExtended reabasto = new ReabastoExtended();
                reabasto = reabastoService.obtenerReabastoByFolioAlternativo(ordenReabasto.getIMCADIT_SAIFOLIO());
                StringBuilder claveInstitucional;
                
                if (reabasto == null) {
                    res.setCodigoRespuesta(RespuestaOrdenReabasto_Enum.FOLIO_NO_EXISTE.getCodigoRespuesta());
                    res.setDescripcionRespuesta(RespuestaOrdenReabasto_Enum.FOLIO_NO_EXISTE.getDescripcion());
                    res.setSolicitudReabasto(null);
                } else {
                    // Actualiza campos de Reabasto
                    reabasto.setUpdateFecha(new Date());
                    reabasto.setUpdateIdUsuario(reabasto.getIdUsuarioSolicitud());
                    reabasto.setIdEstatusReabasto(EstatusReabasto_Enum.ENTRANSITO.getValue());
                    
                    ReabastoInsumo reabastoInsumo;
                    ReabastoEnviado reabastoEnviado;
                    List<ReabastoInsumo> reabastoInsumoLista = new ArrayList<>();
                    List<ReabastoEnviado> reabastoEnviadoLista = new ArrayList<>();
                    List<ReabastoInsumoExtended> listaDetalle = reabastoInsumoService.obtenerDetalleReabasto(reabasto.getIdReabasto(), ordenReabasto.getIMCADIT_SAIFOLIO());
                    
                    FolioAlternativoFolioMus folioAlterMus = folioAlternativoFolioMusService.obtener(new FolioAlternativoFolioMus(ordenReabasto.getIMCADIT_SAIFOLIO()));
                    folioAlterMus.setEstatus(EstatusSolicitud_Enum.PROCESADA.getDescripcion());
                    folioAlterMus.setUpdateFecha(new Date());
                    
                    for (OrdenReabastoMedicamento medicamentoSurtido : ordenReabasto.getOrdenReabastoMedicamentoList()) {
                        boolean bandera = true;
                        String claveRecibida = "";
                        String claveSolicitada = "";
                        String idMedicamento = "";
                        Integer factorTransforma = 0;
                        claveInstitucional = new StringBuilder();
                        claveInstitucional.append(medicamentoSurtido.getIMCEUCONDI_GPO_ART()).append(".")
                                .append(medicamentoSurtido.getIMCEUCONDI_GEN_ART()).append(".")
                                .append(medicamentoSurtido.getIMCEUCONDI_CD_ESP()).append(".")                                
                                .append(medicamentoSurtido.getIMCEUCONDI_CD_DIF()).append(".")
                                .append(medicamentoSurtido.getIMCEUCONDI_CD_VAR());
                        Medicamento medicamento = medicamentoService.obtenerMedicaByClave(claveInstitucional.toString());
                        if(medicamento == null) {
                            res.setCodigoRespuesta(RespuestaOrdenReabasto_Enum.ERROR_MEDICAMENTO.getCodigoRespuesta());
                            res.setDescripcionRespuesta(RespuestaOrdenReabasto_Enum.ERROR_MEDICAMENTO.getDescripcion());
                            res.setSolicitudReabasto(null);
                            return res;
                        }
                        for ( ReabastoInsumoExtended insumoDetalle : listaDetalle) {
                            insumoDetalle.setIdEstatusReabasto(EstatusReabasto_Enum.ENTRANSITO.getValue()); // Para que se cambie el estatus de todos los surtimientos insumos
                            if(insumoDetalle.getClave().equalsIgnoreCase(claveInstitucional.toString())) {                                
                                insumoDetalle.setCantidadSurtida(insumoDetalle.getCantidadSurtida() + (medicamentoSurtido.getIMCEUCONDI_UNISUR() * medicamento.getFactorTransformacion())); // se actualiza lo que se va a surtir todo
                                insumoDetalle.setCantidadRecibida(insumoDetalle.getCantidadRecibida() + (medicamentoSurtido.getIMCEUCONDI_UNISUR() * medicamento.getFactorTransformacion()));
                                insumoDetalle.setUpdateFecha(new Date());
                                idMedicamento = insumoDetalle.getIdInsumo();
                                factorTransforma = medicamento.getFactorTransformacion();
                                bandera = false;
                                break;
                            } else {
                                //si se encuentra activo el parametro se busca por especialidad para agregarlo al insumo
                                if (!cl.isEmpty() && cl.get(0).isActiva()) {
                                    claveRecibida = claveInstitucional.substring(0, 12);
                                    claveSolicitada = insumoDetalle.getClave().substring(0, 12);
                                    // Valida si la especialidad existe en la lista de detalle de reabasto
                                    if(claveRecibida.equalsIgnoreCase(claveSolicitada)) {                                        
                                        //Se calcula la cantidad que se esta surtiendo del medicamento fuera de cuadro
                                        Integer cantSurtida = medicamentoSurtido.getIMCEUCONDI_UNISUR() * medicamento.getFactorTransformacion();
                                        //Valida si la cantidad de conversion es completa al factor de transformacion de la clave dentro de cuadro basico
                                        /*if(cantSurtida % insumoDetalle.getFactorTransformacion() != 0) {
                                            res.setCodigoRespuesta(RespuestaOrdenReabasto_Enum.ERROR_CONVERSION.getCodigoRespuesta());
                                            res.setDescripcionRespuesta(RespuestaOrdenReabasto_Enum.ERROR_CONVERSION.getDescripcion());
                                            res.setSolicitudReabasto(null);
                                            return res;
                                        } else {*/
                                            idMedicamento = insumoDetalle.getIdInsumo();
                                            //Se encuentra dentro de la lista de claves de reabasto a convertir y se actualiza la cantidad de este medicamento                                        
                                            insumoDetalle.setCantidadSurtida(insumoDetalle.getCantidadSurtida() + cantSurtida); // se actualiza lo que se va a surtir todo
                                            insumoDetalle.setCantidadRecibida(insumoDetalle.getCantidadRecibida() + cantSurtida);
                                            insumoDetalle.setUpdateFecha(new Date());
                                            factorTransforma = insumoDetalle.getFactorTransformacion();
                                            bandera = false;
                                            break;
                                       // }                                    
                                    }
                                } else {
                                    idMedicamento = medicamento.getIdMedicamento();
                                }                                
                            }
                        }                        
                        reabastoInsumo = new ReabastoInsumo();
                        if(bandera) {
                            //si se encuentra activo el parametro se busca por especialidad para agregarlo al insumo
                            if (cl.isEmpty() && !cl.get(0).isActiva()) {
                                // Se crea el objeto ReabastoInsumo y se agrega al objeto
                                String idReabastoInsumo = Comunes.getUUID();
                                reabastoInsumo.setIdReabastoInsumo(idReabastoInsumo);
                                reabastoInsumo.setIdReabasto(reabasto.getIdReabasto());
                                reabastoInsumo.setIdInsumo(medicamento.getIdMedicamento());
                                reabastoInsumo.setIdFolioAlternativo(folioAlterMus.getIdFolioAlternativo());
                                reabastoInsumo.setCantidadSolicitada(medicamentoSurtido.getIMCEUCONDI_UNISUR()* medicamento.getFactorTransformacion());
                                reabastoInsumo.setCantidadComprometida(0);
                                reabastoInsumo.setCantidadSurtida(medicamentoSurtido.getIMCEUCONDI_UNISUR() * medicamento.getFactorTransformacion());
                                reabastoInsumo.setCantidadRecibida(medicamentoSurtido.getIMCEUCONDI_UNISUR() * medicamento.getFactorTransformacion());
                                reabastoInsumo.setIdEstatusReabasto(EstatusReabasto_Enum.ENTRANSITO.getValue());
                                reabastoInsumo.setInsertFecha(new Date());
                                reabastoInsumo.setInsertIdUsuario(reabasto.getIdUsuarioSolicitud());  // Usuario que solicita la orden de reabasto
                                reabastoInsumoLista.add(reabastoInsumo);
                            }                          
                        }
                       
                        //Buscar el reabastoInsumo por idReabasto y idInsumo
                        ReabastoInsumo insumoReabasto = reabastoInsumoService.obtenerReabastoInsumo(reabasto.getIdReabasto(), idMedicamento);
                        if(insumoReabasto == null) {
                            insumoReabasto = reabastoInsumo;
                        }
                        boolean crearReabastoEnviado = true;
                        
                        for(ReabastoEnviado reabastoEnv : reabastoEnviadoLista) {
                            if(reabastoEnv.getIdInsumo().equalsIgnoreCase(idMedicamento)) {
                                reabastoEnv.setCantidadEnviado(reabastoEnv.getCantidadEnviado() + (medicamentoSurtido.getIMCEUCONDI_UNISUR() * medicamento.getFactorTransformacion()));
                                reabastoEnv.setCantidadRecibida(reabastoEnv.getCantidadRecibida() + (medicamentoSurtido.getIMCEUCONDI_UNISUR() * medicamento.getFactorTransformacion()));
                                crearReabastoEnviado = false;
                                break;
                            } 
                        }
                        if(crearReabastoEnviado) {
                            // Se crea el Objeto ReabastoEnviado y se agrega a la lista
                            reabastoEnviado = new ReabastoEnviado();
                            reabastoEnviado.setIdReabastoEnviado(Comunes.getUUID());

                            reabastoEnviado.setIdReabastoInsumo(insumoReabasto.getIdReabastoInsumo());
                            reabastoEnviado.setCantidadEnviado(medicamentoSurtido.getIMCEUCONDI_UNISUR() * medicamento.getFactorTransformacion());
                            reabastoEnviado.setCantidadRecibida(medicamentoSurtido.getIMCEUCONDI_UNISUR() * medicamento.getFactorTransformacion());
                            reabastoEnviado.setIdEstatusReabasto(EstatusReabasto_Enum.ENTRANSITO.getValue());
                            reabastoEnviado.setIdInsumo(idMedicamento);
                            reabastoEnviado.setIdEstructura(reabasto.getIdEstructura()); // Almacen CADIT
                            reabastoEnviado.setLoteEnv(Constantes.LOTE_GENERICO); // Lote Generico
                            reabastoEnviado.setFechaCad(FechaUtil.formatoFecha("yyyy-MM-dd", Constantes.CADUCIDAD_GENERICA)); // Caducidad Generica
                            reabastoEnviado.setCantidadXCaja(factorTransforma);
                            reabastoEnviado.setPresentacionComercial(medicamento.getIdPresentacionEntrada());
                            reabastoEnviado.setInsertFecha(new Date());
                            reabastoEnviado.setInsertIdUsuario(reabasto.getIdUsuarioSolicitud());  // usuario
                            reabastoEnviado.setClaveOriginalSurtida(claveInstitucional.toString());
                            reabastoEnviadoLista.add(reabastoEnviado); 
                        }                                                                            
                    }
                    
                    boolean respuesta = reabastoInsumoService.actulizaOrdenReabastoWS(reabasto, listaDetalle, reabastoEnviadoLista, reabastoInsumoLista, folioAlterMus);
                    if(respuesta) {
                        res.setCodigoRespuesta(RespuestaOrdenReabasto_Enum.REABASTO_REGISTRADO.getCodigoRespuesta());
                        res.setDescripcionRespuesta(RespuestaOrdenReabasto_Enum.REABASTO_REGISTRADO.getDescripcion());
                        res.setSolicitudReabasto(null);
                    } else {
                        res.setCodigoRespuesta("08");
                        res.setDescripcionRespuesta("Error al momento de surtir el reabasto.");
                        res.setSolicitudReabasto(null);
                    }                    
                }
            }

        } catch (Exception e) {
            LOGGER.error("Error al registrar " + e.getMessage());
            res.setCodigoRespuesta(RespuestaOrdenReabasto_Enum.ERROR_REGISTRO.getCodigoRespuesta());
            res.setDescripcionRespuesta(RespuestaOrdenReabasto_Enum.ERROR_REGISTRO.getDescripcion());
            res.setSolicitudReabasto(null);
        }

        return res;
    }

}
