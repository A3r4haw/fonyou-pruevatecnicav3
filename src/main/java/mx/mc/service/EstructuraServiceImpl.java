/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import mx.mc.mapper.EstructuraMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.Estructura;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import mx.mc.enums.TipoAreaEstructura_Enum;
import mx.mc.enums.TipoPerfilUsuario_Enum;
import mx.mc.mapper.EstructuraTipoSurtimientoMapper;
import mx.mc.model.EstructuraAlmacenServicio;
import mx.mc.model.EstructuraExtended;
import mx.mc.model.EstructuraTipoSurtimiento;
import mx.mc.model.TipoSurtimiento;
import mx.mc.model.Usuario;
import mx.mc.util.Comunes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author bbautista
 */
@Service
public class EstructuraServiceImpl extends GenericCrudServiceImpl<Estructura, String> implements EstructuraService {

    @Autowired
    private EstructuraMapper estructuraMapper;
    
    @Autowired
    private EstructuraTipoSurtimientoMapper estructuraTipoSurtimientoMapper;

    @Autowired
    public EstructuraServiceImpl(GenericCrudMapper<Estructura, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    /**
     * @author gcruz
     * @return Estructura Metodo para obtener el padre de la estructura por su
     * idEstructura
     */
    @Override
    public Estructura obtenerEstructuraPadre() throws Exception {
        Estructura unaEstructura = new Estructura();
        try {
            unaEstructura = estructuraMapper.obtenerEstructuraPadre();
        } catch (Exception ex) {
            throw new Exception("Error al obtener Estructura Padre " + ex.getMessage());
        }

        return unaEstructura;
    }

    @Override
    public List<Estructura> obtenerUnidadesJerarquicasByTipoArea(int idTipoAreaEstructura) throws Exception {
        List<Estructura> listEstructura = new ArrayList<>();
        try {
            listEstructura = estructuraMapper.obtenerUnidadesJerarquicas(idTipoAreaEstructura);
        } catch (Exception ex) {
            throw new Exception("Error al obtener Estructura Padre " + ex.getMessage());
        }
        return listEstructura;
    }

    @Override
    public List<Estructura> obtenerAlmacenes() throws Exception {
        List<Estructura> listEstruct = new ArrayList<>();
        try {
            listEstruct = estructuraMapper.obtenerAlmacenes();
        } catch (Exception ex) {
            throw new Exception("Error al obtener Almacenes - " + ex.getMessage());
        }
        return listEstruct;
    }

    @Override
    public Estructura obtenerEstructura(String idEstructura) throws Exception {
        try {
            return estructuraMapper.obtenerEstructura(idEstructura);
        } catch (Exception ex) {
            throw new Exception("Error en obtenerEstructura - " + ex.getMessage());
        }

    }

    @Override
    public List<Estructura> obtenerUnidadesOrderTipo() throws Exception {
        List<Estructura> listEstruct = new ArrayList<>();
        try {
            listEstruct = estructuraMapper.obtenerUnidadesOrderTipo();
        } catch (Exception ex) {
            throw new Exception("Error al obtener unidades ordenadas por tipo y fecha de insercion - " + ex.getMessage());
        }
        return listEstruct;
    }

    @Override
    public List<Estructura> obtenerServiciosAlmcenPorIdEstructura(String idEstructura) throws Exception {
        try {
            return estructuraMapper.obtenerServiciosAlmcenPorIdEstructura(idEstructura);
        } catch (Exception ex) {
            throw new Exception("Error al obtener unidades ordenadas por tipo y fecha de insercion - " + ex.getMessage());
        }
    }

    @Override
    public List<Estructura> obtenerEstructurasPorTipo(List<Integer> listaTipos) throws Exception {
        try {
            return estructuraMapper.obtenerEstructurasPorTipo(listaTipos);
        } catch (Exception ex) {
            throw new Exception("Error al obtener la Lista de Estructuras " + ex.getMessage());
        }
    }

    @Override
    public List<Estructura> obtenerAlmacenesTransfer(String idEstructura) throws Exception {
        List<Estructura> listEstruct = new ArrayList<>();
        try {
            listEstruct = estructuraMapper.almacenesTransfer(idEstructura);
        } catch (Exception ex) {
            throw new Exception("Error al obtenerAlmacenesTransfer - " + ex.getMessage());
        }
        return listEstruct;
    }

    @Override
    public List<Estructura> obtenerAlmacenesForTransfer(String idEstructura, Integer idTipoAlmacen) throws Exception {
        List<Estructura> listEstruct = new ArrayList<>();
        try {
            listEstruct = estructuraMapper.almacenesForTransfer(idEstructura, idTipoAlmacen);
        } catch (Exception ex) {
            throw new Exception("Error al obtenerAlmacenesForTransfer - " + ex.getMessage());
        }
        return listEstruct;
    }

    @Override
    public Estructura getEstructuraPadreIdEstructura(String idEstructura) throws Exception {
        try {
            return estructuraMapper.getEstructuraPadreIdEstructura(idEstructura);
        } catch (Exception ex) {
            throw new Exception("Error al obtener la estructura padre!!  " + ex.getMessage());
        }
    }

    @Override
    public List<Estructura> getEstructuraByLisTipoAlmacen(List<Integer> listTipoAlmacen) throws Exception {
        try {
            return estructuraMapper.getEstructuraByLisTipoAlmacen(listTipoAlmacen);
        } catch (Exception ex) {
            throw new Exception("Error al obtener la lista de almacenes y subalmacenes para el comobo");
        }
    }

    @Override
    public List<Estructura> getEstructuraByLisTipoAreaEstructura(List<Integer> listTipoAreaEstructura) throws Exception {
        try {
            return estructuraMapper.getEstructuraByLisTipoAreaEstructura(listTipoAreaEstructura);
        } catch (Exception ex) {
            throw new Exception("Error al obtener la lista de almacenes y subalmacenes para el comobo");
        }
    }

    @Override
    public List<Estructura> getEstructuraServicios(List<Integer> listTipoAlmacen, List<String> listaEstructuras) throws Exception {
        try {
            return estructuraMapper.getEstructuraServicios(listTipoAlmacen, listaEstructuras);
        } catch (Exception e) {
            throw new Exception("Error al obtener la lista de Servicios " + e.getMessage());
        }
    }

    @Override
    public Estructura getEstructuraForName(String nombre) throws Exception {
        try {
            return estructuraMapper.getEstructuraForName(nombre);
        } catch (Exception ex) {
            throw new Exception("Error al obtener la estructura en getEstructuraForName!!  " + ex.getMessage());
        }
    }

    @Override
    public List<Estructura> obtenerAlmacenesEntidad(String idEstructura, Integer idTipoAlmacen) throws Exception {
        try {
            return estructuraMapper.getAlmacenesForEntidad(idEstructura, idTipoAlmacen);
        } catch (Exception ex) {
            throw new Exception("Error al obtener la estructura en obtenerAlmacenesEntidad!!  " + ex.getMessage());
        }
    }

    @Override
    public List<Estructura> obtenerEstructurasLista() throws Exception {
        try {
            return estructuraMapper.obtenerEstructurasLista();
        } catch (Exception e) {
            throw new Exception("Error al obtener la lista de Estructuras" + e.getMessage());
        }
    }

    @Override
    public List<Estructura> obtenerAlmacenesActivos() throws Exception {
        List<Estructura> listEstruct = new ArrayList<>();
        try {
            listEstruct = estructuraMapper.obtenerAlmacenesActivos();
        } catch (Exception ex) {
            throw new Exception("Error al obtener Almacenes Activos - " + ex.getMessage());
        }
        return listEstruct;
    }

    @Override
    public List<Estructura> obtenerAlmacenPadre(Estructura estructura) throws Exception {
        List<Estructura> listEstruct = new ArrayList<>();
        try {
            listEstruct = estructuraMapper.obtenerAlmacenPadre(estructura);
        } catch (Exception ex) {
            throw new Exception("Error al obtener Almacen Padre - " + ex.getMessage());
        }
        return listEstruct;
    }

    @Override
    public List<Estructura> obtenerAlmacenServicio() throws Exception {
        List<Estructura> listEstruct = new ArrayList<>();
        try {
            listEstruct = estructuraMapper.obtenerAlmacenServicio();
        } catch (Exception ex) {
            throw new Exception("Error al obtener Almacen Servicio - " + ex.getMessage());
        }
        return listEstruct;
    }

    @Override
    public List<Estructura> obtenerAlmacenPadreDesc(Estructura estructura) throws Exception {
        List<Estructura> listEstruct = new ArrayList<>();
        try {
            listEstruct = estructuraMapper.obtenerAlmacenPadreDesc(estructura);
        } catch (Exception ex) {
            throw new Exception("Error al obtener Almacen Padre Desc - " + ex.getMessage());
        }
        return listEstruct;
    }

    @Override
    public Estructura getEstructuraForClave(String claveEstructura) throws Exception {
        try {
            return estructuraMapper.getEstructuraForClave(claveEstructura);
        } catch (Exception ex) {
            throw new Exception("Error al obtener la estructura por clave de estructura!!  " + ex.getMessage());
        }
    }

    @Override
    public Estructura obtenerEstructuraHl7(String idEstructura) throws Exception {
        try {
            return estructuraMapper.obtenerEstructuraHl7(idEstructura);
        } catch (Exception e) {
            throw new Exception("Error al obtener la estructura por clave de estructura!!  " + e.getMessage());
        }
    }

    @Override
    public List<Estructura> obtenerEstructuraSnHl7() throws Exception {
        try {
            return estructuraMapper.obtenerEstructuraSnHl7();
        } catch (Exception e) {
            throw new Exception("Error al obtener las estructuras Sn HL7!!  " + e.getMessage());
        }
    }

    @Override
    public List<String> obtenerIdsEstructuraJerarquica(String idEstructuraPadre, Boolean almacen) throws Exception {
        List<String> listaResultado = new ArrayList<>();
        try {
            listaResultado = estructuraMapper.obtenerIdsEstructuraJerarquica(idEstructuraPadre, almacen);
        } catch (Exception ex) {
            throw new Exception("Error al obtener la obtenerSurtimientosPorRecibir:" + ex.getMessage());
        }
        return listaResultado;
    }

    @Override
    public List<Estructura> obtenerEstructurasPadreCadit(String idEstructuraPadre) throws Exception {
        try {
            return estructuraMapper.obtenerEstructurasPadreCadit(idEstructuraPadre);
        } catch (Exception e) {
            throw new Exception("Error las estructuras padre de Cadit" + e.getMessage());
        }
    }

    @Override
    public List<Estructura> getEstructuraListTipoAreaEstructura(List<Integer> listIdAreaEstructura) throws Exception {
        try {
            return estructuraMapper.getEstructuraListTipoAreaEstructura(listIdAreaEstructura);
        } catch (Exception ex) {
            throw new Exception("Error las estructuras por lista de idAreaEstructura" + ex.getMessage());
        }
    }

    @Override
    public List<Estructura> getEstructurDugthersYMe(String idEstructuraPadre) throws Exception {
        try {
            return estructuraMapper.getEstructurDugthersYMe(idEstructuraPadre);
        } catch (Exception ex) {
            throw new Exception("Error las estructuras padre y el mismo IdEstructura" + ex.getMessage());
        }
    }

    @Override
    public String validarExistenciaEstrucutra(String nombre) throws Exception {
        try {
            return estructuraMapper.validarExistenciaEstrucutra(nombre);
        } catch (Exception e) {
            throw new Exception("Error al validar la existencia de la Estructura " + e.getMessage());
        }
    }

    @Override
    public List<Estructura> obtenerServicioQueSurtePorIdEstructura(String idEstructura) throws Exception {
        try {
            return estructuraMapper.obtenerServicioQueSurtePorIdEstructura(idEstructura);
        } catch (Exception ex) {
            throw new Exception("Error al buscar los servicios que surte el Almacen  por IdEstructura" + ex.getMessage());
        }
    }

    @Override
    public Estructura obtenerAlmacenQueSurtePorIdEstructura(String idEstructura) throws Exception {
        try {
            return estructuraMapper.obtenerAlmacenQueSurtePorIdEstructura(idEstructura);
        } catch (Exception ex) {
            throw new Exception("Error al buscar los servicios que surte el Almacen  por IdEstructura" + ex.getMessage());
        }
    }

    @Override
    public String validarExistenciaClave(String claveEstructura) throws Exception {
        try {
            return estructuraMapper.validarExistenciaClave(claveEstructura);
        } catch (Exception e) {
            throw new Exception("Error al validar la existencia de la Estructura " + e.getMessage());
        }
    }

    @Override
    public List<Estructura> obtenerEstructurasPorIdAlmacenPeriferico(String idEstructura) throws Exception {
        try {
            return estructuraMapper.obtenerEstructurasPorIdAlmacenPeriferico(idEstructura);
        } catch (Exception e) {
            throw new Exception("Error al obtener Estructuras por idAlmacenPeriferico " + e.getMessage());
        }
    }

    @Override
    public List<Estructura> obtenerEstructurasActivosPorId(List<String> listaIdEstructuras) throws Exception {
        List<Estructura> listEstruct = new ArrayList<>();
        try {
            listEstruct = estructuraMapper.obtenerEstructurasActivosPorId(listaIdEstructuras);
        } catch (Exception ex) {
            throw new Exception("Error al obtener Almacenes Activos por lista de idEstructura- " + ex.getMessage());
        }
        return listEstruct;
    }

    @Override
    public List<Estructura> obtenerAlmacenesQueSurtenServicio(String idEstructura) throws Exception {
        try {
            return estructuraMapper.obtenerAlmacenesQueSurtenServicio(idEstructura);    
        } catch (Exception ex) {
            throw new Exception("Error al buscar los almacenes que surten el servicio por IdEstructura" + ex.getMessage());
        }
    }

    @Override
    public Estructura obtenerEstructuraAlmacenPerifericoPorNombreEstructura(String nombreEstructura) throws Exception {
        try {
            return estructuraMapper.obtenerEstructuraAlmacenPerifericoPorNombreEstructura(nombreEstructura);
        } catch (Exception e) {
            throw new Exception("Error al obtener idAlmacenPeriferico por nombreEstructura " + e.getMessage());
        }
    }

    @Override
    public boolean quitarAlmacenPeriferico(Estructura estructura) throws Exception {
        try {
            return estructuraMapper.quitarAlmacenPeriferico(estructura);
        } catch (Exception ex) {
            throw new Exception("Error al momento de quitar el almacen periferico al servicio" + ex.getMessage());
        }
    }

    @Override
    public List<Estructura> obtenerPorTipoAreayTipoAreaEstructura(Integer idTipoArea, Integer idTipoAreaEstructura) throws Exception {
        List<Estructura> listEstructura = new ArrayList<>();
        try {
            listEstructura = estructuraMapper.obtenerPorTipoAreayTipoAreaEstructura(idTipoArea, idTipoAreaEstructura);
        } catch (Exception ex) {
            throw new Exception("Error al obtener Estructuras Padre " + ex.getMessage());
        }
        return listEstructura;
    }

    @Override
    public List<Estructura> obtenerAlmacenesServicioPCActivos() throws Exception {
        List<Estructura> listEstruct = new ArrayList<>();
        try {
            listEstruct = estructuraMapper.obtenerAlmacenesServicioPCActivos();
        } catch (Exception ex) {
            throw new Exception("Error al obtener Almacenes Activos - " + ex.getMessage());
        }
        return listEstruct;
    }

    @Override
    public List<Estructura> estructurasByFolioMUS(String folio, String status) throws Exception {
        try {
            return estructuraMapper.estructurasByFolioMUS(folio, status);
        } catch (Exception ex) {
            throw new Exception("Error al obtener Servicios por folioMUS - " + ex.getMessage());
        }
    }

    @Override
    public List<Estructura> estFarmAlmacenSubalmacen(String idEstructura) throws Exception {
        try {
            return estructuraMapper.estFarmAlmacenSubalmacen(idEstructura);
        } catch (Exception ex) {
            throw new Exception("Error al obtener farmacias, almacenes y subalmacenes" + ex.getMessage());
        }
    }

    @Override
    public boolean actualizarEntidadTodas(String idEntidadHospitalaria, String idUsuario, Date updateFecha) throws Exception {
        try {
            return estructuraMapper.actualizarEntidadTodas(idEntidadHospitalaria, idUsuario, updateFecha);
        } catch (Exception ex) {
            throw new Exception("Ocurrio un error al actualiza la entidadHospitalaria para todas las estructuras" + ex.getMessage());
        }
    }

    @Override
    public List<Estructura> obtenerEstructurasPorIdEntidad(String idEntidadHospitalaria) throws Exception {
        try {
            return estructuraMapper.obtenerEstructurasPorIdEntidad(idEntidadHospitalaria);
        } catch (Exception ex) {
            throw new Exception("Error al obtener las estructuras por idEntidadHospitalaria  " + ex.getMessage());
        }
    }

    @Override
    public List<Estructura> estructuraItemList(String idInsumo, List<Estructura> serviciosList) throws Exception {
        try {
            return estructuraMapper.estructuraItemList(idInsumo, serviciosList);
        } catch (Exception ex) {
            throw new Exception("Error al obtener Servicios por item - " + ex.getMessage());
        }
    }
    
    @Override
    public List<Estructura> obtenerAlmacenesPorInsumo(Estructura estructura, String idInsumo) throws Exception {
        try{
            return estructuraMapper.obtenerAlmacenesPorInsumo(estructura, idInsumo);
        }catch(Exception ex){
            throw new Exception("Error al obtener Almacenes por item - " + ex.getMessage());
        }
    }
    
    
    

    @Override
    public List<EstructuraExtended> obtenerEstructurasByEntidadTipoArea(int idTipoAreaEstructura, String idEntidad) throws Exception {
        try {
            return estructuraMapper.obtenerEstructurasByEntidadTipoArea(idTipoAreaEstructura, idEntidad);
        } catch (Exception ex) {
            throw new Exception("Error al obtener obtenerUnidadesJerarquicasByEntidadTipoArea - " + ex.getMessage());
        }
    }

    @Override
    public List<Estructura> obtenerPorTipoAlmacen(Integer idTipoAlmacen, String idEntidad) throws Exception {
        try {
            return estructuraMapper.obtenerPorTipoAlmacen(idTipoAlmacen, idEntidad);
        } catch (Exception ex) {
            throw new Exception("Error al obtener obtenerPorTipoAlmacen - " + ex.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean insertarAlmacen(EstructuraExtended estructura, String[] tipoSurtimientoList) throws Exception {
        boolean resp = false;
        try {
            List<EstructuraTipoSurtimiento> estructuraTipoSurtimientoList = new ArrayList<>();
            for (String idTipo : tipoSurtimientoList) {
                EstructuraTipoSurtimiento item = new EstructuraTipoSurtimiento();
                item.setIdTipoSurtimiento(idTipo);
                item.setIdEstructuraAlmacen(estructura.getIdEstructura());
                item.setIdEstructuraTipoSurtimiento(Comunes.getUUID());
                
                estructuraTipoSurtimientoList.add(item);
            }
            
           resp = estructuraTipoSurtimientoMapper.insertarList(estructuraTipoSurtimientoList);
           if(resp){
               resp = estructuraMapper.insertar(estructura);
           }
        } catch (Exception ex) {
            throw new Exception("Error al insertar la estructura - " + ex.getMessage());
        }
        return resp;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean actualizarAlmacen(EstructuraExtended estructura, String[] tipoSurtimientoList) throws Exception {
        boolean resp = false;
        try {
            List<EstructuraTipoSurtimiento> estructuraTipoSurtimientoList = new ArrayList<>();
            for (String idTipo : tipoSurtimientoList) {
                EstructuraTipoSurtimiento item = new EstructuraTipoSurtimiento();
                item.setIdEstructuraTipoSurtimiento(Comunes.getUUID());
                item.setIdEstructuraAlmacen(estructura.getIdEstructura());
                item.setIdTipoSurtimiento(idTipo);

                estructuraTipoSurtimientoList.add(item);
            }
            estructuraTipoSurtimientoMapper.eliminarIdAlmacen(estructura.getIdEstructura());
            if(estructuraTipoSurtimientoList.size()>0){
                resp = estructuraTipoSurtimientoMapper.insertarList(estructuraTipoSurtimientoList);
                if(resp){
                    resp=estructuraMapper.actualizar(estructura);
                }
            }else{
                throw new Exception("Debe seleccionar un tipo de surtimiento.");
            }
            
        } catch (Exception ex) {
            throw new Exception("Error al actualizar la estructura - " + ex);
        }
        return resp;
    }

    @Override
    public List<Estructura> obtenerServicios() throws Exception {
        List<Estructura> listEstruct = new ArrayList<>();
        try {
            listEstruct = estructuraMapper.obtenerServicios();
        } catch (Exception ex) {
            throw new Exception("Error al obtener Almacen Servicio - " + ex.getMessage());
        }
        return listEstruct;
    }
    
    @Override
    public String obtenerAlmacenPrincipal(String idEstructura) throws Exception{
        try {
            return estructuraMapper.obtenerAlmacenPadreOfSurtimiento(idEstructura);
        } catch (Exception ex) {
             throw new Exception("Error al obtener Almacen principal - " + ex.getMessage());
        }
    }
    
    @Override
    public List<Estructura> obtenerAlmacenesPorPerfil(Estructura estructuraUser,Usuario user) throws Exception{
        try {
            List<Estructura> estructuraList = new ArrayList<>();
            List<Estructura> almacenesServicio = new ArrayList<>();
            List<Estructura> estructuraAll = estructuraMapper.obtenerAlmacenes();
            
            if((Objects.equals(user.getAdministrador(), TipoPerfilUsuario_Enum.ADMIN.getIdTipoPerfil()))){
                estructuraList = estructuraAll;
            }
            else if(estructuraUser!= null){
                
                if(!Objects.equals(estructuraUser.getIdTipoAreaEstructura(), TipoAreaEstructura_Enum.ALMACEN.getValue())){
                    almacenesServicio = estructuraMapper.obtenerAlmacenesQueSurtenServicio(estructuraUser.getIdEstructura());
                }
                
                if((Objects.equals(user.getIdTipoPerfil(), TipoPerfilUsuario_Enum.JEFE_AREA.getIdTipoPerfil()))){
                    if(almacenesServicio.size()>0){                    
                        // Recorreomos todos los almacenes que surten al sercicio
                        for(Estructura item : almacenesServicio){    
                            estructuraList.add(item);
                            List<String> idsHijos = estructuraMapper.obtenerIdsEstructuraJerarquica(item.getIdEstructura(), true);
                            for(String id :idsHijos){
                                Estructura hijo = estructuraAll.stream().filter(p-> p.getIdEstructura().equals(id)).findAny().orElse(null);
                                if(hijo!=null){
                                    estructuraList.add(hijo);
                                }
                            }
                        }
                    }//Si la estructura del usuario es de tipo almacen, buscamos sus hijos
                    else{                        
                        estructuraList.add(estructuraUser);
                        List<String> idsHijos = estructuraMapper.obtenerIdsEstructuraJerarquica(estructuraUser.getIdEstructura(), true);
                        for(String id :idsHijos){
                            Estructura hijo = estructuraAll.stream().filter(p-> p.getIdEstructura().equals(id)).findAny().orElse(null);
                            if(hijo!=null)
                                estructuraList.add(hijo);
                        }                        
                    }
                }
                else {
                    Estructura almacenUser = estructuraAll.stream().filter(e-> e.getIdEstructura().equals(estructuraUser.getIdEstructura())).findAny().orElse(null);
                    estructuraList.add(almacenUser);
                }
            }            
            
            return estructuraList;
        } catch (Exception ex) {
             throw new Exception("Error al obtener la lista de Almacenes - " + ex.getMessage());       }
    }
    
    @Override
    public List<Estructura> obtenerServicioActivosPorIdEstructuraOporIdAlmacen(String idEstructura , String idEstructuraAlmacen )throws Exception {
        List<Estructura> listEstruct = new ArrayList<>();
        try {
            listEstruct = estructuraMapper.obtenerServicioActivosPorIdEstructuraOporIdAlmacen(idEstructura, idEstructuraAlmacen);
        } catch (Exception ex) {
            throw new Exception("Error al obtenerServicioActivosPorIdEstructuraOporIdAlmacen - " + ex.getMessage());
        }
        return listEstruct;
    }
    
}
