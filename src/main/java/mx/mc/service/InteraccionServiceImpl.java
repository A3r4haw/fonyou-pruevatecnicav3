/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.ArrayList;
import java.util.List;
import mx.mc.dto.MedicamentoDTO;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.InteraccionMapper;
import mx.mc.mapper.SustanciaActivaMapper;
import mx.mc.model.AlertaFarmacovigilancia;
import mx.mc.model.Interaccion;
import mx.mc.model.InteraccionExtended;
import mx.mc.model.SustanciaActiva;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author gcruz
 */
@Service
public class InteraccionServiceImpl extends GenericCrudServiceImpl<Interaccion, String> implements InteraccionService {

    @Autowired
    InteraccionMapper interaccionMapper;
    
    @Autowired
    SustanciaActivaMapper sustanciaActivaMapper;
    
    public InteraccionServiceImpl(GenericCrudMapper<Interaccion, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
    
    @Override
    public List<InteraccionExtended> obtenerInteracciones(String cadenaBusqueda, int tipoInteraccion, int startingAt, int maxPerPage, String sortField, SortOrder sortOrder) throws Exception {
        List<InteraccionExtended> listaInteracciones = new ArrayList<>();
        try {
            String order = sortOrder == SortOrder.ASCENDING ? "asc" : sortOrder == SortOrder.DESCENDING ? "desc" : null;
            listaInteracciones = interaccionMapper.obtenerInteracciones(cadenaBusqueda, tipoInteraccion, startingAt, maxPerPage, sortField, order);
        } catch(Exception ex) {
            throw new Exception("Ocurrio un error al buscar la lista de interacciones:  " + ex.getMessage());
        }
        return listaInteracciones;
    }

    
    @Override
    public Long obtenerTotalInteracciones(String cadenaBusqueda, int tipoInteraccion) throws Exception {
        try {
            return interaccionMapper.obtenerTotalInteracciones(cadenaBusqueda, tipoInteraccion);
        }catch(Exception ex) {
            throw new Exception("Ocurrio un error al obtener el total de interacciones:  " +ex.getMessage());
        }
    }

    @Override
    public Integer obtenerSiguienteIdInteraccion() throws Exception {
        Integer idInteraccion = 0;
        try {
            idInteraccion = interaccionMapper.obtenerSiguienteIdInteraccion();
        } catch(Exception ex) {
            throw new Exception("Ocurrio un error al buscar el siguiente idInteracción:  " + ex.getMessage());
        }
        return idInteraccion;
    }
    
    @Override
    public Boolean insertarInteraccion(InteraccionExtended interaccion) throws Exception {
        Boolean resp = false;
        try {
            resp = interaccionMapper.insertarInteraccion(interaccion);
        } catch(Exception ex) {
            throw new Exception("Ocurrio un error al momento de insertar la interacción:  " + ex.getMessage());
        }
        return resp;
    }   

    @Override
    public InteraccionExtended buscarInteraccion(InteraccionExtended interaccionExtended) throws Exception {
        //InteraccionExtended interaccion = new InteraccionExtended();
        try {
            return interaccionMapper.buscarInteraccion(interaccionExtended);
        } catch(Exception ex) {
            throw new Exception("Ocurrio un error al buscar la existencia de interacción:  " + ex.getMessage());
        }
        //return interaccion;
    }

    @Override
    public boolean eliminarInteraccionPorId(Integer idInteraccion) throws Exception {
        try {
            return interaccionMapper.eliminarInteraccionPorId(idInteraccion);
        } catch(Exception ex) {
            throw new Exception("Error al intentar eliminar la interacción id: " + idInteraccion + "  " + ex.getMessage());
        }
    }

    @Override
    public List<AlertaFarmacovigilancia> obtenerAlertaInteraccion(String pacienteNumero) throws Exception {
        List<AlertaFarmacovigilancia> listaAlertaFarmacovigilancia = null;
        try {            
            List<InteraccionExtended> listaInteracciones = interaccionMapper.obtenerAlertaInteraccion(pacienteNumero);
            AlertaFarmacovigilancia unaAlerta;
            for(InteraccionExtended unaInteraccion : listaInteracciones) {
                int num = 1;
                unaAlerta = new AlertaFarmacovigilancia();
                unaAlerta.setNumero(num);
                SustanciaActiva sustancia1 = sustanciaActivaMapper.obtenerPorId(unaInteraccion.getIdSustanciaUno());
                unaAlerta.setFactor1(sustancia1.getNombreSustanciaActiva());
                SustanciaActiva sustancia2 = sustanciaActivaMapper.obtenerPorId(unaInteraccion.getIdSustanciaDos());
                unaAlerta.setFactor1(sustancia2.getNombreSustanciaActiva());
                unaAlerta.setRiesgo(unaInteraccion.getNotas());
                unaAlerta.setTipo(unaInteraccion.getTipoInteraccion());
                unaAlerta.setOrigen(unaInteraccion.getNombreEmisor());
                listaAlertaFarmacovigilancia.add(unaAlerta);
            }
        } catch(Exception ex) {
            throw new Exception("Ocurrio un error al momento de buscar las alertas de interacción medicamentosas:  " + ex.getMessage());
        }
        return listaAlertaFarmacovigilancia;
    }

    @Override
    public InteraccionExtended obtenerInteraccionById(Integer idInteraccion) throws Exception {
        try {
            return interaccionMapper.obtenerInteraccionById(idInteraccion);
        } catch(Exception ex) {
            throw new Exception("Error al obtener la interacciónid: " + idInteraccion + "  " + ex.getMessage());
        }
    }

    @Override
    public Boolean actualizarInteraccion(InteraccionExtended interaccion) throws Exception {
        Boolean resp = false;
        try {
            resp = interaccionMapper.actualizarInteraccion(interaccion);
        } catch(Exception ex) {
            throw new Exception("Ocurrio un error al momento de actualizar la interacción:  " + ex.getMessage());
        }
        return resp;
    }

    @Override
    public List<InteraccionExtended> obtenerInteraccionesClavesMedicamento(List<MedicamentoDTO> listaClavesMedicamento) throws Exception {
        try {
            return interaccionMapper.obtenerInteraccionesClavesMedicamento(listaClavesMedicamento);
        } catch(Exception ex) {
            throw new Exception("Ocurrio un error al obtener interacciones por claves de medicamento:  " + ex.getMessage());
        }
    }
    
}
