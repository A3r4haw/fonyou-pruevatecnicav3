/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.List;
import mx.mc.model.ReabastoRecepcion;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author gcruz
 */
public interface ReabastoRecepcionMapper extends GenericCrudMapper<ReabastoRecepcion, String> {
    
    boolean insertarListaReabastoRecepcion(@Param("listReabastoRecepcion") List<ReabastoRecepcion> listReabastoRecepcion);
    
}
