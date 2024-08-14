/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.VistaRecepcionMedicamentoMapper;
import mx.mc.model.Estructura;
import mx.mc.model.VistaRecepcionMedicamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author bbautista
 */
@Service
public class VistaRecepcionMedicamentoServiceImpl extends GenericCrudServiceImpl<VistaRecepcionMedicamento, String> implements VistaRecepcionMedicamentoService {

    @Autowired
    private VistaRecepcionMedicamentoMapper recepcionMapper;

    @Autowired
    public VistaRecepcionMedicamentoServiceImpl(GenericCrudMapper<VistaRecepcionMedicamento, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public List<VistaRecepcionMedicamento> obtenerBusqueda(String cadena) throws Exception {
        List<VistaRecepcionMedicamento> vistaResultado = new ArrayList<>();
        try {
            vistaResultado = recepcionMapper.obtenerBusqueda(cadena);
        } catch (Exception ex) {
            throw new Exception("Error al obtener la busqueda:" + Arrays.toString(ex.getSuppressed()));
        }
        return vistaResultado;
    }

    @Override
    public VistaRecepcionMedicamento obtenerByFolioPrescripcion(String folioPrescripcion) throws Exception {
        try {
            return recepcionMapper.obtenerByFolioPrescripcion(folioPrescripcion);
        } catch (Exception ex) {
            throw new Exception("Error al obtener datos de la vista recepcion" + ex.getMessage());
        }
    }

    @Override
    public VistaRecepcionMedicamento obtenerByFolioPrescripcionForDev(String folioPrescripcion) throws Exception {
        try {
            return recepcionMapper.obtenerPrescripcionForDev(folioPrescripcion);
        } catch (Exception ex) {
            throw new Exception("Error al obtener datos de la vista recepcion" + ex.getMessage());
        }
    }

    @Override
    public List<VistaRecepcionMedicamento> obtenerSurtimientosPorRecibir(List<String> idsEstructura) throws Exception {
        List<VistaRecepcionMedicamento> vistaResultado = new ArrayList<>();
        try {
            vistaResultado = recepcionMapper.obtenerSurtimientosPorRecibir(idsEstructura);
        } catch (Exception ex) {
            throw new Exception("Error al obtener la obtenerSurtimientosPorRecibir:" + ex.getMessage());
        }
        return vistaResultado;
    }

    @Override
    public List<VistaRecepcionMedicamento> obtenerSurtimientosRecibidos(List<String> idsEstructura) throws Exception {
        List<VistaRecepcionMedicamento> vistaResultado = new ArrayList<>();
        try {
            vistaResultado = recepcionMapper.obtenerSurtimientosRecibidos(idsEstructura);
        } catch (Exception ex) {
            throw new Exception("Error al obtener la obtenerSurtimientosRecibidos:" + ex.getMessage());
        }
        return vistaResultado;
    }
    
    @Override
    public List<VistaRecepcionMedicamento> obtenerSurtimientosCancelados(List<String> idsEstructura) throws Exception {
        List<VistaRecepcionMedicamento> vistaResultado = new ArrayList<>();
        try {
            vistaResultado = recepcionMapper.obtenerSurtimientosCancelados(idsEstructura);
        } catch (Exception ex) {
            throw new Exception("Error al obtener la obtenerSurtimientosCancelados:" + ex.getMessage());
        }
        return vistaResultado;
    }

    @Override
    public List<VistaRecepcionMedicamento> obtenerSurtimientosPorRecibirV2(List<Estructura> lista) throws Exception {
        List<VistaRecepcionMedicamento> vistaResultado = new ArrayList<>();
        try {
            vistaResultado = recepcionMapper.obtenerSurtimientosPorRecibirV2(lista);
        } catch (Exception ex) {
            throw new Exception("Error al obtener la obtenerSurtimientosPorRecibir:" + ex.getMessage());
        }
        return vistaResultado;
    }

    @Override
    public List<VistaRecepcionMedicamento> obtenerSurtimientosGabinetes(List<Estructura> lista) throws Exception {
         List<VistaRecepcionMedicamento> vistaResultado = new ArrayList<>();
        try {
            vistaResultado = recepcionMapper.obtenerSurtimientosGabinetes(lista);
        } catch (Exception ex) {
            throw new Exception("Error al obtener la obtenerSurtimientosGabinetes:" + ex.getMessage());
        }
        return vistaResultado;
    }
    
}
