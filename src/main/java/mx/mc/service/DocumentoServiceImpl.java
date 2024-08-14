package mx.mc.service;

import java.util.List;
import mx.mc.mapper.DocumentoMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.Documento;
import mx.mc.model.DocumentoExtended;
import mx.mc.model.ParamBusquedaReporte;
import org.primefaces.model.SortOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * @author hramirez
 */
@Service
public class DocumentoServiceImpl extends GenericCrudServiceImpl<Documento, String> implements DocumentoService {

    @Autowired
    private DocumentoMapper documentoMapper;

    @Autowired
    public DocumentoServiceImpl(GenericCrudMapper<Documento, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public List<DocumentoExtended> obtenerListaDocumentoActivo(ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage , String sortField, SortOrder sortOrder) throws Exception {
        try {
            return documentoMapper.obtenerListaDocumentoActivo(paramBusquedaReporte, startingAt, maxPerPage , sortField, sortOrder);
        } catch (Exception ex) {
            throw new Exception("Error al obtener Lista de Documentos: " + ex.getMessage());
        }
    }
    
    @Override
    public Long obtenerNoTotalDocumentoActivo(ParamBusquedaReporte paramBusquedaReporte) throws Exception {
        try {
            return documentoMapper.obtenerNoTotalDocumentoActivo(paramBusquedaReporte);
        } catch (Exception e) {
            throw new Exception("Error al Obtener el total de Documentos. " + e.getMessage());
        }
    }
    

}
