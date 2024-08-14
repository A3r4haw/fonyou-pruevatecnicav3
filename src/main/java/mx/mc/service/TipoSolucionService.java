/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.model.TipoSolucion;

/**
 *
 * @author apalacios
 */
public interface TipoSolucionService extends GenericCrudService<TipoSolucion, String> {
    public List<TipoSolucion> obtenerByListaClaves(List<String> listaClaves) throws Exception;
}
