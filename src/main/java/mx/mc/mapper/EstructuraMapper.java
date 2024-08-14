/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import mx.mc.model.Estructura;
import mx.mc.model.EstructuraExtended;

/**
 *
 * @author bbautista
 */
public interface EstructuraMapper extends GenericCrudMapper<Estructura, String> {

    List<Estructura> obtenerAlmacenes();

    List<Estructura> almacenesTransfer(@Param("idEstructura") String idEstructura);

    List<Estructura> almacenesForTransfer(@Param("idEstructura") String idEstructura,@Param("idTipoAlmacen") Integer idTipoAlmacen);
    
    Estructura obtenerEstructuraPadre();

    Estructura obtenerEstructura(@Param("idEstructura") String idEstructura);

    List<Estructura> obtenerUnidadesJerarquicas(@Param("idTipoAreaEstructura") int idTipoAreaEstructura);

    List<Estructura> obtenerUnidadesOrderTipo();

    List<Estructura> obtenerServiciosAlmcenPorIdEstructura(@Param("idEstructura") String idEstructura);

    List<Estructura> obtenerEstructurasPorTipo(@Param("listaTipoEstructura") List<Integer> listEstatusReabasto);

    Estructura getEstructuraPadreIdEstructura(@Param("idEstructura") String idEstructura);

    List<Estructura> getEstructuraByLisTipoAlmacen(@Param("listTipoAlmacen") List<Integer> listTipoAlmacen);
    
    List<Estructura> getEstructuraByLisTipoAreaEstructura(@Param("listTipoAreaEstructura") List<Integer> listTipoAreaEstructura);

    List<Estructura> getEstructuraServicios(@Param("listTipoAlmacen") List<Integer> listTipoAlmacen, @Param("listaEstructuras") List<String> listaEstructuras);

    Estructura getEstructuraForName(@Param("nombre") String nombre);

    List<Estructura> getAlmacenesForEntidad(@Param("idEstructura") String idEstructura,@Param("idTipoAlmacen") Integer idTipoAlmacen );
    
    List<Estructura> obtenerEstructurasLista();

    List<Estructura> obtenerAlmacenesActivos();

    List<Estructura> obtenerAlmacenPadre(Estructura estructura);

    List<Estructura> obtenerAlmacenServicio();

    List<Estructura> obtenerAlmacenPadreDesc(Estructura estructura);

    Estructura getEstructuraForClave(@Param("claveEstructura") String claveEStructura);

    Estructura obtenerEstructuraHl7(@Param("idEstructura") String idEstructura);

    List<Estructura> obtenerEstructuraSnHl7();

    List<String> obtenerIdsEstructuraJerarquica(@Param("idEstructuraPadre") String idEstructuraPadre, @Param("almacen") Boolean almacen);

    List<Estructura> obtenerEstructurasPadreCadit(@Param("idEstructuraPadre") String idEstructuraPadre);

    String obtenerclaveEstructuraPeriferico(@Param("idEstructura") String idEstructura);

    List<Estructura> getEstructuraListTipoAreaEstructura(@Param("listIdAreaEstructura") List<Integer> listIdAreaEstructura);

    List<Estructura> getEstructurDugthersYMe(@Param("idEstructuraPadre") String idEstructuraPadre);

    String validarExistenciaEstrucutra(@Param("nombre") String nombre);

    List<Estructura> obtenerServicioQueSurtePorIdEstructura(@Param("idEstructura") String idEstructura);

    Estructura obtenerAlmacenQueSurtePorIdEstructura(@Param("idEstructura") String idEstructura);

    String validarExistenciaClave(@Param("claveEstructura") String claveEstructura);

    List<Estructura> obtenerEstructurasPorIdAlmacenPeriferico(@Param("idEstructura") String idEstructura);

    List<Estructura> obtenerEstructurasActivosPorId(@Param("listaIdEstructuras") List<String> listaIdEstructuras);
    
    List<Estructura> obtenerAlmacenesQueSurtenServicio(@Param("idEstructura") String idEstructura);
    
    Estructura obtenerEstructuraAlmacenPerifericoPorNombreEstructura(@Param("nombreEstructura") String nombreEstructura);
    
    boolean quitarAlmacenPeriferico(Estructura estructura) throws Exception;

    List<Estructura> obtenerPorTipoAreayTipoAreaEstructura(@Param("idTipoArea") Integer idTipoArea, @Param("idTipoAreaEstructura") Integer idTipoAreaEstructura);
    
    List<Estructura> obtenerAlmacenesServicioPCActivos();
    
    List<Estructura> estructurasByFolioMUS(@Param("Folio") String folio,@Param("Status") String status);
    
    List<Estructura> estructuraItemList(@Param("idInsumo") String idInsumo,@Param("serviciosList") List<Estructura> serviciosList);
    
    List<Estructura> estFarmAlmacenSubalmacen(@Param("idEstructura") String idEstructura);
    
    boolean actualizarEntidadTodas(@Param("idEntidadHospitalaria") String idEntidadHospitalaria, @Param("idUsuario") String idUsuario, @Param("updateFecha") Date updateFecha );
    
    List<Estructura> obtenerEstructurasPorIdEntidad(@Param("idEntidadHospitalaria") String idEntidadHospitalaria);
    
    Estructura getEstructuraMedicoSIAM(@Param("nombre") String nombre);
    
    List<EstructuraExtended> obtenerEstructurasByEntidadTipoArea(@Param("idTipoAreaEstructura") Integer idTipoAreaEstructura,@Param("idEntidadHosp") String idEntidadHospitalaria);
    
    List<Estructura> obtenerPorTipoAlmacen(@Param("idTipoAlmacen") Integer idTipoAlmacen,@Param("idEntidad") String idEntidad );
    
    List<Estructura> obtenerServicios();
    
    List<Estructura> obtenerAlmacenesPorInsumo(@Param("estructura")Estructura estructura , @Param("idInsumo") String idInsumo);

    String obtenerAlmacenPadreOfSurtimiento(@Param("idEstructura") String idEstructura);
    
    List<Estructura> obtenerServicioActivosPorIdEstructuraOporIdAlmacen(@Param("idEstructura") String idEstructura , @Param("idEstructuraAlmacen") String idEstructuraAlmacen );
    
}
