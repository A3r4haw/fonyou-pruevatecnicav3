package mx.mc.lazy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import mx.mc.enums.EstatusSolucion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.Estructura;
import mx.mc.util.FechaUtil;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.Surtimiento_Extend;

import mx.mc.service.SurtimientoService;

/**
 *
 * @author mcalderon
 *
 */
public class DispensacionSolucionLazy extends LazyDataModel<Surtimiento_Extend> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DispensacionSolucionLazy.class);
    private static final long serialVersionUID = 1L;

    private transient SurtimientoService surtimientoService;
    private ParamBusquedaReporte paramBusquedaReporte;
    private Date fechaProgramada = new java.util.Date();
    private transient List<String> tipoPrescripcionSelectedList;
    private transient List<Integer> listEstatusPaciente = new ArrayList<>();
    private transient List<Integer> listEstatusPrescripcion = new ArrayList<>();
    private transient List<Integer> listEstatusSurtimiento = new ArrayList<>();
    private transient List<Estructura> listServiciosQueSurte = new ArrayList<>();
    private transient List<Surtimiento_Extend> surtimientoSolucionList;
    private transient List<Integer> estatusSolucionLista;
    private boolean solucion = Constantes.INACTIVO;
    private int totalReg;
    private String idEstructura;
    private String idTipoSolucion;
    private Date fechaParaEntregar;
    private Date fechaParaEntregarFin;
    private boolean agruparMezclaXPrescripcion;

    private int tipoProceso;

    public DispensacionSolucionLazy() {
        //No code needed in constructor
    }

    public DispensacionSolucionLazy(SurtimientoService surtimientoService, ParamBusquedaReporte paramBusquedaReporte,
            Date fechaProgramada, List<String> tipoPrescripcionSelectedList, List<Integer> listEstatusPaciente, List<Integer> listEstatusPrescripcion,
            List<Integer> listEstatusSurtimiento, List<Estructura> listServiciosQueSurte, boolean sol, List<Integer> estatusSolucionLista
            , String idTipoSolucion, int tipoProceso, boolean agruparMezclaXPrescripcion) {
        this.surtimientoService = surtimientoService;
        this.paramBusquedaReporte = paramBusquedaReporte;
        this.fechaProgramada = fechaProgramada;
        this.tipoPrescripcionSelectedList = tipoPrescripcionSelectedList;
        this.listEstatusPaciente = listEstatusPaciente;
        this.listEstatusPrescripcion = listEstatusPrescripcion;
        this.listEstatusSurtimiento = listEstatusSurtimiento;
        this.listServiciosQueSurte = listServiciosQueSurte;
        this.solucion = sol;
        this.estatusSolucionLista = estatusSolucionLista;
        this.idTipoSolucion = idTipoSolucion;
        this.tipoProceso = tipoProceso;
        this.surtimientoSolucionList = new ArrayList<>();   
        this.agruparMezclaXPrescripcion = agruparMezclaXPrescripcion;
    }

    public DispensacionSolucionLazy(SurtimientoService surtimientoService, ParamBusquedaReporte paramBusquedaReporte,
            Date fechaProgramada, List<String> tipoPrescripcionSelectedList, List<Integer> listEstatusPaciente, List<Integer> listEstatusPrescripcion
            , List<Integer> listEstatusSurtimiento, List<Estructura> listServiciosQueSurte, boolean sol, List<Integer> estatusSolucionLista
            , String idTipoSolucion, int tipoProceso, String idEstructura) {
        this.surtimientoService = surtimientoService;
        this.paramBusquedaReporte = paramBusquedaReporte;
        this.fechaProgramada = fechaProgramada;
        this.tipoPrescripcionSelectedList = tipoPrescripcionSelectedList;
        this.listEstatusPaciente = listEstatusPaciente;
        this.listEstatusPrescripcion = listEstatusPrescripcion;
        this.listEstatusSurtimiento = listEstatusSurtimiento;
        this.listServiciosQueSurte = listServiciosQueSurte;
        this.solucion = sol;
        this.estatusSolucionLista = estatusSolucionLista;
        this.idTipoSolucion = idTipoSolucion;
        this.tipoProceso = tipoProceso;
        this.idEstructura = idEstructura;
        this.surtimientoSolucionList = new ArrayList<>();
    }

    @Override
    public List<Surtimiento_Extend> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> map) {
        if (surtimientoSolucionList != null) {

            String tipoPrescripcion = null;
            String estatusSolucion = null;
            String nombreEstructura = null;
            String tipoSolucion = null;
            String nombreMedico = null;
            String folio = null;
            String fechaProgramada2 = null;
            String nombrePaciente = null;
            String cama = null;
            String folioPrescripcion = null;

            for (String key : map.keySet()) {
                LOGGER.debug(key + ":" + map.get(key));
                switch (key) {
                    case "tipoPrescripcion":
                        tipoPrescripcion = map.get(key).toString();
                        break;
                    case "estatusSolucion":
                        estatusSolucion = map.get(key).toString();
                        break;
                    case "nombreEstructura":
                        nombreEstructura = map.get(key).toString();
                        break; // nombreEstructura:medici
                    case "tipoSolucion":
                        tipoSolucion = map.get(key).toString();
                        break;     // tipoSolucion:on
                    case "nombreMedico":
                        nombreMedico = map.get(key).toString();
                        break;     // nombreMedico:De prueba
                    case "folio":
                        folio = map.get(key).toString();
                        break;            // folio:94
                    case "fechaProgramada":
                        fechaProgramada2 = map.get(key).toString();
                        break;            // fechaProgramada:2024-02-18
                    case "nombrePaciente":
                        nombrePaciente = map.get(key).toString();
                        break;            // nombrePaciente:banda
                    case "cama":
                        cama = map.get(key).toString();
                        break;            // cama:123
                    case "folioPrescripcion":
                        folioPrescripcion = map.get(key).toString();
                        break;            // folioPrescripcion:63
                    }
            }

            if (getRowCount() <= 0) {
                LOGGER.debug("Obtener total resultados");
                int total = obtenerTotalResultados(tipoPrescripcion, estatusSolucion, nombreEstructura, tipoSolucion, nombreMedico,
                        folio, fechaProgramada2, nombrePaciente, cama, folioPrescripcion);
                setRowCount(total);
            }
            try {
                String order = (sortOrder == SortOrder.ASCENDING) ? "asc" : sortOrder == SortOrder.DESCENDING ? "desc" : null;
                paramBusquedaReporte.setSortOrder(order);
                paramBusquedaReporte.setSortField(sortField);
                paramBusquedaReporte.setStartingAt(startingAt);
                paramBusquedaReporte.setMaxPerPage(maxPerPage);
                boolean agruparParaAutorizar = false;
                if (this.tipoProceso != 3) { // soluciones y prescripciones
                    for(Integer idEstatusSolucion : estatusSolucionLista) {
                        if(idEstatusSolucion.equals(EstatusSolucion_Enum.POR_AUTORIZAR.getValue())
                                && agruparMezclaXPrescripcion) {
                            agruparParaAutorizar = true;
                            break;
                        }
                    }
                    surtimientoSolucionList = surtimientoService.obtenerPorFechaEstructuraPacientePrescripcionSolucionLazy(
                            fechaProgramada
                            , fechaParaEntregarFin
                            , paramBusquedaReporte
                            , startingAt
                            , maxPerPage
                            , tipoPrescripcionSelectedList
                            , listEstatusPaciente
                            , listEstatusPrescripcion
                            , listServiciosQueSurte
                            , listEstatusSurtimiento
                            , estatusSolucionLista
                            , idTipoSolucion
                            , tipoProceso
                            , sortField
                            , sortOrder
                            , tipoPrescripcion
                            , estatusSolucion
                            , nombreEstructura
                            , tipoSolucion
                            , nombreMedico
                            , folio
                            , fechaProgramada2
                            , nombrePaciente
                            , cama
                            , folioPrescripcion
                            , agruparParaAutorizar
                    );
                } else {    // soluciones para entrega de dispensación colectiva
                    surtimientoSolucionList = surtimientoService.obtenerSolucionesPorIdEstructuraTipoMezclaFechas(
                            paramBusquedaReporte
                            , tipoPrescripcionSelectedList
                            , listEstatusPaciente
                            , listEstatusPrescripcion
                            , listServiciosQueSurte
                            , listEstatusSurtimiento
                            , estatusSolucionLista
                            , idTipoSolucion
                            , tipoPrescripcion
                            , tipoSolucion
                            , fechaProgramada2
                            , folio
                            , folioPrescripcion
                            , nombrePaciente
                            , nombreEstructura
                            , cama
                            , nombreMedico
                            , estatusSolucion
                    );
                }
                
                for (Surtimiento_Extend surtExtended : surtimientoSolucionList) {
                    long diferMinut = FechaUtil.diferenciaFechasEnMinutos(new java.util.Date(), surtExtended.getFechaProgramada());
                    if (diferMinut <= 90) {
                        surtExtended.setPrioridad("Alta");
                    } else if (diferMinut > 90 && diferMinut <= 180) {
                        surtExtended.setPrioridad("Media");
                    } else {
                        surtExtended.setPrioridad("Baja");
                    }
                }
            } catch (Exception ex) {
                LOGGER.error("Error al realizar la consulta de Soluciones. {}", ex.getMessage());
                surtimientoSolucionList = new ArrayList<>();
            }

            setPageSize(maxPerPage);
        } else {
            surtimientoSolucionList = new ArrayList<>();

        }
        return surtimientoSolucionList;
    }

    private int obtenerTotalResultados(String tipoPrescripcion, String estatusSolucion, String nombreEstructura, String tipoSolucion, String nombreMedico,
            String folio, String fechaProgramada2, String nombrePaciente, String cama, String folioPrescripcion) {
        try {
            if (paramBusquedaReporte != null) {
                Long total = Long.MIN_VALUE;
                boolean agruparParaAutorizar = false;
                if (this.tipoProceso != 3) { // soluciones y prescripciones
                    for(Integer idEstatusSolucion : estatusSolucionLista) {
                        if(idEstatusSolucion.equals(EstatusSolucion_Enum.POR_AUTORIZAR.getValue())) {
                            agruparParaAutorizar = true;
                            break;
                        }
                    }
                    total = surtimientoService.obtenerTotalPorFechaEstructuraPacientePrescripcionSolucionLazy(
                            fechaProgramada, paramBusquedaReporte, tipoPrescripcionSelectedList,
                            listEstatusPaciente, listEstatusPrescripcion,
                            listServiciosQueSurte, listEstatusSurtimiento,
                            estatusSolucionLista, idTipoSolucion, tipoProceso,
                            tipoPrescripcion, estatusSolucion, nombreEstructura, tipoSolucion, nombreMedico,
                            folio, fechaProgramada2, nombrePaciente, cama, folioPrescripcion, agruparParaAutorizar
                    );
                } else {    // soluciones para entrega de dispensación colectiva
                    total = surtimientoService.obtenerTotalSolucionesPorIdEstructuraTipoMezclaFechas(
                            paramBusquedaReporte
                            , tipoPrescripcionSelectedList
                            , listEstatusPaciente
                            , listEstatusPrescripcion
                            , listServiciosQueSurte
                            , listEstatusSurtimiento
                            , estatusSolucionLista
                            , idTipoSolucion
                            , tipoPrescripcion
                            , tipoSolucion
                            , fechaProgramada2
                            , folio
                            , folioPrescripcion
                            , nombrePaciente
                            , nombreEstructura
                            , cama
                            , nombreMedico
                            , estatusSolucion
                    );
                }
                totalReg = total.intValue();
            } else {
                totalReg = 0;
            }
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener el total. {}", ex.getMessage());
        }
        return totalReg;
    }

    @Override
    public Surtimiento_Extend getRowData(String rowKey) {
        for (Surtimiento_Extend surt : surtimientoSolucionList) {
            if (surt.getIdSurtimiento().equals(rowKey)) {
                return surt;
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(Surtimiento_Extend surtSolucion) {
        if (surtSolucion == null) {
            return null;
        }
        return surtSolucion;
    }

    public int getTotalReg() {
        return totalReg;
    }

    public void setTotalReg(int totalReg) {
        this.totalReg = totalReg;
    }

}
