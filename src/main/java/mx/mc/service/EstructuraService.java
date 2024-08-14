/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.Date;
import java.util.List;

import mx.mc.model.Estructura;
import mx.mc.model.EstructuraExtended;
import mx.mc.model.TipoSurtimiento;
import mx.mc.model.Usuario;

/**
 *
 * @author bbautista
 */
public interface EstructuraService extends GenericCrudService<Estructura, String> {

    public Estructura obtenerEstructuraPadre() throws Exception;

    public Estructura obtenerEstructura(String idEstructura) throws Exception;

    public List<Estructura> obtenerUnidadesJerarquicasByTipoArea(int idTipoAreaEstructura) throws Exception;

    public List<Estructura> obtenerAlmacenes() throws Exception;

    public List<Estructura> obtenerUnidadesOrderTipo() throws Exception;

    public List<Estructura> obtenerServiciosAlmcenPorIdEstructura(String idEstructura) throws Exception;

    public List<Estructura> obtenerEstructurasPorTipo(List<Integer> listaTipos) throws Exception;

    public Estructura getEstructuraPadreIdEstructura(String idEstructura) throws Exception;

    public List<Estructura> obtenerAlmacenesTransfer(String idEstructura) throws Exception;
    
    public List<Estructura> obtenerAlmacenesForTransfer(String idEstructura,Integer idTipoAlmacen) throws Exception;

    public List<Estructura> getEstructuraByLisTipoAlmacen(List<Integer> listTipoAlmacen) throws Exception;
    
    public List<Estructura> getEstructuraByLisTipoAreaEstructura(List<Integer> listTipoAreaEstructura) throws Exception;

    public List<Estructura> getEstructuraServicios(List<Integer> listTipoAlmacen, List<String> listaEstructuras) throws Exception;

    public Estructura getEstructuraForName(String nombre) throws Exception;

    public List<Estructura> obtenerAlmacenesEntidad(String idEstructura,Integer idTipoAlmacen) throws Exception;

    public List<Estructura> obtenerEstructurasLista() throws Exception;

    public List<Estructura> obtenerAlmacenesActivos() throws Exception;

    public List<Estructura> obtenerAlmacenPadre(Estructura estructura) throws Exception;

    public List<Estructura> obtenerAlmacenServicio() throws Exception;

    public List<Estructura> obtenerAlmacenPadreDesc(Estructura estructura) throws Exception;

    public Estructura getEstructuraForClave(String claveEstructura) throws Exception;

    public Estructura obtenerEstructuraHl7(String idEstructura) throws Exception;

    public List<Estructura> obtenerEstructuraSnHl7() throws Exception;

    public List<String> obtenerIdsEstructuraJerarquica(String idEstructuraPadre, Boolean almacen) throws Exception;

    public List<Estructura> obtenerEstructurasPadreCadit(String idEstructuraPadre) throws Exception;

    public List<Estructura> getEstructuraListTipoAreaEstructura(List<Integer> listIdAreaEstructura) throws Exception;

    public List<Estructura> getEstructurDugthersYMe(String idEstructuraPadre) throws Exception;

    public String validarExistenciaEstrucutra(String nombre) throws Exception;

    public List<Estructura> obtenerServicioQueSurtePorIdEstructura(String idEstructura) throws Exception;

    public Estructura obtenerAlmacenQueSurtePorIdEstructura(String idEstructura) throws Exception;

    public String validarExistenciaClave(String claveEstructura) throws Exception;

    public List<Estructura> obtenerEstructurasPorIdAlmacenPeriferico(String idEstructura) throws Exception;
    
    public List<Estructura> obtenerEstructurasActivosPorId(List<String> listaIdAlmacenes) throws Exception;
    
    public List<Estructura> obtenerAlmacenesQueSurtenServicio ( String idEstructura) throws Exception;
    
    public Estructura obtenerEstructuraAlmacenPerifericoPorNombreEstructura(String nombreEstructura) throws Exception;
    
    public boolean quitarAlmacenPeriferico(Estructura estructura) throws Exception;
    
    public List<Estructura> obtenerPorTipoAreayTipoAreaEstructura(Integer idTipoArea, Integer idTipoAreaEstructura) throws Exception;
    
    public List<Estructura> obtenerAlmacenesServicioPCActivos() throws Exception;
    
    public List<Estructura> estructurasByFolioMUS(String folio, String status) throws Exception;
    
    public List<Estructura> estructuraItemList(String idInsumo,List<Estructura> serviciosList) throws Exception;
    
    List<Estructura> estFarmAlmacenSubalmacen(String idEstructura) throws Exception;
    
    public boolean actualizarEntidadTodas(String idEntidadHospitalaria, String idUsuario, Date updateFecha) throws Exception;
    
    public List<Estructura> obtenerEstructurasPorIdEntidad(String idEntidadHospitalaria) throws Exception;
    
    public List<EstructuraExtended> obtenerEstructurasByEntidadTipoArea(int idTipoAreaEstructura,String idEntidad) throws Exception;
    
    public List<Estructura> obtenerPorTipoAlmacen(Integer idTipoAlmacen,String idEntidad) throws Exception;
    
    public boolean insertarAlmacen(EstructuraExtended estructura,String[] tipoSurtimientoList)throws Exception;
    
    public boolean actualizarAlmacen(EstructuraExtended estructura,String[] tipoSurtimientoList)throws Exception;
    
    public List<Estructura> obtenerServicios() throws Exception;
    public List<Estructura> obtenerAlmacenesPorInsumo(Estructura estructura, String idInsumo) throws Exception;
    public String obtenerAlmacenPrincipal(String idEstructura) throws Exception;
    
    // Obtiene la lista de almacenes a corde al perfil del usuario
    public List<Estructura> obtenerAlmacenesPorPerfil(Estructura estructuraUser,Usuario user) throws Exception;
    public List<Estructura> obtenerServicioActivosPorIdEstructuraOporIdAlmacen(String idEstructura , String idEstructuraAlmacen )throws Exception;
    
}
