package mx.mc.lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.DocumentoExtended;
import mx.mc.service.DocumentoService;

/**
 *
 * @author hramirez
 *
 */
public class DocumentoLazy extends LazyDataModel<DocumentoExtended> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentoLazy.class);
    private static final long serialVersionUID = 1L;

    private transient DocumentoService documentoService;

    private ParamBusquedaReporte paramBusquedaReporte;
    private List<DocumentoExtended> documentoList;
    private int totalReg;

    public DocumentoLazy() {

    }

    public DocumentoLazy(DocumentoService documentoService, ParamBusquedaReporte paramBusquedaReporte) {
        this.documentoService = documentoService;
        this.paramBusquedaReporte = paramBusquedaReporte;
//        this.totalReg = 0;
        this.documentoList = new ArrayList<>();
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
    public List<DocumentoExtended> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> map) {
        LOGGER.debug("mx.mc.lazy.DocumentoLazy.load()");
        if (documentoList != null) {
            if (getRowCount() <= 0) {
                LOGGER.debug("Obtener total resultados");
                int total = obtenerTotalResultados();
                setRowCount(total);
            }
            try {
                this.documentoList = documentoService.obtenerListaDocumentoActivo(paramBusquedaReporte, startingAt, maxPerPage, sortField, sortOrder);
                //(paramBusquedaReporte, startingAt, maxPerPage, sortField, sortOrder);
            } catch (Exception ex) {
                LOGGER.error("Error al realizar la consulta de reporteAlmacenes. {}", ex.getMessage());
                documentoList = new ArrayList<>();
            }
            setPageSize(maxPerPage);
        } else {
            this.documentoList = new ArrayList<>();

        }
        return this.documentoList;
    }

    /**
     * Obtiene el total de registros con las condiciones de búsqueda
     *
     * @return
     */
    private int obtenerTotalResultados() {
        LOGGER.trace("mx.mc.lazy.DocumentoLazy.obtenerTotalResultados()");
        try {
            if (paramBusquedaReporte != null) {
                Long total = documentoService.obtenerNoTotalDocumentoActivo(paramBusquedaReporte);
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
    public DocumentoExtended getRowData(String rowKey) {
        for (DocumentoExtended doc : documentoList) {
            if (doc.getIdDocumento().equals(rowKey)) {
                return doc;
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(DocumentoExtended doc) {
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
