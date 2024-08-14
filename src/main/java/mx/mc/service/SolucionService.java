/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.model.Prescripcion;
import mx.mc.model.PrescripcionInsumo;
import mx.mc.model.Solucion;
import mx.mc.model.SolucionExtended;
import mx.mc.model.Surtimiento;
import mx.mc.model.SurtimientoEnviado;
import mx.mc.model.SurtimientoInsumo;
import mx.mc.model.SurtimientoInsumo_Extend;

/**
 *
 * @author bbautista
 */
public interface SolucionService extends GenericCrudService<Solucion, String> {

    public boolean actualizaSolucion(Solucion solucion, Surtimiento surtimiento, List<SurtimientoInsumo> surInsumoLista, List<SurtimientoEnviado> surEnviadoLista) throws Exception;

    public boolean mezclaRechazoYReproceso(
            Solucion solucion,
             Surtimiento surtimiento,
             List<SurtimientoInsumo> surInsumoLista,
             List<SurtimientoEnviado> surEnviadoLista) throws Exception;

    public boolean mezclaIncorrecta(Solucion solucion,
             Prescripcion prescripcion,
             List<PrescripcionInsumo> listaPrescripcionInsumo,
             Surtimiento surtimiento,
             List<SurtimientoInsumo> listaSurtimientoInsumo) throws Exception;

    public SolucionExtended obtenerDatosSolucionByIdSurtimiento(String idSurtimiento) throws Exception;

    public List<SolucionExtended> obtenerAutoCompSolucion(String nombre, String idEstructura) throws Exception;

    public SolucionExtended obtenerSolucion(String idSolucion, String idSurtimiento) throws Exception;

    public boolean rechazarMezcla(Solucion solucion, Surtimiento surtimiento, List<SurtimientoInsumo_Extend> listaSurtimientoInsumo, boolean agrupaSolucionesXPrescripcion) throws Exception;

    public Integer obtenerNumeroSolucionesProcesadas(String idPrescripcion) throws Exception;

    public String obtenerDescripcionMezcla(String idSurtimiento) throws Exception;
}
