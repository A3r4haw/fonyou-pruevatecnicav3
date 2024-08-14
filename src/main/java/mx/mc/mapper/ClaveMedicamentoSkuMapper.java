/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.List;
import mx.mc.model.ClaveMedicamentoSku;
import mx.mc.model.ClaveMedicamentoSku_Extend;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author bbautista
 */
public interface ClaveMedicamentoSkuMapper extends GenericCrudMapper<ClaveMedicamentoSku, String> {

    ClaveMedicamentoSku obtenerClave(@Param("sku") String sku);

    Integer obtenerCantidadPorClaveMedicamento(@Param("idMedicamento") String idMedicamento);

    List<ClaveMedicamentoSku> obtenerListaClave(@Param("sku") String sku);
    
    List<ClaveMedicamentoSku_Extend> obtenerListaClavesSku (@Param("sku") String sku, @Param("idEstructura") String idEstructura,@Param("idUsuario") String idUsuario);

}
