/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.List;
import mx.mc.model.MedicamentoConcomitante;
import mx.mc.model.MedicamentoConcomitanteExtended;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author bbautista
 */
public interface MedicamentoConcomitanteMapper extends GenericCrudMapper<MedicamentoConcomitante, String> {
    public List<MedicamentoConcomitanteExtended> obtenerListaByIdReaccion(@Param("idReaccion")String idReaccion);
    public boolean insertarListaInsumos(@Param("listaInsumos") List<MedicamentoConcomitante> concomitanteList);
    public boolean actualizarListaInsumos(@Param("listaInsumos") List<MedicamentoConcomitante> concomitanteList);
    public boolean eliminarInsumos(@Param("idReaccion")String idReaccion);
}
