package mx.mc.mapper;

import java.util.Date;
import java.util.List;
import mx.mc.model.SurtimientoEnviado;
import mx.mc.model.SurtimientoEnviado_Extend;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author hramirez
 */
public interface SurtimientoEnviadoMapper extends GenericCrudMapper<SurtimientoEnviado, String> {

    boolean registraSurtimientoEnviadoList(List<SurtimientoEnviado> surtimientoEnviadoList);

    boolean registraSurtimientoEnviadoExtendedList(List<SurtimientoEnviado_Extend> surtimientoEnviadoList);

    List<SurtimientoEnviado> obtenerListaSurtimientoEnviadoPorIdSurtimiento(@Param("idSurtimiento") String idSurtimiento);

    List<SurtimientoEnviado_Extend> detalleInsumoSurtimientoEnviadoPorIdSurtimientoInsumo(@Param("idSurtimientoInsumo") String idSurtimientoInsumo);

    List<SurtimientoEnviado> detalleInsumoSurtimientoDevolucion(@Param("idPrescripcion") String idPrescripcion);
            
    List<String> surtimientoEnviadoByPrecripcion(@Param("idPrescripcion") String idPrescripcion);

    List<SurtimientoEnviado_Extend> detalleInsumoSurtimientoDevolucionExtend(@Param("idPrescripcion") String idPrescripcion);

    List<SurtimientoEnviado> insumosEnMinistracion(@Param("folio") String folio, @Param("idSurtimientoEnviado") String idSurtimientoEnviado);

    List<SurtimientoEnviado_Extend> insumosEnMinistracionExtend(@Param("folio") String folio, @Param("idSurtimientoEnviado") String idSurtimientoEnviado);

    boolean actualizaCantidadRecibidaList(List<SurtimientoEnviado> surtimientoEnviadoList);

    boolean revertirSurtimientoEnviado0(@Param("listaDetalle") List<SurtimientoEnviado> surtimientoEnviadoList);

    boolean revertirSurtimientoEnviado1(@Param("listaDetalle") List<SurtimientoEnviado> surtimientoEnviadoList);

    boolean revertirSurtimientoEnviado2(@Param("listaDetalle") List<SurtimientoEnviado> surtimientoEnviadoList, @Param("idEstatus") Integer isEstatus, @Param("idUsuario") String idUsuario);

    List<SurtimientoEnviado_Extend> obtenerByIdSurtimientoInsumoRecepMedi(@Param("idSurtimientoInsumo") String idSurtimientoInsumo);

    List<SurtimientoEnviado_Extend> obtenerByIdSurtimientoInsumoDevMedi(@Param("idSurtimiento") String idSurtimiento);
    
    List<SurtimientoEnviado_Extend> obtenerDetalleInsumoDevMediByIdSurtimientoIdDevolucion(@Param("idSurtimiento") String idSurtimiento,@Param("idDevolucionMinistracion") String idDevolucionMinistracion);
    
    List<SurtimientoEnviado_Extend> obtenerByIdSurtimientoEnviadoDevMedi(@Param("idSurtimiento") String idSurtimiento);

    List<SurtimientoEnviado> obtenerByIdPrescripcion(@Param("idPrescripcion") String idPrescripcion);
    
    SurtimientoEnviado_Extend obtenerByIdSurtimientoEnviado(@Param("idSurtimientoEnviado") String idSurtimientoEnviado);
    
    SurtimientoEnviado_Extend obtenerSurtimientoEnviadoByIdSurtimientoInsumo(@Param("idSurtimientoInsumo") String idSurtimientoInsumo);
    
    boolean updateAsignPrescripcionEnviado(@Param("idSurtimientoInsumo") String idSurtimientoInsumo, @Param("updateFecha") Date updateFecha, @Param("updateIdUsuario") String updateIdUsuario, @Param("idSurtimientoEnviado") String idSurtimientoEnviado);
        
    List<SurtimientoEnviado_Extend>  detalleSurtimientoEnviadoXIdSurtimientoInsumo(@Param("idSurtimientoInsumo") String idSurtimientoInsumo, @Param("mayorCero") boolean mayorCero);
    
    List<SurtimientoEnviado> detalleSurtimientoEnviadoByIdSurtimiento(@Param("idSurtimiento") String idSurtimiento);
        
    boolean actualizaSurtimientoEnviadoList(@Param("listaDetalle") List<SurtimientoEnviado> surtimientoEnviadoList);
    
    List<SurtimientoEnviado_Extend> obtenerSurtimientoEnviadoPorIdEstructuraIdInsumoIdInventario(
            @Param("idEstructura") String idEstructura , @Param("idInsumo") String idInsumo , @Param("idInventario") String idInventario );
    
    boolean eliminarPorIdSurtimientoInsumo(@Param("idSurtimientoInsumo") String idSurtimientoInsumo);
    
}
