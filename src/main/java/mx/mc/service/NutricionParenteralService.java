/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.model.DiagnosticoPaciente;
import mx.mc.model.Folios;
import mx.mc.model.NutricionParenteral;
import mx.mc.model.NutricionParenteralDetalleExtended;
import mx.mc.model.NutricionParenteralExtended;
import mx.mc.model.PacienteServicio;
import mx.mc.model.Prescripcion;
import mx.mc.model.PrescripcionInsumo;
import mx.mc.model.Solucion;
import mx.mc.model.Surtimiento;
import mx.mc.model.SurtimientoEnviado;
import mx.mc.model.SurtimientoInsumo;
import mx.mc.model.Visita;
import org.primefaces.model.SortOrder;

/**
 *
 * @author gcruz
 */
public interface NutricionParenteralService extends GenericCrudService<NutricionParenteral,String> {
    
    public boolean guardarOrdenPreparacionMezcla(NutricionParenteralExtended nutricionParenteral, Folios folioOrden) throws Exception;
    
    public boolean actualizarOrdenPreparacionMezcla(List<NutricionParenteralDetalleExtended> listaActualizar, List<NutricionParenteralDetalleExtended> listaBorrar, 
                                                    NutricionParenteralExtended nutricionParenteral) throws Exception;
    
    public List<NutricionParenteralExtended> obtenerBusquedaLazy(String cadena, String idEstructura, int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, List<Integer> estatusSolucionLista
            , String tipoPrescripcion, String estatusSolucion, String nombreEstructura, String tipoSolucion, String nombreMedico
            , String folio, String fechaProgramada2, String nombrePaciente, String cama, String folioPrescripcion ) throws Exception;
    
    public Long obtenerBusquedaTotalLazy(String cadena, String almacen, List<Integer> estatusSolucionLista
            , String tipoPrescripcion, String estatusSolucion, String nombreEstructura, String tipoSolucion, String nombreMedico
            , String folio, String fechaProgramada2, String nombrePaciente, String cama, String folioPrescripcion) throws Exception;
    
    public NutricionParenteralExtended obtenerNutricionParenteralById(String idNutricionParenteral) throws Exception;
    
    public boolean generarSolucionNutricionParenteral(boolean actualiza, Prescripcion prescripcion, List<PrescripcionInsumo> listaPrescripcionInsumos, Surtimiento surtimiento,
                                                        List<SurtimientoInsumo> listaSurtimientoInsumos, List<SurtimientoEnviado> listaSurtimientoEnviado, Solucion solucion,
                                                        Folios folioSurtimiento, NutricionParenteral nutricionParenteral, List<DiagnosticoPaciente> listDiagnosticosPaciente) throws Exception;
        
    public boolean cerrarServicioYAsignarNuevoServicio(PacienteServicio servicioCerrar, PacienteServicio asignarServicio) throws Exception;
    
    public boolean generarNuevaVisitaYPacienteServicio(Visita visita, PacienteServicio pacienteServicio) throws Exception;
    
    public boolean registrarPrecripcionSurtimientoSolucionNPT(
            Prescripcion prescripcion
            , List<PrescripcionInsumo> listaPrescripcionInsumos
            , Surtimiento surtimiento
            , List<SurtimientoInsumo> listaSurtimientoInsumos
            , List<SurtimientoEnviado> listaSurtimientoEnviado
            , Solucion solucion
            , NutricionParenteralExtended nutricionParenteral
            , List<DiagnosticoPaciente> listDiagnosticosPaciente
    ) throws Exception;
    
    public boolean actualizarPrecripcionSurtimientoSolucionNPT(
            Prescripcion prescripcion
            , List<PrescripcionInsumo> listaPrescripcionInsumos
            , Surtimiento surtimiento
            , List<SurtimientoInsumo> listaSurtimientoInsumos
            , List<SurtimientoEnviado> listaSurtimientoEnviado
            , Solucion solucion
            , NutricionParenteralExtended nutricionParenteral
            , List<DiagnosticoPaciente> listDiagnosticosPaciente
    ) throws Exception;
    
}
