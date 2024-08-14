/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;
import java.util.List;
import mx.mc.model.Folios;
import mx.mc.model.Transferencia;
import mx.mc.model.TransferenciaInsumo;
/**
 *
 * @author bbautista
 */
public interface TransferenciaService extends GenericCrudService<Transferencia,String>{
    public List<Transferencia> obtenerBusqueda(String cadena) throws Exception;
    public boolean insertarSolicitud(Transferencia transferencia, List<TransferenciaInsumo> insumos,Folios foli) throws Exception;    
}
