package mx.mc.service;

import java.util.List;
import mx.mc.model.Estudio;
import mx.mc.model.ParamBusquedaReporte;
import org.primefaces.model.SortOrder;

public interface EstudioService extends GenericCrudService<Estudio,String> {
    public List<Estudio> obtenerBusquedaEstudio(ParamBusquedaReporte paramBusquedaReporte,int tipoEstudio, int startingAt, int maxPerPage, String sortField, SortOrder sortOrder) throws Exception;
    public Long obtenerTotalBusquedaEstudio(ParamBusquedaReporte paramBusquedaReporte, int tipoEstudio) throws Exception;
    public Estudio obtenerPorIdEstudio(String idEstudio) throws Exception;
    public Estudio obtenerPorClaveEstudio(String claveEstudio) throws Exception;
}

