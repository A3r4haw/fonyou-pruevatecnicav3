package mx.mc.service;

import java.util.List;
import mx.mc.mapper.CensoInsumoDetalleMapper;
import mx.mc.mapper.CensoInsumoMapper;
import mx.mc.mapper.CensoPacienteMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.CensoInsumo;
import mx.mc.model.CensoInsumoDetalle;
import mx.mc.model.CensoPaciente;
import mx.mc.model.CensoPacienteExtended;
import mx.mc.model.ParamBusquedaReporte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author apalacios
 */
@Service
public class CensoPacienteServiceImpl extends GenericCrudServiceImpl<CensoPaciente, String> implements CensoPacienteService{
    
    @Autowired
    private CensoPacienteMapper censoPacienteMapper;
    @Autowired
    private CensoInsumoMapper censoInsumoMapper;
    @Autowired
    private CensoInsumoDetalleMapper censoInsumoDetalleMapper;
    
    @Autowired
    public CensoPacienteServiceImpl(GenericCrudMapper<CensoPaciente, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
    
    @Override
    public List<CensoPacienteExtended> obtenerRegistrosPorCriterioDeBusquedaLazy(ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage) throws Exception {
        try {
            return censoPacienteMapper.obtenerRegistrosPorCriterioDeBusquedaLazy(paramBusquedaReporte, startingAt, maxPerPage);
        } catch (Exception e) {
            throw  new Exception("Error al obtener los Registros de Censo Pacientes. " + e.getMessage());
        }
    }

    @Override
    public Long obtenerTotalRegistrosPorCriterioDeBusquedaLazy(ParamBusquedaReporte paramBusquedaReporte) throws Exception {
        try {
            return censoPacienteMapper.obtenerTotalRegistrosPorCriterioDeBusquedaLazy(paramBusquedaReporte);
        } catch (Exception e) {
            throw  new Exception("Error al obtener los Registros de Censo Pacientes. " + e.getMessage());
        }
    }
    
    @Transactional (propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean registrarCensoPaciente(CensoPaciente censoPaciente, CensoInsumo censoInsumo, List<CensoInsumoDetalle> listaCensoInsumoDetalle) throws Exception {
        boolean res = true;
        try {
            res = (censoPacienteMapper.insertarCensoPaciente(censoPaciente) > 0);
            if (!res) {
                throw new Exception("Error al registrar Censo Paciente. ");
            } else {
                res = (censoInsumoMapper.insertarCensoInsumo(censoInsumo) > 0);
                if (!res) {
                    throw new Exception("Error al registrar Censo Insumo. ");
                } else {
                    res = censoInsumoDetalleMapper.insertarListaCensoInsumoDetalle(listaCensoInsumoDetalle);
                    if (!res){
                        throw new Exception("Error al registrar lista de Insumo Detalle. ");
                    }
                }
            }
        }
        catch (Exception ex) {
            throw new Exception("Error al registrar Censo Paciente. " + ex.getMessage());
        }
        return res;
    }
    
    @Override
    public List<CensoPacienteExtended> obtenerRegistrosHistorico(String idCensoPaciente, String idPaciente, String claveDerechohabiencia) throws Exception {
        try {
            if (idPaciente == null)
                idPaciente = "";
            if (claveDerechohabiencia == null)
                claveDerechohabiencia = "";
            return censoPacienteMapper.obtenerRegistrosHistorico(idCensoPaciente, idPaciente, claveDerechohabiencia);
        } catch (Exception e) {
            throw  new Exception("Error al obtener los Registros del Censo Histórico. " + e.getMessage());
        }
    }
    
    @Transactional (propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean actualizarCensoPaciente(CensoPaciente censoPaciente, CensoInsumo censoInsumo, List<CensoInsumoDetalle> listaCensoInsumoDetalle) throws Exception {
        boolean res = true;
        try {
            res = (censoPacienteMapper.actualizarCensoPaciente(censoPaciente) > 0);
            if (!res) {
                throw new Exception("Error al actualizar Censo Paciente. ");
            } else {
                res = (censoInsumoMapper.actualizarCensoInsumo(censoInsumo) > 0);
                if (!res) {
                    throw new Exception("Error al actualizar Censo Insumo. ");
                } else {
                    res = censoInsumoDetalleMapper.actualizarListaCensoInsumoDetalle(listaCensoInsumoDetalle);
                    if (!res) {
                        throw new Exception("Error al actualizar lista de Insumo Detalle. ");
                    }
                }
            }
        }
        catch (Exception ex) {
            throw new Exception("Error al registrar Censo Paciente. " + ex.getMessage());
        }
        return res;
    }
}
