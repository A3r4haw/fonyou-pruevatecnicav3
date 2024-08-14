/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.dto.MedicamentoDTO;
import mx.mc.mapper.AdjuntoMapper;
import mx.mc.mapper.FoliosMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.HipersensibilidadAdjuntoMapper;
import mx.mc.mapper.HipersensibilidadMapper;
import mx.mc.model.AdjuntoExtended;
import mx.mc.model.Folios;
import mx.mc.model.Hipersensibilidad;
import mx.mc.model.HipersensibilidadAdjunto;
import mx.mc.model.HipersensibilidadExtended;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author gcruz
 */
@Service
public class HipersensibilidadServiceImpl extends GenericCrudServiceImpl<Hipersensibilidad, String> implements HipersensibilidadService {
    
    @Autowired
    HipersensibilidadMapper hipersensibilidadMapper;
    
    @Autowired
    FoliosMapper foliosMapper;
    
    @Autowired
    AdjuntoMapper adjuntoMapper;
    
    @Autowired
    HipersensibilidadAdjuntoMapper hipersensibilidadAdjuntoMapper;
    
    public HipersensibilidadServiceImpl(GenericCrudMapper<Hipersensibilidad, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public List<HipersensibilidadExtended> obtenerReaccionesHipersensibilidad(String cadenaBusqueda, int startingAt, int maxPerPage, String sortField, SortOrder sortOrder) throws Exception {
        try {
            String order = sortOrder == SortOrder.ASCENDING ? "asc" : sortOrder == SortOrder.DESCENDING ? "desc" : null;
            return hipersensibilidadMapper.obtenerReaccionesHipersensibilidad(cadenaBusqueda, startingAt, maxPerPage, sortField, order);
        } catch(Exception ex) {
            throw new Exception("Error al obtener reacciones de hipersensibilidad:  " + ex.getMessage());
        }
    }

    @Override
    public Long obtenerTotalReacionesHipersensibilidad(String cadenaBusqueda) throws Exception {
        try {
            return hipersensibilidadMapper.obtenerTotalReacionesHipersensibilidad(cadenaBusqueda);
        } catch(Exception ex) {
            throw new Exception("Error al obtener total de reacciones de hipersensibilidad:  " + ex.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean registrarReaccionHipersensibilidad(Folios folioHipersensibilidad, HipersensibilidadExtended hipersensibilidadExtended,
                            List<AdjuntoExtended> listaAdjuntos, List<HipersensibilidadAdjunto> listaHiperAdjunto) throws Exception {
        boolean resp = true;
        try {
            resp = foliosMapper.actualizaFolios(folioHipersensibilidad);
            if(resp) {
                resp = hipersensibilidadMapper.insertarReaccionHipersensibilidad(hipersensibilidadExtended);
                if(resp) {
                    if(!listaAdjuntos.isEmpty()) {
                        resp = adjuntoMapper.insertarListaAdjuntos(listaAdjuntos);
                        if(resp) {
                            if(!listaHiperAdjunto.isEmpty()) {
                                resp = hipersensibilidadAdjuntoMapper.insertarListaHiperAdjuntos(listaHiperAdjunto);
                                if(!resp) {
                                    throw new Exception("Error al insertar registo(s) en tabla intermedia hipersensibilidadAdjunto");
                                }
                            }                            
                        } else {
                            throw new Exception("Error al insertar los archivos adjuntos");
                        }
                    }                    
                } else {
                    throw new Exception("Error al insetar la reacción de hipersensibilidad");
                }
            } else {
                throw new Exception("Error al actualizar el folio de hipersensibilidad");
            }
             
        } catch(Exception ex) {
            throw new Exception("Error al intentar insertar un registro de reacción de hipersensibilidad   " + ex.getMessage());
        }
        return resp;
    }   

    @Override
    public HipersensibilidadExtended obtenerHipersensibilidadPorIdHiper(String idHipersensibilidad) throws Exception {
        try {
            return hipersensibilidadMapper.obtenerHipersensibilidadPorIdHiper(idHipersensibilidad);
        } catch(Exception ex) {
            throw new Exception("Error al buscar la reacción de hipersensibilidad:  " + ex.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean actualizarReaccionHipersensibilidad(HipersensibilidadExtended hipersensibilidadExtended,
                            List<AdjuntoExtended> listaAdjuntos, List<HipersensibilidadAdjunto> listaHiperAdjunto) throws Exception {
        try {
            boolean resp = true;
            
            resp = hipersensibilidadMapper.actualizarReaccionHipersensibilidad(hipersensibilidadExtended);
            if(resp) {
                if(!listaAdjuntos.isEmpty()) {
                    resp = adjuntoMapper.insertarListaAdjuntos(listaAdjuntos);
                    if(resp) {
                        resp = hipersensibilidadAdjuntoMapper.insertarListaHiperAdjuntos(listaHiperAdjunto);
                        if(!resp) {
                            throw new Exception("Error al insertar registo(s) en tabla intermedia hipersensibilidadAdjunto");
                        }
                    } else {
                        throw new Exception("Error al insertar los archivo(s) adjuntos");
                    }
                }                
            } else {
                throw new Exception("Error al actualizar el registro de hipersensibilidad");
            }
        return resp;                
        } catch(Exception ex) {
            throw new Exception("Error al actualizar la reacción de hipersensibilidad:  " + ex.getMessage());
        }
    }

    @Override
    public boolean eliminarHipersensibilidad(String idHipersensibilidad) throws Exception {
        try {
            return hipersensibilidadMapper.eliminarHipersensibilidad(idHipersensibilidad);
        } catch(Exception ex) {
            throw new Exception("Error al eliminar la reacción de hipersensibilidad:  " + ex.getMessage());
        }
    }

    @Override
    public List<HipersensibilidadExtended> obtenerListaReacHiperPorIdPaciente(String idPaciente, List<MedicamentoDTO> listaMedicamentos) throws Exception {
        try {
            return hipersensibilidadMapper.obtenerListaReacHiperPorIdPaciente(idPaciente, listaMedicamentos);
        } catch(Exception ex) {
            throw new Exception("Error al obtener reacciones de hipersensibilidad paciente e insumos:   " + ex.getMessage());
        }
    }
    
}
