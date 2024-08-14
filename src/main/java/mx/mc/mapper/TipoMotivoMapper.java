/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import mx.mc.model.TipoMotivo;
/**
 *
 * @author bbautista
 */
public interface TipoMotivoMapper extends GenericCrudMapper<TipoMotivo,String> {
    List<TipoMotivo> listaAjusteInventario();
    
    List<TipoMotivo> getListaByIdTipoMovimiento(@Param("idTipoMovimiento") int idTipoMovimiento);
    
    List<TipoMotivo> listaMotivoDevolucion();
    
    List<TipoMotivo> obtieneListaMotivosActivos(@Param("activo") int activo);
    
    List<TipoMotivo> obtieneListaMotivosActivosEntrada();
}
