package mx.mc.service; 

import java.util.List;
import mx.mc.mapper.RepExistenciasConsolidadasMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.mc.model.Medicamento_Extended;


/**
 *
 * @author olozada
 *
 */
@Service
public class RepExistenciasConsolidadasServiceImpl implements RepExistenciasConsolidadasService {

    @Autowired
    private RepExistenciasConsolidadasMapper repExistenciasConsolidadasMapper;

    public RepExistenciasConsolidadasServiceImpl() {
        //No code needed in constructor
    }

   @Override
    public List<Medicamento_Extended> obtenerReporteExist_Consolidadas(Medicamento_Extended medicamento_Extended, int startingAt, int maxPerPage) throws Exception {
        try {
            return repExistenciasConsolidadasMapper.obtenerReporteExist_Consolidadas(medicamento_Extended, startingAt, maxPerPage);
        } catch (Exception e) {
            throw new Exception("Error al obtener Existencias Consolidadas "+ e.getMessage());
        }
    }

    @Override
    public Long obtenerTotalReporteExist_Consolidadas(Medicamento_Extended medicamento_Extended) throws Exception {
        try {
            return repExistenciasConsolidadasMapper.obtenerTotalReporteExist_Consolidadas(medicamento_Extended);
        } catch (Exception e) {
            throw new Exception("Error al obtener el total Consolidado "+ e.getMessage());
        }
    }
   @Override
    public List<Medicamento_Extended> obtenerReporteEntregaExist_Consolidadas(Medicamento_Extended medicamento_Extended, int startingAt, int maxPerPage) throws Exception {
        try {
            return repExistenciasConsolidadasMapper.obtenerReporteEntregaExist_Consolidadas(medicamento_Extended, startingAt, maxPerPage);
        } catch (Exception e) {
            throw new Exception("Error al obtener Existencias Consolidadas "+ e.getMessage());
        }
    }

    @Override
    public Long obtenerTotalReporteEntregaExist_Consolidadas(Medicamento_Extended medicamento_Extended) throws Exception {
        try {
            return repExistenciasConsolidadasMapper.obtenerTotalReporteEntregaExist_Consolidadas(medicamento_Extended);
        } catch (Exception e) {
            throw new Exception("Error al obtener el total Consolidado "+ e.getMessage());
        }
    }

}
