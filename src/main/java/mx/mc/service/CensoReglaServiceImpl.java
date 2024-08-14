package mx.mc.service;

import java.util.List;
import mx.mc.mapper.CensoReglaDetalleMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.CensoRegla;
import mx.mc.model.CensoReglaExtended;
import mx.mc.model.ParamBusquedaReporte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mx.mc.mapper.CensoReglaMapper;
import mx.mc.model.CensoReglaDetalle;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author apalacios
 */
@Service
public class CensoReglaServiceImpl extends GenericCrudServiceImpl<CensoRegla, String> implements CensoReglaService {
    
    @Autowired
    private CensoReglaMapper censoReglaMapper;
    @Autowired
    private CensoReglaDetalleMapper censoReglaDetalleMapper;
    
    @Autowired
    public CensoReglaServiceImpl(GenericCrudMapper<CensoRegla, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
    
    @Override
    public CensoReglaExtended obtenerRegla(String idMedicamento, String idDiagnostico) throws Exception {
        try {
            return censoReglaMapper.obtenerRegla(idMedicamento, idDiagnostico);
        } catch (Exception e) {
            throw  new Exception("Error al obtener el Registro de CensoRegla. " + e.getMessage());
        }
    }
    
    @Override
    public List<CensoReglaDetalle> obtenerReglaDetalle(String idCensoRegla) throws Exception {
        try {
            return censoReglaDetalleMapper.obtenerReglaDetalle(idCensoRegla);
        } catch (Exception e) {
            throw  new Exception("Error al obtener los Registros de CensoReglaDetalle. " + e.getMessage());
        }
    }
    
    @Override
    public List<CensoReglaExtended> obtenerRegistrosPorCriterioDeBusquedaLazy(ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage) throws Exception {
        try {
            return censoReglaMapper.obtenerRegistrosPorCriterioDeBusquedaLazy(paramBusquedaReporte, startingAt, maxPerPage);
        } catch (Exception e) {
            throw  new Exception("Error al obtener los Registros de Reglas Censo Pacientes. " + e.getMessage());
        }
    }

    @Override
    public Long obtenerTotalRegistrosPorCriterioDeBusquedaLazy(ParamBusquedaReporte paramBusquedaReporte) throws Exception {
        try {
            return censoReglaMapper.obtenerTotalRegistrosPorCriterioDeBusquedaLazy(paramBusquedaReporte);
        } catch (Exception e) {
            throw  new Exception("Error al obtener los Registros de Reglas Censo Pacientes. " + e.getMessage());
        }
    }
    
    @Transactional (propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean registrarCensoRegla(CensoRegla censoRegla, List<CensoReglaDetalle> listaCensoReglaDetalle) throws Exception {
        boolean res = true;
        try {
            res = (censoReglaMapper.insertarCensoRegla(censoRegla) > 0);
            if (!res) {
                throw new Exception("Error al registrar regla del Censo. ");
            } else {
                res = censoReglaDetalleMapper.insertarListaCensoReglaDetalle(listaCensoReglaDetalle);
                if (!res){
                    throw new Exception("Error al registrar detalle de la regla del Censo. ");
                }
            }
        }
        catch (Exception ex) {
            throw new Exception("Error al registrar la regla del Censo. " + ex.getMessage());
        }
        return res;
    }
}
