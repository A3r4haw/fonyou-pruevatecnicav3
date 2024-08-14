package mx.mc.mapper;

import java.util.Date;
import java.util.List;
import mx.mc.model.PrescripcionInsumo;
import mx.mc.model.PrescripcionInsumo_Extended;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author hramirez
 */
public interface PrescripcionInsumoMapper extends GenericCrudMapper<PrescripcionInsumo, String> {

    List<PrescripcionInsumo_Extended> obtenerInsumosPorIdPrescripcion(@Param("idPrescripcion") String idPrescripcion, @Param("tipoInsumo") Integer tipoInsumo );
    
    boolean registraMedicamentoList (List<PrescripcionInsumo> insumoList);
    
    boolean eliminaMedicamentoList (@Param("idPrescripcion") String idPrescripcion);

    boolean cambiarEstatusPrescripcion(PrescripcionInsumo prescripcionInsumo);
    
    boolean wsCancelar(@Param("folio") String folio, @Param("procesado") Integer procesado);
    
    PrescripcionInsumo_Extended obtenerPrescripcionInsumoByIdPrescripcion(@Param("idPrescripcion") String idPrescripcion);
    
    boolean updateAsignPrescripcionInsumo(@Param("idEstatusPrescripcion") Integer idEstatusPrescripcion, @Param("updateFecha") Date updateFecha, @Param("updateIdUsuario") String updateIdUsuario, @Param("idPrescripcionInsumo") String idPrescripcionInsumo);
    
    boolean actualizarInsumo(PrescripcionInsumo_Extended prescripcionInsumo);
    
    List<PrescripcionInsumo> obtenerPrescripcionInsumosPorIdPrescripcion(@Param("idPrescripcion") String idPrescripcion);
    
    boolean actualizarListaPrescripcionInsumo(List<PrescripcionInsumo> insumoList);    
    
    List<PrescripcionInsumo_Extended> obtenerPrescripcionesByIdPaciente(@Param("idPaciente") String idPaciente, @Param("folioSurtimiento") String folioSurtimiento);
    
    Integer totalSurtimientosByIdPacienteAndIdInsumo(@Param("idPaciente") String idPaciente, @Param("idInsumo") String idInsumo, @Param("idPrescripcionInsumo") String idPrescripcionInsumo);

    PrescripcionInsumo_Extended obtenerPrimerPrescripcionInsumoByIdPrescripcion(@Param("idPrescripcion") String idPrescripcion);
    
    boolean cancelarPorIdPrescripcion(@Param("idPrescripcion") String idPrescripcion
            , @Param("idEstatusPrescripcion") Integer idEstatusPrescripcion
            , @Param("idUsuario") String idUsuario
            , @Param("fecha") Date fecha
    );
}
