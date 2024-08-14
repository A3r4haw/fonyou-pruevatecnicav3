/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.model.AlmacenControl;
import mx.mc.model.AlmacenControl_Extended;
import mx.mc.model.Estructura;
/**
 *
 * @author bbautista
 */
public interface AlmacenControlService extends GenericCrudService<AlmacenControl,String> {
    public boolean actualizaEstatusGabinete(String idMedicamento,Integer estatusGabinete) throws Exception;
    public AlmacenControl obtenerIdPuntosControl(String idAlmacen,String idMedicamento) throws Exception;
    public boolean actualizarMasivo(List<AlmacenControl> insumos) throws Exception;
    public List<AlmacenControl_Extended> obtenerTotalInsumoAlmacen(List<Estructura> estructuraList,List<String> insumoList) throws Exception;
}
