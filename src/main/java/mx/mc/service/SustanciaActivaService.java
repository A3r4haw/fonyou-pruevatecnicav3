/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import mx.mc.model.SustanciaActiva;

import java.util.List;
/**
 *
 * @author bbautista
 */
public interface SustanciaActivaService extends GenericCrudService<SustanciaActiva,String>{
    List<SustanciaActiva> obtenerTodo() throws Exception; 
    public List<SustanciaActiva> obtenerListaPorNombre(String nombreSuatanciaActiva) throws Exception;
    public SustanciaActiva obtenerSustanciaPorNombre(String nombreSuatanciaActiva) throws Exception;
    public SustanciaActiva obtenerPorId(int idSustanciaActiva) throws Exception;
    
    public Integer obtenerSiguienteId() throws Exception;
}
