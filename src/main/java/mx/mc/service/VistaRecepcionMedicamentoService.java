/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.model.Estructura;
import mx.mc.model.VistaRecepcionMedicamento;

/**
 *
 * @author bbautista
 */
public interface VistaRecepcionMedicamentoService extends GenericCrudService<VistaRecepcionMedicamento, String> {

    public List<VistaRecepcionMedicamento> obtenerBusqueda(String cadena) throws Exception;

    public VistaRecepcionMedicamento obtenerByFolioPrescripcion(String folioPrescripcion) throws Exception;

    public VistaRecepcionMedicamento obtenerByFolioPrescripcionForDev(String folioPrescripcion) throws Exception;

    public List<VistaRecepcionMedicamento> obtenerSurtimientosPorRecibir(List<String> idsEstructura) throws Exception;
    
    public List<VistaRecepcionMedicamento> obtenerSurtimientosRecibidos(List<String> idsEstructura) throws Exception;
    
    public List<VistaRecepcionMedicamento> obtenerSurtimientosCancelados(List<String> idsEstructura) throws Exception;
    
    public List<VistaRecepcionMedicamento> obtenerSurtimientosPorRecibirV2(List<Estructura> lista) throws Exception;
    
    public List<VistaRecepcionMedicamento> obtenerSurtimientosGabinetes(List<Estructura> lista) throws Exception;
}
