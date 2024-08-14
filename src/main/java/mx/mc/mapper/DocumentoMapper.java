package mx.mc.mapper;

import java.util.List;
import mx.mc.model.Documento;
import mx.mc.model.DocumentoExtended;
import mx.mc.model.ParamBusquedaReporte;
import org.apache.ibatis.annotations.Param;
import org.primefaces.model.SortOrder;

/**
 *
 * @author hramirez
 */
public interface DocumentoMapper extends GenericCrudMapper<Documento, String> {

    public List<DocumentoExtended> obtenerListaDocumentoActivo(
            @Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte,
             @Param("startingAt") int startingAt,
             @Param("maxPerPage") int maxPerPage,
             @Param("sortField") String sortField,
             @Param("sortOrder") SortOrder sortOrder
    );

    Long obtenerNoTotalDocumentoActivo(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte);

}
