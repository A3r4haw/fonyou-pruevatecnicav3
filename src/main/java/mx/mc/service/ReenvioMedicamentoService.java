/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.Date;
import java.util.List;
import mx.mc.model.ReenvioMedicamento;
import mx.mc.model.Surtimiento;
import mx.mc.model.SurtimientoInsumo;

/**
 *
 * @author mcalderon
 */
public interface ReenvioMedicamentoService extends GenericCrudService<ReenvioMedicamento,String> {
    public List<ReenvioMedicamento> obtenerDatosReenvioMedicamentos(String cama, String prescripcion, String numPaciente, Date fechaPrescripcion)throws Exception;
            
    public List<ReenvioMedicamento> obtenerMotivosReenvio() throws Exception;
    
    boolean surtirReenvioMedicamento (    
    Surtimiento surtimientoSelect,
    List<SurtimientoInsumo> surtimientoInsumoList
    )throws Exception;
}

