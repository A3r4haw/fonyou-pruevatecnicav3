/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.List;
import mx.mc.model.ProtocoloExtended;
import mx.mc.model.Protocolo;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author gcruz
 */
public interface ProtocoloMapper extends GenericCrudMapper<Protocolo, String> {

//    @Override
    List<Protocolo> obtenerListaProtocolo(Protocolo p);

    List<ProtocoloExtended> buscarProtocolosDiagnostico(@Param("listaDiagnosticos") List<String> listaDiagnosticos);

    List<ProtocoloExtended> buscarProtocolosDiagnosticoAndMedicamentos(@Param("listaDiagnosticos") List<String> listaDiagnosticos,
            @Param("listaMedicamentos") List<String> listaMedicamentos);

    List<ProtocoloExtended> buscarProtocolosByIdDiagnosticoAndIdProtocolo(@Param("idDiagnostico") String idDiagnostico,
            @Param("idProtocolo") Integer idProtocolo);

    List<ProtocoloExtended> obtenerListaProtocolos(
            @Param("cadena") String cadenaBusqueda,
             @Param("startingAt") int startingAt,
             @Param("maxPerPage") int maxPerPage,
             @Param("sortField") String sortField,
             @Param("sortOrder") String sortOrder);

    Long obtenerTotalProtocolos(@Param("cadena") String cadenaBusqueda);

    boolean insertarProtocolo(ProtocoloExtended protocolo);

    ProtocoloExtended obtenerProtocoloPoId(@Param("idProtocolo") String idProtocolo);

    ProtocoloExtended buscarExistencia(ProtocoloExtended protocolo);

    boolean actualizarProtocolo(ProtocoloExtended protocolo);

    boolean eliminarProtocoloPorId(@Param("idProtocolo") String idProtocolo);

}
