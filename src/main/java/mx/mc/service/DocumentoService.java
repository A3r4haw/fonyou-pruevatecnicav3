package mx.mc.service;

import java.util.List;
import mx.mc.model.Documento;
import mx.mc.model.DocumentoExtended;
import mx.mc.model.ParamBusquedaReporte;
import org.primefaces.model.SortOrder;

/**
 *
 * @author hramirez
 */
public interface DocumentoService extends GenericCrudService<Documento, String> {

    public List<DocumentoExtended> obtenerListaDocumentoActivo(ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage , String sortField, SortOrder sortOrder) throws Exception;

    Long obtenerNoTotalDocumentoActivo(ParamBusquedaReporte paramBusquedaReporte) throws Exception;

}
