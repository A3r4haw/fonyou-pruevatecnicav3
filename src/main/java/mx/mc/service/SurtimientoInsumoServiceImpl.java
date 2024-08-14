package mx.mc.service;

import java.util.Date;
import java.util.List;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.PrescripcionInsumoMapper;
import mx.mc.mapper.SurtimientoEnviadoMapper;
import mx.mc.mapper.SurtimientoInsumoMapper;
import mx.mc.model.PrescripcionInsumo;
import mx.mc.model.SurtimientoEnviado;
import mx.mc.model.SurtimientoInsumo;
import mx.mc.model.SurtimientoInsumo_Extend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/*
 * @author hramirez
 */
@Service
public class SurtimientoInsumoServiceImpl extends GenericCrudServiceImpl<SurtimientoInsumo, String> implements SurtimientoInsumoService {
    
    @Autowired
    private SurtimientoInsumoMapper surtimientoInsumoMapper;
    
    @Autowired
    private PrescripcionInsumoMapper prescripcionInsumoMapper;
    
    @Autowired
    private SurtimientoEnviadoMapper surtimientoEnviadoMapper;

    @Autowired
    public SurtimientoInsumoServiceImpl(GenericCrudMapper<SurtimientoInsumo, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public List<SurtimientoInsumo_Extend> obtenerSurtimientosProgramados(Date fechaProgramada 
            , String idSurtimiento 
            , String idPrescripcion 
            , List<Integer>  listEstatusSurtimientoInsumo
            , List<Integer>  listEstatusSurtimiento
            ,  String idEstructura
            , boolean surtimientoMixto) throws Exception {
        try {
            return surtimientoInsumoMapper.obtenerSurtimientosProgramados(fechaProgramada, idSurtimiento, idPrescripcion, listEstatusSurtimientoInsumo, listEstatusSurtimiento, idEstructura, surtimientoMixto);
        } catch (Exception ex) {
            throw new Exception("Error obtenerSurtimientosProgramados. " + ex.getMessage());
        }
    }
    
    @Override
    public List<SurtimientoInsumo_Extend> obtenerSurtimientosProgramadosExt(Date fechaProgramada 
            , String idSurtimiento 
            , String idPrescripcion 
            , List<Integer>  listEstatusSurtimientoInsumo
            , List<Integer>  listEstatusSurtimiento
            ,  String idEstructura
            , boolean surtimientoMixto) throws Exception {
        try {
            return surtimientoInsumoMapper.obtenerSurtimientosProgramadosExt(fechaProgramada, idSurtimiento, idPrescripcion, listEstatusSurtimientoInsumo, listEstatusSurtimiento, idEstructura, surtimientoMixto);
        } catch (Exception ex) {
            throw new Exception("Error obtenerSurtimientosProgramadosExt. " + ex.getMessage());
        }
    }
    
    @Override
    public List<SurtimientoInsumo_Extend> obtenerSurtimientoInsumoExtendedByIdPrescripcion(String idPrescripcion) throws Exception {
        try {
            return surtimientoInsumoMapper.obtenerSurtimientoInsumoExtended(idPrescripcion);
        } catch (Exception ex) {
            throw new Exception("Error obtenerSurtimientoInsumoExtendedByIdPrescripcion. " + ex.getMessage());
        }
    }

    @Override
    public boolean clonarSurtimientoInsumo(String idSurtimiento, String newFolio) throws Exception {
        try {
            return surtimientoInsumoMapper.clonarSusrtimientoPorIdSurtimiento(idSurtimiento,newFolio);
        } catch (Exception ex) {
            throw new Exception("Error al clonar SurtimientoInsumo " + ex.getMessage());
        }
    }
    
    @Override
    public boolean clonarSurtimientoInsumoCancelado(String idSurtimiento, String newFolio) throws Exception {
        try {
            return surtimientoInsumoMapper.clonarSurtimientoCanceladoPorIdSurtimiento(idSurtimiento,newFolio);
        } catch (Exception ex) {
            throw new Exception("Error al clonar SurtimientoInsumo Cancelado " + ex.getMessage());
        }
    }
    @Override
    public SurtimientoInsumo_Extend getSurtimientoByTracking(String folio, String numeroPaciente, String claveInstitucional, String lote,Date fechaCaducidad) throws Exception {
        try {
            return surtimientoInsumoMapper.getSurtimientoByTracking(folio, numeroPaciente, claveInstitucional, lote, fechaCaducidad);
        } catch (Exception e) {
            throw  new Exception("Error al getSurtimientoByTracking "+ e.getMessage());
        }
    }  

    @Override
    public SurtimientoInsumo_Extend getSurtimientoByTrackingByFolio(String folio, String numeroPaciente,String claveInstitucional,int numeroMedicacion) throws Exception {
        try {
            return surtimientoInsumoMapper.getSurtimientoByTrackingByFolio(folio, numeroPaciente,claveInstitucional,numeroMedicacion);
        } catch (Exception e) {
            throw new Exception("Error al Buscar el Surtimiento" + e.getMessage()); 
        }        
    }

    @Override
    public SurtimientoInsumo_Extend getSurtimientoByTrackingByPresInsumo(String folio, String claveInstitucional,int numeroMedicacion) throws Exception {
        try {
            return surtimientoInsumoMapper.getSurtimientoByTrackingByPresInsumo(folio, claveInstitucional,numeroMedicacion);
        } catch (Exception e) {
            throw new Exception("No se encontro la prescripcionInsumo" + e.getMessage());
        }        
    }
            
            @Override
    public List<SurtimientoInsumo> obtenerListaSurtiminetoInsumo(List<String> listInsumo) throws Exception {
        try {
            return surtimientoInsumoMapper.obtenerListaSurtiminetoInsumo(listInsumo);
        } catch (Exception e) {
            throw new Exception("No se encontro la lista de insumos" + e.getMessage());
        }        
    }
    
    @Override
    public SurtimientoInsumo_Extend obtenerSurtInsumosByIdSurtimientoInsumo(String idSurtimientoInsumo)throws Exception{
        try {
            return surtimientoInsumoMapper.obtenerSurtInsumosByIdSurtimientoInsumo(idSurtimientoInsumo);
        } catch (Exception e) {
            throw  new Exception("Error al obtenerSurtInsumosByIdSurtimientoInsumo "+ e.getMessage());
        }
    }
    
    
    @Override
    public SurtimientoInsumo_Extend obtenerSurtimientoInsumoByIdSurtimiento(String idSurtimiento) throws Exception{
        try {
            return surtimientoInsumoMapper.obtenerSurtimientoInsumoByIdSurtimiento(idSurtimiento);
        } catch (Exception e) {
            throw  new Exception("Error al obtenerSurtimientoInsumoByIdSurtimiento "+ e.getMessage());
        }
    }

    @Override
    public List<SurtimientoInsumo_Extend> obtenerSurtimInsumoByClaveAgrupada(String claveAgrupada) throws Exception {
        try {
            return surtimientoInsumoMapper.obtenerSurtimInsumoByClaveAgrupada(claveAgrupada);
        } catch (Exception e) {
            throw  new Exception("Error al obterner los surtimientos Insumos por claveAgrupada "+ e.getMessage());
        }
    }
   
    @Override
    public List<SurtimientoInsumo_Extend> obtenerSurtimientoInsumosByIdSurtimiento(String idSurtimiento, boolean mayorCero) throws Exception{
        try {
            return surtimientoInsumoMapper.obtenerSurtimientoInsumosByIdSurtimiento(idSurtimiento, mayorCero);
        } catch (Exception e) {
            throw new Exception("Ocurrio un error al actualizar la lista"+ e.getMessage());
        }
    }
    
    @Override
    public boolean actualizarSurtimientoInsumoList(List<SurtimientoInsumo> insumos) throws Exception {
        try{
            return surtimientoInsumoMapper.actualizarSurtimientoInsumoList(insumos);
        }catch(Exception e){
            throw new Exception("Ocurrio un error al actualizar la lista"+ e.getMessage());
        }
    }
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override    
    public boolean insertarInsumo(PrescripcionInsumo item, SurtimientoInsumo insumo) throws Exception{
        boolean insert=false;
       try{
           insert = prescripcionInsumoMapper.insertar(item);
           if(insert)
               insert= surtimientoInsumoMapper.insertar(insumo);
       }catch(Exception ex){
           throw new Exception("Ocurrio un error al insertar el insumo: "+ex.getMessage());
       } 
       return insert;
    }
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override    
    public boolean eliminarInsumo(PrescripcionInsumo item, SurtimientoInsumo insumo) throws Exception{
        boolean delete = false;
        try{
            delete = surtimientoInsumoMapper.eliminar(insumo);
            if(delete)
                delete = prescripcionInsumoMapper.eliminar(item);
        }catch(Exception ex){
           throw new Exception("Ocurrio un error al eliminar el insumo: "+ex.getMessage());
        }
        
        return delete;
    }
    
    @Override
    public List<SurtimientoEnviado> obtenerSurtimientoEnviados(String idSurtimiento) throws Exception{
        try {
            return surtimientoEnviadoMapper.detalleSurtimientoEnviadoByIdSurtimiento(idSurtimiento);
        } catch (Exception ex) {
            throw new Exception("Ocurrio un error al obtener Insumos Enviados: "+ex.getMessage());
        }
    }

    @Override
    public List<SurtimientoInsumo> obtenerListSurtimientosInsumosByIdSurtimiento(String idSurtimiento) throws Exception {
        try {
            return surtimientoInsumoMapper.obtenerListSurtimientosInsumosByIdSurtimiento(idSurtimiento);
        } catch(Exception ex) {
            throw new Exception("Ocurrio un error al obtenerListSurtimientosInsumosByIdSurtimiento : "+ex.getMessage());
        }        
    }
    
    @Override
    public List<SurtimientoInsumo_Extend> obtenerDetalleReceta(String idPrescripcion) throws Exception{
        try {
            return surtimientoInsumoMapper.obtenerDetalleReceta(idPrescripcion);
        } catch (Exception ex) {
            throw new Exception("Ocurrio un error al obtenerDetalleReceta : "+ex.getMessage());
        }
    }
    
    @Override
    public List<SurtimientoInsumo_Extend> obtenerListaByIdSurtimiento(String idSurtimiento) throws Exception {
        try {
            return surtimientoInsumoMapper.obtenerListaByIdSurtimiento(idSurtimiento);
        } catch (Exception ex) {
            throw new Exception("Ocurrio un error al obtenerListaByIdSurtimiento : "+ex.getMessage());
        }
    }
    
    @Override
    public boolean eliminaPorIdSurtimiento(String idSurtimiento) throws Exception {
        boolean res = false;
        try {
            res = surtimientoInsumoMapper.eliminaPorIdSurtimiento(idSurtimiento);
        } catch (Exception ex) {
            throw new Exception("Error en eliminarSurtimientosInsumo. " + ex.getMessage());
        }
        return res;
    }
    
    @Override
    public List<SurtimientoInsumo_Extend> obtenerMedicamentosPrescritosPorPaciente(
            String idPaciente 
            , List<String> idInsumoList
            , Integer tipoInsumo 
            , Integer noHrsPrev 
            , Integer noHrsPost 
            , List<Integer> listEstatusPrescripcion 
            , List<Integer> listEstatusSurtimientoInsumo 
    ) throws Exception {
        try {
            return surtimientoInsumoMapper.obtenerMedicamentosPrescritosPorPaciente(idPaciente, idInsumoList, tipoInsumo, noHrsPrev, noHrsPost, listEstatusPrescripcion, listEstatusSurtimientoInsumo);
        } catch (Exception ex) {
            throw new Exception("Error en eliminarSurtimientosInsumo. " + ex.getMessage());
        }
    }
    
    @Override
    public List<SurtimientoInsumo_Extend> obtenerSurtimientosProgramadosLoteSug(
            Date fechaProgramada 
            , String idSurtimiento 
            , String idPrescripcion 
            , List<Integer> listEstatusSurtimientoInsumo
            , List<Integer> listEstatusSurtimiento
            , List<String> idEstructuraList 
            , boolean surtimientoMixto) throws Exception {
        try {
            return surtimientoInsumoMapper.obtenerSurtimientosProgramadosLoteSug(fechaProgramada, idSurtimiento, idPrescripcion, listEstatusSurtimientoInsumo, listEstatusSurtimiento, idEstructuraList , surtimientoMixto);
        } catch (Exception ex) {
            throw new Exception("Error obtenerSurtimientosProgramados con Lote sugerido. " + ex.getMessage());
        }
    }    
    
    @Override
    public List<SurtimientoInsumo_Extend> obtenerDetalleSolucion(String idSolucion) throws Exception {
        try {
            return surtimientoInsumoMapper.obtenerDetalleSolucion(idSolucion);
        } catch (Exception ex) {
            throw new Exception("Error al obtener la lista de soluciones: " + ex.getMessage());
        }
    }

    
}
