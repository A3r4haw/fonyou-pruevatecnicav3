package mx.mc.service;

import java.util.List;
import java.util.Map;
import mx.mc.model.EstrucAlmacenServicio_Extend;
import mx.mc.model.EstructuraAlmacenServicio;
import org.primefaces.model.SortOrder;

/**
 *
 * @author gcruz
 *
 */
public interface EstructuraAlmacenServicioService extends GenericCrudService<EstructuraAlmacenServicio, String> {

    EstrucAlmacenServicio_Extend obtenerEstructuraAsignada(String idEstructuraAlmacen) throws Exception;

    List<EstructuraAlmacenServicio> obtenerAlmacenServicio(String idEstructuraAlmacen) throws Exception;

    EstrucAlmacenServicio_Extend validarAsignacionServicio(String idEstructuraServicio) throws Exception;

    EstrucAlmacenServicio_Extend getAlmacenServicioByClaveEstructura(String claveEstructura) throws Exception;

    public List<EstrucAlmacenServicio_Extend> obtenerAlmacenServicios(String idEntidadHosp, int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> map) throws Exception;

    public Long obtenerTotalAlmacenServicios(String idEntidadHosp, Map<String, Object> map) throws Exception;

    public List<EstrucAlmacenServicio_Extend> obtenerServiciosOfAlmacen(String idAlmacen) throws Exception;

    public boolean eliminarAlmacenServicioIdAlmacen(String idAlmacen) throws Exception;

    public boolean insertarAlmacenServicioList(String idAlmacen, List<EstrucAlmacenServicio_Extend> almacenServicioList) throws Exception;

    public List<EstrucAlmacenServicio_Extend> obtenerServiciosXOrdenar() throws Exception;

    public List<EstrucAlmacenServicio_Extend> obtenerAlmacenesOfServicio(String idServicio) throws Exception;

    public boolean guardarPrioridadAlmacenes(List<EstrucAlmacenServicio_Extend> almacenesXOrdenarList) throws Exception;
                    
    public boolean insertarAlmacenServicioList(List<EstrucAlmacenServicio_Extend> almacenServicioList) throws Exception;
}
