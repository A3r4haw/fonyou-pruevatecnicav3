/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.List;
import mx.mc.model.Capsula;
import org.apache.ibatis.annotations.Param;



/**
 *
 * @author olozada
 */
public interface CapsulaMapper extends GenericCrudMapper<Capsula,String>{

   List<Capsula> obtieneListaCapsulasActivas(@Param("activo") int activo , @Param("idEstructura") String idEstructura);

   List<Capsula> obteneridCapsula (@Param("codigoCapsula") String codigoCapsula);
   
   List<Capsula> obtenerCapsulas(@Param("cadenaBusqueda") String cadenaBusqueda) throws Exception;
   
   List<Capsula> obtenerCapsulasponombre(@Param("cadenaBusqueda") String cadenaBusqueda) throws Exception;

   
}
