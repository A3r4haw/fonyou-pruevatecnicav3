/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.model.MedicamentoConcomitante;
import mx.mc.model.MedicamentoConcomitanteExtended;

/**
 *
 * @author bbautista
 */
public interface MedicamentoConcomitanteService extends GenericCrudService<MedicamentoConcomitante, String> {
    List<MedicamentoConcomitanteExtended> obtenerListaByIdReaccion(String idReaccion) throws Exception;
    boolean insertarLista(List<MedicamentoConcomitante> conmitanteList) throws Exception;
    boolean actualizarListaInsumos(List<MedicamentoConcomitante> conmitanteList) throws Exception;
}
