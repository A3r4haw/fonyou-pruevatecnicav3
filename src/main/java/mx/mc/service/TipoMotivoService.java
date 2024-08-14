/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;

import mx.mc.model.TipoMotivo;
/**
 *
 * @author bbautista
 */
public interface TipoMotivoService extends GenericCrudService <TipoMotivo,String> {

    public List<TipoMotivo> listaAjusteInventario() throws Exception;
    
    public List<TipoMotivo> getListaByIdTipoMovimiento(int idTipoMovimiento) throws Exception;
    
    public List<TipoMotivo> listaDevolucionEnPrescripcion() throws Exception;
    
    public List<TipoMotivo> obtieneListaMotivosActivos(int activo) throws Exception;
    
    public List<TipoMotivo> obtieneListaMotivosActivosEntrada() throws Exception; 
    
}
