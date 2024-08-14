/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.List;
import mx.mc.model.TipoSolucion;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author apalacios
 */
public interface TipoSolucionMapper extends GenericCrudMapper<TipoSolucion,String> {
    List<TipoSolucion> obtenerByListaClaves(@Param ("listaClaves") List<String> listaClaves);
}
