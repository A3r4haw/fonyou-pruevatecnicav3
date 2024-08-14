package mx.mc.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import mx.mc.model.IntipharmPrescription;
import mx.mc.model.IntipharmPrescriptionMedication;
import mx.mc.model.ParamLibMedControlados;
import mx.mc.model.Prescripcion;
import mx.mc.model.PrescripcionInsumo_Extended;
import mx.mc.model.Prescripcion_Extended;
import mx.mc.model.ReporteLibroControlados;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author hramirez
 */
public interface PrescripcionMapper extends GenericCrudMapper<Prescripcion, String> {

    List<Prescripcion_Extended> obtenerPrescripcionesPorIdPaciente(
            @Param("idPaciente") String idPaciente, @Param("idPrescripcion") String idPrescripcion, @Param("fechaPrescripcion") Date fechaPrescripcion, @Param("tipoConsulta") String tipoConsulta, @Param("recurrente") Integer recurrente, @Param("cadenaBusqueda") String cadenaBusqueda
    );

    List<Prescripcion_Extended> obtenerRecetasManuales(
            @Param("listaTipoPrescripcion") List<String> listaTipoPrescripcion, 
            @Param("listaEstatusPrescripcion") List<Integer> listaEstatusPrescripcion ,
            @Param("cadenaBusqueda") String cadenaBusqueda
    );

    boolean cancelar (@Param("idPrescripcion") String idPrescripcion
            , @Param("idUsuario") String idUsuario
            , @Param("fecha") Date fecha
            , @Param("idEstatusPrescripcion") Integer idEstatusPrescripcion
            , @Param("idEstatusGabinete") Integer idEstatusGabinete);
    
    boolean cancelarChiconcuac (@Param("idPrescripcion") String idPrescripcion, @Param("idUsuario") String idUsuario, @Param("fecha") Date fecha, @Param("idEstatusPrescripcion") Integer idEstatusPrescripcion, @Param("idEstatusGabinete") Integer idEstatusGabinete,@Param("folio") String folio);
    
    boolean cambiarEstatusPrescripcion(Prescripcion prescripcion);
    boolean wsCancelar(@Param("folio") String folio, @Param("procesado") Integer procesado);
    
    List<ReporteLibroControlados> obtenerReporteMedicamentosControlados(
            @Param("parametrosBusqueda") ParamLibMedControlados parametrosBusqueda);
    
    public Prescripcion verificarFolioCancelar(@Param("folio") String folio);
    
    public ArrayList<IntipharmPrescription>  selectCabiDrgOrdList();
    
    public List<IntipharmPrescriptionMedication>  selectCabiDrgOrdListMedication(@Param("folio")String folio);
    
    public boolean  actualizarByfolioSurt(@Param("folio") String folio);
    
    public String  obtenerFolioPrescBySurt(@Param("folio") String folio);
    
    Prescripcion_Extended obtenerPrescripcionByFolio(@Param("prescripcionFolio") String prescripcionFolio);
            
    List<Prescripcion_Extended> obtenerByFolioPrescripcionForList(@Param("pacienteNombre") String folioSurtimiento,@Param("idPadre") String idPadre, @Param("filter") String filter);

    boolean clonarSusrtimientoPorIdSurtimiento(@Param("idSurtimiento") String idSurtimiento,@Param("newFolio") String newFolio);
    
    boolean updateAsignPrescripcion(@Param("idEstatusPrescripcion") Integer idEstatusPrescripcion,@Param("comentarios") String comentarios, @Param("updateFecha") Date updateFecha, @Param("updateIdUsuario") String updateIdUsuario, @Param("idPrescripcion") String idPrescripcion);
    
    Prescripcion_Extended obtenerByFolioPrescripcion(@Param("prescripcionFolio") String prescripcionFolio);
    
    Prescripcion obtener1erPrescripcionByIdPacienteAndIdPrescripcion(@Param("idPaciente") String idPaciente, @Param("idPrescripcion") String idPrescripcion);
    
        
    List<Prescripcion_Extended> obtenerRecetasSurtidas(@Param("cadenaBusqueda") String cadenaBusqueda);
}
