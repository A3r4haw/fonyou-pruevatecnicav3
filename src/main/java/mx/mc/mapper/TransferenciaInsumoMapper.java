/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;
import java.util.List;
import mx.mc.model.TransferenciaInsumo;
import org.apache.ibatis.annotations.Param;
/**
 *
 * @author bbautista
 */
public interface TransferenciaInsumoMapper extends GenericCrudMapper<TransferenciaInsumo,String>{
    public boolean insertarLista(List<TransferenciaInsumo> rInsumos);
    List<TransferenciaInsumo> detalleTransfer(@Param("idTransferencia") String idTransferencia);
}
