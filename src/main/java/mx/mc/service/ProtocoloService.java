/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.model.Protocolo;
import mx.mc.model.ProtocoloExtended;
import org.primefaces.model.SortOrder;

/**
 *
 * @author gcruz
 */
public interface ProtocoloService extends GenericCrudService<Protocolo, String> {

    public List<Protocolo> obtenerListaProtocolo(Protocolo p) throws Exception;

    public List<ProtocoloExtended> buscarProtocolosDiagnostico(List<String> listaDiagnosticos) throws Exception;

    public List<ProtocoloExtended> buscarProtocolosDiagnosticoAndMedicamentos(List<String> listaDiagnosticos, List<String> listaMedicamentos) throws Exception;

    public List<ProtocoloExtended> obtenerListaProtocolos(
            String cadenaBusqueda,
            int startingAt,
            int maxPerPage,
            String sortField,
            SortOrder sortOrder) throws Exception;

    public Long obtenerTotalProtocolos(String cadenaBusqueda) throws Exception;

    public boolean insertarProtocolo(ProtocoloExtended protocolo) throws Exception;

    public ProtocoloExtended obtenerProtocoloPoId(String idProtocolo) throws Exception;

    public ProtocoloExtended buscarExistencia(ProtocoloExtended protocolo) throws Exception;

    public boolean actualizarProtocolo(ProtocoloExtended protocolo) throws Exception;
    
    public boolean eliminarProtocoloPorId(String idProtocolo) throws Exception;
    
}
