/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.ArrayList;
import java.util.List;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.ProtocoloMapper;
import mx.mc.model.Protocolo;
import mx.mc.model.ProtocoloExtended;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author gcruz
 */
@Service
public class ProtocoloServiceImpl extends GenericCrudServiceImpl<Protocolo, String> implements ProtocoloService {

    @Autowired
    private ProtocoloMapper protocoloMapper;

    @Autowired
    public ProtocoloServiceImpl(GenericCrudMapper<Protocolo, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public List<Protocolo> obtenerListaProtocolo(Protocolo p) throws Exception {
        try {
            return protocoloMapper.obtenerListaProtocolo(p);
        } catch (Exception ex) {
            throw new Exception("Error al buscar la lista de protocolos:  " + ex.getMessage());
        }
    }

    @Override
    public List<ProtocoloExtended> buscarProtocolosDiagnostico(List<String> listaDiagnosticos) throws Exception {
        try {
            return protocoloMapper.buscarProtocolosDiagnostico(listaDiagnosticos);
        } catch (Exception ex) {
            throw new Exception("Error al buscar los protocolos por diagnosticos:  " + ex.getMessage());
        }
    }

    @Override
    public List<ProtocoloExtended> buscarProtocolosDiagnosticoAndMedicamentos(List<String> listaDiagnosticos,
            List<String> listaMedicamentos) throws Exception {
        try {
            return protocoloMapper.buscarProtocolosDiagnosticoAndMedicamentos(listaDiagnosticos, listaMedicamentos);
        } catch (Exception ex) {
            throw new Exception("Error al buscar los protocolos por diagnosticos y medicamentos:  " + ex.getMessage());
        }
    }

    @Override
    public List<ProtocoloExtended> obtenerListaProtocolos(String cadenaBusqueda, int startingAt, int maxPerPage, String sortField, SortOrder sortOrder) throws Exception {
        List<ProtocoloExtended> listaInteracciones = new ArrayList<>();
        try {
            String order = sortOrder == SortOrder.ASCENDING ? "asc" : sortOrder == SortOrder.DESCENDING ? "desc" : null;
            listaInteracciones = protocoloMapper.obtenerListaProtocolos(cadenaBusqueda, startingAt, maxPerPage, sortField, order);
        } catch (Exception ex) {
            throw new Exception("Ocurrio un error al buscar la lista de indicaciones terapeuticas:  " + ex.getMessage());
        }
        return listaInteracciones;
    }

    @Override
    public Long obtenerTotalProtocolos(String cadenaBusqueda) throws Exception {
        try {
            return protocoloMapper.obtenerTotalProtocolos(cadenaBusqueda);
        } catch (Exception ex) {
            throw new Exception("Ocurrio un error al obtener el total de indicaciones terapeuticas:  " + ex.getMessage());
        }
    }

    @Override
    public boolean insertarProtocolo(ProtocoloExtended protocolo) throws Exception {
        try {
            return protocoloMapper.insertarProtocolo(protocolo);
        } catch (Exception ex) {
            throw new Exception("Error al insertar el Protocolo :: {} " + ex.getMessage());
        }
    }

    @Override
    public ProtocoloExtended obtenerProtocoloPoId(String idProtocolo) throws Exception {
        try {
            return protocoloMapper.obtenerProtocoloPoId(idProtocolo);
        } catch (Exception ex) {
            throw new Exception("Error al obtener el Protocolo por id: " + idProtocolo + "  " + ex.getMessage());
        }

    }

    @Override
    public ProtocoloExtended buscarExistencia(ProtocoloExtended protocolo) throws Exception {
        try {
            return protocoloMapper.buscarExistencia(protocolo);
        } catch (Exception ex) {
            throw new Exception("Error al buscar existencia de protocolo " + ex.getMessage());
        }
    }

    @Override
    public boolean actualizarProtocolo(ProtocoloExtended protocolo) throws Exception {
        try {
            return protocoloMapper.actualizarProtocolo(protocolo);
        } catch (Exception ex) {
            throw new Exception("Error al actualizar la interacción terapeutica " + ex.getMessage());
        }
    }

    @Override
    public boolean eliminarProtocoloPorId(String idProtocolo) throws Exception {
        try {
            return protocoloMapper.eliminarProtocoloPorId(idProtocolo);
        } catch (Exception ex) {
            throw new Exception("Error al eliminar el protocolo " + ex.getMessage());
        }
    }

}
