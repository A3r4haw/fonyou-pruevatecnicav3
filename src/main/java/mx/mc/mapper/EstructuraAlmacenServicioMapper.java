package mx.mc.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import mx.mc.model.EstrucAlmacenServicio_Extend;
import mx.mc.model.EstructuraAlmacenServicio;

/**
 *
 * @author gcruz
 *
 */
public interface EstructuraAlmacenServicioMapper extends GenericCrudMapper<EstructuraAlmacenServicio, String> {

    EstrucAlmacenServicio_Extend obtenerEstructuraAsignada(@Param("idEstructuraAlmacen") String idEstructuraAlmacen);

    List<EstructuraAlmacenServicio> obtenerAlmacenServicio(@Param("idEstructuraAlmacen") String idEstructuraAlmacen);

    EstrucAlmacenServicio_Extend validarAsignacionServicio(@Param("idEstructuraServicio") String idEstructuraServicio);

    EstrucAlmacenServicio_Extend getAlmacenServicioByClaveEstructura(@Param("claveEstructura") String claveEstructura);

    List<EstrucAlmacenServicio_Extend> obtenerAlmacenServicios(@Param("idEntidadHosp") String idEntidadHosp,
            @Param("startingAt") int startingAt,
            @Param("maxPerPage") int maxPerPage,
            @Param("valueAlma") String valueAlma,
            @Param("valueServ") String valueServ,
            @Param("sortField") String sortField,
            @Param("sortOrder") String sortOrder);

    Long obtenerTotalAlmacenServicios(@Param("idEntidadHosp") String idEntidadHosp, @Param("valueAlma") String valueAlma, @Param("valueServ") String valueServ);

    List<EstrucAlmacenServicio_Extend> obtenerServiciosOfAlmacen(@Param("idAlmacen") String idAlmacen);

    boolean eliminarAlmacenServicioIdAlmacen(@Param("idEstructuraAlmacen") String idAlmacen);

    boolean insertarAlmacenServicioList(@Param("almacenServicioList") List<EstrucAlmacenServicio_Extend> almacenServicioList);

    List<EstrucAlmacenServicio_Extend> obtenerServiciosXOrdenar();

    List<EstrucAlmacenServicio_Extend> obtenerAlmacenesOfServicio(@Param("idServicio") String idServicio);

    EstrucAlmacenServicio_Extend obtenerTotalServiciosXOrdenar(@Param("idServicio") String idServicio);

    int obtenerTotalNumeroServicios(@Param("idServicio") String idServicio);

    boolean guardarPrioridadAlmacenes(@Param("almacenesXOrdenarList") List<EstrucAlmacenServicio_Extend> almacenesXOrdenarList);
}
