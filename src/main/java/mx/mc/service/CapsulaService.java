/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;


import java.util.List;
import mx.mc.model.Capsula;

/**
 *
 * @author olozada
 */
public interface CapsulaService extends GenericCrudService <Capsula , String>{
  
    public List<Capsula> obtieneListaCapsulasActivas(int activo , String idEstructura) throws Exception;
    
    public List<Capsula> obteneridCapsula (String codigoCapsula)throws Exception;
    
    List<Capsula> obtenerCapsulas(String cadenaBusqueda) throws Exception;
    
    List<Capsula> obtenerCapsulasponombre(String cadenaBusqueda) throws Exception;

}
