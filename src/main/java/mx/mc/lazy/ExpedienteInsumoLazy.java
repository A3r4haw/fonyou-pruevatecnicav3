package mx.mc.lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.Medicamento_Extended;
import mx.mc.model.Medicamento_Extended;
import mx.mc.service.DocumentoService;
import mx.mc.service.MedicamentoService;

/**
 *
 * @author hramirez
 *
 */
public class ExpedienteInsumoLazy extends LazyDataModel<Medicamento_Extended> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExpedienteInsumoLazy.class);
    private static final long serialVersionUID = 1L;

    private transient MedicamentoService medicamentoService;

    private ParamBusquedaReporte paramBusquedaReporte;
    private List<Medicamento_Extended> insumoLista;
    private int totalReg;

    public ExpedienteInsumoLazy() {

    }

    public ExpedienteInsumoLazy(MedicamentoService medicamentoService, ParamBusquedaReporte paramBusquedaReporte) {
        this.medicamentoService = medicamentoService;
        this.paramBusquedaReporte = paramBusquedaReporte;
//        this.totalReg = 0;
        this.insumoLista = new ArrayList<>();
    }

    /**
     * Obtiene registros con las codiciones de busqeuda y paginado
     *
     * @param startingAt
     * @param maxPerPage
     * @param sortField
     * @param sortOrder
     * @param map
     * @return
     */
    @Override
    public List<Medicamento_Extended> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> map) {
        LOGGER.debug("mx.mc.lazy.ExpedienteInsumoLazy.load()");
        if (insumoLista != null) {
            if (getRowCount() <= 0) {
                LOGGER.debug("Obtener total resultados");
                int total = obtenerTotalResultados();
                setRowCount(total);
            }
            try {
                this.insumoLista = medicamentoService.obtenerInsumoLista(paramBusquedaReporte, startingAt, maxPerPage, sortField, sortOrder);
            } catch (Exception ex) {
                LOGGER.error("Error al realizar la consulta de reporteAlmacenes. {}", ex.getMessage());
                insumoLista = new ArrayList<>();
            }
            setPageSize(maxPerPage);
        } else {
            this.insumoLista = new ArrayList<>();

        }
        return this.insumoLista;
    }

    /**
     * Obtiene el total de registros con las condiciones de búsqueda
     *
     * @return
     */
    private int obtenerTotalResultados() {
        LOGGER.trace("mx.mc.lazy.ExpedienteInsumoLazy.obtenerTotalResultados()");
        try {
            if (paramBusquedaReporte != null) {
                Long total = medicamentoService.obtenerInsumoListaNoTotal(paramBusquedaReporte);
                totalReg = Integer.valueOf(total.intValue());
            } else {
                totalReg = 0;
            }
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener el total. {}", ex.getMessage());
        }
        return totalReg;
    }

    @Override
    public Medicamento_Extended getRowData(String rowKey) {
        for (Medicamento_Extended doc : insumoLista) {
            if (doc.getIdMedicamento().equals(rowKey)) {
                return doc;
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(Medicamento_Extended doc) {
        if (doc == null) {
            return null;
        }
        return doc;
    }

    public int getTotalReg() {
        return totalReg;
    }

    public void setTotalReg(int totalReg) {
        this.totalReg = totalReg;
    }

}
