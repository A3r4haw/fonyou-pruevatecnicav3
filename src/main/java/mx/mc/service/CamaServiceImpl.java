package mx.mc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.mc.mapper.CamaMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.Cama;
import mx.mc.model.CamaExtended;
import org.primefaces.model.SortOrder;

/**
 *
 * @author gcruz
 *
 */
@Service
public class CamaServiceImpl extends GenericCrudServiceImpl<Cama, String> implements CamaService {

    @Autowired
    private CamaMapper camaMapper;

    @Autowired
    public CamaServiceImpl(GenericCrudMapper<Cama, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public List<CamaExtended> obtenerCamasByServicio(String idEstructura) throws Exception {
        List<CamaExtended> listaCamas = new ArrayList<>();
        try {
            listaCamas = camaMapper.obtenerCamasByServicio(idEstructura);
        } catch (Exception e) {
            throw new Exception("Error al obtener las camas por servicio" + e.getMessage());
        }

        return listaCamas;
    }

    public Cama obtenerCama(String idCama) throws Exception {
        Cama unaCama = new Cama();
        try {
            unaCama = camaMapper.obtenerCama(idCama);
        } catch (Exception e) {
            throw new Exception("Error al obtener la camas por idCama" + e.getMessage());
        }

        return unaCama;
    }

    @Override
    public List<CamaExtended> obtenerCamaByEstructuraAndEstatus(String idEstructura, List<Integer> listaEstatusCama) throws Exception {
        List<CamaExtended> listaCamas = null;
        try {
            listaCamas = camaMapper.obtenerCamaByEstructuraAndEstatus(idEstructura, listaEstatusCama);
        } catch (Exception e) {
            throw new Exception("Error al obtener las camas" + e.getMessage());
        }
        return listaCamas;
    }

    @Override
    public Cama obtenerPorNombre(Cama cama) throws Exception {
         try {
            return camaMapper.obtenerCamaPorNombre(cama);
        } catch (Exception e) {
            throw new Exception("Error al obtener la camas por idCama" + e.getMessage());
        }
    }

    @Override
    public CamaExtended obtenerCamaNombreEstructura(String idPaciente) throws Exception {
        try {
            return camaMapper.obtenerCamaNombreEstructura(idPaciente);
        } catch (Exception e) {
            throw new Exception("Error al obtener la cama "+ e.getMessage());
        }
    }
    
    public Cama obterCamaPorNombreYEstructura(String nombreCama, String idEstructura) throws Exception {
        try {
            return camaMapper.obterCamaPorNombreYEstructura(nombreCama, idEstructura);
        } catch(Exception ex) {
            throw new Exception("Ocurrio un error al momento de buscar la cama por nombre y servicio : "
                                + "metodo obterCamaPorNombreYEstructura " + ex.getMessage());
        }
    }

    @Override
    public List<CamaExtended> obtenerServicioCamas(String idEntidadHosp,int startingAt, int maxPerPage, String sortField, SortOrder sortOrder,Map<String, Object> map) throws Exception {
        List<CamaExtended> camasList = new ArrayList<>();
        try {
            String order = sortOrder==SortOrder.ASCENDING ? "asc": sortOrder==SortOrder.DESCENDING ? "desc" : null;              
            String valueServ=null;
            String valueRoom=null;

            if(map.size()>0){
                if(map.containsKey("servicio")){
                    valueServ = map.get("servicio").toString();
                }
                if(map.containsKey("nombreCama")){
                    valueRoom = map.get("nombreCama").toString();
                }
            }
            
            camasList = camaMapper.obtenerServicioCamas(idEntidadHosp,startingAt, maxPerPage, valueServ,valueRoom,sortField, order);
        } catch (Exception e) {
            throw new Exception("Error al obtenerServicioCamas "+ e.getMessage());
        }
        return camasList;
    }

    @Override
    public Long obtenerTotalServicioCamas(String idEntidadHosp,Map<String, Object> map) throws Exception {
        Long total = Long.MIN_VALUE;
            String valueServ=null;
            String valueRoom=null;
        try {             

            if(map.size()>0){
                if(map.containsKey("servicio")){
                    valueServ = map.get("servicio").toString();
                }
                if(map.containsKey("nombreCama")){
                    valueRoom = map.get("nombreCama").toString();
                }
            }
            return camaMapper.obtenerTotalServicioCamas(idEntidadHosp,valueServ,valueRoom);
        } catch (Exception e) {
            throw new Exception("Error al obtenerTotalServicioCamas "+ e.getMessage());
        }
    }

    
}
