/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;
import java.util.List;
import mx.mc.model.Transferencia;
import org.apache.ibatis.annotations.Param;
/**
 *
 * @author bbautista
 */
public interface TransferenciaMapper extends GenericCrudMapper<Transferencia,String> {
    List<Transferencia> obtenerTransferencias(@Param("cadena") String cadena);
}
