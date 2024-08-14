package mx.mc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.RepCostPerCapServMapper;
import mx.mc.model.DataResultReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author aortiz
 *
 */
@Service
public class RepCostPerCapServServiceImpl extends GenericCrudServiceImpl<DataResultReport, String> implements RepCostPerCapServService {

    @Autowired
    private RepCostPerCapServMapper repCostPerCapServMapper;

    public RepCostPerCapServServiceImpl() {
        //No code needed in constructor
    }

    public RepCostPerCapServServiceImpl(GenericCrudMapper<DataResultReport, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public List<DataResultReport> obtenerDatosRepCostPerCapServ(
            String idEstructura, Date fechaInicio, Date fechaFin) throws Exception {
        List<DataResultReport> listaDatos = new ArrayList<>();
        try {
            listaDatos = repCostPerCapServMapper.obtenerDatosRepCostPerCapServ(
                    idEstructura, fechaInicio, fechaFin);
        } catch (Exception e) {
            throw new Exception("ocurrio un error en obtenerDatosRepCostPerCapServ :: " + e.getMessage());
        }
        return listaDatos;
    }

}
